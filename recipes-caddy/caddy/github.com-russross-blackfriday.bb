DESCRIPTION = "github.com/russross/blackfriday"

GO_IMPORT = "github.com/russross/blackfriday"

inherit go

SRC_URI = "git://github.com/russross/blackfriday;protocol=https"
SRCREV = "${AUTOREV}"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=ecf8a8a60560c35a862a4a545f2db1b3"
S = "${WORKDIR}/git"

DEPENDS += "github.com-shurcool-sanitized-anchor-name"
