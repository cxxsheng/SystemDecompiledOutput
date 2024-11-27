package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class NotificationOptimizedLinearLayout extends android.widget.LinearLayout {
    private static final boolean DEBUG_LAYOUT = false;
    private static final java.lang.String TAG = "NotifOptimizedLinearLayout";
    private static final boolean TRACE_ONMEASURE = android.os.Build.isDebuggable();
    private boolean mShouldUseOptimizedLayout;

    public NotificationOptimizedLinearLayout(android.content.Context context) {
        super(context);
        this.mShouldUseOptimizedLayout = false;
    }

    public NotificationOptimizedLinearLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mShouldUseOptimizedLayout = false;
    }

    public NotificationOptimizedLinearLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShouldUseOptimizedLayout = false;
    }

    public NotificationOptimizedLinearLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mShouldUseOptimizedLayout = false;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        android.view.View singleWeightedChild = getSingleWeightedChild();
        this.mShouldUseOptimizedLayout = isUseOptimizedLinearLayoutFlagEnabled() && singleWeightedChild != null && isOptimizationPossible(i, i2);
        if (this.mShouldUseOptimizedLayout) {
            onMeasureOptimized(singleWeightedChild, i, i2);
        } else {
            super.onMeasure(i, i2);
        }
    }

    private boolean isUseOptimizedLinearLayoutFlagEnabled() {
        boolean notifLinearlayoutOptimized = android.widget.flags.Flags.notifLinearlayoutOptimized();
        if (!notifLinearlayoutOptimized) {
            logSkipOptimizedOnMeasure("enableNotifLinearlayoutOptimized flag is off.");
        }
        return notifLinearlayoutOptimized;
    }

    private boolean isOptimizationPossible(int i, int i2) {
        if (getWeightSum() > 0.0f) {
            logSkipOptimizedOnMeasure("Has weightSum.");
            return false;
        }
        if (requiresMatchParentRemeasureForVerticalLinearLayout(i)) {
            logSkipOptimizedOnMeasure("Vertical LinearLayout requires children width MATCH_PARENT remeasure ");
            return false;
        }
        if ((getOrientation() == 0) && android.view.View.MeasureSpec.getMode(i) != 1073741824) {
            logSkipOptimizedOnMeasure("Horizontal LinearLayout's width should be measured EXACTLY");
            return false;
        }
        if (requiresBaselineAlignmentForHorizontalLinearLayout()) {
            logSkipOptimizedOnMeasure("Need to apply baseline.");
            return false;
        }
        if (!requiresNegativeMarginHandlingForHorizontalLinearLayout()) {
            return true;
        }
        logSkipOptimizedOnMeasure("Need to handle negative margins.");
        return false;
    }

    private boolean requiresNegativeMarginHandlingForHorizontalLinearLayout() {
        if (getOrientation() == 1) {
            return false;
        }
        java.util.List<android.view.View> activeChildren = getActiveChildren();
        for (int i = 0; i < activeChildren.size(); i++) {
            android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) activeChildren.get(i).getLayoutParams();
            if (marginLayoutParams.leftMargin < 0 || marginLayoutParams.rightMargin < 0) {
                return true;
            }
        }
        return false;
    }

    private boolean requiresMatchParentRemeasureForVerticalLinearLayout(int i) {
        if (getOrientation() == 0) {
            return false;
        }
        boolean z = android.view.View.MeasureSpec.getMode(i) != 1073741824;
        java.util.List<android.view.View> activeChildren = getActiveChildren();
        for (int i2 = 0; i2 < activeChildren.size(); i2++) {
            android.view.ViewGroup.LayoutParams layoutParams = activeChildren.get(i2).getLayoutParams();
            if (z && layoutParams.width == -1) {
                return true;
            }
        }
        return false;
    }

    private boolean requiresBaselineAlignmentForHorizontalLinearLayout() {
        int i;
        if (getOrientation() == 1 || !isBaselineAligned()) {
            return false;
        }
        java.util.List<android.view.View> activeChildren = getActiveChildren();
        int gravity = getGravity() & 112;
        for (int i2 = 0; i2 < activeChildren.size(); i2++) {
            android.view.View view = activeChildren.get(i2);
            if (view.getLayoutParams() instanceof android.widget.LinearLayout.LayoutParams) {
                android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) view.getLayoutParams();
                if (layoutParams.height == -1) {
                    i = -1;
                } else {
                    i = view.getBaseline();
                }
                if (i != -1) {
                    int i3 = layoutParams.gravity;
                    if (i3 < 0) {
                        i3 = gravity;
                    }
                    int i4 = i3 & 112;
                    if (i4 == 48 || i4 == 80) {
                        return true;
                    }
                } else {
                    continue;
                }
            }
        }
        return false;
    }

    private android.view.View getSingleWeightedChild() {
        boolean z = getOrientation() == 1;
        java.util.List<android.view.View> activeChildren = getActiveChildren();
        android.view.View view = null;
        for (int i = 0; i < activeChildren.size(); i++) {
            android.view.View view2 = activeChildren.get(i);
            if (view2.getLayoutParams() instanceof android.widget.LinearLayout.LayoutParams) {
                android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) view2.getLayoutParams();
                if ((!z && layoutParams.width == -1) || (z && layoutParams.height == -1)) {
                    logSkipOptimizedOnMeasure("There is a match parent child in the related orientation.");
                    return null;
                }
                if (layoutParams.weight == 0.0f) {
                    continue;
                } else if (view == null) {
                    view = view2;
                } else {
                    logSkipOptimizedOnMeasure("There is more than one weighted child.");
                    return null;
                }
            }
        }
        if (view == null) {
            logSkipOptimizedOnMeasure("There is no weighted child in this layout.");
        } else {
            android.widget.LinearLayout.LayoutParams layoutParams2 = (android.widget.LinearLayout.LayoutParams) view.getLayoutParams();
            boolean z2 = layoutParams2.height == -2 || layoutParams2.height == 0;
            boolean z3 = layoutParams2.width == -2 || layoutParams2.width == 0;
            if ((z && !z2) || (!z && !z3)) {
                logSkipOptimizedOnMeasure("Single weighted child should be either WRAP_CONTENT or 0 in the related orientation");
                return null;
            }
        }
        return view;
    }

    private void onMeasureOptimized(android.view.View view, int i, int i2) {
        try {
            if (TRACE_ONMEASURE) {
                android.os.Trace.beginSection("NotifOptimizedLinearLayout#onMeasure");
            }
            if (getOrientation() == 0) {
                android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                int i3 = layoutParams.width;
                boolean isBaselineAligned = isBaselineAligned();
                layoutParams.width = 0;
                setBaselineAligned(false);
                super.onMeasure(i, i2);
                layoutParams.width = i3;
                setBaselineAligned(isBaselineAligned);
            } else {
                measureVerticalOptimized(view, i, i2);
            }
        } finally {
            if (TRACE_ONMEASURE) {
                trackShouldUseOptimizedLayout();
                android.os.Trace.endSection();
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mShouldUseOptimizedLayout) {
            onLayoutOptimized(z, i, i2, i3, i4);
        } else {
            super.onLayout(z, i, i2, i3, i4);
        }
    }

    private void onLayoutOptimized(boolean z, int i, int i2, int i3, int i4) {
        if (getOrientation() == 0) {
            super.onLayout(z, i, i2, i3, i4);
        } else {
            layoutVerticalOptimized(i, i2, i3, i4);
        }
    }

    private void measureVerticalOptimized(android.view.View view, int i, int i2) {
        int makeMeasureSpec;
        int size = android.view.View.MeasureSpec.getSize(i2);
        int mode = android.view.View.MeasureSpec.getMode(i2);
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            android.view.View childAt = getChildAt(i5);
            if (childAt != null && childAt.getVisibility() != 8) {
                android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                if (childAt == view) {
                    if (marginLayoutParams.height == 0 && mode == 1073741824) {
                        i3 = java.lang.Math.max(i3, marginLayoutParams.topMargin + i3 + marginLayoutParams.bottomMargin);
                    }
                } else {
                    measureChildWithMargins(childAt, i, 0, i2, 0);
                    i3 = java.lang.Math.max(i3, childAt.getMeasuredHeight() + i3 + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin);
                    i4 = java.lang.Math.max(i4, childAt.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
                }
            }
        }
        int i6 = i3 + this.mPaddingTop + this.mPaddingBottom;
        android.view.ViewGroup.MarginLayoutParams marginLayoutParams2 = (android.view.ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i7 = mode == 1073741824 ? 1073741824 : Integer.MIN_VALUE;
        if (marginLayoutParams2.height == 0 && mode == 1073741824) {
            makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, size - i6), i7);
        } else {
            makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, size - ((marginLayoutParams2.topMargin + marginLayoutParams2.bottomMargin) + i6)), i7);
        }
        view.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight + marginLayoutParams2.leftMargin + marginLayoutParams2.rightMargin, marginLayoutParams2.width), makeMeasureSpec);
        setMeasuredDimension(resolveSizeAndState(java.lang.Math.max(java.lang.Math.max(i4, view.getMeasuredWidth() + marginLayoutParams2.leftMargin + marginLayoutParams2.rightMargin) + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, 0), resolveSizeAndState(java.lang.Math.max(java.lang.Math.max(i6, view.getMeasuredHeight() + i6 + marginLayoutParams2.topMargin + marginLayoutParams2.bottomMargin), getSuggestedMinimumHeight()), i2, 0));
    }

    private java.util.List<android.view.View> getActiveChildren() {
        int childCount = getChildCount();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt != null && childAt.getVisibility() != 8) {
                arrayList.add(childAt);
            }
        }
        return arrayList;
    }

    private void layoutVerticalOptimized(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = this.mPaddingLeft;
        int measuredHeight = getMeasuredHeight();
        int i8 = i3 - i;
        int i9 = i8 - this.mPaddingRight;
        int i10 = (i8 - i7) - this.mPaddingRight;
        int childCount = getChildCount();
        int gravity = getGravity() & 112;
        int gravity2 = getGravity() & android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        switch (gravity) {
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
        int dividerHeight = getDividerHeight();
        for (int i11 = 0; i11 < childCount; i11++) {
            android.view.View childAt = getChildAt(i11);
            if (childAt != null && childAt.getVisibility() != 8) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight2 = childAt.getMeasuredHeight();
                android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) childAt.getLayoutParams();
                int i12 = layoutParams.gravity;
                if (i12 < 0) {
                    i12 = gravity2;
                }
                switch (android.view.Gravity.getAbsoluteGravity(i12, getLayoutDirection()) & 7) {
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
                if (hasDividerBeforeChildAt(i11)) {
                    i5 += dividerHeight;
                }
                int i13 = i5 + layoutParams.topMargin;
                childAt.layout(i6, i13, measuredWidth + i6, i13 + measuredHeight2);
                i5 = i13 + measuredHeight2 + layoutParams.bottomMargin;
            }
        }
    }

    private int getDividerHeight() {
        android.graphics.drawable.Drawable dividerDrawable = getDividerDrawable();
        if (dividerDrawable == null) {
            return 0;
        }
        return dividerDrawable.getIntrinsicHeight();
    }

    private void trackShouldUseOptimizedLayout() {
        if (TRACE_ONMEASURE) {
            android.os.Trace.setCounter("NotifOptimizedLinearLayout#shouldUseOptimizedLayout", this.mShouldUseOptimizedLayout ? 1L : 0L);
        }
    }

    private void logSkipOptimizedOnMeasure(java.lang.String str) {
    }
}
