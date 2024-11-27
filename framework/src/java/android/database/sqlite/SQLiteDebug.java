package android.database.sqlite;

/* loaded from: classes.dex */
public final class SQLiteDebug {

    public static class PagerStats {
        public java.util.ArrayList<android.database.sqlite.SQLiteDebug.DbStats> dbStats;
        public int largestMemAlloc;
        public int memoryUsed;
        public int pageCacheOverflow;
    }

    private static native void nativeGetPagerStats(android.database.sqlite.SQLiteDebug.PagerStats pagerStats);

    public static final class NoPreloadHolder {
        public static final boolean DEBUG_LOG_DETAILED;
        private static final java.lang.String SLOW_QUERY_THRESHOLD_PROP = "db.log.slow_query_threshold";
        public static final boolean DEBUG_SQL_LOG = android.util.Log.isLoggable("SQLiteLog", 2);
        public static final boolean DEBUG_SQL_STATEMENTS = android.util.Log.isLoggable("SQLiteStatements", 2);
        public static final boolean DEBUG_SQL_TIME = android.util.Log.isLoggable("SQLiteTime", 2);
        public static final boolean DEBUG_LOG_SLOW_QUERIES = android.util.Log.isLoggable("SQLiteSlowQueries", 2);
        private static final java.lang.String SLOW_QUERY_THRESHOLD_UID_PROP = "db.log.slow_query_threshold." + android.os.Process.myUid();

        static {
            boolean z = false;
            if (android.os.Build.IS_DEBUGGABLE && android.os.SystemProperties.getBoolean("db.log.detailed", false)) {
                z = true;
            }
            DEBUG_LOG_DETAILED = z;
        }
    }

    private SQLiteDebug() {
    }

    public static boolean shouldLogSlowQuery(long j) {
        return j >= ((long) java.lang.Math.min(android.os.SystemProperties.getInt("db.log.slow_query_threshold", Integer.MAX_VALUE), android.os.SystemProperties.getInt(android.database.sqlite.SQLiteDebug.NoPreloadHolder.SLOW_QUERY_THRESHOLD_UID_PROP, Integer.MAX_VALUE)));
    }

    public static class DbStats {
        public final boolean arePoolStats;
        public final int cacheHits;
        public final int cacheMisses;
        public final int cacheSize;
        public java.lang.String dbName;
        public long dbSize;
        public int lookaside;
        public long pageSize;

        public DbStats(java.lang.String str, long j, long j2, int i, int i2, int i3, int i4, boolean z) {
            this.dbName = str;
            this.pageSize = j2 / 1024;
            this.dbSize = (j * j2) / 1024;
            this.lookaside = i;
            this.cacheHits = i2;
            this.cacheMisses = i3;
            this.cacheSize = i4;
            this.arePoolStats = z;
        }
    }

    public static android.database.sqlite.SQLiteDebug.PagerStats getDatabaseInfo() {
        android.database.sqlite.SQLiteDebug.PagerStats pagerStats = new android.database.sqlite.SQLiteDebug.PagerStats();
        nativeGetPagerStats(pagerStats);
        pagerStats.dbStats = android.database.sqlite.SQLiteDatabase.getDbStats();
        return pagerStats;
    }

    public static void dump(android.util.Printer printer, java.lang.String[] strArr) {
        dump(printer, strArr, false);
    }

    public static void dump(android.util.Printer printer, java.lang.String[] strArr, boolean z) {
        boolean z2 = false;
        for (java.lang.String str : strArr) {
            if (str.equals("-v")) {
                z2 = true;
            }
        }
        android.database.sqlite.SQLiteDatabase.dumpAll(printer, z2, z);
    }
}
