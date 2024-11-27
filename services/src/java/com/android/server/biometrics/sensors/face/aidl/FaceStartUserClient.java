package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class FaceStartUserClient extends com.android.server.biometrics.sensors.StartUserClient<android.hardware.biometrics.face.IFace, android.hardware.biometrics.face.ISession> {
    private static final java.lang.String TAG = "FaceStartUserClient";

    @android.annotation.NonNull
    private final android.hardware.biometrics.face.ISessionCallback mSessionCallback;

    public FaceStartUserClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.face.IFace> supplier, @android.annotation.Nullable android.os.IBinder iBinder, int i, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull android.hardware.biometrics.face.ISessionCallback iSessionCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback<android.hardware.biometrics.face.ISession> userStartedCallback) {
        super(context, supplier, iBinder, i, i2, biometricLogger, biometricContext, userStartedCallback);
        this.mSessionCallback = iSessionCallback;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            android.hardware.biometrics.face.IFace freshDaemon = getFreshDaemon();
            int interfaceVersion = freshDaemon.getInterfaceVersion();
            android.hardware.biometrics.face.ISession createSession = freshDaemon.createSession(getSensorId(), getTargetUserId(), this.mSessionCallback);
            android.os.Binder.allowBlocking(createSession.asBinder());
            this.mUserStartedCallback.onUserStarted(getTargetUserId(), createSession, interfaceVersion);
            getCallback().onClientFinished(this, true);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
            getCallback().onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
    }
}
