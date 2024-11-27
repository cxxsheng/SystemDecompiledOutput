package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public final class FingerprintAuthenticator extends android.hardware.biometrics.IBiometricAuthenticator.Stub {
    private final android.hardware.fingerprint.IFingerprintService mFingerprintService;
    private final int mSensorId;

    public FingerprintAuthenticator(android.hardware.fingerprint.IFingerprintService iFingerprintService, int i) {
        this.mFingerprintService = iFingerprintService;
        this.mSensorId = i;
    }

    public android.hardware.biometrics.ITestSession createTestSession(@android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        return this.mFingerprintService.createTestSession(this.mSensorId, iTestSessionCallback, str);
    }

    public android.hardware.biometrics.SensorPropertiesInternal getSensorProperties(@android.annotation.NonNull java.lang.String str) throws android.os.RemoteException {
        return this.mFingerprintService.getSensorProperties(this.mSensorId, str);
    }

    public byte[] dumpSensorServiceStateProto(boolean z) throws android.os.RemoteException {
        return this.mFingerprintService.dumpSensorServiceStateProto(this.mSensorId, z);
    }

    public void prepareForAuthentication(boolean z, android.os.IBinder iBinder, long j, int i, android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, java.lang.String str, long j2, int i2, boolean z2, boolean z3) throws android.os.RemoteException {
        this.mFingerprintService.prepareForAuthentication(iBinder, j, iBiometricSensorReceiver, new android.hardware.fingerprint.FingerprintAuthenticateOptions.Builder().setSensorId(this.mSensorId).setUserId(i).setOpPackageName(str).build(), j2, i2, z2, z3);
    }

    public void startPreparedClient(int i) throws android.os.RemoteException {
        this.mFingerprintService.startPreparedClient(this.mSensorId, i);
    }

    public void cancelAuthenticationFromService(android.os.IBinder iBinder, java.lang.String str, long j) throws android.os.RemoteException {
        this.mFingerprintService.cancelAuthenticationFromService(this.mSensorId, iBinder, str, j);
    }

    public boolean isHardwareDetected(java.lang.String str) throws android.os.RemoteException {
        return this.mFingerprintService.isHardwareDetected(this.mSensorId, str);
    }

    public boolean hasEnrolledTemplates(int i, java.lang.String str) throws android.os.RemoteException {
        return this.mFingerprintService.hasEnrolledFingerprints(this.mSensorId, i, str);
    }

    public int getLockoutModeForUser(int i) throws android.os.RemoteException {
        return this.mFingerprintService.getLockoutModeForUser(this.mSensorId, i);
    }

    public void invalidateAuthenticatorId(int i, android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) throws android.os.RemoteException {
        this.mFingerprintService.invalidateAuthenticatorId(this.mSensorId, i, iInvalidationCallback);
    }

    public long getAuthenticatorId(int i) throws android.os.RemoteException {
        return this.mFingerprintService.getAuthenticatorId(this.mSensorId, i);
    }

    public void resetLockout(android.os.IBinder iBinder, java.lang.String str, int i, byte[] bArr) throws android.os.RemoteException {
        this.mFingerprintService.resetLockout(iBinder, this.mSensorId, i, bArr, str);
    }
}
