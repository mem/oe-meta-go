DESCRIPTION = "github.com/dustin/go-humanize"

GO_IMPORT = "github.com/dustin/go-humanize"

inherit go

SRC_URI = "git://github.com/dustin/go-humanize;protocol=https"
SRCREV = "${AUTOREV}"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8c6127b79304a5e0a5756d03c7a58766"
S = "${WORKDIR}/git"
