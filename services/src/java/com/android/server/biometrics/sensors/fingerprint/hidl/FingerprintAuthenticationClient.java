package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
class FingerprintAuthenticationClient extends com.android.server.biometrics.sensors.AuthenticationClient<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint, android.hardware.fingerprint.FingerprintAuthenticateOptions> implements com.android.server.biometrics.sensors.fingerprint.Udfps {
    private static final java.lang.String TAG = "Biometrics/FingerprintAuthClient";

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.CallbackWithProbe<com.android.server.biometrics.log.Probe> mALSProbeCallback;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthenticationStateListeners mAuthenticationStateListeners;
    private boolean mIsPointerDown;
    private final com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl mLockoutFrameworkImpl;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.SensorOverlays mSensorOverlays;

    @android.annotation.NonNull
    private final android.hardware.fingerprint.FingerprintSensorPropertiesInternal mSensorProps;

    FingerprintAuthenticationClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> supplier, @android.annotation.NonNull android.os.IBinder iBinder, long j, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j2, boolean z, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, int i, boolean z2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z3, @android.annotation.NonNull android.app.TaskStackListener taskStackListener, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl lockoutFrameworkImpl, @android.annotation.Nullable android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController, @android.annotation.Nullable android.hardware.fingerprint.ISidefpsController iSidefpsController, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, boolean z4, @android.annotation.NonNull android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, int i2) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, j2, z, fingerprintAuthenticateOptions, i, z2, biometricLogger, biometricContext, z3, taskStackListener, lockoutFrameworkImpl, z4, false, i2);
        setRequestId(j);
        this.mLockoutFrameworkImpl = lockoutFrameworkImpl;
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        this.mSensorOverlays = new com.android.server.biometrics.sensors.SensorOverlays(iUdfpsOverlayController, iSidefpsController);
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mSensorProps = fingerprintSensorPropertiesInternal;
        this.mALSProbeCallback = getLogger().getAmbientLightProbe(false);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        if (this.mSensorProps.isAnyUdfpsType()) {
            this.mState = 2;
        } else {
            this.mState = 1;
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    @android.annotation.NonNull
    protected com.android.server.biometrics.sensors.ClientMonitorCallback wrapCallbackForStart(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        return new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(this.mALSProbeCallback, clientMonitorCallback);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AuthenticationConsumer
    public void onAuthenticated(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, boolean z, java.util.ArrayList<java.lang.Byte> arrayList) {
        int i;
        super.onAuthenticated(identifier, z, arrayList);
        if (z) {
            this.mState = 4;
            resetFailedAttempts(getTargetUserId());
            this.mSensorOverlays.hide(getSensorId());
            com.android.systemui.shared.Flags.sidefpsControllerRefactor();
            if (android.adaptiveauth.Flags.reportBiometricAuthAttempts()) {
                this.mAuthenticationStateListeners.onAuthenticationSucceeded(getRequestReason(), getTargetUserId());
                return;
            }
            return;
        }
        this.mState = 3;
        int lockoutModeForUser = this.mLockoutFrameworkImpl.getLockoutModeForUser(getTargetUserId());
        if (lockoutModeForUser != 0) {
            android.util.Slog.w(TAG, "Fingerprint locked out, lockoutMode(" + lockoutModeForUser + ")");
            if (lockoutModeForUser == 1) {
                i = 7;
            } else {
                i = 9;
            }
            this.mSensorOverlays.hide(getSensorId());
            com.android.systemui.shared.Flags.sidefpsControllerRefactor();
            onErrorInternal(i, 0, false);
            cancel();
        }
        if (android.adaptiveauth.Flags.reportBiometricAuthAttempts()) {
            this.mAuthenticationStateListeners.onAuthenticationFailed(getRequestReason(), getTargetUserId());
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient, com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        super.onError(i, i2);
        if (i == 18) {
            com.android.server.biometrics.sensors.BiometricNotificationUtils.showBadCalibrationNotification(getContext());
        }
        this.mSensorOverlays.hide(getSensorId());
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
    }

    private void resetFailedAttempts(int i) {
        this.mLockoutFrameworkImpl.resetFailedAttemptsForUser(true, i);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    protected void handleLifecycleAfterAuth(boolean z) {
        if (z) {
            this.mCallback.onClientFinished(this, true);
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient
    public void onAcquired(int i, int i2) {
        this.mAuthenticationStateListeners.onAuthenticationAcquired(android.hardware.biometrics.BiometricSourceType.FINGERPRINT, getRequestReason(), i);
        super.onAcquired(i, i2);
        if (getLockoutTracker().getLockoutModeForUser(getTargetUserId()) == 0) {
            com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(getSensorId()).incrementAcquireForUser(getTargetUserId(), isCryptoOperation());
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public boolean wasUserDetected() {
        return false;
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public int handleFailedAttempt(int i) {
        this.mLockoutFrameworkImpl.addFailedAttemptForUser(i);
        int lockoutModeForUser = getLockoutTracker().getLockoutModeForUser(i);
        com.android.server.biometrics.sensors.PerformanceTracker instanceForSensorId = com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(getSensorId());
        if (lockoutModeForUser == 2) {
            instanceForSensorId.incrementPermanentLockoutForUser(i);
        } else if (lockoutModeForUser == 1) {
            instanceForSensorId.incrementTimedLockoutForUser(i);
        }
        return lockoutModeForUser;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        this.mSensorOverlays.show(getSensorId(), getRequestReason(), this);
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        try {
            getFreshDaemon().authenticate(this.mOperationId, getTargetUserId());
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting auth", e);
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

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public void onPointerDown(android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        this.mIsPointerDown = true;
        this.mState = 1;
        this.mALSProbeCallback.getProbe().enable();
        com.android.server.biometrics.sensors.fingerprint.UdfpsHelper.onFingerDown(getFreshDaemon(), (int) pointerContext.x, (int) pointerContext.y, pointerContext.minor, pointerContext.major);
        try {
            getListener().onUdfpsPointerDown(getSensorId());
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public void onPointerUp(android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        this.mIsPointerDown = false;
        this.mState = 3;
        this.mALSProbeCallback.getProbe().disable();
        com.android.server.biometrics.sensors.fingerprint.UdfpsHelper.onFingerUp(getFreshDaemon());
        try {
            getListener().onUdfpsPointerUp(getSensorId());
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public boolean isPointerDown() {
        return this.mIsPointerDown;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public void onUdfpsUiEvent(int i) {
    }
}
