package com.android.server.autofill.ui;

/* loaded from: classes.dex */
public class CustomScrollView extends android.widget.ScrollView {
    public static final java.lang.String DEVICE_CONFIG_SAVE_DIALOG_LANDSCAPE_BODY_HEIGHT_MAX_PERCENT = "autofill_save_dialog_landscape_body_height_max_percent";
    public static final java.lang.String DEVICE_CONFIG_SAVE_DIALOG_PORTRAIT_BODY_HEIGHT_MAX_PERCENT = "autofill_save_dialog_portrait_body_height_max_percent";
    private static final java.lang.String TAG = "CustomScrollView";
    private int mAttrBasedMaxHeightPercent;
    private int mHeight;
    private int mMaxLandscapeBodyHeightPercent;
    private int mMaxPortraitBodyHeightPercent;
    private int mWidth;

    public CustomScrollView(android.content.Context context) {
        super(context);
        this.mWidth = -1;
        this.mHeight = -1;
        setMaxBodyHeightPercent(context);
    }

    public CustomScrollView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWidth = -1;
        this.mHeight = -1;
        setMaxBodyHeightPercent(context);
    }

    public CustomScrollView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mWidth = -1;
        this.mHeight = -1;
        setMaxBodyHeightPercent(context);
    }

    public CustomScrollView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mWidth = -1;
        this.mHeight = -1;
        setMaxBodyHeightPercent(context);
    }

    private void setMaxBodyHeightPercent(android.content.Context context) {
        this.mAttrBasedMaxHeightPercent = getAttrBasedMaxHeightPercent(context);
        this.mMaxPortraitBodyHeightPercent = android.provider.DeviceConfig.getInt("autofill", DEVICE_CONFIG_SAVE_DIALOG_PORTRAIT_BODY_HEIGHT_MAX_PERCENT, this.mAttrBasedMaxHeightPercent);
        this.mMaxLandscapeBodyHeightPercent = android.provider.DeviceConfig.getInt("autofill", DEVICE_CONFIG_SAVE_DIALOG_LANDSCAPE_BODY_HEIGHT_MAX_PERCENT, this.mAttrBasedMaxHeightPercent);
    }

    @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (getChildCount() == 0) {
            android.util.Slog.e(TAG, "no children");
            return;
        }
        this.mWidth = android.view.View.MeasureSpec.getSize(i);
        calculateDimensions();
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    private void calculateDimensions() {
        int i;
        if (this.mHeight != -1) {
            return;
        }
        android.graphics.Point point = new android.graphics.Point();
        getContext().getDisplayNoVerify().getSize(point);
        int measuredHeight = getChildAt(0).getMeasuredHeight();
        int i2 = point.y;
        if (getResources().getConfiguration().orientation == 2) {
            i = (this.mMaxLandscapeBodyHeightPercent * i2) / 100;
        } else {
            i = (this.mMaxPortraitBodyHeightPercent * i2) / 100;
        }
        this.mHeight = java.lang.Math.min(measuredHeight, i);
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "calculateDimensions(): mMaxPortraitBodyHeightPercent=" + this.mMaxPortraitBodyHeightPercent + ", mMaxLandscapeBodyHeightPercent=" + this.mMaxLandscapeBodyHeightPercent + ", mAttrBasedMaxHeightPercent=" + this.mAttrBasedMaxHeightPercent + ", maxHeight=" + i + ", contentHeight=" + measuredHeight + ", w=" + this.mWidth + ", h=" + this.mHeight);
        }
    }

    private int getAttrBasedMaxHeightPercent(android.content.Context context) {
        android.util.TypedValue typedValue = new android.util.TypedValue();
        context.getTheme().resolveAttribute(android.R.^attr-private.autofillSaveCustomSubtitleMaxHeight, typedValue, true);
        return (int) typedValue.getFraction(100.0f, 100.0f);
    }
}
