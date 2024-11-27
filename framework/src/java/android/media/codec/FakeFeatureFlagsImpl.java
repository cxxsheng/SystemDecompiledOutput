package android.media.codec;

/* loaded from: classes2.dex */
public class FakeFeatureFlagsImpl implements android.media.codec.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.media.codec.Flags.FLAG_AIDL_HAL_INPUT_SURFACE, false), java.util.Map.entry(android.media.codec.Flags.FLAG_DYNAMIC_COLOR_ASPECTS, false), java.util.Map.entry(android.media.codec.Flags.FLAG_HLG_EDITING, false), java.util.Map.entry(android.media.codec.Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC, false), java.util.Map.entry(android.media.codec.Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC_SUPPORT, false), java.util.Map.entry(android.media.codec.Flags.FLAG_LARGE_AUDIO_FRAME_FINISH, false), java.util.Map.entry(android.media.codec.Flags.FLAG_NULL_OUTPUT_SURFACE, false), java.util.Map.entry(android.media.codec.Flags.FLAG_NULL_OUTPUT_SURFACE_SUPPORT, false), java.util.Map.entry(android.media.codec.Flags.FLAG_REGION_OF_INTEREST, false), java.util.Map.entry(android.media.codec.Flags.FLAG_REGION_OF_INTEREST_SUPPORT, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.media.codec.Flags.FLAG_AIDL_HAL_INPUT_SURFACE, android.media.codec.Flags.FLAG_DYNAMIC_COLOR_ASPECTS, android.media.codec.Flags.FLAG_HLG_EDITING, android.media.codec.Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC, android.media.codec.Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC_SUPPORT, android.media.codec.Flags.FLAG_LARGE_AUDIO_FRAME_FINISH, android.media.codec.Flags.FLAG_NULL_OUTPUT_SURFACE, android.media.codec.Flags.FLAG_NULL_OUTPUT_SURFACE_SUPPORT, android.media.codec.Flags.FLAG_REGION_OF_INTEREST, android.media.codec.Flags.FLAG_REGION_OF_INTEREST_SUPPORT, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.media.codec.FeatureFlags
    public boolean aidlHalInputSurface() {
        return getValue(android.media.codec.Flags.FLAG_AIDL_HAL_INPUT_SURFACE);
    }

    @Override // android.media.codec.FeatureFlags
    public boolean dynamicColorAspects() {
        return getValue(android.media.codec.Flags.FLAG_DYNAMIC_COLOR_ASPECTS);
    }

    @Override // android.media.codec.FeatureFlags
    public boolean hlgEditing() {
        return getValue(android.media.codec.Flags.FLAG_HLG_EDITING);
    }

    @Override // android.media.codec.FeatureFlags
    public boolean inProcessSwAudioCodec() {
        return getValue(android.media.codec.Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC);
    }

    @Override // android.media.codec.FeatureFlags
    public boolean inProcessSwAudioCodecSupport() {
        return getValue(android.media.codec.Flags.FLAG_IN_PROCESS_SW_AUDIO_CODEC_SUPPORT);
    }

    @Override // android.media.codec.FeatureFlags
    public boolean largeAudioFrameFinish() {
        return getValue(android.media.codec.Flags.FLAG_LARGE_AUDIO_FRAME_FINISH);
    }

    @Override // android.media.codec.FeatureFlags
    public boolean nullOutputSurface() {
        return getValue(android.media.codec.Flags.FLAG_NULL_OUTPUT_SURFACE);
    }

    @Override // android.media.codec.FeatureFlags
    public boolean nullOutputSurfaceSupport() {
        return getValue(android.media.codec.Flags.FLAG_NULL_OUTPUT_SURFACE_SUPPORT);
    }

    @Override // android.media.codec.FeatureFlags
    public boolean regionOfInterest() {
        return getValue(android.media.codec.Flags.FLAG_REGION_OF_INTEREST);
    }

    @Override // android.media.codec.FeatureFlags
    public boolean regionOfInterestSupport() {
        return getValue(android.media.codec.Flags.FLAG_REGION_OF_INTEREST_SUPPORT);
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
