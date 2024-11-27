package com.android.server.locksettings;

/* loaded from: classes2.dex */
public class BiometricDeferredQueue {
    private static final java.lang.String TAG = "BiometricDeferredQueue";

    @android.annotation.Nullable
    private android.hardware.biometrics.BiometricManager mBiometricManager;

    @android.annotation.Nullable
    private android.hardware.face.FaceManager mFaceManager;

    @android.annotation.Nullable
    private com.android.server.locksettings.BiometricDeferredQueue.FaceResetLockoutTask mFaceResetLockoutTask;

    @android.annotation.Nullable
    private android.hardware.fingerprint.FingerprintManager mFingerprintManager;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final com.android.server.locksettings.SyntheticPasswordManager mSpManager;
    private final com.android.server.locksettings.BiometricDeferredQueue.FaceResetLockoutTask.FinishCallback mFaceFinishCallback = new com.android.server.locksettings.BiometricDeferredQueue.FaceResetLockoutTask.FinishCallback() { // from class: com.android.server.locksettings.BiometricDeferredQueue$$ExternalSyntheticLambda2
        @Override // com.android.server.locksettings.BiometricDeferredQueue.FaceResetLockoutTask.FinishCallback
        public final void onFinished() {
            com.android.server.locksettings.BiometricDeferredQueue.this.lambda$new$0();
        }
    };

    @android.annotation.NonNull
    private final java.util.ArrayList<com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo> mPendingResetLockoutsForFingerprint = new java.util.ArrayList<>();

    @android.annotation.NonNull
    private final java.util.ArrayList<com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo> mPendingResetLockoutsForFace = new java.util.ArrayList<>();

    @android.annotation.NonNull
    private final java.util.ArrayList<com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo> mPendingResetLockouts = new java.util.ArrayList<>();

    private static class UserAuthInfo {

        @android.annotation.NonNull
        final byte[] gatekeeperPassword;
        final int userId;

