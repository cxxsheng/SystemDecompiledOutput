package com.android.server.am;

/* loaded from: classes.dex */
public class SettingsToPropertiesMapper {
    private static final java.lang.String GLOBAL_SETTINGS_CATEGORY = "global_settings";
    public static final java.lang.String NAMESPACE_REBOOT_STAGING = "staged";
    public static final java.lang.String NAMESPACE_REBOOT_STAGING_DELIMITER = "*";
    private static final java.lang.String RESET_PERFORMED_PROPERTY = "device_config.reset_performed";
    private static final java.lang.String RESET_RECORD_FILE_PATH = "/data/server_configurable_flags/reset_flags";
    private static final java.lang.String SYSTEM_PROPERTY_INVALID_SUBSTRING = "..";
    private static final int SYSTEM_PROPERTY_MAX_LENGTH = 92;
    private static final java.lang.String SYSTEM_PROPERTY_PREFIX = "persist.device_config.";
    private static final java.lang.String SYSTEM_PROPERTY_VALID_CHARACTERS_REGEX = "^[\\w\\.\\-@:]*$";
    private static final java.lang.String TAG = "SettingsToPropertiesMapper";
    private final android.content.ContentResolver mContentResolver;
    private final java.lang.String[] mDeviceConfigAconfigScopes;
    private final java.lang.String[] mDeviceConfigScopes;
    private final java.lang.String[] mGlobalSettings;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String[] sGlobalSettings = {"native_flags_health_check_enabled"};
    private static final java.lang.String NAMESPACE_TETHERING_U_OR_LATER_NATIVE = "tethering_u_or_later_native";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String[] sDeviceConfigScopes = {"activity_manager_native_boot", "camera_native", "configuration", "connectivity", "edgetpu_native", "input_native_boot", "intelligence_content_suggestions", "lmkd_native", "media_native", "mglru_native", "netd_native", "nnapi_native", "profcollect_native_boot", "remote_key_provisioning_native", "runtime_native", "runtime_native_boot", "statsd_native", "statsd_native_boot", "storage_native_boot", "surface_flinger_native_boot", "swcodec_native", "vendor_system_native", "vendor_system_native_boot", "virtualization_framework_native", "window_manager_native_boot", "memory_safety_native_boot", "memory_safety_native", "hdmi_control", NAMESPACE_TETHERING_U_OR_LATER_NATIVE};

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String[] sDeviceConfigAconfigScopes = {"accessibility", "android_core_networking", "android_stylus", "aoc", "app_widgets", "arc_next", "avic", "bluetooth", "brownout_mitigation_audio", "build", "biometrics", "biometrics_framework", "biometrics_integration", "camera_hal", "camera_platform", "car_framework", "car_perception", "car_security", "car_telemetry", "codec_fwk", "companion", "content_protection", "context_hub", "core_experiments_team_internal", "core_graphics", "core_libraries", "dck_framework", "devoptions_settings", "game", "gpu", "haptics", "hardware_backed_security_mainline", "input", "lse_desktop_experience", "machine_learning", "mainline_modularization", "mainline_sdk", "make_pixel_haptics", "media_audio", "media_drm", "media_reliability", "media_tv", "media_solutions", "nfc", "pdf_viewer", "perfetto", "pixel_audio_android", "pixel_biometrics_face", "pixel_bluetooth", "pixel_connectivity_gps", "pixel_system_sw_video", "pixel_watch", "platform_security", "pmw", "power", "preload_safety", "privacy_infra_policy", "resource_manager", "responsible_apis", "rust", "safety_center", "sensors", "system_performance", "system_sw_touch", "system_sw_usb", "statsd", "test_suites", "text", "threadnetwork", "tv_system_ui", "usb", "vibrator", "virtualization", "virtual_devices", "wallet_integration", "wear_calling_messaging", "wear_connectivity", "wear_esim_carriers", "wear_frameworks", "wear_health_services", "wear_media", "wear_offload", "wear_security", "wear_system_health", "wear_systems", "wear_sysui", "window_surfaces", "windowing_frontend"};

    @com.android.internal.annotations.VisibleForTesting
    protected SettingsToPropertiesMapper(android.content.ContentResolver contentResolver, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3) {
        this.mContentResolver = contentResolver;
        this.mGlobalSettings = strArr;
        this.mDeviceConfigScopes = strArr2;
        this.mDeviceConfigAconfigScopes = strArr3;
    }

