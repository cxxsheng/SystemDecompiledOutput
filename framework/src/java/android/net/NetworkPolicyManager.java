package android.net;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes2.dex */
public class NetworkPolicyManager {
    public static final int ALLOWED_METERED_REASON_FOREGROUND = 262144;
    public static final int ALLOWED_METERED_REASON_MASK = -65536;
    public static final int ALLOWED_METERED_REASON_SYSTEM = 131072;
    public static final int ALLOWED_METERED_REASON_USER_EXEMPTED = 65536;
    public static final int ALLOWED_REASON_FOREGROUND = 2;
    public static final int ALLOWED_REASON_LOW_POWER_STANDBY_ALLOWLIST = 64;
    public static final int ALLOWED_REASON_NONE = 0;
    public static final int ALLOWED_REASON_NOT_IN_BACKGROUND = 128;
    public static final int ALLOWED_REASON_POWER_SAVE_ALLOWLIST = 4;
    public static final int ALLOWED_REASON_POWER_SAVE_EXCEPT_IDLE_ALLOWLIST = 8;
    public static final int ALLOWED_REASON_RESTRICTED_MODE_PERMISSIONS = 16;
    public static final int ALLOWED_REASON_SYSTEM = 1;
    public static final int ALLOWED_REASON_TOP = 32;
    private static final boolean ALLOW_PLATFORM_APP_POLICY = true;
    public static final int BACKGROUND_THRESHOLD_STATE = 12;
    public static final java.lang.String EXTRA_NETWORK_TEMPLATE = "android.net.NETWORK_TEMPLATE";
    public static final java.lang.String FIREWALL_CHAIN_NAME_BACKGROUND = "background";
    public static final java.lang.String FIREWALL_CHAIN_NAME_DOZABLE = "dozable";
    public static final java.lang.String FIREWALL_CHAIN_NAME_LOW_POWER_STANDBY = "low_power_standby";
    public static final java.lang.String FIREWALL_CHAIN_NAME_NONE = "none";
    public static final java.lang.String FIREWALL_CHAIN_NAME_POWERSAVE = "powersave";
    public static final java.lang.String FIREWALL_CHAIN_NAME_RESTRICTED = "restricted";
    public static final java.lang.String FIREWALL_CHAIN_NAME_STANDBY = "standby";
    public static final int FIREWALL_RULE_DEFAULT = 0;
    public static final int FOREGROUND_THRESHOLD_STATE = 5;
    public static final int MASK_ALL_NETWORKS = 240;
    public static final int MASK_METERED_NETWORKS = 15;
    public static final int MASK_RESTRICTED_MODE_NETWORKS = 3840;
    public static final int POLICY_ALLOW_METERED_BACKGROUND = 4;
    public static final int POLICY_LOCKDOWN_VPN = 98304;
    public static final int POLICY_LOCKDOWN_VPN_MASK = 229376;
    public static final int POLICY_NONE = 0;
    public static final int POLICY_REJECT_ALL = 262144;
    public static final int POLICY_REJECT_CELLULAR = 65536;
    public static final int POLICY_REJECT_METERED_BACKGROUND = 1;
    public static final int POLICY_REJECT_VPN = 131072;
    public static final int POLICY_REJECT_WIFI = 32768;
    public static final int RULE_ALLOW_ALL = 32;
    public static final int RULE_ALLOW_METERED = 1;
    public static final int RULE_NONE = 0;
    public static final int RULE_REJECT_ALL = 64;
    public static final int RULE_REJECT_METERED = 4;
    public static final int RULE_REJECT_RESTRICTED_MODE = 1024;
    public static final int RULE_TEMPORARY_ALLOW_METERED = 2;
    public static final int SUBSCRIPTION_OVERRIDE_CONGESTED = 2;
    public static final int SUBSCRIPTION_OVERRIDE_UNMETERED = 1;
    public static final int TOP_THRESHOLD_STATE = 3;
    private final android.content.Context mContext;
    private android.net.INetworkPolicyManager mService;
    private final java.util.Map<android.net.NetworkPolicyManager.SubscriptionCallback, android.net.NetworkPolicyManager.SubscriptionCallbackProxy> mSubscriptionCallbackMap = new java.util.concurrent.ConcurrentHashMap();
    private final java.util.Map<android.net.NetworkPolicyManager.NetworkPolicyCallback, android.net.NetworkPolicyManager.NetworkPolicyCallbackProxy> mNetworkPolicyCallbackMap = new java.util.concurrent.ConcurrentHashMap();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SubscriptionOverrideMask {
    }

