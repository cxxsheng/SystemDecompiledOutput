package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class FaceInternalCleanupClient extends com.android.server.biometrics.sensors.InternalCleanupClient<android.hardware.face.Face, com.android.server.biometrics.sensors.face.aidl.AidlSession> {
    public FaceInternalCleanupClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> supplier, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils<android.hardware.face.Face> biometricUtils, @android.annotation.NonNull java.util.Map<java.lang.Integer, java.lang.Long> map) {
        super(context, supplier, i, str, i2, biometricLogger, biometricContext, biometricUtils, map);
    }

    @Override // com.android.server.biometrics.sensors.InternalCleanupClient
    protected com.android.server.biometrics.sensors.InternalEnumerateClient<com.android.server.biometrics.sensors.face.aidl.AidlSession> getEnumerateClient(android.content.Context context, java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> supplier, android.os.IBinder iBinder, int i, java.lang.String str, java.util.List<android.hardware.face.Face> list, com.android.server.biometrics.sensors.BiometricUtils<android.hardware.face.Face> biometricUtils, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        return new com.android.server.biometrics.sensors.face.aidl.FaceInternalEnumerateClient(context, supplier, iBinder, i, str, list, biometricUtils, i2, biometricLogger, biometricContext);
    }

    @Override // com.android.server.biometrics.sensors.InternalCleanupClient
    protected com.android.server.biometrics.sensors.RemovalClient<android.hardware.face.Face, com.android.server.biometrics.sensors.face.aidl.AidlSession> getRemovalClient(android.content.Context context, java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> supplier, android.os.IBinder iBinder, int i, int i2, java.lang.String str, com.android.server.biometrics.sensors.BiometricUtils<android.hardware.face.Face> biometricUtils, int i3, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, java.util.Map<java.lang.Integer, java.lang.Long> map) {
        return new com.android.server.biometrics.sensors.face.aidl.FaceRemovalClient(context, supplier, iBinder, null, new int[]{i}, i2, str, biometricUtils, i3, biometricLogger, biometricContext, map);
    }

    @Override // com.android.server.biometrics.sensors.InternalCleanupClient
    protected void onAddUnknownTemplate(int i, @android.annotation.NonNull android.hardware.biometrics.BiometricAuthenticator.Identifier identifier) {
        com.android.server.biometrics.sensors.face.FaceUtils.getInstance(getSensorId()).addBiometricForUser(getContext(), getTargetUserId(), (android.hardware.face.Face) identifier);
    }
}
