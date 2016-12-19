require go.inc

DEPENDS = "go-bootstrap-native"

SRC_URI = "${SRC_URI_PREFIX}/go${PV}.src.tar.gz;subdir=go-${PV}"
SRC_URI[md5sum] = "49c1076428a5d3b5ad7ac65233fcca2f"
SRC_URI[sha256sum] = "4c189111e9ba651a2bb3ee868aa881fab36b2f2da3409e80885ca758a6b614cc"

SRC_URI += "\
        file://fix-cross-compilation.patch \
        "

LIC_FILES_CHKSUM = "file://${WORKDIR}/go-${PV}/go/LICENSE;md5=5d4950ecb7b26d2c5e4e7b4e0dd74707"
