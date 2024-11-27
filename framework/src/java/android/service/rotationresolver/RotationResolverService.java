package android.service.rotationresolver;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class RotationResolverService extends android.app.Service {
    public static final int ROTATION_RESULT_FAILURE_CANCELLED = 0;
    public static final int ROTATION_RESULT_FAILURE_NOT_SUPPORTED = 4;
    public static final int ROTATION_RESULT_FAILURE_PREEMPTED = 2;
    public static final int ROTATION_RESULT_FAILURE_TIMED_OUT = 1;
    public static final int ROTATION_RESULT_FAILURE_UNKNOWN = 3;
    public static final java.lang.String SERVICE_INTERFACE = "android.service.rotationresolver.RotationResolverService";
    private android.os.CancellationSignal mCancellationSignal;
    private final android.os.Handler mMainThreadHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
    private android.service.rotationresolver.RotationResolverService.RotationResolverCallbackWrapper mPendingCallback;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FailureCodes {
    }

    public interface RotationResolverCallback {
        void onFailure(int i);

        void onSuccess(int i);
    }

    public abstract void onResolveRotation(android.service.rotationresolver.RotationResolutionRequest rotationResolutionRequest, android.os.CancellationSignal cancellationSignal, android.service.rotationresolver.RotationResolverService.RotationResolverCallback rotationResolverCallback);

    /* renamed from: android.service.rotationresolver.RotationResolverService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.rotationresolver.IRotationResolverService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.rotationresolver.IRotationResolverService
        public void resolveRotation(android.service.rotationresolver.IRotationResolverCallback iRotationResolverCallback, android.service.rotationresolver.RotationResolutionRequest rotationResolutionRequest) throws android.os.RemoteException {
            java.util.Objects.requireNonNull(iRotationResolverCallback);
            java.util.Objects.requireNonNull(rotationResolutionRequest);
            android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
            iRotationResolverCallback.onCancellable(createTransport);
            android.service.rotationresolver.RotationResolverService.this.mMainThreadHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.service.rotationresolver.RotationResolverService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.service.rotationresolver.RotationResolverService) obj).resolveRotation((android.service.rotationresolver.IRotationResolverCallback) obj2, (android.service.rotationresolver.RotationResolutionRequest) obj3, (android.os.ICancellationSignal) obj4);
                }
            }, android.service.rotationresolver.RotationResolverService.this, iRotationResolverCallback, rotationResolutionRequest, createTransport));
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new android.service.rotationresolver.RotationResolverService.AnonymousClass1();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resolveRotation(android.service.rotationresolver.IRotationResolverCallback iRotationResolverCallback, android.service.rotationresolver.RotationResolutionRequest rotationResolutionRequest, android.os.ICancellationSignal iCancellationSignal) {
        if (this.mPendingCallback != null && ((this.mCancellationSignal == null || !this.mCancellationSignal.isCanceled()) && android.os.SystemClock.uptimeMillis() < this.mPendingCallback.mExpirationTime)) {
            reportFailures(iRotationResolverCallback, 2);
            return;
        }
        this.mPendingCallback = new android.service.rotationresolver.RotationResolverService.RotationResolverCallbackWrapper(iRotationResolverCallback, this, android.os.SystemClock.uptimeMillis() + rotationResolutionRequest.getTimeoutMillis());
        this.mCancellationSignal = android.os.CancellationSignal.fromTransport(iCancellationSignal);
        onResolveRotation(rotationResolutionRequest, this.mCancellationSignal, this.mPendingCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRotationResult(android.service.rotationresolver.IRotationResolverCallback iRotationResolverCallback, int i) {
        if (this.mPendingCallback != null && this.mPendingCallback.mCallback == iRotationResolverCallback) {
            this.mPendingCallback = null;
            try {
                iRotationResolverCallback.onSuccess(i);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendFailureResult(android.service.rotationresolver.IRotationResolverCallback iRotationResolverCallback, int i) {
        if (this.mPendingCallback != null && iRotationResolverCallback == this.mPendingCallback.mCallback) {
            reportFailures(iRotationResolverCallback, i);
            this.mPendingCallback = null;
        }
    }

    private void reportFailures(android.service.rotationresolver.IRotationResolverCallback iRotationResolverCallback, int i) {
        try {
            iRotationResolverCallback.onFailure(i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public static final class RotationResolverCallbackWrapper implements android.service.rotationresolver.RotationResolverService.RotationResolverCallback {
        private final android.service.rotationresolver.IRotationResolverCallback mCallback;
        private final long mExpirationTime;
        private final android.os.Handler mHandler;
        private final android.service.rotationresolver.RotationResolverService mService;

        private RotationResolverCallbackWrapper(android.service.rotationresolver.IRotationResolverCallback iRotationResolverCallback, android.service.rotationresolver.RotationResolverService rotationResolverService, long j) {
            this.mCallback = iRotationResolverCallback;
            this.mService = rotationResolverService;
            this.mHandler = rotationResolverService.mMainThreadHandler;
            this.mExpirationTime = j;
            java.util.Objects.requireNonNull(this.mHandler);
        }

        @Override // android.service.rotationresolver.RotationResolverService.RotationResolverCallback
        public void onSuccess(int i) {
            this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.rotationresolver.RotationResolverService$RotationResolverCallbackWrapper$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.rotationresolver.RotationResolverService) obj).sendRotationResult((android.service.rotationresolver.IRotationResolverCallback) obj2, ((java.lang.Integer) obj3).intValue());
                }
            }, this.mService, this.mCallback, java.lang.Integer.valueOf(i)));
        }

        @Override // android.service.rotationresolver.RotationResolverService.RotationResolverCallback
        public void onFailure(int i) {
            this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.rotationresolver.RotationResolverService$RotationResolverCallbackWrapper$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.rotationresolver.RotationResolverService) obj).sendFailureResult((android.service.rotationresolver.IRotationResolverCallback) obj2, ((java.lang.Integer) obj3).intValue());
                }
            }, this.mService, this.mCallback, java.lang.Integer.valueOf(i)));
        }
    }
}
