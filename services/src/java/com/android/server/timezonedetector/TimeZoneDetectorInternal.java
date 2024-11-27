package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public interface TimeZoneDetectorInternal {
    @android.annotation.NonNull
    com.android.server.timezonedetector.MetricsTimeZoneDetectorState generateMetricsState();

    @android.annotation.NonNull
    android.app.time.TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfigForDpm();

    void handleLocationAlgorithmEvent(@android.annotation.NonNull com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent);

    boolean setManualTimeZoneForDpm(@android.annotation.NonNull android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion);

    boolean updateConfigurationForDpm(@android.annotation.NonNull android.app.time.TimeZoneConfiguration timeZoneConfiguration);
}
