package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
public class HidlToAidlSensorAdapter extends com.android.server.biometrics.sensors.fingerprint.aidl.Sensor implements android.os.IHwBinder.DeathRecipient {
    private static final java.lang.String TAG = "HidlToAidlSensorAdapter";
    private final com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback mAidlResponseHandlerCallback;
    private final com.android.server.biometrics.sensors.AuthSessionCoordinator mAuthSessionCoordinator;
    private int mCurrentUserId;
    private android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint mDaemon;
    private final java.lang.Runnable mInternalCleanupRunnable;
    private final com.android.server.biometrics.sensors.LockoutResetDispatcher mLockoutResetDispatcher;
    private com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl mLockoutTracker;
    private com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession mSession;
    private final com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> mUserStartedCallback;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession aidlSession, int i2) {
        if (this.mCurrentUserId != i) {
            handleUserChanged(i);
        }
    }

    public HidlToAidlSensorAdapter(@android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider fingerprintProvider, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.hardware.biometrics.fingerprint.SensorProps sensorProps, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z, @android.annotation.NonNull java.lang.Runnable runnable) {
        this(fingerprintProvider, context, handler, sensorProps, lockoutResetDispatcher, biometricContext, z, runnable, new com.android.server.biometrics.sensors.AuthSessionCoordinator(), null, null);
    }

    @com.android.internal.annotations.VisibleForTesting
    HidlToAidlSensorAdapter(@android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider fingerprintProvider, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.hardware.biometrics.fingerprint.SensorProps sensorProps, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z, @android.annotation.NonNull java.lang.Runnable runnable, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthSessionCoordinator authSessionCoordinator, @android.annotation.Nullable android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint iBiometricsFingerprint, @android.annotation.Nullable com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback aidlResponseHandlerCallback) {
        super(fingerprintProvider, context, handler, com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.getFingerprintSensorPropertiesInternal(sensorProps, new java.util.ArrayList(), z), biometricContext, null);
        com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback aidlResponseHandlerCallback2;
        this.mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
        this.mUserStartedCallback = new com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda0
            @Override // com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback
            public final void onUserStarted(int i, java.lang.Object obj, int i2) {
                com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.lambda$new$0(i, (com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession) obj, i2);
            }
        };
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mInternalCleanupRunnable = runnable;
        this.mAuthSessionCoordinator = authSessionCoordinator;
        this.mDaemon = iBiometricsFingerprint;
        if (aidlResponseHandlerCallback == null) {
            aidlResponseHandlerCallback2 = new com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.1
                @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback
                public void onEnrollSuccess() {
                    com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.getScheduler().scheduleClientMonitor(com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.getFingerprintUpdateActiveUserClient(com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.mCurrentUserId, true));
                }

                @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback
                public void onHardwareUnavailable() {
                    com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.mDaemon = null;
                    com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.mSession = null;
                    com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
                }
            };
        } else {
            aidlResponseHandlerCallback2 = aidlResponseHandlerCallback;
        }
        this.mAidlResponseHandlerCallback = aidlResponseHandlerCallback2;
    }

    public void serviceDied(long j) {
        android.util.Slog.d(TAG, "Fingerprint HAL died.");
        this.mSession = null;
        this.mDaemon = null;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    public int getLockoutModeForUser(int i) {
        return this.mLockoutTracker.getLockoutModeForUser(i);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    public void init(@android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher) {
        setLazySession(new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession session;
                session = com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.getSession();
                return session;
            }
        });
        setScheduler(new com.android.server.biometrics.sensors.BiometricScheduler(getHandler(), com.android.server.biometrics.sensors.BiometricScheduler.sensorTypeFromFingerprintProperties(getSensorProperties()), gestureAvailabilityDispatcher, (java.util.function.Supplier<java.lang.Integer>) new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Integer lambda$init$1;
                lambda$init$1 = com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.lambda$init$1();
                return lambda$init$1;
            }
        }, getUserSwitchProvider()));
        this.mLockoutTracker = new com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl(getContext(), new com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.LockoutResetCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda8
            @Override // com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.LockoutResetCallback
            public final void onLockoutReset(int i) {
                com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.lambda$init$2(i);
            }
        }, getHandler());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$init$1() {
        return java.lang.Integer.valueOf(this.mCurrentUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(int i) {
        this.mLockoutResetDispatcher.notifyLockoutResetCallbacks(getSensorProperties().sensorId);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    protected com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession getSessionForUser(int i) {
        if (this.mSession != null && this.mSession.getUserId() == i) {
            return this.mSession;
        }
        return null;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    protected boolean isHardwareDetected(java.lang.String str) {
        return getIBiometricsFingerprint() != null;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.aidl.Sensor
    @android.annotation.NonNull
    protected com.android.server.biometrics.sensors.LockoutTracker getLockoutTracker(boolean z) {
        return this.mLockoutTracker;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession getSession() {
        if (this.mSession != null && this.mDaemon != null) {
            return this.mSession;
        }
        com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession aidlSession = new com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession(new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.getIBiometricsFingerprint();
            }
        }, this.mCurrentUserId, getAidlResponseHandler());
        this.mSession = aidlSession;
        return aidlSession;
    }

    private com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler getAidlResponseHandler() {
        return new com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler(getContext(), getScheduler(), getSensorProperties().sensorId, this.mCurrentUserId, this.mLockoutTracker, this.mLockoutResetDispatcher, this.mAuthSessionCoordinator, new com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.HardwareUnavailableCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda2
            @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.HardwareUnavailableCallback
            public final void onHardwareUnavailable() {
                com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.lambda$getAidlResponseHandler$3();
            }
        }, this.mAidlResponseHandlerCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getAidlResponseHandler$3() {
    }

    @com.android.internal.annotations.VisibleForTesting
    android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint getIBiometricsFingerprint() {
        if (getProvider().getTestHalEnabled()) {
            com.android.server.biometrics.sensors.fingerprint.hidl.TestHal testHal = new com.android.server.biometrics.sensors.fingerprint.hidl.TestHal(getContext(), getSensorProperties().sensorId);
            testHal.setNotify(new com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlCallbackConverter(getAidlResponseHandler()));
            return testHal;
        }
        if (this.mDaemon != null) {
            return this.mDaemon;
        }
        try {
            this.mDaemon = android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint.getService();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to get fingerprint HAL", e);
        } catch (java.util.NoSuchElementException e2) {
            android.util.Slog.w(TAG, "NoSuchElementException", e2);
        }
        if (this.mDaemon == null) {
            android.util.Slog.w(TAG, "Fingerprint HAL not available");
            return null;
        }
        this.mDaemon.asBinder().linkToDeath(this, 0L);
        scheduleLoadAuthenticatorIds();
        this.mInternalCleanupRunnable.run();
        return this.mDaemon;
    }

    /* renamed from: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$2, reason: invalid class name */
    class AnonymousClass2 implements com.android.server.biometrics.sensors.UserSwitchProvider<android.hardware.biometrics.fingerprint.ISession, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> {
        AnonymousClass2() {
        }

        @Override // com.android.server.biometrics.sensors.UserSwitchProvider
        @android.annotation.NonNull
        public com.android.server.biometrics.sensors.StopUserClient<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> getStopUserClient(int i) {
            android.content.Context context = com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.getContext();
            final com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter hidlToAidlSensorAdapter = com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this;
            return new com.android.server.biometrics.sensors.StopUserClient<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession>(context, new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$2$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession session;
                    session = com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.getSession();
                    return session;
                }
            }, null, i, com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.getSensorProperties().sensorId, com.android.server.biometrics.log.BiometricLogger.ofUnknown(com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.getContext()), com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.getBiometricContext(), new com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$2$$ExternalSyntheticLambda1
                @Override // com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback
                public final void onUserStopped() {
                    com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.AnonymousClass2.this.lambda$getStopUserClient$0();
                }
            }) { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.2.1
                @Override // com.android.server.biometrics.sensors.BaseClientMonitor
                public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
                    super.start(clientMonitorCallback);
                    startHalOperation();
                }

                @Override // com.android.server.biometrics.sensors.HalClientMonitor
                protected void startHalOperation() {
                    onUserStopped();
                }

                @Override // com.android.server.biometrics.sensors.HalClientMonitor
                public void unableToStart() {
                    getCallback().onClientFinished(this, false);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStopUserClient$0() {
            com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
            com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.mSession = null;
        }

        @Override // com.android.server.biometrics.sensors.UserSwitchProvider
        @android.annotation.NonNull
        public com.android.server.biometrics.sensors.StartUserClient<android.hardware.biometrics.fingerprint.ISession, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> getStartUserClient(int i) {
            return com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.getFingerprintUpdateActiveUserClient(i, false);
        }
    }

    private com.android.server.biometrics.sensors.UserSwitchProvider<android.hardware.biometrics.fingerprint.ISession, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> getUserSwitchProvider() {
        return new com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.AnonymousClass2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintUpdateActiveUserClient getFingerprintUpdateActiveUserClient(int i, boolean z) {
        return new com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintUpdateActiveUserClient(getContext(), new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.hardware.biometrics.fingerprint.ISession lambda$getFingerprintUpdateActiveUserClient$4;
                lambda$getFingerprintUpdateActiveUserClient$4 = com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.lambda$getFingerprintUpdateActiveUserClient$4();
                return lambda$getFingerprintUpdateActiveUserClient$4;
            }
        }, i, TAG, getSensorProperties().sensorId, com.android.server.biometrics.log.BiometricLogger.ofUnknown(getContext()), getBiometricContext(), new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Integer lambda$getFingerprintUpdateActiveUserClient$5;
                lambda$getFingerprintUpdateActiveUserClient$5 = com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.lambda$getFingerprintUpdateActiveUserClient$5();
                return lambda$getFingerprintUpdateActiveUserClient$5;
            }
        }, !com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(getSensorProperties().sensorId).getBiometricsForUser(getContext(), i).isEmpty(), getAuthenticatorIds(), z, this.mUserStartedCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.hardware.biometrics.fingerprint.ISession lambda$getFingerprintUpdateActiveUserClient$4() {
        return getSession().getSession();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$getFingerprintUpdateActiveUserClient$5() {
        return java.lang.Integer.valueOf(this.mCurrentUserId);
    }

    private void scheduleLoadAuthenticatorIds() {
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSensorAdapter.this.lambda$scheduleLoadAuthenticatorIds$6();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleLoadAuthenticatorIds$6() {
        java.util.Iterator it = android.os.UserManager.get(getContext()).getAliveUsers().iterator();
        while (it.hasNext()) {
            int i = ((android.content.pm.UserInfo) it.next()).id;
            if (!getAuthenticatorIds().containsKey(java.lang.Integer.valueOf(i))) {
                getScheduler().scheduleClientMonitor(getFingerprintUpdateActiveUserClient(i, true));
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void handleUserChanged(int i) {
        android.util.Slog.d(TAG, "User changed. Current user for fingerprint sensor is " + i);
        this.mSession = null;
        this.mCurrentUserId = i;
    }
}
