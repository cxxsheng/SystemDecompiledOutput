package com.android.server.biometrics;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements com.android.server.biometrics.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.server.biometrics.Flags.FLAG_DE_HIDL, false), java.util.Map.entry(com.android.server.biometrics.Flags.FLAG_FACE_VHAL_FEATURE, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.server.biometrics.Flags.FLAG_DE_HIDL, com.android.server.biometrics.Flags.FLAG_FACE_VHAL_FEATURE, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.server.biometrics.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean deHidl() {
        return getValue(com.android.server.biometrics.Flags.FLAG_DE_HIDL);
    }

    @Override // com.android.server.biometrics.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean faceVhalFeature() {
        return getValue(com.android.server.biometrics.Flags.FLAG_FACE_VHAL_FEATURE);
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
        if (this.mReadOnlyFlagsSet.contains(str)) {
            isOptimizationEnabled();
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

    @com.android.aconfig.annotations.AssumeTrueForR8
    private boolean isOptimizationEnabled() {
        return false;
    }
}
