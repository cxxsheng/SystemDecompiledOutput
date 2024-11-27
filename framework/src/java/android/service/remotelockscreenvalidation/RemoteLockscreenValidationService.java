package android.service.remotelockscreenvalidation;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class RemoteLockscreenValidationService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.service.remotelockscreenvalidation.RemoteLockscreenValidationService";
    private static final java.lang.String TAG = android.service.remotelockscreenvalidation.RemoteLockscreenValidationService.class.getSimpleName();
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private final android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService mInterface = new android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService.Stub() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationService.1
        @Override // android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService
        public void validateLockscreenGuess(byte[] bArr, final android.service.remotelockscreenvalidation.IRemoteLockscreenValidationCallback iRemoteLockscreenValidationCallback) {
            android.service.remotelockscreenvalidation.RemoteLockscreenValidationService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.remotelockscreenvalidation.RemoteLockscreenValidationService) obj).onValidateLockscreenGuess((byte[]) obj2, (android.service.remotelockscreenvalidation.RemoteLockscreenValidationService.AnonymousClass1.C00091) obj3);
                }
            }, android.service.remotelockscreenvalidation.RemoteLockscreenValidationService.this, bArr, new android.os.OutcomeReceiver<android.app.RemoteLockscreenValidationResult, java.lang.Exception>() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationService.1.1
                @Override // android.os.OutcomeReceiver
                public void onResult(android.app.RemoteLockscreenValidationResult remoteLockscreenValidationResult) {
                    try {
                        iRemoteLockscreenValidationCallback.onSuccess(remoteLockscreenValidationResult);
                    } catch (android.os.RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                }

                @Override // android.os.OutcomeReceiver
                public void onError(java.lang.Exception exc) {
                    try {
                        iRemoteLockscreenValidationCallback.onFailure(exc.getMessage());
                    } catch (android.os.RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                }
            }));
        }
    };

    public abstract void onValidateLockscreenGuess(byte[] bArr, android.os.OutcomeReceiver<android.app.RemoteLockscreenValidationResult, java.lang.Exception> outcomeReceiver);

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            android.util.Log.w(TAG, "Wrong action");
            return null;
        }
        return this.mInterface.asBinder();
    }
}
