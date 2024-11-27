package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
public class FingerprintAuthenticationClient extends com.android.server.biometrics.sensors.AuthenticationClient<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession, android.hardware.fingerprint.FingerprintAuthenticateOptions> implements com.android.server.biometrics.sensors.fingerprint.Udfps, com.android.server.biometrics.sensors.LockoutConsumer, com.android.server.biometrics.sensors.fingerprint.PowerPressHandler {
    private static final int MESSAGE_AUTH_SUCCESS = 2;
    private static final int MESSAGE_FINGER_UP = 3;
    private static final java.lang.String TAG = "FingerprintAuthenticationClient";

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.CallbackWithProbe<com.android.server.biometrics.log.Probe> mALSProbeCallback;
    private final com.android.server.biometrics.sensors.AuthSessionCoordinator mAuthSessionCoordinator;
    private java.lang.Runnable mAuthSuccessRunnable;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthenticationStateListeners mAuthenticationStateListeners;

    @android.annotation.Nullable
    private android.hardware.biometrics.common.ICancellationSignal mCancellationSignal;
    private final java.time.Clock mClock;
    private final long mFingerUpIgnoresPower;
    private final android.os.Handler mHandler;
    private long mIgnoreAuthFor;
    private boolean mIsPointerDown;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.SensorOverlays mSensorOverlays;

    @android.annotation.NonNull
    private final android.hardware.fingerprint.FingerprintSensorPropertiesInternal mSensorProps;
    private long mSideFpsLastAcquireStartTime;
    private final int mSkipWaitForPowerAcquireMessage;
    private final int mSkipWaitForPowerVendorAcquireMessage;
    private long mWaitForAuthBp;
    private long mWaitForAuthKeyguard;

