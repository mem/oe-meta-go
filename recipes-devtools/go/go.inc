DESCRIPTION = "Go is an open source programming language that makes it easy to build simple, reliable, and efficient software."
HOMEPAGE = "https://golang.org/"
LICENSE = "BSD-3-Clause"

SRC_URI_PREFIX = "http://golang.org/dl/"
GO_BOOTSTRAP_VERSION="1.4"
GO_BOOTSTRAP_TAG="1_4"
GO_BOOTSTRAP_SOURCE="go${GO_BOOTSTRAP_VERSION}.src.tar.gz"
GO_BOOTSTRAP_URI = "${SRC_URI_PREFIX}/${GO_BOOTSTRAP_SOURCE}"
SRC_URI = "${GO_BOOTSTRAP_URI};name=go_${GO_BOOTSTRAP_TAG};subdir=go-${GO_BOOTSTRAP_VERSION}"
SRC_URI[go_1_4.md5sum] = "c0d732ef7181f98db4b93fbce4eef5a2"
SRC_URI[go_1_4.sha256sum] = "3ae9f67e45a5ca7004b28808da8b1367d328a371d641ddbe636c0fb0ae0ffdae"

LIC_FILES_CHKSUM = "file://${WORKDIR}/go-${GO_BOOTSTRAP_VERSION}/go/LICENSE;md5=591778525c869cdde0ab5a1bf283cd81"

INHIBIT_SYSROOT_STRIP = "1"

setup_go_arch() {
  export GOHOSTOS="linux"
  export GOOS="linux"

  case "${TARGET_ARCH}" in
    x86_64)
      GOARCH=amd64
      ;;
    i586)
      GOARCH=386
      ;;
    arm)
      GOARCH=${TARGET_ARCH}
      case "${TUNE_PKGARCH}" in
        cortexa*)
          export GOARM=7
          ;;
      esac
      ;;
    aarch64)
      # ARM64 is invalid for Go 1.4
      GOARCH=arm64
      ;;
    *)
      GOARCH="${TARGET_ARCH}"
      ;;
  esac
  export GOARCH
}
