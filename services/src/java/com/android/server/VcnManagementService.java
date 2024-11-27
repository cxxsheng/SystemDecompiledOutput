package com.android.server;

/* loaded from: classes.dex */
public class VcnManagementService extends android.net.vcn.IVcnManagementService.Stub {

    @android.annotation.NonNull
    private static final java.lang.String CONTEXT_ATTRIBUTION_TAG = "VCN";
    private static final int LOCAL_LOG_LINE_COUNT = 512;
    public static final boolean VDBG = false;

    @android.annotation.NonNull
    private final com.android.server.vcn.util.PersistableBundleUtils.LockingReadWriteHelper mConfigDiskRwHelper;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.VcnManagementService.Dependencies mDeps;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final android.os.Looper mLooper;

    @android.annotation.NonNull
    private final com.android.server.vcn.VcnNetworkProvider mNetworkProvider;

    @android.annotation.NonNull
    private final com.android.server.vcn.TelephonySubscriptionTracker mTelephonySubscriptionTracker;

    @android.annotation.NonNull
    private final com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionTrackerCallback mTelephonySubscriptionTrackerCb;

    @android.annotation.NonNull
    private final android.content.BroadcastReceiver mVcnBroadcastReceiver;

    @android.annotation.NonNull
    private static final java.lang.String TAG = com.android.server.VcnManagementService.class.getSimpleName();
    private static final long DUMP_TIMEOUT_MILLIS = java.util.concurrent.TimeUnit.SECONDS.toMillis(5);
    private static final java.util.Set<java.lang.Integer> RESTRICTED_TRANSPORTS_DEFAULT = java.util.Collections.singleton(1);

    @android.annotation.NonNull
    public static final android.util.LocalLog LOCAL_LOG = new android.util.LocalLog(512);

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final java.lang.String VCN_CONFIG_FILE = new java.io.File(android.os.Environment.getDataSystemDirectory(), "vcn/configs.xml").getPath();

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final long CARRIER_PRIVILEGES_LOST_TEARDOWN_DELAY_MS = java.util.concurrent.TimeUnit.SECONDS.toMillis(30);

    @android.annotation.NonNull
    private final com.android.server.VcnManagementService.TrackingNetworkCallback mTrackingNetworkCallback = new com.android.server.VcnManagementService.TrackingNetworkCallback();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<android.os.ParcelUuid, android.net.vcn.VcnConfig> mConfigs = new android.util.ArrayMap();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<android.os.ParcelUuid, com.android.server.vcn.Vcn> mVcns = new android.util.ArrayMap();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot mLastSnapshot = com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot.EMPTY_SNAPSHOT;

    @android.annotation.NonNull
    private final java.lang.Object mLock = new java.lang.Object();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<android.os.IBinder, com.android.server.VcnManagementService.PolicyListenerBinderDeath> mRegisteredPolicyListeners = new android.util.ArrayMap();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<android.os.IBinder, com.android.server.VcnManagementService.VcnStatusCallbackInfo> mRegisteredStatusCallbacks = new android.util.ArrayMap();

    public interface VcnCallback {
        void onGatewayConnectionError(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.lang.String str3);

