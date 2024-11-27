package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
public class FingerprintInternalCleanupClient extends com.android.server.biometrics.sensors.InternalCleanupClient<android.hardware.fingerprint.Fingerprint, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> {
    public FingerprintInternalCleanupClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> supplier, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.FingerprintUtils fingerprintUtils, @android.annotation.NonNull java.util.Map<java.lang.Integer, java.lang.Long> map) {
        super(context, supplier, i, str, i2, biometricLogger, biometricContext, fingerprintUtils, map);
    }

    @Override // com.android.server.biometrics.sensors.InternalCleanupClient
    protected com.android.server.biometrics.sensors.InternalEnumerateClient<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> getEnumerateClient(android.content.Context context, java.util.function.Supplier<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> supplier, android.os.IBinder iBinder, int i, java.lang.String str, java.util.List<android.hardware.fingerprint.Fingerprint> list, com.android.server.biometrics.sensors.BiometricUtils<android.hardware.fingerprint.Fingerprint> biometricUtils, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        return new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintInternalEnumerateClient(context, supplier, iBinder, i, str, list, biometricUtils, i2, biometricLogger.swapAction(context, 3), biometricContext);
    }

    @Override // com.android.server.biometrics.sensors.InternalCleanupClient
    protected com.android.server.biometrics.sensors.RemovalClient<android.hardware.fingerprint.Fingerprint, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> getRemovalClient(android.content.Context context, java.util.function.Supplier<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> supplier, android.os.IBinder iBinder, int i, int i2, java.lang.String str, com.android.server.biometrics.sensors.BiometricUtils<android.hardware.fingerprint.Fingerprint> biometricUtils, int i3, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, java.util.Map<java.lang.Integer, java.lang.Long> map) {
        return new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintRemovalClient(context, supplier, iBinder, null, new int[]{i}, i2, str, biometricUtils, i3, biometricLogger.swapAction(context, 4), biometricContext, map);
    }

    @Override // com.android.server.biometrics.sensors.InternalCleanupClient
    protected void onAddUnknownTemplate(int i, @android.annotation.NonNull android.hardware.biometrics.BiometricAuthenticator.Identifier identifier) {
        com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(getSensorId()).addBiometricForUser(getContext(), getTargetUserId(), (android.hardware.fingerprint.Fingerprint) identifier);
    }
}
