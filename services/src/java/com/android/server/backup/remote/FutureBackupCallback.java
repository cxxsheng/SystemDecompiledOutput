package com.android.server.backup.remote;

/* loaded from: classes.dex */
public class FutureBackupCallback extends android.app.backup.IBackupCallback.Stub {
    private final java.util.concurrent.CompletableFuture<com.android.server.backup.remote.RemoteResult> mFuture;

    FutureBackupCallback(java.util.concurrent.CompletableFuture<com.android.server.backup.remote.RemoteResult> completableFuture) {
        this.mFuture = completableFuture;
    }

    public void operationComplete(long j) throws android.os.RemoteException {
        this.mFuture.complete(com.android.server.backup.remote.RemoteResult.of(j));
    }
}
