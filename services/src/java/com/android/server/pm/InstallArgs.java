package com.android.server.pm;

/* loaded from: classes2.dex */
final class InstallArgs {
    final java.lang.String mAbiOverride;
    final java.util.List<java.lang.String> mAllowlistedRestrictedPermissions;
    final boolean mApplicationEnabledSettingPersistent;
    final int mAutoRevokePermissionsMode;
    java.io.File mCodeFile;
    final int mDataLoaderType;
    final int mDevelopmentInstallFlags;
    final boolean mForceQueryableOverride;
    final int mInstallFlags;
    final int mInstallReason;
    final int mInstallScenario;

    @android.annotation.NonNull
    final com.android.server.pm.InstallSource mInstallSource;

    @android.annotation.Nullable
    final java.lang.String[] mInstructionSets;
    final com.android.server.pm.MoveInfo mMoveInfo;
    final android.content.pm.IPackageInstallObserver2 mObserver;
    final com.android.server.pm.OriginInfo mOriginInfo;
    final int mPackageSource;

    @android.annotation.NonNull
    final android.util.ArrayMap<java.lang.String, java.lang.Integer> mPermissionStates;
    final android.content.pm.SigningDetails mSigningDetails;
    final int mTraceCookie;
    final java.lang.String mTraceMethod;
    final android.os.UserHandle mUser;
    final java.lang.String mVolumeUuid;

    InstallArgs(com.android.server.pm.OriginInfo originInfo, com.android.server.pm.MoveInfo moveInfo, android.content.pm.IPackageInstallObserver2 iPackageInstallObserver2, int i, int i2, com.android.server.pm.InstallSource installSource, java.lang.String str, android.os.UserHandle userHandle, java.lang.String[] strArr, java.lang.String str2, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap, java.util.List<java.lang.String> list, int i3, java.lang.String str3, int i4, android.content.pm.SigningDetails signingDetails, int i5, int i6, boolean z, int i7, int i8, boolean z2) {
        this.mOriginInfo = originInfo;
        this.mMoveInfo = moveInfo;
        this.mInstallFlags = i;
        this.mDevelopmentInstallFlags = i2;
        this.mObserver = iPackageInstallObserver2;
        this.mInstallSource = (com.android.server.pm.InstallSource) com.android.internal.util.Preconditions.checkNotNull(installSource);
        this.mVolumeUuid = str;
        this.mUser = userHandle;
        this.mInstructionSets = strArr;
        this.mAbiOverride = str2;
        this.mPermissionStates = arrayMap;
        this.mAllowlistedRestrictedPermissions = list;
        this.mAutoRevokePermissionsMode = i3;
        this.mTraceMethod = str3;
        this.mTraceCookie = i4;
        this.mSigningDetails = signingDetails;
        this.mInstallReason = i5;
        this.mInstallScenario = i6;
        this.mForceQueryableOverride = z;
        this.mDataLoaderType = i7;
        this.mPackageSource = i8;
        this.mApplicationEnabledSettingPersistent = z2;
    }
}
