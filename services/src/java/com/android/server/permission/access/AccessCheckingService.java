package com.android.server.permission.access;

/* compiled from: AccessCheckingService.kt */
@com.android.internal.annotations.Keep
/* loaded from: classes2.dex */
public final class AccessCheckingService extends com.android.server.SystemService {
    private com.android.server.permission.access.appop.AppOpService appOpService;
    private android.content.pm.PackageManagerInternal packageManagerInternal;
    private com.android.server.pm.PackageManagerLocal packageManagerLocal;
    private com.android.server.permission.access.permission.PermissionService permissionService;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.AccessPersistence persistence;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.AccessPolicy policy;
    private volatile com.android.server.permission.access.AccessState state;

    @org.jetbrains.annotations.NotNull
    private final java.lang.Object stateLock;
    private com.android.server.SystemConfig systemConfig;
    private com.android.server.pm.UserManagerService userManagerService;

    public AccessCheckingService(@org.jetbrains.annotations.NotNull android.content.Context context) {
        super(context);
        this.stateLock = new java.lang.Object();
        this.policy = new com.android.server.permission.access.AccessPolicy();
        this.persistence = new com.android.server.permission.access.AccessPersistence(this.policy);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        this.appOpService = new com.android.server.permission.access.appop.AppOpService(this);
        this.permissionService = new com.android.server.permission.access.permission.PermissionService(this);
        com.android.server.permission.access.appop.AppOpService appOpService = this.appOpService;
        com.android.server.permission.access.permission.PermissionService permissionService = null;
        if (appOpService == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("appOpService");
            appOpService = null;
        }
        com.android.server.LocalServices.addService(com.android.server.appop.AppOpsCheckingServiceInterface.class, appOpService);
        com.android.server.permission.access.permission.PermissionService permissionService2 = this.permissionService;
        if (permissionService2 == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("permissionService");
        } else {
            permissionService = permissionService2;
        }
        com.android.server.LocalServices.addService(com.android.server.pm.permission.PermissionManagerServiceInterface.class, permissionService);
    }

