package android.hardware;

/* loaded from: classes.dex */
public abstract class SensorManager {
    public static final int AXIS_MINUS_X = 129;
    public static final int AXIS_MINUS_Y = 130;
    public static final int AXIS_MINUS_Z = 131;
    public static final int AXIS_X = 1;
    public static final int AXIS_Y = 2;
    public static final int AXIS_Z = 3;
    public static final int DATA_INJECTION = 1;

    @java.lang.Deprecated
    public static final int DATA_X = 0;

    @java.lang.Deprecated
    public static final int DATA_Y = 1;

    @java.lang.Deprecated
    public static final int DATA_Z = 2;
    public static final float GRAVITY_DEATH_STAR_I = 3.5303614E-7f;
    public static final float GRAVITY_EARTH = 9.80665f;
    public static final float GRAVITY_JUPITER = 23.12f;
    public static final float GRAVITY_MARS = 3.71f;
    public static final float GRAVITY_MERCURY = 3.7f;
    public static final float GRAVITY_MOON = 1.6f;
    public static final float GRAVITY_NEPTUNE = 11.0f;
    public static final float GRAVITY_PLUTO = 0.6f;
    public static final float GRAVITY_SATURN = 8.96f;
    public static final float GRAVITY_SUN = 275.0f;
    public static final float GRAVITY_THE_ISLAND = 4.815162f;
    public static final float GRAVITY_URANUS = 8.69f;
    public static final float GRAVITY_VENUS = 8.87f;
    public static final int HAL_BYPASS_REPLAY_DATA_INJECTION = 4;
    public static final float LIGHT_CLOUDY = 100.0f;
    public static final float LIGHT_FULLMOON = 0.25f;
    public static final float LIGHT_NO_MOON = 0.001f;
    public static final float LIGHT_OVERCAST = 10000.0f;
    public static final float LIGHT_SHADE = 20000.0f;
    public static final float LIGHT_SUNLIGHT = 110000.0f;
    public static final float LIGHT_SUNLIGHT_MAX = 120000.0f;
    public static final float LIGHT_SUNRISE = 400.0f;
    public static final float MAGNETIC_FIELD_EARTH_MAX = 60.0f;
    public static final float MAGNETIC_FIELD_EARTH_MIN = 30.0f;
    public static final float PRESSURE_STANDARD_ATMOSPHERE = 1013.25f;

    @java.lang.Deprecated
    public static final int RAW_DATA_INDEX = 3;

    @java.lang.Deprecated
    public static final int RAW_DATA_X = 3;

    @java.lang.Deprecated
    public static final int RAW_DATA_Y = 4;

    @java.lang.Deprecated
    public static final int RAW_DATA_Z = 5;
    public static final int REPLAY_DATA_INJECTION = 3;

    @java.lang.Deprecated
    public static final int SENSOR_ACCELEROMETER = 2;

    @java.lang.Deprecated
    public static final int SENSOR_ALL = 127;
    public static final int SENSOR_DELAY_FASTEST = 0;
    public static final int SENSOR_DELAY_GAME = 1;
    public static final int SENSOR_DELAY_NORMAL = 3;
    public static final int SENSOR_DELAY_UI = 2;

    @java.lang.Deprecated
    public static final int SENSOR_LIGHT = 16;

    @java.lang.Deprecated
    public static final int SENSOR_MAGNETIC_FIELD = 8;

    @java.lang.Deprecated
    public static final int SENSOR_MAX = 64;

    @java.lang.Deprecated
    public static final int SENSOR_MIN = 1;

    @java.lang.Deprecated
    public static final int SENSOR_ORIENTATION = 1;

    @java.lang.Deprecated
    public static final int SENSOR_ORIENTATION_RAW = 128;

    @java.lang.Deprecated
    public static final int SENSOR_PROXIMITY = 32;
    public static final int SENSOR_STATUS_ACCURACY_HIGH = 3;
    public static final int SENSOR_STATUS_ACCURACY_LOW = 1;
    public static final int SENSOR_STATUS_ACCURACY_MEDIUM = 2;
    public static final int SENSOR_STATUS_NO_CONTACT = -1;
    public static final int SENSOR_STATUS_UNRELIABLE = 0;

