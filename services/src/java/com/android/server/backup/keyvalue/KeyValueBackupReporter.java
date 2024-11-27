package com.android.server.backup.keyvalue;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes.dex */
public class KeyValueBackupReporter {
    private static final boolean DEBUG = true;

    @com.android.internal.annotations.VisibleForTesting
    static final boolean MORE_DEBUG = false;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String TAG = "KeyValueBackupTask";
    private final com.android.server.backup.utils.BackupManagerMonitorEventSender mBackupManagerMonitorEventSender;
    private final com.android.server.backup.UserBackupManagerService mBackupManagerService;
    private final android.app.backup.IBackupObserver mObserver;

    static void onNewThread(java.lang.String str) {
        android.util.Slog.d(TAG, "Spinning thread " + str);
    }

    KeyValueBackupReporter(com.android.server.backup.UserBackupManagerService userBackupManagerService, android.app.backup.IBackupObserver iBackupObserver, com.android.server.backup.utils.BackupManagerMonitorEventSender backupManagerMonitorEventSender) {
        this.mBackupManagerService = userBackupManagerService;
        this.mObserver = iBackupObserver;
        this.mBackupManagerMonitorEventSender = backupManagerMonitorEventSender;
    }

    @android.annotation.Nullable
    android.app.backup.IBackupManagerMonitor getMonitor() {
        return this.mBackupManagerMonitorEventSender.getMonitor();
    }

    android.app.backup.IBackupObserver getObserver() {
        return this.mObserver;
    }

    void onSkipBackup() {
        android.util.Slog.d(TAG, "Skipping backup since one is already in progress");
    }

    void onEmptyQueueAtStart() {
        android.util.Slog.w(TAG, "Backup begun with an empty queue, nothing to do");
    }

    void onQueueReady(java.util.List<java.lang.String> list) {
        android.util.Slog.v(TAG, "Beginning backup of " + list.size() + " targets");
    }

