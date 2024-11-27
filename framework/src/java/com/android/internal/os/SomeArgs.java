package com.android.internal.os;

/* loaded from: classes4.dex */
public final class SomeArgs {
    private static final int MAX_POOL_SIZE = 10;
    static final int WAIT_FINISHED = 2;
    static final int WAIT_NONE = 0;
    static final int WAIT_WAITING = 1;
    private static com.android.internal.os.SomeArgs sPool;
    private static java.lang.Object sPoolLock = new java.lang.Object();
    private static int sPoolSize;
    public java.lang.Object arg1;
    public java.lang.Object arg2;
    public java.lang.Object arg3;
    public java.lang.Object arg4;
    public java.lang.Object arg5;
    public java.lang.Object arg6;
    public java.lang.Object arg7;
    public int argi1;
    public int argi2;
    public int argi3;
    public int argi4;
    public int argi5;
    public int argi6;
    public long argl1;
    public long argl2;
    private boolean mInPool;
    private com.android.internal.os.SomeArgs mNext;
    int mWaitState = 0;

    private SomeArgs() {
    }

    public static com.android.internal.os.SomeArgs obtain() {
        synchronized (sPoolLock) {
            if (sPoolSize > 0) {
                com.android.internal.os.SomeArgs someArgs = sPool;
                sPool = sPool.mNext;
                someArgs.mNext = null;
                someArgs.mInPool = false;
                sPoolSize--;
                return someArgs;
            }
            return new com.android.internal.os.SomeArgs();
        }
    }

    public void complete() {
        synchronized (this) {
            if (this.mWaitState != 1) {
                throw new java.lang.IllegalStateException("Not waiting");
            }
            this.mWaitState = 2;
            notifyAll();
        }
    }

    public void recycle() {
        if (this.mInPool) {
            throw new java.lang.IllegalStateException("Already recycled.");
        }
        if (this.mWaitState != 0) {
            return;
        }
        synchronized (sPoolLock) {
            clear();
            if (sPoolSize < 10) {
                this.mNext = sPool;
                this.mInPool = true;
                sPool = this;
                sPoolSize++;
            }
        }
    }

    private void clear() {
        this.arg1 = null;
        this.arg2 = null;
        this.arg3 = null;
        this.arg4 = null;
        this.arg5 = null;
        this.arg6 = null;
        this.arg7 = null;
        this.argi1 = 0;
        this.argi2 = 0;
        this.argi3 = 0;
        this.argi4 = 0;
        this.argi5 = 0;
        this.argi6 = 0;
        this.argl1 = 0L;
        this.argl2 = 0L;
    }
}
