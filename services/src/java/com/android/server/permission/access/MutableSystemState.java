package com.android.server.permission.access;

/* compiled from: AccessState.kt */
/* loaded from: classes2.dex */
public final class MutableSystemState extends com.android.server.permission.access.SystemState implements com.android.server.permission.access.WritableState {
    private MutableSystemState(com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, android.content.pm.PermissionGroupInfo>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, android.content.pm.PermissionGroupInfo>> mutableReference, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>> mutableReference2, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>> mutableReference3, int writeMode) {
        super(mutableReference, mutableReference2, mutableReference3, writeMode, null);
    }

    public MutableSystemState() {
        this(new com.android.server.permission.access.immutable.MutableReference(new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null)), new com.android.server.permission.access.immutable.MutableReference(new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null)), new com.android.server.permission.access.immutable.MutableReference(new com.android.server.permission.access.immutable.MutableIndexedMap(null, 1, null)), 0);
    }

    public MutableSystemState(@org.jetbrains.annotations.NotNull com.android.server.permission.access.SystemState systemState) {
        this(systemState.getPermissionGroupsReference().toImmutable(), systemState.getPermissionTreesReference().toImmutable(), systemState.getPermissionsReference().toImmutable(), 0);
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, android.content.pm.PermissionGroupInfo> mutatePermissionGroups() {
        return (com.android.server.permission.access.immutable.MutableIndexedMap) getPermissionGroupsReference().mutate();
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission> mutatePermissionTrees() {
        return (com.android.server.permission.access.immutable.MutableIndexedMap) getPermissionTreesReference().mutate();
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission> mutatePermissions() {
        return (com.android.server.permission.access.immutable.MutableIndexedMap) getPermissionsReference().mutate();
    }

    public void requestWriteMode(int writeMode) {
        setWriteMode(java.lang.Math.max(getWriteMode(), writeMode));
    }
}
