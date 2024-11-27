package com.android.server.policy;

/* loaded from: classes2.dex */
public final class PermissionPolicyService extends com.android.server.SystemService {
    private static final boolean DEBUG = false;
    private static final java.lang.String LOG_TAG = com.android.server.policy.PermissionPolicyService.class.getSimpleName();
    private static final long NOTIFICATION_PERM_CHANGE_ID = 194833441;
    private static final java.lang.String SYSTEM_PKG = "android";
    private static final long USER_SENSITIVE_UPDATE_DELAY_MS = 60000;
    private java.util.List<java.lang.String> mAppOpPermissions;
    private com.android.internal.app.IAppOpsCallback mAppOpsCallback;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mBootCompleted;
    private android.content.Context mContext;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mIsStarted;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mIsUidResetScheduled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mIsUidSyncScheduled;
    private final android.app.KeyguardManager mKeyguardManager;
    private final java.lang.Object mLock;
    private com.android.server.notification.NotificationManagerInternal mNotificationManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.policy.PermissionPolicyInternal.OnInitializedCallback mOnInitializedCallback;
    private final android.content.pm.PackageManager mPackageManager;
    private android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private com.android.server.pm.permission.PermissionManagerServiceInternal mPermissionManagerInternal;
    private final java.util.ArrayList<com.android.server.policy.PermissionPolicyService.PhoneCarrierPrivilegesCallback> mPhoneCarrierPrivilegesCallbacks;
    private final android.content.BroadcastReceiver mSimConfigBroadcastReceiver;
    private android.telephony.TelephonyManager mTelephonyManager;

