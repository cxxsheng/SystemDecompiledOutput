package com.android.server;

/* loaded from: classes.dex */
public class AnyMotionDetector {
    private static final long ACCELEROMETER_DATA_TIMEOUT_MILLIS = 3000;
    private static final boolean DEBUG = false;
    private static final long ORIENTATION_MEASUREMENT_DURATION_MILLIS = 2500;
    private static final long ORIENTATION_MEASUREMENT_INTERVAL_MILLIS = 5000;
    public static final int RESULT_MOVED = 1;
    public static final int RESULT_STATIONARY = 0;
    public static final int RESULT_UNKNOWN = -1;
    private static final int SAMPLING_INTERVAL_MILLIS = 40;
    private static final int STALE_MEASUREMENT_TIMEOUT_MILLIS = 120000;
    private static final int STATE_ACTIVE = 1;
    private static final int STATE_INACTIVE = 0;
    private static final java.lang.String TAG = "AnyMotionDetector";
    private static final long WAKELOCK_TIMEOUT_MILLIS = 30000;
    private final android.hardware.Sensor mAccelSensor;
    private final com.android.server.AnyMotionDetector.DeviceIdleCallback mCallback;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mMeasurementInProgress;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mMeasurementTimeoutIsActive;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mNumSufficientSamples;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.AnyMotionDetector.RunningSignalStats mRunningStats;
    private final android.hardware.SensorManager mSensorManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSensorRestartIsActive;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mState;
    private final float mThresholdAngle;
    private final android.os.PowerManager.WakeLock mWakeLock;
    private volatile boolean mWakelockTimeoutIsActive;
    private final float THRESHOLD_ENERGY = 5.0f;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.AnyMotionDetector.Vector3 mCurrentGravityVector = null;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.AnyMotionDetector.Vector3 mPreviousGravityVector = null;
    private final android.hardware.SensorEventListener mListener = new android.hardware.SensorEventListener() { // from class: com.android.server.AnyMotionDetector.1
        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
            int i;
            synchronized (com.android.server.AnyMotionDetector.this.mLock) {
                try {
                    com.android.server.AnyMotionDetector.this.mRunningStats.accumulate(new com.android.server.AnyMotionDetector.Vector3(android.os.SystemClock.elapsedRealtime(), sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]));
                    if (com.android.server.AnyMotionDetector.this.mRunningStats.getSampleCount() < com.android.server.AnyMotionDetector.this.mNumSufficientSamples) {
                        i = -1;
                    } else {
                        i = com.android.server.AnyMotionDetector.this.stopOrientationMeasurementLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (i != -1) {
                com.android.server.AnyMotionDetector.this.mHandler.removeCallbacks(com.android.server.AnyMotionDetector.this.mWakelockTimeout);
                com.android.server.AnyMotionDetector.this.mWakelockTimeoutIsActive = false;
                com.android.server.AnyMotionDetector.this.mCallback.onAnyMotionResult(i);
            }
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
        }
    };
    private final java.lang.Runnable mSensorRestart = new java.lang.Runnable() { // from class: com.android.server.AnyMotionDetector.2
        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.AnyMotionDetector.this.mLock) {
                try {
                    if (com.android.server.AnyMotionDetector.this.mSensorRestartIsActive) {
                        com.android.server.AnyMotionDetector.this.mSensorRestartIsActive = false;
                        com.android.server.AnyMotionDetector.this.startOrientationMeasurementLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    };
    private final java.lang.Runnable mMeasurementTimeout = new java.lang.Runnable() { // from class: com.android.server.AnyMotionDetector.3
        @Override // java.lang.Runnable
        public void run() {
            int i;
            synchronized (com.android.server.AnyMotionDetector.this.mLock) {
                try {
                    if (!com.android.server.AnyMotionDetector.this.mMeasurementTimeoutIsActive) {
                        i = -1;
                    } else {
                        com.android.server.AnyMotionDetector.this.mMeasurementTimeoutIsActive = false;
                        i = com.android.server.AnyMotionDetector.this.stopOrientationMeasurementLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (i != -1) {
                com.android.server.AnyMotionDetector.this.mHandler.removeCallbacks(com.android.server.AnyMotionDetector.this.mWakelockTimeout);
                com.android.server.AnyMotionDetector.this.mWakelockTimeoutIsActive = false;
                com.android.server.AnyMotionDetector.this.mCallback.onAnyMotionResult(i);
            }
        }
    };
    private final java.lang.Runnable mWakelockTimeout = new java.lang.Runnable() { // from class: com.android.server.AnyMotionDetector.4
        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.AnyMotionDetector.this.mLock) {
                try {
                    if (com.android.server.AnyMotionDetector.this.mWakelockTimeoutIsActive) {
                        com.android.server.AnyMotionDetector.this.mWakelockTimeoutIsActive = false;
                        com.android.server.AnyMotionDetector.this.stop();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    };

    interface DeviceIdleCallback {
        void onAnyMotionResult(int i);
    }

    public AnyMotionDetector(android.os.PowerManager powerManager, android.os.Handler handler, android.hardware.SensorManager sensorManager, com.android.server.AnyMotionDetector.DeviceIdleCallback deviceIdleCallback, float f) {
        synchronized (this.mLock) {
            this.mWakeLock = powerManager.newWakeLock(1, TAG);
            this.mWakeLock.setReferenceCounted(false);
            this.mHandler = handler;
            this.mSensorManager = sensorManager;
            this.mAccelSensor = this.mSensorManager.getDefaultSensor(1);
            this.mMeasurementInProgress = false;
            this.mMeasurementTimeoutIsActive = false;
            this.mWakelockTimeoutIsActive = false;
            this.mSensorRestartIsActive = false;
            this.mState = 0;
            this.mCallback = deviceIdleCallback;
            this.mThresholdAngle = f;
            this.mRunningStats = new com.android.server.AnyMotionDetector.RunningSignalStats();
            this.mNumSufficientSamples = (int) java.lang.Math.ceil(62.5d);
        }
    }

    public boolean hasSensor() {
        return this.mAccelSensor != null;
    }

    public void checkForAnyMotion() {
        synchronized (this.mLock) {
            try {
                if (this.mState != 1) {
                    this.mState = 1;
                    this.mCurrentGravityVector = null;
                    this.mPreviousGravityVector = null;
                    this.mWakeLock.acquire();
                    this.mHandler.sendMessageDelayed(android.os.Message.obtain(this.mHandler, this.mWakelockTimeout), 30000L);
                    this.mWakelockTimeoutIsActive = true;
                    startOrientationMeasurementLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void stop() {
        synchronized (this.mLock) {
            try {
                if (this.mState == 1) {
                    this.mState = 0;
                }
                this.mHandler.removeCallbacks(this.mMeasurementTimeout);
                this.mHandler.removeCallbacks(this.mSensorRestart);
                this.mMeasurementTimeoutIsActive = false;
                this.mSensorRestartIsActive = false;
                if (this.mMeasurementInProgress) {
                    this.mMeasurementInProgress = false;
                    this.mSensorManager.unregisterListener(this.mListener);
                }
                this.mCurrentGravityVector = null;
                this.mPreviousGravityVector = null;
                if (this.mWakeLock.isHeld()) {
                    this.mHandler.removeCallbacks(this.mWakelockTimeout);
                    this.mWakelockTimeoutIsActive = false;
                    this.mWakeLock.release();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void startOrientationMeasurementLocked() {
        if (!this.mMeasurementInProgress && this.mAccelSensor != null) {
            if (this.mSensorManager.registerListener(this.mListener, this.mAccelSensor, com.android.server.EventLogTags.VOLUME_CHANGED)) {
                this.mMeasurementInProgress = true;
                this.mRunningStats.reset();
            }
            this.mHandler.sendMessageDelayed(android.os.Message.obtain(this.mHandler, this.mMeasurementTimeout), 3000L);
            this.mMeasurementTimeoutIsActive = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public int stopOrientationMeasurementLocked() {
        if (!this.mMeasurementInProgress) {
            return -1;
        }
        this.mHandler.removeCallbacks(this.mMeasurementTimeout);
        this.mMeasurementTimeoutIsActive = false;
        this.mSensorManager.unregisterListener(this.mListener);
        this.mMeasurementInProgress = false;
        this.mPreviousGravityVector = this.mCurrentGravityVector;
        this.mCurrentGravityVector = this.mRunningStats.getRunningAverage();
        if (this.mRunningStats.getSampleCount() == 0) {
            android.util.Slog.w(TAG, "No accelerometer data acquired for orientation measurement.");
        }
        int stationaryStatusLocked = getStationaryStatusLocked();
        this.mRunningStats.reset();
        if (stationaryStatusLocked != -1) {
            if (this.mWakeLock.isHeld()) {
                this.mHandler.removeCallbacks(this.mWakelockTimeout);
                this.mWakelockTimeoutIsActive = false;
                this.mWakeLock.release();
            }
            this.mState = 0;
        } else {
            this.mHandler.sendMessageDelayed(android.os.Message.obtain(this.mHandler, this.mSensorRestart), ORIENTATION_MEASUREMENT_INTERVAL_MILLIS);
            this.mSensorRestartIsActive = true;
        }
        return stationaryStatusLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int getStationaryStatusLocked() {
        if (this.mPreviousGravityVector == null || this.mCurrentGravityVector == null) {
            return -1;
        }
        float angleBetween = this.mPreviousGravityVector.normalized().angleBetween(this.mCurrentGravityVector.normalized());
        if (angleBetween >= this.mThresholdAngle || this.mRunningStats.getEnergy() >= 5.0f) {
            return (!java.lang.Float.isNaN(angleBetween) && this.mCurrentGravityVector.timeMillisSinceBoot - this.mPreviousGravityVector.timeMillisSinceBoot > 120000) ? -1 : 1;
        }
        return 0;
    }

    public static final class Vector3 {
        public long timeMillisSinceBoot;
        public float x;
        public float y;
        public float z;

        public Vector3(long j, float f, float f2, float f3) {
            this.timeMillisSinceBoot = j;
            this.x = f;
            this.y = f2;
            this.z = f3;
        }

        public float norm() {
            return (float) java.lang.Math.sqrt(dotProduct(this));
        }

        public com.android.server.AnyMotionDetector.Vector3 normalized() {
            float norm = norm();
            return new com.android.server.AnyMotionDetector.Vector3(this.timeMillisSinceBoot, this.x / norm, this.y / norm, this.z / norm);
        }

        public float angleBetween(com.android.server.AnyMotionDetector.Vector3 vector3) {
            float abs = java.lang.Math.abs((float) java.lang.Math.toDegrees(java.lang.Math.atan2(cross(vector3).norm(), dotProduct(vector3))));
            android.util.Slog.d(com.android.server.AnyMotionDetector.TAG, "angleBetween: this = " + toString() + ", other = " + vector3.toString() + ", degrees = " + abs);
            return abs;
        }

        public com.android.server.AnyMotionDetector.Vector3 cross(com.android.server.AnyMotionDetector.Vector3 vector3) {
            return new com.android.server.AnyMotionDetector.Vector3(vector3.timeMillisSinceBoot, (this.y * vector3.z) - (this.z * vector3.y), (this.z * vector3.x) - (this.x * vector3.z), (this.x * vector3.y) - (this.y * vector3.x));
        }

        public java.lang.String toString() {
            return ((("timeMillisSinceBoot=" + this.timeMillisSinceBoot) + " | x=" + this.x) + ", y=" + this.y) + ", z=" + this.z;
        }

        public float dotProduct(com.android.server.AnyMotionDetector.Vector3 vector3) {
            return (this.x * vector3.x) + (this.y * vector3.y) + (this.z * vector3.z);
        }

        public com.android.server.AnyMotionDetector.Vector3 times(float f) {
            return new com.android.server.AnyMotionDetector.Vector3(this.timeMillisSinceBoot, this.x * f, this.y * f, this.z * f);
        }

        public com.android.server.AnyMotionDetector.Vector3 plus(com.android.server.AnyMotionDetector.Vector3 vector3) {
            return new com.android.server.AnyMotionDetector.Vector3(vector3.timeMillisSinceBoot, vector3.x + this.x, vector3.y + this.y, this.z + vector3.z);
        }

        public com.android.server.AnyMotionDetector.Vector3 minus(com.android.server.AnyMotionDetector.Vector3 vector3) {
            return new com.android.server.AnyMotionDetector.Vector3(vector3.timeMillisSinceBoot, this.x - vector3.x, this.y - vector3.y, this.z - vector3.z);
        }
    }

    private static class RunningSignalStats {
        com.android.server.AnyMotionDetector.Vector3 currentVector;
        float energy;
        com.android.server.AnyMotionDetector.Vector3 previousVector;
        com.android.server.AnyMotionDetector.Vector3 runningSum;
        int sampleCount;

        public RunningSignalStats() {
            reset();
        }

        public void reset() {
            this.previousVector = null;
            this.currentVector = null;
            this.runningSum = new com.android.server.AnyMotionDetector.Vector3(0L, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
            this.energy = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            this.sampleCount = 0;
        }

        public void accumulate(com.android.server.AnyMotionDetector.Vector3 vector3) {
            if (vector3 == null) {
                return;
            }
            this.sampleCount++;
            this.runningSum = this.runningSum.plus(vector3);
            this.previousVector = this.currentVector;
            this.currentVector = vector3;
            if (this.previousVector != null) {
                com.android.server.AnyMotionDetector.Vector3 minus = this.currentVector.minus(this.previousVector);
                this.energy += (minus.x * minus.x) + (minus.y * minus.y) + (minus.z * minus.z);
            }
        }

        @android.annotation.Nullable
        public com.android.server.AnyMotionDetector.Vector3 getRunningAverage() {
            if (this.sampleCount > 0) {
                return this.runningSum.times(1.0f / this.sampleCount);
            }
            return null;
        }

        public float getEnergy() {
            return this.energy;
        }

        public int getSampleCount() {
            return this.sampleCount;
        }

        public java.lang.String toString() {
            java.lang.String vector3 = this.currentVector == null ? "null" : this.currentVector.toString();
            return ((("previousVector = " + (this.previousVector != null ? this.previousVector.toString() : "null")) + ", currentVector = " + vector3) + ", sampleCount = " + this.sampleCount) + ", energy = " + this.energy;
        }
    }
}
