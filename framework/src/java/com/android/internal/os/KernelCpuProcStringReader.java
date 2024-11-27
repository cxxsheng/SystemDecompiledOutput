package com.android.internal.os;

/* loaded from: classes4.dex */
public class KernelCpuProcStringReader {
    private static final int ERROR_THRESHOLD = 5;
    private static final long FRESHNESS = 500;
    private static final int MAX_BUFFER_SIZE = 1048576;
    private char[] mBuf;
    private final com.android.internal.os.Clock mClock;
    private int mErrors;
    private final java.nio.file.Path mFile;
    private long mLastReadTime;
    private final java.util.concurrent.locks.ReentrantReadWriteLock mLock;
    private final java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock mReadLock;
    private int mSize;
    private final java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock mWriteLock;
    private static final java.lang.String TAG = com.android.internal.os.KernelCpuProcStringReader.class.getSimpleName();
    private static final java.lang.String PROC_UID_FREQ_TIME = "/proc/uid_time_in_state";
    private static final com.android.internal.os.KernelCpuProcStringReader FREQ_TIME_READER = new com.android.internal.os.KernelCpuProcStringReader(PROC_UID_FREQ_TIME);
    private static final java.lang.String PROC_UID_ACTIVE_TIME = "/proc/uid_concurrent_active_time";
    private static final com.android.internal.os.KernelCpuProcStringReader ACTIVE_TIME_READER = new com.android.internal.os.KernelCpuProcStringReader(PROC_UID_ACTIVE_TIME);
    private static final java.lang.String PROC_UID_CLUSTER_TIME = "/proc/uid_concurrent_policy_time";
    private static final com.android.internal.os.KernelCpuProcStringReader CLUSTER_TIME_READER = new com.android.internal.os.KernelCpuProcStringReader(PROC_UID_CLUSTER_TIME);
    private static final java.lang.String PROC_UID_USER_SYS_TIME = "/proc/uid_cputime/show_uid_stat";
    private static final com.android.internal.os.KernelCpuProcStringReader USER_SYS_TIME_READER = new com.android.internal.os.KernelCpuProcStringReader(PROC_UID_USER_SYS_TIME);

    static com.android.internal.os.KernelCpuProcStringReader getFreqTimeReaderInstance() {
        return FREQ_TIME_READER;
    }

    static com.android.internal.os.KernelCpuProcStringReader getActiveTimeReaderInstance() {
        return ACTIVE_TIME_READER;
    }

    static com.android.internal.os.KernelCpuProcStringReader getClusterTimeReaderInstance() {
        return CLUSTER_TIME_READER;
    }

    static com.android.internal.os.KernelCpuProcStringReader getUserSysTimeReaderInstance() {
        return USER_SYS_TIME_READER;
    }

    public KernelCpuProcStringReader(java.lang.String str) {
        this(str, com.android.internal.os.Clock.SYSTEM_CLOCK);
    }

    public KernelCpuProcStringReader(java.lang.String str, com.android.internal.os.Clock clock) {
        this.mErrors = 0;
        this.mLastReadTime = 0L;
        this.mLock = new java.util.concurrent.locks.ReentrantReadWriteLock();
        this.mReadLock = this.mLock.readLock();
        this.mWriteLock = this.mLock.writeLock();
        this.mFile = java.nio.file.Paths.get(str, new java.lang.String[0]);
        this.mClock = clock;
    }

    public com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator open() {
        return open(false);
    }

