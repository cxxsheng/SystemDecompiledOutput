package android.service.voice.flags;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.service.voice.flags.FeatureFlags FEATURE_FLAGS = new android.service.voice.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ALLOW_COMPLEX_RESULTS_EGRESS_FROM_VQDS = "android.service.voice.flags.allow_complex_results_egress_from_vqds";
    public static final java.lang.String FLAG_ALLOW_FOREGROUND_ACTIVITIES_IN_ON_SHOW = "android.service.voice.flags.allow_foreground_activities_in_on_show";
    public static final java.lang.String FLAG_ALLOW_HOTWORD_BUMP_EGRESS = "android.service.voice.flags.allow_hotword_bump_egress";
    public static final java.lang.String FLAG_ALLOW_SPEAKER_ID_EGRESS = "android.service.voice.flags.allow_speaker_id_egress";
    public static final java.lang.String FLAG_ALLOW_TRAINING_DATA_EGRESS_FROM_HDS = "android.service.voice.flags.allow_training_data_egress_from_hds";
    public static final java.lang.String FLAG_ALLOW_VARIOUS_ATTENTION_TYPES = "android.service.voice.flags.allow_various_attention_types";

    public static boolean allowComplexResultsEgressFromVqds() {
        return FEATURE_FLAGS.allowComplexResultsEgressFromVqds();
    }

    public static boolean allowForegroundActivitiesInOnShow() {
        return FEATURE_FLAGS.allowForegroundActivitiesInOnShow();
    }

    public static boolean allowHotwordBumpEgress() {
        return FEATURE_FLAGS.allowHotwordBumpEgress();
    }

    public static boolean allowSpeakerIdEgress() {
        return FEATURE_FLAGS.allowSpeakerIdEgress();
    }

    public static boolean allowTrainingDataEgressFromHds() {
        return FEATURE_FLAGS.allowTrainingDataEgressFromHds();
    }

    public static boolean allowVariousAttentionTypes() {
        return FEATURE_FLAGS.allowVariousAttentionTypes();
    }
}