    @java.lang.Deprecated
    public static final int SENSOR_TEMPERATURE = 4;

    @java.lang.Deprecated
    public static final int SENSOR_TRICORDER = 64;
    public static final float STANDARD_GRAVITY = 9.80665f;
    protected static final java.lang.String TAG = "SensorManager";
    private static final float[] sTempMatrix = new float[16];
    private android.hardware.LegacySensorManager mLegacySensorManager;
    private final android.util.SparseArray<java.util.List<android.hardware.Sensor>> mSensorListByType = new android.util.SparseArray<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DataInjectionMode {
    }

    protected abstract boolean cancelTriggerSensorImpl(android.hardware.TriggerEventListener triggerEventListener, android.hardware.Sensor sensor, boolean z);

    protected abstract int configureDirectChannelImpl(android.hardware.SensorDirectChannel sensorDirectChannel, android.hardware.Sensor sensor, int i);

    protected abstract android.hardware.SensorDirectChannel createDirectChannelImpl(android.os.MemoryFile memoryFile, android.hardware.HardwareBuffer hardwareBuffer);

    protected abstract void destroyDirectChannelImpl(android.hardware.SensorDirectChannel sensorDirectChannel);

    protected abstract boolean flushImpl(android.hardware.SensorEventListener sensorEventListener);

    protected abstract java.util.List<android.hardware.Sensor> getFullDynamicSensorList();

    protected abstract java.util.List<android.hardware.Sensor> getFullSensorList();

    protected abstract boolean initDataInjectionImpl(boolean z, int i);

    protected abstract boolean injectSensorDataImpl(android.hardware.Sensor sensor, float[] fArr, int i, long j);

    protected abstract void registerDynamicSensorCallbackImpl(android.hardware.SensorManager.DynamicSensorCallback dynamicSensorCallback, android.os.Handler handler);

    protected abstract boolean registerListenerImpl(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor, int i, android.os.Handler handler, int i2, int i3);

    protected abstract boolean requestTriggerSensorImpl(android.hardware.TriggerEventListener triggerEventListener, android.hardware.Sensor sensor);

    protected abstract boolean setOperationParameterImpl(android.hardware.SensorAdditionalInfo sensorAdditionalInfo);

    protected abstract void unregisterDynamicSensorCallbackImpl(android.hardware.SensorManager.DynamicSensorCallback dynamicSensorCallback);

    protected abstract void unregisterListenerImpl(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor);

    @java.lang.Deprecated
    public int getSensors() {
        return getLegacySensorManager().getSensors();
    }

    public java.util.List<android.hardware.Sensor> getSensorList(int i) {
        java.util.List<android.hardware.Sensor> list;
        java.util.List<android.hardware.Sensor> fullSensorList = getFullSensorList();
        synchronized (this.mSensorListByType) {
            list = this.mSensorListByType.get(i);
            if (list == null) {
                if (i != -1) {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    for (android.hardware.Sensor sensor : fullSensorList) {
                        if (sensor.getType() == i) {
                            arrayList.add(sensor);
                        }
                    }
                    fullSensorList = arrayList;
                }
                list = java.util.Collections.unmodifiableList(fullSensorList);
                this.mSensorListByType.append(i, list);
            }
        }
        return list;
    }

    public android.hardware.Sensor getSensorByHandle(int i) {
        for (android.hardware.Sensor sensor : getFullSensorList()) {
            if (sensor.getHandle() == i) {
                return sensor;
            }
        }
        return null;
    }

    public java.util.List<android.hardware.Sensor> getDynamicSensorList(int i) {
        java.util.List<android.hardware.Sensor> fullDynamicSensorList = getFullDynamicSensorList();
        if (i == -1) {
            return java.util.Collections.unmodifiableList(fullDynamicSensorList);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.hardware.Sensor sensor : fullDynamicSensorList) {
            if (sensor.getType() == i) {
                arrayList.add(sensor);
            }
        }
        return java.util.Collections.unmodifiableList(arrayList);
    }

