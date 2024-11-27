package com.android.server.credentials;

/* loaded from: classes.dex */
public class RemoteCredentialService extends com.android.internal.infra.ServiceConnector.Impl<android.service.credentials.ICredentialProviderService> {
    private static final java.lang.String TAG = "RemoteCredentialService";
    private static final long TIMEOUT_IDLE_SERVICE_CONNECTION_MILLIS = 5000;
    private static final long TIMEOUT_REQUEST_MILLIS = 3000;

    @android.annotation.Nullable
    private com.android.server.credentials.RemoteCredentialService.ProviderCallbacks mCallback;
    private final android.content.ComponentName mComponentName;
    private java.util.concurrent.atomic.AtomicBoolean mOngoingRequest;

    public interface ProviderCallbacks<T> {
        void onProviderCancellable(android.os.ICancellationSignal iCancellationSignal);

        void onProviderResponseFailure(int i, @android.annotation.Nullable java.lang.Exception exc);

        void onProviderResponseSuccess(@android.annotation.Nullable T t);

        void onProviderServiceDied(com.android.server.credentials.RemoteCredentialService remoteCredentialService);
    }

    public RemoteCredentialService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.ComponentName componentName, int i) {
        super(context, new android.content.Intent("android.service.credentials.CredentialProviderService").setComponent(componentName), 0, i, new java.util.function.Function() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.service.credentials.ICredentialProviderService.Stub.asInterface((android.os.IBinder) obj);
            }
        });
        this.mOngoingRequest = new java.util.concurrent.atomic.AtomicBoolean(false);
        this.mComponentName = componentName;
    }

    public void setCallback(com.android.server.credentials.RemoteCredentialService.ProviderCallbacks providerCallbacks) {
        this.mCallback = providerCallbacks;
    }

    protected long getAutoDisconnectTimeoutMs() {
        return TIMEOUT_IDLE_SERVICE_CONNECTION_MILLIS;
    }

    public void onBindingDied(android.content.ComponentName componentName) {
        super.onBindingDied(componentName);
        android.util.Slog.w(TAG, "binding died for: " + componentName);
    }

    public void binderDied() {
        super.binderDied();
        android.util.Slog.w(TAG, "binderDied");
        if (this.mCallback != null) {
            this.mOngoingRequest.set(false);
            this.mCallback.onProviderServiceDied(this);
        }
    }

    @android.annotation.NonNull
    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public void destroy() {
        unbind();
    }

    public void onBeginGetCredential(@android.annotation.NonNull final android.service.credentials.BeginGetCredentialRequest beginGetCredentialRequest) {
        if (this.mCallback == null) {
            android.util.Slog.w(TAG, "Callback is not set");
            return;
        }
        this.mOngoingRequest.set(true);
        final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
        final java.util.concurrent.atomic.AtomicReference atomicReference2 = new java.util.concurrent.atomic.AtomicReference();
        com.android.internal.infra.AndroidFuture orTimeout = postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda3
            public final java.lang.Object run(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture lambda$onBeginGetCredential$0;
                lambda$onBeginGetCredential$0 = com.android.server.credentials.RemoteCredentialService.this.lambda$onBeginGetCredential$0(beginGetCredentialRequest, atomicReference2, atomicReference, (android.service.credentials.ICredentialProviderService) obj);
                return lambda$onBeginGetCredential$0;
            }
        }).orTimeout(3000L, java.util.concurrent.TimeUnit.MILLISECONDS);
        atomicReference2.set(orTimeout);
        orTimeout.whenComplete(new java.util.function.BiConsumer() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.credentials.RemoteCredentialService.this.lambda$onBeginGetCredential$2(atomicReference, (android.service.credentials.BeginGetCredentialResponse) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.concurrent.CompletableFuture lambda$onBeginGetCredential$0(android.service.credentials.BeginGetCredentialRequest beginGetCredentialRequest, final java.util.concurrent.atomic.AtomicReference atomicReference, final java.util.concurrent.atomic.AtomicReference atomicReference2, android.service.credentials.ICredentialProviderService iCredentialProviderService) throws java.lang.Exception {
        final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            iCredentialProviderService.onBeginGetCredential(beginGetCredentialRequest, new android.service.credentials.IBeginGetCredentialCallback.Stub() { // from class: com.android.server.credentials.RemoteCredentialService.1
                public void onSuccess(android.service.credentials.BeginGetCredentialResponse beginGetCredentialResponse) {
                    completableFuture.complete(beginGetCredentialResponse);
                }

                public void onFailure(java.lang.String str, java.lang.CharSequence charSequence) {
                    completableFuture.completeExceptionally(new android.credentials.GetCredentialException(str, charSequence == null ? "" : java.lang.String.valueOf(charSequence)));
                }

                public void onCancellable(android.os.ICancellationSignal iCancellationSignal) {
                    java.util.concurrent.CompletableFuture completableFuture2 = (java.util.concurrent.CompletableFuture) atomicReference.get();
                    if (completableFuture2 != null && completableFuture2.isCancelled()) {
                        com.android.server.credentials.RemoteCredentialService.this.dispatchCancellationSignal(iCancellationSignal);
                        return;
                    }
                    atomicReference2.set(iCancellationSignal);
                    if (com.android.server.credentials.RemoteCredentialService.this.mCallback != null) {
                        com.android.server.credentials.RemoteCredentialService.this.mCallback.onProviderCancellable(iCancellationSignal);
                    }
                }
            });
            return completableFuture;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBeginGetCredential$2(final java.util.concurrent.atomic.AtomicReference atomicReference, final android.service.credentials.BeginGetCredentialResponse beginGetCredentialResponse, final java.lang.Throwable th) {
        android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.credentials.RemoteCredentialService.this.lambda$onBeginGetCredential$1(beginGetCredentialResponse, th, atomicReference);
            }
        });
    }

    public void onBeginCreateCredential(@android.annotation.NonNull final android.service.credentials.BeginCreateCredentialRequest beginCreateCredentialRequest) {
        if (this.mCallback == null) {
            android.util.Slog.w(TAG, "Callback is not set");
            return;
        }
        this.mOngoingRequest.set(true);
        final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
        final java.util.concurrent.atomic.AtomicReference atomicReference2 = new java.util.concurrent.atomic.AtomicReference();
        com.android.internal.infra.AndroidFuture orTimeout = postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda0
            public final java.lang.Object run(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture lambda$onBeginCreateCredential$3;
                lambda$onBeginCreateCredential$3 = com.android.server.credentials.RemoteCredentialService.this.lambda$onBeginCreateCredential$3(beginCreateCredentialRequest, atomicReference2, atomicReference, (android.service.credentials.ICredentialProviderService) obj);
                return lambda$onBeginCreateCredential$3;
            }
        }).orTimeout(3000L, java.util.concurrent.TimeUnit.MILLISECONDS);
        atomicReference2.set(orTimeout);
        orTimeout.whenComplete(new java.util.function.BiConsumer() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.credentials.RemoteCredentialService.this.lambda$onBeginCreateCredential$5(atomicReference, (android.service.credentials.BeginCreateCredentialResponse) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.concurrent.CompletableFuture lambda$onBeginCreateCredential$3(android.service.credentials.BeginCreateCredentialRequest beginCreateCredentialRequest, final java.util.concurrent.atomic.AtomicReference atomicReference, final java.util.concurrent.atomic.AtomicReference atomicReference2, android.service.credentials.ICredentialProviderService iCredentialProviderService) throws java.lang.Exception {
        final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            iCredentialProviderService.onBeginCreateCredential(beginCreateCredentialRequest, new android.service.credentials.IBeginCreateCredentialCallback.Stub() { // from class: com.android.server.credentials.RemoteCredentialService.2
                public void onSuccess(android.service.credentials.BeginCreateCredentialResponse beginCreateCredentialResponse) {
                    completableFuture.complete(beginCreateCredentialResponse);
                }

                public void onFailure(java.lang.String str, java.lang.CharSequence charSequence) {
                    completableFuture.completeExceptionally(new android.credentials.CreateCredentialException(str, charSequence == null ? "" : java.lang.String.valueOf(charSequence)));
                }

                public void onCancellable(android.os.ICancellationSignal iCancellationSignal) {
                    java.util.concurrent.CompletableFuture completableFuture2 = (java.util.concurrent.CompletableFuture) atomicReference.get();
                    if (completableFuture2 != null && completableFuture2.isCancelled()) {
                        com.android.server.credentials.RemoteCredentialService.this.dispatchCancellationSignal(iCancellationSignal);
                        return;
                    }
                    atomicReference2.set(iCancellationSignal);
                    if (com.android.server.credentials.RemoteCredentialService.this.mCallback != null) {
                        com.android.server.credentials.RemoteCredentialService.this.mCallback.onProviderCancellable(iCancellationSignal);
                    }
                }
            });
            return completableFuture;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBeginCreateCredential$5(final java.util.concurrent.atomic.AtomicReference atomicReference, final android.service.credentials.BeginCreateCredentialResponse beginCreateCredentialResponse, final java.lang.Throwable th) {
        android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.credentials.RemoteCredentialService.this.lambda$onBeginCreateCredential$4(beginCreateCredentialResponse, th, atomicReference);
            }
        });
    }

    public void onClearCredentialState(@android.annotation.NonNull final android.service.credentials.ClearCredentialStateRequest clearCredentialStateRequest) {
        if (this.mCallback == null) {
            android.util.Slog.w(TAG, "Callback is not set");
            return;
        }
        this.mOngoingRequest.set(true);
        final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
        final java.util.concurrent.atomic.AtomicReference atomicReference2 = new java.util.concurrent.atomic.AtomicReference();
        com.android.internal.infra.AndroidFuture orTimeout = postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda6
            public final java.lang.Object run(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture lambda$onClearCredentialState$6;
                lambda$onClearCredentialState$6 = com.android.server.credentials.RemoteCredentialService.this.lambda$onClearCredentialState$6(clearCredentialStateRequest, atomicReference2, atomicReference, (android.service.credentials.ICredentialProviderService) obj);
                return lambda$onClearCredentialState$6;
            }
        }).orTimeout(3000L, java.util.concurrent.TimeUnit.MILLISECONDS);
        atomicReference2.set(orTimeout);
        orTimeout.whenComplete(new java.util.function.BiConsumer() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.credentials.RemoteCredentialService.this.lambda$onClearCredentialState$8(atomicReference, (java.lang.Void) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.concurrent.CompletableFuture lambda$onClearCredentialState$6(android.service.credentials.ClearCredentialStateRequest clearCredentialStateRequest, final java.util.concurrent.atomic.AtomicReference atomicReference, final java.util.concurrent.atomic.AtomicReference atomicReference2, android.service.credentials.ICredentialProviderService iCredentialProviderService) throws java.lang.Exception {
        final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            iCredentialProviderService.onClearCredentialState(clearCredentialStateRequest, new android.service.credentials.IClearCredentialStateCallback.Stub() { // from class: com.android.server.credentials.RemoteCredentialService.3
                public void onSuccess() {
                    completableFuture.complete(null);
                }

                public void onFailure(java.lang.String str, java.lang.CharSequence charSequence) {
                    completableFuture.completeExceptionally(new android.credentials.ClearCredentialStateException(str, charSequence == null ? "" : java.lang.String.valueOf(charSequence)));
                }

                public void onCancellable(android.os.ICancellationSignal iCancellationSignal) {
                    java.util.concurrent.CompletableFuture completableFuture2 = (java.util.concurrent.CompletableFuture) atomicReference.get();
                    if (completableFuture2 != null && completableFuture2.isCancelled()) {
                        com.android.server.credentials.RemoteCredentialService.this.dispatchCancellationSignal(iCancellationSignal);
                        return;
                    }
                    atomicReference2.set(iCancellationSignal);
                    if (com.android.server.credentials.RemoteCredentialService.this.mCallback != null) {
                        com.android.server.credentials.RemoteCredentialService.this.mCallback.onProviderCancellable(iCancellationSignal);
                    }
                }
            });
            return completableFuture;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClearCredentialState$8(final java.util.concurrent.atomic.AtomicReference atomicReference, final java.lang.Void r4, final java.lang.Throwable th) {
        android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: com.android.server.credentials.RemoteCredentialService$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.credentials.RemoteCredentialService.this.lambda$onClearCredentialState$7(r4, th, atomicReference);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleExecutionResponse, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public <T> void lambda$onClearCredentialState$7(T t, java.lang.Throwable th, java.util.concurrent.atomic.AtomicReference<android.os.ICancellationSignal> atomicReference) {
        if (th == null) {
            if (this.mCallback != null) {
                this.mCallback.onProviderResponseSuccess(t);
                return;
            }
            return;
        }
        if (th instanceof java.util.concurrent.TimeoutException) {
            android.util.Slog.i(TAG, "Remote provider response timed tuo for: " + this.mComponentName);
            if (!this.mOngoingRequest.get()) {
                return;
            }
            dispatchCancellationSignal(atomicReference.get());
            if (this.mCallback != null) {
                this.mOngoingRequest.set(false);
                this.mCallback.onProviderResponseFailure(1, null);
                return;
            }
            return;
        }
        if (th instanceof java.util.concurrent.CancellationException) {
            android.util.Slog.i(TAG, "Cancellation exception for remote provider: " + this.mComponentName);
            if (!this.mOngoingRequest.get()) {
                return;
            }
            dispatchCancellationSignal(atomicReference.get());
            if (this.mCallback != null) {
                this.mOngoingRequest.set(false);
                this.mCallback.onProviderResponseFailure(2, null);
                return;
            }
            return;
        }
        if (th instanceof android.credentials.GetCredentialException) {
            if (this.mCallback != null) {
                this.mCallback.onProviderResponseFailure(3, (android.credentials.GetCredentialException) th);
            }
        } else if (th instanceof android.credentials.CreateCredentialException) {
            if (this.mCallback != null) {
                this.mCallback.onProviderResponseFailure(3, (android.credentials.CreateCredentialException) th);
            }
        } else if (this.mCallback != null) {
            this.mCallback.onProviderResponseFailure(0, (java.lang.Exception) th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchCancellationSignal(@android.annotation.Nullable android.os.ICancellationSignal iCancellationSignal) {
        if (iCancellationSignal == null) {
            android.util.Slog.e(TAG, "Error dispatching a cancellation - Signal is null");
            return;
        }
        try {
            iCancellationSignal.cancel();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error dispatching a cancellation", e);
        }
    }
}
