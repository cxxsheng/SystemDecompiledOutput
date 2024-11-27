package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
class ImeTracingClientImpl extends com.android.internal.inputmethod.ImeTracing {
    ImeTracingClientImpl() {
        sEnabled = android.view.inputmethod.InputMethodManagerGlobal.isImeTraceEnabled();
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void addToBuffer(android.util.proto.ProtoOutputStream protoOutputStream, int i) {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerClientDump(java.lang.String str, android.view.inputmethod.InputMethodManager inputMethodManager, byte[] bArr) {
        if (!isEnabled() || !isAvailable()) {
            return;
        }
        synchronized (this.mDumpInProgressLock) {
            if (this.mDumpInProgress) {
                return;
            }
            this.mDumpInProgress = true;
            try {
                android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
                inputMethodManager.dumpDebug(protoOutputStream, bArr);
                sendToService(protoOutputStream.getBytes(), 0, str);
            } finally {
                this.mDumpInProgress = false;
            }
        }
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerServiceDump(java.lang.String str, com.android.internal.inputmethod.ImeTracing.ServiceDumper serviceDumper, byte[] bArr) {
        if (!isEnabled() || !isAvailable()) {
            return;
        }
        synchronized (this.mDumpInProgressLock) {
            if (this.mDumpInProgress) {
                return;
            }
            this.mDumpInProgress = true;
            try {
                android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
                serviceDumper.dumpToProto(protoOutputStream, bArr);
                sendToService(protoOutputStream.getBytes(), 1, str);
            } finally {
                this.mDumpInProgress = false;
            }
        }
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void triggerManagerServiceDump(java.lang.String str) {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void startTrace(java.io.PrintWriter printWriter) {
    }

    @Override // com.android.internal.inputmethod.ImeTracing
    public void stopTrace(java.io.PrintWriter printWriter) {
    }
}
