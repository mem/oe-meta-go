DESCRIPTION = "github.com/codegangsta/cli"

GO_IMPORT = "github.com/codegangsta/cli"

inherit go

SRC_URI = "git://github.com/codegangsta/cli;protocol=https"
SRCREV = "${AUTOREV}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c542707ca9fc0b7802407ba62310bd8f"
S = "${WORKDIR}/git"

DEPENDS += "github.com-burntsushi-toml"

GO_INSTALL = "${GO_IMPORT}"