        UserAuthInfo(int i, @android.annotation.NonNull byte[] bArr) {
            this.userId = i;
            this.gatekeeperPassword = bArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class FaceResetLockoutTask implements android.hardware.face.FaceManager.GenerateChallengeCallback {

        @android.annotation.NonNull
        android.hardware.face.FaceManager faceManager;

        @android.annotation.NonNull
        com.android.server.locksettings.BiometricDeferredQueue.FaceResetLockoutTask.FinishCallback finishCallback;

        @android.annotation.NonNull
        java.util.List<com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo> pendingResetLockuts;

        @android.annotation.NonNull
        java.util.Set<java.lang.Integer> sensorIds;

        @android.annotation.NonNull
        com.android.server.locksettings.SyntheticPasswordManager spManager;

        interface FinishCallback {
            void onFinished();
        }

        FaceResetLockoutTask(@android.annotation.NonNull com.android.server.locksettings.BiometricDeferredQueue.FaceResetLockoutTask.FinishCallback finishCallback, @android.annotation.NonNull android.hardware.face.FaceManager faceManager, @android.annotation.NonNull com.android.server.locksettings.SyntheticPasswordManager syntheticPasswordManager, @android.annotation.NonNull java.util.Set<java.lang.Integer> set, @android.annotation.NonNull java.util.List<com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo> list) {
            this.finishCallback = finishCallback;
            this.faceManager = faceManager;
            this.spManager = syntheticPasswordManager;
            this.sensorIds = set;
            this.pendingResetLockuts = list;
        }

        public void onGenerateChallengeResult(int i, int i2, long j) {
            if (!this.sensorIds.contains(java.lang.Integer.valueOf(i))) {
                android.util.Slog.e(com.android.server.locksettings.BiometricDeferredQueue.TAG, "Unknown sensorId received: " + i);
                return;
            }
            for (com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo userAuthInfo : this.pendingResetLockuts) {
                android.util.Slog.d(com.android.server.locksettings.BiometricDeferredQueue.TAG, "Resetting face lockout for sensor: " + i + ", user: " + userAuthInfo.userId);
                byte[] requestHatFromGatekeeperPassword = com.android.server.locksettings.BiometricDeferredQueue.requestHatFromGatekeeperPassword(this.spManager, userAuthInfo, j);
                if (requestHatFromGatekeeperPassword != null) {
                    this.faceManager.resetLockout(i, userAuthInfo.userId, requestHatFromGatekeeperPassword);
                }
            }
            this.sensorIds.remove(java.lang.Integer.valueOf(i));
            this.faceManager.revokeChallenge(i, i2, j);
            if (this.sensorIds.isEmpty()) {
                android.util.Slog.d(com.android.server.locksettings.BiometricDeferredQueue.TAG, "Done requesting resetLockout for all face sensors");
                this.finishCallback.onFinished();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mFaceResetLockoutTask = null;
    }

    BiometricDeferredQueue(@android.annotation.NonNull com.android.server.locksettings.SyntheticPasswordManager syntheticPasswordManager, @android.annotation.NonNull android.os.Handler handler) {
        this.mSpManager = syntheticPasswordManager;
        this.mHandler = handler;
    }

    public void systemReady(@android.annotation.Nullable android.hardware.fingerprint.FingerprintManager fingerprintManager, @android.annotation.Nullable android.hardware.face.FaceManager faceManager, @android.annotation.Nullable android.hardware.biometrics.BiometricManager biometricManager) {
        this.mFingerprintManager = fingerprintManager;
        this.mFaceManager = faceManager;
        this.mBiometricManager = biometricManager;
    }

    void addPendingLockoutResetForUser(final int i, @android.annotation.NonNull final byte[] bArr) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.locksettings.BiometricDeferredQueue$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.locksettings.BiometricDeferredQueue.this.lambda$addPendingLockoutResetForUser$1(i, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addPendingLockoutResetForUser$1(int i, byte[] bArr) {
        if (this.mFaceManager != null && this.mFaceManager.hasEnrolledTemplates(i)) {
            android.util.Slog.d(TAG, "Face addPendingLockoutResetForUser: " + i);
            this.mPendingResetLockoutsForFace.add(new com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo(i, bArr));
        }
        if (this.mFingerprintManager != null && this.mFingerprintManager.hasEnrolledFingerprints(i)) {
            android.util.Slog.d(TAG, "Fingerprint addPendingLockoutResetForUser: " + i);
            this.mPendingResetLockoutsForFingerprint.add(new com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo(i, bArr));
        }
        if (this.mBiometricManager != null) {
            android.util.Slog.d(TAG, "Fingerprint addPendingLockoutResetForUser: " + i);
            this.mPendingResetLockouts.add(new com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo(i, bArr));
        }
    }

    void processPendingLockoutResets() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.locksettings.BiometricDeferredQueue$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.locksettings.BiometricDeferredQueue.this.lambda$processPendingLockoutResets$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processPendingLockoutResets$2() {
        if (!this.mPendingResetLockoutsForFace.isEmpty()) {
            android.util.Slog.d(TAG, "Processing pending resetLockout for face");
            processPendingLockoutsForFace(new java.util.ArrayList(this.mPendingResetLockoutsForFace));
            this.mPendingResetLockoutsForFace.clear();
        }
        if (!this.mPendingResetLockoutsForFingerprint.isEmpty()) {
            android.util.Slog.d(TAG, "Processing pending resetLockout for fingerprint");
            processPendingLockoutsForFingerprint(new java.util.ArrayList(this.mPendingResetLockoutsForFingerprint));
            this.mPendingResetLockoutsForFingerprint.clear();
        }
        if (!this.mPendingResetLockouts.isEmpty()) {
            android.util.Slog.d(TAG, "Processing pending resetLockouts(Generic)");
            processPendingLockoutsGeneric(new java.util.ArrayList(this.mPendingResetLockouts));
            this.mPendingResetLockouts.clear();
        }
    }

    private void processPendingLockoutsForFingerprint(java.util.List<com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo> list) {
        if (this.mFingerprintManager != null) {
            for (android.hardware.fingerprint.FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal : this.mFingerprintManager.getSensorPropertiesInternal()) {
                if (!fingerprintSensorPropertiesInternal.resetLockoutRequiresHardwareAuthToken) {
                    java.util.Iterator<com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo> it = list.iterator();
                    while (it.hasNext()) {
                        this.mFingerprintManager.resetLockout(fingerprintSensorPropertiesInternal.sensorId, it.next().userId, null);
                    }
                } else if (fingerprintSensorPropertiesInternal.resetLockoutRequiresChallenge) {
                    android.util.Slog.w(TAG, "No fingerprint HAL interface requires HAT with challenge, sensorId: " + fingerprintSensorPropertiesInternal.sensorId);
                } else {
                    for (com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo userAuthInfo : list) {
                        android.util.Slog.d(TAG, "Resetting fingerprint lockout for sensor: " + fingerprintSensorPropertiesInternal.sensorId + ", user: " + userAuthInfo.userId);
                        byte[] requestHatFromGatekeeperPassword = requestHatFromGatekeeperPassword(this.mSpManager, userAuthInfo, 0L);
                        if (requestHatFromGatekeeperPassword != null) {
                            this.mFingerprintManager.resetLockout(fingerprintSensorPropertiesInternal.sensorId, userAuthInfo.userId, requestHatFromGatekeeperPassword);
                        }
                    }
                }
            }
        }
    }

    private void processPendingLockoutsForFace(java.util.List<com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo> list) {
        if (this.mFaceManager != null) {
            if (this.mFaceResetLockoutTask != null) {
                android.util.Slog.w(TAG, "mFaceGenerateChallengeCallback not null, previous operation may be stuck");
            }
            java.util.List<android.hardware.face.FaceSensorPropertiesInternal> sensorPropertiesInternal = this.mFaceManager.getSensorPropertiesInternal();
            android.util.ArraySet arraySet = new android.util.ArraySet();
            java.util.Iterator it = sensorPropertiesInternal.iterator();
            while (it.hasNext()) {
                arraySet.add(java.lang.Integer.valueOf(((android.hardware.face.FaceSensorPropertiesInternal) it.next()).sensorId));
            }
            this.mFaceResetLockoutTask = new com.android.server.locksettings.BiometricDeferredQueue.FaceResetLockoutTask(this.mFaceFinishCallback, this.mFaceManager, this.mSpManager, arraySet, list);
            for (android.hardware.face.FaceSensorPropertiesInternal faceSensorPropertiesInternal : sensorPropertiesInternal) {
                if (faceSensorPropertiesInternal.resetLockoutRequiresHardwareAuthToken) {
                    for (com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo userAuthInfo : list) {
                        if (faceSensorPropertiesInternal.resetLockoutRequiresChallenge) {
                            android.util.Slog.d(TAG, "Generating challenge for sensor: " + faceSensorPropertiesInternal.sensorId + ", user: " + userAuthInfo.userId);
                            this.mFaceManager.generateChallenge(faceSensorPropertiesInternal.sensorId, userAuthInfo.userId, this.mFaceResetLockoutTask);
                        } else {
                            android.util.Slog.d(TAG, "Resetting face lockout for sensor: " + faceSensorPropertiesInternal.sensorId + ", user: " + userAuthInfo.userId);
                            byte[] requestHatFromGatekeeperPassword = requestHatFromGatekeeperPassword(this.mSpManager, userAuthInfo, 0L);
                            if (requestHatFromGatekeeperPassword != null) {
                                this.mFaceManager.resetLockout(faceSensorPropertiesInternal.sensorId, userAuthInfo.userId, requestHatFromGatekeeperPassword);
                            }
                        }
                    }
                } else {
                    android.util.Slog.w(TAG, "Lockout is below the HAL for all face authentication interfaces, sensorId: " + faceSensorPropertiesInternal.sensorId);
                }
            }
        }
    }

    private void processPendingLockoutsGeneric(java.util.List<com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo> list) {
        for (com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo userAuthInfo : list) {
            android.util.Slog.d(TAG, "Resetting biometric lockout for user: " + userAuthInfo.userId);
            byte[] requestHatFromGatekeeperPassword = requestHatFromGatekeeperPassword(this.mSpManager, userAuthInfo, 0L);
            if (requestHatFromGatekeeperPassword != null) {
                this.mBiometricManager.resetLockout(userAuthInfo.userId, requestHatFromGatekeeperPassword);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public static byte[] requestHatFromGatekeeperPassword(@android.annotation.NonNull com.android.server.locksettings.SyntheticPasswordManager syntheticPasswordManager, @android.annotation.NonNull com.android.server.locksettings.BiometricDeferredQueue.UserAuthInfo userAuthInfo, long j) {
        com.android.internal.widget.VerifyCredentialResponse verifyChallengeInternal = syntheticPasswordManager.verifyChallengeInternal(getGatekeeperService(), userAuthInfo.gatekeeperPassword, j, userAuthInfo.userId);
        if (verifyChallengeInternal == null) {
            android.util.Slog.wtf(TAG, "VerifyChallenge failed, null response");
            return null;
        }
        if (verifyChallengeInternal.getResponseCode() != 0) {
            android.util.Slog.wtf(TAG, "VerifyChallenge failed, response: " + verifyChallengeInternal.getResponseCode());
            return null;
        }
        if (verifyChallengeInternal.getGatekeeperHAT() == null) {
            android.util.Slog.e(TAG, "Null HAT received from spManager");
        }
        return verifyChallengeInternal.getGatekeeperHAT();
    }

    @android.annotation.Nullable
    private static synchronized android.service.gatekeeper.IGateKeeperService getGatekeeperService() {
        synchronized (com.android.server.locksettings.BiometricDeferredQueue.class) {
            android.os.IBinder waitForService = android.os.ServiceManager.waitForService("android.service.gatekeeper.IGateKeeperService");
            if (waitForService == null) {
                android.util.Slog.e(TAG, "Unable to acquire GateKeeperService");
                return null;
            }
            return android.service.gatekeeper.IGateKeeperService.Stub.asInterface(waitForService);
        }
    }
}
