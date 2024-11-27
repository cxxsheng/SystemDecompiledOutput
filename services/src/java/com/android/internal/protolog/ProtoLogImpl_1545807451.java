package com.android.internal.protolog;

/* loaded from: classes.dex */
public class ProtoLogImpl_1545807451 {

    @com.android.internal.protolog.common.ProtoLogToolInjected(com.android.internal.protolog.common.ProtoLogToolInjected.Value.LEGACY_OUTPUT_FILE_PATH)
    private static final java.lang.String sLegacyOutputFilePath = "/data/misc/wmtrace/wm_log.winscope";

    @com.android.internal.protolog.common.ProtoLogToolInjected(com.android.internal.protolog.common.ProtoLogToolInjected.Value.LEGACY_VIEWER_CONFIG_PATH)
    private static final java.lang.String sLegacyViewerConfigPath = "/system/etc/protolog.conf.json.gz";
    private static com.android.internal.protolog.common.IProtoLog sServiceInstance = null;

    @com.android.internal.protolog.common.ProtoLogToolInjected(com.android.internal.protolog.common.ProtoLogToolInjected.Value.VIEWER_CONFIG_PATH)
    private static final java.lang.String sViewerConfigPath = "/etc/core.protolog.pb";

    public static void d(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, @android.annotation.Nullable java.lang.String str, java.lang.Object... objArr) {
        getSingleInstance().log(com.android.internal.protolog.common.LogLevel.DEBUG, iProtoLogGroup, j, i, str, objArr);
    }

    public static void v(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, @android.annotation.Nullable java.lang.String str, java.lang.Object... objArr) {
        getSingleInstance().log(com.android.internal.protolog.common.LogLevel.VERBOSE, iProtoLogGroup, j, i, str, objArr);
    }

    public static void i(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, @android.annotation.Nullable java.lang.String str, java.lang.Object... objArr) {
        getSingleInstance().log(com.android.internal.protolog.common.LogLevel.INFO, iProtoLogGroup, j, i, str, objArr);
    }

    public static void w(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, @android.annotation.Nullable java.lang.String str, java.lang.Object... objArr) {
        getSingleInstance().log(com.android.internal.protolog.common.LogLevel.WARN, iProtoLogGroup, j, i, str, objArr);
    }

    public static void e(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, @android.annotation.Nullable java.lang.String str, java.lang.Object... objArr) {
        getSingleInstance().log(com.android.internal.protolog.common.LogLevel.ERROR, iProtoLogGroup, j, i, str, objArr);
    }

    public static void wtf(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, @android.annotation.Nullable java.lang.String str, java.lang.Object... objArr) {
        getSingleInstance().log(com.android.internal.protolog.common.LogLevel.WTF, iProtoLogGroup, j, i, str, objArr);
    }

    public static boolean isEnabled(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup) {
        return true;
    }

    public static synchronized com.android.internal.protolog.common.IProtoLog getSingleInstance() {
        com.android.internal.protolog.common.IProtoLog iProtoLog;
        synchronized (com.android.internal.protolog.ProtoLogImpl_1545807451.class) {
            try {
                if (sServiceInstance == null) {
                    if (android.tracing.Flags.perfettoProtologTracing()) {
                        sServiceInstance = new com.android.internal.protolog.PerfettoProtoLogImpl(sViewerConfigPath);
                    } else {
                        sServiceInstance = new com.android.internal.protolog.LegacyProtoLogImpl(sLegacyOutputFilePath, sLegacyViewerConfigPath);
                    }
                }
                iProtoLog = sServiceInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return iProtoLog;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static synchronized void setSingleInstance(@android.annotation.Nullable com.android.internal.protolog.common.IProtoLog iProtoLog) {
        synchronized (com.android.internal.protolog.ProtoLogImpl_1545807451.class) {
            sServiceInstance = iProtoLog;
        }
    }
}
