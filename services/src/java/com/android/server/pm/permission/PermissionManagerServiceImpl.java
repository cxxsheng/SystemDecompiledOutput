package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public class PermissionManagerServiceImpl implements com.android.server.pm.permission.PermissionManagerServiceInterface {
    private static final long BACKGROUND_RATIONALE_CHANGE_ID = 147316723;
    private static final int BLOCKING_PERMISSION_FLAGS = 52;
    private static final int MAX_PERMISSION_TREE_FOOTPRINT = 32768;
    private static final java.lang.String SKIP_KILL_APP_REASON_NOTIFICATION_TEST = "skip permission revoke app kill for notification test";
    private static final java.lang.String TAG = "PermissionManager";
    private static final int UPDATE_PERMISSIONS_ALL = 1;
    private static final int UPDATE_PERMISSIONS_REPLACE_ALL = 4;
    private static final int UPDATE_PERMISSIONS_REPLACE_PKG = 2;
    private static final int USER_PERMISSION_FLAGS = 3;

    @android.annotation.NonNull
    private final com.android.server.pm.ApexManager mApexManager;
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final int[] mGlobalGids;
    private final android.os.Handler mHandler;
    private final boolean mIsLeanback;

    @android.annotation.NonNull
    private final com.android.server.pm.permission.PermissionManagerServiceImpl.OnPermissionChangeListeners mOnPermissionChangeListeners;
    private final android.content.pm.PackageManagerInternal mPackageManagerInt;
    private android.permission.PermissionControllerManager mPermissionControllerManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.policy.PermissionPolicyInternal mPermissionPolicyInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.util.ArraySet<java.lang.String> mPrivappPermissionsViolations;
    private final android.util.SparseArray<android.util.ArraySet<java.lang.String>> mSystemPermissions;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSystemReady;
    private final com.android.server.pm.UserManagerInternal mUserManagerInt;
    private static final java.lang.String LOG_TAG = com.android.server.pm.permission.PermissionManagerServiceImpl.class.getSimpleName();
    private static final long BACKUP_TIMEOUT_MILLIS = java.util.concurrent.TimeUnit.SECONDS.toMillis(60);
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final java.util.List<java.lang.String> STORAGE_PERMISSIONS = new java.util.ArrayList();
    private static final java.util.Set<java.lang.String> READ_MEDIA_AURAL_PERMISSIONS = new android.util.ArraySet();
    private static final java.util.Set<java.lang.String> READ_MEDIA_VISUAL_PERMISSIONS = new android.util.ArraySet();
    private static final java.util.List<java.lang.String> NEARBY_DEVICES_PERMISSIONS = new java.util.ArrayList();
    private static final java.util.List<java.lang.String> NOTIFICATION_PERMISSIONS = new java.util.ArrayList();
    private static final java.util.Map<java.lang.String, java.lang.String> FULLER_PERMISSION_MAP = new java.util.HashMap();
    private final android.util.ArraySet<java.lang.String> mPrivilegedPermissionAllowlistSourcePackageNames = new android.util.ArraySet<>();
    private final com.android.server.pm.PackageManagerTracedLock mLock = new com.android.server.pm.PackageManagerTracedLock();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.pm.permission.DevicePermissionState mState = new com.android.server.pm.permission.DevicePermissionState();
    private final com.android.internal.logging.MetricsLogger mMetricsLogger = new com.android.internal.logging.MetricsLogger();
    private final com.android.internal.compat.IPlatformCompat mPlatformCompat = com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService("platform_compat"));

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.pm.permission.PermissionRegistry mRegistry = new com.android.server.pm.permission.PermissionRegistry();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mHasNoDelayedPermBackup = new android.util.SparseBooleanArray();
    private final com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback mDefaultPermissionCallback = new com.android.server.pm.permission.PermissionManagerServiceImpl.AnonymousClass1();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface UpdatePermissionFlags {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: -$$Nest$smkillUid, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m6424$$Nest$smkillUid(int i, int i2, java.lang.String str) {
        killUid(i, i2, str);
    }

    static {
        FULLER_PERMISSION_MAP.put("android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION");
        FULLER_PERMISSION_MAP.put("android.permission.INTERACT_ACROSS_USERS", "android.permission.INTERACT_ACROSS_USERS_FULL");
        STORAGE_PERMISSIONS.add("android.permission.READ_EXTERNAL_STORAGE");
        STORAGE_PERMISSIONS.add("android.permission.WRITE_EXTERNAL_STORAGE");
        READ_MEDIA_AURAL_PERMISSIONS.add("android.permission.READ_MEDIA_AUDIO");
        READ_MEDIA_VISUAL_PERMISSIONS.add("android.permission.READ_MEDIA_VIDEO");
        READ_MEDIA_VISUAL_PERMISSIONS.add("android.permission.READ_MEDIA_IMAGES");
        READ_MEDIA_VISUAL_PERMISSIONS.add("android.permission.ACCESS_MEDIA_LOCATION");
        READ_MEDIA_VISUAL_PERMISSIONS.add("android.permission.READ_MEDIA_VISUAL_USER_SELECTED");
        NEARBY_DEVICES_PERMISSIONS.add("android.permission.BLUETOOTH_ADVERTISE");
        NEARBY_DEVICES_PERMISSIONS.add("android.permission.BLUETOOTH_CONNECT");
        NEARBY_DEVICES_PERMISSIONS.add("android.permission.BLUETOOTH_SCAN");
        NOTIFICATION_PERMISSIONS.add("android.permission.POST_NOTIFICATIONS");
    }

    /* renamed from: com.android.server.pm.permission.PermissionManagerServiceImpl$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback {
        AnonymousClass1() {
            super();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onGidsChanged(final int i, final int i2) {
            com.android.server.pm.permission.PermissionManagerServiceImpl.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.permission.PermissionManagerServiceImpl.m6424$$Nest$smkillUid(i, i2, "permission grant or revoke changed gids");
                }
            });
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onPermissionGranted(int i, int i2) {
            com.android.server.pm.permission.PermissionManagerServiceImpl.this.mOnPermissionChangeListeners.onPermissionsChanged(i);
            com.android.server.pm.permission.PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(true);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onInstallPermissionGranted() {
            com.android.server.pm.permission.PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(true);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onPermissionRevoked(final int i, final int i2, final java.lang.String str, boolean z, @android.annotation.Nullable final java.lang.String str2) {
            com.android.server.pm.permission.PermissionManagerServiceImpl.this.mOnPermissionChangeListeners.onPermissionsChanged(i);
            com.android.server.pm.permission.PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(false);
            if (z) {
                return;
            }
            com.android.server.pm.permission.PermissionManagerServiceImpl.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.permission.PermissionManagerServiceImpl.AnonymousClass1.this.lambda$onPermissionRevoked$1(str2, i, str, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPermissionRevoked$1(java.lang.String str, int i, java.lang.String str2, int i2) {
            if ("android.permission.POST_NOTIFICATIONS".equals(str) && isAppBackupAndRestoreRunning(i)) {
                return;
            }
            int appId = android.os.UserHandle.getAppId(i);
            if (str2 == null) {
                com.android.server.pm.permission.PermissionManagerServiceImpl.killUid(appId, i2, "permissions revoked");
            } else {
                com.android.server.pm.permission.PermissionManagerServiceImpl.killUid(appId, i2, str2);
            }
        }

        private boolean isAppBackupAndRestoreRunning(int i) {
            if (com.android.server.pm.permission.PermissionManagerServiceImpl.this.checkUidPermission(i, "android.permission.BACKUP") != 0) {
                return false;
            }
            try {
                int userId = android.os.UserHandle.getUserId(i);
                return (android.provider.Settings.Secure.getIntForUser(com.android.server.pm.permission.PermissionManagerServiceImpl.this.mContext.getContentResolver(), "user_setup_complete", userId) == 0) || (android.provider.Settings.Secure.getIntForUser(com.android.server.pm.permission.PermissionManagerServiceImpl.this.mContext.getContentResolver(), "user_setup_personalization_state", userId) == 1);
            } catch (android.provider.Settings.SettingNotFoundException e) {
                android.util.Slog.w(com.android.server.pm.permission.PermissionManagerServiceImpl.LOG_TAG, "Failed to check if the user is in restore: " + e);
                return false;
            }
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onInstallPermissionRevoked() {
            com.android.server.pm.permission.PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(true);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onPermissionUpdated(int[] iArr, boolean z, int i) {
            for (int i2 : iArr) {
                com.android.server.pm.permission.PermissionManagerServiceImpl.this.mOnPermissionChangeListeners.onPermissionsChanged(android.os.UserHandle.getUid(i2, i));
            }
            com.android.server.pm.permission.PermissionManagerServiceImpl.this.mPackageManagerInt.writePermissionSettings(iArr, !z);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onInstallPermissionUpdated() {
            com.android.server.pm.permission.PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(true);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
        public void onPermissionRemoved() {
            com.android.server.pm.permission.PermissionManagerServiceImpl.this.mPackageManagerInt.writeSettings(false);
        }
    }

    public PermissionManagerServiceImpl(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, android.content.pm.FeatureInfo> arrayMap) {
        java.lang.String str;
        android.content.pm.PackageManager.invalidatePackageInfoCache();
        android.permission.PermissionManager.disablePackageNamePermissionCache();
        this.mContext = context;
        this.mPackageManagerInt = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mUserManagerInt = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        this.mIsLeanback = arrayMap.containsKey("android.software.leanback");
        this.mApexManager = com.android.server.pm.ApexManager.getInstance();
        this.mPrivilegedPermissionAllowlistSourcePackageNames.add(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
        if (arrayMap.containsKey("android.hardware.type.automotive") && (str = android.os.SystemProperties.get("ro.android.car.carservice.package", (java.lang.String) null)) != null) {
            this.mPrivilegedPermissionAllowlistSourcePackageNames.add(str);
        }
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(TAG, 10, true);
        serviceThread.start();
        this.mHandler = new android.os.Handler(serviceThread.getLooper());
        com.android.server.Watchdog.getInstance().addThread(this.mHandler);
        com.android.server.SystemConfig systemConfig = com.android.server.SystemConfig.getInstance();
        this.mSystemPermissions = systemConfig.getSystemPermissions();
        this.mGlobalGids = systemConfig.getGlobalGids();
        this.mOnPermissionChangeListeners = new com.android.server.pm.permission.PermissionManagerServiceImpl.OnPermissionChangeListeners(com.android.server.FgThread.get().getLooper());
        android.util.ArrayMap<java.lang.String, com.android.server.SystemConfig.PermissionEntry> permissions = com.android.server.SystemConfig.getInstance().getPermissions();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            for (int i = 0; i < permissions.size(); i++) {
                try {
                    com.android.server.SystemConfig.PermissionEntry valueAt = permissions.valueAt(i);
                    com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(valueAt.name);
                    if (permission == null) {
                        permission = new com.android.server.pm.permission.Permission(valueAt.name, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, 1);
                        this.mRegistry.addPermission(permission);
                    }
                    if (valueAt.gids != null) {
                        permission.setGids(valueAt.gids, valueAt.perUser);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void killUid(int i, int i2, java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.app.IActivityManager service = android.app.ActivityManager.getService();
            if (service != null) {
                try {
                    service.killUidForPermissionChange(i, i2, str);
                } catch (android.os.RemoteException e) {
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.NonNull
    private java.lang.String[] getAppOpPermissionPackagesInternal(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                android.util.ArraySet<java.lang.String> appOpPermissionPackages = this.mRegistry.getAppOpPermissionPackages(str);
                if (appOpPermissionPackages == null) {
                    java.lang.String[] strArr = libcore.util.EmptyArray.STRING;
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return strArr;
                }
                java.lang.String[] strArr2 = (java.lang.String[]) appOpPermissionPackages.toArray(new java.lang.String[0]);
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return strArr2;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<android.content.pm.PermissionGroupInfo> getAllPermissionGroups(int i) {
        final int callingUid = android.os.Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                java.util.Iterator<com.android.internal.pm.pkg.component.ParsedPermissionGroup> it = this.mRegistry.getPermissionGroups().iterator();
                while (it.hasNext()) {
                    arrayList.add(com.android.server.pm.parsing.PackageInfoUtils.generatePermissionGroupInfo(it.next(), i));
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        final int userId = android.os.UserHandle.getUserId(callingUid);
        arrayList.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getAllPermissionGroups$0;
                lambda$getAllPermissionGroups$0 = com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$getAllPermissionGroups$0(callingUid, userId, (android.content.pm.PermissionGroupInfo) obj);
                return lambda$getAllPermissionGroups$0;
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getAllPermissionGroups$0(int i, int i2, android.content.pm.PermissionGroupInfo permissionGroupInfo) {
        return this.mPackageManagerInt.filterAppAccess(permissionGroupInfo.packageName, i, i2, false);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.Nullable
    public android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.internal.pm.pkg.component.ParsedPermissionGroup permissionGroup = this.mRegistry.getPermissionGroup(str);
                if (permissionGroup == null) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return null;
                }
                android.content.pm.PermissionGroupInfo generatePermissionGroupInfo = com.android.server.pm.parsing.PackageInfoUtils.generatePermissionGroupInfo(permissionGroup, i);
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                if (this.mPackageManagerInt.filterAppAccess(generatePermissionGroupInfo.packageName, callingUid, android.os.UserHandle.getUserId(callingUid), false)) {
                    android.util.EventLog.writeEvent(1397638484, "186113473", java.lang.Integer.valueOf(callingUid), str);
                    return null;
                }
                return generatePermissionGroupInfo;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.Nullable
    public android.content.pm.PermissionInfo getPermissionInfo(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        int callingUid = android.os.Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        int permissionInfoCallingTargetSdkVersion = getPermissionInfoCallingTargetSdkVersion(this.mPackageManagerInt.getPackage(str2), callingUid);
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str);
                if (permission == null) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return null;
                }
                android.content.pm.PermissionInfo generatePermissionInfo = permission.generatePermissionInfo(i, permissionInfoCallingTargetSdkVersion);
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                if (this.mPackageManagerInt.filterAppAccess(generatePermissionInfo.packageName, callingUid, android.os.UserHandle.getUserId(callingUid), false)) {
                    android.util.EventLog.writeEvent(1397638484, "183122164", java.lang.Integer.valueOf(callingUid), str);
                    return null;
                }
                return generatePermissionInfo;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    private int getPermissionInfoCallingTargetSdkVersion(@android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        int appId = android.os.UserHandle.getAppId(i);
        if (appId == 0 || appId == 1000 || appId == 2000 || androidPackage == null) {
            return 10000;
        }
        return androidPackage.getTargetSdkVersion();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.Nullable
    public java.util.List<android.content.pm.PermissionInfo> queryPermissionsByGroup(java.lang.String str, int i) {
        final int callingUid = android.os.Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(10);
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.internal.pm.pkg.component.ParsedPermissionGroup permissionGroup = this.mRegistry.getPermissionGroup(str);
                if (str != null && permissionGroup == null) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return null;
                }
                for (com.android.server.pm.permission.Permission permission : this.mRegistry.getPermissions()) {
                    if (java.util.Objects.equals(permission.getGroup(), str)) {
                        arrayList.add(permission.generatePermissionInfo(i));
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                final int userId = android.os.UserHandle.getUserId(callingUid);
                if (permissionGroup != null && this.mPackageManagerInt.filterAppAccess(permissionGroup.getPackageName(), callingUid, userId, false)) {
                    return null;
                }
                arrayList.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$queryPermissionsByGroup$1;
                        lambda$queryPermissionsByGroup$1 = com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$queryPermissionsByGroup$1(callingUid, userId, (android.content.pm.PermissionInfo) obj);
                        return lambda$queryPermissionsByGroup$1;
                    }
                });
                return arrayList;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$queryPermissionsByGroup$1(int i, int i2, android.content.pm.PermissionInfo permissionInfo) {
        return this.mPackageManagerInt.filterAppAccess(permissionInfo.packageName, i, i2, false);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean addPermission(android.content.pm.PermissionInfo permissionInfo, boolean z) {
        boolean z2;
        boolean addToTree;
        int callingUid = android.os.Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            throw new java.lang.SecurityException("Instant apps can't add permissions");
        }
        if (permissionInfo.labelRes == 0 && permissionInfo.nonLocalizedLabel == null) {
            throw new java.lang.SecurityException("Label must be specified in permission");
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.Permission enforcePermissionTree = this.mRegistry.enforcePermissionTree(permissionInfo.name, callingUid);
                com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(permissionInfo.name);
                z2 = permission == null;
                int fixProtectionLevel = android.content.pm.PermissionInfo.fixProtectionLevel(permissionInfo.protectionLevel);
                enforcePermissionCapLocked(permissionInfo, enforcePermissionTree);
                if (z2) {
                    permission = new com.android.server.pm.permission.Permission(permissionInfo.name, enforcePermissionTree.getPackageName(), 2);
                } else if (!permission.isDynamic()) {
                    throw new java.lang.SecurityException("Not allowed to modify non-dynamic permission " + permissionInfo.name);
                }
                addToTree = permission.addToTree(fixProtectionLevel, permissionInfo, enforcePermissionTree);
                if (z2) {
                    this.mRegistry.addPermission(permission);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (addToTree) {
            this.mPackageManagerInt.writeSettings(z);
        }
        return z2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void removePermission(java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            throw new java.lang.SecurityException("Instant applications don't have access to this method");
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mRegistry.enforcePermissionTree(str, callingUid);
                com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str);
                if (permission == null) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                if (!permission.isDynamic()) {
                    android.util.Slog.wtf(TAG, "Not allowed to modify non-dynamic permission " + str);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                this.mRegistry.removePermission(str);
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                this.mPackageManagerInt.writeSettings(false);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int getPermissionFlags(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        return getPermissionFlagsInternal(str, str2, android.os.Binder.getCallingUid(), i);
    }

    private int getPermissionFlagsInternal(java.lang.String str, java.lang.String str2, int i, int i2) {
        if (!this.mUserManagerInt.exists(i2)) {
            return 0;
        }
        enforceGrantRevokeGetRuntimePermissionPermissions("getPermissionFlags");
        enforceCrossUserPermission(i, i2, true, false, "getPermissionFlags");
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null || this.mPackageManagerInt.filterAppAccess(str, i, i2, false)) {
            return 0;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                if (this.mRegistry.getPermission(str2) == null) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return 0;
                }
                com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i2);
                if (uidStateLocked == null) {
                    android.util.Slog.e(TAG, "Missing permissions state for " + str + " and user " + i2);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return 0;
                }
                int permissionFlags = uidStateLocked.getPermissionFlags(str2);
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return permissionFlags;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void updatePermissionFlags(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, java.lang.String str3, int i3) {
        boolean z2;
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 1000 && callingUid != 0 && (i & 4) != 0) {
            if (z) {
                this.mContext.enforceCallingOrSelfPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY", "Need android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY to change policy flags");
            } else if (this.mPackageManagerInt.getUidTargetSdkVersion(callingUid) >= 29) {
                throw new java.lang.IllegalArgumentException("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY needs  to be checked for packages targeting 29 or later when changing policy flags");
            }
            z2 = true;
        } else {
            z2 = false;
        }
        updatePermissionFlagsInternal(str, str2, i, i2, callingUid, i3, z2, this.mDefaultPermissionCallback);
    }

    private void updatePermissionFlagsInternal(java.lang.String str, java.lang.String str2, int i, int i2, int i3, int i4, boolean z, com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback permissionCallback) {
        boolean z2;
        if (!this.mUserManagerInt.exists(i4)) {
            return;
        }
        enforceGrantRevokeRuntimePermissionPermissions("updatePermissionFlags");
        enforceCrossUserPermission(i3, i4, true, true, "updatePermissionFlags");
        if ((i & 4) != 0 && !z) {
            throw new java.lang.SecurityException("updatePermissionFlags requires android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY");
        }
        if (i3 != 1000) {
            i = i & (-17) & (-33);
            i2 = i2 & (-17) & (-33) & (-4097) & (-2049) & (-8193) & (-16385);
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null) {
            android.util.Log.e(TAG, "Unknown package: " + str);
            return;
        }
        if (this.mPackageManagerInt.filterAppAccess(str, i3, i4, false)) {
            throw new java.lang.IllegalArgumentException("Unknown package: " + str);
        }
        boolean z3 = true;
        if (!androidPackage.getRequestedPermissions().contains(str2)) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (!z2) {
            for (java.lang.String str3 : this.mPackageManagerInt.getSharedUserPackagesForPackage(str, i4)) {
                com.android.server.pm.pkg.AndroidPackage androidPackage2 = this.mPackageManagerInt.getPackage(str3);
                if (androidPackage2 != null && androidPackage2.getRequestedPermissions().contains(str2)) {
                    break;
                }
            }
        }
        z3 = z2;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str2);
                if (permission == null) {
                    throw new java.lang.IllegalArgumentException("Unknown permission: " + str2);
                }
                boolean isRuntime = permission.isRuntime();
                com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i4);
                if (uidStateLocked == null) {
                    android.util.Slog.e(TAG, "Missing permissions state for " + str + " and user " + i4);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                if (!uidStateLocked.hasPermissionState(str2) && !z3) {
                    android.util.Log.e(TAG, "Permission " + str2 + " isn't requested by package " + str);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                boolean updatePermissionFlags = uidStateLocked.updatePermissionFlags(permission, i, i2);
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                if (updatePermissionFlags && permissionCallback != null) {
                    if (!isRuntime) {
                        permissionCallback.onInstallPermissionUpdated();
                    } else {
                        permissionCallback.onPermissionUpdated(new int[]{i4}, false, androidPackage.getUid());
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void updatePermissionFlagsForAllApps(int i, int i2, final int i3) {
        int callingUid = android.os.Binder.getCallingUid();
        if (!this.mUserManagerInt.exists(i3)) {
            return;
        }
        enforceGrantRevokeRuntimePermissionPermissions("updatePermissionFlagsForAllApps");
        enforceCrossUserPermission(callingUid, i3, true, true, "updatePermissionFlagsForAllApps");
        final int i4 = callingUid != 1000 ? i : i & (-17);
        final int i5 = callingUid != 1000 ? i2 : i2 & (-17);
        final boolean[] zArr = new boolean[1];
        this.mPackageManagerInt.forEachPackage(new java.util.function.Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$updatePermissionFlagsForAllApps$2(i3, zArr, i4, i5, (com.android.server.pm.pkg.AndroidPackage) obj);
            }
        });
        if (zArr[0]) {
            this.mPackageManagerInt.writePermissionSettings(new int[]{i3}, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePermissionFlagsForAllApps$2(int i, boolean[] zArr, int i2, int i3, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i);
                if (uidStateLocked == null) {
                    android.util.Slog.e(TAG, "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                zArr[0] = uidStateLocked.updatePermissionFlagsForAllPermissions(i2, i3) | zArr[0];
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                this.mOnPermissionChangeListeners.onPermissionsChanged(androidPackage.getUid());
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    private int checkPermission(java.lang.String str, java.lang.String str2, int i) {
        return checkPermission(str, str2, "default:0", i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int checkPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        if (this.mUserManagerInt.exists(i) && (androidPackage = this.mPackageManagerInt.getPackage(str)) != null) {
            return checkPermissionInternal(androidPackage, true, str2, i);
        }
        return -1;
    }

    private int checkPermissionInternal(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, @android.annotation.NonNull java.lang.String str, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        if (z || androidPackage.getSharedUserId() == null) {
            if (this.mPackageManagerInt.filterAppAccess(androidPackage.getPackageName(), callingUid, i, false)) {
                return -1;
            }
        } else if (this.mPackageManagerInt.getInstantAppPackageName(callingUid) != null) {
            return -1;
        }
        boolean z2 = this.mPackageManagerInt.getInstantAppPackageName(android.os.UserHandle.getUid(i, androidPackage.getUid())) != null;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i);
                if (uidStateLocked == null) {
                    android.util.Slog.e(TAG, "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return -1;
                }
                if (checkSinglePermissionInternalLocked(uidStateLocked, str, z2)) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return 0;
                }
                java.lang.String str2 = FULLER_PERMISSION_MAP.get(str);
                if (str2 == null || !checkSinglePermissionInternalLocked(uidStateLocked, str2, z2)) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return -1;
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return 0;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean checkSinglePermissionInternalLocked(@android.annotation.NonNull com.android.server.pm.permission.UidPermissionState uidPermissionState, @android.annotation.NonNull java.lang.String str, boolean z) {
        if (!uidPermissionState.isPermissionGranted(str)) {
            return false;
        }
        if (!z) {
            return true;
        }
        com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str);
        return permission != null && permission.isInstant();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int checkUidPermission(int i, java.lang.String str) {
        return checkUidPermission(i, str, "default:0");
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int checkUidPermission(int i, java.lang.String str, java.lang.String str2) {
        if (!this.mUserManagerInt.exists(android.os.UserHandle.getUserId(i))) {
            return -1;
        }
        return checkUidPermissionInternal(this.mPackageManagerInt.getPackage(i), i, str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> getAllPermissionStates(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        throw new java.lang.UnsupportedOperationException("This method is supported in newer implementation only");
    }

    private int checkUidPermissionInternal(@android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, int i, @android.annotation.NonNull java.lang.String str) {
        if (androidPackage != null) {
            return checkPermissionInternal(androidPackage, false, str, android.os.UserHandle.getUserId(i));
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                if (checkSingleUidPermissionInternalLocked(i, str)) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return 0;
                }
                java.lang.String str2 = FULLER_PERMISSION_MAP.get(str);
                if (str2 != null && checkSingleUidPermissionInternalLocked(i, str2)) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return 0;
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return -1;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean checkSingleUidPermissionInternalLocked(int i, @android.annotation.NonNull java.lang.String str) {
        android.util.ArraySet<java.lang.String> arraySet = this.mSystemPermissions.get(i);
        return arraySet != null && arraySet.contains(str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void addOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.OBSERVE_GRANT_REVOKE_PERMISSIONS", "addOnPermissionsChangeListener");
        this.mOnPermissionChangeListeners.addListener(iOnPermissionsChangeListener);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void removeOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        if (this.mPackageManagerInt.getInstantAppPackageName(android.os.Binder.getCallingUid()) != null) {
            throw new java.lang.SecurityException("Instant applications don't have access to this method");
        }
        this.mOnPermissionChangeListeners.removeListener(iOnPermissionsChangeListener);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.Nullable
    public java.util.List<java.lang.String> getAllowlistedRestrictedPermissions(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        java.util.Objects.requireNonNull(str);
        com.android.internal.util.Preconditions.checkFlagsArgument(i, 7);
        com.android.internal.util.Preconditions.checkArgumentNonNegative(i2, (java.lang.String) null);
        if (android.os.UserHandle.getCallingUserId() != i2) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "getAllowlistedRestrictedPermissions for user " + i2);
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null) {
            return null;
        }
        int callingUid = android.os.Binder.getCallingUid();
        if (this.mPackageManagerInt.filterAppAccess(str, callingUid, android.os.UserHandle.getCallingUserId(), false)) {
            return null;
        }
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.WHITELIST_RESTRICTED_PERMISSIONS") == 0;
        boolean isCallerInstallerOfRecord = this.mPackageManagerInt.isCallerInstallerOfRecord(androidPackage, callingUid);
        if ((i & 1) != 0 && !z) {
            throw new java.lang.SecurityException("Querying system allowlist requires android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
        }
        if ((i & 6) != 0 && !z && !isCallerInstallerOfRecord) {
            throw new java.lang.SecurityException("Querying upgrade or installer allowlist requires being installer on record or android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return getAllowlistedRestrictedPermissionsInternal(androidPackage, i, i2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.Nullable
    private java.util.List<java.lang.String> getAllowlistedRestrictedPermissionsInternal(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2) {
        int i3;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i2);
                java.util.ArrayList arrayList = null;
                if (uidStateLocked == null) {
                    android.util.Slog.e(TAG, "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return null;
                }
                if ((i & 1) == 0) {
                    i3 = 0;
                } else {
                    i3 = 4096;
                }
                if ((i & 4) != 0) {
                    i3 |= 8192;
                }
                if ((i & 2) != 0) {
                    i3 |= 2048;
                }
                for (java.lang.String str : androidPackage.getRequestedPermissions()) {
                    if ((uidStateLocked.getPermissionFlags(str) & i3) != 0) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(str);
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return arrayList;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean addAllowlistedRestrictedPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2) {
        java.util.Objects.requireNonNull(str2);
        if (!checkExistsAndEnforceCannotModifyImmutablyRestrictedPermission(str2)) {
            return false;
        }
        java.util.List<java.lang.String> allowlistedRestrictedPermissions = getAllowlistedRestrictedPermissions(str, i, i2);
        if (allowlistedRestrictedPermissions == null) {
            allowlistedRestrictedPermissions = new java.util.ArrayList<>(1);
        }
        if (allowlistedRestrictedPermissions.indexOf(str2) >= 0) {
            return false;
        }
        allowlistedRestrictedPermissions.add(str2);
        return setAllowlistedRestrictedPermissions(str, allowlistedRestrictedPermissions, i, i2);
    }

    private boolean checkExistsAndEnforceCannotModifyImmutablyRestrictedPermission(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str);
                if (permission == null) {
                    android.util.Slog.w(TAG, "No such permissions: " + str);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return false;
                }
                java.lang.String packageName = permission.getPackageName();
                boolean z = permission.isHardOrSoftRestricted() && permission.isImmutablyRestricted();
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                int callingUid = android.os.Binder.getCallingUid();
                if (this.mPackageManagerInt.filterAppAccess(packageName, callingUid, android.os.UserHandle.getUserId(callingUid), false)) {
                    android.util.EventLog.writeEvent(1397638484, "186404356", java.lang.Integer.valueOf(callingUid), str);
                    return false;
                }
                if (!z || this.mContext.checkCallingOrSelfPermission("android.permission.WHITELIST_RESTRICTED_PERMISSIONS") == 0) {
                    return true;
                }
                throw new java.lang.SecurityException("Cannot modify allowlisting of an immutably restricted permission: " + str);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean removeAllowlistedRestrictedPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, int i2) {
        java.util.List<java.lang.String> allowlistedRestrictedPermissions;
        java.util.Objects.requireNonNull(str2);
        if (checkExistsAndEnforceCannotModifyImmutablyRestrictedPermission(str2) && (allowlistedRestrictedPermissions = getAllowlistedRestrictedPermissions(str, i, i2)) != null && allowlistedRestrictedPermissions.remove(str2)) {
            return setAllowlistedRestrictedPermissions(str, allowlistedRestrictedPermissions, i, i2);
        }
        return false;
    }

    private boolean setAllowlistedRestrictedPermissions(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.util.List<java.lang.String> list, int i, int i2) {
        java.util.Objects.requireNonNull(str);
        com.android.internal.util.Preconditions.checkFlagsArgument(i, 7);
        com.android.internal.util.Preconditions.checkArgument(java.lang.Integer.bitCount(i) == 1);
        com.android.internal.util.Preconditions.checkArgumentNonNegative(i2, (java.lang.String) null);
        if (android.os.UserHandle.getCallingUserId() != i2) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "setAllowlistedRestrictedPermissions for user " + i2);
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null) {
            return false;
        }
        int callingUid = android.os.Binder.getCallingUid();
        if (this.mPackageManagerInt.filterAppAccess(str, callingUid, android.os.UserHandle.getCallingUserId(), false)) {
            return false;
        }
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.WHITELIST_RESTRICTED_PERMISSIONS") == 0;
        boolean isCallerInstallerOfRecord = this.mPackageManagerInt.isCallerInstallerOfRecord(androidPackage, callingUid);
        if ((i & 1) != 0 && !z) {
            throw new java.lang.SecurityException("Modifying system allowlist requires android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
        }
        if ((i & 4) != 0) {
            if (!z && !isCallerInstallerOfRecord) {
                throw new java.lang.SecurityException("Modifying upgrade allowlist requires being installer on record or android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
            }
            java.util.List<java.lang.String> allowlistedRestrictedPermissions = getAllowlistedRestrictedPermissions(androidPackage.getPackageName(), i, i2);
            if (list == null || list.isEmpty()) {
                if (allowlistedRestrictedPermissions == null || allowlistedRestrictedPermissions.isEmpty()) {
                    return true;
                }
            } else {
                int size = list.size();
                for (int i3 = 0; i3 < size; i3++) {
                    if ((allowlistedRestrictedPermissions == null || !allowlistedRestrictedPermissions.contains(list.get(i3))) && !z) {
                        throw new java.lang.SecurityException("Adding to upgrade allowlist requiresandroid.permission.WHITELIST_RESTRICTED_PERMISSIONS");
                    }
                }
            }
        }
        if ((i & 2) != 0 && !z && !isCallerInstallerOfRecord) {
            throw new java.lang.SecurityException("Modifying installer allowlist requires being installer on record or android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            setAllowlistedRestrictedPermissionsInternal(androidPackage, list, i, i2);
            return true;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void grantRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        grantRuntimePermissionInternal(str, str2, checkUidPermission(callingUid, "android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY") == 0, callingUid, i, this.mDefaultPermissionCallback);
    }

    private void grantRuntimePermissionInternal(java.lang.String str, java.lang.String str2, boolean z, int i, int i2, com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback permissionCallback) {
        boolean isRole;
        boolean isSoftRestricted;
        if (!this.mUserManagerInt.exists(i2)) {
            android.util.Log.e(TAG, "No such user:" + i2);
            return;
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.GRANT_RUNTIME_PERMISSIONS", "grantRuntimePermission");
        enforceCrossUserPermission(i, i2, true, true, "grantRuntimePermission");
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(str);
        if (androidPackage == null || packageStateInternal == null) {
            android.util.Log.e(TAG, "Unknown package: " + str);
            return;
        }
        boolean z2 = false;
        if (this.mPackageManagerInt.filterAppAccess(str, i, i2, false)) {
            throw new java.lang.IllegalArgumentException("Unknown package: " + str);
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str2);
                if (permission == null) {
                    throw new java.lang.IllegalArgumentException("Unknown permission: " + str2);
                }
                isRole = permission.isRole();
                isSoftRestricted = permission.isSoftRestricted();
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        boolean z3 = isRole && mayManageRolePermission(i);
        if (isSoftRestricted && com.android.server.policy.SoftRestrictedPermissionPolicy.forPermission(this.mContext, com.android.server.pm.parsing.pkg.AndroidPackageUtils.generateAppInfoWithoutState(androidPackage), androidPackage, android.os.UserHandle.of(i2), str2).mayGrantPermission()) {
            z2 = true;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock2) {
            try {
                com.android.server.pm.permission.Permission permission2 = this.mRegistry.getPermission(str2);
                if (permission2 == null) {
                    throw new java.lang.IllegalArgumentException("Unknown permission: " + str2);
                }
                boolean isRuntime = permission2.isRuntime();
                boolean hasGids = permission2.hasGids();
                if (!isRuntime && !permission2.isDevelopment()) {
                    if (permission2.isRole()) {
                        if (!z3) {
                            throw new java.lang.SecurityException("Permission " + str2 + " is managed by role");
                        }
                    } else {
                        throw new java.lang.SecurityException("Permission " + str2 + " requested by " + androidPackage.getPackageName() + " is not a changeable permission type");
                    }
                }
                com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i2);
                if (uidStateLocked == null) {
                    android.util.Slog.e(TAG, "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                    return;
                }
                if (!uidStateLocked.hasPermissionState(str2) && !androidPackage.getRequestedPermissions().contains(str2)) {
                    throw new java.lang.SecurityException("Package " + androidPackage.getPackageName() + " has not requested permission " + str2);
                }
                if (androidPackage.getTargetSdkVersion() < 23 && permission2.isRuntime()) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                int permissionFlags = uidStateLocked.getPermissionFlags(str2);
                if ((permissionFlags & 16) != 0) {
                    android.util.Log.e(TAG, "Cannot grant system fixed permission " + str2 + " for package " + str);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                if (!z && (permissionFlags & 4) != 0) {
                    android.util.Log.e(TAG, "Cannot grant policy fixed permission " + str2 + " for package " + str);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                if (permission2.isHardRestricted() && (permissionFlags & 14336) == 0) {
                    android.util.Log.e(TAG, "Cannot grant hard restricted non-exempt permission " + str2 + " for package " + str);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                if (permission2.isSoftRestricted() && !z2) {
                    android.util.Log.e(TAG, "Cannot grant soft restricted permission " + str2 + " for package " + str);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                if (permission2.isDevelopment() || permission2.isRole()) {
                    if (!uidStateLocked.grantPermission(permission2)) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        return;
                    }
                } else {
                    if (packageStateInternal.getUserStateOrDefault(i2).isInstantApp() && !permission2.isInstant()) {
                        throw new java.lang.SecurityException("Cannot grant non-ephemeral permission " + str2 + " for package " + str);
                    }
                    if (androidPackage.getTargetSdkVersion() < 23) {
                        android.util.Slog.w(TAG, "Cannot grant runtime permission to a legacy app");
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        return;
                    } else if (!uidStateLocked.grantPermission(permission2)) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        return;
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                if (isRuntime) {
                    logPermission(1243, str2, str);
                }
                int uid = android.os.UserHandle.getUid(i2, androidPackage.getUid());
                if (permissionCallback != null) {
                    if (isRuntime) {
                        permissionCallback.onPermissionGranted(uid, i2);
                    } else {
                        permissionCallback.onInstallPermissionGranted();
                    }
                    if (hasGids) {
                        permissionCallback.onGidsChanged(android.os.UserHandle.getAppId(androidPackage.getUid()), i2);
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4) {
        int callingUid = android.os.Binder.getCallingUid();
        revokeRuntimePermissionInternal(str, str2, checkUidPermission(callingUid, "android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY", "default:0") == 0, callingUid, i, str4, this.mDefaultPermissionCallback);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void revokePostNotificationPermissionWithoutKillForTest(java.lang.String str, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        boolean z = checkUidPermission(callingUid, "android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY") == 0;
        this.mContext.enforceCallingPermission("android.permission.REVOKE_POST_NOTIFICATIONS_WITHOUT_KILL", "");
        revokeRuntimePermissionInternal(str, "android.permission.POST_NOTIFICATIONS", z, true, callingUid, i, SKIP_KILL_APP_REASON_NOTIFICATION_TEST, this.mDefaultPermissionCallback);
    }

    private void revokeRuntimePermissionInternal(java.lang.String str, java.lang.String str2, boolean z, int i, int i2, java.lang.String str3, com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback permissionCallback) {
        revokeRuntimePermissionInternal(str, str2, z, false, i, i2, str3, permissionCallback);
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x019f, code lost:
    
        if ((r6 & 4) != 0) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x01c0, code lost:
    
        throw new java.lang.SecurityException("Cannot revoke policy fixed permission " + r14 + " for package " + r13);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void revokeRuntimePermissionInternal(java.lang.String str, java.lang.String str2, boolean z, boolean z2, int i, int i2, java.lang.String str3, com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback permissionCallback) {
        boolean isRole;
        if (!this.mUserManagerInt.exists(i2)) {
            android.util.Log.e(TAG, "No such user:" + i2);
            return;
        }
        this.mContext.enforceCallingOrSelfPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS", "revokeRuntimePermission");
        enforceCrossUserPermission(i, i2, true, true, "revokeRuntimePermission");
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null) {
            android.util.Log.e(TAG, "Unknown package: " + str);
            return;
        }
        boolean z3 = false;
        if (this.mPackageManagerInt.filterAppAccess(str, i, i2, false)) {
            throw new java.lang.IllegalArgumentException("Unknown package: " + str);
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str2);
                if (permission == null) {
                    throw new java.lang.IllegalArgumentException("Unknown permission: " + str2);
                }
                isRole = permission.isRole();
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (isRole && (i == android.os.Process.myUid() || mayManageRolePermission(i))) {
            z3 = true;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock2) {
            try {
                com.android.server.pm.permission.Permission permission2 = this.mRegistry.getPermission(str2);
                if (permission2 == null) {
                    throw new java.lang.IllegalArgumentException("Unknown permission: " + str2);
                }
                boolean isRuntime = permission2.isRuntime();
                if (!isRuntime && !permission2.isDevelopment()) {
                    if (permission2.isRole()) {
                        if (!z3) {
                            throw new java.lang.SecurityException("Permission " + str2 + " is managed by role");
                        }
                    } else {
                        throw new java.lang.SecurityException("Permission " + str2 + " requested by " + androidPackage.getPackageName() + " is not a changeable permission type");
                    }
                }
                com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i2);
                if (uidStateLocked == null) {
                    android.util.Slog.e(TAG, "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                    return;
                }
                if (!uidStateLocked.hasPermissionState(str2) && !androidPackage.getRequestedPermissions().contains(str2)) {
                    throw new java.lang.SecurityException("Package " + androidPackage.getPackageName() + " has not requested permission " + str2);
                }
                if (androidPackage.getTargetSdkVersion() < 23 && permission2.isRuntime()) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                int permissionFlags = uidStateLocked.getPermissionFlags(str2);
                if ((permissionFlags & 16) != 0 && android.os.UserHandle.getCallingAppId() != 1000) {
                    throw new java.lang.SecurityException("Non-System UID cannot revoke system fixed permission " + str2 + " for package " + str);
                }
                if (!uidStateLocked.revokePermission(permission2)) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                if (isRuntime) {
                    logPermission(1245, str2, str);
                }
                if (permissionCallback != null) {
                    if (isRuntime) {
                        permissionCallback.onPermissionRevoked(android.os.UserHandle.getUid(i2, androidPackage.getUid()), i2, str3, z2, str2);
                    } else {
                        this.mDefaultPermissionCallback.onInstallPermissionRevoked();
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    private boolean mayManageRolePermission(int i) {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        java.lang.String[] packagesForUid = packageManager.getPackagesForUid(i);
        if (packagesForUid == null) {
            return false;
        }
        return java.util.Arrays.asList(packagesForUid).contains(packageManager.getPermissionControllerPackageName());
    }

    private void resetRuntimePermissionsInternal(@android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, final int i) {
        final boolean[] zArr = new boolean[1];
        final android.util.ArraySet arraySet = new android.util.ArraySet();
        final android.util.ArraySet arraySet2 = new android.util.ArraySet();
        final android.util.ArraySet arraySet3 = new android.util.ArraySet();
        final com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback permissionCallback = new com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onGidsChanged(int i2, int i3) {
                com.android.server.pm.permission.PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onGidsChanged(i2, i3);
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onPermissionChanged() {
                com.android.server.pm.permission.PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onPermissionChanged();
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onPermissionGranted(int i2, int i3) {
                com.android.server.pm.permission.PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onPermissionGranted(i2, i3);
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onInstallPermissionGranted() {
                com.android.server.pm.permission.PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onInstallPermissionGranted();
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onPermissionRevoked(int i2, int i3, java.lang.String str, boolean z, @android.annotation.Nullable java.lang.String str2) {
                arraySet.add(java.lang.Long.valueOf(com.android.internal.util.IntPair.of(i2, i3)));
                arraySet2.add(java.lang.Integer.valueOf(i3));
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onInstallPermissionRevoked() {
                com.android.server.pm.permission.PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onInstallPermissionRevoked();
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onPermissionUpdated(int[] iArr, boolean z, int i2) {
                com.android.server.pm.permission.PermissionManagerServiceImpl.this.mOnPermissionChangeListeners.onPermissionsChanged(i2);
                for (int i3 : iArr) {
                    if (z) {
                        arraySet2.add(java.lang.Integer.valueOf(i3));
                        arraySet3.remove(java.lang.Integer.valueOf(i3));
                    } else if (arraySet2.indexOf(java.lang.Integer.valueOf(i3)) == -1) {
                        arraySet3.add(java.lang.Integer.valueOf(i3));
                    }
                }
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onPermissionRemoved() {
                zArr[0] = true;
            }

            @Override // com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback
            public void onInstallPermissionUpdated() {
                com.android.server.pm.permission.PermissionManagerServiceImpl.this.mDefaultPermissionCallback.onInstallPermissionUpdated();
            }
        };
        if (androidPackage != null) {
            lambda$resetRuntimePermissionsInternal$3(androidPackage, i, permissionCallback);
        } else {
            this.mPackageManagerInt.forEachPackage(new java.util.function.Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$resetRuntimePermissionsInternal$3(i, permissionCallback, (com.android.server.pm.pkg.AndroidPackage) obj);
                }
            });
        }
        if (zArr[0]) {
            this.mDefaultPermissionCallback.onPermissionRemoved();
        }
        if (!arraySet.isEmpty()) {
            int size = arraySet.size();
            for (int i2 = 0; i2 < size; i2++) {
                final int first = com.android.internal.util.IntPair.first(((java.lang.Long) arraySet.valueAt(i2)).longValue());
                final int second = com.android.internal.util.IntPair.second(((java.lang.Long) arraySet.valueAt(i2)).longValue());
                this.mOnPermissionChangeListeners.onPermissionsChanged(first);
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.permission.PermissionManagerServiceImpl.lambda$resetRuntimePermissionsInternal$4(first, second);
                    }
                });
            }
        }
        this.mPackageManagerInt.writePermissionSettings(com.android.internal.util.ArrayUtils.convertToIntArray(arraySet2), false);
        this.mPackageManagerInt.writePermissionSettings(com.android.internal.util.ArrayUtils.convertToIntArray(arraySet3), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$resetRuntimePermissionsInternal$4(int i, int i2) {
        killUid(android.os.UserHandle.getAppId(i), i2, "permissions revoked");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: resetRuntimePermissionsInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$resetRuntimePermissionsInternal$3(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback permissionCallback) {
        int i2;
        boolean z;
        int i3 = i;
        java.lang.String packageName = androidPackage.getPackageName();
        for (java.lang.String str : androidPackage.getRequestedPermissions()) {
            if (!this.mIsLeanback || !NOTIFICATION_PERMISSIONS.contains(str)) {
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str);
                        if (permission == null) {
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        } else if (permission.isRemoved()) {
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        } else {
                            boolean isRuntime = permission.isRuntime();
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            java.lang.String[] sharedUserPackagesForPackage = this.mPackageManagerInt.getSharedUserPackagesForPackage(androidPackage.getPackageName(), i3);
                            if (sharedUserPackagesForPackage.length > 0) {
                                int length = sharedUserPackagesForPackage.length;
                                int i4 = 0;
                                while (true) {
                                    if (i4 >= length) {
                                        z = false;
                                        break;
                                    }
                                    com.android.server.pm.pkg.AndroidPackage androidPackage2 = this.mPackageManagerInt.getPackage(sharedUserPackagesForPackage[i4]);
                                    if (androidPackage2 == null || androidPackage2.getPackageName().equals(packageName) || !androidPackage2.getRequestedPermissions().contains(str)) {
                                        i4++;
                                    } else {
                                        z = true;
                                        break;
                                    }
                                }
                                if (z) {
                                }
                            }
                            int permissionFlagsInternal = getPermissionFlagsInternal(packageName, str, 1000, i3);
                            int uidTargetSdkVersion = this.mPackageManagerInt.getUidTargetSdkVersion(this.mPackageManagerInt.getPackageUid(packageName, 0L, i3));
                            if (uidTargetSdkVersion < 23 && isRuntime) {
                                i2 = 72;
                            } else {
                                i2 = 0;
                            }
                            updatePermissionFlagsInternal(packageName, str, 589899, i2, 1000, i, false, permissionCallback);
                            if (!isRuntime) {
                                i3 = i;
                            } else if ((permissionFlagsInternal & 20) != 0) {
                                i3 = i;
                            } else {
                                if ((permissionFlagsInternal & 32) != 0 || (32768 & permissionFlagsInternal) != 0) {
                                    grantRuntimePermissionInternal(packageName, str, false, 1000, i, permissionCallback);
                                } else if ((i2 & 64) == 0 && !isPermissionSplitFromNonRuntime(str, uidTargetSdkVersion)) {
                                    revokeRuntimePermissionInternal(packageName, str, false, 1000, i, null, permissionCallback);
                                }
                                i3 = i;
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
            }
        }
    }

    private boolean isPermissionSplitFromNonRuntime(java.lang.String str, int i) {
        java.util.List<android.permission.PermissionManager.SplitPermissionInfo> splitPermissionInfos = getSplitPermissionInfos();
        int size = splitPermissionInfos.size();
        boolean z = false;
        for (int i2 = 0; i2 < size; i2++) {
            android.permission.PermissionManager.SplitPermissionInfo splitPermissionInfo = splitPermissionInfos.get(i2);
            if (i < splitPermissionInfo.getTargetSdk() && splitPermissionInfo.getNewPermissions().contains(str)) {
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(splitPermissionInfo.getSplitPermission());
                        if (permission != null && !permission.isRuntime()) {
                            z = true;
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return z;
            }
        }
        return false;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean shouldShowRequestPermissionRationale(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        if (android.os.UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "canShowRequestPermissionRationale for user " + i);
        }
        if (android.os.UserHandle.getAppId(callingUid) != android.os.UserHandle.getAppId(this.mPackageManagerInt.getPackageUid(str, 268435456L, i)) || checkPermission(str, str2, i) == 0) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int permissionFlagsInternal = getPermissionFlagsInternal(str, str2, callingUid, i);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            if ((permissionFlagsInternal & 22) != 0) {
                return false;
            }
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str2);
                    if (permission == null) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        return false;
                    }
                    if (permission.isHardRestricted() && (permissionFlagsInternal & 14336) == 0) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        return false;
                    }
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        try {
                            if (str2.equals("android.permission.ACCESS_BACKGROUND_LOCATION")) {
                                if (this.mPlatformCompat.isChangeEnabledByPackageName(BACKGROUND_RATIONALE_CHANGE_ID, str, i)) {
                                    return true;
                                }
                            }
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(TAG, "Unable to check if compatibility change is enabled.", e);
                        }
                        return (permissionFlagsInternal & 1) != 0;
                    } finally {
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean isPermissionRevokedByPolicy(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        if (android.os.UserHandle.getCallingUserId() != i) {
            this.mContext.enforceCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "isPermissionRevokedByPolicy for user " + i);
        }
        if (checkPermission(str, str2, i) == 0) {
            return false;
        }
        int callingUid = android.os.Binder.getCallingUid();
        if (this.mPackageManagerInt.filterAppAccess(str, callingUid, i, false)) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return (getPermissionFlagsInternal(str, str2, callingUid, i) & 4) != 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.Nullable
    public byte[] backupRuntimePermissions(int i) {
        com.android.internal.util.Preconditions.checkArgumentNonNegative(i, "userId");
        final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
        android.permission.PermissionControllerManager permissionControllerManager = this.mPermissionControllerManager;
        android.os.UserHandle of = android.os.UserHandle.of(i);
        java.util.concurrent.Executor executor = com.android.server.PermissionThread.getExecutor();
        java.util.Objects.requireNonNull(completableFuture);
        permissionControllerManager.getRuntimePermissionBackup(of, executor, new java.util.function.Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                completableFuture.complete((byte[]) obj);
            }
        });
        try {
            return (byte[]) completableFuture.get(BACKUP_TIMEOUT_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException | java.util.concurrent.TimeoutException e) {
            android.util.Slog.e(TAG, "Cannot create permission backup for user " + i, e);
            return null;
        }
    }

    public void restoreRuntimePermissions(@android.annotation.NonNull byte[] bArr, int i) {
        java.util.Objects.requireNonNull(bArr, com.android.server.am.HostingRecord.HOSTING_TYPE_BACKUP);
        com.android.internal.util.Preconditions.checkArgumentNonNegative(i, "userId");
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mHasNoDelayedPermBackup.delete(i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        this.mPermissionControllerManager.stageAndApplyRuntimePermissionsBackup(bArr, android.os.UserHandle.of(i));
    }

    public void restoreDelayedRuntimePermissions(@android.annotation.NonNull java.lang.String str, final int i) {
        java.util.Objects.requireNonNull(str, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
        com.android.internal.util.Preconditions.checkArgumentNonNegative(i, "userId");
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                if (this.mHasNoDelayedPermBackup.get(i, false)) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                } else {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    this.mPermissionControllerManager.applyStagedRuntimePermissionBackup(str, android.os.UserHandle.of(i), com.android.server.PermissionThread.getExecutor(), new java.util.function.Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda14
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$restoreDelayedRuntimePermissions$5(i, (java.lang.Boolean) obj);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restoreDelayedRuntimePermissions$5(int i, java.lang.Boolean bool) {
        if (bool.booleanValue()) {
            return;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mHasNoDelayedPermBackup.put(i, true);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    private void revokeStoragePermissionsIfScopeExpandedInternal(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage2) {
        android.content.pm.PermissionInfo permissionInfo;
        int i;
        int i2;
        int i3;
        int i4;
        boolean z = androidPackage2.getTargetSdkVersion() >= 29 && androidPackage.getTargetSdkVersion() < 29;
        boolean z2 = ((androidPackage2.getTargetSdkVersion() < 29 && androidPackage.getTargetSdkVersion() >= 29) || androidPackage2.isRequestLegacyExternalStorage() || !androidPackage.isRequestLegacyExternalStorage()) ? false : true;
        if (!z2 && !z) {
            return;
        }
        int callingUid = android.os.Binder.getCallingUid();
        int[] allUserIds = getAllUserIds();
        int length = allUserIds.length;
        int i5 = 0;
        while (i5 < length) {
            int i6 = allUserIds[i5];
            java.util.Iterator it = androidPackage.getRequestedPermissions().iterator();
            while (it.hasNext()) {
                android.content.pm.PermissionInfo permissionInfo2 = getPermissionInfo((java.lang.String) it.next(), 0, androidPackage.getPackageName());
                if (permissionInfo2 != null) {
                    if (STORAGE_PERMISSIONS.contains(permissionInfo2.name) || READ_MEDIA_AURAL_PERMISSIONS.contains(permissionInfo2.name) || READ_MEDIA_VISUAL_PERMISSIONS.contains(permissionInfo2.name)) {
                        if (!((getPermissionFlags(androidPackage.getPackageName(), permissionInfo2.name, "default:0", i6) & 20) != 0)) {
                            android.util.EventLog.writeEvent(1397638484, "171430330", java.lang.Integer.valueOf(androidPackage.getUid()), "Revoking permission " + permissionInfo2.name + " from package " + androidPackage.getPackageName() + " as either the sdk downgraded " + z + " or newly requested legacy full storage " + z2);
                            try {
                                permissionInfo = permissionInfo2;
                                i = i6;
                                i2 = i5;
                                i3 = length;
                            } catch (java.lang.IllegalStateException | java.lang.SecurityException e) {
                                e = e;
                                permissionInfo = permissionInfo2;
                                i = i6;
                                i2 = i5;
                                i3 = length;
                            }
                            try {
                                revokeRuntimePermissionInternal(androidPackage.getPackageName(), permissionInfo2.name, false, callingUid, i6, null, this.mDefaultPermissionCallback);
                                i4 = i;
                            } catch (java.lang.IllegalStateException | java.lang.SecurityException e2) {
                                e = e2;
                                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                sb.append("unable to revoke ");
                                sb.append(permissionInfo.name);
                                sb.append(" for ");
                                sb.append(androidPackage.getPackageName());
                                sb.append(" user ");
                                i4 = i;
                                sb.append(i4);
                                android.util.Log.e(TAG, sb.toString(), e);
                                i6 = i4;
                                length = i3;
                                i5 = i2;
                            }
                            i6 = i4;
                            length = i3;
                            i5 = i2;
                        }
                    }
                }
            }
            i5++;
        }
    }

    private void revokeSystemAlertWindowIfUpgradedPast23(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage2) {
        com.android.server.pm.permission.Permission permission;
        if (androidPackage2.getTargetSdkVersion() >= 23 || androidPackage.getTargetSdkVersion() < 23 || !androidPackage.getRequestedPermissions().contains("android.permission.SYSTEM_ALERT_WINDOW")) {
            return;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                permission = this.mRegistry.getPermission("android.permission.SYSTEM_ALERT_WINDOW");
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (shouldGrantPermissionByProtectionFlags(androidPackage, this.mPackageManagerInt.getPackageStateInternal(androidPackage.getPackageName()), permission, new android.util.ArraySet<>()) || shouldGrantPermissionBySignature(androidPackage, permission)) {
            return;
        }
        for (int i : getAllUserIds()) {
            try {
                revokePermissionFromPackageForUser(androidPackage.getPackageName(), "android.permission.SYSTEM_ALERT_WINDOW", false, i, this.mDefaultPermissionCallback);
            } catch (java.lang.IllegalStateException | java.lang.SecurityException e) {
                android.util.Log.e(TAG, "unable to revoke SYSTEM_ALERT_WINDOW for " + androidPackage.getPackageName() + " user " + i, e);
            }
        }
    }

    private void revokeRuntimePermissionsIfGroupChangedInternal(@android.annotation.NonNull final com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage2) {
        int size = com.android.internal.util.ArrayUtils.size(androidPackage2.getPermissions());
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(size);
        for (int i = 0; i < size; i++) {
            com.android.internal.pm.pkg.component.ParsedPermission parsedPermission = (com.android.internal.pm.pkg.component.ParsedPermission) androidPackage2.getPermissions().get(i);
            if (parsedPermission.getParsedPermissionGroup() != null) {
                arrayMap.put(parsedPermission.getName(), parsedPermission.getParsedPermissionGroup().getName());
            }
        }
        final int callingUid = android.os.Binder.getCallingUid();
        int size2 = com.android.internal.util.ArrayUtils.size(androidPackage.getPermissions());
        for (int i2 = 0; i2 < size2; i2++) {
            com.android.internal.pm.pkg.component.ParsedPermission parsedPermission2 = (com.android.internal.pm.pkg.component.ParsedPermission) androidPackage.getPermissions().get(i2);
            if ((com.android.internal.pm.pkg.component.ParsedPermissionUtils.getProtection(parsedPermission2) & 1) != 0) {
                final java.lang.String name = parsedPermission2.getName();
                final java.lang.String name2 = parsedPermission2.getParsedPermissionGroup() == null ? null : parsedPermission2.getParsedPermissionGroup().getName();
                final java.lang.String str = (java.lang.String) arrayMap.get(name);
                if (name2 != null && !name2.equals(str)) {
                    final int[] userIds = this.mUserManagerInt.getUserIds();
                    this.mPackageManagerInt.forEachPackage(new java.util.function.Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda9
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$revokeRuntimePermissionsIfGroupChangedInternal$6(userIds, name, androidPackage, str, name2, callingUid, (com.android.server.pm.pkg.AndroidPackage) obj);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$revokeRuntimePermissionsIfGroupChangedInternal$6(int[] iArr, java.lang.String str, com.android.server.pm.pkg.AndroidPackage androidPackage, java.lang.String str2, java.lang.String str3, int i, com.android.server.pm.pkg.AndroidPackage androidPackage2) {
        java.lang.String packageName = androidPackage2.getPackageName();
        for (int i2 : iArr) {
            if (checkPermission(packageName, str, i2) == 0) {
                android.util.EventLog.writeEvent(1397638484, "72710897", java.lang.Integer.valueOf(androidPackage.getUid()), "Revoking permission " + str + " from package " + packageName + " as the group changed from " + str2 + " to " + str3);
                try {
                    revokeRuntimePermissionInternal(packageName, str, false, i, i2, null, this.mDefaultPermissionCallback);
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Slog.e(TAG, "Could not revoke " + str + " from " + packageName, e);
                }
            }
        }
    }

    private void revokeRuntimePermissionsIfPermissionDefinitionChangedInternal(@android.annotation.NonNull java.util.List<java.lang.String> list) {
        final int[] userIds = this.mUserManagerInt.getUserIds();
        int size = list.size();
        final int callingUid = android.os.Binder.getCallingUid();
        for (int i = 0; i < size; i++) {
            final java.lang.String str = list.get(i);
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str);
                    if (permission == null || !(permission.isInternal() || permission.isRuntime())) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    } else {
                        final boolean isInternal = permission.isInternal();
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        this.mPackageManagerInt.forEachPackage(new java.util.function.Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$revokeRuntimePermissionsIfPermissionDefinitionChangedInternal$7(userIds, str, isInternal, callingUid, (com.android.server.pm.pkg.AndroidPackage) obj);
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$revokeRuntimePermissionsIfPermissionDefinitionChangedInternal$7(int[] iArr, java.lang.String str, boolean z, int i, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        java.lang.String str2;
        com.android.server.pm.permission.PermissionManagerServiceImpl permissionManagerServiceImpl = this;
        java.lang.String packageName = androidPackage.getPackageName();
        int uid = androidPackage.getUid();
        if (uid < 10000) {
            return;
        }
        int length = iArr.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = iArr[i2];
            int checkPermission = permissionManagerServiceImpl.checkPermission(packageName, str, i3);
            int permissionFlags = permissionManagerServiceImpl.getPermissionFlags(packageName, str, "default:0", i3);
            if (checkPermission == 0 && (32820 & permissionFlags) == 0) {
                int uid2 = android.os.UserHandle.getUid(i3, uid);
                if (z) {
                    android.util.EventLog.writeEvent(1397638484, "195338390", java.lang.Integer.valueOf(uid2), "Revoking permission " + str + " from package " + packageName + " due to definition change");
                } else {
                    android.util.EventLog.writeEvent(1397638484, "154505240", java.lang.Integer.valueOf(uid2), "Revoking permission " + str + " from package " + packageName + " due to definition change");
                    android.util.EventLog.writeEvent(1397638484, "168319670", java.lang.Integer.valueOf(uid2), "Revoking permission " + str + " from package " + packageName + " due to definition change");
                }
                android.util.Slog.e(TAG, "Revoking permission " + str + " from package " + packageName + " due to definition change");
                try {
                    com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback permissionCallback = permissionManagerServiceImpl.mDefaultPermissionCallback;
                    str2 = TAG;
                    try {
                        revokeRuntimePermissionInternal(packageName, str, false, i, i3, null, permissionCallback);
                    } catch (java.lang.Exception e) {
                        e = e;
                        android.util.Slog.e(str2, "Could not revoke " + str + " from " + packageName, e);
                        i2++;
                        permissionManagerServiceImpl = this;
                    }
                } catch (java.lang.Exception e2) {
                    e = e2;
                    str2 = TAG;
                }
            }
            i2++;
            permissionManagerServiceImpl = this;
        }
    }

    private java.util.List<java.lang.String> addAllPermissionsInternal(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        android.content.pm.PermissionInfo generatePermissionInfo;
        com.android.server.pm.permission.Permission permissionTree;
        int size = com.android.internal.util.ArrayUtils.size(androidPackage.getPermissions());
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < size; i++) {
            com.android.internal.pm.pkg.component.ParsedPermission parsedPermission = (com.android.internal.pm.pkg.component.ParsedPermission) androidPackage.getPermissions().get(i);
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    if (androidPackage.getTargetSdkVersion() > 22) {
                        com.android.internal.pm.pkg.component.ComponentMutateUtils.setParsedPermissionGroup(parsedPermission, this.mRegistry.getPermissionGroup(parsedPermission.getGroup()));
                    }
                    generatePermissionInfo = com.android.server.pm.parsing.PackageInfoUtils.generatePermissionInfo(parsedPermission, 128L);
                    permissionTree = parsedPermission.isTree() ? this.mRegistry.getPermissionTree(parsedPermission.getName()) : this.mRegistry.getPermission(parsedPermission.getName());
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            boolean isOverridingSystemPermission = com.android.server.pm.permission.Permission.isOverridingSystemPermission(permissionTree, generatePermissionInfo, this.mPackageManagerInt);
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock2) {
                try {
                    com.android.server.pm.permission.Permission createOrUpdate = com.android.server.pm.permission.Permission.createOrUpdate(permissionTree, generatePermissionInfo, packageState, this.mRegistry.getPermissionTrees(), isOverridingSystemPermission);
                    if (parsedPermission.isTree()) {
                        this.mRegistry.addPermissionTree(createOrUpdate);
                    } else {
                        this.mRegistry.addPermission(createOrUpdate);
                    }
                    if (createOrUpdate.isDefinitionChanged()) {
                        arrayList.add(parsedPermission.getName());
                        createOrUpdate.setDefinitionChanged(false);
                    }
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
        return arrayList;
    }

    private void addAllPermissionGroupsInternal(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                int size = com.android.internal.util.ArrayUtils.size(androidPackage.getPermissionGroups());
                for (int i = 0; i < size; i++) {
                    com.android.internal.pm.pkg.component.ParsedPermissionGroup parsedPermissionGroup = (com.android.internal.pm.pkg.component.ParsedPermissionGroup) androidPackage.getPermissionGroups().get(i);
                    com.android.internal.pm.pkg.component.ParsedPermissionGroup permissionGroup = this.mRegistry.getPermissionGroup(parsedPermissionGroup.getName());
                    boolean equals = parsedPermissionGroup.getPackageName().equals(permissionGroup == null ? null : permissionGroup.getPackageName());
                    if (permissionGroup == null || equals) {
                        this.mRegistry.addPermissionGroup(parsedPermissionGroup);
                    } else {
                        android.util.Slog.w(TAG, "Permission group " + parsedPermissionGroup.getName() + " from package " + parsedPermissionGroup.getPackageName() + " ignored: original from " + permissionGroup.getPackageName());
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    private void removeAllPermissionsInternal(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                int size = com.android.internal.util.ArrayUtils.size(androidPackage.getPermissions());
                for (int i = 0; i < size; i++) {
                    com.android.internal.pm.pkg.component.ParsedPermission parsedPermission = (com.android.internal.pm.pkg.component.ParsedPermission) androidPackage.getPermissions().get(i);
                    com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(parsedPermission.getName());
                    if (permission == null) {
                        permission = this.mRegistry.getPermissionTree(parsedPermission.getName());
                    }
                    if (permission != null && permission.isPermission(parsedPermission)) {
                        permission.setPermissionInfo(null);
                    }
                    if (com.android.internal.pm.pkg.component.ParsedPermissionUtils.isAppOp(parsedPermission)) {
                        this.mRegistry.removeAppOpPermissionPackage(parsedPermission.getName(), androidPackage.getPackageName());
                    }
                }
                for (java.lang.String str : androidPackage.getRequestedPermissions()) {
                    com.android.server.pm.permission.Permission permission2 = this.mRegistry.getPermission(str);
                    if (permission2 != null && permission2.isAppOp()) {
                        this.mRegistry.removeAppOpPermissionPackage(str, androidPackage.getPackageName());
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onUserRemoved(int i) {
        com.android.internal.util.Preconditions.checkArgumentNonNegative(i, "userId");
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mState.removeUserState(i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @android.annotation.NonNull
    private java.util.Set<java.lang.String> getGrantedPermissionsInternal(@android.annotation.NonNull java.lang.String str, final int i) {
        final com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return java.util.Collections.emptySet();
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(packageStateInternal, i);
                if (uidStateLocked == null) {
                    android.util.Slog.e(TAG, "Missing permissions state for " + str + " and user " + i);
                    java.util.Set<java.lang.String> emptySet = java.util.Collections.emptySet();
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return emptySet;
                }
                if (!packageStateInternal.getUserStateOrDefault(i).isInstantApp()) {
                    java.util.Set<java.lang.String> grantedPermissions = uidStateLocked.getGrantedPermissions();
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return grantedPermissions;
                }
                android.util.ArraySet arraySet = new android.util.ArraySet(uidStateLocked.getGrantedPermissions());
                arraySet.removeIf(new java.util.function.Predicate() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$getGrantedPermissionsInternal$8;
                        lambda$getGrantedPermissionsInternal$8 = com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$getGrantedPermissionsInternal$8(i, packageStateInternal, (java.lang.String) obj);
                        return lambda$getGrantedPermissionsInternal$8;
                    }
                });
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return arraySet;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getGrantedPermissionsInternal$8(int i, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, java.lang.String str) {
        com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str);
        if (permission == null) {
            return true;
        }
        if (!permission.isInstant()) {
            android.util.EventLog.writeEvent(1397638484, "140256621", java.lang.Integer.valueOf(android.os.UserHandle.getUid(i, packageStateInternal.getAppId())), str);
            return true;
        }
        return false;
    }

    @android.annotation.NonNull
    private int[] getPermissionGidsInternal(@android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str);
                if (permission == null) {
                    int[] iArr = libcore.util.EmptyArray.INT;
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return iArr;
                }
                int[] computeGids = permission.computeGids(i);
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return computeGids;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:189:0x04f7, code lost:
    
        if (com.android.internal.util.CollectionUtils.contains(r8, r1) == false) goto L297;
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x0509, code lost:
    
        if (r7.isPermissionGranted(r1) == false) goto L307;
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x0503, code lost:
    
        if (r11.isRole() == false) goto L307;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x04ba, code lost:
    
        if (r11.isRole() == false) goto L283;
     */
    /* JADX WARN: Code restructure failed: missing block: B:333:0x057a, code lost:
    
        if (r39.isSystem() == false) goto L330;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0252 A[Catch: all -> 0x01c2, TryCatch #1 {all -> 0x01c2, blocks: (B:77:0x0160, B:79:0x017c, B:80:0x0180, B:82:0x0186, B:85:0x019b, B:87:0x01a9, B:89:0x01af, B:91:0x01b5, B:93:0x01bb, B:98:0x01d0, B:99:0x01c7, B:107:0x01dd, B:109:0x01f2, B:111:0x0200, B:112:0x021a, B:113:0x024c, B:115:0x0252, B:322:0x0276, B:119:0x027e, B:121:0x0284, B:123:0x028e, B:124:0x0291, B:128:0x02a3, B:130:0x02ad, B:131:0x02bb, B:133:0x02c2, B:135:0x02c8, B:137:0x02ce, B:139:0x02d8, B:142:0x02e1, B:144:0x02e7, B:146:0x02ed, B:149:0x046e, B:153:0x050b, B:156:0x051f, B:158:0x0525, B:159:0x052b, B:166:0x047f, B:168:0x0485, B:170:0x048b, B:173:0x04cf, B:175:0x04d5, B:177:0x04db, B:180:0x0515, B:184:0x04e5, B:186:0x04ed, B:188:0x04f3, B:190:0x0505, B:192:0x04f9, B:194:0x04ff, B:198:0x0497, B:200:0x049f, B:202:0x04a5, B:205:0x04b0, B:207:0x04b6, B:209:0x04bc, B:216:0x02fb, B:218:0x0301, B:220:0x0314, B:221:0x031c, B:224:0x032f, B:232:0x034b, B:234:0x0351, B:238:0x035e, B:239:0x0372, B:241:0x037c, B:243:0x0380, B:244:0x0384, B:246:0x0388, B:248:0x0392, B:249:0x03ad, B:251:0x03b1, B:253:0x03b9, B:255:0x03be, B:257:0x03c4, B:264:0x0412, B:266:0x0416, B:271:0x041e, B:272:0x0422, B:278:0x039f, B:280:0x03a5, B:289:0x036b, B:292:0x03d3, B:294:0x03df, B:296:0x03e5, B:297:0x03ea, B:299:0x03f4, B:307:0x0405, B:312:0x0432, B:330:0x056a, B:332:0x0576, B:334:0x0582, B:338:0x0593, B:339:0x05a0, B:344:0x057c, B:349:0x020a, B:351:0x0210), top: B:76:0x0160 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0525 A[Catch: all -> 0x01c2, TryCatch #1 {all -> 0x01c2, blocks: (B:77:0x0160, B:79:0x017c, B:80:0x0180, B:82:0x0186, B:85:0x019b, B:87:0x01a9, B:89:0x01af, B:91:0x01b5, B:93:0x01bb, B:98:0x01d0, B:99:0x01c7, B:107:0x01dd, B:109:0x01f2, B:111:0x0200, B:112:0x021a, B:113:0x024c, B:115:0x0252, B:322:0x0276, B:119:0x027e, B:121:0x0284, B:123:0x028e, B:124:0x0291, B:128:0x02a3, B:130:0x02ad, B:131:0x02bb, B:133:0x02c2, B:135:0x02c8, B:137:0x02ce, B:139:0x02d8, B:142:0x02e1, B:144:0x02e7, B:146:0x02ed, B:149:0x046e, B:153:0x050b, B:156:0x051f, B:158:0x0525, B:159:0x052b, B:166:0x047f, B:168:0x0485, B:170:0x048b, B:173:0x04cf, B:175:0x04d5, B:177:0x04db, B:180:0x0515, B:184:0x04e5, B:186:0x04ed, B:188:0x04f3, B:190:0x0505, B:192:0x04f9, B:194:0x04ff, B:198:0x0497, B:200:0x049f, B:202:0x04a5, B:205:0x04b0, B:207:0x04b6, B:209:0x04bc, B:216:0x02fb, B:218:0x0301, B:220:0x0314, B:221:0x031c, B:224:0x032f, B:232:0x034b, B:234:0x0351, B:238:0x035e, B:239:0x0372, B:241:0x037c, B:243:0x0380, B:244:0x0384, B:246:0x0388, B:248:0x0392, B:249:0x03ad, B:251:0x03b1, B:253:0x03b9, B:255:0x03be, B:257:0x03c4, B:264:0x0412, B:266:0x0416, B:271:0x041e, B:272:0x0422, B:278:0x039f, B:280:0x03a5, B:289:0x036b, B:292:0x03d3, B:294:0x03df, B:296:0x03e5, B:297:0x03ea, B:299:0x03f4, B:307:0x0405, B:312:0x0432, B:330:0x056a, B:332:0x0576, B:334:0x0582, B:338:0x0593, B:339:0x05a0, B:344:0x057c, B:349:0x020a, B:351:0x0210), top: B:76:0x0160 }] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x052a  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x04c2  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x04c3  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x037c A[Catch: all -> 0x01c2, TryCatch #1 {all -> 0x01c2, blocks: (B:77:0x0160, B:79:0x017c, B:80:0x0180, B:82:0x0186, B:85:0x019b, B:87:0x01a9, B:89:0x01af, B:91:0x01b5, B:93:0x01bb, B:98:0x01d0, B:99:0x01c7, B:107:0x01dd, B:109:0x01f2, B:111:0x0200, B:112:0x021a, B:113:0x024c, B:115:0x0252, B:322:0x0276, B:119:0x027e, B:121:0x0284, B:123:0x028e, B:124:0x0291, B:128:0x02a3, B:130:0x02ad, B:131:0x02bb, B:133:0x02c2, B:135:0x02c8, B:137:0x02ce, B:139:0x02d8, B:142:0x02e1, B:144:0x02e7, B:146:0x02ed, B:149:0x046e, B:153:0x050b, B:156:0x051f, B:158:0x0525, B:159:0x052b, B:166:0x047f, B:168:0x0485, B:170:0x048b, B:173:0x04cf, B:175:0x04d5, B:177:0x04db, B:180:0x0515, B:184:0x04e5, B:186:0x04ed, B:188:0x04f3, B:190:0x0505, B:192:0x04f9, B:194:0x04ff, B:198:0x0497, B:200:0x049f, B:202:0x04a5, B:205:0x04b0, B:207:0x04b6, B:209:0x04bc, B:216:0x02fb, B:218:0x0301, B:220:0x0314, B:221:0x031c, B:224:0x032f, B:232:0x034b, B:234:0x0351, B:238:0x035e, B:239:0x0372, B:241:0x037c, B:243:0x0380, B:244:0x0384, B:246:0x0388, B:248:0x0392, B:249:0x03ad, B:251:0x03b1, B:253:0x03b9, B:255:0x03be, B:257:0x03c4, B:264:0x0412, B:266:0x0416, B:271:0x041e, B:272:0x0422, B:278:0x039f, B:280:0x03a5, B:289:0x036b, B:292:0x03d3, B:294:0x03df, B:296:0x03e5, B:297:0x03ea, B:299:0x03f4, B:307:0x0405, B:312:0x0432, B:330:0x056a, B:332:0x0576, B:334:0x0582, B:338:0x0593, B:339:0x05a0, B:344:0x057c, B:349:0x020a, B:351:0x0210), top: B:76:0x0160 }] */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0388 A[Catch: all -> 0x01c2, TryCatch #1 {all -> 0x01c2, blocks: (B:77:0x0160, B:79:0x017c, B:80:0x0180, B:82:0x0186, B:85:0x019b, B:87:0x01a9, B:89:0x01af, B:91:0x01b5, B:93:0x01bb, B:98:0x01d0, B:99:0x01c7, B:107:0x01dd, B:109:0x01f2, B:111:0x0200, B:112:0x021a, B:113:0x024c, B:115:0x0252, B:322:0x0276, B:119:0x027e, B:121:0x0284, B:123:0x028e, B:124:0x0291, B:128:0x02a3, B:130:0x02ad, B:131:0x02bb, B:133:0x02c2, B:135:0x02c8, B:137:0x02ce, B:139:0x02d8, B:142:0x02e1, B:144:0x02e7, B:146:0x02ed, B:149:0x046e, B:153:0x050b, B:156:0x051f, B:158:0x0525, B:159:0x052b, B:166:0x047f, B:168:0x0485, B:170:0x048b, B:173:0x04cf, B:175:0x04d5, B:177:0x04db, B:180:0x0515, B:184:0x04e5, B:186:0x04ed, B:188:0x04f3, B:190:0x0505, B:192:0x04f9, B:194:0x04ff, B:198:0x0497, B:200:0x049f, B:202:0x04a5, B:205:0x04b0, B:207:0x04b6, B:209:0x04bc, B:216:0x02fb, B:218:0x0301, B:220:0x0314, B:221:0x031c, B:224:0x032f, B:232:0x034b, B:234:0x0351, B:238:0x035e, B:239:0x0372, B:241:0x037c, B:243:0x0380, B:244:0x0384, B:246:0x0388, B:248:0x0392, B:249:0x03ad, B:251:0x03b1, B:253:0x03b9, B:255:0x03be, B:257:0x03c4, B:264:0x0412, B:266:0x0416, B:271:0x041e, B:272:0x0422, B:278:0x039f, B:280:0x03a5, B:289:0x036b, B:292:0x03d3, B:294:0x03df, B:296:0x03e5, B:297:0x03ea, B:299:0x03f4, B:307:0x0405, B:312:0x0432, B:330:0x056a, B:332:0x0576, B:334:0x0582, B:338:0x0593, B:339:0x05a0, B:344:0x057c, B:349:0x020a, B:351:0x0210), top: B:76:0x0160 }] */
    /* JADX WARN: Removed duplicated region for block: B:251:0x03b1 A[Catch: all -> 0x01c2, TryCatch #1 {all -> 0x01c2, blocks: (B:77:0x0160, B:79:0x017c, B:80:0x0180, B:82:0x0186, B:85:0x019b, B:87:0x01a9, B:89:0x01af, B:91:0x01b5, B:93:0x01bb, B:98:0x01d0, B:99:0x01c7, B:107:0x01dd, B:109:0x01f2, B:111:0x0200, B:112:0x021a, B:113:0x024c, B:115:0x0252, B:322:0x0276, B:119:0x027e, B:121:0x0284, B:123:0x028e, B:124:0x0291, B:128:0x02a3, B:130:0x02ad, B:131:0x02bb, B:133:0x02c2, B:135:0x02c8, B:137:0x02ce, B:139:0x02d8, B:142:0x02e1, B:144:0x02e7, B:146:0x02ed, B:149:0x046e, B:153:0x050b, B:156:0x051f, B:158:0x0525, B:159:0x052b, B:166:0x047f, B:168:0x0485, B:170:0x048b, B:173:0x04cf, B:175:0x04d5, B:177:0x04db, B:180:0x0515, B:184:0x04e5, B:186:0x04ed, B:188:0x04f3, B:190:0x0505, B:192:0x04f9, B:194:0x04ff, B:198:0x0497, B:200:0x049f, B:202:0x04a5, B:205:0x04b0, B:207:0x04b6, B:209:0x04bc, B:216:0x02fb, B:218:0x0301, B:220:0x0314, B:221:0x031c, B:224:0x032f, B:232:0x034b, B:234:0x0351, B:238:0x035e, B:239:0x0372, B:241:0x037c, B:243:0x0380, B:244:0x0384, B:246:0x0388, B:248:0x0392, B:249:0x03ad, B:251:0x03b1, B:253:0x03b9, B:255:0x03be, B:257:0x03c4, B:264:0x0412, B:266:0x0416, B:271:0x041e, B:272:0x0422, B:278:0x039f, B:280:0x03a5, B:289:0x036b, B:292:0x03d3, B:294:0x03df, B:296:0x03e5, B:297:0x03ea, B:299:0x03f4, B:307:0x0405, B:312:0x0432, B:330:0x056a, B:332:0x0576, B:334:0x0582, B:338:0x0593, B:339:0x05a0, B:344:0x057c, B:349:0x020a, B:351:0x0210), top: B:76:0x0160 }] */
    /* JADX WARN: Removed duplicated region for block: B:275:0x0399 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:327:0x0560  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x0576 A[Catch: all -> 0x01c2, TryCatch #1 {all -> 0x01c2, blocks: (B:77:0x0160, B:79:0x017c, B:80:0x0180, B:82:0x0186, B:85:0x019b, B:87:0x01a9, B:89:0x01af, B:91:0x01b5, B:93:0x01bb, B:98:0x01d0, B:99:0x01c7, B:107:0x01dd, B:109:0x01f2, B:111:0x0200, B:112:0x021a, B:113:0x024c, B:115:0x0252, B:322:0x0276, B:119:0x027e, B:121:0x0284, B:123:0x028e, B:124:0x0291, B:128:0x02a3, B:130:0x02ad, B:131:0x02bb, B:133:0x02c2, B:135:0x02c8, B:137:0x02ce, B:139:0x02d8, B:142:0x02e1, B:144:0x02e7, B:146:0x02ed, B:149:0x046e, B:153:0x050b, B:156:0x051f, B:158:0x0525, B:159:0x052b, B:166:0x047f, B:168:0x0485, B:170:0x048b, B:173:0x04cf, B:175:0x04d5, B:177:0x04db, B:180:0x0515, B:184:0x04e5, B:186:0x04ed, B:188:0x04f3, B:190:0x0505, B:192:0x04f9, B:194:0x04ff, B:198:0x0497, B:200:0x049f, B:202:0x04a5, B:205:0x04b0, B:207:0x04b6, B:209:0x04bc, B:216:0x02fb, B:218:0x0301, B:220:0x0314, B:221:0x031c, B:224:0x032f, B:232:0x034b, B:234:0x0351, B:238:0x035e, B:239:0x0372, B:241:0x037c, B:243:0x0380, B:244:0x0384, B:246:0x0388, B:248:0x0392, B:249:0x03ad, B:251:0x03b1, B:253:0x03b9, B:255:0x03be, B:257:0x03c4, B:264:0x0412, B:266:0x0416, B:271:0x041e, B:272:0x0422, B:278:0x039f, B:280:0x03a5, B:289:0x036b, B:292:0x03d3, B:294:0x03df, B:296:0x03e5, B:297:0x03ea, B:299:0x03f4, B:307:0x0405, B:312:0x0432, B:330:0x056a, B:332:0x0576, B:334:0x0582, B:338:0x0593, B:339:0x05a0, B:344:0x057c, B:349:0x020a, B:351:0x0210), top: B:76:0x0160 }] */
    /* JADX WARN: Removed duplicated region for block: B:336:0x058f  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x059f  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x058b  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x0568  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01d0 A[Catch: all -> 0x01c2, TryCatch #1 {all -> 0x01c2, blocks: (B:77:0x0160, B:79:0x017c, B:80:0x0180, B:82:0x0186, B:85:0x019b, B:87:0x01a9, B:89:0x01af, B:91:0x01b5, B:93:0x01bb, B:98:0x01d0, B:99:0x01c7, B:107:0x01dd, B:109:0x01f2, B:111:0x0200, B:112:0x021a, B:113:0x024c, B:115:0x0252, B:322:0x0276, B:119:0x027e, B:121:0x0284, B:123:0x028e, B:124:0x0291, B:128:0x02a3, B:130:0x02ad, B:131:0x02bb, B:133:0x02c2, B:135:0x02c8, B:137:0x02ce, B:139:0x02d8, B:142:0x02e1, B:144:0x02e7, B:146:0x02ed, B:149:0x046e, B:153:0x050b, B:156:0x051f, B:158:0x0525, B:159:0x052b, B:166:0x047f, B:168:0x0485, B:170:0x048b, B:173:0x04cf, B:175:0x04d5, B:177:0x04db, B:180:0x0515, B:184:0x04e5, B:186:0x04ed, B:188:0x04f3, B:190:0x0505, B:192:0x04f9, B:194:0x04ff, B:198:0x0497, B:200:0x049f, B:202:0x04a5, B:205:0x04b0, B:207:0x04b6, B:209:0x04bc, B:216:0x02fb, B:218:0x0301, B:220:0x0314, B:221:0x031c, B:224:0x032f, B:232:0x034b, B:234:0x0351, B:238:0x035e, B:239:0x0372, B:241:0x037c, B:243:0x0380, B:244:0x0384, B:246:0x0388, B:248:0x0392, B:249:0x03ad, B:251:0x03b1, B:253:0x03b9, B:255:0x03be, B:257:0x03c4, B:264:0x0412, B:266:0x0416, B:271:0x041e, B:272:0x0422, B:278:0x039f, B:280:0x03a5, B:289:0x036b, B:292:0x03d3, B:294:0x03df, B:296:0x03e5, B:297:0x03ea, B:299:0x03f4, B:307:0x0405, B:312:0x0432, B:330:0x056a, B:332:0x0576, B:334:0x0582, B:338:0x0593, B:339:0x05a0, B:344:0x057c, B:349:0x020a, B:351:0x0210), top: B:76:0x0160 }] */
    /* JADX WARN: Type inference failed for: r3v10, types: [com.android.server.pm.PackageManagerTracedLock] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void restorePermissionState(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback permissionCallback, int i) {
        int[] iArr;
        android.util.ArraySet arraySet;
        java.util.Set set;
        int i2;
        java.util.Set set2;
        android.util.ArraySet<com.android.server.pm.pkg.PackageStateInternal> arraySet2;
        android.util.ArraySet arraySet3;
        android.util.ArraySet<java.lang.String> arraySet4;
        com.android.server.pm.permission.UidPermissionState uidPermissionState;
        java.util.Iterator it;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        boolean z2;
        boolean z3;
        com.android.server.pm.permission.UserPermissionState userPermissionState;
        boolean z4;
        int[] iArr2;
        android.util.ArraySet<java.lang.String> arraySet5;
        boolean z5;
        com.android.server.pm.permission.UserPermissionState userPermissionState2;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2;
        android.util.SparseBooleanArray sparseBooleanArray;
        android.util.ArraySet arraySet6;
        android.util.ArraySet<java.lang.String> arraySet7;
        android.util.ArraySet arraySet8;
        boolean z6;
        boolean z7;
        boolean z8;
        com.android.server.pm.permission.Permission permission;
        com.android.server.pm.pkg.AndroidPackage androidPackage2 = androidPackage;
        boolean z9 = z;
        java.lang.String str2 = str;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal3 = this.mPackageManagerInt.getPackageStateInternal(androidPackage.getPackageName());
        if (packageStateInternal3 == null) {
            return;
        }
        int[] allUserIds = i == -1 ? getAllUserIds() : new int[]{i};
        int[] iArr3 = EMPTY_INT_ARRAY;
        android.util.ArraySet<java.lang.String> arraySet9 = new android.util.ArraySet<>();
        java.util.Set requestedPermissions = androidPackage.getRequestedPermissions();
        android.util.ArraySet arraySet10 = null;
        android.util.ArraySet arraySet11 = null;
        android.util.ArraySet arraySet12 = null;
        for (java.lang.String str3 : androidPackage.getRequestedPermissions()) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    permission = this.mRegistry.getPermission(str3);
                } finally {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            if (permission != null) {
                if (permission.isPrivileged() && checkPrivilegedPermissionAllowlist(androidPackage2, packageStateInternal3, permission)) {
                    if (arraySet12 == null) {
                        arraySet12 = new android.util.ArraySet();
                    }
                    arraySet12.add(str3);
                }
                if (permission.isSignature() && (shouldGrantPermissionBySignature(androidPackage2, permission) || shouldGrantPermissionByProtectionFlags(androidPackage2, packageStateInternal3, permission, arraySet9))) {
                    if (arraySet10 == null) {
                        arraySet10 = new android.util.ArraySet();
                    }
                    arraySet10.add(str3);
                }
                if (permission.isInternal() && shouldGrantPermissionByProtectionFlags(androidPackage2, packageStateInternal3, permission, arraySet9)) {
                    if (arraySet11 == null) {
                        arraySet11 = new android.util.ArraySet();
                    }
                    arraySet11.add(str3);
                }
            }
        }
        android.util.SparseBooleanArray sparseBooleanArray2 = new android.util.SparseBooleanArray();
        if (this.mPermissionPolicyInternal == null) {
            iArr = iArr3;
        } else {
            int length = allUserIds.length;
            int i3 = 0;
            while (i3 < length) {
                int i4 = allUserIds[i3];
                int[] iArr4 = iArr3;
                if (this.mPermissionPolicyInternal.isInitialized(i4)) {
                    sparseBooleanArray2.put(i4, true);
                }
                i3++;
                iArr3 = iArr4;
            }
            iArr = iArr3;
        }
        if (!packageStateInternal3.hasSharedUser()) {
            set2 = androidPackage.getRequestedPermissions();
            set = androidPackage.getImplicitPermissions();
            arraySet = arraySet11;
            i2 = androidPackage.getTargetSdkVersion();
        } else {
            android.util.ArraySet arraySet13 = new android.util.ArraySet();
            android.util.ArraySet arraySet14 = new android.util.ArraySet();
            android.util.ArraySet<com.android.server.pm.pkg.PackageStateInternal> sharedUserPackages = this.mPackageManagerInt.getSharedUserPackages(packageStateInternal3.getSharedUserAppId());
            int size = sharedUserPackages.size();
            int i5 = 10000;
            arraySet = arraySet11;
            int i6 = 0;
            while (i6 < size) {
                com.android.server.pm.pkg.AndroidPackage androidPackage3 = sharedUserPackages.valueAt(i6).getAndroidPackage();
                if (androidPackage3 == null) {
                    arraySet2 = sharedUserPackages;
                } else {
                    arraySet2 = sharedUserPackages;
                    arraySet13.addAll(androidPackage3.getRequestedPermissions());
                    arraySet14.addAll(androidPackage3.getImplicitPermissions());
                    i5 = java.lang.Math.min(i5, androidPackage3.getTargetSdkVersion());
                }
                i6++;
                sharedUserPackages = arraySet2;
            }
            set = arraySet14;
            i2 = i5;
            set2 = arraySet13;
        }
        ?? r3 = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (r3) {
            try {
                try {
                    int length2 = allUserIds.length;
                    int[] iArr5 = iArr;
                    java.util.Set set3 = set;
                    int i7 = 0;
                    boolean z10 = false;
                    boolean z11 = false;
                    java.util.Set set4 = r3;
                    while (i7 < length2) {
                        java.util.Set set5 = set4;
                        try {
                            int i8 = allUserIds[i7];
                            int i9 = length2;
                            com.android.server.pm.permission.UserPermissionState orCreateUserState = this.mState.getOrCreateUserState(i8);
                            int[] iArr6 = allUserIds;
                            com.android.server.pm.permission.UidPermissionState orCreateUidState = orCreateUserState.getOrCreateUidState(packageStateInternal3.getAppId());
                            int i10 = i7;
                            if (!orCreateUidState.isMissing()) {
                                arraySet3 = arraySet10;
                                arraySet4 = arraySet9;
                            } else {
                                java.util.Iterator<java.lang.String> it2 = set2.iterator();
                                while (it2.hasNext()) {
                                    android.util.ArraySet<java.lang.String> arraySet15 = arraySet9;
                                    com.android.server.pm.permission.Permission permission2 = this.mRegistry.getPermission(it2.next());
                                    if (permission2 == null) {
                                        arraySet9 = arraySet15;
                                    } else {
                                        android.util.ArraySet arraySet16 = arraySet10;
                                        if (java.util.Objects.equals(permission2.getPackageName(), com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME) && permission2.isRuntime() && !permission2.isRemoved()) {
                                            if (!permission2.isHardOrSoftRestricted()) {
                                                if (permission2.isImmutablyRestricted()) {
                                                }
                                                if (i2 < 23) {
                                                    orCreateUidState.updatePermissionFlags(permission2, 72, 72);
                                                    orCreateUidState.grantPermission(permission2);
                                                }
                                            }
                                            orCreateUidState.updatePermissionFlags(permission2, 8192, 8192);
                                            if (i2 < 23) {
                                            }
                                        }
                                        arraySet9 = arraySet15;
                                        arraySet10 = arraySet16;
                                    }
                                }
                                arraySet3 = arraySet10;
                                arraySet4 = arraySet9;
                                orCreateUidState.setMissing(false);
                                iArr5 = com.android.internal.util.ArrayUtils.appendInt(iArr5, i8);
                            }
                            if (z9) {
                                orCreateUserState.setInstallPermissionsFixed(packageStateInternal3.getPackageName(), false);
                                if (!packageStateInternal3.hasSharedUser()) {
                                    com.android.server.pm.permission.UidPermissionState uidPermissionState2 = new com.android.server.pm.permission.UidPermissionState(orCreateUidState);
                                    orCreateUidState.reset();
                                    uidPermissionState = uidPermissionState2;
                                } else if (revokeUnusedSharedUserPermissionsLocked(set2, orCreateUidState)) {
                                    iArr5 = com.android.internal.util.ArrayUtils.appendInt(iArr5, i8);
                                    uidPermissionState = orCreateUidState;
                                    z11 = true;
                                }
                                android.util.ArraySet<java.lang.String> arraySet17 = new android.util.ArraySet<>();
                                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                int[] iArr7 = iArr5;
                                sb.append(androidPackage.getPackageName());
                                sb.append("(");
                                sb.append(androidPackage.getUid());
                                sb.append(")");
                                sb.toString();
                                it = requestedPermissions.iterator();
                                boolean z12 = false;
                                int i11 = i2;
                                int[] iArr8 = iArr7;
                                while (it.hasNext()) {
                                    java.util.Iterator it3 = it;
                                    java.lang.String str4 = (java.lang.String) it.next();
                                    java.util.Set set6 = set2;
                                    com.android.server.pm.permission.Permission permission3 = this.mRegistry.getPermission(str4);
                                    boolean z13 = z12;
                                    boolean z14 = androidPackage.getTargetSdkVersion() >= 23;
                                    if (permission3 == null) {
                                        if (str2 != null) {
                                            str2.equals(androidPackage.getPackageName());
                                        }
                                    } else {
                                        if (!uidPermissionState.hasPermissionState(str4) && androidPackage.getImplicitPermissions().contains(str4)) {
                                            arraySet17.add(str4);
                                        }
                                        if (!permission3.isRuntimeOnly() || z14) {
                                            java.lang.String name = permission3.getName();
                                            if (!permission3.isAppOp()) {
                                                arraySet5 = arraySet17;
                                            } else {
                                                arraySet5 = arraySet17;
                                                this.mRegistry.addAppOpPermissionPackage(name, androidPackage.getPackageName());
                                            }
                                            if (permission3.isNormal() && !uidPermissionState.isPermissionGranted(name) && !packageStateInternal3.isSystem() && orCreateUserState.areInstallPermissionsFixed(packageStateInternal3.getPackageName()) && !isCompatPlatformPermissionForPackage(name, androidPackage2)) {
                                                z5 = false;
                                            } else {
                                                z5 = true;
                                            }
                                            if (permission3.isNormal() || permission3.isSignature()) {
                                                userPermissionState2 = orCreateUserState;
                                                packageStateInternal2 = packageStateInternal3;
                                                sparseBooleanArray = sparseBooleanArray2;
                                            } else if (permission3.isInternal()) {
                                                userPermissionState2 = orCreateUserState;
                                                packageStateInternal2 = packageStateInternal3;
                                                sparseBooleanArray = sparseBooleanArray2;
                                            } else {
                                                if (permission3.isRuntime()) {
                                                    boolean isHardRestricted = permission3.isHardRestricted();
                                                    boolean isSoftRestricted = permission3.isSoftRestricted();
                                                    boolean z15 = sparseBooleanArray2.get(i8);
                                                    com.android.server.pm.permission.PermissionState permissionState = uidPermissionState.getPermissionState(name);
                                                    int flags = permissionState != null ? permissionState.getFlags() : 0;
                                                    sparseBooleanArray = sparseBooleanArray2;
                                                    boolean z16 = (uidPermissionState.getPermissionFlags(permission3.getName()) & 14336) != 0;
                                                    userPermissionState2 = orCreateUserState;
                                                    boolean z17 = (uidPermissionState.getPermissionFlags(permission3.getName()) & 16384) != 0;
                                                    if (z14) {
                                                        if (z15 && isHardRestricted) {
                                                            if (!z16) {
                                                                if (permissionState != null && permissionState.isGranted() && orCreateUidState.revokePermission(permission3)) {
                                                                    z8 = true;
                                                                } else {
                                                                    z8 = false;
                                                                }
                                                                if (!z17) {
                                                                    flags |= 16384;
                                                                    z8 = true;
                                                                }
                                                                packageStateInternal2 = packageStateInternal3;
                                                                if (!NOTIFICATION_PERMISSIONS.contains(name)) {
                                                                }
                                                                if ((flags & 8) == 0) {
                                                                }
                                                                if (z15) {
                                                                }
                                                                z8 = true;
                                                                if (this.mIsLeanback) {
                                                                }
                                                                z7 = z8;
                                                            }
                                                            z8 = false;
                                                            packageStateInternal2 = packageStateInternal3;
                                                            if (!NOTIFICATION_PERMISSIONS.contains(name)) {
                                                            }
                                                            if ((flags & 8) == 0) {
                                                            }
                                                            if (z15) {
                                                            }
                                                            z8 = true;
                                                            if (this.mIsLeanback) {
                                                            }
                                                            z7 = z8;
                                                        } else {
                                                            if (z15 && isSoftRestricted && !z16 && !z17) {
                                                                flags |= 16384;
                                                                z8 = true;
                                                                packageStateInternal2 = packageStateInternal3;
                                                                if (!NOTIFICATION_PERMISSIONS.contains(name) && (flags & 64) != 0) {
                                                                    flags &= -65;
                                                                    z8 = true;
                                                                }
                                                                if ((flags & 8) == 0 && !isPermissionSplitFromNonRuntime(str4, androidPackage.getTargetSdkVersion())) {
                                                                    flags &= -9;
                                                                    z8 = true;
                                                                } else if ((z15 || !isHardRestricted || z16) && permissionState != null && permissionState.isGranted() && !orCreateUidState.grantPermission(permission3)) {
                                                                    z8 = true;
                                                                }
                                                                if (this.mIsLeanback && NOTIFICATION_PERMISSIONS.contains(str4)) {
                                                                    orCreateUidState.grantPermission(permission3);
                                                                    if ((permissionState != null || !permissionState.isGranted()) && orCreateUidState.grantPermission(permission3)) {
                                                                        z7 = true;
                                                                    }
                                                                }
                                                                z7 = z8;
                                                            }
                                                            z8 = false;
                                                            packageStateInternal2 = packageStateInternal3;
                                                            if (!NOTIFICATION_PERMISSIONS.contains(name)) {
                                                                flags &= -65;
                                                                z8 = true;
                                                            }
                                                            if ((flags & 8) == 0) {
                                                            }
                                                            if (z15) {
                                                            }
                                                            z8 = true;
                                                            if (this.mIsLeanback) {
                                                                orCreateUidState.grantPermission(permission3);
                                                                if (permissionState != null) {
                                                                }
                                                                z7 = true;
                                                            }
                                                            z7 = z8;
                                                        }
                                                    } else {
                                                        packageStateInternal2 = packageStateInternal3;
                                                        if (permissionState == null && com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(permission3.getPackageName()) && !permission3.isRemoved()) {
                                                            flags |= 72;
                                                            z7 = true;
                                                        } else {
                                                            z7 = false;
                                                        }
                                                        if (!orCreateUidState.isPermissionGranted(permission3.getName()) && orCreateUidState.grantPermission(permission3)) {
                                                            z7 = true;
                                                        }
                                                        if (z15 && ((isHardRestricted || isSoftRestricted) && !z16 && !z17)) {
                                                            flags |= 16384;
                                                            z7 = true;
                                                        }
                                                    }
                                                    if (z15 && (((!isHardRestricted && !isSoftRestricted) || z16) && z17)) {
                                                        int i12 = flags & (-16385);
                                                        if (z14) {
                                                            flags = i12;
                                                        } else {
                                                            flags = i12 | 64;
                                                        }
                                                        z7 = true;
                                                    }
                                                    if (z7) {
                                                        iArr8 = com.android.internal.util.ArrayUtils.appendInt(iArr8, i8);
                                                    }
                                                    orCreateUidState.updatePermissionFlags(permission3, 261119, flags);
                                                    arraySet6 = arraySet;
                                                    arraySet7 = arraySet4;
                                                    arraySet8 = arraySet3;
                                                    z12 = z13;
                                                } else {
                                                    userPermissionState2 = orCreateUserState;
                                                    packageStateInternal2 = packageStateInternal3;
                                                    sparseBooleanArray = sparseBooleanArray2;
                                                    android.util.Slog.wtf(LOG_TAG, "Unknown permission protection " + permission3.getProtection() + " for permission " + permission3.getName());
                                                    arraySet6 = arraySet;
                                                    arraySet7 = arraySet4;
                                                    arraySet8 = arraySet3;
                                                    z12 = z13;
                                                }
                                                str2 = str;
                                                arraySet3 = arraySet8;
                                                arraySet4 = arraySet7;
                                                arraySet = arraySet6;
                                                set2 = set6;
                                                it = it3;
                                                arraySet17 = arraySet5;
                                                sparseBooleanArray2 = sparseBooleanArray;
                                                orCreateUserState = userPermissionState2;
                                                packageStateInternal3 = packageStateInternal2;
                                                androidPackage2 = androidPackage;
                                            }
                                            if (permission3.isNormal() && z5) {
                                                arraySet6 = arraySet;
                                                arraySet7 = arraySet4;
                                                arraySet8 = arraySet3;
                                            } else {
                                                if (!permission3.isSignature()) {
                                                    arraySet7 = arraySet4;
                                                    arraySet8 = arraySet3;
                                                } else if (!permission3.isPrivileged() || com.android.internal.util.CollectionUtils.contains(arraySet12, str4)) {
                                                    arraySet8 = arraySet3;
                                                    if (com.android.internal.util.CollectionUtils.contains(arraySet8, str4)) {
                                                        arraySet7 = arraySet4;
                                                        arraySet6 = arraySet;
                                                    } else {
                                                        if (permission3.isPrivileged()) {
                                                            arraySet7 = arraySet4;
                                                            if (!com.android.internal.util.CollectionUtils.contains(arraySet7, str4)) {
                                                            }
                                                            if (!uidPermissionState.isPermissionGranted(str4)) {
                                                                arraySet6 = arraySet;
                                                            }
                                                        } else {
                                                            arraySet7 = arraySet4;
                                                        }
                                                        if (!permission3.isDevelopment()) {
                                                        }
                                                        if (!uidPermissionState.isPermissionGranted(str4)) {
                                                        }
                                                    }
                                                } else {
                                                    arraySet7 = arraySet4;
                                                    arraySet8 = arraySet3;
                                                }
                                                if (!permission3.isInternal()) {
                                                    arraySet6 = arraySet;
                                                } else if (!permission3.isPrivileged() || com.android.internal.util.CollectionUtils.contains(arraySet12, str4)) {
                                                    arraySet6 = arraySet;
                                                    if (!com.android.internal.util.CollectionUtils.contains(arraySet6, str4)) {
                                                        if (permission3.isPrivileged()) {
                                                        }
                                                        if (!permission3.isDevelopment()) {
                                                        }
                                                    }
                                                } else {
                                                    arraySet6 = arraySet;
                                                }
                                                if (orCreateUidState.revokePermission(permission3)) {
                                                    z6 = true;
                                                    com.android.server.pm.permission.PermissionState permissionState2 = uidPermissionState.getPermissionState(name);
                                                    orCreateUidState.updatePermissionFlags(permission3, 261119, permissionState2 == null ? permissionState2.getFlags() : 0);
                                                    z12 = z6;
                                                    str2 = str;
                                                    arraySet3 = arraySet8;
                                                    arraySet4 = arraySet7;
                                                    arraySet = arraySet6;
                                                    set2 = set6;
                                                    it = it3;
                                                    arraySet17 = arraySet5;
                                                    sparseBooleanArray2 = sparseBooleanArray;
                                                    orCreateUserState = userPermissionState2;
                                                    packageStateInternal3 = packageStateInternal2;
                                                    androidPackage2 = androidPackage;
                                                }
                                                z6 = z13;
                                                com.android.server.pm.permission.PermissionState permissionState22 = uidPermissionState.getPermissionState(name);
                                                orCreateUidState.updatePermissionFlags(permission3, 261119, permissionState22 == null ? permissionState22.getFlags() : 0);
                                                z12 = z6;
                                                str2 = str;
                                                arraySet3 = arraySet8;
                                                arraySet4 = arraySet7;
                                                arraySet = arraySet6;
                                                set2 = set6;
                                                it = it3;
                                                arraySet17 = arraySet5;
                                                sparseBooleanArray2 = sparseBooleanArray;
                                                orCreateUserState = userPermissionState2;
                                                packageStateInternal3 = packageStateInternal2;
                                                androidPackage2 = androidPackage;
                                            }
                                            if (orCreateUidState.grantPermission(permission3)) {
                                                z6 = true;
                                                com.android.server.pm.permission.PermissionState permissionState222 = uidPermissionState.getPermissionState(name);
                                                orCreateUidState.updatePermissionFlags(permission3, 261119, permissionState222 == null ? permissionState222.getFlags() : 0);
                                                z12 = z6;
                                                str2 = str;
                                                arraySet3 = arraySet8;
                                                arraySet4 = arraySet7;
                                                arraySet = arraySet6;
                                                set2 = set6;
                                                it = it3;
                                                arraySet17 = arraySet5;
                                                sparseBooleanArray2 = sparseBooleanArray;
                                                orCreateUserState = userPermissionState2;
                                                packageStateInternal3 = packageStateInternal2;
                                                androidPackage2 = androidPackage;
                                            }
                                            z6 = z13;
                                            com.android.server.pm.permission.PermissionState permissionState2222 = uidPermissionState.getPermissionState(name);
                                            orCreateUidState.updatePermissionFlags(permission3, 261119, permissionState2222 == null ? permissionState2222.getFlags() : 0);
                                            z12 = z6;
                                            str2 = str;
                                            arraySet3 = arraySet8;
                                            arraySet4 = arraySet7;
                                            arraySet = arraySet6;
                                            set2 = set6;
                                            it = it3;
                                            arraySet17 = arraySet5;
                                            sparseBooleanArray2 = sparseBooleanArray;
                                            orCreateUserState = userPermissionState2;
                                            packageStateInternal3 = packageStateInternal2;
                                            androidPackage2 = androidPackage;
                                        }
                                    }
                                    set2 = set6;
                                    it = it3;
                                    z12 = z13;
                                }
                                com.android.server.pm.permission.UserPermissionState userPermissionState3 = orCreateUserState;
                                android.util.ArraySet<java.lang.String> arraySet18 = arraySet17;
                                java.util.Set set7 = set2;
                                packageStateInternal = packageStateInternal3;
                                android.util.SparseBooleanArray sparseBooleanArray3 = sparseBooleanArray2;
                                z2 = z12;
                                android.util.ArraySet arraySet19 = arraySet;
                                android.util.ArraySet<java.lang.String> arraySet20 = arraySet4;
                                android.util.ArraySet arraySet21 = arraySet3;
                                if (z2) {
                                    z3 = z;
                                    if (!z3) {
                                        userPermissionState = userPermissionState3;
                                        if (!packageStateInternal.isUpdatedSystemApp()) {
                                            z4 = true;
                                            if (z2) {
                                                iArr2 = iArr8;
                                            } else if (str != null && z3) {
                                                iArr2 = com.android.internal.util.ArrayUtils.appendInt(iArr8, i8);
                                                z10 = z4;
                                            } else {
                                                iArr2 = iArr8;
                                                z10 = z4;
                                            }
                                            android.util.ArraySet arraySet22 = arraySet12;
                                            iArr5 = setInitialGrantForNewImplicitPermissionsLocked(uidPermissionState, orCreateUidState, androidPackage, arraySet18, i8, revokePermissionsNoLongerImplicitLocked(orCreateUidState, androidPackage.getPackageName(), set3, i11, i8, iArr2));
                                            arraySet9 = arraySet20;
                                            set4 = set5;
                                            arraySet10 = arraySet21;
                                            i2 = i11;
                                            allUserIds = iArr6;
                                            arraySet12 = arraySet22;
                                            set2 = set7;
                                            packageStateInternal3 = packageStateInternal;
                                            androidPackage2 = androidPackage;
                                            i7 = i10 + 1;
                                            arraySet = arraySet19;
                                            z9 = z3;
                                            length2 = i9;
                                            sparseBooleanArray2 = sparseBooleanArray3;
                                            str2 = str;
                                        }
                                        z4 = true;
                                        userPermissionState.setInstallPermissionsFixed(packageStateInternal.getPackageName(), true);
                                        if (z2) {
                                        }
                                        android.util.ArraySet arraySet222 = arraySet12;
                                        iArr5 = setInitialGrantForNewImplicitPermissionsLocked(uidPermissionState, orCreateUidState, androidPackage, arraySet18, i8, revokePermissionsNoLongerImplicitLocked(orCreateUidState, androidPackage.getPackageName(), set3, i11, i8, iArr2));
                                        arraySet9 = arraySet20;
                                        set4 = set5;
                                        arraySet10 = arraySet21;
                                        i2 = i11;
                                        allUserIds = iArr6;
                                        arraySet12 = arraySet222;
                                        set2 = set7;
                                        packageStateInternal3 = packageStateInternal;
                                        androidPackage2 = androidPackage;
                                        i7 = i10 + 1;
                                        arraySet = arraySet19;
                                        z9 = z3;
                                        length2 = i9;
                                        sparseBooleanArray2 = sparseBooleanArray3;
                                        str2 = str;
                                    }
                                } else {
                                    z3 = z;
                                }
                                userPermissionState = userPermissionState3;
                                if (!userPermissionState.areInstallPermissionsFixed(packageStateInternal.getPackageName())) {
                                }
                                if (!packageStateInternal.isUpdatedSystemApp()) {
                                }
                                z4 = true;
                                userPermissionState.setInstallPermissionsFixed(packageStateInternal.getPackageName(), true);
                                if (z2) {
                                }
                                android.util.ArraySet arraySet2222 = arraySet12;
                                iArr5 = setInitialGrantForNewImplicitPermissionsLocked(uidPermissionState, orCreateUidState, androidPackage, arraySet18, i8, revokePermissionsNoLongerImplicitLocked(orCreateUidState, androidPackage.getPackageName(), set3, i11, i8, iArr2));
                                arraySet9 = arraySet20;
                                set4 = set5;
                                arraySet10 = arraySet21;
                                i2 = i11;
                                allUserIds = iArr6;
                                arraySet12 = arraySet2222;
                                set2 = set7;
                                packageStateInternal3 = packageStateInternal;
                                androidPackage2 = androidPackage;
                                i7 = i10 + 1;
                                arraySet = arraySet19;
                                z9 = z3;
                                length2 = i9;
                                sparseBooleanArray2 = sparseBooleanArray3;
                                str2 = str;
                            }
                            uidPermissionState = orCreateUidState;
                            android.util.ArraySet<java.lang.String> arraySet172 = new android.util.ArraySet<>();
                            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                            int[] iArr72 = iArr5;
                            sb2.append(androidPackage.getPackageName());
                            sb2.append("(");
                            sb2.append(androidPackage.getUid());
                            sb2.append(")");
                            sb2.toString();
                            it = requestedPermissions.iterator();
                            boolean z122 = false;
                            int i112 = i2;
                            int[] iArr82 = iArr72;
                            while (it.hasNext()) {
                            }
                            com.android.server.pm.permission.UserPermissionState userPermissionState32 = orCreateUserState;
                            android.util.ArraySet<java.lang.String> arraySet182 = arraySet172;
                            java.util.Set set72 = set2;
                            packageStateInternal = packageStateInternal3;
                            android.util.SparseBooleanArray sparseBooleanArray32 = sparseBooleanArray2;
                            z2 = z122;
                            android.util.ArraySet arraySet192 = arraySet;
                            android.util.ArraySet<java.lang.String> arraySet202 = arraySet4;
                            android.util.ArraySet arraySet212 = arraySet3;
                            if (z2) {
                            }
                            userPermissionState = userPermissionState32;
                            if (!userPermissionState.areInstallPermissionsFixed(packageStateInternal.getPackageName())) {
                            }
                            if (!packageStateInternal.isUpdatedSystemApp()) {
                            }
                            z4 = true;
                            userPermissionState.setInstallPermissionsFixed(packageStateInternal.getPackageName(), true);
                            if (z2) {
                            }
                            android.util.ArraySet arraySet22222 = arraySet12;
                            iArr5 = setInitialGrantForNewImplicitPermissionsLocked(uidPermissionState, orCreateUidState, androidPackage, arraySet182, i8, revokePermissionsNoLongerImplicitLocked(orCreateUidState, androidPackage.getPackageName(), set3, i112, i8, iArr2));
                            arraySet9 = arraySet202;
                            set4 = set5;
                            arraySet10 = arraySet212;
                            i2 = i112;
                            allUserIds = iArr6;
                            arraySet12 = arraySet22222;
                            set2 = set72;
                            packageStateInternal3 = packageStateInternal;
                            androidPackage2 = androidPackage;
                            i7 = i10 + 1;
                            arraySet = arraySet192;
                            z9 = z3;
                            length2 = i9;
                            sparseBooleanArray2 = sparseBooleanArray32;
                            str2 = str;
                        } catch (java.lang.Throwable th) {
                            th = th;
                            set = set5;
                            throw th;
                        }
                    }
                    boolean z18 = z9;
                    int[] iArr9 = allUserIds;
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    int[] checkIfLegacyStorageOpsNeedToBeUpdated = checkIfLegacyStorageOpsNeedToBeUpdated(androidPackage, z18, iArr9, iArr5);
                    if (permissionCallback != null) {
                        permissionCallback.onPermissionUpdated(checkIfLegacyStorageOpsNeedToBeUpdated, (str != null && z18 && z10) || z11, androidPackage.getUid());
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    set = r3;
                }
            } catch (java.lang.Throwable th3) {
                th = th3;
            }
        }
    }

    private int[] getAllUserIds() {
        return com.android.server.pm.UserManagerService.getInstance().getUserIdsIncludingPreCreated();
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int[] revokePermissionsNoLongerImplicitLocked(@android.annotation.NonNull com.android.server.pm.permission.UidPermissionState uidPermissionState, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.Collection<java.lang.String> collection, int i, int i2, @android.annotation.NonNull int[] iArr) {
        com.android.server.pm.permission.Permission permission;
        boolean z;
        int i3;
        boolean z2 = i >= 23;
        for (java.lang.String str2 : uidPermissionState.getGrantedPermissions()) {
            if (!collection.contains(str2) && (permission = this.mRegistry.getPermission(str2)) != null && permission.isRuntime()) {
                int permissionFlags = uidPermissionState.getPermissionFlags(str2);
                if ((permissionFlags & 128) != 0) {
                    if (com.android.internal.util.ArrayUtils.contains(NEARBY_DEVICES_PERMISSIONS, str2) && uidPermissionState.isPermissionGranted("android.permission.ACCESS_BACKGROUND_LOCATION") && (uidPermissionState.getPermissionFlags("android.permission.ACCESS_BACKGROUND_LOCATION") & 136) == 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if ((permissionFlags & 52) == 0 && z2 && !z) {
                        uidPermissionState.revokePermission(permission);
                        i3 = 131;
                    } else {
                        i3 = 128;
                    }
                    uidPermissionState.updatePermissionFlags(permission, i3, 0);
                    iArr = com.android.internal.util.ArrayUtils.appendInt(iArr, i2);
                }
            }
        }
        return iArr;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void inheritPermissionStateToNewImplicitPermissionLocked(@android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.pm.permission.UidPermissionState uidPermissionState, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        androidPackage.getPackageName();
        int size = arraySet.size();
        boolean z = false;
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            java.lang.String valueAt = arraySet.valueAt(i2);
            if (uidPermissionState.isPermissionGranted(valueAt)) {
                if (!z) {
                    i = 0;
                }
                i |= uidPermissionState.getPermissionFlags(valueAt);
                z = true;
            } else if (!z) {
                i |= uidPermissionState.getPermissionFlags(valueAt);
            }
        }
        if (z) {
            uidPermissionState.grantPermission(this.mRegistry.getPermission(str));
        }
        uidPermissionState.updatePermissionFlags(this.mRegistry.getPermission(str), i, i);
    }

    @android.annotation.NonNull
    private int[] checkIfLegacyStorageOpsNeedToBeUpdated(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, @android.annotation.NonNull int[] iArr, @android.annotation.NonNull int[] iArr2) {
        if (z && androidPackage.isRequestLegacyExternalStorage() && (androidPackage.getRequestedPermissions().contains("android.permission.READ_EXTERNAL_STORAGE") || androidPackage.getRequestedPermissions().contains("android.permission.WRITE_EXTERNAL_STORAGE"))) {
            return (int[]) iArr.clone();
        }
        return iArr2;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int[] setInitialGrantForNewImplicitPermissionsLocked(@android.annotation.NonNull com.android.server.pm.permission.UidPermissionState uidPermissionState, @android.annotation.NonNull com.android.server.pm.permission.UidPermissionState uidPermissionState2, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet, int i, @android.annotation.NonNull int[] iArr) {
        boolean z;
        androidPackage.getPackageName();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        java.util.List<android.permission.PermissionManager.SplitPermissionInfo> splitPermissionInfos = getSplitPermissionInfos();
        int size = splitPermissionInfos.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.permission.PermissionManager.SplitPermissionInfo splitPermissionInfo = splitPermissionInfos.get(i2);
            java.util.List newPermissions = splitPermissionInfo.getNewPermissions();
            int size2 = newPermissions.size();
            for (int i3 = 0; i3 < size2; i3++) {
                java.lang.String str = (java.lang.String) newPermissions.get(i3);
                android.util.ArraySet arraySet2 = (android.util.ArraySet) arrayMap.get(str);
                if (arraySet2 == null) {
                    arraySet2 = new android.util.ArraySet();
                    arrayMap.put(str, arraySet2);
                }
                arraySet2.add(splitPermissionInfo.getSplitPermission());
            }
        }
        int size3 = arraySet.size();
        int[] iArr2 = iArr;
        for (int i4 = 0; i4 < size3; i4++) {
            java.lang.String valueAt = arraySet.valueAt(i4);
            android.util.ArraySet<java.lang.String> arraySet3 = (android.util.ArraySet) arrayMap.get(valueAt);
            if (arraySet3 != null) {
                com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(valueAt);
                if (permission == null) {
                    throw new java.lang.IllegalStateException("Unknown new permission in split permission: " + valueAt);
                }
                if (permission.isRuntime()) {
                    if (!valueAt.equals("android.permission.ACTIVITY_RECOGNITION") && !READ_MEDIA_AURAL_PERMISSIONS.contains(valueAt) && !READ_MEDIA_VISUAL_PERMISSIONS.contains(valueAt)) {
                        uidPermissionState2.updatePermissionFlags(permission, 128, 128);
                    }
                    iArr2 = com.android.internal.util.ArrayUtils.appendInt(iArr2, i);
                    if (!uidPermissionState.hasPermissionState(arraySet3)) {
                        int i5 = 0;
                        while (true) {
                            if (i5 >= arraySet3.size()) {
                                z = false;
                                break;
                            }
                            java.lang.String valueAt2 = arraySet3.valueAt(i5);
                            com.android.server.pm.permission.Permission permission2 = this.mRegistry.getPermission(valueAt2);
                            if (permission2 == null) {
                                throw new java.lang.IllegalStateException("Unknown source permission in split permission: " + valueAt2);
                            }
                            if (permission2.isRuntime()) {
                                i5++;
                            } else {
                                z = true;
                                break;
                            }
                        }
                        if (!z) {
                        }
                    }
                    inheritPermissionStateToNewImplicitPermissionLocked(arraySet3, valueAt, uidPermissionState2, androidPackage);
                }
            }
        }
        return iArr2;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> getSplitPermissions() {
        return android.permission.PermissionManager.splitPermissionInfoListToParcelableList(getSplitPermissionInfos());
    }

    @android.annotation.NonNull
    private java.util.List<android.permission.PermissionManager.SplitPermissionInfo> getSplitPermissionInfos() {
        return com.android.server.SystemConfig.getInstance().getSplitPermissions();
    }

    private static boolean isCompatPlatformPermissionForPackage(java.lang.String str, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        int length = com.android.internal.pm.permission.CompatibilityPermissionInfo.COMPAT_PERMS.length;
        for (int i = 0; i < length; i++) {
            com.android.internal.pm.permission.CompatibilityPermissionInfo compatibilityPermissionInfo = com.android.internal.pm.permission.CompatibilityPermissionInfo.COMPAT_PERMS[i];
            if (compatibilityPermissionInfo.getName().equals(str) && androidPackage.getTargetSdkVersion() < compatibilityPermissionInfo.getSdkVersion()) {
                android.util.Log.i(TAG, "Auto-granting " + str + " to old pkg " + androidPackage.getPackageName());
                return true;
            }
        }
        return false;
    }

    private boolean checkPrivilegedPermissionAllowlist(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, @android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        if (com.android.internal.os.RoSystemProperties.CONTROL_PRIVAPP_PERMISSIONS_DISABLE) {
            return true;
        }
        java.lang.String packageName = androidPackage.getPackageName();
        if (java.util.Objects.equals(packageName, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME) || !packageStateInternal.isSystem() || !packageStateInternal.isPrivileged() || !this.mPrivilegedPermissionAllowlistSourcePackageNames.contains(permission.getPackageName())) {
            return true;
        }
        java.lang.String name = permission.getName();
        java.lang.Boolean privilegedPermissionAllowlistState = getPrivilegedPermissionAllowlistState(packageStateInternal, name, this.mApexManager.getActiveApexPackageNameContainingPackage(packageName));
        if (privilegedPermissionAllowlistState != null) {
            return privilegedPermissionAllowlistState.booleanValue();
        }
        if (packageStateInternal.isUpdatedSystemApp()) {
            return true;
        }
        if (!this.mSystemReady && !packageStateInternal.isApkInUpdatedApex()) {
            android.util.Slog.w(TAG, "Privileged permission " + name + " for package " + packageName + " (" + androidPackage.getPath() + ") not in privapp-permissions allowlist");
            if (com.android.internal.os.RoSystemProperties.CONTROL_PRIVAPP_PERMISSIONS_ENFORCE) {
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        if (this.mPrivappPermissionsViolations == null) {
                            this.mPrivappPermissionsViolations = new android.util.ArraySet<>();
                        }
                        this.mPrivappPermissionsViolations.add(packageName + " (" + androidPackage.getPath() + "): " + name);
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        return !com.android.internal.os.RoSystemProperties.CONTROL_PRIVAPP_PERMISSIONS_ENFORCE;
    }

    @android.annotation.Nullable
    private java.lang.Boolean getPrivilegedPermissionAllowlistState(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.NonNull java.lang.String str, java.lang.String str2) {
        com.android.server.pm.permission.PermissionAllowlist permissionAllowlist = com.android.server.SystemConfig.getInstance().getPermissionAllowlist();
        java.lang.String packageName = packageState.getPackageName();
        if (packageState.isVendor() || packageState.isOdm()) {
            return permissionAllowlist.getVendorPrivilegedAppAllowlistState(packageName, str);
        }
        if (packageState.isProduct()) {
            return permissionAllowlist.getProductPrivilegedAppAllowlistState(packageName, str);
        }
        if (packageState.isSystemExt()) {
            return permissionAllowlist.getSystemExtPrivilegedAppAllowlistState(packageName, str);
        }
        if (str2 != null) {
            java.lang.Boolean privilegedAppAllowlistState = permissionAllowlist.getPrivilegedAppAllowlistState(packageName, str);
            if (privilegedAppAllowlistState != null) {
                android.util.Slog.w(TAG, "Package " + packageName + " is an APK in APEX, but has permission allowlist on the system image. Please bundle the allowlist in the " + str2 + " APEX instead.");
            }
            java.lang.Boolean apexPrivilegedAppAllowlistState = permissionAllowlist.getApexPrivilegedAppAllowlistState(this.mApexManager.getApexModuleNameForPackageName(str2), packageName, str);
            if (apexPrivilegedAppAllowlistState != null) {
                return apexPrivilegedAppAllowlistState;
            }
            return privilegedAppAllowlistState;
        }
        return permissionAllowlist.getPrivilegedAppAllowlistState(packageName, str);
    }

    private boolean shouldGrantPermissionBySignature(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        com.android.server.pm.pkg.AndroidPackage androidPackage2 = this.mPackageManagerInt.getPackage((java.lang.String) com.android.internal.util.ArrayUtils.firstOrNull(this.mPackageManagerInt.getKnownPackageNames(0, 0)));
        return getSourcePackageSigningDetails(permission).hasCommonSignerWithCapability(androidPackage.getSigningDetails(), 4) || androidPackage.getSigningDetails().hasAncestorOrSelf(androidPackage2.getSigningDetails()) || androidPackage2.getSigningDetails().checkCapability(androidPackage.getSigningDetails(), 4);
    }

    private boolean shouldGrantPermissionByProtectionFlags(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, @android.annotation.NonNull com.android.server.pm.permission.Permission permission, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet) {
        boolean z;
        boolean isPrivileged = permission.isPrivileged();
        boolean isOem = permission.isOem();
        if ((isPrivileged || isOem) && packageStateInternal.isSystem()) {
            java.lang.String name = permission.getName();
            if (packageStateInternal.isUpdatedSystemApp()) {
                com.android.server.pm.pkg.PackageStateInternal disabledSystemPackage = this.mPackageManagerInt.getDisabledSystemPackage(androidPackage.getPackageName());
                com.android.server.pm.pkg.AndroidPackage pkg = disabledSystemPackage == null ? null : disabledSystemPackage.getPkg();
                if (pkg != null && ((isPrivileged && disabledSystemPackage.isPrivileged()) || (isOem && canGrantOemPermission(disabledSystemPackage, name)))) {
                    if (pkg.getRequestedPermissions().contains(name)) {
                        z = true;
                    } else {
                        arraySet.add(name);
                    }
                }
                z = false;
            } else {
                z = (isPrivileged && packageStateInternal.isPrivileged()) || (isOem && canGrantOemPermission(packageStateInternal, name));
            }
            if (z && isPrivileged && !permission.isVendorPrivileged() && (packageStateInternal.isVendor() || packageStateInternal.isOdm())) {
                android.util.Slog.w(TAG, "Permission " + name + " cannot be granted to privileged vendor apk " + androidPackage.getPackageName() + " because it isn't a 'vendorPrivileged' permission.");
                z = false;
            }
        } else {
            z = false;
        }
        if (!z && permission.isPre23() && androidPackage.getTargetSdkVersion() < 23) {
            z = true;
        }
        if (!z && permission.isInstaller() && (com.android.internal.util.ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(2, 0), androidPackage.getPackageName()) || com.android.internal.util.ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(7, 0), androidPackage.getPackageName()))) {
            z = true;
        }
        if (!z && permission.isVerifier() && com.android.internal.util.ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(4, 0), androidPackage.getPackageName())) {
            z = true;
        }
        if (!z && permission.isPreInstalled() && packageStateInternal.isSystem()) {
            z = true;
        }
        if (!z && permission.isKnownSigner()) {
            z = androidPackage.getSigningDetails().hasAncestorOrSelfWithDigest(permission.getKnownCerts());
        }
        if (!z && permission.isSetup() && com.android.internal.util.ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(1, 0), androidPackage.getPackageName())) {
            z = true;
        }
        if (!z && permission.isSystemTextClassifier() && com.android.internal.util.ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(6, 0), androidPackage.getPackageName())) {
            z = true;
        }
        if (!z && permission.isConfigurator() && com.android.internal.util.ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(10, 0), androidPackage.getPackageName())) {
            z = true;
        }
        if (!z && permission.isIncidentReportApprover() && com.android.internal.util.ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(11, 0), androidPackage.getPackageName())) {
            z = true;
        }
        if (!z && permission.isAppPredictor() && com.android.internal.util.ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(12, 0), androidPackage.getPackageName())) {
            z = true;
        }
        if (!z && permission.isCompanion() && com.android.internal.util.ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(15, 0), androidPackage.getPackageName())) {
            z = true;
        }
        if (!z && permission.isRetailDemo() && com.android.internal.util.ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(16, 0), androidPackage.getPackageName()) && isProfileOwner(androidPackage.getUid())) {
            z = true;
        }
        if (!z && permission.isRecents() && com.android.internal.util.ArrayUtils.contains(this.mPackageManagerInt.getKnownPackageNames(17, 0), androidPackage.getPackageName())) {
            z = true;
        }
        if (!z && permission.isModule() && this.mApexManager.getActiveApexPackageNameContainingPackage(androidPackage.getPackageName()) != null) {
            return true;
        }
        return z;
    }

    @android.annotation.NonNull
    private android.content.pm.SigningDetails getSourcePackageSigningDetails(@android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        com.android.server.pm.pkg.PackageStateInternal sourcePackageSetting = getSourcePackageSetting(permission);
        if (sourcePackageSetting == null) {
            return android.content.pm.SigningDetails.UNKNOWN;
        }
        return sourcePackageSetting.getSigningDetails();
    }

    @android.annotation.Nullable
    private com.android.server.pm.pkg.PackageStateInternal getSourcePackageSetting(@android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        return this.mPackageManagerInt.getPackageStateInternal(permission.getPackageName());
    }

    private static boolean canGrantOemPermission(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, java.lang.String str) {
        if (!packageState.isOem()) {
            return false;
        }
        java.lang.String packageName = packageState.getPackageName();
        java.lang.Boolean oemAppAllowlistState = com.android.server.SystemConfig.getInstance().getPermissionAllowlist().getOemAppAllowlistState(packageState.getPackageName(), str);
        if (oemAppAllowlistState != null) {
            return java.lang.Boolean.TRUE == oemAppAllowlistState;
        }
        throw new java.lang.IllegalStateException("OEM permission " + str + " requested by package " + packageName + " must be explicitly declared granted or not");
    }

    private static boolean isProfileOwner(int i) {
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        if (devicePolicyManagerInternal != null) {
            return devicePolicyManagerInternal.isActiveProfileOwner(i) || devicePolicyManagerInternal.isActiveDeviceOwner(i);
        }
        return false;
    }

    private boolean isPermissionsReviewRequiredInternal(@android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (androidPackage == null || androidPackage.getTargetSdkVersion() >= 23) {
            return false;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i);
                if (uidStateLocked == null) {
                    android.util.Slog.e(TAG, "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i);
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return false;
                }
                boolean isPermissionsReviewRequired = uidStateLocked.isPermissionsReviewRequired();
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return isPermissionsReviewRequired;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    private void grantRequestedPermissionsInternal(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap, int i) {
        boolean z = androidPackage.getTargetSdkVersion() >= 23;
        boolean isInstantApp = this.mPackageManagerInt.isInstantApp(androidPackage.getPackageName(), i);
        int myUid = android.os.Process.myUid();
        for (java.lang.String str : androidPackage.getRequestedPermissions()) {
            java.lang.Integer num = arrayMap.get(str);
            if (num != null && num.intValue() != 0) {
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str);
                        if (permission == null) {
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        } else {
                            boolean z2 = (permission.isRuntime() || permission.isDevelopment()) && (!isInstantApp || permission.isInstant()) && ((z || !permission.isRuntimeOnly()) && num.intValue() == 1);
                            boolean isAppOp = permission.isAppOp();
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            int permissionFlagsInternal = getPermissionFlagsInternal(androidPackage.getPackageName(), str, myUid, i);
                            if (z2) {
                                if (z) {
                                    if ((permissionFlagsInternal & 20) == 0) {
                                        grantRuntimePermissionInternal(androidPackage.getPackageName(), str, false, myUid, i, this.mDefaultPermissionCallback);
                                    }
                                } else if ((permissionFlagsInternal & 72) != 0) {
                                    updatePermissionFlagsInternal(androidPackage.getPackageName(), str, 72, 0, myUid, i, false, this.mDefaultPermissionCallback);
                                }
                            } else if (isAppOp && com.android.server.pm.PackageInstallerService.INSTALLER_CHANGEABLE_APP_OP_PERMISSIONS.contains(str) && (permissionFlagsInternal & 1) == 0) {
                                final int i2 = num.intValue() == 1 ? 0 : 2;
                                final int uid = android.os.UserHandle.getUid(i, androidPackage.getUid());
                                final java.lang.String permissionToOp = android.app.AppOpsManager.permissionToOp(str);
                                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda6
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$grantRequestedPermissionsInternal$9(permissionToOp, uid, i2);
                                    }
                                });
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$grantRequestedPermissionsInternal$9(java.lang.String str, int i, int i2) {
        ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).setUidMode(str, i, i2);
    }

    private void setAllowlistedRestrictedPermissionsInternal(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable java.util.List<java.lang.String> list, int i, int i2) {
        android.util.ArraySet arraySet;
        int i3;
        int i4;
        int myUid = android.os.Process.myUid();
        android.util.ArraySet arraySet2 = null;
        boolean z = false;
        for (java.lang.String str : androidPackage.getRequestedPermissions()) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str);
                    if (permission == null || !permission.isHardOrSoftRestricted()) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    } else {
                        com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i2);
                        if (uidStateLocked == null) {
                            android.util.Slog.e(TAG, "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        } else {
                            boolean isPermissionGranted = uidStateLocked.isPermissionGranted(str);
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            if (!isPermissionGranted) {
                                arraySet = arraySet2;
                            } else {
                                if (arraySet2 == null) {
                                    arraySet2 = new android.util.ArraySet();
                                }
                                arraySet2.add(str);
                                arraySet = arraySet2;
                            }
                            int permissionFlagsInternal = getPermissionFlagsInternal(androidPackage.getPackageName(), str, myUid, i2);
                            int i5 = i;
                            int i6 = permissionFlagsInternal;
                            int i7 = 0;
                            while (i5 != 0) {
                                int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i5);
                                i5 &= ~numberOfTrailingZeros;
                                switch (numberOfTrailingZeros) {
                                    case 1:
                                        i7 |= 4096;
                                        if (list != null && list.contains(str)) {
                                            i6 |= 4096;
                                            break;
                                        } else {
                                            i6 &= -4097;
                                            break;
                                        }
                                    case 2:
                                        i7 |= 2048;
                                        if (list != null && list.contains(str)) {
                                            i6 |= 2048;
                                            break;
                                        } else {
                                            i6 &= -2049;
                                            break;
                                        }
                                    case 4:
                                        i7 |= 8192;
                                        if (list != null && list.contains(str)) {
                                            i6 |= 8192;
                                            break;
                                        } else {
                                            i6 &= -8193;
                                            break;
                                        }
                                }
                            }
                            if (permissionFlagsInternal == i6) {
                                arraySet2 = arraySet;
                            } else {
                                boolean z2 = (permissionFlagsInternal & 14336) != 0;
                                boolean z3 = (i6 & 14336) != 0;
                                if ((permissionFlagsInternal & 4) != 0 && !z3 && isPermissionGranted) {
                                    i7 |= 4;
                                    i6 &= -5;
                                }
                                if (androidPackage.getTargetSdkVersion() < 23 && !z2 && z3) {
                                    i4 = i7 | 64;
                                    i3 = i6 | 64;
                                } else {
                                    i3 = i6;
                                    i4 = i7;
                                }
                                updatePermissionFlagsInternal(androidPackage.getPackageName(), str, i4, i3, myUid, i2, false, null);
                                arraySet2 = arraySet;
                                z = true;
                            }
                        }
                    }
                } finally {
                }
            }
        }
        if (z) {
            restorePermissionState(androidPackage, false, androidPackage.getPackageName(), this.mDefaultPermissionCallback, i2);
            if (arraySet2 == null) {
                return;
            }
            int size = arraySet2.size();
            for (int i8 = 0; i8 < size; i8++) {
                java.lang.String str2 = (java.lang.String) arraySet2.valueAt(i8);
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock2) {
                    try {
                        com.android.server.pm.permission.UidPermissionState uidStateLocked2 = getUidStateLocked(androidPackage, i2);
                        if (uidStateLocked2 == null) {
                            android.util.Slog.e(TAG, "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        } else {
                            boolean isPermissionGranted2 = uidStateLocked2.isPermissionGranted(str2);
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            if (!isPermissionGranted2) {
                                this.mDefaultPermissionCallback.onPermissionRevoked(android.os.UserHandle.getUid(i2, androidPackage.getUid()), i2, null);
                                return;
                            }
                        }
                    } finally {
                    }
                }
            }
        }
    }

    private void revokeSharedUserPermissionsForLeavingPackageInternal(@android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, final int i, @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.AndroidPackage> list, int i2) {
        boolean z;
        if (androidPackage == null) {
            android.util.Slog.i(TAG, "Trying to update info for null package. Just ignoring");
            return;
        }
        if (list.isEmpty()) {
            return;
        }
        com.android.server.pm.pkg.PackageStateInternal disabledSystemPackage = this.mPackageManagerInt.getDisabledSystemPackage(androidPackage.getPackageName());
        boolean z2 = disabledSystemPackage != null && disabledSystemPackage.getAppId() == androidPackage.getUid();
        boolean z3 = false;
        for (java.lang.String str : androidPackage.getRequestedPermissions()) {
            java.util.Iterator<com.android.server.pm.pkg.AndroidPackage> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                com.android.server.pm.pkg.AndroidPackage next = it.next();
                if (next != null && !next.getPackageName().equals(androidPackage.getPackageName()) && next.getRequestedPermissions().contains(str)) {
                    z = true;
                    break;
                }
            }
            if (!z && (!z2 || !disabledSystemPackage.getPkg().getRequestedPermissions().contains(str))) {
                com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
                com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                synchronized (packageManagerTracedLock) {
                    try {
                        com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(i, i2);
                        if (uidStateLocked == null) {
                            android.util.Slog.e(TAG, "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i2);
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        } else {
                            com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(str);
                            if (permission == null) {
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            } else {
                                if (uidStateLocked.removePermissionState(permission.getName()) && permission.hasGids()) {
                                    z3 = true;
                                }
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                }
            }
        }
        if (z3) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.permission.PermissionManagerServiceImpl.killUid(i, -1, "permission grant or revoke changed gids");
                }
            });
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean revokeUnusedSharedUserPermissionsLocked(@android.annotation.NonNull java.util.Collection<java.lang.String> collection, @android.annotation.NonNull com.android.server.pm.permission.UidPermissionState uidPermissionState) {
        com.android.server.pm.permission.Permission permission;
        java.util.List<com.android.server.pm.permission.PermissionState> permissionStates = uidPermissionState.getPermissionStates();
        boolean z = false;
        for (int size = permissionStates.size() - 1; size >= 0; size--) {
            com.android.server.pm.permission.PermissionState permissionState = permissionStates.get(size);
            if (!collection.contains(permissionState.getName()) && (permission = this.mRegistry.getPermission(permissionState.getName())) != null && uidPermissionState.removePermissionState(permission.getName()) && permission.isRuntime()) {
                z = true;
            }
        }
        return z;
    }

    private void updatePermissions(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage) {
        updatePermissions(str, androidPackage, getVolumeUuidForPackage(androidPackage), androidPackage == null ? 3 : 2, this.mDefaultPermissionCallback);
    }

    private void updateAllPermissions(@android.annotation.NonNull java.lang.String str, boolean z) {
        int i;
        android.content.pm.PackageManager.corkPackageInfoCache();
        if (z) {
            i = 6;
        } else {
            i = 0;
        }
        try {
            updatePermissions(null, null, str, 1 | i, this.mDefaultPermissionCallback);
        } finally {
            android.content.pm.PackageManager.uncorkPackageInfoCache();
        }
    }

    private void updatePermissions(@android.annotation.Nullable final java.lang.String str, @android.annotation.Nullable final com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable final java.lang.String str2, int i, @android.annotation.Nullable final com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback permissionCallback) {
        int i2;
        if (!(updatePermissionTreeSourcePackage(str, androidPackage) | updatePermissionSourcePackage(str, permissionCallback))) {
            i2 = i;
        } else {
            android.util.Slog.i(TAG, "Permission ownership changed. Updating all permissions.");
            i2 = i | 1;
        }
        android.os.Trace.traceBegin(262144L, "restorePermissionState");
        if ((i2 & 1) != 0) {
            final boolean z = (i2 & 4) != 0;
            this.mPackageManagerInt.forEachPackage(new java.util.function.Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$updatePermissions$11(androidPackage, z, str2, str, permissionCallback, (com.android.server.pm.pkg.AndroidPackage) obj);
                }
            });
        }
        if (androidPackage != null) {
            restorePermissionState(androidPackage, (i2 & 2) != 0 && java.util.Objects.equals(str2, getVolumeUuidForPackage(androidPackage)), str, permissionCallback, -1);
        }
        android.os.Trace.traceEnd(262144L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePermissions$11(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, java.lang.String str, java.lang.String str2, com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback permissionCallback, com.android.server.pm.pkg.AndroidPackage androidPackage2) {
        if (androidPackage2 == androidPackage) {
            return;
        }
        restorePermissionState(androidPackage2, z && java.util.Objects.equals(str, getVolumeUuidForPackage(androidPackage2)), str2, permissionCallback, -1);
    }

    private boolean updatePermissionSourcePackage(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable final com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback permissionCallback) {
        android.util.ArraySet<com.android.server.pm.permission.Permission> arraySet;
        boolean z;
        if (str == null) {
            return true;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                arraySet = null;
                z = false;
                for (com.android.server.pm.permission.Permission permission : this.mRegistry.getPermissions()) {
                    if (permission.isDynamic()) {
                        permission.updateDynamicPermission(this.mRegistry.getPermissionTrees());
                    }
                    if (str.equals(permission.getPackageName())) {
                        if (arraySet == null) {
                            arraySet = new android.util.ArraySet();
                        }
                        arraySet.add(permission);
                        z = true;
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (arraySet != null) {
            com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
            for (final com.android.server.pm.permission.Permission permission2 : arraySet) {
                if (androidPackage == null || !hasPermission(androidPackage, permission2.getName())) {
                    if (!isPermissionDeclaredByDisabledSystemPkg(permission2)) {
                        android.util.Slog.i(TAG, "Removing permission " + permission2.getName() + " that used to be declared by " + permission2.getPackageName());
                        if (permission2.isRuntime()) {
                            for (final int i : this.mUserManagerInt.getUserIds()) {
                                this.mPackageManagerInt.forEachPackage(new java.util.function.Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda3
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$updatePermissionSourcePackage$12(permission2, i, permissionCallback, (com.android.server.pm.pkg.AndroidPackage) obj);
                                    }
                                });
                            }
                        } else {
                            this.mPackageManagerInt.forEachPackage(new java.util.function.Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda4
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$updatePermissionSourcePackage$13(permission2, (com.android.server.pm.pkg.AndroidPackage) obj);
                                }
                            });
                        }
                    }
                    com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mLock;
                    com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                    synchronized (packageManagerTracedLock2) {
                        try {
                            this.mRegistry.removePermission(permission2.getName());
                        } finally {
                        }
                    }
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                } else {
                    com.android.server.pm.pkg.AndroidPackage androidPackage2 = this.mPackageManagerInt.getPackage(permission2.getPackageName());
                    com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(permission2.getPackageName());
                    com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock3 = this.mLock;
                    com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                    synchronized (packageManagerTracedLock3) {
                        if (androidPackage2 == null || packageStateInternal == null) {
                            android.util.Slog.w(TAG, "Removing dangling permission: " + permission2.getName() + " from package " + permission2.getPackageName());
                            this.mRegistry.removePermission(permission2.getName());
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        } else {
                            try {
                                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            } finally {
                            }
                        }
                    }
                }
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePermissionSourcePackage$12(com.android.server.pm.permission.Permission permission, int i, com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback permissionCallback, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        revokePermissionFromPackageForUser(androidPackage.getPackageName(), permission.getName(), true, i, permissionCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePermissionSourcePackage$13(com.android.server.pm.permission.Permission permission, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        int[] userIds = this.mUserManagerInt.getUserIds();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                for (int i : userIds) {
                    com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(androidPackage, i);
                    if (uidStateLocked == null) {
                        android.util.Slog.e(TAG, "Missing permissions state for " + androidPackage.getPackageName() + " and user " + i);
                    } else {
                        uidStateLocked.removePermissionState(permission.getName());
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    private boolean isPermissionDeclaredByDisabledSystemPkg(@android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        com.android.server.pm.pkg.PackageStateInternal disabledSystemPackage = this.mPackageManagerInt.getDisabledSystemPackage(permission.getPackageName());
        if (disabledSystemPackage != null && disabledSystemPackage.getPkg() != null) {
            java.lang.String name = permission.getName();
            for (com.android.internal.pm.pkg.component.ParsedPermission parsedPermission : disabledSystemPackage.getPkg().getPermissions()) {
                if (android.text.TextUtils.equals(name, parsedPermission.getName()) && permission.getProtectionLevel() == parsedPermission.getProtectionLevel()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private void revokePermissionFromPackageForUser(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, boolean z, int i, @android.annotation.Nullable com.android.server.pm.permission.PermissionManagerServiceImpl.PermissionCallback permissionCallback) {
        android.content.pm.ApplicationInfo applicationInfo = this.mPackageManagerInt.getApplicationInfo(str, 0L, 1000, 0);
        if ((applicationInfo == null || applicationInfo.targetSdkVersion >= 23) && checkPermission(str, str2, i) == 0) {
            try {
                revokeRuntimePermissionInternal(str, str2, z, 1000, i, null, permissionCallback);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.e(TAG, "Failed to revoke " + str2 + " from " + str, e);
            }
        }
    }

    private boolean updatePermissionTreeSourcePackage(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage) {
        boolean z;
        if (str == null) {
            return true;
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                java.util.Iterator<com.android.server.pm.permission.Permission> it = this.mRegistry.getPermissionTrees().iterator();
                z = false;
                while (it.hasNext()) {
                    com.android.server.pm.permission.Permission next = it.next();
                    if (str.equals(next.getPackageName())) {
                        if (androidPackage == null || !hasPermission(androidPackage, next.getName())) {
                            android.util.Slog.i(TAG, "Removing permission tree " + next.getName() + " that used to be declared by " + next.getPackageName());
                            it.remove();
                        }
                        z = true;
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return z;
    }

    private void enforceGrantRevokeRuntimePermissionPermissions(java.lang.String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.GRANT_RUNTIME_PERMISSIONS") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS") != 0) {
            throw new java.lang.SecurityException(str + " requires android.permission.GRANT_RUNTIME_PERMISSIONS or android.permission.REVOKE_RUNTIME_PERMISSIONS");
        }
    }

    private void enforceGrantRevokeGetRuntimePermissionPermissions(@android.annotation.NonNull java.lang.String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.GET_RUNTIME_PERMISSIONS") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.GRANT_RUNTIME_PERMISSIONS") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS") != 0) {
            throw new java.lang.SecurityException(str + " requires android.permission.GRANT_RUNTIME_PERMISSIONS or android.permission.REVOKE_RUNTIME_PERMISSIONS or android.permission.GET_RUNTIME_PERMISSIONS");
        }
    }

    private void enforceCrossUserPermission(int i, int i2, boolean z, boolean z2, @android.annotation.Nullable java.lang.String str) {
        if (i2 < 0) {
            throw new java.lang.IllegalArgumentException("Invalid userId " + i2);
        }
        if (z2) {
            enforceShellRestriction("no_debugging_features", i, i2);
        }
        if (checkCrossUserPermission(i, android.os.UserHandle.getUserId(i), i2, z)) {
            return;
        }
        java.lang.String buildInvalidCrossUserPermissionMessage = buildInvalidCrossUserPermissionMessage(i, i2, str, z);
        android.util.Slog.w(TAG, buildInvalidCrossUserPermissionMessage);
        throw new java.lang.SecurityException(buildInvalidCrossUserPermissionMessage);
    }

    private void enforceShellRestriction(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        if (i == 2000) {
            if (i2 >= 0 && this.mUserManagerInt.hasUserRestriction(str, i2)) {
                throw new java.lang.SecurityException("Shell does not have permission to access user " + i2);
            }
            if (i2 < 0) {
                android.util.Slog.e(LOG_TAG, "Unable to check shell permission for user " + i2 + "\n\t" + android.os.Debug.getCallers(3));
            }
        }
    }

    private boolean checkCrossUserPermission(int i, int i2, int i3, boolean z) {
        if (i3 == i2 || i == 1000 || i == 0) {
            return true;
        }
        if (z) {
            return checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL");
        }
        return checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") || checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS");
    }

    private boolean checkCallingOrSelfPermission(java.lang.String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    @android.annotation.NonNull
    private static java.lang.String buildInvalidCrossUserPermissionMessage(int i, int i2, @android.annotation.Nullable java.lang.String str, boolean z) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (str != null) {
            sb.append(str);
            sb.append(": ");
        }
        sb.append("UID ");
        sb.append(i);
        sb.append(" requires ");
        sb.append("android.permission.INTERACT_ACROSS_USERS_FULL");
        if (!z) {
            sb.append(" or ");
            sb.append("android.permission.INTERACT_ACROSS_USERS");
        }
        sb.append(" to access user ");
        sb.append(i2);
        sb.append(".");
        return sb.toString();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int calculateCurrentPermissionFootprintLocked(@android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        java.util.Iterator<com.android.server.pm.permission.Permission> it = this.mRegistry.getPermissions().iterator();
        int i = 0;
        while (it.hasNext()) {
            i += permission.calculateFootprint(it.next());
        }
        return i;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void enforcePermissionCapLocked(android.content.pm.PermissionInfo permissionInfo, com.android.server.pm.permission.Permission permission) {
        if (permission.getUid() != 1000 && calculateCurrentPermissionFootprintLocked(permission) + permissionInfo.calculateFootprint() > 32768) {
            throw new java.lang.SecurityException("Permission tree size cap exceeded");
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onSystemReady() {
        updateAllPermissions(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, false);
        ((com.android.server.policy.PermissionPolicyInternal) com.android.server.LocalServices.getService(com.android.server.policy.PermissionPolicyInternal.class)).setOnInitializedCallback(new com.android.server.policy.PermissionPolicyInternal.OnInitializedCallback() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda18
            @Override // com.android.server.policy.PermissionPolicyInternal.OnInitializedCallback
            public final void onInitialized(int i) {
                com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$onSystemReady$14(i);
            }
        });
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mSystemReady = true;
                if (this.mPrivappPermissionsViolations != null) {
                    throw new java.lang.IllegalStateException("Signature|privileged permissions not in privapp-permissions allowlist: " + this.mPrivappPermissionsViolations);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        this.mPermissionControllerManager = new android.permission.PermissionControllerManager(this.mContext, com.android.server.PermissionThread.getHandler());
        this.mPermissionPolicyInternal = (com.android.server.policy.PermissionPolicyInternal) com.android.server.LocalServices.getService(com.android.server.policy.PermissionPolicyInternal.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$14(int i) {
        updateAllPermissions(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, false);
    }

    private static java.lang.String getVolumeUuidForPackage(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (androidPackage == null) {
            return android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL;
        }
        if (androidPackage.isExternalStorage()) {
            if (android.text.TextUtils.isEmpty(androidPackage.getVolumeUuid())) {
                return "primary_physical";
            }
            return androidPackage.getVolumeUuid();
        }
        return android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL;
    }

    private static boolean hasPermission(com.android.server.pm.pkg.AndroidPackage androidPackage, java.lang.String str) {
        if (androidPackage.getPermissions().isEmpty()) {
            return false;
        }
        for (int size = androidPackage.getPermissions().size() - 1; size >= 0; size--) {
            if (((com.android.internal.pm.pkg.component.ParsedPermission) androidPackage.getPermissions().get(size)).getName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void logPermission(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.metrics.LogMaker logMaker = new android.metrics.LogMaker(i);
        logMaker.setPackageName(str2);
        logMaker.addTaggedData(1241, str);
        this.mMetricsLogger.write(logMaker);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.pm.permission.UidPermissionState getUidStateLocked(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i) {
        return getUidStateLocked(packageStateInternal.getAppId(), i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.pm.permission.UidPermissionState getUidStateLocked(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        return getUidStateLocked(androidPackage.getUid(), i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.pm.permission.UidPermissionState getUidStateLocked(int i, int i2) {
        com.android.server.pm.permission.UserPermissionState userState = this.mState.getUserState(i2);
        if (userState == null) {
            return null;
        }
        return userState.getUidState(i);
    }

    private void removeUidStateAndResetPackageInstallPermissionsFixed(int i, @android.annotation.NonNull java.lang.String str, int i2) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.UserPermissionState userState = this.mState.getUserState(i2);
                if (userState == null) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return;
                }
                userState.removeUidState(i);
                userState.setInstallPermissionsFixed(str, false);
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void readLegacyPermissionStateTEMP() {
        final int[] allUserIds = getAllUserIds();
        this.mPackageManagerInt.forEachPackageState(new java.util.function.Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$readLegacyPermissionStateTEMP$15(allUserIds, (com.android.server.pm.pkg.PackageStateInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readLegacyPermissionStateTEMP$15(int[] iArr, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        com.android.server.pm.permission.LegacyPermissionState legacyPermissionState;
        int appId = packageStateInternal.getAppId();
        if (packageStateInternal.hasSharedUser()) {
            int sharedUserAppId = packageStateInternal.getSharedUserAppId();
            com.android.server.pm.pkg.SharedUserApi sharedUserApi = this.mPackageManagerInt.getSharedUserApi(sharedUserAppId);
            if (sharedUserApi == null) {
                android.util.Slog.wtf(TAG, "Missing shared user Api for " + sharedUserAppId);
                return;
            }
            legacyPermissionState = sharedUserApi.getSharedUserLegacyPermissionState();
        } else {
            legacyPermissionState = packageStateInternal.getLegacyPermissionState();
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                for (int i : iArr) {
                    com.android.server.pm.permission.UserPermissionState orCreateUserState = this.mState.getOrCreateUserState(i);
                    orCreateUserState.setInstallPermissionsFixed(packageStateInternal.getPackageName(), packageStateInternal.isInstallPermissionsFixed());
                    com.android.server.pm.permission.UidPermissionState orCreateUidState = orCreateUserState.getOrCreateUidState(appId);
                    orCreateUidState.reset();
                    orCreateUidState.setMissing(legacyPermissionState.isMissing(i));
                    readLegacyPermissionStatesLocked(orCreateUidState, legacyPermissionState.getPermissionStates(i));
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void readLegacyPermissionStatesLocked(@android.annotation.NonNull com.android.server.pm.permission.UidPermissionState uidPermissionState, @android.annotation.NonNull java.util.Collection<com.android.server.pm.permission.LegacyPermissionState.PermissionState> collection) {
        for (com.android.server.pm.permission.LegacyPermissionState.PermissionState permissionState : collection) {
            java.lang.String name = permissionState.getName();
            com.android.server.pm.permission.Permission permission = this.mRegistry.getPermission(name);
            if (permission == null) {
                android.util.Slog.w(TAG, "Unknown permission: " + name);
            } else {
                uidPermissionState.putPermissionState(permission, permissionState.isGranted(), permissionState.getFlags());
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void writeLegacyPermissionStateTEMP() {
        final int[] userIds;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                userIds = this.mState.getUserIds();
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        this.mPackageManagerInt.forEachPackageSetting(new java.util.function.Consumer() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$writeLegacyPermissionStateTEMP$16(userIds, (com.android.server.pm.PackageSetting) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$writeLegacyPermissionStateTEMP$16(int[] iArr, com.android.server.pm.PackageSetting packageSetting) {
        com.android.server.pm.permission.LegacyPermissionState legacyPermissionState;
        com.android.server.pm.permission.PermissionManagerServiceImpl permissionManagerServiceImpl = this;
        int i = 0;
        packageSetting.setInstallPermissionsFixed(false);
        if (packageSetting.hasSharedUser()) {
            int sharedUserAppId = packageSetting.getSharedUserAppId();
            com.android.server.pm.pkg.SharedUserApi sharedUserApi = permissionManagerServiceImpl.mPackageManagerInt.getSharedUserApi(sharedUserAppId);
            if (sharedUserApi == null) {
                android.util.Slog.wtf(TAG, "Missing shared user Api for " + sharedUserAppId);
                return;
            }
            legacyPermissionState = sharedUserApi.getSharedUserLegacyPermissionState();
        } else {
            legacyPermissionState = packageSetting.getLegacyPermissionState();
        }
        legacyPermissionState.reset();
        int appId = packageSetting.getAppId();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = permissionManagerServiceImpl.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                int length = iArr.length;
                int i2 = 0;
                while (i2 < length) {
                    int i3 = iArr[i2];
                    com.android.server.pm.permission.UserPermissionState userState = permissionManagerServiceImpl.mState.getUserState(i3);
                    if (userState == null) {
                        android.util.Slog.e(TAG, "Missing user state for " + i3);
                    } else {
                        if (userState.areInstallPermissionsFixed(packageSetting.getPackageName())) {
                            packageSetting.setInstallPermissionsFixed(true);
                        }
                        com.android.server.pm.permission.UidPermissionState uidState = userState.getUidState(appId);
                        if (uidState == null) {
                            android.util.Slog.e(TAG, "Missing permission state for " + packageSetting.getPackageName() + " and user " + i3);
                        } else {
                            legacyPermissionState.setMissing(uidState.isMissing(), i3);
                            java.util.List<com.android.server.pm.permission.PermissionState> permissionStates = uidState.getPermissionStates();
                            int size = permissionStates.size();
                            for (int i4 = i; i4 < size; i4++) {
                                com.android.server.pm.permission.PermissionState permissionState = permissionStates.get(i4);
                                legacyPermissionState.putPermissionState(new com.android.server.pm.permission.LegacyPermissionState.PermissionState(permissionState.getName(), permissionState.getPermission().isRuntime(), permissionState.isGranted(), permissionState.getFlags()), i3);
                            }
                        }
                    }
                    i2++;
                    permissionManagerServiceImpl = this;
                    i = 0;
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void readLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings) {
        java.util.List<com.android.server.pm.permission.LegacyPermission> permissionTrees;
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                permissionTrees = legacyPermissionSettings.getPermissions();
            } else {
                permissionTrees = legacyPermissionSettings.getPermissionTrees();
            }
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    int size = permissionTrees.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        com.android.server.pm.permission.LegacyPermission legacyPermission = permissionTrees.get(i2);
                        com.android.server.pm.permission.Permission permission = new com.android.server.pm.permission.Permission(legacyPermission.getPermissionInfo(), legacyPermission.getType());
                        if (i == 0) {
                            com.android.server.pm.permission.Permission permission2 = this.mRegistry.getPermission(permission.getName());
                            if (permission2 != null && permission2.getType() == 1) {
                                permission.setGids(permission2.getRawGids(), permission2.areGidsPerUser());
                            }
                            this.mRegistry.addPermission(permission);
                        } else {
                            this.mRegistry.addPermissionTree(permission);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void writeLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings) {
        java.util.Collection<com.android.server.pm.permission.Permission> permissions;
        for (int i = 0; i < 2; i++) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                if (i == 0) {
                    try {
                        permissions = this.mRegistry.getPermissions();
                    } catch (java.lang.Throwable th) {
                        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                        throw th;
                    }
                } else {
                    permissions = this.mRegistry.getPermissionTrees();
                }
                for (com.android.server.pm.permission.Permission permission : permissions) {
                    arrayList.add(new com.android.server.pm.permission.LegacyPermission(permission.getPermissionInfo(), permission.getType(), 0, libcore.util.EmptyArray.INT));
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            if (i == 0) {
                legacyPermissionSettings.replacePermissions(arrayList);
            } else {
                legacyPermissionSettings.replacePermissionTrees(arrayList);
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.Nullable
    public java.lang.String getDefaultPermissionGrantFingerprint(int i) {
        if (this.mPackageManagerInt.isPermissionUpgradeNeeded(i)) {
            return null;
        }
        return android.os.Build.FINGERPRINT;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void setDefaultPermissionGrantFingerprint(@android.annotation.NonNull java.lang.String str, int i) {
    }

    private void onPackageAddedInternal(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.NonNull final com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, @android.annotation.Nullable final com.android.server.pm.pkg.AndroidPackage androidPackage2) {
        final java.util.List<java.lang.String> addAllPermissionsInternal;
        if (!androidPackage.getAdoptPermissions().isEmpty()) {
            for (int size = androidPackage.getAdoptPermissions().size() - 1; size >= 0; size--) {
                java.lang.String str = (java.lang.String) androidPackage.getAdoptPermissions().get(size);
                if (canAdoptPermissionsInternal(str, androidPackage)) {
                    android.util.Slog.i(TAG, "Adopting permissions from " + str + " to " + androidPackage.getPackageName());
                    com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
                    com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
                    synchronized (packageManagerTracedLock) {
                        try {
                            this.mRegistry.transferPermissions(str, androidPackage.getPackageName());
                        } catch (java.lang.Throwable th) {
                            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                            throw th;
                        }
                    }
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                }
            }
        }
        if (z) {
            android.util.Slog.w(TAG, "Permission groups from package " + androidPackage.getPackageName() + " ignored: instant apps cannot define new permission groups.");
        } else {
            addAllPermissionGroupsInternal(androidPackage);
        }
        if (z) {
            android.util.Slog.w(TAG, "Permissions from package " + androidPackage.getPackageName() + " ignored: instant apps cannot define new permissions.");
            addAllPermissionsInternal = null;
        } else {
            addAllPermissionsInternal = addAllPermissionsInternal(packageState, androidPackage);
        }
        final boolean z2 = androidPackage2 != null;
        final boolean z3 = !com.android.internal.util.CollectionUtils.isEmpty(addAllPermissionsInternal);
        if (z2 || z3) {
            android.os.AsyncTask.execute(new java.lang.Runnable() { // from class: com.android.server.pm.permission.PermissionManagerServiceImpl$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.permission.PermissionManagerServiceImpl.this.lambda$onPackageAddedInternal$17(z2, androidPackage, androidPackage2, z3, addAllPermissionsInternal);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPackageAddedInternal$17(boolean z, com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.pm.pkg.AndroidPackage androidPackage2, boolean z2, java.util.List list) {
        if (z) {
            revokeRuntimePermissionsIfGroupChangedInternal(androidPackage, androidPackage2);
            revokeStoragePermissionsIfScopeExpandedInternal(androidPackage, androidPackage2);
            revokeSystemAlertWindowIfUpgradedPast23(androidPackage, androidPackage2);
        }
        if (z2) {
            revokeRuntimePermissionsIfPermissionDefinitionChangedInternal(list);
        }
    }

    private boolean canAdoptPermissionsInternal(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return false;
        }
        if (!packageStateInternal.isSystem()) {
            android.util.Slog.w(TAG, "Unable to update from " + packageStateInternal.getPackageName() + " to " + androidPackage.getPackageName() + ": old package not in system partition");
            return false;
        }
        if (this.mPackageManagerInt.getPackage(packageStateInternal.getPackageName()) != null) {
            android.util.Slog.w(TAG, "Unable to update from " + packageStateInternal.getPackageName() + " to " + androidPackage.getPackageName() + ": old package still exists");
            return false;
        }
        return true;
    }

    private boolean isEffectivelyGranted(com.android.server.pm.permission.PermissionState permissionState) {
        int flags = permissionState.getFlags();
        if ((flags & 16) != 0) {
            return true;
        }
        if ((flags & 4) != 0) {
            return (flags & 8) == 0 && permissionState.isGranted();
        }
        if ((flags & 65608) != 0) {
            return false;
        }
        return permissionState.isGranted();
    }

    private android.util.Pair<java.lang.Boolean, java.lang.Integer> mergePermissionState(int i, com.android.server.pm.permission.PermissionState permissionState, com.android.server.pm.permission.PermissionState permissionState2) {
        int i2;
        int flags = permissionState2.getFlags();
        boolean isEffectivelyGranted = isEffectivelyGranted(permissionState2);
        int flags2 = permissionState.getFlags();
        boolean isEffectivelyGranted2 = isEffectivelyGranted(permissionState);
        int i3 = flags | flags2;
        int i4 = (524291 & flags) | 0 | (i3 & 14336);
        if ((i4 & 14336) == 0) {
            i4 |= 16384;
        }
        int i5 = i3 & 32820;
        int i6 = i4 | i5;
        if (i5 == 0) {
            i6 |= i3 & 128;
        }
        if ((i6 & 20) == 0) {
            if ((557091 & i6) == 0 && NOTIFICATION_PERMISSIONS.contains(permissionState.getName())) {
                i6 |= i3 & 64;
            } else if ((32820 & i6) == 0) {
                i6 |= flags & 64;
            }
        }
        boolean z = true;
        if ((i6 & 16) != 0) {
            r5 = true;
        } else {
            if ((flags & 4) == 0) {
                if ((flags2 & 4) != 0) {
                    r5 = isEffectivelyGranted || isEffectivelyGranted2;
                    if (isEffectivelyGranted != isEffectivelyGranted2) {
                        i6 &= -5;
                    }
                } else if ((flags & 32800) == 0) {
                    if ((32800 & flags2) != 0) {
                        if (isEffectivelyGranted || isEffectivelyGranted2) {
                            r5 = true;
                        }
                    } else if ((flags & 128) == 0 && (flags2 & 128) != 0) {
                        r5 = isEffectivelyGranted || isEffectivelyGranted2;
                        if (isEffectivelyGranted) {
                            i6 &= -129;
                        }
                    }
                }
            }
            r5 = isEffectivelyGranted;
        }
        if (!r5) {
            i2 = ((131072 & i3) | i6) & (-129);
        } else {
            i2 = i6 & (-65);
        }
        if (r5 != isEffectivelyGranted) {
            i2 &= -524292;
        }
        if (!r5 && isPermissionSplitFromNonRuntime(permissionState.getName(), this.mPackageManagerInt.getUidTargetSdkVersion(i))) {
            i2 |= 8;
        } else {
            z = r5;
        }
        return new android.util.Pair<>(java.lang.Boolean.valueOf(z), java.lang.Integer.valueOf(i2));
    }

    /* JADX WARN: Finally extract failed */
    private void handleAppIdMigration(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        com.android.server.pm.permission.UidPermissionState uidState;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPackageManagerInt.getPackageStateInternal(androidPackage.getPackageName());
        if (packageStateInternal.hasSharedUser()) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    for (int i2 : getAllUserIds()) {
                        com.android.server.pm.permission.UserPermissionState orCreateUserState = this.mState.getOrCreateUserState(i2);
                        com.android.server.pm.permission.UidPermissionState uidState2 = orCreateUserState.getUidState(i);
                        if (uidState2 != null) {
                            com.android.server.pm.permission.UidPermissionState uidState3 = orCreateUserState.getUidState(packageStateInternal.getAppId());
                            if (uidState3 == null) {
                                orCreateUserState.createUidStateWithExisting(packageStateInternal.getAppId(), uidState2);
                            } else {
                                java.util.List<com.android.server.pm.permission.PermissionState> permissionStates = uidState2.getPermissionStates();
                                int size = permissionStates.size();
                                for (int i3 = 0; i3 < size; i3++) {
                                    com.android.server.pm.permission.PermissionState permissionState = permissionStates.get(i3);
                                    com.android.server.pm.permission.PermissionState permissionState2 = uidState3.getPermissionState(permissionState.getName());
                                    if (permissionState2 != null) {
                                        android.util.Pair<java.lang.Boolean, java.lang.Integer> mergePermissionState = mergePermissionState(packageStateInternal.getAppId(), permissionState, permissionState2);
                                        uidState3.putPermissionState(permissionState.getPermission(), ((java.lang.Boolean) mergePermissionState.first).booleanValue(), ((java.lang.Integer) mergePermissionState.second).intValue());
                                    } else {
                                        uidState3.putPermissionState(permissionState.getPermission(), permissionState.isGranted(), permissionState.getFlags());
                                    }
                                }
                            }
                            orCreateUserState.removeUidState(i);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            return;
        }
        java.util.List<com.android.server.pm.pkg.AndroidPackage> packagesForAppId = this.mPackageManagerInt.getPackagesForAppId(i);
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock2) {
            try {
                for (int i4 : getAllUserIds()) {
                    com.android.server.pm.permission.UserPermissionState userState = this.mState.getUserState(i4);
                    if (userState != null && (uidState = userState.getUidState(i)) != null) {
                        userState.createUidStateWithExisting(packageStateInternal.getAppId(), uidState);
                        if (packagesForAppId.isEmpty()) {
                            removeUidStateAndResetPackageInstallPermissionsFixed(i, androidPackage.getPackageName(), i4);
                        } else {
                            revokeSharedUserPermissionsForLeavingPackageInternal(androidPackage, i, packagesForAppId, i4);
                        }
                    }
                }
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    private void onPackageInstalledInternal(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams packageInstalledParams, int[] iArr) {
        if (i != -1) {
            handleAppIdMigration(androidPackage, i);
        }
        updatePermissions(androidPackage.getPackageName(), androidPackage);
        for (int i2 : iArr) {
            addAllowlistedRestrictedPermissionsInternal(androidPackage, packageInstalledParams.getAllowlistedRestrictedPermissions(), 2, i2);
            grantRequestedPermissionsInternal(androidPackage, packageInstalledParams.getPermissionStates(), i2);
        }
    }

    private void addAllowlistedRestrictedPermissionsInternal(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull java.util.List<java.lang.String> list, int i, int i2) {
        java.util.List<java.lang.String> allowlistedRestrictedPermissionsInternal = getAllowlistedRestrictedPermissionsInternal(androidPackage, i, i2);
        if (allowlistedRestrictedPermissionsInternal != null) {
            android.util.ArraySet arraySet = new android.util.ArraySet(allowlistedRestrictedPermissionsInternal);
            arraySet.addAll(list);
            list = new java.util.ArrayList<>(arraySet);
        }
        setAllowlistedRestrictedPermissionsInternal(androidPackage, list, i, i2);
    }

    private void onPackageRemovedInternal(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        removeAllPermissionsInternal(androidPackage);
    }

    private void onPackageUninstalledInternal(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.AndroidPackage> list, int[] iArr) {
        int i2 = 0;
        if (packageState.isSystem() && androidPackage != null && this.mPackageManagerInt.getPackage(str) != null) {
            int length = iArr.length;
            while (i2 < length) {
                resetRuntimePermissionsInternal(androidPackage, iArr[i2]);
                i2++;
            }
            return;
        }
        updatePermissions(str, null);
        int length2 = iArr.length;
        while (i2 < length2) {
            int i3 = iArr[i2];
            if (list.isEmpty()) {
                removeUidStateAndResetPackageInstallPermissionsFixed(i, str, i3);
            } else {
                revokeSharedUserPermissionsForLeavingPackageInternal(androidPackage, i, list, i3);
            }
            i2++;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.permission.LegacyPermission> getLegacyPermissions() {
        java.util.ArrayList arrayList;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                arrayList = new java.util.ArrayList();
                for (com.android.server.pm.permission.Permission permission : this.mRegistry.getPermissions()) {
                    arrayList.add(new com.android.server.pm.permission.LegacyPermission(permission.getPermissionInfo(), permission.getType(), permission.getUid(), permission.getRawGids()));
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getAllAppOpPermissionPackages() {
        android.util.ArrayMap arrayMap;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> allAppOpPermissionPackages = this.mRegistry.getAllAppOpPermissionPackages();
                arrayMap = new android.util.ArrayMap();
                int size = allAppOpPermissionPackages.size();
                for (int i = 0; i < size; i++) {
                    arrayMap.put(allAppOpPermissionPackages.keyAt(i), new android.util.ArraySet((android.util.ArraySet) allAppOpPermissionPackages.valueAt(i)));
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return arrayMap;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public com.android.server.pm.permission.LegacyPermissionState getLegacyPermissionState(int i) {
        com.android.server.pm.permission.LegacyPermissionState legacyPermissionState = new com.android.server.pm.permission.LegacyPermissionState();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                for (int i2 : this.mState.getUserIds()) {
                    com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(i, i2);
                    if (uidStateLocked == null) {
                        android.util.Slog.e(TAG, "Missing permissions state for app ID " + i + " and user ID " + i2);
                    } else {
                        java.util.List<com.android.server.pm.permission.PermissionState> permissionStates = uidStateLocked.getPermissionStates();
                        int size = permissionStates.size();
                        for (int i3 = 0; i3 < size; i3++) {
                            com.android.server.pm.permission.PermissionState permissionState = permissionStates.get(i3);
                            legacyPermissionState.putPermissionState(new com.android.server.pm.permission.LegacyPermissionState.PermissionState(permissionState.getName(), permissionState.getPermission().isRuntime(), permissionState.isGranted(), permissionState.getFlags()), i2);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return legacyPermissionState;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public int[] getGidsForUid(int i) {
        int appId = android.os.UserHandle.getAppId(i);
        int userId = android.os.UserHandle.getUserId(i);
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                com.android.server.pm.permission.UidPermissionState uidStateLocked = getUidStateLocked(appId, userId);
                if (uidStateLocked == null) {
                    android.util.Slog.e(TAG, "Missing permissions state for app ID " + appId + " and user ID " + userId);
                    int[] iArr = EMPTY_INT_ARRAY;
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    return iArr;
                }
                int[] computeGids = uidStateLocked.computeGids(this.mGlobalGids, userId);
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                return computeGids;
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean isPermissionsReviewRequired(@android.annotation.NonNull java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
        return isPermissionsReviewRequiredInternal(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.Set<java.lang.String> getInstalledPermissions(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                for (com.android.server.pm.permission.Permission permission : this.mRegistry.getPermissions()) {
                    if (java.util.Objects.equals(permission.getPackageName(), str)) {
                        arraySet.add(permission.getName());
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return arraySet;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.Set<java.lang.String> getGrantedPermissions(@android.annotation.NonNull java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
        com.android.internal.util.Preconditions.checkArgumentNonNegative(i, "userId");
        return getGrantedPermissionsInternal(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public int[] getPermissionGids(@android.annotation.NonNull java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str, "permissionName");
        com.android.internal.util.Preconditions.checkArgumentNonNegative(i, "userId");
        return getPermissionGidsInternal(str, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.lang.String[] getAppOpPermissionPackages(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str, "permissionName");
        return getAppOpPermissionPackagesInternal(str);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onStorageVolumeMounted(@android.annotation.Nullable java.lang.String str, boolean z) {
        updateAllPermissions(str, z);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void resetRuntimePermissions(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        java.util.Objects.requireNonNull(androidPackage, "pkg");
        com.android.internal.util.Preconditions.checkArgumentNonNegative(i, "userId");
        resetRuntimePermissionsInternal(androidPackage, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void resetRuntimePermissionsForUser(int i) {
        com.android.internal.util.Preconditions.checkArgumentNonNegative(i, "userId");
        resetRuntimePermissionsInternal(null, i);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public com.android.server.pm.permission.Permission getPermissionTEMP(java.lang.String str) {
        com.android.server.pm.permission.Permission permission;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                permission = this.mRegistry.getPermission(str);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return permission;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtection(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                for (com.android.server.pm.permission.Permission permission : this.mRegistry.getPermissions()) {
                    if (permission.getProtection() == i) {
                        arrayList.add(permission.generatePermissionInfo(0));
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @android.annotation.NonNull
    public java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtectionFlags(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                for (com.android.server.pm.permission.Permission permission : this.mRegistry.getPermissions()) {
                    if ((permission.getProtectionFlags() & i) == i) {
                        arrayList.add(permission.generatePermissionInfo(0));
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onUserCreated(int i) {
        com.android.internal.util.Preconditions.checkArgumentNonNegative(i, "userId");
        updateAllPermissions(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, true);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageAdded(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, boolean z, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage) {
        java.util.Objects.requireNonNull(packageState);
        com.android.server.pm.pkg.AndroidPackage androidPackage2 = packageState.getAndroidPackage();
        java.util.Objects.requireNonNull(androidPackage2);
        onPackageAddedInternal(packageState, androidPackage2, z, androidPackage);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageInstalled(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams packageInstalledParams, int i2) {
        java.util.Objects.requireNonNull(androidPackage, "pkg");
        java.util.Objects.requireNonNull(packageInstalledParams, "params");
        com.android.internal.util.Preconditions.checkArgument(i2 >= 0 || i2 == -1, "userId");
        onPackageInstalledInternal(androidPackage, i, packageInstalledParams, i2 == -1 ? getAllUserIds() : new int[]{i2});
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageRemoved(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        java.util.Objects.requireNonNull(androidPackage);
        onPackageRemovedInternal(androidPackage);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageUninstalled(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.AndroidPackage> list, int i2) {
        java.util.Objects.requireNonNull(packageState, "packageState");
        java.util.Objects.requireNonNull(str, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
        java.util.Objects.requireNonNull(list, "sharedUserPkgs");
        com.android.internal.util.Preconditions.checkArgument(i2 >= 0 || i2 == -1, "userId");
        onPackageUninstalledInternal(str, i, packageState, androidPackage, list, i2 == -1 ? getAllUserIds() : new int[]{i2});
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PermissionCallback {
        private PermissionCallback() {
        }

        public void onGidsChanged(int i, int i2) {
        }

        public void onPermissionChanged() {
        }

        public void onPermissionGranted(int i, int i2) {
        }

        public void onInstallPermissionGranted() {
        }

        public void onPermissionRevoked(int i, int i2, java.lang.String str) {
            onPermissionRevoked(i, i2, str, false, null);
        }

        public void onPermissionRevoked(int i, int i2, java.lang.String str, boolean z, @android.annotation.Nullable java.lang.String str2) {
        }

        public void onInstallPermissionRevoked() {
        }

        public void onPermissionUpdated(int[] iArr, boolean z, int i) {
        }

        public void onPermissionRemoved() {
        }

        public void onInstallPermissionUpdated() {
        }
    }

    private static final class OnPermissionChangeListeners extends android.os.Handler {
        private static final int MSG_ON_PERMISSIONS_CHANGED = 1;
        private final android.os.RemoteCallbackList<android.permission.IOnPermissionsChangeListener> mPermissionListeners;

        OnPermissionChangeListeners(android.os.Looper looper) {
            super(looper);
            this.mPermissionListeners = new android.os.RemoteCallbackList<>();
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    handleOnPermissionsChanged(message.arg1);
                    break;
            }
        }

        public void addListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) {
            this.mPermissionListeners.register(iOnPermissionsChangeListener);
        }

        public void removeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) {
            this.mPermissionListeners.unregister(iOnPermissionsChangeListener);
        }

        public void onPermissionsChanged(int i) {
            if (this.mPermissionListeners.getRegisteredCallbackCount() > 0) {
                obtainMessage(1, i, 0).sendToTarget();
            }
        }

        private void handleOnPermissionsChanged(int i) {
            int beginBroadcast = this.mPermissionListeners.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    try {
                        this.mPermissionListeners.getBroadcastItem(i2).onPermissionsChanged(i, "default:0");
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(com.android.server.pm.permission.PermissionManagerServiceImpl.TAG, "Permission listener is dead", e);
                    }
                } finally {
                    this.mPermissionListeners.finishBroadcast();
                }
            }
        }
    }
}
