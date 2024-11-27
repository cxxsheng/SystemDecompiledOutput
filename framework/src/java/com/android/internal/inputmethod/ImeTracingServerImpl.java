package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
class ImeTracingServerImpl extends com.android.internal.inputmethod.ImeTracing {
    private static final int BUFFER_CAPACITY = 4194304;
    private static final long MAGIC_NUMBER_CLIENTS_VALUE = 4990904633913462089L;
    private static final long MAGIC_NUMBER_IMMS_VALUE = 4990904633914117449L;
    private static final long MAGIC_NUMBER_IMS_VALUE = 4990904633914510665L;
    private static final java.lang.String TRACE_DIRNAME = "/data/misc/wmtrace/";
    private static final java.lang.String TRACE_FILENAME_CLIENTS = "ime_trace_clients.winscope";
    private static final java.lang.String TRACE_FILENAME_IMMS = "ime_trace_managerservice.winscope";
    private static final java.lang.String TRACE_FILENAME_IMS = "ime_trace_service.winscope";
    private final java.lang.Object mEnabledLock = new java.lang.Object();
    private final com.android.internal.util.TraceBuffer mBufferClients = new com.android.internal.util.TraceBuffer(4194304);
    private final java.io.File mTraceFileClients = new java.io.File("/data/misc/wmtrace/ime_trace_clients.winscope");
    private final com.android.internal.util.TraceBuffer mBufferIms = new com.android.internal.util.TraceBuffer(4194304);
    private final java.io.File mTraceFileIms = new java.io.File("/data/misc/wmtrace/ime_trace_service.winscope");
    private final com.android.internal.util.TraceBuffer mBufferImms = new com.android.internal.util.TraceBuffer(4194304);
    private final java.io.File mTraceFileImms = new java.io.File("/data/misc/wmtrace/ime_trace_managerservice.winscope");

    ImeTracingServerImpl() {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void addToBuffer(android.util.proto.ProtoOutputStream protoOutputStream, int i) {
        if (isAvailable() && isEnabled()) {
            switch (i) {
                case 0:
                    this.mBufferClients.add(protoOutputStream);
                    break;
                case 1:
                    this.mBufferIms.add(protoOutputStream);
                    break;
                case 2:
                    this.mBufferImms.add(protoOutputStream);
                    break;
                default:
                    android.util.Log.w("imeTracing", "Request to add to buffer, but source not recognised.");
                    break;
            }
        }
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerClientDump(java.lang.String str, android.view.inputmethod.InputMethodManager inputMethodManager, byte[] bArr) {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerServiceDump(java.lang.String str, com.android.internal.inputmethod.ImeTracing.ServiceDumper serviceDumper, byte[] bArr) {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerManagerServiceDump(java.lang.String str) {
        if (!isEnabled() || !isAvailable()) {
            return;
        }
        synchronized (this.mDumpInProgressLock) {
            if (this.mDumpInProgress) {
                return;
            }
            this.mDumpInProgress = true;
            try {
                sendToService(null, 2, str);
            } finally {
                this.mDumpInProgress = false;
            }
        }
    }

    private void writeTracesToFilesLocked() {
        try {
            long nanos = java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(java.lang.System.currentTimeMillis()) - android.os.SystemClock.elapsedRealtimeNanos();
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
            protoOutputStream.write(1125281431553L, MAGIC_NUMBER_CLIENTS_VALUE);
            protoOutputStream.write(1125281431555L, nanos);
            this.mBufferClients.writeTraceToFile(this.mTraceFileClients, protoOutputStream);
            android.util.proto.ProtoOutputStream protoOutputStream2 = new android.util.proto.ProtoOutputStream();
            protoOutputStream2.write(1125281431553L, MAGIC_NUMBER_IMS_VALUE);
            protoOutputStream2.write(1125281431555L, nanos);
            this.mBufferIms.writeTraceToFile(this.mTraceFileIms, protoOutputStream2);
            android.util.proto.ProtoOutputStream protoOutputStream3 = new android.util.proto.ProtoOutputStream();
            protoOutputStream3.write(1125281431553L, MAGIC_NUMBER_IMMS_VALUE);
            protoOutputStream3.write(1125281431555L, nanos);
            this.mBufferImms.writeTraceToFile(this.mTraceFileImms, protoOutputStream3);
            resetBuffers();
        } catch (java.io.IOException e) {
            android.util.Log.e("imeTracing", "Unable to write buffer to file", e);
        }
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void startTrace(java.io.PrintWriter printWriter) {
        if (android.os.Build.IS_USER) {
            android.util.Log.w("imeTracing", "Warn: Tracing is not supported on user builds.");
            return;
        }
        synchronized (this.mEnabledLock) {
            if (isAvailable() && isEnabled()) {
                android.util.Log.w("imeTracing", "Warn: Tracing is already started.");
                return;
            }
            logAndPrintln(printWriter, "Starting tracing in /data/misc/wmtrace/: ime_trace_clients.winscope, ime_trace_service.winscope, ime_trace_managerservice.winscope");
            sEnabled = true;
            resetBuffers();
        }
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void stopTrace(java.io.PrintWriter printWriter) {
        if (android.os.Build.IS_USER) {
            android.util.Log.w("imeTracing", "Warn: Tracing is not supported on user builds.");
            return;
        }
        synchronized (this.mEnabledLock) {
            if (isAvailable() && isEnabled()) {
                logAndPrintln(printWriter, "Stopping tracing and writing traces in /data/misc/wmtrace/: ime_trace_clients.winscope, ime_trace_service.winscope, ime_trace_managerservice.winscope");
                sEnabled = false;
                writeTracesToFilesLocked();
                return;
            }
            android.util.Log.w("imeTracing", "Warn: Tracing is not available or not started.");
        }
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void saveForBugreport(java.io.PrintWriter printWriter) {
        if (android.os.Build.IS_USER) {
            return;
        }
        synchronized (this.mEnabledLock) {
            if (isAvailable() && isEnabled()) {
                sEnabled = false;
                logAndPrintln(printWriter, "Writing traces in /data/misc/wmtrace/: ime_trace_clients.winscope, ime_trace_service.winscope, ime_trace_managerservice.winscope");
                writeTracesToFilesLocked();
                sEnabled = true;
            }
        }
    }

    private void resetBuffers() {
        this.mBufferClients.resetBuffer();
        this.mBufferIms.resetBuffer();
        this.mBufferImms.resetBuffer();
    }
}
