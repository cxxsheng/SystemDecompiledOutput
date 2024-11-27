package com.android.server.biometrics.sensors.face.hidl;

/* loaded from: classes.dex */
public class FaceGetFeatureClient extends com.android.server.biometrics.sensors.HalClientMonitor<android.hardware.biometrics.face.V1_0.IBiometricsFace> {
    private static final java.lang.String TAG = "FaceGetFeatureClient";
    private final int mFaceId;
    private final int mFeature;
    private boolean mValue;

    FaceGetFeatureClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.face.V1_0.IBiometricsFace> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, int i3, int i4) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, str, 0, i2, biometricLogger, biometricContext);
        this.mFeature = i3;
        this.mFaceId = i4;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
        try {
            getListener().onFeatureGet(false, new int[0], new boolean[0]);
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
            android.hardware.biometrics.face.V1_0.OptionalBool feature = getFreshDaemon().getFeature(this.mFeature, this.mFaceId);
            int[] iArr = {this.mFeature};
            boolean[] zArr = {feature.value};
            this.mValue = feature.value;
            getListener().onFeatureGet(feature.status == 0, iArr, zArr);
            this.mCallback.onClientFinished(this, true);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to getFeature", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    boolean getValue() {
        return this.mValue;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 9;
    }
}
