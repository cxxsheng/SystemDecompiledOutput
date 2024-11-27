package com.android.server.display.utils;

/* loaded from: classes.dex */
public class AmbientFilterFactory {
    private AmbientFilterFactory() {
    }

    public static com.android.server.display.utils.AmbientFilter createAmbientFilter(java.lang.String str, int i, float f) {
        if (!java.lang.Float.isNaN(f)) {
            return new com.android.server.display.utils.AmbientFilter.WeightedMovingAverageAmbientFilter(str, i, f);
        }
        throw new java.lang.IllegalArgumentException("missing configurations: expected config_displayWhiteBalanceBrightnessFilterIntercept");
    }

    public static com.android.server.display.utils.AmbientFilter createBrightnessFilter(java.lang.String str, android.content.res.Resources resources) {
        return createAmbientFilter(str, resources.getInteger(android.R.integer.config_deskDockKeepsScreenOn), getFloat(resources, android.R.dimen.config_autoKeyboardBrightnessSmoothingConstant));
    }

    public static com.android.server.display.utils.AmbientFilter createColorTemperatureFilter(java.lang.String str, android.content.res.Resources resources) {
        return createAmbientFilter(str, resources.getInteger(android.R.integer.config_deviceStateRearDisplay), getFloat(resources, android.R.dimen.config_backGestureInset));
    }

    private static float getFloat(android.content.res.Resources resources, int i) {
        android.util.TypedValue typedValue = new android.util.TypedValue();
        resources.getValue(i, typedValue, true);
        if (typedValue.type != 4) {
            return Float.NaN;
        }
        return typedValue.getFloat();
    }
}
