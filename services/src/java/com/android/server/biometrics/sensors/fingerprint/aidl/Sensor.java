package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
public class Sensor {
    private static final java.lang.String TAG = "Sensor";

    @android.annotation.NonNull
    private final java.util.Map<java.lang.Integer, java.lang.Long> mAuthenticatorIds;

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.BiometricContext mBiometricContext;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.Nullable
    com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession mCurrentSession;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private java.util.function.Supplier<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> mLazySession;

    @android.annotation.NonNull
    private com.android.server.biometrics.sensors.LockoutTracker mLockoutTracker;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider mProvider;

    @android.annotation.NonNull
    private com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.fingerprint.IFingerprint, android.hardware.biometrics.fingerprint.ISession> mScheduler;

    @android.annotation.NonNull
    private final android.hardware.fingerprint.FingerprintSensorPropertiesInternal mSensorProperties;
    private boolean mTestHalEnabled;

    @android.annotation.NonNull
    private final android.os.IBinder mToken;

    public Sensor(@android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider fingerprintProvider, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession aidlSession) {
        this.mProvider = fingerprintProvider;
        this.mContext = context;
        this.mToken = new android.os.Binder();
        this.mHandler = handler;
        this.mSensorProperties = fingerprintSensorPropertiesInternal;
        this.mBiometricContext = biometricContext;
        this.mAuthenticatorIds = new java.util.HashMap();
        this.mCurrentSession = aidlSession;
    }

    Sensor(@android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider fingerprintProvider, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        this(fingerprintProvider, context, handler, fingerprintSensorPropertiesInternal, biometricContext, null);
    }

    Sensor(@android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider fingerprintProvider, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.hardware.biometrics.fingerprint.SensorProps sensorProps, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull java.util.List<android.hardware.biometrics.SensorLocationInternal> list, boolean z) {
        this(fingerprintProvider, context, handler, getFingerprintSensorPropertiesInternal(sensorProps, list, z), biometricContext, null);
    }

