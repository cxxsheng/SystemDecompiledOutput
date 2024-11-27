package com.android.server.display.config;

/* loaded from: classes.dex */
public class SensorData {
    public static final java.lang.String TEMPERATURE_TYPE_DISPLAY = "DISPLAY";
    public static final java.lang.String TEMPERATURE_TYPE_SKIN = "SKIN";
    public final float maxRefreshRate;
    public final float minRefreshRate;

    @android.annotation.Nullable
    public final java.lang.String name;
    public final java.util.List<com.android.server.display.config.SensorData.SupportedMode> supportedModes;

    @android.annotation.Nullable
    public final java.lang.String type;

    @com.android.internal.annotations.VisibleForTesting
    public SensorData() {
        this(null, null);
    }

    @com.android.internal.annotations.VisibleForTesting
    public SensorData(java.lang.String str, java.lang.String str2) {
        this(str, str2, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, Float.POSITIVE_INFINITY);
    }

    @com.android.internal.annotations.VisibleForTesting
    public SensorData(java.lang.String str, java.lang.String str2, float f, float f2) {
        this(str, str2, f, f2, java.util.List.of());
    }

    @com.android.internal.annotations.VisibleForTesting
    public SensorData(java.lang.String str, java.lang.String str2, float f, float f2, java.util.List<com.android.server.display.config.SensorData.SupportedMode> list) {
        this.type = str;
        this.name = str2;
        this.minRefreshRate = f;
        this.maxRefreshRate = f2;
        this.supportedModes = java.util.Collections.unmodifiableList(list);
    }

    public boolean matches(java.lang.String str, java.lang.String str2) {
        boolean z = !android.text.TextUtils.isEmpty(str);
        boolean z2 = !android.text.TextUtils.isEmpty(str2);
        return (z || z2) && (!z || str.equals(this.name)) && (!z2 || str2.equals(this.type));
    }

    public java.lang.String toString() {
        return "SensorData{type= " + this.type + ", name= " + this.name + ", refreshRateRange: [" + this.minRefreshRate + ", " + this.maxRefreshRate + "], supportedModes=" + this.supportedModes + '}';
    }

    public static com.android.server.display.config.SensorData loadAmbientLightSensorConfig(com.android.server.display.config.DisplayConfiguration displayConfiguration, android.content.res.Resources resources) {
        com.android.server.display.config.SensorDetails lightSensor = displayConfiguration.getLightSensor();
        if (lightSensor != null) {
            return loadSensorData(lightSensor);
        }
        return loadAmbientLightSensorConfig(resources);
    }

    public static com.android.server.display.config.SensorData loadAmbientLightSensorConfig(android.content.res.Resources resources) {
        return new com.android.server.display.config.SensorData(resources.getString(android.R.string.config_default_dns_server), "");
    }

    public static com.android.server.display.config.SensorData loadScreenOffBrightnessSensorConfig(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.SensorDetails screenOffBrightnessSensor = displayConfiguration.getScreenOffBrightnessSensor();
        if (screenOffBrightnessSensor != null) {
            return loadSensorData(screenOffBrightnessSensor);
        }
        return new com.android.server.display.config.SensorData();
    }

    @android.annotation.Nullable
    public static com.android.server.display.config.SensorData loadProxSensorConfig(com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.SensorDetails proxSensor = displayConfiguration.getProxSensor();
        if (proxSensor != null) {
            java.lang.String name = proxSensor.getName();
            java.lang.String type = proxSensor.getType();
            if ("".equals(name) && "".equals(type)) {
                return null;
            }
            return loadSensorData(proxSensor);
        }
        return new com.android.server.display.config.SensorData();
    }

    public static com.android.server.display.config.SensorData loadTempSensorUnspecifiedConfig() {
        return new com.android.server.display.config.SensorData(TEMPERATURE_TYPE_SKIN, null);
    }

    public static com.android.server.display.config.SensorData loadTempSensorConfig(com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, com.android.server.display.config.DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.SensorDetails tempSensor = displayConfiguration.getTempSensor();
        boolean isSensorBasedBrightnessThrottlingEnabled = displayManagerFlags.isSensorBasedBrightnessThrottlingEnabled();
        java.lang.String str = null;
        java.lang.String str2 = TEMPERATURE_TYPE_SKIN;
        if (!isSensorBasedBrightnessThrottlingEnabled || tempSensor == null) {
            return new com.android.server.display.config.SensorData(TEMPERATURE_TYPE_SKIN, null);
        }
        java.lang.String name = tempSensor.getName();
        java.lang.String type = tempSensor.getType();
        if (!android.text.TextUtils.isEmpty(type) && !android.text.TextUtils.isEmpty(name)) {
            str = name;
            str2 = type;
        }
        return new com.android.server.display.config.SensorData(str2, str);
    }

    @android.annotation.NonNull
    public static com.android.server.display.config.SensorData loadSensorUnspecifiedConfig() {
        return new com.android.server.display.config.SensorData();
    }

    private static com.android.server.display.config.SensorData loadSensorData(@android.annotation.NonNull com.android.server.display.config.SensorDetails sensorDetails) {
        float f;
        float f2;
        com.android.server.display.config.RefreshRateRange refreshRate = sensorDetails.getRefreshRate();
        if (refreshRate == null) {
            f = Float.POSITIVE_INFINITY;
            f2 = 0.0f;
        } else {
            float floatValue = refreshRate.getMinimum().floatValue();
            f = refreshRate.getMaximum().floatValue();
            f2 = floatValue;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.display.config.NonNegativeFloatToFloatMap supportedModes = sensorDetails.getSupportedModes();
        if (supportedModes != null) {
            for (com.android.server.display.config.NonNegativeFloatToFloatPoint nonNegativeFloatToFloatPoint : supportedModes.getPoint()) {
                arrayList.add(new com.android.server.display.config.SensorData.SupportedMode(nonNegativeFloatToFloatPoint.getFirst().floatValue(), nonNegativeFloatToFloatPoint.getSecond().floatValue()));
            }
        }
        return new com.android.server.display.config.SensorData(sensorDetails.getType(), sensorDetails.getName(), f2, f, arrayList);
    }

    public static class SupportedMode {
        public final float refreshRate;
        public final float vsyncRate;

        public SupportedMode(float f, float f2) {
            this.refreshRate = f;
            this.vsyncRate = f2;
        }
    }
}
