package com.android.server.policy;

/* loaded from: classes2.dex */
class DisplayFoldDurationLogger {
    private static final int LOG_SUBTYPE_DURATION_MASK = Integer.MIN_VALUE;
    private static final int LOG_SUBTYPE_FOLDED = 1;
    private static final int LOG_SUBTYPE_UNFOLDED = 0;
    static final int SCREEN_STATE_OFF = 0;
    static final int SCREEN_STATE_ON_FOLDED = 2;
    static final int SCREEN_STATE_ON_UNFOLDED = 1;
    static final int SCREEN_STATE_UNKNOWN = -1;
    private volatile int mScreenState = -1;
    private volatile java.lang.Long mLastChanged = null;
    private final com.android.internal.logging.MetricsLogger mLogger = new com.android.internal.logging.MetricsLogger();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScreenState {
    }

    DisplayFoldDurationLogger() {
    }

    void onFinishedWakingUp(java.lang.Boolean bool) {
        if (bool == null) {
            this.mScreenState = -1;
        } else if (bool.booleanValue()) {
            this.mScreenState = 2;
        } else {
            this.mScreenState = 1;
        }
        this.mLastChanged = java.lang.Long.valueOf(android.os.SystemClock.uptimeMillis());
    }

    void onFinishedGoingToSleep() {
        log();
        this.mScreenState = 0;
        this.mLastChanged = null;
    }

    void setDeviceFolded(boolean z) {
        if (!isOn()) {
            return;
        }
        log();
        this.mScreenState = z ? 2 : 1;
        this.mLastChanged = java.lang.Long.valueOf(android.os.SystemClock.uptimeMillis());
    }

    void logFocusedAppWithFoldState(boolean z, java.lang.String str) {
        this.mLogger.write(new android.metrics.LogMaker(1594).setType(4).setSubtype(z ? 1 : 0).setPackageName(str));
    }

    private void log() {
        int i;
        if (this.mLastChanged == null) {
            return;
        }
        switch (this.mScreenState) {
            case 1:
                i = Integer.MIN_VALUE;
                break;
            case 2:
                i = -2147483647;
                break;
            default:
                return;
        }
        this.mLogger.write(new android.metrics.LogMaker(1594).setType(4).setSubtype(i).setLatency(android.os.SystemClock.uptimeMillis() - this.mLastChanged.longValue()));
    }

    private boolean isOn() {
        return this.mScreenState == 1 || this.mScreenState == 2;
    }
}
