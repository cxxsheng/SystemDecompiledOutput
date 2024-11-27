package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class FaceRevokeChallengeClient extends com.android.server.biometrics.sensors.RevokeChallengeClient<com.android.server.biometrics.sensors.face.aidl.AidlSession> {
    private static final java.lang.String TAG = "FaceRevokeChallengeClient";
    private final long mChallenge;

    public FaceRevokeChallengeClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> supplier, @android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, long j) {
        super(context, supplier, iBinder, i, str, i2, biometricLogger, biometricContext);
        this.mChallenge = j;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            getFreshDaemon().getSession().revokeChallenge(this.mChallenge);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to revokeChallenge", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    void onChallengeRevoked(int i, int i2, long j) {
        this.mCallback.onClientFinished(this, j == this.mChallenge);
    }
}
