package com.android.server.biometrics.sensors.face.hidl;

/* loaded from: classes.dex */
public class BiometricTestSessionImpl extends android.hardware.biometrics.ITestSession.Stub {
    private static final java.lang.String TAG = "BiometricTestSessionImpl";

    @android.annotation.NonNull
    private final android.hardware.biometrics.ITestSessionCallback mCallback;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.hidl.Face10 mFace10;

    @android.annotation.NonNull
    private final com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController mHalResultController;
    private final int mSensorId;
    private final android.hardware.face.IFaceServiceReceiver mReceiver = new android.hardware.face.IFaceServiceReceiver.Stub() { // from class: com.android.server.biometrics.sensors.face.hidl.BiometricTestSessionImpl.1
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

    BiometricTestSessionImpl(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull android.hardware.biometrics.ITestSessionCallback iTestSessionCallback, @android.annotation.NonNull com.android.server.biometrics.sensors.face.hidl.Face10 face10, @android.annotation.NonNull com.android.server.biometrics.sensors.face.hidl.Face10.HalResultController halResultController) {
        this.mContext = context;
        this.mSensorId = i;
        this.mCallback = iTestSessionCallback;
        this.mFace10 = face10;
        this.mHalResultController = halResultController;
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void setTestHalEnabled(boolean z) {
        super.setTestHalEnabled_enforcePermission();
        this.mFace10.setTestHalEnabled(z);
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void startEnroll(int i) {
        super.startEnroll_enforcePermission();
        this.mFace10.scheduleEnroll(this.mSensorId, new android.os.Binder(), new byte[69], i, this.mReceiver, this.mContext.getOpPackageName(), new int[0], null, false, new android.hardware.face.FaceEnrollOptions.Builder().build());
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void finishEnroll(int i) {
        super.finishEnroll_enforcePermission();
        int nextInt = this.mRandom.nextInt();
        while (this.mEnrollmentIds.contains(java.lang.Integer.valueOf(nextInt))) {
            nextInt = this.mRandom.nextInt();
        }
        this.mEnrollmentIds.add(java.lang.Integer.valueOf(nextInt));
        this.mHalResultController.onEnrollResult(0L, nextInt, i, 0);
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void acceptAuthentication(int i) {
        super.acceptAuthentication_enforcePermission();
        java.util.List<android.hardware.face.Face> biometricsForUser = com.android.server.biometrics.sensors.face.FaceUtils.getLegacyInstance(this.mSensorId).getBiometricsForUser(this.mContext, i);
        if (biometricsForUser.isEmpty()) {
            android.util.Slog.w(TAG, "No faces, returning");
            return;
        }
        this.mHalResultController.onAuthenticated(0L, biometricsForUser.get(0).getBiometricId(), i, new java.util.ArrayList<>(java.util.Collections.nCopies(69, (byte) 0)));
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void rejectAuthentication(int i) {
        super.rejectAuthentication_enforcePermission();
        this.mHalResultController.onAuthenticated(0L, 0, i, null);
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void notifyAcquired(int i, int i2) {
        super.notifyAcquired_enforcePermission();
        this.mHalResultController.onAcquired(0L, i, i2, 0);
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void notifyError(int i, int i2) {
        super.notifyError_enforcePermission();
        this.mHalResultController.onError(0L, i, i2, 0);
    }

    @android.annotation.EnforcePermission("android.permission.TEST_BIOMETRIC")
    public void cleanupInternalState(int i) {
        super.cleanupInternalState_enforcePermission();
        this.mFace10.scheduleInternalCleanup(this.mSensorId, i, new com.android.server.biometrics.sensors.ClientMonitorCallback() { // from class: com.android.server.biometrics.sensors.face.hidl.BiometricTestSessionImpl.2
            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientStarted(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor) {
                try {
                    com.android.server.biometrics.sensors.face.hidl.BiometricTestSessionImpl.this.mCallback.onCleanupStarted(baseClientMonitor.getTargetUserId());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.face.hidl.BiometricTestSessionImpl.TAG, "Remote exception", e);
                }
            }

            @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
            public void onClientFinished(@android.annotation.NonNull com.android.server.biometrics.sensors.BaseClientMonitor baseClientMonitor, boolean z) {
                try {
                    com.android.server.biometrics.sensors.face.hidl.BiometricTestSessionImpl.this.mCallback.onCleanupFinished(baseClientMonitor.getTargetUserId());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.biometrics.sensors.face.hidl.BiometricTestSessionImpl.TAG, "Remote exception", e);
                }
            }
        });
    }
}
