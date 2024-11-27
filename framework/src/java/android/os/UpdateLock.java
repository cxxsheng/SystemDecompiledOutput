package android.os;

/* loaded from: classes3.dex */
public class UpdateLock {
    private static final boolean DEBUG = false;
    public static final java.lang.String NOW_IS_CONVENIENT = "nowisconvenient";
    private static final java.lang.String TAG = "UpdateLock";
    public static final java.lang.String TIMESTAMP = "timestamp";
    public static final java.lang.String UPDATE_LOCK_CHANGED = "android.os.UpdateLock.UPDATE_LOCK_CHANGED";
    private static android.os.IUpdateLock sService;
    final java.lang.String mTag;
    int mCount = 0;
    boolean mRefCounted = true;
    boolean mHeld = false;
    android.os.IBinder mToken = new android.os.Binder();

    private static void checkService() {
        if (sService == null) {
            sService = android.os.IUpdateLock.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.UPDATE_LOCK_SERVICE));
        }
    }

    public UpdateLock(java.lang.String str) {
        this.mTag = str;
    }

    public void setReferenceCounted(boolean z) {
        this.mRefCounted = z;
    }

    public boolean isHeld() {
        boolean z;
        synchronized (this.mToken) {
            z = this.mHeld;
        }
        return z;
    }

    public void acquire() {
        checkService();
        synchronized (this.mToken) {
            acquireLocked();
        }
    }

    private void acquireLocked() {
        if (this.mRefCounted) {
            int i = this.mCount;
            this.mCount = i + 1;
            if (i != 0) {
                return;
            }
        }
        if (sService != null) {
            try {
                sService.acquireUpdateLock(this.mToken, this.mTag);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Unable to contact service to acquire");
            }
        }
        this.mHeld = true;
    }

    public void release() {
        checkService();
        synchronized (this.mToken) {
            releaseLocked();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x000a, code lost:
    
        if (r0 == 0) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void releaseLocked() {
        if (this.mRefCounted) {
            int i = this.mCount - 1;
            this.mCount = i;
        }
        if (sService != null) {
            try {
                sService.releaseUpdateLock(this.mToken);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Unable to contact service to release");
            }
        }
        this.mHeld = false;
        if (this.mCount < 0) {
            throw new java.lang.RuntimeException("UpdateLock under-locked");
        }
    }

    protected void finalize() throws java.lang.Throwable {
        synchronized (this.mToken) {
            if (this.mHeld) {
                android.util.Log.wtf(TAG, "UpdateLock finalized while still held");
                try {
                    sService.releaseUpdateLock(this.mToken);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "Unable to contact service to release");
                }
            }
        }
    }
}
