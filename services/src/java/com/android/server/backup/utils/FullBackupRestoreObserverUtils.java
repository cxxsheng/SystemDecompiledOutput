package com.android.server.backup.utils;

/* loaded from: classes.dex */
public class FullBackupRestoreObserverUtils {
    public static android.app.backup.IFullBackupRestoreObserver sendStartRestore(android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver) {
        if (iFullBackupRestoreObserver != null) {
            try {
                iFullBackupRestoreObserver.onStartRestore();
                return iFullBackupRestoreObserver;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "full restore observer went away: startRestore");
                return null;
            }
        }
        return iFullBackupRestoreObserver;
    }

    public static android.app.backup.IFullBackupRestoreObserver sendOnRestorePackage(android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver, java.lang.String str) {
        if (iFullBackupRestoreObserver != null) {
            try {
                iFullBackupRestoreObserver.onRestorePackage(str);
                return iFullBackupRestoreObserver;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "full restore observer went away: restorePackage");
                return null;
            }
        }
        return iFullBackupRestoreObserver;
    }

    public static android.app.backup.IFullBackupRestoreObserver sendEndRestore(android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver) {
        if (iFullBackupRestoreObserver != null) {
            try {
                iFullBackupRestoreObserver.onEndRestore();
                return iFullBackupRestoreObserver;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "full restore observer went away: endRestore");
                return null;
            }
        }
        return iFullBackupRestoreObserver;
    }
}
