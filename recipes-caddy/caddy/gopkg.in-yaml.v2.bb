DESCRIPTION = "gopkg.in/yaml.v2"

GO_IMPORT = "gopkg.in/yaml.v2"

inherit go

SRC_URI = "git://gopkg.in/yaml.v2;protocol=https"
SRCREV = "${AUTOREV}"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6964839e54f4fefcdae13f22b92d0fbb"
S = "${WORKDIR}/git"
