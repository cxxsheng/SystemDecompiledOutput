package com.android.server.backup.restore;

/* loaded from: classes.dex */
class RestoreFileRunnable implements java.lang.Runnable {
    private final android.app.IBackupAgent mAgent;
    private final com.android.server.backup.UserBackupManagerService mBackupManagerService;
    private final com.android.server.backup.FileMetadata mInfo;
    private final android.os.ParcelFileDescriptor mSocket;
    private final int mToken;

    RestoreFileRunnable(com.android.server.backup.UserBackupManagerService userBackupManagerService, android.app.IBackupAgent iBackupAgent, com.android.server.backup.FileMetadata fileMetadata, android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws java.io.IOException {
        this.mAgent = iBackupAgent;
        this.mInfo = fileMetadata;
        this.mToken = i;
        this.mSocket = android.os.ParcelFileDescriptor.dup(parcelFileDescriptor.getFileDescriptor());
        this.mBackupManagerService = userBackupManagerService;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.mAgent.doRestoreFile(this.mSocket, this.mInfo.size, this.mInfo.type, this.mInfo.domain, this.mInfo.path, this.mInfo.mode, this.mInfo.mtime, this.mToken, this.mBackupManagerService.getBackupManagerBinder());
        } catch (android.os.RemoteException e) {
        }
    }
}
