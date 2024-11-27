package android.database.sqlite;

/* loaded from: classes.dex */
public final class SQLiteGlobal {
    public static final java.lang.String SYNC_MODE_FULL = "FULL";
    private static final java.lang.String TAG = "SQLiteGlobal";
    static final java.lang.String WIPE_CHECK_FILE_SUFFIX = "-wipecheck";
    private static int sDefaultPageSize;
    public static volatile java.lang.String sDefaultSyncMode;
    private static final java.lang.Object sLock = new java.lang.Object();

    private static native int nativeReleaseMemory();

    private SQLiteGlobal() {
    }

    public static int releaseMemory() {
        return nativeReleaseMemory();
    }

    public static int getDefaultPageSize() {
        int i;
        synchronized (sLock) {
            if (sDefaultPageSize == 0) {
                sDefaultPageSize = new android.os.StatFs("/data").getBlockSize();
            }
            i = android.os.SystemProperties.getInt("debug.sqlite.pagesize", sDefaultPageSize);
        }
        return i;
    }

    public static java.lang.String getDefaultJournalMode() {
        return android.os.SystemProperties.get("debug.sqlite.journalmode", android.content.res.Resources.getSystem().getString(com.android.internal.R.string.db_default_journal_mode));
    }

    public static int getJournalSizeLimit() {
        return android.os.SystemProperties.getInt("debug.sqlite.journalsizelimit", android.content.res.Resources.getSystem().getInteger(com.android.internal.R.integer.db_journal_size_limit));
    }

    public static java.lang.String getDefaultSyncMode() {
        java.lang.String str = sDefaultSyncMode;
        if (str != null) {
            return str;
        }
        return android.os.SystemProperties.get("debug.sqlite.syncmode", android.content.res.Resources.getSystem().getString(com.android.internal.R.string.db_default_sync_mode));
    }

    public static java.lang.String getWALSyncMode() {
        java.lang.String str = sDefaultSyncMode;
        if (str != null) {
            return str;
        }
        return android.os.SystemProperties.get("debug.sqlite.wal.syncmode", android.content.res.Resources.getSystem().getString(com.android.internal.R.string.db_wal_sync_mode));
    }

    public static int getWALAutoCheckpoint() {
        return java.lang.Math.max(1, android.os.SystemProperties.getInt("debug.sqlite.wal.autocheckpoint", android.content.res.Resources.getSystem().getInteger(com.android.internal.R.integer.db_wal_autocheckpoint)));
    }

    public static int getWALConnectionPoolSize() {
        return java.lang.Math.max(2, android.os.SystemProperties.getInt("debug.sqlite.wal.poolsize", android.content.res.Resources.getSystem().getInteger(com.android.internal.R.integer.db_connection_pool_size)));
    }

    public static int getIdleConnectionTimeout() {
        return android.os.SystemProperties.getInt("debug.sqlite.idle_connection_timeout", android.content.res.Resources.getSystem().getInteger(com.android.internal.R.integer.db_default_idle_connection_timeout));
    }

    public static long getWALTruncateSize() {
        long truncateSize = android.database.sqlite.SQLiteCompatibilityWalFlags.getTruncateSize();
        if (truncateSize >= 0) {
            return truncateSize;
        }
        return android.os.SystemProperties.getInt("debug.sqlite.wal.truncatesize", android.content.res.Resources.getSystem().getInteger(com.android.internal.R.integer.db_wal_truncate_size));
    }

    public static boolean checkDbWipe() {
        return false;
    }
}
