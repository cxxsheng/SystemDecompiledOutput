package android.widget;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class SlidingDrawer extends android.view.ViewGroup {
    private static final int ANIMATION_FRAME_DURATION = 16;
    private static final int COLLAPSED_FULL_CLOSED = -10002;
    private static final int EXPANDED_FULL_OPEN = -10001;
    private static final float MAXIMUM_ACCELERATION = 2000.0f;
    private static final float MAXIMUM_MAJOR_VELOCITY = 200.0f;
    private static final float MAXIMUM_MINOR_VELOCITY = 150.0f;
    private static final float MAXIMUM_TAP_VELOCITY = 100.0f;
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    private static final int TAP_THRESHOLD = 6;
    private static final int VELOCITY_UNITS = 1000;
    private boolean mAllowSingleTap;
    private boolean mAnimateOnClick;
    private float mAnimatedAcceleration;
    private float mAnimatedVelocity;
    private boolean mAnimating;
    private long mAnimationLastTime;
    private float mAnimationPosition;
    private int mBottomOffset;
    private android.view.View mContent;
    private final int mContentId;
    private long mCurrentAnimationTime;
    private boolean mExpanded;
    private final android.graphics.Rect mFrame;
    private android.view.View mHandle;
    private int mHandleHeight;
    private final int mHandleId;
    private int mHandleWidth;
    private final android.graphics.Rect mInvalidate;
    private boolean mLocked;
    private final int mMaximumAcceleration;
    private final int mMaximumMajorVelocity;
    private final int mMaximumMinorVelocity;
    private final int mMaximumTapVelocity;
    private android.widget.SlidingDrawer.OnDrawerCloseListener mOnDrawerCloseListener;
    private android.widget.SlidingDrawer.OnDrawerOpenListener mOnDrawerOpenListener;
    private android.widget.SlidingDrawer.OnDrawerScrollListener mOnDrawerScrollListener;
    private final java.lang.Runnable mSlidingRunnable;
    private final int mTapThreshold;
    private int mTopOffset;
    private int mTouchDelta;
    private boolean mTracking;
    private android.view.VelocityTracker mVelocityTracker;
    private final int mVelocityUnits;
    private boolean mVertical;

    public interface OnDrawerCloseListener {
        void onDrawerClosed();
    }

    public interface OnDrawerOpenListener {
        void onDrawerOpened();
    }

    public interface OnDrawerScrollListener {
        void onScrollEnded();

        void onScrollStarted();
    }

    public SlidingDrawer(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingDrawer(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SlidingDrawer(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mFrame = new android.graphics.Rect();
        this.mInvalidate = new android.graphics.Rect();
        this.mSlidingRunnable = new java.lang.Runnable() { // from class: android.widget.SlidingDrawer.1
            @Override // java.lang.Runnable
            public void run() {
                android.widget.SlidingDrawer.this.doAnimation();
            }
        };
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.R.styleable.SlidingDrawer, i, i2);
        saveAttributeDataForStyleable(context, android.R.styleable.SlidingDrawer, attributeSet, obtainStyledAttributes, i, i2);
        this.mVertical = obtainStyledAttributes.getInt(0, 1) == 1;
        this.mBottomOffset = (int) obtainStyledAttributes.getDimension(1, 0.0f);
        this.mTopOffset = (int) obtainStyledAttributes.getDimension(2, 0.0f);
        this.mAllowSingleTap = obtainStyledAttributes.getBoolean(3, true);
        this.mAnimateOnClick = obtainStyledAttributes.getBoolean(6, true);
        int resourceId = obtainStyledAttributes.getResourceId(4, 0);
        if (resourceId != 0) {
            int resourceId2 = obtainStyledAttributes.getResourceId(5, 0);
            if (resourceId2 == 0) {
                throw new java.lang.IllegalArgumentException("The content attribute is required and must refer to a valid child.");
            }
            if (resourceId == resourceId2) {
                throw new java.lang.IllegalArgumentException("The content and handle attributes must refer to different children.");
            }
            this.mHandleId = resourceId;
            this.mContentId = resourceId2;
            float f = getResources().getDisplayMetrics().density;
            this.mTapThreshold = (int) ((6.0f * f) + 0.5f);
            this.mMaximumTapVelocity = (int) ((100.0f * f) + 0.5f);
            this.mMaximumMinorVelocity = (int) ((MAXIMUM_MINOR_VELOCITY * f) + 0.5f);
            this.mMaximumMajorVelocity = (int) ((200.0f * f) + 0.5f);
            this.mMaximumAcceleration = (int) ((MAXIMUM_ACCELERATION * f) + 0.5f);
            this.mVelocityUnits = (int) ((f * 1000.0f) + 0.5f);
            obtainStyledAttributes.recycle();
            setAlwaysDrawnWithCacheEnabled(false);
            return;
        }
        throw new java.lang.IllegalArgumentException("The handle attribute is required and must refer to a valid child.");
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        this.mHandle = findViewById(this.mHandleId);
        if (this.mHandle == null) {
            throw new java.lang.IllegalArgumentException("The handle attribute is must refer to an existing child.");
        }
        this.mHandle.setOnClickListener(new android.widget.SlidingDrawer.DrawerToggler());
        this.mContent = findViewById(this.mContentId);
        if (this.mContent == null) {
            throw new java.lang.IllegalArgumentException("The content attribute is must refer to an existing child.");
        }
        this.mContent.setVisibility(8);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int mode = android.view.View.MeasureSpec.getMode(i);
        int size = android.view.View.MeasureSpec.getSize(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        if (mode == 0 || mode2 == 0) {
            throw new java.lang.RuntimeException("SlidingDrawer cannot have UNSPECIFIED dimensions");
        }
        android.view.View view = this.mHandle;
        measureChild(view, i, i2);
        if (this.mVertical) {
            this.mContent.measure(android.view.View.MeasureSpec.makeMeasureSpec(size, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec((size2 - view.getMeasuredHeight()) - this.mTopOffset, 1073741824));
        } else {
            this.mContent.measure(android.view.View.MeasureSpec.makeMeasureSpec((size - view.getMeasuredWidth()) - this.mTopOffset, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        long drawingTime = getDrawingTime();
        android.view.View view = this.mHandle;
        boolean z = this.mVertical;
        drawChild(canvas, view, drawingTime);
        if (this.mTracking || this.mAnimating) {
            android.graphics.Bitmap drawingCache = this.mContent.getDrawingCache();
            if (drawingCache != null) {
                if (z) {
                    canvas.drawBitmap(drawingCache, 0.0f, view.getBottom(), (android.graphics.Paint) null);
                    return;
                } else {
                    canvas.drawBitmap(drawingCache, view.getRight(), 0.0f, (android.graphics.Paint) null);
                    return;
                }
            }
            canvas.save();
            canvas.translate(z ? 0.0f : view.getLeft() - this.mTopOffset, z ? view.getTop() - this.mTopOffset : 0.0f);
            drawChild(canvas, this.mContent, drawingTime);
            canvas.restore();
            return;
        }
        if (this.mExpanded) {
            drawChild(canvas, this.mContent, drawingTime);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        if (this.mTracking) {
            return;
        }
        int i7 = i3 - i;
        int i8 = i4 - i2;
        android.view.View view = this.mHandle;
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        android.view.View view2 = this.mContent;
        if (this.mVertical) {
            i5 = (i7 - measuredWidth) / 2;
            i6 = this.mExpanded ? this.mTopOffset : (i8 - measuredHeight) + this.mBottomOffset;
            view2.layout(0, this.mTopOffset + measuredHeight, view2.getMeasuredWidth(), this.mTopOffset + measuredHeight + view2.getMeasuredHeight());
        } else {
            i5 = this.mExpanded ? this.mTopOffset : (i7 - measuredWidth) + this.mBottomOffset;
            i6 = (i8 - measuredHeight) / 2;
            view2.layout(this.mTopOffset + measuredWidth, 0, this.mTopOffset + measuredWidth + view2.getMeasuredWidth(), view2.getMeasuredHeight());
        }
        view.layout(i5, i6, measuredWidth + i5, measuredHeight + i6);
        this.mHandleHeight = view.getHeight();
        this.mHandleWidth = view.getWidth();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        if (this.mLocked) {
            return false;
        }
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        android.graphics.Rect rect = this.mFrame;
        android.view.View view = this.mHandle;
        view.getHitRect(rect);
        if (!this.mTracking && !rect.contains((int) x, (int) y)) {
            return false;
        }
        if (action == 0) {
            this.mTracking = true;
            view.setPressed(true);
            prepareContent();
            if (this.mOnDrawerScrollListener != null) {
                this.mOnDrawerScrollListener.onScrollStarted();
            }
            if (this.mVertical) {
                int top = this.mHandle.getTop();
                this.mTouchDelta = ((int) y) - top;
                prepareTracking(top);
            } else {
                int left = this.mHandle.getLeft();
                this.mTouchDelta = ((int) x) - left;
                prepareTracking(left);
            }
            this.mVelocityTracker.addMovement(motionEvent);
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        boolean z;
        if (this.mLocked) {
            return true;
        }
        if (this.mTracking) {
            this.mVelocityTracker.addMovement(motionEvent);
            switch (motionEvent.getAction()) {
                case 1:
                case 3:
                    android.view.VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(this.mVelocityUnits);
                    float yVelocity = velocityTracker.getYVelocity();
                    float xVelocity = velocityTracker.getXVelocity();
                    boolean z2 = this.mVertical;
                    if (z2) {
                        z = yVelocity < 0.0f;
                        if (xVelocity < 0.0f) {
                            xVelocity = -xVelocity;
                        }
                        if (xVelocity > this.mMaximumMinorVelocity) {
                            xVelocity = this.mMaximumMinorVelocity;
                        }
                    } else {
                        z = xVelocity < 0.0f;
                        if (yVelocity < 0.0f) {
                            yVelocity = -yVelocity;
                        }
                        if (yVelocity > this.mMaximumMinorVelocity) {
                            yVelocity = this.mMaximumMinorVelocity;
                        }
                    }
                    float hypot = (float) java.lang.Math.hypot(xVelocity, yVelocity);
                    if (z) {
                        hypot = -hypot;
                    }
                    int top = this.mHandle.getTop();
                    int left = this.mHandle.getLeft();
                    if (java.lang.Math.abs(hypot) < this.mMaximumTapVelocity) {
                        boolean z3 = this.mExpanded;
                        if (!z2 ? !((!z3 || left >= this.mTapThreshold + this.mTopOffset) && (this.mExpanded || left <= (((this.mBottomOffset + this.mRight) - this.mLeft) - this.mHandleWidth) - this.mTapThreshold)) : !((!z3 || top >= this.mTapThreshold + this.mTopOffset) && (this.mExpanded || top <= (((this.mBottomOffset + this.mBottom) - this.mTop) - this.mHandleHeight) - this.mTapThreshold))) {
                            if (this.mAllowSingleTap) {
                                playSoundEffect(0);
                                if (this.mExpanded) {
                                    if (!z2) {
                                        top = left;
                                    }
                                    animateClose(top, true);
                                    break;
                                } else {
                                    if (!z2) {
                                        top = left;
                                    }
                                    animateOpen(top, true);
                                    break;
                                }
                            } else {
                                if (!z2) {
                                    top = left;
                                }
                                performFling(top, hypot, false, true);
                                break;
                            }
                        } else {
                            if (!z2) {
                                top = left;
                            }
                            performFling(top, hypot, false, true);
                            break;
                        }
                    } else {
                        if (!z2) {
                            top = left;
                        }
                        performFling(top, hypot, false, true);
                        break;
                    }
                case 2:
                    moveHandle(((int) (this.mVertical ? motionEvent.getY() : motionEvent.getX())) - this.mTouchDelta);
                    break;
            }
        }
        return this.mTracking || this.mAnimating || super.onTouchEvent(motionEvent);
    }

    private void animateClose(int i, boolean z) {
        prepareTracking(i);
        performFling(i, this.mMaximumAcceleration, true, z);
    }

    private void animateOpen(int i, boolean z) {
        prepareTracking(i);
        performFling(i, -this.mMaximumAcceleration, true, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0063, code lost:
    
        if (r4 > (-r2.mMaximumMajorVelocity)) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void performFling(int i, float f, boolean z, boolean z2) {
        this.mAnimationPosition = i;
        this.mAnimatedVelocity = f;
        if (this.mExpanded) {
            if (!z && f <= this.mMaximumMajorVelocity) {
                if (i <= this.mTopOffset + (this.mVertical ? this.mHandleHeight : this.mHandleWidth) || f <= (-this.mMaximumMajorVelocity)) {
                    this.mAnimatedAcceleration = -this.mMaximumAcceleration;
                    if (f > 0.0f) {
                        this.mAnimatedVelocity = 0.0f;
                    }
                }
            }
            this.mAnimatedAcceleration = this.mMaximumAcceleration;
            if (f < 0.0f) {
                this.mAnimatedVelocity = 0.0f;
            }
        } else {
            if (!z) {
                if (f <= this.mMaximumMajorVelocity) {
                    if (i > (this.mVertical ? getHeight() : getWidth()) / 2) {
                    }
                }
                this.mAnimatedAcceleration = this.mMaximumAcceleration;
                if (f < 0.0f) {
                    this.mAnimatedVelocity = 0.0f;
                }
            }
            this.mAnimatedAcceleration = -this.mMaximumAcceleration;
            if (f > 0.0f) {
                this.mAnimatedVelocity = 0.0f;
            }
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        this.mAnimationLastTime = uptimeMillis;
        this.mCurrentAnimationTime = uptimeMillis + 16;
        this.mAnimating = true;
        removeCallbacks(this.mSlidingRunnable);
        postDelayed(this.mSlidingRunnable, 16L);
        stopTracking(z2);
    }

    private void prepareTracking(int i) {
        int width;
        int i2;
        this.mTracking = true;
        this.mVelocityTracker = android.view.VelocityTracker.obtain();
        if (!this.mExpanded) {
            this.mAnimatedAcceleration = this.mMaximumAcceleration;
            this.mAnimatedVelocity = this.mMaximumMajorVelocity;
            int i3 = this.mBottomOffset;
            if (this.mVertical) {
                width = getHeight();
                i2 = this.mHandleHeight;
            } else {
                width = getWidth();
                i2 = this.mHandleWidth;
            }
            this.mAnimationPosition = i3 + (width - i2);
            moveHandle((int) this.mAnimationPosition);
            this.mAnimating = true;
            removeCallbacks(this.mSlidingRunnable);
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mAnimationLastTime = uptimeMillis;
            this.mCurrentAnimationTime = uptimeMillis + 16;
            this.mAnimating = true;
            return;
        }
        if (this.mAnimating) {
            this.mAnimating = false;
            removeCallbacks(this.mSlidingRunnable);
        }
        moveHandle(i);
    }

    private void moveHandle(int i) {
        android.view.View view = this.mHandle;
        if (this.mVertical) {
            if (i == -10001) {
                view.offsetTopAndBottom(this.mTopOffset - view.getTop());
                invalidate();
                return;
            }
            if (i == -10002) {
                view.offsetTopAndBottom((((this.mBottomOffset + this.mBottom) - this.mTop) - this.mHandleHeight) - view.getTop());
                invalidate();
                return;
            }
            int top = view.getTop();
            int i2 = i - top;
            if (i < this.mTopOffset) {
                i2 = this.mTopOffset - top;
            } else if (i2 > (((this.mBottomOffset + this.mBottom) - this.mTop) - this.mHandleHeight) - top) {
                i2 = (((this.mBottomOffset + this.mBottom) - this.mTop) - this.mHandleHeight) - top;
            }
            view.offsetTopAndBottom(i2);
            android.graphics.Rect rect = this.mFrame;
            android.graphics.Rect rect2 = this.mInvalidate;
            view.getHitRect(rect);
            rect2.set(rect);
            rect2.union(rect.left, rect.top - i2, rect.right, rect.bottom - i2);
            rect2.union(0, rect.bottom - i2, getWidth(), (rect.bottom - i2) + this.mContent.getHeight());
            invalidate(rect2);
            return;
        }
        if (i == -10001) {
            view.offsetLeftAndRight(this.mTopOffset - view.getLeft());
            invalidate();
            return;
        }
        if (i == -10002) {
            view.offsetLeftAndRight((((this.mBottomOffset + this.mRight) - this.mLeft) - this.mHandleWidth) - view.getLeft());
            invalidate();
            return;
        }
        int left = view.getLeft();
        int i3 = i - left;
        if (i < this.mTopOffset) {
            i3 = this.mTopOffset - left;
        } else if (i3 > (((this.mBottomOffset + this.mRight) - this.mLeft) - this.mHandleWidth) - left) {
            i3 = (((this.mBottomOffset + this.mRight) - this.mLeft) - this.mHandleWidth) - left;
        }
        view.offsetLeftAndRight(i3);
        android.graphics.Rect rect3 = this.mFrame;
        android.graphics.Rect rect4 = this.mInvalidate;
        view.getHitRect(rect3);
        rect4.set(rect3);
        rect4.union(rect3.left - i3, rect3.top, rect3.right - i3, rect3.bottom);
        rect4.union(rect3.right - i3, 0, (rect3.right - i3) + this.mContent.getWidth(), getHeight());
        invalidate(rect4);
    }

    private void prepareContent() {
        if (this.mAnimating) {
            return;
        }
        android.view.View view = this.mContent;
        if (view.isLayoutRequested()) {
            if (this.mVertical) {
                int i = this.mHandleHeight;
                view.measure(android.view.View.MeasureSpec.makeMeasureSpec(this.mRight - this.mLeft, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(((this.mBottom - this.mTop) - i) - this.mTopOffset, 1073741824));
                view.layout(0, this.mTopOffset + i, view.getMeasuredWidth(), this.mTopOffset + i + view.getMeasuredHeight());
            } else {
                int width = this.mHandle.getWidth();
                view.measure(android.view.View.MeasureSpec.makeMeasureSpec(((this.mRight - this.mLeft) - width) - this.mTopOffset, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(this.mBottom - this.mTop, 1073741824));
                view.layout(this.mTopOffset + width, 0, this.mTopOffset + width + view.getMeasuredWidth(), view.getMeasuredHeight());
            }
        }
        view.getViewTreeObserver().dispatchOnPreDraw();
        if (!view.isHardwareAccelerated()) {
            view.buildDrawingCache();
        }
        view.setVisibility(8);
    }

    private void stopTracking(boolean z) {
        this.mHandle.setPressed(false);
        this.mTracking = false;
        if (z && this.mOnDrawerScrollListener != null) {
            this.mOnDrawerScrollListener.onScrollEnded();
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAnimation() {
        if (this.mAnimating) {
            incrementAnimation();
            if (this.mAnimationPosition >= (this.mBottomOffset + (this.mVertical ? getHeight() : getWidth())) - 1) {
                this.mAnimating = false;
                closeDrawer();
            } else if (this.mAnimationPosition < this.mTopOffset) {
                this.mAnimating = false;
                openDrawer();
            } else {
                moveHandle((int) this.mAnimationPosition);
                this.mCurrentAnimationTime += 16;
                postDelayed(this.mSlidingRunnable, 16L);
            }
        }
    }

    private void incrementAnimation() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        float f = (uptimeMillis - this.mAnimationLastTime) / 1000.0f;
        float f2 = this.mAnimationPosition;
        float f3 = this.mAnimatedVelocity;
        float f4 = this.mAnimatedAcceleration;
        this.mAnimationPosition = f2 + (f3 * f) + (0.5f * f4 * f * f);
        this.mAnimatedVelocity = f3 + (f4 * f);
        this.mAnimationLastTime = uptimeMillis;
    }

    public void toggle() {
        if (!this.mExpanded) {
            openDrawer();
        } else {
            closeDrawer();
        }
        invalidate();
        requestLayout();
    }

    public void animateToggle() {
        if (!this.mExpanded) {
            animateOpen();
        } else {
            animateClose();
        }
    }

    public void open() {
        openDrawer();
        invalidate();
        requestLayout();
        sendAccessibilityEvent(32);
    }

    public void close() {
        closeDrawer();
        invalidate();
        requestLayout();
    }

    public void animateClose() {
        prepareContent();
        android.widget.SlidingDrawer.OnDrawerScrollListener onDrawerScrollListener = this.mOnDrawerScrollListener;
        if (onDrawerScrollListener != null) {
            onDrawerScrollListener.onScrollStarted();
        }
        animateClose(this.mVertical ? this.mHandle.getTop() : this.mHandle.getLeft(), false);
        if (onDrawerScrollListener != null) {
            onDrawerScrollListener.onScrollEnded();
        }
    }

    public void animateOpen() {
        prepareContent();
        android.widget.SlidingDrawer.OnDrawerScrollListener onDrawerScrollListener = this.mOnDrawerScrollListener;
        if (onDrawerScrollListener != null) {
            onDrawerScrollListener.onScrollStarted();
        }
        animateOpen(this.mVertical ? this.mHandle.getTop() : this.mHandle.getLeft(), false);
        sendAccessibilityEvent(32);
        if (onDrawerScrollListener != null) {
            onDrawerScrollListener.onScrollEnded();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.SlidingDrawer.class.getName();
    }

    private void closeDrawer() {
        moveHandle(-10002);
        this.mContent.setVisibility(8);
        this.mContent.destroyDrawingCache();
        if (!this.mExpanded) {
            return;
        }
        this.mExpanded = false;
        if (this.mOnDrawerCloseListener != null) {
            this.mOnDrawerCloseListener.onDrawerClosed();
        }
    }

    private void openDrawer() {
        moveHandle(-10001);
        this.mContent.setVisibility(0);
        if (this.mExpanded) {
            return;
        }
        this.mExpanded = true;
        if (this.mOnDrawerOpenListener != null) {
            this.mOnDrawerOpenListener.onDrawerOpened();
        }
    }

    public void setOnDrawerOpenListener(android.widget.SlidingDrawer.OnDrawerOpenListener onDrawerOpenListener) {
        this.mOnDrawerOpenListener = onDrawerOpenListener;
    }

    public void setOnDrawerCloseListener(android.widget.SlidingDrawer.OnDrawerCloseListener onDrawerCloseListener) {
        this.mOnDrawerCloseListener = onDrawerCloseListener;
    }

    public void setOnDrawerScrollListener(android.widget.SlidingDrawer.OnDrawerScrollListener onDrawerScrollListener) {
        this.mOnDrawerScrollListener = onDrawerScrollListener;
    }

    public android.view.View getHandle() {
        return this.mHandle;
    }

    public android.view.View getContent() {
        return this.mContent;
    }

    public void unlock() {
        this.mLocked = false;
    }

    public void lock() {
        this.mLocked = true;
    }

    public boolean isOpened() {
        return this.mExpanded;
    }

    public boolean isMoving() {
        return this.mTracking || this.mAnimating;
    }

    private class DrawerToggler implements android.view.View.OnClickListener {
        private DrawerToggler() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            if (android.widget.SlidingDrawer.this.mLocked) {
                return;
            }
            if (android.widget.SlidingDrawer.this.mAnimateOnClick) {
                android.widget.SlidingDrawer.this.animateToggle();
            } else {
                android.widget.SlidingDrawer.this.toggle();
            }
        }
    }
}
