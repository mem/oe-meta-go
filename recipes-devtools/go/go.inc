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
  sdkpathnative=${7:-${SDKPATHNATIVE}}
  bindir=${5:-${bindir_nativesdk}}

  # Is there a bug in the cross-compiler support for CGO? Can't get it
  # to work without this wrapper
  for t in gcc g++ ; do
    cat > ${WORKDIR}/${TARGET_PREFIX}${t} <<EOT
#!/bin/sh
exec ${STAGING_BINDIR_TOOLCHAIN}/${TARGET_PREFIX}${t} ${TARGET_CC_ARCH} --sysroot=${STAGING_DIR_TARGET} "\$@"
EOT
    chmod +x ${WORKDIR}/${TARGET_PREFIX}${t}
  done
}
