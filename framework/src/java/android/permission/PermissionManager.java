package android.permission;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class PermissionManager {
    public static final java.lang.String ACTION_REVIEW_PERMISSION_DECISIONS = "android.permission.action.REVIEW_PERMISSION_DECISIONS";
    public static final java.lang.String CACHE_KEY_PACKAGE_INFO = "cache_key.package_info";
    public static final long CANNOT_INSTALL_WITH_BAD_PERMISSION_GROUPS = 146211400;
    public static final boolean DEBUG_DEVICE_PERMISSIONS = false;
    public static final boolean DEBUG_TRACE_GRANTS = false;
    public static final boolean DEBUG_TRACE_PERMISSION_UPDATES = false;
    private static final long EXEMPTED_INDICATOR_ROLE_UPDATE_FREQUENCY_MS = 15000;
    public static final int EXPLICIT_SET_FLAGS = 32823;

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PERMISSION_USAGES = "android.permission.extra.PERMISSION_USAGES";
    public static final java.lang.String KILL_APP_REASON_GIDS_CHANGED = "permission grant or revoke changed gids";
    public static final java.lang.String KILL_APP_REASON_PERMISSIONS_REVOKED = "permissions revoked";
    public static final java.lang.String LOG_TAG_TRACE_GRANTS = "PermissionGrantTrace";
    public static final int PERMISSION_GRANTED = 0;
    public static final int PERMISSION_HARD_DENIED = 2;
    public static final int PERMISSION_SOFT_DENIED = 1;
    private static final java.lang.String SYSTEM_PKG = "android";
    private static java.lang.String[] sLocationExtraPkgNames;
    private static java.lang.String[] sLocationProviderPkgNames;
    private static android.app.PropertyInvalidatedCache<android.permission.PermissionManager.PackageNamePermissionQuery, java.lang.Integer> sPackageNamePermissionCache;
    private static final android.app.PropertyInvalidatedCache<android.permission.PermissionManager.PermissionQuery, java.lang.Integer> sPermissionCache;
    private final android.content.Context mContext;
    private final android.permission.LegacyPermissionManager mLegacyPermissionManager;
    private java.util.List<android.permission.PermissionManager.SplitPermissionInfo> mSplitPermissionInfos;
    private android.permission.PermissionUsageHelper mUsageHelper;
    private static final java.lang.String LOG_TAG = android.permission.PermissionManager.class.getName();
    public static final boolean USE_ACCESS_CHECKING_SERVICE = android.internal.modules.utils.build.SdkLevel.isAtLeastV();
    private static long sLastIndicatorUpdateTime = -1;
    private static final int[] EXEMPTED_ROLES = {17039411, 17039410, 17039412, 17039413, 17039414, 17039415};
    private static final java.lang.String[] INDICATOR_EXEMPTED_PACKAGES = new java.lang.String[EXEMPTED_ROLES.length];
    private static volatile boolean sShouldWarnMissingActivityManager = true;
    private final android.util.ArrayMap<android.content.pm.PackageManager.OnPermissionsChangedListener, android.permission.IOnPermissionsChangeListener> mPermissionListeners = new android.util.ArrayMap<>();
    private final android.content.pm.IPackageManager mPackageManager = android.app.AppGlobals.getPackageManager();
    private final android.permission.IPermissionManager mPermissionManager = android.permission.IPermissionManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("permissionmgr"));

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PermissionResult {
    }

    static {
        java.lang.String str = CACHE_KEY_PACKAGE_INFO;
        sPermissionCache = new android.app.PropertyInvalidatedCache<android.permission.PermissionManager.PermissionQuery, java.lang.Integer>(2048, str, "checkPermission") { // from class: android.permission.PermissionManager.1
            @Override // android.app.PropertyInvalidatedCache
            public java.lang.Integer recompute(android.permission.PermissionManager.PermissionQuery permissionQuery) {
                return java.lang.Integer.valueOf(android.permission.PermissionManager.checkPermissionUncached(permissionQuery.permission, permissionQuery.pid, permissionQuery.uid, permissionQuery.deviceId));
            }
        };
        sPackageNamePermissionCache = new android.app.PropertyInvalidatedCache<android.permission.PermissionManager.PackageNamePermissionQuery, java.lang.Integer>(16, str, "checkPackageNamePermission") { // from class: android.permission.PermissionManager.2
            @Override // android.app.PropertyInvalidatedCache
            public java.lang.Integer recompute(android.permission.PermissionManager.PackageNamePermissionQuery packageNamePermissionQuery) {
                return java.lang.Integer.valueOf(android.permission.PermissionManager.checkPackageNamePermissionUncached(packageNamePermissionQuery.permName, packageNamePermissionQuery.pkgName, packageNamePermissionQuery.persistentDeviceId, packageNamePermissionQuery.userId));
            }

            @Override // android.app.PropertyInvalidatedCache
            public boolean bypass(android.permission.PermissionManager.PackageNamePermissionQuery packageNamePermissionQuery) {
                return packageNamePermissionQuery.userId < 0;
            }
        };
    }

    public PermissionManager(android.content.Context context) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mLegacyPermissionManager = (android.permission.LegacyPermissionManager) context.getSystemService(android.permission.LegacyPermissionManager.class);
    }

    public int checkPermissionForDataDelivery(java.lang.String str, android.content.AttributionSource attributionSource, java.lang.String str2) {
        return android.content.PermissionChecker.checkPermissionForDataDelivery(this.mContext, str, -1, attributionSource, str2);
    }

    public int checkPermissionForStartDataDelivery(java.lang.String str, android.content.AttributionSource attributionSource, java.lang.String str2) {
        return android.content.PermissionChecker.checkPermissionForDataDelivery(this.mContext, str, -1, attributionSource, str2, true);
    }

    public void finishDataDelivery(java.lang.String str, android.content.AttributionSource attributionSource) {
        android.content.PermissionChecker.finishDataDelivery(this.mContext, android.app.AppOpsManager.permissionToOp(str), attributionSource);
    }

    public int checkPermissionForDataDeliveryFromDataSource(java.lang.String str, android.content.AttributionSource attributionSource, java.lang.String str2) {
        return android.content.PermissionChecker.checkPermissionForDataDeliveryFromDataSource(this.mContext, str, -1, attributionSource, str2);
    }

    public int checkPermissionForPreflight(java.lang.String str, android.content.AttributionSource attributionSource) {
        return android.content.PermissionChecker.checkPermissionForPreflight(this.mContext, str, attributionSource);
    }

    public android.content.pm.PermissionInfo getPermissionInfo(java.lang.String str, int i) {
        try {
            return this.mPermissionManager.getPermissionInfo(str, this.mContext.getOpPackageName(), i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.pm.PermissionInfo> queryPermissionsByGroup(java.lang.String str, int i) {
        try {
            android.content.pm.ParceledListSlice queryPermissionsByGroup = this.mPermissionManager.queryPermissionsByGroup(str, i);
            if (queryPermissionsByGroup == null) {
                return null;
            }
            return queryPermissionsByGroup.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean addPermission(android.content.pm.PermissionInfo permissionInfo, boolean z) {
        try {
            return this.mPermissionManager.addPermission(permissionInfo, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removePermission(java.lang.String str) {
        try {
            this.mPermissionManager.removePermission(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.pm.PermissionGroupInfo getPermissionGroupInfo(java.lang.String str, int i) {
        try {
            return this.mPermissionManager.getPermissionGroupInfo(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.pm.PermissionGroupInfo> getAllPermissionGroups(int i) {
        try {
            android.content.pm.ParceledListSlice allPermissionGroups = this.mPermissionManager.getAllPermissionGroups(i);
            if (allPermissionGroups == null) {
                return java.util.Collections.emptyList();
            }
            return allPermissionGroups.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isPermissionRevokedByPolicy(java.lang.String str, java.lang.String str2) {
        try {
            return this.mPermissionManager.isPermissionRevokedByPolicy(str, str2, this.mContext.getDeviceId(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean shouldTraceGrant(java.lang.String str, java.lang.String str2, int i) {
        return false;
    }

    public void grantRuntimePermission(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        java.lang.String persistentDeviceId = getPersistentDeviceId(this.mContext.getDeviceId());
        if (persistentDeviceId == null) {
            return;
        }
        grantRuntimePermissionInternal(str, str2, persistentDeviceId, userHandle);
    }

    @android.annotation.SystemApi
    public void grantRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        grantRuntimePermissionInternal(str, str2, str3, this.mContext.getUser());
    }

    private void grantRuntimePermissionInternal(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.UserHandle userHandle) {
        try {
            this.mPermissionManager.grantRuntimePermission(str, str2, str3, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle, java.lang.String str3) {
        java.lang.String persistentDeviceId = getPersistentDeviceId(this.mContext.getDeviceId());
        if (persistentDeviceId == null) {
            return;
        }
        revokeRuntimePermissionInternal(str, str2, persistentDeviceId, userHandle, str3);
    }

    @android.annotation.SystemApi
    public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        revokeRuntimePermissionInternal(str, str2, str3, this.mContext.getUser(), str4);
    }

    private void revokeRuntimePermissionInternal(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.UserHandle userHandle, java.lang.String str4) {
        try {
            this.mPermissionManager.revokeRuntimePermission(str, str2, str3, userHandle.getIdentifier(), str4);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getPermissionFlags(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) {
        java.lang.String persistentDeviceId = getPersistentDeviceId(this.mContext.getDeviceId());
        if (persistentDeviceId == null) {
            return 0;
        }
        return getPermissionFlagsInternal(str, str2, persistentDeviceId, userHandle);
    }

    @android.annotation.SystemApi
    public int getPermissionFlags(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return getPermissionFlagsInternal(str, str2, str3, this.mContext.getUser());
    }

    private int getPermissionFlagsInternal(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.UserHandle userHandle) {
        try {
            return this.mPermissionManager.getPermissionFlags(str, str2, str3, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updatePermissionFlags(java.lang.String str, java.lang.String str2, int i, int i2, android.os.UserHandle userHandle) {
        java.lang.String persistentDeviceId = getPersistentDeviceId(this.mContext.getDeviceId());
        if (persistentDeviceId == null) {
            return;
        }
        updatePermissionFlagsInternal(str, str2, i, i2, persistentDeviceId, userHandle);
    }

    @android.annotation.SystemApi
    public void updatePermissionFlags(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) {
        updatePermissionFlagsInternal(str, str2, i, i2, str3, this.mContext.getUser());
    }

    private void updatePermissionFlagsInternal(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3, android.os.UserHandle userHandle) {
        try {
            this.mPermissionManager.updatePermissionFlags(str, str2, i, i2, this.mContext.getApplicationInfo().targetSdkVersion >= 29, str3, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Set<java.lang.String> getAllowlistedRestrictedPermissions(java.lang.String str, int i) {
        try {
            java.util.List<java.lang.String> allowlistedRestrictedPermissions = this.mPermissionManager.getAllowlistedRestrictedPermissions(str, i, this.mContext.getUserId());
            if (allowlistedRestrictedPermissions == null) {
                return java.util.Collections.emptySet();
            }
            return new android.util.ArraySet(allowlistedRestrictedPermissions);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean addAllowlistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i) {
        try {
            return this.mPermissionManager.addAllowlistedRestrictedPermission(str, str2, i, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean removeAllowlistedRestrictedPermission(java.lang.String str, java.lang.String str2, int i) {
        try {
            return this.mPermissionManager.removeAllowlistedRestrictedPermission(str, str2, i, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAutoRevokeExempted(java.lang.String str) {
        try {
            return this.mPermissionManager.isAutoRevokeExempted(str, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setAutoRevokeExempted(java.lang.String str, boolean z) {
        try {
            return this.mPermissionManager.setAutoRevokeExempted(str, z, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldShowRequestPermissionRationale(java.lang.String str) {
        try {
            return this.mPermissionManager.shouldShowRequestPermissionRationale(this.mContext.getPackageName(), str, this.mContext.getDeviceId(), this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addOnPermissionsChangeListener(android.content.pm.PackageManager.OnPermissionsChangedListener onPermissionsChangedListener) {
        synchronized (this.mPermissionListeners) {
            if (this.mPermissionListeners.get(onPermissionsChangedListener) != null) {
                return;
            }
            android.permission.PermissionManager.OnPermissionsChangeListenerDelegate onPermissionsChangeListenerDelegate = new android.permission.PermissionManager.OnPermissionsChangeListenerDelegate(onPermissionsChangedListener, android.os.Looper.getMainLooper());
            try {
                this.mPermissionManager.addOnPermissionsChangeListener(onPermissionsChangeListenerDelegate);
                this.mPermissionListeners.put(onPermissionsChangedListener, onPermissionsChangeListenerDelegate);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeOnPermissionsChangeListener(android.content.pm.PackageManager.OnPermissionsChangedListener onPermissionsChangedListener) {
        synchronized (this.mPermissionListeners) {
            android.permission.IOnPermissionsChangeListener iOnPermissionsChangeListener = this.mPermissionListeners.get(onPermissionsChangedListener);
            if (iOnPermissionsChangeListener != null) {
                try {
                    this.mPermissionManager.removeOnPermissionsChangeListener(iOnPermissionsChangeListener);
                    this.mPermissionListeners.remove(onPermissionsChangedListener);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @android.annotation.SystemApi
    public int getRuntimePermissionsVersion() {
        try {
            return this.mPackageManager.getRuntimePermissionsVersion(this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setRuntimePermissionsVersion(int i) {
        try {
            this.mPackageManager.setRuntimePermissionsVersion(i, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.permission.PermissionManager.SplitPermissionInfo> getSplitPermissions() {
        if (this.mSplitPermissionInfos != null) {
            return this.mSplitPermissionInfos;
        }
        try {
            this.mSplitPermissionInfos = splitPermissionInfoListToNonParcelableList(android.app.ActivityThread.getPermissionManager().getSplitPermissions());
            return this.mSplitPermissionInfos;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(LOG_TAG, "Error getting split permissions", e);
            return java.util.Collections.emptyList();
        }
    }

    public void initializeUsageHelper() {
        if (this.mUsageHelper == null) {
            this.mUsageHelper = new android.permission.PermissionUsageHelper(this.mContext);
        }
    }

    public void tearDownUsageHelper() {
        if (this.mUsageHelper != null) {
            this.mUsageHelper.tearDown();
            this.mUsageHelper = null;
        }
    }

    public java.util.List<android.permission.PermissionGroupUsage> getIndicatorAppOpUsageData() {
        return getIndicatorAppOpUsageData(new android.media.AudioManager().isMicrophoneMute());
    }

    public java.util.List<android.permission.PermissionGroupUsage> getIndicatorAppOpUsageData(boolean z) {
        initializeUsageHelper();
        return this.mUsageHelper.getOpUsageDataByDevice(!z, android.companion.virtual.VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT);
    }

    public static boolean shouldShowPackageForIndicatorCached(android.content.Context context, java.lang.String str) {
        return !getIndicatorExemptedPackages(context).contains(str);
    }

    public static java.util.Set<java.lang.String> getIndicatorExemptedPackages(android.content.Context context) {
        updateIndicatorExemptedPackages(context);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        arraySet.add("android");
        for (int i = 0; i < INDICATOR_EXEMPTED_PACKAGES.length; i++) {
            java.lang.String str = INDICATOR_EXEMPTED_PACKAGES[i];
            if (str != null) {
                arraySet.add(str);
            }
        }
        for (java.lang.String str2 : sLocationProviderPkgNames) {
            if (str2 != null) {
                arraySet.add(str2);
            }
        }
        for (java.lang.String str3 : sLocationExtraPkgNames) {
            if (str3 != null) {
                arraySet.add(str3);
            }
        }
        return arraySet;
    }

    public static void updateIndicatorExemptedPackages(android.content.Context context) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        if (sLastIndicatorUpdateTime == -1 || elapsedRealtime - sLastIndicatorUpdateTime > EXEMPTED_INDICATOR_ROLE_UPDATE_FREQUENCY_MS) {
            sLastIndicatorUpdateTime = elapsedRealtime;
            for (int i = 0; i < EXEMPTED_ROLES.length; i++) {
                INDICATOR_EXEMPTED_PACKAGES[i] = context.getString(EXEMPTED_ROLES[i]);
            }
            sLocationProviderPkgNames = context.getResources().getStringArray(com.android.internal.R.array.config_locationProviderPackageNames);
            sLocationExtraPkgNames = context.getResources().getStringArray(com.android.internal.R.array.config_locationExtraPackageNames);
        }
    }

    @android.annotation.SystemApi
    public java.util.Set<java.lang.String> getAutoRevokeExemptionRequestedPackages() {
        try {
            return com.android.internal.util.CollectionUtils.toSet(this.mPermissionManager.getAutoRevokeExemptionRequestedPackages(this.mContext.getUser().getIdentifier()));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.Set<java.lang.String> getAutoRevokeExemptionGrantedPackages() {
        try {
            return com.android.internal.util.CollectionUtils.toSet(this.mPermissionManager.getAutoRevokeExemptionGrantedPackages(this.mContext.getUser().getIdentifier()));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private java.util.List<android.permission.PermissionManager.SplitPermissionInfo> splitPermissionInfoListToNonParcelableList(java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> list) {
        int size = list.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(new android.permission.PermissionManager.SplitPermissionInfo(list.get(i)));
        }
        return arrayList;
    }

    public static java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> splitPermissionInfoListToParcelableList(java.util.List<android.permission.PermissionManager.SplitPermissionInfo> list) {
        int size = list.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(size);
        for (int i = 0; i < size; i++) {
            android.permission.PermissionManager.SplitPermissionInfo splitPermissionInfo = list.get(i);
            arrayList.add(new android.content.pm.permission.SplitPermissionInfoParcelable(splitPermissionInfo.getSplitPermission(), splitPermissionInfo.getNewPermissions(), splitPermissionInfo.getTargetSdk()));
        }
        return arrayList;
    }

    public static final class SplitPermissionInfo {
        private final android.content.pm.permission.SplitPermissionInfoParcelable mSplitPermissionInfoParcelable;

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.mSplitPermissionInfoParcelable.equals(((android.permission.PermissionManager.SplitPermissionInfo) obj).mSplitPermissionInfoParcelable);
        }

        public int hashCode() {
            return this.mSplitPermissionInfoParcelable.hashCode();
        }

        public java.lang.String getSplitPermission() {
            return this.mSplitPermissionInfoParcelable.getSplitPermission();
        }

        public java.util.List<java.lang.String> getNewPermissions() {
            return this.mSplitPermissionInfoParcelable.getNewPermissions();
        }

        public int getTargetSdk() {
            return this.mSplitPermissionInfoParcelable.getTargetSdk();
        }

        public SplitPermissionInfo(java.lang.String str, java.util.List<java.lang.String> list, int i) {
            this(new android.content.pm.permission.SplitPermissionInfoParcelable(str, list, i));
        }

        private SplitPermissionInfo(android.content.pm.permission.SplitPermissionInfoParcelable splitPermissionInfoParcelable) {
            this.mSplitPermissionInfoParcelable = splitPermissionInfoParcelable;
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void startOneTimePermissionSession(java.lang.String str, long j, int i, int i2) {
        startOneTimePermissionSession(str, j, -1L, i, i2);
    }

    @android.annotation.SystemApi
    public void startOneTimePermissionSession(java.lang.String str, long j, long j2, int i, int i2) {
        try {
            this.mPermissionManager.startOneTimePermissionSession(str, this.mContext.getDeviceId(), this.mContext.getUserId(), j, j2);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void stopOneTimePermissionSession(java.lang.String str) {
        try {
            this.mPermissionManager.stopOneTimePermissionSession(str, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int checkDeviceIdentifierAccess(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2) {
        return this.mLegacyPermissionManager.checkDeviceIdentifierAccess(str, str2, str3, i, i2);
    }

    public android.content.AttributionSource registerAttributionSource(android.content.AttributionSource attributionSource) {
        try {
            if (android.permission.flags.Flags.serverSideAttributionRegistration()) {
                return attributionSource.withToken(this.mPermissionManager.registerAttributionSource(attributionSource.asState()));
            }
            android.content.AttributionSource withToken = attributionSource.withToken(new android.os.Binder());
            this.mPermissionManager.registerAttributionSource(withToken.asState());
            return withToken;
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return attributionSource;
        }
    }

    public boolean isRegisteredAttributionSource(android.content.AttributionSource attributionSource) {
        try {
            return this.mPermissionManager.isRegisteredAttributionSource(attributionSource.asState());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void revokePostNotificationPermissionWithoutKillForTest(java.lang.String str, int i) {
        try {
            this.mPermissionManager.revokePostNotificationPermissionWithoutKillForTest(str, i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkPermissionUncached(java.lang.String str, int i, int i2, int i3) {
        android.app.IActivityManager service = android.app.ActivityManager.getService();
        if (service == null) {
            int appId = android.os.UserHandle.getAppId(i2);
            if (appId != 0 && appId != 1000) {
                android.util.Slog.w(LOG_TAG, "Missing ActivityManager; assuming " + i2 + " does not hold " + str);
                return -1;
            }
            if (sShouldWarnMissingActivityManager) {
                android.util.Slog.w(LOG_TAG, "Missing ActivityManager; assuming " + i2 + " holds " + str);
                sShouldWarnMissingActivityManager = false;
            }
            return 0;
        }
        try {
            sShouldWarnMissingActivityManager = true;
            return service.checkPermissionForDevice(str, i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static final class PermissionQuery {
        final int deviceId;
        final java.lang.String permission;
        final int pid;
        final int uid;

        PermissionQuery(java.lang.String str, int i, int i2, int i3) {
            this.permission = str;
            this.pid = i;
            this.uid = i2;
            this.deviceId = i3;
        }

        public java.lang.String toString() {
            return android.text.TextUtils.formatSimple("PermissionQuery(permission=\"%s\", pid=%d, uid=%d, deviceId=%d)", this.permission, java.lang.Integer.valueOf(this.pid), java.lang.Integer.valueOf(this.uid), java.lang.Integer.valueOf(this.deviceId));
        }

        public int hashCode() {
            return java.util.Objects.hash(this.permission, java.lang.Integer.valueOf(this.uid), java.lang.Integer.valueOf(this.deviceId));
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            try {
                android.permission.PermissionManager.PermissionQuery permissionQuery = (android.permission.PermissionManager.PermissionQuery) obj;
                return this.uid == permissionQuery.uid && this.deviceId == permissionQuery.deviceId && java.util.Objects.equals(this.permission, permissionQuery.permission);
            } catch (java.lang.ClassCastException e) {
                return false;
            }
        }
    }

    public static int checkPermission(java.lang.String str, int i, int i2, int i3) {
        return sPermissionCache.query(new android.permission.PermissionManager.PermissionQuery(str, i, i2, i3)).intValue();
    }

    @android.annotation.SystemApi
    public java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> getAllPermissionStates(java.lang.String str, java.lang.String str2) {
        try {
            return this.mPermissionManager.getAllPermissionStates(str, str2, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void disablePermissionCache() {
        sPermissionCache.disableLocal();
    }

    private static final class PackageNamePermissionQuery {
        final java.lang.String permName;
        final java.lang.String persistentDeviceId;
        final java.lang.String pkgName;
        final int userId;

        PackageNamePermissionQuery(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
            this.permName = str;
            this.pkgName = str2;
            this.persistentDeviceId = str3;
            this.userId = i;
        }

        public java.lang.String toString() {
            return android.text.TextUtils.formatSimple("PackageNamePermissionQuery(pkgName=\"%s\", permName=\"%s\", persistentDeviceId=%s, userId=%s\")", this.pkgName, this.permName, this.persistentDeviceId, java.lang.Integer.valueOf(this.userId));
        }

        public int hashCode() {
            return java.util.Objects.hash(this.permName, this.pkgName, this.persistentDeviceId, java.lang.Integer.valueOf(this.userId));
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            try {
                android.permission.PermissionManager.PackageNamePermissionQuery packageNamePermissionQuery = (android.permission.PermissionManager.PackageNamePermissionQuery) obj;
                return java.util.Objects.equals(this.permName, packageNamePermissionQuery.permName) && java.util.Objects.equals(this.pkgName, packageNamePermissionQuery.pkgName) && java.util.Objects.equals(this.persistentDeviceId, packageNamePermissionQuery.persistentDeviceId) && this.userId == packageNamePermissionQuery.userId;
            } catch (java.lang.ClassCastException e) {
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkPackageNamePermissionUncached(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        try {
            return android.app.ActivityThread.getPermissionManager().checkPermission(str2, str, str3, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int checkPackageNamePermission(java.lang.String str, java.lang.String str2, int i, int i2) {
        return sPackageNamePermissionCache.query(new android.permission.PermissionManager.PackageNamePermissionQuery(str, str2, getPersistentDeviceId(i), i2)).intValue();
    }

    private java.lang.String getPersistentDeviceId(int i) {
        if (i == 0) {
            return android.companion.virtual.VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT;
        }
        if (android.companion.virtual.flags.Flags.vdmPublicApis()) {
            android.companion.virtual.VirtualDeviceManager virtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) this.mContext.getSystemService(android.companion.virtual.VirtualDeviceManager.class);
            if (virtualDeviceManager == null) {
                return null;
            }
            android.companion.virtual.VirtualDevice virtualDevice = virtualDeviceManager.getVirtualDevice(i);
            if (virtualDevice == null) {
                android.util.Slog.e(LOG_TAG, "Virtual device is not found with device Id " + i);
                return null;
            }
            java.lang.String persistentDeviceId = virtualDevice.getPersistentDeviceId();
            if (persistentDeviceId == null) {
                android.util.Slog.e(LOG_TAG, "Cannot find persistent device Id for " + i);
            }
            return persistentDeviceId;
        }
        android.util.Slog.e(LOG_TAG, "vdmPublicApis flag is not enabled when device Id " + i + "is not default.");
        return null;
    }

    @android.annotation.SystemApi
    public int checkPermission(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return sPackageNamePermissionCache.query(new android.permission.PermissionManager.PackageNamePermissionQuery(str, str2, str3, this.mContext.getUserId())).intValue();
    }

    public static void disablePackageNamePermissionCache() {
        sPackageNamePermissionCache.disableLocal();
    }

    private final class OnPermissionsChangeListenerDelegate extends android.permission.IOnPermissionsChangeListener.Stub implements android.os.Handler.Callback {
        private static final int MSG_PERMISSIONS_CHANGED = 1;
        private final android.os.Handler mHandler;
        private final android.content.pm.PackageManager.OnPermissionsChangedListener mListener;

        public OnPermissionsChangeListenerDelegate(android.content.pm.PackageManager.OnPermissionsChangedListener onPermissionsChangedListener, android.os.Looper looper) {
            this.mListener = onPermissionsChangedListener;
            this.mHandler = new android.os.Handler(looper, this);
        }

        @Override // android.permission.IOnPermissionsChangeListener
        public void onPermissionsChanged(int i, java.lang.String str) {
            this.mHandler.obtainMessage(1, i, 0, str).sendToTarget();
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    this.mListener.onPermissionsChanged(message.arg1, message.obj.toString());
                    return true;
                default:
                    return false;
            }
        }
    }

    @android.annotation.SystemApi
    public static final class PermissionState implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.permission.PermissionManager.PermissionState> CREATOR = new android.os.Parcelable.Creator<android.permission.PermissionManager.PermissionState>() { // from class: android.permission.PermissionManager.PermissionState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.permission.PermissionManager.PermissionState createFromParcel(android.os.Parcel parcel) {
                return new android.permission.PermissionManager.PermissionState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.permission.PermissionManager.PermissionState[] newArray(int i) {
                return new android.permission.PermissionManager.PermissionState[i];
            }
        };
        private final int mFlags;
        private final boolean mGranted;

        public PermissionState(boolean z, int i) {
            this.mGranted = z;
            this.mFlags = i;
        }

        public boolean isGranted() {
            return this.mGranted;
        }

        public int getFlags() {
            return this.mFlags;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeBoolean(this.mGranted);
            parcel.writeInt(this.mFlags);
        }

        private PermissionState(android.os.Parcel parcel) {
            this(parcel.readBoolean(), parcel.readInt());
        }
    }
}
