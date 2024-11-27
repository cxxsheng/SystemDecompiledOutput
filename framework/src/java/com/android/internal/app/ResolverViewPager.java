package com.android.internal.app;

/* loaded from: classes4.dex */
public class ResolverViewPager extends com.android.internal.widget.ViewPager {
    private boolean mSwipingEnabled;

    public ResolverViewPager(android.content.Context context) {
        super(context);
        this.mSwipingEnabled = true;
    }

    public ResolverViewPager(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSwipingEnabled = true;
    }

    public ResolverViewPager(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSwipingEnabled = true;
    }

    public ResolverViewPager(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSwipingEnabled = true;
    }

    @Override // com.android.internal.widget.ViewPager, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (android.view.View.MeasureSpec.getMode(i2) != Integer.MIN_VALUE) {
            return;
        }
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        int measuredHeight = getMeasuredHeight();
        int i3 = 0;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            android.view.View childAt = getChildAt(i4);
            childAt.measure(makeMeasureSpec, android.view.View.MeasureSpec.makeMeasureSpec(measuredHeight, Integer.MIN_VALUE));
            if (i3 < childAt.getMeasuredHeight()) {
                i3 = childAt.getMeasuredHeight();
            }
        }
        if (i3 > 0) {
            measuredHeight = i3;
        }
        super.onMeasure(makeMeasureSpec, android.view.View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
    }

    void setSwipingEnabled(boolean z) {
        this.mSwipingEnabled = z;
    }

    @Override // com.android.internal.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        return !isLayoutRtl() && this.mSwipingEnabled && super.onInterceptTouchEvent(motionEvent);
    }
}
