package com.android.server.media;

/* loaded from: classes2.dex */
class SystemMediaRoute2Provider extends com.android.server.media.MediaRoute2Provider {
    static final java.lang.String SYSTEM_SESSION_ID = "SYSTEM_SESSION";
    private final android.media.AudioManager mAudioManager;
    private final com.android.server.media.SystemMediaRoute2Provider.AudioManagerBroadcastReceiver mAudioReceiver;
    private final com.android.server.media.BluetoothRouteController mBluetoothRouteController;
    private final android.content.Context mContext;
    android.media.MediaRoute2Info mDefaultRoute;
    android.media.RoutingSessionInfo mDefaultSessionInfo;
    private final com.android.server.media.DeviceRouteController mDeviceRouteController;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mRequestLock"})
    private volatile com.android.server.media.SystemMediaRoute2Provider.SessionCreationRequest mPendingSessionCreationRequest;

    @com.android.internal.annotations.GuardedBy({"mTransferLock"})
    @android.annotation.Nullable
    private volatile com.android.server.media.SystemMediaRoute2Provider.SessionCreationRequest mPendingTransferRequest;
    private final java.lang.Object mRequestLock;
    private java.lang.String mSelectedRouteId;
    private final java.lang.Object mTransferLock;
    private final android.os.UserHandle mUser;
    static final java.lang.String TAG = "MR2SystemProvider";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final android.content.ComponentName COMPONENT_NAME = new android.content.ComponentName(com.android.server.media.SystemMediaRoute2Provider.class.getPackage().getName(), com.android.server.media.SystemMediaRoute2Provider.class.getName());

