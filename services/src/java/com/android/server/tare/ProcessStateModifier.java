package com.android.server.tare;

/* loaded from: classes2.dex */
class ProcessStateModifier extends com.android.server.tare.Modifier {
    private static final int PROC_STATE_BUCKET_BFGS = 3;
    private static final int PROC_STATE_BUCKET_BG = 4;
    private static final int PROC_STATE_BUCKET_FGS = 2;
    private static final int PROC_STATE_BUCKET_NONE = 0;
    private static final int PROC_STATE_BUCKET_TOP = 1;
    private static final java.lang.String TAG = "TARE-" + com.android.server.tare.ProcessStateModifier.class.getSimpleName();
    private final com.android.server.tare.InternalResourceService mIrs;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.SparseArrayMap<java.lang.String, java.lang.Integer> mPackageToUidCache = new android.util.SparseArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mUidProcStateBucketCache = new android.util.SparseIntArray();
    private final android.app.IUidObserver mUidObserver = new android.app.UidObserver() { // from class: com.android.server.tare.ProcessStateModifier.1
        public void onUidStateChanged(int i, int i2, long j, int i3) {
            int procStateBucket = com.android.server.tare.ProcessStateModifier.this.getProcStateBucket(i2);
            synchronized (com.android.server.tare.ProcessStateModifier.this.mLock) {
                try {
                    if (com.android.server.tare.ProcessStateModifier.this.mUidProcStateBucketCache.get(i) != procStateBucket) {
                        com.android.server.tare.ProcessStateModifier.this.mUidProcStateBucketCache.put(i, procStateBucket);
                    }
                    com.android.server.tare.ProcessStateModifier.this.notifyStateChangedLocked(i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onUidGone(int i, boolean z) {
            synchronized (com.android.server.tare.ProcessStateModifier.this.mLock) {
                try {
                    if (com.android.server.tare.ProcessStateModifier.this.mUidProcStateBucketCache.indexOfKey(i) < 0) {
                        android.util.Slog.e(com.android.server.tare.ProcessStateModifier.TAG, "UID " + i + " marked gone but wasn't in cache.");
                        return;
                    }
                    com.android.server.tare.ProcessStateModifier.this.mUidProcStateBucketCache.delete(i);
                    com.android.server.tare.ProcessStateModifier.this.notifyStateChangedLocked(i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProcStateBucket {
    }

    ProcessStateModifier(@android.annotation.NonNull com.android.server.tare.InternalResourceService internalResourceService) {
        this.mIrs = internalResourceService;
    }

    @Override // com.android.server.tare.Modifier
    @com.android.internal.annotations.GuardedBy({"mLock"})
    void setup() {
        try {
            android.app.ActivityManager.getService().registerUidObserver(this.mUidObserver, 3, -1, (java.lang.String) null);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // com.android.server.tare.Modifier
    @com.android.internal.annotations.GuardedBy({"mLock"})
    void tearDown() {
        try {
            android.app.ActivityManager.getService().unregisterUidObserver(this.mUidObserver);
        } catch (android.os.RemoteException e) {
        }
        this.mPackageToUidCache.clear();
        this.mUidProcStateBucketCache.clear();
    }

    long getModifiedPrice(int i, @android.annotation.NonNull java.lang.String str, long j, long j2) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mUidProcStateBucketCache.get(this.mIrs.getUid(i, str), 0);
        }
        switch (i2) {
            case 1:
                return 0L;
            case 2:
                return java.lang.Math.min(j, j2);
            case 3:
                if (j2 <= j) {
                    return j2;
                }
                return (long) (j + ((j2 - j) * 0.5d));
            default:
                return j2;
        }
    }

    @Override // com.android.server.tare.Modifier
    @com.android.internal.annotations.GuardedBy({"mLock"})
    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("Proc state bucket cache = ");
        indentingPrintWriter.println(this.mUidProcStateBucketCache);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getProcStateBucket(int i) {
        if (i <= 2) {
            return 1;
        }
        if (i <= 4) {
            return 2;
        }
        if (i > 5) {
            return 4;
        }
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyStateChangedLocked$0(int i) {
        this.mIrs.onUidStateChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void notifyStateChangedLocked(final int i) {
        com.android.server.tare.TareHandlerThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.tare.ProcessStateModifier$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.tare.ProcessStateModifier.this.lambda$notifyStateChangedLocked$0(i);
            }
        });
    }
}
