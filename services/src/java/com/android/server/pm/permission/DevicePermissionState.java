package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public final class DevicePermissionState {
    private final android.util.SparseArray<com.android.server.pm.permission.UserPermissionState> mUserStates = new android.util.SparseArray<>();

    @android.annotation.Nullable
    public com.android.server.pm.permission.UserPermissionState getUserState(int i) {
        return this.mUserStates.get(i);
    }

    @android.annotation.NonNull
    public com.android.server.pm.permission.UserPermissionState getOrCreateUserState(int i) {
        com.android.server.pm.permission.UserPermissionState userPermissionState = this.mUserStates.get(i);
        if (userPermissionState == null) {
            com.android.server.pm.permission.UserPermissionState userPermissionState2 = new com.android.server.pm.permission.UserPermissionState();
            this.mUserStates.put(i, userPermissionState2);
            return userPermissionState2;
        }
        return userPermissionState;
    }

    public void removeUserState(int i) {
        this.mUserStates.delete(i);
    }

    public int[] getUserIds() {
        int size = this.mUserStates.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = this.mUserStates.keyAt(i);
        }
        return iArr;
    }
}
