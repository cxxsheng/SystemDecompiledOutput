package android.app.wearable;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.app.wearable.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.app.wearable.Flags.FLAG_ENABLE_DATA_REQUEST_OBSERVER_API, false), java.util.Map.entry(android.app.wearable.Flags.FLAG_ENABLE_HOTWORD_WEARABLE_SENSING_API, false), java.util.Map.entry(android.app.wearable.Flags.FLAG_ENABLE_PROVIDE_WEARABLE_CONNECTION_API, false), java.util.Map.entry(android.app.wearable.Flags.FLAG_ENABLE_RESTART_WSS_PROCESS, false), java.util.Map.entry(android.app.wearable.Flags.FLAG_ENABLE_UNSUPPORTED_OPERATION_STATUS_CODE, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.app.wearable.Flags.FLAG_ENABLE_DATA_REQUEST_OBSERVER_API, android.app.wearable.Flags.FLAG_ENABLE_HOTWORD_WEARABLE_SENSING_API, android.app.wearable.Flags.FLAG_ENABLE_PROVIDE_WEARABLE_CONNECTION_API, android.app.wearable.Flags.FLAG_ENABLE_RESTART_WSS_PROCESS, android.app.wearable.Flags.FLAG_ENABLE_UNSUPPORTED_OPERATION_STATUS_CODE, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.app.wearable.FeatureFlags
    public boolean enableDataRequestObserverApi() {
        return getValue(android.app.wearable.Flags.FLAG_ENABLE_DATA_REQUEST_OBSERVER_API);
    }

    @Override // android.app.wearable.FeatureFlags
    public boolean enableHotwordWearableSensingApi() {
        return getValue(android.app.wearable.Flags.FLAG_ENABLE_HOTWORD_WEARABLE_SENSING_API);
    }

    @Override // android.app.wearable.FeatureFlags
    public boolean enableProvideWearableConnectionApi() {
        return getValue(android.app.wearable.Flags.FLAG_ENABLE_PROVIDE_WEARABLE_CONNECTION_API);
    }

    @Override // android.app.wearable.FeatureFlags
    public boolean enableRestartWssProcess() {
        return getValue(android.app.wearable.Flags.FLAG_ENABLE_RESTART_WSS_PROCESS);
    }

    @Override // android.app.wearable.FeatureFlags
    public boolean enableUnsupportedOperationStatusCode() {
        return getValue(android.app.wearable.Flags.FLAG_ENABLE_UNSUPPORTED_OPERATION_STATUS_CODE);
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
