DESCRIPTION = "github.com/shurcooL/sanitized_anchor_name"

GO_IMPORT = "github.com/shurcooL/sanitized_anchor_name"

inherit go

SRC_URI = "git://github.com/shurcooL/sanitized_anchor_name;protocol=https"
SRCREV = "${AUTOREV}"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=bee2c3aa5bd0f265ffbd193eb18ca30d"
S = "${WORKDIR}/git"
