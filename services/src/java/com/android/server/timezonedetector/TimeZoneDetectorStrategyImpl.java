package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public final class TimeZoneDetectorStrategyImpl implements com.android.server.timezonedetector.TimeZoneDetectorStrategy {
    private static final boolean DBG = false;
    private static final int KEEP_SUGGESTION_HISTORY_SIZE = 10;
    private static final java.lang.String LOG_TAG = "time_zone_detector";

    @com.android.internal.annotations.VisibleForTesting
    public static final int TELEPHONY_SCORE_HIGH = 3;

    @com.android.internal.annotations.VisibleForTesting
    public static final int TELEPHONY_SCORE_HIGHEST = 4;

    @com.android.internal.annotations.VisibleForTesting
    public static final int TELEPHONY_SCORE_LOW = 1;

    @com.android.internal.annotations.VisibleForTesting
    public static final int TELEPHONY_SCORE_MEDIUM = 2;

    @com.android.internal.annotations.VisibleForTesting
    public static final int TELEPHONY_SCORE_NONE = 0;

    @com.android.internal.annotations.VisibleForTesting
    public static final int TELEPHONY_SCORE_USAGE_THRESHOLD = 2;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"this"})
    private com.android.server.timezonedetector.ConfigurationInternal mCurrentConfigurationInternal;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"this"})
    private android.app.time.TimeZoneDetectorStatus mDetectorStatus;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment mEnvironment;

    @android.annotation.NonNull
    private final com.android.server.timezonedetector.ServiceConfigAccessor mServiceConfigAccessor;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"this"})
    private android.os.TimestampedValue<java.lang.Boolean> mTelephonyTimeZoneFallbackEnabled;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.server.timezonedetector.ArrayMapWithHistory<java.lang.Integer, com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.QualifiedTelephonyTimeZoneSuggestion> mTelephonySuggestionsBySlotIndex = new com.android.server.timezonedetector.ArrayMapWithHistory<>(10);

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.server.timezonedetector.ReferenceWithHistory<com.android.server.timezonedetector.LocationAlgorithmEvent> mLatestLocationAlgorithmEvent = new com.android.server.timezonedetector.ReferenceWithHistory<>(10);

    @com.android.internal.annotations.GuardedBy({"this"})
    private final com.android.server.timezonedetector.ReferenceWithHistory<android.app.timezonedetector.ManualTimeZoneSuggestion> mLatestManualSuggestion = new com.android.server.timezonedetector.ReferenceWithHistory<>(10);

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.List<com.android.server.timezonedetector.StateChangeListener> mStateChangeListeners = new java.util.ArrayList();

    @com.android.internal.annotations.VisibleForTesting
    public interface Environment {
        void addDebugLogEntry(@android.annotation.NonNull java.lang.String str);

        void dumpDebugLog(java.io.PrintWriter printWriter);

        long elapsedRealtimeMillis();

        @android.annotation.NonNull
        java.lang.String getDeviceTimeZone();

        int getDeviceTimeZoneConfidence();

        void runAsync(@android.annotation.NonNull java.lang.Runnable runnable);

        void setDeviceTimeZoneAndConfidence(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2);
    }

    public static com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl create(@android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.timezonedetector.ServiceConfigAccessor serviceConfigAccessor) {
        return new com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl(serviceConfigAccessor, new com.android.server.timezonedetector.EnvironmentImpl(handler));
    }

    @com.android.internal.annotations.VisibleForTesting
    public TimeZoneDetectorStrategyImpl(@android.annotation.NonNull com.android.server.timezonedetector.ServiceConfigAccessor serviceConfigAccessor, @android.annotation.NonNull com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment environment) {
        java.util.Objects.requireNonNull(environment);
        this.mEnvironment = environment;
        java.util.Objects.requireNonNull(serviceConfigAccessor);
        this.mServiceConfigAccessor = serviceConfigAccessor;
        this.mTelephonyTimeZoneFallbackEnabled = new android.os.TimestampedValue<>(this.mEnvironment.elapsedRealtimeMillis(), true);
        synchronized (this) {
            this.mServiceConfigAccessor.addConfigurationInternalChangeListener(new com.android.server.timezonedetector.StateChangeListener() { // from class: com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl$$ExternalSyntheticLambda0
                @Override // com.android.server.timezonedetector.StateChangeListener
                public final void onChange() {
                    com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.this.handleConfigurationInternalMaybeChanged();
                }
            });
            updateCurrentConfigurationInternalIfRequired("TimeZoneDetectorStrategyImpl:");
        }
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategy
    public synchronized android.app.time.TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfig(int i, boolean z) {
        com.android.server.timezonedetector.ConfigurationInternal configurationInternal;
        try {
            if (this.mCurrentConfigurationInternal.getUserId() == i) {
                configurationInternal = this.mCurrentConfigurationInternal;
            } else {
                configurationInternal = this.mServiceConfigAccessor.getConfigurationInternal(i);
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
        return new android.app.time.TimeZoneCapabilitiesAndConfig(this.mDetectorStatus, configurationInternal.asCapabilities(z), configurationInternal.asConfiguration());
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategy
    public synchronized boolean updateConfiguration(int i, @android.annotation.NonNull android.app.time.TimeZoneConfiguration timeZoneConfiguration, boolean z) {
        boolean updateConfiguration;
        updateConfiguration = this.mServiceConfigAccessor.updateConfiguration(i, timeZoneConfiguration, z);
        if (updateConfiguration) {
            updateCurrentConfigurationInternalIfRequired("updateConfiguration: userId=" + i + ", configuration=" + timeZoneConfiguration + ", bypassUserPolicyChecks=" + z);
        }
        return updateConfiguration;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void updateCurrentConfigurationInternalIfRequired(@android.annotation.NonNull java.lang.String str) {
        com.android.server.timezonedetector.ConfigurationInternal currentUserConfigurationInternal = this.mServiceConfigAccessor.getCurrentUserConfigurationInternal();
        com.android.server.timezonedetector.ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
        if (!currentUserConfigurationInternal.equals(configurationInternal)) {
            this.mCurrentConfigurationInternal = currentUserConfigurationInternal;
            java.lang.String str2 = str + " [oldConfiguration=" + configurationInternal + ", newConfiguration=" + currentUserConfigurationInternal + "]";
            logTimeZoneDebugInfo(str2);
            updateDetectorStatus();
            notifyStateChangeListenersAsynchronously();
            doAutoTimeZoneDetection(this.mCurrentConfigurationInternal, str2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void notifyStateChangeListenersAsynchronously() {
        for (com.android.server.timezonedetector.StateChangeListener stateChangeListener : this.mStateChangeListeners) {
            com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.Environment environment = this.mEnvironment;
            java.util.Objects.requireNonNull(stateChangeListener);
            environment.runAsync(new com.android.server.timedetector.TimeDetectorStrategyImpl$$ExternalSyntheticLambda0(stateChangeListener));
        }
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategy
    public synchronized void addChangeListener(com.android.server.timezonedetector.StateChangeListener stateChangeListener) {
        this.mStateChangeListeners.add(stateChangeListener);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategy
    public synchronized boolean confirmTimeZone(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        java.lang.String deviceTimeZone = this.mEnvironment.getDeviceTimeZone();
        if (!deviceTimeZone.equals(str)) {
            return false;
        }
        if (this.mEnvironment.getDeviceTimeZoneConfidence() < 100) {
            this.mEnvironment.setDeviceTimeZoneAndConfidence(deviceTimeZone, 100, "confirmTimeZone: timeZoneId=" + str);
        }
        return true;
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategy
    public synchronized android.app.time.TimeZoneState getTimeZoneState() {
        return new android.app.time.TimeZoneState(this.mEnvironment.getDeviceTimeZone(), this.mEnvironment.getDeviceTimeZoneConfidence() < 100);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategy
    public void setTimeZoneState(@android.annotation.NonNull android.app.time.TimeZoneState timeZoneState) {
        java.util.Objects.requireNonNull(timeZoneState);
        this.mEnvironment.setDeviceTimeZoneAndConfidence(timeZoneState.getId(), timeZoneState.getUserShouldConfirmId() ? 0 : 100, "setTimeZoneState()");
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategy
    public synchronized void handleLocationAlgorithmEvent(@android.annotation.NonNull com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent) {
        try {
            com.android.server.timezonedetector.ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
            java.util.Objects.requireNonNull(locationAlgorithmEvent);
            this.mLatestLocationAlgorithmEvent.set(locationAlgorithmEvent);
            if (updateDetectorStatus()) {
                notifyStateChangeListenersAsynchronously();
            }
            if (locationAlgorithmEvent.getAlgorithmStatus().couldEnableTelephonyFallback()) {
                enableTelephonyTimeZoneFallback("handleLocationAlgorithmEvent(), event=" + locationAlgorithmEvent);
            } else {
                disableTelephonyFallbackIfNeeded();
            }
            doAutoTimeZoneDetection(configurationInternal, "New location algorithm event received. event=" + locationAlgorithmEvent);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategy
    public synchronized boolean suggestManualTimeZone(int i, @android.annotation.NonNull android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion, boolean z) {
        com.android.server.timezonedetector.ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
        if (configurationInternal.getUserId() != i) {
            android.util.Slog.w(LOG_TAG, "Manual suggestion received but user != current user, userId=" + i + " suggestion=" + manualTimeZoneSuggestion);
            return false;
        }
        java.util.Objects.requireNonNull(manualTimeZoneSuggestion);
        java.lang.String zoneId = manualTimeZoneSuggestion.getZoneId();
        java.lang.String str = "Manual time suggestion received: suggestion=" + manualTimeZoneSuggestion;
        android.app.time.TimeZoneCapabilities asCapabilities = configurationInternal.asCapabilities(z);
        if (asCapabilities.getSetManualTimeZoneCapability() != 40) {
            android.util.Slog.i(LOG_TAG, "User does not have the capability needed to set the time zone manually: capabilities=" + asCapabilities + ", timeZoneId=" + zoneId + ", cause=" + str);
            return false;
        }
        this.mLatestManualSuggestion.set(manualTimeZoneSuggestion);
        setDeviceTimeZoneIfRequired(zoneId, str);
        return true;
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategy
    public synchronized void suggestTelephonyTimeZone(@android.annotation.NonNull android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion) {
        com.android.server.timezonedetector.ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
        java.util.Objects.requireNonNull(telephonyTimeZoneSuggestion);
        this.mTelephonySuggestionsBySlotIndex.put(java.lang.Integer.valueOf(telephonyTimeZoneSuggestion.getSlotIndex()), new com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.QualifiedTelephonyTimeZoneSuggestion(telephonyTimeZoneSuggestion, scoreTelephonySuggestion(telephonyTimeZoneSuggestion)));
        doAutoTimeZoneDetection(configurationInternal, "New telephony time zone suggested. suggestion=" + telephonyTimeZoneSuggestion);
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategy
    public synchronized void enableTelephonyTimeZoneFallback(@android.annotation.NonNull java.lang.String str) {
        if (!((java.lang.Boolean) this.mTelephonyTimeZoneFallbackEnabled.getValue()).booleanValue()) {
            com.android.server.timezonedetector.ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
            this.mTelephonyTimeZoneFallbackEnabled = new android.os.TimestampedValue<>(this.mEnvironment.elapsedRealtimeMillis(), true);
            logTimeZoneDebugInfo("enableTelephonyTimeZoneFallback:  reason=" + str + ", currentUserConfig=" + configurationInternal + ", mTelephonyTimeZoneFallbackEnabled=" + this.mTelephonyTimeZoneFallbackEnabled);
            disableTelephonyFallbackIfNeeded();
            if (configurationInternal.isTelephonyFallbackSupported()) {
                doAutoTimeZoneDetection(configurationInternal, str);
            }
        }
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategy
    @android.annotation.NonNull
    public synchronized com.android.server.timezonedetector.MetricsTimeZoneDetectorState generateMetricsState() {
        com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.QualifiedTelephonyTimeZoneSuggestion findBestTelephonySuggestion;
        findBestTelephonySuggestion = findBestTelephonySuggestion();
        return com.android.server.timezonedetector.MetricsTimeZoneDetectorState.create(new com.android.server.timezonedetector.OrdinalGenerator(new com.android.server.timezonedetector.TimeZoneCanonicalizer()), this.mCurrentConfigurationInternal, this.mEnvironment.getDeviceTimeZone(), getLatestManualSuggestion(), findBestTelephonySuggestion == null ? null : findBestTelephonySuggestion.suggestion, getLatestLocationAlgorithmEvent());
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategy
    public boolean isTelephonyTimeZoneDetectionSupported() {
        boolean isTelephonyDetectionSupported;
        synchronized (this) {
            isTelephonyDetectionSupported = this.mCurrentConfigurationInternal.isTelephonyDetectionSupported();
        }
        return isTelephonyDetectionSupported;
    }

    @Override // com.android.server.timezonedetector.TimeZoneDetectorStrategy
    public boolean isGeoTimeZoneDetectionSupported() {
        boolean isGeoDetectionSupported;
        synchronized (this) {
            isGeoDetectionSupported = this.mCurrentConfigurationInternal.isGeoDetectionSupported();
        }
        return isGeoDetectionSupported;
    }

    private static int scoreTelephonySuggestion(@android.annotation.NonNull android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion) {
        if (telephonyTimeZoneSuggestion.getZoneId() == null) {
            return 0;
        }
        if (telephonyTimeZoneSuggestion.getMatchType() == 5 || telephonyTimeZoneSuggestion.getMatchType() == 4) {
            return 4;
        }
        if (telephonyTimeZoneSuggestion.getQuality() == 1) {
            return 3;
        }
        if (telephonyTimeZoneSuggestion.getQuality() == 2) {
            return 2;
        }
        if (telephonyTimeZoneSuggestion.getQuality() == 3) {
            return 1;
        }
        throw new java.lang.AssertionError();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void doAutoTimeZoneDetection(@android.annotation.NonNull com.android.server.timezonedetector.ConfigurationInternal configurationInternal, @android.annotation.NonNull java.lang.String str) {
        int detectionMode = configurationInternal.getDetectionMode();
        switch (detectionMode) {
            case 0:
                android.util.Slog.i(LOG_TAG, "Unknown detection mode: " + detectionMode + ", is location off?");
                break;
            case 1:
                break;
            case 2:
                if (!doGeolocationTimeZoneDetection(str) && ((java.lang.Boolean) this.mTelephonyTimeZoneFallbackEnabled.getValue()).booleanValue() && configurationInternal.isTelephonyFallbackSupported()) {
                    doTelephonyTimeZoneDetection(str + ", telephony fallback mode");
                    break;
                }
                break;
            case 3:
                doTelephonyTimeZoneDetection(str);
                break;
            default:
                android.util.Slog.wtf(LOG_TAG, "Unknown detection mode: " + detectionMode);
                break;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean doGeolocationTimeZoneDetection(@android.annotation.NonNull java.lang.String str) {
        java.util.List<java.lang.String> zoneIds;
        com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent = this.mLatestLocationAlgorithmEvent.get();
        if (locationAlgorithmEvent == null || locationAlgorithmEvent.getSuggestion() == null || (zoneIds = locationAlgorithmEvent.getSuggestion().getZoneIds()) == null) {
            return false;
        }
        if (zoneIds.isEmpty()) {
            return true;
        }
        java.lang.String deviceTimeZone = this.mEnvironment.getDeviceTimeZone();
        if (!zoneIds.contains(deviceTimeZone)) {
            deviceTimeZone = zoneIds.get(0);
        }
        setDeviceTimeZoneIfRequired(deviceTimeZone, str);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void disableTelephonyFallbackIfNeeded() {
        com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent = this.mLatestLocationAlgorithmEvent.get();
        if (locationAlgorithmEvent == null) {
            return;
        }
        com.android.server.timezonedetector.GeolocationTimeZoneSuggestion suggestion = locationAlgorithmEvent.getSuggestion();
        if (((suggestion == null || suggestion.getZoneIds() == null) ? false : true) && ((java.lang.Boolean) this.mTelephonyTimeZoneFallbackEnabled.getValue()).booleanValue()) {
            if (suggestion.getEffectiveFromElapsedMillis() > this.mTelephonyTimeZoneFallbackEnabled.getReferenceTimeMillis()) {
                this.mTelephonyTimeZoneFallbackEnabled = new android.os.TimestampedValue<>(this.mEnvironment.elapsedRealtimeMillis(), false);
                logTimeZoneDebugInfo("disableTelephonyFallbackIfNeeded: mTelephonyTimeZoneFallbackEnabled=" + this.mTelephonyTimeZoneFallbackEnabled);
            }
        }
    }

    private void logTimeZoneDebugInfo(@android.annotation.NonNull java.lang.String str) {
        this.mEnvironment.addDebugLogEntry(str);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void doTelephonyTimeZoneDetection(@android.annotation.NonNull java.lang.String str) {
        com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.QualifiedTelephonyTimeZoneSuggestion findBestTelephonySuggestion = findBestTelephonySuggestion();
        if (findBestTelephonySuggestion == null) {
            return;
        }
        if (!(findBestTelephonySuggestion.score >= 2)) {
            return;
        }
        java.lang.String zoneId = findBestTelephonySuggestion.suggestion.getZoneId();
        if (zoneId == null) {
            android.util.Slog.w(LOG_TAG, "Empty zone suggestion scored higher than expected. This is an error: bestTelephonySuggestion=" + findBestTelephonySuggestion + ", detectionReason=" + str);
            return;
        }
        setDeviceTimeZoneIfRequired(zoneId, "Found good suggestion: bestTelephonySuggestion=" + findBestTelephonySuggestion + ", detectionReason=" + str);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void setDeviceTimeZoneIfRequired(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        java.lang.String deviceTimeZone = this.mEnvironment.getDeviceTimeZone();
        int deviceTimeZoneConfidence = this.mEnvironment.getDeviceTimeZoneConfidence();
        if (str.equals(deviceTimeZone) && 100 <= deviceTimeZoneConfidence) {
            return;
        }
        this.mEnvironment.setDeviceTimeZoneAndConfidence(str, 100, "Set device time zone or higher confidence: newZoneId=" + str + ", cause=" + str2 + ", newConfidence=100");
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.QualifiedTelephonyTimeZoneSuggestion findBestTelephonySuggestion() {
        com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.QualifiedTelephonyTimeZoneSuggestion qualifiedTelephonyTimeZoneSuggestion = null;
        for (int i = 0; i < this.mTelephonySuggestionsBySlotIndex.size(); i++) {
            com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.QualifiedTelephonyTimeZoneSuggestion valueAt = this.mTelephonySuggestionsBySlotIndex.valueAt(i);
            if (valueAt != null && (qualifiedTelephonyTimeZoneSuggestion == null || valueAt.score > qualifiedTelephonyTimeZoneSuggestion.score || (valueAt.score == qualifiedTelephonyTimeZoneSuggestion.score && valueAt.suggestion.getSlotIndex() < qualifiedTelephonyTimeZoneSuggestion.suggestion.getSlotIndex()))) {
                qualifiedTelephonyTimeZoneSuggestion = valueAt;
            }
        }
        return qualifiedTelephonyTimeZoneSuggestion;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public synchronized com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.QualifiedTelephonyTimeZoneSuggestion findBestTelephonySuggestionForTests() {
        return findBestTelephonySuggestion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleConfigurationInternalMaybeChanged() {
        updateCurrentConfigurationInternalIfRequired("handleConfigurationInternalMaybeChanged:");
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean updateDetectorStatus() {
        android.app.time.TimeZoneDetectorStatus createTimeZoneDetectorStatus = createTimeZoneDetectorStatus(this.mCurrentConfigurationInternal, this.mLatestLocationAlgorithmEvent.get());
        boolean z = !createTimeZoneDetectorStatus.equals(this.mDetectorStatus);
        if (z) {
            this.mDetectorStatus = createTimeZoneDetectorStatus;
        }
        return z;
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public synchronized void dump(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        indentingPrintWriter.println("TimeZoneDetectorStrategy:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mCurrentConfigurationInternal=" + this.mCurrentConfigurationInternal);
        indentingPrintWriter.println("mDetectorStatus=" + this.mDetectorStatus);
        indentingPrintWriter.println("[Capabilities=" + this.mCurrentConfigurationInternal.asCapabilities(false) + "]");
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("mEnvironment.getDeviceTimeZone()=");
        sb.append(this.mEnvironment.getDeviceTimeZone());
        indentingPrintWriter.println(sb.toString());
        indentingPrintWriter.println("mEnvironment.getDeviceTimeZoneConfidence()=" + this.mEnvironment.getDeviceTimeZoneConfidence());
        indentingPrintWriter.println("Misc state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mTelephonyTimeZoneFallbackEnabled=" + formatDebugString(this.mTelephonyTimeZoneFallbackEnabled));
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Time zone debug log:");
        indentingPrintWriter.increaseIndent();
        this.mEnvironment.dumpDebugLog(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Manual suggestion history:");
        indentingPrintWriter.increaseIndent();
        this.mLatestManualSuggestion.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Location algorithm event history:");
        indentingPrintWriter.increaseIndent();
        this.mLatestLocationAlgorithmEvent.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Telephony suggestion history:");
        indentingPrintWriter.increaseIndent();
        this.mTelephonySuggestionsBySlotIndex.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public synchronized android.app.timezonedetector.ManualTimeZoneSuggestion getLatestManualSuggestion() {
        return this.mLatestManualSuggestion.get();
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public synchronized com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.QualifiedTelephonyTimeZoneSuggestion getLatestTelephonySuggestion(int i) {
        return this.mTelephonySuggestionsBySlotIndex.get(java.lang.Integer.valueOf(i));
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public synchronized com.android.server.timezonedetector.LocationAlgorithmEvent getLatestLocationAlgorithmEvent() {
        return this.mLatestLocationAlgorithmEvent.get();
    }

    @com.android.internal.annotations.VisibleForTesting
    public synchronized boolean isTelephonyFallbackEnabledForTests() {
        return ((java.lang.Boolean) this.mTelephonyTimeZoneFallbackEnabled.getValue()).booleanValue();
    }

    @com.android.internal.annotations.VisibleForTesting
    public synchronized com.android.server.timezonedetector.ConfigurationInternal getCachedCapabilitiesAndConfigForTests() {
        return this.mCurrentConfigurationInternal;
    }

    @com.android.internal.annotations.VisibleForTesting
    public synchronized android.app.time.TimeZoneDetectorStatus getCachedDetectorStatusForTests() {
        return this.mDetectorStatus;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static final class QualifiedTelephonyTimeZoneSuggestion {

        @com.android.internal.annotations.VisibleForTesting
        public final int score;

        @com.android.internal.annotations.VisibleForTesting
        public final android.app.timezonedetector.TelephonyTimeZoneSuggestion suggestion;

        @com.android.internal.annotations.VisibleForTesting
        public QualifiedTelephonyTimeZoneSuggestion(android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion, int i) {
            this.suggestion = telephonyTimeZoneSuggestion;
            this.score = i;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.QualifiedTelephonyTimeZoneSuggestion.class != obj.getClass()) {
                return false;
            }
            com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.QualifiedTelephonyTimeZoneSuggestion qualifiedTelephonyTimeZoneSuggestion = (com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl.QualifiedTelephonyTimeZoneSuggestion) obj;
            if (this.score == qualifiedTelephonyTimeZoneSuggestion.score && this.suggestion.equals(qualifiedTelephonyTimeZoneSuggestion.suggestion)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.score), this.suggestion);
        }

        public java.lang.String toString() {
            return "QualifiedTelephonyTimeZoneSuggestion{suggestion=" + this.suggestion + ", score=" + this.score + '}';
        }
    }

    private static java.lang.String formatDebugString(android.os.TimestampedValue<?> timestampedValue) {
        return timestampedValue.getValue() + " @ " + java.time.Duration.ofMillis(timestampedValue.getReferenceTimeMillis());
    }

    @android.annotation.NonNull
    private static android.app.time.TimeZoneDetectorStatus createTimeZoneDetectorStatus(@android.annotation.NonNull com.android.server.timezonedetector.ConfigurationInternal configurationInternal, @android.annotation.Nullable com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent) {
        int i;
        if (!configurationInternal.isAutoDetectionSupported()) {
            i = 1;
        } else if (configurationInternal.getAutoDetectionEnabledBehavior()) {
            i = 3;
        } else {
            i = 2;
        }
        return new android.app.time.TimeZoneDetectorStatus(i, createTelephonyAlgorithmStatus(configurationInternal), createLocationAlgorithmStatus(configurationInternal, locationAlgorithmEvent));
    }

    @android.annotation.NonNull
    private static android.app.time.LocationTimeZoneAlgorithmStatus createLocationAlgorithmStatus(com.android.server.timezonedetector.ConfigurationInternal configurationInternal, com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent) {
        if (locationAlgorithmEvent != null) {
            return locationAlgorithmEvent.getAlgorithmStatus();
        }
        if (!configurationInternal.isGeoDetectionSupported()) {
            return android.app.time.LocationTimeZoneAlgorithmStatus.NOT_SUPPORTED;
        }
        if (configurationInternal.isGeoDetectionExecutionEnabled()) {
            return android.app.time.LocationTimeZoneAlgorithmStatus.RUNNING_NOT_REPORTED;
        }
        return android.app.time.LocationTimeZoneAlgorithmStatus.NOT_RUNNING;
    }

    @android.annotation.NonNull
    private static android.app.time.TelephonyTimeZoneAlgorithmStatus createTelephonyAlgorithmStatus(@android.annotation.NonNull com.android.server.timezonedetector.ConfigurationInternal configurationInternal) {
        int i;
        if (!configurationInternal.isTelephonyDetectionSupported()) {
            i = 1;
        } else {
            i = 3;
        }
        return new android.app.time.TelephonyTimeZoneAlgorithmStatus(i);
    }
}
