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

PRODUCT_PACKAGES += \
    hostap

# Init files
PRODUCT_COPY_FILES += \
    device/semc/mogami-common/prebuilt/ueventd.mogami.rc:root/ueventd.mogami.rc \
    device/semc/mogami-common/prebuilt/init.semc.rc:root/init.semc.rc

#WIFI modules and configs
PRODUCT_COPY_FILES += \
    device/semc/mogami-common/prebuilt/tiap_loader.sh:system/bin/tiap_loader.sh \
    device/semc/mogami-common/prebuilt/wpa_supplicant.conf:system/etc/wifi/wpa_supplicant.conf \
    device/semc/mogami-common/prebuilt/hostapd.conf:system/etc/wifi/softap/hostapd.conf \
    device/semc/mogami-common/modules/sdio.ko:root/modules/sdio.ko \
    device/semc/mogami-common/modules/tiap_drv.ko:root/modules/tiap_drv.ko \
    device/semc/mogami-common/modules/tiwlan_drv.ko:root/modules/tiwlan_drv.ko
