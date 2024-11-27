package com.android.server.tare;

/* loaded from: classes2.dex */
class InstalledPackageInfo {
    private static final int HEADLESS_APP_QUERY_FLAGS = 786944;
    static final int NO_UID = -1;
    public final boolean hasCode;

    @android.annotation.Nullable
    public final java.lang.String installerPackageName;
    public final boolean isHeadlessSystemApp;
    public final boolean isSystemInstaller;
    public final java.lang.String packageName;
    public final int uid;

    InstalledPackageInfo(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo) {
        android.content.pm.InstallSourceInfo installSourceInfo;
        android.content.pm.ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        this.uid = applicationInfo == null ? -1 : applicationInfo.uid;
        this.packageName = packageInfo.packageName;
        boolean z = false;
        this.hasCode = applicationInfo != null && applicationInfo.hasCode();
        this.isHeadlessSystemApp = applicationInfo != null && (applicationInfo.isSystemApp() || applicationInfo.isUpdatedSystemApp()) && com.android.internal.util.jobs.ArrayUtils.isEmpty(context.getPackageManager().queryIntentActivitiesAsUser(new android.content.Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage(this.packageName), HEADLESS_APP_QUERY_FLAGS, i));
        if (applicationInfo != null && com.android.internal.util.jobs.ArrayUtils.indexOf(packageInfo.requestedPermissions, "android.permission.INSTALL_PACKAGES") >= 0 && android.content.PermissionChecker.checkPermissionForPreflight(context, "android.permission.INSTALL_PACKAGES", -1, applicationInfo.uid, this.packageName) == 0) {
            z = true;
        }
        this.isSystemInstaller = z;
        try {
            installSourceInfo = android.app.AppGlobals.getPackageManager().getInstallSourceInfo(this.packageName, i);
        } catch (android.os.RemoteException e) {
            installSourceInfo = null;
        }
        this.installerPackageName = installSourceInfo != null ? installSourceInfo.getInstallingPackageName() : null;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("IPO{uid=");
        sb.append(this.uid);
        sb.append(", pkgName=");
        sb.append(this.packageName);
        sb.append(this.hasCode ? " HAS_CODE" : "");
        sb.append(this.isHeadlessSystemApp ? " HEADLESS_SYSTEM" : "");
        sb.append(this.isSystemInstaller ? " SYSTEM_INSTALLER" : "");
        sb.append(", installer=");
        sb.append(this.installerPackageName);
        sb.append('}');
        return sb.toString();
    }
}
