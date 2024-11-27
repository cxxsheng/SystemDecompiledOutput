package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public interface TimeZoneDetectorStrategy extends com.android.server.timezonedetector.Dumpable {
    void addChangeListener(com.android.server.timezonedetector.StateChangeListener stateChangeListener);

    boolean confirmTimeZone(@android.annotation.NonNull java.lang.String str);

    void enableTelephonyTimeZoneFallback(@android.annotation.NonNull java.lang.String str);

    @android.annotation.NonNull
    com.android.server.timezonedetector.MetricsTimeZoneDetectorState generateMetricsState();

    android.app.time.TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfig(int i, boolean z);

    @android.annotation.NonNull
    android.app.time.TimeZoneState getTimeZoneState();

    void handleLocationAlgorithmEvent(@android.annotation.NonNull com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent);

    boolean isGeoTimeZoneDetectionSupported();

    boolean isTelephonyTimeZoneDetectionSupported();

    void setTimeZoneState(@android.annotation.NonNull android.app.time.TimeZoneState timeZoneState);

    boolean suggestManualTimeZone(int i, @android.annotation.NonNull android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion, boolean z);

    void suggestTelephonyTimeZone(@android.annotation.NonNull android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion);

    boolean updateConfiguration(int i, android.app.time.TimeZoneConfiguration timeZoneConfiguration, boolean z);
}
