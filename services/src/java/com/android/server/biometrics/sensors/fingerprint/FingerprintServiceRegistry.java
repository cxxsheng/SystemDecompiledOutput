package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public class FingerprintServiceRegistry extends com.android.server.biometrics.sensors.BiometricServiceRegistry<com.android.server.biometrics.sensors.fingerprint.ServiceProvider, android.hardware.fingerprint.FingerprintSensorPropertiesInternal, android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback> {
    private static final java.lang.String TAG = "FingerprintServiceRegistry";

    @android.annotation.NonNull
    private final android.hardware.fingerprint.IFingerprintService mService;

    public FingerprintServiceRegistry(@android.annotation.NonNull android.hardware.fingerprint.IFingerprintService iFingerprintService, @android.annotation.Nullable java.util.function.Supplier<android.hardware.biometrics.IBiometricService> supplier) {
        super(supplier);
        this.mService = iFingerprintService;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.biometrics.sensors.BiometricServiceRegistry
    public void registerService(@android.annotation.NonNull android.hardware.biometrics.IBiometricService iBiometricService, @android.annotation.NonNull android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
        try {
            iBiometricService.registerAuthenticator(fingerprintSensorPropertiesInternal.sensorId, 2, com.android.server.biometrics.Utils.propertyStrengthToAuthenticatorStrength(fingerprintSensorPropertiesInternal.sensorStrength), new com.android.server.biometrics.sensors.fingerprint.FingerprintAuthenticator(this.mService, fingerprintSensorPropertiesInternal.sensorId));
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when registering sensorId: " + fingerprintSensorPropertiesInternal.sensorId);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.biometrics.sensors.BiometricServiceRegistry
    public void invokeRegisteredCallback(@android.annotation.NonNull android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallback, @android.annotation.NonNull java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> list) throws android.os.RemoteException {
        iFingerprintAuthenticatorsRegisteredCallback.onAllAuthenticatorsRegistered(list);
    }
}
