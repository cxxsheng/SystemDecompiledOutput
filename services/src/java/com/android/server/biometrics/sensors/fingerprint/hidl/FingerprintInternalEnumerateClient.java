package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
class FingerprintInternalEnumerateClient extends com.android.server.biometrics.sensors.InternalEnumerateClient<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> {
    private static final java.lang.String TAG = "FingerprintInternalEnumerateClient";

    FingerprintInternalEnumerateClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> supplier, @android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<android.hardware.fingerprint.Fingerprint> list, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils<android.hardware.fingerprint.Fingerprint> biometricUtils, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        super(context, supplier, iBinder, i, str, list, biometricUtils, i2, biometricLogger, biometricContext);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            getFreshDaemon().enumerate();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting enumerate", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
