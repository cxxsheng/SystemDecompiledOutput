package com.android.media.codec.flags;

/* loaded from: classes5.dex */
public class FakeFeatureFlagsImpl implements com.android.media.codec.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.media.codec.flags.Flags.FLAG_AIDL_HAL, false), java.util.Map.entry(com.android.media.codec.flags.Flags.FLAG_CODEC_IMPORTANCE, false), java.util.Map.entry(com.android.media.codec.flags.Flags.FLAG_LARGE_AUDIO_FRAME, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.media.codec.flags.Flags.FLAG_AIDL_HAL, com.android.media.codec.flags.Flags.FLAG_CODEC_IMPORTANCE, com.android.media.codec.flags.Flags.FLAG_LARGE_AUDIO_FRAME, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.media.codec.flags.FeatureFlags
    public boolean aidlHal() {
        return getValue(com.android.media.codec.flags.Flags.FLAG_AIDL_HAL);
    }

    @Override // com.android.media.codec.flags.FeatureFlags
    public boolean codecImportance() {
        return getValue(com.android.media.codec.flags.Flags.FLAG_CODEC_IMPORTANCE);
    }

    @Override // com.android.media.codec.flags.FeatureFlags
    public boolean largeAudioFrame() {
        return getValue(com.android.media.codec.flags.Flags.FLAG_LARGE_AUDIO_FRAME);
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