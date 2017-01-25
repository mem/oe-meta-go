DESCRIPTION = "Go is an open source programming language that makes it easy to build simple, reliable, and efficient software."
HOMEPAGE = "https://golang.org/"
LICENSE = "BSD-3-Clause"

SRC_URI_PREFIX = "http://golang.org/dl/"
GO_BOOTSTRAP_VERSION="1.4.3"
GO_CROSS_CGO_ENABLED?="0"
GOROOT_BOOTSTRAP="${STAGING_LIBDIR_NATIVE}/go-bootstrap-native-${GO_BOOTSTRAP_VERSION}"

S = "${WORKDIR}/go-${PV}/go"

setup_go_arch() {
  export GOHOSTOS="linux"
  export GOOS="linux"

  translate_arch() {
    local ARCH
    case "$1" in
      x86_64)
        ARCH=amd64
        ;;
      i586|i686)
        ARCH=386
        ;;
      arm)
        ARCH=arm
        ;;
      aarch64)
        # ARM64 is invalid for Go 1.4
        ARCH=arm64
        ;;
    esac

    echo "${ARCH}"
  }

  GOARCH=`translate_arch "${TARGET_ARCH}"`
  export GOARCH

  case "${GOARCH}" in
    arm)
      case "${TUNE_PKGARCH}" in
        cortexa*)
          export GOARM=7
          ;;
      esac
    ;;
  esac

  GOHOSTARCH=`translate_arch "${BUILD_ARCH}"`
  export GOHOSTARCH
}

setup_cgo_gcc_wrapper() {
  # Is there a bug in the cross-compiler support for CGO? Can't get it
  # to work without this wrapper
  for t in gcc g++ ; do
    cat > ${WORKDIR}/${TARGET_PREFIX}${t} <<EOT
#!/bin/sh
exec ${TARGET_PREFIX}${t} ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET} "\$@"
EOT
    chmod +x ${WORKDIR}/${TARGET_PREFIX}${t}
  done
}

go_compile() {
    export GOROOT_BOOTSTRAP="${GOROOT_BOOTSTRAP}"

    setup_go_arch

    setup_cgo_gcc_wrapper

    ## TODO: consider setting GO_EXTLINK_ENABLED
    export CGO_ENABLED="${GO_CROSS_CGO_ENABLED}"
    export CC=${BUILD_CC}
    export CC_FOR_TARGET="${WORKDIR}/${TARGET_PREFIX}gcc"
    export CXX_FOR_TARGET="${WORKDIR}/${TARGET_PREFIX}g++"
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
