package com.android.internal.protolog;

/* loaded from: classes5.dex */
public class LegacyProtoLogImpl implements com.android.internal.protolog.common.IProtoLog {
    private static final int BUFFER_CAPACITY = 1048576;
    private static final int DEFAULT_PER_CHUNK_SIZE = 0;
    private static final long MAGIC_NUMBER_VALUE = 5138409603453637200L;
    private static final int PER_CHUNK_SIZE = 1024;
    static final java.lang.String PROTOLOG_VERSION = "2.0.0";
    private static final java.lang.String TAG = "ProtoLog";
    private final com.android.internal.util.TraceBuffer mBuffer;
    private final java.lang.String mLegacyViewerConfigFilename;
    private final java.io.File mLogFile;
    private final java.util.TreeMap<java.lang.String, com.android.internal.protolog.common.IProtoLogGroup> mLogGroups;
    private final int mPerChunkSize;
    private boolean mProtoLogEnabled;
    private final java.lang.Object mProtoLogEnabledLock;
    private boolean mProtoLogEnabledLockFree;
    private final com.android.internal.protolog.LegacyProtoLogViewerConfigReader mViewerConfig;

    public LegacyProtoLogImpl(java.lang.String str, java.lang.String str2) {
        this(new java.io.File(str), str2, 1048576, new com.android.internal.protolog.LegacyProtoLogViewerConfigReader(), 1024);
    }

    public LegacyProtoLogImpl(java.io.File file, java.lang.String str, int i, com.android.internal.protolog.LegacyProtoLogViewerConfigReader legacyProtoLogViewerConfigReader, int i2) {
        this.mLogGroups = new java.util.TreeMap<>();
        this.mProtoLogEnabledLock = new java.lang.Object();
        this.mLogFile = file;
        this.mBuffer = new com.android.internal.util.TraceBuffer(i);
        this.mLegacyViewerConfigFilename = str;
        this.mViewerConfig = legacyProtoLogViewerConfigReader;
        this.mPerChunkSize = i2;
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public void log(com.android.internal.protolog.common.LogLevel logLevel, com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, java.lang.String str, java.lang.Object[] objArr) {
        if (iProtoLogGroup.isLogToProto()) {
            logToProto(j, i, objArr);
        }
        if (iProtoLogGroup.isLogToLogcat()) {
            logToLogcat(iProtoLogGroup.getTag(), logLevel, j, str, objArr);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void logToLogcat(java.lang.String str, com.android.internal.protolog.common.LogLevel logLevel, long j, java.lang.String str2, java.lang.Object[] objArr) {
        if (str2 == null) {
            str2 = this.mViewerConfig.getViewerString(j);
        }
        if (str2 != null) {
            if (objArr != null) {
                try {
                    str2 = android.text.TextUtils.formatSimple(str2, objArr);
                } catch (java.lang.Exception e) {
                    android.util.Slog.w(TAG, "Invalid ProtoLog format string.", e);
                }
            }
            if (str2 == null) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder("UNKNOWN MESSAGE (" + j + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                for (java.lang.Object obj : objArr) {
                    sb.append(" ").append(obj);
                }
                str2 = sb.toString();
            }
            passToLogcat(str, logLevel, str2);
        }
        str2 = null;
        if (str2 == null) {
        }
        passToLogcat(str, logLevel, str2);
    }

    public void passToLogcat(java.lang.String str, com.android.internal.protolog.common.LogLevel logLevel, java.lang.String str2) {
        switch (logLevel) {
            case DEBUG:
                android.util.Slog.d(str, str2);
                break;
            case VERBOSE:
                android.util.Slog.v(str, str2);
                break;
            case INFO:
                android.util.Slog.i(str, str2);
                break;
            case WARN:
                android.util.Slog.w(str, str2);
                break;
            case ERROR:
                android.util.Slog.e(str, str2);
                break;
            case WTF:
                android.util.Slog.wtf(str, str2);
                break;
        }
    }

    private void logToProto(long j, int i, java.lang.Object[] objArr) {
        if (!isProtoEnabled()) {
            return;
        }
        try {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(this.mPerChunkSize);
            long start = protoOutputStream.start(2246267895812L);
            protoOutputStream.write(com.android.internal.protolog.ProtoLogMessage.MESSAGE_HASH, j);
            protoOutputStream.write(1125281431554L, android.os.SystemClock.elapsedRealtimeNanos());
            if (objArr != null) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                java.util.ArrayList arrayList3 = new java.util.ArrayList();
                int length = objArr.length;
                int i2 = 0;
                int i3 = 0;
                while (i2 < length) {
                    java.lang.Object obj = objArr[i2];
                    int i4 = i2;
                    switch (com.android.internal.protolog.common.LogDataType.bitmaskToLogDataType(i, i3)) {
                        case 0:
                            protoOutputStream.write(2237677961219L, obj.toString());
                            break;
                        case 1:
                            arrayList.add(java.lang.Long.valueOf(((java.lang.Number) obj).longValue()));
                            break;
                        case 2:
                            arrayList2.add(java.lang.Double.valueOf(((java.lang.Number) obj).doubleValue()));
                            break;
                        case 3:
                            try {
                                arrayList3.add(java.lang.Boolean.valueOf(((java.lang.Boolean) obj).booleanValue()));
                                break;
                            } catch (java.lang.ClassCastException e) {
                                protoOutputStream.write(2237677961219L, "(INVALID PARAMS_MASK) " + obj.toString());
                                android.util.Slog.e(TAG, "Invalid ProtoLog paramsMask", e);
                                break;
                            }
                    }
                    i3++;
                    i2 = i4 + 1;
                }
                if (arrayList.size() > 0) {
                    protoOutputStream.writePackedSInt64(com.android.internal.protolog.ProtoLogMessage.SINT64_PARAMS, arrayList.stream().mapToLong(new java.util.function.ToLongFunction() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda4
                        @Override // java.util.function.ToLongFunction
                        public final long applyAsLong(java.lang.Object obj2) {
                            long longValue;
                            longValue = ((java.lang.Long) obj2).longValue();
                            return longValue;
                        }
                    }).toArray());
                }
                if (arrayList2.size() > 0) {
                    protoOutputStream.writePackedDouble(com.android.internal.protolog.ProtoLogMessage.DOUBLE_PARAMS, arrayList2.stream().mapToDouble(new java.util.function.ToDoubleFunction() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda5
                        @Override // java.util.function.ToDoubleFunction
                        public final double applyAsDouble(java.lang.Object obj2) {
                            double doubleValue;
                            doubleValue = ((java.lang.Double) obj2).doubleValue();
                            return doubleValue;
                        }
                    }).toArray());
                }
                if (arrayList3.size() > 0) {
                    boolean[] zArr = new boolean[arrayList3.size()];
                    for (int i5 = 0; i5 < arrayList3.size(); i5++) {
                        zArr[i5] = ((java.lang.Boolean) arrayList3.get(i5)).booleanValue();
                    }
                    protoOutputStream.writePackedBool(com.android.internal.protolog.ProtoLogMessage.BOOLEAN_PARAMS, zArr);
                }
            }
            protoOutputStream.end(start);
            this.mBuffer.add(protoOutputStream);
        } catch (java.lang.Exception e2) {
            android.util.Slog.e(TAG, "Exception while logging to proto", e2);
        }
    }

