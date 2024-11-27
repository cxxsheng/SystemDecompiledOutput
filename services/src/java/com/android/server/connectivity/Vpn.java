package com.android.server.connectivity;

/* loaded from: classes.dex */
public class Vpn {
    private static final java.lang.String ANDROID_KEYSTORE_PROVIDER = "AndroidKeyStore";

    @com.android.internal.annotations.VisibleForTesting
    public static final int AUTOMATIC_KEEPALIVE_DELAY_SECONDS = 30;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_LONG_LIVED_TCP_CONNS_EXPENSIVE_TIMEOUT_SEC = 60;

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_UDP_PORT_4500_NAT_TIMEOUT_SEC_INT = 300;
    private static final long IKE_DELAY_ON_NC_LP_CHANGE_MS = 300;
    private static final java.lang.String LOCKDOWN_ALLOWLIST_SETTING_NAME = "always_on_vpn_lockdown_whitelist";
    private static final boolean LOGD = true;
    private static final int MAX_EVENTS_LOGS = 100;
    private static final int MAX_MOBIKE_RECOVERY_ATTEMPT = 2;

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_VPN_PROFILE_SIZE_BYTES = 131072;
    private static final java.lang.String NETWORKTYPE = "VPN";

    @com.android.internal.annotations.VisibleForTesting
    public static final int PREFERRED_IKE_PROTOCOL_AUTO = 0;

    @com.android.internal.annotations.VisibleForTesting
    public static final int PREFERRED_IKE_PROTOCOL_IPV4_UDP = 40;

    @com.android.internal.annotations.VisibleForTesting
    public static final int PREFERRED_IKE_PROTOCOL_IPV6_ESP = 61;

    @com.android.internal.annotations.VisibleForTesting
    public static final int PREFERRED_IKE_PROTOCOL_IPV6_UDP = 60;
    private static final int PREFERRED_IKE_PROTOCOL_UNKNOWN = -1;
    private static final long RETRY_DELAY_AUTO_BACKOFF = -1;
    private static final int STARTING_TOKEN = -1;
    private static final java.lang.String TAG = "Vpn";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String VPN_APP_EXCLUDED = "VPNAPPEXCLUDED_";
    private static final int VPN_DEFAULT_SCORE = 101;
    private static final long VPN_LAUNCH_IDLE_ALLOWLIST_DURATION_MS = 60000;
    private static final long VPN_MANAGER_EVENT_ALLOWLIST_DURATION_MS = 30000;
    private static final java.lang.String VPN_PROVIDER_NAME_BASE = "VpnNetworkProvider:";

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    protected boolean mAlwaysOn;
    private final android.app.AppOpsManager mAppOpsManager;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final java.util.Set<android.net.UidRangeParcel> mBlockedUidsAsToldToConnectivity;

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.SparseArray<com.android.server.connectivity.Vpn.CarrierConfigInfo> mCachedCarrierConfigInfoPerSubId;
    private final android.telephony.CarrierConfigManager mCarrierConfigManager;

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    protected com.android.internal.net.VpnConfig mConfig;
    private com.android.server.connectivity.Vpn.Connection mConnection;
    private final android.net.ConnectivityDiagnosticsManager mConnectivityDiagnosticsManager;
    private final android.net.ConnectivityManager mConnectivityManager;
    private final android.content.Context mContext;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.connectivity.Vpn.Dependencies mDeps;
    private volatile boolean mEnableTeardown;
    private final android.util.LocalLog mEventChanges;
    private final com.android.server.connectivity.Vpn.Ikev2SessionCreator mIkev2SessionCreator;

    @com.android.internal.annotations.VisibleForTesting
    protected java.lang.String mInterface;
    private boolean mIsPackageTargetingAtLeastQ;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mLegacyState;

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    protected boolean mLockdown;

    @android.annotation.NonNull
    private java.util.List<java.lang.String> mLockdownAllowlist;
    private final android.os.Looper mLooper;
    private final android.net.INetd mNetd;

    @com.android.internal.annotations.VisibleForTesting
    protected android.net.NetworkAgent mNetworkAgent;

    @com.android.internal.annotations.VisibleForTesting
    protected android.net.NetworkCapabilities mNetworkCapabilities;
    private final android.net.NetworkInfo mNetworkInfo;
    private final android.net.NetworkProvider mNetworkProvider;
    private final android.os.INetworkManagementService mNms;
    private android.net.INetworkManagementEventObserver mObserver;
    private int mOwnerUID;

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    protected java.lang.String mPackage;
    private android.app.PendingIntent mStatusIntent;
    private final android.telephony.SubscriptionManager mSubscriptionManager;
    private final com.android.server.connectivity.Vpn.SystemServices mSystemServices;
    private final android.telephony.TelephonyManager mTelephonyManager;
    private final int mUserId;
    private final android.content.Context mUserIdContext;
    private final android.os.UserManager mUserManager;
    private final com.android.server.connectivity.VpnProfileStore mVpnProfileStore;

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.connectivity.Vpn.VpnRunner mVpnRunner;
    private static final long[] IKEV2_VPN_RETRY_DELAYS_MS = {1000, 2000, 5000, 30000, 60000, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS, 900000};
    private static final long[] DATA_STALL_RECOVERY_DELAYS_MS = {1000, 5000, 30000, 60000, 120000, 240000, 480000, 960000};

    interface IkeV2VpnRunnerCallback {
        void onChildMigrated(int i, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform2);

        void onChildOpened(int i, @android.annotation.NonNull android.net.ipsec.ike.ChildSessionConfiguration childSessionConfiguration);

        void onChildTransformCreated(int i, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform, int i2);

        void onDefaultNetworkCapabilitiesChanged(@android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities);

        void onDefaultNetworkChanged(@android.annotation.NonNull android.net.Network network);

        void onDefaultNetworkLinkPropertiesChanged(@android.annotation.NonNull android.net.LinkProperties linkProperties);

        void onDefaultNetworkLost(@android.annotation.NonNull android.net.Network network);

        void onIkeConnectionInfoChanged(int i, @android.annotation.NonNull android.net.ipsec.ike.IkeSessionConnectionInfo ikeSessionConnectionInfo);

        void onIkeOpened(int i, @android.annotation.NonNull android.net.ipsec.ike.IkeSessionConfiguration ikeSessionConfiguration);

        void onSessionLost(int i, @android.annotation.Nullable java.lang.Exception exc);
    }

    @com.android.internal.annotations.VisibleForTesting
    interface ValidationStatusCallback {
        void onValidationStatus(int i);
    }

