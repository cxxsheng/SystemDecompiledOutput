package com.android.internal.foldables.flags;

/* loaded from: classes4.dex */
public final class Flags {
    private static com.android.internal.foldables.flags.FeatureFlags FEATURE_FLAGS = new com.android.internal.foldables.flags.FeatureFlagsImpl();
    public static final java.lang.String FLAG_FOLD_GRACE_PERIOD_ENABLED = "com.android.internal.foldables.flags.fold_grace_period_enabled";
    public static final java.lang.String FLAG_FOLD_LOCK_SETTING_ENABLED = "com.android.internal.foldables.flags.fold_lock_setting_enabled";

    public static boolean foldGracePeriodEnabled() {
        return FEATURE_FLAGS.foldGracePeriodEnabled();
    }

    public static boolean foldLockSettingEnabled() {
        return FEATURE_FLAGS.foldLockSettingEnabled();
    }
}
