package com.android.server.am;

/* loaded from: classes.dex */
final class CoreSettingsObserver extends android.database.ContentObserver {
    private static volatile boolean sDeviceConfigContextEntriesLoaded;
    private final com.android.server.am.ActivityManagerService mActivityManagerService;
    private final android.os.Bundle mCoreSettings;
    private static final java.lang.String LOG_TAG = com.android.server.am.CoreSettingsObserver.class.getSimpleName();

    @com.android.internal.annotations.VisibleForTesting
    static final java.util.Map<java.lang.String, java.lang.Class<?>> sSecureSettingToTypeMap = new java.util.HashMap();

    @com.android.internal.annotations.VisibleForTesting
    static final java.util.Map<java.lang.String, java.lang.Class<?>> sSystemSettingToTypeMap = new java.util.HashMap();

    @com.android.internal.annotations.VisibleForTesting
    static final java.util.Map<java.lang.String, java.lang.Class<?>> sGlobalSettingToTypeMap = new java.util.HashMap();
    static final java.util.List<com.android.server.am.CoreSettingsObserver.DeviceConfigEntry> sDeviceConfigEntries = new java.util.ArrayList();

    static {
        sSecureSettingToTypeMap.put("long_press_timeout", java.lang.Integer.TYPE);
        sSecureSettingToTypeMap.put("multi_press_timeout", java.lang.Integer.TYPE);
        sSecureSettingToTypeMap.put("key_repeat_timeout", java.lang.Integer.TYPE);
        sSecureSettingToTypeMap.put("key_repeat_delay", java.lang.Integer.TYPE);
        sSystemSettingToTypeMap.put("time_12_24", java.lang.String.class);
        sGlobalSettingToTypeMap.put("debug_view_attributes", java.lang.Integer.TYPE);
        sGlobalSettingToTypeMap.put("debug_view_attributes_application_package", java.lang.String.class);
        sGlobalSettingToTypeMap.put("angle_debug_package", java.lang.String.class);
        sGlobalSettingToTypeMap.put("angle_gl_driver_all_angle", java.lang.Integer.TYPE);
        sGlobalSettingToTypeMap.put("angle_gl_driver_selection_pkgs", java.lang.String.class);
        sGlobalSettingToTypeMap.put("angle_gl_driver_selection_values", java.lang.String.class);
        sGlobalSettingToTypeMap.put("angle_egl_features", java.lang.String.class);
        sGlobalSettingToTypeMap.put("show_angle_in_use_dialog_box", java.lang.String.class);
        sGlobalSettingToTypeMap.put("enable_gpu_debug_layers", java.lang.Integer.TYPE);
        sGlobalSettingToTypeMap.put("gpu_debug_app", java.lang.String.class);
        sGlobalSettingToTypeMap.put("gpu_debug_layers", java.lang.String.class);
        sGlobalSettingToTypeMap.put("gpu_debug_layers_gles", java.lang.String.class);
        sGlobalSettingToTypeMap.put("gpu_debug_layer_app", java.lang.String.class);
        sGlobalSettingToTypeMap.put("updatable_driver_all_apps", java.lang.Integer.TYPE);
        sGlobalSettingToTypeMap.put("updatable_driver_production_opt_in_apps", java.lang.String.class);
        sGlobalSettingToTypeMap.put("updatable_driver_prerelease_opt_in_apps", java.lang.String.class);
        sGlobalSettingToTypeMap.put("updatable_driver_production_opt_out_apps", java.lang.String.class);
        sGlobalSettingToTypeMap.put("updatable_driver_production_denylist", java.lang.String.class);
        sGlobalSettingToTypeMap.put("updatable_driver_production_allowlist", java.lang.String.class);
        sGlobalSettingToTypeMap.put("updatable_driver_production_denylists", java.lang.String.class);
        sGlobalSettingToTypeMap.put("updatable_driver_sphal_libraries", java.lang.String.class);
        sDeviceConfigEntries.add(new com.android.server.am.CoreSettingsObserver.DeviceConfigEntry("widget", "CursorControlFeature__enable_cursor_drag_from_anywhere", "widget__enable_cursor_drag_from_anywhere", java.lang.Boolean.TYPE, true));
        sDeviceConfigEntries.add(new com.android.server.am.CoreSettingsObserver.DeviceConfigEntry("widget", "CursorControlFeature__min_angle_from_vertical_to_start_cursor_drag", "widget__min_angle_from_vertical_to_start_cursor_drag", java.lang.Integer.TYPE, 45));
        sDeviceConfigEntries.add(new com.android.server.am.CoreSettingsObserver.DeviceConfigEntry("widget", "CursorControlFeature__finger_to_cursor_distance", "widget__finger_to_cursor_distance", java.lang.Integer.TYPE, -1));
        sDeviceConfigEntries.add(new com.android.server.am.CoreSettingsObserver.DeviceConfigEntry("widget", "CursorControlFeature__enable_insertion_handle_gestures", "widget__enable_insertion_handle_gestures", java.lang.Boolean.TYPE, false));
        sDeviceConfigEntries.add(new com.android.server.am.CoreSettingsObserver.DeviceConfigEntry("widget", "CursorControlFeature__insertion_handle_delta_height", "widget__insertion_handle_delta_height", java.lang.Integer.TYPE, 25));
        sDeviceConfigEntries.add(new com.android.server.am.CoreSettingsObserver.DeviceConfigEntry("widget", "CursorControlFeature__insertion_handle_opacity", "widget__insertion_handle_opacity", java.lang.Integer.TYPE, 50));
        sDeviceConfigEntries.add(new com.android.server.am.CoreSettingsObserver.DeviceConfigEntry("widget", "CursorControlFeature__line_slop_ratio", "widget__line_slop_ratio", java.lang.Float.TYPE, java.lang.Float.valueOf(0.5f)));
        sDeviceConfigEntries.add(new com.android.server.am.CoreSettingsObserver.DeviceConfigEntry("widget", "CursorControlFeature__enable_new_magnifier", "widget__enable_new_magnifier", java.lang.Boolean.TYPE, false));
        sDeviceConfigEntries.add(new com.android.server.am.CoreSettingsObserver.DeviceConfigEntry("widget", "CursorControlFeature__magnifier_zoom_factor", "widget__magnifier_zoom_factor", java.lang.Float.TYPE, java.lang.Float.valueOf(1.5f)));
        sDeviceConfigEntries.add(new com.android.server.am.CoreSettingsObserver.DeviceConfigEntry("widget", "CursorControlFeature__magnifier_aspect_ratio", "widget__magnifier_aspect_ratio", java.lang.Float.TYPE, java.lang.Float.valueOf(5.5f)));
        sDeviceConfigEntries.add(new com.android.server.am.CoreSettingsObserver.DeviceConfigEntry("text", "TextEditing__enable_new_context_menu", "text__enable_new_context_menu", java.lang.Boolean.TYPE, true));
        for (int i = 0; i < android.text.TextFlags.TEXT_ACONFIGS_FLAGS.length; i++) {
            java.lang.String str = android.text.TextFlags.TEXT_ACONFIGS_FLAGS[i];
            sDeviceConfigEntries.add(new com.android.server.am.CoreSettingsObserver.DeviceConfigEntry("text", str, android.text.TextFlags.getKeyForFlag(str), java.lang.Boolean.TYPE, java.lang.Boolean.valueOf(android.text.TextFlags.TEXT_ACONFIG_DEFAULT_VALUE[i])));
        }
        sDeviceConfigContextEntriesLoaded = false;
    }

