package com.android.server.backup.remote;

/* loaded from: classes.dex */
public class ServiceBackupCallback extends android.app.backup.IBackupCallback.Stub {
    private final android.app.backup.IBackupManager mBackupManager;
    private final int mToken;

    public ServiceBackupCallback(android.app.backup.IBackupManager iBackupManager, int i) {
        this.mBackupManager = iBackupManager;
        this.mToken = i;
    }

    public void operationComplete(long j) throws android.os.RemoteException {
        this.mBackupManager.opComplete(this.mToken, j);
    }
}
