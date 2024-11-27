package android.companion.virtual.flags;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.companion.virtual.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_CONSISTENT_DISPLAY_FLAGS, false), java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_CROSS_DEVICE_CLIPBOARD, false), java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_DYNAMIC_POLICY, false), java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_ENABLE_NATIVE_VDM, false), java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_EXPRESS_METRICS, false), java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_INTERACTIVE_SCREEN_MIRROR, false), java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_PERSISTENT_DEVICE_ID_API, false), java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_STREAM_CAMERA, false), java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_STREAM_PERMISSIONS, false), java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_VDM_CUSTOM_HOME, false), java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_VDM_CUSTOM_IME, false), java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_VDM_PUBLIC_APIS, false), java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_VIRTUAL_CAMERA, false), java.util.Map.entry(android.companion.virtual.flags.Flags.FLAG_VIRTUAL_STYLUS, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.companion.virtual.flags.Flags.FLAG_CONSISTENT_DISPLAY_FLAGS, android.companion.virtual.flags.Flags.FLAG_CROSS_DEVICE_CLIPBOARD, android.companion.virtual.flags.Flags.FLAG_DYNAMIC_POLICY, android.companion.virtual.flags.Flags.FLAG_ENABLE_NATIVE_VDM, android.companion.virtual.flags.Flags.FLAG_EXPRESS_METRICS, android.companion.virtual.flags.Flags.FLAG_INTERACTIVE_SCREEN_MIRROR, android.companion.virtual.flags.Flags.FLAG_PERSISTENT_DEVICE_ID_API, android.companion.virtual.flags.Flags.FLAG_STREAM_CAMERA, android.companion.virtual.flags.Flags.FLAG_STREAM_PERMISSIONS, android.companion.virtual.flags.Flags.FLAG_VDM_CUSTOM_HOME, android.companion.virtual.flags.Flags.FLAG_VDM_CUSTOM_IME, android.companion.virtual.flags.Flags.FLAG_VDM_PUBLIC_APIS, android.companion.virtual.flags.Flags.FLAG_VIRTUAL_CAMERA, android.companion.virtual.flags.Flags.FLAG_VIRTUAL_STYLUS, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean consistentDisplayFlags() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_CONSISTENT_DISPLAY_FLAGS);
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean crossDeviceClipboard() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_CROSS_DEVICE_CLIPBOARD);
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean dynamicPolicy() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_DYNAMIC_POLICY);
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean enableNativeVdm() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_ENABLE_NATIVE_VDM);
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean expressMetrics() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_EXPRESS_METRICS);
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean interactiveScreenMirror() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_INTERACTIVE_SCREEN_MIRROR);
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean persistentDeviceIdApi() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_PERSISTENT_DEVICE_ID_API);
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean streamCamera() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_STREAM_CAMERA);
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean streamPermissions() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_STREAM_PERMISSIONS);
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean vdmCustomHome() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_VDM_CUSTOM_HOME);
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean vdmCustomIme() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_VDM_CUSTOM_IME);
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean vdmPublicApis() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_VDM_PUBLIC_APIS);
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean virtualCamera() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_VIRTUAL_CAMERA);
    }

    @Override // android.companion.virtual.flags.FeatureFlags
    public boolean virtualStylus() {
        return getValue(android.companion.virtual.flags.Flags.FLAG_VIRTUAL_STYLUS);
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
