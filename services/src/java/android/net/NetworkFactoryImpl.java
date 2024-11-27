package android.net;

/* loaded from: classes.dex */
class NetworkFactoryImpl extends android.net.NetworkFactoryLegacyImpl {
    private static final int CMD_CANCEL_REQUEST = 2;
    private static final int CMD_LISTEN_TO_ALL_REQUESTS = 6;
    private static final int CMD_OFFER_NETWORK = 5;
    private static final int CMD_REQUEST_NETWORK = 1;
    private static final int CMD_SET_FILTER = 4;
    private static final int CMD_SET_SCORE = 3;
    private static final boolean DBG = true;

    @android.annotation.NonNull
    private static final android.net.NetworkScore INVINCIBLE_SCORE = new android.net.NetworkScore.Builder().setLegacyInt(1000).build();
    private static final boolean VDBG = false;

    @android.annotation.NonNull
    private final java.util.concurrent.Executor mExecutor;
    private final java.util.Map<android.net.NetworkRequest, android.net.NetworkFactoryImpl.NetworkRequestInfo> mNetworkRequests;
    private final android.net.NetworkProvider.NetworkOfferCallback mRequestCallback;

    @android.annotation.NonNull
    private android.net.NetworkScore mScore;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(java.lang.Runnable runnable) {
        post(runnable);
    }

    NetworkFactoryImpl(android.net.NetworkFactory networkFactory, android.os.Looper looper, android.content.Context context, @android.annotation.Nullable android.net.NetworkCapabilities networkCapabilities) {
        super(networkFactory, looper, context, networkCapabilities == null ? android.net.NetworkCapabilities.Builder.withoutDefaultCapabilities().build() : networkCapabilities);
        this.mNetworkRequests = new java.util.LinkedHashMap();
        this.mScore = new android.net.NetworkScore.Builder().setLegacyInt(0).build();
        this.mRequestCallback = new android.net.NetworkProvider.NetworkOfferCallback() { // from class: android.net.NetworkFactoryImpl.1
            public void onNetworkNeeded(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
                android.net.NetworkFactoryImpl.this.handleAddRequest(networkRequest);
            }

            public void onNetworkUnneeded(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
                android.net.NetworkFactoryImpl.this.handleRemoveRequest(networkRequest);
            }
        };
        this.mExecutor = new java.util.concurrent.Executor() { // from class: android.net.NetworkFactoryImpl$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Executor
            public final void execute(java.lang.Runnable runnable) {
                android.net.NetworkFactoryImpl.this.lambda$new$0(runnable);
            }
        };
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    public void register(java.lang.String str) {
        register(str, false);
    }

    @Override // android.net.NetworkFactoryShim
    public void registerIgnoringScore(java.lang.String str) {
        register(str, true);
    }

    private void register(java.lang.String str, boolean z) {
        if (this.mProvider != null) {
            throw new java.lang.IllegalStateException("A NetworkFactory must only be registered once");
        }
        this.mParent.log("Registering NetworkFactory");
        this.mProvider = new android.net.NetworkProvider(this.mContext, getLooper(), str) { // from class: android.net.NetworkFactoryImpl.2
            public void onNetworkRequested(@android.annotation.NonNull android.net.NetworkRequest networkRequest, int i, int i2) {
                android.net.NetworkFactoryImpl.this.handleAddRequest(networkRequest);
            }

            public void onNetworkRequestWithdrawn(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
                android.net.NetworkFactoryImpl.this.handleRemoveRequest(networkRequest);
            }
        };
        ((android.net.ConnectivityManager) this.mContext.getSystemService("connectivity")).registerNetworkProvider(this.mProvider);
        if (z) {
            sendMessage(obtainMessage(6));
        } else {
            sendMessage(obtainMessage(5));
        }
    }

    private void handleOfferNetwork(@android.annotation.NonNull android.net.NetworkScore networkScore) {
        this.mProvider.registerNetworkOffer(networkScore, this.mCapabilityFilter, this.mExecutor, this.mRequestCallback);
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.os.Handler
    public void handleMessage(android.os.Message message) {
        switch (message.what) {
            case 1:
                handleAddRequest((android.net.NetworkRequest) message.obj);
                break;
            case 2:
                handleRemoveRequest((android.net.NetworkRequest) message.obj);
                break;
            case 3:
                handleSetScore((android.net.NetworkScore) message.obj);
                break;
            case 4:
                handleSetFilter((android.net.NetworkCapabilities) message.obj);
                break;
            case 5:
                handleOfferNetwork(this.mScore);
                break;
            case 6:
                handleOfferNetwork(INVINCIBLE_SCORE);
                break;
        }
    }

    private static class NetworkRequestInfo {

        @android.annotation.NonNull
        public final android.net.NetworkRequest request;
        public boolean requested = false;

        NetworkRequestInfo(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
            this.request = networkRequest;
        }

        public java.lang.String toString() {
            return "{" + this.request + ", requested=" + this.requested + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAddRequest(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
        android.net.NetworkFactoryImpl.NetworkRequestInfo networkRequestInfo = this.mNetworkRequests.get(networkRequest);
        if (networkRequestInfo == null) {
            this.mParent.log("got request " + networkRequest);
            networkRequestInfo = new android.net.NetworkFactoryImpl.NetworkRequestInfo(networkRequest);
            this.mNetworkRequests.put(networkRequestInfo.request, networkRequestInfo);
        }
        if (this.mParent.acceptRequest(networkRequest)) {
            networkRequestInfo.requested = true;
            this.mParent.needNetworkFor(networkRequest);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRemoveRequest(android.net.NetworkRequest networkRequest) {
        android.net.NetworkFactoryImpl.NetworkRequestInfo networkRequestInfo = this.mNetworkRequests.get(networkRequest);
        if (networkRequestInfo != null) {
            this.mNetworkRequests.remove(networkRequest);
            if (networkRequestInfo.requested) {
                this.mParent.releaseNetworkFor(networkRequestInfo.request);
            }
        }
    }

    private void handleSetScore(@android.annotation.NonNull android.net.NetworkScore networkScore) {
        if (this.mScore.equals(networkScore)) {
            return;
        }
        this.mScore = networkScore;
        this.mParent.reevaluateAllRequests();
    }

    private void handleSetFilter(@android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities) {
        if (networkCapabilities.equals(this.mCapabilityFilter)) {
            return;
        }
        this.mCapabilityFilter = networkCapabilities;
        this.mParent.reevaluateAllRequests();
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    public final void reevaluateAllRequests() {
        if (this.mProvider == null) {
            return;
        }
        this.mProvider.registerNetworkOffer(this.mScore, this.mCapabilityFilter, this.mExecutor, this.mRequestCallback);
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    @java.lang.Deprecated
    public void setScoreFilter(int i) {
        setScoreFilter(new android.net.NetworkScore.Builder().setLegacyInt(i).build());
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    public void setScoreFilter(@android.annotation.NonNull android.net.NetworkScore networkScore) {
        sendMessage(obtainMessage(3, networkScore));
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    public void setCapabilityFilter(android.net.NetworkCapabilities networkCapabilities) {
        sendMessage(obtainMessage(4, new android.net.NetworkCapabilities(networkCapabilities)));
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    public int getRequestCount() {
        return this.mNetworkRequests.size();
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.net.NetworkFactoryShim
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.println(toString());
        java.util.Iterator<android.net.NetworkFactoryImpl.NetworkRequestInfo> it = this.mNetworkRequests.values().iterator();
        while (it.hasNext()) {
            printWriter.println("  " + it.next());
        }
    }

    @Override // android.net.NetworkFactoryLegacyImpl, android.os.Handler
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
