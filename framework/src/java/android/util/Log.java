package android.util;

/* loaded from: classes3.dex */
public final class Log {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int LOG_ID_CRASH = 4;
    public static final int LOG_ID_EVENTS = 2;
    public static final int LOG_ID_MAIN = 0;
    public static final int LOG_ID_RADIO = 1;
    public static final int LOG_ID_SYSTEM = 3;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private static android.util.Log.TerribleFailureHandler sWtfHandler = new android.util.Log.TerribleFailureHandler() { // from class: android.util.Log.1
        @Override // android.util.Log.TerribleFailureHandler
        public void onTerribleFailure(java.lang.String str, android.util.Log.TerribleFailure terribleFailure, boolean z) {
            com.android.internal.os.RuntimeInit.wtf(str, terribleFailure, z);
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Level {
    }

    public interface TerribleFailureHandler {
        void onTerribleFailure(java.lang.String str, android.util.Log.TerribleFailure terribleFailure, boolean z);
    }

    @dalvik.annotation.optimization.FastNative
    public static native boolean isLoggable(java.lang.String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int logger_entry_max_payload_native();

    public static native int println_native(int i, int i2, java.lang.String str, java.lang.String str2);

    public static class TerribleFailure extends java.lang.Exception {
        TerribleFailure(java.lang.String str, java.lang.Throwable th) {
            super(str, th);
        }
    }

    private Log() {
    }

    public static int v(java.lang.String str, java.lang.String str2) {
        return println_native(0, 2, str, str2);
    }

    public static int v(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return printlns(0, 2, str, str2, th);
    }

    public static int d(java.lang.String str, java.lang.String str2) {
        return println_native(0, 3, str, str2);
    }

    public static int d(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return printlns(0, 3, str, str2, th);
    }

    public static int i(java.lang.String str, java.lang.String str2) {
        return println_native(0, 4, str, str2);
    }

    public static int i(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return printlns(0, 4, str, str2, th);
    }

    public static int w(java.lang.String str, java.lang.String str2) {
        return println_native(0, 5, str, str2);
    }

    public static int w(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return printlns(0, 5, str, str2, th);
    }

    public static int w(java.lang.String str, java.lang.Throwable th) {
        return printlns(0, 5, str, "", th);
    }

    public static int e(java.lang.String str, java.lang.String str2) {
        return println_native(0, 6, str, str2);
    }

    public static int e(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return printlns(0, 6, str, str2, th);
    }

    public static int wtf(java.lang.String str, java.lang.String str2) {
        return wtf(0, str, str2, null, false, false);
    }

    public static int wtfStack(java.lang.String str, java.lang.String str2) {
        return wtf(0, str, str2, null, true, false);
    }

    public static int wtf(java.lang.String str, java.lang.Throwable th) {
        return wtf(0, str, th.getMessage(), th, false, false);
    }

    public static int wtf(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return wtf(0, str, str2, th, false, false);
    }

    static int wtf(int i, java.lang.String str, java.lang.String str2, java.lang.Throwable th, boolean z, boolean z2) {
        android.util.Log.TerribleFailure terribleFailure = new android.util.Log.TerribleFailure(str2, th);
        if (z) {
            th = terribleFailure;
        }
        int printlns = printlns(i, 6, str, str2, th);
        sWtfHandler.onTerribleFailure(str, terribleFailure, z2);
        return printlns;
    }

    static void wtfQuiet(int i, java.lang.String str, java.lang.String str2, boolean z) {
        sWtfHandler.onTerribleFailure(str, new android.util.Log.TerribleFailure(str2, null), z);
    }

    public static android.util.Log.TerribleFailureHandler setWtfHandler(android.util.Log.TerribleFailureHandler terribleFailureHandler) {
        if (terribleFailureHandler == null) {
            throw new java.lang.NullPointerException("handler == null");
        }
        android.util.Log.TerribleFailureHandler terribleFailureHandler2 = sWtfHandler;
        sWtfHandler = terribleFailureHandler;
        return terribleFailureHandler2;
    }

    public static java.lang.String getStackTraceString(java.lang.Throwable th) {
        if (th == null) {
            return "";
        }
        for (java.lang.Throwable th2 = th; th2 != null; th2 = th2.getCause()) {
            if (th2 instanceof java.net.UnknownHostException) {
                return "";
            }
        }
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter((java.io.Writer) stringWriter, false, 256);
        th.printStackTrace(fastPrintWriter);
        fastPrintWriter.flush();
        return stringWriter.toString();
    }

    public static int println(int i, java.lang.String str, java.lang.String str2) {
        return println_native(0, i, str, str2);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static int logToRadioBuffer(int i, java.lang.String str, java.lang.String str2) {
        return println_native(1, i, str, str2);
    }

    public static int printlns(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        android.util.Log.ImmediateLogWriter immediateLogWriter = new android.util.Log.ImmediateLogWriter(i, i2, str);
        com.android.internal.util.LineBreakBufferedWriter lineBreakBufferedWriter = new com.android.internal.util.LineBreakBufferedWriter(immediateLogWriter, java.lang.Math.max(((android.util.Log.PreloadHolder.LOGGER_ENTRY_MAX_PAYLOAD - 2) - (str != null ? str.length() : 0)) - 32, 100));
        lineBreakBufferedWriter.println(str2);
        if (th != null) {
            java.lang.Throwable th2 = th;
            while (true) {
                if (th2 == null || (th2 instanceof java.net.UnknownHostException)) {
                    break;
                }
                if (th2 instanceof android.os.DeadSystemException) {
                    lineBreakBufferedWriter.println("DeadSystemException: The system died; earlier logs will point to the root cause");
                    break;
                }
                th2 = th2.getCause();
            }
            if (th2 == null) {
                th.printStackTrace(lineBreakBufferedWriter);
            }
        }
        lineBreakBufferedWriter.flush();
        return immediateLogWriter.getWritten();
    }

    static class PreloadHolder {
        public static final int LOGGER_ENTRY_MAX_PAYLOAD = android.util.Log.logger_entry_max_payload_native();

        PreloadHolder() {
        }
    }

    private static class ImmediateLogWriter extends java.io.Writer {
        private int bufID;
        private int priority;
        private java.lang.String tag;
        private int written = 0;

        public ImmediateLogWriter(int i, int i2, java.lang.String str) {
            this.bufID = i;
            this.priority = i2;
            this.tag = str;
        }

        public int getWritten() {
            return this.written;
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i, int i2) {
            this.written += android.util.Log.println_native(this.bufID, this.priority, this.tag, new java.lang.String(cArr, i, i2));
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }
    }
}
