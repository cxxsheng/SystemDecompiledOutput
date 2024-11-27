package com.android.server.biometrics.sensors.face.aidl;

/* loaded from: classes.dex */
public class BiometricTestSessionImpl extends android.hardware.biometrics.ITestSession.Stub {
    private static final java.lang.String TAG = "face/aidl/BiometricTestSessionImpl";

    @android.annotation.NonNull
    private final android.hardware.biometrics.ITestSessionCallback mCallback;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.aidl.FaceProvider mProvider;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.aidl.Sensor mSensor;
    private final int mSensorId;
    private final android.hardware.face.IFaceServiceReceiver mReceiver = new android.hardware.face.IFaceServiceReceiver.Stub() { // from class: com.android.server.biometrics.sensors.face.aidl.BiometricTestSessionImpl.1
        public void onEnrollResult(android.hardware.face.Face face, int i) {
        }

        public void onAcquired(int i, int i2) {
        }

        public void onAuthenticationSucceeded(android.hardware.face.Face face, int i, boolean z) {
        }

        public void onFaceDetected(int i, int i2, boolean z) {
        }

        public void onAuthenticationFailed() {
        }

        public void onError(int i, int i2) {
        }

        public void onRemoved(android.hardware.face.Face face, int i) {
        }

        public void onFeatureSet(boolean z, int i) {
        }

        public void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) {
        }

        public void onChallengeGenerated(int i, int i2, long j) {
        }

        public void onAuthenticationFrame(android.hardware.face.FaceAuthenticationFrame faceAuthenticationFrame) {
        }

        public void onEnrollmentFrame(android.hardware.face.FaceEnrollFrame faceEnrollFrame) {
        }
    };

    @android.annotation.NonNull
    private final java.util.Set<java.lang.Integer> mEnrollmentIds = new java.util.HashSet();

    @android.annotation.NonNull
    private final java.util.Random mRandom = new java.util.Random();

    BiometricTestSessionImpl(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.face.aidl.FaceProvider faceProvider, @android.annotation.NonNull com.android.server.biometrics.sensors.face.aidl.Sensor sensor) {
        this.mContext = context;
        this.mSensorId = i;
        this.mCallback = iTestSessionCallback;
        this.mProvider = faceProvider;
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
        this.mProvider.scheduleEnroll(this.mSensorId, new android.os.Binder(), new byte[69], i, this.mReceiver, this.mContext.getOpPackageName(), new int[0], null, false, new android.hardware.face.FaceEnrollOptions.Builder().build());
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
        java.util.List<android.hardware.face.Face> biometricsForUser = com.android.server.biometrics.sensors.face.FaceUtils.getInstance(this.mSensorId).getBiometricsForUser(this.mContext, i);
        if (biometricsForUser.isEmpty()) {
            android.util.Slog.w(TAG, "No faces, returning");
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
        android.hardware.biometrics.face.BaseFrame baseFrame = new android.hardware.biometrics.face.BaseFrame();
        baseFrame.acquiredInfo = (byte) i2;
        if (this.mSensor.getScheduler().getCurrentClient() instanceof com.android.server.biometrics.sensors.EnrollClient) {
            android.hardware.biometrics.face.EnrollmentFrame enrollmentFrame = new android.hardware.biometrics.face.EnrollmentFrame();
            enrollmentFrame.data = baseFrame;
            this.mSensor.getSessionForUser(i).getHalSessionCallback().onEnrollmentFrame(enrollmentFrame);
        } else {
            android.hardware.biometrics.face.AuthenticationFrame authenticationFrame = new android.hardware.biometrics.face.AuthenticationFrame();
            authenticationFrame.data = baseFrame;
            this.mSensor.getSessionForUser(i).getHalSessionCallback().onAuthenticationFrame(authenticationFrame);
        }
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
        this.mProvider.scheduleInternalCleanup(this.mSensorId, i, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.aidl.BiometricTestSessionImpl.2
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
                try {
                    android.util.Slog.d(com.android.server.biometrics.sensors.face.aidl.BiometricTestSessionImpl.TAG, "onClientStarted: " + baseClientMonitor);
                    com.android.server.biometrics.sensors.face.aidl.BiometricTestSessionImpl.this.mCallback.onCleanupStarted(baseClientMonitor.getTargetUserId());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.face.aidl.BiometricTestSessionImpl.TAG, "Remote exception", e);
                }
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                try {
                    android.util.Slog.d(com.android.server.biometrics.sensors.face.aidl.BiometricTestSessionImpl.TAG, "onClientFinished: " + baseClientMonitor);
                    com.android.server.biometrics.sensors.face.aidl.BiometricTestSessionImpl.this.mCallback.onCleanupFinished(baseClientMonitor.getTargetUserId());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.face.aidl.BiometricTestSessionImpl.TAG, "Remote exception", e);
                }
            }
        });
    }
}
