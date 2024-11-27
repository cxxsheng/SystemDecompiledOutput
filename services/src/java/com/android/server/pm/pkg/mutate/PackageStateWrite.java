package com.android.server.pm.pkg.mutate;

/* loaded from: classes2.dex */
public interface PackageStateWrite {
    void onChanged();

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageStateWrite setCategoryOverride(int i);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageStateWrite setHiddenUntilInstalled(boolean z);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageStateWrite setInstaller(@android.annotation.Nullable java.lang.String str, int i);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageStateWrite setLastPackageUsageTime(@android.content.pm.PackageManager.NotifyReason int i, long j);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageStateWrite setLoadingCompletedTime(long j);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageStateWrite setLoadingProgress(float f);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageStateWrite setMimeGroup(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageStateWrite setOverrideSeInfo(@android.annotation.Nullable java.lang.String str);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageStateWrite setRequiredForSystemUser(boolean z);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageStateWrite setUpdateAvailable(boolean z);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageStateWrite setUpdateOwner(@android.annotation.Nullable java.lang.String str);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite userState(int i);
}