    public android.hardware.Sensor getDefaultSensor(int i) {
        java.util.List<android.hardware.Sensor> sensorList = getSensorList(i);
        boolean z = i == 8 || i == 17 || i == 22 || i == 23 || i == 24 || i == 25 || i == 34 || i == 26 || i == 32 || i == 36;
        for (android.hardware.Sensor sensor : sensorList) {
            if (sensor.isWakeUpSensor() == z) {
                return sensor;
            }
        }
        return null;
    }

    public android.hardware.Sensor getDefaultSensor(int i, boolean z) {
        for (android.hardware.Sensor sensor : getSensorList(i)) {
            if (sensor.isWakeUpSensor() == z) {
                return sensor;
            }
        }
        return null;
    }

    @java.lang.Deprecated
    public boolean registerListener(android.hardware.SensorListener sensorListener, int i) {
        return registerListener(sensorListener, i, 3);
    }

    @java.lang.Deprecated
    public boolean registerListener(android.hardware.SensorListener sensorListener, int i, int i2) {
        return getLegacySensorManager().registerListener(sensorListener, i, i2);
    }

    @java.lang.Deprecated
    public void unregisterListener(android.hardware.SensorListener sensorListener) {
        unregisterListener(sensorListener, 255);
    }

    @java.lang.Deprecated
    public void unregisterListener(android.hardware.SensorListener sensorListener, int i) {
        getLegacySensorManager().unregisterListener(sensorListener, i);
    }

    public void unregisterListener(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor) {
        if (sensorEventListener == null || sensor == null) {
            return;
        }
        unregisterListenerImpl(sensorEventListener, sensor);
    }

    public void unregisterListener(android.hardware.SensorEventListener sensorEventListener) {
        if (sensorEventListener == null) {
            return;
        }
        unregisterListenerImpl(sensorEventListener, null);
    }

    public boolean registerListener(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor, int i) {
        return registerListener(sensorEventListener, sensor, i, (android.os.Handler) null);
    }

    public boolean registerListener(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor, int i, int i2) {
        return registerListenerImpl(sensorEventListener, sensor, getDelay(i), null, i2, 0);
    }

    public boolean registerListener(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor, int i, android.os.Handler handler) {
        return registerListenerImpl(sensorEventListener, sensor, getDelay(i), handler, 0, 0);
    }

    public boolean registerListener(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor, int i, int i2, android.os.Handler handler) {
        return registerListenerImpl(sensorEventListener, sensor, getDelay(i), handler, i2, 0);
    }

    public boolean flush(android.hardware.SensorEventListener sensorEventListener) {
        return flushImpl(sensorEventListener);
    }

    public android.hardware.SensorDirectChannel createDirectChannel(android.os.MemoryFile memoryFile) {
        return createDirectChannelImpl(memoryFile, null);
    }

    public android.hardware.SensorDirectChannel createDirectChannel(android.hardware.HardwareBuffer hardwareBuffer) {
        return createDirectChannelImpl(null, hardwareBuffer);
    }

    void destroyDirectChannel(android.hardware.SensorDirectChannel sensorDirectChannel) {
        destroyDirectChannelImpl(sensorDirectChannel);
    }

    public static abstract class DynamicSensorCallback {
        public void onDynamicSensorConnected(android.hardware.Sensor sensor) {
        }

        public void onDynamicSensorDisconnected(android.hardware.Sensor sensor) {
        }
    }

    public void registerDynamicSensorCallback(android.hardware.SensorManager.DynamicSensorCallback dynamicSensorCallback) {
        registerDynamicSensorCallback(dynamicSensorCallback, null);
    }

    public void registerDynamicSensorCallback(android.hardware.SensorManager.DynamicSensorCallback dynamicSensorCallback, android.os.Handler handler) {
        registerDynamicSensorCallbackImpl(dynamicSensorCallback, handler);
    }

