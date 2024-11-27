package android.chre.flags;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.chre.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.chre.flags.Flags.FLAG_CONTEXT_HUB_CALLBACK_UUID_ENABLED, false), java.util.Map.entry(android.chre.flags.Flags.FLAG_FLAG_LOG_NANOAPP_LOAD_METRICS, false), java.util.Map.entry(android.chre.flags.Flags.FLAG_METRICS_REPORTER_IN_THE_DAEMON, false), java.util.Map.entry(android.chre.flags.Flags.FLAG_RELIABLE_MESSAGE, false), java.util.Map.entry(android.chre.flags.Flags.FLAG_RELIABLE_MESSAGE_IMPLEMENTATION, false), java.util.Map.entry(android.chre.flags.Flags.FLAG_REMOVE_AP_WAKEUP_METRIC_REPORT_LIMIT, false), java.util.Map.entry(android.chre.flags.Flags.FLAG_WAIT_FOR_PRELOADED_NANOAPP_START, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.chre.flags.Flags.FLAG_CONTEXT_HUB_CALLBACK_UUID_ENABLED, android.chre.flags.Flags.FLAG_FLAG_LOG_NANOAPP_LOAD_METRICS, android.chre.flags.Flags.FLAG_METRICS_REPORTER_IN_THE_DAEMON, android.chre.flags.Flags.FLAG_RELIABLE_MESSAGE, android.chre.flags.Flags.FLAG_RELIABLE_MESSAGE_IMPLEMENTATION, android.chre.flags.Flags.FLAG_REMOVE_AP_WAKEUP_METRIC_REPORT_LIMIT, android.chre.flags.Flags.FLAG_WAIT_FOR_PRELOADED_NANOAPP_START, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean contextHubCallbackUuidEnabled() {
        return getValue(android.chre.flags.Flags.FLAG_CONTEXT_HUB_CALLBACK_UUID_ENABLED);
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean flagLogNanoappLoadMetrics() {
        return getValue(android.chre.flags.Flags.FLAG_FLAG_LOG_NANOAPP_LOAD_METRICS);
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean metricsReporterInTheDaemon() {
        return getValue(android.chre.flags.Flags.FLAG_METRICS_REPORTER_IN_THE_DAEMON);
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessage() {
        return getValue(android.chre.flags.Flags.FLAG_RELIABLE_MESSAGE);
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean reliableMessageImplementation() {
        return getValue(android.chre.flags.Flags.FLAG_RELIABLE_MESSAGE_IMPLEMENTATION);
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean removeApWakeupMetricReportLimit() {
        return getValue(android.chre.flags.Flags.FLAG_REMOVE_AP_WAKEUP_METRIC_REPORT_LIMIT);
    }

    @Override // android.chre.flags.FeatureFlags
    public boolean waitForPreloadedNanoappStart() {
        return getValue(android.chre.flags.Flags.FLAG_WAIT_FOR_PRELOADED_NANOAPP_START);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    private boolean isOptimizationEnabled() {
        return false;
    }
}
