package com.android.media.projection.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static com.android.media.projection.flags.FeatureFlags FEATURE_FLAGS = new com.android.media.projection.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_LIMIT_MANAGE_MEDIA_PROJECTION = "com.android.media.projection.flags.limit_manage_media_projection";

    public static boolean limitManageMediaProjection() {
        return FEATURE_FLAGS.limitManageMediaProjection();
    }
}
