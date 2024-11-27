package com.android.internal.widget;

/* loaded from: classes5.dex */
public class ResolverDrawerLayout extends android.view.ViewGroup {
    private static final java.lang.String TAG = "ResolverDrawerLayout";
    private int mActivePointerId;
    private int mAlwaysShowHeight;
    private float mCollapseOffset;
    private int mCollapsibleHeight;
    private int mCollapsibleHeightReserved;
    private boolean mDismissLocked;
    private boolean mDismissOnScrollerFinished;
    private float mDragRemainder;
    private int mIgnoreOffsetTopLimitViewId;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private boolean mIsDragging;
    private final boolean mIsMaxCollapsedHeightSmallExplicit;
    private float mLastTouchY;
    private int mMaxCollapsedHeight;
    private int mMaxCollapsedHeightSmall;
    private int mMaxWidth;
    private int mMaxWidthResId;
    private com.android.internal.logging.MetricsLogger mMetricsLogger;
    private final float mMinFlingVelocity;
    private android.widget.AbsListView mNestedListChild;
    private com.android.internal.widget.RecyclerView mNestedRecyclerChild;
    private com.android.internal.widget.ResolverDrawerLayout.OnCollapsedChangedListener mOnCollapsedChangedListener;
    private com.android.internal.widget.ResolverDrawerLayout.OnDismissedListener mOnDismissedListener;
    private boolean mOpenOnClick;
    private boolean mOpenOnLayout;
    private com.android.internal.widget.ResolverDrawerLayout.RunOnDismissedListener mRunOnDismissedListener;
    private android.graphics.drawable.Drawable mScrollIndicatorDrawable;
    private final android.widget.OverScroller mScroller;
    private boolean mShowAtTop;
    private boolean mSmallCollapsed;
    private final android.graphics.Rect mTempRect;
    private int mTopOffset;
    private final android.view.ViewTreeObserver.OnTouchModeChangeListener mTouchModeChangeListener;
    private final int mTouchSlop;
    private int mUncollapsibleHeight;
    private final android.view.VelocityTracker mVelocityTracker;

    public interface OnCollapsedChangedListener {
        void onCollapsedChanged(boolean z);
    }

    public interface OnDismissedListener {
        void onDismissed();
    }

    public ResolverDrawerLayout(android.content.Context context) {
        this(context, null);
    }

