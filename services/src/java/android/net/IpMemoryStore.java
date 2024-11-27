package android.net;

/* loaded from: classes.dex */
public class IpMemoryStore extends android.net.IpMemoryStoreClient {
    private static final java.lang.String TAG = android.net.IpMemoryStore.class.getSimpleName();

    @android.annotation.NonNull
    private final java.util.concurrent.CompletableFuture<android.net.IIpMemoryStore> mService;

    @android.annotation.NonNull
    private final java.util.concurrent.atomic.AtomicReference<java.util.concurrent.CompletableFuture<android.net.IIpMemoryStore>> mTailNode;

    public IpMemoryStore(@android.annotation.NonNull android.content.Context context) {
        super(context);
        this.mService = new java.util.concurrent.CompletableFuture<>();
        this.mTailNode = new java.util.concurrent.atomic.AtomicReference<>(this.mService);
        getModuleNetworkStackClient(context).fetchIpMemoryStore(new android.net.IIpMemoryStoreCallbacks.Stub() { // from class: android.net.IpMemoryStore.1
            @Override // android.net.IIpMemoryStoreCallbacks
            public void onIpMemoryStoreFetched(@android.annotation.NonNull android.net.IIpMemoryStore iIpMemoryStore) {
                android.net.IpMemoryStore.this.mService.complete(iIpMemoryStore);
            }

            @Override // android.net.IIpMemoryStoreCallbacks
            public int getInterfaceVersion() {
                return 10;
            }

            @Override // android.net.IIpMemoryStoreCallbacks
            public java.lang.String getInterfaceHash() {
                return "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.concurrent.CompletableFuture lambda$runWhenServiceReady$1(final java.util.function.Consumer consumer, java.util.concurrent.CompletableFuture completableFuture) {
        return completableFuture.handle(new java.util.function.BiFunction() { // from class: android.net.IpMemoryStore$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                android.net.IIpMemoryStore lambda$runWhenServiceReady$0;
                lambda$runWhenServiceReady$0 = android.net.IpMemoryStore.lambda$runWhenServiceReady$0(consumer, (android.net.IIpMemoryStore) obj, (java.lang.Throwable) obj2);
                return lambda$runWhenServiceReady$0;
            }
        });
    }

    @Override // android.net.IpMemoryStoreClient
    protected void runWhenServiceReady(final java.util.function.Consumer<android.net.IIpMemoryStore> consumer) throws java.util.concurrent.ExecutionException {
        this.mTailNode.getAndUpdate(new java.util.function.UnaryOperator() { // from class: android.net.IpMemoryStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture lambda$runWhenServiceReady$1;
                lambda$runWhenServiceReady$1 = android.net.IpMemoryStore.lambda$runWhenServiceReady$1(consumer, (java.util.concurrent.CompletableFuture) obj);
                return lambda$runWhenServiceReady$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.net.IIpMemoryStore lambda$runWhenServiceReady$0(java.util.function.Consumer consumer, android.net.IIpMemoryStore iIpMemoryStore, java.lang.Throwable th) {
        if (th != null) {
            android.util.Log.wtf(TAG, "Error fetching IpMemoryStore", th);
            return iIpMemoryStore;
        }
        try {
            consumer.accept(iIpMemoryStore);
        } catch (java.lang.Exception e) {
            android.util.Log.wtf(TAG, "Exception occurred: " + e.getMessage());
        }
        return iIpMemoryStore;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.net.networkstack.ModuleNetworkStackClient getModuleNetworkStackClient(android.content.Context context) {
        return android.net.networkstack.ModuleNetworkStackClient.getInstance(context);
    }

    @android.annotation.NonNull
    public static android.net.IpMemoryStore getMemoryStore(android.content.Context context) {
        return new android.net.IpMemoryStore(context);
    }
}
