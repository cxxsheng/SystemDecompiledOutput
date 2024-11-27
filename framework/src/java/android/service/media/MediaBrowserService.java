package android.service.media;

/* loaded from: classes3.dex */
public abstract class MediaBrowserService extends android.app.Service {
    private static final boolean DBG = false;
    public static final java.lang.String KEY_MEDIA_ITEM = "media_item";
    private static final int RESULT_ERROR = -1;
    private static final int RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED = 2;
    private static final int RESULT_FLAG_OPTION_NOT_HANDLED = 1;
    private static final int RESULT_OK = 0;
    public static final java.lang.String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    private static final java.lang.String TAG = "MediaBrowserService";
    private android.service.media.MediaBrowserService.ConnectionRecord mCurrentConnectionOnHandler;
    private final android.os.Handler mHandler = new android.os.Handler();
    private final java.util.concurrent.atomic.AtomicReference<android.service.media.MediaBrowserService.ServiceState> mServiceState = new java.util.concurrent.atomic.AtomicReference<>(new android.service.media.MediaBrowserService.ServiceState());
    private final android.service.media.MediaBrowserService.ServiceBinder mBinder = new android.service.media.MediaBrowserService.ServiceBinder(this.mServiceState.get());

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface ResultFlags {
    }

    public abstract android.service.media.MediaBrowserService.BrowserRoot onGetRoot(java.lang.String str, int i, android.os.Bundle bundle);

    public abstract void onLoadChildren(java.lang.String str, android.service.media.MediaBrowserService.Result<java.util.List<android.media.browse.MediaBrowser.MediaItem>> result);

    /* JADX INFO: Access modifiers changed from: private */
    static class ConnectionRecord implements android.os.IBinder.DeathRecipient {
        public final android.service.media.IMediaBrowserServiceCallbacks callbacks;
        public final int pid;
        public final java.lang.String pkg;
        public final android.service.media.MediaBrowserService.BrowserRoot root;
        public final android.os.Bundle rootHints;
        public final android.service.media.MediaBrowserService.ServiceState serviceState;
        public final java.util.HashMap<java.lang.String, java.util.List<android.util.Pair<android.os.IBinder, android.os.Bundle>>> subscriptions = new java.util.HashMap<>();
        public final int uid;

