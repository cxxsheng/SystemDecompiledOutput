package com.android.server.permission.access.permission;

/* compiled from: AppIdPermissionUpgrade.kt */
/* loaded from: classes2.dex */
public final class AppIdPermissionUpgrade {

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.permission.AppIdPermissionPolicy policy;

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.permission.AppIdPermissionUpgrade.Companion Companion = new com.android.server.permission.access.permission.AppIdPermissionUpgrade.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.permission.AppIdPermissionUpgrade.class.getSimpleName();

    @org.jetbrains.annotations.NotNull
    private static final com.android.server.permission.access.immutable.IndexedSet<java.lang.String> LEGACY_RESTRICTED_PERMISSIONS = com.android.server.permission.access.immutable.IndexedSetExtensionsKt.indexedSetOf("android.permission.ACCESS_BACKGROUND_LOCATION", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.RECEIVE_WAP_PUSH", "android.permission.RECEIVE_MMS", "android.permission.READ_CELL_BROADCASTS", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG", "android.permission.PROCESS_OUTGOING_CALLS");

    @org.jetbrains.annotations.NotNull
    private static final com.android.server.permission.access.immutable.IndexedSet<java.lang.String> STORAGE_PERMISSIONS = com.android.server.permission.access.immutable.IndexedSetExtensionsKt.indexedSetOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE");

    @org.jetbrains.annotations.NotNull
    private static final com.android.server.permission.access.immutable.IndexedSet<java.lang.String> AURAL_VISUAL_MEDIA_PERMISSIONS = com.android.server.permission.access.immutable.IndexedSetExtensionsKt.indexedSetOf("android.permission.READ_MEDIA_AUDIO", "android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.ACCESS_MEDIA_LOCATION", "android.permission.READ_MEDIA_VISUAL_USER_SELECTED");

    @org.jetbrains.annotations.NotNull
    private static final com.android.server.permission.access.immutable.IndexedSet<java.lang.String> VISUAL_MEDIA_PERMISSIONS = com.android.server.permission.access.immutable.IndexedSetExtensionsKt.indexedSetOf("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.ACCESS_MEDIA_LOCATION");

    public AppIdPermissionUpgrade(@org.jetbrains.annotations.NotNull com.android.server.permission.access.permission.AppIdPermissionPolicy policy) {
        this.policy = policy;
    }

