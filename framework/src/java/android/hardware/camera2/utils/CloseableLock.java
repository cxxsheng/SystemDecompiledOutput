package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class CloseableLock implements java.lang.AutoCloseable {
    private static final boolean VERBOSE = false;
    private final java.lang.String TAG;
    private volatile boolean mClosed;
    private final java.util.concurrent.locks.Condition mCondition;
    private boolean mExclusive;
    private final java.util.concurrent.locks.ReentrantLock mLock;
    private final java.lang.ThreadLocal<java.lang.Integer> mLockCount;
    private final java.lang.String mName;
    private int mSharedLocks;

    public class ScopedLock implements java.lang.AutoCloseable {
        private ScopedLock() {
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            android.hardware.camera2.utils.CloseableLock.this.releaseLock();
        }
    }

    public CloseableLock() {
        this.TAG = "CloseableLock";
        this.mClosed = false;
        this.mExclusive = false;
        this.mSharedLocks = 0;
        this.mLock = new java.util.concurrent.locks.ReentrantLock();
        this.mCondition = this.mLock.newCondition();
        this.mLockCount = new java.lang.ThreadLocal<java.lang.Integer>() { // from class: android.hardware.camera2.utils.CloseableLock.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.lang.ThreadLocal
            public java.lang.Integer initialValue() {
                return 0;
            }
        };
        this.mName = "";
    }

    public CloseableLock(java.lang.String str) {
        this.TAG = "CloseableLock";
        this.mClosed = false;
        this.mExclusive = false;
        this.mSharedLocks = 0;
        this.mLock = new java.util.concurrent.locks.ReentrantLock();
        this.mCondition = this.mLock.newCondition();
        this.mLockCount = new java.lang.ThreadLocal<java.lang.Integer>() { // from class: android.hardware.camera2.utils.CloseableLock.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.lang.ThreadLocal
            public java.lang.Integer initialValue() {
                return 0;
            }
        };
        this.mName = str;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.mClosed || acquireExclusiveLock() == null) {
            return;
        }
        if (this.mLockCount.get().intValue() != 1) {
            throw new java.lang.IllegalStateException("Cannot close while one or more acquired locks are being held by this thread; release all other locks first");
        }
        try {
            this.mLock.lock();
            this.mClosed = true;
            this.mExclusive = false;
            this.mSharedLocks = 0;
            this.mLockCount.remove();
            this.mCondition.signalAll();
        } finally {
            this.mLock.unlock();
        }
    }

    public android.hardware.camera2.utils.CloseableLock.ScopedLock acquireLock() {
        try {
            this.mLock.lock();
            if (this.mClosed) {
                return null;
            }
            int intValue = this.mLockCount.get().intValue();
            if (this.mExclusive && intValue > 0) {
                throw new java.lang.IllegalStateException("Cannot acquire shared lock while holding exclusive lock");
            }
            while (this.mExclusive) {
                this.mCondition.awaitUninterruptibly();
                if (this.mClosed) {
                    return null;
                }
            }
            this.mSharedLocks++;
            this.mLockCount.set(java.lang.Integer.valueOf(this.mLockCount.get().intValue() + 1));
            this.mLock.unlock();
            return new android.hardware.camera2.utils.CloseableLock.ScopedLock();
        } finally {
            this.mLock.unlock();
        }
    }

    public android.hardware.camera2.utils.CloseableLock.ScopedLock acquireExclusiveLock() {
        try {
            this.mLock.lock();
            if (this.mClosed) {
                return null;
            }
            int intValue = this.mLockCount.get().intValue();
            if (!this.mExclusive && intValue > 0) {
                throw new java.lang.IllegalStateException("Cannot acquire exclusive lock while holding shared lock");
            }
            while (intValue == 0 && (this.mExclusive || this.mSharedLocks > 0)) {
                this.mCondition.awaitUninterruptibly();
                if (this.mClosed) {
                    return null;
                }
            }
            this.mExclusive = true;
            this.mLockCount.set(java.lang.Integer.valueOf(this.mLockCount.get().intValue() + 1));
            this.mLock.unlock();
            return new android.hardware.camera2.utils.CloseableLock.ScopedLock();
        } finally {
            this.mLock.unlock();
        }
    }

    public void releaseLock() {
        if (this.mLockCount.get().intValue() <= 0) {
            throw new java.lang.IllegalStateException("Cannot release lock that was not acquired by this thread");
        }
        try {
            this.mLock.lock();
            if (this.mClosed) {
                throw new java.lang.IllegalStateException("Do not release after the lock has been closed");
            }
            if (!this.mExclusive) {
                this.mSharedLocks--;
            } else if (this.mSharedLocks != 0) {
                throw new java.lang.AssertionError("Too many shared locks " + this.mSharedLocks);
            }
            int intValue = this.mLockCount.get().intValue() - 1;
            this.mLockCount.set(java.lang.Integer.valueOf(intValue));
            if (intValue == 0 && this.mExclusive) {
                this.mExclusive = false;
                this.mCondition.signalAll();
            } else if (intValue == 0 && this.mSharedLocks == 0) {
                this.mCondition.signalAll();
            }
        } finally {
            this.mLock.unlock();
        }
    }

    private void log(java.lang.String str) {
        android.util.Log.v("CloseableLock[" + this.mName + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END, str);
    }
}
