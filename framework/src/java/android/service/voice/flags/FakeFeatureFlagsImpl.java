package android.service.voice.flags;

/* loaded from: classes3.dex */
public class FakeFeatureFlagsImpl implements android.service.voice.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.service.voice.flags.Flags.FLAG_ALLOW_COMPLEX_RESULTS_EGRESS_FROM_VQDS, false), java.util.Map.entry(android.service.voice.flags.Flags.FLAG_ALLOW_FOREGROUND_ACTIVITIES_IN_ON_SHOW, false), java.util.Map.entry(android.service.voice.flags.Flags.FLAG_ALLOW_HOTWORD_BUMP_EGRESS, false), java.util.Map.entry(android.service.voice.flags.Flags.FLAG_ALLOW_SPEAKER_ID_EGRESS, false), java.util.Map.entry(android.service.voice.flags.Flags.FLAG_ALLOW_TRAINING_DATA_EGRESS_FROM_HDS, false), java.util.Map.entry(android.service.voice.flags.Flags.FLAG_ALLOW_VARIOUS_ATTENTION_TYPES, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.service.voice.flags.Flags.FLAG_ALLOW_COMPLEX_RESULTS_EGRESS_FROM_VQDS, android.service.voice.flags.Flags.FLAG_ALLOW_FOREGROUND_ACTIVITIES_IN_ON_SHOW, android.service.voice.flags.Flags.FLAG_ALLOW_HOTWORD_BUMP_EGRESS, android.service.voice.flags.Flags.FLAG_ALLOW_SPEAKER_ID_EGRESS, android.service.voice.flags.Flags.FLAG_ALLOW_TRAINING_DATA_EGRESS_FROM_HDS, android.service.voice.flags.Flags.FLAG_ALLOW_VARIOUS_ATTENTION_TYPES, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.service.voice.flags.FeatureFlags
    public boolean allowComplexResultsEgressFromVqds() {
        return getValue(android.service.voice.flags.Flags.FLAG_ALLOW_COMPLEX_RESULTS_EGRESS_FROM_VQDS);
    }

    @Override // android.service.voice.flags.FeatureFlags
    public boolean allowForegroundActivitiesInOnShow() {
        return getValue(android.service.voice.flags.Flags.FLAG_ALLOW_FOREGROUND_ACTIVITIES_IN_ON_SHOW);
    }

    @Override // android.service.voice.flags.FeatureFlags
    public boolean allowHotwordBumpEgress() {
        return getValue(android.service.voice.flags.Flags.FLAG_ALLOW_HOTWORD_BUMP_EGRESS);
    }

    @Override // android.service.voice.flags.FeatureFlags
    public boolean allowSpeakerIdEgress() {
        return getValue(android.service.voice.flags.Flags.FLAG_ALLOW_SPEAKER_ID_EGRESS);
    }

    @Override // android.service.voice.flags.FeatureFlags
    public boolean allowTrainingDataEgressFromHds() {
        return getValue(android.service.voice.flags.Flags.FLAG_ALLOW_TRAINING_DATA_EGRESS_FROM_HDS);
    }

    @Override // android.service.voice.flags.FeatureFlags
    public boolean allowVariousAttentionTypes() {
        return getValue(android.service.voice.flags.Flags.FLAG_ALLOW_VARIOUS_ATTENTION_TYPES);
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
