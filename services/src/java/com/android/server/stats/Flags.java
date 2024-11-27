package com.android.server.stats;

/* loaded from: classes2.dex */
public final class Flags {
    private static com.android.server.stats.FeatureFlags FEATURE_FLAGS = new com.android.server.stats.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ADD_MOBILE_BYTES_TRANSFER_BY_PROC_STATE_PULLER = "com.android.server.stats.add_mobile_bytes_transfer_by_proc_state_puller";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean addMobileBytesTransferByProcStatePuller() {
        FEATURE_FLAGS.addMobileBytesTransferByProcStatePuller();
        return false;
    }
}
