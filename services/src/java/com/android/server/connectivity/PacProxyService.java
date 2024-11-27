package com.android.server.connectivity;

/* loaded from: classes.dex */
public class PacProxyService extends android.net.IPacProxyManager.Stub {
    private static final java.lang.String ACTION_PAC_REFRESH = "android.net.proxy.PAC_REFRESH";
    private static final java.lang.String DEFAULT_DELAYS = "8 32 120 14400 43200";
    private static final int DELAY_1 = 0;
    private static final int DELAY_4 = 3;
    private static final int DELAY_LONG = 4;
    private static final long MAX_PAC_SIZE = 20000000;
    private static final java.lang.String PAC_PACKAGE = "com.android.pacprocessor";
    private static final java.lang.String PAC_SERVICE = "com.android.pacprocessor.PacService";
    private static final java.lang.String PAC_SERVICE_NAME = "com.android.net.IProxyService";
    private static final java.lang.String PROXY_PACKAGE = "com.android.proxyhandler";
    private static final java.lang.String PROXY_SERVICE = "com.android.proxyhandler.ProxyService";
    private static final java.lang.String TAG = "PacProxyService";
    private android.app.AlarmManager mAlarmManager;
    private android.content.ServiceConnection mConnection;
    private android.content.Context mContext;
    private int mCurrentDelay;
    private java.lang.String mCurrentPac;
    private volatile boolean mHasDownloaded;
    private volatile boolean mHasSentBroadcast;
    private final android.os.Handler mNetThreadHandler;
    private android.app.PendingIntent mPacRefreshIntent;
    private android.content.ServiceConnection mProxyConnection;

    @com.android.internal.annotations.GuardedBy({"mProxyLock"})
    private com.android.net.IProxyService mProxyService;

    @com.android.internal.annotations.GuardedBy({"mProxyLock"})
    private volatile android.net.Uri mPacUrl = android.net.Uri.EMPTY;
    private final android.os.RemoteCallbackList<android.net.IPacProxyInstalledListener> mCallbacks = new android.os.RemoteCallbackList<>();
    private final java.lang.Object mProxyLock = new java.lang.Object();
    private final java.lang.Object mBroadcastStateLock = new java.lang.Object();
    private java.lang.Runnable mPacDownloader = new java.lang.Runnable() { // from class: com.android.server.connectivity.PacProxyService.1
        @Override // java.lang.Runnable
        public void run() {
            java.lang.String str;
            android.net.Uri uri = com.android.server.connectivity.PacProxyService.this.mPacUrl;
            if (android.net.Uri.EMPTY.equals(uri)) {
                return;
            }
            int andSetThreadStatsTag = android.net.TrafficStats.getAndSetThreadStatsTag(-187);
            try {
                try {
                    str = com.android.server.connectivity.PacProxyService.get(uri);
                } catch (java.io.IOException e) {
                    android.util.Log.w(com.android.server.connectivity.PacProxyService.TAG, "Failed to load PAC file: " + e);
                    android.net.TrafficStats.setThreadStatsTag(andSetThreadStatsTag);
                    str = null;
                }
                if (str == null) {
                    com.android.server.connectivity.PacProxyService.this.reschedule();
                    return;
                }
                synchronized (com.android.server.connectivity.PacProxyService.this.mProxyLock) {
                    try {
                        if (!str.equals(com.android.server.connectivity.PacProxyService.this.mCurrentPac)) {
                            com.android.server.connectivity.PacProxyService.this.setCurrentProxyScript(str);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                com.android.server.connectivity.PacProxyService.this.mHasDownloaded = true;
                com.android.server.connectivity.PacProxyService.this.sendProxyIfNeeded();
                com.android.server.connectivity.PacProxyService.this.longSchedule();
            } finally {
                android.net.TrafficStats.setThreadStatsTag(andSetThreadStatsTag);
            }
        }
    };
    private int mLastPort = -1;

    class PacRefreshIntentReceiver extends android.content.BroadcastReceiver {
        PacRefreshIntentReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.connectivity.PacProxyService.this.mNetThreadHandler.post(com.android.server.connectivity.PacProxyService.this.mPacDownloader);
        }
    }

    public PacProxyService(@android.annotation.NonNull android.content.Context context) {
        this.mContext = context;
        android.os.HandlerThread handlerThread = new android.os.HandlerThread("android.pacproxyservice", 0);
        handlerThread.start();
        this.mNetThreadHandler = new android.os.Handler(handlerThread.getLooper());
        this.mPacRefreshIntent = android.app.PendingIntent.getBroadcast(context, 0, new android.content.Intent(ACTION_PAC_REFRESH), 67108864);
        context.registerReceiver(new com.android.server.connectivity.PacProxyService.PacRefreshIntentReceiver(), new android.content.IntentFilter(ACTION_PAC_REFRESH));
    }

    private android.app.AlarmManager getAlarmManager() {
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (android.app.AlarmManager) this.mContext.getSystemService(android.app.AlarmManager.class);
        }
        return this.mAlarmManager;
    }

    public void addListener(android.net.IPacProxyInstalledListener iPacProxyInstalledListener) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermissionOr(this.mContext, new java.lang.String[]{"android.permission.NETWORK_SETTINGS"});
        this.mCallbacks.register(iPacProxyInstalledListener);
    }

