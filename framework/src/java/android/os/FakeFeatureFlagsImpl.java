package android.os;

/* loaded from: classes3.dex */
public class FakeFeatureFlagsImpl implements android.os.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.os.Flags.FLAG_ADPF_GPU_REPORT_ACTUAL_WORK_DURATION, false), java.util.Map.entry(android.os.Flags.FLAG_ADPF_PREFER_POWER_EFFICIENCY, false), java.util.Map.entry(android.os.Flags.FLAG_ADPF_USE_FMQ_CHANNEL, false), java.util.Map.entry(android.os.Flags.FLAG_ALLOW_PRIVATE_PROFILE, false), java.util.Map.entry(android.os.Flags.FLAG_ALLOW_THERMAL_HEADROOM_THRESHOLDS, false), java.util.Map.entry(android.os.Flags.FLAG_ANDROID_OS_BUILD_VANILLA_ICE_CREAM, false), java.util.Map.entry(android.os.Flags.FLAG_BATTERY_PART_STATUS_API, false), java.util.Map.entry(android.os.Flags.FLAG_BATTERY_SAVER_SUPPORTED_CHECK_API, false), java.util.Map.entry(android.os.Flags.FLAG_BATTERY_SERVICE_SUPPORT_CURRENT_ADB_COMMAND, false), java.util.Map.entry(android.os.Flags.FLAG_BUGREPORT_MODE_MAX_VALUE, false), java.util.Map.entry(android.os.Flags.FLAG_DISALLOW_CELLULAR_NULL_CIPHERS_RESTRICTION, false), java.util.Map.entry(android.os.Flags.FLAG_MESSAGE_QUEUE_TAIL_TRACKING, false), java.util.Map.entry(android.os.Flags.FLAG_REMOVE_APP_PROFILER_PSS_COLLECTION, false), java.util.Map.entry(android.os.Flags.FLAG_SECURITY_STATE_SERVICE, false), java.util.Map.entry(android.os.Flags.FLAG_STATE_OF_HEALTH_PUBLIC, false), java.util.Map.entry(android.os.Flags.FLAG_STORAGE_LIFETIME_API, false), java.util.Map.entry(android.os.Flags.FLAG_STRICT_MODE_RESTRICTED_NETWORK, false), java.util.Map.entry(android.os.Flags.FLAG_TELEMETRY_APIS_FRAMEWORK_INITIALIZATION, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.os.Flags.FLAG_ADPF_GPU_REPORT_ACTUAL_WORK_DURATION, android.os.Flags.FLAG_ADPF_PREFER_POWER_EFFICIENCY, android.os.Flags.FLAG_ADPF_USE_FMQ_CHANNEL, android.os.Flags.FLAG_ALLOW_PRIVATE_PROFILE, android.os.Flags.FLAG_ALLOW_THERMAL_HEADROOM_THRESHOLDS, android.os.Flags.FLAG_ANDROID_OS_BUILD_VANILLA_ICE_CREAM, android.os.Flags.FLAG_BATTERY_PART_STATUS_API, android.os.Flags.FLAG_BATTERY_SAVER_SUPPORTED_CHECK_API, android.os.Flags.FLAG_BATTERY_SERVICE_SUPPORT_CURRENT_ADB_COMMAND, android.os.Flags.FLAG_BUGREPORT_MODE_MAX_VALUE, android.os.Flags.FLAG_DISALLOW_CELLULAR_NULL_CIPHERS_RESTRICTION, android.os.Flags.FLAG_MESSAGE_QUEUE_TAIL_TRACKING, android.os.Flags.FLAG_REMOVE_APP_PROFILER_PSS_COLLECTION, android.os.Flags.FLAG_SECURITY_STATE_SERVICE, android.os.Flags.FLAG_STATE_OF_HEALTH_PUBLIC, android.os.Flags.FLAG_STORAGE_LIFETIME_API, android.os.Flags.FLAG_STRICT_MODE_RESTRICTED_NETWORK, android.os.Flags.FLAG_TELEMETRY_APIS_FRAMEWORK_INITIALIZATION, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.os.FeatureFlags
    public boolean adpfGpuReportActualWorkDuration() {
        return getValue(android.os.Flags.FLAG_ADPF_GPU_REPORT_ACTUAL_WORK_DURATION);
    }

    @Override // android.os.FeatureFlags
    public boolean adpfPreferPowerEfficiency() {
        return getValue(android.os.Flags.FLAG_ADPF_PREFER_POWER_EFFICIENCY);
    }

    @Override // android.os.FeatureFlags
    public boolean adpfUseFmqChannel() {
        return getValue(android.os.Flags.FLAG_ADPF_USE_FMQ_CHANNEL);
    }

    @Override // android.os.FeatureFlags
    public boolean allowPrivateProfile() {
        return getValue(android.os.Flags.FLAG_ALLOW_PRIVATE_PROFILE);
    }

    @Override // android.os.FeatureFlags
    public boolean allowThermalHeadroomThresholds() {
        return getValue(android.os.Flags.FLAG_ALLOW_THERMAL_HEADROOM_THRESHOLDS);
    }

    @Override // android.os.FeatureFlags
    public boolean androidOsBuildVanillaIceCream() {
        return getValue(android.os.Flags.FLAG_ANDROID_OS_BUILD_VANILLA_ICE_CREAM);
    }

    @Override // android.os.FeatureFlags
    public boolean batteryPartStatusApi() {
        return getValue(android.os.Flags.FLAG_BATTERY_PART_STATUS_API);
    }

    @Override // android.os.FeatureFlags
    public boolean batterySaverSupportedCheckApi() {
        return getValue(android.os.Flags.FLAG_BATTERY_SAVER_SUPPORTED_CHECK_API);
    }

    @Override // android.os.FeatureFlags
    public boolean batteryServiceSupportCurrentAdbCommand() {
        return getValue(android.os.Flags.FLAG_BATTERY_SERVICE_SUPPORT_CURRENT_ADB_COMMAND);
    }

    @Override // android.os.FeatureFlags
    public boolean bugreportModeMaxValue() {
        return getValue(android.os.Flags.FLAG_BUGREPORT_MODE_MAX_VALUE);
    }

    @Override // android.os.FeatureFlags
    public boolean disallowCellularNullCiphersRestriction() {
        return getValue(android.os.Flags.FLAG_DISALLOW_CELLULAR_NULL_CIPHERS_RESTRICTION);
    }

    @Override // android.os.FeatureFlags
    public boolean messageQueueTailTracking() {
        return getValue(android.os.Flags.FLAG_MESSAGE_QUEUE_TAIL_TRACKING);
    }

    @Override // android.os.FeatureFlags
    public boolean removeAppProfilerPssCollection() {
        return getValue(android.os.Flags.FLAG_REMOVE_APP_PROFILER_PSS_COLLECTION);
    }

    @Override // android.os.FeatureFlags
    public boolean securityStateService() {
        return getValue(android.os.Flags.FLAG_SECURITY_STATE_SERVICE);
    }

    @Override // android.os.FeatureFlags
    public boolean stateOfHealthPublic() {
        return getValue(android.os.Flags.FLAG_STATE_OF_HEALTH_PUBLIC);
    }

    @Override // android.os.FeatureFlags
    public boolean storageLifetimeApi() {
        return getValue(android.os.Flags.FLAG_STORAGE_LIFETIME_API);
    }

    @Override // android.os.FeatureFlags
    public boolean strictModeRestrictedNetwork() {
        return getValue(android.os.Flags.FLAG_STRICT_MODE_RESTRICTED_NETWORK);
    }

    @Override // android.os.FeatureFlags
    public boolean telemetryApisFrameworkInitialization() {
        return getValue(android.os.Flags.FLAG_TELEMETRY_APIS_FRAMEWORK_INITIALIZATION);
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
