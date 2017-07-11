DESCRIPTION = "github.com/jimstudt/http-authentication"

GO_IMPORT = "github.com/jimstudt/http-authentication"

inherit go

SRC_URI = "git://github.com/jimstudt/http-authentication;protocol=https"
SRCREV = "${AUTOREV}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=61e7f1bfbf294711fc438f57de71ccdd"
S = "${WORKDIR}/git"
