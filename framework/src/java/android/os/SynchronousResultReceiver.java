package android.os;

/* loaded from: classes3.dex */
public class SynchronousResultReceiver extends android.os.ResultReceiver {
    private final java.util.concurrent.CompletableFuture<android.os.SynchronousResultReceiver.Result> mFuture;
    private final java.lang.String mName;

    public static class Result {
        public android.os.Bundle bundle;
        public int resultCode;

        public Result(int i, android.os.Bundle bundle) {
            this.resultCode = i;
            this.bundle = bundle;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SynchronousResultReceiver() {
        super((android.os.Handler) null);
        this.mFuture = new java.util.concurrent.CompletableFuture<>();
        this.mName = null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SynchronousResultReceiver(java.lang.String str) {
        super((android.os.Handler) null);
        this.mFuture = new java.util.concurrent.CompletableFuture<>();
        this.mName = str;
    }

    @Override // android.os.ResultReceiver
    protected final void onReceiveResult(int i, android.os.Bundle bundle) {
        super.onReceiveResult(i, bundle);
        this.mFuture.complete(new android.os.SynchronousResultReceiver.Result(i, bundle));
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public android.os.SynchronousResultReceiver.Result awaitResult(long j) throws java.util.concurrent.TimeoutException {
        long currentTimeMillis = java.lang.System.currentTimeMillis() + j;
        while (j >= 0) {
            try {
                return this.mFuture.get(j, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (java.lang.InterruptedException e) {
                j -= currentTimeMillis - java.lang.System.currentTimeMillis();
            } catch (java.util.concurrent.ExecutionException e2) {
                throw new java.lang.AssertionError("Error receiving response", e2);
            }
        }
        throw new java.util.concurrent.TimeoutException();
    }
}
