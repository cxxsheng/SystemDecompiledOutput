package com.android.server.timedetector;

/* loaded from: classes2.dex */
public interface ServiceConfigAccessor {
    void addConfigurationInternalChangeListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener);

    @android.annotation.NonNull
    com.android.server.timedetector.ConfigurationInternal getConfigurationInternal(int i);

    @android.annotation.NonNull
    com.android.server.timedetector.ConfigurationInternal getCurrentUserConfigurationInternal();

    void removeConfigurationInternalChangeListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener);

    boolean updateConfiguration(int i, @android.annotation.NonNull android.app.time.TimeConfiguration timeConfiguration, boolean z);
}
