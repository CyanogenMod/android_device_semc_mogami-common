#
# Copyright (C) 2011 The CyanogenMod Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
-include device/semc/msm7x30-common/msm7x30.mk


# Init files  and configs

PRODUCT_COPY_FILES += \
    device/semc/mogami-common/prebuilt/ueventd.semc.rc:root/ueventd.semc.rc \
    device/semc/mogami-common/prebuilt/init.semc.rc:root/init.semc.rc \
    device/semc/mogami-common/prebuilt/init.semc.usb.rc:root/init.semc.usb.rc

#    device/semc/mogami-common/prebuilt/init.semc.usb.rc:root/init.semc.usb.rc \
#    device/semc/mogami-common/prebuilt/wpa_supplicant.conf:system/etc/wifi/wpa_supplicant.conf

#WIFI modules and configs
PRODUCT_COPY_FILES += \
    device/semc/mogami-common/wl12xx/modules/compat.ko:root/modules/compat.ko \
    device/semc/mogami-common/wl12xx/modules/compat_firmware_class.ko:root/modules/compat_firmware_class.ko \
    device/semc/mogami-common/wl12xx/modules/cfg80211.ko:root/modules/cfg80211.ko \
    device/semc/mogami-common/wl12xx/modules/mac80211.ko:root/modules/mac80211.ko \
    device/semc/mogami-common/wl12xx/modules/wl12xx.ko:root/modules/wl12xx.ko \
    device/semc/mogami-common/wl12xx/modules/wl12xx_sdio.ko:root/modules/wl12xx_sdio.ko

#WIFI firmware
PRODUCT_COPY_FILES += \
    device/semc/mogami-common/wl12xx/firmware/wl1271-fw-multirole-roc.bin:system/etc/firmware/wl1271-fw-multirole-roc.bin \
    device/semc/mogami-common/wl12xx/firmware/wl1271-nvs.bin:system/etc/firmware/wl1271-nvs.bin

#PRODUCT_PACKAGES += \
#    hostap
