package com.android.server.biometrics.sensors.iris;

/* loaded from: classes.dex */
public final class IrisAuthenticator extends android.hardware.biometrics.IBiometricAuthenticator.Stub {
    private final android.hardware.iris.IIrisService mIrisService;

    public IrisAuthenticator(android.hardware.iris.IIrisService iIrisService, int i) {
        this.mIrisService = iIrisService;
    }

    public android.hardware.biometrics.ITestSession createTestSession(@android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        return null;
    }

    public android.hardware.biometrics.SensorPropertiesInternal getSensorProperties(@android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        return null;
    }

    public byte[] dumpSensorServiceStateProto(boolean z) throws android.os.RemoteException {
        return null;
    }

    public void prepareForAuthentication(boolean z, android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, java.lang.String str, long j2, int i2, boolean z2, boolean z3) throws android.os.RemoteException {
    }

    public void startPreparedClient(int i) throws android.os.RemoteException {
    }

    public void cancelAuthenticationFromService(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
    }

    public boolean isHardwareDetected(java.lang.String str) throws android.os.RemoteException {
        return false;
    }

    public boolean hasEnrolledTemplates(int i, java.lang.String str) throws android.os.RemoteException {
        return false;
    }

    public int getLockoutModeForUser(int i) throws android.os.RemoteException {
        return 0;
    }

    public void invalidateAuthenticatorId(int i, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
    }

    public long getAuthenticatorId(int i) throws android.os.RemoteException {
        return 0L;
    }

    public void resetLockout(android.os.IBinder iBinder, java.lang.String str, int i, byte[] bArr) throws android.os.RemoteException {
    }
}
