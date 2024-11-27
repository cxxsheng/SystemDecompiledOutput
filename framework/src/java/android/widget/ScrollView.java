package android.widget;

/* loaded from: classes4.dex */
public class ScrollView extends android.widget.FrameLayout {
    static final int ANIMATED_SCROLL_GAP = 250;
    private static final float FLING_DESTRETCH_FACTOR = 4.0f;
    private static final int INVALID_POINTER = -1;
    static final float MAX_SCROLL_FACTOR = 0.5f;
    private static final java.lang.String TAG = "ScrollView";
    private int mActivePointerId;
    private android.view.View mChildToScrollTo;
    private android.widget.DifferentialMotionFlingHelper mDifferentialMotionFlingHelper;
    public android.widget.EdgeEffect mEdgeGlowBottom;
    public android.widget.EdgeEffect mEdgeGlowTop;

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    private boolean mFillViewport;
    private android.os.StrictMode.Span mFlingStrictSpan;
    private android.view.HapticScrollFeedbackProvider mHapticScrollFeedbackProvider;
    private boolean mIsBeingDragged;
    private boolean mIsLayoutDirty;
    private int mLastMotionY;
    private long mLastScroll;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mNestedYOffset;
    private int mOverflingDistance;
    private int mOverscrollDistance;
    private android.widget.ScrollView.SavedState mSavedState;
    private final int[] mScrollConsumed;
    private final int[] mScrollOffset;
    private android.os.StrictMode.Span mScrollStrictSpan;
    private android.widget.OverScroller mScroller;
    private boolean mSmoothScrollingEnabled;
    private final android.graphics.Rect mTempRect;
    private int mTouchSlop;
    private android.view.VelocityTracker mVelocityTracker;
    private float mVerticalScrollFactor;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.ScrollView> {
        private int mFillViewportId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mFillViewportId = propertyMapper.mapBoolean("fillViewport", 16843130);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.ScrollView scrollView, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mFillViewportId, scrollView.isFillViewport());
        }
    }

    public ScrollView(android.content.Context context) {
        this(context, null);
    }

    public ScrollView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842880);
    }

    public ScrollView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ScrollView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTempRect = new android.graphics.Rect();
        this.mIsLayoutDirty = true;
        this.mChildToScrollTo = null;
        this.mIsBeingDragged = false;
        this.mSmoothScrollingEnabled = true;
        this.mActivePointerId = -1;
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mScrollStrictSpan = null;
        this.mFlingStrictSpan = null;
        this.mEdgeGlowTop = new android.widget.EdgeEffect(context, attributeSet);
        this.mEdgeGlowBottom = new android.widget.EdgeEffect(context, attributeSet);
        initScrollView();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ScrollView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.ScrollView, attributeSet, obtainStyledAttributes, i, i2);
        setFillViewport(obtainStyledAttributes.getBoolean(0, false));
        obtainStyledAttributes.recycle();
        if (context.getResources().getConfiguration().uiMode == 6) {
            setRevealOnFocusHint(false);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return true;
    }

    @Override // android.view.View
    protected float getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (this.mScrollY < verticalFadingEdgeLength) {
            return this.mScrollY / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    @Override // android.view.View
    protected float getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int bottom = (getChildAt(0).getBottom() - this.mScrollY) - (getHeight() - this.mPaddingBottom);
        if (bottom < verticalFadingEdgeLength) {
            return bottom / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    public void setEdgeEffectColor(int i) {
        setTopEdgeEffectColor(i);
        setBottomEdgeEffectColor(i);
    }

    public void setBottomEdgeEffectColor(int i) {
        this.mEdgeGlowBottom.setColor(i);
    }

    public void setTopEdgeEffectColor(int i) {
        this.mEdgeGlowTop.setColor(i);
    }

    public int getTopEdgeEffectColor() {
        return this.mEdgeGlowTop.getColor();
    }

    public int getBottomEdgeEffectColor() {
        return this.mEdgeGlowBottom.getColor();
    }

    public int getMaxScrollAmount() {
        return (int) ((this.mBottom - this.mTop) * 0.5f);
    }

    private void initScrollView() {
        this.mScroller = new android.widget.OverScroller(getContext());
        setFocusable(true);
        setDescendantFocusability(262144);
        setWillNotDraw(false);
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(this.mContext);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mOverscrollDistance = viewConfiguration.getScaledOverscrollDistance();
        this.mOverflingDistance = viewConfiguration.getScaledOverflingDistance();
        this.mVerticalScrollFactor = viewConfiguration.getScaledVerticalScrollFactor();
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view) {
        if (getChildCount() > 0) {
            throw new java.lang.IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view);
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view, int i) {
        if (getChildCount() > 0) {
            throw new java.lang.IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void addView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new java.lang.IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new java.lang.IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i, layoutParams);
    }

    private boolean canScroll() {
        android.view.View childAt = getChildAt(0);
        if (childAt != null) {
            return getHeight() < (childAt.getHeight() + this.mPaddingTop) + this.mPaddingBottom;
        }
        return false;
    }

    public boolean isFillViewport() {
        return this.mFillViewport;
    }

    public void setFillViewport(boolean z) {
        if (z != this.mFillViewport) {
            this.mFillViewport = z;
            requestLayout();
        }
    }

    public boolean isSmoothScrollingEnabled() {
        return this.mSmoothScrollingEnabled;
    }

    public void setSmoothScrollingEnabled(boolean z) {
        this.mSmoothScrollingEnabled = z;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        super.onMeasure(i, i2);
        if (this.mFillViewport && android.view.View.MeasureSpec.getMode(i2) != 0 && getChildCount() > 0) {
            android.view.View childAt = getChildAt(0);
            int i5 = getContext().getApplicationInfo().targetSdkVersion;
            android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) childAt.getLayoutParams();
            if (i5 >= 23) {
                i3 = this.mPaddingLeft + this.mPaddingRight + layoutParams.leftMargin + layoutParams.rightMargin;
                i4 = this.mPaddingTop + this.mPaddingBottom + layoutParams.topMargin + layoutParams.bottomMargin;
            } else {
                i3 = this.mPaddingLeft + this.mPaddingRight;
                i4 = this.mPaddingTop + this.mPaddingBottom;
            }
            int measuredHeight = getMeasuredHeight() - i4;
            if (childAt.getMeasuredHeight() < measuredHeight) {
                childAt.measure(getChildMeasureSpec(i, i3, layoutParams.width), android.view.View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    public boolean executeKeyEvent(android.view.KeyEvent keyEvent) {
        this.mTempRect.setEmpty();
        if (!canScroll()) {
            if (!isFocused() || keyEvent.getKeyCode() == 4 || keyEvent.getKeyCode() == 111) {
                return false;
            }
            android.view.View findFocus = findFocus();
            if (findFocus == this) {
                findFocus = null;
            }
            android.view.View findNextFocus = android.view.FocusFinder.getInstance().findNextFocus(this, findFocus, 130);
            return (findNextFocus == null || findNextFocus == this || !findNextFocus.requestFocus(130)) ? false : true;
        }
        if (keyEvent.getAction() != 0) {
            return false;
        }
        switch (keyEvent.getKeyCode()) {
            case 19:
                if (!keyEvent.isAltPressed()) {
                    return arrowScroll(33);
                }
                return fullScroll(33);
            case 20:
                if (!keyEvent.isAltPressed()) {
                    return arrowScroll(130);
                }
                return fullScroll(130);
            case 62:
                pageScroll(keyEvent.isShiftPressed() ? 33 : 130);
                return false;
            case 92:
                return pageScroll(33);
            case 93:
                return pageScroll(130);
            case 122:
                return fullScroll(33);
            case 123:
                return fullScroll(130);
            default:
                return false;
        }
    }

    private boolean inChild(int i, int i2) {
        if (getChildCount() <= 0) {
            return false;
        }
        int i3 = this.mScrollY;
        android.view.View childAt = getChildAt(0);
        return i2 >= childAt.getTop() - i3 && i2 < childAt.getBottom() - i3 && i >= childAt.getLeft() && i < childAt.getRight();
    }

    private void initOrResetVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = android.view.VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = android.view.VelocityTracker.obtain();
        }
    }

    private void initDifferentialFlingHelperIfNotExists() {
        if (this.mDifferentialMotionFlingHelper == null) {
            this.mDifferentialMotionFlingHelper = new android.widget.DifferentialMotionFlingHelper(this.mContext, new android.widget.ScrollView.DifferentialFlingTarget());
        }
    }

    private void initHapticScrollFeedbackProviderIfNotExists() {
        if (this.mHapticScrollFeedbackProvider == null) {
            this.mHapticScrollFeedbackProvider = new android.view.HapticScrollFeedbackProvider(this);
        }
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (z) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        boolean z = true;
        if ((action == 2 && this.mIsBeingDragged) || super.onInterceptTouchEvent(motionEvent)) {
            return true;
        }
        if (getScrollY() == 0 && !canScrollVertically(1)) {
            return false;
        }
        switch (action & 255) {
            case 0:
                int y = (int) motionEvent.getY();
                if (!inChild((int) motionEvent.getX(), y)) {
                    this.mIsBeingDragged = false;
                    recycleVelocityTracker();
                    break;
                } else {
                    this.mLastMotionY = y;
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    initOrResetVelocityTracker();
                    this.mVelocityTracker.addMovement(motionEvent);
                    this.mScroller.computeScrollOffset();
                    if (this.mScroller.isFinished() && this.mEdgeGlowBottom.isFinished() && this.mEdgeGlowTop.isFinished()) {
                        z = false;
                    }
                    this.mIsBeingDragged = z;
                    if (!this.mEdgeGlowTop.isFinished()) {
                        this.mEdgeGlowTop.onPullDistance(0.0f, motionEvent.getX() / getWidth());
                    }
                    if (!this.mEdgeGlowBottom.isFinished()) {
                        this.mEdgeGlowBottom.onPullDistance(0.0f, 1.0f - (motionEvent.getX() / getWidth()));
                    }
                    if (this.mIsBeingDragged && this.mScrollStrictSpan == null) {
                        this.mScrollStrictSpan = android.os.StrictMode.enterCriticalSpan("ScrollView-scroll");
                    }
                    startNestedScroll(2);
                    break;
                }
            case 1:
            case 3:
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                recycleVelocityTracker();
                if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, getScrollRange())) {
                    postInvalidateOnAnimation();
                }
                stopNestedScroll();
                break;
            case 2:
                int i = this.mActivePointerId;
                if (i != -1) {
                    int findPointerIndex = motionEvent.findPointerIndex(i);
                    if (findPointerIndex == -1) {
                        android.util.Log.e(TAG, "Invalid pointerId=" + i + " in onInterceptTouchEvent");
                        break;
                    } else {
                        int y2 = (int) motionEvent.getY(findPointerIndex);
                        if (java.lang.Math.abs(y2 - this.mLastMotionY) > this.mTouchSlop && (2 & getNestedScrollAxes()) == 0) {
                            this.mIsBeingDragged = true;
                            this.mLastMotionY = y2;
                            initVelocityTrackerIfNotExists();
                            this.mVelocityTracker.addMovement(motionEvent);
                            this.mNestedYOffset = 0;
                            if (this.mScrollStrictSpan == null) {
                                this.mScrollStrictSpan = android.os.StrictMode.enterCriticalSpan("ScrollView-scroll");
                            }
                            android.view.ViewParent parent = getParent();
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true);
                                break;
                            }
                        }
                    }
                }
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                break;
        }
        return this.mIsBeingDragged;
    }

    private boolean shouldDisplayEdgeEffects() {
        return getOverScrollMode() != 2;
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        android.view.ViewParent parent;
        int i;
        initVelocityTrackerIfNotExists();
        android.view.MotionEvent obtain = android.view.MotionEvent.obtain(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int i2 = 0;
        if (actionMasked == 0) {
            this.mNestedYOffset = 0;
        }
        obtain.offsetLocation(0.0f, this.mNestedYOffset);
        switch (actionMasked) {
            case 0:
                if (getChildCount() == 0) {
                    return false;
                }
                if (!this.mScroller.isFinished() && (parent = getParent()) != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                    if (this.mFlingStrictSpan != null) {
                        this.mFlingStrictSpan.finish();
                        this.mFlingStrictSpan = null;
                    }
                }
                this.mLastMotionY = (int) motionEvent.getY();
                this.mActivePointerId = motionEvent.getPointerId(0);
                startNestedScroll(2);
                break;
                break;
            case 1:
                if (this.mIsBeingDragged) {
                    android.view.VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                    int yVelocity = (int) velocityTracker.getYVelocity(this.mActivePointerId);
                    if (java.lang.Math.abs(yVelocity) > this.mMinimumVelocity) {
                        flingWithNestedDispatch(-yVelocity);
                    } else if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, getScrollRange())) {
                        postInvalidateOnAnimation();
                    }
                    this.mActivePointerId = -1;
                    endDrag();
                    velocityTracker.clear();
                    break;
                }
                break;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex == -1) {
                    android.util.Log.e(TAG, "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
                    break;
                } else {
                    int y = (int) motionEvent.getY(findPointerIndex);
                    int i3 = this.mLastMotionY - y;
                    if (dispatchNestedPreScroll(0, i3, this.mScrollConsumed, this.mScrollOffset)) {
                        i3 -= this.mScrollConsumed[1];
                        obtain.offsetLocation(0.0f, this.mScrollOffset[1]);
                        this.mNestedYOffset += this.mScrollOffset[1];
                    }
                    if (!this.mIsBeingDragged && java.lang.Math.abs(i3) > this.mTouchSlop) {
                        android.view.ViewParent parent2 = getParent();
                        if (parent2 != null) {
                            parent2.requestDisallowInterceptTouchEvent(true);
                        }
                        this.mIsBeingDragged = true;
                        if (i3 > 0) {
                            i3 -= this.mTouchSlop;
                        } else {
                            i3 += this.mTouchSlop;
                        }
                    }
                    if (this.mIsBeingDragged) {
                        this.mLastMotionY = y - this.mScrollOffset[1];
                        int i4 = this.mScrollY;
                        int scrollRange = getScrollRange();
                        int overScrollMode = getOverScrollMode();
                        boolean z = overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0);
                        float x = motionEvent.getX(findPointerIndex) / getWidth();
                        if (!z) {
                            i = i3;
                        } else {
                            if (i3 < 0 && this.mEdgeGlowBottom.getDistance() != 0.0f) {
                                i2 = java.lang.Math.round(getHeight() * this.mEdgeGlowBottom.onPullDistance(i3 / getHeight(), 1.0f - x));
                            } else if (i3 > 0 && this.mEdgeGlowTop.getDistance() != 0.0f) {
                                i2 = java.lang.Math.round((-getHeight()) * this.mEdgeGlowTop.onPullDistance((-i3) / getHeight(), x));
                            }
                            i = i3 - i2;
                        }
                        int i5 = i;
                        overScrollBy(0, i, 0, this.mScrollY, 0, scrollRange, 0, this.mOverscrollDistance, true);
                        int i6 = this.mScrollY - i4;
                        if (dispatchNestedScroll(0, i6, 0, i5 - i6, this.mScrollOffset)) {
                            this.mLastMotionY -= this.mScrollOffset[1];
                            obtain.offsetLocation(0.0f, this.mScrollOffset[1]);
                            this.mNestedYOffset += this.mScrollOffset[1];
                            break;
                        } else if (z) {
                            float f = i5;
                            if (f != 0.0f) {
                                int i7 = i4 + i5;
                                if (i7 < 0) {
                                    this.mEdgeGlowTop.onPullDistance((-i5) / getHeight(), x);
                                    if (!this.mEdgeGlowBottom.isFinished()) {
                                        this.mEdgeGlowBottom.onRelease();
                                    }
                                } else if (i7 > scrollRange) {
                                    this.mEdgeGlowBottom.onPullDistance(f / getHeight(), 1.0f - x);
                                    if (!this.mEdgeGlowTop.isFinished()) {
                                        this.mEdgeGlowTop.onRelease();
                                    }
                                }
                                if (shouldDisplayEdgeEffects() && (!this.mEdgeGlowTop.isFinished() || !this.mEdgeGlowBottom.isFinished())) {
                                    postInvalidateOnAnimation();
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            case 3:
                if (this.mIsBeingDragged && getChildCount() > 0) {
                    if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, getScrollRange())) {
                        postInvalidateOnAnimation();
                    }
                    this.mActivePointerId = -1;
                    endDrag();
                    break;
                }
                break;
            case 5:
                int actionIndex = motionEvent.getActionIndex();
                this.mLastMotionY = (int) motionEvent.getY(actionIndex);
                this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                this.mLastMotionY = (int) motionEvent.getY(motionEvent.findPointerIndex(this.mActivePointerId));
                break;
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(obtain);
        }
        obtain.recycle();
        return true;
    }

    private void onSecondaryPointerUp(android.view.MotionEvent motionEvent) {
        int action = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(action) == this.mActivePointerId) {
            int i = action == 0 ? 1 : 0;
            this.mLastMotionY = (int) motionEvent.getY(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        int i;
        boolean z;
        boolean z2;
        boolean z3;
        switch (motionEvent.getAction()) {
            case 8:
                if (motionEvent.isFromSource(2)) {
                    i = 9;
                } else if (motionEvent.isFromSource(4194304)) {
                    i = 26;
                } else {
                    i = -1;
                }
                int round = java.lang.Math.round((i == -1 ? 0.0f : motionEvent.getAxisValue(i)) * this.mVerticalScrollFactor);
                if (round != 0) {
                    int scrollRange = getScrollRange();
                    int i2 = this.mScrollY;
                    int i3 = i2 - round;
                    int overScrollMode = getOverScrollMode();
                    boolean z4 = !motionEvent.isFromSource(8194) && (overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0));
                    if (i3 < 0) {
                        if (z4) {
                            this.mEdgeGlowTop.onPullDistance((-i3) / getHeight(), 0.5f);
                            this.mEdgeGlowTop.onRelease();
                            invalidate();
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        z = z3;
                        scrollRange = 0;
                        z2 = true;
                    } else if (i3 <= scrollRange) {
                        scrollRange = i3;
                        z = false;
                        z2 = false;
                    } else {
                        if (!z4) {
                            z = false;
                        } else {
                            this.mEdgeGlowBottom.onPullDistance((i3 - scrollRange) / getHeight(), 0.5f);
                            this.mEdgeGlowBottom.onRelease();
                            invalidate();
                            z = true;
                        }
                        z2 = true;
                    }
                    if (scrollRange != i2) {
                        super.scrollTo(this.mScrollX, scrollRange);
                        if (z2) {
                            if (android.view.flags.Flags.scrollFeedbackApi()) {
                                initHapticScrollFeedbackProviderIfNotExists();
                                this.mHapticScrollFeedbackProvider.onScrollLimit(motionEvent.getDeviceId(), motionEvent.getSource(), i, scrollRange == 0);
                            }
                        } else {
                            if (android.view.flags.Flags.scrollFeedbackApi()) {
                                initHapticScrollFeedbackProviderIfNotExists();
                                this.mHapticScrollFeedbackProvider.onScrollProgress(motionEvent.getDeviceId(), motionEvent.getSource(), i, round);
                            }
                            initDifferentialFlingHelperIfNotExists();
                            this.mDifferentialMotionFlingHelper.onMotionEvent(motionEvent, i);
                        }
                        return true;
                    }
                    if (z) {
                        return true;
                    }
                }
                break;
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View
    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        if (!this.mScroller.isFinished()) {
            int i3 = this.mScrollX;
            int i4 = this.mScrollY;
            this.mScrollX = i;
            this.mScrollY = i2;
            invalidateParentIfNeeded();
            onScrollChanged(this.mScrollX, this.mScrollY, i3, i4);
            if (z2) {
                this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, getScrollRange());
            }
        } else {
            super.scrollTo(i, i2);
        }
        awakenScrollBars();
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, android.os.Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        switch (i) {
            case 4096:
            case 16908346:
                int min = java.lang.Math.min(this.mScrollY + ((getHeight() - this.mPaddingBottom) - this.mPaddingTop), getScrollRange());
                if (min == this.mScrollY) {
                    return false;
                }
                smoothScrollTo(0, min);
                return true;
            case 8192:
            case 16908344:
                int max = java.lang.Math.max(this.mScrollY - ((getHeight() - this.mPaddingBottom) - this.mPaddingTop), 0);
                if (max == this.mScrollY) {
                    return false;
                }
                smoothScrollTo(0, max);
                return true;
            default:
                return false;
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.ScrollView.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        int scrollRange;
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (isEnabled() && (scrollRange = getScrollRange()) > 0) {
            accessibilityNodeInfo.setScrollable(true);
            if (this.mScrollY > 0) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
            }
            if (this.mScrollY < scrollRange) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
            }
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setScrollable(getScrollRange() > 0);
        accessibilityEvent.setMaxScrollX(this.mScrollX);
        accessibilityEvent.setMaxScrollY(getScrollRange());
    }

    private int getScrollRange() {
        if (getChildCount() > 0) {
            return java.lang.Math.max(0, getChildAt(0).getHeight() - ((getHeight() - this.mPaddingBottom) - this.mPaddingTop));
        }
        return 0;
    }

    private android.view.View findFocusableViewInBounds(boolean z, int i, int i2) {
        java.util.ArrayList<android.view.View> focusables = getFocusables(2);
        int size = focusables.size();
        android.view.View view = null;
        boolean z2 = false;
        for (int i3 = 0; i3 < size; i3++) {
            android.view.View view2 = focusables.get(i3);
            int top = view2.getTop();
            int bottom = view2.getBottom();
            if (i < bottom && top < i2) {
                boolean z3 = i < top && bottom < i2;
                if (view == null) {
                    view = view2;
                    z2 = z3;
                } else {
                    boolean z4 = (z && top < view.getTop()) || (!z && bottom > view.getBottom());
                    if (z2) {
                        if (z3) {
                            if (!z4) {
                            }
                            view = view2;
                        }
                    } else if (z3) {
                        view = view2;
                        z2 = true;
                    } else {
                        if (!z4) {
                        }
                        view = view2;
                    }
                }
            }
        }
        return view;
    }

    public boolean pageScroll(int i) {
        boolean z = i == 130;
        int height = getHeight();
        if (z) {
            this.mTempRect.top = getScrollY() + height;
            int childCount = getChildCount();
            if (childCount > 0) {
                android.view.View childAt = getChildAt(childCount - 1);
                if (this.mTempRect.top + height > childAt.getBottom()) {
                    this.mTempRect.top = childAt.getBottom() - height;
                }
            }
        } else {
            this.mTempRect.top = getScrollY() - height;
            if (this.mTempRect.top < 0) {
                this.mTempRect.top = 0;
            }
        }
        this.mTempRect.bottom = this.mTempRect.top + height;
        return scrollAndFocus(i, this.mTempRect.top, this.mTempRect.bottom);
    }

    public boolean fullScroll(int i) {
        int childCount;
        boolean z = i == 130;
        int height = getHeight();
        this.mTempRect.top = 0;
        this.mTempRect.bottom = height;
        if (z && (childCount = getChildCount()) > 0) {
            this.mTempRect.bottom = getChildAt(childCount - 1).getBottom() + this.mPaddingBottom;
            this.mTempRect.top = this.mTempRect.bottom - height;
        }
        return scrollAndFocus(i, this.mTempRect.top, this.mTempRect.bottom);
    }

    private boolean scrollAndFocus(int i, int i2, int i3) {
        int height = getHeight();
        int scrollY = getScrollY();
        int i4 = height + scrollY;
        boolean z = false;
        boolean z2 = i == 33;
        android.view.View findFocusableViewInBounds = findFocusableViewInBounds(z2, i2, i3);
        if (findFocusableViewInBounds == null) {
            findFocusableViewInBounds = this;
        }
        if (i2 < scrollY || i3 > i4) {
            doScrollY(z2 ? i2 - scrollY : i3 - i4);
            z = true;
        }
        if (findFocusableViewInBounds != findFocus()) {
            findFocusableViewInBounds.requestFocus(i);
        }
        return z;
    }

    public boolean arrowScroll(int i) {
        int bottom;
        android.view.View findFocus = findFocus();
        if (findFocus == this) {
            findFocus = null;
        }
        android.view.View findNextFocus = android.view.FocusFinder.getInstance().findNextFocus(this, findFocus, i);
        int maxScrollAmount = getMaxScrollAmount();
        if (findNextFocus != null && isWithinDeltaOfScreen(findNextFocus, maxScrollAmount, getHeight())) {
            findNextFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(findNextFocus, this.mTempRect);
            doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            findNextFocus.requestFocus(i);
        } else {
            if (i == 33 && getScrollY() < maxScrollAmount) {
                maxScrollAmount = getScrollY();
            } else if (i == 130 && getChildCount() > 0 && (bottom = getChildAt(0).getBottom() - ((getScrollY() + getHeight()) - this.mPaddingBottom)) < maxScrollAmount) {
                maxScrollAmount = bottom;
            }
            if (maxScrollAmount == 0) {
                return false;
            }
            if (i != 130) {
                maxScrollAmount = -maxScrollAmount;
            }
            doScrollY(maxScrollAmount);
        }
        if (findFocus != null && findFocus.isFocused() && isOffScreen(findFocus)) {
            int descendantFocusability = getDescendantFocusability();
            setDescendantFocusability(131072);
            requestFocus();
            setDescendantFocusability(descendantFocusability);
            return true;
        }
        return true;
    }

    private boolean isOffScreen(android.view.View view) {
        return !isWithinDeltaOfScreen(view, 0, getHeight());
    }

    private boolean isWithinDeltaOfScreen(android.view.View view, int i, int i2) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        return this.mTempRect.bottom + i >= getScrollY() && this.mTempRect.top - i <= getScrollY() + i2;
    }

    private void doScrollY(int i) {
        if (i != 0) {
            if (this.mSmoothScrollingEnabled) {
                smoothScrollBy(0, i);
            } else {
                scrollBy(0, i);
            }
        }
    }

    public final void smoothScrollBy(int i, int i2) {
        if (getChildCount() == 0) {
            return;
        }
        if (android.view.animation.AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250) {
            int max = java.lang.Math.max(0, getChildAt(0).getHeight() - ((getHeight() - this.mPaddingBottom) - this.mPaddingTop));
            int i3 = this.mScrollY;
            this.mScroller.startScroll(this.mScrollX, i3, 0, java.lang.Math.max(0, java.lang.Math.min(i2 + i3, max)) - i3);
            postInvalidateOnAnimation();
        } else {
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                if (this.mFlingStrictSpan != null) {
                    this.mFlingStrictSpan.finish();
                    this.mFlingStrictSpan = null;
                }
            }
            scrollBy(i, i2);
        }
        this.mLastScroll = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
    }

    public final void smoothScrollTo(int i, int i2) {
        smoothScrollBy(i - this.mScrollX, i2 - this.mScrollY);
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        int childCount = getChildCount();
        int height = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
        if (childCount == 0) {
            return height;
        }
        int bottom = getChildAt(0).getBottom();
        int i = this.mScrollY;
        int max = java.lang.Math.max(0, bottom - height);
        if (i < 0) {
            return bottom - i;
        }
        if (i > max) {
            return bottom + (i - max);
        }
        return bottom;
    }

    @Override // android.view.View
    protected int computeVerticalScrollOffset() {
        return java.lang.Math.max(0, super.computeVerticalScrollOffset());
    }

    @Override // android.view.ViewGroup
    protected void measureChild(android.view.View view, int i, int i2) {
        view.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight, view.getLayoutParams().width), android.view.View.MeasureSpec.makeSafeMeasureSpec(java.lang.Math.max(0, android.view.View.MeasureSpec.getSize(i2) - (this.mPaddingTop + this.mPaddingBottom)), 0));
    }

    @Override // android.view.ViewGroup
    protected void measureChildWithMargins(android.view.View view, int i, int i2, int i3, int i4) {
        android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), android.view.View.MeasureSpec.makeSafeMeasureSpec(java.lang.Math.max(0, android.view.View.MeasureSpec.getSize(i3) - ((((this.mPaddingTop + this.mPaddingBottom) + marginLayoutParams.topMargin) + marginLayoutParams.bottomMargin) + i4)), 0));
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int i = this.mScrollX;
            int i2 = this.mScrollY;
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            int consumeFlingInStretch = consumeFlingInStretch(currY - i2);
            if (i != currX || consumeFlingInStretch != 0) {
                int scrollRange = getScrollRange();
                int overScrollMode = getOverScrollMode();
                boolean z = true;
                if (overScrollMode != 0 && (overScrollMode != 1 || scrollRange <= 0)) {
                    z = false;
                }
                boolean z2 = z;
                overScrollBy(currX - i, consumeFlingInStretch, i, i2, 0, scrollRange, 0, this.mOverflingDistance, false);
                onScrollChanged(this.mScrollX, this.mScrollY, i, i2);
                if (z2 && consumeFlingInStretch != 0) {
                    if (currY < 0 && i2 >= 0) {
                        this.mEdgeGlowTop.onAbsorb((int) this.mScroller.getCurrVelocity());
                    } else if (currY > scrollRange && i2 <= scrollRange) {
                        this.mEdgeGlowBottom.onAbsorb((int) this.mScroller.getCurrVelocity());
                    }
                }
            }
            if (!awakenScrollBars()) {
                postInvalidateOnAnimation();
                return;
            }
            return;
        }
        if (this.mFlingStrictSpan != null) {
            this.mFlingStrictSpan.finish();
            this.mFlingStrictSpan = null;
        }
    }

    private int consumeFlingInStretch(int i) {
        int scrollY = getScrollY();
        if (scrollY < 0 || scrollY > getScrollRange()) {
            return i;
        }
        if (i > 0 && this.mEdgeGlowTop != null && this.mEdgeGlowTop.getDistance() != 0.0f) {
            int round = java.lang.Math.round(((-r1) / FLING_DESTRETCH_FACTOR) * this.mEdgeGlowTop.onPullDistance(((-i) * FLING_DESTRETCH_FACTOR) / getHeight(), 0.5f));
            this.mEdgeGlowTop.onRelease();
            if (round != i) {
                this.mEdgeGlowTop.finish();
            }
            return i - round;
        }
        if (i < 0 && this.mEdgeGlowBottom != null && this.mEdgeGlowBottom.getDistance() != 0.0f) {
            float height = getHeight();
            int round2 = java.lang.Math.round((height / FLING_DESTRETCH_FACTOR) * this.mEdgeGlowBottom.onPullDistance((i * FLING_DESTRETCH_FACTOR) / height, 0.5f));
            this.mEdgeGlowBottom.onRelease();
            if (round2 != i) {
                this.mEdgeGlowBottom.finish();
            }
            return i - round2;
        }
        return i;
    }

    public void scrollToDescendant(android.view.View view) {
        if (!this.mIsLayoutDirty) {
            view.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(view, this.mTempRect);
            int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
            if (computeScrollDeltaToGetChildRectOnScreen != 0) {
                scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
                return;
            }
            return;
        }
        this.mChildToScrollTo = view;
    }

    private boolean scrollToChildRect(android.graphics.Rect rect, boolean z) {
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean z2 = computeScrollDeltaToGetChildRectOnScreen != 0;
        if (z2) {
            if (z) {
                scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
            } else {
                smoothScrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
            }
        }
        return z2;
    }

    protected int computeScrollDeltaToGetChildRectOnScreen(android.graphics.Rect rect) {
        int i;
        int i2;
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int i3 = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength;
        }
        if (rect.bottom < getChildAt(0).getHeight()) {
            i3 -= verticalFadingEdgeLength;
        }
        if (rect.bottom > i3 && rect.top > scrollY) {
            if (rect.height() > height) {
                i2 = (rect.top - scrollY) + 0;
            } else {
                i2 = (rect.bottom - i3) + 0;
            }
            return java.lang.Math.min(i2, getChildAt(0).getBottom() - i3);
        }
        if (rect.top >= scrollY || rect.bottom >= i3) {
            return 0;
        }
        if (rect.height() > height) {
            i = 0 - (i3 - rect.bottom);
        } else {
            i = 0 - (scrollY - rect.top);
        }
        return java.lang.Math.max(i, -getScrollY());
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(android.view.View view, android.view.View view2) {
        if (view2 != null && view2.getRevealOnFocusHint()) {
            if (!this.mIsLayoutDirty) {
                scrollToDescendant(view2);
            } else {
                this.mChildToScrollTo = view2;
            }
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup
    protected boolean onRequestFocusInDescendants(int i, android.graphics.Rect rect) {
        android.view.View findNextFocusFromRect;
        if (i == 2) {
            i = 130;
        } else if (i == 1) {
            i = 33;
        }
        if (rect == null) {
            findNextFocusFromRect = android.view.FocusFinder.getInstance().findNextFocus(this, null, i);
        } else {
            findNextFocusFromRect = android.view.FocusFinder.getInstance().findNextFocusFromRect(this, rect, i);
        }
        if (findNextFocusFromRect == null || isOffScreen(findNextFocusFromRect)) {
            return false;
        }
        return findNextFocusFromRect.requestFocus(i, rect);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(android.view.View view, android.graphics.Rect rect, boolean z) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return scrollToChildRect(rect, z);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mScrollStrictSpan != null) {
            this.mScrollStrictSpan.finish();
            this.mScrollStrictSpan = null;
        }
        if (this.mFlingStrictSpan != null) {
            this.mFlingStrictSpan.finish();
            this.mFlingStrictSpan = null;
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mIsLayoutDirty = false;
        if (this.mChildToScrollTo != null && isViewDescendantOf(this.mChildToScrollTo, this)) {
            scrollToDescendant(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (!isLaidOut()) {
            if (this.mSavedState != null) {
                this.mScrollY = this.mSavedState.scrollPosition;
                this.mSavedState = null;
            }
            int max = java.lang.Math.max(0, (getChildCount() > 0 ? getChildAt(0).getMeasuredHeight() : 0) - (((i4 - i2) - this.mPaddingBottom) - this.mPaddingTop));
            if (this.mScrollY > max) {
                this.mScrollY = max;
            } else if (this.mScrollY < 0) {
                this.mScrollY = 0;
            }
        }
        scrollTo(this.mScrollX, this.mScrollY);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        android.view.View findFocus = findFocus();
        if (findFocus != null && this != findFocus && isWithinDeltaOfScreen(findFocus, 0, i4)) {
            findFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(findFocus, this.mTempRect);
            doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
        }
    }

    private static boolean isViewDescendantOf(android.view.View view, android.view.View view2) {
        if (view == view2) {
            return true;
        }
        java.lang.Object parent = view.getParent();
        return (parent instanceof android.view.ViewGroup) && isViewDescendantOf((android.view.View) parent, view2);
    }

    public void fling(int i) {
        if (getChildCount() > 0) {
            int height = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
            this.mScroller.fling(this.mScrollX, this.mScrollY, 0, i, 0, 0, 0, java.lang.Math.max(0, getChildAt(0).getHeight() - height), 0, height / 2);
            if (this.mFlingStrictSpan == null) {
                this.mFlingStrictSpan = android.os.StrictMode.enterCriticalSpan("ScrollView-fling");
            }
            postInvalidateOnAnimation();
        }
    }

    private void flingWithNestedDispatch(int i) {
        boolean z = (this.mScrollY > 0 || i > 0) && (this.mScrollY < getScrollRange() || i < 0);
        float f = i;
        if (!dispatchNestedPreFling(0.0f, f)) {
            boolean dispatchNestedFling = dispatchNestedFling(0.0f, f, z);
            if (z) {
                fling(i);
                return;
            }
            if (!dispatchNestedFling) {
                if (!this.mEdgeGlowTop.isFinished()) {
                    int i2 = -i;
                    if (shouldAbsorb(this.mEdgeGlowTop, i2)) {
                        this.mEdgeGlowTop.onAbsorb(i2);
                        return;
                    } else {
                        fling(i);
                        return;
                    }
                }
                if (!this.mEdgeGlowBottom.isFinished()) {
                    if (shouldAbsorb(this.mEdgeGlowBottom, i)) {
                        this.mEdgeGlowBottom.onAbsorb(i);
                    } else {
                        fling(i);
                    }
                }
            }
        }
    }

    private boolean shouldAbsorb(android.widget.EdgeEffect edgeEffect, int i) {
        if (i > 0) {
            return true;
        }
        return ((float) this.mScroller.getSplineFlingDistance(-i)) < edgeEffect.getDistance() * ((float) getHeight());
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        recycleVelocityTracker();
        if (shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
        if (this.mScrollStrictSpan != null) {
            this.mScrollStrictSpan.finish();
            this.mScrollStrictSpan = null;
        }
    }

    @Override // android.view.View
    public void scrollTo(int i, int i2) {
        if (getChildCount() > 0) {
            android.view.View childAt = getChildAt(0);
            int clamp = clamp(i, (getWidth() - this.mPaddingRight) - this.mPaddingLeft, childAt.getWidth());
            int clamp2 = clamp(i2, (getHeight() - this.mPaddingBottom) - this.mPaddingTop, childAt.getHeight());
            if (clamp != this.mScrollX || clamp2 != this.mScrollY) {
                super.scrollTo(clamp, clamp2);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(android.view.View view, android.view.View view2, int i) {
        return (i & 2) != 0;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(android.view.View view, android.view.View view2, int i) {
        super.onNestedScrollAccepted(view, view2, i);
        startNestedScroll(2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onStopNestedScroll(android.view.View view) {
        super.onStopNestedScroll(view);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(android.view.View view, int i, int i2, int i3, int i4) {
        int i5 = this.mScrollY;
        scrollBy(0, i4);
        int i6 = this.mScrollY - i5;
        dispatchNestedScroll(0, i6, 0, i4 - i6, null);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedFling(android.view.View view, float f, float f2, boolean z) {
        if (!z) {
            flingWithNestedDispatch((int) f2);
            return true;
        }
        return false;
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        int width;
        int height;
        float f;
        float f2;
        int width2;
        int height2;
        float f3;
        float f4;
        super.draw(canvas);
        if (shouldDisplayEdgeEffects()) {
            int i = this.mScrollY;
            boolean clipToPadding = getClipToPadding();
            if (!this.mEdgeGlowTop.isFinished()) {
                int save = canvas.save();
                if (clipToPadding) {
                    width2 = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
                    height2 = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
                    f3 = this.mPaddingLeft;
                    f4 = this.mPaddingTop;
                } else {
                    width2 = getWidth();
                    height2 = getHeight();
                    f3 = 0.0f;
                    f4 = 0.0f;
                }
                canvas.translate(f3, java.lang.Math.min(0, i) + f4);
                this.mEdgeGlowTop.setSize(width2, height2);
                if (this.mEdgeGlowTop.draw(canvas)) {
                    postInvalidateOnAnimation();
                }
                canvas.restoreToCount(save);
            }
            if (!this.mEdgeGlowBottom.isFinished()) {
                int save2 = canvas.save();
                if (clipToPadding) {
                    width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
                    height = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
                    f = this.mPaddingLeft;
                    f2 = this.mPaddingTop;
                } else {
                    width = getWidth();
                    height = getHeight();
                    f = 0.0f;
                    f2 = 0.0f;
                }
                canvas.translate((-width) + f, java.lang.Math.max(getScrollRange(), i) + height + f2);
                canvas.rotate(180.0f, width, 0.0f);
                this.mEdgeGlowBottom.setSize(width, height);
                if (this.mEdgeGlowBottom.draw(canvas)) {
                    postInvalidateOnAnimation();
                }
                canvas.restoreToCount(save2);
            }
        }
    }

    private static int clamp(int i, int i2, int i3) {
        if (i2 >= i3 || i < 0) {
            return 0;
        }
        if (i2 + i > i3) {
            return i3 - i2;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (this.mContext.getApplicationInfo().targetSdkVersion <= 18) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        android.widget.ScrollView.SavedState savedState = (android.widget.ScrollView.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSavedState = savedState;
        requestLayout();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        if (this.mContext.getApplicationInfo().targetSdkVersion <= 18) {
            return super.onSaveInstanceState();
        }
        android.widget.ScrollView.SavedState savedState = new android.widget.ScrollView.SavedState(super.onSaveInstanceState());
        savedState.scrollPosition = this.mScrollY;
        return savedState;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("fillViewport", this.mFillViewport);
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.widget.ScrollView.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.ScrollView.SavedState>() { // from class: android.widget.ScrollView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.ScrollView.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.widget.ScrollView.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.ScrollView.SavedState[] newArray(int i) {
                return new android.widget.ScrollView.SavedState[i];
            }
        };
        public int scrollPosition;

        SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.scrollPosition = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.scrollPosition);
        }

        public java.lang.String toString() {
            return "ScrollView.SavedState{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " scrollPosition=" + this.scrollPosition + "}";
        }
    }

    private class DifferentialFlingTarget implements android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget {
        private DifferentialFlingTarget() {
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public boolean startDifferentialMotionFling(float f) {
            stopDifferentialMotionFling();
            android.widget.ScrollView.this.fling((int) f);
            return true;
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public void stopDifferentialMotionFling() {
            android.widget.ScrollView.this.mScroller.abortAnimation();
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public float getScaledScrollFactor() {
            return -android.widget.ScrollView.this.mVerticalScrollFactor;
        }
    }
}
