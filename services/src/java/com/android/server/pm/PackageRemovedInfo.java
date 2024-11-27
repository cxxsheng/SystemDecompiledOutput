package com.android.server.pm;

/* loaded from: classes2.dex */
final class PackageRemovedInfo {
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    android.util.SparseArray<int[]> mBroadcastAllowList;
    boolean mDataRemoved;
    android.util.SparseIntArray mInstallReasons;
    java.lang.String mInstallerPackageName;
    boolean mIsExternal;
    boolean mIsStaticSharedLib;
    boolean mIsUpdate;
    int[] mOrigUsers;
    boolean mRemovedForAllUsers;
    java.lang.String mRemovedPackage;
    long mRemovedPackageVersionCode;
    android.util.SparseIntArray mUninstallReasons;
    int mUid = -1;
    boolean mIsAppIdRemoved = false;
    int[] mRemovedUsers = null;
    int[] mBroadcastUsers = null;
    int[] mInstantUserIds = null;
    boolean mIsRemovedPackageSystemUpdate = false;
    com.android.server.pm.CleanUpArgs mArgs = null;

    PackageRemovedInfo() {
    }

    public void populateBroadcastUsers(com.android.server.pm.PackageSetting packageSetting) {
        if (this.mRemovedUsers == null) {
            this.mBroadcastUsers = null;
            return;
        }
        this.mBroadcastUsers = EMPTY_INT_ARRAY;
        this.mInstantUserIds = EMPTY_INT_ARRAY;
        for (int length = this.mRemovedUsers.length - 1; length >= 0; length--) {
            int i = this.mRemovedUsers[length];
            if (packageSetting.getInstantApp(i)) {
                this.mInstantUserIds = com.android.internal.util.ArrayUtils.appendInt(this.mInstantUserIds, i);
            } else {
                this.mBroadcastUsers = com.android.internal.util.ArrayUtils.appendInt(this.mBroadcastUsers, i);
            }
        }
    }
}
