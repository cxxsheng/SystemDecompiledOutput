package com.android.server.pm;

/* loaded from: classes2.dex */
abstract class AppsFilterLocked extends com.android.server.pm.AppsFilterBase {
    protected final com.android.server.pm.PackageManagerTracedLock mForceQueryableLock = new com.android.server.pm.PackageManagerTracedLock();
    protected final com.android.server.pm.PackageManagerTracedLock mQueriesViaPackageLock = new com.android.server.pm.PackageManagerTracedLock();
    protected final com.android.server.pm.PackageManagerTracedLock mQueriesViaComponentLock = new com.android.server.pm.PackageManagerTracedLock();
    protected final com.android.server.pm.PackageManagerTracedLock mImplicitlyQueryableLock = new com.android.server.pm.PackageManagerTracedLock();
    protected final com.android.server.pm.PackageManagerTracedLock mQueryableViaUsesLibraryLock = new com.android.server.pm.PackageManagerTracedLock();
    protected final com.android.server.pm.PackageManagerTracedLock mProtectedBroadcastsLock = new com.android.server.pm.PackageManagerTracedLock();
    protected final com.android.server.pm.PackageManagerTracedLock mQueryableViaUsesPermissionLock = new com.android.server.pm.PackageManagerTracedLock();
    protected final com.android.server.pm.PackageManagerTracedLock mCacheLock = new com.android.server.pm.PackageManagerTracedLock();

    AppsFilterLocked() {
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected boolean isForceQueryable(int i) {
        boolean isForceQueryable;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mForceQueryableLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                isForceQueryable = super.isForceQueryable(i);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return isForceQueryable;
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected boolean isQueryableViaPackage(int i, int i2) {
        boolean isQueryableViaPackage;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mQueriesViaPackageLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                isQueryableViaPackage = super.isQueryableViaPackage(i, i2);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return isQueryableViaPackage;
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected boolean isQueryableViaComponent(int i, int i2) {
        boolean isQueryableViaComponent;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mQueriesViaComponentLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                isQueryableViaComponent = super.isQueryableViaComponent(i, i2);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return isQueryableViaComponent;
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected boolean isImplicitlyQueryable(int i, int i2) {
        boolean isImplicitlyQueryable;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mImplicitlyQueryableLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                isImplicitlyQueryable = super.isImplicitlyQueryable(i, i2);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return isImplicitlyQueryable;
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected boolean isRetainedImplicitlyQueryable(int i, int i2) {
        boolean isRetainedImplicitlyQueryable;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mImplicitlyQueryableLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                isRetainedImplicitlyQueryable = super.isRetainedImplicitlyQueryable(i, i2);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return isRetainedImplicitlyQueryable;
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected boolean isQueryableViaUsesLibrary(int i, int i2) {
        boolean isQueryableViaUsesLibrary;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mQueryableViaUsesLibraryLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                isQueryableViaUsesLibrary = super.isQueryableViaUsesLibrary(i, i2);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return isQueryableViaUsesLibrary;
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected boolean isQueryableViaUsesPermission(int i, int i2) {
        boolean isQueryableViaUsesPermission;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mQueryableViaUsesPermissionLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                isQueryableViaUsesPermission = super.isQueryableViaUsesPermission(i, i2);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return isQueryableViaUsesPermission;
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected boolean shouldFilterApplicationUsingCache(int i, int i2, int i3) {
        boolean shouldFilterApplicationUsingCache;
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mCacheLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                shouldFilterApplicationUsingCache = super.shouldFilterApplicationUsingCache(i, i2, i3);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        return shouldFilterApplicationUsingCache;
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected void dumpForceQueryable(java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.Integer num, com.android.server.pm.AppsFilterBase.ToString<java.lang.Integer> toString) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mForceQueryableLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpForceQueryable(printWriter, num, toString);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected void dumpQueriesViaPackage(java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.Integer num, com.android.server.pm.AppsFilterBase.ToString<java.lang.Integer> toString) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mQueriesViaPackageLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpQueriesViaPackage(printWriter, num, toString);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected void dumpQueriesViaComponent(java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.Integer num, com.android.server.pm.AppsFilterBase.ToString<java.lang.Integer> toString) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mQueriesViaComponentLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpQueriesViaComponent(printWriter, num, toString);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected void dumpQueriesViaImplicitlyQueryable(java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.Integer num, int[] iArr, com.android.server.pm.AppsFilterBase.ToString<java.lang.Integer> toString) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mImplicitlyQueryableLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpQueriesViaImplicitlyQueryable(printWriter, num, iArr, toString);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }

    @Override // com.android.server.pm.AppsFilterBase
    protected void dumpQueriesViaUsesLibrary(java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.Integer num, com.android.server.pm.AppsFilterBase.ToString<java.lang.Integer> toString) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = this.mQueryableViaUsesLibraryLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                super.dumpQueriesViaUsesLibrary(printWriter, num, toString);
            } catch (java.lang.Throwable th) {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
                throw th;
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
    }
}
