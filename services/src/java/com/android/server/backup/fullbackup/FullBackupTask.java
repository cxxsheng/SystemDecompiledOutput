package com.android.server.backup.fullbackup;

/* loaded from: classes.dex */
public abstract class FullBackupTask implements java.lang.Runnable {
    android.app.backup.IFullBackupRestoreObserver mObserver;

    FullBackupTask(android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver) {
        this.mObserver = iFullBackupRestoreObserver;
    }

    final void sendStartBackup() {
        if (this.mObserver != null) {
            try {
                this.mObserver.onStartBackup();
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "full backup observer went away: startBackup");
                this.mObserver = null;
            }
        }
    }

    final void sendOnBackupPackage(java.lang.String str) {
        if (this.mObserver != null) {
            try {
                this.mObserver.onBackupPackage(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "full backup observer went away: backupPackage");
                this.mObserver = null;
            }
        }
    }

    final void sendEndBackup() {
        if (this.mObserver != null) {
            try {
                this.mObserver.onEndBackup();
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "full backup observer went away: endBackup");
                this.mObserver = null;
            }
        }
    }
}
