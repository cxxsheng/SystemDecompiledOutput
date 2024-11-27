package android.media;

/* loaded from: classes2.dex */
public abstract class MediaRoute2ProviderService extends android.app.Service {
    public static final java.lang.String CATEGORY_SELF_SCAN_ONLY = "android.media.MediaRoute2ProviderService.SELF_SCAN_ONLY";
    private static final int MAX_REQUEST_IDS_SIZE = 500;
    public static final int REASON_INVALID_COMMAND = 4;
    public static final int REASON_NETWORK_ERROR = 2;
    public static final int REASON_REJECTED = 1;
    public static final int REASON_ROUTE_NOT_AVAILABLE = 3;
    public static final int REASON_UNKNOWN_ERROR = 0;
    public static final long REQUEST_ID_NONE = 0;
    public static final java.lang.String SERVICE_INTERFACE = "android.media.MediaRoute2ProviderService";
    private volatile android.media.MediaRoute2ProviderInfo mProviderInfo;
    private android.media.IMediaRoute2ProviderServiceCallback mRemoteCallback;
    private android.media.MediaRoute2ProviderService.MediaRoute2ProviderServiceStub mStub;
    private static final java.lang.String TAG = "MR2ProviderService";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private final java.lang.Object mSessionLock = new java.lang.Object();
    private final java.lang.Object mRequestIdsLock = new java.lang.Object();
    private final java.util.concurrent.atomic.AtomicBoolean mStatePublishScheduled = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final java.util.concurrent.atomic.AtomicBoolean mSessionUpdateScheduled = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final java.util.Deque<java.lang.Long> mRequestIds = new java.util.ArrayDeque(500);
    private final android.util.ArrayMap<java.lang.String, android.media.RoutingSessionInfo> mSessionInfos = new android.util.ArrayMap<>();
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Reason {
    }

    public abstract void onCreateSession(long j, java.lang.String str, java.lang.String str2, android.os.Bundle bundle);

    public abstract void onDeselectRoute(long j, java.lang.String str, java.lang.String str2);

    public abstract void onReleaseSession(long j, java.lang.String str);

    public abstract void onSelectRoute(long j, java.lang.String str, java.lang.String str2);

    public abstract void onSetRouteVolume(long j, java.lang.String str, int i);

    public abstract void onSetSessionVolume(long j, java.lang.String str, int i);

