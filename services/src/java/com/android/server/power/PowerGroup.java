package com.android.server.power;

/* loaded from: classes2.dex */
public class PowerGroup {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = com.android.server.power.PowerGroup.class.getSimpleName();
    private boolean mButtonOn;
    private boolean mButtonPressed;
    private final android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;

    @com.android.internal.annotations.VisibleForTesting
    final android.hardware.display.DisplayManagerInternal.DisplayPowerRequest mDisplayPowerRequest;
    private final int mGroupId;
    private boolean mIsSandmanSummoned;
    private long mLastButtonActivityTime;
    private long mLastPowerOnTime;
    private long mLastSleepTime;
    private int mLastUserActivityEvent;
    private long mLastUserActivityTime;
    private long mLastUserActivityTimeNoChangeLights;
    private long mLastWakeTime;
    private final com.android.server.power.Notifier mNotifier;
    private boolean mPoweringOn;
    private boolean mReady;
    private final boolean mSupportsSandman;
    private int mUserActivitySummary;
    private int mWakeLockSummary;
    private int mWakefulness;
    private final com.android.server.power.PowerGroup.PowerGroupListener mWakefulnessListener;

    protected interface PowerGroupListener {
        void onWakefulnessChangedLocked(int i, int i2, long j, int i3, int i4, int i5, java.lang.String str, java.lang.String str2);
    }

    PowerGroup(int i, com.android.server.power.PowerGroup.PowerGroupListener powerGroupListener, com.android.server.power.Notifier notifier, android.hardware.display.DisplayManagerInternal displayManagerInternal, int i2, boolean z, boolean z2, long j) {
        this.mDisplayPowerRequest = new android.hardware.display.DisplayManagerInternal.DisplayPowerRequest();
        this.mGroupId = i;
        this.mWakefulnessListener = powerGroupListener;
        this.mNotifier = notifier;
        this.mDisplayManagerInternal = displayManagerInternal;
        this.mWakefulness = i2;
        this.mReady = z;
        this.mSupportsSandman = z2;
        this.mLastWakeTime = j;
        this.mLastSleepTime = j;
    }

    PowerGroup(int i, com.android.server.power.PowerGroup.PowerGroupListener powerGroupListener, com.android.server.power.Notifier notifier, android.hardware.display.DisplayManagerInternal displayManagerInternal, long j) {
        this.mDisplayPowerRequest = new android.hardware.display.DisplayManagerInternal.DisplayPowerRequest();
        this.mGroupId = 0;
        this.mWakefulnessListener = powerGroupListener;
        this.mNotifier = notifier;
        this.mDisplayManagerInternal = displayManagerInternal;
        this.mWakefulness = i;
        this.mReady = false;
        this.mSupportsSandman = true;
        this.mLastWakeTime = j;
        this.mLastSleepTime = j;
    }

    long getLastWakeTimeLocked() {
        return this.mLastWakeTime;
    }

    long getLastSleepTimeLocked() {
        return this.mLastSleepTime;
    }

    int getWakefulnessLocked() {
        return this.mWakefulness;
    }

    int getGroupId() {
        return this.mGroupId;
    }

    boolean setWakefulnessLocked(int i, long j, int i2, int i3, int i4, java.lang.String str, java.lang.String str2) {
        if (this.mWakefulness != i) {
            if (i == 1) {
                setLastPowerOnTimeLocked(j);
                setIsPoweringOnLocked(true);
                this.mLastWakeTime = j;
            } else if (android.os.PowerManagerInternal.isInteractive(this.mWakefulness) && !android.os.PowerManagerInternal.isInteractive(i)) {
                this.mLastSleepTime = j;
            }
            this.mWakefulness = i;
            this.mWakefulnessListener.onWakefulnessChangedLocked(this.mGroupId, this.mWakefulness, j, i3, i2, i4, str, str2);
            return true;
        }
        return false;
    }

    boolean isReadyLocked() {
        return this.mReady;
    }

    boolean setReadyLocked(boolean z) {
        if (this.mReady != z) {
            this.mReady = z;
            return true;
        }
        return false;
    }

    long getLastPowerOnTimeLocked() {
        return this.mLastPowerOnTime;
    }

    void setLastPowerOnTimeLocked(long j) {
        this.mLastPowerOnTime = j;
    }

    boolean isPoweringOnLocked() {
        return this.mPoweringOn;
    }

