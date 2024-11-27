package com.android.server.autofill;

/* loaded from: classes.dex */
final class RemoteFillService extends com.android.internal.infra.ServiceConnector.Impl<android.service.autofill.IAutoFillService> {
    private static final android.content.ComponentName CREDMAN_SERVICE_COMPONENT_NAME = new android.content.ComponentName("com.android.credentialmanager", "com.android.credentialmanager.autofill.CredentialAutofillService");
    private static final java.lang.String TAG = "RemoteFillService";
    private static final long TIMEOUT_IDLE_BIND_MILLIS = 5000;
    private static final long TIMEOUT_REMOTE_REQUEST_MILLIS = 5000;
    private final com.android.server.autofill.RemoteFillService.FillServiceCallbacks mCallbacks;
    private final android.content.ComponentName mComponentName;
    private java.util.concurrent.atomic.AtomicReference<android.service.autofill.IConvertCredentialCallback> mConvertCredentialCallback;
    private java.util.concurrent.atomic.AtomicReference<android.service.autofill.IFillCallback> mFillCallback;
    private final boolean mIsCredentialAutofillService;
    private final java.lang.Object mLock;
    private java.util.concurrent.CompletableFuture<android.service.autofill.FillResponse> mPendingFillRequest;
    private int mPendingFillRequestId;
    private java.util.concurrent.atomic.AtomicReference<android.service.autofill.ISaveCallback> mSaveCallback;

    public interface FillServiceCallbacks extends com.android.internal.infra.AbstractRemoteService.VultureCallback<com.android.server.autofill.RemoteFillService> {
        void onConvertCredentialRequestSuccess(@android.annotation.NonNull android.service.autofill.ConvertCredentialResponse convertCredentialResponse);

        void onFillRequestFailure(int i, @android.annotation.Nullable java.lang.CharSequence charSequence);

        void onFillRequestSuccess(int i, @android.annotation.Nullable android.service.autofill.FillResponse fillResponse, @android.annotation.NonNull java.lang.String str, int i2);

        void onFillRequestTimeout(int i);

        void onSaveRequestFailure(@android.annotation.Nullable java.lang.CharSequence charSequence, @android.annotation.NonNull java.lang.String str);

        void onSaveRequestSuccess(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable android.content.IntentSender intentSender);
    }

    public boolean isCredentialAutofillService() {
        return this.mIsCredentialAutofillService;
    }

