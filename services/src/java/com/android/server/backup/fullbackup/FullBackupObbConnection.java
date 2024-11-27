package com.android.server.backup.fullbackup;

/* loaded from: classes.dex */
public class FullBackupObbConnection implements android.content.ServiceConnection {
    private com.android.server.backup.UserBackupManagerService backupManagerService;
    private final com.android.server.backup.BackupAgentTimeoutParameters mAgentTimeoutParameters;
    volatile com.android.internal.backup.IObbBackupService mService = null;

    public FullBackupObbConnection(com.android.server.backup.UserBackupManagerService userBackupManagerService) {
        this.backupManagerService = userBackupManagerService;
        com.android.server.backup.BackupAgentTimeoutParameters agentTimeoutParameters = userBackupManagerService.getAgentTimeoutParameters();
        java.util.Objects.requireNonNull(agentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = agentTimeoutParameters;
    }

    public void establish() {
        this.backupManagerService.getContext().bindServiceAsUser(new android.content.Intent().setComponent(new android.content.ComponentName(com.android.server.backup.UserBackupManagerService.SHARED_BACKUP_AGENT_PACKAGE, "com.android.sharedstoragebackup.ObbBackupService")), this, 1, android.os.UserHandle.SYSTEM);
    }

    public void tearDown() {
        this.backupManagerService.getContext().unbindService(this);
    }

    public boolean backupObbs(android.content.pm.PackageInfo packageInfo, java.io.OutputStream outputStream) {
        waitForConnection();
        android.os.ParcelFileDescriptor[] parcelFileDescriptorArr = null;
        try {
            try {
                parcelFileDescriptorArr = android.os.ParcelFileDescriptor.createPipe();
                int generateRandomIntegerToken = this.backupManagerService.generateRandomIntegerToken();
                this.backupManagerService.prepareOperationTimeout(generateRandomIntegerToken, this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis(), null, 0);
                this.mService.backupObbs(packageInfo.packageName, parcelFileDescriptorArr[1], generateRandomIntegerToken, this.backupManagerService.getBackupManagerBinder());
                com.android.server.backup.utils.FullBackupUtils.routeSocketDataToOutput(parcelFileDescriptorArr[0], outputStream);
                boolean waitUntilOperationComplete = this.backupManagerService.waitUntilOperationComplete(generateRandomIntegerToken);
                try {
                    outputStream.flush();
                    if (parcelFileDescriptorArr[0] != null) {
                        parcelFileDescriptorArr[0].close();
                    }
                    if (parcelFileDescriptorArr[1] != null) {
                        parcelFileDescriptorArr[1].close();
                    }
                } catch (java.io.IOException e) {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "I/O error closing down OBB backup", e);
                }
                return waitUntilOperationComplete;
            } catch (java.lang.Throwable th) {
                try {
                    outputStream.flush();
                    if (parcelFileDescriptorArr != null) {
                        if (parcelFileDescriptorArr[0] != null) {
                            parcelFileDescriptorArr[0].close();
                        }
                        if (parcelFileDescriptorArr[1] != null) {
                            parcelFileDescriptorArr[1].close();
                        }
                    }
                } catch (java.io.IOException e2) {
                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "I/O error closing down OBB backup", e2);
                }
                throw th;
            }
        } catch (java.lang.Exception e3) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Unable to back up OBBs for " + packageInfo, e3);
            try {
                outputStream.flush();
                if (parcelFileDescriptorArr == null) {
                    return false;
                }
                if (parcelFileDescriptorArr[0] != null) {
                    parcelFileDescriptorArr[0].close();
                }
                if (parcelFileDescriptorArr[1] == null) {
                    return false;
                }
                parcelFileDescriptorArr[1].close();
                return false;
            } catch (java.io.IOException e4) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "I/O error closing down OBB backup", e4);
                return false;
            }
        }
    }

    public void restoreObbFile(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, java.lang.String str2, long j2, long j3, int i2, android.app.backup.IBackupManager iBackupManager) {
        waitForConnection();
        try {
            this.mService.restoreObbFile(str, parcelFileDescriptor, j, i, str2, j2, j3, i2, iBackupManager);
        } catch (java.lang.Exception e) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Unable to restore OBBs for " + str, e);
        }
    }

    private void waitForConnection() {
        synchronized (this) {
            while (this.mService == null) {
                try {
                    wait();
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        synchronized (this) {
            this.mService = com.android.internal.backup.IObbBackupService.Stub.asInterface(iBinder);
            notifyAll();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(android.content.ComponentName componentName) {
        synchronized (this) {
            this.mService = null;
            notifyAll();
        }
    }
}
