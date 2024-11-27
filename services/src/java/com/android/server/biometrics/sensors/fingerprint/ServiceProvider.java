package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public interface ServiceProvider extends com.android.server.biometrics.sensors.BiometricServiceProvider<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> {
    void cancelAuthentication(int i, @android.annotation.NonNull android.os.IBinder iBinder, long j);

    void cancelEnrollment(int i, @android.annotation.NonNull android.os.IBinder iBinder, long j);

    @android.annotation.NonNull
    android.hardware.biometrics.ITestSession createTestSession(int i, @android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull java.lang.String str);

    @android.annotation.NonNull
    java.util.List<android.hardware.fingerprint.Fingerprint> getEnrolledFingerprints(int i, int i2);

    void onPointerDown(long j, int i, android.hardware.biometrics.fingerprint.PointerContext pointerContext);

    void onPointerUp(long j, int i, android.hardware.biometrics.fingerprint.PointerContext pointerContext);

    void onPowerPressed();

    void onUdfpsUiEvent(int i, long j, int i2);

    void rename(int i, int i2, int i3, @android.annotation.NonNull java.lang.String str);

    long scheduleAuthenticate(@android.annotation.NonNull android.os.IBinder iBinder, long j, int i, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, boolean z, int i2, boolean z2);

    void scheduleAuthenticate(@android.annotation.NonNull android.os.IBinder iBinder, long j, int i, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, long j2, boolean z, int i2, boolean z2);

    long scheduleEnroll(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull byte[] bArr, int i2, @android.annotation.NonNull android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, @android.annotation.NonNull java.lang.String str, int i3, @android.annotation.NonNull android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions);

    long scheduleFingerDetect(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, int i);

    void scheduleGenerateChallenge(int i, int i2, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str);

    void scheduleInternalCleanup(int i, int i2, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback);

    void scheduleInternalCleanup(int i, int i2, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback, boolean z);

    void scheduleInvalidateAuthenticatorId(int i, int i2, @android.annotation.NonNull android.hardware.biometrics.IInvalidationCallback iInvalidationCallback);

    void scheduleRemove(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, int i2, int i3, @android.annotation.NonNull java.lang.String str);

    void scheduleRemoveAll(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, int i2, @android.annotation.NonNull java.lang.String str);

    void scheduleResetLockout(int i, int i2, @android.annotation.Nullable byte[] bArr);

    void scheduleRevokeChallenge(int i, int i2, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.lang.String str, long j);

    void setSidefpsController(@android.annotation.NonNull android.hardware.fingerprint.ISidefpsController iSidefpsController);

    void setUdfpsOverlayController(@android.annotation.NonNull android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController);

    void startPreparedClient(int i, int i2);

    default void scheduleWatchdog(int i) {
    }

    default void simulateVhalFingerDown(int i, int i2) {
    }
}
