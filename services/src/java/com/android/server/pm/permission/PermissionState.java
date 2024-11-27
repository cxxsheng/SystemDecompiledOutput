package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public final class PermissionState {

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mFlags;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mGranted;
    private final java.lang.Object mLock;

    @android.annotation.NonNull
    private final com.android.server.pm.permission.Permission mPermission;

    public PermissionState(@android.annotation.NonNull com.android.server.pm.permission.Permission permission) {
        this.mLock = new java.lang.Object();
        this.mPermission = permission;
    }

    public PermissionState(@android.annotation.NonNull com.android.server.pm.permission.PermissionState permissionState) {
        this(permissionState.mPermission);
        this.mGranted = permissionState.mGranted;
        this.mFlags = permissionState.mFlags;
    }

    @android.annotation.NonNull
    public com.android.server.pm.permission.Permission getPermission() {
        return this.mPermission;
    }

    @android.annotation.NonNull
    public java.lang.String getName() {
        return this.mPermission.getName();
    }

    @android.annotation.NonNull
    public int[] computeGids(int i) {
        return this.mPermission.computeGids(i);
    }

    public boolean isGranted() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mGranted;
        }
        return z;
    }

    public boolean grant() {
        synchronized (this.mLock) {
            try {
                if (this.mGranted) {
                    return false;
                }
                this.mGranted = true;
                com.android.server.pm.permission.UidPermissionState.invalidateCache();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean revoke() {
        synchronized (this.mLock) {
            try {
                if (!this.mGranted) {
                    return false;
                }
                this.mGranted = false;
                com.android.server.pm.permission.UidPermissionState.invalidateCache();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getFlags() {
        int i;
        synchronized (this.mLock) {
            i = this.mFlags;
        }
        return i;
    }

    public boolean updateFlags(int i, int i2) {
        boolean z;
        synchronized (this.mLock) {
            int i3 = i2 & i;
            com.android.server.pm.permission.UidPermissionState.invalidateCache();
            int i4 = this.mFlags;
            this.mFlags = ((~i) & this.mFlags) | i3;
            z = this.mFlags != i4;
        }
        return z;
    }

    public boolean isDefault() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = !this.mGranted && this.mFlags == 0;
            } finally {
            }
        }
        return z;
    }
}
