package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
final class ScanResult {

    @android.annotation.Nullable
    public final java.util.List<java.lang.String> mChangedAbiCodePath;
    public final java.util.List<android.content.pm.SharedLibraryInfo> mDynamicSharedLibraryInfos;
    public final boolean mExistingSettingCopied;

    @android.annotation.Nullable
    public final com.android.server.pm.PackageSetting mPkgSetting;
    public final int mPreviousAppId = -1;

    @android.annotation.NonNull
    public final com.android.server.pm.ScanRequest mRequest;
    public final android.content.pm.SharedLibraryInfo mSdkSharedLibraryInfo;
    public final android.content.pm.SharedLibraryInfo mStaticSharedLibraryInfo;

    ScanResult(@android.annotation.NonNull com.android.server.pm.ScanRequest scanRequest, @android.annotation.Nullable com.android.server.pm.PackageSetting packageSetting, @android.annotation.Nullable java.util.List<java.lang.String> list, boolean z, int i, android.content.pm.SharedLibraryInfo sharedLibraryInfo, android.content.pm.SharedLibraryInfo sharedLibraryInfo2, java.util.List<android.content.pm.SharedLibraryInfo> list2) {
        this.mRequest = scanRequest;
        this.mPkgSetting = packageSetting;
        this.mChangedAbiCodePath = list;
        this.mExistingSettingCopied = z;
        this.mSdkSharedLibraryInfo = sharedLibraryInfo;
        this.mStaticSharedLibraryInfo = sharedLibraryInfo2;
        this.mDynamicSharedLibraryInfos = list2;
    }

    public boolean needsNewAppId() {
        return this.mPreviousAppId != -1;
    }
}
