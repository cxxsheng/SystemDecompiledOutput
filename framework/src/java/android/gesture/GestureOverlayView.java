package android.gesture;

/* loaded from: classes.dex */
public class GestureOverlayView extends android.widget.FrameLayout {
    private static final boolean DITHER_FLAG = true;
    private static final int FADE_ANIMATION_RATE = 16;
    private static final boolean GESTURE_RENDERING_ANTIALIAS = true;
    public static final int GESTURE_STROKE_TYPE_MULTIPLE = 1;
    public static final int GESTURE_STROKE_TYPE_SINGLE = 0;
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    private int mCertainGestureColor;
    private int mCurrentColor;
    private android.gesture.Gesture mCurrentGesture;
    private float mCurveEndX;
    private float mCurveEndY;
    private long mFadeDuration;
    private boolean mFadeEnabled;
    private long mFadeOffset;
    private float mFadingAlpha;
    private boolean mFadingHasStarted;
    private final android.gesture.GestureOverlayView.FadeOutRunnable mFadingOut;
    private long mFadingStart;
    private final android.graphics.Paint mGesturePaint;
    private float mGestureStrokeAngleThreshold;
    private float mGestureStrokeLengthThreshold;
    private float mGestureStrokeSquarenessTreshold;
    private int mGestureStrokeType;
    private float mGestureStrokeWidth;
    private boolean mGestureVisible;
    private boolean mHandleGestureActions;
    private boolean mInterceptEvents;
    private final android.view.animation.AccelerateDecelerateInterpolator mInterpolator;
    private final android.graphics.Rect mInvalidRect;
    private int mInvalidateExtraBorder;
    private boolean mIsFadingOut;
    private boolean mIsGesturing;
    private boolean mIsListeningForGestures;
    private final java.util.ArrayList<android.gesture.GestureOverlayView.OnGestureListener> mOnGestureListeners;
    private final java.util.ArrayList<android.gesture.GestureOverlayView.OnGesturePerformedListener> mOnGesturePerformedListeners;
    private final java.util.ArrayList<android.gesture.GestureOverlayView.OnGesturingListener> mOnGesturingListeners;
    private int mOrientation;
    private final android.graphics.Path mPath;
    private boolean mPreviousWasGesturing;
    private boolean mResetGesture;
    private final java.util.ArrayList<android.gesture.GesturePoint> mStrokeBuffer;
    private float mTotalLength;
    private int mUncertainGestureColor;
    private float mX;
    private float mY;

    public interface OnGestureListener {
        void onGesture(android.gesture.GestureOverlayView gestureOverlayView, android.view.MotionEvent motionEvent);

        void onGestureCancelled(android.gesture.GestureOverlayView gestureOverlayView, android.view.MotionEvent motionEvent);

        void onGestureEnded(android.gesture.GestureOverlayView gestureOverlayView, android.view.MotionEvent motionEvent);

        void onGestureStarted(android.gesture.GestureOverlayView gestureOverlayView, android.view.MotionEvent motionEvent);
    }

    public interface OnGesturePerformedListener {
        void onGesturePerformed(android.gesture.GestureOverlayView gestureOverlayView, android.gesture.Gesture gesture);
    }

    public interface OnGesturingListener {
        void onGesturingEnded(android.gesture.GestureOverlayView gestureOverlayView);

        void onGesturingStarted(android.gesture.GestureOverlayView gestureOverlayView);
    }

