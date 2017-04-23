require go.inc

DEPENDS = "go-bootstrap-native"

SSTATE_SCAN_CMD = "true"

SRC_URI = "${SRC_URI_PREFIX}/go${PV}.src.tar.gz;subdir=go-${PV}"
SRC_URI[md5sum] = "409dd21e7347dd1ea9efe64a700073cc"
SRC_URI[sha256sum] = "33daf4c03f86120fdfdc66bddf6bfff4661c7ca11c5da473e537f4d69b470e57"

SRC_URI += "\
        file://fix-cross-compilation.patch \
        "

LIC_FILES_CHKSUM = "file://${WORKDIR}/go-${PV}/go/LICENSE;md5=5d4950ecb7b26d2c5e4e7b4e0dd74707"
