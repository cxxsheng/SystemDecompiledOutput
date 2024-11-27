package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
abstract class LocationTimeZoneProvider implements com.android.server.timezonedetector.Dumpable {

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.location.ThreadingDomain.SingleRunnableQueue mInitializationTimeoutQueue;
    com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderListener mProviderListener;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderMetricsLogger mProviderMetricsLogger;

    @android.annotation.NonNull
    final java.lang.String mProviderName;
    private final boolean mRecordStateChanges;

    @android.annotation.NonNull
    final java.lang.Object mSharedLock;

    @android.annotation.NonNull
    final com.android.server.timezonedetector.location.ThreadingDomain mThreadingDomain;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.location.TimeZoneProviderEventPreProcessor mTimeZoneProviderEventPreProcessor;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private final java.util.ArrayList<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> mRecordedStates = new java.util.ArrayList<>(0);

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    final com.android.server.timezonedetector.ReferenceWithHistory<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> mCurrentState = new com.android.server.timezonedetector.ReferenceWithHistory<>(10);

    interface ProviderListener {
        void onProviderStateChange(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState);
    }

    interface ProviderMetricsLogger {
        void onProviderStateChanged(int i);
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    abstract void onDestroy();

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    abstract boolean onInitialize();

    abstract void onStartUpdates(@android.annotation.NonNull java.time.Duration duration, @android.annotation.NonNull java.time.Duration duration2);

    abstract void onStopUpdates();

    static class ProviderState {
        static final int PROVIDER_STATE_DESTROYED = 6;
        static final int PROVIDER_STATE_PERM_FAILED = 5;
        static final int PROVIDER_STATE_STARTED_CERTAIN = 2;
        static final int PROVIDER_STATE_STARTED_INITIALIZING = 1;
        static final int PROVIDER_STATE_STARTED_UNCERTAIN = 3;
        static final int PROVIDER_STATE_STOPPED = 4;
        static final int PROVIDER_STATE_UNKNOWN = 0;

        @android.annotation.Nullable
        public final com.android.server.timezonedetector.ConfigurationInternal currentUserConfiguration;

        @android.annotation.Nullable
        public final android.service.timezone.TimeZoneProviderEvent event;

        @android.annotation.Nullable
        private final java.lang.String mDebugInfo;
        private final long mStateEntryTimeMillis;

        @android.annotation.NonNull
        public final com.android.server.timezonedetector.location.LocationTimeZoneProvider provider;
        public final int stateEnum;

        @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE, java.lang.annotation.ElementType.TYPE_PARAMETER})
        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface ProviderStateEnum {
        }

        private ProviderState(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider, int i, @android.annotation.Nullable android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent, @android.annotation.Nullable com.android.server.timezonedetector.ConfigurationInternal configurationInternal, @android.annotation.Nullable java.lang.String str) {
            java.util.Objects.requireNonNull(locationTimeZoneProvider);
            this.provider = locationTimeZoneProvider;
            this.stateEnum = i;
            this.event = timeZoneProviderEvent;
            this.currentUserConfiguration = configurationInternal;
            this.mStateEntryTimeMillis = android.os.SystemClock.elapsedRealtime();
            this.mDebugInfo = str;
        }

        static com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState createStartingState(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider) {
            return new com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState(locationTimeZoneProvider, 0, null, null, "Initial state");
        }

