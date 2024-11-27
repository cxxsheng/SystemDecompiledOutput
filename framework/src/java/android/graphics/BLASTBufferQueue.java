package android.graphics;

/* loaded from: classes.dex */
public final class BLASTBufferQueue {
    public long mNativeObject;

    public interface TransactionHangCallback {
        void onTransactionHang(java.lang.String str);
    }

    private static native void nativeApplyPendingTransactions(long j, long j2);

    private static native void nativeClearSyncTransaction(long j);

    private static native long nativeCreate(java.lang.String str, boolean z);

    private static native void nativeDestroy(long j);

    private static native android.view.SurfaceControl.Transaction nativeGatherPendingTransactions(long j, long j2);

    private static native long nativeGetLastAcquiredFrameNum(long j);

    private static native android.view.Surface nativeGetSurface(long j, boolean z);

    private static native boolean nativeIsSameSurfaceControl(long j, long j2);

    private static native void nativeMergeWithNextTransaction(long j, long j2, long j3);

    private static native void nativeSetTransactionHangCallback(long j, android.graphics.BLASTBufferQueue.TransactionHangCallback transactionHangCallback);

    private static native void nativeStopContinuousSyncTransaction(long j);

    private static native boolean nativeSyncNextTransaction(long j, java.util.function.Consumer<android.view.SurfaceControl.Transaction> consumer, boolean z);

    private static native void nativeUpdate(long j, long j2, long j3, long j4, int i);

    public BLASTBufferQueue(java.lang.String str, android.view.SurfaceControl surfaceControl, int i, int i2, int i3) {
        this(str, true);
        update(surfaceControl, i, i2, i3);
    }

    public BLASTBufferQueue(java.lang.String str, boolean z) {
        this.mNativeObject = nativeCreate(str, z);
    }

    public void destroy() {
        nativeDestroy(this.mNativeObject);
        this.mNativeObject = 0L;
    }

    public android.view.Surface createSurface() {
        return nativeGetSurface(this.mNativeObject, false);
    }

    public android.view.Surface createSurfaceWithHandle() {
        return nativeGetSurface(this.mNativeObject, true);
    }

    public boolean syncNextTransaction(boolean z, java.util.function.Consumer<android.view.SurfaceControl.Transaction> consumer) {
        return nativeSyncNextTransaction(this.mNativeObject, consumer, z);
    }

    public boolean syncNextTransaction(java.util.function.Consumer<android.view.SurfaceControl.Transaction> consumer) {
        return syncNextTransaction(true, consumer);
    }

    public void stopContinuousSyncTransaction() {
        nativeStopContinuousSyncTransaction(this.mNativeObject);
    }

    public void clearSyncTransaction() {
        nativeClearSyncTransaction(this.mNativeObject);
    }

    public void update(android.view.SurfaceControl surfaceControl, int i, int i2, int i3) {
        nativeUpdate(this.mNativeObject, surfaceControl.mNativeObject, i, i2, i3);
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mNativeObject != 0) {
                nativeDestroy(this.mNativeObject);
            }
        } finally {
            super.finalize();
        }
    }

    public void mergeWithNextTransaction(android.view.SurfaceControl.Transaction transaction, long j) {
        nativeMergeWithNextTransaction(this.mNativeObject, transaction.mNativeObject, j);
    }

    public void mergeWithNextTransaction(long j, long j2) {
        nativeMergeWithNextTransaction(this.mNativeObject, j, j2);
    }

    public void applyPendingTransactions(long j) {
        nativeApplyPendingTransactions(this.mNativeObject, j);
    }

    public long getLastAcquiredFrameNum() {
        return nativeGetLastAcquiredFrameNum(this.mNativeObject);
    }

    public boolean isSameSurfaceControl(android.view.SurfaceControl surfaceControl) {
        return nativeIsSameSurfaceControl(this.mNativeObject, surfaceControl.mNativeObject);
    }

    public android.view.SurfaceControl.Transaction gatherPendingTransactions(long j) {
        return nativeGatherPendingTransactions(this.mNativeObject, j);
    }

    public void setTransactionHangCallback(android.graphics.BLASTBufferQueue.TransactionHangCallback transactionHangCallback) {
        nativeSetTransactionHangCallback(this.mNativeObject, transactionHangCallback);
    }
}
