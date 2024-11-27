package com.android.server;

/* loaded from: classes5.dex */
abstract class ResettableTimeout {
    private android.os.ConditionVariable mLock = new android.os.ConditionVariable();
    private volatile long mOffAt;
    private volatile boolean mOffCalled;
    private java.lang.Thread mThread;

    public abstract void off();

    public abstract void on(boolean z);

    ResettableTimeout() {
    }

    public void go(long j) {
        boolean z;
        synchronized (this) {
            this.mOffAt = android.os.SystemClock.uptimeMillis() + j;
            if (this.mThread == null) {
                this.mLock.close();
                this.mThread = new com.android.server.ResettableTimeout.T();
                this.mThread.start();
                this.mLock.block();
                z = false;
                this.mOffCalled = false;
            } else {
                this.mThread.interrupt();
                z = true;
            }
            on(z);
        }
    }

    public void cancel() {
        synchronized (this) {
            this.mOffAt = 0L;
            if (this.mThread != null) {
                this.mThread.interrupt();
                this.mThread = null;
            }
            if (!this.mOffCalled) {
                this.mOffCalled = true;
                off();
            }
        }
    }

    private class T extends java.lang.Thread {
        private T() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            long uptimeMillis;
            com.android.server.ResettableTimeout.this.mLock.open();
            while (true) {
                synchronized (this) {
                    uptimeMillis = com.android.server.ResettableTimeout.this.mOffAt - android.os.SystemClock.uptimeMillis();
                    if (uptimeMillis <= 0) {
                        com.android.server.ResettableTimeout.this.mOffCalled = true;
                        com.android.server.ResettableTimeout.this.off();
                        com.android.server.ResettableTimeout.this.mThread = null;
                        return;
                    }
                }
                try {
                    sleep(uptimeMillis);
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
    }
}
