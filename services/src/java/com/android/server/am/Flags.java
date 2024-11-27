package com.android.server.am;

/* loaded from: classes.dex */
public final class Flags {
    private static com.android.server.am.FeatureFlags FEATURE_FLAGS = new com.android.server.am.FeatureFlagsImpl();
    public static final java.lang.String FLAG_AVOID_REPEATED_BCAST_RE_ENQUEUES = "com.android.server.am.avoid_repeated_bcast_re_enqueues";
    public static final java.lang.String FLAG_BFGS_MANAGED_NETWORK_ACCESS = "com.android.server.am.bfgs_managed_network_access";
    public static final java.lang.String FLAG_DEFER_OUTGOING_BCASTS = "com.android.server.am.defer_outgoing_bcasts";
    public static final java.lang.String FLAG_FGS_ABUSE_DETECTION = "com.android.server.am.fgs_abuse_detection";
    public static final java.lang.String FLAG_FGS_BOOT_COMPLETED = "com.android.server.am.fgs_boot_completed";
    public static final java.lang.String FLAG_NEW_FGS_RESTRICTION_LOGIC = "com.android.server.am.new_fgs_restriction_logic";
    public static final java.lang.String FLAG_OOMADJUSTER_CORRECTNESS_REWRITE = "com.android.server.am.oomadjuster_correctness_rewrite";
    public static final java.lang.String FLAG_SERVICE_BINDING_OOM_ADJ_POLICY = "com.android.server.am.service_binding_oom_adj_policy";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean avoidRepeatedBcastReEnqueues() {
        FEATURE_FLAGS.avoidRepeatedBcastReEnqueues();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean bfgsManagedNetworkAccess() {
        FEATURE_FLAGS.bfgsManagedNetworkAccess();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean deferOutgoingBcasts() {
        FEATURE_FLAGS.deferOutgoingBcasts();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean fgsAbuseDetection() {
        FEATURE_FLAGS.fgsAbuseDetection();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean fgsBootCompleted() {
        FEATURE_FLAGS.fgsBootCompleted();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean newFgsRestrictionLogic() {
        FEATURE_FLAGS.newFgsRestrictionLogic();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean oomadjusterCorrectnessRewrite() {
        FEATURE_FLAGS.oomadjusterCorrectnessRewrite();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean serviceBindingOomAdjPolicy() {
        FEATURE_FLAGS.serviceBindingOomAdjPolicy();
        return false;
    }
}
