package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class TestHal extends android.hardware.biometrics.face.IFace.Stub {
    private static final java.lang.String TAG = "face.aidl.TestHal";

    public int getInterfaceVersion() {
        return 4;
    }

    public java.lang.String getInterfaceHash() {
        return "c43fbb9be4a662cc9ace640dba21cccdb84c6c21";
    }

    public android.hardware.biometrics.face.SensorProps[] getSensorProps() {
        android.util.Slog.w(TAG, "getSensorProps");
        return new android.hardware.biometrics.face.SensorProps[0];
    }

    public android.hardware.biometrics.face.ISession createSession(int i, int i2, final android.hardware.biometrics.face.ISessionCallback iSessionCallback) {
        android.util.Slog.w(TAG, "createSession, sensorId: " + i + " userId: " + i2);
        return new android.hardware.biometrics.face.ISession.Stub() { // from class: com.android.server.biometrics.sensors.face.aidl.TestHal.1
            public int getInterfaceVersion() {
                return 4;
            }

            public java.lang.String getInterfaceHash() {
                return "c43fbb9be4a662cc9ace640dba21cccdb84c6c21";
            }

            public void generateChallenge() throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "generateChallenge");
                iSessionCallback.onChallengeGenerated(0L);
            }

            public void revokeChallenge(long j) throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "revokeChallenge: " + j);
                iSessionCallback.onChallengeRevoked(j);
            }

            public android.hardware.biometrics.face.EnrollmentStageConfig[] getEnrollmentConfig(byte b) {
                return new android.hardware.biometrics.face.EnrollmentStageConfig[0];
            }

            public android.hardware.biometrics.common.ICancellationSignal enroll(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, android.hardware.common.NativeHandle nativeHandle) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "enroll");
                return new android.hardware.biometrics.common.ICancellationSignal.Stub() { // from class: com.android.server.biometrics.sensors.face.aidl.TestHal.1.1
                    public void cancel() throws android.os.RemoteException {
                        iSessionCallback.onError((byte) 5, 0);
                    }

                    public int getInterfaceVersion() {
                        return 4;
                    }

                    public java.lang.String getInterfaceHash() {
                        return "8a6cd86630181a4df6f20056259ec200ffe39209";
                    }
                };
            }

            public android.hardware.biometrics.common.ICancellationSignal authenticate(long j) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "authenticate");
                return new android.hardware.biometrics.common.ICancellationSignal.Stub() { // from class: com.android.server.biometrics.sensors.face.aidl.TestHal.1.2
                    public void cancel() throws android.os.RemoteException {
                        iSessionCallback.onError((byte) 5, 0);
                    }

                    public int getInterfaceVersion() {
                        return 4;
                    }

                    public java.lang.String getInterfaceHash() {
                        return "8a6cd86630181a4df6f20056259ec200ffe39209";
                    }
                };
            }

            public android.hardware.biometrics.common.ICancellationSignal detectInteraction() {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "detectInteraction");
                return new android.hardware.biometrics.common.ICancellationSignal.Stub() { // from class: com.android.server.biometrics.sensors.face.aidl.TestHal.1.3
                    public void cancel() throws android.os.RemoteException {
                        iSessionCallback.onError((byte) 5, 0);
                    }

                    public int getInterfaceVersion() {
                        return 4;
                    }

                    public java.lang.String getInterfaceHash() {
                        return "8a6cd86630181a4df6f20056259ec200ffe39209";
                    }
                };
            }

            public void enumerateEnrollments() throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "enumerateEnrollments");
                iSessionCallback.onEnrollmentsEnumerated(new int[0]);
            }

            public void removeEnrollments(int[] iArr) throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "removeEnrollments");
                iSessionCallback.onEnrollmentsRemoved(iArr);
            }

            public void getFeatures() throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "getFeatures");
                iSessionCallback.onFeaturesRetrieved(new byte[0]);
            }

            public void setFeature(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, byte b, boolean z) throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "setFeature");
                iSessionCallback.onFeatureSet(b);
            }

            public void getAuthenticatorId() throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "getAuthenticatorId");
                iSessionCallback.onAuthenticatorIdRetrieved(0L);
            }

            public void invalidateAuthenticatorId() throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "invalidateAuthenticatorId");
                iSessionCallback.onAuthenticatorIdInvalidated(0L);
            }

            public void resetLockout(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "resetLockout");
                iSessionCallback.onLockoutCleared();
            }

            public void close() throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "close");
                iSessionCallback.onSessionClosed();
            }

            public android.hardware.biometrics.common.ICancellationSignal authenticateWithContext(long j, android.hardware.biometrics.common.OperationContext operationContext) {
                return authenticate(j);
            }

            public android.hardware.biometrics.common.ICancellationSignal enrollWithContext(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, byte b, byte[] bArr, android.hardware.common.NativeHandle nativeHandle, android.hardware.biometrics.common.OperationContext operationContext) {
                return enroll(hardwareAuthToken, b, bArr, nativeHandle);
            }

            public android.hardware.biometrics.common.ICancellationSignal detectInteractionWithContext(android.hardware.biometrics.common.OperationContext operationContext) {
                return detectInteraction();
            }

            public void onContextChanged(android.hardware.biometrics.common.OperationContext operationContext) {
                android.util.Slog.w(com.android.server.biometrics.sensors.face.aidl.TestHal.TAG, "onContextChanged");
            }

            public android.hardware.biometrics.common.ICancellationSignal enrollWithOptions(android.hardware.biometrics.face.FaceEnrollOptions faceEnrollOptions) {
                return enroll(faceEnrollOptions.hardwareAuthToken, faceEnrollOptions.enrollmentType, faceEnrollOptions.features, faceEnrollOptions.nativeHandlePreview);
            }
        };
    }
}
