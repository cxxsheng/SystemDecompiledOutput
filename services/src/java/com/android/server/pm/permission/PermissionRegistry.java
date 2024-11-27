package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public class PermissionRegistry {
    private final android.util.ArrayMap<java.lang.String, com.android.server.pm.permission.Permission> mPermissions = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, com.android.server.pm.permission.Permission> mPermissionTrees = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, com.android.internal.pm.pkg.component.ParsedPermissionGroup> mPermissionGroups = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> mAppOpPermissionPackages = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    public java.util.Collection<com.android.server.pm.permission.Permission> getPermissions() {
        return this.mPermissions.values();
    }

    @android.annotation.Nullable
    public com.android.server.pm.permission.Permission getPermission(@android.annotation.NonNull java.lang.String str) {
        return this.mPermissions.get(str);
    }

    public void addPermission(@android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        this.mPermissions.put(permission.getName(), permission);
    }

    public void removePermission(@android.annotation.NonNull java.lang.String str) {
        this.mPermissions.remove(str);
    }

    @android.annotation.NonNull
    public java.util.Collection<com.android.server.pm.permission.Permission> getPermissionTrees() {
        return this.mPermissionTrees.values();
    }

    @android.annotation.Nullable
    public com.android.server.pm.permission.Permission getPermissionTree(@android.annotation.NonNull java.lang.String str) {
        return this.mPermissionTrees.get(str);
    }

    public void addPermissionTree(@android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        this.mPermissionTrees.put(permission.getName(), permission);
    }

    public void transferPermissions(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        int i = 0;
        while (i < 2) {
            java.util.Iterator<com.android.server.pm.permission.Permission> it = (i == 0 ? this.mPermissionTrees : this.mPermissions).values().iterator();
            while (it.hasNext()) {
                it.next().transfer(str, str2);
            }
            i++;
        }
    }

    @android.annotation.NonNull
    public java.util.Collection<com.android.internal.pm.pkg.component.ParsedPermissionGroup> getPermissionGroups() {
        return this.mPermissionGroups.values();
    }

    @android.annotation.Nullable
    public com.android.internal.pm.pkg.component.ParsedPermissionGroup getPermissionGroup(@android.annotation.NonNull java.lang.String str) {
        return this.mPermissionGroups.get(str);
    }

    public void addPermissionGroup(@android.annotation.NonNull com.android.internal.pm.pkg.component.ParsedPermissionGroup parsedPermissionGroup) {
        this.mPermissionGroups.put(parsedPermissionGroup.getName(), parsedPermissionGroup);
    }

    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> getAllAppOpPermissionPackages() {
        return this.mAppOpPermissionPackages;
    }

    @android.annotation.Nullable
    public android.util.ArraySet<java.lang.String> getAppOpPermissionPackages(@android.annotation.NonNull java.lang.String str) {
        return this.mAppOpPermissionPackages.get(str);
    }

    public void addAppOpPermissionPackage(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.util.ArraySet<java.lang.String> arraySet = this.mAppOpPermissionPackages.get(str);
        if (arraySet == null) {
            arraySet = new android.util.ArraySet<>();
            this.mAppOpPermissionPackages.put(str, arraySet);
        }
        arraySet.add(str2);
    }

    public void removeAppOpPermissionPackage(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.util.ArraySet<java.lang.String> arraySet = this.mAppOpPermissionPackages.get(str);
        if (arraySet != null && arraySet.remove(str2) && arraySet.isEmpty()) {
            this.mAppOpPermissionPackages.remove(str);
        }
    }

    @android.annotation.NonNull
    public com.android.server.pm.permission.Permission enforcePermissionTree(@android.annotation.NonNull java.lang.String str, int i) {
        return com.android.server.pm.permission.Permission.enforcePermissionTree(this.mPermissionTrees.values(), str, i);
    }
}
