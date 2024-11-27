package com.android.server.net;

/* loaded from: classes2.dex */
public class NetworkManagementService extends android.os.INetworkManagementService.Stub {

    @com.android.internal.annotations.GuardedBy({"mQuotaLock"})
    private java.util.HashMap<java.lang.String, java.lang.Long> mActiveAlerts;

    @com.android.internal.annotations.GuardedBy({"mQuotaLock"})
    private java.util.HashMap<java.lang.String, java.lang.Long> mActiveQuotas;
    private com.android.internal.app.IBatteryStats mBatteryStats;
    private final android.content.Context mContext;
    private final android.os.Handler mDaemonHandler;

    @com.android.internal.annotations.GuardedBy({"mQuotaLock"})
    private volatile boolean mDataSaverMode;
    private final com.android.server.net.NetworkManagementService.Dependencies mDeps;

    @com.android.internal.annotations.GuardedBy({"mRulesLock"})
    final android.util.SparseBooleanArray mFirewallChainStates;
    private volatile boolean mFirewallEnabled;
    private android.net.INetd mNetdService;
    private final com.android.server.net.NetworkManagementService.NetdUnsolicitedEventListener mNetdUnsolicitedEventListener;
    private final android.os.RemoteCallbackList<android.net.INetworkManagementEventObserver> mObservers;
    private final java.lang.Object mQuotaLock;
    private final java.lang.Object mRulesLock;
    private volatile boolean mStrictEnabled;

    @com.android.internal.annotations.GuardedBy({"mRulesLock"})
    private android.util.SparseBooleanArray mUidAllowOnMetered;

    @com.android.internal.annotations.GuardedBy({"mQuotaLock"})
    private android.util.SparseIntArray mUidCleartextPolicy;

    @com.android.internal.annotations.GuardedBy({"mRulesLock"})
    private final android.util.SparseIntArray mUidFirewallBackgroundRules;

    @com.android.internal.annotations.GuardedBy({"mRulesLock"})
    private final android.util.SparseIntArray mUidFirewallDozableRules;

    @com.android.internal.annotations.GuardedBy({"mRulesLock"})
    private final android.util.SparseIntArray mUidFirewallLowPowerStandbyRules;

    @com.android.internal.annotations.GuardedBy({"mRulesLock"})
    private final android.util.SparseIntArray mUidFirewallPowerSaveRules;

    @com.android.internal.annotations.GuardedBy({"mRulesLock"})
    private final android.util.SparseIntArray mUidFirewallRestrictedRules;

    @com.android.internal.annotations.GuardedBy({"mRulesLock"})
    private final android.util.SparseIntArray mUidFirewallRules;

    @com.android.internal.annotations.GuardedBy({"mRulesLock"})
    private final android.util.SparseIntArray mUidFirewallStandbyRules;

    @com.android.internal.annotations.GuardedBy({"mRulesLock"})
    private android.util.SparseBooleanArray mUidRejectOnMetered;
    private static final java.lang.String TAG = "NetworkManagement";
    private static final boolean DBG = android.util.Log.isLoggable(TAG, 3);

    /* JADX INFO: Access modifiers changed from: private */
    @java.lang.FunctionalInterface
    interface NetworkManagementEventCallback {
        void sendCallback(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) throws android.os.RemoteException;
    }

    static class Dependencies {
        Dependencies() {
        }

        public android.os.IBinder getService(java.lang.String str) {
            return android.os.ServiceManager.getService(str);
        }

        public void registerLocalService(com.android.server.net.NetworkManagementInternal networkManagementInternal) {
            com.android.server.LocalServices.addService(com.android.server.net.NetworkManagementInternal.class, networkManagementInternal);
        }

        public android.net.INetd getNetd() {
            return android.net.util.NetdService.get();
        }

        public int getCallingUid() {
            return android.os.Binder.getCallingUid();
        }
    }

    private NetworkManagementService(android.content.Context context, com.android.server.net.NetworkManagementService.Dependencies dependencies) {
        super(android.os.PermissionEnforcer.fromContext(context));
        this.mObservers = new android.os.RemoteCallbackList<>();
        this.mQuotaLock = new java.lang.Object();
        this.mRulesLock = new java.lang.Object();
        this.mActiveQuotas = com.google.android.collect.Maps.newHashMap();
        this.mActiveAlerts = com.google.android.collect.Maps.newHashMap();
        this.mUidRejectOnMetered = new android.util.SparseBooleanArray();
        this.mUidAllowOnMetered = new android.util.SparseBooleanArray();
        this.mUidCleartextPolicy = new android.util.SparseIntArray();
        this.mUidFirewallRules = new android.util.SparseIntArray();
        this.mUidFirewallStandbyRules = new android.util.SparseIntArray();
        this.mUidFirewallDozableRules = new android.util.SparseIntArray();
        this.mUidFirewallPowerSaveRules = new android.util.SparseIntArray();
        this.mUidFirewallRestrictedRules = new android.util.SparseIntArray();
        this.mUidFirewallLowPowerStandbyRules = new android.util.SparseIntArray();
        this.mUidFirewallBackgroundRules = new android.util.SparseIntArray();
        this.mFirewallChainStates = new android.util.SparseBooleanArray();
        this.mContext = context;
        this.mDeps = dependencies;
        this.mDaemonHandler = new android.os.Handler(com.android.server.FgThread.get().getLooper());
        this.mNetdUnsolicitedEventListener = new com.android.server.net.NetworkManagementService.NetdUnsolicitedEventListener();
        this.mDeps.registerLocalService(new com.android.server.net.NetworkManagementService.LocalService());
    }

    static com.android.server.net.NetworkManagementService create(android.content.Context context, com.android.server.net.NetworkManagementService.Dependencies dependencies) throws java.lang.InterruptedException {
        com.android.server.net.NetworkManagementService networkManagementService = new com.android.server.net.NetworkManagementService(context, dependencies);
        if (DBG) {
            android.util.Slog.d(TAG, "Creating NetworkManagementService");
        }
        if (DBG) {
            android.util.Slog.d(TAG, "Connecting native netd service");
        }
        networkManagementService.connectNativeNetdService();
        if (DBG) {
            android.util.Slog.d(TAG, "Connected");
        }
        return networkManagementService;
    }

