package android.view;

/* loaded from: classes4.dex */
public class InsetsAnimationThreadControlRunner implements android.view.InsetsAnimationControlRunner {
    private static final java.lang.String TAG = "InsetsAnimThreadRunner";
    private final android.view.InsetsAnimationControlCallbacks mCallbacks = new android.view.InsetsAnimationThreadControlRunner.AnonymousClass1();
    private final android.view.InsetsAnimationControlImpl mControl;
    private final android.os.Handler mMainThreadHandler;
    private final android.view.InsetsAnimationControlCallbacks mOuterCallbacks;

    /* renamed from: android.view.InsetsAnimationThreadControlRunner$1, reason: invalid class name */
    class AnonymousClass1 implements android.view.InsetsAnimationControlCallbacks {
        private final float[] mTmpFloat9 = new float[9];

        AnonymousClass1() {
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public <T extends android.view.InsetsAnimationControlRunner & android.view.InternalInsetsAnimationController> void startAnimation(T t, android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, int i, android.view.WindowInsetsAnimation windowInsetsAnimation, android.view.WindowInsetsAnimation.Bounds bounds) {
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public void scheduleApplyChangeInsets(android.view.InsetsAnimationControlRunner insetsAnimationControlRunner) {
            synchronized (android.view.InsetsAnimationThreadControlRunner.this.mControl) {
                android.view.InsetsAnimationThreadControlRunner.this.mControl.applyChangeInsets(null);
            }
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public void notifyFinished(android.view.InsetsAnimationControlRunner insetsAnimationControlRunner, final boolean z) {
            android.os.Trace.asyncTraceEnd(8L, "InsetsAsyncAnimation: " + android.view.WindowInsets.Type.toString(insetsAnimationControlRunner.getTypes()), insetsAnimationControlRunner.getTypes());
            android.view.InsetsController.releaseControls(android.view.InsetsAnimationThreadControlRunner.this.mControl.getControls());
            android.view.InsetsAnimationThreadControlRunner.this.mMainThreadHandler.post(new java.lang.Runnable() { // from class: android.view.InsetsAnimationThreadControlRunner$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.InsetsAnimationThreadControlRunner.AnonymousClass1.this.lambda$notifyFinished$0(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyFinished$0(boolean z) {
            android.view.InsetsAnimationThreadControlRunner.this.mOuterCallbacks.notifyFinished(android.view.InsetsAnimationThreadControlRunner.this, z);
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public void applySurfaceParams(android.view.SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr) {
            android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
            for (int length = surfaceParamsArr.length - 1; length >= 0; length--) {
                android.view.SyncRtSurfaceTransactionApplier.applyParams(transaction, surfaceParamsArr[length], this.mTmpFloat9);
            }
            transaction.setFrameTimelineVsync(android.view.Choreographer.getSfInstance().getVsyncId());
            transaction.apply();
            transaction.close();
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public void releaseSurfaceControlFromRt(android.view.SurfaceControl surfaceControl) {
            surfaceControl.release();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$reportPerceptible$1(int i, boolean z) {
            android.view.InsetsAnimationThreadControlRunner.this.mOuterCallbacks.reportPerceptible(i, z);
        }

        @Override // android.view.InsetsAnimationControlCallbacks
        public void reportPerceptible(final int i, final boolean z) {
            android.view.InsetsAnimationThreadControlRunner.this.mMainThreadHandler.post(new java.lang.Runnable() { // from class: android.view.InsetsAnimationThreadControlRunner$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.InsetsAnimationThreadControlRunner.AnonymousClass1.this.lambda$reportPerceptible$1(i, z);
                }
            });
        }
    }

    public InsetsAnimationThreadControlRunner(android.util.SparseArray<android.view.InsetsSourceControl> sparseArray, android.graphics.Rect rect, android.view.InsetsState insetsState, final android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, final int i, android.view.InsetsAnimationControlCallbacks insetsAnimationControlCallbacks, long j, android.view.animation.Interpolator interpolator, int i2, int i3, android.content.res.CompatibilityInfo.Translator translator, android.os.Handler handler, android.view.inputmethod.ImeTracker.Token token) {
        this.mMainThreadHandler = handler;
        this.mOuterCallbacks = insetsAnimationControlCallbacks;
        this.mControl = new android.view.InsetsAnimationControlImpl(sparseArray, rect, insetsState, windowInsetsAnimationControlListener, i, this.mCallbacks, j, interpolator, i2, i3, translator, token);
        android.view.InsetsAnimationThread.getHandler().post(new java.lang.Runnable() { // from class: android.view.InsetsAnimationThreadControlRunner$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.view.InsetsAnimationThreadControlRunner.this.lambda$new$0(i, windowInsetsAnimationControlListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        if (this.mControl.isCancelled()) {
            return;
        }
        android.os.Trace.asyncTraceBegin(8L, "InsetsAsyncAnimation: " + android.view.WindowInsets.Type.toString(i), i);
        windowInsetsAnimationControlListener.onReady(this.mControl, i);
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        this.mControl.dumpDebug(protoOutputStream, j);
    }

    @Override // android.view.InsetsAnimationControlRunner
    public android.view.inputmethod.ImeTracker.Token getStatsToken() {
        return this.mControl.getStatsToken();
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getTypes() {
        return this.mControl.getTypes();
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getControllingTypes() {
        return this.mControl.getControllingTypes();
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void notifyControlRevoked(int i) {
        this.mControl.notifyControlRevoked(i);
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateSurfacePosition(android.util.SparseArray<android.view.InsetsSourceControl> sparseArray) {
        synchronized (this.mControl) {
            this.mControl.updateSurfacePosition(sparseArray);
        }
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void cancel() {
        android.os.Handler handler = android.view.InsetsAnimationThread.getHandler();
        final android.view.InsetsAnimationControlImpl insetsAnimationControlImpl = this.mControl;
        java.util.Objects.requireNonNull(insetsAnimationControlImpl);
        handler.post(new java.lang.Runnable() { // from class: android.view.InsetsAnimationThreadControlRunner$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.view.InsetsAnimationControlImpl.this.cancel();
            }
        });
    }

    @Override // android.view.InsetsAnimationControlRunner
    public android.view.WindowInsetsAnimation getAnimation() {
        return this.mControl.getAnimation();
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getAnimationType() {
        return this.mControl.getAnimationType();
    }
}
