package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
public class AidlSession {

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler mAidlResponseHandler;
    private final int mHalInterfaceVersion;

    @android.annotation.NonNull
    private final android.hardware.biometrics.fingerprint.ISession mSession;
    private final int mUserId;

    public AidlSession(int i, @android.annotation.NonNull android.hardware.biometrics.fingerprint.ISession iSession, int i2, com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler aidlResponseHandler) {
        this.mHalInterfaceVersion = i;
        this.mSession = iSession;
        this.mUserId = i2;
        this.mAidlResponseHandler = aidlResponseHandler;
    }

    public AidlSession(@android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> supplier, int i, com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler aidlResponseHandler) {
        this.mSession = new com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSessionAdapter(supplier, i, aidlResponseHandler);
        this.mHalInterfaceVersion = 0;
        this.mUserId = i;
        this.mAidlResponseHandler = aidlResponseHandler;
    }

    @android.annotation.NonNull
    public android.hardware.biometrics.fingerprint.ISession getSession() {
        return this.mSession;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler getHalSessionCallback() {
        return this.mAidlResponseHandler;
    }

    public boolean hasContextMethods() {
        return this.mHalInterfaceVersion >= 2;
    }
}
