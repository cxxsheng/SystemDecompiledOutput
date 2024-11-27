package android.media;

/* loaded from: classes2.dex */
public final class MediaRouter2 {
    private static final long MANAGER_REQUEST_ID_NONE = 0;
    public static final int SCANNING_STATE_NOT_SCANNING = 0;
    public static final int SCANNING_STATE_SCANNING_FULL = 2;
    public static final int SCANNING_STATE_WHILE_INTERACTIVE = 1;
    private static final int TRANSFER_TIMEOUT_MS = 30000;
    private static android.media.MediaRouter2 sInstance;
    private final android.content.Context mContext;
    private final java.util.concurrent.CopyOnWriteArrayList<android.media.MediaRouter2.ControllerCallbackRecord> mControllerCallbackRecords;
    private final java.util.concurrent.CopyOnWriteArrayList<android.media.MediaRouter2.ControllerCreationRequest> mControllerCreationRequests;
    private android.media.RouteDiscoveryPreference mDiscoveryPreference;
    private volatile java.util.List<android.media.MediaRoute2Info> mFilteredRoutes;
    private final android.os.Handler mHandler;
    private final android.media.MediaRouter2.MediaRouter2Impl mImpl;
    private final java.util.concurrent.CopyOnWriteArrayList<android.media.MediaRouter2.RouteListingPreferenceCallbackRecord> mListingPreferenceCallbackRecords;
    private final java.lang.Object mLock;
    private final android.media.IMediaRouterService mMediaRouterService;
    private final java.util.concurrent.atomic.AtomicInteger mNextRequestId;
    private final java.util.Map<java.lang.String, android.media.MediaRouter2.RoutingController> mNonSystemRoutingControllers;
    private volatile android.media.MediaRouter2.OnGetControllerHintsListener mOnGetControllerHintsListener;
    private volatile android.util.ArrayMap<java.lang.String, android.media.MediaRoute2Info> mPreviousFilteredRoutes;
    private final java.util.Map<java.lang.String, android.media.MediaRoute2Info> mPreviousUnfilteredRoutes;
    private final java.util.concurrent.CopyOnWriteArrayList<android.media.MediaRouter2.RouteCallbackRecord> mRouteCallbackRecords;
    private android.media.RouteListingPreference mRouteListingPreference;
    private final java.util.Map<java.lang.String, android.media.MediaRoute2Info> mRoutes;
    private final android.util.SparseArray<android.media.MediaRouter2.ScanRequest> mScanRequestsMap;
    private int mScreenOffScanRequestCount;
    private int mScreenOnScanRequestCount;
    private android.media.MediaRouter2.MediaRouter2Stub mStub;
    private final android.media.MediaRouter2.RoutingController mSystemController;
    private final java.util.concurrent.CopyOnWriteArrayList<android.media.MediaRouter2.TransferCallbackRecord> mTransferCallbackRecords;
    private static final java.lang.String TAG = "MR2";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final java.lang.Object sSystemRouterLock = new java.lang.Object();
    private static final java.lang.Object sRouterLock = new java.lang.Object();
    private static final java.util.Map<android.media.MediaRouter2.PackageNameUserHandlePair, android.media.MediaRouter2> sAppToProxyRouterMap = new android.util.ArrayMap();

    private interface MediaRouter2Impl {
        android.media.MediaRouter2.RouteCallbackRecord createRouteCallbackRecord(java.util.concurrent.Executor executor, android.media.MediaRouter2.RouteCallback routeCallback, android.media.RouteDiscoveryPreference routeDiscoveryPreference);

        void deselectRoute(android.media.MediaRoute2Info mediaRoute2Info, android.media.RoutingSessionInfo routingSessionInfo);

        java.util.List<android.media.MediaRoute2Info> filterRoutesWithIndividualPreference(java.util.List<android.media.MediaRoute2Info> list, android.media.RouteDiscoveryPreference routeDiscoveryPreference);

        java.util.List<android.media.MediaRoute2Info> getAllRoutes();

        java.lang.String getClientPackageName();

        java.util.List<android.media.MediaRouter2.RoutingController> getControllers();

        java.lang.String getPackageName();

        android.media.RoutingSessionInfo getSystemSessionInfo();

        void registerRouteCallback();

        void releaseSession(boolean z, boolean z2, android.media.MediaRouter2.RoutingController routingController);

        void selectRoute(android.media.MediaRoute2Info mediaRoute2Info, android.media.RoutingSessionInfo routingSessionInfo);

        void setOnGetControllerHintsListener(android.media.MediaRouter2.OnGetControllerHintsListener onGetControllerHintsListener);

        void setRouteListingPreference(android.media.RouteListingPreference routeListingPreference);

        void setRouteVolume(android.media.MediaRoute2Info mediaRoute2Info, int i);

        void setSessionVolume(int i, android.media.RoutingSessionInfo routingSessionInfo);

        boolean showSystemOutputSwitcher();

        void startScan();

        void stop();

        void stopScan();

        void transfer(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str);

        void transferTo(android.media.MediaRoute2Info mediaRoute2Info);

        void unregisterRouteCallback();

        void updateScanningState(int i) throws android.os.RemoteException;
    }

    public interface OnGetControllerHintsListener {
        android.os.Bundle onGetControllerHints(android.media.MediaRoute2Info mediaRoute2Info);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScanningState {
    }

    private static final class PackageNameUserHandlePair extends java.lang.Record {
        private final java.lang.String packageName;
        private final android.os.UserHandle user;

        private PackageNameUserHandlePair(java.lang.String packageName, android.os.UserHandle user) {
            this.packageName = packageName;
            this.user = user;
        }

        @Override // java.lang.Record
        public final boolean equals(java.lang.Object obj) {
            return (boolean) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "equals", java.lang.invoke.MethodType.methodType(java.lang.Boolean.TYPE, android.media.MediaRouter2.PackageNameUserHandlePair.class, java.lang.Object.class), android.media.MediaRouter2.PackageNameUserHandlePair.class, "packageName;user", "FIELD:Landroid/media/MediaRouter2$PackageNameUserHandlePair;->packageName:Ljava/lang/String;", "FIELD:Landroid/media/MediaRouter2$PackageNameUserHandlePair;->user:Landroid/os/UserHandle;").dynamicInvoker().invoke(this, obj) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "hashCode", java.lang.invoke.MethodType.methodType(java.lang.Integer.TYPE, android.media.MediaRouter2.PackageNameUserHandlePair.class), android.media.MediaRouter2.PackageNameUserHandlePair.class, "packageName;user", "FIELD:Landroid/media/MediaRouter2$PackageNameUserHandlePair;->packageName:Ljava/lang/String;", "FIELD:Landroid/media/MediaRouter2$PackageNameUserHandlePair;->user:Landroid/os/UserHandle;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public java.lang.String packageName() {
            return this.packageName;
        }

        @Override // java.lang.Record
        public final java.lang.String toString() {
            return (java.lang.String) java.lang.runtime.ObjectMethods.bootstrap(java.lang.invoke.MethodHandles.lookup(), "toString", java.lang.invoke.MethodType.methodType(java.lang.String.class, android.media.MediaRouter2.PackageNameUserHandlePair.class), android.media.MediaRouter2.PackageNameUserHandlePair.class, "packageName;user", "FIELD:Landroid/media/MediaRouter2$PackageNameUserHandlePair;->packageName:Ljava/lang/String;", "FIELD:Landroid/media/MediaRouter2$PackageNameUserHandlePair;->user:Landroid/os/UserHandle;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public android.os.UserHandle user() {
            return this.user;
        }
    }

    public static android.media.MediaRouter2 getInstance(android.content.Context context) {
        android.media.MediaRouter2 mediaRouter2;
        java.util.Objects.requireNonNull(context, "context must not be null");
        synchronized (sRouterLock) {
            if (sInstance == null) {
                sInstance = new android.media.MediaRouter2(context.getApplicationContext());
            }
            mediaRouter2 = sInstance;
        }
        return mediaRouter2;
    }

    public static android.media.MediaRouter2 getInstance(android.content.Context context, java.lang.String str) {
        try {
            return findOrCreateProxyInstanceForCallingUser(context, str, context.getUser());
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.e(TAG, "Package " + str + " not found. Ignoring.");
            return null;
        }
    }

    public static android.media.MediaRouter2 getInstance(android.content.Context context, java.lang.String str, android.os.UserHandle userHandle) {
        return findOrCreateProxyInstanceForCallingUser(context, str, userHandle);
    }

    private static android.media.MediaRouter2 findOrCreateProxyInstanceForCallingUser(android.content.Context context, java.lang.String str, android.os.UserHandle userHandle) {
        android.media.MediaRouter2 mediaRouter2;
        java.util.Objects.requireNonNull(context, "context must not be null");
        java.util.Objects.requireNonNull(userHandle, "user must not be null");
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("clientPackageName must not be null or empty");
        }
        android.media.MediaRouter2.PackageNameUserHandlePair packageNameUserHandlePair = new android.media.MediaRouter2.PackageNameUserHandlePair(str, userHandle);
        synchronized (sSystemRouterLock) {
            mediaRouter2 = sAppToProxyRouterMap.get(packageNameUserHandlePair);
            if (mediaRouter2 == null) {
                mediaRouter2 = new android.media.MediaRouter2(context, android.os.Looper.getMainLooper(), str, userHandle);
                ((android.media.MediaRouter2.ProxyMediaRouter2Impl) mediaRouter2.mImpl).registerProxyRouter();
                sAppToProxyRouterMap.put(packageNameUserHandlePair, mediaRouter2);
            }
        }
        return mediaRouter2;
    }

    @android.annotation.SystemApi
    public void startScan() {
        this.mImpl.startScan();
    }

    @android.annotation.SystemApi
    public void stopScan() {
        this.mImpl.stopScan();
    }