    public GestureOverlayView(android.content.Context context) {
        super(context);
        this.mGesturePaint = new android.graphics.Paint();
        this.mFadeDuration = 150L;
        this.mFadeOffset = 420L;
        this.mFadeEnabled = true;
        this.mCertainGestureColor = -256;
        this.mUncertainGestureColor = 1224736512;
        this.mGestureStrokeWidth = 12.0f;
        this.mInvalidateExtraBorder = 10;
        this.mGestureStrokeType = 0;
        this.mGestureStrokeLengthThreshold = 50.0f;
        this.mGestureStrokeSquarenessTreshold = 0.275f;
        this.mGestureStrokeAngleThreshold = 40.0f;
        this.mOrientation = 1;
        this.mInvalidRect = new android.graphics.Rect();
        this.mPath = new android.graphics.Path();
        this.mGestureVisible = true;
        this.mIsGesturing = false;
        this.mPreviousWasGesturing = false;
        this.mInterceptEvents = true;
        this.mStrokeBuffer = new java.util.ArrayList<>(100);
        this.mOnGestureListeners = new java.util.ArrayList<>();
        this.mOnGesturePerformedListeners = new java.util.ArrayList<>();
        this.mOnGesturingListeners = new java.util.ArrayList<>();
        this.mIsFadingOut = false;
        this.mFadingAlpha = 1.0f;
        this.mInterpolator = new android.view.animation.AccelerateDecelerateInterpolator();
        this.mFadingOut = new android.gesture.GestureOverlayView.FadeOutRunnable();
        init();
    }

