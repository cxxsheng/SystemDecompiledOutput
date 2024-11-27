package android.media.audio;

/* loaded from: classes2.dex */
public class FakeFeatureFlagsImpl implements android.media.audio.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.media.audio.Flags.FLAG_AUTO_PUBLIC_VOLUME_API_HARDENING, false), java.util.Map.entry(android.media.audio.Flags.FLAG_AUTOMATIC_BT_DEVICE_TYPE, false), java.util.Map.entry(android.media.audio.Flags.FLAG_FEATURE_SPATIAL_AUDIO_HEADTRACKING_LOW_LATENCY, false), java.util.Map.entry(android.media.audio.Flags.FLAG_FOCUS_EXCLUSIVE_WITH_RECORDING, false), java.util.Map.entry(android.media.audio.Flags.FLAG_FOCUS_FREEZE_TEST_API, false), java.util.Map.entry(android.media.audio.Flags.FLAG_FOREGROUND_AUDIO_CONTROL, false), java.util.Map.entry(android.media.audio.Flags.FLAG_LOUDNESS_CONFIGURATOR_API, false), java.util.Map.entry(android.media.audio.Flags.FLAG_MUTE_BACKGROUND_AUDIO, false), java.util.Map.entry(android.media.audio.Flags.FLAG_SCO_MANAGED_BY_AUDIO, false), java.util.Map.entry(android.media.audio.Flags.FLAG_SUPPORTED_DEVICE_TYPES_API, false), java.util.Map.entry(android.media.audio.Flags.FLAG_VOLUME_RINGER_API_HARDENING, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.media.audio.Flags.FLAG_AUTO_PUBLIC_VOLUME_API_HARDENING, android.media.audio.Flags.FLAG_AUTOMATIC_BT_DEVICE_TYPE, android.media.audio.Flags.FLAG_FEATURE_SPATIAL_AUDIO_HEADTRACKING_LOW_LATENCY, android.media.audio.Flags.FLAG_FOCUS_EXCLUSIVE_WITH_RECORDING, android.media.audio.Flags.FLAG_FOCUS_FREEZE_TEST_API, android.media.audio.Flags.FLAG_FOREGROUND_AUDIO_CONTROL, android.media.audio.Flags.FLAG_LOUDNESS_CONFIGURATOR_API, android.media.audio.Flags.FLAG_MUTE_BACKGROUND_AUDIO, android.media.audio.Flags.FLAG_SCO_MANAGED_BY_AUDIO, android.media.audio.Flags.FLAG_SUPPORTED_DEVICE_TYPES_API, android.media.audio.Flags.FLAG_VOLUME_RINGER_API_HARDENING, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.media.audio.FeatureFlags
    public boolean autoPublicVolumeApiHardening() {
        return getValue(android.media.audio.Flags.FLAG_AUTO_PUBLIC_VOLUME_API_HARDENING);
    }

    @Override // android.media.audio.FeatureFlags
    public boolean automaticBtDeviceType() {
        return getValue(android.media.audio.Flags.FLAG_AUTOMATIC_BT_DEVICE_TYPE);
    }

    @Override // android.media.audio.FeatureFlags
    public boolean featureSpatialAudioHeadtrackingLowLatency() {
        return getValue(android.media.audio.Flags.FLAG_FEATURE_SPATIAL_AUDIO_HEADTRACKING_LOW_LATENCY);
    }

    @Override // android.media.audio.FeatureFlags
    public boolean focusExclusiveWithRecording() {
        return getValue(android.media.audio.Flags.FLAG_FOCUS_EXCLUSIVE_WITH_RECORDING);
    }

    @Override // android.media.audio.FeatureFlags
    public boolean focusFreezeTestApi() {
        return getValue(android.media.audio.Flags.FLAG_FOCUS_FREEZE_TEST_API);
    }

    @Override // android.media.audio.FeatureFlags
    public boolean foregroundAudioControl() {
        return getValue(android.media.audio.Flags.FLAG_FOREGROUND_AUDIO_CONTROL);
    }

    @Override // android.media.audio.FeatureFlags
    public boolean loudnessConfiguratorApi() {
        return getValue(android.media.audio.Flags.FLAG_LOUDNESS_CONFIGURATOR_API);
    }

    @Override // android.media.audio.FeatureFlags
    public boolean muteBackgroundAudio() {
        return getValue(android.media.audio.Flags.FLAG_MUTE_BACKGROUND_AUDIO);
    }

    @Override // android.media.audio.FeatureFlags
    public boolean scoManagedByAudio() {
        return getValue(android.media.audio.Flags.FLAG_SCO_MANAGED_BY_AUDIO);
    }

    @Override // android.media.audio.FeatureFlags
    public boolean supportedDeviceTypesApi() {
        return getValue(android.media.audio.Flags.FLAG_SUPPORTED_DEVICE_TYPES_API);
    }

    @Override // android.media.audio.FeatureFlags
    public boolean volumeRingerApiHardening() {
        return getValue(android.media.audio.Flags.FLAG_VOLUME_RINGER_API_HARDENING);
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
