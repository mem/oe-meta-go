DESCRIPTION = "Packages used to access Google Cloud Services"
SECTION = "net"
HOMEPAGE = "http://cloud.google.com/go"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS += "golang.org-x-text"

GO_IMPORT = "cloud.google.com/go"
GO_INSTALL = "\
	cloud.google.com/go/compute/metadata \
	cloud.google.com/go/internal \
"

inherit go

SRC_URI = "git://code.googlesource.com/gocloud;protocol=https"

SRCREV = "05253f6a829103296c351b643f6815aedd81a3fb"
S = "${WORKDIR}/git"
