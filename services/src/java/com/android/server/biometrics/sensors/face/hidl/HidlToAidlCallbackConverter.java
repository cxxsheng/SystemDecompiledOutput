package com.android.server.biometrics.sensors.face.hidl;

/* loaded from: classes.dex */
public class HidlToAidlCallbackConverter extends android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback.Stub {
    private final com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler mAidlResponseHandler;

    public HidlToAidlCallbackConverter(com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler aidlResponseHandler) {
        this.mAidlResponseHandler = aidlResponseHandler;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onEnrollResult(long j, int i, int i2, int i3) {
        this.mAidlResponseHandler.onEnrollmentProgress(i, i3);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onAuthenticated(long j, int i, int i2, java.util.ArrayList<java.lang.Byte> arrayList) {
        boolean z = i != 0;
        byte[] bArr = new byte[arrayList.size()];
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            bArr[i3] = arrayList.get(i3).byteValue();
        }
        if (z) {
            this.mAidlResponseHandler.onAuthenticationSucceeded(i, com.android.server.biometrics.HardwareAuthTokenUtils.toHardwareAuthToken(bArr));
        } else {
            this.mAidlResponseHandler.onAuthenticationFailed();
        }
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onAcquired(long j, int i, int i2, int i3) {
        this.mAidlResponseHandler.onAcquired(i2, i3);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onError(long j, int i, int i2, int i3) {
        this.mAidlResponseHandler.onError(i2, i3);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onRemoved(long j, java.util.ArrayList<java.lang.Integer> arrayList, int i) {
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = arrayList.get(i2).intValue();
        }
        this.mAidlResponseHandler.onEnrollmentsRemoved(iArr);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onEnumerate(long j, java.util.ArrayList<java.lang.Integer> arrayList, int i) {
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = arrayList.get(i2).intValue();
        }
        this.mAidlResponseHandler.onEnrollmentsEnumerated(iArr);
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
    public void onLockoutChanged(long j) {
        this.mAidlResponseHandler.onLockoutChanged(j);
    }

    void onChallengeGenerated(long j) {
        this.mAidlResponseHandler.onChallengeGenerated(j);
    }

    void onChallengeRevoked(long j) {
        this.mAidlResponseHandler.onChallengeRevoked(j);
    }

    void onFeatureGet(byte[] bArr) {
        this.mAidlResponseHandler.onFeaturesRetrieved(bArr);
    }

    void onFeatureSet(byte b) {
        this.mAidlResponseHandler.onFeatureSet(b);
    }

    void onAuthenticatorIdRetrieved(long j) {
        this.mAidlResponseHandler.onAuthenticatorIdRetrieved(j);
    }

    void onUnsupportedClientScheduled() {
        this.mAidlResponseHandler.onUnsupportedClientScheduled();
    }
}
