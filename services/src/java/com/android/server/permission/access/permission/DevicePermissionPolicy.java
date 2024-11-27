package com.android.server.permission.access.permission;

/* compiled from: DevicePermissionPolicy.kt */
/* loaded from: classes2.dex */
public final class DevicePermissionPolicy extends com.android.server.permission.access.SchemePolicy {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.permission.DevicePermissionPolicy.Companion Companion = new com.android.server.permission.access.permission.DevicePermissionPolicy.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.permission.DevicePermissionPolicy.class.getSimpleName();

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.permission.DevicePermissionPersistence persistence = new com.android.server.permission.access.permission.DevicePermissionPersistence();

    @org.jetbrains.annotations.NotNull
    private volatile com.android.server.permission.access.immutable.IndexedListSet<com.android.server.permission.access.permission.DevicePermissionPolicy.OnDevicePermissionFlagsChangedListener> listeners = new com.android.server.permission.access.immutable.MutableIndexedListSet(null, 1, null);

    @org.jetbrains.annotations.NotNull
    private final java.lang.Object listenersLock = new java.lang.Object();

    /* compiled from: DevicePermissionPolicy.kt */
    public interface OnDevicePermissionFlagsChangedListener {
        void onDevicePermissionFlagsChanged(int i, int i2, @org.jetbrains.annotations.NotNull java.lang.String str, @org.jetbrains.annotations.NotNull java.lang.String str2, int i3, int i4);

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
        return "device-permission";
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onStateMutated(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$onStateMutated) {
        com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv = this.listeners;
        int size = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            com.android.server.permission.access.permission.DevicePermissionPolicy.OnDevicePermissionFlagsChangedListener it = $this$forEachIndexed$iv.elementAt(index$iv);
            it.onStateMutated();
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
            if (userState.getAppIdDevicePermissionFlags().contains(appId)) {
                com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt.minusAssign(com.android.server.permission.access.MutableAccessState.mutateUserStateAt$default($this$onAppIdRemoved.getNewState(), userStateIndex, 0, 2, null).mutateAppIdDevicePermissionFlags(), appId);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void trimDevicePermissionStates(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$trimDevicePermissionStates, @org.jetbrains.annotations.NotNull java.util.Set<java.lang.String> set) {
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv;
        int $i$f$forEachIndexed;
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv2 = $this$trimDevicePermissionStates.getNewState().getUserStates();
        int $i$f$forEachIndexed2 = 0;
        int size = $this$forEachIndexed$iv2.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            int userId = $this$forEachIndexed$iv2.keyAt(index$iv);
            com.android.server.permission.access.UserState userState = $this$forEachIndexed$iv2.valueAt(index$iv);
            com.android.server.permission.access.immutable.IntReferenceMap $this$forEachReversedIndexed$iv = userState.getAppIdDevicePermissionFlags();
            int index$iv2 = $this$forEachReversedIndexed$iv.getSize() - 1;
            while (-1 < index$iv2) {
                int appId = $this$forEachReversedIndexed$iv.keyAt(index$iv2);
                $this$forEachReversedIndexed$iv.valueAt(index$iv2);
                com.android.server.permission.access.MutableUserState mutateUserState$default = com.android.server.permission.access.MutableAccessState.mutateUserState$default($this$trimDevicePermissionStates.getNewState(), userId, 0, 2, null);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(mutateUserState$default);
                com.android.server.permission.access.immutable.MutableIntReferenceMap appIdDevicePermissionFlags = mutateUserState$default.mutateAppIdDevicePermissionFlags();
                com.android.server.permission.access.immutable.MutableIndexedReferenceMap devicePermissionFlags = (com.android.server.permission.access.immutable.MutableIndexedReferenceMap) appIdDevicePermissionFlags.mutate(appId);
                if (devicePermissionFlags == null) {
                    $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                    $i$f$forEachIndexed = $i$f$forEachIndexed2;
                } else {
                    $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                    int index$iv3 = devicePermissionFlags.getSize() - 1;
                    while (true) {
                        $i$f$forEachIndexed = $i$f$forEachIndexed2;
                        if (-1 < index$iv3) {
                            K keyAt = devicePermissionFlags.keyAt(index$iv3);
                            java.lang.String deviceId = (java.lang.String) keyAt;
                            int i = size;
                            if (!set.contains(deviceId)) {
                                devicePermissionFlags.remove(deviceId);
                            }
                            index$iv3--;
                            $i$f$forEachIndexed2 = $i$f$forEachIndexed;
                            size = i;
                        }
                    }
                }
                index$iv2--;
                $i$f$forEachIndexed2 = $i$f$forEachIndexed;
                $this$forEachIndexed$iv2 = $this$forEachIndexed$iv;
                size = size;
            }
        }
    }

    public final void onDeviceIdRemoved(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onDeviceIdRemoved, @org.jetbrains.annotations.NotNull java.lang.String deviceId) {
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv;
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv2 = $this$onDeviceIdRemoved.getNewState().getUserStates();
        int index$iv = 0;
        int size = $this$forEachIndexed$iv2.getSize();
        while (index$iv < size) {
            int userId = $this$forEachIndexed$iv2.keyAt(index$iv);
            com.android.server.permission.access.UserState userState = $this$forEachIndexed$iv2.valueAt(index$iv);
            com.android.server.permission.access.immutable.IntReferenceMap $this$forEachReversedIndexed$iv = userState.getAppIdDevicePermissionFlags();
            int index$iv2 = $this$forEachReversedIndexed$iv.getSize() - 1;
            while (-1 < index$iv2) {
                int appId = $this$forEachReversedIndexed$iv.keyAt(index$iv2);
                $this$forEachReversedIndexed$iv.valueAt(index$iv2);
                com.android.server.permission.access.MutableUserState mutateUserState$default = com.android.server.permission.access.MutableAccessState.mutateUserState$default($this$onDeviceIdRemoved.getNewState(), userId, 0, 2, null);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(mutateUserState$default);
                com.android.server.permission.access.immutable.MutableIntReferenceMap appIdDevicePermissionFlags = mutateUserState$default.mutateAppIdDevicePermissionFlags();
                com.android.server.permission.access.immutable.MutableIndexedReferenceMap devicePermissionFlags = (com.android.server.permission.access.immutable.MutableIndexedReferenceMap) appIdDevicePermissionFlags.mutate(appId);
                if (devicePermissionFlags == null) {
                    $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                } else {
                    $this$forEachIndexed$iv = $this$forEachIndexed$iv2;
                    devicePermissionFlags.remove(deviceId);
                }
                index$iv2--;
                $this$forEachIndexed$iv2 = $this$forEachIndexed$iv;
            }
            index$iv++;
            $this$forEachIndexed$iv2 = $this$forEachIndexed$iv2;
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onStorageVolumeMounted(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onStorageVolumeMounted, @org.jetbrains.annotations.Nullable java.lang.String volumeUuid, @org.jetbrains.annotations.NotNull java.util.List<java.lang.String> list, boolean isSystemUpdated) {
        int size = list.size();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            java.lang.String packageName = list.get(index$iv);
            com.android.server.pm.pkg.PackageState packageState = $this$onStorageVolumeMounted.getNewState().getExternalState().getPackageStates().get(packageName);
            if (packageState != null) {
                trimPermissionStates($this$onStorageVolumeMounted, packageState.getAppId());
            }
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onPackageAdded(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageAdded, @org.jetbrains.annotations.NotNull com.android.server.pm.pkg.PackageState packageState) {
        trimPermissionStates($this$onPackageAdded, packageState.getAppId());
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onPackageRemoved(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageRemoved, @org.jetbrains.annotations.NotNull java.lang.String packageName, int appId) {
        if ($this$onPackageRemoved.getNewState().getExternalState().getAppIdPackageNames().contains(appId)) {
            trimPermissionStates($this$onPackageRemoved, appId);
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void onPackageUninstalled(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageUninstalled, @org.jetbrains.annotations.NotNull java.lang.String packageName, int appId, int userId) {
        resetRuntimePermissions($this$onPackageUninstalled, packageName, userId);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void resetRuntimePermissions(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$resetRuntimePermissions, @org.jetbrains.annotations.NotNull java.lang.String packageName, int userId) {
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        com.android.server.permission.access.immutable.IndexedReferenceMap devicePermissionFlags;
        int index$iv$iv$iv;
        boolean z;
        com.android.server.pm.pkg.PackageState packageState = $this$resetRuntimePermissions.getNewState().getExternalState().getPackageStates().get(packageName);
        if (packageState == null || (androidPackage = packageState.getAndroidPackage()) == null) {
            return;
        }
        int appId = packageState.getAppId();
        com.android.server.permission.access.UserState userState = $this$resetRuntimePermissions.getNewState().getUserStates().get(userId);
        if (userState == null || (devicePermissionFlags = userState.getAppIdDevicePermissionFlags().get(appId)) == null) {
            return;
        }
        java.lang.Iterable $this$forEach$iv = androidPackage.getRequestedPermissions();
        for (java.lang.Object element$iv : $this$forEach$iv) {
            java.lang.String permissionName = (java.lang.String) element$iv;
            com.android.server.permission.access.AccessState state$iv = $this$resetRuntimePermissions.getNewState();
            com.android.server.permission.access.immutable.IndexedListSet indexedListSet = state$iv.getExternalState().getAppIdPackageNames().get(appId);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(indexedListSet);
            com.android.server.permission.access.immutable.IndexedListSet packageNames$iv = indexedListSet;
            com.android.server.pm.pkg.PackageState packageState2 = packageState;
            int size = packageNames$iv.getSize();
            com.android.server.pm.pkg.AndroidPackage androidPackage2 = androidPackage;
            int index$iv$iv$iv2 = 0;
            while (true) {
                index$iv$iv$iv = 0;
                if (index$iv$iv$iv2 >= size) {
                    break;
                }
                java.lang.Object element$iv$iv = packageNames$iv.elementAt(index$iv$iv$iv2);
                int i = size;
                java.lang.String packageName$iv = (java.lang.String) element$iv$iv;
                com.android.server.permission.access.AccessState state$iv2 = state$iv;
                com.android.server.pm.pkg.PackageState packageState3 = state$iv.getExternalState().getPackageStates().get(packageName$iv);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(packageState3);
                com.android.server.pm.pkg.PackageState packageState$iv = packageState3;
                if (packageState$iv.getAndroidPackage() != null) {
                    java.lang.String packageName$iv2 = packageState$iv.getPackageName();
                    if (!com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(packageName$iv2, packageName)) {
                        com.android.server.pm.pkg.AndroidPackage androidPackage3 = packageState$iv.getAndroidPackage();
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(androidPackage3);
                        if (androidPackage3.getRequestedPermissions().contains(permissionName)) {
                            z = true;
                            if (z) {
                                index$iv$iv$iv = 1;
                            }
                        }
                    }
                    z = false;
                    if (z) {
                    }
                }
                if (index$iv$iv$iv != 0) {
                    index$iv$iv$iv = 1;
                    break;
                } else {
                    index$iv$iv$iv2++;
                    size = i;
                    state$iv = state$iv2;
                }
            }
            if (index$iv$iv$iv == 0) {
                int size2 = devicePermissionFlags.getSize();
                int index$iv = 0;
                while (index$iv < size2) {
                    java.lang.String keyAt = devicePermissionFlags.keyAt(index$iv);
                    devicePermissionFlags.valueAt(index$iv);
                    java.lang.String deviceId = keyAt;
                    setPermissionFlags($this$resetRuntimePermissions, appId, deviceId, userId, permissionName, 0);
                    index$iv++;
                    size2 = size2;
                    permissionName = permissionName;
                }
            }
            packageState = packageState2;
            androidPackage = androidPackage2;
        }
    }

    private final void trimPermissionStates(com.android.server.permission.access.MutateStateScope $this$trimPermissionStates, int appId) {
        int index$iv;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachReversedIndexed$iv;
        int i;
        int index$iv2;
        com.android.server.permission.access.immutable.IndexedReferenceMap $this$forEachReversedIndexed$iv2;
        int i2 = 1;
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
        int index$iv3 = 0;
        while (index$iv3 < size2) {
            int userId = $this$forEachIndexed$iv.keyAt(index$iv3);
            com.android.server.permission.access.UserState userState = $this$forEachIndexed$iv.valueAt(index$iv3);
            com.android.server.permission.access.immutable.IndexedReferenceMap $this$lastIndex$iv$iv = userState.getAppIdDevicePermissionFlags().get(appId);
            if ($this$lastIndex$iv$iv != null) {
                com.android.server.permission.access.immutable.IndexedReferenceMap $this$forEachReversedIndexed$iv3 = $this$lastIndex$iv$iv;
                int index$iv4 = $this$lastIndex$iv$iv.getSize() - i2;
                while (true) {
                    int i3 = -1;
                    if (-1 < index$iv4) {
                        java.lang.String keyAt = $this$forEachReversedIndexed$iv3.keyAt(index$iv4);
                        com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> permissionFlags = $this$forEachReversedIndexed$iv3.valueAt(index$iv4);
                        java.lang.String deviceId = keyAt;
                        com.android.server.permission.access.immutable.IndexedMap $this$forEachReversedIndexed$iv4 = permissionFlags;
                        int index$iv5 = $this$forEachReversedIndexed$iv4.getSize() - 1;
                        while (i3 < index$iv5) {
                            java.lang.String keyAt2 = $this$forEachReversedIndexed$iv4.keyAt(index$iv5);
                            $this$forEachReversedIndexed$iv4.valueAt(index$iv5).intValue();
                            java.lang.String permissionName = keyAt2;
                            if (requestedPermissions.contains(permissionName)) {
                                index$iv = index$iv5;
                                $this$forEachReversedIndexed$iv = $this$forEachReversedIndexed$iv4;
                                i = i3;
                                index$iv2 = index$iv4;
                                $this$forEachReversedIndexed$iv2 = $this$forEachReversedIndexed$iv3;
                            } else {
                                index$iv = index$iv5;
                                $this$forEachReversedIndexed$iv = $this$forEachReversedIndexed$iv4;
                                i = i3;
                                index$iv2 = index$iv4;
                                $this$forEachReversedIndexed$iv2 = $this$forEachReversedIndexed$iv3;
                                setPermissionFlags($this$trimPermissionStates, appId, deviceId, userId, permissionName, 0);
                            }
                            index$iv5 = index$iv - 1;
                            $this$forEachReversedIndexed$iv4 = $this$forEachReversedIndexed$iv;
                            i3 = i;
                            index$iv4 = index$iv2;
                            $this$forEachReversedIndexed$iv3 = $this$forEachReversedIndexed$iv2;
                        }
                        index$iv4--;
                    }
                }
            }
            index$iv3++;
            i2 = 1;
        }
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void parseUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser $this$parseUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
        com.android.server.permission.access.permission.DevicePermissionPersistence $this$parseUserState_u24lambda_u2417 = this.persistence;
        $this$parseUserState_u24lambda_u2417.parseUserState($this$parseUserState, state, userId);
    }

    @Override // com.android.server.permission.access.SchemePolicy
    public void serializeUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer $this$serializeUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state, int userId) {
        com.android.server.permission.access.permission.DevicePermissionPersistence $this$serializeUserState_u24lambda_u2418 = this.persistence;
        $this$serializeUserState_u24lambda_u2418.serializeUserState($this$serializeUserState, state, userId);
    }

    public final int getPermissionFlags(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$getPermissionFlags, int appId, @org.jetbrains.annotations.NotNull java.lang.String deviceId, int userId, @org.jetbrains.annotations.NotNull java.lang.String permissionName) {
        com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>, com.android.server.permission.access.immutable.MutableIndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>> appIdDevicePermissionFlags;
        com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> indexedReferenceMap;
        com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> indexedMap;
        com.android.server.permission.access.UserState userState = $this$getPermissionFlags.getState().getUserStates().get(userId);
        int i = 0;
        if (userState != null && (appIdDevicePermissionFlags = userState.getAppIdDevicePermissionFlags()) != null && (indexedReferenceMap = appIdDevicePermissionFlags.get(appId)) != null && (indexedMap = indexedReferenceMap.get(deviceId)) != null) {
            i = ((java.lang.Number) com.android.server.permission.access.immutable.IndexedMapExtensionsKt.getWithDefault(indexedMap, permissionName, 0)).intValue();
        }
        int flags = i;
        return flags;
    }

    @org.jetbrains.annotations.Nullable
    public final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> getAllPermissionFlags(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$getAllPermissionFlags, int appId, @org.jetbrains.annotations.NotNull java.lang.String persistentDeviceId, int userId) {
        com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>, com.android.server.permission.access.immutable.MutableIndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>>> appIdDevicePermissionFlags;
        com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> indexedReferenceMap;
        com.android.server.permission.access.UserState userState = $this$getAllPermissionFlags.getState().getUserStates().get(userId);
        if (userState == null || (appIdDevicePermissionFlags = userState.getAppIdDevicePermissionFlags()) == null || (indexedReferenceMap = appIdDevicePermissionFlags.get(appId)) == null) {
            return null;
        }
        return indexedReferenceMap.get(persistentDeviceId);
    }

    public final boolean setPermissionFlags(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$setPermissionFlags, int appId, @org.jetbrains.annotations.NotNull java.lang.String deviceId, int userId, @org.jetbrains.annotations.NotNull java.lang.String permissionName, int flags) {
        return updatePermissionFlags($this$setPermissionFlags, appId, deviceId, userId, permissionName, -1, flags);
    }

    private final boolean updatePermissionFlags(com.android.server.permission.access.MutateStateScope $this$updatePermissionFlags, int appId, java.lang.String deviceId, int userId, java.lang.String permissionName, int flagMask, int flagValues) {
        com.android.server.permission.access.immutable.Immutable it$iv;
        if (!$this$updatePermissionFlags.getNewState().getUserStates().contains(userId)) {
            android.util.Slog.e(LOG_TAG, "Unable to update permission flags for missing user " + userId);
            return false;
        }
        com.android.server.permission.access.UserState userState = $this$updatePermissionFlags.getNewState().getUserStates().get(userId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        com.android.server.permission.access.immutable.IndexedReferenceMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer>> indexedReferenceMap = userState.getAppIdDevicePermissionFlags().get(appId);
        int oldFlags = ((java.lang.Number) com.android.server.permission.access.immutable.IndexedMapExtensionsKt.getWithDefault(indexedReferenceMap != null ? indexedReferenceMap.get(deviceId) : null, permissionName, 0)).intValue();
        int newFlags = com.android.server.permission.access.util.IntExtensionsKt.andInv(oldFlags, flagMask) | (flagValues & flagMask);
        if (oldFlags == newFlags) {
            return false;
        }
        com.android.server.permission.access.MutableUserState mutateUserState$default = com.android.server.permission.access.MutableAccessState.mutateUserState$default($this$updatePermissionFlags.getNewState(), userId, 0, 2, null);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(mutateUserState$default);
        com.android.server.permission.access.immutable.MutableIntReferenceMap $this$mutateOrPut$iv = mutateUserState$default.mutateAppIdDevicePermissionFlags();
        com.android.server.permission.access.immutable.Immutable it$iv2 = $this$mutateOrPut$iv.mutate(appId);
        if (it$iv2 == null) {
            it$iv2 = new com.android.server.permission.access.immutable.MutableIndexedReferenceMap(null, 1, null);
            $this$mutateOrPut$iv.put(appId, it$iv2);
        }
        com.android.server.permission.access.immutable.MutableIndexedReferenceMap devicePermissionFlags = (com.android.server.permission.access.immutable.MutableIndexedReferenceMap) it$iv2;
        com.android.server.permission.access.immutable.Immutable mutate = devicePermissionFlags.mutate(deviceId);
        if (mutate != null) {
            it$iv = mutate;
        } else {
            it$iv = new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null);
            devicePermissionFlags.put(deviceId, it$iv);
        }
        com.android.server.permission.access.immutable.MutableIndexedMap permissionFlags = (com.android.server.permission.access.immutable.MutableIndexedMap) it$iv;
        com.android.server.permission.access.immutable.IndexedMapExtensionsKt.putWithDefault(permissionFlags, permissionName, java.lang.Integer.valueOf(newFlags), 0);
        if (permissionFlags.isEmpty()) {
            devicePermissionFlags.remove(deviceId);
            if (devicePermissionFlags.isEmpty()) {
                com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt.minusAssign($this$mutateOrPut$iv, appId);
            }
        }
        com.android.server.permission.access.immutable.IndexedListSet $this$forEachIndexed$iv = this.listeners;
        int size = $this$forEachIndexed$iv.getSize();
        int index$iv = 0;
        while (index$iv < size) {
            com.android.server.permission.access.permission.DevicePermissionPolicy.OnDevicePermissionFlagsChangedListener it = $this$forEachIndexed$iv.elementAt(index$iv);
            it.onDevicePermissionFlagsChanged(appId, userId, deviceId, permissionName, oldFlags, newFlags);
            index$iv++;
            size = size;
            $this$forEachIndexed$iv = $this$forEachIndexed$iv;
            permissionFlags = permissionFlags;
        }
        return true;
    }

    public final void addOnPermissionFlagsChangedListener(@org.jetbrains.annotations.NotNull com.android.server.permission.access.permission.DevicePermissionPolicy.OnDevicePermissionFlagsChangedListener listener) {
        synchronized (this.listenersLock) {
            this.listeners = com.android.server.permission.access.immutable.IndexedListSetExtensionsKt.plus(this.listeners, listener);
            com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
        }
    }

    /* compiled from: DevicePermissionPolicy.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
