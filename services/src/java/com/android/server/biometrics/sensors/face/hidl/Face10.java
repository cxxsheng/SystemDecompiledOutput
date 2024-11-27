package com.android.server.biometrics.sensors.face.hidl;

/* loaded from: classes.dex */
public class Face10 implements android.os.IHwBinder.DeathRecipient, com.android.server.biometrics.sensors.face.ServiceProvider {
    private static final int ENROLL_TIMEOUT_SEC = 75;
    private static final int GENERATE_CHALLENGE_COUNTER_TTL_MILLIS = 600000;
    private static final int GENERATE_CHALLENGE_REUSE_INTERVAL_MILLIS = 60000;
    private static final java.lang.String TAG = "Face10";

    @com.android.internal.annotations.VisibleForTesting
    public static java.time.Clock sSystemClock = java.time.Clock.systemUTC();

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthenticationStateListeners mAuthenticationStateListeners;

    @android.annotation.Nullable
    private com.android.server.biometrics.AuthenticationStatsCollector mAuthenticationStatsCollector;

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.BiometricContext mBiometricContext;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.BiometricStateCallback mBiometricStateCallback;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.Nullable
    private android.hardware.biometrics.face.V1_0.IBiometricsFace mDaemon;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController mHalResultController;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;
    private final com.android.server.biometrics.sensors.LockoutResetDispatcher mLockoutResetDispatcher;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.face.V1_0.IBiometricsFace, com.android.server.biometrics.sensors.face.aidl.AidlSession> mScheduler;
    private final int mSensorId;

    @android.annotation.NonNull
    private final android.hardware.face.FaceSensorPropertiesInternal mSensorProperties;
    private com.android.server.biometrics.sensors.face.aidl.AidlSession mSession;
    private boolean mTestHalEnabled;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.UsageStats mUsageStats;

