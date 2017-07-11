DESCRIPTION = "Go support for Google's protocol buffers"
SECTION = "net"
HOMEPAGE = "https://github.com/golang/protobuf"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=14db3a56c3796a940ba32948a15f97d0"

DEPENDS = "golang.org-x-net"

GO_IMPORT = "github.com/golang/protobuf"

SRC_URI = "\
	git://github.com/golang/protobuf.git;protocol=https;name=protobuf \
	"

SRCREV_protobuf = "7390af9dcd3c33042ebaf2474a1724a83cf1a7e6"

inherit go

do_install_append_class-target() {
	find "${D}${GOROOT_FINAL}" \( -name \*.sh -o -name \*.bash \) -delete
}
