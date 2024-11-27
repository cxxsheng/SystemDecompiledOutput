package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
class FaceInternalEnumerateClient extends com.android.server.biometrics.sensors.InternalEnumerateClient<com.android.server.biometrics.sensors.face.aidl.AidlSession> {
    private static final java.lang.String TAG = "FaceInternalEnumerateClient";

    FaceInternalEnumerateClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> supplier, @android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<android.hardware.face.Face> list, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils<android.hardware.face.Face> biometricUtils, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        super(context, supplier, iBinder, i, str, list, biometricUtils, i2, biometricLogger, biometricContext);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            getFreshDaemon().getSession().enumerateEnrollments();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting enumerate", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}