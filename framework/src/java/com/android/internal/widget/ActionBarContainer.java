package com.android.internal.widget;

/* loaded from: classes5.dex */
public class ActionBarContainer extends android.widget.FrameLayout {
    private android.view.View mActionBarView;
    private android.view.View mActionContextView;
    private android.graphics.drawable.Drawable mBackground;
    private int mHeight;
    private boolean mIsSplit;
    private boolean mIsStacked;
    private boolean mIsTransitioning;
    private android.graphics.drawable.Drawable mSplitBackground;
    private android.graphics.drawable.Drawable mStackedBackground;
    private android.view.View mTabContainer;

    public ActionBarContainer(android.content.Context context) {
        this(context, null);
    }

    public ActionBarContainer(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        setBackground(new com.android.internal.widget.ActionBarContainer.ActionBarBackgroundDrawable());
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ActionBar);
        this.mBackground = obtainStyledAttributes.getDrawable(2);
        this.mStackedBackground = obtainStyledAttributes.getDrawable(18);
        this.mHeight = obtainStyledAttributes.getDimensionPixelSize(4, -1);
        boolean z = true;
        if (getId() == 16909576) {
            this.mIsSplit = true;
            this.mSplitBackground = obtainStyledAttributes.getDrawable(19);
        }
        obtainStyledAttributes.recycle();
        if (this.mIsSplit) {
            if (this.mSplitBackground != null) {
                z = false;
            }
        } else if (this.mBackground != null || this.mStackedBackground != null) {
            z = false;
        }
        setWillNotDraw(z);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mActionBarView = findViewById(com.android.internal.R.id.action_bar);
        this.mActionContextView = findViewById(com.android.internal.R.id.action_context_bar);
    }

    public void setPrimaryBackground(android.graphics.drawable.Drawable drawable) {
        if (this.mBackground != null) {
            this.mBackground.setCallback(null);
            unscheduleDrawable(this.mBackground);
        }
        this.mBackground = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.mActionBarView != null) {
                this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
            }
        }
        boolean z = true;
        if (this.mIsSplit) {
            if (this.mSplitBackground != null) {
                z = false;
            }
        } else if (this.mBackground != null || this.mStackedBackground != null) {
            z = false;
        }
        setWillNotDraw(z);
        invalidate();
    }

    public void setStackedBackground(android.graphics.drawable.Drawable drawable) {
        if (this.mStackedBackground != null) {
            this.mStackedBackground.setCallback(null);
            unscheduleDrawable(this.mStackedBackground);
        }
        this.mStackedBackground = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.mIsStacked && this.mStackedBackground != null) {
                this.mStackedBackground.setBounds(this.mTabContainer.getLeft(), this.mTabContainer.getTop(), this.mTabContainer.getRight(), this.mTabContainer.getBottom());
            }
        }
        boolean z = true;
        if (this.mIsSplit) {
            if (this.mSplitBackground != null) {
                z = false;
            }
        } else if (this.mBackground != null || this.mStackedBackground != null) {
            z = false;
        }
        setWillNotDraw(z);
        invalidate();
    }

    public void setSplitBackground(android.graphics.drawable.Drawable drawable) {
        if (this.mSplitBackground != null) {
            this.mSplitBackground.setCallback(null);
            unscheduleDrawable(this.mSplitBackground);
        }
        this.mSplitBackground = drawable;
        boolean z = false;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.mIsSplit && this.mSplitBackground != null) {
                this.mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
        }
        if (this.mIsSplit) {
            if (this.mSplitBackground == null) {
                z = true;
            }
        } else if (this.mBackground == null && this.mStackedBackground == null) {
            z = true;
        }
        setWillNotDraw(z);
        invalidate();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        if (this.mBackground != null) {
            this.mBackground.setVisible(z, false);
        }
        if (this.mStackedBackground != null) {
            this.mStackedBackground.setVisible(z, false);
        }
        if (this.mSplitBackground != null) {
            this.mSplitBackground.setVisible(z, false);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
        return (drawable == this.mBackground && !this.mIsSplit) || (drawable == this.mStackedBackground && this.mIsStacked) || ((drawable == this.mSplitBackground && this.mIsSplit) || super.verifyDrawable(drawable));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        android.graphics.drawable.Drawable drawable = this.mBackground;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        android.graphics.drawable.Drawable drawable2 = this.mStackedBackground;
        if (drawable2 != null && drawable2.isStateful()) {
            z |= drawable2.setState(drawableState);
        }
        android.graphics.drawable.Drawable drawable3 = this.mSplitBackground;
        if (drawable3 != null && drawable3.isStateful()) {
            z |= drawable3.setState(drawableState);
        }
        if (z) {
            invalidate();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mBackground != null) {
            this.mBackground.jumpToCurrentState();
        }
        if (this.mStackedBackground != null) {
            this.mStackedBackground.jumpToCurrentState();
        }
        if (this.mSplitBackground != null) {
            this.mSplitBackground.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public void onResolveDrawables(int i) {
        super.onResolveDrawables(i);
        if (this.mBackground != null) {
            this.mBackground.setLayoutDirection(i);
        }
        if (this.mStackedBackground != null) {
            this.mStackedBackground.setLayoutDirection(i);
        }
        if (this.mSplitBackground != null) {
            this.mSplitBackground.setLayoutDirection(i);
        }
    }

    public void setTransitioning(boolean z) {
        this.mIsTransitioning = z;
        setDescendantFocusability(z ? 393216 : 262144);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        return this.mIsTransitioning || super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    @Override // android.view.View
    public boolean onHoverEvent(android.view.MotionEvent motionEvent) {
        super.onHoverEvent(motionEvent);
        return true;
    }

    public void setTabContainer(com.android.internal.widget.ScrollingTabContainerView scrollingTabContainerView) {
        if (this.mTabContainer != null) {
            removeView(this.mTabContainer);
        }
        this.mTabContainer = scrollingTabContainerView;
        if (scrollingTabContainerView != null) {
            addView(scrollingTabContainerView);
            android.view.ViewGroup.LayoutParams layoutParams = scrollingTabContainerView.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            scrollingTabContainerView.setAllowCollapse(false);
        }
    }

    public android.view.View getTabContainer() {
        return this.mTabContainer;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public android.view.ActionMode startActionModeForChild(android.view.View view, android.view.ActionMode.Callback callback, int i) {
        if (i != 0) {
            return super.startActionModeForChild(view, callback, i);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCollapsed(android.view.View view) {
        return view == null || view.getVisibility() == 8 || view.getMeasuredHeight() == 0;
    }

    private int getMeasuredHeightWithMargins(android.view.View view) {
        android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) view.getLayoutParams();
        return view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int measuredHeightWithMargins;
        if (this.mActionBarView == null && android.view.View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE && this.mHeight >= 0) {
            i2 = android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.min(this.mHeight, android.view.View.MeasureSpec.getSize(i2)), Integer.MIN_VALUE);
        }
        super.onMeasure(i, i2);
        if (this.mActionBarView != null && this.mTabContainer != null && this.mTabContainer.getVisibility() != 8) {
            int childCount = getChildCount();
            int i3 = 0;
            for (int i4 = 0; i4 < childCount; i4++) {
                android.view.View childAt = getChildAt(i4);
                if (childAt != this.mTabContainer) {
                    if (isCollapsed(childAt)) {
                        measuredHeightWithMargins = 0;
                    } else {
                        measuredHeightWithMargins = getMeasuredHeightWithMargins(childAt);
                    }
                    i3 = java.lang.Math.max(i3, measuredHeightWithMargins);
                }
            }
            setMeasuredDimension(getMeasuredWidth(), java.lang.Math.min(i3 + getMeasuredHeightWithMargins(this.mTabContainer), android.view.View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE ? android.view.View.MeasureSpec.getSize(i2) : Integer.MAX_VALUE));
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        android.view.View view = this.mTabContainer;
        boolean z2 = true;
        boolean z3 = false;
        boolean z4 = (view == null || view.getVisibility() == 8) ? false : true;
        if (view != null && view.getVisibility() != 8) {
            int measuredHeight = getMeasuredHeight();
            android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) view.getLayoutParams();
            view.layout(i, (measuredHeight - view.getMeasuredHeight()) - layoutParams.bottomMargin, i3, measuredHeight - layoutParams.bottomMargin);
        }
        if (this.mIsSplit) {
            if (this.mSplitBackground == null) {
                z2 = false;
            } else {
                this.mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
        } else {
            if (this.mBackground != null) {
                if (this.mActionBarView.getVisibility() == 0) {
                    this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
                } else if (this.mActionContextView != null && this.mActionContextView.getVisibility() == 0) {
                    this.mBackground.setBounds(this.mActionContextView.getLeft(), this.mActionContextView.getTop(), this.mActionContextView.getRight(), this.mActionContextView.getBottom());
                } else {
                    this.mBackground.setBounds(0, 0, 0, 0);
                }
                z3 = true;
            }
            this.mIsStacked = z4;
            if (z4 && this.mStackedBackground != null) {
                this.mStackedBackground.setBounds(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            } else {
                z2 = z3;
            }
        }
        if (z2) {
            invalidate();
        }
    }

    private class ActionBarBackgroundDrawable extends android.graphics.drawable.Drawable {
        private ActionBarBackgroundDrawable() {
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(android.graphics.Canvas canvas) {
            if (com.android.internal.widget.ActionBarContainer.this.mIsSplit) {
                if (com.android.internal.widget.ActionBarContainer.this.mSplitBackground != null) {
                    com.android.internal.widget.ActionBarContainer.this.mSplitBackground.draw(canvas);
                }
            } else {
                if (com.android.internal.widget.ActionBarContainer.this.mBackground != null) {
                    com.android.internal.widget.ActionBarContainer.this.mBackground.draw(canvas);
                }
                if (com.android.internal.widget.ActionBarContainer.this.mStackedBackground != null && com.android.internal.widget.ActionBarContainer.this.mIsStacked) {
                    com.android.internal.widget.ActionBarContainer.this.mStackedBackground.draw(canvas);
                }
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void getOutline(android.graphics.Outline outline) {
            if (com.android.internal.widget.ActionBarContainer.this.mIsSplit) {
                if (com.android.internal.widget.ActionBarContainer.this.mSplitBackground != null) {
                    com.android.internal.widget.ActionBarContainer.this.mSplitBackground.getOutline(outline);
                }
            } else if (com.android.internal.widget.ActionBarContainer.this.mBackground != null) {
                com.android.internal.widget.ActionBarContainer.this.mBackground.getOutline(outline);
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            if (com.android.internal.widget.ActionBarContainer.this.mIsSplit) {
                if (com.android.internal.widget.ActionBarContainer.this.mSplitBackground != null && com.android.internal.widget.ActionBarContainer.this.mSplitBackground.getOpacity() == -1) {
                    return -1;
                }
            } else if ((!com.android.internal.widget.ActionBarContainer.this.mIsStacked || (com.android.internal.widget.ActionBarContainer.this.mStackedBackground != null && com.android.internal.widget.ActionBarContainer.this.mStackedBackground.getOpacity() == -1)) && !com.android.internal.widget.ActionBarContainer.isCollapsed(com.android.internal.widget.ActionBarContainer.this.mActionBarView) && com.android.internal.widget.ActionBarContainer.this.mBackground != null && com.android.internal.widget.ActionBarContainer.this.mBackground.getOpacity() == -1) {
                return -1;
            }
            return 0;
        }
    }
}
