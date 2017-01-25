require go_${PV}.inc

#PN_class-native = "go-native"
#PN_class-cross = "go-cross-${TARGET_ARCH}"

GOROOT_FINAL="${libdir}/go"
GOROOT_FINAL_class-native="${STAGING_LIBDIR_NATIVE}/go"
export GOROOT_FINAL

# Go binaries are not understood by the strip tool.
INHIBIT_SYSROOT_STRIP = "1"

do_compile() {
  go_compile
}

do_install() {
  go_install
}

do_install_class-target() {
  setup_go_arch

  go_install

  if test "${GOHOSTOS}_${GOHOSTARCH}" != "${GOOS}_${GOARCH}" ; then
    mv "${D}${bindir}/${GOOS}_${GOARCH}/"* "${D}${bindir}/"
    rmdir "${D}${bindir}/${GOOS}_${GOARCH}"
    rm -rf "${D}${GOROOT_FINAL}/pkg/${GOHOSTOS}_${GOHOSTARCH}"
    rm -rf "${D}${GOROOT_FINAL}/pkg/tool/${GOHOSTOS}_${GOHOSTARCH}"
    # XXX: too aggressive?
    find "${D}" -name "*_${GOHOSTARCH}.syso" -delete
  fi

  rm -rf "${D}${GOROOT_FINAL}"/src/debug/*/testdata/

  chown -R root:root "${D}"
}

## TODO: implement do_clean() and ensure we actually do rebuild super cleanly

INSANE_SKIP_go = "staticdev"

BBCLASSEXTEND = "native"
