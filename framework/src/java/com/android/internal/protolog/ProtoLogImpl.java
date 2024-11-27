package com.android.internal.protolog;

/* loaded from: classes5.dex */
public class ProtoLogImpl {
    private static java.lang.String sLegacyOutputFilePath;
    private static java.lang.String sLegacyViewerConfigPath;
    private static com.android.internal.protolog.common.IProtoLog sServiceInstance = null;
    private static java.lang.String sViewerConfigPath;

    public static void d(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, java.lang.String str, java.lang.Object... objArr) {
        getSingleInstance().log(com.android.internal.protolog.common.LogLevel.DEBUG, iProtoLogGroup, j, i, str, objArr);
    }

    public static void v(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, java.lang.String str, java.lang.Object... objArr) {
        getSingleInstance().log(com.android.internal.protolog.common.LogLevel.VERBOSE, iProtoLogGroup, j, i, str, objArr);
    }

    public static void i(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, java.lang.String str, java.lang.Object... objArr) {
        getSingleInstance().log(com.android.internal.protolog.common.LogLevel.INFO, iProtoLogGroup, j, i, str, objArr);
    }

    public static void w(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, java.lang.String str, java.lang.Object... objArr) {
        getSingleInstance().log(com.android.internal.protolog.common.LogLevel.WARN, iProtoLogGroup, j, i, str, objArr);
    }

    public static void e(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, java.lang.String str, java.lang.Object... objArr) {
        getSingleInstance().log(com.android.internal.protolog.common.LogLevel.ERROR, iProtoLogGroup, j, i, str, objArr);
    }

    public static void wtf(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, java.lang.String str, java.lang.Object... objArr) {
        getSingleInstance().log(com.android.internal.protolog.common.LogLevel.WTF, iProtoLogGroup, j, i, str, objArr);
    }

    public static boolean isEnabled(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup) {
        return true;
    }

    public static synchronized com.android.internal.protolog.common.IProtoLog getSingleInstance() {
        com.android.internal.protolog.common.IProtoLog iProtoLog;
        synchronized (com.android.internal.protolog.ProtoLogImpl.class) {
            if (sServiceInstance == null) {
                if (android.tracing.Flags.perfettoProtologTracing()) {
                    sServiceInstance = new com.android.internal.protolog.PerfettoProtoLogImpl(sViewerConfigPath);
                } else {
                    sServiceInstance = new com.android.internal.protolog.LegacyProtoLogImpl(sLegacyOutputFilePath, sLegacyViewerConfigPath);
                }
            }
            iProtoLog = sServiceInstance;
        }
        return iProtoLog;
    }

    public static synchronized void setSingleInstance(com.android.internal.protolog.common.IProtoLog iProtoLog) {
        synchronized (com.android.internal.protolog.ProtoLogImpl.class) {
            sServiceInstance = iProtoLog;
        }
    }
}
