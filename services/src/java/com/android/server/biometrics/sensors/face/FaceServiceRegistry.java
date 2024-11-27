package com.android.server.biometrics.sensors.face;

/* loaded from: classes.dex */
public class FaceServiceRegistry extends com.android.server.biometrics.sensors.BiometricServiceRegistry<com.android.server.biometrics.sensors.face.ServiceProvider, android.hardware.face.FaceSensorPropertiesInternal, android.hardware.face.IFaceAuthenticatorsRegisteredCallback> {
    private static final java.lang.String TAG = "FaceServiceRegistry";

    @android.annotation.NonNull
    private final android.hardware.face.IFaceService mService;

    public FaceServiceRegistry(@android.annotation.NonNull android.hardware.face.IFaceService iFaceService, @android.annotation.Nullable java.util.function.Supplier<android.hardware.biometrics.IBiometricService> supplier) {
        super(supplier);
        this.mService = iFaceService;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.biometrics.sensors.BiometricServiceRegistry
    public void registerService(@android.annotation.NonNull android.hardware.biometrics.IBiometricService iBiometricService, @android.annotation.NonNull android.hardware.face.FaceSensorPropertiesInternal faceSensorPropertiesInternal) {
        try {
            iBiometricService.registerAuthenticator(faceSensorPropertiesInternal.sensorId, 8, com.android.server.biometrics.Utils.propertyStrengthToAuthenticatorStrength(faceSensorPropertiesInternal.sensorStrength), new com.android.server.biometrics.sensors.face.FaceAuthenticator(this.mService, faceSensorPropertiesInternal.sensorId));
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when registering sensorId: " + faceSensorPropertiesInternal.sensorId);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.biometrics.sensors.BiometricServiceRegistry
    public void invokeRegisteredCallback(@android.annotation.NonNull android.hardware.face.IFaceAuthenticatorsRegisteredCallback iFaceAuthenticatorsRegisteredCallback, @android.annotation.NonNull java.util.List<android.hardware.face.FaceSensorPropertiesInternal> list) throws android.os.RemoteException {
        iFaceAuthenticatorsRegisteredCallback.onAllAuthenticatorsRegistered(list);
    }
}
