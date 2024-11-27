package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public class InvalidationRequesterClient<S extends android.hardware.biometrics.BiometricAuthenticator.Identifier> extends com.android.server.biometrics.sensors.BaseClientMonitor {
    private final android.hardware.biometrics.BiometricManager mBiometricManager;

    @android.annotation.NonNull
    private final android.hardware.biometrics.IInvalidationCallback mInvalidationCallback;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.BiometricUtils<S> mUtils;

    public InvalidationRequesterClient(@android.annotation.NonNull android.content.Context context, int i, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils<S> biometricUtils) {
        super(context, null, null, i, context.getOpPackageName(), 0, i2, biometricLogger, biometricContext);
        this.mInvalidationCallback = new android.hardware.biometrics.IInvalidationCallback.Stub() { // from class: com.android.server.biometrics.sensors.InvalidationRequesterClient.1
            public void onCompleted() {
                com.android.server.biometrics.sensors.InvalidationRequesterClient.this.mUtils.setInvalidationInProgress(com.android.server.biometrics.sensors.InvalidationRequesterClient.this.getContext(), com.android.server.biometrics.sensors.InvalidationRequesterClient.this.getTargetUserId(), false);
                com.android.server.biometrics.sensors.InvalidationRequesterClient.this.mCallback.onClientFinished(com.android.server.biometrics.sensors.InvalidationRequesterClient.this, true);
            }
        };
        this.mBiometricManager = (android.hardware.biometrics.BiometricManager) context.getSystemService(android.hardware.biometrics.BiometricManager.class);
        this.mUtils = biometricUtils;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        this.mUtils.setInvalidationInProgress(getContext(), getTargetUserId(), true);
        this.mBiometricManager.invalidateAuthenticatorIds(getTargetUserId(), getSensorId(), this.mInvalidationCallback);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 14;
    }
}
