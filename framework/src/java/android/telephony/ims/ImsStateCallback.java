package android.telephony.ims;

/* loaded from: classes3.dex */
public abstract class ImsStateCallback {
    public static final int REASON_IMS_SERVICE_DISCONNECTED = 3;
    public static final int REASON_IMS_SERVICE_NOT_READY = 6;
    public static final int REASON_NO_IMS_SERVICE_CONFIGURED = 4;
    public static final int REASON_SUBSCRIPTION_INACTIVE = 5;
    public static final int REASON_UNKNOWN_PERMANENT_ERROR = 2;
    public static final int REASON_UNKNOWN_TEMPORARY_ERROR = 1;
    private android.telephony.ims.ImsStateCallback.IImsStateCallbackStub mCallback;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisconnectedReason {
    }

    public abstract void onAvailable();

    /* renamed from: onError, reason: merged with bridge method [inline-methods] */
    public abstract void lambda$binderDied$0();

    public abstract void onUnavailable(int i);

    public void init(java.util.concurrent.Executor executor) {
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("ImsStateCallback Executor must be non-null");
        }
        this.mCallback = new android.telephony.ims.ImsStateCallback.IImsStateCallbackStub(this, executor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class IImsStateCallbackStub extends com.android.internal.telephony.IImsStateCallback.Stub {
        private java.util.concurrent.Executor mExecutor;
        private java.lang.ref.WeakReference<android.telephony.ims.ImsStateCallback> mImsStateCallbackWeakRef;

        IImsStateCallbackStub(android.telephony.ims.ImsStateCallback imsStateCallback, java.util.concurrent.Executor executor) {
            this.mImsStateCallbackWeakRef = new java.lang.ref.WeakReference<>(imsStateCallback);
            this.mExecutor = executor;
        }

        java.util.concurrent.Executor getExecutor() {
            return this.mExecutor;
        }

        @Override // com.android.internal.telephony.IImsStateCallback
        public void onAvailable() {
            final android.telephony.ims.ImsStateCallback imsStateCallback = this.mImsStateCallbackWeakRef.get();
            if (imsStateCallback == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.ims.ImsStateCallback$IImsStateCallbackStub$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.ims.ImsStateCallback.IImsStateCallbackStub.this.lambda$onAvailable$1(imsStateCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAvailable$1(final android.telephony.ims.ImsStateCallback imsStateCallback) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsStateCallback$IImsStateCallbackStub$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsStateCallback.this.onAvailable();
                }
            });
        }

        @Override // com.android.internal.telephony.IImsStateCallback
        public void onUnavailable(final int i) {
            final android.telephony.ims.ImsStateCallback imsStateCallback = this.mImsStateCallbackWeakRef.get();
            if (imsStateCallback == null) {
                return;
            }
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.ims.ImsStateCallback$IImsStateCallbackStub$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.telephony.ims.ImsStateCallback.IImsStateCallbackStub.this.lambda$onUnavailable$3(imsStateCallback, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUnavailable$3(final android.telephony.ims.ImsStateCallback imsStateCallback, final int i) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsStateCallback$IImsStateCallbackStub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsStateCallback.this.onUnavailable(i);
                }
            });
        }
    }

    public final void binderDied() {
        if (this.mCallback != null) {
            this.mCallback.getExecutor().execute(new java.lang.Runnable() { // from class: android.telephony.ims.ImsStateCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.ImsStateCallback.this.lambda$binderDied$0();
                }
            });
        }
    }

    public android.telephony.ims.ImsStateCallback.IImsStateCallbackStub getCallbackBinder() {
        return this.mCallback;
    }
}
