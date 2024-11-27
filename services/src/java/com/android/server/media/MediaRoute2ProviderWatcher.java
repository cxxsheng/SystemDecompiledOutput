package com.android.server.media;

/* loaded from: classes2.dex */
final class MediaRoute2ProviderWatcher {
    private final com.android.server.media.MediaRoute2ProviderWatcher.Callback mCallback;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final android.content.pm.PackageManager mPackageManager;
    private boolean mRunning;
    private final int mUserId;
    private static final java.lang.String TAG = "MR2ProviderWatcher";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final android.content.pm.PackageManager.ResolveInfoFlags RESOLVE_INFO_FLAGS = android.content.pm.PackageManager.ResolveInfoFlags.of(64);
    private final java.util.ArrayList<com.android.server.media.MediaRoute2ProviderServiceProxy> mProxies = new java.util.ArrayList<>();
    private final android.content.BroadcastReceiver mScanPackagesReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.media.MediaRoute2ProviderWatcher.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (com.android.server.media.MediaRoute2ProviderWatcher.DEBUG) {
                android.util.Slog.d(com.android.server.media.MediaRoute2ProviderWatcher.TAG, "Received package manager broadcast: " + intent);
            }
            com.android.server.media.MediaRoute2ProviderWatcher.this.postScanPackagesIfNeeded();
        }
    };

    public interface Callback {
        void onAddProviderService(@android.annotation.NonNull com.android.server.media.MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy);

        void onRemoveProviderService(@android.annotation.NonNull com.android.server.media.MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy);
    }

    MediaRoute2ProviderWatcher(android.content.Context context, com.android.server.media.MediaRoute2ProviderWatcher.Callback callback, android.os.Handler handler, int i) {
        this.mContext = context;
        this.mCallback = callback;
        this.mHandler = handler;
        this.mUserId = i;
        this.mPackageManager = context.getPackageManager();
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "MediaRoute2ProviderWatcher");
        java.lang.String str2 = str + "  ";
        if (this.mProxies.isEmpty()) {
            printWriter.println(str2 + "<no provider service proxies>");
            return;
        }
        java.util.Iterator<com.android.server.media.MediaRoute2ProviderServiceProxy> it = this.mProxies.iterator();
        while (it.hasNext()) {
            it.next().dump(printWriter, str2);
        }
    }

    public void start() {
        if (!this.mRunning) {
            this.mRunning = true;
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
            if (!com.android.media.flags.Flags.enablePreventionOfKeepAliveRouteProviders()) {
                intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
            }
            intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
            this.mContext.registerReceiverAsUser(this.mScanPackagesReceiver, new android.os.UserHandle(this.mUserId), intentFilter, null, this.mHandler);
            postScanPackagesIfNeeded();
        }
    }

    public void stop() {
        if (this.mRunning) {
            this.mRunning = false;
            this.mContext.unregisterReceiver(this.mScanPackagesReceiver);
            this.mHandler.removeCallbacks(new com.android.server.media.MediaRoute2ProviderWatcher$$ExternalSyntheticLambda0(this));
            for (int size = this.mProxies.size() - 1; size >= 0; size--) {
                this.mProxies.get(size).stop();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scanPackages() {
        if (!this.mRunning) {
            return;
        }
        int i = 0;
        for (android.content.pm.ResolveInfo resolveInfo : this.mPackageManager.queryIntentServicesAsUser(new android.content.Intent("android.media.MediaRoute2ProviderService"), RESOLVE_INFO_FLAGS, this.mUserId)) {
            android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            if (serviceInfo != null) {
                java.util.Iterator<java.lang.String> categoriesIterator = resolveInfo.filter.categoriesIterator();
                boolean z = false;
                if (categoriesIterator != null) {
                    while (categoriesIterator.hasNext()) {
                        z |= "android.media.MediaRoute2ProviderService.SELF_SCAN_ONLY".equals(categoriesIterator.next());
                    }
                }
                int findProvider = findProvider(serviceInfo.packageName, serviceInfo.name);
                if (findProvider < 0) {
                    com.android.server.media.MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy = new com.android.server.media.MediaRoute2ProviderServiceProxy(this.mContext, new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name), z, this.mUserId);
                    mediaRoute2ProviderServiceProxy.start(false);
                    this.mProxies.add(i, mediaRoute2ProviderServiceProxy);
                    this.mCallback.onAddProviderService(mediaRoute2ProviderServiceProxy);
                    i++;
                } else if (findProvider >= i) {
                    this.mProxies.get(findProvider).start(!com.android.media.flags.Flags.enablePreventionOfKeepAliveRouteProviders());
                    java.util.Collections.swap(this.mProxies, findProvider, i);
                    i++;
                }
            }
        }
        if (i < this.mProxies.size()) {
            for (int size = this.mProxies.size() - 1; size >= i; size--) {
                com.android.server.media.MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy2 = this.mProxies.get(size);
                this.mCallback.onRemoveProviderService(mediaRoute2ProviderServiceProxy2);
                this.mProxies.remove(mediaRoute2ProviderServiceProxy2);
                mediaRoute2ProviderServiceProxy2.stop();
            }
        }
    }

    private int findProvider(java.lang.String str, java.lang.String str2) {
        int size = this.mProxies.size();
        for (int i = 0; i < size; i++) {
            if (this.mProxies.get(i).hasComponentName(str, str2)) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postScanPackagesIfNeeded() {
        if (!this.mHandler.hasCallbacks(new com.android.server.media.MediaRoute2ProviderWatcher$$ExternalSyntheticLambda0(this))) {
            this.mHandler.post(new com.android.server.media.MediaRoute2ProviderWatcher$$ExternalSyntheticLambda0(this));
        }
    }
}