    public PermissionPolicyService(@android.annotation.NonNull android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mBootCompleted = false;
        this.mIsStarted = new android.util.SparseBooleanArray();
        this.mIsUidSyncScheduled = new android.util.SparseBooleanArray();
        this.mIsUidResetScheduled = new android.util.SparseBooleanArray();
        this.mPhoneCarrierPrivilegesCallbacks = new java.util.ArrayList<>();
        this.mSimConfigBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.policy.PermissionPolicyService.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (!"android.telephony.action.MULTI_SIM_CONFIG_CHANGED".equals(intent.getAction())) {
                    return;
                }
                com.android.server.policy.PermissionPolicyService.this.unregisterCarrierPrivilegesCallback();
                com.android.server.policy.PermissionPolicyService.this.registerCarrierPrivilegesCallbacks();
            }
        };
        this.mContext = context;
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        this.mPackageManager = context.getPackageManager();
        this.mKeyguardManager = (android.app.KeyguardManager) context.getSystemService(android.app.KeyguardManager.class);
        com.android.server.LocalServices.addService(com.android.server.policy.PermissionPolicyInternal.class, new com.android.server.policy.PermissionPolicyService.Internal());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.android.server.SystemService
    public void onStart() {
        char c;
        int extraAppOpCode;
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mPermissionManagerInternal = (com.android.server.pm.permission.PermissionManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInternal.class);
        com.android.internal.app.IAppOpsService asInterface = com.android.internal.app.IAppOpsService.Stub.asInterface(android.os.ServiceManager.getService("appops"));
        this.mPackageManagerInternal.getPackageList(new android.content.pm.PackageManagerInternal.PackageListObserver() { // from class: com.android.server.policy.PermissionPolicyService.1
            @Override // android.content.pm.PackageManagerInternal.PackageListObserver
            public void onPackageAdded(java.lang.String str, int i) {
                for (int i2 : ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserIds()) {
                    if (com.android.server.policy.PermissionPolicyService.this.isStarted(i2)) {
                        com.android.server.policy.PermissionPolicyService.this.synchronizeUidPermissionsAndAppOps(android.os.UserHandle.getUid(i2, i));
                    }
                }
            }

            @Override // android.content.pm.PackageManagerInternal.PackageListObserver
            public void onPackageChanged(java.lang.String str, int i) {
                for (int i2 : ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserIds()) {
                    if (com.android.server.policy.PermissionPolicyService.this.isStarted(i2)) {
                        int uid = android.os.UserHandle.getUid(i2, i);
                        com.android.server.policy.PermissionPolicyService.this.synchronizeUidPermissionsAndAppOps(uid);
                        com.android.server.policy.PermissionPolicyService.this.resetAppOpPermissionsIfNotRequestedForUid(uid);
                    }
                }
            }

            @Override // android.content.pm.PackageManagerInternal.PackageListObserver
            public void onPackageRemoved(java.lang.String str, int i) {
                for (int i2 : ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserIds()) {
                    if (com.android.server.policy.PermissionPolicyService.this.isStarted(i2)) {
                        com.android.server.policy.PermissionPolicyService.this.resetAppOpPermissionsIfNotRequestedForUid(android.os.UserHandle.getUid(i2, i));
                    }
                }
            }
        });
        this.mPackageManager.addOnPermissionsChangeListener(new android.content.pm.PackageManager.OnPermissionsChangedListener() { // from class: com.android.server.policy.PermissionPolicyService$$ExternalSyntheticLambda0
            public final void onPermissionsChanged(int i) {
                com.android.server.policy.PermissionPolicyService.this.synchronizeUidPermissionsAndAppOpsAsync(i);
            }
        });
        this.mAppOpsCallback = new com.android.internal.app.IAppOpsCallback.Stub() { // from class: com.android.server.policy.PermissionPolicyService.2
            public void opChanged(int i, int i2, @android.annotation.Nullable java.lang.String str, java.lang.String str2) {
                if (!java.util.Objects.equals(str2, "default:0")) {
                    return;
                }
                if (str != null) {
                    com.android.server.policy.PermissionPolicyService.this.synchronizeUidPermissionsAndAppOpsAsync(i2);
                }
                com.android.server.policy.PermissionPolicyService.this.resetAppOpPermissionsIfNotRequestedForUidAsync(i2);
            }
        };
        java.util.List<android.content.pm.PermissionInfo> allPermissionsWithProtection = this.mPermissionManagerInternal.getAllPermissionsWithProtection(1);
        try {
            int size = allPermissionsWithProtection.size();
            for (int i = 0; i < size; i++) {
                android.content.pm.PermissionInfo permissionInfo = allPermissionsWithProtection.get(i);
                if (permissionInfo.isRuntime()) {
                    asInterface.startWatchingMode(getSwitchOp(permissionInfo.name), (java.lang.String) null, this.mAppOpsCallback);
                }
                if (permissionInfo.isSoftRestricted() && (extraAppOpCode = com.android.server.policy.SoftRestrictedPermissionPolicy.forPermission(null, null, null, null, permissionInfo.name).getExtraAppOpCode()) != -1) {
                    asInterface.startWatchingMode(extraAppOpCode, (java.lang.String) null, this.mAppOpsCallback);
                }
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.wtf(LOG_TAG, "Cannot set up app-ops listener");
        }
        java.util.List<android.content.pm.PermissionInfo> allPermissionsWithProtectionFlags = this.mPermissionManagerInternal.getAllPermissionsWithProtectionFlags(64);
        this.mAppOpPermissions = new java.util.ArrayList();
        int size2 = allPermissionsWithProtectionFlags.size();
        for (int i2 = 0; i2 < size2; i2++) {
            android.content.pm.PermissionInfo permissionInfo2 = allPermissionsWithProtectionFlags.get(i2);
            java.lang.String str = permissionInfo2.name;
            switch (str.hashCode()) {
                case 309844284:
                    if (str.equals("android.permission.MANAGE_IPSEC_TUNNELS")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1353874541:
                    if (str.equals("android.permission.ACCESS_NOTIFICATIONS")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1777263169:
                    if (str.equals("android.permission.REQUEST_INSTALL_PACKAGES")) {
                        c = 2;
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
                    break;
                default:
                    int permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(permissionInfo2.name);
                    if (permissionToOpCode != -1) {
                        this.mAppOpPermissions.add(permissionInfo2.name);
                        try {
                            asInterface.startWatchingMode(permissionToOpCode, (java.lang.String) null, this.mAppOpsCallback);
                            break;
                        } catch (android.os.RemoteException e2) {
                            android.util.Slog.wtf(LOG_TAG, "Cannot set up app-ops listener", e2);
                            break;
                        }
                    } else {
                        break;
                    }
            }
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        getContext().registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.policy.PermissionPolicyService.3
            final java.util.List<java.lang.Integer> mUserSetupUids = new java.util.ArrayList(200);
            final java.util.Map<android.os.UserHandle, android.permission.PermissionControllerManager> mPermControllerManagers = new java.util.HashMap();

            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                boolean z;
                try {
                    android.content.ContentResolver contentResolver = com.android.server.policy.PermissionPolicyService.this.getContext().getContentResolver();
                    z = android.provider.Settings.Secure.getIntForUser(contentResolver, "user_setup_complete", contentResolver.getUserId()) != 0;
                } catch (android.provider.Settings.SettingNotFoundException e3) {
                    z = true;
                }
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                if (com.android.server.policy.PermissionPolicyService.this.mPackageManagerInternal.getPackage(intExtra) == null) {
                    return;
                }
                if (z) {
                    if (!this.mUserSetupUids.isEmpty()) {
                        synchronized (this.mUserSetupUids) {
                            try {
                                for (int size3 = this.mUserSetupUids.size() - 1; size3 >= 0; size3--) {
                                    updateUid(this.mUserSetupUids.get(size3).intValue());
                                }
                                this.mUserSetupUids.clear();
                            } finally {
                            }
                        }
                    }
                    updateUid(intExtra);
                    return;
                }
                synchronized (this.mUserSetupUids) {
                    try {
                        if (!this.mUserSetupUids.contains(java.lang.Integer.valueOf(intExtra))) {
                            this.mUserSetupUids.add(java.lang.Integer.valueOf(intExtra));
                        }
                    } finally {
                    }
                }
            }

            private void updateUid(int i3) {
                android.os.UserHandle userHandleForUid = android.os.UserHandle.getUserHandleForUid(i3);
                android.permission.PermissionControllerManager permissionControllerManager = this.mPermControllerManagers.get(userHandleForUid);
                if (permissionControllerManager == null) {
                    try {
                        permissionControllerManager = new android.permission.PermissionControllerManager(com.android.server.policy.PermissionPolicyService.getUserContext(com.android.server.policy.PermissionPolicyService.this.getContext(), userHandleForUid), com.android.server.PermissionThread.getHandler());
                        this.mPermControllerManagers.put(userHandleForUid, permissionControllerManager);
                    } catch (java.lang.IllegalArgumentException e3) {
                        android.util.Log.e(com.android.server.policy.PermissionPolicyService.LOG_TAG, "Could not create PermissionControllerManager for user" + userHandleForUid, e3);
                        return;
                    }
                }
                permissionControllerManager.updateUserSensitiveForApp(i3);
            }
        }, android.os.UserHandle.ALL, intentFilter, null, null);
        final android.permission.PermissionControllerManager permissionControllerManager = new android.permission.PermissionControllerManager(getUserContext(getContext(), android.os.Process.myUserHandle()), com.android.server.PermissionThread.getHandler());
        android.os.Handler handler = com.android.server.PermissionThread.getHandler();
        java.util.Objects.requireNonNull(permissionControllerManager);
        handler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.policy.PermissionPolicyService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                permissionControllerManager.updateUserSensitive();
            }
        }, 60000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getSwitchOp(@android.annotation.NonNull java.lang.String str) {
        int permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(str);
        if (permissionToOpCode == -1) {
            return -1;
        }
        return android.app.AppOpsManager.opToSwitch(permissionToOpCode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void synchronizeUidPermissionsAndAppOpsAsync(int i) {
        if (isStarted(android.os.UserHandle.getUserId(i))) {
            synchronized (this.mLock) {
                try {
                    if (!this.mIsUidSyncScheduled.get(i)) {
                        com.android.server.FgThread.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.policy.PermissionPolicyService$$ExternalSyntheticLambda5
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                ((com.android.server.policy.PermissionPolicyService) obj).synchronizeUidPermissionsAndAppOps(((java.lang.Integer) obj2).intValue());
                            }
                        }, this, java.lang.Integer.valueOf(i)));
                        this.mIsUidSyncScheduled.put(i, true);
                    }
                } finally {
                }
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 520) {
            registerCarrierPrivilegesCallbacks();
            this.mContext.registerReceiver(this.mSimConfigBroadcastReceiver, new android.content.IntentFilter("android.telephony.action.MULTI_SIM_CONFIG_CHANGED"));
        }
        if (i == 550) {
            com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
            for (int i2 : userManagerInternal.getUserIds()) {
                if (userManagerInternal.isUserRunning(i2)) {
                    onStartUser(i2);
                }
            }
        }
        if (i == 550) {
            ((com.android.server.policy.PermissionPolicyService.Internal) com.android.server.LocalServices.getService(com.android.server.policy.PermissionPolicyInternal.class)).onActivityManagerReady();
        }
        if (i == 1000) {
            synchronized (this.mLock) {
                this.mBootCompleted = true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTelephonyManagerIfNeeded() {
        if (this.mTelephonyManager == null) {
            this.mTelephonyManager = android.telephony.TelephonyManager.from(this.mContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerCarrierPrivilegesCallbacks() {
        initTelephonyManagerIfNeeded();
        if (this.mTelephonyManager == null) {
            return;
        }
        int activeModemCount = this.mTelephonyManager.getActiveModemCount();
        for (int i = 0; i < activeModemCount; i++) {
            com.android.server.policy.PermissionPolicyService.PhoneCarrierPrivilegesCallback phoneCarrierPrivilegesCallback = new com.android.server.policy.PermissionPolicyService.PhoneCarrierPrivilegesCallback(i);
            this.mPhoneCarrierPrivilegesCallbacks.add(phoneCarrierPrivilegesCallback);
            this.mTelephonyManager.registerCarrierPrivilegesCallback(i, this.mContext.getMainExecutor(), phoneCarrierPrivilegesCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterCarrierPrivilegesCallback() {
        initTelephonyManagerIfNeeded();
        if (this.mTelephonyManager == null) {
            return;
        }
        for (int i = 0; i < this.mPhoneCarrierPrivilegesCallbacks.size(); i++) {
            com.android.server.policy.PermissionPolicyService.PhoneCarrierPrivilegesCallback phoneCarrierPrivilegesCallback = this.mPhoneCarrierPrivilegesCallbacks.get(i);
            if (phoneCarrierPrivilegesCallback != null) {
                this.mTelephonyManager.unregisterCarrierPrivilegesCallback(phoneCarrierPrivilegesCallback);
            }
        }
        this.mPhoneCarrierPrivilegesCallbacks.clear();
    }

    private final class PhoneCarrierPrivilegesCallback implements android.telephony.TelephonyManager.CarrierPrivilegesCallback {
        private int mPhoneId;

        PhoneCarrierPrivilegesCallback(int i) {
            this.mPhoneId = i;
        }

        public void onCarrierPrivilegesChanged(@android.annotation.NonNull java.util.Set<java.lang.String> set, @android.annotation.NonNull java.util.Set<java.lang.Integer> set2) {
            com.android.server.policy.PermissionPolicyService.this.initTelephonyManagerIfNeeded();
            if (com.android.server.policy.PermissionPolicyService.this.mTelephonyManager == null) {
                android.util.Log.e(com.android.server.policy.PermissionPolicyService.LOG_TAG, "Cannot grant default permissions to Carrier Service app. TelephonyManager is null");
                return;
            }
            java.lang.String carrierServicePackageNameForLogicalSlot = com.android.server.policy.PermissionPolicyService.this.mTelephonyManager.getCarrierServicePackageNameForLogicalSlot(this.mPhoneId);
            if (carrierServicePackageNameForLogicalSlot == null) {
                return;
            }
            int[] userIds = ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserIds();
            android.permission.LegacyPermissionManager legacyPermissionManager = (android.permission.LegacyPermissionManager) com.android.server.policy.PermissionPolicyService.this.mContext.getSystemService(android.permission.LegacyPermissionManager.class);
            for (int i = 0; i < userIds.length; i++) {
                try {
                    com.android.server.policy.PermissionPolicyService.this.mPackageManager.getPackageInfoAsUser(carrierServicePackageNameForLogicalSlot, 0, userIds[i]);
                    legacyPermissionManager.grantDefaultPermissionsToCarrierServiceApp(carrierServicePackageNameForLogicalSlot, userIds[i]);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isStarted(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsStarted.get(i);
        }
        return z;
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        onStartUser(targetUser.getUserIdentifier());
    }

    private void onStartUser(int i) {
        com.android.server.policy.PermissionPolicyInternal.OnInitializedCallback onInitializedCallback;
        if (isStarted(i)) {
            return;
        }
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("Permission_grant_default_permissions-" + i);
        grantOrUpgradeDefaultRuntimePermissionsIfNeeded(i);
        timingsTraceAndSlog.traceEnd();
        synchronized (this.mLock) {
            this.mIsStarted.put(i, true);
            onInitializedCallback = this.mOnInitializedCallback;
        }
        timingsTraceAndSlog.traceBegin("Permission_synchronize_permissions-" + i);
        synchronizePermissionsAndAppOpsForUser(i);
        timingsTraceAndSlog.traceEnd();
        if (onInitializedCallback != null) {
            timingsTraceAndSlog.traceBegin("Permission_onInitialized-" + i);
            onInitializedCallback.onInitialized(i);
            timingsTraceAndSlog.traceEnd();
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mIsStarted.delete(targetUser.getUserIdentifier());
        }
    }

    private void grantOrUpgradeDefaultRuntimePermissionsIfNeeded(final int i) {
        if (android.permission.PermissionManager.USE_ACCESS_CHECKING_SERVICE) {
            return;
        }
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        if (packageManagerInternal.isPermissionUpgradeNeeded(i)) {
            final com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            android.permission.PermissionControllerManager permissionControllerManager = new android.permission.PermissionControllerManager(getUserContext(getContext(), android.os.UserHandle.of(i)), com.android.server.PermissionThread.getHandler());
            permissionControllerManager.grantOrUpgradeDefaultRuntimePermissions(com.android.server.PermissionThread.getExecutor(), new java.util.function.Consumer() { // from class: com.android.server.policy.PermissionPolicyService$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.policy.PermissionPolicyService.lambda$grantOrUpgradeDefaultRuntimePermissionsIfNeeded$0(androidFuture, i, (java.lang.Boolean) obj);
                }
            });
            try {
                try {
                    timingsTraceAndSlog.traceBegin("Permission_callback_waiting-" + i);
                    androidFuture.get();
                    timingsTraceAndSlog.traceEnd();
                    permissionControllerManager.updateUserSensitive();
                    packageManagerInternal.updateRuntimePermissionsFingerprint(i);
                } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                    throw new java.lang.IllegalStateException(e);
                }
            } catch (java.lang.Throwable th) {
                timingsTraceAndSlog.traceEnd();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$grantOrUpgradeDefaultRuntimePermissionsIfNeeded$0(com.android.internal.infra.AndroidFuture androidFuture, int i, java.lang.Boolean bool) {
        if (bool.booleanValue()) {
            androidFuture.complete((java.lang.Object) null);
            return;
        }
        java.lang.String str = "Error granting/upgrading runtime permissions for user " + i;
        android.util.Slog.wtf(LOG_TAG, str);
        androidFuture.completeExceptionally(new java.lang.IllegalStateException(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public static android.content.Context getUserContext(@android.annotation.NonNull android.content.Context context, @android.annotation.Nullable android.os.UserHandle userHandle) {
        if (context.getUser().equals(userHandle)) {
            return context;
        }
        try {
            return context.createPackageContextAsUser(context.getPackageName(), 0, userHandle);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(LOG_TAG, "Cannot create context for user " + userHandle, e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void synchronizeUidPermissionsAndAppOps(int i) {
        synchronized (this.mLock) {
            this.mIsUidSyncScheduled.delete(i);
        }
        com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser permissionToOpSynchroniser = new com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser(getUserContext(getContext(), android.os.UserHandle.getUserHandleForUid(i)));
        java.util.List<com.android.server.pm.pkg.AndroidPackage> packagesForAppId = this.mPackageManagerInternal.getPackagesForAppId(android.os.UserHandle.getAppId(i));
        int size = packagesForAppId.size();
        for (int i2 = 0; i2 < size; i2++) {
            permissionToOpSynchroniser.addPackage(packagesForAppId.get(i2).getPackageName());
        }
        permissionToOpSynchroniser.syncPackages();
    }

    private void synchronizePermissionsAndAppOpsForUser(int i) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        final com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser permissionToOpSynchroniser = new com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser(getUserContext(getContext(), android.os.UserHandle.of(i)));
        timingsTraceAndSlog.traceBegin("Permission_synchronize_addPackages-" + i);
        packageManagerInternal.forEachPackage(new java.util.function.Consumer() { // from class: com.android.server.policy.PermissionPolicyService$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.policy.PermissionPolicyService.lambda$synchronizePermissionsAndAppOpsForUser$1(com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.this, (com.android.server.pm.pkg.AndroidPackage) obj);
            }
        });
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceBegin("Permission_syncPackages-" + i);
        permissionToOpSynchroniser.syncPackages();
        timingsTraceAndSlog.traceEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$synchronizePermissionsAndAppOpsForUser$1(com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser permissionToOpSynchroniser, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        permissionToOpSynchroniser.addPackage(androidPackage.getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetAppOpPermissionsIfNotRequestedForUidAsync(int i) {
        if (isStarted(android.os.UserHandle.getUserId(i))) {
            synchronized (this.mLock) {
                try {
                    if (!this.mIsUidResetScheduled.get(i)) {
                        this.mIsUidResetScheduled.put(i, true);
                        com.android.server.PermissionThread.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.policy.PermissionPolicyService$$ExternalSyntheticLambda2
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                ((com.android.server.policy.PermissionPolicyService) obj).resetAppOpPermissionsIfNotRequestedForUid(((java.lang.Integer) obj2).intValue());
                            }
                        }, this, java.lang.Integer.valueOf(i)));
                    }
                } finally {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetAppOpPermissionsIfNotRequestedForUid(int i) {
        int i2;
        int i3;
        int i4;
        synchronized (this.mLock) {
            this.mIsUidResetScheduled.delete(i);
        }
        android.content.Context context = getContext();
        android.content.pm.PackageManager packageManager = getUserContext(context, android.os.UserHandle.getUserHandleForUid(i)).getPackageManager();
        java.lang.String[] packagesForUid = packageManager.getPackagesForUid(i);
        if (packagesForUid == null || packagesForUid.length == 0) {
            return;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (java.lang.String str : packagesForUid) {
            try {
                android.content.pm.PackageInfo packageInfo = packageManager.getPackageInfo(str, 4096);
                if (packageInfo != null && packageInfo.requestedPermissions != null) {
                    java.util.Collections.addAll(arraySet, packageInfo.requestedPermissions);
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }
        android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        android.app.AppOpsManagerInternal appOpsManagerInternal = (android.app.AppOpsManagerInternal) com.android.server.LocalServices.getService(android.app.AppOpsManagerInternal.class);
        int size = this.mAppOpPermissions.size();
        for (int i5 = 0; i5 < size; i5++) {
            java.lang.String str2 = this.mAppOpPermissions.get(i5);
            if (!arraySet.contains(str2)) {
                int permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(str2);
                int opToDefaultMode = android.app.AppOpsManager.opToDefaultMode(permissionToOpCode);
                int length = packagesForUid.length;
                int i6 = 0;
                while (i6 < length) {
                    java.lang.String str3 = packagesForUid[i6];
                    if (appOpsManager.unsafeCheckOpRawNoThrow(permissionToOpCode, i, str3) == opToDefaultMode) {
                        i2 = i6;
                        i3 = length;
                        i4 = opToDefaultMode;
                    } else {
                        appOpsManagerInternal.setUidModeFromPermissionPolicy(permissionToOpCode, i, opToDefaultMode, this.mAppOpsCallback);
                        i2 = i6;
                        i3 = length;
                        i4 = opToDefaultMode;
                        appOpsManagerInternal.setModeFromPermissionPolicy(permissionToOpCode, i, str3, opToDefaultMode, this.mAppOpsCallback);
                    }
                    i6 = i2 + 1;
                    length = i3;
                    opToDefaultMode = i4;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class PermissionToOpSynchroniser {

        @android.annotation.NonNull
        private final android.app.AppOpsManager mAppOpsManager;

        @android.annotation.NonNull
        private final android.content.Context mContext;

        @android.annotation.NonNull
        private final android.content.pm.PackageManager mPackageManager;

        @android.annotation.NonNull
        private final java.util.ArrayList<com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.OpToChange> mOpsToAllow = new java.util.ArrayList<>();

        @android.annotation.NonNull
        private final java.util.ArrayList<com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.OpToChange> mOpsToIgnore = new java.util.ArrayList<>();

        @android.annotation.NonNull
        private final java.util.ArrayList<com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.OpToChange> mOpsToIgnoreIfNotAllowed = new java.util.ArrayList<>();

        @android.annotation.NonNull
        private final java.util.ArrayList<com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.OpToChange> mOpsToForeground = new java.util.ArrayList<>();

        @android.annotation.NonNull
        private final android.app.AppOpsManagerInternal mAppOpsManagerInternal = (android.app.AppOpsManagerInternal) com.android.server.LocalServices.getService(android.app.AppOpsManagerInternal.class);

        @android.annotation.NonNull
        private final android.util.ArrayMap<java.lang.String, android.content.pm.PermissionInfo> mRuntimeAndTheirBgPermissionInfos = new android.util.ArrayMap<>();

        PermissionToOpSynchroniser(@android.annotation.NonNull android.content.Context context) {
            this.mContext = context;
            this.mPackageManager = context.getPackageManager();
            this.mAppOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
            java.util.List<android.content.pm.PermissionInfo> allPermissionsWithProtection = ((com.android.server.pm.permission.PermissionManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInternal.class)).getAllPermissionsWithProtection(1);
            int size = allPermissionsWithProtection.size();
            for (int i = 0; i < size; i++) {
                android.content.pm.PermissionInfo permissionInfo = allPermissionsWithProtection.get(i);
                this.mRuntimeAndTheirBgPermissionInfos.put(permissionInfo.name, permissionInfo);
                if (permissionInfo.backgroundPermission != null) {
                    java.lang.String str = permissionInfo.backgroundPermission;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            break;
                        }
                        if (!permissionInfo.backgroundPermission.equals(allPermissionsWithProtection.get(i2).name)) {
                            i2++;
                        } else {
                            str = null;
                            break;
                        }
                    }
                    if (str != null) {
                        try {
                            android.content.pm.PermissionInfo permissionInfo2 = this.mPackageManager.getPermissionInfo(str, 0);
                            this.mRuntimeAndTheirBgPermissionInfos.put(permissionInfo2.name, permissionInfo2);
                        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                            android.util.Slog.w(com.android.server.policy.PermissionPolicyService.LOG_TAG, "Unknown background permission: " + str);
                        }
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void syncPackages() {
            android.util.LongSparseLongArray longSparseLongArray = new android.util.LongSparseLongArray();
            int size = this.mOpsToAllow.size();
            for (int i = 0; i < size; i++) {
                com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.OpToChange opToChange = this.mOpsToAllow.get(i);
                setUidModeAllowed(opToChange.code, opToChange.uid, opToChange.packageName);
                longSparseLongArray.put(com.android.internal.util.IntPair.of(opToChange.uid, opToChange.code), 1L);
            }
            int size2 = this.mOpsToForeground.size();
            for (int i2 = 0; i2 < size2; i2++) {
                com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.OpToChange opToChange2 = this.mOpsToForeground.get(i2);
                if (longSparseLongArray.indexOfKey(com.android.internal.util.IntPair.of(opToChange2.uid, opToChange2.code)) < 0) {
                    setUidModeForeground(opToChange2.code, opToChange2.uid, opToChange2.packageName);
                    longSparseLongArray.put(com.android.internal.util.IntPair.of(opToChange2.uid, opToChange2.code), 1L);
                }
            }
            int size3 = this.mOpsToIgnore.size();
            for (int i3 = 0; i3 < size3; i3++) {
                com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.OpToChange opToChange3 = this.mOpsToIgnore.get(i3);
                if (longSparseLongArray.indexOfKey(com.android.internal.util.IntPair.of(opToChange3.uid, opToChange3.code)) < 0) {
                    setUidModeIgnored(opToChange3.code, opToChange3.uid, opToChange3.packageName);
                    longSparseLongArray.put(com.android.internal.util.IntPair.of(opToChange3.uid, opToChange3.code), 1L);
                }
            }
            int size4 = this.mOpsToIgnoreIfNotAllowed.size();
            for (int i4 = 0; i4 < size4; i4++) {
                com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.OpToChange opToChange4 = this.mOpsToIgnoreIfNotAllowed.get(i4);
                if (longSparseLongArray.indexOfKey(com.android.internal.util.IntPair.of(opToChange4.uid, opToChange4.code)) < 0 && setUidModeIgnoredIfNotAllowed(opToChange4.code, opToChange4.uid, opToChange4.packageName)) {
                    longSparseLongArray.put(com.android.internal.util.IntPair.of(opToChange4.uid, opToChange4.code), 1L);
                }
            }
        }

        private void addAppOps(@android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull java.lang.String str) {
            android.content.pm.PermissionInfo permissionInfo = this.mRuntimeAndTheirBgPermissionInfos.get(str);
            if (permissionInfo == null) {
                return;
            }
            addPermissionAppOp(packageInfo, androidPackage, permissionInfo);
            addExtraAppOp(packageInfo, androidPackage, permissionInfo);
        }

        private void addPermissionAppOp(@android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull android.content.pm.PermissionInfo permissionInfo) {
            int switchOp;
            if (!permissionInfo.isRuntime()) {
            }
            java.lang.String str = permissionInfo.name;
            java.lang.String str2 = packageInfo.packageName;
            android.os.UserHandle.getUserHandleForUid(packageInfo.applicationInfo.uid);
            if (((this.mPackageManager.getPermissionFlags(str, str2, this.mContext.getUser()) & 64) != 0) || (switchOp = com.android.server.policy.PermissionPolicyService.getSwitchOp(str)) == -1) {
                return;
            }
            if (shouldGrantAppOp(packageInfo, androidPackage, permissionInfo)) {
                if (permissionInfo.backgroundPermission != null) {
                    android.content.pm.PermissionInfo permissionInfo2 = this.mRuntimeAndTheirBgPermissionInfos.get(permissionInfo.backgroundPermission);
                    r3 = permissionInfo2 != null && shouldGrantAppOp(packageInfo, androidPackage, permissionInfo2) ? false : 4;
                } else {
                    r3 = false;
                }
            }
            com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.OpToChange opToChange = new com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.OpToChange(packageInfo.applicationInfo.uid, str2, switchOp);
            switch (r3) {
                case false:
                    this.mOpsToAllow.add(opToChange);
                    break;
                case true:
                    this.mOpsToIgnore.add(opToChange);
                    break;
                case true:
                    this.mOpsToForeground.add(opToChange);
                    break;
            }
        }

        private boolean shouldGrantAppOp(@android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull android.content.pm.PermissionInfo permissionInfo) {
            java.lang.String str = permissionInfo.name;
            java.lang.String str2 = packageInfo.packageName;
            if (!(this.mPackageManager.checkPermission(str, str2) == 0)) {
                return false;
            }
            int permissionFlags = this.mPackageManager.getPermissionFlags(str, str2, this.mContext.getUser());
            if ((permissionFlags & 8) == 8) {
                return false;
            }
            if (permissionInfo.isHardRestricted()) {
                return !((permissionFlags & 16384) == 16384);
            }
            if (permissionInfo.isSoftRestricted()) {
                return com.android.server.policy.SoftRestrictedPermissionPolicy.forPermission(this.mContext, packageInfo.applicationInfo, androidPackage, this.mContext.getUser(), str).mayGrantPermission();
            }
            return true;
        }

        private void addExtraAppOp(@android.annotation.NonNull android.content.pm.PackageInfo packageInfo, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull android.content.pm.PermissionInfo permissionInfo) {
            if (!permissionInfo.isSoftRestricted()) {
                return;
            }
            com.android.server.policy.SoftRestrictedPermissionPolicy forPermission = com.android.server.policy.SoftRestrictedPermissionPolicy.forPermission(this.mContext, packageInfo.applicationInfo, androidPackage, this.mContext.getUser(), permissionInfo.name);
            int extraAppOpCode = forPermission.getExtraAppOpCode();
            if (extraAppOpCode == -1) {
                return;
            }
            com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.OpToChange opToChange = new com.android.server.policy.PermissionPolicyService.PermissionToOpSynchroniser.OpToChange(packageInfo.applicationInfo.uid, packageInfo.packageName, extraAppOpCode);
            if (forPermission.mayAllowExtraAppOp()) {
                this.mOpsToAllow.add(opToChange);
            } else if (forPermission.mayDenyExtraAppOpIfGranted()) {
                this.mOpsToIgnore.add(opToChange);
            } else {
                this.mOpsToIgnoreIfNotAllowed.add(opToChange);
            }
        }

        void addPackage(@android.annotation.NonNull java.lang.String str) {
            int i;
            android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
            try {
                android.content.pm.PackageInfo packageInfo = this.mPackageManager.getPackageInfo(str, 4096);
                com.android.server.pm.pkg.AndroidPackage androidPackage = packageManagerInternal.getPackage(str);
                if (packageInfo == null || androidPackage == null || packageInfo.applicationInfo == null || packageInfo.requestedPermissions == null || (i = packageInfo.applicationInfo.uid) == 0 || i == 1000) {
                    return;
                }
                for (java.lang.String str2 : packageInfo.requestedPermissions) {
                    addAppOps(packageInfo, androidPackage, str2);
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }

        private void setUidModeAllowed(int i, int i2, @android.annotation.NonNull java.lang.String str) {
            setUidMode(i, i2, 0, str);
        }

        private void setUidModeForeground(int i, int i2, @android.annotation.NonNull java.lang.String str) {
            setUidMode(i, i2, 4, str);
        }

        private void setUidModeIgnored(int i, int i2, @android.annotation.NonNull java.lang.String str) {
            setUidMode(i, i2, 1, str);
        }

        private boolean setUidModeIgnoredIfNotAllowed(int i, int i2, @android.annotation.NonNull java.lang.String str) {
            int unsafeCheckOpRaw = this.mAppOpsManager.unsafeCheckOpRaw(android.app.AppOpsManager.opToPublicName(i), i2, str);
            if (unsafeCheckOpRaw != 0) {
                if (unsafeCheckOpRaw != 1) {
                    this.mAppOpsManagerInternal.setUidModeFromPermissionPolicy(i, i2, 1, com.android.server.policy.PermissionPolicyService.this.mAppOpsCallback);
                }
                return true;
            }
            return false;
        }

        private void setUidMode(int i, int i2, int i3, @android.annotation.NonNull java.lang.String str) {
            if (this.mAppOpsManager.unsafeCheckOpRaw(android.app.AppOpsManager.opToPublicName(i), i2, str) != i3) {
                this.mAppOpsManagerInternal.setUidModeFromPermissionPolicy(i, i2, i3, com.android.server.policy.PermissionPolicyService.this.mAppOpsCallback);
                if (this.mAppOpsManager.unsafeCheckOpRaw(android.app.AppOpsManager.opToPublicName(i), i2, str) != i3) {
                    this.mAppOpsManagerInternal.setModeFromPermissionPolicy(i, i2, str, android.app.AppOpsManager.opToDefaultMode(i), com.android.server.policy.PermissionPolicyService.this.mAppOpsCallback);
                }
            }
        }

        private class OpToChange {
            final int code;

            @android.annotation.NonNull
            final java.lang.String packageName;
            final int uid;

            OpToChange(int i, @android.annotation.NonNull java.lang.String str, int i2) {
                this.uid = i;
                this.packageName = str;
                this.code = i2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Internal extends com.android.server.policy.PermissionPolicyInternal {
        private final com.android.server.wm.ActivityInterceptorCallback mActivityInterceptorCallback;

        private Internal() {
            this.mActivityInterceptorCallback = new com.android.server.policy.PermissionPolicyService.Internal.AnonymousClass1();
        }

        /* renamed from: com.android.server.policy.PermissionPolicyService$Internal$1, reason: invalid class name */
        class AnonymousClass1 implements com.android.server.wm.ActivityInterceptorCallback {
            AnonymousClass1() {
            }

            @Override // com.android.server.wm.ActivityInterceptorCallback
            @android.annotation.Nullable
            public com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptResult onInterceptActivityLaunch(@android.annotation.NonNull com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
                return null;
            }

            @Override // com.android.server.wm.ActivityInterceptorCallback
            public void onActivityLaunched(final android.app.TaskInfo taskInfo, final android.content.pm.ActivityInfo activityInfo, final com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
                if (!com.android.server.policy.PermissionPolicyService.Internal.this.shouldShowNotificationDialogOrClearFlags(taskInfo, activityInfo.packageName, activityInterceptorInfo.getCallingPackage(), activityInterceptorInfo.getIntent(), activityInterceptorInfo.getCheckedOptions(), activityInfo.name, true) || com.android.server.policy.PermissionPolicyService.Internal.this.isNoDisplayActivity(activityInfo)) {
                    return;
                }
                if (!android.app.compat.CompatChanges.isChangeEnabled(com.android.server.policy.PermissionPolicyService.NOTIFICATION_PERM_CHANGE_ID, activityInfo.packageName, android.os.UserHandle.of(taskInfo.userId))) {
                    com.android.server.policy.PermissionPolicyService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.policy.PermissionPolicyService$Internal$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.policy.PermissionPolicyService.Internal.AnonymousClass1.this.lambda$onActivityLaunched$0(activityInfo, taskInfo, activityInterceptorInfo);
                        }
                    });
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onActivityLaunched$0(android.content.pm.ActivityInfo activityInfo, android.app.TaskInfo taskInfo, com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
                com.android.server.policy.PermissionPolicyService.Internal.this.showNotificationPromptIfNeeded(activityInfo.packageName, taskInfo.userId, taskInfo.taskId, activityInterceptorInfo);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onActivityManagerReady() {
            ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).registerActivityStartInterceptor(1, this.mActivityInterceptorCallback);
        }

        @Override // com.android.server.policy.PermissionPolicyInternal
        public boolean checkStartActivity(@android.annotation.NonNull android.content.Intent intent, int i, @android.annotation.Nullable java.lang.String str) {
            if (str != null && isActionRemovedForCallingPackage(intent, i, str)) {
                android.util.Slog.w(com.android.server.policy.PermissionPolicyService.LOG_TAG, "Action Removed: starting " + intent.toString() + " from " + str + " (uid=" + i + ")");
                return false;
            }
            if ("android.content.pm.action.REQUEST_PERMISSIONS_FOR_OTHER".equals(intent.getAction())) {
                if (i != 1000 || !"android".equals(str)) {
                    return false;
                }
                return true;
            }
            return true;
        }

        @Override // com.android.server.policy.PermissionPolicyInternal
        public void showNotificationPromptIfNeeded(@android.annotation.NonNull java.lang.String str, int i, int i2) {
            showNotificationPromptIfNeeded(str, i, i2, null);
        }

        void showNotificationPromptIfNeeded(@android.annotation.NonNull java.lang.String str, int i, int i2, @android.annotation.Nullable com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
            android.os.UserHandle of = android.os.UserHandle.of(i);
            if (str == null || i2 == -1 || !shouldForceShowNotificationPermissionRequest(str, of)) {
                return;
            }
            launchNotificationPermissionRequestDialog(str, of, i2, activityInterceptorInfo);
        }

        @Override // com.android.server.policy.PermissionPolicyInternal
        public boolean isIntentToPermissionDialog(@android.annotation.NonNull android.content.Intent intent) {
            return java.util.Objects.equals(intent.getPackage(), com.android.server.policy.PermissionPolicyService.this.mPackageManager.getPermissionControllerPackageName()) && (java.util.Objects.equals(intent.getAction(), "android.content.pm.action.REQUEST_PERMISSIONS_FOR_OTHER") || java.util.Objects.equals(intent.getAction(), "android.content.pm.action.REQUEST_PERMISSIONS"));
        }

        @Override // com.android.server.policy.PermissionPolicyInternal
        public boolean shouldShowNotificationDialogForTask(android.app.TaskInfo taskInfo, java.lang.String str, java.lang.String str2, android.content.Intent intent, java.lang.String str3) {
            return shouldShowNotificationDialogOrClearFlags(taskInfo, str, str2, intent, null, str3, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isNoDisplayActivity(@android.annotation.NonNull android.content.pm.ActivityInfo activityInfo) {
            com.android.internal.policy.AttributeCache.Entry entry;
            int themeResource = activityInfo.getThemeResource();
            if (themeResource != 0 && (entry = com.android.internal.policy.AttributeCache.instance().get(activityInfo.packageName, themeResource, com.android.internal.R.styleable.Window, 0)) != null) {
                return entry.array.getBoolean(10, false);
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean shouldShowNotificationDialogOrClearFlags(android.app.TaskInfo taskInfo, java.lang.String str, java.lang.String str2, android.content.Intent intent, android.app.ActivityOptions activityOptions, java.lang.String str3, boolean z) {
            if (intent == null || str == null || taskInfo == null || str3 == null || ((!taskInfo.isFocused || !taskInfo.isVisible || !taskInfo.isRunning) && !z)) {
                return false;
            }
            if (!isLauncherIntent(intent) && ((activityOptions == null || !activityOptions.isEligibleForLegacyPermissionPrompt()) && !isTaskStartedFromLauncher(str, taskInfo))) {
                if (!isTaskPotentialTrampoline(str3, str, str2, taskInfo, intent)) {
                    return false;
                }
                if (z && !pkgHasRunningLauncherTask(str, taskInfo)) {
                    return false;
                }
            }
            return true;
        }

        private boolean isTaskPotentialTrampoline(java.lang.String str, java.lang.String str2, java.lang.String str3, android.app.TaskInfo taskInfo, android.content.Intent intent) {
            return str2.equals(str3) && taskInfo.baseIntent.filterEquals(intent) && taskInfo.numActivities == 1 && str.equals(taskInfo.topActivityInfo.name);
        }

        private boolean pkgHasRunningLauncherTask(java.lang.String str, android.app.TaskInfo taskInfo) {
            try {
                java.util.List<android.app.ActivityManager.AppTask> appTasks = ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).getAppTasks(str, com.android.server.policy.PermissionPolicyService.this.mPackageManager.getPackageUid(str, 0));
                for (int i = 0; i < appTasks.size(); i++) {
                    android.app.ActivityManager.RecentTaskInfo taskInfo2 = appTasks.get(i).getTaskInfo();
                    if (((android.app.TaskInfo) taskInfo2).taskId != taskInfo.taskId && ((android.app.TaskInfo) taskInfo2).isFocused && ((android.app.TaskInfo) taskInfo2).isRunning && isTaskStartedFromLauncher(str, taskInfo2)) {
                        return true;
                    }
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
            return false;
        }

        private boolean isLauncherIntent(android.content.Intent intent) {
            return "android.intent.action.MAIN".equals(intent.getAction()) && intent.getCategories() != null && (intent.getCategories().contains("android.intent.category.LAUNCHER") || intent.getCategories().contains("android.intent.category.LEANBACK_LAUNCHER") || intent.getCategories().contains("android.intent.category.CAR_LAUNCHER"));
        }

        private boolean isTaskStartedFromLauncher(java.lang.String str, android.app.TaskInfo taskInfo) {
            return taskInfo.baseActivity != null && str.equals(taskInfo.baseActivity.getPackageName()) && isLauncherIntent(taskInfo.baseIntent);
        }

        private void launchNotificationPermissionRequestDialog(java.lang.String str, android.os.UserHandle userHandle, int i, @android.annotation.Nullable com.android.server.wm.ActivityInterceptorCallback.ActivityInterceptorInfo activityInterceptorInfo) {
            android.app.ActivityOptions activityOptions;
            android.content.Intent buildRequestPermissionsIntent = com.android.server.policy.PermissionPolicyService.this.mPackageManager.buildRequestPermissionsIntent(new java.lang.String[]{"android.permission.POST_NOTIFICATIONS"});
            buildRequestPermissionsIntent.addFlags(268697600);
            buildRequestPermissionsIntent.setAction("android.content.pm.action.REQUEST_PERMISSIONS_FOR_OTHER");
            buildRequestPermissionsIntent.putExtra("android.intent.extra.PACKAGE_NAME", str);
            boolean z = (activityInterceptorInfo == null || activityInterceptorInfo.getCheckedOptions() == null || activityInterceptorInfo.getCheckedOptions().getAnimationType() != 13 || activityInterceptorInfo.getClearOptionsAnimationRunnable() == null) ? false : true;
            if (z) {
                activityOptions = android.app.ActivityOptions.makeRemoteAnimation(activityInterceptorInfo.getCheckedOptions().getRemoteAnimationAdapter(), activityInterceptorInfo.getCheckedOptions().getRemoteTransition());
            } else {
                activityOptions = new android.app.ActivityOptions(new android.os.Bundle());
            }
            activityOptions.setTaskOverlay(true, false);
            activityOptions.setLaunchTaskId(i);
            if (z) {
                activityInterceptorInfo.getClearOptionsAnimationRunnable().run();
            }
            try {
                com.android.server.policy.PermissionPolicyService.this.mContext.startActivityAsUser(buildRequestPermissionsIntent, activityOptions.toBundle(), userHandle);
            } catch (java.lang.Exception e) {
                android.util.Log.e(com.android.server.policy.PermissionPolicyService.LOG_TAG, "couldn't start grant permission dialogfor other package " + str, e);
            }
        }

        @Override // com.android.server.policy.PermissionPolicyInternal
        public boolean isInitialized(int i) {
            return com.android.server.policy.PermissionPolicyService.this.isStarted(i);
        }

        @Override // com.android.server.policy.PermissionPolicyInternal
        public void setOnInitializedCallback(@android.annotation.NonNull com.android.server.policy.PermissionPolicyInternal.OnInitializedCallback onInitializedCallback) {
            synchronized (com.android.server.policy.PermissionPolicyService.this.mLock) {
                com.android.server.policy.PermissionPolicyService.this.mOnInitializedCallback = onInitializedCallback;
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private boolean isActionRemovedForCallingPackage(@android.annotation.NonNull android.content.Intent intent, int i, @android.annotation.NonNull java.lang.String str) {
            char c;
            java.lang.String action = intent.getAction();
            if (action == null) {
                return false;
            }
            switch (action.hashCode()) {
                case -1673968409:
                    if (action.equals("android.provider.Telephony.ACTION_CHANGE_DEFAULT")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 579418056:
                    if (action.equals("android.telecom.action.CHANGE_DEFAULT_DIALER")) {
                        c = 0;
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
                    try {
                        if (com.android.server.policy.PermissionPolicyService.this.getContext().getPackageManager().getApplicationInfoAsUser(str, 0, android.os.UserHandle.getUserId(i)).targetSdkVersion >= 29) {
                        }
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        android.util.Slog.i(com.android.server.policy.PermissionPolicyService.LOG_TAG, "Cannot find application info for " + str);
                    }
                    intent.putExtra("android.intent.extra.CALLING_PACKAGE", str);
                    break;
            }
            return false;
        }

        private boolean shouldForceShowNotificationPermissionRequest(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
            boolean z;
            boolean z2;
            boolean z3;
            com.android.server.pm.pkg.AndroidPackage androidPackage = com.android.server.policy.PermissionPolicyService.this.mPackageManagerInternal.getPackage(str);
            if (androidPackage == null || androidPackage.getPackageName() == null || java.util.Objects.equals(str, com.android.server.policy.PermissionPolicyService.this.mPackageManager.getPermissionControllerPackageName()) || androidPackage.getTargetSdkVersion() < 23) {
                if (androidPackage == null) {
                    android.util.Slog.w(com.android.server.policy.PermissionPolicyService.LOG_TAG, "Cannot check for Notification prompt, no package for " + str);
                }
                return false;
            }
            synchronized (com.android.server.policy.PermissionPolicyService.this.mLock) {
                try {
                    if (!com.android.server.policy.PermissionPolicyService.this.mBootCompleted) {
                        return false;
                    }
                    if (!androidPackage.getRequestedPermissions().contains("android.permission.POST_NOTIFICATIONS") || android.app.compat.CompatChanges.isChangeEnabled(com.android.server.policy.PermissionPolicyService.NOTIFICATION_PERM_CHANGE_ID, str, userHandle) || com.android.server.policy.PermissionPolicyService.this.mKeyguardManager.isKeyguardLocked()) {
                        return false;
                    }
                    int uid = userHandle.getUid(androidPackage.getUid());
                    if (com.android.server.policy.PermissionPolicyService.this.mNotificationManager == null) {
                        com.android.server.policy.PermissionPolicyService.this.mNotificationManager = (com.android.server.notification.NotificationManagerInternal) com.android.server.LocalServices.getService(com.android.server.notification.NotificationManagerInternal.class);
                    }
                    if (com.android.server.policy.PermissionPolicyService.this.mNotificationManager.getNumNotificationChannelsForPackage(str, uid, true) <= 0) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (com.android.server.policy.PermissionPolicyService.this.mPermissionManagerInternal.checkUidPermission(uid, "android.permission.POST_NOTIFICATIONS", 0) != 0) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if ((com.android.server.policy.PermissionPolicyService.this.mPackageManager.getPermissionFlags("android.permission.POST_NOTIFICATIONS", str, userHandle) & 32823) == 0) {
                        z3 = false;
                    } else {
                        z3 = true;
                    }
                    if (z2 || !z || z3) {
                        return false;
                    }
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
