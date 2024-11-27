package com.android.server.backup.keyvalue;

/* loaded from: classes.dex */
class AgentException extends com.android.server.backup.keyvalue.BackupException {
    private final boolean mTransitory;

    static com.android.server.backup.keyvalue.AgentException transitory() {
        return new com.android.server.backup.keyvalue.AgentException(true);
    }

    static com.android.server.backup.keyvalue.AgentException transitory(java.lang.Exception exc) {
        return new com.android.server.backup.keyvalue.AgentException(true, exc);
    }

    static com.android.server.backup.keyvalue.AgentException permanent() {
        return new com.android.server.backup.keyvalue.AgentException(false);
    }

    static com.android.server.backup.keyvalue.AgentException permanent(java.lang.Exception exc) {
        return new com.android.server.backup.keyvalue.AgentException(false, exc);
    }

    private AgentException(boolean z) {
        this.mTransitory = z;
    }

    private AgentException(boolean z, java.lang.Exception exc) {
        super(exc);
        this.mTransitory = z;
    }

    boolean isTransitory() {
        return this.mTransitory;
    }
}
