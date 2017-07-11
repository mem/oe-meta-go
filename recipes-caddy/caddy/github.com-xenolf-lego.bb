DESCRIPTION = "github.com/xenolf/lego"

GO_IMPORT = "github.com/xenolf/lego"

inherit go

SRC_URI = "git://github.com/xenolf/lego;protocol=https"
SRCREV = "${AUTOREV}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=bc6cc6b9955a86b318178c38ac271bba"
S = "${WORKDIR}/git"

GO_INSTALL = ""