    RemoteFillService(android.content.Context context, android.content.ComponentName componentName, int i, com.android.server.autofill.RemoteFillService.FillServiceCallbacks fillServiceCallbacks, boolean z) {
        super(context, new android.content.Intent("android.service.autofill.AutofillService").setComponent(componentName), (z ? 4194304 : 0) | 1048576, i, new java.util.function.Function() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.service.autofill.IAutoFillService.Stub.asInterface((android.os.IBinder) obj);
            }
        });
        this.mLock = new java.lang.Object();
        this.mPendingFillRequestId = Integer.MIN_VALUE;
        this.mCallbacks = fillServiceCallbacks;
        this.mComponentName = componentName;
        this.mIsCredentialAutofillService = this.mComponentName.equals(CREDMAN_SERVICE_COMPONENT_NAME);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onServiceConnectionStatusChanged(android.service.autofill.IAutoFillService iAutoFillService, boolean z) {
        try {
            iAutoFillService.onConnectedStateChanged(z);
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Exception calling onConnectedStateChanged(" + z + "): " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchCancellationSignal(@android.annotation.Nullable android.os.ICancellationSignal iCancellationSignal) {
        if (iCancellationSignal == null) {
            return;
        }
        try {
            iCancellationSignal.cancel();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error requesting a cancellation", e);
        }
    }

    protected long getAutoDisconnectTimeoutMs() {
        return 5000L;
    }

    public void addLast(com.android.internal.infra.ServiceConnector.Job<android.service.autofill.IAutoFillService, ?> job) {
        cancelPendingJobs();
        super.addLast(job);
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public int cancelCurrentRequest() {
        int i;
        synchronized (this.mLock) {
            try {
                if (this.mPendingFillRequest != null && this.mPendingFillRequest.cancel(false)) {
                    i = this.mPendingFillRequestId;
                } else {
                    i = Integer.MIN_VALUE;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    static class IFillCallbackDelegate extends android.service.autofill.IFillCallback.Stub {
        private java.lang.ref.WeakReference<android.service.autofill.IFillCallback> mCallbackWeakRef;

        IFillCallbackDelegate(android.service.autofill.IFillCallback iFillCallback) {
            this.mCallbackWeakRef = new java.lang.ref.WeakReference<>(iFillCallback);
        }

        public void onCancellable(android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException {
            android.service.autofill.IFillCallback iFillCallback = this.mCallbackWeakRef.get();
            if (iFillCallback != null) {
                iFillCallback.onCancellable(iCancellationSignal);
            }
        }

        public void onSuccess(android.service.autofill.FillResponse fillResponse) throws android.os.RemoteException {
            android.service.autofill.IFillCallback iFillCallback = this.mCallbackWeakRef.get();
            if (iFillCallback != null) {
                iFillCallback.onSuccess(fillResponse);
            }
        }

        public void onFailure(int i, java.lang.CharSequence charSequence) throws android.os.RemoteException {
            android.service.autofill.IFillCallback iFillCallback = this.mCallbackWeakRef.get();
            if (iFillCallback != null) {
                iFillCallback.onFailure(i, charSequence);
            }
        }
    }

    static class ISaveCallbackDelegate extends android.service.autofill.ISaveCallback.Stub {
        private java.lang.ref.WeakReference<android.service.autofill.ISaveCallback> mCallbackWeakRef;

        ISaveCallbackDelegate(android.service.autofill.ISaveCallback iSaveCallback) {
            this.mCallbackWeakRef = new java.lang.ref.WeakReference<>(iSaveCallback);
        }

        public void onSuccess(android.content.IntentSender intentSender) throws android.os.RemoteException {
            android.service.autofill.ISaveCallback iSaveCallback = this.mCallbackWeakRef.get();
            if (iSaveCallback != null) {
                iSaveCallback.onSuccess(intentSender);
            }
        }

        public void onFailure(java.lang.CharSequence charSequence) throws android.os.RemoteException {
            android.service.autofill.ISaveCallback iSaveCallback = this.mCallbackWeakRef.get();
            if (iSaveCallback != null) {
                iSaveCallback.onFailure(charSequence);
            }
        }
    }

    static class IConvertCredentialCallbackDelegate extends android.service.autofill.IConvertCredentialCallback.Stub {
        private java.lang.ref.WeakReference<android.service.autofill.IConvertCredentialCallback> mCallbackWeakRef;

        IConvertCredentialCallbackDelegate(android.service.autofill.IConvertCredentialCallback iConvertCredentialCallback) {
            this.mCallbackWeakRef = new java.lang.ref.WeakReference<>(iConvertCredentialCallback);
        }

        public void onSuccess(android.service.autofill.ConvertCredentialResponse convertCredentialResponse) throws android.os.RemoteException {
            android.service.autofill.IConvertCredentialCallback iConvertCredentialCallback = this.mCallbackWeakRef.get();
            if (iConvertCredentialCallback != null) {
                iConvertCredentialCallback.onSuccess(convertCredentialResponse);
            }
        }

        public void onFailure(java.lang.CharSequence charSequence) throws android.os.RemoteException {
            android.service.autofill.IConvertCredentialCallback iConvertCredentialCallback = this.mCallbackWeakRef.get();
            if (iConvertCredentialCallback != null) {
                iConvertCredentialCallback.onFailure(charSequence);
            }
        }
    }

    private android.service.autofill.IFillCallback maybeWrapWithWeakReference(android.service.autofill.IFillCallback iFillCallback) {
        if (android.service.autofill.Flags.remoteFillServiceUseWeakReference()) {
            this.mFillCallback = new java.util.concurrent.atomic.AtomicReference<>(iFillCallback);
            return new com.android.server.autofill.RemoteFillService.IFillCallbackDelegate(iFillCallback);
        }
        return iFillCallback;
    }

    private android.service.autofill.ISaveCallback maybeWrapWithWeakReference(android.service.autofill.ISaveCallback iSaveCallback) {
        if (android.service.autofill.Flags.remoteFillServiceUseWeakReference()) {
            this.mSaveCallback = new java.util.concurrent.atomic.AtomicReference<>(iSaveCallback);
            return new com.android.server.autofill.RemoteFillService.ISaveCallbackDelegate(iSaveCallback);
        }
        return iSaveCallback;
    }

    private android.service.autofill.IConvertCredentialCallback maybeWrapWithWeakReference(android.service.autofill.IConvertCredentialCallback iConvertCredentialCallback) {
        if (android.service.autofill.Flags.remoteFillServiceUseWeakReference()) {
            this.mConvertCredentialCallback = new java.util.concurrent.atomic.AtomicReference<>(iConvertCredentialCallback);
            return new com.android.server.autofill.RemoteFillService.IConvertCredentialCallbackDelegate(iConvertCredentialCallback);
        }
        return iConvertCredentialCallback;
    }

    public void onFillCredentialRequest(@android.annotation.NonNull final android.service.autofill.FillRequest fillRequest, final android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "onFillRequest:" + fillRequest);
        }
        final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
        final java.util.concurrent.atomic.AtomicReference atomicReference2 = new java.util.concurrent.atomic.AtomicReference();
        com.android.internal.infra.AndroidFuture orTimeout = postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda5
            public final java.lang.Object run(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture lambda$onFillCredentialRequest$0;
                lambda$onFillCredentialRequest$0 = com.android.server.autofill.RemoteFillService.this.lambda$onFillCredentialRequest$0(fillRequest, atomicReference2, atomicReference, iAutoFillManagerClient, (android.service.autofill.IAutoFillService) obj);
                return lambda$onFillCredentialRequest$0;
            }
        }).orTimeout(5000L, java.util.concurrent.TimeUnit.MILLISECONDS);
        atomicReference2.set(orTimeout);
        synchronized (this.mLock) {
            this.mPendingFillRequest = orTimeout;
            this.mPendingFillRequestId = fillRequest.getId();
        }
        orTimeout.whenComplete(new java.util.function.BiConsumer() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.autofill.RemoteFillService.this.lambda$onFillCredentialRequest$2(fillRequest, atomicReference, (android.service.autofill.FillResponse) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.concurrent.CompletableFuture lambda$onFillCredentialRequest$0(android.service.autofill.FillRequest fillRequest, final java.util.concurrent.atomic.AtomicReference atomicReference, final java.util.concurrent.atomic.AtomicReference atomicReference2, android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient, android.service.autofill.IAutoFillService iAutoFillService) throws java.lang.Exception {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "calling onFillRequest() for id=" + fillRequest.getId());
        }
        final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
        iAutoFillService.onFillCredentialRequest(fillRequest, maybeWrapWithWeakReference((android.service.autofill.IFillCallback) new android.service.autofill.IFillCallback.Stub() { // from class: com.android.server.autofill.RemoteFillService.1
            public void onCancellable(android.os.ICancellationSignal iCancellationSignal) {
                java.util.concurrent.CompletableFuture completableFuture2 = (java.util.concurrent.CompletableFuture) atomicReference.get();
                if (completableFuture2 != null && completableFuture2.isCancelled()) {
                    com.android.server.autofill.RemoteFillService.this.dispatchCancellationSignal(iCancellationSignal);
                } else {
                    atomicReference2.set(iCancellationSignal);
                }
            }

            public void onSuccess(android.service.autofill.FillResponse fillResponse) {
                completableFuture.complete(fillResponse);
            }

            public void onFailure(int i, java.lang.CharSequence charSequence) {
                completableFuture.completeExceptionally(new java.lang.RuntimeException(charSequence == null ? "" : java.lang.String.valueOf(charSequence)));
            }
        }), iAutoFillManagerClient);
        return completableFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFillCredentialRequest$2(final android.service.autofill.FillRequest fillRequest, final java.util.concurrent.atomic.AtomicReference atomicReference, final android.service.autofill.FillResponse fillResponse, final java.lang.Throwable th) {
        android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.RemoteFillService.this.lambda$onFillCredentialRequest$1(th, fillRequest, fillResponse, atomicReference);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFillCredentialRequest$1(java.lang.Throwable th, android.service.autofill.FillRequest fillRequest, android.service.autofill.FillResponse fillResponse, java.util.concurrent.atomic.AtomicReference atomicReference) {
        synchronized (this.mLock) {
            this.mPendingFillRequest = null;
            this.mPendingFillRequestId = Integer.MIN_VALUE;
        }
        if (this.mCallbacks == null) {
            android.util.Slog.w(TAG, "Error calling RemoteFillService - service already unbound");
            return;
        }
        if (th == null) {
            this.mCallbacks.onFillRequestSuccess(fillRequest.getId(), fillResponse, this.mComponentName.getPackageName(), fillRequest.getFlags());
            return;
        }
        android.util.Slog.e(TAG, "Error calling on fill request", th);
        if (th instanceof java.util.concurrent.TimeoutException) {
            dispatchCancellationSignal((android.os.ICancellationSignal) atomicReference.get());
            this.mCallbacks.onFillRequestTimeout(fillRequest.getId());
        } else if (th instanceof java.util.concurrent.CancellationException) {
            dispatchCancellationSignal((android.os.ICancellationSignal) atomicReference.get());
        } else {
            this.mCallbacks.onFillRequestFailure(fillRequest.getId(), th.getMessage());
        }
    }

    public void onFillRequest(@android.annotation.NonNull final android.service.autofill.FillRequest fillRequest) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "onFillRequest:" + fillRequest);
        }
        final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
        final java.util.concurrent.atomic.AtomicReference atomicReference2 = new java.util.concurrent.atomic.AtomicReference();
        com.android.internal.infra.AndroidFuture orTimeout = postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda0
            public final java.lang.Object run(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture lambda$onFillRequest$3;
                lambda$onFillRequest$3 = com.android.server.autofill.RemoteFillService.this.lambda$onFillRequest$3(fillRequest, atomicReference2, atomicReference, (android.service.autofill.IAutoFillService) obj);
                return lambda$onFillRequest$3;
            }
        }).orTimeout(5000L, java.util.concurrent.TimeUnit.MILLISECONDS);
        atomicReference2.set(orTimeout);
        synchronized (this.mLock) {
            this.mPendingFillRequest = orTimeout;
            this.mPendingFillRequestId = fillRequest.getId();
        }
        orTimeout.whenComplete(new java.util.function.BiConsumer() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.autofill.RemoteFillService.this.lambda$onFillRequest$5(fillRequest, atomicReference, (android.service.autofill.FillResponse) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.concurrent.CompletableFuture lambda$onFillRequest$3(android.service.autofill.FillRequest fillRequest, final java.util.concurrent.atomic.AtomicReference atomicReference, final java.util.concurrent.atomic.AtomicReference atomicReference2, android.service.autofill.IAutoFillService iAutoFillService) throws java.lang.Exception {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "calling onFillRequest() for id=" + fillRequest.getId());
        }
        final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
        iAutoFillService.onFillRequest(fillRequest, maybeWrapWithWeakReference((android.service.autofill.IFillCallback) new android.service.autofill.IFillCallback.Stub() { // from class: com.android.server.autofill.RemoteFillService.2
            public void onCancellable(android.os.ICancellationSignal iCancellationSignal) {
                java.util.concurrent.CompletableFuture completableFuture2 = (java.util.concurrent.CompletableFuture) atomicReference.get();
                if (completableFuture2 != null && completableFuture2.isCancelled()) {
                    com.android.server.autofill.RemoteFillService.this.dispatchCancellationSignal(iCancellationSignal);
                } else {
                    atomicReference2.set(iCancellationSignal);
                }
            }

            public void onSuccess(android.service.autofill.FillResponse fillResponse) {
                completableFuture.complete(fillResponse);
            }

            public void onFailure(int i, java.lang.CharSequence charSequence) {
                completableFuture.completeExceptionally(new java.lang.RuntimeException(charSequence == null ? "" : java.lang.String.valueOf(charSequence)));
            }
        }));
        return completableFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFillRequest$5(final android.service.autofill.FillRequest fillRequest, final java.util.concurrent.atomic.AtomicReference atomicReference, final android.service.autofill.FillResponse fillResponse, final java.lang.Throwable th) {
        android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.RemoteFillService.this.lambda$onFillRequest$4(th, fillRequest, fillResponse, atomicReference);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFillRequest$4(java.lang.Throwable th, android.service.autofill.FillRequest fillRequest, android.service.autofill.FillResponse fillResponse, java.util.concurrent.atomic.AtomicReference atomicReference) {
        synchronized (this.mLock) {
            this.mPendingFillRequest = null;
            this.mPendingFillRequestId = Integer.MIN_VALUE;
        }
        if (th == null) {
            this.mCallbacks.onFillRequestSuccess(fillRequest.getId(), fillResponse, this.mComponentName.getPackageName(), fillRequest.getFlags());
            return;
        }
        android.util.Slog.e(TAG, "Error calling on fill request", th);
        if (th instanceof java.util.concurrent.TimeoutException) {
            dispatchCancellationSignal((android.os.ICancellationSignal) atomicReference.get());
            this.mCallbacks.onFillRequestTimeout(fillRequest.getId());
        } else if (th instanceof java.util.concurrent.CancellationException) {
            dispatchCancellationSignal((android.os.ICancellationSignal) atomicReference.get());
        } else {
            this.mCallbacks.onFillRequestFailure(fillRequest.getId(), th.getMessage());
        }
    }

    public void onConvertCredentialRequest(@android.annotation.NonNull final android.service.autofill.ConvertCredentialRequest convertCredentialRequest) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "calling onConvertCredentialRequest()");
        }
        postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda11
            public final java.lang.Object run(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture lambda$onConvertCredentialRequest$6;
                lambda$onConvertCredentialRequest$6 = com.android.server.autofill.RemoteFillService.this.lambda$onConvertCredentialRequest$6(convertCredentialRequest, (android.service.autofill.IAutoFillService) obj);
                return lambda$onConvertCredentialRequest$6;
            }
        }).orTimeout(5000L, java.util.concurrent.TimeUnit.MILLISECONDS).whenComplete(new java.util.function.BiConsumer() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda12
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.autofill.RemoteFillService.this.lambda$onConvertCredentialRequest$8((android.service.autofill.ConvertCredentialResponse) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.concurrent.CompletableFuture lambda$onConvertCredentialRequest$6(android.service.autofill.ConvertCredentialRequest convertCredentialRequest, android.service.autofill.IAutoFillService iAutoFillService) throws java.lang.Exception {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "calling onConvertCredentialRequest()");
        }
        final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
        iAutoFillService.onConvertCredentialRequest(convertCredentialRequest, maybeWrapWithWeakReference((android.service.autofill.IConvertCredentialCallback) new android.service.autofill.IConvertCredentialCallback.Stub() { // from class: com.android.server.autofill.RemoteFillService.3
            public void onSuccess(android.service.autofill.ConvertCredentialResponse convertCredentialResponse) {
                completableFuture.complete(convertCredentialResponse);
            }

            public void onFailure(java.lang.CharSequence charSequence) {
                completableFuture.completeExceptionally(new java.lang.RuntimeException(charSequence == null ? "" : java.lang.String.valueOf(charSequence)));
            }
        }));
        return completableFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConvertCredentialRequest$8(final android.service.autofill.ConvertCredentialResponse convertCredentialResponse, final java.lang.Throwable th) {
        android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.RemoteFillService.this.lambda$onConvertCredentialRequest$7(th, convertCredentialResponse);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConvertCredentialRequest$7(java.lang.Throwable th, android.service.autofill.ConvertCredentialResponse convertCredentialResponse) {
        if (th == null) {
            this.mCallbacks.onConvertCredentialRequestSuccess(convertCredentialResponse);
        } else {
            android.util.Slog.e(TAG, "Error calling on convert credential request", th);
        }
    }

    public void onSaveRequest(@android.annotation.NonNull final android.service.autofill.SaveRequest saveRequest) {
        postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda3
            public final java.lang.Object run(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture lambda$onSaveRequest$9;
                lambda$onSaveRequest$9 = com.android.server.autofill.RemoteFillService.this.lambda$onSaveRequest$9(saveRequest, (android.service.autofill.IAutoFillService) obj);
                return lambda$onSaveRequest$9;
            }
        }).orTimeout(5000L, java.util.concurrent.TimeUnit.MILLISECONDS).whenComplete(new java.util.function.BiConsumer() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.autofill.RemoteFillService.this.lambda$onSaveRequest$11((android.content.IntentSender) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.concurrent.CompletableFuture lambda$onSaveRequest$9(android.service.autofill.SaveRequest saveRequest, android.service.autofill.IAutoFillService iAutoFillService) throws java.lang.Exception {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "calling onSaveRequest()");
        }
        final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
        iAutoFillService.onSaveRequest(saveRequest, maybeWrapWithWeakReference((android.service.autofill.ISaveCallback) new android.service.autofill.ISaveCallback.Stub() { // from class: com.android.server.autofill.RemoteFillService.4
            public void onSuccess(android.content.IntentSender intentSender) {
                completableFuture.complete(intentSender);
            }

            public void onFailure(java.lang.CharSequence charSequence) {
                completableFuture.completeExceptionally(new java.lang.RuntimeException(java.lang.String.valueOf(charSequence)));
            }
        }));
        return completableFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSaveRequest$11(final android.content.IntentSender intentSender, final java.lang.Throwable th) {
        android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.RemoteFillService.this.lambda$onSaveRequest$10(th, intentSender);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSaveRequest$10(java.lang.Throwable th, android.content.IntentSender intentSender) {
        if (th == null) {
            this.mCallbacks.onSaveRequestSuccess(this.mComponentName.getPackageName(), intentSender);
        } else {
            this.mCallbacks.onSaveRequestFailure(this.mComponentName.getPackageName(), th.getMessage());
        }
    }

    void onSavedPasswordCountRequest(final com.android.internal.os.IResultReceiver iResultReceiver) {
        run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.autofill.RemoteFillService$$ExternalSyntheticLambda8
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.autofill.IAutoFillService) obj).onSavedPasswordCountRequest(iResultReceiver);
            }
        });
    }

    public void destroy() {
        unbind();
    }
}