    void setIsPoweringOnLocked(boolean z) {
        this.mPoweringOn = z;
    }

    boolean isSandmanSummonedLocked() {
        return this.mIsSandmanSummoned;
    }

    void setSandmanSummonedLocked(boolean z) {
        this.mIsSandmanSummoned = z;
    }

    void wakeUpLocked(long j, int i, java.lang.String str, int i2, java.lang.String str2, int i3, com.android.internal.util.LatencyTracker latencyTracker) {
        if (j < this.mLastSleepTime || this.mWakefulness == 1) {
            return;
        }
        android.os.Trace.traceBegin(131072L, "wakePowerGroup" + this.mGroupId);
        try {
            android.util.Slog.i(TAG, "Waking up power group from " + android.os.PowerManagerInternal.wakefulnessToString(this.mWakefulness) + " (groupId=" + this.mGroupId + ", uid=" + i2 + ", reason=" + android.os.PowerManager.wakeReasonToString(i) + ", details=" + str + ")...");
            android.os.Trace.asyncTraceBegin(131072L, "Screen turning on", this.mGroupId);
            latencyTracker.onActionStart(5, java.lang.String.valueOf(this.mGroupId));
            setWakefulnessLocked(1, j, i2, i, i3, str2, str);
        } finally {
            android.os.Trace.traceEnd(131072L);
        }
    }

    boolean dreamLocked(long j, int i, boolean z) {
        if (j >= this.mLastWakeTime) {
            if (!z && this.mWakefulness != 1) {
                return false;
            }
            android.os.Trace.traceBegin(131072L, "dreamPowerGroup" + getGroupId());
            try {
                android.util.Slog.i(TAG, "Napping power group (groupId=" + getGroupId() + ", uid=" + i + ")...");
                setSandmanSummonedLocked(true);
                setWakefulnessLocked(2, j, i, 0, 0, null, null);
                return true;
            } finally {
                android.os.Trace.traceEnd(131072L);
            }
        }
        return false;
    }

    boolean dozeLocked(long j, int i, int i2) {
        if (j < getLastWakeTimeLocked() || !android.os.PowerManagerInternal.isInteractive(this.mWakefulness)) {
            return false;
        }
        android.os.Trace.traceBegin(131072L, "powerOffDisplay");
        try {
            int min = java.lang.Math.min(13, java.lang.Math.max(i2, 0));
            long max = j - java.lang.Math.max(this.mLastUserActivityTimeNoChangeLights, this.mLastUserActivityTime);
            android.util.Slog.i(TAG, "Powering off display group due to " + android.os.PowerManager.sleepReasonToString(min) + " (groupId= " + getGroupId() + ", uid= " + i + ", millisSinceLastUserActivity=" + max + ", lastUserActivityEvent=" + android.os.PowerManager.userActivityEventToString(this.mLastUserActivityEvent) + ")...");
            setSandmanSummonedLocked(true);
            setWakefulnessLocked(3, j, i, min, 0, null, null);
            return true;
        } finally {
            android.os.Trace.traceEnd(131072L);
        }
    }

    boolean sleepLocked(long j, int i, int i2) {
        if (j < this.mLastWakeTime || getWakefulnessLocked() == 0) {
            return false;
        }
        android.os.Trace.traceBegin(131072L, "sleepPowerGroup");
        try {
            android.util.Slog.i(TAG, "Sleeping power group (groupId=" + getGroupId() + ", uid=" + i + ", reason=" + android.os.PowerManager.sleepReasonToString(i2) + ")...");
            setSandmanSummonedLocked(true);
            setWakefulnessLocked(0, j, i, i2, 0, null, null);
            return true;
        } finally {
            android.os.Trace.traceEnd(131072L);
        }
    }

    boolean getButtonOnLocked() {
        return this.mButtonOn;
    }

    boolean getButtonPressedLocked() {
        return this.mButtonPressed;
    }

    long getLastButtonActivityTimeLocked() {
        return this.mLastButtonActivityTime;
    }

    long getLastUserActivityTimeLocked() {
        return this.mLastUserActivityTime;
    }

    void setButtonOnLocked(boolean z) {
        this.mButtonOn = z;
    }

    void setButtonPressedLocked(boolean z) {
        this.mButtonPressed = z;
    }

    void setLastButtonActivityTimeLocked(long j) {
        this.mLastButtonActivityTime = j;
    }

