SUMMARY = "U-Boot bootloader with support for Toradex Computer on Modules"
HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"

require recipes-bsp/u-boot/u-boot.inc

#DEPENDS += "bc-native dtc-native flex-native bison-native"
DEPENDS += "bc-native dtc-native bison-native"
PV = "2020.07+git${SRCPV}"

SRCREV = "51b7d2f591d32916a3addab0193d07bbacb34aad"
SRCBRANCH = "toradex_2020.07"
SRC_URI = " \
    git://git.toradex.com/u-boot-toradex.git;branch=${SRCBRANCH} \
    file://fw_env.config \
"
PV = "2020.07+git${SRCPV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "(mx6|mx7|vf|use-mainline-bsp)"

PROVIDES += "u-boot"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
do_configure[cleandirs] = "${B}"

inherit fsl-u-boot-localversion

PADDING_DIR = "${B}"

UBOOT_INITIAL_ENV = "u-boot-initial-env"

nand_padding () {
    # pad the end of U-Boot with 0x00 up to the the end of the CSF area
    #PAD_END=$(echo -n "0x"; od -X  -j 0x24 -N 4 u-boot.imx | sed -e '/................/!d' -e 's/........\(.*\)/\1/')
    #PAD_END=$(( $PAD_END - 0x400 ))
    #objcopy -I binary -O binary --pad-to $PAD_END u-boot.imx u-boot.imx.zero-padded
    # assume that the above never need more than 10k of padding and skip the
    # shell magic to get a correct size.
    dd bs=10k count=1 if=/dev/zero | cat ${PADDING_DIR}/u-boot.imx - > ${PADDING_DIR}/u-boot.imx.zero-padded

    # U-Boot is flashed 1k into a NAND block, create a binary which prepends
    # U-boot with 1k of zeros to ease flashing
    dd bs=1024 count=1 if=/dev/zero | cat - ${PADDING_DIR}/u-boot.imx.zero-padded > ${PADDING_DIR}/u-boot-nand.imx
}

do_compile_append_colibri-imx6ull () {
    nand_padding
}

do_compile_append_colibri-imx7 () {
    nand_padding
}

do_compile_append_colibri-imx7-nand () {
    nand_padding
}

do_compile_append_colibri-vf () {
    nand_padding
}
