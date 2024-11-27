package com.android.server.media;

/* loaded from: classes2.dex */
final class MediaRoute2ProviderServiceProxy extends com.android.server.media.MediaRoute2Provider implements android.content.ServiceConnection {
    private com.android.server.media.MediaRoute2ProviderServiceProxy.Connection mActiveConnection;
    private boolean mBound;
    private boolean mConnectionReady;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private boolean mIsManagerScanning;
    private final boolean mIsSelfScanOnlyProvider;
    private android.media.RouteDiscoveryPreference mLastDiscoveryPreference;
    private boolean mLastDiscoveryPreferenceIncludesThisPackage;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final java.util.List<android.media.RoutingSessionInfo> mReleasingSessions;
    private boolean mRunning;
    private final int mUserId;
    private static final java.lang.String TAG = "MR2ProviderSvcProxy";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    MediaRoute2ProviderServiceProxy(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.ComponentName componentName, boolean z, int i) {
        super(componentName);
        this.mLastDiscoveryPreference = null;
        this.mLastDiscoveryPreferenceIncludesThisPackage = false;
        this.mReleasingSessions = new java.util.ArrayList();
        java.util.Objects.requireNonNull(context, "Context must not be null.");
        this.mContext = context;
        this.mIsSelfScanOnlyProvider = z;
        this.mUserId = i;
        this.mHandler = new android.os.Handler(android.os.Looper.myLooper());
    }

