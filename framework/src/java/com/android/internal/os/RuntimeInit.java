package com.android.internal.os;

/* loaded from: classes4.dex */
public class RuntimeInit {
    static final boolean DEBUG = false;
    private static final java.lang.String SYSPROP_CRASH_COUNT = "sys.system_server.crash_java";
    static final java.lang.String TAG = "AndroidRuntime";
    private static boolean initialized;
    private static android.os.IBinder mApplicationObject;
    private static int mCrashCount;
    private static volatile boolean mCrashing = false;
    private static volatile com.android.internal.os.RuntimeInit.ApplicationWtfHandler sDefaultApplicationWtfHandler;
    public static java.io.PrintStream sErr$ravenwood;
    public static java.io.PrintStream sOut$ravenwood;

    public interface ApplicationWtfHandler {
        boolean handleApplicationWtf(android.os.IBinder iBinder, java.lang.String str, boolean z, android.app.ApplicationErrorReport.ParcelableCrashInfo parcelableCrashInfo, int i);
    }

    private static final native void nativeFinishInit();

    private static final native void nativeSetExitWithoutCleanup(boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static int Clog_e(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        return android.util.Log.printlns(4, 6, str, str2, th);
    }

    public static void logUncaught(java.lang.String str, java.lang.String str2, int i, java.lang.Throwable th) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("FATAL EXCEPTION: ").append(str).append("\n");
        if (str2 != null) {
            sb.append("Process: ").append(str2).append(", ");
        }
        sb.append("PID: ").append(i);
        Clog_e(TAG, sb.toString(), th);
    }

    private static class LoggingHandler implements java.lang.Thread.UncaughtExceptionHandler {
        public volatile boolean mTriggered;

        private LoggingHandler() {
            this.mTriggered = false;
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(java.lang.Thread thread, java.lang.Throwable th) {
            this.mTriggered = true;
            if (com.android.internal.os.RuntimeInit.mCrashing) {
                return;
            }
            if (com.android.internal.os.RuntimeInit.mApplicationObject == null && 1000 == android.os.Process.myUid()) {
                com.android.internal.os.RuntimeInit.Clog_e(com.android.internal.os.RuntimeInit.TAG, "*** FATAL EXCEPTION IN SYSTEM PROCESS: " + thread.getName(), th);
                com.android.internal.os.RuntimeInit.mCrashCount = android.os.SystemProperties.getInt(com.android.internal.os.RuntimeInit.SYSPROP_CRASH_COUNT, 0) + 1;
                android.os.SystemProperties.set(com.android.internal.os.RuntimeInit.SYSPROP_CRASH_COUNT, java.lang.String.valueOf(com.android.internal.os.RuntimeInit.mCrashCount));
                return;
            }
            com.android.internal.os.RuntimeInit.logUncaught(thread.getName(), android.app.ActivityThread.currentProcessName(), android.os.Process.myPid(), th);
        }
    }

    private static class KillApplicationHandler implements java.lang.Thread.UncaughtExceptionHandler {
        private final com.android.internal.os.RuntimeInit.LoggingHandler mLoggingHandler;

        public KillApplicationHandler(com.android.internal.os.RuntimeInit.LoggingHandler loggingHandler) {
            this.mLoggingHandler = (com.android.internal.os.RuntimeInit.LoggingHandler) java.util.Objects.requireNonNull(loggingHandler);
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(java.lang.Thread thread, java.lang.Throwable th) {
            try {
                ensureLogging(thread, th);
            } catch (java.lang.Throwable th2) {
                try {
                    if (!(th2 instanceof android.os.DeadObjectException)) {
                        try {
                            com.android.internal.os.RuntimeInit.Clog_e(com.android.internal.os.RuntimeInit.TAG, "Error reporting crash", th2);
                        } catch (java.lang.Throwable th3) {
                        }
                    }
                } finally {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    java.lang.System.exit(10);
                }
            }
            if (com.android.internal.os.RuntimeInit.mCrashing) {
                return;
            }
            com.android.internal.os.RuntimeInit.mCrashing = true;
            if (android.app.ActivityThread.currentActivityThread() != null) {
                android.app.ActivityThread.currentActivityThread().stopProfiling();
            }
            android.app.ActivityManager.getService().handleApplicationCrash(com.android.internal.os.RuntimeInit.mApplicationObject, new android.app.ApplicationErrorReport.ParcelableCrashInfo(th));
        }

        private void ensureLogging(java.lang.Thread thread, java.lang.Throwable th) {
            if (!this.mLoggingHandler.mTriggered) {
                try {
                    this.mLoggingHandler.uncaughtException(thread, th);
                } catch (java.lang.Throwable th2) {
                }
            }
        }
    }

