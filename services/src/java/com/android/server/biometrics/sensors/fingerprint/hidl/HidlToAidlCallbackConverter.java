package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
public class HidlToAidlCallbackConverter extends android.hardware.biometrics.fingerprint.V2_2.IBiometricsFingerprintClientCallback.Stub {
    final com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler mAidlResponseHandler;

    public HidlToAidlCallbackConverter(@android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler aidlResponseHandler) {
        this.mAidlResponseHandler = aidlResponseHandler;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public void onEnrollResult(long j, int i, int i2, int i3) {
        this.mAidlResponseHandler.onEnrollmentProgress(i, i3);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public void onAcquired(long j, int i, int i2) {
        onAcquired_2_2(j, i, i2);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_2.IBiometricsFingerprintClientCallback
    public void onAcquired_2_2(long j, int i, int i2) {
        this.mAidlResponseHandler.onAcquired(i, i2);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public void onAuthenticated(long j, int i, int i2, java.util.ArrayList<java.lang.Byte> arrayList) {
        if (i != 0) {
            byte[] bArr = new byte[arrayList.size()];
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                bArr[i3] = arrayList.get(i3).byteValue();
            }
            this.mAidlResponseHandler.onAuthenticationSucceeded(i, com.android.server.biometrics.HardwareAuthTokenUtils.toHardwareAuthToken(bArr));
            return;
        }
        this.mAidlResponseHandler.onAuthenticationFailed();
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public void onError(long j, int i, int i2) {
        this.mAidlResponseHandler.onError(i, i2);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public void onRemoved(long j, int i, int i2, int i3) {
        this.mAidlResponseHandler.onEnrollmentRemoved(i, i3);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public void onEnumerate(long j, int i, int i2, int i3) {
        this.mAidlResponseHandler.onEnrollmentEnumerated(i, i3);
    }

    void onChallengeGenerated(long j) {
        this.mAidlResponseHandler.onChallengeGenerated(j);
    }

    void onChallengeRevoked(long j) {
        this.mAidlResponseHandler.onChallengeRevoked(j);
    }

    void onResetLockout() {
        this.mAidlResponseHandler.onLockoutCleared();
    }

    <T extends com.android.server.biometrics.sensors.BaseClientMonitor> void unsupportedClientScheduled(java.lang.Class<T> cls) {
        this.mAidlResponseHandler.onUnsupportedClientScheduled(cls);
    }
}
