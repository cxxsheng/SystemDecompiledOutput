package android.animation;

/* loaded from: classes.dex */
public class TimeAnimator extends android.animation.ValueAnimator {
    private android.animation.TimeAnimator.TimeListener mListener;
    private long mPreviousTime = -1;

    public interface TimeListener {
        void onTimeUpdate(android.animation.TimeAnimator timeAnimator, long j, long j2);
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    public void start() {
        this.mPreviousTime = -1L;
        super.start();
    }

    @Override // android.animation.ValueAnimator
    boolean animateBasedOnTime(long j) {
        if (this.mListener != null) {
            long j2 = j - this.mStartTime;
            long j3 = this.mPreviousTime < 0 ? 0L : j - this.mPreviousTime;
            this.mPreviousTime = j;
            this.mListener.onTimeUpdate(this, j2, j3);
            return false;
        }
        return false;
    }

    @Override // android.animation.ValueAnimator
    public void setCurrentPlayTime(long j) {
        long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
        this.mStartTime = java.lang.Math.max(this.mStartTime, currentAnimationTimeMillis - j);
        this.mStartTimeCommitted = true;
        animateBasedOnTime(currentAnimationTimeMillis);
    }

    public void setTimeListener(android.animation.TimeAnimator.TimeListener timeListener) {
        this.mListener = timeListener;
    }

    @Override // android.animation.ValueAnimator
    void animateValue(float f) {
    }

    @Override // android.animation.ValueAnimator
    void initAnimation() {
    }
}
