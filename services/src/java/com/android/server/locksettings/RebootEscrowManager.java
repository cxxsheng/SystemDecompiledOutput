package com.android.server.locksettings;

/* loaded from: classes2.dex */
class RebootEscrowManager {
    private static final int BOOT_COUNT_TOLERANCE = 5;
    private static final int DEFAULT_LOAD_ESCROW_BASE_TIMEOUT_MILLIS = 180000;
    private static final int DEFAULT_LOAD_ESCROW_DATA_RETRY_COUNT = 3;
    private static final int DEFAULT_LOAD_ESCROW_DATA_RETRY_INTERVAL_SECONDS = 30;
    private static final int DEFAULT_LOAD_ESCROW_TIMEOUT_EXTENSION_MILLIS = 5000;
    static final int ERROR_KEYSTORE_FAILURE = 7;
    static final int ERROR_LOAD_ESCROW_KEY = 3;
    static final int ERROR_NONE = 0;
    static final int ERROR_NO_NETWORK = 8;
    static final int ERROR_NO_PROVIDER = 2;
    static final int ERROR_PROVIDER_MISMATCH = 6;
    static final int ERROR_RETRY_COUNT_EXHAUSTED = 4;
    static final int ERROR_TIMEOUT_EXHAUSTED = 9;
    static final int ERROR_UNKNOWN = 1;
    static final int ERROR_UNLOCK_ALL_USERS = 5;
    static final java.lang.String OTHER_VBMETA_DIGEST_PROP_NAME = "ota.other.vbmeta_digest";

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String REBOOT_ESCROW_ARMED_KEY = "reboot_escrow_armed_count";
    static final java.lang.String REBOOT_ESCROW_KEY_ARMED_TIMESTAMP = "reboot_escrow_key_stored_timestamp";
    static final java.lang.String REBOOT_ESCROW_KEY_OTHER_VBMETA_DIGEST = "reboot_escrow_key_other_vbmeta_digest";
    static final java.lang.String REBOOT_ESCROW_KEY_PROVIDER = "reboot_escrow_key_provider";
    static final java.lang.String REBOOT_ESCROW_KEY_VBMETA_DIGEST = "reboot_escrow_key_vbmeta_digest";
    private static final java.lang.String TAG = "RebootEscrowManager";
    static final java.lang.String VBMETA_DIGEST_PROP_NAME = "ro.boot.vbmeta.digest";
    private final com.android.server.locksettings.RebootEscrowManager.Callbacks mCallbacks;
    private final com.android.server.locksettings.RebootEscrowManager.RebootEscrowEventLog mEventLog;
    private final android.os.Handler mHandler;
    private final com.android.server.locksettings.RebootEscrowManager.Injector mInjector;
    private final java.lang.Object mKeyGenerationLock;
    private final com.android.server.locksettings.RebootEscrowKeyStoreManager mKeyStoreManager;
    private int mLoadEscrowDataErrorCode;
    private boolean mLoadEscrowDataWithRetry;
    private android.net.ConnectivityManager.NetworkCallback mNetworkCallback;

    @com.android.internal.annotations.GuardedBy({"mKeyGenerationLock"})
    private com.android.server.locksettings.RebootEscrowKey mPendingRebootEscrowKey;
    private com.android.internal.widget.RebootEscrowListener mRebootEscrowListener;
    private boolean mRebootEscrowReady;
    private boolean mRebootEscrowTimedOut;
    private boolean mRebootEscrowWanted;
    private final com.android.server.locksettings.LockSettingsStorage mStorage;
    private final android.os.UserManager mUserManager;
    android.os.PowerManager.WakeLock mWakeLock;

    interface Callbacks {
        boolean isUserSecure(int i);

