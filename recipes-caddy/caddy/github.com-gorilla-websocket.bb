DESCRIPTION = "github.com/gorilla/websocket"

GO_IMPORT = "github.com/gorilla/websocket"

inherit go

SRC_URI = "git://github.com/gorilla/websocket;protocol=https"
SRCREV = "${AUTOREV}"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c007b54a1743d596f46b2748d9f8c044"
S = "${WORKDIR}/git"
