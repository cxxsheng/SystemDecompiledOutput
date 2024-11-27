package com.android.server.display.color;

/* loaded from: classes.dex */
abstract class TintController {
    private static final long TRANSITION_DURATION = 3000;
    private android.animation.ValueAnimator mAnimator;
    private java.lang.Boolean mIsActivated;

    TintController() {
    }

    public abstract int getLevel();

    public abstract float[] getMatrix();

    public abstract boolean isAvailable(android.content.Context context);

    public abstract void setMatrix(int i);

    public abstract void setUp(android.content.Context context, boolean z);

    public android.animation.ValueAnimator getAnimator() {
        return this.mAnimator;
    }

    public void setAnimator(android.animation.ValueAnimator valueAnimator) {
        this.mAnimator = valueAnimator;
    }

    public void cancelAnimator() {
        if (this.mAnimator != null) {
            this.mAnimator.cancel();
        }
    }

    public void endAnimator() {
        if (this.mAnimator != null) {
            this.mAnimator.end();
            this.mAnimator = null;
        }
    }

    public void setActivated(java.lang.Boolean bool) {
        this.mIsActivated = bool;
    }

    public boolean isActivated() {
        return this.mIsActivated != null && this.mIsActivated.booleanValue();
    }

    public boolean isActivatedStateNotSet() {
        return this.mIsActivated == null;
    }

    public long getTransitionDurationMilliseconds() {
        return 3000L;
    }

    public long getTransitionDurationMilliseconds(boolean z) {
        return 3000L;
    }

    public void dump(java.io.PrintWriter printWriter) {
    }

    static java.lang.String matrixToString(float[] fArr, int i) {
        if (fArr == null || i <= 0) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Invalid arguments when formatting matrix to string, matrix is null: ");
            sb.append(fArr == null);
            sb.append(" columns: ");
            sb.append(i);
            android.util.Slog.e("ColorDisplayService", sb.toString());
            return "";
        }
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder("");
        for (int i2 = 0; i2 < fArr.length; i2++) {
            if (i2 % i == 0) {
                sb2.append("\n      ");
            }
            sb2.append(java.lang.String.format("%9.6f", java.lang.Float.valueOf(fArr[i2])));
        }
        return sb2.toString();
    }
}