    @android.annotation.NonNull
    private final java.util.concurrent.atomic.AtomicLong mRequestCounter = new java.util.concurrent.atomic.AtomicLong(0);
    private int mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
    private final java.util.List<java.lang.Long> mGeneratedChallengeCount = new java.util.ArrayList();
    private com.android.server.biometrics.sensors.face.hidl.FaceGenerateChallengeClient mGeneratedChallengeCache = null;
    private final android.app.UserSwitchObserver mUserSwitchObserver = new android.app.SynchronousUserSwitchObserver() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10.1
        public void onUserSwitching(int i) {
            com.android.server.biometrics.sensors.face.hidl.Face10.this.scheduleInternalCleanup(i, null);
            com.android.server.biometrics.sensors.face.hidl.Face10.this.scheduleGetFeature(com.android.server.biometrics.sensors.face.hidl.Face10.this.mSensorId, new android.os.Binder(), i, 1, null, com.android.server.biometrics.sensors.face.hidl.Face10.this.mContext.getOpPackageName());
        }
    };

    @android.annotation.NonNull
    private final java.util.Map<java.lang.Integer, java.lang.Long> mAuthenticatorIds = new java.util.HashMap();

    @android.annotation.NonNull
    private final java.util.function.Supplier<android.hardware.biometrics.face.V1_0.IBiometricsFace> mLazyDaemon = new com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda3(this);

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.LockoutHalImpl mLockoutTracker = new com.android.server.biometrics.sensors.face.LockoutHalImpl();

    public static class HalResultController extends android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback.Stub {

        @android.annotation.Nullable
        private com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController.Callback mCallback;

        @android.annotation.NonNull
        private final android.content.Context mContext;

        @android.annotation.NonNull
        private final android.os.Handler mHandler;

        @android.annotation.NonNull
        private final com.android.server.biometrics.sensors.LockoutResetDispatcher mLockoutResetDispatcher;

        @android.annotation.NonNull
        private final com.android.server.biometrics.sensors.face.LockoutHalImpl mLockoutTracker;

        @android.annotation.NonNull
        private final com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.face.V1_0.IBiometricsFace, com.android.server.biometrics.sensors.face.aidl.AidlSession> mScheduler;
        private final int mSensorId;

        public interface Callback {
            void onHardwareUnavailable();
        }

        HalResultController(int i, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.face.V1_0.IBiometricsFace, com.android.server.biometrics.sensors.face.aidl.AidlSession> biometricScheduler, @android.annotation.NonNull com.android.server.biometrics.sensors.face.LockoutHalImpl lockoutHalImpl, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher) {
            this.mSensorId = i;
            this.mContext = context;
            this.mHandler = handler;
            this.mScheduler = biometricScheduler;
            this.mLockoutTracker = lockoutHalImpl;
            this.mLockoutResetDispatcher = lockoutResetDispatcher;
        }

        public void setCallback(@android.annotation.Nullable com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController.Callback callback) {
            this.mCallback = callback;
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
        public void onEnrollResult(final long j, final int i, final int i2, final int i3) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController.this.lambda$onEnrollResult$0(i2, i, j, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEnrollResult$0(int i, int i2, long j, int i3) {
            android.hardware.biometrics.BiometricAuthenticator.Identifier face = new android.hardware.face.Face(com.android.server.biometrics.sensors.face.FaceUtils.getLegacyInstance(this.mSensorId).getUniqueName(this.mContext, i), i2, j);
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof com.android.server.biometrics.sensors.face.hidl.FaceEnrollClient)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "onEnrollResult for non-enroll client: " + com.android.server.biometrics.Utils.getClientName(currentClient));
                return;
            }
            ((com.android.server.biometrics.sensors.face.hidl.FaceEnrollClient) currentClient).onEnrollResult(face, i3);
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
        public void onAuthenticated(final long j, final int i, int i2, final java.util.ArrayList<java.lang.Byte> arrayList) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController.this.lambda$onAuthenticated$1(i, j, arrayList);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onAuthenticated$1(int i, long j, java.util.ArrayList arrayList) {
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof com.android.server.biometrics.sensors.AuthenticationConsumer)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "onAuthenticated for non-authentication consumer: " + com.android.server.biometrics.Utils.getClientName(currentClient));
                return;
            }
            ((com.android.server.biometrics.sensors.AuthenticationConsumer) currentClient).onAuthenticated(new android.hardware.face.Face("", i, j), i != 0, arrayList);
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
        public void onAcquired(long j, int i, final int i2, final int i3) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController.this.lambda$onAcquired$2(i2, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAcquired$2(int i, int i2) {
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof com.android.server.biometrics.sensors.AcquisitionClient)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "onAcquired for non-acquire client: " + com.android.server.biometrics.Utils.getClientName(currentClient));
                return;
            }
            ((com.android.server.biometrics.sensors.AcquisitionClient) currentClient).onAcquired(i, i2);
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
        public void onError(long j, int i, final int i2, final int i3) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController.this.lambda$onError$3(i2, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onError$3(int i, int i2) {
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("handleError, client: ");
            sb.append(currentClient != 0 ? currentClient.getOwnerString() : null);
            sb.append(", error: ");
            sb.append(i);
            sb.append(", vendorCode: ");
            sb.append(i2);
            android.util.Slog.d(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, sb.toString());
            if (!(currentClient instanceof com.android.server.biometrics.sensors.ErrorConsumer)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "onError for non-error consumer: " + com.android.server.biometrics.Utils.getClientName(currentClient));
                return;
            }
            ((com.android.server.biometrics.sensors.ErrorConsumer) currentClient).onError(i, i2);
            if (i == 1) {
                android.util.Slog.e(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "Got ERROR_HW_UNAVAILABLE");
                if (this.mCallback != null) {
                    this.mCallback.onHardwareUnavailable();
                }
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
        public void onRemoved(final long j, final java.util.ArrayList<java.lang.Integer> arrayList, int i) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController.this.lambda$onRemoved$4(arrayList, j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onRemoved$4(java.util.ArrayList arrayList, long j) {
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof com.android.server.biometrics.sensors.RemovalConsumer)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "onRemoved for non-removal consumer: " + com.android.server.biometrics.Utils.getClientName(currentClient));
                return;
            }
            com.android.server.biometrics.sensors.RemovalConsumer removalConsumer = (com.android.server.biometrics.sensors.RemovalConsumer) currentClient;
            if (!arrayList.isEmpty()) {
                for (int i = 0; i < arrayList.size(); i++) {
                    int intValue = ((java.lang.Integer) arrayList.get(i)).intValue();
                    android.hardware.face.Face face = new android.hardware.face.Face("", intValue, j);
                    int size = (arrayList.size() - i) - 1;
                    android.util.Slog.d(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "Removed, faceId: " + intValue + ", remaining: " + size);
                    removalConsumer.onRemoved(face, size);
                }
            } else {
                removalConsumer.onRemoved(null, 0);
            }
            android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "face_unlock_re_enroll", 0, -2);
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
        public void onEnumerate(final long j, final java.util.ArrayList<java.lang.Integer> arrayList, int i) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController.this.lambda$onEnumerate$5(arrayList, j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onEnumerate$5(java.util.ArrayList arrayList, long j) {
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof com.android.server.biometrics.sensors.EnumerateConsumer)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "onEnumerate for non-enumerate consumer: " + com.android.server.biometrics.Utils.getClientName(currentClient));
                return;
            }
            com.android.server.biometrics.sensors.EnumerateConsumer enumerateConsumer = (com.android.server.biometrics.sensors.EnumerateConsumer) currentClient;
            if (arrayList.isEmpty()) {
                enumerateConsumer.onEnumerationResult(null, 0);
                return;
            }
            for (int i = 0; i < arrayList.size(); i++) {
                enumerateConsumer.onEnumerationResult(new android.hardware.face.Face("", ((java.lang.Integer) arrayList.get(i)).intValue(), j), (arrayList.size() - i) - 1);
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback
        public void onLockoutChanged(final long j) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$HalResultController$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController.this.lambda$onLockoutChanged$6(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLockoutChanged$6(long j) {
            int i;
            android.util.Slog.d(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "onLockoutChanged: " + j);
            if (j == 0) {
                i = 0;
            } else if (j == -1 || j == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                i = 2;
            } else {
                i = 1;
            }
            this.mLockoutTracker.setCurrentUserLockoutMode(i);
            if (j == 0) {
                this.mLockoutResetDispatcher.notifyLockoutResetCallbacks(this.mSensorId);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    Face10(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricStateCallback biometricStateCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, @android.annotation.NonNull android.hardware.face.FaceSensorPropertiesInternal faceSensorPropertiesInternal, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.face.V1_0.IBiometricsFace, com.android.server.biometrics.sensors.face.aidl.AidlSession> biometricScheduler, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        this.mSensorProperties = faceSensorPropertiesInternal;
        this.mContext = context;
        this.mBiometricStateCallback = biometricStateCallback;
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mSensorId = faceSensorPropertiesInternal.sensorId;
        this.mScheduler = biometricScheduler;
        this.mHandler = handler;
        this.mBiometricContext = biometricContext;
        this.mUsageStats = new com.android.server.biometrics.sensors.face.UsageStats(context);
        this.mHalResultController = new com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController(faceSensorPropertiesInternal.sensorId, context, this.mHandler, this.mScheduler, this.mLockoutTracker, lockoutResetDispatcher);
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mHalResultController.setCallback(new com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController.Callback() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda15
            @Override // com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController.Callback
            public final void onHardwareUnavailable() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$new$0();
            }
        });
        new com.android.server.biometrics.AuthenticationStatsBroadcastReceiver(this.mContext, 4, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$new$1((com.android.server.biometrics.AuthenticationStatsCollector) obj);
            }
        });
        try {
            android.app.ActivityManager.getService().registerUserSwitchObserver(this.mUserSwitchObserver, TAG);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to register user switch observer");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mDaemon = null;
        this.mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(com.android.server.biometrics.AuthenticationStatsCollector authenticationStatsCollector) {
        android.util.Slog.d(TAG, "Initializing AuthenticationStatsCollector");
        this.mAuthenticationStatsCollector = authenticationStatsCollector;
    }

    public static com.android.server.biometrics.sensors.face.hidl.Face10 newInstance(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricStateCallback biometricStateCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, @android.annotation.NonNull android.hardware.face.FaceSensorPropertiesInternal faceSensorPropertiesInternal, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher) {
        return new com.android.server.biometrics.sensors.face.hidl.Face10(context, biometricStateCallback, authenticationStateListeners, faceSensorPropertiesInternal, lockoutResetDispatcher, new android.os.Handler(android.os.Looper.getMainLooper()), new com.android.server.biometrics.sensors.BiometricScheduler(1, null), com.android.server.biometrics.log.BiometricContext.getInstance(context));
    }

    public void serviceDied(long j) {
        android.util.Slog.e(TAG, "HAL died");
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$serviceDied$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$serviceDied$2() {
        com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(this.mSensorId).incrementHALDeathCount();
        this.mDaemon = null;
        this.mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
        java.lang.Object currentClient = this.mScheduler.getCurrentClient();
        if (currentClient instanceof com.android.server.biometrics.sensors.ErrorConsumer) {
            android.util.Slog.e(TAG, "Sending ERROR_HW_UNAVAILABLE for client: " + currentClient);
            ((com.android.server.biometrics.sensors.ErrorConsumer) currentClient).onError(1, 0);
            com.android.internal.util.FrameworkStatsLog.write(148, 4, 1, -1);
        }
        this.mScheduler.recordCrashState();
        this.mScheduler.reset();
    }

    public int getCurrentUserId() {
        return this.mCurrentUserId;
    }

    synchronized com.android.server.biometrics.sensors.face.aidl.AidlSession getSession() {
        if (this.mDaemon != null && this.mSession != null) {
            return this.mSession;
        }
        com.android.server.biometrics.sensors.face.aidl.AidlSession aidlSession = new com.android.server.biometrics.sensors.face.aidl.AidlSession(this.mContext, new com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda3(this), this.mCurrentUserId, new com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler(this.mContext, this.mScheduler, this.mSensorId, this.mCurrentUserId, this.mLockoutTracker, this.mLockoutResetDispatcher, new com.android.server.biometrics.sensors.AuthSessionCoordinator(), new com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.HardwareUnavailableCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda4
            @Override // com.android.server.biometrics.sensors.face.aidl.AidlResponseHandler.HardwareUnavailableCallback
            public final void onHardwareUnavailable() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$getSession$3();
            }
        }));
        this.mSession = aidlSession;
        return aidlSession;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSession$3() {
        this.mDaemon = null;
        this.mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized android.hardware.biometrics.face.V1_0.IBiometricsFace getDaemon() {
        long j;
        if (this.mTestHalEnabled) {
            com.android.server.biometrics.sensors.face.hidl.TestHal testHal = new com.android.server.biometrics.sensors.face.hidl.TestHal(this.mContext, this.mSensorId);
            testHal.setCallback(this.mHalResultController);
            return testHal;
        }
        if (this.mDaemon != null) {
            return this.mDaemon;
        }
        android.util.Slog.d(TAG, "Daemon was null, reconnecting, current operation: " + this.mScheduler.getCurrentClient());
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
        try {
            j = this.mDaemon.setCallback(this.mHalResultController).value;
        } catch (android.os.RemoteException e3) {
            android.util.Slog.e(TAG, "Failed to set callback for face HAL", e3);
            this.mDaemon = null;
            j = 0;
        }
        android.util.Slog.d(TAG, "Face HAL ready, HAL ID: " + j);
        if (j != 0) {
            scheduleLoadAuthenticatorIds();
            scheduleInternalCleanup(android.app.ActivityManager.getCurrentUser(), null);
            scheduleGetFeature(this.mSensorId, new android.os.Binder(), android.app.ActivityManager.getCurrentUser(), 1, null, this.mContext.getOpPackageName());
        } else {
            android.util.Slog.e(TAG, "Unable to set callback");
            this.mDaemon = null;
        }
        return this.mDaemon;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public boolean containsSensor(int i) {
        return this.mSensorId == i;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    @android.annotation.NonNull
    public java.util.List<android.hardware.face.FaceSensorPropertiesInternal> getSensorProperties() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(this.mSensorProperties);
        return arrayList;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    @android.annotation.NonNull
    public android.hardware.face.FaceSensorPropertiesInternal getSensorProperties(int i) {
        return this.mSensorProperties;
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    @android.annotation.NonNull
    public java.util.List<android.hardware.face.Face> getEnrolledFaces(int i, int i2) {
        return com.android.server.biometrics.sensors.face.FaceUtils.getLegacyInstance(this.mSensorId).getBiometricsForUser(this.mContext, i2);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public boolean hasEnrollments(int i, int i2) {
        return !getEnrolledFaces(i, i2).isEmpty();
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public int getLockoutModeForUser(int i, int i2) {
        return this.mLockoutTracker.getLockoutModeForUser(i2);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public long getAuthenticatorId(int i, int i2) {
        return this.mAuthenticatorIds.getOrDefault(java.lang.Integer.valueOf(i2), 0L).longValue();
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public boolean isHardwareDetected(int i) {
        return getDaemon() != null;
    }

    private boolean isGeneratedChallengeCacheValid() {
        return this.mGeneratedChallengeCache != null && sSystemClock.millis() - this.mGeneratedChallengeCache.getCreatedAt() < 60000;
    }

    private void incrementChallengeCount() {
        this.mGeneratedChallengeCount.add(0, java.lang.Long.valueOf(sSystemClock.millis()));
    }

    private int decrementChallengeCount() {
        final long millis = sSystemClock.millis();
        this.mGeneratedChallengeCount.removeIf(new java.util.function.Predicate() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$decrementChallengeCount$4;
                lambda$decrementChallengeCount$4 = com.android.server.biometrics.sensors.face.hidl.Face10.lambda$decrementChallengeCount$4(millis, (java.lang.Long) obj);
                return lambda$decrementChallengeCount$4;
            }
        });
        if (!this.mGeneratedChallengeCount.isEmpty()) {
            this.mGeneratedChallengeCount.remove(0);
        }
        return this.mGeneratedChallengeCount.size();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$decrementChallengeCount$4(long j, java.lang.Long l) {
        return j - l.longValue() > 600000;
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleGenerateChallenge(int i, final int i2, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$scheduleGenerateChallenge$5(i2, iBinder, iFaceServiceReceiver, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleGenerateChallenge$5(int i, android.os.IBinder iBinder, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) {
        scheduleUpdateActiveUserWithoutHandler(i);
        com.android.server.biometrics.Flags.deHidl();
        scheduleGenerateChallengeHidl(i, iBinder, iFaceServiceReceiver, str);
    }

    private void scheduleGenerateChallengeAidl(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str) {
        final com.android.server.biometrics.sensors.face.aidl.FaceGenerateChallengeClient faceGenerateChallengeClient = new com.android.server.biometrics.sensors.face.aidl.FaceGenerateChallengeClient(this.mContext, new com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda6(this), iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), i, str, this.mSensorId, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext);
        this.mScheduler.scheduleClientMonitor(faceGenerateChallengeClient, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10.2
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
                if (faceGenerateChallengeClient != baseClientMonitor) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "scheduleGenerateChallenge onClientStarted, mismatched client. Expecting: " + faceGenerateChallengeClient + ", received: " + baseClientMonitor);
                }
            }
        });
    }

    private void scheduleGenerateChallengeHidl(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str) {
        incrementChallengeCount();
        if (isGeneratedChallengeCacheValid()) {
            android.util.Slog.d(TAG, "Current challenge is cached and will be reused");
            this.mGeneratedChallengeCache.reuseResult(iFaceServiceReceiver);
        } else {
            final com.android.server.biometrics.sensors.face.hidl.FaceGenerateChallengeClient faceGenerateChallengeClient = new com.android.server.biometrics.sensors.face.hidl.FaceGenerateChallengeClient(this.mContext, this.mLazyDaemon, iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), i, str, this.mSensorId, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, sSystemClock.millis());
            this.mGeneratedChallengeCache = faceGenerateChallengeClient;
            this.mScheduler.scheduleClientMonitor(faceGenerateChallengeClient, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10.3
                @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
                    if (faceGenerateChallengeClient != baseClientMonitor) {
                        android.util.Slog.e(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "scheduleGenerateChallenge onClientStarted, mismatched client. Expecting: " + faceGenerateChallengeClient + ", received: " + baseClientMonitor);
                    }
                }
            });
        }
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleRevokeChallenge(int i, final int i2, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final java.lang.String str, long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$scheduleRevokeChallenge$6(i2, iBinder, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRevokeChallenge$6(int i, android.os.IBinder iBinder, java.lang.String str) {
        com.android.server.biometrics.Flags.deHidl();
        scheduleRevokeChallengeHidl(i, iBinder, str);
    }

    private void scheduleRevokeChallengeAidl(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.lang.String str) {
        final com.android.server.biometrics.sensors.face.aidl.FaceRevokeChallengeClient faceRevokeChallengeClient = new com.android.server.biometrics.sensors.face.aidl.FaceRevokeChallengeClient(this.mContext, new com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda6(this), iBinder, i, str, this.mSensorId, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, 0L);
        this.mScheduler.scheduleClientMonitor(faceRevokeChallengeClient, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10.4
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                if (faceRevokeChallengeClient != baseClientMonitor) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "scheduleRevokeChallenge, mismatched client.Expecting: " + faceRevokeChallengeClient + ", received: " + baseClientMonitor);
                }
            }
        });
    }

    private void scheduleRevokeChallengeHidl(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.lang.String str) {
        if (decrementChallengeCount() == 0) {
            android.util.Slog.d(TAG, "scheduleRevokeChallenge executing - no active clients");
            this.mGeneratedChallengeCache = null;
            final com.android.server.biometrics.sensors.face.hidl.FaceRevokeChallengeClient faceRevokeChallengeClient = new com.android.server.biometrics.sensors.face.hidl.FaceRevokeChallengeClient(this.mContext, this.mLazyDaemon, iBinder, i, str, this.mSensorId, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext);
            this.mScheduler.scheduleClientMonitor(faceRevokeChallengeClient, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10.5
                @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
                public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                    if (faceRevokeChallengeClient != baseClientMonitor) {
                        android.util.Slog.e(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "scheduleRevokeChallenge, mismatched client.Expecting: " + faceRevokeChallengeClient + ", received: " + baseClientMonitor);
                    }
                }
            });
            return;
        }
        android.util.Slog.w(TAG, "scheduleRevokeChallenge skipped - challenge still in use: " + this.mGeneratedChallengeCount);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public long scheduleEnroll(int i, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final byte[] bArr, final int i2, @android.annotation.NonNull final android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final int[] iArr, @android.annotation.Nullable final android.view.Surface surface, boolean z, @android.annotation.NonNull final android.hardware.face.FaceEnrollOptions faceEnrollOptions) {
        final long incrementAndGet = this.mRequestCounter.incrementAndGet();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$scheduleEnroll$7(i2, iBinder, bArr, iFaceServiceReceiver, str, iArr, surface, incrementAndGet, faceEnrollOptions);
            }
        });
        return incrementAndGet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleEnroll$7(int i, android.os.IBinder iBinder, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str, int[] iArr, android.view.Surface surface, long j, android.hardware.face.FaceEnrollOptions faceEnrollOptions) {
        scheduleUpdateActiveUserWithoutHandler(i);
        com.android.server.biometrics.Flags.deHidl();
        scheduleEnrollHidl(iBinder, bArr, i, iFaceServiceReceiver, str, iArr, surface, j, faceEnrollOptions);
    }

    private void scheduleEnrollAidl(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull byte[] bArr, int i, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull int[] iArr, @android.annotation.Nullable android.view.Surface surface, long j, @android.annotation.NonNull android.hardware.face.FaceEnrollOptions faceEnrollOptions) {
        final com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient faceEnrollClient = new com.android.server.biometrics.sensors.face.aidl.FaceEnrollClient(this.mContext, new com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda6(this), iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), i, bArr, str, j, com.android.server.biometrics.sensors.face.FaceUtils.getLegacyInstance(this.mSensorId), iArr, 75, surface, this.mSensorId, createLogger(1, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mContext.getResources().getInteger(android.R.integer.config_externalDisplayPeakRefreshRate), false, faceEnrollOptions);
        this.mScheduler.scheduleClientMonitor(faceEnrollClient, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10.6
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.mBiometricStateCallback.onClientStarted(baseClientMonitor);
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onBiometricAction(int i2) {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.mBiometricStateCallback.onBiometricAction(i2);
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.mBiometricStateCallback.onClientFinished(baseClientMonitor, z);
                if (z) {
                    com.android.server.biometrics.sensors.face.hidl.Face10.this.scheduleUpdateActiveUserWithoutHandler(faceEnrollClient.getTargetUserId());
                }
            }
        });
    }

    private void scheduleEnrollHidl(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull byte[] bArr, int i, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull int[] iArr, @android.annotation.Nullable android.view.Surface surface, long j, android.hardware.face.FaceEnrollOptions faceEnrollOptions) {
        final com.android.server.biometrics.sensors.face.hidl.FaceEnrollClient faceEnrollClient = new com.android.server.biometrics.sensors.face.hidl.FaceEnrollClient(this.mContext, this.mLazyDaemon, iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), i, bArr, str, j, com.android.server.biometrics.sensors.face.FaceUtils.getLegacyInstance(this.mSensorId), iArr, 75, surface, this.mSensorId, createLogger(1, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, faceEnrollOptions);
        this.mScheduler.scheduleClientMonitor(faceEnrollClient, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10.7
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.mBiometricStateCallback.onClientStarted(baseClientMonitor);
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onBiometricAction(int i2) {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.mBiometricStateCallback.onBiometricAction(i2);
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.mBiometricStateCallback.onClientFinished(baseClientMonitor, z);
                if (z) {
                    com.android.server.biometrics.sensors.face.hidl.Face10.this.scheduleUpdateActiveUserWithoutHandler(faceEnrollClient.getTargetUserId());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelEnrollment$8(android.os.IBinder iBinder, long j) {
        this.mScheduler.cancelEnrollment(iBinder, j);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void cancelEnrollment(int i, @android.annotation.NonNull final android.os.IBinder iBinder, final long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$cancelEnrollment$8(iBinder, j);
            }
        });
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public long scheduleFaceDetect(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, int i) {
        throw new java.lang.IllegalStateException("Face detect not supported by IBiometricsFace@1.0. Did youforget to check the supportsFaceDetection flag?");
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void cancelFaceDetect(int i, @android.annotation.NonNull android.os.IBinder iBinder, long j) {
        throw new java.lang.IllegalStateException("Face detect not supported by IBiometricsFace@1.0. Did youforget to check the supportsFaceDetection flag?");
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleAuthenticate(@android.annotation.NonNull final android.os.IBinder iBinder, final long j, final int i, @android.annotation.NonNull final com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull final android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, final long j2, final boolean z, final int i2, final boolean z2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$scheduleAuthenticate$9(faceAuthenticateOptions, iBinder, j, i, clientMonitorCallbackConverter, j2, z, i2, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleAuthenticate$9(android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, android.os.IBinder iBinder, long j, int i, com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j2, boolean z, int i2, boolean z2) {
        scheduleUpdateActiveUserWithoutHandler(faceAuthenticateOptions.getUserId());
        boolean isStrongBiometric = com.android.server.biometrics.Utils.isStrongBiometric(this.mSensorId);
        com.android.server.biometrics.Flags.deHidl();
        scheduleAuthenticateHidl(iBinder, j, i, clientMonitorCallbackConverter, faceAuthenticateOptions, j2, z, i2, z2, isStrongBiometric);
    }

    private void scheduleAuthenticateAidl(@android.annotation.NonNull android.os.IBinder iBinder, long j, int i, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, long j2, boolean z, int i2, boolean z2, boolean z3) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.face.aidl.FaceAuthenticationClient(this.mContext, new com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda6(this), iBinder, j2, clientMonitorCallbackConverter, j, z, faceAuthenticateOptions, i, false, createLogger(2, i2, this.mAuthenticationStatsCollector), this.mBiometricContext, z3, this.mUsageStats, this.mLockoutTracker, z2, com.android.server.biometrics.Utils.getCurrentStrength(this.mSensorId), this.mAuthenticationStateListeners));
    }

    private void scheduleAuthenticateHidl(@android.annotation.NonNull android.os.IBinder iBinder, long j, int i, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, long j2, boolean z, int i2, boolean z2, boolean z3) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.face.hidl.FaceAuthenticationClient(this.mContext, this.mLazyDaemon, iBinder, j2, clientMonitorCallbackConverter, j, z, faceAuthenticateOptions, i, false, createLogger(2, i2, this.mAuthenticationStatsCollector), this.mBiometricContext, z3, this.mLockoutTracker, this.mUsageStats, z2, com.android.server.biometrics.Utils.getCurrentStrength(this.mSensorId), this.mAuthenticationStateListeners));
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public long scheduleAuthenticate(@android.annotation.NonNull android.os.IBinder iBinder, long j, int i, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions, boolean z, int i2, boolean z2) {
        long incrementAndGet = this.mRequestCounter.incrementAndGet();
        scheduleAuthenticate(iBinder, j, i, clientMonitorCallbackConverter, faceAuthenticateOptions, incrementAndGet, z, i2, z2);
        return incrementAndGet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelAuthentication$10(android.os.IBinder iBinder, long j) {
        this.mScheduler.cancelAuthenticationOrDetection(iBinder, j);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void cancelAuthentication(int i, @android.annotation.NonNull final android.os.IBinder iBinder, final long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$cancelAuthentication$10(iBinder, j);
            }
        });
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleRemove(int i, @android.annotation.NonNull final android.os.IBinder iBinder, final int i2, final int i3, @android.annotation.NonNull final android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$scheduleRemove$11(i3, iBinder, iFaceServiceReceiver, str, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRemove$11(int i, android.os.IBinder iBinder, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str, int i2) {
        scheduleUpdateActiveUserWithoutHandler(i);
        com.android.server.biometrics.Flags.deHidl();
        scheduleRemoveHidl(iBinder, i, iFaceServiceReceiver, str, i2);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleRemoveAll(int i, @android.annotation.NonNull final android.os.IBinder iBinder, final int i2, @android.annotation.NonNull final android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$scheduleRemoveAll$12(i2, iBinder, iFaceServiceReceiver, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRemoveAll$12(int i, android.os.IBinder iBinder, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) {
        scheduleUpdateActiveUserWithoutHandler(i);
        com.android.server.biometrics.Flags.deHidl();
        scheduleRemoveHidl(iBinder, i, iFaceServiceReceiver, str, 0);
    }

    private void scheduleRemoveAidl(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str, int i2) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.face.aidl.FaceRemovalClient(this.mContext, new com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda6(this), iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), new int[]{i2}, i, str, com.android.server.biometrics.sensors.face.FaceUtils.getLegacyInstance(this.mSensorId), this.mSensorId, createLogger(4, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mAuthenticatorIds), this.mBiometricStateCallback);
    }

    private void scheduleRemoveHidl(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str, int i2) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.face.hidl.FaceRemovalClient(this.mContext, this.mLazyDaemon, iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), i2, i, str, com.android.server.biometrics.sensors.face.FaceUtils.getLegacyInstance(this.mSensorId), this.mSensorId, createLogger(4, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mAuthenticatorIds), this.mBiometricStateCallback);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleResetLockout(final int i, final int i2, @android.annotation.NonNull final byte[] bArr) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$scheduleResetLockout$13(i, i2, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleResetLockout$13(int i, int i2, byte[] bArr) {
        if (getEnrolledFaces(i, i2).isEmpty()) {
            android.util.Slog.w(TAG, "Ignoring lockout reset, no templates enrolled for user: " + i2);
            return;
        }
        scheduleUpdateActiveUserWithoutHandler(i2);
        com.android.server.biometrics.Flags.deHidl();
        scheduleResetLockoutHidl(i2, bArr);
    }

    private void scheduleResetLockoutAidl(int i, @android.annotation.NonNull byte[] bArr) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.face.aidl.FaceResetLockoutClient(this.mContext, new com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda6(this), i, this.mContext.getOpPackageName(), this.mSensorId, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, bArr, this.mLockoutTracker, this.mLockoutResetDispatcher, this.mSensorProperties.sensorStrength));
    }

    private void scheduleResetLockoutHidl(int i, @android.annotation.NonNull byte[] bArr) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.face.hidl.FaceResetLockoutClient(this.mContext, this.mLazyDaemon, i, this.mContext.getOpPackageName(), this.mSensorId, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, bArr));
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleSetFeature(final int i, @android.annotation.NonNull final android.os.IBinder iBinder, final int i2, final int i3, final boolean z, @android.annotation.NonNull final byte[] bArr, @android.annotation.NonNull final android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$scheduleSetFeature$14(i2, i, iBinder, i3, z, bArr, iFaceServiceReceiver, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleSetFeature$14(int i, int i2, android.os.IBinder iBinder, int i3, boolean z, byte[] bArr, android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, java.lang.String str) {
        scheduleUpdateActiveUserWithoutHandler(i);
        com.android.server.biometrics.Flags.deHidl();
        scheduleSetFeatureHidl(i2, iBinder, i, i3, z, bArr, iFaceServiceReceiver, str);
    }

    private void scheduleSetFeatureHidl(int i, @android.annotation.NonNull android.os.IBinder iBinder, int i2, int i3, boolean z, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str) {
        java.util.List<android.hardware.face.Face> enrolledFaces = getEnrolledFaces(i, i2);
        if (enrolledFaces.isEmpty()) {
            android.util.Slog.w(TAG, "Ignoring setFeature, no templates enrolled for user: " + i2);
            return;
        }
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.face.hidl.FaceSetFeatureClient(this.mContext, this.mLazyDaemon, iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), i2, str, this.mSensorId, com.android.server.biometrics.log.BiometricLogger.ofUnknown(this.mContext), this.mBiometricContext, i3, z, bArr, enrolledFaces.get(0).getBiometricId()));
    }

    private void scheduleSetFeatureAidl(int i, @android.annotation.NonNull android.os.IBinder iBinder, int i2, int i3, boolean z, @android.annotation.NonNull byte[] bArr, @android.annotation.NonNull android.hardware.face.IFaceServiceReceiver iFaceServiceReceiver, @android.annotation.NonNull java.lang.String str) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.face.aidl.FaceSetFeatureClient(this.mContext, new com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda6(this), iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFaceServiceReceiver), i2, str, this.mSensorId, com.android.server.biometrics.log.BiometricLogger.ofUnknown(this.mContext), this.mBiometricContext, i3, z, bArr));
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleGetFeature(final int i, @android.annotation.NonNull final android.os.IBinder iBinder, final int i2, final int i3, @android.annotation.Nullable final com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$scheduleGetFeature$15(i2, iBinder, i3, clientMonitorCallbackConverter, str, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleGetFeature$15(int i, android.os.IBinder iBinder, int i2, com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, java.lang.String str, int i3) {
        scheduleUpdateActiveUserWithoutHandler(i);
        com.android.server.biometrics.Flags.deHidl();
        scheduleGetFeatureHidl(i3, iBinder, i, i2, clientMonitorCallbackConverter, str);
    }

    private void scheduleGetFeatureHidl(int i, @android.annotation.NonNull android.os.IBinder iBinder, final int i2, final int i3, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull java.lang.String str) {
        java.util.List<android.hardware.face.Face> enrolledFaces = getEnrolledFaces(i, i2);
        if (enrolledFaces.isEmpty()) {
            android.util.Slog.w(TAG, "Ignoring getFeature, no templates enrolled for user: " + i2);
            return;
        }
        final com.android.server.biometrics.sensors.face.hidl.FaceGetFeatureClient faceGetFeatureClient = new com.android.server.biometrics.sensors.face.hidl.FaceGetFeatureClient(this.mContext, this.mLazyDaemon, iBinder, clientMonitorCallbackConverter, i2, str, this.mSensorId, com.android.server.biometrics.log.BiometricLogger.ofUnknown(this.mContext), this.mBiometricContext, i3, enrolledFaces.get(0).getBiometricId());
        this.mScheduler.scheduleClientMonitor(faceGetFeatureClient, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10.8
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                if (z && i3 == 1) {
                    boolean value = faceGetFeatureClient.getValue();
                    android.util.Slog.d(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "Updating attention value for user: " + i2 + " to value: " + (value ? 1 : 0));
                    android.provider.Settings.Secure.putIntForUser(com.android.server.biometrics.sensors.face.hidl.Face10.this.mContext.getContentResolver(), "face_unlock_attention_required", value ? 1 : 0, i2);
                }
            }
        });
    }

    private void scheduleGetFeatureAidl(@android.annotation.NonNull android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull java.lang.String str) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.face.aidl.FaceGetFeatureClient(this.mContext, new com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda6(this), iBinder, clientMonitorCallbackConverter, i, str, this.mSensorId, com.android.server.biometrics.log.BiometricLogger.ofUnknown(this.mContext), this.mBiometricContext, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleInternalCleanup(final int i, @android.annotation.Nullable final com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$scheduleInternalCleanup$16(i, clientMonitorCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleInternalCleanup$16(int i, com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        scheduleUpdateActiveUserWithoutHandler(i);
        com.android.server.biometrics.Flags.deHidl();
        scheduleInternalCleanupHidl(i, clientMonitorCallback);
    }

    private void scheduleInternalCleanupHidl(int i, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.face.hidl.FaceInternalCleanupClient(this.mContext, this.mLazyDaemon, i, this.mContext.getOpPackageName(), this.mSensorId, createLogger(3, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, com.android.server.biometrics.sensors.face.FaceUtils.getLegacyInstance(this.mSensorId), this.mAuthenticatorIds), new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(clientMonitorCallback, this.mBiometricStateCallback));
    }

    private void scheduleInternalCleanupAidl(int i, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.face.aidl.FaceInternalCleanupClient(this.mContext, new com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda6(this), i, this.mContext.getOpPackageName(), this.mSensorId, createLogger(3, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, com.android.server.biometrics.sensors.face.FaceUtils.getLegacyInstance(this.mSensorId), this.mAuthenticatorIds), new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(clientMonitorCallback, this.mBiometricStateCallback));
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleInternalCleanup(int i, int i2, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        scheduleInternalCleanup(i2, this.mBiometricStateCallback);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void scheduleInternalCleanup(int i, int i2, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback, boolean z) {
        scheduleInternalCleanup(i2, clientMonitorCallback);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void startPreparedClient(int i, final int i2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$startPreparedClient$17(i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startPreparedClient$17(int i) {
        this.mScheduler.startPreparedClient(i);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public void dumpProtoState(int i, android.util.proto.ProtoOutputStream protoOutputStream, boolean z) {
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
            protoOutputStream.write(1120986464258L, com.android.server.biometrics.sensors.face.FaceUtils.getLegacyInstance(this.mSensorId).getBiometricsForUser(this.mContext, identifier).size());
            protoOutputStream.end(start2);
        }
        protoOutputStream.write(1133871366150L, this.mSensorProperties.resetLockoutRequiresHardwareAuthToken);
        protoOutputStream.write(1133871366151L, this.mSensorProperties.resetLockoutRequiresChallenge);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public void dumpProtoMetrics(int i, java.io.FileDescriptor fileDescriptor) {
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public void dumpInternal(int i, java.io.PrintWriter printWriter) {
        com.android.server.biometrics.sensors.PerformanceTracker instanceForSensorId = com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(this.mSensorId);
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        try {
            jSONObject.put(com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE, TAG);
            org.json.JSONArray jSONArray = new org.json.JSONArray();
            java.util.Iterator it = android.os.UserManager.get(this.mContext).getUsers().iterator();
            while (it.hasNext()) {
                int identifier = ((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier();
                int size = com.android.server.biometrics.sensors.face.FaceUtils.getLegacyInstance(this.mSensorId).getBiometricsForUser(this.mContext, identifier).size();
                org.json.JSONObject jSONObject2 = new org.json.JSONObject();
                jSONObject2.put("id", identifier);
                jSONObject2.put(com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_COUNT, size);
                jSONObject2.put("accept", instanceForSensorId.getAcceptForUser(identifier));
                jSONObject2.put("reject", instanceForSensorId.getRejectForUser(identifier));
                jSONObject2.put("acquire", instanceForSensorId.getAcquireForUser(identifier));
                jSONObject2.put("lockout", instanceForSensorId.getTimedLockoutForUser(identifier));
                jSONObject2.put("permanentLockout", instanceForSensorId.getPermanentLockoutForUser(identifier));
                jSONObject2.put("acceptCrypto", instanceForSensorId.getAcceptCryptoForUser(identifier));
                jSONObject2.put("rejectCrypto", instanceForSensorId.getRejectCryptoForUser(identifier));
                jSONObject2.put("acquireCrypto", instanceForSensorId.getAcquireCryptoForUser(identifier));
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("prints", jSONArray);
        } catch (org.json.JSONException e) {
            android.util.Slog.e(TAG, "dump formatting failure", e);
        }
        printWriter.println(jSONObject);
        printWriter.println("HAL deaths since last reboot: " + instanceForSensorId.getHALDeathCount());
        this.mScheduler.dump(printWriter);
        this.mUsageStats.print(printWriter);
    }

    private void scheduleLoadAuthenticatorIds() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.face.hidl.Face10.this.lambda$scheduleLoadAuthenticatorIds$18();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleLoadAuthenticatorIds$18() {
        java.util.Iterator it = android.os.UserManager.get(this.mContext).getAliveUsers().iterator();
        while (it.hasNext()) {
            int i = ((android.content.pm.UserInfo) it.next()).id;
            if (!this.mAuthenticatorIds.containsKey(java.lang.Integer.valueOf(i))) {
                scheduleUpdateActiveUserWithoutHandler(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleUpdateActiveUserWithoutHandler(final int i) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.face.hidl.FaceUpdateActiveUserClient(this.mContext, this.mLazyDaemon, i, this.mContext.getOpPackageName(), this.mSensorId, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, !getEnrolledFaces(this.mSensorId, i).isEmpty(), this.mAuthenticatorIds), new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.Face10.9
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                if (z) {
                    if (com.android.server.biometrics.sensors.face.hidl.Face10.this.mCurrentUserId != i) {
                        com.android.server.biometrics.sensors.face.hidl.Face10.this.mSession = null;
                    }
                    com.android.server.biometrics.sensors.face.hidl.Face10.this.mCurrentUserId = i;
                    return;
                }
                android.util.Slog.w(com.android.server.biometrics.sensors.face.hidl.Face10.TAG, "Failed to change user, still: " + com.android.server.biometrics.sensors.face.hidl.Face10.this.mCurrentUserId);
            }
        });
    }

    private com.android.server.biometrics.log.BiometricLogger createLogger(int i, int i2, com.android.server.biometrics.AuthenticationStatsCollector authenticationStatsCollector) {
        return new com.android.server.biometrics.log.BiometricLogger(this.mContext, 4, i, i2, authenticationStatsCollector);
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    public void dumpHal(int i, @android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.lang.String[] strArr) {
        android.hardware.biometrics.face.V1_0.IBiometricsFace daemon;
        if ((!android.os.Build.IS_ENG && !android.os.Build.IS_USERDEBUG) || android.os.SystemProperties.getBoolean("ro.face.disable_debug_data", false) || android.os.SystemProperties.getBoolean("persist.face.disable_debug_data", false) || (daemon = getDaemon()) == null) {
            return;
        }
        java.io.FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    java.io.FileOutputStream fileOutputStream2 = new java.io.FileOutputStream("/dev/null");
                    try {
                        daemon.debug(new android.os.NativeHandle(new java.io.FileDescriptor[]{fileOutputStream2.getFD(), fileDescriptor}, new int[0], false), new java.util.ArrayList<>(java.util.Arrays.asList(strArr)));
                        fileOutputStream2.close();
                    } catch (android.os.RemoteException | java.io.IOException e) {
                        e = e;
                        fileOutputStream = fileOutputStream2;
                        android.util.Slog.d(TAG, "error while reading face debugging data", e);
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (java.lang.Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (java.io.IOException e2) {
                            }
                        }
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                }
            } catch (android.os.RemoteException | java.io.IOException e3) {
                e = e3;
            }
        } catch (java.io.IOException e4) {
        }
    }

    void setTestHalEnabled(boolean z) {
        this.mTestHalEnabled = z;
    }

    @Override // com.android.server.biometrics.sensors.face.ServiceProvider
    @android.annotation.NonNull
    public android.hardware.biometrics.ITestSession createTestSession(int i, @android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull java.lang.String str) {
        return new com.android.server.biometrics.sensors.face.hidl.BiometricTestSessionImpl(this.mContext, this.mSensorId, iTestSessionCallback, this, this.mHalResultController);
    }
}
