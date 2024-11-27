package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
public class Fingerprint21 implements android.os.IHwBinder.DeathRecipient, com.android.server.biometrics.sensors.fingerprint.ServiceProvider {
    private static final int ENROLL_TIMEOUT_SEC = 60;
    private static final java.lang.String TAG = "Fingerprint21";
    private final android.app.ActivityTaskManager mActivityTaskManager;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.AuthenticationStateListeners mAuthenticationStateListeners;

    @android.annotation.Nullable
    private com.android.server.biometrics.AuthenticationStatsCollector mAuthenticationStatsCollector;
    private final java.util.Map<java.lang.Integer, java.lang.Long> mAuthenticatorIds;

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.BiometricContext mBiometricContext;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.BiometricStateCallback mBiometricStateCallback;
    private boolean mCleanup;
    final android.content.Context mContext;

    @android.annotation.Nullable
    private android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint mDaemon;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController mHalResultController;
    private final android.os.Handler mHandler;
    private final boolean mIsPowerbuttonFps;
    private final boolean mIsUdfps;
    private final java.util.function.Supplier<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint> mLazyDaemon;
    private final com.android.server.biometrics.sensors.LockoutResetDispatcher mLockoutResetDispatcher;
    private final com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl mLockoutTracker;
    private final com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> mScheduler;
    private final int mSensorId;

    @android.annotation.NonNull
    private final android.hardware.fingerprint.FingerprintSensorPropertiesInternal mSensorProperties;
    private com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession mSession;

    @android.annotation.Nullable
    private android.hardware.fingerprint.ISidefpsController mSidefpsController;
    private final com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.BiometricTaskStackListener mTaskStackListener;
    private boolean mTestHalEnabled;

    @android.annotation.Nullable
    private android.hardware.fingerprint.IUdfpsOverlayController mUdfpsOverlayController;

