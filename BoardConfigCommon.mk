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

BOARD_WPA_SUPPLICANT_DRIVER      := NL80211
WPA_SUPPLICANT_VERSION           := VER_0_8_X
BOARD_WPA_SUPPLICANT_PRIVATE_LIB := lib_driver_cmd_wl12xx
BOARD_HOSTAPD_DRIVER             := NL80211
BOARD_HOSTAPD_PRIVATE_LIB        := lib_driver_cmd_wl12xx
BOARD_WLAN_DEVICE                := wl12xx_mac80211
BOARD_SOFTAP_DEVICE              := wl12xx_mac80211
USES_TI_MAC80211 := true

COMMON_GLOBAL_CFLAGS += -DUSES_TI_MAC80211

BOARD_HAVE_BLUETOOTH_CUSTOM_HCIATTACH := true
TARGET_CUSTOM_BLUEDROID := ../../../device/semc/mogami-common/bluedroid/bluetooth.c

BOARD_HAVE_QCOM_FM := true
BOARD_HAVE_FM_RADIO_TI := true
COMMON_GLOBAL_CFLAGS += -DQCOM_FM_ENABLED -DHAVE_SEMC_FM_RADIO

BOARD_CPU_COLOR_CONVERT:= true

-include device/semc/msm7x30-common/Android.mk
