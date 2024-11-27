package com.android.server.media;

/* loaded from: classes2.dex */
public final class RemoteDisplayProviderWatcher {
    private final com.android.server.media.RemoteDisplayProviderWatcher.Callback mCallback;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final android.content.pm.PackageManager mPackageManager;
    private boolean mRunning;
    private final int mUserId;
    private static final java.lang.String TAG = "RemoteDisplayProvider";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private final java.util.ArrayList<com.android.server.media.RemoteDisplayProviderProxy> mProviders = new java.util.ArrayList<>();
    private final android.content.BroadcastReceiver mScanPackagesReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.media.RemoteDisplayProviderWatcher.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (com.android.server.media.RemoteDisplayProviderWatcher.DEBUG) {
                android.util.Slog.d(com.android.server.media.RemoteDisplayProviderWatcher.TAG, "Received package manager broadcast: " + intent);
            }
            com.android.server.media.RemoteDisplayProviderWatcher.this.scanPackages();
        }
    };
    private final java.lang.Runnable mScanPackagesRunnable = new java.lang.Runnable() { // from class: com.android.server.media.RemoteDisplayProviderWatcher.2
        @Override // java.lang.Runnable
        public void run() {
            com.android.server.media.RemoteDisplayProviderWatcher.this.scanPackages();
        }
    };

    public interface Callback {
        void addProvider(com.android.server.media.RemoteDisplayProviderProxy remoteDisplayProviderProxy);

        void removeProvider(com.android.server.media.RemoteDisplayProviderProxy remoteDisplayProviderProxy);
    }

    public RemoteDisplayProviderWatcher(android.content.Context context, com.android.server.media.RemoteDisplayProviderWatcher.Callback callback, android.os.Handler handler, int i) {
        this.mContext = context;
        this.mCallback = callback;
        this.mHandler = handler;
        this.mUserId = i;
        this.mPackageManager = context.getPackageManager();
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "Watcher");
        printWriter.println(str + "  mUserId=" + this.mUserId);
        printWriter.println(str + "  mRunning=" + this.mRunning);
        printWriter.println(str + "  mProviders.size()=" + this.mProviders.size());
    }

    public void start() {
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
            for (int size = this.mProviders.size() - 1; size >= 0; size--) {
                this.mProviders.get(size).stop();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scanPackages() {
        if (!this.mRunning) {
            return;
        }
        int i = 0;
        java.util.Iterator it = this.mPackageManager.queryIntentServicesAsUser(new android.content.Intent("com.android.media.remotedisplay.RemoteDisplayProvider"), 0, this.mUserId).iterator();
        while (it.hasNext()) {
            android.content.pm.ServiceInfo serviceInfo = ((android.content.pm.ResolveInfo) it.next()).serviceInfo;
            if (serviceInfo != null && verifyServiceTrusted(serviceInfo)) {
                int findProvider = findProvider(serviceInfo.packageName, serviceInfo.name);
                if (findProvider < 0) {
                    com.android.server.media.RemoteDisplayProviderProxy remoteDisplayProviderProxy = new com.android.server.media.RemoteDisplayProviderProxy(this.mContext, new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name), this.mUserId);
                    remoteDisplayProviderProxy.start();
                    this.mProviders.add(i, remoteDisplayProviderProxy);
                    this.mCallback.addProvider(remoteDisplayProviderProxy);
                    i++;
                } else if (findProvider >= i) {
                    com.android.server.media.RemoteDisplayProviderProxy remoteDisplayProviderProxy2 = this.mProviders.get(findProvider);
                    remoteDisplayProviderProxy2.start();
                    remoteDisplayProviderProxy2.rebindIfDisconnected();
                    java.util.Collections.swap(this.mProviders, findProvider, i);
                    i++;
                }
            }
        }
        if (i < this.mProviders.size()) {
            for (int size = this.mProviders.size() - 1; size >= i; size--) {
                com.android.server.media.RemoteDisplayProviderProxy remoteDisplayProviderProxy3 = this.mProviders.get(size);
                this.mCallback.removeProvider(remoteDisplayProviderProxy3);
                this.mProviders.remove(remoteDisplayProviderProxy3);
                remoteDisplayProviderProxy3.stop();
            }
        }
    }

    private boolean verifyServiceTrusted(android.content.pm.ServiceInfo serviceInfo) {
        if (serviceInfo.permission == null || !serviceInfo.permission.equals("android.permission.BIND_REMOTE_DISPLAY")) {
            android.util.Slog.w(TAG, "Ignoring remote display provider service because it did not require the BIND_REMOTE_DISPLAY permission in its manifest: " + serviceInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + serviceInfo.name);
            return false;
        }
        if (this.mPackageManager.checkPermission("android.permission.REMOTE_DISPLAY_PROVIDER", serviceInfo.packageName) != 0) {
            android.util.Slog.w(TAG, "Ignoring remote display provider service because it does not have the REMOTE_DISPLAY_PROVIDER permission: " + serviceInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + serviceInfo.name);
            return false;
        }
        return true;
    }

    private int findProvider(java.lang.String str, java.lang.String str2) {
        int size = this.mProviders.size();
        for (int i = 0; i < size; i++) {
            if (this.mProviders.get(i).hasComponentName(str, str2)) {
                return i;
            }
        }
        return -1;
    }
}
