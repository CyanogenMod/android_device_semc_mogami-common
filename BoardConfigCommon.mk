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
-include device/semc/msm7x30-common/BoardConfigCommon.mk

TARGET_BOOTLOADER_BOARD_NAME := mogami

BOARD_WPA_SUPPLICANT_DRIVER := CUSTOM
BOARD_WLAN_DEVICE := wl1271
BOARD_SOFTAP_DEVICE := wl1271
WPA_SUPPLICANT_VERSION := VER_0_6_X
WIFI_DRIVER_MODULE_PATH := /system/lib/modules/tiwlan_drv.ko
WIFI_DRIVER_MODULE_NAME := tiwlan_drv
WIFI_FIRMWARE_LOADER := wlan_loader
WIFI_EXT_MODULE_PATH := /system/lib/modules/sdio.ko
WIFI_EXT_MODULE_NAME := sdio

BOARD_CUSTOM_BLUEDROID := ../../../device/semc/mogami-common/bluedroid/bluetooth.c

BOARD_OVERLAY_FORMAT_YCrCb_420_SP := true
BOARD_CAMERA_USE_GETBUFFERINFO := true

-include device/semc/msm7x30-common/Android.mk
