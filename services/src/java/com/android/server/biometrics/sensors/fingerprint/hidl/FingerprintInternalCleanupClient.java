package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
class FingerprintInternalCleanupClient extends com.android.server.biometrics.sensors.InternalCleanupClient<android.hardware.fingerprint.Fingerprint, android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> {
    FingerprintInternalCleanupClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> supplier, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils<android.hardware.fingerprint.Fingerprint> biometricUtils, @android.annotation.NonNull java.util.Map<java.lang.Integer, java.lang.Long> map) {
        super(context, supplier, i, str, i2, biometricLogger, biometricContext, biometricUtils, map);
    }

    @Override // com.android.server.biometrics.sensors.InternalCleanupClient
    protected com.android.server.biometrics.sensors.InternalEnumerateClient<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> getEnumerateClient(android.content.Context context, java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> supplier, android.os.IBinder iBinder, int i, java.lang.String str, java.util.List<android.hardware.fingerprint.Fingerprint> list, com.android.server.biometrics.sensors.BiometricUtils<android.hardware.fingerprint.Fingerprint> biometricUtils, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        return new com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintInternalEnumerateClient(context, supplier, iBinder, i, str, list, biometricUtils, i2, biometricLogger, biometricContext);
    }

    @Override // com.android.server.biometrics.sensors.InternalCleanupClient
    protected com.android.server.biometrics.sensors.RemovalClient<android.hardware.fingerprint.Fingerprint, android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> getRemovalClient(android.content.Context context, java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> supplier, android.os.IBinder iBinder, int i, int i2, java.lang.String str, com.android.server.biometrics.sensors.BiometricUtils<android.hardware.fingerprint.Fingerprint> biometricUtils, int i3, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, java.util.Map<java.lang.Integer, java.lang.Long> map) {
        return new com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintRemovalClient(context, supplier, iBinder, null, i, i2, str, biometricUtils, i3, biometricLogger, biometricContext, map);
    }
}
