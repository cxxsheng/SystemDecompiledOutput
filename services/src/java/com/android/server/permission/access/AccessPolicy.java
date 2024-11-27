package com.android.server.permission.access;

/* compiled from: AccessPolicy.kt */
/* loaded from: classes2.dex */
public final class AccessPolicy {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.AccessPolicy.Companion Companion = new com.android.server.permission.access.AccessPolicy.Companion(null);
    private static final java.lang.String LOG_TAG = com.android.server.permission.access.AccessPolicy.class.getSimpleName();

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy>> schemePolicies;

    private AccessPolicy(com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy>> indexedMap) {
        this.schemePolicies = indexedMap;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AccessPolicy() {
        this($this$_init__u24lambda_u241);
        com.android.server.permission.access.immutable.MutableIndexedMap $this$_init__u24lambda_u241 = new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null);
        _init_$lambda$1$addPolicy($this$_init__u24lambda_u241, new com.android.server.permission.access.permission.AppIdPermissionPolicy());
        _init_$lambda$1$addPolicy($this$_init__u24lambda_u241, new com.android.server.permission.access.permission.DevicePermissionPolicy());
        _init_$lambda$1$addPolicy($this$_init__u24lambda_u241, new com.android.server.permission.access.appop.AppIdAppOpPolicy());
        _init_$lambda$1$addPolicy($this$_init__u24lambda_u241, new com.android.server.permission.access.appop.PackageAppOpPolicy());
    }

    private static final void _init_$lambda$1$addPolicy(com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy>> mutableIndexedMap, com.android.server.permission.access.SchemePolicy policy) {
        java.lang.String subjectScheme = policy.getSubjectScheme();
        com.android.server.permission.access.immutable.MutableIndexedMap mutableIndexedMap2 = mutableIndexedMap.get(subjectScheme);
        if (mutableIndexedMap2 == null) {
            mutableIndexedMap2 = new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null);
            mutableIndexedMap.put(subjectScheme, mutableIndexedMap2);
        }
        com.android.server.permission.access.immutable.MutableIndexedMap $this$set$iv = mutableIndexedMap2;
        $this$set$iv.put(policy.getObjectScheme(), policy);
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.SchemePolicy getSchemePolicy(@org.jetbrains.annotations.NotNull java.lang.String subjectScheme, @org.jetbrains.annotations.NotNull java.lang.String objectScheme) {
        com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> indexedMap = this.schemePolicies.get(subjectScheme);
        com.android.server.permission.access.SchemePolicy schemePolicy = indexedMap != null ? indexedMap.get(objectScheme) : null;
        if (schemePolicy == null) {
            throw new java.lang.IllegalStateException(("Scheme policy for " + subjectScheme + " and " + objectScheme + " does not exist").toString());
        }
        return schemePolicy;
    }