    public void unregisterDynamicSensorCallback(android.hardware.SensorManager.DynamicSensorCallback dynamicSensorCallback) {
        unregisterDynamicSensorCallbackImpl(dynamicSensorCallback);
    }

    public boolean isDynamicSensorDiscoverySupported() {
        return getSensorList(32).size() > 0;
    }

    public static boolean getRotationMatrix(float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4) {
        float f = fArr3[0];
        float f2 = fArr3[1];
        float f3 = fArr3[2];
        float f4 = (f * f) + (f2 * f2) + (f3 * f3);
        if (f4 < 0.96236104f) {
            return false;
        }
        float f5 = fArr4[0];
        float f6 = fArr4[1];
        float f7 = fArr4[2];
        float f8 = (f6 * f3) - (f7 * f2);
        float f9 = (f7 * f) - (f5 * f3);
        float f10 = (f5 * f2) - (f6 * f);
        float sqrt = (float) java.lang.Math.sqrt((f8 * f8) + (f9 * f9) + (f10 * f10));
        if (sqrt < 0.1f) {
            return false;
        }
        float f11 = 1.0f / sqrt;
        float f12 = f8 * f11;
        float f13 = f9 * f11;
        float f14 = f10 * f11;
        float sqrt2 = 1.0f / ((float) java.lang.Math.sqrt(f4));
        float f15 = f * sqrt2;
        float f16 = f2 * sqrt2;
        float f17 = sqrt2 * f3;
        float f18 = (f16 * f14) - (f17 * f13);
        float f19 = (f17 * f12) - (f15 * f14);
        float f20 = (f15 * f13) - (f16 * f12);
        if (fArr != null) {
            if (fArr.length == 9) {
                fArr[0] = f12;
                fArr[1] = f13;
                fArr[2] = f14;
                fArr[3] = f18;
                fArr[4] = f19;
                fArr[5] = f20;
                fArr[6] = f15;
                fArr[7] = f16;
                fArr[8] = f17;
            } else if (fArr.length == 16) {
                fArr[0] = f12;
                fArr[1] = f13;
                fArr[2] = f14;
                fArr[3] = 0.0f;
                fArr[4] = f18;
                fArr[5] = f19;
                fArr[6] = f20;
                fArr[7] = 0.0f;
                fArr[8] = f15;
                fArr[9] = f16;
                fArr[10] = f17;
                fArr[11] = 0.0f;
                fArr[12] = 0.0f;
                fArr[13] = 0.0f;
                fArr[14] = 0.0f;
                fArr[15] = 1.0f;
            }
        }
        if (fArr2 != null) {
            float sqrt3 = 1.0f / ((float) java.lang.Math.sqrt(((f5 * f5) + (f6 * f6)) + (f7 * f7)));
            float f21 = ((f18 * f5) + (f19 * f6) + (f20 * f7)) * sqrt3;
            float f22 = ((f5 * f15) + (f6 * f16) + (f7 * f17)) * sqrt3;
            if (fArr2.length == 9) {
                fArr2[0] = 1.0f;
                fArr2[1] = 0.0f;
                fArr2[2] = 0.0f;
                fArr2[3] = 0.0f;
                fArr2[4] = f21;
                fArr2[5] = f22;
                fArr2[6] = 0.0f;
                fArr2[7] = -f22;
                fArr2[8] = f21;
                return true;
            }
            if (fArr2.length == 16) {
                fArr2[0] = 1.0f;
                fArr2[1] = 0.0f;
                fArr2[2] = 0.0f;
                fArr2[4] = 0.0f;
                fArr2[5] = f21;
                fArr2[6] = f22;
                fArr2[8] = 0.0f;
                fArr2[9] = -f22;
                fArr2[10] = f21;
                fArr2[14] = 0.0f;
                fArr2[13] = 0.0f;
                fArr2[12] = 0.0f;
                fArr2[11] = 0.0f;
                fArr2[7] = 0.0f;
                fArr2[3] = 0.0f;
                fArr2[15] = 1.0f;
                return true;
            }
            return true;
        }
        return true;
    }

