package com.android.server.permission.access.permission;

/* compiled from: AppIdPermissionPolicy.kt */
/* loaded from: classes2.dex */
public final class AppIdPermissionPolicy extends com.android.server.permission.access.SchemePolicy {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.permission.AppIdPermissionPolicy.Companion Companion = new com.android.server.permission.access.permission.AppIdPermissionPolicy.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.permission.AppIdPermissionPolicy.class.getSimpleName();

    @org.jetbrains.annotations.NotNull
    private static final com.android.server.permission.access.immutable.IndexedSet<java.lang.String> RETAIN_IMPLICIT_FLAGS_PERMISSIONS = com.android.server.permission.access.immutable.IndexedSetExtensionsKt.indexedSetOf("android.permission.ACCESS_MEDIA_LOCATION", "android.permission.ACTIVITY_RECOGNITION", "android.permission.READ_MEDIA_AUDIO", "android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO");

    @org.jetbrains.annotations.NotNull
    private static final com.android.server.permission.access.immutable.IndexedSet<java.lang.String> NEARBY_DEVICES_PERMISSIONS = com.android.server.permission.access.immutable.IndexedSetExtensionsKt.indexedSetOf("android.permission.BLUETOOTH_ADVERTISE", "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_SCAN", "android.permission.NEARBY_WIFI_DEVICES");

    @org.jetbrains.annotations.NotNull
    private static final com.android.server.permission.access.immutable.IndexedSet<java.lang.String> NOTIFICATIONS_PERMISSIONS = com.android.server.permission.access.immutable.IndexedSetExtensionsKt.indexedSetOf("android.permission.POST_NOTIFICATIONS");

    @org.jetbrains.annotations.NotNull
    private static final com.android.server.permission.access.immutable.IndexedSet<java.lang.String> STORAGE_AND_MEDIA_PERMISSIONS = com.android.server.permission.access.immutable.IndexedSetExtensionsKt.indexedSetOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_MEDIA_AUDIO", "android.permission.READ_MEDIA_VIDEO", "android.permission.READ_MEDIA_IMAGES", "android.permission.ACCESS_MEDIA_LOCATION", "android.permission.READ_MEDIA_VISUAL_USER_SELECTED");

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.permission.AppIdPermissionPersistence persistence = new com.android.server.permission.access.permission.AppIdPermissionPersistence();

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.permission.AppIdPermissionMigration migration = new com.android.server.permission.access.permission.AppIdPermissionMigration();

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.permission.AppIdPermissionUpgrade upgrade = new com.android.server.permission.access.permission.AppIdPermissionUpgrade(this);

    @org.jetbrains.annotations.NotNull
    private volatile com.android.server.permission.access.immutable.IndexedListSet<com.android.server.permission.access.permission.AppIdPermissionPolicy.OnPermissionFlagsChangedListener> onPermissionFlagsChangedListeners = new com.android.server.permission.access.immutable.MutableIndexedListSet(null, 1, null);

    @org.jetbrains.annotations.NotNull
    private final java.lang.Object onPermissionFlagsChangedListenersLock = new java.lang.Object();

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.immutable.MutableIndexedSet<java.lang.String> privilegedPermissionAllowlistViolations = new com.android.server.permission.access.immutable.MutableIndexedSet<>(null, 1, null);

    /* compiled from: AppIdPermissionPolicy.kt */
    public interface OnPermissionFlagsChangedListener {
        void onPermissionFlagsChanged(int i, int i2, @org.jetbrains.annotations.NotNull java.lang.String str, int i3, int i4);

        void onStateMutated();
    }

    @Override // com.android.server.permission.access.SchemePolicy
    @org.jetbrains.annotations.NotNull
    public java.lang.String getSubjectScheme() {
        return com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID;
    }

