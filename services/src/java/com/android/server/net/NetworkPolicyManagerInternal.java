package com.android.server.net;

/* loaded from: classes2.dex */
public abstract class NetworkPolicyManagerInternal {
    public static final int QUOTA_TYPE_JOBS = 1;
    public static final int QUOTA_TYPE_MULTIPATH = 2;

    public abstract long getSubscriptionOpportunisticQuota(android.net.Network network, int i);

    public abstract android.telephony.SubscriptionPlan getSubscriptionPlan(android.net.Network network);

    public abstract void onAdminDataAvailable();

    public abstract void onTempPowerSaveWhitelistChange(int i, boolean z, int i2, @android.annotation.Nullable java.lang.String str);

    public abstract void resetUserState(int i);

    public abstract void setAppIdleWhitelist(int i, boolean z);

    public abstract void setLowPowerStandbyActive(boolean z);

    public abstract void setLowPowerStandbyAllowlist(int[] iArr);

    public abstract void setMeteredRestrictedPackages(java.util.Set<java.lang.String> set, int i);

    public abstract void setMeteredRestrictedPackagesAsync(java.util.Set<java.lang.String> set, int i);

    public static int updateBlockedReasonsWithProcState(int i, int i2) {
        return com.android.server.net.NetworkPolicyManagerService.UidBlockedState.getEffectiveBlockedReasons(i, com.android.server.net.NetworkPolicyManagerService.UidBlockedState.getAllowedReasonsForProcState(i2));
    }
}