    public NetworkPolicyManager(android.content.Context context, android.net.INetworkPolicyManager iNetworkPolicyManager) {
        if (iNetworkPolicyManager == null) {
            throw new java.lang.IllegalArgumentException("missing INetworkPolicyManager");
        }
        this.mContext = context;
        this.mService = iNetworkPolicyManager;
    }

    public static android.net.NetworkPolicyManager from(android.content.Context context) {
        return (android.net.NetworkPolicyManager) context.getSystemService(android.content.Context.NETWORK_POLICY_SERVICE);
    }

    public void setUidPolicy(int i, int i2) {
        try {
            this.mService.setUidPolicy(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addUidPolicy(int i, int i2) {
        try {
            this.mService.addUidPolicy(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeUidPolicy(int i, int i2) {
        try {
            this.mService.removeUidPolicy(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUidPolicy(int i) {
        try {
            return this.mService.getUidPolicy(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getUidsWithPolicy(int i) {
        try {
            return this.mService.getUidsWithPolicy(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void notifyDenylistChanged(int[] iArr, int[] iArr2) {
        try {
            this.mService.notifyDenylistChanged(iArr, iArr2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerListener(android.net.INetworkPolicyListener iNetworkPolicyListener) {
        try {
            this.mService.registerListener(iNetworkPolicyListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterListener(android.net.INetworkPolicyListener iNetworkPolicyListener) {
        try {
            this.mService.unregisterListener(iNetworkPolicyListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerSubscriptionCallback(android.net.NetworkPolicyManager.SubscriptionCallback subscriptionCallback) {
        if (subscriptionCallback == null) {
            throw new java.lang.NullPointerException("Callback cannot be null.");
        }
        android.net.NetworkPolicyManager.SubscriptionCallbackProxy subscriptionCallbackProxy = new android.net.NetworkPolicyManager.SubscriptionCallbackProxy(subscriptionCallback);
        if (this.mSubscriptionCallbackMap.putIfAbsent(subscriptionCallback, subscriptionCallbackProxy) != null) {
            throw new java.lang.IllegalArgumentException("Callback is already registered.");
        }
        registerListener(subscriptionCallbackProxy);
    }

    public void unregisterSubscriptionCallback(android.net.NetworkPolicyManager.SubscriptionCallback subscriptionCallback) {
        if (subscriptionCallback == null) {
            throw new java.lang.NullPointerException("Callback cannot be null.");
        }
        android.net.NetworkPolicyManager.SubscriptionCallbackProxy remove = this.mSubscriptionCallbackMap.remove(subscriptionCallback);
        if (remove == null) {
            return;
        }
        unregisterListener(remove);
    }

    public void setNetworkPolicies(android.net.NetworkPolicy[] networkPolicyArr) {
        try {
            this.mService.setNetworkPolicies(networkPolicyArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.net.NetworkPolicy[] getNetworkPolicies() {
        try {
            return this.mService.getNetworkPolicies(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRestrictBackground(boolean z) {
        try {
            this.mService.setRestrictBackground(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean getRestrictBackground() {
        try {
            return this.mService.getRestrictBackground();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public int getRestrictBackgroundStatus(int i) {
        try {
            return this.mService.getRestrictBackgroundStatus(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSubscriptionOverride(int i, int i2, int i3, int[] iArr, long j, java.lang.String str) {
        try {
            this.mService.setSubscriptionOverride(i, i2, i3, iArr, j, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSubscriptionPlans(int i, android.telephony.SubscriptionPlan[] subscriptionPlanArr, long j, java.lang.String str) {
        try {
            this.mService.setSubscriptionPlans(i, subscriptionPlanArr, j, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.telephony.SubscriptionPlan[] getSubscriptionPlans(int i, java.lang.String str) {
        try {
            return this.mService.getSubscriptionPlans(i, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public android.telephony.SubscriptionPlan getSubscriptionPlan(android.net.NetworkTemplate networkTemplate) {
        try {
            return this.mService.getSubscriptionPlan(networkTemplate);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void notifyStatsProviderWarningReached() {
        try {
            this.mService.notifyStatsProviderWarningOrLimitReached();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void notifyStatsProviderLimitReached() {
        try {
            this.mService.notifyStatsProviderWarningOrLimitReached();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void factoryReset(java.lang.String str) {
        try {
            this.mService.factoryReset(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUidNetworkingBlocked(int i, boolean z) {
        try {
            return this.mService.isUidNetworkingBlocked(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUidRestrictedOnMeteredNetworks(int i) {
        try {
            return this.mService.isUidRestrictedOnMeteredNetworks(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public int getMultipathPreference(android.net.Network network) {
        try {
            return this.mService.getMultipathPreference(network);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public static java.util.Iterator<android.util.Pair<java.time.ZonedDateTime, java.time.ZonedDateTime>> cycleIterator(android.net.NetworkPolicy networkPolicy) {
        final java.util.Iterator<android.util.Range<java.time.ZonedDateTime>> cycleIterator = networkPolicy.cycleIterator();
        return new java.util.Iterator<android.util.Pair<java.time.ZonedDateTime, java.time.ZonedDateTime>>() { // from class: android.net.NetworkPolicyManager.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return cycleIterator.hasNext();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public android.util.Pair<java.time.ZonedDateTime, java.time.ZonedDateTime> next() {
                if (hasNext()) {
                    android.util.Range range = (android.util.Range) cycleIterator.next();
                    return android.util.Pair.create((java.time.ZonedDateTime) range.getLower(), (java.time.ZonedDateTime) range.getUpper());
                }
                return android.util.Pair.create(null, null);
            }
        };
    }

    @java.lang.Deprecated
    public static boolean isUidValidForPolicy(android.content.Context context, int i) {
        if (!android.os.Process.isApplicationUid(i)) {
            return false;
        }
        return true;
    }

    public static java.lang.String uidRulesToString(int i) {
        java.lang.StringBuilder append = new java.lang.StringBuilder().append(i).append(" (");
        if (i == 0) {
            append.append(android.security.keystore.KeyProperties.DIGEST_NONE);
        } else {
            append.append(android.util.DebugUtils.flagsToString(android.net.NetworkPolicyManager.class, "RULE_", i));
        }
        append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return append.toString();
    }

    public static java.lang.String uidPoliciesToString(int i) {
        java.lang.StringBuilder append = new java.lang.StringBuilder().append(i).append(" (");
        if (i == 0) {
            append.append(android.security.keystore.KeyProperties.DIGEST_NONE);
        } else {
            append.append(android.util.DebugUtils.flagsToString(android.net.NetworkPolicyManager.class, "POLICY_", i));
        }
        append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return append.toString();
    }

    public static int getDefaultProcessNetworkCapabilities(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return 40;
            default:
                return 0;
        }
    }

    public static boolean isProcStateAllowedWhileIdleOrPowerSaveMode(android.net.NetworkPolicyManager.UidState uidState) {
        if (uidState == null) {
            return false;
        }
        return isProcStateAllowedWhileIdleOrPowerSaveMode(uidState.procState, uidState.capability);
    }

    public static boolean isProcStateAllowedWhileIdleOrPowerSaveMode(int i, int i2) {
        if (i == -1) {
            return false;
        }
        return i <= 5 || (i2 & 8) != 0;
    }

    public static boolean isProcStateAllowedWhileInLowPowerStandby(android.net.NetworkPolicyManager.UidState uidState) {
        return uidState != null && uidState.procState <= 3;
    }

    public static boolean isProcStateAllowedNetworkWhileBackground(android.net.NetworkPolicyManager.UidState uidState) {
        if (uidState == null) {
            return false;
        }
        return uidState.procState < 12 || (uidState.capability & 8) != 0;
    }

    public static boolean isProcStateAllowedWhileOnRestrictBackground(android.net.NetworkPolicyManager.UidState uidState) {
        if (uidState == null) {
            return false;
        }
        return isProcStateAllowedWhileOnRestrictBackground(uidState.procState, uidState.capability);
    }

    public static boolean isProcStateAllowedWhileOnRestrictBackground(int i, int i2) {
        if (i == -1) {
            return false;
        }
        return i <= 5 || (i <= 6 && (i2 & 32) != 0);
    }

    public static final class UidState {
        public int capability;
        public int procState;
        public long procStateSeq;
        public int uid;

        public UidState(int i, int i2, long j, int i3) {
            this.uid = i;
            this.procState = i2;
            this.procStateSeq = j;
            this.capability = i3;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("{procState=");
            sb.append(android.app.ActivityManager.procStateToString(this.procState));
            sb.append(",seq=");
            sb.append(this.procStateSeq);
            sb.append(",cap=");
            android.app.ActivityManager.printCapabilitiesSummary(sb, this.capability);
            sb.append("}");
            return sb.toString();
        }
    }

    public static java.lang.String resolveNetworkId(android.net.wifi.WifiConfiguration wifiConfiguration) {
        return android.net.wifi.WifiInfo.sanitizeSsid(wifiConfiguration.isPasspoint() ? wifiConfiguration.providerFriendlyName : wifiConfiguration.SSID);
    }

    public static java.lang.String resolveNetworkId(java.lang.String str) {
        return android.net.wifi.WifiInfo.sanitizeSsid(str);
    }

    public static java.lang.String blockedReasonsToString(int i) {
        return android.util.DebugUtils.flagsToString(android.net.ConnectivityManager.class, "BLOCKED_", i);
    }

    public static java.lang.String allowedReasonsToString(int i) {
        return android.util.DebugUtils.flagsToString(android.net.NetworkPolicyManager.class, "ALLOWED_", i);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void registerNetworkPolicyCallback(java.util.concurrent.Executor executor, android.net.NetworkPolicyManager.NetworkPolicyCallback networkPolicyCallback) {
        if (networkPolicyCallback == null) {
            throw new java.lang.NullPointerException("Callback cannot be null.");
        }
        android.net.NetworkPolicyManager.NetworkPolicyCallbackProxy networkPolicyCallbackProxy = new android.net.NetworkPolicyManager.NetworkPolicyCallbackProxy(executor, networkPolicyCallback);
        registerListener(networkPolicyCallbackProxy);
        this.mNetworkPolicyCallbackMap.put(networkPolicyCallback, networkPolicyCallbackProxy);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void unregisterNetworkPolicyCallback(android.net.NetworkPolicyManager.NetworkPolicyCallback networkPolicyCallback) {
        if (networkPolicyCallback == null) {
            throw new java.lang.NullPointerException("Callback cannot be null.");
        }
        android.net.NetworkPolicyManager.NetworkPolicyCallbackProxy remove = this.mNetworkPolicyCallbackMap.remove(networkPolicyCallback);
        if (remove == null) {
            return;
        }
        unregisterListener(remove);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public interface NetworkPolicyCallback {
        @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
        default void onUidBlockedReasonChanged(int i, int i2) {
        }

        @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
        default void onUidsAllowedTransportsChanged(int[] iArr, long[] jArr) {
        }
    }

    public static class NetworkPolicyCallbackProxy extends android.net.NetworkPolicyManager.Listener {
        private final android.net.NetworkPolicyManager.NetworkPolicyCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        NetworkPolicyCallbackProxy(java.util.concurrent.Executor executor, android.net.NetworkPolicyManager.NetworkPolicyCallback networkPolicyCallback) {
            this.mExecutor = executor;
            this.mCallback = networkPolicyCallback;
        }

        @Override // android.net.NetworkPolicyManager.Listener, android.net.INetworkPolicyListener
        public void onBlockedReasonChanged(int i, int i2, int i3) {
            if (i2 != i3) {
                android.net.NetworkPolicyManager.dispatchOnUidBlockedReasonChanged(this.mExecutor, this.mCallback, i, i3);
            }
        }

        @Override // android.net.NetworkPolicyManager.Listener, android.net.INetworkPolicyListener
        public void onAllowedTransportsChanged(int[] iArr, long[] jArr) {
            android.net.NetworkPolicyManager.dispatchOnUidsAllowedTransportsChanged(this.mExecutor, this.mCallback, iArr, jArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dispatchOnUidBlockedReasonChanged(java.util.concurrent.Executor executor, android.net.NetworkPolicyManager.NetworkPolicyCallback networkPolicyCallback, int i, int i2) {
        if (executor == null) {
            networkPolicyCallback.onUidBlockedReasonChanged(i, i2);
        } else {
            executor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.TriConsumer() { // from class: android.net.NetworkPolicyManager$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.net.NetworkPolicyManager.NetworkPolicyCallback) obj).onUidBlockedReasonChanged(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue());
                }
            }, networkPolicyCallback, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)).recycleOnUse());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dispatchOnUidsAllowedTransportsChanged(java.util.concurrent.Executor executor, android.net.NetworkPolicyManager.NetworkPolicyCallback networkPolicyCallback, int[] iArr, long[] jArr) {
        if (executor == null) {
            networkPolicyCallback.onUidsAllowedTransportsChanged(iArr, jArr);
        } else {
            executor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.TriConsumer() { // from class: android.net.NetworkPolicyManager$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.net.NetworkPolicyManager.NetworkPolicyCallback) obj).onUidsAllowedTransportsChanged((int[]) obj2, (long[]) obj3);
                }
            }, networkPolicyCallback, iArr, jArr).recycleOnUse());
        }
    }

    public static class SubscriptionCallback {
        public void onSubscriptionOverride(int i, int i2, int i3, int[] iArr) {
        }

        public void onSubscriptionPlansChanged(int i, android.telephony.SubscriptionPlan[] subscriptionPlanArr) {
        }
    }

    public class SubscriptionCallbackProxy extends android.net.NetworkPolicyManager.Listener {
        private final android.net.NetworkPolicyManager.SubscriptionCallback mCallback;

        SubscriptionCallbackProxy(android.net.NetworkPolicyManager.SubscriptionCallback subscriptionCallback) {
            this.mCallback = subscriptionCallback;
        }

        @Override // android.net.NetworkPolicyManager.Listener, android.net.INetworkPolicyListener
        public void onSubscriptionOverride(int i, int i2, int i3, int[] iArr) {
            this.mCallback.onSubscriptionOverride(i, i2, i3, iArr);
        }

        @Override // android.net.NetworkPolicyManager.Listener, android.net.INetworkPolicyListener
        public void onSubscriptionPlansChanged(int i, android.telephony.SubscriptionPlan[] subscriptionPlanArr) {
            this.mCallback.onSubscriptionPlansChanged(i, subscriptionPlanArr);
        }
    }

    public static class Listener extends android.net.INetworkPolicyListener.Stub {
        @Override // android.net.INetworkPolicyListener
        public void onUidRulesChanged(int i, int i2) {
        }

        @Override // android.net.INetworkPolicyListener
        public void onMeteredIfacesChanged(java.lang.String[] strArr) {
        }

        @Override // android.net.INetworkPolicyListener
        public void onRestrictBackgroundChanged(boolean z) {
        }

        @Override // android.net.INetworkPolicyListener
        public void onUidPoliciesChanged(int i, int i2) {
        }

        @Override // android.net.INetworkPolicyListener
        public void onSubscriptionOverride(int i, int i2, int i3, int[] iArr) {
        }

        @Override // android.net.INetworkPolicyListener
        public void onSubscriptionPlansChanged(int i, android.telephony.SubscriptionPlan[] subscriptionPlanArr) {
        }

        @Override // android.net.INetworkPolicyListener
        public void onBlockedReasonChanged(int i, int i2, int i3) {
        }

        @Override // android.net.INetworkPolicyListener
        public void onAllowedTransportsChanged(int[] iArr, long[] jArr) {
        }
    }
}
