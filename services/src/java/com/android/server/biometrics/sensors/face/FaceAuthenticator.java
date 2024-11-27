package com.android.server.biometrics.sensors.face;

/* loaded from: classes.dex */
public final class FaceAuthenticator extends android.hardware.biometrics.IBiometricAuthenticator.Stub {
    private final android.hardware.face.IFaceService mFaceService;
    private final int mSensorId;

    public FaceAuthenticator(android.hardware.face.IFaceService iFaceService, int i) {
        this.mFaceService = iFaceService;
        this.mSensorId = i;
    }

    public android.hardware.biometrics.ITestSession createTestSession(@android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        return this.mFaceService.createTestSession(this.mSensorId, iTestSessionCallback, str);
    }

    public android.hardware.biometrics.SensorPropertiesInternal getSensorProperties(@android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        return this.mFaceService.getSensorProperties(this.mSensorId, str);
    }

    public byte[] dumpSensorServiceStateProto(boolean z) throws android.os.RemoteException {
        return this.mFaceService.dumpSensorServiceStateProto(this.mSensorId, z);
    }

    public void prepareForAuthentication(boolean z, android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, java.lang.String str, long j2, int i2, boolean z2, boolean z3) throws android.os.RemoteException {
        this.mFaceService.prepareForAuthentication(z, iBinder, j, iBiometricSensorReceiver, new android.hardware.face.FaceAuthenticateOptions.Builder().setUserId(i).setSensorId(this.mSensorId).setOpPackageName(str).build(), j2, i2, z2);
    }

    public void startPreparedClient(int i) throws android.os.RemoteException {
        this.mFaceService.startPreparedClient(this.mSensorId, i);
    }

    public void cancelAuthenticationFromService(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
        this.mFaceService.cancelAuthenticationFromService(this.mSensorId, iBinder, str, j);
    }

    public boolean isHardwareDetected(java.lang.String str) throws android.os.RemoteException {
        return this.mFaceService.isHardwareDetected(this.mSensorId, str);
    }

    public boolean hasEnrolledTemplates(int i, java.lang.String str) throws android.os.RemoteException {
        return this.mFaceService.hasEnrolledFaces(this.mSensorId, i, str);
    }

    public void invalidateAuthenticatorId(int i, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException {
        this.mFaceService.invalidateAuthenticatorId(this.mSensorId, i, iInvalidationCallback);
    }

    public int getLockoutModeForUser(int i) throws android.os.RemoteException {
        return this.mFaceService.getLockoutModeForUser(this.mSensorId, i);
    }

    public long getAuthenticatorId(int i) throws android.os.RemoteException {
        return this.mFaceService.getAuthenticatorId(this.mSensorId, i);
    }

    public void resetLockout(android.os.IBinder iBinder, java.lang.String str, int i, byte[] bArr) throws android.os.RemoteException {
        this.mFaceService.resetLockout(iBinder, this.mSensorId, i, bArr, str);
    }
}
