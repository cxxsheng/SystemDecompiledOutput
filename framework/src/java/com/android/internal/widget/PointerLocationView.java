package com.android.internal.widget;

/* loaded from: classes5.dex */
public class PointerLocationView extends android.view.View implements android.hardware.input.InputManager.InputDeviceListener, android.view.WindowManagerPolicyConstants.PointerEventListener {
    private static final java.lang.String ALT_STRATEGY_PROPERY_KEY = "debug.velocitytracker.alt";
    private static final com.android.internal.widget.PointerLocationView.PointerState EMPTY_POINTER_STATE = new com.android.internal.widget.PointerLocationView.PointerState();
    private static final java.lang.String GESTURE_EXCLUSION_PROP = "debug.pointerlocation.showexclusion";
    private static final java.lang.String TAG = "Pointer";
    private int mActivePointerId;
    private final android.view.VelocityTracker mAltVelocity;
    private boolean mCurDown;
    private int mCurNumPointers;
    private final android.graphics.Paint mCurrentPointPaint;
    private float mDensity;
    private int mHeaderBottom;
    private int mHeaderPaddingTop;
    private final android.hardware.input.InputManager mIm;
    private int mMaxNumPointers;
    private final android.graphics.Paint mPaint;
    private final android.graphics.Paint mPathPaint;
    private final android.util.SparseArray<com.android.internal.widget.PointerLocationView.PointerState> mPointers;
    private boolean mPrintCoords;
    private android.graphics.RectF mReusableOvalRect;
    private final android.graphics.Region mSystemGestureExclusion;
    private android.view.ISystemGestureExclusionListener mSystemGestureExclusionListener;
    private final android.graphics.Paint mSystemGestureExclusionPaint;
    private final android.graphics.Path mSystemGestureExclusionPath;
    private final android.graphics.Region mSystemGestureExclusionRejected;
    private final android.graphics.Paint mSystemGestureExclusionRejectedPaint;
    private final android.graphics.Paint mTargetPaint;
    private final android.view.MotionEvent.PointerCoords mTempCoords;
    private final com.android.internal.widget.PointerLocationView.FasterStringBuilder mText;
    private final android.graphics.Paint mTextBackgroundPaint;
    private final android.graphics.Paint mTextLevelPaint;
    private final android.graphics.Paint.FontMetricsInt mTextMetrics;
    private final android.graphics.Paint mTextPaint;
    private final android.view.ViewConfiguration mVC;
    private final android.view.VelocityTracker mVelocity;
    private android.graphics.Insets mWaterfallInsets;

    public static class PointerState {
        private float mAltXVelocity;
        private float mAltYVelocity;
        private float mBoundingBottom;
        private float mBoundingLeft;
        private float mBoundingRight;
        private float mBoundingTop;
        private boolean mCurDown;
        private boolean mHasBoundingBox;
        private int mToolType;
        private int mTraceCount;
        private float mXVelocity;
        private float mYVelocity;
        private float[] mTraceX = new float[32];
        private float[] mTraceY = new float[32];
        private boolean[] mTraceCurrent = new boolean[32];
        private android.view.MotionEvent.PointerCoords mCoords = new android.view.MotionEvent.PointerCoords();

        public void clearTrace() {
            this.mTraceCount = 0;
        }

        public void addTrace(float f, float f2, boolean z) {
            int length = this.mTraceX.length;
            if (this.mTraceCount == length) {
                int i = length * 2;
                float[] fArr = new float[i];
                java.lang.System.arraycopy(this.mTraceX, 0, fArr, 0, this.mTraceCount);
                this.mTraceX = fArr;
                float[] fArr2 = new float[i];
                java.lang.System.arraycopy(this.mTraceY, 0, fArr2, 0, this.mTraceCount);
                this.mTraceY = fArr2;
                boolean[] zArr = new boolean[i];
                java.lang.System.arraycopy(this.mTraceCurrent, 0, zArr, 0, this.mTraceCount);
                this.mTraceCurrent = zArr;
            }
            this.mTraceX[this.mTraceCount] = f;
            this.mTraceY[this.mTraceCount] = f2;
            this.mTraceCurrent[this.mTraceCount] = z;
            this.mTraceCount++;
        }
    }

