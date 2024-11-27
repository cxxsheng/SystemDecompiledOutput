package com.android.server.policy;

/* loaded from: classes2.dex */
public final class Flags {
    private static com.android.server.policy.FeatureFlags FEATURE_FLAGS = new com.android.server.policy.FeatureFlagsImpl();
    public static final java.lang.String FLAG_SUPPORT_INPUT_WAKEUP_DELEGATE = "com.android.server.policy.support_input_wakeup_delegate";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean supportInputWakeupDelegate() {
        FEATURE_FLAGS.supportInputWakeupDelegate();
        return false;
    }
}
