package android.net;

/* loaded from: classes.dex */
class NetworkFactoryLegacyImpl extends android.os.Handler implements android.net.NetworkFactoryShim {
    public static final int CMD_CANCEL_REQUEST = 2;
    public static final int CMD_REQUEST_NETWORK = 1;
    private static final int CMD_SET_FILTER = 4;
    private static final int CMD_SET_SCORE = 3;
    private static final boolean DBG = true;
    private static final boolean VDBG = false;
    android.net.NetworkCapabilities mCapabilityFilter;
    final android.content.Context mContext;
    private final java.util.Map<android.net.NetworkRequest, android.net.NetworkFactoryLegacyImpl.NetworkRequestInfo> mNetworkRequests;
    final android.net.NetworkFactory mParent;
    android.net.NetworkProvider mProvider;
    private int mScore;

    NetworkFactoryLegacyImpl(android.net.NetworkFactory networkFactory, android.os.Looper looper, android.content.Context context, android.net.NetworkCapabilities networkCapabilities) {
        super(looper);
        this.mNetworkRequests = new java.util.LinkedHashMap();
        this.mProvider = null;
        this.mParent = networkFactory;
        this.mContext = context;
        this.mCapabilityFilter = networkCapabilities;
    }

    public void register(java.lang.String str) {
        if (this.mProvider != null) {
            throw new java.lang.IllegalStateException("A NetworkFactory must only be registered once");
        }
        this.mParent.log("Registering NetworkFactory");
        this.mProvider = new android.net.NetworkProvider(this.mContext, getLooper(), str) { // from class: android.net.NetworkFactoryLegacyImpl.1
            public void onNetworkRequested(@android.annotation.NonNull android.net.NetworkRequest networkRequest, int i, int i2) {
                android.net.NetworkFactoryLegacyImpl.this.handleAddRequest(networkRequest, i, i2);
            }

            public void onNetworkRequestWithdrawn(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
                android.net.NetworkFactoryLegacyImpl.this.handleRemoveRequest(networkRequest);
            }
        };
        ((android.net.ConnectivityManager) this.mContext.getSystemService("connectivity")).registerNetworkProvider(this.mProvider);
    }

    @Override // android.net.NetworkFactoryShim
    public void terminate() {
        if (this.mProvider == null) {
            throw new java.lang.IllegalStateException("This NetworkFactory was never registered");
        }
        this.mParent.log("Unregistering NetworkFactory");
        ((android.net.ConnectivityManager) this.mContext.getSystemService("connectivity")).unregisterNetworkProvider(this.mProvider);
        removeCallbacksAndMessages(null);
    }

    @Override // android.os.Handler
    public void handleMessage(android.os.Message message) {
        switch (message.what) {
            case 1:
                handleAddRequest((android.net.NetworkRequest) message.obj, message.arg1, message.arg2);
                break;
            case 2:
                handleRemoveRequest((android.net.NetworkRequest) message.obj);
                break;
            case 3:
                handleSetScore(message.arg1);
                break;
            case 4:
                handleSetFilter((android.net.NetworkCapabilities) message.obj);
                break;
        }
    }

    private static class NetworkRequestInfo {
        public int providerId;
        public final android.net.NetworkRequest request;
        public boolean requested = false;
        public int score;

        NetworkRequestInfo(android.net.NetworkRequest networkRequest, int i, int i2) {
            this.request = networkRequest;
            this.score = i;
            this.providerId = i2;
        }

