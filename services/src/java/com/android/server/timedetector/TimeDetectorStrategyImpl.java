package com.android.server.timedetector;

/* loaded from: classes2.dex */
public final class TimeDetectorStrategyImpl implements com.android.server.timedetector.TimeDetectorStrategy {
    private static final boolean DBG = false;
    private static final int KEEP_SUGGESTION_HISTORY_SIZE = 10;
    private static final java.lang.String LOG_TAG = "time_detector";

    @com.android.internal.annotations.VisibleForTesting
    static final long MAX_SUGGESTION_TIME_AGE_MILLIS = 86400000;
    private static final long SYSTEM_CLOCK_PARANOIA_THRESHOLD_MILLIS = 2000;
    private static final int TELEPHONY_BUCKET_COUNT = 24;

    @com.android.internal.annotations.VisibleForTesting
    static final int TELEPHONY_BUCKET_SIZE_MILLIS = 3600000;
    private static final int TELEPHONY_INVALID_SCORE = -1;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"this"})
    private com.android.server.timedetector.ConfigurationInternal mCurrentConfigurationInternal;

    @android.annotation.NonNull
    private final com.android.server.timedetector.TimeDetectorStrategyImpl.Environment mEnvironment;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.app.time.UnixEpochTime mLastAutoSystemClockTimeSet;

    @android.annotation.NonNull
    private final com.android.server.timedetector.ServiceConfigAccessor mServiceConfigAccessor;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.List<com.android.server.timezonedetector.StateChangeListener> mStateChangeListeners = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.server.timezonedetector.ArrayMapWithHistory<java.lang.Integer, android.app.timedetector.TelephonyTimeSuggestion> mSuggestionBySlotIndex = new com.android.server.timezonedetector.ArrayMapWithHistory<>(10);

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.server.timezonedetector.ReferenceWithHistory<com.android.server.timedetector.NetworkTimeSuggestion> mLastNetworkSuggestion = new com.android.server.timezonedetector.ReferenceWithHistory<>(10);

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.server.timezonedetector.ReferenceWithHistory<com.android.server.timedetector.GnssTimeSuggestion> mLastGnssSuggestion = new com.android.server.timezonedetector.ReferenceWithHistory<>(10);

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.server.timezonedetector.ReferenceWithHistory<android.app.time.ExternalTimeSuggestion> mLastExternalSuggestion = new com.android.server.timezonedetector.ReferenceWithHistory<>(10);

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.ArraySet<com.android.server.timezonedetector.StateChangeListener> mNetworkTimeUpdateListeners = new android.util.ArraySet<>();

    public interface Environment {
        void acquireWakeLock();

        void addDebugLogEntry(@android.annotation.NonNull java.lang.String str);

        void dumpDebugLog(android.util.IndentingPrintWriter indentingPrintWriter);

        long elapsedRealtimeMillis();

        void releaseWakeLock();

        void runAsync(@android.annotation.NonNull java.lang.Runnable runnable);

        void setSystemClock(long j, int i, @android.annotation.NonNull java.lang.String str);

        void setSystemClockConfidence(int i, @android.annotation.NonNull java.lang.String str);

        int systemClockConfidence();

        long systemClockMillis();
    }

    static com.android.server.timedetector.TimeDetectorStrategy create(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.timedetector.ServiceConfigAccessor serviceConfigAccessor) {
        return new com.android.server.timedetector.TimeDetectorStrategyImpl(new com.android.server.timedetector.EnvironmentImpl(context, handler), serviceConfigAccessor);
    }

    @com.android.internal.annotations.VisibleForTesting
    TimeDetectorStrategyImpl(@android.annotation.NonNull com.android.server.timedetector.TimeDetectorStrategyImpl.Environment environment, @android.annotation.NonNull com.android.server.timedetector.ServiceConfigAccessor serviceConfigAccessor) {
        java.util.Objects.requireNonNull(environment);
        this.mEnvironment = environment;
        java.util.Objects.requireNonNull(serviceConfigAccessor);
        this.mServiceConfigAccessor = serviceConfigAccessor;
        synchronized (this) {
            this.mServiceConfigAccessor.addConfigurationInternalChangeListener(new com.android.server.timezonedetector.StateChangeListener() { // from class: com.android.server.timedetector.TimeDetectorStrategyImpl$$ExternalSyntheticLambda1
                @Override // com.android.server.timezonedetector.StateChangeListener
                public final void onChange() {
                    com.android.server.timedetector.TimeDetectorStrategyImpl.this.handleConfigurationInternalMaybeChanged();
                }
            });
            updateCurrentConfigurationInternalIfRequired("TimeDetectorStrategyImpl:");
        }
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void suggestExternalTime(@android.annotation.NonNull android.app.time.ExternalTimeSuggestion externalTimeSuggestion) {
        java.util.Objects.requireNonNull(externalTimeSuggestion);
        if (validateAutoSuggestionTime(externalTimeSuggestion.getUnixEpochTime(), externalTimeSuggestion)) {
            this.mLastExternalSuggestion.set(externalTimeSuggestion);
            doAutoTimeDetection("External time suggestion received: suggestion=" + externalTimeSuggestion);
        }
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void suggestGnssTime(@android.annotation.NonNull com.android.server.timedetector.GnssTimeSuggestion gnssTimeSuggestion) {
        java.util.Objects.requireNonNull(gnssTimeSuggestion);
        if (validateAutoSuggestionTime(gnssTimeSuggestion.getUnixEpochTime(), gnssTimeSuggestion)) {
            this.mLastGnssSuggestion.set(gnssTimeSuggestion);
            doAutoTimeDetection("GNSS time suggestion received: suggestion=" + gnssTimeSuggestion);
        }
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized boolean suggestManualTime(int i, @android.annotation.NonNull android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion, boolean z) {
        com.android.server.timedetector.ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
        if (configurationInternal.getUserId() != i) {
            android.util.Slog.w(LOG_TAG, "Manual suggestion received but user != current user, userId=" + i + " suggestion=" + manualTimeSuggestion);
            return false;
        }
        java.util.Objects.requireNonNull(manualTimeSuggestion);
        java.lang.String str = "Manual time suggestion received: suggestion=" + manualTimeSuggestion;
        android.app.time.TimeCapabilities capabilities = configurationInternal.createCapabilitiesAndConfig(z).getCapabilities();
        if (capabilities.getSetManualTimeCapability() != 40) {
            android.util.Slog.i(LOG_TAG, "User does not have the capability needed to set the time manually: capabilities=" + capabilities + ", suggestion=" + manualTimeSuggestion + ", cause=" + str);
            return false;
        }
        android.app.time.UnixEpochTime unixEpochTime = manualTimeSuggestion.getUnixEpochTime();
        if (!validateManualSuggestionTime(unixEpochTime, manualTimeSuggestion)) {
            return false;
        }
        return setSystemClockAndConfidenceIfRequired(2, unixEpochTime, str);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void suggestNetworkTime(@android.annotation.NonNull com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion) {
        try {
            java.util.Objects.requireNonNull(networkTimeSuggestion);
            if (validateAutoSuggestionTime(networkTimeSuggestion.getUnixEpochTime(), networkTimeSuggestion)) {
                com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion2 = this.mLastNetworkSuggestion.get();
                if (networkTimeSuggestion2 == null || !networkTimeSuggestion2.equals(networkTimeSuggestion)) {
                    this.mLastNetworkSuggestion.set(networkTimeSuggestion);
                    notifyNetworkTimeUpdateListenersAsynchronously();
                }
                doAutoTimeDetection("New network time suggested. suggestion=" + networkTimeSuggestion);
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void notifyNetworkTimeUpdateListenersAsynchronously() {
        java.util.Iterator<com.android.server.timezonedetector.StateChangeListener> it = this.mNetworkTimeUpdateListeners.iterator();
        while (it.hasNext()) {
            com.android.server.timezonedetector.StateChangeListener next = it.next();
            com.android.server.timedetector.TimeDetectorStrategyImpl.Environment environment = this.mEnvironment;
            java.util.Objects.requireNonNull(next);
            environment.runAsync(new com.android.server.timedetector.TimeDetectorStrategyImpl$$ExternalSyntheticLambda0(next));
        }
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void addNetworkTimeUpdateListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener) {
        this.mNetworkTimeUpdateListeners.add(stateChangeListener);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    @android.annotation.Nullable
    public synchronized com.android.server.timedetector.NetworkTimeSuggestion getLatestNetworkSuggestion() {
        return this.mLastNetworkSuggestion.get();
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void clearLatestNetworkSuggestion() {
        this.mLastNetworkSuggestion.set(null);
        notifyNetworkTimeUpdateListenersAsynchronously();
        doAutoTimeDetection("Network time cleared");
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    @android.annotation.NonNull
    public synchronized android.app.time.TimeState getTimeState() {
        return new android.app.time.TimeState(new android.app.time.UnixEpochTime(this.mEnvironment.elapsedRealtimeMillis(), this.mEnvironment.systemClockMillis()), this.mEnvironment.systemClockConfidence() < 100);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void setTimeState(@android.annotation.NonNull android.app.time.TimeState timeState) {
        java.util.Objects.requireNonNull(timeState);
        int i = timeState.getUserShouldConfirmTime() ? 0 : 100;
        this.mEnvironment.acquireWakeLock();
        try {
            setSystemClockAndConfidenceUnderWakeLock(2, timeState.getUnixEpochTime(), i, "setTimeState()");
        } finally {
            this.mEnvironment.releaseWakeLock();
        }
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized boolean confirmTime(@android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime) {
        boolean isTimeWithinConfidenceThreshold;
        java.util.Objects.requireNonNull(unixEpochTime);
        this.mEnvironment.acquireWakeLock();
        try {
            long elapsedRealtimeMillis = this.mEnvironment.elapsedRealtimeMillis();
            long systemClockMillis = this.mEnvironment.systemClockMillis();
            isTimeWithinConfidenceThreshold = isTimeWithinConfidenceThreshold(unixEpochTime, elapsedRealtimeMillis, systemClockMillis);
            if (isTimeWithinConfidenceThreshold) {
                int systemClockConfidence = this.mEnvironment.systemClockConfidence();
                if (systemClockConfidence < 100) {
                    this.mEnvironment.setSystemClockConfidence(100, "Confirm system clock time. confirmationTime=" + unixEpochTime + " newTimeConfidence=100 currentElapsedRealtimeMillis=" + elapsedRealtimeMillis + " currentSystemClockMillis=" + systemClockMillis + " (old) currentTimeConfidence=" + systemClockConfidence);
                }
            }
        } finally {
            this.mEnvironment.releaseWakeLock();
        }
        return isTimeWithinConfidenceThreshold;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void notifyStateChangeListenersAsynchronously() {
        for (com.android.server.timezonedetector.StateChangeListener stateChangeListener : this.mStateChangeListeners) {
            com.android.server.timedetector.TimeDetectorStrategyImpl.Environment environment = this.mEnvironment;
            java.util.Objects.requireNonNull(stateChangeListener);
            environment.runAsync(new com.android.server.timedetector.TimeDetectorStrategyImpl$$ExternalSyntheticLambda0(stateChangeListener));
        }
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void addChangeListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener) {
        this.mStateChangeListeners.add(stateChangeListener);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized android.app.time.TimeCapabilitiesAndConfig getCapabilitiesAndConfig(int i, boolean z) {
        com.android.server.timedetector.ConfigurationInternal configurationInternal;
        try {
            if (this.mCurrentConfigurationInternal.getUserId() == i) {
                configurationInternal = this.mCurrentConfigurationInternal;
            } else {
                configurationInternal = this.mServiceConfigAccessor.getConfigurationInternal(i);
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
        return configurationInternal.createCapabilitiesAndConfig(z);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized boolean updateConfiguration(int i, @android.annotation.NonNull android.app.time.TimeConfiguration timeConfiguration, boolean z) {
        boolean updateConfiguration;
        updateConfiguration = this.mServiceConfigAccessor.updateConfiguration(i, timeConfiguration, z);
        if (updateConfiguration) {
            updateCurrentConfigurationInternalIfRequired("updateConfiguration: userId=" + i + ", configuration=" + timeConfiguration + ", bypassUserPolicyChecks=" + z);
        }
        return updateConfiguration;
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void suggestTelephonyTime(@android.annotation.NonNull android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion) {
        if (telephonyTimeSuggestion.getUnixEpochTime() == null) {
            return;
        }
        if (validateAutoSuggestionTime(telephonyTimeSuggestion.getUnixEpochTime(), telephonyTimeSuggestion)) {
            if (storeTelephonySuggestion(telephonyTimeSuggestion)) {
                doAutoTimeDetection("New telephony time suggested. suggestion=" + telephonyTimeSuggestion);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleConfigurationInternalMaybeChanged() {
        updateCurrentConfigurationInternalIfRequired("handleConfigurationInternalMaybeChanged:");
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void updateCurrentConfigurationInternalIfRequired(@android.annotation.NonNull java.lang.String str) {
        com.android.server.timedetector.ConfigurationInternal currentUserConfigurationInternal = this.mServiceConfigAccessor.getCurrentUserConfigurationInternal();
        com.android.server.timedetector.ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
        if (!currentUserConfigurationInternal.equals(configurationInternal)) {
            this.mCurrentConfigurationInternal = currentUserConfigurationInternal;
            addDebugLogEntry(str + " [oldConfiguration=" + configurationInternal + ", newConfiguration=" + currentUserConfigurationInternal + "]");
            notifyStateChangeListenersAsynchronously();
            if (this.mCurrentConfigurationInternal.getAutoDetectionEnabledBehavior()) {
                doAutoTimeDetection("Auto time detection config changed.");
            } else {
                this.mLastAutoSystemClockTimeSet = null;
            }
        }
    }

    private void addDebugLogEntry(@android.annotation.NonNull java.lang.String str) {
        this.mEnvironment.addDebugLogEntry(str);
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public synchronized void dump(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        indentingPrintWriter.println("TimeDetectorStrategy:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mLastAutoSystemClockTimeSet=" + this.mLastAutoSystemClockTimeSet);
        indentingPrintWriter.println("mCurrentConfigurationInternal=" + this.mCurrentConfigurationInternal);
        indentingPrintWriter.println("[Capabilities=" + this.mCurrentConfigurationInternal.createCapabilitiesAndConfig(false) + "]");
        indentingPrintWriter.println("mEnvironment:");
        indentingPrintWriter.increaseIndent();
        this.mEnvironment.dumpDebugLog(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Time change log:");
        indentingPrintWriter.increaseIndent();
        com.android.server.SystemClockTime.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Telephony suggestion history:");
        indentingPrintWriter.increaseIndent();
        this.mSuggestionBySlotIndex.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Network suggestion history:");
        indentingPrintWriter.increaseIndent();
        this.mLastNetworkSuggestion.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Gnss suggestion history:");
        indentingPrintWriter.increaseIndent();
        this.mLastGnssSuggestion.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("External suggestion history:");
        indentingPrintWriter.increaseIndent();
        this.mLastExternalSuggestion.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    @com.android.internal.annotations.VisibleForTesting
    public synchronized com.android.server.timedetector.ConfigurationInternal getCachedCapabilitiesAndConfigForTests() {
        return this.mCurrentConfigurationInternal;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean storeTelephonySuggestion(@android.annotation.NonNull android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion) {
        android.app.time.UnixEpochTime unixEpochTime = telephonyTimeSuggestion.getUnixEpochTime();
        int slotIndex = telephonyTimeSuggestion.getSlotIndex();
        android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion2 = this.mSuggestionBySlotIndex.get(java.lang.Integer.valueOf(slotIndex));
        if (telephonyTimeSuggestion2 != null) {
            if (telephonyTimeSuggestion2.getUnixEpochTime() == null) {
                android.util.Slog.w(LOG_TAG, "Previous suggestion is null or has a null time. previousSuggestion=" + telephonyTimeSuggestion2 + ", suggestion=" + telephonyTimeSuggestion);
                return false;
            }
            long elapsedRealtimeDifference = android.app.time.UnixEpochTime.elapsedRealtimeDifference(unixEpochTime, telephonyTimeSuggestion2.getUnixEpochTime());
            if (elapsedRealtimeDifference < 0) {
                android.util.Slog.w(LOG_TAG, "Out of order telephony suggestion received. referenceTimeDifference=" + elapsedRealtimeDifference + " previousSuggestion=" + telephonyTimeSuggestion2 + " suggestion=" + telephonyTimeSuggestion);
                return false;
            }
        }
        this.mSuggestionBySlotIndex.put(java.lang.Integer.valueOf(slotIndex), telephonyTimeSuggestion);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean validateSuggestionCommon(@android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime, @android.annotation.NonNull java.lang.Object obj) {
        long elapsedRealtimeMillis = this.mEnvironment.elapsedRealtimeMillis();
        if (elapsedRealtimeMillis < unixEpochTime.getElapsedRealtimeMillis()) {
            android.util.Slog.w(LOG_TAG, "New elapsed realtime is in the future? Ignoring. elapsedRealtimeMillis=" + elapsedRealtimeMillis + ", suggestion=" + obj);
            return false;
        }
        if (unixEpochTime.getUnixEpochTimeMillis() > this.mCurrentConfigurationInternal.getSuggestionUpperBound().toEpochMilli()) {
            android.util.Slog.w(LOG_TAG, "Suggested value is above max time supported by this device. suggestion=" + obj);
            return false;
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean validateAutoSuggestionTime(@android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime, @android.annotation.NonNull java.lang.Object obj) {
        return validateSuggestionCommon(unixEpochTime, obj) && validateSuggestionAgainstLowerBound(unixEpochTime, obj, this.mCurrentConfigurationInternal.getAutoSuggestionLowerBound());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean validateManualSuggestionTime(@android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime, @android.annotation.NonNull java.lang.Object obj) {
        return validateSuggestionCommon(unixEpochTime, obj) && validateSuggestionAgainstLowerBound(unixEpochTime, obj, this.mCurrentConfigurationInternal.getManualSuggestionLowerBound());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean validateSuggestionAgainstLowerBound(@android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime, @android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull java.time.Instant instant) {
        if (instant.toEpochMilli() > unixEpochTime.getUnixEpochTimeMillis()) {
            android.util.Slog.w(LOG_TAG, "Suggestion points to time before lower bound, skipping it. suggestion=" + obj + ", lower bound=" + instant);
            return false;
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void doAutoTimeDetection(@android.annotation.NonNull java.lang.String str) {
        java.lang.String str2;
        int[] autoOriginPriorities = this.mCurrentConfigurationInternal.getAutoOriginPriorities();
        for (int i : autoOriginPriorities) {
            android.app.time.UnixEpochTime unixEpochTime = null;
            if (i == 1) {
                android.app.timedetector.TelephonyTimeSuggestion findBestTelephonySuggestion = findBestTelephonySuggestion();
                if (findBestTelephonySuggestion == null) {
                    str2 = null;
                } else {
                    unixEpochTime = findBestTelephonySuggestion.getUnixEpochTime();
                    str2 = "Found good telephony suggestion., bestTelephonySuggestion=" + findBestTelephonySuggestion + ", detectionReason=" + str;
                }
            } else if (i == 3) {
                com.android.server.timedetector.NetworkTimeSuggestion findLatestValidNetworkSuggestion = findLatestValidNetworkSuggestion();
                if (findLatestValidNetworkSuggestion == null) {
                    str2 = null;
                } else {
                    unixEpochTime = findLatestValidNetworkSuggestion.getUnixEpochTime();
                    str2 = "Found good network suggestion., networkSuggestion=" + findLatestValidNetworkSuggestion + ", detectionReason=" + str;
                }
            } else if (i == 4) {
                com.android.server.timedetector.GnssTimeSuggestion findLatestValidGnssSuggestion = findLatestValidGnssSuggestion();
                if (findLatestValidGnssSuggestion == null) {
                    str2 = null;
                } else {
                    unixEpochTime = findLatestValidGnssSuggestion.getUnixEpochTime();
                    str2 = "Found good gnss suggestion., gnssSuggestion=" + findLatestValidGnssSuggestion + ", detectionReason=" + str;
                }
            } else if (i == 5) {
                android.app.time.ExternalTimeSuggestion findLatestValidExternalSuggestion = findLatestValidExternalSuggestion();
                if (findLatestValidExternalSuggestion == null) {
                    str2 = null;
                } else {
                    unixEpochTime = findLatestValidExternalSuggestion.getUnixEpochTime();
                    str2 = "Found good external suggestion., externalSuggestion=" + findLatestValidExternalSuggestion + ", detectionReason=" + str;
                }
            } else {
                android.util.Slog.w(LOG_TAG, "Unknown or unsupported origin=" + i + " in " + java.util.Arrays.toString(autoOriginPriorities) + ": Skipping");
                str2 = null;
            }
            if (unixEpochTime != null) {
                if (this.mCurrentConfigurationInternal.getAutoDetectionEnabledBehavior()) {
                    setSystemClockAndConfidenceIfRequired(i, unixEpochTime, str2);
                    return;
                } else {
                    upgradeSystemClockConfidenceIfRequired(unixEpochTime, str2);
                    return;
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.app.timedetector.TelephonyTimeSuggestion findBestTelephonySuggestion() {
        long elapsedRealtimeMillis = this.mEnvironment.elapsedRealtimeMillis();
        android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion = null;
        int i = -1;
        for (int i2 = 0; i2 < this.mSuggestionBySlotIndex.size(); i2++) {
            java.lang.Integer keyAt = this.mSuggestionBySlotIndex.keyAt(i2);
            android.app.timedetector.TelephonyTimeSuggestion valueAt = this.mSuggestionBySlotIndex.valueAt(i2);
            if (valueAt == null) {
                android.util.Slog.w(LOG_TAG, "Latest suggestion unexpectedly null for slotIndex. slotIndex=" + keyAt);
            } else if (valueAt.getUnixEpochTime() == null) {
                android.util.Slog.w(LOG_TAG, "Latest suggestion unexpectedly empty.  candidateSuggestion=" + valueAt);
            } else {
                int scoreTelephonySuggestion = scoreTelephonySuggestion(elapsedRealtimeMillis, valueAt);
                if (scoreTelephonySuggestion != -1) {
                    if (telephonyTimeSuggestion == null || i < scoreTelephonySuggestion) {
                        i = scoreTelephonySuggestion;
                        telephonyTimeSuggestion = valueAt;
                    } else if (i == scoreTelephonySuggestion && valueAt.getSlotIndex() < telephonyTimeSuggestion.getSlotIndex()) {
                        telephonyTimeSuggestion = valueAt;
                    }
                }
            }
        }
        return telephonyTimeSuggestion;
    }

    private static int scoreTelephonySuggestion(long j, @android.annotation.NonNull android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion) {
        android.app.time.UnixEpochTime unixEpochTime = telephonyTimeSuggestion.getUnixEpochTime();
        if (!validateSuggestionUnixEpochTime(j, unixEpochTime)) {
            android.util.Slog.w(LOG_TAG, "Existing suggestion found to be invalid elapsedRealtimeMillis=" + j + ", suggestion=" + telephonyTimeSuggestion);
            return -1;
        }
        int elapsedRealtimeMillis = (int) ((j - unixEpochTime.getElapsedRealtimeMillis()) / 3600000);
        if (elapsedRealtimeMillis >= 24) {
            return -1;
        }
        return 24 - elapsedRealtimeMillis;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private com.android.server.timedetector.NetworkTimeSuggestion findLatestValidNetworkSuggestion() {
        com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion = this.mLastNetworkSuggestion.get();
        if (networkTimeSuggestion == null) {
            return null;
        }
        if (!validateSuggestionUnixEpochTime(this.mEnvironment.elapsedRealtimeMillis(), networkTimeSuggestion.getUnixEpochTime())) {
            return null;
        }
        return networkTimeSuggestion;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private com.android.server.timedetector.GnssTimeSuggestion findLatestValidGnssSuggestion() {
        com.android.server.timedetector.GnssTimeSuggestion gnssTimeSuggestion = this.mLastGnssSuggestion.get();
        if (gnssTimeSuggestion == null) {
            return null;
        }
        if (!validateSuggestionUnixEpochTime(this.mEnvironment.elapsedRealtimeMillis(), gnssTimeSuggestion.getUnixEpochTime())) {
            return null;
        }
        return gnssTimeSuggestion;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private android.app.time.ExternalTimeSuggestion findLatestValidExternalSuggestion() {
        android.app.time.ExternalTimeSuggestion externalTimeSuggestion = this.mLastExternalSuggestion.get();
        if (externalTimeSuggestion == null) {
            return null;
        }
        if (!validateSuggestionUnixEpochTime(this.mEnvironment.elapsedRealtimeMillis(), externalTimeSuggestion.getUnixEpochTime())) {
            return null;
        }
        return externalTimeSuggestion;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean setSystemClockAndConfidenceIfRequired(int i, @android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime, @android.annotation.NonNull java.lang.String str) {
        if (isOriginAutomatic(i)) {
            if (!this.mCurrentConfigurationInternal.getAutoDetectionEnabledBehavior()) {
                return false;
            }
        } else if (this.mCurrentConfigurationInternal.getAutoDetectionEnabledBehavior()) {
            return false;
        }
        this.mEnvironment.acquireWakeLock();
        try {
            return setSystemClockAndConfidenceUnderWakeLock(i, unixEpochTime, 100, str);
        } finally {
            this.mEnvironment.releaseWakeLock();
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void upgradeSystemClockConfidenceIfRequired(@android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime, @android.annotation.NonNull java.lang.String str) {
        int systemClockConfidence = this.mEnvironment.systemClockConfidence();
        if (!(systemClockConfidence < 100)) {
            return;
        }
        this.mEnvironment.acquireWakeLock();
        try {
            long elapsedRealtimeMillis = this.mEnvironment.elapsedRealtimeMillis();
            long systemClockMillis = this.mEnvironment.systemClockMillis();
            if (isTimeWithinConfidenceThreshold(unixEpochTime, elapsedRealtimeMillis, systemClockMillis)) {
                this.mEnvironment.setSystemClockConfidence(100, "Upgrade system clock confidence. autoDetectedUnixEpochTime=" + unixEpochTime + " newTimeConfidence=100 cause=" + str + " currentElapsedRealtimeMillis=" + elapsedRealtimeMillis + " currentSystemClockMillis=" + systemClockMillis + " currentTimeConfidence=" + systemClockConfidence);
            }
        } finally {
            this.mEnvironment.releaseWakeLock();
        }
    }

    private static boolean isOriginAutomatic(int i) {
        return i != 2;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean isTimeWithinConfidenceThreshold(@android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime, long j, long j2) {
        return java.lang.Math.abs(unixEpochTime.at(j).getUnixEpochTimeMillis() - j2) <= ((long) this.mCurrentConfigurationInternal.getSystemClockConfidenceThresholdMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean setSystemClockAndConfidenceUnderWakeLock(int i, @android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime, int i2, @android.annotation.NonNull java.lang.String str) {
        long elapsedRealtimeMillis = this.mEnvironment.elapsedRealtimeMillis();
        boolean isOriginAutomatic = isOriginAutomatic(i);
        long systemClockMillis = this.mEnvironment.systemClockMillis();
        if (isOriginAutomatic && this.mLastAutoSystemClockTimeSet != null) {
            long unixEpochTimeMillis = this.mLastAutoSystemClockTimeSet.at(elapsedRealtimeMillis).getUnixEpochTimeMillis();
            if (java.lang.Math.abs(unixEpochTimeMillis - systemClockMillis) > SYSTEM_CLOCK_PARANOIA_THRESHOLD_MILLIS) {
                android.util.Slog.w(LOG_TAG, "System clock has not tracked elapsed real time clock. A clock may be inaccurate or something unexpectedly set the system clock. origin=" + com.android.server.timedetector.TimeDetectorStrategy.originToString(i) + " elapsedRealtimeMillis=" + elapsedRealtimeMillis + " expectedTimeMillis=" + unixEpochTimeMillis + " actualTimeMillis=" + systemClockMillis + " cause=" + str);
            }
        }
        long unixEpochTimeMillis2 = unixEpochTime.at(elapsedRealtimeMillis).getUnixEpochTimeMillis();
        boolean z = java.lang.Math.abs(unixEpochTimeMillis2 - systemClockMillis) >= ((long) this.mCurrentConfigurationInternal.getSystemClockUpdateThresholdMillis());
        int systemClockConfidence = this.mEnvironment.systemClockConfidence();
        boolean z2 = i2 != systemClockConfidence;
        if (!z) {
            if (z2) {
                this.mEnvironment.setSystemClockConfidence(i2, "Set system clock confidence. origin=" + com.android.server.timedetector.TimeDetectorStrategy.originToString(i) + " newTime=" + unixEpochTime + " newTimeConfidence=" + i2 + " cause=" + str + " elapsedRealtimeMillis=" + elapsedRealtimeMillis + " (old) actualSystemClockMillis=" + systemClockMillis + " newSystemClockMillis=" + unixEpochTimeMillis2 + " currentTimeConfidence=" + systemClockConfidence);
                return true;
            }
            return true;
        }
        this.mEnvironment.setSystemClock(unixEpochTimeMillis2, i2, "Set system clock & confidence. origin=" + com.android.server.timedetector.TimeDetectorStrategy.originToString(i) + " newTime=" + unixEpochTime + " newTimeConfidence=" + i2 + " cause=" + str + " elapsedRealtimeMillis=" + elapsedRealtimeMillis + " (old) actualSystemClockMillis=" + systemClockMillis + " newSystemClockMillis=" + unixEpochTimeMillis2 + " currentTimeConfidence=" + systemClockConfidence);
        if (!isOriginAutomatic(i)) {
            this.mLastAutoSystemClockTimeSet = null;
            return true;
        }
        this.mLastAutoSystemClockTimeSet = unixEpochTime;
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public synchronized android.app.timedetector.TelephonyTimeSuggestion findBestTelephonySuggestionForTests() {
        return findBestTelephonySuggestion();
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public synchronized com.android.server.timedetector.NetworkTimeSuggestion findLatestValidNetworkSuggestionForTests() {
        return findLatestValidNetworkSuggestion();
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public synchronized com.android.server.timedetector.GnssTimeSuggestion findLatestValidGnssSuggestionForTests() {
        return findLatestValidGnssSuggestion();
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public synchronized android.app.time.ExternalTimeSuggestion findLatestValidExternalSuggestionForTests() {
        return findLatestValidExternalSuggestion();
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public synchronized android.app.timedetector.TelephonyTimeSuggestion getLatestTelephonySuggestion(int i) {
        return this.mSuggestionBySlotIndex.get(java.lang.Integer.valueOf(i));
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public synchronized com.android.server.timedetector.GnssTimeSuggestion getLatestGnssSuggestion() {
        return this.mLastGnssSuggestion.get();
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public synchronized android.app.time.ExternalTimeSuggestion getLatestExternalSuggestion() {
        return this.mLastExternalSuggestion.get();
    }

    private static boolean validateSuggestionUnixEpochTime(long j, @android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime) {
        long elapsedRealtimeMillis = unixEpochTime.getElapsedRealtimeMillis();
        return elapsedRealtimeMillis <= j && j - elapsedRealtimeMillis <= 86400000;
    }
}