    void setLastUserActivityTimeLocked(long j, int i) {
        this.mLastUserActivityTime = j;
        this.mLastUserActivityEvent = i;
    }

    public long getLastUserActivityTimeNoChangeLightsLocked() {
        return this.mLastUserActivityTimeNoChangeLights;
    }

    public void setLastUserActivityTimeNoChangeLightsLocked(long j, int i) {
        this.mLastUserActivityTimeNoChangeLights = j;
        this.mLastUserActivityEvent = i;
    }

    public int getUserActivitySummaryLocked() {
        return this.mUserActivitySummary;
    }

    public boolean isPolicyBrightLocked() {
        return this.mDisplayPowerRequest.policy == 3;
    }

    public boolean isPolicyDimLocked() {
        return this.mDisplayPowerRequest.policy == 2;
    }

    public boolean isBrightOrDimLocked() {
        return this.mDisplayPowerRequest.isBrightOrDim();
    }

    public void setUserActivitySummaryLocked(int i) {
        this.mUserActivitySummary = i;
    }

    public int getWakeLockSummaryLocked() {
        return this.mWakeLockSummary;
    }

    public boolean hasWakeLockKeepingScreenOnLocked() {
        return (this.mWakeLockSummary & 38) != 0;
    }

    public void setWakeLockSummaryLocked(int i) {
        this.mWakeLockSummary = i;
    }

    public boolean supportsSandmanLocked() {
        return this.mSupportsSandman;
    }

    boolean needSuspendBlockerLocked(boolean z, boolean z2) {
        if (!isBrightOrDimLocked() || (this.mDisplayPowerRequest.useProximitySensor && z && z2)) {
            return this.mDisplayPowerRequest.policy == 1 && this.mDisplayPowerRequest.dozeScreenState == 2;
        }
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getDesiredScreenPolicyLocked(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        int wakefulnessLocked = getWakefulnessLocked();
        int wakeLockSummaryLocked = getWakeLockSummaryLocked();
        if (wakefulnessLocked == 0 || z) {
            return 0;
        }
        if (wakefulnessLocked == 3) {
            if ((wakeLockSummaryLocked & 64) != 0) {
                return 1;
            }
            if (z2) {
                return 0;
            }
            if (z5) {
                return 3;
            }
        }
        return ((wakeLockSummaryLocked & 2) == 0 && z3 && (1 & getUserActivitySummaryLocked()) == 0 && !z4) ? 2 : 3;
    }

    int getPolicyLocked() {
        return this.mDisplayPowerRequest.policy;
    }

    boolean updateLocked(float f, boolean z, boolean z2, int i, float f2, boolean z3, android.os.PowerSaveState powerSaveState, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9) {
        this.mDisplayPowerRequest.policy = getDesiredScreenPolicyLocked(z4, z5, z6, z7, z9);
        this.mDisplayPowerRequest.screenBrightnessOverride = f;
        this.mDisplayPowerRequest.useProximitySensor = z;
        this.mDisplayPowerRequest.boostScreenBrightness = z2;
        if (this.mDisplayPowerRequest.policy == 1) {
            this.mDisplayPowerRequest.dozeScreenState = i;
            if ((getWakeLockSummaryLocked() & 128) != 0 && !z3) {
                if (this.mDisplayPowerRequest.dozeScreenState == 4) {
                    this.mDisplayPowerRequest.dozeScreenState = 3;
                }
                if (this.mDisplayPowerRequest.dozeScreenState == 6) {
                    this.mDisplayPowerRequest.dozeScreenState = 2;
                }
            }
            this.mDisplayPowerRequest.dozeScreenBrightness = f2;
        } else {
            this.mDisplayPowerRequest.dozeScreenState = 0;
            this.mDisplayPowerRequest.dozeScreenBrightness = Float.NaN;
        }
        this.mDisplayPowerRequest.lowPowerMode = powerSaveState.batterySaverEnabled;
        this.mDisplayPowerRequest.screenLowPowerBrightnessFactor = powerSaveState.brightnessFactor;
        boolean requestPowerState = this.mDisplayManagerInternal.requestPowerState(this.mGroupId, this.mDisplayPowerRequest, z8);
        this.mNotifier.onScreenPolicyUpdate(this.mGroupId, this.mDisplayPowerRequest.policy);
        return requestPowerState;
    }
}
