package com.android.server.backup.params;

/* loaded from: classes.dex */
public class BackupParams {
    public java.lang.String dirName;
    public java.util.ArrayList<java.lang.String> fullPackages;
    public java.util.ArrayList<java.lang.String> kvPackages;
    public com.android.server.backup.internal.OnTaskFinishedListener listener;
    public com.android.server.backup.utils.BackupEligibilityRules mBackupEligibilityRules;
    public com.android.server.backup.transport.TransportConnection mTransportConnection;
    public android.app.backup.IBackupManagerMonitor monitor;
    public boolean nonIncrementalBackup;
    public android.app.backup.IBackupObserver observer;
    public boolean userInitiated;

    public BackupParams(com.android.server.backup.transport.TransportConnection transportConnection, java.lang.String str, java.util.ArrayList<java.lang.String> arrayList, java.util.ArrayList<java.lang.String> arrayList2, android.app.backup.IBackupObserver iBackupObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener, boolean z, boolean z2, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        this.mTransportConnection = transportConnection;
        this.dirName = str;
        this.kvPackages = arrayList;
        this.fullPackages = arrayList2;
        this.observer = iBackupObserver;
        this.monitor = iBackupManagerMonitor;
        this.listener = onTaskFinishedListener;
        this.userInitiated = z;
        this.nonIncrementalBackup = z2;
        this.mBackupEligibilityRules = backupEligibilityRules;
    }
}
