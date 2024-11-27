package com.android.server.display;

/* loaded from: classes.dex */
final class WifiDisplayAdapter extends com.android.server.display.DisplayAdapter {
    private static final java.lang.String ACTION_DISCONNECT = "android.server.display.wfd.DISCONNECT";
    private static final java.lang.String DISPLAY_NAME_PREFIX = "wifi:";
    private static final int MSG_SEND_STATUS_CHANGE_BROADCAST = 1;
    private android.hardware.display.WifiDisplay mActiveDisplay;
    private int mActiveDisplayState;
    private android.hardware.display.WifiDisplay[] mAvailableDisplays;
    private final android.content.BroadcastReceiver mBroadcastReceiver;
    private android.hardware.display.WifiDisplayStatus mCurrentStatus;
    private com.android.server.display.WifiDisplayController mDisplayController;
    private com.android.server.display.WifiDisplayAdapter.WifiDisplayDevice mDisplayDevice;
    private android.hardware.display.WifiDisplay[] mDisplays;
    private int mFeatureState;
    private final com.android.server.display.WifiDisplayAdapter.WifiDisplayHandler mHandler;
    private boolean mPendingStatusChangeBroadcast;
    private final com.android.server.display.PersistentDataStore mPersistentDataStore;
    private android.hardware.display.WifiDisplay[] mRememberedDisplays;
    private int mScanState;
    private android.hardware.display.WifiDisplaySessionInfo mSessionInfo;
    private final boolean mSupportsProtectedBuffers;
    private final com.android.server.display.WifiDisplayController.Listener mWifiDisplayListener;
    private static final java.lang.String TAG = "WifiDisplayAdapter";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);

    public WifiDisplayAdapter(com.android.server.display.DisplayManagerService.SyncRoot syncRoot, android.content.Context context, android.os.Handler handler, com.android.server.display.DisplayAdapter.Listener listener, com.android.server.display.PersistentDataStore persistentDataStore, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        super(syncRoot, context, handler, listener, TAG, displayManagerFlags);
        this.mDisplays = android.hardware.display.WifiDisplay.EMPTY_ARRAY;
        this.mAvailableDisplays = android.hardware.display.WifiDisplay.EMPTY_ARRAY;
        this.mRememberedDisplays = android.hardware.display.WifiDisplay.EMPTY_ARRAY;
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.display.WifiDisplayAdapter.8
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (intent.getAction().equals(com.android.server.display.WifiDisplayAdapter.ACTION_DISCONNECT)) {
                    synchronized (com.android.server.display.WifiDisplayAdapter.this.getSyncRoot()) {
                        com.android.server.display.WifiDisplayAdapter.this.requestDisconnectLocked();
                    }
                }
            }
        };
        this.mWifiDisplayListener = new com.android.server.display.WifiDisplayController.Listener() { // from class: com.android.server.display.WifiDisplayAdapter.9
            @Override // com.android.server.display.WifiDisplayController.Listener
            public void onFeatureStateChanged(int i) {
                synchronized (com.android.server.display.WifiDisplayAdapter.this.getSyncRoot()) {
                    try {
                        if (com.android.server.display.WifiDisplayAdapter.this.mFeatureState != i) {
                            com.android.server.display.WifiDisplayAdapter.this.mFeatureState = i;
                            com.android.server.display.WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.display.WifiDisplayController.Listener
            public void onScanStarted() {
                synchronized (com.android.server.display.WifiDisplayAdapter.this.getSyncRoot()) {
                    try {
                        if (com.android.server.display.WifiDisplayAdapter.this.mScanState != 1) {
                            com.android.server.display.WifiDisplayAdapter.this.mScanState = 1;
                            com.android.server.display.WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.display.WifiDisplayController.Listener
            public void onScanResults(android.hardware.display.WifiDisplay[] wifiDisplayArr) {
                synchronized (com.android.server.display.WifiDisplayAdapter.this.getSyncRoot()) {
                    try {
                        android.hardware.display.WifiDisplay[] applyWifiDisplayAliases = com.android.server.display.WifiDisplayAdapter.this.mPersistentDataStore.applyWifiDisplayAliases(wifiDisplayArr);
                        boolean z = !java.util.Arrays.equals(com.android.server.display.WifiDisplayAdapter.this.mAvailableDisplays, applyWifiDisplayAliases);
                        for (int i = 0; !z && i < applyWifiDisplayAliases.length; i++) {
                            z = applyWifiDisplayAliases[i].canConnect() != com.android.server.display.WifiDisplayAdapter.this.mAvailableDisplays[i].canConnect();
                        }
                        if (z) {
                            com.android.server.display.WifiDisplayAdapter.this.mAvailableDisplays = applyWifiDisplayAliases;
                            com.android.server.display.WifiDisplayAdapter.this.fixRememberedDisplayNamesFromAvailableDisplaysLocked();
                            com.android.server.display.WifiDisplayAdapter.this.updateDisplaysLocked();
                            com.android.server.display.WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.display.WifiDisplayController.Listener
            public void onScanFinished() {
                synchronized (com.android.server.display.WifiDisplayAdapter.this.getSyncRoot()) {
                    try {
                        if (com.android.server.display.WifiDisplayAdapter.this.mScanState != 0) {
                            com.android.server.display.WifiDisplayAdapter.this.mScanState = 0;
                            com.android.server.display.WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.display.WifiDisplayController.Listener
            public void onDisplayConnecting(android.hardware.display.WifiDisplay wifiDisplay) {
                synchronized (com.android.server.display.WifiDisplayAdapter.this.getSyncRoot()) {
                    try {
                        android.hardware.display.WifiDisplay applyWifiDisplayAlias = com.android.server.display.WifiDisplayAdapter.this.mPersistentDataStore.applyWifiDisplayAlias(wifiDisplay);
                        if (com.android.server.display.WifiDisplayAdapter.this.mActiveDisplayState == 1) {
                            if (com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay != null) {
                                if (!com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay.equals(applyWifiDisplayAlias)) {
                                }
                            }
                        }
                        com.android.server.display.WifiDisplayAdapter.this.mActiveDisplayState = 1;
                        com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay = applyWifiDisplayAlias;
                        com.android.server.display.WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.display.WifiDisplayController.Listener
            public void onDisplayConnectionFailed() {
                synchronized (com.android.server.display.WifiDisplayAdapter.this.getSyncRoot()) {
                    try {
                        if (com.android.server.display.WifiDisplayAdapter.this.mActiveDisplayState != 0 || com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay != null) {
                            com.android.server.display.WifiDisplayAdapter.this.mActiveDisplayState = 0;
                            com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay = null;
                            com.android.server.display.WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.display.WifiDisplayController.Listener
            public void onDisplayConnected(android.hardware.display.WifiDisplay wifiDisplay, android.view.Surface surface, int i, int i2, int i3) {
                synchronized (com.android.server.display.WifiDisplayAdapter.this.getSyncRoot()) {
                    try {
                        android.hardware.display.WifiDisplay applyWifiDisplayAlias = com.android.server.display.WifiDisplayAdapter.this.mPersistentDataStore.applyWifiDisplayAlias(wifiDisplay);
                        com.android.server.display.WifiDisplayAdapter.this.addDisplayDeviceLocked(applyWifiDisplayAlias, surface, i, i2, i3);
                        if (com.android.server.display.WifiDisplayAdapter.this.mActiveDisplayState == 2) {
                            if (com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay != null) {
                                if (!com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay.equals(applyWifiDisplayAlias)) {
                                }
                            }
                        }
                        com.android.server.display.WifiDisplayAdapter.this.mActiveDisplayState = 2;
                        com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay = applyWifiDisplayAlias;
                        com.android.server.display.WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.display.WifiDisplayController.Listener
            public void onDisplaySessionInfo(android.hardware.display.WifiDisplaySessionInfo wifiDisplaySessionInfo) {
                synchronized (com.android.server.display.WifiDisplayAdapter.this.getSyncRoot()) {
                    com.android.server.display.WifiDisplayAdapter.this.mSessionInfo = wifiDisplaySessionInfo;
                    com.android.server.display.WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
                }
            }

            @Override // com.android.server.display.WifiDisplayController.Listener
            public void onDisplayChanged(android.hardware.display.WifiDisplay wifiDisplay) {
                synchronized (com.android.server.display.WifiDisplayAdapter.this.getSyncRoot()) {
                    try {
                        android.hardware.display.WifiDisplay applyWifiDisplayAlias = com.android.server.display.WifiDisplayAdapter.this.mPersistentDataStore.applyWifiDisplayAlias(wifiDisplay);
                        if (com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay != null && com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay.hasSameAddress(applyWifiDisplayAlias) && !com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay.equals(applyWifiDisplayAlias)) {
                            com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay = applyWifiDisplayAlias;
                            com.android.server.display.WifiDisplayAdapter.this.renameDisplayDeviceLocked(applyWifiDisplayAlias.getFriendlyDisplayName());
                            com.android.server.display.WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.display.WifiDisplayController.Listener
            public void onDisplayDisconnected() {
                synchronized (com.android.server.display.WifiDisplayAdapter.this.getSyncRoot()) {
                    try {
                        com.android.server.display.WifiDisplayAdapter.this.removeDisplayDeviceLocked();
                        if (com.android.server.display.WifiDisplayAdapter.this.mActiveDisplayState != 0 || com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay != null) {
                            com.android.server.display.WifiDisplayAdapter.this.mActiveDisplayState = 0;
                            com.android.server.display.WifiDisplayAdapter.this.mActiveDisplay = null;
                            com.android.server.display.WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        if (!context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
            throw new java.lang.RuntimeException("WiFi display was requested, but there is no WiFi Direct feature");
        }
        this.mHandler = new com.android.server.display.WifiDisplayAdapter.WifiDisplayHandler(handler.getLooper());
        this.mPersistentDataStore = persistentDataStore;
        this.mSupportsProtectedBuffers = context.getResources().getBoolean(android.R.bool.config_wakeOnBackKeyPress);
    }

    @Override // com.android.server.display.DisplayAdapter
    public void dumpLocked(java.io.PrintWriter printWriter) {
        super.dumpLocked(printWriter);
        printWriter.println("mCurrentStatus=" + getWifiDisplayStatusLocked());
        printWriter.println("mFeatureState=" + this.mFeatureState);
        printWriter.println("mScanState=" + this.mScanState);
        printWriter.println("mActiveDisplayState=" + this.mActiveDisplayState);
        printWriter.println("mActiveDisplay=" + this.mActiveDisplay);
        printWriter.println("mDisplays=" + java.util.Arrays.toString(this.mDisplays));
        printWriter.println("mAvailableDisplays=" + java.util.Arrays.toString(this.mAvailableDisplays));
        printWriter.println("mRememberedDisplays=" + java.util.Arrays.toString(this.mRememberedDisplays));
        printWriter.println("mPendingStatusChangeBroadcast=" + this.mPendingStatusChangeBroadcast);
        printWriter.println("mSupportsProtectedBuffers=" + this.mSupportsProtectedBuffers);
        if (this.mDisplayController == null) {
            printWriter.println("mDisplayController=null");
            return;
        }
        printWriter.println("mDisplayController:");
        com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.increaseIndent();
        com.android.internal.util.DumpUtils.dumpAsync(getHandler(), this.mDisplayController, indentingPrintWriter, "", 200L);
    }

    @Override // com.android.server.display.DisplayAdapter
    public void registerLocked() {
        super.registerLocked();
        updateRememberedDisplaysLocked();
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.1
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.display.WifiDisplayAdapter.this.mDisplayController = new com.android.server.display.WifiDisplayController(com.android.server.display.WifiDisplayAdapter.this.getContext(), com.android.server.display.WifiDisplayAdapter.this.getHandler(), com.android.server.display.WifiDisplayAdapter.this.mWifiDisplayListener);
                com.android.server.display.WifiDisplayAdapter.this.getContext().registerReceiverAsUser(com.android.server.display.WifiDisplayAdapter.this.mBroadcastReceiver, android.os.UserHandle.ALL, new android.content.IntentFilter(com.android.server.display.WifiDisplayAdapter.ACTION_DISCONNECT), null, com.android.server.display.WifiDisplayAdapter.this.mHandler, 4);
            }
        });
    }

    public void requestStartScanLocked() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestStartScanLocked");
        }
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.2
            @Override // java.lang.Runnable
            public void run() {
                if (com.android.server.display.WifiDisplayAdapter.this.mDisplayController != null) {
                    com.android.server.display.WifiDisplayAdapter.this.mDisplayController.requestStartScan();
                }
            }
        });
    }

    public void requestStopScanLocked() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestStopScanLocked");
        }
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.3
            @Override // java.lang.Runnable
            public void run() {
                if (com.android.server.display.WifiDisplayAdapter.this.mDisplayController != null) {
                    com.android.server.display.WifiDisplayAdapter.this.mDisplayController.requestStopScan();
                }
            }
        });
    }

    public void requestConnectLocked(final java.lang.String str) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestConnectLocked: address=" + str);
        }
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.4
            @Override // java.lang.Runnable
            public void run() {
                if (com.android.server.display.WifiDisplayAdapter.this.mDisplayController != null) {
                    com.android.server.display.WifiDisplayAdapter.this.mDisplayController.requestConnect(str);
                }
            }
        });
    }

    public void requestPauseLocked() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestPauseLocked");
        }
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.5
            @Override // java.lang.Runnable
            public void run() {
                if (com.android.server.display.WifiDisplayAdapter.this.mDisplayController != null) {
                    com.android.server.display.WifiDisplayAdapter.this.mDisplayController.requestPause();
                }
            }
        });
    }

    public void requestResumeLocked() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestResumeLocked");
        }
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.6
            @Override // java.lang.Runnable
            public void run() {
                if (com.android.server.display.WifiDisplayAdapter.this.mDisplayController != null) {
                    com.android.server.display.WifiDisplayAdapter.this.mDisplayController.requestResume();
                }
            }
        });
    }

    public void requestDisconnectLocked() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestDisconnectedLocked");
        }
        getHandler().post(new java.lang.Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.7
            @Override // java.lang.Runnable
            public void run() {
                if (com.android.server.display.WifiDisplayAdapter.this.mDisplayController != null) {
                    com.android.server.display.WifiDisplayAdapter.this.mDisplayController.requestDisconnect();
                }
            }
        });
    }

    public void requestRenameLocked(java.lang.String str, java.lang.String str2) {
        java.lang.String str3;
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestRenameLocked: address=" + str + ", alias=" + str2);
        }
        if (str2 == null) {
            str3 = str2;
        } else {
            java.lang.String trim = str2.trim();
            str3 = (trim.isEmpty() || trim.equals(str)) ? null : trim;
        }
        android.hardware.display.WifiDisplay rememberedWifiDisplay = this.mPersistentDataStore.getRememberedWifiDisplay(str);
        if (rememberedWifiDisplay != null && !java.util.Objects.equals(rememberedWifiDisplay.getDeviceAlias(), str3)) {
            if (this.mPersistentDataStore.rememberWifiDisplay(new android.hardware.display.WifiDisplay(str, rememberedWifiDisplay.getDeviceName(), str3, false, false, false))) {
                this.mPersistentDataStore.saveIfNeeded();
                updateRememberedDisplaysLocked();
                scheduleStatusChangedBroadcastLocked();
            }
        }
        if (this.mActiveDisplay != null && this.mActiveDisplay.getDeviceAddress().equals(str)) {
            renameDisplayDeviceLocked(this.mActiveDisplay.getFriendlyDisplayName());
        }
    }

    public void requestForgetLocked(java.lang.String str) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestForgetLocked: address=" + str);
        }
        if (this.mPersistentDataStore.forgetWifiDisplay(str)) {
            this.mPersistentDataStore.saveIfNeeded();
            updateRememberedDisplaysLocked();
            scheduleStatusChangedBroadcastLocked();
        }
        if (this.mActiveDisplay != null && this.mActiveDisplay.getDeviceAddress().equals(str)) {
            requestDisconnectLocked();
        }
    }

    public android.hardware.display.WifiDisplayStatus getWifiDisplayStatusLocked() {
        if (this.mCurrentStatus == null) {
            this.mCurrentStatus = new android.hardware.display.WifiDisplayStatus(this.mFeatureState, this.mScanState, this.mActiveDisplayState, this.mActiveDisplay, this.mDisplays, this.mSessionInfo);
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "getWifiDisplayStatusLocked: result=" + this.mCurrentStatus);
        }
        return this.mCurrentStatus;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDisplaysLocked() {
        boolean z;
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mAvailableDisplays.length + this.mRememberedDisplays.length);
        boolean[] zArr = new boolean[this.mAvailableDisplays.length];
        for (android.hardware.display.WifiDisplay wifiDisplay : this.mRememberedDisplays) {
            int i = 0;
            while (true) {
                if (i < this.mAvailableDisplays.length) {
                    if (!wifiDisplay.equals(this.mAvailableDisplays[i])) {
                        i++;
                    } else {
                        z = true;
                        zArr[i] = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (!z) {
                arrayList.add(new android.hardware.display.WifiDisplay(wifiDisplay.getDeviceAddress(), wifiDisplay.getDeviceName(), wifiDisplay.getDeviceAlias(), false, false, true));
            }
        }
        for (int i2 = 0; i2 < this.mAvailableDisplays.length; i2++) {
            android.hardware.display.WifiDisplay wifiDisplay2 = this.mAvailableDisplays[i2];
            arrayList.add(new android.hardware.display.WifiDisplay(wifiDisplay2.getDeviceAddress(), wifiDisplay2.getDeviceName(), wifiDisplay2.getDeviceAlias(), true, wifiDisplay2.canConnect(), zArr[i2]));
        }
        this.mDisplays = (android.hardware.display.WifiDisplay[]) arrayList.toArray(android.hardware.display.WifiDisplay.EMPTY_ARRAY);
    }

    private void updateRememberedDisplaysLocked() {
        this.mRememberedDisplays = this.mPersistentDataStore.getRememberedWifiDisplays();
        this.mActiveDisplay = this.mPersistentDataStore.applyWifiDisplayAlias(this.mActiveDisplay);
        this.mAvailableDisplays = this.mPersistentDataStore.applyWifiDisplayAliases(this.mAvailableDisplays);
        updateDisplaysLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fixRememberedDisplayNamesFromAvailableDisplaysLocked() {
        boolean z = false;
        for (int i = 0; i < this.mRememberedDisplays.length; i++) {
            android.hardware.display.WifiDisplay wifiDisplay = this.mRememberedDisplays[i];
            android.hardware.display.WifiDisplay findAvailableDisplayLocked = findAvailableDisplayLocked(wifiDisplay.getDeviceAddress());
            if (findAvailableDisplayLocked != null && !wifiDisplay.equals(findAvailableDisplayLocked)) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "fixRememberedDisplayNamesFromAvailableDisplaysLocked: updating remembered display to " + findAvailableDisplayLocked);
                }
                this.mRememberedDisplays[i] = findAvailableDisplayLocked;
                z |= this.mPersistentDataStore.rememberWifiDisplay(findAvailableDisplayLocked);
            }
        }
        if (z) {
            this.mPersistentDataStore.saveIfNeeded();
        }
    }

    private android.hardware.display.WifiDisplay findAvailableDisplayLocked(java.lang.String str) {
        for (android.hardware.display.WifiDisplay wifiDisplay : this.mAvailableDisplays) {
            if (wifiDisplay.getDeviceAddress().equals(str)) {
                return wifiDisplay;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDisplayDeviceLocked(android.hardware.display.WifiDisplay wifiDisplay, android.view.Surface surface, int i, int i2, int i3) {
        int i4;
        removeDisplayDeviceLocked();
        if (this.mPersistentDataStore.rememberWifiDisplay(wifiDisplay)) {
            this.mPersistentDataStore.saveIfNeeded();
            updateRememberedDisplaysLocked();
            scheduleStatusChangedBroadcastLocked();
        }
        boolean z = (i3 & 1) != 0;
        if (!z) {
            i4 = 64;
        } else if (!this.mSupportsProtectedBuffers) {
            i4 = 68;
        } else {
            i4 = 76;
        }
        java.lang.String friendlyDisplayName = wifiDisplay.getFriendlyDisplayName();
        this.mDisplayDevice = new com.android.server.display.WifiDisplayAdapter.WifiDisplayDevice(com.android.server.display.DisplayControl.createDisplay(friendlyDisplayName, z), friendlyDisplayName, i, i2, 60.0f, i4, wifiDisplay.getDeviceAddress(), surface);
        sendDisplayDeviceEventLocked(this.mDisplayDevice, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeDisplayDeviceLocked() {
        if (this.mDisplayDevice != null) {
            this.mDisplayDevice.destroyLocked();
            sendDisplayDeviceEventLocked(this.mDisplayDevice, 3);
            this.mDisplayDevice = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void renameDisplayDeviceLocked(java.lang.String str) {
        if (this.mDisplayDevice != null && !this.mDisplayDevice.getNameLocked().equals(str)) {
            this.mDisplayDevice.setNameLocked(str);
            sendDisplayDeviceEventLocked(this.mDisplayDevice, 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleStatusChangedBroadcastLocked() {
        this.mCurrentStatus = null;
        if (!this.mPendingStatusChangeBroadcast) {
            this.mPendingStatusChangeBroadcast = true;
            this.mHandler.sendEmptyMessage(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSendStatusChangeBroadcast() {
        synchronized (getSyncRoot()) {
            try {
                if (this.mPendingStatusChangeBroadcast) {
                    this.mPendingStatusChangeBroadcast = false;
                    android.content.Intent intent = new android.content.Intent("android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED");
                    intent.addFlags(1073741824);
                    intent.putExtra("android.hardware.display.extra.WIFI_DISPLAY_STATUS", (android.os.Parcelable) getWifiDisplayStatusLocked());
                    android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
                    makeBasic.setDeliveryGroupPolicy(1);
                    getContext().sendBroadcastAsUser(intent, android.os.UserHandle.ALL, null, makeBasic.toBundle());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private final class WifiDisplayDevice extends com.android.server.display.DisplayDevice {
        private final android.view.DisplayAddress mAddress;
        private final int mFlags;
        private final int mHeight;
        private com.android.server.display.DisplayDeviceInfo mInfo;
        private final android.view.Display.Mode mMode;
        private java.lang.String mName;
        private final float mRefreshRate;
        private android.view.Surface mSurface;
        private final int mWidth;

        public WifiDisplayDevice(android.os.IBinder iBinder, java.lang.String str, int i, int i2, float f, int i3, java.lang.String str2, android.view.Surface surface) {
            super(com.android.server.display.WifiDisplayAdapter.this, iBinder, com.android.server.display.WifiDisplayAdapter.DISPLAY_NAME_PREFIX + str2, com.android.server.display.WifiDisplayAdapter.this.getContext());
            this.mName = str;
            this.mWidth = i;
            this.mHeight = i2;
            this.mRefreshRate = f;
            this.mFlags = i3;
            this.mAddress = android.view.DisplayAddress.fromMacAddress(str2);
            this.mSurface = surface;
            this.mMode = com.android.server.display.DisplayAdapter.createMode(i, i2, f);
        }

        @Override // com.android.server.display.DisplayDevice
        public boolean hasStableUniqueId() {
            return true;
        }

        public void destroyLocked() {
            if (this.mSurface != null) {
                this.mSurface.release();
                this.mSurface = null;
            }
            com.android.server.display.DisplayControl.destroyDisplay(getDisplayTokenLocked());
        }

        public void setNameLocked(java.lang.String str) {
            this.mName = str;
            this.mInfo = null;
        }

        @Override // com.android.server.display.DisplayDevice
        public void performTraversalLocked(android.view.SurfaceControl.Transaction transaction) {
            if (this.mSurface != null) {
                setSurfaceLocked(transaction, this.mSurface);
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public com.android.server.display.DisplayDeviceInfo getDisplayDeviceInfoLocked() {
            if (this.mInfo == null) {
                this.mInfo = new com.android.server.display.DisplayDeviceInfo();
                this.mInfo.name = this.mName;
                this.mInfo.uniqueId = getUniqueId();
                this.mInfo.width = this.mWidth;
                this.mInfo.height = this.mHeight;
                this.mInfo.modeId = this.mMode.getModeId();
                this.mInfo.renderFrameRate = this.mMode.getRefreshRate();
                this.mInfo.defaultModeId = this.mMode.getModeId();
                this.mInfo.supportedModes = new android.view.Display.Mode[]{this.mMode};
                this.mInfo.presentationDeadlineNanos = 1000000000 / ((int) this.mRefreshRate);
                this.mInfo.flags = this.mFlags;
                this.mInfo.type = 3;
                this.mInfo.address = this.mAddress;
                this.mInfo.touch = 2;
                this.mInfo.setAssumedDensityForExternalDisplay(this.mWidth, this.mHeight);
                this.mInfo.flags |= 8192;
                this.mInfo.displayShape = android.view.DisplayShape.createDefaultDisplayShape(this.mInfo.width, this.mInfo.height, false);
            }
            return this.mInfo;
        }
    }

    private final class WifiDisplayHandler extends android.os.Handler {
        public WifiDisplayHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.display.WifiDisplayAdapter.this.handleSendStatusChangeBroadcast();
                    break;
            }
        }
    }
}
