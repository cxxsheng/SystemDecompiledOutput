package android.hardware.biometrics;

/* loaded from: classes.dex */
public class BiometricManager {
    public static final int BIOMETRIC_ERROR_HW_UNAVAILABLE = 1;
    public static final int BIOMETRIC_ERROR_NONE_ENROLLED = 11;
    public static final int BIOMETRIC_ERROR_NO_HARDWARE = 12;
    public static final int BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED = 15;
    public static final long BIOMETRIC_NO_AUTHENTICATION = -1;
    public static final int BIOMETRIC_SUCCESS = 0;
    public static final java.lang.String EXTRA_ENROLL_REASON = "enroll_reason";
    private static final int GET_LAST_AUTH_TIME_ALLOWED_AUTHENTICATORS = 32783;
    private static final java.lang.String TAG = "BiometricManager";
    private final android.content.Context mContext;
    private final android.hardware.biometrics.IAuthService mService;

    public interface Authenticators {

        @android.annotation.SystemApi
        public static final int BIOMETRIC_CONVENIENCE = 4095;
        public static final int BIOMETRIC_MAX_STRENGTH = 1;
        public static final int BIOMETRIC_MIN_STRENGTH = 32767;
        public static final int BIOMETRIC_STRONG = 15;
        public static final int BIOMETRIC_WEAK = 255;
        public static final int DEVICE_CREDENTIAL = 32768;

        @android.annotation.SystemApi
        public static final int EMPTY_SET = 0;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Types {
        }
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BiometricError {
    }

    public static java.lang.String authenticatorToStr(int i) {
        switch (i) {
            case 15:
                return "BIOMETRIC_STRONG";
            case 255:
                return "BIOMETRIC_WEAK";
            case 4095:
                return "BIOMETRIC_CONVENIENCE";
            case 32768:
                return "DEVICE_CREDENTIAL";
            default:
                return "Unknown authenticator type: " + i;
        }
    }

    public static class Strings {
        int mAuthenticators;
        private final android.content.Context mContext;
        private final android.hardware.biometrics.IAuthService mService;

        private Strings(android.content.Context context, android.hardware.biometrics.IAuthService iAuthService, int i) {
            this.mContext = context;
            this.mService = iAuthService;
            this.mAuthenticators = i;
        }

