package com.android.server.biometrics;

/* loaded from: classes.dex */
public final class AuthSession implements android.os.IBinder.DeathRecipient {
    private static final boolean DEBUG = true;
    private static final java.lang.String TAG = "BiometricService/AuthSession";
    private int mAuthenticatedSensorId;
    private long mAuthenticatedTimeMs;

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.BiometricContext mBiometricContext;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.biometrics.log.BiometricFrameworkStatsLogger mBiometricFrameworkStatsLogger;
    private boolean mCancelled;
    private final com.android.server.biometrics.AuthSession.ClientDeathReceiver mClientDeathReceiver;
    private final android.hardware.biometrics.IBiometricServiceReceiver mClientReceiver;
    private final android.content.Context mContext;
    private final boolean mDebugEnabled;
    private int mErrorEscrow;
    private final java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> mFingerprintSensorProperties;
    private final android.security.KeyStore mKeyStore;
    private final java.lang.String mOpPackageName;

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.OperationContextExt mOperationContext;
    private final long mOperationId;
    final com.android.server.biometrics.PreAuthInfo mPreAuthInfo;

    @com.android.internal.annotations.VisibleForTesting
    final android.hardware.biometrics.PromptInfo mPromptInfo;
    private final java.util.Random mRandom;
    private final long mRequestId;

    @com.android.internal.annotations.VisibleForTesting
    final android.hardware.biometrics.IBiometricSensorReceiver mSensorReceiver;
    private int[] mSensors;
    private long mStartTimeMs;
    private int mState;
    private final com.android.internal.statusbar.IStatusBarService mStatusBarService;

    @com.android.internal.annotations.VisibleForTesting
    final android.hardware.biometrics.IBiometricSysuiReceiver mSysuiReceiver;

    @com.android.internal.annotations.VisibleForTesting
    final android.os.IBinder mToken;
    private byte[] mTokenEscrow;
    private final int mUserId;
    private int mVendorCodeEscrow;

    interface ClientDeathReceiver {
        void onClientDied();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface SessionState {
    }

    AuthSession(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull com.android.internal.statusbar.IStatusBarService iStatusBarService, @android.annotation.NonNull android.hardware.biometrics.IBiometricSysuiReceiver iBiometricSysuiReceiver, @android.annotation.NonNull android.security.KeyStore keyStore, @android.annotation.NonNull java.util.Random random, @android.annotation.NonNull com.android.server.biometrics.AuthSession.ClientDeathReceiver clientDeathReceiver, @android.annotation.NonNull com.android.server.biometrics.PreAuthInfo preAuthInfo, @android.annotation.NonNull android.os.IBinder iBinder, long j, long j2, int i, @android.annotation.NonNull android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, @android.annotation.NonNull android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.hardware.biometrics.PromptInfo promptInfo, boolean z, @android.annotation.NonNull java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> list) {
        this(context, biometricContext, iStatusBarService, iBiometricSysuiReceiver, keyStore, random, clientDeathReceiver, preAuthInfo, iBinder, j, j2, i, iBiometricSensorReceiver, iBiometricServiceReceiver, str, promptInfo, z, list, com.android.server.biometrics.log.BiometricFrameworkStatsLogger.getInstance());
    }

