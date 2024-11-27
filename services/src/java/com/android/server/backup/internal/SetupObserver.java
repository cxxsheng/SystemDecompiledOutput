package com.android.server.backup.internal;

/* loaded from: classes.dex */
public class SetupObserver extends android.database.ContentObserver {
    private final android.content.Context mContext;
    private final com.android.server.backup.UserBackupManagerService mUserBackupManagerService;
    private final int mUserId;

    public SetupObserver(com.android.server.backup.UserBackupManagerService userBackupManagerService, android.os.Handler handler) {
        super(handler);
        this.mUserBackupManagerService = userBackupManagerService;
        this.mContext = userBackupManagerService.getContext();
        this.mUserId = userBackupManagerService.getUserId();
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        boolean isSetupComplete = this.mUserBackupManagerService.isSetupComplete();
        boolean z2 = isSetupComplete || com.android.server.backup.UserBackupManagerService.getSetupCompleteSettingForUser(this.mContext, this.mUserId);
        this.mUserBackupManagerService.setSetupComplete(z2);
        synchronized (this.mUserBackupManagerService.getQueueLock()) {
            if (z2 && !isSetupComplete) {
                try {
                    if (this.mUserBackupManagerService.isEnabled()) {
                        com.android.server.backup.KeyValueBackupJob.schedule(this.mUserBackupManagerService.getUserId(), this.mContext, this.mUserBackupManagerService);
                        this.mUserBackupManagerService.scheduleNextFullBackupJob(0L);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
