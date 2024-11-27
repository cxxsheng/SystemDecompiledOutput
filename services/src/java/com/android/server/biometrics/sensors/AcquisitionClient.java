package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public abstract class AcquisitionClient<T> extends com.android.server.biometrics.sensors.HalClientMonitor<T> implements com.android.server.biometrics.sensors.ErrorConsumer {
    private static final java.lang.String TAG = "Biometrics/AcquisitionClient";
    private boolean mAlreadyCancelled;
    private final android.os.PowerManager mPowerManager;
    private boolean mShouldSendErrorToClient;
    protected final boolean mShouldVibrate;
    private static final android.os.VibrationAttributes HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES = android.os.VibrationAttributes.createForUsage(50);
    private static final android.os.VibrationEffect SUCCESS_VIBRATION_EFFECT = android.os.VibrationEffect.get(0);
    private static final android.os.VibrationEffect ERROR_VIBRATION_EFFECT = android.os.VibrationEffect.get(1);

    protected abstract void stopHalOperation();

    public AcquisitionClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<T> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull java.lang.String str, int i2, int i3, boolean z, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, str, i2, i3, biometricLogger, biometricContext);
        this.mShouldSendErrorToClient = true;
        this.mPowerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
        this.mShouldVibrate = z;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
        try {
            getListener().onError(getSensorId(), getCookie(), 1, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to send error", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        onErrorInternal(i, i2, true);
    }

    public void onUserCanceled() {
        android.util.Slog.d(TAG, "onUserCanceled");
        onErrorInternal(10, 0, false);
        stopHalOperation();
    }

    protected void onErrorInternal(int i, int i2, boolean z) {
        android.util.Slog.d(TAG, "onErrorInternal code: " + i + ", finish: " + z);
        if (this.mShouldSendErrorToClient) {
            getLogger().logOnError(getContext(), getOperationContext(), i, i2, getTargetUserId());
            try {
                this.mShouldSendErrorToClient = false;
                getListener().onError(getSensorId(), getCookie(), i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to invoke sendError", e);
            }
        }
        if (z) {
            if (this.mCallback == null) {
                android.util.Slog.e(TAG, "Callback is null, perhaps the client hasn't been started yet?");
            } else {
                this.mCallback.onClientFinished(this, false);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void cancel() {
        if (this.mAlreadyCancelled) {
            android.util.Slog.w(TAG, "Cancel was already requested");
        } else {
            stopHalOperation();
            this.mAlreadyCancelled = true;
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void cancelWithoutStarting(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        android.util.Slog.d(TAG, "cancelWithoutStarting: " + this);
        try {
            getListener().onError(getSensorId(), getCookie(), 5, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to invoke sendError", e);
        }
        clientMonitorCallback.onClientFinished(this, true);
    }

    public void onAcquired(int i, int i2) {
        onAcquiredInternal(i, i2, true);
    }

    protected final void onAcquiredInternal(int i, int i2, boolean z) {
        getLogger().logOnAcquired(getContext(), getOperationContext(), i, i2, getTargetUserId());
        android.util.Slog.v(TAG, "Acquired: " + i + " " + i2 + ", shouldSend: " + z);
        if (i == 0) {
            notifyUserActivity();
        }
        if (z) {
            try {
                getListener().onAcquired(getSensorId(), i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to invoke sendAcquired", e);
                this.mCallback.onClientFinished(this, false);
            }
        }
    }

    final void notifyUserActivity() {
        this.mPowerManager.userActivity(android.os.SystemClock.uptimeMillis(), 2, 0);
    }

    protected final void vibrateSuccess() {
        android.os.Vibrator vibrator = (android.os.Vibrator) getContext().getSystemService(android.os.Vibrator.class);
        if (vibrator != null && this.mShouldVibrate) {
            vibrator.vibrate(android.os.Process.myUid(), getContext().getOpPackageName(), SUCCESS_VIBRATION_EFFECT, getClass().getSimpleName() + "::success", HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES);
        }
    }

    protected final void resetIgnoreDisplayTouches() {
        try {
            ((com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession) getFreshDaemon()).getSession().setIgnoreDisplayTouches(false);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when resetting setIgnoreDisplayTouches");
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public boolean isInterruptable() {
        return true;
    }

    public boolean isAlreadyCancelled() {
        return this.mAlreadyCancelled;
    }
}
