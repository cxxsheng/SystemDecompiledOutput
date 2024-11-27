package android.util;

/* loaded from: classes3.dex */
public abstract class TimedRemoteCaller<T> {
    public static final long DEFAULT_CALL_TIMEOUT_MILLIS = 5000;
    private final long mCallTimeoutMillis;
    private int mSequenceCounter;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.SparseIntArray mAwaitedCalls = new android.util.SparseIntArray(1);
    private final android.util.SparseArray<T> mReceivedCalls = new android.util.SparseArray<>(1);

    public TimedRemoteCaller(long j) {
        this.mCallTimeoutMillis = j;
    }

    protected final int onBeforeRemoteCall() {
        int i;
        synchronized (this.mLock) {
            do {
                i = this.mSequenceCounter;
                this.mSequenceCounter = i + 1;
            } while (this.mAwaitedCalls.get(i) != 0);
            this.mAwaitedCalls.put(i, 1);
        }
        return i;
    }

    protected final void onRemoteMethodResult(T t, int i) {
        synchronized (this.mLock) {
            if (this.mAwaitedCalls.get(i) != 0) {
                this.mAwaitedCalls.delete(i);
                this.mReceivedCalls.put(i, t);
                this.mLock.notifyAll();
            }
        }
    }

    protected final T getResultTimed(int i) throws java.util.concurrent.TimeoutException {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        while (true) {
            try {
                synchronized (this.mLock) {
                    if (this.mReceivedCalls.indexOfKey(i) >= 0) {
                        return this.mReceivedCalls.removeReturnOld(i);
                    }
                    long uptimeMillis2 = this.mCallTimeoutMillis - (android.os.SystemClock.uptimeMillis() - uptimeMillis);
                    if (uptimeMillis2 <= 0) {
                        this.mAwaitedCalls.delete(i);
                        throw new java.util.concurrent.TimeoutException("No response for sequence: " + i);
                    }
                    this.mLock.wait(uptimeMillis2);
                }
            } catch (java.lang.InterruptedException e) {
            }
        }
    }
}
