package com.android.internal.widget;

/* loaded from: classes5.dex */
public class LockPatternView extends android.view.View {
    private static final int ALPHA_MAX_VALUE = 255;
    private static final int ASPECT_LOCK_HEIGHT = 2;
    private static final int ASPECT_LOCK_WIDTH = 1;
    private static final int ASPECT_SQUARE = 0;
    private static final int CELL_ACTIVATE = 0;
    private static final int CELL_DEACTIVATE = 1;
    public static final boolean DEBUG_A11Y = false;
    private static final int DOT_ACTIVATION_DURATION_MILLIS = 50;
    private static final int DOT_RADIUS_DECREASE_DURATION_MILLIS = 192;
    private static final int DOT_RADIUS_INCREASE_DURATION_MILLIS = 96;
    private static final float DRAG_THRESHHOLD = 0.0f;
    private static final int LINE_END_ANIMATION_DURATION_MILLIS = 50;
    private static final int MILLIS_PER_CIRCLE_ANIMATING = 700;
    private static final float MIN_DOT_HIT_FACTOR = 0.2f;
    private static final boolean PROFILE_DRAWING = false;
    private static final java.lang.String TAG = "LockPatternView";
    public static final int VIRTUAL_BASE_VIEW_ID = 1;
    private long mAnimatingPeriodStart;
    private int mAspect;
    private com.android.internal.widget.LockPatternView.CellState[][] mCellStates;
    private final android.graphics.Path mCurrentPath;
    private int mDotActivatedColor;
    private int mDotColor;
    private final float mDotHitFactor;
    private float mDotHitMaxRadius;
    private float mDotHitRadius;
    private final int mDotSize;
    private final int mDotSizeActivated;
    private boolean mDrawingProfilingStarted;
    private boolean mEnlargeVertex;
    private int mErrorColor;
    private final com.android.internal.widget.LockPatternView.PatternExploreByTouchHelper mExploreByTouchHelper;
    private int mFadeAnimationAlpha;
    private boolean mFadeClear;
    private final android.graphics.LinearGradient mFadeOutGradientShader;
    private boolean mFadePattern;
    private final int mFadePatternAnimationDelayMs;
    private final int mFadePatternAnimationDurationMs;
    private final android.view.animation.Interpolator mFastOutSlowInInterpolator;
    private float mInProgressX;
    private float mInProgressY;
    private boolean mInStealthMode;
    private boolean mInputEnabled;
    private final android.graphics.Rect mInvalidate;
    private boolean mKeepDotActivated;
    private final int mLineFadeOutAnimationDelayMs;
    private final int mLineFadeOutAnimationDurationMs;
    private long[] mLineFadeStart;
    private final android.view.animation.Interpolator mLinearOutSlowInInterpolator;
    private com.android.internal.widget.LockPatternUtils mLockPatternUtils;
    private android.graphics.drawable.Drawable mNotSelectedDrawable;
    private com.android.internal.widget.LockPatternView.OnPatternListener mOnPatternListener;
    private final android.graphics.Paint mPaint;
    private final android.graphics.Paint mPathPaint;
    private final int mPathWidth;
    private java.util.ArrayList<com.android.internal.widget.LockPatternView.Cell> mPattern;
    private com.android.internal.widget.LockPatternView.DisplayMode mPatternDisplayMode;
    private boolean[][] mPatternDrawLookup;
    private boolean mPatternInProgress;
    private final android.graphics.Path mPatternPath;
    private byte mPatternSize;
    private int mRegularColor;
    private android.graphics.drawable.Drawable mSelectedDrawable;
    private boolean mShowErrorPath;
    private float mSquareHeight;
    private float mSquareWidth;
    private final android.view.animation.Interpolator mStandardAccelerateInterpolator;
    private int mSuccessColor;
    private final android.graphics.Rect mTmpInvalidateRect;
    private boolean mUseLockPatternDrawable;
    private boolean mVisibleDots;

    public static class CellState {
        float activationAnimationProgress;
        android.animation.Animator activationAnimator;
        int col;
        boolean hwAnimating;
        android.graphics.CanvasProperty<java.lang.Float> hwCenterX;
        android.graphics.CanvasProperty<java.lang.Float> hwCenterY;
        android.graphics.CanvasProperty<android.graphics.Paint> hwPaint;
        android.graphics.CanvasProperty<java.lang.Float> hwRadius;
        float radius;
        int row;
        float translationY;
        float alpha = 1.0f;
        public float lineEndX = Float.MIN_VALUE;
        public float lineEndY = Float.MIN_VALUE;
    }

    public enum DisplayMode {
        Correct,
        Animate,
        Wrong
    }

    public interface OnPatternListener {
        void onPatternCellAdded(java.util.List<com.android.internal.widget.LockPatternView.Cell> list);

        void onPatternCleared();

        void onPatternDetected(java.util.List<com.android.internal.widget.LockPatternView.Cell> list, byte b);

        void onPatternStart();
    }

    public static final class Cell {
        static com.android.internal.widget.LockPatternView.Cell[][] sCells;
        final int column;
        final int row;

        static {
            updateSize((byte) 3);
        }

        private Cell(int i, int i2, byte b) {
            checkRange(i, i2, b);
            this.row = i;
            this.column = i2;
        }

        public int getRow() {
            return this.row;
        }

        public int getColumn() {
            return this.column;
        }

        public static com.android.internal.widget.LockPatternView.Cell of(int i, int i2, byte b) {
            checkRange(i, i2, b);
            return sCells[i][i2];
        }

        public static void updateSize(byte b) {
            sCells = (com.android.internal.widget.LockPatternView.Cell[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) com.android.internal.widget.LockPatternView.Cell.class, b, b);
            for (int i = 0; i < b; i++) {
                for (int i2 = 0; i2 < b; i2++) {
                    sCells[i][i2] = new com.android.internal.widget.LockPatternView.Cell(i, i2, b);
                }
            }
        }

        private static void checkRange(int i, int i2, byte b) {
            int i3;
            if (i < 0 || i > b - 1) {
                throw new java.lang.IllegalArgumentException("row must be in range 0-" + (b - 1));
            }
            if (i2 < 0 || i2 > i3) {
                throw new java.lang.IllegalArgumentException("column must be in range 0-" + i3);
            }
        }

