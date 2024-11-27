package android.hardware;

/* loaded from: classes.dex */
final class LegacySensorManager {
    private static boolean sInitialized;
    private static int sRotation = 0;
    private static android.view.IWindowManager sWindowManager;
    private final java.util.HashMap<android.hardware.SensorListener, android.hardware.LegacySensorManager.LegacyListener> mLegacyListenersMap = new java.util.HashMap<>();
    private final android.hardware.SensorManager mSensorManager;

    public LegacySensorManager(android.hardware.SensorManager sensorManager) {
        this.mSensorManager = sensorManager;
        synchronized (android.hardware.SensorManager.class) {
            if (!sInitialized) {
                sWindowManager = android.view.IWindowManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.WINDOW_SERVICE));
                if (sWindowManager != null) {
                    try {
                        sRotation = sWindowManager.watchRotation(new android.view.IRotationWatcher.Stub() { // from class: android.hardware.LegacySensorManager.1
                            @Override // android.view.IRotationWatcher
                            public void onRotationChanged(int i) {
                                android.hardware.LegacySensorManager.onRotationChanged(i);
                            }
                        }, 0);
                    } catch (android.os.RemoteException e) {
                    }
                }
            }
        }
    }

    public int getSensors() {
        java.util.Iterator<android.hardware.Sensor> it = this.mSensorManager.getFullSensorList().iterator();
        int i = 0;
        while (it.hasNext()) {
            switch (it.next().getType()) {
                case 1:
                    i |= 2;
                    break;
                case 2:
                    i |= 8;
                    break;
                case 3:
                    i |= 129;
                    break;
            }
        }
        return i;
    }

    public boolean registerListener(android.hardware.SensorListener sensorListener, int i, int i2) {
        if (sensorListener == null) {
            return false;
        }
        return registerLegacyListener(4, 7, sensorListener, i, i2) || (registerLegacyListener(1, 3, sensorListener, i, i2) || (registerLegacyListener(128, 3, sensorListener, i, i2) || (registerLegacyListener(8, 2, sensorListener, i, i2) || (registerLegacyListener(2, 1, sensorListener, i, i2)))));
    }

    private boolean registerLegacyListener(int i, int i2, android.hardware.SensorListener sensorListener, int i3, int i4) {
        android.hardware.Sensor defaultSensor;
        boolean z;
        if ((i3 & i) != 0 && (defaultSensor = this.mSensorManager.getDefaultSensor(i2)) != null) {
            synchronized (this.mLegacyListenersMap) {
                android.hardware.LegacySensorManager.LegacyListener legacyListener = this.mLegacyListenersMap.get(sensorListener);
                if (legacyListener == null) {
                    legacyListener = new android.hardware.LegacySensorManager.LegacyListener(sensorListener);
                    this.mLegacyListenersMap.put(sensorListener, legacyListener);
                }
                if (legacyListener.registerSensor(i)) {
                    z = this.mSensorManager.registerListener(legacyListener, defaultSensor, i4);
                } else {
                    z = true;
                }
            }
            return z;
        }
        return false;
    }

    public void unregisterListener(android.hardware.SensorListener sensorListener, int i) {
        if (sensorListener == null) {
            return;
        }
        unregisterLegacyListener(2, 1, sensorListener, i);
        unregisterLegacyListener(8, 2, sensorListener, i);
        unregisterLegacyListener(128, 3, sensorListener, i);
        unregisterLegacyListener(1, 3, sensorListener, i);
        unregisterLegacyListener(4, 7, sensorListener, i);
    }

    private void unregisterLegacyListener(int i, int i2, android.hardware.SensorListener sensorListener, int i3) {
        android.hardware.Sensor defaultSensor;
        if ((i3 & i) != 0 && (defaultSensor = this.mSensorManager.getDefaultSensor(i2)) != null) {
            synchronized (this.mLegacyListenersMap) {
                android.hardware.LegacySensorManager.LegacyListener legacyListener = this.mLegacyListenersMap.get(sensorListener);
                if (legacyListener != null && legacyListener.unregisterSensor(i)) {
                    this.mSensorManager.unregisterListener(legacyListener, defaultSensor);
                    if (!legacyListener.hasSensors()) {
                        this.mLegacyListenersMap.remove(sensorListener);
                    }
                }
            }
        }
    }

    static void onRotationChanged(int i) {
        synchronized (android.hardware.SensorManager.class) {
            sRotation = i;
        }
    }

    static int getRotation() {
        int i;
        synchronized (android.hardware.SensorManager.class) {
            i = sRotation;
        }
        return i;
    }

    private static final class LegacyListener implements android.hardware.SensorEventListener {
        private android.hardware.SensorListener mTarget;
        private float[] mValues = new float[6];
        private final android.hardware.LegacySensorManager.LmsFilter mYawfilter = new android.hardware.LegacySensorManager.LmsFilter();
        private int mSensors = 0;

        LegacyListener(android.hardware.SensorListener sensorListener) {
            this.mTarget = sensorListener;
        }

        boolean registerSensor(int i) {
            if ((this.mSensors & i) != 0) {
                return false;
            }
            boolean hasOrientationSensor = hasOrientationSensor(this.mSensors);
            this.mSensors |= i;
            return (hasOrientationSensor && hasOrientationSensor(i)) ? false : true;
        }

        boolean unregisterSensor(int i) {
            if ((this.mSensors & i) == 0) {
                return false;
            }
            this.mSensors &= ~i;
            return (hasOrientationSensor(i) && hasOrientationSensor(this.mSensors)) ? false : true;
        }

        boolean hasSensors() {
            return this.mSensors != 0;
        }

        private static boolean hasOrientationSensor(int i) {
            return (i & 129) != 0;
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
            try {
                this.mTarget.onAccuracyChanged(getLegacySensorType(sensor.getType()), i);
            } catch (java.lang.AbstractMethodError e) {
            }
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
            float[] fArr = this.mValues;
            fArr[0] = sensorEvent.values[0];
            fArr[1] = sensorEvent.values[1];
            fArr[2] = sensorEvent.values[2];
            int type = sensorEvent.sensor.getType();
            int legacySensorType = getLegacySensorType(type);
            mapSensorDataToWindow(legacySensorType, fArr, android.hardware.LegacySensorManager.getRotation());
            if (type == 3) {
                if ((this.mSensors & 128) != 0) {
                    this.mTarget.onSensorChanged(128, fArr);
                }
                if ((this.mSensors & 1) != 0) {
                    fArr[0] = this.mYawfilter.filter(sensorEvent.timestamp, fArr[0]);
                    this.mTarget.onSensorChanged(1, fArr);
                    return;
                }
                return;
            }
            this.mTarget.onSensorChanged(legacySensorType, fArr);
        }

        private void mapSensorDataToWindow(int i, float[] fArr, int i2) {
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            switch (i) {
                case 1:
                case 128:
                    f3 = -f3;
                    break;
                case 2:
                    f = -f;
                    f2 = -f2;
                    f3 = -f3;
                    break;
                case 8:
                    f = -f;
                    f2 = -f2;
                    break;
            }
            fArr[0] = f;
            fArr[1] = f2;
            fArr[2] = f3;
            fArr[3] = f;
            fArr[4] = f2;
            fArr[5] = f3;
            if ((i2 & 1) != 0) {
                switch (i) {
                    case 1:
                    case 128:
                        fArr[0] = f + (f < 270.0f ? 90 : -270);
                        fArr[1] = f3;
                        fArr[2] = f2;
                        break;
                    case 2:
                    case 8:
                        fArr[0] = -f2;
                        fArr[1] = f;
                        fArr[2] = f3;
                        break;
                }
            }
            if ((i2 & 2) != 0) {
                float f4 = fArr[0];
                float f5 = fArr[1];
                float f6 = fArr[2];
                switch (i) {
                    case 1:
                    case 128:
                        fArr[0] = f4 >= 180.0f ? f4 - 180.0f : f4 + 180.0f;
                        fArr[1] = -f5;
                        fArr[2] = -f6;
                        break;
                    case 2:
                    case 8:
                        fArr[0] = -f4;
                        fArr[1] = -f5;
                        fArr[2] = f6;
                        break;
                }
            }
        }

        private static int getLegacySensorType(int i) {
            switch (i) {
                case 1:
                    return 2;
                case 2:
                    return 8;
                case 3:
                    return 128;
                case 4:
                case 5:
                case 6:
                default:
                    return 0;
                case 7:
                    return 4;
            }
        }
    }

    private static final class LmsFilter {
        private static final int COUNT = 12;
        private static final float PREDICTION_RATIO = 0.33333334f;
        private static final float PREDICTION_TIME = 0.08f;
        private static final int SENSORS_RATE_MS = 20;
        private float[] mV = new float[24];
        private long[] mT = new long[24];
        private int mIndex = 12;

        public float filter(long j, float f) {
            float f2;
            float f3 = this.mV[this.mIndex];
            if (f - f3 > 180.0f) {
                f2 = f - 360.0f;
            } else if (f3 - f <= 180.0f) {
                f2 = f;
            } else {
                f2 = f + 360.0f;
            }
            this.mIndex++;
            if (this.mIndex >= 24) {
                this.mIndex = 12;
            }
            this.mV[this.mIndex] = f2;
            this.mT[this.mIndex] = j;
            this.mV[this.mIndex - 12] = f2;
            this.mT[this.mIndex - 12] = j;
            float f4 = 0.0f;
            float f5 = 0.0f;
            float f6 = 0.0f;
            float f7 = 0.0f;
            float f8 = 0.0f;
            for (int i = 0; i < 11; i++) {
                int i2 = (this.mIndex - 1) - i;
                float f9 = this.mV[i2];
                int i3 = i2 + 1;
                float f10 = (((this.mT[i2] / 2) + (this.mT[i3] / 2)) - j) * 1.0E-9f;
                float f11 = (this.mT[i2] - this.mT[i3]) * 1.0E-9f;
                float f12 = f11 * f11;
                f4 += f9 * f12;
                float f13 = f10 * f12;
                f5 += f10 * f13;
                f6 += f13;
                f7 += f9 * f13;
                f8 += f12;
            }
            float f14 = ((f4 * f5) + (f7 * f6)) / ((f5 * f8) + (f6 * f6));
            float f15 = (f14 + ((((f8 * f14) - f4) / f6) * PREDICTION_TIME)) * 0.0027777778f;
            if ((f15 >= 0.0f ? f15 : -f15) >= 0.5f) {
                f15 = (f15 - ((float) java.lang.Math.ceil(0.5f + f15))) + 1.0f;
            }
            if (f15 < 0.0f) {
                f15 += 1.0f;
            }
            return f15 * 360.0f;
        }
    }
}
