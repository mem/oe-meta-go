require go_${PV}.inc

GOROOT_FINAL = "${libdir}/go"
GOROOT_FINAL_class-native = "${STAGING_LIBDIR_NATIVE}/go"
export GOROOT_FINAL

do_compile() {
  bash -x ./make.bash
}

go_install() {
  install -d "${D}${bindir}" "${D}${GOROOT_FINAL}"
  tar -C "${S}" -cf - bin lib src pkg |
  tar -C "${D}${GOROOT_FINAL}" -xf -
  rm -rf "${D}${GOROOT_FINAL}/pkg/bootstrap"

  mv "${D}${GOROOT_FINAL}/bin/"* "${D}${bindir}/"

  rm -f "${D}${GOROOT_FINAL}/src/"*.rc
}

do_install() {
  go_install
}

do_install_class-target() {
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

BBCLASSEXTEND = "native nativesdk"
