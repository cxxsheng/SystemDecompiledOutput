package android.media.midi;

/* loaded from: classes2.dex */
public final class Flags {
    private static android.media.midi.FeatureFlags FEATURE_FLAGS = new android.media.midi.FeatureFlagsImpl();
    public static final java.lang.String FLAG_VIRTUAL_UMP = "android.media.midi.virtual_ump";

    public static boolean virtualUmp() {
        return FEATURE_FLAGS.virtualUmp();
    }
}
