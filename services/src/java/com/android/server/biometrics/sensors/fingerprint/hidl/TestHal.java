package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
public class TestHal extends android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint.Stub {
    private static final java.lang.String TAG = "fingerprint.hidl.TestHal";

    @android.annotation.Nullable
    private android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback mCallback;

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private final int mSensorId;

    TestHal(@android.annotation.NonNull android.content.Context context, int i) {
        this.mContext = context;
        this.mSensorId = i;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint
    public boolean isUdfps(int i) {
        return false;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint
    public void onFingerDown(int i, int i2, float f, float f2) {
    }

    @Override // android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint
    public void onFingerUp() {
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public long setNotify(android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback iBiometricsFingerprintClientCallback) {
        this.mCallback = iBiometricsFingerprintClientCallback;
        return 0L;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public long preEnroll() {
        return 0L;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int enroll(byte[] bArr, int i, int i2) {
        android.util.Slog.w(TAG, "enroll");
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int postEnroll() {
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public long getAuthenticatorId() {
        return 0L;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int cancel() throws android.os.RemoteException {
        if (this.mCallback != null) {
            this.mCallback.onError(0L, 5, 0);
        }
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int enumerate() throws android.os.RemoteException {
        android.util.Slog.w(TAG, "Enumerate");
        if (this.mCallback != null) {
            this.mCallback.onEnumerate(0L, 0, 0, 0);
            return 0;
        }
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int remove(int i, int i2) throws android.os.RemoteException {
        android.util.Slog.w(TAG, "Remove");
        if (this.mCallback != null) {
            if (i2 == 0) {
                java.util.List<android.hardware.fingerprint.Fingerprint> biometricsForUser = com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(this.mSensorId).getBiometricsForUser(this.mContext, i);
                for (int i3 = 0; i3 < biometricsForUser.size(); i3++) {
                    this.mCallback.onRemoved(0L, biometricsForUser.get(i3).getBiometricId(), i, (biometricsForUser.size() - i3) - 1);
                }
            } else {
                this.mCallback.onRemoved(0L, i2, i, 0);
            }
        }
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int setActiveGroup(int i, java.lang.String str) {
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public int authenticate(long j, int i) {
        android.util.Slog.w(TAG, "Authenticate");
        return 0;
    }
}