    public final void upgradePackageState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$upgradePackageState, @org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState, int userId, int version) {
        java.lang.String packageName = packageState.getPackageName();
        if (version <= 3) {
            android.util.Slog.v(LOG_TAG, "Allowlisting and upgrading background location permission for package: " + packageName + ", version: " + version + ", user:" + userId);
            allowlistRestrictedPermissions($this$upgradePackageState, packageState, userId);
            upgradeBackgroundLocationPermission($this$upgradePackageState, packageState, userId);
        }
        if (version <= 10) {
            android.util.Slog.v(LOG_TAG, "Upgrading access media location permission for package: " + packageName + ", version: " + version + ", user: " + userId);
            upgradeAccessMediaLocationPermission($this$upgradePackageState, packageState, userId);
        }
        if (version <= 12) {
            android.util.Slog.v(LOG_TAG, "Upgrading scoped media and body sensor permissions for package: " + packageName + ", version: " + version + ", user: " + userId);
            upgradeAuralVisualMediaPermissions($this$upgradePackageState, packageState, userId);
            upgradeBodySensorPermissions($this$upgradePackageState, packageState, userId);
        }
        if (version <= 14) {
            android.util.Slog.v(LOG_TAG, "Upgrading visual media permission for package: " + packageName + ", version: " + version + ", user: " + userId);
            upgradeUserSelectedVisualMediaPermission($this$upgradePackageState, packageState, userId);
        }
    }

    private final void allowlistRestrictedPermissions(com.android.server.permission.access.MutateStateScope $this$allowlistRestrictedPermissions, com.android.server.pm.pkg.PackageState packageState, int userId) {
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
        java.lang.Iterable $this$forEach$iv = androidPackage.getRequestedPermissions();
        for (java.lang.Object element$iv : $this$forEach$iv) {
            java.lang.String permissionName = (java.lang.String) element$iv;
            if (LEGACY_RESTRICTED_PERMISSIONS.contains(permissionName)) {
                com.android.server.permission.access.permission.AppIdPermissionPolicy $this$allowlistRestrictedPermissions_u24lambda_u241_u24lambda_u240 = this.policy;
                $this$allowlistRestrictedPermissions_u24lambda_u241_u24lambda_u240.updatePermissionFlags($this$allowlistRestrictedPermissions, packageState.getAppId(), userId, permissionName, 131072, 131072);
            }
        }
    }

    private final void upgradeBackgroundLocationPermission(com.android.server.permission.access.MutateStateScope $this$upgradeBackgroundLocationPermission, com.android.server.pm.pkg.PackageState packageState, int userId) {
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
        if (androidPackage.getRequestedPermissions().contains("android.permission.ACCESS_BACKGROUND_LOCATION")) {
            int appId = packageState.getAppId();
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$upgradeBackgroundLocationPermission_u24lambda_u242 = this.policy;
            int accessFineLocationFlags = $this$upgradeBackgroundLocationPermission_u24lambda_u242.getPermissionFlags($this$upgradeBackgroundLocationPermission, appId, userId, "android.permission.ACCESS_FINE_LOCATION");
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$upgradeBackgroundLocationPermission_u24lambda_u243 = this.policy;
            int accessCoarseLocationFlags = $this$upgradeBackgroundLocationPermission_u24lambda_u243.getPermissionFlags($this$upgradeBackgroundLocationPermission, appId, userId, "android.permission.ACCESS_COARSE_LOCATION");
            boolean isForegroundLocationGranted = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isAppOpGranted(accessFineLocationFlags) || com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isAppOpGranted(accessCoarseLocationFlags);
            if (isForegroundLocationGranted) {
                grantRuntimePermission($this$upgradeBackgroundLocationPermission, packageState, userId, "android.permission.ACCESS_BACKGROUND_LOCATION");
            }
        }
    }

    private final void upgradeAccessMediaLocationPermission(com.android.server.permission.access.MutateStateScope $this$upgradeAccessMediaLocationPermission, com.android.server.pm.pkg.PackageState packageState, int userId) {
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
        if (androidPackage.getRequestedPermissions().contains("android.permission.ACCESS_MEDIA_LOCATION")) {
            com.android.server.permission.access.permission.AppIdPermissionPolicy $this$upgradeAccessMediaLocationPermission_u24lambda_u244 = this.policy;
            int flags = $this$upgradeAccessMediaLocationPermission_u24lambda_u244.getPermissionFlags($this$upgradeAccessMediaLocationPermission, packageState.getAppId(), userId, "android.permission.READ_EXTERNAL_STORAGE");
            if (com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isAppOpGranted(flags)) {
                grantRuntimePermission($this$upgradeAccessMediaLocationPermission, packageState, userId, "android.permission.ACCESS_MEDIA_LOCATION");
            }
        }
    }

    private final void upgradeAuralVisualMediaPermissions(com.android.server.permission.access.MutateStateScope $this$upgradeAuralVisualMediaPermissions, com.android.server.pm.pkg.PackageState packageState, int userId) {
        boolean z;
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        boolean z2;
        com.android.server.pm.pkg.AndroidPackage androidPackage2 = packageState.getAndroidPackage();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage2);
        if (androidPackage2.getTargetSdkVersion() < 33) {
            return;
        }
        java.util.Set requestedPermissionNames = androidPackage2.getRequestedPermissions();
        com.android.server.permission.access.immutable.IndexedSet $this$anyIndexed$iv = STORAGE_PERMISSIONS;
        int index$iv$iv = 0;
        int size = $this$anyIndexed$iv.getSize();
        while (true) {
            if (index$iv$iv >= size) {
                z = false;
                break;
            }
            java.lang.Object element$iv = $this$anyIndexed$iv.elementAt(index$iv$iv);
            java.lang.String permissionName = (java.lang.String) element$iv;
            if (requestedPermissionNames.contains(permissionName)) {
                com.android.server.permission.access.permission.AppIdPermissionPolicy $this$upgradeAuralVisualMediaPermissions_u24lambda_u246_u24lambda_u245 = this.policy;
                androidPackage = androidPackage2;
                int flags = $this$upgradeAuralVisualMediaPermissions_u24lambda_u246_u24lambda_u245.getPermissionFlags($this$upgradeAuralVisualMediaPermissions, packageState.getAppId(), userId, permissionName);
                z2 = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isAppOpGranted(flags) && com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 32);
            } else {
                androidPackage = androidPackage2;
                z2 = false;
            }
            if (z2) {
                z = true;
                break;
            } else {
                index$iv$iv++;
                androidPackage2 = androidPackage;
            }
        }
        boolean isStorageUserGranted = z;
        if (isStorageUserGranted) {
            com.android.server.permission.access.immutable.IndexedSet $this$forEachIndexed$iv = AURAL_VISUAL_MEDIA_PERMISSIONS;
            int size2 = $this$forEachIndexed$iv.getSize();
            for (int index$iv = 0; index$iv < size2; index$iv++) {
                java.lang.String permissionName2 = $this$forEachIndexed$iv.elementAt(index$iv);
                if (requestedPermissionNames.contains(permissionName2)) {
                    grantRuntimePermission($this$upgradeAuralVisualMediaPermissions, packageState, userId, permissionName2);
                }
            }
        }
    }

    private final void upgradeBodySensorPermissions(com.android.server.permission.access.MutateStateScope $this$upgradeBodySensorPermissions, com.android.server.pm.pkg.PackageState packageState, int userId) {
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
        if (!androidPackage.getRequestedPermissions().contains("android.permission.BODY_SENSORS_BACKGROUND")) {
            return;
        }
        int appId = packageState.getAppId();
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$upgradeBodySensorPermissions_u24lambda_u248 = this.policy;
        int backgroundBodySensorsFlags = $this$upgradeBodySensorPermissions_u24lambda_u248.getPermissionFlags($this$upgradeBodySensorPermissions, appId, userId, "android.permission.BODY_SENSORS_BACKGROUND");
        if (com.android.server.permission.access.util.IntExtensionsKt.hasAnyBit(backgroundBodySensorsFlags, 229376)) {
            return;
        }
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$upgradeBodySensorPermissions_u24lambda_u249 = this.policy;
        $this$upgradeBodySensorPermissions_u24lambda_u249.updatePermissionFlags($this$upgradeBodySensorPermissions, appId, userId, "android.permission.BODY_SENSORS_BACKGROUND", 131072, 131072);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$upgradeBodySensorPermissions_u24lambda_u2410 = this.policy;
        int bodySensorsFlags = $this$upgradeBodySensorPermissions_u24lambda_u2410.getPermissionFlags($this$upgradeBodySensorPermissions, appId, userId, "android.permission.BODY_SENSORS");
        boolean isForegroundBodySensorsGranted = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isAppOpGranted(bodySensorsFlags);
        if (isForegroundBodySensorsGranted) {
            grantRuntimePermission($this$upgradeBodySensorPermissions, packageState, userId, "android.permission.BODY_SENSORS_BACKGROUND");
        }
    }

    private final void upgradeUserSelectedVisualMediaPermission(com.android.server.permission.access.MutateStateScope $this$upgradeUserSelectedVisualMediaPermission, com.android.server.pm.pkg.PackageState packageState, int userId) {
        boolean z;
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        boolean z2;
        com.android.server.pm.pkg.AndroidPackage androidPackage2 = packageState.getAndroidPackage();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage2);
        if (androidPackage2.getTargetSdkVersion() < 33) {
            return;
        }
        java.util.Set requestedPermissionNames = androidPackage2.getRequestedPermissions();
        com.android.server.permission.access.immutable.IndexedSet $this$anyIndexed$iv = VISUAL_MEDIA_PERMISSIONS;
        int index$iv$iv = 0;
        int size = $this$anyIndexed$iv.getSize();
        while (true) {
            if (index$iv$iv >= size) {
                z = false;
                break;
            }
            java.lang.Object element$iv = $this$anyIndexed$iv.elementAt(index$iv$iv);
            java.lang.String permissionName = (java.lang.String) element$iv;
            if (!requestedPermissionNames.contains(permissionName)) {
                androidPackage = androidPackage2;
                z2 = false;
            } else {
                com.android.server.permission.access.permission.AppIdPermissionPolicy $this$upgradeUserSelectedVisualMediaPermission_u24lambda_u2412_u24lambda_u2411 = this.policy;
                androidPackage = androidPackage2;
                int flags = $this$upgradeUserSelectedVisualMediaPermission_u24lambda_u2412_u24lambda_u2411.getPermissionFlags($this$upgradeUserSelectedVisualMediaPermission, packageState.getAppId(), userId, permissionName);
                z2 = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isAppOpGranted(flags) && com.android.server.permission.access.util.IntExtensionsKt.hasBits(flags, 32);
            }
            if (z2) {
                z = true;
                break;
            } else {
                index$iv$iv++;
                androidPackage2 = androidPackage;
            }
        }
        boolean isVisualMediaUserGranted = z;
        if (isVisualMediaUserGranted && requestedPermissionNames.contains("android.permission.READ_MEDIA_VISUAL_USER_SELECTED")) {
            grantRuntimePermission($this$upgradeUserSelectedVisualMediaPermission, packageState, userId, "android.permission.READ_MEDIA_VISUAL_USER_SELECTED");
        }
    }

    private final void grantRuntimePermission(com.android.server.permission.access.MutateStateScope $this$grantRuntimePermission, com.android.server.pm.pkg.PackageState packageState, int userId, java.lang.String permissionName) {
        android.util.Slog.v(LOG_TAG, "Granting runtime permission for package: " + packageState.getPackageName() + ", permission: " + permissionName + ", userId: " + userId);
        com.android.server.permission.access.permission.Permission permission = $this$grantRuntimePermission.getNewState().getSystemState().getPermissions().get(permissionName);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(permission);
        com.android.server.permission.access.permission.Permission permission2 = permission;
        if (packageState.getUserStateOrDefault(userId).isInstantApp() && !com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission2.getPermissionInfo().getProtectionFlags(), 4096)) {
            return;
        }
        int appId = packageState.getAppId();
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$grantRuntimePermission_u24lambda_u2413 = this.policy;
        int flags = $this$grantRuntimePermission_u24lambda_u2413.getPermissionFlags($this$grantRuntimePermission, appId, userId, permissionName);
        if (com.android.server.permission.access.util.IntExtensionsKt.hasAnyBit(flags, com.android.server.SystemService.PHASE_LOCK_SETTINGS_READY)) {
            android.util.Slog.v(LOG_TAG, "Not allowed to grant " + permissionName + " to package " + packageState.getPackageName());
            return;
        }
        int flags2 = com.android.server.permission.access.util.IntExtensionsKt.andInv(flags | 16, 7345152);
        com.android.server.permission.access.permission.AppIdPermissionPolicy $this$grantRuntimePermission_u24lambda_u2414 = this.policy;
        $this$grantRuntimePermission_u24lambda_u2414.setPermissionFlags($this$grantRuntimePermission, appId, userId, permissionName, flags2);
    }

    /* compiled from: AppIdPermissionUpgrade.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
