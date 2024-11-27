package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
public class FingerprintEnrollClient extends com.android.server.biometrics.sensors.EnrollClient<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> implements com.android.server.biometrics.sensors.fingerprint.Udfps, com.android.server.biometrics.sensors.fingerprint.PowerPressHandler {
    private static final java.lang.String TAG = "FingerprintEnrollClient";

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.CallbackWithProbe<com.android.server.biometrics.log.Probe> mALSProbeCallback;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthenticationStateListeners mAuthenticationStateListeners;

    @android.annotation.Nullable
    private android.hardware.biometrics.common.ICancellationSignal mCancellationSignal;
    private final int mEnrollReason;
    private boolean mIsPointerDown;
    private final int mMaxTemplatesPerUser;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.SensorOverlays mSensorOverlays;

    @android.annotation.NonNull
    private final android.hardware.fingerprint.FingerprintSensorPropertiesInternal mSensorProps;

    private static boolean shouldVibrateFor(android.content.Context context, android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
        if (fingerprintSensorPropertiesInternal != null) {
            return !fingerprintSensorPropertiesInternal.isAnyUdfpsType() || ((android.view.accessibility.AccessibilityManager) context.getSystemService(android.view.accessibility.AccessibilityManager.class)).isTouchExplorationEnabled();
        }
        return true;
    }

