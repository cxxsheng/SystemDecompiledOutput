package com.android.server.wm;

/* loaded from: classes3.dex */
class SurfaceAnimationRunner {
    private static final java.lang.String TAG = com.android.server.wm.SurfaceAnimationRunner.class.getSimpleName();
    private final android.animation.AnimationHandler mAnimationHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mAnimationStartDeferred;
    private final android.os.Handler mAnimationThreadHandler;
    private final com.android.server.wm.SurfaceAnimationRunner.AnimatorFactory mAnimatorFactory;
    private boolean mApplyScheduled;
    private final java.lang.Runnable mApplyTransactionRunnable;
    private final java.lang.Object mCancelLock;

    @com.android.internal.annotations.VisibleForTesting
    android.view.Choreographer mChoreographer;
    private final java.util.concurrent.ExecutorService mEdgeExtensionExecutor;
    private final java.lang.Object mEdgeExtensionLock;

    @com.android.internal.annotations.GuardedBy({"mEdgeExtensionLock"})
    private final android.util.ArrayMap<android.view.SurfaceControl, java.util.ArrayList<android.view.SurfaceControl>> mEdgeExtensions;
    private final android.view.SurfaceControl.Transaction mFrameTransaction;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    final android.util.ArrayMap<android.view.SurfaceControl, com.android.server.wm.SurfaceAnimationRunner.RunningAnimation> mPendingAnimations;
    private final android.os.PowerManagerInternal mPowerManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    final android.util.ArrayMap<android.view.SurfaceControl, com.android.server.wm.SurfaceAnimationRunner.RunningAnimation> mPreProcessingAnimations;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    final android.util.ArrayMap<android.view.SurfaceControl, com.android.server.wm.SurfaceAnimationRunner.RunningAnimation> mRunningAnimations;
    private final android.os.Handler mSurfaceAnimationHandler;

    @com.android.internal.annotations.VisibleForTesting
    interface AnimatorFactory {
        android.animation.ValueAnimator makeAnimator();
    }

    SurfaceAnimationRunner(java.util.function.Supplier<android.view.SurfaceControl.Transaction> supplier, android.os.PowerManagerInternal powerManagerInternal) {
        this(null, null, supplier.get(), powerManagerInternal);
    }

