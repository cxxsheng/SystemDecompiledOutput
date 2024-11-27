package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
public class AidlResponseHandler extends android.hardware.biometrics.fingerprint.ISessionCallback.Stub {
    private static final java.lang.String TAG = "AidlResponseHandler";

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback mAidlResponseHandlerCallback;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthSessionCoordinator mAuthSessionCoordinator;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.HardwareUnavailableCallback mHardwareUnavailableCallback;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.LockoutResetDispatcher mLockoutResetDispatcher;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.LockoutTracker mLockoutTracker;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.BiometricScheduler mScheduler;
    private final int mSensorId;
    private final int mUserId;

    public interface AidlResponseHandlerCallback {
        void onEnrollSuccess();

        void onHardwareUnavailable();
    }

    public interface HardwareUnavailableCallback {
        void onHardwareUnavailable();
    }

    public AidlResponseHandler(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricScheduler biometricScheduler, int i, int i2, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutTracker lockoutTracker, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthSessionCoordinator authSessionCoordinator, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.HardwareUnavailableCallback hardwareUnavailableCallback) {
        this(context, biometricScheduler, i, i2, lockoutTracker, lockoutResetDispatcher, authSessionCoordinator, hardwareUnavailableCallback, new com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.1
            @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback
            public void onEnrollSuccess() {
            }

            @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback
            public void onHardwareUnavailable() {
            }
        });
    }

    public AidlResponseHandler(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricScheduler biometricScheduler, int i, int i2, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutTracker lockoutTracker, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthSessionCoordinator authSessionCoordinator, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.HardwareUnavailableCallback hardwareUnavailableCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback aidlResponseHandlerCallback) {
        this.mContext = context;
        this.mScheduler = biometricScheduler;
        this.mSensorId = i;
        this.mUserId = i2;
        this.mLockoutTracker = lockoutTracker;
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mAuthSessionCoordinator = authSessionCoordinator;
        this.mHardwareUnavailableCallback = hardwareUnavailableCallback;
        this.mAidlResponseHandlerCallback = aidlResponseHandlerCallback;
    }

    public int getInterfaceVersion() {
        return 3;
    }

    public java.lang.String getInterfaceHash() {
        return "637371b53fb7faf9bd43aa51b72c23852d6e6d96";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onChallengeGenerated$0(long j, com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintGenerateChallengeClient fingerprintGenerateChallengeClient) {
        fingerprintGenerateChallengeClient.onChallengeGenerated(this.mSensorId, this.mUserId, j);
    }

    public void onChallengeGenerated(final long j) {
        handleResponse(com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintGenerateChallengeClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.this.lambda$onChallengeGenerated$0(j, (com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintGenerateChallengeClient) obj);
            }
        });
    }

    public void onChallengeRevoked(final long j) {
        handleResponse(com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintRevokeChallengeClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintRevokeChallengeClient) obj).onChallengeRevoked(j);
            }
        });
    }

    public void onAcquired(final int i, final int i2) {
        handleResponse(com.android.server.biometrics.sensors.AcquisitionClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.AcquisitionClient) obj).onAcquired(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onAcquired$3(byte b, int i, com.android.server.biometrics.sensors.AcquisitionClient acquisitionClient) {
        acquisitionClient.onAcquired(com.android.server.biometrics.sensors.fingerprint.aidl.AidlConversionUtils.toFrameworkAcquiredInfo(b), i);
    }

    public void onAcquired(final byte b, final int i) {
        handleResponse(com.android.server.biometrics.sensors.AcquisitionClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.lambda$onAcquired$3(b, i, (com.android.server.biometrics.sensors.AcquisitionClient) obj);
            }
        });
    }

    public void onError(final int i, final int i2) {
        handleResponse(com.android.server.biometrics.sensors.ErrorConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.this.lambda$onError$4(i, i2, (com.android.server.biometrics.sensors.ErrorConsumer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onError$4(int i, int i2, com.android.server.biometrics.sensors.ErrorConsumer errorConsumer) {
        errorConsumer.onError(i, i2);
        if (i == 1) {
            com.android.server.biometrics.Flags.deHidl();
            this.mHardwareUnavailableCallback.onHardwareUnavailable();
        }
    }

    public void onError(byte b, int i) {
        onError(com.android.server.biometrics.sensors.fingerprint.aidl.AidlConversionUtils.toFrameworkError(b), i);
    }

    public void onEnrollmentProgress(int i, final int i2) {
        com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient == null) {
            return;
        }
        int targetUserId = currentClient.getTargetUserId();
        final android.hardware.fingerprint.Fingerprint fingerprint = new android.hardware.fingerprint.Fingerprint(com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(this.mSensorId).getUniqueName(this.mContext, targetUserId), targetUserId, i, this.mSensorId);
        handleResponse(com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.this.lambda$onEnrollmentProgress$5(fingerprint, i2, (com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEnrollmentProgress$5(android.hardware.fingerprint.Fingerprint fingerprint, int i, com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient fingerprintEnrollClient) {
        fingerprintEnrollClient.onEnrollResult(fingerprint, i);
        if (i == 0) {
            this.mAidlResponseHandlerCallback.onEnrollSuccess();
        }
    }

    public void onAuthenticationSucceeded(int i, android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) {
        final android.hardware.fingerprint.Fingerprint fingerprint = new android.hardware.fingerprint.Fingerprint("", i, this.mSensorId);
        byte[] byteArray = com.android.server.biometrics.HardwareAuthTokenUtils.toByteArray(hardwareAuthToken);
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        for (byte b : byteArray) {
            arrayList.add(java.lang.Byte.valueOf(b));
        }
        handleResponse(com.android.server.biometrics.sensors.AuthenticationConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.AuthenticationConsumer) obj).onAuthenticated(fingerprint, true, arrayList);
            }
        }, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.this.lambda$onAuthenticationSucceeded$7((com.android.server.biometrics.sensors.BaseClientMonitor) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAuthenticationSucceeded$7(com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        onInteractionDetected();
    }

    public void onAuthenticationFailed() {
        final android.hardware.fingerprint.Fingerprint fingerprint = new android.hardware.fingerprint.Fingerprint("", 0, this.mSensorId);
        handleResponse(com.android.server.biometrics.sensors.AuthenticationConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.AuthenticationConsumer) obj).onAuthenticated(fingerprint, false, null);
            }
        }, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.this.lambda$onAuthenticationFailed$9((com.android.server.biometrics.sensors.BaseClientMonitor) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAuthenticationFailed$9(com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        onInteractionDetected();
    }

    public void onLockoutTimed(final long j) {
        handleResponse(com.android.server.biometrics.sensors.LockoutConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.LockoutConsumer) obj).onLockoutTimed(j);
            }
        });
    }

    public void onLockoutPermanent() {
        handleResponse(com.android.server.biometrics.sensors.LockoutConsumer.class, new com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda11());
    }

    public void onLockoutCleared() {
        handleResponse(com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintResetLockoutClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintResetLockoutClient) obj).onLockoutCleared();
            }
        }, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.this.lambda$onLockoutCleared$11((com.android.server.biometrics.sensors.BaseClientMonitor) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLockoutCleared$11(com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintResetLockoutClient.resetLocalLockoutStateToNone(this.mSensorId, this.mUserId, this.mLockoutTracker, this.mLockoutResetDispatcher, this.mAuthSessionCoordinator, com.android.server.biometrics.Utils.getCurrentStrength(this.mSensorId), -1L);
    }

    public void onInteractionDetected() {
        handleResponse(com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintDetectClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintDetectClient) obj).onInteractionDetected();
            }
        });
    }

    public void onEnrollmentsEnumerated(int[] iArr) {
        if (iArr.length > 0) {
            for (int i = 0; i < iArr.length; i++) {
                onEnrollmentEnumerated(iArr[i], (iArr.length - i) - 1);
            }
            return;
        }
        handleResponse(com.android.server.biometrics.sensors.EnumerateConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.EnumerateConsumer) obj).onEnumerationResult(null, 0);
            }
        });
    }

    public void onEnrollmentEnumerated(int i, final int i2) {
        final android.hardware.fingerprint.Fingerprint fingerprint = new android.hardware.fingerprint.Fingerprint("", i, this.mSensorId);
        handleResponse(com.android.server.biometrics.sensors.EnumerateConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.EnumerateConsumer) obj).onEnumerationResult(fingerprint, i2);
            }
        });
    }

    public void onEnrollmentRemoved(int i, final int i2) {
        final android.hardware.fingerprint.Fingerprint fingerprint = new android.hardware.fingerprint.Fingerprint("", i, this.mSensorId);
        handleResponse(com.android.server.biometrics.sensors.RemovalConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.RemovalConsumer) obj).onRemoved(fingerprint, i2);
            }
        });
    }

    public void onEnrollmentsRemoved(int[] iArr) {
        if (iArr.length > 0) {
            for (int i = 0; i < iArr.length; i++) {
                onEnrollmentRemoved(iArr[i], (iArr.length - i) - 1);
            }
            return;
        }
        handleResponse(com.android.server.biometrics.sensors.RemovalConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.RemovalConsumer) obj).onRemoved(null, 0);
            }
        });
    }

    public void onAuthenticatorIdRetrieved(final long j) {
        handleResponse(com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintGetAuthenticatorIdClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintGetAuthenticatorIdClient) obj).onAuthenticatorIdRetrieved(j);
            }
        });
    }

    public void onAuthenticatorIdInvalidated(final long j) {
        handleResponse(com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintInvalidationClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintInvalidationClient) obj).onAuthenticatorIdInvalidated(j);
            }
        });
    }

    public <T extends com.android.server.biometrics.sensors.BaseClientMonitor> void onUnsupportedClientScheduled(java.lang.Class<T> cls) {
        android.util.Slog.e(TAG, cls + " is not supported in the HAL.");
        handleResponse(cls, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.BaseClientMonitor) obj).cancel();
            }
        });
    }

    private <T> void handleResponse(@android.annotation.NonNull java.lang.Class<T> cls, @android.annotation.NonNull java.util.function.Consumer<T> consumer) {
        handleResponse(cls, consumer, null);
    }

    private <T> void handleResponse(@android.annotation.NonNull final java.lang.Class<T> cls, @android.annotation.NonNull final java.util.function.Consumer<T> consumer, @android.annotation.Nullable final java.util.function.Consumer<com.android.server.biometrics.sensors.BaseClientMonitor> consumer2) {
        this.mScheduler.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.this.lambda$handleResponse$19(cls, consumer, consumer2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleResponse$19(java.lang.Class cls, java.util.function.Consumer consumer, java.util.function.Consumer consumer2) {
        com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (cls.isInstance(currentClient)) {
            consumer.accept(currentClient);
            return;
        }
        android.util.Slog.e(TAG, "Client monitor is not an instance of " + cls.getName());
        if (consumer2 != null) {
            consumer2.accept(currentClient);
        }
    }

    public void onSessionClosed() {
        android.os.Handler handler = this.mScheduler.getHandler();
        com.android.server.biometrics.sensors.BiometricScheduler biometricScheduler = this.mScheduler;
        java.util.Objects.requireNonNull(biometricScheduler);
        handler.post(new com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda24(biometricScheduler));
    }
}
