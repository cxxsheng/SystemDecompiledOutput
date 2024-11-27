package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
public class FingerprintRemovalClient extends com.android.server.biometrics.sensors.RemovalClient<android.hardware.fingerprint.Fingerprint, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> {
    private static final java.lang.String TAG = "FingerprintRemovalClient";
    private final int[] mBiometricIds;

    public FingerprintRemovalClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int[] iArr, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils<android.hardware.fingerprint.Fingerprint> biometricUtils, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull java.util.Map<java.lang.Integer, java.lang.Long> map) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, str, biometricUtils, i2, biometricLogger, biometricContext, map);
        this.mBiometricIds = iArr;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            getFreshDaemon().getSession().removeEnrollments(this.mBiometricIds);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting remove", e);
            this.mCallback.onClientFinished(this, false);
        }
    }
}
