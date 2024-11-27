package com.android.internal.view.menu;

/* loaded from: classes5.dex */
public final class IconMenuView extends android.view.ViewGroup implements com.android.internal.view.menu.MenuBuilder.ItemInvoker, com.android.internal.view.menu.MenuView, java.lang.Runnable {
    private static final int ITEM_CAPTION_CYCLE_DELAY = 1000;
    private int mAnimations;
    private boolean mHasStaleChildren;
    private android.graphics.drawable.Drawable mHorizontalDivider;
    private int mHorizontalDividerHeight;
    private java.util.ArrayList<android.graphics.Rect> mHorizontalDividerRects;
    private android.graphics.drawable.Drawable mItemBackground;
    private boolean mLastChildrenCaptionMode;
    private int[] mLayout;
    private int mLayoutNumRows;
    private int mMaxItems;
    private int mMaxItemsPerRow;
    private int mMaxRows;
    private com.android.internal.view.menu.MenuBuilder mMenu;
    private boolean mMenuBeingLongpressed;
    private android.graphics.drawable.Drawable mMoreIcon;
    private int mNumActualItemsShown;
    private int mRowHeight;
    private android.graphics.drawable.Drawable mVerticalDivider;
    private java.util.ArrayList<android.graphics.Rect> mVerticalDividerRects;
    private int mVerticalDividerWidth;

