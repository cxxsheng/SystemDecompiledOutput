package com.android.server.autofill.ui;

/* loaded from: classes.dex */
public class BottomSheetLayout extends android.widget.LinearLayout {
    private static final java.lang.String TAG = "BottomSheetLayout";

    public BottomSheetLayout(android.content.Context context) {
        super(context);
    }

    public BottomSheetLayout(android.content.Context context, @android.annotation.Nullable android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BottomSheetLayout(android.content.Context context, @android.annotation.Nullable android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        if (getContext() == null || getContext().getResources() == null) {
            super.onMeasure(i, i2);
            android.util.Slog.w(TAG, "onMeasure failed due to missing context or missing resources.");
            return;
        }
        if (getChildCount() == 0) {
            super.onMeasure(i, i2);
            android.util.Slog.wtf(TAG, "onMeasure failed due to missing children views.");
            return;
        }
        android.util.DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(android.R.dimen.autofill_button_bar_spacer_height);
        int dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(android.R.dimen.autofill_dialog_max_width);
        boolean z = getContext().getResources().getBoolean(android.R.bool.autofill_dialog_horizontal_space_included);
        int i4 = displayMetrics.heightPixels;
        int i5 = displayMetrics.widthPixels;
        int i6 = (i4 - dimensionPixelSize) - dimensionPixelSize2;
        if (!z) {
            i3 = i5;
        } else {
            i3 = i5 - (dimensionPixelSize * 2);
        }
        int min = java.lang.Math.min(i3, getContext().getResources().getDimensionPixelSize(android.R.dimen.app_header_height));
        super.onMeasure(android.view.View.MeasureSpec.makeMeasureSpec(min, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(i6, Integer.MIN_VALUE));
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "onMeasure() values in dp: screenHeight: " + (i4 / displayMetrics.density) + ", screenWidth: " + (i5 / displayMetrics.density) + ", maxHeight: " + (i6 / displayMetrics.density) + ", maxWidth: " + (min / displayMetrics.density) + ", getMeasuredWidth(): " + (getMeasuredWidth() / displayMetrics.density) + ", getMeasuredHeight(): " + (getMeasuredHeight() / displayMetrics.density));
        }
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }
}
