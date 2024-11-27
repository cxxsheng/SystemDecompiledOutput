package com.android.server.policy;

/* loaded from: classes2.dex */
class WindowWakeUpPolicy {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "WindowWakeUpPolicy";
    private final boolean mAllowTheaterModeWakeFromCameraLens;
    private final boolean mAllowTheaterModeWakeFromKey;
    private final boolean mAllowTheaterModeWakeFromLidSwitch;
    private final boolean mAllowTheaterModeWakeFromMotion;
    private final boolean mAllowTheaterModeWakeFromPowerKey;
    private final boolean mAllowTheaterModeWakeFromWakeGesture;
    private final com.android.internal.os.Clock mClock;
    private final android.content.Context mContext;

    @android.annotation.Nullable
    private com.android.server.policy.WindowWakeUpPolicyInternal.InputWakeUpDelegate mInputWakeUpDelegate;
    private final android.os.PowerManager mPowerManager;

    WindowWakeUpPolicy(android.content.Context context) {
        this(context, com.android.internal.os.Clock.SYSTEM_CLOCK);
    }

    @com.android.internal.annotations.VisibleForTesting
    WindowWakeUpPolicy(android.content.Context context, com.android.internal.os.Clock clock) {
        this.mContext = context;
        this.mPowerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
        this.mClock = clock;
        android.content.res.Resources resources = context.getResources();
        this.mAllowTheaterModeWakeFromKey = resources.getBoolean(android.R.bool.config_allowTheaterModeWakeFromKey);
        this.mAllowTheaterModeWakeFromPowerKey = this.mAllowTheaterModeWakeFromKey || resources.getBoolean(android.R.bool.config_allowTheaterModeWakeFromPowerKey);
        this.mAllowTheaterModeWakeFromMotion = resources.getBoolean(android.R.bool.config_allowTheaterModeWakeFromMotion);
        this.mAllowTheaterModeWakeFromCameraLens = resources.getBoolean(android.R.bool.config_allowTheaterModeWakeFromCameraLens);
        this.mAllowTheaterModeWakeFromLidSwitch = resources.getBoolean(android.R.bool.config_allowTheaterModeWakeFromLidSwitch);
        this.mAllowTheaterModeWakeFromWakeGesture = resources.getBoolean(android.R.bool.config_allowTheaterModeWakeFromGesture);
        com.android.server.policy.Flags.supportInputWakeupDelegate();
    }

    private final class LocalService implements com.android.server.policy.WindowWakeUpPolicyInternal {
        private LocalService() {
        }

        @Override // com.android.server.policy.WindowWakeUpPolicyInternal
        public void setInputWakeUpDelegate(@android.annotation.Nullable com.android.server.policy.WindowWakeUpPolicyInternal.InputWakeUpDelegate inputWakeUpDelegate) {
            com.android.server.policy.Flags.supportInputWakeupDelegate();
            android.util.Slog.w(com.android.server.policy.WindowWakeUpPolicy.TAG, "Input wake up delegates not supported.");
        }
    }

    boolean wakeUpFromKey(long j, int i, boolean z) {
        return wakeUpFromKey(j, i, z, false);
    }

    boolean wakeUpFromKey(long j, int i, boolean z, boolean z2) {
        boolean z3;
        if (i == 26) {
            z3 = this.mAllowTheaterModeWakeFromPowerKey;
        } else {
            z3 = this.mAllowTheaterModeWakeFromKey;
        }
        if (!canWakeUp(z3)) {
            return false;
        }
        if (this.mInputWakeUpDelegate != null && this.mInputWakeUpDelegate.wakeUpFromKey(j, i, z)) {
            return true;
        }
        wakeUp(j, i == 26 ? 1 : 6, i == 26 ? "POWER" : "KEY", z2);
        return true;
    }

    boolean wakeUpFromMotion(long j, int i, boolean z) {
        if (!canWakeUp(this.mAllowTheaterModeWakeFromMotion)) {
            return false;
        }
        if (this.mInputWakeUpDelegate != null && this.mInputWakeUpDelegate.wakeUpFromMotion(j, i, z)) {
            return true;
        }
        wakeUp(j, 7, "MOTION");
        return true;
    }

    boolean wakeUpFromCameraCover(long j) {
        if (!canWakeUp(this.mAllowTheaterModeWakeFromCameraLens)) {
            return false;
        }
        wakeUp(j, 5, "CAMERA_COVER");
        return true;
    }

    boolean wakeUpFromLid() {
        if (!canWakeUp(this.mAllowTheaterModeWakeFromLidSwitch)) {
            return false;
        }
        wakeUp(this.mClock.uptimeMillis(), 9, "LID");
        return true;
    }

    boolean wakeUpFromPowerKeyCameraGesture() {
        if (!canWakeUp(this.mAllowTheaterModeWakeFromPowerKey)) {
            return false;
        }
        wakeUp(this.mClock.uptimeMillis(), 5, "CAMERA_GESTURE_PREVENT_LOCK");
        return true;
    }

    boolean wakeUpFromWakeGesture() {
        if (!canWakeUp(this.mAllowTheaterModeWakeFromWakeGesture)) {
            return false;
        }
        wakeUp(this.mClock.uptimeMillis(), 4, "GESTURE");
        return true;
    }

    private boolean canWakeUp(boolean z) {
        return z || !(android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "theater_mode_on", 0) == 1);
    }

    private void wakeUp(long j, int i, java.lang.String str) {
        wakeUp(j, i, str, false);
    }

    private void wakeUp(long j, int i, java.lang.String str, boolean z) {
        if (z) {
            this.mPowerManager.wakeUpWithProximityCheck(j, i, "android.policy:" + str);
            return;
        }
        this.mPowerManager.wakeUp(j, i, "android.policy:" + str);
    }
}
