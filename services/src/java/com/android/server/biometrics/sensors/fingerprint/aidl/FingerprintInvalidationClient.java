package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
public class FingerprintInvalidationClient extends com.android.server.biometrics.sensors.InvalidationClient<android.hardware.fingerprint.Fingerprint, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> {
    private static final java.lang.String TAG = "FingerprintInvalidationClient";

    public FingerprintInvalidationClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> supplier, int i, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull java.util.Map<java.lang.Integer, java.lang.Long> map, @android.annotation.NonNull android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
        super(context, supplier, i, i2, biometricLogger, biometricContext, map, iInvalidationCallback);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            getFreshDaemon().getSession().invalidateAuthenticatorId();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
