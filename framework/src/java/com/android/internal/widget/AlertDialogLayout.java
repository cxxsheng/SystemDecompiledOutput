package com.android.internal.widget;

/* loaded from: classes5.dex */
public class AlertDialogLayout extends android.widget.LinearLayout {
    public AlertDialogLayout(android.content.Context context) {
        super(context);
    }

    public AlertDialogLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AlertDialogLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public AlertDialogLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (!tryOnMeasure(i, i2)) {
            super.onMeasure(i, i2);
        }
    }

    private boolean tryOnMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int makeMeasureSpec;
        int childCount = getChildCount();
        android.view.View view = null;
        android.view.View view2 = null;
        android.view.View view3 = null;
        for (int i7 = 0; i7 < childCount; i7++) {
            android.view.View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                switch (childAt.getId()) {
                    case com.android.internal.R.id.buttonPanel /* 16908851 */:
                        view2 = childAt;
                        break;
                    case com.android.internal.R.id.contentPanel /* 16908911 */:
                    case com.android.internal.R.id.customPanel /* 16908950 */:
                        if (view3 != null) {
                            return false;
                        }
                        view3 = childAt;
                        break;
                    case com.android.internal.R.id.topPanel /* 16909678 */:
                        view = childAt;
                        break;
                    default:
                        return false;
                }
            }
        }
        int mode = android.view.View.MeasureSpec.getMode(i2);
        int size = android.view.View.MeasureSpec.getSize(i2);
        int mode2 = android.view.View.MeasureSpec.getMode(i);
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (view == null) {
            i3 = 0;
        } else {
            view.measure(i, 0);
            paddingTop += view.getMeasuredHeight();
            i3 = combineMeasuredStates(0, view.getMeasuredState());
        }
        if (view2 == null) {
            i4 = 0;
            i5 = 0;
        } else {
            view2.measure(i, 0);
            i4 = resolveMinimumHeight(view2);
            i5 = view2.getMeasuredHeight() - i4;
            paddingTop += i4;
            i3 = combineMeasuredStates(i3, view2.getMeasuredState());
        }
        if (view3 == null) {
            i6 = 0;
        } else {
            if (mode == 0) {
                makeMeasureSpec = 0;
            } else {
                makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, size - paddingTop), mode);
            }
            view3.measure(i, makeMeasureSpec);
            i6 = view3.getMeasuredHeight();
            paddingTop += i6;
            i3 = combineMeasuredStates(i3, view3.getMeasuredState());
        }
        int i8 = size - paddingTop;
        if (view2 != null) {
            int i9 = paddingTop - i4;
            int min = java.lang.Math.min(i8, i5);
            if (min > 0) {
                i8 -= min;
                i4 += min;
            }
            view2.measure(i, android.view.View.MeasureSpec.makeMeasureSpec(i4, 1073741824));
            paddingTop = i9 + view2.getMeasuredHeight();
            i3 = combineMeasuredStates(i3, view2.getMeasuredState());
        }
        if (view3 != null && i8 > 0) {
            view3.measure(i, android.view.View.MeasureSpec.makeMeasureSpec(i6 + i8, mode));
            paddingTop = (paddingTop - i6) + view3.getMeasuredHeight();
            i3 = combineMeasuredStates(i3, view3.getMeasuredState());
        }
        int i10 = 0;
        for (int i11 = 0; i11 < childCount; i11++) {
            android.view.View childAt2 = getChildAt(i11);
            if (childAt2.getVisibility() != 8) {
                i10 = java.lang.Math.max(i10, childAt2.getMeasuredWidth());
            }
        }
        setMeasuredDimension(resolveSizeAndState(i10 + getPaddingLeft() + getPaddingRight(), i, i3), resolveSizeAndState(paddingTop, i2, 0));
        if (mode2 != 1073741824) {
            forceUniformWidth(childCount, i2);
            return true;
        }
        return true;
    }

    private void forceUniformWidth(int i, int i2) {
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            android.view.View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) childAt.getLayoutParams();
                if (layoutParams.width == -1) {
                    int i4 = layoutParams.height;
                    layoutParams.height = childAt.getMeasuredHeight();
                    measureChildWithMargins(childAt, makeMeasureSpec, 0, i2, 0);
                    layoutParams.height = i4;
                }
            }
        }
    }

    private int resolveMinimumHeight(android.view.View view) {
        int minimumHeight = view.getMinimumHeight();
        if (minimumHeight > 0) {
            return minimumHeight;
        }
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) view;
            if (viewGroup.getChildCount() == 1) {
                return resolveMinimumHeight(viewGroup.getChildAt(0));
            }
        }
        return 0;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = this.mPaddingLeft;
        int i8 = i3 - i;
        int i9 = i8 - this.mPaddingRight;
        int i10 = (i8 - i7) - this.mPaddingRight;
        int measuredHeight = getMeasuredHeight();
        int childCount = getChildCount();
        int gravity = getGravity();
        int i11 = gravity & 112;
        int i12 = gravity & android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        switch (i11) {
            case 16:
                i5 = this.mPaddingTop + (((i4 - i2) - measuredHeight) / 2);
                break;
            case 80:
                i5 = ((this.mPaddingTop + i4) - i2) - measuredHeight;
                break;
            default:
                i5 = this.mPaddingTop;
                break;
        }
        android.graphics.drawable.Drawable dividerDrawable = getDividerDrawable();
        int intrinsicHeight = dividerDrawable == null ? 0 : dividerDrawable.getIntrinsicHeight();
        for (int i13 = 0; i13 < childCount; i13++) {
            android.view.View childAt = getChildAt(i13);
            if (childAt != null && childAt.getVisibility() != 8) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight2 = childAt.getMeasuredHeight();
                android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) childAt.getLayoutParams();
                int i14 = layoutParams.gravity;
                if (i14 < 0) {
                    i14 = i12;
                }
                switch (android.view.Gravity.getAbsoluteGravity(i14, getLayoutDirection()) & 7) {
                    case 1:
                        i6 = ((((i10 - measuredWidth) / 2) + i7) + layoutParams.leftMargin) - layoutParams.rightMargin;
                        break;
                    case 5:
                        i6 = (i9 - measuredWidth) - layoutParams.rightMargin;
                        break;
                    default:
                        i6 = layoutParams.leftMargin + i7;
                        break;
                }
                if (hasDividerBeforeChildAt(i13)) {
                    i5 += intrinsicHeight;
                }
                int i15 = i5 + layoutParams.topMargin;
                setChildFrame(childAt, i6, i15, measuredWidth, measuredHeight2);
                i5 = i15 + measuredHeight2 + layoutParams.bottomMargin;
            }
        }
    }

    private void setChildFrame(android.view.View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i3 + i, i4 + i2);
    }
}
