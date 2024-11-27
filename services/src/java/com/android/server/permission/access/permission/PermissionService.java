package com.android.server.permission.access.permission;

/* compiled from: PermissionService.kt */
/* loaded from: classes2.dex */
public final class PermissionService implements com.android.server.pm.permission.PermissionManagerServiceInterface {
    private static final long BACKUP_TIMEOUT_MILLIS;

    @org.jetbrains.annotations.NotNull
    private static final java.util.Set<java.lang.String> DEVICE_AWARE_PERMISSIONS;

    @org.jetbrains.annotations.NotNull
    private static final android.util.ArrayMap<java.lang.String, java.lang.String> FULLER_PERMISSIONS;

    @org.jetbrains.annotations.NotNull
    private static final android.util.ArraySet<java.lang.String> NOTIFICATIONS_PERMISSIONS;

    @org.jetbrains.annotations.NotNull
    private final android.content.Context context;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.permission.DevicePermissionPolicy devicePolicy;
    private android.os.Handler handler;
    private android.os.HandlerThread handlerThread;

    @org.jetbrains.annotations.NotNull
    private final android.util.SparseBooleanArray isDelayedPermissionBackupFinished;
    private com.android.internal.logging.MetricsLogger metricsLogger;

    @com.android.internal.annotations.GuardedBy({"storageVolumeLock"})
    @org.jetbrains.annotations.NotNull
    private final android.util.ArraySet<java.lang.String> mountedStorageVolumes;
    private com.android.server.permission.access.permission.PermissionService.OnPermissionFlagsChangedListener onPermissionFlagsChangedListener;
    private com.android.server.permission.access.permission.PermissionService.OnPermissionsChangeListeners onPermissionsChangeListeners;
    private android.content.pm.PackageManagerInternal packageManagerInternal;
    private com.android.server.pm.PackageManagerLocal packageManagerLocal;
    private android.permission.PermissionControllerManager permissionControllerManager;
    private com.android.internal.compat.IPlatformCompat platformCompat;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.permission.AppIdPermissionPolicy policy;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.AccessCheckingService service;

    @org.jetbrains.annotations.NotNull
    private final java.lang.Object storageVolumeLock;

    @com.android.internal.annotations.GuardedBy({"storageVolumeLock"})
    @org.jetbrains.annotations.NotNull
    private final android.util.ArrayMap<java.lang.String, java.util.List<java.lang.String>> storageVolumePackageNames;
    private com.android.server.SystemConfig systemConfig;
    private com.android.server.pm.UserManagerInternal userManagerInternal;
    private com.android.server.pm.UserManagerService userManagerService;

    @org.jetbrains.annotations.Nullable
    private com.android.server.companion.virtual.VirtualDeviceManagerInternal virtualDeviceManagerInternal;

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.permission.PermissionService.Companion Companion = new com.android.server.permission.access.permission.PermissionService.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.permission.PermissionService.class.getSimpleName();
    private static final long BACKGROUND_RATIONALE_CHANGE_ID = 147316723;

