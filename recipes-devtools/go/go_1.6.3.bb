require go_${PV}.inc

PR = "r5"

PN_class-native = "go-native"
PN_class-cross = "go-cross"

GOROOT_FINAL_class-target="${libdir}/go"
GOROOT_FINAL_class-native="${STAGING_LIBDIR_NATIVE}/go"
GOROOT_FINAL_class-cross="${libdir}/go"
export GOROOT_FINAL

# Go binaries are not understood by the strip tool.
INHIBIT_SYSROOT_STRIP = "1"

do_compile() {
  export GOROOT_BOOTSTRAP="${GOROOT_BOOTSTRAP}"

  setup_go_arch

  setup_cgo_gcc_wrapper

  ## Add WORKDIR to path.
  export PATH="${WORKDIR}/:$PATH"
  ## TODO: consider setting GO_EXTLINK_ENABLED
  export CGO_ENABLED="${GO_CROSS_CGO_ENABLED}"
  export CC=${BUILD_CC}
  export CC_FOR_TARGET="${TARGET_PREFIX}gcc"
  export CXX_FOR_TARGET="${TARGET_PREFIX}g++"
  export GO_GCFLAGS="${HOST_CFLAGS}"
  export GO_LDFLAGS="${HOST_LDFLAGS}"

  set > ${WORKDIR}/go-${PV}.env
  cd ${WORKDIR}/go-${PV}/go/src && bash -x ./make.bash

  # log the resulting environment
  env "GOROOT=${WORKDIR}/go-${PV}/go" "${WORKDIR}/go-${PV}/go/bin/go" env
}

go_install() {
  
  install -d "${D}${bindir}" "${D}${GOROOT_FINAL}"
  tar -C "${WORKDIR}/go-${PV}/go" -cf - bin lib src pkg test |
  tar -C "${D}${GOROOT_FINAL}" -xf -
  rm -rf "${D}${GOROOT_FINAL}/pkg/bootstrap"

  mv "${D}${GOROOT_FINAL}/bin/"* "${D}${bindir}/"

  rm -f "${D}${GOROOT_FINAL}/src/"*.rc

  for t in gcc g++ ; do
    cat > ${D}${GOROOT_FINAL}/bin/${TARGET_PREFIX}${t} <<EOT
#!/bin/sh
exec ${TARGET_PREFIX}${t} ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET} "\$@"
EOT
    chmod +x ${D}${GOROOT_FINAL}/bin/${TARGET_PREFIX}${t}
  done
}

do_install() {
  go_install
}

do_install_class-cross() {
  go_install
}

do_install_class-target() {
  setup_go_arch

  go_install

  if test "${GOHOSTOS}_${GOHOSTARCH}" != "${GOOS}_${GOARCH}" ; then
    mv "${D}${bindir}/${GOOS}_${GOARCH}/"* "${D}${bindir}/"
    rmdir "${D}${bindir}/${GOOS}_${GOARCH}"
    rm -rf "${D}${GOROOT_FINAL}/pkg/${GOHOSTOS}_${GOHOSTARCH}"
    rm -rf "${D}${GOROOT_FINAL}/pkg/tool/${GOHOSTOS}_${GOHOSTARCH}"
    # XXX: too aggressive?
    find "${D}" -name "*_${GOHOSTARCH}.syso" -delete
  fi

  rm -rf "${D}${GOROOT_FINAL}"/src/debug/*/testdata/

  chown -R root:root "${D}"
}

## TODO: implement do_clean() and ensure we actually do rebuild super cleanly

INSANE_SKIP_go = "staticdev"

BBCLASSEXTEND = "cross native"
