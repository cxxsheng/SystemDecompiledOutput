package com.android.internal.widget;

/* loaded from: classes5.dex */
public class WeightedLinearLayout extends android.widget.LinearLayout {
    private float mMajorWeightMax;
    private float mMajorWeightMin;
    private float mMinorWeightMax;
    private float mMinorWeightMin;

    public WeightedLinearLayout(android.content.Context context) {
        super(context);
    }

    public WeightedLinearLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.WeightedLinearLayout);
        this.mMajorWeightMin = obtainStyledAttributes.getFloat(1, 0.0f);
        this.mMinorWeightMin = obtainStyledAttributes.getFloat(3, 0.0f);
        this.mMajorWeightMax = obtainStyledAttributes.getFloat(0, 0.0f);
        this.mMinorWeightMax = obtainStyledAttributes.getFloat(2, 0.0f);
        obtainStyledAttributes.recycle();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        android.util.DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int i3 = displayMetrics.widthPixels;
        boolean z = false;
        boolean z2 = i3 < displayMetrics.heightPixels;
        int mode = android.view.View.MeasureSpec.getMode(i);
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824);
        float f = z2 ? this.mMinorWeightMin : this.mMajorWeightMin;
        float f2 = z2 ? this.mMinorWeightMax : this.mMajorWeightMax;
        if (mode == Integer.MIN_VALUE) {
            int i4 = (int) (i3 * f);
            if (f > 0.0f && measuredWidth < i4) {
                makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i4, 1073741824);
                z = true;
            } else if (f2 > 0.0f && measuredWidth > i4) {
                makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i4, 1073741824);
                z = true;
            }
        }
        if (z) {
            super.onMeasure(makeMeasureSpec, i2);
        }
    }
}
