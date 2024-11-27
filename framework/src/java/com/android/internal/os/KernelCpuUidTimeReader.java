package com.android.internal.os;

/* loaded from: classes4.dex */
public abstract class KernelCpuUidTimeReader<T> {
    protected static final boolean DEBUG = false;
    private static final long DEFAULT_MIN_TIME_BETWEEN_READ = 1000;
    final com.android.internal.os.KernelCpuUidBpfMapReader mBpfReader;
    protected boolean mBpfTimesAvailable;
    private final com.android.internal.os.Clock mClock;
    private long mLastReadTimeMs;
    final android.util.SparseArray<T> mLastTimes;
    private long mMinTimeBetweenRead;
    final com.android.internal.os.KernelCpuProcStringReader mReader;
    final java.lang.String mTag;
    final boolean mThrottle;

    public interface Callback<T> {
        void onUidCpuTime(int i, T t);
    }

    abstract void readAbsoluteImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<T> callback);

    abstract void readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<T> callback, boolean z);

    KernelCpuUidTimeReader(com.android.internal.os.KernelCpuProcStringReader kernelCpuProcStringReader, com.android.internal.os.KernelCpuUidBpfMapReader kernelCpuUidBpfMapReader, boolean z, com.android.internal.os.Clock clock) {
        this.mTag = getClass().getSimpleName();
        this.mLastTimes = new android.util.SparseArray<>();
        this.mMinTimeBetweenRead = 1000L;
        this.mLastReadTimeMs = 0L;
        this.mReader = kernelCpuProcStringReader;
        this.mThrottle = z;
        this.mBpfReader = kernelCpuUidBpfMapReader;
        this.mClock = clock;
        this.mBpfTimesAvailable = this.mBpfReader != null;
    }

    KernelCpuUidTimeReader(com.android.internal.os.KernelCpuProcStringReader kernelCpuProcStringReader, boolean z, com.android.internal.os.Clock clock) {
        this(kernelCpuProcStringReader, null, z, clock);
    }

    public void readDelta(com.android.internal.os.KernelCpuUidTimeReader.Callback<T> callback) {
        readDelta(false, callback);
    }

    public void readDelta(boolean z, com.android.internal.os.KernelCpuUidTimeReader.Callback<T> callback) {
        if (!this.mThrottle) {
            readDeltaImpl(callback, z);
            return;
        }
        long elapsedRealtime = this.mClock.elapsedRealtime();
        if (!z && elapsedRealtime < this.mLastReadTimeMs + this.mMinTimeBetweenRead) {
            return;
        }
        readDeltaImpl(callback, z);
        this.mLastReadTimeMs = elapsedRealtime;
    }

    public void readAbsolute(com.android.internal.os.KernelCpuUidTimeReader.Callback<T> callback) {
        if (!this.mThrottle) {
            readAbsoluteImpl(callback);
            return;
        }
        long elapsedRealtime = this.mClock.elapsedRealtime();
        if (elapsedRealtime < this.mLastReadTimeMs + this.mMinTimeBetweenRead) {
            return;
        }
        readAbsoluteImpl(callback);
        this.mLastReadTimeMs = elapsedRealtime;
    }

    public void removeUid(int i) {
        this.mLastTimes.delete(i);
        if (this.mBpfTimesAvailable) {
            this.mBpfReader.removeUidsInRange(i, i);
        }
    }

    public void removeUidsInRange(int i, int i2) {
        if (i2 < i) {
            android.util.Slog.e(this.mTag, "start UID " + i + " > end UID " + i2);
            return;
        }
        this.mLastTimes.put(i, null);
        this.mLastTimes.put(i2, null);
        int indexOfKey = this.mLastTimes.indexOfKey(i);
        this.mLastTimes.removeAtRange(indexOfKey, (this.mLastTimes.indexOfKey(i2) - indexOfKey) + 1);
        if (this.mBpfTimesAvailable) {
            this.mBpfReader.removeUidsInRange(i, i2);
        }
    }

    public void setThrottle(long j) {
        if (this.mThrottle && j >= 0) {
            this.mMinTimeBetweenRead = j;
        }
    }

    public static class KernelCpuUidUserSysTimeReader extends com.android.internal.os.KernelCpuUidTimeReader<long[]> {
        private static final java.lang.String REMOVE_UID_PROC_FILE = "/proc/uid_cputime/remove_uid_range";
        private final long[] mBuffer;
        private final long[] mUsrSysTime;

        public KernelCpuUidUserSysTimeReader(boolean z) {
            this(z, com.android.internal.os.Clock.SYSTEM_CLOCK);
        }

        public KernelCpuUidUserSysTimeReader(boolean z, com.android.internal.os.Clock clock) {
            super(com.android.internal.os.KernelCpuProcStringReader.getUserSysTimeReaderInstance(), z, clock);
            this.mBuffer = new long[4];
            this.mUsrSysTime = new long[2];
        }

        public KernelCpuUidUserSysTimeReader(com.android.internal.os.KernelCpuProcStringReader kernelCpuProcStringReader, boolean z, com.android.internal.os.Clock clock) {
            super(kernelCpuProcStringReader, z, clock);
            this.mBuffer = new long[4];
            this.mUsrSysTime = new long[2];
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> callback, boolean z) {
            com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator open = this.mReader.open(!this.mThrottle || z);
            if (open == null) {
                if (open != null) {
                    open.close();
                    return;
                }
                return;
            }
            while (true) {
                try {
                    java.nio.CharBuffer nextLine = open.nextLine();
                    if (nextLine == null) {
                        break;
                    }
                    if (com.android.internal.os.KernelCpuProcStringReader.asLongs(nextLine, this.mBuffer) < 3) {
                        android.util.Slog.wtf(this.mTag, "Invalid line: " + nextLine.toString());
                    } else {
                        int i = (int) this.mBuffer[0];
                        long[] jArr = (long[]) this.mLastTimes.get(i);
                        if (jArr == null) {
                            jArr = new long[2];
                            this.mLastTimes.put(i, jArr);
                        }
                        long j = this.mBuffer[1];
                        long j2 = this.mBuffer[2];
                        this.mUsrSysTime[0] = j - jArr[0];
                        this.mUsrSysTime[1] = j2 - jArr[1];
                        if (this.mUsrSysTime[0] >= 0 && this.mUsrSysTime[1] >= 0) {
                            if ((this.mUsrSysTime[0] > 0 || this.mUsrSysTime[1] > 0) && callback != null) {
                                callback.onUidCpuTime(i, this.mUsrSysTime);
                            }
                            jArr[0] = j;
                            jArr[1] = j2;
                        }
                        android.util.Slog.e(this.mTag, "Negative user/sys time delta for UID=" + i + "\nPrev times: u=" + jArr[0] + " s=" + jArr[1] + " Curr times: u=" + j + " s=" + j2);
                        jArr[0] = j;
                        jArr[1] = j2;
                    }
                } finally {
                }
            }
            if (open != null) {
                open.close();
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readAbsoluteImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> callback) {
            com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator open = this.mReader.open(!this.mThrottle);
            if (open == null) {
                if (open != null) {
                    open.close();
                    return;
                }
                return;
            }
            while (true) {
                try {
                    java.nio.CharBuffer nextLine = open.nextLine();
                    if (nextLine == null) {
                        break;
                    }
                    if (com.android.internal.os.KernelCpuProcStringReader.asLongs(nextLine, this.mBuffer) < 3) {
                        android.util.Slog.wtf(this.mTag, "Invalid line: " + nextLine.toString());
                    } else {
                        this.mUsrSysTime[0] = this.mBuffer[1];
                        this.mUsrSysTime[1] = this.mBuffer[2];
                        callback.onUidCpuTime((int) this.mBuffer[0], this.mUsrSysTime);
                    }
                } catch (java.lang.Throwable th) {
                    if (open != null) {
                        try {
                            open.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            if (open != null) {
                open.close();
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        public void removeUid(int i) {
            super.removeUid(i);
            removeUidsFromKernelModule(i, i);
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        public void removeUidsInRange(int i, int i2) {
            super.removeUidsInRange(i, i2);
            removeUidsFromKernelModule(i, i2);
        }

        private void removeUidsFromKernelModule(int i, int i2) {
            android.util.Slog.d(this.mTag, "Removing uids " + i + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + i2);
            int allowThreadDiskWritesMask = android.os.StrictMode.allowThreadDiskWritesMask();
            try {
                try {
                    java.io.FileWriter fileWriter = new java.io.FileWriter(REMOVE_UID_PROC_FILE);
                    try {
                        fileWriter.write(i + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + i2);
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (java.lang.Throwable th) {
                        try {
                            fileWriter.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (java.io.IOException e) {
                    android.util.Slog.e(this.mTag, "failed to remove uids " + i + " - " + i2 + " from uid_cputime module", e);
                }
            } finally {
                android.os.StrictMode.setThreadPolicyMask(allowThreadDiskWritesMask);
            }
        }
    }

    public static class KernelCpuUidFreqTimeReader extends com.android.internal.os.KernelCpuUidTimeReader<long[]> {
        private static final int MAX_ERROR_COUNT = 5;
        private static final java.lang.String UID_TIMES_PROC_FILE = "/proc/uid_time_in_state";
        private boolean mAllUidTimesAvailable;
        private long[] mBuffer;
        private long[] mCpuFreqs;
        private long[] mCurTimes;
        private long[] mDeltaTimes;
        private int mErrors;
        private int mFreqCount;
        private boolean mPerClusterTimesAvailable;
        private final java.nio.file.Path mProcFilePath;

        public KernelCpuUidFreqTimeReader(boolean z) {
            this(z, com.android.internal.os.Clock.SYSTEM_CLOCK);
        }

        public KernelCpuUidFreqTimeReader(boolean z, com.android.internal.os.Clock clock) {
            this(UID_TIMES_PROC_FILE, com.android.internal.os.KernelCpuProcStringReader.getFreqTimeReaderInstance(), com.android.internal.os.KernelCpuUidBpfMapReader.getFreqTimeReaderInstance(), z, clock);
        }

        public KernelCpuUidFreqTimeReader(java.lang.String str, com.android.internal.os.KernelCpuProcStringReader kernelCpuProcStringReader, com.android.internal.os.KernelCpuUidBpfMapReader kernelCpuUidBpfMapReader, boolean z) {
            this(str, kernelCpuProcStringReader, kernelCpuUidBpfMapReader, z, com.android.internal.os.Clock.SYSTEM_CLOCK);
        }

        private KernelCpuUidFreqTimeReader(java.lang.String str, com.android.internal.os.KernelCpuProcStringReader kernelCpuProcStringReader, com.android.internal.os.KernelCpuUidBpfMapReader kernelCpuUidBpfMapReader, boolean z, com.android.internal.os.Clock clock) {
            super(kernelCpuProcStringReader, kernelCpuUidBpfMapReader, z, clock);
            this.mFreqCount = 0;
            this.mErrors = 0;
            this.mProcFilePath = java.nio.file.Paths.get(str, new java.lang.String[0]);
        }

        public void onSystemReady() {
            if (this.mBpfTimesAvailable && this.mCpuFreqs == null) {
                readFreqsThroughBpf();
                this.mAllUidTimesAvailable = this.mCpuFreqs != null;
            }
        }

        public boolean perClusterTimesAvailable() {
            return this.mBpfTimesAvailable;
        }

        public boolean allUidTimesAvailable() {
            return this.mAllUidTimesAvailable;
        }

        public android.util.SparseArray<long[]> getAllUidCpuFreqTimeMs() {
            return this.mLastTimes;
        }

        private long[] readFreqsThroughBpf() {
            if (!this.mBpfTimesAvailable || this.mBpfReader == null) {
                return null;
            }
            this.mCpuFreqs = this.mBpfReader.getDataDimensions();
            if (this.mCpuFreqs == null) {
                return null;
            }
            this.mFreqCount = this.mCpuFreqs.length;
            this.mCurTimes = new long[this.mFreqCount];
            this.mDeltaTimes = new long[this.mFreqCount];
            this.mBuffer = new long[this.mFreqCount + 1];
            return this.mCpuFreqs;
        }

        private long[] readFreqs(java.lang.String str) {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            java.lang.String[] split = str.split(" ");
            if (split.length <= 1) {
                android.util.Slog.wtf(this.mTag, "Malformed freq line: " + str);
                return null;
            }
            this.mFreqCount = split.length - 1;
            this.mCpuFreqs = new long[this.mFreqCount];
            this.mCurTimes = new long[this.mFreqCount];
            this.mDeltaTimes = new long[this.mFreqCount];
            this.mBuffer = new long[this.mFreqCount + 1];
            int i = 0;
            while (i < this.mFreqCount) {
                int i2 = i + 1;
                this.mCpuFreqs[i] = java.lang.Long.parseLong(split[i2], 10);
                i = i2;
            }
            return this.mCpuFreqs;
        }

        private void processUidDelta(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> callback) {
            int i = (int) this.mBuffer[0];
            long[] jArr = (long[]) this.mLastTimes.get(i);
            if (jArr == null) {
                jArr = new long[this.mFreqCount];
                this.mLastTimes.put(i, jArr);
            }
            copyToCurTimes();
            boolean z = false;
            for (int i2 = 0; i2 < this.mFreqCount; i2++) {
                this.mDeltaTimes[i2] = this.mCurTimes[i2] - jArr[i2];
                if (this.mDeltaTimes[i2] >= 0) {
                    z |= this.mDeltaTimes[i2] > 0;
                } else {
                    android.util.Slog.e(this.mTag, "Negative delta from freq time for uid: " + i + ", delta: " + this.mDeltaTimes[i2]);
                    return;
                }
            }
            if (z) {
                java.lang.System.arraycopy(this.mCurTimes, 0, jArr, 0, this.mFreqCount);
                if (callback != null) {
                    callback.onUidCpuTime(i, this.mDeltaTimes);
                }
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> callback, boolean z) {
            if (this.mBpfTimesAvailable) {
                com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator open = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(open)) {
                        while (open.getNextUid(this.mBuffer)) {
                            processUidDelta(callback);
                        }
                        if (open != null) {
                            open.close();
                            return;
                        }
                        return;
                    }
                    if (open != null) {
                        open.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (open != null) {
                        try {
                            open.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator open2 = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(open2)) {
                    if (open2 != null) {
                        open2.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    java.nio.CharBuffer nextLine = open2.nextLine();
                    if (nextLine == null) {
                        break;
                    } else if (com.android.internal.os.KernelCpuProcStringReader.asLongs(nextLine, this.mBuffer) != this.mBuffer.length) {
                        android.util.Slog.wtf(this.mTag, "Invalid line: " + nextLine.toString());
                    } else {
                        processUidDelta(callback);
                    }
                }
                if (open2 != null) {
                    open2.close();
                }
            } catch (java.lang.Throwable th3) {
                if (open2 != null) {
                    try {
                        open2.close();
                    } catch (java.lang.Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readAbsoluteImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> callback) {
            if (this.mBpfTimesAvailable) {
                com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator open = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(open)) {
                        while (open.getNextUid(this.mBuffer)) {
                            copyToCurTimes();
                            callback.onUidCpuTime((int) this.mBuffer[0], this.mCurTimes);
                        }
                        if (open != null) {
                            open.close();
                            return;
                        }
                        return;
                    }
                    if (open != null) {
                        open.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (open != null) {
                        try {
                            open.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator open2 = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(open2)) {
                    if (open2 != null) {
                        open2.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    java.nio.CharBuffer nextLine = open2.nextLine();
                    if (nextLine == null) {
                        break;
                    }
                    if (com.android.internal.os.KernelCpuProcStringReader.asLongs(nextLine, this.mBuffer) != this.mBuffer.length) {
                        android.util.Slog.wtf(this.mTag, "Invalid line: " + nextLine.toString());
                    } else {
                        copyToCurTimes();
                        callback.onUidCpuTime((int) this.mBuffer[0], this.mCurTimes);
                    }
                }
                if (open2 != null) {
                    open2.close();
                }
            } catch (java.lang.Throwable th3) {
                if (open2 != null) {
                    try {
                        open2.close();
                    } catch (java.lang.Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        private void copyToCurTimes() {
            long j = this.mBpfTimesAvailable ? 1L : 10L;
            int i = 0;
            while (i < this.mFreqCount) {
                int i2 = i + 1;
                this.mCurTimes[i] = this.mBuffer[i2] * j;
                i = i2;
            }
        }

        private boolean checkPrecondition(com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIterator) {
            if (bpfMapIterator == null) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            if (this.mCpuFreqs != null) {
                return true;
            }
            this.mBpfTimesAvailable = readFreqsThroughBpf() != null;
            return this.mBpfTimesAvailable;
        }

        private boolean checkPrecondition(com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator procFileIterator) {
            if (procFileIterator == null || !procFileIterator.hasNextLine()) {
                return false;
            }
            return (this.mCpuFreqs == null && readFreqs(procFileIterator.nextLine().toString()) == null) ? false : true;
        }

        private android.util.IntArray extractClusterInfoFromProcFileFreqs() {
            android.util.IntArray intArray = new android.util.IntArray();
            int i = 0;
            int i2 = 0;
            while (i < this.mFreqCount) {
                i2++;
                int i3 = i + 1;
                if (i3 == this.mFreqCount || this.mCpuFreqs[i3] <= this.mCpuFreqs[i]) {
                    intArray.add(i2);
                    i2 = 0;
                }
                i = i3;
            }
            return intArray;
        }

        public boolean isFastCpuTimesReader() {
            return this.mBpfTimesAvailable;
        }
    }

    public static class KernelCpuUidActiveTimeReader extends com.android.internal.os.KernelCpuUidTimeReader<java.lang.Long> {
        private long[] mBuffer;
        private int mCores;

        public KernelCpuUidActiveTimeReader(boolean z) {
            this(z, com.android.internal.os.Clock.SYSTEM_CLOCK);
        }

        public KernelCpuUidActiveTimeReader(boolean z, com.android.internal.os.Clock clock) {
            super(com.android.internal.os.KernelCpuProcStringReader.getActiveTimeReaderInstance(), com.android.internal.os.KernelCpuUidBpfMapReader.getActiveTimeReaderInstance(), z, clock);
            this.mCores = 0;
        }

        public KernelCpuUidActiveTimeReader(com.android.internal.os.KernelCpuProcStringReader kernelCpuProcStringReader, com.android.internal.os.KernelCpuUidBpfMapReader kernelCpuUidBpfMapReader, boolean z) {
            super(kernelCpuProcStringReader, kernelCpuUidBpfMapReader, z, com.android.internal.os.Clock.SYSTEM_CLOCK);
            this.mCores = 0;
        }

        private void processUidDelta(com.android.internal.os.KernelCpuUidTimeReader.Callback<java.lang.Long> callback) {
            int i = (int) this.mBuffer[0];
            long sumActiveTime = sumActiveTime(this.mBuffer, this.mBpfTimesAvailable ? 1.0d : 10.0d);
            if (sumActiveTime > 0) {
                long longValue = sumActiveTime - ((java.lang.Long) this.mLastTimes.get(i, 0L)).longValue();
                if (longValue > 0) {
                    this.mLastTimes.put(i, java.lang.Long.valueOf(sumActiveTime));
                    if (callback != null) {
                        callback.onUidCpuTime(i, java.lang.Long.valueOf(longValue));
                        return;
                    }
                    return;
                }
                if (longValue < 0) {
                    android.util.Slog.e(this.mTag, "Negative delta from active time for uid: " + i + ", delta: " + longValue);
                }
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<java.lang.Long> callback, boolean z) {
            if (this.mBpfTimesAvailable) {
                com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator open = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(open)) {
                        while (open.getNextUid(this.mBuffer)) {
                            processUidDelta(callback);
                        }
                        if (open != null) {
                            open.close();
                            return;
                        }
                        return;
                    }
                    if (open != null) {
                        open.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (open != null) {
                        try {
                            open.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator open2 = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(open2)) {
                    if (open2 != null) {
                        open2.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    java.nio.CharBuffer nextLine = open2.nextLine();
                    if (nextLine == null) {
                        break;
                    } else if (com.android.internal.os.KernelCpuProcStringReader.asLongs(nextLine, this.mBuffer) != this.mBuffer.length) {
                        android.util.Slog.wtf(this.mTag, "Invalid line: " + nextLine.toString());
                    } else {
                        processUidDelta(callback);
                    }
                }
                if (open2 != null) {
                    open2.close();
                }
            } catch (java.lang.Throwable th3) {
                if (open2 != null) {
                    try {
                        open2.close();
                    } catch (java.lang.Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        private void processUidAbsolute(com.android.internal.os.KernelCpuUidTimeReader.Callback<java.lang.Long> callback) {
            long sumActiveTime = sumActiveTime(this.mBuffer, this.mBpfTimesAvailable ? 1.0d : 10.0d);
            if (sumActiveTime > 0) {
                callback.onUidCpuTime((int) this.mBuffer[0], java.lang.Long.valueOf(sumActiveTime));
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readAbsoluteImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<java.lang.Long> callback) {
            if (this.mBpfTimesAvailable) {
                com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator open = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(open)) {
                        while (open.getNextUid(this.mBuffer)) {
                            processUidAbsolute(callback);
                        }
                        if (open != null) {
                            open.close();
                            return;
                        }
                        return;
                    }
                    if (open != null) {
                        open.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (open != null) {
                        try {
                            open.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator open2 = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(open2)) {
                    if (open2 != null) {
                        open2.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    java.nio.CharBuffer nextLine = open2.nextLine();
                    if (nextLine == null) {
                        break;
                    } else if (com.android.internal.os.KernelCpuProcStringReader.asLongs(nextLine, this.mBuffer) != this.mBuffer.length) {
                        android.util.Slog.wtf(this.mTag, "Invalid line: " + nextLine.toString());
                    } else {
                        processUidAbsolute(callback);
                    }
                }
                if (open2 != null) {
                    open2.close();
                }
            } catch (java.lang.Throwable th3) {
                if (open2 != null) {
                    try {
                        open2.close();
                    } catch (java.lang.Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        private static long sumActiveTime(long[] jArr, double d) {
            double d2 = 0.0d;
            for (int i = 1; i < jArr.length; i++) {
                d2 += (jArr[i] * d) / i;
            }
            return (long) d2;
        }

        private boolean checkPrecondition(com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIterator) {
            if (bpfMapIterator == null) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            if (this.mCores > 0) {
                return true;
            }
            long[] dataDimensions = this.mBpfReader.getDataDimensions();
            if (dataDimensions == null || dataDimensions.length < 1) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            this.mCores = (int) dataDimensions[0];
            this.mBuffer = new long[this.mCores + 1];
            return true;
        }

        private boolean checkPrecondition(com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator procFileIterator) {
            if (procFileIterator == null || !procFileIterator.hasNextLine()) {
                return false;
            }
            java.nio.CharBuffer nextLine = procFileIterator.nextLine();
            if (this.mCores > 0) {
                return true;
            }
            java.lang.String trim = nextLine.toString().trim();
            if (trim.isEmpty()) {
                android.util.Slog.w(this.mTag, "Empty uid_concurrent_active_time");
                return false;
            }
            if (!trim.startsWith("cpus:")) {
                android.util.Slog.wtf(this.mTag, "Malformed uid_concurrent_active_time line: " + trim);
                return false;
            }
            int parseInt = java.lang.Integer.parseInt(trim.substring(5).trim(), 10);
            if (parseInt <= 0) {
                android.util.Slog.wtf(this.mTag, "Malformed uid_concurrent_active_time line: " + trim);
                return false;
            }
            this.mCores = parseInt;
            this.mBuffer = new long[this.mCores + 1];
            return true;
        }
    }

    public static class KernelCpuUidClusterTimeReader extends com.android.internal.os.KernelCpuUidTimeReader<long[]> {
        private long[] mBuffer;
        private int[] mCoresOnClusters;
        private long[] mCurTime;
        private long[] mDeltaTime;
        private int mNumClusters;
        private int mNumCores;

        public KernelCpuUidClusterTimeReader(boolean z) {
            this(z, com.android.internal.os.Clock.SYSTEM_CLOCK);
        }

        public KernelCpuUidClusterTimeReader(boolean z, com.android.internal.os.Clock clock) {
            super(com.android.internal.os.KernelCpuProcStringReader.getClusterTimeReaderInstance(), com.android.internal.os.KernelCpuUidBpfMapReader.getClusterTimeReaderInstance(), z, clock);
        }

        public KernelCpuUidClusterTimeReader(com.android.internal.os.KernelCpuProcStringReader kernelCpuProcStringReader, com.android.internal.os.KernelCpuUidBpfMapReader kernelCpuUidBpfMapReader, boolean z) {
            super(kernelCpuProcStringReader, kernelCpuUidBpfMapReader, z, com.android.internal.os.Clock.SYSTEM_CLOCK);
        }

        void processUidDelta(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> callback) {
            int i = (int) this.mBuffer[0];
            long[] jArr = (long[]) this.mLastTimes.get(i);
            if (jArr == null) {
                jArr = new long[this.mNumClusters];
                this.mLastTimes.put(i, jArr);
            }
            sumClusterTime();
            boolean z = false;
            for (int i2 = 0; i2 < this.mNumClusters; i2++) {
                this.mDeltaTime[i2] = this.mCurTime[i2] - jArr[i2];
                if (this.mDeltaTime[i2] >= 0) {
                    z |= this.mDeltaTime[i2] > 0;
                } else {
                    android.util.Slog.e(this.mTag, "Negative delta from cluster time for uid: " + i + ", delta: " + this.mDeltaTime[i2]);
                    return;
                }
            }
            if (z) {
                java.lang.System.arraycopy(this.mCurTime, 0, jArr, 0, this.mNumClusters);
                if (callback != null) {
                    callback.onUidCpuTime(i, this.mDeltaTime);
                }
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readDeltaImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> callback, boolean z) {
            if (this.mBpfTimesAvailable) {
                com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator open = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(open)) {
                        while (open.getNextUid(this.mBuffer)) {
                            processUidDelta(callback);
                        }
                        if (open != null) {
                            open.close();
                            return;
                        }
                        return;
                    }
                    if (open != null) {
                        open.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (open != null) {
                        try {
                            open.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator open2 = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(open2)) {
                    if (open2 != null) {
                        open2.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    java.nio.CharBuffer nextLine = open2.nextLine();
                    if (nextLine == null) {
                        break;
                    } else if (com.android.internal.os.KernelCpuProcStringReader.asLongs(nextLine, this.mBuffer) != this.mBuffer.length) {
                        android.util.Slog.wtf(this.mTag, "Invalid line: " + nextLine.toString());
                    } else {
                        processUidDelta(callback);
                    }
                }
                if (open2 != null) {
                    open2.close();
                }
            } catch (java.lang.Throwable th3) {
                if (open2 != null) {
                    try {
                        open2.close();
                    } catch (java.lang.Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        @Override // com.android.internal.os.KernelCpuUidTimeReader
        void readAbsoluteImpl(com.android.internal.os.KernelCpuUidTimeReader.Callback<long[]> callback) {
            if (this.mBpfTimesAvailable) {
                com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator open = this.mBpfReader.open(!this.mThrottle);
                try {
                    if (checkPrecondition(open)) {
                        while (open.getNextUid(this.mBuffer)) {
                            sumClusterTime();
                            callback.onUidCpuTime((int) this.mBuffer[0], this.mCurTime);
                        }
                        if (open != null) {
                            open.close();
                            return;
                        }
                        return;
                    }
                    if (open != null) {
                        open.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (open != null) {
                        try {
                            open.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator open2 = this.mReader.open(!this.mThrottle);
            try {
                if (!checkPrecondition(open2)) {
                    if (open2 != null) {
                        open2.close();
                        return;
                    }
                    return;
                }
                while (true) {
                    java.nio.CharBuffer nextLine = open2.nextLine();
                    if (nextLine == null) {
                        break;
                    }
                    if (com.android.internal.os.KernelCpuProcStringReader.asLongs(nextLine, this.mBuffer) != this.mBuffer.length) {
                        android.util.Slog.wtf(this.mTag, "Invalid line: " + nextLine.toString());
                    } else {
                        sumClusterTime();
                        callback.onUidCpuTime((int) this.mBuffer[0], this.mCurTime);
                    }
                }
                if (open2 != null) {
                    open2.close();
                }
            } catch (java.lang.Throwable th3) {
                if (open2 != null) {
                    try {
                        open2.close();
                    } catch (java.lang.Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        private void sumClusterTime() {
            double d = this.mBpfTimesAvailable ? 1.0d : 10.0d;
            int i = 1;
            for (int i2 = 0; i2 < this.mNumClusters; i2++) {
                double d2 = 0.0d;
                int i3 = 1;
                while (i3 <= this.mCoresOnClusters[i2]) {
                    d2 += (this.mBuffer[i] * d) / i3;
                    i3++;
                    i++;
                }
                this.mCurTime[i2] = (long) d2;
            }
        }

        private boolean checkPrecondition(com.android.internal.os.KernelCpuUidBpfMapReader.BpfMapIterator bpfMapIterator) {
            if (bpfMapIterator == null) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            if (this.mNumClusters > 0) {
                return true;
            }
            long[] dataDimensions = this.mBpfReader.getDataDimensions();
            if (dataDimensions == null || dataDimensions.length < 1) {
                this.mBpfTimesAvailable = false;
                return false;
            }
            this.mNumClusters = dataDimensions.length;
            this.mCoresOnClusters = new int[this.mNumClusters];
            int i = 0;
            for (int i2 = 0; i2 < this.mNumClusters; i2++) {
                this.mCoresOnClusters[i2] = (int) dataDimensions[i2];
                i += this.mCoresOnClusters[i2];
            }
            this.mNumCores = i;
            this.mBuffer = new long[i + 1];
            this.mCurTime = new long[this.mNumClusters];
            this.mDeltaTime = new long[this.mNumClusters];
            return true;
        }

        private boolean checkPrecondition(com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator procFileIterator) {
            if (procFileIterator == null || !procFileIterator.hasNextLine()) {
                return false;
            }
            java.nio.CharBuffer nextLine = procFileIterator.nextLine();
            if (this.mNumClusters > 0) {
                return true;
            }
            java.lang.String trim = nextLine.toString().trim();
            if (trim.isEmpty()) {
                android.util.Slog.w(this.mTag, "Empty uid_concurrent_policy_time");
                return false;
            }
            java.lang.String[] split = trim.split(" ");
            if (split.length % 2 != 0) {
                android.util.Slog.wtf(this.mTag, "Malformed uid_concurrent_policy_time line: " + trim);
                return false;
            }
            int length = split.length / 2;
            int[] iArr = new int[length];
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = i2 * 2;
                if (!split[i3].startsWith("policy")) {
                    android.util.Slog.wtf(this.mTag, "Malformed uid_concurrent_policy_time line: " + trim);
                    return false;
                }
                iArr[i2] = java.lang.Integer.parseInt(split[i3 + 1], 10);
                i += iArr[i2];
            }
            this.mNumClusters = length;
            this.mNumCores = i;
            this.mCoresOnClusters = iArr;
            this.mBuffer = new long[i + 1];
            this.mCurTime = new long[this.mNumClusters];
            this.mDeltaTime = new long[this.mNumClusters];
            return true;
        }
    }
}
