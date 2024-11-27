package com.android.server.adb;

/* loaded from: classes.dex */
public class AdbDebuggingManager {
    private static final java.lang.String ADBD_SOCKET = "adbd";
    private static final java.lang.String ADB_DIRECTORY = "misc/adb";
    private static final java.lang.String ADB_KEYS_FILE = "adb_keys";
    private static final java.lang.String ADB_TEMP_KEYS_FILE = "adb_temp_keys.xml";
    private static final int BUFFER_SIZE = 65536;
    private static final boolean DEBUG = false;
    private static final boolean MDNS_DEBUG = false;
    private static final int PAIRING_CODE_LENGTH = 6;
    private static final java.lang.String WIFI_PERSISTENT_CONFIG_PROPERTY = "persist.adb.tls_server.enable";
    private static final java.lang.String WIFI_PERSISTENT_GUID = "persist.adb.wifi.guid";
    private com.android.server.adb.AdbDebuggingManager.AdbConnectionInfo mAdbConnectionInfo;
    private boolean mAdbUsbEnabled;
    private boolean mAdbWifiEnabled;
    private final java.lang.String mConfirmComponent;
    private final java.util.Map<java.lang.String, java.lang.Integer> mConnectedKeys;
    private com.android.server.adb.AdbDebuggingManager.AdbConnectionPortPoller mConnectionPortPoller;
    private final android.content.ContentResolver mContentResolver;
    private final android.content.Context mContext;
    private java.lang.String mFingerprints;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.adb.AdbDebuggingManager.AdbDebuggingHandler mHandler;
    private com.android.server.adb.AdbDebuggingManager.PairingThread mPairingThread;
    private final com.android.server.adb.AdbDebuggingManager.PortListenerImpl mPortListener;

    @android.annotation.Nullable
    private final java.io.File mTempKeysFile;

    @android.annotation.Nullable
    private com.android.server.adb.AdbDebuggingManager.AdbDebuggingThread mThread;
    private final com.android.server.adb.AdbDebuggingManager.Ticker mTicker;

    @android.annotation.Nullable
    private final java.io.File mUserKeyFile;
    private final java.util.Set<java.lang.String> mWifiConnectedKeys;
    private static final java.lang.String TAG = com.android.server.adb.AdbDebuggingManager.class.getSimpleName();
    private static final com.android.server.adb.AdbDebuggingManager.Ticker SYSTEM_TICKER = new com.android.server.adb.AdbDebuggingManager.Ticker() { // from class: com.android.server.adb.AdbDebuggingManager$$ExternalSyntheticLambda0
        @Override // com.android.server.adb.AdbDebuggingManager.Ticker
        public final long currentTimeMillis() {
            long currentTimeMillis;
            currentTimeMillis = java.lang.System.currentTimeMillis();
            return currentTimeMillis;
        }
    };
    private static final long ADBD_STATE_CHANGE_TIMEOUT = android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;

    interface AdbConnectionPortListener {
        void onPortReceived(int i);
    }

    @com.android.internal.annotations.VisibleForTesting
    interface Ticker {
        long currentTimeMillis();
    }

    public AdbDebuggingManager(android.content.Context context) {
        this(context, null, getAdbFile(ADB_KEYS_FILE), getAdbFile(ADB_TEMP_KEYS_FILE), null, SYSTEM_TICKER);
    }

    @com.android.internal.annotations.VisibleForTesting
    AdbDebuggingManager(android.content.Context context, java.lang.String str, java.io.File file, java.io.File file2, com.android.server.adb.AdbDebuggingManager.AdbDebuggingThread adbDebuggingThread, com.android.server.adb.AdbDebuggingManager.Ticker ticker) {
        this.mAdbUsbEnabled = false;
        this.mAdbWifiEnabled = false;
        this.mConnectedKeys = new java.util.HashMap();
        this.mPairingThread = null;
        this.mWifiConnectedKeys = new java.util.HashSet();
        this.mAdbConnectionInfo = new com.android.server.adb.AdbDebuggingManager.AdbConnectionInfo();
        this.mPortListener = new com.android.server.adb.AdbDebuggingManager.PortListenerImpl();
        this.mContext = context;
        this.mContentResolver = this.mContext.getContentResolver();
        this.mConfirmComponent = str;
        this.mUserKeyFile = file;
        this.mTempKeysFile = file2;
        this.mThread = adbDebuggingThread;
        this.mTicker = ticker;
        this.mHandler = new com.android.server.adb.AdbDebuggingManager.AdbDebuggingHandler(com.android.server.FgThread.get().getLooper(), this.mThread);
    }

    static void sendBroadcastWithDebugPermission(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull android.os.UserHandle userHandle) {
        context.sendBroadcastAsUser(intent, userHandle, "android.permission.MANAGE_DEBUGGING");
    }

    class PairingThread extends java.lang.Thread implements android.net.nsd.NsdManager.RegistrationListener {

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String SERVICE_PROTOCOL = "adb-tls-pairing";
        private java.lang.String mGuid;
        private android.net.nsd.NsdManager mNsdManager;
        private java.lang.String mPairingCode;
        private int mPort;
        private java.lang.String mPublicKey;
        private java.lang.String mServiceName;
        private final java.lang.String mServiceType;

        private native void native_pairing_cancel();

        private native int native_pairing_start(java.lang.String str, java.lang.String str2);

        private native boolean native_pairing_wait();

        PairingThread(java.lang.String str, java.lang.String str2) {
            super(com.android.server.adb.AdbDebuggingManager.TAG);
            this.mServiceType = java.lang.String.format("_%s._tcp.", SERVICE_PROTOCOL);
            this.mPairingCode = str;
            this.mGuid = android.os.SystemProperties.get(com.android.server.adb.AdbDebuggingManager.WIFI_PERSISTENT_GUID);
            this.mServiceName = str2;
            if (str2 == null || str2.isEmpty()) {
                this.mServiceName = this.mGuid;
            }
            this.mPort = -1;
            this.mNsdManager = (android.net.nsd.NsdManager) com.android.server.adb.AdbDebuggingManager.this.mContext.getSystemService("servicediscovery");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            android.net.nsd.NsdServiceInfo nsdServiceInfo = new android.net.nsd.NsdServiceInfo();
            nsdServiceInfo.setServiceName(this.mServiceName);
            nsdServiceInfo.setServiceType(this.mServiceType);
            nsdServiceInfo.setPort(this.mPort);
            this.mNsdManager.registerService(nsdServiceInfo, 1, this);
            android.os.Message obtainMessage = com.android.server.adb.AdbDebuggingManager.this.mHandler.obtainMessage(21);
            obtainMessage.obj = java.lang.Integer.valueOf(this.mPort);
            com.android.server.adb.AdbDebuggingManager.this.mHandler.sendMessage(obtainMessage);
            boolean native_pairing_wait = native_pairing_wait();
            this.mNsdManager.unregisterService(this);
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString("publicKey", native_pairing_wait ? this.mPublicKey : null);
            com.android.server.adb.AdbDebuggingManager.this.mHandler.sendMessage(android.os.Message.obtain(com.android.server.adb.AdbDebuggingManager.this.mHandler, 20, bundle));
        }

        @Override // java.lang.Thread
        public void start() {
            if (this.mGuid.isEmpty()) {
                android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "adbwifi guid was not set");
                return;
            }
            this.mPort = native_pairing_start(this.mGuid, this.mPairingCode);
            if (this.mPort <= 0) {
                android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Unable to start pairing server");
            } else {
                super.start();
            }
        }

        public void cancelPairing() {
            native_pairing_cancel();
        }

        @Override // android.net.nsd.NsdManager.RegistrationListener
        public void onServiceRegistered(android.net.nsd.NsdServiceInfo nsdServiceInfo) {
        }

        @Override // android.net.nsd.NsdManager.RegistrationListener
        public void onRegistrationFailed(android.net.nsd.NsdServiceInfo nsdServiceInfo, int i) {
            android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Failed to register pairing service(err=" + i + "): " + nsdServiceInfo);
            cancelPairing();
        }

        @Override // android.net.nsd.NsdManager.RegistrationListener
        public void onServiceUnregistered(android.net.nsd.NsdServiceInfo nsdServiceInfo) {
        }

