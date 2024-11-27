package android.tracing;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.tracing.FeatureFlags FEATURE_FLAGS = new android.tracing.FeatureFlagsImpl();
    public static final java.lang.String FLAG_PERFETTO_PROTOLOG_TRACING = "android.tracing.perfetto_protolog_tracing";
    public static final java.lang.String FLAG_PERFETTO_TRANSITION_TRACING = "android.tracing.perfetto_transition_tracing";

    public static boolean perfettoProtologTracing() {
        return FEATURE_FLAGS.perfettoProtologTracing();
    }

    public static boolean perfettoTransitionTracing() {
        return FEATURE_FLAGS.perfettoTransitionTracing();
    }
}
