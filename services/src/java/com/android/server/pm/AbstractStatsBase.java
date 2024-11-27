package com.android.server.pm;

/* loaded from: classes2.dex */
public abstract class AbstractStatsBase<T> {
    private static final int WRITE_INTERVAL_MS = 1800000;
    private final java.lang.String mBackgroundThreadName;
    private final java.lang.String mFileName;
    private final boolean mLock;
    private final java.lang.Object mFileLock = new java.lang.Object();
    private final java.util.concurrent.atomic.AtomicLong mLastTimeWritten = new java.util.concurrent.atomic.AtomicLong(0);
    private final java.util.concurrent.atomic.AtomicBoolean mBackgroundWriteRunning = new java.util.concurrent.atomic.AtomicBoolean(false);

    protected abstract void readInternal(T t);

    protected abstract void writeInternal(T t);

    protected AbstractStatsBase(java.lang.String str, java.lang.String str2, boolean z) {
        this.mFileName = str;
        this.mBackgroundThreadName = str2;
        this.mLock = z;
    }

    protected android.util.AtomicFile getFile() {
        return new android.util.AtomicFile(new java.io.File(new java.io.File(android.os.Environment.getDataDirectory(), "system"), this.mFileName));
    }

    protected void writeNow(T t) {
        writeImpl(t);
        this.mLastTimeWritten.set(android.os.SystemClock.elapsedRealtime());
    }

    protected boolean maybeWriteAsync(final T t) {
        if (android.os.SystemClock.elapsedRealtime() - this.mLastTimeWritten.get() < 1800000 || !this.mBackgroundWriteRunning.compareAndSet(false, true)) {
            return false;
        }
        new java.lang.Thread(this.mBackgroundThreadName) { // from class: com.android.server.pm.AbstractStatsBase.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    com.android.server.pm.AbstractStatsBase.this.writeImpl(t);
                    com.android.server.pm.AbstractStatsBase.this.mLastTimeWritten.set(android.os.SystemClock.elapsedRealtime());
                } finally {
                    com.android.server.pm.AbstractStatsBase.this.mBackgroundWriteRunning.set(false);
                }
            }
        }.start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeImpl(T t) {
        if (this.mLock) {
            synchronized (t) {
                synchronized (this.mFileLock) {
                    writeInternal(t);
                }
            }
            return;
        }
        synchronized (this.mFileLock) {
            writeInternal(t);
        }
    }

    protected void read(T t) {
        if (this.mLock) {
            synchronized (t) {
                synchronized (this.mFileLock) {
                    readInternal(t);
                }
            }
        } else {
            synchronized (this.mFileLock) {
                readInternal(t);
            }
        }
        this.mLastTimeWritten.set(android.os.SystemClock.elapsedRealtime());
    }
}
