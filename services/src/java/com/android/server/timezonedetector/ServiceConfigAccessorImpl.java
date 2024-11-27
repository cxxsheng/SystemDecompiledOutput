package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public final class ServiceConfigAccessorImpl implements com.android.server.timezonedetector.ServiceConfigAccessor {

    @com.android.internal.annotations.GuardedBy({"SLOCK"})
    @android.annotation.Nullable
    private static com.android.server.timezonedetector.ServiceConfigAccessor sInstance;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.List<com.android.server.timezonedetector.StateChangeListener> mConfigurationInternalListeners = new java.util.ArrayList();

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final android.content.ContentResolver mCr;

    @android.annotation.NonNull
    private final android.location.LocationManager mLocationManager;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mRecordStateChangesForTests;

    @android.annotation.NonNull
    private final com.android.server.timedetector.ServerFlags mServerFlags;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private java.lang.String mTestPrimaryLocationTimeZoneProviderMode;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private java.lang.String mTestPrimaryLocationTimeZoneProviderPackageName;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private java.lang.String mTestSecondaryLocationTimeZoneProviderMode;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private java.lang.String mTestSecondaryLocationTimeZoneProviderPackageName;

    @android.annotation.NonNull
    private final android.os.UserManager mUserManager;
    private static final java.util.Set<java.lang.String> CONFIGURATION_INTERNAL_SERVER_FLAGS_KEYS_TO_WATCH = java.util.Set.of(com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_FEATURE_SUPPORTED, com.android.server.timedetector.ServerFlags.KEY_PRIMARY_LTZP_MODE_OVERRIDE, com.android.server.timedetector.ServerFlags.KEY_SECONDARY_LTZP_MODE_OVERRIDE, com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_RUN_IN_BACKGROUND_ENABLED, com.android.server.timedetector.ServerFlags.KEY_ENHANCED_METRICS_COLLECTION_ENABLED, com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_SETTING_ENABLED_DEFAULT, com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_SETTING_ENABLED_OVERRIDE, com.android.server.timedetector.ServerFlags.KEY_TIME_ZONE_DETECTOR_AUTO_DETECTION_ENABLED_DEFAULT, com.android.server.timedetector.ServerFlags.KEY_TIME_ZONE_DETECTOR_TELEPHONY_FALLBACK_SUPPORTED);
    private static final java.util.Set<java.lang.String> LOCATION_TIME_ZONE_MANAGER_SERVER_FLAGS_KEYS_TO_WATCH = java.util.Set.of(com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_FEATURE_SUPPORTED, com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_RUN_IN_BACKGROUND_ENABLED, com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_SETTING_ENABLED_DEFAULT, com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_SETTING_ENABLED_OVERRIDE, com.android.server.timedetector.ServerFlags.KEY_PRIMARY_LTZP_MODE_OVERRIDE, com.android.server.timedetector.ServerFlags.KEY_SECONDARY_LTZP_MODE_OVERRIDE, com.android.server.timedetector.ServerFlags.KEY_LTZP_INITIALIZATION_TIMEOUT_MILLIS, com.android.server.timedetector.ServerFlags.KEY_LTZP_INITIALIZATION_TIMEOUT_FUZZ_MILLIS, com.android.server.timedetector.ServerFlags.KEY_LTZP_EVENT_FILTERING_AGE_THRESHOLD_MILLIS, com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_UNCERTAINTY_DELAY_MILLIS);
    private static final java.time.Duration DEFAULT_LTZP_INITIALIZATION_TIMEOUT = java.time.Duration.ofMinutes(5);
    private static final java.time.Duration DEFAULT_LTZP_INITIALIZATION_TIMEOUT_FUZZ = java.time.Duration.ofMinutes(1);
    private static final java.time.Duration DEFAULT_LTZP_UNCERTAINTY_DELAY = java.time.Duration.ofMinutes(5);
    private static final java.time.Duration DEFAULT_LTZP_EVENT_FILTER_AGE_THRESHOLD = java.time.Duration.ofMinutes(1);
    private static final java.lang.Object SLOCK = new java.lang.Object();

    private ServiceConfigAccessorImpl(@android.annotation.NonNull android.content.Context context) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        this.mCr = context.getContentResolver();
        this.mUserManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
        this.mLocationManager = (android.location.LocationManager) context.getSystemService(android.location.LocationManager.class);
        this.mServerFlags = com.android.server.timedetector.ServerFlags.getInstance(this.mContext);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.location.MODE_CHANGED");
        this.mContext.registerReceiverForAllUsers(new android.content.BroadcastReceiver() { // from class: com.android.server.timezonedetector.ServiceConfigAccessorImpl.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.android.server.timezonedetector.ServiceConfigAccessorImpl.this.handleConfigurationInternalChangeOnMainThread();
            }
        }, intentFilter, null, null);
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        android.database.ContentObserver contentObserver = new android.database.ContentObserver(this.mContext.getMainThreadHandler()) { // from class: com.android.server.timezonedetector.ServiceConfigAccessorImpl.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.timezonedetector.ServiceConfigAccessorImpl.this.handleConfigurationInternalChangeOnMainThread();
            }
        };
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("auto_time_zone"), true, contentObserver);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("auto_time_zone_explicit"), true, contentObserver);
        contentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("location_time_zone_detection_enabled"), true, contentObserver, -1);
        this.mServerFlags.addListener(new com.android.server.timezonedetector.StateChangeListener() { // from class: com.android.server.timezonedetector.ServiceConfigAccessorImpl$$ExternalSyntheticLambda0
            @Override // com.android.server.timezonedetector.StateChangeListener
            public final void onChange() {
                com.android.server.timezonedetector.ServiceConfigAccessorImpl.this.handleConfigurationInternalChangeOnMainThread();
            }
        }, CONFIGURATION_INTERNAL_SERVER_FLAGS_KEYS_TO_WATCH);
    }

    public static com.android.server.timezonedetector.ServiceConfigAccessor getInstance(android.content.Context context) {
        com.android.server.timezonedetector.ServiceConfigAccessor serviceConfigAccessor;
        synchronized (SLOCK) {
            try {
                if (sInstance == null) {
                    sInstance = new com.android.server.timezonedetector.ServiceConfigAccessorImpl(context);
                }
                serviceConfigAccessor = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return serviceConfigAccessor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleConfigurationInternalChangeOnMainThread() {
        java.util.ArrayList arrayList;
        synchronized (this) {
            arrayList = new java.util.ArrayList(this.mConfigurationInternalListeners);
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((com.android.server.timezonedetector.StateChangeListener) it.next()).onChange();
        }
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public synchronized void addConfigurationInternalChangeListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener) {
        java.util.List<com.android.server.timezonedetector.StateChangeListener> list = this.mConfigurationInternalListeners;
        java.util.Objects.requireNonNull(stateChangeListener);
        list.add(stateChangeListener);
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public synchronized void removeConfigurationInternalChangeListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener) {
        java.util.List<com.android.server.timezonedetector.StateChangeListener> list = this.mConfigurationInternalListeners;
        java.util.Objects.requireNonNull(stateChangeListener);
        list.remove(stateChangeListener);
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    @android.annotation.NonNull
    public synchronized com.android.server.timezonedetector.ConfigurationInternal getCurrentUserConfigurationInternal() {
        return getConfigurationInternal(((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getCurrentUserId());
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public synchronized boolean updateConfiguration(int i, @android.annotation.NonNull android.app.time.TimeZoneConfiguration timeZoneConfiguration, boolean z) {
        java.util.Objects.requireNonNull(timeZoneConfiguration);
        com.android.server.timezonedetector.ConfigurationInternal configurationInternal = getConfigurationInternal(i);
        android.app.time.TimeZoneConfiguration tryApplyConfigChanges = configurationInternal.asCapabilities(z).tryApplyConfigChanges(configurationInternal.asConfiguration(), timeZoneConfiguration);
        if (tryApplyConfigChanges == null) {
            return false;
        }
        storeConfiguration(i, timeZoneConfiguration, tryApplyConfigChanges);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void storeConfiguration(int i, @android.annotation.NonNull android.app.time.TimeZoneConfiguration timeZoneConfiguration, @android.annotation.NonNull android.app.time.TimeZoneConfiguration timeZoneConfiguration2) {
        java.util.Objects.requireNonNull(timeZoneConfiguration2);
        if (isAutoDetectionFeatureSupported()) {
            if (timeZoneConfiguration.hasIsAutoDetectionEnabled()) {
                android.provider.Settings.Global.putInt(this.mCr, "auto_time_zone_explicit", 1);
            }
            setAutoDetectionEnabledIfRequired(timeZoneConfiguration2.isAutoDetectionEnabled());
            if (getGeoDetectionSettingEnabledOverride().isEmpty() && isGeoTimeZoneDetectionFeatureSupported() && isTelephonyTimeZoneDetectionFeatureSupported()) {
                setGeoDetectionEnabledSettingIfRequired(i, timeZoneConfiguration2.isGeoDetectionEnabled());
            }
        }
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    @android.annotation.NonNull
    public synchronized com.android.server.timezonedetector.ConfigurationInternal getConfigurationInternal(int i) {
        return new com.android.server.timezonedetector.ConfigurationInternal.Builder().setUserId(i).setTelephonyDetectionFeatureSupported(isTelephonyTimeZoneDetectionFeatureSupported()).setGeoDetectionFeatureSupported(isGeoTimeZoneDetectionFeatureSupported()).setTelephonyFallbackSupported(isTelephonyFallbackSupported()).setGeoDetectionRunInBackgroundEnabled(getGeoDetectionRunInBackgroundEnabled()).setEnhancedMetricsCollectionEnabled(isEnhancedMetricsCollectionEnabled()).setAutoDetectionEnabledSetting(getAutoDetectionEnabledSetting()).setUserConfigAllowed(isUserConfigAllowed(i)).setLocationEnabledSetting(getLocationEnabledSetting(i)).setGeoDetectionEnabledSetting(getGeoDetectionEnabledSetting(i)).build();
    }

    private void setAutoDetectionEnabledIfRequired(boolean z) {
        if (getAutoDetectionEnabledSetting() != z) {
            android.provider.Settings.Global.putInt(this.mCr, "auto_time_zone", z ? 1 : 0);
        }
    }

    private boolean getLocationEnabledSetting(int i) {
        return this.mLocationManager.isLocationEnabledForUser(android.os.UserHandle.of(i));
    }

    private boolean isUserConfigAllowed(int i) {
        return !this.mUserManager.hasUserRestriction("no_config_date_time", android.os.UserHandle.of(i));
    }

    private boolean getAutoDetectionEnabledSetting() {
        boolean z = android.provider.Settings.Global.getInt(this.mCr, "auto_time_zone", 1) > 0;
        java.util.Optional<java.lang.Boolean> optionalBoolean = this.mServerFlags.getOptionalBoolean(com.android.server.timedetector.ServerFlags.KEY_TIME_ZONE_DETECTOR_AUTO_DETECTION_ENABLED_DEFAULT);
        if (optionalBoolean.isPresent() && android.provider.Settings.Global.getInt(this.mCr, "auto_time_zone_explicit", 0) == 0) {
            boolean booleanValue = optionalBoolean.get().booleanValue();
            if (booleanValue != z) {
                android.provider.Settings.Global.putInt(this.mCr, "auto_time_zone", booleanValue ? 1 : 0);
            }
            return booleanValue;
        }
        return z;
    }

    private boolean getGeoDetectionEnabledSetting(int i) {
        java.util.Optional<java.lang.Boolean> geoDetectionSettingEnabledOverride = getGeoDetectionSettingEnabledOverride();
        if (geoDetectionSettingEnabledOverride.isPresent()) {
            return geoDetectionSettingEnabledOverride.get().booleanValue();
        }
        return android.provider.Settings.Secure.getIntForUser(this.mCr, "location_time_zone_detection_enabled", isGeoDetectionEnabledForUsersByDefault() ? 1 : 0, i) != 0;
    }

    private void setGeoDetectionEnabledSettingIfRequired(int i, boolean z) {
        if (getGeoDetectionEnabledSetting(i) != z) {
            android.provider.Settings.Secure.putIntForUser(this.mCr, "location_time_zone_detection_enabled", z ? 1 : 0, i);
        }
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public void addLocationTimeZoneManagerConfigListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener) {
        this.mServerFlags.addListener(stateChangeListener, LOCATION_TIME_ZONE_MANAGER_SERVER_FLAGS_KEYS_TO_WATCH);
    }

    private boolean isAutoDetectionFeatureSupported() {
        return isTelephonyTimeZoneDetectionFeatureSupported() || isGeoTimeZoneDetectionFeatureSupported();
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public boolean isTelephonyTimeZoneDetectionFeatureSupported() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony");
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public boolean isGeoTimeZoneDetectionFeatureSupportedInConfig() {
        return this.mContext.getResources().getBoolean(android.R.bool.config_enableFusedLocationOverlay);
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public boolean isGeoTimeZoneDetectionFeatureSupported() {
        return isGeoTimeZoneDetectionFeatureSupportedInConfig() && isGeoTimeZoneDetectionFeatureSupportedInternal() && atLeastOneProviderIsEnabled();
    }

    private boolean atLeastOneProviderIsEnabled() {
        return (java.util.Objects.equals(getPrimaryLocationTimeZoneProviderMode(), com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED) && java.util.Objects.equals(getSecondaryLocationTimeZoneProviderMode(), com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED)) ? false : true;
    }

    private boolean isGeoTimeZoneDetectionFeatureSupportedInternal() {
        return this.mServerFlags.getBoolean(com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_FEATURE_SUPPORTED, true);
    }

    private boolean getGeoDetectionRunInBackgroundEnabled() {
        return this.mServerFlags.getBoolean(com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_RUN_IN_BACKGROUND_ENABLED, false);
    }

    private boolean isEnhancedMetricsCollectionEnabled() {
        return this.mServerFlags.getBoolean(com.android.server.timedetector.ServerFlags.KEY_ENHANCED_METRICS_COLLECTION_ENABLED, false);
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    @android.annotation.NonNull
    public synchronized java.lang.String getPrimaryLocationTimeZoneProviderPackageName() {
        if (this.mTestPrimaryLocationTimeZoneProviderMode != null) {
            return this.mTestPrimaryLocationTimeZoneProviderPackageName;
        }
        return this.mContext.getResources().getString(android.R.string.config_onDeviceIntelligenceModelLoadedBroadcastKey);
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public synchronized void setTestPrimaryLocationTimeZoneProviderPackageName(@android.annotation.Nullable java.lang.String str) {
        try {
            this.mTestPrimaryLocationTimeZoneProviderPackageName = str;
            this.mTestPrimaryLocationTimeZoneProviderMode = this.mTestPrimaryLocationTimeZoneProviderPackageName == null ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED;
            this.mContext.getMainThreadHandler().post(new com.android.server.timezonedetector.ServiceConfigAccessorImpl$$ExternalSyntheticLambda1(this));
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public synchronized boolean isTestPrimaryLocationTimeZoneProvider() {
        return this.mTestPrimaryLocationTimeZoneProviderMode != null;
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    @android.annotation.NonNull
    public synchronized java.lang.String getSecondaryLocationTimeZoneProviderPackageName() {
        if (this.mTestSecondaryLocationTimeZoneProviderMode != null) {
            return this.mTestSecondaryLocationTimeZoneProviderPackageName;
        }
        return this.mContext.getResources().getString(android.R.string.config_satellite_gateway_service_package);
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public synchronized void setTestSecondaryLocationTimeZoneProviderPackageName(@android.annotation.Nullable java.lang.String str) {
        try {
            this.mTestSecondaryLocationTimeZoneProviderPackageName = str;
            this.mTestSecondaryLocationTimeZoneProviderMode = this.mTestSecondaryLocationTimeZoneProviderPackageName == null ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED;
            this.mContext.getMainThreadHandler().post(new com.android.server.timezonedetector.ServiceConfigAccessorImpl$$ExternalSyntheticLambda1(this));
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public synchronized boolean isTestSecondaryLocationTimeZoneProvider() {
        return this.mTestSecondaryLocationTimeZoneProviderMode != null;
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public synchronized void setRecordStateChangesForTests(boolean z) {
        this.mRecordStateChangesForTests = z;
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public synchronized boolean getRecordStateChangesForTests() {
        return this.mRecordStateChangesForTests;
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    @android.annotation.NonNull
    public synchronized java.lang.String getPrimaryLocationTimeZoneProviderMode() {
        if (this.mTestPrimaryLocationTimeZoneProviderMode != null) {
            return this.mTestPrimaryLocationTimeZoneProviderMode;
        }
        return this.mServerFlags.getOptionalString(com.android.server.timedetector.ServerFlags.KEY_PRIMARY_LTZP_MODE_OVERRIDE).orElse(getPrimaryLocationTimeZoneProviderModeFromConfig());
    }

    @android.annotation.NonNull
    private synchronized java.lang.String getPrimaryLocationTimeZoneProviderModeFromConfig() {
        try {
        } finally {
        }
        return getConfigBoolean(android.R.bool.config_enableNewAutoSelectNetworkUI) ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED;
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public synchronized java.lang.String getSecondaryLocationTimeZoneProviderMode() {
        if (this.mTestSecondaryLocationTimeZoneProviderMode != null) {
            return this.mTestSecondaryLocationTimeZoneProviderMode;
        }
        return this.mServerFlags.getOptionalString(com.android.server.timedetector.ServerFlags.KEY_SECONDARY_LTZP_MODE_OVERRIDE).orElse(getSecondaryLocationTimeZoneProviderModeFromConfig());
    }

    @android.annotation.NonNull
    private synchronized java.lang.String getSecondaryLocationTimeZoneProviderModeFromConfig() {
        try {
        } finally {
        }
        return getConfigBoolean(android.R.bool.config_enableSafetyCenter) ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED;
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public boolean isGeoDetectionEnabledForUsersByDefault() {
        return this.mServerFlags.getBoolean(com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_SETTING_ENABLED_DEFAULT, false);
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    @android.annotation.NonNull
    public java.util.Optional<java.lang.Boolean> getGeoDetectionSettingEnabledOverride() {
        return this.mServerFlags.getOptionalBoolean(com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_SETTING_ENABLED_OVERRIDE);
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    @android.annotation.NonNull
    public java.time.Duration getLocationTimeZoneProviderInitializationTimeout() {
        return this.mServerFlags.getDurationFromMillis(com.android.server.timedetector.ServerFlags.KEY_LTZP_INITIALIZATION_TIMEOUT_MILLIS, DEFAULT_LTZP_INITIALIZATION_TIMEOUT);
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    @android.annotation.NonNull
    public java.time.Duration getLocationTimeZoneProviderInitializationTimeoutFuzz() {
        return this.mServerFlags.getDurationFromMillis(com.android.server.timedetector.ServerFlags.KEY_LTZP_INITIALIZATION_TIMEOUT_FUZZ_MILLIS, DEFAULT_LTZP_INITIALIZATION_TIMEOUT_FUZZ);
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    @android.annotation.NonNull
    public java.time.Duration getLocationTimeZoneUncertaintyDelay() {
        return this.mServerFlags.getDurationFromMillis(com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_UNCERTAINTY_DELAY_MILLIS, DEFAULT_LTZP_UNCERTAINTY_DELAY);
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    @android.annotation.NonNull
    public java.time.Duration getLocationTimeZoneProviderEventFilteringAgeThreshold() {
        return this.mServerFlags.getDurationFromMillis(com.android.server.timedetector.ServerFlags.KEY_LTZP_EVENT_FILTERING_AGE_THRESHOLD_MILLIS, DEFAULT_LTZP_EVENT_FILTER_AGE_THRESHOLD);
    }

    @Override // com.android.server.timezonedetector.ServiceConfigAccessor
    public synchronized void resetVolatileTestConfig() {
        this.mTestPrimaryLocationTimeZoneProviderPackageName = null;
        this.mTestPrimaryLocationTimeZoneProviderMode = null;
        this.mTestSecondaryLocationTimeZoneProviderPackageName = null;
        this.mTestSecondaryLocationTimeZoneProviderMode = null;
        this.mRecordStateChangesForTests = false;
        this.mContext.getMainThreadHandler().post(new com.android.server.timezonedetector.ServiceConfigAccessorImpl$$ExternalSyntheticLambda1(this));
    }

    private boolean isTelephonyFallbackSupported() {
        return this.mServerFlags.getBoolean(com.android.server.timedetector.ServerFlags.KEY_TIME_ZONE_DETECTOR_TELEPHONY_FALLBACK_SUPPORTED, getConfigBoolean(android.R.bool.config_supportShortPressPowerWhenDefaultDisplayOn));
    }

    private boolean getConfigBoolean(int i) {
        return this.mContext.getResources().getBoolean(i);
    }
}
