package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
public class FingerprintEnrollClient extends com.android.server.biometrics.sensors.EnrollClient<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> implements com.android.server.biometrics.sensors.fingerprint.Udfps {
    private static final java.lang.String TAG = "FingerprintEnrollClient";

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthenticationStateListeners mAuthenticationStateListeners;
    private final int mEnrollReason;
    private boolean mIsPointerDown;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.SensorOverlays mSensorOverlays;

    FingerprintEnrollClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> supplier, @android.annotation.NonNull android.os.IBinder iBinder, long j, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils<android.hardware.fingerprint.Fingerprint> biometricUtils, int i2, int i3, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.Nullable android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController, @android.annotation.Nullable android.hardware.fingerprint.ISidefpsController iSidefpsController, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, int i4, @android.annotation.NonNull android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, bArr, str, biometricUtils, i2, i3, true, biometricLogger, biometricContext, android.hardware.biometrics.BiometricFingerprintConstants.reasonToMetric(fingerprintEnrollOptions.getEnrollReason()));
        setRequestId(j);
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        this.mSensorOverlays = new com.android.server.biometrics.sensors.SensorOverlays(iUdfpsOverlayController, iSidefpsController);
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mEnrollReason = i4;
        if (i4 == 1) {
            getLogger().disableMetrics();
        }
        android.util.Slog.w(TAG, "EnrollOptions " + android.hardware.fingerprint.FingerprintEnrollOptions.enrollReasonToString(fingerprintEnrollOptions.getEnrollReason()));
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient, com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        com.android.server.biometrics.sensors.BiometricNotificationUtils.cancelFingerprintEnrollNotification(getContext());
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    @android.annotation.NonNull
    protected com.android.server.biometrics.sensors.ClientMonitorCallback wrapCallbackForStart(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        return new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(getLogger().getAmbientLightProbe(true), clientMonitorCallback);
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient
    protected boolean hasReachedEnrollmentLimit() {
        if (this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId()).size() >= getContext().getResources().getInteger(android.R.integer.config_externalDisplayPeakWidth)) {
            android.util.Slog.w(TAG, "Too many fingerprints registered, user: " + getTargetUserId());
            return true;
        }
        return false;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        this.mSensorOverlays.show(getSensorId(), getRequestReasonFromEnrollReason(this.mEnrollReason), this);
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        com.android.server.biometrics.sensors.BiometricNotificationUtils.cancelBadCalibrationNotification(getContext());
        try {
            getFreshDaemon().enroll(this.mHardwareAuthToken, getTargetUserId(), this.mTimeoutSec);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting enroll", e);
            onError(1, 0);
            this.mSensorOverlays.hide(getSensorId());
            com.android.systemui.shared.Flags.sidefpsControllerRefactor();
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    protected void stopHalOperation() {
        this.mSensorOverlays.hide(getSensorId());
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        try {
            getFreshDaemon().cancel();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting cancel", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient
    public void onEnrollResult(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, final int i) {
        super.onEnrollResult(identifier, i);
        this.mSensorOverlays.ifUdfps(new com.android.server.biometrics.sensors.SensorOverlays.OverlayControllerConsumer() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintEnrollClient$$ExternalSyntheticLambda0
            @Override // com.android.server.biometrics.sensors.SensorOverlays.OverlayControllerConsumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintEnrollClient.this.lambda$onEnrollResult$0(i, (android.hardware.fingerprint.IUdfpsOverlayController) obj);
            }
        });
        if (i == 0) {
            this.mSensorOverlays.hide(getSensorId());
            com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEnrollResult$0(int i, android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController) throws android.os.RemoteException {
        iUdfpsOverlayController.onEnrollmentProgress(getSensorId(), i);
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public void onAcquired(final int i, final int i2) {
        super.onAcquired(i, i2);
        this.mSensorOverlays.ifUdfps(new com.android.server.biometrics.sensors.SensorOverlays.OverlayControllerConsumer() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintEnrollClient$$ExternalSyntheticLambda1
            @Override // com.android.server.biometrics.sensors.SensorOverlays.OverlayControllerConsumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintEnrollClient.this.lambda$onAcquired$1(i, i2, (android.hardware.fingerprint.IUdfpsOverlayController) obj);
            }
        });
        this.mCallback.onBiometricAction(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAcquired$1(int i, int i2, android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController) throws android.os.RemoteException {
        if (com.android.server.biometrics.sensors.fingerprint.UdfpsHelper.isValidAcquisitionMessage(getContext(), i, i2)) {
            iUdfpsOverlayController.onEnrollmentHelp(getSensorId());
        }
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient, com.android.server.biometrics.sensors.AcquisitionClient, com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        super.onError(i, i2);
        this.mSensorOverlays.hide(getSensorId());
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
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
        try {
            switch (i) {
                case 1:
                    getListener().onUdfpsOverlayShown();
                    break;
                case 2:
                    return;
                default:
                    android.util.Slog.w(TAG, "No matching event for onUdfpsUiEvent");
                    break;
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to send onUdfpsUiEvent", e);
        }
    }
}