    SystemMediaRoute2Provider(android.content.Context context, android.os.UserHandle userHandle) {
        super(COMPONENT_NAME);
        this.mAudioReceiver = new com.android.server.media.SystemMediaRoute2Provider.AudioManagerBroadcastReceiver();
        this.mRequestLock = new java.lang.Object();
        this.mTransferLock = new java.lang.Object();
        this.mIsSystemRouteProvider = true;
        this.mContext = context;
        this.mUser = userHandle;
        android.os.Looper mainLooper = android.os.Looper.getMainLooper();
        this.mHandler = new android.os.Handler(mainLooper);
        this.mAudioManager = (android.media.AudioManager) context.getSystemService("audio");
        this.mBluetoothRouteController = com.android.server.media.BluetoothRouteController.createInstance(context, new com.android.server.media.BluetoothRouteController.BluetoothRoutesUpdatedListener() { // from class: com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda3
            @Override // com.android.server.media.BluetoothRouteController.BluetoothRoutesUpdatedListener
            public final void onBluetoothRoutesUpdated() {
                com.android.server.media.SystemMediaRoute2Provider.this.lambda$new$0();
            }
        });
        this.mDeviceRouteController = com.android.server.media.DeviceRouteController.createInstance(context, mainLooper, new com.android.server.media.DeviceRouteController.OnDeviceRouteChangedListener() { // from class: com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda4
            @Override // com.android.server.media.DeviceRouteController.OnDeviceRouteChangedListener
            public final void onDeviceRouteChanged() {
                com.android.server.media.SystemMediaRoute2Provider.this.lambda$new$2();
            }
        });
        updateProviderState();
        updateSessionInfosIfNeeded();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        publishProviderState();
        if (updateSessionInfosIfNeeded()) {
            notifySessionInfoUpdated();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.media.SystemMediaRoute2Provider.this.lambda$new$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        publishProviderState();
        if (updateSessionInfosIfNeeded()) {
            notifySessionInfoUpdated();
        }
    }

    public void start() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
        this.mContext.registerReceiverAsUser(this.mAudioReceiver, this.mUser, intentFilter, null, null);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.media.SystemMediaRoute2Provider.this.lambda$start$3();
            }
        });
        updateVolume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$3() {
        this.mDeviceRouteController.start(this.mUser);
        this.mBluetoothRouteController.start(this.mUser);
    }

    public void stop() {
        this.mContext.unregisterReceiver(this.mAudioReceiver);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.media.SystemMediaRoute2Provider.this.lambda$stop$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stop$4() {
        this.mBluetoothRouteController.stop();
        this.mDeviceRouteController.stop();
        notifyProviderState();
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void setCallback(com.android.server.media.MediaRoute2Provider.Callback callback) {
        super.setCallback(callback);
        notifyProviderState();
        notifySessionInfoUpdated();
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void requestCreateSession(long j, java.lang.String str, java.lang.String str2, android.os.Bundle bundle, int i, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str3) {
        android.media.RoutingSessionInfo routingSessionInfo;
        if (android.text.TextUtils.equals(str2, "DEFAULT_ROUTE")) {
            this.mCallback.onSessionCreated(this, j, this.mDefaultSessionInfo);
            return;
        }
        if (!com.android.media.flags.Flags.enableBuiltInSpeakerRouteSuitabilityStatuses() && android.text.TextUtils.equals(str2, this.mSelectedRouteId)) {
            synchronized (this.mLock) {
                routingSessionInfo = this.mSessionInfos.get(0);
            }
            this.mCallback.onSessionCreated(this, j, routingSessionInfo);
            return;
        }
        synchronized (this.mRequestLock) {
            try {
                if (this.mPendingSessionCreationRequest != null) {
                    this.mCallback.onRequestFailed(this, this.mPendingSessionCreationRequest.mRequestId, 0);
                }
                this.mPendingSessionCreationRequest = new com.android.server.media.SystemMediaRoute2Provider.SessionCreationRequest(j, str2, 0, userHandle, str3);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        transferToRoute(j, userHandle, str3, SYSTEM_SESSION_ID, str2, i);
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void releaseSession(long j, java.lang.String str) {
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void updateDiscoveryPreference(java.util.Set<java.lang.String> set, android.media.RouteDiscoveryPreference routeDiscoveryPreference) {
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void selectRoute(long j, java.lang.String str, java.lang.String str2) {
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void deselectRoute(long j, java.lang.String str, java.lang.String str2) {
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void transferToRoute(long j, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str, java.lang.String str2, final java.lang.String str3, int i) {
        if (android.text.TextUtils.equals(str3, "DEFAULT_ROUTE")) {
            android.util.Log.w(TAG, "Ignoring transfer to DEFAULT_ROUTE");
            return;
        }
        if (com.android.media.flags.Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()) {
            synchronized (this.mTransferLock) {
                this.mPendingTransferRequest = new com.android.server.media.SystemMediaRoute2Provider.SessionCreationRequest(j, str3, i, userHandle, str);
            }
        }
        android.media.MediaRoute2Info selectedRoute = this.mDeviceRouteController.getSelectedRoute();
        boolean anyMatch = this.mDeviceRouteController.getAvailableRoutes().stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.media.SystemMediaRoute2Provider$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$transferToRoute$5;
                lambda$transferToRoute$5 = com.android.server.media.SystemMediaRoute2Provider.lambda$transferToRoute$5(str3, (android.media.MediaRoute2Info) obj);
                return lambda$transferToRoute$5;
            }
        });
        if (!android.text.TextUtils.equals(str3, selectedRoute.getId()) && !anyMatch) {
            this.mDeviceRouteController.transferTo(null);
            this.mBluetoothRouteController.transferTo(str3);
        } else {
            this.mDeviceRouteController.transferTo(str3);
            this.mBluetoothRouteController.transferTo(null);
        }
        if (com.android.media.flags.Flags.enableBuiltInSpeakerRouteSuitabilityStatuses() && updateSessionInfosIfNeeded()) {
            notifySessionInfoUpdated();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$transferToRoute$5(java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) {
        return mediaRoute2Info.getId().equals(str);
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void setRouteVolume(long j, java.lang.String str, int i) {
        if (!android.text.TextUtils.equals(str, this.mSelectedRouteId)) {
            return;
        }
        this.mAudioManager.setStreamVolume(3, i, 0);
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void setSessionVolume(long j, java.lang.String str, int i) {
    }

    @Override // com.android.server.media.MediaRoute2Provider
    public void prepareReleaseSession(java.lang.String str) {
    }

    public android.media.MediaRoute2Info getDefaultRoute() {
        return this.mDefaultRoute;
    }

    public android.media.RoutingSessionInfo getDefaultSessionInfo() {
        return this.mDefaultSessionInfo;
    }

    @android.annotation.Nullable
    public android.media.RoutingSessionInfo generateDeviceRouteSelectedSessionInfo(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                if (this.mSessionInfos.isEmpty()) {
                    return null;
                }
                android.media.MediaRoute2Info selectedRoute = this.mDeviceRouteController.getSelectedRoute();
                android.media.RoutingSessionInfo.Builder systemSession = new android.media.RoutingSessionInfo.Builder(SYSTEM_SESSION_ID, str).setSystemSession(true);
                systemSession.addSelectedRoute(selectedRoute.getId());
                java.util.Iterator<android.media.MediaRoute2Info> it = this.mBluetoothRouteController.getAllBluetoothRoutes().iterator();
                while (it.hasNext()) {
                    systemSession.addTransferableRoute(it.next().getId());
                }
                if (com.android.media.flags.Flags.enableAudioPoliciesDeviceAndBluetoothController()) {
                    for (android.media.MediaRoute2Info mediaRoute2Info : this.mDeviceRouteController.getAvailableRoutes()) {
                        if (!android.text.TextUtils.equals(selectedRoute.getId(), mediaRoute2Info.getId())) {
                            systemSession.addTransferableRoute(mediaRoute2Info.getId());
                        }
                    }
                }
                return systemSession.setProviderId(this.mUniqueId).build();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void updateProviderState() {
        android.media.MediaRoute2ProviderInfo.Builder builder = new android.media.MediaRoute2ProviderInfo.Builder();
        if (com.android.media.flags.Flags.enableAudioPoliciesDeviceAndBluetoothController()) {
            java.util.Iterator<android.media.MediaRoute2Info> it = this.mDeviceRouteController.getAvailableRoutes().iterator();
            while (it.hasNext()) {
                builder.addRoute(it.next());
            }
            setProviderState(builder.build());
        } else {
            builder.addRoute(this.mDeviceRouteController.getSelectedRoute());
        }
        java.util.Iterator<android.media.MediaRoute2Info> it2 = this.mBluetoothRouteController.getAllBluetoothRoutes().iterator();
        while (it2.hasNext()) {
            builder.addRoute(it2.next());
        }
        android.media.MediaRoute2ProviderInfo build = builder.build();
        setProviderState(build);
        if (DEBUG) {
            android.util.Slog.d(TAG, "Updating system provider info : " + build);
        }
    }

    boolean updateSessionInfosIfNeeded() {
        int i;
        android.os.UserHandle userHandle;
        java.lang.String str;
        synchronized (this.mLock) {
            try {
                android.media.RoutingSessionInfo routingSessionInfo = this.mSessionInfos.isEmpty() ? null : this.mSessionInfos.get(0);
                android.media.RoutingSessionInfo.Builder systemSession = new android.media.RoutingSessionInfo.Builder(SYSTEM_SESSION_ID, "").setSystemSession(true);
                android.media.MediaRoute2Info selectedRoute = this.mDeviceRouteController.getSelectedRoute();
                android.media.MediaRoute2Info selectedRoute2 = this.mBluetoothRouteController.getSelectedRoute();
                java.util.ArrayList arrayList = new java.util.ArrayList();
                if (selectedRoute2 != null) {
                    arrayList.add(selectedRoute.getId());
                    selectedRoute = selectedRoute2;
                }
                this.mSelectedRouteId = selectedRoute.getId();
                this.mDefaultRoute = new android.media.MediaRoute2Info.Builder("DEFAULT_ROUTE", selectedRoute).setSystemRoute(true).setProviderId(this.mUniqueId).build();
                systemSession.addSelectedRoute(this.mSelectedRouteId);
                if (com.android.media.flags.Flags.enableAudioPoliciesDeviceAndBluetoothController()) {
                    java.util.Iterator<android.media.MediaRoute2Info> it = this.mDeviceRouteController.getAvailableRoutes().iterator();
                    while (it.hasNext()) {
                        java.lang.String id = it.next().getId();
                        if (!this.mSelectedRouteId.equals(id)) {
                            arrayList.add(id);
                        }
                    }
                }
                java.util.Iterator<android.media.MediaRoute2Info> it2 = this.mBluetoothRouteController.getTransferableRoutes().iterator();
                while (it2.hasNext()) {
                    arrayList.add(it2.next().getId());
                }
                java.util.Iterator it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    systemSession.addTransferableRoute((java.lang.String) it3.next());
                }
                if (com.android.media.flags.Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()) {
                    if (routingSessionInfo != null && containsSelectedRouteWithId(routingSessionInfo, selectedRoute.getId())) {
                        i = routingSessionInfo.getTransferReason();
                        userHandle = routingSessionInfo.getTransferInitiatorUserHandle();
                        str = routingSessionInfo.getTransferInitiatorPackageName();
                    } else {
                        i = 0;
                        userHandle = null;
                        str = null;
                    }
                    synchronized (this.mTransferLock) {
                        try {
                            if (this.mPendingTransferRequest != null) {
                                boolean isTargetRoute = this.mPendingTransferRequest.isTargetRoute(selectedRoute);
                                boolean isInsideOfRoutesList = this.mPendingTransferRequest.isInsideOfRoutesList(arrayList);
                                if (isTargetRoute) {
                                    i = this.mPendingTransferRequest.mTransferReason;
                                    userHandle = this.mPendingTransferRequest.mTransferInitiatorUserHandle;
                                    str = this.mPendingTransferRequest.mTransferInitiatorPackageName;
                                    this.mPendingTransferRequest = null;
                                } else if (!isInsideOfRoutesList) {
                                    this.mPendingTransferRequest = null;
                                }
                            }
                        } finally {
                        }
                    }
                    systemSession.setTransferReason(i).setTransferInitiator(userHandle, str);
                }
                android.media.RoutingSessionInfo build = systemSession.setProviderId(this.mUniqueId).build();
                synchronized (this.mRequestLock) {
                    reportPendingSessionRequestResultLockedIfNeeded(build);
                }
                if (java.util.Objects.equals(routingSessionInfo, build)) {
                    return false;
                }
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Updating system routing session info : " + build);
                }
                this.mSessionInfos.clear();
                this.mSessionInfos.add(build);
                this.mDefaultSessionInfo = new android.media.RoutingSessionInfo.Builder(SYSTEM_SESSION_ID, "").setProviderId(this.mUniqueId).setSystemSession(true).addSelectedRoute("DEFAULT_ROUTE").build();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mRequestLock"})
    private void reportPendingSessionRequestResultLockedIfNeeded(android.media.RoutingSessionInfo routingSessionInfo) {
        if (this.mPendingSessionCreationRequest == null) {
            return;
        }
        long j = this.mPendingSessionCreationRequest.mRequestId;
        if (android.text.TextUtils.equals(this.mSelectedRouteId, this.mPendingSessionCreationRequest.mRouteId)) {
            if (DEBUG) {
                android.util.Slog.w(TAG, "Session creation success to route " + this.mPendingSessionCreationRequest.mRouteId);
            }
            this.mPendingSessionCreationRequest = null;
            this.mCallback.onSessionCreated(this, j, routingSessionInfo);
            return;
        }
        boolean isRequestedRouteConnectedBtRoute = isRequestedRouteConnectedBtRoute();
        if (!com.android.media.flags.Flags.enableWaitingStateForSystemSessionCreationRequest() || !isRequestedRouteConnectedBtRoute) {
            if (DEBUG) {
                android.util.Slog.w(TAG, "Session creation failed to route " + this.mPendingSessionCreationRequest.mRouteId);
            }
            this.mPendingSessionCreationRequest = null;
            this.mCallback.onRequestFailed(this, j, 0);
            return;
        }
        if (DEBUG) {
            android.util.Slog.w(TAG, "Session creation waiting state to route " + this.mPendingSessionCreationRequest.mRouteId);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mRequestLock"})
    private boolean isRequestedRouteConnectedBtRoute() {
        java.util.Iterator<android.media.MediaRoute2Info> it = this.mBluetoothRouteController.getAllBluetoothRoutes().iterator();
        while (it.hasNext()) {
            if (android.text.TextUtils.equals(it.next().getId(), this.mPendingSessionCreationRequest.mRouteId)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsSelectedRouteWithId(@android.annotation.Nullable android.media.RoutingSessionInfo routingSessionInfo, @android.annotation.NonNull java.lang.String str) {
        if (routingSessionInfo == null) {
            return false;
        }
        java.util.List<java.lang.String> selectedRoutes = routingSessionInfo.getSelectedRoutes();
        if (selectedRoutes.size() != 1) {
            throw new java.lang.IllegalStateException("Selected routes list should contain only 1 route id.");
        }
        java.lang.String originalId = android.media.MediaRouter2Utils.getOriginalId(selectedRoutes.get(0));
        return originalId != null && originalId.equals(str);
    }

    void publishProviderState() {
        updateProviderState();
        notifyProviderState();
    }

    void notifySessionInfoUpdated() {
        android.media.RoutingSessionInfo routingSessionInfo;
        if (this.mCallback == null) {
            return;
        }
        synchronized (this.mLock) {
            routingSessionInfo = this.mSessionInfos.get(0);
        }
        this.mCallback.onSessionUpdated(this, routingSessionInfo);
    }

    @Override // com.android.server.media.MediaRoute2Provider
    protected java.lang.String getDebugString() {
        return android.text.TextUtils.formatSimple("SystemMR2Provider - package: %s, selected route id: %s, bluetooth impl: %s", new java.lang.Object[]{this.mComponentName.getPackageName(), this.mSelectedRouteId, this.mBluetoothRouteController.getClass().getSimpleName()});
    }

    private static class SessionCreationRequest {
        private final long mRequestId;

        @android.annotation.NonNull
        private final java.lang.String mRouteId;

        @android.annotation.NonNull
        private final java.lang.String mTransferInitiatorPackageName;

        @android.annotation.NonNull
        private final android.os.UserHandle mTransferInitiatorUserHandle;
        private final int mTransferReason;

        SessionCreationRequest(long j, @android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull android.os.UserHandle userHandle, @android.annotation.NonNull java.lang.String str2) {
            this.mRequestId = j;
            this.mRouteId = str;
            this.mTransferReason = i;
            this.mTransferInitiatorUserHandle = userHandle;
            this.mTransferInitiatorPackageName = str2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isTargetRoute(@android.annotation.Nullable android.media.MediaRoute2Info mediaRoute2Info) {
            if (mediaRoute2Info == null) {
                return false;
            }
            return isTargetRoute(mediaRoute2Info.getId());
        }

        private boolean isTargetRoute(@android.annotation.Nullable java.lang.String str) {
            return this.mRouteId.equals(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isInsideOfRoutesList(@android.annotation.NonNull java.util.List<java.lang.String> list) {
            java.util.Iterator<java.lang.String> it = list.iterator();
            while (it.hasNext()) {
                if (isTargetRoute(it.next())) {
                    return true;
                }
            }
            return false;
        }
    }

    void updateVolume() {
        int devicesForStream = this.mAudioManager.getDevicesForStream(3);
        int streamVolume = this.mAudioManager.getStreamVolume(3);
        if (this.mDefaultRoute.getVolume() != streamVolume) {
            this.mDefaultRoute = new android.media.MediaRoute2Info.Builder(this.mDefaultRoute).setVolume(streamVolume).build();
        }
        if (this.mBluetoothRouteController.updateVolumeForDevices(devicesForStream, streamVolume)) {
            return;
        }
        this.mDeviceRouteController.updateVolume(streamVolume);
        publishProviderState();
    }

    private class AudioManagerBroadcastReceiver extends android.content.BroadcastReceiver {
        private AudioManagerBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ((!intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION") && !intent.getAction().equals("android.media.STREAM_DEVICES_CHANGED_ACTION")) || intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) != 3) {
                return;
            }
            com.android.server.media.SystemMediaRoute2Provider.this.updateVolume();
        }
    }
}
