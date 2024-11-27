package com.android.server.input.debug;

/* loaded from: classes2.dex */
public class RotaryInputValueView extends android.widget.TextView {
    private static final android.graphics.ColorFilter ACTIVE_BACKGROUND_FILTER = new android.graphics.ColorMatrixColorFilter(new float[]{com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 255.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 255.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 200.0f});
    private static final int ACTIVE_STATUS_DURATION = 250;
    private static final int ACTIVE_TEXT_COLOR = -12447960;
    private static final int INACTIVE_TEXT_COLOR = -65281;
    private static final int SIDE_PADDING_SP = 4;
    private static final int TEXT_SIZE_SP = 8;
    private final java.util.Locale mDefaultLocale;
    private final float mScaledVerticalScrollFactor;
    private final java.lang.Runnable mUpdateActivityStatusCallback;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        updateActivityStatus(false);
    }

    public RotaryInputValueView(android.content.Context context) {
        super(context);
        this.mUpdateActivityStatusCallback = new java.lang.Runnable() { // from class: com.android.server.input.debug.RotaryInputValueView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.input.debug.RotaryInputValueView.this.lambda$new$0();
            }
        };
        this.mDefaultLocale = java.util.Locale.getDefault();
        android.util.DisplayMetrics displayMetrics = ((android.widget.TextView) this).mContext.getResources().getDisplayMetrics();
        this.mScaledVerticalScrollFactor = android.view.ViewConfiguration.get(context).getScaledVerticalScrollFactor();
        setText(getFormattedValue(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE));
        setTextColor(INACTIVE_TEXT_COLOR);
        setTextSize(applyDimensionSp(8, displayMetrics));
        setPaddingRelative(applyDimensionSp(4, displayMetrics), 0, applyDimensionSp(4, displayMetrics), 0);
        setTypeface(null, 1);
        setBackgroundResource(android.R.drawable.focus_event_pressed_key_background);
    }

    public void updateValue(float f) {
        removeCallbacks(this.mUpdateActivityStatusCallback);
        setText(getFormattedValue(f * this.mScaledVerticalScrollFactor));
        updateActivityStatus(true);
        postDelayed(this.mUpdateActivityStatusCallback, 250L);
    }

    public void updateActivityStatus(boolean z) {
        if (z) {
            setTextColor(ACTIVE_TEXT_COLOR);
            getBackground().setColorFilter(ACTIVE_BACKGROUND_FILTER);
        } else {
            setTextColor(INACTIVE_TEXT_COLOR);
            getBackground().clearColorFilter();
        }
    }

    private java.lang.String getFormattedValue(float f) {
        return java.lang.String.format(this.mDefaultLocale, "%s%.1f", f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? "-" : "+", java.lang.Float.valueOf(java.lang.Math.abs(f)));
    }

    private static int applyDimensionSp(int i, android.util.DisplayMetrics displayMetrics) {
        return (int) android.util.TypedValue.applyDimension(2, i, displayMetrics);
    }
}
