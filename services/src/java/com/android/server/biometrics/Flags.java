package com.android.server.biometrics;

/* loaded from: classes.dex */
public final class Flags {
    private static com.android.server.biometrics.FeatureFlags FEATURE_FLAGS = new com.android.server.biometrics.FeatureFlagsImpl();
    public static final java.lang.String FLAG_DE_HIDL = "com.android.server.biometrics.de_hidl";
    public static final java.lang.String FLAG_FACE_VHAL_FEATURE = "com.android.server.biometrics.face_vhal_feature";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean deHidl() {
        FEATURE_FLAGS.deHidl();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean faceVhalFeature() {
        FEATURE_FLAGS.faceVhalFeature();
        return false;
    }
}
