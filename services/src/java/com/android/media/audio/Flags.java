package com.android.media.audio;

/* loaded from: classes.dex */
public final class Flags {
    private static com.android.media.audio.FeatureFlags FEATURE_FLAGS = new com.android.media.audio.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ALARM_MIN_VOLUME_ZERO = "com.android.media.audio.alarm_min_volume_zero";
    public static final java.lang.String FLAG_AS_DEVICE_CONNECTION_FAILURE = "com.android.media.audio.as_device_connection_failure";
    public static final java.lang.String FLAG_BLUETOOTH_MAC_ADDRESS_ANONYMIZATION = "com.android.media.audio.bluetooth_mac_address_anonymization";
    public static final java.lang.String FLAG_DISABLE_PRESCALE_ABSOLUTE_VOLUME = "com.android.media.audio.disable_prescale_absolute_volume";
    public static final java.lang.String FLAG_DSA_OVER_BT_LE_AUDIO = "com.android.media.audio.dsa_over_bt_le_audio";
    public static final java.lang.String FLAG_RINGER_MODE_AFFECTS_ALARM = "com.android.media.audio.ringer_mode_affects_alarm";
    public static final java.lang.String FLAG_SPATIALIZER_OFFLOAD = "com.android.media.audio.spatializer_offload";
    public static final java.lang.String FLAG_STEREO_SPATIALIZATION = "com.android.media.audio.stereo_spatialization";
    public static final java.lang.String FLAG_VOLUME_REFACTORING = "com.android.media.audio.volume_refactoring";

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    public static boolean alarmMinVolumeZero() {
        FEATURE_FLAGS.alarmMinVolumeZero();
        return true;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean asDeviceConnectionFailure() {
        FEATURE_FLAGS.asDeviceConnectionFailure();
        return false;
    }

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    public static boolean bluetoothMacAddressAnonymization() {
        FEATURE_FLAGS.bluetoothMacAddressAnonymization();
        return true;
    }

    @com.android.aconfig.annotations.AssumeTrueForR8
    @android.compat.annotation.UnsupportedAppUsage
    public static boolean disablePrescaleAbsoluteVolume() {
        FEATURE_FLAGS.disablePrescaleAbsoluteVolume();
        return true;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean dsaOverBtLeAudio() {
        FEATURE_FLAGS.dsaOverBtLeAudio();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean ringerModeAffectsAlarm() {
        FEATURE_FLAGS.ringerModeAffectsAlarm();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean spatializerOffload() {
        FEATURE_FLAGS.spatializerOffload();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean stereoSpatialization() {
        FEATURE_FLAGS.stereoSpatialization();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean volumeRefactoring() {
        FEATURE_FLAGS.volumeRefactoring();
        return false;
    }
}