    public abstract void onTransferToRoute(long j, java.lang.String str, java.lang.String str2);

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            if (this.mStub == null) {
                this.mStub = new android.media.MediaRoute2ProviderService.MediaRoute2ProviderServiceStub();
            }
            return this.mStub;
        }
        return null;
    }

    public final android.media.RoutingSessionInfo getSessionInfo(java.lang.String str) {
        android.media.RoutingSessionInfo routingSessionInfo;
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("sessionId must not be empty");
        }
        synchronized (this.mSessionLock) {
            routingSessionInfo = this.mSessionInfos.get(str);
        }
        return routingSessionInfo;
    }

    public final java.util.List<android.media.RoutingSessionInfo> getAllSessionInfo() {
        java.util.ArrayList arrayList;
        synchronized (this.mSessionLock) {
            arrayList = new java.util.ArrayList(this.mSessionInfos.values());
        }
        return arrayList;
    }

    public final void notifySessionCreated(long j, android.media.RoutingSessionInfo routingSessionInfo) {
        java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        if (DEBUG) {
            android.util.Log.d(TAG, "notifySessionCreated: Creating a session. requestId=" + j + ", sessionInfo=" + routingSessionInfo);
        }
        if (j != 0 && !removeRequestId(j)) {
            android.util.Log.w(TAG, "notifySessionCreated: The requestId doesn't exist. requestId=" + j);
            return;
        }
        java.lang.String id = routingSessionInfo.getId();
        synchronized (this.mSessionLock) {
            if (this.mSessionInfos.containsKey(id)) {
                android.util.Log.w(TAG, "notifySessionCreated: Ignoring duplicate session id.");
                return;
            }
            this.mSessionInfos.put(routingSessionInfo.getId(), routingSessionInfo);
            if (this.mRemoteCallback == null) {
                return;
            }
            try {
                this.mRemoteCallback.notifySessionCreated(j, routingSessionInfo);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Failed to notify session created.");
            }
        }
    }

    public final void notifySessionUpdated(android.media.RoutingSessionInfo routingSessionInfo) {
        java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        if (DEBUG) {
            android.util.Log.d(TAG, "notifySessionUpdated: Updating session id=" + routingSessionInfo);
        }
        java.lang.String id = routingSessionInfo.getId();
        synchronized (this.mSessionLock) {
            if (this.mSessionInfos.containsKey(id)) {
                this.mSessionInfos.put(id, routingSessionInfo);
                scheduleUpdateSessions();
            } else {
                android.util.Log.w(TAG, "notifySessionUpdated: Ignoring unknown session info.");
            }
        }
    }

    public final void notifySessionReleased(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("sessionId must not be empty");
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "notifySessionReleased: Releasing session id=" + str);
        }
        synchronized (this.mSessionLock) {
            android.media.RoutingSessionInfo remove = this.mSessionInfos.remove(str);
            if (remove == null) {
                android.util.Log.w(TAG, "notifySessionReleased: Ignoring unknown session info.");
            } else {
                if (this.mRemoteCallback == null) {
                    return;
                }
                try {
                    this.mRemoteCallback.notifySessionReleased(remove);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(TAG, "Failed to notify session released.", e);
                }
            }
        }
    }

    public final void notifyRequestFailed(long j, int i) {
        if (this.mRemoteCallback == null) {
            return;
        }
        if (!removeRequestId(j)) {
            android.util.Log.w(TAG, "notifyRequestFailed: The requestId doesn't exist. requestId=" + j);
            return;
        }
        try {
            this.mRemoteCallback.notifyRequestFailed(j, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to notify that the request has failed.");
        }
    }

    public void onDiscoveryPreferenceChanged(android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
    }

    public final void notifyRoutes(java.util.Collection<android.media.MediaRoute2Info> collection) {
        java.util.Objects.requireNonNull(collection, "routes must not be null");
        this.mProviderInfo = new android.media.MediaRoute2ProviderInfo.Builder().addRoutes(collection).build();
        schedulePublishState();
    }

    void setCallback(android.media.IMediaRoute2ProviderServiceCallback iMediaRoute2ProviderServiceCallback) {
        this.mRemoteCallback = iMediaRoute2ProviderServiceCallback;
        schedulePublishState();
        scheduleUpdateSessions();
    }

    void schedulePublishState() {
        if (this.mStatePublishScheduled.compareAndSet(false, true)) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.MediaRoute2ProviderService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRoute2ProviderService.this.publishState();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void publishState() {
        if (!this.mStatePublishScheduled.compareAndSet(true, false) || this.mRemoteCallback == null) {
            return;
        }
        try {
            this.mRemoteCallback.notifyProviderUpdated(this.mProviderInfo);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to publish provider state.", e);
        }
    }

    void scheduleUpdateSessions() {
        if (this.mSessionUpdateScheduled.compareAndSet(false, true)) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.media.MediaRoute2ProviderService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRoute2ProviderService.this.updateSessions();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSessions() {
        java.util.ArrayList arrayList;
        if (!this.mSessionUpdateScheduled.compareAndSet(true, false) || this.mRemoteCallback == null) {
            return;
        }
        synchronized (this.mSessionLock) {
            arrayList = new java.util.ArrayList(this.mSessionInfos.values());
        }
        try {
            this.mRemoteCallback.notifySessionsUpdated(arrayList);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to notify session info changed.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addRequestId(long j) {
        synchronized (this.mRequestIdsLock) {
            if (this.mRequestIds.size() >= 500) {
                this.mRequestIds.removeFirst();
            }
            this.mRequestIds.addLast(java.lang.Long.valueOf(j));
        }
    }

    private boolean removeRequestId(long j) {
        boolean removeFirstOccurrence;
        synchronized (this.mRequestIdsLock) {
            removeFirstOccurrence = this.mRequestIds.removeFirstOccurrence(java.lang.Long.valueOf(j));
        }
        return removeFirstOccurrence;
    }

    final class MediaRoute2ProviderServiceStub extends android.media.IMediaRoute2ProviderService.Stub {
        MediaRoute2ProviderServiceStub() {
        }

        private boolean checkCallerIsSystem() {
            return android.os.Binder.getCallingUid() == 1000;
        }

        private boolean checkSessionIdIsValid(java.lang.String str, java.lang.String str2) {
            if (android.text.TextUtils.isEmpty(str)) {
                android.util.Log.w(android.media.MediaRoute2ProviderService.TAG, str2 + ": Ignoring empty sessionId from system service.");
                return false;
            }
            if (android.media.MediaRoute2ProviderService.this.getSessionInfo(str) == null) {
                android.util.Log.w(android.media.MediaRoute2ProviderService.TAG, str2 + ": Ignoring unknown session from system service. sessionId=" + str);
                return false;
            }
            return true;
        }

        private boolean checkRouteIdIsValid(java.lang.String str, java.lang.String str2) {
            if (android.text.TextUtils.isEmpty(str)) {
                android.util.Log.w(android.media.MediaRoute2ProviderService.TAG, str2 + ": Ignoring empty routeId from system service.");
                return false;
            }
            if (android.media.MediaRoute2ProviderService.this.mProviderInfo == null || android.media.MediaRoute2ProviderService.this.mProviderInfo.getRoute(str) == null) {
                android.util.Log.w(android.media.MediaRoute2ProviderService.TAG, str2 + ": Ignoring unknown route from system service. routeId=" + str);
                return false;
            }
            return true;
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setCallback(android.media.IMediaRoute2ProviderServiceCallback iMediaRoute2ProviderServiceCallback) {
            if (!checkCallerIsSystem()) {
                return;
            }
            android.media.MediaRoute2ProviderService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda8
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.media.MediaRoute2ProviderService) obj).setCallback((android.media.IMediaRoute2ProviderServiceCallback) obj2);
                }
            }, android.media.MediaRoute2ProviderService.this, iMediaRoute2ProviderServiceCallback));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void updateDiscoveryPreference(android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
            if (!checkCallerIsSystem()) {
                return;
            }
            android.media.MediaRoute2ProviderService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.media.MediaRoute2ProviderService) obj).onDiscoveryPreferenceChanged((android.media.RouteDiscoveryPreference) obj2);
                }
            }, android.media.MediaRoute2ProviderService.this, routeDiscoveryPreference));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setRouteVolume(long j, java.lang.String str, int i) {
            if (!checkCallerIsSystem() || !checkRouteIdIsValid(str, "setRouteVolume")) {
                return;
            }
            android.media.MediaRoute2ProviderService.this.addRequestId(j);
            android.media.MediaRoute2ProviderService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda5
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.media.MediaRoute2ProviderService) obj).onSetRouteVolume(((java.lang.Long) obj2).longValue(), (java.lang.String) obj3, ((java.lang.Integer) obj4).intValue());
                }
            }, android.media.MediaRoute2ProviderService.this, java.lang.Long.valueOf(j), str, java.lang.Integer.valueOf(i)));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void requestCreateSession(long j, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
            if (!checkCallerIsSystem() || !checkRouteIdIsValid(str2, "requestCreateSession")) {
                return;
            }
            android.media.MediaRoute2ProviderService.this.addRequestId(j);
            android.media.MediaRoute2ProviderService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuintConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((android.media.MediaRoute2ProviderService) obj).onCreateSession(((java.lang.Long) obj2).longValue(), (java.lang.String) obj3, (java.lang.String) obj4, (android.os.Bundle) obj5);
                }
            }, android.media.MediaRoute2ProviderService.this, java.lang.Long.valueOf(j), str, str2, bundle));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void selectRoute(long j, java.lang.String str, java.lang.String str2) {
            if (!checkCallerIsSystem() || !checkSessionIdIsValid(str, "selectRoute") || !checkRouteIdIsValid(str2, "selectRoute")) {
                return;
            }
            android.media.MediaRoute2ProviderService.this.addRequestId(j);
            android.media.MediaRoute2ProviderService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda6
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.media.MediaRoute2ProviderService) obj).onSelectRoute(((java.lang.Long) obj2).longValue(), (java.lang.String) obj3, (java.lang.String) obj4);
                }
            }, android.media.MediaRoute2ProviderService.this, java.lang.Long.valueOf(j), str, str2));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void deselectRoute(long j, java.lang.String str, java.lang.String str2) {
            if (!checkCallerIsSystem() || !checkSessionIdIsValid(str, "deselectRoute") || !checkRouteIdIsValid(str2, "deselectRoute")) {
                return;
            }
            android.media.MediaRoute2ProviderService.this.addRequestId(j);
            android.media.MediaRoute2ProviderService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.media.MediaRoute2ProviderService) obj).onDeselectRoute(((java.lang.Long) obj2).longValue(), (java.lang.String) obj3, (java.lang.String) obj4);
                }
            }, android.media.MediaRoute2ProviderService.this, java.lang.Long.valueOf(j), str, str2));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void transferToRoute(long j, java.lang.String str, java.lang.String str2) {
            if (!checkCallerIsSystem() || !checkSessionIdIsValid(str, "transferToRoute") || !checkRouteIdIsValid(str2, "transferToRoute")) {
                return;
            }
            android.media.MediaRoute2ProviderService.this.addRequestId(j);
            android.media.MediaRoute2ProviderService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.media.MediaRoute2ProviderService) obj).onTransferToRoute(((java.lang.Long) obj2).longValue(), (java.lang.String) obj3, (java.lang.String) obj4);
                }
            }, android.media.MediaRoute2ProviderService.this, java.lang.Long.valueOf(j), str, str2));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void setSessionVolume(long j, java.lang.String str, int i) {
            if (!checkCallerIsSystem() || !checkSessionIdIsValid(str, "setSessionVolume")) {
                return;
            }
            android.media.MediaRoute2ProviderService.this.addRequestId(j);
            android.media.MediaRoute2ProviderService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                    ((android.media.MediaRoute2ProviderService) obj).onSetSessionVolume(((java.lang.Long) obj2).longValue(), (java.lang.String) obj3, ((java.lang.Integer) obj4).intValue());
                }
            }, android.media.MediaRoute2ProviderService.this, java.lang.Long.valueOf(j), str, java.lang.Integer.valueOf(i)));
        }

        @Override // android.media.IMediaRoute2ProviderService
        public void releaseSession(long j, java.lang.String str) {
            if (!checkCallerIsSystem() || !checkSessionIdIsValid(str, "releaseSession")) {
                return;
            }
            android.media.MediaRoute2ProviderService.this.addRequestId(j);
            android.media.MediaRoute2ProviderService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.media.MediaRoute2ProviderService$MediaRoute2ProviderServiceStub$$ExternalSyntheticLambda7
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.media.MediaRoute2ProviderService) obj).onReleaseSession(((java.lang.Long) obj2).longValue(), (java.lang.String) obj3);
                }
            }, android.media.MediaRoute2ProviderService.this, java.lang.Long.valueOf(j), str));
        }
    }
}
