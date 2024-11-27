package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
class LocationTimeZoneProviderControllerEnvironmentImpl extends com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Environment {

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.StateChangeListener mConfigurationInternalChangeListener;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.ServiceConfigAccessor mServiceConfigAccessor;

    LocationTimeZoneProviderControllerEnvironmentImpl(@android.annotation.NonNull com.android.server.timezonedetector.location.ThreadingDomain threadingDomain, @android.annotation.NonNull com.android.server.timezonedetector.ServiceConfigAccessor serviceConfigAccessor, @android.annotation.NonNull final com.android.server.timezonedetector.location.LocationTimeZoneProviderController locationTimeZoneProviderController) {
        super(threadingDomain);
        java.util.Objects.requireNonNull(serviceConfigAccessor);
        this.mServiceConfigAccessor = serviceConfigAccessor;
        this.mConfigurationInternalChangeListener = new com.android.server.timezonedetector.StateChangeListener() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneProviderControllerEnvironmentImpl$$ExternalSyntheticLambda1
            @Override // com.android.server.timezonedetector.StateChangeListener
            public final void onChange() {
                com.android.server.timezonedetector.location.LocationTimeZoneProviderControllerEnvironmentImpl.this.lambda$new$0(locationTimeZoneProviderController);
            }
        };
        this.mServiceConfigAccessor.addConfigurationInternalChangeListener(this.mConfigurationInternalChangeListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(final com.android.server.timezonedetector.location.LocationTimeZoneProviderController locationTimeZoneProviderController) {
        com.android.server.timezonedetector.location.ThreadingDomain threadingDomain = this.mThreadingDomain;
        java.util.Objects.requireNonNull(locationTimeZoneProviderController);
        threadingDomain.post(new java.lang.Runnable() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneProviderControllerEnvironmentImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timezonedetector.location.LocationTimeZoneProviderController.this.onConfigurationInternalChanged();
            }
        });
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Environment
    void destroy() {
        this.mServiceConfigAccessor.removeConfigurationInternalChangeListener(this.mConfigurationInternalChangeListener);
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Environment
    @android.annotation.NonNull
    com.android.server.timezonedetector.ConfigurationInternal getCurrentUserConfigurationInternal() {
        return this.mServiceConfigAccessor.getCurrentUserConfigurationInternal();
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Environment
    @android.annotation.NonNull
    java.time.Duration getProviderInitializationTimeout() {
        return this.mServiceConfigAccessor.getLocationTimeZoneProviderInitializationTimeout();
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Environment
    @android.annotation.NonNull
    java.time.Duration getProviderInitializationTimeoutFuzz() {
        return this.mServiceConfigAccessor.getLocationTimeZoneProviderInitializationTimeoutFuzz();
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Environment
    @android.annotation.NonNull
    java.time.Duration getUncertaintyDelay() {
        return this.mServiceConfigAccessor.getLocationTimeZoneUncertaintyDelay();
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Environment
    java.time.Duration getProviderEventFilteringAgeThreshold() {
        return this.mServiceConfigAccessor.getLocationTimeZoneProviderEventFilteringAgeThreshold();
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Environment
    long elapsedRealtimeMillis() {
        return android.os.SystemClock.elapsedRealtime();
    }
}
