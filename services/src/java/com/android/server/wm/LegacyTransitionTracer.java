package com.android.server.wm;

/* loaded from: classes3.dex */
class LegacyTransitionTracer implements com.android.server.wm.TransitionTracer {
    private static final int ACTIVE_TRACING_BUFFER_CAPACITY = 5120000;
    private static final int ALWAYS_ON_TRACING_CAPACITY = 15360;
    private static final int CHUNK_SIZE = 64;
    private static final java.lang.String LOG_TAG = "TransitionTracer";
    private static final long MAGIC_NUMBER_VALUE = 4990904633914184276L;
    private static final java.lang.String TRACE_FILE = "/data/misc/wmtrace/wm_transition_trace.winscope";
    static final java.lang.String WINSCOPE_EXT = ".winscope";
    private final com.android.internal.util.TraceBuffer mTraceBuffer = new com.android.internal.util.TraceBuffer(ALWAYS_ON_TRACING_CAPACITY);
    private final java.lang.Object mEnabledLock = new java.lang.Object();
    private volatile boolean mActiveTracingEnabled = false;

    LegacyTransitionTracer() {
    }

    @Override // com.android.server.wm.TransitionTracer
    public void logSentTransition(com.android.server.wm.Transition transition, java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList) {
        try {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(64);
            long start = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(1120986464257L, transition.getSyncId());
            protoOutputStream.write(1112396529668L, transition.mLogger.mCreateTimeNs);
            protoOutputStream.write(1112396529669L, transition.mLogger.mSendTimeNs);
            protoOutputStream.write(1116691496962L, transition.getStartTransaction().getId());
            protoOutputStream.write(1116691496963L, transition.getFinishTransaction().getId());
            dumpTransitionTargetsToProto(protoOutputStream, transition, arrayList);
            protoOutputStream.end(start);
            this.mTraceBuffer.add(protoOutputStream);
        } catch (java.lang.Exception e) {
            android.util.Log.e(LOG_TAG, "Unexpected exception thrown while logging transitions", e);
        }
    }

    @Override // com.android.server.wm.TransitionTracer
    public void logFinishedTransition(com.android.server.wm.Transition transition) {
        try {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(64);
            long start = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(1120986464257L, transition.getSyncId());
            protoOutputStream.write(1112396529670L, transition.mLogger.mFinishTimeNs);
            protoOutputStream.end(start);
            this.mTraceBuffer.add(protoOutputStream);
        } catch (java.lang.Exception e) {
            android.util.Log.e(LOG_TAG, "Unexpected exception thrown while logging transitions", e);
        }
    }

    @Override // com.android.server.wm.TransitionTracer
    public void logAbortedTransition(com.android.server.wm.Transition transition) {
        try {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(64);
            long start = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(1120986464257L, transition.getSyncId());
            protoOutputStream.write(1112396529674L, transition.mLogger.mAbortTimeNs);
            protoOutputStream.end(start);
            this.mTraceBuffer.add(protoOutputStream);
        } catch (java.lang.Exception e) {
            android.util.Log.e(LOG_TAG, "Unexpected exception thrown while logging transitions", e);
        }
    }

    @Override // com.android.server.wm.TransitionTracer
    public void logRemovingStartingWindow(@android.annotation.NonNull com.android.server.wm.StartingData startingData) {
        if (startingData.mTransitionId == 0) {
            return;
        }
        try {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(64);
            long start = protoOutputStream.start(2246267895810L);
            protoOutputStream.write(1120986464257L, startingData.mTransitionId);
            protoOutputStream.write(1112396529675L, android.os.SystemClock.elapsedRealtimeNanos());
            protoOutputStream.end(start);
            this.mTraceBuffer.add(protoOutputStream);
        } catch (java.lang.Exception e) {
            android.util.Log.e(LOG_TAG, "Unexpected exception thrown while logging transitions", e);
        }
    }

