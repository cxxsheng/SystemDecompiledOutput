package com.android.media.codec.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static com.android.media.codec.flags.FeatureFlags FEATURE_FLAGS = new com.android.media.codec.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_AIDL_HAL = "com.android.media.codec.flags.aidl_hal";
    public static final java.lang.String FLAG_CODEC_IMPORTANCE = "com.android.media.codec.flags.codec_importance";
    public static final java.lang.String FLAG_LARGE_AUDIO_FRAME = "com.android.media.codec.flags.large_audio_frame";

    public static boolean aidlHal() {
        return FEATURE_FLAGS.aidlHal();
    }

    public static boolean codecImportance() {
        return FEATURE_FLAGS.codecImportance();
    }

    public static boolean largeAudioFrame() {
        return FEATURE_FLAGS.largeAudioFrame();
    }
}
