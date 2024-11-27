package com.android.internal.protolog;

/* loaded from: classes5.dex */
public class PerfettoProtoLogImpl implements com.android.internal.protolog.common.IProtoLog {
    private static final java.lang.String LOG_TAG = "ProtoLog";
    private static final int STACK_SIZE_TO_PROTO_LOG_ENTRY_CALL = 12;
    private final com.android.internal.protolog.ProtoLogDataSource mDataSource;
    private final java.util.TreeMap<java.lang.String, com.android.internal.protolog.common.IProtoLogGroup> mLogGroups;
    private final java.util.concurrent.atomic.AtomicInteger mTracingInstances;
    private final com.android.internal.protolog.ViewerConfigInputStreamProvider mViewerConfigInputStreamProvider;
    private final com.android.internal.protolog.ProtoLogViewerConfigReader mViewerConfigReader;

    public PerfettoProtoLogImpl(final java.lang.String str) {
        this(new com.android.internal.protolog.ViewerConfigInputStreamProvider() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda5
            @Override // com.android.internal.protolog.ViewerConfigInputStreamProvider
            public final android.util.proto.ProtoInputStream getInputStream() {
                return com.android.internal.protolog.PerfettoProtoLogImpl.lambda$new$0(str);
            }
        });
    }

    static /* synthetic */ android.util.proto.ProtoInputStream lambda$new$0(java.lang.String str) {
        try {
            return new android.util.proto.ProtoInputStream(new java.io.FileInputStream(str));
        } catch (java.io.FileNotFoundException e) {
            android.util.Slog.w(LOG_TAG, "Failed to load viewer config file " + str, e);
            return null;
        }
    }

    public PerfettoProtoLogImpl(com.android.internal.protolog.ViewerConfigInputStreamProvider viewerConfigInputStreamProvider) {
        this(viewerConfigInputStreamProvider, new com.android.internal.protolog.ProtoLogViewerConfigReader(viewerConfigInputStreamProvider));
    }

    public PerfettoProtoLogImpl(com.android.internal.protolog.ViewerConfigInputStreamProvider viewerConfigInputStreamProvider, com.android.internal.protolog.ProtoLogViewerConfigReader protoLogViewerConfigReader) {
        this.mLogGroups = new java.util.TreeMap<>();
        this.mTracingInstances = new java.util.concurrent.atomic.AtomicInteger();
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = this.mTracingInstances;
        java.util.Objects.requireNonNull(atomicInteger);
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                atomicInteger.incrementAndGet();
            }
        };
        java.lang.Runnable runnable2 = new java.lang.Runnable() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.protolog.PerfettoProtoLogImpl.this.dumpTransitionTraceConfig();
            }
        };
        final java.util.concurrent.atomic.AtomicInteger atomicInteger2 = this.mTracingInstances;
        java.util.Objects.requireNonNull(atomicInteger2);
        this.mDataSource = new com.android.internal.protolog.ProtoLogDataSource(runnable, runnable2, new java.lang.Runnable() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                atomicInteger2.decrementAndGet();
            }
        });
        android.tracing.perfetto.Producer.init(android.tracing.perfetto.InitArguments.DEFAULTS);
        this.mDataSource.register(android.tracing.perfetto.DataSourceParams.DEFAULTS);
        this.mViewerConfigInputStreamProvider = viewerConfigInputStreamProvider;
        this.mViewerConfigReader = protoLogViewerConfigReader;
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public void log(com.android.internal.protolog.common.LogLevel logLevel, com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, long j, int i, java.lang.String str, java.lang.Object[] objArr) {
        android.os.Trace.traceBegin(32L, "log");
        try {
            logToProto(logLevel, iProtoLogGroup.name(), j, i, objArr, android.os.SystemClock.elapsedRealtimeNanos());
            if (iProtoLogGroup.isLogToLogcat()) {
                logToLogcat(iProtoLogGroup.getTag(), logLevel, j, str, objArr);
            }
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpTransitionTraceConfig() {
        final android.util.proto.ProtoInputStream inputStream = this.mViewerConfigInputStreamProvider.getInputStream();
        if (inputStream == null) {
            android.util.Slog.w(LOG_TAG, "Failed to get viewer input stream.");
        } else {
            this.mDataSource.trace(new android.tracing.perfetto.TraceFunction() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda1
                @Override // android.tracing.perfetto.TraceFunction
                public final void trace(android.tracing.perfetto.TracingContext tracingContext) {
                    com.android.internal.protolog.PerfettoProtoLogImpl.lambda$dumpTransitionTraceConfig$1(android.util.proto.ProtoInputStream.this, tracingContext);
                }
            });
            this.mDataSource.flush();
        }
    }

    static /* synthetic */ void lambda$dumpTransitionTraceConfig$1(android.util.proto.ProtoInputStream protoInputStream, android.tracing.perfetto.TracingContext tracingContext) throws java.io.IOException {
        android.util.proto.ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
        newTracePacket.write(1116691496968L, android.os.SystemClock.elapsedRealtimeNanos());
        long start = newTracePacket.start(1146756268137L);
        while (protoInputStream.nextField() != -1) {
            long j = 1138166333442L;
            if (protoInputStream.getFieldNumber() == 1) {
                long start2 = protoInputStream.start(2246267895809L);
                long start3 = newTracePacket.start(2246267895809L);
                while (protoInputStream.nextField() != -1) {
                    switch (protoInputStream.getFieldNumber()) {
                        case 1:
                            newTracePacket.write(1125281431553L, protoInputStream.readLong(1125281431553L));
                            j = 1138166333442L;
                            break;
                        case 2:
                            newTracePacket.write(j, protoInputStream.readString(j));
                            break;
                        case 3:
                            newTracePacket.write(1159641169923L, protoInputStream.readInt(1159641169923L));
                            break;
                        case 4:
                            newTracePacket.write(1155346202628L, protoInputStream.readInt(1155346202628L));
                            break;
                        default:
                            throw new java.lang.RuntimeException("Unexpected field id " + protoInputStream.getFieldNumber());
                    }
                }
                protoInputStream.end(start2);
                newTracePacket.end(start3);
            }
            if (protoInputStream.getFieldNumber() == 2) {
                long start4 = protoInputStream.start(2246267895810L);
                long start5 = newTracePacket.start(2246267895810L);
                while (protoInputStream.nextField() != -1) {
                    switch (protoInputStream.getFieldNumber()) {
                        case 1:
                            newTracePacket.write(1155346202625L, protoInputStream.readInt(1155346202625L));
                            break;
                        case 2:
                            newTracePacket.write(1138166333442L, protoInputStream.readString(1138166333442L));
                            break;
                        case 3:
                            newTracePacket.write(1138166333443L, protoInputStream.readString(1138166333443L));
                            break;
                        default:
                            throw new java.lang.RuntimeException("Unexpected field id " + protoInputStream.getFieldNumber());
                    }
                }
                protoInputStream.end(start4);
                newTracePacket.end(start5);
            }
        }
        newTracePacket.end(start);
        tracingContext.flush();
    }

    private void logToLogcat(java.lang.String str, com.android.internal.protolog.common.LogLevel logLevel, long j, java.lang.String str2, java.lang.Object[] objArr) {
        android.os.Trace.traceBegin(32L, "logToLogcat");
        try {
            doLogToLogcat(str, logLevel, j, str2, objArr);
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void doLogToLogcat(java.lang.String str, com.android.internal.protolog.common.LogLevel logLevel, long j, java.lang.String str2, java.lang.Object[] objArr) {
        if (str2 == null) {
            str2 = this.mViewerConfigReader.getViewerString(j);
        }
        if (str2 != null) {
            if (objArr != null) {
                try {
                    str2 = android.text.TextUtils.formatSimple(str2, objArr);
                } catch (java.lang.Exception e) {
                    android.util.Slog.w(LOG_TAG, "Invalid ProtoLog format string.", e);
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

    private void logToProto(com.android.internal.protolog.common.LogLevel logLevel, java.lang.String str, long j, int i, java.lang.Object[] objArr, long j2) {
        if (!isProtoEnabled()) {
            return;
        }
        android.os.Trace.traceBegin(32L, "logToProto");
        try {
            doLogToProto(logLevel, str, j, i, objArr, j2);
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    private void doLogToProto(final com.android.internal.protolog.common.LogLevel logLevel, final java.lang.String str, final long j, final int i, final java.lang.Object[] objArr, final long j2) {
        this.mDataSource.trace(new android.tracing.perfetto.TraceFunction() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda2
            @Override // android.tracing.perfetto.TraceFunction
            public final void trace(android.tracing.perfetto.TracingContext tracingContext) {
                com.android.internal.protolog.PerfettoProtoLogImpl.this.lambda$doLogToProto$4(str, logLevel, objArr, i, j2, j, tracingContext);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doLogToProto$4(java.lang.String str, com.android.internal.protolog.common.LogLevel logLevel, java.lang.Object[] objArr, int i, long j, long j2, android.tracing.perfetto.TracingContext tracingContext) throws java.io.IOException {
        int i2;
        long j3;
        boolean z;
        java.lang.Object[] objArr2 = objArr;
        int i3 = i;
        com.android.internal.protolog.ProtoLogDataSource.TlsState tlsState = (com.android.internal.protolog.ProtoLogDataSource.TlsState) tracingContext.getCustomTlsState();
        if (logLevel.ordinal() < tlsState.getLogFromLevel(str).ordinal()) {
            return;
        }
        if (objArr2 != null) {
            int i4 = 0;
            for (java.lang.Object obj : objArr2) {
                if (com.android.internal.protolog.common.LogDataType.bitmaskToLogDataType(i3, i4) == 0) {
                    internStringArg(tracingContext, obj.toString());
                }
                i4++;
            }
        }
        if (!tlsState.getShouldCollectStacktrace(str)) {
            i2 = 0;
        } else {
            i2 = internStacktraceString(tracingContext, collectStackTrace());
        }
        final android.util.proto.ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
        newTracePacket.write(1116691496968L, j);
        long start = newTracePacket.start(1146756268136L);
        newTracePacket.write(1125281431553L, j2);
        if (objArr2 == null) {
            j3 = start;
            z = false;
        } else {
            android.util.LongArray longArray = new android.util.LongArray();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            int length = objArr2.length;
            j3 = start;
            int i5 = 0;
            int i6 = 0;
            boolean z2 = false;
            while (i5 < length) {
                java.lang.Object obj2 = objArr2[i5];
                switch (com.android.internal.protolog.common.LogDataType.bitmaskToLogDataType(i3, i6)) {
                    case 0:
                        newTracePacket.write(2254857830402L, internStringArg(tracingContext, obj2.toString()));
                        z2 = true;
                        break;
                    case 1:
                        longArray.add(((java.lang.Number) obj2).longValue());
                        break;
                    case 2:
                        arrayList.add(java.lang.Double.valueOf(((java.lang.Number) obj2).doubleValue()));
                        break;
                    case 3:
                        try {
                            arrayList2.add(java.lang.Boolean.valueOf(((java.lang.Boolean) obj2).booleanValue()));
                            break;
                        } catch (java.lang.ClassCastException e) {
                            android.util.Slog.e(LOG_TAG, "Invalid ProtoLog paramsMask", e);
                            break;
                        }
                }
                i6++;
                i5++;
                objArr2 = objArr;
                i3 = i;
            }
            for (int i7 = 0; i7 < longArray.size(); i7++) {
                newTracePacket.write(android.internal.perfetto.protos.PerfettoTrace.ProtoLogMessage.SINT64_PARAMS, longArray.get(i7));
            }
            arrayList.forEach(new java.util.function.Consumer() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj3) {
                    android.util.proto.ProtoOutputStream.this.write(android.internal.perfetto.protos.PerfettoTrace.ProtoLogMessage.DOUBLE_PARAMS, ((java.lang.Double) obj3).doubleValue());
                }
            });
            arrayList2.forEach(new java.util.function.Consumer() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj3) {
                    android.util.proto.ProtoOutputStream.this.write(android.internal.perfetto.protos.PerfettoTrace.ProtoLogMessage.BOOLEAN_PARAMS, r3.booleanValue() ? 1 : 0);
                }
            });
            z = z2;
        }
        if (tlsState.getShouldCollectStacktrace(str)) {
            newTracePacket.write(1155346202630L, i2);
        }
        newTracePacket.end(j3);
        if (z) {
            newTracePacket.write(1155346202637L, 2);
        }
    }

    private java.lang.String collectStackTrace() {
        java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace();
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        java.io.PrintWriter printWriter = new java.io.PrintWriter(stringWriter);
        for (int i = 12; i < stackTrace.length; i++) {
            try {
                printWriter.println("\tat " + stackTrace[i]);
            } catch (java.lang.Throwable th) {
                try {
                    printWriter.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        printWriter.close();
        return stringWriter.toString();
    }

    private int internStacktraceString(android.tracing.perfetto.TracingContext<com.android.internal.protolog.ProtoLogDataSource.Instance, com.android.internal.protolog.ProtoLogDataSource.TlsState, com.android.internal.protolog.ProtoLogDataSource.IncrementalState> tracingContext, java.lang.String str) {
        return internString(tracingContext, tracingContext.getIncrementalState().stacktraceInterningMap, android.internal.perfetto.protos.PerfettoTrace.InternedData.PROTOLOG_STACKTRACE, str);
    }

    private int internStringArg(android.tracing.perfetto.TracingContext<com.android.internal.protolog.ProtoLogDataSource.Instance, com.android.internal.protolog.ProtoLogDataSource.TlsState, com.android.internal.protolog.ProtoLogDataSource.IncrementalState> tracingContext, java.lang.String str) {
        return internString(tracingContext, tracingContext.getIncrementalState().argumentInterningMap, 2246267895844L, str);
    }

    private int internString(android.tracing.perfetto.TracingContext<com.android.internal.protolog.ProtoLogDataSource.Instance, com.android.internal.protolog.ProtoLogDataSource.TlsState, com.android.internal.protolog.ProtoLogDataSource.IncrementalState> tracingContext, java.util.Map<java.lang.String, java.lang.Integer> map, long j, java.lang.String str) {
        com.android.internal.protolog.ProtoLogDataSource.IncrementalState incrementalState = tracingContext.getIncrementalState();
        if (!incrementalState.clearReported) {
            tracingContext.newTracePacket().write(1155346202637L, 1);
            incrementalState.clearReported = true;
        }
        if (!map.containsKey(str)) {
            int size = map.size() + 1;
            map.put(str, java.lang.Integer.valueOf(size));
            android.util.proto.ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
            long start = newTracePacket.start(1146756268044L);
            long start2 = newTracePacket.start(j);
            newTracePacket.write(1116691496961L, size);
            newTracePacket.write(1151051235330L, str.getBytes());
            newTracePacket.end(start2);
            newTracePacket.end(start);
        }
        return map.get(str).intValue();
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
        com.android.internal.protolog.common.ILogger iLogger = new com.android.internal.protolog.common.ILogger() { // from class: com.android.internal.protolog.PerfettoProtoLogImpl$$ExternalSyntheticLambda0
            @Override // com.android.internal.protolog.common.ILogger
            public final void log(java.lang.String str) {
                com.android.internal.protolog.PerfettoProtoLogImpl.logAndPrintln(outPrintWriter, str);
            }
        };
        java.lang.String[] strArr = (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
        switch (nextArg.hashCode()) {
            case -1475003593:
                if (nextArg.equals("enable-text")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1032071950:
                if (nextArg.equals("disable-text")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return unknownCommand(outPrintWriter);
    }

    private int unknownCommand(java.io.PrintWriter printWriter) {
        printWriter.println("Unknown command");
        printWriter.println("Window manager logging options:");
        printWriter.println("  enable-text [group...]: Enable logcat logging for given groups");
        printWriter.println("  disable-text [group...]: Disable logcat logging for given groups");
        return -1;
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public boolean isProtoEnabled() {
        return this.mTracingInstances.get() > 0;
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int startLoggingToLogcat(java.lang.String[] strArr, com.android.internal.protolog.common.ILogger iLogger) {
        this.mViewerConfigReader.loadViewerConfig(iLogger);
        return setTextLogging(true, iLogger, strArr);
    }

    @Override // com.android.internal.protolog.common.IProtoLog
    public int stopLoggingToLogcat(java.lang.String[] strArr, com.android.internal.protolog.common.ILogger iLogger) {
        this.mViewerConfigReader.unloadViewerConfig();
        return setTextLogging(false, iLogger, strArr);
    }

    public int startLoggingStackTrace(java.lang.String[] strArr, com.android.internal.protolog.common.ILogger iLogger) {
        return -1;
    }

    public int stopLoggingStackTrace() {
        return -1;
    }

    private int setTextLogging(boolean z, com.android.internal.protolog.common.ILogger iLogger, java.lang.String... strArr) {
        for (java.lang.String str : strArr) {
            com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup = this.mLogGroups.get(str);
            if (iProtoLogGroup != null) {
                iProtoLogGroup.setLogToLogcat(z);
            } else {
                iLogger.log("No IProtoLogGroup named " + str);
                return -1;
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void logAndPrintln(java.io.PrintWriter printWriter, java.lang.String str) {
        android.util.Slog.i(LOG_TAG, str);
        if (printWriter != null) {
            printWriter.println(str);
            printWriter.flush();
        }
    }
}
