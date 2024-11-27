package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
class LocationTimeZoneProviderController implements com.android.server.timezonedetector.Dumpable {
    static final java.lang.String STATE_CERTAIN = "CERTAIN";
    static final java.lang.String STATE_DESTROYED = "DESTROYED";
    static final java.lang.String STATE_FAILED = "FAILED";
    static final java.lang.String STATE_INITIALIZING = "INITIALIZING";
    static final java.lang.String STATE_PROVIDERS_INITIALIZING = "PROVIDERS_INITIALIZING";
    static final java.lang.String STATE_STOPPED = "STOPPED";
    static final java.lang.String STATE_UNCERTAIN = "UNCERTAIN";
    static final java.lang.String STATE_UNKNOWN = "UNKNOWN";

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Callback mCallback;

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private com.android.server.timezonedetector.ConfigurationInternal mCurrentUserConfiguration;

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Environment mEnvironment;

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    @android.annotation.Nullable
    private com.android.server.timezonedetector.LocationAlgorithmEvent mLastEvent;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.location.LocationTimeZoneProviderController.MetricsLogger mMetricsLogger;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.location.LocationTimeZoneProvider mPrimaryProvider;
    private final boolean mRecordStateChanges;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.location.LocationTimeZoneProvider mSecondaryProvider;

    @android.annotation.NonNull
    private final java.lang.Object mSharedLock;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.location.ThreadingDomain mThreadingDomain;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.location.ThreadingDomain.SingleRunnableQueue mUncertaintyTimeoutQueue;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private final java.util.ArrayList<java.lang.String> mRecordedStates = new java.util.ArrayList<>(0);

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private final com.android.server.timezonedetector.ReferenceWithHistory<java.lang.String> mState = new com.android.server.timezonedetector.ReferenceWithHistory<>(10);

