package com.android.server.backup.internal;

/* loaded from: classes.dex */
public class PerformClearTask implements java.lang.Runnable {
    private final com.android.server.backup.UserBackupManagerService mBackupManagerService;
    private final com.android.server.backup.internal.OnTaskFinishedListener mListener;
    private final android.content.pm.PackageInfo mPackage;
    private final com.android.server.backup.transport.TransportConnection mTransportConnection;
    private final com.android.server.backup.TransportManager mTransportManager;

    PerformClearTask(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.transport.TransportConnection transportConnection, android.content.pm.PackageInfo packageInfo, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener) {
        this.mBackupManagerService = userBackupManagerService;
        this.mTransportManager = userBackupManagerService.getTransportManager();
        this.mTransportConnection = transportConnection;
        this.mPackage = packageInfo;
        this.mListener = onTaskFinishedListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        java.lang.StringBuilder sb;
        com.android.server.backup.transport.BackupTransportClient backupTransportClient = null;
        try {
            try {
                new java.io.File(new java.io.File(this.mBackupManagerService.getBaseStateDir(), this.mTransportManager.getTransportDirName(this.mTransportConnection.getTransportComponent())), this.mPackage.packageName).delete();
                backupTransportClient = this.mTransportConnection.connectOrThrow("PerformClearTask.run()");
                backupTransportClient.clearBackupData(this.mPackage);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Transport threw clearing data for " + this.mPackage + ": " + e.getMessage());
                if (backupTransportClient != null) {
                    try {
                        backupTransportClient.finishBackup();
                    } catch (java.lang.Exception e2) {
                        e = e2;
                        sb = new java.lang.StringBuilder();
                        sb.append("Unable to mark clear operation finished: ");
                        sb.append(e.getMessage());
                        android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, sb.toString());
                        this.mListener.onFinished("PerformClearTask.run()");
                        this.mBackupManagerService.getWakelock().release();
                    }
                }
            }
            try {
                backupTransportClient.finishBackup();
            } catch (java.lang.Exception e3) {
                e = e3;
                sb = new java.lang.StringBuilder();
                sb.append("Unable to mark clear operation finished: ");
                sb.append(e.getMessage());
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, sb.toString());
                this.mListener.onFinished("PerformClearTask.run()");
                this.mBackupManagerService.getWakelock().release();
            }
            this.mListener.onFinished("PerformClearTask.run()");
            this.mBackupManagerService.getWakelock().release();
        } catch (java.lang.Throwable th) {
            if (backupTransportClient != null) {
                try {
                    backupTransportClient.finishBackup();
                } catch (java.lang.Exception e4) {
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to mark clear operation finished: " + e4.getMessage());
                }
            }
            this.mListener.onFinished("PerformClearTask.run()");
            this.mBackupManagerService.getWakelock().release();
            throw th;
        }
    }
}
