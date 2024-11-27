package android.window;

/* loaded from: classes4.dex */
public final class BackEvent {
    public static final int EDGE_LEFT = 0;
    public static final int EDGE_RIGHT = 1;
    private final float mProgress;
    private final int mSwipeEdge;
    private final float mTouchX;
    private final float mTouchY;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SwipeEdge {
    }

    public BackEvent(float f, float f2, float f3, int i) {
        this.mTouchX = f;
        this.mTouchY = f2;
        this.mProgress = f3;
        this.mSwipeEdge = i;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public float getTouchX() {
        return this.mTouchX;
    }

    public float getTouchY() {
        return this.mTouchY;
    }

    public int getSwipeEdge() {
        return this.mSwipeEdge;
    }

    public java.lang.String toString() {
        return "BackEvent{mTouchX=" + this.mTouchX + ", mTouchY=" + this.mTouchY + ", mProgress=" + this.mProgress + ", mSwipeEdge" + this.mSwipeEdge + "}";
    }
}
