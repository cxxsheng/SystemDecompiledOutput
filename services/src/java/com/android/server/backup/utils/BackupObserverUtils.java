package com.android.server.backup.utils;

/* loaded from: classes.dex */
public class BackupObserverUtils {
    public static void sendBackupOnUpdate(android.app.backup.IBackupObserver iBackupObserver, java.lang.String str, android.app.backup.BackupProgress backupProgress) {
        if (iBackupObserver != null) {
            try {
                iBackupObserver.onUpdate(str, backupProgress);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Backup observer went away: onUpdate");
            }
        }
    }

    public static void sendBackupOnPackageResult(android.app.backup.IBackupObserver iBackupObserver, java.lang.String str, int i) {
        if (iBackupObserver != null) {
            try {
                iBackupObserver.onResult(str, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Backup observer went away: onResult");
            }
        }
    }

    public static void sendBackupFinished(android.app.backup.IBackupObserver iBackupObserver, int i) {
        if (iBackupObserver != null) {
            try {
                iBackupObserver.backupFinished(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Backup observer went away: backupFinished");
            }
        }
    }
}
