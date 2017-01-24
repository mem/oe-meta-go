require go_${PV}.inc

inherit native

CGO_ENABLED = "0"
GOROOT_FINAL="${SYSROOT}${nonarch_libdir}/${PN}-${PV}"

do_compile() {
  bash -x ./make.bash

  # Log the resulting environment
  "${S}/bin/go" env
}