    public static float getInclination(float[] fArr) {
        if (fArr.length == 9) {
            return (float) java.lang.Math.atan2(fArr[5], fArr[4]);
        }
        return (float) java.lang.Math.atan2(fArr[6], fArr[5]);
    }

    public static boolean remapCoordinateSystem(float[] fArr, int i, int i2, float[] fArr2) {
        if (fArr == fArr2) {
            float[] fArr3 = sTempMatrix;
            synchronized (fArr3) {
                if (remapCoordinateSystemImpl(fArr, i, i2, fArr3)) {
                    int length = fArr2.length;
                    for (int i3 = 0; i3 < length; i3++) {
                        fArr2[i3] = fArr3[i3];
                    }
                    return true;
                }
            }
        }
        return remapCoordinateSystemImpl(fArr, i, i2, fArr2);
    }

    private static boolean remapCoordinateSystemImpl(float[] fArr, int i, int i2, float[] fArr2) {
        int i3;
        int i4;
        int length = fArr2.length;
        int i5 = 0;
        if (fArr.length != length || (i & 124) != 0 || (i2 & 124) != 0 || (i3 = i & 3) == 0 || (i4 = i2 & 3) == 0 || i3 == i4) {
            return false;
        }
        int i6 = i ^ i2;
        int i7 = i3 - 1;
        int i8 = i4 - 1;
        int i9 = (i6 & 3) - 1;
        int i10 = 3;
        if (((((i9 + 1) % 3) ^ i7) | (((i9 + 2) % 3) ^ i8)) != 0) {
            i6 ^= 128;
        }
        boolean z = i >= 128;
        boolean z2 = i2 >= 128;
        boolean z3 = i6 >= 128;
        int i11 = length == 16 ? 4 : 3;
        int i12 = 0;
        while (i12 < i10) {
            int i13 = i12 * i11;
            while (i5 < i10) {
                if (i7 == i5) {
                    fArr2[i13 + i5] = z ? -fArr[i13 + 0] : fArr[i13 + 0];
                }
                if (i8 == i5) {
                    fArr2[i13 + i5] = z2 ? -fArr[i13 + 1] : fArr[i13 + 1];
                }
                if (i9 == i5) {
                    int i14 = i13 + 2;
                    fArr2[i13 + i5] = z3 ? -fArr[i14] : fArr[i14];
                }
                i5++;
                i10 = 3;
            }
            i12++;
            i5 = 0;
            i10 = 3;
        }
        if (length == 16) {
            fArr2[14] = 0.0f;
            fArr2[13] = 0.0f;
            fArr2[12] = 0.0f;
            fArr2[11] = 0.0f;
            fArr2[7] = 0.0f;
            fArr2[3] = 0.0f;
            fArr2[15] = 1.0f;
            return true;
        }
        return true;
    }

    public static float[] getOrientation(float[] fArr, float[] fArr2) {
        if (fArr.length == 9) {
            fArr2[0] = (float) java.lang.Math.atan2(fArr[1], fArr[4]);
            fArr2[1] = (float) java.lang.Math.asin(-fArr[7]);
            fArr2[2] = (float) java.lang.Math.atan2(-fArr[6], fArr[8]);
        } else {
            fArr2[0] = (float) java.lang.Math.atan2(fArr[1], fArr[5]);
            fArr2[1] = (float) java.lang.Math.asin(-fArr[9]);
            fArr2[2] = (float) java.lang.Math.atan2(-fArr[8], fArr[10]);
        }
        return fArr2;
    }

    public static float getAltitude(float f, float f2) {
        return (1.0f - ((float) java.lang.Math.pow(f2 / f, 0.19029495120048523d))) * 44330.0f;
    }