        @Override // android.net.nsd.NsdManager.RegistrationListener
        public void onUnregistrationFailed(android.net.nsd.NsdServiceInfo nsdServiceInfo, int i) {
            android.util.Slog.w(com.android.server.adb.AdbDebuggingManager.TAG, "Failed to unregister pairing service(err=" + i + "): " + nsdServiceInfo);
        }
    }

    static class AdbConnectionPortPoller extends java.lang.Thread {
        private com.android.server.adb.AdbDebuggingManager.AdbConnectionPortListener mListener;
        private final java.lang.String mAdbPortProp = "service.adb.tls.port";
        private final int mDurationSecs = 10;
        private java.util.concurrent.atomic.AtomicBoolean mCanceled = new java.util.concurrent.atomic.AtomicBoolean(false);

        AdbConnectionPortPoller(com.android.server.adb.AdbDebuggingManager.AdbConnectionPortListener adbConnectionPortListener) {
            this.mListener = adbConnectionPortListener;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            for (int i = 0; i < 10; i++) {
                if (this.mCanceled.get()) {
                    return;
                }
                int i2 = android.os.SystemProperties.getInt("service.adb.tls.port", Integer.MAX_VALUE);
                if (i2 == -1 || (i2 > 0 && i2 <= 65535)) {
                    this.mListener.onPortReceived(i2);
                    return;
                }
                android.os.SystemClock.sleep(1000L);
            }
            android.util.Slog.w(com.android.server.adb.AdbDebuggingManager.TAG, "Failed to receive adb connection port");
            this.mListener.onPortReceived(-1);
        }

        public void cancelAndWait() {
            this.mCanceled.set(true);
            if (isAlive()) {
                try {
                    join();
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
    }

    class PortListenerImpl implements com.android.server.adb.AdbDebuggingManager.AdbConnectionPortListener {
        PortListenerImpl() {
        }

        @Override // com.android.server.adb.AdbDebuggingManager.AdbConnectionPortListener
        public void onPortReceived(int i) {
            int i2;
            com.android.server.adb.AdbDebuggingManager.AdbDebuggingHandler adbDebuggingHandler = com.android.server.adb.AdbDebuggingManager.this.mHandler;
            if (i > 0) {
                i2 = 24;
            } else {
                i2 = 25;
            }
            android.os.Message obtainMessage = adbDebuggingHandler.obtainMessage(i2);
            obtainMessage.obj = java.lang.Integer.valueOf(i);
            com.android.server.adb.AdbDebuggingManager.this.mHandler.sendMessage(obtainMessage);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class AdbDebuggingThread extends java.lang.Thread {
        private android.os.Handler mHandler;
        private java.io.InputStream mInputStream;
        private java.io.OutputStream mOutputStream;
        private android.net.LocalSocket mSocket;
        private boolean mStopped;

        @com.android.internal.annotations.VisibleForTesting
        AdbDebuggingThread() {
            super(com.android.server.adb.AdbDebuggingManager.TAG);
        }

        @com.android.internal.annotations.VisibleForTesting
        void setHandler(android.os.Handler handler) {
            this.mHandler = handler;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                synchronized (this) {
                    if (this.mStopped) {
                        return;
                    }
                    try {
                        openSocketLocked();
                    } catch (java.lang.Exception e) {
                        android.os.SystemClock.sleep(1000L);
                    }
                }
                try {
                    listenToSocket();
                } catch (java.lang.Exception e2) {
                    android.os.SystemClock.sleep(1000L);
                }
            }
        }

        private void openSocketLocked() throws java.io.IOException {
            try {
                android.net.LocalSocketAddress localSocketAddress = new android.net.LocalSocketAddress(com.android.server.adb.AdbDebuggingManager.ADBD_SOCKET, android.net.LocalSocketAddress.Namespace.RESERVED);
                this.mInputStream = null;
                this.mSocket = new android.net.LocalSocket(3);
                this.mSocket.connect(localSocketAddress);
                this.mOutputStream = this.mSocket.getOutputStream();
                this.mInputStream = this.mSocket.getInputStream();
                this.mHandler.sendEmptyMessage(26);
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Caught an exception opening the socket: " + e);
                closeSocketLocked();
                throw e;
            }
        }

        private void listenToSocket() throws java.io.IOException {
            try {
                byte[] bArr = new byte[65536];
                while (true) {
                    int read = this.mInputStream.read(bArr);
                    if (read < 2) {
                        android.util.Slog.w(com.android.server.adb.AdbDebuggingManager.TAG, "Read failed with count " + read);
                        break;
                    }
                    if (bArr[0] == 80 && bArr[1] == 75) {
                        java.lang.String str = new java.lang.String(java.util.Arrays.copyOfRange(bArr, 2, read));
                        android.util.Slog.d(com.android.server.adb.AdbDebuggingManager.TAG, "Received public key: " + str);
                        android.os.Message obtainMessage = this.mHandler.obtainMessage(5);
                        obtainMessage.obj = str;
                        this.mHandler.sendMessage(obtainMessage);
                    } else if (bArr[0] == 68 && bArr[1] == 67) {
                        java.lang.String str2 = new java.lang.String(java.util.Arrays.copyOfRange(bArr, 2, read));
                        android.util.Slog.d(com.android.server.adb.AdbDebuggingManager.TAG, "Received disconnected message: " + str2);
                        android.os.Message obtainMessage2 = this.mHandler.obtainMessage(7);
                        obtainMessage2.obj = str2;
                        this.mHandler.sendMessage(obtainMessage2);
                    } else if (bArr[0] == 67 && bArr[1] == 75) {
                        java.lang.String str3 = new java.lang.String(java.util.Arrays.copyOfRange(bArr, 2, read));
                        android.util.Slog.d(com.android.server.adb.AdbDebuggingManager.TAG, "Received connected key message: " + str3);
                        android.os.Message obtainMessage3 = this.mHandler.obtainMessage(10);
                        obtainMessage3.obj = str3;
                        this.mHandler.sendMessage(obtainMessage3);
                    } else if (bArr[0] == 87 && bArr[1] == 69) {
                        byte b = bArr[2];
                        java.lang.String str4 = new java.lang.String(java.util.Arrays.copyOfRange(bArr, 3, read));
                        if (b == 0) {
                            android.util.Slog.d(com.android.server.adb.AdbDebuggingManager.TAG, "Received USB TLS connected key message: " + str4);
                            android.os.Message obtainMessage4 = this.mHandler.obtainMessage(10);
                            obtainMessage4.obj = str4;
                            this.mHandler.sendMessage(obtainMessage4);
                        } else if (b == 1) {
                            android.util.Slog.d(com.android.server.adb.AdbDebuggingManager.TAG, "Received WIFI TLS connected key message: " + str4);
                            android.os.Message obtainMessage5 = this.mHandler.obtainMessage(22);
                            obtainMessage5.obj = str4;
                            this.mHandler.sendMessage(obtainMessage5);
                        } else {
                            android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Got unknown transport type from adbd (" + ((int) b) + ")");
                        }
                    } else {
                        if (bArr[0] != 87 || bArr[1] != 70) {
                            break;
                        }
                        byte b2 = bArr[2];
                        java.lang.String str5 = new java.lang.String(java.util.Arrays.copyOfRange(bArr, 3, read));
                        if (b2 == 0) {
                            android.util.Slog.d(com.android.server.adb.AdbDebuggingManager.TAG, "Received USB TLS disconnect message: " + str5);
                            android.os.Message obtainMessage6 = this.mHandler.obtainMessage(7);
                            obtainMessage6.obj = str5;
                            this.mHandler.sendMessage(obtainMessage6);
                        } else if (b2 == 1) {
                            android.util.Slog.d(com.android.server.adb.AdbDebuggingManager.TAG, "Received WIFI TLS disconnect key message: " + str5);
                            android.os.Message obtainMessage7 = this.mHandler.obtainMessage(23);
                            obtainMessage7.obj = str5;
                            this.mHandler.sendMessage(obtainMessage7);
                        } else {
                            android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Got unknown transport type from adbd (" + ((int) b2) + ")");
                        }
                    }
                }
                android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Wrong message: " + new java.lang.String(java.util.Arrays.copyOfRange(bArr, 0, 2)));
                synchronized (this) {
                    closeSocketLocked();
                }
            } catch (java.lang.Throwable th) {
                synchronized (this) {
                    closeSocketLocked();
                    throw th;
                }
            }
        }

        private void closeSocketLocked() {
            try {
                if (this.mOutputStream != null) {
                    this.mOutputStream.close();
                    this.mOutputStream = null;
                }
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Failed closing output stream: " + e);
            }
            try {
                if (this.mSocket != null) {
                    this.mSocket.close();
                    this.mSocket = null;
                }
            } catch (java.io.IOException e2) {
                android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Failed closing socket: " + e2);
            }
            this.mHandler.sendEmptyMessage(27);
        }

        void stopListening() {
            synchronized (this) {
                this.mStopped = true;
                closeSocketLocked();
            }
        }

        void sendResponse(java.lang.String str) {
            synchronized (this) {
                if (!this.mStopped && this.mOutputStream != null) {
                    try {
                        this.mOutputStream.write(str.getBytes());
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Failed to write response:", e);
                    }
                }
            }
        }
    }

    private static class AdbConnectionInfo {
        private java.lang.String mBssid;
        private int mPort;
        private java.lang.String mSsid;

        AdbConnectionInfo() {
            this.mBssid = "";
            this.mSsid = "";
            this.mPort = -1;
        }

        AdbConnectionInfo(java.lang.String str, java.lang.String str2) {
            this.mBssid = str;
            this.mSsid = str2;
        }

        AdbConnectionInfo(com.android.server.adb.AdbDebuggingManager.AdbConnectionInfo adbConnectionInfo) {
            this.mBssid = adbConnectionInfo.mBssid;
            this.mSsid = adbConnectionInfo.mSsid;
            this.mPort = adbConnectionInfo.mPort;
        }

        public java.lang.String getBSSID() {
            return this.mBssid;
        }

        public java.lang.String getSSID() {
            return this.mSsid;
        }

        public int getPort() {
            return this.mPort;
        }

        public void setPort(int i) {
            this.mPort = i;
        }

        public void clear() {
            this.mBssid = "";
            this.mSsid = "";
            this.mPort = -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAdbConnectionInfo(com.android.server.adb.AdbDebuggingManager.AdbConnectionInfo adbConnectionInfo) {
        synchronized (this.mAdbConnectionInfo) {
            try {
                if (adbConnectionInfo == null) {
                    this.mAdbConnectionInfo.clear();
                } else {
                    this.mAdbConnectionInfo = adbConnectionInfo;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private com.android.server.adb.AdbDebuggingManager.AdbConnectionInfo getAdbConnectionInfo() {
        com.android.server.adb.AdbDebuggingManager.AdbConnectionInfo adbConnectionInfo;
        synchronized (this.mAdbConnectionInfo) {
            adbConnectionInfo = new com.android.server.adb.AdbDebuggingManager.AdbConnectionInfo(this.mAdbConnectionInfo);
        }
        return adbConnectionInfo;
    }

    class AdbDebuggingHandler extends android.os.Handler {
        private static final java.lang.String ADB_NOTIFICATION_CHANNEL_ID_TV = "usbdevicemanager.adb.tv";
        static final int MESSAGE_ADB_ALLOW = 3;
        static final int MESSAGE_ADB_CLEAR = 6;
        static final int MESSAGE_ADB_CONFIRM = 5;
        static final int MESSAGE_ADB_CONNECTED_KEY = 10;
        static final int MESSAGE_ADB_DENY = 4;
        static final int MESSAGE_ADB_DISABLED = 2;
        static final int MESSAGE_ADB_DISCONNECT = 7;
        static final int MESSAGE_ADB_ENABLED = 1;
        static final int MESSAGE_ADB_PERSIST_KEYSTORE = 8;
        static final int MESSAGE_ADB_UPDATE_KEYSTORE = 9;
        private static final int MESSAGE_KEY_FILES_UPDATED = 28;
        static final int MSG_ADBDWIFI_DISABLE = 12;
        static final int MSG_ADBDWIFI_ENABLE = 11;
        static final int MSG_ADBD_SOCKET_CONNECTED = 26;
        static final int MSG_ADBD_SOCKET_DISCONNECTED = 27;
        static final int MSG_ADBWIFI_ALLOW = 18;
        static final int MSG_ADBWIFI_DENY = 19;
        static final java.lang.String MSG_DISABLE_ADBDWIFI = "DA";
        static final java.lang.String MSG_DISCONNECT_DEVICE = "DD";
        static final int MSG_PAIRING_CANCEL = 14;
        static final int MSG_PAIR_PAIRING_CODE = 15;
        static final int MSG_PAIR_QR_CODE = 16;
        static final int MSG_REQ_UNPAIR = 17;
        static final int MSG_RESPONSE_PAIRING_PORT = 21;
        static final int MSG_RESPONSE_PAIRING_RESULT = 20;
        static final int MSG_SERVER_CONNECTED = 24;
        static final int MSG_SERVER_DISCONNECTED = 25;
        static final int MSG_WIFI_DEVICE_CONNECTED = 22;
        static final int MSG_WIFI_DEVICE_DISCONNECTED = 23;
        static final long UPDATE_KEYSTORE_JOB_INTERVAL = 86400000;
        static final long UPDATE_KEYSTORE_MIN_JOB_INTERVAL = 60000;
        private int mAdbEnabledRefCount;

        @com.android.internal.annotations.VisibleForTesting
        @android.annotation.Nullable
        com.android.server.adb.AdbDebuggingManager.AdbKeyStore mAdbKeyStore;
        private boolean mAdbNotificationShown;
        private android.database.ContentObserver mAuthTimeObserver;
        private final android.content.BroadcastReceiver mBroadcastReceiver;
        private android.app.NotificationManager mNotificationManager;

        private boolean isTv() {
            return com.android.server.adb.AdbDebuggingManager.this.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
        }

        private void setupNotifications() {
            if (this.mNotificationManager != null) {
                return;
            }
            this.mNotificationManager = (android.app.NotificationManager) com.android.server.adb.AdbDebuggingManager.this.mContext.getSystemService("notification");
            if (this.mNotificationManager == null) {
                android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Unable to setup notifications for wireless debugging");
            } else if (isTv()) {
                this.mNotificationManager.createNotificationChannel(new android.app.NotificationChannel(ADB_NOTIFICATION_CHANNEL_ID_TV, com.android.server.adb.AdbDebuggingManager.this.mContext.getString(android.R.string.activity_resolver_use_once), 4));
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        AdbDebuggingHandler(android.os.Looper looper, com.android.server.adb.AdbDebuggingManager.AdbDebuggingThread adbDebuggingThread) {
            super(looper);
            this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.adb.AdbDebuggingManager.AdbDebuggingHandler.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    java.lang.String action = intent.getAction();
                    if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                        if (intent.getIntExtra("wifi_state", 1) == 1) {
                            android.util.Slog.i(com.android.server.adb.AdbDebuggingManager.TAG, "Wifi disabled. Disabling adbwifi.");
                            android.provider.Settings.Global.putInt(com.android.server.adb.AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                            return;
                        }
                        return;
                    }
                    if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                        android.net.NetworkInfo networkInfo = (android.net.NetworkInfo) intent.getParcelableExtra("networkInfo", android.net.NetworkInfo.class);
                        if (networkInfo.getType() == 1) {
                            if (!networkInfo.isConnected()) {
                                android.util.Slog.i(com.android.server.adb.AdbDebuggingManager.TAG, "Network disconnected. Disabling adbwifi.");
                                android.provider.Settings.Global.putInt(com.android.server.adb.AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                                return;
                            }
                            android.net.wifi.WifiInfo connectionInfo = ((android.net.wifi.WifiManager) com.android.server.adb.AdbDebuggingManager.this.mContext.getSystemService("wifi")).getConnectionInfo();
                            if (connectionInfo == null || connectionInfo.getNetworkId() == -1) {
                                android.util.Slog.i(com.android.server.adb.AdbDebuggingManager.TAG, "Not connected to any wireless network. Not enabling adbwifi.");
                                android.provider.Settings.Global.putInt(com.android.server.adb.AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                                return;
                            }
                            synchronized (com.android.server.adb.AdbDebuggingManager.this.mAdbConnectionInfo) {
                                try {
                                    java.lang.String bssid = connectionInfo.getBSSID();
                                    if (android.text.TextUtils.isEmpty(bssid)) {
                                        android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Unable to get the wifi ap's BSSID. Disabling adbwifi.");
                                        android.provider.Settings.Global.putInt(com.android.server.adb.AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                                    } else {
                                        if (!android.text.TextUtils.equals(bssid, com.android.server.adb.AdbDebuggingManager.this.mAdbConnectionInfo.getBSSID())) {
                                            android.util.Slog.i(com.android.server.adb.AdbDebuggingManager.TAG, "Detected wifi network change. Disabling adbwifi.");
                                            android.provider.Settings.Global.putInt(com.android.server.adb.AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                                        }
                                    }
                                } finally {
                                }
                            }
                        }
                    }
                }
            };
            this.mAdbEnabledRefCount = 0;
            this.mAuthTimeObserver = new android.database.ContentObserver(this) { // from class: com.android.server.adb.AdbDebuggingManager.AdbDebuggingHandler.2
                @Override // android.database.ContentObserver
                public void onChange(boolean z, android.net.Uri uri) {
                    android.util.Slog.d(com.android.server.adb.AdbDebuggingManager.TAG, "Received notification that uri " + uri + " was modified; rescheduling keystore job");
                    com.android.server.adb.AdbDebuggingManager.AdbDebuggingHandler.this.scheduleJobToUpdateAdbKeyStore();
                }
            };
            com.android.server.adb.AdbDebuggingManager.this.mThread = adbDebuggingThread;
        }

        @com.android.internal.annotations.VisibleForTesting
        void initKeyStore() {
            if (this.mAdbKeyStore == null) {
                this.mAdbKeyStore = com.android.server.adb.AdbDebuggingManager.this.new AdbKeyStore();
            }
        }

        public void showAdbConnectedNotification(boolean z) {
            if (z == this.mAdbNotificationShown) {
                return;
            }
            setupNotifications();
            if (!this.mAdbNotificationShown) {
                android.app.Notification createNotification = android.debug.AdbNotifications.createNotification(com.android.server.adb.AdbDebuggingManager.this.mContext, (byte) 1);
                this.mAdbNotificationShown = true;
                this.mNotificationManager.notifyAsUser(null, 62, createNotification, android.os.UserHandle.ALL);
            } else {
                this.mAdbNotificationShown = false;
                this.mNotificationManager.cancelAsUser(null, 62, android.os.UserHandle.ALL);
            }
        }

        private void startAdbDebuggingThread() {
            this.mAdbEnabledRefCount++;
            if (this.mAdbEnabledRefCount > 1) {
                return;
            }
            registerForAuthTimeChanges();
            com.android.server.adb.AdbDebuggingManager.this.mThread = new com.android.server.adb.AdbDebuggingManager.AdbDebuggingThread();
            com.android.server.adb.AdbDebuggingManager.this.mThread.setHandler(com.android.server.adb.AdbDebuggingManager.this.mHandler);
            com.android.server.adb.AdbDebuggingManager.this.mThread.start();
            this.mAdbKeyStore.updateKeyStore();
            scheduleJobToUpdateAdbKeyStore();
        }

        private void stopAdbDebuggingThread() {
            this.mAdbEnabledRefCount--;
            if (this.mAdbEnabledRefCount > 0) {
                return;
            }
            if (com.android.server.adb.AdbDebuggingManager.this.mThread != null) {
                com.android.server.adb.AdbDebuggingManager.this.mThread.stopListening();
                com.android.server.adb.AdbDebuggingManager.this.mThread = null;
            }
            if (!com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.isEmpty()) {
                java.util.Iterator it = com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.entrySet().iterator();
                while (it.hasNext()) {
                    this.mAdbKeyStore.setLastConnectionTime((java.lang.String) ((java.util.Map.Entry) it.next()).getKey(), com.android.server.adb.AdbDebuggingManager.this.mTicker.currentTimeMillis());
                }
                com.android.server.adb.AdbDebuggingManager.this.sendPersistKeyStoreMessage();
                com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.clear();
                com.android.server.adb.AdbDebuggingManager.this.mWifiConnectedKeys.clear();
            }
            scheduleJobToUpdateAdbKeyStore();
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            initKeyStore();
            switch (message.what) {
                case 1:
                    if (!com.android.server.adb.AdbDebuggingManager.this.mAdbUsbEnabled) {
                        startAdbDebuggingThread();
                        com.android.server.adb.AdbDebuggingManager.this.mAdbUsbEnabled = true;
                        return;
                    }
                    return;
                case 2:
                    if (com.android.server.adb.AdbDebuggingManager.this.mAdbUsbEnabled) {
                        stopAdbDebuggingThread();
                        com.android.server.adb.AdbDebuggingManager.this.mAdbUsbEnabled = false;
                        return;
                    }
                    return;
                case 3:
                    java.lang.String str = (java.lang.String) message.obj;
                    java.lang.String fingerprints = com.android.server.adb.AdbDebuggingManager.this.getFingerprints(str);
                    if (!fingerprints.equals(com.android.server.adb.AdbDebuggingManager.this.mFingerprints)) {
                        android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Fingerprints do not match. Got " + fingerprints + ", expected " + com.android.server.adb.AdbDebuggingManager.this.mFingerprints);
                        return;
                    }
                    r3 = message.arg1 == 1;
                    if (com.android.server.adb.AdbDebuggingManager.this.mThread != null) {
                        com.android.server.adb.AdbDebuggingManager.this.mThread.sendResponse("OK");
                        if (r3) {
                            if (!com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.containsKey(str)) {
                                com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.put(str, 1);
                            }
                            this.mAdbKeyStore.setLastConnectionTime(str, com.android.server.adb.AdbDebuggingManager.this.mTicker.currentTimeMillis());
                            com.android.server.adb.AdbDebuggingManager.this.sendPersistKeyStoreMessage();
                            scheduleJobToUpdateAdbKeyStore();
                        }
                        logAdbConnectionChanged(str, 2, r3);
                        return;
                    }
                    return;
                case 4:
                    if (com.android.server.adb.AdbDebuggingManager.this.mThread != null) {
                        android.util.Slog.w(com.android.server.adb.AdbDebuggingManager.TAG, "Denying adb confirmation");
                        com.android.server.adb.AdbDebuggingManager.this.mThread.sendResponse("NO");
                        logAdbConnectionChanged(null, 3, false);
                        return;
                    }
                    return;
                case 5:
                    java.lang.String str2 = (java.lang.String) message.obj;
                    java.lang.String fingerprints2 = com.android.server.adb.AdbDebuggingManager.this.getFingerprints(str2);
                    if ("".equals(fingerprints2)) {
                        if (com.android.server.adb.AdbDebuggingManager.this.mThread != null) {
                            com.android.server.adb.AdbDebuggingManager.this.mThread.sendResponse("NO");
                            logAdbConnectionChanged(str2, 5, false);
                            return;
                        }
                        return;
                    }
                    logAdbConnectionChanged(str2, 1, false);
                    com.android.server.adb.AdbDebuggingManager.this.mFingerprints = fingerprints2;
                    com.android.server.adb.AdbDebuggingManager.this.startConfirmationForKey(str2, com.android.server.adb.AdbDebuggingManager.this.mFingerprints);
                    return;
                case 6:
                    android.util.Slog.d(com.android.server.adb.AdbDebuggingManager.TAG, "Received a request to clear the adb authorizations");
                    com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.clear();
                    initKeyStore();
                    com.android.server.adb.AdbDebuggingManager.this.mWifiConnectedKeys.clear();
                    this.mAdbKeyStore.deleteKeyStore();
                    cancelJobToUpdateAdbKeyStore();
                    if (android.provider.Settings.Global.getInt(com.android.server.adb.AdbDebuggingManager.this.mContentResolver, "adb_disconnect_sessions_on_revoke", 1) == 1 && com.android.server.adb.AdbDebuggingManager.this.mAdbUsbEnabled) {
                        try {
                            android.os.SystemService.stop(com.android.server.adb.AdbDebuggingManager.ADBD_SOCKET);
                            android.os.SystemService.waitForState(com.android.server.adb.AdbDebuggingManager.ADBD_SOCKET, android.os.SystemService.State.STOPPED, com.android.server.adb.AdbDebuggingManager.ADBD_STATE_CHANGE_TIMEOUT);
                            android.os.SystemService.start(com.android.server.adb.AdbDebuggingManager.ADBD_SOCKET);
                            android.os.SystemService.waitForState(com.android.server.adb.AdbDebuggingManager.ADBD_SOCKET, android.os.SystemService.State.RUNNING, com.android.server.adb.AdbDebuggingManager.ADBD_STATE_CHANGE_TIMEOUT);
                            return;
                        } catch (java.util.concurrent.TimeoutException e) {
                            android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Timeout occurred waiting for adbd to cycle: ", e);
                            android.provider.Settings.Global.putInt(com.android.server.adb.AdbDebuggingManager.this.mContentResolver, "adb_enabled", 0);
                            return;
                        }
                    }
                    return;
                case 7:
                    java.lang.String str3 = (java.lang.String) message.obj;
                    if (str3 != null && str3.length() > 0) {
                        if (com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.containsKey(str3)) {
                            int intValue = ((java.lang.Integer) com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.get(str3)).intValue() - 1;
                            if (intValue == 0) {
                                this.mAdbKeyStore.setLastConnectionTime(str3, com.android.server.adb.AdbDebuggingManager.this.mTicker.currentTimeMillis());
                                com.android.server.adb.AdbDebuggingManager.this.sendPersistKeyStoreMessage();
                                scheduleJobToUpdateAdbKeyStore();
                                com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.remove(str3);
                            } else {
                                com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.put(str3, java.lang.Integer.valueOf(intValue));
                            }
                            r3 = true;
                        }
                    } else {
                        android.util.Slog.w(com.android.server.adb.AdbDebuggingManager.TAG, "Received a disconnected key message with an empty key");
                    }
                    logAdbConnectionChanged(str3, 7, r3);
                    return;
                case 8:
                    if (this.mAdbKeyStore != null) {
                        this.mAdbKeyStore.persistKeyStore();
                        return;
                    }
                    return;
                case 9:
                    if (!com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.isEmpty()) {
                        java.util.Iterator it = com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.entrySet().iterator();
                        while (it.hasNext()) {
                            this.mAdbKeyStore.setLastConnectionTime((java.lang.String) ((java.util.Map.Entry) it.next()).getKey(), com.android.server.adb.AdbDebuggingManager.this.mTicker.currentTimeMillis());
                        }
                        com.android.server.adb.AdbDebuggingManager.this.sendPersistKeyStoreMessage();
                        scheduleJobToUpdateAdbKeyStore();
                        return;
                    }
                    if (!this.mAdbKeyStore.isEmpty()) {
                        this.mAdbKeyStore.updateKeyStore();
                        scheduleJobToUpdateAdbKeyStore();
                        return;
                    }
                    return;
                case 10:
                    java.lang.String str4 = (java.lang.String) message.obj;
                    if (str4 == null || str4.length() == 0) {
                        android.util.Slog.w(com.android.server.adb.AdbDebuggingManager.TAG, "Received a connected key message with an empty key");
                        return;
                    }
                    if (!com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.containsKey(str4)) {
                        com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.put(str4, 1);
                    } else {
                        com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.put(str4, java.lang.Integer.valueOf(((java.lang.Integer) com.android.server.adb.AdbDebuggingManager.this.mConnectedKeys.get(str4)).intValue() + 1));
                    }
                    this.mAdbKeyStore.setLastConnectionTime(str4, com.android.server.adb.AdbDebuggingManager.this.mTicker.currentTimeMillis());
                    com.android.server.adb.AdbDebuggingManager.this.sendPersistKeyStoreMessage();
                    scheduleJobToUpdateAdbKeyStore();
                    logAdbConnectionChanged(str4, 4, true);
                    return;
                case 11:
                    if (!com.android.server.adb.AdbDebuggingManager.this.mAdbWifiEnabled) {
                        com.android.server.adb.AdbDebuggingManager.AdbConnectionInfo currentWifiApInfo = getCurrentWifiApInfo();
                        if (currentWifiApInfo == null) {
                            android.provider.Settings.Global.putInt(com.android.server.adb.AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                            return;
                        }
                        if (!verifyWifiNetwork(currentWifiApInfo.getBSSID(), currentWifiApInfo.getSSID())) {
                            android.provider.Settings.Global.putInt(com.android.server.adb.AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                            return;
                        }
                        com.android.server.adb.AdbDebuggingManager.this.setAdbConnectionInfo(currentWifiApInfo);
                        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
                        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
                        com.android.server.adb.AdbDebuggingManager.this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
                        android.os.SystemProperties.set(com.android.server.adb.AdbDebuggingManager.WIFI_PERSISTENT_CONFIG_PROPERTY, "1");
                        com.android.server.adb.AdbDebuggingManager.this.mConnectionPortPoller = new com.android.server.adb.AdbDebuggingManager.AdbConnectionPortPoller(com.android.server.adb.AdbDebuggingManager.this.mPortListener);
                        com.android.server.adb.AdbDebuggingManager.this.mConnectionPortPoller.start();
                        startAdbDebuggingThread();
                        com.android.server.adb.AdbDebuggingManager.this.mAdbWifiEnabled = true;
                        return;
                    }
                    return;
                case 12:
                    if (com.android.server.adb.AdbDebuggingManager.this.mAdbWifiEnabled) {
                        com.android.server.adb.AdbDebuggingManager.this.mAdbWifiEnabled = false;
                        com.android.server.adb.AdbDebuggingManager.this.setAdbConnectionInfo(null);
                        com.android.server.adb.AdbDebuggingManager.this.mContext.unregisterReceiver(this.mBroadcastReceiver);
                        if (com.android.server.adb.AdbDebuggingManager.this.mThread != null) {
                            com.android.server.adb.AdbDebuggingManager.this.mThread.sendResponse(MSG_DISABLE_ADBDWIFI);
                        }
                        onAdbdWifiServerDisconnected(-1);
                        stopAdbDebuggingThread();
                        return;
                    }
                    return;
                case 13:
                default:
                    return;
                case 14:
                    if (com.android.server.adb.AdbDebuggingManager.this.mPairingThread != null) {
                        com.android.server.adb.AdbDebuggingManager.this.mPairingThread.cancelPairing();
                        try {
                            com.android.server.adb.AdbDebuggingManager.this.mPairingThread.join();
                        } catch (java.lang.InterruptedException e2) {
                            android.util.Slog.w(com.android.server.adb.AdbDebuggingManager.TAG, "Error while waiting for pairing thread to quit.");
                            e2.printStackTrace();
                        }
                        com.android.server.adb.AdbDebuggingManager.this.mPairingThread = null;
                        return;
                    }
                    return;
                case 15:
                    java.lang.String createPairingCode = createPairingCode(6);
                    updateUIPairCode(createPairingCode);
                    com.android.server.adb.AdbDebuggingManager.this.mPairingThread = com.android.server.adb.AdbDebuggingManager.this.new PairingThread(createPairingCode, null);
                    com.android.server.adb.AdbDebuggingManager.this.mPairingThread.start();
                    return;
                case 16:
                    android.os.Bundle bundle = (android.os.Bundle) message.obj;
                    com.android.server.adb.AdbDebuggingManager.this.mPairingThread = com.android.server.adb.AdbDebuggingManager.this.new PairingThread(bundle.getString(com.android.server.autofill.HintsHelper.AUTOFILL_HINT_PASSWORD), bundle.getString("serviceName"));
                    com.android.server.adb.AdbDebuggingManager.this.mPairingThread.start();
                    return;
                case 17:
                    java.lang.String str5 = (java.lang.String) message.obj;
                    java.lang.String findKeyFromFingerprint = this.mAdbKeyStore.findKeyFromFingerprint(str5);
                    if (findKeyFromFingerprint == null || findKeyFromFingerprint.isEmpty()) {
                        android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Not a known fingerprint [" + str5 + "]");
                        return;
                    }
                    java.lang.String str6 = MSG_DISCONNECT_DEVICE + findKeyFromFingerprint;
                    if (com.android.server.adb.AdbDebuggingManager.this.mThread != null) {
                        com.android.server.adb.AdbDebuggingManager.this.mThread.sendResponse(str6);
                    }
                    this.mAdbKeyStore.removeKey(findKeyFromFingerprint);
                    sendPairedDevicesToUI(this.mAdbKeyStore.getPairedDevices());
                    return;
                case 18:
                    if (!com.android.server.adb.AdbDebuggingManager.this.mAdbWifiEnabled) {
                        java.lang.String str7 = (java.lang.String) message.obj;
                        if (message.arg1 == 1) {
                            this.mAdbKeyStore.addTrustedNetwork(str7);
                        }
                        com.android.server.adb.AdbDebuggingManager.AdbConnectionInfo currentWifiApInfo2 = getCurrentWifiApInfo();
                        if (currentWifiApInfo2 != null && str7.equals(currentWifiApInfo2.getBSSID())) {
                            com.android.server.adb.AdbDebuggingManager.this.setAdbConnectionInfo(currentWifiApInfo2);
                            android.provider.Settings.Global.putInt(com.android.server.adb.AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 1);
                            android.content.IntentFilter intentFilter2 = new android.content.IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
                            intentFilter2.addAction("android.net.wifi.STATE_CHANGE");
                            com.android.server.adb.AdbDebuggingManager.this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter2);
                            android.os.SystemProperties.set(com.android.server.adb.AdbDebuggingManager.WIFI_PERSISTENT_CONFIG_PROPERTY, "1");
                            com.android.server.adb.AdbDebuggingManager.this.mConnectionPortPoller = new com.android.server.adb.AdbDebuggingManager.AdbConnectionPortPoller(com.android.server.adb.AdbDebuggingManager.this.mPortListener);
                            com.android.server.adb.AdbDebuggingManager.this.mConnectionPortPoller.start();
                            startAdbDebuggingThread();
                            com.android.server.adb.AdbDebuggingManager.this.mAdbWifiEnabled = true;
                            return;
                        }
                        return;
                    }
                    return;
                case 19:
                    android.provider.Settings.Global.putInt(com.android.server.adb.AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                    sendServerConnectionState(false, -1);
                    return;
                case 20:
                    onPairingResult(((android.os.Bundle) message.obj).getString("publicKey"));
                    sendPairedDevicesToUI(this.mAdbKeyStore.getPairedDevices());
                    return;
                case 21:
                    sendPairingPortToUI(((java.lang.Integer) message.obj).intValue());
                    return;
                case 22:
                    if (com.android.server.adb.AdbDebuggingManager.this.mWifiConnectedKeys.add((java.lang.String) message.obj)) {
                        sendPairedDevicesToUI(this.mAdbKeyStore.getPairedDevices());
                        showAdbConnectedNotification(true);
                        return;
                    }
                    return;
                case 23:
                    if (com.android.server.adb.AdbDebuggingManager.this.mWifiConnectedKeys.remove((java.lang.String) message.obj)) {
                        sendPairedDevicesToUI(this.mAdbKeyStore.getPairedDevices());
                        if (com.android.server.adb.AdbDebuggingManager.this.mWifiConnectedKeys.isEmpty()) {
                            showAdbConnectedNotification(false);
                            return;
                        }
                        return;
                    }
                    return;
                case 24:
                    int intValue2 = ((java.lang.Integer) message.obj).intValue();
                    onAdbdWifiServerConnected(intValue2);
                    synchronized (com.android.server.adb.AdbDebuggingManager.this.mAdbConnectionInfo) {
                        com.android.server.adb.AdbDebuggingManager.this.mAdbConnectionInfo.setPort(intValue2);
                    }
                    android.provider.Settings.Global.putInt(com.android.server.adb.AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 1);
                    return;
                case 25:
                    if (com.android.server.adb.AdbDebuggingManager.this.mAdbWifiEnabled) {
                        onAdbdWifiServerDisconnected(((java.lang.Integer) message.obj).intValue());
                        android.provider.Settings.Global.putInt(com.android.server.adb.AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                        stopAdbDebuggingThread();
                        if (com.android.server.adb.AdbDebuggingManager.this.mConnectionPortPoller != null) {
                            com.android.server.adb.AdbDebuggingManager.this.mConnectionPortPoller.cancelAndWait();
                            com.android.server.adb.AdbDebuggingManager.this.mConnectionPortPoller = null;
                            return;
                        }
                        return;
                    }
                    return;
                case 26:
                    if (com.android.server.adb.AdbDebuggingManager.this.mAdbWifiEnabled) {
                        com.android.server.adb.AdbDebuggingManager.this.mConnectionPortPoller = new com.android.server.adb.AdbDebuggingManager.AdbConnectionPortPoller(com.android.server.adb.AdbDebuggingManager.this.mPortListener);
                        com.android.server.adb.AdbDebuggingManager.this.mConnectionPortPoller.start();
                        return;
                    }
                    return;
                case 27:
                    if (com.android.server.adb.AdbDebuggingManager.this.mConnectionPortPoller != null) {
                        com.android.server.adb.AdbDebuggingManager.this.mConnectionPortPoller.cancelAndWait();
                        com.android.server.adb.AdbDebuggingManager.this.mConnectionPortPoller = null;
                    }
                    if (com.android.server.adb.AdbDebuggingManager.this.mAdbWifiEnabled) {
                        onAdbdWifiServerDisconnected(-1);
                        return;
                    }
                    return;
                case 28:
                    this.mAdbKeyStore.reloadKeyMap();
                    return;
            }
        }

        void registerForAuthTimeChanges() {
            com.android.server.adb.AdbDebuggingManager.this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("adb_allowed_connection_time"), false, this.mAuthTimeObserver);
        }

        private void logAdbConnectionChanged(java.lang.String str, int i, boolean z) {
            long lastConnectionTime = this.mAdbKeyStore.getLastConnectionTime(str);
            long allowedConnectionTime = this.mAdbKeyStore.getAllowedConnectionTime();
            android.util.Slog.d(com.android.server.adb.AdbDebuggingManager.TAG, "Logging key " + str + ", state = " + i + ", alwaysAllow = " + z + ", lastConnectionTime = " + lastConnectionTime + ", authWindow = " + allowedConnectionTime);
            com.android.internal.util.FrameworkStatsLog.write(144, lastConnectionTime, allowedConnectionTime, i, z);
        }

        @com.android.internal.annotations.VisibleForTesting
        long scheduleJobToUpdateAdbKeyStore() {
            cancelJobToUpdateAdbKeyStore();
            long nextExpirationTime = this.mAdbKeyStore.getNextExpirationTime();
            if (nextExpirationTime == -1) {
                return -1L;
            }
            long j = 0;
            if (nextExpirationTime != 0) {
                j = java.lang.Math.max(java.lang.Math.min(86400000L, nextExpirationTime), 60000L);
            }
            sendMessageDelayed(obtainMessage(9), j);
            return j;
        }

        private void cancelJobToUpdateAdbKeyStore() {
            removeMessages(9);
        }

        private java.lang.String createPairingCode(int i) {
            java.security.SecureRandom secureRandom = new java.security.SecureRandom();
            java.lang.String str = "";
            for (int i2 = 0; i2 < i; i2++) {
                str = str + secureRandom.nextInt(10);
            }
            return str;
        }

        private void sendServerConnectionState(boolean z, int i) {
            int i2;
            android.content.Intent intent = new android.content.Intent("com.android.server.adb.WIRELESS_DEBUG_STATUS");
            if (z) {
                i2 = 4;
            } else {
                i2 = 5;
            }
            intent.putExtra("status", i2);
            intent.putExtra("adb_port", i);
            com.android.server.adb.AdbDebuggingManager.sendBroadcastWithDebugPermission(com.android.server.adb.AdbDebuggingManager.this.mContext, intent, android.os.UserHandle.ALL);
        }

        private void onAdbdWifiServerConnected(int i) {
            sendPairedDevicesToUI(this.mAdbKeyStore.getPairedDevices());
            sendServerConnectionState(true, i);
        }

        private void onAdbdWifiServerDisconnected(int i) {
            com.android.server.adb.AdbDebuggingManager.this.mWifiConnectedKeys.clear();
            showAdbConnectedNotification(false);
            sendServerConnectionState(false, i);
        }

        private com.android.server.adb.AdbDebuggingManager.AdbConnectionInfo getCurrentWifiApInfo() {
            java.lang.String passpointProviderFriendlyName;
            android.net.wifi.WifiManager wifiManager = (android.net.wifi.WifiManager) com.android.server.adb.AdbDebuggingManager.this.mContext.getSystemService("wifi");
            android.net.wifi.WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo == null || connectionInfo.getNetworkId() == -1) {
                android.util.Slog.i(com.android.server.adb.AdbDebuggingManager.TAG, "Not connected to any wireless network. Not enabling adbwifi.");
                return null;
            }
            if (connectionInfo.isPasspointAp() || connectionInfo.isOsuAp()) {
                passpointProviderFriendlyName = connectionInfo.getPasspointProviderFriendlyName();
            } else {
                passpointProviderFriendlyName = connectionInfo.getSSID();
                if (passpointProviderFriendlyName == null || "<unknown ssid>".equals(passpointProviderFriendlyName)) {
                    java.util.List<android.net.wifi.WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
                    int size = configuredNetworks.size();
                    for (int i = 0; i < size; i++) {
                        if (configuredNetworks.get(i).networkId == connectionInfo.getNetworkId()) {
                            passpointProviderFriendlyName = configuredNetworks.get(i).SSID;
                        }
                    }
                    if (passpointProviderFriendlyName == null) {
                        android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Unable to get ssid of the wifi AP.");
                        return null;
                    }
                }
            }
            java.lang.String bssid = connectionInfo.getBSSID();
            if (android.text.TextUtils.isEmpty(bssid)) {
                android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Unable to get the wifi ap's BSSID.");
                return null;
            }
            return new com.android.server.adb.AdbDebuggingManager.AdbConnectionInfo(bssid, passpointProviderFriendlyName);
        }

        private boolean verifyWifiNetwork(java.lang.String str, java.lang.String str2) {
            if (this.mAdbKeyStore.isTrustedNetwork(str)) {
                return true;
            }
            com.android.server.adb.AdbDebuggingManager.this.startConfirmationForNetwork(str2, str);
            return false;
        }

        private void onPairingResult(java.lang.String str) {
            java.lang.String str2;
            if (str == null) {
                android.content.Intent intent = new android.content.Intent("com.android.server.adb.WIRELESS_DEBUG_PAIRING_RESULT");
                intent.putExtra("status", 0);
                com.android.server.adb.AdbDebuggingManager.sendBroadcastWithDebugPermission(com.android.server.adb.AdbDebuggingManager.this.mContext, intent, android.os.UserHandle.ALL);
                return;
            }
            android.content.Intent intent2 = new android.content.Intent("com.android.server.adb.WIRELESS_DEBUG_PAIRING_RESULT");
            intent2.putExtra("status", 1);
            java.lang.String fingerprints = com.android.server.adb.AdbDebuggingManager.this.getFingerprints(str);
            java.lang.String[] split = str.split("\\s+");
            if (split.length <= 1) {
                str2 = "nouser@nohostname";
            } else {
                str2 = split[1];
            }
            android.debug.PairDevice pairDevice = new android.debug.PairDevice();
            pairDevice.name = fingerprints;
            pairDevice.guid = str2;
            pairDevice.connected = false;
            intent2.putExtra("pair_device", (android.os.Parcelable) pairDevice);
            com.android.server.adb.AdbDebuggingManager.sendBroadcastWithDebugPermission(com.android.server.adb.AdbDebuggingManager.this.mContext, intent2, android.os.UserHandle.ALL);
            this.mAdbKeyStore.setLastConnectionTime(str, com.android.server.adb.AdbDebuggingManager.this.mTicker.currentTimeMillis());
            com.android.server.adb.AdbDebuggingManager.this.sendPersistKeyStoreMessage();
            scheduleJobToUpdateAdbKeyStore();
        }

        private void sendPairingPortToUI(int i) {
            android.content.Intent intent = new android.content.Intent("com.android.server.adb.WIRELESS_DEBUG_PAIRING_RESULT");
            intent.putExtra("status", 4);
            intent.putExtra("adb_port", i);
            com.android.server.adb.AdbDebuggingManager.sendBroadcastWithDebugPermission(com.android.server.adb.AdbDebuggingManager.this.mContext, intent, android.os.UserHandle.ALL);
        }

        private void sendPairedDevicesToUI(java.util.Map<java.lang.String, android.debug.PairDevice> map) {
            android.content.Intent intent = new android.content.Intent("com.android.server.adb.WIRELESS_DEBUG_PAIRED_DEVICES");
            intent.putExtra("devices_map", (java.util.HashMap) map);
            com.android.server.adb.AdbDebuggingManager.sendBroadcastWithDebugPermission(com.android.server.adb.AdbDebuggingManager.this.mContext, intent, android.os.UserHandle.ALL);
        }

        private void updateUIPairCode(java.lang.String str) {
            android.content.Intent intent = new android.content.Intent("com.android.server.adb.WIRELESS_DEBUG_PAIRING_RESULT");
            intent.putExtra("pairing_code", str);
            intent.putExtra("status", 3);
            com.android.server.adb.AdbDebuggingManager.sendBroadcastWithDebugPermission(com.android.server.adb.AdbDebuggingManager.this.mContext, intent, android.os.UserHandle.ALL);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getFingerprints(java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (str == null) {
            return "";
        }
        try {
            try {
                byte[] digest = java.security.MessageDigest.getInstance("MD5").digest(android.util.Base64.decode(str.split("\\s+")[0].getBytes(), 0));
                for (int i = 0; i < digest.length; i++) {
                    sb.append("0123456789ABCDEF".charAt((digest[i] >> 4) & 15));
                    sb.append("0123456789ABCDEF".charAt(digest[i] & 15));
                    if (i < digest.length - 1) {
                        sb.append(":");
                    }
                }
                return sb.toString();
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.e(TAG, "error doing base64 decoding", e);
                return "";
            }
        } catch (java.lang.Exception e2) {
            android.util.Slog.e(TAG, "Error getting digester", e2);
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startConfirmationForNetwork(java.lang.String str, java.lang.String str2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(new java.util.AbstractMap.SimpleEntry("ssid", str));
        arrayList.add(new java.util.AbstractMap.SimpleEntry("bssid", str2));
        int currentUser = android.app.ActivityManager.getCurrentUser();
        java.lang.String string = android.content.res.Resources.getSystem().getString(android.R.string.config_chooserActivity);
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(string);
        android.content.pm.UserInfo userInfo = android.os.UserManager.get(this.mContext).getUserInfo(currentUser);
        if (startConfirmationActivity(unflattenFromString, userInfo.getUserHandle(), arrayList) || startConfirmationService(unflattenFromString, userInfo.getUserHandle(), arrayList)) {
            return;
        }
        android.util.Slog.e(TAG, "Unable to start customAdbWifiNetworkConfirmation[SecondaryUser]Component " + string + " as an Activity or a Service");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startConfirmationForKey(java.lang.String str, java.lang.String str2) {
        java.lang.String string;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(new java.util.AbstractMap.SimpleEntry("key", str));
        arrayList.add(new java.util.AbstractMap.SimpleEntry("fingerprints", str2));
        android.content.pm.UserInfo userInfo = android.os.UserManager.get(this.mContext).getUserInfo(android.app.ActivityManager.getCurrentUser());
        if (userInfo.isAdmin()) {
            string = this.mConfirmComponent != null ? this.mConfirmComponent : android.content.res.Resources.getSystem().getString(android.R.string.config_chooseAccountActivity);
        } else {
            string = android.content.res.Resources.getSystem().getString(android.R.string.config_chooseTypeAndAccountActivity);
        }
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(string);
        if (startConfirmationActivity(unflattenFromString, userInfo.getUserHandle(), arrayList) || startConfirmationService(unflattenFromString, userInfo.getUserHandle(), arrayList)) {
            return;
        }
        android.util.Slog.e(TAG, "unable to start customAdbPublicKeyConfirmation[SecondaryUser]Component " + string + " as an Activity or a Service");
    }

    private boolean startConfirmationActivity(android.content.ComponentName componentName, android.os.UserHandle userHandle, java.util.List<java.util.Map.Entry<java.lang.String, java.lang.String>> list) {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.content.Intent createConfirmationIntent = createConfirmationIntent(componentName, list);
        createConfirmationIntent.addFlags(268435456);
        if (packageManager.resolveActivity(createConfirmationIntent, 65536) != null) {
            try {
                this.mContext.startActivityAsUser(createConfirmationIntent, userHandle);
                return true;
            } catch (android.content.ActivityNotFoundException e) {
                android.util.Slog.e(TAG, "unable to start adb whitelist activity: " + componentName, e);
                return false;
            }
        }
        return false;
    }

    private boolean startConfirmationService(android.content.ComponentName componentName, android.os.UserHandle userHandle, java.util.List<java.util.Map.Entry<java.lang.String, java.lang.String>> list) {
        try {
            if (this.mContext.startServiceAsUser(createConfirmationIntent(componentName, list), userHandle) != null) {
                return true;
            }
            return false;
        } catch (java.lang.SecurityException e) {
            android.util.Slog.e(TAG, "unable to start adb whitelist service: " + componentName, e);
            return false;
        }
    }

    private android.content.Intent createConfirmationIntent(android.content.ComponentName componentName, java.util.List<java.util.Map.Entry<java.lang.String, java.lang.String>> list) {
        android.content.Intent intent = new android.content.Intent();
        intent.setClassName(componentName.getPackageName(), componentName.getClassName());
        for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : list) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        return intent;
    }

    private static java.io.File getAdbFile(java.lang.String str) {
        java.io.File file = new java.io.File(android.os.Environment.getDataDirectory(), ADB_DIRECTORY);
        if (!file.exists()) {
            android.util.Slog.e(TAG, "ADB data directory does not exist");
            return null;
        }
        return new java.io.File(file, str);
    }

    java.io.File getAdbTempKeysFile() {
        return this.mTempKeysFile;
    }

    java.io.File getUserKeyFile() {
        return this.mUserKeyFile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeKeys(java.lang.Iterable<java.lang.String> iterable) {
        java.io.FileOutputStream fileOutputStream;
        if (this.mUserKeyFile == null) {
            return;
        }
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(this.mUserKeyFile);
        try {
            fileOutputStream = atomicFile.startWrite();
            try {
                java.util.Iterator<java.lang.String> it = iterable.iterator();
                while (it.hasNext()) {
                    fileOutputStream.write(it.next().getBytes());
                    fileOutputStream.write(10);
                }
                atomicFile.finishWrite(fileOutputStream);
                android.os.FileUtils.setPermissions(this.mUserKeyFile.toString(), com.android.internal.util.FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, -1, -1);
            } catch (java.io.IOException e) {
                e = e;
                android.util.Slog.e(TAG, "Error writing keys: " + e);
                atomicFile.failWrite(fileOutputStream);
            }
        } catch (java.io.IOException e2) {
            e = e2;
            fileOutputStream = null;
        }
    }

    public void setAdbEnabled(boolean z, byte b) {
        int i = 1;
        if (b == 0) {
            com.android.server.adb.AdbDebuggingManager.AdbDebuggingHandler adbDebuggingHandler = this.mHandler;
            if (!z) {
                i = 2;
            }
            adbDebuggingHandler.sendEmptyMessage(i);
            return;
        }
        if (b == 1) {
            this.mHandler.sendEmptyMessage(z ? 11 : 12);
            return;
        }
        throw new java.lang.IllegalArgumentException("setAdbEnabled called with unimplemented transport type=" + ((int) b));
    }

    public void allowDebugging(boolean z, java.lang.String str) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(3);
        obtainMessage.arg1 = z ? 1 : 0;
        obtainMessage.obj = str;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void denyDebugging() {
        this.mHandler.sendEmptyMessage(4);
    }

    public void clearDebuggingKeys() {
        this.mHandler.sendEmptyMessage(6);
    }

    public void allowWirelessDebugging(boolean z, java.lang.String str) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(18);
        obtainMessage.arg1 = z ? 1 : 0;
        obtainMessage.obj = str;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void denyWirelessDebugging() {
        this.mHandler.sendEmptyMessage(19);
    }

    public int getAdbWirelessPort() {
        com.android.server.adb.AdbDebuggingManager.AdbConnectionInfo adbConnectionInfo = getAdbConnectionInfo();
        if (adbConnectionInfo == null) {
            return 0;
        }
        return adbConnectionInfo.getPort();
    }

    public java.util.Map<java.lang.String, android.debug.PairDevice> getPairedDevices() {
        return new com.android.server.adb.AdbDebuggingManager.AdbKeyStore().getPairedDevices();
    }

    public void unpairDevice(java.lang.String str) {
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 17, str));
    }

    public void enablePairingByPairingCode() {
        this.mHandler.sendEmptyMessage(15);
    }

    public void enablePairingByQrCode(java.lang.String str, java.lang.String str2) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("serviceName", str);
        bundle.putString(com.android.server.autofill.HintsHelper.AUTOFILL_HINT_PASSWORD, str2);
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 16, bundle));
    }

    public void disablePairing() {
        this.mHandler.sendEmptyMessage(14);
    }

    public boolean isAdbWifiEnabled() {
        return this.mAdbWifiEnabled;
    }

    public void notifyKeyFilesUpdated() {
        this.mHandler.sendEmptyMessage(28);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPersistKeyStoreMessage() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(8));
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x004b -> B:9:0x0053). Please report as a decompilation issue!!! */
    public void dump(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("connected_to_adb", 1133871366145L, this.mThread != null);
        com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "last_key_received", 1138166333442L, this.mFingerprints);
        try {
            java.io.File file = new java.io.File("/data/misc/adb/adb_keys");
            if (file.exists()) {
                dualDumpOutputStream.write("user_keys", 1138166333443L, android.os.FileUtils.readTextFile(file, 0, null));
            } else {
                android.util.Slog.i(TAG, "No user keys on this device");
            }
        } catch (java.io.IOException e) {
            android.util.Slog.i(TAG, "Cannot read user keys", e);
        }
        try {
            dualDumpOutputStream.write("system_keys", 1138166333444L, android.os.FileUtils.readTextFile(new java.io.File("/adb_keys"), 0, null));
        } catch (java.io.IOException e2) {
            android.util.Slog.i(TAG, "Cannot read system keys", e2);
        }
        try {
            dualDumpOutputStream.write("keystore", 1138166333445L, android.os.FileUtils.readTextFile(this.mTempKeysFile, 0, null));
        } catch (java.io.IOException e3) {
            android.util.Slog.i(TAG, "Cannot read keystore: ", e3);
        }
        dualDumpOutputStream.end(start);
    }

    class AdbKeyStore {
        private static final int KEYSTORE_VERSION = 1;
        private static final int MAX_SUPPORTED_KEYSTORE_VERSION = 1;
        public static final long NO_PREVIOUS_CONNECTION = 0;
        private static final java.lang.String SYSTEM_KEY_FILE = "/adb_keys";
        private static final java.lang.String XML_ATTRIBUTE_KEY = "key";
        private static final java.lang.String XML_ATTRIBUTE_LAST_CONNECTION = "lastConnection";
        private static final java.lang.String XML_ATTRIBUTE_VERSION = "version";
        private static final java.lang.String XML_ATTRIBUTE_WIFI_BSSID = "bssid";
        private static final java.lang.String XML_KEYSTORE_START_TAG = "keyStore";
        private static final java.lang.String XML_TAG_ADB_KEY = "adbKey";
        private static final java.lang.String XML_TAG_WIFI_ACCESS_POINT = "wifiAP";
        private android.util.AtomicFile mAtomicKeyFile;
        private final java.util.Set<java.lang.String> mSystemKeys;
        private final java.util.Map<java.lang.String, java.lang.Long> mKeyMap = new java.util.HashMap();
        private final java.util.List<java.lang.String> mTrustedNetworks = new java.util.ArrayList();

        AdbKeyStore() {
            initKeyFile();
            readTempKeysFile();
            this.mSystemKeys = getSystemKeysFromFile(SYSTEM_KEY_FILE);
            addExistingUserKeysToKeyStore();
        }

        public void reloadKeyMap() {
            readTempKeysFile();
        }

        public void addTrustedNetwork(java.lang.String str) {
            this.mTrustedNetworks.add(str);
            com.android.server.adb.AdbDebuggingManager.this.sendPersistKeyStoreMessage();
        }

        public java.util.Map<java.lang.String, android.debug.PairDevice> getPairedDevices() {
            java.lang.String str;
            java.util.HashMap hashMap = new java.util.HashMap();
            for (java.util.Map.Entry<java.lang.String, java.lang.Long> entry : this.mKeyMap.entrySet()) {
                java.lang.String fingerprints = com.android.server.adb.AdbDebuggingManager.this.getFingerprints(entry.getKey());
                java.lang.String[] split = entry.getKey().split("\\s+");
                if (split.length <= 1) {
                    str = "nouser@nohostname";
                } else {
                    str = split[1];
                }
                android.debug.PairDevice pairDevice = new android.debug.PairDevice();
                pairDevice.name = str;
                pairDevice.guid = fingerprints;
                pairDevice.connected = com.android.server.adb.AdbDebuggingManager.this.mWifiConnectedKeys.contains(entry.getKey());
                hashMap.put(entry.getKey(), pairDevice);
            }
            return hashMap;
        }

        public java.lang.String findKeyFromFingerprint(java.lang.String str) {
            for (java.util.Map.Entry<java.lang.String, java.lang.Long> entry : this.mKeyMap.entrySet()) {
                if (str.equals(com.android.server.adb.AdbDebuggingManager.this.getFingerprints(entry.getKey()))) {
                    return entry.getKey();
                }
            }
            return null;
        }

        public void removeKey(java.lang.String str) {
            if (this.mKeyMap.containsKey(str)) {
                this.mKeyMap.remove(str);
                com.android.server.adb.AdbDebuggingManager.this.sendPersistKeyStoreMessage();
            }
        }

        private void initKeyFile() {
            if (com.android.server.adb.AdbDebuggingManager.this.mTempKeysFile != null) {
                this.mAtomicKeyFile = new android.util.AtomicFile(com.android.server.adb.AdbDebuggingManager.this.mTempKeysFile);
            }
        }

        private java.util.Set<java.lang.String> getSystemKeysFromFile(java.lang.String str) {
            java.util.HashSet hashSet = new java.util.HashSet();
            java.io.File file = new java.io.File(str);
            if (file.exists()) {
                try {
                    java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(file));
                    while (true) {
                        try {
                            java.lang.String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            java.lang.String trim = readLine.trim();
                            if (trim.length() > 0) {
                                hashSet.add(trim);
                            }
                        } finally {
                        }
                    }
                    bufferedReader.close();
                } catch (java.io.IOException e) {
                    android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Caught an exception reading " + str + ": " + e);
                }
            }
            return hashSet;
        }

        public boolean isEmpty() {
            return this.mKeyMap.isEmpty();
        }

        public void updateKeyStore() {
            if (filterOutOldKeys()) {
                com.android.server.adb.AdbDebuggingManager.this.sendPersistKeyStoreMessage();
            }
        }

        private void readTempKeysFile() {
            com.android.modules.utils.TypedXmlPullParser resolvePullParser;
            this.mKeyMap.clear();
            this.mTrustedNetworks.clear();
            if (this.mAtomicKeyFile == null) {
                initKeyFile();
                if (this.mAtomicKeyFile == null) {
                    android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Unable to obtain the key file, " + com.android.server.adb.AdbDebuggingManager.this.mTempKeysFile + ", for reading");
                    return;
                }
            }
            if (this.mAtomicKeyFile.exists()) {
                try {
                    java.io.FileInputStream openRead = this.mAtomicKeyFile.openRead();
                    try {
                        try {
                            resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                            com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, XML_KEYSTORE_START_TAG);
                            int attributeInt = resolvePullParser.getAttributeInt((java.lang.String) null, XML_ATTRIBUTE_VERSION);
                            if (attributeInt > 1) {
                                android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Keystore version=" + attributeInt + " not supported (max_supported=1)");
                                if (openRead != null) {
                                    openRead.close();
                                    return;
                                }
                                return;
                            }
                        } catch (org.xmlpull.v1.XmlPullParserException e) {
                            resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                        }
                        readKeyStoreContents(resolvePullParser);
                        if (openRead != null) {
                            openRead.close();
                        }
                    } catch (java.lang.Throwable th) {
                        if (openRead != null) {
                            try {
                                openRead.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (java.io.IOException e2) {
                    android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Caught an IOException parsing the XML key file: ", e2);
                } catch (org.xmlpull.v1.XmlPullParserException e3) {
                    android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Caught XmlPullParserException parsing the XML key file: ", e3);
                }
            }
        }

        private void readKeyStoreContents(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            while (typedXmlPullParser.next() != 1) {
                java.lang.String name = typedXmlPullParser.getName();
                if (XML_TAG_ADB_KEY.equals(name)) {
                    addAdbKeyToKeyMap(typedXmlPullParser);
                } else if (XML_TAG_WIFI_ACCESS_POINT.equals(name)) {
                    addTrustedNetworkToTrustedNetworks(typedXmlPullParser);
                } else {
                    android.util.Slog.w(com.android.server.adb.AdbDebuggingManager.TAG, "Ignoring tag '" + name + "'. Not recognized.");
                }
                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }

        private void addAdbKeyToKeyMap(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
            java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, XML_ATTRIBUTE_KEY);
            try {
                this.mKeyMap.put(attributeValue, java.lang.Long.valueOf(typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTRIBUTE_LAST_CONNECTION)));
            } catch (org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Error reading adbKey attributes", e);
            }
        }

        private void addTrustedNetworkToTrustedNetworks(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
            this.mTrustedNetworks.add(typedXmlPullParser.getAttributeValue((java.lang.String) null, XML_ATTRIBUTE_WIFI_BSSID));
        }

        private void addExistingUserKeysToKeyStore() {
            if (com.android.server.adb.AdbDebuggingManager.this.mUserKeyFile == null || !com.android.server.adb.AdbDebuggingManager.this.mUserKeyFile.exists()) {
                return;
            }
            boolean z = false;
            try {
                java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(com.android.server.adb.AdbDebuggingManager.this.mUserKeyFile));
                while (true) {
                    try {
                        java.lang.String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        } else if (!this.mKeyMap.containsKey(readLine)) {
                            this.mKeyMap.put(readLine, java.lang.Long.valueOf(com.android.server.adb.AdbDebuggingManager.this.mTicker.currentTimeMillis()));
                            z = true;
                        }
                    } finally {
                    }
                }
                bufferedReader.close();
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Caught an exception reading " + com.android.server.adb.AdbDebuggingManager.this.mUserKeyFile + ": " + e);
            }
            if (z) {
                com.android.server.adb.AdbDebuggingManager.this.sendPersistKeyStoreMessage();
            }
        }

        public void persistKeyStore() {
            java.io.FileOutputStream startWrite;
            filterOutOldKeys();
            if (this.mKeyMap.isEmpty() && this.mTrustedNetworks.isEmpty()) {
                deleteKeyStore();
                return;
            }
            if (this.mAtomicKeyFile == null) {
                initKeyFile();
                if (this.mAtomicKeyFile == null) {
                    android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Unable to obtain the key file, " + com.android.server.adb.AdbDebuggingManager.this.mTempKeysFile + ", for writing");
                    return;
                }
            }
            java.io.FileOutputStream fileOutputStream = null;
            try {
                startWrite = this.mAtomicKeyFile.startWrite();
            } catch (java.io.IOException e) {
                e = e;
            }
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.startTag((java.lang.String) null, XML_KEYSTORE_START_TAG);
                resolveSerializer.attributeInt((java.lang.String) null, XML_ATTRIBUTE_VERSION, 1);
                for (java.util.Map.Entry<java.lang.String, java.lang.Long> entry : this.mKeyMap.entrySet()) {
                    resolveSerializer.startTag((java.lang.String) null, XML_TAG_ADB_KEY);
                    resolveSerializer.attribute((java.lang.String) null, XML_ATTRIBUTE_KEY, entry.getKey());
                    resolveSerializer.attributeLong((java.lang.String) null, XML_ATTRIBUTE_LAST_CONNECTION, entry.getValue().longValue());
                    resolveSerializer.endTag((java.lang.String) null, XML_TAG_ADB_KEY);
                }
                for (java.lang.String str : this.mTrustedNetworks) {
                    resolveSerializer.startTag((java.lang.String) null, XML_TAG_WIFI_ACCESS_POINT);
                    resolveSerializer.attribute((java.lang.String) null, XML_ATTRIBUTE_WIFI_BSSID, str);
                    resolveSerializer.endTag((java.lang.String) null, XML_TAG_WIFI_ACCESS_POINT);
                }
                resolveSerializer.endTag((java.lang.String) null, XML_KEYSTORE_START_TAG);
                resolveSerializer.endDocument();
                this.mAtomicKeyFile.finishWrite(startWrite);
            } catch (java.io.IOException e2) {
                e = e2;
                fileOutputStream = startWrite;
                android.util.Slog.e(com.android.server.adb.AdbDebuggingManager.TAG, "Caught an exception writing the key map: ", e);
                this.mAtomicKeyFile.failWrite(fileOutputStream);
                com.android.server.adb.AdbDebuggingManager.this.writeKeys(this.mKeyMap.keySet());
            }
            com.android.server.adb.AdbDebuggingManager.this.writeKeys(this.mKeyMap.keySet());
        }

        private boolean filterOutOldKeys() {
            long allowedConnectionTime = getAllowedConnectionTime();
            boolean z = false;
            if (allowedConnectionTime == 0) {
                return false;
            }
            long currentTimeMillis = com.android.server.adb.AdbDebuggingManager.this.mTicker.currentTimeMillis();
            java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Long>> it = this.mKeyMap.entrySet().iterator();
            while (it.hasNext()) {
                if (currentTimeMillis > it.next().getValue().longValue() + allowedConnectionTime) {
                    it.remove();
                    z = true;
                }
            }
            if (z) {
                com.android.server.adb.AdbDebuggingManager.this.writeKeys(this.mKeyMap.keySet());
            }
            return z;
        }

        public long getNextExpirationTime() {
            long allowedConnectionTime = getAllowedConnectionTime();
            if (allowedConnectionTime == 0) {
                return -1L;
            }
            long currentTimeMillis = com.android.server.adb.AdbDebuggingManager.this.mTicker.currentTimeMillis();
            java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Long>> it = this.mKeyMap.entrySet().iterator();
            long j = -1;
            while (it.hasNext()) {
                long max = java.lang.Math.max(0L, (it.next().getValue().longValue() + allowedConnectionTime) - currentTimeMillis);
                if (j == -1 || max < j) {
                    j = max;
                }
            }
            return j;
        }

        public void deleteKeyStore() {
            this.mKeyMap.clear();
            this.mTrustedNetworks.clear();
            if (com.android.server.adb.AdbDebuggingManager.this.mUserKeyFile != null) {
                com.android.server.adb.AdbDebuggingManager.this.mUserKeyFile.delete();
            }
            if (this.mAtomicKeyFile == null) {
                return;
            }
            this.mAtomicKeyFile.delete();
        }

        public long getLastConnectionTime(java.lang.String str) {
            return this.mKeyMap.getOrDefault(str, 0L).longValue();
        }

        public void setLastConnectionTime(java.lang.String str, long j) {
            setLastConnectionTime(str, j, false);
        }

        @com.android.internal.annotations.VisibleForTesting
        void setLastConnectionTime(java.lang.String str, long j, boolean z) {
            if ((this.mKeyMap.containsKey(str) && this.mKeyMap.get(str).longValue() >= j && !z) || this.mSystemKeys.contains(str)) {
                return;
            }
            this.mKeyMap.put(str, java.lang.Long.valueOf(j));
        }

        public long getAllowedConnectionTime() {
            return android.provider.Settings.Global.getLong(com.android.server.adb.AdbDebuggingManager.this.mContext.getContentResolver(), "adb_allowed_connection_time", com.android.server.usage.UnixCalendar.WEEK_IN_MILLIS);
        }

        public boolean isKeyAuthorized(java.lang.String str) {
            if (this.mSystemKeys.contains(str)) {
                return true;
            }
            long lastConnectionTime = getLastConnectionTime(str);
            if (lastConnectionTime == 0) {
                return false;
            }
            long allowedConnectionTime = getAllowedConnectionTime();
            return allowedConnectionTime == 0 || com.android.server.adb.AdbDebuggingManager.this.mTicker.currentTimeMillis() < lastConnectionTime + allowedConnectionTime;
        }

        public boolean isTrustedNetwork(java.lang.String str) {
            return this.mTrustedNetworks.contains(str);
        }
    }
}
