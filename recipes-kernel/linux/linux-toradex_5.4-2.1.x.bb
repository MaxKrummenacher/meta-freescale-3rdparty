FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

require recipes-kernel/linux/linux-imx.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

DEPENDS += "lzop-native bc-native"

SUMMARY = "Linux kernel for Toradex Freescale i.MX based modules"

SRC_URI = "git://git.toradex.com/linux-toradex.git;protocol=git;branch=${SRCBRANCH}"

# Load USB functions configurable through configfs (CONFIG_USB_CONFIGFS)
KERNEL_MODULE_AUTOLOAD += "${@bb.utils.contains('COMBINED_FEATURES', 'usbgadget', ' libcomposite', '',d)}"

PV_append = "+git${SRCPV}"

LINUX_VERSION = "5.4.47"

SRCREV = "9e7307657fc1b86fc652abd32676b516fc28fe23"
SRCBRANCH = "toradex_5.4-2.1.x-imx"

# Deploy symbols to allow for device tree overlays
EXTRA_OEMAKE += "DTC_FLAGS=-@ "

KBUILD_DEFCONFIG_apalis-imx6 ?= "apalis_imx6_defconfig"
KBUILD_DEFCONFIG_colibri-imx6 ?= "colibri_imx6_defconfig"
KBUILD_DEFCONFIG_colibri-imx6ull ?= "colibri-imx6ull_defconfig"
KBUILD_DEFCONFIG_mx7 ?= "colibri_imx7_defconfig"
KBUILD_DEFCONFIG_mx8 ?= "defconfig"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
