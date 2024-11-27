package com.android.server.wm;

/* loaded from: classes3.dex */
public class LegacyDimmer extends com.android.server.wm.Dimmer {
    private static final int DEFAULT_DIM_ANIM_DURATION = 200;
    private static final java.lang.String TAG = "WindowManager";
    com.android.server.wm.LegacyDimmer.DimState mDimState;
    private com.android.server.wm.WindowContainer mLastRequestedDimContainer;
    private final com.android.server.wm.LegacyDimmer.SurfaceAnimatorStarter mSurfaceAnimatorStarter;

    @com.android.internal.annotations.VisibleForTesting
    interface SurfaceAnimatorStarter {
        void startAnimation(com.android.server.wm.SurfaceAnimator surfaceAnimator, android.view.SurfaceControl.Transaction transaction, com.android.server.wm.AnimationAdapter animationAdapter, boolean z, int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DimAnimatable implements com.android.server.wm.SurfaceAnimator.Animatable {
        private android.view.SurfaceControl mDimLayer;

        private DimAnimatable(android.view.SurfaceControl surfaceControl) {
            this.mDimLayer = surfaceControl;
        }

        @Override // com.android.server.wm.SurfaceAnimator.Animatable
        public android.view.SurfaceControl.Transaction getSyncTransaction() {
            return com.android.server.wm.LegacyDimmer.this.mHost.getSyncTransaction();
        }

        @Override // com.android.server.wm.SurfaceAnimator.Animatable
        public android.view.SurfaceControl.Transaction getPendingTransaction() {
            return com.android.server.wm.LegacyDimmer.this.mHost.getPendingTransaction();
        }

        @Override // com.android.server.wm.SurfaceAnimator.Animatable
        public void commitPendingTransaction() {
            com.android.server.wm.LegacyDimmer.this.mHost.commitPendingTransaction();
        }

        @Override // com.android.server.wm.SurfaceAnimator.Animatable
        public void onAnimationLeashCreated(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        }

        @Override // com.android.server.wm.SurfaceAnimator.Animatable
        public void onAnimationLeashLost(android.view.SurfaceControl.Transaction transaction) {
        }

        @Override // com.android.server.wm.SurfaceAnimator.Animatable
        public android.view.SurfaceControl.Builder makeAnimationLeash() {
            return com.android.server.wm.LegacyDimmer.this.mHost.makeAnimationLeash();
        }

        @Override // com.android.server.wm.SurfaceAnimator.Animatable
        public android.view.SurfaceControl getAnimationLeashParent() {
            return com.android.server.wm.LegacyDimmer.this.mHost.getSurfaceControl();
        }

        @Override // com.android.server.wm.SurfaceAnimator.Animatable
        public android.view.SurfaceControl getSurfaceControl() {
            return this.mDimLayer;
        }

        @Override // com.android.server.wm.SurfaceAnimator.Animatable
        public android.view.SurfaceControl getParentSurfaceControl() {
            return com.android.server.wm.LegacyDimmer.this.mHost.getSurfaceControl();
        }

        @Override // com.android.server.wm.SurfaceAnimator.Animatable
        public int getSurfaceWidth() {
            return com.android.server.wm.LegacyDimmer.this.mHost.getSurfaceWidth();
        }

        @Override // com.android.server.wm.SurfaceAnimator.Animatable
        public int getSurfaceHeight() {
            return com.android.server.wm.LegacyDimmer.this.mHost.getSurfaceHeight();
        }

        void removeSurface() {
            if (this.mDimLayer != null && this.mDimLayer.isValid()) {
                getSyncTransaction().remove(this.mDimLayer);
            }
            this.mDimLayer = null;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class DimState {
        android.view.SurfaceControl mDimLayer;
        boolean mDontReset;
        boolean mIsVisible;
        com.android.server.wm.SurfaceAnimator mSurfaceAnimator;
        final android.graphics.Rect mDimBounds = new android.graphics.Rect();
        boolean mAnimateExit = true;
        boolean mDimming = true;

        DimState(android.view.SurfaceControl surfaceControl) {
            this.mDimLayer = surfaceControl;
            final com.android.server.wm.LegacyDimmer.DimAnimatable dimAnimatable = new com.android.server.wm.LegacyDimmer.DimAnimatable(surfaceControl);
            this.mSurfaceAnimator = new com.android.server.wm.SurfaceAnimator(dimAnimatable, new com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.LegacyDimmer$DimState$$ExternalSyntheticLambda0
                @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                public final void onAnimationFinished(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
                    com.android.server.wm.LegacyDimmer.DimState.this.lambda$new$0(dimAnimatable, i, animationAdapter);
                }
            }, com.android.server.wm.LegacyDimmer.this.mHost.mWmService);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(com.android.server.wm.LegacyDimmer.DimAnimatable dimAnimatable, int i, com.android.server.wm.AnimationAdapter animationAdapter) {
            if (!this.mDimming) {
                dimAnimatable.removeSurface();
            }
        }
    }

    protected LegacyDimmer(com.android.server.wm.WindowContainer windowContainer) {
        this(windowContainer, new com.android.server.wm.LegacyDimmer.SurfaceAnimatorStarter() { // from class: com.android.server.wm.LegacyDimmer$$ExternalSyntheticLambda0
            @Override // com.android.server.wm.LegacyDimmer.SurfaceAnimatorStarter
            public final void startAnimation(com.android.server.wm.SurfaceAnimator surfaceAnimator, android.view.SurfaceControl.Transaction transaction, com.android.server.wm.AnimationAdapter animationAdapter, boolean z, int i) {
                surfaceAnimator.startAnimation(transaction, animationAdapter, z, i);
            }
        });
    }

    LegacyDimmer(com.android.server.wm.WindowContainer windowContainer, com.android.server.wm.LegacyDimmer.SurfaceAnimatorStarter surfaceAnimatorStarter) {
        super(windowContainer);
        this.mSurfaceAnimatorStarter = surfaceAnimatorStarter;
    }

    private com.android.server.wm.LegacyDimmer.DimState obtainDimState(com.android.server.wm.WindowContainer windowContainer) {
        if (this.mDimState == null) {
            try {
                this.mDimState = new com.android.server.wm.LegacyDimmer.DimState(makeDimLayer());
            } catch (android.view.Surface.OutOfResourcesException e) {
                android.util.Log.w(TAG, "OutOfResourcesException creating dim surface");
            }
        }
        this.mLastRequestedDimContainer = windowContainer;
        return this.mDimState;
    }

    private android.view.SurfaceControl makeDimLayer() {
        return this.mHost.makeChildSurface(null).setParent(this.mHost.getSurfaceControl()).setColorLayer().setName("Dim Layer for - " + this.mHost.getName()).setCallsite("Dimmer.makeDimLayer").build();
    }

    @Override // com.android.server.wm.Dimmer
    android.view.SurfaceControl getDimLayer() {
        if (this.mDimState != null) {
            return this.mDimState.mDimLayer;
        }
        return null;
    }

    @Override // com.android.server.wm.Dimmer
    void resetDimStates() {
        if (this.mDimState != null && !this.mDimState.mDontReset) {
            this.mDimState.mDimming = false;
        }
    }

    @Override // com.android.server.wm.Dimmer
    android.graphics.Rect getDimBounds() {
        if (this.mDimState != null) {
            return this.mDimState.mDimBounds;
        }
        return null;
    }

    @Override // com.android.server.wm.Dimmer
    void dontAnimateExit() {
        if (this.mDimState != null) {
            this.mDimState.mAnimateExit = false;
        }
    }

    @Override // com.android.server.wm.Dimmer
    protected void adjustAppearance(com.android.server.wm.WindowContainer windowContainer, float f, int i) {
        com.android.server.wm.LegacyDimmer.DimState obtainDimState = obtainDimState(windowContainer);
        if (obtainDimState == null) {
            return;
        }
        android.view.SurfaceControl.Transaction pendingTransaction = this.mHost.getPendingTransaction();
        pendingTransaction.setAlpha(obtainDimState.mDimLayer, f);
        pendingTransaction.setBackgroundBlurRadius(obtainDimState.mDimLayer, i);
        obtainDimState.mDimming = true;
    }

    @Override // com.android.server.wm.Dimmer
    protected void adjustRelativeLayer(com.android.server.wm.WindowContainer windowContainer, int i) {
        com.android.server.wm.LegacyDimmer.DimState dimState = this.mDimState;
        if (dimState != null) {
            this.mHost.getPendingTransaction().setRelativeLayer(dimState.mDimLayer, windowContainer.getSurfaceControl(), i);
        }
    }

    @Override // com.android.server.wm.Dimmer
    boolean updateDims(android.view.SurfaceControl.Transaction transaction) {
        if (this.mDimState == null) {
            return false;
        }
        if (!this.mDimState.mDimming) {
            if (!this.mDimState.mAnimateExit) {
                if (this.mDimState.mDimLayer.isValid()) {
                    transaction.remove(this.mDimState.mDimLayer);
                }
            } else {
                startDimExit(this.mLastRequestedDimContainer, this.mDimState.mSurfaceAnimator, transaction);
            }
            this.mDimState = null;
            return false;
        }
        android.graphics.Rect rect = this.mDimState.mDimBounds;
        transaction.setPosition(this.mDimState.mDimLayer, rect.left, rect.top);
        transaction.setWindowCrop(this.mDimState.mDimLayer, rect.width(), rect.height());
        if (!this.mDimState.mIsVisible) {
            this.mDimState.mIsVisible = true;
            transaction.show(this.mDimState.mDimLayer);
            com.android.server.wm.WindowState asWindowState = this.mLastRequestedDimContainer.asWindowState();
            if (asWindowState == null || asWindowState.mActivityRecord == null || asWindowState.mActivityRecord.mStartingData == null) {
                startDimEnter(this.mLastRequestedDimContainer, this.mDimState.mSurfaceAnimator, transaction);
            }
        }
        return true;
    }

    private long getDimDuration(com.android.server.wm.WindowContainer windowContainer) {
        com.android.server.wm.AnimationAdapter animation = windowContainer.mSurfaceAnimator.getAnimation();
        return animation == null ? (long) (windowContainer.mWmService.getTransitionAnimationScaleLocked() * 200.0f) : animation.getDurationHint();
    }

    private void startDimEnter(com.android.server.wm.WindowContainer windowContainer, com.android.server.wm.SurfaceAnimator surfaceAnimator, android.view.SurfaceControl.Transaction transaction) {
        startAnim(windowContainer, surfaceAnimator, transaction, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f);
    }

    private void startDimExit(com.android.server.wm.WindowContainer windowContainer, com.android.server.wm.SurfaceAnimator surfaceAnimator, android.view.SurfaceControl.Transaction transaction) {
        startAnim(windowContainer, surfaceAnimator, transaction, 1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
    }

    private void startAnim(com.android.server.wm.WindowContainer windowContainer, com.android.server.wm.SurfaceAnimator surfaceAnimator, android.view.SurfaceControl.Transaction transaction, float f, float f2) {
        this.mSurfaceAnimatorStarter.startAnimation(surfaceAnimator, transaction, new com.android.server.wm.LocalAnimationAdapter(new com.android.server.wm.LegacyDimmer.AlphaAnimationSpec(f, f2, getDimDuration(windowContainer)), this.mHost.mWmService.mSurfaceAnimationRunner), false, 4);
    }

    private static class AlphaAnimationSpec implements com.android.server.wm.LocalAnimationAdapter.AnimationSpec {
        private final long mDuration;
        private final float mFromAlpha;
        private final float mToAlpha;

        AlphaAnimationSpec(float f, float f2, long j) {
            this.mFromAlpha = f;
            this.mToAlpha = f2;
            this.mDuration = j;
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public long getDuration() {
            return this.mDuration;
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public void apply(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, long j) {
            transaction.setAlpha(surfaceControl, (getFraction(j) * (this.mToAlpha - this.mFromAlpha)) + this.mFromAlpha);
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.print("from=");
            printWriter.print(this.mFromAlpha);
            printWriter.print(" to=");
            printWriter.print(this.mToAlpha);
            printWriter.print(" duration=");
            printWriter.println(this.mDuration);
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public void dumpDebugInner(android.util.proto.ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(1146756268035L);
            protoOutputStream.write(1108101562369L, this.mFromAlpha);
            protoOutputStream.write(1108101562370L, this.mToAlpha);
            protoOutputStream.write(1112396529667L, this.mDuration);
            protoOutputStream.end(start);
        }
    }
}
