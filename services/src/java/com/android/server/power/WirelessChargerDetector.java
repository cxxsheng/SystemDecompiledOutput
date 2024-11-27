package com.android.server.power;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public class WirelessChargerDetector {
    private static final boolean DEBUG = false;
    private static final double MAX_GRAVITY = 10.806650161743164d;
    private static final double MIN_GRAVITY = 8.806650161743164d;
    private static final int MIN_SAMPLES = 3;
    private static final double MOVEMENT_ANGLE_COS_THRESHOLD = java.lang.Math.cos(0.08726646259971647d);
    private static final int SAMPLING_INTERVAL_MILLIS = 50;
    private static final long SETTLE_TIME_MILLIS = 800;
    private static final java.lang.String TAG = "WirelessChargerDetector";
    private boolean mAtRest;
    private boolean mDetectionInProgress;
    private long mDetectionStartTime;
    private float mFirstSampleX;
    private float mFirstSampleY;
    private float mFirstSampleZ;
    private android.hardware.Sensor mGravitySensor;
    private final android.os.Handler mHandler;
    private float mLastSampleX;
    private float mLastSampleY;
    private float mLastSampleZ;
    private int mMovingSamples;
    private boolean mMustUpdateRestPosition;
    private boolean mPoweredWirelessly;
    private float mRestX;
    private float mRestY;
    private float mRestZ;
    private final android.hardware.SensorManager mSensorManager;
    private final com.android.server.power.SuspendBlocker mSuspendBlocker;
    private int mTotalSamples;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.hardware.SensorEventListener mListener = new android.hardware.SensorEventListener() { // from class: com.android.server.power.WirelessChargerDetector.1
        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
            synchronized (com.android.server.power.WirelessChargerDetector.this.mLock) {
                com.android.server.power.WirelessChargerDetector.this.processSampleLocked(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
            }
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
        }
    };
    private final java.lang.Runnable mSensorTimeout = new java.lang.Runnable() { // from class: com.android.server.power.WirelessChargerDetector.2
        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.power.WirelessChargerDetector.this.mLock) {
                com.android.server.power.WirelessChargerDetector.this.finishDetectionLocked();
            }
        }
    };

    public WirelessChargerDetector(android.hardware.SensorManager sensorManager, com.android.server.power.SuspendBlocker suspendBlocker, android.os.Handler handler) {
        this.mSensorManager = sensorManager;
        this.mSuspendBlocker = suspendBlocker;
        this.mHandler = handler;
        this.mGravitySensor = sensorManager.getDefaultSensor(9);
    }

    public void dump(java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.println();
                printWriter.println("Wireless Charger Detector State:");
                printWriter.println("  mGravitySensor=" + this.mGravitySensor);
                printWriter.println("  mPoweredWirelessly=" + this.mPoweredWirelessly);
                printWriter.println("  mAtRest=" + this.mAtRest);
                printWriter.println("  mRestX=" + this.mRestX + ", mRestY=" + this.mRestY + ", mRestZ=" + this.mRestZ);
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("  mDetectionInProgress=");
                sb.append(this.mDetectionInProgress);
                printWriter.println(sb.toString());
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                sb2.append("  mDetectionStartTime=");
                sb2.append(this.mDetectionStartTime == 0 ? "0 (never)" : android.util.TimeUtils.formatUptime(this.mDetectionStartTime));
                printWriter.println(sb2.toString());
                printWriter.println("  mMustUpdateRestPosition=" + this.mMustUpdateRestPosition);
                printWriter.println("  mTotalSamples=" + this.mTotalSamples);
                printWriter.println("  mMovingSamples=" + this.mMovingSamples);
                printWriter.println("  mFirstSampleX=" + this.mFirstSampleX + ", mFirstSampleY=" + this.mFirstSampleY + ", mFirstSampleZ=" + this.mFirstSampleZ);
                printWriter.println("  mLastSampleX=" + this.mLastSampleX + ", mLastSampleY=" + this.mLastSampleY + ", mLastSampleZ=" + this.mLastSampleZ);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        synchronized (this.mLock) {
            protoOutputStream.write(1133871366145L, this.mPoweredWirelessly);
            protoOutputStream.write(1133871366146L, this.mAtRest);
            long start2 = protoOutputStream.start(1146756268035L);
            protoOutputStream.write(1108101562369L, this.mRestX);
            protoOutputStream.write(1108101562370L, this.mRestY);
            protoOutputStream.write(1108101562371L, this.mRestZ);
            protoOutputStream.end(start2);
            protoOutputStream.write(1133871366148L, this.mDetectionInProgress);
            protoOutputStream.write(1112396529669L, this.mDetectionStartTime);
            protoOutputStream.write(1133871366150L, this.mMustUpdateRestPosition);
            protoOutputStream.write(1120986464263L, this.mTotalSamples);
            protoOutputStream.write(1120986464264L, this.mMovingSamples);
            long start3 = protoOutputStream.start(1146756268041L);
            protoOutputStream.write(1108101562369L, this.mFirstSampleX);
            protoOutputStream.write(1108101562370L, this.mFirstSampleY);
            protoOutputStream.write(1108101562371L, this.mFirstSampleZ);
            protoOutputStream.end(start3);
            long start4 = protoOutputStream.start(1146756268042L);
            protoOutputStream.write(1108101562369L, this.mLastSampleX);
            protoOutputStream.write(1108101562370L, this.mLastSampleY);
            protoOutputStream.write(1108101562371L, this.mLastSampleZ);
            protoOutputStream.end(start4);
        }
        protoOutputStream.end(start);
    }

    public boolean update(boolean z, int i) {
        boolean z2;
        synchronized (this.mLock) {
            try {
                boolean z3 = this.mPoweredWirelessly;
                z2 = false;
                if (z && i == 4) {
                    this.mPoweredWirelessly = true;
                    this.mMustUpdateRestPosition = true;
                    startDetectionLocked();
                } else {
                    this.mPoweredWirelessly = false;
                    if (this.mAtRest) {
                        if (i != 0 && i != 4) {
                            this.mMustUpdateRestPosition = false;
                            clearAtRestLocked();
                        } else {
                            startDetectionLocked();
                        }
                    }
                }
                if (this.mPoweredWirelessly && !z3 && !this.mAtRest) {
                    z2 = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z2;
    }

    private void startDetectionLocked() {
        if (!this.mDetectionInProgress && this.mGravitySensor != null && this.mSensorManager.registerListener(this.mListener, this.mGravitySensor, 50000)) {
            this.mSuspendBlocker.acquire();
            this.mDetectionInProgress = true;
            this.mDetectionStartTime = android.os.SystemClock.uptimeMillis();
            this.mTotalSamples = 0;
            this.mMovingSamples = 0;
            android.os.Message obtain = android.os.Message.obtain(this.mHandler, this.mSensorTimeout);
            obtain.setAsynchronous(true);
            this.mHandler.sendMessageDelayed(obtain, SETTLE_TIME_MILLIS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishDetectionLocked() {
        if (this.mDetectionInProgress) {
            this.mSensorManager.unregisterListener(this.mListener);
            this.mHandler.removeCallbacks(this.mSensorTimeout);
            if (this.mMustUpdateRestPosition) {
                clearAtRestLocked();
                if (this.mTotalSamples < 3) {
                    android.util.Slog.w(TAG, "Wireless charger detector is broken.  Only received " + this.mTotalSamples + " samples from the gravity sensor but we need at least 3 and we expect to see about 16 on average.");
                } else if (this.mMovingSamples == 0) {
                    this.mAtRest = true;
                    this.mRestX = this.mLastSampleX;
                    this.mRestY = this.mLastSampleY;
                    this.mRestZ = this.mLastSampleZ;
                }
                this.mMustUpdateRestPosition = false;
            }
            this.mDetectionInProgress = false;
            this.mSuspendBlocker.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processSampleLocked(float f, float f2, float f3) {
        if (this.mDetectionInProgress) {
            this.mLastSampleX = f;
            this.mLastSampleY = f2;
            this.mLastSampleZ = f3;
            this.mTotalSamples++;
            if (this.mTotalSamples == 1) {
                this.mFirstSampleX = f;
                this.mFirstSampleY = f2;
                this.mFirstSampleZ = f3;
            } else if (hasMoved(this.mFirstSampleX, this.mFirstSampleY, this.mFirstSampleZ, f, f2, f3)) {
                this.mMovingSamples++;
            }
            if (this.mAtRest && hasMoved(this.mRestX, this.mRestY, this.mRestZ, f, f2, f3)) {
                clearAtRestLocked();
            }
        }
    }

    private void clearAtRestLocked() {
        this.mAtRest = false;
        this.mRestX = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mRestY = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mRestZ = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    }

    private static boolean hasMoved(float f, float f2, float f3, float f4, float f5, float f6) {
        double d = (f * f4) + (f2 * f5) + (f3 * f6);
        double sqrt = java.lang.Math.sqrt((f * f) + (f2 * f2) + (f3 * f3));
        double sqrt2 = java.lang.Math.sqrt((f4 * f4) + (f5 * f5) + (f6 * f6));
        return sqrt < MIN_GRAVITY || sqrt > MAX_GRAVITY || sqrt2 < MIN_GRAVITY || sqrt2 > MAX_GRAVITY || d < (sqrt * sqrt2) * MOVEMENT_ANGLE_COS_THRESHOLD;
    }
}
