package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
public class FingerprintRevokeChallengeClient extends com.android.server.biometrics.sensors.RevokeChallengeClient<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> {
    private static final java.lang.String TAG = "FingerprintRevokeChallengeClient";

    FingerprintRevokeChallengeClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> supplier, @android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        super(context, supplier, iBinder, i, str, i2, biometricLogger, biometricContext);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            getFreshDaemon().postEnroll();
            this.mCallback.onClientFinished(this, true);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "revokeChallenge/postEnroll failed", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
