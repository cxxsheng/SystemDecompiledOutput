package com.android.server.wm;

/* loaded from: classes3.dex */
class WindowTracing {
    private static final int BUFFER_CAPACITY_ALL = 20971520;
    private static final int BUFFER_CAPACITY_CRITICAL = 5242880;
    private static final int BUFFER_CAPACITY_TRIM = 10485760;
    private static final long MAGIC_NUMBER_VALUE = 4990904633914181975L;
    private static final java.lang.String TAG = "WindowTracing";
    private static final java.lang.String TRACE_FILENAME = "/data/misc/wmtrace/wm_trace.winscope";
    static final java.lang.String WINSCOPE_EXT = ".winscope";
    private final com.android.internal.util.TraceBuffer mBuffer;
    private final android.view.Choreographer mChoreographer;
    private boolean mEnabled;
    private final java.lang.Object mEnabledLock;
    private volatile boolean mEnabledLockFree;
    private final android.view.Choreographer.FrameCallback mFrameCallback;
    private final com.android.server.wm.WindowManagerGlobalLock mGlobalLock;
    private int mLogLevel;
    private boolean mLogOnFrame;
    private final com.android.internal.protolog.common.IProtoLog mProtoLog;
    private boolean mScheduled;
    private final com.android.server.wm.WindowManagerService mService;
    private final java.io.File mTraceFile;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(long j) {
        log("onFrame");
    }

    static com.android.server.wm.WindowTracing createDefaultAndStartLooper(com.android.server.wm.WindowManagerService windowManagerService, android.view.Choreographer choreographer) {
        return new com.android.server.wm.WindowTracing(new java.io.File(TRACE_FILENAME), windowManagerService, choreographer, BUFFER_CAPACITY_TRIM);
    }

    private WindowTracing(java.io.File file, com.android.server.wm.WindowManagerService windowManagerService, android.view.Choreographer choreographer, int i) {
        this(file, windowManagerService, choreographer, windowManagerService.mGlobalLock, i);
    }

