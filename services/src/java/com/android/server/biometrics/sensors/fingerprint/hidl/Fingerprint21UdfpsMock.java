package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
public class Fingerprint21UdfpsMock extends com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21 implements android.app.trust.TrustManager.TrustListener {
    private static final java.lang.String CONFIG_AUTH_DELAY_PT1 = "com.android.server.biometrics.sensors.fingerprint.test_udfps.auth_delay_pt1";
    private static final java.lang.String CONFIG_AUTH_DELAY_PT2 = "com.android.server.biometrics.sensors.fingerprint.test_udfps.auth_delay_pt2";
    private static final java.lang.String CONFIG_AUTH_DELAY_RANDOMNESS = "com.android.server.biometrics.sensors.fingerprint.test_udfps.auth_delay_randomness";
    public static final java.lang.String CONFIG_ENABLE_TEST_UDFPS = "com.android.server.biometrics.sensors.fingerprint.test_udfps.enable";
    private static final int DEFAULT_AUTH_DELAY_PT1_MS = 300;
    private static final int DEFAULT_AUTH_DELAY_PT2_MS = 400;
    private static final int DEFAULT_AUTH_DELAY_RANDOMNESS_MS = 100;
    private static final java.lang.String TAG = "Fingerprint21UdfpsMock";

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.FakeAcceptRunnable mFakeAcceptRunnable;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.FakeRejectRunnable mFakeRejectRunnable;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.MockHalResultController mMockHalResultController;

    @android.annotation.NonNull
    private final java.util.Random mRandom;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.RestartAuthRunnable mRestartAuthRunnable;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.TestableBiometricScheduler mScheduler;

    @android.annotation.NonNull
    private final android.hardware.fingerprint.FingerprintSensorPropertiesInternal mSensorProperties;

    @android.annotation.NonNull
    private final android.app.trust.TrustManager mTrustManager;

    @android.annotation.NonNull
    private final android.util.SparseBooleanArray mUserHasTrust;

    private static class TestableBiometricScheduler extends com.android.server.biometrics.sensors.BiometricScheduler {

        @android.annotation.NonNull
        private com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock mFingerprint21;

        TestableBiometricScheduler(@android.annotation.Nullable com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher) {
            super(3, gestureAvailabilityDispatcher);
        }

        void init(@android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock fingerprint21UdfpsMock) {
            this.mFingerprint21 = fingerprint21UdfpsMock;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MockHalResultController extends com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController {
        private static final int AUTH_VALIDITY_MS = 10000;

        @android.annotation.NonNull
        private com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock mFingerprint21;

        @android.annotation.Nullable
        private com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.MockHalResultController.LastAuthArgs mLastAuthArgs;

        @android.annotation.NonNull
        private com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.RestartAuthRunnable mRestartAuthRunnable;

        static class LastAuthArgs {
            final long deviceId;
            final int fingerId;
            final int groupId;

            @android.annotation.NonNull
            final com.android.server.biometrics.sensors.AuthenticationConsumer lastAuthenticatedClient;

            @android.annotation.Nullable
            final java.util.ArrayList<java.lang.Byte> token;

            LastAuthArgs(@android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationConsumer authenticationConsumer, long j, int i, int i2, @android.annotation.Nullable java.util.ArrayList<java.lang.Byte> arrayList) {
                this.lastAuthenticatedClient = authenticationConsumer;
                this.deviceId = j;
                this.fingerId = i;
                this.groupId = i2;
                if (arrayList == null) {
                    this.token = null;
                } else {
                    this.token = new java.util.ArrayList<>(arrayList);
                }
            }
        }

        MockHalResultController(int i, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricScheduler biometricScheduler) {
            super(i, context, handler, biometricScheduler);
        }

        void init(@android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.RestartAuthRunnable restartAuthRunnable, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock fingerprint21UdfpsMock) {
            this.mRestartAuthRunnable = restartAuthRunnable;
            this.mFingerprint21 = fingerprint21UdfpsMock;
        }

        @android.annotation.Nullable
        com.android.server.biometrics.sensors.AuthenticationConsumer getLastAuthenticatedClient() {
            if (this.mLastAuthArgs != null) {
                return this.mLastAuthArgs.lastAuthenticatedClient;
            }
            return null;
        }

        @Override // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21.HalResultController, android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
        public void onAuthenticated(final long j, final int i, final int i2, final java.util.ArrayList<java.lang.Byte> arrayList) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock$MockHalResultController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.MockHalResultController.this.lambda$onAuthenticated$0(i, j, i2, arrayList);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAuthenticated$0(int i, long j, int i2, java.util.ArrayList arrayList) {
            java.lang.Object currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof com.android.server.biometrics.sensors.AuthenticationConsumer)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.TAG, "Non authentication consumer: " + currentClient);
                return;
            }
            if (i != 0) {
                this.mFingerprint21.setDebugMessage("Finger accepted");
            } else {
                this.mFingerprint21.setDebugMessage("Finger rejected");
            }
            com.android.server.biometrics.sensors.AuthenticationConsumer authenticationConsumer = (com.android.server.biometrics.sensors.AuthenticationConsumer) currentClient;
            this.mLastAuthArgs = new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.MockHalResultController.LastAuthArgs(authenticationConsumer, j, i, i2, arrayList);
            this.mHandler.removeCallbacks(this.mRestartAuthRunnable);
            this.mRestartAuthRunnable.setLastAuthReference(authenticationConsumer);
            this.mHandler.postDelayed(this.mRestartAuthRunnable, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        }

