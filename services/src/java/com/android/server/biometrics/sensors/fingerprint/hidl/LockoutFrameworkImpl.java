package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
public class LockoutFrameworkImpl implements com.android.server.biometrics.sensors.LockoutTracker {
    private static final java.lang.String ACTION_LOCKOUT_RESET = "com.android.server.biometrics.sensors.fingerprint.ACTION_LOCKOUT_RESET";
    private static final long FAIL_LOCKOUT_TIMEOUT_MS = 30000;
    private static final java.lang.String KEY_LOCKOUT_RESET_USER = "lockout_reset_user";
    private static final int MAX_FAILED_ATTEMPTS_LOCKOUT_PERMANENT = 20;
    private static final int MAX_FAILED_ATTEMPTS_LOCKOUT_TIMED = 5;
    private static final java.lang.String TAG = "LockoutFrameworkImpl";
    private final android.app.AlarmManager mAlarmManager;
    private final android.util.SparseIntArray mFailedAttempts;
    private final android.os.Handler mHandler;
    private final com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.LockoutReceiver mLockoutReceiver;
    private final com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.LockoutResetCallback mLockoutResetCallback;
    private final java.util.function.Function<java.lang.Integer, android.app.PendingIntent> mLockoutResetIntent;
    private final android.util.SparseBooleanArray mTimedLockoutCleared;

    public interface LockoutResetCallback {
        void onLockoutReset(int i);
    }

    private final class LockoutReceiver extends android.content.BroadcastReceiver {
        private LockoutReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            android.util.Slog.v(com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.TAG, "Resetting lockout: " + intent.getAction());
            if (com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.ACTION_LOCKOUT_RESET.equals(intent.getAction())) {
                com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.this.resetFailedAttemptsForUser(false, intent.getIntExtra(com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.KEY_LOCKOUT_RESET_USER, 0));
            }
        }
    }

    public LockoutFrameworkImpl(@android.annotation.NonNull final android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.LockoutResetCallback lockoutResetCallback) {
        this(context, lockoutResetCallback, new java.util.function.Function() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.app.PendingIntent lambda$new$0;
                lambda$new$0 = com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.lambda$new$0(context, (java.lang.Integer) obj);
                return lambda$new$0;
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.app.PendingIntent lambda$new$0(android.content.Context context, java.lang.Integer num) {
        return android.app.PendingIntent.getBroadcast(context, num.intValue(), new android.content.Intent(ACTION_LOCKOUT_RESET).putExtra(KEY_LOCKOUT_RESET_USER, num), android.hardware.audio.common.V2_0.AudioFormat.DTS_HD);
    }

    public LockoutFrameworkImpl(@android.annotation.NonNull final android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.LockoutResetCallback lockoutResetCallback, @android.annotation.NonNull android.os.Handler handler) {
        this(context, lockoutResetCallback, new java.util.function.Function() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.app.PendingIntent lambda$new$1;
                lambda$new$1 = com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.lambda$new$1(context, (java.lang.Integer) obj);
                return lambda$new$1;
            }
        }, handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.app.PendingIntent lambda$new$1(android.content.Context context, java.lang.Integer num) {
        return android.app.PendingIntent.getBroadcast(context, num.intValue(), new android.content.Intent(ACTION_LOCKOUT_RESET).putExtra(KEY_LOCKOUT_RESET_USER, num), android.hardware.audio.common.V2_0.AudioFormat.DTS_HD);
    }

    @com.android.internal.annotations.VisibleForTesting
    LockoutFrameworkImpl(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.LockoutResetCallback lockoutResetCallback, @android.annotation.NonNull java.util.function.Function<java.lang.Integer, android.app.PendingIntent> function, @android.annotation.Nullable android.os.Handler handler) {
        this.mLockoutResetCallback = lockoutResetCallback;
        this.mTimedLockoutCleared = new android.util.SparseBooleanArray();
        this.mFailedAttempts = new android.util.SparseIntArray();
        this.mAlarmManager = (android.app.AlarmManager) context.getSystemService(android.app.AlarmManager.class);
        this.mLockoutReceiver = new com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.LockoutReceiver();
        this.mHandler = handler == null ? new android.os.Handler(android.os.Looper.getMainLooper()) : handler;
        this.mLockoutResetIntent = function;
        context.registerReceiver(this.mLockoutReceiver, new android.content.IntentFilter(ACTION_LOCKOUT_RESET), "android.permission.RESET_FINGERPRINT_LOCKOUT", null, 2);
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public void resetFailedAttemptsForUser(boolean z, int i) {
        if (getLockoutModeForUser(i) != 0) {
            android.util.Slog.v(TAG, "Reset biometric lockout for user: " + i + ", clearAttemptCounter: " + z);
        }
        if (z) {
            this.mFailedAttempts.put(i, 0);
        }
        this.mTimedLockoutCleared.put(i, true);
        cancelLockoutResetForUser(i);
        this.mLockoutResetCallback.onLockoutReset(i);
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public void addFailedAttemptForUser(int i) {
        this.mFailedAttempts.put(i, this.mFailedAttempts.get(i, 0) + 1);
        this.mTimedLockoutCleared.put(i, false);
        if (getLockoutModeForUser(i) != 0) {
            scheduleLockoutResetForUser(i);
        }
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public int getLockoutModeForUser(int i) {
        int i2 = this.mFailedAttempts.get(i, 0);
        if (i2 >= 20) {
            return 2;
        }
        return (i2 <= 0 || this.mTimedLockoutCleared.get(i, false) || i2 % 5 != 0) ? 0 : 1;
    }

    @Override // com.android.server.biometrics.sensors.LockoutTracker
    public void setLockoutModeForUser(int i, int i2) {
    }

    private void cancelLockoutResetForUser(int i) {
        this.mAlarmManager.cancel(this.mLockoutResetIntent.apply(java.lang.Integer.valueOf(i)));
    }

    private void scheduleLockoutResetForUser(final int i) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.this.lambda$scheduleLockoutResetForUser$2(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleLockoutResetForUser$2(int i) {
        this.mAlarmManager.setExact(2, android.os.SystemClock.elapsedRealtime() + 30000, this.mLockoutResetIntent.apply(java.lang.Integer.valueOf(i)));
    }
}
