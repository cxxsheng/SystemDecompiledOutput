package com.android.server.display;

/* loaded from: classes.dex */
final class WifiDisplayController implements com.android.internal.util.DumpUtils.Dump {
    private static final int CONNECTION_TIMEOUT_SECONDS = 30;
    private static final int CONNECT_MAX_RETRIES = 3;
    private static final int CONNECT_RETRY_DELAY_MILLIS = 500;
    private static final int DEFAULT_CONTROL_PORT = 7236;
    private static final int DISCOVER_PEERS_INTERVAL_MILLIS = 10000;
    private static final int MAX_THROUGHPUT = 50;
    private static final int RTSP_TIMEOUT_SECONDS = 30;
    private static final int RTSP_TIMEOUT_SECONDS_CERT_MODE = 120;
    private android.hardware.display.WifiDisplay mAdvertisedDisplay;
    private int mAdvertisedDisplayFlags;
    private int mAdvertisedDisplayHeight;
    private android.view.Surface mAdvertisedDisplaySurface;
    private int mAdvertisedDisplayWidth;
    private android.net.wifi.p2p.WifiP2pDevice mCancelingDevice;
    private android.net.wifi.p2p.WifiP2pDevice mConnectedDevice;
    private android.net.wifi.p2p.WifiP2pGroup mConnectedDeviceGroupInfo;
    private android.net.wifi.p2p.WifiP2pDevice mConnectingDevice;
    private int mConnectionRetriesLeft;
    private final android.content.Context mContext;
    private android.net.wifi.p2p.WifiP2pDevice mDesiredDevice;
    private android.net.wifi.p2p.WifiP2pDevice mDisconnectingDevice;
    private boolean mDiscoverPeersInProgress;
    private java.lang.Object mExtRemoteDisplay;
    private final android.os.Handler mHandler;
    private final com.android.server.display.WifiDisplayController.Listener mListener;
    private android.net.NetworkInfo mNetworkInfo;
    private android.media.RemoteDisplay mRemoteDisplay;
    private boolean mRemoteDisplayConnected;
    private java.lang.String mRemoteDisplayInterface;
    private boolean mScanRequested;
    private android.net.wifi.p2p.WifiP2pDevice mThisDevice;
    private boolean mWfdEnabled;
    private boolean mWfdEnabling;
    private boolean mWifiDisplayCertMode;
    private boolean mWifiDisplayOnSetting;
    private android.net.wifi.p2p.WifiP2pManager.Channel mWifiP2pChannel;
    private boolean mWifiP2pEnabled;
    private android.net.wifi.p2p.WifiP2pManager mWifiP2pManager;
    private static final java.lang.String TAG = "WifiDisplayController";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);
    private final java.util.ArrayList<android.net.wifi.p2p.WifiP2pDevice> mAvailableWifiDisplayPeers = new java.util.ArrayList<>();
    private int mWifiDisplayWpsConfig = 4;
    private final java.lang.Runnable mDiscoverPeers = new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayController.16
        @Override // java.lang.Runnable
        public void run() {
            com.android.server.display.WifiDisplayController.this.tryDiscoverPeers();
        }
    };
    private final java.lang.Runnable mConnectionTimeout = new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayController.17
        @Override // java.lang.Runnable
        public void run() {
            if (com.android.server.display.WifiDisplayController.this.mConnectingDevice != null && com.android.server.display.WifiDisplayController.this.mConnectingDevice == com.android.server.display.WifiDisplayController.this.mDesiredDevice) {
                android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Timed out waiting for Wifi display connection after 30 seconds: " + com.android.server.display.WifiDisplayController.this.mConnectingDevice.deviceName);
                com.android.server.display.WifiDisplayController.this.handleConnectionFailure(true);
            }
        }
    };
    private final java.lang.Runnable mRtspTimeout = new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayController.18
        @Override // java.lang.Runnable
        public void run() {
            if (com.android.server.display.WifiDisplayController.this.mConnectedDevice != null) {
                if ((com.android.server.display.WifiDisplayController.this.mRemoteDisplay != null || com.android.server.display.WifiDisplayController.this.mExtRemoteDisplay != null) && !com.android.server.display.WifiDisplayController.this.mRemoteDisplayConnected) {
                    android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Timed out waiting for Wifi display RTSP connection after 30 seconds: " + com.android.server.display.WifiDisplayController.this.mConnectedDevice.deviceName);
                    com.android.server.display.WifiDisplayController.this.handleConnectionFailure(true);
                }
            }
        }
    };
    private final android.content.BroadcastReceiver mWifiP2pReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.display.WifiDisplayController.22
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            if (action.equals("android.net.wifi.p2p.STATE_CHANGED")) {
                boolean z = intent.getIntExtra("wifi_p2p_state", 1) == 2;
                if (com.android.server.display.WifiDisplayController.DEBUG) {
                    android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Received WIFI_P2P_STATE_CHANGED_ACTION: enabled=" + z);
                }
                com.android.server.display.WifiDisplayController.this.handleStateChanged(z);
                return;
            }
            if (action.equals("android.net.wifi.p2p.PEERS_CHANGED")) {
                if (com.android.server.display.WifiDisplayController.DEBUG) {
                    android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Received WIFI_P2P_PEERS_CHANGED_ACTION.");
                }
                com.android.server.display.WifiDisplayController.this.handlePeersChanged();
                return;
            }
            if (action.equals("android.net.wifi.p2p.CONNECTION_STATE_CHANGE")) {
                android.net.NetworkInfo networkInfo = (android.net.NetworkInfo) intent.getParcelableExtra("networkInfo", android.net.NetworkInfo.class);
                if (com.android.server.display.WifiDisplayController.DEBUG) {
                    android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Received WIFI_P2P_CONNECTION_CHANGED_ACTION: networkInfo=" + networkInfo);
                }
                com.android.server.display.WifiDisplayController.this.handleConnectionChanged(networkInfo);
                return;
            }
            if (action.equals("android.net.wifi.p2p.THIS_DEVICE_CHANGED")) {
                com.android.server.display.WifiDisplayController.this.mThisDevice = (android.net.wifi.p2p.WifiP2pDevice) intent.getParcelableExtra("wifiP2pDevice", android.net.wifi.p2p.WifiP2pDevice.class);
                if (com.android.server.display.WifiDisplayController.DEBUG) {
                    android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Received WIFI_P2P_THIS_DEVICE_CHANGED_ACTION: mThisDevice= " + com.android.server.display.WifiDisplayController.this.mThisDevice);
                }
            }
        }
    };

    public interface Listener {
        void onDisplayChanged(android.hardware.display.WifiDisplay wifiDisplay);

        void onDisplayConnected(android.hardware.display.WifiDisplay wifiDisplay, android.view.Surface surface, int i, int i2, int i3);

        void onDisplayConnecting(android.hardware.display.WifiDisplay wifiDisplay);

        void onDisplayConnectionFailed();

        void onDisplayDisconnected();

        void onDisplaySessionInfo(android.hardware.display.WifiDisplaySessionInfo wifiDisplaySessionInfo);

        void onFeatureStateChanged(int i);

        void onScanFinished();

        void onScanResults(android.hardware.display.WifiDisplay[] wifiDisplayArr);

        void onScanStarted();
    }

    public WifiDisplayController(android.content.Context context, android.os.Handler handler, com.android.server.display.WifiDisplayController.Listener listener) {
        this.mContext = context;
        this.mHandler = handler;
        this.mListener = listener;
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.net.wifi.p2p.STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.p2p.PEERS_CHANGED");
        intentFilter.addAction("android.net.wifi.p2p.CONNECTION_STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.p2p.THIS_DEVICE_CHANGED");
        context.registerReceiver(this.mWifiP2pReceiver, intentFilter, null, this.mHandler);
        android.database.ContentObserver contentObserver = new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.display.WifiDisplayController.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z, android.net.Uri uri) {
                com.android.server.display.WifiDisplayController.this.updateSettings();
            }
        };
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("wifi_display_on"), false, contentObserver);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("wifi_display_certification_on"), false, contentObserver);
        contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("wifi_display_wps_config"), false, contentObserver);
        updateSettings();
    }

    private void retrieveWifiP2pManagerAndChannel() {
        if (this.mWifiP2pManager == null) {
            this.mWifiP2pManager = (android.net.wifi.p2p.WifiP2pManager) this.mContext.getSystemService("wifip2p");
        }
        if (this.mWifiP2pChannel == null && this.mWifiP2pManager != null) {
            this.mWifiP2pChannel = this.mWifiP2pManager.initialize(this.mContext, this.mHandler.getLooper(), null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSettings() {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mWifiDisplayOnSetting = android.provider.Settings.Global.getInt(contentResolver, "wifi_display_on", 0) != 0;
        this.mWifiDisplayCertMode = android.provider.Settings.Global.getInt(contentResolver, "wifi_display_certification_on", 0) != 0;
        this.mWifiDisplayWpsConfig = 4;
        if (this.mWifiDisplayCertMode) {
            this.mWifiDisplayWpsConfig = android.provider.Settings.Global.getInt(contentResolver, "wifi_display_wps_config", 4);
        }
        updateWfdEnableState();
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println("mWifiDisplayOnSetting=" + this.mWifiDisplayOnSetting);
        printWriter.println("mWifiP2pEnabled=" + this.mWifiP2pEnabled);
        printWriter.println("mWfdEnabled=" + this.mWfdEnabled);
        printWriter.println("mWfdEnabling=" + this.mWfdEnabling);
        printWriter.println("mNetworkInfo=" + this.mNetworkInfo);
        printWriter.println("mScanRequested=" + this.mScanRequested);
        printWriter.println("mDiscoverPeersInProgress=" + this.mDiscoverPeersInProgress);
        printWriter.println("mDesiredDevice=" + describeWifiP2pDevice(this.mDesiredDevice));
        printWriter.println("mConnectingDisplay=" + describeWifiP2pDevice(this.mConnectingDevice));
        printWriter.println("mDisconnectingDisplay=" + describeWifiP2pDevice(this.mDisconnectingDevice));
        printWriter.println("mCancelingDisplay=" + describeWifiP2pDevice(this.mCancelingDevice));
        printWriter.println("mConnectedDevice=" + describeWifiP2pDevice(this.mConnectedDevice));
        printWriter.println("mConnectionRetriesLeft=" + this.mConnectionRetriesLeft);
        printWriter.println("mRemoteDisplay=" + this.mRemoteDisplay);
        printWriter.println("mRemoteDisplayInterface=" + this.mRemoteDisplayInterface);
        printWriter.println("mRemoteDisplayConnected=" + this.mRemoteDisplayConnected);
        printWriter.println("mAdvertisedDisplay=" + this.mAdvertisedDisplay);
        printWriter.println("mAdvertisedDisplaySurface=" + this.mAdvertisedDisplaySurface);
        printWriter.println("mAdvertisedDisplayWidth=" + this.mAdvertisedDisplayWidth);
        printWriter.println("mAdvertisedDisplayHeight=" + this.mAdvertisedDisplayHeight);
        printWriter.println("mAdvertisedDisplayFlags=" + this.mAdvertisedDisplayFlags);
        printWriter.println("mAvailableWifiDisplayPeers: size=" + this.mAvailableWifiDisplayPeers.size());
        java.util.Iterator<android.net.wifi.p2p.WifiP2pDevice> it = this.mAvailableWifiDisplayPeers.iterator();
        while (it.hasNext()) {
            printWriter.println("  " + describeWifiP2pDevice(it.next()));
        }
    }

    public void requestStartScan() {
        if (!this.mScanRequested) {
            this.mScanRequested = true;
            updateScanState();
        }
    }

    public void requestStopScan() {
        if (this.mScanRequested) {
            this.mScanRequested = false;
            updateScanState();
        }
    }

    public void requestConnect(java.lang.String str) {
        java.util.Iterator<android.net.wifi.p2p.WifiP2pDevice> it = this.mAvailableWifiDisplayPeers.iterator();
        while (it.hasNext()) {
            android.net.wifi.p2p.WifiP2pDevice next = it.next();
            if (next.deviceAddress.equals(str)) {
                connect(next);
            }
        }
    }

    public void requestPause() {
        if (this.mRemoteDisplay != null) {
            this.mRemoteDisplay.pause();
        }
    }

    public void requestResume() {
        if (this.mRemoteDisplay != null) {
            this.mRemoteDisplay.resume();
        }
    }

    public void requestDisconnect() {
        disconnect();
    }

    private void updateWfdEnableState() {
        if (this.mWifiDisplayOnSetting && this.mWifiP2pEnabled) {
            if (!this.mWfdEnabled && !this.mWfdEnabling) {
                this.mWfdEnabling = true;
                android.net.wifi.p2p.WifiP2pWfdInfo wifiP2pWfdInfo = new android.net.wifi.p2p.WifiP2pWfdInfo();
                wifiP2pWfdInfo.setEnabled(true);
                wifiP2pWfdInfo.setDeviceType(0);
                wifiP2pWfdInfo.setSessionAvailable(true);
                wifiP2pWfdInfo.setControlPort(DEFAULT_CONTROL_PORT);
                wifiP2pWfdInfo.setMaxThroughput(50);
                this.mWifiP2pManager.setWfdInfo(this.mWifiP2pChannel, wifiP2pWfdInfo, new android.net.wifi.p2p.WifiP2pManager.ActionListener() { // from class: com.android.server.display.WifiDisplayController.2
                    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                    public void onSuccess() {
                        if (com.android.server.display.WifiDisplayController.DEBUG) {
                            android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Successfully set WFD info.");
                        }
                        if (com.android.server.display.WifiDisplayController.this.mWfdEnabling) {
                            com.android.server.display.WifiDisplayController.this.mWfdEnabling = false;
                            com.android.server.display.WifiDisplayController.this.mWfdEnabled = true;
                            com.android.server.display.WifiDisplayController.this.reportFeatureState();
                            com.android.server.display.WifiDisplayController.this.updateScanState();
                        }
                    }

                    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                    public void onFailure(int i) {
                        if (com.android.server.display.WifiDisplayController.DEBUG) {
                            android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Failed to set WFD info with reason " + i + ".");
                        }
                        com.android.server.display.WifiDisplayController.this.mWfdEnabling = false;
                    }
                });
                return;
            }
            return;
        }
        if (this.mWfdEnabled || this.mWfdEnabling) {
            android.net.wifi.p2p.WifiP2pWfdInfo wifiP2pWfdInfo2 = new android.net.wifi.p2p.WifiP2pWfdInfo();
            wifiP2pWfdInfo2.setEnabled(false);
            this.mWifiP2pManager.setWfdInfo(this.mWifiP2pChannel, wifiP2pWfdInfo2, new android.net.wifi.p2p.WifiP2pManager.ActionListener() { // from class: com.android.server.display.WifiDisplayController.3
                @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                public void onSuccess() {
                    if (com.android.server.display.WifiDisplayController.DEBUG) {
                        android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Successfully set WFD info.");
                    }
                }

                @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                public void onFailure(int i) {
                    if (com.android.server.display.WifiDisplayController.DEBUG) {
                        android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Failed to set WFD info with reason " + i + ".");
                    }
                }
            });
        }
        this.mWfdEnabling = false;
        this.mWfdEnabled = false;
        reportFeatureState();
        updateScanState();
        disconnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportFeatureState() {
        final int computeFeatureState = computeFeatureState();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayController.4
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.display.WifiDisplayController.this.mListener.onFeatureStateChanged(computeFeatureState);
            }
        });
    }

    private int computeFeatureState() {
        if (this.mWifiP2pEnabled) {
            return this.mWifiDisplayOnSetting ? 3 : 2;
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateScanState() {
        if (this.mScanRequested && this.mWfdEnabled && this.mDesiredDevice == null && this.mConnectedDevice == null && this.mDisconnectingDevice == null) {
            if (!this.mDiscoverPeersInProgress) {
                android.util.Slog.i(TAG, "Starting Wifi display scan.");
                this.mDiscoverPeersInProgress = true;
                handleScanStarted();
                tryDiscoverPeers();
                return;
            }
            return;
        }
        if (this.mDiscoverPeersInProgress) {
            this.mHandler.removeCallbacks(this.mDiscoverPeers);
            if (this.mDesiredDevice == null || this.mDesiredDevice == this.mConnectedDevice) {
                android.util.Slog.i(TAG, "Stopping Wifi display scan.");
                this.mDiscoverPeersInProgress = false;
                stopPeerDiscovery();
                handleScanFinished();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryDiscoverPeers() {
        this.mWifiP2pManager.discoverPeers(this.mWifiP2pChannel, new android.net.wifi.p2p.WifiP2pManager.ActionListener() { // from class: com.android.server.display.WifiDisplayController.5
            @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
            public void onSuccess() {
                if (com.android.server.display.WifiDisplayController.DEBUG) {
                    android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Discover peers succeeded.  Requesting peers now.");
                }
                if (com.android.server.display.WifiDisplayController.this.mDiscoverPeersInProgress) {
                    com.android.server.display.WifiDisplayController.this.requestPeers();
                }
            }

            @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
            public void onFailure(int i) {
                if (com.android.server.display.WifiDisplayController.DEBUG) {
                    android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Discover peers failed with reason " + i + ".");
                }
            }
        });
        this.mHandler.postDelayed(this.mDiscoverPeers, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
    }

    private void stopPeerDiscovery() {
        this.mWifiP2pManager.stopPeerDiscovery(this.mWifiP2pChannel, new android.net.wifi.p2p.WifiP2pManager.ActionListener() { // from class: com.android.server.display.WifiDisplayController.6
            @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
            public void onSuccess() {
                if (com.android.server.display.WifiDisplayController.DEBUG) {
                    android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Stop peer discovery succeeded.");
                }
            }

            @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
            public void onFailure(int i) {
                if (com.android.server.display.WifiDisplayController.DEBUG) {
                    android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Stop peer discovery failed with reason " + i + ".");
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestPeers() {
        this.mWifiP2pManager.requestPeers(this.mWifiP2pChannel, new android.net.wifi.p2p.WifiP2pManager.PeerListListener() { // from class: com.android.server.display.WifiDisplayController.7
            @Override // android.net.wifi.p2p.WifiP2pManager.PeerListListener
            public void onPeersAvailable(android.net.wifi.p2p.WifiP2pDeviceList wifiP2pDeviceList) {
                if (com.android.server.display.WifiDisplayController.DEBUG) {
                    android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Received list of peers.");
                }
                com.android.server.display.WifiDisplayController.this.mAvailableWifiDisplayPeers.clear();
                for (android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice : wifiP2pDeviceList.getDeviceList()) {
                    if (com.android.server.display.WifiDisplayController.DEBUG) {
                        android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "  " + com.android.server.display.WifiDisplayController.describeWifiP2pDevice(wifiP2pDevice));
                    }
                    if (com.android.server.display.WifiDisplayController.isWifiDisplay(wifiP2pDevice)) {
                        com.android.server.display.WifiDisplayController.this.mAvailableWifiDisplayPeers.add(wifiP2pDevice);
                    }
                }
                if (com.android.server.display.WifiDisplayController.this.mDiscoverPeersInProgress) {
                    com.android.server.display.WifiDisplayController.this.handleScanResults();
                }
            }
        });
    }

    private void handleScanStarted() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayController.8
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.display.WifiDisplayController.this.mListener.onScanStarted();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScanResults() {
        int size = this.mAvailableWifiDisplayPeers.size();
        final android.hardware.display.WifiDisplay[] wifiDisplayArr = (android.hardware.display.WifiDisplay[]) android.hardware.display.WifiDisplay.CREATOR.newArray(size);
        for (int i = 0; i < size; i++) {
            android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice = this.mAvailableWifiDisplayPeers.get(i);
            wifiDisplayArr[i] = createWifiDisplay(wifiP2pDevice);
            updateDesiredDevice(wifiP2pDevice);
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayController.9
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.display.WifiDisplayController.this.mListener.onScanResults(wifiDisplayArr);
            }
        });
    }

    private void handleScanFinished() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayController.10
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.display.WifiDisplayController.this.mListener.onScanFinished();
            }
        });
    }

    private void updateDesiredDevice(android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice) {
        java.lang.String str = wifiP2pDevice.deviceAddress;
        if (this.mDesiredDevice != null && this.mDesiredDevice.deviceAddress.equals(str)) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "updateDesiredDevice: new information " + describeWifiP2pDevice(wifiP2pDevice));
            }
            this.mDesiredDevice.update(wifiP2pDevice);
            if (this.mAdvertisedDisplay != null && this.mAdvertisedDisplay.getDeviceAddress().equals(str)) {
                readvertiseDisplay(createWifiDisplay(this.mDesiredDevice));
            }
        }
    }

    private void connect(android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice) {
        if (this.mDesiredDevice != null && !this.mDesiredDevice.deviceAddress.equals(wifiP2pDevice.deviceAddress)) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "connect: nothing to do, already connecting to " + describeWifiP2pDevice(wifiP2pDevice));
                return;
            }
            return;
        }
        if (this.mConnectedDevice != null && !this.mConnectedDevice.deviceAddress.equals(wifiP2pDevice.deviceAddress) && this.mDesiredDevice == null) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "connect: nothing to do, already connected to " + describeWifiP2pDevice(wifiP2pDevice) + " and not part way through connecting to a different device.");
                return;
            }
            return;
        }
        if (!this.mWfdEnabled) {
            android.util.Slog.i(TAG, "Ignoring request to connect to Wifi display because the  feature is currently disabled: " + wifiP2pDevice.deviceName);
            return;
        }
        if (handlePreExistingConnection(wifiP2pDevice)) {
            android.util.Slog.i(TAG, "Already handle the preexisting P2P connection status");
            return;
        }
        this.mDesiredDevice = wifiP2pDevice;
        this.mConnectionRetriesLeft = 3;
        updateConnection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnect() {
        this.mDesiredDevice = null;
        updateConnection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void retryConnection() {
        this.mDesiredDevice = new android.net.wifi.p2p.WifiP2pDevice(this.mDesiredDevice);
        updateConnection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateConnection() {
        updateScanState();
        if (((this.mRemoteDisplay != null || this.mExtRemoteDisplay != null) && this.mConnectedDevice != this.mDesiredDevice) || (this.mRemoteDisplayInterface != null && this.mConnectedDevice == null)) {
            android.util.Slog.i(TAG, "Stopped listening for RTSP connection on " + this.mRemoteDisplayInterface);
            if (this.mRemoteDisplay != null) {
                this.mRemoteDisplay.dispose();
            } else if (this.mExtRemoteDisplay != null) {
                com.android.server.display.ExtendedRemoteDisplayHelper.dispose(this.mExtRemoteDisplay);
            }
            this.mExtRemoteDisplay = null;
            this.mRemoteDisplay = null;
            this.mRemoteDisplayInterface = null;
            this.mHandler.removeCallbacks(this.mRtspTimeout);
            this.mWifiP2pManager.setMiracastMode(0);
            unadvertiseDisplay();
        }
        if (this.mRemoteDisplayConnected || this.mDisconnectingDevice != null) {
            return;
        }
        if (this.mConnectedDevice != null && this.mConnectedDevice != this.mDesiredDevice) {
            android.util.Slog.i(TAG, "Disconnecting from Wifi display: " + this.mConnectedDevice.deviceName);
            this.mDisconnectingDevice = this.mConnectedDevice;
            this.mConnectedDevice = null;
            this.mConnectedDeviceGroupInfo = null;
            unadvertiseDisplay();
            final android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice = this.mDisconnectingDevice;
            this.mWifiP2pManager.removeGroup(this.mWifiP2pChannel, new android.net.wifi.p2p.WifiP2pManager.ActionListener() { // from class: com.android.server.display.WifiDisplayController.11
                @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                public void onSuccess() {
                    android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Disconnected from Wifi display: " + wifiP2pDevice.deviceName);
                    next();
                }

                @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                public void onFailure(int i) {
                    android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Failed to disconnect from Wifi display: " + wifiP2pDevice.deviceName + ", reason=" + i);
                    next();
                }

                private void next() {
                    if (com.android.server.display.WifiDisplayController.this.mDisconnectingDevice == wifiP2pDevice) {
                        com.android.server.display.WifiDisplayController.this.mDisconnectingDevice = null;
                        com.android.server.display.WifiDisplayController.this.updateConnection();
                    }
                }
            });
            return;
        }
        if (this.mCancelingDevice != null) {
            return;
        }
        if (this.mConnectingDevice != null && this.mConnectingDevice != this.mDesiredDevice) {
            android.util.Slog.i(TAG, "Canceling connection to Wifi display: " + this.mConnectingDevice.deviceName);
            this.mCancelingDevice = this.mConnectingDevice;
            this.mConnectingDevice = null;
            unadvertiseDisplay();
            this.mHandler.removeCallbacks(this.mConnectionTimeout);
            final android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice2 = this.mCancelingDevice;
            this.mWifiP2pManager.cancelConnect(this.mWifiP2pChannel, new android.net.wifi.p2p.WifiP2pManager.ActionListener() { // from class: com.android.server.display.WifiDisplayController.12
                @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                public void onSuccess() {
                    android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Canceled connection to Wifi display: " + wifiP2pDevice2.deviceName);
                    next();
                }

                @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                public void onFailure(int i) {
                    android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Failed to cancel connection to Wifi display: " + wifiP2pDevice2.deviceName + ", reason=" + i);
                    next();
                }

                private void next() {
                    if (com.android.server.display.WifiDisplayController.this.mCancelingDevice == wifiP2pDevice2) {
                        com.android.server.display.WifiDisplayController.this.mCancelingDevice = null;
                        com.android.server.display.WifiDisplayController.this.updateConnection();
                    }
                }
            });
            return;
        }
        if (this.mDesiredDevice == null) {
            if (this.mWifiDisplayCertMode) {
                this.mListener.onDisplaySessionInfo(getSessionInfo(this.mConnectedDeviceGroupInfo, 0));
            }
            unadvertiseDisplay();
            return;
        }
        final android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice3 = this.mDesiredDevice;
        android.media.RemoteDisplay.Listener listener = new android.media.RemoteDisplay.Listener() { // from class: com.android.server.display.WifiDisplayController.13
            public void onDisplayConnected(android.view.Surface surface, int i, int i2, int i3, int i4) {
                if (com.android.server.display.WifiDisplayController.this.mConnectedDevice == wifiP2pDevice3 && !com.android.server.display.WifiDisplayController.this.mRemoteDisplayConnected) {
                    android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Opened RTSP connection with Wifi display: " + com.android.server.display.WifiDisplayController.this.mConnectedDevice.deviceName);
                    com.android.server.display.WifiDisplayController.this.mRemoteDisplayConnected = true;
                    com.android.server.display.WifiDisplayController.this.mHandler.removeCallbacks(com.android.server.display.WifiDisplayController.this.mRtspTimeout);
                    if (com.android.server.display.WifiDisplayController.this.mWifiDisplayCertMode) {
                        com.android.server.display.WifiDisplayController.this.mListener.onDisplaySessionInfo(com.android.server.display.WifiDisplayController.this.getSessionInfo(com.android.server.display.WifiDisplayController.this.mConnectedDeviceGroupInfo, i4));
                    }
                    com.android.server.display.WifiDisplayController.this.advertiseDisplay(com.android.server.display.WifiDisplayController.createWifiDisplay(com.android.server.display.WifiDisplayController.this.mConnectedDevice), surface, i, i2, i3);
                }
            }

            public void onDisplayDisconnected() {
                if (com.android.server.display.WifiDisplayController.this.mConnectedDevice == wifiP2pDevice3) {
                    android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Closed RTSP connection with Wifi display: " + com.android.server.display.WifiDisplayController.this.mConnectedDevice.deviceName);
                    com.android.server.display.WifiDisplayController.this.mHandler.removeCallbacks(com.android.server.display.WifiDisplayController.this.mRtspTimeout);
                    com.android.server.display.WifiDisplayController.this.mRemoteDisplayConnected = false;
                    com.android.server.display.WifiDisplayController.this.disconnect();
                }
            }

            public void onDisplayError(int i) {
                if (com.android.server.display.WifiDisplayController.this.mConnectedDevice == wifiP2pDevice3) {
                    android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Lost RTSP connection with Wifi display due to error " + i + ": " + com.android.server.display.WifiDisplayController.this.mConnectedDevice.deviceName);
                    com.android.server.display.WifiDisplayController.this.mHandler.removeCallbacks(com.android.server.display.WifiDisplayController.this.mRtspTimeout);
                    com.android.server.display.WifiDisplayController.this.handleConnectionFailure(false);
                }
            }
        };
        if (this.mConnectedDevice == null && this.mConnectingDevice == null) {
            android.util.Slog.i(TAG, "Connecting to Wifi display: " + this.mDesiredDevice.deviceName);
            this.mConnectingDevice = this.mDesiredDevice;
            android.net.wifi.p2p.WifiP2pConfig wifiP2pConfig = new android.net.wifi.p2p.WifiP2pConfig();
            android.net.wifi.WpsInfo wpsInfo = new android.net.wifi.WpsInfo();
            if (this.mWifiDisplayWpsConfig != 4) {
                wpsInfo.setup = this.mWifiDisplayWpsConfig;
            } else if (this.mConnectingDevice.wpsPbcSupported()) {
                wpsInfo.setup = 0;
            } else if (this.mConnectingDevice.wpsDisplaySupported()) {
                wpsInfo.setup = 2;
            } else {
                wpsInfo.setup = 1;
            }
            wifiP2pConfig.wps = wpsInfo;
            wifiP2pConfig.deviceAddress = this.mConnectingDevice.deviceAddress;
            wifiP2pConfig.groupOwnerIntent = 0;
            advertiseDisplay(createWifiDisplay(this.mConnectingDevice), null, 0, 0, 0);
            if (com.android.server.display.ExtendedRemoteDisplayHelper.isAvailable() && this.mExtRemoteDisplay == null) {
                java.lang.String str = "255.255.255.255:" + getPortNumber(this.mDesiredDevice);
                this.mRemoteDisplayInterface = str;
                android.util.Slog.i(TAG, "Listening for RTSP connection on " + str + " from Wifi display: " + this.mDesiredDevice.deviceName);
                this.mExtRemoteDisplay = com.android.server.display.ExtendedRemoteDisplayHelper.listen(str, listener, this.mHandler, this.mContext);
            }
            final android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice4 = this.mDesiredDevice;
            this.mWifiP2pManager.connect(this.mWifiP2pChannel, wifiP2pConfig, new android.net.wifi.p2p.WifiP2pManager.ActionListener() { // from class: com.android.server.display.WifiDisplayController.14
                @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                public void onSuccess() {
                    android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Initiated connection to Wifi display: " + wifiP2pDevice4.deviceName);
                    com.android.server.display.WifiDisplayController.this.mHandler.postDelayed(com.android.server.display.WifiDisplayController.this.mConnectionTimeout, 30000L);
                }

                @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                public void onFailure(int i) {
                    if (com.android.server.display.WifiDisplayController.this.mConnectingDevice == wifiP2pDevice4) {
                        android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Failed to initiate connection to Wifi display: " + wifiP2pDevice4.deviceName + ", reason=" + i);
                        com.android.server.display.WifiDisplayController.this.mConnectingDevice = null;
                        com.android.server.display.WifiDisplayController.this.handleConnectionFailure(false);
                    }
                }
            });
            return;
        }
        if (this.mConnectedDevice != null && this.mRemoteDisplay == null) {
            java.net.Inet4Address interfaceAddress = getInterfaceAddress(this.mConnectedDeviceGroupInfo);
            if (interfaceAddress == null) {
                android.util.Slog.i(TAG, "Failed to get local interface address for communicating with Wifi display: " + this.mConnectedDevice.deviceName);
                handleConnectionFailure(false);
                return;
            }
            this.mWifiP2pManager.setMiracastMode(1);
            java.lang.String str2 = interfaceAddress.getHostAddress() + ":" + getPortNumber(this.mConnectedDevice);
            this.mRemoteDisplayInterface = str2;
            if (!com.android.server.display.ExtendedRemoteDisplayHelper.isAvailable()) {
                android.util.Slog.i(TAG, "Listening for RTSP connection on " + str2 + " from Wifi display: " + this.mConnectedDevice.deviceName);
                this.mRemoteDisplay = android.media.RemoteDisplay.listen(str2, listener, this.mHandler, this.mContext.getOpPackageName());
            }
            this.mHandler.postDelayed(this.mRtspTimeout, (this.mWifiDisplayCertMode ? 120 : 30) * 1000);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.display.WifiDisplaySessionInfo getSessionInfo(android.net.wifi.p2p.WifiP2pGroup wifiP2pGroup, int i) {
        if (wifiP2pGroup == null || wifiP2pGroup.getOwner() == null) {
            return null;
        }
        java.net.Inet4Address interfaceAddress = getInterfaceAddress(wifiP2pGroup);
        android.hardware.display.WifiDisplaySessionInfo wifiDisplaySessionInfo = new android.hardware.display.WifiDisplaySessionInfo(!wifiP2pGroup.getOwner().deviceAddress.equals(this.mThisDevice.deviceAddress), i, wifiP2pGroup.getOwner().deviceAddress + " " + wifiP2pGroup.getNetworkName(), wifiP2pGroup.getPassphrase(), interfaceAddress != null ? interfaceAddress.getHostAddress() : "");
        if (DEBUG) {
            android.util.Slog.d(TAG, wifiDisplaySessionInfo.toString());
        }
        return wifiDisplaySessionInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStateChanged(boolean z) {
        this.mWifiP2pEnabled = z;
        if (z) {
            retrieveWifiP2pManagerAndChannel();
        }
        updateWfdEnableState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePeersChanged() {
        requestPeers();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean contains(android.net.wifi.p2p.WifiP2pGroup wifiP2pGroup, android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice) {
        return wifiP2pGroup.getOwner().equals(wifiP2pDevice) || wifiP2pGroup.getClientList().contains(wifiP2pDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleConnectionChanged(android.net.NetworkInfo networkInfo) {
        this.mNetworkInfo = networkInfo;
        if (this.mWfdEnabled && networkInfo.isConnected()) {
            if (this.mDesiredDevice != null || this.mWifiDisplayCertMode) {
                this.mWifiP2pManager.requestGroupInfo(this.mWifiP2pChannel, new android.net.wifi.p2p.WifiP2pManager.GroupInfoListener() { // from class: com.android.server.display.WifiDisplayController.15
                    @Override // android.net.wifi.p2p.WifiP2pManager.GroupInfoListener
                    public void onGroupInfoAvailable(android.net.wifi.p2p.WifiP2pGroup wifiP2pGroup) {
                        if (wifiP2pGroup == null) {
                            return;
                        }
                        if (com.android.server.display.WifiDisplayController.DEBUG) {
                            android.util.Slog.d(com.android.server.display.WifiDisplayController.TAG, "Received group info: " + com.android.server.display.WifiDisplayController.describeWifiP2pGroup(wifiP2pGroup));
                        }
                        if (com.android.server.display.WifiDisplayController.this.mConnectingDevice != null && !com.android.server.display.WifiDisplayController.contains(wifiP2pGroup, com.android.server.display.WifiDisplayController.this.mConnectingDevice)) {
                            android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Aborting connection to Wifi display because the current P2P group does not contain the device we expected to find: " + com.android.server.display.WifiDisplayController.this.mConnectingDevice.deviceName + ", group info was: " + com.android.server.display.WifiDisplayController.describeWifiP2pGroup(wifiP2pGroup));
                            com.android.server.display.WifiDisplayController.this.handleConnectionFailure(false);
                            return;
                        }
                        if (com.android.server.display.WifiDisplayController.this.mDesiredDevice != null && !com.android.server.display.WifiDisplayController.contains(wifiP2pGroup, com.android.server.display.WifiDisplayController.this.mDesiredDevice)) {
                            com.android.server.display.WifiDisplayController.this.disconnect();
                            return;
                        }
                        if (com.android.server.display.WifiDisplayController.this.mWifiDisplayCertMode) {
                            boolean equals = wifiP2pGroup.getOwner() != null ? wifiP2pGroup.getOwner().deviceAddress.equals(com.android.server.display.WifiDisplayController.this.mThisDevice.deviceAddress) : false;
                            if (equals && wifiP2pGroup.getClientList().isEmpty()) {
                                com.android.server.display.WifiDisplayController wifiDisplayController = com.android.server.display.WifiDisplayController.this;
                                com.android.server.display.WifiDisplayController.this.mDesiredDevice = null;
                                wifiDisplayController.mConnectingDevice = null;
                                com.android.server.display.WifiDisplayController.this.mConnectedDeviceGroupInfo = wifiP2pGroup;
                                com.android.server.display.WifiDisplayController.this.updateConnection();
                            } else if (com.android.server.display.WifiDisplayController.this.mConnectingDevice == null && com.android.server.display.WifiDisplayController.this.mDesiredDevice == null) {
                                com.android.server.display.WifiDisplayController wifiDisplayController2 = com.android.server.display.WifiDisplayController.this;
                                com.android.server.display.WifiDisplayController wifiDisplayController3 = com.android.server.display.WifiDisplayController.this;
                                android.net.wifi.p2p.WifiP2pDevice next = equals ? wifiP2pGroup.getClientList().iterator().next() : wifiP2pGroup.getOwner();
                                wifiDisplayController3.mDesiredDevice = next;
                                wifiDisplayController2.mConnectingDevice = next;
                            }
                        }
                        if (com.android.server.display.WifiDisplayController.this.mConnectingDevice != null && com.android.server.display.WifiDisplayController.this.mConnectingDevice == com.android.server.display.WifiDisplayController.this.mDesiredDevice) {
                            android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Connected to Wifi display: " + com.android.server.display.WifiDisplayController.this.mConnectingDevice.deviceName);
                            com.android.server.display.WifiDisplayController.this.mHandler.removeCallbacks(com.android.server.display.WifiDisplayController.this.mConnectionTimeout);
                            com.android.server.display.WifiDisplayController.this.mConnectedDeviceGroupInfo = wifiP2pGroup;
                            com.android.server.display.WifiDisplayController.this.mConnectedDevice = com.android.server.display.WifiDisplayController.this.mConnectingDevice;
                            com.android.server.display.WifiDisplayController.this.mConnectingDevice = null;
                            com.android.server.display.WifiDisplayController.this.updateConnection();
                        }
                    }
                });
                return;
            }
            return;
        }
        if (!networkInfo.isConnectedOrConnecting()) {
            this.mConnectedDeviceGroupInfo = null;
            if (this.mConnectingDevice != null || this.mConnectedDevice != null) {
                disconnect();
            }
            if (this.mDesiredDevice != null) {
                android.util.Slog.i(TAG, "Reconnect new device: " + this.mDesiredDevice.deviceName);
                updateConnection();
                return;
            }
            if (this.mWfdEnabled) {
                requestPeers();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleConnectionFailure(boolean z) {
        android.util.Slog.i(TAG, "Wifi display connection failed!");
        if (this.mDesiredDevice != null) {
            if (this.mConnectionRetriesLeft > 0) {
                final android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice = this.mDesiredDevice;
                this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayController.19
                    @Override // java.lang.Runnable
                    public void run() {
                        if (com.android.server.display.WifiDisplayController.this.mDesiredDevice == wifiP2pDevice && com.android.server.display.WifiDisplayController.this.mConnectionRetriesLeft > 0) {
                            com.android.server.display.WifiDisplayController wifiDisplayController = com.android.server.display.WifiDisplayController.this;
                            wifiDisplayController.mConnectionRetriesLeft--;
                            android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Retrying Wifi display connection.  Retries left: " + com.android.server.display.WifiDisplayController.this.mConnectionRetriesLeft);
                            com.android.server.display.WifiDisplayController.this.retryConnection();
                        }
                    }
                }, z ? 0L : 500L);
            } else {
                disconnect();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void advertiseDisplay(final android.hardware.display.WifiDisplay wifiDisplay, final android.view.Surface surface, final int i, final int i2, final int i3) {
        if (!java.util.Objects.equals(this.mAdvertisedDisplay, wifiDisplay) || this.mAdvertisedDisplaySurface != surface || this.mAdvertisedDisplayWidth != i || this.mAdvertisedDisplayHeight != i2 || this.mAdvertisedDisplayFlags != i3) {
            final android.hardware.display.WifiDisplay wifiDisplay2 = this.mAdvertisedDisplay;
            final android.view.Surface surface2 = this.mAdvertisedDisplaySurface;
            this.mAdvertisedDisplay = wifiDisplay;
            this.mAdvertisedDisplaySurface = surface;
            this.mAdvertisedDisplayWidth = i;
            this.mAdvertisedDisplayHeight = i2;
            this.mAdvertisedDisplayFlags = i3;
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayController.20
                @Override // java.lang.Runnable
                public void run() {
                    if (surface2 != null && surface != surface2) {
                        com.android.server.display.WifiDisplayController.this.mListener.onDisplayDisconnected();
                    } else if (wifiDisplay2 != null && !wifiDisplay2.hasSameAddress(wifiDisplay)) {
                        com.android.server.display.WifiDisplayController.this.mListener.onDisplayConnectionFailed();
                    }
                    if (wifiDisplay != null) {
                        if (!wifiDisplay.hasSameAddress(wifiDisplay2)) {
                            com.android.server.display.WifiDisplayController.this.mListener.onDisplayConnecting(wifiDisplay);
                        } else if (!wifiDisplay.equals(wifiDisplay2)) {
                            com.android.server.display.WifiDisplayController.this.mListener.onDisplayChanged(wifiDisplay);
                        }
                        if (surface != null && surface != surface2) {
                            com.android.server.display.WifiDisplayController.this.mListener.onDisplayConnected(wifiDisplay, surface, i, i2, i3);
                        }
                    }
                }
            });
        }
    }

    private void unadvertiseDisplay() {
        advertiseDisplay(null, null, 0, 0, 0);
    }

    private void readvertiseDisplay(android.hardware.display.WifiDisplay wifiDisplay) {
        advertiseDisplay(wifiDisplay, this.mAdvertisedDisplaySurface, this.mAdvertisedDisplayWidth, this.mAdvertisedDisplayHeight, this.mAdvertisedDisplayFlags);
    }

    private boolean handlePreExistingConnection(final android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice) {
        if (this.mNetworkInfo == null || !this.mNetworkInfo.isConnected() || this.mWifiDisplayCertMode) {
            return false;
        }
        if (DEBUG) {
            android.util.Slog.i(TAG, "Handle the preexisting P2P connection status");
        }
        this.mWifiP2pManager.requestGroupInfo(this.mWifiP2pChannel, new android.net.wifi.p2p.WifiP2pManager.GroupInfoListener() { // from class: com.android.server.display.WifiDisplayController.21
            @Override // android.net.wifi.p2p.WifiP2pManager.GroupInfoListener
            public void onGroupInfoAvailable(android.net.wifi.p2p.WifiP2pGroup wifiP2pGroup) {
                if (wifiP2pGroup == null) {
                    return;
                }
                if (com.android.server.display.WifiDisplayController.contains(wifiP2pGroup, wifiP2pDevice)) {
                    if (com.android.server.display.WifiDisplayController.DEBUG) {
                        android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Already connected to the desired device: " + wifiP2pDevice.deviceName);
                    }
                    com.android.server.display.WifiDisplayController.this.updateConnection();
                    com.android.server.display.WifiDisplayController.this.handleConnectionChanged(com.android.server.display.WifiDisplayController.this.mNetworkInfo);
                    return;
                }
                com.android.server.display.WifiDisplayController.this.mWifiP2pManager.removeGroup(com.android.server.display.WifiDisplayController.this.mWifiP2pChannel, new android.net.wifi.p2p.WifiP2pManager.ActionListener() { // from class: com.android.server.display.WifiDisplayController.21.1
                    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                    public void onSuccess() {
                        android.util.Slog.i(com.android.server.display.WifiDisplayController.TAG, "Disconnect the old device");
                    }

                    @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
                    public void onFailure(int i) {
                        android.util.Slog.w(com.android.server.display.WifiDisplayController.TAG, "Failed to disconnect the old device: reason=" + i);
                    }
                });
            }
        });
        this.mDesiredDevice = wifiP2pDevice;
        this.mConnectionRetriesLeft = 3;
        return true;
    }

    private static java.net.Inet4Address getInterfaceAddress(android.net.wifi.p2p.WifiP2pGroup wifiP2pGroup) {
        try {
            java.util.Enumeration<java.net.InetAddress> inetAddresses = java.net.NetworkInterface.getByName(wifiP2pGroup.getInterface()).getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                java.net.InetAddress nextElement = inetAddresses.nextElement();
                if (nextElement instanceof java.net.Inet4Address) {
                    return (java.net.Inet4Address) nextElement;
                }
            }
            android.util.Slog.w(TAG, "Could not obtain address of network interface " + wifiP2pGroup.getInterface() + " because it had no IPv4 addresses.");
            return null;
        } catch (java.net.SocketException e) {
            android.util.Slog.w(TAG, "Could not obtain address of network interface " + wifiP2pGroup.getInterface(), e);
            return null;
        }
    }

    private static int getPortNumber(android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice) {
        if (wifiP2pDevice.deviceName.startsWith("DIRECT-") && wifiP2pDevice.deviceName.endsWith("Broadcom")) {
            return 8554;
        }
        return DEFAULT_CONTROL_PORT;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isWifiDisplay(android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice) {
        android.net.wifi.p2p.WifiP2pWfdInfo wfdInfo = wifiP2pDevice.getWfdInfo();
        return wfdInfo != null && wfdInfo.isEnabled() && isPrimarySinkDeviceType(wfdInfo.getDeviceType());
    }

    private static boolean isPrimarySinkDeviceType(int i) {
        return i == 1 || i == 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String describeWifiP2pDevice(android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice) {
        return wifiP2pDevice != null ? wifiP2pDevice.toString().replace('\n', ',') : "null";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String describeWifiP2pGroup(android.net.wifi.p2p.WifiP2pGroup wifiP2pGroup) {
        return wifiP2pGroup != null ? wifiP2pGroup.toString().replace('\n', ',') : "null";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.hardware.display.WifiDisplay createWifiDisplay(android.net.wifi.p2p.WifiP2pDevice wifiP2pDevice) {
        android.net.wifi.p2p.WifiP2pWfdInfo wfdInfo = wifiP2pDevice.getWfdInfo();
        return new android.hardware.display.WifiDisplay(wifiP2pDevice.deviceAddress, wifiP2pDevice.deviceName, (java.lang.String) null, true, wfdInfo != null && wfdInfo.isSessionAvailable(), false);
    }
}
