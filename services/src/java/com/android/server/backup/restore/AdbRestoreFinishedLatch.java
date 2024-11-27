package com.android.server.backup.restore;

/* loaded from: classes.dex */
public class AdbRestoreFinishedLatch implements com.android.server.backup.BackupRestoreTask {
    private static final java.lang.String TAG = "AdbRestoreFinishedLatch";
    private com.android.server.backup.UserBackupManagerService backupManagerService;
    private final com.android.server.backup.BackupAgentTimeoutParameters mAgentTimeoutParameters;
    private final int mCurrentOpToken;
    final java.util.concurrent.CountDownLatch mLatch = new java.util.concurrent.CountDownLatch(1);
    private final com.android.server.backup.OperationStorage mOperationStorage;

    public AdbRestoreFinishedLatch(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.OperationStorage operationStorage, int i) {
        this.backupManagerService = userBackupManagerService;
        this.mOperationStorage = operationStorage;
        this.mCurrentOpToken = i;
        com.android.server.backup.BackupAgentTimeoutParameters agentTimeoutParameters = userBackupManagerService.getAgentTimeoutParameters();
        java.util.Objects.requireNonNull(agentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = agentTimeoutParameters;
    }

    void await() {
        try {
            this.mLatch.await(this.mAgentTimeoutParameters.getFullBackupAgentTimeoutMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (java.lang.InterruptedException e) {
            android.util.Slog.w(TAG, "Interrupted!");
        }
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void execute() {
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void operationComplete(long j) {
        this.mLatch.countDown();
        this.mOperationStorage.removeOperation(this.mCurrentOpToken);
    }

    @Override // com.android.server.backup.BackupRestoreTask
    public void handleCancel(boolean z) {
        android.util.Slog.w(TAG, "adb onRestoreFinished() timed out");
        this.mLatch.countDown();
        this.mOperationStorage.removeOperation(this.mCurrentOpToken);
    }
}
