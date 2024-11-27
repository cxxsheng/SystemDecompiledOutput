package android.os;

/* loaded from: classes3.dex */
public final class PerformanceHintManager {
    private final long mNativeManagerPtr;

    private static native long nativeAcquireManager();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeCloseSession(long j);

    private static native long nativeCreateSession(long j, int[] iArr, long j2);

    private static native long nativeGetPreferredUpdateRateNanos(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int[] nativeGetThreadIds(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeReportActualWorkDuration(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeReportActualWorkDuration(long j, long j2, long j3, long j4, long j5);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSendHint(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetPreferPowerEfficiency(long j, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetThreads(long j, int[] iArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeUpdateTargetWorkDuration(long j, long j2);

    public static android.os.PerformanceHintManager create() throws android.os.ServiceManager.ServiceNotFoundException {
        long nativeAcquireManager = nativeAcquireManager();
        if (nativeAcquireManager == 0) {
            throw new android.os.ServiceManager.ServiceNotFoundException(android.content.Context.PERFORMANCE_HINT_SERVICE);
        }
        return new android.os.PerformanceHintManager(nativeAcquireManager);
    }

    private PerformanceHintManager(long j) {
        this.mNativeManagerPtr = j;
    }

    public long getPreferredUpdateRateNanos() {
        return nativeGetPreferredUpdateRateNanos(this.mNativeManagerPtr);
    }

    public android.os.PerformanceHintManager.Session createHintSession(int[] iArr, long j) {
        java.util.Objects.requireNonNull(iArr, "tids cannot be null");
        if (iArr.length == 0) {
            throw new java.lang.IllegalArgumentException("thread id list can't be empty.");
        }
        com.android.internal.util.Preconditions.checkArgumentPositive(j, "the hint target duration should be positive.");
        long nativeCreateSession = nativeCreateSession(this.mNativeManagerPtr, iArr, j);
        if (nativeCreateSession == 0) {
            return null;
        }
        return new android.os.PerformanceHintManager.Session(nativeCreateSession);
    }

    public static class Session implements java.io.Closeable {
        public static final int CPU_LOAD_DOWN = 1;
        public static final int CPU_LOAD_RESET = 2;
        public static final int CPU_LOAD_RESUME = 3;
        public static final int CPU_LOAD_UP = 0;
        public static final int GPU_LOAD_DOWN = 6;
        public static final int GPU_LOAD_RESET = 7;
        public static final int GPU_LOAD_UP = 5;
        private long mNativeSessionPtr;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Hint {
        }

        public Session(long j) {
            this.mNativeSessionPtr = j;
        }

        protected void finalize() throws java.lang.Throwable {
            try {
                close();
            } finally {
                super.finalize();
            }
        }

        public void updateTargetWorkDuration(long j) {
            com.android.internal.util.Preconditions.checkArgumentPositive(j, "the hint target duration should be positive.");
            android.os.PerformanceHintManager.nativeUpdateTargetWorkDuration(this.mNativeSessionPtr, j);
        }

        public void reportActualWorkDuration(long j) {
            com.android.internal.util.Preconditions.checkArgumentPositive(j, "the actual duration should be positive.");
            android.os.PerformanceHintManager.nativeReportActualWorkDuration(this.mNativeSessionPtr, j);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (this.mNativeSessionPtr != 0) {
                android.os.PerformanceHintManager.nativeCloseSession(this.mNativeSessionPtr);
                this.mNativeSessionPtr = 0L;
            }
        }

        public void sendHint(int i) {
            com.android.internal.util.Preconditions.checkArgumentNonNegative(i, "the hint ID should be at least zero.");
            try {
                android.os.PerformanceHintManager.nativeSendHint(this.mNativeSessionPtr, i);
            } finally {
                java.lang.ref.Reference.reachabilityFence(this);
            }
        }

        public void setPreferPowerEfficiency(boolean z) {
            android.os.PerformanceHintManager.nativeSetPreferPowerEfficiency(this.mNativeSessionPtr, z);
        }

        public void setThreads(int[] iArr) {
            if (this.mNativeSessionPtr == 0) {
                return;
            }
            java.util.Objects.requireNonNull(iArr, "tids cannot be null");
            if (iArr.length == 0) {
                throw new java.lang.IllegalArgumentException("Thread id list can't be empty.");
            }
            android.os.PerformanceHintManager.nativeSetThreads(this.mNativeSessionPtr, iArr);
        }

        public int[] getThreadIds() {
            return android.os.PerformanceHintManager.nativeGetThreadIds(this.mNativeSessionPtr);
        }

        public void reportActualWorkDuration(android.os.WorkDuration workDuration) {
            if (workDuration.mWorkPeriodStartTimestampNanos <= 0) {
                throw new java.lang.IllegalArgumentException("the work period start timestamp should be greater than zero.");
            }
            if (workDuration.mActualTotalDurationNanos <= 0) {
                throw new java.lang.IllegalArgumentException("the actual total duration should be greater than zero.");
            }
            if (workDuration.mActualCpuDurationNanos < 0) {
                throw new java.lang.IllegalArgumentException("the actual CPU duration should be greater than or equal to zero.");
            }
            if (workDuration.mActualGpuDurationNanos < 0) {
                throw new java.lang.IllegalArgumentException("the actual GPU duration should be greater than or equal to zero.");
            }
            if (workDuration.mActualCpuDurationNanos + workDuration.mActualGpuDurationNanos <= 0) {
                throw new java.lang.IllegalArgumentException("either the actual CPU duration or the actual GPU duration should be greaterthan zero.");
            }
            android.os.PerformanceHintManager.nativeReportActualWorkDuration(this.mNativeSessionPtr, workDuration.mWorkPeriodStartTimestampNanos, workDuration.mActualTotalDurationNanos, workDuration.mActualCpuDurationNanos, workDuration.mActualGpuDurationNanos);
        }
    }
}
