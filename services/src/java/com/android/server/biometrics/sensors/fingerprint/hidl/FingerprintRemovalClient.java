package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
class FingerprintRemovalClient extends com.android.server.biometrics.sensors.RemovalClient<android.hardware.fingerprint.Fingerprint, android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> {
    private static final java.lang.String TAG = "FingerprintRemovalClient";
    private final int mBiometricId;

    FingerprintRemovalClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils<android.hardware.fingerprint.Fingerprint> biometricUtils, int i3, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull java.util.Map<java.lang.Integer, java.lang.Long> map) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i2, str, biometricUtils, i3, biometricLogger, biometricContext, map);
        this.mBiometricId = i;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            getFreshDaemon().remove(getTargetUserId(), this.mBiometricId);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting remove", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
