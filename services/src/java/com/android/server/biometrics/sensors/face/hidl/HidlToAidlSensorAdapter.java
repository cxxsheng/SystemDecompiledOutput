package com.android.server.biometrics.sensors.face.hidl;

/* loaded from: classes.dex */
public class HidlToAidlSensorAdapter extends com.android.server.biometrics.sensors.face.aidl.Sensor implements android.os.IHwBinder.DeathRecipient {
    private static final java.lang.String TAG = "HidlToAidlSensorAdapter";
    private final com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback mAidlResponseHandlerCallback;
    private final com.android.server.biometrics.sensors.AuthSessionCoordinator mAuthSessionCoordinator;
    private int mCurrentUserId;
    private android.hardware.biometrics.face.V1_0.IBiometricsFace mDaemon;
    private final com.android.server.biometrics.sensors.face.aidl.FaceProvider mFaceProvider;
    private final java.lang.Runnable mInternalCleanupAndGetFeatureRunnable;
    private final com.android.server.biometrics.sensors.LockoutResetDispatcher mLockoutResetDispatcher;
    private com.android.server.biometrics.sensors.face.LockoutHalImpl mLockoutTracker;
    private com.android.server.biometrics.sensors.face.aidl.AidlSession mSession;
    private final com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback<com.android.server.biometrics.sensors.face.aidl.AidlSession> mUserStartedCallback;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, com.android.server.biometrics.sensors.face.aidl.AidlSession aidlSession, int i2) {
        if (i != this.mCurrentUserId) {
            handleUserChanged(i);
        }
    }

    public HidlToAidlSensorAdapter(@android.annotation.NonNull com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.hardware.biometrics.face.SensorProps sensorProps, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z, @android.annotation.NonNull java.lang.Runnable runnable) {
        this(faceProvider, context, handler, sensorProps, lockoutResetDispatcher, biometricContext, z, runnable, new com.android.server.biometrics.sensors.AuthSessionCoordinator(), null, null);
    }

    @com.android.internal.annotations.VisibleForTesting
    HidlToAidlSensorAdapter(@android.annotation.NonNull com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.hardware.biometrics.face.SensorProps sensorProps, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z, @android.annotation.NonNull java.lang.Runnable runnable, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthSessionCoordinator authSessionCoordinator, @android.annotation.Nullable android.hardware.biometrics.face.V1_0.IBiometricsFace iBiometricsFace, @android.annotation.Nullable com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback aidlResponseHandlerCallback) {
        super(faceProvider, context, handler, sensorProps, biometricContext, z);
        com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback aidlResponseHandlerCallback2;
        this.mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
        this.mUserStartedCallback = new com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda5
            @Override // com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback
            public final void onUserStarted(int i, java.lang.Object obj, int i2) {
                com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter.this.lambda$new$0(i, (com.android.server.biometrics.sensors.face.aidl.AidlSession) obj, i2);
            }
        };
        this.mInternalCleanupAndGetFeatureRunnable = runnable;
        this.mFaceProvider = faceProvider;
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mAuthSessionCoordinator = authSessionCoordinator;
        this.mDaemon = iBiometricsFace;
        if (aidlResponseHandlerCallback == null) {
            aidlResponseHandlerCallback2 = new com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter.1
                @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback
                public void onEnrollSuccess() {
                    com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter.this.scheduleFaceUpdateActiveUserClient(com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter.this.mCurrentUserId);
                }

                @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback
                public void onHardwareUnavailable() {
                    com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter.this.mDaemon = null;
                    com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter.this.mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
                }
            };
        } else {
            aidlResponseHandlerCallback2 = aidlResponseHandlerCallback;
        }
        this.mAidlResponseHandlerCallback = aidlResponseHandlerCallback2;
    }

    @Override // com.android.server.biometrics.sensors.face.aidl.Sensor
    public void scheduleFaceUpdateActiveUserClient(int i) {
        getScheduler().scheduleClientMonitor(getFaceUpdateActiveUserClient(i));
    }

    public void serviceDied(long j) {
        android.util.Slog.d(TAG, "Face HAL died.");
        this.mDaemon = null;
    }

    @Override // com.android.server.biometrics.sensors.face.aidl.Sensor
    public boolean isHardwareDetected(java.lang.String str) {
        return getIBiometricsFace() != null;
    }

    @Override // com.android.server.biometrics.sensors.face.aidl.Sensor
    public int getLockoutModeForUser(int i) {
        return this.mLockoutTracker.getLockoutModeForUser(i);
    }

    @Override // com.android.server.biometrics.sensors.face.aidl.Sensor
    public void init(@android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider) {
        setScheduler(new com.android.server.biometrics.sensors.BiometricScheduler(getHandler(), 1, (com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher) null, (java.util.function.Supplier<java.lang.Integer>) new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Integer lambda$init$1;
                lambda$init$1 = com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter.this.lambda$init$1();
                return lambda$init$1;
            }
        }, (com.android.server.biometrics.sensors.UserSwitchProvider) null));
        setLazySession(new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter.this.getSession();
            }
        });
        this.mLockoutTracker = new com.android.server.biometrics.sensors.face.LockoutHalImpl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$init$1() {
        return java.lang.Integer.valueOf(this.mCurrentUserId);
    }

    @Override // com.android.server.biometrics.sensors.face.aidl.Sensor
    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    protected com.android.server.biometrics.sensors.face.aidl.AidlSession getSessionForUser(int i) {
        if (this.mSession != null && this.mSession.getUserId() == i) {
            return this.mSession;
        }
        return null;
    }

    @Override // com.android.server.biometrics.sensors.face.aidl.Sensor
    protected com.android.server.biometrics.sensors.LockoutTracker getLockoutTracker(boolean z) {
        return this.mLockoutTracker;
    }

    @android.annotation.NonNull
    com.android.server.biometrics.sensors.face.aidl.AidlSession getSession() {
        if (this.mDaemon != null && this.mSession != null) {
            return this.mSession;
        }
        com.android.server.biometrics.sensors.face.aidl.AidlSession aidlSession = new com.android.server.biometrics.sensors.face.aidl.AidlSession(getContext(), new com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda4(this), this.mCurrentUserId, getAidlResponseHandler());
        this.mSession = aidlSession;
        return aidlSession;
    }

    private com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler getAidlResponseHandler() {
        return new com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler(getContext(), getScheduler(), getSensorProperties().sensorId, this.mCurrentUserId, this.mLockoutTracker, this.mLockoutResetDispatcher, this.mAuthSessionCoordinator, new com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.HardwareUnavailableCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda0
            @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.HardwareUnavailableCallback
            public final void onHardwareUnavailable() {
                com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter.lambda$getAidlResponseHandler$2();
            }
        }, this.mAidlResponseHandlerCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getAidlResponseHandler$2() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.biometrics.face.V1_0.IBiometricsFace getIBiometricsFace() {
        if (this.mFaceProvider.getTestHalEnabled()) {
            com.android.server.biometrics.sensors.face.hidl.TestHal testHal = new com.android.server.biometrics.sensors.face.hidl.TestHal(getContext(), getSensorProperties().sensorId);
            testHal.setCallback(new com.android.server.biometrics.sensors.face.hidl.HidlToAidlCallbackConverter(getAidlResponseHandler()));
            return testHal;
        }
        if (this.mDaemon != null) {
            return this.mDaemon;
        }
        android.util.Slog.d(TAG, "Face daemon was null, reconnecting, current operation: " + getScheduler().getCurrentClient());
        try {
            this.mDaemon = android.hardware.biometrics.face.V1_0.IBiometricsFace.getService();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to get face HAL", e);
        } catch (java.util.NoSuchElementException e2) {
            android.util.Slog.w(TAG, "NoSuchElementException", e2);
        }
        if (this.mDaemon == null) {
            android.util.Slog.w(TAG, "Face HAL not available");
            return null;
        }
        this.mDaemon.asBinder().linkToDeath(this, 0L);
        scheduleLoadAuthenticatorIds();
        this.mInternalCleanupAndGetFeatureRunnable.run();
        return this.mDaemon;
    }

    @com.android.internal.annotations.VisibleForTesting
    void handleUserChanged(int i) {
        android.util.Slog.d(TAG, "User changed. Current user for face sensor is " + i);
        this.mSession = null;
        this.mCurrentUserId = i;
    }

    private void scheduleLoadAuthenticatorIds() {
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter.this.lambda$scheduleLoadAuthenticatorIds$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleLoadAuthenticatorIds$3() {
        java.util.Iterator it = android.os.UserManager.get(getContext()).getAliveUsers().iterator();
        while (it.hasNext()) {
            int i = ((android.content.pm.UserInfo) it.next()).id;
            if (!getAuthenticatorIds().containsKey(java.lang.Integer.valueOf(i))) {
                scheduleFaceUpdateActiveUserClient(i);
            }
        }
    }

    private com.android.server.biometrics.sensors.face.hidl.FaceUpdateActiveUserClient getFaceUpdateActiveUserClient(int i) {
        return new com.android.server.biometrics.sensors.face.hidl.FaceUpdateActiveUserClient(getContext(), new com.android.server.biometrics.sensors.face.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda4(this), this.mUserStartedCallback, i, TAG, getSensorProperties().sensorId, com.android.server.biometrics.log.BiometricLogger.ofUnknown(getContext()), getBiometricContext(), !com.android.server.biometrics.sensors.face.FaceUtils.getInstance(getSensorProperties().sensorId).getBiometricsForUser(getContext(), i).isEmpty(), getAuthenticatorIds());
    }
}