        void onRebootEscrowRestored(byte b, byte[] bArr, int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface RebootEscrowErrorCode {
    }

    static class Injector {
        protected android.content.Context mContext;
        private final com.android.server.locksettings.RebootEscrowKeyStoreManager mKeyStoreManager = new com.android.server.locksettings.RebootEscrowKeyStoreManager();
        private com.android.server.locksettings.RebootEscrowProviderInterface mRebootEscrowProvider;
        private final com.android.server.locksettings.LockSettingsStorage mStorage;

        Injector(android.content.Context context, com.android.server.locksettings.LockSettingsStorage lockSettingsStorage) {
            this.mContext = context;
            this.mStorage = lockSettingsStorage;
        }

        private com.android.server.locksettings.RebootEscrowProviderInterface createRebootEscrowProvider() {
            com.android.server.locksettings.RebootEscrowProviderInterface rebootEscrowProviderHalImpl;
            if (serverBasedResumeOnReboot()) {
                android.util.Slog.i(com.android.server.locksettings.RebootEscrowManager.TAG, "Using server based resume on reboot");
                rebootEscrowProviderHalImpl = new com.android.server.locksettings.RebootEscrowProviderServerBasedImpl(this.mContext, this.mStorage);
            } else {
                android.util.Slog.i(com.android.server.locksettings.RebootEscrowManager.TAG, "Using HAL based resume on reboot");
                rebootEscrowProviderHalImpl = new com.android.server.locksettings.RebootEscrowProviderHalImpl();
            }
            if (rebootEscrowProviderHalImpl.hasRebootEscrowSupport()) {
                return rebootEscrowProviderHalImpl;
            }
            return null;
        }

        void post(android.os.Handler handler, java.lang.Runnable runnable) {
            handler.post(runnable);
        }

        void postDelayed(android.os.Handler handler, java.lang.Runnable runnable, long j) {
            handler.postDelayed(runnable, j);
        }

        public boolean serverBasedResumeOnReboot() {
            if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.reboot_escrow")) {
                return true;
            }
            return android.provider.DeviceConfig.getBoolean("ota", "server_based_ror_enabled", false);
        }

        public boolean waitForInternet() {
            return android.provider.DeviceConfig.getBoolean("ota", "wait_for_internet_ror", false);
        }

        public boolean isNetworkConnected() {
            android.net.NetworkCapabilities networkCapabilities;
            android.net.ConnectivityManager connectivityManager = (android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class);
            return connectivityManager != null && (networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork())) != null && networkCapabilities.hasCapability(12) && networkCapabilities.hasCapability(16);
        }

        public boolean requestNetworkWithInternet(android.net.ConnectivityManager.NetworkCallback networkCallback) {
            android.net.ConnectivityManager connectivityManager = (android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class);
            if (connectivityManager == null) {
                return false;
            }
            connectivityManager.requestNetwork(new android.net.NetworkRequest.Builder().addCapability(12).build(), networkCallback, getLoadEscrowTimeoutMillis());
            return true;
        }

        public void stopRequestingNetwork(android.net.ConnectivityManager.NetworkCallback networkCallback) {
            android.net.ConnectivityManager connectivityManager = (android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class);
            if (connectivityManager == null) {
                return;
            }
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }

        public android.content.Context getContext() {
            return this.mContext;
        }

        public android.os.UserManager getUserManager() {
            return (android.os.UserManager) this.mContext.getSystemService("user");
        }

        public com.android.server.locksettings.RebootEscrowKeyStoreManager getKeyStoreManager() {
            return this.mKeyStoreManager;
        }

        public com.android.server.locksettings.RebootEscrowProviderInterface createRebootEscrowProviderIfNeeded() {
            if (this.mRebootEscrowProvider == null) {
                this.mRebootEscrowProvider = createRebootEscrowProvider();
            }
            return this.mRebootEscrowProvider;
        }

        android.os.PowerManager.WakeLock getWakeLock() {
            return ((android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class)).newWakeLock(1, com.android.server.locksettings.RebootEscrowManager.TAG);
        }

        public com.android.server.locksettings.RebootEscrowProviderInterface getRebootEscrowProvider() {
            return this.mRebootEscrowProvider;
        }

        public void clearRebootEscrowProvider() {
            this.mRebootEscrowProvider = null;
        }

        public int getBootCount() {
            return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "boot_count", 0);
        }

        public long getCurrentTimeMillis() {
            return java.lang.System.currentTimeMillis();
        }

        public int getLoadEscrowDataRetryLimit() {
            return android.provider.DeviceConfig.getInt("ota", "load_escrow_data_retry_count", 3);
        }

        public int getLoadEscrowDataRetryIntervalSeconds() {
            return android.provider.DeviceConfig.getInt("ota", "load_escrow_data_retry_interval_seconds", 30);
        }

        @com.android.internal.annotations.VisibleForTesting
        public int getLoadEscrowTimeoutMillis() {
            return com.android.server.locksettings.RebootEscrowManager.DEFAULT_LOAD_ESCROW_BASE_TIMEOUT_MILLIS;
        }

        @com.android.internal.annotations.VisibleForTesting
        public int getWakeLockTimeoutMillis() {
            return getLoadEscrowTimeoutMillis() + 5000;
        }