    public android.media.MediaRouter2.ScanToken requestScan(android.media.MediaRouter2.ScanRequest scanRequest) {
        int i;
        java.util.Objects.requireNonNull(scanRequest, "scanRequest must not be null.");
        android.media.MediaRouter2.ScanToken scanToken = new android.media.MediaRouter2.ScanToken(this.mNextRequestId.getAndIncrement());
        synchronized (this.mLock) {
            if (this.mScreenOffScanRequestCount == 0 && (scanRequest.isScreenOffScan() || this.mScreenOnScanRequestCount == 0)) {
                try {
                    android.media.MediaRouter2.MediaRouter2Impl mediaRouter2Impl = this.mImpl;
                    if (scanRequest.isScreenOffScan()) {
                        i = 2;
                    } else {
                        i = 1;
                    }
                    mediaRouter2Impl.updateScanningState(i);
                    if (scanRequest.isScreenOffScan()) {
                        this.mScreenOffScanRequestCount++;
                    } else {
                        this.mScreenOnScanRequestCount++;
                    }
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            this.mScanRequestsMap.put(scanToken.mId, scanRequest);
        }
        return scanToken;
    }

    public void cancelScanRequest(android.media.MediaRouter2.ScanToken scanToken) {
        java.util.Objects.requireNonNull(scanToken, "token must not be null");
        synchronized (this.mLock) {
            android.media.MediaRouter2.ScanRequest scanRequest = this.mScanRequestsMap.get(scanToken.mId);
            if (scanRequest == null) {
                throw new java.lang.IllegalArgumentException("The token does not match any active scan request");
            }
            if (this.mScreenOffScanRequestCount == 1 && (scanRequest.isScreenOffScan() || this.mScreenOnScanRequestCount == 1)) {
                try {
                    if (scanRequest.isScreenOffScan() && this.mScreenOnScanRequestCount == 0) {
                        this.mImpl.updateScanningState(0);
                    } else {
                        this.mImpl.updateScanningState(1);
                    }
                    if (scanRequest.isScreenOffScan()) {
                        this.mScreenOffScanRequestCount--;
                    } else {
                        this.mScreenOnScanRequestCount--;
                    }
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
            this.mScanRequestsMap.remove(scanToken.mId);
        }
    }

    private MediaRouter2(android.content.Context context) {
        this.mLock = new java.lang.Object();
        this.mRouteCallbackRecords = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mListingPreferenceCallbackRecords = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mTransferCallbackRecords = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mControllerCallbackRecords = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mControllerCreationRequests = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mRoutes = new android.util.ArrayMap();
        this.mNonSystemRoutingControllers = new android.util.ArrayMap();
        this.mScreenOffScanRequestCount = 0;
        this.mScreenOnScanRequestCount = 0;
        this.mScanRequestsMap = new android.util.SparseArray<>();
        this.mNextRequestId = new java.util.concurrent.atomic.AtomicInteger(1);
        this.mDiscoveryPreference = android.media.RouteDiscoveryPreference.EMPTY;
        this.mPreviousFilteredRoutes = new android.util.ArrayMap<>();
        this.mPreviousUnfilteredRoutes = new android.util.ArrayMap();
        this.mFilteredRoutes = java.util.Collections.emptyList();
        this.mContext = context;
        this.mMediaRouterService = android.media.IMediaRouterService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.MEDIA_ROUTER_SERVICE));
        this.mImpl = new android.media.MediaRouter2.LocalMediaRouter2Impl(this.mContext.getPackageName());
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        loadSystemRoutes();
        android.media.RoutingSessionInfo systemSessionInfo = this.mImpl.getSystemSessionInfo();
        if (systemSessionInfo == null) {
            throw new java.lang.RuntimeException("Null currentSystemSessionInfo. Something is wrong.");
        }
        this.mSystemController = new android.media.MediaRouter2.SystemRoutingController(systemSessionInfo);
    }

    private MediaRouter2(android.content.Context context, android.os.Looper looper, java.lang.String str, android.os.UserHandle userHandle) {
        this.mLock = new java.lang.Object();
        this.mRouteCallbackRecords = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mListingPreferenceCallbackRecords = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mTransferCallbackRecords = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mControllerCallbackRecords = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mControllerCreationRequests = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mRoutes = new android.util.ArrayMap();
        this.mNonSystemRoutingControllers = new android.util.ArrayMap();
        this.mScreenOffScanRequestCount = 0;
        this.mScreenOnScanRequestCount = 0;
        this.mScanRequestsMap = new android.util.SparseArray<>();
        this.mNextRequestId = new java.util.concurrent.atomic.AtomicInteger(1);
        this.mDiscoveryPreference = android.media.RouteDiscoveryPreference.EMPTY;
        this.mPreviousFilteredRoutes = new android.util.ArrayMap<>();
        this.mPreviousUnfilteredRoutes = new android.util.ArrayMap();
        this.mFilteredRoutes = java.util.Collections.emptyList();
        this.mContext = context;
        this.mHandler = new android.os.Handler(looper);
        this.mMediaRouterService = android.media.IMediaRouterService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.MEDIA_ROUTER_SERVICE));
        loadSystemRoutes();
        this.mSystemController = new android.media.MediaRouter2.SystemRoutingController(android.media.MediaRouter2.ProxyMediaRouter2Impl.getSystemSessionInfoImpl(this.mMediaRouterService, str));
        this.mImpl = new android.media.MediaRouter2.ProxyMediaRouter2Impl(context, str, userHandle);
    }

    private void loadSystemRoutes() {
        java.util.List<android.media.MediaRoute2Info> list;
        try {
            list = this.mMediaRouterService.getSystemRoutes();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            list = null;
        }
        if (list == null || list.isEmpty()) {
            throw new java.lang.RuntimeException("Null or empty currentSystemRoutes. Something is wrong.");
        }
        for (android.media.MediaRoute2Info mediaRoute2Info : list) {
            this.mRoutes.put(mediaRoute2Info.getId(), mediaRoute2Info);
        }
    }

    @android.annotation.SystemApi
    public java.lang.String getClientPackageName() {
        return this.mImpl.getClientPackageName();
    }

    public void registerRouteCallback(java.util.concurrent.Executor executor, android.media.MediaRouter2.RouteCallback routeCallback, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(routeCallback, "callback must not be null");
        java.util.Objects.requireNonNull(routeDiscoveryPreference, "preference must not be null");
        android.media.MediaRouter2.RouteCallbackRecord createRouteCallbackRecord = this.mImpl.createRouteCallbackRecord(executor, routeCallback, routeDiscoveryPreference);
        this.mRouteCallbackRecords.remove(createRouteCallbackRecord);
        this.mRouteCallbackRecords.addIfAbsent(createRouteCallbackRecord);
        this.mImpl.registerRouteCallback();
    }

    public void unregisterRouteCallback(android.media.MediaRouter2.RouteCallback routeCallback) {
        java.util.Objects.requireNonNull(routeCallback, "callback must not be null");
        if (!this.mRouteCallbackRecords.remove(new android.media.MediaRouter2.RouteCallbackRecord(null, routeCallback, null))) {
            android.util.Log.w(TAG, "unregisterRouteCallback: Ignoring unknown callback");
        } else {
            this.mImpl.unregisterRouteCallback();
        }
    }

    public void registerRouteListingPreferenceUpdatedCallback(java.util.concurrent.Executor executor, java.util.function.Consumer<android.media.RouteListingPreference> consumer) {
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(consumer, "callback must not be null");
        android.media.MediaRouter2.RouteListingPreferenceCallbackRecord routeListingPreferenceCallbackRecord = new android.media.MediaRouter2.RouteListingPreferenceCallbackRecord(executor, consumer);
        this.mListingPreferenceCallbackRecords.remove(routeListingPreferenceCallbackRecord);
        this.mListingPreferenceCallbackRecords.add(routeListingPreferenceCallbackRecord);
    }

    public void unregisterRouteListingPreferenceUpdatedCallback(java.util.function.Consumer<android.media.RouteListingPreference> consumer) {
        java.util.Objects.requireNonNull(consumer, "callback must not be null");
        if (!this.mListingPreferenceCallbackRecords.remove(new android.media.MediaRouter2.RouteListingPreferenceCallbackRecord(null, consumer))) {
            android.util.Log.w(TAG, "unregisterRouteListingPreferenceUpdatedCallback: Ignoring an unknown callback");
        }
    }

    public boolean showSystemOutputSwitcher() {
        return this.mImpl.showSystemOutputSwitcher();
    }

    public void setRouteListingPreference(android.media.RouteListingPreference routeListingPreference) {
        this.mImpl.setRouteListingPreference(routeListingPreference);
    }

