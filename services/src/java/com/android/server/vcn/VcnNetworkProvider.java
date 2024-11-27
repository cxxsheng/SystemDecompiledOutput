package com.android.server.vcn;

/* loaded from: classes2.dex */
public class VcnNetworkProvider extends android.net.NetworkProvider {
    private static final java.lang.String TAG = com.android.server.vcn.VcnNetworkProvider.class.getSimpleName();
    private final android.content.Context mContext;
    private final com.android.server.vcn.VcnNetworkProvider.Dependencies mDeps;
    private final android.os.Handler mHandler;
    private final java.util.Set<com.android.server.vcn.VcnNetworkProvider.NetworkRequestListener> mListeners;
    private final java.util.Set<android.net.NetworkRequest> mRequests;

    interface NetworkRequestListener {
        void onNetworkRequested(@android.annotation.NonNull android.net.NetworkRequest networkRequest);
    }

    public VcnNetworkProvider(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Looper looper) {
        this(context, looper, new com.android.server.vcn.VcnNetworkProvider.Dependencies());
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public VcnNetworkProvider(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Looper looper, @android.annotation.NonNull com.android.server.vcn.VcnNetworkProvider.Dependencies dependencies) {
        super(context, looper, TAG);
        java.util.Objects.requireNonNull(context, "Missing context");
        java.util.Objects.requireNonNull(looper, "Missing looper");
        this.mListeners = new android.util.ArraySet();
        this.mRequests = new android.util.ArraySet();
        this.mContext = context;
        this.mHandler = new android.os.Handler(looper);
        java.util.Objects.requireNonNull(dependencies, "Missing dependencies");
        this.mDeps = dependencies;
    }

    public void register() {
        ((android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class)).registerNetworkProvider(this);
        this.mDeps.registerNetworkOffer(this, com.android.server.vcn.Vcn.getNetworkScore(), buildCapabilityFilter(), new android.os.HandlerExecutor(this.mHandler), new android.net.NetworkProvider.NetworkOfferCallback() { // from class: com.android.server.vcn.VcnNetworkProvider.1
            public void onNetworkNeeded(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
                com.android.server.vcn.VcnNetworkProvider.this.handleNetworkRequested(networkRequest);
            }

            public void onNetworkUnneeded(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
                com.android.server.vcn.VcnNetworkProvider.this.handleNetworkRequestWithdrawn(networkRequest);
            }
        });
    }

    private android.net.NetworkCapabilities buildCapabilityFilter() {
        android.net.NetworkCapabilities.Builder addCapability = new android.net.NetworkCapabilities.Builder().addTransportType(0).addCapability(14).addCapability(13).addCapability(15).addCapability(28);
        java.util.Iterator it = android.net.vcn.VcnGatewayConnectionConfig.ALLOWED_CAPABILITIES.iterator();
        while (it.hasNext()) {
            addCapability.addCapability(((java.lang.Integer) it.next()).intValue());
        }
        return addCapability.build();
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public void registerListener(@android.annotation.NonNull com.android.server.vcn.VcnNetworkProvider.NetworkRequestListener networkRequestListener) {
        this.mListeners.add(networkRequestListener);
        resendAllRequests(networkRequestListener);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public void unregisterListener(@android.annotation.NonNull com.android.server.vcn.VcnNetworkProvider.NetworkRequestListener networkRequestListener) {
        this.mListeners.remove(networkRequestListener);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public void resendAllRequests(@android.annotation.NonNull com.android.server.vcn.VcnNetworkProvider.NetworkRequestListener networkRequestListener) {
        java.util.Iterator<android.net.NetworkRequest> it = this.mRequests.iterator();
        while (it.hasNext()) {
            notifyListenerForEvent(networkRequestListener, it.next());
        }
    }

    private void notifyListenerForEvent(@android.annotation.NonNull com.android.server.vcn.VcnNetworkProvider.NetworkRequestListener networkRequestListener, @android.annotation.NonNull android.net.NetworkRequest networkRequest) {
        networkRequestListener.onNetworkRequested(networkRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNetworkRequested(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
        this.mRequests.add(networkRequest);
        java.util.Iterator<com.android.server.vcn.VcnNetworkProvider.NetworkRequestListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            notifyListenerForEvent(it.next(), networkRequest);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNetworkRequestWithdrawn(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
        this.mRequests.remove(networkRequest);
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("VcnNetworkProvider:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mListeners:");
        indentingPrintWriter.increaseIndent();
        java.util.Iterator<com.android.server.vcn.VcnNetworkProvider.NetworkRequestListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            indentingPrintWriter.println(it.next());
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.println("mRequests:");
        indentingPrintWriter.increaseIndent();
        java.util.Iterator<android.net.NetworkRequest> it2 = this.mRequests.iterator();
        while (it2.hasNext()) {
            indentingPrintWriter.println(it2.next());
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class Dependencies {
        public void registerNetworkOffer(@android.annotation.NonNull com.android.server.vcn.VcnNetworkProvider vcnNetworkProvider, @android.annotation.NonNull android.net.NetworkScore networkScore, @android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities, @android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull android.net.NetworkProvider.NetworkOfferCallback networkOfferCallback) {
            vcnNetworkProvider.registerNetworkOffer(networkScore, networkCapabilities, executor, networkOfferCallback);
        }
    }
}