    public void setManagerScanning(boolean z) {
        if (this.mIsManagerScanning != z) {
            this.mIsManagerScanning = z;
            updateBinding();
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void requestCreateSession(long j, java.lang.String str, java.lang.String str2, android.os.Bundle bundle, int i, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str3) {
        if (this.mConnectionReady) {
            this.mActiveConnection.requestCreateSession(j, str, str2, bundle);
            updateBinding();
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void releaseSession(long j, java.lang.String str) {
        if (this.mConnectionReady) {
            this.mActiveConnection.releaseSession(j, str);
            updateBinding();
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void updateDiscoveryPreference(java.util.Set<java.lang.String> set, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
        this.mLastDiscoveryPreference = routeDiscoveryPreference;
        this.mLastDiscoveryPreferenceIncludesThisPackage = set.contains(this.mComponentName.getPackageName());
        if (this.mConnectionReady) {
            this.mActiveConnection.updateDiscoveryPreference(routeDiscoveryPreference);
        }
        updateBinding();
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void selectRoute(long j, java.lang.String str, java.lang.String str2) {
        if (this.mConnectionReady) {
            this.mActiveConnection.selectRoute(j, str, str2);
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void deselectRoute(long j, java.lang.String str, java.lang.String str2) {
        if (this.mConnectionReady) {
            this.mActiveConnection.deselectRoute(j, str, str2);
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void transferToRoute(long j, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        if (this.mConnectionReady) {
            this.mActiveConnection.transferToRoute(j, str2, str3);
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void setRouteVolume(long j, java.lang.String str, int i) {
        if (this.mConnectionReady) {
            this.mActiveConnection.setRouteVolume(j, str, i);
            updateBinding();
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void setSessionVolume(long j, java.lang.String str, int i) {
        if (this.mConnectionReady) {
            this.mActiveConnection.setSessionVolume(j, str, i);
            updateBinding();
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void prepareReleaseSession(@android.annotation.NonNull java.lang.String str) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<android.media.RoutingSessionInfo> it = this.mSessionInfos.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    android.media.RoutingSessionInfo next = it.next();
                    if (android.text.TextUtils.equals(next.getId(), str)) {
                        this.mSessionInfos.remove(next);
                        this.mReleasingSessions.add(next);
                        break;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public boolean hasComponentName(java.lang.String str, java.lang.String str2) {
        return this.mComponentName.getPackageName().equals(str) && this.mComponentName.getClassName().equals(str2);
    }

    public void start(boolean z) {
        if (!this.mRunning) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": Starting");
            }
            this.mRunning = true;
            if (!com.android.media.flags.Flags.enablePreventionOfKeepAliveRouteProviders()) {
                updateBinding();
            }
        }
        if (z && this.mActiveConnection == null && shouldBind()) {
            unbind();
            bind();
        }
    }

    public void stop() {
        if (this.mRunning) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": Stopping");
            }
            this.mRunning = false;
            updateBinding();
        }
    }

    private void updateBinding() {
        if (shouldBind()) {
            bind();
        } else {
            unbind();
        }
    }

    private boolean shouldBind() {
        if (!this.mRunning) {
            return false;
        }
        if (!getSessionInfos().isEmpty() || this.mIsManagerScanning) {
            return true;
        }
        if ((this.mLastDiscoveryPreference == null || this.mLastDiscoveryPreference.getPreferredFeatures().isEmpty()) ? false : true) {
            return this.mLastDiscoveryPreferenceIncludesThisPackage || !this.mIsSelfScanOnlyProvider;
        }
        return false;
    }

    private void bind() {
        if (!this.mBound) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": Binding");
            }
            android.content.Intent intent = new android.content.Intent("android.media.MediaRoute2ProviderService");
            intent.setComponent(this.mComponentName);
            try {
                this.mBound = this.mContext.bindServiceAsUser(intent, this, android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN, new android.os.UserHandle(this.mUserId));
                if (!this.mBound && DEBUG) {
                    android.util.Slog.d(TAG, this + ": Bind failed");
                }
            } catch (java.lang.SecurityException e) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, this + ": Bind failed", e);
                }
            }
        }
    }

    private void unbind() {
        if (this.mBound) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": Unbinding");
            }
            this.mBound = false;
            disconnect();
            this.mContext.unbindService(this);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        if (DEBUG) {
            android.util.Slog.d(TAG, this + ": Connected");
        }
        if (this.mBound) {
            disconnect();
            android.media.IMediaRoute2ProviderService asInterface = android.media.IMediaRoute2ProviderService.Stub.asInterface(iBinder);
            if (asInterface != null) {
                com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection = new com.android.server.media.MediaRoute2ProviderServiceProxy.Connection(asInterface);
                if (connection.register()) {
                    this.mActiveConnection = connection;
                    return;
                } else {
                    if (DEBUG) {
                        android.util.Slog.d(TAG, this + ": Registration failed");
                        return;
                    }
                    return;
                }
            }
            android.util.Slog.e(TAG, this + ": Service returned invalid binder");
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(android.content.ComponentName componentName) {
        if (DEBUG) {
            android.util.Slog.d(TAG, this + ": Service disconnected");
        }
        disconnect();
    }

    @Override // android.content.ServiceConnection
    public void onBindingDied(android.content.ComponentName componentName) {
        unbind();
        if (com.android.media.flags.Flags.enablePreventionOfKeepAliveRouteProviders()) {
            android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("Route provider service (%s) binding died, but we did not rebind.", new java.lang.Object[]{componentName.toString()}));
        } else if (shouldBind()) {
            android.util.Slog.w(TAG, android.text.TextUtils.formatSimple("Rebound to provider service (%s) after binding died.", new java.lang.Object[]{componentName.toString()}));
            bind();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectionReady(com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection) {
        java.util.Set<java.lang.String> of;
        if (this.mActiveConnection == connection) {
            this.mConnectionReady = true;
            if (this.mLastDiscoveryPreference != null) {
                if (this.mLastDiscoveryPreferenceIncludesThisPackage) {
                    of = java.util.Set.of(this.mComponentName.getPackageName());
                } else {
                    of = java.util.Set.of();
                }
                updateDiscoveryPreference(of, this.mLastDiscoveryPreference);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectionDied(com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                android.util.Slog.d(TAG, this + ": Service connection died");
            }
            disconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProviderUpdated(com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection, android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo) {
        if (this.mActiveConnection != connection) {
            return;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, this + ": updated");
        }
        setAndNotifyProviderState(mediaRoute2ProviderInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSessionCreated(com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection, long j, android.media.RoutingSessionInfo routingSessionInfo) {
        if (this.mActiveConnection != connection) {
            return;
        }
        if (routingSessionInfo == null) {
            android.util.Slog.w(TAG, "onSessionCreated: Ignoring null session sent from " + this.mComponentName);
            return;
        }
        android.media.RoutingSessionInfo assignProviderIdForSession = assignProviderIdForSession(routingSessionInfo);
        final java.lang.String id = assignProviderIdForSession.getId();
        synchronized (this.mLock) {
            if (!this.mSessionInfos.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$onSessionCreated$0;
                    lambda$onSessionCreated$0 = com.android.server.media.MediaRoute2ProviderServiceProxy.lambda$onSessionCreated$0(id, (android.media.RoutingSessionInfo) obj);
                    return lambda$onSessionCreated$0;
                }
            }) && !this.mReleasingSessions.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$onSessionCreated$1;
                    lambda$onSessionCreated$1 = com.android.server.media.MediaRoute2ProviderServiceProxy.lambda$onSessionCreated$1(id, (android.media.RoutingSessionInfo) obj);
                    return lambda$onSessionCreated$1;
                }
            })) {
                this.mSessionInfos.add(assignProviderIdForSession);
                this.mCallback.onSessionCreated(this, j, assignProviderIdForSession);
                return;
            }
            android.util.Slog.w(TAG, "onSessionCreated: Duplicate session already exists. Ignoring.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onSessionCreated$0(java.lang.String str, android.media.RoutingSessionInfo routingSessionInfo) {
        return android.text.TextUtils.equals(routingSessionInfo.getId(), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onSessionCreated$1(java.lang.String str, android.media.RoutingSessionInfo routingSessionInfo) {
        return android.text.TextUtils.equals(routingSessionInfo.getId(), str);
    }

    private int findSessionByIdLocked(android.media.RoutingSessionInfo routingSessionInfo) {
        for (int i = 0; i < this.mSessionInfos.size(); i++) {
            if (android.text.TextUtils.equals(this.mSessionInfos.get(i).getId(), routingSessionInfo.getId())) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSessionsUpdated(com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection, java.util.List<android.media.RoutingSessionInfo> list) {
        if (this.mActiveConnection != connection) {
            return;
        }
        synchronized (this.mLock) {
            try {
                int i = 0;
                for (android.media.RoutingSessionInfo routingSessionInfo : list) {
                    if (routingSessionInfo != null) {
                        android.media.RoutingSessionInfo assignProviderIdForSession = assignProviderIdForSession(routingSessionInfo);
                        int findSessionByIdLocked = findSessionByIdLocked(assignProviderIdForSession);
                        if (findSessionByIdLocked < 0) {
                            this.mSessionInfos.add(i, assignProviderIdForSession);
                            dispatchSessionCreated(0L, assignProviderIdForSession);
                            i++;
                        } else if (findSessionByIdLocked < i) {
                            android.util.Slog.w(TAG, "Ignoring duplicate session ID: " + assignProviderIdForSession.getId());
                        } else {
                            this.mSessionInfos.set(findSessionByIdLocked, assignProviderIdForSession);
                            java.util.Collections.swap(this.mSessionInfos, findSessionByIdLocked, i);
                            dispatchSessionUpdated(assignProviderIdForSession);
                            i++;
                        }
                    }
                }
                for (int size = this.mSessionInfos.size() - 1; size >= i; size--) {
                    dispatchSessionReleased(this.mSessionInfos.remove(size));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSessionReleased(com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection, android.media.RoutingSessionInfo routingSessionInfo) {
        boolean z;
        if (this.mActiveConnection != connection) {
            return;
        }
        if (routingSessionInfo == null) {
            android.util.Slog.w(TAG, "onSessionReleased: Ignoring null session sent from " + this.mComponentName);
            return;
        }
        android.media.RoutingSessionInfo assignProviderIdForSession = assignProviderIdForSession(routingSessionInfo);
        synchronized (this.mLock) {
            try {
                java.util.Iterator<android.media.RoutingSessionInfo> it = this.mSessionInfos.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    android.media.RoutingSessionInfo next = it.next();
                    if (android.text.TextUtils.equals(next.getId(), assignProviderIdForSession.getId())) {
                        this.mSessionInfos.remove(next);
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    for (android.media.RoutingSessionInfo routingSessionInfo2 : this.mReleasingSessions) {
                        if (android.text.TextUtils.equals(routingSessionInfo2.getId(), assignProviderIdForSession.getId())) {
                            this.mReleasingSessions.remove(routingSessionInfo2);
                            return;
                        }
                    }
                }
                if (!z) {
                    android.util.Slog.w(TAG, "onSessionReleased: Matching session info not found");
                } else {
                    this.mCallback.onSessionReleased(this, assignProviderIdForSession);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void dispatchSessionCreated(long j, android.media.RoutingSessionInfo routingSessionInfo) {
        android.os.Handler handler = this.mHandler;
        final com.android.server.media.MediaRoute2Provider.Callback callback = this.mCallback;
        java.util.Objects.requireNonNull(callback);
        handler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$$ExternalSyntheticLambda4
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                com.android.server.media.MediaRoute2Provider.Callback.this.onSessionCreated((com.android.server.media.MediaRoute2ProviderServiceProxy) obj, ((java.lang.Long) obj2).longValue(), (android.media.RoutingSessionInfo) obj3);
            }
        }, this, java.lang.Long.valueOf(j), routingSessionInfo));
    }

    private void dispatchSessionUpdated(android.media.RoutingSessionInfo routingSessionInfo) {
        android.os.Handler handler = this.mHandler;
        final com.android.server.media.MediaRoute2Provider.Callback callback = this.mCallback;
        java.util.Objects.requireNonNull(callback);
        handler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.media.MediaRoute2Provider.Callback.this.onSessionUpdated((com.android.server.media.MediaRoute2ProviderServiceProxy) obj, (android.media.RoutingSessionInfo) obj2);
            }
        }, this, routingSessionInfo));
    }

    private void dispatchSessionReleased(android.media.RoutingSessionInfo routingSessionInfo) {
        android.os.Handler handler = this.mHandler;
        final com.android.server.media.MediaRoute2Provider.Callback callback = this.mCallback;
        java.util.Objects.requireNonNull(callback);
        handler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.media.MediaRoute2Provider.Callback.this.onSessionReleased((com.android.server.media.MediaRoute2ProviderServiceProxy) obj, (android.media.RoutingSessionInfo) obj2);
            }
        }, this, routingSessionInfo));
    }

    private android.media.RoutingSessionInfo assignProviderIdForSession(android.media.RoutingSessionInfo routingSessionInfo) {
        return new android.media.RoutingSessionInfo.Builder(routingSessionInfo).setOwnerPackageName(this.mComponentName.getPackageName()).setProviderId(getUniqueId()).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRequestFailed(com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection, long j, int i) {
        if (this.mActiveConnection != connection) {
            return;
        }
        if (j == 0) {
            android.util.Slog.w(TAG, "onRequestFailed: Ignoring requestId REQUEST_ID_NONE");
        } else {
            this.mCallback.onRequestFailed(this, j, i);
        }
    }

    private void disconnect() {
        if (this.mActiveConnection != null) {
            this.mConnectionReady = false;
            this.mActiveConnection.dispose();
            this.mActiveConnection = null;
            setAndNotifyProviderState(null);
            synchronized (this.mLock) {
                try {
                    java.util.Iterator<android.media.RoutingSessionInfo> it = this.mSessionInfos.iterator();
                    while (it.hasNext()) {
                        this.mCallback.onSessionReleased(this, it.next());
                    }
                    this.mSessionInfos.clear();
                    this.mReleasingSessions.clear();
                } finally {
                }
            }
        }
    }

    @Override // com.android.server.media.MediaRoute2Provider
    protected java.lang.String getDebugString() {
        return android.text.TextUtils.formatSimple("ProviderServiceProxy - package: %s, bound: %b, connection (active:%b, ready:%b)", new java.lang.Object[]{this.mComponentName.getPackageName(), java.lang.Boolean.valueOf(this.mBound), java.lang.Boolean.valueOf(this.mActiveConnection != null), java.lang.Boolean.valueOf(this.mConnectionReady)});
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class Connection implements android.os.IBinder.DeathRecipient {
        private final com.android.server.media.MediaRoute2ProviderServiceProxy.ServiceCallbackStub mCallbackStub = new com.android.server.media.MediaRoute2ProviderServiceProxy.ServiceCallbackStub(this);
        private final android.media.IMediaRoute2ProviderService mService;

        Connection(android.media.IMediaRoute2ProviderService iMediaRoute2ProviderService) {
            this.mService = iMediaRoute2ProviderService;
        }

        public boolean register() {
            try {
                this.mService.asBinder().linkToDeath(this, 0);
                this.mService.setCallback(this.mCallbackStub);
                com.android.server.media.MediaRoute2ProviderServiceProxy.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.media.MediaRoute2ProviderServiceProxy.Connection.this.lambda$register$0();
                    }
                });
                return true;
            } catch (android.os.RemoteException e) {
                binderDied();
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$register$0() {
            com.android.server.media.MediaRoute2ProviderServiceProxy.this.onConnectionReady(this);
        }

        public void dispose() {
            this.mService.asBinder().unlinkToDeath(this, 0);
            this.mCallbackStub.dispose();
        }

        public void requestCreateSession(long j, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
            try {
                this.mService.requestCreateSession(j, str, str2, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaRoute2ProviderServiceProxy.TAG, "requestCreateSession: Failed to deliver request.");
            }
        }

        public void releaseSession(long j, java.lang.String str) {
            try {
                this.mService.releaseSession(j, str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaRoute2ProviderServiceProxy.TAG, "releaseSession: Failed to deliver request.");
            }
        }

        public void updateDiscoveryPreference(android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
            try {
                this.mService.updateDiscoveryPreference(routeDiscoveryPreference);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaRoute2ProviderServiceProxy.TAG, "updateDiscoveryPreference: Failed to deliver request.");
            }
        }

        public void selectRoute(long j, java.lang.String str, java.lang.String str2) {
            try {
                this.mService.selectRoute(j, str, str2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaRoute2ProviderServiceProxy.TAG, "selectRoute: Failed to deliver request.", e);
            }
        }

        public void deselectRoute(long j, java.lang.String str, java.lang.String str2) {
            try {
                this.mService.deselectRoute(j, str, str2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaRoute2ProviderServiceProxy.TAG, "deselectRoute: Failed to deliver request.", e);
            }
        }

        public void transferToRoute(long j, java.lang.String str, java.lang.String str2) {
            try {
                this.mService.transferToRoute(j, str, str2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaRoute2ProviderServiceProxy.TAG, "transferToRoute: Failed to deliver request.", e);
            }
        }

        public void setRouteVolume(long j, java.lang.String str, int i) {
            try {
                this.mService.setRouteVolume(j, str, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaRoute2ProviderServiceProxy.TAG, "setRouteVolume: Failed to deliver request.", e);
            }
        }

        public void setSessionVolume(long j, java.lang.String str, int i) {
            try {
                this.mService.setSessionVolume(j, str, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaRoute2ProviderServiceProxy.TAG, "setSessionVolume: Failed to deliver request.", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$binderDied$1() {
            com.android.server.media.MediaRoute2ProviderServiceProxy.this.onConnectionDied(this);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.media.MediaRoute2ProviderServiceProxy.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.media.MediaRoute2ProviderServiceProxy.Connection.this.lambda$binderDied$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$postProviderUpdated$2(android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.this.onProviderUpdated(this, mediaRoute2ProviderInfo);
        }

        void postProviderUpdated(final android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.media.MediaRoute2ProviderServiceProxy.Connection.this.lambda$postProviderUpdated$2(mediaRoute2ProviderInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$postSessionCreated$3(long j, android.media.RoutingSessionInfo routingSessionInfo) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.this.onSessionCreated(this, j, routingSessionInfo);
        }

        void postSessionCreated(final long j, final android.media.RoutingSessionInfo routingSessionInfo) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.media.MediaRoute2ProviderServiceProxy.Connection.this.lambda$postSessionCreated$3(j, routingSessionInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$postSessionsUpdated$4(java.util.List list) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.this.onSessionsUpdated(this, list);
        }

        void postSessionsUpdated(final java.util.List<android.media.RoutingSessionInfo> list) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.media.MediaRoute2ProviderServiceProxy.Connection.this.lambda$postSessionsUpdated$4(list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$postSessionReleased$5(android.media.RoutingSessionInfo routingSessionInfo) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.this.onSessionReleased(this, routingSessionInfo);
        }

        void postSessionReleased(final android.media.RoutingSessionInfo routingSessionInfo) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.media.MediaRoute2ProviderServiceProxy.Connection.this.lambda$postSessionReleased$5(routingSessionInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$postRequestFailed$6(long j, int i) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.this.onRequestFailed(this, j, i);
        }

        void postRequestFailed(final long j, final int i) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.media.MediaRoute2ProviderServiceProxy.Connection.this.lambda$postRequestFailed$6(j, i);
                }
            });
        }
    }

    private static final class ServiceCallbackStub extends android.media.IMediaRoute2ProviderServiceCallback.Stub {
        private final java.lang.ref.WeakReference<com.android.server.media.MediaRoute2ProviderServiceProxy.Connection> mConnectionRef;

        ServiceCallbackStub(com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection) {
            this.mConnectionRef = new java.lang.ref.WeakReference<>(connection);
        }

        public void dispose() {
            this.mConnectionRef.clear();
        }

        public void notifyProviderUpdated(android.media.MediaRoute2ProviderInfo mediaRoute2ProviderInfo) {
            for (android.media.MediaRoute2Info mediaRoute2Info : mediaRoute2ProviderInfo.getRoutes()) {
                if (mediaRoute2Info.isSystemRoute()) {
                    throw new java.lang.SecurityException("Only the system is allowed to publish system routes. Disallowed route: " + mediaRoute2Info);
                }
                if (mediaRoute2Info.getSuitabilityStatus() == 2) {
                    throw new java.lang.SecurityException("Only the system is allowed to set not suitable for transfer status. Disallowed route: " + mediaRoute2Info);
                }
            }
            com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection = this.mConnectionRef.get();
            if (connection != null) {
                connection.postProviderUpdated(mediaRoute2ProviderInfo);
            }
        }

        public void notifySessionCreated(long j, android.media.RoutingSessionInfo routingSessionInfo) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection = this.mConnectionRef.get();
            if (connection != null) {
                connection.postSessionCreated(j, routingSessionInfo);
            }
        }

        public void notifySessionsUpdated(java.util.List<android.media.RoutingSessionInfo> list) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection = this.mConnectionRef.get();
            if (connection != null) {
                connection.postSessionsUpdated(list);
            }
        }

        public void notifySessionReleased(android.media.RoutingSessionInfo routingSessionInfo) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection = this.mConnectionRef.get();
            if (connection != null) {
                connection.postSessionReleased(routingSessionInfo);
            }
        }

        public void notifyRequestFailed(long j, int i) {
            com.android.server.media.MediaRoute2ProviderServiceProxy.Connection connection = this.mConnectionRef.get();
            if (connection != null) {
                connection.postRequestFailed(j, i);
            }
        }
    }
}
