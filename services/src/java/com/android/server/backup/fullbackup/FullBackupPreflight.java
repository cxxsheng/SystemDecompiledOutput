package com.android.server.backup.fullbackup;

/* loaded from: classes.dex */
public interface FullBackupPreflight {
    long getExpectedSizeOrErrorCode();

    int preflightFullBackup(android.content.pm.PackageInfo packageInfo, android.app.IBackupAgent iBackupAgent);
}
