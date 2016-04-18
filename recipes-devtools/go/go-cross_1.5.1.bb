require go_${PV}.inc

inherit cross

SRC_URI += "\
        file://Fix-ccache-compilation-issue.patch \
        file://fix-cross-compilation.patch \
        "

DEPENDS = "virtual/${TARGET_PREFIX}gcc libgcc"

do_compile() {
  ## install a build of Go 1.4 in the SYSROOT so we don't need it anywhere else
  ## in the system (as it currently is the default)
  export GOROOT_BOOTSTRAP="${WORKDIR}/go-${GO_BOOTSTRAP_VERSION}/go"

  cd ${GOROOT_BOOTSTRAP}/src
  ./all.bash
  cd -

  ## Setting `$GOBIN` doesn't do any good, looks like it ends up copying binaries there.
  export GOROOT_FINAL="${SYSROOT}${libdir}/go"

  setup_go_arch

  # Is there a bug in the cross-compiler support for CGO? Can't get it
  # to work without this wrapper
  cat > ${WORKDIR}/${TARGET_PREFIX}gcc <<EOT
#!/bin/sh
exec ${TARGET_PREFIX}gcc ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET} "\$@"
EOT
  chmod +x ${WORKDIR}/${TARGET_PREFIX}gcc

  cat > ${WORKDIR}/${TARGET_PREFIX}g++ <<EOT
#!/bin/sh
exec ${TARGET_PREFIX}g++ ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET} "\$@"
EOT
  chmod +x ${WORKDIR}/${TARGET_PREFIX}g++

  ## TODO: consider setting GO_EXTLINK_ENABLED
  export CGO_ENABLED="1"
  export CC=${BUILD_CC}
  export CC_FOR_TARGET="${WORKDIR}/${TARGET_PREFIX}gcc"
  export CXX_FOR_TARGET="${WORKDIR}/${TARGET_PREFIX}g++"
  export GO_GCFLAGS="${HOST_CFLAGS}"
  export GO_LDFLAGS="${HOST_LDFLAGS}"

  set > ${WORKDIR}/go-${PV}.env
  cd ${WORKDIR}/go-${PV}/go/src && bash -x ./make.bash

  ## The result is `go env` giving this:
  # GOARCH="amd64"
  # GOBIN=""
  # GOCHAR="6"
  # GOEXE=""
  # GOHOSTARCH="amd64"
  # GOHOSTOS="linux"
  # GOOS="linux"
  # GOPATH=""
  # GORACE=""
  # GOROOT="/home/build/poky/build/tmp/sysroots/x86_64-linux/usr/lib/cortexa8hf-vfp-neon-poky-linux-gnueabi/go"
  # GOTOOLDIR="/home/build/poky/build/tmp/sysroots/x86_64-linux/usr/lib/cortexa8hf-vfp-neon-poky-linux-gnueabi/go/pkg/tool/linux_amd64"
  ## The above is good, but these are a bit odd... especially the `-m64` flag.
  # CC="arm-poky-linux-gnueabi-gcc"
  # GOGCCFLAGS="-fPIC -m64 -pthread -fmessage-length=0"
  # CXX="arm-poky-linux-gnueabi-g++"
  ## TODO: test on C+Go project.
  # CGO_ENABLED="1"
}

do_install() {
  GOROOT_FINAL="${D}${libdir}/go"

  install -d "${D}${bindir}" "${GOROOT_FINAL}"
  tar -C "${WORKDIR}/go-${PV}/go" -cf - bin lib src pkg test |
  tar -C "${GOROOT_FINAL}" -xpf -
  mv "${GOROOT_FINAL}/bin/"* "${D}${bindir}/"

  for t in gcc g++ ; do
    cat > ${GOROOT_FINAL}/bin/${TARGET_PREFIX}${t} <<EOT
#!/bin/sh
exec ${TARGET_PREFIX}${t} ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET} "\$@"
EOT
    chmod +x ${GOROOT_FINAL}/bin/${TARGET_PREFIX}${t}
  done
}

## TODO: implement do_clean() and ensure we actually do rebuild super cleanly
