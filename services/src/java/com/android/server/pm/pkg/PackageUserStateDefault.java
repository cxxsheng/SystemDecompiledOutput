package com.android.server.pm.pkg;

/* loaded from: classes2.dex */
class PackageUserStateDefault implements com.android.server.pm.pkg.PackageUserStateInternal {
    PackageUserStateDefault() {
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getEnabledState() {
        return 0;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getInstallReason() {
        return 0;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.NonNull
    public java.util.Map<java.lang.String, android.content.pm.overlay.OverlayPaths> getSharedLibraryOverlayPaths() {
        return java.util.Collections.emptyMap();
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getUninstallReason() {
        return 0;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isInstalled() {
        return true;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.NonNull
    /* renamed from: getDisabledComponents, reason: merged with bridge method [inline-methods] */
    public android.util.ArraySet<java.lang.String> m6425getDisabledComponents() {
        return new android.util.ArraySet<>();
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.NonNull
    /* renamed from: getEnabledComponents, reason: merged with bridge method [inline-methods] */
    public android.util.ArraySet<java.lang.String> m6426getEnabledComponents() {
        return new android.util.ArraySet<>();
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public long getCeDataInode() {
        return 0L;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public long getDeDataInode() {
        return 0L;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getDistractionFlags() {
        return 0;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.Nullable
    public java.lang.String getHarmfulAppWarning() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.Nullable
    public java.lang.String getLastDisableAppCaller() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.Nullable
    public android.content.pm.overlay.OverlayPaths getOverlayPaths() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isHidden() {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isInstantApp() {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isNotLaunched() {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isStopped() {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isSuspended() {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isVirtualPreload() {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isQuarantined() {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.Nullable
    public java.lang.String getSplashScreenTheme() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getMinAspectRatio() {
        return 0;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public long getFirstInstallTimeMillis() {
        return 0L;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isComponentEnabled(java.lang.String str) {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isComponentDisabled(java.lang.String str) {
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.Nullable
    public android.content.pm.overlay.OverlayPaths getAllOverlayPaths() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    @android.annotation.Nullable
    public com.android.server.utils.WatchedArrayMap<android.content.pm.UserPackage, com.android.server.pm.pkg.SuspendParams> getSuspendParams() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    @android.annotation.Nullable
    public com.android.server.utils.WatchedArraySet<java.lang.String> getDisabledComponentsNoCopy() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    @android.annotation.Nullable
    public com.android.server.utils.WatchedArraySet<java.lang.String> getEnabledComponentsNoCopy() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    @android.annotation.Nullable
    public android.util.Pair<java.lang.String, java.lang.Integer> getOverrideLabelIconForComponent(@android.annotation.NonNull android.content.ComponentName componentName) {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.Nullable
    public com.android.server.pm.pkg.ArchiveState getArchiveState() {
        return null;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean dataExists() {
        return true;
    }
}
