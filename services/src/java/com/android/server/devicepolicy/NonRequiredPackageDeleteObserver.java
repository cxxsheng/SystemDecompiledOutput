package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class NonRequiredPackageDeleteObserver extends android.content.pm.IPackageDeleteObserver.Stub {
    private static final int PACKAGE_DELETE_TIMEOUT_SEC = 30;
    private final java.util.concurrent.CountDownLatch mLatch;
    private final java.util.concurrent.atomic.AtomicInteger mPackageCount = new java.util.concurrent.atomic.AtomicInteger(0);
    private boolean mSuccess;

    NonRequiredPackageDeleteObserver(int i) {
        this.mLatch = new java.util.concurrent.CountDownLatch(i);
        this.mPackageCount.set(i);
    }

    public void packageDeleted(java.lang.String str, int i) {
        if (i != 1) {
            android.util.Slog.e("DevicePolicyManager", "Failed to delete package: " + str);
            this.mLatch.notifyAll();
            return;
        }
        if (this.mPackageCount.decrementAndGet() == 0) {
            this.mSuccess = true;
            android.util.Slog.i("DevicePolicyManager", "All non-required system apps with launcher icon, and all disallowed apps have been uninstalled.");
        }
        this.mLatch.countDown();
    }

    public boolean awaitPackagesDeletion() {
        try {
            this.mLatch.await(30L, java.util.concurrent.TimeUnit.SECONDS);
        } catch (java.lang.InterruptedException e) {
            android.util.Log.w("DevicePolicyManager", "Interrupted while waiting for package deletion", e);
            java.lang.Thread.currentThread().interrupt();
        }
        return this.mSuccess;
    }
}