    private void dumpTransitionTargetsToProto(android.util.proto.ProtoOutputStream protoOutputStream, com.android.server.wm.Transition transition, java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList) {
        int i;
        android.os.Trace.beginSection("TransitionTracer#dumpTransitionTargetsToProto");
        if (this.mActiveTracingEnabled) {
            protoOutputStream.write(1120986464257L, transition.getSyncId());
        }
        protoOutputStream.write(1120986464263L, transition.mType);
        protoOutputStream.write(1120986464265L, transition.getFlags());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            long start = protoOutputStream.start(2246267895816L);
            com.android.server.wm.Transition.ChangeInfo changeInfo = arrayList.get(i2);
            if (changeInfo.mContainer.mSurfaceControl.isValid()) {
                i = changeInfo.mContainer.mSurfaceControl.getLayerId();
            } else {
                i = -1;
            }
            protoOutputStream.write(1120986464257L, changeInfo.mReadyMode);
            protoOutputStream.write(1120986464260L, changeInfo.mReadyFlags);
            protoOutputStream.write(1120986464258L, i);
            if (this.mActiveTracingEnabled) {
                protoOutputStream.write(1120986464259L, java.lang.System.identityHashCode(changeInfo.mContainer));
            }
            protoOutputStream.end(start);
        }
        android.os.Trace.endSection();
    }

    @Override // com.android.server.wm.TransitionTracer
    public void startTrace(@android.annotation.Nullable java.io.PrintWriter printWriter) {
        if (android.os.Build.IS_USER) {
            com.android.server.wm.LegacyTransitionTracer.LogAndPrintln.e(printWriter, "Tracing is not supported on user builds.");
            return;
        }
        android.os.Trace.beginSection("TransitionTracer#startTrace");
        com.android.server.wm.LegacyTransitionTracer.LogAndPrintln.i(printWriter, "Starting shell transition trace.");
        synchronized (this.mEnabledLock) {
            this.mActiveTracingEnabled = true;
            this.mTraceBuffer.resetBuffer();
            this.mTraceBuffer.setCapacity(ACTIVE_TRACING_BUFFER_CAPACITY);
        }
        android.os.Trace.endSection();
    }

    @Override // com.android.server.wm.TransitionTracer
    public void stopTrace(@android.annotation.Nullable java.io.PrintWriter printWriter) {
        stopTrace(printWriter, new java.io.File(TRACE_FILE));
    }

    public void stopTrace(@android.annotation.Nullable java.io.PrintWriter printWriter, java.io.File file) {
        if (android.os.Build.IS_USER) {
            com.android.server.wm.LegacyTransitionTracer.LogAndPrintln.e(printWriter, "Tracing is not supported on user builds.");
            return;
        }
        android.os.Trace.beginSection("TransitionTracer#stopTrace");
        com.android.server.wm.LegacyTransitionTracer.LogAndPrintln.i(printWriter, "Stopping shell transition trace.");
        synchronized (this.mEnabledLock) {
            this.mActiveTracingEnabled = false;
            writeTraceToFileLocked(printWriter, file);
            this.mTraceBuffer.resetBuffer();
            this.mTraceBuffer.setCapacity(ALWAYS_ON_TRACING_CAPACITY);
        }
        android.os.Trace.endSection();
    }

    @Override // com.android.server.wm.TransitionTracer
    public void saveForBugreport(@android.annotation.Nullable java.io.PrintWriter printWriter) {
        if (android.os.Build.IS_USER) {
            com.android.server.wm.LegacyTransitionTracer.LogAndPrintln.e(printWriter, "Tracing is not supported on user builds.");
            return;
        }
        android.os.Trace.beginSection("TransitionTracer#saveForBugreport");
        synchronized (this.mEnabledLock) {
            writeTraceToFileLocked(printWriter, new java.io.File(TRACE_FILE));
        }
        android.os.Trace.endSection();
    }

    @Override // com.android.server.wm.TransitionTracer
    public boolean isTracing() {
        return this.mActiveTracingEnabled;
    }

    private void writeTraceToFileLocked(@android.annotation.Nullable java.io.PrintWriter printWriter, java.io.File file) {
        android.os.Trace.beginSection("TransitionTracer#writeTraceToFileLocked");
        try {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(64);
            protoOutputStream.write(1125281431553L, MAGIC_NUMBER_VALUE);
            protoOutputStream.write(1125281431555L, java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(java.lang.System.currentTimeMillis()) - android.os.SystemClock.elapsedRealtimeNanos());
            com.android.server.wm.LegacyTransitionTracer.LogAndPrintln.i(printWriter, "Writing file to " + file.getAbsolutePath() + " from process " + android.os.Process.myPid());
            this.mTraceBuffer.writeTraceToFile(file, protoOutputStream);
        } catch (java.io.IOException e) {
            com.android.server.wm.LegacyTransitionTracer.LogAndPrintln.e(printWriter, "Unable to write buffer to file", e);
        }
        android.os.Trace.endSection();
    }

    private static class LogAndPrintln {
        private LogAndPrintln() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void i(@android.annotation.Nullable java.io.PrintWriter printWriter, java.lang.String str) {
            android.util.Log.i(com.android.server.wm.LegacyTransitionTracer.LOG_TAG, str);
            if (printWriter != null) {
                printWriter.println(str);
                printWriter.flush();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void e(@android.annotation.Nullable java.io.PrintWriter printWriter, java.lang.String str) {
            android.util.Log.e(com.android.server.wm.LegacyTransitionTracer.LOG_TAG, str);
            if (printWriter != null) {
                printWriter.println("ERROR: " + str);
                printWriter.flush();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void e(@android.annotation.Nullable java.io.PrintWriter printWriter, java.lang.String str, @android.annotation.NonNull java.lang.Exception exc) {
            android.util.Log.e(com.android.server.wm.LegacyTransitionTracer.LOG_TAG, str, exc);
            if (printWriter != null) {
                printWriter.println("ERROR: " + str + " ::\n " + exc);
                printWriter.flush();
            }
        }
    }
}
