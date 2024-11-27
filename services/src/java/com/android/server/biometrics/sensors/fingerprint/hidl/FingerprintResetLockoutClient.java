package com.android.server.biometrics.sensors.fingerprint.hidl;

/* loaded from: classes.dex */
public class FingerprintResetLockoutClient extends com.android.server.biometrics.sensors.BaseClientMonitor {

    @android.annotation.NonNull
    final com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl mLockoutTracker;

    public FingerprintResetLockoutClient(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull com.android.server.biometrics.log.BiometricLogger biometricLogger, @android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, @android.annotation.NonNull com.android.server.biometrics.sensors.fingerprint.hidl.LockoutFrameworkImpl lockoutFrameworkImpl) {
        super(context, null, null, i, str, 0, i2, biometricLogger, biometricContext);
        this.mLockoutTracker = lockoutFrameworkImpl;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(@android.annotation.NonNull com.android.server.biometrics.sensors.ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        this.mLockoutTracker.resetFailedAttemptsForUser(true, getTargetUserId());
        clientMonitorCallback.onClientFinished(this, true);
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public boolean interruptsPrecedingClients() {
        return true;
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 12;
    }
}