    public static com.android.server.net.NetworkManagementService create(android.content.Context context) throws java.lang.InterruptedException {
        return create(context, new com.android.server.net.NetworkManagementService.Dependencies());
    }

    public void systemReady() {
        if (DBG) {
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            prepareNativeDaemon();
            android.util.Slog.d(TAG, "Prepared in " + (java.lang.System.currentTimeMillis() - currentTimeMillis) + "ms");
            return;
        }
        prepareNativeDaemon();
    }

    private com.android.internal.app.IBatteryStats getBatteryStats() {
        synchronized (this) {
            try {
                if (this.mBatteryStats != null) {
                    return this.mBatteryStats;
                }
                this.mBatteryStats = com.android.internal.app.IBatteryStats.Stub.asInterface(this.mDeps.getService("batterystats"));
                return this.mBatteryStats;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void registerObserver(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        this.mObservers.register(iNetworkManagementEventObserver);
    }

    public void unregisterObserver(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        this.mObservers.unregister(iNetworkManagementEventObserver);
    }

    private void invokeForAllObservers(com.android.server.net.NetworkManagementService.NetworkManagementEventCallback networkManagementEventCallback) {
        int beginBroadcast = this.mObservers.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                networkManagementEventCallback.sendCallback(this.mObservers.getBroadcastItem(i));
            } catch (android.os.RemoteException | java.lang.RuntimeException e) {
            } catch (java.lang.Throwable th) {
                this.mObservers.finishBroadcast();
                throw th;
            }
        }
        this.mObservers.finishBroadcast();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyInterfaceStatusChanged(final java.lang.String str, final boolean z) {
        invokeForAllObservers(new com.android.server.net.NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda2
            @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
            public final void sendCallback(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) {
                iNetworkManagementEventObserver.interfaceStatusChanged(str, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyInterfaceLinkStateChanged(final java.lang.String str, final boolean z) {
        invokeForAllObservers(new com.android.server.net.NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda4
            @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
            public final void sendCallback(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) {
                iNetworkManagementEventObserver.interfaceLinkStateChanged(str, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyInterfaceAdded(final java.lang.String str) {
        invokeForAllObservers(new com.android.server.net.NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda0
            @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
            public final void sendCallback(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) {
                iNetworkManagementEventObserver.interfaceAdded(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyInterfaceRemoved(final java.lang.String str) {
        this.mActiveAlerts.remove(str);
        this.mActiveQuotas.remove(str);
        invokeForAllObservers(new com.android.server.net.NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda5
            @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
            public final void sendCallback(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) {
                iNetworkManagementEventObserver.interfaceRemoved(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyLimitReached(final java.lang.String str, final java.lang.String str2) {
        invokeForAllObservers(new com.android.server.net.NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda10
            @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
            public final void sendCallback(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) {
                iNetworkManagementEventObserver.limitReached(str, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyInterfaceClassActivity(final int i, final boolean z, final long j, final int i2) {
        invokeForAllObservers(new com.android.server.net.NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda1
            @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
            public final void sendCallback(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) {
                iNetworkManagementEventObserver.interfaceClassDataActivityChanged(i, z, j, i2);
            }
        });
    }

    private void syncFirewallChainLocked(int i, java.lang.String str) {
        android.util.SparseIntArray clone;
        synchronized (this.mRulesLock) {
            android.util.SparseIntArray uidFirewallRulesLR = getUidFirewallRulesLR(i);
            clone = uidFirewallRulesLR.clone();
            uidFirewallRulesLR.clear();
        }
        if (clone.size() > 0) {
            if (DBG) {
                android.util.Slog.d(TAG, "Pushing " + clone.size() + " active firewall " + str + "UID rules");
            }
            for (int i2 = 0; i2 < clone.size(); i2++) {
                setFirewallUidRuleLocked(i, clone.keyAt(i2), clone.valueAt(i2));
            }
        }
    }

    private void connectNativeNetdService() {
        this.mNetdService = this.mDeps.getNetd();
        try {
            this.mNetdService.registerUnsolicitedEventListener(this.mNetdUnsolicitedEventListener);
            if (DBG) {
                android.util.Slog.d(TAG, "Register unsolicited event listener");
            }
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            android.util.Slog.e(TAG, "Failed to set Netd unsolicited event listener " + e);
        }
    }

    private void prepareNativeDaemon() {
        android.util.SparseBooleanArray sparseBooleanArray;
        android.util.SparseBooleanArray sparseBooleanArray2;
        synchronized (this.mQuotaLock) {
            try {
                this.mStrictEnabled = true;
                setDataSaverModeEnabled(this.mDataSaverMode);
                int size = this.mActiveQuotas.size();
                if (size > 0) {
                    if (DBG) {
                        android.util.Slog.d(TAG, "Pushing " + size + " active quota rules");
                    }
                    java.util.HashMap<java.lang.String, java.lang.Long> hashMap = this.mActiveQuotas;
                    this.mActiveQuotas = com.google.android.collect.Maps.newHashMap();
                    for (java.util.Map.Entry<java.lang.String, java.lang.Long> entry : hashMap.entrySet()) {
                        setInterfaceQuota(entry.getKey(), entry.getValue().longValue());
                    }
                }
                int size2 = this.mActiveAlerts.size();
                if (size2 > 0) {
                    if (DBG) {
                        android.util.Slog.d(TAG, "Pushing " + size2 + " active alert rules");
                    }
                    java.util.HashMap<java.lang.String, java.lang.Long> hashMap2 = this.mActiveAlerts;
                    this.mActiveAlerts = com.google.android.collect.Maps.newHashMap();
                    for (java.util.Map.Entry<java.lang.String, java.lang.Long> entry2 : hashMap2.entrySet()) {
                        setInterfaceAlert(entry2.getKey(), entry2.getValue().longValue());
                    }
                }
                synchronized (this.mRulesLock) {
                    try {
                        int size3 = this.mUidRejectOnMetered.size();
                        sparseBooleanArray = null;
                        if (size3 <= 0) {
                            sparseBooleanArray2 = null;
                        } else {
                            if (DBG) {
                                android.util.Slog.d(TAG, "Pushing " + size3 + " UIDs to metered denylist rules");
                            }
                            sparseBooleanArray2 = this.mUidRejectOnMetered;
                            this.mUidRejectOnMetered = new android.util.SparseBooleanArray();
                        }
                        int size4 = this.mUidAllowOnMetered.size();
                        if (size4 > 0) {
                            if (DBG) {
                                android.util.Slog.d(TAG, "Pushing " + size4 + " UIDs to metered allowlist rules");
                            }
                            sparseBooleanArray = this.mUidAllowOnMetered;
                            this.mUidAllowOnMetered = new android.util.SparseBooleanArray();
                        }
                    } finally {
                    }
                }
                if (sparseBooleanArray2 != null) {
                    for (int i = 0; i < sparseBooleanArray2.size(); i++) {
                        setUidOnMeteredNetworkDenylist(sparseBooleanArray2.keyAt(i), sparseBooleanArray2.valueAt(i));
                    }
                }
                if (sparseBooleanArray != null) {
                    for (int i2 = 0; i2 < sparseBooleanArray.size(); i2++) {
                        setUidOnMeteredNetworkAllowlist(sparseBooleanArray.keyAt(i2), sparseBooleanArray.valueAt(i2));
                    }
                }
                int size5 = this.mUidCleartextPolicy.size();
                if (size5 > 0) {
                    if (DBG) {
                        android.util.Slog.d(TAG, "Pushing " + size5 + " active UID cleartext policies");
                    }
                    android.util.SparseIntArray sparseIntArray = this.mUidCleartextPolicy;
                    this.mUidCleartextPolicy = new android.util.SparseIntArray();
                    for (int i3 = 0; i3 < sparseIntArray.size(); i3++) {
                        setUidCleartextNetworkPolicy(sparseIntArray.keyAt(i3), sparseIntArray.valueAt(i3));
                    }
                }
                setFirewallEnabled(this.mFirewallEnabled);
                syncFirewallChainLocked(0, "");
                syncFirewallChainLocked(2, "standby ");
                syncFirewallChainLocked(1, "dozable ");
                syncFirewallChainLocked(3, "powersave ");
                syncFirewallChainLocked(4, "restricted ");
                syncFirewallChainLocked(5, "low power standby ");
                syncFirewallChainLocked(6, "background");
                int[] iArr = {2, 1, 3, 4, 5, 6};
                for (int i4 = 0; i4 < 6; i4++) {
                    int i5 = iArr[i4];
                    if (getFirewallChainState(i5)) {
                        setFirewallChainEnabled(i5, true);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        try {
            getBatteryStats().noteNetworkStatsEnabled();
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAddressUpdated(final java.lang.String str, final android.net.LinkAddress linkAddress) {
        invokeForAllObservers(new com.android.server.net.NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda7
            @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
            public final void sendCallback(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) {
                iNetworkManagementEventObserver.addressUpdated(str, linkAddress);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAddressRemoved(final java.lang.String str, final android.net.LinkAddress linkAddress) {
        invokeForAllObservers(new com.android.server.net.NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda3
            @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
            public final void sendCallback(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) {
                iNetworkManagementEventObserver.addressRemoved(str, linkAddress);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyInterfaceDnsServerInfo(final java.lang.String str, final long j, final java.lang.String[] strArr) {
        invokeForAllObservers(new com.android.server.net.NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda6
            @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
            public final void sendCallback(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) {
                iNetworkManagementEventObserver.interfaceDnsServerInfo(str, j, strArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRouteChange(boolean z, final android.net.RouteInfo routeInfo) {
        if (z) {
            invokeForAllObservers(new com.android.server.net.NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda8
                @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
                public final void sendCallback(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) {
                    iNetworkManagementEventObserver.routeUpdated(routeInfo);
                }
            });
        } else {
            invokeForAllObservers(new com.android.server.net.NetworkManagementService.NetworkManagementEventCallback() { // from class: com.android.server.net.NetworkManagementService$$ExternalSyntheticLambda9
                @Override // com.android.server.net.NetworkManagementService.NetworkManagementEventCallback
                public final void sendCallback(android.net.INetworkManagementEventObserver iNetworkManagementEventObserver) {
                    iNetworkManagementEventObserver.routeRemoved(routeInfo);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class NetdUnsolicitedEventListener extends android.net.INetdUnsolicitedEventListener.Stub {
        private NetdUnsolicitedEventListener() {
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceClassActivityChanged(final boolean z, final int i, long j, final int i2) throws android.os.RemoteException {
            final long j2;
            if (j <= 0) {
                j2 = android.os.SystemClock.elapsedRealtimeNanos();
            } else {
                j2 = j;
            }
            com.android.server.net.NetworkManagementService.this.mDaemonHandler.post(new java.lang.Runnable() { // from class: com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.net.NetworkManagementService.NetdUnsolicitedEventListener.this.lambda$onInterfaceClassActivityChanged$0(i, z, j2, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInterfaceClassActivityChanged$0(int i, boolean z, long j, int i2) {
            com.android.server.net.NetworkManagementService.this.notifyInterfaceClassActivity(i, z, j, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onQuotaLimitReached$1(java.lang.String str, java.lang.String str2) {
            com.android.server.net.NetworkManagementService.this.notifyLimitReached(str, str2);
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onQuotaLimitReached(final java.lang.String str, final java.lang.String str2) throws android.os.RemoteException {
            com.android.server.net.NetworkManagementService.this.mDaemonHandler.post(new java.lang.Runnable() { // from class: com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.net.NetworkManagementService.NetdUnsolicitedEventListener.this.lambda$onQuotaLimitReached$1(str, str2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInterfaceDnsServerInfo$2(java.lang.String str, long j, java.lang.String[] strArr) {
            com.android.server.net.NetworkManagementService.this.notifyInterfaceDnsServerInfo(str, j, strArr);
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceDnsServerInfo(final java.lang.String str, final long j, final java.lang.String[] strArr) throws android.os.RemoteException {
            com.android.server.net.NetworkManagementService.this.mDaemonHandler.post(new java.lang.Runnable() { // from class: com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.net.NetworkManagementService.NetdUnsolicitedEventListener.this.lambda$onInterfaceDnsServerInfo$2(str, j, strArr);
                }
            });
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceAddressUpdated(java.lang.String str, final java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            final android.net.LinkAddress linkAddress = new android.net.LinkAddress(str, i, i2);
            com.android.server.net.NetworkManagementService.this.mDaemonHandler.post(new java.lang.Runnable() { // from class: com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.net.NetworkManagementService.NetdUnsolicitedEventListener.this.lambda$onInterfaceAddressUpdated$3(str2, linkAddress);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInterfaceAddressUpdated$3(java.lang.String str, android.net.LinkAddress linkAddress) {
            com.android.server.net.NetworkManagementService.this.notifyAddressUpdated(str, linkAddress);
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceAddressRemoved(java.lang.String str, final java.lang.String str2, int i, int i2) throws android.os.RemoteException {
            final android.net.LinkAddress linkAddress = new android.net.LinkAddress(str, i, i2);
            com.android.server.net.NetworkManagementService.this.mDaemonHandler.post(new java.lang.Runnable() { // from class: com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.net.NetworkManagementService.NetdUnsolicitedEventListener.this.lambda$onInterfaceAddressRemoved$4(str2, linkAddress);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInterfaceAddressRemoved$4(java.lang.String str, android.net.LinkAddress linkAddress) {
            com.android.server.net.NetworkManagementService.this.notifyAddressRemoved(str, linkAddress);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInterfaceAdded$5(java.lang.String str) {
            com.android.server.net.NetworkManagementService.this.notifyInterfaceAdded(str);
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceAdded(final java.lang.String str) throws android.os.RemoteException {
            com.android.server.net.NetworkManagementService.this.mDaemonHandler.post(new java.lang.Runnable() { // from class: com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.net.NetworkManagementService.NetdUnsolicitedEventListener.this.lambda$onInterfaceAdded$5(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInterfaceRemoved$6(java.lang.String str) {
            com.android.server.net.NetworkManagementService.this.notifyInterfaceRemoved(str);
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceRemoved(final java.lang.String str) throws android.os.RemoteException {
            com.android.server.net.NetworkManagementService.this.mDaemonHandler.post(new java.lang.Runnable() { // from class: com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.net.NetworkManagementService.NetdUnsolicitedEventListener.this.lambda$onInterfaceRemoved$6(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInterfaceChanged$7(java.lang.String str, boolean z) {
            com.android.server.net.NetworkManagementService.this.notifyInterfaceStatusChanged(str, z);
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceChanged(final java.lang.String str, final boolean z) throws android.os.RemoteException {
            com.android.server.net.NetworkManagementService.this.mDaemonHandler.post(new java.lang.Runnable() { // from class: com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.net.NetworkManagementService.NetdUnsolicitedEventListener.this.lambda$onInterfaceChanged$7(str, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInterfaceLinkStateChanged$8(java.lang.String str, boolean z) {
            com.android.server.net.NetworkManagementService.this.notifyInterfaceLinkStateChanged(str, z);
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onInterfaceLinkStateChanged(final java.lang.String str, final boolean z) throws android.os.RemoteException {
            com.android.server.net.NetworkManagementService.this.mDaemonHandler.post(new java.lang.Runnable() { // from class: com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.net.NetworkManagementService.NetdUnsolicitedEventListener.this.lambda$onInterfaceLinkStateChanged$8(str, z);
                }
            });
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onRouteChanged(final boolean z, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            final android.net.RouteInfo routeInfo = new android.net.RouteInfo(new android.net.IpPrefix(str), "".equals(str2) ? null : android.net.InetAddresses.parseNumericAddress(str2), str3, 1);
            com.android.server.net.NetworkManagementService.this.mDaemonHandler.post(new java.lang.Runnable() { // from class: com.android.server.net.NetworkManagementService$NetdUnsolicitedEventListener$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.net.NetworkManagementService.NetdUnsolicitedEventListener.this.lambda$onRouteChanged$9(z, routeInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRouteChanged$9(boolean z, android.net.RouteInfo routeInfo) {
            com.android.server.net.NetworkManagementService.this.notifyRouteChange(z, routeInfo);
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public void onStrictCleartextDetected(int i, java.lang.String str) throws android.os.RemoteException {
            android.app.ActivityManager.getService().notifyCleartextNetwork(i, com.android.internal.util.HexDump.hexStringToByteArray(str));
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public int getInterfaceVersion() {
            return 14;
        }

        @Override // android.net.INetdUnsolicitedEventListener
        public java.lang.String getInterfaceHash() {
            return "50bce96bc8d5811ed952950df30ec503f8a561ed";
        }
    }

    public java.lang.String[] listInterfaces() {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermissionOr(this.mContext, new java.lang.String[]{"android.permission.CONNECTIVITY_INTERNAL"});
        try {
            return this.mNetdService.interfaceGetList();
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    private static android.net.InterfaceConfigurationParcel toStableParcel(android.net.InterfaceConfiguration interfaceConfiguration, java.lang.String str) {
        android.net.InterfaceConfigurationParcel interfaceConfigurationParcel = new android.net.InterfaceConfigurationParcel();
        interfaceConfigurationParcel.ifName = str;
        java.lang.String hardwareAddress = interfaceConfiguration.getHardwareAddress();
        if (!android.text.TextUtils.isEmpty(hardwareAddress)) {
            interfaceConfigurationParcel.hwAddr = hardwareAddress;
        } else {
            interfaceConfigurationParcel.hwAddr = "";
        }
        interfaceConfigurationParcel.ipv4Addr = interfaceConfiguration.getLinkAddress().getAddress().getHostAddress();
        interfaceConfigurationParcel.prefixLength = interfaceConfiguration.getLinkAddress().getPrefixLength();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator it = interfaceConfiguration.getFlags().iterator();
        while (it.hasNext()) {
            arrayList.add((java.lang.String) it.next());
        }
        interfaceConfigurationParcel.flags = (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
        return interfaceConfigurationParcel;
    }

    public static android.net.InterfaceConfiguration fromStableParcel(android.net.InterfaceConfigurationParcel interfaceConfigurationParcel) {
        android.net.InterfaceConfiguration interfaceConfiguration = new android.net.InterfaceConfiguration();
        interfaceConfiguration.setHardwareAddress(interfaceConfigurationParcel.hwAddr);
        interfaceConfiguration.setLinkAddress(new android.net.LinkAddress(android.net.InetAddresses.parseNumericAddress(interfaceConfigurationParcel.ipv4Addr), interfaceConfigurationParcel.prefixLength));
        for (java.lang.String str : interfaceConfigurationParcel.flags) {
            interfaceConfiguration.setFlag(str);
        }
        return interfaceConfiguration;
    }

    public android.net.InterfaceConfiguration getInterfaceConfig(java.lang.String str) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermissionOr(this.mContext, new java.lang.String[]{"android.permission.CONNECTIVITY_INTERNAL"});
        try {
            try {
                return fromStableParcel(this.mNetdService.interfaceGetCfg(str));
            } catch (java.lang.IllegalArgumentException e) {
                throw new java.lang.IllegalStateException("Invalid InterfaceConfigurationParcel", e);
            }
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e2) {
            throw new java.lang.IllegalStateException(e2);
        }
    }

    public void setInterfaceConfig(java.lang.String str, android.net.InterfaceConfiguration interfaceConfiguration) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermissionOr(this.mContext, new java.lang.String[]{"android.permission.CONNECTIVITY_INTERNAL"});
        android.net.LinkAddress linkAddress = interfaceConfiguration.getLinkAddress();
        if (linkAddress == null || linkAddress.getAddress() == null) {
            throw new java.lang.IllegalStateException("Null LinkAddress given");
        }
        try {
            this.mNetdService.interfaceSetCfg(toStableParcel(interfaceConfiguration, str));
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void setInterfaceDown(java.lang.String str) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        android.net.InterfaceConfiguration interfaceConfig = getInterfaceConfig(str);
        interfaceConfig.setInterfaceDown();
        setInterfaceConfig(str, interfaceConfig);
    }

    public void setInterfaceUp(java.lang.String str) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        android.net.InterfaceConfiguration interfaceConfig = getInterfaceConfig(str);
        interfaceConfig.setInterfaceUp();
        setInterfaceConfig(str, interfaceConfig);
    }

    public void setInterfaceIpv6PrivacyExtensions(java.lang.String str, boolean z) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.interfaceSetIPv6PrivacyExtensions(str, z);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void clearInterfaceAddresses(java.lang.String str) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.interfaceClearAddrs(str);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void enableIpv6(java.lang.String str) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.interfaceSetEnableIPv6(str, true);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void setIPv6AddrGenMode(java.lang.String str, int i) throws android.os.ServiceSpecificException {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.setIPv6AddrGenMode(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void disableIpv6(java.lang.String str) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.interfaceSetEnableIPv6(str, false);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    @android.annotation.EnforcePermission("android.permission.SHUTDOWN")
    public void shutdown() {
        super.shutdown_enforcePermission();
        android.util.Slog.i(TAG, "Shutting down");
    }

    public boolean getIpForwardingEnabled() throws java.lang.IllegalStateException {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (com.android.modules.utils.build.SdkLevel.isAtLeastV()) {
            throw new java.lang.UnsupportedOperationException("NMS#getIpForwardingEnabled not supported in V+");
        }
        try {
            return this.mNetdService.ipfwdEnabled();
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void setIpForwardingEnabled(boolean z) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (com.android.modules.utils.build.SdkLevel.isAtLeastV()) {
            throw new java.lang.UnsupportedOperationException("NMS#setIpForwardingEnabled not supported in V+");
        }
        try {
            if (z) {
                this.mNetdService.ipfwdEnableForwarding("tethering");
            } else {
                this.mNetdService.ipfwdDisableForwarding("tethering");
            }
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void startTethering(java.lang.String[] strArr) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (com.android.modules.utils.build.SdkLevel.isAtLeastV()) {
            throw new java.lang.UnsupportedOperationException("NMS#startTethering not supported in V+");
        }
        try {
            com.android.net.module.util.NetdUtils.tetherStart(this.mNetdService, true, strArr);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void stopTethering() {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (com.android.modules.utils.build.SdkLevel.isAtLeastV()) {
            throw new java.lang.UnsupportedOperationException("NMS#stopTethering not supported in V+");
        }
        try {
            this.mNetdService.tetherStop();
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public boolean isTetheringStarted() {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (com.android.modules.utils.build.SdkLevel.isAtLeastV()) {
            throw new java.lang.UnsupportedOperationException("NMS#isTetheringStarted not supported in V+");
        }
        try {
            return this.mNetdService.tetherIsEnabled();
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void tetherInterface(java.lang.String str) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (com.android.modules.utils.build.SdkLevel.isAtLeastV()) {
            throw new java.lang.UnsupportedOperationException("NMS#tetherInterface not supported in V+");
        }
        try {
            android.net.LinkAddress linkAddress = getInterfaceConfig(str).getLinkAddress();
            com.android.net.module.util.NetdUtils.tetherInterface(this.mNetdService, str, new android.net.IpPrefix(linkAddress.getAddress(), linkAddress.getPrefixLength()));
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void untetherInterface(java.lang.String str) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (com.android.modules.utils.build.SdkLevel.isAtLeastV()) {
            throw new java.lang.UnsupportedOperationException("NMS#untetherInterface not supported in V+");
        }
        try {
            com.android.net.module.util.NetdUtils.untetherInterface(this.mNetdService, str);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public java.lang.String[] listTetheredInterfaces() {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (com.android.modules.utils.build.SdkLevel.isAtLeastV()) {
            throw new java.lang.UnsupportedOperationException("NMS#listTetheredInterfaces not supported in V+");
        }
        try {
            return this.mNetdService.tetherInterfaceList();
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void enableNat(java.lang.String str, java.lang.String str2) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (com.android.modules.utils.build.SdkLevel.isAtLeastV()) {
            throw new java.lang.UnsupportedOperationException("NMS#enableNat not supported in V+");
        }
        try {
            this.mNetdService.tetherAddForward(str, str2);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void disableNat(java.lang.String str, java.lang.String str2) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (com.android.modules.utils.build.SdkLevel.isAtLeastV()) {
            throw new java.lang.UnsupportedOperationException("NMS#disableNat not supported in V+");
        }
        try {
            this.mNetdService.tetherRemoveForward(str, str2);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void setInterfaceQuota(java.lang.String str, long j) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        synchronized (this.mQuotaLock) {
            try {
                if (this.mActiveQuotas.containsKey(str)) {
                    throw new java.lang.IllegalStateException("iface " + str + " already has quota");
                }
                try {
                    this.mNetdService.bandwidthSetInterfaceQuota(str, j);
                    this.mActiveQuotas.put(str, java.lang.Long.valueOf(j));
                } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                    throw new java.lang.IllegalStateException(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeInterfaceQuota(java.lang.String str) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        synchronized (this.mQuotaLock) {
            try {
                if (this.mActiveQuotas.containsKey(str)) {
                    this.mActiveQuotas.remove(str);
                    this.mActiveAlerts.remove(str);
                    try {
                        this.mNetdService.bandwidthRemoveInterfaceQuota(str);
                    } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                        throw new java.lang.IllegalStateException(e);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setInterfaceAlert(java.lang.String str, long j) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        if (!this.mActiveQuotas.containsKey(str)) {
            throw new java.lang.IllegalStateException("setting alert requires existing quota on iface");
        }
        synchronized (this.mQuotaLock) {
            try {
                if (this.mActiveAlerts.containsKey(str)) {
                    throw new java.lang.IllegalStateException("iface " + str + " already has alert");
                }
                try {
                    this.mNetdService.bandwidthSetInterfaceAlert(str, j);
                    this.mActiveAlerts.put(str, java.lang.Long.valueOf(j));
                } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                    throw new java.lang.IllegalStateException(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeInterfaceAlert(java.lang.String str) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        synchronized (this.mQuotaLock) {
            if (this.mActiveAlerts.containsKey(str)) {
                try {
                    this.mNetdService.bandwidthRemoveInterfaceAlert(str);
                    this.mActiveAlerts.remove(str);
                } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                    throw new java.lang.IllegalStateException(e);
                }
            }
        }
    }

    private void setUidOnMeteredNetworkList(int i, boolean z, boolean z2) {
        android.util.SparseBooleanArray sparseBooleanArray;
        boolean z3;
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        synchronized (this.mQuotaLock) {
            try {
                synchronized (this.mRulesLock) {
                    sparseBooleanArray = z ? this.mUidAllowOnMetered : this.mUidRejectOnMetered;
                    z3 = sparseBooleanArray.get(i, false);
                }
                if (z3 == z2) {
                    return;
                }
                android.os.Trace.traceBegin(2097152L, "inetd bandwidth");
                android.net.ConnectivityManager connectivityManager = (android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class);
                try {
                    if (z) {
                        if (z2) {
                            connectivityManager.addUidToMeteredNetworkAllowList(i);
                        } else {
                            connectivityManager.removeUidFromMeteredNetworkAllowList(i);
                        }
                    } else if (z2) {
                        connectivityManager.addUidToMeteredNetworkDenyList(i);
                    } else {
                        connectivityManager.removeUidFromMeteredNetworkDenyList(i);
                    }
                    synchronized (this.mRulesLock) {
                        try {
                            if (z2) {
                                sparseBooleanArray.put(i, true);
                            } else {
                                sparseBooleanArray.delete(i);
                            }
                        } finally {
                        }
                    }
                    android.os.Trace.traceEnd(2097152L);
                } catch (java.lang.RuntimeException e) {
                    throw new java.lang.IllegalStateException(e);
                }
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(2097152L);
                throw th;
            } finally {
            }
        }
    }

    public void setUidOnMeteredNetworkDenylist(int i, boolean z) {
        setUidOnMeteredNetworkList(i, false, z);
    }

    public void setUidOnMeteredNetworkAllowlist(int i, boolean z) {
        setUidOnMeteredNetworkList(i, true, z);
    }

    @android.annotation.EnforcePermission("android.permission.NETWORK_SETTINGS")
    public boolean setDataSaverModeEnabled(boolean z) {
        super.setDataSaverModeEnabled_enforcePermission();
        if (DBG) {
            android.util.Log.d(TAG, "setDataSaverMode: " + z);
        }
        synchronized (this.mQuotaLock) {
            try {
                if (this.mDataSaverMode == z) {
                    android.util.Log.w(TAG, "setDataSaverMode(): already " + this.mDataSaverMode);
                    return true;
                }
                android.os.Trace.traceBegin(2097152L, "setDataSaverModeEnabled");
                try {
                    try {
                        if (com.android.modules.utils.build.SdkLevel.isAtLeastV()) {
                            ((android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class)).setDataSaverEnabled(z);
                            this.mDataSaverMode = z;
                            return true;
                        }
                        boolean bandwidthEnableDataSaver = this.mNetdService.bandwidthEnableDataSaver(z);
                        if (bandwidthEnableDataSaver) {
                            this.mDataSaverMode = z;
                        } else {
                            android.util.Log.e(TAG, "setDataSaverMode(" + z + "): failed to set iptables");
                        }
                        return bandwidthEnableDataSaver;
                    } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
                        android.util.Log.e(TAG, "setDataSaverMode(" + z + "): failed with exception", e);
                        return false;
                    }
                } finally {
                    android.os.Trace.traceEnd(2097152L);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void applyUidCleartextNetworkPolicy(int i, int i2) {
        int i3;
        switch (i2) {
            case 0:
                i3 = 1;
                break;
            case 1:
                i3 = 2;
                break;
            case 2:
                i3 = 3;
                break;
            default:
                throw new java.lang.IllegalArgumentException("Unknown policy " + i2);
        }
        try {
            this.mNetdService.strictUidCleartextPenalty(i, i3);
            this.mUidCleartextPolicy.put(i, i2);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void setUidCleartextNetworkPolicy(int i, int i2) {
        if (this.mDeps.getCallingUid() != i) {
            com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        }
        synchronized (this.mQuotaLock) {
            try {
                int i3 = this.mUidCleartextPolicy.get(i, 0);
                if (i3 == i2) {
                    return;
                }
                if (!this.mStrictEnabled) {
                    this.mUidCleartextPolicy.put(i, i2);
                    return;
                }
                if (i3 != 0 && i2 != 0) {
                    applyUidCleartextNetworkPolicy(i, 0);
                }
                applyUidCleartextNetworkPolicy(i, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isBandwidthControlEnabled() {
        return true;
    }

    public void setFirewallEnabled(boolean z) {
        enforceSystemUid();
        try {
            this.mNetdService.firewallSetFirewallType(z ? 0 : 1);
            this.mFirewallEnabled = z;
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public boolean isFirewallEnabled() {
        enforceSystemUid();
        return this.mFirewallEnabled;
    }

    public void setFirewallChainEnabled(int i, boolean z) {
        enforceSystemUid();
        synchronized (this.mQuotaLock) {
            synchronized (this.mRulesLock) {
                if (getFirewallChainState(i) == z) {
                    return;
                }
                setFirewallChainState(i, z);
                java.lang.String firewallChainName = getFirewallChainName(i);
                if (i == 0) {
                    throw new java.lang.IllegalArgumentException("Bad child chain: " + firewallChainName);
                }
                try {
                    ((android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class)).setFirewallChainEnabled(i, z);
                } catch (java.lang.RuntimeException e) {
                    throw new java.lang.IllegalStateException(e);
                }
            }
        }
    }

    private java.lang.String getFirewallChainName(int i) {
        switch (i) {
            case 1:
                return "dozable";
            case 2:
                return "standby";
            case 3:
                return "powersave";
            case 4:
                return "restricted";
            case 5:
                return com.android.server.power.LowPowerStandbyController.DeviceConfigWrapper.NAMESPACE;
            case 6:
                return "background";
            default:
                throw new java.lang.IllegalArgumentException("Bad child chain: " + i);
        }
    }

    private int getFirewallType(int i) {
        switch (i) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                if (isFirewallEnabled()) {
                    break;
                }
                break;
        }
        return 0;
    }

    public void setFirewallUidRules(int i, int[] iArr, int[] iArr2) {
        enforceSystemUid();
        synchronized (this.mQuotaLock) {
            synchronized (this.mRulesLock) {
                try {
                    android.util.SparseIntArray uidFirewallRulesLR = getUidFirewallRulesLR(i);
                    android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
                    for (int length = iArr.length - 1; length >= 0; length--) {
                        int i2 = iArr[length];
                        int i3 = iArr2[length];
                        updateFirewallUidRuleLocked(i, i2, i3);
                        sparseIntArray.put(i2, i3);
                    }
                    android.util.SparseIntArray sparseIntArray2 = new android.util.SparseIntArray();
                    for (int size = uidFirewallRulesLR.size() - 1; size >= 0; size--) {
                        int keyAt = uidFirewallRulesLR.keyAt(size);
                        if (sparseIntArray.indexOfKey(keyAt) < 0) {
                            sparseIntArray2.put(keyAt, 0);
                        }
                    }
                    for (int size2 = sparseIntArray2.size() - 1; size2 >= 0; size2--) {
                        updateFirewallUidRuleLocked(i, sparseIntArray2.keyAt(size2), 0);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            try {
                ((android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class)).replaceFirewallChain(i, iArr);
            } catch (java.lang.RuntimeException e) {
                android.util.Slog.w(TAG, "Error flushing firewall chain " + i, e);
            }
        }
    }

    public void setFirewallUidRule(int i, int i2, int i3) {
        enforceSystemUid();
        synchronized (this.mQuotaLock) {
            setFirewallUidRuleLocked(i, i2, i3);
        }
    }

    private void setFirewallUidRuleLocked(int i, int i2, int i3) {
        if (updateFirewallUidRuleLocked(i, i2, i3)) {
            try {
                ((android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class)).setUidFirewallRule(i, i2, i3);
            } catch (java.lang.RuntimeException e) {
                throw new java.lang.IllegalStateException(e);
            }
        }
    }

    private boolean updateFirewallUidRuleLocked(int i, int i2, int i3) {
        synchronized (this.mRulesLock) {
            try {
                android.util.SparseIntArray uidFirewallRulesLR = getUidFirewallRulesLR(i);
                int i4 = uidFirewallRulesLR.get(i2, 0);
                if (DBG) {
                    android.util.Slog.d(TAG, "oldRule = " + i4 + ", newRule=" + i3 + " for uid=" + i2 + " on chain " + i);
                }
                if (i4 == i3) {
                    if (DBG) {
                        android.util.Slog.d(TAG, "!!!!! Skipping change");
                    }
                    return false;
                }
                java.lang.String firewallRuleName = getFirewallRuleName(i, i3);
                java.lang.String firewallRuleName2 = getFirewallRuleName(i, i4);
                if (i3 == 0) {
                    uidFirewallRulesLR.delete(i2);
                } else {
                    uidFirewallRulesLR.put(i2, i3);
                }
                return firewallRuleName.equals(firewallRuleName2) ? false : true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    private java.lang.String getFirewallRuleName(int i, int i2) {
        return getFirewallType(i) == 0 ? i2 == 1 ? "allow" : "deny" : i2 == 2 ? "deny" : "allow";
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mRulesLock"})
    private android.util.SparseIntArray getUidFirewallRulesLR(int i) {
        switch (i) {
            case 0:
                return this.mUidFirewallRules;
            case 1:
                return this.mUidFirewallDozableRules;
            case 2:
                return this.mUidFirewallStandbyRules;
            case 3:
                return this.mUidFirewallPowerSaveRules;
            case 4:
                return this.mUidFirewallRestrictedRules;
            case 5:
                return this.mUidFirewallLowPowerStandbyRules;
            case 6:
                return this.mUidFirewallBackgroundRules;
            default:
                throw new java.lang.IllegalArgumentException("Unknown chain:" + i);
        }
    }

    private void enforceSystemUid() {
        if (this.mDeps.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Only available to AID_SYSTEM");
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            synchronized (this.mQuotaLock) {
                printWriter.print("Active quota ifaces: ");
                printWriter.println(this.mActiveQuotas.toString());
                printWriter.print("Active alert ifaces: ");
                printWriter.println(this.mActiveAlerts.toString());
                printWriter.print("Data saver mode: ");
                printWriter.println(this.mDataSaverMode);
                synchronized (this.mRulesLock) {
                    dumpUidRuleOnQuotaLocked(printWriter, "denied UIDs", this.mUidRejectOnMetered);
                    dumpUidRuleOnQuotaLocked(printWriter, "allowed UIDs", this.mUidAllowOnMetered);
                }
            }
            synchronized (this.mRulesLock) {
                dumpUidFirewallRule(printWriter, "", this.mUidFirewallRules);
                printWriter.print("UID firewall standby chain enabled: ");
                printWriter.println(getFirewallChainState(2));
                dumpUidFirewallRule(printWriter, "standby", this.mUidFirewallStandbyRules);
                printWriter.print("UID firewall dozable chain enabled: ");
                printWriter.println(getFirewallChainState(1));
                dumpUidFirewallRule(printWriter, "dozable", this.mUidFirewallDozableRules);
                printWriter.print("UID firewall powersave chain enabled: ");
                printWriter.println(getFirewallChainState(3));
                dumpUidFirewallRule(printWriter, "powersave", this.mUidFirewallPowerSaveRules);
                printWriter.print("UID firewall restricted mode chain enabled: ");
                printWriter.println(getFirewallChainState(4));
                dumpUidFirewallRule(printWriter, "restricted", this.mUidFirewallRestrictedRules);
                printWriter.print("UID firewall low power standby chain enabled: ");
                printWriter.println(getFirewallChainState(5));
                dumpUidFirewallRule(printWriter, com.android.server.power.LowPowerStandbyController.DeviceConfigWrapper.NAMESPACE, this.mUidFirewallLowPowerStandbyRules);
                printWriter.print("UID firewall background chain enabled: ");
                printWriter.println(getFirewallChainState(6));
                dumpUidFirewallRule(printWriter, "background", this.mUidFirewallBackgroundRules);
            }
            printWriter.print("Firewall enabled: ");
            printWriter.println(this.mFirewallEnabled);
            printWriter.print("Netd service status: ");
            if (this.mNetdService == null) {
                printWriter.println("disconnected");
                return;
            }
            try {
                printWriter.println(this.mNetdService.isAlive() ? "alive" : "dead");
            } catch (android.os.RemoteException e) {
                printWriter.println(android.net.INetd.NEXTHOP_UNREACHABLE);
            }
        }
    }

    private void dumpUidRuleOnQuotaLocked(java.io.PrintWriter printWriter, java.lang.String str, android.util.SparseBooleanArray sparseBooleanArray) {
        printWriter.print("UID bandwith control ");
        printWriter.print(str);
        printWriter.print(": [");
        int size = sparseBooleanArray.size();
        for (int i = 0; i < size; i++) {
            printWriter.print(sparseBooleanArray.keyAt(i));
            if (i < size - 1) {
                printWriter.print(",");
            }
        }
        printWriter.println("]");
    }

    private void dumpUidFirewallRule(java.io.PrintWriter printWriter, java.lang.String str, android.util.SparseIntArray sparseIntArray) {
        printWriter.print("UID firewall ");
        printWriter.print(str);
        printWriter.print(" rule: [");
        int size = sparseIntArray.size();
        for (int i = 0; i < size; i++) {
            printWriter.print(sparseIntArray.keyAt(i));
            printWriter.print(":");
            printWriter.print(sparseIntArray.valueAt(i));
            if (i < size - 1) {
                printWriter.print(",");
            }
        }
        printWriter.println("]");
    }

    public void allowProtect(int i) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.networkSetProtectAllow(i);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public void denyProtect(int i) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermission(this.mContext);
        try {
            this.mNetdService.networkSetProtectDeny(i);
        } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    @android.annotation.EnforcePermission("android.permission.OBSERVE_NETWORK_POLICY")
    public boolean isNetworkRestricted(int i) {
        super.isNetworkRestricted_enforcePermission();
        return isNetworkRestrictedInternal(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isNetworkRestrictedInternal(int i) {
        synchronized (this.mRulesLock) {
            try {
                if (getFirewallChainState(2) && this.mUidFirewallStandbyRules.get(i) == 2) {
                    if (DBG) {
                        android.util.Slog.d(TAG, "Uid " + i + " restricted because of app standby mode");
                    }
                    return true;
                }
                if (getFirewallChainState(1) && this.mUidFirewallDozableRules.get(i) != 1) {
                    if (DBG) {
                        android.util.Slog.d(TAG, "Uid " + i + " restricted because of device idle mode");
                    }
                    return true;
                }
                if (getFirewallChainState(3) && this.mUidFirewallPowerSaveRules.get(i) != 1) {
                    if (DBG) {
                        android.util.Slog.d(TAG, "Uid " + i + " restricted because of power saver mode");
                    }
                    return true;
                }
                if (getFirewallChainState(4) && this.mUidFirewallRestrictedRules.get(i) != 1) {
                    if (DBG) {
                        android.util.Slog.d(TAG, "Uid " + i + " restricted because of restricted mode");
                    }
                    return true;
                }
                if (getFirewallChainState(5) && this.mUidFirewallLowPowerStandbyRules.get(i) != 1) {
                    if (DBG) {
                        android.util.Slog.d(TAG, "Uid " + i + " restricted because of low power standby");
                    }
                    return true;
                }
                if (getFirewallChainState(6) && this.mUidFirewallBackgroundRules.get(i) != 1) {
                    if (DBG) {
                        android.util.Slog.d(TAG, "Uid " + i + " restricted because it is in background");
                    }
                    return true;
                }
                if (this.mUidRejectOnMetered.get(i)) {
                    if (DBG) {
                        android.util.Slog.d(TAG, "Uid " + i + " restricted because of no metered data in the background");
                    }
                    return true;
                }
                if (!this.mDataSaverMode || this.mUidAllowOnMetered.get(i)) {
                    return false;
                }
                if (DBG) {
                    android.util.Slog.d(TAG, "Uid " + i + " restricted because of data saver mode");
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void setFirewallChainState(int i, boolean z) {
        synchronized (this.mRulesLock) {
            this.mFirewallChainStates.put(i, z);
        }
    }

    private boolean getFirewallChainState(int i) {
        boolean z;
        synchronized (this.mRulesLock) {
            z = this.mFirewallChainStates.get(i);
        }
        return z;
    }

    private class LocalService extends com.android.server.net.NetworkManagementInternal {
        private LocalService() {
        }

        @Override // com.android.server.net.NetworkManagementInternal
        public boolean isNetworkRestrictedForUid(int i) {
            return com.android.server.net.NetworkManagementService.this.isNetworkRestrictedInternal(i);
        }
    }
}