    private native boolean jniAddAddress(java.lang.String str, java.lang.String str2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native int jniCheck(java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native int jniCreate(int i);

    private native boolean jniDelAddress(java.lang.String str, java.lang.String str2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native java.lang.String jniGetName(int i);

    private native void jniReset(java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public native int jniSetAddresses(java.lang.String str, java.lang.String str2);

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.connectivity.VpnProfileStore getVpnProfileStore() {
        return this.mVpnProfileStore;
    }

    private static class CarrierConfigInfo {
        public final int encapType;
        public final int ipVersion;
        public final int keepaliveDelaySec;
        public final java.lang.String mccMnc;

        CarrierConfigInfo(java.lang.String str, int i, int i2, int i3) {
            this.mccMnc = str;
            this.keepaliveDelaySec = i;
            this.encapType = i2;
            this.ipVersion = i3;
        }

        public java.lang.String toString() {
            return "CarrierConfigInfo(" + this.mccMnc + ") [keepaliveDelaySec=" + this.keepaliveDelaySec + ", encapType=" + this.encapType + ", ipVersion=" + this.ipVersion + "]";
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class Dependencies {
        public boolean isCallerSystem() {
            return android.os.Binder.getCallingUid() == 1000;
        }

        public com.android.server.DeviceIdleInternal getDeviceIdleInternal() {
            return (com.android.server.DeviceIdleInternal) com.android.server.LocalServices.getService(com.android.server.DeviceIdleInternal.class);
        }

        public android.app.PendingIntent getIntentForStatusPanel(android.content.Context context) {
            return com.android.internal.net.VpnConfig.getIntentForStatusPanel(context);
        }

        public android.os.ParcelFileDescriptor adoptFd(com.android.server.connectivity.Vpn vpn, int i) {
            return android.os.ParcelFileDescriptor.adoptFd(jniCreate(vpn, i));
        }

        public int jniCreate(com.android.server.connectivity.Vpn vpn, int i) {
            return vpn.jniCreate(i);
        }

        public java.lang.String jniGetName(com.android.server.connectivity.Vpn vpn, int i) {
            return vpn.jniGetName(i);
        }

        public int jniSetAddresses(com.android.server.connectivity.Vpn vpn, java.lang.String str, java.lang.String str2) {
            return vpn.jniSetAddresses(str, str2);
        }

        public void setBlocking(java.io.FileDescriptor fileDescriptor, boolean z) {
            try {
                libcore.io.IoUtils.setBlocking(fileDescriptor, z);
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalStateException("Cannot set tunnel's fd as blocking=" + z, e);
            }
        }

        public long getNextRetryDelayMs(int i) {
            if (i >= com.android.server.connectivity.Vpn.IKEV2_VPN_RETRY_DELAYS_MS.length) {
                return com.android.server.connectivity.Vpn.IKEV2_VPN_RETRY_DELAYS_MS[com.android.server.connectivity.Vpn.IKEV2_VPN_RETRY_DELAYS_MS.length - 1];
            }
            return com.android.server.connectivity.Vpn.IKEV2_VPN_RETRY_DELAYS_MS[i];
        }

        public java.util.concurrent.ScheduledThreadPoolExecutor newScheduledThreadPoolExecutor() {
            return new java.util.concurrent.ScheduledThreadPoolExecutor(1);
        }

        public android.net.NetworkAgent newNetworkAgent(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Looper looper, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities, @android.annotation.NonNull android.net.LinkProperties linkProperties, @android.annotation.NonNull android.net.NetworkScore networkScore, @android.annotation.NonNull android.net.NetworkAgentConfig networkAgentConfig, @android.annotation.Nullable android.net.NetworkProvider networkProvider, @android.annotation.Nullable com.android.server.connectivity.Vpn.ValidationStatusCallback validationStatusCallback) {
            return new com.android.server.connectivity.Vpn.VpnNetworkAgentWrapper(context, looper, str, networkCapabilities, linkProperties, networkScore, networkAgentConfig, networkProvider, validationStatusCallback);
        }

        public long getValidationFailRecoveryMs(int i) {
            if (i >= com.android.server.connectivity.Vpn.DATA_STALL_RECOVERY_DELAYS_MS.length) {
                return com.android.server.connectivity.Vpn.DATA_STALL_RECOVERY_DELAYS_MS[com.android.server.connectivity.Vpn.DATA_STALL_RECOVERY_DELAYS_MS.length - 1];
            }
            return com.android.server.connectivity.Vpn.DATA_STALL_RECOVERY_DELAYS_MS[i];
        }

        public int getJavaNetworkInterfaceMtu(@android.annotation.Nullable java.lang.String str, int i) throws java.net.SocketException {
            java.net.NetworkInterface byName;
            return (str == null || (byName = java.net.NetworkInterface.getByName(str)) == null) ? i : byName.getMTU();
        }

        public int calculateVpnMtu(@android.annotation.NonNull java.util.List<android.net.ipsec.ike.ChildSaProposal> list, int i, int i2, boolean z) {
            return com.android.server.vcn.util.MtuUtils.getMtu(list, i, i2, z);
        }

        public void verifyCallingUidAndPackage(android.content.Context context, java.lang.String str, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            if (com.android.server.connectivity.Vpn.getAppUid(context, str, i) != callingUid) {
                throw new java.lang.SecurityException(str + " does not belong to uid " + callingUid);
            }
        }
    }

    public Vpn(android.os.Looper looper, android.content.Context context, android.os.INetworkManagementService iNetworkManagementService, android.net.INetd iNetd, int i, com.android.server.connectivity.VpnProfileStore vpnProfileStore) {
        this(looper, context, new com.android.server.connectivity.Vpn.Dependencies(), iNetworkManagementService, iNetd, i, vpnProfileStore, new com.android.server.connectivity.Vpn.SystemServices(context), new com.android.server.connectivity.Vpn.Ikev2SessionCreator());
    }

    @com.android.internal.annotations.VisibleForTesting
    public Vpn(android.os.Looper looper, android.content.Context context, com.android.server.connectivity.Vpn.Dependencies dependencies, android.os.INetworkManagementService iNetworkManagementService, android.net.INetd iNetd, int i, com.android.server.connectivity.VpnProfileStore vpnProfileStore) {
        this(looper, context, dependencies, iNetworkManagementService, iNetd, i, vpnProfileStore, new com.android.server.connectivity.Vpn.SystemServices(context), new com.android.server.connectivity.Vpn.Ikev2SessionCreator());
    }

    @com.android.internal.annotations.VisibleForTesting
    protected Vpn(android.os.Looper looper, android.content.Context context, com.android.server.connectivity.Vpn.Dependencies dependencies, android.os.INetworkManagementService iNetworkManagementService, android.net.INetd iNetd, int i, com.android.server.connectivity.VpnProfileStore vpnProfileStore, com.android.server.connectivity.Vpn.SystemServices systemServices, com.android.server.connectivity.Vpn.Ikev2SessionCreator ikev2SessionCreator) {
        this.mEnableTeardown = true;
        this.mEventChanges = new android.util.LocalLog(100);
        this.mCachedCarrierConfigInfoPerSubId = new android.util.SparseArray<>();
        this.mAlwaysOn = false;
        this.mLockdown = false;
        this.mLockdownAllowlist = java.util.Collections.emptyList();
        this.mBlockedUidsAsToldToConnectivity = new android.util.ArraySet();
        this.mObserver = new com.android.server.net.BaseNetworkObserver() { // from class: com.android.server.connectivity.Vpn.1
            public void interfaceRemoved(java.lang.String str) {
                synchronized (com.android.server.connectivity.Vpn.this) {
                    try {
                        if (str.equals(com.android.server.connectivity.Vpn.this.mInterface) && com.android.server.connectivity.Vpn.this.jniCheck(str) == 0) {
                            if (com.android.server.connectivity.Vpn.this.mConnection != null) {
                                com.android.server.connectivity.Vpn.this.mAppOpsManager.finishOp("android:establish_vpn_service", com.android.server.connectivity.Vpn.this.mOwnerUID, com.android.server.connectivity.Vpn.this.mPackage, null);
                                com.android.server.connectivity.Vpn.this.mContext.unbindService(com.android.server.connectivity.Vpn.this.mConnection);
                                com.android.server.connectivity.Vpn.this.cleanupVpnStateLocked();
                            } else if (com.android.server.connectivity.Vpn.this.mVpnRunner != null) {
                                if (!"[Legacy VPN]".equals(com.android.server.connectivity.Vpn.this.mPackage)) {
                                    com.android.server.connectivity.Vpn.this.mAppOpsManager.finishOp("android:establish_vpn_manager", com.android.server.connectivity.Vpn.this.mOwnerUID, com.android.server.connectivity.Vpn.this.mPackage, null);
                                }
                                com.android.server.connectivity.Vpn.this.mVpnRunner.exit();
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mVpnProfileStore = vpnProfileStore;
        this.mContext = context;
        this.mConnectivityManager = (android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class);
        this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        this.mUserIdContext = context.createContextAsUser(android.os.UserHandle.of(i), 0);
        this.mConnectivityDiagnosticsManager = (android.net.ConnectivityDiagnosticsManager) this.mContext.getSystemService(android.net.ConnectivityDiagnosticsManager.class);
        this.mCarrierConfigManager = (android.telephony.CarrierConfigManager) this.mContext.getSystemService(android.telephony.CarrierConfigManager.class);
        this.mTelephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class);
        this.mSubscriptionManager = (android.telephony.SubscriptionManager) this.mContext.getSystemService(android.telephony.SubscriptionManager.class);
        this.mDeps = dependencies;
        this.mNms = iNetworkManagementService;
        this.mNetd = iNetd;
        this.mUserId = i;
        this.mLooper = looper;
        this.mSystemServices = systemServices;
        this.mIkev2SessionCreator = ikev2SessionCreator;
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        this.mPackage = "[Legacy VPN]";
        this.mOwnerUID = getAppUid(this.mContext, this.mPackage, this.mUserId);
        this.mIsPackageTargetingAtLeastQ = doesPackageTargetAtLeastQ(this.mPackage);
        try {
            iNetworkManagementService.registerObserver(this.mObserver);
        } catch (android.os.RemoteException e) {
            android.util.Log.wtf(TAG, "Problem registering observer", e);
        }
        this.mNetworkProvider = new android.net.NetworkProvider(context, looper, VPN_PROVIDER_NAME_BASE + this.mUserId);
        this.mConnectivityManager.registerNetworkProvider(this.mNetworkProvider);
        this.mLegacyState = 0;
        this.mNetworkInfo = new android.net.NetworkInfo(17, 0, NETWORKTYPE, "");
        this.mNetworkCapabilities = new android.net.NetworkCapabilities.Builder().addTransportType(4).removeCapability(15).addCapability(28).setTransportInfo(new android.net.VpnTransportInfo(-1, (java.lang.String) null, false, false)).build();
        loadAlwaysOnPackage();
    }

    public void setEnableTeardown(boolean z) {
        this.mEnableTeardown = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean getEnableTeardown() {
        return this.mEnableTeardown;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    protected void updateState(android.net.NetworkInfo.DetailedState detailedState, java.lang.String str) {
        android.util.Log.d(TAG, "setting state=" + detailedState + ", reason=" + str);
        this.mLegacyState = com.android.internal.net.LegacyVpnInfo.stateFromNetworkInfo(detailedState);
        this.mNetworkInfo.setDetailedState(detailedState, str, null);
        switch (com.android.server.connectivity.Vpn.AnonymousClass2.$SwitchMap$android$net$NetworkInfo$DetailedState[detailedState.ordinal()]) {
            case 1:
                if (this.mNetworkAgent != null) {
                    this.mNetworkAgent.markConnected();
                    break;
                }
                break;
            case 2:
            case 3:
                if (this.mNetworkAgent != null) {
                    this.mNetworkAgent.unregister();
                    this.mNetworkAgent = null;
                    break;
                }
                break;
            case 4:
                if (this.mNetworkAgent != null) {
                    throw new java.lang.IllegalStateException("VPN can only go to CONNECTING state when the agent is null.");
                }
                break;
            default:
                throw new java.lang.IllegalArgumentException("Illegal state argument " + detailedState);
        }
        updateAlwaysOnNotification(detailedState);
    }

    /* renamed from: com.android.server.connectivity.Vpn$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$android$net$NetworkInfo$DetailedState = new int[android.net.NetworkInfo.DetailedState.values().length];

        static {
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[android.net.NetworkInfo.DetailedState.CONNECTED.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[android.net.NetworkInfo.DetailedState.DISCONNECTED.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[android.net.NetworkInfo.DetailedState.FAILED.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$net$NetworkInfo$DetailedState[android.net.NetworkInfo.DetailedState.CONNECTING.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
        }
    }

    private void resetNetworkCapabilities() {
        this.mNetworkCapabilities = new android.net.NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids((java.util.Set) null).setTransportInfo(new android.net.VpnTransportInfo(-1, (java.lang.String) null, false, false)).build();
    }

    public synchronized void setLockdown(boolean z) {
        enforceControlPermissionOrInternalCaller();
        setVpnForcedLocked(z);
        this.mLockdown = z;
        if (this.mAlwaysOn) {
            saveAlwaysOnPackage();
        }
    }

    public synchronized java.lang.String getPackage() {
        return this.mPackage;
    }

    public synchronized boolean getLockdown() {
        return this.mLockdown;
    }

    public synchronized boolean getAlwaysOn() {
        return this.mAlwaysOn;
    }

    public boolean isAlwaysOnPackageSupported(java.lang.String str) {
        android.content.pm.ApplicationInfo applicationInfo;
        enforceSettingsPermission();
        if (str == null) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (getVpnProfilePrivileged(str) != null) {
                return true;
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
            try {
                applicationInfo = packageManager.getApplicationInfoAsUser(str, 0, this.mUserId);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.w(TAG, "Can't find \"" + str + "\" when checking always-on support");
                applicationInfo = null;
            }
            if (applicationInfo == null || applicationInfo.targetSdkVersion < 24) {
                return false;
            }
            android.content.Intent intent = new android.content.Intent("android.net.VpnService");
            intent.setPackage(str);
            java.util.List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, 128, this.mUserId);
            if (queryIntentServicesAsUser == null || queryIntentServicesAsUser.size() == 0) {
                return false;
            }
            java.util.Iterator it = queryIntentServicesAsUser.iterator();
            while (it.hasNext()) {
                android.os.Bundle bundle = ((android.content.pm.ResolveInfo) it.next()).serviceInfo.metaData;
                if (bundle != null && !bundle.getBoolean("android.net.VpnService.SUPPORTS_ALWAYS_ON", true)) {
                    return false;
                }
            }
            return true;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private android.content.Intent buildVpnManagerEventIntent(@android.annotation.NonNull java.lang.String str, int i, int i2, @android.annotation.NonNull java.lang.String str2, @android.annotation.Nullable java.lang.String str3, @android.annotation.NonNull android.net.VpnProfileState vpnProfileState, @android.annotation.Nullable android.net.Network network, @android.annotation.Nullable android.net.NetworkCapabilities networkCapabilities, @android.annotation.Nullable android.net.LinkProperties linkProperties) {
        android.util.Log.d(TAG, "buildVpnManagerEventIntent: sessionKey = " + str3);
        android.content.Intent intent = new android.content.Intent("android.net.action.VPN_MANAGER_EVENT");
        intent.setPackage(str2);
        intent.addCategory(str);
        intent.putExtra("android.net.extra.VPN_PROFILE_STATE", vpnProfileState);
        intent.putExtra("android.net.extra.SESSION_KEY", str3);
        intent.putExtra("android.net.extra.UNDERLYING_NETWORK", network);
        intent.putExtra("android.net.extra.UNDERLYING_NETWORK_CAPABILITIES", networkCapabilities);
        intent.putExtra("android.net.extra.UNDERLYING_LINK_PROPERTIES", linkProperties);
        intent.putExtra("android.net.extra.TIMESTAMP_MILLIS", java.lang.System.currentTimeMillis());
        if (!"android.net.category.EVENT_DEACTIVATED_BY_USER".equals(str) || !"android.net.category.EVENT_ALWAYS_ON_STATE_CHANGED".equals(str)) {
            intent.putExtra("android.net.extra.ERROR_CLASS", i);
            intent.putExtra("android.net.extra.ERROR_CODE", i2);
        }
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sendEventToVpnManagerApp(@android.annotation.NonNull java.lang.String str, int i, int i2, @android.annotation.NonNull java.lang.String str2, @android.annotation.Nullable java.lang.String str3, @android.annotation.NonNull android.net.VpnProfileState vpnProfileState, @android.annotation.Nullable android.net.Network network, @android.annotation.Nullable android.net.NetworkCapabilities networkCapabilities, @android.annotation.Nullable android.net.LinkProperties linkProperties) {
        this.mEventChanges.log("[VMEvent] Event class=" + getVpnManagerEventClassName(i) + ", err=" + getVpnManagerEventErrorName(i2) + " for " + str2 + " on session " + str3);
        return sendEventToVpnManagerApp(buildVpnManagerEventIntent(str, i, i2, str2, str3, vpnProfileState, network, networkCapabilities, linkProperties), str2);
    }

    private boolean sendEventToVpnManagerApp(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDeps.getDeviceIdleInternal().addPowerSaveTempWhitelistApp(android.os.Process.myUid(), str, 30000L, this.mUserId, false, 309, "VpnManager event");
            return this.mUserIdContext.startService(intent) != null;
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Service of VpnManager app " + intent + " failed to start", e);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isVpnApp(java.lang.String str) {
        return (str == null || "[Legacy VPN]".equals(str)) ? false : true;
    }

    public synchronized boolean setAlwaysOnPackage(@android.annotation.Nullable java.lang.String str, boolean z, @android.annotation.Nullable java.util.List<java.lang.String> list) {
        try {
            enforceControlPermissionOrInternalCaller();
            java.lang.String str2 = this.mPackage;
            boolean z2 = !java.util.Objects.equals(str, str2);
            boolean z3 = isVpnApp(str2) && this.mAlwaysOn && (z != this.mLockdown || z2);
            boolean z4 = isVpnApp(str) && z2;
            if (!setAlwaysOnPackageInternal(str, z, list)) {
                return false;
            }
            saveAlwaysOnPackage();
            if (z3) {
                sendEventToVpnManagerApp("android.net.category.EVENT_ALWAYS_ON_STATE_CHANGED", -1, -1, str2, null, z2 ? makeDisconnectedVpnProfileState() : makeVpnProfileStateLocked(), null, null, null);
            }
            if (z4) {
                sendEventToVpnManagerApp("android.net.category.EVENT_ALWAYS_ON_STATE_CHANGED", -1, -1, str, getSessionKeyLocked(), makeVpnProfileStateLocked(), null, null, null);
            }
            return true;
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean setAlwaysOnPackageInternal(@android.annotation.Nullable java.lang.String str, boolean z, @android.annotation.Nullable java.util.List<java.lang.String> list) {
        java.util.List<java.lang.String> emptyList;
        boolean z2 = false;
        if ("[Legacy VPN]".equals(str)) {
            android.util.Log.w(TAG, "Not setting legacy VPN \"" + str + "\" as always-on.");
            return false;
        }
        if (list != null) {
            for (java.lang.String str2 : list) {
                if (str2.contains(",")) {
                    android.util.Log.w(TAG, "Not setting always-on vpn, invalid allowed package: " + str2);
                    return false;
                }
            }
        }
        if (str != null) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!setPackageAuthorization(str, getVpnProfilePrivileged(str) == null ? 1 : 2)) {
                    return false;
                }
                this.mAlwaysOn = true;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            this.mAlwaysOn = false;
            str = "[Legacy VPN]";
        }
        boolean z3 = this.mLockdown;
        if (this.mAlwaysOn && z) {
            z2 = true;
        }
        this.mLockdown = z2;
        if (this.mLockdown && list != null) {
            emptyList = java.util.Collections.unmodifiableList(new java.util.ArrayList(list));
        } else {
            emptyList = java.util.Collections.emptyList();
        }
        this.mLockdownAllowlist = emptyList;
        this.mEventChanges.log("[LockdownAlwaysOn] Mode changed: lockdown=" + this.mLockdown + " alwaysOn=" + this.mAlwaysOn + " calling from " + android.os.Binder.getCallingUid());
        if (isCurrentPreparedPackage(str)) {
            updateAlwaysOnNotification(this.mNetworkInfo.getDetailedState());
            setVpnForcedLocked(this.mLockdown);
            if (this.mNetworkAgent != null && z3 != this.mLockdown) {
                startNewNetworkAgent(this.mNetworkAgent, "Lockdown mode changed");
            }
        } else {
            prepareInternal(str);
        }
        return true;
    }

    private static boolean isNullOrLegacyVpn(java.lang.String str) {
        return str == null || "[Legacy VPN]".equals(str);
    }

    public synchronized java.lang.String getAlwaysOnPackage() {
        enforceControlPermissionOrInternalCaller();
        return this.mAlwaysOn ? this.mPackage : null;
    }

    public synchronized java.util.List<java.lang.String> getLockdownAllowlist() {
        return this.mLockdown ? this.mLockdownAllowlist : null;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void saveAlwaysOnPackage() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSystemServices.settingsSecurePutStringForUser("always_on_vpn_app", getAlwaysOnPackage(), this.mUserId);
            this.mSystemServices.settingsSecurePutIntForUser("always_on_vpn_lockdown", (this.mAlwaysOn && this.mLockdown) ? 1 : 0, this.mUserId);
            this.mSystemServices.settingsSecurePutStringForUser(LOCKDOWN_ALLOWLIST_SETTING_NAME, java.lang.String.join(",", this.mLockdownAllowlist), this.mUserId);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void loadAlwaysOnPackage() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.String str = this.mSystemServices.settingsSecureGetStringForUser("always_on_vpn_app", this.mUserId);
            boolean z = this.mSystemServices.settingsSecureGetIntForUser("always_on_vpn_lockdown", 0, this.mUserId) != 0;
            java.lang.String str2 = this.mSystemServices.settingsSecureGetStringForUser(LOCKDOWN_ALLOWLIST_SETTING_NAME, this.mUserId);
            setAlwaysOnPackageInternal(str, z, android.text.TextUtils.isEmpty(str2) ? java.util.Collections.emptyList() : java.util.Arrays.asList(str2.split(",")));
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean startAlwaysOnVpn() {
        synchronized (this) {
            try {
                java.lang.String alwaysOnPackage = getAlwaysOnPackage();
                if (alwaysOnPackage == null) {
                    return true;
                }
                if (!isAlwaysOnPackageSupported(alwaysOnPackage)) {
                    setAlwaysOnPackage(null, false, null);
                    return false;
                }
                if (getNetworkInfo().isConnected()) {
                    return true;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.internal.net.VpnProfile vpnProfilePrivileged = getVpnProfilePrivileged(alwaysOnPackage);
                    if (vpnProfilePrivileged != null) {
                        startVpnProfilePrivileged(vpnProfilePrivileged, alwaysOnPackage);
                        return true;
                    }
                    this.mDeps.getDeviceIdleInternal().addPowerSaveTempWhitelistApp(android.os.Process.myUid(), alwaysOnPackage, 60000L, this.mUserId, false, 309, "vpn");
                    android.content.Intent intent = new android.content.Intent("android.net.VpnService");
                    intent.setPackage(alwaysOnPackage);
                    try {
                        return this.mUserIdContext.startService(intent) != null;
                    } catch (java.lang.RuntimeException e) {
                        android.util.Log.e(TAG, "VpnService " + intent + " failed to start", e);
                        return false;
                    }
                } catch (java.lang.Exception e2) {
                    android.util.Log.e(TAG, "Error starting always-on VPN", e2);
                    return false;
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public synchronized boolean prepare(java.lang.String str, java.lang.String str2, int i) {
        try {
            if (this.mContext.checkCallingOrSelfPermission("android.permission.CONTROL_VPN") != 0) {
                if (str != null) {
                    verifyCallingUidAndPackage(str);
                }
                if (str2 != null) {
                    verifyCallingUidAndPackage(str2);
                }
            }
            if (str != null) {
                if (this.mAlwaysOn && !isCurrentPreparedPackage(str)) {
                    return false;
                }
                if (!isCurrentPreparedPackage(str)) {
                    if (str.equals("[Legacy VPN]") || !isVpnPreConsented(this.mContext, str, i)) {
                        return false;
                    }
                    prepareInternal(str);
                    return true;
                }
                if (!str.equals("[Legacy VPN]") && !isVpnPreConsented(this.mContext, str, i)) {
                    prepareInternal("[Legacy VPN]");
                    return false;
                }
            }
            if (str2 != null && (str2.equals("[Legacy VPN]") || !isCurrentPreparedPackage(str2))) {
                enforceControlPermissionOrInternalCaller();
                if (this.mAlwaysOn && !isCurrentPreparedPackage(str2)) {
                    return false;
                }
                prepareInternal(str2);
                return true;
            }
            return true;
        } finally {
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean isCurrentPreparedPackage(java.lang.String str) {
        return getAppUid(this.mContext, str, this.mUserId) == this.mOwnerUID && this.mPackage.equals(str);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void prepareInternal(java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mInterface != null) {
                this.mStatusIntent = null;
                agentDisconnect();
                jniReset(this.mInterface);
                this.mInterface = null;
                resetNetworkCapabilities();
            }
            if (this.mConnection != null) {
                try {
                    this.mConnection.mService.transact(android.hardware.audio.common.V2_0.AudioFormat.SUB_MASK, android.os.Parcel.obtain(), null, 1);
                } catch (java.lang.Exception e) {
                }
                this.mAppOpsManager.finishOp("android:establish_vpn_service", this.mOwnerUID, this.mPackage, null);
                this.mContext.unbindService(this.mConnection);
                cleanupVpnStateLocked();
            } else if (this.mVpnRunner != null) {
                stopVpnRunnerAndNotifyAppLocked();
            }
            try {
                this.mNetd.networkSetProtectDeny(this.mOwnerUID);
            } catch (java.lang.Exception e2) {
                android.util.Log.wtf(TAG, "Failed to disallow UID " + this.mOwnerUID + " to call protect() " + e2);
            }
            android.util.Log.i(TAG, "Switched from " + this.mPackage + " to " + str);
            this.mPackage = str;
            this.mOwnerUID = getAppUid(this.mContext, str, this.mUserId);
            this.mIsPackageTargetingAtLeastQ = doesPackageTargetAtLeastQ(str);
            try {
                this.mNetd.networkSetProtectAllow(this.mOwnerUID);
            } catch (java.lang.Exception e3) {
                android.util.Log.wtf(TAG, "Failed to allow UID " + this.mOwnerUID + " to call protect() " + e3);
            }
            this.mConfig = null;
            updateState(android.net.NetworkInfo.DetailedState.DISCONNECTED, "prepare");
            setVpnForcedLocked(this.mLockdown);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean setPackageAuthorization(java.lang.String str, int i) {
        java.lang.String[] strArr;
        enforceControlPermissionOrInternalCaller();
        int appUid = getAppUid(this.mContext, str, this.mUserId);
        if (appUid == -1 || "[Legacy VPN]".equals(str)) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            switch (i) {
                case -1:
                    strArr = new java.lang.String[]{"android:activate_vpn", "android:activate_platform_vpn"};
                    break;
                case 0:
                default:
                    android.util.Log.wtf(TAG, "Unrecognized VPN type while granting authorization");
                    return false;
                case 1:
                    strArr = new java.lang.String[]{"android:activate_vpn"};
                    break;
                case 2:
                    strArr = new java.lang.String[]{"android:activate_platform_vpn"};
                    break;
                case 3:
                    return false;
            }
            int length = strArr.length;
            int i2 = 0;
            while (true) {
                int i3 = 1;
                if (i2 >= length) {
                    return true;
                }
                java.lang.String str2 = strArr[i2];
                android.app.AppOpsManager appOpsManager = this.mAppOpsManager;
                if (i != -1) {
                    i3 = 0;
                }
                appOpsManager.setMode(str2, appUid, str, i3);
                i2++;
            }
        } catch (java.lang.Exception e) {
            android.util.Log.wtf(TAG, "Failed to set app ops for package " + str + ", uid " + appUid, e);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static boolean isVpnPreConsented(android.content.Context context, java.lang.String str, int i) {
        switch (i) {
            case 1:
                return isVpnServicePreConsented(context, str);
            case 2:
                return isVpnProfilePreConsented(context, str);
            case 3:
                return "[Legacy VPN]".equals(str);
            default:
                return false;
        }
    }

    private static boolean doesPackageHaveAppop(android.content.Context context, java.lang.String str, java.lang.String str2) {
        return ((android.app.AppOpsManager) context.getSystemService("appops")).noteOpNoThrow(str2, android.os.Binder.getCallingUid(), str, null, null) == 0;
    }

    private static boolean isVpnServicePreConsented(android.content.Context context, java.lang.String str) {
        return doesPackageHaveAppop(context, str, "android:activate_vpn");
    }

    private static boolean isVpnProfilePreConsented(android.content.Context context, java.lang.String str) {
        return doesPackageHaveAppop(context, str, "android:activate_platform_vpn") || isVpnServicePreConsented(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getAppUid(android.content.Context context, java.lang.String str, int i) {
        if ("[Legacy VPN]".equals(str)) {
            return android.os.Process.myUid();
        }
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int packageUidAsUser = packageManager.getPackageUidAsUser(str, i);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return packageUidAsUser;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return -1;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private boolean doesPackageTargetAtLeastQ(java.lang.String str) {
        if ("[Legacy VPN]".equals(str)) {
            return true;
        }
        try {
            return this.mContext.getPackageManager().getApplicationInfoAsUser(str, 0, this.mUserId).targetSdkVersion >= 29;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, "Can't find \"" + str + "\"");
            return false;
        }
    }

    public android.net.NetworkInfo getNetworkInfo() {
        return this.mNetworkInfo;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public synchronized android.net.Network getNetwork() {
        android.net.NetworkAgent networkAgent = this.mNetworkAgent;
        if (networkAgent == null) {
            return null;
        }
        android.net.Network network = networkAgent.getNetwork();
        if (network == null) {
            return null;
        }
        return network;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.net.LinkProperties makeLinkProperties() {
        boolean z = isIkev2VpnRunner() && this.mConfig.mtu < 1280;
        boolean z2 = this.mConfig.allowIPv4;
        boolean z3 = this.mConfig.allowIPv6;
        android.net.LinkProperties linkProperties = new android.net.LinkProperties();
        linkProperties.setInterfaceName(this.mInterface);
        if (this.mConfig.addresses != null) {
            for (android.net.LinkAddress linkAddress : this.mConfig.addresses) {
                if (!z || !linkAddress.isIpv6()) {
                    linkProperties.addLinkAddress(linkAddress);
                    z2 |= linkAddress.getAddress() instanceof java.net.Inet4Address;
                    z3 |= linkAddress.getAddress() instanceof java.net.Inet6Address;
                }
            }
        }
        if (this.mConfig.routes != null) {
            for (android.net.RouteInfo routeInfo : this.mConfig.routes) {
                java.net.InetAddress address = routeInfo.getDestination().getAddress();
                if (!z || !(address instanceof java.net.Inet6Address)) {
                    linkProperties.addRoute(routeInfo);
                    if (routeInfo.getType() == 1) {
                        z2 |= address instanceof java.net.Inet4Address;
                        z3 |= address instanceof java.net.Inet6Address;
                    }
                }
            }
        }
        if (this.mConfig.dnsServers != null) {
            java.util.Iterator it = this.mConfig.dnsServers.iterator();
            while (it.hasNext()) {
                java.net.InetAddress parseNumericAddress = android.net.InetAddresses.parseNumericAddress((java.lang.String) it.next());
                if (!z || !(parseNumericAddress instanceof java.net.Inet6Address)) {
                    linkProperties.addDnsServer(parseNumericAddress);
                    z2 |= parseNumericAddress instanceof java.net.Inet4Address;
                    z3 |= parseNumericAddress instanceof java.net.Inet6Address;
                }
            }
        }
        linkProperties.setHttpProxy(this.mConfig.proxyInfo);
        if (!z2) {
            linkProperties.addRoute(new android.net.RouteInfo(new android.net.IpPrefix(com.android.net.module.util.NetworkStackConstants.IPV4_ADDR_ANY, 0), null, null, 7));
        }
        if (!z3 || z) {
            linkProperties.addRoute(new android.net.RouteInfo(new android.net.IpPrefix(com.android.net.module.util.NetworkStackConstants.IPV6_ADDR_ANY, 0), null, null, 7));
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (this.mConfig.searchDomains != null) {
            java.util.Iterator it2 = this.mConfig.searchDomains.iterator();
            while (it2.hasNext()) {
                sb.append((java.lang.String) it2.next());
                sb.append(' ');
            }
        }
        linkProperties.setDomains(sb.toString().trim());
        if (this.mConfig.mtu > 0) {
            linkProperties.setMtu(this.mConfig.mtu);
        }
        return linkProperties;
    }

    private boolean updateLinkPropertiesInPlaceIfPossible(android.net.NetworkAgent networkAgent, com.android.internal.net.VpnConfig vpnConfig) {
        if (vpnConfig.allowBypass != this.mConfig.allowBypass) {
            android.util.Log.i(TAG, "Handover not possible due to changes to allowBypass");
            return false;
        }
        if (!java.util.Objects.equals(vpnConfig.allowedApplications, this.mConfig.allowedApplications) || !java.util.Objects.equals(vpnConfig.disallowedApplications, this.mConfig.disallowedApplications)) {
            android.util.Log.i(TAG, "Handover not possible due to changes to allowed/denied apps");
            return false;
        }
        networkAgent.sendLinkProperties(makeLinkProperties());
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void agentConnect() {
        agentConnect(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public void agentConnect(@android.annotation.Nullable com.android.server.connectivity.Vpn.ValidationStatusCallback validationStatusCallback) {
        com.android.server.connectivity.Vpn.IkeSessionWrapper ikeSessionWrapper;
        android.net.LinkProperties makeLinkProperties = makeLinkProperties();
        android.net.NetworkCapabilities.Builder builder = new android.net.NetworkCapabilities.Builder(this.mNetworkCapabilities);
        builder.addCapability(12);
        this.mLegacyState = 2;
        updateState(android.net.NetworkInfo.DetailedState.CONNECTING, "agentConnect");
        boolean z = this.mConfig.allowBypass && !this.mLockdown;
        android.net.NetworkAgentConfig build = new android.net.NetworkAgentConfig.Builder().setLegacyType(17).setLegacyTypeName(NETWORKTYPE).setBypassableVpn(z).setVpnRequiresValidation(this.mConfig.requiresInternetValidation).setLocalRoutesExcludedForVpn(this.mConfig.excludeLocalRoutes).setLegacyExtraInfo("VPN:" + this.mPackage).build();
        builder.setOwnerUid(this.mOwnerUID);
        builder.setAdministratorUids(new int[]{this.mOwnerUID});
        builder.setUids(createUserAndRestrictedProfilesRanges(this.mUserId, this.mConfig.allowedApplications, this.mConfig.disallowedApplications));
        builder.setTransportInfo(new android.net.VpnTransportInfo(getActiveVpnType(), this.mConfig.session, z, areLongLivedTcpConnectionsExpensive(this.mVpnRunner)));
        if (this.mIsPackageTargetingAtLeastQ && this.mConfig.isMetered) {
            builder.removeCapability(11);
        } else {
            builder.addCapability(11);
        }
        builder.setUnderlyingNetworks(this.mConfig.underlyingNetworks != null ? java.util.Arrays.asList(this.mConfig.underlyingNetworks) : null);
        this.mNetworkCapabilities = builder.build();
        logUnderlyNetworkChanges(this.mNetworkCapabilities.getUnderlyingNetworks());
        this.mNetworkAgent = this.mDeps.newNetworkAgent(this.mContext, this.mLooper, NETWORKTYPE, this.mNetworkCapabilities, makeLinkProperties, new android.net.NetworkScore.Builder().setLegacyInt(101).build(), build, this.mNetworkProvider, validationStatusCallback);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mNetworkAgent.register();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                updateState(android.net.NetworkInfo.DetailedState.CONNECTED, "agentConnect");
                if (!isIkev2VpnRunner() || (ikeSessionWrapper = ((com.android.server.connectivity.Vpn.IkeV2VpnRunner) this.mVpnRunner).mSession) == null) {
                    return;
                }
                ikeSessionWrapper.setUnderpinnedNetwork(this.mNetworkAgent.getNetwork());
            } catch (java.lang.Exception e) {
                this.mNetworkAgent = null;
                throw e;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private static boolean areLongLivedTcpConnectionsExpensive(@android.annotation.NonNull com.android.server.connectivity.Vpn.VpnRunner vpnRunner) {
        if (vpnRunner instanceof com.android.server.connectivity.Vpn.IkeV2VpnRunner) {
            return areLongLivedTcpConnectionsExpensive(((com.android.server.connectivity.Vpn.IkeV2VpnRunner) vpnRunner).getOrGuessKeepaliveDelaySeconds());
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean areLongLivedTcpConnectionsExpensive(int i) {
        return i < 60;
    }

    private boolean canHaveRestrictedProfile(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return ((android.os.UserManager) this.mContext.createContextAsUser(android.os.UserHandle.of(i), 0).getSystemService(android.os.UserManager.class)).canHaveRestrictedProfile();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void logUnderlyNetworkChanges(java.util.List<android.net.Network> list) {
        android.util.LocalLog localLog = this.mEventChanges;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("[UnderlyingNW] Switch to ");
        sb.append(list != null ? android.text.TextUtils.join(", ", list) : "null");
        localLog.log(sb.toString());
    }

    private void agentDisconnect(android.net.NetworkAgent networkAgent) {
        if (networkAgent != null) {
            networkAgent.unregister();
        }
    }

    private void agentDisconnect() {
        updateState(android.net.NetworkInfo.DetailedState.DISCONNECTED, "agentDisconnect");
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public void startNewNetworkAgent(android.net.NetworkAgent networkAgent, java.lang.String str) {
        this.mNetworkAgent = null;
        updateState(android.net.NetworkInfo.DetailedState.CONNECTING, str);
        agentConnect();
        agentDisconnect(networkAgent);
    }

    public synchronized android.os.ParcelFileDescriptor establish(com.android.internal.net.VpnConfig vpnConfig) {
        try {
            if (android.os.Binder.getCallingUid() != this.mOwnerUID) {
                return null;
            }
            if (!isVpnServicePreConsented(this.mContext, this.mPackage)) {
                return null;
            }
            android.content.Intent intent = new android.content.Intent("android.net.VpnService");
            intent.setClassName(this.mPackage, vpnConfig.user);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                enforceNotRestrictedUser();
                android.content.pm.PackageManager packageManager = this.mUserIdContext.getPackageManager();
                if (packageManager == null) {
                    throw new java.lang.IllegalStateException("Cannot get PackageManager.");
                }
                android.content.pm.ResolveInfo resolveService = packageManager.resolveService(intent, 0);
                if (resolveService == null) {
                    throw new java.lang.SecurityException("Cannot find " + vpnConfig.user);
                }
                if (!"android.permission.BIND_VPN_SERVICE".equals(resolveService.serviceInfo.permission)) {
                    throw new java.lang.SecurityException(vpnConfig.user + " does not require android.permission.BIND_VPN_SERVICE");
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                com.android.internal.net.VpnConfig vpnConfig2 = this.mConfig;
                java.lang.String str = this.mInterface;
                com.android.server.connectivity.Vpn.Connection connection = this.mConnection;
                android.net.NetworkAgent networkAgent = this.mNetworkAgent;
                java.util.Set uids = this.mNetworkCapabilities.getUids();
                android.os.ParcelFileDescriptor adoptFd = this.mDeps.adoptFd(this, vpnConfig.mtu);
                try {
                    java.lang.String jniGetName = this.mDeps.jniGetName(this, adoptFd.getFd());
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    for (android.net.LinkAddress linkAddress : vpnConfig.addresses) {
                        sb.append(" ");
                        sb.append(linkAddress);
                    }
                    if (this.mDeps.jniSetAddresses(this, jniGetName, sb.toString()) < 1) {
                        throw new java.lang.IllegalArgumentException("At least one address must be specified");
                    }
                    com.android.server.connectivity.Vpn.Connection connection2 = new com.android.server.connectivity.Vpn.Connection();
                    if (!this.mContext.bindServiceAsUser(intent, connection2, android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN, new android.os.UserHandle(this.mUserId))) {
                        throw new java.lang.IllegalStateException("Cannot bind " + vpnConfig.user);
                    }
                    this.mConnection = connection2;
                    this.mInterface = jniGetName;
                    vpnConfig.user = this.mPackage;
                    vpnConfig.interfaze = this.mInterface;
                    vpnConfig.startTime = android.os.SystemClock.elapsedRealtime();
                    this.mConfig = vpnConfig;
                    if (vpnConfig2 != null && updateLinkPropertiesInPlaceIfPossible(this.mNetworkAgent, vpnConfig2)) {
                        if (!java.util.Arrays.equals(vpnConfig2.underlyingNetworks, vpnConfig.underlyingNetworks)) {
                            setUnderlyingNetworks(vpnConfig.underlyingNetworks);
                        }
                    } else {
                        startNewNetworkAgent(networkAgent, "establish");
                    }
                    if (connection != null) {
                        this.mContext.unbindService(connection);
                    }
                    if (str != null && !str.equals(jniGetName)) {
                        jniReset(str);
                    }
                    this.mDeps.setBlocking(adoptFd.getFileDescriptor(), vpnConfig.blocking);
                    if (networkAgent != this.mNetworkAgent) {
                        this.mAppOpsManager.startOp("android:establish_vpn_service", this.mOwnerUID, this.mPackage, null, null);
                    }
                    android.util.Log.i(TAG, "Established by " + vpnConfig.user + " on " + this.mInterface);
                    return adoptFd;
                } catch (java.lang.RuntimeException e) {
                    libcore.io.IoUtils.closeQuietly(adoptFd);
                    if (networkAgent != this.mNetworkAgent) {
                        agentDisconnect();
                    }
                    this.mConfig = vpnConfig2;
                    this.mConnection = connection;
                    this.mNetworkCapabilities = new android.net.NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids(uids).build();
                    this.mNetworkAgent = networkAgent;
                    this.mInterface = str;
                    throw e;
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private boolean isRunningLocked() {
        return (this.mNetworkAgent == null || this.mInterface == null) ? false : true;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean isCallerEstablishedOwnerLocked() {
        return isRunningLocked() && android.os.Binder.getCallingUid() == this.mOwnerUID;
    }

    private java.util.SortedSet<java.lang.Integer> getAppsUids(java.util.List<java.lang.String> list, int i) {
        java.util.TreeSet treeSet = new java.util.TreeSet();
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            int appUid = getAppUid(this.mContext, it.next(), i);
            if (appUid != -1) {
                treeSet.add(java.lang.Integer.valueOf(appUid));
            }
            if (android.os.Process.isApplicationUid(appUid)) {
                treeSet.add(java.lang.Integer.valueOf(android.os.Process.toSdkSandboxUid(appUid)));
            }
        }
        return treeSet;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.Set<android.util.Range<java.lang.Integer>> createUserAndRestrictedProfilesRanges(int i, @android.annotation.Nullable java.util.List<java.lang.String> list, @android.annotation.Nullable java.util.List<java.lang.String> list2) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        addUserToRanges(arraySet, i, list, list2);
        if (canHaveRestrictedProfile(i)) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.List<android.content.pm.UserInfo> aliveUsers = this.mUserManager.getAliveUsers();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                for (android.content.pm.UserInfo userInfo : aliveUsers) {
                    if (userInfo.isRestricted() && userInfo.restrictedProfileParentId == i) {
                        addUserToRanges(arraySet, userInfo.id, list, list2);
                    }
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        return arraySet;
    }

    @com.android.internal.annotations.VisibleForTesting
    void addUserToRanges(@android.annotation.NonNull java.util.Set<android.util.Range<java.lang.Integer>> set, int i, @android.annotation.Nullable java.util.List<java.lang.String> list, @android.annotation.Nullable java.util.List<java.lang.String> list2) {
        if (list != null) {
            java.util.Iterator<java.lang.Integer> it = getAppsUids(list, i).iterator();
            int i2 = -1;
            int i3 = -1;
            while (it.hasNext()) {
                int intValue = it.next().intValue();
                if (i2 != -1) {
                    if (intValue != i3 + 1) {
                        set.add(new android.util.Range<>(java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3)));
                    } else {
                        i3 = intValue;
                    }
                }
                i2 = intValue;
                i3 = intValue;
            }
            if (i2 != -1) {
                set.add(new android.util.Range<>(java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3)));
                return;
            }
            return;
        }
        if (list2 != null) {
            android.util.Range<java.lang.Integer> createUidRangeForUser = createUidRangeForUser(i);
            int intValue2 = createUidRangeForUser.getLower().intValue();
            java.util.Iterator<java.lang.Integer> it2 = getAppsUids(list2, i).iterator();
            while (it2.hasNext()) {
                int intValue3 = it2.next().intValue();
                if (intValue3 == intValue2) {
                    intValue2++;
                } else {
                    set.add(new android.util.Range<>(java.lang.Integer.valueOf(intValue2), java.lang.Integer.valueOf(intValue3 - 1)));
                    intValue2 = intValue3 + 1;
                }
            }
            if (intValue2 <= createUidRangeForUser.getUpper().intValue()) {
                set.add(new android.util.Range<>(java.lang.Integer.valueOf(intValue2), createUidRangeForUser.getUpper()));
                return;
            }
            return;
        }
        set.add(createUidRangeForUser(i));
    }

    private static java.util.List<android.util.Range<java.lang.Integer>> uidRangesForUser(int i, java.util.Set<android.util.Range<java.lang.Integer>> set) {
        android.util.Range<java.lang.Integer> createUidRangeForUser = createUidRangeForUser(i);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.util.Range<java.lang.Integer> range : set) {
            if (createUidRangeForUser.contains(range)) {
                arrayList.add(range);
            }
        }
        return arrayList;
    }

    public void onUserAdded(int i) {
        android.content.pm.UserInfo userInfo = this.mUserManager.getUserInfo(i);
        if (userInfo.isRestricted() && userInfo.restrictedProfileParentId == this.mUserId) {
            synchronized (this) {
                java.util.Set uids = this.mNetworkCapabilities.getUids();
                if (uids != null) {
                    try {
                        addUserToRanges(uids, i, this.mConfig.allowedApplications, this.mConfig.disallowedApplications);
                        this.mNetworkCapabilities = new android.net.NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids(uids).build();
                    } catch (java.lang.Exception e) {
                        android.util.Log.wtf(TAG, "Failed to add restricted user to owner", e);
                    }
                    if (this.mNetworkAgent != null) {
                        doSendNetworkCapabilities(this.mNetworkAgent, this.mNetworkCapabilities);
                    }
                }
                setVpnForcedLocked(this.mLockdown);
            }
        }
    }

    public void onUserRemoved(int i) {
        android.content.pm.UserInfo userInfo = this.mUserManager.getUserInfo(i);
        if (userInfo.isRestricted() && userInfo.restrictedProfileParentId == this.mUserId) {
            synchronized (this) {
                java.util.Set uids = this.mNetworkCapabilities.getUids();
                if (uids != null) {
                    try {
                        uids.removeAll(uidRangesForUser(i, uids));
                        this.mNetworkCapabilities = new android.net.NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids(uids).build();
                    } catch (java.lang.Exception e) {
                        android.util.Log.wtf(TAG, "Failed to remove restricted user to owner", e);
                    }
                    if (this.mNetworkAgent != null) {
                        doSendNetworkCapabilities(this.mNetworkAgent, this.mNetworkCapabilities);
                    }
                }
                setVpnForcedLocked(this.mLockdown);
            }
        }
    }

    public synchronized void onUserStopped() {
        setVpnForcedLocked(false);
        this.mAlwaysOn = false;
        agentDisconnect();
        this.mConnectivityManager.unregisterNetworkProvider(this.mNetworkProvider);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void setVpnForcedLocked(boolean z) {
        java.util.List<java.lang.String> arrayList;
        java.util.Set emptySet;
        if (isNullOrLegacyVpn(this.mPackage)) {
            arrayList = null;
        } else {
            arrayList = new java.util.ArrayList<>(this.mLockdownAllowlist);
            arrayList.add(this.mPackage);
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(this.mBlockedUidsAsToldToConnectivity);
        if (z) {
            java.util.Set<android.util.Range<java.lang.Integer>> createUserAndRestrictedProfilesRanges = createUserAndRestrictedProfilesRanges(this.mUserId, null, arrayList);
            emptySet = new android.util.ArraySet();
            for (android.util.Range<java.lang.Integer> range : createUserAndRestrictedProfilesRanges) {
                if (range.getLower().intValue() == 0 && range.getUpper().intValue() != 0) {
                    emptySet.add(new android.net.UidRangeParcel(1, range.getUpper().intValue()));
                } else if (range.getLower().intValue() != 0) {
                    emptySet.add(new android.net.UidRangeParcel(range.getLower().intValue(), range.getUpper().intValue()));
                }
            }
            arraySet.removeAll(emptySet);
            emptySet.removeAll(this.mBlockedUidsAsToldToConnectivity);
        } else {
            emptySet = java.util.Collections.emptySet();
        }
        setAllowOnlyVpnForUids(false, arraySet);
        setAllowOnlyVpnForUids(true, emptySet);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean setAllowOnlyVpnForUids(boolean z, java.util.Collection<android.net.UidRangeParcel> collection) {
        if (collection.size() == 0) {
            return true;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(collection.size());
        for (android.net.UidRangeParcel uidRangeParcel : collection) {
            arrayList.add(new android.util.Range(java.lang.Integer.valueOf(uidRangeParcel.start), java.lang.Integer.valueOf(uidRangeParcel.stop)));
        }
        try {
            this.mConnectivityManager.setRequireVpnForUids(z, arrayList);
            if (z) {
                this.mBlockedUidsAsToldToConnectivity.addAll(collection);
            } else {
                this.mBlockedUidsAsToldToConnectivity.removeAll(collection);
            }
            return true;
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Updating blocked=" + z + " for UIDs " + java.util.Arrays.toString(collection.toArray()) + " failed", e);
            return false;
        }
    }

    public synchronized com.android.internal.net.VpnConfig getVpnConfig() {
        enforceControlPermission();
        if (this.mConfig == null) {
            return null;
        }
        return new com.android.internal.net.VpnConfig(this.mConfig);
    }

    @java.lang.Deprecated
    public synchronized void interfaceStatusChanged(java.lang.String str, boolean z) {
        try {
            this.mObserver.interfaceStatusChanged(str, z);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public void cleanupVpnStateLocked() {
        this.mStatusIntent = null;
        resetNetworkCapabilities();
        this.mConfig = null;
        this.mInterface = null;
        this.mVpnRunner = null;
        this.mConnection = null;
        agentDisconnect();
    }

    private void enforceControlPermission() {
        this.mContext.enforceCallingPermission("android.permission.CONTROL_VPN", "Unauthorized Caller");
    }

    private void enforceControlPermissionOrInternalCaller() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_VPN", "Unauthorized Caller");
    }

    private void enforceSettingsPermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.NETWORK_SETTINGS", "Unauthorized Caller");
    }

    private class Connection implements android.content.ServiceConnection {
        private android.os.IBinder mService;

        private Connection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            this.mService = iBinder;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            this.mService = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareStatusIntent() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mStatusIntent = this.mDeps.getIntentForStatusPanel(this.mContext);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public synchronized boolean addAddress(java.lang.String str, int i) {
        if (!isCallerEstablishedOwnerLocked()) {
            return false;
        }
        boolean jniAddAddress = jniAddAddress(this.mInterface, str, i);
        doSendLinkProperties(this.mNetworkAgent, makeLinkProperties());
        return jniAddAddress;
    }

    public synchronized boolean removeAddress(java.lang.String str, int i) {
        if (!isCallerEstablishedOwnerLocked()) {
            return false;
        }
        boolean jniDelAddress = jniDelAddress(this.mInterface, str, i);
        doSendLinkProperties(this.mNetworkAgent, makeLinkProperties());
        return jniDelAddress;
    }

    public synchronized boolean setUnderlyingNetworks(@android.annotation.Nullable android.net.Network[] networkArr) {
        try {
            if (!isCallerEstablishedOwnerLocked()) {
                return false;
            }
            java.util.List<android.net.Network> list = null;
            this.mConfig.underlyingNetworks = networkArr != null ? (android.net.Network[]) java.util.Arrays.copyOf(networkArr, networkArr.length) : null;
            android.net.NetworkAgent networkAgent = this.mNetworkAgent;
            if (this.mConfig.underlyingNetworks != null) {
                list = java.util.Arrays.asList(this.mConfig.underlyingNetworks);
            }
            doSetUnderlyingNetworks(networkAgent, list);
            return true;
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized android.net.UnderlyingNetworkInfo getUnderlyingNetworkInfo() {
        if (!isRunningLocked()) {
            return null;
        }
        return new android.net.UnderlyingNetworkInfo(this.mOwnerUID, this.mInterface, new java.util.ArrayList());
    }

    public synchronized boolean appliesToUid(int i) {
        if (!isRunningLocked()) {
            return false;
        }
        java.util.Set uids = this.mNetworkCapabilities.getUids();
        if (uids == null) {
            return true;
        }
        java.util.Iterator it = uids.iterator();
        while (it.hasNext()) {
            if (((android.util.Range) it.next()).contains((android.util.Range) java.lang.Integer.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    public synchronized int getActiveVpnType() {
        if (!this.mNetworkInfo.isConnectedOrConnecting()) {
            return -1;
        }
        if (this.mVpnRunner == null) {
            return 1;
        }
        return isIkev2VpnRunner() ? 2 : 3;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void updateAlwaysOnNotification(android.net.NetworkInfo.DetailedState detailedState) {
        boolean z = this.mAlwaysOn && detailedState != android.net.NetworkInfo.DetailedState.CONNECTED;
        android.os.UserHandle of = android.os.UserHandle.of(this.mUserId);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.app.NotificationManager notificationManager = (android.app.NotificationManager) this.mUserIdContext.getSystemService(android.app.NotificationManager.class);
            if (!z) {
                notificationManager.cancel(TAG, 17);
                return;
            }
            android.content.Intent intent = new android.content.Intent();
            intent.setComponent(android.content.ComponentName.unflattenFromString(this.mContext.getString(android.R.string.config_credentialManagerReceiverComponent)));
            intent.putExtra("lockdown", this.mLockdown);
            intent.addFlags(268435456);
            notificationManager.notify(TAG, 17, new android.app.Notification.Builder(this.mContext, NETWORKTYPE).setSmallIcon(android.R.drawable.view_accessibility_focused).setContentTitle(this.mContext.getString(android.R.string.use_a_different_app)).setContentText(this.mContext.getString(android.R.string.usb_unsupported_audio_accessory_message)).setContentIntent(this.mSystemServices.pendingIntentGetActivityAsUser(intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD, of)).setCategory("sys").setVisibility(1).setOngoing(true).setColor(this.mContext.getColor(android.R.color.system_notification_accent_color)).build());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class SystemServices {
        private final android.content.Context mContext;

        public SystemServices(@android.annotation.NonNull android.content.Context context) {
            this.mContext = context;
        }

        public android.app.PendingIntent pendingIntentGetActivityAsUser(android.content.Intent intent, int i, android.os.UserHandle userHandle) {
            return android.app.PendingIntent.getActivity(this.mContext.createContextAsUser(userHandle, 0), 0, intent, i);
        }

        public void settingsSecurePutStringForUser(java.lang.String str, java.lang.String str2, int i) {
            android.provider.Settings.Secure.putString(getContentResolverAsUser(i), str, str2);
        }

        public void settingsSecurePutIntForUser(java.lang.String str, int i, int i2) {
            android.provider.Settings.Secure.putInt(getContentResolverAsUser(i2), str, i);
        }

        public java.lang.String settingsSecureGetStringForUser(java.lang.String str, int i) {
            return android.provider.Settings.Secure.getString(getContentResolverAsUser(i), str);
        }

        public int settingsSecureGetIntForUser(java.lang.String str, int i, int i2) {
            return android.provider.Settings.Secure.getInt(getContentResolverAsUser(i2), str, i);
        }

        private android.content.ContentResolver getContentResolverAsUser(int i) {
            return this.mContext.createContextAsUser(android.os.UserHandle.of(i), 0).getContentResolver();
        }
    }

    private void enforceNotRestrictedUser() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mUserManager.getUserInfo(this.mUserId).isRestricted()) {
                throw new java.lang.SecurityException("Restricted users cannot configure VPNs");
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void startLegacyVpn(com.android.internal.net.VpnProfile vpnProfile) {
        enforceControlPermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            startLegacyVpnPrivileged(vpnProfile);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private java.lang.String makeKeystoreEngineGrantString(java.lang.String str) {
        if (str == null) {
            return null;
        }
        android.security.KeyStore2 keyStore2 = android.security.KeyStore2.getInstance();
        android.system.keystore2.KeyDescriptor keyDescriptor = new android.system.keystore2.KeyDescriptor();
        keyDescriptor.domain = 0;
        keyDescriptor.nspace = -1L;
        keyDescriptor.alias = str;
        keyDescriptor.blob = null;
        try {
            return android.security.KeyStore2.makeKeystoreEngineGrantString(keyStore2.grant(keyDescriptor, 1016, 260).nspace);
        } catch (android.security.KeyStoreException e) {
            android.util.Log.e(TAG, "Failed to get grant for keystore key.", e);
            throw new java.lang.IllegalStateException("Failed to get grant for keystore key.", e);
        }
    }

    private java.lang.String getCaCertificateFromKeystoreAsPem(@android.annotation.NonNull java.security.KeyStore keyStore, @android.annotation.NonNull java.lang.String str) throws java.security.KeyStoreException, java.io.IOException, java.security.cert.CertificateEncodingException {
        if (keyStore.isCertificateEntry(str)) {
            java.security.cert.Certificate certificate = keyStore.getCertificate(str);
            if (certificate == null) {
                return null;
            }
            return new java.lang.String(android.security.Credentials.convertToPem(new java.security.cert.Certificate[]{certificate}), java.nio.charset.StandardCharsets.UTF_8);
        }
        java.security.cert.Certificate[] certificateChain = keyStore.getCertificateChain(str);
        if (certificateChain == null || certificateChain.length <= 1) {
            return null;
        }
        return new java.lang.String(android.security.Credentials.convertToPem((java.security.cert.Certificate[]) java.util.Arrays.copyOfRange(certificateChain, 1, certificateChain.length)), java.nio.charset.StandardCharsets.UTF_8);
    }

    public void startLegacyVpnPrivileged(com.android.internal.net.VpnProfile vpnProfile) {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        com.android.internal.net.VpnProfile clone = vpnProfile.clone();
        if (this.mUserManager.getUserInfo(this.mUserId).isRestricted() || this.mUserManager.hasUserRestriction("no_config_vpn", new android.os.UserHandle(this.mUserId))) {
            throw new java.lang.SecurityException("Restricted users cannot establish VPNs");
        }
        try {
            java.security.KeyStore keyStore = java.security.KeyStore.getInstance("AndroidKeyStore");
            java.lang.String str4 = null;
            keyStore.load(null);
            java.lang.String str5 = "";
            if (clone.ipsecUserCert.isEmpty()) {
                str = "";
                str2 = str;
            } else {
                str = clone.ipsecUserCert;
                java.security.cert.Certificate certificate = keyStore.getCertificate(clone.ipsecUserCert);
                if (certificate == null) {
                    str2 = null;
                } else {
                    str2 = new java.lang.String(android.security.Credentials.convertToPem(new java.security.cert.Certificate[]{certificate}), java.nio.charset.StandardCharsets.UTF_8);
                }
            }
            if (clone.ipsecCaCert.isEmpty()) {
                str3 = "";
            } else {
                str3 = getCaCertificateFromKeystoreAsPem(keyStore, clone.ipsecCaCert);
            }
            if (!clone.ipsecServerCert.isEmpty()) {
                java.security.cert.Certificate certificate2 = keyStore.getCertificate(clone.ipsecServerCert);
                if (certificate2 != null) {
                    str4 = new java.lang.String(android.security.Credentials.convertToPem(new java.security.cert.Certificate[]{certificate2}), java.nio.charset.StandardCharsets.UTF_8);
                }
                str5 = str4;
            }
            if (str2 == null || str3 == null || str5 == null) {
                throw new java.lang.IllegalStateException("Cannot load credentials");
            }
            switch (clone.type) {
                case 6:
                    break;
                case 7:
                    clone.ipsecSecret = android.net.Ikev2VpnProfile.encodeForIpsecSecret(clone.ipsecSecret.getBytes());
                    clone.setAllowedAlgorithms(android.net.Ikev2VpnProfile.DEFAULT_ALGORITHMS);
                    startVpnProfilePrivileged(clone, "[Legacy VPN]");
                    return;
                case 8:
                    clone.ipsecSecret = "KEYSTORE_ALIAS:" + str;
                    clone.ipsecUserCert = str2;
                    break;
                case 9:
                    startVpnProfilePrivileged(clone, "[Legacy VPN]");
                    return;
                default:
                    throw new java.lang.UnsupportedOperationException("Legacy VPN is deprecated");
            }
            clone.ipsecCaCert = str3;
            clone.setAllowedAlgorithms(android.net.Ikev2VpnProfile.DEFAULT_ALGORITHMS);
            startVpnProfilePrivileged(clone, "[Legacy VPN]");
        } catch (java.io.IOException | java.security.KeyStoreException | java.security.NoSuchAlgorithmException | java.security.cert.CertificateException e) {
            throw new java.lang.IllegalStateException("Failed to load credentials from AndroidKeyStore", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSettingsVpnLocked() {
        return this.mVpnRunner != null && "[Legacy VPN]".equals(this.mPackage);
    }

    public synchronized void stopVpnRunnerPrivileged() {
        if (isSettingsVpnLocked()) {
            this.mVpnRunner.exit();
        }
    }

    public synchronized com.android.internal.net.LegacyVpnInfo getLegacyVpnInfo() {
        enforceControlPermission();
        return getLegacyVpnInfoPrivileged();
    }

    private synchronized com.android.internal.net.LegacyVpnInfo getLegacyVpnInfoPrivileged() {
        if (!isSettingsVpnLocked()) {
            return null;
        }
        com.android.internal.net.LegacyVpnInfo legacyVpnInfo = new com.android.internal.net.LegacyVpnInfo();
        legacyVpnInfo.key = this.mConfig.user;
        legacyVpnInfo.state = this.mLegacyState;
        if (this.mNetworkInfo.isConnected()) {
            legacyVpnInfo.intent = this.mStatusIntent;
        }
        return legacyVpnInfo;
    }

    public synchronized com.android.internal.net.VpnConfig getLegacyVpnConfig() {
        if (!isSettingsVpnLocked()) {
            return null;
        }
        return this.mConfig;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public synchronized android.net.NetworkCapabilities getRedactedNetworkCapabilities(android.net.NetworkCapabilities networkCapabilities) {
        if (networkCapabilities == null) {
            return null;
        }
        return this.mConnectivityManager.getRedactedNetworkCapabilitiesForPackage(networkCapabilities, this.mOwnerUID, this.mPackage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public synchronized android.net.LinkProperties getRedactedLinkProperties(android.net.LinkProperties linkProperties) {
        if (linkProperties == null) {
            return null;
        }
        return this.mConnectivityManager.getRedactedLinkPropertiesForPackage(linkProperties, this.mOwnerUID, this.mPackage);
    }

    @com.android.internal.annotations.VisibleForTesting
    abstract class VpnRunner extends java.lang.Thread {
        protected abstract void exitVpnRunner();

        @Override // java.lang.Thread, java.lang.Runnable
        public abstract void run();

        protected VpnRunner(java.lang.String str) {
            super(str);
        }

        protected final void exit() {
            synchronized (com.android.server.connectivity.Vpn.this) {
                exitVpnRunner();
                com.android.server.connectivity.Vpn.this.cleanupVpnStateLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isIPv6Only(java.util.List<android.net.LinkAddress> list) {
        boolean z = false;
        boolean z2 = false;
        for (android.net.LinkAddress linkAddress : list) {
            z |= linkAddress.isIpv6();
            z2 |= linkAddress.isIpv4();
        }
        return z && !z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVpnNetworkPreference(final java.lang.String str, final java.util.Set<android.util.Range<java.lang.Integer>> set) {
        com.android.net.module.util.BinderUtils.withCleanCallingIdentity(new com.android.net.module.util.BinderUtils.ThrowingRunnable() { // from class: com.android.server.connectivity.Vpn$$ExternalSyntheticLambda0
            public final void run() {
                com.android.server.connectivity.Vpn.this.lambda$setVpnNetworkPreference$0(str, set);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setVpnNetworkPreference$0(java.lang.String str, java.util.Set set) throws java.lang.RuntimeException {
        this.mConnectivityManager.setVpnDefaultForUids(str, set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearVpnNetworkPreference(final java.lang.String str) {
        com.android.net.module.util.BinderUtils.withCleanCallingIdentity(new com.android.net.module.util.BinderUtils.ThrowingRunnable() { // from class: com.android.server.connectivity.Vpn$$ExternalSyntheticLambda1
            public final void run() {
                com.android.server.connectivity.Vpn.this.lambda$clearVpnNetworkPreference$1(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearVpnNetworkPreference$1(java.lang.String str) throws java.lang.RuntimeException {
        this.mConnectivityManager.setVpnDefaultForUids(str, java.util.Collections.EMPTY_LIST);
    }

    class IkeV2VpnRunner extends com.android.server.connectivity.Vpn.VpnRunner implements com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback {
        private static final long NETWORK_LOST_TIMEOUT_MS = 5000;

        @android.annotation.NonNull
        private static final java.lang.String TAG = "IkeV2VpnRunner";

        @android.annotation.Nullable
        private android.net.Network mActiveNetwork;
        private android.telephony.CarrierConfigManager.CarrierConfigChangeListener mCarrierConfigChangeListener;
        private int mCurrentToken;

        @android.annotation.NonNull
        private final java.util.concurrent.ScheduledThreadPoolExecutor mExecutor;

        @android.annotation.Nullable
        private android.net.ipsec.ike.IkeSessionConnectionInfo mIkeConnectionInfo;

        @android.annotation.NonNull
        private final android.net.IpSecManager mIpSecManager;
        private boolean mIsRunning;
        private boolean mMobikeEnabled;

        @android.annotation.NonNull
        private final android.net.ConnectivityManager.NetworkCallback mNetworkCallback;

        @android.annotation.NonNull
        private final android.net.Ikev2VpnProfile mProfile;
        private int mRetryCount;

        @android.annotation.Nullable
        private java.util.concurrent.ScheduledFuture<?> mScheduledHandleDataStallFuture;

        @android.annotation.Nullable
        private java.util.concurrent.ScheduledFuture<?> mScheduledHandleNetworkLostFuture;

        @android.annotation.Nullable
        private java.util.concurrent.ScheduledFuture<?> mScheduledHandleRetryIkeSessionFuture;

        @android.annotation.Nullable
        private com.android.server.connectivity.Vpn.IkeSessionWrapper mSession;
        private final java.lang.String mSessionKey;

        @android.annotation.Nullable
        private android.net.IpSecManager.IpSecTunnelInterface mTunnelIface;

        @android.annotation.Nullable
        private android.net.LinkProperties mUnderlyingLinkProperties;

        @android.annotation.Nullable
        private android.net.NetworkCapabilities mUnderlyingNetworkCapabilities;

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
        int mValidationFailRetryCount;

        IkeV2VpnRunner(@android.annotation.NonNull android.net.Ikev2VpnProfile ikev2VpnProfile, @android.annotation.NonNull java.util.concurrent.ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
            super(TAG);
            this.mIsRunning = true;
            this.mCurrentToken = -1;
            this.mMobikeEnabled = false;
            this.mValidationFailRetryCount = 0;
            this.mRetryCount = 0;
            this.mCarrierConfigChangeListener = new android.telephony.CarrierConfigManager.CarrierConfigChangeListener() { // from class: com.android.server.connectivity.Vpn.IkeV2VpnRunner.1
                @Override // android.telephony.CarrierConfigManager.CarrierConfigChangeListener
                public void onCarrierConfigChanged(int i, int i2, int i3, int i4) {
                    com.android.server.connectivity.Vpn.this.mEventChanges.log("[CarrierConfig] Changed on slot " + i + " subId=" + i2 + " carrerId=" + i3 + " specificCarrierId=" + i4);
                    synchronized (com.android.server.connectivity.Vpn.this) {
                        try {
                            com.android.server.connectivity.Vpn.this.mCachedCarrierConfigInfoPerSubId.remove(i2);
                            if (com.android.server.connectivity.Vpn.this.mVpnRunner != com.android.server.connectivity.Vpn.IkeV2VpnRunner.this) {
                                return;
                            }
                            com.android.server.connectivity.Vpn.IkeV2VpnRunner.this.maybeMigrateIkeSessionAndUpdateVpnTransportInfo(com.android.server.connectivity.Vpn.IkeV2VpnRunner.this.mActiveNetwork);
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }
            };
            this.mProfile = ikev2VpnProfile;
            this.mExecutor = scheduledThreadPoolExecutor;
            this.mIpSecManager = (android.net.IpSecManager) com.android.server.connectivity.Vpn.this.mContext.getSystemService(android.net.INetd.IPSEC_INTERFACE_PREFIX);
            this.mNetworkCallback = new com.android.server.connectivity.VpnIkev2Utils.Ikev2VpnNetworkCallback(TAG, this, this.mExecutor);
            this.mSessionKey = java.util.UUID.randomUUID().toString();
            android.util.Log.d(TAG, "Generate session key = " + this.mSessionKey);
            this.mExecutor.setRemoveOnCancelPolicy(true);
            this.mExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
            this.mExecutor.setRejectedExecutionHandler(new java.util.concurrent.RejectedExecutionHandler() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda3
                @Override // java.util.concurrent.RejectedExecutionHandler
                public final void rejectedExecution(java.lang.Runnable runnable, java.util.concurrent.ThreadPoolExecutor threadPoolExecutor) {
                    com.android.server.connectivity.Vpn.IkeV2VpnRunner.lambda$new$0(runnable, threadPoolExecutor);
                }
            });
            com.android.server.connectivity.Vpn.this.setVpnNetworkPreference(this.mSessionKey, com.android.server.connectivity.Vpn.this.createUserAndRestrictedProfilesRanges(com.android.server.connectivity.Vpn.this.mUserId, com.android.server.connectivity.Vpn.this.mConfig.allowedApplications, com.android.server.connectivity.Vpn.this.mConfig.disallowedApplications));
            com.android.server.connectivity.Vpn.this.mCarrierConfigManager.registerCarrierConfigChangeListener(this.mExecutor, this.mCarrierConfigChangeListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$new$0(java.lang.Runnable runnable, java.util.concurrent.ThreadPoolExecutor threadPoolExecutor) {
            android.util.Log.d(TAG, "Runnable " + runnable + " rejected by the mExecutor");
        }

        @Override // com.android.server.connectivity.Vpn.VpnRunner, java.lang.Thread, java.lang.Runnable
        public void run() {
            if (this.mProfile.isRestrictedToTestNetworks()) {
                com.android.server.connectivity.Vpn.this.mConnectivityManager.requestNetwork(new android.net.NetworkRequest.Builder().clearCapabilities().addTransportType(7).addCapability(15).build(), this.mNetworkCallback);
            } else {
                com.android.server.connectivity.Vpn.this.mConnectivityManager.registerSystemDefaultNetworkCallback(this.mNetworkCallback, new android.os.Handler(com.android.server.connectivity.Vpn.this.mLooper));
            }
        }

        private boolean isActiveNetwork(@android.annotation.Nullable android.net.Network network) {
            return java.util.Objects.equals(this.mActiveNetwork, network) && this.mIsRunning;
        }

        private boolean isActiveToken(int i) {
            return this.mCurrentToken == i && this.mIsRunning;
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onIkeOpened(int i, @android.annotation.NonNull android.net.ipsec.ike.IkeSessionConfiguration ikeSessionConfiguration) {
            if (!isActiveToken(i)) {
                com.android.server.connectivity.Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onIkeOpened obsolete token=" + i);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("onIkeOpened called for obsolete token ");
                sb.append(i);
                android.util.Log.d(TAG, sb.toString());
                return;
            }
            this.mMobikeEnabled = ikeSessionConfiguration.isIkeExtensionEnabled(2);
            android.net.ipsec.ike.IkeSessionConnectionInfo ikeSessionConnectionInfo = ikeSessionConfiguration.getIkeSessionConnectionInfo();
            com.android.server.connectivity.Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onIkeOpened token=" + i + ", localAddr=" + ikeSessionConnectionInfo.getLocalAddress() + ", network=" + ikeSessionConnectionInfo.getNetwork() + ", mobikeEnabled= " + this.mMobikeEnabled);
            onIkeConnectionInfoChanged(i, ikeSessionConnectionInfo);
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onIkeConnectionInfoChanged(int i, @android.annotation.NonNull android.net.ipsec.ike.IkeSessionConnectionInfo ikeSessionConnectionInfo) {
            if (!isActiveToken(i)) {
                com.android.server.connectivity.Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onIkeConnectionInfoChanged obsolete token=" + i);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("onIkeConnectionInfoChanged called for obsolete token ");
                sb.append(i);
                android.util.Log.d(TAG, sb.toString());
                return;
            }
            com.android.server.connectivity.Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onIkeConnectionInfoChanged token=" + i + ", localAddr=" + ikeSessionConnectionInfo.getLocalAddress() + ", network=" + ikeSessionConnectionInfo.getNetwork());
            this.mIkeConnectionInfo = ikeSessionConnectionInfo;
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onChildOpened(int i, @android.annotation.NonNull android.net.ipsec.ike.ChildSessionConfiguration childSessionConfiguration) {
            if (!isActiveToken(i)) {
                com.android.server.connectivity.Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onChildOpened obsolete token=" + i);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("onChildOpened called for obsolete token ");
                sb.append(i);
                android.util.Log.d(TAG, sb.toString());
                return;
            }
            com.android.server.connectivity.Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onChildOpened token=" + i + ", addr=" + android.text.TextUtils.join(", ", childSessionConfiguration.getInternalAddresses()) + " dns=" + android.text.TextUtils.join(", ", childSessionConfiguration.getInternalDnsServers()));
            try {
                java.lang.String interfaceName = this.mTunnelIface.getInterfaceName();
                java.util.List<android.net.LinkAddress> internalAddresses = childSessionConfiguration.getInternalAddresses();
                java.util.ArrayList arrayList = new java.util.ArrayList();
                int calculateVpnMtu = calculateVpnMtu();
                if (com.android.server.connectivity.Vpn.isIPv6Only(internalAddresses) && calculateVpnMtu < 1280) {
                    onSessionLost(i, new android.net.ipsec.ike.exceptions.IkeIOException(new java.io.IOException("No valid addresses for MTU < 1280")));
                    return;
                }
                java.util.Collection<android.net.RouteInfo> routesFromTrafficSelectors = com.android.server.connectivity.VpnIkev2Utils.getRoutesFromTrafficSelectors(childSessionConfiguration.getOutboundTrafficSelectors());
                for (android.net.LinkAddress linkAddress : internalAddresses) {
                    this.mTunnelIface.addAddress(linkAddress.getAddress(), linkAddress.getPrefixLength());
                }
                java.util.Iterator it = childSessionConfiguration.getInternalDnsServers().iterator();
                while (it.hasNext()) {
                    arrayList.add(((java.net.InetAddress) it.next()).getHostAddress());
                }
                android.net.Network network = this.mIkeConnectionInfo.getNetwork();
                synchronized (com.android.server.connectivity.Vpn.this) {
                    try {
                        if (com.android.server.connectivity.Vpn.this.mVpnRunner != this) {
                            return;
                        }
                        com.android.server.connectivity.Vpn.this.mInterface = interfaceName;
                        com.android.server.connectivity.Vpn.this.mConfig.mtu = calculateVpnMtu;
                        com.android.server.connectivity.Vpn.this.mConfig.interfaze = com.android.server.connectivity.Vpn.this.mInterface;
                        com.android.server.connectivity.Vpn.this.mConfig.addresses.clear();
                        com.android.server.connectivity.Vpn.this.mConfig.addresses.addAll(internalAddresses);
                        com.android.server.connectivity.Vpn.this.mConfig.routes.clear();
                        com.android.server.connectivity.Vpn.this.mConfig.routes.addAll(routesFromTrafficSelectors);
                        if (com.android.server.connectivity.Vpn.this.mConfig.dnsServers == null) {
                            com.android.server.connectivity.Vpn.this.mConfig.dnsServers = new java.util.ArrayList();
                        }
                        com.android.server.connectivity.Vpn.this.mConfig.dnsServers.clear();
                        com.android.server.connectivity.Vpn.this.mConfig.dnsServers.addAll(arrayList);
                        com.android.server.connectivity.Vpn.this.mConfig.underlyingNetworks = new android.net.Network[]{network};
                        android.net.NetworkAgent networkAgent = com.android.server.connectivity.Vpn.this.mNetworkAgent;
                        if (networkAgent == null) {
                            if (com.android.server.connectivity.Vpn.this.isSettingsVpnLocked()) {
                                com.android.server.connectivity.Vpn.this.prepareStatusIntent();
                            }
                            com.android.server.connectivity.Vpn.this.agentConnect(new com.android.server.connectivity.Vpn.ValidationStatusCallback() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda4
                                @Override // com.android.server.connectivity.Vpn.ValidationStatusCallback
                                public final void onValidationStatus(int i2) {
                                    com.android.server.connectivity.Vpn.IkeV2VpnRunner.this.onValidationStatus(i2);
                                }
                            });
                        } else {
                            com.android.server.connectivity.Vpn.doSendLinkProperties(networkAgent, com.android.server.connectivity.Vpn.this.makeLinkProperties());
                            this.mRetryCount = 0;
                        }
                    } finally {
                    }
                }
            } catch (java.lang.Exception e) {
                android.util.Log.d(TAG, "Error in ChildOpened for token " + i, e);
                onSessionLost(i, e);
            }
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onChildTransformCreated(int i, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform, int i2) {
            if (!isActiveToken(i)) {
                com.android.server.connectivity.Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onChildTransformCreated obsolete token=" + i);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("ChildTransformCreated for obsolete token ");
                sb.append(i);
                android.util.Log.d(TAG, sb.toString());
                return;
            }
            com.android.server.connectivity.Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onChildTransformCreated token=" + i + ", direction=" + i2 + ", transform=" + ipSecTransform);
            try {
                this.mTunnelIface.setUnderlyingNetwork(this.mIkeConnectionInfo.getNetwork());
                this.mIpSecManager.applyTunnelModeTransform(this.mTunnelIface, i2, ipSecTransform);
            } catch (java.io.IOException | java.lang.IllegalArgumentException e) {
                android.util.Log.d(TAG, "Transform application failed for token " + i, e);
                onSessionLost(i, e);
            }
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onChildMigrated(int i, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform2) {
            if (!isActiveToken(i)) {
                com.android.server.connectivity.Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onChildMigrated obsolete token=" + i);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("onChildMigrated for obsolete token ");
                sb.append(i);
                android.util.Log.d(TAG, sb.toString());
                return;
            }
            com.android.server.connectivity.Vpn.this.mEventChanges.log("[IKEEvent-" + this.mSessionKey + "] onChildMigrated token=" + i + ", in=" + ipSecTransform + ", out=" + ipSecTransform2);
            android.net.Network network = this.mIkeConnectionInfo.getNetwork();
            try {
                synchronized (com.android.server.connectivity.Vpn.this) {
                    try {
                        if (com.android.server.connectivity.Vpn.this.mVpnRunner != this) {
                            return;
                        }
                        android.net.LinkProperties makeLinkProperties = com.android.server.connectivity.Vpn.this.makeLinkProperties();
                        com.android.server.connectivity.Vpn.this.mConfig.underlyingNetworks = new android.net.Network[]{network};
                        com.android.server.connectivity.Vpn.this.mConfig.mtu = calculateVpnMtu();
                        android.net.LinkProperties makeLinkProperties2 = com.android.server.connectivity.Vpn.this.makeLinkProperties();
                        if (makeLinkProperties2.getLinkAddresses().isEmpty()) {
                            onSessionLost(i, new android.net.ipsec.ike.exceptions.IkeIOException(new java.io.IOException("No valid addresses for MTU < 1280")));
                            return;
                        }
                        java.util.HashSet<android.net.LinkAddress> hashSet = new java.util.HashSet(makeLinkProperties.getLinkAddresses());
                        hashSet.removeAll(makeLinkProperties2.getLinkAddresses());
                        if (!hashSet.isEmpty()) {
                            com.android.server.connectivity.Vpn.this.startNewNetworkAgent(com.android.server.connectivity.Vpn.this.mNetworkAgent, "MTU too low for IPv6; restarting network agent");
                            for (android.net.LinkAddress linkAddress : hashSet) {
                                this.mTunnelIface.removeAddress(linkAddress.getAddress(), linkAddress.getPrefixLength());
                            }
                        } else if (!makeLinkProperties2.equals(makeLinkProperties)) {
                            com.android.server.connectivity.Vpn.doSendLinkProperties(com.android.server.connectivity.Vpn.this.mNetworkAgent, makeLinkProperties2);
                        }
                        this.mTunnelIface.setUnderlyingNetwork(network);
                        this.mIpSecManager.applyTunnelModeTransform(this.mTunnelIface, 0, ipSecTransform);
                        this.mIpSecManager.applyTunnelModeTransform(this.mTunnelIface, 1, ipSecTransform2);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } catch (java.io.IOException | java.lang.IllegalArgumentException e) {
                android.util.Log.d(TAG, "Transform application failed for token " + i, e);
                onSessionLost(i, e);
            }
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onDefaultNetworkChanged(@android.annotation.NonNull android.net.Network network) {
            com.android.server.connectivity.Vpn.this.mEventChanges.log("[UnderlyingNW] Default network changed to " + network);
            android.util.Log.d(TAG, "onDefaultNetworkChanged: " + network);
            cancelRetryNewIkeSessionFuture();
            cancelHandleNetworkLostTimeout();
            if (!this.mIsRunning) {
                android.util.Log.d(TAG, "onDefaultNetworkChanged after exit");
                return;
            }
            this.mActiveNetwork = network;
            this.mUnderlyingLinkProperties = null;
            this.mUnderlyingNetworkCapabilities = null;
            this.mRetryCount = 0;
        }

        @android.annotation.NonNull
        private android.net.ipsec.ike.IkeSessionParams getIkeSessionParams(@android.annotation.NonNull android.net.Network network) {
            android.net.ipsec.ike.IkeSessionParams.Builder makeIkeSessionParamsBuilder;
            android.net.ipsec.ike.IkeTunnelConnectionParams ikeTunnelConnectionParams = this.mProfile.getIkeTunnelConnectionParams();
            if (ikeTunnelConnectionParams != null) {
                makeIkeSessionParamsBuilder = new android.net.ipsec.ike.IkeSessionParams.Builder(ikeTunnelConnectionParams.getIkeSessionParams()).setNetwork(network);
            } else {
                makeIkeSessionParamsBuilder = com.android.server.connectivity.VpnIkev2Utils.makeIkeSessionParamsBuilder(com.android.server.connectivity.Vpn.this.mContext, this.mProfile, network);
            }
            if (this.mProfile.isAutomaticNattKeepaliveTimerEnabled()) {
                makeIkeSessionParamsBuilder.setNattKeepAliveDelaySeconds(guessNattKeepaliveTimerForNetwork());
            }
            if (this.mProfile.isAutomaticIpVersionSelectionEnabled()) {
                makeIkeSessionParamsBuilder.setIpVersion(guessEspIpVersionForNetwork());
                makeIkeSessionParamsBuilder.setEncapType(guessEspEncapTypeForNetwork());
            }
            return makeIkeSessionParamsBuilder.build();
        }

        @android.annotation.NonNull
        private android.net.ipsec.ike.ChildSessionParams getChildSessionParams() {
            android.net.ipsec.ike.IkeTunnelConnectionParams ikeTunnelConnectionParams = this.mProfile.getIkeTunnelConnectionParams();
            if (ikeTunnelConnectionParams != null) {
                return ikeTunnelConnectionParams.getTunnelModeChildSessionParams();
            }
            return com.android.server.connectivity.VpnIkev2Utils.buildChildSessionParams(this.mProfile.getAllowedAlgorithms());
        }

        private int calculateVpnMtu() {
            android.net.Network network = this.mIkeConnectionInfo.getNetwork();
            android.net.LinkProperties linkProperties = com.android.server.connectivity.Vpn.this.mConnectivityManager.getLinkProperties(network);
            if (network == null || linkProperties == null) {
                return this.mProfile.getMaxMtu();
            }
            int mtu = linkProperties.getMtu();
            if (mtu == 0) {
                try {
                    mtu = com.android.server.connectivity.Vpn.this.mDeps.getJavaNetworkInterfaceMtu(linkProperties.getInterfaceName(), this.mProfile.getMaxMtu());
                } catch (java.net.SocketException e) {
                    android.util.Log.d(TAG, "Got a SocketException when getting MTU from kernel: " + e);
                    return this.mProfile.getMaxMtu();
                }
            }
            return com.android.server.connectivity.Vpn.this.mDeps.calculateVpnMtu(getChildSessionParams().getSaProposals(), this.mProfile.getMaxMtu(), mtu, this.mIkeConnectionInfo.getLocalAddress() instanceof java.net.Inet4Address);
        }

        private void startOrMigrateIkeSession(@android.annotation.Nullable android.net.Network network) {
            synchronized (com.android.server.connectivity.Vpn.this) {
                try {
                    if (com.android.server.connectivity.Vpn.this.mVpnRunner != this) {
                        return;
                    }
                    com.android.server.connectivity.Vpn.this.setVpnNetworkPreference(this.mSessionKey, com.android.server.connectivity.Vpn.this.createUserAndRestrictedProfilesRanges(com.android.server.connectivity.Vpn.this.mUserId, com.android.server.connectivity.Vpn.this.mConfig.allowedApplications, com.android.server.connectivity.Vpn.this.mConfig.disallowedApplications));
                    if (network == null) {
                        android.util.Log.d(TAG, "There is no active network for starting an IKE session");
                        return;
                    }
                    java.util.List singletonList = java.util.Collections.singletonList(network);
                    if (!singletonList.equals(com.android.server.connectivity.Vpn.this.mNetworkCapabilities.getUnderlyingNetworks())) {
                        com.android.server.connectivity.Vpn.this.mNetworkCapabilities = new android.net.NetworkCapabilities.Builder(com.android.server.connectivity.Vpn.this.mNetworkCapabilities).setUnderlyingNetworks(singletonList).build();
                        if (com.android.server.connectivity.Vpn.this.mNetworkAgent != null) {
                            com.android.server.connectivity.Vpn.this.doSetUnderlyingNetworks(com.android.server.connectivity.Vpn.this.mNetworkAgent, singletonList);
                        }
                    }
                    if (maybeMigrateIkeSessionAndUpdateVpnTransportInfo(network)) {
                        return;
                    }
                    startIkeSession(network);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private int guessEspIpVersionForNetwork() {
            if (this.mUnderlyingNetworkCapabilities.getTransportInfo() instanceof android.net.vcn.VcnTransportInfo) {
                android.util.Log.d(TAG, "Running over VCN, esp IP version is auto");
                return 0;
            }
            com.android.server.connectivity.Vpn.CarrierConfigInfo carrierConfigForUnderlyingNetwork = getCarrierConfigForUnderlyingNetwork();
            int i = carrierConfigForUnderlyingNetwork != null ? carrierConfigForUnderlyingNetwork.ipVersion : 0;
            if (carrierConfigForUnderlyingNetwork != null) {
                android.util.Log.d(TAG, "Get customized IP version (" + i + ") on SIM (mccmnc=" + carrierConfigForUnderlyingNetwork.mccMnc + ")");
            }
            return i;
        }

        private int guessEspEncapTypeForNetwork() {
            if (this.mUnderlyingNetworkCapabilities.getTransportInfo() instanceof android.net.vcn.VcnTransportInfo) {
                android.util.Log.d(TAG, "Running over VCN, encap type is auto");
                return 0;
            }
            com.android.server.connectivity.Vpn.CarrierConfigInfo carrierConfigForUnderlyingNetwork = getCarrierConfigForUnderlyingNetwork();
            int i = carrierConfigForUnderlyingNetwork != null ? carrierConfigForUnderlyingNetwork.encapType : 0;
            if (carrierConfigForUnderlyingNetwork != null) {
                android.util.Log.d(TAG, "Get customized encap type (" + i + ") on SIM (mccmnc=" + carrierConfigForUnderlyingNetwork.mccMnc + ")");
            }
            return i;
        }

        private int guessNattKeepaliveTimerForNetwork() {
            android.net.vcn.VcnTransportInfo transportInfo = this.mUnderlyingNetworkCapabilities.getTransportInfo();
            if (transportInfo instanceof android.net.vcn.VcnTransportInfo) {
                int minUdpPort4500NatTimeoutSeconds = transportInfo.getMinUdpPort4500NatTimeoutSeconds();
                android.util.Log.d(TAG, "Running over VCN, keepalive timer : " + minUdpPort4500NatTimeoutSeconds + "s");
                if (-1 != minUdpPort4500NatTimeoutSeconds) {
                    return minUdpPort4500NatTimeoutSeconds;
                }
            }
            com.android.server.connectivity.Vpn.CarrierConfigInfo carrierConfigForUnderlyingNetwork = getCarrierConfigForUnderlyingNetwork();
            int i = carrierConfigForUnderlyingNetwork != null ? carrierConfigForUnderlyingNetwork.keepaliveDelaySec : 30;
            if (carrierConfigForUnderlyingNetwork != null) {
                android.util.Log.d(TAG, "Get customized keepalive (" + i + "s) on SIM (mccmnc=" + carrierConfigForUnderlyingNetwork.mccMnc + ")");
            }
            return i;
        }

        @android.annotation.Nullable
        private com.android.server.connectivity.Vpn.CarrierConfigInfo getCarrierConfigForUnderlyingNetwork() {
            int cellSubIdForNetworkCapabilities = com.android.server.connectivity.Vpn.getCellSubIdForNetworkCapabilities(this.mUnderlyingNetworkCapabilities);
            if (cellSubIdForNetworkCapabilities == -1) {
                android.util.Log.d(TAG, "Underlying network is not a cellular network");
                return null;
            }
            synchronized (com.android.server.connectivity.Vpn.this) {
                try {
                    if (com.android.server.connectivity.Vpn.this.mCachedCarrierConfigInfoPerSubId.contains(cellSubIdForNetworkCapabilities)) {
                        android.util.Log.d(TAG, "Get cached config");
                        return (com.android.server.connectivity.Vpn.CarrierConfigInfo) com.android.server.connectivity.Vpn.this.mCachedCarrierConfigInfoPerSubId.get(cellSubIdForNetworkCapabilities);
                    }
                    android.telephony.TelephonyManager createForSubscriptionId = com.android.server.connectivity.Vpn.this.mTelephonyManager.createForSubscriptionId(cellSubIdForNetworkCapabilities);
                    if (createForSubscriptionId.getSimApplicationState() != 10) {
                        android.util.Log.d(TAG, "SIM card is not ready on sub " + cellSubIdForNetworkCapabilities);
                        return null;
                    }
                    android.os.PersistableBundle configForSubId = com.android.server.connectivity.Vpn.this.mCarrierConfigManager.getConfigForSubId(cellSubIdForNetworkCapabilities);
                    if (!android.telephony.CarrierConfigManager.isConfigForIdentifiedCarrier(configForSubId)) {
                        return null;
                    }
                    com.android.server.connectivity.Vpn.CarrierConfigInfo buildCarrierConfigInfo = buildCarrierConfigInfo(createForSubscriptionId.getSimOperator(cellSubIdForNetworkCapabilities), configForSubId.getInt("min_udp_port_4500_nat_timeout_sec_int"), configForSubId.getInt("preferred_ike_protocol_int", -1));
                    synchronized (com.android.server.connectivity.Vpn.this) {
                        com.android.server.connectivity.Vpn.this.mCachedCarrierConfigInfoPerSubId.put(cellSubIdForNetworkCapabilities, buildCarrierConfigInfo);
                    }
                    return buildCarrierConfigInfo;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private com.android.server.connectivity.Vpn.CarrierConfigInfo buildCarrierConfigInfo(java.lang.String str, int i, int i2) {
            int i3 = 6;
            int i4 = 17;
            switch (i2) {
                case 0:
                    i3 = 0;
                    i4 = 0;
                    break;
                case 40:
                    i3 = 4;
                    break;
                case 60:
                    break;
                case 61:
                    i4 = -1;
                    break;
                default:
                    i3 = 4;
                    break;
            }
            return new com.android.server.connectivity.Vpn.CarrierConfigInfo(str, i, i4, i3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getOrGuessKeepaliveDelaySeconds() {
            if (this.mProfile.isAutomaticNattKeepaliveTimerEnabled()) {
                return guessNattKeepaliveTimerForNetwork();
            }
            if (this.mProfile.getIkeTunnelConnectionParams() != null) {
                return this.mProfile.getIkeTunnelConnectionParams().getIkeSessionParams().getNattKeepAliveDelaySeconds();
            }
            return 300;
        }

        boolean maybeMigrateIkeSessionAndUpdateVpnTransportInfo(@android.annotation.NonNull android.net.Network network) {
            int orGuessKeepaliveDelaySeconds = getOrGuessKeepaliveDelaySeconds();
            boolean maybeMigrateIkeSession = maybeMigrateIkeSession(network, orGuessKeepaliveDelaySeconds);
            if (maybeMigrateIkeSession) {
                updateVpnTransportInfoAndNetCap(orGuessKeepaliveDelaySeconds);
            }
            return maybeMigrateIkeSession;
        }

        public void updateVpnTransportInfoAndNetCap(int i) {
            android.net.VpnTransportInfo vpnTransportInfo;
            synchronized (com.android.server.connectivity.Vpn.this) {
                try {
                    vpnTransportInfo = new android.net.VpnTransportInfo(com.android.server.connectivity.Vpn.this.getActiveVpnType(), com.android.server.connectivity.Vpn.this.mConfig.session, com.android.server.connectivity.Vpn.this.mConfig.allowBypass && !com.android.server.connectivity.Vpn.this.mLockdown, com.android.server.connectivity.Vpn.areLongLivedTcpConnectionsExpensive(i));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (!vpnTransportInfo.equals(com.android.server.connectivity.Vpn.this.mNetworkCapabilities.getTransportInfo())) {
                com.android.server.connectivity.Vpn.this.mNetworkCapabilities = new android.net.NetworkCapabilities.Builder(com.android.server.connectivity.Vpn.this.mNetworkCapabilities).setTransportInfo(vpnTransportInfo).build();
                com.android.server.connectivity.Vpn.this.mEventChanges.log("[VPNRunner] Update agent caps " + com.android.server.connectivity.Vpn.this.mNetworkCapabilities);
                com.android.server.connectivity.Vpn.doSendNetworkCapabilities(com.android.server.connectivity.Vpn.this.mNetworkAgent, com.android.server.connectivity.Vpn.this.mNetworkCapabilities);
            }
        }

        private boolean maybeMigrateIkeSession(@android.annotation.NonNull android.net.Network network, int i) {
            int i2;
            int i3 = 0;
            if (this.mSession == null || !this.mMobikeEnabled) {
                return false;
            }
            android.util.Log.d(TAG, "Migrate IKE Session with token " + this.mCurrentToken + " to network " + network);
            if (this.mProfile.isAutomaticIpVersionSelectionEnabled()) {
                i3 = guessEspIpVersionForNetwork();
                i2 = guessEspEncapTypeForNetwork();
            } else if (this.mProfile.getIkeTunnelConnectionParams() != null) {
                i3 = this.mProfile.getIkeTunnelConnectionParams().getIkeSessionParams().getIpVersion();
                i2 = this.mProfile.getIkeTunnelConnectionParams().getIkeSessionParams().getEncapType();
            } else {
                i2 = 0;
            }
            this.mSession.setNetwork(network, i3, i2, i);
            return true;
        }

        private void startIkeSession(@android.annotation.NonNull android.net.Network network) {
            android.util.Log.d(TAG, "Start new IKE session on network " + network);
            com.android.server.connectivity.Vpn.this.mEventChanges.log("[IKE] Start IKE session over " + network);
            try {
                synchronized (com.android.server.connectivity.Vpn.this) {
                    try {
                        if (com.android.server.connectivity.Vpn.this.mVpnRunner != this) {
                            return;
                        }
                        com.android.server.connectivity.Vpn.this.mInterface = null;
                        resetIkeState();
                        java.net.InetAddress localHost = java.net.InetAddress.getLocalHost();
                        this.mTunnelIface = this.mIpSecManager.createIpSecTunnelInterface(localHost, localHost, network);
                        com.android.net.module.util.NetdUtils.setInterfaceUp(com.android.server.connectivity.Vpn.this.mNetd, this.mTunnelIface.getInterfaceName());
                        int i = this.mCurrentToken + 1;
                        this.mCurrentToken = i;
                        this.mSession = com.android.server.connectivity.Vpn.this.mIkev2SessionCreator.createIkeSession(com.android.server.connectivity.Vpn.this.mContext, getIkeSessionParams(network), getChildSessionParams(), this.mExecutor, new com.android.server.connectivity.VpnIkev2Utils.IkeSessionCallbackImpl(TAG, this, i), new com.android.server.connectivity.VpnIkev2Utils.ChildSessionCallbackImpl(TAG, this, i));
                        android.util.Log.d(TAG, "IKE session started for token " + i);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } catch (java.lang.Exception e) {
                android.util.Log.i(TAG, "Setup failed for token " + this.mCurrentToken + ". Aborting", e);
                onSessionLost(this.mCurrentToken, e);
            }
        }

        private void scheduleStartIkeSession(long j) {
            if (this.mScheduledHandleRetryIkeSessionFuture != null) {
                android.util.Log.d(TAG, "There is a pending retrying task, skip the new retrying task");
                return;
            }
            if (-1 == j) {
                com.android.server.connectivity.Vpn.Dependencies dependencies = com.android.server.connectivity.Vpn.this.mDeps;
                int i = this.mRetryCount;
                this.mRetryCount = i + 1;
                j = dependencies.getNextRetryDelayMs(i);
            }
            android.util.Log.d(TAG, "Retry new IKE session after " + j + " milliseconds.");
            this.mScheduledHandleRetryIkeSessionFuture = this.mExecutor.schedule(new java.lang.Runnable() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.connectivity.Vpn.IkeV2VpnRunner.this.lambda$scheduleStartIkeSession$1();
                }
            }, j, java.util.concurrent.TimeUnit.MILLISECONDS);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$scheduleStartIkeSession$1() {
            startOrMigrateIkeSession(this.mActiveNetwork);
            this.mScheduledHandleRetryIkeSessionFuture = null;
        }

        private boolean significantCapsChange(@android.annotation.Nullable android.net.NetworkCapabilities networkCapabilities, @android.annotation.Nullable android.net.NetworkCapabilities networkCapabilities2) {
            if (networkCapabilities == networkCapabilities2) {
                return false;
            }
            return (networkCapabilities != null && networkCapabilities2 != null && java.util.Arrays.equals(networkCapabilities.getTransportTypes(), networkCapabilities2.getTransportTypes()) && java.util.Arrays.equals(networkCapabilities.getCapabilities(), networkCapabilities2.getCapabilities()) && java.util.Arrays.equals(networkCapabilities.getEnterpriseIds(), networkCapabilities2.getEnterpriseIds()) && java.util.Objects.equals(networkCapabilities.getTransportInfo(), networkCapabilities2.getTransportInfo()) && java.util.Objects.equals(networkCapabilities.getAllowedUids(), networkCapabilities2.getAllowedUids()) && java.util.Objects.equals(networkCapabilities.getUnderlyingNetworks(), networkCapabilities2.getUnderlyingNetworks()) && java.util.Objects.equals(networkCapabilities.getNetworkSpecifier(), networkCapabilities2.getNetworkSpecifier())) ? false : true;
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onDefaultNetworkCapabilitiesChanged(@android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities) {
            if (significantCapsChange(this.mUnderlyingNetworkCapabilities, networkCapabilities)) {
                com.android.server.connectivity.Vpn.this.mEventChanges.log("[UnderlyingNW] Cap changed from " + this.mUnderlyingNetworkCapabilities + " to " + networkCapabilities);
            }
            android.net.NetworkCapabilities networkCapabilities2 = this.mUnderlyingNetworkCapabilities;
            this.mUnderlyingNetworkCapabilities = networkCapabilities;
            if (networkCapabilities2 == null || !networkCapabilities.getSubscriptionIds().equals(networkCapabilities2.getSubscriptionIds())) {
                scheduleStartIkeSession(com.android.server.connectivity.Vpn.IKE_DELAY_ON_NC_LP_CHANGE_MS);
            }
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onDefaultNetworkLinkPropertiesChanged(@android.annotation.NonNull android.net.LinkProperties linkProperties) {
            android.net.LinkProperties linkProperties2 = this.mUnderlyingLinkProperties;
            com.android.server.connectivity.Vpn.this.mEventChanges.log("[UnderlyingNW] Lp changed from " + linkProperties2 + " to " + linkProperties);
            this.mUnderlyingLinkProperties = linkProperties;
            if (linkProperties2 == null || !com.android.net.module.util.LinkPropertiesUtils.isIdenticalAllLinkAddresses(linkProperties2, linkProperties)) {
                scheduleStartIkeSession(com.android.server.connectivity.Vpn.IKE_DELAY_ON_NC_LP_CHANGE_MS);
            }
        }

        public void onValidationStatus(int i) {
            com.android.server.connectivity.Vpn.this.mEventChanges.log("[Validation] validation status " + i);
            if (i == 1) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.connectivity.Vpn.IkeV2VpnRunner.this.lambda$onValidationStatus$2();
                    }
                });
                return;
            }
            if (this.mScheduledHandleDataStallFuture != null) {
                return;
            }
            if (this.mValidationFailRetryCount == 0) {
                com.android.server.connectivity.Vpn.this.mConnectivityManager.reportNetworkConnectivity(this.mActiveNetwork, false);
            }
            if (this.mValidationFailRetryCount < 2) {
                android.util.Log.d(TAG, "Validation failed");
                java.util.concurrent.ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = this.mExecutor;
                java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.connectivity.Vpn.IkeV2VpnRunner.this.lambda$onValidationStatus$3();
                    }
                };
                com.android.server.connectivity.Vpn.Dependencies dependencies = com.android.server.connectivity.Vpn.this.mDeps;
                int i2 = this.mValidationFailRetryCount;
                this.mValidationFailRetryCount = i2 + 1;
                scheduledThreadPoolExecutor.schedule(runnable, dependencies.getValidationFailRecoveryMs(i2), java.util.concurrent.TimeUnit.MILLISECONDS);
                return;
            }
            java.util.concurrent.ScheduledThreadPoolExecutor scheduledThreadPoolExecutor2 = this.mExecutor;
            java.lang.Runnable runnable2 = new java.lang.Runnable() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.connectivity.Vpn.IkeV2VpnRunner.this.lambda$onValidationStatus$4();
                }
            };
            com.android.server.connectivity.Vpn.Dependencies dependencies2 = com.android.server.connectivity.Vpn.this.mDeps;
            int i3 = this.mValidationFailRetryCount;
            this.mValidationFailRetryCount = i3 + 1;
            this.mScheduledHandleDataStallFuture = scheduledThreadPoolExecutor2.schedule(runnable2, dependencies2.getValidationFailRecoveryMs(i3), java.util.concurrent.TimeUnit.MILLISECONDS);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onValidationStatus$2() {
            this.mValidationFailRetryCount = 0;
            if (this.mScheduledHandleDataStallFuture != null) {
                android.util.Log.d(TAG, "Recovered from stall. Cancel pending reset action.");
                this.mScheduledHandleDataStallFuture.cancel(false);
                this.mScheduledHandleDataStallFuture = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onValidationStatus$3() {
            maybeMigrateIkeSessionAndUpdateVpnTransportInfo(this.mActiveNetwork);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onValidationStatus$4() {
            if (this.mValidationFailRetryCount > 0) {
                android.util.Log.d(TAG, "Reset session to recover stalled network");
                startIkeSession(this.mActiveNetwork);
            }
            this.mScheduledHandleDataStallFuture = null;
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onDefaultNetworkLost(@android.annotation.NonNull final android.net.Network network) {
            com.android.server.connectivity.Vpn.this.mEventChanges.log("[UnderlyingNW] Network lost " + network);
            cancelRetryNewIkeSessionFuture();
            if (!isActiveNetwork(network)) {
                android.util.Log.d(TAG, "onDefaultNetworkLost called for obsolete network " + network);
                return;
            }
            this.mActiveNetwork = null;
            this.mUnderlyingNetworkCapabilities = null;
            this.mUnderlyingLinkProperties = null;
            if (this.mScheduledHandleNetworkLostFuture != null) {
                java.lang.IllegalStateException illegalStateException = new java.lang.IllegalStateException("Found a pending mScheduledHandleNetworkLostFuture");
                android.util.Log.i(TAG, "Unexpected error in onDefaultNetworkLost. Tear down session", illegalStateException);
                handleSessionLost(illegalStateException, network);
                return;
            }
            android.util.Log.d(TAG, "Schedule a delay handleSessionLost for losing network " + network + " on session with token " + this.mCurrentToken);
            final int i = this.mCurrentToken;
            this.mScheduledHandleNetworkLostFuture = this.mExecutor.schedule(new java.lang.Runnable() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.connectivity.Vpn.IkeV2VpnRunner.this.lambda$onDefaultNetworkLost$5(i, network);
                }
            }, NETWORK_LOST_TIMEOUT_MS, java.util.concurrent.TimeUnit.MILLISECONDS);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDefaultNetworkLost$5(int i, android.net.Network network) {
            if (isActiveToken(i)) {
                handleSessionLost(new android.net.ipsec.ike.exceptions.IkeNetworkLostException(network), network);
                synchronized (com.android.server.connectivity.Vpn.this) {
                    try {
                        if (com.android.server.connectivity.Vpn.this.mVpnRunner != this) {
                            return;
                        } else {
                            com.android.server.connectivity.Vpn.this.updateState(android.net.NetworkInfo.DetailedState.DISCONNECTED, "Network lost");
                        }
                    } finally {
                    }
                }
            } else {
                android.util.Log.d(TAG, "Scheduled handleSessionLost fired for obsolete token " + i);
            }
            this.mScheduledHandleNetworkLostFuture = null;
        }

        private void cancelHandleNetworkLostTimeout() {
            if (this.mScheduledHandleNetworkLostFuture != null) {
                android.util.Log.d(TAG, "Cancel the task for handling network lost timeout");
                this.mScheduledHandleNetworkLostFuture.cancel(false);
                this.mScheduledHandleNetworkLostFuture = null;
            }
        }

        private void cancelRetryNewIkeSessionFuture() {
            if (this.mScheduledHandleRetryIkeSessionFuture != null) {
                android.util.Log.d(TAG, "Cancel the task for handling new ike session timeout");
                this.mScheduledHandleRetryIkeSessionFuture.cancel(false);
                this.mScheduledHandleRetryIkeSessionFuture = null;
            }
        }

        private void markFailedAndDisconnect(java.lang.Exception exc) {
            synchronized (com.android.server.connectivity.Vpn.this) {
                try {
                    if (com.android.server.connectivity.Vpn.this.mVpnRunner != this) {
                        return;
                    }
                    com.android.server.connectivity.Vpn.this.updateState(android.net.NetworkInfo.DetailedState.FAILED, exc.getMessage());
                    com.android.server.connectivity.Vpn.this.clearVpnNetworkPreference(this.mSessionKey);
                    lambda$exitVpnRunner$6();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback
        public void onSessionLost(int i, @android.annotation.Nullable java.lang.Exception exc) {
            java.lang.String str;
            android.util.LocalLog localLog = com.android.server.connectivity.Vpn.this.mEventChanges;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("[IKE] Session lost on network ");
            sb.append(this.mActiveNetwork);
            if (exc == null) {
                str = "";
            } else {
                str = " reason " + exc.getMessage();
            }
            sb.append(str);
            localLog.log(sb.toString());
            android.util.Log.d(TAG, "onSessionLost() called for token " + i);
            if (!isActiveToken(i)) {
                android.util.Log.d(TAG, "onSessionLost() called for obsolete token " + i);
                return;
            }
            handleSessionLost(exc, this.mActiveNetwork);
        }

        private void handleSessionLost(@android.annotation.Nullable java.lang.Exception exc, @android.annotation.Nullable android.net.Network network) {
            int i;
            java.lang.String str;
            int i2;
            cancelHandleNetworkLostTimeout();
            if (exc instanceof java.lang.IllegalArgumentException) {
                markFailedAndDisconnect(exc);
                return;
            }
            if (exc instanceof android.net.ipsec.ike.exceptions.IkeProtocolException) {
                android.net.ipsec.ike.exceptions.IkeProtocolException ikeProtocolException = (android.net.ipsec.ike.exceptions.IkeProtocolException) exc;
                i = ikeProtocolException.getErrorType();
                switch (ikeProtocolException.getErrorType()) {
                    case 14:
                    case 17:
                    case 24:
                    case 34:
                    case 37:
                    case 38:
                        i2 = 1;
                        break;
                    default:
                        i2 = 2;
                        break;
                }
                str = "android.net.category.EVENT_IKE_ERROR";
            } else if (exc instanceof android.net.ipsec.ike.exceptions.IkeNetworkLostException) {
                i2 = 2;
                i = 2;
                str = "android.net.category.EVENT_NETWORK_ERROR";
            } else {
                i = -1;
                if (exc instanceof android.net.ipsec.ike.exceptions.IkeNonProtocolException) {
                    if (exc.getCause() instanceof java.net.UnknownHostException) {
                        i = 0;
                        i2 = 2;
                        str = "android.net.category.EVENT_NETWORK_ERROR";
                    } else if (exc.getCause() instanceof android.net.ipsec.ike.exceptions.IkeTimeoutException) {
                        i = 1;
                        i2 = 2;
                        str = "android.net.category.EVENT_NETWORK_ERROR";
                    } else if (!(exc.getCause() instanceof java.io.IOException)) {
                        i2 = 2;
                        str = "android.net.category.EVENT_NETWORK_ERROR";
                    } else {
                        i = 3;
                        i2 = 2;
                        str = "android.net.category.EVENT_NETWORK_ERROR";
                    }
                } else {
                    if (exc != null) {
                        android.util.Log.wtf(TAG, "onSessionLost: exception = " + exc);
                    }
                    str = null;
                    i2 = -1;
                }
            }
            synchronized (com.android.server.connectivity.Vpn.this) {
                try {
                    if (com.android.server.connectivity.Vpn.this.mVpnRunner != this) {
                        return;
                    }
                    if (str != null && com.android.server.connectivity.Vpn.isVpnApp(com.android.server.connectivity.Vpn.this.mPackage)) {
                        com.android.server.connectivity.Vpn.this.sendEventToVpnManagerApp(str, i2, i, com.android.server.connectivity.Vpn.this.getPackage(), this.mSessionKey, com.android.server.connectivity.Vpn.this.makeVpnProfileStateLocked(), this.mActiveNetwork, com.android.server.connectivity.Vpn.this.getRedactedNetworkCapabilities(this.mUnderlyingNetworkCapabilities), com.android.server.connectivity.Vpn.this.getRedactedLinkProperties(this.mUnderlyingLinkProperties));
                    }
                    if (i2 != 1) {
                        scheduleStartIkeSession(-1L);
                        android.util.Log.d(TAG, "Resetting state for token: " + this.mCurrentToken);
                        synchronized (com.android.server.connectivity.Vpn.this) {
                            try {
                                if (com.android.server.connectivity.Vpn.this.mVpnRunner != this) {
                                    return;
                                }
                                com.android.server.connectivity.Vpn.this.mInterface = null;
                                if (com.android.server.connectivity.Vpn.this.mConfig != null) {
                                    com.android.server.connectivity.Vpn.this.mConfig.interfaze = null;
                                    if (com.android.server.connectivity.Vpn.this.mConfig.routes != null) {
                                        java.util.ArrayList arrayList = new java.util.ArrayList(com.android.server.connectivity.Vpn.this.mConfig.routes);
                                        com.android.server.connectivity.Vpn.this.mConfig.routes.clear();
                                        java.util.Iterator it = arrayList.iterator();
                                        while (it.hasNext()) {
                                            com.android.server.connectivity.Vpn.this.mConfig.routes.add(new android.net.RouteInfo(((android.net.RouteInfo) it.next()).getDestination(), null, null, 7));
                                        }
                                        if (com.android.server.connectivity.Vpn.this.mNetworkAgent != null) {
                                            com.android.server.connectivity.Vpn.doSendLinkProperties(com.android.server.connectivity.Vpn.this.mNetworkAgent, com.android.server.connectivity.Vpn.this.makeLinkProperties());
                                        }
                                    }
                                }
                                resetIkeState();
                                if (i != 2 && com.android.server.connectivity.Vpn.this.mDeps.getNextRetryDelayMs(this.mRetryCount - 1) > NETWORK_LOST_TIMEOUT_MS) {
                                    com.android.server.connectivity.Vpn.this.clearVpnNetworkPreference(this.mSessionKey);
                                    return;
                                }
                                return;
                            } finally {
                            }
                        }
                    }
                    markFailedAndDisconnect(exc);
                } finally {
                }
            }
        }

        private void resetIkeState() {
            if (this.mTunnelIface != null) {
                this.mTunnelIface.close();
                this.mTunnelIface = null;
            }
            if (this.mSession != null) {
                this.mSession.kill();
                this.mSession = null;
            }
            this.mIkeConnectionInfo = null;
            this.mMobikeEnabled = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: disconnectVpnRunner, reason: merged with bridge method [inline-methods] */
        public void lambda$exitVpnRunner$6() {
            com.android.server.connectivity.Vpn.this.mEventChanges.log("[VPNRunner] Disconnect runner, underlying net " + this.mActiveNetwork);
            this.mActiveNetwork = null;
            this.mUnderlyingNetworkCapabilities = null;
            this.mUnderlyingLinkProperties = null;
            this.mIsRunning = false;
            resetIkeState();
            com.android.server.connectivity.Vpn.this.mCarrierConfigManager.unregisterCarrierConfigChangeListener(this.mCarrierConfigChangeListener);
            com.android.server.connectivity.Vpn.this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
            this.mExecutor.shutdown();
        }

        @Override // com.android.server.connectivity.Vpn.VpnRunner
        public void exitVpnRunner() {
            com.android.server.connectivity.Vpn.this.clearVpnNetworkPreference(this.mSessionKey);
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.connectivity.Vpn$IkeV2VpnRunner$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.connectivity.Vpn.IkeV2VpnRunner.this.lambda$exitVpnRunner$6();
                    }
                });
            } catch (java.util.concurrent.RejectedExecutionException e) {
            }
        }
    }

    private void verifyCallingUidAndPackage(java.lang.String str) {
        this.mDeps.verifyCallingUidAndPackage(this.mContext, str, this.mUserId);
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String getProfileNameForPackage(java.lang.String str) {
        return "PLATFORM_VPN_" + this.mUserId + "_" + str;
    }

    @com.android.internal.annotations.VisibleForTesting
    void validateRequiredFeatures(com.android.internal.net.VpnProfile vpnProfile) {
        switch (vpnProfile.type) {
            case 6:
            case 7:
            case 8:
            case 9:
                if (!this.mContext.getPackageManager().hasSystemFeature("android.software.ipsec_tunnels")) {
                    throw new java.lang.UnsupportedOperationException("Ikev2VpnProfile(s) requires PackageManager.FEATURE_IPSEC_TUNNELS");
                }
                return;
            default:
                return;
        }
    }

    public synchronized boolean provisionVpnProfile(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.internal.net.VpnProfile vpnProfile) {
        try {
            java.util.Objects.requireNonNull(str, "No package name provided");
            java.util.Objects.requireNonNull(vpnProfile, "No profile provided");
            verifyCallingUidAndPackage(str);
            enforceNotRestrictedUser();
            validateRequiredFeatures(vpnProfile);
            if (vpnProfile.isRestrictedToTestNetworks) {
                this.mContext.enforceCallingPermission("android.permission.MANAGE_TEST_NETWORKS", "Test-mode profiles require the MANAGE_TEST_NETWORKS permission");
            }
            byte[] encode = vpnProfile.encode();
            if (encode.length > 131072) {
                throw new java.lang.IllegalArgumentException("Profile too big");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                getVpnProfileStore().put(getProfileNameForPackage(str), encode);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            throw th2;
        }
        return isVpnProfilePreConsented(this.mContext, str);
    }

    private boolean isCurrentIkev2VpnLocked(@android.annotation.NonNull java.lang.String str) {
        return isCurrentPreparedPackage(str) && isIkev2VpnRunner();
    }

    public synchronized void deleteVpnProfile(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str, "No package name provided");
        verifyCallingUidAndPackage(str);
        enforceNotRestrictedUser();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (isCurrentIkev2VpnLocked(str)) {
                if (this.mAlwaysOn) {
                    setAlwaysOnPackage(null, false, null);
                } else {
                    prepareInternal("[Legacy VPN]");
                }
            }
            getVpnProfileStore().remove(getProfileNameForPackage(str));
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.internal.net.VpnProfile getVpnProfilePrivileged(@android.annotation.NonNull java.lang.String str) {
        if (!this.mDeps.isCallerSystem()) {
            android.util.Log.wtf(TAG, "getVpnProfilePrivileged called as non-System UID ");
            return null;
        }
        byte[] bArr = getVpnProfileStore().get(getProfileNameForPackage(str));
        if (bArr == null) {
            return null;
        }
        return com.android.internal.net.VpnProfile.decode("", bArr);
    }

    private boolean isIkev2VpnRunner() {
        return this.mVpnRunner instanceof com.android.server.connectivity.Vpn.IkeV2VpnRunner;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private java.lang.String getSessionKeyLocked() {
        boolean isIkev2VpnRunner = isIkev2VpnRunner();
        java.lang.String str = isIkev2VpnRunner ? ((com.android.server.connectivity.Vpn.IkeV2VpnRunner) this.mVpnRunner).mSessionKey : null;
        android.util.Log.d(TAG, "getSessionKeyLocked: isIkev2VpnRunner = " + isIkev2VpnRunner + ", sessionKey = " + str);
        return str;
    }

    public synchronized java.lang.String startVpnProfile(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str, "No package name provided");
        enforceNotRestrictedUser();
        if (!prepare(str, null, 2)) {
            throw new java.lang.SecurityException("User consent not granted for package " + str);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.internal.net.VpnProfile vpnProfilePrivileged = getVpnProfilePrivileged(str);
            if (vpnProfilePrivileged == null) {
                throw new java.lang.IllegalArgumentException("No profile found for " + str);
            }
            startVpnProfilePrivileged(vpnProfilePrivileged, str);
            if (!isIkev2VpnRunner()) {
                throw new java.lang.IllegalStateException("mVpnRunner shouldn't be null and should also be an instance of Ikev2VpnRunner");
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return getSessionKeyLocked();
    }

    private synchronized void startVpnProfilePrivileged(@android.annotation.NonNull com.android.internal.net.VpnProfile vpnProfile, @android.annotation.NonNull java.lang.String str) {
        try {
            prepareInternal(str);
            updateState(android.net.NetworkInfo.DetailedState.CONNECTING, "startPlatformVpn");
            try {
                com.android.internal.net.VpnConfig vpnConfig = new com.android.internal.net.VpnConfig();
                if ("[Legacy VPN]".equals(str)) {
                    vpnConfig.legacy = true;
                    vpnConfig.session = vpnProfile.name;
                    vpnConfig.user = vpnProfile.key;
                    vpnConfig.isMetered = true;
                } else {
                    vpnConfig.user = str;
                    vpnConfig.isMetered = vpnProfile.isMetered;
                }
                vpnConfig.startTime = android.os.SystemClock.elapsedRealtime();
                vpnConfig.proxyInfo = vpnProfile.proxy;
                vpnConfig.requiresInternetValidation = vpnProfile.requiresInternetValidation;
                vpnConfig.excludeLocalRoutes = vpnProfile.excludeLocalRoutes;
                vpnConfig.allowBypass = vpnProfile.isBypassable;
                vpnConfig.disallowedApplications = getAppExclusionList(this.mPackage);
                this.mConfig = vpnConfig;
                switch (vpnProfile.type) {
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        this.mVpnRunner = new com.android.server.connectivity.Vpn.IkeV2VpnRunner(android.net.Ikev2VpnProfile.fromVpnProfile(vpnProfile), this.mDeps.newScheduledThreadPoolExecutor());
                        this.mVpnRunner.start();
                        break;
                    default:
                        this.mConfig = null;
                        updateState(android.net.NetworkInfo.DetailedState.FAILED, "Invalid platform VPN type");
                        android.util.Log.d(TAG, "Unknown VPN profile type: " + vpnProfile.type);
                        break;
                }
                if (!"[Legacy VPN]".equals(str)) {
                    this.mAppOpsManager.startOp("android:establish_vpn_manager", this.mOwnerUID, this.mPackage, null, null);
                }
            } catch (java.security.GeneralSecurityException e) {
                this.mConfig = null;
                updateState(android.net.NetworkInfo.DetailedState.FAILED, "VPN startup failed");
                throw new java.lang.IllegalArgumentException("VPN startup failed", e);
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void stopVpnRunnerAndNotifyAppLocked() {
        android.content.Intent intent;
        int i = this.mOwnerUID;
        if (!isVpnApp(this.mPackage)) {
            intent = null;
        } else {
            intent = buildVpnManagerEventIntent("android.net.category.EVENT_DEACTIVATED_BY_USER", -1, -1, this.mPackage, getSessionKeyLocked(), makeVpnProfileStateLocked(), null, null, null);
        }
        this.mVpnRunner.exit();
        if (intent != null && isVpnApp(this.mPackage)) {
            notifyVpnManagerVpnStopped(this.mPackage, i, intent);
        }
    }

    public synchronized void stopVpnProfile(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str, "No package name provided");
        enforceNotRestrictedUser();
        if (isCurrentIkev2VpnLocked(str)) {
            stopVpnRunnerAndNotifyAppLocked();
        }
    }

    private synchronized void notifyVpnManagerVpnStopped(java.lang.String str, int i, android.content.Intent intent) {
        this.mAppOpsManager.finishOp("android:establish_vpn_manager", i, str, null);
        this.mEventChanges.log("[VMEvent] " + str + " stopped");
        sendEventToVpnManagerApp(intent, str);
    }

    private boolean storeAppExclusionList(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<java.lang.String> list) {
        try {
            byte[] diskStableBytes = com.android.server.vcn.util.PersistableBundleUtils.toDiskStableBytes(com.android.server.vcn.util.PersistableBundleUtils.fromList(list, com.android.server.vcn.util.PersistableBundleUtils.STRING_SERIALIZER));
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                getVpnProfileStore().put(getVpnAppExcludedForPackage(str), diskStableBytes);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "problem writing into stream", e);
            return false;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String getVpnAppExcludedForPackage(java.lang.String str) {
        return VPN_APP_EXCLUDED + this.mUserId + "_" + str;
    }

    public synchronized boolean setAppExclusionList(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<java.lang.String> list) {
        enforceNotRestrictedUser();
        if (!storeAppExclusionList(str, list)) {
            return false;
        }
        updateAppExclusionList(list);
        return true;
    }

    public synchronized void refreshPlatformVpnAppExclusionList() {
        updateAppExclusionList(getAppExclusionList(this.mPackage));
    }

    private synchronized void updateAppExclusionList(@android.annotation.NonNull java.util.List<java.lang.String> list) {
        if (this.mNetworkAgent != null && isIkev2VpnRunner()) {
            this.mConfig.disallowedApplications = java.util.List.copyOf(list);
            this.mNetworkCapabilities = new android.net.NetworkCapabilities.Builder(this.mNetworkCapabilities).setUids(createUserAndRestrictedProfilesRanges(this.mUserId, null, list)).build();
            setVpnNetworkPreference(getSessionKeyLocked(), createUserAndRestrictedProfilesRanges(this.mUserId, this.mConfig.allowedApplications, this.mConfig.disallowedApplications));
            doSendNetworkCapabilities(this.mNetworkAgent, this.mNetworkCapabilities);
        }
    }

    /* JADX WARN: Not initialized variable reg: 0, insn: 0x004a: INVOKE (r0 I:long) STATIC call: android.os.Binder.restoreCallingIdentity(long):void A[Catch: all -> 0x0026, MD:(long):void (c), TRY_ENTER] (LINE:4373), block:B:26:0x004a */
    @android.annotation.NonNull
    public synchronized java.util.List<java.lang.String> getAppExclusionList(@android.annotation.NonNull java.lang.String str) {
        long restoreCallingIdentity;
        try {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                byte[] bArr = getVpnProfileStore().get(getVpnAppExcludedForPackage(str));
                if (bArr == null || bArr.length == 0) {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return arrayList;
                }
                java.util.List<java.lang.String> list = com.android.server.vcn.util.PersistableBundleUtils.toList(com.android.server.vcn.util.PersistableBundleUtils.fromDiskStableBytes(bArr), com.android.server.vcn.util.PersistableBundleUtils.STRING_DESERIALIZER);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return list;
            } catch (java.io.IOException e) {
                android.util.Log.e(TAG, "problem reading from stream", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return new java.util.ArrayList();
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(restoreCallingIdentity);
            throw th;
        }
    }

    private int getStateFromLegacyState(int i) {
        switch (i) {
            case 0:
                break;
            case 1:
            case 4:
            default:
                android.util.Log.wtf(TAG, "Unhandled state " + i + ", treat it as STATE_DISCONNECTED");
                break;
            case 2:
                break;
            case 3:
                break;
            case 5:
                break;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"this"})
    public android.net.VpnProfileState makeVpnProfileStateLocked() {
        return new android.net.VpnProfileState(getStateFromLegacyState(this.mLegacyState), isIkev2VpnRunner() ? getSessionKeyLocked() : null, this.mAlwaysOn, this.mLockdown);
    }

    @android.annotation.NonNull
    private android.net.VpnProfileState makeDisconnectedVpnProfileState() {
        return new android.net.VpnProfileState(0, null, false, false);
    }

    @android.annotation.Nullable
    public synchronized android.net.VpnProfileState getProvisionedVpnProfileState(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str, "No package name provided");
        enforceNotRestrictedUser();
        return isCurrentIkev2VpnLocked(str) ? makeVpnProfileStateLocked() : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void doSendLinkProperties(@android.annotation.NonNull android.net.NetworkAgent networkAgent, @android.annotation.NonNull android.net.LinkProperties linkProperties) {
        if (networkAgent instanceof com.android.server.connectivity.Vpn.VpnNetworkAgentWrapper) {
            ((com.android.server.connectivity.Vpn.VpnNetworkAgentWrapper) networkAgent).doSendLinkProperties(linkProperties);
        } else {
            networkAgent.sendLinkProperties(linkProperties);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void doSendNetworkCapabilities(@android.annotation.NonNull android.net.NetworkAgent networkAgent, @android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities) {
        if (networkAgent instanceof com.android.server.connectivity.Vpn.VpnNetworkAgentWrapper) {
            ((com.android.server.connectivity.Vpn.VpnNetworkAgentWrapper) networkAgent).doSendNetworkCapabilities(networkCapabilities);
        } else {
            networkAgent.sendNetworkCapabilities(networkCapabilities);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSetUnderlyingNetworks(@android.annotation.NonNull android.net.NetworkAgent networkAgent, @android.annotation.NonNull java.util.List<android.net.Network> list) {
        logUnderlyNetworkChanges(list);
        if (networkAgent instanceof com.android.server.connectivity.Vpn.VpnNetworkAgentWrapper) {
            ((com.android.server.connectivity.Vpn.VpnNetworkAgentWrapper) networkAgent).doSetUnderlyingNetworks(list);
        } else {
            networkAgent.setUnderlyingNetworks(list);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class VpnNetworkAgentWrapper extends android.net.NetworkAgent {
        private final com.android.server.connectivity.Vpn.ValidationStatusCallback mCallback;

        public VpnNetworkAgentWrapper(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Looper looper, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities, @android.annotation.NonNull android.net.LinkProperties linkProperties, @android.annotation.NonNull android.net.NetworkScore networkScore, @android.annotation.NonNull android.net.NetworkAgentConfig networkAgentConfig, @android.annotation.Nullable android.net.NetworkProvider networkProvider, @android.annotation.Nullable com.android.server.connectivity.Vpn.ValidationStatusCallback validationStatusCallback) {
            super(context, looper, str, networkCapabilities, linkProperties, networkScore, networkAgentConfig, networkProvider);
            this.mCallback = validationStatusCallback;
        }

        public void doSendLinkProperties(@android.annotation.NonNull android.net.LinkProperties linkProperties) {
            sendLinkProperties(linkProperties);
        }

        public void doSendNetworkCapabilities(@android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities) {
            sendNetworkCapabilities(networkCapabilities);
        }

        public void doSetUnderlyingNetworks(@android.annotation.NonNull java.util.List<android.net.Network> list) {
            setUnderlyingNetworks(list);
        }

        public void onNetworkUnwanted() {
        }

        public void onValidationStatus(int i, android.net.Uri uri) {
            if (this.mCallback != null) {
                this.mCallback.onValidationStatus(i);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class IkeSessionWrapper {
        private final android.net.ipsec.ike.IkeSession mImpl;

        public IkeSessionWrapper(android.net.ipsec.ike.IkeSession ikeSession) {
            this.mImpl = ikeSession;
        }

        public void setNetwork(@android.annotation.NonNull android.net.Network network, int i, int i2, int i3) {
            this.mImpl.setNetwork(network, i, i2, i3);
        }

        public void setUnderpinnedNetwork(@android.annotation.NonNull android.net.Network network) {
            this.mImpl.setUnderpinnedNetwork(network);
        }

        public void kill() {
            this.mImpl.kill();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class Ikev2SessionCreator {
        public com.android.server.connectivity.Vpn.IkeSessionWrapper createIkeSession(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.net.ipsec.ike.IkeSessionParams ikeSessionParams, @android.annotation.NonNull android.net.ipsec.ike.ChildSessionParams childSessionParams, @android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull android.net.ipsec.ike.IkeSessionCallback ikeSessionCallback, @android.annotation.NonNull android.net.ipsec.ike.ChildSessionCallback childSessionCallback) {
            return new com.android.server.connectivity.Vpn.IkeSessionWrapper(new android.net.ipsec.ike.IkeSession(context, ikeSessionParams, childSessionParams, executor, ikeSessionCallback, childSessionCallback));
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static android.util.Range<java.lang.Integer> createUidRangeForUser(int i) {
        return new android.util.Range<>(java.lang.Integer.valueOf(i * 100000), java.lang.Integer.valueOf(((i + 1) * 100000) - 1));
    }

    private java.lang.String getVpnManagerEventClassName(int i) {
        switch (i) {
            case 1:
                return "ERROR_CLASS_NOT_RECOVERABLE";
            case 2:
                return "ERROR_CLASS_RECOVERABLE";
            default:
                return "UNKNOWN_CLASS";
        }
    }

    private java.lang.String getVpnManagerEventErrorName(int i) {
        switch (i) {
            case 0:
                return "ERROR_CODE_NETWORK_UNKNOWN_HOST";
            case 1:
                return "ERROR_CODE_NETWORK_PROTOCOL_TIMEOUT";
            case 2:
                return "ERROR_CODE_NETWORK_LOST";
            case 3:
                return "ERROR_CODE_NETWORK_IO";
            default:
                return "UNKNOWN_ERROR";
        }
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this) {
            try {
                indentingPrintWriter.println("Active package name: " + this.mPackage);
                indentingPrintWriter.println("Active vpn type: " + getActiveVpnType());
                indentingPrintWriter.println("NetworkCapabilities: " + this.mNetworkCapabilities);
                if (isIkev2VpnRunner()) {
                    com.android.server.connectivity.Vpn.IkeV2VpnRunner ikeV2VpnRunner = (com.android.server.connectivity.Vpn.IkeV2VpnRunner) this.mVpnRunner;
                    indentingPrintWriter.println("SessionKey: " + ikeV2VpnRunner.mSessionKey);
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("MOBIKE ");
                    sb.append(ikeV2VpnRunner.mMobikeEnabled ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
                    indentingPrintWriter.println(sb.toString());
                    indentingPrintWriter.println("Profile: " + ikeV2VpnRunner.mProfile);
                    indentingPrintWriter.println("Token: " + ikeV2VpnRunner.mCurrentToken);
                    indentingPrintWriter.println("Validation failed retry count:" + ikeV2VpnRunner.mValidationFailRetryCount);
                    if (ikeV2VpnRunner.mScheduledHandleDataStallFuture != null) {
                        indentingPrintWriter.println("Reset session scheduled");
                    }
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println("mCachedCarrierConfigInfoPerSubId=" + this.mCachedCarrierConfigInfoPerSubId);
                indentingPrintWriter.println("mEventChanges (most recent first):");
                indentingPrintWriter.increaseIndent();
                this.mEventChanges.reverseDump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getCellSubIdForNetworkCapabilities(@android.annotation.Nullable android.net.NetworkCapabilities networkCapabilities) {
        if (networkCapabilities == null || !networkCapabilities.hasTransport(0)) {
            return -1;
        }
        android.net.NetworkSpecifier networkSpecifier = networkCapabilities.getNetworkSpecifier();
        if (!(networkSpecifier instanceof android.net.TelephonyNetworkSpecifier)) {
            return -1;
        }
        return ((android.net.TelephonyNetworkSpecifier) networkSpecifier).getSubscriptionId();
    }
}
