package com.android.server.input;

/* loaded from: classes2.dex */
final class AmbientKeyboardBacklightController implements android.hardware.display.DisplayManager.DisplayListener, android.hardware.SensorEventListener {

    @com.android.internal.annotations.VisibleForTesting
    public static final int HYSTERESIS_THRESHOLD = 2;
    private static final int MSG_BRIGHTNESS_CALLBACK = 0;
    private static final int MSG_SETUP_DISPLAY_AND_SENSOR = 1;
    private com.android.server.input.AmbientKeyboardBacklightController.BrightnessStep[] mBrightnessSteps;
    private final android.content.Context mContext;
    private int mCurrentBrightnessStepIndex;

    @com.android.internal.annotations.GuardedBy({"sAmbientControllerLock"})
    private java.lang.String mCurrentDefaultDisplayUniqueId;
    private final android.os.Handler mHandler;
    private com.android.server.input.AmbientKeyboardBacklightController.HysteresisState mHysteresisState;

    @com.android.internal.annotations.GuardedBy({"sAmbientControllerLock"})
    @android.annotation.Nullable
    private android.hardware.Sensor mLightSensor;
    private int mSmoothedLux;
    private int mSmoothedLuxAtLastAdjustment;
    private float mSmoothingConstant;
    private static final java.lang.String TAG = "KbdBacklightController";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final java.lang.Object sAmbientControllerLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"sAmbientControllerLock"})
    private final java.util.List<com.android.server.input.AmbientKeyboardBacklightController.AmbientKeyboardBacklightListener> mAmbientKeyboardBacklightListeners = new java.util.ArrayList();
    private int mHysteresisCount = 0;

    public interface AmbientKeyboardBacklightListener {
        void onKeyboardBacklightValueChanged(int i);
    }

    private enum HysteresisState {
        STABLE,
        DECREASING,
        INCREASING,
        IMMEDIATE
    }

    AmbientKeyboardBacklightController(android.content.Context context, android.os.Looper looper) {
        this.mContext = context;
        this.mHandler = new android.os.Handler(looper, new android.os.Handler.Callback() { // from class: com.android.server.input.AmbientKeyboardBacklightController$$ExternalSyntheticLambda0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(android.os.Message message) {
                boolean handleMessage;
                handleMessage = com.android.server.input.AmbientKeyboardBacklightController.this.handleMessage(message);
                return handleMessage;
            }
        });
        initConfiguration();
    }

    public void systemRunning() {
        this.mHandler.sendEmptyMessage(1);
        android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
        java.util.Objects.requireNonNull(displayManager);
        displayManager.registerDisplayListener(this, this.mHandler);
    }

    public void registerAmbientBacklightListener(com.android.server.input.AmbientKeyboardBacklightController.AmbientKeyboardBacklightListener ambientKeyboardBacklightListener) {
        synchronized (sAmbientControllerLock) {
            try {
                if (this.mAmbientKeyboardBacklightListeners.contains(ambientKeyboardBacklightListener)) {
                    throw new java.lang.IllegalStateException("AmbientKeyboardBacklightListener was already registered, listener = " + ambientKeyboardBacklightListener);
                }
                if (this.mAmbientKeyboardBacklightListeners.isEmpty()) {
                    addSensorListener(this.mLightSensor);
                }
                this.mAmbientKeyboardBacklightListeners.add(ambientKeyboardBacklightListener);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterAmbientBacklightListener(com.android.server.input.AmbientKeyboardBacklightController.AmbientKeyboardBacklightListener ambientKeyboardBacklightListener) {
        synchronized (sAmbientControllerLock) {
            try {
                if (!this.mAmbientKeyboardBacklightListeners.contains(ambientKeyboardBacklightListener)) {
                    throw new java.lang.IllegalStateException("AmbientKeyboardBacklightListener was never registered, listener = " + ambientKeyboardBacklightListener);
                }
                this.mAmbientKeyboardBacklightListeners.remove(ambientKeyboardBacklightListener);
                if (this.mAmbientKeyboardBacklightListeners.isEmpty()) {
                    removeSensorListener(this.mLightSensor);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void sendBrightnessAdjustment(int i) {
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 0, java.lang.Integer.valueOf(i)));
    }

    private void handleBrightnessCallback(int i) {
        synchronized (sAmbientControllerLock) {
            try {
                java.util.Iterator<com.android.server.input.AmbientKeyboardBacklightController.AmbientKeyboardBacklightListener> it = this.mAmbientKeyboardBacklightListeners.iterator();
                while (it.hasNext()) {
                    it.next().onKeyboardBacklightValueChanged(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void handleAmbientLuxChange(float f) {
        if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            android.util.Slog.w(TAG, "Light sensor doesn't have valid value");
            return;
        }
        updateSmoothedLux(f);
        if (this.mHysteresisState != com.android.server.input.AmbientKeyboardBacklightController.HysteresisState.IMMEDIATE && this.mSmoothedLux == this.mSmoothedLuxAtLastAdjustment) {
            this.mHysteresisState = com.android.server.input.AmbientKeyboardBacklightController.HysteresisState.STABLE;
            return;
        }
        int max = java.lang.Math.max(0, this.mCurrentBrightnessStepIndex);
        int length = this.mBrightnessSteps.length;
        if (this.mSmoothedLux > this.mSmoothedLuxAtLastAdjustment) {
            if (this.mHysteresisState != com.android.server.input.AmbientKeyboardBacklightController.HysteresisState.IMMEDIATE && this.mHysteresisState != com.android.server.input.AmbientKeyboardBacklightController.HysteresisState.INCREASING) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "ALS transitioned to brightness increasing state");
                }
                this.mHysteresisState = com.android.server.input.AmbientKeyboardBacklightController.HysteresisState.INCREASING;
                this.mHysteresisCount = 0;
            }
            while (max < length && this.mSmoothedLux >= this.mBrightnessSteps[max].mIncreaseLuxThreshold) {
                max++;
            }
        } else if (this.mSmoothedLux < this.mSmoothedLuxAtLastAdjustment) {
            if (this.mHysteresisState != com.android.server.input.AmbientKeyboardBacklightController.HysteresisState.IMMEDIATE && this.mHysteresisState != com.android.server.input.AmbientKeyboardBacklightController.HysteresisState.DECREASING) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "ALS transitioned to brightness decreasing state");
                }
                this.mHysteresisState = com.android.server.input.AmbientKeyboardBacklightController.HysteresisState.DECREASING;
                this.mHysteresisCount = 0;
            }
            while (max >= 0 && this.mSmoothedLux <= this.mBrightnessSteps[max].mDecreaseLuxThreshold) {
                max--;
            }
        }
        if (this.mHysteresisState == com.android.server.input.AmbientKeyboardBacklightController.HysteresisState.IMMEDIATE) {
            this.mCurrentBrightnessStepIndex = max;
            this.mSmoothedLuxAtLastAdjustment = this.mSmoothedLux;
            this.mHysteresisState = com.android.server.input.AmbientKeyboardBacklightController.HysteresisState.STABLE;
            this.mHysteresisCount = 0;
            sendBrightnessAdjustment(this.mBrightnessSteps[max].mBrightnessValue);
            return;
        }
        if (max == this.mCurrentBrightnessStepIndex) {
            return;
        }
        this.mHysteresisCount++;
        if (DEBUG) {
            android.util.Slog.d(TAG, "Incremented hysteresis count to " + this.mHysteresisCount + " (lux went from " + this.mSmoothedLuxAtLastAdjustment + " to " + this.mSmoothedLux + ")");
        }
        if (this.mHysteresisCount >= 2) {
            this.mCurrentBrightnessStepIndex = max;
            this.mSmoothedLuxAtLastAdjustment = this.mSmoothedLux;
            this.mHysteresisCount = 1;
            sendBrightnessAdjustment(this.mBrightnessSteps[max].mBrightnessValue);
        }
    }

    private void handleDisplayChange() {
        android.hardware.display.DisplayManagerInternal displayManagerInternal = (android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class);
        android.view.DisplayInfo displayInfo = displayManagerInternal.getDisplayInfo(0);
        synchronized (sAmbientControllerLock) {
            try {
                if (java.util.Objects.equals(this.mCurrentDefaultDisplayUniqueId, displayInfo.uniqueId)) {
                    return;
                }
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Default display changed: resetting the light sensor");
                }
                this.mCurrentDefaultDisplayUniqueId = displayInfo.uniqueId;
                if (!this.mAmbientKeyboardBacklightListeners.isEmpty()) {
                    removeSensorListener(this.mLightSensor);
                }
                this.mLightSensor = getAmbientLightSensor(displayManagerInternal.getAmbientLightSensorData(0));
                if (!this.mAmbientKeyboardBacklightListeners.isEmpty()) {
                    addSensorListener(this.mLightSensor);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.hardware.Sensor getAmbientLightSensor(android.hardware.display.DisplayManagerInternal.AmbientLightSensorData ambientLightSensorData) {
        android.hardware.SensorManager sensorManager = (android.hardware.SensorManager) this.mContext.getSystemService(android.hardware.SensorManager.class);
        java.util.Objects.requireNonNull(sensorManager);
        if (DEBUG) {
            android.util.Slog.d(TAG, "Ambient Light sensor data: " + ambientLightSensorData);
        }
        return com.android.server.display.utils.SensorUtils.findSensor(sensorManager, ambientLightSensorData.sensorType, ambientLightSensorData.sensorName, 5);
    }

    private void updateSmoothedLux(float f) {
        if (this.mHysteresisState == com.android.server.input.AmbientKeyboardBacklightController.HysteresisState.IMMEDIATE) {
            this.mSmoothedLux = (int) f;
        } else {
            this.mSmoothedLux = (int) ((this.mSmoothingConstant * f) + ((1.0f - this.mSmoothingConstant) * this.mSmoothedLux));
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Current smoothed lux from ALS = " + this.mSmoothedLux);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void addSensorListener(@android.annotation.Nullable android.hardware.Sensor sensor) {
        android.hardware.SensorManager sensorManager = (android.hardware.SensorManager) this.mContext.getSystemService(android.hardware.SensorManager.class);
        if (sensorManager == null || sensor == null) {
            return;
        }
        reset();
        sensorManager.registerListener(this, sensor, 3, this.mHandler);
        if (DEBUG) {
            android.util.Slog.d(TAG, "Registering ALS listener");
        }
    }

    private void removeSensorListener(@android.annotation.Nullable android.hardware.Sensor sensor) {
        android.hardware.SensorManager sensorManager = (android.hardware.SensorManager) this.mContext.getSystemService(android.hardware.SensorManager.class);
        if (sensorManager == null || sensor == null) {
            return;
        }
        sensorManager.unregisterListener(this, sensor);
        if (DEBUG) {
            android.util.Slog.d(TAG, "Unregistering ALS listener");
        }
    }

    private void initConfiguration() {
        android.content.res.Resources resources = this.mContext.getResources();
        int[] intArray = resources.getIntArray(android.R.array.config_autoKeyboardBacklightBrightnessValues);
        int[] intArray2 = resources.getIntArray(android.R.array.config_autoKeyboardBacklightDecreaseLuxThreshold);
        int[] intArray3 = resources.getIntArray(android.R.array.config_autoKeyboardBacklightIncreaseLuxThreshold);
        if (intArray.length != intArray2.length || intArray2.length != intArray3.length) {
            throw new java.lang.IllegalArgumentException("The config files for auto keyboard backlight brightness must contain arrays of equal lengths");
        }
        int length = intArray.length;
        this.mBrightnessSteps = new com.android.server.input.AmbientKeyboardBacklightController.BrightnessStep[length];
        int i = 0;
        while (true) {
            int i2 = Integer.MIN_VALUE;
            if (i >= length) {
                break;
            }
            int i3 = intArray3[i] >= 0 ? intArray3[i] : Integer.MAX_VALUE;
            if (intArray2[i] >= 0) {
                i2 = intArray2[i];
            }
            this.mBrightnessSteps[i] = new com.android.server.input.AmbientKeyboardBacklightController.BrightnessStep(intArray[i], i3, i2);
            i++;
        }
        int length2 = this.mBrightnessSteps.length;
        if (length2 == 0 || this.mBrightnessSteps[0].mDecreaseLuxThreshold != Integer.MIN_VALUE || this.mBrightnessSteps[length2 - 1].mIncreaseLuxThreshold != Integer.MAX_VALUE) {
            throw new java.lang.IllegalArgumentException("The config files for auto keyboard backlight brightness must contain arrays of length > 0 and have -1 or Integer.MIN_VALUE as lower bound for decrease thresholds and -1 or Integer.MAX_VALUE as upper bound for increase thresholds");
        }
        android.util.TypedValue typedValue = new android.util.TypedValue();
        resources.getValue(android.R.dimen.chooser_preview_width, typedValue, true);
        this.mSmoothingConstant = typedValue.getFloat();
        if (this.mSmoothingConstant <= 0.0d || this.mSmoothingConstant > 1.0d) {
            throw new java.lang.IllegalArgumentException("The config files for auto keyboard backlight brightness must contain smoothing constant in range (0.0, 1.0].");
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "Brightness steps: " + java.util.Arrays.toString(this.mBrightnessSteps) + " Smoothing constant = " + this.mSmoothingConstant);
        }
    }

    private void reset() {
        this.mHysteresisState = com.android.server.input.AmbientKeyboardBacklightController.HysteresisState.IMMEDIATE;
        this.mSmoothedLux = 0;
        this.mSmoothedLuxAtLastAdjustment = 0;
        this.mCurrentBrightnessStepIndex = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleMessage(android.os.Message message) {
        switch (message.what) {
            case 0:
                handleBrightnessCallback(((java.lang.Integer) message.obj).intValue());
                break;
            case 1:
                handleDisplayChange();
                break;
        }
        return true;
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
        handleAmbientLuxChange(sensorEvent.values[0]);
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {
        handleDisplayChange();
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {
        handleDisplayChange();
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
        handleDisplayChange();
    }

    private static class BrightnessStep {
        private final int mBrightnessValue;
        private final int mDecreaseLuxThreshold;
        private final int mIncreaseLuxThreshold;

        private BrightnessStep(int i, int i2, int i3) {
            this.mBrightnessValue = i;
            this.mIncreaseLuxThreshold = i2;
            this.mDecreaseLuxThreshold = i3;
        }

        public java.lang.String toString() {
            return "BrightnessStep{mBrightnessValue=" + this.mBrightnessValue + ", mIncreaseThreshold=" + this.mIncreaseLuxThreshold + ", mDecreaseThreshold=" + this.mDecreaseLuxThreshold + '}';
        }
    }
}