    public PointerLocationView(android.content.Context context) {
        super(context);
        this.mTextMetrics = new android.graphics.Paint.FontMetricsInt();
        this.mHeaderPaddingTop = 0;
        this.mWaterfallInsets = android.graphics.Insets.NONE;
        this.mPointers = new android.util.SparseArray<>();
        this.mTempCoords = new android.view.MotionEvent.PointerCoords();
        this.mSystemGestureExclusion = new android.graphics.Region();
        this.mSystemGestureExclusionRejected = new android.graphics.Region();
        this.mSystemGestureExclusionPath = new android.graphics.Path();
        this.mText = new com.android.internal.widget.PointerLocationView.FasterStringBuilder();
        this.mPrintCoords = true;
        this.mReusableOvalRect = new android.graphics.RectF();
        this.mSystemGestureExclusionListener = new com.android.internal.widget.PointerLocationView.AnonymousClass1();
        setFocusableInTouchMode(true);
        this.mIm = (android.hardware.input.InputManager) context.getSystemService(android.hardware.input.InputManager.class);
        this.mVC = android.view.ViewConfiguration.get(context);
        this.mTextPaint = new android.graphics.Paint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setARGB(255, 0, 0, 0);
        this.mTextBackgroundPaint = new android.graphics.Paint();
        this.mTextBackgroundPaint.setAntiAlias(false);
        this.mTextBackgroundPaint.setARGB(128, 255, 255, 255);
        this.mTextLevelPaint = new android.graphics.Paint();
        this.mTextLevelPaint.setAntiAlias(false);
        this.mTextLevelPaint.setARGB(192, 255, 0, 0);
        this.mPaint = new android.graphics.Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setARGB(255, 255, 255, 255);
        this.mPaint.setStyle(android.graphics.Paint.Style.STROKE);
        this.mCurrentPointPaint = new android.graphics.Paint();
        this.mCurrentPointPaint.setAntiAlias(true);
        this.mCurrentPointPaint.setARGB(255, 255, 0, 0);
        this.mCurrentPointPaint.setStyle(android.graphics.Paint.Style.STROKE);
        this.mTargetPaint = new android.graphics.Paint();
        this.mTargetPaint.setAntiAlias(false);
        this.mTargetPaint.setARGB(255, 0, 0, 192);
        this.mPathPaint = new android.graphics.Paint();
        this.mPathPaint.setAntiAlias(false);
        this.mPathPaint.setARGB(255, 0, 96, 255);
        this.mPathPaint.setStyle(android.graphics.Paint.Style.STROKE);
        configureDensityDependentFactors();
        this.mSystemGestureExclusionPaint = new android.graphics.Paint();
        this.mSystemGestureExclusionPaint.setARGB(25, 255, 0, 0);
        this.mSystemGestureExclusionPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        this.mSystemGestureExclusionRejectedPaint = new android.graphics.Paint();
        this.mSystemGestureExclusionRejectedPaint.setARGB(25, 0, 0, 255);
        this.mSystemGestureExclusionRejectedPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        this.mActivePointerId = 0;
        this.mVelocity = android.view.VelocityTracker.obtain();
        java.lang.String str = android.os.SystemProperties.get(ALT_STRATEGY_PROPERY_KEY);
        if (str.length() != 0) {
            android.util.Log.d(TAG, "Comparing default velocity tracker strategy with " + str);
            this.mAltVelocity = android.view.VelocityTracker.obtain(str);
        } else {
            this.mAltVelocity = null;
        }
    }

    public void setPrintCoords(boolean z) {
        this.mPrintCoords = z;
    }

    @Override // android.view.View
    public android.view.WindowInsets onApplyWindowInsets(android.view.WindowInsets windowInsets) {
        android.graphics.Insets insets = android.graphics.Insets.NONE;
        int i = 0;
        android.view.RoundedCorner roundedCorner = windowInsets.getRoundedCorner(0);
        if (roundedCorner != null) {
            i = roundedCorner.getRadius();
        }
        android.view.RoundedCorner roundedCorner2 = windowInsets.getRoundedCorner(1);
        if (roundedCorner2 != null) {
            i = java.lang.Math.max(i, roundedCorner2.getRadius());
        }
        if (windowInsets.getDisplayCutout() != null) {
            i = java.lang.Math.max(i, windowInsets.getDisplayCutout().getSafeInsetTop());
            insets = windowInsets.getDisplayCutout().getWaterfallInsets();
        }
        this.mHeaderPaddingTop = i;
        this.mWaterfallInsets = insets;
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mTextPaint.getFontMetricsInt(this.mTextMetrics);
        this.mHeaderBottom = (this.mHeaderPaddingTop - this.mTextMetrics.ascent) + this.mTextMetrics.descent + 2;
    }

