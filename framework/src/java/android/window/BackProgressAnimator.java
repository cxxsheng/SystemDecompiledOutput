package android.window;

/* loaded from: classes4.dex */
public class BackProgressAnimator {
    private static final android.util.FloatProperty<android.window.BackProgressAnimator> PROGRESS_PROP = new android.util.FloatProperty<android.window.BackProgressAnimator>(android.app.Notification.CATEGORY_PROGRESS) { // from class: android.window.BackProgressAnimator.1
        @Override // android.util.FloatProperty
        public void setValue(android.window.BackProgressAnimator backProgressAnimator, float f) {
            backProgressAnimator.setProgress(f);
            backProgressAnimator.updateProgressValue(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.window.BackProgressAnimator backProgressAnimator) {
            return java.lang.Float.valueOf(backProgressAnimator.getProgress());
        }
    };
    private static final float SCALE_FACTOR = 100.0f;
    private java.lang.Runnable mBackCancelledFinishRunnable;
    private android.window.BackProgressAnimator.ProgressCallback mCallback;
    private android.window.BackMotionEvent mLastBackEvent;
    private float mProgress = 0.0f;
    private boolean mBackAnimationInProgress = false;
    private final com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener mOnAnimationEndListener = new com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener() { // from class: android.window.BackProgressAnimator$$ExternalSyntheticLambda0
        @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
        public final void onAnimationEnd(com.android.internal.dynamicanimation.animation.DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
            android.window.BackProgressAnimator.this.lambda$new$0(dynamicAnimation, z, f, f2);
        }
    };
    private final com.android.internal.dynamicanimation.animation.SpringAnimation mSpring = new com.android.internal.dynamicanimation.animation.SpringAnimation(this, PROGRESS_PROP);

    public interface ProgressCallback {
        void onProgressUpdate(android.window.BackEvent backEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(com.android.internal.dynamicanimation.animation.DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
        invokeBackCancelledRunnable();
        reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setProgress(float f) {
        this.mProgress = f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getProgress() {
        return this.mProgress;
    }

    public BackProgressAnimator() {
        this.mSpring.setSpring(new com.android.internal.dynamicanimation.animation.SpringForce().setStiffness(1500.0f).setDampingRatio(1.0f));
    }

    public void onBackProgressed(android.window.BackMotionEvent backMotionEvent) {
        if (!this.mBackAnimationInProgress) {
            return;
        }
        this.mLastBackEvent = backMotionEvent;
        if (this.mSpring == null) {
            return;
        }
        this.mSpring.animateToFinalPosition(backMotionEvent.getProgress() * 100.0f);
    }

    public void onBackStarted(android.window.BackMotionEvent backMotionEvent, android.window.BackProgressAnimator.ProgressCallback progressCallback) {
        reset();
        this.mLastBackEvent = backMotionEvent;
        this.mCallback = progressCallback;
        this.mBackAnimationInProgress = true;
        updateProgressValue(0.0f);
    }

    public void reset() {
        if (this.mBackCancelledFinishRunnable != null) {
            updateProgressValue(0.0f);
            invokeBackCancelledRunnable();
        }
        this.mSpring.animateToFinalPosition(0.0f);
        if (this.mSpring.canSkipToEnd()) {
            this.mSpring.skipToEnd();
        } else {
            this.mSpring.cancel();
        }
        this.mBackAnimationInProgress = false;
        this.mLastBackEvent = null;
        this.mCallback = null;
        this.mProgress = 0.0f;
    }

    public void onBackCancelled(java.lang.Runnable runnable) {
        this.mBackCancelledFinishRunnable = runnable;
        this.mSpring.addEndListener(this.mOnAnimationEndListener);
        this.mSpring.animateToFinalPosition(0.0f);
    }

    boolean isBackAnimationInProgress() {
        return this.mBackAnimationInProgress;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProgressValue(float f) {
        if (this.mLastBackEvent == null || this.mCallback == null || !this.mBackAnimationInProgress) {
            return;
        }
        this.mCallback.onProgressUpdate(new android.window.BackEvent(this.mLastBackEvent.getTouchX(), this.mLastBackEvent.getTouchY(), f / 100.0f, this.mLastBackEvent.getSwipeEdge()));
    }

    private void invokeBackCancelledRunnable() {
        this.mSpring.removeEndListener(this.mOnAnimationEndListener);
        this.mBackCancelledFinishRunnable.run();
        this.mBackCancelledFinishRunnable = null;
    }
}
