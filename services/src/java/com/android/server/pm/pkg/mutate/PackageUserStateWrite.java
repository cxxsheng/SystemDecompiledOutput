package com.android.server.pm.pkg.mutate;

/* loaded from: classes2.dex */
public interface PackageUserStateWrite {
    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite putSuspendParams(@android.annotation.NonNull android.content.pm.UserPackage userPackage, @android.annotation.Nullable com.android.server.pm.pkg.SuspendParams suspendParams);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite removeSuspension(@android.annotation.NonNull android.content.pm.UserPackage userPackage);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite setComponentLabelIcon(@android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.Integer num);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite setDistractionFlags(int i);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite setHarmfulAppWarning(@android.annotation.Nullable java.lang.String str);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite setHidden(boolean z);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite setInstalled(boolean z);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite setMinAspectRatio(int i);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite setNotLaunched(boolean z);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite setOverlayPaths(@android.annotation.Nullable android.content.pm.overlay.OverlayPaths overlayPaths);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite setOverlayPathsForLibrary(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable android.content.pm.overlay.OverlayPaths overlayPaths);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite setSplashScreenTheme(@android.annotation.Nullable java.lang.String str);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite setStopped(boolean z);

    @android.annotation.NonNull
    com.android.server.pm.pkg.mutate.PackageUserStateWrite setUninstallReason(int i);
}
