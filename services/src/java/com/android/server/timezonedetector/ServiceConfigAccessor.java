package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public interface ServiceConfigAccessor {
    public static final java.lang.String PROVIDER_MODE_DISABLED = "disabled";
    public static final java.lang.String PROVIDER_MODE_ENABLED = "enabled";

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE, java.lang.annotation.ElementType.TYPE_PARAMETER})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProviderMode {
    }

    void addConfigurationInternalChangeListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener);

    void addLocationTimeZoneManagerConfigListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener);

    @android.annotation.NonNull
    com.android.server.timezonedetector.ConfigurationInternal getConfigurationInternal(int i);

    @android.annotation.NonNull
    com.android.server.timezonedetector.ConfigurationInternal getCurrentUserConfigurationInternal();

    @android.annotation.NonNull
    java.util.Optional<java.lang.Boolean> getGeoDetectionSettingEnabledOverride();

    @android.annotation.NonNull
    java.time.Duration getLocationTimeZoneProviderEventFilteringAgeThreshold();

    @android.annotation.NonNull
    java.time.Duration getLocationTimeZoneProviderInitializationTimeout();

    @android.annotation.NonNull
    java.time.Duration getLocationTimeZoneProviderInitializationTimeoutFuzz();

    @android.annotation.NonNull
    java.time.Duration getLocationTimeZoneUncertaintyDelay();

    @android.annotation.NonNull
    java.lang.String getPrimaryLocationTimeZoneProviderMode();

    @android.annotation.NonNull
    java.lang.String getPrimaryLocationTimeZoneProviderPackageName();

    boolean getRecordStateChangesForTests();

    java.lang.String getSecondaryLocationTimeZoneProviderMode();

    @android.annotation.NonNull
    java.lang.String getSecondaryLocationTimeZoneProviderPackageName();

    boolean isGeoDetectionEnabledForUsersByDefault();

    boolean isGeoTimeZoneDetectionFeatureSupported();

    boolean isGeoTimeZoneDetectionFeatureSupportedInConfig();

    boolean isTelephonyTimeZoneDetectionFeatureSupported();

    boolean isTestPrimaryLocationTimeZoneProvider();

    boolean isTestSecondaryLocationTimeZoneProvider();

    void removeConfigurationInternalChangeListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener);

    void resetVolatileTestConfig();

    void setRecordStateChangesForTests(boolean z);

    void setTestPrimaryLocationTimeZoneProviderPackageName(@android.annotation.Nullable java.lang.String str);

    void setTestSecondaryLocationTimeZoneProviderPackageName(@android.annotation.Nullable java.lang.String str);

    boolean updateConfiguration(int i, @android.annotation.NonNull android.app.time.TimeZoneConfiguration timeZoneConfiguration, boolean z);
}
