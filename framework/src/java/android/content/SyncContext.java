package android.content;

/* loaded from: classes.dex */
public class SyncContext {
    private static final long HEARTBEAT_SEND_INTERVAL_IN_MS = 1000;
    private long mLastHeartbeatSendTime = 0;
    private android.content.ISyncContext mSyncContext;

    public SyncContext(android.content.ISyncContext iSyncContext) {
        this.mSyncContext = iSyncContext;
    }

    public void setStatusText(java.lang.String str) {
        updateHeartbeat();
    }

    private void updateHeartbeat() {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        if (elapsedRealtime < this.mLastHeartbeatSendTime + 1000) {
            return;
        }
        try {
            this.mLastHeartbeatSendTime = elapsedRealtime;
            if (this.mSyncContext != null) {
                this.mSyncContext.sendHeartbeat();
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public void onFinished(android.content.SyncResult syncResult) {
        try {
            if (this.mSyncContext != null) {
                this.mSyncContext.onFinished(syncResult);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public android.os.IBinder getSyncContextBinder() {
        if (this.mSyncContext == null) {
            return null;
        }
        return this.mSyncContext.asBinder();
    }
}
