package com.android.server.sensorprivacy;

/* loaded from: classes2.dex */
class CameraPrivacyLightController implements android.app.AppOpsManager.OnOpActiveChangedListener, android.hardware.SensorEventListener {
    private static final double LIGHT_VALUE_MULTIPLIER = 1.0d / java.lang.Math.log(1.1d);
    private final java.util.Set<java.lang.String> mActivePackages;
    private final java.util.Set<java.lang.String> mActivePhonePackages;
    private long mAlvSum;
    private final java.util.ArrayDeque<android.util.Pair<java.lang.Long, java.lang.Integer>> mAmbientLightValues;
    private final android.app.AppOpsManager mAppOpsManager;
    private final java.util.List<android.hardware.lights.Light> mCameraLights;
    private final int[] mColors;
    private final android.content.Context mContext;
    private final java.lang.Object mDelayedUpdateToken;
    private long mElapsedRealTime;
    private long mElapsedTimeStartedReading;
    private final java.util.concurrent.Executor mExecutor;
    private final android.os.Handler mHandler;
    private boolean mIsAmbientLightListenerRegistered;
    private int mLastLightColor;
    private final android.hardware.Sensor mLightSensor;
    private final android.hardware.lights.LightsManager mLightsManager;
    private android.hardware.lights.LightsManager.LightsSession mLightsSession;
    private final long mMovingAverageIntervalMillis;
    private final android.hardware.SensorManager mSensorManager;
    private final long[] mThresholds;

    CameraPrivacyLightController(android.content.Context context) {
        this(context, com.android.server.FgThread.get().getLooper());
    }

    @com.android.internal.annotations.VisibleForTesting
    CameraPrivacyLightController(android.content.Context context, android.os.Looper looper) {
        this.mActivePackages = new android.util.ArraySet();
        this.mActivePhonePackages = new android.util.ArraySet();
        this.mCameraLights = new java.util.ArrayList();
        this.mLightsSession = null;
        this.mIsAmbientLightListenerRegistered = false;
        this.mAmbientLightValues = new java.util.ArrayDeque<>();
        this.mAlvSum = 0L;
        this.mLastLightColor = 0;
        this.mDelayedUpdateToken = new java.lang.Object();
        this.mElapsedRealTime = -1L;
        this.mColors = context.getResources().getIntArray(android.R.array.config_cameraPrivacyLightColors);
        if (com.android.internal.util.ArrayUtils.isEmpty(this.mColors)) {
            this.mHandler = null;
            this.mExecutor = null;
            this.mContext = null;
            this.mAppOpsManager = null;
            this.mLightsManager = null;
            this.mSensorManager = null;
            this.mLightSensor = null;
            this.mMovingAverageIntervalMillis = 0L;
            this.mThresholds = null;
            return;
        }
        this.mContext = context;
        this.mHandler = new android.os.Handler(looper);
        this.mExecutor = new android.os.HandlerExecutor(this.mHandler);
        this.mAppOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        this.mLightsManager = (android.hardware.lights.LightsManager) this.mContext.getSystemService(android.hardware.lights.LightsManager.class);
        this.mSensorManager = (android.hardware.SensorManager) this.mContext.getSystemService(android.hardware.SensorManager.class);
        this.mMovingAverageIntervalMillis = this.mContext.getResources().getInteger(android.R.integer.config_cameraLiftTriggerSensorType);
        int[] intArray = this.mContext.getResources().getIntArray(android.R.array.config_cameraPrivacyLightAlsLuxThresholds);
        if (intArray.length != this.mColors.length - 1) {
            throw new java.lang.IllegalStateException("There must be exactly one more color than thresholds. Found " + this.mColors.length + " colors and " + intArray.length + " thresholds.");
        }
        this.mThresholds = new long[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            this.mThresholds[i] = (long) (java.lang.Math.log(intArray[i]) * LIGHT_VALUE_MULTIPLIER);
        }
        java.util.List<android.hardware.lights.Light> lights = this.mLightsManager.getLights();
        for (int i2 = 0; i2 < lights.size(); i2++) {
            android.hardware.lights.Light light = lights.get(i2);
            if (light.getType() == 9) {
                this.mCameraLights.add(light);
            }
        }
        if (this.mCameraLights.isEmpty()) {
            this.mLightSensor = null;
        } else {
            this.mAppOpsManager.startWatchingActive(new java.lang.String[]{"android:camera", "android:phone_call_camera"}, this.mExecutor, this);
            this.mLightSensor = this.mSensorManager.getDefaultSensor(5);
        }
    }

