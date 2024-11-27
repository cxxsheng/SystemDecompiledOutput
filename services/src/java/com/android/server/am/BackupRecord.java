package com.android.server.am;

/* loaded from: classes.dex */
final class BackupRecord {
    public static final int BACKUP_FULL = 1;
    public static final int BACKUP_NORMAL = 0;
    public static final int RESTORE = 2;
    public static final int RESTORE_FULL = 3;
    com.android.server.am.ProcessRecord app;
    final android.content.pm.ApplicationInfo appInfo;
    final int backupDestination;
    final int backupMode;
    java.lang.String stringName;
    final int userId;

    BackupRecord(android.content.pm.ApplicationInfo applicationInfo, int i, int i2, int i3) {
        this.appInfo = applicationInfo;
        this.backupMode = i;
        this.userId = i2;
        this.backupDestination = i3;
    }

    public java.lang.String toString() {
        if (this.stringName != null) {
            return this.stringName;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("BackupRecord{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        sb.append(this.appInfo.packageName);
        sb.append(' ');
        sb.append(this.appInfo.name);
        sb.append(' ');
        sb.append(this.appInfo.backupAgentName);
        sb.append('}');
        java.lang.String sb2 = sb.toString();
        this.stringName = sb2;
        return sb2;
    }
}
