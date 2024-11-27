package com.android.server.backup.restore;

/* loaded from: classes.dex */
public class AdbRestoreFinishedRunnable implements java.lang.Runnable {
    private final android.app.IBackupAgent mAgent;
    private final com.android.server.backup.UserBackupManagerService mBackupManagerService;
    private final int mToken;

    AdbRestoreFinishedRunnable(android.app.IBackupAgent iBackupAgent, int i, com.android.server.backup.UserBackupManagerService userBackupManagerService) {
        this.mAgent = iBackupAgent;
        this.mToken = i;
        this.mBackupManagerService = userBackupManagerService;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.mAgent.doRestoreFinished(this.mToken, this.mBackupManagerService.getBackupManagerBinder());
        } catch (android.os.RemoteException e) {
        }
    }
}
