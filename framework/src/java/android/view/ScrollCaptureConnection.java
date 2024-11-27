package android.view;

/* loaded from: classes4.dex */
public class ScrollCaptureConnection extends android.view.IScrollCaptureConnection.Stub implements android.os.IBinder.DeathRecipient {
    private static final java.lang.String END_CAPTURE = "endCapture";
    private static final java.lang.String REQUEST_IMAGE = "requestImage";
    private static final java.lang.String SESSION = "Session";
    private static final java.lang.String START_CAPTURE = "startCapture";
    private static final java.lang.String TAG = "ScrollCaptureConnection";
    private static final java.lang.String TRACE_TRACK = "Scroll Capture";
    private volatile boolean mActive;
    private android.os.CancellationSignal mCancellation;
    private volatile boolean mConnected;
    private android.view.ScrollCaptureCallback mLocal;
    private final android.graphics.Point mPositionInWindow;
    private android.view.IScrollCaptureCallbacks mRemote;
    private final android.graphics.Rect mScrollBounds;
    private android.view.ScrollCaptureSession mSession;
    private int mTraceId;
    private final java.util.concurrent.Executor mUiThread;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.CloseGuard mCloseGuard = new android.util.CloseGuard();

    public ScrollCaptureConnection(java.util.concurrent.Executor executor, android.view.ScrollCaptureTarget scrollCaptureTarget) {
        this.mUiThread = (java.util.concurrent.Executor) java.util.Objects.requireNonNull(executor, "<uiThread> must non-null");
        java.util.Objects.requireNonNull(scrollCaptureTarget, "<selectedTarget> must non-null");
        this.mScrollBounds = (android.graphics.Rect) java.util.Objects.requireNonNull(android.graphics.Rect.copyOrNull(scrollCaptureTarget.getScrollBounds()), "target.getScrollBounds() must be non-null to construct a client");
        this.mLocal = scrollCaptureTarget.getCallback();
        this.mPositionInWindow = new android.graphics.Point(scrollCaptureTarget.getPositionInWindow());
    }