    @com.android.internal.annotations.VisibleForTesting
    AuthSession(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull com.android.internal.statusbar.IStatusBarService iStatusBarService, @android.annotation.NonNull android.hardware.biometrics.IBiometricSysuiReceiver iBiometricSysuiReceiver, @android.annotation.NonNull android.security.KeyStore keyStore, @android.annotation.NonNull java.util.Random random, @android.annotation.NonNull com.android.server.biometrics.AuthSession.ClientDeathReceiver clientDeathReceiver, @android.annotation.NonNull com.android.server.biometrics.PreAuthInfo preAuthInfo, @android.annotation.NonNull android.os.IBinder iBinder, long j, long j2, int i, @android.annotation.NonNull android.hardware.biometrics.IBiometricSensorReceiver iBiometricSensorReceiver, @android.annotation.NonNull android.hardware.biometrics.IBiometricServiceReceiver iBiometricServiceReceiver, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.hardware.biometrics.PromptInfo promptInfo, boolean z, @android.annotation.NonNull java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> list, @android.annotation.NonNull com.android.server.biometrics.log.BiometricFrameworkStatsLogger biometricFrameworkStatsLogger) {
        this.mState = 0;
        this.mAuthenticatedSensorId = -1;
        android.util.Slog.d(TAG, "Creating AuthSession with: " + preAuthInfo);
        this.mContext = context;
        this.mBiometricContext = biometricContext;
        this.mStatusBarService = iStatusBarService;
        this.mSysuiReceiver = iBiometricSysuiReceiver;
        this.mKeyStore = keyStore;
        this.mRandom = random;
        this.mClientDeathReceiver = clientDeathReceiver;
        this.mPreAuthInfo = preAuthInfo;
        this.mToken = iBinder;
        this.mRequestId = j;
        this.mOperationId = j2;
        this.mUserId = i;
        this.mSensorReceiver = iBiometricSensorReceiver;
        this.mClientReceiver = iBiometricServiceReceiver;
        this.mOpPackageName = str;
        this.mPromptInfo = promptInfo;
        this.mDebugEnabled = z;
        this.mFingerprintSensorProperties = list;
        this.mCancelled = false;
        this.mBiometricFrameworkStatsLogger = biometricFrameworkStatsLogger;
        this.mOperationContext = new com.android.server.biometrics.log.OperationContextExt(true);
        try {
            this.mClientReceiver.asBinder().linkToDeath(this, 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Unable to link to death");
        }
        setSensorsToStateUnknown();
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        android.util.Slog.e(TAG, "Binder died, session: " + this);
        this.mClientDeathReceiver.onClientDied();
    }

    private int getEligibleModalities() {
        return this.mPreAuthInfo.getEligibleModalities();
    }

    private void setSensorsToStateUnknown() {
        for (com.android.server.biometrics.BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            android.util.Slog.v(TAG, "set to unknown state sensor: " + biometricSensor.id);
            biometricSensor.goToStateUnknown();
        }
    }

    private void setSensorsToStateWaitingForCookie(boolean z) throws android.os.RemoteException {
        for (com.android.server.biometrics.BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            int sensorState = biometricSensor.getSensorState();
            if (z && sensorState != 5 && sensorState != 4) {
                android.util.Slog.d(TAG, "Skip retry because sensor: " + biometricSensor.id + " is: " + sensorState);
            } else {
                int nextInt = this.mRandom.nextInt(2147483646) + 1;
                boolean isConfirmationRequired = isConfirmationRequired(biometricSensor);
                android.util.Slog.v(TAG, "waiting for cooking for sensor: " + biometricSensor.id);
                biometricSensor.goToStateWaitingForCookie(isConfirmationRequired, this.mToken, this.mOperationId, this.mUserId, this.mSensorReceiver, this.mOpPackageName, this.mRequestId, nextInt, this.mPromptInfo.isAllowBackgroundAuthentication(), this.mPromptInfo.isForLegacyFingerprintManager());
            }
        }
    }

    void goToInitialState() throws android.os.RemoteException {
        if (this.mPreAuthInfo.credentialAvailable && this.mPreAuthInfo.eligibleSensors.isEmpty()) {
            this.mState = 9;
            this.mSensors = new int[0];
            this.mStatusBarService.showAuthenticationDialog(this.mPromptInfo, this.mSysuiReceiver, this.mSensors, true, false, this.mUserId, this.mOperationId, this.mOpPackageName, this.mRequestId);
        } else {
            if (!this.mPreAuthInfo.eligibleSensors.isEmpty()) {
                setSensorsToStateWaitingForCookie(false);
                this.mState = 1;
                return;
            }
            throw new java.lang.IllegalStateException("No authenticators requested");
        }
    }

    void onCookieReceived(int i) {
        if (this.mCancelled) {
            android.util.Slog.w(TAG, "Received cookie but already cancelled (ignoring): " + i);
            return;
        }
        if (hasAuthenticated()) {
            android.util.Slog.d(TAG, "onCookieReceived after successful auth");
            return;
        }
        java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = this.mPreAuthInfo.eligibleSensors.iterator();
        while (it.hasNext()) {
            it.next().goToStateCookieReturnedIfCookieMatches(i);
        }
        if (allCookiesReceived()) {
            this.mStartTimeMs = java.lang.System.currentTimeMillis();
            startAllPreparedSensorsExceptFingerprint();
            if (this.mState != 5) {
                try {
                    boolean isConfirmationRequiredByAnyEligibleSensor = isConfirmationRequiredByAnyEligibleSensor();
                    this.mSensors = new int[this.mPreAuthInfo.eligibleSensors.size()];
                    for (int i2 = 0; i2 < this.mPreAuthInfo.eligibleSensors.size(); i2++) {
                        this.mSensors[i2] = this.mPreAuthInfo.eligibleSensors.get(i2).id;
                    }
                    this.mStatusBarService.showAuthenticationDialog(this.mPromptInfo, this.mSysuiReceiver, this.mSensors, this.mPreAuthInfo.shouldShowCredential(), isConfirmationRequiredByAnyEligibleSensor, this.mUserId, this.mOperationId, this.mOpPackageName, this.mRequestId);
                    this.mState = 2;
                    return;
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "Remote exception", e);
                    return;
                }
            }
            this.mState = 3;
            return;
        }
        android.util.Slog.v(TAG, "onCookieReceived: still waiting");
    }