        public java.lang.String toString() {
            return "{" + this.request + ", score=" + this.score + ", requested=" + this.requested + "}";
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void handleAddRequest(android.net.NetworkRequest networkRequest, int i, int i2) {
        android.net.NetworkFactoryLegacyImpl.NetworkRequestInfo networkRequestInfo = this.mNetworkRequests.get(networkRequest);
        if (networkRequestInfo == null) {
            this.mParent.log("got request " + networkRequest + " with score " + i + " and providerId " + i2);
            networkRequestInfo = new android.net.NetworkFactoryLegacyImpl.NetworkRequestInfo(networkRequest, i, i2);
            this.mNetworkRequests.put(networkRequestInfo.request, networkRequestInfo);
        } else {
            networkRequestInfo.score = i;
            networkRequestInfo.providerId = i2;
        }
        evalRequest(networkRequestInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRemoveRequest(android.net.NetworkRequest networkRequest) {
        android.net.NetworkFactoryLegacyImpl.NetworkRequestInfo networkRequestInfo = this.mNetworkRequests.get(networkRequest);
        if (networkRequestInfo != null) {
            this.mNetworkRequests.remove(networkRequest);
            if (networkRequestInfo.requested) {
                this.mParent.releaseNetworkFor(networkRequestInfo.request);
            }
        }
    }

    private void handleSetScore(int i) {
        this.mScore = i;
        evalRequests();
    }

    private void handleSetFilter(android.net.NetworkCapabilities networkCapabilities) {
        this.mCapabilityFilter = networkCapabilities;
        evalRequests();
    }

    public boolean acceptRequest(android.net.NetworkRequest networkRequest) {
        return this.mParent.acceptRequest(networkRequest);
    }

    private void evalRequest(android.net.NetworkFactoryLegacyImpl.NetworkRequestInfo networkRequestInfo) {
        if (shouldNeedNetworkFor(networkRequestInfo)) {
            this.mParent.needNetworkFor(networkRequestInfo.request);
            networkRequestInfo.requested = true;
        } else if (shouldReleaseNetworkFor(networkRequestInfo)) {
            this.mParent.releaseNetworkFor(networkRequestInfo.request);
            networkRequestInfo.requested = false;
        }
    }

    private boolean shouldNeedNetworkFor(android.net.NetworkFactoryLegacyImpl.NetworkRequestInfo networkRequestInfo) {
        return !networkRequestInfo.requested && (networkRequestInfo.score < this.mScore || networkRequestInfo.providerId == this.mProvider.getProviderId()) && networkRequestInfo.request.canBeSatisfiedBy(this.mCapabilityFilter) && acceptRequest(networkRequestInfo.request);
    }

    private boolean shouldReleaseNetworkFor(android.net.NetworkFactoryLegacyImpl.NetworkRequestInfo networkRequestInfo) {
        return networkRequestInfo.requested && !((networkRequestInfo.score <= this.mScore || networkRequestInfo.providerId == this.mProvider.getProviderId()) && networkRequestInfo.request.canBeSatisfiedBy(this.mCapabilityFilter) && acceptRequest(networkRequestInfo.request));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void evalRequests() {
        java.util.Iterator<android.net.NetworkFactoryLegacyImpl.NetworkRequestInfo> it = this.mNetworkRequests.values().iterator();
        while (it.hasNext()) {
            evalRequest(it.next());
        }
    }

    public void reevaluateAllRequests() {
        post(new java.lang.Runnable() { // from class: android.net.NetworkFactoryLegacyImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.net.NetworkFactoryLegacyImpl.this.evalRequests();
            }
        });
    }

    @Override // android.net.NetworkFactoryShim
    public void releaseRequestAsUnfulfillableByAnyFactory(final android.net.NetworkRequest networkRequest) {
        post(new java.lang.Runnable() { // from class: android.net.NetworkFactoryLegacyImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.net.NetworkFactoryLegacyImpl.this.lambda$releaseRequestAsUnfulfillableByAnyFactory$0(networkRequest);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$releaseRequestAsUnfulfillableByAnyFactory$0(android.net.NetworkRequest networkRequest) {
        this.mParent.log("releaseRequestAsUnfulfillableByAnyFactory: " + networkRequest);
        android.net.NetworkProvider networkProvider = this.mProvider;
        if (networkProvider == null) {
            this.mParent.log("Ignoring attempt to release unregistered request as unfulfillable");
        } else {
            networkProvider.declareNetworkRequestUnfulfillable(networkRequest);
        }
    }

    public void setScoreFilter(int i) {
        sendMessage(obtainMessage(3, i, 0));
    }

    public void setScoreFilter(@android.annotation.NonNull android.net.NetworkScore networkScore) {
        setScoreFilter(networkScore.getLegacyInt());
    }

    public void setCapabilityFilter(android.net.NetworkCapabilities networkCapabilities) {
        sendMessage(obtainMessage(4, new android.net.NetworkCapabilities(networkCapabilities)));
    }

    public int getRequestCount() {
        return this.mNetworkRequests.size();
    }

    @Override // android.net.NetworkFactoryShim
    public int getSerialNumber() {
        return this.mProvider.getProviderId();
    }

    @Override // android.net.NetworkFactoryShim
    public android.net.NetworkProvider getProvider() {
        return this.mProvider;
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.println(toString());
        java.util.Iterator<android.net.NetworkFactoryLegacyImpl.NetworkRequestInfo> it = this.mNetworkRequests.values().iterator();
        while (it.hasNext()) {
            printWriter.println("  " + it.next());
        }
    }

    @Override // android.os.Handler
    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("providerId=");
        sb.append(this.mProvider != null ? java.lang.Integer.valueOf(this.mProvider.getProviderId()) : "null");
        sb.append(", ScoreFilter=");
        sb.append(this.mScore);
        sb.append(", Filter=");
        sb.append(this.mCapabilityFilter);
        sb.append(", requests=");
        sb.append(this.mNetworkRequests.size());
        return sb.toString();
    }
}
