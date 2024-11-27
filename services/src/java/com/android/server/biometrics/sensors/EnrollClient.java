package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public abstract class EnrollClient<T> extends com.android.server.biometrics.sensors.AcquisitionClient<T> implements com.android.server.biometrics.sensors.EnrollmentModifier {
    private static final java.lang.String TAG = "Biometrics/EnrollClient";
    protected final com.android.server.biometrics.sensors.BiometricUtils mBiometricUtils;
    private final int mEnrollReason;
    private long mEnrollmentStartTimeMs;
    protected final byte[] mHardwareAuthToken;
    private final boolean mHasEnrollmentsBeforeStarting;
    protected final int mTimeoutSec;

    protected abstract boolean hasReachedEnrollmentLimit();

    public EnrollClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<T> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils biometricUtils, int i2, int i3, boolean z, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, int i4) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, str, 0, i3, z, biometricLogger, biometricContext);
        this.mBiometricUtils = biometricUtils;
        this.mHardwareAuthToken = java.util.Arrays.copyOf(bArr, bArr.length);
        this.mTimeoutSec = i2;
        this.mHasEnrollmentsBeforeStarting = hasEnrollments();
        this.mEnrollReason = i4;
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public boolean hasEnrollmentStateChanged() {
        return hasEnrollments() != this.mHasEnrollmentsBeforeStarting;
    }

    @Override // com.android.server.biometrics.sensors.EnrollmentModifier
    public boolean hasEnrollments() {
        return !this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId()).isEmpty();
    }

    public void onEnrollResult(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, int i) {
        if (this.mShouldVibrate) {
            vibrateSuccess();
        }
        try {
            getListener().onEnrollResult(identifier, i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
        }
        if (i == 0) {
            this.mBiometricUtils.addBiometricForUser(getContext(), getTargetUserId(), identifier);
            getLogger().logOnEnrolled(getTargetUserId(), java.lang.System.currentTimeMillis() - this.mEnrollmentStartTimeMs, true, this.mEnrollReason);
            this.mCallback.onClientFinished(this, true);
        }
        notifyUserActivity();
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        if (hasReachedEnrollmentLimit()) {
            android.util.Slog.e(TAG, "Reached enrollment limit");
            clientMonitorCallback.onClientFinished(this, false);
        } else {
            this.mEnrollmentStartTimeMs = java.lang.System.currentTimeMillis();
            startHalOperation();
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient, com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        getLogger().logOnEnrolled(getTargetUserId(), java.lang.System.currentTimeMillis() - this.mEnrollmentStartTimeMs, false, this.mEnrollReason);
        super.onError(i, i2);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 2;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public boolean interruptsPrecedingClients() {
        return true;
    }

    protected int getRequestReasonFromEnrollReason(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }
}