    public void removeListener(android.net.IPacProxyInstalledListener iPacProxyInstalledListener) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermissionOr(this.mContext, new java.lang.String[]{"android.permission.NETWORK_SETTINGS"});
        this.mCallbacks.unregister(iPacProxyInstalledListener);
    }

    public void setCurrentProxyScriptUrl(@android.annotation.Nullable android.net.ProxyInfo proxyInfo) {
        com.android.net.module.util.PermissionUtils.enforceNetworkStackPermissionOr(this.mContext, new java.lang.String[]{"android.permission.NETWORK_SETTINGS"});
        synchronized (this.mBroadcastStateLock) {
            if (proxyInfo != null) {
                try {
                    if (!android.net.Uri.EMPTY.equals(proxyInfo.getPacFileUrl())) {
                        if (!proxyInfo.getPacFileUrl().equals(this.mPacUrl) || proxyInfo.getPort() <= 0) {
                            this.mPacUrl = proxyInfo.getPacFileUrl();
                            this.mCurrentDelay = 0;
                            this.mHasSentBroadcast = false;
                            this.mHasDownloaded = false;
                            getAlarmManager().cancel(this.mPacRefreshIntent);
                            bind();
                        }
                        return;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            getAlarmManager().cancel(this.mPacRefreshIntent);
            synchronized (this.mProxyLock) {
                try {
                    this.mPacUrl = android.net.Uri.EMPTY;
                    this.mCurrentPac = null;
                    if (this.mProxyService != null) {
                        unbind();
                    }
                } finally {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String get(android.net.Uri uri) throws java.io.IOException {
        long j;
        if (!android.webkit.URLUtil.isValidUrl(uri.toString())) {
            throw new java.io.IOException("Malformed URL:" + uri);
        }
        try {
            java.net.URLConnection openConnection = new java.net.URL(uri.toString()).openConnection(java.net.Proxy.NO_PROXY);
            try {
                j = java.lang.Long.parseLong(openConnection.getHeaderField("Content-Length"));
            } catch (java.lang.NumberFormatException e) {
                j = -1;
            }
            if (j > MAX_PAC_SIZE) {
                throw new java.io.IOException("PAC too big: " + j + " bytes");
            }
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            do {
                int read = openConnection.getInputStream().read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    return byteArrayOutputStream.toString();
                }
            } while (byteArrayOutputStream.size() <= MAX_PAC_SIZE);
            throw new java.io.IOException("PAC too big");
        } catch (java.lang.IllegalArgumentException e2) {
            throw new java.io.IOException("Incorrect proxy type for " + uri);
        } catch (java.lang.UnsupportedOperationException e3) {
            throw new java.io.IOException("Unsupported URL connection type for " + uri);
        }
    }

    private int getNextDelay(int i) {
        int i2 = i + 1;
        if (i2 > 3) {
            return 3;
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void longSchedule() {
        this.mCurrentDelay = 0;
        setDownloadIn(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reschedule() {
        this.mCurrentDelay = getNextDelay(this.mCurrentDelay);
        setDownloadIn(this.mCurrentDelay);
    }

    private java.lang.String getPacChangeDelay() {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        java.lang.String str = android.os.SystemProperties.get("conn.pac_change_delay", DEFAULT_DELAYS);
        java.lang.String string = android.provider.Settings.Global.getString(contentResolver, "pac_change_delay");
        return string == null ? str : string;
    }

    private long getDownloadDelay(int i) {
        java.lang.String[] split = getPacChangeDelay().split(" ");
        if (i < split.length) {
            return java.lang.Long.parseLong(split[i]);
        }
        return 0L;
    }

    private void setDownloadIn(int i) {
        getAlarmManager().set(3, (getDownloadDelay(i) * 1000) + android.os.SystemClock.elapsedRealtime(), this.mPacRefreshIntent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mProxyLock"})
    public void setCurrentProxyScript(java.lang.String str) {
        if (this.mProxyService == null) {
            android.util.Log.e(TAG, "setCurrentProxyScript: no proxy service");
            return;
        }
        try {
            this.mProxyService.setPacFile(str);
            this.mCurrentPac = str;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Unable to set PAC file", e);
        }
    }

    private void bind() {
        if (this.mContext == null) {
            android.util.Log.e(TAG, "No context for binding");
            return;
        }
        android.content.Intent intent = new android.content.Intent();
        intent.setClassName(PAC_PACKAGE, PAC_SERVICE);
        if (this.mProxyConnection != null && this.mConnection != null) {
            this.mNetThreadHandler.post(this.mPacDownloader);
            return;
        }
        this.mConnection = new android.content.ServiceConnection() { // from class: com.android.server.connectivity.PacProxyService.2
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
                synchronized (com.android.server.connectivity.PacProxyService.this.mProxyLock) {
                    com.android.server.connectivity.PacProxyService.this.mProxyService = null;
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                synchronized (com.android.server.connectivity.PacProxyService.this.mProxyLock) {
                    try {
                        android.util.Log.d(com.android.server.connectivity.PacProxyService.TAG, "Adding service com.android.net.IProxyService " + iBinder.getInterfaceDescriptor());
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(com.android.server.connectivity.PacProxyService.TAG, "Remote Exception", e);
                    }
                    android.os.ServiceManager.addService(com.android.server.connectivity.PacProxyService.PAC_SERVICE_NAME, iBinder);
                    com.android.server.connectivity.PacProxyService.this.mProxyService = com.android.net.IProxyService.Stub.asInterface(iBinder);
                    if (com.android.server.connectivity.PacProxyService.this.mProxyService == null) {
                        android.util.Log.e(com.android.server.connectivity.PacProxyService.TAG, "No proxy service");
                    } else if (com.android.server.connectivity.PacProxyService.this.mCurrentPac != null) {
                        com.android.server.connectivity.PacProxyService.this.setCurrentProxyScript(com.android.server.connectivity.PacProxyService.this.mCurrentPac);
                    } else {
                        com.android.server.connectivity.PacProxyService.this.mNetThreadHandler.post(com.android.server.connectivity.PacProxyService.this.mPacDownloader);
                    }
                }
            }
        };
        this.mContext.bindServiceAsUser(intent, this.mConnection, 1073741829, android.os.UserHandle.SYSTEM);
        android.content.Intent intent2 = new android.content.Intent();
        intent2.setClassName(PROXY_PACKAGE, PROXY_SERVICE);
        this.mProxyConnection = new android.content.ServiceConnection() { // from class: com.android.server.connectivity.PacProxyService.3
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                com.android.net.IProxyCallback asInterface = com.android.net.IProxyCallback.Stub.asInterface(iBinder);
                if (asInterface != null) {
                    try {
                        asInterface.getProxyPort(new com.android.net.IProxyPortListener.Stub() { // from class: com.android.server.connectivity.PacProxyService.3.1
                            public void setProxyPort(int i) {
                                if (com.android.server.connectivity.PacProxyService.this.mLastPort != -1) {
                                    com.android.server.connectivity.PacProxyService.this.mHasSentBroadcast = false;
                                }
                                com.android.server.connectivity.PacProxyService.this.mLastPort = i;
                                if (i == -1) {
                                    android.util.Log.e(com.android.server.connectivity.PacProxyService.TAG, "Received invalid port from Local Proxy, PAC will not be operational");
                                    return;
                                }
                                android.util.Log.d(com.android.server.connectivity.PacProxyService.TAG, "Local proxy is bound on " + i);
                                com.android.server.connectivity.PacProxyService.this.sendProxyIfNeeded();
                            }
                        });
                    } catch (android.os.RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.mContext.bindServiceAsUser(intent2, this.mProxyConnection, 1073741829, this.mNetThreadHandler, android.os.UserHandle.SYSTEM);
    }

    private void unbind() {
        if (this.mConnection != null) {
            this.mContext.unbindService(this.mConnection);
            this.mConnection = null;
        }
        if (this.mProxyConnection != null) {
            this.mContext.unbindService(this.mProxyConnection);
            this.mProxyConnection = null;
        }
        this.mProxyService = null;
        this.mLastPort = -1;
    }

    private void sendPacBroadcast(android.net.ProxyInfo proxyInfo) {
        int beginBroadcast = this.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            android.net.IPacProxyInstalledListener broadcastItem = this.mCallbacks.getBroadcastItem(i);
            if (broadcastItem != null) {
                try {
                    broadcastItem.onPacProxyInstalled((android.net.Network) null, proxyInfo);
                } catch (android.os.RemoteException e) {
                }
            }
        }
        this.mCallbacks.finishBroadcast();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendProxyIfNeeded() {
        synchronized (this.mBroadcastStateLock) {
            try {
                if (!this.mHasDownloaded || this.mLastPort == -1) {
                    return;
                }
                if (!this.mHasSentBroadcast) {
                    sendPacBroadcast(android.net.ProxyInfo.buildPacProxy(this.mPacUrl, this.mLastPort));
                    this.mHasSentBroadcast = true;
                }
            } finally {
            }
        }
    }
}
