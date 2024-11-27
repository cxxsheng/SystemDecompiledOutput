package com.android.server.backup.params;

/* loaded from: classes.dex */
public class RestoreParams {
    public final com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules;

    @android.annotation.Nullable
    public final java.lang.String[] filterSet;
    public final boolean isSystemRestore;
    public final com.android.server.backup.internal.OnTaskFinishedListener listener;
    public final com.android.server.backup.transport.TransportConnection mTransportConnection;
    public final android.app.backup.IBackupManagerMonitor monitor;
    public final android.app.backup.IRestoreObserver observer;

    @android.annotation.Nullable
    public final android.content.pm.PackageInfo packageInfo;
    public final int pmToken;
    public final long token;

    public static com.android.server.backup.params.RestoreParams createForSinglePackage(com.android.server.backup.transport.TransportConnection transportConnection, android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, long j, android.content.pm.PackageInfo packageInfo, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        return new com.android.server.backup.params.RestoreParams(transportConnection, iRestoreObserver, iBackupManagerMonitor, j, packageInfo, 0, false, null, onTaskFinishedListener, backupEligibilityRules);
    }

    public static com.android.server.backup.params.RestoreParams createForRestoreAtInstall(com.android.server.backup.transport.TransportConnection transportConnection, android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, long j, java.lang.String str, int i, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        return new com.android.server.backup.params.RestoreParams(transportConnection, iRestoreObserver, iBackupManagerMonitor, j, null, i, false, new java.lang.String[]{str}, onTaskFinishedListener, backupEligibilityRules);
    }

    public static com.android.server.backup.params.RestoreParams createForRestoreAll(com.android.server.backup.transport.TransportConnection transportConnection, android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, long j, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        return new com.android.server.backup.params.RestoreParams(transportConnection, iRestoreObserver, iBackupManagerMonitor, j, null, 0, true, null, onTaskFinishedListener, backupEligibilityRules);
    }

    public static com.android.server.backup.params.RestoreParams createForRestorePackages(com.android.server.backup.transport.TransportConnection transportConnection, android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, long j, java.lang.String[] strArr, boolean z, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        return new com.android.server.backup.params.RestoreParams(transportConnection, iRestoreObserver, iBackupManagerMonitor, j, null, 0, z, strArr, onTaskFinishedListener, backupEligibilityRules);
    }

    private RestoreParams(com.android.server.backup.transport.TransportConnection transportConnection, android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, long j, @android.annotation.Nullable android.content.pm.PackageInfo packageInfo, int i, boolean z, @android.annotation.Nullable java.lang.String[] strArr, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        this.mTransportConnection = transportConnection;
        this.observer = iRestoreObserver;
        this.monitor = iBackupManagerMonitor;
        this.token = j;
        this.packageInfo = packageInfo;
        this.pmToken = i;
        this.isSystemRestore = z;
        this.filterSet = strArr;
        this.listener = onTaskFinishedListener;
        this.backupEligibilityRules = backupEligibilityRules;
    }
}