    @com.android.internal.annotations.VisibleForTesting
    SurfaceAnimationRunner(@android.annotation.Nullable android.animation.AnimationHandler.AnimationFrameCallbackProvider animationFrameCallbackProvider, com.android.server.wm.SurfaceAnimationRunner.AnimatorFactory animatorFactory, android.view.SurfaceControl.Transaction transaction, android.os.PowerManagerInternal powerManagerInternal) {
        this.mLock = new java.lang.Object();
        this.mCancelLock = new java.lang.Object();
        this.mEdgeExtensionLock = new java.lang.Object();
        this.mAnimationThreadHandler = com.android.server.AnimationThread.getHandler();
        this.mSurfaceAnimationHandler = com.android.server.wm.SurfaceAnimationThread.getHandler();
        this.mApplyTransactionRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.SurfaceAnimationRunner.this.applyTransaction();
            }
        };
        this.mEdgeExtensionExecutor = java.util.concurrent.Executors.newFixedThreadPool(2);
        this.mPendingAnimations = new android.util.ArrayMap<>();
        this.mPreProcessingAnimations = new android.util.ArrayMap<>();
        this.mRunningAnimations = new android.util.ArrayMap<>();
        this.mEdgeExtensions = new android.util.ArrayMap<>();
        this.mSurfaceAnimationHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.SurfaceAnimationRunner.this.lambda$new$0();
            }
        }, 0L);
        this.mFrameTransaction = transaction;
        this.mAnimationHandler = new android.animation.AnimationHandler();
        this.mAnimationHandler.setProvider(animationFrameCallbackProvider == null ? new com.android.internal.graphics.SfVsyncFrameCallbackProvider(this.mChoreographer) : animationFrameCallbackProvider);
        this.mAnimatorFactory = animatorFactory == null ? new com.android.server.wm.SurfaceAnimationRunner.AnimatorFactory() { // from class: com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda4
            @Override // com.android.server.wm.SurfaceAnimationRunner.AnimatorFactory
            public final android.animation.ValueAnimator makeAnimator() {
                android.animation.ValueAnimator lambda$new$1;
                lambda$new$1 = com.android.server.wm.SurfaceAnimationRunner.this.lambda$new$1();
                return lambda$new$1;
            }
        } : animatorFactory;
        this.mPowerManagerInternal = powerManagerInternal;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mChoreographer = android.view.Choreographer.getSfInstance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.animation.ValueAnimator lambda$new$1() {
        return new com.android.server.wm.SurfaceAnimationRunner.SfValueAnimator();
    }

    void deferStartingAnimations() {
        synchronized (this.mLock) {
            this.mAnimationStartDeferred = true;
        }
    }

    void continueStartingAnimations() {
        synchronized (this.mLock) {
            try {
                this.mAnimationStartDeferred = false;
                if (!this.mPendingAnimations.isEmpty() && this.mPreProcessingAnimations.isEmpty()) {
                    this.mChoreographer.postFrameCallback(new com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda1(this));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void startAnimation(final com.android.server.wm.LocalAnimationAdapter.AnimationSpec animationSpec, final android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction, java.lang.Runnable runnable) {
        synchronized (this.mLock) {
            final com.android.server.wm.SurfaceAnimationRunner.RunningAnimation runningAnimation = new com.android.server.wm.SurfaceAnimationRunner.RunningAnimation(animationSpec, surfaceControl, runnable);
            boolean requiresEdgeExtension = requiresEdgeExtension(animationSpec);
            if (requiresEdgeExtension) {
                java.util.ArrayList<android.view.SurfaceControl> arrayList = new java.util.ArrayList<>();
                synchronized (this.mEdgeExtensionLock) {
                    this.mEdgeExtensions.put(surfaceControl, arrayList);
                }
                this.mPreProcessingAnimations.put(surfaceControl, runningAnimation);
                transaction.addTransactionCommittedListener(this.mEdgeExtensionExecutor, new android.view.SurfaceControl.TransactionCommittedListener() { // from class: com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda0
                    @Override // android.view.SurfaceControl.TransactionCommittedListener
                    public final void onTransactionCommitted() {
                        com.android.server.wm.SurfaceAnimationRunner.this.lambda$startAnimation$2(surfaceControl, animationSpec, runningAnimation);
                    }
                });
            }
            if (!requiresEdgeExtension) {
                this.mPendingAnimations.put(surfaceControl, runningAnimation);
                if (!this.mAnimationStartDeferred && this.mPreProcessingAnimations.isEmpty()) {
                    this.mChoreographer.postFrameCallback(new com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda1(this));
                }
            }
            applyTransformation(runningAnimation, transaction, 0L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAnimation$2(android.view.SurfaceControl surfaceControl, com.android.server.wm.LocalAnimationAdapter.AnimationSpec animationSpec, com.android.server.wm.SurfaceAnimationRunner.RunningAnimation runningAnimation) {
        if (!surfaceControl.isValid()) {
            android.util.Log.e(TAG, "Animation leash is not valid");
            synchronized (this.mEdgeExtensionLock) {
                this.mEdgeExtensions.remove(surfaceControl);
            }
            synchronized (this.mLock) {
                this.mPreProcessingAnimations.remove(surfaceControl);
            }
            return;
        }
        com.android.server.wm.WindowAnimationSpec asWindowAnimationSpec = animationSpec.asWindowAnimationSpec();
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        edgeExtendWindow(surfaceControl, asWindowAnimationSpec.getRootTaskBounds(), asWindowAnimationSpec.getAnimation(), transaction);
        synchronized (this.mLock) {
            if (this.mPreProcessingAnimations.get(surfaceControl) == runningAnimation) {
                synchronized (this.mEdgeExtensionLock) {
                    try {
                        if (!this.mEdgeExtensions.isEmpty()) {
                            transaction.apply();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                this.mPreProcessingAnimations.remove(surfaceControl);
                this.mPendingAnimations.put(surfaceControl, runningAnimation);
                if (!this.mAnimationStartDeferred && this.mPreProcessingAnimations.isEmpty()) {
                    this.mChoreographer.postFrameCallback(new com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda1(this));
                }
            }
        }
    }

    private boolean requiresEdgeExtension(com.android.server.wm.LocalAnimationAdapter.AnimationSpec animationSpec) {
        return animationSpec.asWindowAnimationSpec() != null && animationSpec.asWindowAnimationSpec().hasExtension();
    }

    void onAnimationCancelled(android.view.SurfaceControl surfaceControl) {
        synchronized (this.mLock) {
            try {
                if (this.mPendingAnimations.containsKey(surfaceControl)) {
                    this.mPendingAnimations.remove(surfaceControl);
                    return;
                }
                if (this.mPreProcessingAnimations.containsKey(surfaceControl)) {
                    this.mPreProcessingAnimations.remove(surfaceControl);
                    return;
                }
                final com.android.server.wm.SurfaceAnimationRunner.RunningAnimation runningAnimation = this.mRunningAnimations.get(surfaceControl);
                if (runningAnimation != null) {
                    this.mRunningAnimations.remove(surfaceControl);
                    synchronized (this.mCancelLock) {
                        runningAnimation.mCancelled = true;
                    }
                    this.mSurfaceAnimationHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.wm.SurfaceAnimationRunner.this.lambda$onAnimationCancelled$3(runningAnimation);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAnimationCancelled$3(com.android.server.wm.SurfaceAnimationRunner.RunningAnimation runningAnimation) {
        runningAnimation.mAnim.cancel();
        applyTransaction();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void startPendingAnimationsLocked() {
        for (int size = this.mPendingAnimations.size() - 1; size >= 0; size--) {
            startAnimationLocked(this.mPendingAnimations.valueAt(size));
        }
        this.mPendingAnimations.clear();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void startAnimationLocked(final com.android.server.wm.SurfaceAnimationRunner.RunningAnimation runningAnimation) {
        final android.animation.ValueAnimator makeAnimator = this.mAnimatorFactory.makeAnimator();
        makeAnimator.overrideDurationScale(1.0f);
        makeAnimator.setDuration(runningAnimation.mAnimSpec.getDuration());
        makeAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.server.wm.SurfaceAnimationRunner$$ExternalSyntheticLambda5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                com.android.server.wm.SurfaceAnimationRunner.this.lambda$startAnimationLocked$4(runningAnimation, makeAnimator, valueAnimator);
            }
        });
        makeAnimator.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.server.wm.SurfaceAnimationRunner.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(android.animation.Animator animator) {
                synchronized (com.android.server.wm.SurfaceAnimationRunner.this.mCancelLock) {
                    try {
                        if (!runningAnimation.mCancelled) {
                            com.android.server.wm.SurfaceAnimationRunner.this.mFrameTransaction.setAlpha(runningAnimation.mLeash, 1.0f);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                synchronized (com.android.server.wm.SurfaceAnimationRunner.this.mLock) {
                    com.android.server.wm.SurfaceAnimationRunner.this.mRunningAnimations.remove(runningAnimation.mLeash);
                    synchronized (com.android.server.wm.SurfaceAnimationRunner.this.mCancelLock) {
                        try {
                            if (!runningAnimation.mCancelled) {
                                com.android.server.wm.SurfaceAnimationRunner.this.mAnimationThreadHandler.post(runningAnimation.mFinishCallback);
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }
            }
        });
        runningAnimation.mAnim = makeAnimator;
        this.mRunningAnimations.put(runningAnimation.mLeash, runningAnimation);
        makeAnimator.start();
        if (runningAnimation.mAnimSpec.canSkipFirstFrame()) {
            makeAnimator.setCurrentPlayTime(this.mChoreographer.getFrameIntervalNanos() / 1000000);
        }
        makeAnimator.doAnimationFrame(this.mChoreographer.getFrameTime());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAnimationLocked$4(com.android.server.wm.SurfaceAnimationRunner.RunningAnimation runningAnimation, android.animation.ValueAnimator valueAnimator, android.animation.ValueAnimator valueAnimator2) {
        synchronized (this.mCancelLock) {
            try {
                if (!runningAnimation.mCancelled) {
                    long duration = valueAnimator.getDuration();
                    long currentPlayTime = valueAnimator.getCurrentPlayTime();
                    if (currentPlayTime <= duration) {
                        duration = currentPlayTime;
                    }
                    applyTransformation(runningAnimation, this.mFrameTransaction, duration);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        scheduleApplyTransaction();
    }

    private void applyTransformation(com.android.server.wm.SurfaceAnimationRunner.RunningAnimation runningAnimation, android.view.SurfaceControl.Transaction transaction, long j) {
        runningAnimation.mAnimSpec.apply(transaction, runningAnimation.mLeash, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAnimations(long j) {
        synchronized (this.mLock) {
            try {
                if (this.mPreProcessingAnimations.isEmpty()) {
                    startPendingAnimationsLocked();
                    this.mPowerManagerInternal.setPowerBoost(0, 0);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void scheduleApplyTransaction() {
        if (!this.mApplyScheduled) {
            this.mChoreographer.postCallback(3, this.mApplyTransactionRunnable, null);
            this.mApplyScheduled = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyTransaction() {
        this.mFrameTransaction.setAnimationTransaction();
        this.mFrameTransaction.setFrameTimelineVsync(this.mChoreographer.getVsyncId());
        this.mFrameTransaction.apply();
        this.mApplyScheduled = false;
    }

    private void edgeExtendWindow(android.view.SurfaceControl surfaceControl, android.graphics.Rect rect, android.view.animation.Animation animation, android.view.SurfaceControl.Transaction transaction) {
        android.view.animation.Transformation transformation = new android.view.animation.Transformation();
        animation.getTransformationAt(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, transformation);
        android.view.animation.Transformation transformation2 = new android.view.animation.Transformation();
        animation.getTransformationAt(1.0f, transformation2);
        android.graphics.Insets min = android.graphics.Insets.min(transformation.getInsets(), transformation2.getInsets());
        int height = rect.height();
        int width = rect.width();
        if (min.left < 0) {
            createExtensionSurface(surfaceControl, new android.graphics.Rect(rect.left, rect.top, rect.left + 1, rect.bottom), new android.graphics.Rect(0, 0, -min.left, height), rect.left + min.left, rect.top, "Left Edge Extension", transaction);
        }
        if (min.top < 0) {
            createExtensionSurface(surfaceControl, new android.graphics.Rect(rect.left, rect.top, width, rect.top + 1), new android.graphics.Rect(0, 0, width, -min.top), rect.left, rect.top + min.top, "Top Edge Extension", transaction);
        }
        if (min.right < 0) {
            createExtensionSurface(surfaceControl, new android.graphics.Rect(rect.right - 1, rect.top, rect.right, rect.bottom), new android.graphics.Rect(0, 0, -min.right, height), rect.right, rect.top, "Right Edge Extension", transaction);
        }
        if (min.bottom < 0) {
            createExtensionSurface(surfaceControl, new android.graphics.Rect(rect.left, rect.bottom - 1, rect.right, rect.bottom), new android.graphics.Rect(0, 0, width, -min.bottom), rect.left, rect.bottom, "Bottom Edge Extension", transaction);
        }
    }

    private void createExtensionSurface(android.view.SurfaceControl surfaceControl, android.graphics.Rect rect, android.graphics.Rect rect2, int i, int i2, java.lang.String str, android.view.SurfaceControl.Transaction transaction) {
        android.os.Trace.traceBegin(32L, "createExtensionSurface");
        doCreateExtensionSurface(surfaceControl, rect, rect2, i, i2, str, transaction);
        android.os.Trace.traceEnd(32L);
    }

    private void doCreateExtensionSurface(android.view.SurfaceControl surfaceControl, android.graphics.Rect rect, android.graphics.Rect rect2, int i, int i2, java.lang.String str, android.view.SurfaceControl.Transaction transaction) {
        android.window.ScreenCapture.ScreenshotHardwareBuffer captureLayers = android.window.ScreenCapture.captureLayers(new android.window.ScreenCapture.LayerCaptureArgs.Builder(surfaceControl).setSourceCrop(rect).setFrameScale(1.0f).setPixelFormat(1).setChildrenOnly(true).setAllowProtected(true).setCaptureSecureLayers(true).build());
        if (captureLayers == null) {
            android.util.Log.e(TAG, "Failed to create edge extension - edge buffer is null");
            return;
        }
        android.view.SurfaceControl build = new android.view.SurfaceControl.Builder().setName(str).setHidden(true).setCallsite("DefaultTransitionHandler#startAnimation").setOpaque(true).setBufferSize(rect2.width(), rect2.height()).build();
        android.graphics.BitmapShader bitmapShader = new android.graphics.BitmapShader(captureLayers.asBitmap(), android.graphics.Shader.TileMode.CLAMP, android.graphics.Shader.TileMode.CLAMP);
        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setShader(bitmapShader);
        android.view.Surface surface = new android.view.Surface(build);
        android.graphics.Canvas lockHardwareCanvas = surface.lockHardwareCanvas();
        lockHardwareCanvas.drawRect(rect2, paint);
        surface.unlockCanvasAndPost(lockHardwareCanvas);
        surface.release();
        synchronized (this.mEdgeExtensionLock) {
            try {
                if (!this.mEdgeExtensions.containsKey(surfaceControl)) {
                    transaction.remove(build);
                    return;
                }
                transaction.reparent(build, surfaceControl);
                transaction.setLayer(build, Integer.MIN_VALUE);
                transaction.setPosition(build, i, i2);
                transaction.setVisibility(build, true);
                this.mEdgeExtensions.get(surfaceControl).add(build);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private float getScaleXForExtensionSurface(android.graphics.Rect rect, android.graphics.Rect rect2) {
        if (rect.width() == rect2.width()) {
            return 1.0f;
        }
        if (rect.width() == 1) {
            return rect2.width();
        }
        throw new java.lang.RuntimeException("Unexpected edgeBounds and extensionRect widths");
    }

    private float getScaleYForExtensionSurface(android.graphics.Rect rect, android.graphics.Rect rect2) {
        if (rect.height() == rect2.height()) {
            return 1.0f;
        }
        if (rect.height() == 1) {
            return rect2.height();
        }
        throw new java.lang.RuntimeException("Unexpected edgeBounds and extensionRect heights");
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class RunningAnimation {
        android.animation.ValueAnimator mAnim;
        final com.android.server.wm.LocalAnimationAdapter.AnimationSpec mAnimSpec;

        @com.android.internal.annotations.GuardedBy({"mCancelLock"})
        private boolean mCancelled;
        final java.lang.Runnable mFinishCallback;
        final android.view.SurfaceControl mLeash;

        RunningAnimation(com.android.server.wm.LocalAnimationAdapter.AnimationSpec animationSpec, android.view.SurfaceControl surfaceControl, java.lang.Runnable runnable) {
            this.mAnimSpec = animationSpec;
            this.mLeash = surfaceControl;
            this.mFinishCallback = runnable;
        }
    }

    protected void onAnimationLeashLost(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.Transaction transaction) {
        synchronized (this.mEdgeExtensionLock) {
            try {
                if (this.mEdgeExtensions.containsKey(surfaceControl)) {
                    java.util.ArrayList<android.view.SurfaceControl> arrayList = this.mEdgeExtensions.get(surfaceControl);
                    for (int i = 0; i < arrayList.size(); i++) {
                        transaction.remove(arrayList.get(i));
                    }
                    this.mEdgeExtensions.remove(surfaceControl);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private class SfValueAnimator extends android.animation.ValueAnimator {
        SfValueAnimator() {
            setFloatValues(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f);
        }

        public android.animation.AnimationHandler getAnimationHandler() {
            return com.android.server.wm.SurfaceAnimationRunner.this.mAnimationHandler;
        }
    }
}
