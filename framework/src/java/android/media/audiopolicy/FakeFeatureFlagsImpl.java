package android.media.audiopolicy;

/* loaded from: classes2.dex */
public class FakeFeatureFlagsImpl implements android.media.audiopolicy.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.media.audiopolicy.Flags.FLAG_AUDIO_MIX_OWNERSHIP, false), java.util.Map.entry(android.media.audiopolicy.Flags.FLAG_AUDIO_MIX_POLICY_ORDERING, false), java.util.Map.entry(android.media.audiopolicy.Flags.FLAG_AUDIO_MIX_TEST_API, false), java.util.Map.entry(android.media.audiopolicy.Flags.FLAG_AUDIO_POLICY_UPDATE_MIXING_RULES_API, false), java.util.Map.entry(android.media.audiopolicy.Flags.FLAG_ENABLE_FADE_MANAGER_CONFIGURATION, false), java.util.Map.entry(android.media.audiopolicy.Flags.FLAG_MULTI_ZONE_AUDIO, false), java.util.Map.entry(android.media.audiopolicy.Flags.FLAG_RECORD_AUDIO_DEVICE_AWARE_PERMISSION, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.media.audiopolicy.Flags.FLAG_AUDIO_MIX_OWNERSHIP, android.media.audiopolicy.Flags.FLAG_AUDIO_MIX_POLICY_ORDERING, android.media.audiopolicy.Flags.FLAG_AUDIO_MIX_TEST_API, android.media.audiopolicy.Flags.FLAG_AUDIO_POLICY_UPDATE_MIXING_RULES_API, android.media.audiopolicy.Flags.FLAG_ENABLE_FADE_MANAGER_CONFIGURATION, android.media.audiopolicy.Flags.FLAG_MULTI_ZONE_AUDIO, android.media.audiopolicy.Flags.FLAG_RECORD_AUDIO_DEVICE_AWARE_PERMISSION, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioMixOwnership() {
        return getValue(android.media.audiopolicy.Flags.FLAG_AUDIO_MIX_OWNERSHIP);
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioMixPolicyOrdering() {
        return getValue(android.media.audiopolicy.Flags.FLAG_AUDIO_MIX_POLICY_ORDERING);
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioMixTestApi() {
        return getValue(android.media.audiopolicy.Flags.FLAG_AUDIO_MIX_TEST_API);
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioPolicyUpdateMixingRulesApi() {
        return getValue(android.media.audiopolicy.Flags.FLAG_AUDIO_POLICY_UPDATE_MIXING_RULES_API);
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean enableFadeManagerConfiguration() {
        return getValue(android.media.audiopolicy.Flags.FLAG_ENABLE_FADE_MANAGER_CONFIGURATION);
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean multiZoneAudio() {
        return getValue(android.media.audiopolicy.Flags.FLAG_MULTI_ZONE_AUDIO);
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean recordAudioDeviceAwarePermission() {
        return getValue(android.media.audiopolicy.Flags.FLAG_RECORD_AUDIO_DEVICE_AWARE_PERMISSION);
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
