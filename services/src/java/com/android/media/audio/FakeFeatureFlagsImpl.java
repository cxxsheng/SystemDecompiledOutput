package com.android.media.audio;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements com.android.media.audio.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.media.audio.Flags.FLAG_ALARM_MIN_VOLUME_ZERO, false), java.util.Map.entry(com.android.media.audio.Flags.FLAG_AS_DEVICE_CONNECTION_FAILURE, false), java.util.Map.entry(com.android.media.audio.Flags.FLAG_BLUETOOTH_MAC_ADDRESS_ANONYMIZATION, false), java.util.Map.entry(com.android.media.audio.Flags.FLAG_DISABLE_PRESCALE_ABSOLUTE_VOLUME, false), java.util.Map.entry(com.android.media.audio.Flags.FLAG_DSA_OVER_BT_LE_AUDIO, false), java.util.Map.entry(com.android.media.audio.Flags.FLAG_RINGER_MODE_AFFECTS_ALARM, false), java.util.Map.entry(com.android.media.audio.Flags.FLAG_SPATIALIZER_OFFLOAD, false), java.util.Map.entry(com.android.media.audio.Flags.FLAG_STEREO_SPATIALIZATION, false), java.util.Map.entry(com.android.media.audio.Flags.FLAG_VOLUME_REFACTORING, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.media.audio.Flags.FLAG_ALARM_MIN_VOLUME_ZERO, com.android.media.audio.Flags.FLAG_AS_DEVICE_CONNECTION_FAILURE, com.android.media.audio.Flags.FLAG_BLUETOOTH_MAC_ADDRESS_ANONYMIZATION, com.android.media.audio.Flags.FLAG_DISABLE_PRESCALE_ABSOLUTE_VOLUME, com.android.media.audio.Flags.FLAG_DSA_OVER_BT_LE_AUDIO, com.android.media.audio.Flags.FLAG_RINGER_MODE_AFFECTS_ALARM, com.android.media.audio.Flags.FLAG_SPATIALIZER_OFFLOAD, com.android.media.audio.Flags.FLAG_STEREO_SPATIALIZATION, com.android.media.audio.Flags.FLAG_VOLUME_REFACTORING, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.media.audio.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean alarmMinVolumeZero() {
        return getValue(com.android.media.audio.Flags.FLAG_ALARM_MIN_VOLUME_ZERO);
    }

    @Override // com.android.media.audio.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean asDeviceConnectionFailure() {
        return getValue(com.android.media.audio.Flags.FLAG_AS_DEVICE_CONNECTION_FAILURE);
    }

    @Override // com.android.media.audio.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean bluetoothMacAddressAnonymization() {
        return getValue(com.android.media.audio.Flags.FLAG_BLUETOOTH_MAC_ADDRESS_ANONYMIZATION);
    }

    @Override // com.android.media.audio.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean disablePrescaleAbsoluteVolume() {
        return getValue(com.android.media.audio.Flags.FLAG_DISABLE_PRESCALE_ABSOLUTE_VOLUME);
    }

    @Override // com.android.media.audio.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean dsaOverBtLeAudio() {
        return getValue(com.android.media.audio.Flags.FLAG_DSA_OVER_BT_LE_AUDIO);
    }

    @Override // com.android.media.audio.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean ringerModeAffectsAlarm() {
        return getValue(com.android.media.audio.Flags.FLAG_RINGER_MODE_AFFECTS_ALARM);
    }

    @Override // com.android.media.audio.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean spatializerOffload() {
        return getValue(com.android.media.audio.Flags.FLAG_SPATIALIZER_OFFLOAD);
    }

    @Override // com.android.media.audio.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean stereoSpatialization() {
        return getValue(com.android.media.audio.Flags.FLAG_STEREO_SPATIALIZATION);
    }

    @Override // com.android.media.audio.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean volumeRefactoring() {
        return getValue(com.android.media.audio.Flags.FLAG_VOLUME_REFACTORING);
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
