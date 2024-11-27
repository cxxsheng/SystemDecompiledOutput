package com.android.server.pm;

/* loaded from: classes2.dex */
final class PackageFreezer implements java.lang.AutoCloseable {
    private final dalvik.system.CloseGuard mCloseGuard;
    private final java.util.concurrent.atomic.AtomicBoolean mClosed;

    @android.annotation.Nullable
    private com.android.server.pm.InstallRequest mInstallRequest;
    private final java.lang.String mPackageName;

    @android.annotation.NonNull
    private final com.android.server.pm.PackageManagerService mPm;

    PackageFreezer(com.android.server.pm.PackageManagerService packageManagerService, @android.annotation.Nullable com.android.server.pm.InstallRequest installRequest) {
        this.mClosed = new java.util.concurrent.atomic.AtomicBoolean();
        this.mCloseGuard = dalvik.system.CloseGuard.get();
        this.mPm = packageManagerService;
        this.mPackageName = null;
        this.mClosed.set(true);
        this.mCloseGuard.open("close");
        this.mInstallRequest = installRequest;
        if (this.mInstallRequest != null) {
            this.mInstallRequest.onFreezeStarted();
        }
    }

    PackageFreezer(java.lang.String str, int i, java.lang.String str2, com.android.server.pm.PackageManagerService packageManagerService, int i2, @android.annotation.Nullable com.android.server.pm.InstallRequest installRequest) {
        com.android.server.pm.PackageSetting packageLPr;
        this.mClosed = new java.util.concurrent.atomic.AtomicBoolean();
        this.mCloseGuard = dalvik.system.CloseGuard.get();
        this.mPm = packageManagerService;
        this.mPackageName = str;
        this.mInstallRequest = installRequest;
        if (this.mInstallRequest != null) {
            this.mInstallRequest.onFreezeStarted();
        }
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mPm.mFrozenPackages.put(this.mPackageName, java.lang.Integer.valueOf(this.mPm.mFrozenPackages.getOrDefault(this.mPackageName, 0).intValue() + 1));
                packageLPr = this.mPm.mSettings.getPackageLPr(this.mPackageName);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        if (packageLPr != null) {
            this.mPm.killApplication(packageLPr.getPackageName(), packageLPr.getAppId(), i, str2, i2);
        }
        this.mCloseGuard.open("close");
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            this.mCloseGuard.warnIfOpen();
            close();
        } finally {
            super.finalize();
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock) {
                try {
                    int intValue = this.mPm.mFrozenPackages.getOrDefault(this.mPackageName, 0).intValue() - 1;
                    if (intValue > 0) {
                        this.mPm.mFrozenPackages.put(this.mPackageName, java.lang.Integer.valueOf(intValue));
                    } else {
                        this.mPm.mFrozenPackages.remove(this.mPackageName);
                    }
                } catch (java.lang.Throwable th) {
                    com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                    throw th;
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        }
        if (this.mInstallRequest != null) {
            this.mInstallRequest.onFreezeCompleted();
            this.mInstallRequest = null;
        }
    }
}
