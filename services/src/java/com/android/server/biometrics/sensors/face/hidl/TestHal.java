package com.android.server.biometrics.sensors.face.hidl;

/* loaded from: classes.dex */
public class TestHal extends android.hardware.biometrics.face.V1_0.IBiometricsFace.Stub {
    private static final java.lang.String TAG = "face.hidl.TestHal";

    @android.annotation.Nullable
    private android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback mCallback;

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private final int mSensorId;
    private int mUserId;

    TestHal(@android.annotation.NonNull android.content.Context context, int i) {
        this.mContext = context;
        this.mSensorId = i;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public android.hardware.biometrics.face.V1_0.OptionalUint64 setCallback(android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback iBiometricsFaceClientCallback) {
        this.mCallback = iBiometricsFaceClientCallback;
        new android.hardware.biometrics.face.V1_0.OptionalUint64().status = 0;
        return new android.hardware.biometrics.face.V1_0.OptionalUint64();
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int setActiveUser(int i, java.lang.String str) {
        this.mUserId = i;
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public android.hardware.biometrics.face.V1_0.OptionalUint64 generateChallenge(int i) {
        android.util.Slog.w(TAG, "generateChallenge");
        android.hardware.biometrics.face.V1_0.OptionalUint64 optionalUint64 = new android.hardware.biometrics.face.V1_0.OptionalUint64();
        optionalUint64.status = 0;
        optionalUint64.value = 0L;
        return optionalUint64;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int enroll(java.util.ArrayList<java.lang.Byte> arrayList, int i, java.util.ArrayList<java.lang.Integer> arrayList2) {
        android.util.Slog.w(TAG, "enroll");
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int revokeChallenge() {
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int setFeature(int i, boolean z, java.util.ArrayList<java.lang.Byte> arrayList, int i2) {
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public android.hardware.biometrics.face.V1_0.OptionalBool getFeature(int i, int i2) {
        android.hardware.biometrics.face.V1_0.OptionalBool optionalBool = new android.hardware.biometrics.face.V1_0.OptionalBool();
        optionalBool.status = 0;
        optionalBool.value = true;
        return optionalBool;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public android.hardware.biometrics.face.V1_0.OptionalUint64 getAuthenticatorId() {
        android.hardware.biometrics.face.V1_0.OptionalUint64 optionalUint64 = new android.hardware.biometrics.face.V1_0.OptionalUint64();
        optionalUint64.status = 0;
        optionalUint64.value = 0L;
        return optionalUint64;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int cancel() throws android.os.RemoteException {
        if (this.mCallback != null) {
            this.mCallback.onError(0L, 0, 5, 0);
            return 0;
        }
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int enumerate() throws android.os.RemoteException {
        android.util.Slog.w(TAG, "enumerate");
        if (this.mCallback != null) {
            this.mCallback.onEnumerate(0L, new java.util.ArrayList<>(), 0);
        }
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int remove(int i) throws android.os.RemoteException {
        android.util.Slog.w(TAG, "remove");
        if (this.mCallback == null) {
            return 0;
        }
        if (i == 0) {
            java.util.List<android.hardware.face.Face> biometricsForUser = com.android.server.biometrics.sensors.face.FaceUtils.getInstance(this.mSensorId).getBiometricsForUser(this.mContext, this.mUserId);
            java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList<>();
            java.util.Iterator<android.hardware.face.Face> it = biometricsForUser.iterator();
            while (it.hasNext()) {
                arrayList.add(java.lang.Integer.valueOf(it.next().getBiometricId()));
            }
            this.mCallback.onRemoved(0L, arrayList, this.mUserId);
            return 0;
        }
        this.mCallback.onRemoved(0L, new java.util.ArrayList<>(java.util.Collections.singletonList(java.lang.Integer.valueOf(i))), this.mUserId);
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int authenticate(long j) {
        android.util.Slog.w(TAG, "authenticate");
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int userActivity() {
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public int resetLockout(java.util.ArrayList<java.lang.Byte> arrayList) {
        android.util.Slog.w(TAG, "resetLockout");
        return 0;
    }
}
