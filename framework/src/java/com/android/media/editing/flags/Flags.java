package com.android.media.editing.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static com.android.media.editing.flags.FeatureFlags FEATURE_FLAGS = new com.android.media.editing.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ADD_MEDIA_METRICS_EDITING = "com.android.media.editing.flags.add_media_metrics_editing";

    public static boolean addMediaMetricsEditing() {
        return FEATURE_FLAGS.addMediaMetricsEditing();
    }
}