    private static class DeviceConfigEntry<T> {
        java.lang.String coreSettingKey;
        T defaultValue;
        java.lang.String flag;
        java.lang.String namespace;
        java.lang.Class<T> type;

        DeviceConfigEntry(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Class<T> cls, @android.annotation.NonNull T t) {
            this.namespace = str;
            this.flag = str2;
            this.coreSettingKey = str3;
            this.type = cls;
            java.util.Objects.requireNonNull(t);
            this.defaultValue = t;
        }
    }

    public CoreSettingsObserver(com.android.server.am.ActivityManagerService activityManagerService) {
        super(activityManagerService.mHandler);
        this.mCoreSettings = new android.os.Bundle();
        if (!sDeviceConfigContextEntriesLoaded) {
            synchronized (sDeviceConfigEntries) {
                try {
                    if (!sDeviceConfigContextEntriesLoaded) {
                        loadDeviceConfigContextEntries(activityManagerService.mContext);
                        sDeviceConfigContextEntriesLoaded = true;
                    }
                } finally {
                }
            }
        }
        this.mActivityManagerService = activityManagerService;
        beginObserveCoreSettings();
        sendCoreSettings();
    }

    private static void loadDeviceConfigContextEntries(android.content.Context context) {
        sDeviceConfigEntries.add(new com.android.server.am.CoreSettingsObserver.DeviceConfigEntry("widget", "AnalogClockFeature__analog_clock_seconds_hand_fps", "widget__analog_clock_seconds_hand_fps", java.lang.Integer.TYPE, java.lang.Integer.valueOf(context.getResources().getInteger(android.R.integer.config_defaultAlarmVibrationIntensity))));
    }

    public android.os.Bundle getCoreSettingsLocked() {
        return (android.os.Bundle) this.mCoreSettings.clone();
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        com.android.server.am.ActivityManagerService activityManagerService = this.mActivityManagerService;
        com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                sendCoreSettings();
            } catch (java.lang.Throwable th) {
                com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
    }

    private void sendCoreSettings() {
        populateSettings(this.mCoreSettings, sSecureSettingToTypeMap);
        populateSettings(this.mCoreSettings, sSystemSettingToTypeMap);
        populateSettings(this.mCoreSettings, sGlobalSettingToTypeMap);
        populateSettingsFromDeviceConfig();
        this.mActivityManagerService.onCoreSettingsChange(this.mCoreSettings);
    }

