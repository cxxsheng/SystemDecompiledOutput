package com.android.internal.widget;

/* loaded from: classes5.dex */
public abstract class AutoScrollHelper implements android.view.View.OnTouchListener {
    private static final int DEFAULT_ACTIVATION_DELAY = android.view.ViewConfiguration.getTapTimeout();
    private static final int DEFAULT_EDGE_TYPE = 1;
    private static final float DEFAULT_MAXIMUM_EDGE = Float.MAX_VALUE;
    private static final int DEFAULT_MAXIMUM_VELOCITY_DIPS = 1575;
    private static final int DEFAULT_MINIMUM_VELOCITY_DIPS = 315;
    private static final int DEFAULT_RAMP_DOWN_DURATION = 500;
    private static final int DEFAULT_RAMP_UP_DURATION = 500;
    private static final float DEFAULT_RELATIVE_EDGE = 0.2f;
    private static final float DEFAULT_RELATIVE_VELOCITY = 1.0f;
    public static final int EDGE_TYPE_INSIDE = 0;
    public static final int EDGE_TYPE_INSIDE_EXTEND = 1;
    public static final int EDGE_TYPE_OUTSIDE = 2;
    private static final int HORIZONTAL = 0;
    public static final float NO_MAX = Float.MAX_VALUE;
    public static final float NO_MIN = 0.0f;
    public static final float RELATIVE_UNSPECIFIED = 0.0f;
    private static final int VERTICAL = 1;
    private int mActivationDelay;
    private boolean mAlreadyDelayed;
    private boolean mAnimating;
    private int mEdgeType;
    private boolean mEnabled;
    private boolean mExclusive;
    private boolean mNeedsCancel;
    private boolean mNeedsReset;
    private java.lang.Runnable mRunnable;
    private final android.view.View mTarget;
    private final com.android.internal.widget.AutoScrollHelper.ClampedScroller mScroller = new com.android.internal.widget.AutoScrollHelper.ClampedScroller();
    private final android.view.animation.Interpolator mEdgeInterpolator = new android.view.animation.AccelerateInterpolator();
    private float[] mRelativeEdges = {0.0f, 0.0f};
    private float[] mMaximumEdges = {Float.MAX_VALUE, Float.MAX_VALUE};
    private float[] mRelativeVelocity = {0.0f, 0.0f};
    private float[] mMinimumVelocity = {0.0f, 0.0f};
    private float[] mMaximumVelocity = {Float.MAX_VALUE, Float.MAX_VALUE};

    public abstract boolean canTargetScrollHorizontally(int i);

    public abstract boolean canTargetScrollVertically(int i);

    public abstract void scrollTargetBy(int i, int i2);

    public AutoScrollHelper(android.view.View view) {
        this.mTarget = view;
        android.util.DisplayMetrics displayMetrics = android.content.res.Resources.getSystem().getDisplayMetrics();
        int i = (int) ((displayMetrics.density * 1575.0f) + 0.5f);
        int i2 = (int) ((displayMetrics.density * 315.0f) + 0.5f);
        float f = i;
        setMaximumVelocity(f, f);
        float f2 = i2;
        setMinimumVelocity(f2, f2);
        setEdgeType(1);
        setMaximumEdges(Float.MAX_VALUE, Float.MAX_VALUE);
        setRelativeEdges(0.2f, 0.2f);
        setRelativeVelocity(1.0f, 1.0f);
        setActivationDelay(DEFAULT_ACTIVATION_DELAY);
        setRampUpDuration(500);
        setRampDownDuration(500);
    }