        void onSafeModeStatusChanged(boolean z);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    VcnManagementService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.VcnManagementService.Dependencies dependencies) {
        java.util.Objects.requireNonNull(context, "Missing context");
        this.mContext = context.createAttributionContext(CONTEXT_ATTRIBUTION_TAG);
        java.util.Objects.requireNonNull(dependencies, "Missing dependencies");
        this.mDeps = dependencies;
        this.mLooper = this.mDeps.getLooper();
        this.mHandler = new android.os.Handler(this.mLooper);
        this.mNetworkProvider = new com.android.server.vcn.VcnNetworkProvider(this.mContext, this.mLooper);
        this.mTelephonySubscriptionTrackerCb = new com.android.server.VcnManagementService.VcnSubscriptionTrackerCallback();
        this.mTelephonySubscriptionTracker = this.mDeps.newTelephonySubscriptionTracker(this.mContext, this.mLooper, this.mTelephonySubscriptionTrackerCb);
        this.mConfigDiskRwHelper = this.mDeps.newPersistableBundleLockingReadWriteHelper(VCN_CONFIG_FILE);
        this.mVcnBroadcastReceiver = new com.android.server.VcnManagementService.VcnBroadcastReceiver();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiver(this.mVcnBroadcastReceiver, intentFilter, null, this.mHandler);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.VcnManagementService.this.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        android.os.PersistableBundle readFromDisk;
        try {
            readFromDisk = this.mConfigDiskRwHelper.readFromDisk();
        } catch (java.io.IOException e) {
            logErr("Failed to read configs from disk; retrying", e);
            try {
                readFromDisk = this.mConfigDiskRwHelper.readFromDisk();
            } catch (java.io.IOException e2) {
                logWtf("Failed to read configs from disk", e2);
                return;
            }
        }
        if (readFromDisk != null) {
            java.util.LinkedHashMap map = com.android.server.vcn.util.PersistableBundleUtils.toMap(readFromDisk, new com.android.server.vcn.util.PersistableBundleUtils.Deserializer() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda9
                @Override // com.android.server.vcn.util.PersistableBundleUtils.Deserializer
                public final java.lang.Object fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
                    return com.android.server.vcn.util.PersistableBundleUtils.toParcelUuid(persistableBundle);
                }
            }, new com.android.server.vcn.util.PersistableBundleUtils.Deserializer() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda10
                @Override // com.android.server.vcn.util.PersistableBundleUtils.Deserializer
                public final java.lang.Object fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
                    return new android.net.vcn.VcnConfig(persistableBundle);
                }
            });
            synchronized (this.mLock) {
                try {
                    for (java.util.Map.Entry entry : map.entrySet()) {
                        if (!this.mConfigs.containsKey(entry.getKey())) {
                            this.mConfigs.put((android.os.ParcelUuid) entry.getKey(), (android.net.vcn.VcnConfig) entry.getValue());
                        }
                    }
                    this.mTelephonySubscriptionTrackerCb.onNewSnapshot(this.mLastSnapshot);
                } finally {
                }
            }
        }
    }

    static com.android.server.VcnManagementService create(@android.annotation.NonNull android.content.Context context) {
        return new com.android.server.VcnManagementService(context, new com.android.server.VcnManagementService.Dependencies());
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class Dependencies {
        private android.os.HandlerThread mHandlerThread;

        public android.os.Looper getLooper() {
            if (this.mHandlerThread == null) {
                synchronized (this) {
                    try {
                        if (this.mHandlerThread == null) {
                            this.mHandlerThread = new android.os.HandlerThread(com.android.server.VcnManagementService.TAG);
                            this.mHandlerThread.start();
                        }
                    } finally {
                    }
                }
            }
            return this.mHandlerThread.getLooper();
        }

        public com.android.server.vcn.TelephonySubscriptionTracker newTelephonySubscriptionTracker(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Looper looper, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionTrackerCallback telephonySubscriptionTrackerCallback) {
            return new com.android.server.vcn.TelephonySubscriptionTracker(context, new android.os.Handler(looper), telephonySubscriptionTrackerCallback);
        }

        public int getBinderCallingUid() {
            return android.os.Binder.getCallingUid();
        }

        public com.android.server.vcn.util.PersistableBundleUtils.LockingReadWriteHelper newPersistableBundleLockingReadWriteHelper(@android.annotation.NonNull java.lang.String str) {
            return new com.android.server.vcn.util.PersistableBundleUtils.LockingReadWriteHelper(str);
        }

        public com.android.server.vcn.VcnContext newVcnContext(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Looper looper, @android.annotation.NonNull com.android.server.vcn.VcnNetworkProvider vcnNetworkProvider, boolean z) {
            return new com.android.server.vcn.VcnContext(context, looper, vcnNetworkProvider, z);
        }

        public com.android.server.vcn.Vcn newVcn(@android.annotation.NonNull com.android.server.vcn.VcnContext vcnContext, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull android.net.vcn.VcnConfig vcnConfig, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, @android.annotation.NonNull com.android.server.VcnManagementService.VcnCallback vcnCallback) {
            return new com.android.server.vcn.Vcn(vcnContext, parcelUuid, vcnConfig, telephonySubscriptionSnapshot, vcnCallback);
        }

        public int getSubIdForWifiInfo(@android.annotation.NonNull android.net.wifi.WifiInfo wifiInfo) {
            return wifiInfo.getSubscriptionId();
        }

        public com.android.net.module.util.LocationPermissionChecker newLocationPermissionChecker(@android.annotation.NonNull android.content.Context context) {
            return new com.android.net.module.util.LocationPermissionChecker(context);
        }

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
        public java.util.Set<java.lang.Integer> getRestrictedTransportsFromCarrierConfig(android.os.ParcelUuid parcelUuid, com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
            if (!android.os.Build.IS_ENG && !android.os.Build.IS_USERDEBUG) {
                return com.android.server.VcnManagementService.RESTRICTED_TRANSPORTS_DEFAULT;
            }
            com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper carrierConfigForSubGrp = telephonySubscriptionSnapshot.getCarrierConfigForSubGrp(parcelUuid);
            if (carrierConfigForSubGrp == null) {
                return com.android.server.VcnManagementService.RESTRICTED_TRANSPORTS_DEFAULT;
            }
            int[] intArray = carrierConfigForSubGrp.getIntArray("vcn_restricted_transports", com.android.server.VcnManagementService.RESTRICTED_TRANSPORTS_DEFAULT.stream().mapToInt(new java.util.function.ToIntFunction() { // from class: com.android.server.VcnManagementService$Dependencies$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(java.lang.Object obj) {
                    int intValue;
                    intValue = ((java.lang.Integer) obj).intValue();
                    return intValue;
                }
            }).toArray());
            android.util.ArraySet arraySet = new android.util.ArraySet();
            for (int i : intArray) {
                arraySet.add(java.lang.Integer.valueOf(i));
            }
            return arraySet;
        }

        public java.util.Set<java.lang.Integer> getRestrictedTransports(android.os.ParcelUuid parcelUuid, com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot, android.net.vcn.VcnConfig vcnConfig) {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            arraySet.addAll(vcnConfig.getRestrictedUnderlyingNetworkTransports());
            arraySet.addAll(getRestrictedTransportsFromCarrierConfig(parcelUuid, telephonySubscriptionSnapshot));
            return arraySet;
        }
    }

    public void systemReady() {
        this.mNetworkProvider.register();
        ((android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class)).registerNetworkCallback(new android.net.NetworkRequest.Builder().clearCapabilities().build(), this.mTrackingNetworkCallback);
        this.mTelephonySubscriptionTracker.register();
    }

    private void enforcePrimaryUser() {
        int binderCallingUid = this.mDeps.getBinderCallingUid();
        if (binderCallingUid == 1000) {
            throw new java.lang.IllegalStateException("Calling identity was System Server. Was Binder calling identity cleared?");
        }
        if (!android.os.UserHandle.getUserHandleForUid(binderCallingUid).isSystem()) {
            throw new java.lang.SecurityException("VcnManagementService can only be used by callers running as the primary user");
        }
    }

    private void enforceCallingUserAndCarrierPrivilege(final android.os.ParcelUuid parcelUuid, java.lang.String str) {
        enforcePrimaryUser();
        final android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) this.mContext.getSystemService(android.telephony.SubscriptionManager.class);
        final java.util.ArrayList<android.telephony.SubscriptionInfo> arrayList = new java.util.ArrayList();
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda8
            public final void runOrThrow() {
                com.android.server.VcnManagementService.this.lambda$enforceCallingUserAndCarrierPrivilege$1(subscriptionManager, parcelUuid, arrayList);
            }
        });
        for (android.telephony.SubscriptionInfo subscriptionInfo : arrayList) {
            android.telephony.TelephonyManager createForSubscriptionId = ((android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class)).createForSubscriptionId(subscriptionInfo.getSubscriptionId());
            if (android.telephony.SubscriptionManager.isValidSlotIndex(subscriptionInfo.getSimSlotIndex()) && createForSubscriptionId.checkCarrierPrivilegesForPackage(str) == 1) {
                return;
            }
        }
        throw new java.lang.SecurityException("Carrier privilege required for subscription group to set VCN Config");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enforceCallingUserAndCarrierPrivilege$1(android.telephony.SubscriptionManager subscriptionManager, android.os.ParcelUuid parcelUuid, java.util.List list) throws java.lang.Exception {
        java.util.List<android.telephony.SubscriptionInfo> subscriptionsInGroup = subscriptionManager.getSubscriptionsInGroup(parcelUuid);
        if (subscriptionsInGroup == null) {
            logWtf("Received null from getSubscriptionsInGroup");
            subscriptionsInGroup = java.util.Collections.emptyList();
        }
        list.addAll(subscriptionsInGroup);
    }

    private void enforceManageTestNetworksForTestMode(@android.annotation.NonNull android.net.vcn.VcnConfig vcnConfig) {
        if (vcnConfig.isTestModeProfile()) {
            this.mContext.enforceCallingPermission("android.permission.MANAGE_TEST_NETWORKS", "Test-mode require the MANAGE_TEST_NETWORKS permission");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isActiveSubGroup(@android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        if (parcelUuid == null || telephonySubscriptionSnapshot == null) {
            return false;
        }
        return java.util.Objects.equals(parcelUuid, telephonySubscriptionSnapshot.getActiveDataSubscriptionGroup());
    }

    private class VcnBroadcastReceiver extends android.content.BroadcastReceiver {
        private VcnBroadcastReceiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            char c;
            java.lang.String action = intent.getAction();
            switch (action.hashCode()) {
                case -810471698:
                    if (action.equals("android.intent.action.PACKAGE_REPLACED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 267468725:
                    if (action.equals("android.intent.action.PACKAGE_DATA_CLEARED")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 525384130:
                    if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1544582882:
                    if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1580442797:
                    if (action.equals("android.intent.action.PACKAGE_FULLY_REMOVED")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                    com.android.server.VcnManagementService.this.mTelephonySubscriptionTracker.handleSubscriptionsChanged();
                    return;
                case 3:
                case 4:
                    java.lang.String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    if (schemeSpecificPart == null || schemeSpecificPart.isEmpty()) {
                        com.android.server.VcnManagementService.this.logWtf("Package name was empty or null for intent with action" + action);
                        return;
                    }
                    synchronized (com.android.server.VcnManagementService.this.mLock) {
                        try {
                            java.util.ArrayList arrayList = new java.util.ArrayList();
                            for (java.util.Map.Entry entry : com.android.server.VcnManagementService.this.mConfigs.entrySet()) {
                                if (schemeSpecificPart.equals(((android.net.vcn.VcnConfig) entry.getValue()).getProvisioningPackageName())) {
                                    arrayList.add((android.os.ParcelUuid) entry.getKey());
                                }
                            }
                            java.util.Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                com.android.server.VcnManagementService.this.stopAndClearVcnConfigInternalLocked((android.os.ParcelUuid) it.next());
                            }
                            if (!arrayList.isEmpty()) {
                                com.android.server.VcnManagementService.this.writeConfigsToDiskLocked();
                            }
                        } finally {
                        }
                    }
                    return;
                default:
                    android.util.Slog.wtf(com.android.server.VcnManagementService.TAG, "received unexpected intent: " + action);
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class VcnSubscriptionTrackerCallback implements com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionTrackerCallback {
        private VcnSubscriptionTrackerCallback() {
        }

        @Override // com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionTrackerCallback
        public void onNewSnapshot(@android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
            long j;
            synchronized (com.android.server.VcnManagementService.this.mLock) {
                try {
                    com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot2 = com.android.server.VcnManagementService.this.mLastSnapshot;
                    com.android.server.VcnManagementService.this.mLastSnapshot = telephonySubscriptionSnapshot;
                    com.android.server.VcnManagementService.this.logInfo("new snapshot: " + com.android.server.VcnManagementService.this.mLastSnapshot);
                    for (java.util.Map.Entry entry : com.android.server.VcnManagementService.this.mConfigs.entrySet()) {
                        android.os.ParcelUuid parcelUuid = (android.os.ParcelUuid) entry.getKey();
                        if (telephonySubscriptionSnapshot.packageHasPermissionsForSubscriptionGroup(parcelUuid, ((android.net.vcn.VcnConfig) entry.getValue()).getProvisioningPackageName()) && com.android.server.VcnManagementService.this.isActiveSubGroup(parcelUuid, telephonySubscriptionSnapshot)) {
                            if (!com.android.server.VcnManagementService.this.mVcns.containsKey(parcelUuid)) {
                                com.android.server.VcnManagementService.this.startVcnLocked(parcelUuid, (android.net.vcn.VcnConfig) entry.getValue());
                            }
                            com.android.server.VcnManagementService.this.mHandler.removeCallbacksAndMessages(com.android.server.VcnManagementService.this.mVcns.get(parcelUuid));
                        }
                    }
                    boolean z = false;
                    for (java.util.Map.Entry entry2 : com.android.server.VcnManagementService.this.mVcns.entrySet()) {
                        final android.os.ParcelUuid parcelUuid2 = (android.os.ParcelUuid) entry2.getKey();
                        android.net.vcn.VcnConfig vcnConfig = (android.net.vcn.VcnConfig) com.android.server.VcnManagementService.this.mConfigs.get(parcelUuid2);
                        boolean isActiveSubGroup = com.android.server.VcnManagementService.this.isActiveSubGroup(parcelUuid2, telephonySubscriptionSnapshot);
                        boolean z2 = android.telephony.SubscriptionManager.isValidSubscriptionId(telephonySubscriptionSnapshot.getActiveDataSubscriptionId()) && !com.android.server.VcnManagementService.this.isActiveSubGroup(parcelUuid2, telephonySubscriptionSnapshot);
                        if (vcnConfig != null) {
                            if (telephonySubscriptionSnapshot.packageHasPermissionsForSubscriptionGroup(parcelUuid2, vcnConfig.getProvisioningPackageName()) && isActiveSubGroup) {
                                ((com.android.server.vcn.Vcn) entry2.getValue()).updateSubscriptionSnapshot(com.android.server.VcnManagementService.this.mLastSnapshot);
                                z |= !java.util.Objects.equals(telephonySubscriptionSnapshot2.getCarrierConfigForSubGrp(parcelUuid2), com.android.server.VcnManagementService.this.mLastSnapshot.getCarrierConfigForSubGrp(parcelUuid2));
                            }
                        }
                        final com.android.server.vcn.Vcn vcn = (com.android.server.vcn.Vcn) entry2.getValue();
                        if (z2) {
                            j = 0;
                        } else {
                            j = com.android.server.VcnManagementService.CARRIER_PRIVILEGES_LOST_TEARDOWN_DELAY_MS;
                        }
                        com.android.server.VcnManagementService.this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.VcnManagementService$VcnSubscriptionTrackerCallback$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.VcnManagementService.VcnSubscriptionTrackerCallback.this.lambda$onNewSnapshot$0(parcelUuid2, vcn);
                            }
                        }, vcn, j);
                    }
                    if (!com.android.server.VcnManagementService.this.getSubGroupToSubIdMappings(com.android.server.VcnManagementService.this.mLastSnapshot).equals(com.android.server.VcnManagementService.this.getSubGroupToSubIdMappings(telephonySubscriptionSnapshot2))) {
                        com.android.server.VcnManagementService.this.garbageCollectAndWriteVcnConfigsLocked();
                        z = true;
                    }
                    if (z) {
                        com.android.server.VcnManagementService.this.notifyAllPolicyListenersLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNewSnapshot$0(android.os.ParcelUuid parcelUuid, com.android.server.vcn.Vcn vcn) {
            synchronized (com.android.server.VcnManagementService.this.mLock) {
                try {
                    if (com.android.server.VcnManagementService.this.mVcns.get(parcelUuid) == vcn) {
                        com.android.server.VcnManagementService.this.stopVcnLocked(parcelUuid);
                        com.android.server.VcnManagementService.this.notifyAllPermissionedStatusCallbacksLocked(parcelUuid, 1);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public java.util.Map<android.os.ParcelUuid, java.util.Set<java.lang.Integer>> getSubGroupToSubIdMappings(@android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (android.os.ParcelUuid parcelUuid : this.mVcns.keySet()) {
            arrayMap.put(parcelUuid, telephonySubscriptionSnapshot.getAllSubIdsInGroup(parcelUuid));
        }
        return arrayMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void stopVcnLocked(@android.annotation.NonNull android.os.ParcelUuid parcelUuid) {
        logInfo("Stopping VCN config for subGrp: " + parcelUuid);
        com.android.server.vcn.Vcn vcn = this.mVcns.get(parcelUuid);
        if (vcn == null) {
            return;
        }
        vcn.teardownAsynchronously();
        this.mVcns.remove(parcelUuid);
        notifyAllPolicyListenersLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void notifyAllPolicyListenersLocked() {
        for (final com.android.server.VcnManagementService.PolicyListenerBinderDeath policyListenerBinderDeath : this.mRegisteredPolicyListeners.values()) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda0
                public final void runOrThrow() {
                    com.android.server.VcnManagementService.this.lambda$notifyAllPolicyListenersLocked$2(policyListenerBinderDeath);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyAllPolicyListenersLocked$2(com.android.server.VcnManagementService.PolicyListenerBinderDeath policyListenerBinderDeath) throws java.lang.Exception {
        try {
            policyListenerBinderDeath.mListener.onPolicyChanged();
        } catch (android.os.RemoteException e) {
            logDbg("VcnStatusCallback threw on VCN status change", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void notifyAllPermissionedStatusCallbacksLocked(@android.annotation.NonNull android.os.ParcelUuid parcelUuid, final int i) {
        for (final com.android.server.VcnManagementService.VcnStatusCallbackInfo vcnStatusCallbackInfo : this.mRegisteredStatusCallbacks.values()) {
            if (isCallbackPermissioned(vcnStatusCallbackInfo, parcelUuid)) {
                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda12
                    public final void runOrThrow() {
                        com.android.server.VcnManagementService.this.lambda$notifyAllPermissionedStatusCallbacksLocked$3(vcnStatusCallbackInfo, i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyAllPermissionedStatusCallbacksLocked$3(com.android.server.VcnManagementService.VcnStatusCallbackInfo vcnStatusCallbackInfo, int i) throws java.lang.Exception {
        try {
            vcnStatusCallbackInfo.mCallback.onVcnStatusChanged(i);
        } catch (android.os.RemoteException e) {
            logDbg("VcnStatusCallback threw on VCN status change", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void startVcnLocked(@android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull android.net.vcn.VcnConfig vcnConfig) {
        logInfo("Starting VCN config for subGrp: " + parcelUuid);
        if (!this.mVcns.isEmpty()) {
            java.util.Iterator<android.os.ParcelUuid> it = this.mVcns.keySet().iterator();
            while (it.hasNext()) {
                stopVcnLocked(it.next());
            }
        }
        this.mVcns.put(parcelUuid, this.mDeps.newVcn(this.mDeps.newVcnContext(this.mContext, this.mLooper, this.mNetworkProvider, vcnConfig.isTestModeProfile()), parcelUuid, vcnConfig, this.mLastSnapshot, new com.android.server.VcnManagementService.VcnCallbackImpl(parcelUuid)));
        notifyAllPolicyListenersLocked();
        notifyAllPermissionedStatusCallbacksLocked(parcelUuid, 2);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void startOrUpdateVcnLocked(@android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull android.net.vcn.VcnConfig vcnConfig) {
        logDbg("Starting or updating VCN config for subGrp: " + parcelUuid);
        if (this.mVcns.containsKey(parcelUuid)) {
            this.mVcns.get(parcelUuid).updateConfig(vcnConfig);
            notifyAllPolicyListenersLocked();
        } else if (isActiveSubGroup(parcelUuid, this.mLastSnapshot)) {
            startVcnLocked(parcelUuid, vcnConfig);
        }
    }

    public void setVcnConfig(@android.annotation.NonNull final android.os.ParcelUuid parcelUuid, @android.annotation.NonNull final android.net.vcn.VcnConfig vcnConfig, @android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(parcelUuid, "subscriptionGroup was null");
        java.util.Objects.requireNonNull(vcnConfig, "config was null");
        java.util.Objects.requireNonNull(str, "opPkgName was null");
        if (!vcnConfig.getProvisioningPackageName().equals(str)) {
            throw new java.lang.IllegalArgumentException("Mismatched caller and VcnConfig creator");
        }
        logInfo("VCN config updated for subGrp: " + parcelUuid);
        ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).checkPackage(this.mDeps.getBinderCallingUid(), vcnConfig.getProvisioningPackageName());
        enforceManageTestNetworksForTestMode(vcnConfig);
        enforceCallingUserAndCarrierPrivilege(parcelUuid, str);
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda7
            public final void runOrThrow() {
                com.android.server.VcnManagementService.this.lambda$setVcnConfig$4(parcelUuid, vcnConfig);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setVcnConfig$4(android.os.ParcelUuid parcelUuid, android.net.vcn.VcnConfig vcnConfig) throws java.lang.Exception {
        synchronized (this.mLock) {
            this.mConfigs.put(parcelUuid, vcnConfig);
            startOrUpdateVcnLocked(parcelUuid, vcnConfig);
            writeConfigsToDiskLocked();
        }
    }

    private void enforceCarrierPrivilegeOrProvisioningPackage(@android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull java.lang.String str) {
        enforcePrimaryUser();
        if (isProvisioningPackageForConfig(parcelUuid, str)) {
            return;
        }
        enforceCallingUserAndCarrierPrivilege(parcelUuid, str);
    }

    private boolean isProvisioningPackageForConfig(@android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                android.net.vcn.VcnConfig vcnConfig = this.mConfigs.get(parcelUuid);
                if (vcnConfig == null || !str.equals(vcnConfig.getProvisioningPackageName())) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void clearVcnConfig(@android.annotation.NonNull final android.os.ParcelUuid parcelUuid, @android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(parcelUuid, "subscriptionGroup was null");
        java.util.Objects.requireNonNull(str, "opPkgName was null");
        logInfo("VCN config cleared for subGrp: " + parcelUuid);
        ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).checkPackage(this.mDeps.getBinderCallingUid(), str);
        enforceCarrierPrivilegeOrProvisioningPackage(parcelUuid, str);
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda4
            public final void runOrThrow() {
                com.android.server.VcnManagementService.this.lambda$clearVcnConfig$5(parcelUuid);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearVcnConfig$5(android.os.ParcelUuid parcelUuid) throws java.lang.Exception {
        synchronized (this.mLock) {
            stopAndClearVcnConfigInternalLocked(parcelUuid);
            writeConfigsToDiskLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopAndClearVcnConfigInternalLocked(@android.annotation.NonNull android.os.ParcelUuid parcelUuid) {
        this.mConfigs.remove(parcelUuid);
        boolean containsKey = this.mVcns.containsKey(parcelUuid);
        stopVcnLocked(parcelUuid);
        if (containsKey) {
            notifyAllPermissionedStatusCallbacksLocked(parcelUuid, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void garbageCollectAndWriteVcnConfigsLocked() {
        android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) this.mContext.getSystemService(android.telephony.SubscriptionManager.class);
        java.util.Iterator<android.os.ParcelUuid> it = this.mConfigs.keySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            java.util.List<android.telephony.SubscriptionInfo> subscriptionsInGroup = subscriptionManager.getSubscriptionsInGroup(it.next());
            if (subscriptionsInGroup == null || subscriptionsInGroup.isEmpty()) {
                it.remove();
                z = true;
            }
        }
        if (z) {
            writeConfigsToDiskLocked();
        }
    }

    @android.annotation.NonNull
    public java.util.List<android.os.ParcelUuid> getConfiguredSubscriptionGroups(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str, "opPkgName was null");
        ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).checkPackage(this.mDeps.getBinderCallingUid(), str);
        enforcePrimaryUser();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                for (android.os.ParcelUuid parcelUuid : this.mConfigs.keySet()) {
                    if (!this.mLastSnapshot.packageHasPermissionsForSubscriptionGroup(parcelUuid, str) && !isProvisioningPackageForConfig(parcelUuid, str)) {
                    }
                    arrayList.add(parcelUuid);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void writeConfigsToDiskLocked() {
        try {
            this.mConfigDiskRwHelper.writeToDisk(com.android.server.vcn.util.PersistableBundleUtils.fromMap(this.mConfigs, new com.android.server.vcn.util.PersistableBundleUtils.Serializer() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda1
                @Override // com.android.server.vcn.util.PersistableBundleUtils.Serializer
                public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
                    return com.android.server.vcn.util.PersistableBundleUtils.fromParcelUuid((android.os.ParcelUuid) obj);
                }
            }, new com.android.server.vcn.util.PersistableBundleUtils.Serializer() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda2
                @Override // com.android.server.vcn.util.PersistableBundleUtils.Serializer
                public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
                    return ((android.net.vcn.VcnConfig) obj).toPersistableBundle();
                }
            }));
        } catch (java.io.IOException e) {
            logErr("Failed to save configs to disk", e);
            throw new android.os.ServiceSpecificException(0, "Failed to save configs");
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    java.util.Map<android.os.ParcelUuid, android.net.vcn.VcnConfig> getConfigs() {
        java.util.Map<android.os.ParcelUuid, android.net.vcn.VcnConfig> unmodifiableMap;
        synchronized (this.mLock) {
            unmodifiableMap = java.util.Collections.unmodifiableMap(this.mConfigs);
        }
        return unmodifiableMap;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public java.util.Map<android.os.ParcelUuid, com.android.server.vcn.Vcn> getAllVcns() {
        java.util.Map<android.os.ParcelUuid, com.android.server.vcn.Vcn> unmodifiableMap;
        synchronized (this.mLock) {
            unmodifiableMap = java.util.Collections.unmodifiableMap(this.mVcns);
        }
        return unmodifiableMap;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public java.util.Map<android.os.IBinder, com.android.server.VcnManagementService.VcnStatusCallbackInfo> getAllStatusCallbacks() {
        java.util.Map<android.os.IBinder, com.android.server.VcnManagementService.VcnStatusCallbackInfo> unmodifiableMap;
        synchronized (this.mLock) {
            unmodifiableMap = java.util.Collections.unmodifiableMap(this.mRegisteredStatusCallbacks);
        }
        return unmodifiableMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class PolicyListenerBinderDeath implements android.os.IBinder.DeathRecipient {

        @android.annotation.NonNull
        private final android.net.vcn.IVcnUnderlyingNetworkPolicyListener mListener;

        PolicyListenerBinderDeath(@android.annotation.NonNull android.net.vcn.IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) {
            this.mListener = iVcnUnderlyingNetworkPolicyListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Log.e(com.android.server.VcnManagementService.TAG, "app died without removing VcnUnderlyingNetworkPolicyListener");
            com.android.server.VcnManagementService.this.removeVcnUnderlyingNetworkPolicyListener(this.mListener);
        }
    }

    public void addVcnUnderlyingNetworkPolicyListener(@android.annotation.NonNull final android.net.vcn.IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) {
        java.util.Objects.requireNonNull(iVcnUnderlyingNetworkPolicyListener, "listener was null");
        com.android.net.module.util.PermissionUtils.enforceAnyPermissionOf(this.mContext, new java.lang.String[]{"android.permission.NETWORK_FACTORY", "android.permission.MANAGE_TEST_NETWORKS"});
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda11
            public final void runOrThrow() {
                com.android.server.VcnManagementService.this.lambda$addVcnUnderlyingNetworkPolicyListener$6(iVcnUnderlyingNetworkPolicyListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addVcnUnderlyingNetworkPolicyListener$6(android.net.vcn.IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) throws java.lang.Exception {
        com.android.server.VcnManagementService.PolicyListenerBinderDeath policyListenerBinderDeath = new com.android.server.VcnManagementService.PolicyListenerBinderDeath(iVcnUnderlyingNetworkPolicyListener);
        synchronized (this.mLock) {
            this.mRegisteredPolicyListeners.put(iVcnUnderlyingNetworkPolicyListener.asBinder(), policyListenerBinderDeath);
            try {
                iVcnUnderlyingNetworkPolicyListener.asBinder().linkToDeath(policyListenerBinderDeath, 0);
            } catch (android.os.RemoteException e) {
                policyListenerBinderDeath.binderDied();
            }
        }
    }

    public void removeVcnUnderlyingNetworkPolicyListener(@android.annotation.NonNull final android.net.vcn.IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) {
        java.util.Objects.requireNonNull(iVcnUnderlyingNetworkPolicyListener, "listener was null");
        com.android.net.module.util.PermissionUtils.enforceAnyPermissionOf(this.mContext, new java.lang.String[]{"android.permission.NETWORK_FACTORY", "android.permission.MANAGE_TEST_NETWORKS"});
        android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda3
            public final void runOrThrow() {
                com.android.server.VcnManagementService.this.lambda$removeVcnUnderlyingNetworkPolicyListener$7(iVcnUnderlyingNetworkPolicyListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeVcnUnderlyingNetworkPolicyListener$7(android.net.vcn.IVcnUnderlyingNetworkPolicyListener iVcnUnderlyingNetworkPolicyListener) throws java.lang.Exception {
        synchronized (this.mLock) {
            try {
                com.android.server.VcnManagementService.PolicyListenerBinderDeath remove = this.mRegisteredPolicyListeners.remove(iVcnUnderlyingNetworkPolicyListener.asBinder());
                if (remove != null) {
                    iVcnUnderlyingNetworkPolicyListener.asBinder().unlinkToDeath(remove, 0);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.os.ParcelUuid getSubGroupForNetworkCapabilities(@android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities) {
        com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot;
        synchronized (this.mLock) {
            telephonySubscriptionSnapshot = this.mLastSnapshot;
        }
        java.util.Iterator it = networkCapabilities.getSubscriptionIds().iterator();
        android.os.ParcelUuid parcelUuid = null;
        while (it.hasNext()) {
            int intValue = ((java.lang.Integer) it.next()).intValue();
            if (parcelUuid != null && !parcelUuid.equals(telephonySubscriptionSnapshot.getGroupForSubId(intValue))) {
                logWtf("Got multiple subscription groups for a single network");
            }
            parcelUuid = telephonySubscriptionSnapshot.getGroupForSubId(intValue);
        }
        return parcelUuid;
    }

    @android.annotation.NonNull
    public android.net.vcn.VcnUnderlyingNetworkPolicy getUnderlyingNetworkPolicy(@android.annotation.NonNull final android.net.NetworkCapabilities networkCapabilities, @android.annotation.NonNull final android.net.LinkProperties linkProperties) {
        java.util.Objects.requireNonNull(networkCapabilities, "networkCapabilities was null");
        java.util.Objects.requireNonNull(linkProperties, "linkProperties was null");
        com.android.net.module.util.PermissionUtils.enforceAnyPermissionOf(this.mContext, new java.lang.String[]{"android.permission.NETWORK_FACTORY", "android.permission.MANAGE_TEST_NETWORKS"});
        if ((this.mContext.checkCallingOrSelfPermission("android.permission.NETWORK_FACTORY") != 0) && !networkCapabilities.hasTransport(7)) {
            throw new java.lang.IllegalStateException("NetworkCapabilities must be for Test Network if using permission MANAGE_TEST_NETWORKS");
        }
        return (android.net.vcn.VcnUnderlyingNetworkPolicy) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda13
            public final java.lang.Object getOrThrow() {
                android.net.vcn.VcnUnderlyingNetworkPolicy lambda$getUnderlyingNetworkPolicy$8;
                lambda$getUnderlyingNetworkPolicy$8 = com.android.server.VcnManagementService.this.lambda$getUnderlyingNetworkPolicy$8(networkCapabilities, linkProperties);
                return lambda$getUnderlyingNetworkPolicy$8;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.net.vcn.VcnUnderlyingNetworkPolicy lambda$getUnderlyingNetworkPolicy$8(android.net.NetworkCapabilities networkCapabilities, android.net.LinkProperties linkProperties) throws java.lang.Exception {
        boolean z;
        boolean z2;
        boolean z3;
        android.net.NetworkCapabilities networkCapabilities2 = new android.net.NetworkCapabilities(networkCapabilities);
        android.os.ParcelUuid subGroupForNetworkCapabilities = getSubGroupForNetworkCapabilities(networkCapabilities2);
        synchronized (this.mLock) {
            try {
                com.android.server.vcn.Vcn vcn = this.mVcns.get(subGroupForNetworkCapabilities);
                android.net.vcn.VcnConfig vcnConfig = this.mConfigs.get(subGroupForNetworkCapabilities);
                z = false;
                if (vcn == null) {
                    z2 = false;
                } else {
                    if (vcnConfig == null) {
                        logWtf("Vcn instance exists but VcnConfig does not for " + subGroupForNetworkCapabilities);
                    }
                    if (vcn.getStatus() != 2) {
                        z3 = false;
                    } else {
                        z3 = true;
                    }
                    java.util.Iterator<java.lang.Integer> it = this.mDeps.getRestrictedTransports(subGroupForNetworkCapabilities, this.mLastSnapshot, vcnConfig).iterator();
                    z2 = false;
                    while (true) {
                        if (!it.hasNext()) {
                            z = z3;
                            break;
                        }
                        int intValue = it.next().intValue();
                        if (networkCapabilities2.hasTransport(intValue)) {
                            if (intValue == 0 || intValue == 7) {
                                z2 |= vcn.getStatus() == 2;
                            } else {
                                z = z3;
                                z2 = true;
                                break;
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.net.NetworkCapabilities.Builder builder = new android.net.NetworkCapabilities.Builder(networkCapabilities2);
        if (z) {
            builder.removeCapability(28);
        } else {
            builder.addCapability(28);
        }
        if (z2) {
            builder.removeCapability(13);
        }
        android.net.NetworkCapabilities build = builder.build();
        android.net.vcn.VcnUnderlyingNetworkPolicy vcnUnderlyingNetworkPolicy = new android.net.vcn.VcnUnderlyingNetworkPolicy(this.mTrackingNetworkCallback.requiresRestartForImmutableCapabilityChanges(build, linkProperties), build);
        logVdbg("getUnderlyingNetworkPolicy() called for caps: " + networkCapabilities + "; and lp: " + linkProperties + "; result = " + vcnUnderlyingNetworkPolicy);
        return vcnUnderlyingNetworkPolicy;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    class VcnStatusCallbackInfo implements android.os.IBinder.DeathRecipient {

        @android.annotation.NonNull
        final android.net.vcn.IVcnStatusCallback mCallback;

        @android.annotation.NonNull
        final java.lang.String mPkgName;

        @android.annotation.NonNull
        final android.os.ParcelUuid mSubGroup;
        final int mUid;

        private VcnStatusCallbackInfo(@android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull android.net.vcn.IVcnStatusCallback iVcnStatusCallback, @android.annotation.NonNull java.lang.String str, int i) {
            this.mSubGroup = parcelUuid;
            this.mCallback = iVcnStatusCallback;
            this.mPkgName = str;
            this.mUid = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Log.e(com.android.server.VcnManagementService.TAG, "app died without unregistering VcnStatusCallback");
            com.android.server.VcnManagementService.this.unregisterVcnStatusCallback(this.mCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCallbackPermissioned(@android.annotation.NonNull com.android.server.VcnManagementService.VcnStatusCallbackInfo vcnStatusCallbackInfo, @android.annotation.NonNull android.os.ParcelUuid parcelUuid) {
        return parcelUuid.equals(vcnStatusCallbackInfo.mSubGroup) && this.mLastSnapshot.packageHasPermissionsForSubscriptionGroup(parcelUuid, vcnStatusCallbackInfo.mPkgName);
    }

    public void registerVcnStatusCallback(@android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull android.net.vcn.IVcnStatusCallback iVcnStatusCallback, @android.annotation.NonNull java.lang.String str) {
        int binderCallingUid = this.mDeps.getBinderCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.Objects.requireNonNull(parcelUuid, "subGroup must not be null");
            java.util.Objects.requireNonNull(iVcnStatusCallback, "callback must not be null");
            java.util.Objects.requireNonNull(str, "opPkgName must not be null");
            ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).checkPackage(binderCallingUid, str);
            android.os.IBinder asBinder = iVcnStatusCallback.asBinder();
            com.android.server.VcnManagementService.VcnStatusCallbackInfo vcnStatusCallbackInfo = new com.android.server.VcnManagementService.VcnStatusCallbackInfo(parcelUuid, iVcnStatusCallback, str, binderCallingUid);
            int i = 0;
            try {
                asBinder.linkToDeath(vcnStatusCallbackInfo, 0);
                synchronized (this.mLock) {
                    if (this.mRegisteredStatusCallbacks.containsKey(asBinder)) {
                        throw new java.lang.IllegalStateException("Attempting to register a callback that is already in use");
                    }
                    this.mRegisteredStatusCallbacks.put(asBinder, vcnStatusCallbackInfo);
                    android.net.vcn.VcnConfig vcnConfig = this.mConfigs.get(parcelUuid);
                    com.android.server.vcn.Vcn vcn = this.mVcns.get(parcelUuid);
                    int status = vcn == null ? 0 : vcn.getStatus();
                    if (vcnConfig != null && isCallbackPermissioned(vcnStatusCallbackInfo, parcelUuid)) {
                        if (vcn == null) {
                            i = 1;
                        } else if (status == 2 || status == 3) {
                            i = status;
                        } else {
                            logWtf("Unknown VCN status: " + status);
                        }
                    }
                    try {
                        vcnStatusCallbackInfo.mCallback.onVcnStatusChanged(i);
                    } catch (android.os.RemoteException e) {
                        logDbg("VcnStatusCallback threw on VCN status change", e);
                    }
                }
            } catch (android.os.RemoteException e2) {
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void unregisterVcnStatusCallback(@android.annotation.NonNull android.net.vcn.IVcnStatusCallback iVcnStatusCallback) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.Objects.requireNonNull(iVcnStatusCallback, "callback must not be null");
            android.os.IBinder asBinder = iVcnStatusCallback.asBinder();
            synchronized (this.mLock) {
                try {
                    com.android.server.VcnManagementService.VcnStatusCallbackInfo remove = this.mRegisteredStatusCallbacks.remove(asBinder);
                    if (remove != null) {
                        asBinder.unlinkToDeath(remove, 0);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    void setLastSnapshot(@android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        java.util.Objects.requireNonNull(telephonySubscriptionSnapshot);
        this.mLastSnapshot = telephonySubscriptionSnapshot;
    }

    private void logVdbg(java.lang.String str) {
    }

    private void logDbg(java.lang.String str) {
        android.util.Slog.d(TAG, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logDbg(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.d(TAG, str, th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logInfo(java.lang.String str) {
        android.util.Slog.i(TAG, str);
        LOCAL_LOG.log("[INFO] [" + TAG + "] " + str);
    }

    private void logInfo(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.i(TAG, str, th);
        LOCAL_LOG.log("[INFO] [" + TAG + "] " + str + th);
    }

    private void logErr(java.lang.String str) {
        android.util.Slog.e(TAG, str);
        LOCAL_LOG.log("[ERR] [" + TAG + "] " + str);
    }

    private void logErr(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.e(TAG, str, th);
        LOCAL_LOG.log("[ERR ] [" + TAG + "] " + str + th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logWtf(java.lang.String str) {
        android.util.Slog.wtf(TAG, str);
        LOCAL_LOG.log("[WTF] [" + TAG + "] " + str);
    }

    private void logWtf(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.wtf(TAG, str, th);
        LOCAL_LOG.log("[WTF ] [" + TAG + "] " + str + th);
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.DUMP", TAG);
        final com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "| ");
        this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.VcnManagementService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.VcnManagementService.this.lambda$dump$9(indentingPrintWriter);
            }
        }, DUMP_TIMEOUT_MILLIS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$9(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        this.mNetworkProvider.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mTrackingNetworkCallback.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        synchronized (this.mLock) {
            try {
                this.mLastSnapshot.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.println("mConfigs:");
                indentingPrintWriter.increaseIndent();
                for (java.util.Map.Entry<android.os.ParcelUuid, android.net.vcn.VcnConfig> entry : this.mConfigs.entrySet()) {
                    indentingPrintWriter.println(entry.getKey() + ": " + entry.getValue().getProvisioningPackageName());
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("mVcns:");
                indentingPrintWriter.increaseIndent();
                java.util.Iterator<com.android.server.vcn.Vcn> it = this.mVcns.values().iterator();
                while (it.hasNext()) {
                    it.next().dump(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.println("Local log:");
        indentingPrintWriter.increaseIndent();
        LOCAL_LOG.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    private class TrackingNetworkCallback extends android.net.ConnectivityManager.NetworkCallback {
        private final java.util.Map<android.net.Network, android.net.NetworkCapabilities> mCaps;
        private final java.util.Map<android.net.Network, android.net.LinkProperties> mLinkProperties;
        private final java.lang.Object mLockObject;

        private TrackingNetworkCallback() {
            this.mLockObject = new java.lang.Object();
            this.mCaps = new android.util.ArrayMap();
            this.mLinkProperties = new android.util.ArrayMap();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(android.net.Network network, android.net.NetworkCapabilities networkCapabilities) {
            synchronized (this.mLockObject) {
                this.mCaps.put(network, networkCapabilities);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLinkPropertiesChanged(android.net.Network network, android.net.LinkProperties linkProperties) {
            synchronized (this.mLockObject) {
                this.mLinkProperties.put(network, linkProperties);
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(android.net.Network network) {
            synchronized (this.mLockObject) {
                this.mCaps.remove(network);
                this.mLinkProperties.remove(network);
            }
        }

        private java.util.Set<java.lang.Integer> getNonTestTransportTypes(android.net.NetworkCapabilities networkCapabilities) {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            for (int i : networkCapabilities.getTransportTypes()) {
                arraySet.add(java.lang.Integer.valueOf(i));
            }
            return arraySet;
        }

        private boolean hasSameTransportsAndCapabilities(android.net.NetworkCapabilities networkCapabilities, android.net.NetworkCapabilities networkCapabilities2) {
            if (!java.util.Objects.equals(getNonTestTransportTypes(networkCapabilities), getNonTestTransportTypes(networkCapabilities2))) {
                return false;
            }
            java.util.Iterator it = android.net.vcn.VcnGatewayConnectionConfig.ALLOWED_CAPABILITIES.iterator();
            while (it.hasNext()) {
                int intValue = ((java.lang.Integer) it.next()).intValue();
                if (networkCapabilities.hasCapability(intValue) != networkCapabilities2.hasCapability(intValue)) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean requiresRestartForImmutableCapabilityChanges(android.net.NetworkCapabilities networkCapabilities, android.net.LinkProperties linkProperties) {
            if (networkCapabilities.getSubscriptionIds() == null) {
                return false;
            }
            synchronized (this.mLockObject) {
                try {
                    for (java.util.Map.Entry<android.net.Network, android.net.LinkProperties> entry : this.mLinkProperties.entrySet()) {
                        if (linkProperties.getInterfaceName() != null && !linkProperties.getInterfaceName().isEmpty() && java.util.Objects.equals(linkProperties.getInterfaceName(), entry.getValue().getInterfaceName())) {
                            return this.mCaps.get(entry.getKey()).hasCapability(13) != networkCapabilities.hasCapability(13);
                        }
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("TrackingNetworkCallback:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("mCaps:");
            indentingPrintWriter.increaseIndent();
            synchronized (this.mCaps) {
                try {
                    for (java.util.Map.Entry<android.net.Network, android.net.NetworkCapabilities> entry : this.mCaps.entrySet()) {
                        indentingPrintWriter.println(entry.getKey() + ": " + entry.getValue());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class VcnCallbackImpl implements com.android.server.VcnManagementService.VcnCallback {

        @android.annotation.NonNull
        private final android.os.ParcelUuid mSubGroup;

        private VcnCallbackImpl(@android.annotation.NonNull android.os.ParcelUuid parcelUuid) {
            java.util.Objects.requireNonNull(parcelUuid, "Missing subGroup");
            this.mSubGroup = parcelUuid;
        }

        @Override // com.android.server.VcnManagementService.VcnCallback
        public void onSafeModeStatusChanged(boolean z) {
            synchronized (com.android.server.VcnManagementService.this.mLock) {
                try {
                    if (com.android.server.VcnManagementService.this.mVcns.containsKey(this.mSubGroup)) {
                        int i = z ? 3 : 2;
                        com.android.server.VcnManagementService.this.notifyAllPolicyListenersLocked();
                        com.android.server.VcnManagementService.this.notifyAllPermissionedStatusCallbacksLocked(this.mSubGroup, i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.VcnManagementService.VcnCallback
        public void onGatewayConnectionError(@android.annotation.NonNull final java.lang.String str, final int i, @android.annotation.Nullable final java.lang.String str2, @android.annotation.Nullable final java.lang.String str3) {
            synchronized (com.android.server.VcnManagementService.this.mLock) {
                try {
                    if (com.android.server.VcnManagementService.this.mVcns.containsKey(this.mSubGroup)) {
                        for (final com.android.server.VcnManagementService.VcnStatusCallbackInfo vcnStatusCallbackInfo : com.android.server.VcnManagementService.this.mRegisteredStatusCallbacks.values()) {
                            if (com.android.server.VcnManagementService.this.isCallbackPermissioned(vcnStatusCallbackInfo, this.mSubGroup)) {
                                android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.VcnManagementService$VcnCallbackImpl$$ExternalSyntheticLambda0
                                    public final void runOrThrow() {
                                        com.android.server.VcnManagementService.VcnCallbackImpl.this.lambda$onGatewayConnectionError$0(vcnStatusCallbackInfo, str, i, str2, str3);
                                    }
                                });
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGatewayConnectionError$0(com.android.server.VcnManagementService.VcnStatusCallbackInfo vcnStatusCallbackInfo, java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws java.lang.Exception {
            try {
                vcnStatusCallbackInfo.mCallback.onGatewayConnectionError(str, i, str2, str3);
            } catch (android.os.RemoteException e) {
                com.android.server.VcnManagementService.this.logDbg("VcnStatusCallback threw on VCN status change", e);
            }
        }
    }
}
