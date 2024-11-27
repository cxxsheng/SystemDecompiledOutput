package com.android.server.content;

/* loaded from: classes.dex */
public class SyncStorageEngine {
    private static final int ACCOUNTS_VERSION = 3;
    private static final java.lang.String ACCOUNT_INFO_FILE_NAME = "accounts.xml";
    private static final double DEFAULT_FLEX_PERCENT_SYNC = 0.04d;
    private static final long DEFAULT_MIN_FLEX_ALLOWED_SECS = 5;
    private static final long DEFAULT_POLL_FREQUENCY_SECONDS = 86400;
    private static final boolean DELETE_LEGACY_PARCEL_FILES = true;
    public static final int EVENT_START = 0;
    public static final int EVENT_STOP = 1;
    private static final java.lang.String LEGACY_STATISTICS_FILE_NAME = "stats.bin";
    private static final java.lang.String LEGACY_STATUS_FILE_NAME = "status.bin";
    public static final int MAX_HISTORY = 100;
    public static final java.lang.String MESG_CANCELED = "canceled";
    public static final java.lang.String MESG_SUCCESS = "success";

    @com.android.internal.annotations.VisibleForTesting
    static final long MILLIS_IN_4WEEKS = 2419200000L;
    private static final int MSG_WRITE_STATISTICS = 2;
    private static final int MSG_WRITE_STATUS = 1;
    public static final long NOT_IN_BACKOFF_MODE = -1;
    public static final int SOURCE_FEED = 5;
    public static final int SOURCE_LOCAL = 1;
    public static final int SOURCE_OTHER = 0;
    public static final int SOURCE_PERIODIC = 4;
    public static final int SOURCE_POLL = 2;
    public static final int SOURCE_USER = 3;
    public static final int STATISTICS_FILE_END = 0;
    public static final int STATISTICS_FILE_ITEM = 101;
    public static final int STATISTICS_FILE_ITEM_OLD = 100;
    private static final java.lang.String STATISTICS_FILE_NAME = "stats";
    public static final int STATUS_FILE_END = 0;
    public static final int STATUS_FILE_ITEM = 100;
    private static final java.lang.String STATUS_FILE_NAME = "status";
    private static final java.lang.String SYNC_DIR_NAME = "sync";
    private static final boolean SYNC_ENABLED_DEFAULT = false;
    private static final java.lang.String TAG = "SyncManager";
    private static final java.lang.String TAG_FILE = "SyncManagerFile";
    private static final long WRITE_STATISTICS_DELAY = 1800000;
    private static final long WRITE_STATUS_DELAY = 600000;
    private static final java.lang.String XML_ATTR_ENABLED = "enabled";
    private static final java.lang.String XML_ATTR_LISTEN_FOR_TICKLES = "listen-for-tickles";
    private static final java.lang.String XML_ATTR_NEXT_AUTHORITY_ID = "nextAuthorityId";
    private static final java.lang.String XML_ATTR_SYNC_RANDOM_OFFSET = "offsetInSeconds";
    private static final java.lang.String XML_ATTR_USER = "user";
    private static final java.lang.String XML_TAG_LISTEN_FOR_TICKLES = "listenForTickles";
    private static com.android.server.content.SyncStorageEngine.PeriodicSyncAddedListener mPeriodicSyncAddedListener;
    private static volatile com.android.server.content.SyncStorageEngine sSyncStorageEngine;
    private final android.util.AtomicFile mAccountInfoFile;
    private com.android.server.content.SyncStorageEngine.OnAuthorityRemovedListener mAuthorityRemovedListener;
    private final java.util.Calendar mCal;
    private final android.content.Context mContext;
    private boolean mDefaultMasterSyncAutomatically;
    private boolean mGrantSyncAdaptersAccountAccess;
    private final com.android.server.content.SyncStorageEngine.MyHandler mHandler;
    private volatile boolean mIsClockValid;
    private volatile boolean mIsJobAttributionFixed;
    private volatile boolean mIsJobNamespaceMigrated;
    private final com.android.server.content.SyncLogger mLogger;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final android.util.AtomicFile mStatisticsFile;
    private final android.util.AtomicFile mStatusFile;
    private java.io.File mSyncDir;
    private int mSyncRandomOffset;
    private com.android.server.content.SyncStorageEngine.OnSyncRequestListener mSyncRequestListener;
    private int mYear;
    private int mYearInDays;
    public static final java.lang.String[] SOURCES = {"OTHER", "LOCAL", "POLL", "USER", "PERIODIC", "FEED"};
    private static java.util.HashMap<java.lang.String, java.lang.String> sAuthorityRenames = new java.util.HashMap<>();

    @com.android.internal.annotations.VisibleForTesting
    final android.util.SparseArray<com.android.server.content.SyncStorageEngine.AuthorityInfo> mAuthorities = new android.util.SparseArray<>();
    private final java.util.HashMap<android.accounts.AccountAndUser, com.android.server.content.SyncStorageEngine.AccountInfo> mAccounts = new java.util.HashMap<>();
    private final android.util.SparseArray<java.util.ArrayList<android.content.SyncInfo>> mCurrentSyncs = new android.util.SparseArray<>();

    @com.android.internal.annotations.VisibleForTesting
    final android.util.SparseArray<android.content.SyncStatusInfo> mSyncStatus = new android.util.SparseArray<>();
    private final java.util.ArrayList<com.android.server.content.SyncStorageEngine.SyncHistoryItem> mSyncHistory = new java.util.ArrayList<>();
    private final android.os.RemoteCallbackList<android.content.ISyncStatusObserver> mChangeListeners = new android.os.RemoteCallbackList<>();
    private final android.util.ArrayMap<android.content.ComponentName, android.util.SparseArray<com.android.server.content.SyncStorageEngine.AuthorityInfo>> mServices = new android.util.ArrayMap<>();
    private int mNextAuthorityId = 0;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.content.SyncStorageEngine.DayStats[] mDayStats = new com.android.server.content.SyncStorageEngine.DayStats[28];
    private int mNextHistoryId = 0;
    private android.util.SparseArray<java.lang.Boolean> mMasterSyncAutomatically = new android.util.SparseArray<>();

    interface OnAuthorityRemovedListener {
        void onAuthorityRemoved(com.android.server.content.SyncStorageEngine.EndPoint endPoint);
    }

    interface OnSyncRequestListener {
        void onSyncRequest(com.android.server.content.SyncStorageEngine.EndPoint endPoint, int i, android.os.Bundle bundle, int i2, int i3, int i4);
    }

    interface PeriodicSyncAddedListener {
        void onPeriodicSyncAdded(com.android.server.content.SyncStorageEngine.EndPoint endPoint, android.os.Bundle bundle, long j, long j2);
    }

    public static class SyncHistoryItem {
        int authorityId;
        long downstreamActivity;
        long elapsedTime;
        int event;
        long eventTime;
        android.os.Bundle extras;
        int historyId;
        boolean initialization;
        java.lang.String mesg;
        int reason;
        int source;
        int syncExemptionFlag;
        long upstreamActivity;
    }

    static {
        sAuthorityRenames.put("contacts", "com.android.contacts");
        sAuthorityRenames.put("calendar", "com.android.calendar");
        sSyncStorageEngine = null;
    }

    static class AccountInfo {
        final android.accounts.AccountAndUser accountAndUser;
        final java.util.HashMap<java.lang.String, com.android.server.content.SyncStorageEngine.AuthorityInfo> authorities = new java.util.HashMap<>();

        AccountInfo(android.accounts.AccountAndUser accountAndUser) {
            this.accountAndUser = accountAndUser;
        }
    }

    public static class EndPoint {
        public static final com.android.server.content.SyncStorageEngine.EndPoint USER_ALL_PROVIDER_ALL_ACCOUNTS_ALL = new com.android.server.content.SyncStorageEngine.EndPoint(null, null, -1);
        final android.accounts.Account account;
        final java.lang.String provider;
        final int userId;

        public EndPoint(android.accounts.Account account, java.lang.String str, int i) {
            this.account = account;
            this.provider = str;
            this.userId = i;
        }

        public boolean matchesSpec(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
            boolean equals;
            boolean equals2;
            if (this.userId != endPoint.userId && this.userId != -1 && endPoint.userId != -1) {
                return false;
            }
            if (endPoint.account == null) {
                equals = true;
            } else {
                equals = this.account.equals(endPoint.account);
            }
            if (endPoint.provider == null) {
                equals2 = true;
            } else {
                equals2 = this.provider.equals(endPoint.provider);
            }
            return equals && equals2;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.account == null ? "ALL ACCS" : this.account.name);
            sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            sb.append(this.provider == null ? "ALL PDRS" : this.provider);
            sb.append(":u" + this.userId);
            return sb.toString();
        }

