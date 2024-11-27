package com.android.server.feature.flags;

/* loaded from: classes.dex */
public final class Flags {
    private static com.android.server.feature.flags.FeatureFlags FEATURE_FLAGS = new com.android.server.feature.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ENABLE_READ_DROPBOX_PERMISSION = "com.android.server.feature.flags.enable_read_dropbox_permission";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean enableReadDropboxPermission() {
        FEATURE_FLAGS.enableReadDropboxPermission();
        return false;
    }
}
