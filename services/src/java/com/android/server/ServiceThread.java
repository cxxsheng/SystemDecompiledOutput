package com.android.server;

/* loaded from: classes.dex */
public class ServiceThread extends android.os.HandlerThread {
    private static final java.lang.String TAG = "ServiceThread";
    private final boolean mAllowIo;

    public ServiceThread(java.lang.String str, int i, boolean z) {
        super(str, i);
        this.mAllowIo = z;
    }

    @Override // android.os.HandlerThread, java.lang.Thread, java.lang.Runnable
    public void run() {
        android.os.Process.setCanSelfBackground(false);
        if (!this.mAllowIo) {
            android.os.StrictMode.initThreadDefaults(null);
        }
        super.run();
    }

    protected static android.os.Handler makeSharedHandler(android.os.Looper looper) {
        return new android.os.Handler(looper, null, false, true);
    }
}
