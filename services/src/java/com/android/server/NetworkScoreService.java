package com.android.server;

/* loaded from: classes.dex */
public class NetworkScoreService extends android.net.INetworkScoreService.Stub {
    private static final boolean DBG;
    private static final java.lang.String TAG = "NetworkScoreService";
    private static final boolean VERBOSE;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private android.content.BroadcastReceiver mLocationModeReceiver;
    private final com.android.server.NetworkScorerAppManager mNetworkScorerAppManager;

    @com.android.internal.annotations.GuardedBy({"mPackageMonitorLock"})
    private com.android.server.NetworkScoreService.NetworkScorerPackageMonitor mPackageMonitor;
    private final java.lang.Object mPackageMonitorLock;
    private final com.android.server.NetworkScoreService.DispatchingContentObserver mRecommendationSettingsObserver;

    @com.android.internal.annotations.GuardedBy({"mScoreCaches"})
    private final java.util.Map<java.lang.Integer, android.os.RemoteCallbackList<android.net.INetworkScoreCache>> mScoreCaches;
    private final java.util.function.Function<android.net.NetworkScorerAppData, com.android.server.NetworkScoreService.ScoringServiceConnection> mServiceConnProducer;

    @com.android.internal.annotations.GuardedBy({"mServiceConnectionLock"})
    private com.android.server.NetworkScoreService.ScoringServiceConnection mServiceConnection;
    private final java.lang.Object mServiceConnectionLock;
    private final android.database.ContentObserver mUseOpenWifiPackageObserver;
    private android.content.BroadcastReceiver mUserIntentReceiver;

    static {
        boolean z = false;
        DBG = android.os.Build.IS_DEBUGGABLE && android.util.Log.isLoggable(TAG, 3);
        if (android.os.Build.IS_DEBUGGABLE && android.util.Log.isLoggable(TAG, 2)) {
            z = true;
        }
        VERBOSE = z;
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        private final com.android.server.NetworkScoreService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
            this.mService = new com.android.server.NetworkScoreService(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            android.util.Log.i(com.android.server.NetworkScoreService.TAG, "Registering network_score");
            publishBinderService("network_score", this.mService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 500) {
                this.mService.systemReady();
            } else if (i == 1000) {
                this.mService.systemRunning();
            }
        }
    }

    private class NetworkScorerPackageMonitor extends com.android.internal.content.PackageMonitor {
        final java.lang.String mPackageToWatch;

        private NetworkScorerPackageMonitor(java.lang.String str) {
            this.mPackageToWatch = str;
        }

        public void onPackageAdded(java.lang.String str, int i) {
            evaluateBinding(str, true);
        }

        public void onPackageRemoved(java.lang.String str, int i) {
            evaluateBinding(str, true);
        }

        public void onPackageModified(java.lang.String str) {
            evaluateBinding(str, false);
        }

        public boolean onHandleForceStop(android.content.Intent intent, java.lang.String[] strArr, int i, boolean z) {
            if (z) {
                for (java.lang.String str : strArr) {
                    evaluateBinding(str, true);
                }
            }
            return super.onHandleForceStop(intent, strArr, i, z);
        }

        public void onPackageUpdateFinished(java.lang.String str, int i) {
            evaluateBinding(str, true);
        }