        ConnectionRecord(android.service.media.MediaBrowserService.ServiceState serviceState, java.lang.String str, int i, int i2, android.os.Bundle bundle, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, android.service.media.MediaBrowserService.BrowserRoot browserRoot) {
            this.serviceState = serviceState;
            this.pkg = str;
            this.pid = i;
            this.uid = i2;
            this.rootHints = bundle;
            this.callbacks = iMediaBrowserServiceCallbacks;
            this.root = browserRoot;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.serviceState.postOnHandler(new java.lang.Runnable() { // from class: android.service.media.MediaBrowserService$ConnectionRecord$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.media.MediaBrowserService.ConnectionRecord.this.lambda$binderDied$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$binderDied$0() {
            this.serviceState.mConnections.remove(this.callbacks.asBinder());
        }
    }

    public class Result<T> {
        private java.lang.Object mDebug;
        private boolean mDetachCalled;
        private int mFlags;
        private boolean mSendResultCalled;

        Result(java.lang.Object obj) {
            this.mDebug = obj;
        }

        public void sendResult(T t) {
            if (this.mSendResultCalled) {
                throw new java.lang.IllegalStateException("sendResult() called twice for: " + this.mDebug);
            }
            this.mSendResultCalled = true;
            onResultSent(t, this.mFlags);
        }

        public void detach() {
            if (this.mDetachCalled) {
                throw new java.lang.IllegalStateException("detach() called when detach() had already been called for: " + this.mDebug);
            }
            if (this.mSendResultCalled) {
                throw new java.lang.IllegalStateException("detach() called when sendResult() had already been called for: " + this.mDebug);
            }
            this.mDetachCalled = true;
        }

        boolean isDone() {
            return this.mDetachCalled || this.mSendResultCalled;
        }

        void setFlags(int i) {
            this.mFlags = i;
        }

        void onResultSent(T t, int i) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ServiceBinder extends android.service.media.IMediaBrowserService.Stub {
        private final java.util.concurrent.atomic.AtomicReference<java.lang.ref.WeakReference<android.service.media.MediaBrowserService.ServiceState>> mServiceState;

        private ServiceBinder(android.service.media.MediaBrowserService.ServiceState serviceState) {
            this.mServiceState = new java.util.concurrent.atomic.AtomicReference<>();
            setServiceState(serviceState);
        }

        public void setServiceState(android.service.media.MediaBrowserService.ServiceState serviceState) {
            this.mServiceState.set(new java.lang.ref.WeakReference<>(serviceState));
        }

        @Override // android.service.media.IMediaBrowserService
        public void connect(final java.lang.String str, final android.os.Bundle bundle, final android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            final android.service.media.MediaBrowserService.ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            final int callingPid = android.os.Binder.getCallingPid();
            final int callingUid = android.os.Binder.getCallingUid();
            if (!serviceState.isValidPackage(str, callingUid)) {
                throw new java.lang.IllegalArgumentException("Package/uid mismatch: uid=" + callingUid + " package=" + str);
            }
            serviceState.postOnHandler(new java.lang.Runnable() { // from class: android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.media.MediaBrowserService.ServiceState.this.connectOnHandler(str, callingPid, callingUid, bundle, iMediaBrowserServiceCallbacks);
                }
            });
        }

        @Override // android.service.media.IMediaBrowserService
        public void disconnect(final android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            final android.service.media.MediaBrowserService.ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            serviceState.postOnHandler(new java.lang.Runnable() { // from class: android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.media.MediaBrowserService.ServiceState.this.removeConnectionRecordOnHandler(iMediaBrowserServiceCallbacks);
                }
            });
        }

        @Override // android.service.media.IMediaBrowserService
        public void addSubscriptionDeprecated(java.lang.String str, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
        }

        @Override // android.service.media.IMediaBrowserService
        public void addSubscription(final java.lang.String str, final android.os.IBinder iBinder, final android.os.Bundle bundle, final android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            final android.service.media.MediaBrowserService.ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            serviceState.postOnHandler(new java.lang.Runnable() { // from class: android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.media.MediaBrowserService.ServiceState.this.addSubscriptionOnHandler(str, iMediaBrowserServiceCallbacks, iBinder, bundle);
                }
            });
        }

        @Override // android.service.media.IMediaBrowserService
        public void removeSubscriptionDeprecated(java.lang.String str, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
        }

        @Override // android.service.media.IMediaBrowserService
        public void removeSubscription(final java.lang.String str, final android.os.IBinder iBinder, final android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            final android.service.media.MediaBrowserService.ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            serviceState.postOnHandler(new java.lang.Runnable() { // from class: android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.media.MediaBrowserService.ServiceBinder.lambda$removeSubscription$3(android.service.media.MediaBrowserService.ServiceState.this, str, iMediaBrowserServiceCallbacks, iBinder);
                }
            });
        }

        static /* synthetic */ void lambda$removeSubscription$3(android.service.media.MediaBrowserService.ServiceState serviceState, java.lang.String str, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, android.os.IBinder iBinder) {
            if (!serviceState.removeSubscriptionOnHandler(str, iMediaBrowserServiceCallbacks, iBinder)) {
                android.util.Log.w(android.service.media.MediaBrowserService.TAG, "removeSubscription for id with no subscription: " + str);
            }
        }

