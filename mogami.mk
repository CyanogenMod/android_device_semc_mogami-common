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

$(call inherit-product-if-exists, vendor/semc/mogami-common/mogami-vendor-blobs.mk)

DEVICE_PACKAGE_OVERLAYS += device/semc/mogami-common/overlay

# Init files  and configs

PRODUCT_COPY_FILES += \
    device/semc/mogami-common/prebuilt/init.semc.rc:root/init.semc.rc

#WIFI modules and configs
PRODUCT_COPY_FILES += \
    device/semc/mogami-common/prebuilt/hostapd.conf:system/etc/wifi/hostapd.conf \
    device/semc/mogami-common/prebuilt/wifiload:system/bin/wifiload

PRODUCT_PACKAGES += \
    wpa_supplicant.conf \
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
    wifi.softap.interface=wlan0 \
    wifi.softapconcurrent.interface=wlan0 \
    ro.telephony.ril_class=SemcRIL \
    ro.telephony.default_network=0
