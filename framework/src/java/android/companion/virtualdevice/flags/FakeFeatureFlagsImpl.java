package android.companion.virtualdevice.flags;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.companion.virtualdevice.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.companion.virtualdevice.flags.Flags.FLAG_CAMERA_DEVICE_AWARENESS, false), java.util.Map.entry(android.companion.virtualdevice.flags.Flags.FLAG_DEVICE_AWARE_RECORD_AUDIO_PERMISSION, false), java.util.Map.entry(android.companion.virtualdevice.flags.Flags.FLAG_METRICS_COLLECTION, false), java.util.Map.entry(android.companion.virtualdevice.flags.Flags.FLAG_VIRTUAL_CAMERA_SERVICE_DISCOVERY, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.companion.virtualdevice.flags.Flags.FLAG_CAMERA_DEVICE_AWARENESS, android.companion.virtualdevice.flags.Flags.FLAG_DEVICE_AWARE_RECORD_AUDIO_PERMISSION, android.companion.virtualdevice.flags.Flags.FLAG_METRICS_COLLECTION, android.companion.virtualdevice.flags.Flags.FLAG_VIRTUAL_CAMERA_SERVICE_DISCOVERY, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean cameraDeviceAwareness() {
        return getValue(android.companion.virtualdevice.flags.Flags.FLAG_CAMERA_DEVICE_AWARENESS);
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean deviceAwareRecordAudioPermission() {
        return getValue(android.companion.virtualdevice.flags.Flags.FLAG_DEVICE_AWARE_RECORD_AUDIO_PERMISSION);
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean metricsCollection() {
        return getValue(android.companion.virtualdevice.flags.Flags.FLAG_METRICS_COLLECTION);
    }

    @Override // android.companion.virtualdevice.flags.FeatureFlags
    public boolean virtualCameraServiceDiscovery() {
        return getValue(android.companion.virtualdevice.flags.Flags.FLAG_VIRTUAL_CAMERA_SERVICE_DISCOVERY);
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
