package com.android.internal.widget;

/* loaded from: classes5.dex */
public abstract class AbsActionBarView extends android.view.ViewGroup {
    private static final int FADE_DURATION = 200;
    private static final android.animation.TimeInterpolator sAlphaInterpolator = new android.view.animation.DecelerateInterpolator();
    protected android.widget.ActionMenuPresenter mActionMenuPresenter;
    protected int mContentHeight;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    protected android.widget.ActionMenuView mMenuView;
    protected final android.content.Context mPopupContext;
    protected boolean mSplitActionBar;
    protected android.view.ViewGroup mSplitView;
    protected boolean mSplitWhenNarrow;
    protected final com.android.internal.widget.AbsActionBarView.VisibilityAnimListener mVisAnimListener;
    protected android.animation.Animator mVisibilityAnim;

    public AbsActionBarView(android.content.Context context) {
        this(context, null);
    }

    public AbsActionBarView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbsActionBarView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AbsActionBarView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mVisAnimListener = new com.android.internal.widget.AbsActionBarView.VisibilityAnimListener();
        android.util.TypedValue typedValue = new android.util.TypedValue();
        if (context.getTheme().resolveAttribute(16843917, typedValue, true) && typedValue.resourceId != 0) {
            this.mPopupContext = new android.view.ContextThemeWrapper(context, typedValue.resourceId);
        } else {
            this.mPopupContext = context;
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        android.content.res.TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(null, com.android.internal.R.styleable.ActionBar, 16843470, 0);
        setContentHeight(obtainStyledAttributes.getLayoutDimension(4, 0));
        obtainStyledAttributes.recycle();
        if (this.mSplitWhenNarrow) {
            setSplitToolbar(getContext().getResources().getBoolean(com.android.internal.R.bool.split_action_bar_is_narrow));
        }
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.onConfigurationChanged(configuration);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean onTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !onTouchEvent) {
                this.mEatingTouch = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    @Override // android.view.View
    public boolean onHoverEvent(android.view.MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean onHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !onHoverEvent) {
                this.mEatingHover = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    public void setSplitToolbar(boolean z) {
        this.mSplitActionBar = z;
    }

    public void setSplitWhenNarrow(boolean z) {
        this.mSplitWhenNarrow = z;
    }

    public void setContentHeight(int i) {
        this.mContentHeight = i;
        requestLayout();
    }

    public int getContentHeight() {
        return this.mContentHeight;
    }

    public void setSplitView(android.view.ViewGroup viewGroup) {
        this.mSplitView = viewGroup;
    }

    public int getAnimatedVisibility() {
        if (this.mVisibilityAnim != null) {
            return this.mVisAnimListener.mFinalVisibility;
        }
        return getVisibility();
    }

    public android.animation.Animator setupAnimatorToVisibility(int i, long j) {
        if (this.mVisibilityAnim != null) {
            this.mVisibilityAnim.cancel();
        }
        if (i == 0) {
            if (getVisibility() != 0) {
                setAlpha(0.0f);
                if (this.mSplitView != null && this.mMenuView != null) {
                    this.mMenuView.setAlpha(0.0f);
                }
            }
            android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(this, (android.util.Property<com.android.internal.widget.AbsActionBarView, java.lang.Float>) android.view.View.ALPHA, 1.0f);
            ofFloat.setDuration(j);
            ofFloat.setInterpolator(sAlphaInterpolator);
            if (this.mSplitView != null && this.mMenuView != null) {
                android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
                android.animation.ObjectAnimator ofFloat2 = android.animation.ObjectAnimator.ofFloat(this.mMenuView, (android.util.Property<android.widget.ActionMenuView, java.lang.Float>) android.view.View.ALPHA, 1.0f);
                ofFloat2.setDuration(j);
                animatorSet.addListener(this.mVisAnimListener.withFinalVisibility(i));
                animatorSet.play(ofFloat).with(ofFloat2);
                return animatorSet;
            }
            ofFloat.addListener(this.mVisAnimListener.withFinalVisibility(i));
            return ofFloat;
        }
        android.animation.ObjectAnimator ofFloat3 = android.animation.ObjectAnimator.ofFloat(this, (android.util.Property<com.android.internal.widget.AbsActionBarView, java.lang.Float>) android.view.View.ALPHA, 0.0f);
        ofFloat3.setDuration(j);
        ofFloat3.setInterpolator(sAlphaInterpolator);
        if (this.mSplitView != null && this.mMenuView != null) {
            android.animation.AnimatorSet animatorSet2 = new android.animation.AnimatorSet();
            android.animation.ObjectAnimator ofFloat4 = android.animation.ObjectAnimator.ofFloat(this.mMenuView, (android.util.Property<android.widget.ActionMenuView, java.lang.Float>) android.view.View.ALPHA, 0.0f);
            ofFloat4.setDuration(j);
            animatorSet2.addListener(this.mVisAnimListener.withFinalVisibility(i));
            animatorSet2.play(ofFloat3).with(ofFloat4);
            return animatorSet2;
        }
        ofFloat3.addListener(this.mVisAnimListener.withFinalVisibility(i));
        return ofFloat3;
    }

    public void animateToVisibility(int i) {
        setupAnimatorToVisibility(i, 200L).start();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        if (i != getVisibility()) {
            if (this.mVisibilityAnim != null) {
                this.mVisibilityAnim.end();
            }
            super.setVisibility(i);
        }
    }

    public boolean showOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.showOverflowMenu();
        }
        return false;
    }

