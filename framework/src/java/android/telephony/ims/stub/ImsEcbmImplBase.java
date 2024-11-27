package android.telephony.ims.stub;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class ImsEcbmImplBase {
    private static final java.lang.String TAG = "ImsEcbmImplBase";
    private com.android.ims.internal.IImsEcbmListener mListener;
    private final java.lang.Object mLock = new java.lang.Object();
    private java.util.concurrent.Executor mExecutor = new android.app.PendingIntent$$ExternalSyntheticLambda0();
    private final com.android.ims.internal.IImsEcbm mImsEcbm = new android.telephony.ims.stub.ImsEcbmImplBase.AnonymousClass1();

    /* renamed from: android.telephony.ims.stub.ImsEcbmImplBase$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.ims.internal.IImsEcbm.Stub {
        AnonymousClass1() {
        }

        @Override // com.android.ims.internal.IImsEcbm
        public void setListener(final com.android.ims.internal.IImsEcbmListener iImsEcbmListener) {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsEcbmImplBase$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsEcbmImplBase.AnonymousClass1.this.lambda$setListener$0(iImsEcbmListener);
                }
            }, "setListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setListener$0(com.android.ims.internal.IImsEcbmListener iImsEcbmListener) {
            if (android.telephony.ims.stub.ImsEcbmImplBase.this.mListener != null && !android.telephony.ims.stub.ImsEcbmImplBase.this.mListener.asBinder().isBinderAlive()) {
                android.util.Log.w(android.telephony.ims.stub.ImsEcbmImplBase.TAG, "setListener: discarding dead Binder");
                android.telephony.ims.stub.ImsEcbmImplBase.this.mListener = null;
            }
            if (android.telephony.ims.stub.ImsEcbmImplBase.this.mListener != null && iImsEcbmListener != null && java.util.Objects.equals(android.telephony.ims.stub.ImsEcbmImplBase.this.mListener.asBinder(), iImsEcbmListener.asBinder())) {
                return;
            }
            if (iImsEcbmListener == null) {
                android.telephony.ims.stub.ImsEcbmImplBase.this.mListener = null;
            } else if (iImsEcbmListener != null && android.telephony.ims.stub.ImsEcbmImplBase.this.mListener == null) {
                android.telephony.ims.stub.ImsEcbmImplBase.this.mListener = iImsEcbmListener;
            } else {
                android.util.Log.w(android.telephony.ims.stub.ImsEcbmImplBase.TAG, "setListener is being called when there is already an active listener");
                android.telephony.ims.stub.ImsEcbmImplBase.this.mListener = iImsEcbmListener;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$exitEmergencyCallbackMode$1() {
            android.telephony.ims.stub.ImsEcbmImplBase.this.exitEmergencyCallbackMode();
        }

        @Override // com.android.ims.internal.IImsEcbm
        public void exitEmergencyCallbackMode() {
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsEcbmImplBase$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsEcbmImplBase.AnonymousClass1.this.lambda$exitEmergencyCallbackMode$1();
                }
            }, "exitEmergencyCallbackMode");
        }

        private void executeMethodAsync(final java.lang.Runnable runnable, java.lang.String str) {
            try {
                java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsEcbmImplBase$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, android.telephony.ims.stub.ImsEcbmImplBase.this.mExecutor).join();
            } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
                android.util.Log.w(android.telephony.ims.stub.ImsEcbmImplBase.TAG, "ImsEcbmImplBase Binder - " + str + " exception: " + e.getMessage());
            }
        }
    }

    public com.android.ims.internal.IImsEcbm getImsEcbm() {
        return this.mImsEcbm;
    }

    public void exitEmergencyCallbackMode() {
        android.util.Log.d(TAG, "exitEmergencyCallbackMode() not implemented");
    }

    public final void enteredEcbm() {
        com.android.ims.internal.IImsEcbmListener iImsEcbmListener;
        android.util.Log.d(TAG, "Entered ECBM.");
        synchronized (this.mLock) {
            iImsEcbmListener = this.mListener;
        }
        if (iImsEcbmListener != null) {
            try {
                iImsEcbmListener.enteredECBM();
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public final void exitedEcbm() {
        com.android.ims.internal.IImsEcbmListener iImsEcbmListener;
        android.util.Log.d(TAG, "Exited ECBM.");
        synchronized (this.mLock) {
            iImsEcbmListener = this.mListener;
        }
        if (iImsEcbmListener != null) {
            try {
                iImsEcbmListener.exitedECBM();
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    public final void setDefaultExecutor(java.util.concurrent.Executor executor) {
        this.mExecutor = executor;
    }
}
