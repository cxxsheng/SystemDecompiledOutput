package com.android.server.display.whitebalance;

/* loaded from: classes.dex */
abstract class AmbientSensor {
    private static final int HISTORY_SIZE = 50;
    private boolean mEnabled;
    private int mEventsCount;
    private com.android.server.display.utils.History mEventsHistory;
    private final android.os.Handler mHandler;
    private android.hardware.SensorEventListener mListener = new android.hardware.SensorEventListener() { // from class: com.android.server.display.whitebalance.AmbientSensor.1
        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
            com.android.server.display.whitebalance.AmbientSensor.this.handleNewEvent(sensorEvent.values[0]);
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
        }
    };
    protected boolean mLoggingEnabled;
    private int mRate;
    protected android.hardware.Sensor mSensor;
    protected final android.hardware.SensorManager mSensorManager;
    protected java.lang.String mTag;

    protected abstract void update(float f);

    AmbientSensor(java.lang.String str, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.hardware.SensorManager sensorManager, int i) {
        validateArguments(handler, sensorManager, i);
        this.mTag = str;
        this.mLoggingEnabled = false;
        this.mHandler = handler;
        this.mSensorManager = sensorManager;
        this.mEnabled = false;
        this.mRate = i;
        this.mEventsCount = 0;
        this.mEventsHistory = new com.android.server.display.utils.History(50);
    }

    public boolean setEnabled(boolean z) {
        if (z) {
            return enable();
        }
        return disable();
    }

    public boolean setLoggingEnabled(boolean z) {
        if (this.mLoggingEnabled == z) {
            return false;
        }
        this.mLoggingEnabled = z;
        return true;
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("  " + this.mTag);
        printWriter.println("    mLoggingEnabled=" + this.mLoggingEnabled);
        printWriter.println("    mHandler=" + this.mHandler);
        printWriter.println("    mSensorManager=" + this.mSensorManager);
        printWriter.println("    mSensor=" + this.mSensor);
        printWriter.println("    mEnabled=" + this.mEnabled);
        printWriter.println("    mRate=" + this.mRate);
        printWriter.println("    mEventsCount=" + this.mEventsCount);
        printWriter.println("    mEventsHistory=" + this.mEventsHistory);
    }

    private static void validateArguments(android.os.Handler handler, android.hardware.SensorManager sensorManager, int i) {
        java.util.Objects.requireNonNull(handler, "handler cannot be null");
        java.util.Objects.requireNonNull(sensorManager, "sensorManager cannot be null");
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("rate must be positive");
        }
    }

    private boolean enable() {
        if (this.mEnabled) {
            return false;
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(this.mTag, "enabling");
        }
        this.mEnabled = true;
        startListening();
        return true;
    }

    private boolean disable() {
        if (!this.mEnabled) {
            return false;
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(this.mTag, "disabling");
        }
        this.mEnabled = false;
        this.mEventsCount = 0;
        stopListening();
        return true;
    }

    private void startListening() {
        if (this.mSensorManager == null) {
            return;
        }
        this.mSensorManager.registerListener(this.mListener, this.mSensor, this.mRate * 1000, this.mHandler);
    }

    private void stopListening() {
        if (this.mSensorManager == null) {
            return;
        }
        this.mSensorManager.unregisterListener(this.mListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNewEvent(float f) {
        if (!this.mEnabled) {
            return;
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(this.mTag, "handle new event: " + f);
        }
        this.mEventsCount++;
        this.mEventsHistory.add(f);
        update(f);
    }

    static class AmbientBrightnessSensor extends com.android.server.display.whitebalance.AmbientSensor {
        private static final java.lang.String TAG = "AmbientBrightnessSensor";

        @android.annotation.Nullable
        private com.android.server.display.whitebalance.AmbientSensor.AmbientBrightnessSensor.Callbacks mCallbacks;

        interface Callbacks {
            void onAmbientBrightnessChanged(float f);
        }

        AmbientBrightnessSensor(@android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.hardware.SensorManager sensorManager, int i) {
            super(TAG, handler, sensorManager, i);
            this.mSensor = this.mSensorManager.getDefaultSensor(5);
            if (this.mSensor == null) {
                throw new java.lang.IllegalStateException("cannot find light sensor");
            }
            this.mCallbacks = null;
        }

        public boolean setCallbacks(com.android.server.display.whitebalance.AmbientSensor.AmbientBrightnessSensor.Callbacks callbacks) {
            if (this.mCallbacks == callbacks) {
                return false;
            }
            this.mCallbacks = callbacks;
            return true;
        }

        @Override // com.android.server.display.whitebalance.AmbientSensor
        public void dump(java.io.PrintWriter printWriter) {
            super.dump(printWriter);
            printWriter.println("    mCallbacks=" + this.mCallbacks);
        }

        @Override // com.android.server.display.whitebalance.AmbientSensor
        protected void update(float f) {
            if (this.mCallbacks != null) {
                this.mCallbacks.onAmbientBrightnessChanged(f);
            }
        }
    }

    static class AmbientColorTemperatureSensor extends com.android.server.display.whitebalance.AmbientSensor {
        private static final java.lang.String TAG = "AmbientColorTemperatureSensor";

        @android.annotation.Nullable
        private com.android.server.display.whitebalance.AmbientSensor.AmbientColorTemperatureSensor.Callbacks mCallbacks;

        interface Callbacks {
            void onAmbientColorTemperatureChanged(float f);
        }

        AmbientColorTemperatureSensor(@android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.hardware.SensorManager sensorManager, java.lang.String str, int i) {
            super(TAG, handler, sensorManager, i);
            this.mSensor = null;
            java.util.Iterator<android.hardware.Sensor> it = this.mSensorManager.getSensorList(-1).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                android.hardware.Sensor next = it.next();
                if (next.getStringType().equals(str)) {
                    this.mSensor = next;
                    break;
                }
            }
            if (this.mSensor == null) {
                throw new java.lang.IllegalStateException("cannot find sensor " + str);
            }
            this.mCallbacks = null;
        }

        public boolean setCallbacks(com.android.server.display.whitebalance.AmbientSensor.AmbientColorTemperatureSensor.Callbacks callbacks) {
            if (this.mCallbacks == callbacks) {
                return false;
            }
            this.mCallbacks = callbacks;
            return true;
        }

        @Override // com.android.server.display.whitebalance.AmbientSensor
        public void dump(java.io.PrintWriter printWriter) {
            super.dump(printWriter);
            printWriter.println("    mCallbacks=" + this.mCallbacks);
        }

        @Override // com.android.server.display.whitebalance.AmbientSensor
        protected void update(float f) {
            if (this.mCallbacks != null) {
                this.mCallbacks.onAmbientColorTemperatureChanged(f);
            }
        }
    }
}
