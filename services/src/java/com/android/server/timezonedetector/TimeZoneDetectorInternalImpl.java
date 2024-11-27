package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public final class TimeZoneDetectorInternalImpl implements com.android.server.timezonedetector.TimeZoneDetectorInternal {

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.CurrentUserIdentityInjector mCurrentUserIdentityInjector;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.TimeZoneDetectorStrategy mTimeZoneDetectorStrategy;

    public TimeZoneDetectorInternalImpl(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.timezonedetector.CurrentUserIdentityInjector currentUserIdentityInjector, @android.annotation.NonNull com.android.server.timezonedetector.TimeZoneDetectorStrategy timeZoneDetectorStrategy) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        java.util.Objects.requireNonNull(handler);
        this.mHandler = handler;
        java.util.Objects.requireNonNull(currentUserIdentityInjector);
        this.mCurrentUserIdentityInjector = currentUserIdentityInjector;
        java.util.Objects.requireNonNull(timeZoneDetectorStrategy);
        this.mTimeZoneDetectorStrategy = timeZoneDetectorStrategy;
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorInternal
    @android.annotation.NonNull
    public android.app.time.TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfigForDpm() {
        return this.mTimeZoneDetectorStrategy.getCapabilitiesAndConfig(this.mCurrentUserIdentityInjector.getCurrentUserId(), true);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorInternal
    public boolean updateConfigurationForDpm(@android.annotation.NonNull android.app.time.TimeZoneConfiguration timeZoneConfiguration) {
        java.util.Objects.requireNonNull(timeZoneConfiguration);
        return this.mTimeZoneDetectorStrategy.updateConfiguration(this.mCurrentUserIdentityInjector.getCurrentUserId(), timeZoneConfiguration, true);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorInternal
    public boolean setManualTimeZoneForDpm(@android.annotation.NonNull android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion) {
        java.util.Objects.requireNonNull(manualTimeZoneSuggestion);
        return this.mTimeZoneDetectorStrategy.suggestManualTimeZone(this.mCurrentUserIdentityInjector.getCurrentUserId(), manualTimeZoneSuggestion, true);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorInternal
    public void handleLocationAlgorithmEvent(@android.annotation.NonNull final com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent) {
        java.util.Objects.requireNonNull(locationAlgorithmEvent);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.timezonedetector.TimeZoneDetectorInternalImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timezonedetector.TimeZoneDetectorInternalImpl.this.lambda$handleLocationAlgorithmEvent$0(locationAlgorithmEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleLocationAlgorithmEvent$0(com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent) {
        this.mTimeZoneDetectorStrategy.handleLocationAlgorithmEvent(locationAlgorithmEvent);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorInternal
    @android.annotation.NonNull
    public com.android.server.timezonedetector.MetricsTimeZoneDetectorState generateMetricsState() {
        return this.mTimeZoneDetectorStrategy.generateMetricsState();
    }
}