    private void drawOval(android.graphics.Canvas canvas, float f, float f2, float f3, float f4, float f5, android.graphics.Paint paint) {
        canvas.save(1);
        canvas.rotate((float) ((f5 * 180.0f) / 3.141592653589793d), f, f2);
        float f6 = f4 / 2.0f;
        this.mReusableOvalRect.left = f - f6;
        this.mReusableOvalRect.right = f + f6;
        float f7 = f3 / 2.0f;
        this.mReusableOvalRect.top = f2 - f7;
        this.mReusableOvalRect.bottom = f2 + f7;
        canvas.drawOval(this.mReusableOvalRect, paint);
        canvas.restore();
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        int i;
        int i2;
        float f;
        int size = this.mPointers.size();
        if (!this.mSystemGestureExclusion.isEmpty()) {
            this.mSystemGestureExclusionPath.reset();
            this.mSystemGestureExclusion.getBoundaryPath(this.mSystemGestureExclusionPath);
            canvas.drawPath(this.mSystemGestureExclusionPath, this.mSystemGestureExclusionPaint);
        }
        if (!this.mSystemGestureExclusionRejected.isEmpty()) {
            this.mSystemGestureExclusionPath.reset();
            this.mSystemGestureExclusionRejected.getBoundaryPath(this.mSystemGestureExclusionPath);
            canvas.drawPath(this.mSystemGestureExclusionPath, this.mSystemGestureExclusionRejectedPaint);
        }
        drawLabels(canvas);
        int i3 = 0;
        while (i3 < size) {
            com.android.internal.widget.PointerLocationView.PointerState valueAt = this.mPointers.valueAt(i3);
            int i4 = valueAt.mTraceCount;
            this.mPaint.setARGB(255, 128, 255, 255);
            float f2 = 0.0f;
            float f3 = 0.0f;
            boolean z = false;
            boolean z2 = false;
            int i5 = 0;
            while (i5 < i4) {
                float f4 = valueAt.mTraceX[i5];
                float f5 = valueAt.mTraceY[i5];
                if (java.lang.Float.isNaN(f4)) {
                    i2 = i5;
                    f = f2;
                } else if (java.lang.Float.isNaN(f5)) {
                    i2 = i5;
                    f = f2;
                } else {
                    if (!z2) {
                        i2 = i5;
                    } else {
                        i2 = i5;
                        float f6 = f2;
                        canvas.drawLine(f3, f2, f4, f5, this.mPathPaint);
                        canvas.drawPoint(f3, f6, valueAt.mTraceCurrent[i2 + (-1)] ? this.mCurrentPointPaint : this.mPaint);
                        z = true;
                    }
                    f3 = f4;
                    f2 = f5;
                    z2 = true;
                    i5 = i2 + 1;
                }
                f2 = f;
                z2 = false;
                i5 = i2 + 1;
            }
            float f7 = f2;
            if (z) {
                this.mPaint.setARGB(255, 255, 64, 128);
                canvas.drawLine(f3, f7, f3 + (valueAt.mXVelocity * 16.0f), f7 + (valueAt.mYVelocity * 16.0f), this.mPaint);
                if (this.mAltVelocity != null) {
                    this.mPaint.setARGB(255, 64, 255, 128);
                    canvas.drawLine(f3, f7, f3 + (valueAt.mAltXVelocity * 16.0f), f7 + (valueAt.mAltYVelocity * 16.0f), this.mPaint);
                }
            }
            if (this.mCurDown && valueAt.mCurDown) {
                canvas.drawLine(0.0f, valueAt.mCoords.y, getWidth(), valueAt.mCoords.y, this.mTargetPaint);
                canvas.drawLine(valueAt.mCoords.x, -getHeight(), valueAt.mCoords.x, java.lang.Math.max(getHeight(), getWidth()), this.mTargetPaint);
                int i6 = (int) (valueAt.mCoords.pressure * 255.0f);
                int i7 = 255 - i6;
                this.mPaint.setARGB(255, i6, 255, i7);
                canvas.drawPoint(valueAt.mCoords.x, valueAt.mCoords.y, this.mPaint);
                this.mPaint.setARGB(255, i6, i7, 128);
                i = size;
                drawOval(canvas, valueAt.mCoords.x, valueAt.mCoords.y, valueAt.mCoords.touchMajor, valueAt.mCoords.touchMinor, valueAt.mCoords.orientation, this.mPaint);
                this.mPaint.setARGB(255, i6, 128, i7);
                drawOval(canvas, valueAt.mCoords.x, valueAt.mCoords.y, valueAt.mCoords.toolMajor, valueAt.mCoords.toolMinor, valueAt.mCoords.orientation, this.mPaint);
                float max = java.lang.Math.max(valueAt.mCoords.toolMajor * 0.7f, this.mDensity * 24.0f);
                this.mPaint.setARGB(255, i6, 255, 0);
                double d = max;
                float sin = (float) (java.lang.Math.sin(valueAt.mCoords.orientation) * d);
                float f8 = (float) ((-java.lang.Math.cos(valueAt.mCoords.orientation)) * d);
                if (valueAt.mToolType == 2 || valueAt.mToolType == 4) {
                    canvas.drawLine(valueAt.mCoords.x, valueAt.mCoords.y, valueAt.mCoords.x + sin, valueAt.mCoords.y + f8, this.mPaint);
                } else {
                    canvas.drawLine(valueAt.mCoords.x - sin, valueAt.mCoords.y - f8, valueAt.mCoords.x + sin, valueAt.mCoords.y + f8, this.mPaint);
                }
                float sin2 = (float) java.lang.Math.sin(valueAt.mCoords.getAxisValue(25));
                canvas.drawCircle(valueAt.mCoords.x + (sin * sin2), valueAt.mCoords.y + (f8 * sin2), this.mDensity * 3.0f, this.mPaint);
                if (valueAt.mHasBoundingBox) {
                    canvas.drawRect(valueAt.mBoundingLeft, valueAt.mBoundingTop, valueAt.mBoundingRight, valueAt.mBoundingBottom, this.mPaint);
                }
            } else {
                i = size;
            }
            i3++;
            size = i;
        }
    }

