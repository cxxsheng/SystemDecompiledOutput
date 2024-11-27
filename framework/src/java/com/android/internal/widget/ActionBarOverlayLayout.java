package com.android.internal.widget;

/* loaded from: classes5.dex */
public class ActionBarOverlayLayout extends android.view.ViewGroup implements com.android.internal.widget.DecorContentParent {
    public static final android.util.Property<com.android.internal.widget.ActionBarOverlayLayout, java.lang.Integer> ACTION_BAR_HIDE_OFFSET = new android.util.IntProperty<com.android.internal.widget.ActionBarOverlayLayout>("actionBarHideOffset") { // from class: com.android.internal.widget.ActionBarOverlayLayout.5
        @Override // android.util.IntProperty
        public void setValue(com.android.internal.widget.ActionBarOverlayLayout actionBarOverlayLayout, int i) {
            actionBarOverlayLayout.setActionBarHideOffset(i);
        }

        @Override // android.util.Property
        public java.lang.Integer get(com.android.internal.widget.ActionBarOverlayLayout actionBarOverlayLayout) {
            return java.lang.Integer.valueOf(actionBarOverlayLayout.getActionBarHideOffset());
        }
    };
    static final int[] ATTRS = {16843499, 16842841};
    private static final java.lang.String TAG = "ActionBarOverlayLayout";
    private final int ACTION_BAR_ANIMATE_DELAY;
    private com.android.internal.widget.ActionBarContainer mActionBarBottom;
    private int mActionBarHeight;
    private com.android.internal.widget.ActionBarContainer mActionBarTop;
    private com.android.internal.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback mActionBarVisibilityCallback;
    private final java.lang.Runnable mAddActionBarHideOffset;
    private boolean mAnimatingForFling;
    private final android.graphics.Rect mBaseContentInsets;
    private android.view.WindowInsets mBaseInnerInsets;
    private final android.animation.Animator.AnimatorListener mBottomAnimatorListener;
    private android.view.View mContent;
    private final android.graphics.Rect mContentInsets;
    private android.view.ViewPropertyAnimator mCurrentActionBarBottomAnimator;
    private android.view.ViewPropertyAnimator mCurrentActionBarTopAnimator;
    private com.android.internal.widget.DecorToolbar mDecorToolbar;
    private android.widget.OverScroller mFlingEstimator;
    private boolean mHasNonEmbeddedTabs;
    private boolean mHideOnContentScroll;
    private int mHideOnContentScrollReference;
    private boolean mIgnoreWindowContentOverlay;
    private android.view.WindowInsets mInnerInsets;
    private final android.graphics.Rect mLastBaseContentInsets;
    private android.view.WindowInsets mLastBaseInnerInsets;
    private android.view.WindowInsets mLastInnerInsets;
    private int mLastSystemUiVisibility;
    private boolean mOverlayMode;
    private final java.lang.Runnable mRemoveActionBarHideOffset;
    private final android.animation.Animator.AnimatorListener mTopAnimatorListener;
    private android.graphics.drawable.Drawable mWindowContentOverlay;
    private int mWindowVisibility;

    public interface ActionBarVisibilityCallback {
        void enableContentAnimations(boolean z);

        void hideForSystem();

        void onContentScrollStarted();

        void onContentScrollStopped();

        void onWindowVisibilityChanged(int i);

        void showForSystem();
    }

