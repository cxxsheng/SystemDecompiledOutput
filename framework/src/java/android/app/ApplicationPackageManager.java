package android.app;

/* loaded from: classes.dex */
public class ApplicationPackageManager extends android.content.pm.PackageManager {
    public static final java.lang.String APP_PERMISSION_BUTTON_ALLOW_ALWAYS = "app_permission_button_allow_always";
    private static final boolean DEBUG_ICONS = false;
    private static final int DEFAULT_CHECKSUMS = 127;
    private static final int DEFAULT_EPHEMERAL_COOKIE_MAX_SIZE_BYTES = 16384;
    public static final java.lang.String PERMISSION_CONTROLLER_RESOURCE_PACKAGE = "com.android.permissioncontroller";
    private static final java.lang.String TAG = "ApplicationPackageManager";
    private static final int sDefaultFlags = 1024;
    private volatile com.nvidia.NvAppProfileService mAppProfileService;
    private volatile android.content.pm.dex.ArtManager mArtManager;
    private final android.app.ContextImpl mContext;
    private volatile android.app.admin.DevicePolicyManager mDevicePolicyManager;
    private volatile android.content.pm.PackageInstaller mInstaller;
    private final android.content.pm.IPackageManager mPM;
    private volatile android.permission.PermissionManager mPermissionManager;
    private volatile java.lang.String mPermissionsControllerPackageName;
    private volatile android.os.UserManager mUserManager;
    private static final android.app.PropertyInvalidatedCache<android.app.ApplicationPackageManager.HasSystemFeatureQuery, java.lang.Boolean> mHasSystemFeatureCache = new android.app.PropertyInvalidatedCache<android.app.ApplicationPackageManager.HasSystemFeatureQuery, java.lang.Boolean>(256, "cache_key.has_system_feature") { // from class: android.app.ApplicationPackageManager.1
        @Override // android.app.PropertyInvalidatedCache
        public java.lang.Boolean recompute(android.app.ApplicationPackageManager.HasSystemFeatureQuery hasSystemFeatureQuery) {
            try {
                android.app.ActivityThread.currentActivityThread();
                return java.lang.Boolean.valueOf(android.app.ActivityThread.getPackageManager().hasSystemFeature(hasSystemFeatureQuery.name, hasSystemFeatureQuery.version));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    };
    private static final java.lang.String CACHE_KEY_PACKAGES_FOR_UID_PROPERTY = "cache_key.get_packages_for_uid";
    private static final android.app.PropertyInvalidatedCache<java.lang.Integer, android.app.ApplicationPackageManager.GetPackagesForUidResult> mGetPackagesForUidCache = new android.app.PropertyInvalidatedCache<java.lang.Integer, android.app.ApplicationPackageManager.GetPackagesForUidResult>(32, CACHE_KEY_PACKAGES_FOR_UID_PROPERTY) { // from class: android.app.ApplicationPackageManager.3
        @Override // android.app.PropertyInvalidatedCache
        public android.app.ApplicationPackageManager.GetPackagesForUidResult recompute(java.lang.Integer num) {
            try {
                android.app.ActivityThread.currentActivityThread();
                return new android.app.ApplicationPackageManager.GetPackagesForUidResult(android.app.ActivityThread.getPackageManager().getPackagesForUid(num.intValue()));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.app.PropertyInvalidatedCache
        public java.lang.String queryToString(java.lang.Integer num) {
            return java.lang.String.format("uid=%d", java.lang.Integer.valueOf(num.intValue()));
        }
    };
    private static final java.lang.Object sSync = new java.lang.Object();
    private static android.util.ArrayMap<android.app.ApplicationPackageManager.ResourceName, java.lang.ref.WeakReference<android.graphics.drawable.Drawable.ConstantState>> sIconCache = new android.util.ArrayMap<>();
    private static android.util.ArrayMap<android.app.ApplicationPackageManager.ResourceName, java.lang.ref.WeakReference<java.lang.CharSequence>> sStringCache = new android.util.ArrayMap<>();
    private final java.util.ArrayList<android.app.ApplicationPackageManager.MoveCallbackDelegate> mDelegates = new java.util.ArrayList<>();
    private final android.util.ArraySet<android.os.IRemoteCallback> mPackageMonitorCallbacks = new android.util.ArraySet<>();
    volatile int mCachedSafeMode = -1;
    private volatile boolean mUserUnlocked = false;

    android.os.UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = android.os.UserManager.get(this.mContext);
        }
        return this.mUserManager;
    }

    android.app.admin.DevicePolicyManager getDevicePolicyManager() {
        if (this.mDevicePolicyManager == null) {
            this.mDevicePolicyManager = (android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class);
        }
        return this.mDevicePolicyManager;
    }

    private android.permission.PermissionManager getPermissionManager() {
        if (this.mPermissionManager == null) {
            this.mPermissionManager = (android.permission.PermissionManager) this.mContext.getSystemService(android.permission.PermissionManager.class);
        }
        return this.mPermissionManager;
    }

    @Override // android.content.pm.PackageManager
    public int getUserId() {
        return this.mContext.getUserId();
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.PackageInfo getPackageInfo(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return getPackageInfo(str, android.content.pm.PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.PackageInfo getPackageInfo(java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        return getPackageInfoAsUser(str, packageInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.PackageInfo getPackageInfo(android.content.pm.VersionedPackage versionedPackage, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return getPackageInfo(versionedPackage, android.content.pm.PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.PackageInfo getPackageInfo(android.content.pm.VersionedPackage versionedPackage, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        int userId = getUserId();
        try {
            android.content.pm.PackageInfo packageInfoVersioned = this.mPM.getPackageInfoVersioned(versionedPackage, updateFlagsForPackage(packageInfoFlags.getValue(), userId), userId);
            if (packageInfoVersioned != null) {
                return packageInfoVersioned;
            }
            throw new android.content.pm.PackageManager.NameNotFoundException(versionedPackage.toString());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.PackageInfo getPackageInfoAsUser(java.lang.String str, int i, int i2) throws android.content.pm.PackageManager.NameNotFoundException {
        return getPackageInfoAsUser(str, android.content.pm.PackageManager.PackageInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.PackageInfo getPackageInfoAsUser(java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        android.content.pm.PackageInfo packageInfoAsUserCached = getPackageInfoAsUserCached(str, updateFlagsForPackage(packageInfoFlags.getValue(), i), i);
        if (packageInfoAsUserCached == null) {
            throw new android.content.pm.PackageManager.NameNotFoundException(str);
        }
        return packageInfoAsUserCached;
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String[] currentToCanonicalPackageNames(java.lang.String[] strArr) {
        try {
            return this.mPM.currentToCanonicalPackageNames(strArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String[] canonicalToCurrentPackageNames(java.lang.String[] strArr) {
        try {
            return this.mPM.canonicalToCurrentPackageNames(strArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.Intent getLaunchIntentForPackage(java.lang.String str) {
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_MAIN);
        intent.addCategory(android.content.Intent.CATEGORY_INFO);
        intent.setPackage(str);
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = queryIntentActivities(intent, 0);
        if (queryIntentActivities == null || queryIntentActivities.size() <= 0) {
            intent.removeCategory(android.content.Intent.CATEGORY_INFO);
            intent.addCategory(android.content.Intent.CATEGORY_LAUNCHER);
            intent.setPackage(str);
            queryIntentActivities = queryIntentActivities(intent, 0);
        }
        if (queryIntentActivities == null || queryIntentActivities.size() <= 0) {
            return null;
        }
        android.content.Intent intent2 = new android.content.Intent(intent);
        intent2.setFlags(268435456);
        intent2.setClassName(queryIntentActivities.get(0).activityInfo.packageName, queryIntentActivities.get(0).activityInfo.name);
        return intent2;
    }

    @Override // android.content.pm.PackageManager
    public android.content.Intent getLeanbackLaunchIntentForPackage(java.lang.String str) {
        return getLaunchIntentForPackageAndCategory(str, android.content.Intent.CATEGORY_LEANBACK_LAUNCHER);
    }

    @Override // android.content.pm.PackageManager
    public android.content.Intent getCarLaunchIntentForPackage(java.lang.String str) {
        return getLaunchIntentForPackageAndCategory(str, android.content.Intent.CATEGORY_CAR_LAUNCHER);
    }

    private android.content.Intent getLaunchIntentForPackageAndCategory(java.lang.String str, java.lang.String str2) {
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_MAIN);
        intent.addCategory(str2);
        intent.setPackage(str);
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = queryIntentActivities(intent, 0);
        if (queryIntentActivities == null || queryIntentActivities.size() <= 0) {
            return null;
        }
        android.content.Intent intent2 = new android.content.Intent(intent);
        intent2.setFlags(268435456);
        intent2.setClassName(queryIntentActivities.get(0).activityInfo.packageName, queryIntentActivities.get(0).activityInfo.name);
        return intent2;
    }

    @Override // android.content.pm.PackageManager
    public android.content.IntentSender getLaunchIntentSenderForPackage(java.lang.String str) {
        try {
            return this.mPM.getLaunchIntentSenderForPackage(str, this.mContext.getPackageName(), this.mContext.getAttributionTag(), getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int[] getPackageGids(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        return getPackageGids(str, 0);
    }

    @Override // android.content.pm.PackageManager
    public int[] getPackageGids(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return getPackageGids(str, android.content.pm.PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public int[] getPackageGids(java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        int userId = getUserId();
        try {
            int[] packageGids = this.mPM.getPackageGids(str, updateFlagsForPackage(packageInfoFlags.getValue(), userId), userId);
            if (packageGids != null) {
                return packageGids;
            }
            throw new android.content.pm.PackageManager.NameNotFoundException(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getPackageUid(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return getPackageUid(str, android.content.pm.PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public int getPackageUid(java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        return getPackageUidAsUser(str, packageInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public int getPackageUidAsUser(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return getPackageUidAsUser(str, 0, i);
    }

    @Override // android.content.pm.PackageManager
    public int getPackageUidAsUser(java.lang.String str, int i, int i2) throws android.content.pm.PackageManager.NameNotFoundException {
        return getPackageUidAsUser(str, android.content.pm.PackageManager.PackageInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public int getPackageUidAsUser(java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            int packageUid = this.mPM.getPackageUid(str, updateFlagsForPackage(packageInfoFlags.getValue(), i), i);
            if (packageUid >= 0) {
                return packageUid;
            }
            throw new android.content.pm.PackageManager.NameNotFoundException(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PermissionGroupInfo> getAllPermissionGroups(int i) {
        return getPermissionManager().getAllPermissionGroups(i);
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        android.content.pm.PermissionGroupInfo permissionGroupInfo = getPermissionManager().getPermissionGroupInfo(str, i);
        if (permissionGroupInfo == null) {
            throw new android.content.pm.PackageManager.NameNotFoundException(str);
        }
        return permissionGroupInfo;
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.PermissionInfo getPermissionInfo(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        android.content.pm.PermissionInfo permissionInfo = getPermissionManager().getPermissionInfo(str, i);
        if (permissionInfo == null) {
            throw new android.content.pm.PackageManager.NameNotFoundException(str);
        }
        return permissionInfo;
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PermissionInfo> queryPermissionsByGroup(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        java.util.List<android.content.pm.PermissionInfo> queryPermissionsByGroup = getPermissionManager().queryPermissionsByGroup(str, i);
        if (queryPermissionsByGroup == null) {
            throw new android.content.pm.PackageManager.NameNotFoundException(str);
        }
        return queryPermissionsByGroup;
    }

    @Override // android.content.pm.PackageManager
    public void getPlatformPermissionsForGroup(java.lang.String str, java.util.concurrent.Executor executor, java.util.function.Consumer<java.util.List<java.lang.String>> consumer) {
        ((android.permission.PermissionControllerManager) this.mContext.getSystemService(android.permission.PermissionControllerManager.class)).getPlatformPermissionsForGroup(str, executor, consumer);
    }

    @Override // android.content.pm.PackageManager
    public void getGroupOfPlatformPermission(java.lang.String str, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.String> consumer) {
        ((android.permission.PermissionControllerManager) this.mContext.getSystemService(android.permission.PermissionControllerManager.class)).getGroupOfPlatformPermission(str, executor, consumer);
    }

    @Override // android.content.pm.PackageManager
    public boolean arePermissionsIndividuallyControlled() {
        return this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_permissionsIndividuallyControlled);
    }

    @Override // android.content.pm.PackageManager
    public boolean isWirelessConsentModeEnabled() {
        return this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_wirelessConsentRequired);
    }

    @Override // android.content.pm.PackageManager
    public com.nvidia.NvAppProfileService getAppProfileService() {
        if (this.mAppProfileService == null) {
            this.mAppProfileService = new com.nvidia.NvAppProfileService(this.mContext);
        }
        return this.mAppProfileService;
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return getApplicationInfo(str, android.content.pm.PackageManager.ApplicationInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, android.content.pm.PackageManager.ApplicationInfoFlags applicationInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        return getApplicationInfoAsUser(str, applicationInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ApplicationInfo getApplicationInfoAsUser(java.lang.String str, int i, int i2) throws android.content.pm.PackageManager.NameNotFoundException {
        return getApplicationInfoAsUser(str, android.content.pm.PackageManager.ApplicationInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ApplicationInfo getApplicationInfoAsUser(java.lang.String str, android.content.pm.PackageManager.ApplicationInfoFlags applicationInfoFlags, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        android.content.pm.ApplicationInfo applicationInfoAsUserCached = getApplicationInfoAsUserCached(str, updateFlagsForApplication(applicationInfoFlags.getValue(), i), i);
        if (applicationInfoAsUserCached == null) {
            throw new android.content.pm.PackageManager.NameNotFoundException(str);
        }
        return maybeAdjustApplicationInfo(applicationInfoAsUserCached);
    }

    private static android.content.pm.ApplicationInfo maybeAdjustApplicationInfo(android.content.pm.ApplicationInfo applicationInfo) {
        if (applicationInfo.primaryCpuAbi != null && applicationInfo.secondaryCpuAbi != null) {
            java.lang.String vmInstructionSet = dalvik.system.VMRuntime.getRuntime().vmInstructionSet();
            java.lang.String instructionSet = dalvik.system.VMRuntime.getInstructionSet(applicationInfo.secondaryCpuAbi);
            java.lang.String str = android.os.SystemProperties.get("ro.dalvik.vm.isa." + instructionSet);
            if (!str.isEmpty()) {
                instructionSet = str;
            }
            if (vmInstructionSet.equals(instructionSet)) {
                android.content.pm.ApplicationInfo applicationInfo2 = new android.content.pm.ApplicationInfo(applicationInfo);
                applicationInfo2.nativeLibraryDir = applicationInfo.secondaryNativeLibraryDir;
                return applicationInfo2;
            }
        }
        return applicationInfo;
    }

    @Override // android.content.pm.PackageManager
    public int getTargetSdkVersion(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            int targetSdkVersion = this.mPM.getTargetSdkVersion(str);
            if (targetSdkVersion != -1) {
                return targetSdkVersion;
            }
            throw new android.content.pm.PackageManager.NameNotFoundException(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return getActivityInfo(componentName, android.content.pm.PackageManager.ComponentInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName componentName, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        int userId = getUserId();
        try {
            android.content.pm.ActivityInfo activityInfo = this.mPM.getActivityInfo(componentName, updateFlagsForComponent(componentInfoFlags.getValue(), userId, null), userId);
            if (activityInfo != null) {
                return activityInfo;
            }
            throw new android.content.pm.PackageManager.NameNotFoundException(componentName.toString());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ActivityInfo getReceiverInfo(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return getReceiverInfo(componentName, android.content.pm.PackageManager.ComponentInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ActivityInfo getReceiverInfo(android.content.ComponentName componentName, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        int userId = getUserId();
        try {
            android.content.pm.ActivityInfo receiverInfo = this.mPM.getReceiverInfo(componentName, updateFlagsForComponent(componentInfoFlags.getValue(), userId, null), userId);
            if (receiverInfo != null) {
                return receiverInfo;
            }
            throw new android.content.pm.PackageManager.NameNotFoundException(componentName.toString());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ServiceInfo getServiceInfo(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return getServiceInfo(componentName, android.content.pm.PackageManager.ComponentInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ServiceInfo getServiceInfo(android.content.ComponentName componentName, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        int userId = getUserId();
        try {
            android.content.pm.ServiceInfo serviceInfo = this.mPM.getServiceInfo(componentName, updateFlagsForComponent(componentInfoFlags.getValue(), userId, null), userId);
            if (serviceInfo != null) {
                return serviceInfo;
            }
            throw new android.content.pm.PackageManager.NameNotFoundException(componentName.toString());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ProviderInfo getProviderInfo(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return getProviderInfo(componentName, android.content.pm.PackageManager.ComponentInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ProviderInfo getProviderInfo(android.content.ComponentName componentName, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags) throws android.content.pm.PackageManager.NameNotFoundException {
        int userId = getUserId();
        try {
            android.content.pm.ProviderInfo providerInfo = this.mPM.getProviderInfo(componentName, updateFlagsForComponent(componentInfoFlags.getValue(), userId, null), userId);
            if (providerInfo != null) {
                return providerInfo;
            }
            throw new android.content.pm.PackageManager.NameNotFoundException(componentName.toString());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String[] getSystemSharedLibraryNames() {
        try {
            return this.mPM.getSystemSharedLibraryNames();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.SharedLibraryInfo> getSharedLibraries(int i) {
        return getSharedLibraries(android.content.pm.PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.SharedLibraryInfo> getSharedLibraries(android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) {
        return getSharedLibrariesAsUser(packageInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.SharedLibraryInfo> getSharedLibrariesAsUser(int i, int i2) {
        return getSharedLibrariesAsUser(android.content.pm.PackageManager.PackageInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.SharedLibraryInfo> getSharedLibrariesAsUser(android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags, int i) {
        try {
            android.content.pm.ParceledListSlice sharedLibraries = this.mPM.getSharedLibraries(this.mContext.getOpPackageName(), packageInfoFlags.getValue(), i);
            if (sharedLibraries == null) {
                return java.util.Collections.emptyList();
            }
            return sharedLibraries.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.SharedLibraryInfo> getDeclaredSharedLibraries(java.lang.String str, int i) {
        return getDeclaredSharedLibraries(str, android.content.pm.PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.SharedLibraryInfo> getDeclaredSharedLibraries(java.lang.String str, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) {
        try {
            android.content.pm.ParceledListSlice declaredSharedLibraries = this.mPM.getDeclaredSharedLibraries(str, packageInfoFlags.getValue(), this.mContext.getUserId());
            return declaredSharedLibraries != null ? declaredSharedLibraries.getList() : java.util.Collections.emptyList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getServicesSystemSharedLibraryPackageName() {
        try {
            return this.mPM.getServicesSystemSharedLibraryPackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getSharedSystemSharedLibraryPackageName() {
        try {
            return this.mPM.getSharedSystemSharedLibraryPackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ChangedPackages getChangedPackages(int i) {
        try {
            return this.mPM.getChangedPackages(i, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.FeatureInfo[] getSystemAvailableFeatures() {
        try {
            android.content.pm.ParceledListSlice systemAvailableFeatures = this.mPM.getSystemAvailableFeatures();
            if (systemAvailableFeatures == null) {
                return new android.content.pm.FeatureInfo[0];
            }
            java.util.List list = systemAvailableFeatures.getList();
            int size = list.size();
            android.content.pm.FeatureInfo[] featureInfoArr = new android.content.pm.FeatureInfo[size];
            for (int i = 0; i < size; i++) {
                featureInfoArr[i] = (android.content.pm.FeatureInfo) list.get(i);
            }
            return featureInfoArr;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean hasSystemFeature(java.lang.String str) {
        return hasSystemFeature(str, 0);
    }

    private static final class HasSystemFeatureQuery {
        public final java.lang.String name;
        public final int version;

        public HasSystemFeatureQuery(java.lang.String str, int i) {
            this.name = str;
            this.version = i;
        }

        public java.lang.String toString() {
            return java.lang.String.format("HasSystemFeatureQuery(name=\"%s\", version=%d)", this.name, java.lang.Integer.valueOf(this.version));
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.app.ApplicationPackageManager.HasSystemFeatureQuery)) {
                return false;
            }
            android.app.ApplicationPackageManager.HasSystemFeatureQuery hasSystemFeatureQuery = (android.app.ApplicationPackageManager.HasSystemFeatureQuery) obj;
            return java.util.Objects.equals(this.name, hasSystemFeatureQuery.name) && this.version == hasSystemFeatureQuery.version;
        }

        public int hashCode() {
            return (java.util.Objects.hashCode(this.name) * 13) + this.version;
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean hasSystemFeature(java.lang.String str, int i) {
        return mHasSystemFeatureCache.query(new android.app.ApplicationPackageManager.HasSystemFeatureQuery(str, i)).booleanValue();
    }

    public void disableHasSystemFeatureCache() {
        mHasSystemFeatureCache.disableLocal();
    }

    public static void invalidateHasSystemFeatureCache() {
        mHasSystemFeatureCache.invalidateCache();
    }

    @Override // android.content.pm.PackageManager
    public int checkPermission(java.lang.String str, java.lang.String str2) {
        return getPermissionManager().checkPackageNamePermission(str, str2, this.mContext.getDeviceId(), getUserId());
    }

    @Override // android.content.pm.PackageManager
    public boolean isPermissionRevokedByPolicy(java.lang.String str, java.lang.String str2) {
        return getPermissionManager().isPermissionRevokedByPolicy(str2, str);
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getPermissionControllerPackageName() {
        if (this.mPermissionsControllerPackageName == null) {
            try {
                this.mPermissionsControllerPackageName = this.mPM.getPermissionControllerPackageName();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mPermissionsControllerPackageName;
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getSdkSandboxPackageName() {
        try {
            return this.mPM.getSdkSandboxPackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean addPermission(android.content.pm.PermissionInfo permissionInfo) {
        return getPermissionManager().addPermission(permissionInfo, false);
    }

    @Override // android.content.pm.PackageManager
    public boolean addPermissionAsync(android.content.pm.PermissionInfo permissionInfo) {
        return getPermissionManager().addPermission(permissionInfo, true);
    }

    @Override // android.content.pm.PackageManager
    public void removePermission(java.lang.String str) {
        getPermissionManager().removePermission(str);
    }

    @Override // android.content.pm.PackageManager
    public void grantRuntimePermission(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        getPermissionManager().grantRuntimePermission(str, str2, userHandle);
    }

    @Override // android.content.pm.PackageManager
    public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        revokeRuntimePermission(str, str2, userHandle, null);
    }

    @Override // android.content.pm.PackageManager
    public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle, java.lang.String str3) {
        getPermissionManager().revokeRuntimePermission(str, str2, userHandle, str3);
    }

    @Override // android.content.pm.PackageManager
    public int getPermissionFlags(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        return getPermissionManager().getPermissionFlags(str2, str, userHandle);
    }

    @Override // android.content.pm.PackageManager
    public void updatePermissionFlags(java.lang.String str, java.lang.String str2, int i, int i2, android.os.UserHandle userHandle) {
        getPermissionManager().updatePermissionFlags(str2, str, i, i2, userHandle);
    }

    @Override // android.content.pm.PackageManager
    public java.util.Set<java.lang.String> getWhitelistedRestrictedPermissions(java.lang.String str, int i) {
        return getPermissionManager().getAllowlistedRestrictedPermissions(str, i);
    }

    @Override // android.content.pm.PackageManager
    public boolean addWhitelistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i) {
        return getPermissionManager().addAllowlistedRestrictedPermission(str, str2, i);
    }

    @Override // android.content.pm.PackageManager
    public boolean setAutoRevokeWhitelisted(java.lang.String str, boolean z) {
        return getPermissionManager().setAutoRevokeExempted(str, z);
    }

    @Override // android.content.pm.PackageManager
    public boolean isAutoRevokeWhitelisted(java.lang.String str) {
        return getPermissionManager().isAutoRevokeExempted(str);
    }

    @Override // android.content.pm.PackageManager
    public boolean removeWhitelistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i) {
        return getPermissionManager().removeAllowlistedRestrictedPermission(str, str2, i);
    }

    @Override // android.content.pm.PackageManager
    public boolean shouldShowRequestPermissionRationale(java.lang.String str) {
        return getPermissionManager().shouldShowRequestPermissionRationale(str);
    }

    @Override // android.content.pm.PackageManager
    public android.content.Intent buildRequestPermissionsIntent(java.lang.String[] strArr) {
        android.content.Intent buildRequestPermissionsIntent = super.buildRequestPermissionsIntent(strArr);
        buildRequestPermissionsIntent.putExtra(android.content.pm.PackageManager.EXTRA_REQUEST_PERMISSIONS_DEVICE_ID, this.mContext.getDeviceId());
        return buildRequestPermissionsIntent;
    }

    @Override // android.content.pm.PackageManager
    public java.lang.CharSequence getBackgroundPermissionOptionLabel() {
        try {
            android.content.Context createPackageContext = this.mContext.createPackageContext(getPermissionControllerPackageName(), 0);
            int identifier = createPackageContext.getResources().getIdentifier(APP_PERMISSION_BUTTON_ALLOW_ALWAYS, "string", PERMISSION_CONTROLLER_RESOURCE_PACKAGE);
            if (identifier != 0) {
                return createPackageContext.getText(identifier);
            }
            return "";
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(TAG, "Permission controller not found.", e);
            return "";
        }
    }

    @Override // android.content.pm.PackageManager
    public int checkSignatures(java.lang.String str, java.lang.String str2) {
        try {
            return this.mPM.checkSignatures(str, str2, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int checkSignatures(int i, int i2) {
        try {
            return this.mPM.checkUidSignatures(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean hasSigningCertificate(java.lang.String str, byte[] bArr, int i) {
        try {
            return this.mPM.hasSigningCertificate(str, bArr, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean hasSigningCertificate(int i, byte[] bArr, int i2) {
        try {
            return this.mPM.hasUidSigningCertificate(i, bArr, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static java.util.List<byte[]> encodeCertificates(java.util.List<java.security.cert.Certificate> list) throws java.security.cert.CertificateEncodingException {
        if (list == null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        for (java.security.cert.Certificate certificate : list) {
            if (!(certificate instanceof java.security.cert.X509Certificate)) {
                throw new java.security.cert.CertificateEncodingException("Only X509 certificates supported.");
            }
            arrayList.add(certificate.getEncoded());
        }
        return arrayList;
    }

    @Override // android.content.pm.PackageManager
    public void requestChecksums(java.lang.String str, boolean z, int i, java.util.List<java.security.cert.Certificate> list, final android.content.pm.PackageManager.OnChecksumsReadyListener onChecksumsReadyListener) throws java.security.cert.CertificateEncodingException, android.content.pm.PackageManager.NameNotFoundException {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(onChecksumsReadyListener);
        java.util.Objects.requireNonNull(list);
        if (list == TRUST_ALL) {
            list = null;
        } else if (list == TRUST_NONE) {
            list = java.util.Collections.emptyList();
        } else if (list.isEmpty()) {
            throw new java.lang.IllegalArgumentException("trustedInstallers has to be one of TRUST_ALL/TRUST_NONE or a non-empty list of certificates.");
        }
        try {
            this.mPM.requestPackageChecksums(str, z, 127, i, encodeCertificates(list), new android.content.pm.IOnChecksumsReadyListener.Stub() { // from class: android.app.ApplicationPackageManager.2
                @Override // android.content.pm.IOnChecksumsReadyListener
                public void onChecksumsReady(java.util.List<android.content.pm.ApkChecksum> list2) throws android.os.RemoteException {
                    onChecksumsReadyListener.onChecksumsReady(list2);
                }
            }, getUserId());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(android.content.pm.PackageManager.NameNotFoundException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    private static class GetPackagesForUidResult {
        private final java.lang.String[] mValue;

        GetPackagesForUidResult(java.lang.String[] strArr) {
            this.mValue = strArr;
        }

        public java.lang.String[] value() {
            return this.mValue;
        }

        public java.lang.String toString() {
            return java.util.Arrays.toString(this.mValue);
        }

        public int hashCode() {
            return java.util.Arrays.hashCode(this.mValue);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.app.ApplicationPackageManager.GetPackagesForUidResult)) {
                return false;
            }
            java.lang.String[] strArr = ((android.app.ApplicationPackageManager.GetPackagesForUidResult) obj).mValue;
            java.lang.String[] strArr2 = this.mValue;
            if ((strArr == null) != (strArr2 == null)) {
                return false;
            }
            if (strArr == null) {
                return true;
            }
            java.util.Arrays.sort(strArr);
            java.util.Arrays.sort(strArr2);
            return java.util.Arrays.equals(strArr2, strArr);
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String[] getPackagesForUid(int i) {
        return mGetPackagesForUidCache.query(java.lang.Integer.valueOf(i)).value();
    }

    public static void disableGetPackagesForUidCache() {
        mGetPackagesForUidCache.disableLocal();
    }

    public static void invalidateGetPackagesForUidCache() {
        android.app.PropertyInvalidatedCache.invalidateCache(CACHE_KEY_PACKAGES_FOR_UID_PROPERTY);
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getNameForUid(int i) {
        try {
            return this.mPM.getNameForUid(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String[] getNamesForUids(int[] iArr) {
        try {
            return this.mPM.getNamesForUids(iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getUidForSharedUser(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            int uidForSharedUser = this.mPM.getUidForSharedUser(str);
            if (uidForSharedUser != -1) {
                return uidForSharedUser;
            }
            throw new android.content.pm.PackageManager.NameNotFoundException("No shared userid for user:" + str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ModuleInfo> getInstalledModules(int i) {
        try {
            return this.mPM.getInstalledModules(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ModuleInfo getModuleInfo(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            android.content.pm.ModuleInfo moduleInfo = this.mPM.getModuleInfo(str, i);
            if (moduleInfo != null) {
                return moduleInfo;
            }
            throw new android.content.pm.PackageManager.NameNotFoundException("No module info for package: " + str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PackageInfo> getInstalledPackages(int i) {
        return getInstalledPackages(android.content.pm.PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PackageInfo> getInstalledPackages(android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) {
        return getInstalledPackagesAsUser(packageInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PackageInfo> getInstalledPackagesAsUser(int i, int i2) {
        return getInstalledPackagesAsUser(android.content.pm.PackageManager.PackageInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PackageInfo> getInstalledPackagesAsUser(android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags, int i) {
        try {
            android.content.pm.ParceledListSlice installedPackages = this.mPM.getInstalledPackages(updateFlagsForPackage(packageInfoFlags.getValue(), i), i);
            if (installedPackages == null) {
                return java.util.Collections.emptyList();
            }
            return installedPackages.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.os.PersistableBundle getAppMetadata(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        android.os.PersistableBundle readFromStream;
        try {
            android.os.ParcelFileDescriptor appMetadataFd = this.mPM.getAppMetadataFd(str, getUserId());
            if (appMetadataFd == null) {
                readFromStream = null;
            } else {
                try {
                    android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(appMetadataFd);
                    try {
                        readFromStream = android.os.PersistableBundle.readFromStream(autoCloseInputStream);
                        autoCloseInputStream.close();
                    } finally {
                    }
                } catch (java.io.IOException e) {
                    throw new java.lang.RuntimeException(e);
                }
            }
            return readFromStream != null ? readFromStream : new android.os.PersistableBundle();
        } catch (android.os.ParcelableException e2) {
            e2.maybeRethrow(android.content.pm.PackageManager.NameNotFoundException.class);
            throw new java.lang.RuntimeException(e2);
        } catch (android.os.RemoteException e3) {
            throw e3.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getAppMetadataSource(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        java.util.Objects.requireNonNull(str, "packageName cannot be null");
        try {
            return this.mPM.getAppMetadataSource(str, getUserId());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(android.content.pm.PackageManager.NameNotFoundException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PackageInfo> getPackagesHoldingPermissions(java.lang.String[] strArr, int i) {
        return getPackagesHoldingPermissions(strArr, android.content.pm.PackageManager.PackageInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PackageInfo> getPackagesHoldingPermissions(java.lang.String[] strArr, android.content.pm.PackageManager.PackageInfoFlags packageInfoFlags) {
        int userId = getUserId();
        try {
            android.content.pm.ParceledListSlice packagesHoldingPermissions = this.mPM.getPackagesHoldingPermissions(strArr, updateFlagsForPackage(packageInfoFlags.getValue(), userId), userId);
            if (packagesHoldingPermissions == null) {
                return java.util.Collections.emptyList();
            }
            return packagesHoldingPermissions.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ApplicationInfo> getInstalledApplications(int i) {
        return getInstalledApplicationsAsUser(i, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ApplicationInfo> getInstalledApplications(android.content.pm.PackageManager.ApplicationInfoFlags applicationInfoFlags) {
        return getInstalledApplicationsAsUser(applicationInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ApplicationInfo> getInstalledApplicationsAsUser(int i, int i2) {
        return getInstalledApplicationsAsUser(android.content.pm.PackageManager.ApplicationInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ApplicationInfo> getInstalledApplicationsAsUser(android.content.pm.PackageManager.ApplicationInfoFlags applicationInfoFlags, int i) {
        try {
            android.content.pm.ParceledListSlice installedApplications = this.mPM.getInstalledApplications(updateFlagsForApplication(applicationInfoFlags.getValue(), i), i);
            if (installedApplications == null) {
                return java.util.Collections.emptyList();
            }
            return installedApplications.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.InstantAppInfo> getInstantApps() {
        try {
            android.content.pm.ParceledListSlice instantApps = this.mPM.getInstantApps(getUserId());
            if (instantApps != null) {
                return instantApps.getList();
            }
            return java.util.Collections.emptyList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getInstantAppIcon(java.lang.String str) {
        try {
            android.graphics.Bitmap instantAppIcon = this.mPM.getInstantAppIcon(str, getUserId());
            if (instantAppIcon == null) {
                return null;
            }
            return new android.graphics.drawable.BitmapDrawable((android.content.res.Resources) null, instantAppIcon);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isInstantApp() {
        return isInstantApp(this.mContext.getPackageName());
    }

    @Override // android.content.pm.PackageManager
    public boolean isInstantApp(java.lang.String str) {
        try {
            return this.mPM.isInstantApp(str, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getInstantAppCookieMaxBytes() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), android.provider.Settings.Global.EPHEMERAL_COOKIE_MAX_SIZE_BYTES, 16384);
    }

    @Override // android.content.pm.PackageManager
    public int getInstantAppCookieMaxSize() {
        return getInstantAppCookieMaxBytes();
    }

    @Override // android.content.pm.PackageManager
    public byte[] getInstantAppCookie() {
        try {
            byte[] instantAppCookie = this.mPM.getInstantAppCookie(this.mContext.getPackageName(), getUserId());
            if (instantAppCookie != null) {
                return instantAppCookie;
            }
            return libcore.util.EmptyArray.BYTE;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void clearInstantAppCookie() {
        updateInstantAppCookie(null);
    }

    @Override // android.content.pm.PackageManager
    public void updateInstantAppCookie(byte[] bArr) {
        if (bArr != null && bArr.length > getInstantAppCookieMaxBytes()) {
            throw new java.lang.IllegalArgumentException("instant cookie longer than " + getInstantAppCookieMaxBytes());
        }
        try {
            this.mPM.setInstantAppCookie(this.mContext.getPackageName(), bArr, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean setInstantAppCookie(byte[] bArr) {
        try {
            return this.mPM.setInstantAppCookie(this.mContext.getPackageName(), bArr, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ResolveInfo resolveActivity(android.content.Intent intent, int i) {
        return resolveActivity(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ResolveInfo resolveActivity(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        return resolveActivityAsUser(intent, resolveInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ResolveInfo resolveActivityAsUser(android.content.Intent intent, int i, int i2) {
        return resolveActivityAsUser(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ResolveInfo resolveActivityAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        try {
            return this.mPM.resolveIntent(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), updateFlagsForComponent(resolveInfoFlags.getValue(), i, intent), i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentActivities(android.content.Intent intent, int i) {
        return queryIntentActivities(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentActivities(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        return queryIntentActivitiesAsUser(intent, resolveInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesAsUser(android.content.Intent intent, int i, int i2) {
        return queryIntentActivitiesAsUser(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        try {
            android.content.pm.ParceledListSlice queryIntentActivities = this.mPM.queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), updateFlagsForComponent(resolveInfoFlags.getValue(), i, intent), i);
            if (queryIntentActivities == null) {
                return java.util.Collections.emptyList();
            }
            return queryIntentActivities.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentActivityOptions(android.content.ComponentName componentName, android.content.Intent[] intentArr, android.content.Intent intent, int i) {
        return queryIntentActivityOptions(componentName, intentArr == null ? null : new java.util.ArrayList(java.util.Arrays.asList(intentArr)), intent, android.content.pm.PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentActivityOptions(android.content.ComponentName componentName, java.util.List<android.content.Intent> list, android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        java.lang.String[] strArr;
        java.lang.String resolveTypeIfNeeded;
        int userId = getUserId();
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        android.content.Intent[] intentArr = null;
        if (list == null) {
            strArr = null;
        } else {
            int size = list.size();
            java.lang.String[] strArr2 = null;
            for (int i = 0; i < size; i++) {
                android.content.Intent intent2 = list.get(i);
                if (intent2 != null && (resolveTypeIfNeeded = intent2.resolveTypeIfNeeded(contentResolver)) != null) {
                    if (strArr2 == null) {
                        strArr2 = new java.lang.String[size];
                    }
                    strArr2[i] = resolveTypeIfNeeded;
                }
            }
            strArr = strArr2;
        }
        try {
            android.content.pm.IPackageManager iPackageManager = this.mPM;
            if (list != null) {
                intentArr = (android.content.Intent[]) list.toArray(new android.content.Intent[0]);
            }
            android.content.pm.ParceledListSlice queryIntentActivityOptions = iPackageManager.queryIntentActivityOptions(componentName, intentArr, strArr, intent, intent.resolveTypeIfNeeded(contentResolver), updateFlagsForComponent(resolveInfoFlags.getValue(), userId, intent), userId);
            if (queryIntentActivityOptions == null) {
                return java.util.Collections.emptyList();
            }
            return queryIntentActivityOptions.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceiversAsUser(android.content.Intent intent, int i, int i2) {
        return queryBroadcastReceiversAsUser(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceiversAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        try {
            android.content.pm.ParceledListSlice queryIntentReceivers = this.mPM.queryIntentReceivers(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), updateFlagsForComponent(resolveInfoFlags.getValue(), i, intent), i);
            if (queryIntentReceivers == null) {
                return java.util.Collections.emptyList();
            }
            return queryIntentReceivers.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceivers(android.content.Intent intent, int i) {
        return queryBroadcastReceivers(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceivers(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        return queryBroadcastReceiversAsUser(intent, resolveInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ResolveInfo resolveServiceAsUser(android.content.Intent intent, int i, int i2) {
        return resolveServiceAsUser(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ResolveInfo resolveServiceAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        try {
            return this.mPM.resolveService(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), updateFlagsForComponent(resolveInfoFlags.getValue(), i, intent), i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ResolveInfo resolveService(android.content.Intent intent, int i) {
        return resolveService(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ResolveInfo resolveService(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        return resolveServiceAsUser(intent, resolveInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentServicesAsUser(android.content.Intent intent, int i, int i2) {
        return queryIntentServicesAsUser(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentServicesAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        try {
            android.content.pm.ParceledListSlice queryIntentServices = this.mPM.queryIntentServices(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), updateFlagsForComponent(resolveInfoFlags.getValue(), i, intent), i);
            if (queryIntentServices == null) {
                return java.util.Collections.emptyList();
            }
            return queryIntentServices.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentServices(android.content.Intent intent, int i) {
        return queryIntentServices(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentServices(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        return queryIntentServicesAsUser(intent, resolveInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentContentProvidersAsUser(android.content.Intent intent, int i, int i2) {
        return queryIntentContentProvidersAsUser(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentContentProvidersAsUser(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags, int i) {
        try {
            android.content.pm.ParceledListSlice queryIntentContentProviders = this.mPM.queryIntentContentProviders(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), updateFlagsForComponent(resolveInfoFlags.getValue(), i, intent), i);
            if (queryIntentContentProviders == null) {
                return java.util.Collections.emptyList();
            }
            return queryIntentContentProviders.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentContentProviders(android.content.Intent intent, int i) {
        return queryIntentContentProviders(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ResolveInfo> queryIntentContentProviders(android.content.Intent intent, android.content.pm.PackageManager.ResolveInfoFlags resolveInfoFlags) {
        return queryIntentContentProvidersAsUser(intent, resolveInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ProviderInfo resolveContentProvider(java.lang.String str, int i) {
        return resolveContentProvider(str, android.content.pm.PackageManager.ComponentInfoFlags.of(i));
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ProviderInfo resolveContentProvider(java.lang.String str, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags) {
        return resolveContentProviderAsUser(str, componentInfoFlags, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ProviderInfo resolveContentProviderAsUser(java.lang.String str, int i, int i2) {
        return resolveContentProviderAsUser(str, android.content.pm.PackageManager.ComponentInfoFlags.of(i), i2);
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ProviderInfo resolveContentProviderAsUser(java.lang.String str, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags, int i) {
        try {
            return this.mPM.resolveContentProvider(str, updateFlagsForComponent(componentInfoFlags.getValue(), i, null), i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ProviderInfo> queryContentProviders(java.lang.String str, int i, int i2) {
        return queryContentProviders(str, i, android.content.pm.PackageManager.ComponentInfoFlags.of(i2));
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ProviderInfo> queryContentProviders(java.lang.String str, int i, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags) {
        return queryContentProviders(str, i, componentInfoFlags, (java.lang.String) null);
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ProviderInfo> queryContentProviders(java.lang.String str, int i, int i2, java.lang.String str2) {
        return queryContentProviders(str, i, android.content.pm.PackageManager.ComponentInfoFlags.of(i2), str2);
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.ProviderInfo> queryContentProviders(java.lang.String str, int i, android.content.pm.PackageManager.ComponentInfoFlags componentInfoFlags, java.lang.String str2) {
        try {
            android.content.pm.ParceledListSlice queryContentProviders = this.mPM.queryContentProviders(str, i, updateFlagsForComponent(componentInfoFlags.getValue(), android.os.UserHandle.getUserId(i), null), str2);
            return queryContentProviders != null ? queryContentProviders.getList() : java.util.Collections.emptyList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.InstrumentationInfo getInstrumentationInfo(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            android.content.pm.InstrumentationInfo instrumentationInfoAsUser = this.mPM.getInstrumentationInfoAsUser(componentName, i, getUserId());
            if (instrumentationInfoAsUser != null) {
                return instrumentationInfoAsUser;
            }
            throw new android.content.pm.PackageManager.NameNotFoundException(componentName.toString());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.InstrumentationInfo> queryInstrumentation(java.lang.String str, int i) {
        try {
            android.content.pm.ParceledListSlice queryInstrumentationAsUser = this.mPM.queryInstrumentationAsUser(str, i, getUserId());
            if (queryInstrumentationAsUser == null) {
                return java.util.Collections.emptyList();
            }
            return queryInstrumentationAsUser.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getDrawable(java.lang.String str, int i, android.content.pm.ApplicationInfo applicationInfo) {
        android.app.ApplicationPackageManager.ResourceName resourceName = new android.app.ApplicationPackageManager.ResourceName(str, i);
        android.graphics.drawable.Drawable cachedIcon = getCachedIcon(resourceName);
        if (cachedIcon != null) {
            return cachedIcon;
        }
        if (applicationInfo == null) {
            try {
                applicationInfo = getApplicationInfo(str, 1024);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return null;
            }
        }
        if (i != 0) {
            try {
                android.graphics.drawable.Drawable drawable = getResourcesForApplication(applicationInfo).getDrawable(i, null);
                if (drawable != null) {
                    putCachedIcon(resourceName, drawable);
                }
                return drawable;
            } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                android.util.Log.w("PackageManager", "Failure retrieving resources for " + applicationInfo.packageName);
            } catch (android.content.res.Resources.NotFoundException e3) {
                android.util.Log.w("PackageManager", "Failure retrieving resources for " + applicationInfo.packageName + ": " + e3.getMessage());
            } catch (java.lang.Exception e4) {
                android.util.Log.w("PackageManager", "Failure retrieving icon 0x" + java.lang.Integer.toHexString(i) + " in package " + str, e4);
            }
        }
        return null;
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getActivityIcon(android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        return getActivityInfo(componentName, 1024).loadIcon(this);
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getActivityIcon(android.content.Intent intent) throws android.content.pm.PackageManager.NameNotFoundException {
        if (intent.getComponent() != null) {
            return getActivityIcon(intent.getComponent());
        }
        android.content.pm.ResolveInfo resolveActivity = resolveActivity(intent, 65536);
        if (resolveActivity != null) {
            return resolveActivity.activityInfo.loadIcon(this);
        }
        throw new android.content.pm.PackageManager.NameNotFoundException(intent.toUri(0));
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getDefaultActivityIcon() {
        return this.mContext.getDrawable(17301651);
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getApplicationIcon(android.content.pm.ApplicationInfo applicationInfo) {
        return applicationInfo.loadIcon(this);
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getApplicationIcon(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        return getApplicationIcon(getApplicationInfo(str, 1024));
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getActivityBanner(android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        return getActivityInfo(componentName, 1024).loadBanner(this);
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getActivityBanner(android.content.Intent intent) throws android.content.pm.PackageManager.NameNotFoundException {
        if (intent.getComponent() != null) {
            return getActivityBanner(intent.getComponent());
        }
        android.content.pm.ResolveInfo resolveActivity = resolveActivity(intent, 65536);
        if (resolveActivity != null) {
            return resolveActivity.activityInfo.loadBanner(this);
        }
        throw new android.content.pm.PackageManager.NameNotFoundException(intent.toUri(0));
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getApplicationBanner(android.content.pm.ApplicationInfo applicationInfo) {
        return applicationInfo.loadBanner(this);
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getApplicationBanner(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        return getApplicationBanner(getApplicationInfo(str, 1024));
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getActivityLogo(android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        return getActivityInfo(componentName, 1024).loadLogo(this);
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getActivityLogo(android.content.Intent intent) throws android.content.pm.PackageManager.NameNotFoundException {
        if (intent.getComponent() != null) {
            return getActivityLogo(intent.getComponent());
        }
        android.content.pm.ResolveInfo resolveActivity = resolveActivity(intent, 65536);
        if (resolveActivity != null) {
            return resolveActivity.activityInfo.loadLogo(this);
        }
        throw new android.content.pm.PackageManager.NameNotFoundException(intent.toUri(0));
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getApplicationLogo(android.content.pm.ApplicationInfo applicationInfo) {
        return applicationInfo.loadLogo(this);
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getApplicationLogo(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        return getApplicationLogo(getApplicationInfo(str, 1024));
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getUserBadgedIcon(android.graphics.drawable.Drawable drawable, final android.os.UserHandle userHandle) {
        if (!hasUserBadge(userHandle.getIdentifier())) {
            return drawable;
        }
        return getBadgedDrawable(drawable, new android.util.LauncherIcons(this.mContext).getBadgeDrawable(getDevicePolicyManager().getResources().getDrawable(getUpdatableUserIconBadgeId(userHandle), android.app.admin.DevicePolicyResources.Drawables.Style.SOLID_COLORED, new java.util.function.Supplier() { // from class: android.app.ApplicationPackageManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.graphics.drawable.Drawable lambda$getUserBadgedIcon$0;
                lambda$getUserBadgedIcon$0 = android.app.ApplicationPackageManager.this.lambda$getUserBadgedIcon$0(userHandle);
                return lambda$getUserBadgedIcon$0;
            }
        }), getUserBadgeColor(userHandle, false)), null, true);
    }

    private java.lang.String getUpdatableUserIconBadgeId(android.os.UserHandle userHandle) {
        return getUserManager().isManagedProfile(userHandle.getIdentifier()) ? android.app.admin.DevicePolicyResources.Drawables.WORK_PROFILE_ICON_BADGE : android.app.admin.DevicePolicyResources.UNDEFINED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getDefaultUserIconBadge, reason: merged with bridge method [inline-methods] */
    public android.graphics.drawable.Drawable lambda$getUserBadgedIcon$0(android.os.UserHandle userHandle) {
        return this.mContext.getDrawable(getUserManager().getUserIconBadgeResId(userHandle.getIdentifier()));
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getUserBadgedDrawableForDensity(android.graphics.drawable.Drawable drawable, android.os.UserHandle userHandle, android.graphics.Rect rect, int i) {
        android.graphics.drawable.Drawable userBadgeForDensity = getUserBadgeForDensity(userHandle, i);
        if (userBadgeForDensity == null) {
            return drawable;
        }
        return getBadgedDrawable(drawable, userBadgeForDensity, rect, true);
    }

    private int getUserBadgeColor(android.os.UserHandle userHandle, boolean z) {
        if (z && this.mContext.getResources().getConfiguration().isNightModeActive()) {
            return getUserManager().getUserBadgeDarkColor(userHandle.getIdentifier());
        }
        return getUserManager().getUserBadgeColor(userHandle.getIdentifier());
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getUserBadgeForDensity(final android.os.UserHandle userHandle, final int i) {
        android.graphics.drawable.Drawable profileIconForDensity = getProfileIconForDensity(userHandle, com.android.internal.R.drawable.ic_corp_badge_color, i);
        if (profileIconForDensity == null) {
            return null;
        }
        android.graphics.drawable.Drawable drawableForDensity = getDevicePolicyManager().getResources().getDrawableForDensity(getUpdatableUserBadgeId(userHandle), android.app.admin.DevicePolicyResources.Drawables.Style.SOLID_COLORED, i, new java.util.function.Supplier() { // from class: android.app.ApplicationPackageManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.graphics.drawable.Drawable lambda$getUserBadgeForDensity$1;
                lambda$getUserBadgeForDensity$1 = android.app.ApplicationPackageManager.this.lambda$getUserBadgeForDensity$1(userHandle, i);
                return lambda$getUserBadgeForDensity$1;
            }
        });
        drawableForDensity.setTint(getUserBadgeColor(userHandle, false));
        return new android.graphics.drawable.LayerDrawable(new android.graphics.drawable.Drawable[]{profileIconForDensity, drawableForDensity});
    }

    private java.lang.String getUpdatableUserBadgeId(android.os.UserHandle userHandle) {
        return getUserManager().isManagedProfile(userHandle.getIdentifier()) ? android.app.admin.DevicePolicyResources.Drawables.WORK_PROFILE_ICON : android.app.admin.DevicePolicyResources.UNDEFINED;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getDefaultUserBadgeForDensity, reason: merged with bridge method [inline-methods] */
    public android.graphics.drawable.Drawable lambda$getUserBadgeForDensity$1(android.os.UserHandle userHandle, int i) {
        return getDrawableForDensity(getUserManager().getUserBadgeResId(userHandle.getIdentifier()), i);
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable getUserBadgeForDensityNoBackground(final android.os.UserHandle userHandle, final int i) {
        if (!hasUserBadge(userHandle.getIdentifier())) {
            return null;
        }
        android.graphics.drawable.Drawable drawableForDensity = getDevicePolicyManager().getResources().getDrawableForDensity(getUpdatableUserBadgeId(userHandle), android.app.admin.DevicePolicyResources.Drawables.Style.SOLID_NOT_COLORED, i, new java.util.function.Supplier() { // from class: android.app.ApplicationPackageManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.graphics.drawable.Drawable lambda$getUserBadgeForDensityNoBackground$2;
                lambda$getUserBadgeForDensityNoBackground$2 = android.app.ApplicationPackageManager.this.lambda$getUserBadgeForDensityNoBackground$2(userHandle, i);
                return lambda$getUserBadgeForDensityNoBackground$2;
            }
        });
        if (drawableForDensity != null) {
            drawableForDensity.setTint(getUserBadgeColor(userHandle, true));
        }
        return drawableForDensity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getDefaultUserBadgeNoBackgroundForDensity, reason: merged with bridge method [inline-methods] */
    public android.graphics.drawable.Drawable lambda$getUserBadgeForDensityNoBackground$2(android.os.UserHandle userHandle, int i) {
        return getDrawableForDensity(getUserManager().getUserBadgeNoBackgroundResId(userHandle.getIdentifier()), i);
    }

    private android.graphics.drawable.Drawable getDrawableForDensity(int i, int i2) {
        if (i2 <= 0) {
            i2 = this.mContext.getResources().getDisplayMetrics().densityDpi;
        }
        return this.mContext.getResources().getDrawableForDensity(i, i2);
    }

    private android.graphics.drawable.Drawable getProfileIconForDensity(android.os.UserHandle userHandle, int i, int i2) {
        if (hasUserBadge(userHandle.getIdentifier())) {
            return getDrawableForDensity(i, i2);
        }
        return null;
    }

    @Override // android.content.pm.PackageManager
    public java.lang.CharSequence getUserBadgedLabel(java.lang.CharSequence charSequence, android.os.UserHandle userHandle) {
        return getUserManager().getBadgedLabelForUser(charSequence, userHandle);
    }

    @Override // android.content.pm.PackageManager
    public android.content.res.Resources getResourcesForActivity(android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        return getResourcesForApplication(getActivityInfo(componentName, 1024).applicationInfo);
    }

    @Override // android.content.pm.PackageManager
    public android.content.res.Resources getResourcesForApplication(android.content.pm.ApplicationInfo applicationInfo) throws android.content.pm.PackageManager.NameNotFoundException {
        return getResourcesForApplication(applicationInfo, null);
    }

    @Override // android.content.pm.PackageManager
    public android.content.res.Resources getResourcesForApplication(android.content.pm.ApplicationInfo applicationInfo, android.content.res.Configuration configuration) throws android.content.pm.PackageManager.NameNotFoundException {
        if (applicationInfo.packageName.equals("system")) {
            android.content.Context systemUiContext = this.mContext.mMainThread.getSystemUiContext();
            if (configuration != null) {
                systemUiContext = systemUiContext.createConfigurationContext(configuration);
            }
            return systemUiContext.getResources();
        }
        boolean z = applicationInfo.uid == android.os.Process.myUid();
        android.content.res.Resources topLevelResources = this.mContext.mMainThread.getTopLevelResources(z ? applicationInfo.sourceDir : applicationInfo.publicSourceDir, z ? applicationInfo.splitSourceDirs : applicationInfo.splitPublicSourceDirs, applicationInfo.resourceDirs, applicationInfo.overlayPaths, applicationInfo.sharedLibraryFiles, this.mContext.mPackageInfo, configuration);
        if (topLevelResources != null) {
            return topLevelResources;
        }
        throw new android.content.pm.PackageManager.NameNotFoundException("Unable to open " + applicationInfo.publicSourceDir);
    }

    @Override // android.content.pm.PackageManager
    public android.content.res.Resources getResourcesForApplication(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        return getResourcesForApplication(getApplicationInfo(str, 1024));
    }

    @Override // android.content.pm.PackageManager
    public android.content.res.Resources getResourcesForApplicationAsUser(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Call does not support special user #" + i);
        }
        if ("system".equals(str)) {
            return this.mContext.mMainThread.getSystemUiContext().getResources();
        }
        try {
            android.content.pm.ApplicationInfo applicationInfo = this.mPM.getApplicationInfo(str, 1024L, i);
            if (applicationInfo != null) {
                return getResourcesForApplication(applicationInfo);
            }
            throw new android.content.pm.PackageManager.NameNotFoundException("Package " + str + " doesn't exist");
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isSafeMode() {
        try {
            if (this.mCachedSafeMode < 0) {
                this.mCachedSafeMode = this.mPM.isSafeMode() ? 1 : 0;
            }
            return this.mCachedSafeMode != 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void addOnPermissionsChangeListener(android.content.pm.PackageManager.OnPermissionsChangedListener onPermissionsChangedListener) {
        getPermissionManager().addOnPermissionsChangeListener(onPermissionsChangedListener);
    }

    @Override // android.content.pm.PackageManager
    public void removeOnPermissionsChangeListener(android.content.pm.PackageManager.OnPermissionsChangedListener onPermissionsChangedListener) {
        getPermissionManager().removeOnPermissionsChangeListener(onPermissionsChangedListener);
    }

    static void configurationChanged() {
        synchronized (sSync) {
            sIconCache.clear();
            sStringCache.clear();
        }
    }

    protected ApplicationPackageManager(android.app.ContextImpl contextImpl, android.content.pm.IPackageManager iPackageManager) {
        this.mContext = contextImpl;
        this.mPM = iPackageManager;
    }

    private long updateFlagsForPackage(long j, int i) {
        if ((15 & j) != 0 && (269221888 & j) == 0) {
            onImplicitDirectBoot(i);
        }
        return j;
    }

    private long updateFlagsForApplication(long j, int i) {
        return updateFlagsForPackage(j, i);
    }

    private long updateFlagsForComponent(long j, int i, android.content.Intent intent) {
        if (intent != null && (intent.getFlags() & 256) != 0) {
            j |= 268435456;
        }
        if ((269221888 & j) == 0) {
            onImplicitDirectBoot(i);
        }
        return j;
    }

    private void onImplicitDirectBoot(int i) {
        if (android.os.StrictMode.vmImplicitDirectBootEnabled()) {
            if (i == android.os.UserHandle.myUserId()) {
                if (this.mUserUnlocked) {
                    return;
                }
                if (((android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class)).isUserUnlockingOrUnlocked(i)) {
                    this.mUserUnlocked = true;
                    return;
                } else {
                    android.os.StrictMode.onImplicitDirectBoot();
                    return;
                }
            }
            if (!((android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class)).isUserUnlockingOrUnlocked(i)) {
                android.os.StrictMode.onImplicitDirectBoot();
            }
        }
    }

    private android.graphics.drawable.Drawable getCachedIcon(android.app.ApplicationPackageManager.ResourceName resourceName) {
        synchronized (sSync) {
            java.lang.ref.WeakReference<android.graphics.drawable.Drawable.ConstantState> weakReference = sIconCache.get(resourceName);
            if (weakReference != null) {
                android.graphics.drawable.Drawable.ConstantState constantState = weakReference.get();
                if (constantState != null) {
                    return constantState.newDrawable();
                }
                sIconCache.remove(resourceName);
            }
            return null;
        }
    }

    private void putCachedIcon(android.app.ApplicationPackageManager.ResourceName resourceName, android.graphics.drawable.Drawable drawable) {
        synchronized (sSync) {
            sIconCache.put(resourceName, new java.lang.ref.WeakReference<>(drawable.getConstantState()));
        }
    }

    static void handlePackageBroadcast(int i, java.lang.String[] strArr, boolean z) {
        boolean z2;
        if (i != 1) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (strArr != null && strArr.length > 0) {
            boolean z3 = false;
            for (java.lang.String str : strArr) {
                synchronized (sSync) {
                    for (int size = sIconCache.size() - 1; size >= 0; size--) {
                        if (sIconCache.keyAt(size).packageName.equals(str)) {
                            sIconCache.removeAt(size);
                            z3 = true;
                        }
                    }
                    for (int size2 = sStringCache.size() - 1; size2 >= 0; size2--) {
                        if (sStringCache.keyAt(size2).packageName.equals(str)) {
                            sStringCache.removeAt(size2);
                            z3 = true;
                        }
                    }
                }
            }
            if (z3 || z) {
                if (z2) {
                    java.lang.Runtime.getRuntime().gc();
                } else {
                    android.app.ActivityThread.currentActivityThread().scheduleGcIdler();
                }
            }
        }
    }

    private static final class ResourceName {
        final int iconId;
        final java.lang.String packageName;

        ResourceName(java.lang.String str, int i) {
            this.packageName = str;
            this.iconId = i;
        }

        ResourceName(android.content.pm.ApplicationInfo applicationInfo, int i) {
            this(applicationInfo.packageName, i);
        }

        ResourceName(android.content.pm.ComponentInfo componentInfo, int i) {
            this(componentInfo.applicationInfo.packageName, i);
        }

        ResourceName(android.content.pm.ResolveInfo resolveInfo, int i) {
            this(resolveInfo.activityInfo.applicationInfo.packageName, i);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.app.ApplicationPackageManager.ResourceName resourceName = (android.app.ApplicationPackageManager.ResourceName) obj;
            if (this.iconId != resourceName.iconId) {
                return false;
            }
            if (this.packageName != null) {
                if (this.packageName.equals(resourceName.packageName)) {
                    return true;
                }
            } else if (resourceName.packageName == null) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.packageName.hashCode() * 31) + this.iconId;
        }

        public java.lang.String toString() {
            return "{ResourceName " + this.packageName + " / " + this.iconId + "}";
        }
    }

    private java.lang.CharSequence getCachedString(android.app.ApplicationPackageManager.ResourceName resourceName) {
        synchronized (sSync) {
            java.lang.ref.WeakReference<java.lang.CharSequence> weakReference = sStringCache.get(resourceName);
            if (weakReference != null) {
                java.lang.CharSequence charSequence = weakReference.get();
                if (charSequence != null) {
                    return charSequence;
                }
                sStringCache.remove(resourceName);
            }
            return null;
        }
    }

    private void putCachedString(android.app.ApplicationPackageManager.ResourceName resourceName, java.lang.CharSequence charSequence) {
        synchronized (sSync) {
            sStringCache.put(resourceName, new java.lang.ref.WeakReference<>(charSequence));
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.CharSequence getText(java.lang.String str, int i, android.content.pm.ApplicationInfo applicationInfo) {
        android.app.ApplicationPackageManager.ResourceName resourceName = new android.app.ApplicationPackageManager.ResourceName(str, i);
        java.lang.CharSequence cachedString = getCachedString(resourceName);
        if (cachedString != null) {
            return cachedString;
        }
        if (applicationInfo == null) {
            try {
                applicationInfo = getApplicationInfo(str, 1024);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return null;
            }
        }
        try {
            java.lang.CharSequence text = getResourcesForApplication(applicationInfo).getText(i);
            putCachedString(resourceName, text);
            return text;
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            android.util.Log.w("PackageManager", "Failure retrieving resources for " + applicationInfo.packageName);
            return null;
        } catch (java.lang.RuntimeException e3) {
            android.util.Log.w("PackageManager", "Failure retrieving text 0x" + java.lang.Integer.toHexString(i) + " in package " + str, e3);
            return null;
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.res.XmlResourceParser getXml(java.lang.String str, int i, android.content.pm.ApplicationInfo applicationInfo) {
        if (applicationInfo == null) {
            try {
                applicationInfo = getApplicationInfo(str, 1024);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return null;
            }
        }
        try {
            return getResourcesForApplication(applicationInfo).getXml(i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            android.util.Log.w("PackageManager", "Failure retrieving resources for " + applicationInfo.packageName);
            return null;
        } catch (java.lang.RuntimeException e3) {
            android.util.Log.w("PackageManager", "Failure retrieving xml 0x" + java.lang.Integer.toHexString(i) + " in package " + str, e3);
            return null;
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.CharSequence getApplicationLabel(android.content.pm.ApplicationInfo applicationInfo) {
        return applicationInfo.loadLabel(this);
    }

    @Override // android.content.pm.PackageManager
    public int installExistingPackage(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        return installExistingPackage(str, 0);
    }

    @Override // android.content.pm.PackageManager
    public int installExistingPackage(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return installExistingPackageAsUser(str, i, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public int installExistingPackageAsUser(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return installExistingPackageAsUser(str, 0, i);
    }

    private int installExistingPackageAsUser(java.lang.String str, int i, int i2) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            int installExistingPackageAsUser = this.mPM.installExistingPackageAsUser(str, i2, 4194304, i, null);
            if (installExistingPackageAsUser == -3) {
                throw new android.content.pm.PackageManager.NameNotFoundException("Package " + str + " doesn't exist");
            }
            return installExistingPackageAsUser;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void verifyPendingInstall(int i, int i2) {
        try {
            this.mPM.verifyPendingInstall(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void extendVerificationTimeout(int i, int i2, long j) {
        try {
            this.mPM.extendVerificationTimeout(i, i2, j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void verifyIntentFilter(int i, int i2, java.util.List<java.lang.String> list) {
        try {
            this.mPM.verifyIntentFilter(i, i2, list);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getIntentVerificationStatusAsUser(java.lang.String str, int i) {
        try {
            return this.mPM.getIntentVerificationStatus(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean updateIntentVerificationStatusAsUser(java.lang.String str, int i, int i2) {
        try {
            return this.mPM.updateIntentVerificationStatus(str, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.IntentFilterVerificationInfo> getIntentFilterVerifications(java.lang.String str) {
        try {
            android.content.pm.ParceledListSlice intentFilterVerifications = this.mPM.getIntentFilterVerifications(str);
            if (intentFilterVerifications == null) {
                return java.util.Collections.emptyList();
            }
            return intentFilterVerifications.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.IntentFilter> getAllIntentFilters(java.lang.String str) {
        try {
            android.content.pm.ParceledListSlice allIntentFilters = this.mPM.getAllIntentFilters(str);
            if (allIntentFilters == null) {
                return java.util.Collections.emptyList();
            }
            return allIntentFilters.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getDefaultBrowserPackageNameAsUser(int i) {
        return ((android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class)).getBrowserRoleHolder(i);
    }

    @Override // android.content.pm.PackageManager
    public boolean setDefaultBrowserPackageNameAsUser(java.lang.String str, int i) {
        return ((android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class)).setBrowserRoleHolder(str, i);
    }

    @Override // android.content.pm.PackageManager
    public void setInstallerPackageName(java.lang.String str, java.lang.String str2) {
        try {
            this.mPM.setInstallerPackageName(str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setUpdateAvailable(java.lang.String str, boolean z) {
        try {
            this.mPM.setUpdateAvailable(str, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getInstallerPackageName(java.lang.String str) {
        try {
            return this.mPM.getInstallerPackageName(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.InstallSourceInfo getInstallSourceInfo(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            android.content.pm.InstallSourceInfo installSourceInfo = this.mPM.getInstallSourceInfo(str, getUserId());
            if (installSourceInfo == null) {
                throw new android.content.pm.PackageManager.NameNotFoundException(str);
            }
            return installSourceInfo;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isAppArchivable(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            java.util.Objects.requireNonNull(str);
            return this.mPM.isAppArchivable(str, new android.os.UserHandle(getUserId()));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getMoveStatus(int i) {
        try {
            return this.mPM.getMoveStatus(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void registerMoveCallback(android.content.pm.PackageManager.MoveCallback moveCallback, android.os.Handler handler) {
        synchronized (this.mDelegates) {
            android.app.ApplicationPackageManager.MoveCallbackDelegate moveCallbackDelegate = new android.app.ApplicationPackageManager.MoveCallbackDelegate(moveCallback, handler.getLooper());
            try {
                this.mPM.registerMoveCallback(moveCallbackDelegate);
                this.mDelegates.add(moveCallbackDelegate);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Override // android.content.pm.PackageManager
    public void unregisterMoveCallback(android.content.pm.PackageManager.MoveCallback moveCallback) {
        synchronized (this.mDelegates) {
            java.util.Iterator<android.app.ApplicationPackageManager.MoveCallbackDelegate> it = this.mDelegates.iterator();
            while (it.hasNext()) {
                android.app.ApplicationPackageManager.MoveCallbackDelegate next = it.next();
                if (next.mCallback == moveCallback) {
                    try {
                        this.mPM.unregisterMoveCallback(next);
                        it.remove();
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    @Override // android.content.pm.PackageManager
    public int movePackage(java.lang.String str, android.os.storage.VolumeInfo volumeInfo) {
        java.lang.String str2;
        try {
            if (android.os.storage.VolumeInfo.ID_PRIVATE_INTERNAL.equals(volumeInfo.id)) {
                str2 = android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL;
            } else if (volumeInfo.isPrimaryPhysical()) {
                str2 = android.os.storage.StorageManager.UUID_PRIMARY_PHYSICAL;
            } else {
                str2 = (java.lang.String) java.util.Objects.requireNonNull(volumeInfo.fsUuid);
            }
            return this.mPM.movePackage(str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.os.storage.VolumeInfo getPackageCurrentVolume(android.content.pm.ApplicationInfo applicationInfo) {
        return getPackageCurrentVolume(applicationInfo, (android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class));
    }

    protected android.os.storage.VolumeInfo getPackageCurrentVolume(android.content.pm.ApplicationInfo applicationInfo, android.os.storage.StorageManager storageManager) {
        if (applicationInfo.isInternal()) {
            return storageManager.findVolumeById(android.os.storage.VolumeInfo.ID_PRIVATE_INTERNAL);
        }
        return storageManager.findVolumeByUuid(applicationInfo.volumeUuid);
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.os.storage.VolumeInfo> getPackageCandidateVolumes(android.content.pm.ApplicationInfo applicationInfo) {
        return getPackageCandidateVolumes(applicationInfo, (android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class), this.mPM);
    }

    protected java.util.List<android.os.storage.VolumeInfo> getPackageCandidateVolumes(android.content.pm.ApplicationInfo applicationInfo, android.os.storage.StorageManager storageManager, android.content.pm.IPackageManager iPackageManager) {
        android.os.storage.VolumeInfo packageCurrentVolume = getPackageCurrentVolume(applicationInfo, storageManager);
        java.util.List<android.os.storage.VolumeInfo> volumes = storageManager.getVolumes();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.os.storage.VolumeInfo volumeInfo : volumes) {
            if (java.util.Objects.equals(volumeInfo, packageCurrentVolume) || isPackageCandidateVolume(this.mContext, applicationInfo, volumeInfo, iPackageManager)) {
                arrayList.add(volumeInfo);
            }
        }
        return arrayList;
    }

    protected boolean isForceAllowOnExternal(android.content.Context context) {
        return android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.FORCE_ALLOW_ON_EXTERNAL, 0) != 0;
    }

    protected boolean isAllow3rdPartyOnInternal(android.content.Context context) {
        return context.getResources().getBoolean(com.android.internal.R.bool.config_allow3rdPartyAppOnInternal);
    }

    private boolean isPackageCandidateVolume(android.app.ContextImpl contextImpl, android.content.pm.ApplicationInfo applicationInfo, android.os.storage.VolumeInfo volumeInfo, android.content.pm.IPackageManager iPackageManager) {
        boolean isForceAllowOnExternal = isForceAllowOnExternal(contextImpl);
        if (android.os.storage.VolumeInfo.ID_PRIVATE_INTERNAL.equals(volumeInfo.getId())) {
            return applicationInfo.isSystemApp() || isAllow3rdPartyOnInternal(contextImpl);
        }
        if (applicationInfo.isSystemApp()) {
            return false;
        }
        if ((!isForceAllowOnExternal && (applicationInfo.installLocation == 1 || applicationInfo.installLocation == -1)) || !volumeInfo.isMountedWritable()) {
            return false;
        }
        if (volumeInfo.isPrimaryPhysical()) {
            return applicationInfo.isInternal();
        }
        try {
            return !iPackageManager.isPackageDeviceAdminOnAnyUser(applicationInfo.packageName) && volumeInfo.getType() == 1;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int movePrimaryStorage(android.os.storage.VolumeInfo volumeInfo) {
        java.lang.String str;
        try {
            if (android.os.storage.VolumeInfo.ID_PRIVATE_INTERNAL.equals(volumeInfo.id)) {
                str = android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL;
            } else if (volumeInfo.isPrimaryPhysical()) {
                str = android.os.storage.StorageManager.UUID_PRIMARY_PHYSICAL;
            } else {
                str = (java.lang.String) java.util.Objects.requireNonNull(volumeInfo.fsUuid);
            }
            return this.mPM.movePrimaryStorage(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.os.storage.VolumeInfo getPrimaryStorageCurrentVolume() {
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class);
        return storageManager.findVolumeByQualifiedUuid(storageManager.getPrimaryStorageUuid());
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.os.storage.VolumeInfo> getPrimaryStorageCandidateVolumes() {
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class);
        android.os.storage.VolumeInfo primaryStorageCurrentVolume = getPrimaryStorageCurrentVolume();
        java.util.List<android.os.storage.VolumeInfo> volumes = storageManager.getVolumes();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (java.util.Objects.equals(android.os.storage.StorageManager.UUID_PRIMARY_PHYSICAL, storageManager.getPrimaryStorageUuid()) && primaryStorageCurrentVolume != null) {
            arrayList.add(primaryStorageCurrentVolume);
        } else {
            for (android.os.storage.VolumeInfo volumeInfo : volumes) {
                if (java.util.Objects.equals(volumeInfo, primaryStorageCurrentVolume) || isPrimaryStorageCandidateVolume(volumeInfo)) {
                    arrayList.add(volumeInfo);
                }
            }
        }
        return arrayList;
    }

    private static boolean isPrimaryStorageCandidateVolume(android.os.storage.VolumeInfo volumeInfo) {
        if (android.os.storage.VolumeInfo.ID_PRIVATE_INTERNAL.equals(volumeInfo.getId())) {
            return true;
        }
        return volumeInfo.isMountedWritable() && volumeInfo.getType() == 1;
    }

    @Override // android.content.pm.PackageManager
    public void deletePackage(java.lang.String str, android.content.pm.IPackageDeleteObserver iPackageDeleteObserver, int i) {
        deletePackageAsUser(str, iPackageDeleteObserver, i, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public void deletePackageAsUser(java.lang.String str, android.content.pm.IPackageDeleteObserver iPackageDeleteObserver, int i, int i2) {
        try {
            this.mPM.deletePackageAsUser(str, -1, iPackageDeleteObserver, i2, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void clearApplicationUserData(java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver) {
        try {
            this.mPM.clearApplicationUserData(str, iPackageDataObserver, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void deleteApplicationCacheFiles(java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver) {
        try {
            this.mPM.deleteApplicationCacheFiles(str, iPackageDataObserver);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void deleteApplicationCacheFilesAsUser(java.lang.String str, int i, android.content.pm.IPackageDataObserver iPackageDataObserver) {
        try {
            this.mPM.deleteApplicationCacheFilesAsUser(str, i, iPackageDataObserver);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void freeStorageAndNotify(java.lang.String str, long j, android.content.pm.IPackageDataObserver iPackageDataObserver) {
        try {
            this.mPM.freeStorageAndNotify(str, j, 0, iPackageDataObserver);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void freeStorage(java.lang.String str, long j, android.content.IntentSender intentSender) {
        try {
            this.mPM.freeStorage(str, j, 0, intentSender);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String[] setDistractingPackageRestrictions(java.lang.String[] strArr, int i) {
        try {
            return this.mPM.setDistractingPackageRestrictionsAsUser(strArr, i, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String[] setPackagesSuspended(java.lang.String[] strArr, boolean z, android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, java.lang.String str) {
        android.content.pm.SuspendDialogInfo suspendDialogInfo;
        if (!android.text.TextUtils.isEmpty(str)) {
            suspendDialogInfo = new android.content.pm.SuspendDialogInfo.Builder().setMessage(str).build();
        } else {
            suspendDialogInfo = null;
        }
        return setPackagesSuspended(strArr, z, persistableBundle, persistableBundle2, suspendDialogInfo, 0);
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String[] setPackagesSuspended(java.lang.String[] strArr, boolean z, android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, android.content.pm.SuspendDialogInfo suspendDialogInfo) {
        return setPackagesSuspended(strArr, z, persistableBundle, persistableBundle2, suspendDialogInfo, 0);
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String[] setPackagesSuspended(java.lang.String[] strArr, boolean z, android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, android.content.pm.SuspendDialogInfo suspendDialogInfo, int i) {
        try {
            return this.mPM.setPackagesSuspendedAsUser(strArr, z, persistableBundle, persistableBundle2, suspendDialogInfo, i, this.mContext.getOpPackageName(), android.os.UserHandle.myUserId(), getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String[] getUnsuspendablePackages(java.lang.String[] strArr) {
        try {
            return this.mPM.getUnsuspendablePackagesForUser(strArr, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.os.Bundle getSuspendedPackageAppExtras() {
        try {
            return this.mPM.getSuspendedPackageAppExtras(this.mContext.getOpPackageName(), getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getSuspendingPackage(java.lang.String str) {
        try {
            return this.mPM.getSuspendingPackage(str, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageSuspendedForUser(java.lang.String str, int i) {
        try {
            return this.mPM.isPackageSuspendedForUser(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageSuspended(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            return isPackageSuspendedForUser(str, getUserId());
        } catch (java.lang.IllegalArgumentException e) {
            throw new android.content.pm.PackageManager.NameNotFoundException(str);
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageSuspended() {
        return isPackageSuspendedForUser(this.mContext.getOpPackageName(), getUserId());
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageQuarantined(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            return this.mPM.isPackageQuarantinedForUser(str, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.lang.IllegalArgumentException e2) {
            throw new android.content.pm.PackageManager.NameNotFoundException(str);
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageStopped(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            return this.mPM.isPackageStoppedForUser(str, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.lang.IllegalArgumentException e2) {
            throw new android.content.pm.PackageManager.NameNotFoundException(str);
        }
    }

    @Override // android.content.pm.PackageManager
    public void setApplicationCategoryHint(java.lang.String str, int i) {
        try {
            this.mPM.setApplicationCategoryHint(str, i, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void getPackageSizeInfoAsUser(java.lang.String str, int i, android.content.pm.IPackageStatsObserver iPackageStatsObserver) {
        if (this.mContext.getApplicationInfo().targetSdkVersion >= 26) {
            throw new java.lang.UnsupportedOperationException("Shame on you for calling the hidden API getPackageSizeInfoAsUser(). Shame!");
        }
        if (iPackageStatsObserver != null) {
            android.util.Log.d(TAG, "Shame on you for calling the hidden API getPackageSizeInfoAsUser(). Shame!");
            try {
                iPackageStatsObserver.onGetStatsCompleted(null, false);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @Override // android.content.pm.PackageManager
    public void addPackageToPreferred(java.lang.String str) {
        android.util.Log.w(TAG, "addPackageToPreferred() is a no-op");
    }

    @Override // android.content.pm.PackageManager
    public void removePackageFromPreferred(java.lang.String str) {
        android.util.Log.w(TAG, "removePackageFromPreferred() is a no-op");
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PackageInfo> getPreferredPackages(int i) {
        android.util.Log.w(TAG, "getPreferredPackages() is a no-op");
        return java.util.Collections.emptyList();
    }

    @Override // android.content.pm.PackageManager
    public void addPreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName) {
        try {
            this.mPM.addPreferredActivity(intentFilter, i, componentNameArr, componentName, getUserId(), false);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void addPreferredActivityAsUser(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, int i2) {
        try {
            this.mPM.addPreferredActivity(intentFilter, i, componentNameArr, componentName, i2, false);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void replacePreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName) {
        try {
            this.mPM.replacePreferredActivity(intentFilter, i, componentNameArr, componentName, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void replacePreferredActivityAsUser(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, int i2) {
        try {
            this.mPM.replacePreferredActivity(intentFilter, i, componentNameArr, componentName, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void clearPackagePreferredActivities(java.lang.String str) {
        try {
            this.mPM.clearPackagePreferredActivities(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void addUniquePreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName) {
        try {
            this.mPM.addPreferredActivity(intentFilter, i, componentNameArr, componentName, getUserId(), true);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getPreferredActivities(java.util.List<android.content.IntentFilter> list, java.util.List<android.content.ComponentName> list2, java.lang.String str) {
        try {
            return this.mPM.getPreferredActivities(list, list2, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.ComponentName getHomeActivities(java.util.List<android.content.pm.ResolveInfo> list) {
        try {
            return this.mPM.getHomeActivities(list);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setSyntheticAppDetailsActivityEnabled(java.lang.String str, boolean z) {
        int i;
        try {
            android.content.ComponentName componentName = new android.content.ComponentName(str, APP_DETAILS_ACTIVITY_CLASS_NAME);
            android.content.pm.IPackageManager iPackageManager = this.mPM;
            if (z) {
                i = 0;
            } else {
                i = 2;
            }
            iPackageManager.setComponentEnabledSetting(componentName, i, 1, getUserId(), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean getSyntheticAppDetailsActivityEnabled(java.lang.String str) {
        try {
            int componentEnabledSetting = this.mPM.getComponentEnabledSetting(new android.content.ComponentName(str, APP_DETAILS_ACTIVITY_CLASS_NAME), getUserId());
            return componentEnabledSetting == 1 || componentEnabledSetting == 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setComponentEnabledSetting(android.content.ComponentName componentName, int i, int i2) {
        try {
            this.mPM.setComponentEnabledSetting(componentName, i, i2, getUserId(), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setComponentEnabledSettings(java.util.List<android.content.pm.PackageManager.ComponentEnabledSetting> list) {
        try {
            this.mPM.setComponentEnabledSettings(list, getUserId(), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getComponentEnabledSetting(android.content.ComponentName componentName) {
        try {
            return this.mPM.getComponentEnabledSetting(componentName, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setApplicationEnabledSetting(java.lang.String str, int i, int i2) {
        try {
            this.mPM.setApplicationEnabledSetting(str, i, i2, getUserId(), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public int getApplicationEnabledSetting(java.lang.String str) {
        try {
            return this.mPM.getApplicationEnabledSetting(str, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void flushPackageRestrictionsAsUser(int i) {
        try {
            this.mPM.flushPackageRestrictionsAsUser(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean setApplicationHiddenSettingAsUser(java.lang.String str, boolean z, android.os.UserHandle userHandle) {
        try {
            return this.mPM.setApplicationHiddenSettingAsUser(str, z, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean getApplicationHiddenSettingAsUser(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mPM.getApplicationHiddenSettingAsUser(str, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setSystemAppState(java.lang.String str, int i) {
        try {
            switch (i) {
                case 0:
                    this.mPM.setSystemAppHiddenUntilInstalled(str, true);
                    break;
                case 1:
                    this.mPM.setSystemAppHiddenUntilInstalled(str, false);
                    break;
                case 2:
                    this.mPM.setSystemAppInstallState(str, true, getUserId());
                    break;
                case 3:
                    this.mPM.setSystemAppInstallState(str, false, getUserId());
                    break;
                default:
                    return;
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.KeySet getKeySetByAlias(java.lang.String str, java.lang.String str2) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        try {
            return this.mPM.getKeySetByAlias(str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.KeySet getSigningKeySet(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        try {
            return this.mPM.getSigningKeySet(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isSignedBy(java.lang.String str, android.content.pm.KeySet keySet) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(keySet);
        try {
            return this.mPM.isPackageSignedByKeySet(str, keySet);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isSignedByExactly(java.lang.String str, android.content.pm.KeySet keySet) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(keySet);
        try {
            return this.mPM.isPackageSignedByKeySetExactly(str, keySet);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.VerifierDeviceIdentity getVerifierDeviceIdentity() {
        try {
            return this.mPM.getVerifierDeviceIdentity();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isUpgrade() {
        return isDeviceUpgrading();
    }

    @Override // android.content.pm.PackageManager
    public boolean isDeviceUpgrading() {
        try {
            return this.mPM.isDeviceUpgrading();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.PackageInstaller getPackageInstaller() {
        if (this.mInstaller == null) {
            try {
                this.mInstaller = new android.content.pm.PackageInstaller(this.mPM.getPackageInstaller(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), getUserId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mInstaller;
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageAvailable(java.lang.String str) {
        try {
            return this.mPM.isPackageAvailable(str, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void addCrossProfileIntentFilter(android.content.IntentFilter intentFilter, int i, int i2, int i3) {
        try {
            this.mPM.addCrossProfileIntentFilter(intentFilter, this.mContext.getOpPackageName(), i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean removeCrossProfileIntentFilter(android.content.IntentFilter intentFilter, int i, int i2, int i3) {
        try {
            return this.mPM.removeCrossProfileIntentFilter(intentFilter, this.mContext.getOpPackageName(), i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void clearCrossProfileIntentFilters(int i) {
        try {
            this.mPM.clearCrossProfileIntentFilters(i, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable loadItemIcon(android.content.pm.PackageItemInfo packageItemInfo, android.content.pm.ApplicationInfo applicationInfo) {
        android.graphics.drawable.Drawable loadUnbadgedItemIcon = loadUnbadgedItemIcon(packageItemInfo, applicationInfo);
        if (packageItemInfo.showUserIcon != -10000) {
            return loadUnbadgedItemIcon;
        }
        return getUserBadgedIcon(loadUnbadgedItemIcon, new android.os.UserHandle(getUserId()));
    }

    @Override // android.content.pm.PackageManager
    public android.graphics.drawable.Drawable loadUnbadgedItemIcon(android.content.pm.PackageItemInfo packageItemInfo, android.content.pm.ApplicationInfo applicationInfo) {
        android.graphics.drawable.Drawable drawable;
        if (packageItemInfo.showUserIcon != -10000) {
            return com.android.internal.util.UserIcons.getDefaultUserIcon(this.mContext.getResources(), packageItemInfo.showUserIcon, false);
        }
        if (packageItemInfo.packageName == null) {
            drawable = null;
        } else if (packageItemInfo.isArchived) {
            drawable = getArchivedAppIcon(packageItemInfo.packageName);
        } else {
            drawable = getDrawable(packageItemInfo.packageName, packageItemInfo.icon, applicationInfo);
        }
        if (drawable == null && packageItemInfo != applicationInfo && applicationInfo != null) {
            drawable = loadUnbadgedItemIcon(applicationInfo, applicationInfo);
        }
        if (drawable == null) {
            return packageItemInfo.loadDefaultIcon(this);
        }
        return drawable;
    }

    private android.graphics.drawable.Drawable getBadgedDrawable(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2, android.graphics.Rect rect, boolean z) {
        android.graphics.Bitmap createBitmap;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        boolean z2 = z && (drawable instanceof android.graphics.drawable.BitmapDrawable) && ((android.graphics.drawable.BitmapDrawable) drawable).getBitmap().isMutable();
        if (z2) {
            createBitmap = ((android.graphics.drawable.BitmapDrawable) drawable).getBitmap();
        } else {
            createBitmap = android.graphics.Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, android.graphics.Bitmap.Config.ARGB_8888);
        }
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        if (!z2) {
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
            drawable.draw(canvas);
        }
        if (rect != null) {
            if (rect.left >= 0 && rect.top >= 0 && rect.width() <= intrinsicWidth && rect.height() <= intrinsicHeight) {
                drawable2.setBounds(0, 0, rect.width(), rect.height());
                canvas.save();
                canvas.translate(rect.left, rect.top);
                drawable2.draw(canvas);
                canvas.restore();
            } else {
                throw new java.lang.IllegalArgumentException("Badge location " + rect + " not in badged drawable bounds " + new android.graphics.Rect(0, 0, intrinsicWidth, intrinsicHeight));
            }
        } else {
            drawable2.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
            drawable2.draw(canvas);
        }
        if (!z2) {
            android.graphics.drawable.BitmapDrawable bitmapDrawable = new android.graphics.drawable.BitmapDrawable(this.mContext.getResources(), createBitmap);
            if (drawable instanceof android.graphics.drawable.BitmapDrawable) {
                bitmapDrawable.setTargetDensity(((android.graphics.drawable.BitmapDrawable) drawable).getBitmap().getDensity());
            }
            return bitmapDrawable;
        }
        return drawable;
    }

    private boolean hasUserBadge(int i) {
        return getUserManager().hasBadge(i);
    }

    @Override // android.content.pm.PackageManager
    public int getInstallReason(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mPM.getInstallReason(str, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static class MoveCallbackDelegate extends android.content.pm.IPackageMoveObserver.Stub implements android.os.Handler.Callback {
        private static final int MSG_CREATED = 1;
        private static final int MSG_STATUS_CHANGED = 2;
        final android.content.pm.PackageManager.MoveCallback mCallback;
        final android.os.Handler mHandler;

        public MoveCallbackDelegate(android.content.pm.PackageManager.MoveCallback moveCallback, android.os.Looper looper) {
            this.mCallback = moveCallback;
            this.mHandler = new android.os.Handler(looper, this);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    this.mCallback.onCreated(someArgs.argi1, (android.os.Bundle) someArgs.arg2);
                    someArgs.recycle();
                    break;
                case 2:
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    this.mCallback.onStatusChanged(someArgs2.argi1, someArgs2.argi2, ((java.lang.Long) someArgs2.arg3).longValue());
                    someArgs2.recycle();
                    break;
            }
            return true;
        }

        @Override // android.content.pm.IPackageMoveObserver
        public void onCreated(int i, android.os.Bundle bundle) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.arg2 = bundle;
            this.mHandler.obtainMessage(1, obtain).sendToTarget();
        }

        @Override // android.content.pm.IPackageMoveObserver
        public void onStatusChanged(int i, int i2, long j) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.argi2 = i2;
            obtain.arg3 = java.lang.Long.valueOf(j);
            this.mHandler.obtainMessage(2, obtain).sendToTarget();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean canRequestPackageInstalls() {
        try {
            return this.mPM.canRequestPackageInstalls(this.mContext.getPackageName(), getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.ComponentName getInstantAppResolverSettingsComponent() {
        try {
            return this.mPM.getInstantAppResolverSettingsComponent();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.ComponentName getInstantAppInstallerComponent() {
        try {
            return this.mPM.getInstantAppInstallerComponent();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getInstantAppAndroidId(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mPM.getInstantAppAndroidId(str, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    private static class DexModuleRegisterResult {
        final java.lang.String dexModulePath;
        final java.lang.String message;
        final boolean success;

        private DexModuleRegisterResult(java.lang.String str, boolean z, java.lang.String str2) {
            this.dexModulePath = str;
            this.success = z;
            this.message = str2;
        }
    }

    private static class DexModuleRegisterCallbackDelegate extends android.content.pm.IDexModuleRegisterCallback.Stub implements android.os.Handler.Callback {
        private static final int MSG_DEX_MODULE_REGISTERED = 1;
        private final android.content.pm.PackageManager.DexModuleRegisterCallback callback;
        private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), this);

        DexModuleRegisterCallbackDelegate(android.content.pm.PackageManager.DexModuleRegisterCallback dexModuleRegisterCallback) {
            this.callback = dexModuleRegisterCallback;
        }

        @Override // android.content.pm.IDexModuleRegisterCallback
        public void onDexModuleRegistered(java.lang.String str, boolean z, java.lang.String str2) throws android.os.RemoteException {
            this.mHandler.obtainMessage(1, new android.app.ApplicationPackageManager.DexModuleRegisterResult(str, z, str2)).sendToTarget();
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(android.os.Message message) {
            if (message.what != 1) {
                return false;
            }
            android.app.ApplicationPackageManager.DexModuleRegisterResult dexModuleRegisterResult = (android.app.ApplicationPackageManager.DexModuleRegisterResult) message.obj;
            this.callback.onDexModuleRegistered(dexModuleRegisterResult.dexModulePath, dexModuleRegisterResult.success, dexModuleRegisterResult.message);
            return true;
        }
    }

    @Override // android.content.pm.PackageManager
    public void registerDexModule(java.lang.String str, android.content.pm.PackageManager.DexModuleRegisterCallback dexModuleRegisterCallback) {
        android.app.ApplicationPackageManager.DexModuleRegisterCallbackDelegate dexModuleRegisterCallbackDelegate;
        if (dexModuleRegisterCallback == null) {
            dexModuleRegisterCallbackDelegate = null;
        } else {
            dexModuleRegisterCallbackDelegate = new android.app.ApplicationPackageManager.DexModuleRegisterCallbackDelegate(dexModuleRegisterCallback);
        }
        boolean z = false;
        try {
            if ((android.system.Os.stat(str).st_mode & android.system.OsConstants.S_IROTH) != 0) {
                z = true;
            }
            try {
                this.mPM.registerDexModule(this.mContext.getPackageName(), str, z, dexModuleRegisterCallbackDelegate);
            } catch (android.os.RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        } catch (android.system.ErrnoException e2) {
            if (dexModuleRegisterCallbackDelegate != null) {
                dexModuleRegisterCallback.onDexModuleRegistered(str, false, "Could not get stat the module file: " + e2.getMessage());
            }
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.CharSequence getHarmfulAppWarning(java.lang.String str) {
        try {
            return this.mPM.getHarmfulAppWarning(str, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setHarmfulAppWarning(java.lang.String str, java.lang.CharSequence charSequence) {
        try {
            this.mPM.setHarmfulAppWarning(str, charSequence, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.dex.ArtManager getArtManager() {
        if (this.mArtManager == null) {
            try {
                this.mArtManager = new android.content.pm.dex.ArtManager(this.mContext, this.mPM.getArtManager());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mArtManager;
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getDefaultTextClassifierPackageName() {
        try {
            return this.mPM.getDefaultTextClassifierPackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getSystemTextClassifierPackageName() {
        try {
            return this.mPM.getSystemTextClassifierPackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getAttentionServicePackageName() {
        try {
            return this.mPM.getAttentionServicePackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getRotationResolverPackageName() {
        try {
            return this.mPM.getRotationResolverPackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getWellbeingPackageName() {
        try {
            return this.mPM.getWellbeingPackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getAppPredictionServicePackageName() {
        try {
            return this.mPM.getAppPredictionServicePackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getSystemCaptionsServicePackageName() {
        try {
            return this.mPM.getSystemCaptionsServicePackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getSetupWizardPackageName() {
        try {
            return this.mPM.getSetupWizardPackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.lang.String getIncidentReportApproverPackageName() {
        try {
            return this.mPM.getIncidentReportApproverPackageName();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isPackageStateProtected(java.lang.String str, int i) {
        try {
            return this.mPM.isPackageStateProtected(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public void sendDeviceCustomizationReadyBroadcast() {
        try {
            this.mPM.sendDeviceCustomizationReadyBroadcast();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean isAutoRevokeWhitelisted() {
        try {
            return this.mPM.isAutoRevokeWhitelisted(this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public void setMimeGroup(java.lang.String str, java.util.Set<java.lang.String> set) {
        try {
            this.mPM.setMimeGroup(this.mContext.getPackageName(), str, new java.util.ArrayList(set));
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.Set<java.lang.String> getMimeGroup(java.lang.String str) {
        try {
            return new android.util.ArraySet(this.mPM.getMimeGroup(this.mContext.getPackageName(), str));
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.PackageManager.Property getProperty(java.lang.String str, java.lang.String str2) throws android.content.pm.PackageManager.NameNotFoundException {
        java.util.Objects.requireNonNull(str2);
        java.util.Objects.requireNonNull(str);
        return getPropertyAsUser(str, str2, null, getUserId());
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.PackageManager.Property getProperty(java.lang.String str, android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        java.util.Objects.requireNonNull(componentName);
        java.util.Objects.requireNonNull(str);
        return getPropertyAsUser(str, componentName.getPackageName(), componentName.getClassName(), getUserId());
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.PackageManager.Property getPropertyAsUser(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        java.util.Objects.requireNonNull(str2);
        java.util.Objects.requireNonNull(str);
        try {
            android.content.pm.PackageManager.Property propertyAsUser = this.mPM.getPropertyAsUser(str, str2, str3, i);
            if (propertyAsUser == null) {
                throw new android.content.pm.PackageManager.NameNotFoundException();
            }
            return propertyAsUser;
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PackageManager.Property> queryApplicationProperty(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        try {
            android.content.pm.ParceledListSlice queryProperty = this.mPM.queryProperty(str, 5);
            if (queryProperty == null) {
                return java.util.Collections.emptyList();
            }
            return queryProperty.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PackageManager.Property> queryActivityProperty(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        try {
            android.content.pm.ParceledListSlice queryProperty = this.mPM.queryProperty(str, 1);
            if (queryProperty == null) {
                return java.util.Collections.emptyList();
            }
            return queryProperty.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PackageManager.Property> queryProviderProperty(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        try {
            android.content.pm.ParceledListSlice queryProperty = this.mPM.queryProperty(str, 4);
            if (queryProperty == null) {
                return java.util.Collections.emptyList();
            }
            return queryProperty.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PackageManager.Property> queryReceiverProperty(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        try {
            android.content.pm.ParceledListSlice queryProperty = this.mPM.queryProperty(str, 2);
            if (queryProperty == null) {
                return java.util.Collections.emptyList();
            }
            return queryProperty.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public java.util.List<android.content.pm.PackageManager.Property> queryServiceProperty(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        try {
            android.content.pm.ParceledListSlice queryProperty = this.mPM.queryProperty(str, 3);
            if (queryProperty == null) {
                return java.util.Collections.emptyList();
            }
            return queryProperty.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean canPackageQuery(java.lang.String str, java.lang.String str2) throws android.content.pm.PackageManager.NameNotFoundException {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        return canPackageQuery(str, new java.lang.String[]{str2})[0];
    }

    @Override // android.content.pm.PackageManager
    public boolean[] canPackageQuery(java.lang.String str, java.lang.String[] strArr) throws android.content.pm.PackageManager.NameNotFoundException {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(strArr);
        try {
            return this.mPM.canPackageQuery(str, strArr, getUserId());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(android.content.pm.PackageManager.NameNotFoundException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public void makeUidVisible(int i, int i2) {
        try {
            this.mPM.makeUidVisible(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public android.content.pm.ArchivedPackageInfo getArchivedPackage(java.lang.String str) {
        try {
            android.content.pm.ArchivedPackageParcel archivedPackage = this.mPM.getArchivedPackage(str, this.mContext.getUserId());
            if (archivedPackage == null) {
                return null;
            }
            return new android.content.pm.ArchivedPackageInfo(archivedPackage);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean canUserUninstall(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mPM.getBlockUninstallForUser(str, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @Override // android.content.pm.PackageManager
    public boolean shouldShowNewAppInstalledNotification() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), android.provider.Settings.Global.SHOW_NEW_APP_INSTALLED_NOTIFICATION_ENABLED, 0) == 1;
    }

    @Override // android.content.pm.PackageManager
    public void relinquishUpdateOwnership(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        try {
            this.mPM.relinquishUpdateOwnership(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void registerPackageMonitorCallback(android.os.IRemoteCallback iRemoteCallback, int i) {
        java.util.Objects.requireNonNull(iRemoteCallback);
        try {
            this.mPM.registerPackageMonitorCallback(iRemoteCallback, i);
            synchronized (this.mPackageMonitorCallbacks) {
                if (this.mPackageMonitorCallbacks.contains(iRemoteCallback)) {
                    throw new java.lang.IllegalStateException("registerPackageMonitorCallback: callback already registered: " + iRemoteCallback);
                }
                this.mPackageMonitorCallbacks.add(iRemoteCallback);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.pm.PackageManager
    public void unregisterPackageMonitorCallback(android.os.IRemoteCallback iRemoteCallback) {
        java.util.Objects.requireNonNull(iRemoteCallback);
        try {
            this.mPM.unregisterPackageMonitorCallback(iRemoteCallback);
            synchronized (this.mPackageMonitorCallbacks) {
                this.mPackageMonitorCallbacks.remove(iRemoteCallback);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private android.graphics.drawable.Drawable getArchivedAppIcon(java.lang.String str) {
        try {
            android.graphics.Bitmap archivedAppIcon = this.mPM.getArchivedAppIcon(str, new android.os.UserHandle(getUserId()), this.mContext.getPackageName());
            if (archivedAppIcon == null) {
                return null;
            }
            return new android.graphics.drawable.BitmapDrawable((android.content.res.Resources) null, archivedAppIcon);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to retrieve archived app icon: " + e.getMessage());
            return null;
        }
    }

    @Override // android.content.pm.PackageManager
    public <T> T parseAndroidManifest(java.io.File file, java.util.function.Function<android.content.res.XmlResourceParser, T> function) throws java.io.IOException {
        java.util.Objects.requireNonNull(file, "apkFile cannot be null");
        java.util.Objects.requireNonNull(function, "parserFunction cannot be null");
        try {
            android.content.res.XmlResourceParser androidManifestParser = getAndroidManifestParser(file);
            try {
                T apply = function.apply(androidManifestParser);
                if (androidManifestParser != null) {
                    androidManifestParser.close();
                }
                return apply;
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, "Failed to get the android manifest parser", e);
            throw e;
        }
    }

    private static android.content.res.XmlResourceParser getAndroidManifestParser(java.io.File file) throws java.io.IOException {
        android.content.res.ApkAssets apkAssets = null;
        try {
            apkAssets = android.content.res.ApkAssets.loadFromPath(file.getAbsolutePath());
            return apkAssets.openXml("AndroidManifest.xml");
        } finally {
            if (apkAssets != null) {
                try {
                    apkAssets.close();
                } catch (java.lang.Throwable th) {
                    android.util.Log.w(TAG, "Failed to close apkAssets", th);
                }
            }
        }
    }
}
