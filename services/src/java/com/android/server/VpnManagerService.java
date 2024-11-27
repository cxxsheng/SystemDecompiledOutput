package com.android.server;

/* loaded from: classes.dex */
public class VpnManagerService extends android.net.IVpnManager.Stub {
    private static final java.lang.String CONTEXT_ATTRIBUTION_TAG = "VPN_MANAGER";
    private static final java.lang.String TAG = com.android.server.VpnManagerService.class.getSimpleName();
    private final android.content.Context mContext;
    private final com.android.server.VpnManagerService.Dependencies mDeps;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.VisibleForTesting
    protected final android.os.HandlerThread mHandlerThread;

    @com.android.internal.annotations.GuardedBy({"mVpns"})
    private boolean mLockdownEnabled;

    @com.android.internal.annotations.GuardedBy({"mVpns"})
    private com.android.server.net.LockdownVpnTracker mLockdownTracker;
    private final int mMainUserId;
    private final android.os.INetworkManagementService mNMS;
    private final android.net.INetd mNetd;
    private final android.content.Context mUserAllContext;
    private final android.os.UserManager mUserManager;
    private final com.android.server.connectivity.VpnProfileStore mVpnProfileStore;

    @com.android.internal.annotations.GuardedBy({"mVpns"})
    @com.android.internal.annotations.VisibleForTesting
    protected final android.util.SparseArray<com.android.server.connectivity.Vpn> mVpns = new android.util.SparseArray<>();
    private android.content.BroadcastReceiver mIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.VpnManagerService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.VpnManagerService.this.ensureRunningOnHandlerThread();
            java.lang.String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
            int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
            android.net.Uri data = intent.getData();
            java.lang.String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
            if (com.android.server.net.LockdownVpnTracker.ACTION_LOCKDOWN_RESET.equals(action)) {
                com.android.server.VpnManagerService.this.onVpnLockdownReset();
                return;
            }
            if (intExtra == -10000) {
                return;
            }
            if ("android.intent.action.USER_STARTED".equals(action)) {
                com.android.server.VpnManagerService.this.onUserStarted(intExtra);
                return;
            }
            if ("android.intent.action.USER_STOPPED".equals(action)) {
                com.android.server.VpnManagerService.this.onUserStopped(intExtra);
                return;
            }
            if ("android.intent.action.USER_ADDED".equals(action)) {
                com.android.server.VpnManagerService.this.onUserAdded(intExtra);
                return;
            }
            if ("android.intent.action.USER_REMOVED".equals(action)) {
                com.android.server.VpnManagerService.this.onUserRemoved(intExtra);
                return;
            }
            if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                com.android.server.VpnManagerService.this.onUserUnlocked(intExtra);
                return;
            }
            if ("android.intent.action.PACKAGE_REPLACED".equals(action)) {
                com.android.server.VpnManagerService.this.onPackageReplaced(schemeSpecificPart, intExtra2);
                return;
            }
            if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                com.android.server.VpnManagerService.this.onPackageRemoved(schemeSpecificPart, intExtra2, intent.getBooleanExtra("android.intent.extra.REPLACING", false));
                return;
            }
            if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                com.android.server.VpnManagerService.this.onPackageAdded(schemeSpecificPart, intExtra2, intent.getBooleanExtra("android.intent.extra.REPLACING", false));
                return;
            }
            android.util.Log.wtf(com.android.server.VpnManagerService.TAG, "received unexpected intent: " + action);
        }
    };
    private android.content.BroadcastReceiver mUserPresentReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.VpnManagerService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.VpnManagerService.this.ensureRunningOnHandlerThread();
            com.android.server.VpnManagerService.this.updateLockdownVpn();
            context.unregisterReceiver(this);
        }
    };

    @com.android.internal.annotations.VisibleForTesting
    public static class Dependencies {
        public int getCallingUid() {
            return android.os.Binder.getCallingUid();
        }

        public android.os.HandlerThread makeHandlerThread() {
            return new android.os.HandlerThread("VpnManagerService");
        }

        public com.android.server.connectivity.VpnProfileStore getVpnProfileStore() {
            return new com.android.server.connectivity.VpnProfileStore();
        }

        public android.net.INetd getNetd() {
            return android.net.util.NetdService.getInstance();
        }

        public android.os.INetworkManagementService getINetworkManagementService() {
            return android.os.INetworkManagementService.Stub.asInterface(android.os.ServiceManager.getService("network_management"));
        }

        public com.android.server.connectivity.Vpn createVpn(android.os.Looper looper, android.content.Context context, android.os.INetworkManagementService iNetworkManagementService, android.net.INetd iNetd, int i) {
            return new com.android.server.connectivity.Vpn(looper, context, iNetworkManagementService, iNetd, i, new com.android.server.connectivity.VpnProfileStore());
        }

        public com.android.server.net.LockdownVpnTracker createLockDownVpnTracker(android.content.Context context, android.os.Handler handler, com.android.server.connectivity.Vpn vpn, com.android.internal.net.VpnProfile vpnProfile) {
            return new com.android.server.net.LockdownVpnTracker(context, handler, vpn, vpnProfile);
        }

        public int getMainUserId() {
            return ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getMainUserId();
        }
    }

    public VpnManagerService(android.content.Context context, com.android.server.VpnManagerService.Dependencies dependencies) {
        this.mContext = context.createAttributionContext(CONTEXT_ATTRIBUTION_TAG);
        this.mDeps = dependencies;
        this.mHandlerThread = this.mDeps.makeHandlerThread();
        this.mHandlerThread.start();
        this.mHandler = this.mHandlerThread.getThreadHandler();
        this.mVpnProfileStore = this.mDeps.getVpnProfileStore();
        this.mUserAllContext = this.mContext.createContextAsUser(android.os.UserHandle.ALL, 0);
        this.mNMS = this.mDeps.getINetworkManagementService();
        this.mNetd = this.mDeps.getNetd();
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        this.mMainUserId = this.mDeps.getMainUserId();
        registerReceivers();
        log("VpnManagerService starting up");
    }

    public static com.android.server.VpnManagerService create(android.content.Context context) {
        return new com.android.server.VpnManagerService(context, new com.android.server.VpnManagerService.Dependencies());
    }

    public void systemReady() {
        updateLockdownVpn();
    }

    protected void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            android.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
            indentingPrintWriter.println("VPNs:");
            indentingPrintWriter.increaseIndent();
            synchronized (this.mVpns) {
                for (int i = 0; i < this.mVpns.size(); i++) {
                    try {
                        indentingPrintWriter.println(this.mVpns.keyAt(i) + ": " + this.mVpns.valueAt(i).getPackage());
                        indentingPrintWriter.increaseIndent();
                        this.mVpns.valueAt(i).dump(indentingPrintWriter);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.println();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                indentingPrintWriter.decreaseIndent();
            }
        }
    }

    public boolean prepareVpn(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i) {
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                throwIfLockdownEnabled();
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(i);
                if (vpn == null) {
                    return false;
                }
                return vpn.prepare(str, str2, 1);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setVpnPackageAuthorization(java.lang.String str, int i, int i2) {
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(i);
                if (vpn != null) {
                    vpn.setPackageAuthorization(str, i2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.os.ParcelFileDescriptor establishVpn(com.android.internal.net.VpnConfig vpnConfig) {
        android.os.ParcelFileDescriptor establish;
        int userId = android.os.UserHandle.getUserId(this.mDeps.getCallingUid());
        synchronized (this.mVpns) {
            throwIfLockdownEnabled();
            establish = this.mVpns.get(userId).establish(vpnConfig);
        }
        return establish;
    }

    public boolean addVpnAddress(java.lang.String str, int i) {
        boolean addAddress;
        int userId = android.os.UserHandle.getUserId(this.mDeps.getCallingUid());
        synchronized (this.mVpns) {
            throwIfLockdownEnabled();
            addAddress = this.mVpns.get(userId).addAddress(str, i);
        }
        return addAddress;
    }

    public boolean removeVpnAddress(java.lang.String str, int i) {
        boolean removeAddress;
        int userId = android.os.UserHandle.getUserId(this.mDeps.getCallingUid());
        synchronized (this.mVpns) {
            throwIfLockdownEnabled();
            removeAddress = this.mVpns.get(userId).removeAddress(str, i);
        }
        return removeAddress;
    }

    public boolean setUnderlyingNetworksForVpn(android.net.Network[] networkArr) {
        boolean underlyingNetworks;
        int userId = android.os.UserHandle.getUserId(this.mDeps.getCallingUid());
        synchronized (this.mVpns) {
            underlyingNetworks = this.mVpns.get(userId).setUnderlyingNetworks(networkArr);
        }
        return underlyingNetworks;
    }

    public boolean provisionVpnProfile(@android.annotation.NonNull com.android.internal.net.VpnProfile vpnProfile, @android.annotation.NonNull java.lang.String str) {
        boolean provisionVpnProfile;
        int userId = android.os.UserHandle.getUserId(this.mDeps.getCallingUid());
        synchronized (this.mVpns) {
            provisionVpnProfile = this.mVpns.get(userId).provisionVpnProfile(str, vpnProfile);
        }
        return provisionVpnProfile;
    }

    public void deleteVpnProfile(@android.annotation.NonNull java.lang.String str) {
        int userId = android.os.UserHandle.getUserId(this.mDeps.getCallingUid());
        synchronized (this.mVpns) {
            this.mVpns.get(userId).deleteVpnProfile(str);
        }
    }

    private int getAppUid(java.lang.String str, int i) {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
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

    private void verifyCallingUidAndPackage(java.lang.String str, int i) {
        if (getAppUid(str, android.os.UserHandle.getUserId(i)) != i) {
            throw new java.lang.SecurityException(str + " does not belong to uid " + i);
        }
    }

    public java.lang.String startVpnProfile(@android.annotation.NonNull java.lang.String str) {
        java.lang.String startVpnProfile;
        int callingUid = android.os.Binder.getCallingUid();
        verifyCallingUidAndPackage(str, callingUid);
        int userId = android.os.UserHandle.getUserId(callingUid);
        synchronized (this.mVpns) {
            throwIfLockdownEnabled();
            startVpnProfile = this.mVpns.get(userId).startVpnProfile(str);
        }
        return startVpnProfile;
    }

    public void stopVpnProfile(@android.annotation.NonNull java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        verifyCallingUidAndPackage(str, callingUid);
        int userId = android.os.UserHandle.getUserId(callingUid);
        synchronized (this.mVpns) {
            this.mVpns.get(userId).stopVpnProfile(str);
        }
    }

    @android.annotation.Nullable
    public android.net.VpnProfileState getProvisionedVpnProfileState(@android.annotation.NonNull java.lang.String str) {
        android.net.VpnProfileState provisionedVpnProfileState;
        int callingUid = android.os.Binder.getCallingUid();
        verifyCallingUidAndPackage(str, callingUid);
        int userId = android.os.UserHandle.getUserId(callingUid);
        synchronized (this.mVpns) {
            provisionedVpnProfileState = this.mVpns.get(userId).getProvisionedVpnProfileState(str);
        }
        return provisionedVpnProfileState;
    }

    public void startLegacyVpn(com.android.internal.net.VpnProfile vpnProfile) {
        if (android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT >= 31 && com.android.internal.net.VpnProfile.isLegacyType(vpnProfile.type)) {
            throw new java.lang.UnsupportedOperationException("Legacy VPN is deprecated");
        }
        int userId = android.os.UserHandle.getUserId(this.mDeps.getCallingUid());
        synchronized (this.mVpns) {
            throwIfLockdownEnabled();
            this.mVpns.get(userId).startLegacyVpn(vpnProfile);
        }
    }

    public com.android.internal.net.LegacyVpnInfo getLegacyVpnInfo(int i) {
        com.android.internal.net.LegacyVpnInfo legacyVpnInfo;
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            legacyVpnInfo = this.mVpns.get(i).getLegacyVpnInfo();
        }
        return legacyVpnInfo;
    }

    public com.android.internal.net.VpnProfile[] getAllLegacyVpns() {
        android.net.NetworkStack.checkNetworkStackPermission(this.mContext);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            for (java.lang.String str : this.mVpnProfileStore.list("VPN_")) {
                com.android.internal.net.VpnProfile decode = com.android.internal.net.VpnProfile.decode(str, this.mVpnProfileStore.get("VPN_" + str));
                if (decode != null) {
                    arrayList.add(decode);
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return (com.android.internal.net.VpnProfile[]) arrayList.toArray(new com.android.internal.net.VpnProfile[arrayList.size()]);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public com.android.internal.net.VpnConfig getVpnConfig(int i) {
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(i);
                if (vpn == null) {
                    return null;
                }
                return vpn.getVpnConfig();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isLockdownVpnEnabled() {
        return this.mVpnProfileStore.get("LOCKDOWN_VPN") != null;
    }

    public boolean updateLockdownVpn() {
        if (this.mDeps.getCallingUid() != 1000 && this.mDeps.getCallingUid() != android.os.UserHandle.getUid(this.mMainUserId, 1000) && android.os.Binder.getCallingPid() != android.os.Process.myPid()) {
            logw("Lockdown VPN only available to system process or AID_SYSTEM on main user");
            return false;
        }
        synchronized (this.mVpns) {
            try {
                this.mLockdownEnabled = isLockdownVpnEnabled();
                if (!this.mLockdownEnabled) {
                    setLockdownTracker(null);
                    return true;
                }
                byte[] bArr = this.mVpnProfileStore.get("LOCKDOWN_VPN");
                if (bArr == null) {
                    loge("Lockdown VPN configured but cannot be read from keystore");
                    return false;
                }
                java.lang.String str = new java.lang.String(bArr);
                com.android.internal.net.VpnProfile decode = com.android.internal.net.VpnProfile.decode(str, this.mVpnProfileStore.get("VPN_" + str));
                if (decode == null) {
                    loge("Lockdown VPN configured invalid profile " + str);
                    setLockdownTracker(null);
                    return true;
                }
                int userId = android.os.UserHandle.getUserId(this.mDeps.getCallingUid());
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(userId);
                if (vpn == null) {
                    logw("VPN for user " + userId + " not ready yet. Skipping lockdown");
                    return false;
                }
                setLockdownTracker(this.mDeps.createLockDownVpnTracker(this.mContext, this.mHandler, vpn, decode));
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mVpns"})
    private void setLockdownTracker(com.android.server.net.LockdownVpnTracker lockdownVpnTracker) {
        com.android.server.net.LockdownVpnTracker lockdownVpnTracker2 = this.mLockdownTracker;
        this.mLockdownTracker = null;
        if (lockdownVpnTracker2 != null) {
            lockdownVpnTracker2.shutdown();
        }
        if (lockdownVpnTracker != null) {
            this.mLockdownTracker = lockdownVpnTracker;
            this.mLockdownTracker.init();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mVpns"})
    private void throwIfLockdownEnabled() {
        if (this.mLockdownEnabled) {
            throw new java.lang.IllegalStateException("Unavailable in lockdown mode");
        }
    }

    private boolean startAlwaysOnVpn(int i) {
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(i);
                if (vpn == null) {
                    android.util.Log.wtf(TAG, "User " + i + " has no Vpn configuration");
                    return false;
                }
                return vpn.startAlwaysOnVpn();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAlwaysOnVpnPackageSupported(int i, java.lang.String str) {
        enforceSettingsPermission();
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(i);
                if (vpn == null) {
                    logw("User " + i + " has no Vpn configuration");
                    return false;
                }
                return vpn.isAlwaysOnPackageSupported(str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean setAlwaysOnVpnPackage(int i, java.lang.String str, boolean z, java.util.List<java.lang.String> list) {
        enforceControlAlwaysOnVpnPermission();
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                if (isLockdownVpnEnabled()) {
                    return false;
                }
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(i);
                if (vpn == null) {
                    logw("User " + i + " has no Vpn configuration");
                    return false;
                }
                if (!vpn.setAlwaysOnPackage(str, z, list)) {
                    return false;
                }
                if (!startAlwaysOnVpn(i)) {
                    vpn.setAlwaysOnPackage(null, false, null);
                    return false;
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.lang.String getAlwaysOnVpnPackage(int i) {
        enforceControlAlwaysOnVpnPermission();
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(i);
                if (vpn == null) {
                    logw("User " + i + " has no Vpn configuration");
                    return null;
                }
                return vpn.getAlwaysOnPackage();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isVpnLockdownEnabled(int i) {
        enforceControlAlwaysOnVpnPermission();
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(i);
                if (vpn == null) {
                    logw("User " + i + " has no Vpn configuration");
                    return false;
                }
                return vpn.getLockdown();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.List<java.lang.String> getVpnLockdownAllowlist(int i) {
        enforceControlAlwaysOnVpnPermission();
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(i);
                if (vpn == null) {
                    logw("User " + i + " has no Vpn configuration");
                    return null;
                }
                return vpn.getLockdownAllowlist();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mVpns"})
    private com.android.server.connectivity.Vpn getVpnIfOwner() {
        return getVpnIfOwner(this.mDeps.getCallingUid());
    }

    @com.android.internal.annotations.GuardedBy({"mVpns"})
    private com.android.server.connectivity.Vpn getVpnIfOwner(int i) {
        com.android.server.connectivity.Vpn vpn = this.mVpns.get(android.os.UserHandle.getUserId(i));
        if (vpn == null) {
            return null;
        }
        android.net.UnderlyingNetworkInfo underlyingNetworkInfo = vpn.getUnderlyingNetworkInfo();
        if (underlyingNetworkInfo == null || underlyingNetworkInfo.getOwnerUid() != i) {
            return null;
        }
        return vpn;
    }

    private void registerReceivers() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_STARTED");
        intentFilter.addAction("android.intent.action.USER_STOPPED");
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        this.mUserAllContext.registerReceiver(this.mIntentReceiver, intentFilter, null, this.mHandler);
        this.mContext.createContextAsUser(android.os.UserHandle.of(this.mMainUserId), 0).registerReceiver(this.mUserPresentReceiver, new android.content.IntentFilter("android.intent.action.USER_PRESENT"), null, this.mHandler);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mUserAllContext.registerReceiver(this.mIntentReceiver, intentFilter2, null, this.mHandler);
        android.content.IntentFilter intentFilter3 = new android.content.IntentFilter();
        intentFilter3.addAction(com.android.server.net.LockdownVpnTracker.ACTION_LOCKDOWN_RESET);
        this.mUserAllContext.registerReceiver(this.mIntentReceiver, intentFilter3, "android.permission.NETWORK_STACK", this.mHandler, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserStarted(int i) {
        if (this.mUserManager.getUserInfo(i) == null) {
            logw("Started user doesn't exist. UserId: " + i);
            return;
        }
        synchronized (this.mVpns) {
            try {
                if (this.mVpns.get(i) != null) {
                    loge("Starting user already has a VPN");
                    return;
                }
                this.mVpns.put(i, this.mDeps.createVpn(this.mHandler.getLooper(), this.mContext, this.mNMS, this.mNetd, i));
                if (i == this.mMainUserId && isLockdownVpnEnabled()) {
                    updateLockdownVpn();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserStopped(int i) {
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(i);
                if (vpn == null) {
                    loge("Stopped user has no VPN");
                } else {
                    vpn.onUserStopped();
                    this.mVpns.delete(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isCallerCurrentAlwaysOnVpnApp() {
        boolean z;
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpnIfOwner = getVpnIfOwner();
                z = vpnIfOwner != null && vpnIfOwner.getAlwaysOn();
            } finally {
            }
        }
        return z;
    }

    public boolean isCallerCurrentAlwaysOnVpnLockdownApp() {
        boolean z;
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpnIfOwner = getVpnIfOwner();
                z = vpnIfOwner != null && vpnIfOwner.getLockdown();
            } finally {
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserAdded(int i) {
        synchronized (this.mVpns) {
            try {
                int size = this.mVpns.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mVpns.valueAt(i2).onUserAdded(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserRemoved(int i) {
        synchronized (this.mVpns) {
            try {
                int size = this.mVpns.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mVpns.valueAt(i2).onUserRemoved(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageReplaced(java.lang.String str, int i) {
        if (android.text.TextUtils.isEmpty(str) || i < 0) {
            android.util.Log.wtf(TAG, "Invalid package in onPackageReplaced: " + str + " | " + i);
            return;
        }
        int userId = android.os.UserHandle.getUserId(i);
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(userId);
                if (vpn == null) {
                    return;
                }
                if (android.text.TextUtils.equals(vpn.getAlwaysOnPackage(), str)) {
                    log("Restarting always-on VPN package " + str + " for user " + userId);
                    vpn.startAlwaysOnVpn();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageRemoved(java.lang.String str, int i, boolean z) {
        if (android.text.TextUtils.isEmpty(str) || i < 0) {
            android.util.Log.wtf(TAG, "Invalid package in onPackageRemoved: " + str + " | " + i);
            return;
        }
        int userId = android.os.UserHandle.getUserId(i);
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(userId);
                if (vpn == null || z) {
                    return;
                }
                if (android.text.TextUtils.equals(vpn.getAlwaysOnPackage(), str)) {
                    log("Removing always-on VPN package " + str + " for user " + userId);
                    vpn.setAlwaysOnPackage(null, false, null);
                }
                vpn.refreshPlatformVpnAppExclusionList();
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPackageAdded(java.lang.String str, int i, boolean z) {
        if (android.text.TextUtils.isEmpty(str) || i < 0) {
            android.util.Log.wtf(TAG, "Invalid package in onPackageAdded: " + str + " | " + i);
            return;
        }
        int userId = android.os.UserHandle.getUserId(i);
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(userId);
                if (vpn != null && !z) {
                    vpn.refreshPlatformVpnAppExclusionList();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserUnlocked(int i) {
        synchronized (this.mVpns) {
            try {
                if (i == this.mMainUserId && isLockdownVpnEnabled()) {
                    updateLockdownVpn();
                } else {
                    startAlwaysOnVpn(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVpnLockdownReset() {
        synchronized (this.mVpns) {
            try {
                if (this.mLockdownTracker != null) {
                    this.mLockdownTracker.reset();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean setAppExclusionList(int i, java.lang.String str, java.util.List<java.lang.String> list) {
        boolean appExclusionList;
        enforceSettingsPermission();
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(i);
                if (vpn != null) {
                    appExclusionList = vpn.setAppExclusionList(str, list);
                } else {
                    logw("User " + i + " has no Vpn configuration");
                    throw new java.lang.IllegalStateException("VPN for user " + i + " not ready yet. Skipping setting the list");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return appExclusionList;
    }

    public java.util.List<java.lang.String> getAppExclusionList(int i, java.lang.String str) {
        enforceSettingsPermission();
        enforceCrossUserPermission(i);
        synchronized (this.mVpns) {
            try {
                com.android.server.connectivity.Vpn vpn = this.mVpns.get(i);
                if (vpn != null) {
                    return vpn.getAppExclusionList(str);
                }
                logw("User " + i + " has no Vpn configuration");
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void factoryReset() {
        enforceSettingsPermission();
        if (this.mUserManager.hasUserRestriction("no_network_reset") || this.mUserManager.hasUserRestriction("no_config_vpn")) {
            return;
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        synchronized (this.mVpns) {
            try {
                java.lang.String alwaysOnVpnPackage = getAlwaysOnVpnPackage(callingUserId);
                if (alwaysOnVpnPackage != null) {
                    setAlwaysOnVpnPackage(callingUserId, null, false, null);
                    setVpnPackageAuthorization(alwaysOnVpnPackage, callingUserId, -1);
                }
                if (this.mLockdownEnabled && callingUserId == this.mMainUserId) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mVpnProfileStore.remove("LOCKDOWN_VPN");
                        this.mLockdownEnabled = false;
                        setLockdownTracker(null);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                com.android.internal.net.VpnConfig vpnConfig = getVpnConfig(callingUserId);
                if (vpnConfig != null) {
                    if (!vpnConfig.legacy) {
                        setVpnPackageAuthorization(vpnConfig.user, callingUserId, -1);
                        prepareVpn(null, "[Legacy VPN]", callingUserId);
                    } else {
                        prepareVpn("[Legacy VPN]", "[Legacy VPN]", callingUserId);
                    }
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensureRunningOnHandlerThread() {
        if (this.mHandler.getLooper().getThread() != java.lang.Thread.currentThread()) {
            throw new java.lang.IllegalStateException("Not running on VpnManagerService thread: " + java.lang.Thread.currentThread().getName());
        }
    }

    private void enforceControlAlwaysOnVpnPermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_ALWAYS_ON_VPN", "VpnManagerService");
    }

    private void enforceCrossUserPermission(int i) {
        if (i == android.os.UserHandle.getCallingUserId()) {
            return;
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "VpnManagerService");
    }

    private void enforceSettingsPermission() {
        com.android.net.module.util.PermissionUtils.enforceAnyPermissionOf(this.mContext, new java.lang.String[]{"android.permission.NETWORK_SETTINGS", "android.permission.MAINLINE_NETWORK_STACK"});
    }

    private static void log(java.lang.String str) {
        android.util.Log.d(TAG, str);
    }

    private static void logw(java.lang.String str) {
        android.util.Log.w(TAG, str);
    }

    private static void loge(java.lang.String str) {
        android.util.Log.e(TAG, str);
    }
}
