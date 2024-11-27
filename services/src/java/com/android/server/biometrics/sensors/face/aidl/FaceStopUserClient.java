package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class FaceStopUserClient extends com.android.server.biometrics.sensors.StopUserClient<android.hardware.biometrics.face.ISession> {
    private static final java.lang.String TAG = "FaceStopUserClient";

    public FaceStopUserClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.face.ISession> supplier, @android.annotation.Nullable android.os.IBinder iBinder, int i, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback userStoppedCallback) {
        super(context, supplier, iBinder, i, i2, biometricLogger, biometricContext, userStoppedCallback);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            getFreshDaemon().close();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
            getCallback().onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
    }
}