    @Override // com.android.server.permission.access.SchemePolicy
    @org.jetbrains.annotations.NotNull
    public java.lang.String getObjectScheme() {
        return "permission";
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onStateMutated(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$onStateMutated) {
        com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv = this.onPermissionFlagsChangedListeners;
        int size = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            com.android.server.permission.access.permission.AppIdPermissionPolicy.OnPermissionFlagsChangedListener it = $this$forEachIndexed$iv.elementAt(index$iv);
            it.onStateMutated();
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onInitialized(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onInitialized) {
        com.android.server.permission.access.permission.Permission newPermission;
        if (!android.permission.flags.Flags.newPermissionGidEnabled()) {
            java.util.Map $this$forEach$iv = $this$onInitialized.getNewState().getExternalState().getConfigPermissions();
            for (java.util.Map.Entry element$iv : $this$forEach$iv.entrySet()) {
                java.lang.String permissionName = element$iv.getKey();
                com.android.server.SystemConfig.PermissionEntry permissionEntry = element$iv.getValue();
                com.android.server.permission.access.permission.Permission oldPermission = $this$onInitialized.getNewState().getSystemState().getPermissions().get(permissionName);
                if (oldPermission != null) {
                    if (permissionEntry.gids != null) {
                        newPermission = com.android.server.permission.access.permission.Permission.copy$default(oldPermission, null, false, 0, 0, permissionEntry.gids, permissionEntry.perUser, 15, null);
                    }
                } else {
                    android.content.pm.PermissionInfo $this$onInitialized_u24lambda_u242_u24lambda_u241 = new android.content.pm.PermissionInfo();
                    $this$onInitialized_u24lambda_u242_u24lambda_u241.name = permissionName;
                    $this$onInitialized_u24lambda_u242_u24lambda_u241.packageName = com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME;
                    $this$onInitialized_u24lambda_u242_u24lambda_u241.protectionLevel = 2;
                    if (permissionEntry.gids != null) {
                        newPermission = new com.android.server.permission.access.permission.Permission($this$onInitialized_u24lambda_u242_u24lambda_u241, false, 1, 0, permissionEntry.gids, permissionEntry.perUser);
                    } else {
                        newPermission = new com.android.server.permission.access.permission.Permission($this$onInitialized_u24lambda_u242_u24lambda_u241, false, 1, 0, null, false, 48, null);
                    }
                }
                com.android.server.permission.access.immutable.MutableIndexedMap $this$set$iv = com.android.server.permission.access.MutableAccessState.mutateSystemState$default($this$onInitialized.getNewState(), 0, 1, null).mutatePermissions();
                $this$set$iv.put(permissionName, newPermission);
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onUserAdded(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onUserAdded, int userId) {
        java.util.Map $this$forEach$iv = $this$onUserAdded.getNewState().getExternalState().getPackageStates();
        for (java.util.Map.Entry element$iv : $this$forEach$iv.entrySet()) {
            com.android.server.pm.pkg.PackageState packageState = element$iv.getValue();
            evaluateAllPermissionStatesForPackageAndUser($this$onUserAdded, packageState, userId, null);
        }
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv = $this$onUserAdded.getNewState().getExternalState().getAppIdPackageNames();
        int size = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            int appId = $this$forEachIndexed$iv.keyAt(index$iv);
            $this$forEachIndexed$iv.valueAt(index$iv);
            inheritImplicitPermissionStates($this$onUserAdded, appId, userId);
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onAppIdRemoved(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onAppIdRemoved, int appId) {
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv = $this$onAppIdRemoved.getNewState().getUserStates();
        int size = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            $this$forEachIndexed$iv.keyAt(index$iv);
            com.android.server.permission.access.UserState userState = $this$forEachIndexed$iv.valueAt(index$iv);
            int userStateIndex = index$iv;
            if (userState.getAppIdPermissionFlags().contains(appId)) {
                com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt.minusAssign(com.android.server.permission.access.MutableAccessState.mutateUserStateAt$default($this$onAppIdRemoved.getNewState(), userStateIndex, 0, 2, null).mutateAppIdPermissionFlags(), appId);
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onStorageVolumeMounted(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onStorageVolumeMounted, @org.jetbrains.annotations.Nullable java.lang.String volumeUuid, @org.jetbrains.annotations.NotNull java.util.List<java.lang.String> list, boolean isSystemUpdated) {
        com.android.server.permission.access.immutable.MutableIndexedSet changedPermissionNames = new com.android.server.permission.access.immutable.MutableIndexedSet(null, 1, null);
        int size = list.size();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            java.lang.String packageName = list.get(index$iv);
            com.android.server.pm.pkg.PackageState packageState = $this$onStorageVolumeMounted.getNewState().getExternalState().getPackageStates().get(packageName);
            if (packageState != null) {
                adoptPermissions($this$onStorageVolumeMounted, packageState, changedPermissionNames);
                addPermissionGroups($this$onStorageVolumeMounted, packageState);
                addPermissions($this$onStorageVolumeMounted, packageState, changedPermissionNames);
                trimPermissions($this$onStorageVolumeMounted, packageState.getPackageName(), changedPermissionNames);
                trimPermissionStates($this$onStorageVolumeMounted, packageState.getAppId());
                revokePermissionsOnPackageUpdate($this$onStorageVolumeMounted, packageState.getAppId());
            }
        }
        int size2 = changedPermissionNames.getSize();
        for (int index$iv2 = 0; index$iv2 < size2; index$iv2++) {
            java.lang.String permissionName = changedPermissionNames.elementAt(index$iv2);
            evaluatePermissionStateForAllPackages($this$onStorageVolumeMounted, permissionName, null);
        }
        int size3 = list.size();
        for (int index$iv3 = 0; index$iv3 < size3; index$iv3++) {
            java.lang.String packageName2 = list.get(index$iv3);
            com.android.server.pm.pkg.PackageState packageState2 = $this$onStorageVolumeMounted.getNewState().getExternalState().getPackageStates().get(packageName2);
            if (packageState2 != null) {
                com.android.server.pm.pkg.PackageState installedPackageState = isSystemUpdated ? packageState2 : null;
                evaluateAllPermissionStatesForPackage($this$onStorageVolumeMounted, packageState2, installedPackageState);
            }
        }
        int index$iv4 = 0;
        int size4 = list.size();
        while (index$iv4 < size4) {
            java.lang.String packageName3 = list.get(index$iv4);
            com.android.server.pm.pkg.PackageState packageState3 = $this$onStorageVolumeMounted.getNewState().getExternalState().getPackageStates().get(packageName3);
            if (packageState3 != null) {
                com.android.server.permission.access.immutable.IntSet $this$forEachIndexed$iv = $this$onStorageVolumeMounted.getNewState().getExternalState().getUserIds();
                int index$iv5 = 0;
                int size5 = $this$forEachIndexed$iv.getSize();
                while (index$iv5 < size5) {
                    int userId = $this$forEachIndexed$iv.elementAt(index$iv5);
                    inheritImplicitPermissionStates($this$onStorageVolumeMounted, packageState3.getAppId(), userId);
                    index$iv5++;
                    changedPermissionNames = changedPermissionNames;
                }
            }
            index$iv4++;
            changedPermissionNames = changedPermissionNames;
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onPackageAdded(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageAdded, @org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState) {
        com.android.server.permission.access.immutable.MutableIndexedSet changedPermissionNames = new com.android.server.permission.access.immutable.MutableIndexedSet(null, 1, null);
        adoptPermissions($this$onPackageAdded, packageState, changedPermissionNames);
        addPermissionGroups($this$onPackageAdded, packageState);
        addPermissions($this$onPackageAdded, packageState, changedPermissionNames);
        trimPermissions($this$onPackageAdded, packageState.getPackageName(), changedPermissionNames);
        trimPermissionStates($this$onPackageAdded, packageState.getAppId());
        revokePermissionsOnPackageUpdate($this$onPackageAdded, packageState.getAppId());
        int size = changedPermissionNames.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            java.lang.String permissionName = changedPermissionNames.elementAt(index$iv);
            evaluatePermissionStateForAllPackages($this$onPackageAdded, permissionName, null);
        }
        evaluateAllPermissionStatesForPackage($this$onPackageAdded, packageState, packageState);
        com.android.server.permission.access.immutable.IntSet $this$forEachIndexed$iv = $this$onPackageAdded.getNewState().getExternalState().getUserIds();
        int size2 = $this$forEachIndexed$iv.getSize();
        for (int index$iv2 = 0; index$iv2 < size2; index$iv2++) {
            int userId = $this$forEachIndexed$iv.elementAt(index$iv2);
            inheritImplicitPermissionStates($this$onPackageAdded, packageState.getAppId(), userId);
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onPackageRemoved(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageRemoved, @org.jetbrains.annotations.NotNull java.lang.String packageName, int appId) {
        if (!(!$this$onPackageRemoved.getNewState().getExternalState().getDisabledSystemPackageStates().containsKey(packageName))) {
            throw new java.lang.IllegalStateException(("Package " + packageName + " reported as removed before disabled system package is enabled").toString());
        }
        com.android.server.permission.access.immutable.MutableIndexedSet changedPermissionNames = new com.android.server.permission.access.immutable.MutableIndexedSet(null, 1, null);
        trimPermissions($this$onPackageRemoved, packageName, changedPermissionNames);
        if ($this$onPackageRemoved.getNewState().getExternalState().getAppIdPackageNames().contains(appId)) {
            trimPermissionStates($this$onPackageRemoved, appId);
        }
        int size = changedPermissionNames.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            java.lang.String permissionName = changedPermissionNames.elementAt(index$iv);
            evaluatePermissionStateForAllPackages($this$onPackageRemoved, permissionName, null);
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onPackageInstalled(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageInstalled, @org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState, int userId) {
        clearRestrictedPermissionImplicitExemption($this$onPackageInstalled, packageState, userId);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00f5 A[LOOP:1: B:25:0x009b->B:36:0x00f5, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f3 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void clearRestrictedPermissionImplicitExemption(com.android.server.permission.access.MutateStateScope $this$clearRestrictedPermissionImplicitExemption, com.android.server.pm.pkg.PackageState packageState, int userId) {
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        com.android.server.pm.pkg.AndroidPackage androidPackage2;
        boolean z;
        int newFlags;
        int newFlags2;
        boolean z2;
        boolean z3;
        if (packageState.isSystem() || (androidPackage = packageState.getAndroidPackage()) == null) {
            return;
        }
        int appId = packageState.getAppId();
        java.lang.Iterable $this$forEach$iv = androidPackage.getRequestedPermissions();
        for (java.lang.Object element$iv : $this$forEach$iv) {
            java.lang.String permissionName = (java.lang.String) element$iv;
            com.android.server.permission.access.permission.Permission this_$iv = $this$clearRestrictedPermissionImplicitExemption.getNewState().getSystemState().getPermissions().get(permissionName);
            if (this_$iv == null) {
                androidPackage2 = androidPackage;
            } else if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(this_$iv.getPermissionInfo().flags, 4) || com.android.server.permission.access.util.IntExtensionsKt.hasBits(this_$iv.getPermissionInfo().flags, 8)) {
                com.android.server.permission.access.AccessState state$iv = $this$clearRestrictedPermissionImplicitExemption.getNewState();
                com.android.server.permission.access.immutable.IndexedListSet indexedListSet = state$iv.getExternalState().getAppIdPackageNames().get(appId);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(indexedListSet);
                com.android.server.permission.access.immutable.IndexedListSet packageNames$iv = indexedListSet;
                com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv$iv$iv = packageNames$iv;
                int size = $this$forEachIndexed$iv$iv$iv.getSize();
                int index$iv$iv$iv = 0;
                while (true) {
                    if (index$iv$iv$iv >= size) {
                        androidPackage2 = androidPackage;
                        z = false;
                        break;
                    }
                    com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv$iv$iv2 = $this$forEachIndexed$iv$iv$iv;
                    java.lang.Object element$iv$iv = $this$forEachIndexed$iv$iv$iv2.elementAt(index$iv$iv$iv);
                    androidPackage2 = androidPackage;
                    java.lang.String packageName$iv = (java.lang.String) element$iv$iv;
                    com.android.server.permission.access.AccessState state$iv2 = state$iv;
                    com.android.server.pm.pkg.PackageState packageState2 = state$iv.getExternalState().getPackageStates().get(packageName$iv);
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState2);
                    com.android.server.pm.pkg.PackageState packageState$iv = packageState2;
                    if (packageState$iv.getAndroidPackage() != null) {
                        if (packageState$iv.isSystem()) {
                            com.android.server.pm.pkg.AndroidPackage androidPackage3 = packageState$iv.getAndroidPackage();
                            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage3);
                            if (androidPackage3.getRequestedPermissions().contains(permissionName)) {
                                z3 = true;
                                if (z3) {
                                    z2 = true;
                                    if (!z2) {
                                        z = true;
                                        break;
                                    }
                                    index$iv$iv$iv++;
                                    $this$forEachIndexed$iv$iv$iv = $this$forEachIndexed$iv$iv$iv2;
                                    androidPackage = androidPackage2;
                                    state$iv = state$iv2;
                                }
                            }
                        }
                        z3 = false;
                        if (z3) {
                        }
                    }
                    z2 = false;
                    if (!z2) {
                    }
                }
                boolean isRequestedBySystemPackage = z;
                if (!isRequestedBySystemPackage) {
                    int oldFlags = getPermissionFlags($this$clearRestrictedPermissionImplicitExemption, appId, userId, permissionName);
                    int newFlags3 = com.android.server.permission.access.util.IntExtensionsKt.andInv(oldFlags, 131072);
                    boolean isExempt = com.android.server.permission.access.util.IntExtensionsKt.hasAnyBit(newFlags3, 229376);
                    if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(this_$iv.getPermissionInfo().flags, 4) && !isExempt) {
                        newFlags = newFlags3 | 262144;
                    } else {
                        newFlags = com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags3, 262144);
                    }
                    if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(this_$iv.getPermissionInfo().flags, 8) && !isExempt) {
                        newFlags2 = newFlags | 524288;
                    } else {
                        newFlags2 = com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags, 524288);
                    }
                    setPermissionFlags($this$clearRestrictedPermissionImplicitExemption, appId, userId, permissionName, newFlags2);
                }
            } else {
                androidPackage2 = androidPackage;
            }
            androidPackage = androidPackage2;
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onPackageUninstalled(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageUninstalled, @org.jetbrains.annotations.NotNull java.lang.String packageName, int appId, int userId) {
        resetRuntimePermissions($this$onPackageUninstalled, packageName, userId);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x010a A[LOOP:1: B:25:0x00ab->B:36:0x010a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0108 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void resetRuntimePermissions(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$resetRuntimePermissions, @org.jetbrains.annotations.NotNull java.lang.String packageName, int userId) {
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        com.android.server.pm.pkg.PackageState packageState;
        com.android.server.pm.pkg.AndroidPackage androidPackage2;
        boolean z;
        boolean z2;
        boolean z3;
        com.android.server.pm.pkg.PackageState packageState2 = $this$resetRuntimePermissions.getNewState().getExternalState().getPackageStates().get(packageName);
        if (packageState2 == null || (androidPackage = packageState2.getAndroidPackage()) == null) {
            return;
        }
        int appId = packageState2.getAppId();
        java.lang.Iterable $this$forEach$iv = androidPackage.getRequestedPermissions();
        for (java.lang.Object element$iv : $this$forEach$iv) {
            java.lang.String permissionName = (java.lang.String) element$iv;
            com.android.server.permission.access.permission.Permission this_$iv = $this$resetRuntimePermissions.getNewState().getSystemState().getPermissions().get(permissionName);
            if (this_$iv == null) {
                packageState = packageState2;
                androidPackage2 = androidPackage;
            } else if ((this_$iv.getPermissionInfo().getProtection() == 1 ? 1 : null) == null) {
                packageState = packageState2;
                androidPackage2 = androidPackage;
            } else if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(this_$iv.getPermissionInfo().flags, 2)) {
                packageState = packageState2;
                androidPackage2 = androidPackage;
            } else {
                com.android.server.permission.access.AccessState state$iv = $this$resetRuntimePermissions.getNewState();
                com.android.server.permission.access.immutable.IndexedListSet indexedListSet = state$iv.getExternalState().getAppIdPackageNames().get(appId);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(indexedListSet);
                com.android.server.permission.access.immutable.IndexedListSet packageNames$iv = indexedListSet;
                com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv$iv$iv = packageNames$iv;
                int size = $this$forEachIndexed$iv$iv$iv.getSize();
                int index$iv$iv$iv = 0;
                while (true) {
                    if (index$iv$iv$iv >= size) {
                        packageState = packageState2;
                        androidPackage2 = androidPackage;
                        z = false;
                        break;
                    }
                    packageState = packageState2;
                    com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv$iv$iv2 = $this$forEachIndexed$iv$iv$iv;
                    java.lang.Object element$iv$iv = $this$forEachIndexed$iv$iv$iv2.elementAt(index$iv$iv$iv);
                    java.lang.String packageName$iv = (java.lang.String) element$iv$iv;
                    androidPackage2 = androidPackage;
                    com.android.server.pm.pkg.PackageState packageState3 = state$iv.getExternalState().getPackageStates().get(packageName$iv);
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState3);
                    com.android.server.pm.pkg.PackageState packageState$iv = packageState3;
                    if (packageState$iv.getAndroidPackage() != null) {
                        java.lang.String packageName$iv2 = packageState$iv.getPackageName();
                        if (!com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(packageName$iv2, packageName)) {
                            com.android.server.pm.pkg.AndroidPackage androidPackage3 = packageState$iv.getAndroidPackage();
                            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage3);
                            if (androidPackage3.getRequestedPermissions().contains(permissionName)) {
                                z3 = true;
                                if (z3) {
                                    z2 = true;
                                    if (!z2) {
                                        z = true;
                                        break;
                                    }
                                    index$iv$iv$iv++;
                                    packageState2 = packageState;
                                    $this$forEachIndexed$iv$iv$iv = $this$forEachIndexed$iv$iv$iv2;
                                    androidPackage = androidPackage2;
                                }
                            }
                        }
                        z3 = false;
                        if (z3) {
                        }
                    }
                    z2 = false;
                    if (!z2) {
                    }
                }
                boolean isRequestedByOtherPackages = z;
                if (!isRequestedByOtherPackages) {
                    int oldFlags = getPermissionFlags($this$resetRuntimePermissions, appId, userId, permissionName);
                    if (!com.android.server.permission.access.util.IntExtensionsKt.hasAnyBit(oldFlags, com.android.internal.util.FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT)) {
                        int newFlags = com.android.server.permission.access.util.IntExtensionsKt.andInv((com.android.server.permission.access.util.IntExtensionsKt.hasBits(oldFlags, 8) || com.android.server.permission.access.util.IntExtensionsKt.hasBits(oldFlags, 512)) ? oldFlags | 16 : com.android.server.permission.access.util.IntExtensionsKt.andInv(oldFlags, 16), 15728736);
                        setPermissionFlags($this$resetRuntimePermissions, appId, userId, permissionName, com.android.server.permission.access.util.IntExtensionsKt.hasBits(newFlags, 1024) ? newFlags | 4096 : newFlags);
                    }
                }
            }
            packageState2 = packageState;
            androidPackage = androidPackage2;
        }
    }

    private final void adoptPermissions(com.android.server.permission.access.MutateStateScope $this$adoptPermissions, com.android.server.pm.pkg.PackageState packageState, com.android.server.permission.access.immutable.MutableIndexedSet<java.lang.String> mutableIndexedSet) {
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        java.util.List $this$forEachIndexed$iv;
        int $i$f$forEachIndexed;
        int i;
        java.util.List $this$forEachIndexed$iv2;
        int $i$f$forEachIndexed2;
        int i2;
        java.lang.String originalPackageName;
        com.android.server.pm.pkg.AndroidPackage androidPackage2 = packageState.getAndroidPackage();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage2);
        java.util.List $this$forEachIndexed$iv3 = androidPackage2.getAdoptPermissions();
        int $i$f$forEachIndexed3 = 0;
        int index$iv = 0;
        int size = $this$forEachIndexed$iv3.size();
        while (index$iv < size) {
            java.lang.String originalPackageName2 = (java.lang.String) $this$forEachIndexed$iv3.get(index$iv);
            java.lang.String packageName = androidPackage2.getPackageName();
            if (!canAdoptPermissions($this$adoptPermissions, packageName, originalPackageName2)) {
                androidPackage = androidPackage2;
                $this$forEachIndexed$iv = $this$forEachIndexed$iv3;
                $i$f$forEachIndexed = $i$f$forEachIndexed3;
                i = size;
            } else {
                com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv4 = $this$adoptPermissions.getNewState().getSystemState().getPermissions();
                int index$iv2 = 0;
                int size2 = $this$forEachIndexed$iv4.getSize();
                while (index$iv2 < size2) {
                    java.lang.String keyAt = $this$forEachIndexed$iv4.keyAt(index$iv2);
                    com.android.server.permission.access.permission.Permission oldPermission = $this$forEachIndexed$iv4.valueAt(index$iv2);
                    java.lang.String permissionName = keyAt;
                    int permissionIndex = index$iv2;
                    com.android.server.pm.pkg.AndroidPackage androidPackage3 = androidPackage2;
                    if (!com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(oldPermission.getPermissionInfo().packageName, originalPackageName2)) {
                        $this$forEachIndexed$iv2 = $this$forEachIndexed$iv3;
                        $i$f$forEachIndexed2 = $i$f$forEachIndexed3;
                        i2 = size;
                        originalPackageName = originalPackageName2;
                    } else {
                        android.content.pm.PermissionInfo newPermissionInfo = new android.content.pm.PermissionInfo();
                        $this$forEachIndexed$iv2 = $this$forEachIndexed$iv3;
                        newPermissionInfo.name = oldPermission.getPermissionInfo().name;
                        newPermissionInfo.packageName = packageName;
                        newPermissionInfo.protectionLevel = oldPermission.getPermissionInfo().protectionLevel;
                        com.android.server.permission.access.permission.Permission newPermission = com.android.server.permission.access.permission.Permission.copy$default(oldPermission, newPermissionInfo, false, 0, 0, null, false, 52, null);
                        $i$f$forEachIndexed2 = $i$f$forEachIndexed3;
                        i2 = size;
                        originalPackageName = originalPackageName2;
                        com.android.server.permission.access.MutableAccessState.mutateSystemState$default($this$adoptPermissions.getNewState(), 0, 1, null).mutatePermissions().putAt(permissionIndex, newPermission);
                        mutableIndexedSet.add(permissionName);
                    }
                    index$iv2++;
                    $i$f$forEachIndexed3 = $i$f$forEachIndexed2;
                    size = i2;
                    originalPackageName2 = originalPackageName;
                    androidPackage2 = androidPackage3;
                    $this$forEachIndexed$iv3 = $this$forEachIndexed$iv2;
                }
                androidPackage = androidPackage2;
                $this$forEachIndexed$iv = $this$forEachIndexed$iv3;
                $i$f$forEachIndexed = $i$f$forEachIndexed3;
                i = size;
            }
            index$iv++;
            $i$f$forEachIndexed3 = $i$f$forEachIndexed;
            size = i;
            androidPackage2 = androidPackage;
            $this$forEachIndexed$iv3 = $this$forEachIndexed$iv;
        }
    }

    private final boolean canAdoptPermissions(com.android.server.permission.access.MutateStateScope $this$canAdoptPermissions, java.lang.String packageName, java.lang.String originalPackageName) {
        com.android.server.pm.pkg.PackageState originalPackageState = $this$canAdoptPermissions.getNewState().getExternalState().getPackageStates().get(originalPackageName);
        if (originalPackageState == null) {
            return false;
        }
        if (!originalPackageState.isSystem()) {
            android.util.Slog.w(LOG_TAG, "Unable to adopt permissions from " + originalPackageName + " to " + packageName + ": original package not in system partition");
            return false;
        }
        if (originalPackageState.getAndroidPackage() != null) {
            android.util.Slog.w(LOG_TAG, "Unable to adopt permissions from " + originalPackageName + " to " + packageName + ": original package still exists");
            return false;
        }
        return true;
    }

    private final void addPermissionGroups(com.android.server.permission.access.MutateStateScope $this$addPermissionGroups, com.android.server.pm.pkg.PackageState packageState) {
        android.util.SparseArray $this$allIndexed$iv;
        android.util.SparseArray $this$allIndexed$iv2 = packageState.getUserStates();
        int index$iv$iv = 0;
        int size = $this$allIndexed$iv2.size();
        while (true) {
            if (index$iv$iv >= size) {
                $this$allIndexed$iv = 1;
                break;
            }
            $this$allIndexed$iv2.keyAt(index$iv$iv);
            java.lang.Object value$iv = $this$allIndexed$iv2.valueAt(index$iv$iv);
            com.android.server.pm.pkg.PackageUserState it = (com.android.server.pm.pkg.PackageUserState) value$iv;
            if (!it.isInstantApp()) {
                $this$allIndexed$iv = null;
                break;
            }
            index$iv$iv++;
        }
        if ($this$allIndexed$iv != null) {
            android.util.Slog.w(LOG_TAG, "Ignoring permission groups declared in package " + packageState.getPackageName() + ": instant apps cannot declare permission groups");
            return;
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
        java.util.List $this$forEachIndexed$iv = androidPackage.getPermissionGroups();
        int size2 = $this$forEachIndexed$iv.size();
        for (int index$iv = 0; index$iv < size2; index$iv++) {
            com.android.internal.pm.pkg.component.ParsedPermissionGroup parsedPermissionGroup = (com.android.internal.pm.pkg.component.ParsedPermissionGroup) $this$forEachIndexed$iv.get(index$iv);
            android.content.pm.PermissionGroupInfo newPermissionGroup = com.android.server.pm.parsing.PackageInfoUtils.generatePermissionGroupInfo(parsedPermissionGroup, 128L);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(newPermissionGroup);
            java.lang.String permissionGroupName = newPermissionGroup.name;
            android.content.pm.PermissionGroupInfo oldPermissionGroup = $this$addPermissionGroups.getNewState().getSystemState().getPermissionGroups().get(permissionGroupName);
            if (oldPermissionGroup != null && !com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(newPermissionGroup.packageName, oldPermissionGroup.packageName)) {
                java.lang.String newPackageName = newPermissionGroup.packageName;
                java.lang.String oldPackageName = oldPermissionGroup.packageName;
                if (!packageState.isSystem()) {
                    android.util.Slog.w(LOG_TAG, "Ignoring permission group " + permissionGroupName + " declared in package " + newPackageName + ": already declared in another package " + oldPackageName);
                } else {
                    com.android.server.pm.pkg.PackageState packageState2 = $this$addPermissionGroups.getNewState().getExternalState().getPackageStates().get(oldPackageName);
                    if (packageState2 != null && packageState2.isSystem()) {
                        android.util.Slog.w(LOG_TAG, "Ignoring permission group " + permissionGroupName + " declared in system package " + newPackageName + ": already declared in another system package " + oldPackageName);
                    } else {
                        android.util.Slog.w(LOG_TAG, "Overriding permission group " + permissionGroupName + " with new declaration in system package " + newPackageName + ": originally declared in another package " + oldPackageName);
                    }
                }
            }
            com.android.server.permission.access.immutable.MutableIndexedMap $this$set$iv = com.android.server.permission.access.MutableAccessState.mutateSystemState$default($this$addPermissionGroups.getNewState(), 0, 1, null).mutatePermissionGroups();
            $this$set$iv.put(permissionGroupName, newPermissionGroup);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:124:0x02d8, code lost:
    
        if ((r22.getPermissionInfo().getProtection() == 1) != false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x02fc, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x02fa, code lost:
    
        if ((r22.getPermissionInfo().getProtection() == 4) == false) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0595, code lost:
    
        if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(r1.getPermissionInfo().group, r22.getPermissionInfo().group) == false) goto L159;
     */
    /* JADX WARN: Removed duplicated region for block: B:65:0x05a4  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x05ac A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void addPermissions(com.android.server.permission.access.MutateStateScope $this$addPermissions, com.android.server.pm.pkg.PackageState packageState, com.android.server.permission.access.immutable.MutableIndexedSet<java.lang.String> mutableIndexedSet) {
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        com.android.server.pm.pkg.AndroidPackage oldNewPackage;
        java.util.List $this$forEachIndexed$iv;
        int $i$f$forEachIndexed;
        java.lang.String permissionName;
        boolean isPackageSigningChanged;
        int index$iv;
        int i;
        com.android.server.permission.access.permission.Permission copy$default;
        boolean isPermissionGroupChanged;
        boolean isPackageSigningChanged2;
        int index$iv2;
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv2;
        int i2;
        boolean z;
        boolean isPermissionChanged;
        com.android.server.SystemConfig.PermissionEntry it;
        com.android.server.pm.pkg.AndroidPackage androidPackage2 = packageState.getAndroidPackage();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage2);
        com.android.server.pm.pkg.PackageState packageState2 = $this$addPermissions.getOldState().getExternalState().getPackageStates().get(packageState.getPackageName());
        com.android.server.pm.pkg.AndroidPackage oldNewPackage2 = packageState2 != null ? packageState2.getAndroidPackage() : null;
        boolean isPackageSigningChanged3 = (oldNewPackage2 == null || com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(androidPackage2.getSigningDetails(), oldNewPackage2.getSigningDetails())) ? false : true;
        java.util.List $this$forEachIndexed$iv3 = androidPackage2.getPermissions();
        int $i$f$forEachIndexed2 = 0;
        int index$iv3 = 0;
        int size = $this$forEachIndexed$iv3.size();
        while (index$iv3 < size) {
            com.android.internal.pm.pkg.component.ParsedPermission parsedPermission = (com.android.internal.pm.pkg.component.ParsedPermission) $this$forEachIndexed$iv3.get(index$iv3);
            int i3 = 0;
            android.content.pm.PermissionInfo newPermissionInfo = com.android.server.pm.parsing.PackageInfoUtils.generatePermissionInfo(parsedPermission, 128L);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(newPermissionInfo);
            java.lang.String permissionName2 = newPermissionInfo.name;
            com.android.server.permission.access.permission.Permission oldPermission = parsedPermission.isTree() ? $this$addPermissions.getNewState().getSystemState().getPermissionTrees().get(permissionName2) : $this$addPermissions.getNewState().getSystemState().getPermissions().get(permissionName2);
            com.android.server.permission.access.permission.Permission permissionTree = findPermissionTree($this$addPermissions, permissionName2);
            java.lang.String newPackageName = newPermissionInfo.packageName;
            if (permissionTree != null) {
                androidPackage = androidPackage2;
                if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(newPackageName, permissionTree.getPermissionInfo().packageName)) {
                    oldNewPackage = oldNewPackage2;
                    $this$forEachIndexed$iv = $this$forEachIndexed$iv3;
                    $i$f$forEachIndexed = $i$f$forEachIndexed2;
                } else {
                    oldNewPackage = oldNewPackage2;
                    $this$forEachIndexed$iv = $this$forEachIndexed$iv3;
                    $i$f$forEachIndexed = $i$f$forEachIndexed2;
                    android.util.Slog.w(LOG_TAG, "Ignoring permission " + permissionName2 + " declared in package " + newPackageName + ": base permission tree " + permissionTree.getPermissionInfo().name + " is declared in another package " + permissionTree.getPermissionInfo().packageName);
                    isPackageSigningChanged = isPackageSigningChanged3;
                    index$iv = index$iv3;
                    i = size;
                    index$iv3 = index$iv + 1;
                    androidPackage2 = androidPackage;
                    oldNewPackage2 = oldNewPackage;
                    $this$forEachIndexed$iv3 = $this$forEachIndexed$iv;
                    $i$f$forEachIndexed2 = $i$f$forEachIndexed;
                    size = i;
                    isPackageSigningChanged3 = isPackageSigningChanged;
                }
            } else {
                androidPackage = androidPackage2;
                oldNewPackage = oldNewPackage2;
                $this$forEachIndexed$iv = $this$forEachIndexed$iv3;
                $i$f$forEachIndexed = $i$f$forEachIndexed2;
            }
            if (oldPermission == null || com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(newPackageName, oldPermission.getPermissionInfo().packageName)) {
                permissionName = permissionName2;
                if (oldPermission == null || !oldPermission.isReconciled()) {
                    isPackageSigningChanged = isPackageSigningChanged3;
                    index$iv = index$iv3;
                    i = size;
                } else {
                    boolean isPermissionGroupChanged2 = (!newPermissionInfo.isRuntime() || newPermissionInfo.group == null || com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(newPermissionInfo.group, oldPermission.getPermissionInfo().group)) ? false : true;
                    if (oldPermission.getType() != 1) {
                        if (newPermissionInfo.isRuntime()) {
                        }
                        if (newPermissionInfo.getProtection() == 4) {
                        }
                    }
                    boolean isPermissionProtectionChanged = false;
                    if (isPermissionGroupChanged2 || isPermissionProtectionChanged) {
                        com.android.server.permission.access.immutable.IntSet $this$forEachIndexed$iv4 = $this$addPermissions.getNewState().getExternalState().getUserIds();
                        int $i$f$forEachIndexed3 = 0;
                        int index$iv4 = 0;
                        int size2 = $this$forEachIndexed$iv4.getSize();
                        while (index$iv4 < size2) {
                            int userId = $this$forEachIndexed$iv4.elementAt(index$iv4);
                            com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv5 = $this$addPermissions.getNewState().getExternalState().getAppIdPackageNames();
                            com.android.server.permission.access.immutable.IntSet $this$forEachIndexed$iv6 = $this$forEachIndexed$iv4;
                            int size3 = $this$forEachIndexed$iv5.getSize();
                            int $i$f$forEachIndexed4 = $i$f$forEachIndexed3;
                            int $i$f$forEachIndexed5 = 0;
                            while ($i$f$forEachIndexed5 < size3) {
                                int i4 = size3;
                                int appId = $this$forEachIndexed$iv5.keyAt($i$f$forEachIndexed5);
                                $this$forEachIndexed$iv5.valueAt($i$f$forEachIndexed5);
                                int i5 = size;
                                int i6 = i3;
                                if (isPermissionGroupChanged2) {
                                    isPermissionGroupChanged = isPermissionGroupChanged2;
                                    java.lang.String str = LOG_TAG;
                                    $this$forEachIndexed$iv2 = $this$forEachIndexed$iv5;
                                    java.lang.String str2 = oldPermission.getPermissionInfo().group;
                                    i2 = size2;
                                    java.lang.String str3 = newPermissionInfo.group;
                                    index$iv2 = index$iv3;
                                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                    isPackageSigningChanged2 = isPackageSigningChanged3;
                                    sb.append("Revoking runtime permission ");
                                    sb.append(permissionName);
                                    sb.append(" for appId ");
                                    sb.append(appId);
                                    sb.append(" and userId ");
                                    sb.append(userId);
                                    sb.append(" as the permission group changed from ");
                                    sb.append(str2);
                                    sb.append(" to ");
                                    sb.append(str3);
                                    android.util.Slog.w(str, sb.toString());
                                } else {
                                    isPermissionGroupChanged = isPermissionGroupChanged2;
                                    isPackageSigningChanged2 = isPackageSigningChanged3;
                                    index$iv2 = index$iv3;
                                    $this$forEachIndexed$iv2 = $this$forEachIndexed$iv5;
                                    i2 = size2;
                                }
                                if (isPermissionProtectionChanged) {
                                    android.util.Slog.w(LOG_TAG, "Revoking permission " + permissionName + " for appId " + appId + " and userId " + userId + " as the permission protection changed.");
                                }
                                int userId2 = userId;
                                setPermissionFlags($this$addPermissions, appId, userId2, permissionName, 0);
                                $i$f$forEachIndexed5++;
                                $this$forEachIndexed$iv5 = $this$forEachIndexed$iv2;
                                userId = userId2;
                                size2 = i2;
                                size3 = i4;
                                size = i5;
                                i3 = i6;
                                isPermissionGroupChanged2 = isPermissionGroupChanged;
                                index$iv3 = index$iv2;
                                isPackageSigningChanged3 = isPackageSigningChanged2;
                            }
                            index$iv4++;
                            $this$forEachIndexed$iv4 = $this$forEachIndexed$iv6;
                            $i$f$forEachIndexed3 = $i$f$forEachIndexed4;
                            size = size;
                            index$iv3 = index$iv3;
                            isPackageSigningChanged3 = isPackageSigningChanged3;
                        }
                        isPackageSigningChanged = isPackageSigningChanged3;
                        index$iv = index$iv3;
                        i = size;
                    } else {
                        isPackageSigningChanged = isPackageSigningChanged3;
                        index$iv = index$iv3;
                        i = size;
                    }
                }
                copy$default = oldPermission != null ? com.android.server.permission.access.permission.Permission.copy$default(oldPermission, newPermissionInfo, true, 0, packageState.getAppId(), null, false, 48, null) : new com.android.server.permission.access.permission.Permission(newPermissionInfo, true, 0, packageState.getAppId(), null, false, 48, null);
            } else {
                java.lang.String oldPackageName = oldPermission.getPermissionInfo().packageName;
                if (!packageState.isSystem()) {
                    android.util.Slog.w(LOG_TAG, "Ignoring permission " + permissionName2 + " declared in package " + newPackageName + ": already declared in another package " + oldPackageName);
                    isPackageSigningChanged = isPackageSigningChanged3;
                    index$iv = index$iv3;
                    i = size;
                } else if (oldPermission.getType() != 1 || oldPermission.isReconciled()) {
                    permissionName = permissionName2;
                    com.android.server.pm.pkg.PackageState packageState3 = $this$addPermissions.getNewState().getExternalState().getPackageStates().get(oldPackageName);
                    if (packageState3 != null && packageState3.isSystem()) {
                        android.util.Slog.w(LOG_TAG, "Ignoring permission " + permissionName + " declared in system package " + newPackageName + ": already declared in another system package " + oldPackageName);
                        isPackageSigningChanged = isPackageSigningChanged3;
                        index$iv = index$iv3;
                        i = size;
                    } else {
                        android.util.Slog.w(LOG_TAG, "Overriding permission " + permissionName + " with new declaration in system package " + newPackageName + ": originally declared in another package " + oldPackageName);
                        com.android.server.permission.access.immutable.IntSet $this$forEachIndexed$iv7 = $this$addPermissions.getNewState().getExternalState().getUserIds();
                        int size4 = $this$forEachIndexed$iv7.getSize();
                        for (int index$iv5 = 0; index$iv5 < size4; index$iv5++) {
                            int userId3 = $this$forEachIndexed$iv7.elementAt(index$iv5);
                            com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv8 = $this$addPermissions.getNewState().getExternalState().getAppIdPackageNames();
                            int index$iv6 = 0;
                            int size5 = $this$forEachIndexed$iv8.getSize();
                            while (index$iv6 < size5) {
                                int appId2 = $this$forEachIndexed$iv8.keyAt(index$iv6);
                                $this$forEachIndexed$iv8.valueAt(index$iv6);
                                setPermissionFlags($this$addPermissions, appId2, userId3, permissionName, 0);
                                index$iv6++;
                                size5 = size5;
                                $this$forEachIndexed$iv8 = $this$forEachIndexed$iv8;
                            }
                        }
                        copy$default = new com.android.server.permission.access.permission.Permission(newPermissionInfo, true, 0, packageState.getAppId(), oldPermission.getGids(), oldPermission.getAreGidsPerUser());
                        isPackageSigningChanged = isPackageSigningChanged3;
                        index$iv = index$iv3;
                        i = size;
                    }
                } else {
                    permissionName = permissionName2;
                    copy$default = com.android.server.permission.access.permission.Permission.copy$default(oldPermission, newPermissionInfo, true, 0, packageState.getAppId(), null, false, 48, null);
                    isPackageSigningChanged = isPackageSigningChanged3;
                    index$iv = index$iv3;
                    i = size;
                }
                index$iv3 = index$iv + 1;
                androidPackage2 = androidPackage;
                oldNewPackage2 = oldNewPackage;
                $this$forEachIndexed$iv3 = $this$forEachIndexed$iv;
                $i$f$forEachIndexed2 = $i$f$forEachIndexed;
                size = i;
                isPackageSigningChanged3 = isPackageSigningChanged;
            }
            com.android.server.permission.access.permission.Permission newPermission = copy$default;
            if (android.permission.flags.Flags.newPermissionGidEnabled()) {
                int[] iArr = libcore.util.EmptyArray.INT;
                boolean areGidsPerUser = false;
                if (!parsedPermission.isTree() && packageState.isSystem() && (it = $this$addPermissions.getNewState().getExternalState().getConfigPermissions().get(permissionName)) != null && it.gids != null) {
                    iArr = it.gids;
                    areGidsPerUser = it.perUser;
                }
                newPermission = new com.android.server.permission.access.permission.Permission(newPermissionInfo, true, 0, packageState.getAppId(), iArr, areGidsPerUser);
            }
            if (parsedPermission.isTree()) {
                com.android.server.permission.access.immutable.MutableIndexedMap $this$set$iv = com.android.server.permission.access.MutableAccessState.mutateSystemState$default($this$addPermissions.getNewState(), 0, 1, null).mutatePermissionTrees();
                $this$set$iv.put(permissionName, newPermission);
            } else {
                com.android.server.permission.access.immutable.MutableIndexedMap $this$set$iv2 = com.android.server.permission.access.MutableAccessState.mutateSystemState$default($this$addPermissions.getNewState(), 0, 1, null).mutatePermissions();
                $this$set$iv2.put(permissionName, newPermission);
                if (oldPermission == null) {
                    z = true;
                } else if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(newPackageName, oldPermission.getPermissionInfo().packageName)) {
                    com.android.server.permission.access.permission.Permission this_$iv = newPermission;
                    if (this_$iv.getPermissionInfo().protectionLevel == oldPermission.getPermissionInfo().protectionLevel) {
                        if (oldPermission.isReconciled()) {
                            com.android.server.permission.access.permission.Permission this_$iv2 = newPermission;
                            if ((this_$iv2.getPermissionInfo().getProtection() == 2) && isPackageSigningChanged) {
                                z = true;
                            } else {
                                com.android.server.permission.access.permission.Permission this_$iv3 = newPermission;
                                if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(this_$iv3.getPermissionInfo().getProtectionFlags(), 134217728)) {
                                    com.android.server.permission.access.permission.Permission this_$iv4 = newPermission;
                                    if (!com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(this_$iv4.getPermissionInfo().knownCerts, oldPermission.getPermissionInfo().knownCerts)) {
                                        z = true;
                                    }
                                }
                                com.android.server.permission.access.permission.Permission this_$iv5 = newPermission;
                                z = true;
                                com.android.server.permission.access.permission.Permission this_$iv6 = this_$iv5.getPermissionInfo().getProtection() == 1 ? 1 : null;
                                if (this_$iv6 != null) {
                                    com.android.server.permission.access.permission.Permission this_$iv7 = newPermission;
                                    if (this_$iv7.getPermissionInfo().group != null) {
                                        com.android.server.permission.access.permission.Permission this_$iv8 = newPermission;
                                    }
                                }
                            }
                        }
                        isPermissionChanged = false;
                        if (!isPermissionChanged) {
                            mutableIndexedSet.add(permissionName);
                        }
                    } else {
                        z = true;
                    }
                } else {
                    z = true;
                }
                isPermissionChanged = z;
                if (!isPermissionChanged) {
                }
            }
            index$iv3 = index$iv + 1;
            androidPackage2 = androidPackage;
            oldNewPackage2 = oldNewPackage;
            $this$forEachIndexed$iv3 = $this$forEachIndexed$iv;
            $i$f$forEachIndexed2 = $i$f$forEachIndexed;
            size = i;
            isPackageSigningChanged3 = isPackageSigningChanged;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00b2 A[LOOP:1: B:18:0x0087->B:25:0x00b2, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00fc A[LOOP:2: B:39:0x00d1->B:46:0x00fc, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00fa A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01bf A[LOOP:4: B:70:0x0194->B:77:0x01bf, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01bd A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0211 A[LOOP:5: B:88:0x01e6->B:95:0x0211, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x020f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void trimPermissions(com.android.server.permission.access.MutateStateScope $this$trimPermissions, java.lang.String packageName, com.android.server.permission.access.immutable.MutableIndexedSet<java.lang.String> mutableIndexedSet) {
        int i;
        com.android.server.pm.pkg.PackageState packageState;
        boolean z;
        java.util.List $this$anyIndexed$iv;
        boolean z2;
        java.util.List $this$anyIndexed$iv2;
        boolean z3;
        boolean z4;
        int i2;
        boolean z5;
        boolean z6;
        java.util.List $this$anyIndexed$iv3;
        boolean z7;
        int i3;
        boolean z8;
        boolean z9;
        java.util.List $this$noneIndexed$iv;
        boolean z10;
        java.lang.String str = packageName;
        com.android.server.pm.pkg.PackageState packageState2 = $this$trimPermissions.getNewState().getExternalState().getPackageStates().get(str);
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageState2 != null ? packageState2.getAndroidPackage() : null;
        if (packageState2 == null || androidPackage != null) {
            com.android.server.pm.pkg.PackageState packageState3 = $this$trimPermissions.getNewState().getExternalState().getDisabledSystemPackageStates().get(str);
            com.android.server.pm.pkg.AndroidPackage disabledSystemPackage = packageState3 != null ? packageState3.getAndroidPackage() : null;
            com.android.server.permission.access.immutable.IndexedMap $this$forEachReversedIndexed$iv = $this$trimPermissions.getNewState().getSystemState().getPermissionTrees();
            int index$iv = $this$forEachReversedIndexed$iv.getSize() - 1;
            while (true) {
                if (-1 >= index$iv) {
                    break;
                }
                java.lang.String keyAt = $this$forEachReversedIndexed$iv.keyAt(index$iv);
                com.android.server.permission.access.permission.Permission permissionTree = $this$forEachReversedIndexed$iv.valueAt(index$iv);
                java.lang.String permissionTreeName = keyAt;
                int permissionTreeIndex = index$iv;
                if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(permissionTree.getPermissionInfo().packageName, str)) {
                    if (packageState2 != null) {
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
                        java.util.List $this$noneIndexed$iv2 = androidPackage.getPermissions();
                        int size = $this$noneIndexed$iv2.size();
                        int index$iv$iv = 0;
                        while (true) {
                            if (index$iv$iv >= size) {
                                z9 = true;
                                break;
                            }
                            java.lang.Object element$iv = $this$noneIndexed$iv2.get(index$iv$iv);
                            com.android.internal.pm.pkg.component.ParsedPermission it = (com.android.internal.pm.pkg.component.ParsedPermission) element$iv;
                            if (it.isTree()) {
                                $this$noneIndexed$iv = $this$noneIndexed$iv2;
                                if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(it.getName(), permissionTreeName)) {
                                    z10 = true;
                                    if (!z10) {
                                        z9 = false;
                                        break;
                                    } else {
                                        index$iv$iv++;
                                        $this$noneIndexed$iv2 = $this$noneIndexed$iv;
                                    }
                                }
                            } else {
                                $this$noneIndexed$iv = $this$noneIndexed$iv2;
                            }
                            z10 = false;
                            if (!z10) {
                            }
                        }
                        if (!z9) {
                        }
                    }
                    if (disabledSystemPackage == null || ($this$anyIndexed$iv3 = disabledSystemPackage.getPermissions()) == null) {
                        z6 = false;
                    } else {
                        int index$iv$iv2 = 0;
                        int size2 = $this$anyIndexed$iv3.size();
                        while (true) {
                            if (index$iv$iv2 >= size2) {
                                z7 = false;
                                break;
                            }
                            java.lang.Object element$iv2 = $this$anyIndexed$iv3.get(index$iv$iv2);
                            com.android.internal.pm.pkg.component.ParsedPermission it2 = (com.android.internal.pm.pkg.component.ParsedPermission) element$iv2;
                            if (it2.isTree()) {
                                i3 = size2;
                                if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(it2.getName(), permissionTreeName)) {
                                    z8 = true;
                                    if (!z8) {
                                        z7 = true;
                                        break;
                                    } else {
                                        index$iv$iv2++;
                                        size2 = i3;
                                    }
                                }
                            } else {
                                i3 = size2;
                            }
                            z8 = false;
                            if (!z8) {
                            }
                        }
                        z6 = z7;
                    }
                    if (!z6) {
                        com.android.server.permission.access.MutableAccessState.mutateSystemState$default($this$trimPermissions.getNewState(), 0, 1, null).mutatePermissionTrees().removeAt(permissionTreeIndex);
                    }
                }
                index$iv--;
            }
            com.android.server.permission.access.immutable.IndexedMap $this$forEachReversedIndexed$iv2 = $this$trimPermissions.getNewState().getSystemState().getPermissions();
            int index$iv2 = $this$forEachReversedIndexed$iv2.getSize() - 1;
            for (i = -1; i < index$iv2; i = -1) {
                java.lang.String keyAt2 = $this$forEachReversedIndexed$iv2.keyAt(index$iv2);
                com.android.server.permission.access.permission.Permission permission = $this$forEachReversedIndexed$iv2.valueAt(index$iv2);
                java.lang.String permissionName = keyAt2;
                int permissionIndex = index$iv2;
                com.android.server.permission.access.permission.Permission updatedPermission = updatePermissionIfDynamic($this$trimPermissions, permission);
                com.android.server.permission.access.immutable.IndexedMap $this$forEachReversedIndexed$iv3 = $this$forEachReversedIndexed$iv2;
                com.android.server.permission.access.MutableAccessState.mutateSystemState$default($this$trimPermissions.getNewState(), 0, 1, null).mutatePermissions().putAt(permissionIndex, updatedPermission);
                if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(updatedPermission.getPermissionInfo().packageName, str)) {
                    if (packageState2 != null) {
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
                        java.util.List $this$noneIndexed$iv3 = androidPackage.getPermissions();
                        int size3 = $this$noneIndexed$iv3.size();
                        packageState = packageState2;
                        int index$iv$iv3 = 0;
                        while (true) {
                            if (index$iv$iv3 >= size3) {
                                z4 = true;
                                break;
                            }
                            java.lang.Object element$iv3 = $this$noneIndexed$iv3.get(index$iv$iv3);
                            com.android.internal.pm.pkg.component.ParsedPermission it3 = (com.android.internal.pm.pkg.component.ParsedPermission) element$iv3;
                            if (it3.isTree()) {
                                i2 = size3;
                            } else {
                                i2 = size3;
                                if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(it3.getName(), permissionName)) {
                                    z5 = true;
                                    if (!z5) {
                                        z4 = false;
                                        break;
                                    } else {
                                        index$iv$iv3++;
                                        size3 = i2;
                                    }
                                }
                            }
                            z5 = false;
                            if (!z5) {
                            }
                        }
                        if (!z4) {
                        }
                    } else {
                        packageState = packageState2;
                    }
                    if (disabledSystemPackage == null || ($this$anyIndexed$iv = disabledSystemPackage.getPermissions()) == null) {
                        z = false;
                    } else {
                        int index$iv$iv4 = 0;
                        int size4 = $this$anyIndexed$iv.size();
                        while (true) {
                            if (index$iv$iv4 >= size4) {
                                z2 = false;
                                break;
                            }
                            java.lang.Object element$iv4 = $this$anyIndexed$iv.get(index$iv$iv4);
                            com.android.internal.pm.pkg.component.ParsedPermission it4 = (com.android.internal.pm.pkg.component.ParsedPermission) element$iv4;
                            if (it4.isTree()) {
                                $this$anyIndexed$iv2 = $this$anyIndexed$iv;
                            } else {
                                $this$anyIndexed$iv2 = $this$anyIndexed$iv;
                                if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(it4.getName(), permissionName)) {
                                    z3 = true;
                                    if (!z3) {
                                        z2 = true;
                                        break;
                                    } else {
                                        index$iv$iv4++;
                                        $this$anyIndexed$iv = $this$anyIndexed$iv2;
                                    }
                                }
                            }
                            z3 = false;
                            if (!z3) {
                            }
                        }
                        z = z2;
                    }
                    if (!z) {
                        com.android.server.permission.access.immutable.IntSet $this$forEachIndexed$iv = $this$trimPermissions.getNewState().getExternalState().getUserIds();
                        int index$iv3 = 0;
                        int size5 = $this$forEachIndexed$iv.getSize();
                        while (index$iv3 < size5) {
                            int userId = $this$forEachIndexed$iv.elementAt(index$iv3);
                            com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv2 = $this$trimPermissions.getNewState().getExternalState().getAppIdPackageNames();
                            int index$iv4 = 0;
                            int permissionIndex2 = permissionIndex;
                            int permissionIndex3 = $this$forEachIndexed$iv2.getSize();
                            while (index$iv4 < permissionIndex3) {
                                int appId = $this$forEachIndexed$iv2.keyAt(index$iv4);
                                $this$forEachIndexed$iv2.valueAt(index$iv4);
                                setPermissionFlags($this$trimPermissions, appId, userId, permissionName, 0);
                                index$iv4++;
                                permissionIndex3 = permissionIndex3;
                                $this$forEachIndexed$iv2 = $this$forEachIndexed$iv2;
                                updatedPermission = updatedPermission;
                                permissionIndex2 = permissionIndex2;
                                $this$forEachIndexed$iv = $this$forEachIndexed$iv;
                            }
                            index$iv3++;
                            permissionIndex = permissionIndex2;
                            $this$forEachIndexed$iv = $this$forEachIndexed$iv;
                        }
                        com.android.server.permission.access.MutableAccessState.mutateSystemState$default($this$trimPermissions.getNewState(), 0, 1, null).mutatePermissions().removeAt(permissionIndex);
                        mutableIndexedSet.add(permissionName);
                    }
                } else {
                    packageState = packageState2;
                }
                index$iv2--;
                str = packageName;
                $this$forEachReversedIndexed$iv2 = $this$forEachReversedIndexed$iv3;
                packageState2 = packageState;
            }
        }
    }

    private final com.android.server.permission.access.permission.Permission updatePermissionIfDynamic(com.android.server.permission.access.MutateStateScope $this$updatePermissionIfDynamic, com.android.server.permission.access.permission.Permission permission) {
        if (!(permission.getType() == 2)) {
            return permission;
        }
        com.android.server.permission.access.permission.Permission permissionTree = findPermissionTree($this$updatePermissionIfDynamic, permission.getPermissionInfo().name);
        if (permissionTree == null) {
            return permission;
        }
        android.content.pm.PermissionInfo $this$updatePermissionIfDynamic_u24lambda_u2438 = new android.content.pm.PermissionInfo(permission.getPermissionInfo());
        $this$updatePermissionIfDynamic_u24lambda_u2438.packageName = permissionTree.getPermissionInfo().packageName;
        return com.android.server.permission.access.permission.Permission.copy$default(permission, $this$updatePermissionIfDynamic_u24lambda_u2438, true, 0, permissionTree.getAppId(), null, false, 52, null);
    }

    private final void trimPermissionStates(com.android.server.permission.access.MutateStateScope $this$trimPermissionStates, int appId) {
        int index$iv;
        com.android.server.permission.access.immutable.MutableIndexedSet requestedPermissions = new com.android.server.permission.access.immutable.MutableIndexedSet(null, 1, null);
        com.android.server.permission.access.AccessState state$iv = $this$trimPermissionStates.getNewState();
        com.android.server.permission.access.immutable.IndexedListSet indexedListSet = state$iv.getExternalState().getAppIdPackageNames().get(appId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(indexedListSet);
        com.android.server.permission.access.immutable.IndexedListSet packageNames$iv = indexedListSet;
        int size = packageNames$iv.getSize();
        for (int index$iv$iv = 0; index$iv$iv < size; index$iv$iv++) {
            java.lang.String packageName$iv = packageNames$iv.elementAt(index$iv$iv);
            com.android.server.pm.pkg.PackageState packageState = state$iv.getExternalState().getPackageStates().get(packageName$iv);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState);
            com.android.server.pm.pkg.PackageState packageState$iv = packageState;
            if (packageState$iv.getAndroidPackage() != null) {
                com.android.server.pm.pkg.AndroidPackage androidPackage = packageState$iv.getAndroidPackage();
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
                com.android.server.permission.access.immutable.IndexedSetExtensionsKt.plusAssign(requestedPermissions, androidPackage.getRequestedPermissions());
            }
        }
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv = $this$trimPermissionStates.getNewState().getUserStates();
        int size2 = $this$forEachIndexed$iv.getSize();
        for (int index$iv2 = 0; index$iv2 < size2; index$iv2++) {
            int userId = $this$forEachIndexed$iv.keyAt(index$iv2);
            com.android.server.permission.access.UserState userState = $this$forEachIndexed$iv.valueAt(index$iv2);
            com.android.server.permission.access.immutable.IndexedMap $this$lastIndex$iv$iv = userState.getAppIdPermissionFlags().get(appId);
            if ($this$lastIndex$iv$iv != null) {
                int index$iv3 = $this$lastIndex$iv$iv.getSize() - 1;
                while (-1 < index$iv3) {
                    java.lang.String keyAt = $this$lastIndex$iv$iv.keyAt(index$iv3);
                    $this$lastIndex$iv$iv.valueAt(index$iv3).intValue();
                    java.lang.String permissionName = keyAt;
                    if (requestedPermissions.contains(permissionName)) {
                        index$iv = index$iv3;
                    } else {
                        index$iv = index$iv3;
                        setPermissionFlags($this$trimPermissionStates, appId, userId, permissionName, 0);
                    }
                    index$iv3 = index$iv - 1;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x007a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x022e A[LOOP:3: B:47:0x01eb->B:54:0x022e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x022c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x02ab A[LOOP:4: B:58:0x0268->B:65:0x02ab, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x02a9 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void revokePermissionsOnPackageUpdate(com.android.server.permission.access.MutateStateScope $this$revokePermissionsOnPackageUpdate, int appId) {
        boolean z;
        boolean hasOldPackage;
        boolean z2;
        boolean z3;
        int index$iv;
        int i;
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv;
        boolean hasOldPackage2;
        int userId;
        int i2;
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv2;
        int index$iv2;
        boolean z4;
        boolean z5;
        boolean z6;
        if ($this$revokePermissionsOnPackageUpdate.getOldState().getExternalState().getAppIdPackageNames().contains(appId)) {
            com.android.server.permission.access.AccessState state$iv = $this$revokePermissionsOnPackageUpdate.getOldState();
            com.android.server.permission.access.immutable.IndexedListSet indexedListSet = state$iv.getExternalState().getAppIdPackageNames().get(appId);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(indexedListSet);
            com.android.server.permission.access.immutable.IndexedListSet packageNames$iv = indexedListSet;
            int index$iv$iv$iv = 0;
            int size = packageNames$iv.getSize();
            while (true) {
                if (index$iv$iv$iv >= size) {
                    z6 = false;
                    break;
                }
                java.lang.Object element$iv$iv = packageNames$iv.elementAt(index$iv$iv$iv);
                java.lang.String packageName$iv = (java.lang.String) element$iv$iv;
                com.android.server.pm.pkg.PackageState packageState$iv = state$iv.getExternalState().getPackageStates().get(packageName$iv);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState$iv);
                if (packageState$iv.getAndroidPackage() != null) {
                    z6 = true;
                    break;
                }
                index$iv$iv$iv++;
            }
            if (z6) {
                z = true;
                hasOldPackage = z;
                if (hasOldPackage) {
                    return;
                }
                int initialValue$iv = 10000;
                com.android.server.permission.access.AccessState state$iv2 = $this$revokePermissionsOnPackageUpdate.getOldState();
                com.android.server.permission.access.MutateStateScope $this$reducePackageInAppId$iv = $this$revokePermissionsOnPackageUpdate;
                com.android.server.permission.access.immutable.IndexedListSet indexedListSet2 = state$iv2.getExternalState().getAppIdPackageNames().get(appId);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(indexedListSet2);
                com.android.server.permission.access.immutable.IndexedListSet packageNames$iv2 = indexedListSet2;
                com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$IntRef value$iv$iv = new com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$IntRef();
                value$iv$iv.element = 10000;
                int index$iv$iv$iv2 = 0;
                int size2 = packageNames$iv2.getSize();
                while (index$iv$iv$iv2 < size2) {
                    java.lang.Object element$iv$iv2 = packageNames$iv2.elementAt(index$iv$iv$iv2);
                    int initialValue$iv2 = initialValue$iv;
                    int value$iv = value$iv$iv.element;
                    com.android.server.permission.access.MutateStateScope $this$reducePackageInAppId$iv2 = $this$reducePackageInAppId$iv;
                    java.lang.String packageName$iv2 = (java.lang.String) element$iv$iv2;
                    com.android.server.permission.access.AccessState state$iv3 = state$iv2;
                    com.android.server.pm.pkg.PackageState packageState = state$iv2.getExternalState().getPackageStates().get(packageName$iv2);
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState);
                    com.android.server.pm.pkg.PackageState packageState$iv2 = packageState;
                    if (packageState$iv2.getAndroidPackage() != null) {
                        com.android.server.pm.pkg.AndroidPackage androidPackage = packageState$iv2.getAndroidPackage();
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
                        value$iv = com.android.server.permission.jarjar.kotlin.ranges.RangesKt___RangesKt.coerceAtMost(value$iv, androidPackage.getTargetSdkVersion());
                    }
                    value$iv$iv.element = java.lang.Integer.valueOf(value$iv).intValue();
                    index$iv$iv$iv2++;
                    initialValue$iv = initialValue$iv2;
                    $this$reducePackageInAppId$iv = $this$reducePackageInAppId$iv2;
                    state$iv2 = state$iv3;
                }
                int oldTargetSdkVersion = value$iv$iv.element;
                int initialValue$iv3 = 10000;
                com.android.server.permission.access.AccessState state$iv4 = $this$revokePermissionsOnPackageUpdate.getNewState();
                com.android.server.permission.access.permission.AppIdPermissionPolicy this_$iv = this;
                com.android.server.permission.access.immutable.IndexedListSet indexedListSet3 = state$iv4.getExternalState().getAppIdPackageNames().get(appId);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(indexedListSet3);
                com.android.server.permission.access.immutable.IndexedListSet packageNames$iv3 = indexedListSet3;
                com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$IntRef value$iv$iv2 = new com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$IntRef();
                value$iv$iv2.element = 10000;
                int index$iv$iv$iv3 = 0;
                int size3 = packageNames$iv3.getSize();
                while (index$iv$iv$iv3 < size3) {
                    java.lang.Object element$iv$iv3 = packageNames$iv3.elementAt(index$iv$iv$iv3);
                    int initialValue$iv4 = initialValue$iv3;
                    int value$iv2 = value$iv$iv2.element;
                    com.android.server.permission.access.permission.AppIdPermissionPolicy this_$iv2 = this_$iv;
                    java.lang.String packageName$iv3 = (java.lang.String) element$iv$iv3;
                    com.android.server.permission.access.AccessState state$iv5 = state$iv4;
                    com.android.server.pm.pkg.PackageState packageState2 = state$iv4.getExternalState().getPackageStates().get(packageName$iv3);
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState2);
                    com.android.server.pm.pkg.PackageState packageState$iv3 = packageState2;
                    if (packageState$iv3.getAndroidPackage() != null) {
                        com.android.server.pm.pkg.AndroidPackage androidPackage2 = packageState$iv3.getAndroidPackage();
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage2);
                        value$iv2 = com.android.server.permission.jarjar.kotlin.ranges.RangesKt___RangesKt.coerceAtMost(value$iv2, androidPackage2.getTargetSdkVersion());
                    }
                    value$iv$iv2.element = java.lang.Integer.valueOf(value$iv2).intValue();
                    index$iv$iv$iv3++;
                    initialValue$iv3 = initialValue$iv4;
                    this_$iv = this_$iv2;
                    state$iv4 = state$iv5;
                }
                int newTargetSdkVersion = value$iv$iv2.element;
                boolean isTargetSdkVersionDowngraded = oldTargetSdkVersion >= 29 && newTargetSdkVersion < 29;
                boolean isTargetSdkVersionUpgraded = oldTargetSdkVersion < 29 && newTargetSdkVersion >= 29;
                com.android.server.permission.access.AccessState state$iv6 = $this$revokePermissionsOnPackageUpdate.getOldState();
                com.android.server.permission.access.immutable.IndexedListSet indexedListSet4 = state$iv6.getExternalState().getAppIdPackageNames().get(appId);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(indexedListSet4);
                com.android.server.permission.access.immutable.IndexedListSet packageNames$iv4 = indexedListSet4;
                int index$iv$iv$iv4 = 0;
                int size4 = packageNames$iv4.getSize();
                while (true) {
                    if (index$iv$iv$iv4 >= size4) {
                        z2 = false;
                        break;
                    }
                    java.lang.Object element$iv$iv4 = packageNames$iv4.elementAt(index$iv$iv$iv4);
                    int i3 = size4;
                    java.lang.String packageName$iv4 = (java.lang.String) element$iv$iv4;
                    com.android.server.permission.access.AccessState state$iv7 = state$iv6;
                    com.android.server.pm.pkg.PackageState packageState3 = state$iv6.getExternalState().getPackageStates().get(packageName$iv4);
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState3);
                    com.android.server.pm.pkg.PackageState packageState$iv4 = packageState3;
                    if (packageState$iv4.getAndroidPackage() != null) {
                        com.android.server.pm.pkg.AndroidPackage androidPackage3 = packageState$iv4.getAndroidPackage();
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage3);
                        if (androidPackage3.isRequestLegacyExternalStorage()) {
                            z5 = true;
                            if (!z5) {
                                z2 = true;
                                break;
                            } else {
                                index$iv$iv$iv4++;
                                size4 = i3;
                                state$iv6 = state$iv7;
                            }
                        }
                    }
                    z5 = false;
                    if (!z5) {
                    }
                }
                boolean oldIsRequestLegacyExternalStorage = z2;
                com.android.server.permission.access.AccessState state$iv8 = $this$revokePermissionsOnPackageUpdate.getNewState();
                com.android.server.permission.access.immutable.IndexedListSet indexedListSet5 = state$iv8.getExternalState().getAppIdPackageNames().get(appId);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(indexedListSet5);
                com.android.server.permission.access.immutable.IndexedListSet packageNames$iv5 = indexedListSet5;
                int size5 = packageNames$iv5.getSize();
                int index$iv$iv$iv5 = 0;
                while (true) {
                    if (index$iv$iv$iv5 >= size5) {
                        z3 = false;
                        break;
                    }
                    java.lang.Object element$iv$iv5 = packageNames$iv5.elementAt(index$iv$iv$iv5);
                    int i4 = size5;
                    java.lang.String packageName$iv5 = (java.lang.String) element$iv$iv5;
                    com.android.server.permission.access.AccessState state$iv9 = state$iv8;
                    com.android.server.pm.pkg.PackageState packageState4 = state$iv8.getExternalState().getPackageStates().get(packageName$iv5);
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState4);
                    com.android.server.pm.pkg.PackageState packageState$iv5 = packageState4;
                    if (packageState$iv5.getAndroidPackage() != null) {
                        com.android.server.pm.pkg.AndroidPackage androidPackage4 = packageState$iv5.getAndroidPackage();
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage4);
                        if (androidPackage4.isRequestLegacyExternalStorage()) {
                            z4 = true;
                            if (!z4) {
                                z3 = true;
                                break;
                            } else {
                                index$iv$iv$iv5++;
                                size5 = i4;
                                state$iv8 = state$iv9;
                            }
                        }
                    }
                    z4 = false;
                    if (!z4) {
                    }
                }
                boolean newIsRequestLegacyExternalStorage = z3;
                boolean isNewlyRequestingLegacyExternalStorage = (isTargetSdkVersionUpgraded || oldIsRequestLegacyExternalStorage || !newIsRequestLegacyExternalStorage) ? false : true;
                boolean shouldRevokeStorageAndMediaPermissions = isNewlyRequestingLegacyExternalStorage || isTargetSdkVersionDowngraded;
                if (shouldRevokeStorageAndMediaPermissions) {
                    com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv3 = $this$revokePermissionsOnPackageUpdate.getNewState().getUserStates();
                    int size6 = $this$forEachIndexed$iv3.getSize();
                    int index$iv3 = 0;
                    while (index$iv3 < size6) {
                        int userId2 = $this$forEachIndexed$iv3.keyAt(index$iv3);
                        com.android.server.permission.access.UserState userState = $this$forEachIndexed$iv3.valueAt(index$iv3);
                        int userId3 = userId2;
                        com.android.server.permission.access.immutable.IndexedMap $this$forEachReversedIndexed$iv = userState.getAppIdPermissionFlags().get(appId);
                        if ($this$forEachReversedIndexed$iv != null) {
                            int index$iv4 = $this$forEachReversedIndexed$iv.getSize() - 1;
                            while (true) {
                                index$iv = index$iv3;
                                if (-1 >= index$iv4) {
                                    break;
                                }
                                java.lang.String keyAt = $this$forEachReversedIndexed$iv.keyAt(index$iv4);
                                boolean hasOldPackage3 = hasOldPackage;
                                int oldFlags = $this$forEachReversedIndexed$iv.valueAt(index$iv4).intValue();
                                java.lang.String permissionName = keyAt;
                                com.android.server.permission.access.immutable.IndexedMap $this$forEachReversedIndexed$iv2 = $this$forEachReversedIndexed$iv;
                                if (!STORAGE_AND_MEDIA_PERMISSIONS.contains(permissionName)) {
                                    userId = userId3;
                                    i2 = size6;
                                    $this$forEachIndexed$iv2 = $this$forEachIndexed$iv3;
                                    index$iv2 = index$iv4;
                                } else if (!com.android.server.permission.access.util.IntExtensionsKt.hasBits(oldFlags, 16)) {
                                    userId = userId3;
                                    i2 = size6;
                                    $this$forEachIndexed$iv2 = $this$forEachIndexed$iv3;
                                    index$iv2 = index$iv4;
                                } else if (com.android.server.permission.access.util.IntExtensionsKt.hasAnyBit(oldFlags, com.android.internal.util.FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT)) {
                                    userId = userId3;
                                    i2 = size6;
                                    $this$forEachIndexed$iv2 = $this$forEachIndexed$iv3;
                                    index$iv2 = index$iv4;
                                } else {
                                    java.lang.String str = LOG_TAG;
                                    i2 = size6;
                                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                    $this$forEachIndexed$iv2 = $this$forEachIndexed$iv3;
                                    sb.append("Revoking storage permission: ");
                                    sb.append(permissionName);
                                    sb.append(" for appId:  ");
                                    sb.append(appId);
                                    sb.append(" and user: ");
                                    sb.append(userId3);
                                    android.util.Slog.v(str, sb.toString());
                                    int newFlags = com.android.server.permission.access.util.IntExtensionsKt.andInv(oldFlags, 15728752);
                                    index$iv2 = index$iv4;
                                    userId = userId3;
                                    setPermissionFlags($this$revokePermissionsOnPackageUpdate, appId, userId, permissionName, newFlags);
                                }
                                index$iv4 = index$iv2 - 1;
                                index$iv3 = index$iv;
                                hasOldPackage = hasOldPackage3;
                                $this$forEachReversedIndexed$iv = $this$forEachReversedIndexed$iv2;
                                size6 = i2;
                                $this$forEachIndexed$iv3 = $this$forEachIndexed$iv2;
                                userId3 = userId;
                            }
                            i = size6;
                            $this$forEachIndexed$iv = $this$forEachIndexed$iv3;
                            hasOldPackage2 = hasOldPackage;
                        } else {
                            index$iv = index$iv3;
                            i = size6;
                            $this$forEachIndexed$iv = $this$forEachIndexed$iv3;
                            hasOldPackage2 = hasOldPackage;
                        }
                        index$iv3 = index$iv + 1;
                        hasOldPackage = hasOldPackage2;
                        size6 = i;
                        $this$forEachIndexed$iv3 = $this$forEachIndexed$iv;
                    }
                    return;
                }
                return;
            }
        }
        z = false;
        hasOldPackage = z;
        if (hasOldPackage) {
        }
    }

    private final void evaluatePermissionStateForAllPackages(com.android.server.permission.access.MutateStateScope $this$evaluatePermissionStateForAllPackages, java.lang.String permissionName, com.android.server.pm.pkg.PackageState installedPackageState) {
        int index$iv$iv$iv;
        int $i$f$forEachIndexed;
        int index$iv;
        com.android.server.permission.access.ExternalState externalState = $this$evaluatePermissionStateForAllPackages.getNewState().getExternalState();
        com.android.server.permission.access.immutable.IntSet $this$forEachIndexed$iv = externalState.getUserIds();
        int $i$f$forEachIndexed2 = 0;
        int index$iv2 = 0;
        int size = $this$forEachIndexed$iv.getSize();
        while (index$iv2 < size) {
            int userId = $this$forEachIndexed$iv.elementAt(index$iv2);
            com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv2 = externalState.getAppIdPackageNames();
            int size2 = $this$forEachIndexed$iv2.getSize();
            int index$iv3 = 0;
            while (index$iv3 < size2) {
                int appId = $this$forEachIndexed$iv2.keyAt(index$iv3);
                $this$forEachIndexed$iv2.valueAt(index$iv3);
                com.android.server.permission.access.AccessState state$iv = $this$evaluatePermissionStateForAllPackages.getNewState();
                com.android.server.permission.access.immutable.IndexedListSet indexedListSet = state$iv.getExternalState().getAppIdPackageNames().get(appId);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(indexedListSet);
                com.android.server.permission.access.immutable.IndexedListSet packageNames$iv = indexedListSet;
                com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv$iv$iv = packageNames$iv;
                com.android.server.permission.access.ExternalState externalState2 = externalState;
                int size3 = $this$forEachIndexed$iv$iv$iv.getSize();
                com.android.server.permission.access.immutable.IntSet $this$forEachIndexed$iv3 = $this$forEachIndexed$iv;
                int index$iv$iv$iv2 = 0;
                while (true) {
                    index$iv$iv$iv = 0;
                    if (index$iv$iv$iv2 >= size3) {
                        $i$f$forEachIndexed = $i$f$forEachIndexed2;
                        break;
                    }
                    int i = size3;
                    com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv$iv$iv2 = $this$forEachIndexed$iv$iv$iv;
                    java.lang.Object element$iv$iv = $this$forEachIndexed$iv$iv$iv2.elementAt(index$iv$iv$iv2);
                    java.lang.String packageName$iv = (java.lang.String) element$iv$iv;
                    $i$f$forEachIndexed = $i$f$forEachIndexed2;
                    com.android.server.pm.pkg.PackageState packageState = state$iv.getExternalState().getPackageStates().get(packageName$iv);
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState);
                    com.android.server.pm.pkg.PackageState packageState$iv = packageState;
                    if (packageState$iv.getAndroidPackage() != null) {
                        com.android.server.pm.pkg.AndroidPackage androidPackage = packageState$iv.getAndroidPackage();
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
                        if (androidPackage.getRequestedPermissions().contains(permissionName)) {
                            index$iv$iv$iv = 1;
                        }
                    }
                    if (index$iv$iv$iv != 0) {
                        index$iv$iv$iv = 1;
                        break;
                    }
                    index$iv$iv$iv2++;
                    size3 = i;
                    $this$forEachIndexed$iv$iv$iv = $this$forEachIndexed$iv$iv$iv2;
                    $i$f$forEachIndexed2 = $i$f$forEachIndexed;
                }
                if (index$iv$iv$iv != 0) {
                    index$iv = index$iv3;
                    evaluatePermissionState($this$evaluatePermissionStateForAllPackages, appId, userId, permissionName, installedPackageState);
                } else {
                    index$iv = index$iv3;
                }
                index$iv3 = index$iv + 1;
                externalState = externalState2;
                $this$forEachIndexed$iv = $this$forEachIndexed$iv3;
                $i$f$forEachIndexed2 = $i$f$forEachIndexed;
            }
            index$iv2++;
            $i$f$forEachIndexed2 = $i$f$forEachIndexed2;
        }
    }

    private final void evaluateAllPermissionStatesForPackage(com.android.server.permission.access.MutateStateScope $this$evaluateAllPermissionStatesForPackage, com.android.server.pm.pkg.PackageState packageState, com.android.server.pm.pkg.PackageState installedPackageState) {
        com.android.server.permission.access.immutable.IntSet $this$forEachIndexed$iv = $this$evaluateAllPermissionStatesForPackage.getNewState().getExternalState().getUserIds();
        int size = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            int userId = $this$forEachIndexed$iv.elementAt(index$iv);
            evaluateAllPermissionStatesForPackageAndUser($this$evaluateAllPermissionStatesForPackage, packageState, userId, installedPackageState);
        }
    }

    private final void evaluateAllPermissionStatesForPackageAndUser(com.android.server.permission.access.MutateStateScope $this$evaluateAllPermissionStatesForPackageAndUser, com.android.server.pm.pkg.PackageState packageState, int userId, com.android.server.pm.pkg.PackageState installedPackageState) {
        java.lang.Iterable $this$forEach$iv;
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
        if (androidPackage == null || ($this$forEach$iv = androidPackage.getRequestedPermissions()) == null) {
            return;
        }
        for (java.lang.Object element$iv : $this$forEach$iv) {
            java.lang.String permissionName = (java.lang.String) element$iv;
            evaluatePermissionState($this$evaluateAllPermissionStatesForPackageAndUser, packageState.getAppId(), userId, permissionName, installedPackageState);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:109:0x0581  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0586  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x05c9  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x05f0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x05e8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0583  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x013f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0107 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void evaluatePermissionState(com.android.server.permission.access.MutateStateScope $this$evaluatePermissionState, int appId, int userId, java.lang.String permissionName, com.android.server.pm.pkg.PackageState installedPackageState) {
        com.android.server.permission.access.immutable.MutableIndexedList requestingPackageStates;
        boolean z;
        boolean mayGrantByPrivileged;
        boolean shouldGrantBySignature;
        int index$iv$iv;
        int size;
        boolean shouldGrantByProtectionFlags;
        int i;
        int i2;
        boolean z2;
        boolean z3;
        int targetSdkVersion;
        boolean isImplicitPermission;
        boolean z4;
        com.android.server.permission.access.immutable.IndexedListSet sourcePermissions;
        int newFlags;
        boolean isExempt;
        int coerceAtMost;
        boolean z5;
        int size2;
        int index$iv$iv2;
        boolean z6;
        int index$iv$iv3;
        int size3;
        boolean isCompatibilityPermission;
        int i3;
        com.android.server.permission.access.immutable.IndexedListSet indexedListSet = $this$evaluatePermissionState.getNewState().getExternalState().getAppIdPackageNames().get(appId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(indexedListSet);
        com.android.server.permission.access.immutable.IndexedListSet packageNames = indexedListSet;
        com.android.server.permission.access.immutable.MutableIndexedList requestingPackageStates2 = new com.android.server.permission.access.immutable.MutableIndexedList(null, 1, null);
        int size4 = packageNames.getSize();
        boolean hasMissingPackage = false;
        for (int index$iv = 0; index$iv < size4; index$iv++) {
            java.lang.String packageName = packageNames.elementAt(index$iv);
            com.android.server.pm.pkg.PackageState packageState = $this$evaluatePermissionState.getNewState().getExternalState().getPackageStates().get(packageName);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState);
            com.android.server.pm.pkg.PackageState packageState2 = packageState;
            com.android.server.pm.pkg.AndroidPackage androidPackage = packageState2.getAndroidPackage();
            if (androidPackage == null) {
                hasMissingPackage = true;
            } else if (androidPackage.getRequestedPermissions().contains(permissionName)) {
                requestingPackageStates2.add(packageState2);
            }
        }
        if (packageNames.getSize() == 1 && hasMissingPackage) {
            return;
        }
        com.android.server.permission.access.permission.Permission permission = $this$evaluatePermissionState.getNewState().getSystemState().getPermissions().get(permissionName);
        int oldFlags = getPermissionFlags($this$evaluatePermissionState, appId, userId, permissionName);
        if (permission == null) {
            if (oldFlags == 0) {
                setPermissionFlags($this$evaluatePermissionState, appId, userId, permissionName, 2);
                return;
            }
            return;
        }
        if (permission.getPermissionInfo().getProtection() == 0) {
            boolean wasGranted = com.android.server.permission.access.util.IntExtensionsKt.hasBits(oldFlags, 1);
            if (wasGranted) {
                return;
            }
            boolean wasRevoked = com.android.server.permission.access.util.IntExtensionsKt.hasBits(oldFlags, 2);
            if (installedPackageState != null) {
                com.android.server.pm.pkg.AndroidPackage androidPackage2 = installedPackageState.getAndroidPackage();
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage2);
                if (androidPackage2.getRequestedPermissions().contains(permissionName)) {
                    z5 = true;
                    boolean isRequestedByInstalledPackage = z5;
                    size2 = requestingPackageStates2.getSize();
                    index$iv$iv2 = 0;
                    while (true) {
                        if (index$iv$iv2 < size2) {
                            z6 = false;
                            break;
                        }
                        java.lang.Object element$iv = requestingPackageStates2.get(index$iv$iv2);
                        com.android.server.pm.pkg.PackageState it = (com.android.server.pm.pkg.PackageState) element$iv;
                        if (it.isSystem()) {
                            z6 = true;
                            break;
                        }
                        index$iv$iv2++;
                    }
                    boolean isRequestedBySystemPackage = z6;
                    index$iv$iv3 = 0;
                    size3 = requestingPackageStates2.getSize();
                    while (true) {
                        if (index$iv$iv3 < size3) {
                            isCompatibilityPermission = false;
                            break;
                        }
                        java.lang.Object element$iv2 = requestingPackageStates2.get(index$iv$iv3);
                        com.android.server.pm.pkg.PackageState it2 = (com.android.server.pm.pkg.PackageState) element$iv2;
                        i3 = size3;
                        com.android.server.pm.pkg.AndroidPackage androidPackage3 = it2.getAndroidPackage();
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage3);
                        if (isCompatibilityPermissionForPackage(androidPackage3, permissionName)) {
                            isCompatibilityPermission = true;
                            break;
                        } else {
                            index$iv$iv3++;
                            size3 = i3;
                        }
                    }
                    int newFlags2 = (wasRevoked || isRequestedByInstalledPackage || isRequestedBySystemPackage || isCompatibilityPermission) ? 1 : 2;
                    setPermissionFlags($this$evaluatePermissionState, appId, userId, permissionName, !com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 64) ? (oldFlags & 40) | newFlags2 : newFlags2);
                    return;
                }
            }
            z5 = false;
            boolean isRequestedByInstalledPackage2 = z5;
            size2 = requestingPackageStates2.getSize();
            index$iv$iv2 = 0;
            while (true) {
                if (index$iv$iv2 < size2) {
                }
                index$iv$iv2++;
            }
            boolean isRequestedBySystemPackage2 = z6;
            index$iv$iv3 = 0;
            size3 = requestingPackageStates2.getSize();
            while (true) {
                if (index$iv$iv3 < size3) {
                }
                index$iv$iv3++;
                size3 = i3;
            }
            int newFlags22 = (wasRevoked || isRequestedByInstalledPackage2 || isRequestedBySystemPackage2 || isCompatibilityPermission) ? 1 : 2;
            setPermissionFlags($this$evaluatePermissionState, appId, userId, permissionName, !com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 64) ? (oldFlags & 40) | newFlags22 : newFlags22);
            return;
        }
        if (permission.getPermissionInfo().getProtection() == 2) {
            requestingPackageStates = requestingPackageStates2;
        } else {
            if (!(permission.getPermissionInfo().getProtection() == 4)) {
                if (!(permission.getPermissionInfo().getProtection() == 1)) {
                    android.util.Slog.e(LOG_TAG, "Unknown protection level " + permission.getPermissionInfo().protectionLevel + "for permission " + permission.getPermissionInfo().name + " while evaluating permission statefor appId " + appId + " and userId " + userId);
                    return;
                }
                int newFlags3 = 16777208 & oldFlags;
                boolean wasRevoked2 = (newFlags3 == 0 || com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isPermissionGranted(newFlags3)) ? false : true;
                int initialValue$iv = 10000;
                int value$iv = 10000;
                com.android.server.permission.access.immutable.IndexedList $this$forEachIndexed$iv$iv = requestingPackageStates2;
                int size5 = $this$forEachIndexed$iv$iv.getSize();
                int index$iv$iv4 = 0;
                while (index$iv$iv4 < size5) {
                    int i4 = size5;
                    com.android.server.permission.access.immutable.IndexedList $this$forEachIndexed$iv$iv2 = $this$forEachIndexed$iv$iv;
                    java.lang.Object element$iv3 = $this$forEachIndexed$iv$iv2.get(index$iv$iv4);
                    com.android.server.pm.pkg.AndroidPackage androidPackage4 = ((com.android.server.pm.pkg.PackageState) element$iv3).getAndroidPackage();
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage4);
                    coerceAtMost = com.android.server.permission.jarjar.kotlin.ranges.RangesKt___RangesKt.coerceAtMost(value$iv, androidPackage4.getTargetSdkVersion());
                    value$iv = coerceAtMost;
                    index$iv$iv4++;
                    size5 = i4;
                    initialValue$iv = initialValue$iv;
                    $this$forEachIndexed$iv$iv = $this$forEachIndexed$iv$iv2;
                }
                int targetSdkVersion2 = value$iv;
                if (targetSdkVersion2 >= 23) {
                    boolean wasGrantedByLegacy = com.android.server.permission.access.util.IntExtensionsKt.hasBits(newFlags3, 1024);
                    boolean hasImplicitFlag = com.android.server.permission.access.util.IntExtensionsKt.hasBits(newFlags3, 4096);
                    if (wasGrantedByLegacy) {
                        newFlags3 = com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags3, 1024);
                        if (!hasImplicitFlag) {
                            newFlags3 |= 16;
                        }
                    }
                    boolean wasGrantedByImplicit = com.android.server.permission.access.util.IntExtensionsKt.hasBits(newFlags3, 2048);
                    boolean isLeanbackNotificationsPermission = $this$evaluatePermissionState.getNewState().getExternalState().isLeanback() && NOTIFICATIONS_PERMISSIONS.contains(permissionName);
                    com.android.server.permission.access.immutable.IndexedList $this$forEachIndexed$iv$iv3 = requestingPackageStates2;
                    int size6 = $this$forEachIndexed$iv$iv3.getSize();
                    int index$iv$iv5 = 0;
                    while (true) {
                        if (index$iv$iv5 >= size6) {
                            targetSdkVersion = targetSdkVersion2;
                            isImplicitPermission = false;
                            break;
                        }
                        targetSdkVersion = targetSdkVersion2;
                        com.android.server.permission.access.immutable.IndexedList $this$forEachIndexed$iv$iv4 = $this$forEachIndexed$iv$iv3;
                        java.lang.Object element$iv4 = $this$forEachIndexed$iv$iv4.get(index$iv$iv5);
                        com.android.server.pm.pkg.PackageState it3 = (com.android.server.pm.pkg.PackageState) element$iv4;
                        com.android.server.pm.pkg.AndroidPackage androidPackage5 = it3.getAndroidPackage();
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage5);
                        if (androidPackage5.getImplicitPermissions().contains(permissionName)) {
                            isImplicitPermission = true;
                            break;
                        } else {
                            index$iv$iv5++;
                            targetSdkVersion2 = targetSdkVersion;
                            $this$forEachIndexed$iv$iv3 = $this$forEachIndexed$iv$iv4;
                        }
                    }
                    com.android.server.permission.access.immutable.IndexedListSet sourcePermissions2 = $this$evaluatePermissionState.getNewState().getExternalState().getImplicitToSourcePermissions().get(permissionName);
                    if (sourcePermissions2 != null) {
                        com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv$iv5 = sourcePermissions2;
                        int size7 = $this$forEachIndexed$iv$iv5.getSize();
                        int index$iv$iv6 = 0;
                        while (true) {
                            if (index$iv$iv6 >= size7) {
                                z4 = true;
                                sourcePermissions = null;
                                break;
                            }
                            int i5 = size7;
                            com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv$iv6 = $this$forEachIndexed$iv$iv5;
                            java.lang.Object element$iv5 = $this$forEachIndexed$iv$iv6.elementAt(index$iv$iv6);
                            java.lang.String sourcePermissionName = (java.lang.String) element$iv5;
                            com.android.server.permission.access.immutable.IndexedListSet packageNames2 = packageNames;
                            com.android.server.permission.access.permission.Permission sourcePermission = $this$evaluatePermissionState.getNewState().getSystemState().getPermissions().get(sourcePermissionName);
                            if (sourcePermission == null) {
                                throw new java.lang.IllegalStateException(("Unknown source permission " + sourcePermissionName + " in split permissions").toString());
                            }
                            com.android.server.permission.access.immutable.MutableIndexedList requestingPackageStates3 = requestingPackageStates2;
                            z4 = true;
                            if (!(sourcePermission.getPermissionInfo().getProtection() == 1)) {
                                sourcePermissions = 1;
                                break;
                            }
                            index$iv$iv6++;
                            size7 = i5;
                            $this$forEachIndexed$iv$iv5 = $this$forEachIndexed$iv$iv6;
                            packageNames = packageNames2;
                            requestingPackageStates2 = requestingPackageStates3;
                        }
                    } else {
                        z4 = true;
                        sourcePermissions = null;
                    }
                    boolean shouldGrantByImplicit = (isLeanbackNotificationsPermission || (isImplicitPermission && sourcePermissions != null)) ? z4 : false;
                    if (shouldGrantByImplicit) {
                        newFlags = newFlags3 | 2048;
                        if (wasRevoked2) {
                            newFlags |= 1048576;
                        }
                    } else {
                        newFlags = com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags3, 2048);
                        if ((wasGrantedByLegacy || wasGrantedByImplicit) && com.android.server.permission.access.util.IntExtensionsKt.hasBits(newFlags, 1048576)) {
                            newFlags = com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags, 1048592);
                        }
                    }
                    if (!isImplicitPermission && hasImplicitFlag) {
                        newFlags = com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags, 4096);
                        boolean shouldRetainAsNearbyDevices = false;
                        if (NEARBY_DEVICES_PERMISSIONS.contains(permissionName)) {
                            int accessBackgroundLocationFlags = getPermissionFlags($this$evaluatePermissionState, appId, userId, "android.permission.ACCESS_BACKGROUND_LOCATION");
                            shouldRetainAsNearbyDevices = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isAppOpGranted(accessBackgroundLocationFlags) && !com.android.server.permission.access.util.IntExtensionsKt.hasBits(accessBackgroundLocationFlags, 4096);
                        }
                        boolean shouldRetainByMask = com.android.server.permission.access.util.IntExtensionsKt.hasAnyBit(newFlags, com.android.internal.util.FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT);
                        if (!shouldRetainAsNearbyDevices && !shouldRetainByMask) {
                            newFlags = com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags, 112);
                        } else if (wasGrantedByImplicit) {
                            newFlags |= 16;
                        }
                    }
                } else if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 8192)) {
                    newFlags = newFlags3 & 229376;
                    targetSdkVersion = targetSdkVersion2;
                } else {
                    newFlags = newFlags3 | 1024;
                    if (wasRevoked2) {
                        newFlags |= 1048576;
                    }
                    boolean isNewPermission = getOldStatePermissionFlags($this$evaluatePermissionState, appId, userId, permissionName) == 0;
                    if (isNewPermission) {
                        newFlags |= 4096;
                        targetSdkVersion = targetSdkVersion2;
                    } else {
                        targetSdkVersion = targetSdkVersion2;
                    }
                }
                boolean wasExempt = com.android.server.permission.access.util.IntExtensionsKt.hasAnyBit(newFlags, 229376);
                boolean wasRestricted = com.android.server.permission.access.util.IntExtensionsKt.hasAnyBit(newFlags, 786432);
                if (!(com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 4) || com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 8)) || wasExempt || wasRestricted) {
                    isExempt = wasExempt;
                } else {
                    newFlags |= 131072;
                    isExempt = true;
                }
                int newFlags4 = (!com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 4) || isExempt) ? com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags, 262144) : newFlags | 262144;
                setPermissionFlags($this$evaluatePermissionState, appId, userId, permissionName, (!com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().flags, 8) || isExempt) ? com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags4, 524288) : newFlags4 | 524288);
                return;
            }
            requestingPackageStates = requestingPackageStates2;
        }
        boolean wasProtectionGranted = com.android.server.permission.access.util.IntExtensionsKt.hasBits(oldFlags, 4);
        if (hasMissingPackage && wasProtectionGranted) {
            i = 4;
        } else {
            if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 16)) {
                com.android.server.permission.access.immutable.MutableIndexedList $this$anyIndexed$iv = requestingPackageStates;
                int index$iv$iv7 = 0;
                int size8 = $this$anyIndexed$iv.getSize();
                while (true) {
                    if (index$iv$iv7 >= size8) {
                        z3 = false;
                        break;
                    }
                    java.lang.Object element$iv6 = $this$anyIndexed$iv.get(index$iv$iv7);
                    com.android.server.pm.pkg.PackageState it4 = (com.android.server.pm.pkg.PackageState) element$iv6;
                    if (checkPrivilegedPermissionAllowlist($this$evaluatePermissionState, it4, permission)) {
                        z3 = true;
                        break;
                    }
                    index$iv$iv7++;
                }
                if (!z3) {
                    z = false;
                    mayGrantByPrivileged = z;
                    if (permission.getPermissionInfo().getProtection() != 2) {
                        com.android.server.permission.access.immutable.MutableIndexedList $this$anyIndexed$iv2 = requestingPackageStates;
                        int index$iv$iv8 = 0;
                        int size9 = $this$anyIndexed$iv2.getSize();
                        while (true) {
                            if (index$iv$iv8 >= size9) {
                                z2 = false;
                                break;
                            }
                            java.lang.Object element$iv7 = $this$anyIndexed$iv2.get(index$iv$iv8);
                            com.android.server.permission.access.immutable.MutableIndexedList $this$anyIndexed$iv3 = $this$anyIndexed$iv2;
                            com.android.server.pm.pkg.PackageState it5 = (com.android.server.pm.pkg.PackageState) element$iv7;
                            if (shouldGrantPermissionBySignature($this$evaluatePermissionState, it5, permission)) {
                                z2 = true;
                                break;
                            } else {
                                index$iv$iv8++;
                                $this$anyIndexed$iv2 = $this$anyIndexed$iv3;
                            }
                        }
                        if (z2) {
                            shouldGrantBySignature = true;
                            com.android.server.permission.access.immutable.MutableIndexedList $this$anyIndexed$iv4 = requestingPackageStates;
                            index$iv$iv = 0;
                            size = $this$anyIndexed$iv4.getSize();
                            while (true) {
                                if (index$iv$iv >= size) {
                                    shouldGrantByProtectionFlags = false;
                                    break;
                                }
                                java.lang.Object element$iv8 = $this$anyIndexed$iv4.get(index$iv$iv);
                                i2 = size;
                                com.android.server.pm.pkg.PackageState it6 = (com.android.server.pm.pkg.PackageState) element$iv8;
                                if (shouldGrantPermissionByProtectionFlags($this$evaluatePermissionState, it6, permission)) {
                                    shouldGrantByProtectionFlags = true;
                                    break;
                                } else {
                                    index$iv$iv++;
                                    size = i2;
                                }
                            }
                            i = (mayGrantByPrivileged || !(shouldGrantBySignature || shouldGrantByProtectionFlags)) ? 0 : 4;
                        }
                    }
                    shouldGrantBySignature = false;
                    com.android.server.permission.access.immutable.MutableIndexedList $this$anyIndexed$iv42 = requestingPackageStates;
                    index$iv$iv = 0;
                    size = $this$anyIndexed$iv42.getSize();
                    while (true) {
                        if (index$iv$iv >= size) {
                        }
                        index$iv$iv++;
                        size = i2;
                    }
                    if (mayGrantByPrivileged) {
                    }
                }
            }
            z = true;
            mayGrantByPrivileged = z;
            if (permission.getPermissionInfo().getProtection() != 2) {
            }
            shouldGrantBySignature = false;
            com.android.server.permission.access.immutable.MutableIndexedList $this$anyIndexed$iv422 = requestingPackageStates;
            index$iv$iv = 0;
            size = $this$anyIndexed$iv422.getSize();
            while (true) {
                if (index$iv$iv >= size) {
                }
                index$iv$iv++;
                size = i2;
            }
            if (mayGrantByPrivileged) {
            }
        }
        int newFlags5 = i;
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 64)) {
            newFlags5 = (oldFlags & 40) | newFlags5;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 32)) {
            newFlags5 |= oldFlags & 16;
        }
        setPermissionFlags($this$evaluatePermissionState, appId, userId, permissionName, com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 67108864) ? (oldFlags & 24) | newFlags5 : newFlags5);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void inheritImplicitPermissionStates(com.android.server.permission.access.MutateStateScope $this$inheritImplicitPermissionStates, int appId, int userId) {
        com.android.server.permission.access.immutable.IndexedListSet sourcePermissions;
        com.android.server.permission.access.permission.AppIdPermissionPolicy appIdPermissionPolicy = this;
        com.android.server.permission.access.immutable.MutableIndexedSet implicitPermissions = new com.android.server.permission.access.immutable.MutableIndexedSet(null, 1, null);
        com.android.server.permission.access.AccessState state$iv = $this$inheritImplicitPermissionStates.getNewState();
        com.android.server.permission.access.immutable.IndexedListSet indexedListSet = state$iv.getExternalState().getAppIdPackageNames().get(appId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(indexedListSet);
        com.android.server.permission.access.immutable.IndexedListSet packageNames$iv = indexedListSet;
        int size = packageNames$iv.getSize();
        for (int index$iv$iv = 0; index$iv$iv < size; index$iv$iv++) {
            java.lang.String packageName$iv = packageNames$iv.elementAt(index$iv$iv);
            com.android.server.pm.pkg.PackageState packageState = state$iv.getExternalState().getPackageStates().get(packageName$iv);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState);
            com.android.server.pm.pkg.PackageState packageState$iv = packageState;
            if (packageState$iv.getAndroidPackage() != null) {
                com.android.server.pm.pkg.AndroidPackage androidPackage = packageState$iv.getAndroidPackage();
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
                com.android.server.permission.access.immutable.IndexedSetExtensionsKt.plusAssign(implicitPermissions, androidPackage.getImplicitPermissions());
            }
        }
        int size2 = implicitPermissions.getSize();
        int index$iv = 0;
        while (index$iv < size2) {
            java.lang.String implicitPermissionName = (java.lang.String) implicitPermissions.elementAt(index$iv);
            com.android.server.permission.access.permission.Permission implicitPermission = $this$inheritImplicitPermissionStates.getNewState().getSystemState().getPermissions().get(implicitPermissionName);
            if (implicitPermission == null) {
                throw new java.lang.IllegalStateException(("Unknown implicit permission " + implicitPermissionName + " in split permissions").toString());
            }
            com.android.server.permission.access.permission.Permission this_$iv = implicitPermission.getPermissionInfo().getProtection() == 1 ? 1 : null;
            if (this_$iv != null) {
                boolean isNewPermission = appIdPermissionPolicy.getOldStatePermissionFlags($this$inheritImplicitPermissionStates, appId, userId, implicitPermissionName) == 0;
                if (isNewPermission && (sourcePermissions = $this$inheritImplicitPermissionStates.getNewState().getExternalState().getImplicitToSourcePermissions().get(implicitPermissionName)) != null) {
                    int newFlags = appIdPermissionPolicy.getPermissionFlags($this$inheritImplicitPermissionStates, appId, userId, implicitPermissionName);
                    com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv = sourcePermissions;
                    int $i$f$forEachIndexed = 0;
                    int size3 = $this$forEachIndexed$iv.getSize();
                    int index$iv2 = 0;
                    while (index$iv2 < size3) {
                        com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv2 = $this$forEachIndexed$iv;
                        java.lang.String sourcePermissionName = $this$forEachIndexed$iv.elementAt(index$iv2);
                        int $i$f$forEachIndexed2 = $i$f$forEachIndexed;
                        com.android.server.permission.access.permission.Permission sourcePermission = $this$inheritImplicitPermissionStates.getNewState().getSystemState().getPermissions().get(sourcePermissionName);
                        if (sourcePermission == null) {
                            throw new java.lang.IllegalStateException(("Unknown source permission " + sourcePermissionName + " in split permissions").toString());
                        }
                        int sourceFlags = appIdPermissionPolicy.getPermissionFlags($this$inheritImplicitPermissionStates, appId, userId, sourcePermissionName);
                        int i = size3;
                        boolean isSourceGranted = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isPermissionGranted(sourceFlags);
                        boolean isNewGranted = com.android.server.permission.access.permission.PermissionFlags.INSTANCE.isPermissionGranted(newFlags);
                        boolean isGrantingNewFromRevoke = isSourceGranted && !isNewGranted;
                        if (isSourceGranted == isNewGranted || isGrantingNewFromRevoke) {
                            if (isGrantingNewFromRevoke) {
                                newFlags = 0;
                            }
                            newFlags |= sourceFlags & 16777208;
                        }
                        index$iv2++;
                        appIdPermissionPolicy = this;
                        $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                        $i$f$forEachIndexed = $i$f$forEachIndexed2;
                        size3 = i;
                    }
                    setPermissionFlags($this$inheritImplicitPermissionStates, appId, userId, implicitPermissionName, RETAIN_IMPLICIT_FLAGS_PERMISSIONS.contains(implicitPermissionName) ? com.android.server.permission.access.util.IntExtensionsKt.andInv(newFlags, 4096) : newFlags | 4096);
                }
            }
            index$iv++;
            appIdPermissionPolicy = this;
        }
    }

    private final boolean isCompatibilityPermissionForPackage(com.android.server.pm.pkg.AndroidPackage androidPackage, java.lang.String permissionName) {
        for (com.android.internal.pm.permission.CompatibilityPermissionInfo compatibilityPermission : com.android.internal.pm.permission.CompatibilityPermissionInfo.COMPAT_PERMS) {
            if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(compatibilityPermission.getName(), permissionName) && androidPackage.getTargetSdkVersion() < compatibilityPermission.getSdkVersion()) {
                android.util.Slog.i(LOG_TAG, "Auto-granting " + permissionName + " to old package " + androidPackage.getPackageName());
                return true;
            }
        }
        return false;
    }

    private final boolean shouldGrantPermissionBySignature(com.android.server.permission.access.MutateStateScope $this$shouldGrantPermissionBySignature, com.android.server.pm.pkg.PackageState packageState, com.android.server.permission.access.permission.Permission permission) {
        boolean isRequestedByFactoryApp;
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        com.android.server.pm.pkg.AndroidPackage androidPackage2 = packageState.getAndroidPackage();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage2);
        android.content.pm.SigningDetails packageSigningDetails = androidPackage2.getSigningDetails();
        com.android.server.pm.pkg.PackageState packageState2 = $this$shouldGrantPermissionBySignature.getNewState().getExternalState().getPackageStates().get(permission.getPermissionInfo().packageName);
        android.content.pm.SigningDetails sourceSigningDetails = (packageState2 == null || (androidPackage = packageState2.getAndroidPackage()) == null) ? null : androidPackage.getSigningDetails();
        com.android.server.pm.pkg.PackageState packageState3 = $this$shouldGrantPermissionBySignature.getNewState().getExternalState().getPackageStates().get(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState3);
        com.android.server.pm.pkg.AndroidPackage androidPackage3 = packageState3.getAndroidPackage();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage3);
        android.content.pm.SigningDetails platformSigningDetails = androidPackage3.getSigningDetails();
        boolean hasCommonSigner = (sourceSigningDetails != null ? sourceSigningDetails.hasCommonSignerWithCapability(packageSigningDetails, 4) : false) || packageSigningDetails.hasAncestorOrSelf(platformSigningDetails) || platformSigningDetails.checkCapability(packageSigningDetails, 4);
        if (!android.permission.flags.Flags.signaturePermissionAllowlistEnabled()) {
            return hasCommonSigner;
        }
        if (!hasCommonSigner) {
            return false;
        }
        if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(permission.getPermissionInfo().packageName, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME)) {
            if (!packageState.isSystem()) {
                isRequestedByFactoryApp = false;
            } else if (packageState.isUpdatedSystemApp()) {
                com.android.server.pm.pkg.PackageState packageState4 = $this$shouldGrantPermissionBySignature.getNewState().getExternalState().getDisabledSystemPackageStates().get(packageState.getPackageName());
                com.android.server.pm.pkg.AndroidPackage disabledSystemPackage = packageState4 != null ? packageState4.getAndroidPackage() : null;
                isRequestedByFactoryApp = disabledSystemPackage != null && disabledSystemPackage.getRequestedPermissions().contains(permission.getPermissionInfo().name);
            } else {
                isRequestedByFactoryApp = true;
            }
            if (!isRequestedByFactoryApp && !com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(getSignaturePermissionAllowlistState($this$shouldGrantPermissionBySignature, packageState, permission.getPermissionInfo().name), true)) {
                android.util.Slog.w(LOG_TAG, "Signature permission " + permission.getPermissionInfo().name + " for package " + packageState.getPackageName() + " (" + packageState.getPath() + ") not in signature permission allowlist");
                if (!android.os.Build.isDebuggable()) {
                    return false;
                }
            }
        }
        return true;
    }

    private final java.lang.Boolean getSignaturePermissionAllowlistState(com.android.server.permission.access.MutateStateScope $this$getSignaturePermissionAllowlistState, com.android.server.pm.pkg.PackageState packageState, java.lang.String permissionName) {
        com.android.server.pm.permission.PermissionAllowlist permissionAllowlist = $this$getSignaturePermissionAllowlistState.getNewState().getExternalState().getPermissionAllowlist();
        java.lang.String packageName = packageState.getPackageName();
        if (packageState.isVendor() || packageState.isOdm()) {
            return permissionAllowlist.getVendorSignatureAppAllowlistState(packageName, permissionName);
        }
        if (packageState.isProduct()) {
            return permissionAllowlist.getProductSignatureAppAllowlistState(packageName, permissionName);
        }
        if (packageState.isSystemExt()) {
            return permissionAllowlist.getSystemExtSignatureAppAllowlistState(packageName, permissionName);
        }
        return permissionAllowlist.getSignatureAppAllowlistState(packageName, permissionName);
    }

    private final boolean checkPrivilegedPermissionAllowlist(com.android.server.permission.access.MutateStateScope $this$checkPrivilegedPermissionAllowlist, com.android.server.pm.pkg.PackageState packageState, com.android.server.permission.access.permission.Permission permission) {
        if (com.android.internal.os.RoSystemProperties.CONTROL_PRIVAPP_PERMISSIONS_DISABLE || com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(packageState.getPackageName(), com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME) || !packageState.isSystem() || !packageState.isPrivileged() || !$this$checkPrivilegedPermissionAllowlist.getNewState().getExternalState().getPrivilegedPermissionAllowlistPackages().contains(permission.getPermissionInfo().packageName)) {
            return true;
        }
        java.lang.Boolean allowlistState = getPrivilegedPermissionAllowlistState($this$checkPrivilegedPermissionAllowlist, packageState, permission.getPermissionInfo().name);
        if (allowlistState != null) {
            return allowlistState.booleanValue();
        }
        if (packageState.isUpdatedSystemApp()) {
            return true;
        }
        if (!$this$checkPrivilegedPermissionAllowlist.getNewState().getExternalState().isSystemReady() && !packageState.isApkInUpdatedApex()) {
            android.util.Slog.w(LOG_TAG, "Privileged permission " + permission.getPermissionInfo().name + " for package " + packageState.getPackageName() + " (" + packageState.getPath() + ") not in privileged permission allowlist");
            if (com.android.internal.os.RoSystemProperties.CONTROL_PRIVAPP_PERMISSIONS_ENFORCE) {
                com.android.server.permission.access.immutable.MutableIndexedSet $this$plusAssign$iv = this.privilegedPermissionAllowlistViolations;
                $this$plusAssign$iv.add(packageState.getPackageName() + " (" + packageState.getPath() + "): " + permission.getPermissionInfo().name);
            }
        }
        return true ^ com.android.internal.os.RoSystemProperties.CONTROL_PRIVAPP_PERMISSIONS_ENFORCE;
    }

    private final java.lang.Boolean getPrivilegedPermissionAllowlistState(com.android.server.permission.access.MutateStateScope $this$getPrivilegedPermissionAllowlistState, com.android.server.pm.pkg.PackageState packageState, java.lang.String permissionName) {
        com.android.server.pm.permission.PermissionAllowlist permissionAllowlist = $this$getPrivilegedPermissionAllowlistState.getNewState().getExternalState().getPermissionAllowlist();
        java.lang.String apexModuleName = packageState.getApexModuleName();
        java.lang.String packageName = packageState.getPackageName();
        if (packageState.isVendor() || packageState.isOdm()) {
            return permissionAllowlist.getVendorPrivilegedAppAllowlistState(packageName, permissionName);
        }
        if (packageState.isProduct()) {
            return permissionAllowlist.getProductPrivilegedAppAllowlistState(packageName, permissionName);
        }
        if (packageState.isSystemExt()) {
            return permissionAllowlist.getSystemExtPrivilegedAppAllowlistState(packageName, permissionName);
        }
        if (apexModuleName != null) {
            java.lang.Boolean nonApexAllowlistState = permissionAllowlist.getPrivilegedAppAllowlistState(packageName, permissionName);
            if (nonApexAllowlistState != null) {
                android.util.Slog.w(LOG_TAG, "Package " + packageName + " is an APK in APEX but has permission allowlist on the system image, please bundle the allowlist in the " + apexModuleName + " APEX instead");
            }
            java.lang.Boolean apexAllowlistState = permissionAllowlist.getApexPrivilegedAppAllowlistState(apexModuleName, packageName, permissionName);
            return apexAllowlistState == null ? nonApexAllowlistState : apexAllowlistState;
        }
        return permissionAllowlist.getPrivilegedAppAllowlistState(packageName, permissionName);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x00e3, code lost:
    
        if (r3 != false) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean shouldGrantPermissionByProtectionFlags(com.android.server.permission.access.MutateStateScope $this$shouldGrantPermissionByProtectionFlags, com.android.server.pm.pkg.PackageState packageState, com.android.server.permission.access.permission.Permission permission) {
        boolean shouldGrant;
        boolean contains;
        boolean contains2;
        boolean contains3;
        boolean contains4;
        boolean contains5;
        boolean contains6;
        boolean contains7;
        boolean contains8;
        boolean contains9;
        boolean contains10;
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageState.getAndroidPackage();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage);
        com.android.server.permission.access.immutable.IntMap knownPackages = $this$shouldGrantPermissionByProtectionFlags.getNewState().getExternalState().getKnownPackages();
        java.lang.String packageName = packageState.getPackageName();
        if ((com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 16) || com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 16384)) && packageState.isSystem()) {
            if (packageState.isUpdatedSystemApp()) {
                com.android.server.pm.pkg.PackageState disabledSystemPackageState = $this$shouldGrantPermissionByProtectionFlags.getNewState().getExternalState().getDisabledSystemPackageStates().get(packageState.getPackageName());
                com.android.server.pm.pkg.AndroidPackage disabledSystemPackage = disabledSystemPackageState != null ? disabledSystemPackageState.getAndroidPackage() : null;
                shouldGrant = disabledSystemPackage != null && disabledSystemPackage.getRequestedPermissions().contains(permission.getPermissionInfo().name) && shouldGrantPrivilegedOrOemPermission($this$shouldGrantPermissionByProtectionFlags, disabledSystemPackageState, permission);
            } else {
                shouldGrant = shouldGrantPrivilegedOrOemPermission($this$shouldGrantPermissionByProtectionFlags, packageState, permission);
            }
            if (shouldGrant) {
                return true;
            }
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 128) && androidPackage.getTargetSdkVersion() < 23) {
            return true;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 256)) {
            java.lang.String[] strArr = knownPackages.get(2);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(strArr);
            contains9 = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt.contains(strArr, packageName);
            if (!contains9) {
                java.lang.String[] strArr2 = knownPackages.get(7);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(strArr2);
                contains10 = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt.contains(strArr2, packageName);
            }
            return true;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 512)) {
            java.lang.String[] strArr3 = knownPackages.get(4);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(strArr3);
            contains8 = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt.contains(strArr3, packageName);
            if (contains8) {
                return true;
            }
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 1024) && packageState.isSystem()) {
            return true;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 134217728) && androidPackage.getSigningDetails().hasAncestorOrSelfWithDigest(permission.getPermissionInfo().knownCerts)) {
            return true;
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 2048)) {
            java.lang.String[] strArr4 = knownPackages.get(1);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(strArr4);
            contains7 = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt.contains(strArr4, packageName);
            if (contains7) {
                return true;
            }
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 65536)) {
            java.lang.String[] strArr5 = knownPackages.get(6);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(strArr5);
            contains6 = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt.contains(strArr5, packageName);
            if (contains6) {
                return true;
            }
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 524288)) {
            java.lang.String[] strArr6 = knownPackages.get(10);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(strArr6);
            contains5 = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt.contains(strArr6, packageName);
            if (contains5) {
                return true;
            }
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 1048576)) {
            java.lang.String[] strArr7 = knownPackages.get(11);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(strArr7);
            contains4 = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt.contains(strArr7, packageName);
            if (contains4) {
                return true;
            }
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 2097152)) {
            java.lang.String[] strArr8 = knownPackages.get(12);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(strArr8);
            contains3 = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt.contains(strArr8, packageName);
            if (contains3) {
                return true;
            }
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 8388608)) {
            java.lang.String[] strArr9 = knownPackages.get(15);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(strArr9);
            contains2 = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt.contains(strArr9, packageName);
            if (contains2) {
                return true;
            }
        }
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 33554432)) {
            java.lang.String[] strArr10 = knownPackages.get(17);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(strArr10);
            contains = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt.contains(strArr10, packageName);
            if (contains) {
                return true;
            }
        }
        return com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 4194304) && packageState.getApexModuleName() != null;
    }

    private final boolean shouldGrantPrivilegedOrOemPermission(com.android.server.permission.access.MutateStateScope $this$shouldGrantPrivilegedOrOemPermission, com.android.server.pm.pkg.PackageState packageState, com.android.server.permission.access.permission.Permission permission) {
        java.lang.String permissionName = permission.getPermissionInfo().name;
        java.lang.String packageName = packageState.getPackageName();
        if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 16)) {
            if (packageState.isPrivileged()) {
                if ((packageState.isVendor() || packageState.isOdm()) && !com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 32768)) {
                    android.util.Slog.w(LOG_TAG, "Permission " + permissionName + " cannot be granted to privileged vendor (or odm) app " + packageName + " because it isn't a vendorPrivileged permission");
                    return false;
                }
                return true;
            }
        } else if (com.android.server.permission.access.util.IntExtensionsKt.hasBits(permission.getPermissionInfo().getProtectionFlags(), 16384) && packageState.isOem()) {
            java.lang.Boolean allowlistState = $this$shouldGrantPrivilegedOrOemPermission.getNewState().getExternalState().getPermissionAllowlist().getOemAppAllowlistState(packageName, permissionName);
            if (allowlistState == null) {
                throw new java.lang.IllegalStateException(("OEM permission " + permissionName + " requested by package " + packageName + " must be explicitly declared granted or not").toString());
            }
            return allowlistState.booleanValue();
        }
        return false;
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onSystemReady(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onSystemReady) {
        if (!this.privilegedPermissionAllowlistViolations.isEmpty()) {
            throw new java.lang.IllegalStateException("Signature|privileged permissions not in privileged permission allowlist: " + this.privilegedPermissionAllowlistViolations);
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void parseSystemState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser $this$parseSystemState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state) {
        com.android.server.permission.access.permission.AppIdPermissionPersistence $this$parseSystemState_u24lambda_u2473 = this.persistence;
        $this$parseSystemState_u24lambda_u2473.parseSystemState($this$parseSystemState, state);
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void serializeSystemState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer $this$serializeSystemState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state) {
        com.android.server.permission.access.permission.AppIdPermissionPersistence $this$serializeSystemState_u24lambda_u2474 = this.persistence;
        $this$serializeSystemState_u24lambda_u2474.serializeSystemState($this$serializeSystemState, state);
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void parseUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser $this$parseUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
        com.android.server.permission.access.permission.AppIdPermissionPersistence $this$parseUserState_u24lambda_u2475 = this.persistence;
        $this$parseUserState_u24lambda_u2475.parseUserState($this$parseUserState, state, userId);
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void serializeUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer $this$serializeUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state, int userId) {
        com.android.server.permission.access.permission.AppIdPermissionPersistence $this$serializeUserState_u24lambda_u2476 = this.persistence;
        $this$serializeUserState_u24lambda_u2476.serializeUserState($this$serializeUserState, state, userId);
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission> getPermissionTrees(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$getPermissionTrees) {
        return $this$getPermissionTrees.getState().getSystemState().getPermissionTrees();
    }

    @org.jetbrains.annotations.Nullable
    public final com.android.server.permission.access.permission.Permission findPermissionTree(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$findPermissionTree, @org.jetbrains.annotations.NotNull java.lang.String permissionName) {
        com.android.server.permission.access.permission.Permission permission;
        boolean startsWith$default;
        com.android.server.permission.access.immutable.IndexedMap $this$firstNotNullOfOrNullIndexed$iv = $this$findPermissionTree.getState().getSystemState().getPermissionTrees();
        int index$iv$iv = 0;
        int size = $this$firstNotNullOfOrNullIndexed$iv.getSize();
        while (true) {
            permission = null;
            if (index$iv$iv >= size) {
                break;
            }
            java.lang.Object key$iv = $this$firstNotNullOfOrNullIndexed$iv.keyAt(index$iv$iv);
            java.lang.Object value$iv = $this$firstNotNullOfOrNullIndexed$iv.valueAt(index$iv$iv);
            com.android.server.permission.access.permission.Permission permissionTree = (com.android.server.permission.access.permission.Permission) value$iv;
            java.lang.String permissionTreeName = (java.lang.String) key$iv;
            com.android.server.permission.access.immutable.IndexedMap $this$firstNotNullOfOrNullIndexed$iv2 = $this$firstNotNullOfOrNullIndexed$iv;
            startsWith$default = com.android.server.permission.jarjar.kotlin.text.StringsKt__StringsJVMKt.startsWith$default(permissionName, permissionTreeName, false, 2, null);
            if (startsWith$default && permissionName.length() > permissionTreeName.length() && permissionName.charAt(permissionTreeName.length()) == '.') {
                permission = permissionTree;
            }
            if (permission != null) {
                break;
            }
            index$iv$iv++;
            $this$firstNotNullOfOrNullIndexed$iv = $this$firstNotNullOfOrNullIndexed$iv2;
        }
        return permission;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, android.content.pm.PermissionGroupInfo> getPermissionGroups(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$getPermissionGroups) {
        return $this$getPermissionGroups.getState().getSystemState().getPermissionGroups();
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission> getPermissions(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$getPermissions) {
        return $this$getPermissions.getState().getSystemState().getPermissions();
    }

    public final void addPermission(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$addPermission, @org.jetbrains.annotations.NotNull com.android.server.permission.access.permission.Permission permission, boolean isSynchronousWrite) {
        int writeMode = isSynchronousWrite ? 2 : 1;
        com.android.server.permission.access.immutable.MutableIndexedMap $this$set$iv = $this$addPermission.getNewState().mutateSystemState(writeMode).mutatePermissions();
        $this$set$iv.put(permission.getPermissionInfo().name, permission);
    }

    public final void removePermission(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$removePermission, @org.jetbrains.annotations.NotNull com.android.server.permission.access.permission.Permission permission) {
        com.android.server.permission.access.immutable.MutableIndexedMap $this$minusAssign$iv = com.android.server.permission.access.MutableAccessState.mutateSystemState$default($this$removePermission.getNewState(), 0, 1, null).mutatePermissions();
        $this$minusAssign$iv.remove(permission.getPermissionInfo().name);
    }

    @org.jetbrains.annotations.Nullable
    public final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> getUidPermissionFlags(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$getUidPermissionFlags, int appId, int userId) {
        com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> appIdPermissionFlags;
        com.android.server.permission.access.UserState userState = $this$getUidPermissionFlags.getState().getUserStates().get(userId);
        if (userState == null || (appIdPermissionFlags = userState.getAppIdPermissionFlags()) == null) {
            return null;
        }
        return appIdPermissionFlags.get(appId);
    }

    public final int getPermissionFlags(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$getPermissionFlags, int appId, int userId, @org.jetbrains.annotations.NotNull java.lang.String permissionName) {
        return getPermissionFlags($this$getPermissionFlags.getState(), appId, userId, permissionName);
    }

    private final int getOldStatePermissionFlags(com.android.server.permission.access.MutateStateScope $this$getOldStatePermissionFlags, int appId, int userId, java.lang.String permissionName) {
        return getPermissionFlags($this$getOldStatePermissionFlags.getOldState(), appId, userId, permissionName);
    }

    private final int getPermissionFlags(com.android.server.permission.access.AccessState state, int appId, int userId, java.lang.String permissionName) {
        com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> appIdPermissionFlags;
        com.android.server.permission.access.UserState userState = state.getUserStates().get(userId);
        return ((java.lang.Number) com.android.server.permission.access.immutable.IndexedMapExtensionsKt.getWithDefault((userState == null || (appIdPermissionFlags = userState.getAppIdPermissionFlags()) == null) ? null : appIdPermissionFlags.get(appId), permissionName, 0)).intValue();
    }

    @org.jetbrains.annotations.Nullable
    public final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> getAllPermissionFlags(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$getAllPermissionFlags, int appId, int userId) {
        com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> appIdPermissionFlags;
        com.android.server.permission.access.UserState userState = $this$getAllPermissionFlags.getState().getUserStates().get(userId);
        if (userState == null || (appIdPermissionFlags = userState.getAppIdPermissionFlags()) == null) {
            return null;
        }
        return appIdPermissionFlags.get(appId);
    }

    public final boolean setPermissionFlags(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$setPermissionFlags, int appId, int userId, @org.jetbrains.annotations.NotNull java.lang.String permissionName, int flags) {
        return updatePermissionFlags($this$setPermissionFlags, appId, userId, permissionName, -1, flags);
    }

    public final boolean updatePermissionFlags(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$updatePermissionFlags, int appId, int userId, @org.jetbrains.annotations.NotNull java.lang.String permissionName, int flagMask, int flagValues) {
        if (!$this$updatePermissionFlags.getNewState().getUserStates().contains(userId)) {
            android.util.Slog.e(LOG_TAG, "Unable to update permission flags for missing user " + userId);
            return false;
        }
        com.android.server.permission.access.UserState userState = $this$updatePermissionFlags.getNewState().getUserStates().get(userId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        int oldFlags = ((java.lang.Number) com.android.server.permission.access.immutable.IndexedMapExtensionsKt.getWithDefault(userState.getAppIdPermissionFlags().get(appId), permissionName, 0)).intValue();
        int newFlags = com.android.server.permission.access.util.IntExtensionsKt.andInv(oldFlags, flagMask) | (flagValues & flagMask);
        if (oldFlags == newFlags) {
            return false;
        }
        com.android.server.permission.access.MutableUserState mutateUserState$default = com.android.server.permission.access.MutableAccessState.mutateUserState$default($this$updatePermissionFlags.getNewState(), userId, 0, 2, null);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(mutateUserState$default);
        com.android.server.permission.access.immutable.MutableIntReferenceMap appIdPermissionFlags = mutateUserState$default.mutateAppIdPermissionFlags();
        com.android.server.permission.access.immutable.Immutable it$iv = appIdPermissionFlags.mutate(appId);
        if (it$iv == null) {
            it$iv = new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null);
            appIdPermissionFlags.put(appId, it$iv);
        }
        com.android.server.permission.access.immutable.MutableIndexedMap permissionFlags = (com.android.server.permission.access.immutable.MutableIndexedMap) it$iv;
        com.android.server.permission.access.immutable.IndexedMapExtensionsKt.putWithDefault(permissionFlags, permissionName, java.lang.Integer.valueOf(newFlags), 0);
        if (permissionFlags.isEmpty()) {
            com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt.minusAssign(appIdPermissionFlags, appId);
        }
        com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv = this.onPermissionFlagsChangedListeners;
        int size = $this$forEachIndexed$iv.getSize();
        int index$iv = 0;
        while (index$iv < size) {
            com.android.server.permission.access.permission.AppIdPermissionPolicy.OnPermissionFlagsChangedListener it = $this$forEachIndexed$iv.elementAt(index$iv);
            it.onPermissionFlagsChanged(appId, userId, permissionName, oldFlags, newFlags);
            index$iv++;
            size = size;
            $this$forEachIndexed$iv = $this$forEachIndexed$iv;
        }
        return true;
    }

    public final void addOnPermissionFlagsChangedListener(@org.jetbrains.annotations.NotNull com.android.server.permission.access.permission.AppIdPermissionPolicy.OnPermissionFlagsChangedListener listener) {
        synchronized (this.onPermissionFlagsChangedListenersLock) {
            this.onPermissionFlagsChangedListeners = com.android.server.permission.access.immutable.IndexedListSetExtensionsKt.plus(this.onPermissionFlagsChangedListeners, listener);
            com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void migrateSystemState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state) {
        this.migration.migrateSystemState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(state);
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void migrateUserState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
        this.migration.migrateUserState$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(state, userId);
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void upgradePackageState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$upgradePackageState, @org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState, int userId, int version) {
        com.android.server.permission.access.permission.AppIdPermissionUpgrade $this$upgradePackageState_u24lambda_u2482 = this.upgrade;
        $this$upgradePackageState_u24lambda_u2482.upgradePackageState($this$upgradePackageState, packageState, userId, version);
    }

    /* compiled from: AppIdPermissionPolicy.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
