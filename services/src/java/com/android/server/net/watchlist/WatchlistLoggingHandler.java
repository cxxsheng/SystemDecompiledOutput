package com.android.server.net.watchlist;

/* loaded from: classes2.dex */
class WatchlistLoggingHandler extends android.os.Handler {
    private static final boolean DEBUG = false;
    private static final java.lang.String DROPBOX_TAG = "network_watchlist_report";

    @com.android.internal.annotations.VisibleForTesting
    static final int FORCE_REPORT_RECORDS_NOW_FOR_TEST_MSG = 3;

    @com.android.internal.annotations.VisibleForTesting
    static final int LOG_WATCHLIST_EVENT_MSG = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int REPORT_RECORDS_IF_NECESSARY_MSG = 2;
    private final com.android.server.net.watchlist.FileHashCache mApkHashCache;
    private final java.util.concurrent.ConcurrentHashMap<java.lang.Integer, byte[]> mCachedUidDigestMap;
    private final com.android.server.net.watchlist.WatchlistConfig mConfig;
    private final android.content.Context mContext;
    private final com.android.server.net.watchlist.WatchlistReportDbHelper mDbHelper;

    @android.annotation.Nullable
    private final android.os.DropBoxManager mDropBoxManager;
    private final android.content.pm.PackageManager mPm;
    private int mPrimaryUserId;
    private final android.content.ContentResolver mResolver;
    private final com.android.server.net.watchlist.WatchlistSettings mSettings;
    private static final java.lang.String TAG = com.android.server.net.watchlist.WatchlistLoggingHandler.class.getSimpleName();
    private static final long ONE_DAY_MS = java.util.concurrent.TimeUnit.DAYS.toMillis(1);

    private interface WatchlistEventKeys {
        public static final java.lang.String HOST = "host";
        public static final java.lang.String IP_ADDRESSES = "ipAddresses";
        public static final java.lang.String TIMESTAMP = "timestamp";
        public static final java.lang.String UID = "uid";
    }

    WatchlistLoggingHandler(android.content.Context context, android.os.Looper looper) {
        super(looper);
        this.mPrimaryUserId = -1;
        this.mCachedUidDigestMap = new java.util.concurrent.ConcurrentHashMap<>();
        this.mContext = context;
        this.mPm = this.mContext.getPackageManager();
        this.mResolver = this.mContext.getContentResolver();
        this.mDbHelper = com.android.server.net.watchlist.WatchlistReportDbHelper.getInstance(context);
        this.mConfig = com.android.server.net.watchlist.WatchlistConfig.getInstance();
        this.mSettings = com.android.server.net.watchlist.WatchlistSettings.getInstance();
        this.mDropBoxManager = (android.os.DropBoxManager) this.mContext.getSystemService(android.os.DropBoxManager.class);
        this.mPrimaryUserId = getPrimaryUserId();
        if (context.getResources().getBoolean(android.R.bool.config_wakeOnAssistKeyPress)) {
            this.mApkHashCache = new com.android.server.net.watchlist.FileHashCache(this);
            android.util.Slog.i(TAG, "Using file hashes cache.");
        } else {
            this.mApkHashCache = null;
        }
    }

    @Override // android.os.Handler
    public void handleMessage(android.os.Message message) {
        switch (message.what) {
            case 1:
                android.os.Bundle data = message.getData();
                handleNetworkEvent(data.getString("host"), data.getStringArray(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.IP_ADDRESSES), data.getInt(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID), data.getLong(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.TIMESTAMP));
                break;
            case 2:
                tryAggregateRecords(getLastMidnightTime());
                break;
            case 3:
                if (message.obj instanceof java.lang.Long) {
                    tryAggregateRecords(((java.lang.Long) message.obj).longValue());
                    break;
                } else {
                    android.util.Slog.e(TAG, "Msg.obj needs to be a Long object.");
                    break;
                }
            default:
                android.util.Slog.d(TAG, "WatchlistLoggingHandler received an unknown of message.");
                break;
        }
    }

    private int getPrimaryUserId() {
        android.content.pm.UserInfo primaryUser = ((android.os.UserManager) this.mContext.getSystemService("user")).getPrimaryUser();
        if (primaryUser != null) {
            return primaryUser.id;
        }
        return -1;
    }

