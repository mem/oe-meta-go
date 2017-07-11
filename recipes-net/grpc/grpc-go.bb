DESCRIPTION = "The Go language implementation of gRPC. HTTP/2 based RPC"
SECTION = "net"
HOMEPAGE = "https://github.com/grpc/grpc-go"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a4bad33881612090c6035d8393175996"
S = "${WORKDIR}/git"

GO_IMPORT = "google.golang.org/grpc"

DEPENDS = "\
	github.com-golang-glog \
	github.com-golang-protobuf \
	golang.org-x-net \
	golang.org-x-oauth2 \
	"

GO_INSTALL = "\
	${GO_IMPORT} \
	${GO_IMPORT}/codes/... \
	${GO_IMPORT}/credentials/... \
	${GO_IMPORT}/grpclog/... \
	${GO_IMPORT}/internal/... \
	${GO_IMPORT}/metadata/... \
	${GO_IMPORT}/naming/... \
	${GO_IMPORT}/peer/... \
	${GO_IMPORT}/transport/... \
"

inherit go

SRC_URI = "git://github.com/grpc/grpc-go.git;protocol=https"
SRCREV = "c2781963b3af261a37e0f14fdcb7c1fa13259e1f"
