package com.android.server.location.contexthub;

/* loaded from: classes2.dex */
public class AuthStateDenialTimer {
    private static final int MSG = 1;
    private static final long TIMEOUT_MS = java.util.concurrent.TimeUnit.SECONDS.toMillis(60);
    private boolean mCancelled = false;
    private final com.android.server.location.contexthub.ContextHubClientBroker mClient;
    private final android.os.Handler mHandler;
    private final long mNanoAppId;
    private long mStopTimeInFuture;

    public AuthStateDenialTimer(com.android.server.location.contexthub.ContextHubClientBroker contextHubClientBroker, long j, android.os.Looper looper) {
        this.mClient = contextHubClientBroker;
        this.mNanoAppId = j;
        this.mHandler = new com.android.server.location.contexthub.AuthStateDenialTimer.CountDownHandler(looper);
    }

    public synchronized void cancel() {
        this.mCancelled = true;
        this.mHandler.removeMessages(1);
    }

    public synchronized void start() {
        this.mCancelled = false;
        this.mStopTimeInFuture = android.os.SystemClock.elapsedRealtime() + TIMEOUT_MS;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1));
    }

    public void onFinish() {
        this.mClient.handleAuthStateTimerExpiry(this.mNanoAppId);
    }

    private class CountDownHandler extends android.os.Handler {
        CountDownHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            synchronized (com.android.server.location.contexthub.AuthStateDenialTimer.this) {
                try {
                    if (com.android.server.location.contexthub.AuthStateDenialTimer.this.mCancelled) {
                        return;
                    }
                    long elapsedRealtime = com.android.server.location.contexthub.AuthStateDenialTimer.this.mStopTimeInFuture - android.os.SystemClock.elapsedRealtime();
                    if (elapsedRealtime <= 0) {
                        com.android.server.location.contexthub.AuthStateDenialTimer.this.onFinish();
                    } else {
                        sendMessageDelayed(obtainMessage(1), elapsedRealtime);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
