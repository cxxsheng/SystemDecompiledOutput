package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
public class FingerprintGenerateChallengeClient extends com.android.server.biometrics.sensors.GenerateChallengeClient<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> {
    private static final java.lang.String TAG = "FingerprintGenerateChallengeClient";

    public FingerprintGenerateChallengeClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, str, i2, biometricLogger, biometricContext);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            getFreshDaemon().getSession().generateChallenge();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to generateChallenge", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    void onChallengeGenerated(int i, int i2, long j) {
        try {
            getListener().onChallengeGenerated(i, i2, j);
            this.mCallback.onClientFinished(this, true);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to send challenge", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