    @com.android.internal.annotations.VisibleForTesting
    void updatePropertiesFromSettings() {
        for (final java.lang.String str : this.mGlobalSettings) {
            android.net.Uri uriFor = android.provider.Settings.Global.getUriFor(str);
            final java.lang.String makePropertyName = makePropertyName(GLOBAL_SETTINGS_CATEGORY, str);
            if (uriFor == null) {
                log("setting uri is null for globalSetting " + str);
            } else if (makePropertyName == null) {
                log("invalid prop name for globalSetting " + str);
            } else {
                android.database.ContentObserver contentObserver = new android.database.ContentObserver(null) { // from class: com.android.server.am.SettingsToPropertiesMapper.1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        com.android.server.am.SettingsToPropertiesMapper.this.updatePropertyFromSetting(str, makePropertyName);
                    }
                };
                if (!isNativeFlagsResetPerformed()) {
                    updatePropertyFromSetting(str, makePropertyName);
                }
                this.mContentResolver.registerContentObserver(uriFor, false, contentObserver);
            }
        }
        for (java.lang.String str2 : this.mDeviceConfigScopes) {
            android.provider.DeviceConfig.addOnPropertiesChangedListener(str2, android.os.AsyncTask.THREAD_POOL_EXECUTOR, new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.am.SettingsToPropertiesMapper$$ExternalSyntheticLambda0
                public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                    com.android.server.am.SettingsToPropertiesMapper.this.lambda$updatePropertiesFromSettings$0(properties);
                }
            });
        }
        for (java.lang.String str3 : this.mDeviceConfigAconfigScopes) {
            android.provider.DeviceConfig.addOnPropertiesChangedListener(str3, android.os.AsyncTask.THREAD_POOL_EXECUTOR, new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.am.SettingsToPropertiesMapper$$ExternalSyntheticLambda1
                public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                    com.android.server.am.SettingsToPropertiesMapper.this.lambda$updatePropertiesFromSettings$1(properties);
                }
            });
        }
        android.provider.DeviceConfig.addOnPropertiesChangedListener(NAMESPACE_REBOOT_STAGING, android.os.AsyncTask.THREAD_POOL_EXECUTOR, new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.am.SettingsToPropertiesMapper$$ExternalSyntheticLambda2
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.am.SettingsToPropertiesMapper.this.lambda$updatePropertiesFromSettings$2(properties);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePropertiesFromSettings$0(android.provider.DeviceConfig.Properties properties) {
        java.lang.String namespace = properties.getNamespace();
        for (java.lang.String str : properties.getKeyset()) {
            java.lang.String makePropertyName = makePropertyName(namespace, str);
            if (makePropertyName == null) {
                log("unable to construct system property for " + namespace + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str);
                return;
            }
            setProperty(makePropertyName, properties.getString(str, (java.lang.String) null));
            java.lang.String makeAconfigFlagPropertyName = makeAconfigFlagPropertyName(namespace, str);
            if (makeAconfigFlagPropertyName == null) {
                log("unable to construct system property for " + namespace + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str);
                return;
            }
            setProperty(makeAconfigFlagPropertyName, properties.getString(str, (java.lang.String) null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePropertiesFromSettings$1(android.provider.DeviceConfig.Properties properties) {
        java.lang.String namespace = properties.getNamespace();
        for (java.lang.String str : properties.getKeyset()) {
            java.lang.String makeAconfigFlagPropertyName = makeAconfigFlagPropertyName(namespace, str);
            if (makeAconfigFlagPropertyName == null) {
                log("unable to construct system property for " + namespace + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str);
                return;
            }
            setProperty(makeAconfigFlagPropertyName, properties.getString(str, (java.lang.String) null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePropertiesFromSettings$2(android.provider.DeviceConfig.Properties properties) {
        java.lang.String namespace = properties.getNamespace();
        for (java.lang.String str : properties.getKeyset()) {
            java.lang.String makeAconfigFlagStagedPropertyName = makeAconfigFlagStagedPropertyName(str);
            if (makeAconfigFlagStagedPropertyName == null) {
                log("unable to construct system property for " + namespace + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str);
                return;
            }
            setProperty(makeAconfigFlagStagedPropertyName, properties.getString(str, (java.lang.String) null));
        }
    }

    public static com.android.server.am.SettingsToPropertiesMapper start(android.content.ContentResolver contentResolver) {
        com.android.server.am.SettingsToPropertiesMapper settingsToPropertiesMapper = new com.android.server.am.SettingsToPropertiesMapper(contentResolver, sGlobalSettings, sDeviceConfigScopes, sDeviceConfigAconfigScopes);
        settingsToPropertiesMapper.updatePropertiesFromSettings();
        return settingsToPropertiesMapper;
    }

    public static boolean isNativeFlagsResetPerformed() {
        return "true".equals(android.os.SystemProperties.get(RESET_PERFORMED_PROPERTY));
    }

    @android.annotation.NonNull
    public static java.lang.String[] getResetNativeCategories() {
        if (!isNativeFlagsResetPerformed()) {
            return new java.lang.String[0];
        }
        java.lang.String resetFlagsFileContent = getResetFlagsFileContent();
        if (android.text.TextUtils.isEmpty(resetFlagsFileContent)) {
            return new java.lang.String[0];
        }
        java.lang.String[] split = resetFlagsFileContent.split(";");
        java.util.HashSet hashSet = new java.util.HashSet();
        for (java.lang.String str : split) {
            java.lang.String[] split2 = str.split("\\.");
            if (split2.length < 3) {
                log("failed to extract category name from property " + str);
            } else {
                hashSet.add(split2[2]);
            }
        }
        return (java.lang.String[]) hashSet.toArray(new java.lang.String[0]);
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String makePropertyName(java.lang.String str, java.lang.String str2) {
        java.lang.String str3 = SYSTEM_PROPERTY_PREFIX + str + "." + str2;
        if (!str3.matches(SYSTEM_PROPERTY_VALID_CHARACTERS_REGEX) || str3.contains(SYSTEM_PROPERTY_INVALID_SUBSTRING)) {
            return null;
        }
        return str3;
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String makeAconfigFlagStagedPropertyName(java.lang.String str) {
        int indexOf = str.indexOf(NAMESPACE_REBOOT_STAGING_DELIMITER);
        if (indexOf == -1 || indexOf == str.length() - 1 || indexOf == 0) {
            log("invalid staged flag: " + str);
            return null;
        }
        java.lang.String str2 = "next_boot." + makeAconfigFlagPropertyName(str.substring(0, indexOf), str.substring(indexOf + 1));
        if (!str2.matches(SYSTEM_PROPERTY_VALID_CHARACTERS_REGEX) || str2.contains(SYSTEM_PROPERTY_INVALID_SUBSTRING)) {
            return null;
        }
        return str2;
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String makeAconfigFlagPropertyName(java.lang.String str, java.lang.String str2) {
        java.lang.String str3 = "persist.device_config.aconfig_flags." + str + "." + str2;
        if (!str3.matches(SYSTEM_PROPERTY_VALID_CHARACTERS_REGEX) || str3.contains(SYSTEM_PROPERTY_INVALID_SUBSTRING)) {
            return null;
        }
        return str3;
    }

    private void setProperty(java.lang.String str, java.lang.String str2) {
        if (str2 == null) {
            if (android.text.TextUtils.isEmpty(android.os.SystemProperties.get(str))) {
                return;
            } else {
                str2 = "";
            }
        } else if (str2.length() > 92) {
            log("key=" + str + " value=" + str2 + " exceeds system property max length.");
            return;
        }
        try {
            android.os.SystemProperties.set(str, str2);
        } catch (java.lang.Exception e) {
            log("Unable to set property " + str + " value '" + str2 + "'", e);
        }
    }

    private static void log(java.lang.String str, java.lang.Exception exc) {
        if (android.os.Build.IS_DEBUGGABLE) {
            android.util.Slog.wtf(TAG, str, exc);
        } else {
            android.util.Slog.e(TAG, str, exc);
        }
    }

    private static void log(java.lang.String str) {
        if (android.os.Build.IS_DEBUGGABLE) {
            android.util.Slog.wtf(TAG, str);
        } else {
            android.util.Slog.e(TAG, str);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String getResetFlagsFileContent() {
        java.lang.String str = null;
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(new java.io.File(RESET_RECORD_FILE_PATH)));
            str = bufferedReader.readLine();
            bufferedReader.close();
            return str;
        } catch (java.io.IOException e) {
            log("failed to read file /data/server_configurable_flags/reset_flags", e);
            return str;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void updatePropertyFromSetting(java.lang.String str, java.lang.String str2) {
        setProperty(str2, android.provider.Settings.Global.getString(this.mContentResolver, str));
    }
}