    public void postShowOverflowMenu() {
        post(new java.lang.Runnable() { // from class: com.android.internal.widget.AbsActionBarView.1
            @Override // java.lang.Runnable
            public void run() {
                com.android.internal.widget.AbsActionBarView.this.showOverflowMenu();
            }
        });
    }

    public boolean hideOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.hideOverflowMenu();
        }
        return false;
    }

    public boolean isOverflowMenuShowing() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowing();
        }
        return false;
    }

    public boolean isOverflowMenuShowPending() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowPending();
        }
        return false;
    }

    public boolean isOverflowReserved() {
        return this.mActionMenuPresenter != null && this.mActionMenuPresenter.isOverflowReserved();
    }

    public boolean canShowOverflowMenu() {
        return isOverflowReserved() && getVisibility() == 0;
    }

    public void dismissPopupMenus() {
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.dismissPopupMenus();
        }
    }

    protected int measureChildView(android.view.View view, int i, int i2, int i3) {
        view.measure(android.view.View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE), i2);
        return java.lang.Math.max(0, (i - view.getMeasuredWidth()) - i3);
    }

    protected static int next(int i, int i2, boolean z) {
        return z ? i - i2 : i + i2;
    }

    protected int positionChild(android.view.View view, int i, int i2, int i3, boolean z) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i4 = i2 + ((i3 - measuredHeight) / 2);
        if (z) {
            view.layout(i - measuredWidth, i4, i, measuredHeight + i4);
        } else {
            view.layout(i, i4, i + measuredWidth, measuredHeight + i4);
        }
        return z ? -measuredWidth : measuredWidth;
    }

    protected class VisibilityAnimListener implements android.animation.Animator.AnimatorListener {
        private boolean mCanceled = false;
        int mFinalVisibility;

        protected VisibilityAnimListener() {
        }

        public com.android.internal.widget.AbsActionBarView.VisibilityAnimListener withFinalVisibility(int i) {
            this.mFinalVisibility = i;
            return this;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(android.animation.Animator animator) {
            com.android.internal.widget.AbsActionBarView.this.setVisibility(0);
            com.android.internal.widget.AbsActionBarView.this.mVisibilityAnim = animator;
            this.mCanceled = false;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            if (this.mCanceled) {
                return;
            }
            com.android.internal.widget.AbsActionBarView.this.mVisibilityAnim = null;
            com.android.internal.widget.AbsActionBarView.this.setVisibility(this.mFinalVisibility);
            if (com.android.internal.widget.AbsActionBarView.this.mSplitView != null && com.android.internal.widget.AbsActionBarView.this.mMenuView != null) {
                com.android.internal.widget.AbsActionBarView.this.mMenuView.setVisibility(this.mFinalVisibility);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(android.animation.Animator animator) {
            this.mCanceled = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(android.animation.Animator animator) {
        }
    }
}