        com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState newState(int i, @android.annotation.Nullable android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent, @android.annotation.Nullable com.android.server.timezonedetector.ConfigurationInternal configurationInternal, @android.annotation.Nullable java.lang.String str) {
            switch (this.stateEnum) {
                case 0:
                    if (i != 4) {
                        throw new java.lang.IllegalArgumentException("Must transition from " + prettyPrintStateEnum(0) + " to " + prettyPrintStateEnum(4));
                    }
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                    break;
                case 5:
                case 6:
                    throw new java.lang.IllegalArgumentException("Illegal transition out of " + prettyPrintStateEnum(this.stateEnum));
                default:
                    throw new java.lang.IllegalArgumentException("Invalid this.stateEnum=" + this.stateEnum);
            }
            switch (i) {
                case 0:
                    throw new java.lang.IllegalArgumentException("Cannot transition to " + prettyPrintStateEnum(0));
                case 1:
                case 2:
                case 3:
                    if (configurationInternal == null) {
                        throw new java.lang.IllegalArgumentException("Started state: currentUserConfig must not be null");
                    }
                    break;
                case 4:
                    if (timeZoneProviderEvent != null || configurationInternal != null) {
                        throw new java.lang.IllegalArgumentException("Stopped state: event and currentUserConfig must be null, event=" + timeZoneProviderEvent + ", currentUserConfig=" + configurationInternal);
                    }
                case 5:
                case 6:
                    if (timeZoneProviderEvent != null || configurationInternal != null) {
                        throw new java.lang.IllegalArgumentException("Terminal state: event and currentUserConfig must be null, newStateEnum=" + i + ", event=" + timeZoneProviderEvent + ", currentUserConfig=" + configurationInternal);
                    }
                default:
                    throw new java.lang.IllegalArgumentException("Unknown newStateEnum=" + i);
            }
            return new com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState(this.provider, i, timeZoneProviderEvent, configurationInternal, str);
        }

        boolean isStarted() {
            return this.stateEnum == 1 || this.stateEnum == 2 || this.stateEnum == 3;
        }

        boolean isTerminated() {
            return this.stateEnum == 5 || this.stateEnum == 6;
        }

        public int getProviderStatus() {
            switch (this.stateEnum) {
                case 1:
                    return 2;
                case 2:
                    return 3;
                case 3:
                    return 4;
                case 4:
                case 6:
                    return 2;
                case 5:
                    return 1;
                default:
                    throw new java.lang.IllegalStateException("Unknown state enum:" + prettyPrintStateEnum(this.stateEnum));
            }
        }

        @android.annotation.Nullable
        android.service.timezone.TimeZoneProviderStatus getReportedStatus() {
            if (this.event == null) {
                return null;
            }
            return this.event.getTimeZoneProviderStatus();
        }