    public com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator open(boolean z) {
        if (this.mErrors >= 5) {
            return null;
        }
        if (z) {
            this.mWriteLock.lock();
        } else {
            this.mReadLock.lock();
            if (dataValid()) {
                return new com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator(this.mSize);
            }
            this.mReadLock.unlock();
            this.mWriteLock.lock();
            if (dataValid()) {
                this.mReadLock.lock();
                this.mWriteLock.unlock();
                return new com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator(this.mSize);
            }
        }
        int i = 0;
        this.mSize = 0;
        int allowThreadDiskReadsMask = android.os.StrictMode.allowThreadDiskReadsMask();
        try {
            try {
                try {
                    java.io.BufferedReader newBufferedReader = java.nio.file.Files.newBufferedReader(this.mFile);
                    try {
                        if (this.mBuf == null) {
                            this.mBuf = new char[1024];
                        }
                        while (true) {
                            int read = newBufferedReader.read(this.mBuf, i, this.mBuf.length - i);
                            if (read < 0) {
                                this.mSize = i;
                                this.mLastReadTime = this.mClock.elapsedRealtime();
                                this.mReadLock.lock();
                                com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator procFileIterator = new com.android.internal.os.KernelCpuProcStringReader.ProcFileIterator(i);
                                if (newBufferedReader != null) {
                                    newBufferedReader.close();
                                }
                                return procFileIterator;
                            }
                            i += read;
                            if (i == this.mBuf.length) {
                                if (this.mBuf.length == 1048576) {
                                    this.mErrors++;
                                    android.util.Slog.e(TAG, "Proc file too large: " + this.mFile);
                                    if (newBufferedReader != null) {
                                        newBufferedReader.close();
                                    }
                                    return null;
                                }
                                this.mBuf = java.util.Arrays.copyOf(this.mBuf, java.lang.Math.min(this.mBuf.length << 1, 1048576));
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        if (newBufferedReader != null) {
                            try {
                                newBufferedReader.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (java.io.IOException e) {
                    this.mErrors++;
                    android.util.Slog.e(TAG, "Error reading " + this.mFile, e);
                    return null;
                }
            } catch (java.io.FileNotFoundException | java.nio.file.NoSuchFileException e2) {
                this.mErrors++;
                android.util.Slog.w(TAG, "File not found. It's normal if not implemented: " + this.mFile);
                return null;
            }
        } finally {
            android.os.StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
            this.mWriteLock.unlock();
        }
    }

    private boolean dataValid() {
        return this.mSize > 0 && this.mClock.elapsedRealtime() - this.mLastReadTime < FRESHNESS;
    }

    public class ProcFileIterator implements java.lang.AutoCloseable {
        private int mPos;
        private final int mSize;

        public ProcFileIterator(int i) {
            this.mSize = i;
        }

        public boolean hasNextLine() {
            return this.mPos < this.mSize;
        }

        public java.nio.CharBuffer nextLine() {
            if (this.mPos >= this.mSize) {
                return null;
            }
            int i = this.mPos;
            while (i < this.mSize && com.android.internal.os.KernelCpuProcStringReader.this.mBuf[i] != '\n') {
                i++;
            }
            int i2 = this.mPos;
            this.mPos = i + 1;
            return java.nio.CharBuffer.wrap(com.android.internal.os.KernelCpuProcStringReader.this.mBuf, i2, i - i2);
        }

        public int size() {
            return this.mSize;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            com.android.internal.os.KernelCpuProcStringReader.this.mReadLock.unlock();
        }
    }

    public static int asLongs(java.nio.CharBuffer charBuffer, long[] jArr) {
        if (charBuffer == null) {
            return -1;
        }
        int position = charBuffer.position();
        int i = 0;
        long j = -1;
        while (charBuffer.remaining() > 0 && i < jArr.length) {
            char c = charBuffer.get();
            if (!isNumber(c) && c != ' ' && c != ':') {
                charBuffer.position(position);
                return -2;
            }
            if (j < 0) {
                if (isNumber(c)) {
                    j = c - '0';
                }
            } else if (isNumber(c)) {
                j = ((j * 10) + c) - 48;
                if (j < 0) {
                    charBuffer.position(position);
                    return -3;
                }
            } else {
                jArr[i] = j;
                j = -1;
                i++;
            }
        }
        if (j >= 0) {
            jArr[i] = j;
            i++;
        }
        charBuffer.position(position);
        return i;
    }

    private static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }
}
