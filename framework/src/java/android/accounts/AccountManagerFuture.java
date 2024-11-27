package android.accounts;

/* loaded from: classes.dex */
public interface AccountManagerFuture<V> {
    boolean cancel(boolean z);

    V getResult() throws android.accounts.OperationCanceledException, java.io.IOException, android.accounts.AuthenticatorException;

    V getResult(long j, java.util.concurrent.TimeUnit timeUnit) throws android.accounts.OperationCanceledException, java.io.IOException, android.accounts.AuthenticatorException;

    boolean isCancelled();

    boolean isDone();
}