    private void drawLabels(android.graphics.Canvas canvas) {
        android.graphics.Paint paint;
        android.graphics.Paint paint2;
        int width = (getWidth() - this.mWaterfallInsets.left) - this.mWaterfallInsets.right;
        int i = width / 7;
        int i2 = (this.mHeaderPaddingTop - this.mTextMetrics.ascent) + 1;
        int i3 = this.mHeaderBottom;
        canvas.save();
        canvas.translate(this.mWaterfallInsets.left, 0.0f);
        com.android.internal.widget.PointerLocationView.PointerState pointerState = this.mPointers.get(this.mActivePointerId, EMPTY_POINTER_STATE);
        float f = i3;
        canvas.drawRect(0.0f, this.mHeaderPaddingTop, i - 1, f, this.mTextBackgroundPaint);
        float f2 = i2;
        canvas.drawText(this.mText.clear().append("P: ").append(this.mCurNumPointers).append(" / ").append(this.mMaxNumPointers).toString(), 1.0f, f2, this.mTextPaint);
        int i4 = pointerState.mTraceCount;
        if ((this.mCurDown && pointerState.mCurDown) || i4 == 0) {
            canvas.drawRect(i, this.mHeaderPaddingTop, r15 - 1, f, this.mTextBackgroundPaint);
            canvas.drawText(this.mText.clear().append("X: ").append(pointerState.mCoords.x, 1).toString(), i + 1, f2, this.mTextPaint);
            canvas.drawRect(i * 2, this.mHeaderPaddingTop, (i * 3) - 1, f, this.mTextBackgroundPaint);
            canvas.drawText(this.mText.clear().append("Y: ").append(pointerState.mCoords.y, 1).toString(), r15 + 1, f2, this.mTextPaint);
        } else {
            int i5 = i4 - 1;
            float f3 = pointerState.mTraceX[i5] - pointerState.mTraceX[0];
            float f4 = pointerState.mTraceY[i5] - pointerState.mTraceY[0];
            float f5 = i;
            float f6 = this.mHeaderPaddingTop;
            int i6 = i * 2;
            float f7 = i6 - 1;
            if (java.lang.Math.abs(f3) >= this.mVC.getScaledTouchSlop()) {
                paint = this.mTextLevelPaint;
            } else {
                paint = this.mTextBackgroundPaint;
            }
            canvas.drawRect(f5, f6, f7, f, paint);
            canvas.drawText(this.mText.clear().append("dX: ").append(f3, 1).toString(), i + 1, f2, this.mTextPaint);
            float f8 = i6;
            float f9 = this.mHeaderPaddingTop;
            float f10 = (i * 3) - 1;
            if (java.lang.Math.abs(f4) >= this.mVC.getScaledTouchSlop()) {
                paint2 = this.mTextLevelPaint;
            } else {
                paint2 = this.mTextBackgroundPaint;
            }
            canvas.drawRect(f8, f9, f10, f, paint2);
            canvas.drawText(this.mText.clear().append("dY: ").append(f4, 1).toString(), i6 + 1, f2, this.mTextPaint);
        }
        canvas.drawRect(i * 3, this.mHeaderPaddingTop, r15 - 1, f, this.mTextBackgroundPaint);
        canvas.drawText(this.mText.clear().append("Xv: ").append(pointerState.mXVelocity, 3).toString(), r14 + 1, f2, this.mTextPaint);
        canvas.drawRect(i * 4, this.mHeaderPaddingTop, r14 - 1, f, this.mTextBackgroundPaint);
        canvas.drawText(this.mText.clear().append("Yv: ").append(pointerState.mYVelocity, 3).toString(), r15 + 1, f2, this.mTextPaint);
        float f11 = i * 5;
        int i7 = i * 6;
        canvas.drawRect(f11, this.mHeaderPaddingTop, i7 - 1, f, this.mTextBackgroundPaint);
        float f12 = i;
        canvas.drawRect(f11, this.mHeaderPaddingTop, ((pointerState.mCoords.pressure * f12) + f11) - 1.0f, f, this.mTextLevelPaint);
        canvas.drawText(this.mText.clear().append("Prs: ").append(pointerState.mCoords.pressure, 2).toString(), r14 + 1, f2, this.mTextPaint);
        float f13 = i7;
        canvas.drawRect(f13, this.mHeaderPaddingTop, width, f, this.mTextBackgroundPaint);
        canvas.drawRect(f13, this.mHeaderPaddingTop, ((pointerState.mCoords.size * f12) + f13) - 1.0f, f, this.mTextLevelPaint);
        canvas.drawText(this.mText.clear().append("Size: ").append(pointerState.mCoords.size, 2).toString(), i7 + 1, f2, this.mTextPaint);
        canvas.restore();
    }

