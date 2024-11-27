package com.android.internal.os;

/* loaded from: classes4.dex */
public class KernelSingleUidTimeReader {
    private static final boolean DBG = false;
    private static final java.lang.String PROC_FILE_DIR = "/proc/uid/";
    private static final java.lang.String PROC_FILE_NAME = "/time_in_state";
    private static final java.lang.String TAG = com.android.internal.os.KernelSingleUidTimeReader.class.getName();
    public static final int TOTAL_READ_ERROR_COUNT = 5;
    private static final java.lang.String UID_TIMES_PROC_FILE = "/proc/uid_time_in_state";
    private boolean mBpfTimesAvailable;
    private final int mCpuFreqsCount;
    private boolean mCpuFreqsCountVerified;
    private final com.android.internal.os.KernelSingleUidTimeReader.Injector mInjector;
    private android.util.SparseArray<long[]> mLastUidCpuTimeMs;
    private int mReadErrorCounter;
    private boolean mSingleUidCpuTimesAvailable;

    private static final native boolean canReadBpfTimes();

    public KernelSingleUidTimeReader(int i) {
        this(i, new com.android.internal.os.KernelSingleUidTimeReader.Injector());
    }

    public KernelSingleUidTimeReader(int i, com.android.internal.os.KernelSingleUidTimeReader.Injector injector) {
        this.mLastUidCpuTimeMs = new android.util.SparseArray<>();
        this.mSingleUidCpuTimesAvailable = true;
        this.mBpfTimesAvailable = true;
        this.mInjector = injector;
        this.mCpuFreqsCount = i;
        if (this.mCpuFreqsCount == 0) {
            this.mSingleUidCpuTimesAvailable = false;
        }
    }

    public boolean singleUidCpuTimesAvailable() {
        return this.mSingleUidCpuTimesAvailable;
    }

    public long[] readDeltaMs(int i) {
        synchronized (this) {
            if (!this.mSingleUidCpuTimesAvailable) {
                return null;
            }
            if (this.mBpfTimesAvailable) {
                long[] readBpfData = this.mInjector.readBpfData(i);
                if (readBpfData.length == 0) {
                    this.mBpfTimesAvailable = false;
                } else {
                    if (!this.mCpuFreqsCountVerified && readBpfData.length != this.mCpuFreqsCount) {
                        this.mSingleUidCpuTimesAvailable = false;
                        return null;
                    }
                    this.mCpuFreqsCountVerified = true;
                    return computeDelta(i, readBpfData);
                }
            }
            java.lang.String str = PROC_FILE_DIR + i + PROC_FILE_NAME;
            try {
                byte[] readData = this.mInjector.readData(str);
                if (!this.mCpuFreqsCountVerified) {
                    verifyCpuFreqsCount(readData.length, str);
                }
                java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(readData);
                wrap.order(java.nio.ByteOrder.nativeOrder());
                return computeDelta(i, readCpuTimesFromByteBuffer(wrap));
            } catch (java.lang.Exception e) {
                int i2 = this.mReadErrorCounter + 1;
                this.mReadErrorCounter = i2;
                if (i2 >= 5) {
                    this.mSingleUidCpuTimesAvailable = false;
                }
                return null;
            }
        }
    }

    private void verifyCpuFreqsCount(int i, java.lang.String str) {
        int i2 = i / 8;
        if (this.mCpuFreqsCount != i2) {
            this.mSingleUidCpuTimesAvailable = false;
            throw new java.lang.IllegalStateException("Freq count didn't match,count from /proc/uid_time_in_state=" + this.mCpuFreqsCount + ", butcount from " + str + "=" + i2);
        }
        this.mCpuFreqsCountVerified = true;
    }

    private long[] readCpuTimesFromByteBuffer(java.nio.ByteBuffer byteBuffer) {
        long[] jArr = new long[this.mCpuFreqsCount];
        for (int i = 0; i < this.mCpuFreqsCount; i++) {
            jArr[i] = byteBuffer.getLong() * 10;
        }
        return jArr;
    }

