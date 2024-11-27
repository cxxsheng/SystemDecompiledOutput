package android.os;

/* loaded from: classes3.dex */
public class PerformanceCollector {
    public static final java.lang.String METRIC_KEY_CPU_TIME = "cpu_time";
    public static final java.lang.String METRIC_KEY_EXECUTION_TIME = "execution_time";
    public static final java.lang.String METRIC_KEY_GC_INVOCATION_COUNT = "gc_invocation_count";
    public static final java.lang.String METRIC_KEY_GLOBAL_ALLOC_COUNT = "global_alloc_count";
    public static final java.lang.String METRIC_KEY_GLOBAL_ALLOC_SIZE = "global_alloc_size";
    public static final java.lang.String METRIC_KEY_GLOBAL_FREED_COUNT = "global_freed_count";
    public static final java.lang.String METRIC_KEY_GLOBAL_FREED_SIZE = "global_freed_size";
    public static final java.lang.String METRIC_KEY_ITERATIONS = "iterations";
    public static final java.lang.String METRIC_KEY_JAVA_ALLOCATED = "java_allocated";
    public static final java.lang.String METRIC_KEY_JAVA_FREE = "java_free";
    public static final java.lang.String METRIC_KEY_JAVA_PRIVATE_DIRTY = "java_private_dirty";
    public static final java.lang.String METRIC_KEY_JAVA_PSS = "java_pss";
    public static final java.lang.String METRIC_KEY_JAVA_SHARED_DIRTY = "java_shared_dirty";
    public static final java.lang.String METRIC_KEY_JAVA_SIZE = "java_size";
    public static final java.lang.String METRIC_KEY_LABEL = "label";
    public static final java.lang.String METRIC_KEY_NATIVE_ALLOCATED = "native_allocated";
    public static final java.lang.String METRIC_KEY_NATIVE_FREE = "native_free";
    public static final java.lang.String METRIC_KEY_NATIVE_PRIVATE_DIRTY = "native_private_dirty";
    public static final java.lang.String METRIC_KEY_NATIVE_PSS = "native_pss";
    public static final java.lang.String METRIC_KEY_NATIVE_SHARED_DIRTY = "native_shared_dirty";
    public static final java.lang.String METRIC_KEY_NATIVE_SIZE = "native_size";
    public static final java.lang.String METRIC_KEY_OTHER_PRIVATE_DIRTY = "other_private_dirty";
    public static final java.lang.String METRIC_KEY_OTHER_PSS = "other_pss";
    public static final java.lang.String METRIC_KEY_OTHER_SHARED_DIRTY = "other_shared_dirty";
    public static final java.lang.String METRIC_KEY_PRE_RECEIVED_TRANSACTIONS = "pre_received_transactions";
    public static final java.lang.String METRIC_KEY_PRE_SENT_TRANSACTIONS = "pre_sent_transactions";
    public static final java.lang.String METRIC_KEY_RECEIVED_TRANSACTIONS = "received_transactions";
    public static final java.lang.String METRIC_KEY_SENT_TRANSACTIONS = "sent_transactions";
    private long mCpuTime;
    private long mExecTime;
    private android.os.Bundle mPerfMeasurement;
    private android.os.Bundle mPerfSnapshot;
    private android.os.PerformanceCollector.PerformanceResultsWriter mPerfWriter;
    private long mSnapshotCpuTime;
    private long mSnapshotExecTime;

    public interface PerformanceResultsWriter {
        void writeBeginSnapshot(java.lang.String str);

        void writeEndSnapshot(android.os.Bundle bundle);

        void writeMeasurement(java.lang.String str, float f);

        void writeMeasurement(java.lang.String str, long j);

        void writeMeasurement(java.lang.String str, java.lang.String str2);

        void writeStartTiming(java.lang.String str);

        void writeStopTiming(android.os.Bundle bundle);
    }

    public PerformanceCollector() {
    }

