package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public class PermissionMigrationHelperImpl implements com.android.server.pm.permission.PermissionMigrationHelper {
    private static final java.lang.String LOG_TAG = com.android.server.pm.permission.PermissionMigrationHelperImpl.class.getSimpleName();

    @Override // com.android.server.pm.permission.PermissionMigrationHelper
    public boolean hasLegacyPermission() {
        com.android.server.pm.permission.LegacyPermissionSettings legacyPermissions = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getLegacyPermissions();
        return (legacyPermissions.getPermissions().isEmpty() && legacyPermissions.getPermissionTrees().isEmpty()) ? false : true;
    }

    @Override // com.android.server.pm.permission.PermissionMigrationHelper
    @android.annotation.NonNull
    public java.util.Map<java.lang.String, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermission> getLegacyPermissions() {
        return toLegacyPermissions(((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getLegacyPermissions().getPermissions());
    }

    @Override // com.android.server.pm.permission.PermissionMigrationHelper
    @android.annotation.NonNull
    public java.util.Map<java.lang.String, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermission> getLegacyPermissionTrees() {
        return toLegacyPermissions(((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getLegacyPermissions().getPermissionTrees());
    }

    @android.annotation.NonNull
    private java.util.Map<java.lang.String, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermission> toLegacyPermissions(java.util.List<com.android.server.pm.permission.LegacyPermission> list) {
        final android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        list.forEach(new java.util.function.Consumer() { // from class: com.android.server.pm.permission.PermissionMigrationHelperImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.permission.PermissionMigrationHelperImpl.lambda$toLegacyPermissions$0(arrayMap, (com.android.server.pm.permission.LegacyPermission) obj);
            }
        });
        return arrayMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$toLegacyPermissions$0(java.util.Map map, com.android.server.pm.permission.LegacyPermission legacyPermission) {
        map.put(legacyPermission.getPermissionInfo().name, new com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermission(legacyPermission.getPermissionInfo(), legacyPermission.getType()));
    }

    @Override // com.android.server.pm.permission.PermissionMigrationHelper
    @android.annotation.NonNull
    public java.util.Map<java.lang.Integer, java.util.Map<java.lang.String, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermissionState>> getLegacyPermissionStates(int i) {
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        final android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        com.android.permission.persistence.RuntimePermissionsState runtimePermissionsState = (com.android.permission.persistence.RuntimePermissionsState) packageManagerInternal.getLegacyPermissionsState(i);
        com.android.server.pm.PackageManagerLocal.UnfilteredSnapshot withUnfilteredSnapshot = ((com.android.server.pm.PackageManagerLocal) com.android.server.LocalManagerRegistry.getManager(com.android.server.pm.PackageManagerLocal.class)).withUnfilteredSnapshot();
        try {
            final java.util.Map<java.lang.String, com.android.server.pm.pkg.PackageState> packageStates = withUnfilteredSnapshot.getPackageStates();
            runtimePermissionsState.getPackagePermissions().forEach(new java.util.function.BiConsumer() { // from class: com.android.server.pm.permission.PermissionMigrationHelperImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.pm.permission.PermissionMigrationHelperImpl.this.lambda$getLegacyPermissionStates$1(packageStates, arrayMap, (java.lang.String) obj, (java.util.List) obj2);
                }
            });
            final java.util.Map<java.lang.String, com.android.server.pm.pkg.SharedUserApi> sharedUsers = withUnfilteredSnapshot.getSharedUsers();
            runtimePermissionsState.getSharedUserPermissions().forEach(new java.util.function.BiConsumer() { // from class: com.android.server.pm.permission.PermissionMigrationHelperImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.pm.permission.PermissionMigrationHelperImpl.this.lambda$getLegacyPermissionStates$2(sharedUsers, arrayMap, (java.lang.String) obj, (java.util.List) obj2);
                }
            });
            withUnfilteredSnapshot.close();
            return arrayMap;
        } catch (java.lang.Throwable th) {
            if (withUnfilteredSnapshot != null) {
                try {
                    withUnfilteredSnapshot.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getLegacyPermissionStates$1(java.util.Map map, java.util.Map map2, java.lang.String str, java.util.List list) {
        if (!list.isEmpty()) {
            com.android.server.pm.pkg.PackageState packageState = (com.android.server.pm.pkg.PackageState) map.get(str);
            if (packageState != null) {
                map2.put(java.lang.Integer.valueOf(packageState.getAppId()), toLegacyPermissionStates(list));
                return;
            }
            android.util.Log.w(LOG_TAG, "Package " + str + " not found.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getLegacyPermissionStates$2(java.util.Map map, java.util.Map map2, java.lang.String str, java.util.List list) {
        if (!list.isEmpty()) {
            com.android.server.pm.pkg.SharedUserApi sharedUserApi = (com.android.server.pm.pkg.SharedUserApi) map.get(str);
            if (sharedUserApi != null) {
                map2.put(java.lang.Integer.valueOf(sharedUserApi.getAppId()), toLegacyPermissionStates(list));
                return;
            }
            android.util.Log.w(LOG_TAG, "Shared user " + str + " not found.");
        }
    }

    @Override // com.android.server.pm.permission.PermissionMigrationHelper
    public int getLegacyPermissionStateVersion(int i) {
        int legacyPermissionsVersion = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getLegacyPermissionsVersion(i);
        switch (legacyPermissionsVersion) {
            case -1:
                return 0;
            case 0:
                return -1;
            default:
                return legacyPermissionsVersion;
        }
    }

    @Override // com.android.server.pm.permission.PermissionMigrationHelper
    public boolean hasLegacyPermissionState(int i) {
        return getLegacyPermissionStateVersion(i) > -1;
    }

    @android.annotation.NonNull
    private java.util.Map<java.lang.String, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermissionState> toLegacyPermissionStates(java.util.List<com.android.permission.persistence.RuntimePermissionsState.PermissionState> list) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            com.android.permission.persistence.RuntimePermissionsState.PermissionState permissionState = list.get(i);
            arrayMap.put(permissionState.getName(), new com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermissionState(permissionState.isGranted(), permissionState.getFlags()));
        }
        return arrayMap;
    }
}
