package com.android.server.timedetector;

/* loaded from: classes2.dex */
public final class ServerFlags {
    public static final java.lang.String KEY_ENHANCED_METRICS_COLLECTION_ENABLED = "enhanced_metrics_collection_enabled";
    public static final java.lang.String KEY_LOCATION_TIME_ZONE_DETECTION_FEATURE_SUPPORTED = "location_time_zone_detection_feature_supported";
    public static final java.lang.String KEY_LOCATION_TIME_ZONE_DETECTION_RUN_IN_BACKGROUND_ENABLED = "location_time_zone_detection_run_in_background_enabled";
    public static final java.lang.String KEY_LOCATION_TIME_ZONE_DETECTION_SETTING_ENABLED_DEFAULT = "location_time_zone_detection_setting_enabled_default";
    public static final java.lang.String KEY_LOCATION_TIME_ZONE_DETECTION_SETTING_ENABLED_OVERRIDE = "location_time_zone_detection_setting_enabled_override";
    public static final java.lang.String KEY_LOCATION_TIME_ZONE_DETECTION_UNCERTAINTY_DELAY_MILLIS = "location_time_zone_detection_uncertainty_delay_millis";
    public static final java.lang.String KEY_LTZP_EVENT_FILTERING_AGE_THRESHOLD_MILLIS = "ltzp_event_filtering_age_threshold_millis";
    public static final java.lang.String KEY_LTZP_INITIALIZATION_TIMEOUT_FUZZ_MILLIS = "ltzp_init_timeout_fuzz_millis";
    public static final java.lang.String KEY_LTZP_INITIALIZATION_TIMEOUT_MILLIS = "ltzp_init_timeout_millis";
    public static final java.lang.String KEY_PRIMARY_LTZP_MODE_OVERRIDE = "primary_location_time_zone_provider_mode_override";
    public static final java.lang.String KEY_SECONDARY_LTZP_MODE_OVERRIDE = "secondary_location_time_zone_provider_mode_override";
    public static final java.lang.String KEY_TIME_DETECTOR_LOWER_BOUND_MILLIS_OVERRIDE = "time_detector_lower_bound_millis_override";
    public static final java.lang.String KEY_TIME_DETECTOR_ORIGIN_PRIORITIES_OVERRIDE = "time_detector_origin_priorities_override";
    public static final java.lang.String KEY_TIME_ZONE_DETECTOR_AUTO_DETECTION_ENABLED_DEFAULT = "time_zone_detector_auto_detection_enabled_default";
    public static final java.lang.String KEY_TIME_ZONE_DETECTOR_TELEPHONY_FALLBACK_SUPPORTED = "time_zone_detector_telephony_fallback_supported";

    @com.android.internal.annotations.GuardedBy({"SLOCK"})
    @android.annotation.Nullable
    private static com.android.server.timedetector.ServerFlags sInstance;

