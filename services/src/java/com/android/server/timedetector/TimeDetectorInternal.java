package com.android.server.timedetector;

/* loaded from: classes2.dex */
public interface TimeDetectorInternal {
    void addNetworkTimeUpdateListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener);

    @android.annotation.NonNull
    android.app.time.TimeCapabilitiesAndConfig getCapabilitiesAndConfigForDpm();

    @android.annotation.Nullable
    com.android.server.timedetector.NetworkTimeSuggestion getLatestNetworkSuggestion();

    boolean setManualTimeForDpm(@android.annotation.NonNull android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion);

    void suggestGnssTime(@android.annotation.NonNull com.android.server.timedetector.GnssTimeSuggestion gnssTimeSuggestion);

    void suggestNetworkTime(@android.annotation.NonNull com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion);

    boolean updateConfigurationForDpm(@android.annotation.NonNull android.app.time.TimeConfiguration timeConfiguration);
}
