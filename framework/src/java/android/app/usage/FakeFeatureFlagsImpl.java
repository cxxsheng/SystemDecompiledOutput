package android.app.usage;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.app.usage.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.app.usage.Flags.FLAG_FILTER_BASED_EVENT_QUERY_API, false), java.util.Map.entry(android.app.usage.Flags.FLAG_GET_APP_BYTES_BY_DATA_TYPE_API, false), java.util.Map.entry(android.app.usage.Flags.FLAG_REPORT_USAGE_STATS_PERMISSION, false), java.util.Map.entry(android.app.usage.Flags.FLAG_USE_DEDICATED_HANDLER_THREAD, false), java.util.Map.entry(android.app.usage.Flags.FLAG_USE_PARCELED_LIST, false), java.util.Map.entry(android.app.usage.Flags.FLAG_USER_INTERACTION_TYPE_API, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.app.usage.Flags.FLAG_FILTER_BASED_EVENT_QUERY_API, android.app.usage.Flags.FLAG_GET_APP_BYTES_BY_DATA_TYPE_API, android.app.usage.Flags.FLAG_REPORT_USAGE_STATS_PERMISSION, android.app.usage.Flags.FLAG_USE_DEDICATED_HANDLER_THREAD, android.app.usage.Flags.FLAG_USE_PARCELED_LIST, android.app.usage.Flags.FLAG_USER_INTERACTION_TYPE_API, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.app.usage.FeatureFlags
    public boolean filterBasedEventQueryApi() {
        return getValue(android.app.usage.Flags.FLAG_FILTER_BASED_EVENT_QUERY_API);
    }

    @Override // android.app.usage.FeatureFlags
    public boolean getAppBytesByDataTypeApi() {
        return getValue(android.app.usage.Flags.FLAG_GET_APP_BYTES_BY_DATA_TYPE_API);
    }

    @Override // android.app.usage.FeatureFlags
    public boolean reportUsageStatsPermission() {
        return getValue(android.app.usage.Flags.FLAG_REPORT_USAGE_STATS_PERMISSION);
    }

    @Override // android.app.usage.FeatureFlags
    public boolean useDedicatedHandlerThread() {
        return getValue(android.app.usage.Flags.FLAG_USE_DEDICATED_HANDLER_THREAD);
    }

    @Override // android.app.usage.FeatureFlags
    public boolean useParceledList() {
        return getValue(android.app.usage.Flags.FLAG_USE_PARCELED_LIST);
    }

    @Override // android.app.usage.FeatureFlags
    public boolean userInteractionTypeApi() {
        return getValue(android.app.usage.Flags.FLAG_USER_INTERACTION_TYPE_API);
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
