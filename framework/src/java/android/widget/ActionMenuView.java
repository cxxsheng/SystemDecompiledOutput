package android.widget;

/* loaded from: classes4.dex */
public class ActionMenuView extends android.widget.LinearLayout implements com.android.internal.view.menu.MenuBuilder.ItemInvoker, com.android.internal.view.menu.MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final java.lang.String TAG = "ActionMenuView";
    private com.android.internal.view.menu.MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private com.android.internal.view.menu.MenuBuilder mMenu;
    private com.android.internal.view.menu.MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    private android.widget.ActionMenuView.OnMenuItemClickListener mOnMenuItemClickListener;
    private android.content.Context mPopupContext;
    private int mPopupTheme;
    private android.widget.ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(android.view.MenuItem menuItem);
    }

    public ActionMenuView(android.content.Context context) {
        this(context, null);
    }

    public ActionMenuView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        setBaselineAligned(false);
        float f = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int) (56.0f * f);
        this.mGeneratedItemPadding = (int) (f * 4.0f);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
    }

    public void setPopupTheme(int i) {
        if (this.mPopupTheme != i) {
            this.mPopupTheme = i;
            if (i == 0) {
                this.mPopupContext = this.mContext;
            } else {
                this.mPopupContext = new android.view.ContextThemeWrapper(this.mContext, i);
            }
        }
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public void setPresenter(android.widget.ActionMenuPresenter actionMenuPresenter) {
        this.mPresenter = actionMenuPresenter;
        this.mPresenter.setMenuView(this);
    }

    @Override // android.view.View
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mPresenter != null) {
            this.mPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    public void setOnMenuItemClickListener(android.widget.ActionMenuView.OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        boolean z = this.mFormatItems;
        this.mFormatItems = android.view.View.MeasureSpec.getMode(i) == 1073741824;
        if (z != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int size = android.view.View.MeasureSpec.getSize(i);
        if (this.mFormatItems && this.mMenu != null && size != this.mFormatItemsWidth) {
            this.mFormatItemsWidth = size;
            this.mMenu.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (this.mFormatItems && childCount > 0) {
            onMeasureExactFormat(i, i2);
            return;
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            android.widget.ActionMenuView.LayoutParams layoutParams = (android.widget.ActionMenuView.LayoutParams) getChildAt(i3).getLayoutParams();
            layoutParams.rightMargin = 0;
            layoutParams.leftMargin = 0;
        }
        super.onMeasure(i, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x027b  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x02a4  */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v28, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v43 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void onMeasureExactFormat(int i, int i2) {
        int i3;
        boolean z;
        int i4;
        int i5;
        int i6;
        int i7;
        boolean z2;
        int i8;
        boolean z3;
        int i9;
        int i10;
        int i11;
        int i12;
        ?? r2;
        int mode = android.view.View.MeasureSpec.getMode(i2);
        int size = android.view.View.MeasureSpec.getSize(i);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int childMeasureSpec = getChildMeasureSpec(i2, paddingTop, -2);
        int i13 = size - paddingLeft;
        int i14 = i13 / this.mMinCellSize;
        int i15 = i13 % this.mMinCellSize;
        if (i14 == 0) {
            setMeasuredDimension(i13, 0);
            return;
        }
        int i16 = this.mMinCellSize + (i15 / i14);
        int childCount = getChildCount();
        int i17 = 0;
        int i18 = 0;
        boolean z4 = false;
        int i19 = 0;
        int i20 = 0;
        int i21 = 0;
        long j = 0;
        while (i18 < childCount) {
            android.view.View childAt = getChildAt(i18);
            int i22 = size2;
            if (childAt.getVisibility() == 8) {
                i11 = i13;
            } else {
                boolean z5 = childAt instanceof com.android.internal.view.menu.ActionMenuItemView;
                int i23 = i19 + 1;
                if (z5) {
                    i12 = i23;
                    i11 = i13;
                    r2 = 0;
                    childAt.setPadding(this.mGeneratedItemPadding, 0, this.mGeneratedItemPadding, 0);
                } else {
                    i11 = i13;
                    i12 = i23;
                    r2 = 0;
                }
                android.widget.ActionMenuView.LayoutParams layoutParams = (android.widget.ActionMenuView.LayoutParams) childAt.getLayoutParams();
                layoutParams.expanded = r2;
                layoutParams.extraPixels = r2;
                layoutParams.cellsUsed = r2;
                layoutParams.expandable = r2;
                layoutParams.leftMargin = r2;
                layoutParams.rightMargin = r2;
                layoutParams.preventEdgeOffset = z5 && ((com.android.internal.view.menu.ActionMenuItemView) childAt).hasText();
                int measureChildForCells = measureChildForCells(childAt, i16, layoutParams.isOverflowButton ? 1 : i14, childMeasureSpec, paddingTop);
                i20 = java.lang.Math.max(i20, measureChildForCells);
                if (layoutParams.expandable) {
                    i21++;
                }
                if (layoutParams.isOverflowButton) {
                    z4 = true;
                }
                i14 -= measureChildForCells;
                i17 = java.lang.Math.max(i17, childAt.getMeasuredHeight());
                if (measureChildForCells == 1) {
                    j |= 1 << i18;
                }
                i19 = i12;
            }
            i18++;
            size2 = i22;
            i13 = i11;
        }
        int i24 = i13;
        int i25 = size2;
        boolean z6 = z4 && i19 == 2;
        boolean z7 = false;
        while (i21 > 0 && i14 > 0) {
            int i26 = Integer.MAX_VALUE;
            z = z7;
            int i27 = 0;
            int i28 = 0;
            long j2 = 0;
            while (i28 < childCount) {
                int i29 = i17;
                android.widget.ActionMenuView.LayoutParams layoutParams2 = (android.widget.ActionMenuView.LayoutParams) getChildAt(i28).getLayoutParams();
                int i30 = mode;
                if (!layoutParams2.expandable) {
                    i9 = i19;
                    i10 = i20;
                } else if (layoutParams2.cellsUsed < i26) {
                    i26 = layoutParams2.cellsUsed;
                    j2 = 1 << i28;
                    i9 = i19;
                    i10 = i20;
                    i27 = 1;
                } else if (layoutParams2.cellsUsed != i26) {
                    i9 = i19;
                    i10 = i20;
                } else {
                    i9 = i19;
                    i10 = i20;
                    j2 |= 1 << i28;
                    i27++;
                }
                i28++;
                i19 = i9;
                mode = i30;
                i17 = i29;
                i20 = i10;
            }
            i3 = mode;
            i4 = i17;
            i5 = i19;
            i6 = i20;
            j |= j2;
            if (i27 > i14) {
                break;
            }
            int i31 = i26 + 1;
            int i32 = 0;
            while (i32 < childCount) {
                android.view.View childAt2 = getChildAt(i32);
                android.widget.ActionMenuView.LayoutParams layoutParams3 = (android.widget.ActionMenuView.LayoutParams) childAt2.getLayoutParams();
                long j3 = 1 << i32;
                if ((j2 & j3) == 0) {
                    if (layoutParams3.cellsUsed == i31) {
                        j |= j3;
                    }
                    z3 = z6;
                } else {
                    if (z6 && layoutParams3.preventEdgeOffset && i14 == 1) {
                        z3 = z6;
                        childAt2.setPadding(this.mGeneratedItemPadding + i16, 0, this.mGeneratedItemPadding, 0);
                    } else {
                        z3 = z6;
                    }
                    layoutParams3.cellsUsed++;
                    layoutParams3.expanded = true;
                    i14--;
                }
                i32++;
                z6 = z3;
            }
            i19 = i5;
            mode = i3;
            i17 = i4;
            i20 = i6;
            z7 = true;
        }
        i3 = mode;
        z = z7;
        i4 = i17;
        i5 = i19;
        i6 = i20;
        boolean z8 = !z4 && i5 == 1;
        if (i14 <= 0 || j == 0) {
            i7 = 0;
        } else {
            if (i14 < i5 - 1 || z8 || i6 > 1) {
                float bitCount = java.lang.Long.bitCount(j);
                if (z8) {
                    i7 = 0;
                } else {
                    if ((j & 1) == 0) {
                        i7 = 0;
                    } else {
                        i7 = 0;
                        if (!((android.widget.ActionMenuView.LayoutParams) getChildAt(0).getLayoutParams()).preventEdgeOffset) {
                            bitCount -= 0.5f;
                        }
                    }
                    int i33 = childCount - 1;
                    if ((j & (1 << i33)) != 0 && !((android.widget.ActionMenuView.LayoutParams) getChildAt(i33).getLayoutParams()).preventEdgeOffset) {
                        bitCount -= 0.5f;
                    }
                }
                int i34 = bitCount > 0.0f ? (int) ((i14 * i16) / bitCount) : i7;
                for (int i35 = i7; i35 < childCount; i35++) {
                    if ((j & (1 << i35)) != 0) {
                        android.view.View childAt3 = getChildAt(i35);
                        android.widget.ActionMenuView.LayoutParams layoutParams4 = (android.widget.ActionMenuView.LayoutParams) childAt3.getLayoutParams();
                        if (childAt3 instanceof com.android.internal.view.menu.ActionMenuItemView) {
                            layoutParams4.extraPixels = i34;
                            layoutParams4.expanded = true;
                            if (i35 == 0 && !layoutParams4.preventEdgeOffset) {
                                layoutParams4.leftMargin = (-i34) / 2;
                            }
                            z = true;
                        } else if (layoutParams4.isOverflowButton) {
                            layoutParams4.extraPixels = i34;
                            layoutParams4.expanded = true;
                            layoutParams4.rightMargin = (-i34) / 2;
                            z = true;
                        } else {
                            if (i35 != 0) {
                                layoutParams4.leftMargin = i34 / 2;
                            }
                            if (i35 != childCount - 1) {
                                layoutParams4.rightMargin = i34 / 2;
                            }
                        }
                    }
                }
                z2 = z;
                if (z2) {
                    for (int i36 = i7; i36 < childCount; i36++) {
                        android.view.View childAt4 = getChildAt(i36);
                        android.widget.ActionMenuView.LayoutParams layoutParams5 = (android.widget.ActionMenuView.LayoutParams) childAt4.getLayoutParams();
                        if (layoutParams5.expanded) {
                            childAt4.measure(android.view.View.MeasureSpec.makeMeasureSpec((layoutParams5.cellsUsed * i16) + layoutParams5.extraPixels, 1073741824), childMeasureSpec);
                        }
                    }
                }
                if (i3 != 1073741824) {
                    i8 = i25;
                } else {
                    i8 = i4;
                }
                setMeasuredDimension(i24, i8);
            }
            i7 = 0;
        }
        z2 = z;
        if (z2) {
        }
        if (i3 != 1073741824) {
        }
        setMeasuredDimension(i24, i8);
    }

    static int measureChildForCells(android.view.View view, int i, int i2, int i3, int i4) {
        int i5;
        android.widget.ActionMenuView.LayoutParams layoutParams = (android.widget.ActionMenuView.LayoutParams) view.getLayoutParams();
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(android.view.View.MeasureSpec.getSize(i3) - i4, android.view.View.MeasureSpec.getMode(i3));
        com.android.internal.view.menu.ActionMenuItemView actionMenuItemView = view instanceof com.android.internal.view.menu.ActionMenuItemView ? (com.android.internal.view.menu.ActionMenuItemView) view : null;
        boolean z = actionMenuItemView != null && actionMenuItemView.hasText();
        if (i2 > 0) {
            i5 = 2;
            if (!z || i2 >= 2) {
                view.measure(android.view.View.MeasureSpec.makeMeasureSpec(i2 * i, Integer.MIN_VALUE), makeMeasureSpec);
                int measuredWidth = view.getMeasuredWidth();
                int i6 = measuredWidth / i;
                if (measuredWidth % i != 0) {
                    i6++;
                }
                if (!z || i6 >= 2) {
                    i5 = i6;
                }
                layoutParams.expandable = layoutParams.isOverflowButton && z;
                layoutParams.cellsUsed = i5;
                view.measure(android.view.View.MeasureSpec.makeMeasureSpec(i * i5, 1073741824), makeMeasureSpec);
                return i5;
            }
        }
        i5 = 0;
        layoutParams.expandable = layoutParams.isOverflowButton && z;
        layoutParams.cellsUsed = i5;
        view.measure(android.view.View.MeasureSpec.makeMeasureSpec(i * i5, 1073741824), makeMeasureSpec);
        return i5;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int width;
        int i5;
        if (!this.mFormatItems) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int childCount = getChildCount();
        int i6 = (i4 - i2) / 2;
        int dividerWidth = getDividerWidth();
        int i7 = i3 - i;
        int paddingRight = (i7 - getPaddingRight()) - getPaddingLeft();
        boolean isLayoutRtl = isLayoutRtl();
        int i8 = 0;
        int i9 = 0;
        for (int i10 = 0; i10 < childCount; i10++) {
            android.view.View childAt = getChildAt(i10);
            if (childAt.getVisibility() != 8) {
                android.widget.ActionMenuView.LayoutParams layoutParams = (android.widget.ActionMenuView.LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isOverflowButton) {
                    int measuredWidth = childAt.getMeasuredWidth();
                    if (hasDividerBeforeChildAt(i10)) {
                        measuredWidth += dividerWidth;
                    }
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (isLayoutRtl) {
                        i5 = getPaddingLeft() + layoutParams.leftMargin;
                        width = i5 + measuredWidth;
                    } else {
                        width = (getWidth() - getPaddingRight()) - layoutParams.rightMargin;
                        i5 = width - measuredWidth;
                    }
                    int i11 = i6 - (measuredHeight / 2);
                    childAt.layout(i5, i11, width, measuredHeight + i11);
                    paddingRight -= measuredWidth;
                    i8 = 1;
                } else {
                    paddingRight -= (childAt.getMeasuredWidth() + layoutParams.leftMargin) + layoutParams.rightMargin;
                    hasDividerBeforeChildAt(i10);
                    i9++;
                }
            }
        }
        if (childCount == 1 && i8 == 0) {
            android.view.View childAt2 = getChildAt(0);
            int measuredWidth2 = childAt2.getMeasuredWidth();
            int measuredHeight2 = childAt2.getMeasuredHeight();
            int i12 = (i7 / 2) - (measuredWidth2 / 2);
            int i13 = i6 - (measuredHeight2 / 2);
            childAt2.layout(i12, i13, measuredWidth2 + i12, measuredHeight2 + i13);
            return;
        }
        int i14 = i9 - (i8 ^ 1);
        int max = java.lang.Math.max(0, i14 > 0 ? paddingRight / i14 : 0);
        if (isLayoutRtl) {
            int width2 = getWidth() - getPaddingRight();
            for (int i15 = 0; i15 < childCount; i15++) {
                android.view.View childAt3 = getChildAt(i15);
                android.widget.ActionMenuView.LayoutParams layoutParams2 = (android.widget.ActionMenuView.LayoutParams) childAt3.getLayoutParams();
                if (childAt3.getVisibility() != 8 && !layoutParams2.isOverflowButton) {
                    int i16 = width2 - layoutParams2.rightMargin;
                    int measuredWidth3 = childAt3.getMeasuredWidth();
                    int measuredHeight3 = childAt3.getMeasuredHeight();
                    int i17 = i6 - (measuredHeight3 / 2);
                    childAt3.layout(i16 - measuredWidth3, i17, i16, measuredHeight3 + i17);
                    width2 = i16 - ((measuredWidth3 + layoutParams2.leftMargin) + max);
                }
            }
            return;
        }
        int paddingLeft = getPaddingLeft();
        for (int i18 = 0; i18 < childCount; i18++) {
            android.view.View childAt4 = getChildAt(i18);
            android.widget.ActionMenuView.LayoutParams layoutParams3 = (android.widget.ActionMenuView.LayoutParams) childAt4.getLayoutParams();
            if (childAt4.getVisibility() != 8 && !layoutParams3.isOverflowButton) {
                int i19 = paddingLeft + layoutParams3.leftMargin;
                int measuredWidth4 = childAt4.getMeasuredWidth();
                int measuredHeight4 = childAt4.getMeasuredHeight();
                int i20 = i6 - (measuredHeight4 / 2);
                childAt4.layout(i19, i20, i19 + measuredWidth4, measuredHeight4 + i20);
                paddingLeft = i19 + measuredWidth4 + layoutParams3.rightMargin + max;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    public void setOverflowIcon(android.graphics.drawable.Drawable drawable) {
        getMenu();
        this.mPresenter.setOverflowIcon(drawable);
    }

    public android.graphics.drawable.Drawable getOverflowIcon() {
        getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    public void setOverflowReserved(boolean z) {
        this.mReserveOverflow = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public android.widget.ActionMenuView.LayoutParams generateDefaultLayoutParams() {
        android.widget.ActionMenuView.LayoutParams layoutParams = new android.widget.ActionMenuView.LayoutParams(-2, -2);
        layoutParams.gravity = 16;
        return layoutParams;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public android.widget.ActionMenuView.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.widget.ActionMenuView.LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public android.widget.ActionMenuView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        android.widget.ActionMenuView.LayoutParams layoutParams2;
        if (layoutParams != null) {
            if (layoutParams instanceof android.widget.ActionMenuView.LayoutParams) {
                layoutParams2 = new android.widget.ActionMenuView.LayoutParams((android.widget.ActionMenuView.LayoutParams) layoutParams);
            } else {
                layoutParams2 = new android.widget.ActionMenuView.LayoutParams(layoutParams);
            }
            if (layoutParams2.gravity <= 0) {
                layoutParams2.gravity = 16;
            }
            return layoutParams2;
        }
        return generateDefaultLayoutParams();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams != null && (layoutParams instanceof android.widget.ActionMenuView.LayoutParams);
    }

    public android.widget.ActionMenuView.LayoutParams generateOverflowButtonLayoutParams() {
        android.widget.ActionMenuView.LayoutParams generateDefaultLayoutParams = generateDefaultLayoutParams();
        generateDefaultLayoutParams.isOverflowButton = true;
        return generateDefaultLayoutParams;
    }

    @Override // com.android.internal.view.menu.MenuBuilder.ItemInvoker
    public boolean invokeItem(com.android.internal.view.menu.MenuItemImpl menuItemImpl) {
        return this.mMenu.performItemAction(menuItemImpl, 0);
    }

    @Override // com.android.internal.view.menu.MenuView
    public int getWindowAnimations() {
        return 0;
    }

    @Override // com.android.internal.view.menu.MenuView
    public void initialize(com.android.internal.view.menu.MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    public android.view.Menu getMenu() {
        com.android.internal.view.menu.MenuPresenter.Callback actionMenuPresenterCallback;
        if (this.mMenu == null) {
            android.content.Context context = getContext();
            this.mMenu = new com.android.internal.view.menu.MenuBuilder(context);
            byte b = 0;
            this.mMenu.setCallback(new android.widget.ActionMenuView.MenuBuilderCallback());
            this.mPresenter = new android.widget.ActionMenuPresenter(context);
            this.mPresenter.setReserveOverflow(true);
            android.widget.ActionMenuPresenter actionMenuPresenter = this.mPresenter;
            if (this.mActionMenuPresenterCallback != null) {
                actionMenuPresenterCallback = this.mActionMenuPresenterCallback;
            } else {
                actionMenuPresenterCallback = new android.widget.ActionMenuView.ActionMenuPresenterCallback();
            }
            actionMenuPresenter.setCallback(actionMenuPresenterCallback);
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    public void setMenuCallbacks(com.android.internal.view.menu.MenuPresenter.Callback callback, com.android.internal.view.menu.MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
    }

    public com.android.internal.view.menu.MenuBuilder peekMenu() {
        return this.mMenu;
    }

    public boolean showOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.hideOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowPending();
    }

    public void dismissPopupMenus() {
        if (this.mPresenter != null) {
            this.mPresenter.dismissPopupMenus();
        }
    }

    @Override // android.widget.LinearLayout
    protected boolean hasDividerBeforeChildAt(int i) {
        boolean z = false;
        if (i == 0) {
            return false;
        }
        android.view.KeyEvent.Callback childAt = getChildAt(i - 1);
        android.view.KeyEvent.Callback childAt2 = getChildAt(i);
        if (i < getChildCount() && (childAt instanceof android.widget.ActionMenuView.ActionMenuChildView)) {
            z = false | ((android.widget.ActionMenuView.ActionMenuChildView) childAt).needsDividerAfter();
        }
        if (i > 0 && (childAt2 instanceof android.widget.ActionMenuView.ActionMenuChildView)) {
            return z | ((android.widget.ActionMenuView.ActionMenuChildView) childAt2).needsDividerBefore();
        }
        return z;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        return false;
    }

    public void setExpandedActionViewsExclusive(boolean z) {
        this.mPresenter.setExpandedActionViewsExclusive(z);
    }

    private class MenuBuilderCallback implements com.android.internal.view.menu.MenuBuilder.Callback {
        private MenuBuilderCallback() {
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem) {
            return android.widget.ActionMenuView.this.mOnMenuItemClickListener != null && android.widget.ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(menuItem);
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            if (android.widget.ActionMenuView.this.mMenuBuilderCallback != null) {
                android.widget.ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menuBuilder);
            }
        }
    }

    private class ActionMenuPresenterCallback implements com.android.internal.view.menu.MenuPresenter.Callback {
        private ActionMenuPresenterCallback() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            return false;
        }
    }

    public static class LayoutParams extends android.widget.LinearLayout.LayoutParams {

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        public int cellsUsed;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        public boolean expandable;
        public boolean expanded;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        public int extraPixels;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        public boolean isOverflowButton;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        public boolean preventEdgeOffset;

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(android.widget.ActionMenuView.LayoutParams layoutParams) {
            super((android.widget.LinearLayout.LayoutParams) layoutParams);
            this.isOverflowButton = layoutParams.isOverflowButton;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.isOverflowButton = false;
        }

        public LayoutParams(int i, int i2, boolean z) {
            super(i, i2);
            this.isOverflowButton = z;
        }

        @Override // android.widget.LinearLayout.LayoutParams, android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:overFlowButton", this.isOverflowButton);
            viewHierarchyEncoder.addProperty("layout:cellsUsed", this.cellsUsed);
            viewHierarchyEncoder.addProperty("layout:extraPixels", this.extraPixels);
            viewHierarchyEncoder.addProperty("layout:expandable", this.expandable);
            viewHierarchyEncoder.addProperty("layout:preventEdgeOffset", this.preventEdgeOffset);
        }
    }
}