        @Override // android.service.media.IMediaBrowserService
        public void getMediaItem(final java.lang.String str, final android.os.ResultReceiver resultReceiver, final android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            final android.service.media.MediaBrowserService.ServiceState serviceState = this.mServiceState.get().get();
            if (serviceState == null) {
                return;
            }
            serviceState.postOnHandler(new java.lang.Runnable() { // from class: android.service.media.MediaBrowserService$ServiceBinder$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.media.MediaBrowserService.ServiceState.this.performLoadItemOnHandler(str, iMediaBrowserServiceCallbacks, resultReceiver);
                }
            });
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mBinder;
        }
        return null;
    }

    @Override // android.app.Service
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
    }

    public void onLoadChildren(java.lang.String str, android.service.media.MediaBrowserService.Result<java.util.List<android.media.browse.MediaBrowser.MediaItem>> result, android.os.Bundle bundle) {
        result.setFlags(1);
        onLoadChildren(str, result);
    }

    public void onLoadItem(java.lang.String str, android.service.media.MediaBrowserService.Result<android.media.browse.MediaBrowser.MediaItem> result) {
        result.setFlags(2);
        result.sendResult(null);
    }

    public void setSessionToken(final android.media.session.MediaSession.Token token) {
        final android.service.media.MediaBrowserService.ServiceState serviceState = this.mServiceState.get();
        if (token == null) {
            if (!com.android.media.flags.Flags.enableNullSessionInMediaBrowserService()) {
                throw new java.lang.IllegalArgumentException("Session token may not be null.");
            }
            if (serviceState.mSession != null) {
                android.service.media.MediaBrowserService.ServiceState serviceState2 = new android.service.media.MediaBrowserService.ServiceState();
                this.mBinder.setServiceState(serviceState2);
                this.mServiceState.set(serviceState2);
                serviceState.release();
                return;
            }
            return;
        }
        if (serviceState.mSession != null) {
            throw new java.lang.IllegalStateException("The session token has already been set.");
        }
        serviceState.mSession = token;
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.media.MediaBrowserService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.service.media.MediaBrowserService.ServiceState.this.notifySessionTokenInitializedOnHandler(token);
            }
        });
    }

    public android.media.session.MediaSession.Token getSessionToken() {
        return this.mServiceState.get().mSession;
    }

    public final android.os.Bundle getBrowserRootHints() {
        android.service.media.MediaBrowserService.ConnectionRecord connectionRecord = this.mCurrentConnectionOnHandler;
        if (connectionRecord == null) {
            throw new java.lang.IllegalStateException("This should be called inside of onGetRoot or onLoadChildren or onLoadItem methods");
        }
        if (connectionRecord.rootHints == null) {
            return null;
        }
        return new android.os.Bundle(connectionRecord.rootHints);
    }

    public final android.media.session.MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
        android.service.media.MediaBrowserService.ConnectionRecord connectionRecord = this.mCurrentConnectionOnHandler;
        if (connectionRecord == null) {
            throw new java.lang.IllegalStateException("This should be called inside of onGetRoot or onLoadChildren or onLoadItem methods");
        }
        return new android.media.session.MediaSessionManager.RemoteUserInfo(connectionRecord.pkg, connectionRecord.pid, connectionRecord.uid);
    }

    public void notifyChildrenChanged(java.lang.String str) {
        notifyChildrenChanged(str, android.os.Bundle.EMPTY);
    }

    public void notifyChildrenChanged(final java.lang.String str, final android.os.Bundle bundle) {
        if (bundle == null) {
            throw new java.lang.IllegalArgumentException("options cannot be null in notifyChildrenChanged");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: android.service.media.MediaBrowserService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.service.media.MediaBrowserService.this.lambda$notifyChildrenChanged$1(str, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyChildrenChanged$1(java.lang.String str, android.os.Bundle bundle) {
        this.mServiceState.get().notifyChildrenChangeOnHandler(str, bundle);
    }

    public static final class BrowserRoot {
        public static final java.lang.String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final java.lang.String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final java.lang.String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
        private final android.os.Bundle mExtras;
        private final java.lang.String mRootId;

        public BrowserRoot(java.lang.String str, android.os.Bundle bundle) {
            if (str == null) {
                throw new java.lang.IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
            }
            this.mRootId = str;
            this.mExtras = bundle;
        }

        public java.lang.String getRootId() {
            return this.mRootId;
        }

        public android.os.Bundle getExtras() {
            return this.mExtras;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ServiceState {
        private final android.util.ArrayMap<android.os.IBinder, android.service.media.MediaBrowserService.ConnectionRecord> mConnections;
        private android.media.session.MediaSession.Token mSession;

        private ServiceState() {
            this.mConnections = new android.util.ArrayMap<>();
        }

        public android.service.media.MediaBrowserService.ServiceBinder getBinder() {
            return android.service.media.MediaBrowserService.this.mBinder;
        }

        public void postOnHandler(java.lang.Runnable runnable) {
            android.service.media.MediaBrowserService.this.mHandler.post(runnable);
        }

        public void release() {
            android.service.media.MediaBrowserService.this.mHandler.postAtFrontOfQueue(new java.lang.Runnable() { // from class: android.service.media.MediaBrowserService$ServiceState$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.media.MediaBrowserService.ServiceState.this.clearConnectionsOnHandler();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearConnectionsOnHandler() {
            java.util.Iterator<android.service.media.MediaBrowserService.ConnectionRecord> it = this.mConnections.values().iterator();
            while (it.hasNext()) {
                android.service.media.MediaBrowserService.ConnectionRecord next = it.next();
                it.remove();
                try {
                    next.callbacks.onDisconnect();
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(android.service.media.MediaBrowserService.TAG, android.text.TextUtils.formatSimple("onDisconnectRequest for %s failed", next.pkg), e);
                }
            }
        }

        public void removeConnectionRecordOnHandler(android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            android.service.media.MediaBrowserService.ConnectionRecord remove = this.mConnections.remove(iMediaBrowserServiceCallbacks.asBinder());
            if (remove != null) {
                remove.callbacks.asBinder().unlinkToDeath(remove, 0);
            }
        }

        public void notifySessionTokenInitializedOnHandler(android.media.session.MediaSession.Token token) {
            java.util.Iterator<android.service.media.MediaBrowserService.ConnectionRecord> it = this.mConnections.values().iterator();
            while (it.hasNext()) {
                android.service.media.MediaBrowserService.ConnectionRecord next = it.next();
                try {
                    next.callbacks.onConnect(next.root.getRootId(), token, next.root.getExtras());
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(android.service.media.MediaBrowserService.TAG, "Connection for " + next.pkg + " is no longer valid.");
                    it.remove();
                }
            }
        }

        public void notifyChildrenChangeOnHandler(java.lang.String str, android.os.Bundle bundle) {
            java.util.Iterator<android.os.IBinder> it = this.mConnections.keySet().iterator();
            while (it.hasNext()) {
                android.service.media.MediaBrowserService.ConnectionRecord connectionRecord = this.mConnections.get(it.next());
                java.util.List<android.util.Pair<android.os.IBinder, android.os.Bundle>> list = connectionRecord.subscriptions.get(str);
                if (list != null) {
                    for (android.util.Pair<android.os.IBinder, android.os.Bundle> pair : list) {
                        if (android.media.browse.MediaBrowserUtils.hasDuplicatedItems(bundle, pair.second)) {
                            performLoadChildrenOnHandler(str, connectionRecord, pair.second);
                        }
                    }
                }
            }
        }

        public void addSubscriptionOnHandler(java.lang.String str, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, android.os.IBinder iBinder, android.os.Bundle bundle) {
            android.service.media.MediaBrowserService.ConnectionRecord connectionRecord = this.mConnections.get(iMediaBrowserServiceCallbacks.asBinder());
            if (connectionRecord == null) {
                android.util.Log.w(android.service.media.MediaBrowserService.TAG, "addSubscription for callback that isn't registered id=" + str);
                return;
            }
            java.util.List<android.util.Pair<android.os.IBinder, android.os.Bundle>> list = connectionRecord.subscriptions.get(str);
            if (list == null) {
                list = new java.util.ArrayList<>();
            }
            for (android.util.Pair<android.os.IBinder, android.os.Bundle> pair : list) {
                if (iBinder == pair.first && android.media.browse.MediaBrowserUtils.areSameOptions(bundle, pair.second)) {
                    return;
                }
            }
            list.add(new android.util.Pair<>(iBinder, bundle));
            connectionRecord.subscriptions.put(str, list);
            performLoadChildrenOnHandler(str, connectionRecord, bundle);
        }

        public void connectOnHandler(java.lang.String str, int i, int i2, android.os.Bundle bundle, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks) {
            android.os.IBinder asBinder = iMediaBrowserServiceCallbacks.asBinder();
            this.mConnections.remove(asBinder);
            android.service.media.MediaBrowserService.this.mCurrentConnectionOnHandler = new android.service.media.MediaBrowserService.ConnectionRecord(this, str, i, i2, bundle, iMediaBrowserServiceCallbacks, null);
            android.service.media.MediaBrowserService.BrowserRoot onGetRoot = android.service.media.MediaBrowserService.this.onGetRoot(str, i2, bundle);
            android.service.media.MediaBrowserService.this.mCurrentConnectionOnHandler = null;
            if (onGetRoot == null) {
                android.util.Log.i(android.service.media.MediaBrowserService.TAG, "No root for client " + str + " from service " + getClass().getName());
                try {
                    iMediaBrowserServiceCallbacks.onConnectFailed();
                    return;
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(android.service.media.MediaBrowserService.TAG, "Calling onConnectFailed() failed. Ignoring. pkg=" + str);
                    return;
                }
            }
            try {
                android.service.media.MediaBrowserService.ConnectionRecord connectionRecord = new android.service.media.MediaBrowserService.ConnectionRecord(this, str, i, i2, bundle, iMediaBrowserServiceCallbacks, onGetRoot);
                this.mConnections.put(asBinder, connectionRecord);
                asBinder.linkToDeath(connectionRecord, 0);
                if (this.mSession != null) {
                    iMediaBrowserServiceCallbacks.onConnect(connectionRecord.root.getRootId(), this.mSession, connectionRecord.root.getExtras());
                }
            } catch (android.os.RemoteException e2) {
                android.util.Log.w(android.service.media.MediaBrowserService.TAG, "Calling onConnect() failed. Dropping client. pkg=" + str);
                this.mConnections.remove(asBinder);
            }
        }

        public boolean removeSubscriptionOnHandler(java.lang.String str, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, android.os.IBinder iBinder) {
            android.service.media.MediaBrowserService.ConnectionRecord connectionRecord = this.mConnections.get(iMediaBrowserServiceCallbacks.asBinder());
            if (connectionRecord == null) {
                android.util.Log.w(android.service.media.MediaBrowserService.TAG, "removeSubscription for callback that isn't registered id=" + str);
                return true;
            }
            boolean z = false;
            if (iBinder == null) {
                if (connectionRecord.subscriptions.remove(str) != null) {
                    return true;
                }
                return false;
            }
            java.util.List<android.util.Pair<android.os.IBinder, android.os.Bundle>> list = connectionRecord.subscriptions.get(str);
            if (list != null) {
                java.util.Iterator<android.util.Pair<android.os.IBinder, android.os.Bundle>> it = list.iterator();
                while (it.hasNext()) {
                    if (iBinder == it.next().first) {
                        it.remove();
                        z = true;
                    }
                }
                if (list.isEmpty()) {
                    connectionRecord.subscriptions.remove(str);
                }
            }
            return z;
        }

        public void performLoadChildrenOnHandler(final java.lang.String str, final android.service.media.MediaBrowserService.ConnectionRecord connectionRecord, final android.os.Bundle bundle) {
            android.service.media.MediaBrowserService.Result<java.util.List<android.media.browse.MediaBrowser.MediaItem>> result = new android.service.media.MediaBrowserService.Result<java.util.List<android.media.browse.MediaBrowser.MediaItem>>(str) { // from class: android.service.media.MediaBrowserService.ServiceState.1
                {
                    android.service.media.MediaBrowserService mediaBrowserService = android.service.media.MediaBrowserService.this;
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // android.service.media.MediaBrowserService.Result
                public void onResultSent(java.util.List<android.media.browse.MediaBrowser.MediaItem> list, int i) {
                    android.content.pm.ParceledListSlice parceledListSlice;
                    if (android.service.media.MediaBrowserService.ServiceState.this.mConnections.get(connectionRecord.callbacks.asBinder()) != connectionRecord) {
                        return;
                    }
                    if ((i & 1) != 0) {
                        list = android.media.browse.MediaBrowserUtils.applyPagingOptions(list, bundle);
                    }
                    if (list == null) {
                        parceledListSlice = null;
                    } else {
                        parceledListSlice = new android.content.pm.ParceledListSlice(list);
                        parceledListSlice.setInlineCountLimit(1);
                    }
                    try {
                        connectionRecord.callbacks.onLoadChildren(str, parceledListSlice, bundle);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(android.service.media.MediaBrowserService.TAG, "Calling onLoadChildren() failed for id=" + str + " package=" + connectionRecord.pkg);
                    }
                }
            };
            android.service.media.MediaBrowserService.this.mCurrentConnectionOnHandler = connectionRecord;
            if (bundle == null) {
                android.service.media.MediaBrowserService.this.onLoadChildren(str, result);
            } else {
                android.service.media.MediaBrowserService.this.onLoadChildren(str, result, bundle);
            }
            android.service.media.MediaBrowserService.this.mCurrentConnectionOnHandler = null;
            if (!result.isDone()) {
                throw new java.lang.IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + connectionRecord.pkg + " id=" + str);
            }
        }

        public void performLoadItemOnHandler(final java.lang.String str, android.service.media.IMediaBrowserServiceCallbacks iMediaBrowserServiceCallbacks, final android.os.ResultReceiver resultReceiver) {
            final android.service.media.MediaBrowserService.ConnectionRecord connectionRecord = this.mConnections.get(iMediaBrowserServiceCallbacks.asBinder());
            if (connectionRecord == null) {
                android.util.Log.w(android.service.media.MediaBrowserService.TAG, "getMediaItem for callback that isn't registered id=" + str);
                return;
            }
            android.service.media.MediaBrowserService.Result<android.media.browse.MediaBrowser.MediaItem> result = new android.service.media.MediaBrowserService.Result<android.media.browse.MediaBrowser.MediaItem>(str) { // from class: android.service.media.MediaBrowserService.ServiceState.2
                {
                    android.service.media.MediaBrowserService mediaBrowserService = android.service.media.MediaBrowserService.this;
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // android.service.media.MediaBrowserService.Result
                public void onResultSent(android.media.browse.MediaBrowser.MediaItem mediaItem, int i) {
                    if (android.service.media.MediaBrowserService.ServiceState.this.mConnections.get(connectionRecord.callbacks.asBinder()) != connectionRecord) {
                        return;
                    }
                    if ((i & 2) != 0) {
                        resultReceiver.send(-1, null);
                        return;
                    }
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putParcelable(android.service.media.MediaBrowserService.KEY_MEDIA_ITEM, mediaItem);
                    resultReceiver.send(0, bundle);
                }
            };
            android.service.media.MediaBrowserService.this.mCurrentConnectionOnHandler = connectionRecord;
            android.service.media.MediaBrowserService.this.onLoadItem(str, result);
            android.service.media.MediaBrowserService.this.mCurrentConnectionOnHandler = null;
            if (!result.isDone()) {
                throw new java.lang.IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + str);
            }
        }

        public boolean isValidPackage(java.lang.String str, int i) {
            if (str == null) {
                return false;
            }
            for (java.lang.String str2 : android.service.media.MediaBrowserService.this.getPackageManager().getPackagesForUid(i)) {
                if (str2.equals(str)) {
                    return true;
                }
            }
            return false;
        }
    }
}