    public PerformanceCollector(android.os.PerformanceCollector.PerformanceResultsWriter performanceResultsWriter) {
        setPerformanceResultsWriter(performanceResultsWriter);
    }

    public void setPerformanceResultsWriter(android.os.PerformanceCollector.PerformanceResultsWriter performanceResultsWriter) {
        this.mPerfWriter = performanceResultsWriter;
    }

    public void beginSnapshot(java.lang.String str) {
        if (this.mPerfWriter != null) {
            this.mPerfWriter.writeBeginSnapshot(str);
        }
        startPerformanceSnapshot();
    }

    public android.os.Bundle endSnapshot() {
        endPerformanceSnapshot();
        if (this.mPerfWriter != null) {
            this.mPerfWriter.writeEndSnapshot(this.mPerfSnapshot);
        }
        return this.mPerfSnapshot;
    }

    public void startTiming(java.lang.String str) {
        if (this.mPerfWriter != null) {
            this.mPerfWriter.writeStartTiming(str);
        }
        this.mPerfMeasurement = new android.os.Bundle();
        this.mPerfMeasurement.putParcelableArrayList(METRIC_KEY_ITERATIONS, new java.util.ArrayList<>());
        this.mExecTime = android.os.SystemClock.uptimeMillis();
        this.mCpuTime = android.os.Process.getElapsedCpuTime();
    }

    public android.os.Bundle addIteration(java.lang.String str) {
        this.mCpuTime = android.os.Process.getElapsedCpuTime() - this.mCpuTime;
        this.mExecTime = android.os.SystemClock.uptimeMillis() - this.mExecTime;
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("label", str);
        bundle.putLong(METRIC_KEY_EXECUTION_TIME, this.mExecTime);
        bundle.putLong(METRIC_KEY_CPU_TIME, this.mCpuTime);
        this.mPerfMeasurement.getParcelableArrayList(METRIC_KEY_ITERATIONS).add(bundle);
        this.mExecTime = android.os.SystemClock.uptimeMillis();
        this.mCpuTime = android.os.Process.getElapsedCpuTime();
        return bundle;
    }

    public android.os.Bundle stopTiming(java.lang.String str) {
        addIteration(str);
        if (this.mPerfWriter != null) {
            this.mPerfWriter.writeStopTiming(this.mPerfMeasurement);
        }
        return this.mPerfMeasurement;
    }

    public void addMeasurement(java.lang.String str, long j) {
        if (this.mPerfWriter != null) {
            this.mPerfWriter.writeMeasurement(str, j);
        }
    }

    public void addMeasurement(java.lang.String str, float f) {
        if (this.mPerfWriter != null) {
            this.mPerfWriter.writeMeasurement(str, f);
        }
    }

    public void addMeasurement(java.lang.String str, java.lang.String str2) {
        if (this.mPerfWriter != null) {
            this.mPerfWriter.writeMeasurement(str, str2);
        }
    }

    private void startPerformanceSnapshot() {
        this.mPerfSnapshot = new android.os.Bundle();
        android.os.Bundle binderCounts = getBinderCounts();
        for (java.lang.String str : binderCounts.keySet()) {
            this.mPerfSnapshot.putLong("pre_" + str, binderCounts.getLong(str));
        }
        startAllocCounting();
        this.mSnapshotExecTime = android.os.SystemClock.uptimeMillis();
        this.mSnapshotCpuTime = android.os.Process.getElapsedCpuTime();
    }

