package com.android.server.biometrics.sensors.face;

/* loaded from: classes.dex */
public interface ServiceProvider extends com.android.server.biometrics.sensors.BiometricServiceProvider<android.hardware.face.FaceSensorPropertiesInternal> {
    void cancelAuthentication(int i, @android.annotation.NonNull android.os.IBinder iBinder, long j);

    void cancelEnrollment(int i, @android.annotation.NonNull android.os.IBinder iBinder, long j);

    void cancelFaceDetect(int i, @android.annotation.NonNull android.os.IBinder iBinder, long j);

    @android.annotation.NonNull
    android.hardware.biometrics.ITestSession createTestSession(int i, @android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull java.lang.String str);

    void dumpHal(int i, @android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.lang.String[] strArr);

    @android.annotation.NonNull
    java.util.List<android.hardware.face.Face> getEnrolledFaces(int i, int i2);

    long scheduleAuthenticate(@android.annotation.NonNull android.os.IBinder iBinder, long j, int i, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, boolean z, int i2, boolean z2);

    void scheduleAuthenticate(@android.annotation.NonNull android.os.IBinder iBinder, long j, int i, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, long j2, boolean z, int i2, boolean z2);

    long scheduleEnroll(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull byte[] bArr, int i2, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull int[] iArr, @android.annotation.Nullable android.view.Surface surface, boolean z, android.hardware.face.FaceEnrollOptions faceEnrollOptions);

    long scheduleFaceDetect(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, int i);

    void scheduleGenerateChallenge(int i, int i2, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str);

    void scheduleGetFeature(int i, @android.annotation.NonNull android.os.IBinder iBinder, int i2, int i3, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull java.lang.String str);

    void scheduleInternalCleanup(int i, int i2, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback);

    void scheduleInternalCleanup(int i, int i2, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback, boolean z);

    void scheduleRemove(int i, @android.annotation.NonNull android.os.IBinder iBinder, int i2, int i3, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str);

    void scheduleRemoveAll(int i, @android.annotation.NonNull android.os.IBinder iBinder, int i2, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str);

    void scheduleResetLockout(int i, int i2, @android.annotation.NonNull byte[] bArr);

    void scheduleRevokeChallenge(int i, int i2, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.lang.String str, long j);

    void scheduleSetFeature(int i, @android.annotation.NonNull android.os.IBinder iBinder, int i2, int i3, boolean z, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str);

    void startPreparedClient(int i, int i2);

    default void scheduleInvalidateAuthenticatorId(int i, int i2, @android.annotation.NonNull android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
        throw new java.lang.IllegalStateException("Providers that support invalidation must override this method");
    }

    default void scheduleWatchdog(int i) {
    }
}