    @com.android.internal.annotations.GuardedBy({"mListeners"})
    private final android.util.ArrayMap<com.android.server.timezonedetector.StateChangeListener, java.util.HashSet<java.lang.String>> mListeners = new android.util.ArrayMap<>();
    private static final java.util.Optional<java.lang.Boolean> OPTIONAL_TRUE = java.util.Optional.of(true);
    private static final java.util.Optional<java.lang.Boolean> OPTIONAL_FALSE = java.util.Optional.of(false);
    private static final java.lang.Object SLOCK = new java.lang.Object();

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE, java.lang.annotation.ElementType.TYPE_PARAMETER})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface DeviceConfigKey {
    }

    private ServerFlags(android.content.Context context) {
        android.provider.DeviceConfig.addOnPropertiesChangedListener("system_time", context.getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.timedetector.ServerFlags$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.timedetector.ServerFlags.this.handlePropertiesChanged(properties);
            }
        });
    }

    public static com.android.server.timedetector.ServerFlags getInstance(android.content.Context context) {
        com.android.server.timedetector.ServerFlags serverFlags;
        synchronized (SLOCK) {
            try {
                if (sInstance == null) {
                    sInstance = new com.android.server.timedetector.ServerFlags(context);
                }
                serverFlags = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return serverFlags;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePropertiesChanged(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
        java.util.ArrayList arrayList;
        synchronized (this.mListeners) {
            try {
                arrayList = new java.util.ArrayList(this.mListeners.size());
                for (java.util.Map.Entry<com.android.server.timezonedetector.StateChangeListener, java.util.HashSet<java.lang.String>> entry : this.mListeners.entrySet()) {
                    if (containsAny(entry.getValue(), properties.getKeyset())) {
                        arrayList.add(entry.getKey());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((com.android.server.timezonedetector.StateChangeListener) it.next()).onChange();
        }
    }

    private static boolean containsAny(@android.annotation.NonNull java.util.Set<java.lang.String> set, @android.annotation.NonNull java.lang.Iterable<java.lang.String> iterable) {
        java.util.Iterator<java.lang.String> it = iterable.iterator();
        while (it.hasNext()) {
            if (set.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    public void addListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener, @android.annotation.NonNull java.util.Set<java.lang.String> set) {
        java.util.Objects.requireNonNull(stateChangeListener);
        java.util.Objects.requireNonNull(set);
        java.util.HashSet<java.lang.String> hashSet = new java.util.HashSet<>(set);
        synchronized (this.mListeners) {
            this.mListeners.put(stateChangeListener, hashSet);
        }
    }

    @android.annotation.NonNull
    public java.util.Optional<java.lang.String> getOptionalString(java.lang.String str) {
        return java.util.Optional.ofNullable(android.provider.DeviceConfig.getProperty("system_time", str));
    }

    @android.annotation.NonNull
    public java.util.Optional<java.lang.String[]> getOptionalStringArray(java.lang.String str) {
        java.util.Optional<java.lang.String> optionalString = getOptionalString(str);
        if (!optionalString.isPresent()) {
            return java.util.Optional.empty();
        }
        java.lang.String str2 = optionalString.get();
        if ("_[]_".equals(str2)) {
            return java.util.Optional.of(new java.lang.String[0]);
        }
        return java.util.Optional.of(str2.split(","));
    }

    @android.annotation.NonNull
    public java.util.Optional<java.time.Instant> getOptionalInstant(java.lang.String str) {
        java.lang.String property = android.provider.DeviceConfig.getProperty("system_time", str);
        if (property == null) {
            return java.util.Optional.empty();
        }
        try {
            return java.util.Optional.of(java.time.Instant.ofEpochMilli(java.lang.Long.parseLong(property)));
        } catch (java.lang.NumberFormatException | java.time.DateTimeException e) {
            return java.util.Optional.empty();
        }
    }

    @android.annotation.NonNull
    public java.util.Optional<java.lang.Boolean> getOptionalBoolean(java.lang.String str) {
        return parseOptionalBoolean(android.provider.DeviceConfig.getProperty("system_time", str));
    }

    @android.annotation.NonNull
    private static java.util.Optional<java.lang.Boolean> parseOptionalBoolean(@android.annotation.Nullable java.lang.String str) {
        if (str == null) {
            return java.util.Optional.empty();
        }
        return java.lang.Boolean.parseBoolean(str) ? OPTIONAL_TRUE : OPTIONAL_FALSE;
    }

    public boolean getBoolean(java.lang.String str, boolean z) {
        return android.provider.DeviceConfig.getBoolean("system_time", str, z);
    }

    @android.annotation.Nullable
    public java.time.Duration getDurationFromMillis(java.lang.String str, @android.annotation.Nullable java.time.Duration duration) {
        long j = android.provider.DeviceConfig.getLong("system_time", str, -1L);
        if (j < 0) {
            return duration;
        }
        return java.time.Duration.ofMillis(j);
    }
}
