package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
public class FingerprintUpdateActiveUserClient extends com.android.server.biometrics.sensors.StartUserClient<android.hardware.biometrics.fingerprint.ISession, com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> {
    private static final java.lang.String FP_DATA_DIR = "fpdata";
    private static final java.lang.String TAG = "FingerprintUpdateActiveUserClient";
    private final java.util.Map<java.lang.Integer, java.lang.Long> mAuthenticatorIds;
    private final java.util.function.Supplier<java.lang.Integer> mCurrentUserId;
    private java.io.File mDirectory;
    private final boolean mForceUpdateAuthenticatorId;
    private final boolean mHasEnrolledBiometrics;

    FingerprintUpdateActiveUserClient(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.fingerprint.ISession> supplier, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull java.util.function.Supplier<java.lang.Integer> supplier2, boolean z, @android.annotation.NonNull java.util.Map<java.lang.Integer, java.lang.Long> map, boolean z2, @android.annotation.NonNull com.android.server.biometrics.sensors.StartUserClient.UserStartedCallback<com.android.server.biometrics.sensors.fingerprint.aidl.AidlSession> userStartedCallback) {
        super(context, supplier, null, i, i2, biometricLogger, biometricContext, userStartedCallback);
        this.mCurrentUserId = supplier2;
        this.mForceUpdateAuthenticatorId = z2;
        this.mHasEnrolledBiometrics = z;
        this.mAuthenticatorIds = map;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        java.io.File dataVendorDeDirectory;
        super.start(clientMonitorCallback);
        if (this.mCurrentUserId.get().intValue() == getTargetUserId() && !this.mForceUpdateAuthenticatorId) {
            android.util.Slog.d(TAG, "Already user: " + this.mCurrentUserId + ", returning");
            this.mUserStartedCallback.onUserStarted(getTargetUserId(), null, 0);
            clientMonitorCallback.onClientFinished(this, true);
            return;
        }
        int i = android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT;
        if (i < 1) {
            android.util.Slog.e(TAG, "First SDK version " + i + " is invalid; must be at least VERSION_CODES.BASE");
        }
        if (i <= 27) {
            dataVendorDeDirectory = android.os.Environment.getUserSystemDirectory(getTargetUserId());
        } else {
            dataVendorDeDirectory = android.os.Environment.getDataVendorDeDirectory(getTargetUserId());
        }
        this.mDirectory = new java.io.File(dataVendorDeDirectory, FP_DATA_DIR);
        if (!this.mDirectory.exists()) {
            if (!this.mDirectory.mkdir()) {
                android.util.Slog.e(TAG, "Cannot make directory: " + this.mDirectory.getAbsolutePath());
                clientMonitorCallback.onClientFinished(this, false);
                return;
            }
            if (!android.os.SELinux.restorecon(this.mDirectory)) {
                android.util.Slog.e(TAG, "Restorecons failed. Directory will have wrong label.");
                clientMonitorCallback.onClientFinished(this, false);
                return;
            }
        }
        startHalOperation();
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    public void unableToStart() {
    }

    @Override // com.android.server.biometrics.sensors.HalClientMonitor
    protected void startHalOperation() {
        try {
            int targetUserId = getTargetUserId();
            android.util.Slog.d(TAG, "Setting active user: " + targetUserId);
            com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSessionAdapter hidlToAidlSessionAdapter = (com.android.server.biometrics.sensors.fingerprint.hidl.HidlToAidlSessionAdapter) getFreshDaemon();
            hidlToAidlSessionAdapter.setActiveGroup(targetUserId, this.mDirectory.getAbsolutePath());
            this.mAuthenticatorIds.put(java.lang.Integer.valueOf(targetUserId), java.lang.Long.valueOf(this.mHasEnrolledBiometrics ? hidlToAidlSessionAdapter.getAuthenticatorIdForUpdateClient() : 0L));
            this.mUserStartedCallback.onUserStarted(targetUserId, null, 0);
            this.mCallback.onClientFinished(this, true);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to setActiveGroup: " + e);
            this.mCallback.onClientFinished(this, false);
        }
    }

    @Override // com.android.server.biometrics.sensors.StartUserClient, com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 1;
    }
}
