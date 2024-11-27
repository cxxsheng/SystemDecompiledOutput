package com.android.server.backup.fullbackup;

/* loaded from: classes.dex */
public class FullBackupEntry implements java.lang.Comparable<com.android.server.backup.fullbackup.FullBackupEntry> {
    public long lastBackup;
    public java.lang.String packageName;

    public FullBackupEntry(java.lang.String str, long j) {
        this.packageName = str;
        this.lastBackup = j;
    }

    @Override // java.lang.Comparable
    public int compareTo(com.android.server.backup.fullbackup.FullBackupEntry fullBackupEntry) {
        if (this.lastBackup < fullBackupEntry.lastBackup) {
            return -1;
        }
        if (this.lastBackup > fullBackupEntry.lastBackup) {
            return 1;
        }
        return 0;
    }
}
