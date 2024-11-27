package android.media.browse;

/* loaded from: classes2.dex */
public final class MediaBrowser {
    private static final int CONNECT_STATE_CONNECTED = 3;
    private static final int CONNECT_STATE_CONNECTING = 2;
    private static final int CONNECT_STATE_DISCONNECTED = 1;
    private static final int CONNECT_STATE_DISCONNECTING = 0;
    private static final int CONNECT_STATE_SUSPENDED = 4;
    private static final boolean DBG = false;
    public static final java.lang.String EXTRA_PAGE = "android.media.browse.extra.PAGE";
    public static final java.lang.String EXTRA_PAGE_SIZE = "android.media.browse.extra.PAGE_SIZE";
    private static final java.lang.String TAG = "MediaBrowser";
    private final android.media.browse.MediaBrowser.ConnectionCallback mCallback;
    private final android.content.Context mContext;
    private volatile android.os.Bundle mExtras;
    private volatile android.media.session.MediaSession.Token mMediaSessionToken;
    private final android.os.Bundle mRootHints;
    private volatile java.lang.String mRootId;
    private android.service.media.IMediaBrowserService mServiceBinder;
    private android.service.media.IMediaBrowserServiceCallbacks mServiceCallbacks;
    private final android.content.ComponentName mServiceComponent;
    private android.media.browse.MediaBrowser.MediaServiceConnection mServiceConnection;
    private final android.os.Handler mHandler = new android.os.Handler();
    private final android.util.ArrayMap<java.lang.String, android.media.browse.MediaBrowser.Subscription> mSubscriptions = new android.util.ArrayMap<>();
    private volatile int mState = 1;

    public MediaBrowser(android.content.Context context, android.content.ComponentName componentName, android.media.browse.MediaBrowser.ConnectionCallback connectionCallback, android.os.Bundle bundle) {
        if (context == null) {
            throw new java.lang.IllegalArgumentException("context must not be null");
        }
        if (componentName == null) {
            throw new java.lang.IllegalArgumentException("service component must not be null");
        }
        if (connectionCallback == null) {
            throw new java.lang.IllegalArgumentException("connection callback must not be null");
        }
        this.mContext = context;
        this.mServiceComponent = componentName;
        this.mCallback = connectionCallback;
        this.mRootHints = bundle == null ? null : new android.os.Bundle(bundle);
    }

