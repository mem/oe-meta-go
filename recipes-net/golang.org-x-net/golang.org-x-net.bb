DESCRIPTION = "Go supplementary libraries"
SECTION = "net"
HOMEPAGE = "https://godoc.org/golang.org/x"
LICENSE = "BSD-3-Clause"

LIC_FILES_CHKSUM = "\
	file://net/LICENSE;md5=5d4950ecb7b26d2c5e4e7b4e0dd74707 \
	file://crypto/LICENSE;md5=5d4950ecb7b26d2c5e4e7b4e0dd74707 \
	file://text/LICENSE;md5=5d4950ecb7b26d2c5e4e7b4e0dd74707 \
	"

GO_IMPORT = "golang.org/x"

PROVIDES += "golang.org-x-crypto"
PROVIDES += "golang.org-x-text"

inherit go

SRC_URI = "\
	git://github.com/golang/net.git;protocol=https;name=net;destsuffix=git/net \
	git://github.com/golang/crypto.git;protocol=https;name=crypto;destsuffix=git/crypto \
	git://github.com/golang/text.git;protocol=https;name=text;destsuffix=git/text \
	"

SRCREV_net = "07b51741c1d6423d4a6abab1c49940ec09cb1aaf"
SRCREV_crypto = "a548aac93ed489257b9d959b40fe1e8c1e20778c"
SRCREV_text = "d69c40b4be55797923cec7457fac7a244d91a9b6"

S = "${WORKDIR}/git"
