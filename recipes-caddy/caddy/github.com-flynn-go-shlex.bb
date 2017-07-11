DESCRIPTION = "github.com/flynn/go-shlex"

GO_IMPORT = "github.com/flynn/go-shlex"

inherit go

SRC_URI = "git://github.com/flynn/go-shlex;protocol=https"
SRCREV = "${AUTOREV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=3b83ef96387f14655fc854ddc3c6bd57"
S = "${WORKDIR}/git"
