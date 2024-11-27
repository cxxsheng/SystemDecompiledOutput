package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public abstract class ImeTracing {
    public static final int IME_TRACING_FROM_CLIENT = 0;
    public static final int IME_TRACING_FROM_IMMS = 2;
    public static final int IME_TRACING_FROM_IMS = 1;
    public static final java.lang.String PROTO_ARG = "--proto-com-android-imetracing";
    static final java.lang.String TAG = "imeTracing";
    static boolean sEnabled = false;
    private static com.android.internal.inputmethod.ImeTracing sInstance;
    protected boolean mDumpInProgress;
    private final boolean mIsAvailable = android.view.inputmethod.InputMethodManagerGlobal.isImeTraceAvailable();
    protected final java.lang.Object mDumpInProgressLock = new java.lang.Object();

    @java.lang.FunctionalInterface
    public interface ServiceDumper {
        void dumpToProto(android.util.proto.ProtoOutputStream protoOutputStream, byte[] bArr);
    }

    public abstract void addToBuffer(android.util.proto.ProtoOutputStream protoOutputStream, int i);

    public abstract void startTrace(java.io.PrintWriter printWriter);

    public abstract void stopTrace(java.io.PrintWriter printWriter);

    public abstract void triggerClientDump(java.lang.String str, android.view.inputmethod.InputMethodManager inputMethodManager, byte[] bArr);

    public abstract void triggerManagerServiceDump(java.lang.String str);

    public abstract void triggerServiceDump(java.lang.String str, com.android.internal.inputmethod.ImeTracing.ServiceDumper serviceDumper, byte[] bArr);

    public static com.android.internal.inputmethod.ImeTracing getInstance() {
        if (sInstance == null) {
            if (isSystemProcess()) {
                sInstance = new com.android.internal.inputmethod.ImeTracingServerImpl();
            } else {
                sInstance = new com.android.internal.inputmethod.ImeTracingClientImpl();
            }
        }
        return sInstance;
    }

    public void sendToService(byte[] bArr, int i, java.lang.String str) {
        android.view.inputmethod.InputMethodManagerGlobal.startProtoDump(bArr, i, str, new java.util.function.Consumer() { // from class: com.android.internal.inputmethod.ImeTracing$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.util.Log.e(com.android.internal.inputmethod.ImeTracing.TAG, "Exception while sending ime-related dump to server", (android.os.RemoteException) obj);
            }
        });
    }

    public final void startImeTrace() {
        android.view.inputmethod.InputMethodManagerGlobal.startImeTrace(new java.util.function.Consumer() { // from class: com.android.internal.inputmethod.ImeTracing$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.util.Log.e(com.android.internal.inputmethod.ImeTracing.TAG, "Could not start ime trace.", (android.os.RemoteException) obj);
            }
        });
    }

    public final void stopImeTrace() {
        android.view.inputmethod.InputMethodManagerGlobal.stopImeTrace(new java.util.function.Consumer() { // from class: com.android.internal.inputmethod.ImeTracing$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.util.Log.e(com.android.internal.inputmethod.ImeTracing.TAG, "Could not stop ime trace.", (android.os.RemoteException) obj);
            }
        });
    }

    public void saveForBugreport(java.io.PrintWriter printWriter) {
    }

    public void setEnabled(boolean z) {
        sEnabled = z;
    }

    public boolean isEnabled() {
        return sEnabled;
    }

    public boolean isAvailable() {
        return this.mIsAvailable;
    }

    private static boolean isSystemProcess() {
        return android.app.ActivityThread.isSystem();
    }

    protected void logAndPrintln(java.io.PrintWriter printWriter, java.lang.String str) {
        android.util.Log.i(TAG, str);
        if (printWriter != null) {
            printWriter.println(str);
            printWriter.flush();
        }
    }
}
