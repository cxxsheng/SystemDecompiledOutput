package com.android.server.display;

/* loaded from: classes.dex */
public class ScreenOffBrightnessSensorController implements android.hardware.SensorEventListener {
    private static final int SENSOR_INVALID_VALUE = -1;
    private static final long SENSOR_VALUE_VALID_TIME_MILLIS = 1500;
    private static final java.lang.String TAG = "ScreenOffBrightnessSensorController";

    @android.annotation.Nullable
    private final com.android.server.display.BrightnessMappingStrategy mBrightnessMapper;
    private final com.android.server.display.ScreenOffBrightnessSensorController.Clock mClock;
    private final android.os.Handler mHandler;
    private final android.hardware.Sensor mLightSensor;
    private boolean mRegistered;
    private final android.hardware.SensorManager mSensorManager;
    private final int[] mSensorValueToLux;
    private int mLastSensorValue = -1;
    private long mSensorDisableTime = -1;

    @com.android.internal.annotations.VisibleForTesting
    interface Clock {
        long uptimeMillis();
    }

    public ScreenOffBrightnessSensorController(android.hardware.SensorManager sensorManager, android.hardware.Sensor sensor, android.os.Handler handler, com.android.server.display.ScreenOffBrightnessSensorController.Clock clock, int[] iArr, com.android.server.display.BrightnessMappingStrategy brightnessMappingStrategy) {
        this.mSensorManager = sensorManager;
        this.mLightSensor = sensor;
        this.mHandler = handler;
        this.mClock = clock;
        this.mSensorValueToLux = iArr;
        this.mBrightnessMapper = brightnessMappingStrategy;
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
        if (this.mRegistered) {
            this.mLastSensorValue = (int) sensorEvent.values[0];
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
    }

    void setLightSensorEnabled(boolean z) {
        if (z && !this.mRegistered) {
            this.mRegistered = this.mSensorManager.registerListener(this, this.mLightSensor, 3, this.mHandler);
            this.mLastSensorValue = -1;
        } else if (!z && this.mRegistered) {
            this.mSensorManager.unregisterListener(this);
            this.mRegistered = false;
            this.mSensorDisableTime = this.mClock.uptimeMillis();
        }
    }

    void stop() {
        setLightSensorEnabled(false);
    }

    float getAutomaticScreenBrightness() {
        int i;
        if (this.mLastSensorValue < 0 || this.mLastSensorValue >= this.mSensorValueToLux.length || ((!this.mRegistered && this.mClock.uptimeMillis() - this.mSensorDisableTime > SENSOR_VALUE_VALID_TIME_MILLIS) || (i = this.mSensorValueToLux[this.mLastSensorValue]) < 0)) {
            return Float.NaN;
        }
        return this.mBrightnessMapper.getBrightness(i);
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("Screen Off Brightness Sensor Controller:");
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("registered=" + this.mRegistered);
        indentingPrintWriter.println("lastSensorValue=" + this.mLastSensorValue);
    }
}
