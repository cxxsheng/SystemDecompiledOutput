package android.app.servertransaction;

/* loaded from: classes.dex */
public interface BaseClientRequest extends android.app.servertransaction.ObjectPoolItem {
    void execute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions);

    default void preExecute(android.app.ClientTransactionHandler clientTransactionHandler) {
    }

    default void postExecute(android.app.ClientTransactionHandler clientTransactionHandler, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
    }
}
