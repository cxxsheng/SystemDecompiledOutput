package com.android.server.backup.internal;

/* loaded from: classes.dex */
public class Operation {
    public final com.android.server.backup.BackupRestoreTask callback;
    public int state;
    public final int type;

    public Operation(int i, com.android.server.backup.BackupRestoreTask backupRestoreTask, int i2) {
        this.state = i;
        this.callback = backupRestoreTask;
        this.type = i2;
    }
}
