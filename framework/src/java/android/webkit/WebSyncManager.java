package android.webkit;

@java.lang.Deprecated
/* loaded from: classes4.dex */
abstract class WebSyncManager implements java.lang.Runnable {
    protected static final java.lang.String LOGTAG = "websync";
    protected android.webkit.WebViewDatabase mDataBase;
    protected android.os.Handler mHandler;

    abstract void syncFromRamToFlash();

    protected WebSyncManager(android.content.Context context, java.lang.String str) {
    }

    protected java.lang.Object clone() throws java.lang.CloneNotSupportedException {
        throw new java.lang.CloneNotSupportedException("doesn't implement Cloneable");
    }

    @Override // java.lang.Runnable
    public void run() {
    }

    public void sync() {
    }

    public void resetSync() {
    }

    public void startSync() {
    }

    public void stopSync() {
    }

    protected void onSyncInit() {
    }
}
