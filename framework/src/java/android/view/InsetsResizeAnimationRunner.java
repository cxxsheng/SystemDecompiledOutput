package android.view;

/* loaded from: classes4.dex */
public class InsetsResizeAnimationRunner implements android.view.InsetsAnimationControlRunner, android.view.InternalInsetsAnimationController, android.view.WindowInsetsAnimationControlListener {
    private final android.view.WindowInsetsAnimation mAnimation;
    private android.animation.ValueAnimator mAnimator;
    private boolean mCancelled;
    private final android.view.InsetsAnimationControlCallbacks mController;
    private boolean mFinished;
    private final android.view.InsetsState mFromState;
    private final android.view.InsetsState mToState;
    private final int mTypes;

    public InsetsResizeAnimationRunner(android.graphics.Rect rect, android.view.InsetsState insetsState, android.view.InsetsState insetsState2, android.view.animation.Interpolator interpolator, long j, int i, android.view.InsetsAnimationControlCallbacks insetsAnimationControlCallbacks) {
        this.mFromState = insetsState;
        this.mToState = insetsState2;
        this.mTypes = i;
        this.mController = insetsAnimationControlCallbacks;
        this.mAnimation = new android.view.WindowInsetsAnimation(i, interpolator, j);
        this.mAnimation.setAlpha(1.0f);
        android.graphics.Insets calculateInsets = insetsState.calculateInsets(rect, i, false);
        android.graphics.Insets calculateInsets2 = insetsState2.calculateInsets(rect, i, false);
        insetsAnimationControlCallbacks.startAnimation(this, this, i, this.mAnimation, new android.view.WindowInsetsAnimation.Bounds(android.graphics.Insets.min(calculateInsets, calculateInsets2), android.graphics.Insets.max(calculateInsets, calculateInsets2)));
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getTypes() {
        return this.mTypes;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getControllingTypes() {
        return this.mTypes;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public android.view.WindowInsetsAnimation getAnimation() {
        return this.mAnimation;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getAnimationType() {
        return 3;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public android.view.inputmethod.ImeTracker.Token getStatsToken() {
        return null;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void cancel() {
        if (this.mCancelled || this.mFinished) {
            return;
        }
        this.mCancelled = true;
        if (this.mAnimator != null) {
            this.mAnimator.cancel();
        }
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean isCancelled() {
        return this.mCancelled;
    }

    @Override // android.view.WindowInsetsAnimationControlListener
    public void onReady(android.view.WindowInsetsAnimationController windowInsetsAnimationController, int i) {
        if (this.mCancelled) {
            return;
        }
        this.mAnimator = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mAnimator.setDuration(this.mAnimation.getDurationMillis());
        this.mAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.view.InsetsResizeAnimationRunner$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                android.view.InsetsResizeAnimationRunner.this.lambda$onReady$0(valueAnimator);
            }
        });
        this.mAnimator.addListener(new android.animation.AnimatorListenerAdapter() { // from class: android.view.InsetsResizeAnimationRunner.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                android.view.InsetsResizeAnimationRunner.this.mFinished = true;
                android.view.InsetsResizeAnimationRunner.this.mController.scheduleApplyChangeInsets(android.view.InsetsResizeAnimationRunner.this);
            }
        });
        this.mAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onReady$0(android.animation.ValueAnimator valueAnimator) {
        this.mAnimation.setFraction(valueAnimator.getAnimatedFraction());
        this.mController.scheduleApplyChangeInsets(this);
    }

    @Override // android.view.InternalInsetsAnimationController
    public boolean applyChangeInsets(final android.view.InsetsState insetsState) {
        if (this.mCancelled) {
            return false;
        }
        final float interpolatedFraction = this.mAnimation.getInterpolatedFraction();
        android.view.InsetsState.traverse(this.mFromState, this.mToState, new android.view.InsetsState.OnTraverseCallbacks() { // from class: android.view.InsetsResizeAnimationRunner.2
            @Override // android.view.InsetsState.OnTraverseCallbacks
            public void onIdMatch(android.view.InsetsSource insetsSource, android.view.InsetsSource insetsSource2) {
                android.graphics.Rect frame = insetsSource.getFrame();
                android.graphics.Rect frame2 = insetsSource2.getFrame();
                android.graphics.Rect rect = new android.graphics.Rect((int) (frame.left + (interpolatedFraction * (frame2.left - frame.left))), (int) (frame.top + (interpolatedFraction * (frame2.top - frame.top))), (int) (frame.right + (interpolatedFraction * (frame2.right - frame.right))), (int) (frame.bottom + (interpolatedFraction * (frame2.bottom - frame.bottom))));
                android.view.InsetsSource insetsSource3 = new android.view.InsetsSource(insetsSource.getId(), insetsSource.getType());
                insetsSource3.setFrame(rect);
                insetsSource3.setVisible(insetsSource2.isVisible());
                insetsState.addSource(insetsSource3);
            }
        });
        if (this.mFinished) {
            this.mController.notifyFinished(this, true);
        }
        return this.mFinished;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1133871366145L, this.mCancelled);
        protoOutputStream.write(1133871366146L, this.mFinished);
        protoOutputStream.write(1138166333443L, "null");
        protoOutputStream.write(1138166333444L, "null");
        protoOutputStream.write(1108101562373L, this.mAnimation.getInterpolatedFraction());
        protoOutputStream.write(1133871366150L, true);
        protoOutputStream.write(1108101562375L, 1.0f);
        protoOutputStream.write(1108101562376L, 1.0f);
        protoOutputStream.end(start);
    }

    @Override // android.view.WindowInsetsAnimationController
    public android.graphics.Insets getHiddenStateInsets() {
        return android.graphics.Insets.NONE;
    }

    @Override // android.view.WindowInsetsAnimationController
    public android.graphics.Insets getShownStateInsets() {
        return android.graphics.Insets.NONE;
    }

    @Override // android.view.WindowInsetsAnimationController
    public android.graphics.Insets getCurrentInsets() {
        return android.graphics.Insets.NONE;
    }

    @Override // android.view.WindowInsetsAnimationController
    public float getCurrentFraction() {
        return 0.0f;
    }

    @Override // android.view.WindowInsetsAnimationController
    public float getCurrentAlpha() {
        return 0.0f;
    }

    @Override // android.view.WindowInsetsAnimationController
    public void setInsetsAndAlpha(android.graphics.Insets insets, float f, float f2) {
    }

    @Override // android.view.WindowInsetsAnimationController
    public void finish(boolean z) {
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean isFinished() {
        return false;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void notifyControlRevoked(int i) {
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateSurfacePosition(android.util.SparseArray<android.view.InsetsSourceControl> sparseArray) {
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean hasZeroInsetsIme() {
        return false;
    }

    @Override // android.view.InternalInsetsAnimationController
    public void setReadyDispatched(boolean z) {
    }

    @Override // android.view.WindowInsetsAnimationControlListener
    public void onFinished(android.view.WindowInsetsAnimationController windowInsetsAnimationController) {
    }

    @Override // android.view.WindowInsetsAnimationControlListener
    public void onCancelled(android.view.WindowInsetsAnimationController windowInsetsAnimationController) {
    }
}
