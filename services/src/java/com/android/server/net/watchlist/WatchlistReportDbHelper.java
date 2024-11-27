package com.android.server.net.watchlist;

/* loaded from: classes2.dex */
class WatchlistReportDbHelper extends android.database.sqlite.SQLiteOpenHelper {
    private static final java.lang.String CREATE_TABLE_MODEL = "CREATE TABLE records(app_digest BLOB,cnc_domain TEXT,timestamp INTEGER DEFAULT 0 )";
    private static final java.lang.String[] DIGEST_DOMAIN_PROJECTION = {"app_digest", "cnc_domain"};
    private static final int IDLE_CONNECTION_TIMEOUT_MS = 30000;
    private static final int INDEX_CNC_DOMAIN = 1;
    private static final int INDEX_DIGEST = 0;
    private static final int INDEX_TIMESTAMP = 2;
    private static final java.lang.String NAME = "watchlist_report.db";
    private static final java.lang.String TAG = "WatchlistReportDbHelper";
    private static final int VERSION = 2;
    private static com.android.server.net.watchlist.WatchlistReportDbHelper sInstance;

    private static class WhiteListReportContract {
        private static final java.lang.String APP_DIGEST = "app_digest";
        private static final java.lang.String CNC_DOMAIN = "cnc_domain";
        private static final java.lang.String TABLE = "records";
        private static final java.lang.String TIMESTAMP = "timestamp";

        private WhiteListReportContract() {
        }
    }

    public static class AggregatedResult {
        final java.util.HashMap<java.lang.String, java.lang.String> appDigestCNCList;
        final java.util.Set<java.lang.String> appDigestList;

        @android.annotation.Nullable
        final java.lang.String cncDomainVisited;

        public AggregatedResult(java.util.Set<java.lang.String> set, java.lang.String str, java.util.HashMap<java.lang.String, java.lang.String> hashMap) {
            this.appDigestList = set;
            this.cncDomainVisited = str;
            this.appDigestCNCList = hashMap;
        }
    }

    static java.io.File getSystemWatchlistDbFile() {
        return new java.io.File(android.os.Environment.getDataSystemDirectory(), NAME);
    }

    private WatchlistReportDbHelper(android.content.Context context) {
        super(context, getSystemWatchlistDbFile().getAbsolutePath(), (android.database.sqlite.SQLiteDatabase.CursorFactory) null, 2);
        setIdleConnectionTimeout(30000L);
    }

    public static synchronized com.android.server.net.watchlist.WatchlistReportDbHelper getInstance(android.content.Context context) {
        synchronized (com.android.server.net.watchlist.WatchlistReportDbHelper.class) {
            if (sInstance != null) {
                return sInstance;
            }
            sInstance = new com.android.server.net.watchlist.WatchlistReportDbHelper(context);
            return sInstance;
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_TABLE_MODEL);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS records");
        onCreate(sQLiteDatabase);
    }

    public boolean insertNewRecord(byte[] bArr, java.lang.String str, long j) {
        try {
            android.database.sqlite.SQLiteDatabase writableDatabase = getWritableDatabase();
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("app_digest", bArr);
            contentValues.put("cnc_domain", str);
            contentValues.put(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.TIMESTAMP, java.lang.Long.valueOf(j));
            return writableDatabase.insert("records", null, contentValues) != -1;
        } catch (android.database.sqlite.SQLiteException e) {
            android.util.Slog.e(TAG, "Error opening the database to insert a new record", e);
            return false;
        }
    }

    @android.annotation.Nullable
    public com.android.server.net.watchlist.WatchlistReportDbHelper.AggregatedResult getAggregatedRecords(long j) {
        android.database.Cursor cursor = null;
        java.lang.String str = null;
        try {
            try {
                android.database.Cursor query = getReadableDatabase().query(true, "records", DIGEST_DOMAIN_PROJECTION, "timestamp < ?", new java.lang.String[]{java.lang.Long.toString(j)}, null, null, null, null);
                if (query == null) {
                    if (query != null) {
                        query.close();
                    }
                    return null;
                }
                try {
                    java.util.HashSet hashSet = new java.util.HashSet();
                    java.util.HashMap hashMap = new java.util.HashMap();
                    while (query.moveToNext()) {
                        java.lang.String hexString = com.android.internal.util.HexDump.toHexString(query.getBlob(0));
                        java.lang.String string = query.getString(1);
                        hashSet.add(hexString);
                        if (str != null) {
                            str = string;
                        }
                        hashMap.put(hexString, string);
                    }
                    com.android.server.net.watchlist.WatchlistReportDbHelper.AggregatedResult aggregatedResult = new com.android.server.net.watchlist.WatchlistReportDbHelper.AggregatedResult(hashSet, str, hashMap);
                    query.close();
                    return aggregatedResult;
                } catch (java.lang.Throwable th) {
                    th = th;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } catch (android.database.sqlite.SQLiteException e) {
            android.util.Slog.e(TAG, "Error opening the database", e);
            return null;
        }
    }

    public boolean cleanup(long j) {
        try {
            android.database.sqlite.SQLiteDatabase writableDatabase = getWritableDatabase();
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("timestamp< ");
            sb.append(j);
            return writableDatabase.delete("records", sb.toString(), null) != 0;
        } catch (android.database.sqlite.SQLiteException e) {
            android.util.Slog.e(TAG, "Error opening the database to cleanup", e);
            return false;
        }
    }
}
