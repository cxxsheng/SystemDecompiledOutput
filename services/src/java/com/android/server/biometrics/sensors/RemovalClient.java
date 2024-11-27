package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public abstract class RemovalClient<S extends android.hardware.biometrics.BiometricAuthenticator.Identifier, T> extends com.android.server.biometrics.sensors.HalClientMonitor<T> implements com.android.server.biometrics.sensors.RemovalConsumer, com.android.server.biometrics.sensors.EnrollmentModifier {
    private static final java.lang.String TAG = "Biometrics/RemovalClient";
    private final java.util.Map<java.lang.Integer, java.lang.Long> mAuthenticatorIds;
    private final com.android.server.biometrics.sensors.BiometricUtils<S> mBiometricUtils;
    private final boolean mHasEnrollmentsBeforeStarting;

    public RemovalClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<T> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils<S> biometricUtils, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull java.util.Map<java.lang.Integer, java.lang.Long> map) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, str, 0, i2, biometricLogger, biometricContext);
        this.mBiometricUtils = biometricUtils;
        this.mAuthenticatorIds = map;
        this.mHasEnrollmentsBeforeStarting = !biometricUtils.getBiometricsForUser(context, i).isEmpty();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.RemovalConsumer
    public void onRemoved(@android.annotation.NonNull android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, int i) {
        if (identifier == null) {
            android.util.Slog.e(TAG, "identifier was null, skipping onRemove()");
            try {
                getListener().onError(getSensorId(), getCookie(), 6, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to send error to client for onRemoved", e);
            }
            this.mCallback.onClientFinished(this, false);
            return;
        }
        android.util.Slog.d(TAG, "onRemoved: " + identifier.getBiometricId() + " remaining: " + i);
        this.mBiometricUtils.removeBiometricForUser(getContext(), getTargetUserId(), identifier.getBiometricId());
        try {
            getListener().onRemoved(identifier, i);
        } catch (android.os.RemoteException e2) {
            android.util.Slog.w(TAG, "Failed to notify Removed:", e2);
        }
        if (i == 0 && this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId()).isEmpty()) {
            android.util.Slog.d(TAG, "Last biometric removed for user: " + getTargetUserId());
            this.mAuthenticatorIds.put(java.lang.Integer.valueOf(getTargetUserId()), 0L);
        }
        this.mCallback.onClientFinished(this, true);
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public boolean hasEnrollmentStateChanged() {
        return (this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId()).isEmpty() ^ true) != this.mHasEnrollmentsBeforeStarting;
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public boolean hasEnrollments() {
        return !this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId()).isEmpty();
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 4;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public boolean interruptsPrecedingClients() {
        return true;
    }
}
