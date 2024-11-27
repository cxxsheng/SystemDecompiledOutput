package com.android.server.wm;

/* loaded from: classes3.dex */
class PerfettoTransitionTracer implements com.android.server.wm.TransitionTracer {
    private final java.util.concurrent.atomic.AtomicInteger mActiveTraces = new java.util.concurrent.atomic.AtomicInteger(0);
    private final android.tracing.transition.TransitionDataSource mDataSource;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$0() {
    }

    PerfettoTransitionTracer() {
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = this.mActiveTraces;
        java.util.Objects.requireNonNull(atomicInteger);
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.wm.PerfettoTransitionTracer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                atomicInteger.incrementAndGet();
            }
        };
        java.lang.Runnable runnable2 = new java.lang.Runnable() { // from class: com.android.server.wm.PerfettoTransitionTracer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.PerfettoTransitionTracer.lambda$new$0();
            }
        };
        final java.util.concurrent.atomic.AtomicInteger atomicInteger2 = this.mActiveTraces;
        java.util.Objects.requireNonNull(atomicInteger2);
        this.mDataSource = new android.tracing.transition.TransitionDataSource(runnable, runnable2, new java.lang.Runnable() { // from class: com.android.server.wm.PerfettoTransitionTracer$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                atomicInteger2.decrementAndGet();
            }
        });
        android.tracing.perfetto.Producer.init(android.tracing.perfetto.InitArguments.DEFAULTS);
        this.mDataSource.register(android.tracing.perfetto.DataSourceParams.DEFAULTS);
    }

    @Override // com.android.server.wm.TransitionTracer
    public void logSentTransition(com.android.server.wm.Transition transition, java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList) {
        if (!isTracing()) {
            return;
        }
        android.os.Trace.traceBegin(32L, "logSentTransition");
        try {
            doLogSentTransition(transition, arrayList);
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    private void doLogSentTransition(final com.android.server.wm.Transition transition, final java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList) {
        this.mDataSource.trace(new android.tracing.perfetto.TraceFunction() { // from class: com.android.server.wm.PerfettoTransitionTracer$$ExternalSyntheticLambda3
            public final void trace(android.tracing.perfetto.TracingContext tracingContext) {
                com.android.server.wm.PerfettoTransitionTracer.this.lambda$doLogSentTransition$1(transition, arrayList, tracingContext);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doLogSentTransition$1(com.android.server.wm.Transition transition, java.util.ArrayList arrayList, android.tracing.perfetto.TracingContext tracingContext) throws java.io.IOException {
        android.util.proto.ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
        long start = newTracePacket.start(1146756268128L);
        newTracePacket.write(1120986464257L, transition.getSyncId());
        newTracePacket.write(1112396529666L, transition.mLogger.mCreateTimeNs);
        newTracePacket.write(1112396529667L, transition.mLogger.mSendTimeNs);
        newTracePacket.write(1116691496970L, transition.getStartTransaction().getId());
        newTracePacket.write(1116691496971L, transition.getFinishTransaction().getId());
        newTracePacket.write(1120986464269L, transition.mType);
        newTracePacket.write(1120986464272L, transition.getFlags());
        addTransitionTargetsToProto(newTracePacket, arrayList);
        newTracePacket.end(start);
    }

    @Override // com.android.server.wm.TransitionTracer
    public void logFinishedTransition(com.android.server.wm.Transition transition) {
        if (!isTracing()) {
            return;
        }
        android.os.Trace.traceBegin(32L, "logFinishedTransition");
        try {
            doLogFinishTransition(transition);
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    private void doLogFinishTransition(final com.android.server.wm.Transition transition) {
        this.mDataSource.trace(new android.tracing.perfetto.TraceFunction() { // from class: com.android.server.wm.PerfettoTransitionTracer$$ExternalSyntheticLambda4
            public final void trace(android.tracing.perfetto.TracingContext tracingContext) {
                com.android.server.wm.PerfettoTransitionTracer.lambda$doLogFinishTransition$2(com.android.server.wm.Transition.this, tracingContext);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$doLogFinishTransition$2(com.android.server.wm.Transition transition, android.tracing.perfetto.TracingContext tracingContext) throws java.io.IOException {
        android.util.proto.ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
        long start = newTracePacket.start(1146756268128L);
        newTracePacket.write(1120986464257L, transition.getSyncId());
        newTracePacket.write(1112396529673L, transition.mLogger.mFinishTimeNs);
        newTracePacket.end(start);
    }

    @Override // com.android.server.wm.TransitionTracer
    public void logAbortedTransition(com.android.server.wm.Transition transition) {
        if (!isTracing()) {
            return;
        }
        android.os.Trace.traceBegin(32L, "logAbortedTransition");
        try {
            doLogAbortedTransition(transition);
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    private void doLogAbortedTransition(final com.android.server.wm.Transition transition) {
        this.mDataSource.trace(new android.tracing.perfetto.TraceFunction() { // from class: com.android.server.wm.PerfettoTransitionTracer$$ExternalSyntheticLambda6
            public final void trace(android.tracing.perfetto.TracingContext tracingContext) {
                com.android.server.wm.PerfettoTransitionTracer.lambda$doLogAbortedTransition$3(com.android.server.wm.Transition.this, tracingContext);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$doLogAbortedTransition$3(com.android.server.wm.Transition transition, android.tracing.perfetto.TracingContext tracingContext) throws java.io.IOException {
        android.util.proto.ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
        long start = newTracePacket.start(1146756268128L);
        newTracePacket.write(1120986464257L, transition.getSyncId());
        newTracePacket.write(1112396529672L, transition.mLogger.mAbortTimeNs);
        newTracePacket.end(start);
    }

    @Override // com.android.server.wm.TransitionTracer
    public void logRemovingStartingWindow(@android.annotation.NonNull com.android.server.wm.StartingData startingData) {
        if (!isTracing()) {
            return;
        }
        android.os.Trace.traceBegin(32L, "logRemovingStartingWindow");
        try {
            doLogRemovingStartingWindow(startingData);
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    public void doLogRemovingStartingWindow(@android.annotation.NonNull final com.android.server.wm.StartingData startingData) {
        this.mDataSource.trace(new android.tracing.perfetto.TraceFunction() { // from class: com.android.server.wm.PerfettoTransitionTracer$$ExternalSyntheticLambda5
            public final void trace(android.tracing.perfetto.TracingContext tracingContext) {
                com.android.server.wm.PerfettoTransitionTracer.lambda$doLogRemovingStartingWindow$4(com.android.server.wm.StartingData.this, tracingContext);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$doLogRemovingStartingWindow$4(com.android.server.wm.StartingData startingData, android.tracing.perfetto.TracingContext tracingContext) throws java.io.IOException {
        android.util.proto.ProtoOutputStream newTracePacket = tracingContext.newTracePacket();
        long start = newTracePacket.start(1146756268128L);
        newTracePacket.write(1120986464257L, startingData.mTransitionId);
        newTracePacket.write(1112396529681L, android.os.SystemClock.elapsedRealtimeNanos());
        newTracePacket.end(start);
    }

    @Override // com.android.server.wm.TransitionTracer
    public void startTrace(java.io.PrintWriter printWriter) {
    }

    @Override // com.android.server.wm.TransitionTracer
    public void stopTrace(java.io.PrintWriter printWriter) {
    }

    @Override // com.android.server.wm.TransitionTracer
    public void saveForBugreport(java.io.PrintWriter printWriter) {
    }

    @Override // com.android.server.wm.TransitionTracer
    public boolean isTracing() {
        return this.mActiveTraces.get() > 0;
    }

    private void addTransitionTargetsToProto(android.util.proto.ProtoOutputStream protoOutputStream, java.util.ArrayList<com.android.server.wm.Transition.ChangeInfo> arrayList) {
        int i;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            com.android.server.wm.Transition.ChangeInfo changeInfo = arrayList.get(i2);
            if (changeInfo.mContainer.mSurfaceControl.isValid()) {
                i = changeInfo.mContainer.mSurfaceControl.getLayerId();
            } else {
                i = -1;
            }
            int identityHashCode = java.lang.System.identityHashCode(changeInfo.mContainer);
            long start = protoOutputStream.start(2246267895822L);
            protoOutputStream.write(1120986464257L, changeInfo.mReadyMode);
            protoOutputStream.write(1120986464260L, changeInfo.mReadyFlags);
            protoOutputStream.write(1120986464258L, i);
            protoOutputStream.write(1120986464259L, identityHashCode);
            protoOutputStream.end(start);
        }
    }
}