    public void connect() {
        if (this.mState != 0 && this.mState != 1) {
            throw new java.lang.IllegalStateException("connect() called while neither disconnecting nor disconnected (state=" + getStateLabel(this.mState) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mState = 2;
        this.mHandler.post(new java.lang.Runnable() { // from class: android.media.browse.MediaBrowser.1
            @Override // java.lang.Runnable
            public void run() {
                boolean z;
                if (android.media.browse.MediaBrowser.this.mState == 0) {
                    return;
                }
                android.media.browse.MediaBrowser.this.mState = 2;
                if (android.media.browse.MediaBrowser.this.mServiceBinder != null) {
                    throw new java.lang.RuntimeException("mServiceBinder should be null. Instead it is " + android.media.browse.MediaBrowser.this.mServiceBinder);
                }
                if (android.media.browse.MediaBrowser.this.mServiceCallbacks != null) {
                    throw new java.lang.RuntimeException("mServiceCallbacks should be null. Instead it is " + android.media.browse.MediaBrowser.this.mServiceCallbacks);
                }
                android.content.Intent intent = new android.content.Intent(android.service.media.MediaBrowserService.SERVICE_INTERFACE);
                intent.setComponent(android.media.browse.MediaBrowser.this.mServiceComponent);
                android.media.browse.MediaBrowser.this.mServiceConnection = new android.media.browse.MediaBrowser.MediaServiceConnection();
                try {
                    z = android.media.browse.MediaBrowser.this.mContext.bindService(intent, android.media.browse.MediaBrowser.this.mServiceConnection, 4097);
                } catch (java.lang.Exception e) {
                    android.util.Log.e(android.media.browse.MediaBrowser.TAG, "Failed binding to service " + android.media.browse.MediaBrowser.this.mServiceComponent);
                    z = false;
                }
                if (!z) {
                    android.media.browse.MediaBrowser.this.forceCloseConnection();
                    android.media.browse.MediaBrowser.this.mCallback.onConnectionFailed();
                }
            }
        });
    }

    public void disconnect() {
        this.mState = 0;
        this.mHandler.post(new java.lang.Runnable() { // from class: android.media.browse.MediaBrowser.2
            @Override // java.lang.Runnable
            public void run() {
                if (android.media.browse.MediaBrowser.this.mServiceCallbacks != null) {
                    try {
                        android.media.browse.MediaBrowser.this.mServiceBinder.disconnect(android.media.browse.MediaBrowser.this.mServiceCallbacks);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.browse.MediaBrowser.TAG, "RemoteException during connect for " + android.media.browse.MediaBrowser.this.mServiceComponent);
                    }
                }
                int i = android.media.browse.MediaBrowser.this.mState;
                android.media.browse.MediaBrowser.this.forceCloseConnection();
                if (i != 0) {
                    android.media.browse.MediaBrowser.this.mState = i;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forceCloseConnection() {
        if (this.mServiceConnection != null) {
            try {
                this.mContext.unbindService(this.mServiceConnection);
            } catch (java.lang.IllegalArgumentException e) {
            }
        }
        this.mState = 1;
        this.mServiceConnection = null;
        this.mServiceBinder = null;
        this.mServiceCallbacks = null;
        this.mRootId = null;
        this.mMediaSessionToken = null;
    }

    public boolean isConnected() {
        return this.mState == 3;
    }

    public android.content.ComponentName getServiceComponent() {
        if (!isConnected()) {
            throw new java.lang.IllegalStateException("getServiceComponent() called while not connected (state=" + this.mState + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return this.mServiceComponent;
    }

    public java.lang.String getRoot() {
        if (!isConnected()) {
            throw new java.lang.IllegalStateException("getRoot() called while not connected (state=" + getStateLabel(this.mState) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return this.mRootId;
    }

    public android.os.Bundle getExtras() {
        if (!isConnected()) {
            throw new java.lang.IllegalStateException("getExtras() called while not connected (state=" + getStateLabel(this.mState) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return this.mExtras;
    }

    public android.media.session.MediaSession.Token getSessionToken() {
        if (!isConnected()) {
            throw new java.lang.IllegalStateException("getSessionToken() called while not connected (state=" + this.mState + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return this.mMediaSessionToken;
    }

    public void subscribe(java.lang.String str, android.media.browse.MediaBrowser.SubscriptionCallback subscriptionCallback) {
        subscribeInternal(str, null, subscriptionCallback);
    }

    public void subscribe(java.lang.String str, android.os.Bundle bundle, android.media.browse.MediaBrowser.SubscriptionCallback subscriptionCallback) {
        if (bundle == null) {
            throw new java.lang.IllegalArgumentException("options cannot be null");
        }
        subscribeInternal(str, new android.os.Bundle(bundle), subscriptionCallback);
    }

    public void unsubscribe(java.lang.String str) {
        unsubscribeInternal(str, null);
    }

    public void unsubscribe(java.lang.String str, android.media.browse.MediaBrowser.SubscriptionCallback subscriptionCallback) {
        if (subscriptionCallback == null) {
            throw new java.lang.IllegalArgumentException("callback cannot be null");
        }
        unsubscribeInternal(str, subscriptionCallback);
    }

    public void getItem(final java.lang.String str, final android.media.browse.MediaBrowser.ItemCallback itemCallback) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("mediaId cannot be empty.");
        }
        if (itemCallback == null) {
            throw new java.lang.IllegalArgumentException("cb cannot be null.");
        }
        if (this.mState != 3) {
            android.util.Log.i(TAG, "Not connected, unable to retrieve the MediaItem.");
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.browse.MediaBrowser.3
                @Override // java.lang.Runnable
                public void run() {
                    itemCallback.onError(str);
                }
            });
            return;
        }
        try {
            this.mServiceBinder.getMediaItem(str, new android.os.ResultReceiver(this.mHandler) { // from class: android.media.browse.MediaBrowser.4
                @Override // android.os.ResultReceiver
                protected void onReceiveResult(int i, android.os.Bundle bundle) {
                    if (!android.media.browse.MediaBrowser.this.isConnected()) {
                        return;
                    }
                    if (i != 0 || bundle == null || !bundle.containsKey(android.service.media.MediaBrowserService.KEY_MEDIA_ITEM)) {
                        itemCallback.onError(str);
                        return;
                    }
                    android.os.Parcelable parcelable = bundle.getParcelable(android.service.media.MediaBrowserService.KEY_MEDIA_ITEM);
                    if (parcelable != null && !(parcelable instanceof android.media.browse.MediaBrowser.MediaItem)) {
                        itemCallback.onError(str);
                    } else {
                        itemCallback.onItemLoaded((android.media.browse.MediaBrowser.MediaItem) parcelable);
                    }
                }
            }, this.mServiceCallbacks);
        } catch (android.os.RemoteException e) {
            android.util.Log.i(TAG, "Remote error getting media item.");
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.browse.MediaBrowser.5
                @Override // java.lang.Runnable
                public void run() {
                    itemCallback.onError(str);
                }
            });
        }
    }

    private void subscribeInternal(java.lang.String str, android.os.Bundle bundle, android.media.browse.MediaBrowser.SubscriptionCallback subscriptionCallback) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("parentId cannot be empty.");
        }
        if (subscriptionCallback == null) {
            throw new java.lang.IllegalArgumentException("callback cannot be null");
        }
        android.media.browse.MediaBrowser.Subscription subscription = this.mSubscriptions.get(str);
        if (subscription == null) {
            subscription = new android.media.browse.MediaBrowser.Subscription();
            this.mSubscriptions.put(str, subscription);
        }
        subscription.putCallback(this.mContext, bundle, subscriptionCallback);
        if (isConnected()) {
            if (bundle == null) {
                try {
                    this.mServiceBinder.addSubscriptionDeprecated(str, this.mServiceCallbacks);
                } catch (android.os.RemoteException e) {
                    android.util.Log.d(TAG, "addSubscription failed with RemoteException parentId=" + str);
                    return;
                }
            }
            this.mServiceBinder.addSubscription(str, subscriptionCallback.mToken, bundle, this.mServiceCallbacks);
        }
    }

    private void unsubscribeInternal(java.lang.String str, android.media.browse.MediaBrowser.SubscriptionCallback subscriptionCallback) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("parentId cannot be empty.");
        }
        android.media.browse.MediaBrowser.Subscription subscription = this.mSubscriptions.get(str);
        if (subscription == null) {
            return;
        }
        try {
            if (subscriptionCallback == null) {
                if (isConnected()) {
                    this.mServiceBinder.removeSubscriptionDeprecated(str, this.mServiceCallbacks);
                    this.mServiceBinder.removeSubscription(str, null, this.mServiceCallbacks);
                }
            } else {
                java.util.List<android.media.browse.MediaBrowser.SubscriptionCallback> callbacks = subscription.getCallbacks();
                java.util.List<android.os.Bundle> optionsList = subscription.getOptionsList();
                for (int size = callbacks.size() - 1; size >= 0; size--) {
                    if (callbacks.get(size) == subscriptionCallback) {
                        if (isConnected()) {
                            this.mServiceBinder.removeSubscription(str, subscriptionCallback.mToken, this.mServiceCallbacks);
                        }
                        callbacks.remove(size);
                        optionsList.remove(size);
                    }
                }
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "removeSubscription failed with RemoteException parentId=" + str);
        }
        if (subscription.isEmpty() || subscriptionCallback == null) {
            this.mSubscriptions.remove(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getStateLabel(int i) {
        switch (i) {
            case 0:
                return "CONNECT_STATE_DISCONNECTING";
            case 1:
                return "CONNECT_STATE_DISCONNECTED";
            case 2:
                return "CONNECT_STATE_CONNECTING";
            case 3:
                return "CONNECT_STATE_CONNECTED";
            case 4:
                return "CONNECT_STATE_SUSPENDED";
            default:
                return "UNKNOWN/" + i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onServiceConnected(final android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, final java.lang.String str, final android.media.session.MediaSession.Token token, final android.os.Bundle bundle) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.media.browse.MediaBrowser.6
            @Override // java.lang.Runnable
            public void run() {
                if (!android.media.browse.MediaBrowser.this.isCurrent(iMediaBrowserServiceCallbacks, "onConnect")) {
                    return;
                }
                if (android.media.browse.MediaBrowser.this.mState != 2) {
                    android.util.Log.w(android.media.browse.MediaBrowser.TAG, "onConnect from service while mState=" + android.media.browse.MediaBrowser.getStateLabel(android.media.browse.MediaBrowser.this.mState) + "... ignoring");
                    return;
                }
                android.media.browse.MediaBrowser.this.mRootId = str;
                android.media.browse.MediaBrowser.this.mMediaSessionToken = token;
                android.media.browse.MediaBrowser.this.mExtras = bundle;
                android.media.browse.MediaBrowser.this.mState = 3;
                android.media.browse.MediaBrowser.this.mCallback.onConnected();
                for (java.util.Map.Entry entry : android.media.browse.MediaBrowser.this.mSubscriptions.entrySet()) {
                    java.lang.String str2 = (java.lang.String) entry.getKey();
                    android.media.browse.MediaBrowser.Subscription subscription = (android.media.browse.MediaBrowser.Subscription) entry.getValue();
                    java.util.List<android.media.browse.MediaBrowser.SubscriptionCallback> callbacks = subscription.getCallbacks();
                    java.util.List<android.os.Bundle> optionsList = subscription.getOptionsList();
                    for (int i = 0; i < callbacks.size(); i++) {
                        try {
                            android.media.browse.MediaBrowser.this.mServiceBinder.addSubscription(str2, callbacks.get(i).mToken, optionsList.get(i), android.media.browse.MediaBrowser.this.mServiceCallbacks);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.d(android.media.browse.MediaBrowser.TAG, "addSubscription failed with RemoteException parentId=" + str2);
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectionFailed(final android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.media.browse.MediaBrowser.7
            @Override // java.lang.Runnable
            public void run() {
                android.util.Log.e(android.media.browse.MediaBrowser.TAG, "onConnectFailed for " + android.media.browse.MediaBrowser.this.mServiceComponent);
                if (!android.media.browse.MediaBrowser.this.isCurrent(iMediaBrowserServiceCallbacks, "onConnectFailed")) {
                    return;
                }
                if (android.media.browse.MediaBrowser.this.mState != 2) {
                    android.util.Log.w(android.media.browse.MediaBrowser.TAG, "onConnect from service while mState=" + android.media.browse.MediaBrowser.getStateLabel(android.media.browse.MediaBrowser.this.mState) + "... ignoring");
                } else {
                    android.media.browse.MediaBrowser.this.forceCloseConnection();
                    android.media.browse.MediaBrowser.this.mCallback.onConnectionFailed();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLoadChildren(final android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, final java.lang.String str, final android.content.pm.ParceledListSlice parceledListSlice, final android.os.Bundle bundle) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.media.browse.MediaBrowser.8
            @Override // java.lang.Runnable
            public void run() {
                android.media.browse.MediaBrowser.Subscription subscription;
                android.media.browse.MediaBrowser.SubscriptionCallback callback;
                if (android.media.browse.MediaBrowser.this.isCurrent(iMediaBrowserServiceCallbacks, "onLoadChildren") && (subscription = (android.media.browse.MediaBrowser.Subscription) android.media.browse.MediaBrowser.this.mSubscriptions.get(str)) != null && (callback = subscription.getCallback(android.media.browse.MediaBrowser.this.mContext, bundle)) != null) {
                    java.util.List list = parceledListSlice == null ? null : parceledListSlice.getList();
                    if (bundle == null) {
                        if (list == null) {
                            callback.onError(str);
                            return;
                        } else {
                            callback.onChildrenLoaded(str, list);
                            return;
                        }
                    }
                    if (list == null) {
                        callback.onError(str, bundle);
                    } else {
                        callback.onChildrenLoaded(str, list, bundle);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDisconnectRequested(final android.media.browse.MediaBrowser.ServiceCallbacks serviceCallbacks) {
        this.mHandler.post(new java.lang.Runnable() { // from class: android.media.browse.MediaBrowser$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.media.browse.MediaBrowser.this.lambda$onDisconnectRequested$0(serviceCallbacks);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisconnectRequested$0(android.media.browse.MediaBrowser.ServiceCallbacks serviceCallbacks) {
        android.util.Log.i(TAG, "onDisconnectRequest for " + this.mServiceComponent);
        if (!isCurrent(serviceCallbacks, "onDisconnectRequest")) {
            return;
        }
        forceCloseConnection();
        this.mCallback.onDisconnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCurrent(android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, java.lang.String str) {
        if (this.mServiceCallbacks == iMediaBrowserServiceCallbacks && this.mState != 0 && this.mState != 1) {
            return true;
        }
        if (this.mState != 0 && this.mState != 1) {
            android.util.Log.i(TAG, str + " for " + this.mServiceComponent + " with mServiceConnection=" + this.mServiceCallbacks + " this=" + this);
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.media.browse.MediaBrowser.ServiceCallbacks getNewServiceCallbacks() {
        return new android.media.browse.MediaBrowser.ServiceCallbacks(this);
    }

    void dump() {
        android.util.Log.d(TAG, "MediaBrowser...");
        android.util.Log.d(TAG, "  mServiceComponent=" + this.mServiceComponent);
        android.util.Log.d(TAG, "  mCallback=" + this.mCallback);
        android.util.Log.d(TAG, "  mRootHints=" + this.mRootHints);
        android.util.Log.d(TAG, "  mState=" + getStateLabel(this.mState));
        android.util.Log.d(TAG, "  mServiceConnection=" + this.mServiceConnection);
        android.util.Log.d(TAG, "  mServiceBinder=" + this.mServiceBinder);
        android.util.Log.d(TAG, "  mServiceCallbacks=" + this.mServiceCallbacks);
        android.util.Log.d(TAG, "  mRootId=" + this.mRootId);
        android.util.Log.d(TAG, "  mMediaSessionToken=" + this.mMediaSessionToken);
    }

    public static class MediaItem implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.media.browse.MediaBrowser.MediaItem> CREATOR = new android.os.Parcelable.Creator<android.media.browse.MediaBrowser.MediaItem>() { // from class: android.media.browse.MediaBrowser.MediaItem.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.browse.MediaBrowser.MediaItem createFromParcel(android.os.Parcel parcel) {
                return new android.media.browse.MediaBrowser.MediaItem(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.browse.MediaBrowser.MediaItem[] newArray(int i) {
                return new android.media.browse.MediaBrowser.MediaItem[i];
            }
        };
        public static final int FLAG_BROWSABLE = 1;
        public static final int FLAG_PLAYABLE = 2;
        private final android.media.MediaDescription mDescription;
        private final int mFlags;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Flags {
        }

        public MediaItem(android.media.MediaDescription mediaDescription, int i) {
            if (mediaDescription == null) {
                throw new java.lang.IllegalArgumentException("description cannot be null");
            }
            if (android.text.TextUtils.isEmpty(mediaDescription.getMediaId())) {
                throw new java.lang.IllegalArgumentException("description must have a non-empty media id");
            }
            this.mFlags = i;
            this.mDescription = mediaDescription;
        }

        private MediaItem(android.os.Parcel parcel) {
            this.mFlags = parcel.readInt();
            this.mDescription = android.media.MediaDescription.CREATOR.createFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mFlags);
            this.mDescription.writeToParcel(parcel, i);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("MediaItem{");
            sb.append("mFlags=").append(this.mFlags);
            sb.append(", mDescription=").append(this.mDescription);
            sb.append('}');
            return sb.toString();
        }

        public int getFlags() {
            return this.mFlags;
        }

        public boolean isBrowsable() {
            return (this.mFlags & 1) != 0;
        }

        public boolean isPlayable() {
            return (this.mFlags & 2) != 0;
        }

        public android.media.MediaDescription getDescription() {
            return this.mDescription;
        }

        public java.lang.String getMediaId() {
            return this.mDescription.getMediaId();
        }
    }

    public static class ConnectionCallback {
        public void onConnected() {
        }

        public void onConnectionSuspended() {
        }

        public void onConnectionFailed() {
        }

        public void onDisconnected() {
            onConnectionFailed();
        }
    }

    public static abstract class SubscriptionCallback {
        android.os.Binder mToken = new android.os.Binder();

        public void onChildrenLoaded(java.lang.String str, java.util.List<android.media.browse.MediaBrowser.MediaItem> list) {
        }

        public void onChildrenLoaded(java.lang.String str, java.util.List<android.media.browse.MediaBrowser.MediaItem> list, android.os.Bundle bundle) {
        }

        public void onError(java.lang.String str) {
        }

        public void onError(java.lang.String str, android.os.Bundle bundle) {
        }
    }

    public static abstract class ItemCallback {
        public void onItemLoaded(android.media.browse.MediaBrowser.MediaItem mediaItem) {
        }

        public void onError(java.lang.String str) {
        }
    }

    private class MediaServiceConnection implements android.content.ServiceConnection {
        private MediaServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(final android.content.ComponentName componentName, final android.os.IBinder iBinder) {
            postOrRun(new java.lang.Runnable() { // from class: android.media.browse.MediaBrowser.MediaServiceConnection.1
                @Override // java.lang.Runnable
                public void run() {
                    if (!android.media.browse.MediaBrowser.MediaServiceConnection.this.isCurrent("onServiceConnected")) {
                        return;
                    }
                    android.media.browse.MediaBrowser.this.mServiceBinder = android.service.media.IMediaBrowserService.Stub.asInterface(iBinder);
                    android.media.browse.MediaBrowser.this.mServiceCallbacks = android.media.browse.MediaBrowser.this.getNewServiceCallbacks();
                    android.media.browse.MediaBrowser.this.mState = 2;
                    try {
                        android.media.browse.MediaBrowser.this.mServiceBinder.connect(android.media.browse.MediaBrowser.this.mContext.getPackageName(), android.media.browse.MediaBrowser.this.mRootHints, android.media.browse.MediaBrowser.this.mServiceCallbacks);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.media.browse.MediaBrowser.TAG, "RemoteException during connect for " + android.media.browse.MediaBrowser.this.mServiceComponent);
                    }
                }
            });
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(final android.content.ComponentName componentName) {
            postOrRun(new java.lang.Runnable() { // from class: android.media.browse.MediaBrowser.MediaServiceConnection.2
                @Override // java.lang.Runnable
                public void run() {
                    if (!android.media.browse.MediaBrowser.MediaServiceConnection.this.isCurrent("onServiceDisconnected")) {
                        return;
                    }
                    android.media.browse.MediaBrowser.this.mServiceBinder = null;
                    android.media.browse.MediaBrowser.this.mServiceCallbacks = null;
                    android.media.browse.MediaBrowser.this.mState = 4;
                    android.media.browse.MediaBrowser.this.mCallback.onConnectionSuspended();
                }
            });
        }

        private void postOrRun(java.lang.Runnable runnable) {
            if (java.lang.Thread.currentThread() == android.media.browse.MediaBrowser.this.mHandler.getLooper().getThread()) {
                runnable.run();
            } else {
                android.media.browse.MediaBrowser.this.mHandler.post(runnable);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isCurrent(java.lang.String str) {
            if (android.media.browse.MediaBrowser.this.mServiceConnection == this && android.media.browse.MediaBrowser.this.mState != 0 && android.media.browse.MediaBrowser.this.mState != 1) {
                return true;
            }
            if (android.media.browse.MediaBrowser.this.mState != 0 && android.media.browse.MediaBrowser.this.mState != 1) {
                android.util.Log.i(android.media.browse.MediaBrowser.TAG, str + " for " + android.media.browse.MediaBrowser.this.mServiceComponent + " with mServiceConnection=" + android.media.browse.MediaBrowser.this.mServiceConnection + " this=" + this);
                return false;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ServiceCallbacks extends android.service.media.IMediaBrowserServiceCallbacks.Stub {
        private java.lang.ref.WeakReference<android.media.browse.MediaBrowser> mMediaBrowser;

        ServiceCallbacks(android.media.browse.MediaBrowser mediaBrowser) {
            this.mMediaBrowser = new java.lang.ref.WeakReference<>(mediaBrowser);
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onConnect(java.lang.String str, android.media.session.MediaSession.Token token, android.os.Bundle bundle) {
            android.media.browse.MediaBrowser mediaBrowser = this.mMediaBrowser.get();
            if (mediaBrowser != null) {
                mediaBrowser.onServiceConnected(this, str, token, bundle);
            }
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onConnectFailed() {
            android.media.browse.MediaBrowser mediaBrowser = this.mMediaBrowser.get();
            if (mediaBrowser != null) {
                mediaBrowser.onConnectionFailed(this);
            }
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onLoadChildren(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, android.os.Bundle bundle) {
            android.media.browse.MediaBrowser mediaBrowser = this.mMediaBrowser.get();
            if (mediaBrowser != null) {
                mediaBrowser.onLoadChildren(this, str, parceledListSlice, bundle);
            }
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onDisconnect() {
            android.media.browse.MediaBrowser mediaBrowser = this.mMediaBrowser.get();
            if (mediaBrowser != null) {
                mediaBrowser.onDisconnectRequested(this);
            }
        }
    }

    private static class Subscription {
        private final java.util.List<android.media.browse.MediaBrowser.SubscriptionCallback> mCallbacks = new java.util.ArrayList();
        private final java.util.List<android.os.Bundle> mOptionsList = new java.util.ArrayList();

        Subscription() {
        }

        public boolean isEmpty() {
            return this.mCallbacks.isEmpty();
        }

        public java.util.List<android.os.Bundle> getOptionsList() {
            return this.mOptionsList;
        }

        public java.util.List<android.media.browse.MediaBrowser.SubscriptionCallback> getCallbacks() {
            return this.mCallbacks;
        }

        public android.media.browse.MediaBrowser.SubscriptionCallback getCallback(android.content.Context context, android.os.Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader());
            }
            for (int i = 0; i < this.mOptionsList.size(); i++) {
                if (android.media.browse.MediaBrowserUtils.areSameOptions(this.mOptionsList.get(i), bundle)) {
                    return this.mCallbacks.get(i);
                }
            }
            return null;
        }

        public void putCallback(android.content.Context context, android.os.Bundle bundle, android.media.browse.MediaBrowser.SubscriptionCallback subscriptionCallback) {
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader());
            }
            for (int i = 0; i < this.mOptionsList.size(); i++) {
                if (android.media.browse.MediaBrowserUtils.areSameOptions(this.mOptionsList.get(i), bundle)) {
                    this.mCallbacks.set(i, subscriptionCallback);
                    return;
                }
            }
            this.mCallbacks.add(subscriptionCallback);
            this.mOptionsList.add(bundle);
        }
    }
}
