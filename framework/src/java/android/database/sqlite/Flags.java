package android.database.sqlite;

/* loaded from: classes.dex */
public final class Flags {
    private static android.database.sqlite.FeatureFlags FEATURE_FLAGS = new android.database.sqlite.FeatureFlagsImpl();
    public static final java.lang.String FLAG_SQLITE_ALLOW_TEMP_TABLES = "android.database.sqlite.sqlite_allow_temp_tables";
    public static final java.lang.String FLAG_SQLITE_APIS_35 = "android.database.sqlite.sqlite_apis_35";

    public static boolean sqliteAllowTempTables() {
        return FEATURE_FLAGS.sqliteAllowTempTables();
    }

    public static boolean sqliteApis35() {
        return FEATURE_FLAGS.sqliteApis35();
    }
}
