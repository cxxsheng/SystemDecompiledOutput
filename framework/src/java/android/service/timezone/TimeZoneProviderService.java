package android.service.timezone;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class TimeZoneProviderService extends android.app.Service {
    public static final java.lang.String PRIMARY_LOCATION_TIME_ZONE_PROVIDER_SERVICE_INTERFACE = "android.service.timezone.PrimaryLocationTimeZoneProviderService";
    public static final java.lang.String SECONDARY_LOCATION_TIME_ZONE_PROVIDER_SERVICE_INTERFACE = "android.service.timezone.SecondaryLocationTimeZoneProviderService";
    private static final java.lang.String TAG = "TimeZoneProviderService";
    public static final java.lang.String TEST_COMMAND_RESULT_ERROR_KEY = "ERROR";
    public static final java.lang.String TEST_COMMAND_RESULT_SUCCESS_KEY = "SUCCESS";
    private long mEventFilteringAgeThresholdMillis;
    private android.service.timezone.TimeZoneProviderEvent mLastEventSent;
    private android.service.timezone.ITimeZoneProviderManager mManager;
    private final android.service.timezone.TimeZoneProviderService.TimeZoneProviderServiceWrapper mWrapper = new android.service.timezone.TimeZoneProviderService.TimeZoneProviderServiceWrapper();
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.os.Handler mHandler = com.android.internal.os.BackgroundThread.getHandler();

    public abstract void onStartUpdates(long j);

    public abstract void onStopUpdates();

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return this.mWrapper;
    }

    public final void reportSuggestion(android.service.timezone.TimeZoneProviderSuggestion timeZoneProviderSuggestion) {
        reportSuggestionInternal(timeZoneProviderSuggestion, null);
    }

    public final void reportSuggestion(android.service.timezone.TimeZoneProviderSuggestion timeZoneProviderSuggestion, android.service.timezone.TimeZoneProviderStatus timeZoneProviderStatus) {
        java.util.Objects.requireNonNull(timeZoneProviderStatus);
        reportSuggestionInternal(timeZoneProviderSuggestion, timeZoneProviderStatus);
    }

    private void reportSuggestionInternal(final android.service.timezone.TimeZoneProviderSuggestion timeZoneProviderSuggestion, final android.service.timezone.TimeZoneProviderStatus timeZoneProviderStatus) {
        java.util.Objects.requireNonNull(timeZoneProviderSuggestion);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.timezone.TimeZoneProviderService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.service.timezone.TimeZoneProviderService.this.lambda$reportSuggestionInternal$0(timeZoneProviderSuggestion, timeZoneProviderStatus);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportSuggestionInternal$0(android.service.timezone.TimeZoneProviderSuggestion timeZoneProviderSuggestion, android.service.timezone.TimeZoneProviderStatus timeZoneProviderStatus) {
        synchronized (this.mLock) {
            android.service.timezone.ITimeZoneProviderManager iTimeZoneProviderManager = this.mManager;
            if (iTimeZoneProviderManager != null) {
                try {
                    android.service.timezone.TimeZoneProviderEvent createSuggestionEvent = android.service.timezone.TimeZoneProviderEvent.createSuggestionEvent(android.os.SystemClock.elapsedRealtime(), timeZoneProviderSuggestion, timeZoneProviderStatus);
                    if (shouldSendEvent(createSuggestionEvent)) {
                        iTimeZoneProviderManager.onTimeZoneProviderEvent(createSuggestionEvent);
                        this.mLastEventSent = createSuggestionEvent;
                    }
                } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                    android.util.Log.w(TAG, e);
                }
            }
        }
    }

    public final void reportUncertain() {
        reportUncertainInternal(null);
    }

    public final void reportUncertain(android.service.timezone.TimeZoneProviderStatus timeZoneProviderStatus) {
        java.util.Objects.requireNonNull(timeZoneProviderStatus);
        reportUncertainInternal(timeZoneProviderStatus);
    }

    private void reportUncertainInternal(final android.service.timezone.TimeZoneProviderStatus timeZoneProviderStatus) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.timezone.TimeZoneProviderService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.service.timezone.TimeZoneProviderService.this.lambda$reportUncertainInternal$1(timeZoneProviderStatus);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportUncertainInternal$1(android.service.timezone.TimeZoneProviderStatus timeZoneProviderStatus) {
        synchronized (this.mLock) {
            android.service.timezone.ITimeZoneProviderManager iTimeZoneProviderManager = this.mManager;
            if (iTimeZoneProviderManager != null) {
                try {
                    android.service.timezone.TimeZoneProviderEvent createUncertainEvent = android.service.timezone.TimeZoneProviderEvent.createUncertainEvent(android.os.SystemClock.elapsedRealtime(), timeZoneProviderStatus);
                    if (shouldSendEvent(createUncertainEvent)) {
                        iTimeZoneProviderManager.onTimeZoneProviderEvent(createUncertainEvent);
                        this.mLastEventSent = createUncertainEvent;
                    }
                } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                    android.util.Log.w(TAG, e);
                }
            }
        }
    }

    public final void reportPermanentFailure(final java.lang.Throwable th) {
        java.util.Objects.requireNonNull(th);
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.timezone.TimeZoneProviderService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                android.service.timezone.TimeZoneProviderService.this.lambda$reportPermanentFailure$2(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportPermanentFailure$2(java.lang.Throwable th) {
        synchronized (this.mLock) {
            android.service.timezone.ITimeZoneProviderManager iTimeZoneProviderManager = this.mManager;
            if (iTimeZoneProviderManager != null) {
                try {
                    android.service.timezone.TimeZoneProviderEvent createPermanentFailureEvent = android.service.timezone.TimeZoneProviderEvent.createPermanentFailureEvent(android.os.SystemClock.elapsedRealtime(), th.getMessage());
                    if (shouldSendEvent(createPermanentFailureEvent)) {
                        iTimeZoneProviderManager.onTimeZoneProviderEvent(createPermanentFailureEvent);
                        this.mLastEventSent = createPermanentFailureEvent;
                    }
                } catch (android.os.RemoteException | java.lang.RuntimeException e) {
                    android.util.Log.w(TAG, e);
                }
            }
        }
    }

    private boolean shouldSendEvent(android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) {
        return !timeZoneProviderEvent.isEquivalentTo(this.mLastEventSent) || timeZoneProviderEvent.getCreationElapsedMillis() - this.mLastEventSent.getCreationElapsedMillis() > this.mEventFilteringAgeThresholdMillis;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStartUpdatesInternal(android.service.timezone.ITimeZoneProviderManager iTimeZoneProviderManager, long j, long j2) {
        synchronized (this.mLock) {
            this.mManager = iTimeZoneProviderManager;
            this.mEventFilteringAgeThresholdMillis = j2;
            this.mLastEventSent = null;
            onStartUpdates(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStopUpdatesInternal() {
        synchronized (this.mLock) {
            onStopUpdates();
            this.mManager = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        synchronized (this.mLock) {
            printWriter.append((java.lang.CharSequence) ("mLastEventSent=" + this.mLastEventSent));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class TimeZoneProviderServiceWrapper extends android.service.timezone.ITimeZoneProvider.Stub {
        private TimeZoneProviderServiceWrapper() {
        }

        @Override // android.service.timezone.ITimeZoneProvider
        public void startUpdates(final android.service.timezone.ITimeZoneProviderManager iTimeZoneProviderManager, final long j, final long j2) {
            java.util.Objects.requireNonNull(iTimeZoneProviderManager);
            android.service.timezone.TimeZoneProviderService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.timezone.TimeZoneProviderService$TimeZoneProviderServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.timezone.TimeZoneProviderService.TimeZoneProviderServiceWrapper.this.lambda$startUpdates$0(iTimeZoneProviderManager, j, j2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startUpdates$0(android.service.timezone.ITimeZoneProviderManager iTimeZoneProviderManager, long j, long j2) {
            android.service.timezone.TimeZoneProviderService.this.onStartUpdatesInternal(iTimeZoneProviderManager, j, j2);
        }

        @Override // android.service.timezone.ITimeZoneProvider
        public void stopUpdates() {
            android.os.Handler handler = android.service.timezone.TimeZoneProviderService.this.mHandler;
            final android.service.timezone.TimeZoneProviderService timeZoneProviderService = android.service.timezone.TimeZoneProviderService.this;
            handler.post(new java.lang.Runnable() { // from class: android.service.timezone.TimeZoneProviderService$TimeZoneProviderServiceWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.timezone.TimeZoneProviderService.this.onStopUpdatesInternal();
                }
            });
        }
    }
}
