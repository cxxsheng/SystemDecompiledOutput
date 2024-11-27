package com.android.server.timedetector;

/* loaded from: classes2.dex */
public class TimeDetectorInternalImpl implements com.android.server.timedetector.TimeDetectorInternal {

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.CurrentUserIdentityInjector mCurrentUserIdentityInjector;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final com.android.server.timedetector.ServiceConfigAccessor mServiceConfigAccessor;

    @android.annotation.NonNull
    private final com.android.server.timedetector.TimeDetectorStrategy mTimeDetectorStrategy;

    public TimeDetectorInternalImpl(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.timezonedetector.CurrentUserIdentityInjector currentUserIdentityInjector, @android.annotation.NonNull com.android.server.timedetector.ServiceConfigAccessor serviceConfigAccessor, @android.annotation.NonNull com.android.server.timedetector.TimeDetectorStrategy timeDetectorStrategy) {
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        java.util.Objects.requireNonNull(handler);
        this.mHandler = handler;
        java.util.Objects.requireNonNull(currentUserIdentityInjector);
        this.mCurrentUserIdentityInjector = currentUserIdentityInjector;
        java.util.Objects.requireNonNull(serviceConfigAccessor);
        this.mServiceConfigAccessor = serviceConfigAccessor;
        java.util.Objects.requireNonNull(timeDetectorStrategy);
        this.mTimeDetectorStrategy = timeDetectorStrategy;
    }

    @Override // com.android.server.timedetector.TimeDetectorInternal
    @android.annotation.NonNull
    public android.app.time.TimeCapabilitiesAndConfig getCapabilitiesAndConfigForDpm() {
        return this.mServiceConfigAccessor.getConfigurationInternal(this.mCurrentUserIdentityInjector.getCurrentUserId()).createCapabilitiesAndConfig(true);
    }

    @Override // com.android.server.timedetector.TimeDetectorInternal
    public boolean updateConfigurationForDpm(@android.annotation.NonNull android.app.time.TimeConfiguration timeConfiguration) {
        java.util.Objects.requireNonNull(timeConfiguration);
        return this.mServiceConfigAccessor.updateConfiguration(this.mCurrentUserIdentityInjector.getCurrentUserId(), timeConfiguration, true);
    }

    @Override // com.android.server.timedetector.TimeDetectorInternal
    public boolean setManualTimeForDpm(@android.annotation.NonNull android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion) {
        java.util.Objects.requireNonNull(manualTimeSuggestion);
        return this.mTimeDetectorStrategy.suggestManualTime(this.mCurrentUserIdentityInjector.getCurrentUserId(), manualTimeSuggestion, false);
    }

    @Override // com.android.server.timedetector.TimeDetectorInternal
    public void suggestNetworkTime(@android.annotation.NonNull final com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion) {
        java.util.Objects.requireNonNull(networkTimeSuggestion);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.timedetector.TimeDetectorInternalImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timedetector.TimeDetectorInternalImpl.this.lambda$suggestNetworkTime$0(networkTimeSuggestion);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$suggestNetworkTime$0(com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion) {
        this.mTimeDetectorStrategy.suggestNetworkTime(networkTimeSuggestion);
    }

    @Override // com.android.server.timedetector.TimeDetectorInternal
    public void addNetworkTimeUpdateListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener) {
        java.util.Objects.requireNonNull(stateChangeListener);
        this.mTimeDetectorStrategy.addNetworkTimeUpdateListener(stateChangeListener);
    }

    @Override // com.android.server.timedetector.TimeDetectorInternal
    @android.annotation.NonNull
    public com.android.server.timedetector.NetworkTimeSuggestion getLatestNetworkSuggestion() {
        return this.mTimeDetectorStrategy.getLatestNetworkSuggestion();
    }

    @Override // com.android.server.timedetector.TimeDetectorInternal
    public void suggestGnssTime(@android.annotation.NonNull final com.android.server.timedetector.GnssTimeSuggestion gnssTimeSuggestion) {
        java.util.Objects.requireNonNull(gnssTimeSuggestion);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.timedetector.TimeDetectorInternalImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timedetector.TimeDetectorInternalImpl.this.lambda$suggestGnssTime$1(gnssTimeSuggestion);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$suggestGnssTime$1(com.android.server.timedetector.GnssTimeSuggestion gnssTimeSuggestion) {
        this.mTimeDetectorStrategy.suggestGnssTime(gnssTimeSuggestion);
    }
}
