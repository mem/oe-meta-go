def map_go_arch(a, d):
    import re

    if   re.match('^x86.64$', a):  return 'amd64'
    elif re.match('^i.86$', a):    return '386'
    elif re.match('^arm$', a):     return 'arm'
    elif re.match('^aarch64$', a): return 'arm64'
    else:
        bb.error("cannot map '%s' to a Go architecture" % a)

def map_go_arm(f, d):
    values = set(f.split())
    if   'armv7' in values: return '7'
    elif 'armv6' in values: return '6'
    elif 'armv5' in values: return '5'
    else: return ''

def map_go_386(f, d):
    values = set(f.split())
    if   'pentium4' in values: return 'sse2'
    elif 'i586'     in values: return '387'
    else: return ''

export GOOS = "linux"
export GOARCH = "${@map_go_arch(d.getVar('TARGET_ARCH', True), d)}"
export GOARM = "${@map_go_arm(d.getVar('TUNE_FEATURES', True), d)}"
export GO386 = "${@map_go_386(d.getVar('TUNE_FEATURES', True), d)}"

GO_FLAGS ?= ""
GO_GCFLAGS ?= ""
GO_ASMFLAGS ?= ""
GO_LDFLAGS ?= ""
export CGO_ENABLED = "1"
export CGO_CFLAGS = "${CFLAGS}"
export CGO_CPPFLAGS = "${CPPFLAGS}"
export CGO_CXXFLAGS = "${CXXFLAGS}"
export CGO_LDFLAGS = "${LDFLAGS}"

DEPENDS += "go-native"

# go binaries don't use GNU_HASH. Known, disable "QA Issue: No GNU_HASH in the elf binary: ..." warnings.
INSANE_SKIP_${PN} += "ldflags"

PACKAGE_ARCH_${PN}-dev = "all"
FILES_${PN}-dev += "${GOSRC_FINAL}/${GO_IMPORT}"
FILES_${PN}-staticdev += "${GOPKG_FINAL}/${GO_IMPORT}*"

GO ?= "go"

GO_INSTALL ?= "${GO_IMPORT}/..."

B = "${WORKDIR}/gopath"
export GOPATH = "${B}:${STAGING_LIBDIR}/go"

# The "real" GOROOT is embedded in the built go binary
GOROOT_FINAL = "${libdir}/go"
GOROOT_FINAL_class-native = "${STAGING_LIBDIR_NATIVE}/go"

GOPKG_FINAL = "${GOROOT_FINAL}/pkg/${GOOS}_${GOARCH}"
GOSRC_FINAL = "${GOROOT_FINAL}/src"

do_go_compile() {
	go env

	install -d ${B}/src/${GO_IMPORT}
	tar -C ${S} -cf - . | tar -C ${B}/src/${GO_IMPORT} -xpf -

	if [ -n "${GO_INSTALL}" ]; then
	   # `godep` needs to be run from dir with ./vendor; `go` doesn't care
	   ( cd ${B}/src/${GO_IMPORT} && ${GO} install -x -asmflags "${GO_ASMFLAGS}" -gcflags "${GO_GCFLAGS}" -ldflags "${GO_LDFLAGS}" ${GO_FLAGS} ${GO_INSTALL} )
	fi
}

do_go_install() {
	rm -rf ${WORKDIR}/staging
	install -d ${WORKDIR}/staging${GOROOT_FINAL} ${D}${GOROOT_FINAL}
	tar -C ${B} -cf - src pkg | tar -C ${WORKDIR}/staging${GOROOT_FINAL} -xpf -

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

	chown -R root:root "${D}${GOROOT_FINAL}"

	if test -e "${B}/bin" ; then
		install -d -m 0755 "${D}${bindir}"
		find "${B}/bin" ! -type d -print0 | xargs -r0 install -m 0755 --target-directory="${D}${bindir}"
	fi
}

do_compile() {
	do_go_compile
}

do_install() {
	do_go_install
}
