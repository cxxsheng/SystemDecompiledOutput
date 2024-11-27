package com.android.server.biometrics.sensors.face.hidl;

/* loaded from: classes.dex */
class FaceAuthenticationClient extends com.android.server.biometrics.sensors.AuthenticationClient<android.hardware.biometrics.face.V1_0.IBiometricsFace, android.hardware.face.FaceAuthenticateOptions> {
    private static final java.lang.String TAG = "FaceAuthenticationClient";

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthenticationStateListeners mAuthenticationStateListeners;
    private final int[] mBiometricPromptIgnoreList;
    private final int[] mBiometricPromptIgnoreListVendor;
    private final int[] mKeyguardIgnoreList;
    private final int[] mKeyguardIgnoreListVendor;
    private int mLastAcquire;
    private android.hardware.SensorPrivacyManager mSensorPrivacyManager;
    private final com.android.server.biometrics.sensors.face.UsageStats mUsageStats;

    FaceAuthenticationClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.face.V1_0.IBiometricsFace> supplier, @android.annotation.NonNull android.os.IBinder iBinder, long j, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j2, boolean z, @android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, int i, boolean z2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z3, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutTracker lockoutTracker, @android.annotation.NonNull com.android.server.biometrics.sensors.face.UsageStats usageStats, boolean z4, int i2, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners) {
        super(context, supplier, iBinder, clientMonitorCallbackConverter, j2, z, faceAuthenticateOptions, i, z2, biometricLogger, biometricContext, z3, null, lockoutTracker, z4, false, i2);
        setRequestId(j);
        this.mUsageStats = usageStats;
        this.mSensorPrivacyManager = (android.hardware.SensorPrivacyManager) context.getSystemService(android.hardware.SensorPrivacyManager.class);
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
        if (this.mSensorPrivacyManager != null && this.mSensorPrivacyManager.isSensorPrivacyEnabled(1, 2)) {
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
            return;
        }
        try {
            getFreshDaemon().authenticate(this.mOperationId);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting auth", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AcquisitionClient
    protected void stopHalOperation() {
        try {
            getFreshDaemon().cancel();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when requesting cancel", e);
            onError(1, 0);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public boolean wasUserDetected() {
        return (this.mLastAcquire == 11 || this.mLastAcquire == 21) ? false : true;
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    protected void handleLifecycleAfterAuth(boolean z) {
        this.mCallback.onClientFinished(this, true);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient
    public int handleFailedAttempt(int i) {
        int lockoutModeForUser = getLockoutTracker().getLockoutModeForUser(i);
        com.android.server.biometrics.sensors.PerformanceTracker instanceForSensorId = com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(getSensorId());
        if (lockoutModeForUser == 2) {
            instanceForSensorId.incrementPermanentLockoutForUser(i);
        } else if (lockoutModeForUser == 1) {
            instanceForSensorId.incrementTimedLockoutForUser(i);
        }
        return lockoutModeForUser;
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

    private boolean shouldSend(int i, int i2) {
        if (i == 22) {
            return !com.android.server.biometrics.Utils.listContains(getAcquireVendorIgnorelist(), i2);
        }
        return !com.android.server.biometrics.Utils.listContains(getAcquireIgnorelist(), i);
    }

    @Override // com.android.server.biometrics.sensors.AuthenticationClient, com.android.server.biometrics.sensors.AcquisitionClient
    public void onAcquired(int i, int i2) {
        this.mLastAcquire = i;
        if (i == 13) {
            com.android.server.biometrics.sensors.BiometricNotificationUtils.showReEnrollmentNotification(getContext());
        }
        if (getLockoutTracker().getLockoutModeForUser(getTargetUserId()) == 0) {
            com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(getSensorId()).incrementAcquireForUser(getTargetUserId(), isCryptoOperation());
        }
        onAcquiredInternal(i, i2, shouldSend(i, i2));
    }
}
