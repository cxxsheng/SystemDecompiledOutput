package com.android.server.security.rkp;

/* loaded from: classes2.dex */
final class RemoteProvisioningRegistration extends android.security.rkp.IRegistration.Stub {
    static final java.lang.String TAG = "RemoteProvisionSysSvc";
    private final java.util.concurrent.Executor mExecutor;
    private final android.security.rkp.service.RegistrationProxy mRegistration;
    private final java.util.concurrent.ConcurrentHashMap<android.os.IBinder, android.os.CancellationSignal> mGetKeyOperations = new java.util.concurrent.ConcurrentHashMap<>();
    private final java.util.Set<android.os.IBinder> mStoreUpgradedKeyOperations = java.util.concurrent.ConcurrentHashMap.newKeySet();

    interface CallbackRunner {
        void run() throws java.lang.Exception;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class GetKeyReceiver implements android.os.OutcomeReceiver<android.security.rkp.service.RemotelyProvisionedKey, java.lang.Exception> {
        android.security.rkp.IGetKeyCallback mCallback;

        GetKeyReceiver(android.security.rkp.IGetKeyCallback iGetKeyCallback) {
            this.mCallback = iGetKeyCallback;
        }

        @Override // android.os.OutcomeReceiver
        public void onResult(android.security.rkp.service.RemotelyProvisionedKey remotelyProvisionedKey) {
            com.android.server.security.rkp.RemoteProvisioningRegistration.this.mGetKeyOperations.remove(this.mCallback.asBinder());
            android.util.Log.i("RemoteProvisionSysSvc", "Successfully fetched key for client " + this.mCallback.asBinder().hashCode());
            final android.security.rkp.RemotelyProvisionedKey remotelyProvisionedKey2 = new android.security.rkp.RemotelyProvisionedKey();
            remotelyProvisionedKey2.keyBlob = remotelyProvisionedKey.getKeyBlob();
            remotelyProvisionedKey2.encodedCertChain = remotelyProvisionedKey.getEncodedCertChain();
            com.android.server.security.rkp.RemoteProvisioningRegistration.this.wrapCallback(new com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner() { // from class: com.android.server.security.rkp.RemoteProvisioningRegistration$GetKeyReceiver$$ExternalSyntheticLambda0
                @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
                public final void run() {
                    com.android.server.security.rkp.RemoteProvisioningRegistration.GetKeyReceiver.this.lambda$onResult$0(remotelyProvisionedKey2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResult$0(android.security.rkp.RemotelyProvisionedKey remotelyProvisionedKey) throws java.lang.Exception {
            this.mCallback.onSuccess(remotelyProvisionedKey);
        }

        @Override // android.os.OutcomeReceiver
        public void onError(final java.lang.Exception exc) {
            com.android.server.security.rkp.RemoteProvisioningRegistration.this.mGetKeyOperations.remove(this.mCallback.asBinder());
            if (exc instanceof android.os.OperationCanceledException) {
                android.util.Log.i("RemoteProvisionSysSvc", "Operation cancelled for client " + this.mCallback.asBinder().hashCode());
                com.android.server.security.rkp.RemoteProvisioningRegistration remoteProvisioningRegistration = com.android.server.security.rkp.RemoteProvisioningRegistration.this;
                final android.security.rkp.IGetKeyCallback iGetKeyCallback = this.mCallback;
                java.util.Objects.requireNonNull(iGetKeyCallback);
                remoteProvisioningRegistration.wrapCallback(new com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner() { // from class: com.android.server.security.rkp.RemoteProvisioningRegistration$GetKeyReceiver$$ExternalSyntheticLambda1
                    @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
                    public final void run() {
                        iGetKeyCallback.onCancel();
                    }
                });
                return;
            }
            if (exc instanceof android.security.rkp.service.RkpProxyException) {
                android.util.Log.e("RemoteProvisionSysSvc", "RKP error fetching key for client " + this.mCallback.asBinder().hashCode() + ": " + exc.getMessage());
                final android.security.rkp.service.RkpProxyException rkpProxyException = (android.security.rkp.service.RkpProxyException) exc;
                com.android.server.security.rkp.RemoteProvisioningRegistration.this.wrapCallback(new com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner() { // from class: com.android.server.security.rkp.RemoteProvisioningRegistration$GetKeyReceiver$$ExternalSyntheticLambda2
                    @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
                    public final void run() {
                        com.android.server.security.rkp.RemoteProvisioningRegistration.GetKeyReceiver.this.lambda$onError$1(rkpProxyException, exc);
                    }
                });
                return;
            }
            android.util.Log.e("RemoteProvisionSysSvc", "Unknown error fetching key for client " + this.mCallback.asBinder().hashCode() + ": " + exc.getMessage());
            com.android.server.security.rkp.RemoteProvisioningRegistration.this.wrapCallback(new com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner() { // from class: com.android.server.security.rkp.RemoteProvisioningRegistration$GetKeyReceiver$$ExternalSyntheticLambda3
                @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
                public final void run() {
                    com.android.server.security.rkp.RemoteProvisioningRegistration.GetKeyReceiver.this.lambda$onError$2(exc);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(android.security.rkp.service.RkpProxyException rkpProxyException, java.lang.Exception exc) throws java.lang.Exception {
            this.mCallback.onError(com.android.server.security.rkp.RemoteProvisioningRegistration.this.toGetKeyError(rkpProxyException), exc.getMessage());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(java.lang.Exception exc) throws java.lang.Exception {
            this.mCallback.onError((byte) 1, exc.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte toGetKeyError(android.security.rkp.service.RkpProxyException rkpProxyException) {
        switch (rkpProxyException.getError()) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                android.util.Log.e("RemoteProvisionSysSvc", "Unexpected error code in RkpProxyException", rkpProxyException);
                break;
        }
        return (byte) 1;
    }

    RemoteProvisioningRegistration(android.security.rkp.service.RegistrationProxy registrationProxy, java.util.concurrent.Executor executor) {
        this.mRegistration = registrationProxy;
        this.mExecutor = executor;
    }

    public void getKey(int i, final android.security.rkp.IGetKeyCallback iGetKeyCallback) {
        android.os.CancellationSignal cancellationSignal = new android.os.CancellationSignal();
        if (this.mGetKeyOperations.putIfAbsent(iGetKeyCallback.asBinder(), cancellationSignal) != null) {
            android.util.Log.e("RemoteProvisionSysSvc", "Client can only request one call at a time " + iGetKeyCallback.asBinder().hashCode());
            throw new java.lang.IllegalArgumentException("Callback is already associated with an existing operation: " + iGetKeyCallback.asBinder().hashCode());
        }
        try {
            android.util.Log.i("RemoteProvisionSysSvc", "Fetching key " + i + " for client " + iGetKeyCallback.asBinder().hashCode());
            this.mRegistration.getKeyAsync(i, cancellationSignal, this.mExecutor, new com.android.server.security.rkp.RemoteProvisioningRegistration.GetKeyReceiver(iGetKeyCallback));
        } catch (java.lang.Exception e) {
            android.util.Log.e("RemoteProvisionSysSvc", "getKeyAsync threw an exception for client " + iGetKeyCallback.asBinder().hashCode(), e);
            this.mGetKeyOperations.remove(iGetKeyCallback.asBinder());
            wrapCallback(new com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner() { // from class: com.android.server.security.rkp.RemoteProvisioningRegistration$$ExternalSyntheticLambda0
                @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
                public final void run() {
                    com.android.server.security.rkp.RemoteProvisioningRegistration.lambda$getKey$0(iGetKeyCallback, e);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getKey$0(android.security.rkp.IGetKeyCallback iGetKeyCallback, java.lang.Exception exc) throws java.lang.Exception {
        iGetKeyCallback.onError((byte) 1, exc.getMessage());
    }

    public void cancelGetKey(android.security.rkp.IGetKeyCallback iGetKeyCallback) {
        android.os.CancellationSignal remove = this.mGetKeyOperations.remove(iGetKeyCallback.asBinder());
        if (remove == null) {
            throw new java.lang.IllegalArgumentException("Invalid client in cancelGetKey: " + iGetKeyCallback.asBinder().hashCode());
        }
        android.util.Log.i("RemoteProvisionSysSvc", "Requesting cancellation for client " + iGetKeyCallback.asBinder().hashCode());
        remove.cancel();
    }

    public void storeUpgradedKeyAsync(byte[] bArr, byte[] bArr2, final android.security.rkp.IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback) {
        if (!this.mStoreUpgradedKeyOperations.add(iStoreUpgradedKeyCallback.asBinder())) {
            throw new java.lang.IllegalArgumentException("Callback is already associated with an existing operation: " + iStoreUpgradedKeyCallback.asBinder().hashCode());
        }
        try {
            this.mRegistration.storeUpgradedKeyAsync(bArr, bArr2, this.mExecutor, new com.android.server.security.rkp.RemoteProvisioningRegistration.AnonymousClass1(iStoreUpgradedKeyCallback));
        } catch (java.lang.Exception e) {
            android.util.Log.e("RemoteProvisionSysSvc", "storeUpgradedKeyAsync threw an exception for client " + iStoreUpgradedKeyCallback.asBinder().hashCode(), e);
            this.mStoreUpgradedKeyOperations.remove(iStoreUpgradedKeyCallback.asBinder());
            wrapCallback(new com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner() { // from class: com.android.server.security.rkp.RemoteProvisioningRegistration$$ExternalSyntheticLambda1
                @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
                public final void run() {
                    com.android.server.security.rkp.RemoteProvisioningRegistration.lambda$storeUpgradedKeyAsync$1(iStoreUpgradedKeyCallback, e);
                }
            });
        }
    }

    /* renamed from: com.android.server.security.rkp.RemoteProvisioningRegistration$1, reason: invalid class name */
    class AnonymousClass1 implements android.os.OutcomeReceiver<java.lang.Void, java.lang.Exception> {
        final /* synthetic */ android.security.rkp.IStoreUpgradedKeyCallback val$callback;

        AnonymousClass1(android.security.rkp.IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback) {
            this.val$callback = iStoreUpgradedKeyCallback;
        }

        @Override // android.os.OutcomeReceiver
        public void onResult(java.lang.Void r3) {
            com.android.server.security.rkp.RemoteProvisioningRegistration.this.mStoreUpgradedKeyOperations.remove(this.val$callback.asBinder());
            com.android.server.security.rkp.RemoteProvisioningRegistration remoteProvisioningRegistration = com.android.server.security.rkp.RemoteProvisioningRegistration.this;
            final android.security.rkp.IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback = this.val$callback;
            java.util.Objects.requireNonNull(iStoreUpgradedKeyCallback);
            remoteProvisioningRegistration.wrapCallback(new com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner() { // from class: com.android.server.security.rkp.RemoteProvisioningRegistration$1$$ExternalSyntheticLambda1
                @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
                public final void run() {
                    iStoreUpgradedKeyCallback.onSuccess();
                }
            });
        }

        @Override // android.os.OutcomeReceiver
        public void onError(final java.lang.Exception exc) {
            com.android.server.security.rkp.RemoteProvisioningRegistration.this.mStoreUpgradedKeyOperations.remove(this.val$callback.asBinder());
            com.android.server.security.rkp.RemoteProvisioningRegistration remoteProvisioningRegistration = com.android.server.security.rkp.RemoteProvisioningRegistration.this;
            final android.security.rkp.IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback = this.val$callback;
            remoteProvisioningRegistration.wrapCallback(new com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner() { // from class: com.android.server.security.rkp.RemoteProvisioningRegistration$1$$ExternalSyntheticLambda0
                @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
                public final void run() {
                    com.android.server.security.rkp.RemoteProvisioningRegistration.AnonymousClass1.lambda$onError$0(iStoreUpgradedKeyCallback, exc);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onError$0(android.security.rkp.IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback, java.lang.Exception exc) throws java.lang.Exception {
            iStoreUpgradedKeyCallback.onError(exc.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$storeUpgradedKeyAsync$1(android.security.rkp.IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback, java.lang.Exception exc) throws java.lang.Exception {
        iStoreUpgradedKeyCallback.onError(exc.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wrapCallback(com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner callbackRunner) {
        try {
            callbackRunner.run();
        } catch (java.lang.Exception e) {
            android.util.Log.e("RemoteProvisionSysSvc", "Error invoking callback on client binder", e);
        }
    }
}
