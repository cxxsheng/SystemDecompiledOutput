package android.net;

/* loaded from: classes.dex */
public abstract class IpMemoryStoreClient {
    private static final java.lang.String TAG = android.net.IpMemoryStoreClient.class.getSimpleName();
    private final android.content.Context mContext;

    /* JADX INFO: Access modifiers changed from: private */
    @java.lang.FunctionalInterface
    interface ThrowingRunnable {
        void run() throws android.os.RemoteException;
    }

    protected abstract void runWhenServiceReady(java.util.function.Consumer<android.net.IIpMemoryStore> consumer) throws java.util.concurrent.ExecutionException;

    public IpMemoryStoreClient(@android.annotation.NonNull android.content.Context context) {
        if (context == null) {
            throw new java.lang.IllegalArgumentException("missing context");
        }
        this.mContext = context;
    }

    private void ignoringRemoteException(android.net.IpMemoryStoreClient.ThrowingRunnable throwingRunnable) {
        ignoringRemoteException("Failed to execute remote procedure call", throwingRunnable);
    }

    private void ignoringRemoteException(java.lang.String str, android.net.IpMemoryStoreClient.ThrowingRunnable throwingRunnable) {
        try {
            throwingRunnable.run();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, str, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$storeNetworkAttributes$1(final java.lang.String str, final android.net.ipmemorystore.NetworkAttributes networkAttributes, final android.net.ipmemorystore.OnStatusListener onStatusListener, final android.net.IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda12
            @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
            public final void run() {
                android.net.IpMemoryStoreClient.lambda$storeNetworkAttributes$0(android.net.IIpMemoryStore.this, str, networkAttributes, onStatusListener);
            }
        });
    }

