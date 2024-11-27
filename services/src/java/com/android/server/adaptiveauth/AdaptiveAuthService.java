package com.android.server.adaptiveauth;

/* loaded from: classes.dex */
public class AdaptiveAuthService extends com.android.server.SystemService {
    private static final int AUTH_FAILURE = 0;
    private static final int AUTH_SUCCESS = 1;
    private static final boolean DEBUG;

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_ALLOWED_FAILED_AUTH_ATTEMPTS = 5;
    private static final int MSG_REPORT_BIOMETRIC_AUTH_ATTEMPT = 2;
    private static final int MSG_REPORT_PRIMARY_AUTH_ATTEMPT = 1;
    private static final java.lang.String TAG = "AdaptiveAuthService";
    private static final int TYPE_BIOMETRIC_AUTH = 1;
    private static final int TYPE_PRIMARY_AUTH = 0;
    private final android.hardware.biometrics.AuthenticationStateListener mAuthenticationStateListener;
    private final android.hardware.biometrics.BiometricManager mBiometricManager;

    @com.android.internal.annotations.VisibleForTesting
    final android.util.SparseIntArray mFailedAttemptsForUser;
    private final android.os.Handler mHandler;
    private final android.app.KeyguardManager mKeyguardManager;
    private final android.util.SparseLongArray mLastLockedTimestamp;
    private final com.android.internal.widget.LockPatternUtils mLockPatternUtils;
    private final com.android.internal.widget.LockSettingsInternal mLockSettings;
    private final com.android.internal.widget.LockSettingsStateListener mLockSettingsStateListener;
    private final android.os.PowerManager mPowerManager;
    private final com.android.server.pm.UserManagerInternal mUserManager;
    private final com.android.server.wm.WindowManagerInternal mWindowManager;

    static {
        DEBUG = android.os.Build.IS_DEBUGGABLE && android.util.Log.isLoggable(TAG, 3);
    }

    public AdaptiveAuthService(android.content.Context context) {
        this(context, new com.android.internal.widget.LockPatternUtils(context));
    }