    private void logMotionEvent(java.lang.String str, android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int historySize = motionEvent.getHistorySize();
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < historySize; i++) {
            for (int i2 = 0; i2 < pointerCount; i2++) {
                int pointerId = motionEvent.getPointerId(i2);
                motionEvent.getHistoricalPointerCoords(i2, i, this.mTempCoords);
                logCoords(str, action, i2, this.mTempCoords, pointerId, motionEvent);
            }
        }
        for (int i3 = 0; i3 < pointerCount; i3++) {
            int pointerId2 = motionEvent.getPointerId(i3);
            motionEvent.getPointerCoords(i3, this.mTempCoords);
            logCoords(str, action, i3, this.mTempCoords, pointerId2, motionEvent);
        }
    }

    private void logCoords(java.lang.String str, int i, int i2, android.view.MotionEvent.PointerCoords pointerCoords, int i3, android.view.MotionEvent motionEvent) {
        int toolType = motionEvent.getToolType(i2);
        int buttonState = motionEvent.getButtonState();
        java.lang.String str2 = "UP";
        switch (i & 255) {
            case 0:
                str2 = "DOWN";
                break;
            case 1:
                break;
            case 2:
                str2 = "MOVE";
                break;
            case 3:
                str2 = "CANCEL";
                break;
            case 4:
                str2 = "OUTSIDE";
                break;
            case 5:
                if (i2 == ((i & 65280) >> 8)) {
                    str2 = "DOWN";
                    break;
                } else {
                    str2 = "MOVE";
                    break;
                }
            case 6:
                if (i2 != ((i & 65280) >> 8)) {
                    str2 = "MOVE";
                    break;
                }
                break;
            case 7:
                str2 = "HOVER MOVE";
                break;
            case 8:
                str2 = "SCROLL";
                break;
            case 9:
                str2 = "HOVER ENTER";
                break;
            case 10:
                str2 = "HOVER EXIT";
                break;
            default:
                str2 = java.lang.Integer.toString(i);
                break;
        }
        android.util.Log.i(TAG, this.mText.clear().append(str).append(" id ").append(i3 + 1).append(": ").append(str2).append(" (").append(pointerCoords.x, 3).append(", ").append(pointerCoords.y, 3).append(") Pressure=").append(pointerCoords.pressure, 3).append(" Size=").append(pointerCoords.size, 3).append(" TouchMajor=").append(pointerCoords.touchMajor, 3).append(" TouchMinor=").append(pointerCoords.touchMinor, 3).append(" ToolMajor=").append(pointerCoords.toolMajor, 3).append(" ToolMinor=").append(pointerCoords.toolMinor, 3).append(" Orientation=").append((float) ((pointerCoords.orientation * 180.0f) / 3.141592653589793d), 1).append("deg").append(" Tilt=").append((float) ((pointerCoords.getAxisValue(25) * 180.0f) / 3.141592653589793d), 1).append("deg").append(" Distance=").append(pointerCoords.getAxisValue(24), 1).append(" VScroll=").append(pointerCoords.getAxisValue(9), 1).append(" HScroll=").append(pointerCoords.getAxisValue(10), 1).append(" BoundingBox=[(").append(motionEvent.getAxisValue(32), 3).append(", ").append(motionEvent.getAxisValue(33), 3).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END).append(", (").append(motionEvent.getAxisValue(34), 3).append(", ").append(motionEvent.getAxisValue(35), 3).append(")]").append(" ToolType=").append(android.view.MotionEvent.toolTypeToString(toolType)).append(" ButtonState=").append(android.view.MotionEvent.buttonStateToString(buttonState)).toString());
    }

    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v19 */
    @Override // android.view.WindowManagerPolicyConstants.PointerEventListener
    public void onPointerEvent(android.view.MotionEvent motionEvent) {
        ?? r1;
        android.view.MotionEvent.PointerCoords pointerCoords;
        com.android.internal.widget.PointerLocationView.PointerState pointerState;
        char c;
        android.view.MotionEvent.PointerCoords pointerCoords2;
        com.android.internal.widget.PointerLocationView.PointerState pointerState2;
        int i;
        int i2;
        int action = motionEvent.getAction();
        if (action == 0 || (action & 255) == 5) {
            int i3 = (action & 65280) >> 8;
            if (action == 0) {
                this.mPointers.clear();
                this.mCurDown = true;
                this.mCurNumPointers = 0;
                this.mMaxNumPointers = 0;
                this.mVelocity.clear();
                if (this.mAltVelocity != null) {
                    this.mAltVelocity.clear();
                }
            }
            this.mCurNumPointers++;
            if (this.mMaxNumPointers < this.mCurNumPointers) {
                this.mMaxNumPointers = this.mCurNumPointers;
            }
            int pointerId = motionEvent.getPointerId(i3);
            com.android.internal.widget.PointerLocationView.PointerState pointerState3 = this.mPointers.get(pointerId);
            if (pointerState3 == null) {
                pointerState3 = new com.android.internal.widget.PointerLocationView.PointerState();
                this.mPointers.put(pointerId, pointerState3);
            }
            if (!this.mPointers.contains(this.mActivePointerId) || !this.mPointers.get(this.mActivePointerId).mCurDown) {
                this.mActivePointerId = pointerId;
            }
            pointerState3.mCurDown = true;
            android.view.InputDevice device = android.view.InputDevice.getDevice(motionEvent.getDeviceId());
            pointerState3.mHasBoundingBox = (device == null || device.getMotionRange(32) == null) ? false : true;
        }
        int pointerCount = motionEvent.getPointerCount();
        this.mVelocity.addMovement(motionEvent);
        this.mVelocity.computeCurrentVelocity(1);
        if (this.mAltVelocity != null) {
            this.mAltVelocity.addMovement(motionEvent);
            this.mAltVelocity.computeCurrentVelocity(1);
        }
        int historySize = motionEvent.getHistorySize();
        int i4 = 0;
        while (i4 < historySize) {
            int i5 = 0;
            while (i5 < pointerCount) {
                int pointerId2 = motionEvent.getPointerId(i5);
                com.android.internal.widget.PointerLocationView.PointerState pointerState4 = this.mCurDown ? this.mPointers.get(pointerId2) : null;
                android.view.MotionEvent.PointerCoords pointerCoords3 = pointerState4 != null ? pointerState4.mCoords : this.mTempCoords;
                motionEvent.getHistoricalPointerCoords(i5, i4, pointerCoords3);
                if (!this.mPrintCoords) {
                    pointerCoords2 = pointerCoords3;
                    pointerState2 = pointerState4;
                    i = i5;
                    i2 = i4;
                } else {
                    pointerCoords2 = pointerCoords3;
                    pointerState2 = pointerState4;
                    i = i5;
                    i2 = i4;
                    logCoords(TAG, action, i5, pointerCoords2, pointerId2, motionEvent);
                }
                if (pointerState2 != null) {
                    android.view.MotionEvent.PointerCoords pointerCoords4 = pointerCoords2;
                    pointerState2.addTrace(pointerCoords4.x, pointerCoords4.y, false);
                }
                i5 = i + 1;
                i4 = i2;
            }
            i4++;
        }
        for (int i6 = 0; i6 < pointerCount; i6++) {
            int pointerId3 = motionEvent.getPointerId(i6);
            com.android.internal.widget.PointerLocationView.PointerState pointerState5 = this.mCurDown ? this.mPointers.get(pointerId3) : null;
            android.view.MotionEvent.PointerCoords pointerCoords5 = pointerState5 != null ? pointerState5.mCoords : this.mTempCoords;
            motionEvent.getPointerCoords(i6, pointerCoords5);
            if (!this.mPrintCoords) {
                pointerCoords = pointerCoords5;
                pointerState = pointerState5;
            } else {
                pointerCoords = pointerCoords5;
                pointerState = pointerState5;
                logCoords(TAG, action, i6, pointerCoords5, pointerId3, motionEvent);
            }
            if (pointerState == null) {
                c = ' ';
            } else {
                pointerState.addTrace(pointerCoords.x, pointerCoords.y, true);
                pointerState.mXVelocity = this.mVelocity.getXVelocity(pointerId3);
                pointerState.mYVelocity = this.mVelocity.getYVelocity(pointerId3);
                if (this.mAltVelocity != null) {
                    pointerState.mAltXVelocity = this.mAltVelocity.getXVelocity(pointerId3);
                    pointerState.mAltYVelocity = this.mAltVelocity.getYVelocity(pointerId3);
                }
                pointerState.mToolType = motionEvent.getToolType(i6);
                if (!pointerState.mHasBoundingBox) {
                    c = ' ';
                } else {
                    c = ' ';
                    pointerState.mBoundingLeft = motionEvent.getAxisValue(32, i6);
                    pointerState.mBoundingTop = motionEvent.getAxisValue(33, i6);
                    pointerState.mBoundingRight = motionEvent.getAxisValue(34, i6);
                    pointerState.mBoundingBottom = motionEvent.getAxisValue(35, i6);
                }
            }
        }
        if (action == 1 || action == 3 || (action & 255) == 6) {
            int i7 = (65280 & action) >> 8;
            int pointerId4 = motionEvent.getPointerId(i7);
            com.android.internal.widget.PointerLocationView.PointerState pointerState6 = this.mPointers.get(pointerId4);
            if (pointerState6 == null) {
                android.util.Slog.wtf(TAG, "Could not find pointer id=" + pointerId4 + " in mPointers map, size=" + this.mPointers.size() + " pointerindex=" + i7 + " action=0x" + java.lang.Integer.toHexString(action));
                return;
            }
            pointerState6.mCurDown = false;
            if (action != 1) {
                if (action == 3) {
                    r1 = 0;
                } else {
                    this.mCurNumPointers--;
                    if (this.mActivePointerId == pointerId4) {
                        this.mActivePointerId = motionEvent.getPointerId(i7 != 0 ? 0 : 1);
                    }
                    pointerState6.addTrace(Float.NaN, Float.NaN, false);
                }
            } else {
                r1 = 0;
            }
            this.mCurDown = r1;
            this.mCurNumPointers = r1;
        }
        invalidate();
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        onPointerEvent(motionEvent);
        if (motionEvent.getAction() == 0 && !isFocused()) {
            requestFocus();
            return true;
        }
        return true;
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        int source = motionEvent.getSource();
        if ((source & 2) != 0) {
            onPointerEvent(motionEvent);
            return true;
        }
        if ((source & 16) != 0) {
            logMotionEvent("Joystick", motionEvent);
            return true;
        }
        if ((source & 8) != 0) {
            logMotionEvent("Position", motionEvent);
            return true;
        }
        logMotionEvent("Generic", motionEvent);
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (shouldLogKey(i)) {
            int repeatCount = keyEvent.getRepeatCount();
            if (repeatCount == 0) {
                android.util.Log.i(TAG, "Key Down: " + keyEvent);
                return true;
            }
            android.util.Log.i(TAG, "Key Repeat #" + repeatCount + ": " + keyEvent);
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (shouldLogKey(i)) {
            android.util.Log.i(TAG, "Key Up: " + keyEvent);
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    private static boolean shouldLogKey(int i) {
        switch (i) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                break;
            default:
                if (android.view.KeyEvent.isGamepadButton(i) || android.view.KeyEvent.isModifierKey(i)) {
                }
                break;
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTrackballEvent(android.view.MotionEvent motionEvent) {
        logMotionEvent("Trackball", motionEvent);
        return true;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIm.registerInputDeviceListener(this, getHandler());
        if (shouldShowSystemGestureExclusion()) {
            try {
                android.view.WindowManagerGlobal.getWindowManagerService().registerSystemGestureExclusionListener(this.mSystemGestureExclusionListener, this.mContext.getDisplayId());
                int systemGestureExclusionOpacity = systemGestureExclusionOpacity();
                this.mSystemGestureExclusionPaint.setAlpha(systemGestureExclusionOpacity);
                this.mSystemGestureExclusionRejectedPaint.setAlpha(systemGestureExclusionOpacity);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            this.mSystemGestureExclusion.setEmpty();
        }
        logInputDevices();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIm.unregisterInputDeviceListener(this);
        try {
            android.view.WindowManagerGlobal.getWindowManagerService().unregisterSystemGestureExclusionListener(this.mSystemGestureExclusionListener, this.mContext.getDisplayId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.lang.IllegalArgumentException e2) {
            android.util.Log.e(TAG, "Failed to unregister window manager callbacks", e2);
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceAdded(int i) {
        logInputDeviceState(i, "Device Added");
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int i) {
        logInputDeviceState(i, "Device Changed");
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceRemoved(int i) {
        logInputDeviceState(i, "Device Removed");
    }

    private void logInputDevices() {
        for (int i : android.view.InputDevice.getDeviceIds()) {
            logInputDeviceState(i, "Device Enumerated");
        }
    }

    private void logInputDeviceState(int i, java.lang.String str) {
        android.view.InputDevice inputDevice = this.mIm.getInputDevice(i);
        if (inputDevice != null) {
            android.util.Log.i(TAG, str + ": " + inputDevice);
        } else {
            android.util.Log.i(TAG, str + ": " + i);
        }
    }

    private static boolean shouldShowSystemGestureExclusion() {
        return systemGestureExclusionOpacity() > 0;
    }

    private static int systemGestureExclusionOpacity() {
        int i = android.os.SystemProperties.getInt(GESTURE_EXCLUSION_PROP, 0);
        if (i < 0 || i > 255) {
            return 0;
        }
        return i;
    }

    private static final class FasterStringBuilder {
        private char[] mChars = new char[64];
        private int mLength;

        public com.android.internal.widget.PointerLocationView.FasterStringBuilder clear() {
            this.mLength = 0;
            return this;
        }

        public com.android.internal.widget.PointerLocationView.FasterStringBuilder append(java.lang.String str) {
            int length = str.length();
            str.getChars(0, length, this.mChars, reserve(length));
            this.mLength += length;
            return this;
        }

        public com.android.internal.widget.PointerLocationView.FasterStringBuilder append(int i) {
            return append(i, 0);
        }

        public com.android.internal.widget.PointerLocationView.FasterStringBuilder append(int i, int i2) {
            boolean z = i < 0;
            if (z && (i = -i) < 0) {
                append("-2147483648");
                return this;
            }
            int reserve = reserve(11);
            char[] cArr = this.mChars;
            if (i == 0) {
                cArr[reserve] = '0';
                this.mLength++;
                return this;
            }
            if (z) {
                cArr[reserve] = '-';
                reserve++;
            }
            int i3 = 1000000000;
            int i4 = 10;
            while (i < i3) {
                i3 /= 10;
                i4--;
                if (i4 < i2) {
                    cArr[reserve] = '0';
                    reserve++;
                }
            }
            while (true) {
                int i5 = i / i3;
                i -= i5 * i3;
                i3 /= 10;
                int i6 = reserve + 1;
                cArr[reserve] = (char) (i5 + 48);
                if (i3 != 0) {
                    reserve = i6;
                } else {
                    this.mLength = i6;
                    return this;
                }
            }
        }

        public com.android.internal.widget.PointerLocationView.FasterStringBuilder append(float f, int i) {
            int i2 = 1;
            for (int i3 = 0; i3 < i; i3++) {
                i2 *= 10;
            }
            float f2 = i2;
            float rint = (float) (java.lang.Math.rint(f * f2) / i2);
            int i4 = (int) rint;
            if (i4 == 0 && rint < 0.0f) {
                append(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            }
            append(i4);
            if (i != 0) {
                append(android.media.MediaMetrics.SEPARATOR);
                double abs = java.lang.Math.abs(rint);
                append((int) (((float) (abs - java.lang.Math.floor(abs))) * f2), i);
            }
            return this;
        }

        public java.lang.String toString() {
            return new java.lang.String(this.mChars, 0, this.mLength);
        }

        private int reserve(int i) {
            int i2 = this.mLength;
            int i3 = this.mLength + i;
            char[] cArr = this.mChars;
            int length = cArr.length;
            if (i3 > length) {
                char[] cArr2 = new char[length * 2];
                java.lang.System.arraycopy(cArr, 0, cArr2, 0, i2);
                this.mChars = cArr2;
            }
            return i2;
        }
    }

    /* renamed from: com.android.internal.widget.PointerLocationView$1, reason: invalid class name */
    class AnonymousClass1 extends android.view.ISystemGestureExclusionListener.Stub {
        AnonymousClass1() {
        }

        @Override // android.view.ISystemGestureExclusionListener
        public void onSystemGestureExclusionChanged(int i, android.graphics.Region region, android.graphics.Region region2) {
            final android.graphics.Region obtain = android.graphics.Region.obtain(region);
            final android.graphics.Region obtain2 = android.graphics.Region.obtain();
            if (region2 != null) {
                obtain2.set(region2);
                obtain2.op(obtain, android.graphics.Region.Op.DIFFERENCE);
            }
            android.os.Handler handler = com.android.internal.widget.PointerLocationView.this.getHandler();
            if (handler != null) {
                handler.post(new java.lang.Runnable() { // from class: com.android.internal.widget.PointerLocationView$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.widget.PointerLocationView.AnonymousClass1.this.lambda$onSystemGestureExclusionChanged$0(obtain, obtain2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSystemGestureExclusionChanged$0(android.graphics.Region region, android.graphics.Region region2) {
            com.android.internal.widget.PointerLocationView.this.mSystemGestureExclusion.set(region);
            com.android.internal.widget.PointerLocationView.this.mSystemGestureExclusionRejected.set(region2);
            region.recycle();
            com.android.internal.widget.PointerLocationView.this.invalidate();
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        configureDensityDependentFactors();
    }

    private void configureDensityDependentFactors() {
        this.mDensity = getResources().getDisplayMetrics().density;
        this.mTextPaint.setTextSize(this.mDensity * 10.0f);
        this.mPaint.setStrokeWidth(this.mDensity * 1.0f);
        this.mCurrentPointPaint.setStrokeWidth(this.mDensity * 1.0f);
        this.mPathPaint.setStrokeWidth(this.mDensity * 1.0f);
    }
}
