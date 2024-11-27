package com.android.server.display;

/* loaded from: classes.dex */
public final class DisplayPowerProximityStateController {
    private static final boolean DEBUG_PRETEND_PROXIMITY_SENSOR_ABSENT = false;
    private static final int MSG_IGNORE_PROXIMITY = 2;

    @com.android.internal.annotations.VisibleForTesting
    static final int MSG_PROXIMITY_SENSOR_DEBOUNCED = 1;
    private static final int PROXIMITY_NEGATIVE = 0;

    @com.android.internal.annotations.VisibleForTesting
    static final int PROXIMITY_POSITIVE = 1;
    private static final int PROXIMITY_SENSOR_NEGATIVE_DEBOUNCE_DELAY = 250;

    @com.android.internal.annotations.VisibleForTesting
    static final int PROXIMITY_SENSOR_POSITIVE_DEBOUNCE_DELAY = 0;

    @com.android.internal.annotations.VisibleForTesting
    static final int PROXIMITY_UNKNOWN = -1;
    private static final float TYPICAL_PROXIMITY_THRESHOLD = 5.0f;
    private com.android.server.display.DisplayPowerProximityStateController.Clock mClock;
    private com.android.server.display.DisplayDeviceConfig mDisplayDeviceConfig;
    private int mDisplayId;
    private final com.android.server.display.DisplayPowerProximityStateController.DisplayPowerProximityStateHandler mHandler;
    private boolean mIgnoreProximityUntilChanged;
    private final java.lang.Runnable mNudgeUpdatePowerState;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mPendingWaitForNegativeProximityLocked;
    private android.hardware.Sensor mProximitySensor;
    private boolean mProximitySensorEnabled;
    private float mProximityThreshold;
    private boolean mScreenOffBecauseOfProximity;
    private final android.hardware.SensorManager mSensorManager;
    private final java.lang.String mTag;
    private boolean mWaitingForNegativeProximity;
    private final com.android.server.display.WakelockController mWakelockController;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.hardware.SensorEventListener mProximitySensorListener = new android.hardware.SensorEventListener() { // from class: com.android.server.display.DisplayPowerProximityStateController.1
        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
            if (com.android.server.display.DisplayPowerProximityStateController.this.mProximitySensorEnabled) {
                long uptimeMillis = com.android.server.display.DisplayPowerProximityStateController.this.mClock.uptimeMillis();
                boolean z = false;
                float f = sensorEvent.values[0];
                if (f >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && f < com.android.server.display.DisplayPowerProximityStateController.this.mProximityThreshold) {
                    z = true;
                }
                com.android.server.display.DisplayPowerProximityStateController.this.handleProximitySensorEvent(uptimeMillis, z);
            }
        }

        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
        }
    };
    private int mPendingProximity = -1;
    private long mPendingProximityDebounceTime = -1;
    private int mProximity = -1;
    private boolean mSkipRampBecauseOfProximityChangeToNegative = false;

    @com.android.internal.annotations.VisibleForTesting
    interface Clock {
        long uptimeMillis();
    }

    public DisplayPowerProximityStateController(com.android.server.display.WakelockController wakelockController, com.android.server.display.DisplayDeviceConfig displayDeviceConfig, android.os.Looper looper, java.lang.Runnable runnable, int i, android.hardware.SensorManager sensorManager, com.android.server.display.DisplayPowerProximityStateController.Injector injector) {
        this.mClock = (injector == null ? new com.android.server.display.DisplayPowerProximityStateController.Injector() : injector).createClock();
        this.mWakelockController = wakelockController;
        this.mHandler = new com.android.server.display.DisplayPowerProximityStateController.DisplayPowerProximityStateHandler(looper);
        this.mNudgeUpdatePowerState = runnable;
        this.mDisplayDeviceConfig = displayDeviceConfig;
        this.mDisplayId = i;
        this.mTag = "DisplayPowerProximityStateController[" + this.mDisplayId + "]";
        this.mSensorManager = sensorManager;
        loadProximitySensor();
    }

    public void updatePendingProximityRequestsLocked() {
        synchronized (this.mLock) {
            try {
                this.mWaitingForNegativeProximity |= this.mPendingWaitForNegativeProximityLocked;
                this.mPendingWaitForNegativeProximityLocked = false;
                if (this.mIgnoreProximityUntilChanged) {
                    this.mWaitingForNegativeProximity = false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void cleanup() {
        setProximitySensorEnabled(false);
    }

    public boolean isProximitySensorAvailable() {
        return this.mProximitySensor != null;
    }

    public boolean setPendingWaitForNegativeProximityLocked(boolean z) {
        synchronized (this.mLock) {
            if (z) {
                try {
                    if (!this.mPendingWaitForNegativeProximityLocked) {
                        this.mPendingWaitForNegativeProximityLocked = true;
                        return true;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return false;
        }
    }

    public void updateProximityState(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, int i) {
        this.mSkipRampBecauseOfProximityChangeToNegative = false;
        if (this.mProximitySensor != null) {
            if (displayPowerRequest.useProximitySensor && i != 1) {
                setProximitySensorEnabled(true);
                if (!this.mScreenOffBecauseOfProximity && this.mProximity == 1 && !this.mIgnoreProximityUntilChanged) {
                    this.mScreenOffBecauseOfProximity = true;
                    sendOnProximityPositiveWithWakelock();
                }
            } else if (this.mWaitingForNegativeProximity && this.mScreenOffBecauseOfProximity && this.mProximity == 1 && i != 1) {
                setProximitySensorEnabled(true);
            } else {
                setProximitySensorEnabled(false);
                this.mWaitingForNegativeProximity = false;
            }
            if (this.mScreenOffBecauseOfProximity) {
                if (this.mProximity != 1 || this.mIgnoreProximityUntilChanged) {
                    this.mScreenOffBecauseOfProximity = false;
                    this.mSkipRampBecauseOfProximityChangeToNegative = true;
                    sendOnProximityNegativeWithWakelock();
                    return;
                }
                return;
            }
            return;
        }
        setProximitySensorEnabled(false);
        this.mWaitingForNegativeProximity = false;
        this.mIgnoreProximityUntilChanged = false;
        if (this.mScreenOffBecauseOfProximity) {
            this.mScreenOffBecauseOfProximity = false;
            this.mSkipRampBecauseOfProximityChangeToNegative = true;
            sendOnProximityNegativeWithWakelock();
        }
    }

    public boolean shouldSkipRampBecauseOfProximityChangeToNegative() {
        return this.mSkipRampBecauseOfProximityChangeToNegative;
    }

    public boolean isScreenOffBecauseOfProximity() {
        return this.mScreenOffBecauseOfProximity;
    }

    public void ignoreProximitySensorUntilChanged() {
        this.mHandler.sendEmptyMessage(2);
    }

    public void notifyDisplayDeviceChanged(com.android.server.display.DisplayDeviceConfig displayDeviceConfig) {
        this.mDisplayDeviceConfig = displayDeviceConfig;
        loadProximitySensor();
    }

    public void dumpLocal(java.io.PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("DisplayPowerProximityStateController:");
        synchronized (this.mLock) {
            printWriter.println("  mPendingWaitForNegativeProximityLocked=" + this.mPendingWaitForNegativeProximityLocked);
        }
        printWriter.println("  mDisplayId=" + this.mDisplayId);
        printWriter.println("  mWaitingForNegativeProximity=" + this.mWaitingForNegativeProximity);
        printWriter.println("  mIgnoreProximityUntilChanged=" + this.mIgnoreProximityUntilChanged);
        printWriter.println("  mProximitySensor=" + this.mProximitySensor);
        printWriter.println("  mProximitySensorEnabled=" + this.mProximitySensorEnabled);
        printWriter.println("  mProximityThreshold=" + this.mProximityThreshold);
        printWriter.println("  mProximity=" + proximityToString(this.mProximity));
        printWriter.println("  mPendingProximity=" + proximityToString(this.mPendingProximity));
        printWriter.println("  mPendingProximityDebounceTime=" + android.util.TimeUtils.formatUptime(this.mPendingProximityDebounceTime));
        printWriter.println("  mScreenOffBecauseOfProximity=" + this.mScreenOffBecauseOfProximity);
        printWriter.println("  mSkipRampBecauseOfProximityChangeToNegative=" + this.mSkipRampBecauseOfProximityChangeToNegative);
    }

    void ignoreProximitySensorUntilChangedInternal() {
        if (!this.mIgnoreProximityUntilChanged && this.mProximity == 1) {
            this.mIgnoreProximityUntilChanged = true;
            android.util.Slog.i(this.mTag, "Ignoring proximity");
            this.mNudgeUpdatePowerState.run();
        }
    }

    private void sendOnProximityPositiveWithWakelock() {
        this.mWakelockController.acquireWakelock(1);
        this.mHandler.post(this.mWakelockController.getOnProximityPositiveRunnable());
    }

    private void sendOnProximityNegativeWithWakelock() {
        this.mWakelockController.acquireWakelock(2);
        this.mHandler.post(this.mWakelockController.getOnProximityNegativeRunnable());
    }

    private void loadProximitySensor() {
        if (this.mDisplayId != 0) {
            return;
        }
        this.mProximitySensor = com.android.server.display.utils.SensorUtils.findSensor(this.mSensorManager, this.mDisplayDeviceConfig.getProximitySensor(), 8);
        if (this.mProximitySensor != null) {
            this.mProximityThreshold = java.lang.Math.min(this.mProximitySensor.getMaximumRange(), TYPICAL_PROXIMITY_THRESHOLD);
        }
    }

    private void setProximitySensorEnabled(boolean z) {
        if (z) {
            if (!this.mProximitySensorEnabled) {
                this.mProximitySensorEnabled = true;
                this.mIgnoreProximityUntilChanged = false;
                this.mSensorManager.registerListener(this.mProximitySensorListener, this.mProximitySensor, 3, this.mHandler);
                return;
            }
            return;
        }
        if (this.mProximitySensorEnabled) {
            this.mProximitySensorEnabled = false;
            this.mProximity = -1;
            this.mIgnoreProximityUntilChanged = false;
            this.mPendingProximity = -1;
            this.mHandler.removeMessages(1);
            this.mSensorManager.unregisterListener(this.mProximitySensorListener);
            if (this.mWakelockController.releaseWakelock(3)) {
                this.mPendingProximityDebounceTime = -1L;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleProximitySensorEvent(long j, boolean z) {
        if (this.mProximitySensorEnabled) {
            if (this.mPendingProximity == 0 && !z) {
                return;
            }
            if (this.mPendingProximity == 1 && z) {
                return;
            }
            this.mHandler.removeMessages(1);
            if (z) {
                this.mPendingProximity = 1;
                this.mPendingProximityDebounceTime = j + 0;
                this.mWakelockController.acquireWakelock(3);
            } else {
                this.mPendingProximity = 0;
                this.mPendingProximityDebounceTime = j + 250;
                this.mWakelockController.acquireWakelock(3);
            }
            debounceProximitySensor();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void debounceProximitySensor() {
        if (this.mProximitySensorEnabled && this.mPendingProximity != -1 && this.mPendingProximityDebounceTime >= 0) {
            if (this.mPendingProximityDebounceTime <= this.mClock.uptimeMillis()) {
                if (this.mProximity != this.mPendingProximity) {
                    this.mIgnoreProximityUntilChanged = false;
                    android.util.Slog.i(this.mTag, "No longer ignoring proximity [" + this.mPendingProximity + "]");
                }
                this.mProximity = this.mPendingProximity;
                this.mNudgeUpdatePowerState.run();
                if (this.mWakelockController.releaseWakelock(3)) {
                    this.mPendingProximityDebounceTime = -1L;
                    return;
                }
                return;
            }
            this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(1), this.mPendingProximityDebounceTime);
        }
    }

    private class DisplayPowerProximityStateHandler extends android.os.Handler {
        DisplayPowerProximityStateHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.display.DisplayPowerProximityStateController.this.debounceProximitySensor();
                    break;
                case 2:
                    com.android.server.display.DisplayPowerProximityStateController.this.ignoreProximitySensorUntilChangedInternal();
                    break;
            }
        }
    }

    private java.lang.String proximityToString(int i) {
        switch (i) {
            case -1:
                return "Unknown";
            case 0:
                return "Negative";
            case 1:
                return "Positive";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean getPendingWaitForNegativeProximityLocked() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mPendingWaitForNegativeProximityLocked;
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean getWaitingForNegativeProximity() {
        return this.mWaitingForNegativeProximity;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldIgnoreProximityUntilChanged() {
        return this.mIgnoreProximityUntilChanged;
    }

    boolean isProximitySensorEnabled() {
        return this.mProximitySensorEnabled;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.os.Handler getHandler() {
        return this.mHandler;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getPendingProximity() {
        return this.mPendingProximity;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getProximity() {
        return this.mProximity;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getPendingProximityDebounceTime() {
        return this.mPendingProximityDebounceTime;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.hardware.SensorEventListener getProximitySensorListener() {
        return this.mProximitySensorListener;
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        com.android.server.display.DisplayPowerProximityStateController.Clock createClock() {
            return new com.android.server.display.DisplayPowerProximityStateController.Clock() { // from class: com.android.server.display.DisplayPowerProximityStateController$Injector$$ExternalSyntheticLambda0
                @Override // com.android.server.display.DisplayPowerProximityStateController.Clock
                public final long uptimeMillis() {
                    long uptimeMillis;
                    uptimeMillis = android.os.SystemClock.uptimeMillis();
                    return uptimeMillis;
                }
            };
        }
    }
}
