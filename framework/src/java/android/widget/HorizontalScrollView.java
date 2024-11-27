package android.widget;

/* loaded from: classes4.dex */
public class HorizontalScrollView extends android.widget.FrameLayout {
    private static final int ANIMATED_SCROLL_GAP = 250;
    private static final float FLING_DESTRETCH_FACTOR = 4.0f;
    private static final int INVALID_POINTER = -1;
    private static final float MAX_SCROLL_FACTOR = 0.5f;
    private static final java.lang.String TAG = "HorizontalScrollView";
    private int mActivePointerId;
    private android.view.View mChildToScrollTo;
    public android.widget.EdgeEffect mEdgeGlowLeft;
    public android.widget.EdgeEffect mEdgeGlowRight;

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    private boolean mFillViewport;
    private float mHorizontalScrollFactor;
    private boolean mIsBeingDragged;
    private boolean mIsLayoutDirty;
    private int mLastMotionX;
    private long mLastScroll;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mOverflingDistance;
    private int mOverscrollDistance;
    private android.widget.HorizontalScrollView.SavedState mSavedState;
    private android.widget.OverScroller mScroller;
    private boolean mSmoothScrollingEnabled;
    private final android.graphics.Rect mTempRect;
    private int mTouchSlop;
    private android.view.VelocityTracker mVelocityTracker;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.HorizontalScrollView> {
        private int mFillViewportId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mFillViewportId = propertyMapper.mapBoolean("fillViewport", 16843130);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.HorizontalScrollView horizontalScrollView, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mFillViewportId, horizontalScrollView.isFillViewport());
        }
    }

    public HorizontalScrollView(android.content.Context context) {
        this(context, null);
    }

    public HorizontalScrollView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16843603);
    }

    public HorizontalScrollView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public HorizontalScrollView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTempRect = new android.graphics.Rect();
        this.mIsLayoutDirty = true;
        this.mChildToScrollTo = null;
        this.mIsBeingDragged = false;
        this.mSmoothScrollingEnabled = true;
        this.mActivePointerId = -1;
        this.mEdgeGlowLeft = new android.widget.EdgeEffect(context, attributeSet);
        this.mEdgeGlowRight = new android.widget.EdgeEffect(context, attributeSet);
        initScrollView();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.R.styleable.HorizontalScrollView, i, i2);
        saveAttributeDataForStyleable(context, android.R.styleable.HorizontalScrollView, attributeSet, obtainStyledAttributes, i, i2);
        setFillViewport(obtainStyledAttributes.getBoolean(0, false));
        obtainStyledAttributes.recycle();
        if (context.getResources().getConfiguration().uiMode == 6) {
            setRevealOnFocusHint(false);
        }
    }

    @Override // android.view.View
    protected float getLeftFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (this.mScrollX < horizontalFadingEdgeLength) {
            return this.mScrollX / horizontalFadingEdgeLength;
        }
        return 1.0f;
    }

    @Override // android.view.View
    protected float getRightFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        int right = (getChildAt(0).getRight() - this.mScrollX) - (getWidth() - this.mPaddingRight);
        if (right < horizontalFadingEdgeLength) {
            return right / horizontalFadingEdgeLength;
        }
        return 1.0f;
    }

    public void setEdgeEffectColor(int i) {
        setLeftEdgeEffectColor(i);
        setRightEdgeEffectColor(i);
    }

    public void setRightEdgeEffectColor(int i) {
        this.mEdgeGlowRight.setColor(i);
    }

    public void setLeftEdgeEffectColor(int i) {
        this.mEdgeGlowLeft.setColor(i);
    }

    public int getLeftEdgeEffectColor() {
        return this.mEdgeGlowLeft.getColor();
    }

    public int getRightEdgeEffectColor() {
        return this.mEdgeGlowRight.getColor();
    }

    public int getMaxScrollAmount() {
        return (int) ((this.mRight - this.mLeft) * 0.5f);
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
        this.mHorizontalScrollFactor = viewConfiguration.getScaledHorizontalScrollFactor();
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view) {
        if (getChildCount() > 0) {
            throw new java.lang.IllegalStateException("HorizontalScrollView can host only one direct child");
        }
        super.addView(view);
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view, int i) {
        if (getChildCount() > 0) {
            throw new java.lang.IllegalStateException("HorizontalScrollView can host only one direct child");
        }
        super.addView(view, i);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void addView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new java.lang.IllegalStateException("HorizontalScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new java.lang.IllegalStateException("HorizontalScrollView can host only one direct child");
        }
        super.addView(view, i, layoutParams);
    }

    private boolean canScroll() {
        android.view.View childAt = getChildAt(0);
        if (childAt != null) {
            return getWidth() < (childAt.getWidth() + this.mPaddingLeft) + this.mPaddingRight;
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
        if (this.mFillViewport && android.view.View.MeasureSpec.getMode(i) != 0 && getChildCount() > 0) {
            android.view.View childAt = getChildAt(0);
            android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) childAt.getLayoutParams();
            if (getContext().getApplicationInfo().targetSdkVersion >= 23) {
                i3 = this.mPaddingLeft + this.mPaddingRight + layoutParams.leftMargin + layoutParams.rightMargin;
                i4 = this.mPaddingTop + this.mPaddingBottom + layoutParams.topMargin + layoutParams.bottomMargin;
            } else {
                i3 = this.mPaddingLeft + this.mPaddingRight;
                i4 = this.mPaddingTop + this.mPaddingBottom;
            }
            int measuredWidth = getMeasuredWidth() - i3;
            if (childAt.getMeasuredWidth() < measuredWidth) {
                childAt.measure(android.view.View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), getChildMeasureSpec(i2, i4, layoutParams.height));
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
            if (!isFocused()) {
                return false;
            }
            android.view.View findFocus = findFocus();
            if (findFocus == this) {
                findFocus = null;
            }
            android.view.View findNextFocus = android.view.FocusFinder.getInstance().findNextFocus(this, findFocus, 66);
            return (findNextFocus == null || findNextFocus == this || !findNextFocus.requestFocus(66)) ? false : true;
        }
        if (keyEvent.getAction() != 0) {
            return false;
        }
        switch (keyEvent.getKeyCode()) {
            case 21:
                if (!keyEvent.isAltPressed()) {
                    return arrowScroll(17);
                }
                return fullScroll(17);
            case 22:
                if (!keyEvent.isAltPressed()) {
                    return arrowScroll(66);
                }
                return fullScroll(66);
            case 62:
                pageScroll(keyEvent.isShiftPressed() ? 17 : 66);
                return false;
            default:
                return false;
        }
    }

    private boolean inChild(int i, int i2) {
        if (getChildCount() <= 0) {
            return false;
        }
        int i3 = this.mScrollX;
        android.view.View childAt = getChildAt(0);
        return i2 >= childAt.getTop() && i2 < childAt.getBottom() && i >= childAt.getLeft() - i3 && i < childAt.getRight() - i3;
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
        switch (action & 255) {
            case 0:
                int x = (int) motionEvent.getX();
                if (!inChild(x, (int) motionEvent.getY())) {
                    this.mIsBeingDragged = false;
                    recycleVelocityTracker();
                    break;
                } else {
                    this.mLastMotionX = x;
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    initOrResetVelocityTracker();
                    this.mVelocityTracker.addMovement(motionEvent);
                    if (this.mScroller.isFinished() && this.mEdgeGlowLeft.isFinished() && this.mEdgeGlowRight.isFinished()) {
                        z = false;
                    }
                    this.mIsBeingDragged = z;
                    if (!this.mEdgeGlowLeft.isFinished()) {
                        this.mEdgeGlowLeft.onPullDistance(0.0f, 1.0f - (motionEvent.getY() / getHeight()));
                    }
                    if (!this.mEdgeGlowRight.isFinished()) {
                        this.mEdgeGlowRight.onPullDistance(0.0f, motionEvent.getY() / getHeight());
                        break;
                    }
                }
                break;
            case 1:
            case 3:
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, getScrollRange(), 0, 0)) {
                    postInvalidateOnAnimation();
                    break;
                }
                break;
            case 2:
                int i = this.mActivePointerId;
                if (i != -1) {
                    int findPointerIndex = motionEvent.findPointerIndex(i);
                    if (findPointerIndex == -1) {
                        android.util.Log.e(TAG, "Invalid pointerId=" + i + " in onInterceptTouchEvent");
                        break;
                    } else {
                        int x2 = (int) motionEvent.getX(findPointerIndex);
                        if (java.lang.Math.abs(x2 - this.mLastMotionX) > this.mTouchSlop) {
                            this.mIsBeingDragged = true;
                            this.mLastMotionX = x2;
                            initVelocityTrackerIfNotExists();
                            this.mVelocityTracker.addMovement(motionEvent);
                            if (this.mParent != null) {
                                this.mParent.requestDisallowInterceptTouchEvent(true);
                                break;
                            }
                        }
                    }
                }
                break;
            case 5:
                int actionIndex = motionEvent.getActionIndex();
                this.mLastMotionX = (int) motionEvent.getX(actionIndex);
                this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                this.mLastMotionX = (int) motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                break;
        }
        return this.mIsBeingDragged;
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        android.view.ViewParent parent;
        int i;
        initVelocityTrackerIfNotExists();
        this.mVelocityTracker.addMovement(motionEvent);
        int i2 = 0;
        switch (motionEvent.getAction() & 255) {
            case 0:
                if (getChildCount() == 0) {
                    return false;
                }
                if (!this.mScroller.isFinished() && (parent = getParent()) != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                this.mLastMotionX = (int) motionEvent.getX();
                this.mActivePointerId = motionEvent.getPointerId(0);
                return true;
            case 1:
                if (this.mIsBeingDragged) {
                    android.view.VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                    int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
                    if (getChildCount() > 0) {
                        if (java.lang.Math.abs(xVelocity) > this.mMinimumVelocity) {
                            fling(-xVelocity);
                        } else if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, getScrollRange(), 0, 0)) {
                            postInvalidateOnAnimation();
                        }
                    }
                    this.mActivePointerId = -1;
                    this.mIsBeingDragged = false;
                    recycleVelocityTracker();
                    if (shouldDisplayEdgeEffects()) {
                        this.mEdgeGlowLeft.onRelease();
                        this.mEdgeGlowRight.onRelease();
                        return true;
                    }
                    return true;
                }
                return true;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex == -1) {
                    android.util.Log.e(TAG, "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
                    return true;
                }
                int x = (int) motionEvent.getX(findPointerIndex);
                int i3 = this.mLastMotionX - x;
                if (!this.mIsBeingDragged && java.lang.Math.abs(i3) > this.mTouchSlop) {
                    android.view.ViewParent parent2 = getParent();
                    if (parent2 != null) {
                        parent2.requestDisallowInterceptTouchEvent(true);
                    }
                    this.mIsBeingDragged = true;
                    i3 = i3 > 0 ? i3 - this.mTouchSlop : i3 + this.mTouchSlop;
                }
                if (this.mIsBeingDragged) {
                    this.mLastMotionX = x;
                    int i4 = this.mScrollX;
                    int scrollRange = getScrollRange();
                    int overScrollMode = getOverScrollMode();
                    boolean z = overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0);
                    float y = motionEvent.getY(findPointerIndex) / getHeight();
                    if (!z) {
                        i = i3;
                    } else {
                        if (i3 < 0 && this.mEdgeGlowRight.getDistance() != 0.0f) {
                            i2 = java.lang.Math.round(getWidth() * this.mEdgeGlowRight.onPullDistance(i3 / getWidth(), y));
                        } else if (i3 > 0 && this.mEdgeGlowLeft.getDistance() != 0.0f) {
                            i2 = java.lang.Math.round((-getWidth()) * this.mEdgeGlowLeft.onPullDistance((-i3) / getWidth(), 1.0f - y));
                        }
                        i = i3 - i2;
                    }
                    int i5 = i;
                    overScrollBy(i, 0, this.mScrollX, 0, scrollRange, 0, this.mOverscrollDistance, 0, true);
                    if (!z) {
                        return true;
                    }
                    float f = i5;
                    if (f != 0.0f) {
                        int i6 = i4 + i5;
                        if (i6 < 0) {
                            this.mEdgeGlowLeft.onPullDistance((-i5) / getWidth(), 1.0f - y);
                            if (!this.mEdgeGlowRight.isFinished()) {
                                this.mEdgeGlowRight.onRelease();
                            }
                        } else if (i6 > scrollRange) {
                            this.mEdgeGlowRight.onPullDistance(f / getWidth(), y);
                            if (!this.mEdgeGlowLeft.isFinished()) {
                                this.mEdgeGlowLeft.onRelease();
                            }
                        }
                        if (shouldDisplayEdgeEffects()) {
                            if (!this.mEdgeGlowLeft.isFinished() || !this.mEdgeGlowRight.isFinished()) {
                                postInvalidateOnAnimation();
                                return true;
                            }
                            return true;
                        }
                        return true;
                    }
                    return true;
                }
                return true;
            case 3:
                if (this.mIsBeingDragged && getChildCount() > 0) {
                    if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, getScrollRange(), 0, 0)) {
                        postInvalidateOnAnimation();
                    }
                    this.mActivePointerId = -1;
                    this.mIsBeingDragged = false;
                    recycleVelocityTracker();
                    if (shouldDisplayEdgeEffects()) {
                        this.mEdgeGlowLeft.onRelease();
                        this.mEdgeGlowRight.onRelease();
                        return true;
                    }
                    return true;
                }
                return true;
            case 4:
            case 5:
            default:
                return true;
            case 6:
                onSecondaryPointerUp(motionEvent);
                return true;
        }
    }

    private void onSecondaryPointerUp(android.view.MotionEvent motionEvent) {
        int action = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(action) == this.mActivePointerId) {
            int i = action == 0 ? 1 : 0;
            this.mLastMotionX = (int) motionEvent.getX(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        float f;
        boolean z;
        switch (motionEvent.getAction()) {
            case 8:
                if (!this.mIsBeingDragged) {
                    if (motionEvent.isFromSource(2)) {
                        if ((motionEvent.getMetaState() & 1) != 0) {
                            f = -motionEvent.getAxisValue(9);
                        } else {
                            f = motionEvent.getAxisValue(10);
                        }
                    } else if (motionEvent.isFromSource(4194304)) {
                        f = motionEvent.getAxisValue(26);
                    } else {
                        f = 0.0f;
                    }
                    int round = java.lang.Math.round(f * this.mHorizontalScrollFactor);
                    if (round != 0) {
                        int scrollRange = getScrollRange();
                        int i = this.mScrollX;
                        int i2 = round + i;
                        int overScrollMode = getOverScrollMode();
                        boolean z2 = false;
                        boolean z3 = !motionEvent.isFromSource(8194) && (overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0));
                        if (i2 < 0) {
                            if (z3) {
                                this.mEdgeGlowLeft.onPullDistance((-i2) / getWidth(), 0.5f);
                                this.mEdgeGlowLeft.onRelease();
                                invalidate();
                                z = true;
                            } else {
                                z = false;
                            }
                            scrollRange = 0;
                            z2 = z;
                        } else if (i2 <= scrollRange) {
                            scrollRange = i2;
                        } else if (z3) {
                            this.mEdgeGlowRight.onPullDistance((i2 - scrollRange) / getWidth(), 0.5f);
                            this.mEdgeGlowRight.onRelease();
                            invalidate();
                            z2 = true;
                        }
                        if (scrollRange != i) {
                            super.scrollTo(scrollRange, this.mScrollY);
                            return true;
                        }
                        if (z2) {
                            return true;
                        }
                    }
                }
                break;
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return true;
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
            if (z) {
                this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, getScrollRange(), 0, 0);
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
        switch (i) {
            case 4096:
            case 16908347:
                if (!isEnabled()) {
                    return false;
                }
                int min = java.lang.Math.min(this.mScrollX + ((getWidth() - this.mPaddingLeft) - this.mPaddingRight), getScrollRange());
                if (min == this.mScrollX) {
                    return false;
                }
                smoothScrollTo(min, 0);
                return true;
            case 8192:
            case 16908345:
                if (!isEnabled()) {
                    return false;
                }
                int max = java.lang.Math.max(0, this.mScrollX - ((getWidth() - this.mPaddingLeft) - this.mPaddingRight));
                if (max == this.mScrollX) {
                    return false;
                }
                smoothScrollTo(max, 0);
                return true;
            default:
                return false;
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.HorizontalScrollView.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        int scrollRange = getScrollRange();
        if (scrollRange > 0) {
            accessibilityNodeInfo.setScrollable(true);
            if (isEnabled() && this.mScrollX > 0) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT);
            }
            if (isEnabled() && this.mScrollX < scrollRange) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT);
            }
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setScrollable(getScrollRange() > 0);
        accessibilityEvent.setMaxScrollX(getScrollRange());
        accessibilityEvent.setMaxScrollY(this.mScrollY);
    }

    private int getScrollRange() {
        if (getChildCount() > 0) {
            return java.lang.Math.max(0, getChildAt(0).getWidth() - ((getWidth() - this.mPaddingLeft) - this.mPaddingRight));
        }
        return 0;
    }

    private android.view.View findFocusableViewInMyBounds(boolean z, int i, android.view.View view) {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength() / 2;
        int i2 = i + horizontalFadingEdgeLength;
        int width = (i + getWidth()) - horizontalFadingEdgeLength;
        if (view != null && view.getLeft() < width && view.getRight() > i2) {
            return view;
        }
        return findFocusableViewInBounds(z, i2, width);
    }

    private android.view.View findFocusableViewInBounds(boolean z, int i, int i2) {
        java.util.ArrayList<android.view.View> focusables = getFocusables(2);
        int size = focusables.size();
        android.view.View view = null;
        boolean z2 = false;
        for (int i3 = 0; i3 < size; i3++) {
            android.view.View view2 = focusables.get(i3);
            int left = view2.getLeft();
            int right = view2.getRight();
            if (i < right && left < i2) {
                boolean z3 = i < left && right < i2;
                if (view == null) {
                    view = view2;
                    z2 = z3;
                } else {
                    boolean z4 = (z && left < view.getLeft()) || (!z && right > view.getRight());
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
        boolean z = i == 66;
        int width = getWidth();
        if (z) {
            this.mTempRect.left = getScrollX() + width;
            if (getChildCount() > 0) {
                android.view.View childAt = getChildAt(0);
                if (this.mTempRect.left + width > childAt.getRight()) {
                    this.mTempRect.left = childAt.getRight() - width;
                }
            }
        } else {
            this.mTempRect.left = getScrollX() - width;
            if (this.mTempRect.left < 0) {
                this.mTempRect.left = 0;
            }
        }
        this.mTempRect.right = this.mTempRect.left + width;
        return scrollAndFocus(i, this.mTempRect.left, this.mTempRect.right);
    }

    public boolean fullScroll(int i) {
        boolean z = i == 66;
        int width = getWidth();
        this.mTempRect.left = 0;
        this.mTempRect.right = width;
        if (z && getChildCount() > 0) {
            this.mTempRect.right = getChildAt(0).getRight();
            this.mTempRect.left = this.mTempRect.right - width;
        }
        return scrollAndFocus(i, this.mTempRect.left, this.mTempRect.right);
    }

    private boolean scrollAndFocus(int i, int i2, int i3) {
        int width = getWidth();
        int scrollX = getScrollX();
        int i4 = width + scrollX;
        boolean z = false;
        boolean z2 = i == 17;
        android.view.View findFocusableViewInBounds = findFocusableViewInBounds(z2, i2, i3);
        if (findFocusableViewInBounds == null) {
            findFocusableViewInBounds = this;
        }
        if (i2 < scrollX || i3 > i4) {
            doScrollX(z2 ? i2 - scrollX : i3 - i4);
            z = true;
        }
        if (findFocusableViewInBounds != findFocus()) {
            findFocusableViewInBounds.requestFocus(i);
        }
        return z;
    }

    public boolean arrowScroll(int i) {
        int right;
        android.view.View findFocus = findFocus();
        if (findFocus == this) {
            findFocus = null;
        }
        android.view.View findNextFocus = android.view.FocusFinder.getInstance().findNextFocus(this, findFocus, i);
        int maxScrollAmount = getMaxScrollAmount();
        if (findNextFocus != null && isWithinDeltaOfScreen(findNextFocus, maxScrollAmount)) {
            findNextFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(findNextFocus, this.mTempRect);
            doScrollX(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            findNextFocus.requestFocus(i);
        } else {
            if (i == 17 && getScrollX() < maxScrollAmount) {
                maxScrollAmount = getScrollX();
            } else if (i == 66 && getChildCount() > 0 && (right = getChildAt(0).getRight() - (getScrollX() + getWidth())) < maxScrollAmount) {
                maxScrollAmount = right;
            }
            if (maxScrollAmount == 0) {
                return false;
            }
            if (i != 66) {
                maxScrollAmount = -maxScrollAmount;
            }
            doScrollX(maxScrollAmount);
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
        return !isWithinDeltaOfScreen(view, 0);
    }

    private boolean isWithinDeltaOfScreen(android.view.View view, int i) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        return this.mTempRect.right + i >= getScrollX() && this.mTempRect.left - i <= getScrollX() + getWidth();
    }

    private void doScrollX(int i) {
        if (i != 0) {
            if (this.mSmoothScrollingEnabled) {
                smoothScrollBy(i, 0);
            } else {
                scrollBy(i, 0);
            }
        }
    }

    public final void smoothScrollBy(int i, int i2) {
        if (getChildCount() == 0) {
            return;
        }
        if (android.view.animation.AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250) {
            int max = java.lang.Math.max(0, getChildAt(0).getWidth() - ((getWidth() - this.mPaddingRight) - this.mPaddingLeft));
            int i3 = this.mScrollX;
            this.mScroller.startScroll(i3, this.mScrollY, java.lang.Math.max(0, java.lang.Math.min(i + i3, max)) - i3, 0);
            postInvalidateOnAnimation();
        } else {
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
            }
            scrollBy(i, i2);
        }
        this.mLastScroll = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
    }

    public final void smoothScrollTo(int i, int i2) {
        smoothScrollBy(i - this.mScrollX, i2 - this.mScrollY);
    }

    @Override // android.view.View
    protected int computeHorizontalScrollRange() {
        int childCount = getChildCount();
        int width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
        if (childCount == 0) {
            return width;
        }
        int right = getChildAt(0).getRight();
        int i = this.mScrollX;
        int max = java.lang.Math.max(0, right - width);
        if (i < 0) {
            return right - i;
        }
        if (i > max) {
            return right + (i - max);
        }
        return right;
    }

    @Override // android.view.View
    protected int computeHorizontalScrollOffset() {
        return java.lang.Math.max(0, super.computeHorizontalScrollOffset());
    }

    @Override // android.view.ViewGroup
    protected void measureChild(android.view.View view, int i, int i2) {
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(android.view.View.MeasureSpec.makeSafeMeasureSpec(java.lang.Math.max(0, android.view.View.MeasureSpec.getSize(i) - (this.mPaddingLeft + this.mPaddingRight)), 0), getChildMeasureSpec(i2, this.mPaddingTop + this.mPaddingBottom, layoutParams.height));
    }

    @Override // android.view.ViewGroup
    protected void measureChildWithMargins(android.view.View view, int i, int i2, int i3, int i4) {
        android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(android.view.View.MeasureSpec.makeSafeMeasureSpec(java.lang.Math.max(0, android.view.View.MeasureSpec.getSize(i) - ((((this.mPaddingLeft + this.mPaddingRight) + marginLayoutParams.leftMargin) + marginLayoutParams.rightMargin) + i2)), 0), getChildMeasureSpec(i3, this.mPaddingTop + this.mPaddingBottom + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int i = this.mScrollX;
            int i2 = this.mScrollY;
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            int consumeFlingInStretch = consumeFlingInStretch(currX - i);
            if (consumeFlingInStretch != 0 || i2 != currY) {
                int scrollRange = getScrollRange();
                int overScrollMode = getOverScrollMode();
                boolean z = true;
                if (overScrollMode != 0 && (overScrollMode != 1 || scrollRange <= 0)) {
                    z = false;
                }
                boolean z2 = z;
                overScrollBy(consumeFlingInStretch, currY - i2, i, i2, scrollRange, 0, this.mOverflingDistance, 0, false);
                onScrollChanged(this.mScrollX, this.mScrollY, i, i2);
                if (z2 && consumeFlingInStretch != 0) {
                    if (currX < 0 && i >= 0) {
                        this.mEdgeGlowLeft.onAbsorb((int) this.mScroller.getCurrVelocity());
                    } else if (currX > scrollRange && i <= scrollRange) {
                        this.mEdgeGlowRight.onAbsorb((int) this.mScroller.getCurrVelocity());
                    }
                }
            }
            if (!awakenScrollBars()) {
                postInvalidateOnAnimation();
            }
        }
    }

    private int consumeFlingInStretch(int i) {
        int scrollX = getScrollX();
        if (scrollX < 0 || scrollX > getScrollRange()) {
            return i;
        }
        if (i > 0 && this.mEdgeGlowLeft != null && this.mEdgeGlowLeft.getDistance() != 0.0f) {
            int round = java.lang.Math.round(((-r1) / FLING_DESTRETCH_FACTOR) * this.mEdgeGlowLeft.onPullDistance(((-i) * FLING_DESTRETCH_FACTOR) / getWidth(), 0.5f));
            if (round != i) {
                this.mEdgeGlowLeft.finish();
            }
            return i - round;
        }
        if (i < 0 && this.mEdgeGlowRight != null && this.mEdgeGlowRight.getDistance() != 0.0f) {
            float width = getWidth();
            int round2 = java.lang.Math.round((width / FLING_DESTRETCH_FACTOR) * this.mEdgeGlowRight.onPullDistance((i * FLING_DESTRETCH_FACTOR) / width, 0.5f));
            if (round2 != i) {
                this.mEdgeGlowRight.finish();
            }
            return i - round2;
        }
        return i;
    }

    private void scrollToChild(android.view.View view) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
        if (computeScrollDeltaToGetChildRectOnScreen != 0) {
            scrollBy(computeScrollDeltaToGetChildRectOnScreen, 0);
        }
    }

    private boolean scrollToChildRect(android.graphics.Rect rect, boolean z) {
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean z2 = computeScrollDeltaToGetChildRectOnScreen != 0;
        if (z2) {
            if (z) {
                scrollBy(computeScrollDeltaToGetChildRectOnScreen, 0);
            } else {
                smoothScrollBy(computeScrollDeltaToGetChildRectOnScreen, 0);
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
        int width = getWidth();
        int scrollX = getScrollX();
        int i3 = scrollX + width;
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (rect.left > 0) {
            scrollX += horizontalFadingEdgeLength;
        }
        if (rect.right < getChildAt(0).getWidth()) {
            i3 -= horizontalFadingEdgeLength;
        }
        if (rect.right > i3 && rect.left > scrollX) {
            if (rect.width() > width) {
                i2 = (rect.left - scrollX) + 0;
            } else {
                i2 = (rect.right - i3) + 0;
            }
            return java.lang.Math.min(i2, getChildAt(0).getRight() - i3);
        }
        if (rect.left >= scrollX || rect.right >= i3) {
            return 0;
        }
        if (rect.width() > width) {
            i = 0 - (i3 - rect.right);
        } else {
            i = 0 - (scrollX - rect.left);
        }
        return java.lang.Math.max(i, -getScrollX());
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(android.view.View view, android.view.View view2) {
        if (view2 != null && view2.getRevealOnFocusHint()) {
            if (!this.mIsLayoutDirty) {
                scrollToChild(view2);
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
            i = 66;
        } else if (i == 1) {
            i = 17;
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

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        if (getChildCount() <= 0) {
            i5 = 0;
            i6 = 0;
        } else {
            i5 = getChildAt(0).getMeasuredWidth();
            android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) getChildAt(0).getLayoutParams();
            i6 = layoutParams.leftMargin + layoutParams.rightMargin;
        }
        int i8 = i3 - i;
        layoutChildren(i, i2, i3, i4, i5 > ((i8 - getPaddingLeftWithForeground()) - getPaddingRightWithForeground()) - i6);
        this.mIsLayoutDirty = false;
        if (this.mChildToScrollTo != null && isViewDescendantOf(this.mChildToScrollTo, this)) {
            scrollToChild(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (!isLaidOut()) {
            int max = java.lang.Math.max(0, i5 - ((i8 - this.mPaddingLeft) - this.mPaddingRight));
            if (this.mSavedState != null) {
                if (isLayoutRtl()) {
                    i7 = max - this.mSavedState.scrollOffsetFromStart;
                } else {
                    i7 = this.mSavedState.scrollOffsetFromStart;
                }
                this.mScrollX = i7;
                this.mSavedState = null;
            } else if (isLayoutRtl()) {
                this.mScrollX = max - this.mScrollX;
            }
            if (this.mScrollX > max) {
                this.mScrollX = max;
            } else if (this.mScrollX < 0) {
                this.mScrollX = 0;
            }
        }
        scrollTo(this.mScrollX, this.mScrollY);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        android.view.View findFocus = findFocus();
        if (findFocus != null && this != findFocus && isWithinDeltaOfScreen(findFocus, this.mRight - this.mLeft)) {
            findFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(findFocus, this.mTempRect);
            doScrollX(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
        }
    }

    private static boolean isViewDescendantOf(android.view.View view, android.view.View view2) {
        if (view == view2) {
            return true;
        }
        java.lang.Object parent = view.getParent();
        return (parent instanceof android.view.ViewGroup) && isViewDescendantOf((android.view.View) parent, view2);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void fling(int i) {
        boolean z;
        if (getChildCount() > 0) {
            int width = (getWidth() - this.mPaddingRight) - this.mPaddingLeft;
            int max = java.lang.Math.max(0, (getChildAt(0).getRight() - this.mPaddingLeft) - width);
            if (this.mScrollX == 0 && !this.mEdgeGlowLeft.isFinished()) {
                int i2 = -i;
                if (shouldAbsorb(this.mEdgeGlowLeft, i2)) {
                    this.mEdgeGlowLeft.onAbsorb(i2);
                    z = false;
                    if (z) {
                    }
                    postInvalidateOnAnimation();
                }
                z = true;
                if (z) {
                }
                postInvalidateOnAnimation();
            }
            if (this.mScrollX == max && !this.mEdgeGlowRight.isFinished()) {
                if (shouldAbsorb(this.mEdgeGlowRight, i)) {
                    this.mEdgeGlowRight.onAbsorb(i);
                    z = false;
                } else {
                    z = true;
                }
            } else {
                z = true;
            }
            if (z) {
                this.mScroller.fling(this.mScrollX, this.mScrollY, i, 0, 0, max, 0, 0, width / 2, 0);
                boolean z2 = i > 0;
                android.view.View findFocus = findFocus();
                android.view.View findFocusableViewInMyBounds = findFocusableViewInMyBounds(z2, this.mScroller.getFinalX(), findFocus);
                if (findFocusableViewInMyBounds == null) {
                    findFocusableViewInMyBounds = this;
                }
                if (findFocusableViewInMyBounds != findFocus) {
                    findFocusableViewInMyBounds.requestFocus(z2 ? 66 : 17);
                }
            }
            postInvalidateOnAnimation();
        }
    }

    private boolean shouldAbsorb(android.widget.EdgeEffect edgeEffect, int i) {
        if (i > 0) {
            return true;
        }
        return ((float) this.mScroller.getSplineFlingDistance(-i)) < edgeEffect.getDistance() * ((float) getWidth());
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

    private boolean shouldDisplayEdgeEffects() {
        return getOverScrollMode() != 2;
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        super.draw(canvas);
        if (shouldDisplayEdgeEffects()) {
            int i = this.mScrollX;
            if (!this.mEdgeGlowLeft.isFinished()) {
                int save = canvas.save();
                int height = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
                canvas.rotate(270.0f);
                canvas.translate((-height) + this.mPaddingTop, java.lang.Math.min(0, i));
                this.mEdgeGlowLeft.setSize(height, getWidth());
                if (this.mEdgeGlowLeft.draw(canvas)) {
                    postInvalidateOnAnimation();
                }
                canvas.restoreToCount(save);
            }
            if (!this.mEdgeGlowRight.isFinished()) {
                int save2 = canvas.save();
                int width = getWidth();
                int height2 = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
                canvas.rotate(90.0f);
                canvas.translate(-this.mPaddingTop, -(java.lang.Math.max(getScrollRange(), i) + width));
                this.mEdgeGlowRight.setSize(height2, width);
                if (this.mEdgeGlowRight.draw(canvas)) {
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
        android.widget.HorizontalScrollView.SavedState savedState = (android.widget.HorizontalScrollView.SavedState) parcelable;
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
        android.widget.HorizontalScrollView.SavedState savedState = new android.widget.HorizontalScrollView.SavedState(super.onSaveInstanceState());
        savedState.scrollOffsetFromStart = isLayoutRtl() ? -this.mScrollX : this.mScrollX;
        return savedState;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("layout:fillViewPort", this.mFillViewport);
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.widget.HorizontalScrollView.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.HorizontalScrollView.SavedState>() { // from class: android.widget.HorizontalScrollView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.HorizontalScrollView.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.widget.HorizontalScrollView.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.HorizontalScrollView.SavedState[] newArray(int i) {
                return new android.widget.HorizontalScrollView.SavedState[i];
            }
        };
        public int scrollOffsetFromStart;

        SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.scrollOffsetFromStart = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.scrollOffsetFromStart);
        }

        public java.lang.String toString() {
            return "HorizontalScrollView.SavedState{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " scrollPosition=" + this.scrollOffsetFromStart + "}";
        }
    }
}
