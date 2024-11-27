package android.view.animation;

/* loaded from: classes4.dex */
public class LayoutAnimationController {
    public static final int ORDER_NORMAL = 0;
    public static final int ORDER_RANDOM = 2;
    public static final int ORDER_REVERSE = 1;
    protected android.view.animation.Animation mAnimation;
    private float mDelay;
    private long mDuration;
    protected android.view.animation.Interpolator mInterpolator;
    private long mMaxDelay;
    private int mOrder;
    protected java.util.Random mRandomizer;

    public static class AnimationParameters {
        public int count;
        public int index;
    }

    public LayoutAnimationController(android.content.Context context, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.LayoutAnimation);
        this.mDelay = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(1), context).value;
        this.mOrder = obtainStyledAttributes.getInt(3, 0);
        int resourceId = obtainStyledAttributes.getResourceId(2, 0);
        if (resourceId > 0) {
            setAnimation(context, resourceId);
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId2 > 0) {
            setInterpolator(context, resourceId2);
        }
        obtainStyledAttributes.recycle();
    }

    public LayoutAnimationController(android.view.animation.Animation animation) {
        this(animation, 0.5f);
    }

    public LayoutAnimationController(android.view.animation.Animation animation, float f) {
        this.mDelay = f;
        setAnimation(animation);
    }

    public int getOrder() {
        return this.mOrder;
    }

    public void setOrder(int i) {
        this.mOrder = i;
    }

    public void setAnimation(android.content.Context context, int i) {
        setAnimation(android.view.animation.AnimationUtils.loadAnimation(context, i));
    }

    public void setAnimation(android.view.animation.Animation animation) {
        this.mAnimation = animation;
        this.mAnimation.setFillBefore(true);
    }

    public android.view.animation.Animation getAnimation() {
        return this.mAnimation;
    }

    public void setInterpolator(android.content.Context context, int i) {
        setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(context, i));
    }

    public void setInterpolator(android.view.animation.Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public android.view.animation.Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    public float getDelay() {
        return this.mDelay;
    }

    public void setDelay(float f) {
        this.mDelay = f;
    }

    public boolean willOverlap() {
        return this.mDelay < 1.0f;
    }

    public void start() {
        this.mDuration = this.mAnimation.getDuration();
        this.mMaxDelay = Long.MIN_VALUE;
        this.mAnimation.setStartTime(-1L);
    }

    public final android.view.animation.Animation getAnimationForView(android.view.View view) {
        long delayForView = getDelayForView(view) + this.mAnimation.getStartOffset();
        this.mMaxDelay = java.lang.Math.max(this.mMaxDelay, delayForView);
        try {
            android.view.animation.Animation mo5398clone = this.mAnimation.mo5398clone();
            mo5398clone.setStartOffset(delayForView);
            return mo5398clone;
        } catch (java.lang.CloneNotSupportedException e) {
            return null;
        }
    }

    public boolean isDone() {
        return android.view.animation.AnimationUtils.currentAnimationTimeMillis() > (this.mAnimation.getStartTime() + this.mMaxDelay) + this.mDuration;
    }

    protected long getDelayForView(android.view.View view) {
        if (view.getLayoutParams().layoutAnimationParameters == null) {
            return 0L;
        }
        float duration = this.mDelay * this.mAnimation.getDuration();
        long transformedIndex = (long) (getTransformedIndex(r4) * duration);
        float f = duration * r4.count;
        if (this.mInterpolator == null) {
            this.mInterpolator = new android.view.animation.LinearInterpolator();
        }
        return (long) (this.mInterpolator.getInterpolation(transformedIndex / f) * f);
    }

    protected int getTransformedIndex(android.view.animation.LayoutAnimationController.AnimationParameters animationParameters) {
        switch (getOrder()) {
            case 1:
                return (animationParameters.count - 1) - animationParameters.index;
            case 2:
                if (this.mRandomizer == null) {
                    this.mRandomizer = new java.util.Random();
                }
                return (int) (animationParameters.count * this.mRandomizer.nextFloat());
            default:
                return animationParameters.index;
        }
    }
}