    public final void initialize(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntSet userIds, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map2, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntMap<java.lang.String[]> intMap, boolean isLeanback, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, com.android.server.SystemConfig.PermissionEntry> map3, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IndexedListSet<java.lang.String> indexedListSet, @org.jetbrains.annotations.NotNull com.android.server.pm.permission.PermissionAllowlist permissionAllowlist, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>> indexedMap) {
        int i;
        com.android.server.permission.access.MutableExternalState $this$initialize_u24lambda_u245 = state.mutateExternalState();
        int i2 = 0;
        com.android.server.permission.access.immutable.IntSetExtensionsKt.plusAssign($this$initialize_u24lambda_u245.mutateUserIds(), userIds);
        $this$initialize_u24lambda_u245.setPackageStatesPublic(map);
        $this$initialize_u24lambda_u245.setDisabledSystemPackageStatesPublic(map2);
        for (java.util.Map.Entry element$iv : map.entrySet()) {
            com.android.server.pm.pkg.PackageState packageState = element$iv.getValue();
            com.android.server.permission.access.immutable.MutableIntReferenceMap $this$mutateOrPut$iv = $this$initialize_u24lambda_u245.mutateAppIdPackageNames();
            int key$iv = packageState.getAppId();
            com.android.server.permission.access.immutable.Immutable mutate = $this$mutateOrPut$iv.mutate(key$iv);
            if (mutate != null) {
                i = i2;
            } else {
                i = i2;
                com.android.server.permission.access.immutable.Immutable it$iv = new com.android.server.permission.access.immutable.MutableIndexedListSet(null, 1, null);
                $this$mutateOrPut$iv.put(key$iv, it$iv);
                mutate = it$iv;
            }
            ((com.android.server.permission.access.immutable.MutableIndexedListSet) mutate).add(packageState.getPackageName());
            i2 = i;
        }
        $this$initialize_u24lambda_u245.setKnownPackagesPublic(intMap);
        $this$initialize_u24lambda_u245.setLeanbackPublic(isLeanback);
        $this$initialize_u24lambda_u245.setConfigPermissionsPublic(map3);
        $this$initialize_u24lambda_u245.setPrivilegedPermissionAllowlistPackagesPublic(indexedListSet);
        $this$initialize_u24lambda_u245.setPermissionAllowlistPublic(permissionAllowlist);
        $this$initialize_u24lambda_u245.setImplicitToSourcePermissionsPublic(indexedMap);
        com.android.server.permission.access.immutable.MutableIntReferenceMap $this$initialize_u24lambda_u247 = state.mutateUserStatesNoWrite();
        int size = userIds.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            int userId = userIds.elementAt(index$iv);
            com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt.set($this$initialize_u24lambda_u247, userId, new com.android.server.permission.access.MutableUserState());
        }
    }

    public final void onStateMutated(@org.jetbrains.annotations.NotNull com.android.server.permission.access.GetStateScope $this$onStateMutated) {
        com.android.server.permission.access.AccessPolicy this_$iv = this;
        int $i$f$forEachSchemePolicy = 0;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this_$iv.schemePolicies;
        int index$iv$iv = 0;
        int size = $this$forEachIndexed$iv$iv.getSize();
        while (index$iv$iv < size) {
            $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
            int index$iv$iv2 = 0;
            int size2 = objectSchemePolicies$iv.getSize();
            while (index$iv$iv2 < size2) {
                objectSchemePolicies$iv.keyAt(index$iv$iv2);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv = objectSchemePolicies$iv.valueAt(index$iv$iv2);
                schemePolicy$iv.onStateMutated($this$onStateMutated);
                index$iv$iv2++;
                $i$f$forEachSchemePolicy = $i$f$forEachSchemePolicy;
                this_$iv = this_$iv;
            }
            index$iv$iv++;
            this_$iv = this_$iv;
        }
    }

    public final void onInitialized(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onInitialized) {
        com.android.server.permission.access.AccessPolicy this_$iv = this;
        int $i$f$forEachSchemePolicy = 0;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this_$iv.schemePolicies;
        int index$iv$iv = 0;
        int size = $this$forEachIndexed$iv$iv.getSize();
        while (index$iv$iv < size) {
            $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
            int index$iv$iv2 = 0;
            int size2 = objectSchemePolicies$iv.getSize();
            while (index$iv$iv2 < size2) {
                objectSchemePolicies$iv.keyAt(index$iv$iv2);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv = objectSchemePolicies$iv.valueAt(index$iv$iv2);
                schemePolicy$iv.onInitialized($this$onInitialized);
                index$iv$iv2++;
                $i$f$forEachSchemePolicy = $i$f$forEachSchemePolicy;
                this_$iv = this_$iv;
            }
            index$iv$iv++;
            this_$iv = this_$iv;
        }
    }

    public final void onUserAdded(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onUserAdded, int userId) {
        com.android.server.permission.access.immutable.IntSetExtensionsKt.plusAssign($this$onUserAdded.getNewState().mutateExternalState().mutateUserIds(), userId);
        com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt.set($this$onUserAdded.getNewState().mutateUserStatesNoWrite(), userId, new com.android.server.permission.access.MutableUserState());
        com.android.server.permission.access.AccessPolicy this_$iv = this;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this_$iv.schemePolicies;
        int size = $this$forEachIndexed$iv$iv.getSize();
        for (int index$iv$iv = 0; index$iv$iv < size; index$iv$iv++) {
            $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
            int index$iv$iv2 = 0;
            int size2 = objectSchemePolicies$iv.getSize();
            while (index$iv$iv2 < size2) {
                objectSchemePolicies$iv.keyAt(index$iv$iv2);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv = objectSchemePolicies$iv.valueAt(index$iv$iv2);
                schemePolicy$iv.onUserAdded($this$onUserAdded, userId);
                index$iv$iv2++;
                this_$iv = this_$iv;
            }
        }
        java.util.Map $this$forEach$iv = $this$onUserAdded.getNewState().getExternalState().getPackageStates();
        for (java.util.Map.Entry element$iv : $this$forEach$iv.entrySet()) {
            com.android.server.pm.pkg.PackageState packageState = element$iv.getValue();
            upgradePackageVersion($this$onUserAdded, packageState, userId);
        }
    }

    public final void onUserRemoved(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onUserRemoved, int userId) {
        com.android.server.permission.access.immutable.IntSetExtensionsKt.minusAssign($this$onUserRemoved.getNewState().mutateExternalState().mutateUserIds(), userId);
        com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt.minusAssign($this$onUserRemoved.getNewState().mutateUserStatesNoWrite(), userId);
        com.android.server.permission.access.AccessPolicy this_$iv = this;
        int $i$f$forEachSchemePolicy = 0;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this_$iv.schemePolicies;
        int index$iv$iv = 0;
        int size = $this$forEachIndexed$iv$iv.getSize();
        while (index$iv$iv < size) {
            $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
            int index$iv$iv2 = 0;
            int size2 = objectSchemePolicies$iv.getSize();
            while (index$iv$iv2 < size2) {
                objectSchemePolicies$iv.keyAt(index$iv$iv2);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv = objectSchemePolicies$iv.valueAt(index$iv$iv2);
                schemePolicy$iv.onUserRemoved($this$onUserRemoved, userId);
                index$iv$iv2++;
                $i$f$forEachSchemePolicy = $i$f$forEachSchemePolicy;
                this_$iv = this_$iv;
            }
            index$iv$iv++;
            this_$iv = this_$iv;
        }
    }

    public final void onStorageVolumeMounted(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onStorageVolumeMounted, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map2, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntMap<java.lang.String[]> intMap, @org.jetbrains.annotations.Nullable java.lang.String volumeUuid, @org.jetbrains.annotations.NotNull java.util.List<java.lang.String> list, boolean isSystemUpdated) {
        boolean z;
        int i;
        com.android.server.permission.access.immutable.Immutable it$iv;
        java.lang.String str = volumeUuid;
        boolean z2 = true;
        com.android.server.permission.access.immutable.MutableIntSet addedAppIds = new com.android.server.permission.access.immutable.MutableIntSet(null, 1, null);
        com.android.server.permission.access.MutableExternalState $this$onStorageVolumeMounted_u24lambda_u2420 = $this$onStorageVolumeMounted.getNewState().mutateExternalState();
        int i2 = 0;
        $this$onStorageVolumeMounted_u24lambda_u2420.setPackageStatesPublic(map);
        $this$onStorageVolumeMounted_u24lambda_u2420.setDisabledSystemPackageStatesPublic(map2);
        for (java.util.Map.Entry element$iv : map.entrySet()) {
            java.lang.String packageName = element$iv.getKey();
            com.android.server.pm.pkg.PackageState packageState = element$iv.getValue();
            if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(packageState.getVolumeUuid(), str)) {
                if (!((packageState.getAndroidPackage() == null || list.contains(packageName)) ? z2 : false)) {
                    throw new java.lang.IllegalStateException(("Package " + packageName + " on storage volume " + str + " didn't receive onPackageAdded() before onStorageVolumeMounted()").toString());
                }
                int appId = packageState.getAppId();
                com.android.server.permission.access.immutable.MutableIntReferenceMap $this$mutateOrPut$iv = $this$onStorageVolumeMounted_u24lambda_u2420.mutateAppIdPackageNames();
                com.android.server.permission.access.immutable.Immutable it$iv2 = $this$mutateOrPut$iv.mutate(appId);
                if (it$iv2 != null) {
                    i = i2;
                    it$iv = it$iv2;
                    z = true;
                } else {
                    com.android.server.permission.access.immutable.IntSetExtensionsKt.plusAssign(addedAppIds, appId);
                    i = i2;
                    z = true;
                    it$iv = new com.android.server.permission.access.immutable.MutableIndexedListSet(null, 1, null);
                    $this$mutateOrPut$iv.put(appId, it$iv);
                }
                com.android.server.permission.access.immutable.MutableIndexedListSet $this$plusAssign$iv = (com.android.server.permission.access.immutable.MutableIndexedListSet) it$iv;
                $this$plusAssign$iv.add(packageName);
            } else {
                z = z2;
                i = i2;
            }
            z2 = z;
            i2 = i;
        }
        $this$onStorageVolumeMounted_u24lambda_u2420.setKnownPackagesPublic(intMap);
        int index$iv = 0;
        int size = addedAppIds.getSize();
        while (index$iv < size) {
            int appId2 = addedAppIds.elementAt(index$iv);
            com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this.schemePolicies;
            int index$iv$iv = 0;
            com.android.server.permission.access.immutable.MutableIntSet addedAppIds2 = addedAppIds;
            int size2 = $this$forEachIndexed$iv$iv.getSize();
            while (index$iv$iv < size2) {
                $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
                com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv2 = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
                int i3 = size2;
                int size3 = $this$forEachIndexed$iv$iv2.getSize();
                int index$iv$iv2 = 0;
                while (index$iv$iv2 < size3) {
                    int i4 = size3;
                    com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv3 = $this$forEachIndexed$iv$iv2;
                    $this$forEachIndexed$iv$iv3.keyAt(index$iv$iv2);
                    com.android.server.permission.access.SchemePolicy schemePolicy$iv = $this$forEachIndexed$iv$iv3.valueAt(index$iv$iv2);
                    schemePolicy$iv.onAppIdAdded($this$onStorageVolumeMounted, appId2);
                    index$iv$iv2++;
                    size3 = i4;
                    $this$forEachIndexed$iv$iv2 = $this$forEachIndexed$iv$iv3;
                }
                index$iv$iv++;
                size2 = i3;
            }
            index$iv++;
            addedAppIds = addedAppIds2;
        }
        com.android.server.permission.access.AccessPolicy this_$iv = this;
        int $i$f$forEachSchemePolicy = 0;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv4 = this_$iv.schemePolicies;
        int index$iv$iv3 = 0;
        int size4 = $this$forEachIndexed$iv$iv4.getSize();
        while (index$iv$iv3 < size4) {
            $this$forEachIndexed$iv$iv4.keyAt(index$iv$iv3);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv4.valueAt(index$iv$iv3);
            int index$iv$iv4 = 0;
            int size5 = objectSchemePolicies$iv.getSize();
            while (index$iv$iv4 < size5) {
                objectSchemePolicies$iv.keyAt(index$iv$iv4);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv2 = objectSchemePolicies$iv.valueAt(index$iv$iv4);
                schemePolicy$iv2.onStorageVolumeMounted($this$onStorageVolumeMounted, str, list, isSystemUpdated);
                index$iv$iv4++;
                $i$f$forEachSchemePolicy = $i$f$forEachSchemePolicy;
                this_$iv = this_$iv;
            }
            index$iv$iv3++;
            this_$iv = this_$iv;
        }
        java.util.Iterator<java.util.Map.Entry<java.lang.String, ? extends com.android.server.pm.pkg.PackageState>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            com.android.server.pm.pkg.PackageState packageState2 = it.next().getValue();
            if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(packageState2.getVolumeUuid(), str)) {
                com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv = $this$onStorageVolumeMounted.getNewState().getUserStates();
                int size6 = $this$forEachIndexed$iv.getSize();
                for (int index$iv2 = 0; index$iv2 < size6; index$iv2++) {
                    int userId = $this$forEachIndexed$iv.keyAt(index$iv2);
                    $this$forEachIndexed$iv.valueAt(index$iv2);
                    upgradePackageVersion($this$onStorageVolumeMounted, packageState2, userId);
                }
            }
            str = volumeUuid;
        }
    }

    public final void onPackageAdded(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageAdded, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map2, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntMap<java.lang.String[]> intMap, @org.jetbrains.annotations.NotNull java.lang.String packageName) {
        com.android.server.pm.pkg.PackageState packageState = map.get(packageName);
        if (packageState == null) {
            throw new java.lang.IllegalStateException(("Added package " + packageName + " isn't found in packageStates in onPackageAdded()").toString());
        }
        int appId = packageState.getAppId();
        boolean isAppIdAdded = false;
        com.android.server.permission.access.MutableExternalState $this$onPackageAdded_u24lambda_u2430 = $this$onPackageAdded.getNewState().mutateExternalState();
        $this$onPackageAdded_u24lambda_u2430.setPackageStatesPublic(map);
        $this$onPackageAdded_u24lambda_u2430.setDisabledSystemPackageStatesPublic(map2);
        com.android.server.permission.access.immutable.MutableIntReferenceMap $this$mutateOrPut$iv = $this$onPackageAdded_u24lambda_u2430.mutateAppIdPackageNames();
        com.android.server.permission.access.immutable.Immutable it$iv = $this$mutateOrPut$iv.mutate(appId);
        if (it$iv == null) {
            isAppIdAdded = true;
            it$iv = new com.android.server.permission.access.immutable.MutableIndexedListSet(null, 1, null);
            $this$mutateOrPut$iv.put(appId, it$iv);
        }
        com.android.server.permission.access.immutable.MutableIndexedListSet $this$plusAssign$iv = (com.android.server.permission.access.immutable.MutableIndexedListSet) it$iv;
        $this$plusAssign$iv.add(packageName);
        $this$onPackageAdded_u24lambda_u2430.setKnownPackagesPublic(intMap);
        if (isAppIdAdded) {
            com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this.schemePolicies;
            int index$iv$iv = 0;
            int size = $this$forEachIndexed$iv$iv.getSize();
            while (index$iv$iv < size) {
                $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
                com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv2 = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
                int size2 = $this$forEachIndexed$iv$iv2.getSize();
                boolean isAppIdAdded2 = isAppIdAdded;
                int index$iv$iv2 = 0;
                while (index$iv$iv2 < size2) {
                    int i = size2;
                    com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv3 = $this$forEachIndexed$iv$iv2;
                    $this$forEachIndexed$iv$iv3.keyAt(index$iv$iv2);
                    com.android.server.permission.access.SchemePolicy schemePolicy$iv = $this$forEachIndexed$iv$iv3.valueAt(index$iv$iv2);
                    schemePolicy$iv.onAppIdAdded($this$onPackageAdded, appId);
                    index$iv$iv2++;
                    size2 = i;
                    $this$forEachIndexed$iv$iv2 = $this$forEachIndexed$iv$iv3;
                }
                index$iv$iv++;
                isAppIdAdded = isAppIdAdded2;
            }
        }
        com.android.server.permission.access.AccessPolicy this_$iv = this;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv4 = this_$iv.schemePolicies;
        int index$iv$iv3 = 0;
        int size3 = $this$forEachIndexed$iv$iv4.getSize();
        while (index$iv$iv3 < size3) {
            $this$forEachIndexed$iv$iv4.keyAt(index$iv$iv3);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv4.valueAt(index$iv$iv3);
            com.android.server.permission.access.AccessPolicy this_$iv2 = this_$iv;
            int appId2 = appId;
            int appId3 = 0;
            for (int size4 = objectSchemePolicies$iv.getSize(); appId3 < size4; size4 = size4) {
                objectSchemePolicies$iv.keyAt(appId3);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv2 = objectSchemePolicies$iv.valueAt(appId3);
                schemePolicy$iv2.onPackageAdded($this$onPackageAdded, packageState);
                appId3++;
            }
            index$iv$iv3++;
            this_$iv = this_$iv2;
            appId = appId2;
        }
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv = $this$onPackageAdded.getNewState().getUserStates();
        int size5 = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size5; index$iv++) {
            int userId = $this$forEachIndexed$iv.keyAt(index$iv);
            $this$forEachIndexed$iv.valueAt(index$iv);
            upgradePackageVersion($this$onPackageAdded, packageState, userId);
        }
    }

    public final void onPackageRemoved(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageRemoved, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map2, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntMap<java.lang.String[]> intMap, @org.jetbrains.annotations.NotNull java.lang.String packageName, int appId) {
        if (!(!map.containsKey(packageName))) {
            throw new java.lang.IllegalStateException(("Removed package " + packageName + " is still in packageStates in onPackageRemoved()").toString());
        }
        boolean isAppIdRemoved = false;
        com.android.server.permission.access.MutableExternalState $this$onPackageRemoved_u24lambda_u2438 = $this$onPackageRemoved.getNewState().mutateExternalState();
        $this$onPackageRemoved_u24lambda_u2438.setPackageStatesPublic(map);
        $this$onPackageRemoved_u24lambda_u2438.setDisabledSystemPackageStatesPublic(map2);
        com.android.server.permission.access.immutable.MutableIndexedListSet $this$onPackageRemoved_u24lambda_u2438_u24lambda_u2437 = (com.android.server.permission.access.immutable.MutableIndexedListSet) $this$onPackageRemoved_u24lambda_u2438.mutateAppIdPackageNames().mutate(appId);
        if ($this$onPackageRemoved_u24lambda_u2438_u24lambda_u2437 != null) {
            $this$onPackageRemoved_u24lambda_u2438_u24lambda_u2437.remove(packageName);
            if ($this$onPackageRemoved_u24lambda_u2438_u24lambda_u2437.isEmpty()) {
                com.android.server.permission.access.immutable.IntReferenceMapExtensionsKt.minusAssign($this$onPackageRemoved_u24lambda_u2438.mutateAppIdPackageNames(), appId);
                isAppIdRemoved = true;
            }
        }
        $this$onPackageRemoved_u24lambda_u2438.setKnownPackagesPublic(intMap);
        com.android.server.permission.access.AccessPolicy this_$iv = this;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this_$iv.schemePolicies;
        int index$iv$iv = 0;
        int size = $this$forEachIndexed$iv$iv.getSize();
        while (index$iv$iv < size) {
            $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
            com.android.server.permission.access.AccessPolicy this_$iv2 = this_$iv;
            int index$iv$iv2 = 0;
            for (int size2 = objectSchemePolicies$iv.getSize(); index$iv$iv2 < size2; size2 = size2) {
                objectSchemePolicies$iv.keyAt(index$iv$iv2);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv = objectSchemePolicies$iv.valueAt(index$iv$iv2);
                schemePolicy$iv.onPackageRemoved($this$onPackageRemoved, packageName, appId);
                index$iv$iv2++;
            }
            index$iv$iv++;
            this_$iv = this_$iv2;
        }
        if (isAppIdRemoved) {
            com.android.server.permission.access.AccessPolicy this_$iv3 = this;
            com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv2 = this_$iv3.schemePolicies;
            int index$iv$iv3 = 0;
            int size3 = $this$forEachIndexed$iv$iv2.getSize();
            while (index$iv$iv3 < size3) {
                $this$forEachIndexed$iv$iv2.keyAt(index$iv$iv3);
                com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv2 = $this$forEachIndexed$iv$iv2.valueAt(index$iv$iv3);
                com.android.server.permission.access.AccessPolicy this_$iv4 = this_$iv3;
                boolean isAppIdRemoved2 = isAppIdRemoved;
                int index$iv$iv4 = 0;
                for (int size4 = objectSchemePolicies$iv2.getSize(); index$iv$iv4 < size4; size4 = size4) {
                    objectSchemePolicies$iv2.keyAt(index$iv$iv4);
                    com.android.server.permission.access.SchemePolicy schemePolicy$iv2 = objectSchemePolicies$iv2.valueAt(index$iv$iv4);
                    schemePolicy$iv2.onAppIdRemoved($this$onPackageRemoved, appId);
                    index$iv$iv4++;
                }
                index$iv$iv3++;
                this_$iv3 = this_$iv4;
                isAppIdRemoved = isAppIdRemoved2;
            }
        }
        com.android.server.permission.access.immutable.IntReferenceMap $this$forEachIndexed$iv = $this$onPackageRemoved.getNewState().getUserStates();
        int size5 = $this$forEachIndexed$iv.getSize();
        for (int index$iv = 0; index$iv < size5; index$iv++) {
            $this$forEachIndexed$iv.keyAt(index$iv);
            com.android.server.permission.access.UserState userState = $this$forEachIndexed$iv.valueAt(index$iv);
            int userStateIndex = index$iv;
            if (userState.getPackageVersions().contains(packageName)) {
                com.android.server.permission.access.immutable.MutableIndexedMap $this$minusAssign$iv = com.android.server.permission.access.MutableAccessState.mutateUserStateAt$default($this$onPackageRemoved.getNewState(), userStateIndex, 0, 2, null).mutatePackageVersions();
                $this$minusAssign$iv.remove(packageName);
            }
        }
    }

    public final void onPackageInstalled(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageInstalled, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map2, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntMap<java.lang.String[]> intMap, @org.jetbrains.annotations.NotNull java.lang.String packageName, int userId) {
        com.android.server.permission.access.MutableExternalState $this$onPackageInstalled_u24lambda_u2444 = $this$onPackageInstalled.getNewState().mutateExternalState();
        $this$onPackageInstalled_u24lambda_u2444.setPackageStatesPublic(map);
        $this$onPackageInstalled_u24lambda_u2444.setDisabledSystemPackageStatesPublic(map2);
        $this$onPackageInstalled_u24lambda_u2444.setKnownPackagesPublic(intMap);
        com.android.server.pm.pkg.PackageState packageState = map.get(packageName);
        if (packageState == null) {
            throw new java.lang.IllegalStateException(("Installed package " + packageName + " isn't found in packageStates in onPackageInstalled()").toString());
        }
        com.android.server.permission.access.AccessPolicy this_$iv = this;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this_$iv.schemePolicies;
        int index$iv$iv = 0;
        int size = $this$forEachIndexed$iv$iv.getSize();
        while (index$iv$iv < size) {
            $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
            int index$iv$iv2 = 0;
            for (int size2 = objectSchemePolicies$iv.getSize(); index$iv$iv2 < size2; size2 = size2) {
                objectSchemePolicies$iv.keyAt(index$iv$iv2);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv = objectSchemePolicies$iv.valueAt(index$iv$iv2);
                schemePolicy$iv.onPackageInstalled($this$onPackageInstalled, packageState, userId);
                index$iv$iv2++;
                this_$iv = this_$iv;
            }
            index$iv$iv++;
            this_$iv = this_$iv;
        }
    }

    public final void onPackageUninstalled(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onPackageUninstalled, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map2, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntMap<java.lang.String[]> intMap, @org.jetbrains.annotations.NotNull java.lang.String packageName, int appId, int userId) {
        com.android.server.permission.access.MutableExternalState $this$onPackageUninstalled_u24lambda_u2448 = $this$onPackageUninstalled.getNewState().mutateExternalState();
        $this$onPackageUninstalled_u24lambda_u2448.setPackageStatesPublic(map);
        $this$onPackageUninstalled_u24lambda_u2448.setDisabledSystemPackageStatesPublic(map2);
        $this$onPackageUninstalled_u24lambda_u2448.setKnownPackagesPublic(intMap);
        com.android.server.permission.access.AccessPolicy this_$iv = this;
        int $i$f$forEachSchemePolicy = 0;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this_$iv.schemePolicies;
        int index$iv$iv = 0;
        int size = $this$forEachIndexed$iv$iv.getSize();
        while (index$iv$iv < size) {
            $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
            int index$iv$iv2 = 0;
            int size2 = objectSchemePolicies$iv.getSize();
            while (index$iv$iv2 < size2) {
                objectSchemePolicies$iv.keyAt(index$iv$iv2);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv = objectSchemePolicies$iv.valueAt(index$iv$iv2);
                schemePolicy$iv.onPackageUninstalled($this$onPackageUninstalled, packageName, appId, userId);
                index$iv$iv2++;
                $i$f$forEachSchemePolicy = $i$f$forEachSchemePolicy;
                this_$iv = this_$iv;
            }
            index$iv$iv++;
            $i$f$forEachSchemePolicy = $i$f$forEachSchemePolicy;
            this_$iv = this_$iv;
        }
    }

    public final void onSystemReady(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutateStateScope $this$onSystemReady) {
        $this$onSystemReady.getNewState().mutateExternalState().setSystemReadyPublic(true);
        com.android.server.permission.access.AccessPolicy this_$iv = this;
        int $i$f$forEachSchemePolicy = 0;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this_$iv.schemePolicies;
        int index$iv$iv = 0;
        int size = $this$forEachIndexed$iv$iv.getSize();
        while (index$iv$iv < size) {
            $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
            int index$iv$iv2 = 0;
            int size2 = objectSchemePolicies$iv.getSize();
            while (index$iv$iv2 < size2) {
                objectSchemePolicies$iv.keyAt(index$iv$iv2);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv = objectSchemePolicies$iv.valueAt(index$iv$iv2);
                schemePolicy$iv.onSystemReady($this$onSystemReady);
                index$iv$iv2++;
                $i$f$forEachSchemePolicy = $i$f$forEachSchemePolicy;
                this_$iv = this_$iv;
            }
            index$iv$iv++;
            this_$iv = this_$iv;
        }
    }

    public final void migrateSystemState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state) {
        com.android.server.permission.access.AccessPolicy this_$iv = this;
        int $i$f$forEachSchemePolicy = 0;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this_$iv.schemePolicies;
        int index$iv$iv = 0;
        int size = $this$forEachIndexed$iv$iv.getSize();
        while (index$iv$iv < size) {
            $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
            int index$iv$iv2 = 0;
            int size2 = objectSchemePolicies$iv.getSize();
            while (index$iv$iv2 < size2) {
                objectSchemePolicies$iv.keyAt(index$iv$iv2);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv = objectSchemePolicies$iv.valueAt(index$iv$iv2);
                schemePolicy$iv.migrateSystemState(state);
                index$iv$iv2++;
                $i$f$forEachSchemePolicy = $i$f$forEachSchemePolicy;
                this_$iv = this_$iv;
            }
            index$iv$iv++;
            this_$iv = this_$iv;
        }
    }

    public final void migrateUserState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState state, int userId) {
        com.android.server.permission.access.AccessPolicy this_$iv = this;
        int $i$f$forEachSchemePolicy = 0;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this_$iv.schemePolicies;
        int index$iv$iv = 0;
        int size = $this$forEachIndexed$iv$iv.getSize();
        while (index$iv$iv < size) {
            $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
            int index$iv$iv2 = 0;
            int size2 = objectSchemePolicies$iv.getSize();
            while (index$iv$iv2 < size2) {
                objectSchemePolicies$iv.keyAt(index$iv$iv2);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv = objectSchemePolicies$iv.valueAt(index$iv$iv2);
                schemePolicy$iv.migrateUserState(state, userId);
                index$iv$iv2++;
                $i$f$forEachSchemePolicy = $i$f$forEachSchemePolicy;
                this_$iv = this_$iv;
                $this$forEachIndexed$iv$iv = $this$forEachIndexed$iv$iv;
            }
            index$iv$iv++;
            $i$f$forEachSchemePolicy = $i$f$forEachSchemePolicy;
            this_$iv = this_$iv;
        }
    }

    private final void upgradePackageVersion(com.android.server.permission.access.MutateStateScope $this$upgradePackageVersion, com.android.server.pm.pkg.PackageState packageState, int userId) {
        if (packageState.getAndroidPackage() == null) {
            return;
        }
        java.lang.String packageName = packageState.getPackageName();
        com.android.server.permission.access.UserState userState = $this$upgradePackageVersion.getNewState().getUserStates().get(userId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        java.lang.Integer version = userState.getPackageVersions().get(packageName);
        if (version == null) {
            com.android.server.permission.access.MutableUserState mutateUserState$default = com.android.server.permission.access.MutableAccessState.mutateUserState$default($this$upgradePackageVersion.getNewState(), userId, 0, 2, null);
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(mutateUserState$default);
            com.android.server.permission.access.immutable.MutableIndexedMap $this$set$iv = mutateUserState$default.mutatePackageVersions();
            $this$set$iv.put(packageName, 15);
            return;
        }
        if (version.intValue() >= 15) {
            if (version.intValue() != 15) {
                android.util.Slog.w(LOG_TAG, "Unexpected version " + version + " for package " + packageName + ",latest version is 15");
                return;
            }
            return;
        }
        com.android.server.permission.access.AccessPolicy this_$iv = this;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this_$iv.schemePolicies;
        int size = $this$forEachIndexed$iv$iv.getSize();
        for (int index$iv$iv = 0; index$iv$iv < size; index$iv$iv++) {
            $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
            int size2 = objectSchemePolicies$iv.getSize();
            int index$iv$iv2 = 0;
            while (index$iv$iv2 < size2) {
                objectSchemePolicies$iv.keyAt(index$iv$iv2);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv = objectSchemePolicies$iv.valueAt(index$iv$iv2);
                schemePolicy$iv.upgradePackageState($this$upgradePackageVersion, packageState, userId, version.intValue());
                index$iv$iv2++;
                size2 = size2;
                this_$iv = this_$iv;
            }
        }
        com.android.server.permission.access.MutableUserState mutateUserState$default2 = com.android.server.permission.access.MutableAccessState.mutateUserState$default($this$upgradePackageVersion.getNewState(), userId, 0, 2, null);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(mutateUserState$default2);
        com.android.server.permission.access.immutable.MutableIndexedMap $this$set$iv2 = mutateUserState$default2.mutatePackageVersions();
        $this$set$iv2.put(packageName, 15);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    public final void parseSystemState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser r36, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState r37) {
        /*
            Method dump skipped, instructions count: 730
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.AccessPolicy.parseSystemState(com.android.modules.utils.BinaryXmlPullParser, com.android.server.permission.access.MutableAccessState):void");
    }

    public final void serializeSystemState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer $this$serializeSystemState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state) {
        int $i$f$tag = 0;
        $this$serializeSystemState.startTag((java.lang.String) null, "access");
        int i = 0;
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this.schemePolicies;
        int index$iv$iv = 0;
        int size = $this$forEachIndexed$iv$iv.getSize();
        while (index$iv$iv < size) {
            $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
            int $i$f$tag2 = $i$f$tag;
            int $i$f$tag3 = 0;
            for (int size2 = objectSchemePolicies$iv.getSize(); $i$f$tag3 < size2; size2 = size2) {
                objectSchemePolicies$iv.keyAt($i$f$tag3);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv = objectSchemePolicies$iv.valueAt($i$f$tag3);
                schemePolicy$iv.serializeSystemState($this$serializeSystemState, state);
                $i$f$tag3++;
                i = i;
            }
            index$iv$iv++;
            $i$f$tag = $i$f$tag2;
        }
        $this$serializeSystemState.endTag((java.lang.String) null, "access");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    public final void parseUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlPullParser r37, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState r38, int r39) {
        /*
            Method dump skipped, instructions count: 764
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.AccessPolicy.parseUserState(com.android.modules.utils.BinaryXmlPullParser, com.android.server.permission.access.MutableAccessState, int):void");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:92)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private final void parsePackageVersions(com.android.modules.utils.BinaryXmlPullParser r17, com.android.server.permission.access.MutableAccessState r18, int r19) {
        /*
            Method dump skipped, instructions count: 420
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.permission.access.AccessPolicy.parsePackageVersions(com.android.modules.utils.BinaryXmlPullParser, com.android.server.permission.access.MutableAccessState, int):void");
    }

    private final void parsePackageVersion(com.android.modules.utils.BinaryXmlPullParser $this$parsePackageVersion, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, java.lang.Integer> mutableIndexedMap) {
        java.lang.String name$iv = $this$parsePackageVersion.getAttributeValue($this$parsePackageVersion.getAttributeIndexOrThrow((java.lang.String) null, "name"));
        java.lang.String packageName = name$iv.intern();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(packageName, "intern(...)");
        int version = $this$parsePackageVersion.getAttributeInt((java.lang.String) null, "version");
        mutableIndexedMap.put(packageName, java.lang.Integer.valueOf(version));
    }

    private final void parseDefaultPermissionGrant(com.android.modules.utils.BinaryXmlPullParser $this$parseDefaultPermissionGrant, com.android.server.permission.access.MutableAccessState state, int userId) {
        com.android.server.permission.access.MutableUserState userState = state.mutateUserState(userId, 0);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        java.lang.String name$iv = $this$parseDefaultPermissionGrant.getAttributeValue($this$parseDefaultPermissionGrant.getAttributeIndexOrThrow((java.lang.String) null, "fingerprint"));
        java.lang.String fingerprint = name$iv.intern();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(fingerprint, "intern(...)");
        userState.setDefaultPermissionGrantFingerprintPublic(fingerprint);
    }

    public final void serializeUserState(@org.jetbrains.annotations.NotNull com.android.modules.utils.BinaryXmlSerializer $this$serializeUserState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state, int userId) {
        int $i$f$tag = 0;
        $this$serializeUserState.startTag((java.lang.String) null, "access");
        com.android.server.permission.access.UserState userState = state.getUserStates().get(userId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState);
        serializePackageVersions($this$serializeUserState, userState.getPackageVersions());
        com.android.server.permission.access.UserState userState2 = state.getUserStates().get(userId);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(userState2);
        serializeDefaultPermissionGrantFingerprint($this$serializeUserState, userState2.getDefaultPermissionGrantFingerprint());
        com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv = this.schemePolicies;
        int index$iv$iv = 0;
        int size = $this$forEachIndexed$iv$iv.getSize();
        while (index$iv$iv < size) {
            $this$forEachIndexed$iv$iv.keyAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.SchemePolicy> objectSchemePolicies$iv = $this$forEachIndexed$iv$iv.valueAt(index$iv$iv);
            com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv2 = objectSchemePolicies$iv;
            int size2 = $this$forEachIndexed$iv$iv2.getSize();
            int index$iv$iv2 = 0;
            while (index$iv$iv2 < size2) {
                int $i$f$tag2 = $i$f$tag;
                com.android.server.permission.access.immutable.IndexedMap $this$forEachIndexed$iv$iv3 = $this$forEachIndexed$iv$iv2;
                $this$forEachIndexed$iv$iv3.keyAt(index$iv$iv2);
                com.android.server.permission.access.SchemePolicy schemePolicy$iv = $this$forEachIndexed$iv$iv3.valueAt(index$iv$iv2);
                schemePolicy$iv.serializeUserState($this$serializeUserState, state, userId);
                index$iv$iv2++;
                $i$f$tag = $i$f$tag2;
                size2 = size2;
                $this$forEachIndexed$iv$iv2 = $this$forEachIndexed$iv$iv3;
            }
            index$iv$iv++;
            $i$f$tag = $i$f$tag;
        }
        $this$serializeUserState.endTag((java.lang.String) null, "access");
    }

    private final void serializePackageVersions(com.android.modules.utils.BinaryXmlSerializer $this$serializePackageVersions, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, java.lang.Integer> indexedMap) {
        int $i$f$tag = 0;
        java.lang.String str = null;
        $this$serializePackageVersions.startTag((java.lang.String) null, "package-versions");
        com.android.modules.utils.BinaryXmlSerializer $this$serializePackageVersions_u24lambda_u2477 = $this$serializePackageVersions;
        int index$iv = 0;
        int size = indexedMap.getSize();
        while (index$iv < size) {
            java.lang.String keyAt = indexedMap.keyAt(index$iv);
            int version = indexedMap.valueAt(index$iv).intValue();
            java.lang.String packageName = keyAt;
            com.android.modules.utils.BinaryXmlSerializer $this$tag$iv = $this$serializePackageVersions_u24lambda_u2477;
            $this$tag$iv.startTag(str, com.android.server.pm.Settings.ATTR_PACKAGE);
            com.android.modules.utils.BinaryXmlSerializer $this$attributeInterned$iv = $this$serializePackageVersions_u24lambda_u2477;
            $this$tag$iv.attributeInterned((java.lang.String) null, "name", packageName);
            $this$tag$iv.attributeInt((java.lang.String) null, "version", version);
            $this$tag$iv.endTag((java.lang.String) null, com.android.server.pm.Settings.ATTR_PACKAGE);
            index$iv++;
            $this$serializePackageVersions_u24lambda_u2477 = $this$attributeInterned$iv;
            $i$f$tag = $i$f$tag;
            str = null;
        }
        $this$serializePackageVersions.endTag((java.lang.String) null, "package-versions");
    }

    private final void serializeDefaultPermissionGrantFingerprint(com.android.modules.utils.BinaryXmlSerializer $this$serializeDefaultPermissionGrantFingerprint, java.lang.String fingerprint) {
        if (fingerprint != null) {
            $this$serializeDefaultPermissionGrantFingerprint.startTag((java.lang.String) null, "default-permission-grant");
            $this$serializeDefaultPermissionGrantFingerprint.attributeInterned((java.lang.String) null, "fingerprint", fingerprint);
            $this$serializeDefaultPermissionGrantFingerprint.endTag((java.lang.String) null, "default-permission-grant");
        }
    }

    /* compiled from: AccessPolicy.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
