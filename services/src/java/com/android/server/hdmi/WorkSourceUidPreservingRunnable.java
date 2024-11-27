package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class WorkSourceUidPreservingRunnable implements java.lang.Runnable {
    private java.lang.Runnable mRunnable;
    private int mUid = android.os.Binder.getCallingWorkSourceUid();

    public WorkSourceUidPreservingRunnable(java.lang.Runnable runnable) {
        this.mRunnable = runnable;
    }

    @Override // java.lang.Runnable
    public void run() {
        long callingWorkSourceUid = android.os.Binder.setCallingWorkSourceUid(this.mUid);
        try {
            this.mRunnable.run();
        } finally {
            android.os.Binder.restoreCallingWorkSource(callingWorkSourceUid);
        }
    }
}
