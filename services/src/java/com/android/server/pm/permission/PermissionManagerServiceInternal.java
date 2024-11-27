package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public interface PermissionManagerServiceInternal extends android.permission.PermissionManagerInternal, com.android.server.pm.permission.LegacyPermissionDataProvider {

    public interface HotwordDetectionServiceProvider {
        int getUid();
    }

    int checkPermission(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3, int i);

    int checkUidPermission(int i, @android.annotation.NonNull java.lang.String str, int i2);

    @android.annotation.NonNull
    java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtection(int i);

    @android.annotation.NonNull
    java.util.List<android.content.pm.PermissionInfo> getAllPermissionsWithProtectionFlags(int i);

    @android.annotation.NonNull
    java.lang.String[] getAppOpPermissionPackages(@android.annotation.NonNull java.lang.String str);

    @android.annotation.Nullable
    java.lang.String getDefaultPermissionGrantFingerprint(int i);

    @android.annotation.NonNull
    java.util.List<java.lang.String> getDelegatedShellPermissions();

    @android.annotation.NonNull
    java.util.Set<java.lang.String> getGrantedPermissions(@android.annotation.NonNull java.lang.String str, int i);

    @android.annotation.Nullable
    com.android.server.pm.permission.PermissionManagerServiceInternal.HotwordDetectionServiceProvider getHotwordDetectionServiceProvider();

    @android.annotation.NonNull
    java.util.Set<java.lang.String> getInstalledPermissions(@android.annotation.NonNull java.lang.String str);

    @android.annotation.NonNull
    int[] getPermissionGids(@android.annotation.NonNull java.lang.String str, int i);

    @android.annotation.Nullable
    com.android.server.pm.permission.Permission getPermissionTEMP(@android.annotation.NonNull java.lang.String str);

    boolean isPermissionsReviewRequired(@android.annotation.NonNull java.lang.String str, int i);

    void onPackageAdded(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, boolean z, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage);

    void onPackageInstalled(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i, @android.annotation.NonNull com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams packageInstalledParams, int i2);

    void onPackageRemoved(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage);

    void onPackageUninstalled(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.Nullable com.android.server.pm.pkg.PackageState packageState, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.AndroidPackage> list, int i2);

    void onStorageVolumeMounted(@android.annotation.NonNull java.lang.String str, boolean z);

    void onSystemReady();

    void onUserCreated(int i);

    void onUserRemoved(int i);

    void readLegacyPermissionStateTEMP();

    void readLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings);

    void resetRuntimePermissions(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i);

    void resetRuntimePermissionsForUser(int i);

    void setDefaultPermissionGrantFingerprint(@android.annotation.NonNull java.lang.String str, int i);

    void setHotwordDetectionServiceProvider(@android.annotation.Nullable com.android.server.pm.permission.PermissionManagerServiceInternal.HotwordDetectionServiceProvider hotwordDetectionServiceProvider);

    void startShellPermissionIdentityDelegation(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.util.List<java.lang.String> list);

    void stopShellPermissionIdentityDelegation();

    void writeLegacyPermissionStateTEMP();

    void writeLegacyPermissionsTEMP(@android.annotation.NonNull com.android.server.pm.permission.LegacyPermissionSettings legacyPermissionSettings);

    public static final class PackageInstalledParams {
        public static final com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams DEFAULT = new com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams.Builder().build();

        @android.annotation.NonNull
        private final java.util.List<java.lang.String> mAllowlistedRestrictedPermissions;

        @android.annotation.NonNull
        private final int mAutoRevokePermissionsMode;

        @android.annotation.NonNull
        private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mPermissionStates;

        private PackageInstalledParams(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap, @android.annotation.NonNull java.util.List<java.lang.String> list, int i) {
            this.mPermissionStates = arrayMap;
            this.mAllowlistedRestrictedPermissions = list;
            this.mAutoRevokePermissionsMode = i;
        }

        @android.annotation.NonNull
        public android.util.ArrayMap<java.lang.String, java.lang.Integer> getPermissionStates() {
            return this.mPermissionStates;
        }

        @android.annotation.NonNull
        public java.util.List<java.lang.String> getAllowlistedRestrictedPermissions() {
            return this.mAllowlistedRestrictedPermissions;
        }

        public int getAutoRevokePermissionsMode() {
            return this.mAutoRevokePermissionsMode;
        }

        public static final class Builder {

            @android.annotation.Nullable
            private android.util.ArrayMap<java.lang.String, java.lang.Integer> mPermissionStates = null;

            @android.annotation.NonNull
            private java.util.List<java.lang.String> mAllowlistedRestrictedPermissions = java.util.Collections.emptyList();

            @android.annotation.NonNull
            private int mAutoRevokePermissionsMode = 3;

            public com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams.Builder setPermissionStates(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap) {
                java.util.Objects.requireNonNull(arrayMap);
                this.mPermissionStates = arrayMap;
                return this;
            }

            public void setAllowlistedRestrictedPermissions(@android.annotation.NonNull java.util.List<java.lang.String> list) {
                java.util.Objects.requireNonNull(list);
                this.mAllowlistedRestrictedPermissions = new java.util.ArrayList(list);
            }

            public void setAutoRevokePermissionsMode(int i) {
                this.mAutoRevokePermissionsMode = i;
            }

            @android.annotation.NonNull
            public com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams build() {
                return new com.android.server.pm.permission.PermissionManagerServiceInternal.PackageInstalledParams(this.mPermissionStates == null ? new android.util.ArrayMap<>() : this.mPermissionStates, this.mAllowlistedRestrictedPermissions, this.mAutoRevokePermissionsMode);
            }
        }
    }
}