    public IconMenuView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMenuBeingLongpressed = false;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.IconMenuView, 0, 0);
        this.mRowHeight = obtainStyledAttributes.getDimensionPixelSize(0, 64);
        this.mMaxRows = obtainStyledAttributes.getInt(1, 2);
        this.mMaxItems = obtainStyledAttributes.getInt(4, 6);
        this.mMaxItemsPerRow = obtainStyledAttributes.getInt(2, 3);
        this.mMoreIcon = obtainStyledAttributes.getDrawable(3);
        obtainStyledAttributes.recycle();
        android.content.res.TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.MenuView, 0, 0);
        this.mItemBackground = obtainStyledAttributes2.getDrawable(5);
        this.mHorizontalDivider = obtainStyledAttributes2.getDrawable(2);
        this.mHorizontalDividerRects = new java.util.ArrayList<>();
        this.mVerticalDivider = obtainStyledAttributes2.getDrawable(3);
        this.mVerticalDividerRects = new java.util.ArrayList<>();
        this.mAnimations = obtainStyledAttributes2.getResourceId(0, 0);
        obtainStyledAttributes2.recycle();
        if (this.mHorizontalDivider != null) {
            this.mHorizontalDividerHeight = this.mHorizontalDivider.getIntrinsicHeight();
            if (this.mHorizontalDividerHeight == -1) {
                this.mHorizontalDividerHeight = 1;
            }
        }
        if (this.mVerticalDivider != null) {
            this.mVerticalDividerWidth = this.mVerticalDivider.getIntrinsicWidth();
            if (this.mVerticalDividerWidth == -1) {
                this.mVerticalDividerWidth = 1;
            }
        }
        this.mLayout = new int[this.mMaxRows];
        setWillNotDraw(false);
        setFocusableInTouchMode(true);
        setDescendantFocusability(262144);
    }

    int getMaxItems() {
        return this.mMaxItems;
    }

    private void layoutItems(int i) {
        int childCount = getChildCount();
        if (childCount == 0) {
            this.mLayoutNumRows = 0;
            return;
        }
        for (int min = java.lang.Math.min((int) java.lang.Math.ceil(childCount / this.mMaxItemsPerRow), this.mMaxRows); min <= this.mMaxRows; min++) {
            layoutItemsUsingGravity(min, childCount);
            if (min >= childCount || doItemsFit()) {
                return;
            }
        }
    }

    private void layoutItemsUsingGravity(int i, int i2) {
        int i3 = i2 / i;
        int i4 = i - (i2 % i);
        int[] iArr = this.mLayout;
        for (int i5 = 0; i5 < i; i5++) {
            iArr[i5] = i3;
            if (i5 >= i4) {
                iArr[i5] = iArr[i5] + 1;
            }
        }
        this.mLayoutNumRows = i;
    }

    private boolean doItemsFit() {
        int[] iArr = this.mLayout;
        int i = this.mLayoutNumRows;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = iArr[i3];
            if (i4 == 1) {
                i2++;
            } else {
                int i5 = i4;
                while (i5 > 0) {
                    int i6 = i2 + 1;
                    if (((com.android.internal.view.menu.IconMenuView.LayoutParams) getChildAt(i2).getLayoutParams()).maxNumItemsOnRow < i4) {
                        return false;
                    }
                    i5--;
                    i2 = i6;
                }
            }
        }
        return true;
    }

    android.graphics.drawable.Drawable getItemBackgroundDrawable() {
        return this.mItemBackground.getConstantState().newDrawable(getContext().getResources());
    }

    com.android.internal.view.menu.IconMenuItemView createMoreItemView() {
        android.content.Context context = getContext();
        com.android.internal.view.menu.IconMenuItemView iconMenuItemView = (com.android.internal.view.menu.IconMenuItemView) android.view.LayoutInflater.from(context).inflate(com.android.internal.R.layout.icon_menu_item_layout, (android.view.ViewGroup) null);
        iconMenuItemView.initialize(context.getResources().getText(com.android.internal.R.string.more_item_label), this.mMoreIcon);
        iconMenuItemView.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.view.menu.IconMenuView.1
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                com.android.internal.view.menu.IconMenuView.this.mMenu.changeMenuMode();
            }
        });
        return iconMenuItemView;
    }

    @Override // com.android.internal.view.menu.MenuView
    public void initialize(com.android.internal.view.menu.MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    private void positionChildren(int i, int i2) {
        int[] iArr;
        com.android.internal.view.menu.IconMenuView.LayoutParams layoutParams;
        if (this.mHorizontalDivider != null) {
            this.mHorizontalDividerRects.clear();
        }
        if (this.mVerticalDivider != null) {
            this.mVerticalDividerRects.clear();
        }
        int i3 = this.mLayoutNumRows;
        int i4 = i3 - 1;
        int[] iArr2 = this.mLayout;
        float f = (i2 - (this.mHorizontalDividerHeight * i4)) / i3;
        com.android.internal.view.menu.IconMenuView.LayoutParams layoutParams2 = null;
        int i5 = 0;
        int i6 = 0;
        float f2 = 0.0f;
        while (i5 < i3) {
            float f3 = (i - (this.mVerticalDividerWidth * (iArr2[i5] - 1))) / iArr2[i5];
            int i7 = 0;
            float f4 = 0.0f;
            while (i7 < iArr2[i5]) {
                android.view.View childAt = getChildAt(i6);
                childAt.measure(android.view.View.MeasureSpec.makeMeasureSpec((int) f3, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec((int) f, 1073741824));
                com.android.internal.view.menu.IconMenuView.LayoutParams layoutParams3 = (com.android.internal.view.menu.IconMenuView.LayoutParams) childAt.getLayoutParams();
                layoutParams3.left = (int) f4;
                float f5 = f4 + f3;
                int i8 = (int) f5;
                layoutParams3.right = i8;
                int i9 = (int) f2;
                layoutParams3.top = i9;
                int i10 = (int) (f2 + f);
                layoutParams3.bottom = i10;
                i6++;
                int i11 = i3;
                if (this.mVerticalDivider != null) {
                    iArr = iArr2;
                    layoutParams = layoutParams3;
                    this.mVerticalDividerRects.add(new android.graphics.Rect(i8, i9, (int) (this.mVerticalDividerWidth + f5), i10));
                } else {
                    iArr = iArr2;
                    layoutParams = layoutParams3;
                }
                f4 = f5 + this.mVerticalDividerWidth;
                i7++;
                i3 = i11;
                iArr2 = iArr;
                layoutParams2 = layoutParams;
            }
            int i12 = i3;
            int[] iArr3 = iArr2;
            if (layoutParams2 != null) {
                layoutParams2.right = i;
            }
            f2 += f;
            if (this.mHorizontalDivider != null && i5 < i4) {
                this.mHorizontalDividerRects.add(new android.graphics.Rect(0, (int) f2, i, (int) (this.mHorizontalDividerHeight + f2)));
                f2 += this.mHorizontalDividerHeight;
            }
            i5++;
            i3 = i12;
            iArr2 = iArr3;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int resolveSize = resolveSize(Integer.MAX_VALUE, i);
        calculateItemFittingMetadata(resolveSize);
        layoutItems(resolveSize);
        int i3 = this.mLayoutNumRows;
        setMeasuredDimension(resolveSize, resolveSize(((this.mRowHeight + this.mHorizontalDividerHeight) * i3) - this.mHorizontalDividerHeight, i2));
        if (i3 > 0) {
            positionChildren(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            android.view.View childAt = getChildAt(childCount);
            com.android.internal.view.menu.IconMenuView.LayoutParams layoutParams = (com.android.internal.view.menu.IconMenuView.LayoutParams) childAt.getLayoutParams();
            childAt.layout(layoutParams.left, layoutParams.top, layoutParams.right, layoutParams.bottom);
        }
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        android.graphics.drawable.Drawable drawable = this.mHorizontalDivider;
        if (drawable != null) {
            java.util.ArrayList<android.graphics.Rect> arrayList = this.mHorizontalDividerRects;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                drawable.setBounds(arrayList.get(size));
                drawable.draw(canvas);
            }
        }
        android.graphics.drawable.Drawable drawable2 = this.mVerticalDivider;
        if (drawable2 != null) {
            java.util.ArrayList<android.graphics.Rect> arrayList2 = this.mVerticalDividerRects;
            for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
                drawable2.setBounds(arrayList2.get(size2));
                drawable2.draw(canvas);
            }
        }
    }

    @Override // com.android.internal.view.menu.MenuBuilder.ItemInvoker
    public boolean invokeItem(com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        return this.mMenu.performItemAction(menuItemImpl, 0);
    }

    @Override // android.view.ViewGroup
    public com.android.internal.view.menu.IconMenuView.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new com.android.internal.view.menu.IconMenuView.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof com.android.internal.view.menu.IconMenuView.LayoutParams;
    }

    void markStaleChildren() {
        if (!this.mHasStaleChildren) {
            this.mHasStaleChildren = true;
            requestLayout();
        }
    }

    int getNumActualItemsShown() {
        return this.mNumActualItemsShown;
    }

    void setNumActualItemsShown(int i) {
        this.mNumActualItemsShown = i;
    }

    @Override // com.android.internal.view.menu.MenuView
    public int getWindowAnimations() {
        return this.mAnimations;
    }

    public int[] getLayout() {
        return this.mLayout;
    }

    public int getLayoutNumRows() {
        return this.mLayoutNumRows;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 82) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                removeCallbacks(this);
                postDelayed(this, android.view.ViewConfiguration.getLongPressTimeout());
            } else if (keyEvent.getAction() == 1) {
                if (this.mMenuBeingLongpressed) {
                    setCycleShortcutCaptionMode(false);
                    return true;
                }
                removeCallbacks(this);
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        requestFocus();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        setCycleShortcutCaptionMode(false);
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        if (!z) {
            setCycleShortcutCaptionMode(false);
        }
        super.onWindowFocusChanged(z);
    }

    private void setCycleShortcutCaptionMode(boolean z) {
        if (!z) {
            removeCallbacks(this);
            setChildrenCaptionMode(false);
            this.mMenuBeingLongpressed = false;
            return;
        }
        setChildrenCaptionMode(true);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.mMenuBeingLongpressed) {
            setChildrenCaptionMode(!this.mLastChildrenCaptionMode);
        } else {
            this.mMenuBeingLongpressed = true;
            setCycleShortcutCaptionMode(true);
        }
        postDelayed(this, 1000L);
    }

    private void setChildrenCaptionMode(boolean z) {
        this.mLastChildrenCaptionMode = z;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            ((com.android.internal.view.menu.IconMenuItemView) getChildAt(childCount)).setCaptionMode(z);
        }
    }

    private void calculateItemFittingMetadata(int i) {
        int i2 = this.mMaxItemsPerRow;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            com.android.internal.view.menu.IconMenuView.LayoutParams layoutParams = (com.android.internal.view.menu.IconMenuView.LayoutParams) getChildAt(i3).getLayoutParams();
            layoutParams.maxNumItemsOnRow = 1;
            int i4 = i2;
            while (true) {
                if (i4 <= 0) {
                    break;
                }
                if (layoutParams.desiredWidth < i / i4) {
                    layoutParams.maxNumItemsOnRow = i4;
                    break;
                }
                i4--;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        android.view.View focusedChild = getFocusedChild();
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            if (getChildAt(childCount) == focusedChild) {
                return new com.android.internal.view.menu.IconMenuView.SavedState(onSaveInstanceState, childCount);
            }
        }
        return new com.android.internal.view.menu.IconMenuView.SavedState(onSaveInstanceState, -1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        android.view.View childAt;
        com.android.internal.view.menu.IconMenuView.SavedState savedState = (com.android.internal.view.menu.IconMenuView.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.focusedPosition < getChildCount() && (childAt = getChildAt(savedState.focusedPosition)) != null) {
            childAt.requestFocus();
        }
    }

    private static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<com.android.internal.view.menu.IconMenuView.SavedState> CREATOR = new android.os.Parcelable.Creator<com.android.internal.view.menu.IconMenuView.SavedState>() { // from class: com.android.internal.view.menu.IconMenuView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.view.menu.IconMenuView.SavedState createFromParcel(android.os.Parcel parcel) {
                return new com.android.internal.view.menu.IconMenuView.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.view.menu.IconMenuView.SavedState[] newArray(int i) {
                return new com.android.internal.view.menu.IconMenuView.SavedState[i];
            }
        };
        int focusedPosition;

        public SavedState(android.os.Parcelable parcelable, int i) {
            super(parcelable);
            this.focusedPosition = i;
        }

        private SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.focusedPosition = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.focusedPosition);
        }
    }

    public static class LayoutParams extends android.view.ViewGroup.MarginLayoutParams {
        int bottom;
        int desiredWidth;
        int left;
        int maxNumItemsOnRow;
        int right;
        int top;

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }
    }
}
