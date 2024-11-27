package com.android.server.wm;

/* loaded from: classes3.dex */
class ClientLifecycleManager {
    private static final java.lang.String TAG = "ClientLifecycleManager";

    @com.android.internal.annotations.VisibleForTesting
    final android.util.ArrayMap<android.os.IBinder, android.app.servertransaction.ClientTransaction> mPendingTransactions = new android.util.ArrayMap<>();
    private com.android.server.wm.WindowManagerService mWms;

    ClientLifecycleManager() {
    }

    void setWindowManager(@android.annotation.NonNull com.android.server.wm.WindowManagerService windowManagerService) {
        this.mWms = windowManagerService;
    }

    @java.lang.Deprecated
    void scheduleTransaction(@android.annotation.NonNull android.app.servertransaction.ClientTransaction clientTransaction) throws android.os.RemoteException {
        android.app.IApplicationThread client = clientTransaction.getClient();
        try {
            try {
                clientTransaction.schedule();
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to deliver transaction for " + client + "\ntransaction=" + clientTransaction);
                throw e;
            }
        } finally {
            if (!(client instanceof android.os.Binder)) {
                clientTransaction.recycle();
            }
        }
    }

    void scheduleTransactionItemNow(@android.annotation.NonNull android.app.IApplicationThread iApplicationThread, @android.annotation.NonNull android.app.servertransaction.ClientTransactionItem clientTransactionItem) throws android.os.RemoteException {
        android.app.servertransaction.ClientTransaction obtain = android.app.servertransaction.ClientTransaction.obtain(iApplicationThread);
        obtain.addTransactionItem(clientTransactionItem);
        scheduleTransaction(obtain);
    }

    void scheduleTransactionItem(@android.annotation.NonNull android.app.IApplicationThread iApplicationThread, @android.annotation.NonNull android.app.servertransaction.ClientTransactionItem clientTransactionItem) throws android.os.RemoteException {
        if (com.android.window.flags.Flags.bundleClientTransactionFlag()) {
            android.app.servertransaction.ClientTransaction orCreatePendingTransaction = getOrCreatePendingTransaction(iApplicationThread);
            orCreatePendingTransaction.addTransactionItem(clientTransactionItem);
            onClientTransactionItemScheduled(orCreatePendingTransaction, false);
        } else {
            android.app.servertransaction.ClientTransaction obtain = android.app.servertransaction.ClientTransaction.obtain(iApplicationThread);
            obtain.addTransactionItem(clientTransactionItem);
            scheduleTransaction(obtain);
        }
    }

    void scheduleTransactionAndLifecycleItems(@android.annotation.NonNull android.app.IApplicationThread iApplicationThread, @android.annotation.NonNull android.app.servertransaction.ClientTransactionItem clientTransactionItem, @android.annotation.NonNull android.app.servertransaction.ActivityLifecycleItem activityLifecycleItem) throws android.os.RemoteException {
        scheduleTransactionAndLifecycleItems(iApplicationThread, clientTransactionItem, activityLifecycleItem, false);
    }

    void scheduleTransactionAndLifecycleItems(@android.annotation.NonNull android.app.IApplicationThread iApplicationThread, @android.annotation.NonNull android.app.servertransaction.ClientTransactionItem clientTransactionItem, @android.annotation.NonNull android.app.servertransaction.ActivityLifecycleItem activityLifecycleItem, boolean z) throws android.os.RemoteException {
        if (com.android.window.flags.Flags.bundleClientTransactionFlag()) {
            android.app.servertransaction.ClientTransaction orCreatePendingTransaction = getOrCreatePendingTransaction(iApplicationThread);
            orCreatePendingTransaction.addTransactionItem(clientTransactionItem);
            orCreatePendingTransaction.addTransactionItem(activityLifecycleItem);
            onClientTransactionItemScheduled(orCreatePendingTransaction, z);
            return;
        }
        android.app.servertransaction.ClientTransaction obtain = android.app.servertransaction.ClientTransaction.obtain(iApplicationThread);
        obtain.addTransactionItem(clientTransactionItem);
        obtain.addTransactionItem(activityLifecycleItem);
        scheduleTransaction(obtain);
    }

    void dispatchPendingTransactions() {
        if (!com.android.window.flags.Flags.bundleClientTransactionFlag() || this.mPendingTransactions.isEmpty()) {
            return;
        }
        android.os.Trace.traceBegin(32L, "clientTransactionsDispatched");
        int size = this.mPendingTransactions.size();
        for (int i = 0; i < size; i++) {
            try {
                scheduleTransaction(this.mPendingTransactions.valueAt(i));
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to deliver pending transaction", e);
            }
        }
        this.mPendingTransactions.clear();
        android.os.Trace.traceEnd(32L);
    }

    void dispatchPendingTransaction(@android.annotation.NonNull android.app.IApplicationThread iApplicationThread) {
        android.app.servertransaction.ClientTransaction remove;
        if (com.android.window.flags.Flags.bundleClientTransactionFlag() && (remove = this.mPendingTransactions.remove(iApplicationThread.asBinder())) != null) {
            try {
                scheduleTransaction(remove);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to deliver pending transaction", e);
            }
        }
    }

    void onLayoutContinued() {
        if (shouldDispatchPendingTransactionsImmediately()) {
            dispatchPendingTransactions();
        }
    }

    @android.annotation.NonNull
    private android.app.servertransaction.ClientTransaction getOrCreatePendingTransaction(@android.annotation.NonNull android.app.IApplicationThread iApplicationThread) {
        android.os.IBinder asBinder = iApplicationThread.asBinder();
        android.app.servertransaction.ClientTransaction clientTransaction = this.mPendingTransactions.get(asBinder);
        if (clientTransaction != null) {
            return clientTransaction;
        }
        android.app.servertransaction.ClientTransaction obtain = android.app.servertransaction.ClientTransaction.obtain(iApplicationThread);
        this.mPendingTransactions.put(asBinder, obtain);
        return obtain;
    }

    private void onClientTransactionItemScheduled(@android.annotation.NonNull android.app.servertransaction.ClientTransaction clientTransaction, boolean z) throws android.os.RemoteException {
        if (z || shouldDispatchPendingTransactionsImmediately()) {
            this.mPendingTransactions.remove(clientTransaction.getClient().asBinder());
            scheduleTransaction(clientTransaction);
        }
    }

    private boolean shouldDispatchPendingTransactionsImmediately() {
        if (this.mWms == null) {
            return true;
        }
        return (this.mWms.mWindowPlacerLocked.isLayoutDeferred() || this.mWms.mWindowPlacerLocked.isTraversalScheduled() || this.mWms.mWindowPlacerLocked.isInLayout()) ? false : true;
    }

    static boolean shouldDispatchCompatClientTransactionIndependently(int i) {
        return i <= 34;
    }
}
