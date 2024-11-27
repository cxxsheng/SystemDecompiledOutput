package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class SipDialogStateCallback {
    private android.telephony.ims.SipDialogStateCallback.CallbackBinder mCallback;

    public abstract void onActiveSipDialogsChanged(java.util.List<android.telephony.ims.SipDialogState> list);

    /* renamed from: onError, reason: merged with bridge method [inline-methods] */
    public abstract void lambda$binderDied$0();

    public void attachExecutor(java.util.concurrent.Executor executor) {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("SipDialogStateCallback Executor must be non-null");
        }
        this.mCallback = new android.telephony.ims.SipDialogStateCallback.CallbackBinder(executor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CallbackBinder extends com.android.internal.telephony.ISipDialogStateCallback.Stub {
        private java.util.concurrent.Executor mExecutor;
        private java.lang.ref.WeakReference<android.telephony.ims.SipDialogStateCallback> mSipDialogStateCallbackWeakRef;

        private CallbackBinder(android.telephony.ims.SipDialogStateCallback sipDialogStateCallback, java.util.concurrent.Executor executor) {
            this.mSipDialogStateCallbackWeakRef = new java.lang.ref.WeakReference<>(sipDialogStateCallback);
            this.mExecutor = executor;
        }

        java.util.concurrent.Executor getExecutor() {
            return this.mExecutor;
        }

        @Override // com.android.internal.telephony.ISipDialogStateCallback
        public void onActiveSipDialogsChanged(final java.util.List<android.telephony.ims.SipDialogState> list) {
            final android.telephony.ims.SipDialogStateCallback sipDialogStateCallback = this.mSipDialogStateCallbackWeakRef.get();
            if (sipDialogStateCallback == null || list == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.ims.SipDialogStateCallback$CallbackBinder$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.ims.SipDialogStateCallback.CallbackBinder.this.lambda$onActiveSipDialogsChanged$1(sipDialogStateCallback, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActiveSipDialogsChanged$1(final android.telephony.ims.SipDialogStateCallback sipDialogStateCallback, final java.util.List list) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.SipDialogStateCallback$CallbackBinder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.SipDialogStateCallback.this.onActiveSipDialogsChanged(list);
                }
            });
        }
    }

    public final void binderDied() {
        if (this.mCallback != null) {
            this.mCallback.getExecutor().execute(new java.lang.Runnable() { // from class: android.telephony.ims.SipDialogStateCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.SipDialogStateCallback.this.lambda$binderDied$0();
                }
            });
        }
    }

    public android.telephony.ims.SipDialogStateCallback.CallbackBinder getCallbackBinder() {
        return this.mCallback;
    }
}
