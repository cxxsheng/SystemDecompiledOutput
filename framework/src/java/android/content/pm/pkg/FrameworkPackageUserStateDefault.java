package android.content.pm.pkg;

@java.lang.Deprecated
/* loaded from: classes.dex */
class FrameworkPackageUserStateDefault implements android.content.pm.pkg.FrameworkPackageUserState {
    FrameworkPackageUserStateDefault() {
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public int getEnabledState() {
        return 0;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public int getInstallReason() {
        return 0;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public java.util.Map<java.lang.String, android.content.pm.overlay.OverlayPaths> getSharedLibraryOverlayPaths() {
        return java.util.Collections.emptyMap();
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public int getUninstallReason() {
        return 0;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public boolean isInstalled() {
        return true;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public java.util.Set<java.lang.String> getDisabledComponents() {
        return java.util.Collections.emptySet();
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public java.util.Set<java.lang.String> getEnabledComponents() {
        return java.util.Collections.emptySet();
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public long getCeDataInode() {
        return 0L;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public int getDistractionFlags() {
        return 0;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public java.lang.String getHarmfulAppWarning() {
        return null;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public java.lang.String getLastDisableAppCaller() {
        return null;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public android.content.pm.overlay.OverlayPaths getOverlayPaths() {
        return null;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public boolean isHidden() {
        return false;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public boolean isInstantApp() {
        return false;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public boolean isNotLaunched() {
        return false;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public boolean isStopped() {
        return false;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public boolean isSuspended() {
        return false;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public boolean isVirtualPreload() {
        return false;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public java.lang.String getSplashScreenTheme() {
        return null;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public boolean isComponentEnabled(java.lang.String str) {
        return false;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public boolean isComponentDisabled(java.lang.String str) {
        return false;
    }

    @Override // android.content.pm.pkg.FrameworkPackageUserState
    public android.content.pm.overlay.OverlayPaths getAllOverlayPaths() {
        return null;
    }
}
