package com.android.server.pm;

/* loaded from: classes2.dex */
public final class DomainVerificationConnection implements com.android.server.pm.verify.domain.DomainVerificationManagerInternal.Connection, com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1.Connection, com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV2.Connection {
    final com.android.server.pm.PackageManagerService mPm;
    final com.android.server.pm.UserManagerInternal mUmInternal;

    DomainVerificationConnection(com.android.server.pm.PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
        this.mUmInternal = (com.android.server.pm.UserManagerInternal) this.mPm.mInjector.getLocalService(com.android.server.pm.UserManagerInternal.class);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal.Connection
    public void scheduleWriteSettings() {
        this.mPm.scheduleWriteSettings();
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal.Connection
    public int getCallingUid() {
        return android.os.Binder.getCallingUid();
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal.Connection
    public int getCallingUserId() {
        return android.os.UserHandle.getCallingUserId();
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal.Connection, com.android.server.pm.verify.domain.proxy.DomainVerificationProxy.BaseConnection
    public void schedule(int i, @android.annotation.Nullable java.lang.Object obj) {
        android.os.Message obtainMessage = this.mPm.mHandler.obtainMessage(27);
        obtainMessage.arg1 = i;
        obtainMessage.obj = obj;
        this.mPm.mHandler.sendMessage(obtainMessage);
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy.BaseConnection
    public long getPowerSaveTempWhitelistAppDuration() {
        return com.android.server.pm.VerificationUtils.getDefaultVerificationTimeout(this.mPm.mContext);
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy.BaseConnection
    public com.android.server.DeviceIdleInternal getDeviceIdleInternal() {
        return (com.android.server.DeviceIdleInternal) this.mPm.mInjector.getLocalService(com.android.server.DeviceIdleInternal.class);
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxy.BaseConnection
    public boolean isCallerPackage(int i, @android.annotation.NonNull java.lang.String str) {
        return i == this.mPm.snapshotComputer().getPackageUid(str, 0L, android.os.UserHandle.getUserId(i));
    }

    @Override // com.android.server.pm.verify.domain.proxy.DomainVerificationProxyV1.Connection
    @android.annotation.Nullable
    public com.android.server.pm.pkg.AndroidPackage getPackage(@android.annotation.NonNull java.lang.String str) {
        return this.mPm.snapshotComputer().getPackage(str);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationEnforcer.Callback
    public boolean filterAppAccess(java.lang.String str, int i, int i2) {
        return this.mPm.snapshotComputer().filterAppAccess(str, i, i2, true);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal.Connection
    public int[] getAllUserIds() {
        return this.mUmInternal.getUserIds();
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationEnforcer.Callback
    public boolean doesUserExist(int i) {
        return this.mUmInternal.exists(i);
    }

    @Override // com.android.server.pm.verify.domain.DomainVerificationManagerInternal.Connection
    @android.annotation.NonNull
    public com.android.server.pm.Computer snapshot() {
        return this.mPm.snapshotComputer();
    }
}
