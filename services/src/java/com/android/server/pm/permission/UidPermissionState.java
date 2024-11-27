package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public final class UidPermissionState {
    private boolean mMissing;

    @android.annotation.Nullable
    private android.util.ArrayMap<java.lang.String, com.android.server.pm.permission.PermissionState> mPermissions;

    public UidPermissionState() {
    }

    public UidPermissionState(@android.annotation.NonNull com.android.server.pm.permission.UidPermissionState uidPermissionState) {
        this.mMissing = uidPermissionState.mMissing;
        if (uidPermissionState.mPermissions != null) {
            this.mPermissions = new android.util.ArrayMap<>();
            int size = uidPermissionState.mPermissions.size();
            for (int i = 0; i < size; i++) {
                this.mPermissions.put(uidPermissionState.mPermissions.keyAt(i), new com.android.server.pm.permission.PermissionState(uidPermissionState.mPermissions.valueAt(i)));
            }
        }
    }

    public void reset() {
        this.mMissing = false;
        this.mPermissions = null;
        invalidateCache();
    }

    public boolean isMissing() {
        return this.mMissing;
    }

    public void setMissing(boolean z) {
        this.mMissing = z;
    }

    @java.lang.Deprecated
    public boolean hasPermissionState(@android.annotation.NonNull java.lang.String str) {
        return this.mPermissions != null && this.mPermissions.containsKey(str);
    }

    @java.lang.Deprecated
    public boolean hasPermissionState(@android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet) {
        if (this.mPermissions == null) {
            return false;
        }
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            if (this.mPermissions.containsKey(arraySet.valueAt(i))) {
                return true;
            }
        }
        return false;
    }

    @android.annotation.Nullable
    public com.android.server.pm.permission.PermissionState getPermissionState(@android.annotation.NonNull java.lang.String str) {
        if (this.mPermissions == null) {
            return null;
        }
        return this.mPermissions.get(str);
    }

    @android.annotation.NonNull
    private com.android.server.pm.permission.PermissionState getOrCreatePermissionState(@android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        if (this.mPermissions == null) {
            this.mPermissions = new android.util.ArrayMap<>();
        }
        java.lang.String name = permission.getName();
        com.android.server.pm.permission.PermissionState permissionState = this.mPermissions.get(name);
        if (permissionState == null) {
            com.android.server.pm.permission.PermissionState permissionState2 = new com.android.server.pm.permission.PermissionState(permission);
            this.mPermissions.put(name, permissionState2);
            return permissionState2;
        }
        return permissionState;
    }

    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.permission.PermissionState> getPermissionStates() {
        if (this.mPermissions == null) {
            return java.util.Collections.emptyList();
        }
        return new java.util.ArrayList(this.mPermissions.values());
    }

    public void putPermissionState(@android.annotation.NonNull com.android.server.pm.permission.Permission permission, boolean z, int i) {
        java.lang.String name = permission.getName();
        if (this.mPermissions == null) {
            this.mPermissions = new android.util.ArrayMap<>();
        } else {
            this.mPermissions.remove(name);
        }
        com.android.server.pm.permission.PermissionState permissionState = new com.android.server.pm.permission.PermissionState(permission);
        if (z) {
            permissionState.grant();
        }
        permissionState.updateFlags(i, i);
        this.mPermissions.put(name, permissionState);
    }

    public boolean removePermissionState(@android.annotation.NonNull java.lang.String str) {
        if (this.mPermissions == null) {
            return false;
        }
        boolean z = this.mPermissions.remove(str) != null;
        if (z && this.mPermissions.isEmpty()) {
            this.mPermissions = null;
        }
        return z;
    }

    public boolean isPermissionGranted(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.permission.PermissionState permissionState = getPermissionState(str);
        return permissionState != null && permissionState.isGranted();
    }

    @android.annotation.NonNull
    public java.util.Set<java.lang.String> getGrantedPermissions() {
        if (this.mPermissions == null) {
            return java.util.Collections.emptySet();
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(this.mPermissions.size());
        int size = this.mPermissions.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.permission.PermissionState valueAt = this.mPermissions.valueAt(i);
            if (valueAt.isGranted()) {
                arraySet.add(valueAt.getName());
            }
        }
        return arraySet;
    }

    public boolean grantPermission(@android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        return getOrCreatePermissionState(permission).grant();
    }

    public boolean revokePermission(@android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        java.lang.String name = permission.getName();
        com.android.server.pm.permission.PermissionState permissionState = getPermissionState(name);
        if (permissionState == null) {
            return false;
        }
        boolean revoke = permissionState.revoke();
        if (revoke && permissionState.isDefault()) {
            removePermissionState(name);
        }
        return revoke;
    }

    public int getPermissionFlags(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.permission.PermissionState permissionState = getPermissionState(str);
        if (permissionState == null) {
            return 0;
        }
        return permissionState.getFlags();
    }

    public boolean updatePermissionFlags(@android.annotation.NonNull com.android.server.pm.permission.Permission permission, int i, int i2) {
        if (i == 0) {
            return false;
        }
        com.android.server.pm.permission.PermissionState orCreatePermissionState = getOrCreatePermissionState(permission);
        boolean updateFlags = orCreatePermissionState.updateFlags(i, i2);
        if (updateFlags && orCreatePermissionState.isDefault()) {
            removePermissionState(permission.getName());
        }
        return updateFlags;
    }

    public boolean updatePermissionFlagsForAllPermissions(int i, int i2) {
        boolean z = false;
        if (i == 0 || this.mPermissions == null) {
            return false;
        }
        for (int size = this.mPermissions.size() - 1; size >= 0; size--) {
            com.android.server.pm.permission.PermissionState valueAt = this.mPermissions.valueAt(size);
            boolean updateFlags = valueAt.updateFlags(i, i2);
            if (updateFlags && valueAt.isDefault()) {
                this.mPermissions.removeAt(size);
            }
            z |= updateFlags;
        }
        return z;
    }

    public boolean isPermissionsReviewRequired() {
        if (this.mPermissions == null) {
            return false;
        }
        int size = this.mPermissions.size();
        for (int i = 0; i < size; i++) {
            if ((this.mPermissions.valueAt(i).getFlags() & 64) != 0) {
                return true;
            }
        }
        return false;
    }

    @android.annotation.NonNull
    public int[] computeGids(@android.annotation.NonNull int[] iArr, int i) {
        android.util.IntArray wrap = android.util.IntArray.wrap(iArr);
        if (this.mPermissions == null) {
            return wrap.toArray();
        }
        int size = this.mPermissions.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.pm.permission.PermissionState valueAt = this.mPermissions.valueAt(i2);
            if (valueAt.isGranted()) {
                int[] computeGids = valueAt.computeGids(i);
                if (computeGids.length != 0) {
                    wrap.addAll(computeGids);
                }
            }
        }
        return wrap.toArray();
    }

    static void invalidateCache() {
        android.content.pm.PackageManager.invalidatePackageInfoCache();
    }
}
