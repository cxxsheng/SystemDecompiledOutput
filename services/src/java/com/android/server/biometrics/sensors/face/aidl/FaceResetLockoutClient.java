package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class FaceResetLockoutClient extends com.android.server.biometrics.sensors.HalClientMonitor<com.android.server.biometrics.sensors.face.aidl.AidlSession> implements com.android.server.biometrics.sensors.ErrorConsumer {
    private static final java.lang.String TAG = "FaceResetLockoutClient";
    private final int mBiometricStrength;
    private final android.hardware.keymaster.HardwareAuthToken mHardwareAuthToken;
    private final com.android.server.biometrics.sensors.LockoutResetDispatcher mLockoutResetDispatcher;
    private final com.android.server.biometrics.sensors.LockoutTracker mLockoutTracker;

    public FaceResetLockoutClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> supplier, int i, java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutTracker lockoutTracker, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, int i3) {
        super(context, supplier, null, null, i, str, 0, i2, biometricLogger, biometricContext);
        this.mHardwareAuthToken = com.android.server.biometrics.HardwareAuthTokenUtils.toHardwareAuthToken(bArr);
        this.mLockoutTracker = lockoutTracker;
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
            android.hardware.biometrics.face.ISession session = getFreshDaemon().getSession();
            session.resetLockout(this.mHardwareAuthToken);
            if (session instanceof com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter) {
                this.mCallback.onClientFinished(this, true);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to reset lockout", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    void onLockoutCleared() {
        resetLocalLockoutStateToNone(getSensorId(), getTargetUserId(), this.mLockoutTracker, this.mLockoutResetDispatcher, getBiometricContext().getAuthSessionCoordinator(), this.mBiometricStrength, getRequestId());
        this.mCallback.onClientFinished(this, true);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public boolean interruptsPrecedingClients() {
        return true;
    }

    static void resetLocalLockoutStateToNone(int i, int i2, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutTracker lockoutTracker, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthSessionCoordinator authSessionCoordinator, int i3, long j) {
        authSessionCoordinator.resetLockoutFor(i2, i3, j);
        lockoutTracker.setLockoutModeForUser(i2, 0);
        lockoutResetDispatcher.notifyLockoutResetCallbacks(i);
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
