FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
require recipes-kernel/linux/linux-imx.inc

SUMMARY = "Linux kernel for Toradex Colibri VFxx Computer on Modules"

SRC_URI = "git://git.toradex.com/linux-toradex.git;protocol=git;branch=${SRCBRANCH}"

KBUILD_DEFCONFIG ?= "colibri_vf_defconfig"

KERNEL_MODULE_AUTOLOAD += "${@bb.utils.contains('COMBINED_FEATURES', 'usbgadget', ' libcomposite', '',d)}"

LOCALVERSION = "-2.8.7"
PV_append = "+git${SRCPV}"

LINUX_VERSION = "4.4.238"

SRCBRANCH = "toradex_vf_4.4"
SRCREV = "566575659eec6b0a72de46dd1c3ae8fc221a90a5"

DEPENDS += "lzop-native bc-native"
COMPATIBLE_MACHINE = "(vf)"
