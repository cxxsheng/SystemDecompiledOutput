package com.android.server.am;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements com.android.server.am.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.server.am.Flags.FLAG_AVOID_REPEATED_BCAST_RE_ENQUEUES, false), java.util.Map.entry(com.android.server.am.Flags.FLAG_BFGS_MANAGED_NETWORK_ACCESS, false), java.util.Map.entry(com.android.server.am.Flags.FLAG_DEFER_OUTGOING_BCASTS, false), java.util.Map.entry(com.android.server.am.Flags.FLAG_FGS_ABUSE_DETECTION, false), java.util.Map.entry(com.android.server.am.Flags.FLAG_FGS_BOOT_COMPLETED, false), java.util.Map.entry(com.android.server.am.Flags.FLAG_NEW_FGS_RESTRICTION_LOGIC, false), java.util.Map.entry(com.android.server.am.Flags.FLAG_OOMADJUSTER_CORRECTNESS_REWRITE, false), java.util.Map.entry(com.android.server.am.Flags.FLAG_SERVICE_BINDING_OOM_ADJ_POLICY, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.server.am.Flags.FLAG_AVOID_REPEATED_BCAST_RE_ENQUEUES, com.android.server.am.Flags.FLAG_BFGS_MANAGED_NETWORK_ACCESS, com.android.server.am.Flags.FLAG_DEFER_OUTGOING_BCASTS, com.android.server.am.Flags.FLAG_FGS_ABUSE_DETECTION, com.android.server.am.Flags.FLAG_FGS_BOOT_COMPLETED, com.android.server.am.Flags.FLAG_NEW_FGS_RESTRICTION_LOGIC, com.android.server.am.Flags.FLAG_OOMADJUSTER_CORRECTNESS_REWRITE, com.android.server.am.Flags.FLAG_SERVICE_BINDING_OOM_ADJ_POLICY, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.server.am.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean avoidRepeatedBcastReEnqueues() {
        return getValue(com.android.server.am.Flags.FLAG_AVOID_REPEATED_BCAST_RE_ENQUEUES);
    }

    @Override // com.android.server.am.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean bfgsManagedNetworkAccess() {
        return getValue(com.android.server.am.Flags.FLAG_BFGS_MANAGED_NETWORK_ACCESS);
    }

    @Override // com.android.server.am.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean deferOutgoingBcasts() {
        return getValue(com.android.server.am.Flags.FLAG_DEFER_OUTGOING_BCASTS);
    }

    @Override // com.android.server.am.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean fgsAbuseDetection() {
        return getValue(com.android.server.am.Flags.FLAG_FGS_ABUSE_DETECTION);
    }

    @Override // com.android.server.am.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean fgsBootCompleted() {
        return getValue(com.android.server.am.Flags.FLAG_FGS_BOOT_COMPLETED);
    }

    @Override // com.android.server.am.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean newFgsRestrictionLogic() {
        return getValue(com.android.server.am.Flags.FLAG_NEW_FGS_RESTRICTION_LOGIC);
    }

    @Override // com.android.server.am.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean oomadjusterCorrectnessRewrite() {
        return getValue(com.android.server.am.Flags.FLAG_OOMADJUSTER_CORRECTNESS_REWRITE);
    }

    @Override // com.android.server.am.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean serviceBindingOomAdjPolicy() {
        return getValue(com.android.server.am.Flags.FLAG_SERVICE_BINDING_OOM_ADJ_POLICY);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str)) {
            isOptimizationEnabled();
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    @com.android.aconfig.annotations.AssumeTrueForR8
    private boolean isOptimizationEnabled() {
        return false;
    }
}
