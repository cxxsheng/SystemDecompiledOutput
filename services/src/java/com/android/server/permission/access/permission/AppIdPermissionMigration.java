package com.android.server.permission.access.permission;

/* compiled from: AppIdPermissionMigration.kt */
/* loaded from: classes2.dex */
public final class AppIdPermissionMigration {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.permission.AppIdPermissionMigration.Companion Companion = new com.android.server.permission.access.permission.AppIdPermissionMigration.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.permission.AppIdPermissionMigration.class.getSimpleName();

    public final void migrateSystemState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state) {
        java.lang.Object service = com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionMigrationHelper.class);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(service);
        com.android.server.pm.permission.PermissionMigrationHelper legacyPermissionsManager = (com.android.server.pm.permission.PermissionMigrationHelper) service;
        if (!legacyPermissionsManager.hasLegacyPermission()) {
            return;
        }
        migratePermissions$default(this, com.android.server.permission.access.MutableAccessState.mutateSystemState$default(state, 0, 1, null).mutatePermissions(), legacyPermissionsManager.getLegacyPermissions(), false, 4, null);
        migratePermissions(com.android.server.permission.access.MutableAccessState.mutateSystemState$default(state, 0, 1, null).mutatePermissionTrees(), legacyPermissionsManager.getLegacyPermissionTrees(), true);
    }

    static /* synthetic */ void migratePermissions$default(com.android.server.permission.access.permission.AppIdPermissionMigration appIdPermissionMigration, com.android.server.permission.access.immutable.MutableIndexedMap mutableIndexedMap, java.util.Map map, boolean z, int i, java.lang.Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        appIdPermissionMigration.migratePermissions(mutableIndexedMap, map, z);
    }

    private final void migratePermissions(com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission> mutableIndexedMap, java.util.Map<java.lang.String, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermission> map, boolean isPermissionTree) {
        for (java.util.Map.Entry element$iv : map.entrySet()) {
            com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermission legacyPermission = element$iv.getValue();
            com.android.server.permission.access.permission.Permission permission = new com.android.server.permission.access.permission.Permission(legacyPermission.getPermissionInfo(), false, legacyPermission.getType(), 0, null, false, 48, null);
            mutableIndexedMap.put(permission.getPermissionInfo().name, permission);
        }
    }

    public final void migrateUserState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
        com.android.server.pm.permission.PermissionMigrationHelper permissionMigrationHelper;
        java.util.Map legacyAppIdPermissionStates;
        com.android.server.permission.access.immutable.MutableIntReferenceMap appIdPermissionFlags;
        java.util.Map $this$forEach$iv;
        int $i$f$forEach;
        java.util.Iterator<java.util.Map.Entry<java.lang.Integer, java.util.Map<java.lang.String, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermissionState>>> it;
        java.util.Map $this$forEach$iv2;
        int $i$f$forEach2;
        java.util.Iterator<java.util.Map.Entry<java.lang.Integer, java.util.Map<java.lang.String, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermissionState>>> it2;
        int i = userId;
        java.lang.Object service = com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionMigrationHelper.class);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(service);
        com.android.server.pm.permission.PermissionMigrationHelper permissionMigrationHelper2 = (com.android.server.pm.permission.PermissionMigrationHelper) service;
        if (!permissionMigrationHelper2.hasLegacyPermissionState(i)) {
            return;
        }
        java.util.Map legacyAppIdPermissionStates2 = permissionMigrationHelper2.getLegacyPermissionStates(i);
        int version = com.android.server.permission.access.util.PackageVersionMigration.INSTANCE.getVersion$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(i);
        com.android.server.permission.access.MutableUserState userState = com.android.server.permission.access.MutableAccessState.mutateUserState$default(state, i, 0, 2, null);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        com.android.server.permission.access.immutable.MutableIntReferenceMap appIdPermissionFlags2 = userState.mutateAppIdPermissionFlags();
        java.util.Map $this$forEach$iv3 = legacyAppIdPermissionStates2;
        int $i$f$forEach3 = 0;
        java.util.Iterator<java.util.Map.Entry<java.lang.Integer, java.util.Map<java.lang.String, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermissionState>>> it3 = $this$forEach$iv3.entrySet().iterator();
        while (it3.hasNext()) {
            java.util.Map.Entry element$iv = it3.next();
            java.lang.Integer appId = element$iv.getKey();
            java.util.Map<java.lang.String, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermissionState> legacyPermissionStates = element$iv.getValue();
            com.android.server.permission.access.immutable.IndexedListSet packageNames = state.getExternalState().getAppIdPackageNames().get(appId.intValue());
            if (packageNames == null) {
                permissionMigrationHelper = permissionMigrationHelper2;
                legacyAppIdPermissionStates = legacyAppIdPermissionStates2;
                android.util.Slog.w(LOG_TAG, "Dropping unknown app ID " + appId + " when migrating permission state");
                appIdPermissionFlags = appIdPermissionFlags2;
                $this$forEach$iv = $this$forEach$iv3;
                $i$f$forEach = $i$f$forEach3;
                it = it3;
            } else {
                permissionMigrationHelper = permissionMigrationHelper2;
                legacyAppIdPermissionStates = legacyAppIdPermissionStates2;
                com.android.server.permission.access.immutable.MutableIndexedMap permissionFlags = new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null);
                com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt.set(appIdPermissionFlags2, appId.intValue(), permissionFlags);
                java.util.Map $this$forEach$iv4 = legacyPermissionStates;
                for (java.util.Map.Entry element$iv2 : $this$forEach$iv4.entrySet()) {
                    java.lang.String permissionName = element$iv2.getKey();
                    java.util.Map $this$forEach$iv5 = $this$forEach$iv4;
                    com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermissionState legacyPermissionState = element$iv2.getValue();
                    com.android.server.permission.access.immutable.MutableIntReferenceMap appIdPermissionFlags3 = appIdPermissionFlags2;
                    com.android.server.permission.access.permission.Permission permission = state.getSystemState().getPermissions().get(permissionName);
                    if (permission == null) {
                        $this$forEach$iv2 = $this$forEach$iv3;
                        java.lang.String str = LOG_TAG;
                        $i$f$forEach2 = $i$f$forEach3;
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        it2 = it3;
                        sb.append("Dropping unknown permission ");
                        sb.append(permissionName);
                        sb.append(" for app ID ");
                        sb.append(appId);
                        sb.append(" when migrating permission state");
                        android.util.Slog.w(str, sb.toString());
                    } else {
                        $this$forEach$iv2 = $this$forEach$iv3;
                        $i$f$forEach2 = $i$f$forEach3;
                        it2 = it3;
                        permissionFlags.put(permissionName, java.lang.Integer.valueOf(migratePermissionFlags(permission, legacyPermissionState, appId.intValue(), i)));
                    }
                    $this$forEach$iv3 = $this$forEach$iv2;
                    $this$forEach$iv4 = $this$forEach$iv5;
                    appIdPermissionFlags2 = appIdPermissionFlags3;
                    $i$f$forEach3 = $i$f$forEach2;
                    it3 = it2;
                }
                appIdPermissionFlags = appIdPermissionFlags2;
                $this$forEach$iv = $this$forEach$iv3;
                $i$f$forEach = $i$f$forEach3;
                it = it3;
                com.android.server.permission.access.immutable.MutableIndexedMap packageVersions = userState.mutatePackageVersions();
                int index$iv = 0;
                int size = packageNames.getSize();
                while (index$iv < size) {
                    java.lang.String packageName = packageNames.elementAt(index$iv);
                    packageVersions.put(packageName, java.lang.Integer.valueOf(version));
                    index$iv++;
                    permissionFlags = permissionFlags;
                }
            }
            i = userId;
            permissionMigrationHelper2 = permissionMigrationHelper;
            legacyAppIdPermissionStates2 = legacyAppIdPermissionStates;
            $this$forEach$iv3 = $this$forEach$iv;
            appIdPermissionFlags2 = appIdPermissionFlags;
            $i$f$forEach3 = $i$f$forEach;
            it3 = it;
        }
    }

    private final int migratePermissionFlags(com.android.server.permission.access.permission.Permission permission, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermissionState legacyPermissionState, int appId, int userId) {
        if (!(permission.getPermissionInfo().getProtection() == 0)) {
            if (!(permission.getPermissionInfo().getProtection() == 2)) {
                com.android.server.permission.access.permission.Permission this_$iv = permission.getPermissionInfo().getProtection() == 4 ? 1 : null;
                if (this_$iv == null) {
                    $i$f$getProtection = permission.getPermissionInfo().getProtection() != 1 ? 0 : 1;
                    $i$f$getProtection = $i$f$getProtection != 0 ? legacyPermissionState.isGranted() ? 16 : 0 : 0;
                }
            }
            $i$f$getProtection = legacyPermissionState.isGranted() ? (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 32) || com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 67108864)) ? 20 : 4 : 0;
        } else if (!legacyPermissionState.isGranted()) {
            $i$f$getProtection = 2;
        }
        int flags = $i$f$getProtection;
        return com.android.server.permission.access.permission.PermissionFlags.INSTANCE.updateFlags(permission, flags, legacyPermissionState.getFlags(), legacyPermissionState.getFlags());
    }

    /* compiled from: AppIdPermissionMigration.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
