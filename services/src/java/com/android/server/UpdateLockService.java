package com.android.server;

/* loaded from: classes.dex */
public class UpdateLockService extends android.os.IUpdateLock.Stub {
    static final boolean DEBUG = false;
    static final java.lang.String PERMISSION = "android.permission.UPDATE_LOCK";
    static final java.lang.String TAG = "UpdateLockService";
    android.content.Context mContext;
    com.android.server.UpdateLockService.LockWatcher mLocks = new com.android.server.UpdateLockService.LockWatcher(new android.os.Handler(), "UpdateLocks");

    class LockWatcher extends android.os.TokenWatcher {
        LockWatcher(android.os.Handler handler, java.lang.String str) {
            super(handler, str);
        }

        @Override // android.os.TokenWatcher
        public void acquired() {
            com.android.server.UpdateLockService.this.sendLockChangedBroadcast(false);
        }

        @Override // android.os.TokenWatcher
        public void released() {
            com.android.server.UpdateLockService.this.sendLockChangedBroadcast(true);
        }
    }

    UpdateLockService(android.content.Context context) {
        this.mContext = context;
        sendLockChangedBroadcast(true);
    }

    void sendLockChangedBroadcast(boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mContext.sendStickyBroadcastAsUser(new android.content.Intent("android.os.UpdateLock.UPDATE_LOCK_CHANGED").putExtra("nowisconvenient", z).putExtra(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.TIMESTAMP, java.lang.System.currentTimeMillis()).addFlags(67108864), android.os.UserHandle.ALL);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void acquireUpdateLock(android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
        this.mContext.enforceCallingOrSelfPermission(PERMISSION, "acquireUpdateLock");
        this.mLocks.acquire(iBinder, makeTag(str));
    }

    public void releaseUpdateLock(android.os.IBinder iBinder) throws android.os.RemoteException {
        this.mContext.enforceCallingOrSelfPermission(PERMISSION, "releaseUpdateLock");
        this.mLocks.release(iBinder);
    }

    private java.lang.String makeTag(java.lang.String str) {
        return "{tag=" + str + " uid=" + android.os.Binder.getCallingUid() + " pid=" + android.os.Binder.getCallingPid() + '}';
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            this.mLocks.dump(printWriter);
        }
    }
}