    @android.annotation.NonNull
    private final java.util.concurrent.atomic.AtomicLong mRequestCounter = new java.util.concurrent.atomic.AtomicLong(0);
    private int mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
    private final com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.LockoutResetCallback mLockoutResetCallback = new com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.LockoutResetCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.1
        @Override // com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl.LockoutResetCallback
        public void onLockoutReset(int i) {
            com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mLockoutResetDispatcher.notifyLockoutResetCallbacks(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mSensorProperties.sensorId);
        }
    };
    private final android.app.UserSwitchObserver mUserSwitchObserver = new android.app.SynchronousUserSwitchObserver() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.2
        public void onUserSwitching(int i) {
            com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.scheduleInternalCleanup(i, null);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    final class BiometricTaskStackListener extends android.app.TaskStackListener {
        private BiometricTaskStackListener() {
        }

        public void onTaskStackChanged() {
            com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$BiometricTaskStackListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.BiometricTaskStackListener.this.lambda$onTaskStackChanged$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskStackChanged$0() {
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof com.android.server.biometrics.sensors.AuthenticationClient)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.TAG, "Task stack changed for client: " + currentClient);
                return;
            }
            if (!com.android.server.biometrics.Utils.isKeyguard(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mContext, currentClient.getOwnerString()) && !com.android.server.biometrics.Utils.isSystem(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mContext, currentClient.getOwnerString()) && com.android.server.biometrics.Utils.isBackground(currentClient.getOwnerString()) && !currentClient.isAlreadyDone()) {
                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.TAG, "Stopping background authentication, currentClient: " + currentClient);
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mScheduler.cancelAuthenticationOrDetection(currentClient.getToken(), currentClient.getRequestId());
            }
        }
    }

    public static class HalResultController extends android.hardware.biometrics.fingerprint.V2_2.IBiometricsFingerprintClientCallback.Stub {

        @android.annotation.Nullable
        private com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController.Callback mCallback;

        @android.annotation.NonNull
        private final android.content.Context mContext;

        @android.annotation.NonNull
        final android.os.Handler mHandler;

        @android.annotation.NonNull
        final com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> mScheduler;
        private final int mSensorId;

        public interface Callback {
            void onHardwareUnavailable();
        }

        HalResultController(int i, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> biometricScheduler) {
            this.mSensorId = i;
            this.mContext = context;
            this.mHandler = handler;
            this.mScheduler = biometricScheduler;
        }

        public void setCallback(@android.annotation.Nullable com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController.Callback callback) {
            this.mCallback = callback;
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
        public void onEnrollResult(final long j, final int i, final int i2, final int i3) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$HalResultController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController.this.lambda$onEnrollResult$0(i2, i, j, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEnrollResult$0(int i, int i2, long j, int i3) {
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintEnrollClient)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.TAG, "onEnrollResult for non-enroll client: " + com.android.server.biometrics.Utils.getClientName(currentClient));
                return;
            }
            ((com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintEnrollClient) currentClient).onEnrollResult(new android.hardware.fingerprint.Fingerprint(com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getLegacyInstance(this.mSensorId).getUniqueName(this.mContext, currentClient.getTargetUserId()), i, i2, j), i3);
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
        public void onAcquired(long j, int i, int i2) {
            onAcquired_2_2(j, i, i2);
        }

        @Override // android.hardware.biometrics.fingerprint.V2_2.IBiometricsFingerprintClientCallback
        public void onAcquired_2_2(long j, final int i, final int i2) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$HalResultController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController.this.lambda$onAcquired_2_2$1(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAcquired_2_2$1(int i, int i2) {
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof com.android.server.biometrics.sensors.AcquisitionClient)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.TAG, "onAcquired for non-acquisition client: " + com.android.server.biometrics.Utils.getClientName(currentClient));
                return;
            }
            ((com.android.server.biometrics.sensors.AcquisitionClient) currentClient).onAcquired(i, i2);
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
        public void onAuthenticated(final long j, final int i, final int i2, final java.util.ArrayList<java.lang.Byte> arrayList) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$HalResultController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController.this.lambda$onAuthenticated$2(i, i2, j, arrayList);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onAuthenticated$2(int i, int i2, long j, java.util.ArrayList arrayList) {
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof com.android.server.biometrics.sensors.AuthenticationConsumer)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.TAG, "onAuthenticated for non-authentication consumer: " + com.android.server.biometrics.Utils.getClientName(currentClient));
                return;
            }
            ((com.android.server.biometrics.sensors.AuthenticationConsumer) currentClient).onAuthenticated(new android.hardware.fingerprint.Fingerprint("", i2, i, j), i != 0, arrayList);
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
        public void onError(long j, final int i, final int i2) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$HalResultController$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController.this.lambda$onError$3(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onError$3(int i, int i2) {
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            android.util.Slog.d(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.TAG, "handleError, client: " + com.android.server.biometrics.Utils.getClientName(currentClient) + ", error: " + i + ", vendorCode: " + i2);
            if (!(currentClient instanceof com.android.server.biometrics.sensors.ErrorConsumer)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.TAG, "onError for non-error consumer: " + com.android.server.biometrics.Utils.getClientName(currentClient));
                return;
            }
            ((com.android.server.biometrics.sensors.ErrorConsumer) currentClient).onError(i, i2);
            if (i == 1) {
                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.TAG, "Got ERROR_HW_UNAVAILABLE");
                if (this.mCallback != null) {
                    this.mCallback.onHardwareUnavailable();
                }
            }
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
        public void onRemoved(final long j, final int i, final int i2, final int i3) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$HalResultController$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController.this.lambda$onRemoved$4(i, i3, i2, j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onRemoved$4(int i, int i2, int i3, long j) {
            android.util.Slog.d(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.TAG, "Removed, fingerId: " + i + ", remaining: " + i2);
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof com.android.server.biometrics.sensors.RemovalConsumer)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.TAG, "onRemoved for non-removal consumer: " + com.android.server.biometrics.Utils.getClientName(currentClient));
                return;
            }
            ((com.android.server.biometrics.sensors.RemovalConsumer) currentClient).onRemoved(new android.hardware.fingerprint.Fingerprint("", i3, i, j), i2);
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
        public void onEnumerate(final long j, final int i, final int i2, final int i3) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$HalResultController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController.this.lambda$onEnumerate$5(i2, i, j, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$onEnumerate$5(int i, int i2, long j, int i3) {
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof com.android.server.biometrics.sensors.EnumerateConsumer)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.TAG, "onEnumerate for non-enumerate consumer: " + com.android.server.biometrics.Utils.getClientName(currentClient));
                return;
            }
            ((com.android.server.biometrics.sensors.EnumerateConsumer) currentClient).onEnumerationResult(new android.hardware.fingerprint.Fingerprint("", i, i2, j), i3);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    Fingerprint21(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricStateCallback biometricStateCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, @android.annotation.NonNull android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricScheduler<android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> biometricScheduler, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController halResultController, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        this.mContext = context;
        this.mBiometricStateCallback = biometricStateCallback;
        this.mAuthenticationStateListeners = authenticationStateListeners;
        this.mBiometricContext = biometricContext;
        this.mSensorProperties = fingerprintSensorPropertiesInternal;
        this.mSensorId = fingerprintSensorPropertiesInternal.sensorId;
        this.mIsUdfps = fingerprintSensorPropertiesInternal.sensorType == 3 || fingerprintSensorPropertiesInternal.sensorType == 2;
        this.mIsPowerbuttonFps = fingerprintSensorPropertiesInternal.sensorType == 4;
        this.mScheduler = biometricScheduler;
        this.mHandler = handler;
        this.mActivityTaskManager = android.app.ActivityTaskManager.getInstance();
        this.mTaskStackListener = new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.BiometricTaskStackListener();
        this.mAuthenticatorIds = java.util.Collections.synchronizedMap(new java.util.HashMap());
        this.mLazyDaemon = new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda13(this);
        this.mLockoutResetDispatcher = lockoutResetDispatcher;
        this.mLockoutTracker = new com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl(context, this.mLockoutResetCallback);
        this.mHalResultController = halResultController;
        this.mHalResultController.setCallback(new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController.Callback() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda20
            @Override // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController.Callback
            public final void onHardwareUnavailable() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$new$0();
            }
        });
        new com.android.server.biometrics.AuthenticationStatsBroadcastReceiver(this.mContext, 1, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$new$1((com.android.server.biometrics.AuthenticationStatsCollector) obj);
            }
        });
        this.mCleanup = context.getResources().getBoolean(1057161217);
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

    public static com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21 newInstance(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricStateCallback biometricStateCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, @android.annotation.NonNull android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher) {
        com.android.server.biometrics.sensors.BiometricScheduler biometricScheduler = new com.android.server.biometrics.sensors.BiometricScheduler(com.android.server.biometrics.sensors.BiometricScheduler.sensorTypeFromFingerprintProperties(fingerprintSensorPropertiesInternal), gestureAvailabilityDispatcher);
        return new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21(context, biometricStateCallback, authenticationStateListeners, fingerprintSensorPropertiesInternal, biometricScheduler, handler, lockoutResetDispatcher, new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController(fingerprintSensorPropertiesInternal.sensorId, context, handler, biometricScheduler), com.android.server.biometrics.log.BiometricContext.getInstance(context));
    }

    public void serviceDied(long j) {
        android.util.Slog.e(TAG, "HAL died");
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$serviceDied$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$serviceDied$2() {
        com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(this.mSensorProperties.sensorId).incrementHALDeathCount();
        this.mDaemon = null;
        this.mCurrentUserId = com.android.server.am.ProcessList.INVALID_ADJ;
        java.lang.Object currentClient = this.mScheduler.getCurrentClient();
        if (currentClient instanceof com.android.server.biometrics.sensors.ErrorConsumer) {
            android.util.Slog.e(TAG, "Sending ERROR_HW_UNAVAILABLE for client: " + currentClient);
            ((com.android.server.biometrics.sensors.ErrorConsumer) currentClient).onError(1, 0);
            com.android.internal.util.FrameworkStatsLog.write(148, 1, 1, -1);
        }
        this.mScheduler.recordCrashState();
        this.mScheduler.reset();
    }

    synchronized com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession getSession() {
        if (this.mDaemon != null && this.mSession != null) {
            return this.mSession;
        }
        com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession aidlSession = new com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession(new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda13(this), this.mCurrentUserId, new com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler(this.mContext, this.mScheduler, this.mSensorId, this.mCurrentUserId, new com.android.server.biometrics.sensors.LockoutCache(), this.mLockoutResetDispatcher, new com.android.server.biometrics.sensors.AuthSessionCoordinator(), new com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.HardwareUnavailableCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda14
            @Override // com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler.HardwareUnavailableCallback
            public final void onHardwareUnavailable() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$getSession$3();
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

    @com.android.internal.annotations.VisibleForTesting
    synchronized android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint getDaemon() {
        long j;
        if (this.mTestHalEnabled) {
            com.android.server.biometrics.sensors.fingerprint.hidl.TestHal testHal = new com.android.server.biometrics.sensors.fingerprint.hidl.TestHal(this.mContext, this.mSensorId);
            testHal.setNotify(this.mHalResultController);
            return testHal;
        }
        if (this.mDaemon != null) {
            return this.mDaemon;
        }
        android.util.Slog.d(TAG, "Daemon was null, reconnecting, current operation: " + this.mScheduler.getCurrentClient());
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
        try {
            j = this.mDaemon.setNotify(this.mHalResultController);
        } catch (android.os.RemoteException e3) {
            android.util.Slog.e(TAG, "Failed to set callback for fingerprint HAL", e3);
            this.mDaemon = null;
            j = 0;
        }
        android.util.Slog.d(TAG, "Fingerprint HAL ready, HAL ID: " + j);
        if (j != 0) {
            scheduleLoadAuthenticatorIds();
            scheduleInternalCleanup(android.app.ActivityManager.getCurrentUser(), null);
        } else {
            android.util.Slog.e(TAG, "Unable to set callback");
            this.mDaemon = null;
        }
        return this.mDaemon;
    }

    @android.annotation.Nullable
    android.hardware.fingerprint.IUdfpsOverlayController getUdfpsOverlayController() {
        return this.mUdfpsOverlayController;
    }

    private void scheduleLoadAuthenticatorIds() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$scheduleLoadAuthenticatorIds$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleLoadAuthenticatorIds$4() {
        java.util.Iterator it = android.os.UserManager.get(this.mContext).getAliveUsers().iterator();
        while (it.hasNext()) {
            int i = ((android.content.pm.UserInfo) it.next()).id;
            if (!this.mAuthenticatorIds.containsKey(java.lang.Integer.valueOf(i))) {
                scheduleUpdateActiveUserWithoutHandler(i, true);
            }
        }
    }

    private void scheduleUpdateActiveUserWithoutHandler(int i) {
        scheduleUpdateActiveUserWithoutHandler(i, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleUpdateActiveUserWithoutHandler(final int i, boolean z) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintUpdateActiveUserClientLegacy(this.mContext, this.mLazyDaemon, i, this.mContext.getOpPackageName(), this.mSensorProperties.sensorId, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, new java.util.function.Supplier() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                int currentUser;
                currentUser = com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.getCurrentUser();
                return java.lang.Integer.valueOf(currentUser);
            }
        }, !getEnrolledFingerprints(this.mSensorProperties.sensorId, i).isEmpty(), this.mAuthenticatorIds, z), new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.3
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z2) {
                if (z2) {
                    if (com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mCurrentUserId != i) {
                        com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mSession = null;
                    }
                    com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mCurrentUserId = i;
                    return;
                }
                android.util.Slog.w(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.TAG, "Failed to change user, still: " + com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mCurrentUserId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCurrentUser() {
        return this.mCurrentUserId;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public boolean containsSensor(int i) {
        return this.mSensorProperties.sensorId == i;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    @android.annotation.NonNull
    public java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> getSensorProperties() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(this.mSensorProperties);
        return arrayList;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    @android.annotation.Nullable
    public android.hardware.fingerprint.FingerprintSensorPropertiesInternal getSensorProperties(int i) {
        return this.mSensorProperties;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleResetLockout(final int i, final int i2, @android.annotation.Nullable final byte[] bArr) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$scheduleResetLockout$5(i, i2, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleResetLockout$5(int i, int i2, byte[] bArr) {
        com.android.server.biometrics.Flags.deHidl();
        scheduleResetLockoutHidl(i, i2);
    }

    private void scheduleResetLockoutAidl(int i, int i2, @android.annotation.Nullable byte[] bArr) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintResetLockoutClient(this.mContext, new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda1(this), i2, this.mContext.getOpPackageName(), i, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, bArr, this.mLockoutTracker, this.mLockoutResetDispatcher, com.android.server.biometrics.Utils.getCurrentStrength(i)));
    }

    private void scheduleResetLockoutHidl(int i, int i2) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintResetLockoutClient(this.mContext, i2, this.mContext.getOpPackageName(), i, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mLockoutTracker));
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleGenerateChallenge(int i, final int i2, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, @android.annotation.NonNull final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$scheduleGenerateChallenge$6(i2, iBinder, iFingerprintServiceReceiver, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleGenerateChallenge$6(int i, android.os.IBinder iBinder, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) {
        com.android.server.biometrics.Flags.deHidl();
        scheduleGenerateChallengeHidl(i, iBinder, iFingerprintServiceReceiver, str);
    }

    private void scheduleGenerateChallengeAidl(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, @android.annotation.NonNull java.lang.String str) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintGenerateChallengeClient(this.mContext, new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda1(this), iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFingerprintServiceReceiver), i, str, this.mSensorProperties.sensorId, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext));
    }

    private void scheduleGenerateChallengeHidl(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, @android.annotation.NonNull java.lang.String str) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintGenerateChallengeClient(this.mContext, this.mLazyDaemon, iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFingerprintServiceReceiver), i, str, this.mSensorProperties.sensorId, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext));
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleRevokeChallenge(int i, final int i2, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final java.lang.String str, long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$scheduleRevokeChallenge$7(i2, iBinder, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRevokeChallenge$7(int i, android.os.IBinder iBinder, java.lang.String str) {
        com.android.server.biometrics.Flags.deHidl();
        scheduleRevokeChallengeHidl(i, iBinder, str);
    }

    private void scheduleRevokeChallengeAidl(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.lang.String str) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintRevokeChallengeClient(this.mContext, new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda1(this), iBinder, i, str, this.mSensorProperties.sensorId, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, 0L));
    }

    private void scheduleRevokeChallengeHidl(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.lang.String str) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintRevokeChallengeClient(this.mContext, this.mLazyDaemon, iBinder, i, str, this.mSensorProperties.sensorId, createLogger(0, 0, this.mAuthenticationStatsCollector), this.mBiometricContext));
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public long scheduleEnroll(int i, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final byte[] bArr, final int i2, @android.annotation.NonNull final android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, @android.annotation.NonNull final java.lang.String str, final int i3, @android.annotation.NonNull final android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions) {
        final long incrementAndGet = this.mRequestCounter.incrementAndGet();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$scheduleEnroll$8(i2, iBinder, bArr, iFingerprintServiceReceiver, str, i3, incrementAndGet, fingerprintEnrollOptions);
            }
        });
        return incrementAndGet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleEnroll$8(int i, android.os.IBinder iBinder, byte[] bArr, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str, int i2, long j, android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions) {
        scheduleUpdateActiveUserWithoutHandler(i);
        com.android.server.biometrics.Flags.deHidl();
        scheduleEnrollHidl(iBinder, bArr, i, iFingerprintServiceReceiver, str, i2, j, fingerprintEnrollOptions);
    }

    private void scheduleEnrollHidl(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull byte[] bArr, int i, @android.annotation.NonNull android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, @android.annotation.NonNull java.lang.String str, int i2, long j, @android.annotation.NonNull android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintEnrollClient(this.mContext, this.mLazyDaemon, iBinder, j, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFingerprintServiceReceiver), i, bArr, str, com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getLegacyInstance(this.mSensorId), 60, this.mSensorProperties.sensorId, createLogger(1, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mUdfpsOverlayController, this.mSidefpsController, this.mAuthenticationStateListeners, i2, fingerprintEnrollOptions), new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.4
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mBiometricStateCallback.onClientStarted(baseClientMonitor);
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onBiometricAction(int i3) {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mBiometricStateCallback.onBiometricAction(i3);
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mBiometricStateCallback.onClientFinished(baseClientMonitor, z);
                if (z) {
                    com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.scheduleUpdateActiveUserWithoutHandler(baseClientMonitor.getTargetUserId(), true);
                }
            }
        });
    }

    private void scheduleEnrollAidl(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull byte[] bArr, int i, @android.annotation.NonNull android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, @android.annotation.NonNull java.lang.String str, int i2, long j, @android.annotation.NonNull android.hardware.fingerprint.FingerprintEnrollOptions fingerprintEnrollOptions) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintEnrollClient(this.mContext, new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda1(this), iBinder, j, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFingerprintServiceReceiver), i, bArr, str, com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getLegacyInstance(this.mSensorId), this.mSensorProperties.sensorId, createLogger(1, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, null, this.mUdfpsOverlayController, this.mSidefpsController, this.mAuthenticationStateListeners, this.mContext.getResources().getInteger(android.R.integer.config_externalDisplayPeakWidth), i2, fingerprintEnrollOptions), new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.5
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mBiometricStateCallback.onClientStarted(baseClientMonitor);
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onBiometricAction(int i3) {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mBiometricStateCallback.onBiometricAction(i3);
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.mBiometricStateCallback.onClientFinished(baseClientMonitor, z);
                if (z) {
                    com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.scheduleUpdateActiveUserWithoutHandler(baseClientMonitor.getTargetUserId(), true);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelEnrollment$9(android.os.IBinder iBinder, long j) {
        this.mScheduler.cancelEnrollment(iBinder, j);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void cancelEnrollment(int i, @android.annotation.NonNull final android.os.IBinder iBinder, final long j) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$cancelEnrollment$9(iBinder, j);
            }
        });
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public long scheduleFingerDetect(@android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull final android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, final int i) {
        final long incrementAndGet = this.mRequestCounter.incrementAndGet();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$scheduleFingerDetect$10(fingerprintAuthenticateOptions, iBinder, clientMonitorCallbackConverter, i, incrementAndGet);
            }
        });
        return incrementAndGet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleFingerDetect$10(android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, android.os.IBinder iBinder, com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, int i, long j) {
        scheduleUpdateActiveUserWithoutHandler(fingerprintAuthenticateOptions.getUserId());
        boolean isStrongBiometric = com.android.server.biometrics.Utils.isStrongBiometric(this.mSensorProperties.sensorId);
        com.android.server.biometrics.Flags.deHidl();
        scheduleFingerDetectHidl(iBinder, clientMonitorCallbackConverter, fingerprintAuthenticateOptions, i, j, isStrongBiometric);
    }

    private void scheduleFingerDetectHidl(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, int i, long j, boolean z) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintDetectClient(this.mContext, this.mLazyDaemon, iBinder, j, clientMonitorCallbackConverter, fingerprintAuthenticateOptions, createLogger(2, i, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mUdfpsOverlayController, z), this.mBiometricStateCallback);
    }

    private void scheduleFingerDetectAidl(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, int i, long j, boolean z) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintDetectClient(this.mContext, new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda1(this), iBinder, j, clientMonitorCallbackConverter, fingerprintAuthenticateOptions, createLogger(2, i, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mUdfpsOverlayController, z), this.mBiometricStateCallback);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleAuthenticate(@android.annotation.NonNull final android.os.IBinder iBinder, final long j, final int i, @android.annotation.NonNull final com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull final android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, final long j2, final boolean z, final int i2, final boolean z2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$scheduleAuthenticate$11(fingerprintAuthenticateOptions, iBinder, j, i, clientMonitorCallbackConverter, j2, z, i2, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleAuthenticate$11(android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, android.os.IBinder iBinder, long j, int i, com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, long j2, boolean z, int i2, boolean z2) {
        scheduleUpdateActiveUserWithoutHandler(fingerprintAuthenticateOptions.getUserId());
        boolean isStrongBiometric = com.android.server.biometrics.Utils.isStrongBiometric(this.mSensorProperties.sensorId);
        com.android.server.biometrics.Flags.deHidl();
        scheduleAuthenticateHidl(iBinder, j, i, clientMonitorCallbackConverter, fingerprintAuthenticateOptions, j2, z, i2, z2, isStrongBiometric);
    }

    private void scheduleAuthenticateAidl(@android.annotation.NonNull android.os.IBinder iBinder, long j, int i, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, long j2, boolean z, int i2, boolean z2, boolean z3) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintAuthenticationClient(this.mContext, new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda1(this), iBinder, j2, clientMonitorCallbackConverter, j, z, fingerprintAuthenticateOptions, i, false, createLogger(2, i2, this.mAuthenticationStatsCollector), this.mBiometricContext, z3, this.mTaskStackListener, this.mUdfpsOverlayController, this.mSidefpsController, this.mAuthenticationStateListeners, z2, this.mSensorProperties, this.mHandler, com.android.server.biometrics.Utils.getCurrentStrength(this.mSensorId), null, this.mLockoutTracker), this.mBiometricStateCallback);
    }

    private void scheduleAuthenticateHidl(@android.annotation.NonNull android.os.IBinder iBinder, long j, int i, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, long j2, boolean z, int i2, boolean z2, boolean z3) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintAuthenticationClient(this.mContext, this.mLazyDaemon, iBinder, j2, clientMonitorCallbackConverter, j, z, fingerprintAuthenticateOptions, i, false, createLogger(2, i2, this.mAuthenticationStatsCollector), this.mBiometricContext, z3, this.mTaskStackListener, this.mLockoutTracker, this.mUdfpsOverlayController, this.mSidefpsController, this.mAuthenticationStateListeners, z2, this.mSensorProperties, com.android.server.biometrics.Utils.getCurrentStrength(this.mSensorId)), this.mBiometricStateCallback);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public long scheduleAuthenticate(@android.annotation.NonNull android.os.IBinder iBinder, long j, int i, @android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter, @android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions, boolean z, int i2, boolean z2) {
        long incrementAndGet = this.mRequestCounter.incrementAndGet();
        scheduleAuthenticate(iBinder, j, i, clientMonitorCallbackConverter, fingerprintAuthenticateOptions, incrementAndGet, z, i2, z2);
        return incrementAndGet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startPreparedClient$12(int i) {
        this.mScheduler.startPreparedClient(i);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void startPreparedClient(int i, final int i2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$startPreparedClient$12(i2);
            }
        });
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void cancelAuthentication(int i, @android.annotation.NonNull final android.os.IBinder iBinder, final long j) {
        android.util.Slog.d(TAG, "cancelAuthentication, sensorId: " + i);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$cancelAuthentication$13(iBinder, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelAuthentication$13(android.os.IBinder iBinder, long j) {
        this.mScheduler.cancelAuthenticationOrDetection(iBinder, j);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleRemove(int i, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, final int i2, final int i3, @android.annotation.NonNull final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$scheduleRemove$14(i3, iBinder, iFingerprintServiceReceiver, i2, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRemove$14(int i, android.os.IBinder iBinder, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, int i2, java.lang.String str) {
        scheduleUpdateActiveUserWithoutHandler(i);
        com.android.server.biometrics.Flags.deHidl();
        scheduleRemoveHidl(iBinder, iFingerprintServiceReceiver, i2, i, str);
    }

    private void scheduleRemoveHidl(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, int i, int i2, @android.annotation.NonNull java.lang.String str) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintRemovalClient(this.mContext, this.mLazyDaemon, iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFingerprintServiceReceiver), i, i2, str, com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getLegacyInstance(this.mSensorId), this.mSensorProperties.sensorId, createLogger(4, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mAuthenticatorIds), this.mBiometricStateCallback);
    }

    private void scheduleRemoveAidl(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, int i, int i2, @android.annotation.NonNull java.lang.String str) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintRemovalClient(this.mContext, new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda1(this), iBinder, new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter(iFingerprintServiceReceiver), new int[]{i}, i2, str, com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getLegacyInstance(this.mSensorId), this.mSensorId, createLogger(4, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, this.mAuthenticatorIds), this.mBiometricStateCallback);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleRemoveAll(int i, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, final int i2, @android.annotation.NonNull final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$scheduleRemoveAll$15(i2, iBinder, iFingerprintServiceReceiver, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleRemoveAll$15(int i, android.os.IBinder iBinder, android.hardware.fingerprint.IFingerprintServiceReceiver iFingerprintServiceReceiver, java.lang.String str) {
        scheduleUpdateActiveUserWithoutHandler(i);
        com.android.server.biometrics.Flags.deHidl();
        scheduleRemoveHidl(iBinder, iFingerprintServiceReceiver, 0, i, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleInternalCleanup(final int i, @android.annotation.Nullable final com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        if (!this.mCleanup) {
            return;
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$scheduleInternalCleanup$16(i, clientMonitorCallback);
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
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintInternalCleanupClient(this.mContext, this.mLazyDaemon, i, this.mContext.getOpPackageName(), this.mSensorProperties.sensorId, createLogger(3, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getLegacyInstance(this.mSensorId), this.mAuthenticatorIds), clientMonitorCallback);
    }

    private void scheduleInternalCleanupAidl(int i, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        this.mScheduler.scheduleClientMonitor(new com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintInternalCleanupClient(this.mContext, new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda1(this), i, this.mContext.getOpPackageName(), this.mSensorProperties.sensorId, createLogger(3, 0, this.mAuthenticationStatsCollector), this.mBiometricContext, com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getLegacyInstance(this.mSensorId), this.mAuthenticatorIds), clientMonitorCallback);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleInternalCleanup(int i, int i2, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        scheduleInternalCleanup(i2, new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(clientMonitorCallback, this.mBiometricStateCallback));
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleInternalCleanup(int i, int i2, @android.annotation.Nullable com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback, boolean z) {
        scheduleInternalCleanup(i2, new com.android.server.biometrics.sensors.ClientMonitorCompositeCallback(clientMonitorCallback, this.mBiometricStateCallback));
    }

    private com.android.server.biometrics.log.BiometricLogger createLogger(int i, int i2, com.android.server.biometrics.AuthenticationStatsCollector authenticationStatsCollector) {
        return new com.android.server.biometrics.log.BiometricLogger(this.mContext, 1, i, i2, authenticationStatsCollector);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public boolean isHardwareDetected(int i) {
        return getDaemon() != null;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void rename(int i, final int i2, final int i3, @android.annotation.NonNull final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.this.lambda$rename$17(i3, i2, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rename$17(int i, int i2, java.lang.String str) {
        com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getLegacyInstance(this.mSensorId).renameBiometricForUser(this.mContext, i, i2, str);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    @android.annotation.NonNull
    public java.util.List<android.hardware.fingerprint.Fingerprint> getEnrolledFingerprints(int i, int i2) {
        return com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getLegacyInstance(this.mSensorId).getBiometricsForUser(this.mContext, i2);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public boolean hasEnrollments(int i, int i2) {
        return !getEnrolledFingerprints(i, i2).isEmpty();
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public int getLockoutModeForUser(int i, int i2) {
        return this.mLockoutTracker.getLockoutModeForUser(i2);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public long getAuthenticatorId(int i, int i2) {
        return this.mAuthenticatorIds.getOrDefault(java.lang.Integer.valueOf(i2), 0L).longValue();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void onPointerDown(long j, int i, final android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        this.mScheduler.getCurrentClientIfMatches(j, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.lambda$onPointerDown$18(pointerContext, (com.android.server.biometrics.sensors.BaseClientMonitor) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void lambda$onPointerDown$18(android.hardware.biometrics.fingerprint.PointerContext pointerContext, com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        if (!(baseClientMonitor instanceof com.android.server.biometrics.sensors.fingerprint.Udfps)) {
            android.util.Slog.w(TAG, "onFingerDown received during client: " + baseClientMonitor);
            return;
        }
        ((com.android.server.biometrics.sensors.fingerprint.Udfps) baseClientMonitor).onPointerDown(pointerContext);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void onPointerUp(long j, int i, final android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        this.mScheduler.getCurrentClientIfMatches(j, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.lambda$onPointerUp$19(pointerContext, (com.android.server.biometrics.sensors.BaseClientMonitor) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void lambda$onPointerUp$19(android.hardware.biometrics.fingerprint.PointerContext pointerContext, com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        if (!(baseClientMonitor instanceof com.android.server.biometrics.sensors.fingerprint.Udfps)) {
            android.util.Slog.w(TAG, "onFingerDown received during client: " + baseClientMonitor);
            return;
        }
        ((com.android.server.biometrics.sensors.fingerprint.Udfps) baseClientMonitor).onPointerUp(pointerContext);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void onUdfpsUiEvent(final int i, long j, int i2) {
        this.mScheduler.getCurrentClientIfMatches(j, new java.util.function.Consumer() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.lambda$onUdfpsUiEvent$20(i, (com.android.server.biometrics.sensors.BaseClientMonitor) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void lambda$onUdfpsUiEvent$20(int i, com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
        if (!(baseClientMonitor instanceof com.android.server.biometrics.sensors.fingerprint.Udfps)) {
            android.util.Slog.w(TAG, "onUdfpsUiEvent received during client: " + baseClientMonitor);
            return;
        }
        ((com.android.server.biometrics.sensors.fingerprint.Udfps) baseClientMonitor).onUdfpsUiEvent(i);
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void onPowerPressed() {
        android.util.Slog.e(TAG, "onPowerPressed not supported for HIDL clients");
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void setUdfpsOverlayController(@android.annotation.NonNull android.hardware.fingerprint.IUdfpsOverlayController iUdfpsOverlayController) {
        this.mUdfpsOverlayController = iUdfpsOverlayController;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void setSidefpsController(@android.annotation.NonNull android.hardware.fingerprint.ISidefpsController iSidefpsController) {
        this.mSidefpsController = iSidefpsController;
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public void dumpProtoState(int i, @android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, boolean z) {
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
            protoOutputStream.write(1120986464258L, com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getLegacyInstance(this.mSensorId).getBiometricsForUser(this.mContext, identifier).size());
            protoOutputStream.end(start2);
        }
        protoOutputStream.write(1133871366150L, this.mSensorProperties.resetLockoutRequiresHardwareAuthToken);
        protoOutputStream.write(1133871366151L, this.mSensorProperties.resetLockoutRequiresChallenge);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public void dumpProtoMetrics(int i, java.io.FileDescriptor fileDescriptor) {
        com.android.server.biometrics.sensors.PerformanceTracker instanceForSensorId = com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(this.mSensorProperties.sensorId);
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        java.util.Iterator it = android.os.UserManager.get(this.mContext).getUsers().iterator();
        while (it.hasNext()) {
            int identifier = ((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier();
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1120986464257L, identifier);
            protoOutputStream.write(1120986464258L, com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getLegacyInstance(this.mSensorId).getBiometricsForUser(this.mContext, identifier).size());
            long start2 = protoOutputStream.start(1146756268035L);
            protoOutputStream.write(1120986464257L, instanceForSensorId.getAcceptForUser(identifier));
            protoOutputStream.write(1120986464258L, instanceForSensorId.getRejectForUser(identifier));
            protoOutputStream.write(1120986464259L, instanceForSensorId.getAcquireForUser(identifier));
            protoOutputStream.write(1120986464260L, instanceForSensorId.getTimedLockoutForUser(identifier));
            protoOutputStream.write(1120986464261L, instanceForSensorId.getPermanentLockoutForUser(identifier));
            protoOutputStream.end(start2);
            long start3 = protoOutputStream.start(1146756268036L);
            protoOutputStream.write(1120986464257L, instanceForSensorId.getAcceptCryptoForUser(identifier));
            protoOutputStream.write(1120986464258L, instanceForSensorId.getRejectCryptoForUser(identifier));
            protoOutputStream.write(1120986464259L, instanceForSensorId.getAcquireCryptoForUser(identifier));
            protoOutputStream.write(1120986464260L, 0);
            protoOutputStream.write(1120986464261L, 0);
            protoOutputStream.end(start3);
            protoOutputStream.end(start);
        }
        protoOutputStream.flush();
        instanceForSensorId.clear();
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void scheduleInvalidateAuthenticatorId(int i, int i2, @android.annotation.NonNull android.hardware.biometrics.IInvalidationCallback iInvalidationCallback) {
        try {
            iInvalidationCallback.onCompleted();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to complete InvalidateAuthenticatorId");
        }
    }

    @Override // com.android.server.biometrics.sensors.BiometricServiceProvider
    public void dumpInternal(int i, @android.annotation.NonNull java.io.PrintWriter printWriter) {
        com.android.server.biometrics.sensors.PerformanceTracker instanceForSensorId = com.android.server.biometrics.sensors.PerformanceTracker.getInstanceForSensorId(this.mSensorProperties.sensorId);
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        try {
            jSONObject.put(com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE, TAG);
            jSONObject.put("isUdfps", this.mIsUdfps);
            jSONObject.put("isPowerbuttonFps", this.mIsPowerbuttonFps);
            org.json.JSONArray jSONArray = new org.json.JSONArray();
            java.util.Iterator it = android.os.UserManager.get(this.mContext).getUsers().iterator();
            while (it.hasNext()) {
                int identifier = ((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier();
                int size = com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getLegacyInstance(this.mSensorId).getBiometricsForUser(this.mContext, identifier).size();
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
    }

    void setTestHalEnabled(boolean z) {
        this.mTestHalEnabled = z;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    @android.annotation.NonNull
    public android.hardware.biometrics.ITestSession createTestSession(int i, @android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull java.lang.String str) {
        return new com.android.server.biometrics.sensors.fingerprint.hidl.BiometricTestSessionImpl(this.mContext, this.mSensorProperties.sensorId, iTestSessionCallback, this.mBiometricStateCallback, this, this.mHalResultController);
    }
}