        public java.lang.String toSafeString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(this.account == null ? "ALL ACCS" : com.android.server.content.SyncLogger.logSafe(this.account));
            sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            sb.append(this.provider == null ? "ALL PDRS" : this.provider);
            sb.append(":u" + this.userId);
            return sb.toString();
        }
    }

    public static class AuthorityInfo {
        public static final int NOT_INITIALIZED = -1;
        public static final int NOT_SYNCABLE = 0;
        public static final int SYNCABLE = 1;
        public static final int SYNCABLE_NOT_INITIALIZED = 2;
        public static final int SYNCABLE_NO_ACCOUNT_ACCESS = 3;
        public static final int UNDEFINED = -2;
        long backoffDelay;
        long backoffTime;
        long delayUntil;
        boolean enabled;
        final int ident;
        final java.util.ArrayList<android.content.PeriodicSync> periodicSyncs;
        int syncable;
        final com.android.server.content.SyncStorageEngine.EndPoint target;

        AuthorityInfo(com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo) {
            this.target = authorityInfo.target;
            this.ident = authorityInfo.ident;
            this.enabled = authorityInfo.enabled;
            this.syncable = authorityInfo.syncable;
            this.backoffTime = authorityInfo.backoffTime;
            this.backoffDelay = authorityInfo.backoffDelay;
            this.delayUntil = authorityInfo.delayUntil;
            this.periodicSyncs = new java.util.ArrayList<>();
            java.util.Iterator<android.content.PeriodicSync> it = authorityInfo.periodicSyncs.iterator();
            while (it.hasNext()) {
                this.periodicSyncs.add(new android.content.PeriodicSync(it.next()));
            }
        }

        AuthorityInfo(com.android.server.content.SyncStorageEngine.EndPoint endPoint, int i) {
            this.target = endPoint;
            this.ident = i;
            this.enabled = false;
            this.periodicSyncs = new java.util.ArrayList<>();
            defaultInitialisation();
        }

        private void defaultInitialisation() {
            this.syncable = -1;
            this.backoffTime = -1L;
            this.backoffDelay = -1L;
            if (com.android.server.content.SyncStorageEngine.mPeriodicSyncAddedListener != null) {
                com.android.server.content.SyncStorageEngine.mPeriodicSyncAddedListener.onPeriodicSyncAdded(this.target, new android.os.Bundle(), com.android.server.content.SyncStorageEngine.DEFAULT_POLL_FREQUENCY_SECONDS, com.android.server.content.SyncStorageEngine.calculateDefaultFlexTime(com.android.server.content.SyncStorageEngine.DEFAULT_POLL_FREQUENCY_SECONDS));
            }
        }

        public java.lang.String toString() {
            return this.target + ", enabled=" + this.enabled + ", syncable=" + this.syncable + ", backoff=" + this.backoffTime + ", delay=" + this.delayUntil;
        }
    }

    public static class DayStats {
        public final int day;
        public int failureCount;
        public long failureTime;
        public int successCount;
        public long successTime;

        public DayStats(int i) {
            this.day = i;
        }
    }

    private static class AccountAuthorityValidator {
        private final android.accounts.AccountManager mAccountManager;
        private final android.content.pm.PackageManager mPackageManager;
        private final android.util.SparseArray<android.accounts.Account[]> mAccountsCache = new android.util.SparseArray<>();
        private final android.util.SparseArray<android.util.ArrayMap<java.lang.String, java.lang.Boolean>> mProvidersPerUserCache = new android.util.SparseArray<>();

        AccountAuthorityValidator(android.content.Context context) {
            this.mAccountManager = (android.accounts.AccountManager) context.getSystemService(android.accounts.AccountManager.class);
            this.mPackageManager = context.getPackageManager();
        }

        boolean isAccountValid(android.accounts.Account account, int i) {
            android.accounts.Account[] accountArr = this.mAccountsCache.get(i);
            if (accountArr == null) {
                accountArr = this.mAccountManager.getAccountsAsUser(i);
                this.mAccountsCache.put(i, accountArr);
            }
            return com.android.internal.util.ArrayUtils.contains(accountArr, account);
        }

        boolean isAuthorityValid(java.lang.String str, int i) {
            android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap = this.mProvidersPerUserCache.get(i);
            if (arrayMap == null) {
                arrayMap = new android.util.ArrayMap<>();
                this.mProvidersPerUserCache.put(i, arrayMap);
            }
            if (!arrayMap.containsKey(str)) {
                arrayMap.put(str, java.lang.Boolean.valueOf(this.mPackageManager.resolveContentProviderAsUser(str, 786432, i) != null));
            }
            return arrayMap.get(str).booleanValue();
        }
    }

    private SyncStorageEngine(android.content.Context context, java.io.File file, android.os.Looper looper) {
        this.mHandler = new com.android.server.content.SyncStorageEngine.MyHandler(looper);
        this.mContext = context;
        sSyncStorageEngine = this;
        this.mLogger = com.android.server.content.SyncLogger.getInstance();
        this.mCal = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("GMT+0"));
        this.mDefaultMasterSyncAutomatically = this.mContext.getResources().getBoolean(android.R.bool.config_swipe_up_gesture_setting_available);
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mSyncDir = new java.io.File(new java.io.File(file, "system"), SYNC_DIR_NAME);
        this.mSyncDir.mkdirs();
        maybeDeleteLegacyPendingInfoLocked(this.mSyncDir);
        this.mAccountInfoFile = new android.util.AtomicFile(new java.io.File(this.mSyncDir, ACCOUNT_INFO_FILE_NAME), "sync-accounts");
        this.mStatusFile = new android.util.AtomicFile(new java.io.File(this.mSyncDir, STATUS_FILE_NAME), "sync-status");
        this.mStatisticsFile = new android.util.AtomicFile(new java.io.File(this.mSyncDir, STATISTICS_FILE_NAME), "sync-stats");
        readAccountInfoLocked();
        readStatusLocked();
        readStatisticsLocked();
        if (this.mLogger.enabled()) {
            int size = this.mAuthorities.size();
            this.mLogger.log("Loaded ", java.lang.Integer.valueOf(size), " items");
            for (int i = 0; i < size; i++) {
                this.mLogger.log(this.mAuthorities.valueAt(i));
            }
        }
    }

    public static com.android.server.content.SyncStorageEngine newTestInstance(android.content.Context context) {
        return new com.android.server.content.SyncStorageEngine(context, context.getFilesDir(), android.os.Looper.getMainLooper());
    }

    public static void init(android.content.Context context, android.os.Looper looper) {
        if (sSyncStorageEngine != null) {
            return;
        }
        sSyncStorageEngine = new com.android.server.content.SyncStorageEngine(context, android.os.Environment.getDataDirectory(), looper);
    }

    public static com.android.server.content.SyncStorageEngine getSingleton() {
        if (sSyncStorageEngine == null) {
            throw new java.lang.IllegalStateException("not initialized");
        }
        return sSyncStorageEngine;
    }

    protected void setOnSyncRequestListener(com.android.server.content.SyncStorageEngine.OnSyncRequestListener onSyncRequestListener) {
        if (this.mSyncRequestListener == null) {
            this.mSyncRequestListener = onSyncRequestListener;
        }
    }

    protected void setOnAuthorityRemovedListener(com.android.server.content.SyncStorageEngine.OnAuthorityRemovedListener onAuthorityRemovedListener) {
        if (this.mAuthorityRemovedListener == null) {
            this.mAuthorityRemovedListener = onAuthorityRemovedListener;
        }
    }

    protected void setPeriodicSyncAddedListener(com.android.server.content.SyncStorageEngine.PeriodicSyncAddedListener periodicSyncAddedListener) {
        if (mPeriodicSyncAddedListener == null) {
            mPeriodicSyncAddedListener = periodicSyncAddedListener;
        }
    }

    private class MyHandler extends android.os.Handler {
        public MyHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (message.what == 1) {
                synchronized (com.android.server.content.SyncStorageEngine.this.mAuthorities) {
                    com.android.server.content.SyncStorageEngine.this.writeStatusLocked();
                }
            } else if (message.what == 2) {
                synchronized (com.android.server.content.SyncStorageEngine.this.mAuthorities) {
                    com.android.server.content.SyncStorageEngine.this.writeStatisticsLocked();
                }
            }
        }
    }

    public int getSyncRandomOffset() {
        return this.mSyncRandomOffset;
    }

    public void addStatusChangeListener(int i, int i2, android.content.ISyncStatusObserver iSyncStatusObserver) {
        synchronized (this.mAuthorities) {
            this.mChangeListeners.register(iSyncStatusObserver, java.lang.Long.valueOf(com.android.internal.util.IntPair.of(i2, i)));
        }
    }

    public void removeStatusChangeListener(android.content.ISyncStatusObserver iSyncStatusObserver) {
        synchronized (this.mAuthorities) {
            this.mChangeListeners.unregister(iSyncStatusObserver);
        }
    }

    public static long calculateDefaultFlexTime(long j) {
        if (j < DEFAULT_MIN_FLEX_ALLOWED_SECS) {
            return 0L;
        }
        if (j < DEFAULT_POLL_FREQUENCY_SECONDS) {
            return (long) (j * DEFAULT_FLEX_PERCENT_SYNC);
        }
        return 3456L;
    }

    void reportChange(int i, com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        java.lang.String str;
        if (endPoint.account == null || endPoint.provider == null) {
            str = null;
        } else {
            str = android.content.ContentResolver.getSyncAdapterPackageAsUser(endPoint.account.type, endPoint.provider, endPoint.userId);
        }
        reportChange(i, str, endPoint.userId);
    }

    void reportChange(int i, java.lang.String str, int i2) {
        java.util.ArrayList arrayList;
        synchronized (this.mAuthorities) {
            try {
                int beginBroadcast = this.mChangeListeners.beginBroadcast();
                arrayList = null;
                while (beginBroadcast > 0) {
                    beginBroadcast--;
                    long longValue = ((java.lang.Long) this.mChangeListeners.getBroadcastCookie(beginBroadcast)).longValue();
                    int first = com.android.internal.util.IntPair.first(longValue);
                    int userId = android.os.UserHandle.getUserId(first);
                    if ((com.android.internal.util.IntPair.second(longValue) & i) != 0 && i2 == userId && (str == null || !this.mPackageManagerInternal.filterAppAccess(str, first, i2))) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList(beginBroadcast);
                        }
                        arrayList.add(this.mChangeListeners.getBroadcastItem(beginBroadcast));
                    }
                }
                this.mChangeListeners.finishBroadcast();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.v("SyncManager", "reportChange " + i + " to: " + arrayList);
        }
        if (arrayList != null) {
            int size = arrayList.size();
            while (size > 0) {
                size--;
                try {
                    ((android.content.ISyncStatusObserver) arrayList.get(size)).onStatusChanged(i);
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }

    public boolean getSyncAutomatically(android.accounts.Account account, int i, java.lang.String str) {
        synchronized (this.mAuthorities) {
            boolean z = false;
            try {
                if (account != null) {
                    com.android.server.content.SyncStorageEngine.AuthorityInfo authorityLocked = getAuthorityLocked(new com.android.server.content.SyncStorageEngine.EndPoint(account, str, i), "getSyncAutomatically");
                    if (authorityLocked != null && authorityLocked.enabled) {
                        z = true;
                    }
                    return z;
                }
                int size = this.mAuthorities.size();
                while (size > 0) {
                    size--;
                    com.android.server.content.SyncStorageEngine.AuthorityInfo valueAt = this.mAuthorities.valueAt(size);
                    if (valueAt.target.matchesSpec(new com.android.server.content.SyncStorageEngine.EndPoint(account, str, i)) && valueAt.enabled) {
                        return true;
                    }
                }
                return false;
            } finally {
            }
        }
    }

    public void setSyncAutomatically(android.accounts.Account account, int i, java.lang.String str, boolean z, int i2, int i3, int i4) {
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.d("SyncManager", "setSyncAutomatically:  provider " + str + ", user " + i + " -> " + z);
        }
        this.mLogger.log("Set sync auto account=", account, " user=", java.lang.Integer.valueOf(i), " authority=", str, " value=", java.lang.Boolean.toString(z), " cuid=", java.lang.Integer.valueOf(i3), " cpid=", java.lang.Integer.valueOf(i4));
        synchronized (this.mAuthorities) {
            try {
                com.android.server.content.SyncStorageEngine.AuthorityInfo orCreateAuthorityLocked = getOrCreateAuthorityLocked(new com.android.server.content.SyncStorageEngine.EndPoint(account, str, i), -1, false);
                if (orCreateAuthorityLocked.enabled == z) {
                    if (android.util.Log.isLoggable("SyncManager", 2)) {
                        android.util.Slog.d("SyncManager", "setSyncAutomatically: already set to " + z + ", doing nothing");
                    }
                    return;
                }
                if (z && orCreateAuthorityLocked.syncable == 2) {
                    orCreateAuthorityLocked.syncable = -1;
                }
                orCreateAuthorityLocked.enabled = z;
                writeAccountInfoLocked();
                if (z) {
                    requestSync(account, i, -6, str, new android.os.Bundle(), i2, i3, i4);
                }
                reportChange(1, orCreateAuthorityLocked.target);
                queueBackup();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getIsSyncable(android.accounts.Account account, int i, java.lang.String str) {
        synchronized (this.mAuthorities) {
            try {
                if (account != null) {
                    com.android.server.content.SyncStorageEngine.AuthorityInfo authorityLocked = getAuthorityLocked(new com.android.server.content.SyncStorageEngine.EndPoint(account, str, i), "get authority syncable");
                    if (authorityLocked == null) {
                        return -1;
                    }
                    return authorityLocked.syncable;
                }
                int size = this.mAuthorities.size();
                while (size > 0) {
                    size--;
                    com.android.server.content.SyncStorageEngine.AuthorityInfo valueAt = this.mAuthorities.valueAt(size);
                    if (valueAt.target != null && valueAt.target.provider.equals(str)) {
                        return valueAt.syncable;
                    }
                }
                return -1;
            } finally {
            }
        }
    }

    public void setIsSyncable(android.accounts.Account account, int i, java.lang.String str, int i2, int i3, int i4) {
        setSyncableStateForEndPoint(new com.android.server.content.SyncStorageEngine.EndPoint(account, str, i), i2, i3, i4);
    }

    private void setSyncableStateForEndPoint(com.android.server.content.SyncStorageEngine.EndPoint endPoint, int i, int i2, int i3) {
        this.mLogger.log("Set syncable ", endPoint, " value=", java.lang.Integer.toString(i), " cuid=", java.lang.Integer.valueOf(i2), " cpid=", java.lang.Integer.valueOf(i3));
        synchronized (this.mAuthorities) {
            try {
                com.android.server.content.SyncStorageEngine.AuthorityInfo orCreateAuthorityLocked = getOrCreateAuthorityLocked(endPoint, -1, false);
                if (i < -1) {
                    i = -1;
                }
                if (android.util.Log.isLoggable("SyncManager", 2)) {
                    android.util.Slog.d("SyncManager", "setIsSyncable: " + orCreateAuthorityLocked.toString() + " -> " + i);
                }
                if (orCreateAuthorityLocked.syncable == i) {
                    if (android.util.Log.isLoggable("SyncManager", 2)) {
                        android.util.Slog.d("SyncManager", "setIsSyncable: already set to " + i + ", doing nothing");
                    }
                    return;
                }
                orCreateAuthorityLocked.syncable = i;
                writeAccountInfoLocked();
                if (i == 1) {
                    requestSync(orCreateAuthorityLocked, -5, new android.os.Bundle(), 0, i2, i3);
                }
                reportChange(1, endPoint);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setJobNamespaceMigrated(boolean z) {
        if (this.mIsJobNamespaceMigrated == z) {
            return;
        }
        this.mIsJobNamespaceMigrated = z;
        this.mHandler.sendEmptyMessageDelayed(1, 600000L);
    }

    boolean isJobNamespaceMigrated() {
        return this.mIsJobNamespaceMigrated;
    }

    void setJobAttributionFixed(boolean z) {
        if (this.mIsJobAttributionFixed == z) {
            return;
        }
        this.mIsJobAttributionFixed = z;
        this.mHandler.sendEmptyMessageDelayed(1, 600000L);
    }

    boolean isJobAttributionFixed() {
        return this.mIsJobAttributionFixed;
    }

    public android.util.Pair<java.lang.Long, java.lang.Long> getBackoff(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        synchronized (this.mAuthorities) {
            try {
                com.android.server.content.SyncStorageEngine.AuthorityInfo authorityLocked = getAuthorityLocked(endPoint, "getBackoff");
                if (authorityLocked == null) {
                    return null;
                }
                return android.util.Pair.create(java.lang.Long.valueOf(authorityLocked.backoffTime), java.lang.Long.valueOf(authorityLocked.backoffDelay));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setBackoff(com.android.server.content.SyncStorageEngine.EndPoint endPoint, long j, long j2) {
        boolean backoffLocked;
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.v("SyncManager", "setBackoff: " + endPoint + " -> nextSyncTime " + j + ", nextDelay " + j2);
        }
        synchronized (this.mAuthorities) {
            try {
                if (endPoint.account == null || endPoint.provider == null) {
                    backoffLocked = setBackoffLocked(endPoint.account, endPoint.userId, endPoint.provider, j, j2);
                } else {
                    com.android.server.content.SyncStorageEngine.AuthorityInfo orCreateAuthorityLocked = getOrCreateAuthorityLocked(endPoint, -1, true);
                    if (orCreateAuthorityLocked.backoffTime == j && orCreateAuthorityLocked.backoffDelay == j2) {
                        backoffLocked = false;
                    } else {
                        orCreateAuthorityLocked.backoffTime = j;
                        orCreateAuthorityLocked.backoffDelay = j2;
                        backoffLocked = true;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (backoffLocked) {
            reportChange(1, endPoint);
        }
    }

    private boolean setBackoffLocked(android.accounts.Account account, int i, java.lang.String str, long j, long j2) {
        boolean z = false;
        for (com.android.server.content.SyncStorageEngine.AccountInfo accountInfo : this.mAccounts.values()) {
            if (account == null || account.equals(accountInfo.accountAndUser.account) || i == accountInfo.accountAndUser.userId) {
                for (com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo : accountInfo.authorities.values()) {
                    if (str == null || str.equals(authorityInfo.target.provider)) {
                        if (authorityInfo.backoffTime != j || authorityInfo.backoffDelay != j2) {
                            authorityInfo.backoffTime = j;
                            authorityInfo.backoffDelay = j2;
                            z = true;
                        }
                    }
                }
            }
        }
        return z;
    }

    public void clearAllBackoffsLocked() {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        synchronized (this.mAuthorities) {
            try {
                for (com.android.server.content.SyncStorageEngine.AccountInfo accountInfo : this.mAccounts.values()) {
                    for (com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo : accountInfo.authorities.values()) {
                        if (authorityInfo.backoffTime != -1 || authorityInfo.backoffDelay != -1) {
                            if (android.util.Log.isLoggable("SyncManager", 2)) {
                                android.util.Slog.v("SyncManager", "clearAllBackoffsLocked: authority:" + authorityInfo.target + " account:" + accountInfo.accountAndUser.account.name + " user:" + accountInfo.accountAndUser.userId + " backoffTime was: " + authorityInfo.backoffTime + " backoffDelay was: " + authorityInfo.backoffDelay);
                            }
                            authorityInfo.backoffTime = -1L;
                            authorityInfo.backoffDelay = -1L;
                            arraySet.add(java.lang.Integer.valueOf(accountInfo.accountAndUser.userId));
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        for (int size = arraySet.size() - 1; size > 0; size--) {
            reportChange(1, null, ((java.lang.Integer) arraySet.valueAt(size)).intValue());
        }
    }

    public long getDelayUntilTime(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        synchronized (this.mAuthorities) {
            try {
                com.android.server.content.SyncStorageEngine.AuthorityInfo authorityLocked = getAuthorityLocked(endPoint, "getDelayUntil");
                if (authorityLocked == null) {
                    return 0L;
                }
                return authorityLocked.delayUntil;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setDelayUntilTime(com.android.server.content.SyncStorageEngine.EndPoint endPoint, long j) {
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.v("SyncManager", "setDelayUntil: " + endPoint + " -> delayUntil " + j);
        }
        synchronized (this.mAuthorities) {
            try {
                com.android.server.content.SyncStorageEngine.AuthorityInfo orCreateAuthorityLocked = getOrCreateAuthorityLocked(endPoint, -1, true);
                if (orCreateAuthorityLocked.delayUntil == j) {
                    return;
                }
                orCreateAuthorityLocked.delayUntil = j;
                reportChange(1, endPoint);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean restoreAllPeriodicSyncs() {
        if (mPeriodicSyncAddedListener == null) {
            return false;
        }
        synchronized (this.mAuthorities) {
            for (int i = 0; i < this.mAuthorities.size(); i++) {
                try {
                    com.android.server.content.SyncStorageEngine.AuthorityInfo valueAt = this.mAuthorities.valueAt(i);
                    java.util.Iterator<android.content.PeriodicSync> it = valueAt.periodicSyncs.iterator();
                    while (it.hasNext()) {
                        android.content.PeriodicSync next = it.next();
                        mPeriodicSyncAddedListener.onPeriodicSyncAdded(valueAt.target, next.extras, next.period, next.flexTime);
                    }
                    valueAt.periodicSyncs.clear();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            writeAccountInfoLocked();
        }
        return true;
    }

    public void setMasterSyncAutomatically(boolean z, int i, int i2, int i3, int i4) {
        this.mLogger.log("Set master enabled=", java.lang.Boolean.valueOf(z), " user=", java.lang.Integer.valueOf(i), " cuid=", java.lang.Integer.valueOf(i3), " cpid=", java.lang.Integer.valueOf(i4));
        synchronized (this.mAuthorities) {
            try {
                java.lang.Boolean bool = this.mMasterSyncAutomatically.get(i);
                if (bool == null || !bool.equals(java.lang.Boolean.valueOf(z))) {
                    this.mMasterSyncAutomatically.put(i, java.lang.Boolean.valueOf(z));
                    writeAccountInfoLocked();
                    if (z) {
                        requestSync(null, i, -7, null, new android.os.Bundle(), i2, i3, i4);
                    }
                    reportChange(1, null, i);
                    this.mContext.sendBroadcast(android.content.ContentResolver.ACTION_SYNC_CONN_STATUS_CHANGED);
                    queueBackup();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean getMasterSyncAutomatically(int i) {
        boolean booleanValue;
        synchronized (this.mAuthorities) {
            try {
                java.lang.Boolean bool = this.mMasterSyncAutomatically.get(i);
                booleanValue = bool == null ? this.mDefaultMasterSyncAutomatically : bool.booleanValue();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return booleanValue;
    }

    public int getAuthorityCount() {
        int size;
        synchronized (this.mAuthorities) {
            size = this.mAuthorities.size();
        }
        return size;
    }

    public com.android.server.content.SyncStorageEngine.AuthorityInfo getAuthority(int i) {
        com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo;
        synchronized (this.mAuthorities) {
            authorityInfo = this.mAuthorities.get(i);
        }
        return authorityInfo;
    }

    public boolean isSyncActive(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        synchronized (this.mAuthorities) {
            try {
                java.util.Iterator<android.content.SyncInfo> it = getCurrentSyncs(endPoint.userId).iterator();
                while (it.hasNext()) {
                    com.android.server.content.SyncStorageEngine.AuthorityInfo authority = getAuthority(it.next().authorityId);
                    if (authority != null && authority.target.matchesSpec(endPoint)) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void markPending(com.android.server.content.SyncStorageEngine.EndPoint endPoint, boolean z) {
        synchronized (this.mAuthorities) {
            try {
                com.android.server.content.SyncStorageEngine.AuthorityInfo orCreateAuthorityLocked = getOrCreateAuthorityLocked(endPoint, -1, true);
                if (orCreateAuthorityLocked == null) {
                    return;
                }
                getOrCreateSyncStatusLocked(orCreateAuthorityLocked.ident).pending = z;
                reportChange(2, endPoint);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeStaleAccounts(@android.annotation.Nullable android.accounts.Account[] accountArr, int i) {
        synchronized (this.mAuthorities) {
            try {
                if (android.util.Log.isLoggable("SyncManager", 2)) {
                    android.util.Slog.v("SyncManager", "Updating for new accounts...");
                }
                android.util.SparseArray sparseArray = new android.util.SparseArray();
                java.util.Iterator<com.android.server.content.SyncStorageEngine.AccountInfo> it = this.mAccounts.values().iterator();
                while (it.hasNext()) {
                    com.android.server.content.SyncStorageEngine.AccountInfo next = it.next();
                    if (next.accountAndUser.userId == i) {
                        if (accountArr == null || !com.android.internal.util.ArrayUtils.contains(accountArr, next.accountAndUser.account)) {
                            if (android.util.Log.isLoggable("SyncManager", 2)) {
                                android.util.Slog.v("SyncManager", "Account removed: " + next.accountAndUser);
                            }
                            for (com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo : next.authorities.values()) {
                                sparseArray.put(authorityInfo.ident, authorityInfo);
                            }
                            it.remove();
                        }
                    }
                }
                int size = sparseArray.size();
                if (size > 0) {
                    while (size > 0) {
                        size--;
                        int keyAt = sparseArray.keyAt(size);
                        com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo2 = (com.android.server.content.SyncStorageEngine.AuthorityInfo) sparseArray.valueAt(size);
                        if (this.mAuthorityRemovedListener != null) {
                            this.mAuthorityRemovedListener.onAuthorityRemoved(authorityInfo2.target);
                        }
                        this.mAuthorities.remove(keyAt);
                        int size2 = this.mSyncStatus.size();
                        while (size2 > 0) {
                            size2--;
                            if (this.mSyncStatus.keyAt(size2) == keyAt) {
                                this.mSyncStatus.remove(this.mSyncStatus.keyAt(size2));
                            }
                        }
                        int size3 = this.mSyncHistory.size();
                        while (size3 > 0) {
                            size3--;
                            if (this.mSyncHistory.get(size3).authorityId == keyAt) {
                                this.mSyncHistory.remove(size3);
                            }
                        }
                    }
                    writeAccountInfoLocked();
                    writeStatusLocked();
                    writeStatisticsLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.content.SyncInfo addActiveSync(com.android.server.content.SyncManager.ActiveSyncContext activeSyncContext) {
        android.content.SyncInfo syncInfo;
        synchronized (this.mAuthorities) {
            try {
                if (android.util.Log.isLoggable("SyncManager", 2)) {
                    android.util.Slog.v("SyncManager", "setActiveSync: account= auth=" + activeSyncContext.mSyncOperation.target + " src=" + activeSyncContext.mSyncOperation.syncSource + " extras=" + activeSyncContext.mSyncOperation.getExtrasAsString());
                }
                com.android.server.content.SyncStorageEngine.AuthorityInfo orCreateAuthorityLocked = getOrCreateAuthorityLocked(activeSyncContext.mSyncOperation.target, -1, true);
                syncInfo = new android.content.SyncInfo(orCreateAuthorityLocked.ident, orCreateAuthorityLocked.target.account, orCreateAuthorityLocked.target.provider, activeSyncContext.mStartTime);
                getCurrentSyncs(orCreateAuthorityLocked.target.userId).add(syncInfo);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        reportActiveChange(activeSyncContext.mSyncOperation.target);
        return syncInfo;
    }

    public void removeActiveSync(android.content.SyncInfo syncInfo, int i) {
        synchronized (this.mAuthorities) {
            try {
                if (android.util.Log.isLoggable("SyncManager", 2)) {
                    android.util.Slog.v("SyncManager", "removeActiveSync: account=" + syncInfo.account + " user=" + i + " auth=" + syncInfo.authority);
                }
                getCurrentSyncs(i).remove(syncInfo);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        reportActiveChange(new com.android.server.content.SyncStorageEngine.EndPoint(syncInfo.account, syncInfo.authority, i));
    }

    public void reportActiveChange(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        reportChange(4, endPoint);
    }

    public long insertStartSyncEvent(com.android.server.content.SyncOperation syncOperation, long j) {
        synchronized (this.mAuthorities) {
            try {
                if (android.util.Log.isLoggable("SyncManager", 2)) {
                    android.util.Slog.v("SyncManager", "insertStartSyncEvent: " + syncOperation);
                }
                com.android.server.content.SyncStorageEngine.AuthorityInfo authorityLocked = getAuthorityLocked(syncOperation.target, "insertStartSyncEvent");
                if (authorityLocked == null) {
                    return -1L;
                }
                com.android.server.content.SyncStorageEngine.SyncHistoryItem syncHistoryItem = new com.android.server.content.SyncStorageEngine.SyncHistoryItem();
                syncHistoryItem.initialization = syncOperation.isInitialization();
                syncHistoryItem.authorityId = authorityLocked.ident;
                int i = this.mNextHistoryId;
                this.mNextHistoryId = i + 1;
                syncHistoryItem.historyId = i;
                if (this.mNextHistoryId < 0) {
                    this.mNextHistoryId = 0;
                }
                syncHistoryItem.eventTime = j;
                syncHistoryItem.source = syncOperation.syncSource;
                syncHistoryItem.reason = syncOperation.reason;
                syncHistoryItem.extras = syncOperation.getClonedExtras();
                syncHistoryItem.event = 0;
                syncHistoryItem.syncExemptionFlag = syncOperation.syncExemptionFlag;
                this.mSyncHistory.add(0, syncHistoryItem);
                while (this.mSyncHistory.size() > 100) {
                    this.mSyncHistory.remove(this.mSyncHistory.size() - 1);
                }
                long j2 = syncHistoryItem.historyId;
                if (android.util.Log.isLoggable("SyncManager", 2)) {
                    android.util.Slog.v("SyncManager", "returning historyId " + j2);
                }
                reportChange(8, syncOperation.owningPackage, syncOperation.target.userId);
                return j2;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x014b A[Catch: all -> 0x002c, TryCatch #0 {all -> 0x002c, blocks: (B:4:0x000b, B:6:0x0014, B:7:0x002f, B:9:0x0038, B:15:0x004e, B:16:0x0065, B:19:0x0067, B:20:0x00a2, B:22:0x0100, B:24:0x010b, B:26:0x0138, B:28:0x014b, B:30:0x0151, B:32:0x0158, B:33:0x01a4, B:35:0x01e6, B:36:0x01ed, B:37:0x01f0, B:38:0x01f6, B:39:0x01fd, B:40:0x0203, B:42:0x0216, B:44:0x0232, B:45:0x024d, B:48:0x0236, B:50:0x023f, B:51:0x021a, B:53:0x0222, B:55:0x0168, B:57:0x0170, B:60:0x0177, B:61:0x0195, B:62:0x0115, B:64:0x011d, B:65:0x0133, B:66:0x00a6, B:67:0x00b5, B:68:0x00c4, B:69:0x00d3, B:70:0x00e2, B:71:0x00f1), top: B:3:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x01e6 A[Catch: all -> 0x002c, TryCatch #0 {all -> 0x002c, blocks: (B:4:0x000b, B:6:0x0014, B:7:0x002f, B:9:0x0038, B:15:0x004e, B:16:0x0065, B:19:0x0067, B:20:0x00a2, B:22:0x0100, B:24:0x010b, B:26:0x0138, B:28:0x014b, B:30:0x0151, B:32:0x0158, B:33:0x01a4, B:35:0x01e6, B:36:0x01ed, B:37:0x01f0, B:38:0x01f6, B:39:0x01fd, B:40:0x0203, B:42:0x0216, B:44:0x0232, B:45:0x024d, B:48:0x0236, B:50:0x023f, B:51:0x021a, B:53:0x0222, B:55:0x0168, B:57:0x0170, B:60:0x0177, B:61:0x0195, B:62:0x0115, B:64:0x011d, B:65:0x0133, B:66:0x00a6, B:67:0x00b5, B:68:0x00c4, B:69:0x00d3, B:70:0x00e2, B:71:0x00f1), top: B:3:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0216 A[Catch: all -> 0x002c, TryCatch #0 {all -> 0x002c, blocks: (B:4:0x000b, B:6:0x0014, B:7:0x002f, B:9:0x0038, B:15:0x004e, B:16:0x0065, B:19:0x0067, B:20:0x00a2, B:22:0x0100, B:24:0x010b, B:26:0x0138, B:28:0x014b, B:30:0x0151, B:32:0x0158, B:33:0x01a4, B:35:0x01e6, B:36:0x01ed, B:37:0x01f0, B:38:0x01f6, B:39:0x01fd, B:40:0x0203, B:42:0x0216, B:44:0x0232, B:45:0x024d, B:48:0x0236, B:50:0x023f, B:51:0x021a, B:53:0x0222, B:55:0x0168, B:57:0x0170, B:60:0x0177, B:61:0x0195, B:62:0x0115, B:64:0x011d, B:65:0x0133, B:66:0x00a6, B:67:0x00b5, B:68:0x00c4, B:69:0x00d3, B:70:0x00e2, B:71:0x00f1), top: B:3:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0232 A[Catch: all -> 0x002c, TryCatch #0 {all -> 0x002c, blocks: (B:4:0x000b, B:6:0x0014, B:7:0x002f, B:9:0x0038, B:15:0x004e, B:16:0x0065, B:19:0x0067, B:20:0x00a2, B:22:0x0100, B:24:0x010b, B:26:0x0138, B:28:0x014b, B:30:0x0151, B:32:0x0158, B:33:0x01a4, B:35:0x01e6, B:36:0x01ed, B:37:0x01f0, B:38:0x01f6, B:39:0x01fd, B:40:0x0203, B:42:0x0216, B:44:0x0232, B:45:0x024d, B:48:0x0236, B:50:0x023f, B:51:0x021a, B:53:0x0222, B:55:0x0168, B:57:0x0170, B:60:0x0177, B:61:0x0195, B:62:0x0115, B:64:0x011d, B:65:0x0133, B:66:0x00a6, B:67:0x00b5, B:68:0x00c4, B:69:0x00d3, B:70:0x00e2, B:71:0x00f1), top: B:3:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0236 A[Catch: all -> 0x002c, TryCatch #0 {all -> 0x002c, blocks: (B:4:0x000b, B:6:0x0014, B:7:0x002f, B:9:0x0038, B:15:0x004e, B:16:0x0065, B:19:0x0067, B:20:0x00a2, B:22:0x0100, B:24:0x010b, B:26:0x0138, B:28:0x014b, B:30:0x0151, B:32:0x0158, B:33:0x01a4, B:35:0x01e6, B:36:0x01ed, B:37:0x01f0, B:38:0x01f6, B:39:0x01fd, B:40:0x0203, B:42:0x0216, B:44:0x0232, B:45:0x024d, B:48:0x0236, B:50:0x023f, B:51:0x021a, B:53:0x0222, B:55:0x0168, B:57:0x0170, B:60:0x0177, B:61:0x0195, B:62:0x0115, B:64:0x011d, B:65:0x0133, B:66:0x00a6, B:67:0x00b5, B:68:0x00c4, B:69:0x00d3, B:70:0x00e2, B:71:0x00f1), top: B:3:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x021a A[Catch: all -> 0x002c, TryCatch #0 {all -> 0x002c, blocks: (B:4:0x000b, B:6:0x0014, B:7:0x002f, B:9:0x0038, B:15:0x004e, B:16:0x0065, B:19:0x0067, B:20:0x00a2, B:22:0x0100, B:24:0x010b, B:26:0x0138, B:28:0x014b, B:30:0x0151, B:32:0x0158, B:33:0x01a4, B:35:0x01e6, B:36:0x01ed, B:37:0x01f0, B:38:0x01f6, B:39:0x01fd, B:40:0x0203, B:42:0x0216, B:44:0x0232, B:45:0x024d, B:48:0x0236, B:50:0x023f, B:51:0x021a, B:53:0x0222, B:55:0x0168, B:57:0x0170, B:60:0x0177, B:61:0x0195, B:62:0x0115, B:64:0x011d, B:65:0x0133, B:66:0x00a6, B:67:0x00b5, B:68:0x00c4, B:69:0x00d3, B:70:0x00e2, B:71:0x00f1), top: B:3:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0168 A[Catch: all -> 0x002c, TryCatch #0 {all -> 0x002c, blocks: (B:4:0x000b, B:6:0x0014, B:7:0x002f, B:9:0x0038, B:15:0x004e, B:16:0x0065, B:19:0x0067, B:20:0x00a2, B:22:0x0100, B:24:0x010b, B:26:0x0138, B:28:0x014b, B:30:0x0151, B:32:0x0158, B:33:0x01a4, B:35:0x01e6, B:36:0x01ed, B:37:0x01f0, B:38:0x01f6, B:39:0x01fd, B:40:0x0203, B:42:0x0216, B:44:0x0232, B:45:0x024d, B:48:0x0236, B:50:0x023f, B:51:0x021a, B:53:0x0222, B:55:0x0168, B:57:0x0170, B:60:0x0177, B:61:0x0195, B:62:0x0115, B:64:0x011d, B:65:0x0133, B:66:0x00a6, B:67:0x00b5, B:68:0x00c4, B:69:0x00d3, B:70:0x00e2, B:71:0x00f1), top: B:3:0x000b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void stopSyncEvent(long j, long j2, java.lang.String str, long j3, long j4, java.lang.String str2, int i) {
        com.android.server.content.SyncStorageEngine.SyncHistoryItem syncHistoryItem;
        boolean z;
        boolean z2;
        synchronized (this.mAuthorities) {
            try {
                if (android.util.Log.isLoggable("SyncManager", 2)) {
                    android.util.Slog.v("SyncManager", "stopSyncEvent: historyId=" + j);
                }
                int size = this.mSyncHistory.size();
                while (true) {
                    if (size <= 0) {
                        syncHistoryItem = null;
                        break;
                    }
                    size--;
                    syncHistoryItem = this.mSyncHistory.get(size);
                    if (syncHistoryItem.historyId == j) {
                        break;
                    }
                }
                if (syncHistoryItem == null) {
                    android.util.Slog.w("SyncManager", "stopSyncEvent: no history for id " + j);
                    return;
                }
                syncHistoryItem.elapsedTime = j2;
                syncHistoryItem.event = 1;
                syncHistoryItem.mesg = str;
                syncHistoryItem.downstreamActivity = j3;
                syncHistoryItem.upstreamActivity = j4;
                android.content.SyncStatusInfo orCreateSyncStatusLocked = getOrCreateSyncStatusLocked(syncHistoryItem.authorityId);
                orCreateSyncStatusLocked.maybeResetTodayStats(isClockValid(), false);
                orCreateSyncStatusLocked.totalStats.numSyncs++;
                orCreateSyncStatusLocked.todayStats.numSyncs++;
                orCreateSyncStatusLocked.totalStats.totalElapsedTime += j2;
                orCreateSyncStatusLocked.todayStats.totalElapsedTime += j2;
                switch (syncHistoryItem.source) {
                    case 0:
                        orCreateSyncStatusLocked.totalStats.numSourceOther++;
                        orCreateSyncStatusLocked.todayStats.numSourceOther++;
                        break;
                    case 1:
                        orCreateSyncStatusLocked.totalStats.numSourceLocal++;
                        orCreateSyncStatusLocked.todayStats.numSourceLocal++;
                        break;
                    case 2:
                        orCreateSyncStatusLocked.totalStats.numSourcePoll++;
                        orCreateSyncStatusLocked.todayStats.numSourcePoll++;
                        break;
                    case 3:
                        orCreateSyncStatusLocked.totalStats.numSourceUser++;
                        orCreateSyncStatusLocked.todayStats.numSourceUser++;
                        break;
                    case 4:
                        orCreateSyncStatusLocked.totalStats.numSourcePeriodic++;
                        orCreateSyncStatusLocked.todayStats.numSourcePeriodic++;
                        break;
                    case 5:
                        orCreateSyncStatusLocked.totalStats.numSourceFeed++;
                        orCreateSyncStatusLocked.todayStats.numSourceFeed++;
                        break;
                }
                int currentDayLocked = getCurrentDayLocked();
                if (this.mDayStats[0] == null) {
                    this.mDayStats[0] = new com.android.server.content.SyncStorageEngine.DayStats(currentDayLocked);
                } else {
                    if (currentDayLocked != this.mDayStats[0].day) {
                        java.lang.System.arraycopy(this.mDayStats, 0, this.mDayStats, 1, this.mDayStats.length - 1);
                        this.mDayStats[0] = new com.android.server.content.SyncStorageEngine.DayStats(currentDayLocked);
                        z = true;
                        com.android.server.content.SyncStorageEngine.DayStats dayStats = this.mDayStats[0];
                        long j5 = syncHistoryItem.eventTime + j2;
                        if (!MESG_SUCCESS.equals(str)) {
                            z2 = orCreateSyncStatusLocked.lastSuccessTime == 0 || orCreateSyncStatusLocked.lastFailureTime != 0;
                            orCreateSyncStatusLocked.setLastSuccess(syncHistoryItem.source, j5);
                            dayStats.successCount++;
                            dayStats.successTime += j2;
                        } else if (!MESG_CANCELED.equals(str)) {
                            z2 = orCreateSyncStatusLocked.lastFailureTime == 0;
                            orCreateSyncStatusLocked.totalStats.numFailures++;
                            orCreateSyncStatusLocked.todayStats.numFailures++;
                            orCreateSyncStatusLocked.setLastFailure(syncHistoryItem.source, j5, str);
                            dayStats.failureCount++;
                            dayStats.failureTime += j2;
                        } else {
                            orCreateSyncStatusLocked.totalStats.numCancels++;
                            orCreateSyncStatusLocked.todayStats.numCancels++;
                            z2 = true;
                        }
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        sb.append("" + str + " Source=" + SOURCES[syncHistoryItem.source] + " Elapsed=");
                        com.android.server.content.SyncManager.formatDurationHMS(sb, j2);
                        sb.append(" Reason=");
                        sb.append(com.android.server.content.SyncOperation.reasonToString(null, syncHistoryItem.reason));
                        if (syncHistoryItem.syncExemptionFlag != 0) {
                            sb.append(" Exemption=");
                            switch (syncHistoryItem.syncExemptionFlag) {
                                case 1:
                                    sb.append("fg");
                                    break;
                                case 2:
                                    sb.append("top");
                                    break;
                                default:
                                    sb.append(syncHistoryItem.syncExemptionFlag);
                                    break;
                            }
                        }
                        sb.append(" Extras=");
                        com.android.server.content.SyncOperation.extrasToStringBuilder(syncHistoryItem.extras, sb);
                        orCreateSyncStatusLocked.addEvent(sb.toString());
                        if (z2) {
                            if (!this.mHandler.hasMessages(1)) {
                                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), 600000L);
                            }
                        } else {
                            writeStatusLocked();
                        }
                        if (!z) {
                            writeStatisticsLocked();
                        } else if (!this.mHandler.hasMessages(2)) {
                            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(2), 1800000L);
                        }
                        reportChange(8, str2, i);
                    }
                    com.android.server.content.SyncStorageEngine.DayStats dayStats2 = this.mDayStats[0];
                }
                z = false;
                com.android.server.content.SyncStorageEngine.DayStats dayStats3 = this.mDayStats[0];
                long j52 = syncHistoryItem.eventTime + j2;
                if (!MESG_SUCCESS.equals(str)) {
                }
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                sb2.append("" + str + " Source=" + SOURCES[syncHistoryItem.source] + " Elapsed=");
                com.android.server.content.SyncManager.formatDurationHMS(sb2, j2);
                sb2.append(" Reason=");
                sb2.append(com.android.server.content.SyncOperation.reasonToString(null, syncHistoryItem.reason));
                if (syncHistoryItem.syncExemptionFlag != 0) {
                }
                sb2.append(" Extras=");
                com.android.server.content.SyncOperation.extrasToStringBuilder(syncHistoryItem.extras, sb2);
                orCreateSyncStatusLocked.addEvent(sb2.toString());
                if (z2) {
                }
                if (!z) {
                }
                reportChange(8, str2, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private java.util.List<android.content.SyncInfo> getCurrentSyncs(int i) {
        java.util.List<android.content.SyncInfo> currentSyncsLocked;
        synchronized (this.mAuthorities) {
            currentSyncsLocked = getCurrentSyncsLocked(i);
        }
        return currentSyncsLocked;
    }

    public java.util.List<android.content.SyncInfo> getCurrentSyncsCopy(int i, boolean z) {
        java.util.ArrayList arrayList;
        android.content.SyncInfo syncInfo;
        synchronized (this.mAuthorities) {
            try {
                java.util.List<android.content.SyncInfo> currentSyncsLocked = getCurrentSyncsLocked(i);
                arrayList = new java.util.ArrayList();
                for (android.content.SyncInfo syncInfo2 : currentSyncsLocked) {
                    if (!z) {
                        syncInfo = android.content.SyncInfo.createAccountRedacted(syncInfo2.authorityId, syncInfo2.authority, syncInfo2.startTime);
                    } else {
                        syncInfo = new android.content.SyncInfo(syncInfo2);
                    }
                    arrayList.add(syncInfo);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    private java.util.List<android.content.SyncInfo> getCurrentSyncsLocked(int i) {
        java.util.ArrayList<android.content.SyncInfo> arrayList = this.mCurrentSyncs.get(i);
        if (arrayList == null) {
            java.util.ArrayList<android.content.SyncInfo> arrayList2 = new java.util.ArrayList<>();
            this.mCurrentSyncs.put(i, arrayList2);
            return arrayList2;
        }
        return arrayList;
    }

    public android.util.Pair<com.android.server.content.SyncStorageEngine.AuthorityInfo, android.content.SyncStatusInfo> getCopyOfAuthorityWithSyncStatus(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        android.util.Pair<com.android.server.content.SyncStorageEngine.AuthorityInfo, android.content.SyncStatusInfo> createCopyPairOfAuthorityWithSyncStatusLocked;
        synchronized (this.mAuthorities) {
            createCopyPairOfAuthorityWithSyncStatusLocked = createCopyPairOfAuthorityWithSyncStatusLocked(getOrCreateAuthorityLocked(endPoint, -1, true));
        }
        return createCopyPairOfAuthorityWithSyncStatusLocked;
    }

    public android.content.SyncStatusInfo getStatusByAuthority(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        if (endPoint.account == null || endPoint.provider == null) {
            return null;
        }
        synchronized (this.mAuthorities) {
            try {
                int size = this.mSyncStatus.size();
                for (int i = 0; i < size; i++) {
                    android.content.SyncStatusInfo valueAt = this.mSyncStatus.valueAt(i);
                    com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo = this.mAuthorities.get(valueAt.authorityId);
                    if (authorityInfo != null && authorityInfo.target.matchesSpec(endPoint)) {
                        return valueAt;
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isSyncPending(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        synchronized (this.mAuthorities) {
            try {
                int size = this.mSyncStatus.size();
                for (int i = 0; i < size; i++) {
                    android.content.SyncStatusInfo valueAt = this.mSyncStatus.valueAt(i);
                    com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo = this.mAuthorities.get(valueAt.authorityId);
                    if (authorityInfo != null && authorityInfo.target.matchesSpec(endPoint) && valueAt.pending) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.ArrayList<com.android.server.content.SyncStorageEngine.SyncHistoryItem> getSyncHistory() {
        java.util.ArrayList<com.android.server.content.SyncStorageEngine.SyncHistoryItem> arrayList;
        synchronized (this.mAuthorities) {
            try {
                int size = this.mSyncHistory.size();
                arrayList = new java.util.ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    arrayList.add(this.mSyncHistory.get(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public com.android.server.content.SyncStorageEngine.DayStats[] getDayStatistics() {
        com.android.server.content.SyncStorageEngine.DayStats[] dayStatsArr;
        synchronized (this.mAuthorities) {
            int length = this.mDayStats.length;
            dayStatsArr = new com.android.server.content.SyncStorageEngine.DayStats[length];
            java.lang.System.arraycopy(this.mDayStats, 0, dayStatsArr, 0, length);
        }
        return dayStatsArr;
    }

    private android.util.Pair<com.android.server.content.SyncStorageEngine.AuthorityInfo, android.content.SyncStatusInfo> createCopyPairOfAuthorityWithSyncStatusLocked(com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo) {
        return android.util.Pair.create(new com.android.server.content.SyncStorageEngine.AuthorityInfo(authorityInfo), new android.content.SyncStatusInfo(getOrCreateSyncStatusLocked(authorityInfo.ident)));
    }

    private int getCurrentDayLocked() {
        this.mCal.setTimeInMillis(java.lang.System.currentTimeMillis());
        int i = this.mCal.get(6);
        if (this.mYear != this.mCal.get(1)) {
            this.mYear = this.mCal.get(1);
            this.mCal.clear();
            this.mCal.set(1, this.mYear);
            this.mYearInDays = (int) (this.mCal.getTimeInMillis() / 86400000);
        }
        return i + this.mYearInDays;
    }

    private com.android.server.content.SyncStorageEngine.AuthorityInfo getAuthorityLocked(com.android.server.content.SyncStorageEngine.EndPoint endPoint, java.lang.String str) {
        android.accounts.AccountAndUser accountAndUser = new android.accounts.AccountAndUser(endPoint.account, endPoint.userId);
        com.android.server.content.SyncStorageEngine.AccountInfo accountInfo = this.mAccounts.get(accountAndUser);
        if (accountInfo == null) {
            if (str != null && android.util.Log.isLoggable("SyncManager", 2)) {
                android.util.Slog.v("SyncManager", str + ": unknown account " + accountAndUser);
            }
            return null;
        }
        com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo = accountInfo.authorities.get(endPoint.provider);
        if (authorityInfo == null) {
            if (str != null && android.util.Log.isLoggable("SyncManager", 2)) {
                android.util.Slog.v("SyncManager", str + ": unknown provider " + endPoint.provider);
            }
            return null;
        }
        return authorityInfo;
    }

    private com.android.server.content.SyncStorageEngine.AuthorityInfo getOrCreateAuthorityLocked(com.android.server.content.SyncStorageEngine.EndPoint endPoint, int i, boolean z) {
        android.accounts.AccountAndUser accountAndUser = new android.accounts.AccountAndUser(endPoint.account, endPoint.userId);
        com.android.server.content.SyncStorageEngine.AccountInfo accountInfo = this.mAccounts.get(accountAndUser);
        if (accountInfo == null) {
            accountInfo = new com.android.server.content.SyncStorageEngine.AccountInfo(accountAndUser);
            this.mAccounts.put(accountAndUser, accountInfo);
        }
        com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo = accountInfo.authorities.get(endPoint.provider);
        if (authorityInfo == null) {
            com.android.server.content.SyncStorageEngine.AuthorityInfo createAuthorityLocked = createAuthorityLocked(endPoint, i, z);
            accountInfo.authorities.put(endPoint.provider, createAuthorityLocked);
            return createAuthorityLocked;
        }
        return authorityInfo;
    }

    private com.android.server.content.SyncStorageEngine.AuthorityInfo createAuthorityLocked(com.android.server.content.SyncStorageEngine.EndPoint endPoint, int i, boolean z) {
        if (i < 0) {
            i = this.mNextAuthorityId;
            this.mNextAuthorityId++;
            z = true;
        }
        if (android.util.Log.isLoggable("SyncManager", 2)) {
            android.util.Slog.v("SyncManager", "created a new AuthorityInfo for " + endPoint);
        }
        com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo = new com.android.server.content.SyncStorageEngine.AuthorityInfo(endPoint, i);
        this.mAuthorities.put(i, authorityInfo);
        if (z) {
            writeAccountInfoLocked();
        }
        return authorityInfo;
    }

    public void removeAuthority(com.android.server.content.SyncStorageEngine.EndPoint endPoint) {
        synchronized (this.mAuthorities) {
            removeAuthorityLocked(endPoint.account, endPoint.userId, endPoint.provider, true);
        }
    }

    private void removeAuthorityLocked(android.accounts.Account account, int i, java.lang.String str, boolean z) {
        com.android.server.content.SyncStorageEngine.AuthorityInfo remove;
        com.android.server.content.SyncStorageEngine.AccountInfo accountInfo = this.mAccounts.get(new android.accounts.AccountAndUser(account, i));
        if (accountInfo != null && (remove = accountInfo.authorities.remove(str)) != null) {
            if (this.mAuthorityRemovedListener != null) {
                this.mAuthorityRemovedListener.onAuthorityRemoved(remove.target);
            }
            this.mAuthorities.remove(remove.ident);
            if (z) {
                writeAccountInfoLocked();
            }
        }
    }

    private android.content.SyncStatusInfo getOrCreateSyncStatusLocked(int i) {
        android.content.SyncStatusInfo syncStatusInfo = this.mSyncStatus.get(i);
        if (syncStatusInfo == null) {
            android.content.SyncStatusInfo syncStatusInfo2 = new android.content.SyncStatusInfo(i);
            this.mSyncStatus.put(i, syncStatusInfo2);
            return syncStatusInfo2;
        }
        return syncStatusInfo;
    }

    public void writeAllState() {
        synchronized (this.mAuthorities) {
            writeStatusLocked();
            writeStatisticsLocked();
        }
    }

    public boolean shouldGrantSyncAdaptersAccountAccess() {
        return this.mGrantSyncAdaptersAccountAccess;
    }

    public void clearAndReadState() {
        synchronized (this.mAuthorities) {
            this.mAuthorities.clear();
            this.mAccounts.clear();
            this.mServices.clear();
            this.mSyncStatus.clear();
            this.mSyncHistory.clear();
            readAccountInfoLocked();
            readStatusLocked();
            readStatisticsLocked();
            writeAccountInfoLocked();
            writeStatusLocked();
            writeStatisticsLocked();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x01db A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:108:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x01ed A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:120:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01af A[Catch: all -> 0x01b3, TRY_ENTER, TryCatch #10 {all -> 0x01b3, blocks: (B:85:0x01af, B:95:0x01b6, B:99:0x01cc), top: B:2:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01c5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01b6 A[Catch: all -> 0x01b3, TRY_LEAVE, TryCatch #10 {all -> 0x01b3, blocks: (B:85:0x01af, B:95:0x01b6, B:99:0x01cc), top: B:2:0x000e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readAccountInfoLocked() {
        java.lang.Throwable th;
        java.io.FileInputStream fileInputStream;
        java.io.FileInputStream fileInputStream2;
        java.io.FileInputStream fileInputStream3;
        int i;
        int i2 = -1;
        java.io.FileInputStream fileInputStream4 = null;
        try {
            try {
                java.io.FileInputStream openRead = this.mAccountInfoFile.openRead();
                try {
                    try {
                        if (android.util.Log.isLoggable(TAG_FILE, 2)) {
                            try {
                                try {
                                    android.util.Slog.v(TAG_FILE, "Reading " + this.mAccountInfoFile.getBaseFile());
                                } catch (java.io.IOException e) {
                                    e = e;
                                    fileInputStream2 = openRead;
                                    if (fileInputStream2 != null) {
                                    }
                                    this.mNextAuthorityId = java.lang.Math.max(i2 + 1, this.mNextAuthorityId);
                                    if (fileInputStream2 == null) {
                                    }
                                } catch (java.lang.Throwable th2) {
                                    th = th2;
                                    fileInputStream4 = openRead;
                                    this.mNextAuthorityId = java.lang.Math.max(i2 + 1, this.mNextAuthorityId);
                                    if (fileInputStream4 != null) {
                                    }
                                }
                            } catch (java.lang.ArrayIndexOutOfBoundsException | org.xmlpull.v1.XmlPullParserException e2) {
                                e = e2;
                                fileInputStream = openRead;
                                android.util.Slog.w("SyncManager", "Error reading accounts", e);
                                this.mNextAuthorityId = java.lang.Math.max(i2 + 1, this.mNextAuthorityId);
                                if (fileInputStream == null) {
                                    try {
                                        fileInputStream.close();
                                        return;
                                    } catch (java.io.IOException e3) {
                                        return;
                                    }
                                }
                                return;
                            }
                        }
                        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                        int eventType = resolvePullParser.getEventType();
                        while (eventType != 2 && eventType != 1) {
                            eventType = resolvePullParser.next();
                        }
                        if (eventType == 1) {
                            android.util.Slog.i("SyncManager", "No initial accounts");
                            this.mNextAuthorityId = java.lang.Math.max(0, this.mNextAuthorityId);
                            if (openRead != null) {
                                try {
                                    openRead.close();
                                    return;
                                } catch (java.io.IOException e4) {
                                    return;
                                }
                            }
                            return;
                        }
                        if ("accounts".equals(resolvePullParser.getName())) {
                            boolean attributeBoolean = resolvePullParser.getAttributeBoolean((java.lang.String) null, XML_ATTR_LISTEN_FOR_TICKLES, true);
                            int attributeInt = resolvePullParser.getAttributeInt((java.lang.String) null, "version", 0);
                            if (attributeInt < 3) {
                                this.mGrantSyncAdaptersAccountAccess = true;
                            }
                            this.mNextAuthorityId = java.lang.Math.max(this.mNextAuthorityId, resolvePullParser.getAttributeInt((java.lang.String) null, XML_ATTR_NEXT_AUTHORITY_ID, 0));
                            this.mSyncRandomOffset = resolvePullParser.getAttributeInt((java.lang.String) null, XML_ATTR_SYNC_RANDOM_OFFSET, 0);
                            if (this.mSyncRandomOffset == 0) {
                                try {
                                    fileInputStream3 = openRead;
                                } catch (java.io.IOException e5) {
                                    e = e5;
                                    fileInputStream3 = openRead;
                                } catch (java.lang.ArrayIndexOutOfBoundsException | org.xmlpull.v1.XmlPullParserException e6) {
                                    e = e6;
                                    fileInputStream3 = openRead;
                                } catch (java.lang.Throwable th3) {
                                    th = th3;
                                    fileInputStream3 = openRead;
                                }
                                try {
                                    this.mSyncRandomOffset = new java.util.Random(java.lang.System.currentTimeMillis()).nextInt(86400);
                                } catch (java.io.IOException e7) {
                                    e = e7;
                                    fileInputStream2 = fileInputStream3;
                                    if (fileInputStream2 != null) {
                                    }
                                    this.mNextAuthorityId = java.lang.Math.max(i2 + 1, this.mNextAuthorityId);
                                    if (fileInputStream2 == null) {
                                    }
                                } catch (java.lang.ArrayIndexOutOfBoundsException | org.xmlpull.v1.XmlPullParserException e8) {
                                    e = e8;
                                    fileInputStream = fileInputStream3;
                                    android.util.Slog.w("SyncManager", "Error reading accounts", e);
                                    this.mNextAuthorityId = java.lang.Math.max(i2 + 1, this.mNextAuthorityId);
                                    if (fileInputStream == null) {
                                    }
                                } catch (java.lang.Throwable th4) {
                                    th = th4;
                                    th = th;
                                    fileInputStream4 = fileInputStream3;
                                    this.mNextAuthorityId = java.lang.Math.max(i2 + 1, this.mNextAuthorityId);
                                    if (fileInputStream4 != null) {
                                    }
                                }
                            } else {
                                fileInputStream3 = openRead;
                            }
                            try {
                                this.mMasterSyncAutomatically.put(0, java.lang.Boolean.valueOf(attributeBoolean));
                                int next = resolvePullParser.next();
                                com.android.server.content.SyncStorageEngine.AccountAuthorityValidator accountAuthorityValidator = new com.android.server.content.SyncStorageEngine.AccountAuthorityValidator(this.mContext);
                                int i3 = -1;
                                android.content.PeriodicSync periodicSync = null;
                                com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo = null;
                                while (true) {
                                    if (next == 2) {
                                        try {
                                            java.lang.String name = resolvePullParser.getName();
                                            if (resolvePullParser.getDepth() == 2) {
                                                if ("authority".equals(name)) {
                                                    authorityInfo = parseAuthority(resolvePullParser, attributeInt, accountAuthorityValidator);
                                                    if (authorityInfo == null) {
                                                        android.util.EventLog.writeEvent(1397638484, "26513719", java.lang.Integer.valueOf(i2), "Malformed authority");
                                                    } else if (authorityInfo.ident > i3) {
                                                        i3 = authorityInfo.ident;
                                                        periodicSync = null;
                                                    }
                                                    periodicSync = null;
                                                } else if (XML_TAG_LISTEN_FOR_TICKLES.equals(name)) {
                                                    parseListenForTickles(resolvePullParser);
                                                }
                                            } else if (resolvePullParser.getDepth() == 3) {
                                                if ("periodicSync".equals(name) && authorityInfo != null) {
                                                    periodicSync = parsePeriodicSync(resolvePullParser, authorityInfo);
                                                }
                                            } else if (resolvePullParser.getDepth() == 4 && periodicSync != null && "extra".equals(name)) {
                                                parseExtra(resolvePullParser, periodicSync.extras);
                                            }
                                        } catch (java.io.IOException e9) {
                                            e = e9;
                                            i2 = i3;
                                            fileInputStream2 = fileInputStream3;
                                            if (fileInputStream2 != null) {
                                            }
                                            this.mNextAuthorityId = java.lang.Math.max(i2 + 1, this.mNextAuthorityId);
                                            if (fileInputStream2 == null) {
                                            }
                                        } catch (java.lang.ArrayIndexOutOfBoundsException | org.xmlpull.v1.XmlPullParserException e10) {
                                            e = e10;
                                            i2 = i3;
                                            fileInputStream = fileInputStream3;
                                            android.util.Slog.w("SyncManager", "Error reading accounts", e);
                                            this.mNextAuthorityId = java.lang.Math.max(i2 + 1, this.mNextAuthorityId);
                                            if (fileInputStream == null) {
                                            }
                                        } catch (java.lang.Throwable th5) {
                                            th = th5;
                                            i2 = i3;
                                            fileInputStream4 = fileInputStream3;
                                            this.mNextAuthorityId = java.lang.Math.max(i2 + 1, this.mNextAuthorityId);
                                            if (fileInputStream4 != null) {
                                            }
                                        }
                                    }
                                    next = resolvePullParser.next();
                                    if (next == 1) {
                                        break;
                                    } else {
                                        i2 = -1;
                                    }
                                }
                                i = i3;
                            } catch (java.io.IOException e11) {
                                e = e11;
                                fileInputStream2 = fileInputStream3;
                                i2 = -1;
                                if (fileInputStream2 != null) {
                                    android.util.Slog.i("SyncManager", "No initial accounts");
                                } else {
                                    android.util.Slog.w("SyncManager", "Error reading accounts", e);
                                }
                                this.mNextAuthorityId = java.lang.Math.max(i2 + 1, this.mNextAuthorityId);
                                if (fileInputStream2 == null) {
                                    try {
                                        fileInputStream2.close();
                                        return;
                                    } catch (java.io.IOException e12) {
                                        return;
                                    }
                                }
                                return;
                            } catch (java.lang.ArrayIndexOutOfBoundsException | org.xmlpull.v1.XmlPullParserException e13) {
                                e = e13;
                                fileInputStream = fileInputStream3;
                                i2 = -1;
                                android.util.Slog.w("SyncManager", "Error reading accounts", e);
                                this.mNextAuthorityId = java.lang.Math.max(i2 + 1, this.mNextAuthorityId);
                                if (fileInputStream == null) {
                                }
                            } catch (java.lang.Throwable th6) {
                                th = th6;
                                th = th;
                                fileInputStream4 = fileInputStream3;
                                i2 = -1;
                                this.mNextAuthorityId = java.lang.Math.max(i2 + 1, this.mNextAuthorityId);
                                if (fileInputStream4 != null) {
                                    throw th;
                                }
                                try {
                                    fileInputStream4.close();
                                    throw th;
                                } catch (java.io.IOException e14) {
                                    throw th;
                                }
                            }
                        } else {
                            fileInputStream3 = openRead;
                            i = -1;
                        }
                        this.mNextAuthorityId = java.lang.Math.max(i + 1, this.mNextAuthorityId);
                        if (fileInputStream3 != null) {
                            try {
                                fileInputStream3.close();
                            } catch (java.io.IOException e15) {
                            }
                        }
                        maybeMigrateSettingsForRenamedAuthorities();
                    } catch (java.io.IOException e16) {
                        e = e16;
                        fileInputStream3 = openRead;
                    } catch (java.lang.Throwable th7) {
                        th = th7;
                        fileInputStream3 = openRead;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException | org.xmlpull.v1.XmlPullParserException e17) {
                    e = e17;
                    fileInputStream3 = openRead;
                }
            } catch (java.io.IOException e18) {
                e = e18;
                i2 = -1;
                fileInputStream2 = null;
            } catch (java.lang.ArrayIndexOutOfBoundsException | org.xmlpull.v1.XmlPullParserException e19) {
                e = e19;
                i2 = -1;
                fileInputStream = null;
            } catch (java.lang.Throwable th8) {
                th = th8;
                i2 = -1;
                fileInputStream4 = null;
            }
        } catch (java.lang.Throwable th9) {
            th = th9;
        }
    }

    private void maybeDeleteLegacyPendingInfoLocked(java.io.File file) {
        java.io.File file2 = new java.io.File(file, "pending.bin");
        if (!file2.exists()) {
            return;
        }
        file2.delete();
    }

    private boolean maybeMigrateSettingsForRenamedAuthorities() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = this.mAuthorities.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            com.android.server.content.SyncStorageEngine.AuthorityInfo valueAt = this.mAuthorities.valueAt(i);
            java.lang.String str = sAuthorityRenames.get(valueAt.target.provider);
            if (str != null) {
                arrayList.add(valueAt);
                if (valueAt.enabled) {
                    com.android.server.content.SyncStorageEngine.EndPoint endPoint = new com.android.server.content.SyncStorageEngine.EndPoint(valueAt.target.account, str, valueAt.target.userId);
                    if (getAuthorityLocked(endPoint, "cleanup") == null) {
                        getOrCreateAuthorityLocked(endPoint, -1, false).enabled = true;
                        z = true;
                    }
                }
            }
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo = (com.android.server.content.SyncStorageEngine.AuthorityInfo) it.next();
            removeAuthorityLocked(authorityInfo.target.account, authorityInfo.target.userId, authorityInfo.target.provider, false);
            z = true;
        }
        return z;
    }

    private void parseListenForTickles(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        int i;
        try {
            i = typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTR_USER);
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e("SyncManager", "error parsing the user for listen-for-tickles", e);
            i = 0;
        }
        this.mMasterSyncAutomatically.put(i, java.lang.Boolean.valueOf(typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "enabled", true)));
    }

    private com.android.server.content.SyncStorageEngine.AuthorityInfo parseAuthority(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, com.android.server.content.SyncStorageEngine.AccountAuthorityValidator accountAuthorityValidator) throws org.xmlpull.v1.XmlPullParserException {
        int i2;
        int i3;
        java.lang.String str;
        int i4;
        int parseInt;
        com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo = null;
        try {
            i2 = typedXmlPullParser.getAttributeInt((java.lang.String) null, "id");
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e("SyncManager", "error parsing the id of the authority", e);
            i2 = -1;
        }
        if (i2 >= 0) {
            java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "authority");
            boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "enabled", true);
            java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "syncable");
            java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "account");
            java.lang.String attributeValue4 = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE);
            int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTR_USER, 0);
            java.lang.String attributeValue5 = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.pm.Settings.ATTR_PACKAGE);
            java.lang.String attributeValue6 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "class");
            if (attributeValue4 == null && attributeValue5 == null) {
                attributeValue2 = java.lang.String.valueOf(-1);
                attributeValue4 = "com.google";
            }
            authorityInfo = this.mAuthorities.get(i2);
            if (!android.util.Log.isLoggable(TAG_FILE, 2)) {
                i3 = i2;
                str = "SyncManager";
            } else {
                str = "SyncManager";
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                i3 = i2;
                sb.append("Adding authority: account=");
                sb.append(attributeValue3);
                sb.append(" accountType=");
                sb.append(attributeValue4);
                sb.append(" auth=");
                sb.append(attributeValue);
                sb.append(" package=");
                sb.append(attributeValue5);
                sb.append(" class=");
                sb.append(attributeValue6);
                sb.append(" user=");
                sb.append(attributeInt);
                sb.append(" enabled=");
                sb.append(attributeBoolean);
                sb.append(" syncable=");
                sb.append(attributeValue2);
                android.util.Slog.v(TAG_FILE, sb.toString());
            }
            if (authorityInfo == null) {
                if (android.util.Log.isLoggable(TAG_FILE, 2)) {
                    android.util.Slog.v(TAG_FILE, "Creating authority entry");
                }
                if (attributeValue3 == null || attributeValue == null) {
                    i4 = 0;
                } else {
                    com.android.server.content.SyncStorageEngine.EndPoint endPoint = new com.android.server.content.SyncStorageEngine.EndPoint(new android.accounts.Account(attributeValue3, attributeValue4), attributeValue, attributeInt);
                    if (!accountAuthorityValidator.isAccountValid(endPoint.account, attributeInt)) {
                        i4 = 0;
                    } else if (accountAuthorityValidator.isAuthorityValid(attributeValue, attributeInt)) {
                        i4 = 0;
                        com.android.server.content.SyncStorageEngine.AuthorityInfo orCreateAuthorityLocked = getOrCreateAuthorityLocked(endPoint, i3, false);
                        if (i > 0) {
                            orCreateAuthorityLocked.periodicSyncs.clear();
                        }
                        authorityInfo = orCreateAuthorityLocked;
                    } else {
                        i4 = 0;
                    }
                    android.util.EventLog.writeEvent(1397638484, "35028827", -1, "account:" + endPoint.account + " provider:" + attributeValue + " user:" + attributeInt);
                }
            } else {
                i4 = 0;
            }
            if (authorityInfo != null) {
                authorityInfo.enabled = attributeBoolean;
                if (attributeValue2 == null) {
                    parseInt = -1;
                } else {
                    try {
                        parseInt = java.lang.Integer.parseInt(attributeValue2);
                    } catch (java.lang.NumberFormatException e2) {
                        if ("unknown".equals(attributeValue2)) {
                            authorityInfo.syncable = -1;
                        } else {
                            authorityInfo.syncable = java.lang.Boolean.parseBoolean(attributeValue2) ? 1 : i4;
                        }
                    }
                }
                authorityInfo.syncable = parseInt;
            } else {
                android.util.Slog.w(str, "Failure adding authority: auth=" + attributeValue + " enabled=" + attributeBoolean + " syncable=" + attributeValue2);
            }
        }
        return authorityInfo;
    }

    private android.content.PeriodicSync parsePeriodicSync(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo) {
        long j;
        android.os.Bundle bundle = new android.os.Bundle();
        try {
            long attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, "period");
            try {
                j = typedXmlPullParser.getAttributeLong((java.lang.String) null, "flex");
            } catch (org.xmlpull.v1.XmlPullParserException e) {
                long calculateDefaultFlexTime = calculateDefaultFlexTime(attributeLong);
                android.util.Slog.e("SyncManager", "Error formatting value parsed for periodic sync flex, using default: " + calculateDefaultFlexTime, e);
                j = calculateDefaultFlexTime;
            }
            android.content.PeriodicSync periodicSync = new android.content.PeriodicSync(authorityInfo.target.account, authorityInfo.target.provider, bundle, attributeLong, j);
            authorityInfo.periodicSyncs.add(periodicSync);
            return periodicSync;
        } catch (org.xmlpull.v1.XmlPullParserException e2) {
            android.util.Slog.e("SyncManager", "error parsing the period of a periodic sync", e2);
            return null;
        }
    }

    private void parseExtra(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.os.Bundle bundle) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE);
        try {
            if ("long".equals(attributeValue2)) {
                bundle.putLong(attributeValue, typedXmlPullParser.getAttributeLong((java.lang.String) null, "value1"));
            } else if ("integer".equals(attributeValue2)) {
                bundle.putInt(attributeValue, typedXmlPullParser.getAttributeInt((java.lang.String) null, "value1"));
            } else if ("double".equals(attributeValue2)) {
                bundle.putDouble(attributeValue, typedXmlPullParser.getAttributeDouble((java.lang.String) null, "value1"));
            } else if ("float".equals(attributeValue2)) {
                bundle.putFloat(attributeValue, typedXmlPullParser.getAttributeFloat((java.lang.String) null, "value1"));
            } else if ("boolean".equals(attributeValue2)) {
                bundle.putBoolean(attributeValue, typedXmlPullParser.getAttributeBoolean((java.lang.String) null, "value1"));
            } else if ("string".equals(attributeValue2)) {
                bundle.putString(attributeValue, typedXmlPullParser.getAttributeValue((java.lang.String) null, "value1"));
            } else if ("account".equals(attributeValue2)) {
                bundle.putParcelable(attributeValue, new android.accounts.Account(typedXmlPullParser.getAttributeValue((java.lang.String) null, "value1"), typedXmlPullParser.getAttributeValue((java.lang.String) null, "value2")));
            }
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e("SyncManager", "error parsing bundle value", e);
        }
    }

    private void writeAccountInfoLocked() {
        if (android.util.Log.isLoggable(TAG_FILE, 2)) {
            android.util.Slog.v(TAG_FILE, "Writing new " + this.mAccountInfoFile.getBaseFile());
        }
        java.io.FileOutputStream fileOutputStream = null;
        try {
            java.io.FileOutputStream startWrite = this.mAccountInfoFile.startWrite();
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                resolveSerializer.startTag((java.lang.String) null, "accounts");
                resolveSerializer.attributeInt((java.lang.String) null, "version", 3);
                resolveSerializer.attributeInt((java.lang.String) null, XML_ATTR_NEXT_AUTHORITY_ID, this.mNextAuthorityId);
                resolveSerializer.attributeInt((java.lang.String) null, XML_ATTR_SYNC_RANDOM_OFFSET, this.mSyncRandomOffset);
                int size = this.mMasterSyncAutomatically.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = this.mMasterSyncAutomatically.keyAt(i);
                    java.lang.Boolean valueAt = this.mMasterSyncAutomatically.valueAt(i);
                    resolveSerializer.startTag((java.lang.String) null, XML_TAG_LISTEN_FOR_TICKLES);
                    resolveSerializer.attributeInt((java.lang.String) null, XML_ATTR_USER, keyAt);
                    resolveSerializer.attributeBoolean((java.lang.String) null, "enabled", valueAt.booleanValue());
                    resolveSerializer.endTag((java.lang.String) null, XML_TAG_LISTEN_FOR_TICKLES);
                }
                int size2 = this.mAuthorities.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    com.android.server.content.SyncStorageEngine.AuthorityInfo valueAt2 = this.mAuthorities.valueAt(i2);
                    com.android.server.content.SyncStorageEngine.EndPoint endPoint = valueAt2.target;
                    resolveSerializer.startTag((java.lang.String) null, "authority");
                    resolveSerializer.attributeInt((java.lang.String) null, "id", valueAt2.ident);
                    resolveSerializer.attributeInt((java.lang.String) null, XML_ATTR_USER, endPoint.userId);
                    resolveSerializer.attributeBoolean((java.lang.String) null, "enabled", valueAt2.enabled);
                    resolveSerializer.attribute((java.lang.String) null, "account", endPoint.account.name);
                    resolveSerializer.attribute((java.lang.String) null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, endPoint.account.type);
                    resolveSerializer.attribute((java.lang.String) null, "authority", endPoint.provider);
                    resolveSerializer.attributeInt((java.lang.String) null, "syncable", valueAt2.syncable);
                    resolveSerializer.endTag((java.lang.String) null, "authority");
                }
                resolveSerializer.endTag((java.lang.String) null, "accounts");
                resolveSerializer.endDocument();
                this.mAccountInfoFile.finishWrite(startWrite);
            } catch (java.io.IOException e) {
                e = e;
                fileOutputStream = startWrite;
                android.util.Slog.w("SyncManager", "Error writing accounts", e);
                if (fileOutputStream != null) {
                    this.mAccountInfoFile.failWrite(fileOutputStream);
                }
            }
        } catch (java.io.IOException e2) {
            e = e2;
        }
    }

    private void readStatusParcelLocked(java.io.File file) {
        android.os.Parcel obtain;
        try {
            byte[] readFully = new android.util.AtomicFile(file).readFully();
            obtain = android.os.Parcel.obtain();
            obtain.unmarshall(readFully, 0, readFully.length);
            obtain.setDataPosition(0);
        } catch (java.io.IOException e) {
            android.util.Slog.i("SyncManager", "No initial status");
            return;
        }
        while (true) {
            int readInt = obtain.readInt();
            if (readInt != 0) {
                if (readInt == 100) {
                    try {
                        android.content.SyncStatusInfo syncStatusInfo = new android.content.SyncStatusInfo(obtain);
                        if (this.mAuthorities.indexOfKey(syncStatusInfo.authorityId) >= 0) {
                            syncStatusInfo.pending = false;
                            this.mSyncStatus.put(syncStatusInfo.authorityId, syncStatusInfo);
                        }
                    } catch (java.lang.Exception e2) {
                        android.util.Slog.e("SyncManager", "Unable to parse some sync status.", e2);
                    }
                } else {
                    android.util.Slog.w("SyncManager", "Unknown status token: " + readInt);
                    return;
                }
                android.util.Slog.i("SyncManager", "No initial status");
                return;
            }
            return;
        }
    }

    private void upgradeStatusIfNeededLocked() {
        java.io.File file = new java.io.File(this.mSyncDir, LEGACY_STATUS_FILE_NAME);
        if (file.exists() && !this.mStatusFile.exists()) {
            readStatusParcelLocked(file);
            writeStatusLocked();
        }
        if (file.exists() && this.mStatusFile.exists()) {
            file.delete();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void readStatusLocked() {
        upgradeStatusIfNeededLocked();
        if (!this.mStatusFile.exists()) {
            return;
        }
        try {
            java.io.FileInputStream openRead = this.mStatusFile.openRead();
            try {
                readStatusInfoLocked(openRead);
                if (openRead != null) {
                    openRead.close();
                }
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e("SyncManager", "Unable to read status info file.", e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x004c, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readStatusInfoLocked(java.io.InputStream inputStream) throws java.io.IOException {
        android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(inputStream);
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    long start = protoInputStream.start(2246267895809L);
                    android.content.SyncStatusInfo readSyncStatusInfoLocked = readSyncStatusInfoLocked(protoInputStream);
                    protoInputStream.end(start);
                    if (this.mAuthorities.indexOfKey(readSyncStatusInfoLocked.authorityId) < 0) {
                        break;
                    } else {
                        readSyncStatusInfoLocked.pending = false;
                        this.mSyncStatus.put(readSyncStatusInfoLocked.authorityId, readSyncStatusInfoLocked);
                        break;
                    }
                case 2:
                    this.mIsJobNamespaceMigrated = protoInputStream.readBoolean(1133871366146L);
                    break;
                case 3:
                    this.mIsJobAttributionFixed = protoInputStream.readBoolean(1133871366147L);
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:75:0x0145, code lost:
    
        r2.populateLastEventsInformation(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0148, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.content.SyncStatusInfo readSyncStatusInfoLocked(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        android.content.SyncStatusInfo syncStatusInfo;
        int i = 0;
        if (protoInputStream.nextField(1120986464258L)) {
            syncStatusInfo = new android.content.SyncStatusInfo(protoInputStream.readInt(1120986464258L));
        } else {
            syncStatusInfo = new android.content.SyncStatusInfo(0);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 0;
        while (true) {
            switch (protoInputStream.nextField()) {
                case 2:
                    android.util.Slog.w("SyncManager", "Failed to read the authority id via fast-path; some data might not have been read.");
                    syncStatusInfo = new android.content.SyncStatusInfo(protoInputStream.readInt(1120986464258L), syncStatusInfo);
                    break;
                case 3:
                    syncStatusInfo.lastSuccessTime = protoInputStream.readLong(1112396529667L);
                    break;
                case 4:
                    syncStatusInfo.lastSuccessSource = protoInputStream.readInt(1120986464260L);
                    break;
                case 5:
                    syncStatusInfo.lastFailureTime = protoInputStream.readLong(1112396529669L);
                    break;
                case 6:
                    syncStatusInfo.lastFailureSource = protoInputStream.readInt(1120986464262L);
                    break;
                case 7:
                    syncStatusInfo.lastFailureMesg = protoInputStream.readString(1138166333447L);
                    break;
                case 8:
                    syncStatusInfo.initialFailureTime = protoInputStream.readLong(1112396529672L);
                    break;
                case 9:
                    syncStatusInfo.pending = protoInputStream.readBoolean(1133871366153L);
                    break;
                case 10:
                    syncStatusInfo.initialize = protoInputStream.readBoolean(1133871366154L);
                    break;
                case 11:
                    syncStatusInfo.addPeriodicSyncTime(protoInputStream.readLong(2211908157451L));
                    break;
                case 12:
                    long start = protoInputStream.start(2246267895820L);
                    android.util.Pair<java.lang.Long, java.lang.String> parseLastEventInfoLocked = parseLastEventInfoLocked(protoInputStream);
                    if (parseLastEventInfoLocked != null) {
                        arrayList.add(parseLastEventInfoLocked);
                    }
                    protoInputStream.end(start);
                    break;
                case 13:
                    syncStatusInfo.lastTodayResetTime = protoInputStream.readLong(1112396529677L);
                    break;
                case 14:
                    long start2 = protoInputStream.start(1146756268046L);
                    readSyncStatusStatsLocked(protoInputStream, syncStatusInfo.totalStats);
                    protoInputStream.end(start2);
                    break;
                case 15:
                    long start3 = protoInputStream.start(1146756268047L);
                    readSyncStatusStatsLocked(protoInputStream, syncStatusInfo.todayStats);
                    protoInputStream.end(start3);
                    break;
                case 16:
                    long start4 = protoInputStream.start(1146756268048L);
                    readSyncStatusStatsLocked(protoInputStream, syncStatusInfo.yesterdayStats);
                    protoInputStream.end(start4);
                    break;
                case 17:
                    long readLong = protoInputStream.readLong(2211908157457L);
                    if (i == syncStatusInfo.perSourceLastSuccessTimes.length) {
                        android.util.Slog.w("SyncManager", "Attempted to read more per source last success times than expected; data might be corrupted.");
                        break;
                    } else {
                        syncStatusInfo.perSourceLastSuccessTimes[i] = readLong;
                        i++;
                        break;
                    }
                case 18:
                    long readLong2 = protoInputStream.readLong(2211908157458L);
                    if (i2 == syncStatusInfo.perSourceLastFailureTimes.length) {
                        android.util.Slog.w("SyncManager", "Attempted to read more per source last failure times than expected; data might be corrupted.");
                        break;
                    } else {
                        syncStatusInfo.perSourceLastFailureTimes[i2] = readLong2;
                        i2++;
                        break;
                    }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0022, code lost:
    
        if (r3 != null) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x002e, code lost:
    
        return new android.util.Pair<>(java.lang.Long.valueOf(r0), r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:?, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.util.Pair<java.lang.Long, java.lang.String> parseLastEventInfoLocked(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        long j = 0;
        java.lang.String str = null;
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    j = protoInputStream.readLong(1112396529665L);
                    break;
                case 2:
                    str = protoInputStream.readString(1138166333442L);
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x0084, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readSyncStatusStatsLocked(android.util.proto.ProtoInputStream protoInputStream, android.content.SyncStatusInfo.Stats stats) throws java.io.IOException {
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    stats.totalElapsedTime = protoInputStream.readLong(1112396529665L);
                    break;
                case 2:
                    stats.numSyncs = protoInputStream.readInt(1120986464258L);
                    break;
                case 3:
                    stats.numFailures = protoInputStream.readInt(1120986464259L);
                    break;
                case 4:
                    stats.numCancels = protoInputStream.readInt(1120986464260L);
                    break;
                case 5:
                    stats.numSourceOther = protoInputStream.readInt(1120986464261L);
                    break;
                case 6:
                    stats.numSourceLocal = protoInputStream.readInt(1120986464262L);
                    break;
                case 7:
                    stats.numSourcePoll = protoInputStream.readInt(1120986464263L);
                    break;
                case 8:
                    stats.numSourceUser = protoInputStream.readInt(1120986464264L);
                    break;
                case 9:
                    stats.numSourcePeriodic = protoInputStream.readInt(1120986464265L);
                    break;
                case 10:
                    stats.numSourceFeed = protoInputStream.readInt(1120986464266L);
                    break;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.util.AtomicFile] */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.server.content.SyncStorageEngine] */
    @com.android.internal.annotations.VisibleForTesting
    void writeStatusLocked() {
        java.lang.Throwable th;
        java.io.FileOutputStream fileOutputStream;
        java.lang.Throwable e;
        if (android.util.Log.isLoggable(TAG_FILE, 2)) {
            android.util.Slog.v(TAG_FILE, "Writing new " + this.mStatusFile.getBaseFile());
        }
        ?? r1 = 1;
        this.mHandler.removeMessages(1);
        try {
            try {
                fileOutputStream = this.mStatusFile.startWrite();
            } catch (java.io.IOException | java.lang.IllegalArgumentException e2) {
                fileOutputStream = null;
                e = e2;
            } catch (java.lang.Throwable th2) {
                r1 = 0;
                th = th2;
                this.mStatusFile.failWrite(r1);
                throw th;
            }
            try {
                writeStatusInfoLocked(fileOutputStream);
                this.mStatusFile.finishWrite(fileOutputStream);
                android.util.AtomicFile atomicFile = this.mStatusFile;
                atomicFile.failWrite(null);
                r1 = atomicFile;
            } catch (java.io.IOException | java.lang.IllegalArgumentException e3) {
                e = e3;
                android.util.Slog.e("SyncManager", "Unable to write sync status to proto.", e);
                this.mStatusFile.failWrite(fileOutputStream);
                r1 = fileOutputStream;
            }
        } catch (java.lang.Throwable th3) {
            th = th3;
            this.mStatusFile.failWrite(r1);
            throw th;
        }
    }

    private void writeStatusInfoLocked(java.io.OutputStream outputStream) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(outputStream);
        int size = this.mSyncStatus.size();
        for (int i = 0; i < size; i++) {
            android.content.SyncStatusInfo valueAt = this.mSyncStatus.valueAt(i);
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1120986464258L, valueAt.authorityId);
            protoOutputStream.write(1112396529667L, valueAt.lastSuccessTime);
            protoOutputStream.write(1120986464260L, valueAt.lastSuccessSource);
            protoOutputStream.write(1112396529669L, valueAt.lastFailureTime);
            protoOutputStream.write(1120986464262L, valueAt.lastFailureSource);
            protoOutputStream.write(1138166333447L, valueAt.lastFailureMesg);
            protoOutputStream.write(1112396529672L, valueAt.initialFailureTime);
            protoOutputStream.write(1133871366153L, valueAt.pending);
            protoOutputStream.write(1133871366154L, valueAt.initialize);
            int periodicSyncTimesSize = valueAt.getPeriodicSyncTimesSize();
            for (int i2 = 0; i2 < periodicSyncTimesSize; i2++) {
                protoOutputStream.write(2211908157451L, valueAt.getPeriodicSyncTime(i2));
            }
            int eventCount = valueAt.getEventCount();
            for (int i3 = 0; i3 < eventCount; i3++) {
                long start2 = protoOutputStream.start(2246267895820L);
                protoOutputStream.write(1112396529665L, valueAt.getEventTime(i3));
                protoOutputStream.write(1138166333442L, valueAt.getEvent(i3));
                protoOutputStream.end(start2);
            }
            protoOutputStream.write(1112396529677L, valueAt.lastTodayResetTime);
            long start3 = protoOutputStream.start(1146756268046L);
            writeStatusStatsLocked(protoOutputStream, valueAt.totalStats);
            protoOutputStream.end(start3);
            long start4 = protoOutputStream.start(1146756268047L);
            writeStatusStatsLocked(protoOutputStream, valueAt.todayStats);
            protoOutputStream.end(start4);
            long start5 = protoOutputStream.start(1146756268048L);
            writeStatusStatsLocked(protoOutputStream, valueAt.yesterdayStats);
            protoOutputStream.end(start5);
            int length = valueAt.perSourceLastSuccessTimes.length;
            for (int i4 = 0; i4 < length; i4++) {
                protoOutputStream.write(2211908157457L, valueAt.perSourceLastSuccessTimes[i4]);
            }
            int length2 = valueAt.perSourceLastFailureTimes.length;
            for (int i5 = 0; i5 < length2; i5++) {
                protoOutputStream.write(2211908157458L, valueAt.perSourceLastFailureTimes[i5]);
            }
            protoOutputStream.end(start);
        }
        protoOutputStream.write(1133871366146L, this.mIsJobNamespaceMigrated);
        protoOutputStream.write(1133871366147L, this.mIsJobAttributionFixed);
        protoOutputStream.flush();
    }

    private void writeStatusStatsLocked(android.util.proto.ProtoOutputStream protoOutputStream, android.content.SyncStatusInfo.Stats stats) {
        protoOutputStream.write(1112396529665L, stats.totalElapsedTime);
        protoOutputStream.write(1120986464258L, stats.numSyncs);
        protoOutputStream.write(1120986464259L, stats.numFailures);
        protoOutputStream.write(1120986464260L, stats.numCancels);
        protoOutputStream.write(1120986464261L, stats.numSourceOther);
        protoOutputStream.write(1120986464262L, stats.numSourceLocal);
        protoOutputStream.write(1120986464263L, stats.numSourcePoll);
        protoOutputStream.write(1120986464264L, stats.numSourceUser);
        protoOutputStream.write(1120986464265L, stats.numSourcePeriodic);
        protoOutputStream.write(1120986464266L, stats.numSourceFeed);
    }

    private void requestSync(com.android.server.content.SyncStorageEngine.AuthorityInfo authorityInfo, int i, android.os.Bundle bundle, int i2, int i3, int i4) {
        if (android.os.Process.myUid() == 1000 && this.mSyncRequestListener != null) {
            this.mSyncRequestListener.onSyncRequest(authorityInfo.target, i, bundle, i2, i3, i4);
            return;
        }
        android.content.SyncRequest.Builder extras = new android.content.SyncRequest.Builder().syncOnce().setExtras(bundle);
        extras.setSyncAdapter(authorityInfo.target.account, authorityInfo.target.provider);
        android.content.ContentResolver.requestSync(extras.build());
    }

    private void requestSync(android.accounts.Account account, int i, int i2, java.lang.String str, android.os.Bundle bundle, int i3, int i4, int i5) {
        if (android.os.Process.myUid() == 1000 && this.mSyncRequestListener != null) {
            this.mSyncRequestListener.onSyncRequest(new com.android.server.content.SyncStorageEngine.EndPoint(account, str, i), i2, bundle, i3, i4, i5);
        } else {
            android.content.ContentResolver.requestSync(account, str, bundle);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0027, code lost:
    
        android.util.Slog.w("SyncManager", "Unknown stats token: " + r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readStatsParcelLocked(java.io.File file) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            try {
                byte[] readFully = new android.util.AtomicFile(file).readFully();
                int i = 0;
                obtain.unmarshall(readFully, 0, readFully.length);
                obtain.setDataPosition(0);
                while (true) {
                    int readInt = obtain.readInt();
                    if (readInt != 0) {
                        if (readInt != 101 && readInt != 100) {
                            break;
                        }
                        int readInt2 = obtain.readInt();
                        if (readInt == 100) {
                            readInt2 = (readInt2 - 2009) + 14245;
                        }
                        com.android.server.content.SyncStorageEngine.DayStats dayStats = new com.android.server.content.SyncStorageEngine.DayStats(readInt2);
                        dayStats.successCount = obtain.readInt();
                        dayStats.successTime = obtain.readLong();
                        dayStats.failureCount = obtain.readInt();
                        dayStats.failureTime = obtain.readLong();
                        if (i < this.mDayStats.length) {
                            this.mDayStats[i] = dayStats;
                            i++;
                        }
                    } else {
                        break;
                    }
                }
            } catch (java.io.IOException e) {
                android.util.Slog.i("SyncManager", "No initial statistics");
            }
            obtain.recycle();
        } catch (java.lang.Throwable th) {
            obtain.recycle();
            throw th;
        }
    }

    private void upgradeStatisticsIfNeededLocked() {
        java.io.File file = new java.io.File(this.mSyncDir, LEGACY_STATISTICS_FILE_NAME);
        if (file.exists() && !this.mStatisticsFile.exists()) {
            readStatsParcelLocked(file);
            writeStatisticsLocked();
        }
        if (file.exists() && this.mStatisticsFile.exists()) {
            file.delete();
        }
    }

    private void readStatisticsLocked() {
        upgradeStatisticsIfNeededLocked();
        if (!this.mStatisticsFile.exists()) {
            return;
        }
        try {
            java.io.FileInputStream openRead = this.mStatisticsFile.openRead();
            try {
                readDayStatsLocked(openRead);
                if (openRead != null) {
                    openRead.close();
                }
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e("SyncManager", "Unable to read day stats file.", e);
        }
    }

    private void readDayStatsLocked(java.io.InputStream inputStream) throws java.io.IOException {
        android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(inputStream);
        int i = 0;
        while (true) {
            switch (protoInputStream.nextField()) {
                case -1:
                    return;
                case 1:
                    long start = protoInputStream.start(2246267895809L);
                    com.android.server.content.SyncStorageEngine.DayStats readIndividualDayStatsLocked = readIndividualDayStatsLocked(protoInputStream);
                    protoInputStream.end(start);
                    this.mDayStats[i] = readIndividualDayStatsLocked;
                    i++;
                    if (i != this.mDayStats.length) {
                        break;
                    } else {
                        return;
                    }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0076, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private com.android.server.content.SyncStorageEngine.DayStats readIndividualDayStatsLocked(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        com.android.server.content.SyncStorageEngine.DayStats dayStats;
        if (protoInputStream.nextField(1120986464257L)) {
            dayStats = new com.android.server.content.SyncStorageEngine.DayStats(protoInputStream.readInt(1120986464257L));
        } else {
            dayStats = new com.android.server.content.SyncStorageEngine.DayStats(0);
        }
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    android.util.Slog.w("SyncManager", "Failed to read the day via fast-path; some data might not have been read.");
                    com.android.server.content.SyncStorageEngine.DayStats dayStats2 = new com.android.server.content.SyncStorageEngine.DayStats(protoInputStream.readInt(1120986464257L));
                    dayStats2.successCount = dayStats.successCount;
                    dayStats2.successTime = dayStats.successTime;
                    dayStats2.failureCount = dayStats.failureCount;
                    dayStats2.failureTime = dayStats.failureTime;
                    dayStats = dayStats2;
                    break;
                case 2:
                    dayStats.successCount = protoInputStream.readInt(1120986464258L);
                    break;
                case 3:
                    dayStats.successTime = protoInputStream.readLong(1112396529667L);
                    break;
                case 4:
                    dayStats.failureCount = protoInputStream.readInt(1120986464260L);
                    break;
                case 5:
                    dayStats.failureTime = protoInputStream.readLong(1112396529669L);
                    break;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.util.AtomicFile] */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.server.content.SyncStorageEngine] */
    @com.android.internal.annotations.VisibleForTesting
    void writeStatisticsLocked() {
        java.lang.Throwable th;
        java.io.FileOutputStream fileOutputStream;
        java.lang.Throwable e;
        ?? r1 = 2;
        if (android.util.Log.isLoggable(TAG_FILE, 2)) {
            android.util.Slog.v("SyncManager", "Writing new " + this.mStatisticsFile.getBaseFile());
        }
        this.mHandler.removeMessages(2);
        try {
            try {
                fileOutputStream = this.mStatisticsFile.startWrite();
            } catch (java.io.IOException | java.lang.IllegalArgumentException e2) {
                fileOutputStream = null;
                e = e2;
            } catch (java.lang.Throwable th2) {
                r1 = 0;
                th = th2;
                this.mStatisticsFile.failWrite(r1);
                throw th;
            }
            try {
                writeDayStatsLocked(fileOutputStream);
                this.mStatisticsFile.finishWrite(fileOutputStream);
                android.util.AtomicFile atomicFile = this.mStatisticsFile;
                atomicFile.failWrite(null);
                r1 = atomicFile;
            } catch (java.io.IOException | java.lang.IllegalArgumentException e3) {
                e = e3;
                android.util.Slog.e("SyncManager", "Unable to write day stats to proto.", e);
                this.mStatisticsFile.failWrite(fileOutputStream);
                r1 = fileOutputStream;
            }
        } catch (java.lang.Throwable th3) {
            th = th3;
            this.mStatisticsFile.failWrite(r1);
            throw th;
        }
    }

    private void writeDayStatsLocked(java.io.OutputStream outputStream) throws java.io.IOException, java.lang.IllegalArgumentException {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(outputStream);
        int length = this.mDayStats.length;
        for (int i = 0; i < length; i++) {
            com.android.server.content.SyncStorageEngine.DayStats dayStats = this.mDayStats[i];
            if (dayStats == null) {
                break;
            }
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1120986464257L, dayStats.day);
            protoOutputStream.write(1120986464258L, dayStats.successCount);
            protoOutputStream.write(1112396529667L, dayStats.successTime);
            protoOutputStream.write(1120986464260L, dayStats.failureCount);
            protoOutputStream.write(1112396529669L, dayStats.failureTime);
            protoOutputStream.end(start);
        }
        protoOutputStream.flush();
    }

    public void queueBackup() {
        android.app.backup.BackupManager.dataChanged(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
    }

    public void setClockValid() {
        if (!this.mIsClockValid) {
            this.mIsClockValid = true;
            android.util.Slog.w("SyncManager", "Clock is valid now.");
        }
    }

    public boolean isClockValid() {
        return this.mIsClockValid;
    }

    public void resetTodayStats(boolean z) {
        if (z) {
            android.util.Log.w("SyncManager", "Force resetting today stats.");
        }
        synchronized (this.mAuthorities) {
            try {
                int size = this.mSyncStatus.size();
                for (int i = 0; i < size; i++) {
                    this.mSyncStatus.valueAt(i).maybeResetTodayStats(isClockValid(), z);
                }
                writeStatusLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