    public FingerprintEnrollClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> supplier, @android.annotation.NonNull android.os.IBinder iBinder, long j, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils<android.hardware.fingerprint.Fingerprint> biometricUtils, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, @android.annotation.Nullable android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController, @android.annotation.Nullable android.hardware.fingerprint.ISidefpsController iSidefpsController, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, int i3, int i4, @android.annotation.NonNull android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, bArr, str, biometricUtils, 0, i2, shouldVibrateFor(context, fingerprintSensorPropertiesInternal), biometricLogger, biometricContext, android.hardware.biometrics.BiometricFingerprintConstants.reasonToMetric(fingerprintEnrollOptions.getEnrollReason()));
        setRequestId(j);
        this.mSensorProps = fingerprintSensorPropertiesInternal;
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        this.mSensorOverlays = new com.android.server.biometrics.sensors.SensorOverlays(iUdfpsOverlayController, iSidefpsController);
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mMaxTemplatesPerUser = i3;
        this.mALSProbeCallback = getLogger().getAmbientLightProbe(true);
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
        return new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(this.mALSProbeCallback, getBiometricContextUnsubscriber(), clientMonitorCallback);
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient
    public void onEnrollResult(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, final int i) {
        super.onEnrollResult(identifier, i);
        this.mSensorOverlays.ifUdfps(new com.android.server.biometrics.sensors.SensorOverlays.OverlayControllerConsumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient$$ExternalSyntheticLambda2
            @Override // com.android.server.biometrics.sensors.SensorOverlays.OverlayControllerConsumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient.this.lambda$onEnrollResult$0(i, (android.hardware.fingerprint.IUdfpsOverlayController) obj);
            }
        });
        if (i == 0) {
            resetIgnoreDisplayTouches();
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
        boolean z = i == 0;
        if (this.mSensorProps != null && this.mSensorProps.isAnyUdfpsType()) {
            if (z && this.mShouldVibrate) {
                vibrateSuccess();
            }
            this.mSensorOverlays.ifUdfps(new com.android.server.biometrics.sensors.SensorOverlays.OverlayControllerConsumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient$$ExternalSyntheticLambda4
                @Override // com.android.server.biometrics.sensors.SensorOverlays.OverlayControllerConsumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient.this.lambda$onAcquired$1(i, (android.hardware.fingerprint.IUdfpsOverlayController) obj);
                }
            });
        }
        this.mSensorOverlays.ifUdfps(new com.android.server.biometrics.sensors.SensorOverlays.OverlayControllerConsumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient$$ExternalSyntheticLambda5
            @Override // com.android.server.biometrics.sensors.SensorOverlays.OverlayControllerConsumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient.this.lambda$onAcquired$2(i, i2, (android.hardware.fingerprint.IUdfpsOverlayController) obj);
            }
        });
        this.mCallback.onBiometricAction(0);
        super.onAcquired(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAcquired$1(int i, android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController) throws android.os.RemoteException {
        iUdfpsOverlayController.onAcquired(getSensorId(), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAcquired$2(int i, int i2, android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController) throws android.os.RemoteException {
        if (com.android.server.biometrics.sensors.fingerprint.UdfpsHelper.isValidAcquisitionMessage(getContext(), i, i2)) {
            iUdfpsOverlayController.onEnrollmentHelp(getSensorId());
        }
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient, com.android.server.biometrics.sensors.AcquisitionClient, com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        super.onError(i, i2);
        resetIgnoreDisplayTouches();
        this.mSensorOverlays.hide(getSensorId());
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient
    protected boolean hasReachedEnrollmentLimit() {
        return this.mBiometricUtils.getBiometricsForUser(getContext(), getTargetUserId()).size() >= this.mMaxTemplatesPerUser;
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        resetIgnoreDisplayTouches();
        this.mSensorOverlays.show(getSensorId(), getRequestReasonFromEnrollReason(this.mEnrollReason), this);
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        com.android.server.biometrics.sensors.BiometricNotificationUtils.cancelBadCalibrationNotification(getContext());
        try {
            com.android.server.biometrics.Flags.deHidl();
            this.mCancellationSignal = doEnroll();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting enroll", e);
            onError(2, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    private android.hardware.biometrics.common.ICancellationSignal doEnroll() throws android.os.RemoteException {
        final com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession freshDaemon = getFreshDaemon();
        android.hardware.keymaster.HardwareAuthToken hardwareAuthToken = com.android.server.biometrics.HardwareAuthTokenUtils.toHardwareAuthToken(this.mHardwareAuthToken);
        if (freshDaemon.hasContextMethods()) {
            com.android.server.biometrics.log.OperationContextExt operationContext = getOperationContext();
            android.hardware.biometrics.common.ICancellationSignal enrollWithContext = freshDaemon.getSession().enrollWithContext(hardwareAuthToken, operationContext.toAidlContext());
            getBiometricContext().subscribe(operationContext, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient.lambda$doEnroll$3(com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession.this, (android.hardware.biometrics.common.OperationContext) obj);
                }
            });
            return enrollWithContext;
        }
        return freshDaemon.getSession().enroll(hardwareAuthToken);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$doEnroll$3(com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            aidlSession.getSession().onContextChanged(operationContext);
            if (operationContext.operationState != null && operationContext.operationState.getTag() == 0) {
                aidlSession.getSession().setIgnoreDisplayTouches(operationContext.operationState.getFingerprintOperationState().isHardwareIgnoringTouches);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to notify context changed", e);
        }
    }

    private void startEnroll() throws android.os.RemoteException {
        final com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession freshDaemon = getFreshDaemon();
        final android.hardware.keymaster.HardwareAuthToken hardwareAuthToken = com.android.server.biometrics.HardwareAuthTokenUtils.toHardwareAuthToken(this.mHardwareAuthToken);
        if (freshDaemon.hasContextMethods()) {
            getBiometricContext().subscribe(getOperationContext(), new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient.this.lambda$startEnroll$4(freshDaemon, hardwareAuthToken, (android.hardware.biometrics.common.OperationContext) obj);
                }
            }, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient.lambda$startEnroll$5(com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession.this, (android.hardware.biometrics.common.OperationContext) obj);
                }
            }, null);
        } else {
            this.mCancellationSignal = freshDaemon.getSession().enroll(hardwareAuthToken);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startEnroll$4(com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession aidlSession, android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            this.mCancellationSignal = aidlSession.getSession().enrollWithContext(hardwareAuthToken, operationContext);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting enroll", e);
            onError(2, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startEnroll$5(com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            aidlSession.getSession().onContextChanged(operationContext);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to notify context changed", e);
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
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception when requesting cancel", e);
                onError(1, 0);
                this.mCallback.onClientFinished(this, false);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public void onPointerDown(android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        try {
            this.mIsPointerDown = true;
            com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession freshDaemon = getFreshDaemon();
            if (freshDaemon.hasContextMethods()) {
                freshDaemon.getSession().onPointerDownWithContext(pointerContext);
            } else {
                freshDaemon.getSession().onPointerDown(pointerContext.pointerId, (int) pointerContext.x, (int) pointerContext.y, pointerContext.minor, pointerContext.major);
            }
            getListener().onUdfpsPointerDown(getSensorId());
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to send pointer down", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.Udfps
    public void onPointerUp(android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        try {
            this.mIsPointerDown = false;
            com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession freshDaemon = getFreshDaemon();
            if (freshDaemon.hasContextMethods()) {
                freshDaemon.getSession().onPointerUpWithContext(pointerContext);
            } else {
                freshDaemon.getSession().onPointerUp(pointerContext.pointerId);
            }
            getListener().onUdfpsPointerUp(getSensorId());
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to send pointer up", e);
        }
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
                    getFreshDaemon().getSession().onUiReady();
                    break;
                default:
                    android.util.Slog.w(TAG, "No matching event for onUdfpsUiEvent");
                    break;
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to send onUdfpsUiEvent", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.PowerPressHandler
    public void onPowerPressed() {
    }
}