        public void reportMetric(boolean z, int i, int i2, int i3, int i4, int i5, int i6) {
            com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.REBOOT_ESCROW_RECOVERY_REPORTED, z, i, i2, i3, i4, i5, i6);
        }

        public com.android.server.locksettings.RebootEscrowManager.RebootEscrowEventLog getEventLog() {
            return new com.android.server.locksettings.RebootEscrowManager.RebootEscrowEventLog();
        }

        public java.lang.String getVbmetaDigest(boolean z) {
            return z ? android.os.SystemProperties.get(com.android.server.locksettings.RebootEscrowManager.OTHER_VBMETA_DIGEST_PROP_NAME) : android.os.SystemProperties.get(com.android.server.locksettings.RebootEscrowManager.VBMETA_DIGEST_PROP_NAME);
        }
    }

    RebootEscrowManager(android.content.Context context, com.android.server.locksettings.RebootEscrowManager.Callbacks callbacks, com.android.server.locksettings.LockSettingsStorage lockSettingsStorage, android.os.Handler handler) {
        this(new com.android.server.locksettings.RebootEscrowManager.Injector(context, lockSettingsStorage), callbacks, lockSettingsStorage, handler);
    }

    @com.android.internal.annotations.VisibleForTesting
    RebootEscrowManager(com.android.server.locksettings.RebootEscrowManager.Injector injector, com.android.server.locksettings.RebootEscrowManager.Callbacks callbacks, com.android.server.locksettings.LockSettingsStorage lockSettingsStorage, android.os.Handler handler) {
        this.mLoadEscrowDataErrorCode = 0;
        this.mRebootEscrowTimedOut = false;
        this.mLoadEscrowDataWithRetry = false;
        this.mKeyGenerationLock = new java.lang.Object();
        this.mInjector = injector;
        this.mCallbacks = callbacks;
        this.mStorage = lockSettingsStorage;
        this.mUserManager = injector.getUserManager();
        this.mEventLog = injector.getEventLog();
        this.mKeyStoreManager = injector.getKeyStoreManager();
        this.mHandler = handler;
    }

    private void setLoadEscrowDataErrorCode(final int i, android.os.Handler handler) {
        if (this.mInjector.waitForInternet()) {
            this.mInjector.post(handler, new java.lang.Runnable() { // from class: com.android.server.locksettings.RebootEscrowManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.locksettings.RebootEscrowManager.this.lambda$setLoadEscrowDataErrorCode$0(i);
                }
            });
        } else {
            this.mLoadEscrowDataErrorCode = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLoadEscrowDataErrorCode$0(int i) {
        this.mLoadEscrowDataErrorCode = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void compareAndSetLoadEscrowDataErrorCode(int i, int i2, android.os.Handler handler) {
        if (i == this.mLoadEscrowDataErrorCode) {
            setLoadEscrowDataErrorCode(i2, handler);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onGetRebootEscrowKeyFailed(java.util.List<android.content.pm.UserInfo> list, int i, android.os.Handler handler) {
        android.util.Slog.w(TAG, "Had reboot escrow data for users, but no key; removing escrow storage.");
        java.util.Iterator<android.content.pm.UserInfo> it = list.iterator();
        while (it.hasNext()) {
            this.mStorage.removeRebootEscrow(it.next().id);
        }
        onEscrowRestoreComplete(false, i, handler);
    }

    void loadRebootEscrowDataIfAvailable(final android.os.Handler handler) {
        final java.util.List<android.content.pm.UserInfo> users = this.mUserManager.getUsers();
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.content.pm.UserInfo userInfo : users) {
            if (this.mCallbacks.isUserSecure(userInfo.id) && this.mStorage.hasRebootEscrow(userInfo.id)) {
                arrayList.add(userInfo);
            }
        }
        if (arrayList.isEmpty()) {
            android.util.Slog.i(TAG, "No reboot escrow data found for users, skipping loading escrow data");
            clearMetricsStorage();
            return;
        }
        this.mWakeLock = this.mInjector.getWakeLock();
        if (this.mWakeLock != null) {
            this.mWakeLock.setReferenceCounted(false);
            this.mWakeLock.acquire(this.mInjector.getWakeLockTimeoutMillis());
        }
        if (this.mInjector.waitForInternet()) {
            this.mInjector.postDelayed(handler, new java.lang.Runnable() { // from class: com.android.server.locksettings.RebootEscrowManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.locksettings.RebootEscrowManager.this.lambda$loadRebootEscrowDataIfAvailable$1();
                }
            }, this.mInjector.getLoadEscrowTimeoutMillis());
            this.mInjector.post(handler, new java.lang.Runnable() { // from class: com.android.server.locksettings.RebootEscrowManager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.locksettings.RebootEscrowManager.this.lambda$loadRebootEscrowDataIfAvailable$2(handler, users, arrayList);
                }
            });
        } else {
            this.mInjector.post(handler, new java.lang.Runnable() { // from class: com.android.server.locksettings.RebootEscrowManager$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.locksettings.RebootEscrowManager.this.lambda$loadRebootEscrowDataIfAvailable$3(handler, users, arrayList);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadRebootEscrowDataIfAvailable$1() {
        this.mRebootEscrowTimedOut = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadRebootEscrowDataIfAvailable$3(android.os.Handler handler, java.util.List list, java.util.List list2) {
        lambda$scheduleLoadRebootEscrowDataOrFail$4(handler, 0, list, list2);
    }

    void scheduleLoadRebootEscrowDataOrFail(final android.os.Handler handler, final int i, final java.util.List<android.content.pm.UserInfo> list, final java.util.List<android.content.pm.UserInfo> list2) {
        java.util.Objects.requireNonNull(handler);
        int loadEscrowDataRetryLimit = this.mInjector.getLoadEscrowDataRetryLimit();
        int loadEscrowDataRetryIntervalSeconds = this.mInjector.getLoadEscrowDataRetryIntervalSeconds();
        if (i < loadEscrowDataRetryLimit && !this.mRebootEscrowTimedOut) {
            android.util.Slog.i(TAG, "Scheduling loadRebootEscrowData retry number: " + i);
            this.mInjector.postDelayed(handler, new java.lang.Runnable() { // from class: com.android.server.locksettings.RebootEscrowManager$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.locksettings.RebootEscrowManager.this.lambda$scheduleLoadRebootEscrowDataOrFail$4(handler, i, list, list2);
                }
            }, (long) (loadEscrowDataRetryIntervalSeconds * 1000));
            return;
        }
        if (this.mInjector.waitForInternet()) {
            if (this.mRebootEscrowTimedOut) {
                android.util.Slog.w(TAG, "Failed to load reboot escrow data within timeout");
                compareAndSetLoadEscrowDataErrorCode(0, 9, handler);
            } else {
                android.util.Slog.w(TAG, "Failed to load reboot escrow data after " + i + " attempts");
                compareAndSetLoadEscrowDataErrorCode(0, 4, handler);
            }
            onGetRebootEscrowKeyFailed(list, i, handler);
            return;
        }
        android.util.Slog.w(TAG, "Failed to load reboot escrow data after " + i + " attempts");
        if (this.mInjector.serverBasedResumeOnReboot() && !this.mInjector.isNetworkConnected()) {
            this.mLoadEscrowDataErrorCode = 8;
        } else {
            this.mLoadEscrowDataErrorCode = 4;
        }
        onGetRebootEscrowKeyFailed(list, i, handler);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: loadRebootEscrowDataOnInternet, reason: merged with bridge method [inline-methods] */
    public void lambda$loadRebootEscrowDataIfAvailable$2(final android.os.Handler handler, final java.util.List<android.content.pm.UserInfo> list, final java.util.List<android.content.pm.UserInfo> list2) {
        if (!this.mInjector.serverBasedResumeOnReboot()) {
            lambda$scheduleLoadRebootEscrowDataOrFail$4(handler, 0, list, list2);
            return;
        }
        this.mNetworkCallback = new android.net.ConnectivityManager.NetworkCallback() { // from class: com.android.server.locksettings.RebootEscrowManager.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(android.net.Network network) {
                com.android.server.locksettings.RebootEscrowManager.this.compareAndSetLoadEscrowDataErrorCode(8, 0, handler);
                if (!com.android.server.locksettings.RebootEscrowManager.this.mLoadEscrowDataWithRetry) {
                    com.android.server.locksettings.RebootEscrowManager.this.mLoadEscrowDataWithRetry = true;
                    com.android.server.locksettings.RebootEscrowManager.this.lambda$scheduleLoadRebootEscrowDataOrFail$4(handler, 0, list, list2);
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onUnavailable() {
                android.util.Slog.w(com.android.server.locksettings.RebootEscrowManager.TAG, "Failed to connect to network within timeout");
                com.android.server.locksettings.RebootEscrowManager.this.compareAndSetLoadEscrowDataErrorCode(0, 8, handler);
                com.android.server.locksettings.RebootEscrowManager.this.onGetRebootEscrowKeyFailed(list, 0, handler);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(android.net.Network network) {
                android.util.Slog.w(com.android.server.locksettings.RebootEscrowManager.TAG, "Network lost, still attempting to load escrow key.");
                com.android.server.locksettings.RebootEscrowManager.this.compareAndSetLoadEscrowDataErrorCode(0, 8, handler);
            }
        };
        if (!this.mInjector.requestNetworkWithInternet(this.mNetworkCallback)) {
            lambda$scheduleLoadRebootEscrowDataOrFail$4(handler, 0, list, list2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: loadRebootEscrowDataWithRetry, reason: merged with bridge method [inline-methods] */
    public void lambda$scheduleLoadRebootEscrowDataOrFail$4(android.os.Handler handler, int i, java.util.List<android.content.pm.UserInfo> list, java.util.List<android.content.pm.UserInfo> list2) {
        int i2;
        javax.crypto.SecretKey keyStoreEncryptionKey = this.mKeyStoreManager.getKeyStoreEncryptionKey();
        if (keyStoreEncryptionKey == null) {
            android.util.Slog.i(TAG, "Failed to load the key for resume on reboot from key store.");
        }
        try {
            com.android.server.locksettings.RebootEscrowKey andClearRebootEscrowKey = getAndClearRebootEscrowKey(keyStoreEncryptionKey, handler);
            if (andClearRebootEscrowKey != null) {
                this.mEventLog.addEntry(1);
                java.util.Iterator<android.content.pm.UserInfo> it = list2.iterator();
                boolean z = true;
                while (it.hasNext()) {
                    z &= restoreRebootEscrowForUser(it.next().id, andClearRebootEscrowKey, keyStoreEncryptionKey);
                }
                if (!z) {
                    compareAndSetLoadEscrowDataErrorCode(0, 5, handler);
                }
                onEscrowRestoreComplete(z, i + 1, handler);
                return;
            }
            if (this.mLoadEscrowDataErrorCode == 0) {
                if (this.mInjector.serverBasedResumeOnReboot()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                if (i2 != this.mStorage.getInt(REBOOT_ESCROW_KEY_PROVIDER, -1, 0)) {
                    setLoadEscrowDataErrorCode(6, handler);
                } else {
                    setLoadEscrowDataErrorCode(3, handler);
                }
            }
            onGetRebootEscrowKeyFailed(list, i + 1, handler);
        } catch (java.io.IOException e) {
            android.util.Slog.i(TAG, "Failed to load escrow key, scheduling retry.", e);
            scheduleLoadRebootEscrowDataOrFail(handler, i + 1, list, list2);
        }
    }

    private void clearMetricsStorage() {
        this.mStorage.removeKey(REBOOT_ESCROW_ARMED_KEY, 0);
        this.mStorage.removeKey(REBOOT_ESCROW_KEY_ARMED_TIMESTAMP, 0);
        this.mStorage.removeKey(REBOOT_ESCROW_KEY_VBMETA_DIGEST, 0);
        this.mStorage.removeKey(REBOOT_ESCROW_KEY_OTHER_VBMETA_DIGEST, 0);
        this.mStorage.removeKey(REBOOT_ESCROW_KEY_PROVIDER, 0);
    }

    private int getVbmetaDigestStatusOnRestoreComplete() {
        java.lang.String vbmetaDigest = this.mInjector.getVbmetaDigest(false);
        java.lang.String string = this.mStorage.getString(REBOOT_ESCROW_KEY_VBMETA_DIGEST, "", 0);
        java.lang.String string2 = this.mStorage.getString(REBOOT_ESCROW_KEY_OTHER_VBMETA_DIGEST, "", 0);
        if (string2.isEmpty()) {
            return vbmetaDigest.equals(string) ? 0 : 2;
        }
        if (vbmetaDigest.equals(string2)) {
            return 0;
        }
        return vbmetaDigest.equals(string) ? 1 : 2;
    }

    private void reportMetricOnRestoreComplete(boolean z, int i, android.os.Handler handler) {
        int i2;
        int i3;
        if (this.mInjector.serverBasedResumeOnReboot()) {
            i2 = 2;
        } else {
            i2 = 1;
        }
        long j = this.mStorage.getLong(REBOOT_ESCROW_KEY_ARMED_TIMESTAMP, -1L, 0);
        long currentTimeMillis = this.mInjector.getCurrentTimeMillis();
        if (j != -1 && currentTimeMillis > j) {
            i3 = ((int) (currentTimeMillis - j)) / 1000;
        } else {
            i3 = -1;
        }
        int vbmetaDigestStatusOnRestoreComplete = getVbmetaDigestStatusOnRestoreComplete();
        if (!z) {
            compareAndSetLoadEscrowDataErrorCode(0, 1, handler);
        }
        android.util.Slog.i(TAG, "Reporting RoR recovery metrics, success: " + z + ", service type: " + i2 + ", error code: " + this.mLoadEscrowDataErrorCode);
        this.mInjector.reportMetric(z, this.mLoadEscrowDataErrorCode, i2, i, i3, vbmetaDigestStatusOnRestoreComplete, -1);
        setLoadEscrowDataErrorCode(0, handler);
    }

    private void onEscrowRestoreComplete(boolean z, int i, android.os.Handler handler) {
        int i2 = this.mStorage.getInt(REBOOT_ESCROW_ARMED_KEY, -1, 0);
        int bootCount = this.mInjector.getBootCount() - i2;
        if (z || (i2 != -1 && bootCount <= 5)) {
            reportMetricOnRestoreComplete(z, i, handler);
        }
        this.mKeyStoreManager.clearKeyStoreEncryptionKey();
        this.mInjector.clearRebootEscrowProvider();
        clearMetricsStorage();
        if (this.mNetworkCallback != null) {
            this.mInjector.stopRequestingNetwork(this.mNetworkCallback);
        }
        if (this.mWakeLock != null) {
            this.mWakeLock.release();
        }
    }

    private com.android.server.locksettings.RebootEscrowKey getAndClearRebootEscrowKey(javax.crypto.SecretKey secretKey, android.os.Handler handler) throws java.io.IOException {
        com.android.server.locksettings.RebootEscrowProviderInterface createRebootEscrowProviderIfNeeded = this.mInjector.createRebootEscrowProviderIfNeeded();
        if (createRebootEscrowProviderIfNeeded == null) {
            android.util.Slog.w(TAG, "Had reboot escrow data for users, but RebootEscrowProvider is unavailable");
            setLoadEscrowDataErrorCode(2, handler);
            return null;
        }
        if (createRebootEscrowProviderIfNeeded.getType() == 1 && secretKey == null) {
            setLoadEscrowDataErrorCode(7, handler);
            return null;
        }
        com.android.server.locksettings.RebootEscrowKey andClearRebootEscrowKey = createRebootEscrowProviderIfNeeded.getAndClearRebootEscrowKey(secretKey);
        if (andClearRebootEscrowKey != null) {
            this.mEventLog.addEntry(4);
        }
        return andClearRebootEscrowKey;
    }

    private boolean restoreRebootEscrowForUser(int i, com.android.server.locksettings.RebootEscrowKey rebootEscrowKey, javax.crypto.SecretKey secretKey) {
        if (!this.mStorage.hasRebootEscrow(i)) {
            return false;
        }
        try {
            byte[] readRebootEscrow = this.mStorage.readRebootEscrow(i);
            this.mStorage.removeRebootEscrow(i);
            com.android.server.locksettings.RebootEscrowData fromEncryptedData = com.android.server.locksettings.RebootEscrowData.fromEncryptedData(rebootEscrowKey, readRebootEscrow, secretKey);
            this.mCallbacks.onRebootEscrowRestored(fromEncryptedData.getSpVersion(), fromEncryptedData.getSyntheticPassword(), i);
            android.util.Slog.i(TAG, "Restored reboot escrow data for user " + i);
            this.mEventLog.addEntry(7, i);
            return true;
        } catch (java.io.IOException e) {
            android.util.Slog.w(TAG, "Could not load reboot escrow data for user " + i, e);
            return false;
        }
    }

    void callToRebootEscrowIfNeeded(int i, byte b, byte[] bArr) {
        if (!this.mRebootEscrowWanted) {
            return;
        }
        if (this.mInjector.createRebootEscrowProviderIfNeeded() == null) {
            android.util.Slog.w(TAG, "Not storing escrow data, RebootEscrowProvider is unavailable");
            return;
        }
        com.android.server.locksettings.RebootEscrowKey generateEscrowKeyIfNeeded = generateEscrowKeyIfNeeded();
        if (generateEscrowKeyIfNeeded == null) {
            android.util.Slog.e(TAG, "Could not generate escrow key");
            return;
        }
        javax.crypto.SecretKey generateKeyStoreEncryptionKeyIfNeeded = this.mKeyStoreManager.generateKeyStoreEncryptionKeyIfNeeded();
        if (generateKeyStoreEncryptionKeyIfNeeded == null) {
            android.util.Slog.e(TAG, "Failed to generate encryption key from keystore.");
            return;
        }
        try {
            this.mStorage.writeRebootEscrow(i, com.android.server.locksettings.RebootEscrowData.fromSyntheticPassword(generateEscrowKeyIfNeeded, b, bArr, generateKeyStoreEncryptionKeyIfNeeded).getBlob());
            this.mEventLog.addEntry(6, i);
            setRebootEscrowReady(true);
        } catch (java.io.IOException e) {
            setRebootEscrowReady(false);
            android.util.Slog.w(TAG, "Could not escrow reboot data", e);
        }
    }

    private com.android.server.locksettings.RebootEscrowKey generateEscrowKeyIfNeeded() {
        synchronized (this.mKeyGenerationLock) {
            if (this.mPendingRebootEscrowKey != null) {
                return this.mPendingRebootEscrowKey;
            }
            try {
                com.android.server.locksettings.RebootEscrowKey generate = com.android.server.locksettings.RebootEscrowKey.generate();
                this.mPendingRebootEscrowKey = generate;
                return generate;
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Could not generate reboot escrow key");
                return null;
            }
        }
    }

    private void clearRebootEscrowIfNeeded() {
        this.mRebootEscrowWanted = false;
        setRebootEscrowReady(false);
        com.android.server.locksettings.RebootEscrowProviderInterface createRebootEscrowProviderIfNeeded = this.mInjector.createRebootEscrowProviderIfNeeded();
        if (createRebootEscrowProviderIfNeeded == null) {
            android.util.Slog.w(TAG, "RebootEscrowProvider is unavailable for clear request");
        } else {
            createRebootEscrowProviderIfNeeded.clearRebootEscrowKey();
        }
        this.mInjector.clearRebootEscrowProvider();
        clearMetricsStorage();
        java.util.Iterator it = this.mUserManager.getUsers().iterator();
        while (it.hasNext()) {
            this.mStorage.removeRebootEscrow(((android.content.pm.UserInfo) it.next()).id);
        }
        this.mEventLog.addEntry(3);
    }

    int armRebootEscrowIfNeeded() {
        int i;
        com.android.server.locksettings.RebootEscrowKey rebootEscrowKey;
        if (!this.mRebootEscrowReady) {
            return 2;
        }
        com.android.server.locksettings.RebootEscrowProviderInterface rebootEscrowProvider = this.mInjector.getRebootEscrowProvider();
        if (rebootEscrowProvider == null) {
            android.util.Slog.w(TAG, "Not storing escrow key, RebootEscrowProvider is unavailable");
            clearRebootEscrowIfNeeded();
            return 3;
        }
        if (this.mInjector.serverBasedResumeOnReboot()) {
            i = 1;
        } else {
            i = 0;
        }
        int type = rebootEscrowProvider.getType();
        if (i != type) {
            android.util.Slog.w(TAG, "Expect reboot escrow provider " + i + ", but the RoR is prepared with " + type + ". Please prepare the RoR again.");
            clearRebootEscrowIfNeeded();
            return 4;
        }
        synchronized (this.mKeyGenerationLock) {
            rebootEscrowKey = this.mPendingRebootEscrowKey;
        }
        if (rebootEscrowKey == null) {
            android.util.Slog.e(TAG, "Escrow key is null, but escrow was marked as ready");
            clearRebootEscrowIfNeeded();
            return 5;
        }
        javax.crypto.SecretKey keyStoreEncryptionKey = this.mKeyStoreManager.getKeyStoreEncryptionKey();
        if (keyStoreEncryptionKey == null) {
            android.util.Slog.e(TAG, "Failed to get encryption key from keystore.");
            clearRebootEscrowIfNeeded();
            return 6;
        }
        if (!rebootEscrowProvider.storeRebootEscrowKey(rebootEscrowKey, keyStoreEncryptionKey)) {
            return 7;
        }
        this.mStorage.setInt(REBOOT_ESCROW_ARMED_KEY, this.mInjector.getBootCount(), 0);
        this.mStorage.setLong(REBOOT_ESCROW_KEY_ARMED_TIMESTAMP, this.mInjector.getCurrentTimeMillis(), 0);
        this.mStorage.setString(REBOOT_ESCROW_KEY_VBMETA_DIGEST, this.mInjector.getVbmetaDigest(false), 0);
        this.mStorage.setString(REBOOT_ESCROW_KEY_OTHER_VBMETA_DIGEST, this.mInjector.getVbmetaDigest(true), 0);
        this.mStorage.setInt(REBOOT_ESCROW_KEY_PROVIDER, type, 0);
        this.mEventLog.addEntry(2);
        return 0;
    }

    private void setRebootEscrowReady(final boolean z) {
        if (this.mRebootEscrowReady != z) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.locksettings.RebootEscrowManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.locksettings.RebootEscrowManager.this.lambda$setRebootEscrowReady$5(z);
                }
            });
        }
        this.mRebootEscrowReady = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setRebootEscrowReady$5(boolean z) {
        this.mRebootEscrowListener.onPreparedForReboot(z);
    }

    boolean prepareRebootEscrow() {
        clearRebootEscrowIfNeeded();
        if (this.mInjector.createRebootEscrowProviderIfNeeded() == null) {
            android.util.Slog.w(TAG, "No reboot escrow provider, skipping resume on reboot preparation.");
            return false;
        }
        this.mRebootEscrowWanted = true;
        this.mEventLog.addEntry(5);
        return true;
    }

    boolean clearRebootEscrow() {
        clearRebootEscrowIfNeeded();
        return true;
    }

    void setRebootEscrowListener(com.android.internal.widget.RebootEscrowListener rebootEscrowListener) {
        this.mRebootEscrowListener = rebootEscrowListener;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class RebootEscrowEvent {
        static final int CLEARED_LSKF_REQUEST = 3;
        static final int FOUND_ESCROW_DATA = 1;
        static final int REQUESTED_LSKF = 5;
        static final int RETRIEVED_LSKF_FOR_USER = 7;
        static final int RETRIEVED_STORED_KEK = 4;
        static final int SET_ARMED_STATUS = 2;
        static final int STORED_LSKF_FOR_USER = 6;
        final int mEventId;
        final long mTimestamp;
        final java.lang.Integer mUserId;
        final long mWallTime;

        RebootEscrowEvent(int i) {
            this(i, null);
        }

        RebootEscrowEvent(int i, java.lang.Integer num) {
            this.mEventId = i;
            this.mUserId = num;
            this.mTimestamp = android.os.SystemClock.uptimeMillis();
            this.mWallTime = java.lang.System.currentTimeMillis();
        }

        java.lang.String getEventDescription() {
            switch (this.mEventId) {
                case 1:
                    return "Found escrow data";
                case 2:
                    return "Set armed status";
                case 3:
                    return "Cleared request for LSKF";
                case 4:
                    return "Retrieved stored KEK";
                case 5:
                    return "Requested LSKF";
                case 6:
                    return "Stored LSKF for user";
                case 7:
                    return "Retrieved LSKF for user";
                default:
                    return "Unknown event ID " + this.mEventId;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class RebootEscrowEventLog {
        private com.android.server.locksettings.RebootEscrowManager.RebootEscrowEvent[] mEntries = new com.android.server.locksettings.RebootEscrowManager.RebootEscrowEvent[16];
        private int mNextIndex = 0;

        void addEntry(int i) {
            addEntryInternal(new com.android.server.locksettings.RebootEscrowManager.RebootEscrowEvent(i));
        }

        void addEntry(int i, int i2) {
            addEntryInternal(new com.android.server.locksettings.RebootEscrowManager.RebootEscrowEvent(i, java.lang.Integer.valueOf(i2)));
        }

        private void addEntryInternal(com.android.server.locksettings.RebootEscrowManager.RebootEscrowEvent rebootEscrowEvent) {
            this.mEntries[this.mNextIndex] = rebootEscrowEvent;
            this.mNextIndex = (this.mNextIndex + 1) % this.mEntries.length;
        }

        void dump(@android.annotation.NonNull com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", java.util.Locale.US);
            for (int i = 0; i < this.mEntries.length; i++) {
                com.android.server.locksettings.RebootEscrowManager.RebootEscrowEvent rebootEscrowEvent = this.mEntries[(this.mNextIndex + i) % this.mEntries.length];
                if (rebootEscrowEvent != null) {
                    indentingPrintWriter.print("Event #");
                    indentingPrintWriter.println(i);
                    indentingPrintWriter.println(" time=" + simpleDateFormat.format(new java.util.Date(rebootEscrowEvent.mWallTime)) + " (timestamp=" + rebootEscrowEvent.mTimestamp + ")");
                    indentingPrintWriter.print(" event=");
                    indentingPrintWriter.println(rebootEscrowEvent.getEventDescription());
                    if (rebootEscrowEvent.mUserId != null) {
                        indentingPrintWriter.print(" user=");
                        indentingPrintWriter.println(rebootEscrowEvent.mUserId);
                    }
                }
            }
        }
    }

    void dump(@android.annotation.NonNull com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        boolean z;
        indentingPrintWriter.print("mRebootEscrowWanted=");
        indentingPrintWriter.println(this.mRebootEscrowWanted);
        indentingPrintWriter.print("mRebootEscrowReady=");
        indentingPrintWriter.println(this.mRebootEscrowReady);
        indentingPrintWriter.print("mRebootEscrowListener=");
        indentingPrintWriter.println(this.mRebootEscrowListener);
        indentingPrintWriter.print("mLoadEscrowDataErrorCode=");
        indentingPrintWriter.println(this.mLoadEscrowDataErrorCode);
        synchronized (this.mKeyGenerationLock) {
            z = this.mPendingRebootEscrowKey != null;
        }
        indentingPrintWriter.print("mPendingRebootEscrowKey is ");
        indentingPrintWriter.println(z ? "set" : "not set");
        com.android.server.locksettings.RebootEscrowProviderInterface rebootEscrowProvider = this.mInjector.getRebootEscrowProvider();
        indentingPrintWriter.print("RebootEscrowProvider type is " + (rebootEscrowProvider == null ? "null" : java.lang.String.valueOf(rebootEscrowProvider.getType())));
        indentingPrintWriter.println();
        indentingPrintWriter.println("Event log:");
        indentingPrintWriter.increaseIndent();
        this.mEventLog.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }
}