    public GestureOverlayView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, com.android.internal.R.attr.gestureOverlayViewStyle);
    }

    public GestureOverlayView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public GestureOverlayView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mGesturePaint = new android.graphics.Paint();
        this.mFadeDuration = 150L;
        this.mFadeOffset = 420L;
        this.mFadeEnabled = true;
        this.mCertainGestureColor = -256;
        this.mUncertainGestureColor = 1224736512;
        this.mGestureStrokeWidth = 12.0f;
        this.mInvalidateExtraBorder = 10;
        this.mGestureStrokeType = 0;
        this.mGestureStrokeLengthThreshold = 50.0f;
        this.mGestureStrokeSquarenessTreshold = 0.275f;
        this.mGestureStrokeAngleThreshold = 40.0f;
        this.mOrientation = 1;
        this.mInvalidRect = new android.graphics.Rect();
        this.mPath = new android.graphics.Path();
        this.mGestureVisible = true;
        this.mIsGesturing = false;
        this.mPreviousWasGesturing = false;
        this.mInterceptEvents = true;
        this.mStrokeBuffer = new java.util.ArrayList<>(100);
        this.mOnGestureListeners = new java.util.ArrayList<>();
        this.mOnGesturePerformedListeners = new java.util.ArrayList<>();
        this.mOnGesturingListeners = new java.util.ArrayList<>();
        this.mIsFadingOut = false;
        this.mFadingAlpha = 1.0f;
        this.mInterpolator = new android.view.animation.AccelerateDecelerateInterpolator();
        this.mFadingOut = new android.gesture.GestureOverlayView.FadeOutRunnable();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.GestureOverlayView, i, i2);
        this.mGestureStrokeWidth = obtainStyledAttributes.getFloat(1, this.mGestureStrokeWidth);
        this.mInvalidateExtraBorder = java.lang.Math.max(1, ((int) this.mGestureStrokeWidth) - 1);
        this.mCertainGestureColor = obtainStyledAttributes.getColor(2, this.mCertainGestureColor);
        this.mUncertainGestureColor = obtainStyledAttributes.getColor(3, this.mUncertainGestureColor);
        this.mFadeDuration = obtainStyledAttributes.getInt(5, (int) this.mFadeDuration);
        this.mFadeOffset = obtainStyledAttributes.getInt(4, (int) this.mFadeOffset);
        this.mGestureStrokeType = obtainStyledAttributes.getInt(6, this.mGestureStrokeType);
        this.mGestureStrokeLengthThreshold = obtainStyledAttributes.getFloat(7, this.mGestureStrokeLengthThreshold);
        this.mGestureStrokeAngleThreshold = obtainStyledAttributes.getFloat(9, this.mGestureStrokeAngleThreshold);
        this.mGestureStrokeSquarenessTreshold = obtainStyledAttributes.getFloat(8, this.mGestureStrokeSquarenessTreshold);
        this.mInterceptEvents = obtainStyledAttributes.getBoolean(10, this.mInterceptEvents);
        this.mFadeEnabled = obtainStyledAttributes.getBoolean(11, this.mFadeEnabled);
        this.mOrientation = obtainStyledAttributes.getInt(0, this.mOrientation);
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        setWillNotDraw(false);
        android.graphics.Paint paint = this.mGesturePaint;
        paint.setAntiAlias(true);
        paint.setColor(this.mCertainGestureColor);
        paint.setStyle(android.graphics.Paint.Style.STROKE);
        paint.setStrokeJoin(android.graphics.Paint.Join.ROUND);
        paint.setStrokeCap(android.graphics.Paint.Cap.ROUND);
        paint.setStrokeWidth(this.mGestureStrokeWidth);
        paint.setDither(true);
        this.mCurrentColor = this.mCertainGestureColor;
        setPaintAlpha(255);
    }

    public java.util.ArrayList<android.gesture.GesturePoint> getCurrentStroke() {
        return this.mStrokeBuffer;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(int i) {
        this.mOrientation = i;
    }

    public void setGestureColor(int i) {
        this.mCertainGestureColor = i;
    }

    public void setUncertainGestureColor(int i) {
        this.mUncertainGestureColor = i;
    }

    public int getUncertainGestureColor() {
        return this.mUncertainGestureColor;
    }

    public int getGestureColor() {
        return this.mCertainGestureColor;
    }

    public float getGestureStrokeWidth() {
        return this.mGestureStrokeWidth;
    }

    public void setGestureStrokeWidth(float f) {
        this.mGestureStrokeWidth = f;
        this.mInvalidateExtraBorder = java.lang.Math.max(1, ((int) f) - 1);
        this.mGesturePaint.setStrokeWidth(f);
    }

    public int getGestureStrokeType() {
        return this.mGestureStrokeType;
    }

    public void setGestureStrokeType(int i) {
        this.mGestureStrokeType = i;
    }

    public float getGestureStrokeLengthThreshold() {
        return this.mGestureStrokeLengthThreshold;
    }

    public void setGestureStrokeLengthThreshold(float f) {
        this.mGestureStrokeLengthThreshold = f;
    }

    public float getGestureStrokeSquarenessTreshold() {
        return this.mGestureStrokeSquarenessTreshold;
    }

    public void setGestureStrokeSquarenessTreshold(float f) {
        this.mGestureStrokeSquarenessTreshold = f;
    }

    public float getGestureStrokeAngleThreshold() {
        return this.mGestureStrokeAngleThreshold;
    }

    public void setGestureStrokeAngleThreshold(float f) {
        this.mGestureStrokeAngleThreshold = f;
    }

    public boolean isEventsInterceptionEnabled() {
        return this.mInterceptEvents;
    }

    public void setEventsInterceptionEnabled(boolean z) {
        this.mInterceptEvents = z;
    }

    public boolean isFadeEnabled() {
        return this.mFadeEnabled;
    }

    public void setFadeEnabled(boolean z) {
        this.mFadeEnabled = z;
    }

    public android.gesture.Gesture getGesture() {
        return this.mCurrentGesture;
    }

    public void setGesture(android.gesture.Gesture gesture) {
        if (this.mCurrentGesture != null) {
            clear(false);
        }
        setCurrentColor(this.mCertainGestureColor);
        this.mCurrentGesture = gesture;
        android.graphics.Path path = this.mCurrentGesture.toPath();
        android.graphics.RectF rectF = new android.graphics.RectF();
        path.computeBounds(rectF, true);
        this.mPath.rewind();
        this.mPath.addPath(path, (-rectF.left) + ((getWidth() - rectF.width()) / 2.0f), (-rectF.top) + ((getHeight() - rectF.height()) / 2.0f));
        this.mResetGesture = true;
        invalidate();
    }

    public android.graphics.Path getGesturePath() {
        return this.mPath;
    }

    public android.graphics.Path getGesturePath(android.graphics.Path path) {
        path.set(this.mPath);
        return path;
    }

    public boolean isGestureVisible() {
        return this.mGestureVisible;
    }

    public void setGestureVisible(boolean z) {
        this.mGestureVisible = z;
    }

    public long getFadeOffset() {
        return this.mFadeOffset;
    }

    public void setFadeOffset(long j) {
        this.mFadeOffset = j;
    }

    public void addOnGestureListener(android.gesture.GestureOverlayView.OnGestureListener onGestureListener) {
        this.mOnGestureListeners.add(onGestureListener);
    }

    public void removeOnGestureListener(android.gesture.GestureOverlayView.OnGestureListener onGestureListener) {
        this.mOnGestureListeners.remove(onGestureListener);
    }

    public void removeAllOnGestureListeners() {
        this.mOnGestureListeners.clear();
    }

    public void addOnGesturePerformedListener(android.gesture.GestureOverlayView.OnGesturePerformedListener onGesturePerformedListener) {
        this.mOnGesturePerformedListeners.add(onGesturePerformedListener);
        if (this.mOnGesturePerformedListeners.size() > 0) {
            this.mHandleGestureActions = true;
        }
    }

    public void removeOnGesturePerformedListener(android.gesture.GestureOverlayView.OnGesturePerformedListener onGesturePerformedListener) {
        this.mOnGesturePerformedListeners.remove(onGesturePerformedListener);
        if (this.mOnGesturePerformedListeners.size() <= 0) {
            this.mHandleGestureActions = false;
        }
    }

    public void removeAllOnGesturePerformedListeners() {
        this.mOnGesturePerformedListeners.clear();
        this.mHandleGestureActions = false;
    }

    public void addOnGesturingListener(android.gesture.GestureOverlayView.OnGesturingListener onGesturingListener) {
        this.mOnGesturingListeners.add(onGesturingListener);
    }

    public void removeOnGesturingListener(android.gesture.GestureOverlayView.OnGesturingListener onGesturingListener) {
        this.mOnGesturingListeners.remove(onGesturingListener);
    }

    public void removeAllOnGesturingListeners() {
        this.mOnGesturingListeners.clear();
    }

    public boolean isGesturing() {
        return this.mIsGesturing;
    }

    private void setCurrentColor(int i) {
        this.mCurrentColor = i;
        if (this.mFadingHasStarted) {
            setPaintAlpha((int) (this.mFadingAlpha * 255.0f));
        } else {
            setPaintAlpha(255);
        }
        invalidate();
    }

    public android.graphics.Paint getGesturePaint() {
        return this.mGesturePaint;
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        super.draw(canvas);
        if (this.mCurrentGesture != null && this.mGestureVisible) {
            canvas.drawPath(this.mPath, this.mGesturePaint);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPaintAlpha(int i) {
        this.mGesturePaint.setColor(((((this.mCurrentColor >>> 24) * (i + (i >> 7))) >> 8) << 24) | ((this.mCurrentColor << 8) >>> 8));
    }

    public void clear(boolean z) {
        clear(z, false, true);
    }

    private void clear(boolean z, boolean z2, boolean z3) {
        setPaintAlpha(255);
        removeCallbacks(this.mFadingOut);
        this.mResetGesture = false;
        this.mFadingOut.fireActionPerformed = z2;
        this.mFadingOut.resetMultipleStrokes = false;
        if (z && this.mCurrentGesture != null) {
            this.mFadingAlpha = 1.0f;
            this.mIsFadingOut = true;
            this.mFadingHasStarted = false;
            this.mFadingStart = android.view.animation.AnimationUtils.currentAnimationTimeMillis() + this.mFadeOffset;
            postDelayed(this.mFadingOut, this.mFadeOffset);
            return;
        }
        this.mFadingAlpha = 1.0f;
        this.mIsFadingOut = false;
        this.mFadingHasStarted = false;
        if (z3) {
            this.mCurrentGesture = null;
            this.mPath.rewind();
            invalidate();
        } else {
            if (z2) {
                postDelayed(this.mFadingOut, this.mFadeOffset);
                return;
            }
            if (this.mGestureStrokeType == 1) {
                this.mFadingOut.resetMultipleStrokes = true;
                postDelayed(this.mFadingOut, this.mFadeOffset);
            } else {
                this.mCurrentGesture = null;
                this.mPath.rewind();
                invalidate();
            }
        }
    }

    public void cancelClearAnimation() {
        setPaintAlpha(255);
        this.mIsFadingOut = false;
        this.mFadingHasStarted = false;
        removeCallbacks(this.mFadingOut);
        this.mPath.rewind();
        this.mCurrentGesture = null;
    }

    public void cancelGesture() {
        this.mIsListeningForGestures = false;
        this.mCurrentGesture.addStroke(new android.gesture.GestureStroke(this.mStrokeBuffer));
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.view.MotionEvent obtain = android.view.MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
        java.util.ArrayList<android.gesture.GestureOverlayView.OnGestureListener> arrayList = this.mOnGestureListeners;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).onGestureCancelled(this, obtain);
        }
        obtain.recycle();
        clear(false);
        this.mIsGesturing = false;
        this.mPreviousWasGesturing = false;
        this.mStrokeBuffer.clear();
        java.util.ArrayList<android.gesture.GestureOverlayView.OnGesturingListener> arrayList2 = this.mOnGesturingListeners;
        int size2 = arrayList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            arrayList2.get(i2).onGesturingEnded(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelClearAnimation();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        if (isEnabled()) {
            boolean z = (this.mIsGesturing || (this.mCurrentGesture != null && this.mCurrentGesture.getStrokesCount() > 0 && this.mPreviousWasGesturing)) && this.mInterceptEvents;
            processEvent(motionEvent);
            if (z) {
                motionEvent.setAction(3);
            }
            super.dispatchTouchEvent(motionEvent);
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean processEvent(android.view.MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                touchDown(motionEvent);
                invalidate();
                return true;
            case 1:
                if (this.mIsListeningForGestures) {
                    touchUp(motionEvent, false);
                    invalidate();
                    return true;
                }
                return false;
            case 2:
                if (this.mIsListeningForGestures) {
                    android.graphics.Rect rect = touchMove(motionEvent);
                    if (rect != null) {
                        invalidate(rect);
                    }
                    return true;
                }
                return false;
            case 3:
                if (this.mIsListeningForGestures) {
                    touchUp(motionEvent, true);
                    invalidate();
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    private void touchDown(android.view.MotionEvent motionEvent) {
        this.mIsListeningForGestures = true;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        this.mX = x;
        this.mY = y;
        this.mTotalLength = 0.0f;
        this.mIsGesturing = false;
        if (this.mGestureStrokeType == 0 || this.mResetGesture) {
            if (this.mHandleGestureActions) {
                setCurrentColor(this.mUncertainGestureColor);
            }
            this.mResetGesture = false;
            this.mCurrentGesture = null;
            this.mPath.rewind();
        } else if ((this.mCurrentGesture == null || this.mCurrentGesture.getStrokesCount() == 0) && this.mHandleGestureActions) {
            setCurrentColor(this.mUncertainGestureColor);
        }
        if (this.mFadingHasStarted) {
            cancelClearAnimation();
        } else if (this.mIsFadingOut) {
            setPaintAlpha(255);
            this.mIsFadingOut = false;
            this.mFadingHasStarted = false;
            removeCallbacks(this.mFadingOut);
        }
        if (this.mCurrentGesture == null) {
            this.mCurrentGesture = new android.gesture.Gesture();
        }
        this.mStrokeBuffer.add(new android.gesture.GesturePoint(x, y, motionEvent.getEventTime()));
        this.mPath.moveTo(x, y);
        int i = this.mInvalidateExtraBorder;
        int i2 = (int) x;
        int i3 = (int) y;
        this.mInvalidRect.set(i2 - i, i3 - i, i2 + i, i3 + i);
        this.mCurveEndX = x;
        this.mCurveEndY = y;
        java.util.ArrayList<android.gesture.GestureOverlayView.OnGestureListener> arrayList = this.mOnGestureListeners;
        int size = arrayList.size();
        for (int i4 = 0; i4 < size; i4++) {
            arrayList.get(i4).onGestureStarted(this, motionEvent);
        }
    }

    private android.graphics.Rect touchMove(android.view.MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        float f = this.mX;
        float f2 = this.mY;
        float abs = java.lang.Math.abs(x - f);
        float abs2 = java.lang.Math.abs(y - f2);
        if (abs < 3.0f && abs2 < 3.0f) {
            return null;
        }
        android.graphics.Rect rect = this.mInvalidRect;
        int i = this.mInvalidateExtraBorder;
        rect.set(((int) this.mCurveEndX) - i, ((int) this.mCurveEndY) - i, ((int) this.mCurveEndX) + i, ((int) this.mCurveEndY) + i);
        float f3 = (x + f) / 2.0f;
        this.mCurveEndX = f3;
        float f4 = (y + f2) / 2.0f;
        this.mCurveEndY = f4;
        this.mPath.quadTo(f, f2, f3, f4);
        int i2 = (int) f;
        int i3 = (int) f2;
        rect.union(i2 - i, i3 - i, i2 + i, i3 + i);
        int i4 = (int) f3;
        int i5 = (int) f4;
        rect.union(i4 - i, i5 - i, i4 + i, i5 + i);
        this.mX = x;
        this.mY = y;
        this.mStrokeBuffer.add(new android.gesture.GesturePoint(x, y, motionEvent.getEventTime()));
        if (this.mHandleGestureActions && !this.mIsGesturing) {
            this.mTotalLength += (float) java.lang.Math.hypot(abs, abs2);
            if (this.mTotalLength > this.mGestureStrokeLengthThreshold) {
                android.gesture.OrientedBoundingBox computeOrientedBoundingBox = android.gesture.GestureUtils.computeOrientedBoundingBox(this.mStrokeBuffer);
                float abs3 = java.lang.Math.abs(computeOrientedBoundingBox.orientation);
                if (abs3 > 90.0f) {
                    abs3 = 180.0f - abs3;
                }
                if (computeOrientedBoundingBox.squareness > this.mGestureStrokeSquarenessTreshold || (this.mOrientation != 1 ? abs3 > this.mGestureStrokeAngleThreshold : abs3 < this.mGestureStrokeAngleThreshold)) {
                    this.mIsGesturing = true;
                    setCurrentColor(this.mCertainGestureColor);
                    java.util.ArrayList<android.gesture.GestureOverlayView.OnGesturingListener> arrayList = this.mOnGesturingListeners;
                    int size = arrayList.size();
                    for (int i6 = 0; i6 < size; i6++) {
                        arrayList.get(i6).onGesturingStarted(this);
                    }
                }
            }
        }
        java.util.ArrayList<android.gesture.GestureOverlayView.OnGestureListener> arrayList2 = this.mOnGestureListeners;
        int size2 = arrayList2.size();
        for (int i7 = 0; i7 < size2; i7++) {
            arrayList2.get(i7).onGesture(this, motionEvent);
        }
        return rect;
    }

    private void touchUp(android.view.MotionEvent motionEvent, boolean z) {
        this.mIsListeningForGestures = false;
        if (this.mCurrentGesture != null) {
            this.mCurrentGesture.addStroke(new android.gesture.GestureStroke(this.mStrokeBuffer));
            if (!z) {
                java.util.ArrayList<android.gesture.GestureOverlayView.OnGestureListener> arrayList = this.mOnGestureListeners;
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    arrayList.get(i).onGestureEnded(this, motionEvent);
                }
                clear(this.mHandleGestureActions && this.mFadeEnabled, this.mHandleGestureActions && this.mIsGesturing, false);
            } else {
                cancelGesture(motionEvent);
            }
        } else {
            cancelGesture(motionEvent);
        }
        this.mStrokeBuffer.clear();
        this.mPreviousWasGesturing = this.mIsGesturing;
        this.mIsGesturing = false;
        java.util.ArrayList<android.gesture.GestureOverlayView.OnGesturingListener> arrayList2 = this.mOnGesturingListeners;
        int size2 = arrayList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            arrayList2.get(i2).onGesturingEnded(this);
        }
    }

    private void cancelGesture(android.view.MotionEvent motionEvent) {
        java.util.ArrayList<android.gesture.GestureOverlayView.OnGestureListener> arrayList = this.mOnGestureListeners;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).onGestureCancelled(this, motionEvent);
        }
        clear(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fireOnGesturePerformed() {
        java.util.ArrayList<android.gesture.GestureOverlayView.OnGesturePerformedListener> arrayList = this.mOnGesturePerformedListeners;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).onGesturePerformed(this, this.mCurrentGesture);
        }
    }

    private class FadeOutRunnable implements java.lang.Runnable {
        boolean fireActionPerformed;
        boolean resetMultipleStrokes;

        private FadeOutRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (android.gesture.GestureOverlayView.this.mIsFadingOut) {
                long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis() - android.gesture.GestureOverlayView.this.mFadingStart;
                if (currentAnimationTimeMillis > android.gesture.GestureOverlayView.this.mFadeDuration) {
                    if (this.fireActionPerformed) {
                        android.gesture.GestureOverlayView.this.fireOnGesturePerformed();
                    }
                    android.gesture.GestureOverlayView.this.mPreviousWasGesturing = false;
                    android.gesture.GestureOverlayView.this.mIsFadingOut = false;
                    android.gesture.GestureOverlayView.this.mFadingHasStarted = false;
                    android.gesture.GestureOverlayView.this.mPath.rewind();
                    android.gesture.GestureOverlayView.this.mCurrentGesture = null;
                    android.gesture.GestureOverlayView.this.setPaintAlpha(255);
                } else {
                    android.gesture.GestureOverlayView.this.mFadingHasStarted = true;
                    android.gesture.GestureOverlayView.this.mFadingAlpha = 1.0f - android.gesture.GestureOverlayView.this.mInterpolator.getInterpolation(java.lang.Math.max(0.0f, java.lang.Math.min(1.0f, currentAnimationTimeMillis / android.gesture.GestureOverlayView.this.mFadeDuration)));
                    android.gesture.GestureOverlayView.this.setPaintAlpha((int) (android.gesture.GestureOverlayView.this.mFadingAlpha * 255.0f));
                    android.gesture.GestureOverlayView.this.postDelayed(this, 16L);
                }
            } else if (this.resetMultipleStrokes) {
                android.gesture.GestureOverlayView.this.mResetGesture = true;
            } else {
                android.gesture.GestureOverlayView.this.fireOnGesturePerformed();
                android.gesture.GestureOverlayView.this.mFadingHasStarted = false;
                android.gesture.GestureOverlayView.this.mPath.rewind();
                android.gesture.GestureOverlayView.this.mCurrentGesture = null;
                android.gesture.GestureOverlayView.this.mPreviousWasGesturing = false;
                android.gesture.GestureOverlayView.this.setPaintAlpha(255);
            }
            android.gesture.GestureOverlayView.this.invalidate();
        }
    }
}
