package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class FaceAuthenticationClient extends com.android.server.biometrics.sensors.AuthenticationClient<com.android.server.biometrics.sensors.face.aidl.AidlSession, android.hardware.face.FaceAuthenticateOptions> implements com.android.server.biometrics.sensors.LockoutConsumer {
    private static final java.lang.String TAG = "FaceAuthenticationClient";

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthSessionCoordinator mAuthSessionCoordinator;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthenticationStateListeners mAuthenticationStateListeners;
    private final int[] mBiometricPromptIgnoreList;
    private final int[] mBiometricPromptIgnoreListVendor;

    @android.annotation.Nullable
    private android.hardware.biometrics.common.ICancellationSignal mCancellationSignal;
    private final int[] mKeyguardIgnoreList;
    private final int[] mKeyguardIgnoreListVendor;
    private int mLastAcquire;

    @android.annotation.Nullable
    private final android.app.NotificationManager mNotificationManager;

    @android.annotation.Nullable
    private final android.hardware.SensorPrivacyManager mSensorPrivacyManager;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.UsageStats mUsageStats;

    public FaceAuthenticationClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> supplier, @android.annotation.NonNull android.os.IBinder iBinder, long j, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j2, boolean z, @android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, int i, boolean z2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z3, @android.annotation.NonNull com.android.server.biometrics.sensors.face.UsageStats usageStats, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutTracker lockoutTracker, boolean z4, int i2, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners) {
        this(context, supplier, iBinder, j, clientMonitorCallbackConverter, j2, z, faceAuthenticateOptions, i, z2, biometricLogger, biometricContext, z3, usageStats, lockoutTracker, z4, (android.hardware.SensorPrivacyManager) context.getSystemService(android.hardware.SensorPrivacyManager.class), i2, authenticationStateListeners);
    }

    @com.android.internal.annotations.VisibleForTesting
    FaceAuthenticationClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> supplier, @android.annotation.NonNull android.os.IBinder iBinder, long j, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j2, boolean z, @android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, int i, boolean z2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z3, @android.annotation.NonNull com.android.server.biometrics.sensors.face.UsageStats usageStats, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutTracker lockoutTracker, boolean z4, android.hardware.SensorPrivacyManager sensorPrivacyManager, int i2, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, j2, z, faceAuthenticateOptions, i, z2, biometricLogger, biometricContext, z3, null, lockoutTracker, z4, false, i2);
        this.mLastAcquire = 23;
        setRequestId(j);
        this.mUsageStats = usageStats;
        this.mNotificationManager = (android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class);
        this.mSensorPrivacyManager = sensorPrivacyManager;
        this.mAuthSessionCoordinator = biometricContext.getAuthSessionCoordinator();
        this.mAuthenticationStateListeners = authenticationStateListeners;
        android.content.res.Resources resources = getContext().getResources();
        this.mBiometricPromptIgnoreList = resources.getIntArray(android.R.array.config_ephemeralResolverPackage);
        this.mBiometricPromptIgnoreListVendor = resources.getIntArray(android.R.array.config_face_acquire_enroll_ignorelist);
        this.mKeyguardIgnoreList = resources.getIntArray(android.R.array.config_face_acquire_biometricprompt_ignorelist);
        this.mKeyguardIgnoreListVendor = resources.getIntArray(android.R.array.config_face_acquire_vendor_biometricprompt_ignorelist);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        this.mState = 1;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    @android.annotation.NonNull
    protected com.android.server.biometrics.sensors.ClientMonitorCallback wrapCallbackForStart(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        return new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(getLogger().getAmbientLightProbe(true), clientMonitorCallback);
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            if (this.mSensorPrivacyManager != null && this.mSensorPrivacyManager.isSensorPrivacyEnabled(1, 2)) {
                onError(1, 0);
                this.mCallback.onClientFinished(this, false);
            } else {
                com.android.server.biometrics.Flags.deHidl();
                this.mCancellationSignal = doAuthenticate();
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting auth", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    private android.hardware.biometrics.common.ICancellationSignal doAuthenticate() throws android.os.RemoteException {
        final com.android.server.biometrics.sensors.face.aidl.AidlSession freshDaemon = getFreshDaemon();
        if (freshDaemon.hasContextMethods()) {
            com.android.server.biometrics.log.OperationContextExt operationContext = getOperationContext();
            android.hardware.biometrics.common.ICancellationSignal authenticateWithContext = freshDaemon.getSession().authenticateWithContext(this.mOperationId, operationContext.toAidlContext(getOptions()));
            getBiometricContext().subscribe(operationContext, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient.lambda$doAuthenticate$0(com.android.server.biometrics.sensors.face.aidl.AidlSession.this, (android.hardware.biometrics.common.OperationContext) obj);
                }
            });
            return authenticateWithContext;
        }
        return freshDaemon.getSession().authenticate(this.mOperationId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$doAuthenticate$0(com.android.server.biometrics.sensors.face.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            aidlSession.getSession().onContextChanged(operationContext);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to notify context changed", e);
        }
    }

    private void startAuthenticate() throws android.os.RemoteException {
        final com.android.server.biometrics.sensors.face.aidl.AidlSession freshDaemon = getFreshDaemon();
        if (freshDaemon.hasContextMethods()) {
            getBiometricContext().subscribe(getOperationContext(), new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient.this.lambda$startAuthenticate$1(freshDaemon, (android.hardware.biometrics.common.OperationContext) obj);
                }
            }, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient.lambda$startAuthenticate$2(com.android.server.biometrics.sensors.face.aidl.AidlSession.this, (android.hardware.biometrics.common.OperationContext) obj);
                }
            }, getOptions());
        } else {
            this.mCancellationSignal = freshDaemon.getSession().authenticate(this.mOperationId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAuthenticate$1(com.android.server.biometrics.sensors.face.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
        try {
            this.mCancellationSignal = aidlSession.getSession().authenticateWithContext(this.mOperationId, operationContext);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting auth", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startAuthenticate$2(com.android.server.biometrics.sensors.face.aidl.AidlSession aidlSession, android.hardware.biometrics.common.OperationContext operationContext) {
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

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public boolean wasUserDetected() {
        return (this.mLastAcquire == 11 || this.mLastAcquire == 21 || this.mLastAcquire == 23) ? false : true;
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    protected void handleLifecycleAfterAuth(boolean z) {
        this.mCallback.onClientFinished(this, true);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AuthenticationConsumer
    public void onAuthenticated(android.hardware.biometrics.BiometricAuthenticator.Identifier identifier, boolean z, java.util.ArrayList<java.lang.Byte> arrayList) {
        super.onAuthenticated(identifier, z, arrayList);
        this.mState = 4;
        this.mUsageStats.addEvent(new com.android.server.biometrics.sensors.face.UsageStats.AuthenticationEvent(getStartTimeMs(), java.lang.System.currentTimeMillis() - getStartTimeMs(), z, 0, 0, getTargetUserId()));
        if (android.adaptiveauth.Flags.reportBiometricAuthAttempts()) {
            if (z) {
                this.mAuthenticationStateListeners.onAuthenticationSucceeded(getRequestReason(), getTargetUserId());
            } else {
                this.mAuthenticationStateListeners.onAuthenticationFailed(getRequestReason(), getTargetUserId());
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient, com.android.server.biometrics.sensors.ErrorConsumer
    public void onError(int i, int i2) {
        this.mUsageStats.addEvent(new com.android.server.biometrics.sensors.face.UsageStats.AuthenticationEvent(getStartTimeMs(), java.lang.System.currentTimeMillis() - getStartTimeMs(), false, i, i2, getTargetUserId()));
        super.onError(i, i2);
    }

    private int[] getAcquireIgnorelist() {
        return isBiometricPrompt() ? this.mBiometricPromptIgnoreList : this.mKeyguardIgnoreList;
    }

    private int[] getAcquireVendorIgnorelist() {
        return isBiometricPrompt() ? this.mBiometricPromptIgnoreListVendor : this.mKeyguardIgnoreListVendor;
    }

    private boolean shouldSendAcquiredMessage(int i, int i2) {
        return i == 22 ? !com.android.server.biometrics.Utils.listContains(getAcquireVendorIgnorelist(), i2) : !com.android.server.biometrics.Utils.listContains(getAcquireIgnorelist(), i);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient
    public void onAcquired(int i, int i2) {
        this.mLastAcquire = i;
        onAcquiredInternal(i, i2, shouldSendAcquiredMessage(i, i2));
        if (getLockoutTracker() == null || getLockoutTracker().getLockoutModeForUser(getTargetUserId()) == 0) {
            com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(getSensorId()).incrementAcquireForUser(getTargetUserId(), isCryptoOperation());
        }
    }

    public void onAuthenticationFrame(@android.annotation.NonNull android.hardware.face.FaceAuthenticationFrame faceAuthenticationFrame) {
        int acquiredInfo = faceAuthenticationFrame.getData().getAcquiredInfo();
        int vendorCode = faceAuthenticationFrame.getData().getVendorCode();
        this.mLastAcquire = acquiredInfo;
        onAcquiredInternal(acquiredInfo, vendorCode, false);
        if (shouldSendAcquiredMessage(acquiredInfo, vendorCode)) {
            try {
                getListener().onAuthenticationFrame(faceAuthenticationFrame);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to send authentication frame", e);
                this.mCallback.onClientFinished(this, false);
            }
        }
    }

    @Override // com.android.server.biometrics.sensors.LockoutConsumer
    public void onLockoutTimed(long j) {
        this.mAuthSessionCoordinator.lockOutTimed(getTargetUserId(), getSensorStrength(), getSensorId(), j, getRequestId());
        getLogger().logOnError(getContext(), getOperationContext(), 7, 0, getTargetUserId());
        com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(getSensorId()).incrementTimedLockoutForUser(getTargetUserId());
        onError(7, 0);
    }

    @Override // com.android.server.biometrics.sensors.LockoutConsumer
    public void onLockoutPermanent() {
        this.mAuthSessionCoordinator.lockedOutFor(getTargetUserId(), getSensorStrength(), getSensorId(), getRequestId());
        getLogger().logOnError(getContext(), getOperationContext(), 9, 0, getTargetUserId());
        com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(getSensorId()).incrementPermanentLockoutForUser(getTargetUserId());
        onError(9, 0);
    }
}
