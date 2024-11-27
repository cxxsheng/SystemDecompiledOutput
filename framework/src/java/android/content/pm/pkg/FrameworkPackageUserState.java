package android.content.pm.pkg;

@java.lang.Deprecated
/* loaded from: classes.dex */
public interface FrameworkPackageUserState {
    public static final android.content.pm.pkg.FrameworkPackageUserState DEFAULT = new android.content.pm.pkg.FrameworkPackageUserStateDefault();

    android.content.pm.overlay.OverlayPaths getAllOverlayPaths();

    long getCeDataInode();

    java.util.Set<java.lang.String> getDisabledComponents();

    int getDistractionFlags();

    java.util.Set<java.lang.String> getEnabledComponents();

    int getEnabledState();

    java.lang.String getHarmfulAppWarning();

    int getInstallReason();

    java.lang.String getLastDisableAppCaller();

    android.content.pm.overlay.OverlayPaths getOverlayPaths();

    java.util.Map<java.lang.String, android.content.pm.overlay.OverlayPaths> getSharedLibraryOverlayPaths();

    java.lang.String getSplashScreenTheme();

    int getUninstallReason();

    boolean isComponentDisabled(java.lang.String str);

    boolean isComponentEnabled(java.lang.String str);

    boolean isHidden();

    boolean isInstalled();

    boolean isInstantApp();

    boolean isNotLaunched();

    boolean isStopped();

    boolean isSuspended();

    boolean isVirtualPreload();
}
