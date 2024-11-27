package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
public class FingerprintGenerateChallengeClient extends com.android.server.biometrics.sensors.GenerateChallengeClient<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> {
    private static final java.lang.String TAG = "FingerprintGenerateChallengeClient";

    FingerprintGenerateChallengeClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, str, i2, biometricLogger, biometricContext);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            try {
                getListener().onChallengeGenerated(getSensorId(), getTargetUserId(), getFreshDaemon().preEnroll());
                this.mCallback.onClientFinished(this, true);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception", e);
                this.mCallback.onClientFinished(this, false);
            }
        } catch (android.os.RemoteException e2) {
            android.util.Slog.e(TAG, "preEnroll failed", e2);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
