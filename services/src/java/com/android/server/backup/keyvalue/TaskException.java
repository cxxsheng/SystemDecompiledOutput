package com.android.server.backup.keyvalue;

/* loaded from: classes.dex */
class TaskException extends com.android.server.backup.keyvalue.BackupException {
    private static final int DEFAULT_STATUS = -1000;
    private final boolean mStateCompromised;
    private final int mStatus;

    static com.android.server.backup.keyvalue.TaskException stateCompromised() {
        return new com.android.server.backup.keyvalue.TaskException(true, -1000);
    }

    static com.android.server.backup.keyvalue.TaskException stateCompromised(java.lang.Exception exc) {
        if (exc instanceof com.android.server.backup.keyvalue.TaskException) {
            return new com.android.server.backup.keyvalue.TaskException(exc, true, ((com.android.server.backup.keyvalue.TaskException) exc).getStatus());
        }
        return new com.android.server.backup.keyvalue.TaskException(exc, true, -1000);
    }

    static com.android.server.backup.keyvalue.TaskException forStatus(int i) {
        com.android.internal.util.Preconditions.checkArgument(i != 0, "Exception based on TRANSPORT_OK");
        return new com.android.server.backup.keyvalue.TaskException(false, i);
    }

    static com.android.server.backup.keyvalue.TaskException causedBy(java.lang.Exception exc) {
        if (exc instanceof com.android.server.backup.keyvalue.TaskException) {
            return (com.android.server.backup.keyvalue.TaskException) exc;
        }
        return new com.android.server.backup.keyvalue.TaskException(exc, false, -1000);
    }

    static com.android.server.backup.keyvalue.TaskException create() {
        return new com.android.server.backup.keyvalue.TaskException(false, -1000);
    }

    private TaskException(java.lang.Exception exc, boolean z, int i) {
        super(exc);
        this.mStateCompromised = z;
        this.mStatus = i;
    }

    private TaskException(boolean z, int i) {
        this.mStateCompromised = z;
        this.mStatus = i;
    }

    boolean isStateCompromised() {
        return this.mStateCompromised;
    }

    int getStatus() {
        return this.mStatus;
    }
}
