package com.android.server.power;

/* loaded from: classes2.dex */
public class ScreenUndimDetector {
    private static final boolean DEBUG = false;
    private static final boolean DEFAULT_KEEP_SCREEN_ON_ENABLED = true;

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_KEEP_SCREEN_ON_FOR_MILLIS = java.util.concurrent.TimeUnit.MINUTES.toMillis(10);

    @com.android.internal.annotations.VisibleForTesting
    static final long DEFAULT_MAX_DURATION_BETWEEN_UNDIMS_MILLIS = java.util.concurrent.TimeUnit.MINUTES.toMillis(5);

    @com.android.internal.annotations.VisibleForTesting
    static final int DEFAULT_UNDIMS_REQUIRED = 2;
    static final java.lang.String KEY_KEEP_SCREEN_ON_ENABLED = "keep_screen_on_enabled";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_KEEP_SCREEN_ON_FOR_MILLIS = "keep_screen_on_for_millis";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_MAX_DURATION_BETWEEN_UNDIMS_MILLIS = "max_duration_between_undims_millis";

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String KEY_UNDIMS_REQUIRED = "undims_required";
    private static final int OUTCOME_POWER_BUTTON = 1;
    private static final int OUTCOME_TIMEOUT = 2;
    private static final java.lang.String TAG = "ScreenUndimDetector";
    private static final java.lang.String UNDIM_DETECTOR_WAKE_LOCK = "UndimDetectorWakeLock";
    private com.android.server.power.ScreenUndimDetector.InternalClock mClock;

    @com.android.internal.annotations.VisibleForTesting
    int mCurrentScreenPolicy;
    private long mInteractionAfterUndimTime;
    private boolean mKeepScreenOnEnabled;
    private long mKeepScreenOnForMillis;
    private long mMaxDurationBetweenUndimsMillis;

    @com.android.internal.annotations.VisibleForTesting
    int mUndimCounter;

    @com.android.internal.annotations.VisibleForTesting
    long mUndimCounterStartedMillis;
    private long mUndimOccurredTime;
    private int mUndimsRequired;

    @com.android.internal.annotations.VisibleForTesting
    android.os.PowerManager.WakeLock mWakeLock;

    public ScreenUndimDetector() {
        this.mUndimCounter = 0;
        this.mUndimOccurredTime = -1L;
        this.mInteractionAfterUndimTime = -1L;
        this.mClock = new com.android.server.power.ScreenUndimDetector.InternalClock();
    }

    ScreenUndimDetector(com.android.server.power.ScreenUndimDetector.InternalClock internalClock) {
        this.mUndimCounter = 0;
        this.mUndimOccurredTime = -1L;
        this.mInteractionAfterUndimTime = -1L;
        this.mClock = internalClock;
    }

    static class InternalClock {
        InternalClock() {
        }

        public long getCurrentTime() {
            return android.os.SystemClock.elapsedRealtime();
        }
    }

