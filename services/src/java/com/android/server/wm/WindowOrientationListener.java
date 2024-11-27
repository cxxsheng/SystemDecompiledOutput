package com.android.server.wm;

/* loaded from: classes3.dex */
public abstract class WindowOrientationListener {
    private static final int DEFAULT_BATCH_LATENCY = 100000;
    private static final long DEFAULT_ROTATION_MEMORIZATION_TIMEOUT_MILLIS = 3000;
    private static final long DEFAULT_ROTATION_RESOLVER_TIMEOUT_MILLIS = 700;
    private static final java.lang.String KEY_ROTATION_MEMORIZATION_TIMEOUT = "rotation_memorization_timeout_millis";
    private static final java.lang.String KEY_ROTATION_RESOLVER_TIMEOUT = "rotation_resolver_timeout_millis";
    private static final boolean LOG = android.os.SystemProperties.getBoolean("debug.orientation.log", false);
    private static final java.lang.String TAG = "WindowOrientationListener";
    private static final boolean USE_GRAVITY_SENSOR = false;
    private final android.content.Context mContext;
    private int mCurrentRotation;
    private final int mDefaultRotation;
    private boolean mEnabled;
    private android.os.Handler mHandler;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.WindowOrientationListener.OrientationJudge mOrientationJudge;
    private int mRate;

    @com.android.internal.annotations.VisibleForTesting
    android.rotationresolver.RotationResolverInternal mRotationResolverService;
    private android.hardware.Sensor mSensor;
    private android.hardware.SensorManager mSensorManager;
    private java.lang.String mSensorType;

    public abstract boolean isKeyguardShowingAndNotOccluded();

    @com.android.internal.annotations.VisibleForTesting
    abstract boolean isRotationResolverEnabled();

    public abstract void onProposedRotationChanged(int i);

    public WindowOrientationListener(android.content.Context context, android.os.Handler handler, int i) {
        this(context, handler, i, 2);
    }

    private WindowOrientationListener(android.content.Context context, android.os.Handler handler, int i, int i2) {
        this.mCurrentRotation = -1;
        this.mLock = new java.lang.Object();
        this.mContext = context;
        this.mHandler = handler;
        this.mDefaultRotation = i;
        this.mSensorManager = (android.hardware.SensorManager) context.getSystemService("sensor");
        this.mRate = i2;
        android.hardware.Sensor sensor = null;
        android.hardware.Sensor sensor2 = null;
        for (android.hardware.Sensor sensor3 : this.mSensorManager.getSensorList(27)) {
            if (sensor3.isWakeUpSensor()) {
                sensor = sensor3;
            } else {
                sensor2 = sensor3;
            }
        }
        if (sensor != null) {
            this.mSensor = sensor;
        } else {
            this.mSensor = sensor2;
        }
        if (this.mSensor != null) {
            this.mOrientationJudge = new com.android.server.wm.WindowOrientationListener.OrientationSensorJudge();
        }
        if (this.mOrientationJudge == null) {
            this.mSensor = this.mSensorManager.getDefaultSensor(1);
            if (this.mSensor != null) {
                this.mOrientationJudge = new com.android.server.wm.WindowOrientationListener.AccelSensorJudge(context);
            }
        }
    }

    public void enable() {
        enable(true);
    }

