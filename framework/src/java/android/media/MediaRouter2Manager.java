package android.media;

/* loaded from: classes2.dex */
public final class MediaRouter2Manager {
    public static final int REQUEST_ID_NONE = 0;
    private static final java.lang.String TAG = "MR2Manager";
    public static final int TRANSFER_TIMEOUT_MS = 30000;
    private static android.media.MediaRouter2Manager sInstance;
    private static final java.lang.Object sLock = new java.lang.Object();
    final android.os.Handler mHandler;
    private final android.media.session.MediaSessionManager mMediaSessionManager;
    private final java.util.concurrent.atomic.AtomicInteger mScanRequestCount = new java.util.concurrent.atomic.AtomicInteger(0);
    final java.util.concurrent.CopyOnWriteArrayList<android.media.MediaRouter2Manager.CallbackRecord> mCallbackRecords = new java.util.concurrent.CopyOnWriteArrayList<>();
    private final java.lang.Object mRoutesLock = new java.lang.Object();
    private final java.util.Map<java.lang.String, android.media.MediaRoute2Info> mRoutes = new java.util.HashMap();
    final java.util.concurrent.ConcurrentMap<java.lang.String, android.media.RouteDiscoveryPreference> mDiscoveryPreferenceMap = new java.util.concurrent.ConcurrentHashMap();
    private final java.util.concurrent.ConcurrentMap<java.lang.String, android.media.RouteListingPreference> mPackageToRouteListingPreferenceMap = new java.util.concurrent.ConcurrentHashMap();
    private final java.util.concurrent.atomic.AtomicInteger mNextRequestId = new java.util.concurrent.atomic.AtomicInteger(1);
    private final java.util.concurrent.CopyOnWriteArrayList<android.media.MediaRouter2Manager.TransferRequest> mTransferRequests = new java.util.concurrent.CopyOnWriteArrayList<>();
    private final android.media.IMediaRouterService mMediaRouterService = android.media.IMediaRouterService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.MEDIA_ROUTER_SERVICE));
    private final android.media.MediaRouter2Manager.Client mClient = new android.media.MediaRouter2Manager.Client();

    public static android.media.MediaRouter2Manager getInstance(android.content.Context context) {
        android.media.MediaRouter2Manager mediaRouter2Manager;
        java.util.Objects.requireNonNull(context, "context must not be null");
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new android.media.MediaRouter2Manager(context);
            }
            mediaRouter2Manager = sInstance;
        }
        return mediaRouter2Manager;
    }

    private MediaRouter2Manager(android.content.Context context) {
        this.mMediaSessionManager = (android.media.session.MediaSessionManager) context.getSystemService(android.content.Context.MEDIA_SESSION_SERVICE);
        this.mHandler = new android.os.Handler(context.getMainLooper());
        try {
            this.mMediaRouterService.registerManager(this.mClient, context.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerCallback(java.util.concurrent.Executor executor, android.media.MediaRouter2Manager.Callback callback) {
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(callback, "callback must not be null");
        if (!this.mCallbackRecords.addIfAbsent(new android.media.MediaRouter2Manager.CallbackRecord(executor, callback))) {
            android.util.Log.w(TAG, "Ignoring to register the same callback twice.");
        }
    }

    public void unregisterCallback(android.media.MediaRouter2Manager.Callback callback) {
        java.util.Objects.requireNonNull(callback, "callback must not be null");
        if (!this.mCallbackRecords.remove(new android.media.MediaRouter2Manager.CallbackRecord(null, callback))) {
            android.util.Log.w(TAG, "unregisterCallback: Ignore unknown callback. " + callback);
        }
    }

    public void registerScanRequest() {
        if (this.mScanRequestCount.getAndIncrement() == 0) {
            try {
                this.mMediaRouterService.updateScanningState(this.mClient, 1);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterScanRequest() {
        if (this.mScanRequestCount.updateAndGet(new java.util.function.IntUnaryOperator() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda12
            @Override // java.util.function.IntUnaryOperator
            public final int applyAsInt(int i) {
                return android.media.MediaRouter2Manager.lambda$unregisterScanRequest$0(i);
            }
        }) == 0) {
            try {
                this.mMediaRouterService.updateScanningState(this.mClient, 0);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    static /* synthetic */ int lambda$unregisterScanRequest$0(int i) {
        if (i == 0) {
            throw new java.lang.IllegalStateException("No active scan requests to unregister.");
        }
        return i - 1;
    }

    public android.media.session.MediaController getMediaControllerForRoutingSession(android.media.RoutingSessionInfo routingSessionInfo) {
        for (android.media.session.MediaController mediaController : this.mMediaSessionManager.getActiveSessions(null)) {
            if (areSessionsMatched(mediaController, routingSessionInfo)) {
                return mediaController;
            }
        }
        return null;
    }

    public java.util.List<android.media.MediaRoute2Info> getAvailableRoutes(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "packageName must not be null");
        return getAvailableRoutes(getRoutingSessions(str).get(r2.size() - 1));
    }

    public java.util.List<android.media.MediaRoute2Info> getTransferableRoutes(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "packageName must not be null");
        return getTransferableRoutes(getRoutingSessions(str).get(r2.size() - 1));
    }

    public java.util.List<android.media.MediaRoute2Info> getAvailableRoutes(android.media.RoutingSessionInfo routingSessionInfo) {
        return getFilteredRoutes(routingSessionInfo, true, null);
    }

    public java.util.List<android.media.MediaRoute2Info> getTransferableRoutes(final android.media.RoutingSessionInfo routingSessionInfo) {
        return getFilteredRoutes(routingSessionInfo, false, new java.util.function.Predicate() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.media.MediaRouter2Manager.lambda$getTransferableRoutes$1(android.media.RoutingSessionInfo.this, (android.media.MediaRoute2Info) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$getTransferableRoutes$1(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info) {
        return routingSessionInfo.isSystemSession() ^ mediaRoute2Info.isSystemRoute();
    }

    private java.util.List<android.media.MediaRoute2Info> getSortedRoutes(android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
        java.util.ArrayList arrayList;
        java.util.List<android.media.MediaRoute2Info> copyOf;
        if (!routeDiscoveryPreference.shouldRemoveDuplicates()) {
            synchronized (this.mRoutesLock) {
                copyOf = java.util.List.copyOf(this.mRoutes.values());
            }
            return copyOf;
        }
        final android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        int size = routeDiscoveryPreference.getDeduplicationPackageOrder().size();
        for (int i = 0; i < size; i++) {
            arrayMap.put(routeDiscoveryPreference.getDeduplicationPackageOrder().get(i), java.lang.Integer.valueOf(size - i));
        }
        synchronized (this.mRoutesLock) {
            arrayList = new java.util.ArrayList(this.mRoutes.values());
        }
        arrayList.sort(java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                return android.media.MediaRouter2Manager.lambda$getSortedRoutes$2(arrayMap, (android.media.MediaRoute2Info) obj);
            }
        }));
        return arrayList;
    }

    static /* synthetic */ int lambda$getSortedRoutes$2(java.util.Map map, android.media.MediaRoute2Info mediaRoute2Info) {
        return -((java.lang.Integer) map.getOrDefault(mediaRoute2Info.getPackageName(), 0)).intValue();
    }

    private java.util.List<android.media.MediaRoute2Info> getFilteredRoutes(android.media.RoutingSessionInfo routingSessionInfo, boolean z, java.util.function.Predicate<android.media.MediaRoute2Info> predicate) {
        java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.lang.String clientPackageName = routingSessionInfo.getClientPackageName();
        android.media.RouteDiscoveryPreference orDefault = this.mDiscoveryPreferenceMap.getOrDefault(clientPackageName, android.media.RouteDiscoveryPreference.EMPTY);
        for (android.media.MediaRoute2Info mediaRoute2Info : getSortedRoutes(orDefault)) {
            if (mediaRoute2Info.isVisibleTo(clientPackageName)) {
                boolean contains = routingSessionInfo.getTransferableRoutes().contains(mediaRoute2Info.getId());
                boolean contains2 = routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId());
                if (contains || (z && contains2)) {
                    arrayList.add(mediaRoute2Info);
                } else if (mediaRoute2Info.hasAnyFeatures(orDefault.getPreferredFeatures()) && (orDefault.getAllowedPackages().isEmpty() || (mediaRoute2Info.getPackageName() != null && orDefault.getAllowedPackages().contains(mediaRoute2Info.getPackageName())))) {
                    if (predicate == null || predicate.test(mediaRoute2Info)) {
                        if (orDefault.shouldRemoveDuplicates()) {
                            if (java.util.Collections.disjoint(arraySet, mediaRoute2Info.getDeduplicationIds())) {
                                arraySet.addAll(mediaRoute2Info.getDeduplicationIds());
                            }
                        }
                        arrayList.add(mediaRoute2Info);
                    }
                }
            }
        }
        return arrayList;
    }

    public android.media.RouteDiscoveryPreference getDiscoveryPreference(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "packageName must not be null");
        return this.mDiscoveryPreferenceMap.getOrDefault(str, android.media.RouteDiscoveryPreference.EMPTY);
    }

    public android.media.RouteListingPreference getRouteListingPreference(java.lang.String str) {
        com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(str));
        return this.mPackageToRouteListingPreferenceMap.get(str);
    }

    public android.media.RoutingSessionInfo getSystemRoutingSession(java.lang.String str) {
        try {
            return this.mMediaRouterService.getSystemSessionInfoForPackage(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.media.RoutingSessionInfo getRoutingSessionForMediaController(android.media.session.MediaController mediaController) {
        android.media.session.MediaController.PlaybackInfo playbackInfo = mediaController.getPlaybackInfo();
        if (playbackInfo == null) {
            return null;
        }
        if (playbackInfo.getPlaybackType() == 1) {
            return getSystemRoutingSession(mediaController.getPackageName());
        }
        for (android.media.RoutingSessionInfo routingSessionInfo : getRemoteSessions()) {
            if (areSessionsMatched(mediaController, routingSessionInfo)) {
                return routingSessionInfo;
            }
        }
        return null;
    }

    public java.util.List<android.media.RoutingSessionInfo> getRoutingSessions(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "packageName must not be null");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(getSystemRoutingSession(str));
        for (android.media.RoutingSessionInfo routingSessionInfo : getRemoteSessions()) {
            if (android.text.TextUtils.equals(routingSessionInfo.getClientPackageName(), str)) {
                arrayList.add(routingSessionInfo);
            }
        }
        return arrayList;
    }

    public java.util.List<android.media.RoutingSessionInfo> getRemoteSessions() {
        try {
            return this.mMediaRouterService.getRemoteSessions(this.mClient);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.media.MediaRoute2Info> getAllRoutes() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mRoutesLock) {
            arrayList.addAll(this.mRoutes.values());
        }
        return arrayList;
    }

    public void transfer(java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle) {
        java.util.Objects.requireNonNull(str, "packageName must not be null");
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        transfer(getRoutingSessions(str).get(r0.size() - 1), mediaRoute2Info, userHandle, str);
    }

    public void transfer(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) {
        java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        java.util.Objects.requireNonNull(userHandle);
        java.util.Objects.requireNonNull(str);
        android.util.Log.v(TAG, "Transferring routing session. session= " + routingSessionInfo + ", route=" + mediaRoute2Info);
        synchronized (this.mRoutesLock) {
            if (!this.mRoutes.containsKey(mediaRoute2Info.getId())) {
                android.util.Log.w(TAG, "transfer: Ignoring an unknown route id=" + mediaRoute2Info.getId());
                notifyTransferFailed(routingSessionInfo, mediaRoute2Info);
            } else if (routingSessionInfo.getTransferableRoutes().contains(mediaRoute2Info.getId())) {
                transferToRoute(routingSessionInfo, mediaRoute2Info, userHandle, str);
            } else {
                requestCreateSession(routingSessionInfo, mediaRoute2Info, userHandle, str);
            }
        }
    }

    public void setRouteVolume(android.media.MediaRoute2Info mediaRoute2Info, int i) {
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        if (mediaRoute2Info.getVolumeHandling() == 0) {
            android.util.Log.w(TAG, "setRouteVolume: the route has fixed volume. Ignoring.");
            return;
        }
        if (i < 0 || i > mediaRoute2Info.getVolumeMax()) {
            android.util.Log.w(TAG, "setRouteVolume: the target volume is out of range. Ignoring");
            return;
        }
        try {
            this.mMediaRouterService.setRouteVolumeWithManager(this.mClient, this.mNextRequestId.getAndIncrement(), mediaRoute2Info, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSessionVolume(android.media.RoutingSessionInfo routingSessionInfo, int i) {
        java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        if (routingSessionInfo.getVolumeHandling() == 0) {
            android.util.Log.w(TAG, "setSessionVolume: the route has fixed volume. Ignoring.");
            return;
        }
        if (i < 0 || i > routingSessionInfo.getVolumeMax()) {
            android.util.Log.w(TAG, "setSessionVolume: the target volume is out of range. Ignoring");
            return;
        }
        try {
            this.mMediaRouterService.setSessionVolumeWithManager(this.mClient, this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId(), i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void updateRoutesOnHandler(java.util.List<android.media.MediaRoute2Info> list) {
        synchronized (this.mRoutesLock) {
            this.mRoutes.clear();
            for (android.media.MediaRoute2Info mediaRoute2Info : list) {
                this.mRoutes.put(mediaRoute2Info.getId(), mediaRoute2Info);
            }
        }
        notifyRoutesUpdated();
    }

    void createSessionOnHandler(int i, android.media.RoutingSessionInfo routingSessionInfo) {
        android.media.MediaRouter2Manager.TransferRequest transferRequest;
        java.util.Iterator<android.media.MediaRouter2Manager.TransferRequest> it = this.mTransferRequests.iterator();
        while (true) {
            if (!it.hasNext()) {
                transferRequest = null;
                break;
            } else {
                transferRequest = it.next();
                if (transferRequest.mRequestId == i) {
                    break;
                }
            }
        }
        if (transferRequest == null) {
            return;
        }
        this.mTransferRequests.remove(transferRequest);
        android.media.MediaRoute2Info mediaRoute2Info = transferRequest.mTargetRoute;
        if (routingSessionInfo == null) {
            notifyTransferFailed(transferRequest.mOldSessionInfo, mediaRoute2Info);
            return;
        }
        if (!routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId())) {
            android.util.Log.w(TAG, "The session does not contain the requested route. (requestedRouteId=" + mediaRoute2Info.getId() + ", actualRoutes=" + routingSessionInfo.getSelectedRoutes() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            notifyTransferFailed(transferRequest.mOldSessionInfo, mediaRoute2Info);
        } else if (!android.text.TextUtils.equals(mediaRoute2Info.getProviderId(), routingSessionInfo.getProviderId())) {
            android.util.Log.w(TAG, "The session's provider ID does not match the requested route's. (requested route's providerId=" + mediaRoute2Info.getProviderId() + ", actual providerId=" + routingSessionInfo.getProviderId() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            notifyTransferFailed(transferRequest.mOldSessionInfo, mediaRoute2Info);
        } else {
            notifyTransferred(transferRequest.mOldSessionInfo, routingSessionInfo);
        }
    }

    void handleFailureOnHandler(int i, int i2) {
        android.media.MediaRouter2Manager.TransferRequest transferRequest;
        java.util.Iterator<android.media.MediaRouter2Manager.TransferRequest> it = this.mTransferRequests.iterator();
        while (true) {
            if (!it.hasNext()) {
                transferRequest = null;
                break;
            } else {
                transferRequest = it.next();
                if (transferRequest.mRequestId == i) {
                    break;
                }
            }
        }
        if (transferRequest != null) {
            this.mTransferRequests.remove(transferRequest);
            notifyTransferFailed(transferRequest.mOldSessionInfo, transferRequest.mTargetRoute);
        } else {
            notifyRequestFailed(i2);
        }
    }

    void handleSessionsUpdatedOnHandler(android.media.RoutingSessionInfo routingSessionInfo) {
        java.util.Iterator<android.media.MediaRouter2Manager.TransferRequest> it = this.mTransferRequests.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            android.media.MediaRouter2Manager.TransferRequest next = it.next();
            if (android.text.TextUtils.equals(next.mOldSessionInfo.getId(), routingSessionInfo.getId()) && routingSessionInfo.getSelectedRoutes().contains(next.mTargetRoute.getId())) {
                this.mTransferRequests.remove(next);
                notifyTransferred(next.mOldSessionInfo, routingSessionInfo);
                break;
            }
        }
        notifySessionUpdated(routingSessionInfo);
    }

    private void notifyRoutesUpdated() {
        java.util.Iterator<android.media.MediaRouter2Manager.CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2Manager.CallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2Manager.CallbackRecord.this.mCallback.onRoutesUpdated();
                }
            });
        }
    }

    void notifySessionUpdated(final android.media.RoutingSessionInfo routingSessionInfo) {
        java.util.Iterator<android.media.MediaRouter2Manager.CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2Manager.CallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2Manager.CallbackRecord.this.mCallback.onSessionUpdated(routingSessionInfo);
                }
            });
        }
    }

    void notifySessionReleased(final android.media.RoutingSessionInfo routingSessionInfo) {
        java.util.Iterator<android.media.MediaRouter2Manager.CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2Manager.CallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2Manager.CallbackRecord.this.mCallback.onSessionReleased(routingSessionInfo);
                }
            });
        }
    }

    void notifyRequestFailed(final int i) {
        java.util.Iterator<android.media.MediaRouter2Manager.CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2Manager.CallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2Manager.CallbackRecord.this.mCallback.onRequestFailed(i);
                }
            });
        }
    }

    void notifyTransferred(final android.media.RoutingSessionInfo routingSessionInfo, final android.media.RoutingSessionInfo routingSessionInfo2) {
        java.util.Iterator<android.media.MediaRouter2Manager.CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2Manager.CallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2Manager.CallbackRecord.this.mCallback.onTransferred(routingSessionInfo, routingSessionInfo2);
                }
            });
        }
    }

    void notifyTransferFailed(final android.media.RoutingSessionInfo routingSessionInfo, final android.media.MediaRoute2Info mediaRoute2Info) {
        java.util.Iterator<android.media.MediaRouter2Manager.CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2Manager.CallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2Manager.CallbackRecord.this.mCallback.onTransferFailed(routingSessionInfo, mediaRoute2Info);
                }
            });
        }
    }

    void updateDiscoveryPreference(final java.lang.String str, final android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
        if (routeDiscoveryPreference == null) {
            this.mDiscoveryPreferenceMap.remove(str);
        } else {
            if (java.util.Objects.equals(routeDiscoveryPreference, this.mDiscoveryPreferenceMap.put(str, routeDiscoveryPreference))) {
                return;
            }
            java.util.Iterator<android.media.MediaRouter2Manager.CallbackRecord> it = this.mCallbackRecords.iterator();
            while (it.hasNext()) {
                final android.media.MediaRouter2Manager.CallbackRecord next = it.next();
                next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.MediaRouter2Manager.CallbackRecord.this.mCallback.onDiscoveryPreferenceChanged(str, routeDiscoveryPreference);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRouteListingPreference(final java.lang.String str, final android.media.RouteListingPreference routeListingPreference) {
        android.media.RouteListingPreference put;
        if (routeListingPreference == null) {
            put = this.mPackageToRouteListingPreferenceMap.remove(str);
        } else {
            put = this.mPackageToRouteListingPreferenceMap.put(str, routeListingPreference);
        }
        if (java.util.Objects.equals(put, routeListingPreference)) {
            return;
        }
        java.util.Iterator<android.media.MediaRouter2Manager.CallbackRecord> it = this.mCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2Manager.CallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2Manager.CallbackRecord.this.mCallback.onRouteListingPreferenceUpdated(str, routeListingPreference);
                }
            });
        }
    }

    public java.util.List<android.media.MediaRoute2Info> getSelectedRoutes(android.media.RoutingSessionInfo routingSessionInfo) {
        java.util.List<android.media.MediaRoute2Info> list;
        java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        synchronized (this.mRoutesLock) {
            java.util.stream.Stream<java.lang.String> stream = routingSessionInfo.getSelectedRoutes().stream();
            java.util.Map<java.lang.String, android.media.MediaRoute2Info> map = this.mRoutes;
            java.util.Objects.requireNonNull(map);
            list = (java.util.List) stream.map(new android.media.MediaRouter2$$ExternalSyntheticLambda10(map)).filter(new android.media.MediaRouter2$$ExternalSyntheticLambda11()).collect(java.util.stream.Collectors.toList());
        }
        return list;
    }

    public java.util.List<android.media.MediaRoute2Info> getSelectableRoutes(android.media.RoutingSessionInfo routingSessionInfo) {
        java.util.List<android.media.MediaRoute2Info> list;
        java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        final java.util.List<java.lang.String> selectedRoutes = routingSessionInfo.getSelectedRoutes();
        synchronized (this.mRoutesLock) {
            java.util.stream.Stream<java.lang.String> filter = routingSessionInfo.getSelectableRoutes().stream().filter(new java.util.function.Predicate() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return android.media.MediaRouter2Manager.lambda$getSelectableRoutes$11(selectedRoutes, (java.lang.String) obj);
                }
            });
            java.util.Map<java.lang.String, android.media.MediaRoute2Info> map = this.mRoutes;
            java.util.Objects.requireNonNull(map);
            list = (java.util.List) filter.map(new android.media.MediaRouter2$$ExternalSyntheticLambda10(map)).filter(new android.media.MediaRouter2$$ExternalSyntheticLambda11()).collect(java.util.stream.Collectors.toList());
        }
        return list;
    }

    static /* synthetic */ boolean lambda$getSelectableRoutes$11(java.util.List list, java.lang.String str) {
        return !list.contains(str);
    }

    public java.util.List<android.media.MediaRoute2Info> getDeselectableRoutes(android.media.RoutingSessionInfo routingSessionInfo) {
        java.util.List<android.media.MediaRoute2Info> list;
        java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        final java.util.List<java.lang.String> selectedRoutes = routingSessionInfo.getSelectedRoutes();
        synchronized (this.mRoutesLock) {
            java.util.stream.Stream<java.lang.String> filter = routingSessionInfo.getDeselectableRoutes().stream().filter(new java.util.function.Predicate() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean contains;
                    contains = selectedRoutes.contains((java.lang.String) obj);
                    return contains;
                }
            });
            java.util.Map<java.lang.String, android.media.MediaRoute2Info> map = this.mRoutes;
            java.util.Objects.requireNonNull(map);
            list = (java.util.List) filter.map(new android.media.MediaRouter2$$ExternalSyntheticLambda10(map)).filter(new android.media.MediaRouter2$$ExternalSyntheticLambda11()).collect(java.util.stream.Collectors.toList());
        }
        return list;
    }

    public void selectRoute(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info) {
        java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        if (routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId())) {
            android.util.Log.w(TAG, "Ignoring selecting a route that is already selected. route=" + mediaRoute2Info);
            return;
        }
        if (!routingSessionInfo.getSelectableRoutes().contains(mediaRoute2Info.getId())) {
            android.util.Log.w(TAG, "Ignoring selecting a non-selectable route=" + mediaRoute2Info);
            return;
        }
        try {
            this.mMediaRouterService.selectRouteWithManager(this.mClient, this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId(), mediaRoute2Info);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void deselectRoute(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info) {
        java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        if (!routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId())) {
            android.util.Log.w(TAG, "Ignoring deselecting a route that is not selected. route=" + mediaRoute2Info);
            return;
        }
        if (!routingSessionInfo.getDeselectableRoutes().contains(mediaRoute2Info.getId())) {
            android.util.Log.w(TAG, "Ignoring deselecting a non-deselectable route=" + mediaRoute2Info);
            return;
        }
        try {
            this.mMediaRouterService.deselectRouteWithManager(this.mClient, this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId(), mediaRoute2Info);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseSession(android.media.RoutingSessionInfo routingSessionInfo) {
        java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
        try {
            this.mMediaRouterService.releaseSessionWithManager(this.mClient, this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void transferToRoute(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) {
        try {
            this.mMediaRouterService.transferToRouteWithManager(this.mClient, createTransferRequest(routingSessionInfo, mediaRoute2Info), routingSessionInfo.getId(), mediaRoute2Info, userHandle, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void requestCreateSession(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) {
        if (android.text.TextUtils.isEmpty(routingSessionInfo.getClientPackageName())) {
            android.util.Log.w(TAG, "requestCreateSession: Can't create a session without package name.");
            notifyTransferFailed(routingSessionInfo, mediaRoute2Info);
        } else {
            try {
                this.mMediaRouterService.requestCreateSessionWithManager(this.mClient, createTransferRequest(routingSessionInfo, mediaRoute2Info), routingSessionInfo, mediaRoute2Info, userHandle, str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private int createTransferRequest(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info) {
        int andIncrement = this.mNextRequestId.getAndIncrement();
        android.media.MediaRouter2Manager.TransferRequest transferRequest = new android.media.MediaRouter2Manager.TransferRequest(andIncrement, routingSessionInfo, mediaRoute2Info);
        this.mTransferRequests.add(transferRequest);
        this.mHandler.sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRouter2Manager$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((android.media.MediaRouter2Manager) obj).handleTransferTimeout((android.media.MediaRouter2Manager.TransferRequest) obj2);
            }
        }, this, transferRequest), 30000L);
        return andIncrement;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTransferTimeout(android.media.MediaRouter2Manager.TransferRequest transferRequest) {
        if (this.mTransferRequests.remove(transferRequest)) {
            notifyTransferFailed(transferRequest.mOldSessionInfo, transferRequest.mTargetRoute);
        }
    }

    private boolean areSessionsMatched(android.media.session.MediaController mediaController, android.media.RoutingSessionInfo routingSessionInfo) {
        java.lang.String volumeControlId;
        android.media.session.MediaController.PlaybackInfo playbackInfo = mediaController.getPlaybackInfo();
        if (playbackInfo == null || (volumeControlId = playbackInfo.getVolumeControlId()) == null) {
            return false;
        }
        if (android.text.TextUtils.equals(volumeControlId, routingSessionInfo.getId())) {
            return true;
        }
        return android.text.TextUtils.equals(volumeControlId, routingSessionInfo.getOriginalId()) && android.text.TextUtils.equals(mediaController.getPackageName(), routingSessionInfo.getOwnerPackageName());
    }

    public interface Callback {
        default void onRoutesUpdated() {
        }

        default void onSessionUpdated(android.media.RoutingSessionInfo routingSessionInfo) {
        }

        default void onSessionReleased(android.media.RoutingSessionInfo routingSessionInfo) {
        }

        default void onTransferred(android.media.RoutingSessionInfo routingSessionInfo, android.media.RoutingSessionInfo routingSessionInfo2) {
        }

        default void onTransferFailed(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info) {
        }

        default void onPreferredFeaturesChanged(java.lang.String str, java.util.List<java.lang.String> list) {
        }

        default void onDiscoveryPreferenceChanged(java.lang.String str, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
            onPreferredFeaturesChanged(str, routeDiscoveryPreference.getPreferredFeatures());
        }

        default void onRouteListingPreferenceUpdated(java.lang.String str, android.media.RouteListingPreference routeListingPreference) {
        }

        default void onRequestFailed(int i) {
        }
    }

    final class CallbackRecord {
        public final android.media.MediaRouter2Manager.Callback mCallback;
        public final java.util.concurrent.Executor mExecutor;

        CallbackRecord(java.util.concurrent.Executor executor, android.media.MediaRouter2Manager.Callback callback) {
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof android.media.MediaRouter2Manager.CallbackRecord) && this.mCallback == ((android.media.MediaRouter2Manager.CallbackRecord) obj).mCallback;
        }

        public int hashCode() {
            return this.mCallback.hashCode();
        }
    }

    static final class TransferRequest {
        public final android.media.RoutingSessionInfo mOldSessionInfo;
        public final int mRequestId;
        public final android.media.MediaRoute2Info mTargetRoute;

        TransferRequest(int i, android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info) {
            this.mRequestId = i;
            this.mOldSessionInfo = routingSessionInfo;
            this.mTargetRoute = mediaRoute2Info;
        }
    }

    class Client extends android.media.IMediaRouter2Manager.Stub {
        Client() {
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifySessionCreated(int i, android.media.RoutingSessionInfo routingSessionInfo) {
            android.media.MediaRouter2Manager.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.media.MediaRouter2Manager) obj).createSessionOnHandler(((java.lang.Integer) obj2).intValue(), (android.media.RoutingSessionInfo) obj3);
                }
            }, android.media.MediaRouter2Manager.this, java.lang.Integer.valueOf(i), routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifySessionUpdated(android.media.RoutingSessionInfo routingSessionInfo) {
            android.media.MediaRouter2Manager.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.media.MediaRouter2Manager) obj).handleSessionsUpdatedOnHandler((android.media.RoutingSessionInfo) obj2);
                }
            }, android.media.MediaRouter2Manager.this, routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifySessionReleased(android.media.RoutingSessionInfo routingSessionInfo) {
            android.media.MediaRouter2Manager.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.media.MediaRouter2Manager) obj).notifySessionReleased((android.media.RoutingSessionInfo) obj2);
                }
            }, android.media.MediaRouter2Manager.this, routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyRequestFailed(int i, int i2) {
            android.media.MediaRouter2Manager.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.media.MediaRouter2Manager) obj).handleFailureOnHandler(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue());
                }
            }, android.media.MediaRouter2Manager.this, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyDiscoveryPreferenceChanged(java.lang.String str, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
            android.media.MediaRouter2Manager.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.media.MediaRouter2Manager) obj).updateDiscoveryPreference((java.lang.String) obj2, (android.media.RouteDiscoveryPreference) obj3);
                }
            }, android.media.MediaRouter2Manager.this, str, routeDiscoveryPreference));
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyRouteListingPreferenceChange(java.lang.String str, android.media.RouteListingPreference routeListingPreference) {
            android.media.MediaRouter2Manager.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.media.MediaRouter2Manager) obj).updateRouteListingPreference((java.lang.String) obj2, (android.media.RouteListingPreference) obj3);
                }
            }, android.media.MediaRouter2Manager.this, str, routeListingPreference));
        }

        @Override // android.media.IMediaRouter2Manager
        public void notifyRoutesUpdated(java.util.List<android.media.MediaRoute2Info> list) {
            android.media.MediaRouter2Manager.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRouter2Manager$Client$$ExternalSyntheticLambda6
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.media.MediaRouter2Manager) obj).updateRoutesOnHandler((java.util.List) obj2);
                }
            }, android.media.MediaRouter2Manager.this, list));
        }
    }
}
