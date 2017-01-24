DESCRIPTION = "Go is an open source programming language that makes it easy to build simple, reliable, and efficient software."
HOMEPAGE = "https://golang.org/"
LICENSE = "BSD-3-Clause"

SRC_URI_PREFIX = "http://golang.org/dl/"
GO_BOOTSTRAP_VERSION="1.4.3"
GO_CROSS_CGO_ENABLED?="0"
GOROOT_BOOTSTRAP="${STAGING_LIBDIR_NATIVE}/go-bootstrap-native-${GO_BOOTSTRAP_VERSION}"

S = "${WORKDIR}/go-${PV}/go"
