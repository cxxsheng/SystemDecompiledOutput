package android.media.codec;

/* loaded from: classes2.dex */
public final class Flags {
    private static android.media.codec.FeatureFlags FEATURE_FLAGS = new android.media.codec.FeatureFlagsImpl();
    public static final java.lang.String FLAG_AIDL_HAL_INPUT_SURFACE = "android.media.codec.aidl_hal_input_surface";
    public static final java.lang.String FLAG_DYNAMIC_COLOR_ASPECTS = "android.media.codec.dynamic_color_aspects";
    public static final java.lang.String FLAG_HLG_EDITING = "android.media.codec.hlg_editing";
    public static final java.lang.String FLAG_IN_PROCESS_SW_AUDIO_CODEC = "android.media.codec.in_process_sw_audio_codec";
    public static final java.lang.String FLAG_IN_PROCESS_SW_AUDIO_CODEC_SUPPORT = "android.media.codec.in_process_sw_audio_codec_support";
    public static final java.lang.String FLAG_LARGE_AUDIO_FRAME_FINISH = "android.media.codec.large_audio_frame_finish";
    public static final java.lang.String FLAG_NULL_OUTPUT_SURFACE = "android.media.codec.null_output_surface";
    public static final java.lang.String FLAG_NULL_OUTPUT_SURFACE_SUPPORT = "android.media.codec.null_output_surface_support";
    public static final java.lang.String FLAG_REGION_OF_INTEREST = "android.media.codec.region_of_interest";
    public static final java.lang.String FLAG_REGION_OF_INTEREST_SUPPORT = "android.media.codec.region_of_interest_support";

    public static boolean aidlHalInputSurface() {
        return FEATURE_FLAGS.aidlHalInputSurface();
    }

    public static boolean dynamicColorAspects() {
        return FEATURE_FLAGS.dynamicColorAspects();
    }

    public static boolean hlgEditing() {
        return FEATURE_FLAGS.hlgEditing();
    }

    public static boolean inProcessSwAudioCodec() {
        return FEATURE_FLAGS.inProcessSwAudioCodec();
    }

    public static boolean inProcessSwAudioCodecSupport() {
        return FEATURE_FLAGS.inProcessSwAudioCodecSupport();
    }

    public static boolean largeAudioFrameFinish() {
        return FEATURE_FLAGS.largeAudioFrameFinish();
    }

    public static boolean nullOutputSurface() {
        return FEATURE_FLAGS.nullOutputSurface();
    }

    public static boolean nullOutputSurfaceSupport() {
        return FEATURE_FLAGS.nullOutputSurfaceSupport();
    }

    public static boolean regionOfInterest() {
        return FEATURE_FLAGS.regionOfInterest();
    }

    public static boolean regionOfInterestSupport() {
        return FEATURE_FLAGS.regionOfInterestSupport();
    }
}