    private boolean isConfirmationRequired(com.android.server.biometrics.BiometricSensor biometricSensor) {
        return biometricSensor.confirmationSupported() && (biometricSensor.confirmationAlwaysRequired(this.mUserId) || this.mPreAuthInfo.confirmationRequested);
    }

    private boolean isConfirmationRequiredByAnyEligibleSensor() {
        java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = this.mPreAuthInfo.eligibleSensors.iterator();
        while (it.hasNext()) {
            if (isConfirmationRequired(it.next())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$startAllPreparedSensorsExceptFingerprint$0(com.android.server.biometrics.BiometricSensor biometricSensor) {
        return java.lang.Boolean.valueOf(biometricSensor.modality != 2);
    }

    private void startAllPreparedSensorsExceptFingerprint() {
        startAllPreparedSensors(new java.util.function.Function() { // from class: com.android.server.biometrics.AuthSession$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$startAllPreparedSensorsExceptFingerprint$0;
                lambda$startAllPreparedSensorsExceptFingerprint$0 = com.android.server.biometrics.AuthSession.lambda$startAllPreparedSensorsExceptFingerprint$0((com.android.server.biometrics.BiometricSensor) obj);
                return lambda$startAllPreparedSensorsExceptFingerprint$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$startAllPreparedFingerprintSensors$1(com.android.server.biometrics.BiometricSensor biometricSensor) {
        return java.lang.Boolean.valueOf(biometricSensor.modality == 2);
    }

    private void startAllPreparedFingerprintSensors() {
        startAllPreparedSensors(new java.util.function.Function() { // from class: com.android.server.biometrics.AuthSession$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$startAllPreparedFingerprintSensors$1;
                lambda$startAllPreparedFingerprintSensors$1 = com.android.server.biometrics.AuthSession.lambda$startAllPreparedFingerprintSensors$1((com.android.server.biometrics.BiometricSensor) obj);
                return lambda$startAllPreparedFingerprintSensors$1;
            }
        });
    }

    private void startAllPreparedSensors(java.util.function.Function<com.android.server.biometrics.BiometricSensor, java.lang.Boolean> function) {
        for (com.android.server.biometrics.BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            if (function.apply(biometricSensor).booleanValue()) {
                try {
                    android.util.Slog.v(TAG, "Starting sensor: " + biometricSensor.id);
                    biometricSensor.startSensor();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "Unable to start prepared client, sensor: " + biometricSensor, e);
                }
            }
        }
    }

    private void cancelAllSensors() {
        cancelAllSensors(new java.util.function.Function() { // from class: com.android.server.biometrics.AuthSession$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$cancelAllSensors$2;
                lambda$cancelAllSensors$2 = com.android.server.biometrics.AuthSession.lambda$cancelAllSensors$2((com.android.server.biometrics.BiometricSensor) obj);
                return lambda$cancelAllSensors$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$cancelAllSensors$2(com.android.server.biometrics.BiometricSensor biometricSensor) {
        return true;
    }

    private void cancelAllSensors(java.util.function.Function<com.android.server.biometrics.BiometricSensor, java.lang.Boolean> function) {
        for (com.android.server.biometrics.BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            try {
                if (function.apply(biometricSensor).booleanValue()) {
                    android.util.Slog.d(TAG, "Cancelling sensorId: " + biometricSensor.id);
                    biometricSensor.goToStateCancelling(this.mToken, this.mOpPackageName, this.mRequestId);
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Unable to cancel authentication");
            }
        }
    }

    boolean onErrorReceived(final int i, int i2, int i3, int i4) throws android.os.RemoteException {
        android.util.Slog.d(TAG, "onErrorReceived sensor: " + i + " error: " + i3);
        if (!containsCookie(i2)) {
            android.util.Slog.e(TAG, "Unknown/expired cookie: " + i2);
            return false;
        }
        for (com.android.server.biometrics.BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            if (biometricSensor.getSensorState() == 3) {
                biometricSensor.goToStoppedStateIfCookieMatches(i2, i3);
            }
        }
        if (hasAuthenticated()) {
            android.util.Slog.d(TAG, "onErrorReceived after successful auth (ignoring)");
            return false;
        }
        boolean z = i3 == 7 || i3 == 9;
        if (z) {
            cancelAllSensors(new java.util.function.Function() { // from class: com.android.server.biometrics.AuthSession$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.Boolean lambda$onErrorReceived$3;
                    lambda$onErrorReceived$3 = com.android.server.biometrics.AuthSession.this.lambda$onErrorReceived$3(i, (com.android.server.biometrics.BiometricSensor) obj);
                    return lambda$onErrorReceived$3;
                }
            });
        }
        this.mErrorEscrow = i3;
        this.mVendorCodeEscrow = i4;
        int sensorIdToModality = sensorIdToModality(i);
        switch (this.mState) {
            case 1:
                if (isAllowDeviceCredential()) {
                    this.mPromptInfo.setAuthenticators(com.android.server.biometrics.Utils.removeBiometricBits(this.mPromptInfo.getAuthenticators()));
                    this.mState = 9;
                    this.mSensors = new int[0];
                    this.mStatusBarService.showAuthenticationDialog(this.mPromptInfo, this.mSysuiReceiver, this.mSensors, true, false, this.mUserId, this.mOperationId, this.mOpPackageName, this.mRequestId);
                    return false;
                }
                this.mClientReceiver.onError(sensorIdToModality, i3, i4);
                return true;
            case 2:
            case 3:
                if (isAllowDeviceCredential() && z) {
                    this.mState = 9;
                    this.mStatusBarService.onBiometricError(sensorIdToModality, i3, i4);
                } else {
                    if (i3 == 5) {
                        this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
                        this.mClientReceiver.onError(sensorIdToModality, i3, i4);
                        return true;
                    }
                    this.mState = 8;
                    this.mStatusBarService.onBiometricError(sensorIdToModality, i3, i4);
                }
                return false;
            case 4:
                this.mClientReceiver.onError(sensorIdToModality, i3, i4);
                this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
                return true;
            case 5:
            case 6:
            case 7:
            case 8:
            default:
                android.util.Slog.e(TAG, "Unhandled error state, mState: " + this.mState);
                return false;
            case 9:
                android.util.Slog.d(TAG, "Biometric canceled, ignoring from state: " + this.mState);
                return false;
            case 10:
                this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
                return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$onErrorReceived$3(int i, com.android.server.biometrics.BiometricSensor biometricSensor) {
        return java.lang.Boolean.valueOf(com.android.server.biometrics.Utils.isAtLeastStrength(sensorIdToStrength(i), biometricSensor.getCurrentStrength()));
    }

    void onAcquired(int i, int i2, int i3) {
        if (hasAuthenticated()) {
            android.util.Slog.d(TAG, "onAcquired after successful auth");
            return;
        }
        java.lang.String acquiredMessageForSensor = getAcquiredMessageForSensor(i, i2, i3);
        android.util.Slog.d(TAG, "sensorId: " + i + " acquiredInfo: " + i2 + " message: " + acquiredMessageForSensor);
        if (acquiredMessageForSensor == null) {
            return;
        }
        try {
            this.mStatusBarService.onBiometricHelp(sensorIdToModality(i), acquiredMessageForSensor);
            if (i2 == 6) {
                i2 = i3 + 1000;
            }
            this.mClientReceiver.onAcquired(i2, acquiredMessageForSensor);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
        }
    }

    void onSystemEvent(int i) {
        if (hasAuthenticated()) {
            android.util.Slog.d(TAG, "onSystemEvent after successful auth");
        } else {
            if (!this.mPromptInfo.isReceiveSystemEvents()) {
                return;
            }
            try {
                this.mClientReceiver.onSystemEvent(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "RemoteException", e);
            }
        }
    }

    void onDialogAnimatedIn(boolean z) {
        if (this.mState != 2 && this.mState != 8) {
            android.util.Slog.e(TAG, "onDialogAnimatedIn, unexpected state: " + this.mState);
            return;
        }
        this.mState = 3;
        if (z) {
            startAllPreparedFingerprintSensors();
        } else {
            android.util.Slog.d(TAG, "delaying fingerprint sensor start");
        }
        this.mBiometricContext.updateContext(this.mOperationContext, isCrypto());
    }

    void onStartFingerprint() {
        if (this.mState != 2 && this.mState != 3 && this.mState != 4 && this.mState != 8) {
            android.util.Slog.w(TAG, "onStartFingerprint, started from unexpected state: " + this.mState);
        }
        startAllPreparedFingerprintSensors();
    }

    void onTryAgainPressed() {
        if (hasAuthenticated()) {
            android.util.Slog.d(TAG, "onTryAgainPressed after successful auth");
            return;
        }
        if (this.mState != 4) {
            android.util.Slog.w(TAG, "onTryAgainPressed, state: " + this.mState);
        }
        try {
            setSensorsToStateWaitingForCookie(true);
            this.mState = 5;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "RemoteException: " + e);
        }
    }

    void onAuthenticationSucceeded(final int i, boolean z, byte[] bArr) {
        if (hasAuthenticated()) {
            android.util.Slog.d(TAG, "onAuthenticationSucceeded after successful auth");
            return;
        }
        this.mAuthenticatedSensorId = i;
        if (z) {
            this.mTokenEscrow = bArr;
        } else if (bArr != null) {
            android.util.Slog.w(TAG, "Dropping authToken for non-strong biometric, id: " + i);
        }
        try {
            this.mStatusBarService.onBiometricAuthenticated(sensorIdToModality(i));
            if (!isConfirmationRequiredByAnyEligibleSensor()) {
                this.mState = 7;
            } else {
                this.mAuthenticatedTimeMs = java.lang.System.currentTimeMillis();
                this.mState = 6;
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "RemoteException", e);
        }
        cancelAllSensors(new java.util.function.Function() { // from class: com.android.server.biometrics.AuthSession$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$onAuthenticationSucceeded$4;
                lambda$onAuthenticationSucceeded$4 = com.android.server.biometrics.AuthSession.lambda$onAuthenticationSucceeded$4(i, (com.android.server.biometrics.BiometricSensor) obj);
                return lambda$onAuthenticationSucceeded$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$onAuthenticationSucceeded$4(int i, com.android.server.biometrics.BiometricSensor biometricSensor) {
        return java.lang.Boolean.valueOf(biometricSensor.id != i);
    }

    void onAuthenticationRejected(int i) {
        if (hasAuthenticated()) {
            android.util.Slog.d(TAG, "onAuthenticationRejected after successful auth");
            return;
        }
        try {
            this.mStatusBarService.onBiometricError(sensorIdToModality(i), 100, 0);
            if (pauseSensorIfSupported(i)) {
                this.mState = 4;
            }
            this.mClientReceiver.onAuthenticationFailed();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "RemoteException", e);
        }
    }

    void onAuthenticationTimedOut(int i, int i2, int i3, int i4) {
        if (hasAuthenticated()) {
            android.util.Slog.d(TAG, "onAuthenticationTimedOut after successful auth");
            return;
        }
        try {
            this.mStatusBarService.onBiometricError(sensorIdToModality(i), i3, i4);
            pauseSensorIfSupported(i);
            this.mState = 4;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "RemoteException", e);
        }
    }

    private boolean pauseSensorIfSupported(final int i) {
        boolean z = sensorIdToState(i) == 4;
        if (sensorIdToModality(i) != 8 || z) {
            return false;
        }
        cancelAllSensors(new java.util.function.Function() { // from class: com.android.server.biometrics.AuthSession$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Boolean lambda$pauseSensorIfSupported$5;
                lambda$pauseSensorIfSupported$5 = com.android.server.biometrics.AuthSession.lambda$pauseSensorIfSupported$5(i, (com.android.server.biometrics.BiometricSensor) obj);
                return lambda$pauseSensorIfSupported$5;
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Boolean lambda$pauseSensorIfSupported$5(int i, com.android.server.biometrics.BiometricSensor biometricSensor) {
        return java.lang.Boolean.valueOf(biometricSensor.id == i);
    }

    void onDeviceCredentialPressed() {
        if (hasAuthenticated()) {
            android.util.Slog.d(TAG, "onDeviceCredentialPressed after successful auth");
        } else {
            cancelAllSensors();
            this.mState = 9;
        }
    }

    boolean onClientDied() {
        try {
            switch (this.mState) {
                case 2:
                case 3:
                    this.mState = 10;
                    cancelAllSensors();
                    break;
                default:
                    this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
                    break;
            }
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote Exception: " + e);
            return true;
        }
    }

    private boolean hasAuthenticated() {
        return this.mAuthenticatedSensorId != -1;
    }

    private void logOnDialogDismissed(int i) {
        int i2;
        if (i == 1) {
            long currentTimeMillis = java.lang.System.currentTimeMillis() - this.mAuthenticatedTimeMs;
            android.util.Slog.v(TAG, "Confirmed! Modality: " + statsModality() + ", User: " + this.mUserId + ", IsCrypto: " + isCrypto() + ", Client: " + getStatsClient() + ", RequireConfirmation: " + this.mPreAuthInfo.confirmationRequested + ", State: 3, Latency: " + currentTimeMillis + ", SessionId: " + this.mOperationContext.getId());
            this.mBiometricFrameworkStatsLogger.authenticate(this.mOperationContext, statsModality(), 0, getStatsClient(), this.mDebugEnabled, currentTimeMillis, 3, this.mPreAuthInfo.confirmationRequested, this.mUserId, -1.0f);
            return;
        }
        long currentTimeMillis2 = java.lang.System.currentTimeMillis() - this.mStartTimeMs;
        switch (i) {
            case 2:
                i2 = 13;
                break;
            case 3:
                i2 = 10;
                break;
            default:
                i2 = 0;
                break;
        }
        android.util.Slog.v(TAG, "Dismissed! Modality: " + statsModality() + ", User: " + this.mUserId + ", IsCrypto: " + isCrypto() + ", Action: 2, Client: " + getStatsClient() + ", Reason: " + i + ", Error: " + i2 + ", Latency: " + currentTimeMillis2 + ", SessionId: " + this.mOperationContext.getId());
        if (i2 != 0) {
            this.mBiometricFrameworkStatsLogger.error(this.mOperationContext, statsModality(), 2, getStatsClient(), this.mDebugEnabled, currentTimeMillis2, i2, 0, this.mUserId);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0055 A[Catch: all -> 0x001d, RemoteException -> 0x0020, TryCatch #0 {RemoteException -> 0x0020, blocks: (B:4:0x0008, B:9:0x0024, B:10:0x002a, B:11:0x0030, B:12:0x003e, B:13:0x004b, B:14:0x0051, B:16:0x0055, B:17:0x0078, B:18:0x0072), top: B:2:0x0005, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0072 A[Catch: all -> 0x001d, RemoteException -> 0x0020, TryCatch #0 {RemoteException -> 0x0020, blocks: (B:4:0x0008, B:9:0x0024, B:10:0x002a, B:11:0x0030, B:12:0x003e, B:13:0x004b, B:14:0x0051, B:16:0x0055, B:17:0x0078, B:18:0x0072), top: B:2:0x0005, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void onDialogDismissed(int i, @android.annotation.Nullable byte[] bArr) {
        logOnDialogDismissed(i);
        try {
            try {
                switch (i) {
                    case 1:
                    case 4:
                        if (this.mTokenEscrow != null) {
                            android.util.Slog.e(TAG, "mTokenEscrow is null");
                        } else {
                            android.util.Slog.d(TAG, "addAuthToken: " + this.mKeyStore.addAuthToken(this.mTokenEscrow));
                        }
                        this.mClientReceiver.onAuthenticationSucceeded(com.android.server.biometrics.Utils.getAuthenticationTypeForResult(i));
                        break;
                    case 2:
                        this.mClientReceiver.onDialogDismissed(i);
                        break;
                    case 3:
                        this.mClientReceiver.onError(getEligibleModalities(), 10, 0);
                        break;
                    case 5:
                    case 6:
                        this.mClientReceiver.onError(getEligibleModalities(), this.mErrorEscrow, this.mVendorCodeEscrow);
                        break;
                    case 7:
                        if (bArr == null) {
                            android.util.Slog.e(TAG, "credentialAttestation is null");
                        } else {
                            this.mKeyStore.addAuthToken(bArr);
                        }
                        if (this.mTokenEscrow != null) {
                        }
                        this.mClientReceiver.onAuthenticationSucceeded(com.android.server.biometrics.Utils.getAuthenticationTypeForResult(i));
                        break;
                    default:
                        android.util.Slog.w(TAG, "Unhandled reason: " + i);
                        break;
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception", e);
            }
            cancelAllSensors();
        } catch (java.lang.Throwable th) {
            cancelAllSensors();
            throw th;
        }
    }

    boolean onCancelAuthSession(boolean z) {
        if (hasAuthenticated()) {
            android.util.Slog.d(TAG, "onCancelAuthSession after successful auth");
            return true;
        }
        this.mCancelled = true;
        boolean z2 = this.mState == 1 || this.mState == 2 || this.mState == 3;
        cancelAllSensors();
        if (z2 && !z) {
            return false;
        }
        try {
            this.mClientReceiver.onError(getEligibleModalities(), 5, 0);
            this.mStatusBarService.hideAuthenticationDialog(this.mRequestId);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception", e);
            return false;
        }
    }

    boolean isCrypto() {
        return this.mOperationId != 0;
    }

    private boolean containsCookie(int i) {
        java.util.Iterator<com.android.server.biometrics.BiometricSensor> it = this.mPreAuthInfo.eligibleSensors.iterator();
        while (it.hasNext()) {
            if (it.next().getCookie() == i) {
                return true;
            }
        }
        return false;
    }

    private boolean isAllowDeviceCredential() {
        return com.android.server.biometrics.Utils.isCredentialRequested(this.mPromptInfo);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean allCookiesReceived() {
        int numSensorsWaitingForCookie = this.mPreAuthInfo.numSensorsWaitingForCookie();
        android.util.Slog.d(TAG, "Remaining cookies: " + numSensorsWaitingForCookie);
        return numSensorsWaitingForCookie == 0;
    }

    int getState() {
        return this.mState;
    }

    long getRequestId() {
        return this.mRequestId;
    }

    private int statsModality() {
        int i = 0;
        for (com.android.server.biometrics.BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            if ((biometricSensor.modality & 2) != 0) {
                i |= 1;
            }
            if ((biometricSensor.modality & 4) != 0) {
                i |= 2;
            }
            if ((biometricSensor.modality & 8) != 0) {
                i |= 4;
            }
        }
        return i;
    }

    private int sensorIdToModality(int i) {
        for (com.android.server.biometrics.BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            if (i == biometricSensor.id) {
                return biometricSensor.modality;
            }
        }
        android.util.Slog.e(TAG, "Unknown sensor: " + i);
        return 0;
    }

    private int sensorIdToState(int i) {
        for (com.android.server.biometrics.BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            if (i == biometricSensor.id) {
                return biometricSensor.getSensorState();
            }
        }
        android.util.Slog.e(TAG, "Unknown sensor: " + i);
        return 0;
    }

    private int sensorIdToStrength(int i) {
        for (com.android.server.biometrics.BiometricSensor biometricSensor : this.mPreAuthInfo.eligibleSensors) {
            if (i == biometricSensor.id) {
                return biometricSensor.getCurrentStrength();
            }
        }
        android.util.Slog.e(TAG, "Unknown sensor: " + i);
        return 4095;
    }

    private java.lang.String getAcquiredMessageForSensor(int i, int i2, int i3) {
        switch (sensorIdToModality(i)) {
            case 2:
                return android.hardware.fingerprint.FingerprintManager.getAcquiredString(this.mContext, i2, i3);
            case 8:
                return android.hardware.face.FaceManager.getAuthHelpMessage(this.mContext, i2, i3);
            default:
                return null;
        }
    }

    private int getStatsClient() {
        if (this.mPromptInfo.isForLegacyFingerprintManager()) {
            return 3;
        }
        return 2;
    }

    public java.lang.String toString() {
        return "State: " + this.mState + ", cancelled: " + this.mCancelled + ", isCrypto: " + isCrypto() + ", PreAuthInfo: " + this.mPreAuthInfo + ", requestId: " + this.mRequestId;
    }
}