    public FingerprintAuthenticationClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> supplier, @android.annotation.NonNull android.os.IBinder iBinder, long j, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j2, boolean z, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, int i, boolean z2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z3, @android.annotation.Nullable android.app.TaskStackListener taskStackListener, @android.annotation.Nullable android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController, @android.annotation.Nullable android.hardware.fingerprint.ISidefpsController iSidefpsController, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, boolean z4, @android.annotation.NonNull android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, @android.annotation.NonNull android.os.Handler handler, int i2, @android.annotation.NonNull java.time.Clock clock, @android.annotation.Nullable com.android.server.biometrics.sensors.LockoutTracker lockoutTracker) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, j2, z, fingerprintAuthenticateOptions, i, z2, biometricLogger, biometricContext, z3, taskStackListener, lockoutTracker, z4, false, i2);
        this.mFingerUpIgnoresPower = 500L;
        setRequestId(j);
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        this.mSensorOverlays = new com.android.server.biometrics.sensors.SensorOverlays(iUdfpsOverlayController, iSidefpsController);
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mSensorProps = fingerprintSensorPropertiesInternal;
        this.mALSProbeCallback = getLogger().getAmbientLightProbe(false);
        this.mHandler = handler;
        this.mWaitForAuthKeyguard = context.getResources().getInteger(android.R.integer.config_selected_udfps_touch_detection);
        this.mWaitForAuthBp = context.getResources().getInteger(android.R.integer.config_searchKeyBehavior);
        this.mIgnoreAuthFor = context.getResources().getInteger(android.R.integer.config_shortPressOnPowerBehavior);
        this.mSkipWaitForPowerAcquireMessage = context.getResources().getInteger(android.R.integer.config_shortPressOnSettingsBehavior);
        this.mSkipWaitForPowerVendorAcquireMessage = context.getResources().getInteger(android.R.integer.config_shortPressOnSleepBehavior);
        this.mAuthSessionCoordinator = biometricContext.getAuthSessionCoordinator();
        this.mSideFpsLastAcquireStartTime = -1L;
        this.mClock = clock;
        if (this.mSensorProps.isAnySidefpsType() && android.os.Build.isDebuggable()) {
            this.mWaitForAuthKeyguard = android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "fingerprint_side_fps_kg_power_window", (int) this.mWaitForAuthKeyguard, -2);
            this.mWaitForAuthBp = android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "fingerprint_side_fps_bp_power_window", (int) this.mWaitForAuthBp, -2);
            this.mIgnoreAuthFor = android.provider.Settings.Secure.getIntForUser(context.getContentResolver(), "fingerprint_side_fps_auth_downtime", (int) this.mIgnoreAuthFor, -2);
        }
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
        return new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(this.mALSProbeCallback, getBiometricContextUnsubscriber(), clientMonitorCallback);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    protected void handleLifecycleAfterAuth(boolean z) {
        if (z) {
            this.mCallback.onClientFinished(this, true);
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public boolean wasUserDetected() {
        return false;
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AuthenticationConsumer
    public void onAuthenticated(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, boolean z, java.util.ArrayList<java.lang.Byte> arrayList) {
        super.onAuthenticated(identifier, z, arrayList);
        handleLockout(z);
        if (z) {
            this.mState = 4;
            resetIgnoreDisplayTouches();
            this.mSensorOverlays.hide(getSensorId());
            com.android.systemui.shared.Flags.sidefpsControllerRefactor();
            if (android.adaptiveauth.Flags.reportBiometricAuthAttempts()) {
                this.mAuthenticationStateListeners.onAuthenticationSucceeded(getRequestReason(), getTargetUserId());
                return;
            }
            return;
        }
        this.mState = 3;
        if (android.adaptiveauth.Flags.reportBiometricAuthAttempts()) {
            this.mAuthenticationStateListeners.onAuthenticationFailed(getRequestReason(), getTargetUserId());
        }
    }

    private void handleLockout(boolean z) {
        int i;
        if (getLockoutTracker() == null) {
            android.util.Slog.d(TAG, "Lockout is implemented by the HAL");
            return;
        }
        if (z) {
            getLockoutTracker().resetFailedAttemptsForUser(true, getTargetUserId());
            return;
        }
        int lockoutModeForUser = getLockoutTracker().getLockoutModeForUser(getTargetUserId());
        if (lockoutModeForUser != 0) {
            android.util.Slog.w(TAG, "Fingerprint locked out, lockoutMode(" + lockoutModeForUser + ")");
            if (lockoutModeForUser == 1) {
                i = 7;
            } else {
                i = 9;
            }
            resetIgnoreDisplayTouches();
            this.mSensorOverlays.hide(getSensorId());
            com.android.systemui.shared.Flags.sidefpsControllerRefactor();
            onErrorInternal(i, 0, false);
            cancel();
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient
    public void onAcquired(final int i, int i2) {
        this.mAuthenticationStateListeners.onAuthenticationAcquired(android.hardware.biometrics.BiometricSourceType.FINGERPRINT, getRequestReason(), i);
        this.mSensorOverlays.ifUdfps(new com.android.server.biometrics.sensors.SensorOverlays.OverlayControllerConsumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintAuthenticationClient$$ExternalSyntheticLambda0
            @Override // com.android.server.biometrics.sensors.SensorOverlays.OverlayControllerConsumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintAuthenticationClient.this.lambda$onAcquired$0(i, (android.hardware.fingerprint.IUdfpsOverlayController) obj);
            }
        });
        super.onAcquired(i, i2);
        com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(getSensorId()).incrementAcquireForUser(getTargetUserId(), isCryptoOperation());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAcquired$0(int i, android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController) throws android.os.RemoteException {
        iUdfpsOverlayController.onAcquired(getSensorId(), i);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient, com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        super.onError(i, i2);
        if (i == 18) {
            com.android.server.biometrics.sensors.BiometricNotificationUtils.showBadCalibrationNotification(getContext());
        }
        resetIgnoreDisplayTouches();
        this.mSensorOverlays.hide(getSensorId());
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        resetIgnoreDisplayTouches();
        this.mSensorOverlays.show(getSensorId(), getRequestReason(), this);
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        try {
            com.android.server.biometrics.Flags.deHidl();
            this.mCancellationSignal = doAuthenticate();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
            onError(1, 0);
            this.mSensorOverlays.hide(getSensorId());
            com.android.systemui.shared.Flags.sidefpsControllerRefactor();
            this.mCallback.onClientFinished(this, false);
        }
    }

    private android.hardware.biometrics.common.ICancellationSignal doAuthenticate() throws android.os.RemoteException {
        android.hardware.biometrics.common.ICancellationSignal authenticate;
        final com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession freshDaemon = getFreshDaemon();
        com.android.server.biometrics.log.OperationContextExt operationContext = getOperationContext();
        if (freshDaemon.hasContextMethods()) {
            authenticate = freshDaemon.getSession().authenticateWithContext(this.mOperationId, operationContext.toAidlContext(getOptions()));
        } else {
            authenticate = freshDaemon.getSession().authenticate(this.mOperationId);
        }
        getBiometricContext().subscribe(operationContext, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintAuthenticationClient$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintAuthenticationClient.this.lambda$doAuthenticate$1(freshDaemon, (android.hardware.biometrics.common.OperationContext) obj);
            }
        });
        if (getBiometricContext().isAwake()) {
            this.mALSProbeCallback.getProbe().enable();
        }
        return authenticate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doAuthenticate$1(com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        if (aidlSession.hasContextMethods()) {
            try {
                aidlSession.getSession().onContextChanged(operationContext);
                if (operationContext.operationState != null && operationContext.operationState.getTag() == 0) {
                    aidlSession.getSession().setIgnoreDisplayTouches(operationContext.operationState.getFingerprintOperationState().isHardwareIgnoringTouches);
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Unable to notify context changed", e);
            }
        }
        if (getBiometricContext().isAwake()) {
            this.mALSProbeCallback.getProbe().enable();
        } else {
            this.mALSProbeCallback.getProbe().disable();
        }
    }

    private void startAuthentication() {
        final com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession freshDaemon = getFreshDaemon();
        getBiometricContext().subscribe(getOperationContext(), new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintAuthenticationClient$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintAuthenticationClient.this.lambda$startAuthentication$2(freshDaemon, (android.hardware.biometrics.common.OperationContext) obj);
            }
        }, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintAuthenticationClient$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintAuthenticationClient.this.lambda$startAuthentication$3(freshDaemon, (android.hardware.biometrics.common.OperationContext) obj);
            }
        }, getOptions());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAuthentication$2(com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            if (aidlSession.hasContextMethods()) {
                this.mCancellationSignal = aidlSession.getSession().authenticateWithContext(this.mOperationId, operationContext);
            } else {
                this.mCancellationSignal = aidlSession.getSession().authenticate(this.mOperationId);
            }
            if (getBiometricContext().isAwake()) {
                this.mALSProbeCallback.getProbe().enable();
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
            onError(1, 0);
            this.mSensorOverlays.hide(getSensorId());
            com.android.systemui.shared.Flags.sidefpsControllerRefactor();
            this.mCallback.onClientFinished(this, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAuthentication$3(com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        if (aidlSession.hasContextMethods()) {
            try {
                aidlSession.getSession().onContextChanged(operationContext);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Unable to notify context changed", e);
            }
        }
        if (getBiometricContext().isAwake()) {
            this.mALSProbeCallback.getProbe().enable();
        } else {
            this.mALSProbeCallback.getProbe().disable();
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    protected void stopHalOperation() {
        resetIgnoreDisplayTouches();
        this.mSensorOverlays.hide(getSensorId());
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        unsubscribeBiometricContext();
        if (this.mCancellationSignal != null) {
            try {
                this.mCancellationSignal.cancel();
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception", e);
                onError(1, 0);
                this.mCallback.onClientFinished(this, false);
                return;
            }
        }
        android.util.Slog.e(TAG, "cancellation signal was null");
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public void onPointerDown(android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        try {
            this.mIsPointerDown = true;
            this.mState = 1;
            com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession freshDaemon = getFreshDaemon();
            if (freshDaemon.hasContextMethods()) {
                freshDaemon.getSession().onPointerDownWithContext(pointerContext);
            } else {
                freshDaemon.getSession().onPointerDown(pointerContext.pointerId, (int) pointerContext.x, (int) pointerContext.y, pointerContext.minor, pointerContext.major);
            }
            getListener().onUdfpsPointerDown(getSensorId());
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public void onPointerUp(android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        try {
            this.mIsPointerDown = false;
            this.mState = 3;
            com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession freshDaemon = getFreshDaemon();
            if (freshDaemon.hasContextMethods()) {
                freshDaemon.getSession().onPointerUpWithContext(pointerContext);
            } else {
                freshDaemon.getSession().onPointerUp(pointerContext.pointerId);
            }
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
        if (i == 2) {
            try {
                getFreshDaemon().getSession().onUiReady();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception", e);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.LockoutConsumer
    public void onLockoutTimed(long j) {
        this.mAuthSessionCoordinator.lockOutTimed(getTargetUserId(), getSensorStrength(), getSensorId(), j, getRequestId());
        getLogger().logOnError(getContext(), getOperationContext(), 7, 0, getTargetUserId());
        com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(getSensorId()).incrementTimedLockoutForUser(getTargetUserId());
        try {
            getListener().onError(getSensorId(), getCookie(), 7, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
        }
        resetIgnoreDisplayTouches();
        this.mSensorOverlays.hide(getSensorId());
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        this.mCallback.onClientFinished(this, false);
    }

    @Override // com.android.server.biometrics.sensors.LockoutConsumer
    public void onLockoutPermanent() {
        this.mAuthSessionCoordinator.lockedOutFor(getTargetUserId(), getSensorStrength(), getSensorId(), getRequestId());
        getLogger().logOnError(getContext(), getOperationContext(), 9, 0, getTargetUserId());
        com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(getSensorId()).incrementPermanentLockoutForUser(getTargetUserId());
        try {
            getListener().onError(getSensorId(), getCookie(), 9, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
        }
        resetIgnoreDisplayTouches();
        this.mSensorOverlays.hide(getSensorId());
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        this.mCallback.onClientFinished(this, false);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.PowerPressHandler
    public void onPowerPressed() {
    }
}
