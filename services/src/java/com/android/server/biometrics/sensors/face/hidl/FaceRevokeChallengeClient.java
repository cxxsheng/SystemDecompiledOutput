package com.android.server.biometrics.sensors.face.hidl;

/* loaded from: classes.dex */
public class FaceRevokeChallengeClient extends com.android.server.biometrics.sensors.RevokeChallengeClient<android.hardware.biometrics.face.V1_0.IBiometricsFace> {
    private static final java.lang.String TAG = "FaceRevokeChallengeClient";

    FaceRevokeChallengeClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.face.V1_0.IBiometricsFace> supplier, @android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        super(context, supplier, iBinder, i, str, i2, biometricLogger, biometricContext);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            getFreshDaemon().revokeChallenge();
            this.mCallback.onClientFinished(this, true);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "revokeChallenge failed", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