    public void enable(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mSensor == null) {
                    android.util.Slog.w(TAG, "Cannot detect sensors. Not enabled");
                    return;
                }
                if (this.mEnabled) {
                    return;
                }
                if (LOG) {
                    android.util.Slog.d(TAG, "WindowOrientationListener enabled clearCurrentRotation=" + z);
                }
                this.mOrientationJudge.resetLocked(z);
                if (this.mSensor.getType() == 1) {
                    this.mSensorManager.registerListener(this.mOrientationJudge, this.mSensor, this.mRate, DEFAULT_BATCH_LATENCY, this.mHandler);
                } else {
                    this.mSensorManager.registerListener(this.mOrientationJudge, this.mSensor, this.mRate, this.mHandler);
                }
                this.mEnabled = true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void disable() {
        synchronized (this.mLock) {
            try {
                if (this.mSensor == null) {
                    android.util.Slog.w(TAG, "Cannot detect sensors. Invalid disable");
                    return;
                }
                if (this.mEnabled) {
                    if (LOG) {
                        android.util.Slog.d(TAG, "WindowOrientationListener disabled");
                    }
                    this.mSensorManager.unregisterListener(this.mOrientationJudge);
                    this.mEnabled = false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onTouchStart() {
        synchronized (this.mLock) {
            try {
                if (this.mOrientationJudge != null) {
                    this.mOrientationJudge.onTouchStartLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onTouchEnd() {
        long elapsedRealtimeNanos = android.os.SystemClock.elapsedRealtimeNanos();
        synchronized (this.mLock) {
            try {
                if (this.mOrientationJudge != null) {
                    this.mOrientationJudge.onTouchEndLocked(elapsedRealtimeNanos);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.os.Handler getHandler() {
        return this.mHandler;
    }

    public void setCurrentRotation(int i) {
        synchronized (this.mLock) {
            this.mCurrentRotation = i;
        }
    }

    public int getProposedRotation() {
        synchronized (this.mLock) {
            try {
                if (!this.mEnabled) {
                    return -1;
                }
                return this.mOrientationJudge.getProposedRotationLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean canDetectOrientation() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSensor != null;
        }
        return z;
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        synchronized (this.mLock) {
            protoOutputStream.write(1133871366145L, this.mEnabled);
            protoOutputStream.write(1159641169922L, this.mCurrentRotation);
        }
        protoOutputStream.end(start);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        synchronized (this.mLock) {
            try {
                printWriter.println(str + TAG);
                java.lang.String str2 = str + "  ";
                printWriter.println(str2 + "mEnabled=" + this.mEnabled);
                printWriter.println(str2 + "mCurrentRotation=" + android.view.Surface.rotationToString(this.mCurrentRotation));
                printWriter.println(str2 + "mSensorType=" + this.mSensorType);
                printWriter.println(str2 + "mSensor=" + this.mSensor);
                printWriter.println(str2 + "mRate=" + this.mRate);
                if (this.mOrientationJudge != null) {
                    this.mOrientationJudge.dumpLocked(printWriter, str2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean shouldStayEnabledWhileDreaming() {
        if (this.mContext.getResources().getBoolean(android.R.bool.config_flexibleSplitRatios)) {
            return true;
        }
        return this.mSensor.getType() == 27 && this.mSensor.isWakeUpSensor();
    }

    abstract class OrientationJudge implements android.hardware.SensorEventListener {
        protected static final float MILLIS_PER_NANO = 1.0E-6f;
        protected static final long NANOS_PER_MS = 1000000;
        protected static final long PROPOSAL_MIN_TIME_SINCE_TOUCH_END_NANOS = 500000000;

        public abstract void dumpLocked(java.io.PrintWriter printWriter, java.lang.String str);

        public abstract int getProposedRotationLocked();

        @Override // android.hardware.SensorEventListener
        public abstract void onAccuracyChanged(android.hardware.Sensor sensor, int i);

        @Override // android.hardware.SensorEventListener
        public abstract void onSensorChanged(android.hardware.SensorEvent sensorEvent);

        public abstract void onTouchEndLocked(long j);

        public abstract void onTouchStartLocked();

        public abstract void resetLocked(boolean z);

        OrientationJudge() {
        }
    }

    final class AccelSensorJudge extends com.android.server.wm.WindowOrientationListener.OrientationJudge {
        private static final float ACCELERATION_TOLERANCE = 4.0f;
        private static final int ACCELEROMETER_DATA_X = 0;
        private static final int ACCELEROMETER_DATA_Y = 1;
        private static final int ACCELEROMETER_DATA_Z = 2;
        private static final int ADJACENT_ORIENTATION_ANGLE_GAP = 45;
        private static final float FILTER_TIME_CONSTANT_MS = 200.0f;
        private static final float FLAT_ANGLE = 80.0f;
        private static final long FLAT_TIME_NANOS = 1000000000;
        private static final float MAX_ACCELERATION_MAGNITUDE = 13.80665f;
        private static final long MAX_FILTER_DELTA_TIME_NANOS = 1000000000;
        private static final int MAX_TILT = 80;
        private static final float MIN_ACCELERATION_MAGNITUDE = 5.80665f;
        private static final float NEAR_ZERO_MAGNITUDE = 1.0f;
        private static final long PROPOSAL_MIN_TIME_SINCE_ACCELERATION_ENDED_NANOS = 500000000;
        private static final long PROPOSAL_MIN_TIME_SINCE_FLAT_ENDED_NANOS = 500000000;
        private static final long PROPOSAL_MIN_TIME_SINCE_SWING_ENDED_NANOS = 300000000;
        private static final long PROPOSAL_SETTLE_TIME_NANOS = 40000000;
        private static final float RADIANS_TO_DEGREES = 57.29578f;
        private static final float SWING_AWAY_ANGLE_DELTA = 20.0f;
        private static final long SWING_TIME_NANOS = 300000000;
        private static final int TILT_HISTORY_SIZE = 200;
        private static final int TILT_OVERHEAD_ENTER = -40;
        private static final int TILT_OVERHEAD_EXIT = -15;
        private boolean mAccelerating;
        private long mAccelerationTimestampNanos;
        private boolean mFlat;
        private long mFlatTimestampNanos;
        private long mLastFilteredTimestampNanos;
        private float mLastFilteredX;
        private float mLastFilteredY;
        private float mLastFilteredZ;
        private boolean mOverhead;
        private int mPredictedRotation;
        private long mPredictedRotationTimestampNanos;
        private int mProposedRotation;
        private long mSwingTimestampNanos;
        private boolean mSwinging;
        private float[] mTiltHistory;
        private int mTiltHistoryIndex;
        private long[] mTiltHistoryTimestampNanos;
        private final int[][] mTiltToleranceConfig;
        private long mTouchEndedTimestampNanos;
        private boolean mTouched;

        public AccelSensorJudge(android.content.Context context) {
            super();
            this.mTiltToleranceConfig = new int[][]{new int[]{-25, 70}, new int[]{-25, 65}, new int[]{-25, 60}, new int[]{-25, 65}};
            this.mTouchEndedTimestampNanos = Long.MIN_VALUE;
            this.mTiltHistory = new float[200];
            this.mTiltHistoryTimestampNanos = new long[200];
            int[] intArray = context.getResources().getIntArray(android.R.array.config_autoRotationTiltTolerance);
            if (intArray.length == 8) {
                for (int i = 0; i < 4; i++) {
                    int i2 = i * 2;
                    int i3 = intArray[i2];
                    int i4 = intArray[i2 + 1];
                    if (i3 >= -90 && i3 <= i4 && i4 <= 90) {
                        this.mTiltToleranceConfig[i][0] = i3;
                        this.mTiltToleranceConfig[i][1] = i4;
                    } else {
                        android.util.Slog.wtf(com.android.server.wm.WindowOrientationListener.TAG, "config_autoRotationTiltTolerance contains invalid range: min=" + i3 + ", max=" + i4);
                    }
                }
                return;
            }
            android.util.Slog.wtf(com.android.server.wm.WindowOrientationListener.TAG, "config_autoRotationTiltTolerance should have exactly 8 elements");
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public int getProposedRotationLocked() {
            return this.mProposedRotation;
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public void dumpLocked(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.println(str + "AccelSensorJudge");
            java.lang.String str2 = str + "  ";
            printWriter.println(str2 + "mProposedRotation=" + this.mProposedRotation);
            printWriter.println(str2 + "mPredictedRotation=" + this.mPredictedRotation);
            printWriter.println(str2 + "mLastFilteredX=" + this.mLastFilteredX);
            printWriter.println(str2 + "mLastFilteredY=" + this.mLastFilteredY);
            printWriter.println(str2 + "mLastFilteredZ=" + this.mLastFilteredZ);
            printWriter.println(str2 + "mLastFilteredTimestampNanos=" + this.mLastFilteredTimestampNanos + " (" + ((android.os.SystemClock.elapsedRealtimeNanos() - this.mLastFilteredTimestampNanos) * 1.0E-6f) + "ms ago)");
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(str2);
            sb.append("mTiltHistory={last: ");
            sb.append(getLastTiltLocked());
            sb.append("}");
            printWriter.println(sb.toString());
            printWriter.println(str2 + "mFlat=" + this.mFlat);
            printWriter.println(str2 + "mSwinging=" + this.mSwinging);
            printWriter.println(str2 + "mAccelerating=" + this.mAccelerating);
            printWriter.println(str2 + "mOverhead=" + this.mOverhead);
            printWriter.println(str2 + "mTouched=" + this.mTouched);
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
            sb2.append(str2);
            sb2.append("mTiltToleranceConfig=[");
            printWriter.print(sb2.toString());
            for (int i = 0; i < 4; i++) {
                if (i != 0) {
                    printWriter.print(", ");
                }
                printWriter.print("[");
                printWriter.print(this.mTiltToleranceConfig[i][0]);
                printWriter.print(", ");
                printWriter.print(this.mTiltToleranceConfig[i][1]);
                printWriter.print("]");
            }
            printWriter.println("]");
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge, android.hardware.SensorEventListener
        public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
        }

        /* JADX WARN: Removed duplicated region for block: B:54:0x0276 A[Catch: all -> 0x005c, TryCatch #0 {all -> 0x005c, blocks: (B:4:0x000b, B:6:0x0020, B:7:0x005f, B:9:0x006e, B:17:0x0084, B:19:0x00a4, B:21:0x00f2, B:23:0x00ff, B:25:0x0113, B:27:0x0119, B:28:0x0120, B:29:0x0125, B:31:0x012c, B:32:0x0131, B:34:0x014d, B:35:0x0152, B:37:0x0159, B:40:0x0162, B:41:0x016b, B:43:0x016f, B:45:0x0175, B:46:0x018b, B:48:0x0258, B:50:0x0264, B:52:0x026e, B:54:0x0276, B:55:0x032c, B:67:0x026a, B:68:0x0192, B:70:0x019a, B:72:0x01a0, B:73:0x01b6, B:74:0x01bd, B:76:0x01cf, B:77:0x01d1, B:80:0x01db, B:82:0x01e1, B:84:0x01e7, B:86:0x01f0, B:87:0x022a, B:89:0x0230, B:90:0x024e, B:94:0x0169, B:100:0x00e1, B:102:0x00e7, B:103:0x00ee), top: B:3:0x000b }] */
        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge, android.hardware.SensorEventListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            int i;
            int i2;
            boolean z5;
            boolean z6;
            boolean z7;
            int i3;
            synchronized (com.android.server.wm.WindowOrientationListener.this.mLock) {
                try {
                    float f = sensorEvent.values[0];
                    float f2 = sensorEvent.values[1];
                    float f3 = sensorEvent.values[2];
                    if (com.android.server.wm.WindowOrientationListener.LOG) {
                        android.util.Slog.v(com.android.server.wm.WindowOrientationListener.TAG, "Raw acceleration vector: x=" + f + ", y=" + f2 + ", z=" + f3 + ", magnitude=" + java.lang.Math.sqrt((f * f) + (f2 * f2) + (f3 * f3)));
                    }
                    long j = sensorEvent.timestamp;
                    long j2 = this.mLastFilteredTimestampNanos;
                    float f4 = (j - j2) * 1.0E-6f;
                    if (j < j2 || j > j2 + 1000000000 || (f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f2 == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f3 == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE)) {
                        if (com.android.server.wm.WindowOrientationListener.LOG) {
                            android.util.Slog.v(com.android.server.wm.WindowOrientationListener.TAG, "Resetting orientation listener.");
                        }
                        resetLocked(true);
                        z = true;
                    } else {
                        float f5 = f4 / (FILTER_TIME_CONSTANT_MS + f4);
                        f = ((f - this.mLastFilteredX) * f5) + this.mLastFilteredX;
                        f2 = ((f2 - this.mLastFilteredY) * f5) + this.mLastFilteredY;
                        f3 = this.mLastFilteredZ + (f5 * (f3 - this.mLastFilteredZ));
                        if (com.android.server.wm.WindowOrientationListener.LOG) {
                            android.util.Slog.v(com.android.server.wm.WindowOrientationListener.TAG, "Filtered acceleration vector: x=" + f + ", y=" + f2 + ", z=" + f3 + ", magnitude=" + java.lang.Math.sqrt((f * f) + (f2 * f2) + (f3 * f3)));
                        }
                        z = false;
                    }
                    this.mLastFilteredTimestampNanos = j;
                    this.mLastFilteredX = f;
                    this.mLastFilteredY = f2;
                    this.mLastFilteredZ = f3;
                    if (!z) {
                        float sqrt = (float) java.lang.Math.sqrt((f * f) + (f2 * f2) + (f3 * f3));
                        if (sqrt < 1.0f) {
                            if (com.android.server.wm.WindowOrientationListener.LOG) {
                                android.util.Slog.v(com.android.server.wm.WindowOrientationListener.TAG, "Ignoring sensor data, magnitude too close to zero.");
                            }
                            clearPredictedRotationLocked();
                        } else {
                            if (!isAcceleratingLocked(sqrt)) {
                                z4 = false;
                            } else {
                                this.mAccelerationTimestampNanos = j;
                                z4 = true;
                            }
                            int round = (int) java.lang.Math.round(java.lang.Math.asin(f3 / sqrt) * 57.295780181884766d);
                            float f6 = round;
                            addTiltHistoryEntryLocked(j, f6);
                            if (!isFlatLocked(j)) {
                                z5 = false;
                            } else {
                                this.mFlatTimestampNanos = j;
                                z5 = true;
                            }
                            if (!isSwingingLocked(j, f6)) {
                                z6 = false;
                            } else {
                                this.mSwingTimestampNanos = j;
                                z6 = true;
                            }
                            if (round <= TILT_OVERHEAD_ENTER) {
                                this.mOverhead = true;
                            } else if (round >= -15) {
                                this.mOverhead = false;
                            }
                            if (this.mOverhead) {
                                if (com.android.server.wm.WindowOrientationListener.LOG) {
                                    android.util.Slog.v(com.android.server.wm.WindowOrientationListener.TAG, "Ignoring sensor data, device is overhead: tiltAngle=" + round);
                                }
                                clearPredictedRotationLocked();
                                z7 = z5;
                            } else if (java.lang.Math.abs(round) > 80) {
                                if (com.android.server.wm.WindowOrientationListener.LOG) {
                                    android.util.Slog.v(com.android.server.wm.WindowOrientationListener.TAG, "Ignoring sensor data, tilt angle too high: tiltAngle=" + round);
                                }
                                clearPredictedRotationLocked();
                                z7 = z5;
                            } else {
                                z7 = z5;
                                int round2 = (int) java.lang.Math.round((-java.lang.Math.atan2(-f, f2)) * 57.295780181884766d);
                                if (round2 < 0) {
                                    round2 += 360;
                                }
                                int i4 = (round2 + 45) / 90;
                                if (i4 != 4) {
                                    i3 = i4;
                                } else {
                                    i3 = 0;
                                }
                                if (isTiltAngleAcceptableLocked(i3, round) && isOrientationAngleAcceptableLocked(i3, round2)) {
                                    updatePredictedRotationLocked(j, i3);
                                    if (com.android.server.wm.WindowOrientationListener.LOG) {
                                        android.util.Slog.v(com.android.server.wm.WindowOrientationListener.TAG, "Predicted: tiltAngle=" + round + ", orientationAngle=" + round2 + ", predictedRotation=" + this.mPredictedRotation + ", predictedRotationAgeMS=" + ((j - this.mPredictedRotationTimestampNanos) * 1.0E-6f));
                                    }
                                } else {
                                    if (com.android.server.wm.WindowOrientationListener.LOG) {
                                        android.util.Slog.v(com.android.server.wm.WindowOrientationListener.TAG, "Ignoring sensor data, no predicted rotation: tiltAngle=" + round + ", orientationAngle=" + round2);
                                    }
                                    clearPredictedRotationLocked();
                                }
                            }
                            z3 = z7;
                            z2 = z6;
                            this.mFlat = z3;
                            this.mSwinging = z2;
                            this.mAccelerating = z4;
                            i = this.mProposedRotation;
                            if (this.mPredictedRotation >= 0 || isPredictedRotationAcceptableLocked(j)) {
                                this.mProposedRotation = this.mPredictedRotation;
                            }
                            i2 = this.mProposedRotation;
                            if (com.android.server.wm.WindowOrientationListener.LOG) {
                                android.util.Slog.v(com.android.server.wm.WindowOrientationListener.TAG, "Result: currentRotation=" + com.android.server.wm.WindowOrientationListener.this.mCurrentRotation + ", proposedRotation=" + i2 + ", predictedRotation=" + this.mPredictedRotation + ", timeDeltaMS=" + f4 + ", isAccelerating=" + z4 + ", isFlat=" + z3 + ", isSwinging=" + z2 + ", isOverhead=" + this.mOverhead + ", isTouched=" + this.mTouched + ", timeUntilSettledMS=" + remainingMS(j, this.mPredictedRotationTimestampNanos + PROPOSAL_SETTLE_TIME_NANOS) + ", timeUntilAccelerationDelayExpiredMS=" + remainingMS(j, this.mAccelerationTimestampNanos + 500000000) + ", timeUntilFlatDelayExpiredMS=" + remainingMS(j, this.mFlatTimestampNanos + 500000000) + ", timeUntilSwingDelayExpiredMS=" + remainingMS(j, this.mSwingTimestampNanos + 300000000) + ", timeUntilTouchDelayExpiredMS=" + remainingMS(j, this.mTouchEndedTimestampNanos + 500000000));
                            }
                        }
                    }
                    z2 = false;
                    z3 = false;
                    z4 = false;
                    this.mFlat = z3;
                    this.mSwinging = z2;
                    this.mAccelerating = z4;
                    i = this.mProposedRotation;
                    if (this.mPredictedRotation >= 0) {
                    }
                    this.mProposedRotation = this.mPredictedRotation;
                    i2 = this.mProposedRotation;
                    if (com.android.server.wm.WindowOrientationListener.LOG) {
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (i2 != i && i2 >= 0) {
                if (com.android.server.wm.WindowOrientationListener.LOG) {
                    android.util.Slog.v(com.android.server.wm.WindowOrientationListener.TAG, "Proposed rotation changed!  proposedRotation=" + i2 + ", oldProposedRotation=" + i);
                }
                com.android.server.wm.WindowOrientationListener.this.onProposedRotationChanged(i2);
            }
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public void onTouchStartLocked() {
            this.mTouched = true;
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public void onTouchEndLocked(long j) {
            this.mTouched = false;
            this.mTouchEndedTimestampNanos = j;
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public void resetLocked(boolean z) {
            this.mLastFilteredTimestampNanos = Long.MIN_VALUE;
            if (z) {
                this.mProposedRotation = -1;
            }
            this.mFlatTimestampNanos = Long.MIN_VALUE;
            this.mFlat = false;
            this.mSwingTimestampNanos = Long.MIN_VALUE;
            this.mSwinging = false;
            this.mAccelerationTimestampNanos = Long.MIN_VALUE;
            this.mAccelerating = false;
            this.mOverhead = false;
            clearPredictedRotationLocked();
            clearTiltHistoryLocked();
        }

        private boolean isTiltAngleAcceptableLocked(int i, int i2) {
            return i2 >= this.mTiltToleranceConfig[i][0] && i2 <= this.mTiltToleranceConfig[i][1];
        }

        private boolean isOrientationAngleAcceptableLocked(int i, int i2) {
            int i3 = com.android.server.wm.WindowOrientationListener.this.mCurrentRotation;
            if (i3 >= 0) {
                if (i == i3 || i == (i3 + 1) % 4) {
                    int i4 = ((i * 90) - 45) + 22;
                    if (i == 0) {
                        if (i2 >= 315 && i2 < i4 + 360) {
                            return false;
                        }
                    } else if (i2 < i4) {
                        return false;
                    }
                }
                if (i == i3 || i == (i3 + 3) % 4) {
                    int i5 = ((i * 90) + 45) - 22;
                    return i == 0 ? i2 > 45 || i2 <= i5 : i2 <= i5;
                }
                return true;
            }
            return true;
        }

        private boolean isPredictedRotationAcceptableLocked(long j) {
            return j >= this.mPredictedRotationTimestampNanos + PROPOSAL_SETTLE_TIME_NANOS && j >= this.mFlatTimestampNanos + 500000000 && j >= this.mSwingTimestampNanos + 300000000 && j >= this.mAccelerationTimestampNanos + 500000000 && !this.mTouched && j >= this.mTouchEndedTimestampNanos + 500000000;
        }

        private void clearPredictedRotationLocked() {
            this.mPredictedRotation = -1;
            this.mPredictedRotationTimestampNanos = Long.MIN_VALUE;
        }

        private void updatePredictedRotationLocked(long j, int i) {
            if (this.mPredictedRotation != i) {
                this.mPredictedRotation = i;
                this.mPredictedRotationTimestampNanos = j;
            }
        }

        private boolean isAcceleratingLocked(float f) {
            return f < MIN_ACCELERATION_MAGNITUDE || f > MAX_ACCELERATION_MAGNITUDE;
        }

        private void clearTiltHistoryLocked() {
            this.mTiltHistoryTimestampNanos[0] = Long.MIN_VALUE;
            this.mTiltHistoryIndex = 1;
        }

        private void addTiltHistoryEntryLocked(long j, float f) {
            this.mTiltHistory[this.mTiltHistoryIndex] = f;
            this.mTiltHistoryTimestampNanos[this.mTiltHistoryIndex] = j;
            this.mTiltHistoryIndex = (this.mTiltHistoryIndex + 1) % 200;
            this.mTiltHistoryTimestampNanos[this.mTiltHistoryIndex] = Long.MIN_VALUE;
        }

        private boolean isFlatLocked(long j) {
            int i = this.mTiltHistoryIndex;
            do {
                i = nextTiltHistoryIndexLocked(i);
                if (i < 0 || this.mTiltHistory[i] < FLAT_ANGLE) {
                    return false;
                }
            } while (this.mTiltHistoryTimestampNanos[i] + 1000000000 > j);
            return true;
        }

        private boolean isSwingingLocked(long j, float f) {
            int i = this.mTiltHistoryIndex;
            do {
                i = nextTiltHistoryIndexLocked(i);
                if (i < 0 || this.mTiltHistoryTimestampNanos[i] + 300000000 < j) {
                    return false;
                }
            } while (this.mTiltHistory[i] + SWING_AWAY_ANGLE_DELTA > f);
            return true;
        }

        private int nextTiltHistoryIndexLocked(int i) {
            if (i == 0) {
                i = 200;
            }
            int i2 = i - 1;
            if (this.mTiltHistoryTimestampNanos[i2] != Long.MIN_VALUE) {
                return i2;
            }
            return -1;
        }

        private float getLastTiltLocked() {
            int nextTiltHistoryIndexLocked = nextTiltHistoryIndexLocked(this.mTiltHistoryIndex);
            if (nextTiltHistoryIndexLocked >= 0) {
                return this.mTiltHistory[nextTiltHistoryIndexLocked];
            }
            return Float.NaN;
        }

        private float remainingMS(long j, long j2) {
            return j >= j2 ? com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE : (j2 - j) * 1.0E-6f;
        }
    }

    final class OrientationSensorJudge extends com.android.server.wm.WindowOrientationListener.OrientationJudge {
        private static final int ROTATION_UNSET = -1;
        private final com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;
        private java.lang.Runnable mCancelRotationResolverRequest;
        private int mCurrentCallbackId;
        private int mDesiredRotation;
        private int mLastRotationResolution;
        private long mLastRotationResolutionTimeStamp;
        private int mProposedRotation;
        private boolean mRotationEvaluationScheduled;
        private java.lang.Runnable mRotationEvaluator;
        private long mRotationMemorizationTimeoutMillis;
        private long mRotationResolverTimeoutMillis;
        private long mTouchEndedTimestampNanos;
        private boolean mTouching;

        OrientationSensorJudge() {
            super();
            this.mTouchEndedTimestampNanos = Long.MIN_VALUE;
            this.mProposedRotation = -1;
            this.mDesiredRotation = -1;
            this.mLastRotationResolution = -1;
            this.mCurrentCallbackId = 0;
            this.mRotationEvaluator = new java.lang.Runnable() { // from class: com.android.server.wm.WindowOrientationListener.OrientationSensorJudge.2
                @Override // java.lang.Runnable
                public void run() {
                    int evaluateRotationChangeLocked;
                    synchronized (com.android.server.wm.WindowOrientationListener.this.mLock) {
                        com.android.server.wm.WindowOrientationListener.OrientationSensorJudge.this.mRotationEvaluationScheduled = false;
                        evaluateRotationChangeLocked = com.android.server.wm.WindowOrientationListener.OrientationSensorJudge.this.evaluateRotationChangeLocked();
                    }
                    if (evaluateRotationChangeLocked >= 0) {
                        com.android.server.wm.WindowOrientationListener.this.onProposedRotationChanged(evaluateRotationChangeLocked);
                    }
                }
            };
            setupRotationResolverParameters();
            this.mActivityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
        }

        private void setupRotationResolverParameters() {
            android.provider.DeviceConfig.addOnPropertiesChangedListener("window_manager", android.app.ActivityThread.currentApplication().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.wm.WindowOrientationListener$OrientationSensorJudge$$ExternalSyntheticLambda1
                public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                    com.android.server.wm.WindowOrientationListener.OrientationSensorJudge.this.lambda$setupRotationResolverParameters$0(properties);
                }
            });
            readRotationResolverParameters();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setupRotationResolverParameters$0(android.provider.DeviceConfig.Properties properties) {
            java.util.Set keyset = properties.getKeyset();
            if (keyset.contains(com.android.server.wm.WindowOrientationListener.KEY_ROTATION_RESOLVER_TIMEOUT) || keyset.contains(com.android.server.wm.WindowOrientationListener.KEY_ROTATION_MEMORIZATION_TIMEOUT)) {
                readRotationResolverParameters();
            }
        }

        private void readRotationResolverParameters() {
            this.mRotationResolverTimeoutMillis = android.provider.DeviceConfig.getLong("window_manager", com.android.server.wm.WindowOrientationListener.KEY_ROTATION_RESOLVER_TIMEOUT, com.android.server.wm.WindowOrientationListener.DEFAULT_ROTATION_RESOLVER_TIMEOUT_MILLIS);
            this.mRotationMemorizationTimeoutMillis = android.provider.DeviceConfig.getLong("window_manager", com.android.server.wm.WindowOrientationListener.KEY_ROTATION_MEMORIZATION_TIMEOUT, 3000L);
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public int getProposedRotationLocked() {
            return this.mProposedRotation;
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public void onTouchStartLocked() {
            this.mTouching = true;
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public void onTouchEndLocked(long j) {
            this.mTouching = false;
            this.mTouchEndedTimestampNanos = j;
            if (this.mDesiredRotation != this.mProposedRotation) {
                scheduleRotationEvaluationIfNecessaryLocked(android.os.SystemClock.elapsedRealtimeNanos());
            }
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge, android.hardware.SensorEventListener
        public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
            java.lang.String str;
            com.android.server.wm.WindowProcessController topApp;
            final int i = (int) sensorEvent.values[0];
            if (i < 0 || i > 3) {
                return;
            }
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DEVICE_ROTATED, sensorEvent.timestamp, rotationToLogEnum(i), 2);
            if (com.android.server.wm.WindowOrientationListener.this.isRotationResolverEnabled()) {
                if (com.android.server.wm.WindowOrientationListener.this.isKeyguardShowingAndNotOccluded()) {
                    if (this.mLastRotationResolution != -1 && android.os.SystemClock.uptimeMillis() - this.mLastRotationResolutionTimeStamp < this.mRotationMemorizationTimeoutMillis) {
                        android.util.Slog.d(com.android.server.wm.WindowOrientationListener.TAG, "Reusing the last rotation resolution: " + this.mLastRotationResolution);
                        finalizeRotation(this.mLastRotationResolution);
                        return;
                    }
                    finalizeRotation(com.android.server.wm.WindowOrientationListener.this.mDefaultRotation);
                    return;
                }
                if (com.android.server.wm.WindowOrientationListener.this.mRotationResolverService == null) {
                    com.android.server.wm.WindowOrientationListener.this.mRotationResolverService = (android.rotationresolver.RotationResolverInternal) com.android.server.LocalServices.getService(android.rotationresolver.RotationResolverInternal.class);
                    if (com.android.server.wm.WindowOrientationListener.this.mRotationResolverService == null) {
                        finalizeRotation(i);
                        return;
                    }
                }
                if (this.mActivityTaskManagerInternal != null && (topApp = this.mActivityTaskManagerInternal.getTopApp()) != null && topApp.mInfo != null && topApp.mInfo.packageName != null) {
                    str = topApp.mInfo.packageName;
                } else {
                    str = null;
                }
                this.mCurrentCallbackId++;
                if (this.mCancelRotationResolverRequest != null) {
                    com.android.server.wm.WindowOrientationListener.this.getHandler().removeCallbacks(this.mCancelRotationResolverRequest);
                }
                final android.os.CancellationSignal cancellationSignal = new android.os.CancellationSignal();
                java.util.Objects.requireNonNull(cancellationSignal);
                this.mCancelRotationResolverRequest = new java.lang.Runnable() { // from class: com.android.server.wm.WindowOrientationListener$OrientationSensorJudge$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        cancellationSignal.cancel();
                    }
                };
                com.android.server.wm.WindowOrientationListener.this.getHandler().postDelayed(this.mCancelRotationResolverRequest, this.mRotationResolverTimeoutMillis);
                com.android.server.wm.WindowOrientationListener.this.mRotationResolverService.resolveRotation(new android.rotationresolver.RotationResolverInternal.RotationResolverCallbackInternal() { // from class: com.android.server.wm.WindowOrientationListener.OrientationSensorJudge.1
                    private final int mCallbackId;

                    {
                        this.mCallbackId = com.android.server.wm.WindowOrientationListener.OrientationSensorJudge.this.mCurrentCallbackId;
                    }

                    public void onSuccess(int i2) {
                        finalizeRotationIfFresh(i2);
                    }

                    public void onFailure(int i2) {
                        finalizeRotationIfFresh(i);
                    }

                    private void finalizeRotationIfFresh(int i2) {
                        if (this.mCallbackId == com.android.server.wm.WindowOrientationListener.OrientationSensorJudge.this.mCurrentCallbackId) {
                            com.android.server.wm.WindowOrientationListener.this.getHandler().removeCallbacks(com.android.server.wm.WindowOrientationListener.OrientationSensorJudge.this.mCancelRotationResolverRequest);
                            com.android.server.wm.WindowOrientationListener.OrientationSensorJudge.this.finalizeRotation(i2);
                        } else {
                            android.util.Slog.d(com.android.server.wm.WindowOrientationListener.TAG, java.lang.String.format("An outdated callback received [%s vs. %s]. Ignoring it.", java.lang.Integer.valueOf(this.mCallbackId), java.lang.Integer.valueOf(com.android.server.wm.WindowOrientationListener.OrientationSensorJudge.this.mCurrentCallbackId)));
                        }
                    }
                }, str, i, com.android.server.wm.WindowOrientationListener.this.mCurrentRotation, this.mRotationResolverTimeoutMillis, cancellationSignal);
                return;
            }
            finalizeRotation(i);
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge, android.hardware.SensorEventListener
        public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public void dumpLocked(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.println(str + "OrientationSensorJudge");
            java.lang.String str2 = str + "  ";
            printWriter.println(str2 + "mDesiredRotation=" + android.view.Surface.rotationToString(this.mDesiredRotation));
            printWriter.println(str2 + "mProposedRotation=" + android.view.Surface.rotationToString(this.mProposedRotation));
            printWriter.println(str2 + "mTouching=" + this.mTouching);
            printWriter.println(str2 + "mTouchEndedTimestampNanos=" + this.mTouchEndedTimestampNanos);
            printWriter.println(str2 + "mLastRotationResolution=" + this.mLastRotationResolution);
        }

        @Override // com.android.server.wm.WindowOrientationListener.OrientationJudge
        public void resetLocked(boolean z) {
            if (z) {
                this.mProposedRotation = -1;
                this.mDesiredRotation = -1;
            }
            this.mTouching = false;
            this.mTouchEndedTimestampNanos = Long.MIN_VALUE;
            unscheduleRotationEvaluationLocked();
        }

        public int evaluateRotationChangeLocked() {
            unscheduleRotationEvaluationLocked();
            if (this.mDesiredRotation == this.mProposedRotation) {
                return -1;
            }
            long elapsedRealtimeNanos = android.os.SystemClock.elapsedRealtimeNanos();
            if (isDesiredRotationAcceptableLocked(elapsedRealtimeNanos)) {
                this.mProposedRotation = this.mDesiredRotation;
                return this.mProposedRotation;
            }
            scheduleRotationEvaluationIfNecessaryLocked(elapsedRealtimeNanos);
            return -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void finalizeRotation(int i) {
            int evaluateRotationChangeLocked;
            synchronized (com.android.server.wm.WindowOrientationListener.this.mLock) {
                this.mDesiredRotation = i;
                evaluateRotationChangeLocked = evaluateRotationChangeLocked();
            }
            if (evaluateRotationChangeLocked >= 0) {
                this.mLastRotationResolution = evaluateRotationChangeLocked;
                this.mLastRotationResolutionTimeStamp = android.os.SystemClock.uptimeMillis();
                com.android.server.wm.WindowOrientationListener.this.onProposedRotationChanged(evaluateRotationChangeLocked);
            }
        }

        private boolean isDesiredRotationAcceptableLocked(long j) {
            return !this.mTouching && j >= this.mTouchEndedTimestampNanos + 500000000;
        }

        private void scheduleRotationEvaluationIfNecessaryLocked(long j) {
            if (this.mRotationEvaluationScheduled || this.mDesiredRotation == this.mProposedRotation) {
                if (com.android.server.wm.WindowOrientationListener.LOG) {
                    android.util.Slog.d(com.android.server.wm.WindowOrientationListener.TAG, "scheduleRotationEvaluationLocked: ignoring, an evaluation is already scheduled or is unnecessary.");
                }
            } else {
                if (this.mTouching) {
                    if (com.android.server.wm.WindowOrientationListener.LOG) {
                        android.util.Slog.d(com.android.server.wm.WindowOrientationListener.TAG, "scheduleRotationEvaluationLocked: ignoring, user is still touching the screen.");
                        return;
                    }
                    return;
                }
                if (j >= this.mTouchEndedTimestampNanos + 500000000) {
                    if (com.android.server.wm.WindowOrientationListener.LOG) {
                        android.util.Slog.d(com.android.server.wm.WindowOrientationListener.TAG, "scheduleRotationEvaluationLocked: ignoring, already past the next possible time of rotation.");
                    }
                } else {
                    com.android.server.wm.WindowOrientationListener.this.mHandler.postDelayed(this.mRotationEvaluator, (long) java.lang.Math.ceil((r2 - j) * 1.0E-6f));
                    this.mRotationEvaluationScheduled = true;
                }
            }
        }

        private void unscheduleRotationEvaluationLocked() {
            if (!this.mRotationEvaluationScheduled) {
                return;
            }
            com.android.server.wm.WindowOrientationListener.this.mHandler.removeCallbacks(this.mRotationEvaluator);
            this.mRotationEvaluationScheduled = false;
        }

        private int rotationToLogEnum(int i) {
            switch (i) {
                case 0:
                    return 1;
                case 1:
                    return 2;
                case 2:
                    return 3;
                case 3:
                    return 4;
                default:
                    return 0;
            }
        }
    }
}