        public java.lang.String toString() {
            return "(row=" + this.row + ",clmn=" + this.column + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public LockPatternView(android.content.Context context) {
        this(context, null);
    }

    public LockPatternView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDrawingProfilingStarted = false;
        this.mPaint = new android.graphics.Paint();
        this.mPathPaint = new android.graphics.Paint();
        this.mPatternSize = (byte) 3;
        this.mPattern = new java.util.ArrayList<>(this.mPatternSize * this.mPatternSize);
        this.mPatternDrawLookup = (boolean[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Boolean.TYPE, this.mPatternSize, this.mPatternSize);
        this.mInProgressX = -1.0f;
        this.mInProgressY = -1.0f;
        this.mLineFadeStart = new long[this.mPatternSize * this.mPatternSize];
        this.mPatternDisplayMode = com.android.internal.widget.LockPatternView.DisplayMode.Correct;
        this.mInputEnabled = true;
        this.mInStealthMode = false;
        this.mPatternInProgress = false;
        this.mFadePattern = true;
        this.mVisibleDots = true;
        this.mShowErrorPath = true;
        this.mFadeClear = false;
        this.mFadeAnimationAlpha = 255;
        this.mPatternPath = new android.graphics.Path();
        this.mCurrentPath = new android.graphics.Path();
        this.mInvalidate = new android.graphics.Rect();
        this.mTmpInvalidateRect = new android.graphics.Rect();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.LockPatternView, com.android.internal.R.attr.lockPatternStyle, com.android.internal.R.style.Widget_LockPatternView);
        java.lang.String string = obtainStyledAttributes.getString(0);
        if ("square".equals(string)) {
            this.mAspect = 0;
        } else if ("lock_width".equals(string)) {
            this.mAspect = 1;
        } else if ("lock_height".equals(string)) {
            this.mAspect = 2;
        } else {
            this.mAspect = 0;
        }
        setClickable(true);
        this.mPathPaint.setAntiAlias(true);
        this.mPathPaint.setDither(true);
        this.mRegularColor = obtainStyledAttributes.getColor(7, 0);
        this.mErrorColor = obtainStyledAttributes.getColor(4, 0);
        this.mSuccessColor = obtainStyledAttributes.getColor(8, 0);
        this.mDotColor = obtainStyledAttributes.getColor(2, this.mRegularColor);
        this.mDotActivatedColor = obtainStyledAttributes.getColor(1, this.mDotColor);
        this.mKeepDotActivated = obtainStyledAttributes.getBoolean(5, false);
        this.mEnlargeVertex = obtainStyledAttributes.getBoolean(3, false);
        int color = obtainStyledAttributes.getColor(6, this.mRegularColor);
        this.mPathPaint.setColor(color);
        this.mPathPaint.setStyle(android.graphics.Paint.Style.STROKE);
        this.mPathPaint.setStrokeJoin(android.graphics.Paint.Join.ROUND);
        this.mPathPaint.setStrokeCap(android.graphics.Paint.Cap.ROUND);
        this.mPathWidth = getResources().getDimensionPixelSize(com.android.internal.R.dimen.lock_pattern_dot_line_width);
        this.mPathPaint.setStrokeWidth(this.mPathWidth);
        this.mLineFadeOutAnimationDurationMs = getResources().getInteger(com.android.internal.R.integer.lock_pattern_line_fade_out_duration);
        this.mLineFadeOutAnimationDelayMs = getResources().getInteger(com.android.internal.R.integer.lock_pattern_line_fade_out_delay);
        this.mFadePatternAnimationDurationMs = getResources().getInteger(com.android.internal.R.integer.lock_pattern_fade_pattern_duration);
        this.mFadePatternAnimationDelayMs = getResources().getInteger(com.android.internal.R.integer.lock_pattern_fade_pattern_delay);
        this.mDotSize = getResources().getDimensionPixelSize(com.android.internal.R.dimen.lock_pattern_dot_size);
        this.mDotSizeActivated = getResources().getDimensionPixelSize(com.android.internal.R.dimen.lock_pattern_dot_size_activated);
        android.util.TypedValue typedValue = new android.util.TypedValue();
        getResources().getValue(com.android.internal.R.dimen.lock_pattern_dot_hit_factor, typedValue, true);
        this.mDotHitFactor = java.lang.Math.max(java.lang.Math.min(typedValue.getFloat(), 1.0f), 0.2f);
        this.mUseLockPatternDrawable = getResources().getBoolean(com.android.internal.R.bool.use_lock_pattern_drawable);
        if (this.mUseLockPatternDrawable) {
            this.mSelectedDrawable = getResources().getDrawable(com.android.internal.R.drawable.lockscreen_selected);
            this.mNotSelectedDrawable = getResources().getDrawable(com.android.internal.R.drawable.lockscreen_notselected);
        }
        this.mPaint.setAntiAlias(true);
        this.mPaint.setDither(true);
        this.mCellStates = (com.android.internal.widget.LockPatternView.CellState[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) com.android.internal.widget.LockPatternView.CellState.class, this.mPatternSize, this.mPatternSize);
        for (int i = 0; i < this.mPatternSize; i++) {
            for (int i2 = 0; i2 < this.mPatternSize; i2++) {
                this.mCellStates[i][i2] = new com.android.internal.widget.LockPatternView.CellState();
                this.mCellStates[i][i2].radius = this.mDotSize / 2;
                this.mCellStates[i][i2].row = i;
                this.mCellStates[i][i2].col = i2;
            }
        }
        this.mFastOutSlowInInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, 17563661);
        this.mLinearOutSlowInInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, 17563662);
        this.mStandardAccelerateInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, 17563663);
        this.mExploreByTouchHelper = new com.android.internal.widget.LockPatternView.PatternExploreByTouchHelper(this);
        setAccessibilityDelegate(this.mExploreByTouchHelper);
        this.mFadeOutGradientShader = new android.graphics.LinearGradient((-r1) / 2.0f, 0.0f, getResources().getDimensionPixelSize(com.android.internal.R.dimen.lock_pattern_fade_away_gradient_width) / 2.0f, 0.0f, 0, color, android.graphics.Shader.TileMode.CLAMP);
        obtainStyledAttributes.recycle();
    }

    public com.android.internal.widget.LockPatternView.CellState[][] getCellStates() {
        return this.mCellStates;
    }

    public boolean isInStealthMode() {
        return this.mInStealthMode;
    }

    public byte getLockPatternSize() {
        return this.mPatternSize;
    }

    public void setInStealthMode(boolean z) {
        this.mInStealthMode = z;
    }

    public void setVisibleDots(boolean z) {
        this.mVisibleDots = z;
    }

    public boolean isVisibleDots() {
        return this.mVisibleDots;
    }

    public void setShowErrorPath(boolean z) {
        this.mShowErrorPath = z;
    }

    public boolean isShowErrorPath() {
        return this.mShowErrorPath;
    }

    public void setFadePattern(boolean z) {
        this.mFadePattern = z;
    }

    public void setLockPatternSize(byte b) {
        this.mPatternSize = b;
        com.android.internal.widget.LockPatternView.Cell.updateSize(b);
        this.mCellStates = (com.android.internal.widget.LockPatternView.CellState[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) com.android.internal.widget.LockPatternView.CellState.class, this.mPatternSize, this.mPatternSize);
        for (int i = 0; i < this.mPatternSize; i++) {
            for (int i2 = 0; i2 < this.mPatternSize; i2++) {
                this.mCellStates[i][i2] = new com.android.internal.widget.LockPatternView.CellState();
                this.mCellStates[i][i2].radius = this.mDotSize / 2;
                this.mCellStates[i][i2].row = i;
                this.mCellStates[i][i2].col = i2;
            }
        }
        int i3 = b * b;
        this.mPattern = new java.util.ArrayList<>(i3);
        this.mLineFadeStart = new long[i3];
        this.mPatternDrawLookup = (boolean[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Boolean.TYPE, b, b);
    }

    public void setLockPatternUtils(com.android.internal.widget.LockPatternUtils lockPatternUtils) {
        this.mLockPatternUtils = lockPatternUtils;
    }

    public void setOnPatternListener(com.android.internal.widget.LockPatternView.OnPatternListener onPatternListener) {
        this.mOnPatternListener = onPatternListener;
    }

    public void setPattern(com.android.internal.widget.LockPatternView.DisplayMode displayMode, java.util.List<com.android.internal.widget.LockPatternView.Cell> list) {
        this.mPattern.clear();
        this.mPattern.addAll(list);
        clearPatternDrawLookup();
        for (com.android.internal.widget.LockPatternView.Cell cell : list) {
            this.mPatternDrawLookup[cell.getRow()][cell.getColumn()] = true;
        }
        setDisplayMode(displayMode);
    }

    public void setDisplayMode(com.android.internal.widget.LockPatternView.DisplayMode displayMode) {
        this.mPatternDisplayMode = displayMode;
        if (displayMode == com.android.internal.widget.LockPatternView.DisplayMode.Animate) {
            if (this.mPattern.size() == 0) {
                throw new java.lang.IllegalStateException("you must have a pattern to animate if you want to set the display mode to animate");
            }
            this.mAnimatingPeriodStart = android.os.SystemClock.elapsedRealtime();
            com.android.internal.widget.LockPatternView.Cell cell = this.mPattern.get(0);
            this.mInProgressX = getCenterXForColumn(cell.getColumn());
            this.mInProgressY = getCenterYForRow(cell.getRow());
            clearPatternDrawLookup();
        }
        invalidate();
    }

    public void startCellStateAnimation(com.android.internal.widget.LockPatternView.CellState cellState, float f, float f2, float f3, float f4, float f5, float f6, long j, long j2, android.view.animation.Interpolator interpolator, java.lang.Runnable runnable) {
        if (isHardwareAccelerated()) {
            startCellStateAnimationHw(cellState, f, f2, f3, f4, f5, f6, j, j2, interpolator, runnable);
        } else {
            startCellStateAnimationSw(cellState, f, f2, f3, f4, f5, f6, j, j2, interpolator, runnable);
        }
    }

    private void startCellStateAnimationSw(final com.android.internal.widget.LockPatternView.CellState cellState, final float f, final float f2, final float f3, final float f4, final float f5, final float f6, long j, long j2, android.view.animation.Interpolator interpolator, final java.lang.Runnable runnable) {
        cellState.alpha = f;
        cellState.translationY = f3;
        cellState.radius = (this.mDotSize / 2) * f5;
        android.animation.ValueAnimator ofFloat = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(j2);
        ofFloat.setStartDelay(j);
        ofFloat.setInterpolator(interpolator);
        ofFloat.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.LockPatternView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                float floatValue = ((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue();
                float f7 = 1.0f - floatValue;
                cellState.alpha = (f * f7) + (f2 * floatValue);
                cellState.translationY = (f3 * f7) + (f4 * floatValue);
                cellState.radius = (com.android.internal.widget.LockPatternView.this.mDotSize / 2) * ((f7 * f5) + (floatValue * f6));
                com.android.internal.widget.LockPatternView.this.invalidate();
            }
        });
        ofFloat.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.LockPatternView.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        ofFloat.start();
    }

    private void startCellStateAnimationHw(final com.android.internal.widget.LockPatternView.CellState cellState, float f, float f2, float f3, float f4, float f5, float f6, long j, long j2, android.view.animation.Interpolator interpolator, final java.lang.Runnable runnable) {
        cellState.alpha = f2;
        cellState.translationY = f4;
        cellState.radius = (this.mDotSize / 2) * f6;
        cellState.hwAnimating = true;
        cellState.hwCenterY = android.graphics.CanvasProperty.createFloat(getCenterYForRow(cellState.row) + f3);
        cellState.hwCenterX = android.graphics.CanvasProperty.createFloat(getCenterXForColumn(cellState.col));
        cellState.hwRadius = android.graphics.CanvasProperty.createFloat((this.mDotSize / 2) * f5);
        this.mPaint.setColor(getDotColor());
        this.mPaint.setAlpha((int) (255.0f * f));
        cellState.hwPaint = android.graphics.CanvasProperty.createPaint(new android.graphics.Paint(this.mPaint));
        startRtFloatAnimation(cellState.hwCenterY, getCenterYForRow(cellState.row) + f4, j, j2, interpolator);
        startRtFloatAnimation(cellState.hwRadius, (this.mDotSize / 2) * f6, j, j2, interpolator);
        startRtAlphaAnimation(cellState, f2, j, j2, interpolator, new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.LockPatternView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                cellState.hwAnimating = false;
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        invalidate();
    }

    private void startRtAlphaAnimation(com.android.internal.widget.LockPatternView.CellState cellState, float f, long j, long j2, android.view.animation.Interpolator interpolator, android.animation.Animator.AnimatorListener animatorListener) {
        android.view.RenderNodeAnimator renderNodeAnimator = new android.view.RenderNodeAnimator(cellState.hwPaint, 1, (int) (f * 255.0f));
        renderNodeAnimator.setDuration(j2);
        renderNodeAnimator.setStartDelay(j);
        renderNodeAnimator.setInterpolator(interpolator);
        renderNodeAnimator.setTarget((android.view.View) this);
        renderNodeAnimator.addListener(animatorListener);
        renderNodeAnimator.start();
    }

    private void startRtFloatAnimation(android.graphics.CanvasProperty<java.lang.Float> canvasProperty, float f, long j, long j2, android.view.animation.Interpolator interpolator) {
        android.view.RenderNodeAnimator renderNodeAnimator = new android.view.RenderNodeAnimator(canvasProperty, f);
        renderNodeAnimator.setDuration(j2);
        renderNodeAnimator.setStartDelay(j);
        renderNodeAnimator.setInterpolator(interpolator);
        renderNodeAnimator.setTarget((android.view.View) this);
        renderNodeAnimator.start();
    }

    private void notifyCellAdded() {
        if (this.mOnPatternListener != null) {
            this.mOnPatternListener.onPatternCellAdded(this.mPattern);
        }
        this.mExploreByTouchHelper.invalidateRoot();
    }

    private void notifyPatternStarted() {
        sendAccessEvent(com.android.internal.R.string.lockscreen_access_pattern_start);
        if (this.mOnPatternListener != null) {
            this.mOnPatternListener.onPatternStart();
        }
    }

    private void notifyPatternDetected() {
        sendAccessEvent(com.android.internal.R.string.lockscreen_access_pattern_detected);
        if (this.mOnPatternListener != null) {
            this.mOnPatternListener.onPatternDetected(this.mPattern, this.mPatternSize);
        }
    }

    private void notifyPatternCleared() {
        sendAccessEvent(com.android.internal.R.string.lockscreen_access_pattern_cleared);
        if (this.mOnPatternListener != null) {
            this.mOnPatternListener.onPatternCleared();
        }
    }

    public void clearPattern() {
        resetPattern();
    }

    public void fadeClearPattern() {
        this.mFadeClear = true;
        startFadePatternAnimation();
    }

    @Override // android.view.View
    protected boolean dispatchHoverEvent(android.view.MotionEvent motionEvent) {
        return this.mExploreByTouchHelper.dispatchHoverEvent(motionEvent) | super.dispatchHoverEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetPattern() {
        if (this.mKeepDotActivated && !this.mPattern.isEmpty()) {
            resetLastActivatedCellProgress();
        }
        this.mPattern.clear();
        this.mPatternPath.reset();
        clearPatternDrawLookup();
        this.mPatternDisplayMode = com.android.internal.widget.LockPatternView.DisplayMode.Correct;
        invalidate();
    }

    private void resetLastActivatedCellProgress() {
        com.android.internal.widget.LockPatternView.Cell cell = this.mPattern.get(r0.size() - 1);
        com.android.internal.widget.LockPatternView.CellState cellState = this.mCellStates[cell.row][cell.column];
        if (cellState.activationAnimator != null) {
            cellState.activationAnimator.cancel();
        }
        cellState.activationAnimationProgress = 0.0f;
    }

    public boolean isEmpty() {
        return this.mPattern.isEmpty();
    }

    private void clearPatternDrawLookup() {
        for (int i = 0; i < this.mPatternSize; i++) {
            for (int i2 = 0; i2 < this.mPatternSize; i2++) {
                this.mPatternDrawLookup[i][i2] = false;
                this.mLineFadeStart[(this.mPatternSize * i) + i2] = 0;
            }
        }
    }

    public void disableInput() {
        this.mInputEnabled = false;
    }

    public void enableInput() {
        this.mInputEnabled = true;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        int i5 = (i - this.mPaddingLeft) - this.mPaddingRight;
        this.mSquareWidth = i5 / this.mPatternSize;
        int i6 = (i2 - this.mPaddingTop) - this.mPaddingBottom;
        this.mSquareHeight = i6 / this.mPatternSize;
        this.mExploreByTouchHelper.invalidateRoot();
        this.mDotHitMaxRadius = java.lang.Math.min(this.mSquareHeight / 2.0f, this.mSquareWidth / 2.0f);
        this.mDotHitRadius = this.mDotHitMaxRadius * this.mDotHitFactor;
        if (this.mUseLockPatternDrawable) {
            this.mNotSelectedDrawable.setBounds(this.mPaddingLeft, this.mPaddingTop, i5, i6);
            this.mSelectedDrawable.setBounds(this.mPaddingLeft, this.mPaddingTop, i5, i6);
        }
    }

    private int resolveMeasured(int i, int i2) {
        int size = android.view.View.MeasureSpec.getSize(i);
        switch (android.view.View.MeasureSpec.getMode(i)) {
            case Integer.MIN_VALUE:
                return java.lang.Math.max(size, i2);
            case 0:
                return i2;
            default:
                return size;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int suggestedMinimumWidth = getSuggestedMinimumWidth();
        int suggestedMinimumHeight = getSuggestedMinimumHeight();
        int resolveMeasured = resolveMeasured(i, suggestedMinimumWidth);
        int resolveMeasured2 = resolveMeasured(i2, suggestedMinimumHeight);
        switch (this.mAspect) {
            case 0:
                resolveMeasured = java.lang.Math.min(resolveMeasured, resolveMeasured2);
                resolveMeasured2 = resolveMeasured;
                break;
            case 1:
                resolveMeasured2 = java.lang.Math.min(resolveMeasured, resolveMeasured2);
                break;
            case 2:
                resolveMeasured = java.lang.Math.min(resolveMeasured, resolveMeasured2);
                break;
        }
        setMeasuredDimension(resolveMeasured, resolveMeasured2);
    }

    private com.android.internal.widget.LockPatternView.Cell detectAndAddHit(float f, float f2) {
        com.android.internal.widget.LockPatternView.Cell checkForNewHit = checkForNewHit(f, f2);
        if (checkForNewHit != null) {
            java.util.ArrayList<com.android.internal.widget.LockPatternView.Cell> arrayList = this.mPattern;
            if (!arrayList.isEmpty()) {
                com.android.internal.widget.LockPatternView.Cell cell = arrayList.get(arrayList.size() - 1);
                int i = checkForNewHit.row - cell.row;
                int i2 = checkForNewHit.column - cell.column;
                int i3 = cell.row;
                int i4 = cell.column;
                if (i == 0 || i2 == 0 || java.lang.Math.abs(i) == java.lang.Math.abs(i2)) {
                    while (true) {
                        i3 += java.lang.Integer.signum(i);
                        i4 += java.lang.Integer.signum(i2);
                        if (i3 == checkForNewHit.row && i4 == checkForNewHit.column) {
                            break;
                        }
                        com.android.internal.widget.LockPatternView.Cell of = com.android.internal.widget.LockPatternView.Cell.of(i3, i4, this.mPatternSize);
                        if (!this.mPatternDrawLookup[of.row][of.column]) {
                            addCellToPattern(of);
                        }
                    }
                }
            }
            addCellToPattern(checkForNewHit);
            performHapticFeedback(1, 1);
            return checkForNewHit;
        }
        return null;
    }

    private void addCellToPattern(com.android.internal.widget.LockPatternView.Cell cell) {
        this.mPatternDrawLookup[cell.getRow()][cell.getColumn()] = true;
        this.mPattern.add(cell);
        if (!this.mInStealthMode) {
            startCellActivatedAnimation(cell);
        }
        notifyCellAdded();
    }

    private void startFadePatternAnimation() {
        android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
        animatorSet.play(createFadePatternAnimation());
        animatorSet.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.LockPatternView.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                com.android.internal.widget.LockPatternView.this.mFadeAnimationAlpha = 255;
                com.android.internal.widget.LockPatternView.this.mFadeClear = false;
                com.android.internal.widget.LockPatternView.this.resetPattern();
            }
        });
        animatorSet.start();
    }

    private android.animation.Animator createFadePatternAnimation() {
        android.animation.ValueAnimator ofInt = android.animation.ValueAnimator.ofInt(255, 0);
        ofInt.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                com.android.internal.widget.LockPatternView.this.lambda$createFadePatternAnimation$0(valueAnimator);
            }
        });
        ofInt.setInterpolator(this.mStandardAccelerateInterpolator);
        ofInt.setStartDelay(this.mFadePatternAnimationDelayMs);
        ofInt.setDuration(this.mFadePatternAnimationDurationMs);
        return ofInt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createFadePatternAnimation$0(android.animation.ValueAnimator valueAnimator) {
        this.mFadeAnimationAlpha = ((java.lang.Integer) valueAnimator.getAnimatedValue()).intValue();
        invalidate();
    }

    private void startCellActivatedAnimation(com.android.internal.widget.LockPatternView.Cell cell) {
        startCellActivationAnimation(cell, 0);
    }

    private void startCellDeactivatedAnimation(com.android.internal.widget.LockPatternView.Cell cell) {
        startCellActivationAnimation(cell, 1);
    }

    private void startCellActivationAnimation(com.android.internal.widget.LockPatternView.Cell cell, int i) {
        final com.android.internal.widget.LockPatternView.CellState cellState = this.mCellStates[cell.row][cell.column];
        if (cellState.activationAnimator != null) {
            cellState.activationAnimator.cancel();
        }
        android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
        android.animation.AnimatorSet.Builder with = animatorSet.play(createLineDisappearingAnimation()).with(createLineEndAnimation(cellState, this.mInProgressX, this.mInProgressY, getCenterXForColumn(cell.column), getCenterYForRow(cell.row)));
        if (this.mDotSize != this.mDotSizeActivated) {
            with.with(createDotRadiusAnimation(cellState));
        }
        if (this.mDotColor != this.mDotActivatedColor) {
            with.with(createDotActivationColorAnimation(cellState, i));
        }
        animatorSet.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.LockPatternView.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                cellState.activationAnimator = null;
                com.android.internal.widget.LockPatternView.this.invalidate();
            }
        });
        cellState.activationAnimator = animatorSet;
        animatorSet.start();
    }

    private android.animation.Animator createDotActivationColorAnimation(final com.android.internal.widget.LockPatternView.CellState cellState, int i) {
        android.animation.ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                com.android.internal.widget.LockPatternView.this.lambda$createDotActivationColorAnimation$1(cellState, valueAnimator);
            }
        };
        android.animation.ValueAnimator ofFloat = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
        android.animation.ValueAnimator ofFloat2 = android.animation.ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.addUpdateListener(animatorUpdateListener);
        ofFloat2.addUpdateListener(animatorUpdateListener);
        ofFloat.setInterpolator(this.mFastOutSlowInInterpolator);
        ofFloat2.setInterpolator(this.mLinearOutSlowInInterpolator);
        ofFloat.setDuration(50L);
        ofFloat2.setDuration(50L);
        android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
        if (this.mKeepDotActivated) {
            if (i != 0) {
                ofFloat = ofFloat2;
            }
            animatorSet.play(ofFloat);
        } else {
            animatorSet.play(ofFloat2).after((this.mLineFadeOutAnimationDelayMs + this.mLineFadeOutAnimationDurationMs) - 100).after(ofFloat);
        }
        return animatorSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createDotActivationColorAnimation$1(com.android.internal.widget.LockPatternView.CellState cellState, android.animation.ValueAnimator valueAnimator) {
        cellState.activationAnimationProgress = ((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    private android.animation.Animator createLineEndAnimation(final com.android.internal.widget.LockPatternView.CellState cellState, final float f, final float f2, final float f3, final float f4) {
        android.animation.ValueAnimator ofFloat = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                com.android.internal.widget.LockPatternView.this.lambda$createLineEndAnimation$2(cellState, f, f3, f2, f4, valueAnimator);
            }
        });
        ofFloat.setInterpolator(this.mFastOutSlowInInterpolator);
        ofFloat.setDuration(50L);
        return ofFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createLineEndAnimation$2(com.android.internal.widget.LockPatternView.CellState cellState, float f, float f2, float f3, float f4, android.animation.ValueAnimator valueAnimator) {
        float floatValue = ((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue();
        float f5 = 1.0f - floatValue;
        cellState.lineEndX = (f * f5) + (f2 * floatValue);
        cellState.lineEndY = (f5 * f3) + (floatValue * f4);
        invalidate();
    }

    private android.animation.Animator createLineDisappearingAnimation() {
        android.animation.ValueAnimator ofFloat = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                com.android.internal.widget.LockPatternView.this.lambda$createLineDisappearingAnimation$3(valueAnimator);
            }
        });
        ofFloat.setStartDelay(this.mLineFadeOutAnimationDelayMs);
        ofFloat.setDuration(this.mLineFadeOutAnimationDurationMs);
        return ofFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createLineDisappearingAnimation$3(android.animation.ValueAnimator valueAnimator) {
        invalidate();
    }

    private android.animation.Animator createDotRadiusAnimation(final com.android.internal.widget.LockPatternView.CellState cellState) {
        float f = this.mDotSize / 2.0f;
        float f2 = this.mDotSizeActivated / 2.0f;
        android.animation.ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.LockPatternView$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                com.android.internal.widget.LockPatternView.this.lambda$createDotRadiusAnimation$4(cellState, valueAnimator);
            }
        };
        android.animation.ValueAnimator ofFloat = android.animation.ValueAnimator.ofFloat(f, f2);
        ofFloat.addUpdateListener(animatorUpdateListener);
        ofFloat.setInterpolator(this.mLinearOutSlowInInterpolator);
        ofFloat.setDuration(96L);
        android.animation.ValueAnimator ofFloat2 = android.animation.ValueAnimator.ofFloat(f2, f);
        ofFloat2.addUpdateListener(animatorUpdateListener);
        ofFloat2.setInterpolator(this.mFastOutSlowInInterpolator);
        ofFloat2.setDuration(192L);
        android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
        animatorSet.playSequentially(ofFloat, ofFloat2);
        return animatorSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createDotRadiusAnimation$4(com.android.internal.widget.LockPatternView.CellState cellState, android.animation.ValueAnimator valueAnimator) {
        cellState.radius = ((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    private com.android.internal.widget.LockPatternView.Cell checkForNewHit(float f, float f2) {
        com.android.internal.widget.LockPatternView.Cell detectCellHit = detectCellHit(f, f2);
        if (detectCellHit != null && !this.mPatternDrawLookup[detectCellHit.row][detectCellHit.column]) {
            return detectCellHit;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.internal.widget.LockPatternView.Cell detectCellHit(float f, float f2) {
        float f3;
        for (int i = 0; i < this.mPatternSize; i++) {
            for (int i2 = 0; i2 < this.mPatternSize; i2++) {
                float centerYForRow = getCenterYForRow(i);
                float centerXForColumn = getCenterXForColumn(i2);
                if (this.mEnlargeVertex) {
                    if (isVertex(i, i2)) {
                        f3 = this.mDotHitMaxRadius * this.mDotHitMaxRadius;
                    } else {
                        f3 = this.mDotHitRadius * this.mDotHitRadius;
                    }
                } else {
                    f3 = this.mDotHitRadius * this.mDotHitRadius;
                }
                float f4 = f - centerXForColumn;
                float f5 = f2 - centerYForRow;
                if ((f4 * f4) + (f5 * f5) < f3) {
                    return com.android.internal.widget.LockPatternView.Cell.of(i, i2, this.mPatternSize);
                }
            }
        }
        return null;
    }

    private boolean isVertex(int i, int i2) {
        return (i == 1 || i2 == 1) ? false : true;
    }

    @Override // android.view.View
    public boolean onHoverEvent(android.view.MotionEvent motionEvent) {
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isTouchExplorationEnabled()) {
            int action = motionEvent.getAction();
            switch (action) {
                case 7:
                    motionEvent.setAction(2);
                    break;
                case 9:
                    motionEvent.setAction(0);
                    break;
                case 10:
                    motionEvent.setAction(1);
                    break;
            }
            onTouchEvent(motionEvent);
            motionEvent.setAction(action);
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        if (!this.mInputEnabled || !isEnabled()) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                handleActionDown(motionEvent);
                return true;
            case 1:
                handleActionUp();
                return true;
            case 2:
                handleActionMove(motionEvent);
                return true;
            case 3:
                if (this.mPatternInProgress) {
                    setPatternInProgress(false);
                    resetPattern();
                    notifyPatternCleared();
                }
                return true;
            default:
                return false;
        }
    }

    private void setPatternInProgress(boolean z) {
        this.mPatternInProgress = z;
        this.mExploreByTouchHelper.invalidateRoot();
    }

    private void handleActionMove(android.view.MotionEvent motionEvent) {
        float f = this.mPathWidth;
        int historySize = motionEvent.getHistorySize();
        this.mTmpInvalidateRect.setEmpty();
        int i = 0;
        boolean z = false;
        while (i < historySize + 1) {
            float historicalX = i < historySize ? motionEvent.getHistoricalX(i) : motionEvent.getX();
            float historicalY = i < historySize ? motionEvent.getHistoricalY(i) : motionEvent.getY();
            com.android.internal.widget.LockPatternView.Cell detectAndAddHit = detectAndAddHit(historicalX, historicalY);
            int size = this.mPattern.size();
            if (detectAndAddHit != null && size == 1) {
                setPatternInProgress(true);
                notifyPatternStarted();
            }
            float abs = java.lang.Math.abs(historicalX - this.mInProgressX);
            float abs2 = java.lang.Math.abs(historicalY - this.mInProgressY);
            if (abs > 0.0f || abs2 > 0.0f) {
                z = true;
            }
            if (this.mPatternInProgress && size > 0) {
                com.android.internal.widget.LockPatternView.Cell cell = this.mPattern.get(size - 1);
                float centerXForColumn = getCenterXForColumn(cell.column);
                float centerYForRow = getCenterYForRow(cell.row);
                float min = java.lang.Math.min(centerXForColumn, historicalX) - f;
                float max = java.lang.Math.max(centerXForColumn, historicalX) + f;
                float min2 = java.lang.Math.min(centerYForRow, historicalY) - f;
                float max2 = java.lang.Math.max(centerYForRow, historicalY) + f;
                if (detectAndAddHit != null) {
                    float f2 = this.mSquareWidth * 0.5f;
                    float f3 = this.mSquareHeight * 0.5f;
                    float centerXForColumn2 = getCenterXForColumn(detectAndAddHit.column);
                    float centerYForRow2 = getCenterYForRow(detectAndAddHit.row);
                    min = java.lang.Math.min(centerXForColumn2 - f2, min);
                    max = java.lang.Math.max(centerXForColumn2 + f2, max);
                    min2 = java.lang.Math.min(centerYForRow2 - f3, min2);
                    max2 = java.lang.Math.max(centerYForRow2 + f3, max2);
                }
                this.mTmpInvalidateRect.union(java.lang.Math.round(min), java.lang.Math.round(min2), java.lang.Math.round(max), java.lang.Math.round(max2));
            }
            i++;
        }
        this.mInProgressX = motionEvent.getX();
        this.mInProgressY = motionEvent.getY();
        if (z) {
            this.mInvalidate.union(this.mTmpInvalidateRect);
            invalidate(this.mInvalidate);
            this.mInvalidate.set(this.mTmpInvalidateRect);
        }
    }

    private void sendAccessEvent(int i) {
        announceForAccessibility(this.mContext.getString(i));
    }

    private void handleActionUp() {
        if (!this.mPattern.isEmpty()) {
            setPatternInProgress(false);
            cancelLineAnimations();
            if (this.mKeepDotActivated) {
                deactivateLastCell();
            }
            notifyPatternDetected();
            invalidate();
        }
    }

    private void deactivateLastCell() {
        startCellDeactivatedAnimation(this.mPattern.get(this.mPattern.size() - 1));
    }

    private void cancelLineAnimations() {
        for (int i = 0; i < this.mPatternSize; i++) {
            for (int i2 = 0; i2 < this.mPatternSize; i2++) {
                com.android.internal.widget.LockPatternView.CellState cellState = this.mCellStates[i][i2];
                if (cellState.activationAnimator != null) {
                    cellState.activationAnimator.cancel();
                    cellState.activationAnimator = null;
                    cellState.radius = this.mDotSize / 2.0f;
                    cellState.lineEndX = Float.MIN_VALUE;
                    cellState.lineEndY = Float.MIN_VALUE;
                    cellState.activationAnimationProgress = 0.0f;
                }
            }
        }
    }

    private void handleActionDown(android.view.MotionEvent motionEvent) {
        resetPattern();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        com.android.internal.widget.LockPatternView.Cell detectAndAddHit = detectAndAddHit(x, y);
        if (detectAndAddHit != null) {
            setPatternInProgress(true);
            this.mPatternDisplayMode = com.android.internal.widget.LockPatternView.DisplayMode.Correct;
            notifyPatternStarted();
        } else if (this.mPatternInProgress) {
            setPatternInProgress(false);
            notifyPatternCleared();
        }
        if (detectAndAddHit != null) {
            float centerXForColumn = getCenterXForColumn(detectAndAddHit.column);
            float centerYForRow = getCenterYForRow(detectAndAddHit.row);
            float f = this.mSquareWidth / 2.0f;
            float f2 = this.mSquareHeight / 2.0f;
            invalidate((int) (centerXForColumn - f), (int) (centerYForRow - f2), (int) (centerXForColumn + f), (int) (centerYForRow + f2));
        }
        this.mInProgressX = x;
        this.mInProgressY = y;
    }

    public void setColors(int i, int i2, int i3) {
        this.mRegularColor = i;
        this.mErrorColor = i3;
        this.mSuccessColor = i2;
        this.mPathPaint.setColor(i);
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getCenterXForColumn(int i) {
        return this.mPaddingLeft + (i * this.mSquareWidth) + (this.mSquareWidth / 2.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getCenterYForRow(int i) {
        return this.mPaddingTop + (i * this.mSquareHeight) + (this.mSquareHeight / 2.0f);
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        boolean[][] zArr;
        float f;
        android.graphics.Path path;
        float f2;
        float f3;
        int i;
        boolean z;
        android.graphics.Path path2;
        java.util.ArrayList<com.android.internal.widget.LockPatternView.Cell> arrayList;
        int i2;
        boolean[][] zArr2;
        float f4;
        float f5;
        float f6;
        java.util.ArrayList<com.android.internal.widget.LockPatternView.Cell> arrayList2 = this.mPattern;
        int size = arrayList2.size();
        boolean[][] zArr3 = this.mPatternDrawLookup;
        boolean z2 = true;
        if (this.mPatternDisplayMode == com.android.internal.widget.LockPatternView.DisplayMode.Animate) {
            int elapsedRealtime = (((int) (android.os.SystemClock.elapsedRealtime() - this.mAnimatingPeriodStart)) % ((size + 1) * 700)) / 700;
            clearPatternDrawLookup();
            for (int i3 = 0; i3 < elapsedRealtime; i3++) {
                com.android.internal.widget.LockPatternView.Cell cell = arrayList2.get(i3);
                zArr3[cell.getRow()][cell.getColumn()] = true;
            }
            if (elapsedRealtime > 0 && elapsedRealtime < size) {
                float f7 = (r1 % 700) / 700.0f;
                com.android.internal.widget.LockPatternView.Cell cell2 = arrayList2.get(elapsedRealtime - 1);
                float centerXForColumn = getCenterXForColumn(cell2.column);
                float centerYForRow = getCenterYForRow(cell2.row);
                com.android.internal.widget.LockPatternView.Cell cell3 = arrayList2.get(elapsedRealtime);
                float centerXForColumn2 = (getCenterXForColumn(cell3.column) - centerXForColumn) * f7;
                float centerYForRow2 = f7 * (getCenterYForRow(cell3.row) - centerYForRow);
                this.mInProgressX = centerXForColumn + centerXForColumn2;
                this.mInProgressY = centerYForRow + centerYForRow2;
            }
            invalidate();
        }
        android.graphics.Path path3 = this.mCurrentPath;
        path3.rewind();
        if ((!(this.mInStealthMode || this.mPatternDisplayMode == com.android.internal.widget.LockPatternView.DisplayMode.Wrong) || (this.mPatternDisplayMode == com.android.internal.widget.LockPatternView.DisplayMode.Wrong && this.mShowErrorPath)) && !this.mFadeClear) {
            this.mPathPaint.setColor(getCurrentColor(true));
            long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
            float f8 = 0.0f;
            float f9 = 0.0f;
            boolean z3 = false;
            int i4 = 0;
            while (true) {
                if (i4 >= size) {
                    f = f8;
                    path = path3;
                    zArr = zArr3;
                    f2 = f9;
                    break;
                }
                com.android.internal.widget.LockPatternView.Cell cell4 = arrayList2.get(i4);
                if (zArr3[cell4.row][cell4.column]) {
                    if (this.mLineFadeStart[i4] == 0) {
                        this.mLineFadeStart[i4] = android.os.SystemClock.elapsedRealtime();
                    }
                    float centerXForColumn3 = getCenterXForColumn(cell4.column);
                    float centerYForRow3 = getCenterYForRow(cell4.row);
                    if (i4 == 0) {
                        f3 = centerYForRow3;
                        i = i4;
                        z = z2;
                        path2 = path3;
                        arrayList = arrayList2;
                        i2 = size;
                        zArr2 = zArr3;
                        f4 = centerXForColumn3;
                    } else {
                        com.android.internal.widget.LockPatternView.CellState cellState = this.mCellStates[cell4.row][cell4.column];
                        path3.rewind();
                        if (cellState.lineEndX != Float.MIN_VALUE && cellState.lineEndY != Float.MIN_VALUE) {
                            float f10 = cellState.lineEndX;
                            f5 = cellState.lineEndY;
                            f6 = f10;
                        } else {
                            f5 = centerYForRow3;
                            f6 = centerXForColumn3;
                        }
                        long j = this.mLineFadeStart[i4];
                        f3 = centerYForRow3;
                        arrayList = arrayList2;
                        f4 = centerXForColumn3;
                        float f11 = f6;
                        i = i4;
                        float f12 = f5;
                        i2 = size;
                        zArr2 = zArr3;
                        z = z2;
                        path2 = path3;
                        drawLineSegment(canvas, f8, f9, f11, f12, j, elapsedRealtime2);
                        android.graphics.Path path4 = new android.graphics.Path();
                        path4.moveTo(f8, f9);
                        path4.lineTo(f4, f3);
                        this.mPatternPath.addPath(path4);
                    }
                    i4 = i + 1;
                    f8 = f4;
                    f9 = f3;
                    size = i2;
                    z3 = z;
                    z2 = z3;
                    path3 = path2;
                    arrayList2 = arrayList;
                    zArr3 = zArr2;
                } else {
                    f = f8;
                    path = path3;
                    zArr = zArr3;
                    f2 = f9;
                    break;
                }
            }
            if ((this.mPatternInProgress || this.mPatternDisplayMode == com.android.internal.widget.LockPatternView.DisplayMode.Animate) && z3) {
                path.rewind();
                android.graphics.Path path5 = path;
                path5.moveTo(f, f2);
                path5.lineTo(this.mInProgressX, this.mInProgressY);
                this.mPathPaint.setAlpha((int) (calculateLastSegmentAlpha(this.mInProgressX, this.mInProgressY, f, f2) * 255.0f));
                canvas.drawPath(path5, this.mPathPaint);
            }
        } else {
            zArr = zArr3;
        }
        if (this.mFadeClear) {
            this.mPathPaint.setAlpha(this.mFadeAnimationAlpha);
            canvas.drawPath(this.mPatternPath, this.mPathPaint);
        }
        if (this.mVisibleDots) {
            for (int i5 = 0; i5 < this.mPatternSize; i5++) {
                float centerYForRow4 = getCenterYForRow(i5);
                for (int i6 = 0; i6 < this.mPatternSize; i6++) {
                    com.android.internal.widget.LockPatternView.CellState cellState2 = this.mCellStates[i5][i6];
                    float centerXForColumn4 = getCenterXForColumn(i6);
                    float f13 = cellState2.translationY;
                    if (this.mUseLockPatternDrawable) {
                        drawCellDrawable(canvas, i5, i6, cellState2.radius, zArr[i5][i6]);
                    } else if (isHardwareAccelerated() && cellState2.hwAnimating) {
                        ((android.graphics.RecordingCanvas) canvas).drawCircle(cellState2.hwCenterX, cellState2.hwCenterY, cellState2.hwRadius, cellState2.hwPaint);
                    } else {
                        drawCircle(canvas, (int) centerXForColumn4, ((int) centerYForRow4) + f13, cellState2.radius, zArr[i5][i6], cellState2.alpha, cellState2.activationAnimationProgress);
                    }
                }
            }
        }
    }

    private void drawLineSegment(android.graphics.Canvas canvas, float f, float f2, float f3, float f4, long j, long j2) {
        boolean z = this.mPatternDisplayMode == com.android.internal.widget.LockPatternView.DisplayMode.Wrong && this.mShowErrorPath;
        if (this.mFadePattern && !z) {
            if (j2 - j >= this.mLineFadeOutAnimationDelayMs + this.mLineFadeOutAnimationDurationMs) {
                return;
            }
            drawFadingAwayLineSegment(canvas, f, f2, f3, f4, java.lang.Math.max((r1 - this.mLineFadeOutAnimationDelayMs) / this.mLineFadeOutAnimationDurationMs, 0.0f));
            return;
        }
        this.mPathPaint.setAlpha(255);
        canvas.drawLine(f, f2, f3, f4, this.mPathPaint);
    }

    private void drawFadingAwayLineSegment(android.graphics.Canvas canvas, float f, float f2, float f3, float f4, float f5) {
        float f6 = 1.0f - f5;
        this.mPathPaint.setAlpha((int) (255.0f * f6));
        this.mPathPaint.setShader(this.mFadeOutGradientShader);
        canvas.save();
        canvas.translate((f3 * f5) + (f * f6), (f4 * f5) + (f2 * f6));
        float f7 = f4 - f2;
        float f8 = f3 - f;
        float degrees = (float) java.lang.Math.toDegrees(java.lang.Math.atan(f7 / f8));
        if (f8 < 0.0f) {
            degrees += 180.0f;
        }
        canvas.rotate(degrees);
        float hypot = (float) java.lang.Math.hypot(f8, f7);
        canvas.drawLine((-hypot) * f5, 0.0f, hypot * f6, 0.0f, this.mPathPaint);
        canvas.restore();
        this.mPathPaint.setShader(null);
    }

    private float calculateLastSegmentAlpha(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        return java.lang.Math.min(1.0f, java.lang.Math.max(0.0f, ((((float) java.lang.Math.sqrt((f5 * f5) + (f6 * f6))) / this.mSquareWidth) - 0.3f) * 4.0f));
    }

    private int getDotColor() {
        if (this.mInStealthMode) {
            return this.mDotColor;
        }
        if (this.mPatternDisplayMode == com.android.internal.widget.LockPatternView.DisplayMode.Wrong) {
            return this.mErrorColor;
        }
        return this.mDotColor;
    }

    private int getCurrentColor(boolean z) {
        if (!z || ((this.mInStealthMode && this.mPatternDisplayMode != com.android.internal.widget.LockPatternView.DisplayMode.Wrong) || ((this.mPatternDisplayMode == com.android.internal.widget.LockPatternView.DisplayMode.Wrong && !this.mShowErrorPath) || this.mPatternInProgress))) {
            return this.mRegularColor;
        }
        if (this.mPatternDisplayMode == com.android.internal.widget.LockPatternView.DisplayMode.Wrong) {
            return this.mErrorColor;
        }
        if (this.mPatternDisplayMode == com.android.internal.widget.LockPatternView.DisplayMode.Correct || this.mPatternDisplayMode == com.android.internal.widget.LockPatternView.DisplayMode.Animate) {
            return this.mSuccessColor;
        }
        throw new java.lang.IllegalStateException("unknown display mode " + this.mPatternDisplayMode);
    }

    private void drawCircle(android.graphics.Canvas canvas, float f, float f2, float f3, boolean z, float f4, float f5) {
        boolean z2 = this.mPatternDisplayMode == com.android.internal.widget.LockPatternView.DisplayMode.Wrong && this.mShowErrorPath;
        if (this.mFadePattern && !this.mInStealthMode && !z2) {
            this.mPaint.setColor(com.android.internal.graphics.ColorUtils.blendARGB(this.mDotColor, this.mDotActivatedColor, f5));
        } else if (!this.mFadePattern && z) {
            this.mPaint.setColor(this.mDotActivatedColor);
        } else {
            this.mPaint.setColor(getDotColor());
        }
        this.mPaint.setAlpha((int) (f4 * 255.0f));
        canvas.drawCircle(f, f2, f3, this.mPaint);
    }

    private void drawCellDrawable(android.graphics.Canvas canvas, int i, int i2, float f, boolean z) {
        android.graphics.Rect rect = new android.graphics.Rect((int) (this.mPaddingLeft + (i2 * this.mSquareWidth)), (int) (this.mPaddingTop + (i * this.mSquareHeight)), (int) (this.mPaddingLeft + ((i2 + 1) * this.mSquareWidth)), (int) (this.mPaddingTop + ((i + 1) * this.mSquareHeight)));
        float f2 = f / (this.mDotSize / 2);
        canvas.save();
        canvas.clipRect(rect);
        canvas.scale(f2, f2, rect.centerX(), rect.centerY());
        if (!z || f2 > 1.0f) {
            this.mNotSelectedDrawable.draw(canvas);
        } else {
            this.mSelectedDrawable.draw(canvas);
        }
        canvas.restore();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        byte[] patternToByteArray = com.android.internal.widget.LockPatternUtils.patternToByteArray(this.mPattern, this.mPatternSize);
        return new com.android.internal.widget.LockPatternView.SavedState(onSaveInstanceState, patternToByteArray != null ? new java.lang.String(patternToByteArray) : null, this.mPatternDisplayMode.ordinal(), this.mPatternSize, this.mInputEnabled, this.mInStealthMode, this.mVisibleDots, this.mShowErrorPath);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        com.android.internal.widget.LockPatternView.SavedState savedState = (com.android.internal.widget.LockPatternView.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setPattern(com.android.internal.widget.LockPatternView.DisplayMode.Correct, com.android.internal.widget.LockPatternUtils.byteArrayToPattern(savedState.getSerializedPattern().getBytes(), savedState.getPatternSize()));
        this.mPatternDisplayMode = com.android.internal.widget.LockPatternView.DisplayMode.values()[savedState.getDisplayMode()];
        this.mPatternSize = savedState.getPatternSize();
        this.mInputEnabled = savedState.isInputEnabled();
        this.mInStealthMode = savedState.isInStealthMode();
        this.mVisibleDots = savedState.isVisibleDots();
        this.mShowErrorPath = savedState.isShowErrorPath();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setSystemGestureExclusionRects(java.util.List.of(new android.graphics.Rect(i, i2, i3, i4)));
    }

    private static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<com.android.internal.widget.LockPatternView.SavedState> CREATOR = new android.os.Parcelable.Creator<com.android.internal.widget.LockPatternView.SavedState>() { // from class: com.android.internal.widget.LockPatternView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.widget.LockPatternView.SavedState createFromParcel(android.os.Parcel parcel) {
                return new com.android.internal.widget.LockPatternView.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.widget.LockPatternView.SavedState[] newArray(int i) {
                return new com.android.internal.widget.LockPatternView.SavedState[i];
            }
        };
        private final int mDisplayMode;
        private final boolean mInStealthMode;
        private final boolean mInputEnabled;
        private final byte mPatternSize;
        private final java.lang.String mSerializedPattern;
        private final boolean mShowErrorPath;
        private final boolean mVisibleDots;

        private SavedState(android.os.Parcelable parcelable, java.lang.String str, int i, byte b, boolean z, boolean z2, boolean z3, boolean z4) {
            super(parcelable);
            this.mSerializedPattern = str;
            this.mDisplayMode = i;
            this.mPatternSize = b;
            this.mInputEnabled = z;
            this.mInStealthMode = z2;
            this.mVisibleDots = z3;
            this.mShowErrorPath = z4;
        }

        private SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.mSerializedPattern = parcel.readString();
            this.mDisplayMode = parcel.readInt();
            this.mPatternSize = parcel.readByte();
            this.mInputEnabled = ((java.lang.Boolean) parcel.readValue(null)).booleanValue();
            this.mInStealthMode = ((java.lang.Boolean) parcel.readValue(null)).booleanValue();
            this.mVisibleDots = ((java.lang.Boolean) parcel.readValue(null)).booleanValue();
            this.mShowErrorPath = ((java.lang.Boolean) parcel.readValue(null)).booleanValue();
        }

        public java.lang.String getSerializedPattern() {
            return this.mSerializedPattern;
        }

        public int getDisplayMode() {
            return this.mDisplayMode;
        }

        public byte getPatternSize() {
            return this.mPatternSize;
        }

        public boolean isInputEnabled() {
            return this.mInputEnabled;
        }

        public boolean isInStealthMode() {
            return this.mInStealthMode;
        }

        public boolean isVisibleDots() {
            return this.mVisibleDots;
        }

        public boolean isShowErrorPath() {
            return this.mShowErrorPath;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.mSerializedPattern);
            parcel.writeInt(this.mDisplayMode);
            parcel.writeByte(this.mPatternSize);
            parcel.writeValue(java.lang.Boolean.valueOf(this.mInputEnabled));
            parcel.writeValue(java.lang.Boolean.valueOf(this.mInStealthMode));
            parcel.writeValue(java.lang.Boolean.valueOf(this.mVisibleDots));
            parcel.writeValue(java.lang.Boolean.valueOf(this.mShowErrorPath));
        }
    }

    private final class PatternExploreByTouchHelper extends com.android.internal.widget.ExploreByTouchHelper {
        private final android.util.SparseArray<com.android.internal.widget.LockPatternView.PatternExploreByTouchHelper.VirtualViewContainer> mItems;
        private android.graphics.Rect mTempRect;

        class VirtualViewContainer {
            java.lang.CharSequence description;

            public VirtualViewContainer(java.lang.CharSequence charSequence) {
                this.description = charSequence;
            }
        }

        public PatternExploreByTouchHelper(android.view.View view) {
            super(view);
            this.mTempRect = new android.graphics.Rect();
            this.mItems = new android.util.SparseArray<>();
            for (int i = 1; i < 10; i++) {
                this.mItems.put(i, new com.android.internal.widget.LockPatternView.PatternExploreByTouchHelper.VirtualViewContainer(getTextForVirtualView(i)));
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float f, float f2) {
            return getVirtualViewIdForHit(f, f2);
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(android.util.IntArray intArray) {
            if (!com.android.internal.widget.LockPatternView.this.mPatternInProgress) {
                return;
            }
            for (int i = 1; i < 10; i++) {
                intArray.add(i);
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateEventForVirtualView(int i, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            com.android.internal.widget.LockPatternView.PatternExploreByTouchHelper.VirtualViewContainer virtualViewContainer = this.mItems.get(i);
            if (virtualViewContainer != null) {
                accessibilityEvent.getText().add(virtualViewContainer.description);
            }
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onPopulateAccessibilityEvent(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            if (!com.android.internal.widget.LockPatternView.this.mPatternInProgress) {
                accessibilityEvent.setContentDescription(com.android.internal.widget.LockPatternView.this.getContext().getText(com.android.internal.R.string.lockscreen_access_pattern_area));
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int i, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            accessibilityNodeInfo.setText(getTextForVirtualView(i));
            accessibilityNodeInfo.setContentDescription(getTextForVirtualView(i));
            if (com.android.internal.widget.LockPatternView.this.mPatternInProgress) {
                accessibilityNodeInfo.setFocusable(true);
                if (isClickable(i)) {
                    accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                    accessibilityNodeInfo.setClickable(isClickable(i));
                }
            }
            accessibilityNodeInfo.setBoundsInParent(getBoundsForVirtualView(i));
        }

        private boolean isClickable(int i) {
            if (i != Integer.MIN_VALUE) {
                int i2 = i - 1;
                int i3 = i2 / 3;
                int i4 = i2 % 3;
                if (i3 < 3) {
                    return !com.android.internal.widget.LockPatternView.this.mPatternDrawLookup[i3][i4];
                }
                return false;
            }
            return false;
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int i, int i2, android.os.Bundle bundle) {
            switch (i2) {
                case 16:
                    return onItemClicked(i);
                default:
                    return false;
            }
        }

        boolean onItemClicked(int i) {
            invalidateVirtualView(i);
            sendEventForVirtualView(i, 1);
            return true;
        }

        private android.graphics.Rect getBoundsForVirtualView(int i) {
            int i2 = i - 1;
            android.graphics.Rect rect = this.mTempRect;
            int i3 = i2 / 3;
            float centerXForColumn = com.android.internal.widget.LockPatternView.this.getCenterXForColumn(i2 % 3);
            float centerYForRow = com.android.internal.widget.LockPatternView.this.getCenterYForRow(i3);
            float f = com.android.internal.widget.LockPatternView.this.mDotHitRadius;
            rect.left = (int) (centerXForColumn - f);
            rect.right = (int) (centerXForColumn + f);
            rect.top = (int) (centerYForRow - f);
            rect.bottom = (int) (centerYForRow + f);
            return rect;
        }

        private java.lang.CharSequence getTextForVirtualView(int i) {
            return com.android.internal.widget.LockPatternView.this.getResources().getString(com.android.internal.R.string.lockscreen_access_pattern_cell_added_verbose, java.lang.Integer.valueOf(i));
        }

        private int getVirtualViewIdForHit(float f, float f2) {
            com.android.internal.widget.LockPatternView.Cell detectCellHit = com.android.internal.widget.LockPatternView.this.detectCellHit(f, f2);
            if (detectCellHit == null) {
                return Integer.MIN_VALUE;
            }
            boolean z = com.android.internal.widget.LockPatternView.this.mPatternDrawLookup[detectCellHit.row][detectCellHit.column];
            int i = (detectCellHit.row * 3) + detectCellHit.column + 1;
            if (z) {
                return i;
            }
            return Integer.MIN_VALUE;
        }
    }
}
