package com.android.server.biometrics.sensors.face.hidl;

/* loaded from: classes.dex */
public class FaceGenerateChallengeClient extends com.android.server.biometrics.sensors.GenerateChallengeClient<android.hardware.biometrics.face.V1_0.IBiometricsFace> {
    static final int CHALLENGE_TIMEOUT_SEC = 600;
    private static final com.android.server.biometrics.sensors.ClientMonitorCallback EMPTY_CALLBACK = new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.FaceGenerateChallengeClient.1
    };
    private static final java.lang.String TAG = "FaceGenerateChallengeClient";
    private java.lang.Long mChallengeResult;
    private final long mCreatedAt;
    private java.util.List<android.hardware.face.IFaceServiceReceiver> mWaiting;

    FaceGenerateChallengeClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.face.V1_0.IBiometricsFace> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, long j) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, str, i2, biometricLogger, biometricContext);
        this.mCreatedAt = j;
        this.mWaiting = new java.util.ArrayList();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        this.mChallengeResult = null;
        try {
            try {
                this.mChallengeResult = java.lang.Long.valueOf(getFreshDaemon().generateChallenge(600).value);
                sendChallengeResult(getListener(), this.mCallback);
                java.util.Iterator<android.hardware.face.IFaceServiceReceiver> it = this.mWaiting.iterator();
                while (it.hasNext()) {
                    sendChallengeResult(new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(it.next()), EMPTY_CALLBACK);
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "generateChallenge failed", e);
                this.mCallback.onClientFinished(this, false);
            }
        } finally {
            this.mWaiting = null;
        }
    }

    public long getCreatedAt() {
        return this.mCreatedAt;
    }

    public void reuseResult(@android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver) {
        if (this.mWaiting != null) {
            this.mWaiting.add(iFaceServiceReceiver);
        } else {
            sendChallengeResult(new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), EMPTY_CALLBACK);
        }
    }

    private void sendChallengeResult(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        com.android.internal.util.Preconditions.checkState(this.mChallengeResult != null, "result not available");
        try {
            clientMonitorCallbackConverter.onChallengeGenerated(getSensorId(), getTargetUserId(), this.mChallengeResult.longValue());
            clientMonitorCallback.onClientFinished(this, true);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
            clientMonitorCallback.onClientFinished(this, false);
        }
    }
}
