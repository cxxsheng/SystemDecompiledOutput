package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class RemeasuringLinearLayout extends com.android.internal.widget.NotificationOptimizedLinearLayout {
    private java.util.ArrayList<android.view.View> mMatchParentViews;

    public RemeasuringLinearLayout(android.content.Context context) {
        super(context);
        this.mMatchParentViews = new java.util.ArrayList<>();
    }

    public RemeasuringLinearLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMatchParentViews = new java.util.ArrayList<>();
    }

    public RemeasuringLinearLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMatchParentViews = new java.util.ArrayList<>();
    }

    public RemeasuringLinearLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMatchParentViews = new java.util.ArrayList<>();
    }

    @Override // com.android.internal.widget.NotificationOptimizedLinearLayout, android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int childCount = getChildCount();
        boolean z = getOrientation() == 1;
        boolean z2 = getLayoutParams().height == -2;
        int i3 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            android.view.View childAt = getChildAt(i4);
            if (childAt != null && childAt.getVisibility() != 8) {
                android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) childAt.getLayoutParams();
                if (!z2 || layoutParams.height != -1 || z) {
                    int measuredHeight = childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                    if (z) {
                        measuredHeight += i3;
                    }
                    i3 = java.lang.Math.max(i3, measuredHeight);
                } else {
                    this.mMatchParentViews.add(childAt);
                }
            }
        }
        if (this.mMatchParentViews.size() > 0) {
            int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
            java.util.Iterator<android.view.View> it = this.mMatchParentViews.iterator();
            while (it.hasNext()) {
                android.view.View next = it.next();
                next.measure(getChildMeasureSpec(i, getPaddingStart() + getPaddingEnd(), next.getLayoutParams().width), makeMeasureSpec);
            }
        }
        this.mMatchParentViews.clear();
        setMeasuredDimension(getMeasuredWidth(), i3);
    }
}
