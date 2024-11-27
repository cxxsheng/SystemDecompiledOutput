package com.android.server.pm;

/* loaded from: classes2.dex */
public final class AppsFilterSnapshotImpl extends com.android.server.pm.AppsFilterBase {
    AppsFilterSnapshotImpl(com.android.server.pm.AppsFilterImpl appsFilterImpl) {
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock = appsFilterImpl.mImplicitlyQueryableLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock) {
            try {
                this.mImplicitlyQueryable = appsFilterImpl.mImplicitQueryableSnapshot.snapshot();
                this.mRetainedImplicitlyQueryable = appsFilterImpl.mRetainedImplicitlyQueryableSnapshot.snapshot();
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        this.mImplicitQueryableSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mRetainedImplicitlyQueryableSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock2 = appsFilterImpl.mQueriesViaPackageLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock2) {
            try {
                this.mQueriesViaPackage = appsFilterImpl.mQueriesViaPackageSnapshot.snapshot();
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        this.mQueriesViaPackageSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock3 = appsFilterImpl.mQueriesViaComponentLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock3) {
            try {
                this.mQueriesViaComponent = appsFilterImpl.mQueriesViaComponentSnapshot.snapshot();
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        this.mQueriesViaComponentSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock4 = appsFilterImpl.mQueryableViaUsesLibraryLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock4) {
            try {
                this.mQueryableViaUsesLibrary = appsFilterImpl.mQueryableViaUsesLibrarySnapshot.snapshot();
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        this.mQueryableViaUsesLibrarySnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock5 = appsFilterImpl.mQueryableViaUsesPermissionLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock5) {
            try {
                this.mQueryableViaUsesPermission = appsFilterImpl.mQueryableViaUsesPermissionSnapshot.snapshot();
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        this.mQueryableViaUsesPermissionSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock6 = appsFilterImpl.mForceQueryableLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock6) {
            try {
                this.mForceQueryable = appsFilterImpl.mForceQueryableSnapshot.snapshot();
            } finally {
                com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        this.mForceQueryableSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock7 = appsFilterImpl.mProtectedBroadcastsLock;
        com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
        synchronized (packageManagerTracedLock7) {
            try {
                this.mProtectedBroadcasts = appsFilterImpl.mProtectedBroadcastsSnapshot.snapshot();
            } finally {
            }
        }
        com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        this.mProtectedBroadcastsSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mQueriesViaComponentRequireRecompute = appsFilterImpl.mQueriesViaComponentRequireRecompute;
        this.mForceQueryableByDevicePackageNames = (java.lang.String[]) java.util.Arrays.copyOf(appsFilterImpl.mForceQueryableByDevicePackageNames, appsFilterImpl.mForceQueryableByDevicePackageNames.length);
        this.mSystemAppsQueryable = appsFilterImpl.mSystemAppsQueryable;
        this.mFeatureConfig = appsFilterImpl.mFeatureConfig.snapshot();
        this.mOverlayReferenceMapper = appsFilterImpl.mOverlayReferenceMapper;
        this.mSystemSigningDetails = appsFilterImpl.mSystemSigningDetails;
        this.mCacheReady = appsFilterImpl.mCacheReady;
        if (this.mCacheReady) {
            com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock8 = appsFilterImpl.mCacheLock;
            com.android.server.pm.PackageManagerService.boostPriorityForPackageManagerTracedLockedSection();
            synchronized (packageManagerTracedLock8) {
                try {
                    this.mShouldFilterCache = appsFilterImpl.mShouldFilterCacheSnapshot.snapshot();
                } finally {
                }
            }
            com.android.server.pm.PackageManagerService.resetPriorityAfterPackageManagerTracedLockedSection();
        } else {
            this.mShouldFilterCache = new com.android.server.utils.WatchedSparseBooleanMatrix();
        }
        this.mCacheEnabled = appsFilterImpl.mCacheEnabled;
        this.mShouldFilterCacheSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
        this.mHandler = null;
    }
}
