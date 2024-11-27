package com.android.server.display.whitebalance;

/* loaded from: classes.dex */
public class DisplayWhiteBalanceFactory {
    private static final java.lang.String BRIGHTNESS_FILTER_TAG = "AmbientBrightnessFilter";
    private static final java.lang.String COLOR_TEMPERATURE_FILTER_TAG = "AmbientColorTemperatureFilter";

    private DisplayWhiteBalanceFactory() {
    }

    public static com.android.server.display.whitebalance.DisplayWhiteBalanceController create(android.os.Handler handler, android.hardware.SensorManager sensorManager, android.content.res.Resources resources) {
        com.android.server.display.whitebalance.AmbientSensor.AmbientBrightnessSensor createBrightnessSensor = createBrightnessSensor(handler, sensorManager, resources);
        com.android.server.display.utils.AmbientFilter createBrightnessFilter = com.android.server.display.utils.AmbientFilterFactory.createBrightnessFilter(BRIGHTNESS_FILTER_TAG, resources);
        com.android.server.display.whitebalance.AmbientSensor.AmbientColorTemperatureSensor createColorTemperatureSensor = createColorTemperatureSensor(handler, sensorManager, resources);
        com.android.server.display.whitebalance.DisplayWhiteBalanceController displayWhiteBalanceController = new com.android.server.display.whitebalance.DisplayWhiteBalanceController(createBrightnessSensor, createBrightnessFilter, createColorTemperatureSensor, com.android.server.display.utils.AmbientFilterFactory.createColorTemperatureFilter(COLOR_TEMPERATURE_FILTER_TAG, resources), createThrottler(resources), getFloatArray(resources, android.R.array.config_displayWhiteBalanceLowLightAmbientBiases), getFloatArray(resources, android.R.array.config_displayWhiteBalanceLowLightAmbientBiasesStrong), getFloatArray(resources, android.R.array.config_displayWhiteBalanceHighLightAmbientBrightnessesStrong), getFloatArray(resources, android.R.array.config_displayWhiteBalanceIncreaseThresholds), getFloat(resources, android.R.dimen.config_buttonCornerRadius), getFloat(resources, android.R.dimen.config_closeToSquareDisplayMaxAspectRatio), getFloatArray(resources, android.R.array.config_displayWhiteBalanceHighLightAmbientBiases), getFloatArray(resources, android.R.array.config_displayWhiteBalanceHighLightAmbientBiasesStrong), getFloatArray(resources, android.R.array.config_displayWhiteBalanceDisplayRangeMinimums), getFloatArray(resources, android.R.array.config_displayWhiteBalanceDisplaySteps), getFloat(resources, android.R.dimen.config_batterySaver_full_adjustBrightnessFactor), getFloat(resources, android.R.dimen.config_bottomDialogCornerRadius), getFloatArray(resources, android.R.array.config_displayShapeArray), getFloatArray(resources, android.R.array.config_displayWhiteBalanceBaseThresholds), getFloatArray(resources, android.R.array.config_displayWhiteBalanceLowLightAmbientBrightnesses), getFloatArray(resources, android.R.array.config_displayWhiteBalanceLowLightAmbientBrightnessesStrong), resources.getBoolean(android.R.bool.config_displayWhiteBalanceEnabledDefault));
        createBrightnessSensor.setCallbacks(displayWhiteBalanceController);
        createColorTemperatureSensor.setCallbacks(displayWhiteBalanceController);
        return displayWhiteBalanceController;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static com.android.server.display.whitebalance.AmbientSensor.AmbientBrightnessSensor createBrightnessSensor(android.os.Handler handler, android.hardware.SensorManager sensorManager, android.content.res.Resources resources) {
        return new com.android.server.display.whitebalance.AmbientSensor.AmbientBrightnessSensor(handler, sensorManager, resources.getInteger(android.R.integer.config_deskDockRotation));
    }

    @com.android.internal.annotations.VisibleForTesting
    public static com.android.server.display.whitebalance.AmbientSensor.AmbientColorTemperatureSensor createColorTemperatureSensor(android.os.Handler handler, android.hardware.SensorManager sensorManager, android.content.res.Resources resources) {
        return new com.android.server.display.whitebalance.AmbientSensor.AmbientColorTemperatureSensor(handler, sensorManager, resources.getString(android.R.string.config_deviceConfiguratorPackageName), resources.getInteger(android.R.integer.config_displayWhiteBalanceColorTemperatureDefault));
    }

    private static com.android.server.display.whitebalance.DisplayWhiteBalanceThrottler createThrottler(android.content.res.Resources resources) {
        return new com.android.server.display.whitebalance.DisplayWhiteBalanceThrottler(resources.getInteger(android.R.integer.config_displayWhiteBalanceColorTemperatureFilterHorizon), resources.getInteger(android.R.integer.config_displayWhiteBalanceColorTemperatureMin), getFloatArray(resources, android.R.array.config_displayUniqueIdArray), getFloatArray(resources, android.R.array.config_displayWhiteBalanceHighLightAmbientBrightnesses), getFloatArray(resources, android.R.array.config_displayWhiteBalanceAmbientColorTemperatures));
    }

    private static float getFloat(android.content.res.Resources resources, int i) {
        android.util.TypedValue typedValue = new android.util.TypedValue();
        resources.getValue(i, typedValue, true);
        if (typedValue.type != 4) {
            return Float.NaN;
        }
        return typedValue.getFloat();
    }

    private static float[] getFloatArray(android.content.res.Resources resources, int i) {
        android.content.res.TypedArray obtainTypedArray = resources.obtainTypedArray(i);
        try {
            if (obtainTypedArray.length() == 0) {
                return null;
            }
            int length = obtainTypedArray.length();
            float[] fArr = new float[length];
            for (int i2 = 0; i2 < length; i2++) {
                fArr[i2] = obtainTypedArray.getFloat(i2, Float.NaN);
                if (java.lang.Float.isNaN(fArr[i2])) {
                    return null;
                }
            }
            return fArr;
        } finally {
            obtainTypedArray.recycle();
        }
    }
}
