package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class AidlSession {

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler mAidlResponseHandler;
    private final int mHalInterfaceVersion;

    @android.annotation.NonNull
    private final android.hardware.biometrics.face.ISession mSession;
    private final int mUserId;

    public AidlSession(int i, @android.annotation.NonNull android.hardware.biometrics.face.ISession iSession, int i2, com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler aidlResponseHandler) {
        this.mHalInterfaceVersion = i;
        this.mSession = iSession;
        this.mUserId = i2;
        this.mAidlResponseHandler = aidlResponseHandler;
    }

    public AidlSession(android.content.Context context, java.util.function.Supplier<android.hardware.biometrics.face.V1_0.IBiometricsFace> supplier, int i, com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler aidlResponseHandler) {
        this.mSession = new com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter(context, supplier, i, aidlResponseHandler);
        this.mHalInterfaceVersion = 0;
        this.mUserId = i;
        this.mAidlResponseHandler = aidlResponseHandler;
    }

    @android.annotation.NonNull
    public android.hardware.biometrics.face.ISession getSession() {
        return this.mSession;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler getHalSessionCallback() {
        return this.mAidlResponseHandler;
    }

    public boolean hasContextMethods() {
        return this.mHalInterfaceVersion >= 2;
    }

    public boolean supportsFaceEnrollOptions() {
        return this.mHalInterfaceVersion >= 4;
    }
}
