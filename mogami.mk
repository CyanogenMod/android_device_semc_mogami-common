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

DEVICE_PACKAGE_OVERLAYS += device/semc/mogami-common/overlay

# Init files  and configs

PRODUCT_COPY_FILES += \
    device/semc/mogami-common/prebuilt/init.semc.rc:root/init.semc.rc

#WIFI modules and configs
PRODUCT_COPY_FILES += \
    device/semc/mogami-common/prebuilt/wifi_load.sh:system/etc/init.d/10wifiload \
    device/semc/mogami-common/wl12xx/modules/compat.ko:system/lib/modules/compat.ko \
    device/semc/mogami-common/wl12xx/modules/compat_firmware_class.ko:system/lib/modules/compat_firmware_class.ko \
    device/semc/mogami-common/wl12xx/modules/cfg80211.ko:system/lib/modules/cfg80211.ko \
    device/semc/mogami-common/wl12xx/modules/mac80211.ko:system/lib/modules/mac80211.ko \
    device/semc/mogami-common/wl12xx/modules/wl12xx.ko:system/lib/modules/wl12xx.ko \
    device/semc/mogami-common/wl12xx/modules/wl12xx_sdio.ko:system/lib/modules/wl12xx_sdio.ko

PRODUCT_PACKAGES += \
    hostapd_cli \
    hostapd

PRODUCT_PACKAGES += \
    Fmapplication \
    fmapp \
    libfm_stack \
    fmreceiverif \
    com.ti.fm.fmreceiverif.xml \
    FmRxService \
    libfmrx

PRODUCT_PROPERTY_OVERRIDES += \
    wifi.interface=wlan0 \
    ro.telephony.default_network=0
