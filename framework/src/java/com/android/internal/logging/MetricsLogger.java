package com.android.internal.logging;

/* loaded from: classes4.dex */
public class MetricsLogger {
    public static final int LOGTAG = 524292;
    public static final int VIEW_UNKNOWN = 0;
    private static com.android.internal.logging.MetricsLogger sMetricsLogger;

    private static com.android.internal.logging.MetricsLogger getLogger() {
        if (sMetricsLogger == null) {
            sMetricsLogger = new com.android.internal.logging.MetricsLogger();
        }
        return sMetricsLogger;
    }

    protected void saveLog(android.metrics.LogMaker logMaker) {
        com.android.internal.logging.EventLogTags.writeSysuiMultiAction(logMaker.serialize());
    }

    public void write(android.metrics.LogMaker logMaker) {
        if (logMaker.getType() == 0) {
            logMaker.setType(4);
        }
        saveLog(logMaker);
    }

    public void count(java.lang.String str, int i) {
        saveLog(new android.metrics.LogMaker(803).setCounterName(str).setCounterValue(i));
    }

    public void histogram(java.lang.String str, int i) {
        saveLog(new android.metrics.LogMaker(804).setCounterName(str).setCounterBucket(i).setCounterValue(1));
    }

    public void visible(int i) throws java.lang.IllegalArgumentException {
        if (android.os.Build.IS_DEBUGGABLE && i == 0) {
            throw new java.lang.IllegalArgumentException("Must define metric category");
        }
        saveLog(new android.metrics.LogMaker(i).setType(1));
    }

    public void hidden(int i) throws java.lang.IllegalArgumentException {
        if (android.os.Build.IS_DEBUGGABLE && i == 0) {
            throw new java.lang.IllegalArgumentException("Must define metric category");
        }
        saveLog(new android.metrics.LogMaker(i).setType(2));
    }

    public void visibility(int i, boolean z) throws java.lang.IllegalArgumentException {
        if (z) {
            visible(i);
        } else {
            hidden(i);
        }
    }

    public void visibility(int i, int i2) throws java.lang.IllegalArgumentException {
        visibility(i, i2 == 0);
    }

    public void action(int i) {
        saveLog(new android.metrics.LogMaker(i).setType(4));
    }

    public void action(int i, int i2) {
        saveLog(new android.metrics.LogMaker(i).setType(4).setSubtype(i2));
    }

    public void action(int i, boolean z) {
        saveLog(new android.metrics.LogMaker(i).setType(4).setSubtype(z ? 1 : 0));
    }

    public void action(int i, java.lang.String str) {
        if (android.os.Build.IS_DEBUGGABLE && i == 0) {
            throw new java.lang.IllegalArgumentException("Must define metric category");
        }
        saveLog(new android.metrics.LogMaker(i).setType(4).setPackageName(str));
    }

    @java.lang.Deprecated
    public static void visible(android.content.Context context, int i) throws java.lang.IllegalArgumentException {
        getLogger().visible(i);
    }

    @java.lang.Deprecated
    public static void hidden(android.content.Context context, int i) throws java.lang.IllegalArgumentException {
        getLogger().hidden(i);
    }

    @java.lang.Deprecated
    public static void visibility(android.content.Context context, int i, boolean z) throws java.lang.IllegalArgumentException {
        getLogger().visibility(i, z);
    }

    @java.lang.Deprecated
    public static void visibility(android.content.Context context, int i, int i2) throws java.lang.IllegalArgumentException {
        visibility(context, i, i2 == 0);
    }

    @java.lang.Deprecated
    public static void action(android.content.Context context, int i) {
        getLogger().action(i);
    }

    @java.lang.Deprecated
    public static void action(android.content.Context context, int i, int i2) {
        getLogger().action(i, i2);
    }

    @java.lang.Deprecated
    public static void action(android.content.Context context, int i, boolean z) {
        getLogger().action(i, z);
    }

    @java.lang.Deprecated
    public static void action(android.metrics.LogMaker logMaker) {
        getLogger().write(logMaker);
    }

    @java.lang.Deprecated
    public static void action(android.content.Context context, int i, java.lang.String str) {
        getLogger().action(i, str);
    }

    @java.lang.Deprecated
    public static void count(android.content.Context context, java.lang.String str, int i) {
        getLogger().count(str, i);
    }

    @java.lang.Deprecated
    public static void histogram(android.content.Context context, java.lang.String str, int i) {
        getLogger().histogram(str, i);
    }
}
