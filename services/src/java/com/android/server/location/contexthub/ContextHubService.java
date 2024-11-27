package com.android.server.location.contexthub;

/* loaded from: classes2.dex */
public class ContextHubService extends android.hardware.location.IContextHubService.Stub {
    public static final int CONTEXT_HUB_EVENT_RESTARTED = 1;
    public static final int CONTEXT_HUB_EVENT_UNKNOWN = 0;
    private static final boolean DEBUG_LOG_ENABLED = false;
    public static final int MSG_DISABLE_NANO_APP = 2;
    public static final int MSG_ENABLE_NANO_APP = 1;
    public static final int MSG_HUB_RESET = 7;
    public static final int MSG_LOAD_NANO_APP = 3;
    public static final int MSG_QUERY_MEMORY = 6;
    public static final int MSG_QUERY_NANO_APPS = 5;
    public static final int MSG_UNLOAD_NANO_APP = 4;
    private static final int OS_APP_INSTANCE = -1;
    private static final int PERIOD_METRIC_QUERY_DAYS = 1;
    private static final java.lang.String TAG = "ContextHubService";
    private com.android.server.location.contexthub.ContextHubClientManager mClientManager;
    private final android.content.Context mContext;
    private java.util.Map<java.lang.Integer, android.hardware.location.ContextHubInfo> mContextHubIdToInfoMap;
    private java.util.List<android.hardware.location.ContextHubInfo> mContextHubInfoList;
    private final com.android.server.location.contexthub.IContextHubWrapper mContextHubWrapper;
    private java.util.Map<java.lang.Integer, android.hardware.location.IContextHubClient> mDefaultClientMap;
    private android.hardware.SensorPrivacyManagerInternal mSensorPrivacyManagerInternal;
    private java.util.List<java.lang.String> mSupportedContextHubPerms;
    private com.android.server.location.contexthub.ContextHubTransactionManager mTransactionManager;
    private final android.os.RemoteCallbackList<android.hardware.location.IContextHubCallback> mCallbacksList = new android.os.RemoteCallbackList<>();
    private final com.android.server.location.contexthub.NanoAppStateManager mNanoAppStateManager = new com.android.server.location.contexthub.NanoAppStateManager();
    private final java.util.concurrent.ScheduledThreadPoolExecutor mDailyMetricTimer = new java.util.concurrent.ScheduledThreadPoolExecutor(1);
    private boolean mIsWifiAvailable = false;
    private boolean mIsWifiScanningEnabled = false;
    private boolean mIsWifiMainEnabled = false;
    private boolean mIsBtScanningEnabled = false;
    private boolean mIsBtMainEnabled = false;
    private java.util.Set<java.lang.Integer> mMetricQueryPendingContextHubIds = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap());
    private final java.lang.Object mSendWifiSettingUpdateLock = new java.lang.Object();
    private android.os.UserManager mUserManager = null;
    private final java.util.Map<java.lang.Integer, java.util.concurrent.atomic.AtomicLong> mLastRestartTimestampMap = new java.util.HashMap();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private class ContextHubServiceCallback implements com.android.server.location.contexthub.IContextHubWrapper.ICallback {
        private final int mContextHubId;

        ContextHubServiceCallback(int i) {
            this.mContextHubId = i;
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper.ICallback
        public void handleTransactionResult(int i, boolean z) {
            com.android.server.location.contexthub.ContextHubService.this.handleTransactionResultCallback(this.mContextHubId, i, z);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper.ICallback
        public void handleContextHubEvent(int i) {
            com.android.server.location.contexthub.ContextHubService.this.handleHubEventCallback(this.mContextHubId, i);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper.ICallback
        public void handleNanoappAbort(long j, int i) {
            com.android.server.location.contexthub.ContextHubService.this.handleAppAbortCallback(this.mContextHubId, j, i);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper.ICallback
        public void handleNanoappInfo(java.util.List<android.hardware.location.NanoAppState> list) {
            com.android.server.location.contexthub.ContextHubService.this.handleQueryAppsCallback(this.mContextHubId, list);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper.ICallback
        public void handleNanoappMessage(short s, android.hardware.location.NanoAppMessage nanoAppMessage, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2) {
            com.android.server.location.contexthub.ContextHubService.this.handleClientMessageCallback(this.mContextHubId, s, nanoAppMessage, list, list2);
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper.ICallback
        public void handleServiceRestart() {
            android.util.Log.i(com.android.server.location.contexthub.ContextHubService.TAG, "Starting Context Hub Service restart");
            com.android.server.location.contexthub.ContextHubService.this.initExistingCallbacks();
            com.android.server.location.contexthub.ContextHubService.this.resetSettings();
            android.util.Log.i(com.android.server.location.contexthub.ContextHubService.TAG, "Finished Context Hub Service restart");
        }

        @Override // com.android.server.location.contexthub.IContextHubWrapper.ICallback
        public void handleMessageDeliveryStatus(android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus) {
            com.android.server.location.contexthub.ContextHubService.this.handleMessageDeliveryStatusCallback(messageDeliveryStatus);
        }
    }

    public ContextHubService(android.content.Context context, com.android.server.location.contexthub.IContextHubWrapper iContextHubWrapper) {
        android.util.Log.i(TAG, "Starting Context Hub Service init");
        this.mContext = context;
        long elapsedRealtimeNanos = android.os.SystemClock.elapsedRealtimeNanos();
        this.mContextHubWrapper = iContextHubWrapper;
        if (!initContextHubServiceState(elapsedRealtimeNanos)) {
            android.util.Log.e(TAG, "Failed to initialize the Context Hub Service");
            return;
        }
        initDefaultClientMap();
        initLocationSettingNotifications();
        initWifiSettingNotifications();
        initAirplaneModeSettingNotifications();
        initMicrophoneSettingNotifications();
        initBtSettingNotifications();
        scheduleDailyMetricSnapshot();
        android.util.Log.i(TAG, "Finished Context Hub Service init");
    }

    private android.hardware.location.IContextHubClientCallback createDefaultClientCallback(final int i) {
        return new android.hardware.location.IContextHubClientCallback.Stub() { // from class: com.android.server.location.contexthub.ContextHubService.1
            private void finishCallback() {
                try {
                    ((android.hardware.location.IContextHubClient) com.android.server.location.contexthub.ContextHubService.this.mDefaultClientMap.get(java.lang.Integer.valueOf(i))).callbackFinished();
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.location.contexthub.ContextHubService.TAG, "RemoteException while finishing callback for hub (ID = " + i + ")", e);
                }
            }

            public void onMessageFromNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage) {
                com.android.server.location.contexthub.ContextHubService.this.onMessageReceiptOldApi(nanoAppMessage.getMessageType(), i, com.android.server.location.contexthub.ContextHubService.this.mNanoAppStateManager.getNanoAppHandle(i, nanoAppMessage.getNanoAppId()), nanoAppMessage.getMessageBody());
                finishCallback();
            }

            public void onHubReset() {
                com.android.server.location.contexthub.ContextHubService.this.onMessageReceiptOldApi(7, i, -1, new byte[]{0});
                finishCallback();
            }

            public void onNanoAppAborted(long j, int i2) {
                finishCallback();
            }

            public void onNanoAppLoaded(long j) {
                finishCallback();
            }

            public void onNanoAppUnloaded(long j) {
                finishCallback();
            }

            public void onNanoAppEnabled(long j) {
                finishCallback();
            }

            public void onNanoAppDisabled(long j) {
                finishCallback();
            }

            public void onClientAuthorizationChanged(long j, int i2) {
                finishCallback();
            }
        };
    }

    private boolean initContextHubServiceState(long j) {
        android.util.Pair<java.util.List<android.hardware.location.ContextHubInfo>, java.util.List<java.lang.String>> pair;
        if (this.mContextHubWrapper == null) {
            this.mTransactionManager = null;
            this.mClientManager = null;
            this.mSensorPrivacyManagerInternal = null;
            this.mDefaultClientMap = java.util.Collections.emptyMap();
            this.mContextHubIdToInfoMap = java.util.Collections.emptyMap();
            this.mSupportedContextHubPerms = java.util.Collections.emptyList();
            this.mContextHubInfoList = java.util.Collections.emptyList();
            return false;
        }
        try {
            pair = this.mContextHubWrapper.getHubs();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "RemoteException while getting Context Hub info", e);
            pair = new android.util.Pair<>(java.util.Collections.emptyList(), java.util.Collections.emptyList());
        }
        com.android.server.location.contexthub.ContextHubStatsLog.write(com.android.server.location.contexthub.ContextHubStatsLog.CONTEXT_HUB_BOOTED, android.os.SystemClock.elapsedRealtimeNanos() - j, ((java.util.List) pair.first).size());
        this.mContextHubIdToInfoMap = java.util.Collections.unmodifiableMap(com.android.server.location.contexthub.ContextHubServiceUtil.createContextHubInfoMap((java.util.List) pair.first));
        this.mSupportedContextHubPerms = (java.util.List) pair.second;
        this.mContextHubInfoList = new java.util.ArrayList(this.mContextHubIdToInfoMap.values());
        this.mClientManager = new com.android.server.location.contexthub.ContextHubClientManager(this.mContext, this.mContextHubWrapper);
        this.mTransactionManager = new com.android.server.location.contexthub.ContextHubTransactionManager(this.mContextHubWrapper, this.mClientManager, this.mNanoAppStateManager);
        this.mSensorPrivacyManagerInternal = (android.hardware.SensorPrivacyManagerInternal) com.android.server.LocalServices.getService(android.hardware.SensorPrivacyManagerInternal.class);
        return true;
    }

    private void initDefaultClientMap() {
        java.util.HashMap hashMap = new java.util.HashMap();
        for (java.util.Map.Entry<java.lang.Integer, android.hardware.location.ContextHubInfo> entry : this.mContextHubIdToInfoMap.entrySet()) {
            int intValue = entry.getKey().intValue();
            android.hardware.location.ContextHubInfo value = entry.getValue();
            this.mLastRestartTimestampMap.put(java.lang.Integer.valueOf(intValue), new java.util.concurrent.atomic.AtomicLong(android.os.SystemClock.elapsedRealtimeNanos()));
            try {
                this.mContextHubWrapper.registerCallback(intValue, new com.android.server.location.contexthub.ContextHubService.ContextHubServiceCallback(intValue));
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException while registering service callback for hub (ID = " + intValue + ")", e);
            }
            hashMap.put(java.lang.Integer.valueOf(intValue), this.mClientManager.registerClient(value, createDefaultClientCallback(intValue), (java.lang.String) null, this.mTransactionManager, this.mContext.getPackageName()));
            queryNanoAppsInternal(intValue);
        }
        this.mDefaultClientMap = java.util.Collections.unmodifiableMap(hashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initExistingCallbacks() {
        java.util.Iterator<java.lang.Integer> it = this.mContextHubIdToInfoMap.keySet().iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            try {
                this.mContextHubWrapper.registerExistingCallback(intValue);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException while registering existing service callback for hub (ID = " + intValue + ")", e);
            }
        }
    }

    private void initLocationSettingNotifications() {
        if (this.mContextHubWrapper == null || !this.mContextHubWrapper.supportsLocationSettingNotifications()) {
            return;
        }
        sendLocationSettingUpdate();
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("location_mode"), true, new android.database.ContentObserver(null) { // from class: com.android.server.location.contexthub.ContextHubService.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.location.contexthub.ContextHubService.this.sendLocationSettingUpdate();
            }
        }, -1);
    }

    private void initWifiSettingNotifications() {
        if (this.mContextHubWrapper == null || !this.mContextHubWrapper.supportsWifiSettingNotifications()) {
            return;
        }
        sendWifiSettingUpdate(true);
        android.content.BroadcastReceiver broadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.location.contexthub.ContextHubService.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction()) || "android.net.wifi.action.WIFI_SCAN_AVAILABILITY_CHANGED".equals(intent.getAction())) {
                    com.android.server.location.contexthub.ContextHubService.this.sendWifiSettingUpdate(false);
                }
            }
        };
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.action.WIFI_SCAN_AVAILABILITY_CHANGED");
        this.mContext.registerReceiver(broadcastReceiver, intentFilter);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("wifi_scan_always_enabled"), true, new android.database.ContentObserver(null) { // from class: com.android.server.location.contexthub.ContextHubService.4
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.location.contexthub.ContextHubService.this.sendWifiSettingUpdate(false);
            }
        }, -1);
    }

    private void initAirplaneModeSettingNotifications() {
        if (this.mContextHubWrapper == null || !this.mContextHubWrapper.supportsAirplaneModeSettingNotifications()) {
            return;
        }
        sendAirplaneModeSettingUpdate();
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("airplane_mode_on"), true, new android.database.ContentObserver(null) { // from class: com.android.server.location.contexthub.ContextHubService.5
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.location.contexthub.ContextHubService.this.sendAirplaneModeSettingUpdate();
            }
        }, -1);
    }

    private void initMicrophoneSettingNotifications() {
        if (this.mContextHubWrapper == null || !this.mContextHubWrapper.supportsMicrophoneSettingNotifications()) {
            return;
        }
        if (this.mUserManager == null) {
            this.mUserManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
            if (this.mUserManager == null) {
                android.util.Log.e(TAG, "Unable to get the UserManager service");
                return;
            }
        }
        sendMicrophoneDisableSettingUpdateForCurrentUser();
        if (this.mSensorPrivacyManagerInternal == null) {
            android.util.Log.e(TAG, "Unable to add a sensor privacy listener for all users");
        } else {
            this.mSensorPrivacyManagerInternal.addSensorPrivacyListenerForAllUsers(1, new android.hardware.SensorPrivacyManagerInternal.OnUserSensorPrivacyChangedListener() { // from class: com.android.server.location.contexthub.ContextHubService$$ExternalSyntheticLambda5
                public final void onSensorPrivacyChanged(int i, boolean z) {
                    com.android.server.location.contexthub.ContextHubService.this.lambda$initMicrophoneSettingNotifications$0(i, z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMicrophoneSettingNotifications$0(int i, boolean z) {
        if (android.os.UserManager.isHeadlessSystemUserMode() || i == getCurrentUserId()) {
            android.util.Log.d(TAG, "User: " + i + " mic privacy: " + z);
            sendMicrophoneDisableSettingUpdate(z);
        }
    }

    private void initBtSettingNotifications() {
        if (this.mContextHubWrapper == null || !this.mContextHubWrapper.supportsBtSettingNotifications()) {
            return;
        }
        sendBtSettingUpdate(true);
        android.content.BroadcastReceiver broadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.location.contexthub.ContextHubService.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                    com.android.server.location.contexthub.ContextHubService.this.sendBtSettingUpdate(false);
                }
            }
        };
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mContext.registerReceiver(broadcastReceiver, intentFilter);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("ble_scan_always_enabled"), false, new android.database.ContentObserver(null) { // from class: com.android.server.location.contexthub.ContextHubService.7
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.location.contexthub.ContextHubService.this.sendBtSettingUpdate(false);
            }
        }, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetSettings() {
        sendLocationSettingUpdate();
        sendWifiSettingUpdate(true);
        sendAirplaneModeSettingUpdate();
        sendMicrophoneDisableSettingUpdateForCurrentUser();
        sendBtSettingUpdate(true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.location.contexthub.ContextHubShellCommand(this.mContext, this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public int registerCallback(android.hardware.location.IContextHubCallback iContextHubCallback) throws android.os.RemoteException {
        super.registerCallback_enforcePermission();
        this.mCallbacksList.register(iContextHubCallback);
        android.util.Log.d(TAG, "Added callback, total callbacks " + this.mCallbacksList.getRegisteredCallbackCount());
        return 0;
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public int[] getContextHubHandles() throws android.os.RemoteException {
        super.getContextHubHandles_enforcePermission();
        return com.android.server.location.contexthub.ContextHubServiceUtil.createPrimitiveIntArray(this.mContextHubIdToInfoMap.keySet());
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public android.hardware.location.ContextHubInfo getContextHubInfo(int i) throws android.os.RemoteException {
        super.getContextHubInfo_enforcePermission();
        if (!this.mContextHubIdToInfoMap.containsKey(java.lang.Integer.valueOf(i))) {
            android.util.Log.e(TAG, "Invalid Context Hub handle " + i + " in getContextHubInfo");
            return null;
        }
        return this.mContextHubIdToInfoMap.get(java.lang.Integer.valueOf(i));
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public java.util.List<android.hardware.location.ContextHubInfo> getContextHubs() throws android.os.RemoteException {
        super.getContextHubs_enforcePermission();
        return this.mContextHubInfoList;
    }

    private android.hardware.location.IContextHubTransactionCallback createLoadTransactionCallback(final int i, final android.hardware.location.NanoAppBinary nanoAppBinary) {
        return new android.hardware.location.IContextHubTransactionCallback.Stub() { // from class: com.android.server.location.contexthub.ContextHubService.8
            public void onTransactionComplete(int i2) {
                com.android.server.location.contexthub.ContextHubService.this.handleLoadResponseOldApi(i, i2, nanoAppBinary);
            }

            public void onQueryResponse(int i2, java.util.List<android.hardware.location.NanoAppState> list) {
            }
        };
    }

    private android.hardware.location.IContextHubTransactionCallback createUnloadTransactionCallback(final int i) {
        return new android.hardware.location.IContextHubTransactionCallback.Stub() { // from class: com.android.server.location.contexthub.ContextHubService.9
            public void onTransactionComplete(int i2) {
                com.android.server.location.contexthub.ContextHubService.this.handleUnloadResponseOldApi(i, i2);
            }

            public void onQueryResponse(int i2, java.util.List<android.hardware.location.NanoAppState> list) {
            }
        };
    }

    private android.hardware.location.IContextHubTransactionCallback createQueryTransactionCallback(final int i) {
        return new android.hardware.location.IContextHubTransactionCallback.Stub() { // from class: com.android.server.location.contexthub.ContextHubService.10
            public void onTransactionComplete(int i2) {
            }

            public void onQueryResponse(int i2, java.util.List<android.hardware.location.NanoAppState> list) {
                com.android.server.location.contexthub.ContextHubService.this.onMessageReceiptOldApi(5, i, -1, new byte[]{(byte) i2});
            }
        };
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public int loadNanoApp(int i, android.hardware.location.NanoApp nanoApp) throws android.os.RemoteException {
        super.loadNanoApp_enforcePermission();
        if (this.mContextHubWrapper == null) {
            return -1;
        }
        if (!isValidContextHubId(i)) {
            android.util.Log.e(TAG, "Invalid Context Hub handle " + i + " in loadNanoApp");
            return -1;
        }
        if (nanoApp == null) {
            android.util.Log.e(TAG, "NanoApp cannot be null in loadNanoApp");
            return -1;
        }
        android.hardware.location.NanoAppBinary nanoAppBinary = new android.hardware.location.NanoAppBinary(nanoApp.getAppBinary());
        this.mTransactionManager.addTransaction(this.mTransactionManager.createLoadTransaction(i, nanoAppBinary, createLoadTransactionCallback(i, nanoAppBinary), getCallingPackageName()));
        return 0;
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public int unloadNanoApp(int i) throws android.os.RemoteException {
        super.unloadNanoApp_enforcePermission();
        if (this.mContextHubWrapper == null) {
            return -1;
        }
        android.hardware.location.NanoAppInstanceInfo nanoAppInstanceInfo = this.mNanoAppStateManager.getNanoAppInstanceInfo(i);
        if (nanoAppInstanceInfo == null) {
            android.util.Log.e(TAG, "Invalid nanoapp handle " + i + " in unloadNanoApp");
            return -1;
        }
        int contexthubId = nanoAppInstanceInfo.getContexthubId();
        this.mTransactionManager.addTransaction(this.mTransactionManager.createUnloadTransaction(contexthubId, nanoAppInstanceInfo.getAppId(), createUnloadTransactionCallback(contexthubId), getCallingPackageName()));
        return 0;
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public android.hardware.location.NanoAppInstanceInfo getNanoAppInstanceInfo(int i) throws android.os.RemoteException {
        super.getNanoAppInstanceInfo_enforcePermission();
        return this.mNanoAppStateManager.getNanoAppInstanceInfo(i);
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public int[] findNanoAppOnHub(int i, final android.hardware.location.NanoAppFilter nanoAppFilter) throws android.os.RemoteException {
        super.findNanoAppOnHub_enforcePermission();
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        if (nanoAppFilter != null) {
            this.mNanoAppStateManager.foreachNanoAppInstanceInfo(new java.util.function.Consumer() { // from class: com.android.server.location.contexthub.ContextHubService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.location.contexthub.ContextHubService.lambda$findNanoAppOnHub$1(nanoAppFilter, arrayList, (android.hardware.location.NanoAppInstanceInfo) obj);
                }
            });
        }
        int[] iArr = new int[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            iArr[i2] = ((java.lang.Integer) arrayList.get(i2)).intValue();
        }
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$findNanoAppOnHub$1(android.hardware.location.NanoAppFilter nanoAppFilter, java.util.ArrayList arrayList, android.hardware.location.NanoAppInstanceInfo nanoAppInstanceInfo) {
        if (nanoAppFilter.testMatch(nanoAppInstanceInfo)) {
            arrayList.add(java.lang.Integer.valueOf(nanoAppInstanceInfo.getHandle()));
        }
    }

    private boolean queryNanoAppsInternal(int i) {
        if (this.mContextHubWrapper == null) {
            return false;
        }
        this.mTransactionManager.addTransaction(this.mTransactionManager.createQueryTransaction(i, createQueryTransactionCallback(i), getCallingPackageName()));
        return true;
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public int sendMessage(int i, int i2, android.hardware.location.ContextHubMessage contextHubMessage) throws android.os.RemoteException {
        boolean z;
        super.sendMessage_enforcePermission();
        if (this.mContextHubWrapper == null) {
            return -1;
        }
        if (contextHubMessage == null) {
            android.util.Log.e(TAG, "ContextHubMessage cannot be null in sendMessage");
            return -1;
        }
        if (contextHubMessage.getData() == null) {
            android.util.Log.e(TAG, "ContextHubMessage message body cannot be null in sendMessage");
            return -1;
        }
        if (!isValidContextHubId(i)) {
            android.util.Log.e(TAG, "Invalid Context Hub handle " + i + " in sendMessage");
            return -1;
        }
        if (i2 == -1) {
            if (contextHubMessage.getMsgType() == 5) {
                z = queryNanoAppsInternal(i);
            } else {
                android.util.Log.e(TAG, "Invalid OS message params of type " + contextHubMessage.getMsgType());
                z = false;
            }
        } else {
            android.hardware.location.NanoAppInstanceInfo nanoAppInstanceInfo = getNanoAppInstanceInfo(i2);
            if (nanoAppInstanceInfo != null) {
                z = this.mDefaultClientMap.get(java.lang.Integer.valueOf(i)).sendMessageToNanoApp(android.hardware.location.NanoAppMessage.createMessageToNanoApp(nanoAppInstanceInfo.getAppId(), contextHubMessage.getMsgType(), contextHubMessage.getData())) == 0;
            } else {
                android.util.Log.e(TAG, "Failed to send nanoapp message - nanoapp with handle " + i2 + " does not exist.");
                z = false;
            }
        }
        return z ? 0 : -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleClientMessageCallback(int i, short s, android.hardware.location.NanoAppMessage nanoAppMessage, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2) {
        byte onMessageFromNanoApp = this.mClientManager.onMessageFromNanoApp(i, s, nanoAppMessage, list, list2);
        if (nanoAppMessage.isReliable() && onMessageFromNanoApp != 0) {
            sendMessageDeliveryStatusToContextHub(i, nanoAppMessage.getMessageSequenceNumber(), onMessageFromNanoApp);
        }
    }

    private void sendMessageDeliveryStatusToContextHub(int i, int i2, byte b) {
        if (!android.chre.flags.Flags.reliableMessageImplementation()) {
            return;
        }
        android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus = new android.hardware.contexthub.MessageDeliveryStatus();
        messageDeliveryStatus.messageSequenceNumber = i2;
        messageDeliveryStatus.errorCode = b;
        if (this.mContextHubWrapper.sendMessageDeliveryStatusToContextHub(i, messageDeliveryStatus) != 0) {
            android.util.Log.e(TAG, "Failed to send the reliable message status for message sequence number: " + i2 + " with error code: " + ((int) b));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLoadResponseOldApi(int i, int i2, android.hardware.location.NanoAppBinary nanoAppBinary) {
        if (nanoAppBinary == null) {
            android.util.Log.e(TAG, "Nanoapp binary field was null for a load transaction");
            return;
        }
        byte[] bArr = new byte[5];
        bArr[0] = (byte) i2;
        java.nio.ByteBuffer.wrap(bArr, 1, 4).order(java.nio.ByteOrder.nativeOrder()).putInt(this.mNanoAppStateManager.getNanoAppHandle(i, nanoAppBinary.getNanoAppId()));
        onMessageReceiptOldApi(3, i, -1, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUnloadResponseOldApi(int i, int i2) {
        onMessageReceiptOldApi(4, i, -1, new byte[]{(byte) i2});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTransactionResultCallback(int i, int i2, boolean z) {
        this.mTransactionManager.onTransactionResponse(i2, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessageDeliveryStatusCallback(android.hardware.contexthub.MessageDeliveryStatus messageDeliveryStatus) {
        this.mTransactionManager.onMessageDeliveryResponse(messageDeliveryStatus.messageSequenceNumber, messageDeliveryStatus.errorCode == 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleHubEventCallback(int i, int i2) {
        if (i2 == 1) {
            long elapsedRealtimeNanos = android.os.SystemClock.elapsedRealtimeNanos();
            com.android.server.location.contexthub.ContextHubStatsLog.write(com.android.server.location.contexthub.ContextHubStatsLog.CONTEXT_HUB_RESTARTED, java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(elapsedRealtimeNanos - this.mLastRestartTimestampMap.get(java.lang.Integer.valueOf(i)).getAndSet(elapsedRealtimeNanos)), i);
            com.android.server.location.contexthub.ContextHubEventLogger.getInstance().logContextHubRestart(i);
            resetSettings();
            this.mTransactionManager.onHubReset();
            queryNanoAppsInternal(i);
            this.mClientManager.onHubReset(i);
            return;
        }
        android.util.Log.i(TAG, "Received unknown hub event (hub ID = " + i + ", type = " + i2 + ")");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAppAbortCallback(int i, long j, int i2) {
        this.mClientManager.onNanoAppAborted(i, j, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleQueryAppsCallback(int i, java.util.List<android.hardware.location.NanoAppState> list) {
        if (this.mMetricQueryPendingContextHubIds.contains(java.lang.Integer.valueOf(i))) {
            for (android.hardware.location.NanoAppState nanoAppState : list) {
                com.android.server.location.contexthub.ContextHubStatsLog.write(400, i, nanoAppState.getNanoAppId(), (int) nanoAppState.getNanoAppVersion());
            }
            this.mMetricQueryPendingContextHubIds.remove(java.lang.Integer.valueOf(i));
            if (this.mMetricQueryPendingContextHubIds.isEmpty()) {
                scheduleDailyMetricSnapshot();
            }
        }
        this.mNanoAppStateManager.updateCache(i, list);
        this.mTransactionManager.onQueryResponse(list);
    }

    private boolean isValidContextHubId(int i) {
        return this.mContextHubIdToInfoMap.containsKey(java.lang.Integer.valueOf(i));
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public android.hardware.location.IContextHubClient createClient(int i, android.hardware.location.IContextHubClientCallback iContextHubClientCallback, @android.annotation.Nullable java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        super.createClient_enforcePermission();
        if (!isValidContextHubId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid context hub ID " + i);
        }
        if (iContextHubClientCallback == null) {
            throw new java.lang.NullPointerException("Cannot register client with null callback");
        }
        return this.mClientManager.registerClient(this.mContextHubIdToInfoMap.get(java.lang.Integer.valueOf(i)), iContextHubClientCallback, str, this.mTransactionManager, str2);
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public android.hardware.location.IContextHubClient createPendingIntentClient(int i, android.app.PendingIntent pendingIntent, long j, @android.annotation.Nullable java.lang.String str) throws android.os.RemoteException {
        super.createPendingIntentClient_enforcePermission();
        if (!isValidContextHubId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid context hub ID " + i);
        }
        return this.mClientManager.registerClient(this.mContextHubIdToInfoMap.get(java.lang.Integer.valueOf(i)), pendingIntent, j, str, this.mTransactionManager);
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public void loadNanoAppOnHub(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, android.hardware.location.NanoAppBinary nanoAppBinary) throws android.os.RemoteException {
        super.loadNanoAppOnHub_enforcePermission();
        if (!checkHalProxyAndContextHubId(i, iContextHubTransactionCallback, 0)) {
            return;
        }
        if (nanoAppBinary == null) {
            android.util.Log.e(TAG, "NanoAppBinary cannot be null in loadNanoAppOnHub");
            iContextHubTransactionCallback.onTransactionComplete(2);
        } else {
            this.mTransactionManager.addTransaction(this.mTransactionManager.createLoadTransaction(i, nanoAppBinary, iContextHubTransactionCallback, getCallingPackageName()));
        }
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public void unloadNanoAppFromHub(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws android.os.RemoteException {
        super.unloadNanoAppFromHub_enforcePermission();
        if (!checkHalProxyAndContextHubId(i, iContextHubTransactionCallback, 1)) {
            return;
        }
        this.mTransactionManager.addTransaction(this.mTransactionManager.createUnloadTransaction(i, j, iContextHubTransactionCallback, getCallingPackageName()));
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public void enableNanoApp(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws android.os.RemoteException {
        super.enableNanoApp_enforcePermission();
        if (!checkHalProxyAndContextHubId(i, iContextHubTransactionCallback, 2)) {
            return;
        }
        this.mTransactionManager.addTransaction(this.mTransactionManager.createEnableTransaction(i, j, iContextHubTransactionCallback, getCallingPackageName()));
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public void disableNanoApp(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, long j) throws android.os.RemoteException {
        super.disableNanoApp_enforcePermission();
        if (!checkHalProxyAndContextHubId(i, iContextHubTransactionCallback, 3)) {
            return;
        }
        this.mTransactionManager.addTransaction(this.mTransactionManager.createDisableTransaction(i, j, iContextHubTransactionCallback, getCallingPackageName()));
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public void queryNanoApps(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback) throws android.os.RemoteException {
        super.queryNanoApps_enforcePermission();
        if (!checkHalProxyAndContextHubId(i, iContextHubTransactionCallback, 4)) {
            return;
        }
        this.mTransactionManager.addTransaction(this.mTransactionManager.createQueryTransaction(i, iContextHubTransactionCallback, getCallingPackageName()));
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public long[] getPreloadedNanoAppIds(android.hardware.location.ContextHubInfo contextHubInfo) throws android.os.RemoteException {
        super.getPreloadedNanoAppIds_enforcePermission();
        java.util.Objects.requireNonNull(contextHubInfo, "hubInfo cannot be null");
        long[] preloadedNanoappIds = this.mContextHubWrapper.getPreloadedNanoappIds(contextHubInfo.getId());
        if (preloadedNanoappIds == null) {
            return new long[0];
        }
        return preloadedNanoappIds;
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_CONTEXT_HUB")
    public boolean setTestMode(boolean z) {
        super.setTestMode_enforcePermission();
        boolean testMode = this.mContextHubWrapper.setTestMode(z);
        java.util.Iterator<java.lang.Integer> it = this.mDefaultClientMap.keySet().iterator();
        while (it.hasNext()) {
            queryNanoAppsInternal(it.next().intValue());
        }
        return testMode;
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, final java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            for (java.lang.String str : strArr) {
                if ("--proto".equals(str)) {
                    dump(new android.util.proto.ProtoOutputStream(fileDescriptor));
                    return;
                }
            }
            printWriter.println("Dumping ContextHub Service");
            printWriter.println("");
            printWriter.println("=================== CONTEXT HUBS ====================");
            java.util.Iterator<android.hardware.location.ContextHubInfo> it = this.mContextHubIdToInfoMap.values().iterator();
            while (it.hasNext()) {
                printWriter.println(it.next());
            }
            printWriter.println("Supported permissions: " + java.util.Arrays.toString(this.mSupportedContextHubPerms.toArray()));
            printWriter.println("");
            printWriter.println("=================== NANOAPPS ====================");
            com.android.server.location.contexthub.NanoAppStateManager nanoAppStateManager = this.mNanoAppStateManager;
            java.util.Objects.requireNonNull(printWriter);
            nanoAppStateManager.foreachNanoAppInstanceInfo(new java.util.function.Consumer() { // from class: com.android.server.location.contexthub.ContextHubService$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    printWriter.println((android.hardware.location.NanoAppInstanceInfo) obj);
                }
            });
            printWriter.println("");
            printWriter.println("=================== PRELOADED NANOAPPS ====================");
            dumpPreloadedNanoapps(printWriter);
            printWriter.println("");
            printWriter.println("=================== CLIENTS ====================");
            printWriter.println(this.mClientManager);
            printWriter.println("");
            printWriter.println("=================== TRANSACTIONS ====================");
            printWriter.println(this.mTransactionManager);
            printWriter.println("");
            printWriter.println("=================== EVENTS ====================");
            printWriter.println(com.android.server.location.contexthub.ContextHubEventLogger.getInstance());
        }
    }

    void denyClientAuthState(int i, final java.lang.String str, final long j) {
        android.util.Log.i(TAG, "Denying " + str + " access to " + java.lang.Long.toHexString(j) + " on context hub # " + i);
        this.mClientManager.forEachClientOfHub(i, new java.util.function.Consumer() { // from class: com.android.server.location.contexthub.ContextHubService$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.location.contexthub.ContextHubService.lambda$denyClientAuthState$2(str, j, (com.android.server.location.contexthub.ContextHubClientBroker) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$denyClientAuthState$2(java.lang.String str, long j, com.android.server.location.contexthub.ContextHubClientBroker contextHubClientBroker) {
        if (contextHubClientBroker.getPackageName().equals(str)) {
            contextHubClientBroker.updateNanoAppAuthState(j, java.util.Collections.emptyList(), false, true);
        }
    }

    private void dump(final android.util.proto.ProtoOutputStream protoOutputStream) {
        this.mContextHubIdToInfoMap.values().forEach(new java.util.function.Consumer() { // from class: com.android.server.location.contexthub.ContextHubService$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.location.contexthub.ContextHubService.lambda$dump$3(protoOutputStream, (android.hardware.location.ContextHubInfo) obj);
            }
        });
        long start = protoOutputStream.start(1146756268034L);
        this.mClientManager.dump(protoOutputStream);
        protoOutputStream.end(start);
        protoOutputStream.flush();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$3(android.util.proto.ProtoOutputStream protoOutputStream, android.hardware.location.ContextHubInfo contextHubInfo) {
        long start = protoOutputStream.start(2246267895809L);
        contextHubInfo.dump(protoOutputStream);
        protoOutputStream.end(start);
    }

    private void dumpPreloadedNanoapps(java.io.PrintWriter printWriter) {
        int intValue;
        long[] preloadedNanoappIds;
        if (this.mContextHubWrapper == null) {
            return;
        }
        java.util.Iterator<java.lang.Integer> it = this.mContextHubIdToInfoMap.keySet().iterator();
        while (it.hasNext() && (preloadedNanoappIds = this.mContextHubWrapper.getPreloadedNanoappIds((intValue = it.next().intValue()))) != null) {
            printWriter.print("Context Hub (id=");
            printWriter.print(intValue);
            printWriter.println("):");
            for (long j : preloadedNanoappIds) {
                printWriter.print("  ID: 0x");
                printWriter.println(java.lang.Long.toHexString(j));
            }
        }
    }

    private void checkPermissions() {
        com.android.server.location.contexthub.ContextHubServiceUtil.checkPermissions(this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int onMessageReceiptOldApi(int i, int i2, int i3, byte[] bArr) {
        if (bArr == null) {
            return -1;
        }
        synchronized (this.mCallbacksList) {
            try {
                int beginBroadcast = this.mCallbacksList.beginBroadcast();
                if (beginBroadcast < 1) {
                    return 0;
                }
                android.hardware.location.ContextHubMessage contextHubMessage = new android.hardware.location.ContextHubMessage(i, 0, bArr);
                for (int i4 = 0; i4 < beginBroadcast; i4++) {
                    android.hardware.location.IContextHubCallback broadcastItem = this.mCallbacksList.getBroadcastItem(i4);
                    try {
                        broadcastItem.onMessageReceipt(i2, i3, contextHubMessage);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.i(TAG, "Exception (" + e + ") calling remote callback (" + broadcastItem + ").");
                    }
                }
                this.mCallbacksList.finishBroadcast();
                return 0;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean checkHalProxyAndContextHubId(int i, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback, int i2) {
        if (this.mContextHubWrapper == null) {
            try {
                iContextHubTransactionCallback.onTransactionComplete(8);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException while calling onTransactionComplete", e);
            }
            return false;
        }
        if (!isValidContextHubId(i)) {
            android.util.Log.e(TAG, "Cannot start " + android.hardware.location.ContextHubTransaction.typeToString(i2, false) + " transaction for invalid hub ID " + i);
            try {
                iContextHubTransactionCallback.onTransactionComplete(2);
            } catch (android.os.RemoteException e2) {
                android.util.Log.e(TAG, "RemoteException while calling onTransactionComplete", e2);
            }
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendLocationSettingUpdate() {
        this.mContextHubWrapper.onLocationSettingChanged(((android.location.LocationManager) this.mContext.getSystemService(android.location.LocationManager.class)).isLocationEnabledForUser(android.os.UserHandle.CURRENT));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendWifiSettingUpdate(boolean z) {
        synchronized (this.mSendWifiSettingUpdateLock) {
            try {
                android.net.wifi.WifiManager wifiManager = (android.net.wifi.WifiManager) this.mContext.getSystemService(android.net.wifi.WifiManager.class);
                boolean isWifiEnabled = wifiManager.isWifiEnabled();
                boolean isScanAlwaysAvailable = wifiManager.isScanAlwaysAvailable();
                boolean z2 = isWifiEnabled || isScanAlwaysAvailable;
                if (z || this.mIsWifiAvailable != z2) {
                    this.mIsWifiAvailable = z2;
                    this.mContextHubWrapper.onWifiSettingChanged(z2);
                }
                if (z || this.mIsWifiScanningEnabled != isScanAlwaysAvailable) {
                    this.mIsWifiScanningEnabled = isScanAlwaysAvailable;
                    this.mContextHubWrapper.onWifiScanningSettingChanged(isScanAlwaysAvailable);
                }
                if (z || this.mIsWifiMainEnabled != isWifiEnabled) {
                    this.mIsWifiMainEnabled = isWifiEnabled;
                    this.mContextHubWrapper.onWifiMainSettingChanged(isWifiEnabled);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBtSettingUpdate(boolean z) {
        android.bluetooth.BluetoothAdapter defaultAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            boolean isEnabled = defaultAdapter.isEnabled();
            boolean isBleScanAlwaysAvailable = defaultAdapter.isBleScanAlwaysAvailable();
            if (z || this.mIsBtScanningEnabled != isBleScanAlwaysAvailable) {
                this.mIsBtScanningEnabled = isBleScanAlwaysAvailable;
                this.mContextHubWrapper.onBtScanningSettingChanged(isBleScanAlwaysAvailable);
            }
            if (z || this.mIsBtMainEnabled != isEnabled) {
                this.mIsBtMainEnabled = isEnabled;
                this.mContextHubWrapper.onBtMainSettingChanged(isEnabled);
                return;
            }
            return;
        }
        android.util.Log.d(TAG, "BT adapter not available. Getting permissions from user settings");
        boolean z2 = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "bluetooth_on", 0) == 1;
        boolean z3 = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "ble_scan_always_enabled", 0) == 1;
        if (z || this.mIsBtMainEnabled != z2) {
            this.mIsBtMainEnabled = z2;
            this.mContextHubWrapper.onBtMainSettingChanged(this.mIsBtMainEnabled);
        }
        if (z || this.mIsBtScanningEnabled != z3) {
            this.mIsBtScanningEnabled = z3;
            this.mContextHubWrapper.onBtScanningSettingChanged(this.mIsBtScanningEnabled);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAirplaneModeSettingUpdate() {
        this.mContextHubWrapper.onAirplaneModeSettingChanged(android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) == 1);
    }

    private void sendMicrophoneDisableSettingUpdate(boolean z) {
        android.util.Log.d(TAG, "Mic Disabled Setting: " + z);
        this.mContextHubWrapper.onMicrophoneSettingChanged(z ^ true);
    }

    private void sendMicrophoneDisableSettingUpdateForCurrentUser() {
        sendMicrophoneDisableSettingUpdate(this.mSensorPrivacyManagerInternal == null ? false : this.mSensorPrivacyManagerInternal.isSensorPrivacyEnabled(getCurrentUserId(), 1));
    }

    private void scheduleDailyMetricSnapshot() {
        try {
            this.mDailyMetricTimer.schedule(new java.lang.Runnable() { // from class: com.android.server.location.contexthub.ContextHubService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.contexthub.ContextHubService.this.lambda$scheduleDailyMetricSnapshot$4();
                }
            }, 1L, java.util.concurrent.TimeUnit.DAYS);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error when schedule a timer", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleDailyMetricSnapshot$4() {
        java.util.Iterator<java.lang.Integer> it = this.mContextHubIdToInfoMap.keySet().iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            this.mMetricQueryPendingContextHubIds.add(java.lang.Integer.valueOf(intValue));
            queryNanoAppsInternal(intValue);
        }
    }

    private java.lang.String getCallingPackageName() {
        return this.mContext.getPackageManager().getNameForUid(android.os.Binder.getCallingUid());
    }

    private int getCurrentUserId() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int i = android.app.ActivityManager.getService().getCurrentUser().id;
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (android.os.RemoteException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void onUserChanged() {
        android.util.Log.d(TAG, "User changed to id: " + getCurrentUserId());
        sendLocationSettingUpdate();
        sendMicrophoneDisableSettingUpdateForCurrentUser();
    }
}