    WindowTracing(java.io.File file, com.android.server.wm.WindowManagerService windowManagerService, android.view.Choreographer choreographer, com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock, int i) {
        this.mEnabledLock = new java.lang.Object();
        this.mFrameCallback = new android.view.Choreographer.FrameCallback() { // from class: com.android.server.wm.WindowTracing$$ExternalSyntheticLambda0
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                com.android.server.wm.WindowTracing.this.lambda$new$0(j);
            }
        };
        this.mLogLevel = 1;
        this.mLogOnFrame = false;
        this.mChoreographer = choreographer;
        this.mService = windowManagerService;
        this.mGlobalLock = windowManagerGlobalLock;
        this.mTraceFile = file;
        this.mBuffer = new com.android.internal.util.TraceBuffer(i);
        setLogLevel(1, null);
        this.mProtoLog = com.android.internal.protolog.ProtoLogImpl_1545807451.getSingleInstance();
    }

    void startTrace(@android.annotation.Nullable java.io.PrintWriter printWriter) {
        if (android.os.Build.IS_USER) {
            logAndPrintln(printWriter, "Error: Tracing is not supported on user builds.");
            return;
        }
        synchronized (this.mEnabledLock) {
            logAndPrintln(printWriter, "Start tracing to " + this.mTraceFile + ".");
            this.mBuffer.resetBuffer();
            this.mEnabledLockFree = true;
            this.mEnabled = true;
        }
        log("trace.enable");
    }

    void stopTrace(@android.annotation.Nullable java.io.PrintWriter printWriter) {
        if (android.os.Build.IS_USER) {
            logAndPrintln(printWriter, "Error: Tracing is not supported on user builds.");
            return;
        }
        synchronized (this.mEnabledLock) {
            try {
                logAndPrintln(printWriter, "Stop tracing to " + this.mTraceFile + ". Waiting for traces to flush.");
                this.mEnabledLockFree = false;
                this.mEnabled = false;
                if (this.mEnabled) {
                    logAndPrintln(printWriter, "ERROR: tracing was re-enabled while waiting for flush.");
                    throw new java.lang.IllegalStateException("tracing enabled while waiting for flush.");
                }
                writeTraceToFileLocked();
                logAndPrintln(printWriter, "Trace written to " + this.mTraceFile + ".");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void saveForBugreport(@android.annotation.Nullable java.io.PrintWriter printWriter) {
        if (android.os.Build.IS_USER) {
            logAndPrintln(printWriter, "Error: Tracing is not supported on user builds.");
            return;
        }
        synchronized (this.mEnabledLock) {
            try {
                if (this.mEnabled) {
                    this.mEnabledLockFree = false;
                    this.mEnabled = false;
                    logAndPrintln(printWriter, "Stop tracing to " + this.mTraceFile + ". Waiting for traces to flush.");
                    writeTraceToFileLocked();
                    logAndPrintln(printWriter, "Trace written to " + this.mTraceFile + ".");
                    if (!android.tracing.Flags.perfettoProtologTracing()) {
                        this.mProtoLog.stopProtoLog(printWriter, true);
                    }
                    logAndPrintln(printWriter, "Start tracing to " + this.mTraceFile + ".");
                    this.mBuffer.resetBuffer();
                    this.mEnabledLockFree = true;
                    this.mEnabled = true;
                    if (!android.tracing.Flags.perfettoProtologTracing()) {
                        this.mProtoLog.startProtoLog(printWriter);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void setLogLevel(int i, java.io.PrintWriter printWriter) {
        logAndPrintln(printWriter, "Setting window tracing log level to " + i);
        this.mLogLevel = i;
        switch (i) {
            case 0:
                setBufferCapacity(BUFFER_CAPACITY_ALL, printWriter);
                break;
            case 1:
                setBufferCapacity(BUFFER_CAPACITY_TRIM, printWriter);
                break;
            case 2:
                setBufferCapacity(BUFFER_CAPACITY_CRITICAL, printWriter);
                break;
        }
    }

    private void setLogFrequency(boolean z, java.io.PrintWriter printWriter) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Setting window tracing log frequency to ");
        sb.append(z ? "frame" : "transaction");
        logAndPrintln(printWriter, sb.toString());
        this.mLogOnFrame = z;
    }

    private void setBufferCapacity(int i, java.io.PrintWriter printWriter) {
        logAndPrintln(printWriter, "Setting window tracing buffer capacity to " + i + "bytes");
        this.mBuffer.setCapacity(i);
    }

    boolean isEnabled() {
        return this.mEnabledLockFree;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    int onShellCommand(android.os.ShellCommand shellCommand) {
        char c;
        java.io.PrintWriter outPrintWriter = shellCommand.getOutPrintWriter();
        java.lang.String nextArgRequired = shellCommand.getNextArgRequired();
        char c2 = 65535;
        switch (nextArgRequired.hashCode()) {
            case -892481550:
                if (nextArgRequired.equals("status")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -390772652:
                if (nextArgRequired.equals("save-for-bugreport")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3530753:
                if (nextArgRequired.equals("size")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 3540994:
                if (nextArgRequired.equals("stop")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 97692013:
                if (nextArgRequired.equals("frame")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 102865796:
                if (nextArgRequired.equals("level")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 109757538:
                if (nextArgRequired.equals("start")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2141246174:
                if (nextArgRequired.equals("transaction")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                startTrace(outPrintWriter);
                return 0;
            case 1:
                stopTrace(outPrintWriter);
                return 0;
            case 2:
                saveForBugreport(outPrintWriter);
                return 0;
            case 3:
                logAndPrintln(outPrintWriter, getStatus());
                return 0;
            case 4:
                setLogFrequency(true, outPrintWriter);
                this.mBuffer.resetBuffer();
                return 0;
            case 5:
                setLogFrequency(false, outPrintWriter);
                this.mBuffer.resetBuffer();
                return 0;
            case 6:
                java.lang.String lowerCase = shellCommand.getNextArgRequired().toLowerCase();
                switch (lowerCase.hashCode()) {
                    case 96673:
                        if (lowerCase.equals("all")) {
                            c2 = 0;
                            break;
                        }
                        break;
                    case 3568674:
                        if (lowerCase.equals("trim")) {
                            c2 = 1;
                            break;
                        }
                        break;
                    case 1952151455:
                        if (lowerCase.equals("critical")) {
                            c2 = 2;
                            break;
                        }
                        break;
                }
                switch (c2) {
                    case 0:
                        setLogLevel(0, outPrintWriter);
                        break;
                    case 1:
                        setLogLevel(1, outPrintWriter);
                        break;
                    case 2:
                        setLogLevel(2, outPrintWriter);
                        break;
                    default:
                        setLogLevel(1, outPrintWriter);
                        break;
                }
                this.mBuffer.resetBuffer();
                return 0;
            case 7:
                setBufferCapacity(java.lang.Integer.parseInt(shellCommand.getNextArgRequired()) * 1024, outPrintWriter);
                this.mBuffer.resetBuffer();
                return 0;
            default:
                outPrintWriter.println("Unknown command: " + nextArgRequired);
                outPrintWriter.println("Window manager trace options:");
                outPrintWriter.println("  start: Start logging");
                outPrintWriter.println("  stop: Stop logging");
                outPrintWriter.println("  save-for-bugreport: Save logging data to file if it's running.");
                outPrintWriter.println("  frame: Log trace once per frame");
                outPrintWriter.println("  transaction: Log each transaction");
                outPrintWriter.println("  size: Set the maximum log size (in KB)");
                outPrintWriter.println("  status: Print trace status");
                outPrintWriter.println("  level [lvl]: Set the log level between");
                outPrintWriter.println("    lvl may be one of:");
                outPrintWriter.println("      critical: Only visible windows with reduced information");
                outPrintWriter.println("      trim: All windows with reduced");
                outPrintWriter.println("      all: All window and information");
                return -1;
        }
    }

    java.lang.String getStatus() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Status: ");
        sb.append(isEnabled() ? "Enabled" : "Disabled");
        sb.append("\nLog level: ");
        sb.append(this.mLogLevel);
        sb.append("\n");
        sb.append(this.mBuffer.getStatus());
        return sb.toString();
    }

    void logState(java.lang.String str) {
        if (!isEnabled()) {
            return;
        }
        if (this.mLogOnFrame) {
            schedule();
        } else {
            log(str);
        }
    }

    private void schedule() {
        if (this.mScheduled) {
            return;
        }
        this.mScheduled = true;
        this.mChoreographer.postFrameCallback(this.mFrameCallback);
    }

    private void log(java.lang.String str) {
        android.os.Trace.traceBegin(32L, "traceStateLocked");
        try {
            try {
                android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
                long start = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1125281431553L, android.os.SystemClock.elapsedRealtimeNanos());
                protoOutputStream.write(1138166333442L, str);
                long start2 = protoOutputStream.start(1146756268035L);
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        android.os.Trace.traceBegin(32L, "dumpDebugLocked");
                        try {
                            this.mService.dumpDebugLocked(protoOutputStream, this.mLogLevel);
                            android.os.Trace.traceEnd(32L);
                        } finally {
                        }
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                protoOutputStream.end(start2);
                protoOutputStream.end(start);
                this.mBuffer.add(protoOutputStream);
                this.mScheduled = false;
            } catch (java.lang.Exception e) {
                android.util.Log.wtf(TAG, "Exception while tracing state", e);
            }
        } finally {
        }
    }

    private void logAndPrintln(@android.annotation.Nullable java.io.PrintWriter printWriter, java.lang.String str) {
        android.util.Log.i(TAG, str);
        if (printWriter != null) {
            printWriter.println(str);
            printWriter.flush();
        }
    }

    private void writeTraceToFileLocked() {
        try {
            try {
                android.os.Trace.traceBegin(32L, "writeTraceToFileLocked");
                android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
                protoOutputStream.write(1125281431553L, MAGIC_NUMBER_VALUE);
                protoOutputStream.write(1125281431555L, java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(java.lang.System.currentTimeMillis()) - android.os.SystemClock.elapsedRealtimeNanos());
                this.mBuffer.writeTraceToFile(this.mTraceFile, protoOutputStream);
            } catch (java.io.IOException e) {
                android.util.Log.e(TAG, "Unable to write buffer to file", e);
            }
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }
}