    void onTransportReady(java.lang.String str) {
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_START, str);
    }

    void onInitializeTransport(java.lang.String str) {
        android.util.Slog.i(TAG, "Initializing transport and resetting backup state");
    }

    void onTransportInitialized(int i) {
        if (i == 0) {
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_INITIALIZE, new java.lang.Object[0]);
        } else {
            android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_TRANSPORT_FAILURE, "(initialize)");
            android.util.Slog.e(TAG, "Transport error in initializeDevice()");
        }
    }

    void onInitializeTransportError(java.lang.Exception exc) {
        android.util.Slog.e(TAG, "Error during initialization", exc);
    }

    void onSkipPm() {
        android.util.Slog.d(TAG, "Skipping backup of PM metadata");
    }

    void onExtractPmAgentDataError(java.lang.Exception exc) {
        android.util.Slog.e(TAG, "Error during PM metadata backup", exc);
    }

    void onStartPackageBackup(java.lang.String str) {
        android.util.Slog.d(TAG, "Starting key-value backup of " + str);
    }

    void onPackageNotEligibleForBackup(java.lang.String str) {
        android.util.Slog.i(TAG, "Package " + str + " no longer supports backup, skipping");
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str, -2001);
    }

    void onPackageEligibleForFullBackup(java.lang.String str) {
        android.util.Slog.i(TAG, "Package " + str + " performs full-backup rather than key-value, skipping");
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str, -2001);
    }

    void onPackageStopped(java.lang.String str) {
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str, -2001);
    }

    void onAgentUnknown(java.lang.String str) {
        android.util.Slog.d(TAG, "Package does not exist, skipping");
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str, -2002);
    }

    void onBindAgentError(java.lang.String str, java.lang.SecurityException securityException) {
        android.util.Slog.d(TAG, "Error in bind/backup", securityException);
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str, -1003);
    }

    void onAgentError(java.lang.String str) {
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str, -1003);
    }

    void onExtractAgentData(java.lang.String str) {
        android.util.Slog.d(TAG, "Invoking agent on " + str);
    }

    void onAgentFilesReady(java.io.File file) {
    }

    void onRestoreconFailed(java.io.File file) {
        android.util.Slog.e(TAG, "SELinux restorecon failed on " + file);
    }

    void onCallAgentDoBackupError(java.lang.String str, boolean z, java.lang.Exception exc) {
        if (z) {
            android.util.Slog.e(TAG, "Error invoking agent on " + str + ": " + exc);
            com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str, -1003);
        } else {
            android.util.Slog.e(TAG, "Error before invoking agent on " + str + ": " + exc);
        }
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_AGENT_FAILURE, str, exc.toString());
    }

    void onFailAgentError(java.lang.String str) {
        android.util.Slog.w(TAG, "Error conveying failure to " + str);
    }

    void onAgentIllegalKey(android.content.pm.PackageInfo packageInfo, java.lang.String str) {
        java.lang.String str2 = packageInfo.packageName;
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_AGENT_FAILURE, str2, "bad key");
        this.mBackupManagerMonitorEventSender.monitorEvent(5, packageInfo, 3, com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.LOG_ILLEGAL_KEY", str));
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str2, -1003);
    }

    void onAgentDataError(java.lang.String str, java.io.IOException iOException) {
        android.util.Slog.w(TAG, "Unable to read/write agent data for " + str + ": " + iOException);
    }

    void onDigestError(java.security.NoSuchAlgorithmException noSuchAlgorithmException) {
        android.util.Slog.e(TAG, "Unable to use SHA-1!");
    }

    void onWriteWidgetData(boolean z, @android.annotation.Nullable byte[] bArr) {
    }

    void onTransportPerformBackup(java.lang.String str) {
    }

    void onEmptyData(android.content.pm.PackageInfo packageInfo) {
        this.mBackupManagerMonitorEventSender.monitorEvent(7, packageInfo, 3, null);
    }

    void onPackageBackupComplete(java.lang.String str, long j) {
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str, 0);
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_PACKAGE, str, java.lang.Long.valueOf(j));
        this.mBackupManagerService.logBackupComplete(str);
    }

    void onPackageBackupRejected(java.lang.String str) {
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str, com.android.server.job.JobSchedulerShellCommand.CMD_ERR_CONSTRAINTS);
        com.android.server.EventLogTags.writeBackupAgentFailure(str, "Transport rejected");
    }

    void onPackageBackupQuotaExceeded(java.lang.String str) {
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str, -1005);
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_QUOTA_EXCEEDED, str);
    }

    void onAgentDoQuotaExceededError(java.lang.Exception exc) {
        android.util.Slog.e(TAG, "Unable to notify about quota exceeded: " + exc);
    }

    void onPackageBackupNonIncrementalRequired(android.content.pm.PackageInfo packageInfo) {
        android.util.Slog.i(TAG, "Transport lost data, retrying package");
        this.mBackupManagerMonitorEventSender.monitorEvent(51, packageInfo, 1, null);
    }

    void onPackageBackupNonIncrementalAndNonIncrementalRequired(java.lang.String str) {
        android.util.Slog.e(TAG, "Transport requested non-incremental but already the case");
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str, -1000);
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_TRANSPORT_FAILURE, str);
    }

    void onPackageBackupTransportFailure(java.lang.String str) {
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str, -1000);
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_TRANSPORT_FAILURE, str);
    }

    void onPackageBackupTransportError(java.lang.String str, java.lang.Exception exc) {
        android.util.Slog.e(TAG, "Transport error backing up " + str, exc);
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, str, -1000);
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_TRANSPORT_FAILURE, str);
    }

    void onCloseFileDescriptorError(java.lang.String str) {
        android.util.Slog.w(TAG, "Error closing " + str + " file-descriptor");
    }

    void onCancel() {
    }

    void onAgentTimedOut(@android.annotation.Nullable android.content.pm.PackageInfo packageInfo) {
        java.lang.String packageName = getPackageName(packageInfo);
        android.util.Slog.i(TAG, "Agent " + packageName + " timed out");
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_AGENT_FAILURE, packageName);
        this.mBackupManagerMonitorEventSender.monitorEvent(21, packageInfo, 2, com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.LOG_CANCEL_ALL", false));
    }

    void onAgentCancelled(@android.annotation.Nullable android.content.pm.PackageInfo packageInfo) {
        java.lang.String packageName = getPackageName(packageInfo);
        android.util.Slog.i(TAG, "Cancel backing up " + packageName);
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_AGENT_FAILURE, packageName);
        this.mBackupManagerMonitorEventSender.monitorEvent(21, packageInfo, 2, com.android.server.backup.utils.BackupManagerMonitorEventSender.putMonitoringExtra((android.os.Bundle) null, "android.app.backup.extra.LOG_CANCEL_ALL", true));
    }

    void onAgentResultError(@android.annotation.Nullable android.content.pm.PackageInfo packageInfo) {
        java.lang.String packageName = getPackageName(packageInfo);
        com.android.server.backup.utils.BackupObserverUtils.sendBackupOnPackageResult(this.mObserver, packageName, -1003);
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_AGENT_FAILURE, packageName, "result error");
        android.util.Slog.w(TAG, "Agent " + packageName + " error in onBackup()");
    }

    private java.lang.String getPackageName(@android.annotation.Nullable android.content.pm.PackageInfo packageInfo) {
        return packageInfo != null ? packageInfo.packageName : "no_package_yet";
    }

    void onRevertTask() {
    }

    void onTransportRequestBackupTimeError(java.lang.Exception exc) {
        android.util.Slog.w(TAG, "Unable to contact transport for recommended backoff: " + exc);
    }

    void onRemoteCallReturned(com.android.server.backup.remote.RemoteResult remoteResult, java.lang.String str) {
    }

    void onJournalDeleteFailed(com.android.server.backup.DataChangedJournal dataChangedJournal) {
        android.util.Slog.e(TAG, "Unable to remove backup journal file " + dataChangedJournal);
    }

    void onSetCurrentTokenError(java.lang.Exception exc) {
        android.util.Slog.e(TAG, "Transport threw reporting restore set: " + exc);
    }

    void onTransportNotInitialized(@android.annotation.Nullable java.lang.String str) {
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_RESET, str);
    }

    void onPendingInitializeTransportError(java.lang.Exception exc) {
        android.util.Slog.w(TAG, "Failed to query transport name for pending init: " + exc);
    }

    void onBackupFinished(int i) {
        com.android.server.backup.utils.BackupObserverUtils.sendBackupFinished(this.mObserver, i);
    }

    void onStartFullBackup(java.util.List<java.lang.String> list) {
        android.util.Slog.d(TAG, "Starting full backups for: " + list);
    }

    void onTaskFinished() {
        android.util.Slog.i(TAG, "K/V backup pass finished");
    }
}
