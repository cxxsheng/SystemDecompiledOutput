package com.android.server.tv;

/* loaded from: classes2.dex */
final class TvRemoteProviderWatcher {
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final java.lang.Object mLock;
    private final android.content.pm.PackageManager mPackageManager;
    private final java.util.ArrayList<com.android.server.tv.TvRemoteProviderProxy> mProviderProxies;
    private boolean mRunning;
    private final android.content.BroadcastReceiver mScanPackagesReceiver;
    private final java.lang.Runnable mScanPackagesRunnable;
    private final java.util.Set<java.lang.String> mUnbundledServicePackages;
    private final int mUserId;
    private static final java.lang.String TAG = "TvRemoteProviderWatcher";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 2);

    TvRemoteProviderWatcher(android.content.Context context, java.lang.Object obj, android.os.Handler handler) {
        this.mProviderProxies = new java.util.ArrayList<>();
        this.mUnbundledServicePackages = new java.util.HashSet();
        this.mScanPackagesReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.tv.TvRemoteProviderWatcher.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (com.android.server.tv.TvRemoteProviderWatcher.DEBUG) {
                    android.util.Slog.d(com.android.server.tv.TvRemoteProviderWatcher.TAG, "Received package manager broadcast: " + intent);
                }
                com.android.server.tv.TvRemoteProviderWatcher.this.mHandler.post(com.android.server.tv.TvRemoteProviderWatcher.this.mScanPackagesRunnable);
            }
        };
        this.mScanPackagesRunnable = new java.lang.Runnable() { // from class: com.android.server.tv.TvRemoteProviderWatcher.2
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.tv.TvRemoteProviderWatcher.this.scanPackages();
            }
        };
        this.mContext = context;
        this.mHandler = handler;
        this.mUserId = android.os.UserHandle.myUserId();
        this.mPackageManager = context.getPackageManager();
        this.mLock = obj;
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(',');
        simpleStringSplitter.setString(context.getString(android.R.string.config_servicesExtensionPackage));
        simpleStringSplitter.forEach(new java.util.function.Consumer() { // from class: com.android.server.tv.TvRemoteProviderWatcher$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj2) {
                com.android.server.tv.TvRemoteProviderWatcher.this.lambda$new$0((java.lang.String) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(java.lang.String str) {
        java.lang.String trim = str.trim();
        if (!trim.isEmpty()) {
            this.mUnbundledServicePackages.add(trim);
        }
    }

    TvRemoteProviderWatcher(android.content.Context context, java.lang.Object obj) {
        this(context, obj, new android.os.Handler(true));
    }

    public void start() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "start()");
        }
        if (!this.mRunning) {
            this.mRunning = true;
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
            intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
            intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
            this.mContext.registerReceiverAsUser(this.mScanPackagesReceiver, new android.os.UserHandle(this.mUserId), intentFilter, null, this.mHandler);
            this.mHandler.post(this.mScanPackagesRunnable);
        }
    }

    public void stop() {
        if (this.mRunning) {
            this.mRunning = false;
            this.mContext.unregisterReceiver(this.mScanPackagesReceiver);
            this.mHandler.removeCallbacks(this.mScanPackagesRunnable);
            for (int size = this.mProviderProxies.size() - 1; size >= 0; size--) {
                this.mProviderProxies.get(size).stop();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scanPackages() {
        if (!this.mRunning) {
            return;
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "scanPackages()");
        }
        int i = 0;
        java.util.Iterator it = this.mPackageManager.queryIntentServicesAsUser(new android.content.Intent("com.android.media.tv.remoteprovider.TvRemoteProvider"), 0, this.mUserId).iterator();
        while (it.hasNext()) {
            android.content.pm.ServiceInfo serviceInfo = ((android.content.pm.ResolveInfo) it.next()).serviceInfo;
            if (serviceInfo != null && verifyServiceTrusted(serviceInfo)) {
                int findProvider = findProvider(serviceInfo.packageName, serviceInfo.name);
                if (findProvider < 0) {
                    com.android.server.tv.TvRemoteProviderProxy tvRemoteProviderProxy = new com.android.server.tv.TvRemoteProviderProxy(this.mContext, this.mLock, new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name), this.mUserId, serviceInfo.applicationInfo.uid);
                    tvRemoteProviderProxy.start();
                    this.mProviderProxies.add(i, tvRemoteProviderProxy);
                    i++;
                } else if (findProvider >= i) {
                    com.android.server.tv.TvRemoteProviderProxy tvRemoteProviderProxy2 = this.mProviderProxies.get(findProvider);
                    tvRemoteProviderProxy2.start();
                    tvRemoteProviderProxy2.rebindIfDisconnected();
                    java.util.Collections.swap(this.mProviderProxies, findProvider, i);
                    i++;
                }
            }
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "scanPackages() targetIndex " + i);
        }
        if (i < this.mProviderProxies.size()) {
            for (int size = this.mProviderProxies.size() - 1; size >= i; size--) {
                com.android.server.tv.TvRemoteProviderProxy tvRemoteProviderProxy3 = this.mProviderProxies.get(size);
                this.mProviderProxies.remove(tvRemoteProviderProxy3);
                tvRemoteProviderProxy3.stop();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean verifyServiceTrusted(android.content.pm.ServiceInfo serviceInfo) {
        if (serviceInfo.permission == null || !serviceInfo.permission.equals("android.permission.BIND_TV_REMOTE_SERVICE")) {
            android.util.Slog.w(TAG, "Ignoring atv remote provider service because it did not require the BIND_TV_REMOTE_SERVICE permission in its manifest: " + serviceInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + serviceInfo.name);
            return false;
        }
        if (!this.mUnbundledServicePackages.contains(serviceInfo.packageName)) {
            android.util.Slog.w(TAG, "Ignoring atv remote provider service because the package has not been set and/or whitelisted: " + serviceInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + serviceInfo.name);
            return false;
        }
        if (!hasNecessaryPermissions(serviceInfo.packageName)) {
            android.util.Slog.w(TAG, "Ignoring atv remote provider service because its package does not have TV_VIRTUAL_REMOTE_CONTROLLER permission: " + serviceInfo.packageName);
            return false;
        }
        return true;
    }

    private boolean hasNecessaryPermissions(java.lang.String str) {
        if (this.mPackageManager.checkPermission("android.permission.TV_VIRTUAL_REMOTE_CONTROLLER", str) == 0) {
            return true;
        }
        return false;
    }

    private int findProvider(java.lang.String str, java.lang.String str2) {
        int size = this.mProviderProxies.size();
        for (int i = 0; i < size; i++) {
            if (this.mProviderProxies.get(i).hasComponentName(str, str2)) {
                return i;
            }
        }
        return -1;
    }
}
