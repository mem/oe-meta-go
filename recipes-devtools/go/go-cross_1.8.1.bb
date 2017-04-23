require go-cross.inc
require go_${PV}.inc

# Go binaries are not understood by the strip tool.
INHIBIT_SYSROOT_STRIP = "1"

do_compile() {
  go_compile
}

do_install() {
  go_install
}

## TODO: implement do_clean() and ensure we actually do rebuild super cleanly
