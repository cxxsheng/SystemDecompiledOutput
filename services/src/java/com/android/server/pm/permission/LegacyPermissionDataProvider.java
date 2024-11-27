package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public interface LegacyPermissionDataProvider {
    @android.annotation.NonNull
    java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getAllAppOpPermissionPackages();

    @android.annotation.NonNull
    int[] getGidsForUid(int i);

    @android.annotation.NonNull
    com.android.server.pm.permission.LegacyPermissionState getLegacyPermissionState(int i);

    @android.annotation.NonNull
    java.util.List<com.android.server.pm.permission.LegacyPermission> getLegacyPermissions();

    void writeLegacyPermissionStateTEMP();
}
