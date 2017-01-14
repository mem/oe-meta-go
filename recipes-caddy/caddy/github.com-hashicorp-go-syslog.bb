DESCRIPTION = "github.com/hashicorp/go-syslog"

GO_IMPORT = "github.com/hashicorp/go-syslog"

inherit go

SRC_URI = "git://github.com/hashicorp/go-syslog;protocol=https"
SRCREV = "${AUTOREV}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cb04212e101fbbd028f325e04ad45778"
S = "${WORKDIR}/git"