    public ResolverDrawerLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ResolverDrawerLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDragRemainder = 0.0f;
        this.mIgnoreOffsetTopLimitViewId = 0;
        this.mActivePointerId = -1;
        this.mTempRect = new android.graphics.Rect();
        this.mTouchModeChangeListener = new android.view.ViewTreeObserver.OnTouchModeChangeListener() { // from class: com.android.internal.widget.ResolverDrawerLayout.1
            @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
            public void onTouchModeChanged(boolean z) {
                if (!z && com.android.internal.widget.ResolverDrawerLayout.this.hasFocus() && com.android.internal.widget.ResolverDrawerLayout.this.isDescendantClipped(com.android.internal.widget.ResolverDrawerLayout.this.getFocusedChild())) {
                    com.android.internal.widget.ResolverDrawerLayout.this.smoothScrollTo(0, 0.0f);
                }
            }
        };
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ResolverDrawerLayout, i, 0);
        this.mMaxWidthResId = obtainStyledAttributes.getResourceId(0, -1);
        this.mMaxWidth = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        this.mMaxCollapsedHeight = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        this.mMaxCollapsedHeightSmall = obtainStyledAttributes.getDimensionPixelSize(3, this.mMaxCollapsedHeight);
        this.mIsMaxCollapsedHeightSmallExplicit = obtainStyledAttributes.hasValue(3);
        this.mShowAtTop = obtainStyledAttributes.getBoolean(4, false);
        if (obtainStyledAttributes.hasValue(1)) {
            this.mIgnoreOffsetTopLimitViewId = obtainStyledAttributes.getResourceId(1, 0);
        }
        obtainStyledAttributes.recycle();
        this.mScrollIndicatorDrawable = this.mContext.getDrawable(com.android.internal.R.drawable.scroll_indicator_material);
        this.mScroller = new android.widget.OverScroller(context, android.view.animation.AnimationUtils.loadInterpolator(context, 17563653));
        this.mVelocityTracker = android.view.VelocityTracker.obtain();
        this.mTouchSlop = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
        this.mMinFlingVelocity = r4.getScaledMinimumFlingVelocity();
        setImportantForAccessibility(1);
    }

    public void setMaxCollapsedHeight(int i) {
        if (i == this.mMaxCollapsedHeight) {
            return;
        }
        this.mMaxCollapsedHeight = i;
        if (!this.mIsMaxCollapsedHeightSmallExplicit) {
            this.mMaxCollapsedHeightSmall = this.mMaxCollapsedHeight;
        }
        requestLayout();
    }

    public void setSmallCollapsed(boolean z) {
        if (this.mSmallCollapsed != z) {
            this.mSmallCollapsed = z;
            requestLayout();
        }
    }

    public boolean isSmallCollapsed() {
        return this.mSmallCollapsed;
    }

    public boolean isCollapsed() {
        return this.mCollapseOffset > 0.0f;
    }

    public void setShowAtTop(boolean z) {
        if (this.mShowAtTop != z) {
            this.mShowAtTop = z;
            requestLayout();
        }
    }

    public boolean getShowAtTop() {
        return this.mShowAtTop;
    }

    public void setCollapsed(boolean z) {
        if (!isLaidOut()) {
            this.mOpenOnLayout = !z;
        } else {
            smoothScrollTo(z ? this.mCollapsibleHeight : 0, 0.0f);
        }
    }

    public void setCollapsibleHeightReserved(int i) {
        int i2 = this.mCollapsibleHeightReserved;
        this.mCollapsibleHeightReserved = i;
        if (i2 != this.mCollapsibleHeightReserved) {
            requestLayout();
        }
        int i3 = this.mCollapsibleHeightReserved - i2;
        if (i3 != 0 && this.mIsDragging) {
            this.mLastTouchY -= i3;
        }
        int i4 = this.mCollapsibleHeight;
        this.mCollapsibleHeight = java.lang.Math.min(this.mCollapsibleHeight, getMaxCollapsedHeight());
        if (updateCollapseOffset(i4, !isDragging())) {
            return;
        }
        invalidate();
    }

    public void setDismissLocked(boolean z) {
        this.mDismissLocked = z;
    }

    private boolean isMoving() {
        return this.mIsDragging || !this.mScroller.isFinished();
    }

    private boolean isDragging() {
        return this.mIsDragging || getNestedScrollAxes() == 2;
    }

    private boolean updateCollapseOffset(int i, boolean z) {
        if (i == this.mCollapsibleHeight) {
            return false;
        }
        if (getShowAtTop()) {
            setCollapseOffset(0.0f);
            return false;
        }
        if (isLaidOut()) {
            boolean z2 = this.mCollapseOffset != 0.0f;
            if (z && i < this.mCollapsibleHeight && this.mCollapseOffset == i) {
                setCollapseOffset(this.mCollapsibleHeight);
            } else {
                setCollapseOffset(java.lang.Math.min(this.mCollapseOffset, this.mCollapsibleHeight));
            }
            boolean z3 = this.mCollapseOffset != 0.0f;
            if (z2 != z3) {
                onCollapsedChanged(z3);
            }
        } else {
            setCollapseOffset(this.mOpenOnLayout ? 0.0f : this.mCollapsibleHeight);
        }
        return true;
    }

    private void setCollapseOffset(float f) {
        if (this.mCollapseOffset != f) {
            this.mCollapseOffset = f;
            requestLayout();
        }
    }

    private int getMaxCollapsedHeight() {
        return (isSmallCollapsed() ? this.mMaxCollapsedHeightSmall : this.mMaxCollapsedHeight) + this.mCollapsibleHeightReserved;
    }

    public void setOnDismissedListener(com.android.internal.widget.ResolverDrawerLayout.OnDismissedListener onDismissedListener) {
        this.mOnDismissedListener = onDismissedListener;
    }

    private boolean isDismissable() {
        return (this.mOnDismissedListener == null || this.mDismissLocked) ? false : true;
    }

    public void setOnCollapsedChangedListener(com.android.internal.widget.ResolverDrawerLayout.OnCollapsedChangedListener onCollapsedChangedListener) {
        this.mOnCollapsedChangedListener = onCollapsedChangedListener;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mVelocityTracker.clear();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        switch (actionMasked) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                this.mInitialTouchX = x;
                this.mLastTouchY = y;
                this.mInitialTouchY = y;
                this.mOpenOnClick = isListChildUnderClipped(x, y) && this.mCollapseOffset > 0.0f;
                break;
            case 1:
            case 3:
                resetTouch();
                break;
            case 2:
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                float f = y2 - this.mInitialTouchY;
                if (java.lang.Math.abs(f) > this.mTouchSlop && findChildUnder(x2, y2) != null && (getNestedScrollAxes() & 2) == 0) {
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    this.mIsDragging = true;
                    this.mLastTouchY = java.lang.Math.max(this.mLastTouchY - this.mTouchSlop, java.lang.Math.min(this.mLastTouchY + f, this.mLastTouchY + this.mTouchSlop));
                    break;
                }
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                break;
        }
        if (this.mIsDragging) {
            abortAnimation();
        }
        return this.mIsDragging || this.mOpenOnClick;
    }

    private boolean isNestedListChildScrolled() {
        if (this.mNestedListChild == null || this.mNestedListChild.getChildCount() <= 0) {
            return false;
        }
        return this.mNestedListChild.getFirstVisiblePosition() > 0 || this.mNestedListChild.getChildAt(0).getTop() < 0;
    }

    private boolean isNestedRecyclerChildScrolled() {
        if (this.mNestedRecyclerChild == null || this.mNestedRecyclerChild.getChildCount() <= 0) {
            return false;
        }
        com.android.internal.widget.RecyclerView.ViewHolder findViewHolderForAdapterPosition = this.mNestedRecyclerChild.findViewHolderForAdapterPosition(0);
        return findViewHolderForAdapterPosition == null || findViewHolderForAdapterPosition.itemView.getTop() < 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00cb  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        this.mVelocityTracker.addMovement(motionEvent);
        switch (actionMasked) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                this.mInitialTouchX = x;
                this.mLastTouchY = y;
                this.mInitialTouchY = y;
                this.mActivePointerId = motionEvent.getPointerId(0);
                boolean z = findChildUnder(this.mInitialTouchX, this.mInitialTouchY) != null;
                boolean z2 = isDismissable() || this.mCollapsibleHeight > 0;
                this.mIsDragging = z && z2;
                abortAnimation();
                break;
            case 1:
                boolean z3 = this.mIsDragging;
                this.mIsDragging = false;
                if (!z3 && findChildUnder(this.mInitialTouchX, this.mInitialTouchY) == null && findChildUnder(motionEvent.getX(), motionEvent.getY()) == null && isDismissable()) {
                    dispatchOnDismissed();
                    resetTouch();
                    break;
                } else if (this.mOpenOnClick && java.lang.Math.abs(motionEvent.getX() - this.mInitialTouchX) < this.mTouchSlop && java.lang.Math.abs(motionEvent.getY() - this.mInitialTouchY) < this.mTouchSlop) {
                    smoothScrollTo(0, 0.0f);
                    break;
                } else {
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                    if (java.lang.Math.abs(yVelocity) > this.mMinFlingVelocity) {
                        if (getShowAtTop()) {
                            if (!isDismissable() || yVelocity >= 0.0f) {
                                smoothScrollTo(yVelocity < 0.0f ? 0 : this.mCollapsibleHeight, yVelocity);
                            } else {
                                abortAnimation();
                                dismiss();
                            }
                        } else if (isDismissable() && yVelocity > 0.0f && this.mCollapseOffset > this.mCollapsibleHeight) {
                            smoothScrollTo(this.mCollapsibleHeight + this.mUncollapsibleHeight, yVelocity);
                            this.mDismissOnScrollerFinished = true;
                        } else {
                            scrollNestedScrollableChildBackToTop();
                            smoothScrollTo(yVelocity < 0.0f ? 0 : this.mCollapsibleHeight, yVelocity);
                        }
                    } else {
                        smoothScrollTo(this.mCollapseOffset < ((float) (this.mCollapsibleHeight / 2)) ? 0 : this.mCollapsibleHeight, 0.0f);
                    }
                    resetTouch();
                    break;
                }
                break;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex < 0) {
                    android.util.Log.e(TAG, "Bad pointer id " + this.mActivePointerId + ", resetting");
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    this.mInitialTouchX = motionEvent.getX();
                    float y2 = motionEvent.getY();
                    this.mLastTouchY = y2;
                    this.mInitialTouchY = y2;
                    findPointerIndex = 0;
                }
                float x2 = motionEvent.getX(findPointerIndex);
                float y3 = motionEvent.getY(findPointerIndex);
                if (!this.mIsDragging) {
                    float f = y3 - this.mInitialTouchY;
                    if (java.lang.Math.abs(f) > this.mTouchSlop && findChildUnder(x2, y3) != null) {
                        this.mIsDragging = true;
                        this.mLastTouchY = java.lang.Math.max(this.mLastTouchY - this.mTouchSlop, java.lang.Math.min(this.mLastTouchY + f, this.mLastTouchY + this.mTouchSlop));
                        if (this.mIsDragging) {
                            float f2 = y3 - this.mLastTouchY;
                            if (f2 > 0.0f && isNestedListChildScrolled()) {
                                this.mNestedListChild.smoothScrollBy((int) (-f2), 0);
                            } else if (f2 > 0.0f && isNestedRecyclerChildScrolled()) {
                                this.mNestedRecyclerChild.scrollBy(0, (int) (-f2));
                            } else {
                                performDrag(f2);
                            }
                        }
                        this.mLastTouchY = y3;
                        break;
                    }
                }
                r2 = false;
                if (this.mIsDragging) {
                }
                this.mLastTouchY = y3;
                break;
            case 3:
                if (this.mIsDragging) {
                    smoothScrollTo(this.mCollapseOffset >= ((float) (this.mCollapsibleHeight / 2)) ? this.mCollapsibleHeight : 0, 0.0f);
                }
                resetTouch();
                break;
            case 5:
                int actionIndex = motionEvent.getActionIndex();
                this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                this.mInitialTouchX = motionEvent.getX(actionIndex);
                float y4 = motionEvent.getY(actionIndex);
                this.mLastTouchY = y4;
                this.mInitialTouchY = y4;
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                break;
        }
        return true;
    }

    public void scrollNestedScrollableChildBackToTop() {
        if (isNestedListChildScrolled()) {
            this.mNestedListChild.smoothScrollToPosition(0);
        } else if (isNestedRecyclerChildScrolled()) {
            this.mNestedRecyclerChild.smoothScrollToPosition(0);
        }
    }

    private void onSecondaryPointerUp(android.view.MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mInitialTouchX = motionEvent.getX(i);
            float y = motionEvent.getY(i);
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
            this.mActivePointerId = motionEvent.getPointerId(i);
        }
    }

    private void resetTouch() {
        this.mActivePointerId = -1;
        this.mIsDragging = false;
        this.mOpenOnClick = false;
        this.mLastTouchY = 0.0f;
        this.mInitialTouchY = 0.0f;
        this.mInitialTouchX = 0.0f;
        this.mVelocityTracker.clear();
    }

    private void dismiss() {
        this.mRunOnDismissedListener = new com.android.internal.widget.ResolverDrawerLayout.RunOnDismissedListener();
        post(this.mRunOnDismissedListener);
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.mScroller.computeScrollOffset()) {
            boolean z = !this.mScroller.isFinished();
            performDrag(this.mScroller.getCurrY() - this.mCollapseOffset);
            if (z) {
                postInvalidateOnAnimation();
            } else if (this.mDismissOnScrollerFinished && this.mOnDismissedListener != null) {
                dismiss();
            }
        }
    }

    private void abortAnimation() {
        this.mScroller.abortAnimation();
        this.mRunOnDismissedListener = null;
        this.mDismissOnScrollerFinished = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v6 */
    private float performDrag(float f) {
        int i;
        boolean z;
        if (getShowAtTop()) {
            return 0.0f;
        }
        float max = java.lang.Math.max(0.0f, java.lang.Math.min(this.mCollapseOffset + f, this.mCollapsibleHeight + this.mUncollapsibleHeight));
        if (max == this.mCollapseOffset) {
            return 0.0f;
        }
        float f2 = max - this.mCollapseOffset;
        this.mDragRemainder += f2 - ((int) f2);
        if (this.mDragRemainder >= 1.0f) {
            this.mDragRemainder -= 1.0f;
            f2 += 1.0f;
        } else if (this.mDragRemainder <= -1.0f) {
            this.mDragRemainder += 1.0f;
            f2 -= 1.0f;
        }
        android.view.View findIgnoreOffsetLimitView = findIgnoreOffsetLimitView();
        if (findIgnoreOffsetLimitView == null) {
            i = 0;
            z = false;
        } else {
            i = findIgnoreOffsetLimitView.getBottom() + ((com.android.internal.widget.ResolverDrawerLayout.LayoutParams) findIgnoreOffsetLimitView.getLayoutParams()).bottomMargin;
            z = true;
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            android.view.View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                com.android.internal.widget.ResolverDrawerLayout.LayoutParams layoutParams = (com.android.internal.widget.ResolverDrawerLayout.LayoutParams) childAt.getLayoutParams();
                if (!layoutParams.ignoreOffset) {
                    childAt.offsetTopAndBottom((int) f2);
                } else if (z) {
                    int top = childAt.getTop();
                    int max2 = java.lang.Math.max((int) (i + layoutParams.topMargin + f2), layoutParams.mFixedTop);
                    if (top != max2) {
                        childAt.offsetTopAndBottom(max2 - top);
                    }
                    i = childAt.getBottom() + layoutParams.bottomMargin;
                }
            }
        }
        boolean z2 = this.mCollapseOffset != 0.0f;
        this.mCollapseOffset = max;
        this.mTopOffset = (int) (this.mTopOffset + f2);
        ?? r3 = max == 0.0f ? 0 : 1;
        if (z2 != r3) {
            onCollapsedChanged(r3);
            getMetricsLogger().write(new android.metrics.LogMaker(com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_SHARESHEET_COLLAPSED_CHANGED).setSubtype(r3));
        }
        onScrollChanged(0, (int) max, 0, (int) (max - f2));
        postInvalidateOnAnimation();
        return f2;
    }

    private void onCollapsedChanged(boolean z) {
        notifyViewAccessibilityStateChangedIfNeeded(0);
        if (this.mScrollIndicatorDrawable != null) {
            setWillNotDraw(!z);
        }
        if (this.mOnCollapsedChangedListener != null) {
            this.mOnCollapsedChangedListener.onCollapsedChanged(z);
        }
    }

    void dispatchOnDismissed() {
        if (this.mOnDismissedListener != null) {
            this.mOnDismissedListener.onDismissed();
        }
        if (this.mRunOnDismissedListener != null) {
            removeCallbacks(this.mRunOnDismissedListener);
            this.mRunOnDismissedListener = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void smoothScrollTo(int i, float f) {
        int abs;
        abortAnimation();
        int i2 = (int) this.mCollapseOffset;
        int i3 = i - i2;
        if (i3 == 0) {
            return;
        }
        int height = getHeight();
        int i4 = height / 2;
        float f2 = height;
        float f3 = i4;
        float distanceInfluenceForSnapDuration = f3 + (distanceInfluenceForSnapDuration(java.lang.Math.min(1.0f, (java.lang.Math.abs(i3) * 1.0f) / f2)) * f3);
        float abs2 = java.lang.Math.abs(f);
        if (abs2 > 0.0f) {
            abs = java.lang.Math.round(java.lang.Math.abs(distanceInfluenceForSnapDuration / abs2) * 1000.0f) * 4;
        } else {
            abs = (int) (((java.lang.Math.abs(i3) / f2) + 1.0f) * 100.0f);
        }
        this.mScroller.startScroll(0, i2, 0, i3, java.lang.Math.min(abs, 300));
        postInvalidateOnAnimation();
    }

    private float distanceInfluenceForSnapDuration(float f) {
        return (float) java.lang.Math.sin((float) ((f - 0.5f) * 0.4712389167638204d));
    }

    private android.view.View findChildUnder(float f, float f2) {
        return findChildUnder(this, f, f2);
    }

    private static android.view.View findChildUnder(android.view.ViewGroup viewGroup, float f, float f2) {
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            android.view.View childAt = viewGroup.getChildAt(childCount);
            if (isChildUnder(childAt, f, f2)) {
                return childAt;
            }
        }
        return null;
    }

    private android.view.View findListChildUnder(float f, float f2) {
        android.view.View findChildUnder = findChildUnder(f, f2);
        while (findChildUnder != null) {
            f -= findChildUnder.getX();
            f2 -= findChildUnder.getY();
            if (findChildUnder instanceof android.widget.AbsListView) {
                return findChildUnder((android.view.ViewGroup) findChildUnder, f, f2);
            }
            findChildUnder = findChildUnder instanceof android.view.ViewGroup ? findChildUnder((android.view.ViewGroup) findChildUnder, f, f2) : null;
        }
        return findChildUnder;
    }

    private boolean isListChildUnderClipped(float f, float f2) {
        android.view.View findListChildUnder = findListChildUnder(f, f2);
        return findListChildUnder != null && isDescendantClipped(findListChildUnder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDescendantClipped(android.view.View view) {
        this.mTempRect.set(0, 0, view.getWidth(), view.getHeight());
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        if (view.getParent() != this) {
            android.view.ViewParent parent = view.getParent();
            while (parent != this) {
                view = parent;
                parent = view.getParent();
            }
        }
        int height = getHeight() - getPaddingBottom();
        int childCount = getChildCount();
        for (int indexOfChild = indexOfChild(view) + 1; indexOfChild < childCount; indexOfChild++) {
            android.view.View childAt = getChildAt(indexOfChild);
            if (childAt.getVisibility() != 8) {
                height = java.lang.Math.min(height, childAt.getTop());
            }
        }
        return this.mTempRect.bottom > height;
    }

    private static boolean isChildUnder(android.view.View view, float f, float f2) {
        float x = view.getX();
        float y = view.getY();
        return f >= x && f2 >= y && f < ((float) view.getWidth()) + x && f2 < ((float) view.getHeight()) + y;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(android.view.View view, android.view.View view2) {
        super.requestChildFocus(view, view2);
        if (!isInTouchMode() && isDescendantClipped(view2)) {
            smoothScrollTo(0, 0.0f);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnTouchModeChangeListener(this.mTouchModeChangeListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnTouchModeChangeListener(this.mTouchModeChangeListener);
        abortAnimation();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(android.view.View view, android.view.View view2, int i) {
        if ((i & 2) != 0) {
            if (view2 instanceof android.widget.AbsListView) {
                this.mNestedListChild = (android.widget.AbsListView) view2;
            }
            if (view2 instanceof com.android.internal.widget.RecyclerView) {
                this.mNestedRecyclerChild = (com.android.internal.widget.RecyclerView) view2;
                return true;
            }
            return true;
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(android.view.View view, android.view.View view2, int i) {
        super.onNestedScrollAccepted(view, view2, i);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onStopNestedScroll(android.view.View view) {
        super.onStopNestedScroll(view);
        if (this.mScroller.isFinished()) {
            smoothScrollTo(this.mCollapseOffset < ((float) (this.mCollapsibleHeight / 2)) ? 0 : this.mCollapsibleHeight, 0.0f);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(android.view.View view, int i, int i2, int i3, int i4) {
        if (i4 < 0) {
            performDrag(-i4);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedPreScroll(android.view.View view, int i, int i2, int[] iArr) {
        if (i2 > 0) {
            iArr[1] = (int) (-performDrag(-i2));
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedPreFling(android.view.View view, float f, float f2) {
        if (getShowAtTop() || f2 <= this.mMinFlingVelocity || this.mCollapseOffset == 0.0f) {
            return false;
        }
        smoothScrollTo(0, f2);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedFling(android.view.View view, float f, float f2, boolean z) {
        if (z || java.lang.Math.abs(f2) <= this.mMinFlingVelocity) {
            return false;
        }
        if (getShowAtTop()) {
            if (isDismissable() && f2 > 0.0f) {
                abortAnimation();
                dismiss();
            } else {
                smoothScrollTo(f2 < 0.0f ? this.mCollapsibleHeight : 0, f2);
            }
        } else if (isDismissable() && f2 < 0.0f && this.mCollapseOffset > this.mCollapsibleHeight) {
            smoothScrollTo(this.mCollapsibleHeight + this.mUncollapsibleHeight, f2);
            this.mDismissOnScrollerFinished = true;
        } else {
            smoothScrollTo(f2 <= 0.0f ? this.mCollapsibleHeight : 0, f2);
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean performAccessibilityActionCommon(int i) {
        switch (i) {
            case 4096:
            case 262144:
            case 16908346:
                if (this.mCollapseOffset != 0.0f) {
                    smoothScrollTo(0, 0.0f);
                    return true;
                }
                return false;
            case 524288:
                if (this.mCollapseOffset < this.mCollapsibleHeight) {
                    smoothScrollTo(this.mCollapsibleHeight, 0.0f);
                    return true;
                }
                return false;
            case 1048576:
                if (this.mCollapseOffset < this.mCollapsibleHeight + this.mUncollapsibleHeight && isDismissable()) {
                    smoothScrollTo(this.mCollapsibleHeight + this.mUncollapsibleHeight, 0.0f);
                    this.mDismissOnScrollerFinished = true;
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedPrePerformAccessibilityAction(android.view.View view, int i, android.os.Bundle bundle) {
        if (super.onNestedPrePerformAccessibilityAction(view, i, bundle)) {
            return true;
        }
        return performAccessibilityActionCommon(i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.ScrollView.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (isEnabled()) {
            if (this.mCollapseOffset != 0.0f) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
                accessibilityNodeInfo.setScrollable(true);
            }
            if (this.mCollapseOffset < this.mCollapsibleHeight + this.mUncollapsibleHeight && (this.mCollapseOffset < this.mCollapsibleHeight || isDismissable())) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
                accessibilityNodeInfo.setScrollable(true);
            }
            if (this.mCollapseOffset < this.mCollapsibleHeight) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
            }
            if (this.mCollapseOffset < this.mCollapsibleHeight + this.mUncollapsibleHeight && isDismissable()) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS);
            }
        }
        accessibilityNodeInfo.removeAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS);
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, android.os.Bundle bundle) {
        if (i == android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS.getId()) {
            return false;
        }
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        return performAccessibilityActionCommon(i);
    }

    @Override // android.view.View
    public void onDrawForeground(android.graphics.Canvas canvas) {
        if (this.mScrollIndicatorDrawable != null) {
            this.mScrollIndicatorDrawable.draw(canvas);
        }
        super.onDrawForeground(canvas);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int size = android.view.View.MeasureSpec.getSize(i);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        if (this.mMaxWidth < 0) {
            i3 = size;
        } else {
            i3 = java.lang.Math.min(size, this.mMaxWidth + getPaddingLeft() + getPaddingRight());
        }
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
        int makeMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(size2, 1073741824);
        int childCount = getChildCount();
        int i8 = 0;
        int i9 = 0;
        while (true) {
            i4 = -1;
            i5 = 8;
            if (i8 >= childCount) {
                break;
            }
            android.view.View childAt = getChildAt(i8);
            com.android.internal.widget.ResolverDrawerLayout.LayoutParams layoutParams = (com.android.internal.widget.ResolverDrawerLayout.LayoutParams) childAt.getLayoutParams();
            if (layoutParams.alwaysShow && childAt.getVisibility() != 8) {
                if (layoutParams.maxHeight != -1) {
                    int i10 = size2 - i9;
                    measureChildWithMargins(childAt, makeMeasureSpec, 0, android.view.View.MeasureSpec.makeMeasureSpec(layoutParams.maxHeight, Integer.MIN_VALUE), layoutParams.maxHeight > i10 ? layoutParams.maxHeight - i10 : 0);
                } else {
                    measureChildWithMargins(childAt, makeMeasureSpec, 0, makeMeasureSpec2, i9);
                }
                i9 += childAt.getMeasuredHeight();
            }
            i8++;
        }
        this.mAlwaysShowHeight = i9;
        int i11 = 0;
        while (i11 < childCount) {
            android.view.View childAt2 = getChildAt(i11);
            com.android.internal.widget.ResolverDrawerLayout.LayoutParams layoutParams2 = (com.android.internal.widget.ResolverDrawerLayout.LayoutParams) childAt2.getLayoutParams();
            if (layoutParams2.alwaysShow || childAt2.getVisibility() == i5) {
                i6 = i5;
                i7 = i4;
            } else {
                if (layoutParams2.maxHeight != i4) {
                    int i12 = size2 - i9;
                    i6 = i5;
                    i7 = i4;
                    measureChildWithMargins(childAt2, makeMeasureSpec, 0, android.view.View.MeasureSpec.makeMeasureSpec(layoutParams2.maxHeight, Integer.MIN_VALUE), layoutParams2.maxHeight > i12 ? layoutParams2.maxHeight - i12 : 0);
                } else {
                    i6 = i5;
                    i7 = i4;
                    measureChildWithMargins(childAt2, makeMeasureSpec, 0, makeMeasureSpec2, i9);
                }
                i9 += childAt2.getMeasuredHeight();
            }
            i11++;
            i4 = i7;
            i5 = i6;
        }
        int i13 = this.mCollapsibleHeight;
        this.mCollapsibleHeight = java.lang.Math.max(0, (i9 - this.mAlwaysShowHeight) - getMaxCollapsedHeight());
        this.mUncollapsibleHeight = i9 - this.mCollapsibleHeight;
        updateCollapseOffset(i13, !isDragging());
        if (!getShowAtTop()) {
            this.mTopOffset = java.lang.Math.max(0, size2 - i9) + ((int) this.mCollapseOffset);
        } else {
            this.mTopOffset = 0;
        }
        setMeasuredDimension(size, size2);
    }

    public int getAlwaysShowHeight() {
        return this.mAlwaysShowHeight;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mMaxWidthResId > 0) {
            this.mMaxWidth = getResources().getDimensionPixelSize(this.mMaxWidthResId);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int width = getWidth();
        int i5 = this.mTopOffset;
        int paddingLeft = getPaddingLeft();
        int paddingRight = (width - getPaddingRight()) - paddingLeft;
        int childCount = getChildCount();
        android.view.View view = null;
        boolean z2 = false;
        int i6 = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            android.view.View childAt = getChildAt(i7);
            com.android.internal.widget.ResolverDrawerLayout.LayoutParams layoutParams = (com.android.internal.widget.ResolverDrawerLayout.LayoutParams) childAt.getLayoutParams();
            if (layoutParams.hasNestedScrollIndicator) {
                view = childAt;
            }
            if (childAt.getVisibility() != 8) {
                if (this.mIgnoreOffsetTopLimitViewId != 0 && !z2 && this.mIgnoreOffsetTopLimitViewId == childAt.getId()) {
                    i6 = layoutParams.bottomMargin + childAt.getBottom();
                    z2 = true;
                }
                int i8 = i5 + layoutParams.topMargin;
                if (layoutParams.ignoreOffset) {
                    if (!isDragging()) {
                        layoutParams.mFixedTop = (int) (i8 - this.mCollapseOffset);
                    }
                    if (z2) {
                        i8 = java.lang.Math.max(i6 + layoutParams.topMargin, (int) (i8 - this.mCollapseOffset));
                        i6 = childAt.getMeasuredHeight() + i8 + layoutParams.bottomMargin;
                    } else {
                        i8 = (int) (i8 - this.mCollapseOffset);
                    }
                }
                int measuredHeight = childAt.getMeasuredHeight() + i8;
                int measuredWidth = childAt.getMeasuredWidth();
                int i9 = ((paddingRight - measuredWidth) / 2) + paddingLeft;
                childAt.layout(i9, i8, measuredWidth + i9, measuredHeight);
                i5 = measuredHeight + layoutParams.bottomMargin;
            }
        }
        if (this.mScrollIndicatorDrawable != null) {
            if (view != null) {
                int left = view.getLeft();
                int right = view.getRight();
                int top = view.getTop();
                this.mScrollIndicatorDrawable.setBounds(left, top - this.mScrollIndicatorDrawable.getIntrinsicHeight(), right, top);
                setWillNotDraw(!isCollapsed());
                return;
            }
            this.mScrollIndicatorDrawable = null;
            setWillNotDraw(true);
        }
    }

    @Override // android.view.ViewGroup
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new com.android.internal.widget.ResolverDrawerLayout.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof com.android.internal.widget.ResolverDrawerLayout.LayoutParams) {
            return new com.android.internal.widget.ResolverDrawerLayout.LayoutParams((com.android.internal.widget.ResolverDrawerLayout.LayoutParams) layoutParams);
        }
        if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
            return new com.android.internal.widget.ResolverDrawerLayout.LayoutParams((android.view.ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new com.android.internal.widget.ResolverDrawerLayout.LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new com.android.internal.widget.ResolverDrawerLayout.LayoutParams(-1, -2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        com.android.internal.widget.ResolverDrawerLayout.SavedState savedState = new com.android.internal.widget.ResolverDrawerLayout.SavedState(super.onSaveInstanceState());
        savedState.open = this.mCollapsibleHeight > 0 && this.mCollapseOffset == 0.0f;
        savedState.mCollapsibleHeightReserved = this.mCollapsibleHeightReserved;
        return savedState;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        com.android.internal.widget.ResolverDrawerLayout.SavedState savedState = (com.android.internal.widget.ResolverDrawerLayout.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mOpenOnLayout = savedState.open;
        this.mCollapsibleHeightReserved = savedState.mCollapsibleHeightReserved;
    }

    private android.view.View findIgnoreOffsetLimitView() {
        android.view.View findViewById;
        if (this.mIgnoreOffsetTopLimitViewId == 0 || (findViewById = findViewById(this.mIgnoreOffsetTopLimitViewId)) == null || findViewById == this || findViewById.getParent() != this || findViewById.getVisibility() == 8) {
            return null;
        }
        return findViewById;
    }

    public static class LayoutParams extends android.view.ViewGroup.MarginLayoutParams {
        public boolean alwaysShow;
        public boolean hasNestedScrollIndicator;
        public boolean ignoreOffset;
        int mFixedTop;
        public int maxHeight;

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ResolverDrawerLayout_LayoutParams);
            this.alwaysShow = obtainStyledAttributes.getBoolean(1, false);
            this.ignoreOffset = obtainStyledAttributes.getBoolean(3, false);
            this.hasNestedScrollIndicator = obtainStyledAttributes.getBoolean(2, false);
            this.maxHeight = obtainStyledAttributes.getDimensionPixelSize(4, -1);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(com.android.internal.widget.ResolverDrawerLayout.LayoutParams layoutParams) {
            super((android.view.ViewGroup.MarginLayoutParams) layoutParams);
            this.alwaysShow = layoutParams.alwaysShow;
            this.ignoreOffset = layoutParams.ignoreOffset;
            this.hasNestedScrollIndicator = layoutParams.hasNestedScrollIndicator;
            this.maxHeight = layoutParams.maxHeight;
        }

        public LayoutParams(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<com.android.internal.widget.ResolverDrawerLayout.SavedState> CREATOR = new android.os.Parcelable.Creator<com.android.internal.widget.ResolverDrawerLayout.SavedState>() { // from class: com.android.internal.widget.ResolverDrawerLayout.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.widget.ResolverDrawerLayout.SavedState createFromParcel(android.os.Parcel parcel) {
                return new com.android.internal.widget.ResolverDrawerLayout.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.widget.ResolverDrawerLayout.SavedState[] newArray(int i) {
                return new com.android.internal.widget.ResolverDrawerLayout.SavedState[i];
            }
        };
        private int mCollapsibleHeightReserved;
        boolean open;

        SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.open = parcel.readInt() != 0;
            this.mCollapsibleHeightReserved = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.open ? 1 : 0);
            parcel.writeInt(this.mCollapsibleHeightReserved);
        }
    }

    private class RunOnDismissedListener implements java.lang.Runnable {
        private RunOnDismissedListener() {
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.internal.widget.ResolverDrawerLayout.this.dispatchOnDismissed();
        }
    }

    private com.android.internal.logging.MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new com.android.internal.logging.MetricsLogger();
        }
        return this.mMetricsLogger;
    }
}