    public void init(@android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher) {
        com.android.server.biometrics.Flags.deHidl();
        setScheduler(getUserAwareBiometricSchedulerForInit(gestureAvailabilityDispatcher, lockoutResetDispatcher));
        this.mLockoutTracker = new com.android.server.biometrics.sensors.LockoutCache();
        this.mLazySession = new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession lambda$init$0;
                lambda$init$0 = com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.lambda$init$0();
                return lambda$init$0;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession lambda$init$0() {
        if (this.mCurrentSession != null) {
            return this.mCurrentSession;
        }
        return null;
    }

    private com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.fingerprint.IFingerprint, android.hardware.biometrics.fingerprint.ISession> getBiometricSchedulerForInit(@android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher) {
        return new com.android.server.biometrics.sensors.BiometricScheduler<>(this.mHandler, com.android.server.biometrics.sensors.BiometricScheduler.sensorTypeFromFingerprintProperties(this.mSensorProperties), gestureAvailabilityDispatcher, (java.util.function.Supplier<java.lang.Integer>) new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Integer lambda$getBiometricSchedulerForInit$1;
                lambda$getBiometricSchedulerForInit$1 = com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.lambda$getBiometricSchedulerForInit$1();
                return lambda$getBiometricSchedulerForInit$1;
            }
        }, new com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.AnonymousClass1(lockoutResetDispatcher));
    }

    /* renamed from: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$1, reason: invalid class name */
    class AnonymousClass1 implements com.android.server.biometrics.sensors.UserSwitchProvider<android.hardware.biometrics.fingerprint.IFingerprint, android.hardware.biometrics.fingerprint.ISession> {
        final /* synthetic */ com.android.server.biometrics.sensors.LockoutResetDispatcher val$lockoutResetDispatcher;

        AnonymousClass1(com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher) {
            this.val$lockoutResetDispatcher = lockoutResetDispatcher;
        }

        @Override // com.android.server.biometrics.sensors.UserSwitchProvider
        @android.annotation.NonNull
        public com.android.server.biometrics.sensors.StopUserClient<android.hardware.biometrics.fingerprint.ISession> getStopUserClient(int i) {
            return new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintStopUserClient(com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mContext, new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.hardware.biometrics.fingerprint.ISession lambda$getStopUserClient$0;
                    lambda$getStopUserClient$0 = com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.AnonymousClass1.this.lambda$getStopUserClient$0();
                    return lambda$getStopUserClient$0;
                }
            }, com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mToken, i, com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mSensorProperties.sensorId, com.android.server.biometrics.log.BiometricLogger.ofUnknown(com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mContext), com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mBiometricContext, new com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$1$$ExternalSyntheticLambda2
                @Override // com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback
                public final void onUserStopped() {
                    com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.AnonymousClass1.this.lambda$getStopUserClient$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.hardware.biometrics.fingerprint.ISession lambda$getStopUserClient$0() {
            return ((com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession) com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mLazySession.get()).getSession();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStopUserClient$1() {
            com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mCurrentSession = null;
        }

        @Override // com.android.server.biometrics.sensors.UserSwitchProvider
        @android.annotation.NonNull
        public com.android.server.biometrics.sensors.StartUserClient<android.hardware.biometrics.fingerprint.IFingerprint, android.hardware.biometrics.fingerprint.ISession> getStartUserClient(final int i) {
            final int i2 = com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mSensorProperties.sensorId;
            return com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.getStartUserClient(new com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler(com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mContext, com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mScheduler, i2, i, com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mLockoutTracker, this.val$lockoutResetDispatcher, com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mBiometricContext.getAuthSessionCoordinator(), new com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.HardwareUnavailableCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$1$$ExternalSyntheticLambda0
                @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.HardwareUnavailableCallback
                public final void onHardwareUnavailable() {
                    com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.AnonymousClass1.lambda$getStartUserClient$2();
                }
            }, new com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.1.1
                @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback
                public void onEnrollSuccess() {
                    com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mProvider.scheduleLoadAuthenticatorIdsForUser(i2, i);
                    com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mProvider.scheduleInvalidationRequest(i2, i);
                }

                @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.AidlResponseHandlerCallback
                public void onHardwareUnavailable() {
                    android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.TAG, "Fingerprint sensor hardware unavailable.");
                    com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mCurrentSession = null;
                }
            }), i2, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getStartUserClient$2() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$getBiometricSchedulerForInit$1() {
        return java.lang.Integer.valueOf(this.mCurrentSession != null ? this.mCurrentSession.getUserId() : com.android.server.am.ProcessList.INVALID_ADJ);
    }

    private com.android.server.biometrics.sensors.UserAwareBiometricScheduler<android.hardware.biometrics.fingerprint.ISession, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> getUserAwareBiometricSchedulerForInit(com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher) {
        return new com.android.server.biometrics.sensors.UserAwareBiometricScheduler<>(TAG, com.android.server.biometrics.sensors.BiometricScheduler.sensorTypeFromFingerprintProperties(this.mSensorProperties), gestureAvailabilityDispatcher, new com.android.server.biometrics.sensors.UserAwareBiometricScheduler.CurrentUserRetriever() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda2
            @Override // com.android.server.biometrics.sensors.UserAwareBiometricScheduler.CurrentUserRetriever
            public final int getCurrentUserId() {
                int lambda$getUserAwareBiometricSchedulerForInit$2;
                lambda$getUserAwareBiometricSchedulerForInit$2 = com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.lambda$getUserAwareBiometricSchedulerForInit$2();
                return lambda$getUserAwareBiometricSchedulerForInit$2;
            }
        }, new com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.AnonymousClass2(lockoutResetDispatcher));
    }

    /* renamed from: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$2, reason: invalid class name */
    class AnonymousClass2 implements com.android.server.biometrics.sensors.UserAwareBiometricScheduler.UserSwitchCallback {
        final /* synthetic */ com.android.server.biometrics.sensors.LockoutResetDispatcher val$lockoutResetDispatcher;

        AnonymousClass2(com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher) {
            this.val$lockoutResetDispatcher = lockoutResetDispatcher;
        }

        @Override // com.android.server.biometrics.sensors.UserAwareBiometricScheduler.UserSwitchCallback
        @android.annotation.NonNull
        public com.android.server.biometrics.sensors.StopUserClient<android.hardware.biometrics.fingerprint.ISession> getStopUserClient(int i) {
            return new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintStopUserClient(com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mContext, new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$2$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.hardware.biometrics.fingerprint.ISession lambda$getStopUserClient$0;
                    lambda$getStopUserClient$0 = com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.AnonymousClass2.this.lambda$getStopUserClient$0();
                    return lambda$getStopUserClient$0;
                }
            }, com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mToken, i, com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mSensorProperties.sensorId, com.android.server.biometrics.log.BiometricLogger.ofUnknown(com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mContext), com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mBiometricContext, new com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$2$$ExternalSyntheticLambda2
                @Override // com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback
                public final void onUserStopped() {
                    com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.AnonymousClass2.this.lambda$getStopUserClient$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.hardware.biometrics.fingerprint.ISession lambda$getStopUserClient$0() {
            return ((com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession) com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mLazySession.get()).getSession();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStopUserClient$1() {
            com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mCurrentSession = null;
        }

        @Override // com.android.server.biometrics.sensors.UserAwareBiometricScheduler.UserSwitchCallback
        @android.annotation.NonNull
        public com.android.server.biometrics.sensors.StartUserClient<android.hardware.biometrics.fingerprint.IFingerprint, android.hardware.biometrics.fingerprint.ISession> getStartUserClient(int i) {
            int i2 = com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mSensorProperties.sensorId;
            return com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.getStartUserClient(new com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler(com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mContext, com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mScheduler, i2, i, com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mLockoutTracker, this.val$lockoutResetDispatcher, com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mBiometricContext.getAuthSessionCoordinator(), new com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.HardwareUnavailableCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$2$$ExternalSyntheticLambda0
                @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.HardwareUnavailableCallback
                public final void onHardwareUnavailable() {
                    com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.AnonymousClass2.this.lambda$getStartUserClient$2();
                }
            }), i2, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStartUserClient$2() {
            android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.TAG, "Fingerprint hardware unavailable.");
            com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.mCurrentSession = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$getUserAwareBiometricSchedulerForInit$2() {
        return this.mCurrentSession != null ? this.mCurrentSession.getUserId() : com.android.server.am.ProcessList.INVALID_ADJ;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintStartUserClient getStartUserClient(final com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler aidlResponseHandler, final int i, int i2) {
        com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback userStartedCallback = new com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda0
            @Override // com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback
            public final void onUserStarted(int i3, java.lang.Object obj, int i4) {
                com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.this.lambda$getStartUserClient$3(aidlResponseHandler, i, i3, (android.hardware.biometrics.fingerprint.ISession) obj, i4);
            }
        };
        android.content.Context context = this.mContext;
        final com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider fingerprintProvider = this.mProvider;
        java.util.Objects.requireNonNull(fingerprintProvider);
        return new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintStartUserClient(context, new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider.this.getHalInstance();
            }
        }, this.mToken, i2, this.mSensorProperties.sensorId, com.android.server.biometrics.log.BiometricLogger.ofUnknown(this.mContext), this.mBiometricContext, aidlResponseHandler, userStartedCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getStartUserClient$3(com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler aidlResponseHandler, int i, int i2, android.hardware.biometrics.fingerprint.ISession iSession, int i3) {
        android.util.Slog.d(TAG, "New fingerprint session created for user: " + i2 + " with hal version: " + i3);
        this.mCurrentSession = new com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession(i3, iSession, i2, aidlResponseHandler);
        if (com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(i).isInvalidationInProgress(this.mContext, i2)) {
            android.util.Slog.w(TAG, "Scheduling unfinished invalidation request for fingerprint sensor: " + i + ", user: " + i2);
            this.mProvider.scheduleInvalidationRequest(i, i2);
        }
    }

    protected static android.hardware.fingerprint.FingerprintSensorPropertiesInternal getFingerprintSensorPropertiesInternal(android.hardware.biometrics.fingerprint.SensorProps sensorProps, java.util.List<android.hardware.biometrics.SensorLocationInternal> list, boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (sensorProps.commonProps.componentInfo != null) {
            for (android.hardware.biometrics.common.ComponentInfo componentInfo : sensorProps.commonProps.componentInfo) {
                arrayList.add(new android.hardware.biometrics.ComponentInfoInternal(componentInfo.componentId, componentInfo.hardwareVersion, componentInfo.firmwareVersion, componentInfo.serialNumber, componentInfo.softwareVersion));
            }
        }
        return new android.hardware.fingerprint.FingerprintSensorPropertiesInternal(sensorProps.commonProps.sensorId, sensorProps.commonProps.sensorStrength, sensorProps.commonProps.maxEnrollmentsPerUser, arrayList, sensorProps.sensorType, sensorProps.halControlsIllumination, z, !list.isEmpty() ? list : (java.util.List) java.util.Arrays.stream(sensorProps.sensorLocations).map(new java.util.function.Function() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.Sensor$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.hardware.biometrics.SensorLocationInternal lambda$getFingerprintSensorPropertiesInternal$4;
                lambda$getFingerprintSensorPropertiesInternal$4 = com.android.server.biometrics.sensors.fingerprint.aidl.Sensor.lambda$getFingerprintSensorPropertiesInternal$4((android.hardware.biometrics.fingerprint.SensorLocation) obj);
                return lambda$getFingerprintSensorPropertiesInternal$4;
            }
        }).collect(java.util.stream.Collectors.toList()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.biometrics.SensorLocationInternal lambda$getFingerprintSensorPropertiesInternal$4(android.hardware.biometrics.fingerprint.SensorLocation sensorLocation) {
        return new android.hardware.biometrics.SensorLocationInternal(sensorLocation.display, sensorLocation.sensorLocationX, sensorLocation.sensorLocationY, sensorLocation.sensorRadius);
    }

    @android.annotation.NonNull
    public java.util.function.Supplier<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> getLazySession() {
        return this.mLazySession;
    }

    @android.annotation.NonNull
    public android.hardware.fingerprint.FingerprintSensorPropertiesInternal getSensorProperties() {
        return this.mSensorProperties;
    }

    @android.annotation.Nullable
    protected com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession getSessionForUser(int i) {
        if (this.mCurrentSession != null && this.mCurrentSession.getUserId() == i) {
            return this.mCurrentSession;
        }
        return null;
    }

    @android.annotation.NonNull
    android.hardware.biometrics.ITestSession createTestSession(@android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricStateCallback biometricStateCallback) {
        return new com.android.server.biometrics.sensors.fingerprint.aidl.BiometricTestSessionImpl(this.mContext, this.mSensorProperties.sensorId, iTestSessionCallback, biometricStateCallback, this.mProvider, this);
    }

    @android.annotation.NonNull
    public com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.fingerprint.IFingerprint, android.hardware.biometrics.fingerprint.ISession> getScheduler() {
        return this.mScheduler;
    }

    @android.annotation.NonNull
    protected com.android.server.biometrics.sensors.LockoutTracker getLockoutTracker(boolean z) {
        if (z) {
            return null;
        }
        return this.mLockoutTracker;
    }

    @android.annotation.NonNull
    public java.util.Map<java.lang.Integer, java.lang.Long> getAuthenticatorIds() {
        return this.mAuthenticatorIds;
    }

    void setTestHalEnabled(boolean z) {
        android.util.Slog.w(TAG, "Fingerprint setTestHalEnabled: " + z);
        if (z != this.mTestHalEnabled) {
            try {
                if (this.mCurrentSession != null) {
                    android.util.Slog.d(TAG, "Closing old fingerprint session");
                    this.mCurrentSession.getSession().close();
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "RemoteException", e);
            }
            this.mCurrentSession = null;
        }
        this.mTestHalEnabled = z;
    }

    void dumpProtoState(int i, @android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, boolean z) {
        long start = protoOutputStream.start(2246267895809L);
        protoOutputStream.write(1120986464257L, this.mSensorProperties.sensorId);
        protoOutputStream.write(1159641169922L, 1);
        if (this.mSensorProperties.isAnyUdfpsType()) {
            protoOutputStream.write(2259152797704L, 0);
        }
        protoOutputStream.write(1120986464259L, com.android.server.biometrics.Utils.getCurrentStrength(this.mSensorProperties.sensorId));
        protoOutputStream.write(1146756268036L, this.mScheduler.dumpProtoState(z));
        java.util.Iterator it = android.os.UserManager.get(this.mContext).getUsers().iterator();
        while (it.hasNext()) {
            int identifier = ((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier();
            long start2 = protoOutputStream.start(2246267895813L);
            protoOutputStream.write(1120986464257L, identifier);
            protoOutputStream.write(1120986464258L, com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(this.mSensorProperties.sensorId).getBiometricsForUser(this.mContext, identifier).size());
            protoOutputStream.end(start2);
        }
        protoOutputStream.write(1133871366150L, this.mSensorProperties.resetLockoutRequiresHardwareAuthToken);
        protoOutputStream.write(1133871366151L, this.mSensorProperties.resetLockoutRequiresChallenge);
        protoOutputStream.end(start);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onBinderDied() {
        com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient instanceof com.android.server.biometrics.sensors.ErrorConsumer) {
            android.util.Slog.e(TAG, "Sending fingerprint hardware unavailable error for client: " + currentClient);
            ((com.android.server.biometrics.sensors.ErrorConsumer) currentClient).onError(1, 0);
            com.android.internal.util.FrameworkStatsLog.write(148, 1, 1, -1);
        } else if (currentClient != 0) {
            currentClient.cancel();
        }
        this.mScheduler.recordCrashState();
        this.mScheduler.reset();
        this.mCurrentSession = null;
    }

    @android.annotation.NonNull
    protected android.os.Handler getHandler() {
        return this.mHandler;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @android.annotation.NonNull
    public android.content.Context getContext() {
        return this.mContext;
    }

    protected boolean isHardwareDetected(java.lang.String str) {
        if (this.mTestHalEnabled) {
            return true;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.hardware.biometrics.fingerprint.IFingerprint.DESCRIPTOR);
        sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
        sb.append(str);
        return android.os.ServiceManager.checkService(sb.toString()) != null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @android.annotation.NonNull
    public com.android.server.biometrics.log.BiometricContext getBiometricContext() {
        return this.mBiometricContext;
    }

    public int getLockoutModeForUser(int i) {
        return this.mBiometricContext.getAuthSessionCoordinator().getLockoutStateFor(i, com.android.server.biometrics.Utils.getCurrentStrength(this.mSensorProperties.sensorId));
    }

    public void setScheduler(com.android.server.biometrics.sensors.BiometricScheduler biometricScheduler) {
        this.mScheduler = biometricScheduler;
    }

    public void setLazySession(java.util.function.Supplier<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> supplier) {
        this.mLazySession = supplier;
    }

    public com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider getProvider() {
        return this.mProvider;
    }
}
