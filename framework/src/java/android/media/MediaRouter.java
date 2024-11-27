package android.media;

/* loaded from: classes2.dex */
public class MediaRouter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int AVAILABILITY_FLAG_IGNORE_DEFAULT_ROUTE = 1;
    public static final int CALLBACK_FLAG_PASSIVE_DISCOVERY = 8;
    public static final int CALLBACK_FLAG_PERFORM_ACTIVE_SCAN = 1;
    public static final int CALLBACK_FLAG_REQUEST_DISCOVERY = 4;
    public static final int CALLBACK_FLAG_UNFILTERED_EVENTS = 2;
    private static final boolean DEBUG_RESTORE_ROUTE = true;
    public static final java.lang.String MIRRORING_GROUP_ID = "android.media.mirroring_group";
    static final int ROUTE_TYPE_ANY = 8388615;
    public static final int ROUTE_TYPE_LIVE_AUDIO = 1;
    public static final int ROUTE_TYPE_LIVE_VIDEO = 2;
    public static final int ROUTE_TYPE_REMOTE_DISPLAY = 4;
    public static final int ROUTE_TYPE_USER = 8388608;
    static android.media.MediaRouter.Static sStatic;
    private static final java.lang.String TAG = "MediaRouter";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    static final java.util.HashMap<android.content.Context, android.media.MediaRouter> sRouters = new java.util.HashMap<>();

    public static abstract class VolumeCallback {
        public abstract void onVolumeSetRequest(android.media.MediaRouter.RouteInfo routeInfo, int i);

        public abstract void onVolumeUpdateRequest(android.media.MediaRouter.RouteInfo routeInfo, int i);
    }

    static class Static implements android.hardware.display.DisplayManager.DisplayListener {
        boolean mActivelyScanningWifiDisplays;
        android.media.MediaRouter.RouteInfo mBluetoothA2dpRoute;
        final boolean mCanConfigureWifiDisplays;
        android.media.IMediaRouterClient mClient;
        android.media.MediaRouterClientState mClientState;
        android.media.MediaRouter.RouteInfo mDefaultAudioVideo;
        boolean mDiscoverRequestActiveScan;
        int mDiscoveryRequestRouteTypes;
        final android.hardware.display.DisplayManager mDisplayService;
        final android.os.Handler mHandler;
        boolean mIsBluetoothA2dpOn;
        final java.lang.String mPackageName;
        java.lang.String mPreviousActiveWifiDisplayAddress;
        final android.content.res.Resources mResources;
        android.media.MediaRouter.RouteInfo mSelectedRoute;
        final java.util.concurrent.CopyOnWriteArrayList<android.media.MediaRouter.CallbackInfo> mCallbacks = new java.util.concurrent.CopyOnWriteArrayList<>();
        final java.util.ArrayList<android.media.MediaRouter.RouteInfo> mRoutes = new java.util.ArrayList<>();
        final java.util.ArrayList<android.media.MediaRouter.RouteCategory> mCategories = new java.util.ArrayList<>();
        final android.media.AudioRoutesInfo mCurAudioRoutesInfo = new android.media.AudioRoutesInfo();
        int mCurrentUserId = -1;
        android.util.SparseIntArray mStreamVolume = new android.util.SparseIntArray();
        final android.media.IAudioRoutesObserver.Stub mAudioRoutesObserver = new android.media.IAudioRoutesObserver.Stub() { // from class: android.media.MediaRouter.Static.1
            @Override // android.media.IAudioRoutesObserver
            public void dispatchAudioRoutesChanged(final android.media.AudioRoutesInfo audioRoutesInfo) {
                try {
                    android.media.MediaRouter.Static.this.mIsBluetoothA2dpOn = android.media.MediaRouter.Static.this.mAudioService.isBluetoothA2dpOn();
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.media.MediaRouter.TAG, "Error querying Bluetooth A2DP state", e);
                }
                android.media.MediaRouter.Static.this.mHandler.post(new java.lang.Runnable() { // from class: android.media.MediaRouter.Static.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        android.media.MediaRouter.Static.this.updateAudioRoutes(audioRoutesInfo);
                    }
                });
            }
        };
        final android.media.IAudioService mAudioService = android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio"));
        final android.media.IMediaRouterService mMediaRouterService = android.media.IMediaRouterService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.MEDIA_ROUTER_SERVICE));
        final android.media.MediaRouter.RouteCategory mSystemCategory = new android.media.MediaRouter.RouteCategory(com.android.internal.R.string.default_audio_route_category_name, 3, false);

        Static(android.content.Context context) {
            this.mPackageName = context.getPackageName();
            this.mResources = context.getResources();
            this.mHandler = new android.os.Handler(context.getMainLooper());
            this.mDisplayService = (android.hardware.display.DisplayManager) context.getSystemService(android.content.Context.DISPLAY_SERVICE);
            this.mSystemCategory.mIsSystem = true;
            this.mCanConfigureWifiDisplays = context.checkPermission(android.Manifest.permission.CONFIGURE_WIFI_DISPLAY, android.os.Process.myPid(), android.os.Process.myUid()) == 0;
        }

        void startMonitoringRoutes(android.content.Context context) {
            android.media.AudioRoutesInfo audioRoutesInfo;
            this.mDefaultAudioVideo = new android.media.MediaRouter.RouteInfo(this.mSystemCategory);
            this.mDefaultAudioVideo.mNameResId = com.android.internal.R.string.default_audio_route_name;
            this.mDefaultAudioVideo.mSupportedTypes = 3;
            this.mDefaultAudioVideo.updatePresentationDisplay();
            if (((android.media.AudioManager) context.getSystemService("audio")).isVolumeFixed()) {
                this.mDefaultAudioVideo.mVolumeHandling = 0;
            }
            this.mDefaultAudioVideo.mGlobalRouteId = android.media.MediaRouter.sStatic.mResources.getString(com.android.internal.R.string.default_audio_route_id);
            android.media.MediaRouter.addRouteStatic(this.mDefaultAudioVideo);
            android.media.MediaRouter.updateWifiDisplayStatus(this.mDisplayService.getWifiDisplayStatus());
            context.registerReceiver(new android.media.MediaRouter.WifiDisplayStatusChangedReceiver(), new android.content.IntentFilter(android.hardware.display.DisplayManager.ACTION_WIFI_DISPLAY_STATUS_CHANGED));
            context.registerReceiver(new android.media.MediaRouter.VolumeChangeReceiver(), new android.content.IntentFilter("android.media.VOLUME_CHANGED_ACTION"));
            this.mDisplayService.registerDisplayListener(this, this.mHandler);
            try {
                this.mIsBluetoothA2dpOn = this.mAudioService.isBluetoothA2dpOn();
                audioRoutesInfo = this.mAudioService.startWatchingRoutes(this.mAudioRoutesObserver);
            } catch (android.os.RemoteException e) {
                audioRoutesInfo = null;
            }
            if (audioRoutesInfo != null) {
                updateAudioRoutes(audioRoutesInfo);
            }
            rebindAsUser(android.os.UserHandle.myUserId());
            if (this.mSelectedRoute == null) {
                android.media.MediaRouter.selectDefaultRouteStatic();
            }
        }

        void updateAudioRoutes(android.media.AudioRoutesInfo audioRoutesInfo) {
            boolean z;
            boolean z2;
            int i;
            if (audioRoutesInfo.mainType == this.mCurAudioRoutesInfo.mainType) {
                z = false;
                z2 = false;
            } else {
                this.mCurAudioRoutesInfo.mainType = audioRoutesInfo.mainType;
                if ((audioRoutesInfo.mainType & 2) != 0 || (audioRoutesInfo.mainType & 1) != 0) {
                    i = com.android.internal.R.string.default_audio_route_name_headphones;
                } else if ((audioRoutesInfo.mainType & 4) != 0) {
                    i = com.android.internal.R.string.default_audio_route_name_dock_speakers;
                } else if ((audioRoutesInfo.mainType & 8) != 0) {
                    i = com.android.internal.R.string.default_audio_route_name_external_device;
                } else if ((audioRoutesInfo.mainType & 16) != 0) {
                    i = com.android.internal.R.string.default_audio_route_name_usb;
                } else {
                    i = com.android.internal.R.string.default_audio_route_name;
                }
                this.mDefaultAudioVideo.mNameResId = i;
                android.media.MediaRouter.dispatchRouteChanged(this.mDefaultAudioVideo);
                if ((audioRoutesInfo.mainType & 19) == 0) {
                    z = false;
                } else {
                    z = true;
                }
                z2 = true;
            }
            if (!android.text.TextUtils.equals(audioRoutesInfo.bluetoothName, this.mCurAudioRoutesInfo.bluetoothName)) {
                if (audioRoutesInfo.bluetoothName != null) {
                    if (this.mBluetoothA2dpRoute == null) {
                        android.media.MediaRouter.RouteInfo routeInfo = new android.media.MediaRouter.RouteInfo(this.mSystemCategory);
                        routeInfo.mName = audioRoutesInfo.bluetoothName;
                        routeInfo.mDescription = this.mResources.getText(com.android.internal.R.string.bluetooth_a2dp_audio_route_name);
                        routeInfo.mSupportedTypes = 1;
                        routeInfo.mDeviceType = 3;
                        routeInfo.mGlobalRouteId = android.media.MediaRouter.sStatic.mResources.getString(com.android.internal.R.string.bluetooth_a2dp_audio_route_id);
                        this.mBluetoothA2dpRoute = routeInfo;
                        android.media.MediaRouter.addRouteStatic(this.mBluetoothA2dpRoute);
                    } else {
                        this.mBluetoothA2dpRoute.mName = audioRoutesInfo.bluetoothName;
                        android.media.MediaRouter.dispatchRouteChanged(this.mBluetoothA2dpRoute);
                    }
                } else if (this.mBluetoothA2dpRoute != null) {
                    android.media.MediaRouter.RouteInfo routeInfo2 = this.mBluetoothA2dpRoute;
                    this.mBluetoothA2dpRoute = null;
                    android.media.MediaRouter.removeRouteStatic(routeInfo2);
                }
                z2 = true;
                z = false;
            }
            if (z2) {
                android.util.Log.v(android.media.MediaRouter.TAG, "Audio routes updated: " + audioRoutesInfo + ", a2dp=" + isBluetoothA2dpOn());
                if (this.mSelectedRoute == null || this.mSelectedRoute.isDefault() || this.mSelectedRoute.isBluetooth()) {
                    if (z || this.mBluetoothA2dpRoute == null) {
                        android.media.MediaRouter.selectRouteStatic(1, this.mDefaultAudioVideo, false);
                    } else {
                        android.media.MediaRouter.selectRouteStatic(1, this.mBluetoothA2dpRoute, false);
                    }
                }
            }
            this.mCurAudioRoutesInfo.bluetoothName = audioRoutesInfo.bluetoothName;
        }

        int getStreamVolume(int i) {
            int indexOfKey = this.mStreamVolume.indexOfKey(i);
            if (indexOfKey < 0) {
                int i2 = 0;
                try {
                    try {
                        i2 = this.mAudioService.getStreamVolume(i);
                        this.mStreamVolume.put(i, i2);
                        return i2;
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.media.MediaRouter.TAG, "Error getting local stream volume", e);
                        return i2;
                    }
                } catch (java.lang.Throwable th) {
                    return i2;
                }
            }
            return this.mStreamVolume.valueAt(indexOfKey);
        }

        boolean isBluetoothA2dpOn() {
            return this.mBluetoothA2dpRoute != null && this.mIsBluetoothA2dpOn;
        }

        void updateDiscoveryRequest() {
            int size = this.mCallbacks.size();
            int i = 0;
            boolean z = false;
            int i2 = 0;
            boolean z2 = false;
            for (int i3 = 0; i3 < size; i3++) {
                android.media.MediaRouter.CallbackInfo callbackInfo = this.mCallbacks.get(i3);
                if ((callbackInfo.flags & 5) != 0) {
                    i |= callbackInfo.type;
                } else if ((callbackInfo.flags & 8) != 0) {
                    i2 |= callbackInfo.type;
                } else {
                    i |= callbackInfo.type;
                }
                if ((callbackInfo.flags & 1) != 0) {
                    if ((callbackInfo.type & 4) == 0) {
                        z = true;
                    } else {
                        z = true;
                        z2 = true;
                    }
                }
            }
            if (i != 0 || z) {
                i |= i2;
            }
            if (this.mCanConfigureWifiDisplays) {
                if (this.mSelectedRoute != null && this.mSelectedRoute.matchesTypes(4)) {
                    z2 = false;
                }
                if (z2) {
                    if (!this.mActivelyScanningWifiDisplays) {
                        this.mActivelyScanningWifiDisplays = true;
                        this.mDisplayService.startWifiDisplayScan();
                    }
                } else if (this.mActivelyScanningWifiDisplays) {
                    this.mActivelyScanningWifiDisplays = false;
                    this.mDisplayService.stopWifiDisplayScan();
                }
            }
            if (i != this.mDiscoveryRequestRouteTypes || z != this.mDiscoverRequestActiveScan) {
                this.mDiscoveryRequestRouteTypes = i;
                this.mDiscoverRequestActiveScan = z;
                publishClientDiscoveryRequest();
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
            updatePresentationDisplays(i);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            updatePresentationDisplays(i);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            updatePresentationDisplays(i);
        }

        public void setRouterGroupId(java.lang.String str) {
            if (this.mClient != null) {
                try {
                    this.mMediaRouterService.registerClientGroupId(this.mClient, str);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        public android.view.Display[] getAllPresentationDisplays() {
            try {
                return this.mDisplayService.getDisplays(android.hardware.display.DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
            } catch (java.lang.RuntimeException e) {
                android.util.Log.e(android.media.MediaRouter.TAG, "Unable to get displays.", e);
                return null;
            }
        }

        private void updatePresentationDisplays(int i) {
            int size = this.mRoutes.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.media.MediaRouter.RouteInfo routeInfo = this.mRoutes.get(i2);
                if (routeInfo.updatePresentationDisplay() || (routeInfo.mPresentationDisplay != null && routeInfo.mPresentationDisplay.getDisplayId() == i)) {
                    android.media.MediaRouter.dispatchRoutePresentationDisplayChanged(routeInfo);
                }
            }
        }

        void handleGroupRouteSelected(java.lang.String str) {
            android.media.MediaRouter.RouteInfo routeInfo = isBluetoothA2dpOn() ? this.mBluetoothA2dpRoute : this.mDefaultAudioVideo;
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                android.media.MediaRouter.RouteInfo routeInfo2 = this.mRoutes.get(i);
                if (android.text.TextUtils.equals(routeInfo2.mGlobalRouteId, str)) {
                    routeInfo = routeInfo2;
                }
            }
            if (routeInfo != this.mSelectedRoute) {
                android.media.MediaRouter.selectRouteStatic(routeInfo.mSupportedTypes, routeInfo, false);
            }
        }

        void setSelectedRoute(android.media.MediaRouter.RouteInfo routeInfo, boolean z) {
            this.mSelectedRoute = routeInfo;
            publishClientSelectedRoute(z);
        }

        void rebindAsUser(int i) {
            if (this.mCurrentUserId != i || i < 0 || this.mClient == null) {
                if (this.mClient != null) {
                    try {
                        this.mMediaRouterService.unregisterClient(this.mClient);
                    } catch (android.os.RemoteException e) {
                        e.rethrowFromSystemServer();
                    }
                    this.mClient = null;
                }
                this.mCurrentUserId = i;
                try {
                    android.media.MediaRouter.Static.Client client = new android.media.MediaRouter.Static.Client();
                    this.mMediaRouterService.registerClientAsUser(client, this.mPackageName, i);
                    this.mClient = client;
                } catch (android.os.RemoteException e2) {
                    android.util.Log.e(android.media.MediaRouter.TAG, "Unable to register media router client.", e2);
                }
                publishClientDiscoveryRequest();
                publishClientSelectedRoute(false);
                updateClientState();
            }
        }

        void publishClientDiscoveryRequest() {
            if (this.mClient != null) {
                try {
                    this.mMediaRouterService.setDiscoveryRequest(this.mClient, this.mDiscoveryRequestRouteTypes, this.mDiscoverRequestActiveScan);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        void publishClientSelectedRoute(boolean z) {
            if (this.mClient != null) {
                try {
                    this.mMediaRouterService.setSelectedRoute(this.mClient, this.mSelectedRoute != null ? this.mSelectedRoute.mGlobalRouteId : null, z);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        void updateClientState() {
            this.mClientState = null;
            if (this.mClient != null) {
                try {
                    this.mClientState = this.mMediaRouterService.getState(this.mClient);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
            java.util.ArrayList<android.media.MediaRouterClientState.RouteInfo> arrayList = this.mClientState != null ? this.mClientState.routes : null;
            int size = arrayList != null ? arrayList.size() : 0;
            for (int i = 0; i < size; i++) {
                android.media.MediaRouterClientState.RouteInfo routeInfo = arrayList.get(i);
                android.media.MediaRouter.RouteInfo findGlobalRoute = findGlobalRoute(routeInfo.id);
                if (findGlobalRoute == null) {
                    android.media.MediaRouter.addRouteStatic(makeGlobalRoute(routeInfo));
                } else {
                    updateGlobalRoute(findGlobalRoute, routeInfo);
                }
            }
            int size2 = this.mRoutes.size();
            while (true) {
                int i2 = size2 - 1;
                if (size2 > 0) {
                    android.media.MediaRouter.RouteInfo routeInfo2 = this.mRoutes.get(i2);
                    java.lang.String str = routeInfo2.mGlobalRouteId;
                    if (!routeInfo2.isDefault() && !routeInfo2.isBluetooth() && str != null) {
                        int i3 = 0;
                        while (true) {
                            if (i3 < size) {
                                if (str.equals(arrayList.get(i3).id)) {
                                    break;
                                } else {
                                    i3++;
                                }
                            } else {
                                android.media.MediaRouter.removeRouteStatic(routeInfo2);
                                break;
                            }
                        }
                    }
                    size2 = i2;
                } else {
                    return;
                }
            }
        }

        void requestSetVolume(android.media.MediaRouter.RouteInfo routeInfo, int i) {
            if (routeInfo.mGlobalRouteId != null && this.mClient != null) {
                try {
                    this.mMediaRouterService.requestSetVolume(this.mClient, routeInfo.mGlobalRouteId, i);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        void requestUpdateVolume(android.media.MediaRouter.RouteInfo routeInfo, int i) {
            if (routeInfo.mGlobalRouteId != null && this.mClient != null) {
                try {
                    this.mMediaRouterService.requestUpdateVolume(this.mClient, routeInfo.mGlobalRouteId, i);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        android.media.MediaRouter.RouteInfo makeGlobalRoute(android.media.MediaRouterClientState.RouteInfo routeInfo) {
            android.media.MediaRouter.RouteInfo routeInfo2 = new android.media.MediaRouter.RouteInfo(this.mSystemCategory);
            routeInfo2.mGlobalRouteId = routeInfo.id;
            routeInfo2.mName = routeInfo.name;
            routeInfo2.mDescription = routeInfo.description;
            routeInfo2.mSupportedTypes = routeInfo.supportedTypes;
            routeInfo2.mDeviceType = routeInfo.deviceType;
            routeInfo2.mEnabled = routeInfo.enabled;
            routeInfo2.setRealStatusCode(routeInfo.statusCode);
            routeInfo2.mPlaybackType = routeInfo.playbackType;
            routeInfo2.mPlaybackStream = routeInfo.playbackStream;
            routeInfo2.mVolume = routeInfo.volume;
            routeInfo2.mVolumeMax = routeInfo.volumeMax;
            routeInfo2.mVolumeHandling = routeInfo.volumeHandling;
            routeInfo2.mPresentationDisplayId = routeInfo.presentationDisplayId;
            routeInfo2.updatePresentationDisplay();
            return routeInfo2;
        }

        void updateGlobalRoute(android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouterClientState.RouteInfo routeInfo2) {
            boolean z;
            boolean z2;
            boolean z3 = true;
            boolean z4 = false;
            if (java.util.Objects.equals(routeInfo.mName, routeInfo2.name)) {
                z = false;
            } else {
                routeInfo.mName = routeInfo2.name;
                z = true;
            }
            if (!java.util.Objects.equals(routeInfo.mDescription, routeInfo2.description)) {
                routeInfo.mDescription = routeInfo2.description;
                z = true;
            }
            int i = routeInfo.mSupportedTypes;
            if (i != routeInfo2.supportedTypes) {
                routeInfo.mSupportedTypes = routeInfo2.supportedTypes;
                z = true;
            }
            if (routeInfo.mEnabled != routeInfo2.enabled) {
                routeInfo.mEnabled = routeInfo2.enabled;
                z = true;
            }
            if (routeInfo.mRealStatusCode != routeInfo2.statusCode) {
                routeInfo.setRealStatusCode(routeInfo2.statusCode);
                z = true;
            }
            if (routeInfo.mPlaybackType != routeInfo2.playbackType) {
                routeInfo.mPlaybackType = routeInfo2.playbackType;
                z = true;
            }
            if (routeInfo.mPlaybackStream != routeInfo2.playbackStream) {
                routeInfo.mPlaybackStream = routeInfo2.playbackStream;
                z = true;
            }
            if (routeInfo.mVolume == routeInfo2.volume) {
                z2 = false;
            } else {
                routeInfo.mVolume = routeInfo2.volume;
                z = true;
                z2 = true;
            }
            if (routeInfo.mVolumeMax != routeInfo2.volumeMax) {
                routeInfo.mVolumeMax = routeInfo2.volumeMax;
                z = true;
                z2 = true;
            }
            if (routeInfo.mVolumeHandling != routeInfo2.volumeHandling) {
                routeInfo.mVolumeHandling = routeInfo2.volumeHandling;
                z = true;
                z2 = true;
            }
            if (routeInfo.mPresentationDisplayId == routeInfo2.presentationDisplayId) {
                z3 = z;
            } else {
                routeInfo.mPresentationDisplayId = routeInfo2.presentationDisplayId;
                routeInfo.updatePresentationDisplay();
                z4 = true;
            }
            if (z3) {
                android.media.MediaRouter.dispatchRouteChanged(routeInfo, i);
            }
            if (z2) {
                android.media.MediaRouter.dispatchRouteVolumeChanged(routeInfo);
            }
            if (z4) {
                android.media.MediaRouter.dispatchRoutePresentationDisplayChanged(routeInfo);
            }
        }

        android.media.MediaRouter.RouteInfo findGlobalRoute(java.lang.String str) {
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                android.media.MediaRouter.RouteInfo routeInfo = this.mRoutes.get(i);
                if (str.equals(routeInfo.mGlobalRouteId)) {
                    return routeInfo;
                }
            }
            return null;
        }

        boolean isPlaybackActive() {
            if (this.mClient != null) {
                try {
                    return this.mMediaRouterService.isPlaybackActive(this.mClient);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                    return false;
                }
            }
            return false;
        }

        final class Client extends android.media.IMediaRouterClient.Stub {
            Client() {
            }

            @Override // android.media.IMediaRouterClient
            public void onStateChanged() {
                android.media.MediaRouter.Static.this.mHandler.post(new java.lang.Runnable() { // from class: android.media.MediaRouter.Static.Client.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (android.media.MediaRouter.Static.Client.this == android.media.MediaRouter.Static.this.mClient) {
                            android.media.MediaRouter.Static.this.updateClientState();
                        }
                    }
                });
            }

            @Override // android.media.IMediaRouterClient
            public void onRestoreRoute() {
                android.media.MediaRouter.Static.this.mHandler.post(new java.lang.Runnable() { // from class: android.media.MediaRouter$Static$Client$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.MediaRouter.Static.Client.this.lambda$onRestoreRoute$0();
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onRestoreRoute$0() {
                if (this == android.media.MediaRouter.Static.this.mClient && android.media.MediaRouter.Static.this.mSelectedRoute != null) {
                    if (!android.media.MediaRouter.Static.this.mSelectedRoute.isDefault() && !android.media.MediaRouter.Static.this.mSelectedRoute.isBluetooth()) {
                        return;
                    }
                    if (android.media.MediaRouter.Static.this.mSelectedRoute.isDefault() && android.media.MediaRouter.Static.this.mBluetoothA2dpRoute != null) {
                        android.util.Log.d(android.media.MediaRouter.TAG, "onRestoreRoute() : selectedRoute=" + android.media.MediaRouter.Static.this.mSelectedRoute + ", a2dpRoute=" + android.media.MediaRouter.Static.this.mBluetoothA2dpRoute);
                    } else {
                        android.util.Log.d(android.media.MediaRouter.TAG, "onRestoreRoute() : route=" + android.media.MediaRouter.Static.this.mSelectedRoute);
                    }
                    android.media.MediaRouter.Static.this.mSelectedRoute.select();
                }
            }

            @Override // android.media.IMediaRouterClient
            public void onGroupRouteSelected(final java.lang.String str) {
                android.media.MediaRouter.Static.this.mHandler.post(new java.lang.Runnable() { // from class: android.media.MediaRouter$Static$Client$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.MediaRouter.Static.Client.this.lambda$onGroupRouteSelected$1(str);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onGroupRouteSelected$1(java.lang.String str) {
                if (this == android.media.MediaRouter.Static.this.mClient) {
                    android.media.MediaRouter.Static.this.handleGroupRouteSelected(str);
                }
            }
        }
    }

    static java.lang.String typesToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if ((i & 1) != 0) {
            sb.append("ROUTE_TYPE_LIVE_AUDIO ");
        }
        if ((i & 2) != 0) {
            sb.append("ROUTE_TYPE_LIVE_VIDEO ");
        }
        if ((i & 4) != 0) {
            sb.append("ROUTE_TYPE_REMOTE_DISPLAY ");
        }
        if ((i & 8388608) != 0) {
            sb.append("ROUTE_TYPE_USER ");
        }
        return sb.toString();
    }

    public MediaRouter(android.content.Context context) {
        synchronized (android.media.MediaRouter.Static.class) {
            if (sStatic == null) {
                android.content.Context applicationContext = context.getApplicationContext();
                sStatic = new android.media.MediaRouter.Static(applicationContext);
                sStatic.startMonitoringRoutes(applicationContext);
            }
        }
    }

    public android.media.MediaRouter.RouteInfo getDefaultRoute() {
        return sStatic.mDefaultAudioVideo;
    }

    public android.media.MediaRouter.RouteInfo getFallbackRoute() {
        return sStatic.mBluetoothA2dpRoute != null ? sStatic.mBluetoothA2dpRoute : sStatic.mDefaultAudioVideo;
    }

    public android.media.MediaRouter.RouteCategory getSystemCategory() {
        return sStatic.mSystemCategory;
    }

    public android.media.MediaRouter.RouteInfo getSelectedRoute() {
        return getSelectedRoute(8388615);
    }

    public android.media.MediaRouter.RouteInfo getSelectedRoute(int i) {
        if (sStatic.mSelectedRoute != null && (sStatic.mSelectedRoute.mSupportedTypes & i) != 0) {
            return sStatic.mSelectedRoute;
        }
        if (i == 8388608) {
            return null;
        }
        return sStatic.mDefaultAudioVideo;
    }

    public boolean isRouteAvailable(int i, int i2) {
        int size = sStatic.mRoutes.size();
        for (int i3 = 0; i3 < size; i3++) {
            android.media.MediaRouter.RouteInfo routeInfo = sStatic.mRoutes.get(i3);
            if (routeInfo.matchesTypes(i) && ((i2 & 1) == 0 || routeInfo != sStatic.mDefaultAudioVideo)) {
                return true;
            }
        }
        return false;
    }

    public void setRouterGroupId(java.lang.String str) {
        sStatic.setRouterGroupId(str);
    }

    public void addCallback(int i, android.media.MediaRouter.Callback callback) {
        addCallback(i, callback, 0);
    }

    public void addCallback(int i, android.media.MediaRouter.Callback callback, int i2) {
        int findCallbackInfo = findCallbackInfo(callback);
        if (findCallbackInfo >= 0) {
            android.media.MediaRouter.CallbackInfo callbackInfo = sStatic.mCallbacks.get(findCallbackInfo);
            callbackInfo.type = i | callbackInfo.type;
            callbackInfo.flags |= i2;
        } else {
            sStatic.mCallbacks.add(new android.media.MediaRouter.CallbackInfo(callback, i, i2, this));
        }
        sStatic.updateDiscoveryRequest();
    }

    public void removeCallback(android.media.MediaRouter.Callback callback) {
        int findCallbackInfo = findCallbackInfo(callback);
        if (findCallbackInfo >= 0) {
            sStatic.mCallbacks.remove(findCallbackInfo);
            sStatic.updateDiscoveryRequest();
        } else {
            android.util.Log.w(TAG, "removeCallback(" + callback + "): callback not registered");
        }
    }

    private int findCallbackInfo(android.media.MediaRouter.Callback callback) {
        int size = sStatic.mCallbacks.size();
        for (int i = 0; i < size; i++) {
            if (sStatic.mCallbacks.get(i).cb == callback) {
                return i;
            }
        }
        return -1;
    }

    public void selectRoute(int i, android.media.MediaRouter.RouteInfo routeInfo) {
        if (routeInfo == null) {
            throw new java.lang.IllegalArgumentException("Route cannot be null.");
        }
        selectRouteStatic(i, routeInfo, true);
    }

    public void selectRouteInt(int i, android.media.MediaRouter.RouteInfo routeInfo, boolean z) {
        selectRouteStatic(i, routeInfo, z);
    }

    static void selectRouteStatic(int i, android.media.MediaRouter.RouteInfo routeInfo, boolean z) {
        android.util.Log.v(TAG, "Selecting route: " + routeInfo);
        android.media.MediaRouter.RouteInfo routeInfo2 = sStatic.mSelectedRoute;
        android.media.MediaRouter.RouteInfo routeInfo3 = sStatic.isBluetoothA2dpOn() ? sStatic.mBluetoothA2dpRoute : sStatic.mDefaultAudioVideo;
        boolean z2 = routeInfo2 != null && (routeInfo2.isDefault() || routeInfo2.isBluetooth());
        if (routeInfo2 == routeInfo && (!z2 || routeInfo == routeInfo3)) {
            return;
        }
        if (!routeInfo.matchesTypes(i)) {
            android.util.Log.w(TAG, "selectRoute ignored; cannot select route with supported types " + typesToString(routeInfo.getSupportedTypes()) + " into route types " + typesToString(i));
            return;
        }
        if (sStatic.isPlaybackActive() && sStatic.mBluetoothA2dpRoute != null && (i & 1) != 0 && (routeInfo.isBluetooth() || routeInfo.isDefault())) {
            try {
                sStatic.mMediaRouterService.setBluetoothA2dpOn(sStatic.mClient, routeInfo.isBluetooth());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error changing Bluetooth A2DP state", e);
            }
        } else {
            android.util.Log.i(TAG, "Skip setBluetoothA2dpOn(): types=" + i + ", isPlaybackActive()=" + sStatic.isPlaybackActive() + ", BT route=" + sStatic.mBluetoothA2dpRoute);
        }
        android.hardware.display.WifiDisplay activeDisplay = sStatic.mDisplayService.getWifiDisplayStatus().getActiveDisplay();
        boolean z3 = (routeInfo2 == null || routeInfo2.mDeviceAddress == null) ? false : true;
        boolean z4 = routeInfo.mDeviceAddress != null;
        if (activeDisplay != null || z3 || z4) {
            if (z4 && !matchesDeviceAddress(activeDisplay, routeInfo)) {
                if (sStatic.mCanConfigureWifiDisplays) {
                    sStatic.mDisplayService.connectWifiDisplay(routeInfo.mDeviceAddress);
                } else {
                    android.util.Log.e(TAG, "Cannot connect to wifi displays because this process is not allowed to do so.");
                }
            } else if (activeDisplay != null && !z4) {
                sStatic.mDisplayService.disconnectWifiDisplay();
            }
        }
        sStatic.setSelectedRoute(routeInfo, z);
        if (routeInfo2 != null) {
            dispatchRouteUnselected(routeInfo2.getSupportedTypes() & i, routeInfo2);
            if (routeInfo2.resolveStatusCode()) {
                dispatchRouteChanged(routeInfo2);
            }
        }
        if (routeInfo != null) {
            if (routeInfo.resolveStatusCode()) {
                dispatchRouteChanged(routeInfo);
            }
            dispatchRouteSelected(i & routeInfo.getSupportedTypes(), routeInfo);
        }
        sStatic.updateDiscoveryRequest();
    }

    static void selectDefaultRouteStatic() {
        if (sStatic.isBluetoothA2dpOn() && sStatic.mSelectedRoute != null && !sStatic.mSelectedRoute.isBluetooth()) {
            selectRouteStatic(8388615, sStatic.mBluetoothA2dpRoute, false);
        } else {
            selectRouteStatic(8388615, sStatic.mDefaultAudioVideo, false);
        }
    }

    static boolean matchesDeviceAddress(android.hardware.display.WifiDisplay wifiDisplay, android.media.MediaRouter.RouteInfo routeInfo) {
        boolean z = (routeInfo == null || routeInfo.mDeviceAddress == null) ? false : true;
        if (wifiDisplay == null && !z) {
            return true;
        }
        if (wifiDisplay == null || !z) {
            return false;
        }
        return wifiDisplay.getDeviceAddress().equals(routeInfo.mDeviceAddress);
    }

    public void addUserRoute(android.media.MediaRouter.UserRouteInfo userRouteInfo) {
        addRouteStatic(userRouteInfo);
    }

    public void addRouteInt(android.media.MediaRouter.RouteInfo routeInfo) {
        addRouteStatic(routeInfo);
    }

    static void addRouteStatic(android.media.MediaRouter.RouteInfo routeInfo) {
        if (DEBUG) {
            android.util.Log.d(TAG, "Adding route: " + routeInfo);
        }
        android.media.MediaRouter.RouteCategory category = routeInfo.getCategory();
        if (!sStatic.mCategories.contains(category)) {
            sStatic.mCategories.add(category);
        }
        if (category.isGroupable() && !(routeInfo instanceof android.media.MediaRouter.RouteGroup)) {
            android.media.MediaRouter.RouteGroup routeGroup = new android.media.MediaRouter.RouteGroup(routeInfo.getCategory());
            routeGroup.mSupportedTypes = routeInfo.mSupportedTypes;
            sStatic.mRoutes.add(routeGroup);
            dispatchRouteAdded(routeGroup);
            routeGroup.addRoute(routeInfo);
            return;
        }
        sStatic.mRoutes.add(routeInfo);
        dispatchRouteAdded(routeInfo);
    }

    public void removeUserRoute(android.media.MediaRouter.UserRouteInfo userRouteInfo) {
        removeRouteStatic(userRouteInfo);
    }

    public void clearUserRoutes() {
        int i = 0;
        while (i < sStatic.mRoutes.size()) {
            android.media.MediaRouter.RouteInfo routeInfo = sStatic.mRoutes.get(i);
            if ((routeInfo instanceof android.media.MediaRouter.UserRouteInfo) || (routeInfo instanceof android.media.MediaRouter.RouteGroup)) {
                removeRouteStatic(routeInfo);
                i--;
            }
            i++;
        }
    }

    public void removeRouteInt(android.media.MediaRouter.RouteInfo routeInfo) {
        removeRouteStatic(routeInfo);
    }

    static void removeRouteStatic(android.media.MediaRouter.RouteInfo routeInfo) {
        if (DEBUG) {
            android.util.Log.d(TAG, "Removing route: " + routeInfo);
        }
        if (sStatic.mRoutes.remove(routeInfo)) {
            android.media.MediaRouter.RouteCategory category = routeInfo.getCategory();
            int size = sStatic.mRoutes.size();
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (category != sStatic.mRoutes.get(i).getCategory()) {
                    i++;
                } else {
                    z = true;
                    break;
                }
            }
            if (routeInfo.isSelected()) {
                selectDefaultRouteStatic();
            }
            if (!z) {
                sStatic.mCategories.remove(category);
            }
            dispatchRouteRemoved(routeInfo);
        }
    }

    public int getCategoryCount() {
        return sStatic.mCategories.size();
    }

    public android.media.MediaRouter.RouteCategory getCategoryAt(int i) {
        return sStatic.mCategories.get(i);
    }

    public int getRouteCount() {
        return sStatic.mRoutes.size();
    }

    public android.media.MediaRouter.RouteInfo getRouteAt(int i) {
        return sStatic.mRoutes.get(i);
    }

    static int getRouteCountStatic() {
        return sStatic.mRoutes.size();
    }

    static android.media.MediaRouter.RouteInfo getRouteAtStatic(int i) {
        return sStatic.mRoutes.get(i);
    }

    public android.media.MediaRouter.UserRouteInfo createUserRoute(android.media.MediaRouter.RouteCategory routeCategory) {
        return new android.media.MediaRouter.UserRouteInfo(routeCategory);
    }

    public android.media.MediaRouter.RouteCategory createRouteCategory(java.lang.CharSequence charSequence, boolean z) {
        return new android.media.MediaRouter.RouteCategory(charSequence, 8388608, z);
    }

    public android.media.MediaRouter.RouteCategory createRouteCategory(int i, boolean z) {
        return new android.media.MediaRouter.RouteCategory(i, 8388608, z);
    }

    public void rebindAsUser(int i) {
        sStatic.rebindAsUser(i);
    }

    static void updateRoute(android.media.MediaRouter.RouteInfo routeInfo) {
        dispatchRouteChanged(routeInfo);
    }

    static void dispatchRouteSelected(int i, android.media.MediaRouter.RouteInfo routeInfo) {
        java.util.Iterator<android.media.MediaRouter.CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            android.media.MediaRouter.CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeInfo)) {
                next.cb.onRouteSelected(next.router, i, routeInfo);
            }
        }
    }

    static void dispatchRouteUnselected(int i, android.media.MediaRouter.RouteInfo routeInfo) {
        java.util.Iterator<android.media.MediaRouter.CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            android.media.MediaRouter.CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeInfo)) {
                next.cb.onRouteUnselected(next.router, i, routeInfo);
            }
        }
    }

    static void dispatchRouteChanged(android.media.MediaRouter.RouteInfo routeInfo) {
        dispatchRouteChanged(routeInfo, routeInfo.mSupportedTypes);
    }

    static void dispatchRouteChanged(android.media.MediaRouter.RouteInfo routeInfo, int i) {
        if (DEBUG) {
            android.util.Log.d(TAG, "Dispatching route change: " + routeInfo);
        }
        int i2 = routeInfo.mSupportedTypes;
        java.util.Iterator<android.media.MediaRouter.CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            android.media.MediaRouter.CallbackInfo next = it.next();
            boolean filterRouteEvent = next.filterRouteEvent(i);
            boolean filterRouteEvent2 = next.filterRouteEvent(i2);
            if (!filterRouteEvent && filterRouteEvent2) {
                next.cb.onRouteAdded(next.router, routeInfo);
                if (routeInfo.isSelected()) {
                    next.cb.onRouteSelected(next.router, i2, routeInfo);
                }
            }
            if (filterRouteEvent || filterRouteEvent2) {
                next.cb.onRouteChanged(next.router, routeInfo);
            }
            if (filterRouteEvent && !filterRouteEvent2) {
                if (routeInfo.isSelected()) {
                    next.cb.onRouteUnselected(next.router, i, routeInfo);
                }
                next.cb.onRouteRemoved(next.router, routeInfo);
            }
        }
    }

    static void dispatchRouteAdded(android.media.MediaRouter.RouteInfo routeInfo) {
        java.util.Iterator<android.media.MediaRouter.CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            android.media.MediaRouter.CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeInfo)) {
                next.cb.onRouteAdded(next.router, routeInfo);
            }
        }
    }

    static void dispatchRouteRemoved(android.media.MediaRouter.RouteInfo routeInfo) {
        java.util.Iterator<android.media.MediaRouter.CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            android.media.MediaRouter.CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeInfo)) {
                next.cb.onRouteRemoved(next.router, routeInfo);
            }
        }
    }

    static void dispatchRouteGrouped(android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup, int i) {
        java.util.Iterator<android.media.MediaRouter.CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            android.media.MediaRouter.CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeGroup)) {
                next.cb.onRouteGrouped(next.router, routeInfo, routeGroup, i);
            }
        }
    }

    static void dispatchRouteUngrouped(android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup) {
        java.util.Iterator<android.media.MediaRouter.CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            android.media.MediaRouter.CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeGroup)) {
                next.cb.onRouteUngrouped(next.router, routeInfo, routeGroup);
            }
        }
    }

    static void dispatchRouteVolumeChanged(android.media.MediaRouter.RouteInfo routeInfo) {
        java.util.Iterator<android.media.MediaRouter.CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            android.media.MediaRouter.CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeInfo)) {
                next.cb.onRouteVolumeChanged(next.router, routeInfo);
            }
        }
    }

    static void dispatchRoutePresentationDisplayChanged(android.media.MediaRouter.RouteInfo routeInfo) {
        java.util.Iterator<android.media.MediaRouter.CallbackInfo> it = sStatic.mCallbacks.iterator();
        while (it.hasNext()) {
            android.media.MediaRouter.CallbackInfo next = it.next();
            if (next.filterRouteEvent(routeInfo)) {
                next.cb.onRoutePresentationDisplayChanged(next.router, routeInfo);
            }
        }
    }

    static void systemVolumeChanged(int i) {
        android.media.MediaRouter.RouteInfo routeInfo = sStatic.mSelectedRoute;
        if (routeInfo == null) {
            return;
        }
        if (routeInfo.isBluetooth() || routeInfo.isDefault()) {
            dispatchRouteVolumeChanged(routeInfo);
        } else if (sStatic.mBluetoothA2dpRoute != null) {
            dispatchRouteVolumeChanged(sStatic.mIsBluetoothA2dpOn ? sStatic.mBluetoothA2dpRoute : sStatic.mDefaultAudioVideo);
        } else {
            dispatchRouteVolumeChanged(sStatic.mDefaultAudioVideo);
        }
    }

    static void updateWifiDisplayStatus(android.hardware.display.WifiDisplayStatus wifiDisplayStatus) {
        android.hardware.display.WifiDisplay[] wifiDisplayArr;
        android.hardware.display.WifiDisplay wifiDisplay;
        android.hardware.display.WifiDisplay findWifiDisplay;
        if (wifiDisplayStatus.getFeatureState() == 3) {
            wifiDisplayArr = wifiDisplayStatus.getDisplays();
            wifiDisplay = wifiDisplayStatus.getActiveDisplay();
            if (!sStatic.mCanConfigureWifiDisplays) {
                if (wifiDisplay != null) {
                    wifiDisplayArr = new android.hardware.display.WifiDisplay[]{wifiDisplay};
                } else {
                    wifiDisplayArr = android.hardware.display.WifiDisplay.EMPTY_ARRAY;
                }
            }
        } else {
            wifiDisplayArr = android.hardware.display.WifiDisplay.EMPTY_ARRAY;
            wifiDisplay = null;
        }
        java.lang.String deviceAddress = wifiDisplay != null ? wifiDisplay.getDeviceAddress() : null;
        for (android.hardware.display.WifiDisplay wifiDisplay2 : wifiDisplayArr) {
            if (shouldShowWifiDisplay(wifiDisplay2, wifiDisplay)) {
                android.media.MediaRouter.RouteInfo findWifiDisplayRoute = findWifiDisplayRoute(wifiDisplay2);
                if (findWifiDisplayRoute == null) {
                    findWifiDisplayRoute = makeWifiDisplayRoute(wifiDisplay2, wifiDisplayStatus);
                    addRouteStatic(findWifiDisplayRoute);
                } else {
                    java.lang.String deviceAddress2 = wifiDisplay2.getDeviceAddress();
                    updateWifiDisplayRoute(findWifiDisplayRoute, wifiDisplay2, wifiDisplayStatus, !deviceAddress2.equals(deviceAddress) && deviceAddress2.equals(sStatic.mPreviousActiveWifiDisplayAddress));
                }
                if (wifiDisplay2.equals(wifiDisplay)) {
                    selectRouteStatic(findWifiDisplayRoute.getSupportedTypes(), findWifiDisplayRoute, false);
                }
            }
        }
        int size = sStatic.mRoutes.size();
        while (true) {
            int i = size - 1;
            if (size > 0) {
                android.media.MediaRouter.RouteInfo routeInfo = sStatic.mRoutes.get(i);
                if (routeInfo.mDeviceAddress != null && ((findWifiDisplay = findWifiDisplay(wifiDisplayArr, routeInfo.mDeviceAddress)) == null || !shouldShowWifiDisplay(findWifiDisplay, wifiDisplay))) {
                    removeRouteStatic(routeInfo);
                }
                size = i;
            } else {
                sStatic.mPreviousActiveWifiDisplayAddress = deviceAddress;
                return;
            }
        }
    }

    private static boolean shouldShowWifiDisplay(android.hardware.display.WifiDisplay wifiDisplay, android.hardware.display.WifiDisplay wifiDisplay2) {
        return wifiDisplay.isRemembered() || wifiDisplay.equals(wifiDisplay2);
    }

    static int getWifiDisplayStatusCode(android.hardware.display.WifiDisplay wifiDisplay, android.hardware.display.WifiDisplayStatus wifiDisplayStatus) {
        int i = 1;
        if (wifiDisplayStatus.getScanState() != 1) {
            if (wifiDisplay.isAvailable()) {
                i = wifiDisplay.canConnect() ? 3 : 5;
            } else {
                i = 4;
            }
        }
        if (wifiDisplay.equals(wifiDisplayStatus.getActiveDisplay())) {
            switch (wifiDisplayStatus.getActiveDisplayState()) {
                case 0:
                    android.util.Log.e(TAG, "Active display is not connected!");
                    break;
            }
            return i;
        }
        return i;
    }

    static boolean isWifiDisplayEnabled(android.hardware.display.WifiDisplay wifiDisplay, android.hardware.display.WifiDisplayStatus wifiDisplayStatus) {
        return wifiDisplay.isAvailable() && (wifiDisplay.canConnect() || wifiDisplay.equals(wifiDisplayStatus.getActiveDisplay()));
    }

    static android.media.MediaRouter.RouteInfo makeWifiDisplayRoute(android.hardware.display.WifiDisplay wifiDisplay, android.hardware.display.WifiDisplayStatus wifiDisplayStatus) {
        android.media.MediaRouter.RouteInfo routeInfo = new android.media.MediaRouter.RouteInfo(sStatic.mSystemCategory);
        routeInfo.mDeviceAddress = wifiDisplay.getDeviceAddress();
        routeInfo.mSupportedTypes = 7;
        routeInfo.mVolumeHandling = 0;
        routeInfo.mPlaybackType = 1;
        routeInfo.setRealStatusCode(getWifiDisplayStatusCode(wifiDisplay, wifiDisplayStatus));
        routeInfo.mEnabled = isWifiDisplayEnabled(wifiDisplay, wifiDisplayStatus);
        routeInfo.mName = wifiDisplay.getFriendlyDisplayName();
        routeInfo.mDescription = sStatic.mResources.getText(com.android.internal.R.string.wireless_display_route_description);
        routeInfo.updatePresentationDisplay();
        routeInfo.mDeviceType = 1;
        return routeInfo;
    }

    private static void updateWifiDisplayRoute(android.media.MediaRouter.RouteInfo routeInfo, android.hardware.display.WifiDisplay wifiDisplay, android.hardware.display.WifiDisplayStatus wifiDisplayStatus, boolean z) {
        boolean z2;
        java.lang.String friendlyDisplayName = wifiDisplay.getFriendlyDisplayName();
        if (routeInfo.getName().equals(friendlyDisplayName)) {
            z2 = false;
        } else {
            routeInfo.mName = friendlyDisplayName;
            z2 = true;
        }
        boolean isWifiDisplayEnabled = isWifiDisplayEnabled(wifiDisplay, wifiDisplayStatus);
        boolean z3 = routeInfo.mEnabled != isWifiDisplayEnabled;
        routeInfo.mEnabled = isWifiDisplayEnabled;
        if (routeInfo.setRealStatusCode(getWifiDisplayStatusCode(wifiDisplay, wifiDisplayStatus)) | z2 | z3) {
            dispatchRouteChanged(routeInfo);
        }
        if ((!isWifiDisplayEnabled || z) && routeInfo.isSelected()) {
            selectDefaultRouteStatic();
        }
    }

    private static android.hardware.display.WifiDisplay findWifiDisplay(android.hardware.display.WifiDisplay[] wifiDisplayArr, java.lang.String str) {
        for (android.hardware.display.WifiDisplay wifiDisplay : wifiDisplayArr) {
            if (wifiDisplay.getDeviceAddress().equals(str)) {
                return wifiDisplay;
            }
        }
        return null;
    }

    private static android.media.MediaRouter.RouteInfo findWifiDisplayRoute(android.hardware.display.WifiDisplay wifiDisplay) {
        int size = sStatic.mRoutes.size();
        for (int i = 0; i < size; i++) {
            android.media.MediaRouter.RouteInfo routeInfo = sStatic.mRoutes.get(i);
            if (wifiDisplay.getDeviceAddress().equals(routeInfo.mDeviceAddress)) {
                return routeInfo;
            }
        }
        return null;
    }

    public static class RouteInfo {
        private static final int DEFAULT_PLAYBACK_MAX_VOLUME = 15;
        private static final int DEFAULT_PLAYBACK_VOLUME = 15;
        public static final int DEVICE_TYPE_BLUETOOTH = 3;
        public static final int DEVICE_TYPE_SPEAKER = 2;
        public static final int DEVICE_TYPE_TV = 1;
        public static final int DEVICE_TYPE_UNKNOWN = 0;
        public static final int PLAYBACK_TYPE_LOCAL = 0;
        public static final int PLAYBACK_TYPE_REMOTE = 1;
        public static final int PLAYBACK_VOLUME_FIXED = 0;
        public static final int PLAYBACK_VOLUME_VARIABLE = 1;
        public static final int STATUS_AVAILABLE = 3;
        public static final int STATUS_CONNECTED = 6;
        public static final int STATUS_CONNECTING = 2;
        public static final int STATUS_IN_USE = 5;
        public static final int STATUS_NONE = 0;
        public static final int STATUS_NOT_AVAILABLE = 4;
        public static final int STATUS_SCANNING = 1;
        final android.media.MediaRouter.RouteCategory mCategory;
        java.lang.CharSequence mDescription;
        java.lang.String mDeviceAddress;
        java.lang.String mGlobalRouteId;
        android.media.MediaRouter.RouteGroup mGroup;
        android.graphics.drawable.Drawable mIcon;
        java.lang.CharSequence mName;
        int mNameResId;
        android.view.Display mPresentationDisplay;
        private int mRealStatusCode;
        private int mResolvedStatusCode;
        private java.lang.CharSequence mStatus;
        int mSupportedTypes;
        private java.lang.Object mTag;
        android.media.MediaRouter.VolumeCallbackInfo mVcb;
        int mPlaybackType = 0;
        int mVolumeMax = 15;
        int mVolume = 15;
        int mVolumeHandling = 1;
        int mPlaybackStream = 3;
        int mPresentationDisplayId = -1;
        boolean mEnabled = true;
        final android.media.IRemoteVolumeObserver.Stub mRemoteVolObserver = new android.media.IRemoteVolumeObserver.Stub() { // from class: android.media.MediaRouter.RouteInfo.1
            @Override // android.media.IRemoteVolumeObserver
            public void dispatchRemoteVolumeUpdate(final int i, final int i2) {
                android.media.MediaRouter.sStatic.mHandler.post(new java.lang.Runnable() { // from class: android.media.MediaRouter.RouteInfo.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (android.media.MediaRouter.RouteInfo.this.mVcb != null) {
                            if (i != 0) {
                                android.media.MediaRouter.RouteInfo.this.mVcb.vcb.onVolumeUpdateRequest(android.media.MediaRouter.RouteInfo.this.mVcb.route, i);
                            } else {
                                android.media.MediaRouter.RouteInfo.this.mVcb.vcb.onVolumeSetRequest(android.media.MediaRouter.RouteInfo.this.mVcb.route, i2);
                            }
                        }
                    }
                });
            }
        };
        int mDeviceType = 0;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface DeviceType {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface PlaybackType {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        private @interface PlaybackVolume {
        }

        public RouteInfo(android.media.MediaRouter.RouteCategory routeCategory) {
            this.mCategory = routeCategory;
        }

        public java.lang.CharSequence getName() {
            return getName(android.media.MediaRouter.sStatic.mResources);
        }

        public java.lang.CharSequence getName(android.content.Context context) {
            return getName(context.getResources());
        }

        java.lang.CharSequence getName(android.content.res.Resources resources) {
            if (this.mNameResId != 0) {
                return resources.getText(this.mNameResId);
            }
            return this.mName;
        }

        public java.lang.CharSequence getDescription() {
            return this.mDescription;
        }

        public java.lang.CharSequence getStatus() {
            return this.mStatus;
        }

        boolean setRealStatusCode(int i) {
            if (this.mRealStatusCode != i) {
                this.mRealStatusCode = i;
                return resolveStatusCode();
            }
            return false;
        }

        boolean resolveStatusCode() {
            int i = this.mRealStatusCode;
            if (isSelected()) {
                switch (i) {
                    case 1:
                    case 3:
                        i = 2;
                        break;
                }
            }
            int i2 = 0;
            if (this.mResolvedStatusCode == i) {
                return false;
            }
            this.mResolvedStatusCode = i;
            switch (i) {
                case 1:
                    i2 = com.android.internal.R.string.media_route_status_scanning;
                    break;
                case 2:
                    i2 = com.android.internal.R.string.media_route_status_connecting;
                    break;
                case 3:
                    i2 = com.android.internal.R.string.media_route_status_available;
                    break;
                case 4:
                    i2 = com.android.internal.R.string.media_route_status_not_available;
                    break;
                case 5:
                    i2 = com.android.internal.R.string.media_route_status_in_use;
                    break;
            }
            this.mStatus = i2 != 0 ? android.media.MediaRouter.sStatic.mResources.getText(i2) : null;
            return true;
        }

        public int getStatusCode() {
            return this.mResolvedStatusCode;
        }

        public int getSupportedTypes() {
            return this.mSupportedTypes;
        }

        public int getDeviceType() {
            return this.mDeviceType;
        }

        public boolean matchesTypes(int i) {
            return (i & this.mSupportedTypes) != 0;
        }

        public android.media.MediaRouter.RouteGroup getGroup() {
            return this.mGroup;
        }

        public android.media.MediaRouter.RouteCategory getCategory() {
            return this.mCategory;
        }

        public android.graphics.drawable.Drawable getIconDrawable() {
            return this.mIcon;
        }

        public void setTag(java.lang.Object obj) {
            this.mTag = obj;
            routeUpdated();
        }

        public java.lang.Object getTag() {
            return this.mTag;
        }

        public int getPlaybackType() {
            return this.mPlaybackType;
        }

        public int getPlaybackStream() {
            return this.mPlaybackStream;
        }

        public int getVolume() {
            if (this.mPlaybackType == 0) {
                return android.media.MediaRouter.sStatic.getStreamVolume(this.mPlaybackStream);
            }
            return this.mVolume;
        }

        public void requestSetVolume(int i) {
            if (this.mPlaybackType == 0) {
                try {
                    android.media.MediaRouter.sStatic.mAudioService.setStreamVolumeWithAttribution(this.mPlaybackStream, i, 0, android.app.ActivityThread.currentPackageName(), null);
                    return;
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.media.MediaRouter.TAG, "Error setting local stream volume", e);
                    return;
                }
            }
            android.media.MediaRouter.sStatic.requestSetVolume(this, i);
        }

        public void requestUpdateVolume(int i) {
            if (this.mPlaybackType == 0) {
                try {
                    android.media.MediaRouter.sStatic.mAudioService.setStreamVolumeWithAttribution(this.mPlaybackStream, java.lang.Math.max(0, java.lang.Math.min(getVolume() + i, getVolumeMax())), 0, android.app.ActivityThread.currentPackageName(), null);
                    return;
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.media.MediaRouter.TAG, "Error setting local stream volume", e);
                    return;
                }
            }
            android.media.MediaRouter.sStatic.requestUpdateVolume(this, i);
        }

        public int getVolumeMax() {
            if (this.mPlaybackType == 0) {
                try {
                    return android.media.MediaRouter.sStatic.mAudioService.getStreamMaxVolume(this.mPlaybackStream);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.media.MediaRouter.TAG, "Error getting local stream volume", e);
                    return 0;
                }
            }
            return this.mVolumeMax;
        }

        public int getVolumeHandling() {
            return this.mVolumeHandling;
        }

        public android.view.Display getPresentationDisplay() {
            return this.mPresentationDisplay;
        }

        public boolean updatePresentationDisplay() {
            android.view.Display choosePresentationDisplay = choosePresentationDisplay();
            if (this.mPresentationDisplay != choosePresentationDisplay) {
                this.mPresentationDisplay = choosePresentationDisplay;
                return true;
            }
            return false;
        }

        private android.view.Display choosePresentationDisplay() {
            android.view.Display[] allPresentationDisplays;
            if ((getSupportedTypes() & 2) == 0 || (allPresentationDisplays = getAllPresentationDisplays()) == null || allPresentationDisplays.length == 0) {
                return null;
            }
            if (this.mPresentationDisplayId >= 0) {
                for (android.view.Display display : allPresentationDisplays) {
                    if (display.getDisplayId() == this.mPresentationDisplayId) {
                        return display;
                    }
                }
                return null;
            }
            if (getDeviceAddress() != null) {
                for (android.view.Display display2 : allPresentationDisplays) {
                    if (display2.getType() == 3 && displayAddressEquals(display2)) {
                        return display2;
                    }
                }
            }
            for (android.view.Display display3 : allPresentationDisplays) {
                if (display3.getType() == 2) {
                    return display3;
                }
            }
            for (android.view.Display display4 : allPresentationDisplays) {
                if (display4.getType() == 1) {
                    return display4;
                }
            }
            if (this == getDefaultAudioVideo()) {
                return allPresentationDisplays[0];
            }
            return null;
        }

        public android.view.Display[] getAllPresentationDisplays() {
            return android.media.MediaRouter.sStatic.getAllPresentationDisplays();
        }

        public android.media.MediaRouter.RouteInfo getDefaultAudioVideo() {
            return android.media.MediaRouter.sStatic.mDefaultAudioVideo;
        }

        private boolean displayAddressEquals(android.view.Display display) {
            android.view.DisplayAddress address = display.getAddress();
            if (!(address instanceof android.view.DisplayAddress.Network)) {
                return false;
            }
            return getDeviceAddress().equals(((android.view.DisplayAddress.Network) address).toString());
        }

        public java.lang.String getDeviceAddress() {
            return this.mDeviceAddress;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public boolean isConnecting() {
            return this.mResolvedStatusCode == 2;
        }

        public boolean isSelected() {
            return this == android.media.MediaRouter.sStatic.mSelectedRoute;
        }

        public boolean isDefault() {
            return this == android.media.MediaRouter.sStatic.mDefaultAudioVideo;
        }

        public boolean isBluetooth() {
            return this.mDeviceType == 3;
        }

        public void select() {
            android.media.MediaRouter.selectRouteStatic(this.mSupportedTypes, this, true);
        }

        void setStatusInt(java.lang.CharSequence charSequence) {
            if (!charSequence.equals(this.mStatus)) {
                this.mStatus = charSequence;
                if (this.mGroup != null) {
                    this.mGroup.memberStatusChanged(this, charSequence);
                }
                routeUpdated();
            }
        }

        void routeUpdated() {
            android.media.MediaRouter.updateRoute(this);
        }

        public java.lang.String toString() {
            return getClass().getSimpleName() + "{ name=" + ((java.lang.Object) getName()) + ", description=" + ((java.lang.Object) getDescription()) + ", status=" + ((java.lang.Object) getStatus()) + ", category=" + getCategory() + ", supportedTypes=" + android.media.MediaRouter.typesToString(getSupportedTypes()) + ", presentationDisplay=" + this.mPresentationDisplay + " }";
        }
    }

    public static class UserRouteInfo extends android.media.MediaRouter.RouteInfo {
        android.media.RemoteControlClient mRcc;
        android.media.MediaRouter.UserRouteInfo.SessionVolumeProvider mSvp;

        UserRouteInfo(android.media.MediaRouter.RouteCategory routeCategory) {
            super(routeCategory);
            this.mSupportedTypes = 8388608;
            this.mPlaybackType = 1;
            this.mVolumeHandling = 0;
        }

        public void setName(java.lang.CharSequence charSequence) {
            this.mNameResId = 0;
            this.mName = charSequence;
            routeUpdated();
        }

        public void setName(int i) {
            this.mNameResId = i;
            this.mName = null;
            routeUpdated();
        }

        public void setDescription(java.lang.CharSequence charSequence) {
            this.mDescription = charSequence;
            routeUpdated();
        }

        public void setStatus(java.lang.CharSequence charSequence) {
            setStatusInt(charSequence);
        }

        public void setRemoteControlClient(android.media.RemoteControlClient remoteControlClient) {
            this.mRcc = remoteControlClient;
            updatePlaybackInfoOnRcc();
        }

        public android.media.RemoteControlClient getRemoteControlClient() {
            return this.mRcc;
        }

        public void setIconDrawable(android.graphics.drawable.Drawable drawable) {
            this.mIcon = drawable;
        }

        public void setIconResource(int i) {
            setIconDrawable(android.media.MediaRouter.sStatic.mResources.getDrawable(i));
        }

        public void setVolumeCallback(android.media.MediaRouter.VolumeCallback volumeCallback) {
            this.mVcb = new android.media.MediaRouter.VolumeCallbackInfo(volumeCallback, this);
        }

        public void setPlaybackType(int i) {
            if (this.mPlaybackType != i) {
                this.mPlaybackType = i;
                configureSessionVolume();
            }
        }

        public void setVolumeHandling(int i) {
            if (this.mVolumeHandling != i) {
                this.mVolumeHandling = i;
                configureSessionVolume();
            }
        }

        public void setVolume(int i) {
            int max = java.lang.Math.max(0, java.lang.Math.min(i, getVolumeMax()));
            if (this.mVolume != max) {
                this.mVolume = max;
                if (this.mSvp != null) {
                    this.mSvp.setCurrentVolume(this.mVolume);
                }
                android.media.MediaRouter.dispatchRouteVolumeChanged(this);
                if (this.mGroup != null) {
                    this.mGroup.memberVolumeChanged(this);
                }
            }
        }

        @Override // android.media.MediaRouter.RouteInfo
        public void requestSetVolume(int i) {
            if (this.mVolumeHandling == 1) {
                if (this.mVcb == null) {
                    android.util.Log.e(android.media.MediaRouter.TAG, "Cannot requestSetVolume on user route - no volume callback set");
                } else {
                    this.mVcb.vcb.onVolumeSetRequest(this, i);
                }
            }
        }

        @Override // android.media.MediaRouter.RouteInfo
        public void requestUpdateVolume(int i) {
            if (this.mVolumeHandling == 1) {
                if (this.mVcb == null) {
                    android.util.Log.e(android.media.MediaRouter.TAG, "Cannot requestChangeVolume on user route - no volumec callback set");
                } else {
                    this.mVcb.vcb.onVolumeUpdateRequest(this, i);
                }
            }
        }

        public void setVolumeMax(int i) {
            if (this.mVolumeMax != i) {
                this.mVolumeMax = i;
                configureSessionVolume();
            }
        }

        public void setPlaybackStream(int i) {
            if (this.mPlaybackStream != i) {
                this.mPlaybackStream = i;
                configureSessionVolume();
            }
        }

        private void updatePlaybackInfoOnRcc() {
            configureSessionVolume();
        }

        private void configureSessionVolume() {
            int i;
            if (this.mRcc == null) {
                if (android.media.MediaRouter.DEBUG) {
                    android.util.Log.d(android.media.MediaRouter.TAG, "No Rcc to configure volume for route " + ((java.lang.Object) getName()));
                    return;
                }
                return;
            }
            android.media.session.MediaSession mediaSession = this.mRcc.getMediaSession();
            if (mediaSession == null) {
                if (android.media.MediaRouter.DEBUG) {
                    android.util.Log.d(android.media.MediaRouter.TAG, "Rcc has no session to configure volume");
                    return;
                }
                return;
            }
            if (this.mPlaybackType == 1) {
                switch (this.mVolumeHandling) {
                    case 1:
                        i = 2;
                        break;
                    default:
                        i = 0;
                        break;
                }
                if (this.mSvp == null || this.mSvp.getVolumeControl() != i || this.mSvp.getMaxVolume() != this.mVolumeMax) {
                    this.mSvp = new android.media.MediaRouter.UserRouteInfo.SessionVolumeProvider(i, this.mVolumeMax, this.mVolume);
                    mediaSession.setPlaybackToRemote(this.mSvp);
                    return;
                }
                return;
            }
            android.media.AudioAttributes.Builder builder = new android.media.AudioAttributes.Builder();
            builder.setLegacyStreamType(this.mPlaybackStream);
            mediaSession.setPlaybackToLocal(builder.build());
            this.mSvp = null;
        }

        class SessionVolumeProvider extends android.media.VolumeProvider {
            SessionVolumeProvider(int i, int i2, int i3) {
                super(i, i2, i3);
            }

            @Override // android.media.VolumeProvider
            public void onSetVolumeTo(final int i) {
                android.media.MediaRouter.sStatic.mHandler.post(new java.lang.Runnable() { // from class: android.media.MediaRouter.UserRouteInfo.SessionVolumeProvider.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (android.media.MediaRouter.UserRouteInfo.this.mVcb != null) {
                            android.media.MediaRouter.UserRouteInfo.this.mVcb.vcb.onVolumeSetRequest(android.media.MediaRouter.UserRouteInfo.this.mVcb.route, i);
                        }
                    }
                });
            }

            @Override // android.media.VolumeProvider
            public void onAdjustVolume(final int i) {
                android.media.MediaRouter.sStatic.mHandler.post(new java.lang.Runnable() { // from class: android.media.MediaRouter.UserRouteInfo.SessionVolumeProvider.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (android.media.MediaRouter.UserRouteInfo.this.mVcb != null) {
                            android.media.MediaRouter.UserRouteInfo.this.mVcb.vcb.onVolumeUpdateRequest(android.media.MediaRouter.UserRouteInfo.this.mVcb.route, i);
                        }
                    }
                });
            }
        }
    }

    public static class RouteGroup extends android.media.MediaRouter.RouteInfo {
        final java.util.ArrayList<android.media.MediaRouter.RouteInfo> mRoutes;
        private boolean mUpdateName;

        RouteGroup(android.media.MediaRouter.RouteCategory routeCategory) {
            super(routeCategory);
            this.mRoutes = new java.util.ArrayList<>();
            this.mGroup = this;
            this.mVolumeHandling = 0;
        }

        @Override // android.media.MediaRouter.RouteInfo
        java.lang.CharSequence getName(android.content.res.Resources resources) {
            if (this.mUpdateName) {
                updateName();
            }
            return super.getName(resources);
        }

        public void addRoute(android.media.MediaRouter.RouteInfo routeInfo) {
            if (routeInfo.getGroup() != null) {
                throw new java.lang.IllegalStateException("Route " + routeInfo + " is already part of a group.");
            }
            if (routeInfo.getCategory() != this.mCategory) {
                throw new java.lang.IllegalArgumentException("Route cannot be added to a group with a different category. (Route category=" + routeInfo.getCategory() + " group category=" + this.mCategory + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            int size = this.mRoutes.size();
            this.mRoutes.add(routeInfo);
            routeInfo.mGroup = this;
            this.mUpdateName = true;
            updateVolume();
            routeUpdated();
            android.media.MediaRouter.dispatchRouteGrouped(routeInfo, this, size);
        }

        public void addRoute(android.media.MediaRouter.RouteInfo routeInfo, int i) {
            if (routeInfo.getGroup() != null) {
                throw new java.lang.IllegalStateException("Route " + routeInfo + " is already part of a group.");
            }
            if (routeInfo.getCategory() != this.mCategory) {
                throw new java.lang.IllegalArgumentException("Route cannot be added to a group with a different category. (Route category=" + routeInfo.getCategory() + " group category=" + this.mCategory + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            this.mRoutes.add(i, routeInfo);
            routeInfo.mGroup = this;
            this.mUpdateName = true;
            updateVolume();
            routeUpdated();
            android.media.MediaRouter.dispatchRouteGrouped(routeInfo, this, i);
        }

        public void removeRoute(android.media.MediaRouter.RouteInfo routeInfo) {
            if (routeInfo.getGroup() != this) {
                throw new java.lang.IllegalArgumentException("Route " + routeInfo + " is not a member of this group.");
            }
            this.mRoutes.remove(routeInfo);
            routeInfo.mGroup = null;
            this.mUpdateName = true;
            updateVolume();
            android.media.MediaRouter.dispatchRouteUngrouped(routeInfo, this);
            routeUpdated();
        }

        public void removeRoute(int i) {
            android.media.MediaRouter.RouteInfo remove = this.mRoutes.remove(i);
            remove.mGroup = null;
            this.mUpdateName = true;
            updateVolume();
            android.media.MediaRouter.dispatchRouteUngrouped(remove, this);
            routeUpdated();
        }

        public int getRouteCount() {
            return this.mRoutes.size();
        }

        public android.media.MediaRouter.RouteInfo getRouteAt(int i) {
            return this.mRoutes.get(i);
        }

        public void setIconDrawable(android.graphics.drawable.Drawable drawable) {
            this.mIcon = drawable;
        }

        public void setIconResource(int i) {
            setIconDrawable(android.media.MediaRouter.sStatic.mResources.getDrawable(i));
        }

        @Override // android.media.MediaRouter.RouteInfo
        public void requestSetVolume(int i) {
            int volumeMax = getVolumeMax();
            if (volumeMax == 0) {
                return;
            }
            float f = i / volumeMax;
            int routeCount = getRouteCount();
            for (int i2 = 0; i2 < routeCount; i2++) {
                getRouteAt(i2).requestSetVolume((int) (r3.getVolumeMax() * f));
            }
            if (i != this.mVolume) {
                this.mVolume = i;
                android.media.MediaRouter.dispatchRouteVolumeChanged(this);
            }
        }

        @Override // android.media.MediaRouter.RouteInfo
        public void requestUpdateVolume(int i) {
            if (getVolumeMax() == 0) {
                return;
            }
            int routeCount = getRouteCount();
            int i2 = 0;
            for (int i3 = 0; i3 < routeCount; i3++) {
                android.media.MediaRouter.RouteInfo routeAt = getRouteAt(i3);
                routeAt.requestUpdateVolume(i);
                int volume = routeAt.getVolume();
                if (volume > i2) {
                    i2 = volume;
                }
            }
            if (i2 != this.mVolume) {
                this.mVolume = i2;
                android.media.MediaRouter.dispatchRouteVolumeChanged(this);
            }
        }

        void memberNameChanged(android.media.MediaRouter.RouteInfo routeInfo, java.lang.CharSequence charSequence) {
            this.mUpdateName = true;
            routeUpdated();
        }

        void memberStatusChanged(android.media.MediaRouter.RouteInfo routeInfo, java.lang.CharSequence charSequence) {
            setStatusInt(charSequence);
        }

        void memberVolumeChanged(android.media.MediaRouter.RouteInfo routeInfo) {
            updateVolume();
        }

        void updateVolume() {
            int routeCount = getRouteCount();
            int i = 0;
            for (int i2 = 0; i2 < routeCount; i2++) {
                int volume = getRouteAt(i2).getVolume();
                if (volume > i) {
                    i = volume;
                }
            }
            if (i != this.mVolume) {
                this.mVolume = i;
                android.media.MediaRouter.dispatchRouteVolumeChanged(this);
            }
        }

        @Override // android.media.MediaRouter.RouteInfo
        void routeUpdated() {
            int size = this.mRoutes.size();
            if (size == 0) {
                android.media.MediaRouter.removeRouteStatic(this);
                return;
            }
            int i = 1;
            int i2 = 1;
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < size; i5++) {
                android.media.MediaRouter.RouteInfo routeInfo = this.mRoutes.get(i5);
                i3 |= routeInfo.mSupportedTypes;
                int volumeMax = routeInfo.getVolumeMax();
                if (volumeMax > i4) {
                    i4 = volumeMax;
                }
                i &= routeInfo.getPlaybackType() == 0 ? 1 : 0;
                i2 &= routeInfo.getVolumeHandling() == 0 ? 1 : 0;
            }
            this.mPlaybackType = i ^ 1;
            this.mVolumeHandling = i2 ^ 1;
            this.mSupportedTypes = i3;
            this.mVolumeMax = i4;
            this.mIcon = size == 1 ? this.mRoutes.get(0).getIconDrawable() : null;
            super.routeUpdated();
        }

        void updateName() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                android.media.MediaRouter.RouteInfo routeInfo = this.mRoutes.get(i);
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(routeInfo.getName());
            }
            this.mName = sb.toString();
            this.mUpdateName = false;
        }

        @Override // android.media.MediaRouter.RouteInfo
        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(super.toString());
            sb.append('[');
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.mRoutes.get(i));
            }
            sb.append(']');
            return sb.toString();
        }
    }

    public static class RouteCategory {
        final boolean mGroupable;
        boolean mIsSystem;
        java.lang.CharSequence mName;
        int mNameResId;
        int mTypes;

        RouteCategory(java.lang.CharSequence charSequence, int i, boolean z) {
            this.mName = charSequence;
            this.mTypes = i;
            this.mGroupable = z;
        }

        RouteCategory(int i, int i2, boolean z) {
            this.mNameResId = i;
            this.mTypes = i2;
            this.mGroupable = z;
        }

        public java.lang.CharSequence getName() {
            return getName(android.media.MediaRouter.sStatic.mResources);
        }

        public java.lang.CharSequence getName(android.content.Context context) {
            return getName(context.getResources());
        }

        java.lang.CharSequence getName(android.content.res.Resources resources) {
            if (this.mNameResId != 0) {
                return resources.getText(this.mNameResId);
            }
            return this.mName;
        }

        public java.util.List<android.media.MediaRouter.RouteInfo> getRoutes(java.util.List<android.media.MediaRouter.RouteInfo> list) {
            if (list == null) {
                list = new java.util.ArrayList<>();
            } else {
                list.clear();
            }
            int routeCountStatic = android.media.MediaRouter.getRouteCountStatic();
            for (int i = 0; i < routeCountStatic; i++) {
                android.media.MediaRouter.RouteInfo routeAtStatic = android.media.MediaRouter.getRouteAtStatic(i);
                if (routeAtStatic.mCategory == this) {
                    list.add(routeAtStatic);
                }
            }
            return list;
        }

        public int getSupportedTypes() {
            return this.mTypes;
        }

        public boolean isGroupable() {
            return this.mGroupable;
        }

        public boolean isSystem() {
            return this.mIsSystem;
        }

        public java.lang.String toString() {
            return "RouteCategory{ name=" + ((java.lang.Object) getName()) + " types=" + android.media.MediaRouter.typesToString(this.mTypes) + " groupable=" + this.mGroupable + " }";
        }
    }

    static class CallbackInfo {
        public final android.media.MediaRouter.Callback cb;
        public int flags;
        public final android.media.MediaRouter router;
        public int type;

        public CallbackInfo(android.media.MediaRouter.Callback callback, int i, int i2, android.media.MediaRouter mediaRouter) {
            this.cb = callback;
            this.type = i;
            this.flags = i2;
            this.router = mediaRouter;
        }

        public boolean filterRouteEvent(android.media.MediaRouter.RouteInfo routeInfo) {
            return filterRouteEvent(routeInfo.mSupportedTypes);
        }

        public boolean filterRouteEvent(int i) {
            return ((this.flags & 2) == 0 && (i & this.type) == 0) ? false : true;
        }
    }

    public static abstract class Callback {
        public abstract void onRouteAdded(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo);

        public abstract void onRouteChanged(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo);

        public abstract void onRouteGrouped(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup, int i);

        public abstract void onRouteRemoved(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo);

        public abstract void onRouteSelected(android.media.MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo);

        public abstract void onRouteUngrouped(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup);

        public abstract void onRouteUnselected(android.media.MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo);

        public abstract void onRouteVolumeChanged(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo);

        public void onRoutePresentationDisplayChanged(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
        }
    }

    public static class SimpleCallback extends android.media.MediaRouter.Callback {
        @Override // android.media.MediaRouter.Callback
        public void onRouteSelected(android.media.MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteUnselected(android.media.MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteAdded(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteRemoved(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteChanged(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteGrouped(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup, int i) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteUngrouped(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup) {
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteVolumeChanged(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
        }
    }

    static class VolumeCallbackInfo {
        public final android.media.MediaRouter.RouteInfo route;
        public final android.media.MediaRouter.VolumeCallback vcb;

        public VolumeCallbackInfo(android.media.MediaRouter.VolumeCallback volumeCallback, android.media.MediaRouter.RouteInfo routeInfo) {
            this.vcb = volumeCallback;
            this.route = routeInfo;
        }
    }

    static class VolumeChangeReceiver extends android.content.BroadcastReceiver {
        VolumeChangeReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                int intExtra = intent.getIntExtra(android.media.AudioManager.EXTRA_VOLUME_STREAM_TYPE, -1);
                int intExtra2 = intent.getIntExtra(android.media.AudioManager.EXTRA_VOLUME_STREAM_VALUE, 0);
                android.media.MediaRouter.sStatic.mStreamVolume.put(intExtra, intExtra2);
                if (intExtra == 3 && intExtra2 != intent.getIntExtra(android.media.AudioManager.EXTRA_PREV_VOLUME_STREAM_VALUE, 0)) {
                    android.media.MediaRouter.systemVolumeChanged(intExtra2);
                }
            }
        }
    }

    static class WifiDisplayStatusChangedReceiver extends android.content.BroadcastReceiver {
        WifiDisplayStatusChangedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (intent.getAction().equals(android.hardware.display.DisplayManager.ACTION_WIFI_DISPLAY_STATUS_CHANGED)) {
                android.media.MediaRouter.updateWifiDisplayStatus((android.hardware.display.WifiDisplayStatus) intent.getParcelableExtra(android.hardware.display.DisplayManager.EXTRA_WIFI_DISPLAY_STATUS, android.hardware.display.WifiDisplayStatus.class));
            }
        }
    }
}
