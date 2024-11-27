package android.location.flags;

/* loaded from: classes2.dex */
public class FakeFeatureFlagsImpl implements android.location.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.location.flags.Flags.FLAG_FIX_SERVICE_WATCHER, false), java.util.Map.entry(android.location.flags.Flags.FLAG_GEOID_HEIGHTS_VIA_ALTITUDE_HAL, false), java.util.Map.entry(android.location.flags.Flags.FLAG_GNSS_API_MEASUREMENT_REQUEST_WORK_SOURCE, false), java.util.Map.entry(android.location.flags.Flags.FLAG_GNSS_API_NAVIC_L1, false), java.util.Map.entry(android.location.flags.Flags.FLAG_GNSS_CALL_STOP_BEFORE_SET_POSITION_MODE, false), java.util.Map.entry(android.location.flags.Flags.FLAG_GNSS_CONFIGURATION_FROM_RESOURCE, false), java.util.Map.entry(android.location.flags.Flags.FLAG_LOCATION_BYPASS, false), java.util.Map.entry(android.location.flags.Flags.FLAG_LOCATION_VALIDATION, false), java.util.Map.entry(android.location.flags.Flags.FLAG_NEW_GEOCODER, false), java.util.Map.entry(android.location.flags.Flags.FLAG_RELEASE_SUPL_CONNECTION_ON_TIMEOUT, false), java.util.Map.entry(android.location.flags.Flags.FLAG_REPLACE_FUTURE_ELAPSED_REALTIME_JNI, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.location.flags.Flags.FLAG_FIX_SERVICE_WATCHER, android.location.flags.Flags.FLAG_GEOID_HEIGHTS_VIA_ALTITUDE_HAL, android.location.flags.Flags.FLAG_GNSS_API_MEASUREMENT_REQUEST_WORK_SOURCE, android.location.flags.Flags.FLAG_GNSS_API_NAVIC_L1, android.location.flags.Flags.FLAG_GNSS_CALL_STOP_BEFORE_SET_POSITION_MODE, android.location.flags.Flags.FLAG_GNSS_CONFIGURATION_FROM_RESOURCE, android.location.flags.Flags.FLAG_LOCATION_BYPASS, android.location.flags.Flags.FLAG_LOCATION_VALIDATION, android.location.flags.Flags.FLAG_NEW_GEOCODER, android.location.flags.Flags.FLAG_RELEASE_SUPL_CONNECTION_ON_TIMEOUT, android.location.flags.Flags.FLAG_REPLACE_FUTURE_ELAPSED_REALTIME_JNI, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.location.flags.FeatureFlags
    public boolean fixServiceWatcher() {
        return getValue(android.location.flags.Flags.FLAG_FIX_SERVICE_WATCHER);
    }

    @Override // android.location.flags.FeatureFlags
    public boolean geoidHeightsViaAltitudeHal() {
        return getValue(android.location.flags.Flags.FLAG_GEOID_HEIGHTS_VIA_ALTITUDE_HAL);
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssApiMeasurementRequestWorkSource() {
        return getValue(android.location.flags.Flags.FLAG_GNSS_API_MEASUREMENT_REQUEST_WORK_SOURCE);
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssApiNavicL1() {
        return getValue(android.location.flags.Flags.FLAG_GNSS_API_NAVIC_L1);
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssCallStopBeforeSetPositionMode() {
        return getValue(android.location.flags.Flags.FLAG_GNSS_CALL_STOP_BEFORE_SET_POSITION_MODE);
    }

    @Override // android.location.flags.FeatureFlags
    public boolean gnssConfigurationFromResource() {
        return getValue(android.location.flags.Flags.FLAG_GNSS_CONFIGURATION_FROM_RESOURCE);
    }

    @Override // android.location.flags.FeatureFlags
    public boolean locationBypass() {
        return getValue(android.location.flags.Flags.FLAG_LOCATION_BYPASS);
    }

    @Override // android.location.flags.FeatureFlags
    public boolean locationValidation() {
        return getValue(android.location.flags.Flags.FLAG_LOCATION_VALIDATION);
    }

    @Override // android.location.flags.FeatureFlags
    public boolean newGeocoder() {
        return getValue(android.location.flags.Flags.FLAG_NEW_GEOCODER);
    }

    @Override // android.location.flags.FeatureFlags
    public boolean releaseSuplConnectionOnTimeout() {
        return getValue(android.location.flags.Flags.FLAG_RELEASE_SUPL_CONNECTION_ON_TIMEOUT);
    }

    @Override // android.location.flags.FeatureFlags
    public boolean replaceFutureElapsedRealtimeJni() {
        return getValue(android.location.flags.Flags.FLAG_REPLACE_FUTURE_ELAPSED_REALTIME_JNI);
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