    public static void getAngleChange(float[] fArr, float[] fArr2, float[] fArr3) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11;
        float f12;
        float f13;
        float f14;
        float f15;
        float f16;
        float f17;
        float f18;
        if (fArr2.length == 9) {
            f2 = fArr2[0];
            f3 = fArr2[1];
            f4 = fArr2[2];
            f5 = fArr2[3];
            f6 = fArr2[4];
            f7 = fArr2[5];
            f8 = fArr2[6];
            f9 = fArr2[7];
            f = fArr2[8];
        } else if (fArr2.length != 16) {
            f = 0.0f;
            f2 = 0.0f;
            f3 = 0.0f;
            f4 = 0.0f;
            f5 = 0.0f;
            f6 = 0.0f;
            f7 = 0.0f;
            f8 = 0.0f;
            f9 = 0.0f;
        } else {
            f2 = fArr2[0];
            f3 = fArr2[1];
            f4 = fArr2[2];
            f5 = fArr2[4];
            f6 = fArr2[5];
            f7 = fArr2[6];
            f8 = fArr2[8];
            f9 = fArr2[9];
            f = fArr2[10];
        }
        if (fArr3.length == 9) {
            f17 = fArr3[0];
            f11 = fArr3[1];
            f14 = fArr3[2];
            f13 = fArr3[3];
            float f19 = fArr3[4];
            float f20 = fArr3[5];
            float f21 = fArr3[6];
            f12 = fArr3[7];
            f10 = fArr3[8];
            f18 = f21;
            f15 = f20;
            f16 = f19;
        } else if (fArr3.length != 16) {
            f10 = 0.0f;
            f11 = 0.0f;
            f12 = 0.0f;
            f13 = 0.0f;
            f14 = 0.0f;
            f15 = 0.0f;
            f16 = 0.0f;
            f17 = 0.0f;
            f18 = 0.0f;
        } else {
            f17 = fArr3[0];
            float f22 = fArr3[1];
            float f23 = fArr3[2];
            float f24 = fArr3[4];
            f16 = fArr3[5];
            f15 = fArr3[6];
            float f25 = fArr3[8];
            float f26 = fArr3[9];
            f10 = fArr3[10];
            f11 = f22;
            f18 = f25;
            f12 = f26;
            f14 = f23;
            f13 = f24;
        }
        float f27 = (f11 * f3) + (f16 * f6) + (f12 * f9);
        float f28 = (f3 * f14) + (f6 * f15) + (f9 * f10);
        fArr[0] = (float) java.lang.Math.atan2((f17 * f3) + (f13 * f6) + (f18 * f9), f27);
        fArr[1] = (float) java.lang.Math.asin(-f28);
        fArr[2] = (float) java.lang.Math.atan2(-((f2 * f14) + (f5 * f15) + (f8 * f10)), (f14 * f4) + (f15 * f7) + (f10 * f));
    }

    public static void getRotationMatrixFromVector(float[] fArr, float[] fArr2) {
        float sqrt;
        float f = fArr2[0];
        float f2 = fArr2[1];
        float f3 = fArr2[2];
        if (fArr2.length >= 4) {
            sqrt = fArr2[3];
        } else {
            float f4 = ((1.0f - (f * f)) - (f2 * f2)) - (f3 * f3);
            sqrt = f4 > 0.0f ? (float) java.lang.Math.sqrt(f4) : 0.0f;
        }
        float f5 = f * 2.0f;
        float f6 = f * f5;
        float f7 = f2 * 2.0f;
        float f8 = f7 * f2;
        float f9 = 2.0f * f3;
        float f10 = f9 * f3;
        float f11 = f2 * f5;
        float f12 = f9 * sqrt;
        float f13 = f5 * f3;
        float f14 = f7 * sqrt;
        float f15 = f7 * f3;
        float f16 = f5 * sqrt;
        if (fArr.length == 9) {
            fArr[0] = (1.0f - f8) - f10;
            fArr[1] = f11 - f12;
            fArr[2] = f13 + f14;
            fArr[3] = f11 + f12;
            float f17 = 1.0f - f6;
            fArr[4] = f17 - f10;
            fArr[5] = f15 - f16;
            fArr[6] = f13 - f14;
            fArr[7] = f15 + f16;
            fArr[8] = f17 - f8;
            return;
        }
        if (fArr.length == 16) {
            fArr[0] = (1.0f - f8) - f10;
            fArr[1] = f11 - f12;
            fArr[2] = f13 + f14;
            fArr[3] = 0.0f;
            fArr[4] = f11 + f12;
            float f18 = 1.0f - f6;
            fArr[5] = f18 - f10;
            fArr[6] = f15 - f16;
            fArr[7] = 0.0f;
            fArr[8] = f13 - f14;
            fArr[9] = f15 + f16;
            fArr[10] = f18 - f8;
            fArr[11] = 0.0f;
            fArr[14] = 0.0f;
            fArr[13] = 0.0f;
            fArr[12] = 0.0f;
            fArr[15] = 1.0f;
        }
    }

    public static void getQuaternionFromVector(float[] fArr, float[] fArr2) {
        if (fArr2.length >= 4) {
            fArr[0] = fArr2[3];
        } else {
            fArr[0] = ((1.0f - (fArr2[0] * fArr2[0])) - (fArr2[1] * fArr2[1])) - (fArr2[2] * fArr2[2]);
            fArr[0] = fArr[0] > 0.0f ? (float) java.lang.Math.sqrt(fArr[0]) : 0.0f;
        }
        fArr[1] = fArr2[0];
        fArr[2] = fArr2[1];
        fArr[3] = fArr2[2];
    }

    public boolean requestTriggerSensor(android.hardware.TriggerEventListener triggerEventListener, android.hardware.Sensor sensor) {
        return requestTriggerSensorImpl(triggerEventListener, sensor);
    }

    public boolean cancelTriggerSensor(android.hardware.TriggerEventListener triggerEventListener, android.hardware.Sensor sensor) {
        return cancelTriggerSensorImpl(triggerEventListener, sensor, true);
    }

    @android.annotation.SystemApi
    public boolean initDataInjection(boolean z) {
        return initDataInjectionImpl(z, 1);
    }

    public boolean initDataInjection(boolean z, int i) {
        return initDataInjectionImpl(z, i);
    }

    @android.annotation.SystemApi
    public boolean injectSensorData(android.hardware.Sensor sensor, float[] fArr, int i, long j) {
        if (sensor == null) {
            throw new java.lang.IllegalArgumentException("sensor cannot be null");
        }
        if (fArr == null) {
            throw new java.lang.IllegalArgumentException("sensor data cannot be null");
        }
        int maxLengthValuesArray = android.hardware.Sensor.getMaxLengthValuesArray(sensor, 23);
        if (fArr.length != maxLengthValuesArray) {
            throw new java.lang.IllegalArgumentException("Wrong number of values for sensor " + sensor.getName() + " actual=" + fArr.length + " expected=" + maxLengthValuesArray);
        }
        if (i < -1 || i > 3) {
            throw new java.lang.IllegalArgumentException("Invalid sensor accuracy");
        }
        if (j <= 0) {
            throw new java.lang.IllegalArgumentException("Negative or zero sensor timestamp");
        }
        return injectSensorDataImpl(sensor, fArr, i, j);
    }

    private android.hardware.LegacySensorManager getLegacySensorManager() {
        android.hardware.LegacySensorManager legacySensorManager;
        synchronized (this.mSensorListByType) {
            if (this.mLegacySensorManager == null) {
                android.util.Log.i(TAG, "This application is using deprecated SensorManager API which will be removed someday.  Please consider switching to the new API.");
                this.mLegacySensorManager = new android.hardware.LegacySensorManager(this);
            }
            legacySensorManager = this.mLegacySensorManager;
        }
        return legacySensorManager;
    }

    private static int getDelay(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 20000;
            case 2:
                return 66667;
            case 3:
                return 200000;
            default:
                return i;
        }
    }

    public boolean setOperationParameter(android.hardware.SensorAdditionalInfo sensorAdditionalInfo) {
        return setOperationParameterImpl(sensorAdditionalInfo);
    }
}