        public java.lang.CharSequence getButtonLabel() {
            try {
                return this.mService.getButtonLabel(this.mContext.getUserId(), this.mContext.getOpPackageName(), this.mAuthenticators);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public java.lang.CharSequence getPromptMessage() {
            try {
                return this.mService.getPromptMessage(this.mContext.getUserId(), this.mContext.getOpPackageName(), this.mAuthenticators);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public java.lang.CharSequence getSettingName() {
            try {
                return this.mService.getSettingName(this.mContext.getUserId(), this.mContext.getOpPackageName(), this.mAuthenticators);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public BiometricManager(android.content.Context context, android.hardware.biometrics.IAuthService iAuthService) {
        this.mContext = context;
        this.mService = iAuthService;
    }

    public java.util.List<android.hardware.biometrics.SensorProperties> getSensorProperties() {
        try {
            java.util.List<android.hardware.biometrics.SensorPropertiesInternal> sensorProperties = this.mService.getSensorProperties(this.mContext.getOpPackageName());
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<android.hardware.biometrics.SensorPropertiesInternal> it = sensorProperties.iterator();
            while (it.hasNext()) {
                arrayList.add(android.hardware.biometrics.SensorProperties.from(it.next()));
            }
            return arrayList;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.biometrics.BiometricTestSession createTestSession(int i) {
        try {
            return new android.hardware.biometrics.BiometricTestSession(this.mContext, i, new android.hardware.biometrics.BiometricTestSession.TestSessionProvider() { // from class: android.hardware.biometrics.BiometricManager$$ExternalSyntheticLambda0
                @Override // android.hardware.biometrics.BiometricTestSession.TestSessionProvider
                public final android.hardware.biometrics.ITestSession createTestSession(android.content.Context context, int i2, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback) {
                    android.hardware.biometrics.ITestSession lambda$createTestSession$0;
                    lambda$createTestSession$0 = android.hardware.biometrics.BiometricManager.this.lambda$createTestSession$0(context, i2, iTestSessionCallback);
                    return lambda$createTestSession$0;
                }
            });
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.hardware.biometrics.ITestSession lambda$createTestSession$0(android.content.Context context, int i, android.hardware.biometrics.ITestSessionCallback iTestSessionCallback) throws android.os.RemoteException {
        return this.mService.createTestSession(i, iTestSessionCallback, context.getOpPackageName());
    }

    public java.lang.String getUiPackage() {
        try {
            return this.mService.getUiPackage();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public int canAuthenticate() {
        int canAuthenticate = canAuthenticate(this.mContext.getUserId(), 255);
        com.android.internal.util.FrameworkStatsLog.write(354, false, 0, canAuthenticate);
        com.android.internal.util.FrameworkStatsLog.write(356, 4, this.mContext.getApplicationInfo().uid, this.mContext.getApplicationInfo().targetSdkVersion);
        return canAuthenticate;
    }

    public int canAuthenticate(int i) {
        int canAuthenticate = canAuthenticate(this.mContext.getUserId(), i);
        com.android.internal.util.FrameworkStatsLog.write(354, true, i, canAuthenticate);
        return canAuthenticate;
    }

    public int canAuthenticate(int i, int i2) {
        if (this.mService != null) {
            try {
                return this.mService.canAuthenticate(this.mContext.getOpPackageName(), i, i2);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.util.Slog.w(TAG, "canAuthenticate(): Service not connected");
        return 1;
    }

    public android.hardware.biometrics.BiometricManager.Strings getStrings(int i) {
        return new android.hardware.biometrics.BiometricManager.Strings(this.mContext, this.mService, i);
    }

    public boolean hasEnrolledBiometrics(int i) {
        if (this.mService == null) {
            return false;
        }
        try {
            return this.mService.hasEnrolledBiometrics(i, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Remote exception in hasEnrolledBiometrics(): " + e);
            return false;
        }
    }

    public void registerEnabledOnKeyguardCallback(android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) {
        if (this.mService != null) {
            try {
                this.mService.registerEnabledOnKeyguardCallback(iBiometricEnabledOnKeyguardCallback);
                return;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.util.Slog.w(TAG, "registerEnabledOnKeyguardCallback(): Service not connected");
    }

    public void registerAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) {
        if (this.mService != null) {
            try {
                this.mService.registerAuthenticationStateListener(authenticationStateListener);
                return;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.util.Slog.w(TAG, "registerAuthenticationStateListener(): Service not connected");
    }

    public void unregisterAuthenticationStateListener(android.hardware.biometrics.AuthenticationStateListener authenticationStateListener) {
        if (this.mService != null) {
            try {
                this.mService.unregisterAuthenticationStateListener(authenticationStateListener);
                return;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.util.Slog.w(TAG, "unregisterAuthenticationStateListener(): Service not connected");
    }

    public void invalidateAuthenticatorIds(int i, int i2, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
        if (this.mService != null) {
            try {
                this.mService.invalidateAuthenticatorIds(i, i2, iInvalidationCallback);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long[] getAuthenticatorIds() {
        return getAuthenticatorIds(android.os.UserHandle.myUserId());
    }

    public long[] getAuthenticatorIds(int i) {
        if (this.mService != null) {
            try {
                return this.mService.getAuthenticatorIds(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.util.Slog.w(TAG, "getAuthenticatorIds(): Service not connected");
        return new long[0];
    }

    public void resetLockoutTimeBound(android.os.IBinder iBinder, java.lang.String str, int i, int i2, byte[] bArr) {
        if (this.mService != null) {
            try {
                this.mService.resetLockoutTimeBound(iBinder, str, i, i2, bArr);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void resetLockout(int i, byte[] bArr) {
        if (this.mService != null) {
            try {
                this.mService.resetLockout(i, bArr);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long getLastAuthenticationTime(int i) {
        if (i == 0 || (GET_LAST_AUTH_TIME_ALLOWED_AUTHENTICATORS & i) != i) {
            throw new java.lang.IllegalArgumentException("Only BIOMETRIC_STRONG and DEVICE_CREDENTIAL authenticators may be used.");
        }
        if (this.mService != null) {
            try {
                return this.mService.getLastAuthenticationTime(android.os.UserHandle.myUserId(), i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return -1L;
    }
}