        public java.lang.String toString() {
            return "ProviderState{stateEnum=" + prettyPrintStateEnum(this.stateEnum) + ", event=" + this.event + ", currentUserConfiguration=" + this.currentUserConfiguration + ", mStateEntryTimeMillis=" + this.mStateEntryTimeMillis + ", mDebugInfo=" + this.mDebugInfo + '}';
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState = (com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState) obj;
            if (this.stateEnum == providerState.stateEnum && java.util.Objects.equals(this.event, providerState.event) && java.util.Objects.equals(this.currentUserConfiguration, providerState.currentUserConfiguration)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.stateEnum), this.event, this.currentUserConfiguration);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static java.lang.String prettyPrintStateEnum(int i) {
            switch (i) {
                case 1:
                    return "Started initializing (1)";
                case 2:
                    return "Started certain (2)";
                case 3:
                    return "Started uncertain (3)";
                case 4:
                    return "Stopped (4)";
                case 5:
                    return "Perm failure (5)";
                case 6:
                    return "Destroyed (6)";
                default:
                    return "Unknown (" + i + ")";
            }
        }
    }

    LocationTimeZoneProvider(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderMetricsLogger providerMetricsLogger, @android.annotation.NonNull com.android.server.timezonedetector.location.ThreadingDomain threadingDomain, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.timezonedetector.location.TimeZoneProviderEventPreProcessor timeZoneProviderEventPreProcessor, boolean z) {
        java.util.Objects.requireNonNull(threadingDomain);
        this.mThreadingDomain = threadingDomain;
        java.util.Objects.requireNonNull(providerMetricsLogger);
        this.mProviderMetricsLogger = providerMetricsLogger;
        this.mInitializationTimeoutQueue = threadingDomain.createSingleRunnableQueue();
        this.mSharedLock = threadingDomain.getLockObject();
        java.util.Objects.requireNonNull(str);
        this.mProviderName = str;
        java.util.Objects.requireNonNull(timeZoneProviderEventPreProcessor);
        this.mTimeZoneProviderEventPreProcessor = timeZoneProviderEventPreProcessor;
        this.mRecordStateChanges = z;
    }

    final void initialize(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderListener providerListener) {
        java.lang.String str;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            if (this.mProviderListener != null) {
                throw new java.lang.IllegalStateException("initialize already called");
            }
            java.util.Objects.requireNonNull(providerListener);
            this.mProviderListener = providerListener;
            com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState newState = com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState.createStartingState(this).newState(4, null, null, "initialize");
            boolean z = false;
            setCurrentState(newState, false);
            try {
                str = "onInitialize() returned false";
                z = onInitialize();
            } catch (java.lang.RuntimeException e) {
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog("Unable to initialize the provider due to exception", e);
                str = "onInitialize() threw exception:" + e.getMessage();
            }
            if (!z) {
                setCurrentState(newState.newState(5, null, null, "Failed to initialize: " + str), true);
            }
        }
    }

    final void destroy() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState = this.mCurrentState.get();
                if (!providerState.isTerminated()) {
                    setCurrentState(providerState.newState(6, null, null, "destroy"), false);
                    onDestroy();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    final void clearRecordedStates() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            this.mRecordedStates.clear();
            this.mRecordedStates.trimToSize();
        }
    }

    final java.util.List<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> getRecordedStates() {
        java.util.ArrayList arrayList;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            arrayList = new java.util.ArrayList(this.mRecordedStates);
        }
        return arrayList;
    }

    private void setCurrentState(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState, boolean z) {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState2 = this.mCurrentState.get();
                this.mCurrentState.set(providerState);
                onSetCurrentState(providerState);
                if (!java.util.Objects.equals(providerState, providerState2)) {
                    this.mProviderMetricsLogger.onProviderStateChanged(providerState.stateEnum);
                    if (this.mRecordStateChanges) {
                        this.mRecordedStates.add(providerState);
                    }
                    if (z) {
                        this.mProviderListener.onProviderStateChange(providerState);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    void onSetCurrentState(com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState) {
    }

    @android.annotation.NonNull
    final com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState getCurrentState() {
        com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            providerState = this.mCurrentState.get();
        }
        return providerState;
    }

    final java.lang.String getName() {
        this.mThreadingDomain.assertCurrentThread();
        return this.mProviderName;
    }

    final void startUpdates(@android.annotation.NonNull com.android.server.timezonedetector.ConfigurationInternal configurationInternal, @android.annotation.NonNull java.time.Duration duration, @android.annotation.NonNull java.time.Duration duration2, @android.annotation.NonNull java.time.Duration duration3) {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            assertCurrentState(4);
            setCurrentState(this.mCurrentState.get().newState(1, null, configurationInternal, "startUpdates"), false);
            this.mInitializationTimeoutQueue.runDelayed(new java.lang.Runnable() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneProvider$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.timezonedetector.location.LocationTimeZoneProvider.this.handleInitializationTimeout();
                }
            }, duration.plus(duration2).toMillis());
            onStartUpdates(duration, duration3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInitializationTimeout() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState = this.mCurrentState.get();
                if (providerState.stateEnum == 1) {
                    setCurrentState(providerState.newState(3, null, providerState.currentUserConfiguration, "handleInitializationTimeout"), true);
                } else {
                    com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog("handleInitializationTimeout: Initialization timeout triggered when in an unexpected state=" + providerState);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    final void stopUpdates() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            assertIsStarted();
            setCurrentState(this.mCurrentState.get().newState(4, null, null, "stopUpdates"), false);
            cancelInitializationTimeoutIfSet();
            onStopUpdates();
        }
    }

    final void handleTimeZoneProviderEvent(@android.annotation.NonNull android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) {
        this.mThreadingDomain.assertCurrentThread();
        java.util.Objects.requireNonNull(timeZoneProviderEvent);
        android.service.timezone.TimeZoneProviderEvent preProcess = this.mTimeZoneProviderEventPreProcessor.preProcess(timeZoneProviderEvent);
        synchronized (this.mSharedLock) {
            try {
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("handleTimeZoneProviderEvent: mProviderName=" + this.mProviderName + ", timeZoneProviderEvent=" + preProcess);
                com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState = this.mCurrentState.get();
                int type = preProcess.getType();
                switch (providerState.stateEnum) {
                    case 1:
                    case 2:
                    case 3:
                        switch (type) {
                            case 1:
                                java.lang.String str = "handleTimeZoneProviderEvent: Failure event=" + preProcess + " received for provider=" + this.mProviderName + " in state=" + com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState.prettyPrintStateEnum(providerState.stateEnum) + ", entering permanently failed state";
                                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog(str);
                                setCurrentState(providerState.newState(5, null, null, str), true);
                                cancelInitializationTimeoutIfSet();
                                return;
                            case 2:
                            case 3:
                                int i = 3;
                                if (type != 3) {
                                    i = 2;
                                }
                                setCurrentState(providerState.newState(i, preProcess, providerState.currentUserConfiguration, "handleTimeZoneProviderEvent"), true);
                                cancelInitializationTimeoutIfSet();
                                return;
                            default:
                                throw new java.lang.IllegalStateException("Unknown eventType=" + preProcess);
                        }
                    case 4:
                        switch (type) {
                            case 1:
                                java.lang.String str2 = "handleTimeZoneProviderEvent: Failure event=" + preProcess + " received for stopped provider=" + this.mProviderName + ", entering permanently failed state";
                                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog(str2);
                                setCurrentState(providerState.newState(5, null, null, str2), true);
                                cancelInitializationTimeoutIfSet();
                                return;
                            case 2:
                            case 3:
                                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog("handleTimeZoneProviderEvent: event=" + preProcess + " received for stopped provider=" + this + ", ignoring");
                                return;
                            default:
                                throw new java.lang.IllegalStateException("Unknown eventType=" + preProcess);
                        }
                    case 5:
                    case 6:
                        com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog("handleTimeZoneProviderEvent: Event=" + preProcess + " received for provider=" + this + " when in terminated state");
                        return;
                    default:
                        throw new java.lang.IllegalStateException("Unknown providerType=" + providerState);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    final void handleTemporaryFailure(java.lang.String str) {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState = this.mCurrentState.get();
                switch (providerState.stateEnum) {
                    case 1:
                    case 2:
                    case 3:
                        setCurrentState(providerState.newState(3, null, providerState.currentUserConfiguration, "handleTemporaryFailure: reason=" + str + ", currentState=" + com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState.prettyPrintStateEnum(providerState.stateEnum)), true);
                        cancelInitializationTimeoutIfSet();
                        break;
                    case 4:
                        com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("handleProviderLost reason=" + str + ", mProviderName=" + this.mProviderName + ", currentState=" + providerState + ": No state change required, provider is stopped.");
                        break;
                    case 5:
                    case 6:
                        com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("handleProviderLost reason=" + str + ", mProviderName=" + this.mProviderName + ", currentState=" + providerState + ": No state change required, provider is terminated.");
                        break;
                    default:
                        throw new java.lang.IllegalStateException("Unknown currentState=" + providerState);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void assertIsStarted() {
        com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState = this.mCurrentState.get();
        if (!providerState.isStarted()) {
            throw new java.lang.IllegalStateException("Required a started state, but was " + providerState);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void assertCurrentState(int i) {
        com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState = this.mCurrentState.get();
        if (providerState.stateEnum != i) {
            throw new java.lang.IllegalStateException("Required stateEnum=" + i + ", but was " + providerState);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isInitializationTimeoutSet() {
        boolean hasQueued;
        synchronized (this.mSharedLock) {
            hasQueued = this.mInitializationTimeoutQueue.hasQueued();
        }
        return hasQueued;
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void cancelInitializationTimeoutIfSet() {
        if (this.mInitializationTimeoutQueue.hasQueued()) {
            this.mInitializationTimeoutQueue.cancel();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.time.Duration getInitializationTimeoutDelay() {
        java.time.Duration ofMillis;
        synchronized (this.mSharedLock) {
            ofMillis = java.time.Duration.ofMillis(this.mInitializationTimeoutQueue.getQueuedDelayMillis());
        }
        return ofMillis;
    }
}