    public long[] computeDelta(int i, long[] jArr) {
        synchronized (this) {
            if (!this.mSingleUidCpuTimesAvailable) {
                return null;
            }
            long[] deltaLocked = getDeltaLocked(this.mLastUidCpuTimeMs.get(i), jArr);
            if (deltaLocked == null) {
                return null;
            }
            boolean z = true;
            int length = deltaLocked.length - 1;
            while (true) {
                if (length < 0) {
                    z = false;
                    break;
                }
                if (deltaLocked[length] > 0) {
                    break;
                }
                length--;
            }
            if (!z) {
                return null;
            }
            this.mLastUidCpuTimeMs.put(i, jArr);
            return deltaLocked;
        }
    }

    public long[] getDeltaLocked(long[] jArr, long[] jArr2) {
        int length = jArr2.length;
        do {
            length--;
            if (length < 0) {
                if (jArr == null) {
                    return jArr2;
                }
                long[] jArr3 = new long[jArr2.length];
                for (int length2 = jArr2.length - 1; length2 >= 0; length2--) {
                    jArr3[length2] = jArr2[length2] - jArr[length2];
                    if (jArr3[length2] < 0) {
                        return null;
                    }
                }
                return jArr3;
            }
        } while (jArr2[length] >= 0);
        return null;
    }

    public void setAllUidsCpuTimesMs(android.util.SparseArray<long[]> sparseArray) {
        synchronized (this) {
            this.mLastUidCpuTimeMs.clear();
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                long[] valueAt = sparseArray.valueAt(size);
                if (valueAt != null) {
                    this.mLastUidCpuTimeMs.put(sparseArray.keyAt(size), (long[]) valueAt.clone());
                }
            }
        }
    }

    public void removeUid(int i) {
        synchronized (this) {
            this.mLastUidCpuTimeMs.delete(i);
        }
    }

    public void removeUidsInRange(int i, int i2) {
        if (i2 < i) {
            return;
        }
        synchronized (this) {
            this.mLastUidCpuTimeMs.put(i, null);
            this.mLastUidCpuTimeMs.put(i2, null);
            int indexOfKey = this.mLastUidCpuTimeMs.indexOfKey(i);
            this.mLastUidCpuTimeMs.removeAtRange(indexOfKey, (this.mLastUidCpuTimeMs.indexOfKey(i2) - indexOfKey) + 1);
        }
    }

    public void addDelta(int i, com.android.internal.os.LongArrayMultiStateCounter longArrayMultiStateCounter, long j) {
        this.mInjector.addDelta(i, longArrayMultiStateCounter, j, null);
    }

    public void addDelta(int i, com.android.internal.os.LongArrayMultiStateCounter longArrayMultiStateCounter, long j, com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer longArrayContainer) {
        this.mInjector.addDelta(i, longArrayMultiStateCounter, j, longArrayContainer);
    }

    public static class Injector {
        private static native boolean addDeltaForTest(int i, long j, long j2, long[][] jArr, long j3);

        @dalvik.annotation.optimization.CriticalNative
        private static native boolean addDeltaFromBpf(int i, long j, long j2, long j3);

        public native long[] readBpfData(int i);

        public byte[] readData(java.lang.String str) throws java.io.IOException {
            return java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(str, new java.lang.String[0]));
        }

        public boolean addDelta(int i, com.android.internal.os.LongArrayMultiStateCounter longArrayMultiStateCounter, long j, com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer longArrayContainer) {
            return addDeltaFromBpf(i, longArrayMultiStateCounter.mNativeObject, j, longArrayContainer != null ? longArrayContainer.mNativeObject : 0L);
        }

        public boolean addDeltaForTest(int i, com.android.internal.os.LongArrayMultiStateCounter longArrayMultiStateCounter, long j, long[][] jArr, com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer longArrayContainer) {
            return addDeltaForTest(i, longArrayMultiStateCounter.mNativeObject, j, jArr, longArrayContainer != null ? longArrayContainer.mNativeObject : 0L);
        }
    }

    public android.util.SparseArray<long[]> getLastUidCpuTimeMs() {
        return this.mLastUidCpuTimeMs;
    }

    public void setSingleUidCpuTimesAvailable(boolean z) {
        this.mSingleUidCpuTimesAvailable = z;
    }
}
