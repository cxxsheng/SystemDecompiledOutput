package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public class PermissionManagerService extends android.permission.IPermissionManager.Stub {
    private static final java.lang.String LOG_TAG = com.android.server.pm.permission.PermissionManagerService.class.getSimpleName();
    private static final java.util.concurrent.ConcurrentHashMap<android.os.IBinder, com.android.server.pm.permission.PermissionManagerService.RegisteredAttribution> sRunningAttributionSources = new java.util.concurrent.ConcurrentHashMap<>();
    private final android.app.AppOpsManager mAppOpsManager;

    @android.annotation.NonNull
    private final com.android.server.pm.permission.PermissionManagerService.AttributionSourceRegistry mAttributionSourceRegistry;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate mCheckPermissionDelegate;
    private final android.content.Context mContext;

    @android.annotation.Nullable
    private com.android.server.pm.permission.PermissionManagerServiceInternal.HotwordDetectionServiceProvider mHotwordDetectionServiceProvider;
    private final java.lang.Object mLock = new java.lang.Object();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.pm.permission.OneTimePermissionUserManager> mOneTimePermissionUserManagers = new android.util.SparseArray<>();
    private final android.content.pm.PackageManagerInternal mPackageManagerInt;
    private final com.android.server.pm.permission.PermissionManagerServiceInterface mPermissionManagerServiceImpl;

    @android.annotation.Nullable
    private com.android.server.companion.virtual.VirtualDeviceManagerInternal mVirtualDeviceManagerInternal;

    private interface CheckPermissionDelegate {
        int checkPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, java.lang.String str3, int i, @android.annotation.NonNull com.android.internal.util.function.QuadFunction<java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer> quadFunction);

        int checkUidPermission(int i, @android.annotation.NonNull java.lang.String str, java.lang.String str2, com.android.internal.util.function.TriFunction<java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer> triFunction);

        java.util.List<java.lang.String> getDelegatedPermissionNames();

        int getDelegatedUid();
    }

    PermissionManagerService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, android.content.pm.FeatureInfo> arrayMap) {
        android.content.pm.PackageManager.invalidatePackageInfoCache();
        android.permission.PermissionManager.disablePackageNamePermissionCache();
        this.mContext = context;
        this.mPackageManagerInt = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mAppOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        this.mVirtualDeviceManagerInternal = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.LocalServices.getService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);
        this.mAttributionSourceRegistry = new com.android.server.pm.permission.PermissionManagerService.AttributionSourceRegistry(context);
        com.android.server.pm.permission.PermissionManagerService.PermissionManagerServiceInternalImpl permissionManagerServiceInternalImpl = new com.android.server.pm.permission.PermissionManagerService.PermissionManagerServiceInternalImpl();
        com.android.server.LocalServices.addService(com.android.server.pm.permission.PermissionManagerServiceInternal.class, permissionManagerServiceInternalImpl);
        com.android.server.LocalServices.addService(android.permission.PermissionManagerInternal.class, permissionManagerServiceInternalImpl);
        if (android.permission.PermissionManager.USE_ACCESS_CHECKING_SERVICE) {
            this.mPermissionManagerServiceImpl = (com.android.server.pm.permission.PermissionManagerServiceInterface) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInterface.class);
        } else {
            this.mPermissionManagerServiceImpl = new com.android.server.pm.permission.PermissionManagerServiceImpl(context, arrayMap);
        }
    }

    @android.annotation.NonNull
    public static com.android.server.pm.permission.PermissionManagerServiceInternal create(@android.annotation.NonNull android.content.Context context, android.util.ArrayMap<java.lang.String, android.content.pm.FeatureInfo> arrayMap) {
        com.android.server.pm.permission.PermissionManagerServiceInternal permissionManagerServiceInternal = (com.android.server.pm.permission.PermissionManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInternal.class);
        if (permissionManagerServiceInternal != null) {
            return permissionManagerServiceInternal;
        }
        if (((com.android.server.pm.permission.PermissionManagerService) android.os.ServiceManager.getService("permissionmgr")) == null) {
            android.os.ServiceManager.addService("permissionmgr", new com.android.server.pm.permission.PermissionManagerService(context, arrayMap));
            android.os.ServiceManager.addService("permission_checker", new com.android.server.pm.permission.PermissionManagerService.PermissionCheckerService(context));
        }
        return (com.android.server.pm.permission.PermissionManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInternal.class);
    }

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

    public int checkPermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate checkPermissionDelegate;
        if (str == null || str2 == null) {
            return -1;
        }
        synchronized (this.mLock) {
            checkPermissionDelegate = this.mCheckPermissionDelegate;
        }
        if (checkPermissionDelegate == null) {
            return this.mPermissionManagerServiceImpl.checkPermission(str, str2, str3, i);
        }
        final com.android.server.pm.permission.PermissionManagerServiceInterface permissionManagerServiceInterface = this.mPermissionManagerServiceImpl;
        java.util.Objects.requireNonNull(permissionManagerServiceInterface);
        return checkPermissionDelegate.checkPermission(str, str2, str3, i, new com.android.internal.util.function.QuadFunction() { // from class: com.android.server.pm.permission.PermissionManagerService$$ExternalSyntheticLambda0
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                return java.lang.Integer.valueOf(com.android.server.pm.permission.PermissionManagerServiceInterface.this.checkPermission((java.lang.String) obj, (java.lang.String) obj2, (java.lang.String) obj3, ((java.lang.Integer) obj4).intValue()));
            }
        });
    }

    public int checkUidPermission(int i, java.lang.String str, int i2) {
        com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate checkPermissionDelegate;
        if (str == null) {
            return -1;
        }
        java.lang.String persistentDeviceId = getPersistentDeviceId(i2);
        synchronized (this.mLock) {
            checkPermissionDelegate = this.mCheckPermissionDelegate;
        }
        if (checkPermissionDelegate == null) {
            return this.mPermissionManagerServiceImpl.checkUidPermission(i, str, persistentDeviceId);
        }
        final com.android.server.pm.permission.PermissionManagerServiceInterface permissionManagerServiceInterface = this.mPermissionManagerServiceImpl;
        java.util.Objects.requireNonNull(permissionManagerServiceInterface);
        return checkPermissionDelegate.checkUidPermission(i, str, persistentDeviceId, new com.android.internal.util.function.TriFunction() { // from class: com.android.server.pm.permission.PermissionManagerService$$ExternalSyntheticLambda1
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                return java.lang.Integer.valueOf(com.android.server.pm.permission.PermissionManagerServiceInterface.this.checkUidPermission(((java.lang.Integer) obj).intValue(), (java.lang.String) obj2, (java.lang.String) obj3));
            }
        });
    }

    private java.lang.String getPersistentDeviceId(int i) {
        if (i == 0) {
            return "default:0";
        }
        if (this.mVirtualDeviceManagerInternal == null) {
            this.mVirtualDeviceManagerInternal = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.LocalServices.getService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);
        }
        return this.mVirtualDeviceManagerInternal.getPersistentIdForDevice(i);
    }

    public java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> getAllPermissionStates(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        return this.mPermissionManagerServiceImpl.getAllPermissionStates(str, str2, i);
    }

    public boolean setAutoRevokeExempted(@android.annotation.NonNull java.lang.String str, boolean z, int i) {
        java.util.Objects.requireNonNull(str);
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (!checkAutoRevokeAccess(androidPackage, android.os.Binder.getCallingUid())) {
            return false;
        }
        return setAutoRevokeExemptedInternal(androidPackage, z, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setAutoRevokeExemptedInternal(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, int i) {
        int uid = android.os.UserHandle.getUid(i, androidPackage.getUid());
        if (this.mAppOpsManager.checkOpNoThrow(98, new android.content.AttributionSource(uid, androidPackage.getPackageName(), null)) != 0) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mAppOpsManager.setMode(97, uid, androidPackage.getPackageName(), z ? 1 : 0);
            return true;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean checkAutoRevokeAccess(com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.WHITELIST_AUTO_REVOKE_PERMISSIONS") == 0;
        boolean isCallerInstallerOfRecord = this.mPackageManagerInt.isCallerInstallerOfRecord(androidPackage, i);
        if (z || isCallerInstallerOfRecord) {
            return (androidPackage == null || this.mPackageManagerInt.filterAppAccess(androidPackage, i, android.os.UserHandle.getUserId(i))) ? false : true;
        }
        throw new java.lang.SecurityException("Caller must either hold android.permission.WHITELIST_AUTO_REVOKE_PERMISSIONS or be the installer on record");
    }

    public boolean isAutoRevokeExempted(@android.annotation.NonNull java.lang.String str, int i) {
        java.util.Objects.requireNonNull(str);
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackageManagerInt.getPackage(str);
        if (!checkAutoRevokeAccess(androidPackage, android.os.Binder.getCallingUid())) {
            return false;
        }
        int uid = android.os.UserHandle.getUid(i, androidPackage.getUid());
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mAppOpsManager.checkOpNoThrow(97, new android.content.AttributionSource(uid, str, null)) == 1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startShellPermissionIdentityDelegationInternal(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.util.List<java.lang.String> list) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate checkPermissionDelegate = this.mCheckPermissionDelegate;
                if (checkPermissionDelegate != null && checkPermissionDelegate.getDelegatedUid() != i) {
                    throw new java.lang.SecurityException("Shell can delegate permissions only to one UID at a time");
                }
                setCheckPermissionDelegateLocked(new com.android.server.pm.permission.PermissionManagerService.ShellDelegate(i, str, list));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopShellPermissionIdentityDelegationInternal() {
        synchronized (this.mLock) {
            setCheckPermissionDelegateLocked(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public java.util.List<java.lang.String> getDelegatedShellPermissionsInternal() {
        synchronized (this.mLock) {
            try {
                if (this.mCheckPermissionDelegate == null) {
                    return java.util.Collections.EMPTY_LIST;
                }
                return this.mCheckPermissionDelegate.getDelegatedPermissionNames();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void setCheckPermissionDelegateLocked(@android.annotation.Nullable com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate checkPermissionDelegate) {
        if (checkPermissionDelegate != null || this.mCheckPermissionDelegate != null) {
            android.content.pm.PackageManager.invalidatePackageInfoCache();
        }
        this.mCheckPermissionDelegate = checkPermissionDelegate;
    }

    @android.annotation.NonNull
    private com.android.server.pm.permission.OneTimePermissionUserManager getOneTimePermissionUserManager(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.pm.permission.OneTimePermissionUserManager oneTimePermissionUserManager = this.mOneTimePermissionUserManagers.get(i);
                if (oneTimePermissionUserManager != null) {
                    return oneTimePermissionUserManager;
                }
                com.android.server.pm.permission.OneTimePermissionUserManager oneTimePermissionUserManager2 = new com.android.server.pm.permission.OneTimePermissionUserManager(this.mContext.createContextAsUser(android.os.UserHandle.of(i), 0));
                synchronized (this.mLock) {
                    try {
                        com.android.server.pm.permission.OneTimePermissionUserManager oneTimePermissionUserManager3 = this.mOneTimePermissionUserManagers.get(i);
                        if (oneTimePermissionUserManager3 != null) {
                            return oneTimePermissionUserManager3;
                        }
                        this.mOneTimePermissionUserManagers.put(i, oneTimePermissionUserManager2);
                        oneTimePermissionUserManager2.registerUninstallListener();
                        return oneTimePermissionUserManager2;
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_ONE_TIME_PERMISSION_SESSIONS")
    public void startOneTimePermissionSession(java.lang.String str, int i, int i2, long j, long j2) {
        startOneTimePermissionSession_enforcePermission();
        java.util.Objects.requireNonNull(str);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            getOneTimePermissionUserManager(i2).startPackageOneTimeSession(str, i, j, j2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MANAGE_ONE_TIME_PERMISSION_SESSIONS")
    public void stopOneTimePermissionSession(java.lang.String str, int i) {
        super.stopOneTimePermissionSession_enforcePermission();
        java.util.Objects.requireNonNull(str);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            getOneTimePermissionUserManager(i).stopPackageOneTimeSession(str);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.os.IBinder registerAttributionSource(@android.annotation.NonNull android.content.AttributionSourceState attributionSourceState) {
        if (android.permission.flags.Flags.serverSideAttributionRegistration()) {
            android.os.Binder binder = new android.os.Binder();
            this.mAttributionSourceRegistry.registerAttributionSource(new android.content.AttributionSource(attributionSourceState).withToken(binder));
            return binder;
        }
        this.mAttributionSourceRegistry.registerAttributionSource(new android.content.AttributionSource(attributionSourceState));
        return attributionSourceState.token;
    }

    public boolean isRegisteredAttributionSource(@android.annotation.NonNull android.content.AttributionSourceState attributionSourceState) {
        return this.mAttributionSourceRegistry.isRegisteredAttributionSource(new android.content.AttributionSource(attributionSourceState));
    }

    public java.util.List<java.lang.String> getAutoRevokeExemptionRequestedPackages(int i) {
        return getPackagesWithAutoRevokePolicy(1, i);
    }

    public java.util.List<java.lang.String> getAutoRevokeExemptionGrantedPackages(int i) {
        return getPackagesWithAutoRevokePolicy(2, i);
    }

    @android.annotation.NonNull
    private java.util.List<java.lang.String> getPackagesWithAutoRevokePolicy(final int i, int i2) {
        this.mContext.enforceCallingPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY", "Must hold android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY");
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        this.mPackageManagerInt.forEachInstalledPackage(new java.util.function.Consumer() { // from class: com.android.server.pm.permission.PermissionManagerService$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.permission.PermissionManagerService.lambda$getPackagesWithAutoRevokePolicy$0(i, arrayList, (com.android.server.pm.pkg.AndroidPackage) obj);
            }
        }, i2);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getPackagesWithAutoRevokePolicy$0(int i, java.util.List list, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (androidPackage.getAutoRevokePermissions() == i) {
            list.add(androidPackage.getPackageName());
        }
    }

    public android.content.pm.ParceledListSlice<android.content.pm.PermissionGroupInfo> getAllPermissionGroups(int i) {
        return new android.content.pm.ParceledListSlice<>(this.mPermissionManagerServiceImpl.getAllPermissionGroups(i));
    }

    public android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) {
        return this.mPermissionManagerServiceImpl.getPermissionGroupInfo(str, i);
    }

    public android.content.pm.PermissionInfo getPermissionInfo(java.lang.String str, java.lang.String str2, int i) {
        return this.mPermissionManagerServiceImpl.getPermissionInfo(str, i, str2);
    }

    public android.content.pm.ParceledListSlice<android.content.pm.PermissionInfo> queryPermissionsByGroup(java.lang.String str, int i) {
        java.util.List<android.content.pm.PermissionInfo> queryPermissionsByGroup = this.mPermissionManagerServiceImpl.queryPermissionsByGroup(str, i);
        if (queryPermissionsByGroup == null) {
            return null;
        }
        return new android.content.pm.ParceledListSlice<>(queryPermissionsByGroup);
    }

    public boolean addPermission(android.content.pm.PermissionInfo permissionInfo, boolean z) {
        return this.mPermissionManagerServiceImpl.addPermission(permissionInfo, z);
    }

    public void removePermission(java.lang.String str) {
        this.mPermissionManagerServiceImpl.removePermission(str);
    }

    public int getPermissionFlags(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        return this.mPermissionManagerServiceImpl.getPermissionFlags(str, str2, str3, i);
    }

    public void updatePermissionFlags(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, java.lang.String str3, int i3) {
        this.mPermissionManagerServiceImpl.updatePermissionFlags(str, str2, i, i2, z, str3, i3);
    }

    public void updatePermissionFlagsForAllApps(int i, int i2, int i3) {
        this.mPermissionManagerServiceImpl.updatePermissionFlagsForAllApps(i, i2, i3);
    }

    public void addOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        this.mPermissionManagerServiceImpl.addOnPermissionsChangeListener(iOnPermissionsChangeListener);
    }

    public void removeOnPermissionsChangeListener(android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener) {
        this.mPermissionManagerServiceImpl.removeOnPermissionsChangeListener(iOnPermissionsChangeListener);
    }

    public java.util.List<java.lang.String> getAllowlistedRestrictedPermissions(java.lang.String str, int i, int i2) {
        return this.mPermissionManagerServiceImpl.getAllowlistedRestrictedPermissions(str, i, i2);
    }

    public boolean addAllowlistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i, int i2) {
        return this.mPermissionManagerServiceImpl.addAllowlistedRestrictedPermission(str, str2, i, i2);
    }

    public boolean removeAllowlistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i, int i2) {
        return this.mPermissionManagerServiceImpl.removeAllowlistedRestrictedPermission(str, str2, i, i2);
    }

    public void grantRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        this.mPermissionManagerServiceImpl.grantRuntimePermission(str, str2, str3, i);
    }

    public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4) {
        this.mPermissionManagerServiceImpl.revokeRuntimePermission(str, str2, str3, i, str4);
    }

    public void revokePostNotificationPermissionWithoutKillForTest(java.lang.String str, int i) {
        this.mPermissionManagerServiceImpl.revokePostNotificationPermissionWithoutKillForTest(str, i);
    }

    public boolean shouldShowRequestPermissionRationale(java.lang.String str, java.lang.String str2, int i, int i2) {
        return this.mPermissionManagerServiceImpl.shouldShowRequestPermissionRationale(str, str2, getPersistentDeviceId(i), i2);
    }

    public boolean isPermissionRevokedByPolicy(java.lang.String str, java.lang.String str2, int i, int i2) {
        return this.mPermissionManagerServiceImpl.isPermissionRevokedByPolicy(str, str2, getPersistentDeviceId(i), i2);
    }

    public java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> getSplitPermissions() {
        return this.mPermissionManagerServiceImpl.getSplitPermissions();
    }

    private class PermissionManagerServiceInternalImpl implements com.android.server.pm.permission.PermissionManagerServiceInternal {
        private PermissionManagerServiceInternalImpl() {
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public int checkPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, int i) {
            return com.android.server.pm.permission.PermissionManagerService.this.checkPermission(str, str2, str3, i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public int checkUidPermission(int i, @android.annotation.NonNull java.lang.String str, int i2) {
            return com.android.server.pm.permission.PermissionManagerService.this.checkUidPermission(i, str, i2);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void startShellPermissionIdentityDelegation(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.util.List<java.lang.String> list) {
            java.util.Objects.requireNonNull(str, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
            com.android.server.pm.permission.PermissionManagerService.this.startShellPermissionIdentityDelegationInternal(i, str, list);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void stopShellPermissionIdentityDelegation() {
            com.android.server.pm.permission.PermissionManagerService.this.stopShellPermissionIdentityDelegationInternal();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        @android.annotation.NonNull
        public java.util.List<java.lang.String> getDelegatedShellPermissions() {
            return com.android.server.pm.permission.PermissionManagerService.this.getDelegatedShellPermissionsInternal();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void setHotwordDetectionServiceProvider(com.android.server.pm.permission.PermissionManagerServiceInternal.HotwordDetectionServiceProvider hotwordDetectionServiceProvider) {
            com.android.server.pm.permission.PermissionManagerService.this.mHotwordDetectionServiceProvider = hotwordDetectionServiceProvider;
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public com.android.server.pm.permission.PermissionManagerServiceInternal.HotwordDetectionServiceProvider getHotwordDetectionServiceProvider() {
            return com.android.server.pm.permission.PermissionManagerService.this.mHotwordDetectionServiceProvider;
        }

        @Override // com.android.server.pm.permission.LegacyPermissionDataProvider
        @android.annotation.NonNull
        public int[] getGidsForUid(int i) {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.getGidsForUid(i);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionDataProvider
        @android.annotation.NonNull
        public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getAllAppOpPermissionPackages() {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.getAllAppOpPermissionPackages();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onUserCreated(int i) {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.onUserCreated(i);
        }

        @Override // com.android.server.pm.permission.LegacyPermissionDataProvider
        @android.annotation.NonNull
        public java.util.List<com.android.server.pm.permission.LegacyPermission> getLegacyPermissions() {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.getLegacyPermissions();
        }

        @Override // com.android.server.pm.permission.LegacyPermissionDataProvider
        @android.annotation.NonNull
        public com.android.server.pm.permission.LegacyPermissionState getLegacyPermissionState(int i) {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.getLegacyPermissionState(i);
        }

        @android.annotation.Nullable
        public byte[] backupRuntimePermissions(int i) {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.backupRuntimePermissions(i);
        }

        public void restoreRuntimePermissions(@android.annotation.NonNull byte[] bArr, int i) {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.restoreRuntimePermissions(bArr, i);
        }

        public void restoreDelayedRuntimePermissions(@android.annotation.NonNull java.lang.String str, int i) {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.restoreDelayedRuntimePermissions(str, i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void readLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings) {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.readLegacyPermissionsTEMP(legacyPermissionSettings);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void writeLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings) {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.writeLegacyPermissionsTEMP(legacyPermissionSettings);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        @android.annotation.Nullable
        public java.lang.String getDefaultPermissionGrantFingerprint(int i) {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.getDefaultPermissionGrantFingerprint(i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void setDefaultPermissionGrantFingerprint(@android.annotation.NonNull java.lang.String str, int i) {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.setDefaultPermissionGrantFingerprint(str, i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onPackageAdded(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, boolean z, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage) {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.onPackageAdded(packageState, z, androidPackage);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onPackageInstalled(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams packageInstalledParams, int i2) {
            java.util.Objects.requireNonNull(androidPackage, "pkg");
            java.util.Objects.requireNonNull(packageInstalledParams, "params");
            com.android.internal.util.Preconditions.checkArgument(i2 >= 0 || i2 == -1, "userId");
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.onPackageInstalled(androidPackage, i, packageInstalledParams, i2);
            for (int i3 : i2 == -1 ? com.android.server.pm.permission.PermissionManagerService.this.getAllUserIds() : new int[]{i2}) {
                int autoRevokePermissionsMode = packageInstalledParams.getAutoRevokePermissionsMode();
                if (autoRevokePermissionsMode == 0 || autoRevokePermissionsMode == 1) {
                    com.android.server.pm.permission.PermissionManagerService.this.setAutoRevokeExemptedInternal(androidPackage, autoRevokePermissionsMode == 1, i3);
                }
            }
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onPackageRemoved(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.onPackageRemoved(androidPackage);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onPackageUninstalled(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.AndroidPackage> list, int i2) {
            if (i2 != -1 && !com.android.internal.util.ArrayUtils.contains(com.android.server.pm.permission.PermissionManagerService.this.getAllUserIds(), i2)) {
                android.util.Slog.w(com.android.server.pm.permission.PermissionManagerService.LOG_TAG, "Skipping onPackageUninstalled() for non-existent user " + i2);
                return;
            }
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.onPackageUninstalled(str, i, packageState, androidPackage, list, i2);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onSystemReady() {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.onSystemReady();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public boolean isPermissionsReviewRequired(@android.annotation.NonNull java.lang.String str, int i) {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.isPermissionsReviewRequired(str, i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void readLegacyPermissionStateTEMP() {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.readLegacyPermissionStateTEMP();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal, com.android.server.pm.permission.LegacyPermissionDataProvider
        public void writeLegacyPermissionStateTEMP() {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.writeLegacyPermissionStateTEMP();
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onUserRemoved(int i) {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.onUserRemoved(i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        @android.annotation.NonNull
        public java.util.Set<java.lang.String> getInstalledPermissions(@android.annotation.NonNull java.lang.String str) {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.getInstalledPermissions(str);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        @android.annotation.NonNull
        public java.util.Set<java.lang.String> getGrantedPermissions(@android.annotation.NonNull java.lang.String str, int i) {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.getGrantedPermissions(str, i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        @android.annotation.NonNull
        public int[] getPermissionGids(@android.annotation.NonNull java.lang.String str, int i) {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.getPermissionGids(str, i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        @android.annotation.NonNull
        public java.lang.String[] getAppOpPermissionPackages(@android.annotation.NonNull java.lang.String str) {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.getAppOpPermissionPackages(str);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void onStorageVolumeMounted(@android.annotation.Nullable java.lang.String str, boolean z) {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.onStorageVolumeMounted(str, z);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void resetRuntimePermissions(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.resetRuntimePermissions(androidPackage, i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public void resetRuntimePermissionsForUser(int i) {
            com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.resetRuntimePermissionsForUser(i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        public com.android.server.pm.permission.Permission getPermissionTEMP(java.lang.String str) {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.getPermissionTEMP(str);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        @android.annotation.NonNull
        public java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtection(int i) {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.getAllPermissionsWithProtection(i);
        }

        @Override // com.android.server.pm.permission.PermissionManagerServiceInternal
        @android.annotation.NonNull
        public java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtectionFlags(int i) {
            return com.android.server.pm.permission.PermissionManagerService.this.mPermissionManagerServiceImpl.getAllPermissionsWithProtectionFlags(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getAllUserIds() {
        return com.android.server.pm.UserManagerService.getInstance().getUserIdsIncludingPreCreated();
    }

    private class ShellDelegate implements com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate {

        @android.annotation.NonNull
        private final java.lang.String mDelegatedPackageName;

        @android.annotation.Nullable
        private final java.util.List<java.lang.String> mDelegatedPermissionNames;
        private final int mDelegatedUid;

        public ShellDelegate(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.util.List<java.lang.String> list) {
            this.mDelegatedUid = i;
            this.mDelegatedPackageName = str;
            this.mDelegatedPermissionNames = list;
        }

        @Override // com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate
        public int getDelegatedUid() {
            return this.mDelegatedUid;
        }

        @Override // com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate
        public int checkPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, java.lang.String str3, int i, @android.annotation.NonNull com.android.internal.util.function.QuadFunction<java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer> quadFunction) {
            if (this.mDelegatedPackageName.equals(str) && isDelegatedPermission(str2)) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return ((java.lang.Integer) quadFunction.apply(com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME, str2, str3, java.lang.Integer.valueOf(i))).intValue();
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return ((java.lang.Integer) quadFunction.apply(str, str2, str3, java.lang.Integer.valueOf(i))).intValue();
        }

        @Override // com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate
        public int checkUidPermission(int i, @android.annotation.NonNull java.lang.String str, java.lang.String str2, @android.annotation.NonNull com.android.internal.util.function.TriFunction<java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer> triFunction) {
            if (i == this.mDelegatedUid && isDelegatedPermission(str)) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    return ((java.lang.Integer) triFunction.apply(2000, str, str2)).intValue();
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return ((java.lang.Integer) triFunction.apply(java.lang.Integer.valueOf(i), str, str2)).intValue();
        }

        @Override // com.android.server.pm.permission.PermissionManagerService.CheckPermissionDelegate
        public java.util.List<java.lang.String> getDelegatedPermissionNames() {
            if (this.mDelegatedPermissionNames == null) {
                return null;
            }
            return new java.util.ArrayList(this.mDelegatedPermissionNames);
        }

        private boolean isDelegatedPermission(@android.annotation.NonNull java.lang.String str) {
            return this.mDelegatedPermissionNames == null || this.mDelegatedPermissionNames.contains(str);
        }
    }

    private static final class AttributionSourceRegistry {
        private final android.content.Context mContext;
        private final java.lang.Object mLock = new java.lang.Object();
        private final java.util.WeakHashMap<android.os.IBinder, android.content.AttributionSource> mAttributions = new java.util.WeakHashMap<>();

        AttributionSourceRegistry(@android.annotation.NonNull android.content.Context context) {
            this.mContext = context;
        }

        public void registerAttributionSource(@android.annotation.NonNull android.content.AttributionSource attributionSource) {
            int resolveUid = resolveUid(android.os.Binder.getCallingUid());
            int resolveUid2 = resolveUid(attributionSource.getUid());
            if (resolveUid2 != resolveUid && this.mContext.checkPermission("android.permission.UPDATE_APP_OPS_STATS", -1, resolveUid) != 0) {
                throw new java.lang.SecurityException("Cannot register attribution source for uid:" + resolveUid2 + " from uid:" + resolveUid);
            }
            if (((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getPackageUid(attributionSource.getPackageName(), 0L, android.os.UserHandle.getUserId(resolveUid == 1000 ? resolveUid2 : resolveUid)) != resolveUid2) {
                throw new java.lang.SecurityException("Cannot register attribution source for package:" + attributionSource.getPackageName() + " from uid:" + resolveUid);
            }
            android.content.AttributionSource next = attributionSource.getNext();
            if (next != null && next.getNext() != null && !isRegisteredAttributionSource(next)) {
                throw new java.lang.SecurityException("Cannot register forged attribution source:" + attributionSource);
            }
            synchronized (this.mLock) {
                this.mAttributions.put(attributionSource.getToken(), attributionSource.withDefaultToken());
            }
        }

        public boolean isRegisteredAttributionSource(@android.annotation.NonNull android.content.AttributionSource attributionSource) {
            synchronized (this.mLock) {
                try {
                    android.content.AttributionSource attributionSource2 = this.mAttributions.get(attributionSource.getToken());
                    if (attributionSource2 == null) {
                        return false;
                    }
                    return attributionSource2.equalsExceptToken(attributionSource);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private int resolveUid(int i) {
            android.service.voice.VoiceInteractionManagerInternal voiceInteractionManagerInternal = (android.service.voice.VoiceInteractionManagerInternal) com.android.server.LocalServices.getService(android.service.voice.VoiceInteractionManagerInternal.class);
            if (voiceInteractionManagerInternal == null) {
                return i;
            }
            android.service.voice.VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity hotwordDetectionServiceIdentity = voiceInteractionManagerInternal.getHotwordDetectionServiceIdentity();
            if (hotwordDetectionServiceIdentity != null && i == hotwordDetectionServiceIdentity.getIsolatedUid()) {
                return hotwordDetectionServiceIdentity.getOwnerUid();
            }
            return i;
        }
    }

    private static final class PermissionCheckerService extends android.permission.IPermissionChecker.Stub {

        @android.annotation.NonNull
        private final android.content.Context mContext;

        @android.annotation.NonNull
        private final com.android.server.pm.permission.PermissionManagerServiceInternal mPermissionManagerServiceInternal = (com.android.server.pm.permission.PermissionManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInternal.class);
        private static final java.util.concurrent.ConcurrentHashMap<java.lang.String, android.content.pm.PermissionInfo> sPlatformPermissions = new java.util.concurrent.ConcurrentHashMap<>();
        private static final java.util.concurrent.atomic.AtomicInteger sAttributionChainIds = new java.util.concurrent.atomic.AtomicInteger(0);

        PermissionCheckerService(@android.annotation.NonNull android.content.Context context) {
            this.mContext = context;
        }

        public int checkPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.AttributionSourceState attributionSourceState, @android.annotation.Nullable java.lang.String str2, boolean z, boolean z2, boolean z3, int i) {
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(attributionSourceState);
            android.content.AttributionSource attributionSource = new android.content.AttributionSource(attributionSourceState);
            int checkPermission = checkPermission(this.mContext, this.mPermissionManagerServiceInternal, str, attributionSource, str2, z, z2, z3, i);
            if (z2 && checkPermission != 0 && checkPermission != 1) {
                if (i == -1) {
                    finishDataDelivery(android.app.AppOpsManager.permissionToOpCode(str), attributionSource.asState(), z3);
                } else {
                    finishDataDelivery(i, attributionSource.asState(), z3);
                }
            }
            return checkPermission;
        }

        public void finishDataDelivery(int i, @android.annotation.NonNull android.content.AttributionSourceState attributionSourceState, boolean z) {
            finishDataDelivery(this.mContext, i, attributionSourceState, z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x009c, code lost:
        
            if (r5 == null) goto L62;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x009e, code lost:
        
            r7 = (com.android.server.pm.permission.PermissionManagerService.RegisteredAttribution) com.android.server.pm.permission.PermissionManagerService.sRunningAttributionSources.remove(r5.getToken());
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00ac, code lost:
        
            if (r7 == null) goto L63;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00ae, code lost:
        
            r7.unregister();
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x00b1, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:?, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:?, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static void finishDataDelivery(android.content.Context context, int i, @android.annotation.NonNull android.content.AttributionSourceState attributionSourceState, boolean z) {
            java.util.Objects.requireNonNull(attributionSourceState);
            android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
            if (i == -1) {
                return;
            }
            android.content.AttributionSource attributionSource = new android.content.AttributionSource(attributionSourceState);
            android.content.AttributionSource attributionSource2 = null;
            while (true) {
                boolean z2 = true;
                boolean z3 = z || attributionSource2 != null;
                android.content.AttributionSource next = attributionSource.getNext();
                if ((!z || attributionSource.asState() != attributionSourceState) && next != null && !attributionSource.isTrusted(context)) {
                    return;
                }
                boolean z4 = z && attributionSource.asState() == attributionSourceState && next != null && next.getNext() == null;
                if (!z4 && next != null) {
                    z2 = false;
                }
                android.content.AttributionSource attributionSource3 = !z4 ? attributionSource : next;
                if (z2) {
                    java.lang.String resolvePackageName = resolvePackageName(context, attributionSource3);
                    if (resolvePackageName == null) {
                        return;
                    } else {
                        appOpsManager.finishOp(attributionSourceState.token, i, attributionSource3.withPackageName(resolvePackageName));
                    }
                } else {
                    android.content.AttributionSource resolveAttributionSource = resolveAttributionSource(context, attributionSource3);
                    if (resolveAttributionSource.getPackageName() == null) {
                        return;
                    } else {
                        appOpsManager.finishProxyOp(attributionSourceState.token, android.app.AppOpsManager.opToPublicName(i), resolveAttributionSource, z3);
                    }
                }
                com.android.server.pm.permission.PermissionManagerService.RegisteredAttribution registeredAttribution = (com.android.server.pm.permission.PermissionManagerService.RegisteredAttribution) com.android.server.pm.permission.PermissionManagerService.sRunningAttributionSources.remove(attributionSource.getToken());
                if (registeredAttribution != null) {
                    registeredAttribution.unregister();
                }
                if (next == null || next.getNext() == null) {
                    break;
                }
                attributionSource = next;
                attributionSource2 = attributionSource;
            }
        }

        public int checkOp(int i, android.content.AttributionSourceState attributionSourceState, java.lang.String str, boolean z, boolean z2) {
            int checkOp = checkOp(this.mContext, i, this.mPermissionManagerServiceInternal, new android.content.AttributionSource(attributionSourceState), str, z, z2);
            if (checkOp != 0 && z2) {
                finishDataDelivery(i, attributionSourceState, false);
            }
            return checkOp;
        }

        private static int checkPermission(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal permissionManagerServiceInternal, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.AttributionSource attributionSource, @android.annotation.Nullable java.lang.String str2, boolean z, boolean z2, boolean z3, int i) {
            android.content.pm.PermissionInfo permissionInfo = sPlatformPermissions.get(str);
            if (permissionInfo == null) {
                try {
                    permissionInfo = context.getPackageManager().getPermissionInfo(str, 0);
                    if (!com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(permissionInfo.packageName)) {
                        if (android.health.connect.HealthConnectManager.isHealthPermission(context, str)) {
                        }
                    }
                    sPlatformPermissions.put(str, permissionInfo);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    return 2;
                }
            }
            if (permissionInfo.isAppOp()) {
                return checkAppOpPermission(context, permissionManagerServiceInternal, str, attributionSource, str2, z, z3);
            }
            if (permissionInfo.isRuntime()) {
                return checkRuntimePermission(context, permissionManagerServiceInternal, str, attributionSource, str2, z, z2, z3, i);
            }
            if (!z3 && !checkPermission(context, permissionManagerServiceInternal, str, attributionSource)) {
                return 2;
            }
            if (attributionSource.getNext() != null) {
                return checkPermission(context, permissionManagerServiceInternal, str, attributionSource.getNext(), str2, z, z2, false, i);
            }
            return 0;
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x00b3, code lost:
        
            if (r0.getNext() != null) goto L51;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static int checkAppOpPermission(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal permissionManagerServiceInternal, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.AttributionSource attributionSource, @android.annotation.Nullable java.lang.String str2, boolean z, boolean z2) {
            java.lang.Object obj = attributionSource;
            int permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(str);
            if (permissionToOpCode < 0) {
                android.util.Slog.wtf(com.android.server.pm.permission.PermissionManagerService.LOG_TAG, "Appop permission " + str + " with no app op defined!");
                return 2;
            }
            android.content.AttributionSource attributionSource2 = null;
            android.content.AttributionSource attributionSource3 = obj;
            while (true) {
                boolean z3 = z2 || attributionSource2 != null;
                android.content.AttributionSource next = attributionSource3.getNext();
                if ((!z2 || !attributionSource3.equals(obj)) && next != null && !attributionSource3.isTrusted(context)) {
                    return 2;
                }
                boolean z4 = z2 && attributionSource3.equals(obj) && next != null && next.getNext() == null;
                switch (performOpTransaction(context, attributionSource.getToken(), permissionToOpCode, attributionSource3, str2, z, false, z3, z4 || next == null, z4, -1, 0, 0, -1)) {
                    case 1:
                    case 2:
                        return 2;
                    case 3:
                        if (!z3 && !checkPermission(context, permissionManagerServiceInternal, str, attributionSource)) {
                            return 2;
                        }
                        attributionSource2 = next;
                        if (attributionSource2 != null && !checkPermission(context, permissionManagerServiceInternal, str, attributionSource2)) {
                            return 2;
                        }
                        break;
                    default:
                        attributionSource2 = next;
                        break;
                }
                obj = attributionSource;
                attributionSource3 = attributionSource2;
            }
            return 0;
        }

        /* JADX WARN: Code restructure failed: missing block: B:81:0x017d, code lost:
        
            return 0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static int checkRuntimePermission(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal permissionManagerServiceInternal, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.AttributionSource attributionSource, @android.annotation.Nullable java.lang.String str2, boolean z, boolean z2, boolean z3, int i) {
            int i2;
            android.content.AttributionSource attributionSource2;
            int i3;
            boolean z4;
            int i4;
            com.android.server.pm.permission.PermissionManagerServiceInternal permissionManagerServiceInternal2 = permissionManagerServiceInternal;
            java.lang.String str3 = str;
            android.content.AttributionSource attributionSource3 = attributionSource;
            boolean z5 = z3;
            int permissionToOpCode = android.app.AppOpsManager.permissionToOpCode(str);
            int attributionChainId = getAttributionChainId(z2, attributionSource3);
            boolean z6 = attributionChainId != -1;
            boolean z7 = !z6 || checkPermission(context, permissionManagerServiceInternal2, "android.permission.UPDATE_APP_OPS_STATS", attributionSource3);
            android.content.AttributionSource attributionSource4 = null;
            android.content.AttributionSource attributionSource5 = attributionSource3;
            while (true) {
                boolean z8 = z5 || attributionSource4 != null;
                android.content.AttributionSource next = attributionSource5.getNext();
                if (!(z5 && attributionSource5.equals(attributionSource3)) && next != null && !attributionSource5.isTrusted(context)) {
                    return 2;
                }
                if (!z8 && !checkPermission(context, permissionManagerServiceInternal2, str3, attributionSource5)) {
                    return 2;
                }
                if (next != null && !checkPermission(context, permissionManagerServiceInternal2, str3, next)) {
                    return 2;
                }
                if (permissionToOpCode < 0) {
                    if (sPlatformPermissions.containsKey(str3) && !"android.permission.ACCESS_BACKGROUND_LOCATION".equals(str3) && !"android.permission.BODY_SENSORS_BACKGROUND".equals(str3)) {
                        android.util.Slog.wtf(com.android.server.pm.permission.PermissionManagerService.LOG_TAG, "Platform runtime permission " + str3 + " with no app op defined!");
                    }
                    if (next == null) {
                        return 0;
                    }
                    attributionSource2 = next;
                    i3 = attributionChainId;
                    i4 = permissionToOpCode;
                    z4 = z5;
                } else {
                    boolean z9 = z5 && attributionSource5.equals(attributionSource3) && next != null && next.getNext() == null;
                    boolean z10 = z9 || next == null;
                    boolean z11 = z7 && (attributionSource5.isTrusted(context) || attributionSource5.equals(attributionSource3)) && (next == null || next.isTrusted(context));
                    if (!z8 && z6) {
                        i2 = resolveProxyAttributionFlags(attributionSource, attributionSource5, z3, z2, z10, z11);
                    } else {
                        i2 = 0;
                    }
                    android.content.AttributionSource attributionSource6 = attributionSource5;
                    attributionSource2 = next;
                    android.content.AttributionSource attributionSource7 = attributionSource5;
                    boolean z12 = z8;
                    i3 = attributionChainId;
                    int i5 = permissionToOpCode;
                    switch (performOpTransaction(context, attributionSource.getToken(), permissionToOpCode, attributionSource6, str2, z, z2, z12, z10, z9, i, i2, z6 ? resolveProxiedAttributionFlags(attributionSource, next, z3, z2, z10, z11) : 0, i3)) {
                        case 1:
                            return 1;
                        case 2:
                            if (str.equals("android.permission.BLUETOOTH_CONNECT")) {
                                android.util.Slog.e(com.android.server.pm.permission.PermissionManagerService.LOG_TAG, "BLUETOOTH_CONNECT permission hard denied as op mode is MODE_ERRORED. Permission check was requested for: " + attributionSource + " and op transaction was invoked for " + attributionSource7);
                            }
                            return 2;
                        default:
                            if (!z2) {
                                z4 = z3;
                                i4 = i5;
                            } else {
                                z4 = z3;
                                i4 = i5;
                                com.android.server.pm.permission.PermissionManagerService.sRunningAttributionSources.put(attributionSource7.getToken(), new com.android.server.pm.permission.PermissionManagerService.RegisteredAttribution(context, i4, attributionSource7, z4));
                            }
                            if (attributionSource2 != null && attributionSource2.getNext() != null) {
                                break;
                            }
                            break;
                    }
                }
                permissionManagerServiceInternal2 = permissionManagerServiceInternal;
                str3 = str;
                attributionSource3 = attributionSource;
                z5 = z4;
                permissionToOpCode = i4;
                attributionChainId = i3;
                attributionSource4 = attributionSource2;
                attributionSource5 = attributionSource4;
            }
        }

        private static boolean checkPermission(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal permissionManagerServiceInternal, @android.annotation.NonNull java.lang.String str, android.content.AttributionSource attributionSource) {
            int uid = attributionSource.getUid();
            int deviceId = attributionSource.getDeviceId();
            if (context.getDeviceId() != deviceId) {
                context = context.createDeviceContext(deviceId);
            }
            boolean z = context.checkPermission(str, -1, uid) == 0;
            if (!z && android.os.Process.isIsolated(uid) && (str.equals("android.permission.RECORD_AUDIO") || str.equals("android.permission.CAPTURE_AUDIO_HOTWORD") || str.equals("android.permission.CAPTURE_AUDIO_OUTPUT") || str.equals("android.permission.CAMERA"))) {
                com.android.server.pm.permission.PermissionManagerServiceInternal.HotwordDetectionServiceProvider hotwordDetectionServiceProvider = permissionManagerServiceInternal.getHotwordDetectionServiceProvider();
                z = hotwordDetectionServiceProvider != null && uid == hotwordDetectionServiceProvider.getUid();
            }
            java.util.Set renouncedPermissions = attributionSource.getRenouncedPermissions();
            if (z && renouncedPermissions.contains(str) && context.checkPermission("android.permission.RENOUNCE_PERMISSIONS", -1, uid) == 0) {
                return false;
            }
            return z;
        }

        private static int resolveProxyAttributionFlags(@android.annotation.NonNull android.content.AttributionSource attributionSource, @android.annotation.NonNull android.content.AttributionSource attributionSource2, boolean z, boolean z2, boolean z3, boolean z4) {
            return resolveAttributionFlags(attributionSource, attributionSource2, z, z2, z3, z4, true);
        }

        private static int resolveProxiedAttributionFlags(@android.annotation.NonNull android.content.AttributionSource attributionSource, @android.annotation.NonNull android.content.AttributionSource attributionSource2, boolean z, boolean z2, boolean z3, boolean z4) {
            return resolveAttributionFlags(attributionSource, attributionSource2, z, z2, z3, z4, false);
        }

        private static int resolveAttributionFlags(@android.annotation.NonNull android.content.AttributionSource attributionSource, @android.annotation.NonNull android.content.AttributionSource attributionSource2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            int i;
            if (attributionSource2 == null || !z2) {
                return 0;
            }
            if (!z4) {
                i = 0;
            } else {
                i = 8;
            }
            if (z5) {
                if (z3) {
                    return i | 1;
                }
                if (!z && attributionSource2.equals(attributionSource)) {
                    return i | 1;
                }
            } else {
                if (z3) {
                    return i | 4;
                }
                if (z && attributionSource2.equals(attributionSource.getNext())) {
                    return i | 1;
                }
                if (attributionSource2.getNext() == null) {
                    return i | 4;
                }
            }
            if (z && attributionSource2.equals(attributionSource)) {
                return 0;
            }
            return i | 2;
        }

        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
            	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
            	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
            	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
            	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
            	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
            	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
            	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
            */
        private static int checkOp(@android.annotation.NonNull android.content.Context r24, @android.annotation.NonNull int r25, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal r26, @android.annotation.NonNull android.content.AttributionSource r27, @android.annotation.Nullable java.lang.String r28, boolean r29, boolean r30) {
            /*
                Method dump skipped, instructions count: 216
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.permission.PermissionManagerService.PermissionCheckerService.checkOp(android.content.Context, int, com.android.server.pm.permission.PermissionManagerServiceInternal, android.content.AttributionSource, java.lang.String, boolean, boolean):int");
        }

        /* JADX WARN: Removed duplicated region for block: B:65:0x01ac  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x0201  */
        /* JADX WARN: Removed duplicated region for block: B:75:0x0223  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x01e4  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static int performOpTransaction(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, @android.annotation.Nullable java.lang.String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i2, int i3, int i4, int i5) {
            java.lang.String str2;
            int i6;
            int i7;
            int noteProxyOpNoThrow;
            int max;
            java.lang.String str3;
            int i8;
            int i9;
            int i10;
            int startProxyOpNoThrow;
            int i11 = i2;
            android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
            android.content.AttributionSource next = !z5 ? attributionSource : attributionSource.getNext();
            if (!z) {
                java.lang.String resolvePackageName = resolvePackageName(context, next);
                if (resolvePackageName == null) {
                    return 2;
                }
                int unsafeCheckOpRawNoThrow = appOpsManager.unsafeCheckOpRawNoThrow(i, next.withPackageName(resolvePackageName));
                android.content.AttributionSource next2 = next.getNext();
                if (!z4 && unsafeCheckOpRawNoThrow == 0 && next2 != null) {
                    java.lang.String resolvePackageName2 = resolvePackageName(context, next2);
                    if (resolvePackageName2 == null) {
                        return 2;
                    }
                    return appOpsManager.unsafeCheckOpRawNoThrow(i, next2.withPackageName(resolvePackageName2));
                }
                return unsafeCheckOpRawNoThrow;
            }
            if (!z2) {
                android.content.AttributionSource resolveAttributionSource = resolveAttributionSource(context, next);
                if (resolveAttributionSource.getPackageName() == null) {
                    return 2;
                }
                if (i11 != -1) {
                    str2 = " while not having ";
                    i6 = i;
                    if (i11 != i6) {
                        int checkOpNoThrow = appOpsManager.checkOpNoThrow(i6, resolveAttributionSource);
                        if (checkOpNoThrow == 2) {
                            return checkOpNoThrow;
                        }
                        i7 = checkOpNoThrow;
                        if (!z4) {
                            try {
                                noteProxyOpNoThrow = appOpsManager.noteOpNoThrow(i11, resolveAttributionSource, str);
                            } catch (java.lang.SecurityException e) {
                                android.util.Slog.w(com.android.server.pm.permission.PermissionManagerService.LOG_TAG, "Datasource " + attributionSource + " protecting data with platform defined runtime permission " + android.app.AppOpsManager.opToPermission(i) + str2 + "android.permission.UPDATE_APP_OPS_STATS");
                                noteProxyOpNoThrow = appOpsManager.noteProxyOpNoThrow(i11, attributionSource, str, z3);
                            }
                        } else {
                            try {
                                noteProxyOpNoThrow = appOpsManager.noteProxyOpNoThrow(i11, resolveAttributionSource, str, z3);
                            } catch (java.lang.SecurityException e2) {
                                java.lang.String str4 = "Security exception for op " + i11 + " with source " + attributionSource.getUid() + ":" + attributionSource.getPackageName() + ", " + attributionSource.getNextUid() + ":" + attributionSource.getNextPackageName();
                                if (attributionSource.getNext() != null) {
                                    android.content.AttributionSource next3 = attributionSource.getNext();
                                    str4 = str4 + ", " + next3.getNextPackageName() + ":" + next3.getNextUid();
                                }
                                throw new java.lang.SecurityException(str4 + ":" + e2.getMessage());
                            }
                        }
                        max = java.lang.Math.max(i7, noteProxyOpNoThrow);
                        if (i6 == 111 && max == 2) {
                            if (max != i7) {
                                android.util.Slog.e(com.android.server.pm.permission.PermissionManagerService.LOG_TAG, "BLUETOOTH_CONNECT permission hard denied as checkOp for resolvedAttributionSource " + resolveAttributionSource + " and op " + i6 + " returned MODE_ERRORED");
                            } else {
                                android.util.Slog.e(com.android.server.pm.permission.PermissionManagerService.LOG_TAG, "BLUETOOTH_CONNECT permission hard denied as noteOp for resolvedAttributionSource " + resolveAttributionSource + " and op " + i11 + " returned MODE_ERRORED");
                            }
                        }
                        return max;
                    }
                } else {
                    str2 = " while not having ";
                    i6 = i;
                }
                i11 = i6;
                i7 = 0;
                if (!z4) {
                }
                max = java.lang.Math.max(i7, noteProxyOpNoThrow);
                if (i6 == 111) {
                    if (max != i7) {
                    }
                }
                return max;
            }
            android.content.AttributionSource resolveAttributionSource2 = resolveAttributionSource(context, next);
            if (resolveAttributionSource2.getPackageName() == null) {
                return 2;
            }
            if (i11 == -1 || i11 == i) {
                str3 = ":";
                i8 = i;
                i9 = 0;
            } else {
                i9 = appOpsManager.checkOpNoThrow(i, resolveAttributionSource2);
                str3 = ":";
                if (i9 == 2) {
                    return i9;
                }
                i8 = i11;
            }
            if (z4) {
                int i12 = i9;
                try {
                    startProxyOpNoThrow = appOpsManager.startOpNoThrow(iBinder, i8, resolveAttributionSource2, false, str, i3, i5);
                } catch (java.lang.SecurityException e3) {
                    android.util.Slog.w(com.android.server.pm.permission.PermissionManagerService.LOG_TAG, "Datasource " + attributionSource + " protecting data with platform defined runtime permission " + android.app.AppOpsManager.opToPermission(i) + " while not having android.permission.UPDATE_APP_OPS_STATS");
                    startProxyOpNoThrow = appOpsManager.startProxyOpNoThrow(iBinder, i2, attributionSource, str, z3, i3, i4, i5);
                }
                i10 = i12;
            } else {
                i10 = i9;
                int i13 = i8;
                java.lang.String str5 = str3;
                try {
                    startProxyOpNoThrow = appOpsManager.startProxyOpNoThrow(iBinder, i13, resolveAttributionSource2, str, z3, i3, i4, i5);
                } catch (java.lang.SecurityException e4) {
                    java.lang.String str6 = "Security exception for op " + i13 + " with source " + attributionSource.getUid() + str5 + attributionSource.getPackageName() + ", " + attributionSource.getNextUid() + str5 + attributionSource.getNextPackageName();
                    if (attributionSource.getNext() != null) {
                        android.content.AttributionSource next4 = attributionSource.getNext();
                        str6 = str6 + ", " + next4.getNextPackageName() + str5 + next4.getNextUid();
                    }
                    throw new java.lang.SecurityException(str6 + str5 + e4.getMessage());
                }
            }
            return java.lang.Math.max(i10, startProxyOpNoThrow);
        }

        private static int getAttributionChainId(boolean z, android.content.AttributionSource attributionSource) {
            if (attributionSource == null || attributionSource.getNext() == null || !z) {
                return -1;
            }
            int incrementAndGet = sAttributionChainIds.incrementAndGet();
            if (incrementAndGet < 0) {
                sAttributionChainIds.set(0);
                return 0;
            }
            return incrementAndGet;
        }

        @android.annotation.Nullable
        private static java.lang.String resolvePackageName(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.AttributionSource attributionSource) {
            if (attributionSource.getPackageName() != null) {
                return attributionSource.getPackageName();
            }
            java.lang.String[] packagesForUid = context.getPackageManager().getPackagesForUid(attributionSource.getUid());
            if (packagesForUid != null) {
                return packagesForUid[0];
            }
            return android.app.AppOpsManager.resolvePackageName(attributionSource.getUid(), attributionSource.getPackageName());
        }

        @android.annotation.NonNull
        private static android.content.AttributionSource resolveAttributionSource(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.AttributionSource attributionSource) {
            if (attributionSource.getPackageName() != null) {
                return attributionSource;
            }
            return attributionSource.withPackageName(resolvePackageName(context, attributionSource));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class RegisteredAttribution {
        private final android.os.IBinder.DeathRecipient mDeathRecipient;
        private final java.util.concurrent.atomic.AtomicBoolean mFinished = new java.util.concurrent.atomic.AtomicBoolean(false);
        private final android.os.IBinder mToken;

        RegisteredAttribution(final android.content.Context context, final int i, final android.content.AttributionSource attributionSource, final boolean z) {
            this.mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.pm.permission.PermissionManagerService$RegisteredAttribution$$ExternalSyntheticLambda0
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    com.android.server.pm.permission.PermissionManagerService.RegisteredAttribution.this.lambda$new$0(context, i, attributionSource, z);
                }
            };
            this.mToken = attributionSource.getToken();
            if (this.mToken != null) {
                try {
                    this.mToken.linkToDeath(this.mDeathRecipient, 0);
                } catch (android.os.RemoteException e) {
                    this.mDeathRecipient.binderDied();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(android.content.Context context, int i, android.content.AttributionSource attributionSource, boolean z) {
            if (unregister()) {
                com.android.server.pm.permission.PermissionManagerService.PermissionCheckerService.finishDataDelivery(context, i, attributionSource.asState(), z);
            }
        }

        public boolean unregister() {
            if (!this.mFinished.compareAndSet(false, true)) {
                return false;
            }
            try {
                if (this.mToken != null) {
                    this.mToken.unlinkToDeath(this.mDeathRecipient, 0);
                }
            } catch (java.util.NoSuchElementException e) {
            }
            return true;
        }
    }

    protected void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        this.mPermissionManagerServiceImpl.dump(fileDescriptor, printWriter, strArr);
    }
}