    private boolean isPackageTestOnly(int i) {
        try {
            java.lang.String[] packagesForUid = this.mPm.getPackagesForUid(i);
            if (packagesForUid != null && packagesForUid.length != 0) {
                return (this.mPm.getApplicationInfo(packagesForUid[0], 0).flags & 256) != 0;
            }
            android.util.Slog.e(TAG, "Couldn't find package: " + java.util.Arrays.toString(packagesForUid));
            return false;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void reportWatchlistIfNecessary() {
        sendMessage(obtainMessage(2));
    }

    public void forceReportWatchlistForTest(long j) {
        android.os.Message obtainMessage = obtainMessage(3);
        obtainMessage.obj = java.lang.Long.valueOf(j);
        sendMessage(obtainMessage);
    }

    public void asyncNetworkEvent(java.lang.String str, java.lang.String[] strArr, int i) {
        android.os.Message obtainMessage = obtainMessage(1);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("host", str);
        bundle.putStringArray(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.IP_ADDRESSES, strArr);
        bundle.putInt(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, i);
        bundle.putLong(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.TIMESTAMP, java.lang.System.currentTimeMillis());
        obtainMessage.setData(bundle);
        sendMessage(obtainMessage);
    }

    private void handleNetworkEvent(java.lang.String str, java.lang.String[] strArr, int i, long j) {
        if (this.mPrimaryUserId == -1) {
            this.mPrimaryUserId = getPrimaryUserId();
        }
        if (android.os.UserHandle.getUserId(i) != this.mPrimaryUserId) {
            return;
        }
        java.lang.String searchAllSubDomainsInWatchlist = searchAllSubDomainsInWatchlist(str);
        if (searchAllSubDomainsInWatchlist != null) {
            insertRecord(i, searchAllSubDomainsInWatchlist, j);
            return;
        }
        java.lang.String searchIpInWatchlist = searchIpInWatchlist(strArr);
        if (searchIpInWatchlist != null) {
            insertRecord(i, searchIpInWatchlist, j);
        }
    }

    private void insertRecord(int i, java.lang.String str, long j) {
        byte[] digestFromUid;
        if ((this.mConfig.isConfigSecure() || isPackageTestOnly(i)) && (digestFromUid = getDigestFromUid(i)) != null && this.mDbHelper.insertNewRecord(digestFromUid, str, j)) {
            android.util.Slog.w(TAG, "Unable to insert record for uid: " + i);
        }
    }

    private boolean shouldReportNetworkWatchlist(long j) {
        long j2 = android.provider.Settings.Global.getLong(this.mResolver, "network_watchlist_last_report_time", 0L);
        if (j >= j2) {
            return j >= j2 + ONE_DAY_MS;
        }
        android.util.Slog.i(TAG, "Last report time is larger than current time, reset report");
        this.mDbHelper.cleanup(j2);
        return false;
    }

    private void tryAggregateRecords(long j) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        try {
            if (!shouldReportNetworkWatchlist(j)) {
                android.util.Slog.i(TAG, "No need to aggregate record yet.");
                return;
            }
            android.util.Slog.i(TAG, "Start aggregating watchlist records.");
            if (this.mDropBoxManager == null || !this.mDropBoxManager.isTagEnabled(DROPBOX_TAG)) {
                android.util.Slog.w(TAG, "Network Watchlist dropbox tag is not enabled");
            } else {
                android.provider.Settings.Global.putLong(this.mResolver, "network_watchlist_last_report_time", j);
                com.android.server.net.watchlist.WatchlistReportDbHelper.AggregatedResult aggregatedRecords = this.mDbHelper.getAggregatedRecords(j);
                if (aggregatedRecords == null) {
                    android.util.Slog.i(TAG, "Cannot get result from database");
                    return;
                }
                byte[] encodeWatchlistReport = com.android.server.net.watchlist.ReportEncoder.encodeWatchlistReport(this.mConfig, this.mSettings.getPrivacySecretKey(), getAllDigestsForReport(aggregatedRecords), aggregatedRecords);
                if (encodeWatchlistReport != null) {
                    addEncodedReportToDropBox(encodeWatchlistReport);
                }
            }
            this.mDbHelper.cleanup(j);
        } finally {
            long currentTimeMillis2 = java.lang.System.currentTimeMillis();
            android.util.Slog.i(TAG, "Milliseconds spent on tryAggregateRecords(): " + (currentTimeMillis2 - currentTimeMillis));
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.List<java.lang.String> getAllDigestsForReport(com.android.server.net.watchlist.WatchlistReportDbHelper.AggregatedResult aggregatedResult) {
        java.util.List<android.content.pm.ApplicationInfo> installedApplications = this.mContext.getPackageManager().getInstalledApplications(131072);
        java.util.HashSet hashSet = new java.util.HashSet(installedApplications.size() + aggregatedResult.appDigestCNCList.size());
        int size = installedApplications.size();
        for (int i = 0; i < size; i++) {
            byte[] digestFromUid = getDigestFromUid(installedApplications.get(i).uid);
            if (digestFromUid != null) {
                hashSet.add(com.android.internal.util.HexDump.toHexString(digestFromUid));
            }
        }
        hashSet.addAll(aggregatedResult.appDigestCNCList.keySet());
        return new java.util.ArrayList(hashSet);
    }

    private void addEncodedReportToDropBox(byte[] bArr) {
        this.mDropBoxManager.addData(DROPBOX_TAG, bArr, 0);
    }

    @android.annotation.Nullable
    private byte[] getDigestFromUid(final int i) {
        return this.mCachedUidDigestMap.computeIfAbsent(java.lang.Integer.valueOf(i), new java.util.function.Function() { // from class: com.android.server.net.watchlist.WatchlistLoggingHandler$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                byte[] lambda$getDigestFromUid$0;
                lambda$getDigestFromUid$0 = com.android.server.net.watchlist.WatchlistLoggingHandler.this.lambda$getDigestFromUid$0(i, (java.lang.Integer) obj);
                return lambda$getDigestFromUid$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ byte[] lambda$getDigestFromUid$0(int i, java.lang.Integer num) {
        java.lang.String[] packagesForUid = this.mPm.getPackagesForUid(num.intValue());
        int userId = android.os.UserHandle.getUserId(i);
        if (!com.android.internal.util.ArrayUtils.isEmpty(packagesForUid)) {
            for (java.lang.String str : packagesForUid) {
                try {
                    java.lang.String str2 = this.mPm.getPackageInfoAsUser(str, 786432, userId).applicationInfo.publicSourceDir;
                    if (android.text.TextUtils.isEmpty(str2)) {
                        android.util.Slog.w(TAG, "Cannot find apkPath for " + str);
                    } else if (android.os.incremental.IncrementalManager.isIncrementalPath(str2)) {
                        android.util.Slog.i(TAG, "Skipping incremental path: " + str);
                    } else {
                        if (this.mApkHashCache != null) {
                            return this.mApkHashCache.getSha256Hash(new java.io.File(str2));
                        }
                        return com.android.server.net.watchlist.DigestUtils.getSha256Hash(new java.io.File(str2));
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | java.security.NoSuchAlgorithmException e) {
                    android.util.Slog.e(TAG, "Cannot get digest from uid: " + num + ",pkg: " + str, e);
                    return null;
                }
            }
        }
        return null;
    }

    @android.annotation.Nullable
    private java.lang.String searchIpInWatchlist(java.lang.String[] strArr) {
        for (java.lang.String str : strArr) {
            if (isIpInWatchlist(str)) {
                return str;
            }
        }
        return null;
    }

    private boolean isIpInWatchlist(java.lang.String str) {
        if (str == null) {
            return false;
        }
        return this.mConfig.containsIp(str);
    }

    private boolean isHostInWatchlist(java.lang.String str) {
        if (str == null) {
            return false;
        }
        return this.mConfig.containsDomain(str);
    }

    @android.annotation.Nullable
    private java.lang.String searchAllSubDomainsInWatchlist(java.lang.String str) {
        if (str == null) {
            return null;
        }
        for (java.lang.String str2 : getAllSubDomains(str)) {
            if (isHostInWatchlist(str2)) {
                return str2;
            }
        }
        return null;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    static java.lang.String[] getAllSubDomains(java.lang.String str) {
        if (str == null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(str);
        int indexOf = str.indexOf(".");
        while (indexOf != -1) {
            str = str.substring(indexOf + 1);
            if (!android.text.TextUtils.isEmpty(str)) {
                arrayList.add(str);
            }
            indexOf = str.indexOf(".");
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
    }

    static long getLastMidnightTime() {
        return getMidnightTimestamp(0);
    }

    static long getMidnightTimestamp(int i) {
        java.util.GregorianCalendar gregorianCalendar = new java.util.GregorianCalendar();
        gregorianCalendar.set(11, 0);
        gregorianCalendar.set(12, 0);
        gregorianCalendar.set(13, 0);
        gregorianCalendar.set(14, 0);
        gregorianCalendar.add(5, -i);
        return gregorianCalendar.getTimeInMillis();
    }
}
