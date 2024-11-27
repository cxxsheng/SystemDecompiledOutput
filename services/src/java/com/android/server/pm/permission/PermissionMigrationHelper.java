package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public interface PermissionMigrationHelper {
    int getLegacyPermissionStateVersion(int i);

    @android.annotation.NonNull
    java.util.Map<java.lang.Integer, java.util.Map<java.lang.String, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermissionState>> getLegacyPermissionStates(int i);

    @android.annotation.NonNull
    java.util.Map<java.lang.String, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermission> getLegacyPermissionTrees();

    @android.annotation.NonNull
    java.util.Map<java.lang.String, com.android.server.pm.permission.PermissionMigrationHelper.LegacyPermission> getLegacyPermissions();

    boolean hasLegacyPermission();

    boolean hasLegacyPermissionState(int i);

    public static final class LegacyPermission {
        private final android.content.pm.PermissionInfo mPermissionInfo;
        private final int mType;

        LegacyPermission(android.content.pm.PermissionInfo permissionInfo, int i) {
            this.mPermissionInfo = permissionInfo;
            this.mType = i;
        }

        @android.annotation.NonNull
        public android.content.pm.PermissionInfo getPermissionInfo() {
            return this.mPermissionInfo;
        }

        public int getType() {
            return this.mType;
        }
    }

    public static final class LegacyPermissionState {
        private final int mFlags;
        private final boolean mGranted;

        LegacyPermissionState(boolean z, int i) {
            this.mGranted = z;
            this.mFlags = i;
        }

        public boolean isGranted() {
            return this.mGranted;
        }

        public int getFlags() {
            return this.mFlags;
        }
    }
}