    private void addElement(long j, int i) {
        if (this.mAmbientLightValues.isEmpty()) {
            this.mAmbientLightValues.add(new android.util.Pair<>(java.lang.Long.valueOf((j - getCurrentIntervalMillis()) - 1), java.lang.Integer.valueOf(i)));
        }
        android.util.Pair<java.lang.Long, java.lang.Integer> peekLast = this.mAmbientLightValues.peekLast();
        this.mAmbientLightValues.add(new android.util.Pair<>(java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i)));
        this.mAlvSum += (j - ((java.lang.Long) peekLast.first).longValue()) * ((java.lang.Integer) peekLast.second).intValue();
        removeObsoleteData(j);
    }

    private void removeObsoleteData(long j) {
        while (this.mAmbientLightValues.size() > 1) {
            android.util.Pair<java.lang.Long, java.lang.Integer> pollFirst = this.mAmbientLightValues.pollFirst();
            android.util.Pair<java.lang.Long, java.lang.Integer> peekFirst = this.mAmbientLightValues.peekFirst();
            if (((java.lang.Long) peekFirst.first).longValue() > j - getCurrentIntervalMillis()) {
                this.mAmbientLightValues.addFirst(pollFirst);
                return;
            }
            this.mAlvSum -= (((java.lang.Long) peekFirst.first).longValue() - ((java.lang.Long) pollFirst.first).longValue()) * ((java.lang.Integer) pollFirst.second).intValue();
        }
    }

    private long getLiveAmbientLightTotal() {
        if (this.mAmbientLightValues.isEmpty()) {
            return this.mAlvSum;
        }
        long elapsedRealTime = getElapsedRealTime();
        removeObsoleteData(elapsedRealTime);
        return (this.mAlvSum - (java.lang.Math.max(0L, (elapsedRealTime - getCurrentIntervalMillis()) - ((java.lang.Long) this.mAmbientLightValues.peekFirst().first).longValue()) * ((java.lang.Integer) r2.second).intValue())) + ((elapsedRealTime - ((java.lang.Long) this.mAmbientLightValues.peekLast().first).longValue()) * ((java.lang.Integer) r3.second).intValue());
    }

    @Override // android.app.AppOpsManager.OnOpActiveChangedListener
    public void onOpActiveChanged(java.lang.String str, int i, java.lang.String str2, boolean z) {
        java.util.Set<java.lang.String> set;
        if ("android:camera".equals(str)) {
            set = this.mActivePackages;
        } else if ("android:phone_call_camera".equals(str)) {
            set = this.mActivePhonePackages;
        } else {
            return;
        }
        if (z) {
            set.add(str2);
        } else {
            set.remove(str2);
        }
        updateLightSession();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLightSession() {
        if (android.os.Looper.myLooper() != this.mHandler.getLooper()) {
            this.mHandler.post(new com.android.server.sensorprivacy.CameraPrivacyLightController$$ExternalSyntheticLambda0(this));
            return;
        }
        java.util.Set indicatorExemptedPackages = android.permission.PermissionManager.getIndicatorExemptedPackages(this.mContext);
        boolean z = indicatorExemptedPackages.containsAll(this.mActivePackages) && indicatorExemptedPackages.containsAll(this.mActivePhonePackages);
        updateSensorListener(z);
        if (z) {
            if (this.mLightsSession == null) {
                return;
            }
            this.mLightsSession.close();
            this.mLightsSession = null;
            return;
        }
        int computeCurrentLightColor = this.mLightSensor == null ? this.mColors[this.mColors.length - 1] : computeCurrentLightColor();
        if (this.mLastLightColor == computeCurrentLightColor && this.mLightsSession != null) {
            return;
        }
        this.mLastLightColor = computeCurrentLightColor;
        android.hardware.lights.LightsRequest.Builder builder = new android.hardware.lights.LightsRequest.Builder();
        for (int i = 0; i < this.mCameraLights.size(); i++) {
            builder.addLight(this.mCameraLights.get(i), new android.hardware.lights.LightState.Builder().setColor(computeCurrentLightColor).build());
        }
        if (this.mLightsSession == null) {
            this.mLightsSession = this.mLightsManager.openSession(Integer.MAX_VALUE);
        }
        this.mLightsSession.requestLights(builder.build());
    }

    private int computeCurrentLightColor() {
        long liveAmbientLightTotal = getLiveAmbientLightTotal();
        long currentIntervalMillis = getCurrentIntervalMillis();
        for (int i = 0; i < this.mThresholds.length; i++) {
            if (liveAmbientLightTotal < this.mThresholds[i] * currentIntervalMillis) {
                return this.mColors[i];
            }
        }
        return this.mColors[this.mColors.length - 1];
    }

    private void updateSensorListener(boolean z) {
        if (z && this.mIsAmbientLightListenerRegistered) {
            this.mSensorManager.unregisterListener(this);
            this.mIsAmbientLightListenerRegistered = false;
        }
        if (!z && !this.mIsAmbientLightListenerRegistered && this.mLightSensor != null) {
            this.mSensorManager.registerListener(this, this.mLightSensor, 3, this.mHandler);
            this.mIsAmbientLightListenerRegistered = true;
            this.mElapsedTimeStartedReading = getElapsedRealTime();
        }
    }

    private long getElapsedRealTime() {
        return this.mElapsedRealTime == -1 ? android.os.SystemClock.elapsedRealtime() : this.mElapsedRealTime;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setElapsedRealTime(long j) {
        this.mElapsedRealTime = j;
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
        addElement(java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(sensorEvent.timestamp), java.lang.Math.max(0, (int) (java.lang.Math.log(sensorEvent.values[0]) * LIGHT_VALUE_MULTIPLIER)));
        updateLightSession();
        this.mHandler.removeCallbacksAndMessages(this.mDelayedUpdateToken);
        this.mHandler.postDelayed(new com.android.server.sensorprivacy.CameraPrivacyLightController$$ExternalSyntheticLambda0(this), this.mDelayedUpdateToken, this.mMovingAverageIntervalMillis);
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
    }

    private long getCurrentIntervalMillis() {
        return java.lang.Math.min(this.mMovingAverageIntervalMillis, getElapsedRealTime() - this.mElapsedTimeStartedReading);
    }
}
