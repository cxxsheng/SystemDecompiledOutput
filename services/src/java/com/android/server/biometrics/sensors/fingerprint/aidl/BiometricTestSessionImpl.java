package com.android.server.biometrics.sensors.fingerprint.aidl;

/* loaded from: classes.dex */
class BiometricTestSessionImpl extends android.hardware.biometrics.ITestSession.Stub {
    private static final java.lang.String TAG = "fp/aidl/BiometricTestSessionImpl";

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.BiometricStateCallback mBiometricStateCallback;

    @android.annotation.NonNull
    private final android.hardware.biometrics.ITestSessionCallback mCallback;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider mProvider;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.fingerprint.aidl.Sensor mSensor;
    private final int mSensorId;
    private final android.hardware.fingerprint.IFingerprintServiceReceiver mReceiver = new android.hardware.fingerprint.IFingerprintServiceReceiver.Stub() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.BiometricTestSessionImpl.1
        public void onEnrollResult(android.hardware.fingerprint.Fingerprint fingerprint, int i) {
        }

        public void onAcquired(int i, int i2) {
        }

        public void onAuthenticationSucceeded(android.hardware.fingerprint.Fingerprint fingerprint, int i, boolean z) {
        }

        public void onFingerprintDetected(int i, int i2, boolean z) {
        }

        public void onAuthenticationFailed() {
        }

        public void onError(int i, int i2) {
        }

        public void onRemoved(android.hardware.fingerprint.Fingerprint fingerprint, int i) {
        }

        public void onChallengeGenerated(int i, int i2, long j) {
        }

        public void onUdfpsPointerDown(int i) {
        }

        public void onUdfpsPointerUp(int i) {
        }

        public void onUdfpsOverlayShown() {
        }
    };

    @android.annotation.NonNull
    private final java.util.Set<java.lang.Integer> mEnrollmentIds = new java.util.HashSet();

    @android.annotation.NonNull
    private final java.util.Random mRandom = new java.util.Random();

    BiometricTestSessionImpl(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.BiometricStateCallback biometricStateCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider fingerprintProvider, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.aidl.Sensor sensor) {
        this.mContext = context;
        this.mSensorId = i;
        this.mCallback = iTestSessionCallback;
        this.mBiometricStateCallback = biometricStateCallback;
        this.mProvider = fingerprintProvider;
        this.mSensor = sensor;
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void setTestHalEnabled(boolean z) {
        super.setTestHalEnabled_enforcePermission();
        this.mProvider.setTestHalEnabled(z);
        this.mSensor.setTestHalEnabled(z);
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void startEnroll(int i) {
        super.startEnroll_enforcePermission();
        this.mProvider.scheduleEnroll(this.mSensorId, new android.os.Binder(), new byte[69], i, this.mReceiver, this.mContext.getOpPackageName(), 2, new android.hardware.fingerprint.FingerprintEnrollOptions.Builder().build());
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void finishEnroll(int i) {
        super.finishEnroll_enforcePermission();
        int nextInt = this.mRandom.nextInt();
        while (this.mEnrollmentIds.contains(java.lang.Integer.valueOf(nextInt))) {
            nextInt = this.mRandom.nextInt();
        }
        this.mEnrollmentIds.add(java.lang.Integer.valueOf(nextInt));
        this.mSensor.getSessionForUser(i).getHalSessionCallback().onEnrollmentProgress(nextInt, 0);
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void acceptAuthentication(int i) {
        super.acceptAuthentication_enforcePermission();
        java.util.List<android.hardware.fingerprint.Fingerprint> biometricsForUser = com.android.server.biometrics.sensors.fingerprint.FingerprintUtils.getInstance(this.mSensorId).getBiometricsForUser(this.mContext, i);
        if (biometricsForUser.isEmpty()) {
            android.util.Slog.w(TAG, "No fingerprints, returning");
        } else {
            this.mSensor.getSessionForUser(i).getHalSessionCallback().onAuthenticationSucceeded(biometricsForUser.get(0).getBiometricId(), com.android.server.biometrics.HardwareAuthTokenUtils.toHardwareAuthToken(new byte[69]));
        }
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void rejectAuthentication(int i) {
        super.rejectAuthentication_enforcePermission();
        this.mSensor.getSessionForUser(i).getHalSessionCallback().onAuthenticationFailed();
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void notifyAcquired(int i, int i2) {
        super.notifyAcquired_enforcePermission();
        this.mSensor.getSessionForUser(i).getHalSessionCallback().onAcquired((byte) i2, 0);
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void notifyError(int i, int i2) {
        super.notifyError_enforcePermission();
        this.mSensor.getSessionForUser(i).getHalSessionCallback().onError((byte) i2, 0);
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void cleanupInternalState(int i) {
        super.cleanupInternalState_enforcePermission();
        android.util.Slog.d(TAG, "cleanupInternalState: " + i);
        this.mProvider.scheduleInternalCleanup(this.mSensorId, i, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.fingerprint.aidl.BiometricTestSessionImpl.2
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
                try {
                    android.util.Slog.d(com.android.server.biometrics.sensors.fingerprint.aidl.BiometricTestSessionImpl.TAG, "onClientStarted: " + baseClientMonitor);
                    com.android.server.biometrics.sensors.fingerprint.aidl.BiometricTestSessionImpl.this.mCallback.onCleanupStarted(baseClientMonitor.getTargetUserId());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.aidl.BiometricTestSessionImpl.TAG, "Remote exception", e);
                }
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                try {
                    android.util.Slog.d(com.android.server.biometrics.sensors.fingerprint.aidl.BiometricTestSessionImpl.TAG, "onClientFinished: " + baseClientMonitor);
                    com.android.server.biometrics.sensors.fingerprint.aidl.BiometricTestSessionImpl.this.mCallback.onCleanupFinished(baseClientMonitor.getTargetUserId());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.fingerprint.aidl.BiometricTestSessionImpl.TAG, "Remote exception", e);
                }
            }
        });
    }
}
