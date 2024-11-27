package com.android.server.os;

/* loaded from: classes2.dex */
public final class Flags {
    private static com.android.server.os.FeatureFlags FEATURE_FLAGS = new com.android.server.os.FeatureFlagsImpl();
    public static final java.lang.String FLAG_PROTO_TOMBSTONE = "com.android.server.os.proto_tombstone";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean protoTombstone() {
        FEATURE_FLAGS.protoTombstone();
        return false;
    }
}