    private void endPerformanceSnapshot() {
        this.mSnapshotCpuTime = android.os.Process.getElapsedCpuTime() - this.mSnapshotCpuTime;
        this.mSnapshotExecTime = android.os.SystemClock.uptimeMillis() - this.mSnapshotExecTime;
        stopAllocCounting();
        long nativeHeapSize = android.os.Debug.getNativeHeapSize() / 1024;
        long nativeHeapAllocatedSize = android.os.Debug.getNativeHeapAllocatedSize() / 1024;
        long nativeHeapFreeSize = android.os.Debug.getNativeHeapFreeSize() / 1024;
        android.os.Debug.getMemoryInfo(new android.os.Debug.MemoryInfo());
        java.lang.Runtime runtime = java.lang.Runtime.getRuntime();
        long j = runtime.totalMemory() / 1024;
        long freeMemory = runtime.freeMemory() / 1024;
        long j2 = j - freeMemory;
        android.os.Bundle binderCounts = getBinderCounts();
        java.util.Iterator<java.lang.String> it = binderCounts.keySet().iterator();
        while (it.hasNext()) {
            java.util.Iterator<java.lang.String> it2 = it;
            java.lang.String next = it.next();
            this.mPerfSnapshot.putLong(next, binderCounts.getLong(next));
            it = it2;
            freeMemory = freeMemory;
            j2 = j2;
        }
        long j3 = j2;
        long j4 = freeMemory;
        android.os.Bundle allocCounts = getAllocCounts();
        for (java.lang.String str : allocCounts.keySet()) {
            this.mPerfSnapshot.putLong(str, allocCounts.getLong(str));
        }
        this.mPerfSnapshot.putLong(METRIC_KEY_EXECUTION_TIME, this.mSnapshotExecTime);
        this.mPerfSnapshot.putLong(METRIC_KEY_CPU_TIME, this.mSnapshotCpuTime);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_SIZE, nativeHeapSize);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_ALLOCATED, nativeHeapAllocatedSize);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_FREE, nativeHeapFreeSize);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_PSS, r9.nativePss);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_PRIVATE_DIRTY, r9.nativePrivateDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_NATIVE_SHARED_DIRTY, r9.nativeSharedDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_SIZE, j);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_ALLOCATED, j3);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_FREE, j4);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_PSS, r9.dalvikPss);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_PRIVATE_DIRTY, r9.dalvikPrivateDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_JAVA_SHARED_DIRTY, r9.dalvikSharedDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_OTHER_PSS, r9.otherPss);
        this.mPerfSnapshot.putLong(METRIC_KEY_OTHER_PRIVATE_DIRTY, r9.otherPrivateDirty);
        this.mPerfSnapshot.putLong(METRIC_KEY_OTHER_SHARED_DIRTY, r9.otherSharedDirty);
    }

    private static void startAllocCounting() {
        java.lang.Runtime.getRuntime().gc();
        java.lang.Runtime.getRuntime().runFinalization();
        java.lang.Runtime.getRuntime().gc();
        android.os.Debug.resetAllCounts();
        android.os.Debug.startAllocCounting();
    }

    private static void stopAllocCounting() {
        java.lang.Runtime.getRuntime().gc();
        java.lang.Runtime.getRuntime().runFinalization();
        java.lang.Runtime.getRuntime().gc();
        android.os.Debug.stopAllocCounting();
    }

    private static android.os.Bundle getAllocCounts() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putLong(METRIC_KEY_GLOBAL_ALLOC_COUNT, android.os.Debug.getGlobalAllocCount());
        bundle.putLong(METRIC_KEY_GLOBAL_ALLOC_SIZE, android.os.Debug.getGlobalAllocSize());
        bundle.putLong(METRIC_KEY_GLOBAL_FREED_COUNT, android.os.Debug.getGlobalFreedCount());
        bundle.putLong(METRIC_KEY_GLOBAL_FREED_SIZE, android.os.Debug.getGlobalFreedSize());
        bundle.putLong(METRIC_KEY_GC_INVOCATION_COUNT, android.os.Debug.getGlobalGcInvocationCount());
        return bundle;
    }

    private static android.os.Bundle getBinderCounts() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putLong(METRIC_KEY_SENT_TRANSACTIONS, android.os.Debug.getBinderSentTransactions());
        bundle.putLong(METRIC_KEY_RECEIVED_TRANSACTIONS, android.os.Debug.getBinderReceivedTransactions());
        return bundle;
    }
}
