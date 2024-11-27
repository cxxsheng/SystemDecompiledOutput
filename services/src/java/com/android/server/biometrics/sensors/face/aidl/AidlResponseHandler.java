package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class AidlResponseHandler extends android.hardware.biometrics.face.ISessionCallback.Stub {
    private static final java.lang.String TAG = "AidlResponseHandler";

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback mAidlResponseHandlerCallback;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthSessionCoordinator mAuthSessionCoordinator;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.HardwareUnavailableCallback mHardwareUnavailableCallback;

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

    public AidlResponseHandler(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricScheduler biometricScheduler, int i, int i2, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutTracker lockoutTracker, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthSessionCoordinator authSessionCoordinator, @android.annotation.NonNull com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.HardwareUnavailableCallback hardwareUnavailableCallback) {
        this(context, biometricScheduler, i, i2, lockoutTracker, lockoutResetDispatcher, authSessionCoordinator, hardwareUnavailableCallback, new com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.1
            @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback
            public void onEnrollSuccess() {
            }

            @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback
            public void onHardwareUnavailable() {
            }
        });
    }

    public AidlResponseHandler(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricScheduler biometricScheduler, int i, int i2, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutTracker lockoutTracker, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthSessionCoordinator authSessionCoordinator, @android.annotation.NonNull com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.HardwareUnavailableCallback hardwareUnavailableCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback aidlResponseHandlerCallback) {
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
        return 4;
    }

    public java.lang.String getInterfaceHash() {
        return "c43fbb9be4a662cc9ace640dba21cccdb84c6c21";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onChallengeGenerated$0(long j, com.android.server.biometrics.sensors.face.aidl.FaceGenerateChallengeClient faceGenerateChallengeClient) {
        faceGenerateChallengeClient.onChallengeGenerated(this.mSensorId, this.mUserId, j);
    }

    public void onChallengeGenerated(final long j) {
        handleResponse(com.android.server.biometrics.sensors.face.aidl.FaceGenerateChallengeClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.this.lambda$onChallengeGenerated$0(j, (com.android.server.biometrics.sensors.face.aidl.FaceGenerateChallengeClient) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onChallengeRevoked$1(long j, com.android.server.biometrics.sensors.face.aidl.FaceRevokeChallengeClient faceRevokeChallengeClient) {
        faceRevokeChallengeClient.onChallengeRevoked(this.mSensorId, this.mUserId, j);
    }

    public void onChallengeRevoked(final long j) {
        handleResponse(com.android.server.biometrics.sensors.face.aidl.FaceRevokeChallengeClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.this.lambda$onChallengeRevoked$1(j, (com.android.server.biometrics.sensors.face.aidl.FaceRevokeChallengeClient) obj);
            }
        });
    }

    public void onAuthenticationFrame(final android.hardware.biometrics.face.AuthenticationFrame authenticationFrame) {
        handleResponse(com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.lambda$onAuthenticationFrame$2(authenticationFrame, (com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onAuthenticationFrame$2(android.hardware.biometrics.face.AuthenticationFrame authenticationFrame, com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient faceAuthenticationClient) {
        if (authenticationFrame == null) {
            android.util.Slog.e(TAG, "Received null enrollment frame for face authentication client.");
        } else {
            faceAuthenticationClient.onAuthenticationFrame(com.android.server.biometrics.sensors.face.aidl.AidlConversionUtils.toFrameworkAuthenticationFrame(authenticationFrame));
        }
    }

    public void onEnrollmentFrame(final android.hardware.biometrics.face.EnrollmentFrame enrollmentFrame) {
        handleResponse(com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.lambda$onEnrollmentFrame$3(enrollmentFrame, (com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onEnrollmentFrame$3(android.hardware.biometrics.face.EnrollmentFrame enrollmentFrame, com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient faceEnrollClient) {
        if (enrollmentFrame == null) {
            android.util.Slog.e(TAG, "Received null enrollment frame for face enroll client.");
        } else {
            faceEnrollClient.onEnrollmentFrame(com.android.server.biometrics.sensors.face.aidl.AidlConversionUtils.toFrameworkEnrollmentFrame(enrollmentFrame));
        }
    }

    public void onError(byte b, int i) {
        onError(com.android.server.biometrics.sensors.face.aidl.AidlConversionUtils.toFrameworkError(b), i);
    }

    public void onError(final int i, final int i2) {
        handleResponse(com.android.server.biometrics.sensors.ErrorConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda25
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.this.lambda$onError$4(i, i2, (com.android.server.biometrics.sensors.ErrorConsumer) obj);
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

    public void onEnrollmentProgress(int i, final int i2) {
        com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient == null) {
            return;
        }
        final android.hardware.face.Face face = new android.hardware.face.Face(com.android.server.biometrics.sensors.face.FaceUtils.getInstance(this.mSensorId).getUniqueName(this.mContext, currentClient.getTargetUserId()), i, this.mSensorId);
        handleResponse(com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.this.lambda$onEnrollmentProgress$5(face, i2, (com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onEnrollmentProgress$5(android.hardware.face.Face face, int i, com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient faceEnrollClient) {
        faceEnrollClient.onEnrollResult(face, i);
        if (i == 0) {
            this.mAidlResponseHandlerCallback.onEnrollSuccess();
        }
    }

    public void onAuthenticationSucceeded(int i, android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) {
        final android.hardware.face.Face face = new android.hardware.face.Face("", i, this.mSensorId);
        byte[] byteArray = com.android.server.biometrics.HardwareAuthTokenUtils.toByteArray(hardwareAuthToken);
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        for (byte b : byteArray) {
            arrayList.add(java.lang.Byte.valueOf(b));
        }
        handleResponse(com.android.server.biometrics.sensors.AuthenticationConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.AuthenticationConsumer) obj).onAuthenticated(face, true, arrayList);
            }
        });
    }

    public void onAuthenticationFailed() {
        final android.hardware.face.Face face = new android.hardware.face.Face("", 0, this.mSensorId);
        handleResponse(com.android.server.biometrics.sensors.AuthenticationConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.AuthenticationConsumer) obj).onAuthenticated(face, false, null);
            }
        });
    }

    public void onLockoutTimed(final long j) {
        handleResponse(com.android.server.biometrics.sensors.LockoutConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda5
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
        handleResponse(com.android.server.biometrics.sensors.face.aidl.FaceResetLockoutClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.face.aidl.FaceResetLockoutClient) obj).onLockoutCleared();
            }
        }, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.this.lambda$onLockoutCleared$9((com.android.server.biometrics.sensors.BaseClientMonitor) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLockoutCleared$9(com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        com.android.server.biometrics.sensors.face.aidl.FaceResetLockoutClient.resetLocalLockoutStateToNone(this.mSensorId, this.mUserId, this.mLockoutTracker, this.mLockoutResetDispatcher, this.mAuthSessionCoordinator, com.android.server.biometrics.Utils.getCurrentStrength(this.mSensorId), -1L);
    }

    public void onInteractionDetected() {
        handleResponse(com.android.server.biometrics.sensors.face.aidl.FaceDetectClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.face.aidl.FaceDetectClient) obj).onInteractionDetected();
            }
        });
    }

    public void onEnrollmentsEnumerated(final int[] iArr) {
        if (iArr.length > 0) {
            for (final int i = 0; i < iArr.length; i++) {
                final android.hardware.face.Face face = new android.hardware.face.Face("", iArr[i], this.mSensorId);
                handleResponse(com.android.server.biometrics.sensors.EnumerateConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda17
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.lambda$onEnrollmentsEnumerated$10(face, iArr, i, (com.android.server.biometrics.sensors.EnumerateConsumer) obj);
                    }
                });
            }
            return;
        }
        handleResponse(com.android.server.biometrics.sensors.EnumerateConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.EnumerateConsumer) obj).onEnumerationResult(null, 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onEnrollmentsEnumerated$10(android.hardware.face.Face face, int[] iArr, int i, com.android.server.biometrics.sensors.EnumerateConsumer enumerateConsumer) {
        enumerateConsumer.onEnumerationResult(face, (iArr.length - i) - 1);
    }

    public void onFeaturesRetrieved(final byte[] bArr) {
        handleResponse(com.android.server.biometrics.sensors.face.aidl.FaceGetFeatureClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.face.aidl.FaceGetFeatureClient) obj).onFeatureGet(true, bArr);
            }
        });
    }

    public void onFeatureSet(byte b) {
        handleResponse(com.android.server.biometrics.sensors.face.aidl.FaceSetFeatureClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.face.aidl.FaceSetFeatureClient) obj).onFeatureSet(true);
            }
        });
    }

    public void onEnrollmentsRemoved(final int[] iArr) {
        if (iArr.length > 0) {
            for (final int i = 0; i < iArr.length; i++) {
                final android.hardware.face.Face face = new android.hardware.face.Face("", iArr[i], this.mSensorId);
                handleResponse(com.android.server.biometrics.sensors.RemovalConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.lambda$onEnrollmentsRemoved$14(face, iArr, i, (com.android.server.biometrics.sensors.RemovalConsumer) obj);
                    }
                });
            }
            return;
        }
        handleResponse(com.android.server.biometrics.sensors.RemovalConsumer.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.RemovalConsumer) obj).onRemoved(null, 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onEnrollmentsRemoved$14(android.hardware.face.Face face, int[] iArr, int i, com.android.server.biometrics.sensors.RemovalConsumer removalConsumer) {
        removalConsumer.onRemoved(face, (iArr.length - i) - 1);
    }

    public void onAuthenticatorIdRetrieved(final long j) {
        handleResponse(com.android.server.biometrics.sensors.face.aidl.FaceGetAuthenticatorIdClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.face.aidl.FaceGetAuthenticatorIdClient) obj).onAuthenticatorIdRetrieved(j);
            }
        });
    }

    public void onAuthenticatorIdInvalidated(final long j) {
        handleResponse(com.android.server.biometrics.sensors.face.aidl.FaceInvalidationClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.face.aidl.FaceInvalidationClient) obj).onAuthenticatorIdInvalidated(j);
            }
        });
    }

    public void onAcquired(final int i, final int i2) {
        handleResponse(com.android.server.biometrics.sensors.AcquisitionClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.AcquisitionClient) obj).onAcquired(i, i2);
            }
        });
    }

    public void onLockoutChanged(final long j) {
        this.mScheduler.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.this.lambda$onLockoutChanged$19(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLockoutChanged$19(long j) {
        int i;
        if (j == 0) {
            i = 0;
        } else if (j == -1 || j == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            i = 2;
        } else {
            i = 1;
        }
        this.mLockoutTracker.setLockoutModeForUser(this.mUserId, i);
        if (j == 0) {
            this.mLockoutResetDispatcher.notifyLockoutResetCallbacks(this.mSensorId);
        }
    }

    public void onUnsupportedClientScheduled() {
        android.util.Slog.e(TAG, "FaceInvalidationClient is not supported in the HAL.");
        handleResponse(com.android.server.biometrics.sensors.face.aidl.FaceInvalidationClient.class, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda23
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.biometrics.sensors.face.aidl.FaceInvalidationClient) obj).cancel();
            }
        });
    }

    private <T> void handleResponse(@android.annotation.NonNull java.lang.Class<T> cls, @android.annotation.NonNull java.util.function.Consumer<T> consumer) {
        handleResponse(cls, consumer, null);
    }

    private <T> void handleResponse(@android.annotation.NonNull final java.lang.Class<T> cls, @android.annotation.NonNull final java.util.function.Consumer<T> consumer, @android.annotation.Nullable final java.util.function.Consumer<com.android.server.biometrics.sensors.BaseClientMonitor> consumer2) {
        this.mScheduler.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.this.lambda$handleResponse$20(cls, consumer, consumer2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleResponse$20(java.lang.Class cls, java.util.function.Consumer consumer, java.util.function.Consumer consumer2) {
        com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (cls.isInstance(currentClient)) {
            consumer.accept(currentClient);
            return;
        }
        android.util.Slog.d(TAG, "Current client is not an instance of " + cls.getName());
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
