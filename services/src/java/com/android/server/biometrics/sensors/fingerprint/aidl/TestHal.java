package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
public class TestHal extends android.hardware.biometrics.fingerprint.IFingerprint.Stub {
    private static final java.lang.String TAG = "fingerprint.aidl.TestHal";

    public int getInterfaceVersion() {
        return 3;
    }

    public java.lang.String getInterfaceHash() {
        return "637371b53fb7faf9bd43aa51b72c23852d6e6d96";
    }

    public android.hardware.biometrics.fingerprint.SensorProps[] getSensorProps() {
        android.util.Slog.w(TAG, "getSensorProps");
        return new android.hardware.biometrics.fingerprint.SensorProps[0];
    }

    public android.hardware.biometrics.fingerprint.ISession createSession(int i, int i2, final android.hardware.biometrics.fingerprint.ISessionCallback iSessionCallback) {
        android.util.Slog.w(TAG, "createSession, sensorId: " + i + " userId: " + i2);
        return new android.hardware.biometrics.fingerprint.ISession.Stub() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.1
            public int getInterfaceVersion() {
                return 3;
            }

            public java.lang.String getInterfaceHash() {
                return "637371b53fb7faf9bd43aa51b72c23852d6e6d96";
            }

            public void generateChallenge() throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "generateChallenge");
                iSessionCallback.onChallengeGenerated(0L);
            }

            public void revokeChallenge(long j) throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "revokeChallenge: " + j);
                iSessionCallback.onChallengeRevoked(j);
            }

            public android.hardware.biometrics.common.ICancellationSignal enroll(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "enroll");
                return new android.hardware.biometrics.common.ICancellationSignal.Stub() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.1.1
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
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "authenticate");
                return new android.hardware.biometrics.common.ICancellationSignal.Stub() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.1.2
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
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "detectInteraction");
                return new android.hardware.biometrics.common.ICancellationSignal.Stub() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.1.3
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
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "enumerateEnrollments");
                iSessionCallback.onEnrollmentsEnumerated(new int[0]);
            }

            public void removeEnrollments(int[] iArr) throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "removeEnrollments");
                iSessionCallback.onEnrollmentsRemoved(iArr);
            }

            public void getAuthenticatorId() throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "getAuthenticatorId");
                iSessionCallback.onAuthenticatorIdRetrieved(0L);
            }

            public void invalidateAuthenticatorId() throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "invalidateAuthenticatorId");
                iSessionCallback.onAuthenticatorIdInvalidated(0L);
            }

            public void resetLockout(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "resetLockout");
                iSessionCallback.onLockoutCleared();
            }

            public void close() throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "close");
                iSessionCallback.onSessionClosed();
            }

            public void onPointerDown(int i3, int i4, int i5, float f, float f2) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "onPointerDown");
            }

            public void onPointerUp(int i3) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "onPointerUp");
            }

            public void onUiReady() {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "onUiReady");
            }

            public android.hardware.biometrics.common.ICancellationSignal authenticateWithContext(long j, android.hardware.biometrics.common.OperationContext operationContext) {
                return authenticate(j);
            }

            public android.hardware.biometrics.common.ICancellationSignal enrollWithContext(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, android.hardware.biometrics.common.OperationContext operationContext) {
                return enroll(hardwareAuthToken);
            }

            public android.hardware.biometrics.common.ICancellationSignal detectInteractionWithContext(android.hardware.biometrics.common.OperationContext operationContext) {
                return detectInteraction();
            }

            public void onPointerDownWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
                onPointerDown(pointerContext.pointerId, (int) pointerContext.x, (int) pointerContext.y, pointerContext.minor, pointerContext.major);
            }

            public void onPointerUpWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
                onPointerUp(pointerContext.pointerId);
            }

            public void onContextChanged(android.hardware.biometrics.common.OperationContext operationContext) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "onContextChanged");
            }

            public void onPointerCancelWithContext(android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "onPointerCancelWithContext");
            }

            public void setIgnoreDisplayTouches(boolean z) {
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.aidl.TestHal.TAG, "setIgnoreDisplayTouches");
            }
        };
    }
}
