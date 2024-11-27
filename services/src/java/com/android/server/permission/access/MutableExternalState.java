package com.android.server.permission.access;

/* compiled from: AccessState.kt */
/* loaded from: classes2.dex */
public final class MutableExternalState extends com.android.server.permission.access.ExternalState {
    private MutableExternalState(com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntSet, com.android.server.permission.access.immutable.MutableIntSet> mutableReference, java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map, java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map2, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IntReferenceMap<com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>, com.android.server.permission.access.immutable.MutableIndexedListSet<java.lang.String>>, com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>, com.android.server.permission.access.immutable.MutableIndexedListSet<java.lang.String>>> mutableReference2, com.android.server.permission.access.immutable.IntMap<java.lang.String[]> intMap, boolean isLeanback, java.util.Map<java.lang.String, com.android.server.SystemConfig.PermissionEntry> map3, com.android.server.permission.access.immutable.IndexedListSet<java.lang.String> indexedListSet, com.android.server.pm.permission.PermissionAllowlist permissionAllowlist, com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>> indexedMap, boolean isSystemReady) {
        super(mutableReference, map, map2, mutableReference2, intMap, isLeanback, map3, indexedListSet, permissionAllowlist, indexedMap, isSystemReady, null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public MutableExternalState() {
        this(r1, r4, r5, r6, r7, false, r8, new com.android.server.permission.access.immutable.MutableIndexedListSet(null, 1, null), new com.android.server.pm.permission.PermissionAllowlist(), new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null), false);
        java.util.Map emptyMap;
        java.util.Map emptyMap2;
        java.util.Map emptyMap3;
        com.android.server.permission.access.immutable.MutableReference mutableReference = new com.android.server.permission.access.immutable.MutableReference(new com.android.server.permission.access.immutable.MutableIntSet(null, 1, null));
        emptyMap = com.android.server.permission.jarjar.kotlin.collections.MapsKt__MapsKt.emptyMap();
        emptyMap2 = com.android.server.permission.jarjar.kotlin.collections.MapsKt__MapsKt.emptyMap();
        com.android.server.permission.access.immutable.MutableReference mutableReference2 = new com.android.server.permission.access.immutable.MutableReference(new com.android.server.permission.access.immutable.MutableIntReferenceMap(null, 1, null));
        com.android.server.permission.access.immutable.MutableIntMap mutableIntMap = new com.android.server.permission.access.immutable.MutableIntMap(null, 1, null);
        emptyMap3 = com.android.server.permission.jarjar.kotlin.collections.MapsKt__MapsKt.emptyMap();
    }

    public MutableExternalState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.ExternalState externalState) {
        this(externalState.getUserIdsReference().toImmutable(), externalState.getPackageStates(), externalState.getDisabledSystemPackageStates(), externalState.getAppIdPackageNamesReference().toImmutable(), externalState.getKnownPackages(), externalState.isLeanback(), externalState.getConfigPermissions(), externalState.getPrivilegedPermissionAllowlistPackages(), externalState.getPermissionAllowlist(), externalState.getImplicitToSourcePermissions(), externalState.isSystemReady());
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableIntSet mutateUserIds() {
        return (com.android.server.permission.access.immutable.MutableIntSet) getUserIdsReference().mutate();
    }

    public final void setPackageStatesPublic(@org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map) {
        setPackageStates(map);
    }

    public final void setDisabledSystemPackageStatesPublic(@org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, ? extends com.android.server.pm.pkg.PackageState> map) {
        setDisabledSystemPackageStates(map);
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableIntReferenceMap<com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>, com.android.server.permission.access.immutable.MutableIndexedListSet<java.lang.String>> mutateAppIdPackageNames() {
        return (com.android.server.permission.access.immutable.MutableIntReferenceMap) getAppIdPackageNamesReference().mutate();
    }

    public final void setKnownPackagesPublic(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntMap<java.lang.String[]> intMap) {
        setKnownPackages(intMap);
    }

    public final void setLeanbackPublic(boolean isLeanback) {
        setLeanback(isLeanback);
    }

    public final void setConfigPermissionsPublic(@org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, com.android.server.SystemConfig.PermissionEntry> map) {
        setConfigPermissions(map);
    }

    public final void setPrivilegedPermissionAllowlistPackagesPublic(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IndexedListSet<java.lang.String> indexedListSet) {
        setPrivilegedPermissionAllowlistPackages(indexedListSet);
    }

    public final void setPermissionAllowlistPublic(@org.jetbrains.annotations.NotNull com.android.server.pm.permission.PermissionAllowlist permissionAllowlist) {
        setPermissionAllowlist(permissionAllowlist);
    }

    public final void setImplicitToSourcePermissionsPublic(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.immutable.IndexedListSet<java.lang.String>> indexedMap) {
        setImplicitToSourcePermissions(indexedMap);
    }

    public final void setSystemReadyPublic(boolean isSystemReady) {
        setSystemReady(isSystemReady);
    }
}
