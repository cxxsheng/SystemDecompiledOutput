package com.android.internal.logging.testing;

/* loaded from: classes4.dex */
public class FakeMetricsLogger extends com.android.internal.logging.MetricsLogger {
    private java.util.Queue<android.metrics.LogMaker> logs = new java.util.LinkedList();

    @Override // com.android.internal.logging.MetricsLogger
    protected void saveLog(android.metrics.LogMaker logMaker) {
        this.logs.offer(logMaker);
    }

    public java.util.Queue<android.metrics.LogMaker> getLogs() {
        return this.logs;
    }

    public void reset() {
        this.logs.clear();
    }
}