    public PermissionService(@org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessCheckingService service) {
        this.service = service;
        com.android.server.permission.access.SchemePolicy schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar = this.service.getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, "permission");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar, "null cannot be cast to non-null type com.android.server.permission.access.permission.AppIdPermissionPolicy");
        this.policy = (com.android.server.permission.access.permission.AppIdPermissionPolicy) schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar;
        com.android.server.permission.access.SchemePolicy schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2 = this.service.getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, "device-permission");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2, "null cannot be cast to non-null type com.android.server.permission.access.permission.DevicePermissionPolicy");
        this.devicePolicy = (com.android.server.permission.access.permission.DevicePermissionPolicy) schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2;
        this.context = this.service.getContext();
        this.storageVolumeLock = new java.lang.Object();
        this.mountedStorageVolumes = new android.util.ArraySet<>();
        this.storageVolumePackageNames = new android.util.ArrayMap<>();
        this.isDelayedPermissionBackupFinished = new android.util.SparseBooleanArray();
    }

    public final void initialize() {
        this.metricsLogger = new com.android.internal.logging.MetricsLogger();
        this.packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.packageManagerLocal = (com.android.server.pm.PackageManagerLocal) com.android.server.LocalManagerRegistry.getManagerOrThrow(com.android.server.pm.PackageManagerLocal.class);
        this.platformCompat = com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService("platform_compat"));
        this.systemConfig = com.android.server.SystemConfig.getInstance();
        this.userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        this.userManagerService = com.android.server.pm.UserManagerService.getInstance();
        android.content.pm.PackageManager.invalidatePackageInfoCache();
        android.permission.PermissionManager.disablePackageNamePermissionCache();
        com.android.server.ServiceThread $this$initialize_u24lambda_u240 = new com.android.server.ServiceThread(LOG_TAG, 10, true);
        $this$initialize_u24lambda_u240.start();
        this.handlerThread = $this$initialize_u24lambda_u240;
        android.os.HandlerThread handlerThread = this.handlerThread;
        com.android.server.permission.access.permission.PermissionService.OnPermissionFlagsChangedListener onPermissionFlagsChangedListener = null;
        if (handlerThread == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("handlerThread");
            handlerThread = null;
        }
        this.handler = new android.os.Handler(handlerThread.getLooper());
        this.onPermissionsChangeListeners = new com.android.server.permission.access.permission.PermissionService.OnPermissionsChangeListeners(com.android.server.FgThread.get().getLooper());
        this.onPermissionFlagsChangedListener = new com.android.server.permission.access.permission.PermissionService.OnPermissionFlagsChangedListener();
        com.android.server.permission.access.permission.AppIdPermissionPolicy appIdPermissionPolicy = this.policy;
        com.android.server.permission.access.permission.PermissionService.OnPermissionFlagsChangedListener onPermissionFlagsChangedListener2 = this.onPermissionFlagsChangedListener;
        if (onPermissionFlagsChangedListener2 == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("onPermissionFlagsChangedListener");
            onPermissionFlagsChangedListener2 = null;
        }
        appIdPermissionPolicy.addOnPermissionFlagsChangedListener(onPermissionFlagsChangedListener2);
        com.android.server.permission.access.permission.DevicePermissionPolicy devicePermissionPolicy = this.devicePolicy;
        com.android.server.permission.access.permission.PermissionService.OnPermissionFlagsChangedListener onPermissionFlagsChangedListener3 = this.onPermissionFlagsChangedListener;
        if (onPermissionFlagsChangedListener3 == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("onPermissionFlagsChangedListener");
        } else {
            onPermissionFlagsChangedListener = onPermissionFlagsChangedListener3;
        }
        devicePermissionPolicy.addOnPermissionFlagsChangedListener(onPermissionFlagsChangedListener);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.NotNull
    public java.util.List<android.content.pm.PermissionGroupInfo> getAllPermissionGroups(int flags) {
        android.content.pm.PermissionGroupInfo permissionGroupInfo;
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot snapshot = packageManagerLocal.withUnfilteredSnapshot();
        int i = 0;
        try {
            int callingUid = android.os.Binder.getCallingUid();
            if (isUidInstantApp(snapshot, callingUid)) {
                java.util.List<android.content.pm.PermissionGroupInfo> emptyList = com.android.server.permission.jarjar.kotlin.collections.CollectionsKt__CollectionsKt.emptyList();
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
                return emptyList;
            }
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            com.android.server.permission.access.AccessState accessState = this_$iv.state;
            if (accessState == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                accessState = null;
            }
            com.android.server.permission.access.GetStateScope $this$getAllPermissionGroups_u24lambda_u244_u24lambda_u242 = new com.android.server.permission.access.GetStateScope(accessState);
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getAllPermissionGroups_u24lambda_u244_u24lambda_u242_u24lambda_u241 = this.policy;
            com.android.server.permission.access.immutable.IndexedMap permissionGroups = $this$getAllPermissionGroups_u24lambda_u244_u24lambda_u242_u24lambda_u241.getPermissionGroups($this$getAllPermissionGroups_u24lambda_u244_u24lambda_u242);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int index$iv$iv = 0;
            int size = permissionGroups.getSize();
            while (index$iv$iv < size) {
                java.lang.Object key$iv = permissionGroups.keyAt(index$iv$iv);
                java.lang.Object value$iv = permissionGroups.valueAt(index$iv$iv);
                android.content.pm.PermissionGroupInfo permissionGroup = (android.content.pm.PermissionGroupInfo) value$iv;
                int i2 = i;
                if (isPackageVisibleToUid(snapshot, permissionGroup.packageName, callingUid)) {
                    try {
                        permissionGroupInfo = generatePermissionGroupInfo(permissionGroup, flags);
                    } catch (java.lang.Throwable th) {
                        th = th;
                        java.lang.Throwable th2 = th;
                        try {
                            throw th2;
                        } catch (java.lang.Throwable th3) {
                            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, th2);
                            throw th3;
                        }
                    }
                } else {
                    permissionGroupInfo = null;
                }
                if (permissionGroupInfo != null) {
                    arrayList.add(permissionGroupInfo);
                }
                index$iv$iv++;
                i = i2;
            }
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
            return arrayList;
        } catch (java.lang.Throwable th4) {
            th = th4;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v2, types: [T, android.content.pm.PermissionGroupInfo] */
    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.Nullable
    public android.content.pm.PermissionGroupInfo getPermissionGroupInfo(@org.jetbrains.annotations.NotNull java.lang.String permissionGroupName, int flags) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$ObjectRef permissionGroup = new com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$ObjectRef();
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot snapshot = packageManagerLocal.withUnfilteredSnapshot();
        try {
            int callingUid = android.os.Binder.getCallingUid();
            if (isUidInstantApp(snapshot, callingUid)) {
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
                return null;
            }
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            com.android.server.permission.access.AccessState accessState = this_$iv.state;
            if (accessState == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                accessState = null;
            }
            com.android.server.permission.access.GetStateScope $this$getPermissionGroupInfo_u24lambda_u247_u24lambda_u246 = new com.android.server.permission.access.GetStateScope(accessState);
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getPermissionGroupInfo_u24lambda_u247_u24lambda_u246_u24lambda_u245 = this.policy;
            android.content.pm.PermissionGroupInfo permissionGroupInfo = $this$getPermissionGroupInfo_u24lambda_u247_u24lambda_u246_u24lambda_u245.getPermissionGroups($this$getPermissionGroupInfo_u24lambda_u247_u24lambda_u246).get(permissionGroupName);
            if (permissionGroupInfo == 0) {
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
                return null;
            }
            permissionGroup.element = permissionGroupInfo;
            if (!isPackageVisibleToUid(snapshot, ((android.content.pm.PermissionGroupInfo) permissionGroup.element).packageName, callingUid)) {
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
                return null;
            }
            com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
            return generatePermissionGroupInfo((android.content.pm.PermissionGroupInfo) permissionGroup.element, flags);
        } finally {
        }
    }

    private final android.content.pm.PermissionGroupInfo generatePermissionGroupInfo(android.content.pm.PermissionGroupInfo $this$generatePermissionGroupInfo, int flags) {
        android.content.pm.PermissionGroupInfo $this$generatePermissionGroupInfo_u24lambda_u248 = new android.content.pm.PermissionGroupInfo($this$generatePermissionGroupInfo);
        if (!com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 128)) {
            $this$generatePermissionGroupInfo_u24lambda_u248.metaData = null;
        }
        return $this$generatePermissionGroupInfo_u24lambda_u248;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v2, types: [T, com.android.server.permission.access.permission.Permission] */
    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.Nullable
    public android.content.pm.PermissionInfo getPermissionInfo(@org.jetbrains.annotations.NotNull java.lang.String permissionName, int flags, @org.jetbrains.annotations.NotNull java.lang.String opPackageName) {
        java.lang.Throwable th;
        com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$ObjectRef permission = new com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$ObjectRef();
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot snapshot = packageManagerLocal.withUnfilteredSnapshot();
        try {
            int callingUid = android.os.Binder.getCallingUid();
            if (isUidInstantApp(snapshot, callingUid)) {
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
                return null;
            }
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            com.android.server.permission.access.AccessState accessState = this_$iv.state;
            if (accessState == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                accessState = null;
            }
            com.android.server.permission.access.GetStateScope $this$getPermissionInfo_u24lambda_u2411_u24lambda_u2410 = new com.android.server.permission.access.GetStateScope(accessState);
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getPermissionInfo_u24lambda_u2411_u24lambda_u2410_u24lambda_u249 = this.policy;
            try {
                com.android.server.permission.access.permission.Permission permission2 = $this$getPermissionInfo_u24lambda_u2411_u24lambda_u2410_u24lambda_u249.getPermissions($this$getPermissionInfo_u24lambda_u2411_u24lambda_u2410).get(permissionName);
                if (permission2 == 0) {
                    com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
                    return null;
                }
                permission.element = permission2;
                com.android.server.permission.access.permission.Permission this_$iv2 = (com.android.server.permission.access.permission.Permission) permission.element;
                if (!isPackageVisibleToUid(snapshot, this_$iv2.getPermissionInfo().packageName, callingUid)) {
                    com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
                    return null;
                }
                try {
                    com.android.server.pm.pkg.PackageState packageState = getPackageState(snapshot, opPackageName);
                    com.android.server.pm.pkg.AndroidPackage opPackage = packageState != null ? packageState.getAndroidPackage() : null;
                    int i = 10000;
                    if (!isRootOrSystemOrShellUid(callingUid) && opPackage != null) {
                        i = opPackage.getTargetSdkVersion();
                    }
                    int targetSdkVersion = i;
                    com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                    com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
                    return generatePermissionInfo((com.android.server.permission.access.permission.Permission) permission.element, flags, targetSdkVersion);
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    th = th;
                    try {
                        throw th;
                    } catch (java.lang.Throwable th3) {
                        com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, th);
                        throw th3;
                    }
                }
            } catch (java.lang.Throwable th4) {
                th = th4;
                th = th;
                throw th;
            }
        } catch (java.lang.Throwable th5) {
            th = th5;
        }
    }

    static /* synthetic */ android.content.pm.PermissionInfo generatePermissionInfo$default(com.android.server.permission.access.permission.PermissionService permissionService, com.android.server.permission.access.permission.Permission permission, int i, int i2, int i3, java.lang.Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 10000;
        }
        return permissionService.generatePermissionInfo(permission, i, i2);
    }

    private final android.content.pm.PermissionInfo generatePermissionInfo(com.android.server.permission.access.permission.Permission $this$generatePermissionInfo, int flags, int targetSdkVersion) {
        int protection;
        android.content.pm.PermissionInfo $this$generatePermissionInfo_u24lambda_u2412 = new android.content.pm.PermissionInfo($this$generatePermissionInfo.getPermissionInfo());
        $this$generatePermissionInfo_u24lambda_u2412.flags |= 1073741824;
        if (!com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 128)) {
            $this$generatePermissionInfo_u24lambda_u2412.metaData = null;
        }
        if (targetSdkVersion < 26 && (protection = $this$generatePermissionInfo_u24lambda_u2412.getProtection()) != 2) {
            $this$generatePermissionInfo_u24lambda_u2412.protectionLevel = protection;
        }
        return $this$generatePermissionInfo_u24lambda_u2412;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00e5 A[Catch: all -> 0x003a, TRY_LEAVE, TryCatch #0 {all -> 0x003a, blocks: (B:6:0x0016, B:11:0x0026, B:13:0x0032, B:14:0x003e, B:16:0x0044, B:21:0x005b, B:25:0x0068, B:27:0x0086, B:29:0x00b0, B:31:0x00be, B:33:0x00e5), top: B:5:0x0016 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00e9 A[SYNTHETIC] */
    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public java.util.List<android.content.pm.PermissionInfo> queryPermissionsByGroup(@org.jetbrains.annotations.Nullable java.lang.String permissionGroupName, int flags) {
        int index$iv$iv;
        int i;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv;
        android.content.pm.PermissionInfo permissionInfo;
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot snapshot = packageManagerLocal.withUnfilteredSnapshot();
        try {
            int callingUid = android.os.Binder.getCallingUid();
            if (isUidInstantApp(snapshot, callingUid)) {
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
                return null;
            }
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            com.android.server.permission.access.AccessState accessState = this_$iv.state;
            if (accessState == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                accessState = null;
            }
            com.android.server.permission.access.GetStateScope $this$queryPermissionsByGroup_u24lambda_u2417_u24lambda_u2415 = new com.android.server.permission.access.GetStateScope(accessState);
            if (permissionGroupName != null) {
                com.android.server.permission.access.permission.AppIdPermissionPolicy $this$queryPermissionsByGroup_u24lambda_u2417_u24lambda_u2415_u24lambda_u2413 = this.policy;
                android.content.pm.PermissionGroupInfo permissionGroup = $this$queryPermissionsByGroup_u24lambda_u2417_u24lambda_u2415_u24lambda_u2413.getPermissionGroups($this$queryPermissionsByGroup_u24lambda_u2417_u24lambda_u2415).get(permissionGroupName);
                if (permissionGroup == null) {
                    com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
                    return null;
                }
                if (!isPackageVisibleToUid(snapshot, permissionGroup.packageName, callingUid)) {
                    com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
                    return null;
                }
            }
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$queryPermissionsByGroup_u24lambda_u2417_u24lambda_u2415_u24lambda_u2414 = this.policy;
            com.android.server.permission.access.immutable.IndexedMap permissions = $this$queryPermissionsByGroup_u24lambda_u2417_u24lambda_u2415_u24lambda_u2414.getPermissions($this$queryPermissionsByGroup_u24lambda_u2417_u24lambda_u2415);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv2 = permissions;
            int size = $this$forEachIndexed$iv$iv2.getSize();
            int index$iv$iv2 = 0;
            while (index$iv$iv2 < size) {
                java.lang.Object key$iv = $this$forEachIndexed$iv$iv2.keyAt(index$iv$iv2);
                java.lang.Object value$iv = $this$forEachIndexed$iv$iv2.valueAt(index$iv$iv2);
                com.android.server.permission.access.permission.Permission permission = (com.android.server.permission.access.permission.Permission) value$iv;
                if (!com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(permission.getPermissionInfo().group, permissionGroupName)) {
                    index$iv$iv = index$iv$iv2;
                    i = size;
                    $this$forEachIndexed$iv$iv = $this$forEachIndexed$iv$iv2;
                } else if (isPackageVisibleToUid(snapshot, permission.getPermissionInfo().packageName, callingUid)) {
                    index$iv$iv = index$iv$iv2;
                    i = size;
                    $this$forEachIndexed$iv$iv = $this$forEachIndexed$iv$iv2;
                    permissionInfo = generatePermissionInfo$default(this, permission, flags, 0, 2, null);
                    if (permissionInfo == null) {
                        arrayList.add(permissionInfo);
                    }
                    index$iv$iv2 = index$iv$iv + 1;
                    $this$forEachIndexed$iv$iv2 = $this$forEachIndexed$iv$iv;
                    size = i;
                } else {
                    index$iv$iv = index$iv$iv2;
                    i = size;
                    $this$forEachIndexed$iv$iv = $this$forEachIndexed$iv$iv2;
                }
                permissionInfo = null;
                if (permissionInfo == null) {
                }
                index$iv$iv2 = index$iv$iv + 1;
                $this$forEachIndexed$iv$iv2 = $this$forEachIndexed$iv$iv;
                size = i;
            }
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
            return arrayList;
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.NotNull
    public java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtection(int protection) {
        int index$iv$iv$iv;
        android.content.pm.PermissionInfo permissionInfo;
        com.android.server.permission.access.AccessCheckingService this_$iv$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getPermissionsWithProtectionOrProtectionFlags_u24lambda_u2421$iv = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getPermissionsWithProtectionOrProtectionFlags_u24lambda_u2421_u24lambda_u2420$iv = this.policy;
        com.android.server.permission.access.immutable.IndexedMap permissions$iv = $this$getPermissionsWithProtectionOrProtectionFlags_u24lambda_u2421_u24lambda_u2420$iv.getPermissions($this$getPermissionsWithProtectionOrProtectionFlags_u24lambda_u2421$iv);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = permissions$iv.getSize();
        int index$iv$iv$iv2 = 0;
        while (index$iv$iv$iv2 < size) {
            java.lang.Object key$iv$iv = permissions$iv.keyAt(index$iv$iv$iv2);
            java.lang.Object value$iv$iv = permissions$iv.valueAt(index$iv$iv$iv2);
            com.android.server.permission.access.permission.Permission permission$iv = (com.android.server.permission.access.permission.Permission) value$iv$iv;
            if (permission$iv.getPermissionInfo().getProtection() == protection) {
                index$iv$iv$iv = index$iv$iv$iv2;
                permissionInfo = generatePermissionInfo$default(this, permission$iv, 0, 0, 2, null);
            } else {
                index$iv$iv$iv = index$iv$iv$iv2;
                permissionInfo = null;
            }
            if (permissionInfo != null) {
                arrayList.add(permissionInfo);
            }
            index$iv$iv$iv2 = index$iv$iv$iv + 1;
        }
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.NotNull
    public java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtectionFlags(int protectionFlags) {
        int index$iv$iv$iv;
        android.content.pm.PermissionInfo permissionInfo;
        com.android.server.permission.access.AccessCheckingService this_$iv$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getPermissionsWithProtectionOrProtectionFlags_u24lambda_u2421$iv = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getPermissionsWithProtectionOrProtectionFlags_u24lambda_u2421_u24lambda_u2420$iv = this.policy;
        com.android.server.permission.access.immutable.IndexedMap permissions$iv = $this$getPermissionsWithProtectionOrProtectionFlags_u24lambda_u2421_u24lambda_u2420$iv.getPermissions($this$getPermissionsWithProtectionOrProtectionFlags_u24lambda_u2421$iv);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = permissions$iv.getSize();
        int index$iv$iv$iv2 = 0;
        while (index$iv$iv$iv2 < size) {
            java.lang.Object key$iv$iv = permissions$iv.keyAt(index$iv$iv$iv2);
            java.lang.Object value$iv$iv = permissions$iv.valueAt(index$iv$iv$iv2);
            com.android.server.permission.access.permission.Permission permission$iv = (com.android.server.permission.access.permission.Permission) value$iv$iv;
            if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission$iv.getPermissionInfo().getProtectionFlags(), protectionFlags)) {
                index$iv$iv$iv = index$iv$iv$iv2;
                permissionInfo = generatePermissionInfo$default(this, permission$iv, 0, 0, 2, null);
            } else {
                index$iv$iv$iv = index$iv$iv$iv2;
                permissionInfo = null;
            }
            if (permissionInfo != null) {
                arrayList.add(permissionInfo);
            }
            index$iv$iv$iv2 = index$iv$iv$iv + 1;
        }
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.NotNull
    public int[] getPermissionGids(@org.jetbrains.annotations.NotNull java.lang.String permissionName, int userId) {
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getPermissionGids_u24lambda_u2424 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getPermissionGids_u24lambda_u2424_u24lambda_u2423 = this.policy;
        com.android.server.permission.access.permission.Permission permission = $this$getPermissionGids_u24lambda_u2424_u24lambda_u2423.getPermissions($this$getPermissionGids_u24lambda_u2424).get(permissionName);
        return permission == null ? libcore.util.EmptyArray.INT : permission.getGidsForUser(userId);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.NotNull
    public java.util.Set<java.lang.String> getInstalledPermissions(@org.jetbrains.annotations.NotNull java.lang.String packageName) {
        if (packageName == null) {
            throw new java.lang.IllegalArgumentException("packageName cannot be null".toString());
        }
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getInstalledPermissions_u24lambda_u2427 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getInstalledPermissions_u24lambda_u2427_u24lambda_u2426 = this.policy;
        com.android.server.permission.access.immutable.IndexedMap permissions = $this$getInstalledPermissions_u24lambda_u2427_u24lambda_u2426.getPermissions($this$getInstalledPermissions_u24lambda_u2427);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int size = permissions.getSize();
        for (int index$iv$iv = 0; index$iv$iv < size; index$iv$iv++) {
            java.lang.Object key$iv = permissions.keyAt(index$iv$iv);
            java.lang.Object value$iv = permissions.valueAt(index$iv$iv);
            com.android.server.permission.access.permission.Permission permission = (com.android.server.permission.access.permission.Permission) value$iv;
            java.lang.String str = com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(permission.getPermissionInfo().packageName, packageName) ? permission.getPermissionInfo().name : null;
            if (str != null) {
                arraySet.add(str);
            }
        }
        return arraySet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v4, types: [T, com.android.server.permission.access.permission.Permission] */
    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean addPermission(@org.jetbrains.annotations.NotNull android.content.pm.PermissionInfo permissionInfo, boolean async) {
        java.lang.String permissionName = permissionInfo.name;
        if (permissionName == null) {
            throw new java.lang.IllegalArgumentException("permissionName cannot be null".toString());
        }
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        com.android.server.permission.access.AccessState accessState = null;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot it = packageManagerLocal.withUnfilteredSnapshot();
        try {
            boolean isUidInstantApp = isUidInstantApp(it, callingUid);
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
            if (isUidInstantApp) {
                throw new java.lang.SecurityException("Instant apps cannot add permissions");
            }
            if (permissionInfo.labelRes == 0 && permissionInfo.nonLocalizedLabel == null) {
                throw new java.lang.SecurityException("Label must be specified in permission");
            }
            com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$ObjectRef oldPermission = new com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$ObjectRef();
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            synchronized (this_$iv.stateLock) {
                try {
                    com.android.server.permission.access.AccessState accessState2 = this_$iv.state;
                    if (accessState2 == null) {
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    } else {
                        accessState = accessState2;
                    }
                    com.android.server.permission.access.AccessState oldState$iv = accessState;
                    com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                    com.android.server.permission.access.MutateStateScope $this$addPermission_u24lambda_u2433 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                    com.android.server.permission.access.permission.Permission permissionTree = getAndEnforcePermissionTree($this$addPermission_u24lambda_u2433, permissionName);
                    enforcePermissionTreeSize($this$addPermission_u24lambda_u2433, permissionInfo, permissionTree);
                    com.android.server.permission.access.permission.AppIdPermissionPolicy $this$addPermission_u24lambda_u2433_u24lambda_u2431 = this.policy;
                    oldPermission.element = $this$addPermission_u24lambda_u2433_u24lambda_u2431.getPermissions($this$addPermission_u24lambda_u2433).get(permissionName);
                    if (oldPermission.element != 0) {
                        if ((((com.android.server.permission.access.permission.Permission) oldPermission.element).getType() == 2 ? 1 : null) == null) {
                            throw new java.lang.SecurityException("Not allowed to modify non-dynamic permission " + permissionName);
                        }
                    }
                    permissionInfo.packageName = permissionTree.getPermissionInfo().packageName;
                    permissionInfo.protectionLevel = android.content.pm.PermissionInfo.fixProtectionLevel(permissionInfo.protectionLevel);
                    com.android.server.permission.access.permission.Permission newPermission = new com.android.server.permission.access.permission.Permission(permissionInfo, true, 2, permissionTree.getAppId(), null, false, 48, null);
                    com.android.server.permission.access.permission.AppIdPermissionPolicy $this$addPermission_u24lambda_u2433_u24lambda_u2432 = this.policy;
                    $this$addPermission_u24lambda_u2433_u24lambda_u2432.addPermission($this$addPermission_u24lambda_u2433, newPermission, !async);
                    this_$iv.persistence.write(newState$iv);
                    this_$iv.state = newState$iv;
                    com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                    $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                    com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return oldPermission.element == 0;
        } catch (java.lang.Throwable th2) {
            try {
                throw th2;
            } catch (java.lang.Throwable th3) {
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, th2);
                throw th3;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void removePermission(@org.jetbrains.annotations.NotNull java.lang.String permissionName) {
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        com.android.server.permission.access.AccessState oldState$iv = null;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot it = packageManagerLocal.withUnfilteredSnapshot();
        try {
            boolean isUidInstantApp = isUidInstantApp(it, callingUid);
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
            if (isUidInstantApp) {
                throw new java.lang.SecurityException("Instant applications don't have access to this method");
            }
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            synchronized (this_$iv.stateLock) {
                try {
                    com.android.server.permission.access.AccessState accessState = this_$iv.state;
                    if (accessState == null) {
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    } else {
                        oldState$iv = accessState;
                    }
                    com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                    com.android.server.permission.access.MutateStateScope $this$removePermission_u24lambda_u2437 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                    getAndEnforcePermissionTree($this$removePermission_u24lambda_u2437, permissionName);
                    com.android.server.permission.access.permission.AppIdPermissionPolicy $this$removePermission_u24lambda_u2437_u24lambda_u2435 = this.policy;
                    com.android.server.permission.access.permission.Permission permission = $this$removePermission_u24lambda_u2437_u24lambda_u2435.getPermissions($this$removePermission_u24lambda_u2437).get(permissionName);
                    if (permission != null) {
                        if (!(permission.getType() == 2)) {
                            throw new java.lang.SecurityException("Not allowed to modify non-dynamic permission " + permissionName);
                        }
                        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$removePermission_u24lambda_u2437_u24lambda_u2436 = this.policy;
                        $this$removePermission_u24lambda_u2437_u24lambda_u2436.removePermission($this$removePermission_u24lambda_u2437, permission);
                    }
                    this_$iv.persistence.write(newState$iv);
                    this_$iv.state = newState$iv;
                    com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                    $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                    com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                } finally {
                }
            }
        } finally {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final com.android.server.permission.access.permission.Permission getAndEnforcePermissionTree(com.android.server.permission.access.GetStateScope $this$getAndEnforcePermissionTree, java.lang.String permissionName) {
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getAndEnforcePermissionTree_u24lambda_u2438 = this.policy;
        com.android.server.permission.access.permission.Permission permissionTree = $this$getAndEnforcePermissionTree_u24lambda_u2438.findPermissionTree($this$getAndEnforcePermissionTree, permissionName);
        if (permissionTree != null && permissionTree.getAppId() == android.os.UserHandle.getAppId(callingUid)) {
            return permissionTree;
        }
        throw new java.lang.SecurityException("Calling UID " + callingUid + " is not allowed to add to or remove from the permission tree");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void enforcePermissionTreeSize(com.android.server.permission.access.GetStateScope $this$enforcePermissionTreeSize, android.content.pm.PermissionInfo permissionInfo, com.android.server.permission.access.permission.Permission permissionTree) {
        if (permissionTree.getAppId() != 1000) {
            int permissionTreeFootprint = calculatePermissionTreeFootprint($this$enforcePermissionTreeSize, permissionTree);
            if (permissionInfo.calculateFootprint() + permissionTreeFootprint > 32768) {
                throw new java.lang.SecurityException("Permission tree size cap exceeded");
            }
        }
    }

    private final int calculatePermissionTreeFootprint(com.android.server.permission.access.GetStateScope $this$calculatePermissionTreeFootprint, com.android.server.permission.access.permission.Permission permissionTree) {
        int size = 0;
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$calculatePermissionTreeFootprint_u24lambda_u2440 = this.policy;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv = $this$calculatePermissionTreeFootprint_u24lambda_u2440.getPermissions($this$calculatePermissionTreeFootprint);
        int size2 = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size2; index$iv++) {
            $this$forEachIndexed$iv.keyAt(index$iv);
            com.android.server.permission.access.permission.Permission permission = $this$forEachIndexed$iv.valueAt(index$iv);
            if (permissionTree.getAppId() == permission.getAppId()) {
                size += permission.getPermissionInfo().name.length() + permission.getPermissionInfo().calculateFootprint();
            }
        }
        return size;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int checkUidPermission(int uid, @org.jetbrains.annotations.NotNull java.lang.String permissionName, @org.jetbrains.annotations.NotNull java.lang.String deviceId) {
        int userId = android.os.UserHandle.getUserId(uid);
        com.android.server.pm.UserManagerInternal userManagerInternal = this.userManagerInternal;
        com.android.server.permission.access.AccessState accessState = null;
        if (userManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            userManagerInternal = null;
        }
        if (!userManagerInternal.exists(userId)) {
            return -1;
        }
        android.content.pm.PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            packageManagerInternal = null;
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageManagerInternal.getPackage(uid);
        if (androidPackage != null) {
            android.content.pm.PackageManagerInternal packageManagerInternal2 = this.packageManagerInternal;
            if (packageManagerInternal2 == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
                packageManagerInternal2 = null;
            }
            com.android.server.pm.pkg.PackageStateInternal packageState = packageManagerInternal2.getPackageStateInternal(androidPackage.getPackageName());
            if (packageState == null) {
                android.util.Slog.e(LOG_TAG, "checkUidPermission: PackageState not found for AndroidPackage " + androidPackage);
                return -1;
            }
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            com.android.server.permission.access.AccessState accessState2 = this_$iv.state;
            if (accessState2 == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            } else {
                accessState = accessState2;
            }
            com.android.server.permission.access.GetStateScope $this$checkUidPermission_u24lambda_u2441 = new com.android.server.permission.access.GetStateScope(accessState);
            boolean isPermissionGranted = isPermissionGranted($this$checkUidPermission_u24lambda_u2441, packageState, userId, permissionName, deviceId);
            return isPermissionGranted ? 0 : -1;
        }
        boolean isPermissionGranted2 = isSystemUidPermissionGranted(uid, permissionName);
        return isPermissionGranted2 ? 0 : -1;
    }

    private final boolean isSystemUidPermissionGranted(int uid, java.lang.String permissionName) {
        com.android.server.SystemConfig systemConfig = this.systemConfig;
        if (systemConfig == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
            systemConfig = null;
        }
        android.util.ArraySet uidPermissions = systemConfig.getSystemPermissions().get(uid);
        if (uidPermissions == null) {
            return false;
        }
        if (uidPermissions.contains(permissionName)) {
            return true;
        }
        java.lang.String fullerPermissionName = FULLER_PERMISSIONS.get(permissionName);
        return fullerPermissionName != null && uidPermissions.contains(fullerPermissionName);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int checkPermission(@org.jetbrains.annotations.NotNull java.lang.String packageName, @org.jetbrains.annotations.NotNull java.lang.String permissionName, @org.jetbrains.annotations.NotNull java.lang.String deviceId, int userId) {
        com.android.server.pm.UserManagerInternal userManagerInternal = this.userManagerInternal;
        com.android.server.permission.access.AccessState accessState = null;
        if (userManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            userManagerInternal = null;
        }
        if (!userManagerInternal.exists(userId)) {
            return -1;
        }
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.FilteredSnapshot it = withFilteredSnapshot(packageManagerLocal, android.os.Binder.getCallingUid(), userId);
        try {
            com.android.server.pm.pkg.PackageState packageState = it.getPackageState(packageName);
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
            if (packageState == null) {
                return -1;
            }
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            com.android.server.permission.access.AccessState accessState2 = this_$iv.state;
            if (accessState2 == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            } else {
                accessState = accessState2;
            }
            com.android.server.permission.access.GetStateScope $this$checkPermission_u24lambda_u2443 = new com.android.server.permission.access.GetStateScope(accessState);
            boolean isPermissionGranted = isPermissionGranted($this$checkPermission_u24lambda_u2443, packageState, userId, permissionName, deviceId);
            return isPermissionGranted ? 0 : -1;
        } catch (java.lang.Throwable th) {
            try {
                throw th;
            } catch (java.lang.Throwable th2) {
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, th);
                throw th2;
            }
        }
    }

    private final boolean isPermissionGranted(com.android.server.permission.access.GetStateScope $this$isPermissionGranted, com.android.server.pm.pkg.PackageState packageState, int userId, java.lang.String permissionName, java.lang.String deviceId) {
        int appId = packageState.getAppId();
        boolean isInstantApp = packageState.getUserStateOrDefault(userId).isInstantApp();
        if (isSinglePermissionGranted($this$isPermissionGranted, appId, userId, isInstantApp, permissionName, deviceId)) {
            return true;
        }
        java.lang.String fullerPermissionName = FULLER_PERMISSIONS.get(permissionName);
        return fullerPermissionName != null && isSinglePermissionGranted($this$isPermissionGranted, appId, userId, isInstantApp, fullerPermissionName, deviceId);
    }

    private final boolean isSinglePermissionGranted(com.android.server.permission.access.GetStateScope $this$isSinglePermissionGranted, int appId, int userId, boolean isInstantApp, java.lang.String permissionName, java.lang.String deviceId) {
        int flags = getPermissionFlagsWithPolicy($this$isSinglePermissionGranted, appId, userId, permissionName, deviceId);
        if (!com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isPermissionGranted(flags)) {
            return false;
        }
        if (isInstantApp) {
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$isSinglePermissionGranted_u24lambda_u2444 = this.policy;
            com.android.server.permission.access.permission.Permission permission = $this$isSinglePermissionGranted_u24lambda_u2444.getPermissions($this$isSinglePermissionGranted).get(permissionName);
            return permission != null && com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 4096);
        }
        return true;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.NotNull
    public java.util.Set<java.lang.String> getGrantedPermissions(@org.jetbrains.annotations.NotNull java.lang.String packageName, int userId) {
        java.util.Set<java.lang.String> emptySet;
        java.util.Set<java.lang.String> emptySet2;
        if (packageName == null) {
            throw new java.lang.IllegalArgumentException("packageName cannot be null".toString());
        }
        com.android.internal.util.Preconditions.checkArgumentNonnegative(userId, "userId");
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot it = packageManagerLocal.withUnfilteredSnapshot();
        try {
            com.android.server.pm.pkg.PackageState packageState = getPackageState(it, packageName);
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
            if (packageState == null) {
                android.util.Slog.w(LOG_TAG, "getGrantedPermissions: Unknown package " + packageName);
                emptySet2 = com.android.server.permission.jarjar.kotlin.collections.SetsKt__SetsKt.emptySet();
                return emptySet2;
            }
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            com.android.server.permission.access.AccessState accessState = this_$iv.state;
            if (accessState == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                accessState = null;
            }
            com.android.server.permission.access.GetStateScope $this$getGrantedPermissions_u24lambda_u2449 = new com.android.server.permission.access.GetStateScope(accessState);
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getGrantedPermissions_u24lambda_u2449_u24lambda_u2447 = this.policy;
            com.android.server.permission.access.immutable.IndexedMap permissionFlags = $this$getGrantedPermissions_u24lambda_u2449_u24lambda_u2447.getUidPermissionFlags($this$getGrantedPermissions_u24lambda_u2449, packageState.getAppId(), userId);
            if (permissionFlags == null) {
                emptySet = com.android.server.permission.jarjar.kotlin.collections.SetsKt__SetsKt.emptySet();
                return emptySet;
            }
            android.util.ArraySet arraySet = new android.util.ArraySet();
            com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = permissionFlags;
            int size = $this$forEachIndexed$iv$iv.getSize();
            int index$iv$iv = 0;
            while (index$iv$iv < size) {
                java.lang.Object key$iv = $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
                java.lang.Object value$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
                ((java.lang.Number) value$iv).intValue();
                java.lang.String permissionName = (java.lang.String) key$iv;
                int index$iv$iv2 = index$iv$iv;
                int i = size;
                com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv2 = $this$forEachIndexed$iv$iv;
                android.util.ArraySet arraySet2 = arraySet;
                java.lang.String str = isPermissionGranted($this$getGrantedPermissions_u24lambda_u2449, packageState, userId, permissionName, "default:0") ? permissionName : null;
                if (str != null) {
                    arraySet2.add(str);
                }
                index$iv$iv = index$iv$iv2 + 1;
                arraySet = arraySet2;
                size = i;
                $this$forEachIndexed$iv$iv = $this$forEachIndexed$iv$iv2;
            }
            return arraySet;
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.NotNull
    public int[] getGidsForUid(int uid) {
        com.android.server.permission.access.permission.PermissionService permissionService = this;
        int appId = android.os.UserHandle.getAppId(uid);
        int userId = android.os.UserHandle.getUserId(uid);
        com.android.server.SystemConfig systemConfig = permissionService.systemConfig;
        com.android.server.permission.access.AccessState accessState = null;
        if (systemConfig == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
            systemConfig = null;
        }
        int[] globalGids = systemConfig.getGlobalGids();
        com.android.server.permission.access.AccessCheckingService this_$iv = permissionService.service;
        com.android.server.permission.access.AccessState accessState2 = this_$iv.state;
        if (accessState2 == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
        } else {
            accessState = accessState2;
        }
        com.android.server.permission.access.GetStateScope $this$getGidsForUid_u24lambda_u2453 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getGidsForUid_u24lambda_u2453_u24lambda_u2450 = permissionService.policy;
        com.android.server.permission.access.immutable.IndexedMap permissionFlags = $this$getGidsForUid_u24lambda_u2453_u24lambda_u2450.getUidPermissionFlags($this$getGidsForUid_u24lambda_u2453, appId, userId);
        if (permissionFlags == null) {
            int[] copyOf = java.util.Arrays.copyOf(globalGids, globalGids.length);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
            return copyOf;
        }
        android.util.IntArray gids = android.util.IntArray.wrap(globalGids);
        int index$iv = 0;
        int size = permissionFlags.getSize();
        while (index$iv < size) {
            java.lang.String keyAt = permissionFlags.keyAt(index$iv);
            int flags = permissionFlags.valueAt(index$iv).intValue();
            java.lang.String permissionName = keyAt;
            int appId2 = appId;
            if (com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isPermissionGranted(flags)) {
                com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getGidsForUid_u24lambda_u2453_u24lambda_u2452_u24lambda_u2451 = permissionService.policy;
                com.android.server.permission.access.permission.Permission permission = $this$getGidsForUid_u24lambda_u2453_u24lambda_u2452_u24lambda_u2451.getPermissions($this$getGidsForUid_u24lambda_u2453).get(permissionName);
                if (permission != null) {
                    int[] permissionGids = permission.getGidsForUser(userId);
                    if (!(permissionGids.length == 0)) {
                        gids.addAll(permissionGids);
                    }
                }
            }
            index$iv++;
            permissionService = this;
            appId = appId2;
        }
        return gids.toArray();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void grantRuntimePermission(@org.jetbrains.annotations.NotNull java.lang.String packageName, @org.jetbrains.annotations.NotNull java.lang.String permissionName, @org.jetbrains.annotations.NotNull java.lang.String deviceId, int userId) {
        setRuntimePermissionGranted$default(this, packageName, userId, permissionName, deviceId, true, false, null, 96, null);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void revokeRuntimePermission(@org.jetbrains.annotations.NotNull java.lang.String packageName, @org.jetbrains.annotations.NotNull java.lang.String permissionName, @org.jetbrains.annotations.NotNull java.lang.String deviceId, int userId, @org.jetbrains.annotations.Nullable java.lang.String reason) {
        setRuntimePermissionGranted$default(this, packageName, userId, permissionName, deviceId, false, false, reason, 32, null);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void revokePostNotificationPermissionWithoutKillForTest(@org.jetbrains.annotations.NotNull java.lang.String packageName, int userId) {
        setRuntimePermissionGranted$default(this, packageName, userId, "android.permission.POST_NOTIFICATIONS", "default:0", false, true, null, 64, null);
    }

    static /* synthetic */ void setRuntimePermissionGranted$default(com.android.server.permission.access.permission.PermissionService permissionService, java.lang.String str, int i, java.lang.String str2, java.lang.String str3, boolean z, boolean z2, java.lang.String str4, int i2, java.lang.Object obj) {
        boolean z3;
        java.lang.String str5;
        if ((i2 & 32) == 0) {
            z3 = z2;
        } else {
            z3 = false;
        }
        if ((i2 & 64) == 0) {
            str5 = str4;
        } else {
            str5 = null;
        }
        permissionService.setRuntimePermissionGranted(str, i, str2, str3, z, z3, str5);
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0101  */
    /* JADX WARN: Type inference failed for: r2v2, types: [T, com.android.server.pm.pkg.PackageState] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void setRuntimePermissionGranted(java.lang.String packageName, int userId, java.lang.String permissionName, java.lang.String deviceId, boolean isGranted, boolean skipKillUid, java.lang.String revokeReason) {
        java.lang.Object first;
        java.lang.Throwable th;
        boolean canManageRolePermission;
        com.android.server.permission.access.AccessCheckingService this_$iv;
        java.lang.String methodName = isGranted ? "grantRuntimePermission" : "revokeRuntimePermission";
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.pm.UserManagerInternal userManagerInternal = this.userManagerInternal;
        if (userManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            userManagerInternal = null;
        }
        if (!userManagerInternal.exists(userId)) {
            android.util.Slog.w(LOG_TAG, methodName + ": Unknown user " + userId);
            return;
        }
        enforceCallingOrSelfCrossUserPermission(userId, true, true, methodName);
        java.lang.String enforcedPermissionName = isGranted ? "android.permission.GRANT_RUNTIME_PERMISSIONS" : "android.permission.REVOKE_RUNTIME_PERMISSIONS";
        this.context.enforceCallingOrSelfPermission(enforcedPermissionName, methodName);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$ObjectRef packageState = new com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$ObjectRef();
        android.content.pm.PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            packageManagerInternal = null;
        }
        first = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt.first(packageManagerInternal.getKnownPackageNames(7, 0));
        java.lang.String permissionControllerPackageName = (java.lang.String) first;
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot snapshot = packageManagerLocal.withUnfilteredSnapshot();
        try {
            com.android.server.pm.PackageManagerLocal.FilteredSnapshot it = filtered(snapshot, callingUid, userId);
            try {
                ?? packageState2 = it.getPackageState(packageName);
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
                packageState.element = packageState2;
                com.android.server.pm.pkg.PackageState packageState3 = getPackageState(snapshot, permissionControllerPackageName);
                try {
                    com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                    com.android.server.permission.access.permission.PermissionService.OnPermissionFlagsChangedListener $this$setRuntimePermissionGranted_u24lambda_u2457_u24lambda_u2456 = null;
                    com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
                    com.android.server.pm.pkg.PackageState packageState4 = (com.android.server.pm.pkg.PackageState) packageState.element;
                    com.android.server.pm.pkg.AndroidPackage androidPackage = packageState4 != null ? packageState4.getAndroidPackage() : null;
                    if (androidPackage == null) {
                        android.util.Slog.w(LOG_TAG, methodName + ": Unknown package " + packageName);
                        return;
                    }
                    if (!isRootOrSystemUid(callingUid)) {
                        int appId = android.os.UserHandle.getAppId(callingUid);
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState3);
                        if (appId != packageState3.getAppId()) {
                            canManageRolePermission = false;
                            boolean overridePolicyFixed = this.context.checkCallingOrSelfPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY") != 0;
                            this_$iv = this.service;
                            synchronized (this_$iv.stateLock) {
                                try {
                                    com.android.server.permission.access.AccessState accessState = this_$iv.state;
                                    if (accessState == null) {
                                        try {
                                            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                                            accessState = null;
                                        } catch (java.lang.Throwable th2) {
                                            th = th2;
                                            throw th;
                                        }
                                    }
                                    com.android.server.permission.access.AccessState oldState$iv = accessState;
                                    com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                                    com.android.server.permission.access.MutateStateScope $this$setRuntimePermissionGranted_u24lambda_u2457 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                                    com.android.server.permission.access.permission.PermissionService.OnPermissionFlagsChangedListener onPermissionFlagsChangedListener = this.onPermissionFlagsChangedListener;
                                    if (onPermissionFlagsChangedListener == null) {
                                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("onPermissionFlagsChangedListener");
                                    } else {
                                        $this$setRuntimePermissionGranted_u24lambda_u2457_u24lambda_u2456 = onPermissionFlagsChangedListener;
                                    }
                                    if (skipKillUid) {
                                        $this$setRuntimePermissionGranted_u24lambda_u2457_u24lambda_u2456.skipKillRuntimePermissionRevokedUids($this$setRuntimePermissionGranted_u24lambda_u2457);
                                    }
                                    if (revokeReason != null) {
                                        $this$setRuntimePermissionGranted_u24lambda_u2457_u24lambda_u2456.addKillRuntimePermissionRevokedUidsReason($this$setRuntimePermissionGranted_u24lambda_u2457, revokeReason);
                                    }
                                    try {
                                        setRuntimePermissionGranted($this$setRuntimePermissionGranted_u24lambda_u2457, (com.android.server.pm.pkg.PackageState) packageState.element, userId, permissionName, deviceId, isGranted, canManageRolePermission, overridePolicyFixed, true, methodName);
                                        this_$iv.persistence.write(newState$iv);
                                    } catch (java.lang.Throwable th3) {
                                        th = th3;
                                    }
                                    try {
                                        this_$iv.state = newState$iv;
                                        com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                                        $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                                        com.android.server.permission.jarjar.kotlin.Unit unit2 = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                                        return;
                                    } catch (java.lang.Throwable th4) {
                                        th = th4;
                                        throw th;
                                    }
                                } catch (java.lang.Throwable th5) {
                                    th = th5;
                                }
                            }
                        }
                    }
                    canManageRolePermission = true;
                    if (this.context.checkCallingOrSelfPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY") != 0) {
                    }
                    this_$iv = this.service;
                    synchronized (this_$iv.stateLock) {
                    }
                } catch (java.lang.Throwable th6) {
                    th = th6;
                    try {
                        throw th;
                    } catch (java.lang.Throwable th7) {
                        com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, th);
                        throw th7;
                    }
                }
            } catch (java.lang.Throwable th8) {
                try {
                    throw th8;
                } catch (java.lang.Throwable th9) {
                    try {
                        com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, th8);
                        throw th9;
                    } catch (java.lang.Throwable th10) {
                        th = th10;
                        throw th;
                    }
                }
            }
        } catch (java.lang.Throwable th11) {
            th = th11;
        }
    }

    private final void setRequestedPermissionStates(com.android.server.pm.pkg.PackageState packageState, int userId, android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap) {
        java.lang.String permissionName;
        int index$iv;
        int i;
        android.util.ArrayMap $this$forEachIndexed$iv;
        com.android.server.permission.access.MutateStateScope $this$setRequestedPermissionStates_u24lambda_u2460;
        com.android.server.permission.access.MutableAccessState newState$iv;
        com.android.server.permission.access.AccessState oldState$iv;
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        synchronized (this_$iv.stateLock) {
            try {
                com.android.server.permission.access.AccessState accessState = this_$iv.state;
                if (accessState == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    accessState = null;
                }
                com.android.server.permission.access.AccessState oldState$iv2 = accessState;
                com.android.server.permission.access.MutableAccessState newState$iv2 = oldState$iv2.toMutable();
                com.android.server.permission.access.MutateStateScope $this$setRequestedPermissionStates_u24lambda_u24602 = new com.android.server.permission.access.MutateStateScope(oldState$iv2, newState$iv2);
                android.util.ArrayMap $this$forEachIndexed$iv2 = arrayMap;
                int size = $this$forEachIndexed$iv2.size();
                int index$iv2 = 0;
                while (index$iv2 < size) {
                    java.lang.String keyAt = $this$forEachIndexed$iv2.keyAt(index$iv2);
                    int permissionState = $this$forEachIndexed$iv2.valueAt(index$iv2).intValue();
                    java.lang.String permissionName2 = keyAt;
                    switch (permissionState) {
                        case 1:
                        case 2:
                            com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
                            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
                            if (!androidPackage.getRequestedPermissions().contains(permissionName2)) {
                                index$iv = index$iv2;
                                i = size;
                                $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                                $this$setRequestedPermissionStates_u24lambda_u2460 = $this$setRequestedPermissionStates_u24lambda_u24602;
                                newState$iv = newState$iv2;
                                oldState$iv = oldState$iv2;
                                break;
                            } else {
                                com.android.server.permission.access.permission.AppIdPermissionPolicy $this$setRequestedPermissionStates_u24lambda_u2460_u24lambda_u2459_u24lambda_u2458 = this.policy;
                                com.android.server.permission.access.permission.Permission permission = $this$setRequestedPermissionStates_u24lambda_u2460_u24lambda_u2459_u24lambda_u2458.getPermissions($this$setRequestedPermissionStates_u24lambda_u24602).get(permissionName2);
                                if (permission != null) {
                                    if (!com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 32)) {
                                        if (!(permission.getPermissionInfo().getProtection() == 1)) {
                                            if (!com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 64)) {
                                                index$iv = index$iv2;
                                                i = size;
                                                $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                                                $this$setRequestedPermissionStates_u24lambda_u2460 = $this$setRequestedPermissionStates_u24lambda_u24602;
                                                newState$iv = newState$iv2;
                                                oldState$iv = oldState$iv2;
                                                break;
                                            } else if (!com.android.server.pm.PackageInstallerService.INSTALLER_CHANGEABLE_APP_OP_PERMISSIONS.contains(permissionName2)) {
                                                index$iv = index$iv2;
                                                i = size;
                                                $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                                                $this$setRequestedPermissionStates_u24lambda_u2460 = $this$setRequestedPermissionStates_u24lambda_u24602;
                                                newState$iv = newState$iv2;
                                                oldState$iv = oldState$iv2;
                                                break;
                                            } else {
                                                setAppOpPermissionGranted($this$setRequestedPermissionStates_u24lambda_u24602, packageState, userId, permissionName2, permissionState == 1);
                                                index$iv = index$iv2;
                                                i = size;
                                                $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                                                $this$setRequestedPermissionStates_u24lambda_u2460 = $this$setRequestedPermissionStates_u24lambda_u24602;
                                                newState$iv = newState$iv2;
                                                oldState$iv = oldState$iv2;
                                                break;
                                            }
                                        } else {
                                            permissionName = permissionName2;
                                        }
                                    } else {
                                        permissionName = permissionName2;
                                    }
                                    if (permissionState != 1) {
                                        index$iv = index$iv2;
                                        i = size;
                                        $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                                        $this$setRequestedPermissionStates_u24lambda_u2460 = $this$setRequestedPermissionStates_u24lambda_u24602;
                                        newState$iv = newState$iv2;
                                        oldState$iv = oldState$iv2;
                                        break;
                                    } else {
                                        index$iv = index$iv2;
                                        i = size;
                                        $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                                        $this$setRequestedPermissionStates_u24lambda_u2460 = $this$setRequestedPermissionStates_u24lambda_u24602;
                                        setRuntimePermissionGranted($this$setRequestedPermissionStates_u24lambda_u24602, packageState, userId, permissionName, "default:0", true, false, false, false, "setRequestedPermissionStates");
                                        newState$iv = newState$iv2;
                                        oldState$iv = oldState$iv2;
                                        updatePermissionFlags($this$setRequestedPermissionStates_u24lambda_u2460, packageState.getAppId(), userId, permissionName, "default:0", 72, 0, false, false, true, "setRequestedPermissionStates", packageState.getPackageName());
                                        break;
                                    }
                                } else {
                                    index$iv = index$iv2;
                                    i = size;
                                    $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                                    $this$setRequestedPermissionStates_u24lambda_u2460 = $this$setRequestedPermissionStates_u24lambda_u24602;
                                    newState$iv = newState$iv2;
                                    oldState$iv = oldState$iv2;
                                    break;
                                }
                            }
                        default:
                            android.util.Slog.w(LOG_TAG, "setRequestedPermissionStates: Unknown permission state " + permissionState + " for permission " + permissionName2);
                            index$iv = index$iv2;
                            i = size;
                            $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                            $this$setRequestedPermissionStates_u24lambda_u2460 = $this$setRequestedPermissionStates_u24lambda_u24602;
                            newState$iv = newState$iv2;
                            oldState$iv = oldState$iv2;
                            break;
                    }
                    index$iv2 = index$iv + 1;
                    oldState$iv2 = oldState$iv;
                    size = i;
                    $this$forEachIndexed$iv2 = $this$forEachIndexed$iv;
                    $this$setRequestedPermissionStates_u24lambda_u24602 = $this$setRequestedPermissionStates_u24lambda_u2460;
                    newState$iv2 = newState$iv;
                }
                com.android.server.permission.access.MutableAccessState newState$iv3 = newState$iv2;
                this_$iv.persistence.write(newState$iv3);
                this_$iv.state = newState$iv3;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv3));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setRuntimePermissionGranted(com.android.server.permission.access.MutateStateScope $this$setRuntimePermissionGranted, com.android.server.pm.pkg.PackageState packageState, int userId, java.lang.String permissionName, java.lang.String deviceId, boolean isGranted, boolean canManageRolePermission, boolean overridePolicyFixed, boolean reportError, java.lang.String methodName) {
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$setRuntimePermissionGranted_u24lambda_u2461 = this.policy;
        com.android.server.permission.access.permission.Permission permission = $this$setRuntimePermissionGranted_u24lambda_u2461.getPermissions($this$setRuntimePermissionGranted).get(permissionName);
        if (permission == null) {
            if (reportError) {
                throw new java.lang.IllegalArgumentException("Unknown permission " + permissionName);
            }
            return;
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
        java.lang.String packageName = packageState.getPackageName();
        if (!com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 32)) {
            if (!com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 67108864)) {
                com.android.server.permission.access.permission.Permission this_$iv = permission.getPermissionInfo().getProtection() == 1 ? 1 : null;
                if (this_$iv == null) {
                    if (reportError) {
                        throw new java.lang.SecurityException("Permission " + permissionName + " requested by package " + packageName + " is not a changeable permission type");
                    }
                    return;
                }
                if (androidPackage.getTargetSdkVersion() < 23) {
                    return;
                }
                if (isGranted && packageState.getUserStateOrDefault(userId).isInstantApp() && !com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 4096)) {
                    if (reportError) {
                        throw new java.lang.SecurityException("Cannot grant non-instant permission " + permissionName + " to package " + packageName);
                    }
                    return;
                }
            } else if (!canManageRolePermission) {
                if (reportError) {
                    throw new java.lang.SecurityException("Permission " + permissionName + " is managed by role");
                }
                return;
            }
        }
        int appId = packageState.getAppId();
        int oldFlags = getPermissionFlagsWithPolicy($this$setRuntimePermissionGranted, appId, userId, permissionName, deviceId);
        if (!androidPackage.getRequestedPermissions().contains(permissionName) && oldFlags == 0) {
            if (reportError) {
                android.util.Slog.e(LOG_TAG, "Permission " + permissionName + " isn't requested by package " + packageName);
                return;
            }
            return;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(oldFlags, 256)) {
            if (reportError) {
                android.util.Slog.e(LOG_TAG, methodName + ": Cannot change system fixed permission " + permissionName + " for package " + packageName);
                return;
            }
            return;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(oldFlags, 128) && !overridePolicyFixed) {
            if (reportError) {
                android.util.Slog.e(LOG_TAG, methodName + ": Cannot change policy fixed permission " + permissionName + " for package " + packageName);
                return;
            }
            return;
        }
        if (isGranted && com.android.server.permission.access.util.IntExtensionsKt.hasBits(oldFlags, 262144)) {
            if (reportError) {
                android.util.Slog.e(LOG_TAG, methodName + ": Cannot grant hard-restricted non-exempt permission " + permissionName + " to package " + packageName);
                return;
            }
            return;
        }
        if (isGranted && com.android.server.permission.access.util.IntExtensionsKt.hasBits(oldFlags, 524288)) {
            com.android.server.policy.SoftRestrictedPermissionPolicy softRestrictedPermissionPolicy = com.android.server.policy.SoftRestrictedPermissionPolicy.forPermission(this.context, com.android.server.pm.parsing.pkg.AndroidPackageUtils.generateAppInfoWithoutState(androidPackage), androidPackage, android.os.UserHandle.of(userId), permissionName);
            if (!softRestrictedPermissionPolicy.mayGrantPermission()) {
                if (reportError) {
                    android.util.Slog.e(LOG_TAG, methodName + ": Cannot grant soft-restricted non-exempt permission " + permissionName + " to package " + packageName);
                    return;
                }
                return;
            }
        }
        int newFlags = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.updateRuntimePermissionGranted(oldFlags, isGranted);
        if (oldFlags == newFlags) {
            return;
        }
        setPermissionFlagsWithPolicy($this$setRuntimePermissionGranted, appId, userId, permissionName, deviceId, newFlags);
        if (permission.getPermissionInfo().getProtection() == 1) {
            int action = isGranted ? 1243 : 1245;
            android.metrics.LogMaker log = new android.metrics.LogMaker(action);
            log.setPackageName(packageName);
            log.addTaggedData(1241, permissionName);
            com.android.internal.logging.MetricsLogger metricsLogger = this.metricsLogger;
            if (metricsLogger == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("metricsLogger");
                metricsLogger = null;
            }
            metricsLogger.write(log);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setAppOpPermissionGranted(com.android.server.permission.access.MutateStateScope $this$setAppOpPermissionGranted, com.android.server.pm.pkg.PackageState packageState, int userId, java.lang.String permissionName, boolean isGranted) {
        com.android.server.permission.access.SchemePolicy schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar = this.service.getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, "app-op");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar, "null cannot be cast to non-null type com.android.server.permission.access.appop.AppIdAppOpPolicy");
        com.android.server.permission.access.appop.AppIdAppOpPolicy appOpPolicy = (com.android.server.permission.access.appop.AppIdAppOpPolicy) schemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar;
        java.lang.String appOpName = android.app.AppOpsManager.permissionToOp(permissionName);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(appOpName);
        int mode = isGranted ? 0 : 2;
        appOpPolicy.setAppOpMode($this$setAppOpPermissionGranted, packageState.getAppId(), userId, appOpName, mode);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public int getPermissionFlags(@org.jetbrains.annotations.NotNull java.lang.String packageName, @org.jetbrains.annotations.NotNull java.lang.String permissionName, @org.jetbrains.annotations.NotNull java.lang.String deviceId, int userId) {
        com.android.server.pm.UserManagerInternal userManagerInternal = this.userManagerInternal;
        com.android.server.permission.access.AccessState accessState = null;
        if (userManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            userManagerInternal = null;
        }
        if (userManagerInternal.exists(userId)) {
            enforceCallingOrSelfCrossUserPermission(userId, true, false, "getPermissionFlags");
            enforceCallingOrSelfAnyPermission("getPermissionFlags", "android.permission.GRANT_RUNTIME_PERMISSIONS", "android.permission.REVOKE_RUNTIME_PERMISSIONS", "android.permission.GET_RUNTIME_PERMISSIONS");
            com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
            if (packageManagerLocal == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
                packageManagerLocal = null;
            }
            com.android.server.pm.PackageManagerLocal.FilteredSnapshot it = packageManagerLocal.withFilteredSnapshot();
            try {
                com.android.server.pm.pkg.PackageState packageState = it.getPackageState(packageName);
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
                if (packageState == null) {
                    android.util.Slog.w(LOG_TAG, "getPermissionFlags: Unknown package " + packageName);
                    return 0;
                }
                com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
                com.android.server.permission.access.AccessState accessState2 = this_$iv.state;
                if (accessState2 == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                } else {
                    accessState = accessState2;
                }
                com.android.server.permission.access.GetStateScope $this$getPermissionFlags_u24lambda_u2466 = new com.android.server.permission.access.GetStateScope(accessState);
                com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getPermissionFlags_u24lambda_u2466_u24lambda_u2465 = this.policy;
                com.android.server.permission.access.permission.Permission permission = $this$getPermissionFlags_u24lambda_u2466_u24lambda_u2465.getPermissions($this$getPermissionFlags_u24lambda_u2466).get(permissionName);
                if (permission == null) {
                    android.util.Slog.w(LOG_TAG, "getPermissionFlags: Unknown permission " + permissionName);
                    return 0;
                }
                int flags = getPermissionFlagsWithPolicy($this$getPermissionFlags_u24lambda_u2466, packageState.getAppId(), userId, permissionName, deviceId);
                return com.android.server.permission.access.permission.PermissionFlags.INSTANCE.toApiFlags(flags);
            } finally {
            }
        } else {
            android.util.Slog.w(LOG_TAG, "getPermissionFlags: Unknown user " + userId);
            return 0;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.NotNull
    public java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> getAllPermissionStates(@org.jetbrains.annotations.NotNull java.lang.String packageName, @org.jetbrains.annotations.NotNull java.lang.String deviceId, int userId) {
        com.android.server.permission.access.immutable.IndexedMap allPermissionFlags;
        java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> emptyMap;
        java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> emptyMap2;
        java.util.Map<java.lang.String, android.permission.PermissionManager.PermissionState> emptyMap3;
        com.android.server.pm.UserManagerInternal userManagerInternal = this.userManagerInternal;
        com.android.server.permission.access.AccessState accessState = null;
        if (userManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            userManagerInternal = null;
        }
        if (userManagerInternal.exists(userId)) {
            enforceCallingOrSelfCrossUserPermission(userId, true, false, "getAllPermissionStates");
            enforceCallingOrSelfAnyPermission("getAllPermissionStates", "android.permission.GET_RUNTIME_PERMISSIONS");
            com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
            if (packageManagerLocal == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
                packageManagerLocal = null;
            }
            com.android.server.pm.PackageManagerLocal.FilteredSnapshot it = packageManagerLocal.withFilteredSnapshot();
            try {
                com.android.server.pm.pkg.PackageState packageState = it.getPackageState(packageName);
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
                com.android.server.pm.pkg.PackageState packageState2 = packageState;
                if (packageState2 == null) {
                    android.util.Slog.w(LOG_TAG, "getAllPermissionStates: Unknown package " + packageName);
                    emptyMap2 = com.android.server.permission.jarjar.kotlin.collections.MapsKt__MapsKt.emptyMap();
                    return emptyMap2;
                }
                com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
                com.android.server.permission.access.AccessState accessState2 = this_$iv.state;
                if (accessState2 == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                } else {
                    accessState = accessState2;
                }
                com.android.server.permission.access.GetStateScope $this$getAllPermissionStates_u24lambda_u2470 = new com.android.server.permission.access.GetStateScope(accessState);
                if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(deviceId, "default:0")) {
                    com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getAllPermissionStates_u24lambda_u2470_u24lambda_u2468 = this.policy;
                    allPermissionFlags = $this$getAllPermissionStates_u24lambda_u2470_u24lambda_u2468.getAllPermissionFlags($this$getAllPermissionStates_u24lambda_u2470, packageState2.getAppId(), userId);
                } else {
                    com.android.server.permission.access.permission.DevicePermissionPolicy $this$getAllPermissionStates_u24lambda_u2470_u24lambda_u2469 = this.devicePolicy;
                    allPermissionFlags = $this$getAllPermissionStates_u24lambda_u2470_u24lambda_u2469.getAllPermissionFlags($this$getAllPermissionStates_u24lambda_u2470, packageState2.getAppId(), deviceId, userId);
                }
                if (allPermissionFlags != null) {
                    com.android.server.permission.access.immutable.IndexedMap permissionFlagsMap = allPermissionFlags;
                    android.util.ArrayMap permissionStates = new android.util.ArrayMap();
                    int index$iv = 0;
                    int size = permissionFlagsMap.getSize();
                    while (index$iv < size) {
                        java.lang.String keyAt = permissionFlagsMap.keyAt(index$iv);
                        int flags = permissionFlagsMap.valueAt(index$iv).intValue();
                        java.lang.String permissionName = keyAt;
                        boolean granted = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isPermissionGranted(flags);
                        int apiFlags = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.toApiFlags(flags);
                        permissionStates.put(permissionName, new android.permission.PermissionManager.PermissionState(granted, apiFlags));
                        index$iv++;
                        packageState2 = packageState2;
                    }
                    return permissionStates;
                }
                emptyMap = com.android.server.permission.jarjar.kotlin.collections.MapsKt__MapsKt.emptyMap();
                return emptyMap;
            } finally {
            }
        } else {
            android.util.Slog.w(LOG_TAG, "getAllPermissionStates: Unknown user " + userId);
            emptyMap3 = com.android.server.permission.jarjar.kotlin.collections.MapsKt__MapsKt.emptyMap();
            return emptyMap3;
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean isPermissionRevokedByPolicy(@org.jetbrains.annotations.NotNull java.lang.String packageName, @org.jetbrains.annotations.NotNull java.lang.String permissionName, @org.jetbrains.annotations.NotNull java.lang.String deviceId, int userId) {
        com.android.server.pm.UserManagerInternal userManagerInternal = this.userManagerInternal;
        com.android.server.permission.access.AccessState accessState = null;
        if (userManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            userManagerInternal = null;
        }
        if (!userManagerInternal.exists(userId)) {
            android.util.Slog.w(LOG_TAG, "isPermissionRevokedByPolicy: Unknown user " + userId);
            return false;
        }
        enforceCallingOrSelfCrossUserPermission(userId, true, false, "isPermissionRevokedByPolicy");
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.FilteredSnapshot it = withFilteredSnapshot(packageManagerLocal, android.os.Binder.getCallingUid(), userId);
        try {
            com.android.server.pm.pkg.PackageState packageState = it.getPackageState(packageName);
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
            if (packageState == null) {
                return false;
            }
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            com.android.server.permission.access.AccessState accessState2 = this_$iv.state;
            if (accessState2 == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            } else {
                accessState = accessState2;
            }
            com.android.server.permission.access.GetStateScope $this$isPermissionRevokedByPolicy_u24lambda_u2473 = new com.android.server.permission.access.GetStateScope(accessState);
            if (isPermissionGranted($this$isPermissionRevokedByPolicy_u24lambda_u2473, packageState, userId, permissionName, deviceId)) {
                return false;
            }
            int flags = getPermissionFlagsWithPolicy($this$isPermissionRevokedByPolicy_u24lambda_u2473, packageState.getAppId(), userId, permissionName, deviceId);
            return com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 128);
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean isPermissionsReviewRequired(@org.jetbrains.annotations.NotNull java.lang.String packageName, int userId) {
        if (packageName == null) {
            throw new java.lang.IllegalArgumentException("packageName cannot be null".toString());
        }
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        com.android.server.permission.access.AccessState accessState = null;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot it = packageManagerLocal.withUnfilteredSnapshot();
        try {
            com.android.server.pm.pkg.PackageState packageState = getPackageState(it, packageName);
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
            if (packageState == null) {
                return false;
            }
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            com.android.server.permission.access.AccessState accessState2 = this_$iv.state;
            if (accessState2 == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            } else {
                accessState = accessState2;
            }
            com.android.server.permission.access.GetStateScope $this$isPermissionsReviewRequired_u24lambda_u2477 = new com.android.server.permission.access.GetStateScope(accessState);
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$isPermissionsReviewRequired_u24lambda_u2477_u24lambda_u2476 = this.policy;
            com.android.server.permission.access.immutable.IndexedMap permissionFlags = $this$isPermissionsReviewRequired_u24lambda_u2477_u24lambda_u2476.getUidPermissionFlags($this$isPermissionsReviewRequired_u24lambda_u2477, packageState.getAppId(), userId);
            if (permissionFlags == null) {
                return false;
            }
            int size = permissionFlags.getSize();
            for (int index$iv$iv = 0; index$iv$iv < size; index$iv$iv++) {
                java.lang.Object key$iv = permissionFlags.keyAt(index$iv$iv);
                java.lang.Object value$iv = permissionFlags.valueAt(index$iv$iv);
                if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(((java.lang.Number) value$iv).intValue(), 5120)) {
                    return true;
                }
            }
            return false;
        } catch (java.lang.Throwable th) {
            try {
                throw th;
            } catch (java.lang.Throwable th2) {
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, th);
                throw th2;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean shouldShowRequestPermissionRationale(@org.jetbrains.annotations.NotNull java.lang.String packageName, @org.jetbrains.annotations.NotNull java.lang.String permissionName, @org.jetbrains.annotations.NotNull java.lang.String deviceId, int userId) {
        int appId;
        boolean z;
        com.android.server.pm.UserManagerInternal userManagerInternal = this.userManagerInternal;
        com.android.internal.compat.IPlatformCompat iPlatformCompat = null;
        if (userManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            userManagerInternal = null;
        }
        if (!userManagerInternal.exists(userId)) {
            android.util.Slog.w(LOG_TAG, "shouldShowRequestPermissionRationale: Unknown user " + userId);
            return false;
        }
        enforceCallingOrSelfCrossUserPermission(userId, true, false, "shouldShowRequestPermissionRationale");
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.FilteredSnapshot it = withFilteredSnapshot(packageManagerLocal, callingUid, userId);
        try {
            com.android.server.pm.pkg.PackageState packageState = it.getPackageState(packageName);
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
            if (packageState == null || android.os.UserHandle.getAppId(callingUid) != (appId = packageState.getAppId())) {
                return false;
            }
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            com.android.server.permission.access.AccessState accessState = this_$iv.state;
            if (accessState == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                accessState = null;
            }
            com.android.server.permission.access.GetStateScope $this$shouldShowRequestPermissionRationale_u24lambda_u2480 = new com.android.server.permission.access.GetStateScope(accessState);
            if (isPermissionGranted($this$shouldShowRequestPermissionRationale_u24lambda_u2480, packageState, userId, permissionName, deviceId)) {
                return false;
            }
            int flags = getPermissionFlagsWithPolicy($this$shouldShowRequestPermissionRationale_u24lambda_u2480, appId, userId, permissionName, deviceId);
            if (com.android.server.permission.access.util.IntExtensionsKt.hasAnyBit(flags, 262592)) {
                return false;
            }
            if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(permissionName, "android.permission.ACCESS_BACKGROUND_LOCATION")) {
                long token$iv = android.os.Binder.clearCallingIdentity();
                try {
                    try {
                        com.android.internal.compat.IPlatformCompat iPlatformCompat2 = this.platformCompat;
                        if (iPlatformCompat2 == null) {
                            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("platformCompat");
                        } else {
                            iPlatformCompat = iPlatformCompat2;
                        }
                        z = iPlatformCompat.isChangeEnabledByPackageName(BACKGROUND_RATIONALE_CHANGE_ID, packageName, userId);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(LOG_TAG, "shouldShowRequestPermissionRationale: Unable to check if compatibility change is enabled", e);
                        z = false;
                    }
                    android.os.Binder.restoreCallingIdentity(token$iv);
                    boolean isBackgroundRationaleChangeEnabled = z;
                    if (isBackgroundRationaleChangeEnabled) {
                        return true;
                    }
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(token$iv);
                    throw th;
                }
            }
            return com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 32);
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void updatePermissionFlags(@org.jetbrains.annotations.NotNull java.lang.String packageName, @org.jetbrains.annotations.NotNull java.lang.String permissionName, int flagMask, int flagValues, boolean enforceAdjustPolicyPermission, @org.jetbrains.annotations.NotNull java.lang.String deviceId, int userId) {
        boolean isPermissionRequested;
        com.android.server.permission.access.MutableAccessState newState$iv;
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.pm.UserManagerInternal userManagerInternal = this.userManagerInternal;
        if (userManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            userManagerInternal = null;
        }
        if (!userManagerInternal.exists(userId)) {
            android.util.Slog.w(LOG_TAG, "updatePermissionFlags: Unknown user " + userId);
            return;
        }
        enforceCallingOrSelfCrossUserPermission(userId, true, true, "updatePermissionFlags");
        enforceCallingOrSelfAnyPermission("updatePermissionFlags", "android.permission.GRANT_RUNTIME_PERMISSIONS", "android.permission.REVOKE_RUNTIME_PERMISSIONS");
        if (!isRootOrSystemUid(callingUid) && com.android.server.permission.access.util.IntExtensionsKt.hasBits(flagMask, 4)) {
            if (enforceAdjustPolicyPermission) {
                this.context.enforceCallingOrSelfPermission("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY", "Need android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY to change policy flags");
            } else {
                android.content.pm.PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
                if (packageManagerInternal == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
                    packageManagerInternal = null;
                }
                int targetSdkVersion = packageManagerInternal.getUidTargetSdkVersion(callingUid);
                if (!(targetSdkVersion < 29)) {
                    throw new java.lang.IllegalArgumentException("android.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY needs to be checked for packages targeting 29 or later when changing policy flags".toString());
                }
            }
        }
        android.content.pm.PackageManagerInternal packageManagerInternal2 = this.packageManagerInternal;
        if (packageManagerInternal2 == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            packageManagerInternal2 = null;
        }
        com.android.server.pm.pkg.PackageStateInternal packageState = packageManagerInternal2.getPackageStateInternal(packageName);
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageState != null ? packageState.getAndroidPackage() : null;
        if (androidPackage != null) {
            android.content.pm.PackageManagerInternal packageManagerInternal3 = this.packageManagerInternal;
            if (packageManagerInternal3 == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
                packageManagerInternal3 = null;
            }
            if (!packageManagerInternal3.filterAppAccess(packageName, callingUid, userId, false)) {
                boolean canUpdateSystemFlags = isRootOrSystemUid(callingUid);
                if (!androidPackage.getRequestedPermissions().contains(permissionName)) {
                    android.content.pm.PackageManagerInternal packageManagerInternal4 = this.packageManagerInternal;
                    if (packageManagerInternal4 == null) {
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
                        packageManagerInternal4 = null;
                    }
                    java.lang.String[] sharedUserPackageNames = packageManagerInternal4.getSharedUserPackagesForPackage(packageName, userId);
                    int length = sharedUserPackageNames.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            isPermissionRequested = false;
                            break;
                        }
                        java.lang.String str = sharedUserPackageNames[i];
                        android.content.pm.PackageManagerInternal packageManagerInternal5 = this.packageManagerInternal;
                        if (packageManagerInternal5 == null) {
                            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
                            packageManagerInternal5 = null;
                        }
                        com.android.server.pm.pkg.AndroidPackage sharedUserPackage = packageManagerInternal5.getPackage(str);
                        if (sharedUserPackage != null && sharedUserPackage.getRequestedPermissions().contains(permissionName)) {
                            isPermissionRequested = true;
                            break;
                        }
                        i++;
                    }
                } else {
                    isPermissionRequested = true;
                }
                int appId = packageState.getAppId();
                com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
                synchronized (this_$iv.stateLock) {
                    try {
                        com.android.server.permission.access.AccessState accessState = this_$iv.state;
                        if (accessState == null) {
                            try {
                                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                                accessState = null;
                            } catch (java.lang.Throwable th) {
                                th = th;
                                throw th;
                            }
                        }
                        com.android.server.permission.access.AccessState oldState$iv = accessState;
                        newState$iv = oldState$iv.toMutable();
                        com.android.server.permission.access.MutateStateScope $this$updatePermissionFlags_u24lambda_u2484 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                        try {
                            updatePermissionFlags($this$updatePermissionFlags_u24lambda_u2484, appId, userId, permissionName, deviceId, flagMask, flagValues, canUpdateSystemFlags, true, isPermissionRequested, "updatePermissionFlags", packageName);
                            this_$iv.persistence.write(newState$iv);
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                        }
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                    }
                    try {
                        this_$iv.state = newState$iv;
                        com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                        $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                        com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                        return;
                    } catch (java.lang.Throwable th4) {
                        th = th4;
                        throw th;
                    }
                }
            }
        }
        android.util.Slog.w(LOG_TAG, "updatePermissionFlags: Unknown package " + packageName);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void updatePermissionFlagsForAllApps(int flagMask, int flagValues, int userId) {
        com.android.server.permission.access.MutableAccessState newState$iv;
        com.android.server.permission.access.AccessCheckingService this_$iv;
        com.android.server.permission.access.MutableAccessState newState$iv2;
        com.android.server.permission.access.AccessState oldState$iv;
        com.android.server.permission.access.AccessCheckingService this_$iv2;
        int callingUid;
        int callingUid2 = android.os.Binder.getCallingUid();
        com.android.server.pm.UserManagerInternal userManagerInternal = this.userManagerInternal;
        com.android.server.permission.access.AccessState accessState = null;
        if (userManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            userManagerInternal = null;
        }
        if (!userManagerInternal.exists(userId)) {
            android.util.Slog.w(LOG_TAG, "updatePermissionFlagsForAllApps: Unknown user " + userId);
            return;
        }
        enforceCallingOrSelfCrossUserPermission(userId, true, true, "updatePermissionFlagsForAllApps");
        enforceCallingOrSelfAnyPermission("updatePermissionFlagsForAllApps", "android.permission.GRANT_RUNTIME_PERMISSIONS", "android.permission.REVOKE_RUNTIME_PERMISSIONS");
        boolean canUpdateSystemFlags = isRootOrSystemUid(callingUid2);
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot it = packageManagerLocal.withUnfilteredSnapshot();
        try {
            java.util.Map packageStates = it.getPackageStates();
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
            com.android.server.permission.access.AccessCheckingService this_$iv3 = this.service;
            synchronized (this_$iv3.stateLock) {
                try {
                    com.android.server.permission.access.AccessState accessState2 = this_$iv3.state;
                    if (accessState2 == null) {
                        try {
                            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                        } catch (java.lang.Throwable th) {
                            th = th;
                            throw th;
                        }
                    } else {
                        accessState = accessState2;
                    }
                    com.android.server.permission.access.AccessState oldState$iv2 = accessState;
                    com.android.server.permission.access.MutableAccessState newState$iv3 = oldState$iv2.toMutable();
                    com.android.server.permission.access.MutateStateScope $this$updatePermissionFlagsForAllApps_u24lambda_u2488 = new com.android.server.permission.access.MutateStateScope(oldState$iv2, newState$iv3);
                    for (java.util.Map.Entry element$iv : packageStates.entrySet()) {
                        try {
                            java.lang.String packageName = element$iv.getKey();
                            com.android.server.pm.pkg.PackageState packageState = element$iv.getValue();
                            com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
                            if (androidPackage == null) {
                                newState$iv2 = newState$iv3;
                                oldState$iv = oldState$iv2;
                                this_$iv2 = this_$iv3;
                                callingUid = callingUid2;
                            } else {
                                java.lang.Iterable<java.lang.String> $this$forEach$iv = androidPackage.getRequestedPermissions();
                                for (java.lang.String permissionName : $this$forEach$iv) {
                                    com.android.server.permission.access.MutableAccessState newState$iv4 = newState$iv3;
                                    com.android.server.permission.access.AccessState oldState$iv3 = oldState$iv2;
                                    com.android.server.permission.access.AccessCheckingService this_$iv4 = this_$iv3;
                                    int callingUid3 = callingUid2;
                                    try {
                                        updatePermissionFlags($this$updatePermissionFlagsForAllApps_u24lambda_u2488, packageState.getAppId(), userId, permissionName, "default:0", flagMask, flagValues, canUpdateSystemFlags, false, true, "updatePermissionFlagsForAllApps", packageName);
                                        this_$iv3 = this_$iv4;
                                        callingUid2 = callingUid3;
                                        newState$iv3 = newState$iv4;
                                        oldState$iv2 = oldState$iv3;
                                    } catch (java.lang.Throwable th2) {
                                        th = th2;
                                        throw th;
                                    }
                                }
                                newState$iv2 = newState$iv3;
                                oldState$iv = oldState$iv2;
                                this_$iv2 = this_$iv3;
                                callingUid = callingUid2;
                            }
                            this_$iv3 = this_$iv2;
                            callingUid2 = callingUid;
                            newState$iv3 = newState$iv2;
                            oldState$iv2 = oldState$iv;
                        } catch (java.lang.Throwable th3) {
                            th = th3;
                        }
                    }
                    newState$iv = newState$iv3;
                    this_$iv = this_$iv3;
                    try {
                        this_$iv.persistence.write(newState$iv);
                    } catch (java.lang.Throwable th4) {
                        th = th4;
                    }
                } catch (java.lang.Throwable th5) {
                    th = th5;
                }
                try {
                    this_$iv.state = newState$iv;
                    com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                    $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                    com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                } catch (java.lang.Throwable th6) {
                    th = th6;
                    throw th;
                }
            }
        } finally {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updatePermissionFlags(com.android.server.permission.access.MutateStateScope $this$updatePermissionFlags, int appId, int userId, java.lang.String permissionName, java.lang.String deviceId, int flagMask, int flagValues, boolean canUpdateSystemFlags, boolean reportErrorForUnknownPermission, boolean isPermissionRequested, java.lang.String methodName, java.lang.String packageName) {
        int flagMask2;
        int flagValues2;
        if (canUpdateSystemFlags) {
            flagMask2 = flagMask;
            flagValues2 = flagValues;
        } else {
            flagMask2 = com.android.server.permission.access.util.IntExtensionsKt.andInv(flagMask, 30768);
            flagValues2 = com.android.server.permission.access.util.IntExtensionsKt.andInv(flagValues, 30768);
        }
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$updatePermissionFlags_u24lambda_u2489 = this.policy;
        com.android.server.permission.access.permission.Permission permission = $this$updatePermissionFlags_u24lambda_u2489.getPermissions($this$updatePermissionFlags).get(permissionName);
        if (permission == null) {
            if (reportErrorForUnknownPermission) {
                throw new java.lang.IllegalArgumentException("Unknown permission " + permissionName);
            }
            return;
        }
        int oldFlags = getPermissionFlagsWithPolicy($this$updatePermissionFlags, appId, userId, permissionName, deviceId);
        if (!isPermissionRequested && oldFlags == 0) {
            android.util.Slog.w(LOG_TAG, methodName + ": Permission " + permissionName + " isn't requested by package " + packageName);
            return;
        }
        int newFlags = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.updateFlags(permission, oldFlags, flagMask2, flagValues2);
        setPermissionFlagsWithPolicy($this$updatePermissionFlags, appId, userId, permissionName, deviceId, newFlags);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.Nullable
    public java.util.ArrayList<java.lang.String> getAllowlistedRestrictedPermissions(@org.jetbrains.annotations.NotNull java.lang.String packageName, int allowlistedFlags, int userId) {
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        if (packageName == null) {
            throw new java.lang.IllegalArgumentException("packageName cannot be null".toString());
        }
        com.android.internal.util.Preconditions.checkFlagsArgument(allowlistedFlags, 7);
        com.android.internal.util.Preconditions.checkArgumentNonnegative(userId, "userId cannot be null");
        com.android.server.pm.UserManagerInternal userManagerInternal = this.userManagerInternal;
        android.content.pm.PackageManagerInternal packageManagerInternal = null;
        if (userManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
            userManagerInternal = null;
        }
        if (!userManagerInternal.exists(userId)) {
            android.util.Slog.w(LOG_TAG, "AllowlistedRestrictedPermission api: Unknown user " + userId);
            return null;
        }
        enforceCallingOrSelfCrossUserPermission(userId, false, false, "getAllowlistedRestrictedPermissions");
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.FilteredSnapshot it = withFilteredSnapshot(packageManagerLocal, callingUid, userId);
        try {
            com.android.server.pm.pkg.PackageState packageState = it.getPackageState(packageName);
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
            if (packageState == null || (androidPackage = packageState.getAndroidPackage()) == null) {
                return null;
            }
            boolean isCallerPrivileged = this.context.checkCallingOrSelfPermission("android.permission.WHITELIST_RESTRICTED_PERMISSIONS") == 0;
            if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(allowlistedFlags, 1) && !isCallerPrivileged) {
                throw new java.lang.SecurityException("Querying system allowlist requires android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
            }
            android.content.pm.PackageManagerInternal packageManagerInternal2 = this.packageManagerInternal;
            if (packageManagerInternal2 == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            } else {
                packageManagerInternal = packageManagerInternal2;
            }
            boolean isCallerInstallerOnRecord = packageManagerInternal.isCallerInstallerOfRecord(androidPackage, callingUid);
            if (!com.android.server.permission.access.util.IntExtensionsKt.hasAnyBit(allowlistedFlags, 6) || isCallerPrivileged || isCallerInstallerOnRecord) {
                return getAllowlistedRestrictedPermissionsUnchecked(packageState.getAppId(), allowlistedFlags, userId);
            }
            throw new java.lang.SecurityException("Querying upgrade or installer allowlist requires being installer on record or android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
        } finally {
        }
    }

    private final int getPermissionFlagsWithPolicy(com.android.server.permission.access.GetStateScope $this$getPermissionFlagsWithPolicy, int appId, int userId, java.lang.String permissionName, java.lang.String deviceId) {
        if (!android.permission.flags.Flags.deviceAwarePermissionApisEnabled() || com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(deviceId, "default:0")) {
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getPermissionFlagsWithPolicy_u24lambda_u2492 = this.policy;
            return $this$getPermissionFlagsWithPolicy_u24lambda_u2492.getPermissionFlags($this$getPermissionFlagsWithPolicy, appId, userId, permissionName);
        }
        if (!DEVICE_AWARE_PERMISSIONS.contains(permissionName)) {
            android.util.Slog.i(LOG_TAG, permissionName + " is not device aware permission,  get the flags for default device.");
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getPermissionFlagsWithPolicy_u24lambda_u2493 = this.policy;
            return $this$getPermissionFlagsWithPolicy_u24lambda_u2493.getPermissionFlags($this$getPermissionFlagsWithPolicy, appId, userId, permissionName);
        }
        com.android.server.permission.access.permission.DevicePermissionPolicy $this$getPermissionFlagsWithPolicy_u24lambda_u2494 = this.devicePolicy;
        return $this$getPermissionFlagsWithPolicy_u24lambda_u2494.getPermissionFlags($this$getPermissionFlagsWithPolicy, appId, deviceId, userId, permissionName);
    }

    private final boolean setPermissionFlagsWithPolicy(com.android.server.permission.access.MutateStateScope $this$setPermissionFlagsWithPolicy, int appId, int userId, java.lang.String permissionName, java.lang.String deviceId, int flags) {
        if (!android.permission.flags.Flags.deviceAwarePermissionApisEnabled() || com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(deviceId, "default:0")) {
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$setPermissionFlagsWithPolicy_u24lambda_u2495 = this.policy;
            return $this$setPermissionFlagsWithPolicy_u24lambda_u2495.setPermissionFlags($this$setPermissionFlagsWithPolicy, appId, userId, permissionName, flags);
        }
        if (!DEVICE_AWARE_PERMISSIONS.contains(permissionName)) {
            android.util.Slog.i(LOG_TAG, permissionName + " is not device aware permission,  set the flags for default device.");
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$setPermissionFlagsWithPolicy_u24lambda_u2496 = this.policy;
            return $this$setPermissionFlagsWithPolicy_u24lambda_u2496.setPermissionFlags($this$setPermissionFlagsWithPolicy, appId, userId, permissionName, flags);
        }
        com.android.server.permission.access.permission.DevicePermissionPolicy $this$setPermissionFlagsWithPolicy_u24lambda_u2497 = this.devicePolicy;
        return $this$setPermissionFlagsWithPolicy_u24lambda_u2497.setPermissionFlags($this$setPermissionFlagsWithPolicy, appId, deviceId, userId, permissionName, flags);
    }

    private final java.util.ArrayList<java.lang.String> getAllowlistedRestrictedPermissionsUnchecked(int appId, int allowlistedFlags, int userId) {
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getAllowlistedRestrictedPermissionsUnchecked_u24lambda_u2499 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getAllowlistedRestrictedPermissionsUnchecked_u24lambda_u2499_u24lambda_u2498 = this.policy;
        com.android.server.permission.access.immutable.IndexedMap permissionFlags = $this$getAllowlistedRestrictedPermissionsUnchecked_u24lambda_u2499_u24lambda_u2498.getUidPermissionFlags($this$getAllowlistedRestrictedPermissionsUnchecked_u24lambda_u2499, appId, userId);
        if (permissionFlags == null) {
            return null;
        }
        int queryFlags = com.android.server.permission.access.util.IntExtensionsKt.hasBits(allowlistedFlags, 1) ? 0 | 65536 : 0;
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(allowlistedFlags, 4)) {
            queryFlags |= 131072;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(allowlistedFlags, 2)) {
            queryFlags |= 32768;
        }
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        int size = permissionFlags.getSize();
        for (int index$iv$iv = 0; index$iv$iv < size; index$iv$iv++) {
            java.lang.Object key$iv = permissionFlags.keyAt(index$iv$iv);
            java.lang.Object value$iv = permissionFlags.valueAt(index$iv$iv);
            int flags = ((java.lang.Number) value$iv).intValue();
            java.lang.String permissionName = (java.lang.String) key$iv;
            if (!com.android.server.permission.access.util.IntExtensionsKt.hasAnyBit(flags, queryFlags)) {
                permissionName = null;
            }
            if (permissionName != null) {
                arrayList.add(permissionName);
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean addAllowlistedRestrictedPermission(@org.jetbrains.annotations.NotNull java.lang.String packageName, @org.jetbrains.annotations.NotNull java.lang.String permissionName, int allowlistedFlags, int userId) {
        if (permissionName == null) {
            throw new java.lang.IllegalArgumentException("permissionName cannot be null".toString());
        }
        if (!enforceRestrictedPermission(permissionName)) {
            return false;
        }
        java.util.ArrayList permissionNames = getAllowlistedRestrictedPermissions(packageName, allowlistedFlags, userId);
        if (permissionNames == null) {
            permissionNames = new java.util.ArrayList(1);
        }
        if (permissionNames.contains(permissionName)) {
            return false;
        }
        permissionNames.add(permissionName);
        return setAllowlistedRestrictedPermissions(packageName, permissionNames, allowlistedFlags, userId, true);
    }

    private final void addAllowlistedRestrictedPermissionsUnchecked(com.android.server.pm.pkg.AndroidPackage androidPackage, int appId, java.util.List<java.lang.String> list, int userId) {
        java.util.List newPermissionNames;
        java.util.List list2;
        java.util.ArrayList it = getAllowlistedRestrictedPermissionsUnchecked(appId, 2, userId);
        if (it != null) {
            android.util.ArraySet $this$addAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24103_u24lambda_u24102 = new android.util.ArraySet(list);
            com.android.server.permission.jarjar.kotlin.collections.CollectionsKt__MutableCollectionsKt.addAll($this$addAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24103_u24lambda_u24102, it);
            list2 = com.android.server.permission.jarjar.kotlin.collections.CollectionsKt___CollectionsKt.toList($this$addAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24103_u24lambda_u24102);
            if (list2 != null) {
                newPermissionNames = list2;
                setAllowlistedRestrictedPermissionsUnchecked(androidPackage, appId, newPermissionNames, 2, userId);
            }
        }
        newPermissionNames = list;
        setAllowlistedRestrictedPermissionsUnchecked(androidPackage, appId, newPermissionNames, 2, userId);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public boolean removeAllowlistedRestrictedPermission(@org.jetbrains.annotations.NotNull java.lang.String packageName, @org.jetbrains.annotations.NotNull java.lang.String permissionName, int allowlistedFlags, int userId) {
        java.util.ArrayList permissions;
        if (permissionName == null) {
            throw new java.lang.IllegalArgumentException("permissionName cannot be null".toString());
        }
        if (enforceRestrictedPermission(permissionName) && (permissions = getAllowlistedRestrictedPermissions(packageName, allowlistedFlags, userId)) != null && permissions.remove(permissionName)) {
            return setAllowlistedRestrictedPermissions(packageName, permissions, allowlistedFlags, userId, false);
        }
        return false;
    }

    private final boolean enforceRestrictedPermission(java.lang.String permissionName) {
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$enforceRestrictedPermission_u24lambda_u24106 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$enforceRestrictedPermission_u24lambda_u24106_u24lambda_u24105 = this.policy;
        com.android.server.permission.access.permission.Permission permission = $this$enforceRestrictedPermission_u24lambda_u24106_u24lambda_u24105.getPermissions($this$enforceRestrictedPermission_u24lambda_u24106).get(permissionName);
        boolean isImmutablyRestrictedPermission = false;
        if (permission == null) {
            android.util.Slog.w(LOG_TAG, "permission definition for " + permissionName + " does not exist");
            return false;
        }
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.FilteredSnapshot it = packageManagerLocal.withFilteredSnapshot();
        try {
            com.android.server.pm.pkg.PackageState packageState = it.getPackageState(permission.getPermissionInfo().packageName);
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
            if (packageState == null) {
                return false;
            }
            if ((com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 4) || com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 8)) && com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 16)) {
                isImmutablyRestrictedPermission = true;
            }
            if (!isImmutablyRestrictedPermission || this.context.checkCallingOrSelfPermission("android.permission.WHITELIST_RESTRICTED_PERMISSIONS") == 0) {
                return true;
            }
            throw new java.lang.SecurityException("Cannot modify allowlist of an immutably restricted permission: " + permission.getPermissionInfo().name);
        } finally {
        }
    }

    private final boolean setAllowlistedRestrictedPermissions(java.lang.String packageName, java.util.List<java.lang.String> list, int allowlistedFlags, int userId, boolean isAddingPermission) {
        com.android.internal.util.Preconditions.checkArgument(java.lang.Integer.bitCount(allowlistedFlags) == 1);
        boolean isCallerPrivileged = this.context.checkCallingOrSelfPermission("android.permission.WHITELIST_RESTRICTED_PERMISSIONS") == 0;
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        android.content.pm.PackageManagerInternal packageManagerInternal = null;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.FilteredSnapshot snapshot = withFilteredSnapshot(packageManagerLocal, callingUid, userId);
        try {
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            com.android.server.pm.pkg.PackageState packageState = snapshot.getPackageStates().get(packageName);
            if (packageState == null) {
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
                return false;
            }
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
            com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
            if (androidPackage == null) {
                return false;
            }
            android.content.pm.PackageManagerInternal packageManagerInternal2 = this.packageManagerInternal;
            if (packageManagerInternal2 == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            } else {
                packageManagerInternal = packageManagerInternal2;
            }
            boolean isCallerInstallerOnRecord = packageManagerInternal.isCallerInstallerOfRecord(androidPackage, callingUid);
            if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(allowlistedFlags, 4)) {
                if (!isCallerPrivileged && !isCallerInstallerOnRecord) {
                    throw new java.lang.SecurityException("Modifying upgrade allowlist requires being installer on record or android.permission.WHITELIST_RESTRICTED_PERMISSIONS");
                }
                if (isAddingPermission && !isCallerPrivileged) {
                    throw new java.lang.SecurityException("Adding to upgrade allowlist requiresandroid.permission.WHITELIST_RESTRICTED_PERMISSIONS");
                }
            }
            setAllowlistedRestrictedPermissionsUnchecked(androidPackage, packageState.getAppId(), list, allowlistedFlags, userId);
            return true;
        } catch (java.lang.Throwable th2) {
            th = th2;
            java.lang.Throwable th3 = th;
            try {
                throw th3;
            } catch (java.lang.Throwable th4) {
                com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, th3);
                throw th4;
            }
        }
    }

    private final void setAllowlistedRestrictedPermissionsUnchecked(com.android.server.pm.pkg.AndroidPackage androidPackage, int appId, java.util.List<java.lang.String> list, int allowlistedFlags, int userId) {
        int i;
        com.android.server.permission.access.permission.AppIdPermissionPolicy appIdPermissionPolicy;
        com.android.server.permission.access.AccessState oldState$iv;
        com.android.server.permission.access.immutable.IndexedMap permissions;
        com.android.server.permission.access.immutable.IndexedMap permissionsFlags;
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u24110;
        java.util.List<java.lang.String> list2 = list;
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241102 = null;
        synchronized (this_$iv.stateLock) {
            int i2 = 0;
            try {
                com.android.server.permission.access.AccessState oldState$iv2 = this_$iv.state;
                if (oldState$iv2 == null) {
                    try {
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                        oldState$iv2 = null;
                    } catch (java.lang.Throwable th) {
                        th = th;
                        throw th;
                    }
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv2.toMutable();
                com.android.server.permission.access.MutateStateScope $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111 = new com.android.server.permission.access.MutateStateScope(oldState$iv2, newState$iv);
                com.android.server.permission.access.permission.AppIdPermissionPolicy $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241103 = this.policy;
                com.android.server.permission.access.immutable.IndexedMap uidPermissionFlags = $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241103.getUidPermissionFlags($this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111, appId, userId);
                if (uidPermissionFlags != null) {
                    com.android.server.permission.access.immutable.IndexedMap permissionsFlags2 = uidPermissionFlags;
                    com.android.server.permission.access.immutable.IndexedMap permissions2 = $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241103.getPermissions($this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111);
                    java.lang.Iterable $this$forEachIndexed$iv = androidPackage.getRequestedPermissions();
                    int index$iv = 0;
                    for (java.lang.Object item$iv : $this$forEachIndexed$iv) {
                        int index$iv2 = index$iv + 1;
                        if (index$iv < 0) {
                            com.android.server.permission.jarjar.kotlin.collections.CollectionsKt__CollectionsKt.throwIndexOverflow();
                        }
                        java.lang.String requestedPermission = (java.lang.String) item$iv;
                        com.android.server.permission.access.permission.Permission permission = permissions2.get(requestedPermission);
                        if (permission != null) {
                            i = i2;
                            if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 4) || com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 8)) {
                                java.lang.Integer num = permissionsFlags2.get(requestedPermission);
                                int oldFlags = num != null ? num.intValue() : 0;
                                boolean wasGranted = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isPermissionGranted(oldFlags);
                                int newFlags = oldFlags;
                                int allowlistFlagsCopy = allowlistedFlags;
                                appIdPermissionPolicy = $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241102;
                                int $i$f$mutateState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar = 0;
                                while (allowlistFlagsCopy != 0) {
                                    try {
                                        com.android.server.permission.access.AccessState oldState$iv3 = oldState$iv2;
                                        int flag = 1 << java.lang.Integer.numberOfTrailingZeros(allowlistFlagsCopy);
                                        com.android.server.permission.access.immutable.IndexedMap permissions3 = permissions2;
                                        allowlistFlagsCopy &= ~flag;
                                        switch (flag) {
                                            case 1:
                                                $i$f$mutateState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar |= 65536;
                                                newFlags = list2.contains(requestedPermission) ? 65536 | newFlags : com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags, 65536);
                                                permissions2 = permissions3;
                                                oldState$iv2 = oldState$iv3;
                                                break;
                                            case 2:
                                                $i$f$mutateState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar |= 32768;
                                                newFlags = list2.contains(requestedPermission) ? 32768 | newFlags : com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags, 32768);
                                                permissions2 = permissions3;
                                                oldState$iv2 = oldState$iv3;
                                                break;
                                            case 3:
                                            default:
                                                permissions2 = permissions3;
                                                oldState$iv2 = oldState$iv3;
                                                break;
                                            case 4:
                                                $i$f$mutateState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar |= 131072;
                                                newFlags = list2.contains(requestedPermission) ? 131072 | newFlags : com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags, 131072);
                                                permissions2 = permissions3;
                                                oldState$iv2 = oldState$iv3;
                                                break;
                                        }
                                    } catch (java.lang.Throwable th2) {
                                        th = th2;
                                        throw th;
                                    }
                                }
                                oldState$iv = oldState$iv2;
                                com.android.server.permission.access.immutable.IndexedMap permissions4 = permissions2;
                                if (oldFlags == newFlags) {
                                    permissionsFlags = permissionsFlags2;
                                    $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u24110 = $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241103;
                                    permissions = permissions4;
                                } else {
                                    boolean isExempt = com.android.server.permission.access.util.IntExtensionsKt.hasAnyBit(newFlags, 229376);
                                    if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(oldFlags, 128) && !isExempt && wasGranted) {
                                        $i$f$mutateState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar |= 128;
                                        newFlags = com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags, 128);
                                    }
                                    int newFlags2 = (!com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 4) || isExempt) ? com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags, 262144) : newFlags | 262144;
                                    int newFlags3 = (!com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 8) || isExempt) ? com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags2, 524288) : newFlags2 | 524288;
                                    int mask = 262144 | $i$f$mutateState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar | 524288;
                                    permissions = permissions4;
                                    permissionsFlags = permissionsFlags2;
                                    $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u24110 = $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241103;
                                    $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241103.updatePermissionFlags($this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111, appId, userId, requestedPermission, mask, newFlags3);
                                }
                            } else {
                                appIdPermissionPolicy = $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241102;
                                oldState$iv = oldState$iv2;
                                permissions = permissions2;
                                permissionsFlags = permissionsFlags2;
                                $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u24110 = $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241103;
                            }
                        } else {
                            i = i2;
                            appIdPermissionPolicy = $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241102;
                            oldState$iv = oldState$iv2;
                            permissions = permissions2;
                            permissionsFlags = permissionsFlags2;
                            $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u24110 = $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241103;
                        }
                        list2 = list;
                        permissions2 = permissions;
                        permissionsFlags2 = permissionsFlags;
                        $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241103 = $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u24110;
                        index$iv = index$iv2;
                        $this$setAllowlistedRestrictedPermissionsUnchecked_u24lambda_u24111_u24lambda_u241102 = appIdPermissionPolicy;
                        i2 = i;
                        oldState$iv2 = oldState$iv;
                    }
                }
                this_$iv.persistence.write(newState$iv);
                this_$iv.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th3) {
                th = th3;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void resetRuntimePermissions(@org.jetbrains.annotations.NotNull com.android.server.pm.pkg.AndroidPackage androidPackage, int userId) {
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        synchronized (this_$iv.stateLock) {
            try {
                com.android.server.permission.access.AccessState oldState$iv = this_$iv.state;
                if (oldState$iv == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    oldState$iv = null;
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$resetRuntimePermissions_u24lambda_u24114 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.permission.AppIdPermissionPolicy $this$resetRuntimePermissions_u24lambda_u24114_u24lambda_u24112 = this.policy;
                $this$resetRuntimePermissions_u24lambda_u24114_u24lambda_u24112.resetRuntimePermissions($this$resetRuntimePermissions_u24lambda_u24114, androidPackage.getPackageName(), userId);
                com.android.server.permission.access.permission.DevicePermissionPolicy $this$resetRuntimePermissions_u24lambda_u24114_u24lambda_u24113 = this.devicePolicy;
                $this$resetRuntimePermissions_u24lambda_u24114_u24lambda_u24113.resetRuntimePermissions($this$resetRuntimePermissions_u24lambda_u24114, androidPackage.getPackageName(), userId);
                this_$iv.persistence.write(newState$iv);
                this_$iv.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void resetRuntimePermissionsForUser(int userId) {
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot snapshot = packageManagerLocal.withUnfilteredSnapshot();
        try {
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            synchronized (this_$iv.stateLock) {
                int i = 0;
                try {
                    com.android.server.permission.access.AccessState oldState$iv = this_$iv.state;
                    if (oldState$iv == null) {
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                        oldState$iv = null;
                    }
                    com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                    com.android.server.permission.access.MutateStateScope $this$resetRuntimePermissionsForUser_u24lambda_u24119_u24lambda_u24118 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                    java.util.Map $this$forEach$iv = snapshot.getPackageStates();
                    for (java.util.Map.Entry element$iv : $this$forEach$iv.entrySet()) {
                        com.android.server.pm.pkg.PackageState packageState = element$iv.getValue();
                        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$resetRuntimePermissionsForUser_u24lambda_u24119_u24lambda_u24118_u24lambda_u24117_u24lambda_u24115 = this.policy;
                        int i2 = i;
                        $this$resetRuntimePermissionsForUser_u24lambda_u24119_u24lambda_u24118_u24lambda_u24117_u24lambda_u24115.resetRuntimePermissions($this$resetRuntimePermissionsForUser_u24lambda_u24119_u24lambda_u24118, packageState.getPackageName(), userId);
                        com.android.server.permission.access.permission.DevicePermissionPolicy $this$resetRuntimePermissionsForUser_u24lambda_u24119_u24lambda_u24118_u24lambda_u24117_u24lambda_u24116 = this.devicePolicy;
                        $this$resetRuntimePermissionsForUser_u24lambda_u24119_u24lambda_u24118_u24lambda_u24117_u24lambda_u24116.resetRuntimePermissions($this$resetRuntimePermissionsForUser_u24lambda_u24119_u24lambda_u24118, packageState.getPackageName(), userId);
                        i = i2;
                    }
                    this_$iv.persistence.write(newState$iv);
                    this_$iv.state = newState$iv;
                    com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                    $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                    com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.permission.jarjar.kotlin.Unit unit2 = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void addOnPermissionsChangeListener(@org.jetbrains.annotations.NotNull android.permission.IOnPermissionsChangeListener listener) {
        this.context.enforceCallingOrSelfPermission("android.permission.OBSERVE_GRANT_REVOKE_PERMISSIONS", "addOnPermissionsChangeListener");
        com.android.server.permission.access.permission.PermissionService.OnPermissionsChangeListeners onPermissionsChangeListeners = this.onPermissionsChangeListeners;
        if (onPermissionsChangeListeners == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("onPermissionsChangeListeners");
            onPermissionsChangeListeners = null;
        }
        onPermissionsChangeListeners.addListener(listener);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void removeOnPermissionsChangeListener(@org.jetbrains.annotations.NotNull android.permission.IOnPermissionsChangeListener listener) {
        this.context.enforceCallingOrSelfPermission("android.permission.OBSERVE_GRANT_REVOKE_PERMISSIONS", "removeOnPermissionsChangeListener");
        com.android.server.permission.access.permission.PermissionService.OnPermissionsChangeListeners onPermissionsChangeListeners = this.onPermissionsChangeListeners;
        if (onPermissionsChangeListeners == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("onPermissionsChangeListeners");
            onPermissionsChangeListeners = null;
        }
        onPermissionsChangeListeners.removeListener(listener);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.NotNull
    public java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> getSplitPermissions() {
        com.android.server.SystemConfig systemConfig = this.systemConfig;
        if (systemConfig == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
            systemConfig = null;
        }
        return android.permission.PermissionManager.splitPermissionInfoListToParcelableList(systemConfig.getSplitPermissions());
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.NotNull
    public java.lang.String[] getAppOpPermissionPackages(@org.jetbrains.annotations.NotNull java.lang.String permissionName) {
        if (permissionName == null) {
            throw new java.lang.IllegalArgumentException("permissionName cannot be null".toString());
        }
        android.util.ArraySet packageNames = new android.util.ArraySet();
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getAppOpPermissionPackages_u24lambda_u24122 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getAppOpPermissionPackages_u24lambda_u24122_u24lambda_u24121 = this.policy;
        com.android.server.permission.access.permission.Permission permission = $this$getAppOpPermissionPackages_u24lambda_u24122_u24lambda_u24121.getPermissions($this$getAppOpPermissionPackages_u24lambda_u24122).get(permissionName);
        if (permission == null || !com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 64)) {
            packageNames.toArray(new java.lang.String[0]);
        }
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot snapshot = packageManagerLocal.withUnfilteredSnapshot();
        try {
            java.util.Map $this$forEach$iv = snapshot.getPackageStates();
            for (java.util.Map.Entry element$iv : $this$forEach$iv.entrySet()) {
                com.android.server.pm.pkg.PackageState packageState = element$iv.getValue();
                com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
                if (androidPackage != null && androidPackage.getRequestedPermissions().contains(permissionName)) {
                    packageNames.add(androidPackage.getPackageName());
                }
            }
            com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(snapshot, null);
            return (java.lang.String[]) packageNames.toArray(new java.lang.String[0]);
        } finally {
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.NotNull
    public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getAllAppOpPermissionPackages() {
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot snapshot;
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot snapshot2;
        android.util.ArrayMap appOpPermissionPackageNames = new android.util.ArrayMap();
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getAllAppOpPermissionPackages_u24lambda_u24126 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getAllAppOpPermissionPackages_u24lambda_u24126_u24lambda_u24125 = this.policy;
        com.android.server.permission.access.immutable.IndexedMap permissions = $this$getAllAppOpPermissionPackages_u24lambda_u24126_u24lambda_u24125.getPermissions($this$getAllAppOpPermissionPackages_u24lambda_u24126);
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot = packageManagerLocal.withUnfilteredSnapshot();
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot snapshot3 = withUnfilteredSnapshot;
        try {
            java.util.Map $this$forEach$iv = snapshot3.getPackageStates();
            for (java.util.Map.Entry element$iv : $this$forEach$iv.entrySet()) {
                com.android.server.pm.pkg.PackageState packageState = element$iv.getValue();
                com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
                if (androidPackage == null) {
                    snapshot = snapshot3;
                } else {
                    java.lang.Iterable $this$forEach$iv2 = androidPackage.getRequestedPermissions();
                    for (java.lang.Object element$iv2 : $this$forEach$iv2) {
                        java.lang.String permissionName = (java.lang.String) element$iv2;
                        com.android.server.permission.access.permission.Permission permission = permissions.get(permissionName);
                        if (permission == null) {
                            snapshot2 = snapshot3;
                        } else {
                            snapshot2 = snapshot3;
                            if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 64)) {
                                java.lang.Object obj = appOpPermissionPackageNames.get(permissionName);
                                if (obj == null) {
                                    android.util.ArraySet arraySet = new android.util.ArraySet();
                                    appOpPermissionPackageNames.put(permissionName, arraySet);
                                    obj = arraySet;
                                }
                                android.util.ArraySet packageNames = (android.util.ArraySet) obj;
                                packageNames.add(androidPackage.getPackageName());
                            }
                        }
                        snapshot3 = snapshot2;
                    }
                    snapshot = snapshot3;
                }
                snapshot3 = snapshot;
            }
            com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(withUnfilteredSnapshot, null);
            return appOpPermissionPackageNames;
        } finally {
        }
    }

    @org.jetbrains.annotations.Nullable
    public byte[] backupRuntimePermissions(int userId) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(userId, "userId cannot be null");
        final java.util.concurrent.CompletableFuture backup = new java.util.concurrent.CompletableFuture();
        android.permission.PermissionControllerManager permissionControllerManager = this.permissionControllerManager;
        if (permissionControllerManager == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("permissionControllerManager");
            permissionControllerManager = null;
        }
        permissionControllerManager.getRuntimePermissionBackup(android.os.UserHandle.of(userId), com.android.server.PermissionThread.getExecutor(), new java.util.function.Consumer() { // from class: com.android.server.permission.access.permission.PermissionService$backupRuntimePermissions$1
            @Override // java.util.function.Consumer
            public final void accept(byte[] p0) {
                backup.complete(p0);
            }
        });
        try {
            return (byte[]) backup.get(BACKUP_TIMEOUT_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (java.lang.Exception e) {
            if (e instanceof java.util.concurrent.TimeoutException ? true : e instanceof java.lang.InterruptedException ? true : e instanceof java.util.concurrent.ExecutionException) {
                android.util.Slog.e(LOG_TAG, "Cannot create permission backup for user " + userId, e);
                return null;
            }
            throw e;
        }
    }

    public void restoreRuntimePermissions(@org.jetbrains.annotations.NotNull byte[] backup, int userId) {
        if (backup == null) {
            throw new java.lang.IllegalArgumentException(com.android.server.am.HostingRecord.HOSTING_TYPE_BACKUP.toString());
        }
        com.android.internal.util.Preconditions.checkArgumentNonnegative(userId, "userId");
        synchronized (this.isDelayedPermissionBackupFinished) {
            android.util.SparseBooleanArray $this$minusAssign$iv = this.isDelayedPermissionBackupFinished;
            $this$minusAssign$iv.delete(userId);
            com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
        }
        android.permission.PermissionControllerManager permissionControllerManager = this.permissionControllerManager;
        if (permissionControllerManager == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("permissionControllerManager");
            permissionControllerManager = null;
        }
        permissionControllerManager.stageAndApplyRuntimePermissionsBackup(backup, android.os.UserHandle.of(userId));
    }

    public void restoreDelayedRuntimePermissions(@org.jetbrains.annotations.NotNull java.lang.String packageName, final int userId) {
        if (packageName == null) {
            throw new java.lang.IllegalArgumentException(com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME.toString());
        }
        com.android.internal.util.Preconditions.checkArgumentNonnegative(userId, "userId");
        synchronized (this.isDelayedPermissionBackupFinished) {
            if (this.isDelayedPermissionBackupFinished.get(userId, false)) {
                return;
            }
            com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            android.permission.PermissionControllerManager permissionControllerManager = this.permissionControllerManager;
            if (permissionControllerManager == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("permissionControllerManager");
                permissionControllerManager = null;
            }
            permissionControllerManager.applyStagedRuntimePermissionBackup(packageName, android.os.UserHandle.of(userId), com.android.server.PermissionThread.getExecutor(), new java.util.function.Consumer() { // from class: com.android.server.permission.access.permission.PermissionService$restoreDelayedRuntimePermissions$3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Boolean hasMoreBackup) {
                    android.util.SparseBooleanArray sparseBooleanArray;
                    android.util.SparseBooleanArray sparseBooleanArray2;
                    if (!hasMoreBackup.booleanValue()) {
                        sparseBooleanArray = com.android.server.permission.access.permission.PermissionService.this.isDelayedPermissionBackupFinished;
                        com.android.server.permission.access.permission.PermissionService permissionService = com.android.server.permission.access.permission.PermissionService.this;
                        int i = userId;
                        synchronized (sparseBooleanArray) {
                            sparseBooleanArray2 = permissionService.isDelayedPermissionBackupFinished;
                            sparseBooleanArray2.put(i, true);
                            com.android.server.permission.jarjar.kotlin.Unit unit2 = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                        }
                    }
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0080  */
    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dump(@org.jetbrains.annotations.NotNull java.io.FileDescriptor fd, @org.jetbrains.annotations.NotNull java.io.PrintWriter pw, @org.jetbrains.annotations.Nullable java.lang.String[] args) {
        boolean z;
        if (!com.android.internal.util.DumpUtils.checkDumpPermission(this.context, LOG_TAG, pw)) {
            return;
        }
        android.util.IndentingPrintWriter writer = new android.util.IndentingPrintWriter(pw, "  ");
        if (args != null) {
            if (!(args.length == 0)) {
                z = false;
                com.android.server.permission.access.AccessState accessState = null;
                if (!z) {
                    com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
                    com.android.server.permission.access.AccessState accessState2 = this_$iv.state;
                    if (accessState2 == null) {
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    } else {
                        accessState = accessState2;
                    }
                    com.android.server.permission.access.GetStateScope $this$dump_u24lambda_u24136 = new com.android.server.permission.access.GetStateScope(accessState);
                    dumpSystemState(writer, $this$dump_u24lambda_u24136.getState());
                    com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv = getAllAppIdPackageNames($this$dump_u24lambda_u24136.getState());
                    int size = $this$forEachIndexed$iv.getSize();
                    for (int index$iv = 0; index$iv < size; index$iv++) {
                        java.lang.Integer keyAt = $this$forEachIndexed$iv.keyAt(index$iv);
                        com.android.server.permission.access.immutable.MutableIndexedSet<java.lang.String> packageNames = $this$forEachIndexed$iv.valueAt(index$iv);
                        int appId = keyAt.intValue();
                        if (appId != -1) {
                            dumpAppIdState(writer, appId, $this$dump_u24lambda_u24136.getState(), packageNames);
                        }
                    }
                    return;
                }
                if (!com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(args[0], "--app-id") || args.length != 2) {
                    writer.println("Usage: dumpsys permission [--app-id APP_ID]");
                    return;
                }
                int appId2 = java.lang.Integer.parseInt(args[1]);
                com.android.server.permission.access.AccessCheckingService this_$iv2 = this.service;
                com.android.server.permission.access.AccessState accessState3 = this_$iv2.state;
                if (accessState3 == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                } else {
                    accessState = accessState3;
                }
                com.android.server.permission.access.GetStateScope $this$dump_u24lambda_u24137 = new com.android.server.permission.access.GetStateScope(accessState);
                com.android.server.permission.access.immutable.IndexedMap appIdPackageNames = getAllAppIdPackageNames($this$dump_u24lambda_u24137.getState());
                if (appIdPackageNames.contains(java.lang.Integer.valueOf(appId2))) {
                    dumpAppIdState(writer, appId2, $this$dump_u24lambda_u24137.getState(), appIdPackageNames.get(java.lang.Integer.valueOf(appId2)));
                    return;
                }
                writer.println("Unknown app ID " + appId2 + ".");
                return;
            }
        }
        z = true;
        com.android.server.permission.access.AccessState accessState4 = null;
        if (!z) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final com.android.server.permission.access.immutable.IndexedMap<java.lang.Integer, com.android.server.permission.access.immutable.MutableIndexedSet<java.lang.String>> getAllAppIdPackageNames(com.android.server.permission.access.AccessState state) {
        java.util.Map packageStates;
        com.android.server.permission.access.immutable.IndexedReferenceMap $this$forEachIndexed$iv;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv2;
        com.android.server.permission.access.immutable.MutableIndexedSet appIds = new com.android.server.permission.access.immutable.MutableIndexedSet(null, 1, null);
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot it = packageManagerLocal.withUnfilteredSnapshot();
        try {
            java.util.Map packageStates2 = it.getPackageStates();
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
            java.util.Map packageStates3 = packageStates2;
            com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv3 = state.getUserStates();
            int size = $this$forEachIndexed$iv3.getSize();
            for (int index$iv = 0; index$iv < size; index$iv++) {
                $this$forEachIndexed$iv3.keyAt(index$iv);
                com.android.server.permission.access.UserState userState = $this$forEachIndexed$iv3.valueAt(index$iv);
                com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv4 = userState.getAppIdPermissionFlags();
                int size2 = $this$forEachIndexed$iv4.getSize();
                for (int index$iv2 = 0; index$iv2 < size2; index$iv2++) {
                    int appId = $this$forEachIndexed$iv4.keyAt(index$iv2);
                    $this$forEachIndexed$iv4.valueAt(index$iv2);
                    appIds.add(java.lang.Integer.valueOf(appId));
                }
                com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv5 = userState.getAppIdAppOpModes();
                int size3 = $this$forEachIndexed$iv5.getSize();
                for (int index$iv3 = 0; index$iv3 < size3; index$iv3++) {
                    int appId2 = $this$forEachIndexed$iv5.keyAt(index$iv3);
                    $this$forEachIndexed$iv5.valueAt(index$iv3);
                    appIds.add(java.lang.Integer.valueOf(appId2));
                }
                com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv6 = userState.getPackageVersions();
                int index$iv4 = 0;
                int size4 = $this$forEachIndexed$iv6.getSize();
                while (index$iv4 < size4) {
                    java.lang.Object keyAt = $this$forEachIndexed$iv6.keyAt(index$iv4);
                    $this$forEachIndexed$iv6.valueAt(index$iv4).intValue();
                    java.lang.String packageName = (java.lang.String) keyAt;
                    com.android.server.pm.pkg.PackageState packageState = packageStates3.get(packageName);
                    if (packageState != null) {
                        int appId3 = packageState.getAppId();
                        $this$forEachIndexed$iv2 = $this$forEachIndexed$iv6;
                        appIds.add(java.lang.Integer.valueOf(appId3));
                    } else {
                        $this$forEachIndexed$iv2 = $this$forEachIndexed$iv6;
                    }
                    index$iv4++;
                    $this$forEachIndexed$iv6 = $this$forEachIndexed$iv2;
                }
                com.android.server.permission.access.immutable.IndexedReferenceMap $this$forEachIndexed$iv7 = userState.getPackageAppOpModes();
                int index$iv5 = 0;
                int size5 = $this$forEachIndexed$iv7.getSize();
                while (index$iv5 < size5) {
                    java.lang.Object keyAt2 = $this$forEachIndexed$iv7.keyAt(index$iv5);
                    $this$forEachIndexed$iv7.valueAt(index$iv5);
                    java.lang.String packageName2 = (java.lang.String) keyAt2;
                    com.android.server.pm.pkg.PackageState packageState2 = packageStates3.get(packageName2);
                    if (packageState2 != null) {
                        int appId4 = packageState2.getAppId();
                        $this$forEachIndexed$iv = $this$forEachIndexed$iv7;
                        appIds.add(java.lang.Integer.valueOf(appId4));
                    } else {
                        $this$forEachIndexed$iv = $this$forEachIndexed$iv7;
                    }
                    index$iv5++;
                    $this$forEachIndexed$iv7 = $this$forEachIndexed$iv;
                }
            }
            com.android.server.permission.access.immutable.MutableIndexedMap appIdPackageNames = new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null);
            for (java.util.Map.Entry element$iv : packageStates3.entrySet()) {
                com.android.server.pm.pkg.PackageState packageState3 = element$iv.getValue();
                java.lang.Integer valueOf = java.lang.Integer.valueOf(packageState3.getAppId());
                java.lang.Object obj = appIdPackageNames.get(valueOf);
                if (obj != null) {
                    packageStates = packageStates3;
                } else {
                    packageStates = packageStates3;
                    com.android.server.permission.access.immutable.MutableIndexedSet mutableIndexedSet = new com.android.server.permission.access.immutable.MutableIndexedSet(null, 1, null);
                    appIdPackageNames.put(valueOf, mutableIndexedSet);
                    obj = mutableIndexedSet;
                }
                ((com.android.server.permission.access.immutable.MutableIndexedSet) obj).add(packageState3.getPackageName());
                packageStates3 = packageStates;
            }
            int size6 = appIds.getSize();
            for (int index$iv6 = 0; index$iv6 < size6; index$iv6++) {
                int appId5 = ((java.lang.Number) appIds.elementAt(index$iv6)).intValue();
                java.lang.Integer valueOf2 = java.lang.Integer.valueOf(appId5);
                if (appIdPackageNames.get(valueOf2) == 0) {
                    appIdPackageNames.put(valueOf2, new com.android.server.permission.access.immutable.MutableIndexedSet(null, 1, null));
                }
            }
            return appIdPackageNames;
        } finally {
        }
    }

    private final void dumpSystemState(android.util.IndentingPrintWriter $this$dumpSystemState, com.android.server.permission.access.AccessState state) {
        $this$dumpSystemState.println("Permissions:");
        com.android.server.permission.access.permission.PermissionService this_$iv = this;
        int $i$f$withIndent = 0;
        $this$dumpSystemState.increaseIndent();
        int i = 0;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv = state.getSystemState().getPermissions();
        int $i$f$forEachIndexed = 0;
        int index$iv = 0;
        int size = $this$forEachIndexed$iv.getSize();
        while (index$iv < size) {
            $this$forEachIndexed$iv.keyAt(index$iv);
            com.android.server.permission.access.permission.Permission permission = $this$forEachIndexed$iv.valueAt(index$iv);
            java.lang.String protectionLevel = android.content.pm.PermissionInfo.protectionToString(permission.getPermissionInfo().protectionLevel);
            com.android.server.permission.access.permission.PermissionService this_$iv2 = this_$iv;
            java.lang.String str = permission.getPermissionInfo().name;
            java.lang.String typeToString = com.android.server.permission.access.permission.Permission.Companion.typeToString(permission.getType());
            int $i$f$withIndent2 = $i$f$withIndent;
            java.lang.String str2 = permission.getPermissionInfo().packageName;
            int appId = permission.getAppId();
            int i2 = i;
            java.lang.String arrays = java.util.Arrays.toString(permission.getGids());
            com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv2 = $this$forEachIndexed$iv;
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(arrays, "toString(...)");
            $this$dumpSystemState.println(str + ": type=" + typeToString + ", packageName=" + str2 + ", appId=" + appId + ", gids=" + arrays + ", protectionLevel=[" + protectionLevel + "], flags=" + android.content.pm.PermissionInfo.flagsToString(permission.getPermissionInfo().flags));
            index$iv++;
            this_$iv = this_$iv2;
            $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
            $i$f$withIndent = $i$f$withIndent2;
            i = i2;
            $i$f$forEachIndexed = $i$f$forEachIndexed;
        }
        $this$dumpSystemState.decreaseIndent();
        $this$dumpSystemState.println("Permission groups:");
        com.android.server.permission.access.permission.PermissionService this_$iv3 = this;
        $this$dumpSystemState.increaseIndent();
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv3 = state.getSystemState().getPermissionGroups();
        int index$iv2 = 0;
        int size2 = $this$forEachIndexed$iv3.getSize();
        while (index$iv2 < size2) {
            $this$forEachIndexed$iv3.keyAt(index$iv2);
            android.content.pm.PermissionGroupInfo permissionGroup = $this$forEachIndexed$iv3.valueAt(index$iv2);
            $this$dumpSystemState.println(permissionGroup.name + ": packageName=" + permissionGroup.packageName);
            index$iv2++;
            this_$iv3 = this_$iv3;
        }
        $this$dumpSystemState.decreaseIndent();
        $this$dumpSystemState.println("Permission trees:");
        com.android.server.permission.access.permission.PermissionService this_$iv4 = this;
        $this$dumpSystemState.increaseIndent();
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv4 = state.getSystemState().getPermissionTrees();
        int index$iv3 = 0;
        int size3 = $this$forEachIndexed$iv4.getSize();
        while (index$iv3 < size3) {
            $this$forEachIndexed$iv4.keyAt(index$iv3);
            com.android.server.permission.access.permission.Permission permissionTree = $this$forEachIndexed$iv4.valueAt(index$iv3);
            com.android.server.permission.access.permission.PermissionService this_$iv5 = this_$iv4;
            $this$dumpSystemState.println(permissionTree.getPermissionInfo().name + ": packageName=" + permissionTree.getPermissionInfo().packageName + ", appId=" + permissionTree.getAppId());
            index$iv3++;
            this_$iv4 = this_$iv5;
        }
        $this$dumpSystemState.decreaseIndent();
    }

    private final void dumpAppIdState(android.util.IndentingPrintWriter $this$dumpAppIdState, int appId, com.android.server.permission.access.AccessState state, com.android.server.permission.access.immutable.IndexedSet<java.lang.String> indexedSet) {
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv;
        int $i$f$forEachIndexed;
        int i;
        int i2 = appId;
        $this$dumpAppIdState.println("App ID: " + i2);
        com.android.server.permission.access.permission.PermissionService this_$iv = this;
        int $i$f$withIndent = 0;
        $this$dumpAppIdState.increaseIndent();
        android.util.IndentingPrintWriter $this$dumpAppIdState_u24lambda_u24167 = $this$dumpAppIdState;
        int i3 = 0;
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv2 = state.getUserStates();
        int $i$f$forEachIndexed2 = 0;
        int index$iv = 0;
        int size = $this$forEachIndexed$iv2.getSize();
        while (index$iv < size) {
            int userId = $this$forEachIndexed$iv2.keyAt(index$iv);
            com.android.server.permission.access.UserState userState = $this$forEachIndexed$iv2.valueAt(index$iv);
            int i4 = 0;
            $this$dumpAppIdState_u24lambda_u24167.println("User: " + userId);
            android.util.IndentingPrintWriter $this$withIndent$iv = $this$dumpAppIdState_u24lambda_u24167;
            $this$withIndent$iv.increaseIndent();
            com.android.server.permission.access.permission.PermissionService this_$iv2 = this_$iv;
            android.util.IndentingPrintWriter $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165 = $this$withIndent$iv;
            $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165.println("Permissions:");
            $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165.increaseIndent();
            int $i$f$withIndent2 = $i$f$withIndent;
            com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv3 = userState.getAppIdPermissionFlags().get(i2);
            android.util.IndentingPrintWriter $this$dumpAppIdState_u24lambda_u241672 = $this$dumpAppIdState_u24lambda_u24167;
            int i5 = i3;
            if ($this$forEachIndexed$iv3 != null) {
                $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                int size2 = $this$forEachIndexed$iv3.getSize();
                $i$f$forEachIndexed = $i$f$forEachIndexed2;
                int $i$f$forEachIndexed3 = 0;
                while ($i$f$forEachIndexed3 < size2) {
                    java.lang.String keyAt = $this$forEachIndexed$iv3.keyAt($i$f$forEachIndexed3);
                    com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv4 = $this$forEachIndexed$iv3;
                    int flags = $this$forEachIndexed$iv3.valueAt($i$f$forEachIndexed3).intValue();
                    java.lang.String permissionName = keyAt;
                    int i6 = size2;
                    boolean isGranted = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isPermissionGranted(flags);
                    int i7 = size;
                    $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165.println(permissionName + ": granted=" + isGranted + ", flags=" + com.android.server.permission.access.permission.PermissionFlags.INSTANCE.toString(flags));
                    $i$f$forEachIndexed3++;
                    $this$forEachIndexed$iv3 = $this$forEachIndexed$iv4;
                    size2 = i6;
                    size = i7;
                    userId = userId;
                }
                i = size;
            } else {
                $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                $i$f$forEachIndexed = $i$f$forEachIndexed2;
                i = size;
            }
            $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165.decreaseIndent();
            com.android.server.permission.access.immutable.IndexedReferenceMap $this$forEachIndexed$iv5 = userState.getAppIdDevicePermissionFlags().get(i2);
            if ($this$forEachIndexed$iv5 != null) {
                int $i$f$forEachIndexed4 = 0;
                int index$iv2 = 0;
                int size3 = $this$forEachIndexed$iv5.getSize();
                while (index$iv2 < size3) {
                    java.lang.String keyAt2 = $this$forEachIndexed$iv5.keyAt(index$iv2);
                    com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> devicePermissionFlags = $this$forEachIndexed$iv5.valueAt(index$iv2);
                    java.lang.String deviceId = keyAt2;
                    com.android.server.permission.access.immutable.IndexedReferenceMap $this$forEachIndexed$iv6 = $this$forEachIndexed$iv5;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    int $i$f$forEachIndexed5 = $i$f$forEachIndexed4;
                    sb.append("Permissions (Device ");
                    sb.append(deviceId);
                    sb.append("):");
                    $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165.println(sb.toString());
                    $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165.increaseIndent();
                    com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv7 = devicePermissionFlags;
                    int size4 = $this$forEachIndexed$iv7.getSize();
                    int i8 = size3;
                    int index$iv3 = 0;
                    while (index$iv3 < size4) {
                        int i9 = size4;
                        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv8 = $this$forEachIndexed$iv7;
                        java.lang.String keyAt3 = $this$forEachIndexed$iv8.keyAt(index$iv3);
                        int flags2 = $this$forEachIndexed$iv8.valueAt(index$iv3).intValue();
                        java.lang.String deviceId2 = deviceId;
                        java.lang.String permissionName2 = keyAt3;
                        com.android.server.permission.access.immutable.IndexedMap devicePermissionFlags2 = devicePermissionFlags;
                        boolean isGranted2 = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isPermissionGranted(flags2);
                        int i10 = i4;
                        $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165.println(permissionName2 + ": granted=" + isGranted2 + ", flags=" + com.android.server.permission.access.permission.PermissionFlags.INSTANCE.toString(flags2));
                        index$iv3++;
                        size4 = i9;
                        deviceId = deviceId2;
                        $this$forEachIndexed$iv7 = $this$forEachIndexed$iv8;
                        devicePermissionFlags = devicePermissionFlags2;
                        i4 = i10;
                    }
                    $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165.decreaseIndent();
                    index$iv2++;
                    $this$forEachIndexed$iv5 = $this$forEachIndexed$iv6;
                    $i$f$forEachIndexed4 = $i$f$forEachIndexed5;
                    size3 = i8;
                    i4 = i4;
                }
            }
            java.lang.String str = "App ops:";
            $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165.println("App ops:");
            int $i$f$withIndent3 = 0;
            $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165.increaseIndent();
            int i11 = 0;
            com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv9 = userState.getAppIdAppOpModes().get(i2);
            if ($this$forEachIndexed$iv9 != null) {
                int size5 = $this$forEachIndexed$iv9.getSize();
                int index$iv4 = 0;
                while (index$iv4 < size5) {
                    java.lang.String keyAt4 = $this$forEachIndexed$iv9.keyAt(index$iv4);
                    int appOpMode = $this$forEachIndexed$iv9.valueAt(index$iv4).intValue();
                    java.lang.String appOpName = keyAt4;
                    int i12 = size5;
                    $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165.println(appOpName + ": mode=" + android.app.AppOpsManager.modeToName(appOpMode));
                    index$iv4++;
                    size5 = i12;
                    $i$f$withIndent3 = $i$f$withIndent3;
                    i11 = i11;
                }
            }
            $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165.decreaseIndent();
            if (indexedSet != null) {
                com.android.server.permission.access.immutable.IndexedSet $this$forEachIndexed$iv10 = indexedSet;
                int $i$f$forEachIndexed6 = 0;
                int index$iv5 = 0;
                int size6 = $this$forEachIndexed$iv10.getSize();
                while (index$iv5 < size6) {
                    java.lang.String packageName = $this$forEachIndexed$iv10.elementAt(index$iv5);
                    $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165.println("Package: " + packageName);
                    android.util.IndentingPrintWriter $this$withIndent$iv2 = $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165;
                    $this$withIndent$iv2.increaseIndent();
                    com.android.server.permission.access.immutable.IndexedSet $this$forEachIndexed$iv11 = $this$forEachIndexed$iv10;
                    java.lang.Integer num = userState.getPackageVersions().get(packageName);
                    android.util.IndentingPrintWriter $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u241652 = $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165;
                    java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                    int $i$f$forEachIndexed7 = $i$f$forEachIndexed6;
                    sb2.append("version=");
                    sb2.append(num);
                    $this$withIndent$iv2.println(sb2.toString());
                    $this$withIndent$iv2.println(str);
                    $this$withIndent$iv2.increaseIndent();
                    java.lang.String str2 = str;
                    com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv12 = userState.getPackageAppOpModes().get(packageName);
                    if ($this$forEachIndexed$iv12 != null) {
                        int size7 = $this$forEachIndexed$iv12.getSize();
                        int index$iv6 = 0;
                        while (index$iv6 < size7) {
                            java.lang.String keyAt5 = $this$forEachIndexed$iv12.keyAt(index$iv6);
                            int appOpMode2 = $this$forEachIndexed$iv12.valueAt(index$iv6).intValue();
                            com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv13 = $this$forEachIndexed$iv12;
                            java.lang.String appOpName2 = keyAt5;
                            int i13 = size7;
                            java.lang.String modeName = android.app.AppOpsManager.modeToName(appOpMode2);
                            $this$withIndent$iv2.println(appOpName2 + ": mode=" + modeName);
                            index$iv6++;
                            $this$forEachIndexed$iv12 = $this$forEachIndexed$iv13;
                            size7 = i13;
                            size6 = size6;
                        }
                    }
                    $this$withIndent$iv2.decreaseIndent();
                    $this$withIndent$iv2.decreaseIndent();
                    index$iv5++;
                    $this$forEachIndexed$iv10 = $this$forEachIndexed$iv11;
                    $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u24165 = $this$dumpAppIdState_u24lambda_u24167_u24lambda_u24166_u24lambda_u241652;
                    $i$f$forEachIndexed6 = $i$f$forEachIndexed7;
                    str = str2;
                    size6 = size6;
                }
            }
            $this$withIndent$iv.decreaseIndent();
            index$iv++;
            i2 = appId;
            this_$iv = this_$iv2;
            $i$f$withIndent = $i$f$withIndent2;
            $this$dumpAppIdState_u24lambda_u24167 = $this$dumpAppIdState_u24lambda_u241672;
            i3 = i5;
            $this$forEachIndexed$iv2 = $this$forEachIndexed$iv;
            $i$f$forEachIndexed2 = $i$f$forEachIndexed;
            size = i;
        }
        $this$dumpAppIdState.decreaseIndent();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.Nullable
    public com.android.server.pm.permission.Permission getPermissionTEMP(@org.jetbrains.annotations.NotNull java.lang.String permissionName) {
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getPermissionTEMP_u24lambda_u24169 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getPermissionTEMP_u24lambda_u24169_u24lambda_u24168 = this.policy;
        com.android.server.permission.access.permission.Permission permission = $this$getPermissionTEMP_u24lambda_u24169_u24lambda_u24168.getPermissions($this$getPermissionTEMP_u24lambda_u24169).get(permissionName);
        if (permission == null) {
            return null;
        }
        return new com.android.server.pm.permission.Permission(permission.getPermissionInfo(), permission.getType(), permission.isReconciled(), permission.getAppId(), permission.getGids(), permission.getAreGidsPerUser());
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.NotNull
    public java.util.List<com.android.server.pm.permission.LegacyPermission> getLegacyPermissions() {
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getLegacyPermissions_u24lambda_u24171 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getLegacyPermissions_u24lambda_u24171_u24lambda_u24170 = this.policy;
        com.android.server.permission.access.immutable.IndexedMap permissions = $this$getLegacyPermissions_u24lambda_u24171_u24lambda_u24170.getPermissions($this$getLegacyPermissions_u24lambda_u24171);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.permission.access.immutable.IndexedMap $this$mapIndexedTo$iv = permissions;
        int $i$f$mapIndexedTo = 0;
        int index$iv$iv = 0;
        int size = $this$mapIndexedTo$iv.getSize();
        while (index$iv$iv < size) {
            java.lang.Object key$iv = $this$mapIndexedTo$iv.keyAt(index$iv$iv);
            java.lang.Object value$iv = $this$mapIndexedTo$iv.valueAt(index$iv$iv);
            com.android.server.permission.access.permission.Permission permission = (com.android.server.permission.access.permission.Permission) value$iv;
            arrayList.add(new com.android.server.pm.permission.LegacyPermission(permission.getPermissionInfo(), permission.getType(), permission.getAppId(), permission.getGids()));
            index$iv$iv++;
            $this$mapIndexedTo$iv = $this$mapIndexedTo$iv;
            $i$f$mapIndexedTo = $i$f$mapIndexedTo;
        }
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void readLegacyPermissionsTEMP(@org.jetbrains.annotations.NotNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings) {
        this.service.initialize();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void writeLegacyPermissionsTEMP(@org.jetbrains.annotations.NotNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings) {
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$writeLegacyPermissionsTEMP_u24lambda_u24175 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$writeLegacyPermissionsTEMP_u24lambda_u24175_u24lambda_u24173 = this.policy;
        com.android.server.permission.access.immutable.IndexedMap permissions = $this$writeLegacyPermissionsTEMP_u24lambda_u24175_u24lambda_u24173.getPermissions($this$writeLegacyPermissionsTEMP_u24lambda_u24175);
        legacyPermissionSettings.replacePermissions(toLegacyPermissions(permissions));
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$writeLegacyPermissionsTEMP_u24lambda_u24175_u24lambda_u24174 = this.policy;
        com.android.server.permission.access.immutable.IndexedMap permissionTrees = $this$writeLegacyPermissionsTEMP_u24lambda_u24175_u24lambda_u24174.getPermissionTrees($this$writeLegacyPermissionsTEMP_u24lambda_u24175);
        legacyPermissionSettings.replacePermissionTrees(toLegacyPermissions(permissionTrees));
    }

    private final java.util.List<com.android.server.pm.permission.LegacyPermission> toLegacyPermissions(com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission> indexedMap) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.permission.access.immutable.IndexedMap $this$mapIndexedTo$iv = indexedMap;
        int $i$f$mapIndexedTo = 0;
        int index$iv$iv = 0;
        int size = $this$mapIndexedTo$iv.getSize();
        while (index$iv$iv < size) {
            java.lang.Object key$iv = $this$mapIndexedTo$iv.keyAt(index$iv$iv);
            java.lang.Object value$iv = $this$mapIndexedTo$iv.valueAt(index$iv$iv);
            com.android.server.permission.access.permission.Permission permission = (com.android.server.permission.access.permission.Permission) value$iv;
            arrayList.add(new com.android.server.pm.permission.LegacyPermission(permission.getPermissionInfo(), permission.getType(), 0, libcore.util.EmptyArray.INT));
            index$iv$iv++;
            $this$mapIndexedTo$iv = $this$mapIndexedTo$iv;
            $i$f$mapIndexedTo = $i$f$mapIndexedTo;
        }
        return arrayList;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.NotNull
    public com.android.server.pm.permission.LegacyPermissionState getLegacyPermissionState(int appId) {
        int[] userIds;
        com.android.server.permission.access.GetStateScope $this$getLegacyPermissionState_u24lambda_u24181;
        com.android.server.permission.access.AccessCheckingService this_$iv;
        int $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar;
        int i;
        com.android.server.permission.access.immutable.IndexedMap permissions;
        com.android.server.permission.access.AccessCheckingService this_$iv2;
        int $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2;
        int i2;
        com.android.server.permission.access.immutable.IndexedMap permissions2;
        com.android.server.permission.access.permission.PermissionService permissionService = this;
        com.android.server.pm.permission.LegacyPermissionState legacyState = new com.android.server.pm.permission.LegacyPermissionState();
        com.android.server.pm.UserManagerService userManagerService = permissionService.userManagerService;
        com.android.server.permission.access.AccessState accessState = null;
        if (userManagerService == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerService");
            userManagerService = null;
        }
        int[] userIds2 = userManagerService.getUserIdsIncludingPreCreated();
        com.android.server.permission.access.AccessCheckingService this_$iv3 = permissionService.service;
        int $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar3 = 0;
        com.android.server.permission.access.AccessState accessState2 = this_$iv3.state;
        if (accessState2 == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
        } else {
            accessState = accessState2;
        }
        com.android.server.permission.access.GetStateScope $this$getLegacyPermissionState_u24lambda_u241812 = new com.android.server.permission.access.GetStateScope(accessState);
        int i3 = 0;
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getLegacyPermissionState_u24lambda_u24181_u24lambda_u24177 = permissionService.policy;
        com.android.server.permission.access.immutable.IndexedMap permissions3 = $this$getLegacyPermissionState_u24lambda_u24181_u24lambda_u24177.getPermissions($this$getLegacyPermissionState_u24lambda_u241812);
        int index$iv = 0;
        int length = userIds2.length;
        int i4 = 0;
        while (i4 < length) {
            int item$iv = userIds2[i4];
            index$iv++;
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$getLegacyPermissionState_u24lambda_u24181_u24lambda_u24180_u24lambda_u24178 = permissionService.policy;
            com.android.server.permission.access.immutable.IndexedMap permissionFlags = $this$getLegacyPermissionState_u24lambda_u24181_u24lambda_u24180_u24lambda_u24178.getUidPermissionFlags($this$getLegacyPermissionState_u24lambda_u241812, appId, item$iv);
            if (permissionFlags == null) {
                userIds = userIds2;
                $this$getLegacyPermissionState_u24lambda_u24181 = $this$getLegacyPermissionState_u24lambda_u241812;
                this_$iv = this_$iv3;
                $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar = $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar3;
                i = i3;
                permissions = permissions3;
            } else {
                com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv = permissionFlags;
                int size = $this$forEachIndexed$iv.getSize();
                userIds = userIds2;
                int index$iv2 = 0;
                while (index$iv2 < size) {
                    int i5 = size;
                    com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv2 = $this$forEachIndexed$iv;
                    java.lang.String keyAt = $this$forEachIndexed$iv2.keyAt(index$iv2);
                    int flags = $this$forEachIndexed$iv2.valueAt(index$iv2).intValue();
                    com.android.server.permission.access.GetStateScope $this$getLegacyPermissionState_u24lambda_u241813 = $this$getLegacyPermissionState_u24lambda_u241812;
                    java.lang.String permissionName = keyAt;
                    com.android.server.permission.access.permission.Permission permission = permissions3.get(permissionName);
                    if (permission == null) {
                        this_$iv2 = this_$iv3;
                        $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2 = $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar3;
                        i2 = i3;
                        permissions2 = permissions3;
                    } else {
                        this_$iv2 = this_$iv3;
                        $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2 = $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar3;
                        int $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar4 = permission.getPermissionInfo().getProtection();
                        i2 = i3;
                        permissions2 = permissions3;
                        com.android.server.pm.permission.LegacyPermissionState.PermissionState legacyPermissionState = new com.android.server.pm.permission.LegacyPermissionState.PermissionState(permissionName, $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar4 == 1, com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isPermissionGranted(flags), com.android.server.permission.access.permission.PermissionFlags.INSTANCE.toApiFlags(flags));
                        legacyState.putPermissionState(legacyPermissionState, item$iv);
                    }
                    index$iv2++;
                    size = i5;
                    $this$getLegacyPermissionState_u24lambda_u241812 = $this$getLegacyPermissionState_u24lambda_u241813;
                    $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                    this_$iv3 = this_$iv2;
                    permissions3 = permissions2;
                    i3 = i2;
                    $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar3 = $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar2;
                }
                $this$getLegacyPermissionState_u24lambda_u24181 = $this$getLegacyPermissionState_u24lambda_u241812;
                this_$iv = this_$iv3;
                $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar = $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar3;
                i = i3;
                permissions = permissions3;
            }
            i4++;
            permissionService = this;
            userIds2 = userIds;
            $this$getLegacyPermissionState_u24lambda_u241812 = $this$getLegacyPermissionState_u24lambda_u24181;
            this_$iv3 = this_$iv;
            permissions3 = permissions;
            i3 = i;
            $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar3 = $i$f$getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar;
        }
        return legacyState;
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void readLegacyPermissionStateTEMP() {
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void writeLegacyPermissionStateTEMP() {
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    @org.jetbrains.annotations.Nullable
    public java.lang.String getDefaultPermissionGrantFingerprint(int userId) {
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        com.android.server.permission.access.AccessState accessState = this_$iv.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        com.android.server.permission.access.GetStateScope $this$getDefaultPermissionGrantFingerprint_u24lambda_u24182 = new com.android.server.permission.access.GetStateScope(accessState);
        com.android.server.permission.access.UserState userState = $this$getDefaultPermissionGrantFingerprint_u24lambda_u24182.getState().getUserStates().get(userId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        return userState.getDefaultPermissionGrantFingerprint();
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void setDefaultPermissionGrantFingerprint(@org.jetbrains.annotations.NotNull java.lang.String fingerprint, int userId) {
        com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
        synchronized (this_$iv.stateLock) {
            try {
                com.android.server.permission.access.AccessState oldState$iv = this_$iv.state;
                if (oldState$iv == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    oldState$iv = null;
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$setDefaultPermissionGrantFingerprint_u24lambda_u24183 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.MutableUserState mutateUserState$default = com.android.server.permission.access.MutableAccessState.mutateUserState$default($this$setDefaultPermissionGrantFingerprint_u24lambda_u24183.getNewState(), userId, 0, 2, null);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(mutateUserState$default);
                mutateUserState$default.setDefaultPermissionGrantFingerprintPublic(fingerprint);
                this_$iv.persistence.write(newState$iv);
                this_$iv.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onSystemReady() {
        java.util.Set persistentDeviceIds;
        this.service.onSystemReady$frameworks__base__services__permission__android_common__services_permission_pre_jarjar();
        this.virtualDeviceManagerInternal = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.LocalServices.getService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);
        com.android.server.companion.virtual.VirtualDeviceManagerInternal virtualDeviceManagerInternal = this.virtualDeviceManagerInternal;
        if (virtualDeviceManagerInternal != null && (persistentDeviceIds = virtualDeviceManagerInternal.getAllPersistentDeviceIds()) != null) {
            com.android.server.permission.access.AccessCheckingService this_$iv = this.service;
            synchronized (this_$iv.stateLock) {
                try {
                    com.android.server.permission.access.AccessState oldState$iv = this_$iv.state;
                    if (oldState$iv == null) {
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                        oldState$iv = null;
                    }
                    com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                    com.android.server.permission.access.MutateStateScope $this$onSystemReady_u24lambda_u24186_u24lambda_u24185 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                    com.android.server.permission.access.permission.DevicePermissionPolicy $this$onSystemReady_u24lambda_u24186_u24lambda_u24185_u24lambda_u24184 = this.devicePolicy;
                    $this$onSystemReady_u24lambda_u24186_u24lambda_u24185_u24lambda_u24184.trimDevicePermissionStates($this$onSystemReady_u24lambda_u24186_u24lambda_u24185, persistentDeviceIds);
                    this_$iv.persistence.write(newState$iv);
                    this_$iv.state = newState$iv;
                    com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this_$iv.policy;
                    $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                    com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        com.android.server.companion.virtual.VirtualDeviceManagerInternal virtualDeviceManagerInternal2 = this.virtualDeviceManagerInternal;
        if (virtualDeviceManagerInternal2 != null) {
            virtualDeviceManagerInternal2.registerPersistentDeviceIdRemovedListener(new java.util.function.Consumer() { // from class: com.android.server.permission.access.permission.PermissionService$onSystemReady$2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.String deviceId) {
                    com.android.server.permission.access.AccessCheckingService this_$iv2 = com.android.server.permission.access.permission.PermissionService.this.service;
                    com.android.server.permission.access.permission.PermissionService permissionService = com.android.server.permission.access.permission.PermissionService.this;
                    synchronized (this_$iv2.stateLock) {
                        try {
                            com.android.server.permission.access.AccessState oldState$iv2 = this_$iv2.state;
                            if (oldState$iv2 == null) {
                                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                                oldState$iv2 = null;
                            }
                            com.android.server.permission.access.MutableAccessState newState$iv2 = oldState$iv2.toMutable();
                            com.android.server.permission.access.MutateStateScope $this$accept_u24lambda_u241 = new com.android.server.permission.access.MutateStateScope(oldState$iv2, newState$iv2);
                            com.android.server.permission.access.permission.DevicePermissionPolicy $this$accept_u24lambda_u241_u24lambda_u240 = permissionService.devicePolicy;
                            $this$accept_u24lambda_u241_u24lambda_u240.onDeviceIdRemoved($this$accept_u24lambda_u241, deviceId);
                            this_$iv2.persistence.write(newState$iv2);
                            this_$iv2.state = newState$iv2;
                            com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv2 = this_$iv2.policy;
                            $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv2.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv2));
                            com.android.server.permission.jarjar.kotlin.Unit unit2 = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                        } catch (java.lang.Throwable th2) {
                            throw th2;
                        }
                    }
                }
            });
        }
        this.permissionControllerManager = new android.permission.PermissionControllerManager(this.context, com.android.server.PermissionThread.getHandler());
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onUserCreated(int userId) {
        android.content.pm.PackageManager.corkPackageInfoCache();
        try {
            this.service.onUserAdded$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(userId);
            com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
        } finally {
            android.content.pm.PackageManager.uncorkPackageInfoCache();
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onUserRemoved(int userId) {
        this.service.onUserRemoved$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(userId);
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onStorageVolumeMounted(@org.jetbrains.annotations.NotNull java.lang.String volumeUuid, boolean fingerprintChanged) {
        java.util.List<java.lang.String> list;
        synchronized (this.storageVolumeLock) {
            try {
                java.util.List<java.lang.String> remove = this.storageVolumePackageNames.remove(volumeUuid);
                if (remove == null) {
                    remove = com.android.server.permission.jarjar.kotlin.collections.CollectionsKt__CollectionsKt.emptyList();
                }
                list = remove;
                android.util.ArraySet $this$plusAssign$iv = this.mountedStorageVolumes;
                $this$plusAssign$iv.add(volumeUuid);
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.content.pm.PackageManager.corkPackageInfoCache();
        try {
            this.service.onStorageVolumeMounted$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(volumeUuid, list, fingerprintChanged);
            com.android.server.permission.jarjar.kotlin.Unit unit2 = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
        } finally {
            android.content.pm.PackageManager.uncorkPackageInfoCache();
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageAdded(@org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState, boolean isInstantApp, @org.jetbrains.annotations.Nullable com.android.server.pm.pkg.AndroidPackage oldPackage) {
        synchronized (this.storageVolumeLock) {
            android.util.ArrayMap $this$getOrPut$iv = this.storageVolumePackageNames;
            java.lang.String volumeUuid = packageState.getVolumeUuid();
            java.lang.Object it$iv = $this$getOrPut$iv.get(volumeUuid);
            if (it$iv == null) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                $this$getOrPut$iv.put(volumeUuid, arrayList);
                it$iv = arrayList;
            }
            ((java.util.Collection) it$iv).add(packageState.getPackageName());
            if (this.mountedStorageVolumes.contains(packageState.getVolumeUuid())) {
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                this.service.onPackageAdded$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(packageState.getPackageName());
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageRemoved(@org.jetbrains.annotations.NotNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageInstalled(@org.jetbrains.annotations.NotNull com.android.server.pm.pkg.AndroidPackage androidPackage, int previousAppId, @org.jetbrains.annotations.NotNull com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams params, int userId) {
        int[] userIds;
        if (params == com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams.DEFAULT) {
            return;
        }
        synchronized (this.storageVolumeLock) {
            if (this.mountedStorageVolumes.contains(androidPackage.getVolumeUuid())) {
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                if (userId == -1) {
                    com.android.server.pm.UserManagerService userManagerService = this.userManagerService;
                    if (userManagerService == null) {
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerService");
                        userManagerService = null;
                    }
                    userIds = userManagerService.getUserIdsIncludingPreCreated();
                } else {
                    userIds = new int[]{userId};
                }
                for (int i : userIds) {
                    this.service.onPackageInstalled$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(androidPackage.getPackageName(), i);
                }
                int[] $this$forEach$iv = userIds;
                for (int element$iv : $this$forEach$iv) {
                    android.content.pm.PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
                    if (packageManagerInternal == null) {
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
                        packageManagerInternal = null;
                    }
                    com.android.server.pm.pkg.PackageStateInternal packageState = packageManagerInternal.getPackageStateInternal(androidPackage.getPackageName());
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState);
                    addAllowlistedRestrictedPermissionsUnchecked(androidPackage, packageState.getAppId(), params.getAllowlistedRestrictedPermissions(), element$iv);
                    setRequestedPermissionStates(packageState, element$iv, params.getPermissionStates());
                }
            }
        }
    }

    @Override // com.android.server.pm.permission.PermissionManagerServiceInterface
    public void onPackageUninstalled(@org.jetbrains.annotations.NotNull java.lang.String packageName, int appId, @org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState, @org.jetbrains.annotations.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @org.jetbrains.annotations.NotNull java.util.List<? extends com.android.server.pm.pkg.AndroidPackage> list, int userId) {
        int[] userIds;
        android.content.pm.PackageManagerInternal packageManagerInternal = null;
        if (userId == -1) {
            com.android.server.pm.UserManagerService userManagerService = this.userManagerService;
            if (userManagerService == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerService");
                userManagerService = null;
            }
            userIds = userManagerService.getUserIdsIncludingPreCreated();
        } else {
            userIds = new int[]{userId};
        }
        int[] $this$forEach$iv = userIds;
        for (int element$iv : $this$forEach$iv) {
            this.service.onPackageUninstalled$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(packageName, appId, element$iv);
        }
        android.content.pm.PackageManagerInternal packageManagerInternal2 = this.packageManagerInternal;
        if (packageManagerInternal2 == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
        } else {
            packageManagerInternal = packageManagerInternal2;
        }
        com.android.server.pm.pkg.PackageStateInternal packageState2 = packageManagerInternal.getPackageStates().get(packageName);
        if (packageState2 == null) {
            this.service.onPackageRemoved$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(packageName, appId);
        }
    }

    private final boolean isRootOrSystemUid(int uid) {
        switch (android.os.UserHandle.getAppId(uid)) {
            case 0:
            case 1000:
                return true;
            default:
                return false;
        }
    }

    private final boolean isShellUid(int uid) {
        return android.os.UserHandle.getAppId(uid) == 2000;
    }

    private final boolean isRootOrSystemOrShellUid(int uid) {
        return isRootOrSystemUid(uid) || isShellUid(uid);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void killUid(int uid, java.lang.String reason) {
        android.app.IActivityManager activityManager = android.app.ActivityManager.getService();
        if (activityManager != null) {
            int appId = android.os.UserHandle.getAppId(uid);
            int userId = android.os.UserHandle.getUserId(uid);
            long token$iv = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    activityManager.killUidForPermissionChange(appId, userId, reason);
                } finally {
                    android.os.Binder.restoreCallingIdentity(token$iv);
                }
            } catch (android.os.RemoteException e) {
            }
            com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
        }
    }

    private final com.android.server.pm.PackageManagerLocal.FilteredSnapshot withFilteredSnapshot(com.android.server.pm.PackageManagerLocal $this$withFilteredSnapshot, int callingUid, int userId) {
        return $this$withFilteredSnapshot.withFilteredSnapshot(callingUid, android.os.UserHandle.of(userId));
    }

    private final com.android.server.pm.pkg.PackageState getPackageState(com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot $this$getPackageState, java.lang.String packageName) {
        return $this$getPackageState.getPackageStates().get(packageName);
    }

    private final boolean isUidInstantApp(com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot $this$isUidInstantApp, int uid) {
        android.content.pm.PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            packageManagerInternal = null;
        }
        return packageManagerInternal.getInstantAppPackageName(uid) != null;
    }

    private final boolean isPackageVisibleToUid(com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot $this$isPackageVisibleToUid, java.lang.String packageName, int uid) {
        return isPackageVisibleToUid($this$isPackageVisibleToUid, packageName, android.os.UserHandle.getUserId(uid), uid);
    }

    private final boolean isPackageVisibleToUid(com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot $this$isPackageVisibleToUid, java.lang.String packageName, int userId, int uid) {
        com.android.server.pm.PackageManagerLocal.FilteredSnapshot it = filtered($this$isPackageVisibleToUid, uid, userId);
        try {
            boolean z = it.getPackageState(packageName) != null;
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
            return z;
        } finally {
        }
    }

    private final com.android.server.pm.PackageManagerLocal.FilteredSnapshot filtered(com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot $this$filtered, int callingUid, int userId) {
        return $this$filtered.filtered(callingUid, android.os.UserHandle.of(userId));
    }

    private final void enforceCallingOrSelfCrossUserPermission(int userId, boolean enforceFullPermission, boolean enforceShellRestriction, java.lang.String message) {
        java.lang.String permissionName;
        if (!(userId >= 0)) {
            throw new java.lang.IllegalArgumentException(("userId " + userId + " is invalid").toString());
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingUserId = android.os.UserHandle.getUserId(callingUid);
        if (userId != callingUserId) {
            if (enforceFullPermission) {
                permissionName = "android.permission.INTERACT_ACROSS_USERS_FULL";
            } else {
                permissionName = "android.permission.INTERACT_ACROSS_USERS";
            }
            if (this.context.checkCallingOrSelfPermission(permissionName) != 0) {
                java.lang.StringBuilder $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24199 = new java.lang.StringBuilder();
                if (message != null) {
                    $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24199.append(message);
                    $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24199.append(": ");
                }
                $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24199.append("Neither user ");
                $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24199.append(callingUid);
                $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24199.append(" nor current process has ");
                $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24199.append(permissionName);
                $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24199.append(" to access user ");
                $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24199.append(userId);
                java.lang.String exceptionMessage = $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24199.toString();
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(exceptionMessage, "toString(...)");
                throw new java.lang.SecurityException(exceptionMessage);
            }
        }
        if (enforceShellRestriction && isShellUid(callingUid)) {
            com.android.server.pm.UserManagerInternal userManagerInternal = this.userManagerInternal;
            if (userManagerInternal == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerInternal");
                userManagerInternal = null;
            }
            boolean isShellRestricted = userManagerInternal.hasUserRestriction("no_debugging_features", userId);
            if (isShellRestricted) {
                java.lang.StringBuilder $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24200 = new java.lang.StringBuilder();
                if (message != null) {
                    $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24200.append(message);
                    $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24200.append(": ");
                }
                $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24200.append("Shell is disallowed to access user ");
                $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24200.append(userId);
                java.lang.String exceptionMessage2 = $this$enforceCallingOrSelfCrossUserPermission_u24lambda_u24200.toString();
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(exceptionMessage2, "toString(...)");
                throw new java.lang.SecurityException(exceptionMessage2);
            }
        }
    }

    private final void enforceCallingOrSelfAnyPermission(java.lang.String message, java.lang.String... permissionNames) {
        int length = permissionNames.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            java.lang.String permissionName = this.context.checkCallingOrSelfPermission(permissionNames[i]) == 0 ? 1 : null;
            if (permissionName != null) {
                z = true;
                break;
            }
            i++;
        }
        boolean hasAnyPermission = z;
        if (!hasAnyPermission) {
            java.lang.StringBuilder $this$enforceCallingOrSelfAnyPermission_u24lambda_u24202 = new java.lang.StringBuilder();
            if (message != null) {
                $this$enforceCallingOrSelfAnyPermission_u24lambda_u24202.append(message);
                $this$enforceCallingOrSelfAnyPermission_u24lambda_u24202.append(": ");
            }
            $this$enforceCallingOrSelfAnyPermission_u24lambda_u24202.append("Neither user ");
            $this$enforceCallingOrSelfAnyPermission_u24lambda_u24202.append(android.os.Binder.getCallingUid());
            $this$enforceCallingOrSelfAnyPermission_u24lambda_u24202.append(" nor current process has any of ");
            com.android.server.permission.jarjar.kotlin.collections.ArraysKt.joinTo$default(permissionNames, $this$enforceCallingOrSelfAnyPermission_u24lambda_u24202, ", ", null, null, 0, null, null, 124, null);
            java.lang.String exceptionMessage = $this$enforceCallingOrSelfAnyPermission_u24lambda_u24202.toString();
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(exceptionMessage, "toString(...)");
            throw new java.lang.SecurityException(exceptionMessage);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: PermissionService.kt */
    final class OnPermissionFlagsChangedListener implements com.android.server.permission.access.permission.AppIdPermissionPolicy.OnPermissionFlagsChangedListener, com.android.server.permission.access.permission.DevicePermissionPolicy.OnDevicePermissionFlagsChangedListener {
        private boolean isKillRuntimePermissionRevokedUidsSkipped;
        private boolean isPermissionFlagsChanged;

        @org.jetbrains.annotations.NotNull
        private final com.android.server.permission.access.immutable.MutableIntMap<java.util.Set<java.lang.String>> runtimePermissionChangedUidDevices = new com.android.server.permission.access.immutable.MutableIntMap<>(null, 1, null);

        @org.jetbrains.annotations.NotNull
        private final android.util.SparseBooleanArray runtimePermissionRevokedUids = new android.util.SparseBooleanArray();

        @org.jetbrains.annotations.NotNull
        private final com.android.server.permission.access.immutable.MutableIntSet gidsChangedUids = new com.android.server.permission.access.immutable.MutableIntSet(null, 1, null);

        @org.jetbrains.annotations.NotNull
        private final android.util.ArraySet<java.lang.String> killRuntimePermissionRevokedUidsReasons = new android.util.ArraySet<>();

        public OnPermissionFlagsChangedListener() {
        }

        public final void skipKillRuntimePermissionRevokedUids(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$skipKillRuntimePermissionRevokedUids) {
            this.isKillRuntimePermissionRevokedUidsSkipped = true;
        }

        public final void addKillRuntimePermissionRevokedUidsReason(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$addKillRuntimePermissionRevokedUidsReason, @org.jetbrains.annotations.NotNull java.lang.String reason) {
            android.util.ArraySet $this$plusAssign$iv = this.killRuntimePermissionRevokedUidsReasons;
            $this$plusAssign$iv.add(reason);
        }

        @Override // com.android.server.permission.access.permission.AppIdPermissionPolicy.OnPermissionFlagsChangedListener
        public void onPermissionFlagsChanged(int appId, int userId, @org.jetbrains.annotations.NotNull java.lang.String permissionName, int oldFlags, int newFlags) {
            onDevicePermissionFlagsChanged(appId, userId, "default:0", permissionName, oldFlags, newFlags);
        }

        @Override // com.android.server.permission.access.permission.DevicePermissionPolicy.OnDevicePermissionFlagsChangedListener
        public void onDevicePermissionFlagsChanged(int appId, int userId, @org.jetbrains.annotations.NotNull java.lang.String deviceId, @org.jetbrains.annotations.NotNull java.lang.String permissionName, int oldFlags, int newFlags) {
            this.isPermissionFlagsChanged = true;
            int uid = android.os.UserHandle.getUid(userId, appId);
            com.android.server.permission.access.AccessCheckingService this_$iv = com.android.server.permission.access.permission.PermissionService.this.service;
            com.android.server.permission.access.permission.PermissionService permissionService = com.android.server.permission.access.permission.PermissionService.this;
            com.android.server.permission.access.AccessState accessState = this_$iv.state;
            if (accessState == null) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                accessState = null;
            }
            com.android.server.permission.access.GetStateScope $this$onDevicePermissionFlagsChanged_u24lambda_u241 = new com.android.server.permission.access.GetStateScope(accessState);
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$onDevicePermissionFlagsChanged_u24lambda_u241_u24lambda_u240 = permissionService.policy;
            com.android.server.permission.access.permission.Permission permission = $this$onDevicePermissionFlagsChanged_u24lambda_u241_u24lambda_u240.getPermissions($this$onDevicePermissionFlagsChanged_u24lambda_u241).get(permissionName);
            if (permission == null) {
                return;
            }
            boolean wasPermissionGranted = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isPermissionGranted(oldFlags);
            boolean isPermissionGranted = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isPermissionGranted(newFlags);
            com.android.server.permission.access.permission.Permission this_$iv2 = permission.getPermissionInfo().getProtection() == 1 ? 1 : null;
            if (this_$iv2 != null) {
                if (wasPermissionGranted && !isPermissionGranted) {
                    android.util.SparseBooleanArray $this$set$iv = this.runtimePermissionRevokedUids;
                    boolean value$iv = com.android.server.permission.access.permission.PermissionService.NOTIFICATIONS_PERMISSIONS.contains(permissionName) && this.runtimePermissionRevokedUids.get(uid, true);
                    $this$set$iv.put(uid, value$iv);
                }
                com.android.server.permission.access.immutable.MutableIntMap $this$getOrPut$iv = this.runtimePermissionChangedUidDevices;
                java.util.Set<java.lang.String> set = $this$getOrPut$iv.get(uid);
                if (set == null) {
                    set = new java.util.LinkedHashSet();
                    $this$getOrPut$iv.put(uid, set);
                }
                set.add(deviceId);
            }
            int $i$f$getProtection = permission.getGids().length == 0 ? 1 : 0;
            if ((1 ^ $i$f$getProtection) == 0 || wasPermissionGranted || !isPermissionGranted) {
                return;
            }
            com.android.server.permission.access.immutable.IntSetExtensionsKt.plusAssign(this.gidsChangedUids, uid);
        }

        @Override // com.android.server.permission.access.permission.AppIdPermissionPolicy.OnPermissionFlagsChangedListener, com.android.server.permission.access.permission.DevicePermissionPolicy.OnDevicePermissionFlagsChangedListener
        public void onStateMutated() {
            final java.lang.String reason;
            android.os.Handler handler;
            java.lang.String joinToString$default;
            com.android.server.permission.access.permission.PermissionService.OnPermissionsChangeListeners onPermissionsChangeListeners;
            if (this.isPermissionFlagsChanged) {
                android.content.pm.PackageManager.invalidatePackageInfoCache();
                this.isPermissionFlagsChanged = false;
            }
            com.android.server.permission.access.immutable.IntMap $this$forEachIndexed$iv = this.runtimePermissionChangedUidDevices;
            com.android.server.permission.access.permission.PermissionService permissionService = com.android.server.permission.access.permission.PermissionService.this;
            int size = $this$forEachIndexed$iv.getSize();
            for (int index$iv = 0; index$iv < size; index$iv++) {
                int uid = $this$forEachIndexed$iv.keyAt(index$iv);
                java.util.Set<java.lang.String> persistentDeviceIds = $this$forEachIndexed$iv.valueAt(index$iv);
                for (java.lang.Object element$iv : persistentDeviceIds) {
                    java.lang.String deviceId = (java.lang.String) element$iv;
                    com.android.server.permission.access.permission.PermissionService.OnPermissionsChangeListeners onPermissionsChangeListeners2 = permissionService.onPermissionsChangeListeners;
                    if (onPermissionsChangeListeners2 == null) {
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("onPermissionsChangeListeners");
                        onPermissionsChangeListeners = null;
                    } else {
                        onPermissionsChangeListeners = onPermissionsChangeListeners2;
                    }
                    onPermissionsChangeListeners.onPermissionsChanged(uid, deviceId);
                }
            }
            this.runtimePermissionChangedUidDevices.clear();
            if (!this.isKillRuntimePermissionRevokedUidsSkipped) {
                if (!this.killRuntimePermissionRevokedUidsReasons.isEmpty()) {
                    joinToString$default = com.android.server.permission.jarjar.kotlin.collections.CollectionsKt___CollectionsKt.joinToString$default(this.killRuntimePermissionRevokedUidsReasons, ", ", null, null, 0, null, null, 62, null);
                    reason = joinToString$default;
                } else {
                    reason = "permissions revoked";
                }
                android.util.SparseBooleanArray $this$forEachIndexed$iv2 = this.runtimePermissionRevokedUids;
                final com.android.server.permission.access.permission.PermissionService permissionService2 = com.android.server.permission.access.permission.PermissionService.this;
                int size2 = $this$forEachIndexed$iv2.size();
                int index$iv2 = 0;
                while (index$iv2 < size2) {
                    final int uid2 = $this$forEachIndexed$iv2.keyAt(index$iv2);
                    final boolean areOnlyNotificationsPermissionsRevoked = $this$forEachIndexed$iv2.valueAt(index$iv2);
                    android.os.Handler handler2 = permissionService2.handler;
                    if (handler2 == null) {
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("handler");
                        handler = null;
                    } else {
                        handler = handler2;
                    }
                    handler.post(new java.lang.Runnable() { // from class: com.android.server.permission.access.permission.PermissionService$OnPermissionFlagsChangedListener$onStateMutated$2$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            boolean isAppBackupAndRestoreRunning;
                            if (areOnlyNotificationsPermissionsRevoked) {
                                isAppBackupAndRestoreRunning = this.isAppBackupAndRestoreRunning(uid2);
                                if (isAppBackupAndRestoreRunning) {
                                    return;
                                }
                            }
                            permissionService2.killUid(uid2, reason);
                        }
                    });
                    index$iv2++;
                    $this$forEachIndexed$iv2 = $this$forEachIndexed$iv2;
                }
            }
            this.runtimePermissionRevokedUids.clear();
            com.android.server.permission.access.immutable.IntSet $this$forEachIndexed$iv3 = this.gidsChangedUids;
            final com.android.server.permission.access.permission.PermissionService permissionService3 = com.android.server.permission.access.permission.PermissionService.this;
            int size3 = $this$forEachIndexed$iv3.getSize();
            for (int index$iv3 = 0; index$iv3 < size3; index$iv3++) {
                final int uid3 = $this$forEachIndexed$iv3.elementAt(index$iv3);
                android.os.Handler handler3 = permissionService3.handler;
                if (handler3 == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("handler");
                    handler3 = null;
                }
                handler3.post(new java.lang.Runnable() { // from class: com.android.server.permission.access.permission.PermissionService$OnPermissionFlagsChangedListener$onStateMutated$3$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.permission.access.permission.PermissionService.this.killUid(uid3, "permission grant or revoke changed gids");
                    }
                });
            }
            this.gidsChangedUids.clear();
            this.isKillRuntimePermissionRevokedUidsSkipped = false;
            this.killRuntimePermissionRevokedUidsReasons.clear();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isAppBackupAndRestoreRunning(int uid) {
            if (com.android.server.permission.access.permission.PermissionService.this.checkUidPermission(uid, "android.permission.BACKUP", "default:0") != 0) {
                return false;
            }
            try {
                android.content.ContentResolver contentResolver = com.android.server.permission.access.permission.PermissionService.this.context.getContentResolver();
                int userId = android.os.UserHandle.getUserId(uid);
                boolean isInSetup = android.provider.Settings.Secure.getIntForUser(contentResolver, "user_setup_complete", userId) == 0;
                boolean isInDeferredSetup = android.provider.Settings.Secure.getIntForUser(contentResolver, "user_setup_personalization_state", userId) == 1;
                return isInSetup || isInDeferredSetup;
            } catch (android.provider.Settings.SettingNotFoundException e) {
                android.util.Slog.w(com.android.server.permission.access.permission.PermissionService.LOG_TAG, "Failed to check if the user is in restore: " + e);
                return false;
            }
        }
    }

    /* compiled from: PermissionService.kt */
    private static final class OnPermissionsChangeListeners extends android.os.Handler {

        @org.jetbrains.annotations.NotNull
        public static final com.android.server.permission.access.permission.PermissionService.OnPermissionsChangeListeners.Companion Companion = new com.android.server.permission.access.permission.PermissionService.OnPermissionsChangeListeners.Companion(null);

        @org.jetbrains.annotations.NotNull
        private final android.os.RemoteCallbackList<android.permission.IOnPermissionsChangeListener> listeners;

        public OnPermissionsChangeListeners(@org.jetbrains.annotations.NotNull android.os.Looper looper) {
            super(looper);
            this.listeners = new android.os.RemoteCallbackList<>();
        }

        @Override // android.os.Handler
        public void handleMessage(@org.jetbrains.annotations.NotNull android.os.Message msg) {
            if (msg.what == 1) {
                int uid = msg.arg1;
                java.lang.Object obj = msg.obj;
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                java.lang.String deviceId = (java.lang.String) obj;
                handleOnPermissionsChanged(uid, deviceId);
            }
        }

        private final void handleOnPermissionsChanged(final int uid, final java.lang.String deviceId) {
            this.listeners.broadcast(new java.util.function.Consumer() { // from class: com.android.server.permission.access.permission.PermissionService$OnPermissionsChangeListeners$handleOnPermissionsChanged$1
                @Override // java.util.function.Consumer
                public final void accept(android.permission.IOnPermissionsChangeListener listener) {
                    try {
                        listener.onPermissionsChanged(uid, deviceId);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.permission.access.permission.PermissionService.LOG_TAG, "Error when calling OnPermissionsChangeListener", e);
                    }
                }
            });
        }

        public final void addListener(@org.jetbrains.annotations.NotNull android.permission.IOnPermissionsChangeListener listener) {
            this.listeners.register(listener);
        }

        public final void removeListener(@org.jetbrains.annotations.NotNull android.permission.IOnPermissionsChangeListener listener) {
            this.listeners.unregister(listener);
        }

        public final void onPermissionsChanged(int uid, @org.jetbrains.annotations.NotNull java.lang.String deviceId) {
            if (this.listeners.getRegisteredCallbackCount() > 0) {
                obtainMessage(1, uid, 0, deviceId).sendToTarget();
            }
        }

        /* compiled from: PermissionService.kt */
        public static final class Companion {
            public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }
    }

    /* compiled from: PermissionService.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        java.util.Set<java.lang.String> emptySet;
        android.util.ArrayMap $this$FULLER_PERMISSIONS_u24lambda_u24203 = new android.util.ArrayMap();
        $this$FULLER_PERMISSIONS_u24lambda_u24203.put("android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION");
        $this$FULLER_PERMISSIONS_u24lambda_u24203.put("android.permission.INTERACT_ACROSS_USERS", "android.permission.INTERACT_ACROSS_USERS_FULL");
        FULLER_PERMISSIONS = $this$FULLER_PERMISSIONS_u24lambda_u24203;
        NOTIFICATIONS_PERMISSIONS = com.android.server.permission.access.collection.ArraySetExtensionsKt.arraySetOf("android.permission.POST_NOTIFICATIONS");
        BACKUP_TIMEOUT_MILLIS = java.util.concurrent.TimeUnit.SECONDS.toMillis(60L);
        if (android.permission.flags.Flags.deviceAwarePermissionsEnabled()) {
            emptySet = com.android.server.permission.jarjar.kotlin.collections.SetsKt__SetsKt.setOf((java.lang.Object[]) new java.lang.String[]{"android.permission.CAMERA", "android.permission.RECORD_AUDIO"});
        } else {
            emptySet = com.android.server.permission.jarjar.kotlin.collections.SetsKt__SetsKt.emptySet();
        }
        DEVICE_AWARE_PERMISSIONS = emptySet;
    }
}