    public void systemReady(android.content.Context context) {
        readValuesFromDeviceConfig();
        android.provider.DeviceConfig.addOnPropertiesChangedListener("attention_manager_service", context.getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.power.ScreenUndimDetector$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.power.ScreenUndimDetector.this.lambda$systemReady$0(properties);
            }
        });
        this.mWakeLock = ((android.os.PowerManager) context.getSystemService(android.os.PowerManager.class)).newWakeLock(536870922, UNDIM_DETECTOR_WAKE_LOCK);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemReady$0(android.provider.DeviceConfig.Properties properties) {
        onDeviceConfigChange(properties.getKeyset());
    }

    public void recordScreenPolicy(int i, int i2) {
        if (i != 0 || i2 == this.mCurrentScreenPolicy) {
            return;
        }
        int i3 = this.mCurrentScreenPolicy;
        this.mCurrentScreenPolicy = i2;
        if (!this.mKeepScreenOnEnabled) {
        }
        switch (i3) {
            case 2:
                if (i2 == 3) {
                    long currentTime = this.mClock.getCurrentTime();
                    if (currentTime - this.mUndimCounterStartedMillis >= this.mMaxDurationBetweenUndimsMillis) {
                        reset();
                    }
                    if (this.mUndimCounter == 0) {
                        this.mUndimCounterStartedMillis = currentTime;
                    }
                    this.mUndimCounter++;
                    if (this.mUndimCounter >= this.mUndimsRequired) {
                        reset();
                        if (this.mWakeLock != null) {
                            this.mUndimOccurredTime = this.mClock.getCurrentTime();
                            this.mWakeLock.acquire(this.mKeepScreenOnForMillis);
                            break;
                        }
                    }
                } else {
                    if (i2 == 0 || i2 == 1) {
                        checkAndLogUndim(2);
                    }
                    reset();
                    break;
                }
                break;
            case 3:
                if (i2 == 0 || i2 == 1) {
                    checkAndLogUndim(1);
                }
                if (i2 != 2) {
                    reset();
                    break;
                }
                break;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void reset() {
        this.mUndimCounter = 0;
        this.mUndimCounterStartedMillis = 0L;
        if (this.mWakeLock != null && this.mWakeLock.isHeld()) {
            this.mWakeLock.release();
        }
    }

    private boolean readKeepScreenOnNotificationEnabled() {
        return android.provider.DeviceConfig.getBoolean("attention_manager_service", KEY_KEEP_SCREEN_ON_ENABLED, true);
    }

    private long readKeepScreenOnForMillis() {
        return android.provider.DeviceConfig.getLong("attention_manager_service", KEY_KEEP_SCREEN_ON_FOR_MILLIS, DEFAULT_KEEP_SCREEN_ON_FOR_MILLIS);
    }

    private int readUndimsRequired() {
        int i = android.provider.DeviceConfig.getInt("attention_manager_service", KEY_UNDIMS_REQUIRED, 2);
        if (i < 1 || i > 5) {
            android.util.Slog.e(TAG, "Provided undimsRequired=" + i + " is not allowed [1, 5]; using the default=2");
            return 2;
        }
        return i;
    }

    private long readMaxDurationBetweenUndimsMillis() {
        return android.provider.DeviceConfig.getLong("attention_manager_service", KEY_MAX_DURATION_BETWEEN_UNDIMS_MILLIS, DEFAULT_MAX_DURATION_BETWEEN_UNDIMS_MILLIS);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void onDeviceConfigChange(@android.annotation.NonNull java.util.Set<java.lang.String> set) {
        char c;
        for (java.lang.String str : set) {
            android.util.Slog.i(TAG, "onDeviceConfigChange; key=" + str);
            switch (str.hashCode()) {
                case -2114725254:
                    if (str.equals(KEY_UNDIMS_REQUIRED)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1871288230:
                    if (str.equals(KEY_KEEP_SCREEN_ON_ENABLED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 352003779:
                    if (str.equals(KEY_KEEP_SCREEN_ON_FOR_MILLIS)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1709324730:
                    if (str.equals(KEY_MAX_DURATION_BETWEEN_UNDIMS_MILLIS)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                    readValuesFromDeviceConfig();
                    return;
                default:
                    android.util.Slog.i(TAG, "Ignoring change on " + str);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void readValuesFromDeviceConfig() {
        this.mKeepScreenOnEnabled = readKeepScreenOnNotificationEnabled();
        this.mKeepScreenOnForMillis = readKeepScreenOnForMillis();
        this.mUndimsRequired = readUndimsRequired();
        this.mMaxDurationBetweenUndimsMillis = readMaxDurationBetweenUndimsMillis();
        android.util.Slog.i(TAG, "readValuesFromDeviceConfig():\nmKeepScreenOnForMillis=" + this.mKeepScreenOnForMillis + "\nmKeepScreenOnNotificationEnabled=" + this.mKeepScreenOnEnabled + "\nmUndimsRequired=" + this.mUndimsRequired);
    }

    public void userActivity(int i) {
        if (i == 0 && this.mUndimOccurredTime != 1 && this.mInteractionAfterUndimTime == -1) {
            this.mInteractionAfterUndimTime = this.mClock.getCurrentTime();
        }
    }

    private void checkAndLogUndim(int i) {
        if (this.mUndimOccurredTime != -1) {
            long currentTime = this.mClock.getCurrentTime();
            com.android.internal.util.FrameworkStatsLog.write(365, i, currentTime - this.mUndimOccurredTime, this.mInteractionAfterUndimTime != -1 ? currentTime - this.mInteractionAfterUndimTime : -1L);
            this.mUndimOccurredTime = -1L;
            this.mInteractionAfterUndimTime = -1L;
        }
    }
}
