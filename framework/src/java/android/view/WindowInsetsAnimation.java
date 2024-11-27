package android.view;

/* loaded from: classes4.dex */
public final class WindowInsetsAnimation {
    private float mAlpha;
    private final long mDurationMillis;
    private float mFraction;
    private final android.view.animation.Interpolator mInterpolator;
    private final int mTypeMask;

    public WindowInsetsAnimation(int i, android.view.animation.Interpolator interpolator, long j) {
        this.mTypeMask = i;
        this.mInterpolator = interpolator;
        this.mDurationMillis = j;
    }

    public int getTypeMask() {
        return this.mTypeMask;
    }

    public float getFraction() {
        return this.mFraction;
    }

    public float getInterpolatedFraction() {
        if (this.mInterpolator != null) {
            return this.mInterpolator.getInterpolation(this.mFraction);
        }
        return this.mFraction;
    }

    public android.view.animation.Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    public long getDurationMillis() {
        return this.mDurationMillis;
    }

    public void setFraction(float f) {
        this.mFraction = f;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public void setAlpha(float f) {
        this.mAlpha = f;
    }

    public static final class Bounds {
        private final android.graphics.Insets mLowerBound;
        private final android.graphics.Insets mUpperBound;

        public Bounds(android.graphics.Insets insets, android.graphics.Insets insets2) {
            this.mLowerBound = insets;
            this.mUpperBound = insets2;
        }

        public android.graphics.Insets getLowerBound() {
            return this.mLowerBound;
        }

        public android.graphics.Insets getUpperBound() {
            return this.mUpperBound;
        }

        public android.view.WindowInsetsAnimation.Bounds inset(android.graphics.Insets insets) {
            return new android.view.WindowInsetsAnimation.Bounds(android.view.WindowInsets.insetInsets(this.mLowerBound, insets.left, insets.top, insets.right, insets.bottom), android.view.WindowInsets.insetInsets(this.mUpperBound, insets.left, insets.top, insets.right, insets.bottom));
        }

        public java.lang.String toString() {
            return "Bounds{lower=" + this.mLowerBound + " upper=" + this.mUpperBound + "}";
        }
    }

    public static abstract class Callback {
        public static final int DISPATCH_MODE_CONTINUE_ON_SUBTREE = 1;
        public static final int DISPATCH_MODE_STOP = 0;
        private final int mDispatchMode;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface DispatchMode {
        }

        public abstract android.view.WindowInsets onProgress(android.view.WindowInsets windowInsets, java.util.List<android.view.WindowInsetsAnimation> list);

        public Callback(int i) {
            this.mDispatchMode = i;
        }

        public final int getDispatchMode() {
            return this.mDispatchMode;
        }

        public void onPrepare(android.view.WindowInsetsAnimation windowInsetsAnimation) {
        }

        public android.view.WindowInsetsAnimation.Bounds onStart(android.view.WindowInsetsAnimation windowInsetsAnimation, android.view.WindowInsetsAnimation.Bounds bounds) {
            return bounds;
        }

        public void onEnd(android.view.WindowInsetsAnimation windowInsetsAnimation) {
        }
    }
}
