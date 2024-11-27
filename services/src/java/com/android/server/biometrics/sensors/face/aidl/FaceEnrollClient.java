package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class FaceEnrollClient extends com.android.server.biometrics.sensors.EnrollClient<com.android.server.biometrics.sensors.face.aidl.AidlSession> {
    private static final java.lang.String TAG = "FaceEnrollClient";

    @android.annotation.Nullable
    private android.hardware.biometrics.common.ICancellationSignal mCancellationSignal;
    private final boolean mDebugConsent;

    @android.annotation.NonNull
    private final int[] mDisabledFeatures;

    @android.annotation.NonNull
    private final int[] mEnrollIgnoreList;

    @android.annotation.NonNull
    private final int[] mEnrollIgnoreListVendor;

    @android.annotation.Nullable
    private android.hardware.common.NativeHandle mHwPreviewHandle;
    private final int mMaxTemplatesPerUser;

    @android.annotation.Nullable
    private android.os.NativeHandle mOsPreviewHandle;
    private final com.android.server.biometrics.sensors.ClientMonitorCallback mPreviewHandleDeleterCallback;

    @android.annotation.Nullable
    private final android.view.Surface mPreviewSurface;

    public FaceEnrollClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> supplier, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull java.lang.String str, long j, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricUtils<android.hardware.face.Face> biometricUtils, @android.annotation.NonNull int[] iArr, int i2, @android.annotation.Nullable android.view.Surface surface, int i3, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, int i4, boolean z, android.hardware.face.FaceEnrollOptions faceEnrollOptions) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, i, bArr, str, biometricUtils, i2, i3, false, biometricLogger, biometricContext, android.hardware.biometrics.BiometricFaceConstants.reasonToMetric(faceEnrollOptions.getEnrollReason()));
        this.mPreviewHandleDeleterCallback = new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient.1
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z2) {
                com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient.this.releaseSurfaceHandlesIfNeeded();
            }
        };
        setRequestId(j);
        this.mEnrollIgnoreList = getContext().getResources().getIntArray(android.R.array.config_ethernet_interfaces);
        this.mEnrollIgnoreListVendor = getContext().getResources().getIntArray(android.R.array.config_face_acquire_keyguard_ignorelist);
        this.mMaxTemplatesPerUser = i4;
        this.mDebugConsent = z;
        this.mDisabledFeatures = iArr;
        this.mPreviewSurface = surface;
        android.util.Slog.w(TAG, "EnrollOptions " + android.hardware.face.FaceEnrollOptions.enrollReasonToString(faceEnrollOptions.getEnrollReason()));
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient, com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        com.android.server.biometrics.sensors.BiometricNotificationUtils.cancelFaceEnrollNotification(getContext());
        com.android.server.biometrics.sensors.BiometricNotificationUtils.cancelFaceReEnrollNotification(getContext());
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    @android.annotation.NonNull
    protected com.android.server.biometrics.sensors.ClientMonitorCallback wrapCallbackForStart(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        return new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(this.mPreviewHandleDeleterCallback, getLogger().getAmbientLightProbe(true), clientMonitorCallback);
    }

    @Override // com.android.server.biometrics.sensors.EnrollClient
    protected boolean hasReachedEnrollmentLimit() {
        return com.android.server.biometrics.sensors.face.FaceUtils.getInstance(getSensorId()).getBiometricsForUser(getContext(), getTargetUserId()).size() >= this.mMaxTemplatesPerUser;
    }

    private boolean shouldSendAcquiredMessage(int i, int i2) {
        return i == 22 ? !com.android.server.biometrics.Utils.listContains(this.mEnrollIgnoreListVendor, i2) : !com.android.server.biometrics.Utils.listContains(this.mEnrollIgnoreList, i);
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    public void onAcquired(int i, int i2) {
        onAcquiredInternal(i, i2, shouldSendAcquiredMessage(i, i2));
    }

    public void onEnrollmentFrame(@android.annotation.NonNull android.hardware.face.FaceEnrollFrame faceEnrollFrame) {
        int acquiredInfo = faceEnrollFrame.getData().getAcquiredInfo();
        int vendorCode = faceEnrollFrame.getData().getVendorCode();
        onAcquiredInternal(acquiredInfo, vendorCode, false);
        if (shouldSendAcquiredMessage(acquiredInfo, vendorCode)) {
            try {
                getListener().onEnrollmentFrame(faceEnrollFrame);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to send enrollment frame", e);
                this.mCallback.onClientFinished(this, false);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        obtainSurfaceHandlesIfNeeded();
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (this.mDebugConsent) {
                arrayList.add((byte) 2);
            }
            boolean z = true;
            for (int i : this.mDisabledFeatures) {
                if (com.android.server.biometrics.sensors.face.aidl.AidlConversionUtils.convertFrameworkToAidlFeature(i) == 1) {
                    z = false;
                }
            }
            if (z) {
                arrayList.add((byte) 1);
            }
            byte[] bArr = new byte[arrayList.size()];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                bArr[i2] = ((java.lang.Byte) arrayList.get(i2)).byteValue();
            }
            com.android.server.biometrics.Flags.deHidl();
            this.mCancellationSignal = doEnroll(bArr);
        } catch (android.os.RemoteException | java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Exception when requesting enroll", e);
            onError(2, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    private android.hardware.biometrics.common.ICancellationSignal doEnroll(byte[] bArr) throws android.os.RemoteException {
        android.hardware.biometrics.common.ICancellationSignal enrollWithContext;
        final com.android.server.biometrics.sensors.face.aidl.AidlSession freshDaemon = getFreshDaemon();
        android.hardware.keymaster.HardwareAuthToken hardwareAuthToken = com.android.server.biometrics.HardwareAuthTokenUtils.toHardwareAuthToken(this.mHardwareAuthToken);
        if (freshDaemon.hasContextMethods()) {
            com.android.server.biometrics.log.OperationContextExt operationContext = getOperationContext();
            if (freshDaemon.supportsFaceEnrollOptions()) {
                android.hardware.biometrics.face.FaceEnrollOptions faceEnrollOptions = new android.hardware.biometrics.face.FaceEnrollOptions();
                faceEnrollOptions.hardwareAuthToken = hardwareAuthToken;
                faceEnrollOptions.enrollmentType = (byte) 0;
                faceEnrollOptions.features = bArr;
                faceEnrollOptions.nativeHandlePreview = null;
                faceEnrollOptions.context = operationContext.toAidlContext();
                faceEnrollOptions.surfacePreview = this.mPreviewSurface;
                enrollWithContext = freshDaemon.getSession().enrollWithOptions(faceEnrollOptions);
            } else {
                enrollWithContext = freshDaemon.getSession().enrollWithContext(hardwareAuthToken, (byte) 0, bArr, this.mHwPreviewHandle, operationContext.toAidlContext());
            }
            getBiometricContext().subscribe(operationContext, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient.lambda$doEnroll$0(com.android.server.biometrics.sensors.face.aidl.AidlSession.this, (android.hardware.biometrics.common.OperationContext) obj);
                }
            });
            return enrollWithContext;
        }
        return freshDaemon.getSession().enroll(hardwareAuthToken, (byte) 0, bArr, this.mHwPreviewHandle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$doEnroll$0(com.android.server.biometrics.sensors.face.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            aidlSession.getSession().onContextChanged(operationContext);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to notify context changed", e);
        }
    }

    private void startEnroll(final byte[] bArr) throws android.os.RemoteException {
        final com.android.server.biometrics.sensors.face.aidl.AidlSession freshDaemon = getFreshDaemon();
        final android.hardware.keymaster.HardwareAuthToken hardwareAuthToken = com.android.server.biometrics.HardwareAuthTokenUtils.toHardwareAuthToken(this.mHardwareAuthToken);
        if (freshDaemon.hasContextMethods()) {
            getBiometricContext().subscribe(getOperationContext(), new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient.this.lambda$startEnroll$1(freshDaemon, hardwareAuthToken, bArr, (android.hardware.biometrics.common.OperationContext) obj);
                }
            }, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient.lambda$startEnroll$2(com.android.server.biometrics.sensors.face.aidl.AidlSession.this, (android.hardware.biometrics.common.OperationContext) obj);
                }
            }, null);
        } else {
            this.mCancellationSignal = freshDaemon.getSession().enroll(hardwareAuthToken, (byte) 0, bArr, this.mHwPreviewHandle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startEnroll$1(com.android.server.biometrics.sensors.face.aidl.AidlSession aidlSession, android.hardware.keymaster.HardwareAuthToken hardwareAuthToken, byte[] bArr, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            if (aidlSession.supportsFaceEnrollOptions()) {
                android.hardware.biometrics.face.FaceEnrollOptions faceEnrollOptions = new android.hardware.biometrics.face.FaceEnrollOptions();
                faceEnrollOptions.hardwareAuthToken = hardwareAuthToken;
                faceEnrollOptions.enrollmentType = (byte) 0;
                faceEnrollOptions.features = bArr;
                faceEnrollOptions.nativeHandlePreview = null;
                faceEnrollOptions.context = operationContext;
                faceEnrollOptions.surfacePreview = this.mPreviewSurface;
                this.mCancellationSignal = aidlSession.getSession().enrollWithOptions(faceEnrollOptions);
            } else {
                this.mCancellationSignal = aidlSession.getSession().enrollWithContext(hardwareAuthToken, (byte) 0, bArr, this.mHwPreviewHandle, operationContext);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Exception when requesting enroll", e);
            onError(2, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startEnroll$2(com.android.server.biometrics.sensors.face.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            aidlSession.getSession().onContextChanged(operationContext);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to notify context changed", e);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    protected void stopHalOperation() {
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

    private void obtainSurfaceHandlesIfNeeded() {
        if (this.mPreviewSurface != null) {
            this.mOsPreviewHandle = com.android.server.biometrics.sensors.face.FaceService.acquireSurfaceHandle(this.mPreviewSurface);
            try {
                this.mHwPreviewHandle = com.android.server.biometrics.sensors.face.aidl.AidlNativeHandleUtils.dup(this.mOsPreviewHandle);
                android.util.Slog.v(TAG, "Obtained handles for the preview surface.");
            } catch (java.io.IOException e) {
                this.mHwPreviewHandle = null;
                android.util.Slog.e(TAG, "Failed to dup mOsPreviewHandle", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseSurfaceHandlesIfNeeded() {
        if (this.mPreviewSurface != null && this.mHwPreviewHandle == null) {
            android.util.Slog.w(TAG, "mHwPreviewHandle is null even though mPreviewSurface is not null.");
        }
        if (this.mHwPreviewHandle != null) {
            try {
                android.util.Slog.v(TAG, "Closing mHwPreviewHandle");
                com.android.server.biometrics.sensors.face.aidl.AidlNativeHandleUtils.close(this.mHwPreviewHandle);
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Failed to close mPreviewSurface", e);
            }
            this.mHwPreviewHandle = null;
        }
        if (this.mOsPreviewHandle != null) {
            android.util.Slog.v(TAG, "Releasing mOsPreviewHandle");
            com.android.server.biometrics.sensors.face.FaceService.releaseSurfaceHandle(this.mOsPreviewHandle);
            this.mOsPreviewHandle = null;
        }
        if (this.mPreviewSurface != null) {
            android.util.Slog.v(TAG, "Releasing mPreviewSurface");
            this.mPreviewSurface.release();
        }
    }
}
