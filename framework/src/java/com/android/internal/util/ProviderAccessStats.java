package com.android.internal.util;

/* loaded from: classes5.dex */
public class ProviderAccessStats {
    private final java.lang.Object mLock = new java.lang.Object();
    private final long mStartUptime = android.os.SystemClock.uptimeMillis();
    private final android.util.SparseBooleanArray mAllCallingUids = new android.util.SparseBooleanArray();
    private final android.util.SparseLongArray mQueryStats = new android.util.SparseLongArray(16);
    private final android.util.SparseLongArray mBatchStats = new android.util.SparseLongArray(0);
    private final android.util.SparseLongArray mInsertStats = new android.util.SparseLongArray(0);
    private final android.util.SparseLongArray mUpdateStats = new android.util.SparseLongArray(0);
    private final android.util.SparseLongArray mDeleteStats = new android.util.SparseLongArray(0);
    private final android.util.SparseLongArray mInsertInBatchStats = new android.util.SparseLongArray(0);
    private final android.util.SparseLongArray mUpdateInBatchStats = new android.util.SparseLongArray(0);
    private final android.util.SparseLongArray mDeleteInBatchStats = new android.util.SparseLongArray(0);
    private final android.util.SparseLongArray mOperationDurationMillis = new android.util.SparseLongArray(16);
    private final java.lang.ThreadLocal<com.android.internal.util.ProviderAccessStats.PerThreadData> mThreadLocal = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: com.android.internal.util.ProviderAccessStats$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return com.android.internal.util.ProviderAccessStats.lambda$new$0();
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    static class PerThreadData {
        public int nestCount;
        public long startUptimeMillis;

        private PerThreadData() {
        }
    }

    static /* synthetic */ com.android.internal.util.ProviderAccessStats.PerThreadData lambda$new$0() {
        return new com.android.internal.util.ProviderAccessStats.PerThreadData();
    }

    private void incrementStats(int i, android.util.SparseLongArray sparseLongArray) {
        synchronized (this.mLock) {
            sparseLongArray.put(i, sparseLongArray.get(i) + 1);
            this.mAllCallingUids.put(i, true);
        }
        com.android.internal.util.ProviderAccessStats.PerThreadData perThreadData = this.mThreadLocal.get();
        perThreadData.nestCount++;
        if (perThreadData.nestCount == 1) {
            perThreadData.startUptimeMillis = android.os.SystemClock.uptimeMillis();
        }
    }

    private void incrementStats(int i, boolean z, android.util.SparseLongArray sparseLongArray, android.util.SparseLongArray sparseLongArray2) {
        if (z) {
            sparseLongArray = sparseLongArray2;
        }
        incrementStats(i, sparseLongArray);
    }

    public final void incrementInsertStats(int i, boolean z) {
        incrementStats(i, z, this.mInsertStats, this.mInsertInBatchStats);
    }

    public final void incrementUpdateStats(int i, boolean z) {
        incrementStats(i, z, this.mUpdateStats, this.mUpdateInBatchStats);
    }

    public final void incrementDeleteStats(int i, boolean z) {
        incrementStats(i, z, this.mDeleteStats, this.mDeleteInBatchStats);
    }

    public final void incrementQueryStats(int i) {
        incrementStats(i, this.mQueryStats);
    }

    public final void incrementBatchStats(int i) {
        incrementStats(i, this.mBatchStats);
    }

    public void finishOperation(int i) {
        com.android.internal.util.ProviderAccessStats.PerThreadData perThreadData = this.mThreadLocal.get();
        perThreadData.nestCount--;
        if (perThreadData.nestCount == 0) {
            long max = java.lang.Math.max(1L, android.os.SystemClock.uptimeMillis() - perThreadData.startUptimeMillis);
            synchronized (this.mLock) {
                this.mOperationDurationMillis.put(i, this.mOperationDurationMillis.get(i) + max);
            }
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        synchronized (this.mLock) {
            printWriter.print("  Process uptime: ");
            printWriter.print((android.os.SystemClock.uptimeMillis() - this.mStartUptime) / 60000);
            printWriter.println(" minutes");
            printWriter.println();
            printWriter.print(str);
            printWriter.println("Client activities:");
            printWriter.print(str);
            printWriter.println("  UID        Query  Insert Update Delete   Batch Insert Update Delete          Sec");
            for (int i = 0; i < this.mAllCallingUids.size(); i++) {
                int keyAt = this.mAllCallingUids.keyAt(i);
                printWriter.print(str);
                printWriter.println(java.lang.String.format("  %-9d %6d  %6d %6d %6d  %6d %6d %6d %6d %12.3f", java.lang.Integer.valueOf(keyAt), java.lang.Long.valueOf(this.mQueryStats.get(keyAt)), java.lang.Long.valueOf(this.mInsertStats.get(keyAt)), java.lang.Long.valueOf(this.mUpdateStats.get(keyAt)), java.lang.Long.valueOf(this.mDeleteStats.get(keyAt)), java.lang.Long.valueOf(this.mBatchStats.get(keyAt)), java.lang.Long.valueOf(this.mInsertInBatchStats.get(keyAt)), java.lang.Long.valueOf(this.mUpdateInBatchStats.get(keyAt)), java.lang.Long.valueOf(this.mDeleteInBatchStats.get(keyAt)), java.lang.Double.valueOf(this.mOperationDurationMillis.get(keyAt) / 1000.0d)));
            }
            printWriter.println();
        }
    }
}
