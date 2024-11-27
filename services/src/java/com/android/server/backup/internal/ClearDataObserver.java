package com.android.server.backup.internal;

/* loaded from: classes.dex */
public class ClearDataObserver extends android.content.pm.IPackageDataObserver.Stub {
    private com.android.server.backup.UserBackupManagerService backupManagerService;

    public ClearDataObserver(com.android.server.backup.UserBackupManagerService userBackupManagerService) {
        this.backupManagerService = userBackupManagerService;
    }

    public void onRemoveCompleted(java.lang.String str, boolean z) {
        synchronized (this.backupManagerService.getClearDataLock()) {
            this.backupManagerService.setClearingData(false);
            this.backupManagerService.getClearDataLock().notifyAll();
        }
    }
}