        void sendAuthenticated(long j, int i, int i2, java.util.ArrayList<java.lang.Byte> arrayList) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("sendAuthenticated: ");
            sb.append(i != 0);
            android.util.Slog.d(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.TAG, sb.toString());
            com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock fingerprint21UdfpsMock = this.mFingerprint21;
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
            sb2.append("Udfps match: ");
            sb2.append(i != 0);
            fingerprint21UdfpsMock.setDebugMessage(sb2.toString());
            super.onAuthenticated(j, i, i2, arrayList);
        }
    }

    public static com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock newInstance(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricStateCallback biometricStateCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, @android.annotation.NonNull android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher gestureAvailabilityDispatcher, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        android.util.Slog.d(TAG, "Creating Fingerprint23Mock!");
        android.os.Handler handler = new android.os.Handler(android.os.Looper.getMainLooper());
        com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.TestableBiometricScheduler testableBiometricScheduler = new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.TestableBiometricScheduler(gestureAvailabilityDispatcher);
        return new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock(context, biometricStateCallback, authenticationStateListeners, fingerprintSensorPropertiesInternal, testableBiometricScheduler, handler, lockoutResetDispatcher, new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.MockHalResultController(fingerprintSensorPropertiesInternal.sensorId, context, handler, testableBiometricScheduler), biometricContext);
    }

    private static abstract class FakeFingerRunnable implements java.lang.Runnable {
        private int mCaptureDuration;
        private long mFingerDownTime;

        private FakeFingerRunnable() {
        }

        void setSimulationTime(long j, int i) {
            this.mFingerDownTime = j;
            this.mCaptureDuration = i;
        }

        boolean isImageCaptureComplete() {
            return java.lang.System.currentTimeMillis() - this.mFingerDownTime > ((long) this.mCaptureDuration);
        }
    }

    private final class FakeRejectRunnable extends com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.FakeFingerRunnable {
        private FakeRejectRunnable() {
            super();
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.this.mMockHalResultController.sendAuthenticated(0L, 0, 0, null);
        }
    }

    private final class FakeAcceptRunnable extends com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.FakeFingerRunnable {
        private FakeAcceptRunnable() {
            super();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.this.mMockHalResultController.mLastAuthArgs == null) {
                android.util.Slog.d(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.TAG, "Sending fake finger");
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.this.mMockHalResultController.sendAuthenticated(1L, 1, 1, null);
            } else {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.this.mMockHalResultController.sendAuthenticated(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.this.mMockHalResultController.mLastAuthArgs.deviceId, com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.this.mMockHalResultController.mLastAuthArgs.fingerId, com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.this.mMockHalResultController.mLastAuthArgs.groupId, com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.this.mMockHalResultController.mLastAuthArgs.token);
            }
        }
    }

    private static final class RestartAuthRunnable implements java.lang.Runnable {

        @android.annotation.NonNull
        private final com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock mFingerprint21;
        private com.android.server.biometrics.sensors.AuthenticationConsumer mLastAuthConsumer;

        @android.annotation.NonNull
        private final com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.TestableBiometricScheduler mScheduler;

        RestartAuthRunnable(@android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock fingerprint21UdfpsMock, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.TestableBiometricScheduler testableBiometricScheduler) {
            this.mFingerprint21 = fingerprint21UdfpsMock;
            this.mScheduler = testableBiometricScheduler;
        }

        void setLastAuthReference(com.android.server.biometrics.sensors.AuthenticationConsumer authenticationConsumer) {
            this.mLastAuthConsumer = authenticationConsumer;
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
            if (!(currentClient instanceof com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintAuthenticationClient)) {
                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.TAG, "Non-FingerprintAuthenticationClient client: " + currentClient);
                return;
            }
            if (currentClient != this.mLastAuthConsumer) {
                android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.TAG, "Current client: " + currentClient + " does not match mLastAuthConsumer: " + this.mLastAuthConsumer);
                return;
            }
            android.util.Slog.d(com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.TAG, "Restarting auth, current: " + currentClient);
            this.mFingerprint21.setDebugMessage("Auth timed out");
            com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintAuthenticationClient fingerprintAuthenticationClient = (com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintAuthenticationClient) currentClient;
            android.os.IBinder token = currentClient.getToken();
            long operationId = fingerprintAuthenticationClient.getOperationId();
            int cookie = currentClient.getCookie();
            com.android.server.biometrics.sensors.ClientMonitorCallbackConverter clientMonitorCallbackConverter = new com.android.server.biometrics.sensors.ClientMonitorCallbackConverter((android.hardware.fingerprint.IFingerprintServiceReceiver) new android.hardware.fingerprint.IFingerprintServiceReceiver.Default());
            boolean isRestricted = fingerprintAuthenticationClient.isRestricted();
            int statsClient = currentClient.getLogger().getStatsClient();
            boolean isKeyguard = fingerprintAuthenticationClient.isKeyguard();
            android.hardware.fingerprint.FingerprintAuthenticateOptions build = new android.hardware.fingerprint.FingerprintAuthenticateOptions.Builder().setUserId(currentClient.getTargetUserId()).setOpPackageName(currentClient.getOwnerString()).build();
            this.mScheduler.getInternalCallback().onClientFinished(currentClient, true);
            this.mFingerprint21.scheduleAuthenticate(token, operationId, cookie, clientMonitorCallbackConverter, build, isRestricted, statsClient, isKeyguard);
        }
    }

    private Fingerprint21UdfpsMock(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricStateCallback biometricStateCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.AuthenticationStateListeners authenticationStateListeners, @android.annotation.NonNull android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.TestableBiometricScheduler testableBiometricScheduler, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.biometrics.sensors.LockoutResetDispatcher lockoutResetDispatcher, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.MockHalResultController mockHalResultController, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        super(context, biometricStateCallback, authenticationStateListeners, fingerprintSensorPropertiesInternal, testableBiometricScheduler, handler, lockoutResetDispatcher, mockHalResultController, biometricContext);
        this.mScheduler = testableBiometricScheduler;
        this.mScheduler.init(this);
        this.mHandler = handler;
        this.mSensorProperties = new android.hardware.fingerprint.FingerprintSensorPropertiesInternal(fingerprintSensorPropertiesInternal.sensorId, fingerprintSensorPropertiesInternal.sensorStrength, this.mContext.getResources().getInteger(android.R.integer.config_externalDisplayPeakWidth), fingerprintSensorPropertiesInternal.componentInfo, 3, false, false, fingerprintSensorPropertiesInternal.getAllLocations());
        this.mMockHalResultController = mockHalResultController;
        this.mUserHasTrust = new android.util.SparseBooleanArray();
        this.mTrustManager = (android.app.trust.TrustManager) context.getSystemService(android.app.trust.TrustManager.class);
        this.mTrustManager.registerTrustListener(this);
        this.mRandom = new java.util.Random();
        this.mFakeRejectRunnable = new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.FakeRejectRunnable();
        this.mFakeAcceptRunnable = new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.FakeAcceptRunnable();
        this.mRestartAuthRunnable = new com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.RestartAuthRunnable(this, this.mScheduler);
        this.mMockHalResultController.init(this.mRestartAuthRunnable, this);
    }

    public void onTrustChanged(boolean z, boolean z2, int i, int i2, java.util.List<java.lang.String> list) {
        this.mUserHasTrust.put(i, z);
    }

    public void onTrustManagedChanged(boolean z, int i) {
    }

    public void onTrustError(java.lang.CharSequence charSequence) {
    }

    public void onEnabledTrustAgentsChanged(int i) {
    }

    public void onIsActiveUnlockRunningChanged(boolean z, int i) {
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21, com.android.server.biometrics.sensors.BiometricServiceProvider
    @android.annotation.NonNull
    public java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> getSensorProperties() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(this.mSensorProperties);
        return arrayList;
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21, com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void onPointerDown(long j, int i, android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.this.lambda$onPointerDown$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPointerDown$0() {
        android.util.Slog.d(TAG, "onFingerDown");
        com.android.server.biometrics.sensors.AuthenticationConsumer lastAuthenticatedClient = this.mMockHalResultController.getLastAuthenticatedClient();
        com.android.server.biometrics.sensors.BaseClientMonitor currentClient = this.mScheduler.getCurrentClient();
        if (currentClient == null) {
            android.util.Slog.d(TAG, "Not authenticating");
            return;
        }
        this.mHandler.removeCallbacks(this.mFakeRejectRunnable);
        this.mHandler.removeCallbacks(this.mFakeAcceptRunnable);
        boolean z = false;
        boolean z2 = lastAuthenticatedClient != null && lastAuthenticatedClient == currentClient;
        if (currentClient instanceof com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintAuthenticationClient) {
            z = ((com.android.server.biometrics.sensors.fingerprint.hidl.FingerprintAuthenticationClient) currentClient).isKeyguard() && this.mUserHasTrust.get(currentClient.getTargetUserId(), false);
        }
        int newCaptureDuration = getNewCaptureDuration();
        int matchingDuration = getMatchingDuration();
        int i = newCaptureDuration + matchingDuration;
        setDebugMessage("Duration: " + i + " (" + newCaptureDuration + " + " + matchingDuration + ")");
        if (z2 || z) {
            this.mFakeAcceptRunnable.setSimulationTime(java.lang.System.currentTimeMillis(), newCaptureDuration);
            this.mHandler.postDelayed(this.mFakeAcceptRunnable, i);
        } else if (currentClient instanceof com.android.server.biometrics.sensors.AuthenticationConsumer) {
            this.mFakeRejectRunnable.setSimulationTime(java.lang.System.currentTimeMillis(), newCaptureDuration);
            this.mHandler.postDelayed(this.mFakeRejectRunnable, i);
        }
    }

    @Override // com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21, com.android.server.biometrics.sensors.fingerprint.ServiceProvider
    public void onPointerUp(long j, int i, android.hardware.biometrics.fingerprint.PointerContext pointerContext) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.fingerprint.hidl.Fingerprint21UdfpsMock.this.lambda$onPointerUp$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPointerUp$1() {
        android.util.Slog.d(TAG, "onFingerUp");
        if (this.mHandler.hasCallbacks(this.mFakeRejectRunnable) && !this.mFakeRejectRunnable.isImageCaptureComplete()) {
            this.mHandler.removeCallbacks(this.mFakeRejectRunnable);
            this.mMockHalResultController.onAcquired(0L, 5, 0);
        } else if (this.mHandler.hasCallbacks(this.mFakeAcceptRunnable) && !this.mFakeAcceptRunnable.isImageCaptureComplete()) {
            this.mHandler.removeCallbacks(this.mFakeAcceptRunnable);
            this.mMockHalResultController.onAcquired(0L, 5, 0);
        }
    }

    private int getNewCaptureDuration() {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        int intForUser = android.provider.Settings.Secure.getIntForUser(contentResolver, CONFIG_AUTH_DELAY_PT1, 300, -2);
        int intForUser2 = android.provider.Settings.Secure.getIntForUser(contentResolver, CONFIG_AUTH_DELAY_RANDOMNESS, 100, -2);
        return java.lang.Math.max(intForUser + (this.mRandom.nextInt(intForUser2 * 2) - intForUser2), 0);
    }

    private int getMatchingDuration() {
        return java.lang.Math.max(android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), CONFIG_AUTH_DELAY_PT2, 400, -2), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDebugMessage(java.lang.String str) {
        try {
            android.hardware.fingerprint.IUdfpsOverlayController udfpsOverlayController = getUdfpsOverlayController();
            if (udfpsOverlayController != null) {
                android.util.Slog.d(TAG, "setDebugMessage: " + str);
                udfpsOverlayController.setDebugMessage(this.mSensorProperties.sensorId, str);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Remote exception when sending message: " + str, e);
        }
    }
}
