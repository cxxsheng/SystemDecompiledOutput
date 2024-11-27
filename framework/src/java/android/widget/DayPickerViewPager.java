package android.widget;

/* loaded from: classes4.dex */
class DayPickerViewPager extends com.android.internal.widget.ViewPager {
    private final java.util.ArrayList<android.view.View> mMatchParentChildren;

    public DayPickerViewPager(android.content.Context context) {
        this(context, null);
    }

    public DayPickerViewPager(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DayPickerViewPager(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DayPickerViewPager(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMatchParentChildren = new java.util.ArrayList<>(1);
    }

    @Override // com.android.internal.widget.ViewPager, android.view.View
    protected void onMeasure(int i, int i2) {
        int childMeasureSpec;
        int childMeasureSpec2;
        populate();
        int childCount = getChildCount();
        boolean z = (android.view.View.MeasureSpec.getMode(i) == 1073741824 && android.view.View.MeasureSpec.getMode(i2) == 1073741824) ? false : true;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            android.view.View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                measureChild(childAt, i, i2);
                com.android.internal.widget.ViewPager.LayoutParams layoutParams = (com.android.internal.widget.ViewPager.LayoutParams) childAt.getLayoutParams();
                i3 = java.lang.Math.max(i3, childAt.getMeasuredWidth());
                i4 = java.lang.Math.max(i4, childAt.getMeasuredHeight());
                i5 = combineMeasuredStates(i5, childAt.getMeasuredState());
                if (z && (layoutParams.width == -1 || layoutParams.height == -1)) {
                    this.mMatchParentChildren.add(childAt);
                }
            }
        }
        int paddingLeft = i3 + getPaddingLeft() + getPaddingRight();
        int max = java.lang.Math.max(i4 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight());
        int max2 = java.lang.Math.max(paddingLeft, getSuggestedMinimumWidth());
        android.graphics.drawable.Drawable foreground = getForeground();
        if (foreground != null) {
            max = java.lang.Math.max(max, foreground.getMinimumHeight());
            max2 = java.lang.Math.max(max2, foreground.getMinimumWidth());
        }
        setMeasuredDimension(resolveSizeAndState(max2, i, i5), resolveSizeAndState(max, i2, i5 << 16));
        int size = this.mMatchParentChildren.size();
        if (size > 1) {
            for (int i7 = 0; i7 < size; i7++) {
                android.view.View view = this.mMatchParentChildren.get(i7);
                com.android.internal.widget.ViewPager.LayoutParams layoutParams2 = (com.android.internal.widget.ViewPager.LayoutParams) view.getLayoutParams();
                if (layoutParams2.width == -1) {
                    childMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824);
                } else {
                    childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), layoutParams2.width);
                }
                if (layoutParams2.height == -1) {
                    childMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824);
                } else {
                    childMeasureSpec2 = getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom(), layoutParams2.height);
                }
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }
        this.mMatchParentChildren.clear();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected <T extends android.view.View> T findViewByPredicateTraversal(java.util.function.Predicate<android.view.View> predicate, android.view.View view) {
        T t;
        T t2;
        if (predicate.test(this)) {
            return this;
        }
        android.widget.SimpleMonthView view2 = ((android.widget.DayPickerPagerAdapter) getAdapter()).getView(getCurrent());
        if (view2 != view && view2 != null && (t2 = (T) view2.findViewByPredicate(predicate)) != null) {
            return t2;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt != view && childAt != view2 && (t = (T) childAt.findViewByPredicate(predicate)) != null) {
                return t;
            }
        }
        return null;
    }
}
