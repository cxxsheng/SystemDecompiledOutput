package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
final class ScanRequest {

    @android.annotation.Nullable
    public final java.lang.String mCpuAbiOverride;

    @android.annotation.Nullable
    public final com.android.server.pm.PackageSetting mDisabledPkgSetting;
    public final boolean mIsPlatformPackage;

    @android.annotation.Nullable
    public final com.android.server.pm.pkg.AndroidPackage mOldPkg;

    @android.annotation.Nullable
    public final com.android.server.pm.PackageSetting mOldPkgSetting;

    @android.annotation.Nullable
    public final com.android.server.pm.SharedUserSetting mOldSharedUserSetting;

    @android.annotation.Nullable
    public final com.android.server.pm.PackageSetting mOriginalPkgSetting;
    public final int mParseFlags;

    @android.annotation.NonNull
    public final com.android.internal.pm.parsing.pkg.ParsedPackage mParsedPackage;

    @android.annotation.Nullable
    public final com.android.server.pm.PackageSetting mPkgSetting;

    @android.annotation.Nullable
    public final java.lang.String mRealPkgName;
    public final int mScanFlags;

    @android.annotation.Nullable
    public final com.android.server.pm.SharedUserSetting mSharedUserSetting;

    @android.annotation.Nullable
    public final android.os.UserHandle mUser;

    ScanRequest(@android.annotation.NonNull com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, @android.annotation.Nullable com.android.server.pm.SharedUserSetting sharedUserSetting, @android.annotation.Nullable com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting, @android.annotation.Nullable com.android.server.pm.SharedUserSetting sharedUserSetting2, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting2, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting3, @android.annotation.Nullable java.lang.String str, int i, int i2, boolean z, @android.annotation.Nullable android.os.UserHandle userHandle, @android.annotation.Nullable java.lang.String str2) {
        this.mParsedPackage = parsedPackage;
        this.mOldPkg = androidPackage;
        this.mPkgSetting = packageSetting;
        this.mOldSharedUserSetting = sharedUserSetting;
        this.mSharedUserSetting = sharedUserSetting2;
        this.mOldPkgSetting = packageSetting == null ? null : new com.android.server.pm.PackageSetting(packageSetting);
        this.mDisabledPkgSetting = packageSetting2;
        this.mOriginalPkgSetting = packageSetting3;
        this.mRealPkgName = str;
        this.mParseFlags = i;
        this.mScanFlags = i2;
        this.mIsPlatformPackage = z;
        this.mUser = userHandle;
        this.mCpuAbiOverride = str2;
    }
}
