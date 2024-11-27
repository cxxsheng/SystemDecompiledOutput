package com.android.server.backup.remote;

/* loaded from: classes.dex */
public class RemoteCall {
    private final com.android.server.backup.remote.RemoteCallable<android.app.backup.IBackupCallback> mCallable;
    private final java.util.concurrent.CompletableFuture<com.android.server.backup.remote.RemoteResult> mFuture;
    private final long mTimeoutMs;

    public static com.android.server.backup.remote.RemoteResult execute(com.android.server.backup.remote.RemoteCallable<android.app.backup.IBackupCallback> remoteCallable, long j) throws android.os.RemoteException {
        return new com.android.server.backup.remote.RemoteCall(remoteCallable, j).call();
    }

    public RemoteCall(com.android.server.backup.remote.RemoteCallable<android.app.backup.IBackupCallback> remoteCallable, long j) {
        this(false, remoteCallable, j);
    }

    public RemoteCall(boolean z, com.android.server.backup.remote.RemoteCallable<android.app.backup.IBackupCallback> remoteCallable, long j) {
        this.mCallable = remoteCallable;
        this.mTimeoutMs = j;
        this.mFuture = new java.util.concurrent.CompletableFuture<>();
        if (z) {
            cancel();
        }
    }

    public com.android.server.backup.remote.RemoteResult call() throws android.os.RemoteException {
        com.android.internal.util.Preconditions.checkState(!android.os.Looper.getMainLooper().isCurrentThread(), "Can't call call() on main thread");
        if (!this.mFuture.isDone()) {
            if (this.mTimeoutMs == 0) {
                timeOut();
            } else {
                android.os.Handler.getMain().postDelayed(new java.lang.Runnable() { // from class: com.android.server.backup.remote.RemoteCall$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.backup.remote.RemoteCall.this.timeOut();
                    }
                }, this.mTimeoutMs);
                this.mCallable.call(new com.android.server.backup.remote.FutureBackupCallback(this.mFuture));
            }
        }
        try {
            return this.mFuture.get();
        } catch (java.lang.InterruptedException e) {
            return com.android.server.backup.remote.RemoteResult.FAILED_THREAD_INTERRUPTED;
        } catch (java.util.concurrent.ExecutionException e2) {
            throw new java.lang.IllegalStateException("Future unexpectedly completed with an exception");
        }
    }

    public void cancel() {
        this.mFuture.complete(com.android.server.backup.remote.RemoteResult.FAILED_CANCELLED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void timeOut() {
        this.mFuture.complete(com.android.server.backup.remote.RemoteResult.FAILED_TIMED_OUT);
    }
}