    public final void initialize() {
        com.android.server.permission.access.permission.PermissionService permissionService;
        this.packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.packageManagerLocal = (com.android.server.pm.PackageManagerLocal) com.android.server.LocalManagerRegistry.getManagerOrThrow(com.android.server.pm.PackageManagerLocal.class);
        this.userManagerService = com.android.server.pm.UserManagerService.getInstance();
        this.systemConfig = com.android.server.SystemConfig.getInstance();
        com.android.server.pm.UserManagerService userManagerService = this.userManagerService;
        if (userManagerService == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("userManagerService");
            userManagerService = null;
        }
        com.android.server.permission.access.immutable.MutableIntSet userIds = com.android.server.permission.access.immutable.IntSetExtensionsKt.MutableIntSet(userManagerService.getUserIdsIncludingPreCreated());
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.permission.jarjar.kotlin.Pair<java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>, java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>> allPackageStates = getAllPackageStates(packageManagerLocal);
        java.util.Map packageStates = allPackageStates.component1();
        java.util.Map disabledSystemPackageStates = allPackageStates.component2();
        android.content.pm.PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            packageManagerInternal = null;
        }
        com.android.server.permission.access.immutable.IntMap knownPackages = getKnownPackages(packageManagerInternal);
        com.android.server.SystemConfig systemConfig = this.systemConfig;
        if (systemConfig == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
            systemConfig = null;
        }
        boolean isLeanback = isLeanback(systemConfig);
        com.android.server.SystemConfig systemConfig2 = this.systemConfig;
        if (systemConfig2 == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
            systemConfig2 = null;
        }
        android.util.ArrayMap configPermissions = systemConfig2.getPermissions();
        com.android.server.SystemConfig systemConfig3 = this.systemConfig;
        if (systemConfig3 == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
            systemConfig3 = null;
        }
        com.android.server.permission.access.immutable.IndexedListSet privilegedPermissionAllowlistPackages = getPrivilegedPermissionAllowlistPackages(systemConfig3);
        com.android.server.SystemConfig systemConfig4 = this.systemConfig;
        if (systemConfig4 == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
            systemConfig4 = null;
        }
        com.android.server.pm.permission.PermissionAllowlist permissionAllowlist = systemConfig4.getPermissionAllowlist();
        com.android.server.SystemConfig systemConfig5 = this.systemConfig;
        if (systemConfig5 == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("systemConfig");
            systemConfig5 = null;
        }
        com.android.server.permission.access.immutable.IndexedMap implicitToSourcePermissions = getImplicitToSourcePermissions(systemConfig5);
        com.android.server.permission.access.MutableAccessState state = new com.android.server.permission.access.MutableAccessState();
        this.policy.initialize(state, userIds, packageStates, disabledSystemPackageStates, knownPackages, isLeanback, configPermissions, privilegedPermissionAllowlistPackages, permissionAllowlist, implicitToSourcePermissions);
        this.persistence.initialize();
        this.persistence.read(state);
        this.state = state;
        synchronized (this.stateLock) {
            try {
                com.android.server.permission.access.AccessState oldState$iv = this.state;
                if (oldState$iv == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    oldState$iv = null;
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$initialize_u24lambda_u241 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.AccessPolicy $this$initialize_u24lambda_u241_u24lambda_u240 = this.policy;
                $this$initialize_u24lambda_u241_u24lambda_u240.onInitialized($this$initialize_u24lambda_u241);
                this.persistence.write(newState$iv);
                this.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        com.android.server.permission.access.appop.AppOpService appOpService = this.appOpService;
        if (appOpService == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("appOpService");
            appOpService = null;
        }
        appOpService.initialize();
        com.android.server.permission.access.permission.PermissionService permissionService2 = this.permissionService;
        if (permissionService2 == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("permissionService");
            permissionService = null;
        } else {
            permissionService = permissionService2;
        }
        permissionService.initialize();
    }

    private final boolean isLeanback(com.android.server.SystemConfig $this$isLeanback) {
        return $this$isLeanback.getAvailableFeatures().containsKey("android.software.leanback");
    }

    private final com.android.server.permission.access.immutable.IndexedListSet<java.lang.String> getPrivilegedPermissionAllowlistPackages(com.android.server.SystemConfig $this$privilegedPermissionAllowlistPackages) {
        com.android.server.permission.access.immutable.MutableIndexedListSet $this$_get_privilegedPermissionAllowlistPackages__u24lambda_u242 = new com.android.server.permission.access.immutable.MutableIndexedListSet(null, 1, null);
        $this$_get_privilegedPermissionAllowlistPackages__u24lambda_u242.add(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
        if ($this$privilegedPermissionAllowlistPackages.getAvailableFeatures().containsKey("android.hardware.type.automotive")) {
            java.lang.String carServicePackage = android.os.SystemProperties.get("ro.android.car.carservice.package");
            if (carServicePackage.length() > 0) {
                $this$_get_privilegedPermissionAllowlistPackages__u24lambda_u242.add(carServicePackage);
            }
        }
        return $this$_get_privilegedPermissionAllowlistPackages__u24lambda_u242;
    }

    private final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>> getImplicitToSourcePermissions(com.android.server.SystemConfig systemConfig) {
        com.android.server.permission.access.immutable.MutableIndexedMap mutableIndexedMap;
        int i;
        java.util.ArrayList<android.permission.PermissionManager.SplitPermissionInfo> arrayList;
        boolean z;
        boolean z2 = true;
        com.android.server.permission.access.immutable.MutableIndexedMap mutableIndexedMap2 = new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null);
        com.android.server.permission.access.immutable.MutableIndexedMap mutableIndexedMap3 = mutableIndexedMap2;
        int i2 = 0;
        java.util.ArrayList<android.permission.PermissionManager.SplitPermissionInfo> splitPermissions = systemConfig.getSplitPermissions();
        for (android.permission.PermissionManager.SplitPermissionInfo splitPermissionInfo : splitPermissions) {
            java.lang.String splitPermission = splitPermissionInfo.getSplitPermission();
            for (java.lang.String str : splitPermissionInfo.getNewPermissions()) {
                com.android.server.permission.access.immutable.MutableIndexedMap mutableIndexedMap4 = mutableIndexedMap3;
                java.lang.Object obj = mutableIndexedMap4.get(str);
                if (obj != null) {
                    mutableIndexedMap = mutableIndexedMap3;
                    i = i2;
                    arrayList = splitPermissions;
                    z = true;
                } else {
                    mutableIndexedMap = mutableIndexedMap3;
                    i = i2;
                    arrayList = splitPermissions;
                    z = true;
                    obj = new com.android.server.permission.access.immutable.MutableIndexedListSet(null, 1, null);
                    mutableIndexedMap4.put(str, obj);
                }
                ((com.android.server.permission.access.immutable.MutableIndexedListSet) obj).add(splitPermission);
                z2 = z;
                splitPermissions = arrayList;
                mutableIndexedMap3 = mutableIndexedMap;
                i2 = i;
            }
            byte b = z2 ? 1 : 0;
            splitPermissions = splitPermissions;
        }
        return mutableIndexedMap2;
    }

    public final void onUserAdded$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(int userId) {
        synchronized (this.stateLock) {
            try {
                com.android.server.permission.access.AccessState oldState$iv = this.state;
                if (oldState$iv == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    oldState$iv = null;
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$onUserAdded_u24lambda_u248 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.AccessPolicy $this$onUserAdded_u24lambda_u248_u24lambda_u247 = this.policy;
                $this$onUserAdded_u24lambda_u248_u24lambda_u247.onUserAdded($this$onUserAdded_u24lambda_u248, userId);
                this.persistence.write(newState$iv);
                this.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public final void onUserRemoved$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(int userId) {
        synchronized (this.stateLock) {
            try {
                com.android.server.permission.access.AccessState oldState$iv = this.state;
                if (oldState$iv == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    oldState$iv = null;
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$onUserRemoved_u24lambda_u2410 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.AccessPolicy $this$onUserRemoved_u24lambda_u2410_u24lambda_u249 = this.policy;
                $this$onUserRemoved_u24lambda_u2410_u24lambda_u249.onUserRemoved($this$onUserRemoved_u24lambda_u2410, userId);
                this.persistence.write(newState$iv);
                this.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public final void onStorageVolumeMounted$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(@org.jetbrains.annotations.Nullable java.lang.String volumeUuid, @org.jetbrains.annotations.NotNull java.util.List<java.lang.String> list, boolean isSystemUpdated) {
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        com.android.server.permission.access.AccessState oldState$iv = null;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.permission.jarjar.kotlin.Pair<java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>, java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>> allPackageStates = getAllPackageStates(packageManagerLocal);
        java.util.Map packageStates = allPackageStates.component1();
        java.util.Map disabledSystemPackageStates = allPackageStates.component2();
        android.content.pm.PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            packageManagerInternal = null;
        }
        com.android.server.permission.access.immutable.IntMap knownPackages = getKnownPackages(packageManagerInternal);
        synchronized (this.stateLock) {
            try {
                com.android.server.permission.access.AccessState accessState = this.state;
                if (accessState == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                } else {
                    oldState$iv = accessState;
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$onStorageVolumeMounted_u24lambda_u2412 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.AccessPolicy $this$onStorageVolumeMounted_u24lambda_u2412_u24lambda_u2411 = this.policy;
                $this$onStorageVolumeMounted_u24lambda_u2412_u24lambda_u2411.onStorageVolumeMounted($this$onStorageVolumeMounted_u24lambda_u2412, packageStates, disabledSystemPackageStates, knownPackages, volumeUuid, list, isSystemUpdated);
                this.persistence.write(newState$iv);
                this.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public final void onPackageAdded$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(@org.jetbrains.annotations.NotNull java.lang.String packageName) {
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        com.android.server.permission.access.AccessState oldState$iv = null;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.permission.jarjar.kotlin.Pair<java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>, java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>> allPackageStates = getAllPackageStates(packageManagerLocal);
        java.util.Map packageStates = allPackageStates.component1();
        java.util.Map disabledSystemPackageStates = allPackageStates.component2();
        android.content.pm.PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            packageManagerInternal = null;
        }
        com.android.server.permission.access.immutable.IntMap knownPackages = getKnownPackages(packageManagerInternal);
        synchronized (this.stateLock) {
            try {
                com.android.server.permission.access.AccessState accessState = this.state;
                if (accessState == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                } else {
                    oldState$iv = accessState;
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$onPackageAdded_u24lambda_u2414 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.AccessPolicy $this$onPackageAdded_u24lambda_u2414_u24lambda_u2413 = this.policy;
                $this$onPackageAdded_u24lambda_u2414_u24lambda_u2413.onPackageAdded($this$onPackageAdded_u24lambda_u2414, packageStates, disabledSystemPackageStates, knownPackages, packageName);
                this.persistence.write(newState$iv);
                this.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public final void onPackageRemoved$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(@org.jetbrains.annotations.NotNull java.lang.String packageName, int appId) {
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        com.android.server.permission.access.AccessState oldState$iv = null;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.permission.jarjar.kotlin.Pair<java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>, java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>> allPackageStates = getAllPackageStates(packageManagerLocal);
        java.util.Map packageStates = allPackageStates.component1();
        java.util.Map disabledSystemPackageStates = allPackageStates.component2();
        android.content.pm.PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            packageManagerInternal = null;
        }
        com.android.server.permission.access.immutable.IntMap knownPackages = getKnownPackages(packageManagerInternal);
        synchronized (this.stateLock) {
            try {
                com.android.server.permission.access.AccessState accessState = this.state;
                if (accessState == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                } else {
                    oldState$iv = accessState;
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$onPackageRemoved_u24lambda_u2416 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.AccessPolicy $this$onPackageRemoved_u24lambda_u2416_u24lambda_u2415 = this.policy;
                $this$onPackageRemoved_u24lambda_u2416_u24lambda_u2415.onPackageRemoved($this$onPackageRemoved_u24lambda_u2416, packageStates, disabledSystemPackageStates, knownPackages, packageName, appId);
                this.persistence.write(newState$iv);
                this.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public final void onPackageInstalled$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(@org.jetbrains.annotations.NotNull java.lang.String packageName, int userId) {
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        com.android.server.permission.access.AccessState oldState$iv = null;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.permission.jarjar.kotlin.Pair<java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>, java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>> allPackageStates = getAllPackageStates(packageManagerLocal);
        java.util.Map packageStates = allPackageStates.component1();
        java.util.Map disabledSystemPackageStates = allPackageStates.component2();
        android.content.pm.PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            packageManagerInternal = null;
        }
        com.android.server.permission.access.immutable.IntMap knownPackages = getKnownPackages(packageManagerInternal);
        synchronized (this.stateLock) {
            try {
                com.android.server.permission.access.AccessState accessState = this.state;
                if (accessState == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                } else {
                    oldState$iv = accessState;
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$onPackageInstalled_u24lambda_u2418 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.AccessPolicy $this$onPackageInstalled_u24lambda_u2418_u24lambda_u2417 = this.policy;
                $this$onPackageInstalled_u24lambda_u2418_u24lambda_u2417.onPackageInstalled($this$onPackageInstalled_u24lambda_u2418, packageStates, disabledSystemPackageStates, knownPackages, packageName, userId);
                this.persistence.write(newState$iv);
                this.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public final void onPackageUninstalled$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(@org.jetbrains.annotations.NotNull java.lang.String packageName, int appId, int userId) {
        com.android.server.pm.PackageManagerLocal packageManagerLocal = this.packageManagerLocal;
        com.android.server.permission.access.AccessState oldState$iv = null;
        if (packageManagerLocal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerLocal");
            packageManagerLocal = null;
        }
        com.android.server.permission.jarjar.kotlin.Pair<java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>, java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>> allPackageStates = getAllPackageStates(packageManagerLocal);
        java.util.Map packageStates = allPackageStates.component1();
        java.util.Map disabledSystemPackageStates = allPackageStates.component2();
        android.content.pm.PackageManagerInternal packageManagerInternal = this.packageManagerInternal;
        if (packageManagerInternal == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("packageManagerInternal");
            packageManagerInternal = null;
        }
        com.android.server.permission.access.immutable.IntMap knownPackages = getKnownPackages(packageManagerInternal);
        synchronized (this.stateLock) {
            try {
                com.android.server.permission.access.AccessState accessState = this.state;
                if (accessState == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                } else {
                    oldState$iv = accessState;
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$onPackageUninstalled_u24lambda_u2420 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.AccessPolicy $this$onPackageUninstalled_u24lambda_u2420_u24lambda_u2419 = this.policy;
                $this$onPackageUninstalled_u24lambda_u2420_u24lambda_u2419.onPackageUninstalled($this$onPackageUninstalled_u24lambda_u2420, packageStates, disabledSystemPackageStates, knownPackages, packageName, appId, userId);
                this.persistence.write(newState$iv);
                this.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public final void onSystemReady$frameworks__base__services__permission__android_common__services_permission_pre_jarjar() {
        synchronized (this.stateLock) {
            try {
                com.android.server.permission.access.AccessState oldState$iv = this.state;
                if (oldState$iv == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    oldState$iv = null;
                }
                com.android.server.permission.access.MutableAccessState newState$iv = oldState$iv.toMutable();
                com.android.server.permission.access.MutateStateScope $this$onSystemReady_u24lambda_u2422 = new com.android.server.permission.access.MutateStateScope(oldState$iv, newState$iv);
                com.android.server.permission.access.AccessPolicy $this$onSystemReady_u24lambda_u2422_u24lambda_u2421 = this.policy;
                $this$onSystemReady_u24lambda_u2422_u24lambda_u2421.onSystemReady($this$onSystemReady_u24lambda_u2422);
                this.persistence.write(newState$iv);
                this.state = newState$iv;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv = this.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427$iv.onStateMutated(new com.android.server.permission.access.GetStateScope(newState$iv));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private final com.android.server.permission.jarjar.kotlin.Pair<java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>, java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>> getAllPackageStates(com.android.server.pm.PackageManagerLocal $this$allPackageStates) {
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot it = $this$allPackageStates.withUnfilteredSnapshot();
        try {
            com.android.server.permission.jarjar.kotlin.Pair<java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>, java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState>> pair = com.android.server.permission.jarjar.kotlin.TuplesKt.to(it.getPackageStates(), it.getDisabledSystemPackageStates());
            com.android.server.permission.jarjar.kotlin.jdk7.AutoCloseableKt.closeFinally(it, null);
            return pair;
        } finally {
        }
    }

    private final com.android.server.permission.access.immutable.IntMap<java.lang.String[]> getKnownPackages(android.content.pm.PackageManagerInternal $this$knownPackages) {
        com.android.server.permission.access.immutable.MutableIntMap $this$_get_knownPackages__u24lambda_u2424 = new com.android.server.permission.access.immutable.MutableIntMap(null, 1, null);
        com.android.server.permission.access.immutable.IntMapExtensionsKt.set($this$_get_knownPackages__u24lambda_u2424, 2, $this$knownPackages.getKnownPackageNames(2, 0));
        com.android.server.permission.access.immutable.IntMapExtensionsKt.set($this$_get_knownPackages__u24lambda_u2424, 7, $this$knownPackages.getKnownPackageNames(7, 0));
        com.android.server.permission.access.immutable.IntMapExtensionsKt.set($this$_get_knownPackages__u24lambda_u2424, 4, $this$knownPackages.getKnownPackageNames(4, 0));
        com.android.server.permission.access.immutable.IntMapExtensionsKt.set($this$_get_knownPackages__u24lambda_u2424, 1, $this$knownPackages.getKnownPackageNames(1, 0));
        com.android.server.permission.access.immutable.IntMapExtensionsKt.set($this$_get_knownPackages__u24lambda_u2424, 6, $this$knownPackages.getKnownPackageNames(6, 0));
        com.android.server.permission.access.immutable.IntMapExtensionsKt.set($this$_get_knownPackages__u24lambda_u2424, 10, $this$knownPackages.getKnownPackageNames(10, 0));
        com.android.server.permission.access.immutable.IntMapExtensionsKt.set($this$_get_knownPackages__u24lambda_u2424, 11, $this$knownPackages.getKnownPackageNames(11, 0));
        com.android.server.permission.access.immutable.IntMapExtensionsKt.set($this$_get_knownPackages__u24lambda_u2424, 12, $this$knownPackages.getKnownPackageNames(12, 0));
        com.android.server.permission.access.immutable.IntMapExtensionsKt.set($this$_get_knownPackages__u24lambda_u2424, 15, $this$knownPackages.getKnownPackageNames(15, 0));
        com.android.server.permission.access.immutable.IntMapExtensionsKt.set($this$_get_knownPackages__u24lambda_u2424, 16, $this$knownPackages.getKnownPackageNames(16, 0));
        com.android.server.permission.access.immutable.IntMapExtensionsKt.set($this$_get_knownPackages__u24lambda_u2424, 17, $this$knownPackages.getKnownPackageNames(17, 0));
        return $this$_get_knownPackages__u24lambda_u2424;
    }

    public final <T> T getState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(@org.jetbrains.annotations.NotNull com.android.server.permission.jarjar.kotlin.jvm.functions.Function1<? super com.android.server.permission.access.GetStateScope, ? extends T> function1) {
        com.android.server.permission.access.AccessState accessState = this.state;
        if (accessState == null) {
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
            accessState = null;
        }
        return function1.invoke(new com.android.server.permission.access.GetStateScope(accessState));
    }

    public final void mutateState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(@org.jetbrains.annotations.NotNull com.android.server.permission.jarjar.kotlin.jvm.functions.Function1<? super com.android.server.permission.access.MutateStateScope, com.android.server.permission.jarjar.kotlin.Unit> function1) {
        synchronized (this.stateLock) {
            try {
                com.android.server.permission.access.AccessState oldState = this.state;
                if (oldState == null) {
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException("state");
                    oldState = null;
                }
                com.android.server.permission.access.MutableAccessState newState = oldState.toMutable();
                function1.invoke(new com.android.server.permission.access.MutateStateScope(oldState, newState));
                this.persistence.write(newState);
                this.state = newState;
                com.android.server.permission.access.AccessPolicy $this$mutateState_u24lambda_u2428_u24lambda_u2427 = this.policy;
                $this$mutateState_u24lambda_u2428_u24lambda_u2427.onStateMutated(new com.android.server.permission.access.GetStateScope(newState));
                com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
                com.android.server.permission.jarjar.kotlin.jvm.internal.InlineMarker.finallyStart(1);
            } catch (java.lang.Throwable th) {
                com.android.server.permission.jarjar.kotlin.jvm.internal.InlineMarker.finallyStart(1);
                com.android.server.permission.jarjar.kotlin.jvm.internal.InlineMarker.finallyEnd(1);
                throw th;
            }
        }
        com.android.server.permission.jarjar.kotlin.jvm.internal.InlineMarker.finallyEnd(1);
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.SchemePolicy getSchemePolicy$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(@org.jetbrains.annotations.NotNull java.lang.String subjectScheme, @org.jetbrains.annotations.NotNull java.lang.String objectScheme) {
        return this.policy.getSchemePolicy(subjectScheme, objectScheme);
    }
}
