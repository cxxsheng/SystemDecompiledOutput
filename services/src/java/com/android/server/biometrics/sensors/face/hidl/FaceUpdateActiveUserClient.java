package com.android.server.biometrics.sensors.face.hidl;

/* loaded from: classes.dex */
public class FaceUpdateActiveUserClient extends com.android.server.biometrics.sensors.StartUserClient<android.hardware.biometrics.face.V1_0.IBiometricsFace, com.android.server.biometrics.sensors.face.aidl.AidlSession> {
    private static final java.lang.String FACE_DATA_DIR = "facedata";
    private static final java.lang.String TAG = "FaceUpdateActiveUserClient";

    @android.annotation.NonNull
    private final java.util.Map<java.lang.Integer, java.lang.Long> mAuthenticatorIds;
    private final boolean mHasEnrolledBiometrics;

    FaceUpdateActiveUserClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.face.V1_0.IBiometricsFace> supplier, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z, @android.annotation.NonNull java.util.Map<java.lang.Integer, java.lang.Long> map) {
        this(context, supplier, new com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.FaceUpdateActiveUserClient$$ExternalSyntheticLambda0
            @Override // com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback
            public final void onUserStarted(int i3, java.lang.Object obj, int i4) {
                com.android.server.biometrics.sensors.face.hidl.FaceUpdateActiveUserClient.lambda$new$0(i3, obj, i4);
            }
        }, i, str, i2, biometricLogger, biometricContext, z, map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$0(int i, java.lang.Object obj, int i2) {
    }

    FaceUpdateActiveUserClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.face.V1_0.IBiometricsFace> supplier, com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback userStartedCallback, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z, @android.annotation.NonNull java.util.Map<java.lang.Integer, java.lang.Long> map) {
        super(context, supplier, null, i, i2, biometricLogger, biometricContext, userStartedCallback);
        this.mHasEnrolledBiometrics = z;
        this.mAuthenticatorIds = map;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        java.io.File file = new java.io.File(android.os.Environment.getDataVendorDeDirectory(getTargetUserId()), FACE_DATA_DIR);
        if (!file.exists()) {
            android.util.Slog.e(TAG, "vold has not created the directory?");
            this.mCallback.onClientFinished(this, false);
            return;
        }
        try {
            android.hardware.biometrics.face.V1_0.IBiometricsFace freshDaemon = getFreshDaemon();
            freshDaemon.setActiveUser(getTargetUserId(), file.getAbsolutePath());
            this.mAuthenticatorIds.put(java.lang.Integer.valueOf(getTargetUserId()), java.lang.Long.valueOf(this.mHasEnrolledBiometrics ? freshDaemon.getAuthenticatorId().value : 0L));
            this.mUserStartedCallback.onUserStarted(getTargetUserId(), null, 0);
            this.mCallback.onClientFinished(this, true);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to setActiveUser: " + e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.StartUserClient, com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 1;
    }
}
