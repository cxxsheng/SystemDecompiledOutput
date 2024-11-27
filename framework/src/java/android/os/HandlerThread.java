package android.os;

/* loaded from: classes3.dex */
public class HandlerThread extends java.lang.Thread {
    private volatile java.util.concurrent.Executor mExecutor;
    private volatile android.os.Handler mHandler;
    android.os.Looper mLooper;
    int mPriority;
    int mTid;

    public HandlerThread(java.lang.String str) {
        super(str);
        this.mTid = -1;
        this.mPriority = 0;
        onCreated();
    }

    public HandlerThread(java.lang.String str, int i) {
        super(str);
        this.mTid = -1;
        this.mPriority = i;
        onCreated();
    }

    protected void onCreated() {
    }

    protected void onCreated$ravenwood() {
        setDaemon(true);
    }

    protected void onLooperPrepared() {
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        this.mTid = android.os.Process.myTid();
        android.os.Looper.prepare();
        synchronized (this) {
            this.mLooper = android.os.Looper.myLooper();
            notifyAll();
        }
        android.os.Process.setThreadPriority(this.mPriority);
        onLooperPrepared();
        android.os.Looper.loop();
        this.mTid = -1;
    }

    public android.os.Looper getLooper() {
        boolean z;
        if (!isAlive()) {
            return null;
        }
        synchronized (this) {
            z = false;
            while (isAlive() && this.mLooper == null) {
                try {
                    wait();
                } catch (java.lang.InterruptedException e) {
                    z = true;
                }
            }
        }
        if (z) {
            java.lang.Thread.currentThread().interrupt();
        }
        return this.mLooper;
    }

    public android.os.Handler getThreadHandler() {
        if (this.mHandler == null) {
            this.mHandler = new android.os.Handler(getLooper());
        }
        return this.mHandler;
    }

    public java.util.concurrent.Executor getThreadExecutor() {
        if (this.mExecutor == null) {
            this.mExecutor = new android.os.HandlerExecutor(getThreadHandler());
        }
        return this.mExecutor;
    }

    public boolean quit() {
        android.os.Looper looper = getLooper();
        if (looper != null) {
            looper.quit();
            return true;
        }
        return false;
    }

    public boolean quitSafely() {
        android.os.Looper looper = getLooper();
        if (looper != null) {
            looper.quitSafely();
            return true;
        }
        return false;
    }

    public int getThreadId() {
        return this.mTid;
    }
}
