package android.hardware.location;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class ContextHubTransaction<T> {
    public static final int RESULT_FAILED_AT_HUB = 5;
    public static final int RESULT_FAILED_BAD_PARAMS = 2;
    public static final int RESULT_FAILED_BUSY = 4;
    public static final int RESULT_FAILED_HAL_UNAVAILABLE = 8;
    public static final int RESULT_FAILED_NOT_SUPPORTED = 9;
    public static final int RESULT_FAILED_SERVICE_INTERNAL_FAILURE = 7;
    public static final int RESULT_FAILED_TIMEOUT = 6;
    public static final int RESULT_FAILED_UNINITIALIZED = 3;
    public static final int RESULT_FAILED_UNKNOWN = 1;
    public static final int RESULT_SUCCESS = 0;
    private static final java.lang.String TAG = "ContextHubTransaction";
    public static final int TYPE_DISABLE_NANOAPP = 3;
    public static final int TYPE_ENABLE_NANOAPP = 2;
    public static final int TYPE_LOAD_NANOAPP = 0;
    public static final int TYPE_QUERY_NANOAPPS = 4;
    public static final int TYPE_RELIABLE_MESSAGE = 5;
    public static final int TYPE_UNLOAD_NANOAPP = 1;
    private android.hardware.location.ContextHubTransaction.Response<T> mResponse;
    private int mTransactionType;
    private java.util.concurrent.Executor mExecutor = null;
    private android.hardware.location.ContextHubTransaction.OnCompleteListener<T> mListener = null;
    private final java.util.concurrent.CountDownLatch mDoneSignal = new java.util.concurrent.CountDownLatch(1);
    private boolean mIsResponseSet = false;

    @java.lang.FunctionalInterface
    public interface OnCompleteListener<L> {
        void onComplete(android.hardware.location.ContextHubTransaction<L> contextHubTransaction, android.hardware.location.ContextHubTransaction.Response<L> response);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Result {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static class Response<R> {
        private R mContents;
        private int mResult;

        Response(int i, R r) {
            this.mResult = i;
            this.mContents = r;
        }

        public int getResult() {
            return this.mResult;
        }

        public R getContents() {
            return this.mContents;
        }
    }

    ContextHubTransaction(int i) {
        this.mTransactionType = i;
    }

    public static java.lang.String typeToString(int i, boolean z) {
        switch (i) {
            case 0:
                return z ? "Load" : "load";
            case 1:
                return z ? "Unload" : "unload";
            case 2:
                return z ? "Enable" : "enable";
            case 3:
                return z ? "Disable" : "disable";
            case 4:
                return z ? "Query" : "query";
            case 5:
                if (android.chre.flags.Flags.reliableMessage()) {
                    return z ? "Reliable Message" : "reliable message";
                }
                break;
        }
        return z ? "Unknown" : "unknown";
    }

    public int getType() {
        return this.mTransactionType;
    }

    public android.hardware.location.ContextHubTransaction.Response<T> waitForResponse(long j, java.util.concurrent.TimeUnit timeUnit) throws java.lang.InterruptedException, java.util.concurrent.TimeoutException {
        if (!this.mDoneSignal.await(j, timeUnit)) {
            throw new java.util.concurrent.TimeoutException("Timed out while waiting for transaction");
        }
        return this.mResponse;
    }

    public void setOnCompleteListener(android.hardware.location.ContextHubTransaction.OnCompleteListener<T> onCompleteListener, java.util.concurrent.Executor executor) {
        synchronized (this) {
            java.util.Objects.requireNonNull(onCompleteListener, "OnCompleteListener cannot be null");
            java.util.Objects.requireNonNull(executor, "Executor cannot be null");
            if (this.mListener != null) {
                throw new java.lang.IllegalStateException("Cannot set ContextHubTransaction listener multiple times");
            }
            this.mListener = onCompleteListener;
            this.mExecutor = executor;
            if (this.mDoneSignal.getCount() == 0) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.location.ContextHubTransaction$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.location.ContextHubTransaction.this.lambda$setOnCompleteListener$0();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOnCompleteListener$0() {
        this.mListener.onComplete(this, this.mResponse);
    }

    public void setOnCompleteListener(android.hardware.location.ContextHubTransaction.OnCompleteListener<T> onCompleteListener) {
        setOnCompleteListener(onCompleteListener, new android.os.HandlerExecutor(android.os.Handler.getMain()));
    }

    void setResponse(android.hardware.location.ContextHubTransaction.Response<T> response) {
        synchronized (this) {
            java.util.Objects.requireNonNull(response, "Response cannot be null");
            if (this.mIsResponseSet) {
                throw new java.lang.IllegalStateException("Cannot set response of ContextHubTransaction multiple times");
            }
            this.mResponse = response;
            this.mIsResponseSet = true;
            this.mDoneSignal.countDown();
            if (this.mListener != null) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.location.ContextHubTransaction$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.location.ContextHubTransaction.this.lambda$setResponse$1();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setResponse$1() {
        this.mListener.onComplete(this, this.mResponse);
    }
}
