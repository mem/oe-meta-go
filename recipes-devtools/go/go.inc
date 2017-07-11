DESCRIPTION = "Go is an open source programming language that makes it easy to build simple, reliable, and efficient software."
HOMEPAGE = "https://golang.org/"
LICENSE = "BSD-3-Clause"

SRC_URI_PREFIX = "http://golang.org/dl/"
GO_BOOTSTRAP_VERSION="1.4.3"
GO_CROSS_CGO_ENABLED?="0"
export GOROOT_BOOTSTRAP="${STAGING_LIBDIR_NATIVE}/go-bootstrap-native-${GO_BOOTSTRAP_VERSION}"

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
    if   'armv7a' in values: return '7'
    elif 'armv6'  in values: return '6'
    elif 'armv5'  in values: return '5'
    else: return ''

def map_go_386(f, d):
    values = set(f.split())
    if   'pentium4' in values: return 'sse2'
    elif 'i586'     in values: return '387'
    else: return ''

export GOOS = "linux"
export GOHOSTOS = "linux"
export GOARCH = "${@map_go_arch(d.getVar('TARGET_ARCH', True), d)}"
export GOHOSTARCH = "${@map_go_arch(d.getVar('BUILD_ARCH', True), d)}"
export GOARM = "${@map_go_arm(d.getVar('TUNE_FEATURES', True), d)}"
export GO386 = "${@map_go_386(d.getVar('TUNE_FEATURES', True), d)}"

## TODO: consider setting GO_EXTLINK_ENABLED
export CGO_ENABLED = "1"
export CC_FOR_TARGET = "${CCACHE}${HOST_PREFIX}gcc ${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS}"
export CXX_FOR_TARGET = "${CCACHE}${HOST_PREFIX}g++ ${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS}"
export CC = "${BUILD_CC}"
export FC = "${BUILD_FC}"

S = "${WORKDIR}/go"
B = "${S}/src"

# go binaries don't use GNU_HASH. Known, disable "QA Issue: No GNU_HASH in the elf binary: ..." warnings.
INSANE_SKIP_${PN} += "ldflags"

# Ignore prebuilt .a libraries shipped with compiler
INSANE_SKIP_${PN} += "staticdev"

RDEPENDS_${PN} += "bash"