    public void startProtoLog(java.io.PrintWriter printWriter) {
        if (isProtoEnabled()) {
            return;
        }
        synchronized (this.mProtoLogEnabledLock) {
            logAndPrintln(printWriter, "Start logging to " + this.mLogFile + android.media.MediaMetrics.SEPARATOR);
            this.mBuffer.resetBuffer();
            this.mProtoLogEnabled = true;
            this.mProtoLogEnabledLockFree = true;
        }
    }

    public void stopProtoLog(java.io.PrintWriter printWriter, boolean z) {
        if (!isProtoEnabled()) {
            return;
        }
        synchronized (this.mProtoLogEnabledLock) {
            logAndPrintln(printWriter, "Stop logging to " + this.mLogFile + ". Waiting for log to flush.");
            this.mProtoLogEnabledLockFree = false;
            this.mProtoLogEnabled = false;
            if (z) {
                writeProtoLogToFileLocked();
                logAndPrintln(printWriter, "Log written to " + this.mLogFile + android.media.MediaMetrics.SEPARATOR);
                this.mBuffer.resetBuffer();
            }
            if (this.mProtoLogEnabled) {
                logAndPrintln(printWriter, "ERROR: logging was re-enabled while waiting for flush.");
                throw new java.lang.IllegalStateException("logging enabled while waiting for flush.");
            }
        }
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public boolean isProtoEnabled() {
        return this.mProtoLogEnabledLockFree;
    }

    private int setLogging(boolean z, boolean z2, com.android.internal.protolog.common.ILogger iLogger, java.lang.String... strArr) {
        for (java.lang.String str : strArr) {
            com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup = this.mLogGroups.get(str);
            if (iProtoLogGroup != null) {
                if (z) {
                    iProtoLogGroup.setLogToLogcat(z2);
                } else {
                    iProtoLogGroup.setLogToProto(z2);
                }
            } else {
                iLogger.log("No IProtoLogGroup named " + str);
                return -1;
            }
        }
        return 0;
    }

    private int unknownCommand(java.io.PrintWriter printWriter) {
        printWriter.println("Unknown command");
        printWriter.println("Window manager logging options:");
        printWriter.println("  start: Start proto logging");
        printWriter.println("  stop: Stop proto logging");
        printWriter.println("  enable [group...]: Enable proto logging for given groups");
        printWriter.println("  disable [group...]: Disable proto logging for given groups");
        printWriter.println("  enable-text [group...]: Enable logcat logging for given groups");
        printWriter.println("  disable-text [group...]: Disable logcat logging for given groups");
        return -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onShellCommand(android.os.ShellCommand shellCommand) {
        char c;
        final java.io.PrintWriter outPrintWriter = shellCommand.getOutPrintWriter();
        java.lang.String nextArg = shellCommand.getNextArg();
        if (nextArg == null) {
            return unknownCommand(outPrintWriter);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (true) {
            java.lang.String nextArg2 = shellCommand.getNextArg();
            if (nextArg2 == null) {
                break;
            }
            arrayList.add(nextArg2);
        }
        com.android.internal.protolog.common.ILogger iLogger = new com.android.internal.protolog.common.ILogger() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda3
            @Override // com.android.internal.protolog.common.ILogger
            public final void log(java.lang.String str) {
                com.android.internal.protolog.LegacyProtoLogImpl.logAndPrintln(outPrintWriter, str);
            }
        };
        java.lang.String[] strArr = (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
        switch (nextArg.hashCode()) {
            case -1475003593:
                if (nextArg.equals("enable-text")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1298848381:
                if (nextArg.equals("enable")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1032071950:
                if (nextArg.equals("disable-text")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -892481550:
                if (nextArg.equals("status")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3540994:
                if (nextArg.equals("stop")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 109757538:
                if (nextArg.equals("start")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1671308008:
                if (nextArg.equals("disable")) {
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
                startProtoLog(outPrintWriter);
                break;
            case 1:
                stopProtoLog(outPrintWriter, true);
                break;
            case 2:
                logAndPrintln(outPrintWriter, getStatus());
                break;
            case 4:
                this.mViewerConfig.loadViewerConfig(iLogger, this.mLegacyViewerConfigFilename);
                break;
        }
        return unknownCommand(outPrintWriter);
    }

    public java.lang.String getStatus() {
        return "ProtoLog status: " + (isProtoEnabled() ? "Enabled" : "Disabled") + "\nEnabled log groups: \n  Proto: " + ((java.lang.String) this.mLogGroups.values().stream().filter(new java.util.function.Predicate() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return com.android.internal.protolog.LegacyProtoLogImpl.lambda$getStatus$3((com.android.internal.protolog.common.IProtoLogGroup) obj);
            }
        }).map(new java.util.function.Function() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((com.android.internal.protolog.common.IProtoLogGroup) obj).name();
            }
        }).collect(java.util.stream.Collectors.joining(" "))) + "\n  Logcat: " + ((java.lang.String) this.mLogGroups.values().stream().filter(new java.util.function.Predicate() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return com.android.internal.protolog.LegacyProtoLogImpl.lambda$getStatus$4((com.android.internal.protolog.common.IProtoLogGroup) obj);
            }
        }).map(new java.util.function.Function() { // from class: com.android.internal.protolog.LegacyProtoLogImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((com.android.internal.protolog.common.IProtoLogGroup) obj).name();
            }
        }).collect(java.util.stream.Collectors.joining(" "))) + "\nLogging definitions loaded: " + this.mViewerConfig.knownViewerStringsNumber();
    }

    static /* synthetic */ boolean lambda$getStatus$3(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup) {
        return iProtoLogGroup.isEnabled() && iProtoLogGroup.isLogToProto();
    }

    static /* synthetic */ boolean lambda$getStatus$4(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup) {
        return iProtoLogGroup.isEnabled() && iProtoLogGroup.isLogToLogcat();
    }

    private void writeProtoLogToFileLocked() {
        try {
            long currentTimeMillis = java.lang.System.currentTimeMillis() - (android.os.SystemClock.elapsedRealtimeNanos() / 1000000);
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(this.mPerChunkSize);
            protoOutputStream.write(1125281431553L, MAGIC_NUMBER_VALUE);
            protoOutputStream.write(1138166333442L, PROTOLOG_VERSION);
            protoOutputStream.write(1125281431555L, currentTimeMillis);
            this.mBuffer.writeTraceToFile(this.mLogFile, protoOutputStream);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Unable to write buffer to file", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void logAndPrintln(java.io.PrintWriter printWriter, java.lang.String str) {
        android.util.Slog.i(TAG, str);
        if (printWriter != null) {
            printWriter.println(str);
            printWriter.flush();
        }
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int startLoggingToLogcat(java.lang.String[] strArr, com.android.internal.protolog.common.ILogger iLogger) {
        this.mViewerConfig.loadViewerConfig(iLogger, this.mLegacyViewerConfigFilename);
        return setLogging(true, true, iLogger, strArr);
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int stopLoggingToLogcat(java.lang.String[] strArr, com.android.internal.protolog.common.ILogger iLogger) {
        return setLogging(true, false, iLogger, strArr);
    }
}
