package com.android.server.backup.restore;

/* loaded from: classes.dex */
public class RestoreDeleteObserver extends android.content.pm.IPackageDeleteObserver.Stub {

    @com.android.internal.annotations.GuardedBy({"mDone"})
    private final java.util.concurrent.atomic.AtomicBoolean mDone = new java.util.concurrent.atomic.AtomicBoolean();

    public void reset() {
        synchronized (this.mDone) {
            this.mDone.set(false);
        }
    }

    public void waitForCompletion() {
        synchronized (this.mDone) {
            while (!this.mDone.get()) {
                try {
                    this.mDone.wait();
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
    }

    public void packageDeleted(java.lang.String str, int i) throws android.os.RemoteException {
        synchronized (this.mDone) {
            this.mDone.set(true);
            this.mDone.notifyAll();
        }
    }
}
