package com.android.server.backup.utils;

/* loaded from: classes.dex */
public class BackupManagerMonitorEventSender {
    private static final int AGENT_LOGGER_RESULTS_TIMEOUT_MILLIS = 500;
    private final com.android.server.backup.utils.BackupManagerMonitorDumpsysUtils mBackupManagerMonitorDumpsysUtils;

    @android.annotation.Nullable
    private android.app.backup.IBackupManagerMonitor mMonitor;

    public BackupManagerMonitorEventSender(@android.annotation.Nullable android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) {
        this.mMonitor = iBackupManagerMonitor;
        this.mBackupManagerMonitorDumpsysUtils = new com.android.server.backup.utils.BackupManagerMonitorDumpsysUtils();
    }

    @com.android.internal.annotations.VisibleForTesting
    BackupManagerMonitorEventSender(@android.annotation.Nullable android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, com.android.server.backup.utils.BackupManagerMonitorDumpsysUtils backupManagerMonitorDumpsysUtils) {
        this.mMonitor = iBackupManagerMonitor;
        this.mBackupManagerMonitorDumpsysUtils = backupManagerMonitorDumpsysUtils;
    }

    public void setMonitor(android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) {
        this.mMonitor = iBackupManagerMonitor;
    }

    public android.app.backup.IBackupManagerMonitor getMonitor() {
        return this.mMonitor;
    }

    public void monitorEvent(int i, android.content.pm.PackageInfo packageInfo, int i2, android.os.Bundle bundle) {
        try {
            android.os.Bundle bundle2 = new android.os.Bundle();
            bundle2.putInt("android.app.backup.extra.LOG_EVENT_ID", i);
            bundle2.putInt("android.app.backup.extra.LOG_EVENT_CATEGORY", i2);
            if (packageInfo != null) {
                bundle2.putString("android.app.backup.extra.LOG_EVENT_PACKAGE_NAME", packageInfo.packageName);
                bundle2.putInt("android.app.backup.extra.LOG_EVENT_PACKAGE_VERSION", packageInfo.versionCode);
                bundle2.putLong("android.app.backup.extra.LOG_EVENT_PACKAGE_FULL_VERSION", packageInfo.getLongVersionCode());
            }
            if (bundle != null) {
                bundle2.putAll(bundle);
                if (bundle.containsKey("android.app.backup.extra.OPERATION_TYPE") && bundle.getInt("android.app.backup.extra.OPERATION_TYPE") == 1) {
                    this.mBackupManagerMonitorDumpsysUtils.parseBackupManagerMonitorRestoreEventForDumpsys(bundle2);
                }
            }
            if (this.mMonitor == null) {
                android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "backup manager monitor is null unable to send event");
            } else {
                this.mMonitor.onEvent(bundle2);
            }
        } catch (android.os.RemoteException e) {
            this.mMonitor = null;
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "backup manager monitor went away");
        }
    }

    public void monitorAgentLoggingResults(android.content.pm.PackageInfo packageInfo, android.app.IBackupAgent iBackupAgent) {
        if (this.mMonitor == null) {
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "backup manager monitor is null unable to send event" + packageInfo);
        }
        try {
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            com.android.internal.infra.AndroidFuture androidFuture2 = new com.android.internal.infra.AndroidFuture();
            iBackupAgent.getLoggerResults(androidFuture);
            iBackupAgent.getOperationType(androidFuture2);
            sendAgentLoggingResults(packageInfo, (java.util.List) androidFuture.get(500L, java.util.concurrent.TimeUnit.MILLISECONDS), ((java.lang.Integer) androidFuture2.get(500L, java.util.concurrent.TimeUnit.MILLISECONDS)).intValue());
        } catch (java.util.concurrent.TimeoutException e) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Timeout while waiting to retrieve logging results from agent", e);
        } catch (java.lang.Exception e2) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Failed to retrieve logging results from agent", e2);
        }
    }

    public void sendAgentLoggingResults(android.content.pm.PackageInfo packageInfo, java.util.List<android.app.backup.BackupRestoreEventLogger.DataTypeResult> list, int i) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelableList("android.app.backup.extra.LOG_AGENT_LOGGING_RESULTS", list);
        bundle.putInt("android.app.backup.extra.OPERATION_TYPE", i);
        monitorEvent(52, packageInfo, 2, bundle);
    }

    public static android.os.Bundle putMonitoringExtra(android.os.Bundle bundle, java.lang.String str, java.lang.String str2) {
        if (bundle == null) {
            bundle = new android.os.Bundle();
        }
        bundle.putString(str, str2);
        return bundle;
    }

    public static android.os.Bundle putMonitoringExtra(android.os.Bundle bundle, java.lang.String str, long j) {
        if (bundle == null) {
            bundle = new android.os.Bundle();
        }
        bundle.putLong(str, j);
        return bundle;
    }

    public static android.os.Bundle putMonitoringExtra(android.os.Bundle bundle, java.lang.String str, boolean z) {
        if (bundle == null) {
            bundle = new android.os.Bundle();
        }
        bundle.putBoolean(str, z);
        return bundle;
    }

    public static android.os.Bundle putMonitoringExtra(android.os.Bundle bundle, java.lang.String str, int i) {
        if (bundle == null) {
            bundle = new android.os.Bundle();
        }
        bundle.putInt(str, i);
        return bundle;
    }
}
