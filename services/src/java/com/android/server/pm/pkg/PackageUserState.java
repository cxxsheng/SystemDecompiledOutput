package com.android.server.pm.pkg;

@android.processor.immutability.Immutable
@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes2.dex */
public interface PackageUserState {

    @android.annotation.NonNull
    public static final com.android.server.pm.pkg.PackageUserState DEFAULT = com.android.server.pm.pkg.PackageUserStateInternal.DEFAULT;

    boolean dataExists();

    @android.processor.immutability.Immutable.Ignore
    @android.annotation.Nullable
    android.content.pm.overlay.OverlayPaths getAllOverlayPaths();

    @android.processor.immutability.Immutable.Ignore
    @android.annotation.Nullable
    com.android.server.pm.pkg.ArchiveState getArchiveState();

    long getCeDataInode();

    long getDeDataInode();

    @android.annotation.NonNull
    android.util.ArraySet<java.lang.String> getDisabledComponents();

    int getDistractionFlags();

    @android.annotation.NonNull
    android.util.ArraySet<java.lang.String> getEnabledComponents();

    int getEnabledState();

    long getFirstInstallTimeMillis();

    @android.annotation.Nullable
    java.lang.String getHarmfulAppWarning();

    int getInstallReason();

    @android.annotation.Nullable
    java.lang.String getLastDisableAppCaller();

    int getMinAspectRatio();

    @android.processor.immutability.Immutable.Ignore
    @android.annotation.Nullable
    android.content.pm.overlay.OverlayPaths getOverlayPaths();

    @android.annotation.NonNull
    @android.processor.immutability.Immutable.Ignore
    java.util.Map<java.lang.String, android.content.pm.overlay.OverlayPaths> getSharedLibraryOverlayPaths();

    @android.annotation.Nullable
    java.lang.String getSplashScreenTheme();

    int getUninstallReason();

    boolean isComponentDisabled(@android.annotation.NonNull java.lang.String str);

    boolean isComponentEnabled(@android.annotation.NonNull java.lang.String str);

    boolean isHidden();

    boolean isInstalled();

    boolean isInstantApp();

    boolean isNotLaunched();

    boolean isQuarantined();

    boolean isStopped();

    boolean isSuspended();

    boolean isVirtualPreload();
}