    public android.media.RouteListingPreference getRouteListingPreference() {
        android.media.RouteListingPreference routeListingPreference;
        synchronized (this.mLock) {
            routeListingPreference = this.mRouteListingPreference;
        }
        return routeListingPreference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateDiscoveryPreferenceIfNeededLocked() {
        android.media.RouteDiscoveryPreference build = new android.media.RouteDiscoveryPreference.Builder((java.util.Collection<android.media.RouteDiscoveryPreference>) this.mRouteCallbackRecords.stream().map(new java.util.function.Function() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda17
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.media.RouteDiscoveryPreference routeDiscoveryPreference;
                routeDiscoveryPreference = ((android.media.MediaRouter2.RouteCallbackRecord) obj).mPreference;
                return routeDiscoveryPreference;
            }
        }).collect(java.util.stream.Collectors.toList())).build();
        if (java.util.Objects.equals(this.mDiscoveryPreference, build)) {
            return false;
        }
        this.mDiscoveryPreference = build;
        updateFilteredRoutesLocked();
        return true;
    }

    @android.annotation.SystemApi
    public java.util.List<android.media.MediaRoute2Info> getAllRoutes() {
        return this.mImpl.getAllRoutes();
    }

    public java.util.List<android.media.MediaRoute2Info> getRoutes() {
        java.util.List<android.media.MediaRoute2Info> list;
        synchronized (this.mLock) {
            list = this.mFilteredRoutes;
        }
        return list;
    }

    public void registerTransferCallback(java.util.concurrent.Executor executor, android.media.MediaRouter2.TransferCallback transferCallback) {
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(transferCallback, "callback must not be null");
        if (!this.mTransferCallbackRecords.addIfAbsent(new android.media.MediaRouter2.TransferCallbackRecord(executor, transferCallback))) {
            android.util.Log.w(TAG, "registerTransferCallback: Ignoring the same callback");
        }
    }

    public void unregisterTransferCallback(android.media.MediaRouter2.TransferCallback transferCallback) {
        java.util.Objects.requireNonNull(transferCallback, "callback must not be null");
        if (!this.mTransferCallbackRecords.remove(new android.media.MediaRouter2.TransferCallbackRecord(null, transferCallback))) {
            android.util.Log.w(TAG, "unregisterTransferCallback: Ignoring an unknown callback");
        }
    }

    public void registerControllerCallback(java.util.concurrent.Executor executor, android.media.MediaRouter2.ControllerCallback controllerCallback) {
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(controllerCallback, "callback must not be null");
        if (!this.mControllerCallbackRecords.addIfAbsent(new android.media.MediaRouter2.ControllerCallbackRecord(executor, controllerCallback))) {
            android.util.Log.w(TAG, "registerControllerCallback: Ignoring the same callback");
        }
    }

    public void unregisterControllerCallback(android.media.MediaRouter2.ControllerCallback controllerCallback) {
        java.util.Objects.requireNonNull(controllerCallback, "callback must not be null");
        if (!this.mControllerCallbackRecords.remove(new android.media.MediaRouter2.ControllerCallbackRecord(null, controllerCallback))) {
            android.util.Log.w(TAG, "unregisterControllerCallback: Ignoring an unknown callback");
        }
    }

    public void setOnGetControllerHintsListener(android.media.MediaRouter2.OnGetControllerHintsListener onGetControllerHintsListener) {
        this.mImpl.setOnGetControllerHintsListener(onGetControllerHintsListener);
    }

    public void transferTo(android.media.MediaRoute2Info mediaRoute2Info) {
        this.mImpl.transferTo(mediaRoute2Info);
    }

    public void stop() {
        this.mImpl.stop();
    }

    @android.annotation.SystemApi
    public void transfer(android.media.MediaRouter2.RoutingController routingController, android.media.MediaRoute2Info mediaRoute2Info) {
        this.mImpl.transfer(routingController.getRoutingSessionInfo(), mediaRoute2Info, android.os.Process.myUserHandle(), this.mContext.getPackageName());
    }

    public void transfer(android.media.MediaRouter2.RoutingController routingController, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) {
        this.mImpl.transfer(routingController.getRoutingSessionInfo(), mediaRoute2Info, userHandle, str);
    }

    void requestCreateController(android.media.MediaRouter2.RoutingController routingController, android.media.MediaRoute2Info mediaRoute2Info, long j, android.os.UserHandle userHandle, java.lang.String str) {
        android.os.Bundle bundle;
        android.media.MediaRouter2.MediaRouter2Stub mediaRouter2Stub;
        int andIncrement = this.mNextRequestId.getAndIncrement();
        android.media.MediaRouter2.ControllerCreationRequest controllerCreationRequest = new android.media.MediaRouter2.ControllerCreationRequest(andIncrement, j, mediaRoute2Info, routingController);
        this.mControllerCreationRequests.add(controllerCreationRequest);
        android.media.MediaRouter2.OnGetControllerHintsListener onGetControllerHintsListener = this.mOnGetControllerHintsListener;
        if (onGetControllerHintsListener == null) {
            bundle = null;
        } else {
            android.os.Bundle onGetControllerHints = onGetControllerHintsListener.onGetControllerHints(mediaRoute2Info);
            if (onGetControllerHints == null) {
                bundle = onGetControllerHints;
            } else {
                bundle = new android.os.Bundle(onGetControllerHints);
            }
        }
        synchronized (this.mLock) {
            mediaRouter2Stub = this.mStub;
        }
        if (mediaRouter2Stub != null) {
            try {
                this.mMediaRouterService.requestCreateSessionWithRouter2(mediaRouter2Stub, andIncrement, j, routingController.getRoutingSessionInfo(), mediaRoute2Info, bundle, userHandle, str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "createControllerForTransfer: Failed to request for creating a controller.", e);
                this.mControllerCreationRequests.remove(controllerCreationRequest);
                if (j == 0) {
                    notifyTransferFailure(mediaRoute2Info);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.media.MediaRouter2.RoutingController getCurrentController() {
        return getControllers().get(r0.size() - 1);
    }

    public android.media.MediaRouter2.RoutingController getSystemController() {
        return this.mSystemController;
    }

    public android.media.MediaRouter2.RoutingController getController(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "id must not be null");
        for (android.media.MediaRouter2.RoutingController routingController : getControllers()) {
            if (android.text.TextUtils.equals(str, routingController.getId())) {
                return routingController;
            }
        }
        return null;
    }

    public java.util.List<android.media.MediaRouter2.RoutingController> getControllers() {
        return this.mImpl.getControllers();
    }

    public void setRouteVolume(android.media.MediaRoute2Info mediaRoute2Info, int i) {
        java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        this.mImpl.setRouteVolume(mediaRoute2Info, i);
    }

    void syncRoutesOnHandler(java.util.List<android.media.MediaRoute2Info> list, android.media.RoutingSessionInfo routingSessionInfo) {
        if (list == null || list.isEmpty() || routingSessionInfo == null) {
            android.util.Log.e(TAG, "syncRoutesOnHandler: Received wrong data. currentRoutes=" + list + ", currentSystemSessionInfo=" + routingSessionInfo);
            return;
        }
        updateRoutesOnHandler(list);
        android.media.RoutingSessionInfo routingSessionInfo2 = this.mSystemController.getRoutingSessionInfo();
        this.mSystemController.setRoutingSessionInfo(ensureClientPackageNameForSystemSession(routingSessionInfo, this.mContext.getPackageName()));
        if (!routingSessionInfo2.equals(routingSessionInfo)) {
            notifyControllerUpdated(this.mSystemController);
        }
    }

    void dispatchFilteredRoutesUpdatedOnHandler(java.util.List<android.media.MediaRoute2Info> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        java.util.Set set = (java.util.Set) list.stream().map(new android.media.MediaRouter2$$ExternalSyntheticLambda16()).collect(java.util.stream.Collectors.toSet());
        for (android.media.MediaRoute2Info mediaRoute2Info : list) {
            android.media.MediaRoute2Info mediaRoute2Info2 = this.mPreviousFilteredRoutes.get(mediaRoute2Info.getId());
            if (mediaRoute2Info2 == null) {
                arrayList.add(mediaRoute2Info);
            } else if (!mediaRoute2Info2.equals(mediaRoute2Info)) {
                arrayList3.add(mediaRoute2Info);
            }
        }
        for (int i = 0; i < this.mPreviousFilteredRoutes.size(); i++) {
            if (!set.contains(this.mPreviousFilteredRoutes.keyAt(i))) {
                arrayList2.add(this.mPreviousFilteredRoutes.valueAt(i));
            }
        }
        java.util.Iterator<android.media.MediaRoute2Info> it = arrayList2.iterator();
        while (it.hasNext()) {
            this.mPreviousFilteredRoutes.remove(it.next().getId());
        }
        for (android.media.MediaRoute2Info mediaRoute2Info3 : arrayList) {
            this.mPreviousFilteredRoutes.put(mediaRoute2Info3.getId(), mediaRoute2Info3);
        }
        for (android.media.MediaRoute2Info mediaRoute2Info4 : arrayList3) {
            this.mPreviousFilteredRoutes.put(mediaRoute2Info4.getId(), mediaRoute2Info4);
        }
        if (!arrayList.isEmpty()) {
            notifyRoutesAdded(arrayList);
        }
        if (!arrayList2.isEmpty()) {
            notifyRoutesRemoved(arrayList2);
        }
        if (!arrayList3.isEmpty()) {
            notifyRoutesChanged(arrayList3);
        }
        if (!arrayList.isEmpty() || !arrayList2.isEmpty() || !arrayList3.isEmpty()) {
            notifyRoutesUpdated(list);
        }
    }

    void dispatchControllerUpdatedIfNeededOnHandler(java.util.Map<java.lang.String, android.media.MediaRoute2Info> map) {
        for (android.media.MediaRouter2.RoutingController routingController : getControllers()) {
            java.util.Iterator<java.lang.String> it = routingController.getRoutingSessionInfo().getSelectedRoutes().iterator();
            while (true) {
                if (it.hasNext()) {
                    java.lang.String next = it.next();
                    if (map.containsKey(next) && this.mPreviousUnfilteredRoutes.containsKey(next) && !map.get(next).equals(this.mPreviousUnfilteredRoutes.get(next))) {
                        notifyControllerUpdated(routingController);
                        break;
                    }
                }
            }
        }
        this.mPreviousUnfilteredRoutes.clear();
        this.mPreviousUnfilteredRoutes.putAll(map);
    }

    void updateRoutesOnHandler(java.util.List<android.media.MediaRoute2Info> list) {
        synchronized (this.mLock) {
            this.mRoutes.clear();
            for (android.media.MediaRoute2Info mediaRoute2Info : list) {
                this.mRoutes.put(mediaRoute2Info.getId(), mediaRoute2Info);
            }
            updateFilteredRoutesLocked();
        }
    }

    void updateFilteredRoutesLocked() {
        this.mFilteredRoutes = java.util.Collections.unmodifiableList(filterRoutesWithCompositePreferenceLocked(java.util.List.copyOf(this.mRoutes.values())));
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((android.media.MediaRouter2) obj).dispatchFilteredRoutesUpdatedOnHandler((java.util.List) obj2);
            }
        }, this, this.mFilteredRoutes));
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((android.media.MediaRouter2) obj).dispatchControllerUpdatedIfNeededOnHandler((java.util.HashMap) obj2);
            }
        }, this, new java.util.HashMap(this.mRoutes)));
    }

    void createControllerOnHandler(int i, android.media.RoutingSessionInfo routingSessionInfo) {
        android.media.MediaRouter2.ControllerCreationRequest controllerCreationRequest;
        android.media.MediaRouter2.RoutingController routingController;
        java.util.Iterator<android.media.MediaRouter2.ControllerCreationRequest> it = this.mControllerCreationRequests.iterator();
        while (true) {
            if (!it.hasNext()) {
                controllerCreationRequest = null;
                break;
            } else {
                controllerCreationRequest = it.next();
                if (controllerCreationRequest.mRequestId == i) {
                    break;
                }
            }
        }
        if (controllerCreationRequest == null) {
            android.util.Log.w(TAG, "createControllerOnHandler: Ignoring an unknown request.");
            return;
        }
        this.mControllerCreationRequests.remove(controllerCreationRequest);
        android.media.MediaRoute2Info mediaRoute2Info = controllerCreationRequest.mRoute;
        if (routingSessionInfo == null) {
            notifyTransferFailure(mediaRoute2Info);
            return;
        }
        if (!android.text.TextUtils.equals(mediaRoute2Info.getProviderId(), routingSessionInfo.getProviderId())) {
            android.util.Log.w(TAG, "The session's provider ID does not match the requested route's. (requested route's providerId=" + mediaRoute2Info.getProviderId() + ", actual providerId=" + routingSessionInfo.getProviderId() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            notifyTransferFailure(mediaRoute2Info);
            return;
        }
        android.media.MediaRouter2.RoutingController routingController2 = controllerCreationRequest.mOldController;
        if (!routingController2.scheduleRelease()) {
            android.util.Log.w(TAG, "createControllerOnHandler: Ignoring controller creation for released old controller. oldController=" + routingController2);
            if (!routingSessionInfo.isSystemSession()) {
                new android.media.MediaRouter2.RoutingController(routingSessionInfo).release();
            }
            notifyTransferFailure(mediaRoute2Info);
            return;
        }
        if (routingSessionInfo.isSystemSession()) {
            routingController = getSystemController();
            routingController.setRoutingSessionInfo(routingSessionInfo);
        } else {
            routingController = new android.media.MediaRouter2.RoutingController(routingSessionInfo);
            synchronized (this.mLock) {
                this.mNonSystemRoutingControllers.put(routingController.getId(), routingController);
            }
        }
        notifyTransfer(routingController2, routingController);
    }

    void updateControllerOnHandler(android.media.RoutingSessionInfo routingSessionInfo) {
        android.media.MediaRouter2.RoutingController routingController;
        if (routingSessionInfo == null) {
            android.util.Log.w(TAG, "updateControllerOnHandler: Ignoring null sessionInfo.");
            return;
        }
        if (routingSessionInfo.isSystemSession()) {
            android.media.MediaRouter2.RoutingController systemController = getSystemController();
            systemController.setRoutingSessionInfo(routingSessionInfo);
            notifyControllerUpdated(systemController);
            return;
        }
        synchronized (this.mLock) {
            routingController = this.mNonSystemRoutingControllers.get(routingSessionInfo.getId());
        }
        if (routingController == null) {
            android.util.Log.w(TAG, "updateControllerOnHandler: Matching controller not found. uniqueSessionId=" + routingSessionInfo.getId());
            return;
        }
        android.media.RoutingSessionInfo routingSessionInfo2 = routingController.getRoutingSessionInfo();
        if (!android.text.TextUtils.equals(routingSessionInfo2.getProviderId(), routingSessionInfo.getProviderId())) {
            android.util.Log.w(TAG, "updateControllerOnHandler: Provider IDs are not matched. old=" + routingSessionInfo2.getProviderId() + ", new=" + routingSessionInfo.getProviderId());
        } else {
            routingController.setRoutingSessionInfo(routingSessionInfo);
            notifyControllerUpdated(routingController);
        }
    }

    void releaseControllerOnHandler(android.media.RoutingSessionInfo routingSessionInfo) {
        android.media.MediaRouter2.RoutingController routingController;
        if (routingSessionInfo == null) {
            android.util.Log.w(TAG, "releaseControllerOnHandler: Ignoring null sessionInfo.");
            return;
        }
        synchronized (this.mLock) {
            routingController = this.mNonSystemRoutingControllers.get(routingSessionInfo.getId());
        }
        if (routingController == null) {
            if (DEBUG) {
                android.util.Log.d(TAG, "releaseControllerOnHandler: Matching controller not found. uniqueSessionId=" + routingSessionInfo.getId());
            }
        } else {
            android.media.RoutingSessionInfo routingSessionInfo2 = routingController.getRoutingSessionInfo();
            if (!android.text.TextUtils.equals(routingSessionInfo2.getProviderId(), routingSessionInfo.getProviderId())) {
                android.util.Log.w(TAG, "releaseControllerOnHandler: Provider IDs are not matched. old=" + routingSessionInfo2.getProviderId() + ", new=" + routingSessionInfo.getProviderId());
            } else {
                routingController.releaseInternal(false);
            }
        }
    }

    void onRequestCreateControllerByManagerOnHandler(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, long j, android.os.UserHandle userHandle, java.lang.String str) {
        android.media.MediaRouter2.RoutingController routingController;
        android.media.MediaRouter2.RoutingController routingController2;
        android.util.Log.i(TAG, android.text.TextUtils.formatSimple("requestCreateSessionByManager | requestId: %d, oldSession: %s, route: %s", java.lang.Long.valueOf(j), routingSessionInfo, mediaRoute2Info));
        if (routingSessionInfo.isSystemSession()) {
            routingController2 = getSystemController();
        } else {
            synchronized (this.mLock) {
                routingController = this.mNonSystemRoutingControllers.get(routingSessionInfo.getId());
            }
            routingController2 = routingController;
        }
        if (routingController2 == null) {
            return;
        }
        requestCreateController(routingController2, mediaRoute2Info, j, userHandle, str);
    }

    private java.util.List<android.media.MediaRoute2Info> getSortedRoutes(java.util.List<android.media.MediaRoute2Info> list, java.util.List<java.lang.String> list2) {
        if (list2.isEmpty()) {
            return list;
        }
        final android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        int size = list2.size();
        for (int i = 0; i < size; i++) {
            arrayMap.put(list2.get(i), java.lang.Integer.valueOf(size - i));
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(list);
        arrayList.sort(java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                return android.media.MediaRouter2.lambda$getSortedRoutes$1(arrayMap, (android.media.MediaRoute2Info) obj);
            }
        }));
        return arrayList;
    }

    static /* synthetic */ int lambda$getSortedRoutes$1(java.util.Map map, android.media.MediaRoute2Info mediaRoute2Info) {
        return -((java.lang.Integer) map.getOrDefault(mediaRoute2Info.getPackageName(), 0)).intValue();
    }

    private java.util.List<android.media.MediaRoute2Info> filterRoutesWithCompositePreferenceLocked(java.util.List<android.media.MediaRoute2Info> list) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.media.MediaRoute2Info mediaRoute2Info : getSortedRoutes(list, this.mDiscoveryPreference.getDeduplicationPackageOrder())) {
            if (mediaRoute2Info.hasAnyFeatures(this.mDiscoveryPreference.getPreferredFeatures()) && (this.mDiscoveryPreference.getAllowedPackages().isEmpty() || (mediaRoute2Info.getPackageName() != null && this.mDiscoveryPreference.getAllowedPackages().contains(mediaRoute2Info.getPackageName())))) {
                if (this.mDiscoveryPreference.shouldRemoveDuplicates()) {
                    if (java.util.Collections.disjoint(arraySet, mediaRoute2Info.getDeduplicationIds())) {
                        arraySet.addAll(mediaRoute2Info.getDeduplicationIds());
                    }
                }
                arrayList.add(mediaRoute2Info);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.media.MediaRoute2Info> getRoutesWithIds(java.util.List<java.lang.String> list) {
        java.util.List<android.media.MediaRoute2Info> list2;
        synchronized (this.mLock) {
            java.util.stream.Stream<java.lang.String> stream = list.stream();
            java.util.Map<java.lang.String, android.media.MediaRoute2Info> map = this.mRoutes;
            java.util.Objects.requireNonNull(map);
            list2 = (java.util.List) stream.map(new android.media.MediaRouter2$$ExternalSyntheticLambda10(map)).filter(new android.media.MediaRouter2$$ExternalSyntheticLambda11()).collect(java.util.stream.Collectors.toList());
        }
        return list2;
    }

    private void notifyRoutesAdded(java.util.List<android.media.MediaRoute2Info> list) {
        java.util.Iterator<android.media.MediaRouter2.RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2.RouteCallbackRecord next = it.next();
            final java.util.List<android.media.MediaRoute2Info> filterRoutesWithIndividualPreference = this.mImpl.filterRoutesWithIndividualPreference(list, next.mPreference);
            if (!filterRoutesWithIndividualPreference.isEmpty()) {
                next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.MediaRouter2.RouteCallbackRecord.this.mRouteCallback.onRoutesAdded(filterRoutesWithIndividualPreference);
                    }
                });
            }
        }
    }

    private void notifyRoutesRemoved(java.util.List<android.media.MediaRoute2Info> list) {
        java.util.Iterator<android.media.MediaRouter2.RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2.RouteCallbackRecord next = it.next();
            final java.util.List<android.media.MediaRoute2Info> filterRoutesWithIndividualPreference = this.mImpl.filterRoutesWithIndividualPreference(list, next.mPreference);
            if (!filterRoutesWithIndividualPreference.isEmpty()) {
                next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.MediaRouter2.RouteCallbackRecord.this.mRouteCallback.onRoutesRemoved(filterRoutesWithIndividualPreference);
                    }
                });
            }
        }
    }

    private void notifyRoutesChanged(java.util.List<android.media.MediaRoute2Info> list) {
        java.util.Iterator<android.media.MediaRouter2.RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2.RouteCallbackRecord next = it.next();
            final java.util.List<android.media.MediaRoute2Info> filterRoutesWithIndividualPreference = this.mImpl.filterRoutesWithIndividualPreference(list, next.mPreference);
            if (!filterRoutesWithIndividualPreference.isEmpty()) {
                next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.MediaRouter2.RouteCallbackRecord.this.mRouteCallback.onRoutesChanged(filterRoutesWithIndividualPreference);
                    }
                });
            }
        }
    }

    private void notifyRoutesUpdated(java.util.List<android.media.MediaRoute2Info> list) {
        java.util.Iterator<android.media.MediaRouter2.RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2.RouteCallbackRecord next = it.next();
            final java.util.List<android.media.MediaRoute2Info> filterRoutesWithIndividualPreference = this.mImpl.filterRoutesWithIndividualPreference(list, next.mPreference);
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2.RouteCallbackRecord.this.mRouteCallback.onRoutesUpdated(filterRoutesWithIndividualPreference);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyPreferredFeaturesChanged(final java.util.List<java.lang.String> list) {
        java.util.Iterator<android.media.MediaRouter2.RouteCallbackRecord> it = this.mRouteCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2.RouteCallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2.RouteCallbackRecord.this.mRouteCallback.onPreferredFeaturesChanged(list);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRouteListingPreferenceUpdated(final android.media.RouteListingPreference routeListingPreference) {
        java.util.Iterator<android.media.MediaRouter2.RouteListingPreferenceCallbackRecord> it = this.mListingPreferenceCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2.RouteListingPreferenceCallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2.RouteListingPreferenceCallbackRecord.this.mRouteListingPreferenceCallback.accept(routeListingPreference);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTransfer(final android.media.MediaRouter2.RoutingController routingController, final android.media.MediaRouter2.RoutingController routingController2) {
        java.util.Iterator<android.media.MediaRouter2.TransferCallbackRecord> it = this.mTransferCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2.TransferCallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2.TransferCallbackRecord.this.mTransferCallback.onTransfer(routingController, routingController2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTransferFailure(final android.media.MediaRoute2Info mediaRoute2Info) {
        java.util.Iterator<android.media.MediaRouter2.TransferCallbackRecord> it = this.mTransferCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2.TransferCallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2.TransferCallbackRecord.this.mTransferCallback.onTransferFailure(mediaRoute2Info);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyRequestFailed(final int i) {
        java.util.Iterator<android.media.MediaRouter2.TransferCallbackRecord> it = this.mTransferCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2.TransferCallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2.TransferCallbackRecord.this.mTransferCallback.onRequestFailed(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyStop(final android.media.MediaRouter2.RoutingController routingController) {
        java.util.Iterator<android.media.MediaRouter2.TransferCallbackRecord> it = this.mTransferCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2.TransferCallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2.TransferCallbackRecord.this.mTransferCallback.onStop(routingController);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyControllerUpdated(final android.media.MediaRouter2.RoutingController routingController) {
        java.util.Iterator<android.media.MediaRouter2.ControllerCallbackRecord> it = this.mControllerCallbackRecords.iterator();
        while (it.hasNext()) {
            final android.media.MediaRouter2.ControllerCallbackRecord next = it.next();
            next.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.MediaRouter2$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.MediaRouter2.ControllerCallbackRecord.this.mCallback.onControllerUpdated(routingController);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.media.RoutingSessionInfo ensureClientPackageNameForSystemSession(android.media.RoutingSessionInfo routingSessionInfo, java.lang.String str) {
        if (!routingSessionInfo.isSystemSession() || !android.text.TextUtils.isEmpty(routingSessionInfo.getClientPackageName())) {
            return routingSessionInfo;
        }
        return new android.media.RoutingSessionInfo.Builder(routingSessionInfo).setClientPackageName(str).build();
    }

    public static abstract class RouteCallback {
        @java.lang.Deprecated
        public void onRoutesAdded(java.util.List<android.media.MediaRoute2Info> list) {
        }

        @java.lang.Deprecated
        public void onRoutesRemoved(java.util.List<android.media.MediaRoute2Info> list) {
        }

        @java.lang.Deprecated
        public void onRoutesChanged(java.util.List<android.media.MediaRoute2Info> list) {
        }

        public void onRoutesUpdated(java.util.List<android.media.MediaRoute2Info> list) {
        }

        @android.annotation.SystemApi
        public void onPreferredFeaturesChanged(java.util.List<java.lang.String> list) {
        }
    }

    public static abstract class TransferCallback {
        public void onTransfer(android.media.MediaRouter2.RoutingController routingController, android.media.MediaRouter2.RoutingController routingController2) {
        }

        public void onTransferFailure(android.media.MediaRoute2Info mediaRoute2Info) {
        }

        public void onStop(android.media.MediaRouter2.RoutingController routingController) {
        }

        public void onRequestFailed(int i) {
        }
    }

    public static abstract class ControllerCallback {
        public void onControllerUpdated(android.media.MediaRouter2.RoutingController routingController) {
        }
    }

    public static final class ScanToken {
        private final int mId;

        private ScanToken(int i) {
            this.mId = i;
        }
    }

    public static final class ScanRequest {
        private final boolean mIsScreenOffScan;

        private ScanRequest(boolean z) {
            this.mIsScreenOffScan = z;
        }

        public boolean isScreenOffScan() {
            return this.mIsScreenOffScan;
        }

        public static final class Builder {
            boolean mIsScreenOffScan;

            public android.media.MediaRouter2.ScanRequest.Builder setScreenOffScan(boolean z) {
                this.mIsScreenOffScan = z;
                return this;
            }

            public android.media.MediaRouter2.ScanRequest build() {
                return new android.media.MediaRouter2.ScanRequest(this.mIsScreenOffScan);
            }
        }
    }

    public class RoutingController {
        private static final int CONTROLLER_STATE_ACTIVE = 1;
        private static final int CONTROLLER_STATE_RELEASED = 3;
        private static final int CONTROLLER_STATE_RELEASING = 2;
        private static final int CONTROLLER_STATE_UNKNOWN = 0;
        private final java.lang.Object mControllerLock;
        private android.media.RoutingSessionInfo mSessionInfo;
        private int mState;

        RoutingController(android.media.RoutingSessionInfo routingSessionInfo) {
            this.mControllerLock = new java.lang.Object();
            this.mSessionInfo = routingSessionInfo;
            this.mState = 1;
        }

        RoutingController(android.media.RoutingSessionInfo routingSessionInfo, int i) {
            this.mControllerLock = new java.lang.Object();
            this.mSessionInfo = routingSessionInfo;
            this.mState = i;
        }

        public java.lang.String getId() {
            java.lang.String id;
            synchronized (this.mControllerLock) {
                id = this.mSessionInfo.getId();
            }
            return id;
        }

        public java.lang.String getOriginalId() {
            java.lang.String originalId;
            synchronized (this.mControllerLock) {
                originalId = this.mSessionInfo.getOriginalId();
            }
            return originalId;
        }

        public android.os.Bundle getControlHints() {
            android.os.Bundle controlHints;
            synchronized (this.mControllerLock) {
                controlHints = this.mSessionInfo.getControlHints();
            }
            return controlHints;
        }

        public java.util.List<android.media.MediaRoute2Info> getSelectedRoutes() {
            java.util.List<java.lang.String> selectedRoutes;
            synchronized (this.mControllerLock) {
                selectedRoutes = this.mSessionInfo.getSelectedRoutes();
            }
            return android.media.MediaRouter2.this.getRoutesWithIds(selectedRoutes);
        }

        public java.util.List<android.media.MediaRoute2Info> getSelectableRoutes() {
            java.util.List<java.lang.String> selectableRoutes;
            synchronized (this.mControllerLock) {
                selectableRoutes = this.mSessionInfo.getSelectableRoutes();
            }
            return android.media.MediaRouter2.this.getRoutesWithIds(selectableRoutes);
        }

        public java.util.List<android.media.MediaRoute2Info> getDeselectableRoutes() {
            java.util.List<java.lang.String> deselectableRoutes;
            synchronized (this.mControllerLock) {
                deselectableRoutes = this.mSessionInfo.getDeselectableRoutes();
            }
            return android.media.MediaRouter2.this.getRoutesWithIds(deselectableRoutes);
        }

        public java.util.List<android.media.MediaRoute2Info> getTransferableRoutes() {
            java.util.List<java.lang.String> transferableRoutes;
            synchronized (this.mControllerLock) {
                transferableRoutes = this.mSessionInfo.getTransferableRoutes();
            }
            return android.media.MediaRouter2.this.getRoutesWithIds(transferableRoutes);
        }

        public boolean wasTransferInitiatedBySelf() {
            android.media.RoutingSessionInfo routingSessionInfo = getRoutingSessionInfo();
            return java.util.Objects.equals(android.os.Process.myUserHandle(), routingSessionInfo.getTransferInitiatorUserHandle()) && java.util.Objects.equals(android.media.MediaRouter2.this.mContext.getPackageName(), routingSessionInfo.getTransferInitiatorPackageName());
        }

        public android.media.RoutingSessionInfo getRoutingSessionInfo() {
            android.media.RoutingSessionInfo routingSessionInfo;
            synchronized (this.mControllerLock) {
                routingSessionInfo = this.mSessionInfo;
            }
            return routingSessionInfo;
        }

        public int getVolumeHandling() {
            int volumeHandling;
            synchronized (this.mControllerLock) {
                volumeHandling = this.mSessionInfo.getVolumeHandling();
            }
            return volumeHandling;
        }

        public int getVolumeMax() {
            int volumeMax;
            synchronized (this.mControllerLock) {
                volumeMax = this.mSessionInfo.getVolumeMax();
            }
            return volumeMax;
        }

        public int getVolume() {
            int volume;
            synchronized (this.mControllerLock) {
                volume = this.mSessionInfo.getVolume();
            }
            return volume;
        }

        public boolean isReleased() {
            boolean z;
            synchronized (this.mControllerLock) {
                z = this.mState == 3;
            }
            return z;
        }

        public void selectRoute(android.media.MediaRoute2Info mediaRoute2Info) {
            java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            if (isReleased()) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "selectRoute: Called on released controller. Ignoring.");
                return;
            }
            if (containsRouteInfoWithId(getSelectedRoutes(), mediaRoute2Info.getId())) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "Ignoring selecting a route that is already selected. route=" + mediaRoute2Info);
            } else if (!containsRouteInfoWithId(getSelectableRoutes(), mediaRoute2Info.getId())) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "Ignoring selecting a non-selectable route=" + mediaRoute2Info);
            } else {
                android.media.MediaRouter2.this.mImpl.selectRoute(mediaRoute2Info, getRoutingSessionInfo());
            }
        }

        public void deselectRoute(android.media.MediaRoute2Info mediaRoute2Info) {
            java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            if (isReleased()) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "deselectRoute: called on released controller. Ignoring.");
                return;
            }
            if (!containsRouteInfoWithId(getSelectedRoutes(), mediaRoute2Info.getId())) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "Ignoring deselecting a route that is not selected. route=" + mediaRoute2Info);
            } else if (!containsRouteInfoWithId(getDeselectableRoutes(), mediaRoute2Info.getId())) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "Ignoring deselecting a non-deselectable route=" + mediaRoute2Info);
            } else {
                android.media.MediaRouter2.this.mImpl.deselectRoute(mediaRoute2Info, getRoutingSessionInfo());
            }
        }

        void transferToRoute(android.media.MediaRoute2Info mediaRoute2Info) {
            android.media.MediaRouter2.MediaRouter2Stub mediaRouter2Stub;
            java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            synchronized (this.mControllerLock) {
                if (isReleased()) {
                    android.util.Log.w(android.media.MediaRouter2.TAG, "transferToRoute: Called on released controller. Ignoring.");
                    return;
                }
                if (!this.mSessionInfo.getTransferableRoutes().contains(mediaRoute2Info.getId())) {
                    android.util.Log.w(android.media.MediaRouter2.TAG, "Ignoring transferring to a non-transferable route=" + mediaRoute2Info);
                    return;
                }
                synchronized (android.media.MediaRouter2.this.mLock) {
                    mediaRouter2Stub = android.media.MediaRouter2.this.mStub;
                }
                if (mediaRouter2Stub != null) {
                    try {
                        android.media.MediaRouter2.this.mMediaRouterService.transferToRouteWithRouter2(mediaRouter2Stub, getId(), mediaRoute2Info);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.media.MediaRouter2.TAG, "Unable to transfer to route for session.", e);
                    }
                }
            }
        }

        public void setVolume(int i) {
            if (getVolumeHandling() == 0) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "setVolume: The routing session has fixed volume. Ignoring.");
                return;
            }
            if (i < 0 || i > getVolumeMax()) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "setVolume: The target volume is out of range. Ignoring");
            } else if (isReleased()) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "setVolume: Called on released controller. Ignoring.");
            } else {
                android.media.MediaRouter2.this.mImpl.setSessionVolume(i, getRoutingSessionInfo());
            }
        }

        public void release() {
            releaseInternal(true);
        }

        boolean scheduleRelease() {
            synchronized (this.mControllerLock) {
                if (this.mState != 1) {
                    return false;
                }
                this.mState = 2;
                synchronized (android.media.MediaRouter2.this.mLock) {
                    if (!android.media.MediaRouter2.this.mNonSystemRoutingControllers.remove(getId(), this)) {
                        return true;
                    }
                    android.media.MediaRouter2.this.mHandler.postDelayed(new java.lang.Runnable() { // from class: android.media.MediaRouter2$RoutingController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.MediaRouter2.RoutingController.this.release();
                        }
                    }, 30000L);
                    return true;
                }
            }
        }

        void releaseInternal(boolean z) {
            synchronized (this.mControllerLock) {
                if (this.mState == 3) {
                    if (android.media.MediaRouter2.DEBUG) {
                        android.util.Log.d(android.media.MediaRouter2.TAG, "releaseInternal: Called on released controller. Ignoring.");
                    }
                    return;
                }
                boolean z2 = true;
                if (this.mState != 1) {
                    z2 = false;
                }
                this.mState = 3;
                android.media.MediaRouter2.this.mImpl.releaseSession(z, z2, this);
            }
        }

        public java.lang.String toString() {
            return "RoutingController{ id=" + getId() + ", selectedRoutes={" + ((java.util.List) getSelectedRoutes().stream().map(new android.media.MediaRouter2$$ExternalSyntheticLambda16()).collect(java.util.stream.Collectors.toList())) + "}, selectableRoutes={" + ((java.util.List) getSelectableRoutes().stream().map(new android.media.MediaRouter2$$ExternalSyntheticLambda16()).collect(java.util.stream.Collectors.toList())) + "}, deselectableRoutes={" + ((java.util.List) getDeselectableRoutes().stream().map(new android.media.MediaRouter2$$ExternalSyntheticLambda16()).collect(java.util.stream.Collectors.toList())) + "} }";
        }

        void setRoutingSessionInfo(android.media.RoutingSessionInfo routingSessionInfo) {
            synchronized (this.mControllerLock) {
                this.mSessionInfo = routingSessionInfo;
            }
        }

        private static boolean containsRouteInfoWithId(java.util.List<android.media.MediaRoute2Info> list, java.lang.String str) {
            java.util.Iterator<android.media.MediaRoute2Info> it = list.iterator();
            while (it.hasNext()) {
                if (android.text.TextUtils.equals(str, it.next().getId())) {
                    return true;
                }
            }
            return false;
        }
    }

    class SystemRoutingController extends android.media.MediaRouter2.RoutingController {
        SystemRoutingController(android.media.RoutingSessionInfo routingSessionInfo) {
            super(routingSessionInfo);
        }

        @Override // android.media.MediaRouter2.RoutingController
        public boolean isReleased() {
            return false;
        }

        @Override // android.media.MediaRouter2.RoutingController
        boolean scheduleRelease() {
            return true;
        }

        @Override // android.media.MediaRouter2.RoutingController
        void releaseInternal(boolean z) {
        }
    }

    static final class RouteCallbackRecord {
        public final java.util.concurrent.Executor mExecutor;
        public final android.media.RouteDiscoveryPreference mPreference;
        public final android.media.MediaRouter2.RouteCallback mRouteCallback;

        RouteCallbackRecord(java.util.concurrent.Executor executor, android.media.MediaRouter2.RouteCallback routeCallback, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
            this.mRouteCallback = routeCallback;
            this.mExecutor = executor;
            this.mPreference = routeDiscoveryPreference;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof android.media.MediaRouter2.RouteCallbackRecord) && this.mRouteCallback == ((android.media.MediaRouter2.RouteCallbackRecord) obj).mRouteCallback;
        }

        public int hashCode() {
            return this.mRouteCallback.hashCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class RouteListingPreferenceCallbackRecord {
        public final java.util.concurrent.Executor mExecutor;
        public final java.util.function.Consumer<android.media.RouteListingPreference> mRouteListingPreferenceCallback;

        RouteListingPreferenceCallbackRecord(java.util.concurrent.Executor executor, java.util.function.Consumer<android.media.RouteListingPreference> consumer) {
            this.mExecutor = executor;
            this.mRouteListingPreferenceCallback = consumer;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof android.media.MediaRouter2.RouteListingPreferenceCallbackRecord) && this.mRouteListingPreferenceCallback == ((android.media.MediaRouter2.RouteListingPreferenceCallbackRecord) obj).mRouteListingPreferenceCallback;
        }

        public int hashCode() {
            return this.mRouteListingPreferenceCallback.hashCode();
        }
    }

    static final class TransferCallbackRecord {
        public final java.util.concurrent.Executor mExecutor;
        public final android.media.MediaRouter2.TransferCallback mTransferCallback;

        TransferCallbackRecord(java.util.concurrent.Executor executor, android.media.MediaRouter2.TransferCallback transferCallback) {
            this.mTransferCallback = transferCallback;
            this.mExecutor = executor;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof android.media.MediaRouter2.TransferCallbackRecord) && this.mTransferCallback == ((android.media.MediaRouter2.TransferCallbackRecord) obj).mTransferCallback;
        }

        public int hashCode() {
            return this.mTransferCallback.hashCode();
        }
    }

    static final class ControllerCallbackRecord {
        public final android.media.MediaRouter2.ControllerCallback mCallback;
        public final java.util.concurrent.Executor mExecutor;

        ControllerCallbackRecord(java.util.concurrent.Executor executor, android.media.MediaRouter2.ControllerCallback controllerCallback) {
            this.mCallback = controllerCallback;
            this.mExecutor = executor;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof android.media.MediaRouter2.ControllerCallbackRecord) && this.mCallback == ((android.media.MediaRouter2.ControllerCallbackRecord) obj).mCallback;
        }

        public int hashCode() {
            return this.mCallback.hashCode();
        }
    }

    static final class ControllerCreationRequest {
        public final long mManagerRequestId;
        public final android.media.MediaRouter2.RoutingController mOldController;
        public final int mRequestId;
        public final android.media.MediaRoute2Info mRoute;

        ControllerCreationRequest(int i, long j, android.media.MediaRoute2Info mediaRoute2Info, android.media.MediaRouter2.RoutingController routingController) {
            this.mRequestId = i;
            this.mManagerRequestId = j;
            this.mRoute = (android.media.MediaRoute2Info) java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            this.mOldController = (android.media.MediaRouter2.RoutingController) java.util.Objects.requireNonNull(routingController, "oldController must not be null");
        }
    }

    class MediaRouter2Stub extends android.media.IMediaRouter2.Stub {
        MediaRouter2Stub() {
        }

        @Override // android.media.IMediaRouter2
        public void notifyRouterRegistered(java.util.List<android.media.MediaRoute2Info> list, android.media.RoutingSessionInfo routingSessionInfo) {
            android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.media.MediaRouter2) obj).syncRoutesOnHandler((java.util.List) obj2, (android.media.RoutingSessionInfo) obj3);
                }
            }, android.media.MediaRouter2.this, list, routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2
        public void notifyRoutesUpdated(java.util.List<android.media.MediaRoute2Info> list) {
            android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda4(), android.media.MediaRouter2.this, list));
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionCreated(int i, android.media.RoutingSessionInfo routingSessionInfo) {
            android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.media.MediaRouter2) obj).createControllerOnHandler(((java.lang.Integer) obj2).intValue(), (android.media.RoutingSessionInfo) obj3);
                }
            }, android.media.MediaRouter2.this, java.lang.Integer.valueOf(i), routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionInfoChanged(android.media.RoutingSessionInfo routingSessionInfo) {
            android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.media.MediaRouter2) obj).updateControllerOnHandler((android.media.RoutingSessionInfo) obj2);
                }
            }, android.media.MediaRouter2.this, routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2
        public void notifySessionReleased(android.media.RoutingSessionInfo routingSessionInfo) {
            android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.media.MediaRouter2) obj).releaseControllerOnHandler((android.media.RoutingSessionInfo) obj2);
                }
            }, android.media.MediaRouter2.this, routingSessionInfo));
        }

        @Override // android.media.IMediaRouter2
        public void requestCreateSessionByManager(long j, android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) {
            android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HexConsumer() { // from class: android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.HexConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                    ((android.media.MediaRouter2) obj).onRequestCreateControllerByManagerOnHandler((android.media.RoutingSessionInfo) obj2, (android.media.MediaRoute2Info) obj3, ((java.lang.Long) obj4).longValue(), (android.os.UserHandle) obj5, (java.lang.String) obj6);
                }
            }, android.media.MediaRouter2.this, routingSessionInfo, mediaRoute2Info, java.lang.Long.valueOf(j), userHandle, str));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ProxyMediaRouter2Impl implements android.media.MediaRouter2.MediaRouter2Impl {
        private final java.lang.String mClientPackageName;
        private final android.os.UserHandle mClientUser;
        private final java.util.concurrent.CopyOnWriteArrayList<android.media.MediaRouter2Manager.TransferRequest> mTransferRequests = new java.util.concurrent.CopyOnWriteArrayList<>();
        private final java.util.concurrent.atomic.AtomicInteger mScanRequestCount = new java.util.concurrent.atomic.AtomicInteger(0);
        private final java.util.concurrent.atomic.AtomicBoolean mIsScanning = new java.util.concurrent.atomic.AtomicBoolean(false);
        private final android.media.IMediaRouter2Manager.Stub mClient = new android.media.MediaRouter2.ProxyMediaRouter2Impl.Client();

        ProxyMediaRouter2Impl(android.content.Context context, java.lang.String str, android.os.UserHandle userHandle) {
            this.mClientUser = userHandle;
            this.mClientPackageName = str;
            android.media.MediaRouter2.this.mDiscoveryPreference = android.media.RouteDiscoveryPreference.EMPTY;
        }

        public void registerProxyRouter() {
            try {
                android.media.MediaRouter2.this.mMediaRouterService.registerProxyRouter(this.mClient, android.media.MediaRouter2.this.mContext.getApplicationContext().getPackageName(), this.mClientPackageName, this.mClientUser);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void updateScanningState(int i) throws android.os.RemoteException {
            android.media.MediaRouter2.this.mMediaRouterService.updateScanningState(this.mClient, i);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void startScan() {
            if (!this.mIsScanning.getAndSet(true) && this.mScanRequestCount.getAndIncrement() == 0) {
                try {
                    android.media.MediaRouter2.this.mMediaRouterService.updateScanningState(this.mClient, 1);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void stopScan() {
            if (this.mIsScanning.getAndSet(false) && this.mScanRequestCount.updateAndGet(new java.util.function.IntUnaryOperator() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$$ExternalSyntheticLambda0
                @Override // java.util.function.IntUnaryOperator
                public final int applyAsInt(int i) {
                    return android.media.MediaRouter2.ProxyMediaRouter2Impl.lambda$stopScan$0(i);
                }
            }) == 0) {
                try {
                    android.media.MediaRouter2.this.mMediaRouterService.updateScanningState(this.mClient, 0);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        static /* synthetic */ int lambda$stopScan$0(int i) {
            if (i == 0) {
                throw new java.lang.IllegalStateException("No active scan requests to unregister.");
            }
            return i - 1;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public java.lang.String getClientPackageName() {
            return this.mClientPackageName;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public java.lang.String getPackageName() {
            return null;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public android.media.RoutingSessionInfo getSystemSessionInfo() {
            return getSystemSessionInfoImpl(android.media.MediaRouter2.this.mMediaRouterService, this.mClientPackageName);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public android.media.MediaRouter2.RouteCallbackRecord createRouteCallbackRecord(java.util.concurrent.Executor executor, android.media.MediaRouter2.RouteCallback routeCallback, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
            return new android.media.MediaRouter2.RouteCallbackRecord(executor, routeCallback, android.media.RouteDiscoveryPreference.EMPTY);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void registerRouteCallback() {
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void unregisterRouteCallback() {
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setRouteListingPreference(android.media.RouteListingPreference routeListingPreference) {
            throw new java.lang.UnsupportedOperationException("RouteListingPreference cannot be set by a privileged MediaRouter2 instance.");
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public boolean showSystemOutputSwitcher() {
            throw new java.lang.UnsupportedOperationException("Cannot show system output switcher from a privileged router.");
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public java.util.List<android.media.MediaRoute2Info> getAllRoutes() {
            java.util.ArrayList arrayList;
            synchronized (android.media.MediaRouter2.this.mLock) {
                arrayList = new java.util.ArrayList(android.media.MediaRouter2.this.mRoutes.values());
            }
            return arrayList;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setOnGetControllerHintsListener(android.media.MediaRouter2.OnGetControllerHintsListener onGetControllerHintsListener) {
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void transferTo(android.media.MediaRoute2Info mediaRoute2Info) {
            java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            transfer(getRoutingSessions().get(r0.size() - 1), mediaRoute2Info, android.os.Process.myUserHandle(), android.media.MediaRouter2.this.mContext.getPackageName());
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void stop() {
            releaseSession(getRoutingSessions().get(r0.size() - 1));
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void transfer(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) {
            boolean z;
            java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
            java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            java.util.Objects.requireNonNull(userHandle);
            java.util.Objects.requireNonNull(str);
            android.util.Log.v(android.media.MediaRouter2.TAG, "Transferring routing session. session= " + routingSessionInfo + ", route=" + mediaRoute2Info);
            synchronized (android.media.MediaRouter2.this.mLock) {
                z = !android.media.MediaRouter2.this.mRoutes.containsKey(mediaRoute2Info.getId());
            }
            if (z) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "transfer: Ignoring an unknown route id=" + mediaRoute2Info.getId());
                onTransferFailed(routingSessionInfo, mediaRoute2Info);
            } else if (routingSessionInfo.getTransferableRoutes().contains(mediaRoute2Info.getId())) {
                transferToRoute(routingSessionInfo, mediaRoute2Info, userHandle, str);
            } else {
                requestCreateSession(routingSessionInfo, mediaRoute2Info, userHandle, str);
            }
        }

        private void transferToRoute(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) {
            try {
                android.media.MediaRouter2.this.mMediaRouterService.transferToRouteWithManager(this.mClient, createTransferRequest(routingSessionInfo, mediaRoute2Info), routingSessionInfo.getId(), mediaRoute2Info, userHandle, str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private void requestCreateSession(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) {
            if (android.text.TextUtils.isEmpty(routingSessionInfo.getClientPackageName())) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "requestCreateSession: Can't create a session without package name.");
                onTransferFailed(routingSessionInfo, mediaRoute2Info);
            } else {
                try {
                    android.media.MediaRouter2.this.mMediaRouterService.requestCreateSessionWithManager(this.mClient, createTransferRequest(routingSessionInfo, mediaRoute2Info), routingSessionInfo, mediaRoute2Info, userHandle, str);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public java.util.List<android.media.MediaRouter2.RoutingController> getControllers() {
            android.media.MediaRouter2.RoutingController routingController;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.media.RoutingSessionInfo routingSessionInfo : getRoutingSessions()) {
                if (routingSessionInfo.isSystemSession()) {
                    android.media.MediaRouter2.this.mSystemController.setRoutingSessionInfo(routingSessionInfo);
                    routingController = android.media.MediaRouter2.this.mSystemController;
                } else {
                    routingController = android.media.MediaRouter2.this.new RoutingController(routingSessionInfo);
                }
                arrayList.add(routingController);
            }
            return arrayList;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setRouteVolume(android.media.MediaRoute2Info mediaRoute2Info, int i) {
            if (mediaRoute2Info.getVolumeHandling() == 0) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "setRouteVolume: the route has fixed volume. Ignoring.");
                return;
            }
            if (i < 0 || i > mediaRoute2Info.getVolumeMax()) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "setRouteVolume: the target volume is out of range. Ignoring");
                return;
            }
            try {
                android.media.MediaRouter2.this.mMediaRouterService.setRouteVolumeWithManager(this.mClient, android.media.MediaRouter2.this.mNextRequestId.getAndIncrement(), mediaRoute2Info, i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setSessionVolume(int i, android.media.RoutingSessionInfo routingSessionInfo) {
            java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
            if (routingSessionInfo.getVolumeHandling() == 0) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "setSessionVolume: the route has fixed volume. Ignoring.");
                return;
            }
            if (i < 0 || i > routingSessionInfo.getVolumeMax()) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "setSessionVolume: the target volume is out of range. Ignoring");
                return;
            }
            try {
                android.media.MediaRouter2.this.mMediaRouterService.setSessionVolumeWithManager(this.mClient, android.media.MediaRouter2.this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId(), i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public java.util.List<android.media.MediaRoute2Info> filterRoutesWithIndividualPreference(java.util.List<android.media.MediaRoute2Info> list, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
            return new java.util.ArrayList(list);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void selectRoute(android.media.MediaRoute2Info mediaRoute2Info, android.media.RoutingSessionInfo routingSessionInfo) {
            java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
            java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            if (routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId())) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "Ignoring selecting a route that is already selected. route=" + mediaRoute2Info);
                return;
            }
            if (!routingSessionInfo.getSelectableRoutes().contains(mediaRoute2Info.getId())) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "Ignoring selecting a non-selectable route=" + mediaRoute2Info);
                return;
            }
            try {
                android.media.MediaRouter2.this.mMediaRouterService.selectRouteWithManager(this.mClient, android.media.MediaRouter2.this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId(), mediaRoute2Info);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void deselectRoute(android.media.MediaRoute2Info mediaRoute2Info, android.media.RoutingSessionInfo routingSessionInfo) {
            java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
            java.util.Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            if (!routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId())) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "Ignoring deselecting a route that is not selected. route=" + mediaRoute2Info);
                return;
            }
            if (!routingSessionInfo.getDeselectableRoutes().contains(mediaRoute2Info.getId())) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "Ignoring deselecting a non-deselectable route=" + mediaRoute2Info);
                return;
            }
            try {
                android.media.MediaRouter2.this.mMediaRouterService.deselectRouteWithManager(this.mClient, android.media.MediaRouter2.this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId(), mediaRoute2Info);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void releaseSession(boolean z, boolean z2, android.media.MediaRouter2.RoutingController routingController) {
            releaseSession(routingController.getRoutingSessionInfo());
        }

        static android.media.RoutingSessionInfo getSystemSessionInfoImpl(android.media.IMediaRouterService iMediaRouterService, java.lang.String str) {
            try {
                return iMediaRouterService.getSystemSessionInfoForPackage(str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private void releaseSession(android.media.RoutingSessionInfo routingSessionInfo) {
            java.util.Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
            try {
                android.media.MediaRouter2.this.mMediaRouterService.releaseSessionWithManager(this.mClient, android.media.MediaRouter2.this.mNextRequestId.getAndIncrement(), routingSessionInfo.getId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private int createTransferRequest(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info) {
            int andIncrement = android.media.MediaRouter2.this.mNextRequestId.getAndIncrement();
            android.media.MediaRouter2Manager.TransferRequest transferRequest = new android.media.MediaRouter2Manager.TransferRequest(andIncrement, routingSessionInfo, mediaRoute2Info);
            this.mTransferRequests.add(transferRequest);
            android.media.MediaRouter2.this.mHandler.sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.media.MediaRouter2.ProxyMediaRouter2Impl) obj).handleTransferTimeout((android.media.MediaRouter2Manager.TransferRequest) obj2);
                }
            }, this, transferRequest), 30000L);
            return andIncrement;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleTransferTimeout(android.media.MediaRouter2Manager.TransferRequest transferRequest) {
            if (this.mTransferRequests.remove(transferRequest)) {
                onTransferFailed(transferRequest.mOldSessionInfo, transferRequest.mTargetRoute);
            }
        }

        private java.util.List<android.media.RoutingSessionInfo> getRoutingSessions() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(getSystemSessionInfo());
            try {
                for (android.media.RoutingSessionInfo routingSessionInfo : android.media.MediaRouter2.this.mMediaRouterService.getRemoteSessions(this.mClient)) {
                    if (android.text.TextUtils.equals(routingSessionInfo.getClientPackageName(), this.mClientPackageName)) {
                        arrayList.add(routingSessionInfo);
                    }
                }
                return arrayList;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private void onTransferred(android.media.RoutingSessionInfo routingSessionInfo, android.media.RoutingSessionInfo routingSessionInfo2) {
            android.media.MediaRouter2.RoutingController routingController;
            android.media.MediaRouter2.RoutingController routingController2;
            if (!routingSessionInfo.isSystemSession() && !android.text.TextUtils.equals(getClientPackageName(), routingSessionInfo.getClientPackageName())) {
                return;
            }
            if ((!routingSessionInfo2.isSystemSession() && !android.text.TextUtils.equals(getClientPackageName(), routingSessionInfo2.getClientPackageName())) || android.text.TextUtils.equals(routingSessionInfo.getId(), routingSessionInfo2.getId())) {
                return;
            }
            if (routingSessionInfo.isSystemSession()) {
                android.media.MediaRouter2.this.mSystemController.setRoutingSessionInfo(android.media.MediaRouter2.ensureClientPackageNameForSystemSession(routingSessionInfo, this.mClientPackageName));
                routingController = android.media.MediaRouter2.this.mSystemController;
            } else {
                routingController = android.media.MediaRouter2.this.new RoutingController(routingSessionInfo);
            }
            if (routingSessionInfo2.isSystemSession()) {
                android.media.MediaRouter2.this.mSystemController.setRoutingSessionInfo(android.media.MediaRouter2.ensureClientPackageNameForSystemSession(routingSessionInfo2, this.mClientPackageName));
                routingController2 = android.media.MediaRouter2.this.mSystemController;
            } else {
                routingController2 = android.media.MediaRouter2.this.new RoutingController(routingSessionInfo2);
            }
            android.media.MediaRouter2.this.notifyTransfer(routingController, routingController2);
        }

        private void onTransferFailed(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info) {
            if (!routingSessionInfo.isSystemSession() && !android.text.TextUtils.equals(getClientPackageName(), routingSessionInfo.getClientPackageName())) {
                return;
            }
            android.media.MediaRouter2.this.notifyTransferFailure(mediaRoute2Info);
        }

        private void onSessionUpdated(android.media.RoutingSessionInfo routingSessionInfo) {
            android.media.MediaRouter2.RoutingController routingController;
            if (!routingSessionInfo.isSystemSession() && !android.text.TextUtils.equals(getClientPackageName(), routingSessionInfo.getClientPackageName())) {
                return;
            }
            if (routingSessionInfo.isSystemSession()) {
                android.media.MediaRouter2.this.mSystemController.setRoutingSessionInfo(android.media.MediaRouter2.ensureClientPackageNameForSystemSession(routingSessionInfo, this.mClientPackageName));
                routingController = android.media.MediaRouter2.this.mSystemController;
            } else {
                routingController = android.media.MediaRouter2.this.new RoutingController(routingSessionInfo);
            }
            android.media.MediaRouter2.this.notifyControllerUpdated(routingController);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSessionCreatedOnHandler(int i, android.media.RoutingSessionInfo routingSessionInfo) {
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
            if (!routingSessionInfo.getSelectedRoutes().contains(mediaRoute2Info.getId())) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "The session does not contain the requested route. (requestedRouteId=" + mediaRoute2Info.getId() + ", actualRoutes=" + routingSessionInfo.getSelectedRoutes() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                onTransferFailed(transferRequest.mOldSessionInfo, mediaRoute2Info);
            } else if (!android.text.TextUtils.equals(mediaRoute2Info.getProviderId(), routingSessionInfo.getProviderId())) {
                android.util.Log.w(android.media.MediaRouter2.TAG, "The session's provider ID does not match the requested route's. (requested route's providerId=" + mediaRoute2Info.getProviderId() + ", actual providerId=" + routingSessionInfo.getProviderId() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                onTransferFailed(transferRequest.mOldSessionInfo, mediaRoute2Info);
            } else {
                onTransferred(transferRequest.mOldSessionInfo, routingSessionInfo);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSessionUpdatedOnHandler(android.media.RoutingSessionInfo routingSessionInfo) {
            java.util.Iterator<android.media.MediaRouter2Manager.TransferRequest> it = this.mTransferRequests.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                android.media.MediaRouter2Manager.TransferRequest next = it.next();
                if (android.text.TextUtils.equals(next.mOldSessionInfo.getId(), routingSessionInfo.getId()) && routingSessionInfo.getSelectedRoutes().contains(next.mTargetRoute.getId())) {
                    this.mTransferRequests.remove(next);
                    onTransferred(next.mOldSessionInfo, routingSessionInfo);
                    break;
                }
            }
            onSessionUpdated(routingSessionInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSessionReleasedOnHandler(android.media.RoutingSessionInfo routingSessionInfo) {
            if (routingSessionInfo.isSystemSession()) {
                android.util.Log.e(android.media.MediaRouter2.TAG, "onSessionReleasedOnHandler: Called on system session. Ignoring.");
            } else {
                if (!android.text.TextUtils.equals(getClientPackageName(), routingSessionInfo.getClientPackageName())) {
                    return;
                }
                android.media.MediaRouter2.this.notifyStop(android.media.MediaRouter2.this.new RoutingController(routingSessionInfo, 3));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onDiscoveryPreferenceChangedOnHandler(java.lang.String str, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
            if (!android.text.TextUtils.equals(getClientPackageName(), str) || routeDiscoveryPreference == null) {
                return;
            }
            synchronized (android.media.MediaRouter2.this.mLock) {
                if (java.util.Objects.equals(routeDiscoveryPreference, android.media.MediaRouter2.this.mDiscoveryPreference)) {
                    return;
                }
                android.media.MediaRouter2.this.mDiscoveryPreference = routeDiscoveryPreference;
                android.media.MediaRouter2.this.updateFilteredRoutesLocked();
                android.media.MediaRouter2.this.notifyPreferredFeaturesChanged(routeDiscoveryPreference.getPreferredFeatures());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onRouteListingPreferenceChangedOnHandler(java.lang.String str, android.media.RouteListingPreference routeListingPreference) {
            if (!android.text.TextUtils.equals(getClientPackageName(), str)) {
                return;
            }
            synchronized (android.media.MediaRouter2.this.mLock) {
                if (java.util.Objects.equals(android.media.MediaRouter2.this.mRouteListingPreference, routeListingPreference)) {
                    return;
                }
                android.media.MediaRouter2.this.mRouteListingPreference = routeListingPreference;
                android.media.MediaRouter2.this.notifyRouteListingPreferenceUpdated(routeListingPreference);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onRequestFailedOnHandler(int i, int i2) {
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
                onTransferFailed(transferRequest.mOldSessionInfo, transferRequest.mTargetRoute);
            } else {
                android.media.MediaRouter2.this.notifyRequestFailed(i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        class Client extends android.media.IMediaRouter2Manager.Stub {
            private Client() {
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionCreated(int i, android.media.RoutingSessionInfo routingSessionInfo) {
                android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((android.media.MediaRouter2.ProxyMediaRouter2Impl) obj).onSessionCreatedOnHandler(((java.lang.Integer) obj2).intValue(), (android.media.RoutingSessionInfo) obj3);
                    }
                }, android.media.MediaRouter2.ProxyMediaRouter2Impl.this, java.lang.Integer.valueOf(i), routingSessionInfo));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionUpdated(android.media.RoutingSessionInfo routingSessionInfo) {
                android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((android.media.MediaRouter2.ProxyMediaRouter2Impl) obj).onSessionUpdatedOnHandler((android.media.RoutingSessionInfo) obj2);
                    }
                }, android.media.MediaRouter2.ProxyMediaRouter2Impl.this, routingSessionInfo));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifySessionReleased(android.media.RoutingSessionInfo routingSessionInfo) {
                android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda4
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((android.media.MediaRouter2.ProxyMediaRouter2Impl) obj).onSessionReleasedOnHandler((android.media.RoutingSessionInfo) obj2);
                    }
                }, android.media.MediaRouter2.ProxyMediaRouter2Impl.this, routingSessionInfo));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyDiscoveryPreferenceChanged(java.lang.String str, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
                android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda5
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((android.media.MediaRouter2.ProxyMediaRouter2Impl) obj).onDiscoveryPreferenceChangedOnHandler((java.lang.String) obj2, (android.media.RouteDiscoveryPreference) obj3);
                    }
                }, android.media.MediaRouter2.ProxyMediaRouter2Impl.this, str, routeDiscoveryPreference));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRouteListingPreferenceChange(java.lang.String str, android.media.RouteListingPreference routeListingPreference) {
                android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda3
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((android.media.MediaRouter2.ProxyMediaRouter2Impl) obj).onRouteListingPreferenceChangedOnHandler((java.lang.String) obj2, (android.media.RouteListingPreference) obj3);
                    }
                }, android.media.MediaRouter2.ProxyMediaRouter2Impl.this, str, routeListingPreference));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRoutesUpdated(java.util.List<android.media.MediaRoute2Info> list) {
                android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new android.media.MediaRouter2$MediaRouter2Stub$$ExternalSyntheticLambda4(), android.media.MediaRouter2.this, list));
            }

            @Override // android.media.IMediaRouter2Manager
            public void notifyRequestFailed(int i, int i2) {
                android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.media.MediaRouter2$ProxyMediaRouter2Impl$Client$$ExternalSyntheticLambda2
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((android.media.MediaRouter2.ProxyMediaRouter2Impl) obj).onRequestFailedOnHandler(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue());
                    }
                }, android.media.MediaRouter2.ProxyMediaRouter2Impl.this, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class LocalMediaRouter2Impl implements android.media.MediaRouter2.MediaRouter2Impl {
        private final java.lang.String mPackageName;

        LocalMediaRouter2Impl(java.lang.String str) {
            this.mPackageName = str;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void startScan() {
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void stopScan() {
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void updateScanningState(int i) throws android.os.RemoteException {
            if (i != 0) {
                registerRouterStubIfNeededLocked();
            }
            android.media.MediaRouter2.this.mMediaRouterService.updateScanningStateWithRouter2(android.media.MediaRouter2.this.mStub, i);
            if (i == 0) {
                unregisterRouterStubIfNeededLocked(true);
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public java.lang.String getClientPackageName() {
            return null;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public java.lang.String getPackageName() {
            return this.mPackageName;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public android.media.RoutingSessionInfo getSystemSessionInfo() {
            try {
                return android.media.MediaRouter2.ensureClientPackageNameForSystemSession(android.media.MediaRouter2.this.mMediaRouterService.getSystemSessionInfo(), android.media.MediaRouter2.this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                return null;
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public android.media.MediaRouter2.RouteCallbackRecord createRouteCallbackRecord(java.util.concurrent.Executor executor, android.media.MediaRouter2.RouteCallback routeCallback, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
            return new android.media.MediaRouter2.RouteCallbackRecord(executor, routeCallback, routeDiscoveryPreference);
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void registerRouteCallback() {
            synchronized (android.media.MediaRouter2.this.mLock) {
                try {
                    registerRouterStubIfNeededLocked();
                    if (android.media.MediaRouter2.this.updateDiscoveryPreferenceIfNeededLocked()) {
                        android.media.MediaRouter2.this.mMediaRouterService.setDiscoveryRequestWithRouter2(android.media.MediaRouter2.this.mStub, android.media.MediaRouter2.this.mDiscoveryPreference);
                    }
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void unregisterRouteCallback() {
            synchronized (android.media.MediaRouter2.this.mLock) {
                if (android.media.MediaRouter2.this.mStub == null) {
                    return;
                }
                try {
                    if (android.media.MediaRouter2.this.updateDiscoveryPreferenceIfNeededLocked()) {
                        android.media.MediaRouter2.this.mMediaRouterService.setDiscoveryRequestWithRouter2(android.media.MediaRouter2.this.mStub, android.media.MediaRouter2.this.mDiscoveryPreference);
                    }
                    unregisterRouterStubIfNeededLocked(false);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.media.MediaRouter2.TAG, "unregisterRouteCallback: Unable to set discovery request.", e);
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setRouteListingPreference(android.media.RouteListingPreference routeListingPreference) {
            synchronized (android.media.MediaRouter2.this.mLock) {
                if (java.util.Objects.equals(android.media.MediaRouter2.this.mRouteListingPreference, routeListingPreference)) {
                    return;
                }
                android.media.MediaRouter2.this.mRouteListingPreference = routeListingPreference;
                try {
                    registerRouterStubIfNeededLocked();
                    android.media.MediaRouter2.this.mMediaRouterService.setRouteListingPreference(android.media.MediaRouter2.this.mStub, android.media.MediaRouter2.this.mRouteListingPreference);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
                android.media.MediaRouter2.this.notifyRouteListingPreferenceUpdated(routeListingPreference);
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public boolean showSystemOutputSwitcher() {
            boolean showMediaOutputSwitcher;
            synchronized (android.media.MediaRouter2.this.mLock) {
                try {
                    try {
                        showMediaOutputSwitcher = android.media.MediaRouter2.this.mMediaRouterService.showMediaOutputSwitcher(android.media.MediaRouter2.this.mImpl.getPackageName());
                    } catch (android.os.RemoteException e) {
                        e.rethrowFromSystemServer();
                        return false;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return showMediaOutputSwitcher;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public java.util.List<android.media.MediaRoute2Info> getAllRoutes() {
            return java.util.Collections.emptyList();
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setOnGetControllerHintsListener(android.media.MediaRouter2.OnGetControllerHintsListener onGetControllerHintsListener) {
            android.media.MediaRouter2.this.mOnGetControllerHintsListener = onGetControllerHintsListener;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void transferTo(android.media.MediaRoute2Info mediaRoute2Info) {
            boolean containsKey;
            android.util.Log.v(android.media.MediaRouter2.TAG, "Transferring to route: " + mediaRoute2Info);
            synchronized (android.media.MediaRouter2.this.mLock) {
                containsKey = android.media.MediaRouter2.this.mRoutes.containsKey(mediaRoute2Info.getId());
            }
            if (!containsKey) {
                android.media.MediaRouter2.this.notifyTransferFailure(mediaRoute2Info);
                return;
            }
            android.media.MediaRouter2.RoutingController currentController = android.media.MediaRouter2.this.getCurrentController();
            if (currentController.getRoutingSessionInfo().getTransferableRoutes().contains(mediaRoute2Info.getId())) {
                currentController.transferToRoute(mediaRoute2Info);
            } else {
                android.media.MediaRouter2.this.requestCreateController(currentController, mediaRoute2Info, 0L, android.os.Process.myUserHandle(), android.media.MediaRouter2.this.mContext.getPackageName());
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void stop() {
            android.media.MediaRouter2.this.getCurrentController().release();
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void transfer(android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) {
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public java.util.List<android.media.MediaRouter2.RoutingController> getControllers() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(0, android.media.MediaRouter2.this.mSystemController);
            synchronized (android.media.MediaRouter2.this.mLock) {
                arrayList.addAll(android.media.MediaRouter2.this.mNonSystemRoutingControllers.values());
            }
            return arrayList;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setRouteVolume(android.media.MediaRoute2Info mediaRoute2Info, int i) {
            throw new java.lang.UnsupportedOperationException("setRouteVolume is only supported by proxy routers. See javadoc.");
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void setSessionVolume(int i, android.media.RoutingSessionInfo routingSessionInfo) {
            android.media.MediaRouter2.MediaRouter2Stub mediaRouter2Stub;
            synchronized (android.media.MediaRouter2.this.mLock) {
                mediaRouter2Stub = android.media.MediaRouter2.this.mStub;
            }
            if (mediaRouter2Stub != null) {
                try {
                    android.media.MediaRouter2.this.mMediaRouterService.setSessionVolumeWithRouter2(mediaRouter2Stub, routingSessionInfo.getId(), i);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.media.MediaRouter2.TAG, "setVolume: Failed to deliver request.", e);
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public java.util.List<android.media.MediaRoute2Info> filterRoutesWithIndividualPreference(java.util.List<android.media.MediaRoute2Info> list, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.media.MediaRoute2Info mediaRoute2Info : list) {
                if (mediaRoute2Info.hasAnyFeatures(routeDiscoveryPreference.getPreferredFeatures()) && (routeDiscoveryPreference.getAllowedPackages().isEmpty() || (mediaRoute2Info.getPackageName() != null && routeDiscoveryPreference.getAllowedPackages().contains(mediaRoute2Info.getPackageName())))) {
                    arrayList.add(mediaRoute2Info);
                }
            }
            return arrayList;
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void selectRoute(android.media.MediaRoute2Info mediaRoute2Info, android.media.RoutingSessionInfo routingSessionInfo) {
            android.media.MediaRouter2.MediaRouter2Stub mediaRouter2Stub;
            synchronized (android.media.MediaRouter2.this.mLock) {
                mediaRouter2Stub = android.media.MediaRouter2.this.mStub;
            }
            if (mediaRouter2Stub != null) {
                try {
                    android.media.MediaRouter2.this.mMediaRouterService.selectRouteWithRouter2(mediaRouter2Stub, routingSessionInfo.getId(), mediaRoute2Info);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.media.MediaRouter2.TAG, "Unable to select route for session.", e);
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void deselectRoute(android.media.MediaRoute2Info mediaRoute2Info, android.media.RoutingSessionInfo routingSessionInfo) {
            android.media.MediaRouter2.MediaRouter2Stub mediaRouter2Stub;
            synchronized (android.media.MediaRouter2.this.mLock) {
                mediaRouter2Stub = android.media.MediaRouter2.this.mStub;
            }
            if (mediaRouter2Stub != null) {
                try {
                    android.media.MediaRouter2.this.mMediaRouterService.deselectRouteWithRouter2(mediaRouter2Stub, routingSessionInfo.getId(), mediaRoute2Info);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.media.MediaRouter2.TAG, "Unable to deselect route from session.", e);
                }
            }
        }

        @Override // android.media.MediaRouter2.MediaRouter2Impl
        public void releaseSession(boolean z, boolean z2, android.media.MediaRouter2.RoutingController routingController) {
            synchronized (android.media.MediaRouter2.this.mLock) {
                android.media.MediaRouter2.this.mNonSystemRoutingControllers.remove(routingController.getId(), routingController);
                if (z && android.media.MediaRouter2.this.mStub != null) {
                    try {
                        android.media.MediaRouter2.this.mMediaRouterService.releaseSessionWithRouter2(android.media.MediaRouter2.this.mStub, routingController.getId());
                    } catch (android.os.RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                }
                if (z2) {
                    android.media.MediaRouter2.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: android.media.MediaRouter2$LocalMediaRouter2Impl$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            ((android.media.MediaRouter2) obj).notifyStop((android.media.MediaRouter2.RoutingController) obj2);
                        }
                    }, android.media.MediaRouter2.this, routingController));
                }
                try {
                    unregisterRouterStubIfNeededLocked(false);
                } catch (android.os.RemoteException e2) {
                    e2.rethrowFromSystemServer();
                }
            }
        }

        private void registerRouterStubIfNeededLocked() throws android.os.RemoteException {
            if (android.media.MediaRouter2.this.mStub == null) {
                android.media.MediaRouter2.MediaRouter2Stub mediaRouter2Stub = android.media.MediaRouter2.this.new MediaRouter2Stub();
                android.media.MediaRouter2.this.mMediaRouterService.registerRouter2(mediaRouter2Stub, this.mPackageName);
                android.media.MediaRouter2.this.mStub = mediaRouter2Stub;
            }
        }

        private void unregisterRouterStubIfNeededLocked(boolean z) throws android.os.RemoteException {
            if (android.media.MediaRouter2.this.mStub != null && android.media.MediaRouter2.this.mRouteCallbackRecords.isEmpty() && android.media.MediaRouter2.this.mNonSystemRoutingControllers.isEmpty()) {
                if (android.media.MediaRouter2.this.mScanRequestsMap.size() == 0 || z) {
                    android.media.MediaRouter2.this.mMediaRouterService.unregisterRouter2(android.media.MediaRouter2.this.mStub);
                    android.media.MediaRouter2.this.mStub = null;
                }
            }
        }
    }
}
