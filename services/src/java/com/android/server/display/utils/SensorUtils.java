package com.android.server.display.utils;

/* loaded from: classes.dex */
public class SensorUtils {
    public static final int NO_FALLBACK = 0;

    @android.annotation.Nullable
    public static android.hardware.Sensor findSensor(@android.annotation.Nullable android.hardware.SensorManager sensorManager, @android.annotation.Nullable com.android.server.display.config.SensorData sensorData, int i) {
        if (sensorData == null) {
            return null;
        }
        return findSensor(sensorManager, sensorData.type, sensorData.name, i);
    }

    @android.annotation.Nullable
    public static android.hardware.Sensor findSensor(@android.annotation.Nullable android.hardware.SensorManager sensorManager, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i) {
        if (sensorManager == null) {
            return null;
        }
        boolean z = !android.text.TextUtils.isEmpty(str2);
        boolean z2 = !android.text.TextUtils.isEmpty(str);
        if (z || z2) {
            for (android.hardware.Sensor sensor : sensorManager.getSensorList(-1)) {
                if (!z || str2.equals(sensor.getName())) {
                    if (!z2 || str.equals(sensor.getStringType())) {
                        return sensor;
                    }
                }
            }
        }
        if (i == 0) {
            return null;
        }
        return sensorManager.getDefaultSensor(i);
    }

    public static int getSensorTemperatureType(@android.annotation.NonNull com.android.server.display.config.SensorData sensorData) {
        if (sensorData.type.equalsIgnoreCase(com.android.server.display.config.SensorData.TEMPERATURE_TYPE_DISPLAY)) {
            return 11;
        }
        if (sensorData.type.equalsIgnoreCase(com.android.server.display.config.SensorData.TEMPERATURE_TYPE_SKIN)) {
            return 3;
        }
        throw new java.lang.IllegalArgumentException("tempSensor doesn't support type: " + sensorData.type);
    }
}