    @Override // android.view.IScrollCaptureConnection
    public android.os.ICancellationSignal startCapture(android.view.Surface surface, android.view.IScrollCaptureCallbacks iScrollCaptureCallbacks) throws android.os.RemoteException {
        this.mTraceId = java.lang.System.identityHashCode(surface);
        android.os.Trace.asyncTraceForTrackBegin(2L, TRACE_TRACK, "Session", this.mTraceId);
        android.os.Trace.asyncTraceForTrackBegin(2L, TRACE_TRACK, START_CAPTURE, this.mTraceId);
        this.mCloseGuard.open("ScrollCaptureConnection.close");
        if (!surface.isValid()) {
            throw new android.os.RemoteException(new java.lang.IllegalArgumentException("surface must be valid"));
        }
        this.mRemote = (android.view.IScrollCaptureCallbacks) java.util.Objects.requireNonNull(iScrollCaptureCallbacks, "<callbacks> must non-null");
        this.mRemote.asBinder().linkToDeath(this, 0);
        this.mConnected = true;
        android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
        this.mCancellation = android.os.CancellationSignal.fromTransport(createTransport);
        this.mSession = new android.view.ScrollCaptureSession(surface, this.mScrollBounds, this.mPositionInWindow);
        final java.lang.Runnable create = android.view.ScrollCaptureConnection.SafeCallback.create(this.mCancellation, this.mUiThread, new java.lang.Runnable() { // from class: android.view.ScrollCaptureConnection$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                android.view.ScrollCaptureConnection.this.onStartCaptureCompleted();
            }
        });
        this.mUiThread.execute(new java.lang.Runnable() { // from class: android.view.ScrollCaptureConnection$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                android.view.ScrollCaptureConnection.this.lambda$startCapture$0(create);
            }
        });
        return createTransport;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startCapture$0(java.lang.Runnable runnable) {
        this.mLocal.onScrollCaptureStart(this.mSession, this.mCancellation, runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStartCaptureCompleted() {
        this.mActive = true;
        try {
            this.mRemote.onCaptureStarted();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Shutting down due to error: ", e);
            close();
        }
        this.mCancellation = null;
        android.os.Trace.asyncTraceForTrackEnd(2L, TRACE_TRACK, this.mTraceId);
    }

    @Override // android.view.IScrollCaptureConnection
    public android.os.ICancellationSignal requestImage(final android.graphics.Rect rect) throws android.os.RemoteException {
        android.os.Trace.asyncTraceForTrackBegin(2L, TRACE_TRACK, REQUEST_IMAGE, this.mTraceId);
        checkActive();
        cancelPendingAction();
        android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
        this.mCancellation = android.os.CancellationSignal.fromTransport(createTransport);
        final java.util.function.Consumer create = android.view.ScrollCaptureConnection.SafeCallback.create(this.mCancellation, this.mUiThread, new java.util.function.Consumer() { // from class: android.view.ScrollCaptureConnection$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.view.ScrollCaptureConnection.this.onImageRequestCompleted((android.graphics.Rect) obj);
            }
        });
        this.mUiThread.execute(new java.lang.Runnable() { // from class: android.view.ScrollCaptureConnection$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                android.view.ScrollCaptureConnection.this.lambda$requestImage$1(rect, create);
            }
        });
        return createTransport;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestImage$1(android.graphics.Rect rect, java.util.function.Consumer consumer) {
        if (this.mLocal != null) {
            this.mLocal.onScrollCaptureImageRequest(this.mSession, this.mCancellation, new android.graphics.Rect(rect), consumer);
        }
    }

    void onImageRequestCompleted(android.graphics.Rect rect) {
        try {
            try {
                this.mRemote.onImageRequestCompleted(0, rect);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Shutting down due to error: ", e);
                close();
            }
            android.os.Trace.asyncTraceForTrackEnd(2L, TRACE_TRACK, this.mTraceId);
        } finally {
            this.mCancellation = null;
        }
    }

    @Override // android.view.IScrollCaptureConnection
    public android.os.ICancellationSignal endCapture() throws android.os.RemoteException {
        android.os.Trace.asyncTraceForTrackBegin(2L, TRACE_TRACK, END_CAPTURE, this.mTraceId);
        checkActive();
        cancelPendingAction();
        android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
        this.mCancellation = android.os.CancellationSignal.fromTransport(createTransport);
        final java.lang.Runnable create = android.view.ScrollCaptureConnection.SafeCallback.create(this.mCancellation, this.mUiThread, new java.lang.Runnable() { // from class: android.view.ScrollCaptureConnection$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.view.ScrollCaptureConnection.this.onEndCaptureCompleted();
            }
        });
        this.mUiThread.execute(new java.lang.Runnable() { // from class: android.view.ScrollCaptureConnection$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                android.view.ScrollCaptureConnection.this.lambda$endCapture$2(create);
            }
        });
        return createTransport;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$endCapture$2(java.lang.Runnable runnable) {
        if (this.mLocal != null) {
            this.mLocal.onScrollCaptureEnd(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onEndCaptureCompleted() {
        this.mActive = false;
        try {
            try {
                if (this.mRemote != null) {
                    this.mRemote.onCaptureEnded();
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Caught exception confirming capture end!", e);
            }
            android.os.Trace.asyncTraceForTrackEnd(2L, TRACE_TRACK, this.mTraceId);
            android.os.Trace.asyncTraceForTrackEnd(2L, TRACE_TRACK, this.mTraceId);
        } finally {
            this.mCancellation = null;
            close();
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        android.os.Trace.instantForTrack(2L, TRACE_TRACK, "binderDied");
        android.util.Log.e(TAG, "Controlling process just died.");
        close();
    }

    @Override // android.view.IScrollCaptureConnection
    public synchronized void close() {
        android.os.Trace.instantForTrack(2L, TRACE_TRACK, "close");
        if (this.mActive) {
            android.util.Log.w(TAG, "close(): capture session still active! Ending now.");
            cancelPendingAction();
            final android.view.ScrollCaptureCallback scrollCaptureCallback = this.mLocal;
            this.mUiThread.execute(new java.lang.Runnable() { // from class: android.view.ScrollCaptureConnection$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.ScrollCaptureCallback.this.onScrollCaptureEnd(new java.lang.Runnable() { // from class: android.view.ScrollCaptureConnection$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.view.ScrollCaptureConnection.lambda$close$3();
                        }
                    });
                }
            });
            this.mActive = false;
        }
        if (this.mRemote != null) {
            this.mRemote.asBinder().unlinkToDeath(this, 0);
        }
        this.mActive = false;
        this.mConnected = false;
        this.mSession = null;
        this.mRemote = null;
        this.mLocal = null;
        this.mCloseGuard.close();
        android.os.Trace.endSection();
        java.lang.ref.Reference.reachabilityFence(this);
    }

    static /* synthetic */ void lambda$close$3() {
    }

    private void cancelPendingAction() {
        if (this.mCancellation != null) {
            android.os.Trace.instantForTrack(2L, TRACE_TRACK, "CancellationSignal.cancel");
            android.util.Log.w(TAG, "cancelling pending operation.");
            this.mCancellation.cancel();
            this.mCancellation = null;
        }
    }

    public boolean isConnected() {
        return this.mConnected;
    }

    public boolean isActive() {
        return this.mActive;
    }

    private void checkActive() throws android.os.RemoteException {
        synchronized (this.mLock) {
            if (!this.mActive) {
                throw new android.os.RemoteException(new java.lang.IllegalStateException("Not started!"));
            }
        }
    }

    public java.lang.String toString() {
        return "ScrollCaptureConnection{active=" + this.mActive + ", session=" + this.mSession + ", remote=" + this.mRemote + ", local=" + this.mLocal + "}";
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            this.mCloseGuard.warnIfOpen();
            close();
        } finally {
            super.finalize();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SafeCallback<T> {
        private final java.util.concurrent.Executor mExecutor;
        private final android.os.CancellationSignal mSignal;
        private final java.util.concurrent.atomic.AtomicReference<T> mValue;

        protected SafeCallback(android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, T t) {
            this.mSignal = cancellationSignal;
            this.mValue = new java.util.concurrent.atomic.AtomicReference<>(t);
            this.mExecutor = executor;
        }

        protected final void maybeAccept(final java.util.function.Consumer<T> consumer) {
            final T andSet = this.mValue.getAndSet(null);
            if (!this.mSignal.isCanceled() && andSet != null) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.view.ScrollCaptureConnection$SafeCallback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(andSet);
                    }
                });
            }
        }

        static java.lang.Runnable create(android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, java.lang.Runnable runnable) {
            return new android.view.ScrollCaptureConnection.RunnableCallback(cancellationSignal, executor, runnable);
        }

        static <T> java.util.function.Consumer<T> create(android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, java.util.function.Consumer<T> consumer) {
            return new android.view.ScrollCaptureConnection.ConsumerCallback(cancellationSignal, executor, consumer);
        }
    }

    private static final class RunnableCallback extends android.view.ScrollCaptureConnection.SafeCallback<java.lang.Runnable> implements java.lang.Runnable {
        RunnableCallback(android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, java.lang.Runnable runnable) {
            super(cancellationSignal, executor, runnable);
        }

        @Override // java.lang.Runnable
        public void run() {
            maybeAccept(new android.telephony.BinderCacheManager$BinderDeathTracker$$ExternalSyntheticLambda0());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ConsumerCallback<T> extends android.view.ScrollCaptureConnection.SafeCallback<java.util.function.Consumer<T>> implements java.util.function.Consumer<T> {
        ConsumerCallback(android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, java.util.function.Consumer<T> consumer) {
            super(cancellationSignal, executor, consumer);
        }

        @Override // java.util.function.Consumer
        public void accept(final T t) {
            maybeAccept(new java.util.function.Consumer() { // from class: android.view.ScrollCaptureConnection$ConsumerCallback$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((java.util.function.Consumer) obj).accept(t);
                }
            });
        }
    }
}