    public static void preForkInit() {
        enableDdms();
        libcore.content.type.MimeMap.setDefaultSupplier(new java.util.function.Supplier() { // from class: com.android.internal.os.RuntimeInit$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return android.content.type.DefaultMimeMapFactory.create();
            }
        });
    }

    protected static final void commonInit() {
        com.android.internal.os.RuntimeInit.LoggingHandler loggingHandler = new com.android.internal.os.RuntimeInit.LoggingHandler();
        dalvik.system.RuntimeHooks.setUncaughtExceptionPreHandler(loggingHandler);
        java.lang.Thread.setDefaultUncaughtExceptionHandler(new com.android.internal.os.RuntimeInit.KillApplicationHandler(loggingHandler));
        dalvik.system.RuntimeHooks.setTimeZoneIdSupplier(new java.util.function.Supplier() { // from class: com.android.internal.os.RuntimeInit$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String str;
                str = android.os.SystemProperties.get("persist.sys.timezone");
                return str;
            }
        });
        java.util.logging.LogManager.getLogManager().reset();
        new com.android.internal.logging.AndroidConfig();
        java.lang.System.setProperty("http.agent", getDefaultUserAgent());
        android.net.TrafficStats.attachSocketTagger();
        initialized = true;
    }

    private static java.lang.String getDefaultUserAgent() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
        sb.append("Dalvik/");
        sb.append(java.lang.System.getProperty("java.vm.version"));
        sb.append(" (Linux; U; Android ");
        java.lang.String str = android.os.Build.VERSION.RELEASE_OR_CODENAME;
        if (str.length() <= 0) {
            str = "1.0";
        }
        sb.append(str);
        if ("REL".equals(android.os.Build.VERSION.CODENAME)) {
            java.lang.String str2 = android.os.Build.MODEL;
            if (str2.length() > 0) {
                sb.append("; ");
                sb.append(str2);
            }
        }
        java.lang.String str3 = android.os.Build.ID;
        if (str3.length() > 0) {
            sb.append(" Build/");
            sb.append(str3);
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    protected static java.lang.Runnable findStaticMain(java.lang.String str, java.lang.String[] strArr, java.lang.ClassLoader classLoader) {
        try {
            try {
                java.lang.reflect.Method method = java.lang.Class.forName(str, true, classLoader).getMethod("main", java.lang.String[].class);
                int modifiers = method.getModifiers();
                if (!java.lang.reflect.Modifier.isStatic(modifiers) || !java.lang.reflect.Modifier.isPublic(modifiers)) {
                    throw new java.lang.RuntimeException("Main method is not public and static on " + str);
                }
                return new com.android.internal.os.RuntimeInit.MethodAndArgsCaller(method, strArr);
            } catch (java.lang.NoSuchMethodException e) {
                throw new java.lang.RuntimeException("Missing static main on " + str, e);
            } catch (java.lang.SecurityException e2) {
                throw new java.lang.RuntimeException("Problem getting static main on " + str, e2);
            }
        } catch (java.lang.ClassNotFoundException e3) {
            throw new java.lang.RuntimeException("Missing class when invoking static main " + str, e3);
        }
    }

    public static final void main(java.lang.String[] strArr) {
        preForkInit();
        if (strArr.length == 2 && strArr[1].equals("application")) {
            redirectLogStreams();
        }
        commonInit();
        nativeFinishInit();
    }

    protected static java.lang.Runnable applicationInit(int i, long[] jArr, java.lang.String[] strArr, java.lang.ClassLoader classLoader) {
        nativeSetExitWithoutCleanup(true);
        dalvik.system.VMRuntime.getRuntime().setTargetSdkVersion(i);
        dalvik.system.VMRuntime.getRuntime().setDisabledCompatChanges(jArr);
        com.android.internal.os.RuntimeInit.Arguments arguments = new com.android.internal.os.RuntimeInit.Arguments(strArr);
        android.os.Trace.traceEnd(64L);
        return findStaticMain(arguments.startClass, arguments.startArgs, classLoader);
    }

    public static void redirectLogStreams() {
        java.lang.System.out.close();
        java.lang.System.setOut(new com.android.internal.os.AndroidPrintStream(4, "System.out"));
        java.lang.System.err.close();
        java.lang.System.setErr(new com.android.internal.os.AndroidPrintStream(5, "System.err"));
    }

    public static void redirectLogStreams$ravenwood() {
        if (sOut$ravenwood == null) {
            sOut$ravenwood = java.lang.System.out;
            java.lang.System.setOut(new com.android.internal.os.AndroidPrintStream(4, "System.out"));
        }
        if (sErr$ravenwood == null) {
            sErr$ravenwood = java.lang.System.err;
            java.lang.System.setErr(new com.android.internal.os.AndroidPrintStream(5, "System.err"));
        }
    }

    public static void wtf(java.lang.String str, java.lang.Throwable th, boolean z) {
        boolean z2;
        try {
            android.app.IActivityManager service = android.app.ActivityManager.getService();
            if (service != null) {
                z2 = service.handleApplicationWtf(mApplicationObject, str, z, new android.app.ApplicationErrorReport.ParcelableCrashInfo(th), android.os.Process.myPid());
            } else {
                com.android.internal.os.RuntimeInit.ApplicationWtfHandler applicationWtfHandler = sDefaultApplicationWtfHandler;
                if (applicationWtfHandler != null) {
                    z2 = applicationWtfHandler.handleApplicationWtf(mApplicationObject, str, z, new android.app.ApplicationErrorReport.ParcelableCrashInfo(th), android.os.Process.myPid());
                } else {
                    android.util.Slog.e(TAG, "Original WTF:", th);
                    z2 = false;
                }
            }
            if (z2) {
                android.os.Process.killProcess(android.os.Process.myPid());
                java.lang.System.exit(10);
            }
        } catch (java.lang.Throwable th2) {
            if (!(th2 instanceof android.os.DeadObjectException)) {
                android.util.Slog.e(TAG, "Error reporting WTF", th2);
                android.util.Slog.e(TAG, "Original WTF:", th);
            }
        }
    }

    public static void wtf$ravenwood(java.lang.String str, java.lang.Throwable th, boolean z) {
    }

    public static void setDefaultApplicationWtfHandler(com.android.internal.os.RuntimeInit.ApplicationWtfHandler applicationWtfHandler) {
        sDefaultApplicationWtfHandler = applicationWtfHandler;
    }

    public static final void setApplicationObject(android.os.IBinder iBinder) {
        mApplicationObject = iBinder;
    }

    public static final android.os.IBinder getApplicationObject() {
        return mApplicationObject;
    }

    private static void enableDdms() {
        android.ddm.DdmRegister.registerHandlers();
    }

    static class Arguments {
        java.lang.String[] startArgs;
        java.lang.String startClass;

        Arguments(java.lang.String[] strArr) throws java.lang.IllegalArgumentException {
            parseArgs(strArr);
        }

        private void parseArgs(java.lang.String[] strArr) throws java.lang.IllegalArgumentException {
            int i = 0;
            while (true) {
                if (i >= strArr.length) {
                    break;
                }
                java.lang.String str = strArr[i];
                if (str.equals("--")) {
                    i++;
                    break;
                } else if (!str.startsWith("--")) {
                    break;
                } else {
                    i++;
                }
            }
            if (i == strArr.length) {
                throw new java.lang.IllegalArgumentException("Missing classname argument to RuntimeInit!");
            }
            int i2 = i + 1;
            this.startClass = strArr[i];
            this.startArgs = new java.lang.String[strArr.length - i2];
            java.lang.System.arraycopy(strArr, i2, this.startArgs, 0, this.startArgs.length);
        }
    }

    static class MethodAndArgsCaller implements java.lang.Runnable {
        private final java.lang.String[] mArgs;
        private final java.lang.reflect.Method mMethod;

        public MethodAndArgsCaller(java.lang.reflect.Method method, java.lang.String[] strArr) {
            this.mMethod = method;
            this.mArgs = strArr;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.mMethod.invoke(null, this.mArgs);
            } catch (java.lang.IllegalAccessException e) {
                throw new java.lang.RuntimeException(e);
            } catch (java.lang.reflect.InvocationTargetException e2) {
                java.lang.Throwable cause = e2.getCause();
                if (cause instanceof java.lang.RuntimeException) {
                    throw ((java.lang.RuntimeException) cause);
                }
                if (cause instanceof java.lang.Error) {
                    throw ((java.lang.Error) cause);
                }
                throw new java.lang.RuntimeException(e2);
            }
        }
    }
}