        private void evaluateBinding(java.lang.String str, boolean z) {
            if (!this.mPackageToWatch.equals(str)) {
                return;
            }
            if (com.android.server.NetworkScoreService.DBG) {
                android.util.Log.d(com.android.server.NetworkScoreService.TAG, "Evaluating binding for: " + str + ", forceUnbind=" + z);
            }
            android.net.NetworkScorerAppData activeScorer = com.android.server.NetworkScoreService.this.mNetworkScorerAppManager.getActiveScorer();
            if (activeScorer == null) {
                if (com.android.server.NetworkScoreService.DBG) {
                    android.util.Log.d(com.android.server.NetworkScoreService.TAG, "No active scorers available.");
                }
                com.android.server.NetworkScoreService.this.refreshBinding();
                return;
            }
            if (z) {
                com.android.server.NetworkScoreService.this.unbindFromScoringServiceIfNeeded();
            }
            if (com.android.server.NetworkScoreService.DBG) {
                android.util.Log.d(com.android.server.NetworkScoreService.TAG, "Binding to " + activeScorer.getRecommendationServiceComponent() + " if needed.");
            }
            com.android.server.NetworkScoreService.this.bindToScoringServiceIfNeeded(activeScorer);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class DispatchingContentObserver extends android.database.ContentObserver {
        private final android.content.Context mContext;
        private final android.os.Handler mHandler;
        private final java.util.Map<android.net.Uri, java.lang.Integer> mUriEventMap;

        public DispatchingContentObserver(android.content.Context context, android.os.Handler handler) {
            super(handler);
            this.mContext = context;
            this.mHandler = handler;
            this.mUriEventMap = new android.util.ArrayMap();
        }

        void observe(android.net.Uri uri, int i) {
            this.mUriEventMap.put(uri, java.lang.Integer.valueOf(i));
            this.mContext.getContentResolver().registerContentObserver(uri, false, this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            onChange(z, null);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (com.android.server.NetworkScoreService.DBG) {
                android.util.Log.d(com.android.server.NetworkScoreService.TAG, java.lang.String.format("onChange(%s, %s)", java.lang.Boolean.valueOf(z), uri));
            }
            java.lang.Integer num = this.mUriEventMap.get(uri);
            if (num != null) {
                this.mHandler.obtainMessage(num.intValue()).sendToTarget();
                return;
            }
            android.util.Log.w(com.android.server.NetworkScoreService.TAG, "No matching event to send for URI = " + uri);
        }
    }

    public NetworkScoreService(android.content.Context context) {
        this(context, new com.android.server.NetworkScorerAppManager(context), new java.util.function.Function() { // from class: com.android.server.NetworkScoreService$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return new com.android.server.NetworkScoreService.ScoringServiceConnection((android.net.NetworkScorerAppData) obj);
            }
        }, android.os.Looper.myLooper());
    }

    @com.android.internal.annotations.VisibleForTesting
    NetworkScoreService(android.content.Context context, com.android.server.NetworkScorerAppManager networkScorerAppManager, java.util.function.Function<android.net.NetworkScorerAppData, com.android.server.NetworkScoreService.ScoringServiceConnection> function, android.os.Looper looper) {
        this.mPackageMonitorLock = new java.lang.Object();
        this.mServiceConnectionLock = new java.lang.Object();
        this.mUserIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.NetworkScoreService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                java.lang.String action = intent.getAction();
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
                if (com.android.server.NetworkScoreService.DBG) {
                    android.util.Log.d(com.android.server.NetworkScoreService.TAG, "Received " + action + " for userId " + intExtra);
                }
                if (intExtra != -10000 && "android.intent.action.USER_UNLOCKED".equals(action)) {
                    com.android.server.NetworkScoreService.this.onUserUnlocked(intExtra);
                }
            }
        };
        this.mLocationModeReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.NetworkScoreService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if ("android.location.MODE_CHANGED".equals(intent.getAction())) {
                    com.android.server.NetworkScoreService.this.refreshBinding();
                }
            }
        };
        this.mContext = context;
        this.mNetworkScorerAppManager = networkScorerAppManager;
        this.mScoreCaches = new android.util.ArrayMap();
        this.mContext.registerReceiverAsUser(this.mUserIntentReceiver, android.os.UserHandle.SYSTEM, new android.content.IntentFilter("android.intent.action.USER_UNLOCKED"), null, null);
        this.mHandler = new com.android.server.NetworkScoreService.ServiceHandler(looper);
        this.mContext.registerReceiverAsUser(this.mLocationModeReceiver, android.os.UserHandle.SYSTEM, new android.content.IntentFilter("android.location.MODE_CHANGED"), null, this.mHandler);
        this.mRecommendationSettingsObserver = new com.android.server.NetworkScoreService.DispatchingContentObserver(context, this.mHandler);
        this.mServiceConnProducer = function;
        this.mUseOpenWifiPackageObserver = new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.NetworkScoreService.3
            @Override // android.database.ContentObserver
            public void onChange(boolean z, android.net.Uri uri, int i) {
                if (android.provider.Settings.Global.getUriFor("use_open_wifi_package").equals(uri)) {
                    java.lang.String string = android.provider.Settings.Global.getString(com.android.server.NetworkScoreService.this.mContext.getContentResolver(), "use_open_wifi_package");
                    if (!android.text.TextUtils.isEmpty(string)) {
                        ((com.android.server.pm.permission.LegacyPermissionManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.LegacyPermissionManagerInternal.class)).grantDefaultPermissionsToDefaultUseOpenWifiApp(string, i);
                    }
                }
            }
        };
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("use_open_wifi_package"), false, this.mUseOpenWifiPackageObserver);
        ((com.android.server.pm.permission.LegacyPermissionManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.LegacyPermissionManagerInternal.class)).setUseOpenWifiAppPackagesProvider(new com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider() { // from class: com.android.server.NetworkScoreService$$ExternalSyntheticLambda0
            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider
            public final java.lang.String[] getPackages(int i) {
                java.lang.String[] lambda$new$0;
                lambda$new$0 = com.android.server.NetworkScoreService.this.lambda$new$0(i);
                return lambda$new$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String[] lambda$new$0(int i) {
        java.lang.String string = android.provider.Settings.Global.getString(this.mContext.getContentResolver(), "use_open_wifi_package");
        if (!android.text.TextUtils.isEmpty(string)) {
            return new java.lang.String[]{string};
        }
        return null;
    }

    void systemReady() {
        if (DBG) {
            android.util.Log.d(TAG, "systemReady");
        }
        registerRecommendationSettingsObserver();
    }

    void systemRunning() {
        if (DBG) {
            android.util.Log.d(TAG, "systemRunning");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void onUserUnlocked(int i) {
        if (DBG) {
            android.util.Log.d(TAG, "onUserUnlocked(" + i + ")");
        }
        refreshBinding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshBinding() {
        if (DBG) {
            android.util.Log.d(TAG, "refreshBinding()");
        }
        this.mNetworkScorerAppManager.updateState();
        this.mNetworkScorerAppManager.migrateNetworkScorerAppSettingIfNeeded();
        registerPackageMonitorIfNeeded();
        bindToScoringServiceIfNeeded();
    }

    private void registerRecommendationSettingsObserver() {
        this.mRecommendationSettingsObserver.observe(android.provider.Settings.Global.getUriFor("network_recommendations_package"), 1);
        this.mRecommendationSettingsObserver.observe(android.provider.Settings.Global.getUriFor("network_recommendations_enabled"), 2);
    }

    private void registerPackageMonitorIfNeeded() {
        if (DBG) {
            android.util.Log.d(TAG, "registerPackageMonitorIfNeeded()");
        }
        android.net.NetworkScorerAppData activeScorer = this.mNetworkScorerAppManager.getActiveScorer();
        synchronized (this.mPackageMonitorLock) {
            try {
                if (this.mPackageMonitor != null) {
                    if (activeScorer != null) {
                        if (!activeScorer.getRecommendationServicePackageName().equals(this.mPackageMonitor.mPackageToWatch)) {
                        }
                    }
                    if (DBG) {
                        android.util.Log.d(TAG, "Unregistering package monitor for " + this.mPackageMonitor.mPackageToWatch);
                    }
                    this.mPackageMonitor.unregister();
                    this.mPackageMonitor = null;
                }
                if (activeScorer != null && this.mPackageMonitor == null) {
                    this.mPackageMonitor = new com.android.server.NetworkScoreService.NetworkScorerPackageMonitor(activeScorer.getRecommendationServicePackageName());
                    this.mPackageMonitor.register(this.mContext, (android.os.Looper) null, android.os.UserHandle.SYSTEM, false);
                    if (DBG) {
                        android.util.Log.d(TAG, "Registered package monitor for " + this.mPackageMonitor.mPackageToWatch);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void bindToScoringServiceIfNeeded() {
        if (DBG) {
            android.util.Log.d(TAG, "bindToScoringServiceIfNeeded");
        }
        bindToScoringServiceIfNeeded(this.mNetworkScorerAppManager.getActiveScorer());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindToScoringServiceIfNeeded(android.net.NetworkScorerAppData networkScorerAppData) {
        if (DBG) {
            android.util.Log.d(TAG, "bindToScoringServiceIfNeeded(" + networkScorerAppData + ")");
        }
        if (networkScorerAppData != null) {
            synchronized (this.mServiceConnectionLock) {
                try {
                    if (this.mServiceConnection != null && !this.mServiceConnection.getAppData().equals(networkScorerAppData)) {
                        unbindFromScoringServiceIfNeeded();
                    }
                    if (this.mServiceConnection == null) {
                        this.mServiceConnection = this.mServiceConnProducer.apply(networkScorerAppData);
                    }
                    this.mServiceConnection.bind(this.mContext);
                } finally {
                }
            }
            return;
        }
        unbindFromScoringServiceIfNeeded();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unbindFromScoringServiceIfNeeded() {
        if (DBG) {
            android.util.Log.d(TAG, "unbindFromScoringServiceIfNeeded");
        }
        synchronized (this.mServiceConnectionLock) {
            try {
                if (this.mServiceConnection != null) {
                    this.mServiceConnection.unbind(this.mContext);
                    if (DBG) {
                        android.util.Log.d(TAG, "Disconnected from: " + this.mServiceConnection.getAppData().getRecommendationServiceComponent());
                    }
                }
                this.mServiceConnection = null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        clearInternal();
    }

    public boolean updateScores(android.net.ScoredNetwork[] scoredNetworkArr) {
        android.os.RemoteCallbackList<android.net.INetworkScoreCache> remoteCallbackList;
        if (!isCallerActiveScorer(android.net.INetworkScoreService.Stub.getCallingUid())) {
            throw new java.lang.SecurityException("Caller with UID " + android.net.INetworkScoreService.Stub.getCallingUid() + " is not the active scorer.");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            for (android.net.ScoredNetwork scoredNetwork : scoredNetworkArr) {
                java.util.List list = (java.util.List) arrayMap.get(java.lang.Integer.valueOf(scoredNetwork.networkKey.type));
                if (list == null) {
                    list = new java.util.ArrayList();
                    arrayMap.put(java.lang.Integer.valueOf(scoredNetwork.networkKey.type), list);
                }
                list.add(scoredNetwork);
            }
            java.util.Iterator it = arrayMap.entrySet().iterator();
            while (true) {
                boolean z = true;
                if (it.hasNext()) {
                    java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
                    synchronized (this.mScoreCaches) {
                        try {
                            remoteCallbackList = this.mScoreCaches.get(entry.getKey());
                            if (remoteCallbackList != null && remoteCallbackList.getRegisteredCallbackCount() != 0) {
                                z = false;
                            }
                        } finally {
                        }
                    }
                    if (z) {
                        if (android.util.Log.isLoggable(TAG, 2)) {
                            android.util.Log.v(TAG, "No scorer registered for type " + entry.getKey() + ", discarding");
                        }
                    } else {
                        sendCacheUpdateCallback(com.android.server.NetworkScoreService.FilteringCacheUpdatingConsumer.create(this.mContext, (java.util.List) entry.getValue(), ((java.lang.Integer) entry.getKey()).intValue()), java.util.Collections.singleton(remoteCallbackList));
                    }
                } else {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class FilteringCacheUpdatingConsumer implements java.util.function.BiConsumer<android.net.INetworkScoreCache, java.lang.Object> {
        private final android.content.Context mContext;
        private java.util.function.UnaryOperator<java.util.List<android.net.ScoredNetwork>> mCurrentNetworkFilter;
        private final int mNetworkType;
        private java.util.function.UnaryOperator<java.util.List<android.net.ScoredNetwork>> mScanResultsFilter;
        private final java.util.List<android.net.ScoredNetwork> mScoredNetworkList;

        static com.android.server.NetworkScoreService.FilteringCacheUpdatingConsumer create(android.content.Context context, java.util.List<android.net.ScoredNetwork> list, int i) {
            return new com.android.server.NetworkScoreService.FilteringCacheUpdatingConsumer(context, list, i, null, null);
        }

        @com.android.internal.annotations.VisibleForTesting
        FilteringCacheUpdatingConsumer(android.content.Context context, java.util.List<android.net.ScoredNetwork> list, int i, java.util.function.UnaryOperator<java.util.List<android.net.ScoredNetwork>> unaryOperator, java.util.function.UnaryOperator<java.util.List<android.net.ScoredNetwork>> unaryOperator2) {
            this.mContext = context;
            this.mScoredNetworkList = list;
            this.mNetworkType = i;
            this.mCurrentNetworkFilter = unaryOperator;
            this.mScanResultsFilter = unaryOperator2;
        }

        @Override // java.util.function.BiConsumer
        public void accept(android.net.INetworkScoreCache iNetworkScoreCache, java.lang.Object obj) {
            int i;
            if (!(obj instanceof java.lang.Integer)) {
                i = 0;
            } else {
                i = ((java.lang.Integer) obj).intValue();
            }
            try {
                java.util.List<android.net.ScoredNetwork> filterScores = filterScores(this.mScoredNetworkList, i);
                if (!filterScores.isEmpty()) {
                    iNetworkScoreCache.updateScores(filterScores);
                }
            } catch (android.os.RemoteException e) {
                if (com.android.server.NetworkScoreService.VERBOSE) {
                    android.util.Log.v(com.android.server.NetworkScoreService.TAG, "Unable to update scores of type " + this.mNetworkType, e);
                }
            }
        }

        private java.util.List<android.net.ScoredNetwork> filterScores(java.util.List<android.net.ScoredNetwork> list, int i) {
            switch (i) {
                case 0:
                    return list;
                case 1:
                    if (this.mCurrentNetworkFilter == null) {
                        this.mCurrentNetworkFilter = new com.android.server.NetworkScoreService.CurrentNetworkScoreCacheFilter(new com.android.server.NetworkScoreService.WifiInfoSupplier(this.mContext));
                    }
                    return (java.util.List) this.mCurrentNetworkFilter.apply(list);
                case 2:
                    if (this.mScanResultsFilter == null) {
                        this.mScanResultsFilter = new com.android.server.NetworkScoreService.ScanResultsScoreCacheFilter(new com.android.server.NetworkScoreService.ScanResultsSupplier(this.mContext));
                    }
                    return (java.util.List) this.mScanResultsFilter.apply(list);
                default:
                    android.util.Log.w(com.android.server.NetworkScoreService.TAG, "Unknown filter type: " + i);
                    return list;
            }
        }
    }

    private static class WifiInfoSupplier implements java.util.function.Supplier<android.net.wifi.WifiInfo> {
        private final android.content.Context mContext;

        WifiInfoSupplier(android.content.Context context) {
            this.mContext = context;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.function.Supplier
        public android.net.wifi.WifiInfo get() {
            android.net.wifi.WifiManager wifiManager = (android.net.wifi.WifiManager) this.mContext.getSystemService(android.net.wifi.WifiManager.class);
            if (wifiManager != null) {
                return wifiManager.getConnectionInfo();
            }
            android.util.Log.w(com.android.server.NetworkScoreService.TAG, "WifiManager is null, failed to return the WifiInfo.");
            return null;
        }
    }

    private static class ScanResultsSupplier implements java.util.function.Supplier<java.util.List<android.net.wifi.ScanResult>> {
        private final android.content.Context mContext;

        ScanResultsSupplier(android.content.Context context) {
            this.mContext = context;
        }

        @Override // java.util.function.Supplier
        public java.util.List<android.net.wifi.ScanResult> get() {
            android.net.wifi.WifiScanner wifiScanner = (android.net.wifi.WifiScanner) this.mContext.getSystemService(android.net.wifi.WifiScanner.class);
            if (wifiScanner != null) {
                return wifiScanner.getSingleScanResults();
            }
            android.util.Log.w(com.android.server.NetworkScoreService.TAG, "WifiScanner is null, failed to return scan results.");
            return java.util.Collections.emptyList();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class CurrentNetworkScoreCacheFilter implements java.util.function.UnaryOperator<java.util.List<android.net.ScoredNetwork>> {
        private final android.net.NetworkKey mCurrentNetwork;

        CurrentNetworkScoreCacheFilter(java.util.function.Supplier<android.net.wifi.WifiInfo> supplier) {
            this.mCurrentNetwork = android.net.NetworkKey.createFromWifiInfo(supplier.get());
        }

        @Override // java.util.function.Function
        public java.util.List<android.net.ScoredNetwork> apply(java.util.List<android.net.ScoredNetwork> list) {
            if (this.mCurrentNetwork == null || list.isEmpty()) {
                return java.util.Collections.emptyList();
            }
            for (int i = 0; i < list.size(); i++) {
                android.net.ScoredNetwork scoredNetwork = list.get(i);
                if (scoredNetwork.networkKey.equals(this.mCurrentNetwork)) {
                    return java.util.Collections.singletonList(scoredNetwork);
                }
            }
            return java.util.Collections.emptyList();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class ScanResultsScoreCacheFilter implements java.util.function.UnaryOperator<java.util.List<android.net.ScoredNetwork>> {
        private final java.util.Set<android.net.NetworkKey> mScanResultKeys;

        ScanResultsScoreCacheFilter(java.util.function.Supplier<java.util.List<android.net.wifi.ScanResult>> supplier) {
            java.util.List<android.net.wifi.ScanResult> list = supplier.get();
            int size = list.size();
            this.mScanResultKeys = new android.util.ArraySet(size);
            for (int i = 0; i < size; i++) {
                android.net.NetworkKey createFromScanResult = android.net.NetworkKey.createFromScanResult(list.get(i));
                if (createFromScanResult != null) {
                    this.mScanResultKeys.add(createFromScanResult);
                }
            }
        }

        @Override // java.util.function.Function
        public java.util.List<android.net.ScoredNetwork> apply(java.util.List<android.net.ScoredNetwork> list) {
            if (this.mScanResultKeys.isEmpty() || list.isEmpty()) {
                return java.util.Collections.emptyList();
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i = 0; i < list.size(); i++) {
                android.net.ScoredNetwork scoredNetwork = list.get(i);
                if (this.mScanResultKeys.contains(scoredNetwork.networkKey)) {
                    arrayList.add(scoredNetwork);
                }
            }
            return arrayList;
        }
    }

    public boolean clearScores() {
        enforceSystemOrIsActiveScorer(android.net.INetworkScoreService.Stub.getCallingUid());
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            clearInternal();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean setActiveScorer(java.lang.String str) {
        enforceSystemOrHasScoreNetworks();
        return this.mNetworkScorerAppManager.setActiveScorer(str);
    }

    public boolean isCallerActiveScorer(int i) {
        boolean z;
        synchronized (this.mServiceConnectionLock) {
            try {
                z = this.mServiceConnection != null && this.mServiceConnection.getAppData().packageUid == i;
            } finally {
            }
        }
        return z;
    }

    private void enforceSystemOnly() throws java.lang.SecurityException {
        this.mContext.enforceCallingOrSelfPermission("android.permission.REQUEST_NETWORK_SCORES", "Caller must be granted REQUEST_NETWORK_SCORES.");
    }

    private void enforceSystemOrHasScoreNetworks() throws java.lang.SecurityException {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.REQUEST_NETWORK_SCORES") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.SCORE_NETWORKS") != 0) {
            throw new java.lang.SecurityException("Caller is neither the system process or a network scorer.");
        }
    }

    private void enforceSystemOrIsActiveScorer(int i) throws java.lang.SecurityException {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.REQUEST_NETWORK_SCORES") != 0 && !isCallerActiveScorer(i)) {
            throw new java.lang.SecurityException("Caller is neither the system process or the active network scorer.");
        }
    }

    public java.lang.String getActiveScorerPackage() {
        enforceSystemOrHasScoreNetworks();
        android.net.NetworkScorerAppData activeScorer = this.mNetworkScorerAppManager.getActiveScorer();
        if (activeScorer == null) {
            return null;
        }
        return activeScorer.getRecommendationServicePackageName();
    }

    public android.net.NetworkScorerAppData getActiveScorer() {
        enforceSystemOnly();
        return this.mNetworkScorerAppManager.getActiveScorer();
    }

    public java.util.List<android.net.NetworkScorerAppData> getAllValidScorers() {
        enforceSystemOnly();
        return this.mNetworkScorerAppManager.getAllValidScorers();
    }

    public void disableScoring() {
        enforceSystemOrIsActiveScorer(android.net.INetworkScoreService.Stub.getCallingUid());
    }

    private void clearInternal() {
        sendCacheUpdateCallback(new java.util.function.BiConsumer<android.net.INetworkScoreCache, java.lang.Object>() { // from class: com.android.server.NetworkScoreService.4
            @Override // java.util.function.BiConsumer
            public void accept(android.net.INetworkScoreCache iNetworkScoreCache, java.lang.Object obj) {
                try {
                    iNetworkScoreCache.clearScores();
                } catch (android.os.RemoteException e) {
                    if (android.util.Log.isLoggable(com.android.server.NetworkScoreService.TAG, 2)) {
                        android.util.Log.v(com.android.server.NetworkScoreService.TAG, "Unable to clear scores", e);
                    }
                }
            }
        }, getScoreCacheLists());
    }

    public void registerNetworkScoreCache(int i, android.net.INetworkScoreCache iNetworkScoreCache, int i2) {
        enforceSystemOnly();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mScoreCaches) {
                try {
                    android.os.RemoteCallbackList<android.net.INetworkScoreCache> remoteCallbackList = this.mScoreCaches.get(java.lang.Integer.valueOf(i));
                    if (remoteCallbackList == null) {
                        remoteCallbackList = new android.os.RemoteCallbackList<>();
                        this.mScoreCaches.put(java.lang.Integer.valueOf(i), remoteCallbackList);
                    }
                    if (!remoteCallbackList.register(iNetworkScoreCache, java.lang.Integer.valueOf(i2))) {
                        if (remoteCallbackList.getRegisteredCallbackCount() == 0) {
                            this.mScoreCaches.remove(java.lang.Integer.valueOf(i));
                        }
                        if (android.util.Log.isLoggable(TAG, 2)) {
                            android.util.Log.v(TAG, "Unable to register NetworkScoreCache for type " + i);
                        }
                    }
                } finally {
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void unregisterNetworkScoreCache(int i, android.net.INetworkScoreCache iNetworkScoreCache) {
        enforceSystemOnly();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mScoreCaches) {
                try {
                    android.os.RemoteCallbackList<android.net.INetworkScoreCache> remoteCallbackList = this.mScoreCaches.get(java.lang.Integer.valueOf(i));
                    if (remoteCallbackList == null || !remoteCallbackList.unregister(iNetworkScoreCache)) {
                        if (android.util.Log.isLoggable(TAG, 2)) {
                            android.util.Log.v(TAG, "Unable to unregister NetworkScoreCache for type " + i);
                        }
                    } else if (remoteCallbackList.getRegisteredCallbackCount() == 0) {
                        this.mScoreCaches.remove(java.lang.Integer.valueOf(i));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean requestScores(android.net.NetworkKey[] networkKeyArr) {
        enforceSystemOnly();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.net.INetworkRecommendationProvider recommendationProvider = getRecommendationProvider();
            if (recommendationProvider != null) {
                try {
                    recommendationProvider.requestScores(networkKeyArr);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(TAG, "Failed to request scores.", e);
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.net.NetworkScorerAppData activeScorer = this.mNetworkScorerAppManager.getActiveScorer();
                if (activeScorer == null) {
                    printWriter.println("Scoring is disabled.");
                    return;
                }
                printWriter.println("Current scorer: " + activeScorer);
                synchronized (this.mServiceConnectionLock) {
                    try {
                        if (this.mServiceConnection != null) {
                            this.mServiceConnection.dump(fileDescriptor, printWriter, strArr);
                        } else {
                            printWriter.println("ScoringServiceConnection: null");
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                printWriter.flush();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private java.util.Collection<android.os.RemoteCallbackList<android.net.INetworkScoreCache>> getScoreCacheLists() {
        java.util.ArrayList arrayList;
        synchronized (this.mScoreCaches) {
            arrayList = new java.util.ArrayList(this.mScoreCaches.values());
        }
        return arrayList;
    }

    private void sendCacheUpdateCallback(java.util.function.BiConsumer<android.net.INetworkScoreCache, java.lang.Object> biConsumer, java.util.Collection<android.os.RemoteCallbackList<android.net.INetworkScoreCache>> collection) {
        java.util.Iterator<android.os.RemoteCallbackList<android.net.INetworkScoreCache>> it = collection.iterator();
        while (it.hasNext()) {
            android.os.RemoteCallbackList<android.net.INetworkScoreCache> next = it.next();
            synchronized (next) {
                try {
                    int beginBroadcast = next.beginBroadcast();
                    for (int i = 0; i < beginBroadcast; i++) {
                        try {
                            biConsumer.accept(next.getBroadcastItem(i), next.getBroadcastCookie(i));
                        } finally {
                        }
                    }
                } finally {
                }
            }
        }
    }

    @android.annotation.Nullable
    private android.net.INetworkRecommendationProvider getRecommendationProvider() {
        synchronized (this.mServiceConnectionLock) {
            try {
                if (this.mServiceConnection != null) {
                    return this.mServiceConnection.getRecommendationProvider();
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class ScoringServiceConnection implements android.content.ServiceConnection {
        private final android.net.NetworkScorerAppData mAppData;
        private volatile boolean mBound = false;
        private volatile boolean mConnected = false;
        private volatile android.net.INetworkRecommendationProvider mRecommendationProvider;

        ScoringServiceConnection(android.net.NetworkScorerAppData networkScorerAppData) {
            this.mAppData = networkScorerAppData;
        }

        @com.android.internal.annotations.VisibleForTesting
        public void bind(android.content.Context context) {
            if (!this.mBound) {
                android.content.Intent intent = new android.content.Intent("android.net.action.RECOMMEND_NETWORKS");
                intent.setComponent(this.mAppData.getRecommendationServiceComponent());
                this.mBound = context.bindServiceAsUser(intent, this, android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN, android.os.UserHandle.SYSTEM);
                if (!this.mBound) {
                    android.util.Log.w(com.android.server.NetworkScoreService.TAG, "Bind call failed for " + intent);
                    context.unbindService(this);
                    return;
                }
                if (com.android.server.NetworkScoreService.DBG) {
                    android.util.Log.d(com.android.server.NetworkScoreService.TAG, "ScoringServiceConnection bound.");
                }
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        public void unbind(android.content.Context context) {
            try {
                if (this.mBound) {
                    this.mBound = false;
                    context.unbindService(this);
                    if (com.android.server.NetworkScoreService.DBG) {
                        android.util.Log.d(com.android.server.NetworkScoreService.TAG, "ScoringServiceConnection unbound.");
                    }
                }
            } catch (java.lang.RuntimeException e) {
                android.util.Log.e(com.android.server.NetworkScoreService.TAG, "Unbind failed.", e);
            }
            this.mConnected = false;
            this.mRecommendationProvider = null;
        }

        @com.android.internal.annotations.VisibleForTesting
        public android.net.NetworkScorerAppData getAppData() {
            return this.mAppData;
        }

        @com.android.internal.annotations.VisibleForTesting
        public android.net.INetworkRecommendationProvider getRecommendationProvider() {
            return this.mRecommendationProvider;
        }

        @com.android.internal.annotations.VisibleForTesting
        public java.lang.String getPackageName() {
            return this.mAppData.getRecommendationServiceComponent().getPackageName();
        }

        @com.android.internal.annotations.VisibleForTesting
        public boolean isAlive() {
            return this.mBound && this.mConnected;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            if (com.android.server.NetworkScoreService.DBG) {
                android.util.Log.d(com.android.server.NetworkScoreService.TAG, "ScoringServiceConnection: " + componentName.flattenToString());
            }
            this.mConnected = true;
            this.mRecommendationProvider = android.net.INetworkRecommendationProvider.Stub.asInterface(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            if (com.android.server.NetworkScoreService.DBG) {
                android.util.Log.d(com.android.server.NetworkScoreService.TAG, "ScoringServiceConnection, disconnected: " + componentName.flattenToString());
            }
            this.mConnected = false;
            this.mRecommendationProvider = null;
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            printWriter.println("ScoringServiceConnection: " + this.mAppData.getRecommendationServiceComponent() + ", bound: " + this.mBound + ", connected: " + this.mConnected);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public final class ServiceHandler extends android.os.Handler {
        public static final int MSG_RECOMMENDATIONS_PACKAGE_CHANGED = 1;
        public static final int MSG_RECOMMENDATION_ENABLED_SETTING_CHANGED = 2;

        public ServiceHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int i = message.what;
            switch (i) {
                case 1:
                case 2:
                    com.android.server.NetworkScoreService.this.refreshBinding();
                    break;
                default:
                    android.util.Log.w(com.android.server.NetworkScoreService.TAG, "Unknown message: " + i);
                    break;
            }
        }
    }
}
