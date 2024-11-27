package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
class FingerprintDetectClient extends com.android.server.biometrics.sensors.AcquisitionClient<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> implements com.android.server.biometrics.sensors.AuthenticationConsumer, com.android.server.biometrics.sensors.fingerprint.Udfps {
    private static final java.lang.String TAG = "FingerprintDetectClient";
    private boolean mIsPointerDown;
    private final boolean mIsStrongBiometric;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.SensorOverlays mSensorOverlays;

    public FingerprintDetectClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> supplier, @android.annotation.NonNull android.os.IBinder iBinder, long j, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.Nullable android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController, boolean z) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, fingerprintAuthenticateOptions.getUserId(), fingerprintAuthenticateOptions.getOpPackageName(), 0, fingerprintAuthenticateOptions.getSensorId(), true, biometricLogger, biometricContext);
        setRequestId(j);
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        this.mSensorOverlays = new com.android.server.biometrics.sensors.SensorOverlays(iUdfpsOverlayController, null);
        this.mIsStrongBiometric = z;
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    protected void stopHalOperation() {
        this.mSensorOverlays.hide(getSensorId());
        try {
            getFreshDaemon().cancel();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting cancel", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        this.mSensorOverlays.show(getSensorId(), 4, this);
        try {
            getFreshDaemon().authenticate(0L, getTargetUserId());
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting auth", e);
            onError(1, 0);
            this.mSensorOverlays.hide(getSensorId());
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public void onPointerDown(android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        this.mIsPointerDown = true;
        com.android.server.biometrics.sensors.fingerprint.UdfpsHelper.onFingerDown(getFreshDaemon(), (int) pointerContext.x, (int) pointerContext.y, pointerContext.minor, pointerContext.major);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public void onPointerUp(android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        this.mIsPointerDown = false;
        com.android.server.biometrics.sensors.fingerprint.UdfpsHelper.onFingerUp(getFreshDaemon());
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public boolean isPointerDown() {
        return this.mIsPointerDown;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public void onUdfpsUiEvent(int i) {
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationConsumer
    public void onAuthenticated(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, boolean z, java.util.ArrayList<java.lang.Byte> arrayList) {
        getLogger().logOnAuthenticated(getContext(), getOperationContext(), z, false, getTargetUserId(), false);
        vibrateSuccess();
        com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(getSensorId()).incrementAuthForUser(getTargetUserId(), z);
        try {
            getListener().onDetected(getSensorId(), getTargetUserId(), this.mIsStrongBiometric);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when sending onDetected", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 13;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public boolean interruptsPrecedingClients() {
        return true;
    }
}
