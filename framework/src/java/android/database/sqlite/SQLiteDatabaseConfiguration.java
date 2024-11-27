package android.database.sqlite;

/* loaded from: classes.dex */
public final class SQLiteDatabaseConfiguration {
    private static final java.util.regex.Pattern EMAIL_IN_DB_PATTERN = java.util.regex.Pattern.compile("[\\w\\.\\-]+@[\\w\\.\\-]+");
    public static final java.lang.String MEMORY_DB_PATH = ":memory:";
    public boolean foreignKeyConstraintsEnabled;
    public java.lang.String journalMode;
    public final java.lang.String label;
    public java.util.Locale locale;
    public int maxSqlCacheSize;
    public int openFlags;
    public final java.lang.String path;
    public boolean shouldTruncateWalFile;
    public java.lang.String syncMode;
    public final android.util.ArrayMap<java.lang.String, java.util.function.UnaryOperator<java.lang.String>> customScalarFunctions = new android.util.ArrayMap<>();
    public final android.util.ArrayMap<java.lang.String, java.util.function.BinaryOperator<java.lang.String>> customAggregateFunctions = new android.util.ArrayMap<>();
    public final java.util.ArrayList<android.util.Pair<java.lang.String, java.lang.Object[]>> perConnectionSql = new java.util.ArrayList<>();
    public int lookasideSlotSize = -1;
    public int lookasideSlotCount = -1;
    public long idleConnectionTimeoutMs = Long.MAX_VALUE;

    public SQLiteDatabaseConfiguration(java.lang.String str, int i) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("path must not be null.");
        }
        this.path = str;
        this.label = stripPathForLogs(str);
        this.openFlags = i;
        this.maxSqlCacheSize = 25;
        this.locale = java.util.Locale.getDefault();
    }

    public SQLiteDatabaseConfiguration(android.database.sqlite.SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        if (sQLiteDatabaseConfiguration == null) {
            throw new java.lang.IllegalArgumentException("other must not be null.");
        }
        this.path = sQLiteDatabaseConfiguration.path;
        this.label = sQLiteDatabaseConfiguration.label;
        updateParametersFrom(sQLiteDatabaseConfiguration);
    }

    public void updateParametersFrom(android.database.sqlite.SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        if (sQLiteDatabaseConfiguration == null) {
            throw new java.lang.IllegalArgumentException("other must not be null.");
        }
        if (!this.path.equals(sQLiteDatabaseConfiguration.path)) {
            throw new java.lang.IllegalArgumentException("other configuration must refer to the same database.");
        }
        this.openFlags = sQLiteDatabaseConfiguration.openFlags;
        this.maxSqlCacheSize = sQLiteDatabaseConfiguration.maxSqlCacheSize;
        this.locale = sQLiteDatabaseConfiguration.locale;
        this.foreignKeyConstraintsEnabled = sQLiteDatabaseConfiguration.foreignKeyConstraintsEnabled;
        this.customScalarFunctions.clear();
        this.customScalarFunctions.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends java.util.function.UnaryOperator<java.lang.String>>) sQLiteDatabaseConfiguration.customScalarFunctions);
        this.customAggregateFunctions.clear();
        this.customAggregateFunctions.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends java.util.function.BinaryOperator<java.lang.String>>) sQLiteDatabaseConfiguration.customAggregateFunctions);
        this.perConnectionSql.clear();
        this.perConnectionSql.addAll(sQLiteDatabaseConfiguration.perConnectionSql);
        this.lookasideSlotSize = sQLiteDatabaseConfiguration.lookasideSlotSize;
        this.lookasideSlotCount = sQLiteDatabaseConfiguration.lookasideSlotCount;
        this.idleConnectionTimeoutMs = sQLiteDatabaseConfiguration.idleConnectionTimeoutMs;
        this.journalMode = sQLiteDatabaseConfiguration.journalMode;
        this.syncMode = sQLiteDatabaseConfiguration.syncMode;
    }

    public boolean isInMemoryDb() {
        return this.path.equalsIgnoreCase(MEMORY_DB_PATH);
    }

    public boolean isReadOnlyDatabase() {
        return (this.openFlags & 1) != 0;
    }

    boolean isLegacyCompatibilityWalEnabled() {
        return this.journalMode == null && this.syncMode == null && (this.openFlags & Integer.MIN_VALUE) != 0;
    }

    private static java.lang.String stripPathForLogs(java.lang.String str) {
        if (str.indexOf(64) == -1) {
            return str;
        }
        return EMAIL_IN_DB_PATTERN.matcher(str).replaceAll("XX@YY");
    }

    boolean isLookasideConfigSet() {
        return this.lookasideSlotCount >= 0 && this.lookasideSlotSize >= 0;
    }

    public java.lang.String resolveJournalMode() {
        if (isReadOnlyDatabase()) {
            return "";
        }
        if (isInMemoryDb()) {
            if (this.journalMode != null && this.journalMode.equalsIgnoreCase("OFF")) {
                return "OFF";
            }
            return android.database.sqlite.SQLiteDatabase.JOURNAL_MODE_MEMORY;
        }
        this.shouldTruncateWalFile = false;
        if (!isWalEnabledInternal()) {
            return this.journalMode != null ? this.journalMode : android.database.sqlite.SQLiteGlobal.getDefaultJournalMode();
        }
        this.shouldTruncateWalFile = true;
        return android.database.sqlite.SQLiteDatabase.JOURNAL_MODE_WAL;
    }

    public java.lang.String resolveSyncMode() {
        if (isReadOnlyDatabase() || isInMemoryDb()) {
            return "";
        }
        if (!android.text.TextUtils.isEmpty(this.syncMode)) {
            return this.syncMode;
        }
        if (isWalEnabledInternal()) {
            if (isLegacyCompatibilityWalEnabled()) {
                return android.database.sqlite.SQLiteCompatibilityWalFlags.getWALSyncMode();
            }
            return android.database.sqlite.SQLiteGlobal.getWALSyncMode();
        }
        return android.database.sqlite.SQLiteGlobal.getDefaultSyncMode();
    }

    private boolean isWalEnabledInternal() {
        boolean z = (this.openFlags & 536870912) != 0;
        boolean isLegacyCompatibilityWalEnabled = isLegacyCompatibilityWalEnabled();
        if (z || isLegacyCompatibilityWalEnabled) {
            return true;
        }
        return this.journalMode != null && this.journalMode.equalsIgnoreCase(android.database.sqlite.SQLiteDatabase.JOURNAL_MODE_WAL);
    }
}
