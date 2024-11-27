package android.widget;

/* loaded from: classes4.dex */
public class EditorTouchState {
    private float mInitialDragDirectionXYRatio;
    private boolean mIsOnHandle;
    private long mLastDownMillis;
    private float mLastDownX;
    private float mLastDownY;
    private long mLastUpMillis;
    private float mLastUpX;
    private float mLastUpY;
    private boolean mMovedEnoughForDrag;
    private boolean mMultiTapInSameArea;
    private int mMultiTapStatus = 0;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MultiTapStatus {
        public static final int DOUBLE_TAP = 2;
        public static final int FIRST_TAP = 1;
        public static final int NONE = 0;
        public static final int TRIPLE_CLICK = 3;
    }

    public float getLastDownX() {
        return this.mLastDownX;
    }

    public float getLastDownY() {
        return this.mLastDownY;
    }

    public float getLastUpX() {
        return this.mLastUpX;
    }

    public float getLastUpY() {
        return this.mLastUpY;
    }

    public boolean isDoubleTap() {
        return this.mMultiTapStatus == 2;
    }

    public boolean isTripleClick() {
        return this.mMultiTapStatus == 3;
    }

    public boolean isMultiTap() {
        return this.mMultiTapStatus == 2 || this.mMultiTapStatus == 3;
    }

    public boolean isMultiTapInSameArea() {
        return isMultiTap() && this.mMultiTapInSameArea;
    }

    public boolean isMovedEnoughForDrag() {
        return this.mMovedEnoughForDrag;
    }

    public float getInitialDragDirectionXYRatio() {
        return this.mInitialDragDirectionXYRatio;
    }

    public void setIsOnHandle(boolean z) {
        this.mIsOnHandle = z;
    }

    public boolean isOnHandle() {
        return this.mIsOnHandle;
    }

    public void update(android.view.MotionEvent motionEvent, android.view.ViewConfiguration viewConfiguration) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            boolean isFromSource = motionEvent.isFromSource(8194);
            long eventTime = motionEvent.getEventTime() - this.mLastUpMillis;
            long j = this.mLastUpMillis - this.mLastDownMillis;
            if (eventTime <= android.view.ViewConfiguration.getDoubleTapTimeout() && j <= android.view.ViewConfiguration.getDoubleTapTimeout() && (this.mMultiTapStatus == 1 || (this.mMultiTapStatus == 2 && isFromSource))) {
                if (this.mMultiTapStatus == 1) {
                    this.mMultiTapStatus = 2;
                } else {
                    this.mMultiTapStatus = 3;
                }
                this.mMultiTapInSameArea = isDistanceWithin(this.mLastDownX, this.mLastDownY, motionEvent.getX(), motionEvent.getY(), viewConfiguration.getScaledDoubleTapSlop());
            } else {
                this.mMultiTapStatus = 1;
                this.mMultiTapInSameArea = false;
            }
            this.mLastDownX = motionEvent.getX();
            this.mLastDownY = motionEvent.getY();
            this.mLastDownMillis = motionEvent.getEventTime();
            this.mMovedEnoughForDrag = false;
            this.mInitialDragDirectionXYRatio = 0.0f;
            return;
        }
        if (actionMasked == 1) {
            this.mLastUpX = motionEvent.getX();
            this.mLastUpY = motionEvent.getY();
            this.mLastUpMillis = motionEvent.getEventTime();
            this.mMovedEnoughForDrag = false;
            this.mInitialDragDirectionXYRatio = 0.0f;
            return;
        }
        if (actionMasked == 2) {
            if (!this.mMovedEnoughForDrag) {
                float x = motionEvent.getX() - this.mLastDownX;
                float y = motionEvent.getY() - this.mLastDownY;
                float f = (x * x) + (y * y);
                int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
                this.mMovedEnoughForDrag = f > ((float) (scaledTouchSlop * scaledTouchSlop));
                if (this.mMovedEnoughForDrag) {
                    this.mInitialDragDirectionXYRatio = y == 0.0f ? Float.MAX_VALUE : java.lang.Math.abs(x / y);
                    return;
                }
                return;
            }
            return;
        }
        if (actionMasked == 3) {
            this.mLastDownMillis = 0L;
            this.mLastUpMillis = 0L;
            this.mMultiTapStatus = 0;
            this.mMultiTapInSameArea = false;
            this.mMovedEnoughForDrag = false;
            this.mInitialDragDirectionXYRatio = 0.0f;
        }
    }

    public static boolean isDistanceWithin(float f, float f2, float f3, float f4, int i) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        return (f5 * f5) + (f6 * f6) <= ((float) (i * i));
    }

    public static float getXYRatio(int i) {
        if (i <= 0) {
            return 0.0f;
        }
        if (i >= 90) {
            return Float.MAX_VALUE;
        }
        return (float) java.lang.Math.tan(java.lang.Math.toRadians(i));
    }
}
