package com.android.server.rollback;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public class AppDataRollbackHelper {
    private static final java.lang.String TAG = "RollbackManager";
    private final com.android.server.pm.ApexManager mApexManager;
    private final com.android.server.pm.Installer mInstaller;

    AppDataRollbackHelper(com.android.server.pm.Installer installer) {
        this.mInstaller = installer;
        this.mApexManager = com.android.server.pm.ApexManager.getInstance();
    }

    @com.android.internal.annotations.VisibleForTesting
    AppDataRollbackHelper(com.android.server.pm.Installer installer, com.android.server.pm.ApexManager apexManager) {
        this.mInstaller = installer;
        this.mApexManager = apexManager;
    }

    public void snapshotAppData(int i, android.content.rollback.PackageRollbackInfo packageRollbackInfo, int[] iArr) {
        int i2;
        for (int i3 : iArr) {
            if (isUserCredentialLocked(i3)) {
                android.util.Slog.v(TAG, "User: " + i3 + " isn't unlocked, skipping CE userdata backup.");
                packageRollbackInfo.addPendingBackup(i3);
                i2 = 1;
            } else {
                i2 = 3;
            }
            doSnapshot(packageRollbackInfo, i3, i, i2);
        }
    }

    public boolean restoreAppData(int i, android.content.rollback.PackageRollbackInfo packageRollbackInfo, int i2, int i3, java.lang.String str) {
        int i4;
        java.util.List pendingBackups = packageRollbackInfo.getPendingBackups();
        java.util.ArrayList pendingRestores = packageRollbackInfo.getPendingRestores();
        boolean z = true;
        if (pendingBackups != null && pendingBackups.indexOf(java.lang.Integer.valueOf(i2)) != -1) {
            pendingBackups.remove(pendingBackups.indexOf(java.lang.Integer.valueOf(i2)));
            i4 = 1;
        } else if (isUserCredentialLocked(i2)) {
            pendingRestores.add(new android.content.rollback.PackageRollbackInfo.RestoreInfo(i2, i3, str));
            i4 = 1;
        } else {
            z = false;
            i4 = 3;
        }
        doRestoreOrWipe(packageRollbackInfo, i2, i, i3, str, i4);
        return z;
    }

    private boolean doSnapshot(android.content.rollback.PackageRollbackInfo packageRollbackInfo, int i, int i2, int i3) {
        if (packageRollbackInfo.isApex()) {
            if ((i3 & 2) != 0) {
                return this.mApexManager.snapshotCeData(i, i2, packageRollbackInfo.getPackageName());
            }
            return true;
        }
        try {
            return this.mInstaller.snapshotAppData(packageRollbackInfo.getPackageName(), i, i2, i3);
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.e(TAG, "Unable to create app data snapshot for: " + packageRollbackInfo.getPackageName() + ", userId: " + i, e);
            return false;
        }
    }

    private boolean doRestoreOrWipe(android.content.rollback.PackageRollbackInfo packageRollbackInfo, int i, int i2, int i3, java.lang.String str, int i4) {
        if (packageRollbackInfo.isApex()) {
            switch (packageRollbackInfo.getRollbackDataPolicy()) {
                case 0:
                    if ((i4 & 2) != 0) {
                        this.mApexManager.restoreCeData(i, i2, packageRollbackInfo.getPackageName());
                        return true;
                    }
                    return true;
                case 1:
                default:
                    return true;
            }
        }
        try {
            switch (packageRollbackInfo.getRollbackDataPolicy()) {
                case 0:
                    this.mInstaller.restoreAppDataSnapshot(packageRollbackInfo.getPackageName(), i3, str, i, i2, i4);
                    break;
                case 1:
                    this.mInstaller.clearAppData(null, packageRollbackInfo.getPackageName(), i, i4, 0L);
                    break;
            }
            return true;
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.e(TAG, "Unable to restore/wipe app data: " + packageRollbackInfo.getPackageName() + " policy=" + packageRollbackInfo.getRollbackDataPolicy(), e);
            return false;
        }
    }

    public void destroyAppDataSnapshot(int i, android.content.rollback.PackageRollbackInfo packageRollbackInfo, int i2) {
        try {
            this.mInstaller.destroyAppDataSnapshot(packageRollbackInfo.getPackageName(), i2, i, 3);
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.e(TAG, "Unable to delete app data snapshot for " + packageRollbackInfo.getPackageName(), e);
        }
    }

    public void destroyApexDeSnapshots(int i) {
        this.mApexManager.destroyDeSnapshots(i);
    }

    public void destroyApexCeSnapshots(int i, int i2) {
        if (!isUserCredentialLocked(i)) {
            this.mApexManager.destroyCeSnapshots(i, i2);
        }
    }

    boolean commitPendingBackupAndRestoreForUser(int i, com.android.server.rollback.Rollback rollback) {
        boolean z;
        boolean z2 = false;
        for (android.content.rollback.PackageRollbackInfo packageRollbackInfo : rollback.info.getPackages()) {
            java.util.List pendingBackups = packageRollbackInfo.getPendingBackups();
            boolean z3 = true;
            if (pendingBackups != null && pendingBackups.indexOf(java.lang.Integer.valueOf(i)) != -1) {
                z2 = true;
                z = true;
            } else {
                z = false;
            }
            android.content.rollback.PackageRollbackInfo.RestoreInfo restoreInfo = packageRollbackInfo.getRestoreInfo(i);
            if (restoreInfo == null) {
                z3 = false;
            } else {
                z2 = true;
            }
            if (z && z3) {
                packageRollbackInfo.removePendingBackup(i);
                packageRollbackInfo.removePendingRestoreInfo(i);
            } else {
                if (z) {
                    int indexOf = pendingBackups.indexOf(java.lang.Integer.valueOf(i));
                    if (doSnapshot(packageRollbackInfo, i, rollback.info.getRollbackId(), 2)) {
                        pendingBackups.remove(indexOf);
                    }
                }
                if (z3 && doRestoreOrWipe(packageRollbackInfo, i, rollback.info.getRollbackId(), restoreInfo.appId, restoreInfo.seInfo, 2)) {
                    packageRollbackInfo.removeRestoreInfo(restoreInfo);
                }
            }
        }
        return z2;
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean isUserCredentialLocked(int i) {
        return android.os.storage.StorageManager.isFileEncrypted() && !android.os.storage.StorageManager.isCeStorageUnlocked(i);
    }
}
