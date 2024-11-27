package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
public class FingerprintDetectClient extends com.android.server.biometrics.sensors.AcquisitionClient<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> implements com.android.server.biometrics.sensors.DetectionConsumer {
    private static final java.lang.String TAG = "FingerprintDetectClient";

    @android.annotation.Nullable
    private android.hardware.biometrics.common.ICancellationSignal mCancellationSignal;
    private final boolean mIsStrongBiometric;
    private final android.hardware.fingerprint.FingerprintAuthenticateOptions mOptions;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.SensorOverlays mSensorOverlays;

    public FingerprintDetectClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> supplier, @android.annotation.NonNull android.os.IBinder iBinder, long j, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.Nullable android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController, boolean z) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, fingerprintAuthenticateOptions.getUserId(), fingerprintAuthenticateOptions.getOpPackageName(), 0, fingerprintAuthenticateOptions.getSensorId(), true, biometricLogger, biometricContext);
        setRequestId(j);
        this.mIsStrongBiometric = z;
        com.android.systemui.shared.Flags.sidefpsControllerRefactor();
        this.mSensorOverlays = new com.android.server.biometrics.sensors.SensorOverlays(iUdfpsOverlayController, null);
        this.mOptions = fingerprintAuthenticateOptions;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    protected void stopHalOperation() {
        resetIgnoreDisplayTouches();
        this.mSensorOverlays.hide(getSensorId());
        unsubscribeBiometricContext();
        if (this.mCancellationSignal != null) {
            try {
                this.mCancellationSignal.cancel();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception", e);
                this.mCallback.onClientFinished(this, false);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        resetIgnoreDisplayTouches();
        this.mSensorOverlays.show(getSensorId(), 4, this);
        try {
            com.android.server.biometrics.Flags.deHidl();
            this.mCancellationSignal = doDetectInteraction();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting finger detect", e);
            this.mSensorOverlays.hide(getSensorId());
            this.mCallback.onClientFinished(this, false);
        }
    }

    private android.hardware.biometrics.common.ICancellationSignal doDetectInteraction() throws android.os.RemoteException {
        final com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession freshDaemon = getFreshDaemon();
        if (freshDaemon.hasContextMethods()) {
            com.android.server.biometrics.log.OperationContextExt operationContext = getOperationContext();
            android.hardware.biometrics.common.ICancellationSignal detectInteractionWithContext = freshDaemon.getSession().detectInteractionWithContext(operationContext.toAidlContext(this.mOptions));
            getBiometricContext().subscribe(operationContext, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintDetectClient$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintDetectClient.lambda$doDetectInteraction$0(com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession.this, (android.hardware.biometrics.common.OperationContext) obj);
                }
            });
            return detectInteractionWithContext;
        }
        return freshDaemon.getSession().detectInteraction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$doDetectInteraction$0(com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            aidlSession.getSession().onContextChanged(operationContext);
            if (operationContext.operationState != null && operationContext.operationState.getTag() == 0) {
                aidlSession.getSession().setIgnoreDisplayTouches(operationContext.operationState.getFingerprintOperationState().isHardwareIgnoringTouches);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to notify context changed", e);
        }
    }

    private void startDetectInteraction() throws android.os.RemoteException {
        final com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession freshDaemon = getFreshDaemon();
        if (freshDaemon.hasContextMethods()) {
            getBiometricContext().subscribe(getOperationContext(), new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintDetectClient$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintDetectClient.this.lambda$startDetectInteraction$1(freshDaemon, (android.hardware.biometrics.common.OperationContext) obj);
                }
            }, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintDetectClient$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintDetectClient.lambda$startDetectInteraction$2(com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession.this, (android.hardware.biometrics.common.OperationContext) obj);
                }
            }, this.mOptions);
        } else {
            this.mCancellationSignal = freshDaemon.getSession().detectInteraction();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDetectInteraction$1(com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            this.mCancellationSignal = aidlSession.getSession().detectInteractionWithContext(operationContext);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to start detect interaction", e);
            this.mSensorOverlays.hide(getSensorId());
            this.mCallback.onClientFinished(this, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startDetectInteraction$2(com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            aidlSession.getSession().onContextChanged(operationContext);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to notify context changed", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.DetectionConsumer
    public void onInteractionDetected() {
        vibrateSuccess();
        try {
            getListener().onDetected(getSensorId(), getTargetUserId(), this.mIsStrongBiometric);
            this.mCallback.onClientFinished(this, true);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when sending onDetected", e);
            this.mCallback.onClientFinished(this, false);
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
