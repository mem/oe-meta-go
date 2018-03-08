require go.inc

DEPENDS = "go-bootstrap-native"

SSTATE_SCAN_CMD = "true"

SRC_URI = "${SRC_URI_PREFIX}/go${PV}.src.tar.gz;subdir=go-${PV}"
SRC_URI[md5sum] = "07cbb9d0091b846c6aea40bf5bc0cea7"
SRC_URI[sha256sum] = "f3de49289405fda5fd1483a8fe6bd2fa5469e005fd567df64485c4fa000c7f24"

LIC_FILES_CHKSUM = "file://${WORKDIR}/go-${PV}/go/LICENSE;md5=5d4950ecb7b26d2c5e4e7b4e0dd74707"
