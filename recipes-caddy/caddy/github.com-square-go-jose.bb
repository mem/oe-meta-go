DESCRIPTION = "github.com/square/go-jose"

GO_IMPORT = "github.com/square/go-jose"

inherit go

SRC_URI = "git://github.com/square/go-jose;protocol=https"
SRCREV = "${AUTOREV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"
S = "${WORKDIR}/git"

DEPENDS += "github.com-codegangsta-cli"

GO_INSTALL = "${GO_IMPORT}"
