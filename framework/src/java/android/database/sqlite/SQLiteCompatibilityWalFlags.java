package android.database.sqlite;

/* loaded from: classes.dex */
public class SQLiteCompatibilityWalFlags {
    private static final java.lang.String TAG = "SQLiteCompatibilityWalFlags";
    private static volatile boolean sCallingGlobalSettings;
    private static volatile boolean sInitialized;
    private static volatile boolean sLegacyCompatibilityWalEnabled;
    private static volatile long sTruncateSize = -1;
    private static volatile java.lang.String sWALSyncMode;

    private SQLiteCompatibilityWalFlags() {
    }

    public static boolean isLegacyCompatibilityWalEnabled() {
        initIfNeeded();
        return sLegacyCompatibilityWalEnabled;
    }

    public static java.lang.String getWALSyncMode() {
        initIfNeeded();
        if (!sLegacyCompatibilityWalEnabled) {
            throw new java.lang.IllegalStateException("isLegacyCompatibilityWalEnabled() == false");
        }
        return sWALSyncMode;
    }

    public static long getTruncateSize() {
        initIfNeeded();
        return sTruncateSize;
    }

    private static void initIfNeeded() {
        if (sInitialized || sCallingGlobalSettings) {
            return;
        }
        android.app.ActivityThread currentActivityThread = android.app.ActivityThread.currentActivityThread();
        java.lang.String str = null;
        android.app.Application application = currentActivityThread == null ? null : currentActivityThread.getApplication();
        if (application == null) {
            android.util.Log.w(TAG, "Cannot read global setting sqlite_compatibility_wal_flags - Application state not available");
        } else {
            try {
                sCallingGlobalSettings = true;
                str = android.provider.Settings.Global.getString(application.getContentResolver(), android.provider.Settings.Global.SQLITE_COMPATIBILITY_WAL_FLAGS);
            } finally {
                sCallingGlobalSettings = false;
            }
        }
        init(str);
    }

    public static void init(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            sInitialized = true;
            return;
        }
        android.util.KeyValueListParser keyValueListParser = new android.util.KeyValueListParser(',');
        try {
            keyValueListParser.setString(str);
            sLegacyCompatibilityWalEnabled = keyValueListParser.getBoolean("legacy_compatibility_wal_enabled", false);
            sWALSyncMode = keyValueListParser.getString("wal_syncmode", android.database.sqlite.SQLiteGlobal.getWALSyncMode());
            sTruncateSize = keyValueListParser.getInt("truncate_size", -1);
            android.util.Log.i(TAG, "Read compatibility WAL flags: legacy_compatibility_wal_enabled=" + sLegacyCompatibilityWalEnabled + ", wal_syncmode=" + sWALSyncMode);
            sInitialized = true;
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.e(TAG, "Setting has invalid format: " + str, e);
            sInitialized = true;
        }
    }

    public static void reset() {
        sInitialized = false;
        sLegacyCompatibilityWalEnabled = false;
        sWALSyncMode = null;
    }
}
