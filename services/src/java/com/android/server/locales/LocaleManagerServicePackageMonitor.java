package com.android.server.locales;

/* loaded from: classes2.dex */
final class LocaleManagerServicePackageMonitor extends com.android.internal.content.PackageMonitor {
    private com.android.server.locales.LocaleManagerBackupHelper mBackupHelper;
    private com.android.server.locales.LocaleManagerService mLocaleManagerService;
    private com.android.server.locales.SystemAppUpdateTracker mSystemAppUpdateTracker;

    LocaleManagerServicePackageMonitor(@android.annotation.NonNull com.android.server.locales.LocaleManagerBackupHelper localeManagerBackupHelper, @android.annotation.NonNull com.android.server.locales.SystemAppUpdateTracker systemAppUpdateTracker, @android.annotation.NonNull com.android.server.locales.LocaleManagerService localeManagerService) {
        this.mBackupHelper = localeManagerBackupHelper;
        this.mSystemAppUpdateTracker = systemAppUpdateTracker;
        this.mLocaleManagerService = localeManagerService;
    }

    public void onPackageAdded(java.lang.String str, int i) {
        this.mBackupHelper.onPackageAdded(str, i);
    }

    public void onPackageDataCleared(java.lang.String str, int i) {
        this.mBackupHelper.onPackageDataCleared(str, i);
    }

    public void onPackageRemoved(java.lang.String str, int i) {
        this.mBackupHelper.onPackageRemoved(str, i);
        this.mLocaleManagerService.deleteOverrideLocaleConfig(str, android.os.UserHandle.getUserId(i));
    }

    public void onPackageUpdateFinished(java.lang.String str, int i) {
        this.mBackupHelper.onPackageUpdateFinished(str, i);
        this.mSystemAppUpdateTracker.onPackageUpdateFinished(str, i);
    }
}
