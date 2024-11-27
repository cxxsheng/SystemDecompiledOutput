package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
public class FingerprintResetLockoutClient extends com.android.server.biometrics.sensors.HalClientMonitor<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> implements com.android.server.biometrics.sensors.ErrorConsumer {
    private static final java.lang.String TAG = "FingerprintResetLockoutClient";
    private final int mBiometricStrength;
    private final android.hardware.keymaster.HardwareAuthToken mHardwareAuthToken;
    private final com.android.server.biometrics.sensors.LockoutTracker mLockoutCache;
    private final com.android.server.biometrics.sensors.LockoutResetDispatcher mLockoutResetDispatcher;

    public FingerprintResetLockoutClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> supplier, int i, java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutTracker lockoutTracker, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, int i3) {
        super(context, supplier, null, null, i, str, 0, i2, biometricLogger, biometricContext);
        this.mHardwareAuthToken = bArr == null ? null : com.android.server.biometrics.HardwareAuthTokenUtils.toHardwareAuthToken(bArr);
        this.mLockoutCache = lockoutTracker;
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mBiometricStrength = i3;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            getFreshDaemon().getSession().resetLockout(this.mHardwareAuthToken);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to reset lockout", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public boolean interruptsPrecedingClients() {
        return true;
    }

    void onLockoutCleared() {
        resetLocalLockoutStateToNone(getSensorId(), getTargetUserId(), this.mLockoutCache, this.mLockoutResetDispatcher, getBiometricContext().getAuthSessionCoordinator(), this.mBiometricStrength, getRequestId());
        this.mCallback.onClientFinished(this, true);
    }

    static void resetLocalLockoutStateToNone(int i, int i2, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutTracker lockoutTracker, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthSessionCoordinator authSessionCoordinator, int i3, long j) {
        lockoutTracker.resetFailedAttemptsForUser(true, i2);
        lockoutTracker.setLockoutModeForUser(i2, 0);
        lockoutResetDispatcher.notifyLockoutResetCallbacks(i);
        authSessionCoordinator.resetLockoutFor(i2, i3, j);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 12;
    }

    @Override // com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        android.util.Slog.e(TAG, "Error during resetLockout: " + i);
        this.mCallback.onClientFinished(this, false);
    }
}
