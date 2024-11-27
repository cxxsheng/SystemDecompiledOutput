package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class FaceDetectClient extends com.android.server.biometrics.sensors.AcquisitionClient<com.android.server.biometrics.sensors.face.aidl.AidlSession> implements com.android.server.biometrics.sensors.DetectionConsumer {
    private static final java.lang.String TAG = "FaceDetectClient";

    @android.annotation.Nullable
    private android.hardware.biometrics.common.ICancellationSignal mCancellationSignal;
    private final boolean mIsStrongBiometric;
    private final android.hardware.face.FaceAuthenticateOptions mOptions;

    @android.annotation.Nullable
    private android.hardware.SensorPrivacyManager mSensorPrivacyManager;

    FaceDetectClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> supplier, @android.annotation.NonNull android.os.IBinder iBinder, long j, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z) {
        this(context, supplier, iBinder, j, clientMonitorCallbackConverter, faceAuthenticateOptions, biometricLogger, biometricContext, z, (android.hardware.SensorPrivacyManager) context.getSystemService(android.hardware.SensorPrivacyManager.class));
    }

    @com.android.internal.annotations.VisibleForTesting
    FaceDetectClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> supplier, @android.annotation.NonNull android.os.IBinder iBinder, long j, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z, android.hardware.SensorPrivacyManager sensorPrivacyManager) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, faceAuthenticateOptions.getUserId(), faceAuthenticateOptions.getOpPackageName(), 0, faceAuthenticateOptions.getSensorId(), false, biometricLogger, biometricContext);
        setRequestId(j);
        this.mIsStrongBiometric = z;
        this.mSensorPrivacyManager = sensorPrivacyManager;
        this.mOptions = faceAuthenticateOptions;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    protected void stopHalOperation() {
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
        if (this.mSensorPrivacyManager != null && this.mSensorPrivacyManager.isSensorPrivacyEnabled(1, 2)) {
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
            return;
        }
        try {
            com.android.server.biometrics.Flags.deHidl();
            this.mCancellationSignal = doDetectInteraction();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting face detect", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    private android.hardware.biometrics.common.ICancellationSignal doDetectInteraction() throws android.os.RemoteException {
        final com.android.server.biometrics.sensors.face.aidl.AidlSession freshDaemon = getFreshDaemon();
        if (freshDaemon.hasContextMethods()) {
            com.android.server.biometrics.log.OperationContextExt operationContext = getOperationContext();
            android.hardware.biometrics.common.ICancellationSignal detectInteractionWithContext = freshDaemon.getSession().detectInteractionWithContext(operationContext.toAidlContext(this.mOptions));
            getBiometricContext().subscribe(operationContext, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceDetectClient$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.face.aidl.FaceDetectClient.lambda$doDetectInteraction$0(com.android.server.biometrics.sensors.face.aidl.AidlSession.this, (android.hardware.biometrics.common.OperationContext) obj);
                }
            });
            return detectInteractionWithContext;
        }
        return freshDaemon.getSession().detectInteraction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$doDetectInteraction$0(com.android.server.biometrics.sensors.face.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            aidlSession.getSession().onContextChanged(operationContext);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to notify context changed", e);
        }
    }

    private void startDetect() throws android.os.RemoteException {
        final com.android.server.biometrics.sensors.face.aidl.AidlSession freshDaemon = getFreshDaemon();
        if (freshDaemon.hasContextMethods()) {
            getBiometricContext().subscribe(getOperationContext(), new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceDetectClient$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.face.aidl.FaceDetectClient.this.lambda$startDetect$1(freshDaemon, (android.hardware.biometrics.common.OperationContext) obj);
                }
            }, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceDetectClient$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.face.aidl.FaceDetectClient.lambda$startDetect$2(com.android.server.biometrics.sensors.face.aidl.AidlSession.this, (android.hardware.biometrics.common.OperationContext) obj);
                }
            }, this.mOptions);
        } else {
            this.mCancellationSignal = freshDaemon.getSession().detectInteraction();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDetect$1(com.android.server.biometrics.sensors.face.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            this.mCancellationSignal = aidlSession.getSession().detectInteractionWithContext(operationContext);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting face detect", e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startDetect$2(com.android.server.biometrics.sensors.face.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
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
