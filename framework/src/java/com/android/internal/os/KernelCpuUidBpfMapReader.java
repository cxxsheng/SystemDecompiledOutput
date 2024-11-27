package com.android.internal.os;

/* loaded from: classes4.dex */
public abstract class KernelCpuUidBpfMapReader {
    private static final int ERROR_THRESHOLD = 5;
    private static final long FRESHNESS_MS = 500;
    private static final com.android.internal.os.KernelCpuUidBpfMapReader FREQ_TIME_READER = new com.android.internal.os.KernelCpuUidBpfMapReader.KernelCpuUidFreqTimeBpfMapReader();
    private static final com.android.internal.os.KernelCpuUidBpfMapReader ACTIVE_TIME_READER = new com.android.internal.os.KernelCpuUidBpfMapReader.KernelCpuUidActiveTimeBpfMapReader();
    private static final com.android.internal.os.KernelCpuUidBpfMapReader CLUSTER_TIME_READER = new com.android.internal.os.KernelCpuUidBpfMapReader.KernelCpuUidClusterTimeBpfMapReader();
    final java.lang.String mTag = getClass().getSimpleName();
    private int mErrors = 0;
    protected android.util.SparseArray<long[]> mData = new android.util.SparseArray<>();
    private long mLastReadTime = 0;
    protected final java.util.concurrent.locks.ReentrantReadWriteLock mLock = new java.util.concurrent.locks.ReentrantReadWriteLock();
    protected final java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock mReadLock = this.mLock.readLock();
    protected final java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock mWriteLock = this.mLock.writeLock();

    public static class KernelCpuUidActiveTimeBpfMapReader extends com.android.internal.os.KernelCpuUidBpfMapReader {
        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        public final native long[] getDataDimensions();

        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        protected final native boolean readBpfData();
    }

    public static class KernelCpuUidClusterTimeBpfMapReader extends com.android.internal.os.KernelCpuUidBpfMapReader {
        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        public final native long[] getDataDimensions();

        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        protected final native boolean readBpfData();
    }

    public abstract long[] getDataDimensions();

    protected abstract boolean readBpfData();

    static com.android.internal.os.KernelCpuUidBpfMapReader getFreqTimeReaderInstance() {
        return FREQ_TIME_READER;
    }

    static com.android.internal.os.KernelCpuUidBpfMapReader getActiveTimeReaderInstance() {
        return ACTIVE_TIME_READER;
    }

    static com.android.internal.os.KernelCpuUidBpfMapReader getClusterTimeReaderInstance() {
        return CLUSTER_TIME_READER;
    }

    public boolean startTrackingBpfTimes() {
        return com.android.internal.os.KernelCpuBpfTracking.startTracking();
    }

    public void removeUidsInRange(int i, int i2) {
        if (this.mErrors > 5 || i2 < i || i < 0) {
            return;
        }
        this.mWriteLock.lock();
        int indexOfKey = this.mData.indexOfKey(i);
        if (indexOfKey < 0) {
            this.mData.put(i, null);
            indexOfKey = this.mData.indexOfKey(i);
        }
        int indexOfKey2 = this.mData.indexOfKey(i2);
        if (indexOfKey2 < 0) {
            this.mData.put(i2, null);
            indexOfKey2 = this.mData.indexOfKey(i2);
        }
        this.mData.removeAtRange(indexOfKey, (indexOfKey2 - indexOfKey) + 1);
        this.mWriteLock.unlock();
    }

    public com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator open() {
        return open(false);
    }

    public com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator open(boolean z) {
        if (this.mErrors > 5) {
            return null;
        }
        if (!startTrackingBpfTimes()) {
            android.util.Slog.w(this.mTag, "Failed to start tracking");
            this.mErrors++;
            return null;
        }
        if (z) {
            this.mWriteLock.lock();
        } else {
            this.mReadLock.lock();
            if (dataValid()) {
                return new com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator();
            }
            this.mReadLock.unlock();
            this.mWriteLock.lock();
            if (dataValid()) {
                this.mReadLock.lock();
                this.mWriteLock.unlock();
                return new com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator();
            }
        }
        if (readBpfData()) {
            this.mLastReadTime = android.os.SystemClock.elapsedRealtime();
            this.mReadLock.lock();
            this.mWriteLock.unlock();
            return new com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator();
        }
        this.mWriteLock.unlock();
        this.mErrors++;
        android.util.Slog.w(this.mTag, "Failed to read bpf times");
        return null;
    }

    private boolean dataValid() {
        return this.mData.size() > 0 && android.os.SystemClock.elapsedRealtime() - this.mLastReadTime < FRESHNESS_MS;
    }

    public class BpfMapIterator implements java.lang.AutoCloseable {
        private int mPos;

        public BpfMapIterator() {
        }

        public boolean getNextUid(long[] jArr) {
            if (this.mPos >= com.android.internal.os.KernelCpuUidBpfMapReader.this.mData.size()) {
                return false;
            }
            jArr[0] = com.android.internal.os.KernelCpuUidBpfMapReader.this.mData.keyAt(this.mPos);
            java.lang.System.arraycopy(com.android.internal.os.KernelCpuUidBpfMapReader.this.mData.valueAt(this.mPos), 0, jArr, 1, com.android.internal.os.KernelCpuUidBpfMapReader.this.mData.valueAt(this.mPos).length);
            this.mPos++;
            return true;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            com.android.internal.os.KernelCpuUidBpfMapReader.this.mReadLock.unlock();
        }
    }

    public static class KernelCpuUidFreqTimeBpfMapReader extends com.android.internal.os.KernelCpuUidBpfMapReader {
        private final native boolean removeUidRange(int i, int i2);

        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        protected final native boolean readBpfData();

        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        public final long[] getDataDimensions() {
            return com.android.internal.os.KernelCpuBpfTracking.getFreqsInternal();
        }

        @Override // com.android.internal.os.KernelCpuUidBpfMapReader
        public void removeUidsInRange(int i, int i2) {
            this.mWriteLock.lock();
            super.removeUidsInRange(i, i2);
            removeUidRange(i, i2);
            this.mWriteLock.unlock();
        }
    }
}
