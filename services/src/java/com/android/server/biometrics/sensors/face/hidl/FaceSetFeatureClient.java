package com.android.server.biometrics.sensors.face.hidl;

/* loaded from: classes.dex */
public class FaceSetFeatureClient extends com.android.server.biometrics.sensors.HalClientMonitor<android.hardware.biometrics.face.V1_0.IBiometricsFace> {
    private static final java.lang.String TAG = "FaceSetFeatureClient";
    private final boolean mEnabled;
    private final int mFaceId;
    private final int mFeature;
    private final java.util.ArrayList<java.lang.Byte> mHardwareAuthToken;

    FaceSetFeatureClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.face.V1_0.IBiometricsFace> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, int i3, boolean z, byte[] bArr, int i4) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, str, 0, i2, biometricLogger, biometricContext);
        this.mFeature = i3;
        this.mEnabled = z;
        this.mFaceId = i4;
        this.mHardwareAuthToken = new java.util.ArrayList<>();
        for (byte b : bArr) {
            this.mHardwareAuthToken.add(java.lang.Byte.valueOf(b));
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
        try {
            getListener().onFeatureSet(false, this.mFeature);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to send error", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            getListener().onFeatureSet(getFreshDaemon().setFeature(this.mFeature, this.mEnabled, this.mHardwareAuthToken, this.mFaceId) == 0, this.mFeature);
            this.mCallback.onClientFinished(this, true);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to set feature: " + this.mFeature + " to enabled: " + this.mEnabled, e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 8;
    }
}
