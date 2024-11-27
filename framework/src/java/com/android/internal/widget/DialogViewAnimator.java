package com.android.internal.widget;

/* loaded from: classes5.dex */
public class DialogViewAnimator extends android.widget.ViewAnimator {
    private final java.util.ArrayList<android.view.View> mMatchParentChildren;

    public DialogViewAnimator(android.content.Context context) {
        super(context);
        this.mMatchParentChildren = new java.util.ArrayList<>(1);
    }

    public DialogViewAnimator(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMatchParentChildren = new java.util.ArrayList<>(1);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int childMeasureSpec;
        int childMeasureSpec2;
        int i3;
        boolean z = (android.view.View.MeasureSpec.getMode(i) == 1073741824 && android.view.View.MeasureSpec.getMode(i2) == 1073741824) ? false : true;
        int childCount = getChildCount();
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            android.view.View childAt = getChildAt(i7);
            if (getMeasureAllChildren() || childAt.getVisibility() != 8) {
                android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) childAt.getLayoutParams();
                boolean z2 = layoutParams.width == -1;
                boolean z3 = layoutParams.height == -1;
                if (z && (z2 || z3)) {
                    this.mMatchParentChildren.add(childAt);
                }
                int i8 = i4;
                int i9 = i5;
                measureChildWithMargins(childAt, i, 0, i2, 0);
                if (z && !z2) {
                    int max = java.lang.Math.max(i6, childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                    i3 = (childAt.getMeasuredWidthAndState() & (-16777216)) | 0;
                    i6 = max;
                } else {
                    i3 = 0;
                }
                if (z && !z3) {
                    int max2 = java.lang.Math.max(i9, childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                    i3 |= (childAt.getMeasuredHeightAndState() >> 16) & (-256);
                    i5 = max2;
                } else {
                    i5 = i9;
                }
                i4 = combineMeasuredStates(i8, i3);
            }
        }
        int i10 = i4;
        int paddingLeft = i6 + getPaddingLeft() + getPaddingRight();
        int max3 = java.lang.Math.max(i5 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight());
        int max4 = java.lang.Math.max(paddingLeft, getSuggestedMinimumWidth());
        android.graphics.drawable.Drawable foreground = getForeground();
        if (foreground != null) {
            max3 = java.lang.Math.max(max3, foreground.getMinimumHeight());
            max4 = java.lang.Math.max(max4, foreground.getMinimumWidth());
        }
        setMeasuredDimension(resolveSizeAndState(max4, i, i10), resolveSizeAndState(max3, i2, i10 << 16));
        int size = this.mMatchParentChildren.size();
        for (int i11 = 0; i11 < size; i11++) {
            android.view.View view = this.mMatchParentChildren.get(i11);
            android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (marginLayoutParams.width == -1) {
                childMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec((((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) - marginLayoutParams.leftMargin) - marginLayoutParams.rightMargin, 1073741824);
            } else {
                childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width);
            }
            if (marginLayoutParams.height == -1) {
                childMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec((((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom()) - marginLayoutParams.topMargin) - marginLayoutParams.bottomMargin, 1073741824);
            } else {
                childMeasureSpec2 = getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height);
            }
            view.measure(childMeasureSpec, childMeasureSpec2);
        }
        this.mMatchParentChildren.clear();
    }
}