    public ActionBarOverlayLayout(android.content.Context context) {
        super(context);
        this.mWindowVisibility = 0;
        this.mBaseContentInsets = new android.graphics.Rect();
        this.mLastBaseContentInsets = new android.graphics.Rect();
        this.mContentInsets = new android.graphics.Rect();
        this.mBaseInnerInsets = android.view.WindowInsets.CONSUMED;
        this.mLastBaseInnerInsets = android.view.WindowInsets.CONSUMED;
        this.mInnerInsets = android.view.WindowInsets.CONSUMED;
        this.mLastInnerInsets = android.view.WindowInsets.CONSUMED;
        this.ACTION_BAR_ANIMATE_DELAY = 600;
        this.mTopAnimatorListener = new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.ActionBarOverlayLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
                com.android.internal.widget.ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(android.animation.Animator animator) {
                com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
                com.android.internal.widget.ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }
        };
        this.mBottomAnimatorListener = new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.ActionBarOverlayLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
                com.android.internal.widget.ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(android.animation.Animator animator) {
                com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
                com.android.internal.widget.ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }
        };
        this.mRemoveActionBarHideOffset = new java.lang.Runnable() { // from class: com.android.internal.widget.ActionBarOverlayLayout.3
            @Override // java.lang.Runnable
            public void run() {
                com.android.internal.widget.ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
                com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarTop.animate().translationY(0.0f).setListener(com.android.internal.widget.ActionBarOverlayLayout.this.mTopAnimatorListener);
                if (com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom != null && com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom.getVisibility() != 8) {
                    com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom.animate().translationY(0.0f).setListener(com.android.internal.widget.ActionBarOverlayLayout.this.mBottomAnimatorListener);
                }
            }
        };
        this.mAddActionBarHideOffset = new java.lang.Runnable() { // from class: com.android.internal.widget.ActionBarOverlayLayout.4
            @Override // java.lang.Runnable
            public void run() {
                com.android.internal.widget.ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
                com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarTop.animate().translationY(-com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarTop.getHeight()).setListener(com.android.internal.widget.ActionBarOverlayLayout.this.mTopAnimatorListener);
                if (com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom != null && com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom.getVisibility() != 8) {
                    com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom.animate().translationY(com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom.getHeight()).setListener(com.android.internal.widget.ActionBarOverlayLayout.this.mBottomAnimatorListener);
                }
            }
        };
        init(context);
    }

    public ActionBarOverlayLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWindowVisibility = 0;
        this.mBaseContentInsets = new android.graphics.Rect();
        this.mLastBaseContentInsets = new android.graphics.Rect();
        this.mContentInsets = new android.graphics.Rect();
        this.mBaseInnerInsets = android.view.WindowInsets.CONSUMED;
        this.mLastBaseInnerInsets = android.view.WindowInsets.CONSUMED;
        this.mInnerInsets = android.view.WindowInsets.CONSUMED;
        this.mLastInnerInsets = android.view.WindowInsets.CONSUMED;
        this.ACTION_BAR_ANIMATE_DELAY = 600;
        this.mTopAnimatorListener = new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.ActionBarOverlayLayout.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
                com.android.internal.widget.ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(android.animation.Animator animator) {
                com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = null;
                com.android.internal.widget.ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }
        };
        this.mBottomAnimatorListener = new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.ActionBarOverlayLayout.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
                com.android.internal.widget.ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(android.animation.Animator animator) {
                com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = null;
                com.android.internal.widget.ActionBarOverlayLayout.this.mAnimatingForFling = false;
            }
        };
        this.mRemoveActionBarHideOffset = new java.lang.Runnable() { // from class: com.android.internal.widget.ActionBarOverlayLayout.3
            @Override // java.lang.Runnable
            public void run() {
                com.android.internal.widget.ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
                com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarTop.animate().translationY(0.0f).setListener(com.android.internal.widget.ActionBarOverlayLayout.this.mTopAnimatorListener);
                if (com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom != null && com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom.getVisibility() != 8) {
                    com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom.animate().translationY(0.0f).setListener(com.android.internal.widget.ActionBarOverlayLayout.this.mBottomAnimatorListener);
                }
            }
        };
        this.mAddActionBarHideOffset = new java.lang.Runnable() { // from class: com.android.internal.widget.ActionBarOverlayLayout.4
            @Override // java.lang.Runnable
            public void run() {
                com.android.internal.widget.ActionBarOverlayLayout.this.haltActionBarHideOffsetAnimations();
                com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarTopAnimator = com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarTop.animate().translationY(-com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarTop.getHeight()).setListener(com.android.internal.widget.ActionBarOverlayLayout.this.mTopAnimatorListener);
                if (com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom != null && com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom.getVisibility() != 8) {
                    com.android.internal.widget.ActionBarOverlayLayout.this.mCurrentActionBarBottomAnimator = com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom.animate().translationY(com.android.internal.widget.ActionBarOverlayLayout.this.mActionBarBottom.getHeight()).setListener(com.android.internal.widget.ActionBarOverlayLayout.this.mBottomAnimatorListener);
                }
            }
        };
        init(context);
    }

    private void init(android.content.Context context) {
        boolean z;
        android.content.res.TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(ATTRS);
        boolean z2 = false;
        this.mActionBarHeight = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.mWindowContentOverlay = obtainStyledAttributes.getDrawable(1);
        if (this.mWindowContentOverlay != null) {
            z = false;
        } else {
            z = true;
        }
        setWillNotDraw(z);
        obtainStyledAttributes.recycle();
        if (context.getApplicationInfo().targetSdkVersion < 19) {
            z2 = true;
        }
        this.mIgnoreWindowContentOverlay = z2;
        this.mFlingEstimator = new android.widget.OverScroller(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        haltActionBarHideOffsetAnimations();
    }

    public void setActionBarVisibilityCallback(com.android.internal.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback actionBarVisibilityCallback) {
        this.mActionBarVisibilityCallback = actionBarVisibilityCallback;
        if (getWindowToken() != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged(this.mWindowVisibility);
            if (this.mLastSystemUiVisibility != 0) {
                onWindowSystemUiVisibilityChanged(this.mLastSystemUiVisibility);
                requestApplyInsets();
            }
        }
    }

    public void setOverlayMode(boolean z) {
        this.mOverlayMode = z;
        this.mIgnoreWindowContentOverlay = z && getContext().getApplicationInfo().targetSdkVersion < 19;
    }

    public boolean isInOverlayMode() {
        return this.mOverlayMode;
    }

    public void setHasNonEmbeddedTabs(boolean z) {
        this.mHasNonEmbeddedTabs = z;
    }

    public void setShowingForActionMode(boolean z) {
        if (z) {
            if ((getWindowSystemUiVisibility() & 1280) == 1280) {
                setDisabledSystemUiVisibility(4);
                return;
            }
            return;
        }
        setDisabledSystemUiVisibility(0);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        init(getContext());
        requestApplyInsets();
    }

    @Override // android.view.View
    public void onWindowSystemUiVisibilityChanged(int i) {
        super.onWindowSystemUiVisibilityChanged(i);
        pullChildren();
        int i2 = this.mLastSystemUiVisibility ^ i;
        this.mLastSystemUiVisibility = i;
        boolean z = (i & 4) == 0;
        boolean z2 = (i & 256) != 0;
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.enableContentAnimations(!z2);
            if (z || !z2) {
                this.mActionBarVisibilityCallback.showForSystem();
            } else {
                this.mActionBarVisibilityCallback.hideForSystem();
            }
        }
        if ((i2 & 256) != 0 && this.mActionBarVisibilityCallback != null) {
            requestApplyInsets();
        }
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.mWindowVisibility = i;
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onWindowVisibilityChanged(i);
        }
    }

    private boolean applyInsets(android.view.View view, android.graphics.Rect rect, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5;
        com.android.internal.widget.ActionBarOverlayLayout.LayoutParams layoutParams = (com.android.internal.widget.ActionBarOverlayLayout.LayoutParams) view.getLayoutParams();
        if (z && layoutParams.leftMargin != rect.left) {
            layoutParams.leftMargin = rect.left;
            z5 = true;
        } else {
            z5 = false;
        }
        if (z2 && layoutParams.topMargin != rect.top) {
            layoutParams.topMargin = rect.top;
            z5 = true;
        }
        if (z4 && layoutParams.rightMargin != rect.right) {
            layoutParams.rightMargin = rect.right;
            z5 = true;
        }
        if (z3 && layoutParams.bottomMargin != rect.bottom) {
            layoutParams.bottomMargin = rect.bottom;
            return true;
        }
        return z5;
    }

    @Override // android.view.View
    public android.view.WindowInsets onApplyWindowInsets(android.view.WindowInsets windowInsets) {
        pullChildren();
        getWindowSystemUiVisibility();
        android.graphics.Rect systemWindowInsetsAsRect = windowInsets.getSystemWindowInsetsAsRect();
        boolean applyInsets = applyInsets(this.mActionBarTop, systemWindowInsetsAsRect, true, true, false, true);
        if (this.mActionBarBottom != null) {
            applyInsets |= applyInsets(this.mActionBarBottom, systemWindowInsetsAsRect, true, false, true, true);
        }
        computeSystemWindowInsets(windowInsets, this.mBaseContentInsets);
        this.mBaseInnerInsets = windowInsets.inset(this.mBaseContentInsets);
        boolean z = true;
        if (!this.mLastBaseInnerInsets.equals(this.mBaseInnerInsets)) {
            this.mLastBaseInnerInsets = this.mBaseInnerInsets;
            applyInsets = true;
        }
        if (this.mLastBaseContentInsets.equals(this.mBaseContentInsets)) {
            z = applyInsets;
        } else {
            this.mLastBaseContentInsets.set(this.mBaseContentInsets);
        }
        if (z) {
            requestLayout();
        }
        return android.view.WindowInsets.CONSUMED;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public com.android.internal.widget.ActionBarOverlayLayout.LayoutParams generateDefaultLayoutParams() {
        return new com.android.internal.widget.ActionBarOverlayLayout.LayoutParams(-1, -1);
    }

    @Override // android.view.ViewGroup
    public com.android.internal.widget.ActionBarOverlayLayout.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new com.android.internal.widget.ActionBarOverlayLayout.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new com.android.internal.widget.ActionBarOverlayLayout.LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof com.android.internal.widget.ActionBarOverlayLayout.LayoutParams;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int measuredHeight;
        int i3;
        pullChildren();
        measureChildWithMargins(this.mActionBarTop, i, 0, i2, 0);
        com.android.internal.widget.ActionBarOverlayLayout.LayoutParams layoutParams = (com.android.internal.widget.ActionBarOverlayLayout.LayoutParams) this.mActionBarTop.getLayoutParams();
        int max = java.lang.Math.max(0, this.mActionBarTop.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
        int max2 = java.lang.Math.max(0, this.mActionBarTop.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
        int combineMeasuredStates = combineMeasuredStates(0, this.mActionBarTop.getMeasuredState());
        if (this.mActionBarBottom != null) {
            measureChildWithMargins(this.mActionBarBottom, i, 0, i2, 0);
            com.android.internal.widget.ActionBarOverlayLayout.LayoutParams layoutParams2 = (com.android.internal.widget.ActionBarOverlayLayout.LayoutParams) this.mActionBarBottom.getLayoutParams();
            max = java.lang.Math.max(max, this.mActionBarBottom.getMeasuredWidth() + layoutParams2.leftMargin + layoutParams2.rightMargin);
            max2 = java.lang.Math.max(max2, this.mActionBarBottom.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin);
            combineMeasuredStates = combineMeasuredStates(combineMeasuredStates, this.mActionBarBottom.getMeasuredState());
        }
        boolean z = (getWindowSystemUiVisibility() & 256) != 0;
        if (z) {
            measuredHeight = this.mActionBarHeight;
            if (this.mHasNonEmbeddedTabs && this.mActionBarTop.getTabContainer() != null) {
                measuredHeight += this.mActionBarHeight;
            }
        } else {
            measuredHeight = this.mActionBarTop.getVisibility() != 8 ? this.mActionBarTop.getMeasuredHeight() : 0;
        }
        if (this.mDecorToolbar.isSplit() && this.mActionBarBottom != null) {
            if (z) {
                i3 = this.mActionBarHeight;
            } else {
                i3 = this.mActionBarBottom.getMeasuredHeight();
            }
        } else {
            i3 = 0;
        }
        this.mContentInsets.set(this.mBaseContentInsets);
        this.mInnerInsets = this.mBaseInnerInsets;
        if (!this.mOverlayMode && !z) {
            this.mContentInsets.top += measuredHeight;
            this.mContentInsets.bottom += i3;
            this.mInnerInsets = this.mInnerInsets.inset(0, measuredHeight, 0, i3);
        } else {
            this.mInnerInsets = this.mInnerInsets.replaceSystemWindowInsets(this.mInnerInsets.getSystemWindowInsetLeft(), this.mInnerInsets.getSystemWindowInsetTop() + measuredHeight, this.mInnerInsets.getSystemWindowInsetRight(), this.mInnerInsets.getSystemWindowInsetBottom() + i3);
        }
        applyInsets(this.mContent, this.mContentInsets, true, true, true, true);
        if (!this.mLastInnerInsets.equals(this.mInnerInsets)) {
            this.mLastInnerInsets = this.mInnerInsets;
            this.mContent.dispatchApplyWindowInsets(this.mInnerInsets);
        }
        measureChildWithMargins(this.mContent, i, 0, i2, 0);
        com.android.internal.widget.ActionBarOverlayLayout.LayoutParams layoutParams3 = (com.android.internal.widget.ActionBarOverlayLayout.LayoutParams) this.mContent.getLayoutParams();
        int max3 = java.lang.Math.max(max, this.mContent.getMeasuredWidth() + layoutParams3.leftMargin + layoutParams3.rightMargin);
        int max4 = java.lang.Math.max(max2, this.mContent.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin);
        int combineMeasuredStates2 = combineMeasuredStates(combineMeasuredStates, this.mContent.getMeasuredState());
        setMeasuredDimension(resolveSizeAndState(java.lang.Math.max(max3 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, combineMeasuredStates2), resolveSizeAndState(java.lang.Math.max(max4 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i2, combineMeasuredStates2 << 16));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = (i4 - i2) - getPaddingBottom();
        for (int i6 = 0; i6 < childCount; i6++) {
            android.view.View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                com.android.internal.widget.ActionBarOverlayLayout.LayoutParams layoutParams = (com.android.internal.widget.ActionBarOverlayLayout.LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i7 = layoutParams.leftMargin + paddingLeft;
                if (childAt == this.mActionBarBottom) {
                    i5 = (paddingBottom - measuredHeight) - layoutParams.bottomMargin;
                } else {
                    i5 = paddingTop + layoutParams.topMargin;
                }
                childAt.layout(i7, i5, measuredWidth + i7, measuredHeight + i5);
            }
        }
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        super.draw(canvas);
        if (this.mWindowContentOverlay != null && !this.mIgnoreWindowContentOverlay) {
            int bottom = this.mActionBarTop.getVisibility() == 0 ? (int) (this.mActionBarTop.getBottom() + this.mActionBarTop.getTranslationY() + 0.5f) : 0;
            this.mWindowContentOverlay.setBounds(0, bottom, getWidth(), this.mWindowContentOverlay.getIntrinsicHeight() + bottom);
            this.mWindowContentOverlay.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(android.view.View view, android.view.View view2, int i) {
        if ((i & 2) == 0 || this.mActionBarTop.getVisibility() != 0) {
            return false;
        }
        return this.mHideOnContentScroll;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(android.view.View view, android.view.View view2, int i) {
        super.onNestedScrollAccepted(view, view2, i);
        this.mHideOnContentScrollReference = getActionBarHideOffset();
        haltActionBarHideOffsetAnimations();
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onContentScrollStarted();
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(android.view.View view, int i, int i2, int i3, int i4) {
        this.mHideOnContentScrollReference += i2;
        setActionBarHideOffset(this.mHideOnContentScrollReference);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onStopNestedScroll(android.view.View view) {
        super.onStopNestedScroll(view);
        if (this.mHideOnContentScroll && !this.mAnimatingForFling) {
            if (this.mHideOnContentScrollReference <= this.mActionBarTop.getHeight()) {
                postRemoveActionBarHideOffset();
            } else {
                postAddActionBarHideOffset();
            }
        }
        if (this.mActionBarVisibilityCallback != null) {
            this.mActionBarVisibilityCallback.onContentScrollStopped();
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedFling(android.view.View view, float f, float f2, boolean z) {
        if (!this.mHideOnContentScroll || !z) {
            return false;
        }
        if (shouldHideActionBarOnFling(f, f2)) {
            addActionBarHideOffset();
        } else {
            removeActionBarHideOffset();
        }
        this.mAnimatingForFling = true;
        return true;
    }

    void pullChildren() {
        if (this.mContent == null) {
            this.mContent = findViewById(16908290);
            this.mActionBarTop = (com.android.internal.widget.ActionBarContainer) findViewById(com.android.internal.R.id.action_bar_container);
            this.mDecorToolbar = getDecorToolbar(findViewById(com.android.internal.R.id.action_bar));
            this.mActionBarBottom = (com.android.internal.widget.ActionBarContainer) findViewById(com.android.internal.R.id.split_action_bar);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private com.android.internal.widget.DecorToolbar getDecorToolbar(android.view.View view) {
        if (view instanceof com.android.internal.widget.DecorToolbar) {
            return (com.android.internal.widget.DecorToolbar) view;
        }
        if (view instanceof android.widget.Toolbar) {
            return ((android.widget.Toolbar) view).getWrapper();
        }
        throw new java.lang.IllegalStateException("Can't make a decor toolbar out of " + view.getClass().getSimpleName());
    }

    public void setHideOnContentScrollEnabled(boolean z) {
        if (z != this.mHideOnContentScroll) {
            this.mHideOnContentScroll = z;
            if (!z) {
                stopNestedScroll();
                haltActionBarHideOffsetAnimations();
                setActionBarHideOffset(0);
            }
        }
    }

    public boolean isHideOnContentScrollEnabled() {
        return this.mHideOnContentScroll;
    }

    public int getActionBarHideOffset() {
        if (this.mActionBarTop != null) {
            return -((int) this.mActionBarTop.getTranslationY());
        }
        return 0;
    }

    public void setActionBarHideOffset(int i) {
        haltActionBarHideOffsetAnimations();
        int max = java.lang.Math.max(0, java.lang.Math.min(i, this.mActionBarTop.getHeight()));
        this.mActionBarTop.setTranslationY(-max);
        if (this.mActionBarBottom != null && this.mActionBarBottom.getVisibility() != 8) {
            this.mActionBarBottom.setTranslationY((int) (this.mActionBarBottom.getHeight() * (max / r0)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void haltActionBarHideOffsetAnimations() {
        removeCallbacks(this.mRemoveActionBarHideOffset);
        removeCallbacks(this.mAddActionBarHideOffset);
        if (this.mCurrentActionBarTopAnimator != null) {
            this.mCurrentActionBarTopAnimator.cancel();
        }
        if (this.mCurrentActionBarBottomAnimator != null) {
            this.mCurrentActionBarBottomAnimator.cancel();
        }
    }

    private void postRemoveActionBarHideOffset() {
        haltActionBarHideOffsetAnimations();
        postDelayed(this.mRemoveActionBarHideOffset, 600L);
    }

    private void postAddActionBarHideOffset() {
        haltActionBarHideOffsetAnimations();
        postDelayed(this.mAddActionBarHideOffset, 600L);
    }

    private void removeActionBarHideOffset() {
        haltActionBarHideOffsetAnimations();
        this.mRemoveActionBarHideOffset.run();
    }

    private void addActionBarHideOffset() {
        haltActionBarHideOffsetAnimations();
        this.mAddActionBarHideOffset.run();
    }

    private boolean shouldHideActionBarOnFling(float f, float f2) {
        this.mFlingEstimator.fling(0, 0, 0, (int) f2, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return this.mFlingEstimator.getFinalY() > this.mActionBarTop.getHeight();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setWindowCallback(android.view.Window.Callback callback) {
        pullChildren();
        this.mDecorToolbar.setWindowCallback(callback);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setWindowTitle(java.lang.CharSequence charSequence) {
        pullChildren();
        this.mDecorToolbar.setWindowTitle(charSequence);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public java.lang.CharSequence getTitle() {
        pullChildren();
        return this.mDecorToolbar.getTitle();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void initFeature(int i) {
        pullChildren();
        switch (i) {
            case 2:
                this.mDecorToolbar.initProgress();
                break;
            case 5:
                this.mDecorToolbar.initIndeterminateProgress();
                break;
            case 9:
                setOverlayMode(true);
                break;
        }
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setUiOptions(int i) {
        boolean z = (i & 1) != 0;
        boolean z2 = z ? getContext().getResources().getBoolean(com.android.internal.R.bool.split_action_bar_is_narrow) : false;
        if (z2) {
            pullChildren();
            if (this.mActionBarBottom != null && this.mDecorToolbar.canSplit()) {
                this.mDecorToolbar.setSplitView(this.mActionBarBottom);
                this.mDecorToolbar.setSplitToolbar(z2);
                this.mDecorToolbar.setSplitWhenNarrow(z);
                com.android.internal.widget.ActionBarContextView actionBarContextView = (com.android.internal.widget.ActionBarContextView) findViewById(com.android.internal.R.id.action_context_bar);
                actionBarContextView.setSplitView(this.mActionBarBottom);
                actionBarContextView.setSplitToolbar(z2);
                actionBarContextView.setSplitWhenNarrow(z);
                return;
            }
            if (z2) {
                android.util.Log.e(TAG, "Requested split action bar with incompatible window decor! Ignoring request.");
            }
        }
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean hasIcon() {
        pullChildren();
        return this.mDecorToolbar.hasIcon();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean hasLogo() {
        pullChildren();
        return this.mDecorToolbar.hasLogo();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setIcon(int i) {
        pullChildren();
        this.mDecorToolbar.setIcon(i);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setIcon(android.graphics.drawable.Drawable drawable) {
        pullChildren();
        this.mDecorToolbar.setIcon(drawable);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setLogo(int i) {
        pullChildren();
        this.mDecorToolbar.setLogo(i);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean canShowOverflowMenu() {
        pullChildren();
        return this.mDecorToolbar.canShowOverflowMenu();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean isOverflowMenuShowing() {
        pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowing();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean isOverflowMenuShowPending() {
        pullChildren();
        return this.mDecorToolbar.isOverflowMenuShowPending();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean showOverflowMenu() {
        pullChildren();
        return this.mDecorToolbar.showOverflowMenu();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public boolean hideOverflowMenu() {
        pullChildren();
        return this.mDecorToolbar.hideOverflowMenu();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setMenuPrepared() {
        pullChildren();
        this.mDecorToolbar.setMenuPrepared();
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void setMenu(android.view.Menu menu, com.android.internal.view.menu.MenuPresenter.Callback callback) {
        pullChildren();
        this.mDecorToolbar.setMenu(menu, callback);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void saveToolbarHierarchyState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        pullChildren();
        this.mDecorToolbar.saveHierarchyState(sparseArray);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void restoreToolbarHierarchyState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        pullChildren();
        this.mDecorToolbar.restoreHierarchyState(sparseArray);
    }

    @Override // com.android.internal.widget.DecorContentParent
    public void dismissPopups() {
        pullChildren();
        this.mDecorToolbar.dismissPopupMenus();
    }

    public static class LayoutParams extends android.view.ViewGroup.MarginLayoutParams {
        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }
}
