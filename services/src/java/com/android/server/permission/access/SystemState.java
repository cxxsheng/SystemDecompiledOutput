package com.android.server.permission.access;

/* compiled from: AccessState.kt */
/* loaded from: classes2.dex */
public abstract class SystemState implements com.android.server.permission.access.WritableState, com.android.server.permission.access.immutable.Immutable<com.android.server.permission.access.MutableSystemState> {

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, android.content.pm.PermissionGroupInfo>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, android.content.pm.PermissionGroupInfo>> permissionGroupsReference;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>> permissionTreesReference;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>> permissionsReference;
    private int writeMode;

    public /* synthetic */ SystemState(com.android.server.permission.access.immutable.MutableReference mutableReference, com.android.server.permission.access.immutable.MutableReference mutableReference2, com.android.server.permission.access.immutable.MutableReference mutableReference3, int i, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableReference, mutableReference2, mutableReference3, i);
    }

    private SystemState(com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, android.content.pm.PermissionGroupInfo>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, android.content.pm.PermissionGroupInfo>> mutableReference, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>> mutableReference2, com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>> mutableReference3, int writeMode) {
        this.permissionGroupsReference = mutableReference;
        this.permissionTreesReference = mutableReference2;
        this.permissionsReference = mutableReference3;
        this.writeMode = writeMode;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, android.content.pm.PermissionGroupInfo>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, android.content.pm.PermissionGroupInfo>> getPermissionGroupsReference() {
        return this.permissionGroupsReference;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>> getPermissionTreesReference() {
        return this.permissionTreesReference;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableReference<com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>, com.android.server.permission.access.immutable.MutableIndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission>> getPermissionsReference() {
        return this.permissionsReference;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, android.content.pm.PermissionGroupInfo> getPermissionGroups() {
        return this.permissionGroupsReference.get();
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission> getPermissionTrees() {
        return this.permissionTreesReference.get();
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.IndexedMap<java.lang.String, com.android.server.permission.access.permission.Permission> getPermissions() {
        return this.permissionsReference.get();
    }

    @Override // com.android.server.permission.access.WritableState
    public int getWriteMode() {
        return this.writeMode;
    }

    protected void setWriteMode(int i) {
        this.writeMode = i;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.permission.access.immutable.Immutable
    @org.jetbrains.annotations.NotNull
    public com.android.server.permission.access.MutableSystemState toMutable() {
        return new com.android.server.permission.access.MutableSystemState(this);
    }
}
