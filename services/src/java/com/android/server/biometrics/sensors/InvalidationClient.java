package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public abstract class InvalidationClient<S extends android.hardware.biometrics.BiometricAuthenticator.Identifier, T> extends com.android.server.biometrics.sensors.HalClientMonitor<T> {
    private static final java.lang.String TAG = "InvalidationClient";

    @android.annotation.NonNull
    private final java.util.Map<java.lang.Integer, java.lang.Long> mAuthenticatorIds;

    @android.annotation.NonNull
    private final android.hardware.biometrics.IInvalidationCallback mInvalidationCallback;

    public InvalidationClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<T> supplier, int i, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull java.util.Map<java.lang.Integer, java.lang.Long> map, @android.annotation.NonNull android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
        super(context, supplier, null, null, i, context.getOpPackageName(), 0, i2, biometricLogger, biometricContext);
        this.mAuthenticatorIds = map;
        this.mInvalidationCallback = iInvalidationCallback;
    }

    public void onAuthenticatorIdInvalidated(long j) {
        this.mAuthenticatorIds.put(java.lang.Integer.valueOf(getTargetUserId()), java.lang.Long.valueOf(j));
        try {
            this.mInvalidationCallback.onCompleted();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
        }
        this.mCallback.onClientFinished(this, true);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 15;
    }
}
