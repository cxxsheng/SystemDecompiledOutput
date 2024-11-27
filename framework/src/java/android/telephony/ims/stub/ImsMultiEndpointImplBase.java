package android.telephony.ims.stub;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class ImsMultiEndpointImplBase {
    private static final java.lang.String TAG = "MultiEndpointImplBase";
    private com.android.ims.internal.IImsExternalCallStateListener mListener;
    private final java.lang.Object mLock = new java.lang.Object();
    private java.util.concurrent.Executor mExecutor = new android.app.PendingIntent$$ExternalSyntheticLambda0();
    private final com.android.ims.internal.IImsMultiEndpoint mImsMultiEndpoint = new android.telephony.ims.stub.ImsMultiEndpointImplBase.AnonymousClass1();

    /* renamed from: android.telephony.ims.stub.ImsMultiEndpointImplBase$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.ims.internal.IImsMultiEndpoint.Stub {
        AnonymousClass1() {
        }

        @Override // com.android.ims.internal.IImsMultiEndpoint
        public void setListener(final com.android.ims.internal.IImsExternalCallStateListener iImsExternalCallStateListener) throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsMultiEndpointImplBase$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsMultiEndpointImplBase.AnonymousClass1.this.lambda$setListener$0(iImsExternalCallStateListener);
                }
            }, "setListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setListener$0(com.android.ims.internal.IImsExternalCallStateListener iImsExternalCallStateListener) {
            if (android.telephony.ims.stub.ImsMultiEndpointImplBase.this.mListener != null && !android.telephony.ims.stub.ImsMultiEndpointImplBase.this.mListener.asBinder().isBinderAlive()) {
                android.util.Log.w(android.telephony.ims.stub.ImsMultiEndpointImplBase.TAG, "setListener: discarding dead Binder");
                android.telephony.ims.stub.ImsMultiEndpointImplBase.this.mListener = null;
            }
            if (android.telephony.ims.stub.ImsMultiEndpointImplBase.this.mListener != null && iImsExternalCallStateListener != null && java.util.Objects.equals(android.telephony.ims.stub.ImsMultiEndpointImplBase.this.mListener.asBinder(), iImsExternalCallStateListener.asBinder())) {
                return;
            }
            if (iImsExternalCallStateListener == null) {
                android.telephony.ims.stub.ImsMultiEndpointImplBase.this.mListener = null;
            } else if (iImsExternalCallStateListener != null && android.telephony.ims.stub.ImsMultiEndpointImplBase.this.mListener == null) {
                android.telephony.ims.stub.ImsMultiEndpointImplBase.this.mListener = iImsExternalCallStateListener;
            } else {
                android.util.Log.w(android.telephony.ims.stub.ImsMultiEndpointImplBase.TAG, "setListener is being called when there is already an active listener");
                android.telephony.ims.stub.ImsMultiEndpointImplBase.this.mListener = iImsExternalCallStateListener;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestImsExternalCallStateInfo$1() {
            android.telephony.ims.stub.ImsMultiEndpointImplBase.this.requestImsExternalCallStateInfo();
        }

        @Override // com.android.ims.internal.IImsMultiEndpoint
        public void requestImsExternalCallStateInfo() throws android.os.RemoteException {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsMultiEndpointImplBase$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsMultiEndpointImplBase.AnonymousClass1.this.lambda$requestImsExternalCallStateInfo$1();
                }
            }, "requestImsExternalCallStateInfo");
        }

        private void executeMethodAsync(final java.lang.Runnable runnable, java.lang.String str) {
            try {
                java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsMultiEndpointImplBase$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, android.telephony.ims.stub.ImsMultiEndpointImplBase.this.mExecutor).join();
            } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
                android.util.Log.w(android.telephony.ims.stub.ImsMultiEndpointImplBase.TAG, "ImsMultiEndpointImplBase Binder - " + str + " exception: " + e.getMessage());
            }
        }
    }

    public com.android.ims.internal.IImsMultiEndpoint getIImsMultiEndpoint() {
        return this.mImsMultiEndpoint;
    }

    public final void onImsExternalCallStateUpdate(java.util.List<android.telephony.ims.ImsExternalCallState> list) {
        com.android.ims.internal.IImsExternalCallStateListener iImsExternalCallStateListener;
        android.util.Log.d(TAG, "ims external call state update triggered.");
        synchronized (this.mLock) {
            iImsExternalCallStateListener = this.mListener;
        }
        if (iImsExternalCallStateListener != null) {
            try {
                iImsExternalCallStateListener.onImsExternalCallStateUpdate(list);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public void requestImsExternalCallStateInfo() {
        android.util.Log.d(TAG, "requestImsExternalCallStateInfo() not implemented");
    }

    public final void setDefaultExecutor(java.util.concurrent.Executor executor) {
        this.mExecutor = executor;
    }
}
