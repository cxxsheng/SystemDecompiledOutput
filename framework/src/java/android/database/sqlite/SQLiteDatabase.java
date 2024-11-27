package android.database.sqlite;

/* loaded from: classes.dex */
public final class SQLiteDatabase extends android.database.sqlite.SQLiteClosable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CONFLICT_ABORT = 2;
    public static final int CONFLICT_FAIL = 3;
    public static final int CONFLICT_IGNORE = 4;
    public static final int CONFLICT_NONE = 0;
    public static final int CONFLICT_REPLACE = 5;
    public static final int CONFLICT_ROLLBACK = 1;
    public static final int CREATE_IF_NECESSARY = 268435456;
    public static final int ENABLE_LEGACY_COMPATIBILITY_WAL = Integer.MIN_VALUE;
    public static final int ENABLE_WRITE_AHEAD_LOGGING = 536870912;
    private static final int EVENT_DB_CORRUPT = 75004;
    public static final java.lang.String JOURNAL_MODE_DELETE = "DELETE";
    public static final java.lang.String JOURNAL_MODE_MEMORY = "MEMORY";
    public static final java.lang.String JOURNAL_MODE_OFF = "OFF";
    public static final java.lang.String JOURNAL_MODE_PERSIST = "PERSIST";
    public static final java.lang.String JOURNAL_MODE_TRUNCATE = "TRUNCATE";
    public static final java.lang.String JOURNAL_MODE_WAL = "WAL";
    public static final int MAX_SQL_CACHE_SIZE = 100;
    public static final int NO_LOCALIZED_COLLATORS = 16;
    public static final int OPEN_READONLY = 1;
    public static final int OPEN_READWRITE = 0;
    private static final int OPEN_READ_MASK = 1;
    public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;
    public static final java.lang.String SYNC_MODE_EXTRA = "EXTRA";
    public static final java.lang.String SYNC_MODE_FULL = "FULL";
    public static final java.lang.String SYNC_MODE_NORMAL = "NORMAL";
    public static final java.lang.String SYNC_MODE_OFF = "OFF";
    private static final java.lang.String TAG = "SQLiteDatabase";
    private final android.database.sqlite.SQLiteDatabaseConfiguration mConfigurationLocked;
    private android.database.sqlite.SQLiteConnectionPool mConnectionPoolLocked;
    private final android.database.sqlite.SQLiteDatabase.CursorFactory mCursorFactory;
    private final android.database.DatabaseErrorHandler mErrorHandler;
    private boolean mHasAttachedDbsLocked;
    private static final boolean DEBUG_CLOSE_IDLE_CONNECTIONS = android.os.SystemProperties.getBoolean("persist.debug.sqlite.close_idle_connections", false);
    private static java.util.WeakHashMap<android.database.sqlite.SQLiteDatabase, java.lang.Object> sActiveDatabases = new java.util.WeakHashMap<>();
    public static final java.lang.String[] CONFLICT_VALUES = {"", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
    private final java.lang.ThreadLocal<android.database.sqlite.SQLiteSession> mThreadSession = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return android.database.sqlite.SQLiteDatabase.this.createSession();
        }
    });
    private final java.lang.Object mLock = new java.lang.Object();
    private final dalvik.system.CloseGuard mCloseGuardLocked = dalvik.system.CloseGuard.get();

    public interface CursorFactory {
        android.database.Cursor newCursor(android.database.sqlite.SQLiteDatabase sQLiteDatabase, android.database.sqlite.SQLiteCursorDriver sQLiteCursorDriver, java.lang.String str, android.database.sqlite.SQLiteQuery sQLiteQuery);
    }

    public interface CustomFunction {
        void callback(java.lang.String[] strArr);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DatabaseOpenFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface JournalMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SyncMode {
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private SQLiteDatabase(java.lang.String str, int i, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, android.database.DatabaseErrorHandler databaseErrorHandler, int i2, int i3, long j, java.lang.String str2, java.lang.String str3) {
        this.mCursorFactory = cursorFactory;
        this.mErrorHandler = databaseErrorHandler == null ? new android.database.DefaultDatabaseErrorHandler() : databaseErrorHandler;
        this.mConfigurationLocked = new android.database.sqlite.SQLiteDatabaseConfiguration(str, i);
        this.mConfigurationLocked.lookasideSlotSize = i2;
        this.mConfigurationLocked.lookasideSlotCount = i3;
        if (android.app.ActivityManager.isLowRamDeviceStatic()) {
            this.mConfigurationLocked.lookasideSlotCount = 0;
            this.mConfigurationLocked.lookasideSlotSize = 0;
        }
        if (!this.mConfigurationLocked.isInMemoryDb()) {
            if (j < 0) {
                if (DEBUG_CLOSE_IDLE_CONNECTIONS) {
                    j = android.database.sqlite.SQLiteGlobal.getIdleConnectionTimeout();
                }
            }
            this.mConfigurationLocked.idleConnectionTimeoutMs = j;
            if (android.database.sqlite.SQLiteCompatibilityWalFlags.isLegacyCompatibilityWalEnabled()) {
                this.mConfigurationLocked.openFlags |= Integer.MIN_VALUE;
            }
            this.mConfigurationLocked.journalMode = str2;
            this.mConfigurationLocked.syncMode = str3;
        }
        j = Long.MAX_VALUE;
        this.mConfigurationLocked.idleConnectionTimeoutMs = j;
        if (android.database.sqlite.SQLiteCompatibilityWalFlags.isLegacyCompatibilityWalEnabled()) {
        }
        this.mConfigurationLocked.journalMode = str2;
        this.mConfigurationLocked.syncMode = str3;
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    @Override // android.database.sqlite.SQLiteClosable
    protected void onAllReferencesReleased() {
        dispose(false);
    }

    private void dispose(boolean z) {
        android.database.sqlite.SQLiteConnectionPool sQLiteConnectionPool;
        synchronized (this.mLock) {
            if (this.mCloseGuardLocked != null) {
                if (z) {
                    this.mCloseGuardLocked.warnIfOpen();
                }
                this.mCloseGuardLocked.close();
            }
            sQLiteConnectionPool = this.mConnectionPoolLocked;
            this.mConnectionPoolLocked = null;
        }
        if (!z) {
            synchronized (sActiveDatabases) {
                sActiveDatabases.remove(this);
            }
            if (sQLiteConnectionPool != null) {
                sQLiteConnectionPool.close();
            }
        }
    }

    public static int releaseMemory() {
        return android.database.sqlite.SQLiteGlobal.releaseMemory();
    }

    @java.lang.Deprecated
    public void setLockingEnabled(boolean z) {
    }

    java.lang.String getLabel() {
        java.lang.String str;
        synchronized (this.mLock) {
            str = this.mConfigurationLocked.label;
        }
        return str;
    }

    void onCorruption() {
        android.util.EventLog.writeEvent(EVENT_DB_CORRUPT, getLabel());
        this.mErrorHandler.onCorruption(this);
    }

    android.database.sqlite.SQLiteSession getThreadSession() {
        return this.mThreadSession.get();
    }

    android.database.sqlite.SQLiteSession createSession() {
        android.database.sqlite.SQLiteConnectionPool sQLiteConnectionPool;
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            sQLiteConnectionPool = this.mConnectionPoolLocked;
        }
        return new android.database.sqlite.SQLiteSession(sQLiteConnectionPool);
    }

    int getThreadDefaultConnectionFlags(boolean z) {
        int i = z ? 1 : 2;
        if (isMainThread()) {
            return i | 4;
        }
        return i;
    }

    private static boolean isMainThread() {
        android.os.Looper myLooper = android.os.Looper.myLooper();
        return myLooper != null && myLooper == android.os.Looper.getMainLooper();
    }

    public void beginTransaction() {
        beginTransaction((android.database.sqlite.SQLiteTransactionListener) null, true);
    }

    public void beginTransactionNonExclusive() {
        beginTransaction((android.database.sqlite.SQLiteTransactionListener) null, false);
    }

    public void beginTransactionReadOnly() {
        beginTransactionWithListenerReadOnly(null);
    }

    public void beginTransactionWithListener(android.database.sqlite.SQLiteTransactionListener sQLiteTransactionListener) {
        beginTransaction(sQLiteTransactionListener, true);
    }

    public void beginTransactionWithListenerNonExclusive(android.database.sqlite.SQLiteTransactionListener sQLiteTransactionListener) {
        beginTransaction(sQLiteTransactionListener, false);
    }

    public void beginTransactionWithListenerReadOnly(android.database.sqlite.SQLiteTransactionListener sQLiteTransactionListener) {
        beginTransaction(sQLiteTransactionListener, 0);
    }

    private void beginTransaction(android.database.sqlite.SQLiteTransactionListener sQLiteTransactionListener, boolean z) {
        beginTransaction(sQLiteTransactionListener, z ? 2 : 1);
    }

    private void beginTransaction(android.database.sqlite.SQLiteTransactionListener sQLiteTransactionListener, int i) {
        acquireReference();
        try {
            getThreadSession().beginTransaction(i, sQLiteTransactionListener, getThreadDefaultConnectionFlags(i == 0), null);
        } finally {
            releaseReference();
        }
    }

    public void endTransaction() {
        acquireReference();
        try {
            getThreadSession().endTransaction(null);
        } finally {
            releaseReference();
        }
    }

    public void setTransactionSuccessful() {
        acquireReference();
        try {
            getThreadSession().setTransactionSuccessful();
        } finally {
            releaseReference();
        }
    }

    public boolean inTransaction() {
        acquireReference();
        try {
            return getThreadSession().hasTransaction();
        } finally {
            releaseReference();
        }
    }

    public boolean isDbLockedByCurrentThread() {
        acquireReference();
        try {
            return getThreadSession().hasConnection();
        } finally {
            releaseReference();
        }
    }

    @java.lang.Deprecated
    public boolean isDbLockedByOtherThreads() {
        return false;
    }

    @java.lang.Deprecated
    public boolean yieldIfContended() {
        return yieldIfContendedHelper(false, -1L);
    }

    public boolean yieldIfContendedSafely() {
        return yieldIfContendedHelper(true, -1L);
    }

    public boolean yieldIfContendedSafely(long j) {
        return yieldIfContendedHelper(true, j);
    }

    private boolean yieldIfContendedHelper(boolean z, long j) {
        acquireReference();
        try {
            return getThreadSession().yieldTransaction(j, z, null);
        } finally {
            releaseReference();
        }
    }

    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String> getSyncedTables() {
        return new java.util.HashMap(0);
    }

    public static android.database.sqlite.SQLiteDatabase openDatabase(java.lang.String str, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, int i) {
        return openDatabase(str, cursorFactory, i, null);
    }

    public static android.database.sqlite.SQLiteDatabase openDatabase(java.io.File file, android.database.sqlite.SQLiteDatabase.OpenParams openParams) {
        return openDatabase(file.getPath(), openParams);
    }

    private static android.database.sqlite.SQLiteDatabase openDatabase(java.lang.String str, android.database.sqlite.SQLiteDatabase.OpenParams openParams) {
        com.android.internal.util.Preconditions.checkArgument(openParams != null, "OpenParams cannot be null");
        android.database.sqlite.SQLiteDatabase sQLiteDatabase = new android.database.sqlite.SQLiteDatabase(str, openParams.mOpenFlags, openParams.mCursorFactory, openParams.mErrorHandler, openParams.mLookasideSlotSize, openParams.mLookasideSlotCount, openParams.mIdleConnectionTimeout, openParams.mJournalMode, openParams.mSyncMode);
        sQLiteDatabase.open();
        return sQLiteDatabase;
    }

    public static android.database.sqlite.SQLiteDatabase openDatabase(java.lang.String str, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, int i, android.database.DatabaseErrorHandler databaseErrorHandler) {
        android.database.sqlite.SQLiteDatabase sQLiteDatabase = new android.database.sqlite.SQLiteDatabase(str, i, cursorFactory, databaseErrorHandler, -1, -1, -1L, null, null);
        sQLiteDatabase.open();
        return sQLiteDatabase;
    }

    public static android.database.sqlite.SQLiteDatabase openOrCreateDatabase(java.io.File file, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory) {
        return openOrCreateDatabase(file.getPath(), cursorFactory);
    }

    public static android.database.sqlite.SQLiteDatabase openOrCreateDatabase(java.lang.String str, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory) {
        return openDatabase(str, cursorFactory, 268435456, null);
    }

    public static android.database.sqlite.SQLiteDatabase openOrCreateDatabase(java.lang.String str, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, android.database.DatabaseErrorHandler databaseErrorHandler) {
        return openDatabase(str, cursorFactory, 268435456, databaseErrorHandler);
    }

    public static boolean deleteDatabase(java.io.File file) {
        return deleteDatabase(file, true);
    }

    public static boolean deleteDatabase(java.io.File file, boolean z) {
        if (file == null) {
            throw new java.lang.IllegalArgumentException("file must not be null");
        }
        boolean delete = file.delete() | false | new java.io.File(file.getPath() + "-journal").delete() | new java.io.File(file.getPath() + "-shm").delete() | new java.io.File(file.getPath() + "-wal").delete();
        new java.io.File(file.getPath() + "-wipecheck").delete();
        java.io.File parentFile = file.getParentFile();
        if (parentFile != null) {
            final java.lang.String str = file.getName() + "-mj";
            java.io.File[] listFiles = parentFile.listFiles(new java.io.FileFilter() { // from class: android.database.sqlite.SQLiteDatabase.1
                @Override // java.io.FileFilter
                public boolean accept(java.io.File file2) {
                    return file2.getName().startsWith(str);
                }
            });
            if (listFiles != null) {
                for (java.io.File file2 : listFiles) {
                    delete |= file2.delete();
                }
            }
        }
        return delete;
    }

    public void reopenReadWrite() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (isReadOnlyLocked()) {
                int i = this.mConfigurationLocked.openFlags;
                this.mConfigurationLocked.openFlags = (this.mConfigurationLocked.openFlags & (-2)) | 0;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                } catch (java.lang.RuntimeException e) {
                    this.mConfigurationLocked.openFlags = i;
                    throw e;
                }
            }
        }
    }

    private void open() {
        try {
            try {
                openInner();
            } catch (java.lang.RuntimeException e) {
                if (android.database.sqlite.SQLiteDatabaseCorruptException.isCorruptException(e)) {
                    android.util.Log.e(TAG, "Database corruption detected in open()", e);
                    onCorruption();
                    openInner();
                    return;
                }
                throw e;
            }
        } catch (android.database.sqlite.SQLiteException e2) {
            android.util.Log.e(TAG, "Failed to open database '" + getLabel() + "'.", e2);
            close();
            throw e2;
        }
    }

    private void openInner() {
        synchronized (this.mLock) {
            this.mConnectionPoolLocked = android.database.sqlite.SQLiteConnectionPool.open(this.mConfigurationLocked);
            this.mCloseGuardLocked.open("close");
        }
        synchronized (sActiveDatabases) {
            sActiveDatabases.put(this, null);
        }
    }

    public static android.database.sqlite.SQLiteDatabase create(android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory) {
        return openDatabase(android.database.sqlite.SQLiteDatabaseConfiguration.MEMORY_DB_PATH, cursorFactory, 268435456);
    }

    public static android.database.sqlite.SQLiteDatabase createInMemory(android.database.sqlite.SQLiteDatabase.OpenParams openParams) {
        return openDatabase(android.database.sqlite.SQLiteDatabaseConfiguration.MEMORY_DB_PATH, openParams.toBuilder().addOpenFlags(268435456).build());
    }

    public void setCustomScalarFunction(java.lang.String str, java.util.function.UnaryOperator<java.lang.String> unaryOperator) throws android.database.sqlite.SQLiteException {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(unaryOperator);
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConfigurationLocked.customScalarFunctions.put(str, unaryOperator);
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (java.lang.RuntimeException e) {
                this.mConfigurationLocked.customScalarFunctions.remove(str);
                throw e;
            }
        }
    }

    public void setCustomAggregateFunction(java.lang.String str, java.util.function.BinaryOperator<java.lang.String> binaryOperator) throws android.database.sqlite.SQLiteException {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(binaryOperator);
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            this.mConfigurationLocked.customAggregateFunctions.put(str, binaryOperator);
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (java.lang.RuntimeException e) {
                this.mConfigurationLocked.customAggregateFunctions.remove(str);
                throw e;
            }
        }
    }

    public void execPerConnectionSQL(java.lang.String str, java.lang.Object[] objArr) throws android.database.SQLException {
        java.util.Objects.requireNonNull(str);
        java.lang.Object[] deepCopyOf = android.database.DatabaseUtils.deepCopyOf(objArr);
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            int size = this.mConfigurationLocked.perConnectionSql.size();
            this.mConfigurationLocked.perConnectionSql.add(android.util.Pair.create(str, deepCopyOf));
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (java.lang.RuntimeException e) {
                this.mConfigurationLocked.perConnectionSql.remove(size);
                throw e;
            }
        }
    }

    public int getVersion() {
        return java.lang.Long.valueOf(android.database.DatabaseUtils.longForQuery(this, "PRAGMA user_version;", null)).intValue();
    }

    public void setVersion(int i) {
        execSQL("PRAGMA user_version = " + i);
    }

    public long getMaximumSize() {
        return android.database.DatabaseUtils.longForQuery(this, "PRAGMA max_page_count;", null) * getPageSize();
    }

    public long setMaximumSize(long j) {
        long pageSize = getPageSize();
        long j2 = j / pageSize;
        if (j % pageSize != 0) {
            j2++;
        }
        return android.database.DatabaseUtils.longForQuery(this, "PRAGMA max_page_count = " + j2, null) * pageSize;
    }

    public long getPageSize() {
        return android.database.DatabaseUtils.longForQuery(this, "PRAGMA page_size;", null);
    }

    public void setPageSize(long j) {
        execSQL("PRAGMA page_size = " + j);
    }

    @java.lang.Deprecated
    public void markTableSyncable(java.lang.String str, java.lang.String str2) {
    }

    @java.lang.Deprecated
    public void markTableSyncable(java.lang.String str, java.lang.String str2, java.lang.String str3) {
    }

    public static java.lang.String findEditTable(java.lang.String str) {
        if (!android.text.TextUtils.isEmpty(str)) {
            int indexOf = str.indexOf(32);
            int indexOf2 = str.indexOf(44);
            if (indexOf > 0 && (indexOf < indexOf2 || indexOf2 < 0)) {
                return str.substring(0, indexOf);
            }
            if (indexOf2 > 0 && (indexOf2 < indexOf || indexOf < 0)) {
                return str.substring(0, indexOf2);
            }
            return str;
        }
        throw new java.lang.IllegalStateException("Invalid tables");
    }

    public android.database.sqlite.SQLiteStatement compileStatement(java.lang.String str) throws android.database.SQLException {
        acquireReference();
        try {
            return new android.database.sqlite.SQLiteStatement(this, str, null);
        } finally {
            releaseReference();
        }
    }

    public android.database.Cursor query(boolean z, java.lang.String str, java.lang.String[] strArr, java.lang.String str2, java.lang.String[] strArr2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6) {
        return queryWithFactory(null, z, str, strArr, str2, strArr2, str3, str4, str5, str6, null);
    }

    public android.database.Cursor query(boolean z, java.lang.String str, java.lang.String[] strArr, java.lang.String str2, java.lang.String[] strArr2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, android.os.CancellationSignal cancellationSignal) {
        return queryWithFactory(null, z, str, strArr, str2, strArr2, str3, str4, str5, str6, cancellationSignal);
    }

    public android.database.Cursor queryWithFactory(android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, boolean z, java.lang.String str, java.lang.String[] strArr, java.lang.String str2, java.lang.String[] strArr2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6) {
        return queryWithFactory(cursorFactory, z, str, strArr, str2, strArr2, str3, str4, str5, str6, null);
    }

    public android.database.Cursor queryWithFactory(android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, boolean z, java.lang.String str, java.lang.String[] strArr, java.lang.String str2, java.lang.String[] strArr2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, android.os.CancellationSignal cancellationSignal) {
        acquireReference();
        try {
            return rawQueryWithFactory(cursorFactory, android.database.sqlite.SQLiteQueryBuilder.buildQueryString(z, str, strArr, str2, str3, str4, str5, str6), strArr2, findEditTable(str), cancellationSignal);
        } finally {
            releaseReference();
        }
    }

    public android.database.Cursor query(java.lang.String str, java.lang.String[] strArr, java.lang.String str2, java.lang.String[] strArr2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
        return query(false, str, strArr, str2, strArr2, str3, str4, str5, null);
    }

    public android.database.Cursor query(java.lang.String str, java.lang.String[] strArr, java.lang.String str2, java.lang.String[] strArr2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6) {
        return query(false, str, strArr, str2, strArr2, str3, str4, str5, str6);
    }

    public android.database.Cursor rawQuery(java.lang.String str, java.lang.String[] strArr) {
        return rawQueryWithFactory(null, str, strArr, null, null);
    }

    public android.database.Cursor rawQuery(java.lang.String str, java.lang.String[] strArr, android.os.CancellationSignal cancellationSignal) {
        return rawQueryWithFactory(null, str, strArr, null, cancellationSignal);
    }

    public android.database.Cursor rawQueryWithFactory(android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, java.lang.String str, java.lang.String[] strArr, java.lang.String str2) {
        return rawQueryWithFactory(cursorFactory, str, strArr, str2, null);
    }

    public android.database.Cursor rawQueryWithFactory(android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, java.lang.String str, java.lang.String[] strArr, java.lang.String str2, android.os.CancellationSignal cancellationSignal) {
        acquireReference();
        try {
            android.database.sqlite.SQLiteDirectCursorDriver sQLiteDirectCursorDriver = new android.database.sqlite.SQLiteDirectCursorDriver(this, str, str2, cancellationSignal);
            if (cursorFactory == null) {
                cursorFactory = this.mCursorFactory;
            }
            return sQLiteDirectCursorDriver.query(cursorFactory, strArr);
        } finally {
            releaseReference();
        }
    }

    public long insert(java.lang.String str, java.lang.String str2, android.content.ContentValues contentValues) {
        try {
            return insertWithOnConflict(str, str2, contentValues, 0);
        } catch (android.database.SQLException e) {
            android.util.Log.e(TAG, "Error inserting " + contentValues, e);
            return -1L;
        }
    }

    public long insertOrThrow(java.lang.String str, java.lang.String str2, android.content.ContentValues contentValues) throws android.database.SQLException {
        return insertWithOnConflict(str, str2, contentValues, 0);
    }

    public long replace(java.lang.String str, java.lang.String str2, android.content.ContentValues contentValues) {
        try {
            return insertWithOnConflict(str, str2, contentValues, 5);
        } catch (android.database.SQLException e) {
            android.util.Log.e(TAG, "Error inserting " + contentValues, e);
            return -1L;
        }
    }

    public long replaceOrThrow(java.lang.String str, java.lang.String str2, android.content.ContentValues contentValues) throws android.database.SQLException {
        return insertWithOnConflict(str, str2, contentValues, 5);
    }

    public long insertWithOnConflict(java.lang.String str, java.lang.String str2, android.content.ContentValues contentValues, int i) {
        int i2;
        java.lang.Object[] objArr;
        acquireReference();
        try {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("INSERT");
            sb.append(CONFLICT_VALUES[i]);
            sb.append(" INTO ");
            sb.append(str);
            sb.append('(');
            int i3 = 0;
            if (contentValues == null || contentValues.isEmpty()) {
                i2 = 0;
            } else {
                i2 = contentValues.size();
            }
            if (i2 > 0) {
                objArr = new java.lang.Object[i2];
                int i4 = 0;
                for (java.lang.String str3 : contentValues.keySet()) {
                    sb.append(i4 > 0 ? "," : "");
                    sb.append(str3);
                    objArr[i4] = contentValues.get(str3);
                    i4++;
                }
                sb.append(')');
                sb.append(" VALUES (");
                while (i3 < i2) {
                    sb.append(i3 > 0 ? ",?" : "?");
                    i3++;
                }
            } else {
                sb.append(str2).append(") VALUES (NULL");
                objArr = null;
            }
            sb.append(')');
            android.database.sqlite.SQLiteStatement sQLiteStatement = new android.database.sqlite.SQLiteStatement(this, sb.toString(), objArr);
            try {
                return sQLiteStatement.executeInsert();
            } finally {
                sQLiteStatement.close();
            }
        } finally {
            releaseReference();
        }
    }

    public int delete(java.lang.String str, java.lang.String str2, java.lang.String[] strArr) {
        acquireReference();
        try {
            android.database.sqlite.SQLiteStatement sQLiteStatement = new android.database.sqlite.SQLiteStatement(this, "DELETE FROM " + str + (!android.text.TextUtils.isEmpty(str2) ? " WHERE " + str2 : ""), strArr);
            try {
                return sQLiteStatement.executeUpdateDelete();
            } finally {
                sQLiteStatement.close();
            }
        } finally {
            releaseReference();
        }
    }

    public int update(java.lang.String str, android.content.ContentValues contentValues, java.lang.String str2, java.lang.String[] strArr) {
        return updateWithOnConflict(str, contentValues, str2, strArr, 0);
    }

    public int updateWithOnConflict(java.lang.String str, android.content.ContentValues contentValues, java.lang.String str2, java.lang.String[] strArr, int i) {
        if (contentValues == null || contentValues.isEmpty()) {
            throw new java.lang.IllegalArgumentException("Empty values");
        }
        acquireReference();
        try {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(120);
            sb.append("UPDATE ");
            sb.append(CONFLICT_VALUES[i]);
            sb.append(str);
            sb.append(" SET ");
            int size = contentValues.size();
            int length = strArr == null ? size : strArr.length + size;
            java.lang.Object[] objArr = new java.lang.Object[length];
            int i2 = 0;
            for (java.lang.String str3 : contentValues.keySet()) {
                sb.append(i2 > 0 ? "," : "");
                sb.append(str3);
                objArr[i2] = contentValues.get(str3);
                sb.append("=?");
                i2++;
            }
            if (strArr != null) {
                for (int i3 = size; i3 < length; i3++) {
                    objArr[i3] = strArr[i3 - size];
                }
            }
            if (!android.text.TextUtils.isEmpty(str2)) {
                sb.append(" WHERE ");
                sb.append(str2);
            }
            android.database.sqlite.SQLiteStatement sQLiteStatement = new android.database.sqlite.SQLiteStatement(this, sb.toString(), objArr);
            try {
                return sQLiteStatement.executeUpdateDelete();
            } finally {
                sQLiteStatement.close();
            }
        } finally {
            releaseReference();
        }
    }

    public void execSQL(java.lang.String str) throws android.database.SQLException {
        executeSql(str, null);
    }

    public void execSQL(java.lang.String str, java.lang.Object[] objArr) throws android.database.SQLException {
        if (objArr == null) {
            throw new java.lang.IllegalArgumentException("Empty bindArgs");
        }
        executeSql(str, objArr);
    }

    public int executeSql(java.lang.String str, java.lang.Object[] objArr) throws android.database.SQLException {
        boolean z;
        acquireReference();
        try {
            int sqlStatementType = android.database.DatabaseUtils.getSqlStatementType(str);
            if (sqlStatementType == 3) {
                synchronized (this.mLock) {
                    if (this.mHasAttachedDbsLocked) {
                        z = false;
                    } else {
                        z = true;
                        this.mHasAttachedDbsLocked = true;
                        this.mConnectionPoolLocked.disableIdleConnectionHandler();
                    }
                }
                if (z) {
                    disableWriteAheadLogging();
                }
            }
            try {
                android.database.sqlite.SQLiteStatement sQLiteStatement = new android.database.sqlite.SQLiteStatement(this, str, objArr);
                try {
                    int executeUpdateDelete = sQLiteStatement.executeUpdateDelete();
                    sQLiteStatement.close();
                    return executeUpdateDelete;
                } finally {
                }
            } finally {
                if (sqlStatementType == 8) {
                    this.mConnectionPoolLocked.closeAvailableNonPrimaryConnectionsAndLogExceptions();
                    this.mConnectionPoolLocked.clearAcquiredConnectionsPreparedStatementCache();
                }
            }
        } finally {
            releaseReference();
        }
    }

    public android.database.sqlite.SQLiteRawStatement createRawStatement(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        return new android.database.sqlite.SQLiteRawStatement(this, str);
    }

    public long getLastInsertRowId() {
        return getThreadSession().getLastInsertRowId();
    }

    public long getLastChangedRowCount() {
        return getThreadSession().getLastChangedRowCount();
    }

    public long getTotalChangedRowCount() {
        return getThreadSession().getTotalChangedRowCount();
    }

    public void validateSql(java.lang.String str, android.os.CancellationSignal cancellationSignal) {
        getThreadSession().prepare(str, getThreadDefaultConnectionFlags(true), cancellationSignal, null);
    }

    public boolean isReadOnly() {
        boolean isReadOnlyLocked;
        synchronized (this.mLock) {
            isReadOnlyLocked = isReadOnlyLocked();
        }
        return isReadOnlyLocked;
    }

    private boolean isReadOnlyLocked() {
        return (this.mConfigurationLocked.openFlags & 1) == 1;
    }

    public boolean isInMemoryDatabase() {
        boolean isInMemoryDb;
        synchronized (this.mLock) {
            isInMemoryDb = this.mConfigurationLocked.isInMemoryDb();
        }
        return isInMemoryDb;
    }

    public boolean isOpen() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mConnectionPoolLocked != null;
        }
        return z;
    }

    public boolean needUpgrade(int i) {
        return i > getVersion();
    }

    public final java.lang.String getPath() {
        java.lang.String str;
        synchronized (this.mLock) {
            str = this.mConfigurationLocked.path;
        }
        return str;
    }

    public void setLocale(java.util.Locale locale) {
        if (locale == null) {
            throw new java.lang.IllegalArgumentException("locale must not be null.");
        }
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            java.util.Locale locale2 = this.mConfigurationLocked.locale;
            this.mConfigurationLocked.locale = locale;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (java.lang.RuntimeException e) {
                this.mConfigurationLocked.locale = locale2;
                throw e;
            }
        }
    }

    public void setMaxSqlCacheSize(int i) {
        if (i > 100 || i < 0) {
            throw new java.lang.IllegalStateException("expected value between 0 and 100");
        }
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            int i2 = this.mConfigurationLocked.maxSqlCacheSize;
            this.mConfigurationLocked.maxSqlCacheSize = i;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (java.lang.RuntimeException e) {
                this.mConfigurationLocked.maxSqlCacheSize = i2;
                throw e;
            }
        }
    }

    @dalvik.annotation.optimization.NeverCompile
    public double getStatementCacheMissRate() {
        double statementCacheMissRate;
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            statementCacheMissRate = this.mConnectionPoolLocked.getStatementCacheMissRate();
        }
        return statementCacheMissRate;
    }

    public void setForeignKeyConstraintsEnabled(boolean z) {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.foreignKeyConstraintsEnabled == z) {
                return;
            }
            this.mConfigurationLocked.foreignKeyConstraintsEnabled = z;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
            } catch (java.lang.RuntimeException e) {
                this.mConfigurationLocked.foreignKeyConstraintsEnabled = !z;
                throw e;
            }
        }
    }

    public boolean enableWriteAheadLogging() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            if (this.mConfigurationLocked.resolveJournalMode().equalsIgnoreCase(JOURNAL_MODE_WAL)) {
                return true;
            }
            if (isReadOnlyLocked()) {
                return false;
            }
            if (this.mConfigurationLocked.isInMemoryDb()) {
                android.util.Log.i(TAG, "can't enable WAL for memory databases.");
                return false;
            }
            if (this.mHasAttachedDbsLocked) {
                if (android.util.Log.isLoggable(TAG, 3)) {
                    android.util.Log.d(TAG, "this database: " + this.mConfigurationLocked.label + " has attached databases. can't  enable WAL.");
                }
                return false;
            }
            this.mConfigurationLocked.openFlags |= 536870912;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                return true;
            } catch (java.lang.RuntimeException e) {
                this.mConfigurationLocked.openFlags &= -536870913;
                throw e;
            }
        }
    }

    public void disableWriteAheadLogging() {
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            int i = this.mConfigurationLocked.openFlags;
            if (this.mConfigurationLocked.resolveJournalMode().equalsIgnoreCase(JOURNAL_MODE_WAL)) {
                this.mConfigurationLocked.openFlags &= -536870913;
                this.mConfigurationLocked.openFlags &= Integer.MAX_VALUE;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                } catch (java.lang.RuntimeException e) {
                    this.mConfigurationLocked.openFlags = i;
                    throw e;
                }
            }
        }
    }

    public boolean isWriteAheadLoggingEnabled() {
        boolean equalsIgnoreCase;
        synchronized (this.mLock) {
            throwIfNotOpenLocked();
            equalsIgnoreCase = this.mConfigurationLocked.resolveJournalMode().equalsIgnoreCase(JOURNAL_MODE_WAL);
        }
        return equalsIgnoreCase;
    }

    static java.util.ArrayList<android.database.sqlite.SQLiteDebug.DbStats> getDbStats() {
        java.util.ArrayList<android.database.sqlite.SQLiteDebug.DbStats> arrayList = new java.util.ArrayList<>();
        java.util.Iterator<android.database.sqlite.SQLiteDatabase> it = getActiveDatabases().iterator();
        while (it.hasNext()) {
            it.next().collectDbStats(arrayList);
        }
        return arrayList;
    }

    private void collectDbStats(java.util.ArrayList<android.database.sqlite.SQLiteDebug.DbStats> arrayList) {
        synchronized (this.mLock) {
            if (this.mConnectionPoolLocked != null) {
                this.mConnectionPoolLocked.collectDbStats(arrayList);
            }
        }
    }

    private static java.util.ArrayList<android.database.sqlite.SQLiteDatabase> getActiveDatabases() {
        java.util.ArrayList<android.database.sqlite.SQLiteDatabase> arrayList = new java.util.ArrayList<>();
        synchronized (sActiveDatabases) {
            arrayList.addAll(sActiveDatabases.keySet());
        }
        return arrayList;
    }

    private static java.util.ArrayList<android.database.sqlite.SQLiteConnectionPool> getActiveDatabasePools() {
        java.util.ArrayList<android.database.sqlite.SQLiteConnectionPool> arrayList = new java.util.ArrayList<>();
        synchronized (sActiveDatabases) {
            for (android.database.sqlite.SQLiteDatabase sQLiteDatabase : sActiveDatabases.keySet()) {
                synchronized (sQLiteDatabase.mLock) {
                    if (sQLiteDatabase.mConnectionPoolLocked != null) {
                        arrayList.add(sQLiteDatabase.mConnectionPoolLocked);
                    }
                }
            }
        }
        return arrayList;
    }

    @dalvik.annotation.optimization.NeverCompile
    public int getTotalPreparedStatements() {
        throwIfNotOpenLocked();
        return this.mConnectionPoolLocked.mTotalPrepareStatements;
    }

    @dalvik.annotation.optimization.NeverCompile
    public int getTotalStatementCacheMisses() {
        throwIfNotOpenLocked();
        return this.mConnectionPoolLocked.mTotalPrepareStatementCacheMiss;
    }

    static void dumpAll(android.util.Printer printer, boolean z, boolean z2) {
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        java.util.ArrayList<android.database.sqlite.SQLiteConnectionPool> activeDatabasePools = getActiveDatabasePools();
        activeDatabasePools.sort(new java.util.Comparator() { // from class: android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int compare;
                compare = java.lang.Long.compare(((android.database.sqlite.SQLiteConnectionPool) obj2).getTotalStatementsCount(), ((android.database.sqlite.SQLiteConnectionPool) obj).getTotalStatementsCount());
                return compare;
            }
        });
        java.util.Iterator<android.database.sqlite.SQLiteConnectionPool> it = activeDatabasePools.iterator();
        long j = 0;
        long j2 = 0;
        while (it.hasNext()) {
            android.database.sqlite.SQLiteConnectionPool next = it.next();
            next.dump(printer, z, arraySet);
            j2 += next.getTotalStatementsTime();
            j += next.getTotalStatementsCount();
        }
        if (j > 0) {
            printer.println("Statements Executed per Database");
            java.util.Iterator<android.database.sqlite.SQLiteConnectionPool> it2 = activeDatabasePools.iterator();
            while (it2.hasNext()) {
                android.database.sqlite.SQLiteConnectionPool next2 = it2.next();
                printer.println("  " + next2.getPath() + " :    " + next2.getTotalStatementsCount());
            }
            printer.println("");
            printer.println("Total Statements Executed for all Active Databases: " + j);
            activeDatabasePools.sort(new java.util.Comparator() { // from class: android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda2
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int compare;
                    compare = java.lang.Long.compare(((android.database.sqlite.SQLiteConnectionPool) obj2).getTotalStatementsTime(), ((android.database.sqlite.SQLiteConnectionPool) obj).getTotalStatementsTime());
                    return compare;
                }
            });
            printer.println("");
            printer.println("");
            printer.println("Statement Time per Database (ms)");
            java.util.Iterator<android.database.sqlite.SQLiteConnectionPool> it3 = activeDatabasePools.iterator();
            while (it3.hasNext()) {
                android.database.sqlite.SQLiteConnectionPool next3 = it3.next();
                printer.println("  " + next3.getPath() + " :    " + next3.getTotalStatementsTime());
            }
            printer.println("Total Statements Time for all Active Databases (ms): " + j2);
        }
        if (arraySet.size() > 0) {
            java.lang.String[] strArr = (java.lang.String[]) arraySet.toArray(new java.lang.String[arraySet.size()]);
            java.util.Arrays.sort(strArr);
            for (java.lang.String str : strArr) {
                dumpDatabaseDirectory(printer, new java.io.File(str), z2);
            }
        }
    }

    private static void dumpDatabaseDirectory(android.util.Printer printer, java.io.File file, boolean z) {
        int i;
        printer.println("");
        printer.println("Database files in " + file.getAbsolutePath() + ":");
        java.io.File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            printer.println("  [none]");
            return;
        }
        java.util.Arrays.sort(listFiles, new java.util.Comparator() { // from class: android.database.sqlite.SQLiteDatabase$$ExternalSyntheticLambda3
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int compareTo;
                compareTo = ((java.io.File) obj).getName().compareTo(((java.io.File) obj2).getName());
                return compareTo;
            }
        });
        int length = listFiles.length;
        while (i < length) {
            java.io.File file2 = listFiles[i];
            if (z) {
                java.lang.String name = file2.getName();
                i = (name.endsWith(".db") || name.endsWith(".db-wal") || name.endsWith(".db-journal") || name.endsWith("-wipecheck")) ? 0 : i + 1;
            }
            printer.println(java.lang.String.format("  %-40s %7db %s", file2.getName(), java.lang.Long.valueOf(file2.length()), getFileTimestamps(file2.getAbsolutePath())));
        }
    }

    public java.util.List<android.util.Pair<java.lang.String, java.lang.String>> getAttachedDbs() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            android.database.Cursor cursor = null;
            if (this.mConnectionPoolLocked == null) {
                return null;
            }
            if (!this.mHasAttachedDbsLocked) {
                arrayList.add(new android.util.Pair("main", this.mConfigurationLocked.path));
                return arrayList;
            }
            acquireReference();
            try {
                try {
                    cursor = rawQuery("pragma database_list;", null);
                    while (cursor.moveToNext()) {
                        arrayList.add(new android.util.Pair(cursor.getString(1), cursor.getString(2)));
                    }
                    return arrayList;
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            } finally {
                releaseReference();
            }
        }
    }

    public boolean isDatabaseIntegrityOk() {
        java.util.List<android.util.Pair<java.lang.String, java.lang.String>> arrayList;
        acquireReference();
        try {
            try {
                arrayList = getAttachedDbs();
            } catch (android.database.sqlite.SQLiteException e) {
                arrayList = new java.util.ArrayList<>();
                arrayList.add(new android.util.Pair<>("main", getPath()));
            }
            if (arrayList == null) {
                throw new java.lang.IllegalStateException("databaselist for: " + getPath() + " couldn't be retrieved. probably because the database is closed");
            }
            for (int i = 0; i < arrayList.size(); i++) {
                android.util.Pair<java.lang.String, java.lang.String> pair = arrayList.get(i);
                android.database.sqlite.SQLiteStatement sQLiteStatement = null;
                try {
                    sQLiteStatement = compileStatement("PRAGMA " + pair.first + ".integrity_check(1);");
                    java.lang.String simpleQueryForString = sQLiteStatement.simpleQueryForString();
                    if (!simpleQueryForString.equalsIgnoreCase("ok")) {
                        android.util.Log.e(TAG, "PRAGMA integrity_check on " + pair.second + " returned: " + simpleQueryForString);
                        return false;
                    }
                    if (sQLiteStatement != null) {
                        sQLiteStatement.close();
                    }
                } finally {
                    if (sQLiteStatement != null) {
                        sQLiteStatement.close();
                    }
                }
            }
            releaseReference();
            return true;
        } finally {
            releaseReference();
        }
    }

    public java.lang.String toString() {
        return "SQLiteDatabase: " + getPath();
    }

    private void throwIfNotOpenLocked() {
        if (this.mConnectionPoolLocked == null) {
            throw new java.lang.IllegalStateException("The database '" + this.mConfigurationLocked.label + "' is not open.");
        }
    }

    public static final class OpenParams {
        private final android.database.sqlite.SQLiteDatabase.CursorFactory mCursorFactory;
        private final android.database.DatabaseErrorHandler mErrorHandler;
        private final long mIdleConnectionTimeout;
        private final java.lang.String mJournalMode;
        private final int mLookasideSlotCount;
        private final int mLookasideSlotSize;
        private final int mOpenFlags;
        private final java.lang.String mSyncMode;

        private OpenParams(int i, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, android.database.DatabaseErrorHandler databaseErrorHandler, int i2, int i3, long j, java.lang.String str, java.lang.String str2) {
            this.mOpenFlags = i;
            this.mCursorFactory = cursorFactory;
            this.mErrorHandler = databaseErrorHandler;
            this.mLookasideSlotSize = i2;
            this.mLookasideSlotCount = i3;
            this.mIdleConnectionTimeout = j;
            this.mJournalMode = str;
            this.mSyncMode = str2;
        }

        public int getLookasideSlotSize() {
            return this.mLookasideSlotSize;
        }

        public int getLookasideSlotCount() {
            return this.mLookasideSlotCount;
        }

        public int getOpenFlags() {
            return this.mOpenFlags;
        }

        public android.database.sqlite.SQLiteDatabase.CursorFactory getCursorFactory() {
            return this.mCursorFactory;
        }

        public android.database.DatabaseErrorHandler getErrorHandler() {
            return this.mErrorHandler;
        }

        public long getIdleConnectionTimeout() {
            return this.mIdleConnectionTimeout;
        }

        public java.lang.String getJournalMode() {
            return this.mJournalMode;
        }

        public java.lang.String getSynchronousMode() {
            return this.mSyncMode;
        }

        public android.database.sqlite.SQLiteDatabase.OpenParams.Builder toBuilder() {
            return new android.database.sqlite.SQLiteDatabase.OpenParams.Builder(this);
        }

        public static final class Builder {
            private android.database.sqlite.SQLiteDatabase.CursorFactory mCursorFactory;
            private android.database.DatabaseErrorHandler mErrorHandler;
            private long mIdleConnectionTimeout;
            private java.lang.String mJournalMode;
            private int mLookasideSlotCount;
            private int mLookasideSlotSize;
            private int mOpenFlags;
            private java.lang.String mSyncMode;

            public Builder() {
                this.mLookasideSlotSize = -1;
                this.mLookasideSlotCount = -1;
                this.mIdleConnectionTimeout = -1L;
            }

            public Builder(android.database.sqlite.SQLiteDatabase.OpenParams openParams) {
                this.mLookasideSlotSize = -1;
                this.mLookasideSlotCount = -1;
                this.mIdleConnectionTimeout = -1L;
                this.mLookasideSlotSize = openParams.mLookasideSlotSize;
                this.mLookasideSlotCount = openParams.mLookasideSlotCount;
                this.mOpenFlags = openParams.mOpenFlags;
                this.mCursorFactory = openParams.mCursorFactory;
                this.mErrorHandler = openParams.mErrorHandler;
                this.mJournalMode = openParams.mJournalMode;
                this.mSyncMode = openParams.mSyncMode;
            }

            public android.database.sqlite.SQLiteDatabase.OpenParams.Builder setLookasideConfig(int i, int i2) {
                boolean z = true;
                com.android.internal.util.Preconditions.checkArgument(i >= 0, "lookasideSlotCount cannot be negative");
                com.android.internal.util.Preconditions.checkArgument(i2 >= 0, "lookasideSlotSize cannot be negative");
                if ((i <= 0 || i2 <= 0) && (i2 != 0 || i != 0)) {
                    z = false;
                }
                com.android.internal.util.Preconditions.checkArgument(z, "Invalid configuration: %d, %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                this.mLookasideSlotSize = i;
                this.mLookasideSlotCount = i2;
                return this;
            }

            public boolean isWriteAheadLoggingEnabled() {
                return (this.mOpenFlags & 536870912) != 0;
            }

            public android.database.sqlite.SQLiteDatabase.OpenParams.Builder setOpenFlags(int i) {
                this.mOpenFlags = i;
                return this;
            }

            public android.database.sqlite.SQLiteDatabase.OpenParams.Builder addOpenFlags(int i) {
                this.mOpenFlags = i | this.mOpenFlags;
                return this;
            }

            public android.database.sqlite.SQLiteDatabase.OpenParams.Builder removeOpenFlags(int i) {
                this.mOpenFlags = (~i) & this.mOpenFlags;
                return this;
            }

            public void setWriteAheadLoggingEnabled(boolean z) {
                if (z) {
                    addOpenFlags(536870912);
                } else {
                    removeOpenFlags(536870912);
                }
            }

            public android.database.sqlite.SQLiteDatabase.OpenParams.Builder setCursorFactory(android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory) {
                this.mCursorFactory = cursorFactory;
                return this;
            }

            public android.database.sqlite.SQLiteDatabase.OpenParams.Builder setErrorHandler(android.database.DatabaseErrorHandler databaseErrorHandler) {
                this.mErrorHandler = databaseErrorHandler;
                return this;
            }

            @java.lang.Deprecated
            public android.database.sqlite.SQLiteDatabase.OpenParams.Builder setIdleConnectionTimeout(long j) {
                com.android.internal.util.Preconditions.checkArgument(j >= 0, "idle connection timeout cannot be negative");
                this.mIdleConnectionTimeout = j;
                return this;
            }

            public android.database.sqlite.SQLiteDatabase.OpenParams.Builder setJournalMode(java.lang.String str) {
                java.util.Objects.requireNonNull(str);
                this.mJournalMode = str;
                return this;
            }

            public android.database.sqlite.SQLiteDatabase.OpenParams.Builder setSynchronousMode(java.lang.String str) {
                java.util.Objects.requireNonNull(str);
                this.mSyncMode = str;
                return this;
            }

            public android.database.sqlite.SQLiteDatabase.OpenParams build() {
                return new android.database.sqlite.SQLiteDatabase.OpenParams(this.mOpenFlags, this.mCursorFactory, this.mErrorHandler, this.mLookasideSlotSize, this.mLookasideSlotCount, this.mIdleConnectionTimeout, this.mJournalMode, this.mSyncMode);
            }
        }
    }

    public static void wipeDetected(java.lang.String str, java.lang.String str2) {
        wtfAsSystemServer(TAG, "DB wipe detected: package=" + android.app.ActivityThread.currentPackageName() + " reason=" + str2 + " file=" + str + " " + getFileTimestamps(str) + " checkfile " + getFileTimestamps(str + "-wipecheck"), new java.lang.Throwable("STACKTRACE"));
    }

    public static java.lang.String getFileTimestamps(java.lang.String str) {
        try {
            java.nio.file.attribute.BasicFileAttributes readAttributes = java.nio.file.Files.readAttributes(java.nio.file.FileSystems.getDefault().getPath(str, new java.lang.String[0]), (java.lang.Class<java.nio.file.attribute.BasicFileAttributes>) java.nio.file.attribute.BasicFileAttributes.class, new java.nio.file.LinkOption[0]);
            return "ctime=" + readAttributes.creationTime() + " mtime=" + readAttributes.lastModifiedTime() + " atime=" + readAttributes.lastAccessTime();
        } catch (java.io.IOException e) {
            return "[unable to obtain timestamp]";
        }
    }

    static void wtfAsSystemServer(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        android.util.Log.e(str, str2, th);
        android.content.ContentResolver.onDbCorruption(str, str2, th);
    }
}