    private void beginObserveCoreSettings() {
        java.util.Iterator<java.lang.String> it = sSecureSettingToTypeMap.keySet().iterator();
        while (it.hasNext()) {
            this.mActivityManagerService.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor(it.next()), false, this);
        }
        java.util.Iterator<java.lang.String> it2 = sSystemSettingToTypeMap.keySet().iterator();
        while (it2.hasNext()) {
            this.mActivityManagerService.mContext.getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor(it2.next()), false, this);
        }
        java.util.Iterator<java.lang.String> it3 = sGlobalSettingToTypeMap.keySet().iterator();
        while (it3.hasNext()) {
            this.mActivityManagerService.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor(it3.next()), false, this);
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        for (com.android.server.am.CoreSettingsObserver.DeviceConfigEntry deviceConfigEntry : sDeviceConfigEntries) {
            if (!hashSet.contains(deviceConfigEntry.namespace)) {
                android.provider.DeviceConfig.addOnPropertiesChangedListener(deviceConfigEntry.namespace, android.app.ActivityThread.currentApplication().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.am.CoreSettingsObserver$$ExternalSyntheticLambda0
                    public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                        com.android.server.am.CoreSettingsObserver.this.lambda$beginObserveCoreSettings$0(properties);
                    }
                });
                hashSet.add(deviceConfigEntry.namespace);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$beginObserveCoreSettings$0(android.provider.DeviceConfig.Properties properties) {
        onChange(false);
    }

    @com.android.internal.annotations.VisibleForTesting
    void populateSettings(android.os.Bundle bundle, java.util.Map<java.lang.String, java.lang.Class<?>> map) {
        java.lang.String string;
        android.content.ContentResolver contentResolver = this.mActivityManagerService.mContext.getContentResolver();
        for (java.util.Map.Entry<java.lang.String, java.lang.Class<?>> entry : map.entrySet()) {
            java.lang.String key = entry.getKey();
            if (map == sSecureSettingToTypeMap) {
                string = android.provider.Settings.Secure.getStringForUser(contentResolver, key, contentResolver.getUserId());
            } else if (map == sSystemSettingToTypeMap) {
                string = android.provider.Settings.System.getStringForUser(contentResolver, key, contentResolver.getUserId());
            } else {
                string = android.provider.Settings.Global.getString(contentResolver, key);
            }
            if (string == null) {
                bundle.remove(key);
            } else {
                java.lang.Class<?> value = entry.getValue();
                if (value == java.lang.String.class) {
                    bundle.putString(key, string);
                } else if (value == java.lang.Integer.TYPE) {
                    bundle.putInt(key, java.lang.Integer.parseInt(string));
                } else if (value == java.lang.Float.TYPE) {
                    bundle.putFloat(key, java.lang.Float.parseFloat(string));
                } else if (value == java.lang.Long.TYPE) {
                    bundle.putLong(key, java.lang.Long.parseLong(string));
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void populateSettingsFromDeviceConfig() {
        for (com.android.server.am.CoreSettingsObserver.DeviceConfigEntry deviceConfigEntry : sDeviceConfigEntries) {
            if (deviceConfigEntry.type == java.lang.String.class) {
                this.mCoreSettings.putString(deviceConfigEntry.coreSettingKey, android.provider.DeviceConfig.getString(deviceConfigEntry.namespace, deviceConfigEntry.flag, (java.lang.String) deviceConfigEntry.defaultValue));
            } else if (deviceConfigEntry.type == java.lang.Integer.TYPE) {
                this.mCoreSettings.putInt(deviceConfigEntry.coreSettingKey, android.provider.DeviceConfig.getInt(deviceConfigEntry.namespace, deviceConfigEntry.flag, ((java.lang.Integer) deviceConfigEntry.defaultValue).intValue()));
            } else if (deviceConfigEntry.type == java.lang.Float.TYPE) {
                this.mCoreSettings.putFloat(deviceConfigEntry.coreSettingKey, android.provider.DeviceConfig.getFloat(deviceConfigEntry.namespace, deviceConfigEntry.flag, ((java.lang.Float) deviceConfigEntry.defaultValue).floatValue()));
            } else if (deviceConfigEntry.type == java.lang.Long.TYPE) {
                this.mCoreSettings.putLong(deviceConfigEntry.coreSettingKey, android.provider.DeviceConfig.getLong(deviceConfigEntry.namespace, deviceConfigEntry.flag, ((java.lang.Long) deviceConfigEntry.defaultValue).longValue()));
            } else if (deviceConfigEntry.type == java.lang.Boolean.TYPE) {
                this.mCoreSettings.putInt(deviceConfigEntry.coreSettingKey, android.provider.DeviceConfig.getBoolean(deviceConfigEntry.namespace, deviceConfigEntry.flag, ((java.lang.Boolean) deviceConfigEntry.defaultValue).booleanValue()) ? 1 : 0);
            }
        }
    }
}
