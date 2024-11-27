package com.android.internal.widget;

/* loaded from: classes5.dex */
public class WatchListDecorLayout extends android.widget.FrameLayout implements android.view.ViewTreeObserver.OnScrollChangedListener {
    private android.view.View mBottomPanel;
    private int mForegroundPaddingBottom;
    private int mForegroundPaddingLeft;
    private int mForegroundPaddingRight;
    private int mForegroundPaddingTop;
    private android.widget.ListView mListView;
    private final java.util.ArrayList<android.view.View> mMatchParentChildren;
    private android.view.ViewTreeObserver mObserver;
    private int mPendingScroll;
    private android.view.View mTopPanel;

    public WatchListDecorLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mForegroundPaddingLeft = 0;
        this.mForegroundPaddingTop = 0;
        this.mForegroundPaddingRight = 0;
        this.mForegroundPaddingBottom = 0;
        this.mMatchParentChildren = new java.util.ArrayList<>(1);
    }

    public WatchListDecorLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mForegroundPaddingLeft = 0;
        this.mForegroundPaddingTop = 0;
        this.mForegroundPaddingRight = 0;
        this.mForegroundPaddingBottom = 0;
        this.mMatchParentChildren = new java.util.ArrayList<>(1);
    }

    public WatchListDecorLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mForegroundPaddingLeft = 0;
        this.mForegroundPaddingTop = 0;
        this.mForegroundPaddingRight = 0;
        this.mForegroundPaddingBottom = 0;
        this.mMatchParentChildren = new java.util.ArrayList<>(1);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mPendingScroll = 0;
        for (int i = 0; i < getChildCount(); i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt instanceof android.widget.ListView) {
                if (this.mListView != null) {
                    throw new java.lang.IllegalArgumentException("only one ListView child allowed");
                }
                this.mListView = (android.widget.ListView) childAt;
                this.mListView.setNestedScrollingEnabled(true);
                this.mObserver = this.mListView.getViewTreeObserver();
                this.mObserver.addOnScrollChangedListener(this);
            } else {
                int i2 = ((android.widget.FrameLayout.LayoutParams) childAt.getLayoutParams()).gravity & 112;
                if (i2 == 48 && this.mTopPanel == null) {
                    this.mTopPanel = childAt;
                } else if (i2 == 80 && this.mBottomPanel == null) {
                    this.mBottomPanel = childAt;
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        this.mListView = null;
        this.mBottomPanel = null;
        this.mTopPanel = null;
        if (this.mObserver != null) {
            if (this.mObserver.isAlive()) {
                this.mObserver.removeOnScrollChangedListener(this);
            }
            this.mObserver = null;
        }
    }

    private void applyMeasureToChild(android.view.View view, int i, int i2) {
        int childMeasureSpec;
        int childMeasureSpec2;
        android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (marginLayoutParams.width == -1) {
            childMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, (((getMeasuredWidth() - getPaddingLeftWithForeground()) - getPaddingRightWithForeground()) - marginLayoutParams.leftMargin) - marginLayoutParams.rightMargin), 1073741824);
        } else {
            childMeasureSpec = getChildMeasureSpec(i, getPaddingLeftWithForeground() + getPaddingRightWithForeground() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width);
        }
        if (marginLayoutParams.height == -1) {
            childMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, (((getMeasuredHeight() - getPaddingTopWithForeground()) - getPaddingBottomWithForeground()) - marginLayoutParams.topMargin) - marginLayoutParams.bottomMargin), 1073741824);
        } else {
            childMeasureSpec2 = getChildMeasureSpec(i2, getPaddingTopWithForeground() + getPaddingBottomWithForeground() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height);
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    private int measureAndGetHeight(android.view.View view, int i, int i2) {
        if (view != null) {
            if (view.getVisibility() != 8) {
                applyMeasureToChild(this.mBottomPanel, i, i2);
                return view.getMeasuredHeight();
            }
            if (getMeasureAllChildren()) {
                applyMeasureToChild(this.mBottomPanel, i, i2);
                return 0;
            }
            return 0;
        }
        return 0;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        boolean z = (android.view.View.MeasureSpec.getMode(i) == 1073741824 && android.view.View.MeasureSpec.getMode(i2) == 1073741824) ? false : true;
        this.mMatchParentChildren.clear();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            android.view.View childAt = getChildAt(i6);
            if (getMeasureAllChildren() || childAt.getVisibility() != 8) {
                measureChildWithMargins(childAt, i, 0, i2, 0);
                android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) childAt.getLayoutParams();
                i4 = java.lang.Math.max(i4, childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                i5 = java.lang.Math.max(i5, childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                i3 = combineMeasuredStates(i3, childAt.getMeasuredState());
                if (z && (layoutParams.width == -1 || layoutParams.height == -1)) {
                    this.mMatchParentChildren.add(childAt);
                }
            }
        }
        int i7 = i3;
        int paddingLeftWithForeground = i4 + getPaddingLeftWithForeground() + getPaddingRightWithForeground();
        int max = java.lang.Math.max(i5 + getPaddingTopWithForeground() + getPaddingBottomWithForeground(), getSuggestedMinimumHeight());
        int max2 = java.lang.Math.max(paddingLeftWithForeground, getSuggestedMinimumWidth());
        android.graphics.drawable.Drawable foreground = getForeground();
        if (foreground != null) {
            max = java.lang.Math.max(max, foreground.getMinimumHeight());
            max2 = java.lang.Math.max(max2, foreground.getMinimumWidth());
        }
        setMeasuredDimension(resolveSizeAndState(max2, i, i7), resolveSizeAndState(max, i2, i7 << 16));
        if (this.mListView != null) {
            if (this.mPendingScroll != 0) {
                this.mListView.scrollListBy(this.mPendingScroll);
                this.mPendingScroll = 0;
            }
            int max3 = java.lang.Math.max(this.mListView.getPaddingTop(), measureAndGetHeight(this.mTopPanel, i, i2));
            int max4 = java.lang.Math.max(this.mListView.getPaddingBottom(), measureAndGetHeight(this.mBottomPanel, i, i2));
            if (max3 != this.mListView.getPaddingTop() || max4 != this.mListView.getPaddingBottom()) {
                this.mPendingScroll += this.mListView.getPaddingTop() - max3;
                this.mListView.setPadding(this.mListView.getPaddingLeft(), max3, this.mListView.getPaddingRight(), max4);
            }
        }
        int size = this.mMatchParentChildren.size();
        if (size > 1) {
            for (int i8 = 0; i8 < size; i8++) {
                android.view.View view = this.mMatchParentChildren.get(i8);
                if (this.mListView == null || (view != this.mTopPanel && view != this.mBottomPanel)) {
                    applyMeasureToChild(view, i, i2);
                }
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void setForegroundGravity(int i) {
        if (getForegroundGravity() != i) {
            super.setForegroundGravity(i);
            android.graphics.drawable.Drawable foreground = getForeground();
            if (getForegroundGravity() == 119 && foreground != null) {
                android.graphics.Rect rect = new android.graphics.Rect();
                if (foreground.getPadding(rect)) {
                    this.mForegroundPaddingLeft = rect.left;
                    this.mForegroundPaddingTop = rect.top;
                    this.mForegroundPaddingRight = rect.right;
                    this.mForegroundPaddingBottom = rect.bottom;
                    return;
                }
                return;
            }
            this.mForegroundPaddingLeft = 0;
            this.mForegroundPaddingTop = 0;
            this.mForegroundPaddingRight = 0;
            this.mForegroundPaddingBottom = 0;
        }
    }

    private int getPaddingLeftWithForeground() {
        return isForegroundInsidePadding() ? java.lang.Math.max(this.mPaddingLeft, this.mForegroundPaddingLeft) : this.mPaddingLeft + this.mForegroundPaddingLeft;
    }

    private int getPaddingRightWithForeground() {
        return isForegroundInsidePadding() ? java.lang.Math.max(this.mPaddingRight, this.mForegroundPaddingRight) : this.mPaddingRight + this.mForegroundPaddingRight;
    }

    private int getPaddingTopWithForeground() {
        return isForegroundInsidePadding() ? java.lang.Math.max(this.mPaddingTop, this.mForegroundPaddingTop) : this.mPaddingTop + this.mForegroundPaddingTop;
    }

    private int getPaddingBottomWithForeground() {
        return isForegroundInsidePadding() ? java.lang.Math.max(this.mPaddingBottom, this.mForegroundPaddingBottom) : this.mPaddingBottom + this.mForegroundPaddingBottom;
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public void onScrollChanged() {
        if (this.mListView == null) {
            return;
        }
        if (this.mTopPanel != null) {
            if (this.mListView.getChildCount() > 0) {
                if (this.mListView.getFirstVisiblePosition() == 0) {
                    setScrolling(this.mTopPanel, (this.mListView.getChildAt(0).getY() - this.mTopPanel.getHeight()) - this.mTopPanel.getTop());
                } else {
                    setScrolling(this.mTopPanel, -this.mTopPanel.getHeight());
                }
            } else {
                setScrolling(this.mTopPanel, 0.0f);
            }
        }
        if (this.mBottomPanel != null) {
            if (this.mListView.getChildCount() > 0) {
                if (this.mListView.getLastVisiblePosition() >= this.mListView.getCount() - 1) {
                    setScrolling(this.mBottomPanel, java.lang.Math.max(0.0f, (this.mListView.getChildAt(this.mListView.getChildCount() - 1).getY() + r0.getHeight()) - this.mBottomPanel.getTop()));
                    return;
                } else {
                    setScrolling(this.mBottomPanel, this.mBottomPanel.getHeight());
                    return;
                }
            }
            setScrolling(this.mBottomPanel, 0.0f);
        }
    }

    private void setScrolling(android.view.View view, float f) {
        if (view.getTranslationY() != f) {
            view.setTranslationY(f);
        }
    }
}
