def map_go_arch(a, d):
    import re

    if   re.match('^x86.64$', a):  return 'amd64'
    elif re.match('^i.86$', a):    return '386'
    elif re.match('^arm$', a):     return 'arm'
    elif re.match('^aarch64$', a): return 'arm64'
    else:
        bb.error("cannot map '%s' to a Go architecture" % a)

export GOOS = "linux"
export GOARCH = "${@map_go_arch(d.getVar('TARGET_ARCH', True), d)}"
export GOROOT = "${STAGING_LIBDIR_NATIVE}/${TARGET_SYS}/go"
export GOROOT_FINAL = "${libdir}/${TARGET_SYS}/go"
export GOBIN_FINAL = "${GOROOT_FINAL}/bin/${GOOS}_${GOARCH}"
export GOPKG_FINAL = "${GOROOT_FINAL}/pkg/${GOOS}_${GOARCH}"
export GOSRC_FINAL = "${GOROOT_FINAL}/src"
export GO_GCFLAGS = "${TARGET_CFLAGS}"
export GO_LDFLAGS = "${TARGET_LDFLAGS}"
export CGO_CFLAGS = "${TARGET_CC_ARCH}${TOOLCHAIN_OPTIONS} ${TARGET_CFLAGS}"
export CGO_CPPFLAGS = "${TARGET_CPPFLAGS}"
export CGO_CXXFLAGS = "${TARGET_CC_ARCH}${TOOLCHAIN_OPTIONS} ${TARGET_CXXFLAGS}"
export CGO_LDFLAGS = "${TARGET_CC_ARCH}${TOOLCHAIN_OPTIONS} ${TARGET_LDFLAGS}"

DEPENDS += "go-cross"

INHIBIT_PACKAGE_STRIP = "1"

FILES_${PN}-staticdev += "${GOSRC_FINAL}/${GO_IMPORT}"
FILES_${PN}-staticdev += "${GOPKG_FINAL}/${GO_IMPORT}*"

do_go_compile() {
	GOPATH=${S}:${STAGING_LIBDIR}/${TARGET_SYS}/go go env
	GOPATH=${S}:${STAGING_LIBDIR}/${TARGET_SYS}/go go install -v ${GO_IMPORT}/...
}

do_go_install() {
	rm -rf ${WORKDIR}/staging
	install -d ${WORKDIR}/staging${GOROOT_FINAL} ${D}${GOROOT_FINAL}
	tar -C ${S} -cf - . | tar -C ${WORKDIR}/staging${GOROOT_FINAL} -xpvf -

	find ${WORKDIR}/staging${GOROOT_FINAL} \( \
		-name \*.indirectionsymlink -o \
		-name .git\* -o                \
		-name .hg -o                   \
		-name .svn -o                  \
		-name .pc\* -o                 \
		-name patches\*                \
		\) -print0 | \
	xargs -r0 rm -rf

	tar -C ${WORKDIR}/staging${GOROOT_FINAL} -cf - . | \
	tar -C ${D}${GOROOT_FINAL} -xpvf -
}

do_compile() {
	do_go_compile
}

do_install() {
	do_go_install
}
