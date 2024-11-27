package android.widget;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class TabWidget extends android.widget.LinearLayout implements android.view.View.OnFocusChangeListener {
    private final android.graphics.Rect mBounds;
    private boolean mDrawBottomStrips;
    private int[] mImposedTabWidths;
    private int mImposedTabsHeight;
    private android.graphics.drawable.Drawable mLeftStrip;
    private android.graphics.drawable.Drawable mRightStrip;
    private int mSelectedTab;
    private android.widget.TabWidget.OnTabSelectionChanged mSelectionChangedListener;
    private boolean mStripMoved;

    interface OnTabSelectionChanged {
        void onTabSelectionChanged(int i, boolean z);
    }

    public TabWidget(android.content.Context context) {
        this(context, null);
    }

    public TabWidget(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842883);
    }

    public TabWidget(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TabWidget(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mBounds = new android.graphics.Rect();
        this.mSelectedTab = -1;
        this.mDrawBottomStrips = true;
        this.mImposedTabsHeight = -1;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TabWidget, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.TabWidget, attributeSet, obtainStyledAttributes, i, i2);
        this.mDrawBottomStrips = obtainStyledAttributes.getBoolean(3, this.mDrawBottomStrips);
        boolean z = context.getApplicationInfo().targetSdkVersion <= 4;
        if (obtainStyledAttributes.hasValueOrEmpty(1)) {
            this.mLeftStrip = obtainStyledAttributes.getDrawable(1);
        } else if (z) {
            this.mLeftStrip = context.getDrawable(com.android.internal.R.drawable.tab_bottom_left_v4);
        } else {
            this.mLeftStrip = context.getDrawable(com.android.internal.R.drawable.tab_bottom_left);
        }
        if (obtainStyledAttributes.hasValueOrEmpty(2)) {
            this.mRightStrip = obtainStyledAttributes.getDrawable(2);
        } else if (z) {
            this.mRightStrip = context.getDrawable(com.android.internal.R.drawable.tab_bottom_right_v4);
        } else {
            this.mRightStrip = context.getDrawable(com.android.internal.R.drawable.tab_bottom_right);
        }
        obtainStyledAttributes.recycle();
        setChildrenDrawingOrderEnabled(true);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        this.mStripMoved = true;
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.view.ViewGroup
    protected int getChildDrawingOrder(int i, int i2) {
        if (this.mSelectedTab == -1) {
            return i2;
        }
        if (i2 == i - 1) {
            return this.mSelectedTab;
        }
        if (i2 >= this.mSelectedTab) {
            return i2 + 1;
        }
        return i2;
    }

    @Override // android.widget.LinearLayout
    void measureChildBeforeLayout(android.view.View view, int i, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        if (!isMeasureWithLargestChildEnabled() && this.mImposedTabsHeight >= 0) {
            i6 = android.view.View.MeasureSpec.makeMeasureSpec(this.mImposedTabWidths[i] + i3, 1073741824);
            i7 = android.view.View.MeasureSpec.makeMeasureSpec(this.mImposedTabsHeight, 1073741824);
        } else {
            i6 = i2;
            i7 = i4;
        }
        super.measureChildBeforeLayout(view, i, i6, i3, i7, i5);
    }

    @Override // android.widget.LinearLayout
    void measureHorizontal(int i, int i2) {
        if (android.view.View.MeasureSpec.getMode(i) == 0) {
            super.measureHorizontal(i, i2);
            return;
        }
        int size = android.view.View.MeasureSpec.getSize(i);
        int makeSafeMeasureSpec = android.view.View.MeasureSpec.makeSafeMeasureSpec(size, 0);
        this.mImposedTabsHeight = -1;
        super.measureHorizontal(makeSafeMeasureSpec, i2);
        int measuredWidth = getMeasuredWidth() - size;
        if (measuredWidth > 0) {
            int childCount = getChildCount();
            int i3 = 0;
            for (int i4 = 0; i4 < childCount; i4++) {
                if (getChildAt(i4).getVisibility() != 8) {
                    i3++;
                }
            }
            if (i3 > 0) {
                if (this.mImposedTabWidths == null || this.mImposedTabWidths.length != childCount) {
                    this.mImposedTabWidths = new int[childCount];
                }
                for (int i5 = 0; i5 < childCount; i5++) {
                    android.view.View childAt = getChildAt(i5);
                    if (childAt.getVisibility() != 8) {
                        int measuredWidth2 = childAt.getMeasuredWidth();
                        int max = java.lang.Math.max(0, measuredWidth2 - (measuredWidth / i3));
                        this.mImposedTabWidths[i5] = max;
                        measuredWidth -= measuredWidth2 - max;
                        i3--;
                        this.mImposedTabsHeight = java.lang.Math.max(this.mImposedTabsHeight, childAt.getMeasuredHeight());
                    }
                }
            }
        }
        super.measureHorizontal(i, i2);
    }

    public android.view.View getChildTabViewAt(int i) {
        return getChildAt(i);
    }

    public int getTabCount() {
        return getChildCount();
    }

    @Override // android.widget.LinearLayout
    public void setDividerDrawable(android.graphics.drawable.Drawable drawable) {
        super.setDividerDrawable(drawable);
    }

    public void setDividerDrawable(int i) {
        setDividerDrawable(this.mContext.getDrawable(i));
    }

    public void setLeftStripDrawable(android.graphics.drawable.Drawable drawable) {
        this.mLeftStrip = drawable;
        requestLayout();
        invalidate();
    }

    public void setLeftStripDrawable(int i) {
        setLeftStripDrawable(this.mContext.getDrawable(i));
    }

    public android.graphics.drawable.Drawable getLeftStripDrawable() {
        return this.mLeftStrip;
    }

    public void setRightStripDrawable(android.graphics.drawable.Drawable drawable) {
        this.mRightStrip = drawable;
        requestLayout();
        invalidate();
    }

    public void setRightStripDrawable(int i) {
        setRightStripDrawable(this.mContext.getDrawable(i));
    }

    public android.graphics.drawable.Drawable getRightStripDrawable() {
        return this.mRightStrip;
    }

    public void setStripEnabled(boolean z) {
        this.mDrawBottomStrips = z;
        invalidate();
    }

    public boolean isStripEnabled() {
        return this.mDrawBottomStrips;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void childDrawableStateChanged(android.view.View view) {
        if (getTabCount() > 0 && view == getChildTabViewAt(this.mSelectedTab)) {
            invalidate();
        }
        super.childDrawableStateChanged(view);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(android.graphics.Canvas canvas) {
        super.dispatchDraw(canvas);
        if (getTabCount() == 0 || !this.mDrawBottomStrips) {
            return;
        }
        android.view.View childTabViewAt = getChildTabViewAt(this.mSelectedTab);
        android.graphics.drawable.Drawable drawable = this.mLeftStrip;
        android.graphics.drawable.Drawable drawable2 = this.mRightStrip;
        if (drawable != null) {
            drawable.setState(childTabViewAt.getDrawableState());
        }
        if (drawable2 != null) {
            drawable2.setState(childTabViewAt.getDrawableState());
        }
        if (this.mStripMoved) {
            android.graphics.Rect rect = this.mBounds;
            rect.left = childTabViewAt.getLeft();
            rect.right = childTabViewAt.getRight();
            int height = getHeight();
            if (drawable != null) {
                drawable.setBounds(java.lang.Math.min(0, rect.left - drawable.getIntrinsicWidth()), height - drawable.getIntrinsicHeight(), rect.left, height);
            }
            if (drawable2 != null) {
                drawable2.setBounds(rect.right, height - drawable2.getIntrinsicHeight(), java.lang.Math.max(getWidth(), rect.right + drawable2.getIntrinsicWidth()), height);
            }
            this.mStripMoved = false;
        }
        if (drawable != null) {
            drawable.draw(canvas);
        }
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
    }

    public void setCurrentTab(int i) {
        if (i < 0 || i >= getTabCount() || i == this.mSelectedTab) {
            return;
        }
        if (this.mSelectedTab != -1) {
            getChildTabViewAt(this.mSelectedTab).setSelected(false);
        }
        this.mSelectedTab = i;
        getChildTabViewAt(this.mSelectedTab).setSelected(true);
        this.mStripMoved = true;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.TabWidget.class.getName();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setItemCount(getTabCount());
        accessibilityEvent.setCurrentItemIndex(this.mSelectedTab);
    }

    public void focusCurrentTab(int i) {
        int i2 = this.mSelectedTab;
        setCurrentTab(i);
        if (i2 != i) {
            getChildTabViewAt(i).requestFocus();
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        int tabCount = getTabCount();
        for (int i = 0; i < tabCount; i++) {
            getChildTabViewAt(i).setEnabled(z);
        }
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view) {
        if (view.getLayoutParams() == null) {
            android.widget.LinearLayout.LayoutParams layoutParams = new android.widget.LinearLayout.LayoutParams(0, -1, 1.0f);
            layoutParams.setMargins(0, 0, 0, 0);
            view.setLayoutParams(layoutParams);
        }
        view.setFocusable(true);
        view.setClickable(true);
        if (!android.view.flags.Flags.enableArrowIconOnHoverWhenClickable() && view.getPointerIcon() == null) {
            view.setPointerIcon(android.view.PointerIcon.getSystemIcon(getContext(), 1002));
        }
        super.addView(view);
        view.setOnClickListener(new android.widget.TabWidget.TabClickListener(getTabCount() - 1));
    }

    @Override // android.view.ViewGroup
    public void removeAllViews() {
        super.removeAllViews();
        this.mSelectedTab = -1;
    }

    @Override // android.view.ViewGroup, android.view.View
    public android.view.PointerIcon onResolvePointerIcon(android.view.MotionEvent motionEvent, int i) {
        if (!isEnabled()) {
            return null;
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    void setTabSelectionListener(android.widget.TabWidget.OnTabSelectionChanged onTabSelectionChanged) {
        this.mSelectionChangedListener = onTabSelectionChanged;
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(android.view.View view, boolean z) {
    }

    private class TabClickListener implements android.view.View.OnClickListener {
        private final int mTabIndex;

        private TabClickListener(int i) {
            this.mTabIndex = i;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            android.widget.TabWidget.this.mSelectionChangedListener.onTabSelectionChanged(this.mTabIndex, true);
        }
    }
}