    public void storeNetworkAttributes(@android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final android.net.ipmemorystore.NetworkAttributes networkAttributes, @android.annotation.Nullable final android.net.ipmemorystore.OnStatusListener onStatusListener) {
        try {
            runWhenServiceReady(new java.util.function.Consumer() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.net.IpMemoryStoreClient.this.lambda$storeNetworkAttributes$1(str, networkAttributes, onStatusListener, (android.net.IIpMemoryStore) obj);
                }
            });
        } catch (java.util.concurrent.ExecutionException e) {
            if (onStatusListener == null) {
                return;
            }
            ignoringRemoteException("Error storing network attributes", new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda1
                @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
                public final void run() {
                    android.net.IpMemoryStoreClient.lambda$storeNetworkAttributes$2(android.net.ipmemorystore.OnStatusListener.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$storeNetworkAttributes$0(android.net.IIpMemoryStore iIpMemoryStore, java.lang.String str, android.net.ipmemorystore.NetworkAttributes networkAttributes, android.net.ipmemorystore.OnStatusListener onStatusListener) throws android.os.RemoteException {
        iIpMemoryStore.storeNetworkAttributes(str, networkAttributes.toParcelable(), android.net.ipmemorystore.OnStatusListener.toAIDL(onStatusListener));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$storeNetworkAttributes$2(android.net.ipmemorystore.OnStatusListener onStatusListener) throws android.os.RemoteException {
        onStatusListener.onComplete(new android.net.ipmemorystore.Status(-5));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$storeBlob$4(final java.lang.String str, final java.lang.String str2, final java.lang.String str3, final android.net.ipmemorystore.Blob blob, final android.net.ipmemorystore.OnStatusListener onStatusListener, final android.net.IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda19
            @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
            public final void run() {
                android.net.IpMemoryStoreClient.lambda$storeBlob$3(android.net.IIpMemoryStore.this, str, str2, str3, blob, onStatusListener);
            }
        });
    }

    public void storeBlob(@android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final java.lang.String str2, @android.annotation.NonNull final java.lang.String str3, @android.annotation.NonNull final android.net.ipmemorystore.Blob blob, @android.annotation.Nullable final android.net.ipmemorystore.OnStatusListener onStatusListener) {
        try {
            runWhenServiceReady(new java.util.function.Consumer() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda17
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.net.IpMemoryStoreClient.this.lambda$storeBlob$4(str, str2, str3, blob, onStatusListener, (android.net.IIpMemoryStore) obj);
                }
            });
        } catch (java.util.concurrent.ExecutionException e) {
            if (onStatusListener == null) {
                return;
            }
            ignoringRemoteException("Error storing blob", new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda18
                @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
                public final void run() {
                    android.net.IpMemoryStoreClient.lambda$storeBlob$5(android.net.ipmemorystore.OnStatusListener.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$storeBlob$3(android.net.IIpMemoryStore iIpMemoryStore, java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.ipmemorystore.Blob blob, android.net.ipmemorystore.OnStatusListener onStatusListener) throws android.os.RemoteException {
        iIpMemoryStore.storeBlob(str, str2, str3, blob, android.net.ipmemorystore.OnStatusListener.toAIDL(onStatusListener));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$storeBlob$5(android.net.ipmemorystore.OnStatusListener onStatusListener) throws android.os.RemoteException {
        onStatusListener.onComplete(new android.net.ipmemorystore.Status(-5));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$findL2Key$7(final android.net.ipmemorystore.NetworkAttributes networkAttributes, final android.net.ipmemorystore.OnL2KeyResponseListener onL2KeyResponseListener, final android.net.IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda9
            @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
            public final void run() {
                android.net.IpMemoryStoreClient.lambda$findL2Key$6(android.net.IIpMemoryStore.this, networkAttributes, onL2KeyResponseListener);
            }
        });
    }

    public void findL2Key(@android.annotation.NonNull final android.net.ipmemorystore.NetworkAttributes networkAttributes, @android.annotation.NonNull final android.net.ipmemorystore.OnL2KeyResponseListener onL2KeyResponseListener) {
        try {
            runWhenServiceReady(new java.util.function.Consumer() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda20
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.net.IpMemoryStoreClient.this.lambda$findL2Key$7(networkAttributes, onL2KeyResponseListener, (android.net.IIpMemoryStore) obj);
                }
            });
        } catch (java.util.concurrent.ExecutionException e) {
            ignoringRemoteException("Error finding L2 Key", new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda21
                @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
                public final void run() {
                    android.net.IpMemoryStoreClient.lambda$findL2Key$8(android.net.ipmemorystore.OnL2KeyResponseListener.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$findL2Key$6(android.net.IIpMemoryStore iIpMemoryStore, android.net.ipmemorystore.NetworkAttributes networkAttributes, android.net.ipmemorystore.OnL2KeyResponseListener onL2KeyResponseListener) throws android.os.RemoteException {
        iIpMemoryStore.findL2Key(networkAttributes.toParcelable(), android.net.ipmemorystore.OnL2KeyResponseListener.toAIDL(onL2KeyResponseListener));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$findL2Key$8(android.net.ipmemorystore.OnL2KeyResponseListener onL2KeyResponseListener) throws android.os.RemoteException {
        onL2KeyResponseListener.onL2KeyResponse(new android.net.ipmemorystore.Status(-5), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$isSameNetwork$10(final java.lang.String str, final java.lang.String str2, final android.net.ipmemorystore.OnSameL3NetworkResponseListener onSameL3NetworkResponseListener, final android.net.IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda15
            @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
            public final void run() {
                android.net.IpMemoryStoreClient.lambda$isSameNetwork$9(android.net.IIpMemoryStore.this, str, str2, onSameL3NetworkResponseListener);
            }
        });
    }

    public void isSameNetwork(@android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final java.lang.String str2, @android.annotation.NonNull final android.net.ipmemorystore.OnSameL3NetworkResponseListener onSameL3NetworkResponseListener) {
        try {
            runWhenServiceReady(new java.util.function.Consumer() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.net.IpMemoryStoreClient.this.lambda$isSameNetwork$10(str, str2, onSameL3NetworkResponseListener, (android.net.IIpMemoryStore) obj);
                }
            });
        } catch (java.util.concurrent.ExecutionException e) {
            ignoringRemoteException("Error checking for network sameness", new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda5
                @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
                public final void run() {
                    android.net.IpMemoryStoreClient.lambda$isSameNetwork$11(android.net.ipmemorystore.OnSameL3NetworkResponseListener.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$isSameNetwork$9(android.net.IIpMemoryStore iIpMemoryStore, java.lang.String str, java.lang.String str2, android.net.ipmemorystore.OnSameL3NetworkResponseListener onSameL3NetworkResponseListener) throws android.os.RemoteException {
        iIpMemoryStore.isSameNetwork(str, str2, android.net.ipmemorystore.OnSameL3NetworkResponseListener.toAIDL(onSameL3NetworkResponseListener));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$isSameNetwork$11(android.net.ipmemorystore.OnSameL3NetworkResponseListener onSameL3NetworkResponseListener) throws android.os.RemoteException {
        onSameL3NetworkResponseListener.onSameL3NetworkResponse(new android.net.ipmemorystore.Status(-5), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$retrieveNetworkAttributes$13(final java.lang.String str, final android.net.ipmemorystore.OnNetworkAttributesRetrievedListener onNetworkAttributesRetrievedListener, final android.net.IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda6
            @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
            public final void run() {
                android.net.IpMemoryStoreClient.lambda$retrieveNetworkAttributes$12(android.net.IIpMemoryStore.this, str, onNetworkAttributesRetrievedListener);
            }
        });
    }

    public void retrieveNetworkAttributes(@android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final android.net.ipmemorystore.OnNetworkAttributesRetrievedListener onNetworkAttributesRetrievedListener) {
        try {
            runWhenServiceReady(new java.util.function.Consumer() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda24
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.net.IpMemoryStoreClient.this.lambda$retrieveNetworkAttributes$13(str, onNetworkAttributesRetrievedListener, (android.net.IIpMemoryStore) obj);
                }
            });
        } catch (java.util.concurrent.ExecutionException e) {
            ignoringRemoteException("Error retrieving network attributes", new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda25
                @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
                public final void run() {
                    android.net.IpMemoryStoreClient.lambda$retrieveNetworkAttributes$14(android.net.ipmemorystore.OnNetworkAttributesRetrievedListener.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$retrieveNetworkAttributes$12(android.net.IIpMemoryStore iIpMemoryStore, java.lang.String str, android.net.ipmemorystore.OnNetworkAttributesRetrievedListener onNetworkAttributesRetrievedListener) throws android.os.RemoteException {
        iIpMemoryStore.retrieveNetworkAttributes(str, android.net.ipmemorystore.OnNetworkAttributesRetrievedListener.toAIDL(onNetworkAttributesRetrievedListener));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$retrieveNetworkAttributes$14(android.net.ipmemorystore.OnNetworkAttributesRetrievedListener onNetworkAttributesRetrievedListener) throws android.os.RemoteException {
        onNetworkAttributesRetrievedListener.onNetworkAttributesRetrieved(new android.net.ipmemorystore.Status(-5), null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$retrieveBlob$16(final java.lang.String str, final java.lang.String str2, final java.lang.String str3, final android.net.ipmemorystore.OnBlobRetrievedListener onBlobRetrievedListener, final android.net.IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda13
            @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
            public final void run() {
                android.net.IpMemoryStoreClient.lambda$retrieveBlob$15(android.net.IIpMemoryStore.this, str, str2, str3, onBlobRetrievedListener);
            }
        });
    }

    public void retrieveBlob(@android.annotation.NonNull final java.lang.String str, @android.annotation.NonNull final java.lang.String str2, @android.annotation.NonNull final java.lang.String str3, @android.annotation.NonNull final android.net.ipmemorystore.OnBlobRetrievedListener onBlobRetrievedListener) {
        try {
            runWhenServiceReady(new java.util.function.Consumer() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.net.IpMemoryStoreClient.this.lambda$retrieveBlob$16(str, str2, str3, onBlobRetrievedListener, (android.net.IIpMemoryStore) obj);
                }
            });
        } catch (java.util.concurrent.ExecutionException e) {
            ignoringRemoteException("Error retrieving blob", new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda8
                @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
                public final void run() {
                    android.net.IpMemoryStoreClient.lambda$retrieveBlob$17(android.net.ipmemorystore.OnBlobRetrievedListener.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$retrieveBlob$15(android.net.IIpMemoryStore iIpMemoryStore, java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.ipmemorystore.OnBlobRetrievedListener onBlobRetrievedListener) throws android.os.RemoteException {
        iIpMemoryStore.retrieveBlob(str, str2, str3, android.net.ipmemorystore.OnBlobRetrievedListener.toAIDL(onBlobRetrievedListener));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$retrieveBlob$17(android.net.ipmemorystore.OnBlobRetrievedListener onBlobRetrievedListener) throws android.os.RemoteException {
        onBlobRetrievedListener.onBlobRetrieved(new android.net.ipmemorystore.Status(-5), null, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$delete$19(final java.lang.String str, final boolean z, final android.net.ipmemorystore.OnDeleteStatusListener onDeleteStatusListener, final android.net.IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda10
            @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
            public final void run() {
                android.net.IpMemoryStoreClient.lambda$delete$18(android.net.IIpMemoryStore.this, str, z, onDeleteStatusListener);
            }
        });
    }

    public void delete(@android.annotation.NonNull final java.lang.String str, final boolean z, @android.annotation.Nullable final android.net.ipmemorystore.OnDeleteStatusListener onDeleteStatusListener) {
        try {
            runWhenServiceReady(new java.util.function.Consumer() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.net.IpMemoryStoreClient.this.lambda$delete$19(str, z, onDeleteStatusListener, (android.net.IIpMemoryStore) obj);
                }
            });
        } catch (java.util.concurrent.ExecutionException e) {
            if (onDeleteStatusListener == null) {
                return;
            }
            ignoringRemoteException("Error deleting from the memory store", new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda3
                @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
                public final void run() {
                    android.net.IpMemoryStoreClient.lambda$delete$20(android.net.ipmemorystore.OnDeleteStatusListener.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$delete$18(android.net.IIpMemoryStore iIpMemoryStore, java.lang.String str, boolean z, android.net.ipmemorystore.OnDeleteStatusListener onDeleteStatusListener) throws android.os.RemoteException {
        iIpMemoryStore.delete(str, z, android.net.ipmemorystore.OnDeleteStatusListener.toAIDL(onDeleteStatusListener));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$delete$20(android.net.ipmemorystore.OnDeleteStatusListener onDeleteStatusListener) throws android.os.RemoteException {
        onDeleteStatusListener.onComplete(new android.net.ipmemorystore.Status(-5), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteCluster$22(final java.lang.String str, final boolean z, final android.net.ipmemorystore.OnDeleteStatusListener onDeleteStatusListener, final android.net.IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda16
            @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
            public final void run() {
                android.net.IpMemoryStoreClient.lambda$deleteCluster$21(android.net.IIpMemoryStore.this, str, z, onDeleteStatusListener);
            }
        });
    }

    public void deleteCluster(@android.annotation.NonNull final java.lang.String str, final boolean z, @android.annotation.Nullable final android.net.ipmemorystore.OnDeleteStatusListener onDeleteStatusListener) {
        try {
            runWhenServiceReady(new java.util.function.Consumer() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda22
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.net.IpMemoryStoreClient.this.lambda$deleteCluster$22(str, z, onDeleteStatusListener, (android.net.IIpMemoryStore) obj);
                }
            });
        } catch (java.util.concurrent.ExecutionException e) {
            if (onDeleteStatusListener == null) {
                return;
            }
            ignoringRemoteException("Error deleting from the memory store", new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda23
                @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
                public final void run() {
                    android.net.IpMemoryStoreClient.lambda$deleteCluster$23(android.net.ipmemorystore.OnDeleteStatusListener.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deleteCluster$21(android.net.IIpMemoryStore iIpMemoryStore, java.lang.String str, boolean z, android.net.ipmemorystore.OnDeleteStatusListener onDeleteStatusListener) throws android.os.RemoteException {
        iIpMemoryStore.deleteCluster(str, z, android.net.ipmemorystore.OnDeleteStatusListener.toAIDL(onDeleteStatusListener));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deleteCluster$23(android.net.ipmemorystore.OnDeleteStatusListener onDeleteStatusListener) throws android.os.RemoteException {
        onDeleteStatusListener.onComplete(new android.net.ipmemorystore.Status(-5), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$factoryReset$25(final android.net.IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(new android.net.IpMemoryStoreClient.ThrowingRunnable() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda14
            @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
            public final void run() {
                android.net.IIpMemoryStore.this.factoryReset();
            }
        });
    }

    public void factoryReset() {
        try {
            runWhenServiceReady(new java.util.function.Consumer() { // from class: android.net.IpMemoryStoreClient$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.net.IpMemoryStoreClient.this.lambda$factoryReset$25((android.net.IIpMemoryStore) obj);
                }
            });
        } catch (java.util.concurrent.ExecutionException e) {
            android.util.Log.e(TAG, "Error executing factory reset", e);
        }
    }
}
