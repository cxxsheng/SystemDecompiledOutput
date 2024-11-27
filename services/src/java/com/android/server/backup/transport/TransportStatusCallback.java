package com.android.server.backup.transport;

/* loaded from: classes.dex */
public class TransportStatusCallback extends com.android.internal.backup.ITransportStatusCallback.Stub {
    private static final int OPERATION_STATUS_DEFAULT = 0;
    private static final java.lang.String TAG = "TransportStatusCallback";

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mHasCompletedOperation;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mOperationStatus;
    private final long mOperationTimeout;

    public TransportStatusCallback() {
        this.mOperationStatus = 0;
        this.mHasCompletedOperation = false;
        this.mOperationTimeout = com.android.server.backup.BackupAndRestoreFeatureFlags.getBackupTransportCallbackTimeoutMillis();
    }

    @com.android.internal.annotations.VisibleForTesting
    TransportStatusCallback(int i) {
        this.mOperationStatus = 0;
        this.mHasCompletedOperation = false;
        this.mOperationTimeout = i;
    }

    public synchronized void onOperationCompleteWithStatus(int i) throws android.os.RemoteException {
        this.mHasCompletedOperation = true;
        this.mOperationStatus = i;
        notifyAll();
    }

    public synchronized void onOperationComplete() throws android.os.RemoteException {
        onOperationCompleteWithStatus(0);
    }

    synchronized int getOperationStatus() {
        if (this.mHasCompletedOperation) {
            return this.mOperationStatus;
        }
        long j = this.mOperationTimeout;
        while (!this.mHasCompletedOperation && j > 0) {
            try {
                long currentTimeMillis = java.lang.System.currentTimeMillis();
                wait(j);
                if (this.mHasCompletedOperation) {
                    return this.mOperationStatus;
                }
                j -= java.lang.System.currentTimeMillis() - currentTimeMillis;
            } catch (java.lang.InterruptedException e) {
                android.util.Slog.w(TAG, "Couldn't get operation status from transport: ", e);
            }
        }
        android.util.Slog.w(TAG, "Couldn't get operation status from transport");
        return -1000;
    }

    synchronized void reset() {
        this.mHasCompletedOperation = false;
        this.mOperationStatus = 0;
    }
}
