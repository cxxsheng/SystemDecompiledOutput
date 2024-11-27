package com.android.server.backup.internal;

/* loaded from: classes.dex */
public class RunInitializeReceiver extends android.content.BroadcastReceiver {
    private final com.android.server.backup.UserBackupManagerService mUserBackupManagerService;

    public RunInitializeReceiver(com.android.server.backup.UserBackupManagerService userBackupManagerService) {
        this.mUserBackupManagerService = userBackupManagerService;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        if (!com.android.server.backup.UserBackupManagerService.RUN_INITIALIZE_ACTION.equals(intent.getAction())) {
            return;
        }
        synchronized (this.mUserBackupManagerService.getQueueLock()) {
            try {
                android.util.ArraySet<java.lang.String> pendingInits = this.mUserBackupManagerService.getPendingInits();
                android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, "Running a device init; " + pendingInits.size() + " pending");
                if (pendingInits.size() > 0) {
                    java.lang.String[] strArr = (java.lang.String[]) pendingInits.toArray(new java.lang.String[pendingInits.size()]);
                    this.mUserBackupManagerService.clearPendingInits();
                    this.mUserBackupManagerService.initializeTransports(strArr, null);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
