package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class Sensor {
    private static final java.lang.String TAG = "Sensor";

    @android.annotation.NonNull
    private final java.util.Map<java.lang.Integer, java.lang.Long> mAuthenticatorIds;

    @android.annotation.NonNull
    com.android.server.biometrics.log.BiometricContext mBiometricContext;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.Nullable
    com.android.server.biometrics.sensors.face.aidl.AidlSession mCurrentSession;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> mLazySession;

    @android.annotation.Nullable
    private com.android.server.biometrics.sensors.LockoutTracker mLockoutTracker;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.aidl.FaceProvider mProvider;

    @android.annotation.NonNull
    private com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.face.IFace, android.hardware.biometrics.face.ISession> mScheduler;

    @android.annotation.NonNull
    private final android.hardware.face.FaceSensorPropertiesInternal mSensorProperties;
    private boolean mTestHalEnabled;

    @android.annotation.NonNull
    private final android.os.IBinder mToken;

    Sensor(@android.annotation.NonNull com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.hardware.face.FaceSensorPropertiesInternal faceSensorPropertiesInternal, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        this.mProvider = faceProvider;
        this.mContext = context;
        this.mToken = new android.os.Binder();
        this.mHandler = handler;
        this.mSensorProperties = faceSensorPropertiesInternal;
        this.mBiometricContext = biometricContext;
        this.mAuthenticatorIds = new java.util.HashMap();
    }

    public Sensor(@android.annotation.NonNull com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull android.hardware.biometrics.face.SensorProps sensorProps, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z) {
        this(faceProvider, context, handler, getFaceSensorPropertiesInternal(sensorProps, z), biometricContext);
    }

    public void init(@android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider) {
        com.android.server.biometrics.Flags.deHidl();
        setScheduler(getUserAwareBiometricSchedulerForInit(lockoutResetDispatcher, faceProvider));
        this.mLazySession = new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                com.android.server.biometrics.sensors.face.aidl.AidlSession lambda$init$0;
                lambda$init$0 = com.android.server.biometrics.sensors.face.aidl.Sensor.this.lambda$init$0();
                return lambda$init$0;
            }
        };
        this.mLockoutTracker = new com.android.server.biometrics.sensors.LockoutCache();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.biometrics.sensors.face.aidl.AidlSession lambda$init$0() {
        if (this.mCurrentSession != null) {
            return this.mCurrentSession;
        }
        return null;
    }

    private com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.face.IFace, android.hardware.biometrics.face.ISession> getBiometricSchedulerForInit(@android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider) {
        return new com.android.server.biometrics.sensors.BiometricScheduler<>(this.mHandler, 1, (com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher) null, (java.util.function.Supplier<java.lang.Integer>) new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Integer lambda$getBiometricSchedulerForInit$1;
                lambda$getBiometricSchedulerForInit$1 = com.android.server.biometrics.sensors.face.aidl.Sensor.this.lambda$getBiometricSchedulerForInit$1();
                return lambda$getBiometricSchedulerForInit$1;
            }
        }, new com.android.server.biometrics.sensors.face.aidl.Sensor.AnonymousClass1(lockoutResetDispatcher, faceProvider));
    }

    /* renamed from: com.android.server.biometrics.sensors.face.aidl.Sensor$1, reason: invalid class name */
    class AnonymousClass1 implements com.android.server.biometrics.sensors.UserSwitchProvider<android.hardware.biometrics.face.IFace, android.hardware.biometrics.face.ISession> {
        final /* synthetic */ com.android.server.biometrics.sensors.LockoutResetDispatcher val$lockoutResetDispatcher;
        final /* synthetic */ com.android.server.biometrics.sensors.face.aidl.FaceProvider val$provider;

        AnonymousClass1(com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider) {
            this.val$lockoutResetDispatcher = lockoutResetDispatcher;
            this.val$provider = faceProvider;
        }

        @Override // com.android.server.biometrics.sensors.UserSwitchProvider
        @android.annotation.NonNull
        public com.android.server.biometrics.sensors.StopUserClient<android.hardware.biometrics.face.ISession> getStopUserClient(int i) {
            return new com.android.server.biometrics.sensors.face.aidl.FaceStopUserClient(com.android.server.biometrics.sensors.face.aidl.Sensor.this.mContext, new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.hardware.biometrics.face.ISession lambda$getStopUserClient$0;
                    lambda$getStopUserClient$0 = com.android.server.biometrics.sensors.face.aidl.Sensor.AnonymousClass1.this.lambda$getStopUserClient$0();
                    return lambda$getStopUserClient$0;
                }
            }, com.android.server.biometrics.sensors.face.aidl.Sensor.this.mToken, i, com.android.server.biometrics.sensors.face.aidl.Sensor.this.mSensorProperties.sensorId, com.android.server.biometrics.log.BiometricLogger.ofUnknown(com.android.server.biometrics.sensors.face.aidl.Sensor.this.mContext), com.android.server.biometrics.sensors.face.aidl.Sensor.this.mBiometricContext, new com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$1$$ExternalSyntheticLambda1
                @Override // com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback
                public final void onUserStopped() {
                    com.android.server.biometrics.sensors.face.aidl.Sensor.AnonymousClass1.this.lambda$getStopUserClient$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.hardware.biometrics.face.ISession lambda$getStopUserClient$0() {
            return ((com.android.server.biometrics.sensors.face.aidl.AidlSession) com.android.server.biometrics.sensors.face.aidl.Sensor.this.mLazySession.get()).getSession();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStopUserClient$1() {
            com.android.server.biometrics.sensors.face.aidl.Sensor.this.mCurrentSession = null;
        }

        @Override // com.android.server.biometrics.sensors.UserSwitchProvider
        @android.annotation.NonNull
        public com.android.server.biometrics.sensors.StartUserClient<android.hardware.biometrics.face.IFace, android.hardware.biometrics.face.ISession> getStartUserClient(final int i) {
            final int i2 = com.android.server.biometrics.sensors.face.aidl.Sensor.this.mSensorProperties.sensorId;
            return com.android.server.biometrics.sensors.face.aidl.Sensor.this.getStartUserClient(new com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler(com.android.server.biometrics.sensors.face.aidl.Sensor.this.mContext, com.android.server.biometrics.sensors.face.aidl.Sensor.this.mScheduler, i2, i, com.android.server.biometrics.sensors.face.aidl.Sensor.this.mLockoutTracker, this.val$lockoutResetDispatcher, com.android.server.biometrics.sensors.face.aidl.Sensor.this.mBiometricContext.getAuthSessionCoordinator(), new com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.HardwareUnavailableCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$1$$ExternalSyntheticLambda2
                @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.HardwareUnavailableCallback
                public final void onHardwareUnavailable() {
                    com.android.server.biometrics.sensors.face.aidl.Sensor.AnonymousClass1.lambda$getStartUserClient$2();
                }
            }, new com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor.1.1
                @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback
                public void onEnrollSuccess() {
                    com.android.server.biometrics.sensors.face.aidl.Sensor.this.mProvider.scheduleLoadAuthenticatorIdsForUser(i2, i);
                    com.android.server.biometrics.sensors.face.aidl.Sensor.this.mProvider.scheduleInvalidationRequest(i2, i);
                }

                @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.AidlResponseHandlerCallback
                public void onHardwareUnavailable() {
                    android.util.Slog.e(com.android.server.biometrics.sensors.face.aidl.Sensor.TAG, "Face sensor hardware unavailable.");
                    com.android.server.biometrics.sensors.face.aidl.Sensor.this.mCurrentSession = null;
                }
            }), i2, i, this.val$provider);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getStartUserClient$2() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$getBiometricSchedulerForInit$1() {
        return java.lang.Integer.valueOf(this.mCurrentSession != null ? this.mCurrentSession.getUserId() : com.android.server.am.ProcessList.INVALID_ADJ);
    }

    private com.android.server.biometrics.sensors.UserAwareBiometricScheduler<android.hardware.biometrics.face.IFace, android.hardware.biometrics.face.ISession> getUserAwareBiometricSchedulerForInit(com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider) {
        return new com.android.server.biometrics.sensors.UserAwareBiometricScheduler<>(TAG, 1, null, new com.android.server.biometrics.sensors.UserAwareBiometricScheduler.CurrentUserRetriever() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$$ExternalSyntheticLambda1
            @Override // com.android.server.biometrics.sensors.UserAwareBiometricScheduler.CurrentUserRetriever
            public final int getCurrentUserId() {
                int lambda$getUserAwareBiometricSchedulerForInit$2;
                lambda$getUserAwareBiometricSchedulerForInit$2 = com.android.server.biometrics.sensors.face.aidl.Sensor.this.lambda$getUserAwareBiometricSchedulerForInit$2();
                return lambda$getUserAwareBiometricSchedulerForInit$2;
            }
        }, new com.android.server.biometrics.sensors.face.aidl.Sensor.AnonymousClass2(lockoutResetDispatcher, faceProvider));
    }

    /* renamed from: com.android.server.biometrics.sensors.face.aidl.Sensor$2, reason: invalid class name */
    class AnonymousClass2 implements com.android.server.biometrics.sensors.UserAwareBiometricScheduler.UserSwitchCallback {
        final /* synthetic */ com.android.server.biometrics.sensors.LockoutResetDispatcher val$lockoutResetDispatcher;
        final /* synthetic */ com.android.server.biometrics.sensors.face.aidl.FaceProvider val$provider;

        AnonymousClass2(com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider) {
            this.val$lockoutResetDispatcher = lockoutResetDispatcher;
            this.val$provider = faceProvider;
        }

        @Override // com.android.server.biometrics.sensors.UserAwareBiometricScheduler.UserSwitchCallback
        @android.annotation.NonNull
        public com.android.server.biometrics.sensors.StopUserClient<android.hardware.biometrics.face.ISession> getStopUserClient(int i) {
            return new com.android.server.biometrics.sensors.face.aidl.FaceStopUserClient(com.android.server.biometrics.sensors.face.aidl.Sensor.this.mContext, new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$2$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.hardware.biometrics.face.ISession lambda$getStopUserClient$0;
                    lambda$getStopUserClient$0 = com.android.server.biometrics.sensors.face.aidl.Sensor.AnonymousClass2.this.lambda$getStopUserClient$0();
                    return lambda$getStopUserClient$0;
                }
            }, com.android.server.biometrics.sensors.face.aidl.Sensor.this.mToken, i, com.android.server.biometrics.sensors.face.aidl.Sensor.this.mSensorProperties.sensorId, com.android.server.biometrics.log.BiometricLogger.ofUnknown(com.android.server.biometrics.sensors.face.aidl.Sensor.this.mContext), com.android.server.biometrics.sensors.face.aidl.Sensor.this.mBiometricContext, new com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$2$$ExternalSyntheticLambda2
                @Override // com.android.server.biometrics.sensors.StopUserClient.UserStoppedCallback
                public final void onUserStopped() {
                    com.android.server.biometrics.sensors.face.aidl.Sensor.AnonymousClass2.this.lambda$getStopUserClient$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.hardware.biometrics.face.ISession lambda$getStopUserClient$0() {
            return ((com.android.server.biometrics.sensors.face.aidl.AidlSession) com.android.server.biometrics.sensors.face.aidl.Sensor.this.mLazySession.get()).getSession();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStopUserClient$1() {
            com.android.server.biometrics.sensors.face.aidl.Sensor.this.mCurrentSession = null;
        }

        @Override // com.android.server.biometrics.sensors.UserAwareBiometricScheduler.UserSwitchCallback
        @android.annotation.NonNull
        public com.android.server.biometrics.sensors.StartUserClient<android.hardware.biometrics.face.IFace, android.hardware.biometrics.face.ISession> getStartUserClient(int i) {
            int i2 = com.android.server.biometrics.sensors.face.aidl.Sensor.this.mSensorProperties.sensorId;
            return com.android.server.biometrics.sensors.face.aidl.Sensor.this.getStartUserClient(new com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler(com.android.server.biometrics.sensors.face.aidl.Sensor.this.mContext, com.android.server.biometrics.sensors.face.aidl.Sensor.this.mScheduler, i2, i, com.android.server.biometrics.sensors.face.aidl.Sensor.this.mLockoutTracker, this.val$lockoutResetDispatcher, com.android.server.biometrics.sensors.face.aidl.Sensor.this.mBiometricContext.getAuthSessionCoordinator(), new com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.HardwareUnavailableCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$2$$ExternalSyntheticLambda0
                @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.HardwareUnavailableCallback
                public final void onHardwareUnavailable() {
                    com.android.server.biometrics.sensors.face.aidl.Sensor.AnonymousClass2.this.lambda$getStartUserClient$2();
                }
            }), i2, i, this.val$provider);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getStartUserClient$2() {
            android.util.Slog.e(com.android.server.biometrics.sensors.face.aidl.Sensor.TAG, "Face sensor hardware unavailable.");
            com.android.server.biometrics.sensors.face.aidl.Sensor.this.mCurrentSession = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$getUserAwareBiometricSchedulerForInit$2() {
        return this.mCurrentSession != null ? this.mCurrentSession.getUserId() : com.android.server.am.ProcessList.INVALID_ADJ;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.biometrics.sensors.face.aidl.FaceStartUserClient getStartUserClient(@android.annotation.NonNull final com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler aidlResponseHandler, final int i, int i2, @android.annotation.NonNull final com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider) {
        com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback userStartedCallback = new com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$$ExternalSyntheticLambda3
            @Override // com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback
            public final void onUserStarted(int i3, java.lang.Object obj, int i4) {
                com.android.server.biometrics.sensors.face.aidl.Sensor.this.lambda$getStartUserClient$3(aidlResponseHandler, i, faceProvider, i3, (android.hardware.biometrics.face.ISession) obj, i4);
            }
        };
        android.content.Context context = this.mContext;
        java.util.Objects.requireNonNull(faceProvider);
        return new com.android.server.biometrics.sensors.face.aidl.FaceStartUserClient(context, new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.face.aidl.Sensor$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return com.android.server.biometrics.sensors.face.aidl.FaceProvider.this.getHalInstance();
            }
        }, this.mToken, i2, this.mSensorProperties.sensorId, com.android.server.biometrics.log.BiometricLogger.ofUnknown(this.mContext), this.mBiometricContext, aidlResponseHandler, userStartedCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getStartUserClient$3(com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler aidlResponseHandler, int i, com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider, int i2, android.hardware.biometrics.face.ISession iSession, int i3) {
        android.util.Slog.d(TAG, "New face session created for user: " + i2 + " with hal version: " + i3);
        this.mCurrentSession = new com.android.server.biometrics.sensors.face.aidl.AidlSession(i3, iSession, i2, aidlResponseHandler);
        if (com.android.server.biometrics.sensors.face.FaceUtils.getLegacyInstance(i).isInvalidationInProgress(this.mContext, i2)) {
            android.util.Slog.w(TAG, "Scheduling unfinished invalidation request for face sensor: " + i + ", user: " + i2);
            faceProvider.scheduleInvalidationRequest(i, i2);
        }
    }

    private static android.hardware.face.FaceSensorPropertiesInternal getFaceSensorPropertiesInternal(android.hardware.biometrics.face.SensorProps sensorProps, boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (sensorProps.commonProps.componentInfo != null) {
            for (android.hardware.biometrics.common.ComponentInfo componentInfo : sensorProps.commonProps.componentInfo) {
                arrayList.add(new android.hardware.biometrics.ComponentInfoInternal(componentInfo.componentId, componentInfo.hardwareVersion, componentInfo.firmwareVersion, componentInfo.serialNumber, componentInfo.softwareVersion));
            }
        }
        return new android.hardware.face.FaceSensorPropertiesInternal(sensorProps.commonProps.sensorId, sensorProps.commonProps.sensorStrength, sensorProps.commonProps.maxEnrollmentsPerUser, arrayList, sensorProps.sensorType, sensorProps.supportsDetectInteraction, sensorProps.halControlsPreview, z);
    }

    @android.annotation.NonNull
    public java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> getLazySession() {
        return this.mLazySession;
    }

    @android.annotation.NonNull
    protected android.hardware.face.FaceSensorPropertiesInternal getSensorProperties() {
        return this.mSensorProperties;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    protected com.android.server.biometrics.sensors.face.aidl.AidlSession getSessionForUser(int i) {
        if (this.mCurrentSession != null && this.mCurrentSession.getUserId() == i) {
            return this.mCurrentSession;
        }
        return null;
    }

    @android.annotation.NonNull
    android.hardware.biometrics.ITestSession createTestSession(@android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback) {
        return new com.android.server.biometrics.sensors.face.aidl.BiometricTestSessionImpl(this.mContext, this.mSensorProperties.sensorId, iTestSessionCallback, this.mProvider, this);
    }

    @android.annotation.NonNull
    public com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.face.IFace, android.hardware.biometrics.face.ISession> getScheduler() {
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
    protected java.util.Map<java.lang.Integer, java.lang.Long> getAuthenticatorIds() {
        return this.mAuthenticatorIds;
    }

    void setTestHalEnabled(boolean z) {
        android.util.Slog.w(TAG, "Face setTestHalEnabled: " + z);
        if (z != this.mTestHalEnabled) {
            try {
                if (this.mCurrentSession != null) {
                    android.util.Slog.d(TAG, "Closing old face session");
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
        protoOutputStream.write(1159641169922L, 2);
        protoOutputStream.write(1120986464259L, com.android.server.biometrics.Utils.getCurrentStrength(this.mSensorProperties.sensorId));
        protoOutputStream.write(1146756268036L, this.mScheduler.dumpProtoState(z));
        java.util.Iterator it = android.os.UserManager.get(this.mContext).getUsers().iterator();
        while (it.hasNext()) {
            int identifier = ((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier();
            long start2 = protoOutputStream.start(2246267895813L);
            protoOutputStream.write(1120986464257L, identifier);
            protoOutputStream.write(1120986464258L, com.android.server.biometrics.sensors.face.FaceUtils.getInstance(this.mSensorProperties.sensorId).getBiometricsForUser(this.mContext, identifier).size());
            protoOutputStream.end(start2);
        }
        protoOutputStream.write(1133871366150L, this.mSensorProperties.resetLockoutRequiresHardwareAuthToken);
        protoOutputStream.write(1133871366151L, this.mSensorProperties.resetLockoutRequiresChallenge);
        protoOutputStream.end(start);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onBinderDied() {
        com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient != 0 && currentClient.isInterruptable()) {
            android.util.Slog.e(TAG, "Sending face hardware unavailable error for client: " + currentClient);
            ((com.android.server.biometrics.sensors.ErrorConsumer) currentClient).onError(1, 0);
            com.android.internal.util.FrameworkStatsLog.write(148, 4, 1, -1);
        } else if (currentClient != 0) {
            currentClient.cancel();
        }
        this.mScheduler.recordCrashState();
        this.mScheduler.reset();
        this.mCurrentSession = null;
    }

    protected com.android.server.biometrics.log.BiometricContext getBiometricContext() {
        return this.mBiometricContext;
    }

    protected android.os.Handler getHandler() {
        return this.mHandler;
    }

    protected android.content.Context getContext() {
        return this.mContext;
    }

    public void scheduleFaceUpdateActiveUserClient(int i) {
    }

    public boolean isHardwareDetected(java.lang.String str) {
        if (this.mTestHalEnabled) {
            return true;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.hardware.biometrics.face.IFace.DESCRIPTOR);
        sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
        sb.append(str);
        return android.os.ServiceManager.checkService(sb.toString()) != null;
    }

    public int getLockoutModeForUser(int i) {
        return this.mBiometricContext.getAuthSessionCoordinator().getLockoutStateFor(i, com.android.server.biometrics.Utils.getCurrentStrength(this.mSensorProperties.sensorId));
    }

    public void setScheduler(com.android.server.biometrics.sensors.BiometricScheduler biometricScheduler) {
        this.mScheduler = biometricScheduler;
    }

    public void setLazySession(java.util.function.Supplier<com.android.server.biometrics.sensors.face.aidl.AidlSession> supplier) {
        this.mLazySession = supplier;
    }
}
