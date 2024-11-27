package com.android.server.pm;

/* loaded from: classes2.dex */
final class DeletePackageAction {
    public final com.android.server.pm.PackageSetting mDeletingPs;
    public final com.android.server.pm.PackageSetting mDisabledPs;
    public final int mFlags;

    @android.annotation.NonNull
    public final com.android.server.pm.PackageRemovedInfo mRemovedInfo;
    public final android.os.UserHandle mUser;

    DeletePackageAction(com.android.server.pm.PackageSetting packageSetting, com.android.server.pm.PackageSetting packageSetting2, @android.annotation.NonNull com.android.server.pm.PackageRemovedInfo packageRemovedInfo, int i, android.os.UserHandle userHandle) {
        this.mDeletingPs = packageSetting;
        this.mDisabledPs = packageSetting2;
        this.mRemovedInfo = packageRemovedInfo;
        this.mFlags = i;
        this.mUser = userHandle;
    }
}