    public com.android.internal.widget.AutoScrollHelper setEnabled(boolean z) {
        if (this.mEnabled && !z) {
            requestStop();
        }
        this.mEnabled = z;
        return this;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public com.android.internal.widget.AutoScrollHelper setExclusive(boolean z) {
        this.mExclusive = z;
        return this;
    }

    public boolean isExclusive() {
        return this.mExclusive;
    }

    public com.android.internal.widget.AutoScrollHelper setMaximumVelocity(float f, float f2) {
        this.mMaximumVelocity[0] = f / 1000.0f;
        this.mMaximumVelocity[1] = f2 / 1000.0f;
        return this;
    }

    public com.android.internal.widget.AutoScrollHelper setMinimumVelocity(float f, float f2) {
        this.mMinimumVelocity[0] = f / 1000.0f;
        this.mMinimumVelocity[1] = f2 / 1000.0f;
        return this;
    }

    public com.android.internal.widget.AutoScrollHelper setRelativeVelocity(float f, float f2) {
        this.mRelativeVelocity[0] = f / 1000.0f;
        this.mRelativeVelocity[1] = f2 / 1000.0f;
        return this;
    }

    public com.android.internal.widget.AutoScrollHelper setEdgeType(int i) {
        this.mEdgeType = i;
        return this;
    }

    public com.android.internal.widget.AutoScrollHelper setRelativeEdges(float f, float f2) {
        this.mRelativeEdges[0] = f;
        this.mRelativeEdges[1] = f2;
        return this;
    }

    public com.android.internal.widget.AutoScrollHelper setMaximumEdges(float f, float f2) {
        this.mMaximumEdges[0] = f;
        this.mMaximumEdges[1] = f2;
        return this;
    }

    public com.android.internal.widget.AutoScrollHelper setActivationDelay(int i) {
        this.mActivationDelay = i;
        return this;
    }

    public com.android.internal.widget.AutoScrollHelper setRampUpDuration(int i) {
        this.mScroller.setRampUpDuration(i);
        return this;
    }

    public com.android.internal.widget.AutoScrollHelper setRampDownDuration(int i) {
        this.mScroller.setRampDownDuration(i);
        return this;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View.OnTouchListener
    public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
        if (!this.mEnabled) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.mNeedsCancel = true;
                this.mAlreadyDelayed = false;
                this.mScroller.setTargetVelocity(computeTargetVelocity(0, motionEvent.getX(), view.getWidth(), this.mTarget.getWidth()), computeTargetVelocity(1, motionEvent.getY(), view.getHeight(), this.mTarget.getHeight()));
                if (!this.mAnimating && shouldAnimate()) {
                    startAnimating();
                    break;
                }
                break;
            case 1:
            case 3:
                requestStop();
                break;
            case 2:
                this.mScroller.setTargetVelocity(computeTargetVelocity(0, motionEvent.getX(), view.getWidth(), this.mTarget.getWidth()), computeTargetVelocity(1, motionEvent.getY(), view.getHeight(), this.mTarget.getHeight()));
                if (!this.mAnimating) {
                    startAnimating();
                    break;
                }
                break;
        }
        return this.mExclusive && this.mAnimating;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldAnimate() {
        com.android.internal.widget.AutoScrollHelper.ClampedScroller clampedScroller = this.mScroller;
        int verticalDirection = clampedScroller.getVerticalDirection();
        int horizontalDirection = clampedScroller.getHorizontalDirection();
        return (verticalDirection != 0 && canTargetScrollVertically(verticalDirection)) || (horizontalDirection != 0 && canTargetScrollHorizontally(horizontalDirection));
    }

    private void startAnimating() {
        if (this.mRunnable == null) {
            this.mRunnable = new com.android.internal.widget.AutoScrollHelper.ScrollAnimationRunnable();
        }
        this.mAnimating = true;
        this.mNeedsReset = true;
        if (!this.mAlreadyDelayed && this.mActivationDelay > 0) {
            this.mTarget.postOnAnimationDelayed(this.mRunnable, this.mActivationDelay);
        } else {
            this.mRunnable.run();
        }
        this.mAlreadyDelayed = true;
    }

    private void requestStop() {
        if (this.mNeedsReset) {
            this.mAnimating = false;
        } else {
            this.mScroller.requestStop();
        }
    }

    private float computeTargetVelocity(int i, float f, float f2, float f3) {
        float edgeValue = getEdgeValue(this.mRelativeEdges[i], f2, this.mMaximumEdges[i], f);
        if (edgeValue == 0.0f) {
            return 0.0f;
        }
        float f4 = this.mRelativeVelocity[i];
        float f5 = this.mMinimumVelocity[i];
        float f6 = this.mMaximumVelocity[i];
        float f7 = f4 * f3;
        if (edgeValue > 0.0f) {
            return constrain(edgeValue * f7, f5, f6);
        }
        return -constrain((-edgeValue) * f7, f5, f6);
    }

    private float getEdgeValue(float f, float f2, float f3, float f4) {
        float interpolation;
        float constrain = constrain(f * f2, 0.0f, f3);
        float constrainEdgeValue = constrainEdgeValue(f2 - f4, constrain) - constrainEdgeValue(f4, constrain);
        if (constrainEdgeValue < 0.0f) {
            interpolation = -this.mEdgeInterpolator.getInterpolation(-constrainEdgeValue);
        } else {
            if (constrainEdgeValue <= 0.0f) {
                return 0.0f;
            }
            interpolation = this.mEdgeInterpolator.getInterpolation(constrainEdgeValue);
        }
        return constrain(interpolation, -1.0f, 1.0f);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private float constrainEdgeValue(float f, float f2) {
        if (f2 == 0.0f) {
            return 0.0f;
        }
        switch (this.mEdgeType) {
            case 0:
            case 1:
                if (f < f2) {
                    if (f >= 0.0f) {
                        return 1.0f - (f / f2);
                    }
                    if (this.mAnimating && this.mEdgeType == 1) {
                        return 1.0f;
                    }
                }
                return 0.0f;
            case 2:
                if (f < 0.0f) {
                    return f / (-f2);
                }
                return 0.0f;
            default:
                return 0.0f;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int constrain(int i, int i2, int i3) {
        if (i > i3) {
            return i3;
        }
        if (i < i2) {
            return i2;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float constrain(float f, float f2, float f3) {
        if (f > f3) {
            return f3;
        }
        if (f < f2) {
            return f2;
        }
        return f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelTargetTouch() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.view.MotionEvent obtain = android.view.MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
        this.mTarget.onTouchEvent(obtain);
        obtain.recycle();
    }

    private class ScrollAnimationRunnable implements java.lang.Runnable {
        private ScrollAnimationRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!com.android.internal.widget.AutoScrollHelper.this.mAnimating) {
                return;
            }
            if (com.android.internal.widget.AutoScrollHelper.this.mNeedsReset) {
                com.android.internal.widget.AutoScrollHelper.this.mNeedsReset = false;
                com.android.internal.widget.AutoScrollHelper.this.mScroller.start();
            }
            com.android.internal.widget.AutoScrollHelper.ClampedScroller clampedScroller = com.android.internal.widget.AutoScrollHelper.this.mScroller;
            if (clampedScroller.isFinished() || !com.android.internal.widget.AutoScrollHelper.this.shouldAnimate()) {
                com.android.internal.widget.AutoScrollHelper.this.mAnimating = false;
                return;
            }
            if (com.android.internal.widget.AutoScrollHelper.this.mNeedsCancel) {
                com.android.internal.widget.AutoScrollHelper.this.mNeedsCancel = false;
                com.android.internal.widget.AutoScrollHelper.this.cancelTargetTouch();
            }
            clampedScroller.computeScrollDelta();
            com.android.internal.widget.AutoScrollHelper.this.scrollTargetBy(clampedScroller.getDeltaX(), clampedScroller.getDeltaY());
            com.android.internal.widget.AutoScrollHelper.this.mTarget.postOnAnimation(this);
        }
    }

    private static class ClampedScroller {
        private int mEffectiveRampDown;
        private int mRampDownDuration;
        private int mRampUpDuration;
        private float mStopValue;
        private float mTargetVelocityX;
        private float mTargetVelocityY;
        private long mStartTime = Long.MIN_VALUE;
        private long mStopTime = -1;
        private long mDeltaTime = 0;
        private int mDeltaX = 0;
        private int mDeltaY = 0;

        public void setRampUpDuration(int i) {
            this.mRampUpDuration = i;
        }

        public void setRampDownDuration(int i) {
            this.mRampDownDuration = i;
        }

        public void start() {
            this.mStartTime = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
            this.mStopTime = -1L;
            this.mDeltaTime = this.mStartTime;
            this.mStopValue = 0.5f;
            this.mDeltaX = 0;
            this.mDeltaY = 0;
        }

        public void requestStop() {
            long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
            this.mEffectiveRampDown = com.android.internal.widget.AutoScrollHelper.constrain((int) (currentAnimationTimeMillis - this.mStartTime), 0, this.mRampDownDuration);
            this.mStopValue = getValueAt(currentAnimationTimeMillis);
            this.mStopTime = currentAnimationTimeMillis;
        }

        public boolean isFinished() {
            return this.mStopTime > 0 && android.view.animation.AnimationUtils.currentAnimationTimeMillis() > this.mStopTime + ((long) this.mEffectiveRampDown);
        }

        private float getValueAt(long j) {
            if (j < this.mStartTime) {
                return 0.0f;
            }
            if (this.mStopTime < 0 || j < this.mStopTime) {
                return com.android.internal.widget.AutoScrollHelper.constrain((j - this.mStartTime) / this.mRampUpDuration, 0.0f, 1.0f) * 0.5f;
            }
            return (1.0f - this.mStopValue) + (this.mStopValue * com.android.internal.widget.AutoScrollHelper.constrain((j - this.mStopTime) / this.mEffectiveRampDown, 0.0f, 1.0f));
        }

        private float interpolateValue(float f) {
            return ((-4.0f) * f * f) + (f * 4.0f);
        }

        public void computeScrollDelta() {
            if (this.mDeltaTime == 0) {
                throw new java.lang.RuntimeException("Cannot compute scroll delta before calling start()");
            }
            long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
            float interpolateValue = interpolateValue(getValueAt(currentAnimationTimeMillis));
            long j = currentAnimationTimeMillis - this.mDeltaTime;
            this.mDeltaTime = currentAnimationTimeMillis;
            float f = j * interpolateValue;
            this.mDeltaX = (int) (this.mTargetVelocityX * f);
            this.mDeltaY = (int) (f * this.mTargetVelocityY);
        }

        public void setTargetVelocity(float f, float f2) {
            this.mTargetVelocityX = f;
            this.mTargetVelocityY = f2;
        }

        public int getHorizontalDirection() {
            return (int) (this.mTargetVelocityX / java.lang.Math.abs(this.mTargetVelocityX));
        }

        public int getVerticalDirection() {
            return (int) (this.mTargetVelocityY / java.lang.Math.abs(this.mTargetVelocityY));
        }

        public int getDeltaX() {
            return this.mDeltaX;
        }

        public int getDeltaY() {
            return this.mDeltaY;
        }
    }

    public static class AbsListViewAutoScroller extends com.android.internal.widget.AutoScrollHelper {
        private final android.widget.AbsListView mTarget;

        public AbsListViewAutoScroller(android.widget.AbsListView absListView) {
            super(absListView);
            this.mTarget = absListView;
        }

        @Override // com.android.internal.widget.AutoScrollHelper
        public void scrollTargetBy(int i, int i2) {
            this.mTarget.scrollListBy(i2);
        }

        @Override // com.android.internal.widget.AutoScrollHelper
        public boolean canTargetScrollHorizontally(int i) {
            return false;
        }

        @Override // com.android.internal.widget.AutoScrollHelper
        public boolean canTargetScrollVertically(int i) {
            android.widget.AbsListView absListView = this.mTarget;
            int count = absListView.getCount();
            if (count == 0) {
                return false;
            }
            int childCount = absListView.getChildCount();
            int firstVisiblePosition = absListView.getFirstVisiblePosition();
            int i2 = firstVisiblePosition + childCount;
            if (i > 0) {
                if (i2 >= count && absListView.getChildAt(childCount - 1).getBottom() <= absListView.getHeight()) {
                    return false;
                }
            } else {
                if (i >= 0) {
                    return false;
                }
                if (firstVisiblePosition <= 0 && absListView.getChildAt(0).getTop() >= 0) {
                    return false;
                }
            }
            return true;
        }
    }
}