    interface MetricsLogger {
        void onStateChange(java.lang.String str);
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE, java.lang.annotation.ElementType.TYPE_PARAMETER})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface State {
    }

    LocationTimeZoneProviderController(@android.annotation.NonNull com.android.server.timezonedetector.location.ThreadingDomain threadingDomain, @android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProviderController.MetricsLogger metricsLogger, @android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider, @android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider2, boolean z) {
        java.util.Objects.requireNonNull(threadingDomain);
        this.mThreadingDomain = threadingDomain;
        this.mSharedLock = threadingDomain.getLockObject();
        this.mUncertaintyTimeoutQueue = threadingDomain.createSingleRunnableQueue();
        java.util.Objects.requireNonNull(metricsLogger);
        this.mMetricsLogger = metricsLogger;
        java.util.Objects.requireNonNull(locationTimeZoneProvider);
        this.mPrimaryProvider = locationTimeZoneProvider;
        java.util.Objects.requireNonNull(locationTimeZoneProvider2);
        this.mSecondaryProvider = locationTimeZoneProvider2;
        this.mRecordStateChanges = z;
        synchronized (this.mSharedLock) {
            this.mState.set(STATE_UNKNOWN);
        }
    }

    void initialize(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Environment environment, @android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Callback callback) {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("initialize()");
            java.util.Objects.requireNonNull(environment);
            com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Environment environment2 = environment;
            this.mEnvironment = environment;
            java.util.Objects.requireNonNull(callback);
            com.android.server.timezonedetector.location.LocationTimeZoneProviderController.Callback callback2 = callback;
            this.mCallback = callback;
            this.mCurrentUserConfiguration = environment.getCurrentUserConfigurationInternal();
            com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderListener providerListener = new com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderListener() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneProviderController$$ExternalSyntheticLambda0
                @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderListener
                public final void onProviderStateChange(com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState) {
                    com.android.server.timezonedetector.location.LocationTimeZoneProviderController.this.onProviderStateChange(providerState);
                }
            };
            setState(STATE_PROVIDERS_INITIALIZING);
            this.mPrimaryProvider.initialize(providerListener);
            this.mSecondaryProvider.initialize(providerListener);
            setStateAndReportStatusOnlyEvent(STATE_STOPPED, "initialize()");
            alterProvidersStartedStateIfRequired(null, this.mCurrentUserConfiguration);
        }
    }

    void onConfigurationInternalChanged() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("onConfigChanged()");
                com.android.server.timezonedetector.ConfigurationInternal configurationInternal = this.mCurrentUserConfiguration;
                com.android.server.timezonedetector.ConfigurationInternal currentUserConfigurationInternal = this.mEnvironment.getCurrentUserConfigurationInternal();
                this.mCurrentUserConfiguration = currentUserConfigurationInternal;
                if (!currentUserConfigurationInternal.equals(configurationInternal)) {
                    if (currentUserConfigurationInternal.getUserId() != configurationInternal.getUserId()) {
                        java.lang.String str = "User changed. old=" + configurationInternal.getUserId() + ", new=" + currentUserConfigurationInternal.getUserId();
                        com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("Stopping providers: " + str);
                        stopProviders(str);
                        alterProvidersStartedStateIfRequired(null, currentUserConfigurationInternal);
                    } else {
                        alterProvidersStartedStateIfRequired(configurationInternal, currentUserConfigurationInternal);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isUncertaintyTimeoutSet() {
        return this.mUncertaintyTimeoutQueue.hasQueued();
    }

    @com.android.internal.annotations.VisibleForTesting
    long getUncertaintyTimeoutDelayMillis() {
        return this.mUncertaintyTimeoutQueue.getQueuedDelayMillis();
    }

    void destroy() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            stopProviders("destroy()");
            this.mPrimaryProvider.destroy();
            this.mSecondaryProvider.destroy();
            setStateAndReportStatusOnlyEvent(STATE_DESTROYED, "destroy()");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void setStateAndReportStatusOnlyEvent(java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        setState(str);
        com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent = new com.android.server.timezonedetector.LocationAlgorithmEvent(generateCurrentAlgorithmStatus(), null);
        locationAlgorithmEvent.addDebugInfo(str2);
        reportEvent(locationAlgorithmEvent);
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void reportSuggestionEvent(@android.annotation.NonNull com.android.server.timezonedetector.GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion, @android.annotation.NonNull java.lang.String str) {
        com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent = new com.android.server.timezonedetector.LocationAlgorithmEvent(generateCurrentAlgorithmStatus(), geolocationTimeZoneSuggestion);
        locationAlgorithmEvent.addDebugInfo(str);
        reportEvent(locationAlgorithmEvent);
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void reportEvent(@android.annotation.NonNull com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent) {
        com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("makeSuggestion: suggestion=" + locationAlgorithmEvent);
        this.mCallback.sendEvent(locationAlgorithmEvent);
        this.mLastEvent = locationAlgorithmEvent;
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void setState(java.lang.String str) {
        if (!java.util.Objects.equals(this.mState.get(), str)) {
            this.mState.set(str);
            if (this.mRecordStateChanges) {
                this.mRecordedStates.add(str);
            }
            this.mMetricsLogger.onStateChange(str);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void stopProviders(@android.annotation.NonNull java.lang.String str) {
        stopProviderIfStarted(this.mPrimaryProvider);
        stopProviderIfStarted(this.mSecondaryProvider);
        cancelUncertaintyTimeout();
        setStateAndReportStatusOnlyEvent(STATE_STOPPED, "Providers stopped: " + str);
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void stopProviderIfStarted(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider) {
        if (locationTimeZoneProvider.getCurrentState().isStarted()) {
            stopProvider(locationTimeZoneProvider);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void stopProvider(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider) {
        switch (locationTimeZoneProvider.getCurrentState().stateEnum) {
            case 1:
            case 2:
            case 3:
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("Stopping " + locationTimeZoneProvider);
                locationTimeZoneProvider.stopUpdates();
                break;
            case 4:
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("No need to stop " + locationTimeZoneProvider + ": already stopped");
                break;
            case 5:
            case 6:
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("Unable to stop " + locationTimeZoneProvider + ": it is terminated.");
                break;
            default:
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog("Unknown provider state: " + locationTimeZoneProvider);
                break;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void alterProvidersStartedStateIfRequired(@android.annotation.Nullable com.android.server.timezonedetector.ConfigurationInternal configurationInternal, @android.annotation.NonNull com.android.server.timezonedetector.ConfigurationInternal configurationInternal2) {
        boolean z = configurationInternal != null && configurationInternal.isGeoDetectionExecutionEnabled();
        boolean isGeoDetectionExecutionEnabled = configurationInternal2.isGeoDetectionExecutionEnabled();
        if (z == isGeoDetectionExecutionEnabled) {
            return;
        }
        if (isGeoDetectionExecutionEnabled) {
            setStateAndReportStatusOnlyEvent(STATE_INITIALIZING, "initializing()");
            tryStartProvider(this.mPrimaryProvider, configurationInternal2);
            if (!this.mPrimaryProvider.getCurrentState().isStarted()) {
                tryStartProvider(this.mSecondaryProvider, configurationInternal2);
                if (!this.mSecondaryProvider.getCurrentState().isStarted()) {
                    setStateAndReportStatusOnlyEvent(STATE_FAILED, "Providers are failed: primary=" + this.mPrimaryProvider.getCurrentState() + " secondary=" + this.mPrimaryProvider.getCurrentState());
                    return;
                }
                return;
            }
            return;
        }
        stopProviders("Geo detection behavior disabled");
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void tryStartProvider(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider, @android.annotation.NonNull com.android.server.timezonedetector.ConfigurationInternal configurationInternal) {
        switch (locationTimeZoneProvider.getCurrentState().stateEnum) {
            case 1:
            case 2:
            case 3:
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("No need to start " + locationTimeZoneProvider + ": already started");
                return;
            case 4:
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("Enabling " + locationTimeZoneProvider);
                locationTimeZoneProvider.startUpdates(configurationInternal, this.mEnvironment.getProviderInitializationTimeout(), this.mEnvironment.getProviderInitializationTimeoutFuzz(), this.mEnvironment.getProviderEventFilteringAgeThreshold());
                return;
            case 5:
            case 6:
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("Unable to start " + locationTimeZoneProvider + ": it is terminated");
                return;
            default:
                throw new java.lang.IllegalStateException("Unknown provider state: provider=" + locationTimeZoneProvider);
        }
    }

    void onProviderStateChange(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState) {
        this.mThreadingDomain.assertCurrentThread();
        com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider = providerState.provider;
        assertProviderKnown(locationTimeZoneProvider);
        synchronized (this.mSharedLock) {
            try {
                if (java.util.Objects.equals(this.mState.get(), STATE_PROVIDERS_INITIALIZING)) {
                    com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog("onProviderStateChange: Ignoring provider state change because both providers have not yet completed initialization. providerState=" + providerState);
                    return;
                }
                switch (providerState.stateEnum) {
                    case 1:
                    case 4:
                    case 6:
                        com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog("onProviderStateChange: Unexpected state change for provider, provider=" + locationTimeZoneProvider);
                        break;
                    case 2:
                    case 3:
                        com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("onProviderStateChange: Received notification of a state change while started, provider=" + locationTimeZoneProvider);
                        handleProviderStartedStateChange(providerState);
                        break;
                    case 5:
                        com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("Received notification of permanent failure for provider=" + locationTimeZoneProvider);
                        handleProviderFailedStateChange(providerState);
                        break;
                    default:
                        com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog("onProviderStateChange: Unexpected provider=" + locationTimeZoneProvider);
                        break;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void assertProviderKnown(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider) {
        if (locationTimeZoneProvider != this.mPrimaryProvider && locationTimeZoneProvider != this.mSecondaryProvider) {
            throw new java.lang.IllegalArgumentException("Unknown provider: " + locationTimeZoneProvider);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void handleProviderFailedStateChange(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState) {
        com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider = providerState.provider;
        com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState currentState = this.mPrimaryProvider.getCurrentState();
        com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState currentState2 = this.mSecondaryProvider.getCurrentState();
        if (locationTimeZoneProvider == this.mPrimaryProvider) {
            if (!currentState2.isTerminated()) {
                tryStartProvider(this.mSecondaryProvider, this.mCurrentUserConfiguration);
            }
        } else if (locationTimeZoneProvider == this.mSecondaryProvider && currentState.stateEnum != 3 && !currentState.isTerminated()) {
            com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog("Secondary provider unexpected reported a failure: failed provider=" + locationTimeZoneProvider.getName() + ", primary provider=" + this.mPrimaryProvider + ", secondary provider=" + this.mSecondaryProvider);
        }
        if (currentState.isTerminated() && currentState2.isTerminated()) {
            cancelUncertaintyTimeout();
            setStateAndReportStatusOnlyEvent(STATE_FAILED, "Both providers are terminated: primary=" + currentState.provider + ", secondary=" + currentState2.provider);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void handleProviderStartedStateChange(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState) {
        com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider = providerState.provider;
        android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent = providerState.event;
        if (timeZoneProviderEvent == null) {
            handleProviderUncertainty(locationTimeZoneProvider, this.mEnvironment.elapsedRealtimeMillis(), "provider=" + locationTimeZoneProvider + ", implicit uncertainty, event=null");
        }
        if (!this.mCurrentUserConfiguration.isGeoDetectionExecutionEnabled()) {
            com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog("Provider=" + locationTimeZoneProvider + " is started, but currentUserConfiguration=" + this.mCurrentUserConfiguration + " suggests it shouldn't be.");
        }
        switch (timeZoneProviderEvent.getType()) {
            case 1:
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog("Provider=" + locationTimeZoneProvider + " is started, but event suggests it shouldn't be");
                break;
            case 2:
                handleProviderSuggestion(locationTimeZoneProvider, timeZoneProviderEvent);
                break;
            case 3:
                handleProviderUncertainty(locationTimeZoneProvider, timeZoneProviderEvent.getCreationElapsedMillis(), "provider=" + locationTimeZoneProvider + ", explicit uncertainty. event=" + timeZoneProviderEvent);
                break;
            default:
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.warnLog("Unknown eventType=" + timeZoneProviderEvent.getType());
                break;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void handleProviderSuggestion(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider, @android.annotation.NonNull android.service.timezone.TimeZoneProviderEvent timeZoneProviderEvent) {
        cancelUncertaintyTimeout();
        if (locationTimeZoneProvider == this.mPrimaryProvider) {
            stopProviderIfStarted(this.mSecondaryProvider);
        }
        android.service.timezone.TimeZoneProviderSuggestion suggestion = timeZoneProviderEvent.getSuggestion();
        setState(STATE_CERTAIN);
        reportSuggestionEvent(com.android.server.timezonedetector.GeolocationTimeZoneSuggestion.createCertainSuggestion(suggestion.getElapsedRealtimeMillis(), suggestion.getTimeZoneIds()), "Provider event received: provider=" + locationTimeZoneProvider + ", providerEvent=" + timeZoneProviderEvent + ", suggestionCreationTime=" + this.mEnvironment.elapsedRealtimeMillis());
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public void dump(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        synchronized (this.mSharedLock) {
            indentingPrintWriter.println("LocationTimeZoneProviderController:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("mCurrentUserConfiguration=" + this.mCurrentUserConfiguration);
            indentingPrintWriter.println("providerInitializationTimeout=" + this.mEnvironment.getProviderInitializationTimeout());
            indentingPrintWriter.println("providerInitializationTimeoutFuzz=" + this.mEnvironment.getProviderInitializationTimeoutFuzz());
            indentingPrintWriter.println("uncertaintyDelay=" + this.mEnvironment.getUncertaintyDelay());
            indentingPrintWriter.println("mState=" + this.mState.get());
            indentingPrintWriter.println("mLastEvent=" + this.mLastEvent);
            indentingPrintWriter.println("State history:");
            indentingPrintWriter.increaseIndent();
            this.mState.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Primary Provider:");
            indentingPrintWriter.increaseIndent();
            this.mPrimaryProvider.dump(indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Secondary Provider:");
            indentingPrintWriter.increaseIndent();
            this.mSecondaryProvider.dump(indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private void cancelUncertaintyTimeout() {
        this.mUncertaintyTimeoutQueue.cancel();
    }

    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    void handleProviderUncertainty(@android.annotation.NonNull final com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider, final long j, @android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(locationTimeZoneProvider);
        if (!this.mUncertaintyTimeoutQueue.hasQueued()) {
            if (STATE_UNCERTAIN.equals(this.mState.get())) {
                reportSuggestionEvent(com.android.server.timezonedetector.GeolocationTimeZoneSuggestion.createUncertainSuggestion(j), "Uncertainty received from " + locationTimeZoneProvider.getName() + ": primary=" + this.mPrimaryProvider + ", secondary=" + this.mSecondaryProvider + ", uncertaintyStarted=" + java.time.Duration.ofMillis(j));
            } else {
                com.android.server.timezonedetector.location.LocationTimeZoneManagerService.debugLog("Starting uncertainty timeout: reason=" + str);
                final java.time.Duration uncertaintyDelay = this.mEnvironment.getUncertaintyDelay();
                this.mUncertaintyTimeoutQueue.runDelayed(new java.lang.Runnable() { // from class: com.android.server.timezonedetector.location.LocationTimeZoneProviderController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.timezonedetector.location.LocationTimeZoneProviderController.this.lambda$handleProviderUncertainty$0(locationTimeZoneProvider, j, uncertaintyDelay);
                    }
                }, uncertaintyDelay.toMillis());
            }
        }
        if (locationTimeZoneProvider == this.mPrimaryProvider) {
            tryStartProvider(this.mSecondaryProvider, this.mCurrentUserConfiguration);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onProviderUncertaintyTimeout, reason: merged with bridge method [inline-methods] */
    public void lambda$handleProviderUncertainty$0(@android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider locationTimeZoneProvider, long j, @android.annotation.NonNull java.time.Duration duration) {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            long elapsedRealtimeMillis = this.mEnvironment.elapsedRealtimeMillis();
            setState(STATE_UNCERTAIN);
            reportSuggestionEvent(com.android.server.timezonedetector.GeolocationTimeZoneSuggestion.createUncertainSuggestion(j), "Uncertainty timeout triggered for " + locationTimeZoneProvider.getName() + ": primary=" + this.mPrimaryProvider + ", secondary=" + this.mSecondaryProvider + ", uncertaintyStarted=" + java.time.Duration.ofMillis(j) + ", afterUncertaintyTimeout=" + java.time.Duration.ofMillis(elapsedRealtimeMillis) + ", uncertaintyDelay=" + duration);
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mSharedLock"})
    private android.app.time.LocationTimeZoneAlgorithmStatus generateCurrentAlgorithmStatus() {
        return createAlgorithmStatus(this.mState.get(), this.mPrimaryProvider.getCurrentState(), this.mSecondaryProvider.getCurrentState());
    }

    @android.annotation.NonNull
    private static android.app.time.LocationTimeZoneAlgorithmStatus createAlgorithmStatus(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState, @android.annotation.NonNull com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState2) {
        return new android.app.time.LocationTimeZoneAlgorithmStatus(mapControllerStateToDetectionAlgorithmStatus(str), providerState.getProviderStatus(), providerState.getReportedStatus(), providerState2.getProviderStatus(), providerState2.getReportedStatus());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int mapControllerStateToDetectionAlgorithmStatus(@android.annotation.NonNull java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1166336595:
                if (str.equals(STATE_STOPPED)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -468307734:
                if (str.equals(STATE_PROVIDERS_INITIALIZING)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 433141802:
                if (str.equals(STATE_UNKNOWN)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 478389753:
                if (str.equals(STATE_DESTROYED)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 872357833:
                if (str.equals(STATE_UNCERTAIN)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1386911874:
                if (str.equals(STATE_CERTAIN)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1917201485:
                if (str.equals(STATE_INITIALIZING)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2066319421:
                if (str.equals(STATE_FAILED)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
                return 3;
            default:
                return 2;
        }
    }

    void clearRecordedStates() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            this.mRecordedStates.clear();
            this.mPrimaryProvider.clearRecordedStates();
            this.mSecondaryProvider.clearRecordedStates();
        }
    }

    @android.annotation.NonNull
    com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState getStateForTests() {
        com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState build;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState.Builder builder = new com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState.Builder();
                if (this.mLastEvent != null) {
                    builder.setLastEvent(this.mLastEvent);
                }
                builder.setControllerState(this.mState.get()).setStateChanges(this.mRecordedStates).setPrimaryProviderStateChanges(this.mPrimaryProvider.getRecordedStates()).setSecondaryProviderStateChanges(this.mSecondaryProvider.getRecordedStates());
                build = builder.build();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return build;
    }

    static abstract class Environment {

        @android.annotation.NonNull
        protected final java.lang.Object mSharedLock;

        @android.annotation.NonNull
        protected final com.android.server.timezonedetector.location.ThreadingDomain mThreadingDomain;

        abstract void destroy();

        abstract long elapsedRealtimeMillis();

        abstract com.android.server.timezonedetector.ConfigurationInternal getCurrentUserConfigurationInternal();

        abstract java.time.Duration getProviderEventFilteringAgeThreshold();

        abstract java.time.Duration getProviderInitializationTimeout();

        abstract java.time.Duration getProviderInitializationTimeoutFuzz();

        abstract java.time.Duration getUncertaintyDelay();

        Environment(@android.annotation.NonNull com.android.server.timezonedetector.location.ThreadingDomain threadingDomain) {
            java.util.Objects.requireNonNull(threadingDomain);
            this.mThreadingDomain = threadingDomain;
            this.mSharedLock = threadingDomain.getLockObject();
        }
    }

    static abstract class Callback {

        @android.annotation.NonNull
        protected final com.android.server.timezonedetector.location.ThreadingDomain mThreadingDomain;

        abstract void sendEvent(@android.annotation.NonNull com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent);

        Callback(@android.annotation.NonNull com.android.server.timezonedetector.location.ThreadingDomain threadingDomain) {
            java.util.Objects.requireNonNull(threadingDomain);
            this.mThreadingDomain = threadingDomain;
        }
    }
}