    @com.android.internal.annotations.VisibleForTesting
    public AdaptiveAuthService(android.content.Context context, com.android.internal.widget.LockPatternUtils lockPatternUtils) {
        super(context);
        this.mFailedAttemptsForUser = new android.util.SparseIntArray();
        this.mLastLockedTimestamp = new android.util.SparseLongArray();
        this.mLockSettingsStateListener = new com.android.internal.widget.LockSettingsStateListener() { // from class: com.android.server.adaptiveauth.AdaptiveAuthService.1
            public void onAuthenticationSucceeded(int i) {
                if (com.android.server.adaptiveauth.AdaptiveAuthService.DEBUG) {
                    android.util.Slog.d(com.android.server.adaptiveauth.AdaptiveAuthService.TAG, "LockSettingsStateListener#onAuthenticationSucceeded");
                }
                com.android.server.adaptiveauth.AdaptiveAuthService.this.mHandler.obtainMessage(1, 1, i).sendToTarget();
            }

            public void onAuthenticationFailed(int i) {
                android.util.Slog.i(com.android.server.adaptiveauth.AdaptiveAuthService.TAG, "LockSettingsStateListener#onAuthenticationFailed");
                com.android.server.adaptiveauth.AdaptiveAuthService.this.mHandler.obtainMessage(1, 0, i).sendToTarget();
            }
        };
        this.mAuthenticationStateListener = new android.hardware.biometrics.AuthenticationStateListener.Stub() { // from class: com.android.server.adaptiveauth.AdaptiveAuthService.2
            public void onAuthenticationStarted(int i) {
            }

            public void onAuthenticationStopped() {
            }

            public void onAuthenticationSucceeded(int i, int i2) {
                if (com.android.server.adaptiveauth.AdaptiveAuthService.DEBUG) {
                    android.util.Slog.d(com.android.server.adaptiveauth.AdaptiveAuthService.TAG, "AuthenticationStateListener#onAuthenticationSucceeded");
                }
                com.android.server.adaptiveauth.AdaptiveAuthService.this.mHandler.obtainMessage(2, 1, i2).sendToTarget();
            }

            public void onAuthenticationFailed(int i, int i2) {
                android.util.Slog.i(com.android.server.adaptiveauth.AdaptiveAuthService.TAG, "AuthenticationStateListener#onAuthenticationFailed");
                com.android.server.adaptiveauth.AdaptiveAuthService.this.mHandler.obtainMessage(2, 0, i2).sendToTarget();
            }

            public void onAuthenticationAcquired(android.hardware.biometrics.BiometricSourceType biometricSourceType, int i, int i2) {
            }
        };
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: com.android.server.adaptiveauth.AdaptiveAuthService.3
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        com.android.server.adaptiveauth.AdaptiveAuthService.this.handleReportPrimaryAuthAttempt(message.arg1 != 0, message.arg2);
                        break;
                    case 2:
                        com.android.server.adaptiveauth.AdaptiveAuthService.this.handleReportBiometricAuthAttempt(message.arg1 != 0, message.arg2);
                        break;
                }
            }
        };
        this.mLockPatternUtils = lockPatternUtils;
        com.android.internal.widget.LockSettingsInternal lockSettingsInternal = (com.android.internal.widget.LockSettingsInternal) com.android.server.LocalServices.getService(com.android.internal.widget.LockSettingsInternal.class);
        java.util.Objects.requireNonNull(lockSettingsInternal);
        this.mLockSettings = lockSettingsInternal;
        android.hardware.biometrics.BiometricManager biometricManager = (android.hardware.biometrics.BiometricManager) context.getSystemService(android.hardware.biometrics.BiometricManager.class);
        java.util.Objects.requireNonNull(biometricManager);
        this.mBiometricManager = biometricManager;
        android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) context.getSystemService(android.app.KeyguardManager.class);
        java.util.Objects.requireNonNull(keyguardManager);
        this.mKeyguardManager = keyguardManager;
        android.os.PowerManager powerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
        java.util.Objects.requireNonNull(powerManager);
        this.mPowerManager = powerManager;
        com.android.server.wm.WindowManagerInternal windowManagerInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        java.util.Objects.requireNonNull(windowManagerInternal);
        this.mWindowManager = windowManagerInternal;
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        java.util.Objects.requireNonNull(userManagerInternal);
        this.mUserManager = userManagerInternal;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            init();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void init() {
        this.mLockSettings.registerLockSettingsStateListener(this.mLockSettingsStateListener);
        this.mBiometricManager.registerAuthenticationStateListener(this.mAuthenticationStateListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReportPrimaryAuthAttempt(boolean z, int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "handleReportPrimaryAuthAttempt: success=" + z + ", userId=" + i);
        }
        reportAuthAttempt(0, z, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReportBiometricAuthAttempt(boolean z, int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "handleReportBiometricAuthAttempt: success=" + z + ", userId=" + i);
        }
        reportAuthAttempt(1, z, i);
    }

    private void reportAuthAttempt(int i, boolean z, int i2) {
        if (getContext().getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            return;
        }
        if (z) {
            this.mFailedAttemptsForUser.delete(i2);
            if (this.mLastLockedTimestamp.indexOfKey(i2) >= 0) {
                collectTimeElapsedSinceLastLocked(this.mLastLockedTimestamp.get(i2), android.os.SystemClock.elapsedRealtime(), i);
                this.mLastLockedTimestamp.delete(i2);
                return;
            }
            return;
        }
        int i3 = this.mFailedAttemptsForUser.get(i2, 0) + 1;
        android.util.Slog.i(TAG, "reportAuthAttempt: numFailedAttempts=" + i3 + ", userId=" + i2);
        this.mFailedAttemptsForUser.put(i2, i3);
        if (this.mKeyguardManager.isDeviceLocked(i2) && this.mKeyguardManager.isKeyguardLocked()) {
            android.util.Slog.d(TAG, "Not locking the device because the device is already locked.");
        } else if (i3 < 5) {
            android.util.Slog.d(TAG, "Not locking the device because the number of failed attempts is below the threshold.");
        } else {
            lockDevice(i2);
        }
    }

    private static void collectTimeElapsedSinceLastLocked(long j, long j2, int i) {
        int i2;
        switch (i) {
            case 0:
                i2 = 1;
                break;
            case 1:
                i2 = 2;
                break;
            default:
                i2 = 0;
                break;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "collectTimeElapsedSinceLastLockedForUser: lastLockedTime=" + j + ", authTime=" + j2 + ", unlockType=" + i2);
        }
        if (j > j2) {
            return;
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.ADAPTIVE_AUTH_UNLOCK_AFTER_LOCK_REPORTED, j, j2, i2);
    }

    private void lockDevice(int i) {
        this.mLockPatternUtils.requireStrongAuth(512, i);
        int profileParentId = this.mUserManager.getProfileParentId(i);
        android.util.Slog.i(TAG, "lockDevice: userId=" + i + ", parentUserId=" + profileParentId);
        if (profileParentId != i) {
            this.mLockPatternUtils.requireStrongAuth(512, profileParentId);
        }
        this.mPowerManager.goToSleep(android.os.SystemClock.uptimeMillis());
        this.mWindowManager.lockNow();
        this.mLastLockedTimestamp.put(i, android.os.SystemClock.elapsedRealtime());
    }
}
