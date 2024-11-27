package com.android.server.am;

/* loaded from: classes.dex */
public class TraceErrorLogger {
    private static final java.lang.String COUNTER_PREFIX = "ErrorId:";
    private static final int PLACEHOLDER_VALUE = 1;

    public boolean isAddErrorIdEnabled() {
        return true;
    }

    public java.util.UUID generateErrorId() {
        return java.util.UUID.randomUUID();
    }

    public void addProcessInfoAndErrorIdToTrace(java.lang.String str, int i, java.util.UUID uuid) {
        android.os.Trace.traceCounter(64L, COUNTER_PREFIX + str + " " + i + "#" + uuid.toString(), 1);
    }

    public void addSubjectToTrace(java.lang.String str, java.util.UUID uuid) {
        android.os.Trace.traceCounter(64L, java.lang.String.format("Subject(for ErrorId %s):%s", uuid.toString(), str), 1);
    }
}
