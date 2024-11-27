package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public final class UserPermissionState {

    @android.annotation.NonNull
    private final android.util.ArraySet<java.lang.String> mInstallPermissionsFixed = new android.util.ArraySet<>();

    @android.annotation.NonNull
    private final android.util.SparseArray<com.android.server.pm.permission.UidPermissionState> mUidStates = new android.util.SparseArray<>();

    public boolean areInstallPermissionsFixed(@android.annotation.NonNull java.lang.String str) {
        return this.mInstallPermissionsFixed.contains(str);
    }

    public void setInstallPermissionsFixed(@android.annotation.NonNull java.lang.String str, boolean z) {
        if (z) {
            this.mInstallPermissionsFixed.add(str);
        } else {
            this.mInstallPermissionsFixed.remove(str);
        }
    }

    @android.annotation.Nullable
    public com.android.server.pm.permission.UidPermissionState getUidState(int i) {
        checkAppId(i);
        return this.mUidStates.get(i);
    }

    @android.annotation.NonNull
    public com.android.server.pm.permission.UidPermissionState getOrCreateUidState(int i) {
        checkAppId(i);
        com.android.server.pm.permission.UidPermissionState uidPermissionState = this.mUidStates.get(i);
        if (uidPermissionState == null) {
            com.android.server.pm.permission.UidPermissionState uidPermissionState2 = new com.android.server.pm.permission.UidPermissionState();
            this.mUidStates.put(i, uidPermissionState2);
            return uidPermissionState2;
        }
        return uidPermissionState;
    }

    @android.annotation.NonNull
    com.android.server.pm.permission.UidPermissionState createUidStateWithExisting(int i, @android.annotation.NonNull com.android.server.pm.permission.UidPermissionState uidPermissionState) {
        checkAppId(i);
        com.android.server.pm.permission.UidPermissionState uidPermissionState2 = new com.android.server.pm.permission.UidPermissionState(uidPermissionState);
        this.mUidStates.put(i, uidPermissionState2);
        return uidPermissionState2;
    }

    public void removeUidState(int i) {
        checkAppId(i);
        this.mUidStates.delete(i);
    }

    private void checkAppId(int i) {
        if (android.os.UserHandle.getUserId(i) != 0) {
            throw new java.lang.IllegalArgumentException("Invalid app ID " + i);
        }
    }
}
