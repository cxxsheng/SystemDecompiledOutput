package android.database.sqlite;

/* loaded from: classes.dex */
public final class SQLiteConnection implements android.os.CancellationSignal.OnCancelListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "SQLiteConnection";
    private int mCancellationSignalAttachCount;
    private final android.database.sqlite.SQLiteDatabaseConfiguration mConfiguration;
    private final int mConnectionId;
    private long mConnectionPtr;
    private final boolean mIsPrimaryConnection;
    private final boolean mIsReadOnlyConnection;
    private boolean mOnlyAllowReadOnlyOperations;
    private final android.database.sqlite.SQLiteConnectionPool mPool;
    private final android.database.sqlite.SQLiteConnection.PreparedStatementCache mPreparedStatementCache;
    private android.database.sqlite.SQLiteConnection.PreparedStatement mPreparedStatementPool;
    private final android.database.sqlite.SQLiteConnection.OperationLog mRecentOperations;
    private static final java.lang.String[] EMPTY_STRING_ARRAY = new java.lang.String[0];
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private boolean mAllowTempTableRetry = android.database.sqlite.Flags.sqliteAllowTempTables();

    private static native void nativeBindBlob(long j, long j2, int i, byte[] bArr);

    private static native void nativeBindDouble(long j, long j2, int i, double d);

    private static native void nativeBindLong(long j, long j2, int i, long j3);

    private static native void nativeBindNull(long j, long j2, int i);

    private static native void nativeBindString(long j, long j2, int i, java.lang.String str);

    private static native void nativeCancel(long j);

    private static native long nativeChanges(long j);

    private static native void nativeClose(long j);

    private static native void nativeExecute(long j, long j2, boolean z);

    private static native int nativeExecuteForBlobFileDescriptor(long j, long j2);

    private static native int nativeExecuteForChangedRowCount(long j, long j2);

    private static native long nativeExecuteForCursorWindow(long j, long j2, long j3, int i, int i2, boolean z);

    private static native long nativeExecuteForLastInsertedRowId(long j, long j2);

    private static native long nativeExecuteForLong(long j, long j2);

    private static native java.lang.String nativeExecuteForString(long j, long j2);

    private static native void nativeFinalizeStatement(long j, long j2);

    private static native int nativeGetColumnCount(long j, long j2);

    private static native java.lang.String nativeGetColumnName(long j, long j2, int i);

    private static native int nativeGetDbLookaside(long j);

    private static native int nativeGetParameterCount(long j, long j2);

    private static native boolean nativeIsReadOnly(long j, long j2);

    private static native int nativeLastInsertRowId(long j);

    private static native long nativeOpen(java.lang.String str, int i, java.lang.String str2, boolean z, boolean z2, int i2, int i3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativePrepareStatement(long j, java.lang.String str);

    private static native void nativeRegisterCustomAggregateFunction(long j, java.lang.String str, java.util.function.BinaryOperator<java.lang.String> binaryOperator);

    private static native void nativeRegisterCustomScalarFunction(long j, java.lang.String str, java.util.function.UnaryOperator<java.lang.String> unaryOperator);

    private static native void nativeRegisterLocalizedCollators(long j, java.lang.String str);

    private static native void nativeResetCancel(long j, boolean z);

    private static native void nativeResetStatementAndClearBindings(long j, long j2);

    private static native long nativeTotalChanges(long j);

    private static native boolean nativeUpdatesTempOnly(long j, long j2);

    private SQLiteConnection(android.database.sqlite.SQLiteConnectionPool sQLiteConnectionPool, android.database.sqlite.SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, int i, boolean z) {
        this.mPool = sQLiteConnectionPool;
        this.mRecentOperations = new android.database.sqlite.SQLiteConnection.OperationLog(this.mPool);
        this.mConfiguration = new android.database.sqlite.SQLiteDatabaseConfiguration(sQLiteDatabaseConfiguration);
        this.mConnectionId = i;
        this.mIsPrimaryConnection = z;
        this.mIsReadOnlyConnection = this.mConfiguration.isReadOnlyDatabase();
        this.mPreparedStatementCache = new android.database.sqlite.SQLiteConnection.PreparedStatementCache(this.mConfiguration.maxSqlCacheSize);
        this.mCloseGuard.open("SQLiteConnection.close");
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mPool != null && this.mConnectionPtr != 0) {
                this.mPool.onConnectionLeaked();
            }
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    static android.database.sqlite.SQLiteConnection open(android.database.sqlite.SQLiteConnectionPool sQLiteConnectionPool, android.database.sqlite.SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, int i, boolean z) {
        android.database.sqlite.SQLiteConnection sQLiteConnection = new android.database.sqlite.SQLiteConnection(sQLiteConnectionPool, sQLiteDatabaseConfiguration, i, z);
        try {
            sQLiteConnection.open();
            return sQLiteConnection;
        } catch (android.database.sqlite.SQLiteException e) {
            sQLiteConnection.dispose(false);
            throw e;
        }
    }

    void close() {
        dispose(false);
    }

    private void open() {
        java.lang.String str = this.mConfiguration.path;
        int beginOperation = this.mRecentOperations.beginOperation(android.telephony.ims.RcsContactPresenceTuple.TUPLE_BASIC_STATUS_OPEN, null, null);
        try {
            try {
                this.mConnectionPtr = nativeOpen(str, this.mConfiguration.openFlags, this.mConfiguration.label, android.database.sqlite.SQLiteDebug.NoPreloadHolder.DEBUG_SQL_STATEMENTS, android.database.sqlite.SQLiteDebug.NoPreloadHolder.DEBUG_SQL_TIME, this.mConfiguration.lookasideSlotSize, this.mConfiguration.lookasideSlotCount);
                this.mRecentOperations.endOperation(beginOperation);
                setPageSize();
                setForeignKeyModeFromConfiguration();
                setJournalFromConfiguration();
                setSyncModeFromConfiguration();
                setJournalSizeLimit();
                setAutoCheckpointInterval();
                setLocaleFromConfiguration();
                setCustomFunctionsFromConfiguration();
                executePerConnectionSqlFromConfiguration(0);
            } catch (android.database.sqlite.SQLiteCantOpenDatabaseException e) {
                java.lang.StringBuilder append = new java.lang.StringBuilder("Cannot open database '").append(str).append(android.text.format.DateFormat.QUOTE).append(" with flags 0x").append(java.lang.Integer.toHexString(this.mConfiguration.openFlags));
                try {
                    java.nio.file.Path path = java.nio.file.FileSystems.getDefault().getPath(str, new java.lang.String[0]);
                    java.nio.file.Path parent = path.getParent();
                    if (parent == null) {
                        append.append(": Directory not specified in the file path");
                    } else if (!java.nio.file.Files.isDirectory(parent, new java.nio.file.LinkOption[0])) {
                        append.append(": Directory ").append(parent).append(" doesn't exist");
                    } else if (!java.nio.file.Files.exists(path, new java.nio.file.LinkOption[0])) {
                        append.append(": File ").append(path).append(" doesn't exist");
                        if ((this.mConfiguration.openFlags & 268435456) != 0) {
                            append.append(" and CREATE_IF_NECESSARY is set, check directory permissions");
                        }
                    } else if (!java.nio.file.Files.isReadable(path)) {
                        append.append(": File ").append(path).append(" is not readable");
                    } else if (java.nio.file.Files.isDirectory(path, new java.nio.file.LinkOption[0])) {
                        append.append(": Path ").append(path).append(" is a directory");
                    } else {
                        append.append(": Unable to deduct failure reason");
                    }
                } catch (java.lang.Throwable th) {
                    append.append(": Unable to deduct failure reason because filesystem couldn't be examined: ").append(th.getMessage());
                }
                throw new android.database.sqlite.SQLiteCantOpenDatabaseException(append.toString(), e);
            }
        } catch (java.lang.Throwable th2) {
            this.mRecentOperations.endOperation(beginOperation);
            throw th2;
        }
    }

    private void dispose(boolean z) {
        if (this.mCloseGuard != null) {
            if (z) {
                this.mCloseGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if (this.mConnectionPtr != 0) {
            int beginOperation = this.mRecentOperations.beginOperation("close", null, null);
            try {
                this.mPreparedStatementCache.evictAll();
                nativeClose(this.mConnectionPtr);
                this.mConnectionPtr = 0L;
            } finally {
                this.mRecentOperations.endOperation(beginOperation);
            }
        }
    }

    private void setPageSize() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long defaultPageSize = android.database.sqlite.SQLiteGlobal.getDefaultPageSize();
            if (executeForLong("PRAGMA page_size", null, null) != defaultPageSize) {
                execute("PRAGMA page_size=" + defaultPageSize, null, null);
            }
        }
    }

    private void setAutoCheckpointInterval() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long wALAutoCheckpoint = android.database.sqlite.SQLiteGlobal.getWALAutoCheckpoint();
            if (executeForLong("PRAGMA wal_autocheckpoint", null, null) != wALAutoCheckpoint) {
                executeForLong("PRAGMA wal_autocheckpoint=" + wALAutoCheckpoint, null, null);
            }
        }
    }

    private void setJournalSizeLimit() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long journalSizeLimit = android.database.sqlite.SQLiteGlobal.getJournalSizeLimit();
            if (executeForLong("PRAGMA journal_size_limit", null, null) != journalSizeLimit) {
                executeForLong("PRAGMA journal_size_limit=" + journalSizeLimit, null, null);
            }
        }
    }

    private void setForeignKeyModeFromConfiguration() {
        if (!this.mIsReadOnlyConnection) {
            long j = this.mConfiguration.foreignKeyConstraintsEnabled ? 1L : 0L;
            if (executeForLong("PRAGMA foreign_keys", null, null) != j) {
                execute("PRAGMA foreign_keys=" + j, null, null);
            }
        }
    }

    private void setJournalFromConfiguration() {
        if (!this.mIsReadOnlyConnection) {
            setJournalMode(this.mConfiguration.resolveJournalMode());
            maybeTruncateWalFile();
        } else {
            this.mConfiguration.shouldTruncateWalFile = false;
        }
    }

    private void setSyncModeFromConfiguration() {
        if (!this.mIsReadOnlyConnection) {
            setSyncMode(this.mConfiguration.resolveSyncMode());
        }
    }

    private void maybeTruncateWalFile() {
        if (!this.mConfiguration.shouldTruncateWalFile) {
            return;
        }
        long wALTruncateSize = android.database.sqlite.SQLiteGlobal.getWALTruncateSize();
        if (wALTruncateSize == 0) {
            return;
        }
        java.io.File file = new java.io.File(this.mConfiguration.path + "-wal");
        if (!file.isFile() || file.length() < wALTruncateSize) {
            return;
        }
        try {
            executeForString("PRAGMA wal_checkpoint(TRUNCATE)", null, null);
            this.mConfiguration.shouldTruncateWalFile = false;
        } catch (android.database.sqlite.SQLiteException e) {
            android.util.Log.w(TAG, "Failed to truncate the -wal file", e);
        }
    }

    private void setSyncMode(java.lang.String str) {
        if (!android.text.TextUtils.isEmpty(str) && !canonicalizeSyncMode(executeForString("PRAGMA synchronous", null, null)).equalsIgnoreCase(canonicalizeSyncMode(str))) {
            execute("PRAGMA synchronous=" + str, null, null);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static java.lang.String canonicalizeSyncMode(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case 48:
                if (str.equals(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (str.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (str.equals("2")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (str.equals("3")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return "OFF";
            case 1:
                return android.database.sqlite.SQLiteDatabase.SYNC_MODE_NORMAL;
            case 2:
                return "FULL";
            case 3:
                return android.database.sqlite.SQLiteDatabase.SYNC_MODE_EXTRA;
            default:
                return str;
        }
    }

    private void setJournalMode(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return;
        }
        java.lang.String executeForString = executeForString("PRAGMA journal_mode", null, null);
        if (!executeForString.equalsIgnoreCase(str)) {
            try {
                if (executeForString("PRAGMA journal_mode=" + str, null, null).equalsIgnoreCase(str)) {
                    return;
                }
            } catch (android.database.sqlite.SQLiteDatabaseLockedException e) {
            }
            android.util.Log.w(TAG, "Could not change the database journal mode of '" + this.mConfiguration.label + "' from '" + executeForString + "' to '" + str + "' because the database is locked.  This usually means that there are other open connections to the database which prevents the database from enabling or disabling write-ahead logging mode.  Proceeding without changing the journal mode.");
        }
    }

    private void setLocaleFromConfiguration() {
        if ((this.mConfiguration.openFlags & 16) != 0) {
            return;
        }
        java.lang.String obj = this.mConfiguration.locale.toString();
        nativeRegisterLocalizedCollators(this.mConnectionPtr, obj);
        if (!this.mConfiguration.isInMemoryDb()) {
            checkDatabaseWiped();
        }
        if (this.mIsReadOnlyConnection) {
            return;
        }
        try {
            execute("CREATE TABLE IF NOT EXISTS android_metadata (locale TEXT)", null, null);
            java.lang.String executeForString = executeForString("SELECT locale FROM android_metadata UNION SELECT NULL ORDER BY locale DESC LIMIT 1", null, null);
            if (executeForString != null && executeForString.equals(obj)) {
                return;
            }
            execute("BEGIN", null, null);
            try {
                execute("DELETE FROM android_metadata", null, null);
                execute("INSERT INTO android_metadata (locale) VALUES(?)", new java.lang.Object[]{obj}, null);
                execute("REINDEX LOCALIZED", null, null);
                execute("COMMIT", null, null);
            } catch (java.lang.Throwable th) {
                execute("ROLLBACK", null, null);
                throw th;
            }
        } catch (android.database.sqlite.SQLiteException e) {
            throw e;
        } catch (java.lang.RuntimeException e2) {
            throw new android.database.sqlite.SQLiteException("Failed to change locale for db '" + this.mConfiguration.label + "' to '" + obj + "'.", e2);
        }
    }

    private void setCustomFunctionsFromConfiguration() {
        for (int i = 0; i < this.mConfiguration.customScalarFunctions.size(); i++) {
            nativeRegisterCustomScalarFunction(this.mConnectionPtr, this.mConfiguration.customScalarFunctions.keyAt(i), this.mConfiguration.customScalarFunctions.valueAt(i));
        }
        for (int i2 = 0; i2 < this.mConfiguration.customAggregateFunctions.size(); i2++) {
            nativeRegisterCustomAggregateFunction(this.mConnectionPtr, this.mConfiguration.customAggregateFunctions.keyAt(i2), this.mConfiguration.customAggregateFunctions.valueAt(i2));
        }
    }

    private void executePerConnectionSqlFromConfiguration(int i) {
        while (i < this.mConfiguration.perConnectionSql.size()) {
            android.util.Pair<java.lang.String, java.lang.Object[]> pair = this.mConfiguration.perConnectionSql.get(i);
            switch (android.database.DatabaseUtils.getSqlStatementType(pair.first)) {
                case 1:
                    executeForString(pair.first, pair.second, null);
                    break;
                case 7:
                    execute(pair.first, pair.second, null);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported configuration statement: " + pair);
            }
            i++;
        }
    }

    private void checkDatabaseWiped() {
        if (!android.database.sqlite.SQLiteGlobal.checkDbWipe()) {
            return;
        }
        try {
            java.io.File file = new java.io.File(this.mConfiguration.path + "-wipecheck");
            boolean z = executeForLong("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='android_metadata'", null, null) > 0;
            boolean exists = file.exists();
            if (!this.mIsReadOnlyConnection && !exists) {
                file.createNewFile();
            }
            if (!z && exists) {
                android.database.sqlite.SQLiteDatabase.wipeDetected(this.mConfiguration.path, "unknown");
            }
        } catch (java.io.IOException | java.lang.RuntimeException e) {
            android.database.sqlite.SQLiteDatabase.wtfAsSystemServer(TAG, "Unexpected exception while checking for wipe", e);
        }
    }

    void reconfigure(android.database.sqlite.SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        this.mOnlyAllowReadOnlyOperations = false;
        boolean z = sQLiteDatabaseConfiguration.foreignKeyConstraintsEnabled != this.mConfiguration.foreignKeyConstraintsEnabled;
        boolean z2 = !sQLiteDatabaseConfiguration.locale.equals(this.mConfiguration.locale);
        boolean z3 = !sQLiteDatabaseConfiguration.customScalarFunctions.equals(this.mConfiguration.customScalarFunctions);
        boolean z4 = !sQLiteDatabaseConfiguration.customAggregateFunctions.equals(this.mConfiguration.customAggregateFunctions);
        int size = this.mConfiguration.perConnectionSql.size();
        boolean z5 = sQLiteDatabaseConfiguration.perConnectionSql.size() > size;
        boolean z6 = !sQLiteDatabaseConfiguration.resolveJournalMode().equalsIgnoreCase(this.mConfiguration.resolveJournalMode());
        boolean equalsIgnoreCase = true ^ sQLiteDatabaseConfiguration.resolveSyncMode().equalsIgnoreCase(this.mConfiguration.resolveSyncMode());
        this.mConfiguration.updateParametersFrom(sQLiteDatabaseConfiguration);
        this.mPreparedStatementCache.resize(sQLiteDatabaseConfiguration.maxSqlCacheSize);
        if (z) {
            setForeignKeyModeFromConfiguration();
        }
        if (z6) {
            setJournalFromConfiguration();
        }
        if (equalsIgnoreCase) {
            setSyncModeFromConfiguration();
        }
        if (z2) {
            setLocaleFromConfiguration();
        }
        if (z3 || z4) {
            setCustomFunctionsFromConfiguration();
        }
        if (z5) {
            executePerConnectionSqlFromConfiguration(size);
        }
    }

    void setOnlyAllowReadOnlyOperations(boolean z) {
        this.mOnlyAllowReadOnlyOperations = z;
    }

    boolean isPreparedStatementInCache(java.lang.String str) {
        return this.mPreparedStatementCache.get(str) != null;
    }

    public int getConnectionId() {
        return this.mConnectionId;
    }

    public boolean isPrimaryConnection() {
        return this.mIsPrimaryConnection;
    }

    public void prepare(java.lang.String str, android.database.sqlite.SQLiteStatementInfo sQLiteStatementInfo) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        int beginOperation = this.mRecentOperations.beginOperation("prepare", str, null);
        try {
            try {
                android.database.sqlite.SQLiteConnection.PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                if (sQLiteStatementInfo != null) {
                    try {
                        sQLiteStatementInfo.numParameters = acquirePreparedStatement.mNumParameters;
                        sQLiteStatementInfo.readOnly = acquirePreparedStatement.mReadOnly;
                        int nativeGetColumnCount = nativeGetColumnCount(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr);
                        if (nativeGetColumnCount == 0) {
                            sQLiteStatementInfo.columnNames = EMPTY_STRING_ARRAY;
                        } else {
                            sQLiteStatementInfo.columnNames = new java.lang.String[nativeGetColumnCount];
                            for (int i = 0; i < nativeGetColumnCount; i++) {
                                sQLiteStatementInfo.columnNames[i] = nativeGetColumnName(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr, i);
                            }
                        }
                    } finally {
                        releasePreparedStatement(acquirePreparedStatement);
                    }
                }
            } catch (java.lang.RuntimeException e) {
                this.mRecentOperations.failOperation(beginOperation, e);
                throw e;
            }
        } finally {
            this.mRecentOperations.endOperation(beginOperation);
        }
    }

    public void execute(java.lang.String str, java.lang.Object[] objArr, android.os.CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        int beginOperation = this.mRecentOperations.beginOperation("execute", str, objArr);
        try {
            try {
                boolean z = android.database.DatabaseUtils.getSqlStatementType(str) == 7;
                android.database.sqlite.SQLiteConnection.PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                try {
                    throwIfStatementForbidden(acquirePreparedStatement);
                    bindArguments(acquirePreparedStatement, objArr);
                    applyBlockGuardPolicy(acquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        nativeExecute(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr, z);
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(acquirePreparedStatement);
                }
            } catch (java.lang.RuntimeException e) {
                this.mRecentOperations.failOperation(beginOperation, e);
                throw e;
            }
        } finally {
            this.mRecentOperations.endOperation(beginOperation);
        }
    }

    public long executeForLong(java.lang.String str, java.lang.Object[] objArr, android.os.CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        int beginOperation = this.mRecentOperations.beginOperation("executeForLong", str, objArr);
        try {
            try {
                android.database.sqlite.SQLiteConnection.PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                try {
                    throwIfStatementForbidden(acquirePreparedStatement);
                    bindArguments(acquirePreparedStatement, objArr);
                    applyBlockGuardPolicy(acquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        long nativeExecuteForLong = nativeExecuteForLong(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr);
                        this.mRecentOperations.setResult(nativeExecuteForLong);
                        return nativeExecuteForLong;
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(acquirePreparedStatement);
                }
            } catch (java.lang.RuntimeException e) {
                this.mRecentOperations.failOperation(beginOperation, e);
                throw e;
            }
        } finally {
            this.mRecentOperations.endOperation(beginOperation);
        }
    }

    public java.lang.String executeForString(java.lang.String str, java.lang.Object[] objArr, android.os.CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        int beginOperation = this.mRecentOperations.beginOperation("executeForString", str, objArr);
        try {
            try {
                android.database.sqlite.SQLiteConnection.PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                try {
                    throwIfStatementForbidden(acquirePreparedStatement);
                    bindArguments(acquirePreparedStatement, objArr);
                    applyBlockGuardPolicy(acquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        java.lang.String nativeExecuteForString = nativeExecuteForString(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr);
                        this.mRecentOperations.setResult(nativeExecuteForString);
                        return nativeExecuteForString;
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(acquirePreparedStatement);
                }
            } catch (java.lang.RuntimeException e) {
                this.mRecentOperations.failOperation(beginOperation, e);
                throw e;
            }
        } finally {
            this.mRecentOperations.endOperation(beginOperation);
        }
    }

    public android.os.ParcelFileDescriptor executeForBlobFileDescriptor(java.lang.String str, java.lang.Object[] objArr, android.os.CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        int beginOperation = this.mRecentOperations.beginOperation("executeForBlobFileDescriptor", str, objArr);
        try {
            try {
                android.database.sqlite.SQLiteConnection.PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                try {
                    throwIfStatementForbidden(acquirePreparedStatement);
                    bindArguments(acquirePreparedStatement, objArr);
                    applyBlockGuardPolicy(acquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        int nativeExecuteForBlobFileDescriptor = nativeExecuteForBlobFileDescriptor(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr);
                        return nativeExecuteForBlobFileDescriptor >= 0 ? android.os.ParcelFileDescriptor.adoptFd(nativeExecuteForBlobFileDescriptor) : null;
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(acquirePreparedStatement);
                }
            } catch (java.lang.RuntimeException e) {
                this.mRecentOperations.failOperation(beginOperation, e);
                throw e;
            }
        } finally {
            this.mRecentOperations.endOperation(beginOperation);
        }
    }

    public int executeForChangedRowCount(java.lang.String str, java.lang.Object[] objArr, android.os.CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        int beginOperation = this.mRecentOperations.beginOperation("executeForChangedRowCount", str, objArr);
        try {
            try {
                android.database.sqlite.SQLiteConnection.PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                try {
                    throwIfStatementForbidden(acquirePreparedStatement);
                    bindArguments(acquirePreparedStatement, objArr);
                    applyBlockGuardPolicy(acquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        int nativeExecuteForChangedRowCount = nativeExecuteForChangedRowCount(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr);
                        if (this.mRecentOperations.endOperationDeferLog(beginOperation)) {
                            this.mRecentOperations.logOperation(beginOperation, "changedRows=" + nativeExecuteForChangedRowCount);
                        }
                        return nativeExecuteForChangedRowCount;
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(acquirePreparedStatement);
                }
            } catch (java.lang.RuntimeException e) {
                this.mRecentOperations.failOperation(beginOperation, e);
                throw e;
            }
        } catch (java.lang.Throwable th) {
            if (this.mRecentOperations.endOperationDeferLog(beginOperation)) {
                this.mRecentOperations.logOperation(beginOperation, "changedRows=0");
            }
            throw th;
        }
    }

    public long executeForLastInsertedRowId(java.lang.String str, java.lang.Object[] objArr, android.os.CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        int beginOperation = this.mRecentOperations.beginOperation("executeForLastInsertedRowId", str, objArr);
        try {
            try {
                android.database.sqlite.SQLiteConnection.PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                try {
                    throwIfStatementForbidden(acquirePreparedStatement);
                    bindArguments(acquirePreparedStatement, objArr);
                    applyBlockGuardPolicy(acquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                        return nativeExecuteForLastInsertedRowId(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr);
                    } finally {
                        detachCancellationSignal(cancellationSignal);
                    }
                } finally {
                    releasePreparedStatement(acquirePreparedStatement);
                }
            } catch (java.lang.RuntimeException e) {
                this.mRecentOperations.failOperation(beginOperation, e);
                throw e;
            }
        } finally {
            this.mRecentOperations.endOperation(beginOperation);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x0186 A[Catch: all -> 0x01bd, TryCatch #14 {all -> 0x01bd, blocks: (B:6:0x0021, B:35:0x0075, B:37:0x007d, B:49:0x017e, B:51:0x0186, B:52:0x01bc), top: B:5:0x0021 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int executeForCursorWindow(java.lang.String str, java.lang.Object[] objArr, android.database.CursorWindow cursorWindow, int i, int i2, boolean z, android.os.CancellationSignal cancellationSignal) {
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        int i3;
        int i4;
        java.lang.String str5;
        java.lang.String str6;
        int i5;
        int i6;
        int i7;
        android.database.sqlite.SQLiteConnection.PreparedStatement preparedStatement;
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        if (cursorWindow == null) {
            throw new java.lang.IllegalArgumentException("window must not be null.");
        }
        cursorWindow.acquireReference();
        try {
            int beginOperation = this.mRecentOperations.beginOperation("executeForCursorWindow", str, objArr);
            int i8 = -1;
            try {
                android.database.sqlite.SQLiteConnection.PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                try {
                    throwIfStatementForbidden(acquirePreparedStatement);
                    bindArguments(acquirePreparedStatement, objArr);
                    applyBlockGuardPolicy(acquirePreparedStatement);
                    attachCancellationSignal(cancellationSignal);
                    try {
                    } catch (java.lang.Throwable th) {
                        th = th;
                        preparedStatement = acquirePreparedStatement;
                        str2 = "', startPos=";
                        str3 = ", filledRows=";
                        str4 = "window='";
                        i3 = i;
                    }
                    try {
                        try {
                            preparedStatement = acquirePreparedStatement;
                            i4 = beginOperation;
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            str5 = ", actualPos=";
                            str3 = ", filledRows=";
                            i3 = i;
                            str6 = ", countedRows=";
                            str2 = "', startPos=";
                            i4 = beginOperation;
                            str4 = "window='";
                            preparedStatement = acquirePreparedStatement;
                        }
                        try {
                            long nativeExecuteForCursorWindow = nativeExecuteForCursorWindow(this.mConnectionPtr, acquirePreparedStatement.mStatementPtr, cursorWindow.mWindowPtr, i, i2, z);
                            i7 = (int) (nativeExecuteForCursorWindow >> 32);
                            i5 = (int) nativeExecuteForCursorWindow;
                            try {
                                i6 = cursorWindow.getNumRows();
                            } catch (java.lang.Throwable th3) {
                                th = th3;
                                i3 = i;
                                str4 = "window='";
                                str6 = ", countedRows=";
                                str2 = "', startPos=";
                                str5 = ", actualPos=";
                                str3 = ", filledRows=";
                                i6 = -1;
                            }
                            try {
                                cursorWindow.setStartPosition(i7);
                                try {
                                    detachCancellationSignal(cancellationSignal);
                                    try {
                                        releasePreparedStatement(preparedStatement);
                                        if (this.mRecentOperations.endOperationDeferLog(i4)) {
                                            this.mRecentOperations.logOperation(i4, "window='" + cursorWindow + "', startPos=" + i + ", actualPos=" + i7 + ", filledRows=" + i6 + ", countedRows=" + i5);
                                        }
                                        return i5;
                                    } catch (java.lang.RuntimeException e) {
                                        e = e;
                                        i3 = i;
                                        str4 = "window='";
                                        str6 = ", countedRows=";
                                        str2 = "', startPos=";
                                        str5 = ", actualPos=";
                                        str3 = ", filledRows=";
                                        i8 = i7;
                                        this.mRecentOperations.failOperation(i4, e);
                                        throw e;
                                    } catch (java.lang.Throwable th4) {
                                        th = th4;
                                        i3 = i;
                                        str4 = "window='";
                                        str6 = ", countedRows=";
                                        str2 = "', startPos=";
                                        str5 = ", actualPos=";
                                        str3 = ", filledRows=";
                                        if (this.mRecentOperations.endOperationDeferLog(i4)) {
                                        }
                                        throw th;
                                    }
                                } catch (java.lang.Throwable th5) {
                                    th = th5;
                                    i3 = i;
                                    str4 = "window='";
                                    str6 = ", countedRows=";
                                    str2 = "', startPos=";
                                    str5 = ", actualPos=";
                                    str3 = ", filledRows=";
                                    i8 = i7;
                                    try {
                                        try {
                                            releasePreparedStatement(preparedStatement);
                                            throw th;
                                        } catch (java.lang.Throwable th6) {
                                            th = th6;
                                            i7 = i8;
                                            if (this.mRecentOperations.endOperationDeferLog(i4)) {
                                                this.mRecentOperations.logOperation(i4, str4 + cursorWindow + str2 + i3 + str5 + i7 + str3 + i6 + str6 + i5);
                                            }
                                            throw th;
                                        }
                                    } catch (java.lang.RuntimeException e2) {
                                        e = e2;
                                        this.mRecentOperations.failOperation(i4, e);
                                        throw e;
                                    }
                                }
                            } catch (java.lang.Throwable th7) {
                                th = th7;
                                i3 = i;
                                str4 = "window='";
                                str6 = ", countedRows=";
                                str2 = "', startPos=";
                                str5 = ", actualPos=";
                                str3 = ", filledRows=";
                                i8 = i7;
                                try {
                                    detachCancellationSignal(cancellationSignal);
                                    throw th;
                                } catch (java.lang.Throwable th8) {
                                    th = th8;
                                    releasePreparedStatement(preparedStatement);
                                    throw th;
                                }
                            }
                        } catch (java.lang.Throwable th9) {
                            th = th9;
                            i3 = i;
                            str4 = "window='";
                            str6 = ", countedRows=";
                            str2 = "', startPos=";
                            str5 = ", actualPos=";
                            str3 = ", filledRows=";
                            i5 = -1;
                            i6 = -1;
                            detachCancellationSignal(cancellationSignal);
                            throw th;
                        }
                    } catch (java.lang.Throwable th10) {
                        th = th10;
                        str2 = "', startPos=";
                        str3 = ", filledRows=";
                        i3 = i;
                        str4 = "window='";
                        preparedStatement = acquirePreparedStatement;
                        i4 = beginOperation;
                        str5 = ", actualPos=";
                        str6 = ", countedRows=";
                        i5 = -1;
                        i6 = -1;
                        detachCancellationSignal(cancellationSignal);
                        throw th;
                    }
                } catch (java.lang.Throwable th11) {
                    th = th11;
                    preparedStatement = acquirePreparedStatement;
                    str2 = "', startPos=";
                    str3 = ", filledRows=";
                    str4 = "window='";
                    i3 = i;
                    i4 = beginOperation;
                    str5 = ", actualPos=";
                    str6 = ", countedRows=";
                    i5 = -1;
                    i6 = -1;
                }
            } catch (java.lang.RuntimeException e3) {
                e = e3;
                str2 = "', startPos=";
                str3 = ", filledRows=";
                str4 = "window='";
                i3 = i;
                i4 = beginOperation;
                str5 = ", actualPos=";
                str6 = ", countedRows=";
                i5 = -1;
                i6 = -1;
            } catch (java.lang.Throwable th12) {
                th = th12;
                str2 = "', startPos=";
                str3 = ", filledRows=";
                str4 = "window='";
                i3 = i;
                i4 = beginOperation;
                str5 = ", actualPos=";
                str6 = ", countedRows=";
                i5 = -1;
                i6 = -1;
                i7 = -1;
            }
        } finally {
            cursorWindow.releaseReference();
        }
    }

    private android.database.sqlite.SQLiteConnection.PreparedStatement acquirePreparedStatementLI(java.lang.String str) {
        android.database.sqlite.SQLiteConnection.PreparedStatement preparedStatement;
        boolean z;
        this.mPool.mTotalPrepareStatements++;
        android.database.sqlite.SQLiteConnection.PreparedStatement statement = this.mPreparedStatementCache.getStatement(str);
        long lastSeqNum = this.mPreparedStatementCache.getLastSeqNum();
        if (statement == null) {
            preparedStatement = statement;
            z = false;
        } else if (statement.mInUse) {
            preparedStatement = statement;
            z = true;
        } else {
            if (statement.mSeqNum == lastSeqNum) {
                statement.mInUse = true;
                return statement;
            }
            this.mPreparedStatementCache.remove(str);
            preparedStatement = null;
            z = false;
        }
        this.mPool.mTotalPrepareStatementCacheMiss++;
        long createStatement = this.mPreparedStatementCache.createStatement(str);
        long lastSeqNum2 = this.mPreparedStatementCache.getLastSeqNum();
        try {
            int nativeGetParameterCount = nativeGetParameterCount(this.mConnectionPtr, createStatement);
            int sqlStatementTypeExtended = android.database.DatabaseUtils.getSqlStatementTypeExtended(str);
            preparedStatement = obtainPreparedStatement(str, createStatement, nativeGetParameterCount, sqlStatementTypeExtended, nativeIsReadOnly(this.mConnectionPtr, createStatement), lastSeqNum2);
            if (!z && isCacheable(sqlStatementTypeExtended)) {
                this.mPreparedStatementCache.put(str, preparedStatement);
                preparedStatement.mInCache = true;
            }
            preparedStatement.mInUse = true;
            return preparedStatement;
        } catch (java.lang.RuntimeException e) {
            if (preparedStatement == null || !preparedStatement.mInCache) {
                nativeFinalizeStatement(this.mConnectionPtr, createStatement);
            }
            throw e;
        }
    }

    android.database.sqlite.SQLiteConnection.PreparedStatement acquirePreparedStatement(java.lang.String str) {
        return acquirePreparedStatementLI(str);
    }

    private void releasePreparedStatementLI(android.database.sqlite.SQLiteConnection.PreparedStatement preparedStatement) {
        preparedStatement.mInUse = false;
        if (preparedStatement.mInCache) {
            try {
                nativeResetStatementAndClearBindings(this.mConnectionPtr, preparedStatement.mStatementPtr);
                return;
            } catch (android.database.sqlite.SQLiteException e) {
                this.mPreparedStatementCache.remove(preparedStatement.mSql);
                return;
            }
        }
        finalizePreparedStatement(preparedStatement);
    }

    void releasePreparedStatement(android.database.sqlite.SQLiteConnection.PreparedStatement preparedStatement) {
        releasePreparedStatementLI(preparedStatement);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finalizePreparedStatement(android.database.sqlite.SQLiteConnection.PreparedStatement preparedStatement) {
        nativeFinalizeStatement(this.mConnectionPtr, preparedStatement.mStatementPtr);
        recyclePreparedStatement(preparedStatement);
    }

    android.database.sqlite.SQLiteConnection.PreparedStatement acquirePersistentStatement(java.lang.String str) {
        int beginOperation = this.mRecentOperations.beginOperation("prepare", str, null);
        try {
            try {
                android.database.sqlite.SQLiteConnection.PreparedStatement acquirePreparedStatement = acquirePreparedStatement(str);
                throwIfStatementForbidden(acquirePreparedStatement);
                return acquirePreparedStatement;
            } catch (java.lang.RuntimeException e) {
                this.mRecentOperations.failOperation(beginOperation, e);
                throw e;
            }
        } finally {
            this.mRecentOperations.endOperation(beginOperation);
        }
    }

    private void attachCancellationSignal(android.os.CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
            this.mCancellationSignalAttachCount++;
            if (this.mCancellationSignalAttachCount == 1) {
                nativeResetCancel(this.mConnectionPtr, true);
                cancellationSignal.setOnCancelListener(this);
            }
        }
    }

    private void detachCancellationSignal(android.os.CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            this.mCancellationSignalAttachCount--;
            if (this.mCancellationSignalAttachCount == 0) {
                cancellationSignal.setOnCancelListener(null);
                nativeResetCancel(this.mConnectionPtr, false);
            }
        }
    }

    @Override // android.os.CancellationSignal.OnCancelListener
    public void onCancel() {
        nativeCancel(this.mConnectionPtr);
    }

    private void bindArguments(android.database.sqlite.SQLiteConnection.PreparedStatement preparedStatement, java.lang.Object[] objArr) {
        int length = objArr != null ? objArr.length : 0;
        if (length != preparedStatement.mNumParameters) {
            throw new android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException("Expected " + preparedStatement.mNumParameters + " bind arguments but " + length + " were provided.");
        }
        if (length == 0) {
            return;
        }
        long j = preparedStatement.mStatementPtr;
        for (int i = 0; i < length; i++) {
            java.lang.Object obj = objArr[i];
            switch (android.database.DatabaseUtils.getTypeOfObject(obj)) {
                case 0:
                    nativeBindNull(this.mConnectionPtr, j, i + 1);
                    break;
                case 1:
                    nativeBindLong(this.mConnectionPtr, j, i + 1, ((java.lang.Number) obj).longValue());
                    break;
                case 2:
                    nativeBindDouble(this.mConnectionPtr, j, i + 1, ((java.lang.Number) obj).doubleValue());
                    break;
                case 3:
                default:
                    if (obj instanceof java.lang.Boolean) {
                        nativeBindLong(this.mConnectionPtr, j, i + 1, ((java.lang.Boolean) obj).booleanValue() ? 1L : 0L);
                        break;
                    } else {
                        nativeBindString(this.mConnectionPtr, j, i + 1, obj.toString());
                        break;
                    }
                case 4:
                    nativeBindBlob(this.mConnectionPtr, j, i + 1, (byte[]) obj);
                    break;
            }
        }
    }

    void throwIfStatementForbidden(android.database.sqlite.SQLiteConnection.PreparedStatement preparedStatement) {
        if (this.mOnlyAllowReadOnlyOperations && !preparedStatement.mReadOnly) {
            if (this.mAllowTempTableRetry) {
                preparedStatement.mReadOnly = nativeUpdatesTempOnly(this.mConnectionPtr, preparedStatement.mStatementPtr);
                if (preparedStatement.mReadOnly) {
                    return;
                }
            }
            throw new android.database.sqlite.SQLiteException("Cannot execute this statement because it might modify the database but the connection is read-only.");
        }
    }

    private static boolean isCacheable(int i) {
        return i == 2 || i == 1 || i == 100;
    }

    private void applyBlockGuardPolicy(android.database.sqlite.SQLiteConnection.PreparedStatement preparedStatement) {
        if (!this.mConfiguration.isInMemoryDb()) {
            if (preparedStatement.mReadOnly) {
                dalvik.system.BlockGuard.getThreadPolicy().onReadFromDisk();
            } else {
                dalvik.system.BlockGuard.getThreadPolicy().onWriteToDisk();
            }
        }
    }

    public void dump(android.util.Printer printer, boolean z) {
        dumpUnsafe(printer, z);
    }

    void dumpUnsafe(android.util.Printer printer, boolean z) {
        printer.println("Connection #" + this.mConnectionId + ":");
        if (z) {
            printer.println("  connectionPtr: 0x" + java.lang.Long.toHexString(this.mConnectionPtr));
        }
        printer.println("  isPrimaryConnection: " + this.mIsPrimaryConnection);
        printer.println("  onlyAllowReadOnlyOperations: " + this.mOnlyAllowReadOnlyOperations);
        this.mRecentOperations.dump(printer);
        if (z) {
            this.mPreparedStatementCache.dump(printer);
        }
    }

    java.lang.String describeCurrentOperationUnsafe() {
        return this.mRecentOperations.describeCurrentOperation();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:0|1|2|3|(3:4|5|6)|7|8|9|(11:12|13|14|15|16|17|18|(1:20)|21|22|10)|29|30|31|(1:(0))) */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0049 A[Catch: all -> 0x00cd, SQLiteException -> 0x00d2, TRY_LEAVE, TryCatch #2 {SQLiteException -> 0x00d2, blocks: (B:9:0x0034, B:10:0x0043, B:12:0x0049, B:18:0x0097, B:20:0x00a8, B:21:0x00b1), top: B:8:0x0034 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00a8 A[Catch: all -> 0x00cd, SQLiteException -> 0x00d2, TryCatch #2 {SQLiteException -> 0x00d2, blocks: (B:9:0x0034, B:10:0x0043, B:12:0x0049, B:18:0x0097, B:20:0x00a8, B:21:0x00b1), top: B:8:0x0034 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void collectDbStats(java.util.ArrayList<android.database.sqlite.SQLiteDebug.DbStats> arrayList) {
        long j;
        long j2;
        android.database.CursorWindow cursorWindow;
        int i;
        long j3;
        long j4;
        long j5;
        int nativeGetDbLookaside = nativeGetDbLookaside(this.mConnectionPtr);
        try {
            j = executeForLong("PRAGMA page_count;", null, null);
        } catch (android.database.sqlite.SQLiteException e) {
            j = 0;
        }
        try {
            try {
                j2 = executeForLong("PRAGMA page_size;", null, null);
            } catch (android.database.sqlite.SQLiteException e2) {
                j2 = 0;
                arrayList.add(getMainDbStatsUnsafe(nativeGetDbLookaside, j, j2));
                cursorWindow = new android.database.CursorWindow("collectDbStats");
                executeForCursorWindow("PRAGMA database_list;", null, cursorWindow, 0, 0, false, null);
                while (i < cursorWindow.getNumRows()) {
                }
                return;
            }
            executeForCursorWindow("PRAGMA database_list;", null, cursorWindow, 0, 0, false, null);
            for (i = 1; i < cursorWindow.getNumRows(); i++) {
                java.lang.String string = cursorWindow.getString(i, 1);
                java.lang.String string2 = cursorWindow.getString(i, 2);
                try {
                    j3 = executeForLong("PRAGMA " + string + ".page_count;", null, null);
                } catch (android.database.sqlite.SQLiteException e3) {
                    j3 = 0;
                }
                try {
                    j4 = j3;
                    j5 = executeForLong("PRAGMA " + string + ".page_size;", null, null);
                } catch (android.database.sqlite.SQLiteException e4) {
                    j4 = j3;
                    j5 = 0;
                    java.lang.StringBuilder append = new java.lang.StringBuilder("  (attached) ").append(string);
                    if (!string2.isEmpty()) {
                    }
                    arrayList.add(new android.database.sqlite.SQLiteDebug.DbStats(append.toString(), j4, j5, 0, 0, 0, 0, false));
                }
                java.lang.StringBuilder append2 = new java.lang.StringBuilder("  (attached) ").append(string);
                if (!string2.isEmpty()) {
                    append2.append(": ").append(string2);
                }
                arrayList.add(new android.database.sqlite.SQLiteDebug.DbStats(append2.toString(), j4, j5, 0, 0, 0, 0, false));
            }
            return;
        } finally {
            cursorWindow.close();
        }
        arrayList.add(getMainDbStatsUnsafe(nativeGetDbLookaside, j, j2));
        cursorWindow = new android.database.CursorWindow("collectDbStats");
    }

    void collectDbStatsUnsafe(java.util.ArrayList<android.database.sqlite.SQLiteDebug.DbStats> arrayList) {
        arrayList.add(getMainDbStatsUnsafe(0, 0L, 0L));
    }

    private android.database.sqlite.SQLiteDebug.DbStats getMainDbStatsUnsafe(int i, long j, long j2) {
        java.lang.String str;
        if (!this.mIsPrimaryConnection) {
            str = this.mConfiguration.path + " (" + this.mConnectionId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        } else {
            str = this.mConfiguration.path;
        }
        return new android.database.sqlite.SQLiteDebug.DbStats(str, j, j2, i, this.mPreparedStatementCache.hitCount(), this.mPreparedStatementCache.missCount(), this.mPreparedStatementCache.size(), false);
    }

    public java.lang.String toString() {
        return "SQLiteConnection: " + this.mConfiguration.path + " (" + this.mConnectionId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    private android.database.sqlite.SQLiteConnection.PreparedStatement obtainPreparedStatement(java.lang.String str, long j, int i, int i2, boolean z, long j2) {
        android.database.sqlite.SQLiteConnection.PreparedStatement preparedStatement = this.mPreparedStatementPool;
        if (preparedStatement != null) {
            this.mPreparedStatementPool = preparedStatement.mPoolNext;
            preparedStatement.mPoolNext = null;
            preparedStatement.mInCache = false;
        } else {
            preparedStatement = new android.database.sqlite.SQLiteConnection.PreparedStatement();
        }
        preparedStatement.mSql = str;
        preparedStatement.mStatementPtr = j;
        preparedStatement.mNumParameters = i;
        preparedStatement.mType = i2;
        preparedStatement.mReadOnly = z;
        preparedStatement.mSeqNum = j2;
        return preparedStatement;
    }

    private void recyclePreparedStatement(android.database.sqlite.SQLiteConnection.PreparedStatement preparedStatement) {
        preparedStatement.mSql = null;
        preparedStatement.mPoolNext = this.mPreparedStatementPool;
        this.mPreparedStatementPool = preparedStatement;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String trimSqlForDisplay(java.lang.String str) {
        return str.replaceAll("[\\s]*\\n+[\\s]*", " ");
    }

    void setDatabaseSeqNum(long j) {
        this.mPreparedStatementCache.setDatabaseSeqNum(j);
    }

    static final class PreparedStatement {
        public boolean mInCache;
        public boolean mInUse;
        public int mNumParameters;
        public android.database.sqlite.SQLiteConnection.PreparedStatement mPoolNext;
        public boolean mReadOnly;
        public long mSeqNum;
        public java.lang.String mSql;
        public long mStatementPtr;
        public int mType;

        PreparedStatement() {
        }
    }

    private final class PreparedStatementCache extends android.util.LruCache<java.lang.String, android.database.sqlite.SQLiteConnection.PreparedStatement> {
        private long mDatabaseSeqNum;
        private long mLastSeqNum;

        public PreparedStatementCache(int i) {
            super(i);
            this.mDatabaseSeqNum = 0L;
            this.mLastSeqNum = 0L;
        }

        public synchronized void setDatabaseSeqNum(long j) {
            this.mDatabaseSeqNum = j;
        }

        public long getLastSeqNum() {
            return this.mLastSeqNum;
        }

        public synchronized android.database.sqlite.SQLiteConnection.PreparedStatement getStatement(java.lang.String str) {
            this.mLastSeqNum = this.mDatabaseSeqNum;
            return get(str);
        }

        public synchronized long createStatement(java.lang.String str) {
            this.mLastSeqNum = this.mDatabaseSeqNum;
            return android.database.sqlite.SQLiteConnection.nativePrepareStatement(android.database.sqlite.SQLiteConnection.this.mConnectionPtr, str);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.util.LruCache
        public void entryRemoved(boolean z, java.lang.String str, android.database.sqlite.SQLiteConnection.PreparedStatement preparedStatement, android.database.sqlite.SQLiteConnection.PreparedStatement preparedStatement2) {
            preparedStatement.mInCache = false;
            if (!preparedStatement.mInUse) {
                android.database.sqlite.SQLiteConnection.this.finalizePreparedStatement(preparedStatement);
            }
        }

        public void dump(android.util.Printer printer) {
            printer.println("  Prepared statement cache:");
            java.util.Map<java.lang.String, android.database.sqlite.SQLiteConnection.PreparedStatement> snapshot = snapshot();
            if (!snapshot.isEmpty()) {
                int i = 0;
                for (java.util.Map.Entry<java.lang.String, android.database.sqlite.SQLiteConnection.PreparedStatement> entry : snapshot.entrySet()) {
                    android.database.sqlite.SQLiteConnection.PreparedStatement value = entry.getValue();
                    if (value.mInCache) {
                        printer.println("    " + i + ": statementPtr=0x" + java.lang.Long.toHexString(value.mStatementPtr) + ", numParameters=" + value.mNumParameters + ", type=" + value.mType + ", readOnly=" + value.mReadOnly + ", sql=\"" + android.database.sqlite.SQLiteConnection.trimSqlForDisplay(entry.getKey()) + "\"");
                    }
                    i++;
                }
                return;
            }
            printer.println("    <none>");
        }
    }

    private static final class OperationLog {
        private static final int COOKIE_GENERATION_SHIFT = 8;
        private static final int COOKIE_INDEX_MASK = 255;
        private static final int MAX_RECENT_OPERATIONS = 20;
        private int mGeneration;
        private int mIndex;
        private final android.database.sqlite.SQLiteConnectionPool mPool;
        private java.lang.String mResultString;
        private final android.database.sqlite.SQLiteConnection.Operation[] mOperations = new android.database.sqlite.SQLiteConnection.Operation[20];
        private long mResultLong = Long.MIN_VALUE;

        OperationLog(android.database.sqlite.SQLiteConnectionPool sQLiteConnectionPool) {
            this.mPool = sQLiteConnectionPool;
        }

        public int beginOperation(java.lang.String str, java.lang.String str2, java.lang.Object[] objArr) {
            int i;
            this.mResultLong = Long.MIN_VALUE;
            this.mResultString = null;
            synchronized (this.mOperations) {
                int i2 = (this.mIndex + 1) % 20;
                android.database.sqlite.SQLiteConnection.Operation operation = this.mOperations[i2];
                if (operation == null) {
                    operation = new android.database.sqlite.SQLiteConnection.Operation();
                    this.mOperations[i2] = operation;
                } else {
                    operation.mFinished = false;
                    operation.mException = null;
                    if (operation.mBindArgs != null) {
                        operation.mBindArgs.clear();
                    }
                }
                operation.mStartWallTime = java.lang.System.currentTimeMillis();
                operation.mStartTime = android.os.SystemClock.uptimeMillis();
                operation.mKind = str;
                operation.mSql = str2;
                operation.mPath = this.mPool.getPath();
                operation.mResultLong = Long.MIN_VALUE;
                operation.mResultString = null;
                if (objArr != null) {
                    if (operation.mBindArgs == null) {
                        operation.mBindArgs = new java.util.ArrayList<>();
                    } else {
                        operation.mBindArgs.clear();
                    }
                    for (java.lang.Object obj : objArr) {
                        if (obj != null && (obj instanceof byte[])) {
                            operation.mBindArgs.add(android.database.sqlite.SQLiteConnection.EMPTY_BYTE_ARRAY);
                        } else {
                            operation.mBindArgs.add(obj);
                        }
                    }
                }
                operation.mCookie = newOperationCookieLocked(i2);
                if (android.os.Trace.isTagEnabled(1048576L)) {
                    android.os.Trace.asyncTraceBegin(1048576L, operation.getTraceMethodName(), operation.mCookie);
                }
                this.mIndex = i2;
                i = operation.mCookie;
            }
            return i;
        }

        public void failOperation(int i, java.lang.Exception exc) {
            synchronized (this.mOperations) {
                android.database.sqlite.SQLiteConnection.Operation operationLocked = getOperationLocked(i);
                if (operationLocked != null) {
                    operationLocked.mException = exc;
                }
            }
        }

        public void endOperation(int i) {
            synchronized (this.mOperations) {
                if (endOperationDeferLogLocked(i)) {
                    logOperationLocked(i, null);
                }
            }
        }

        public boolean endOperationDeferLog(int i) {
            boolean endOperationDeferLogLocked;
            synchronized (this.mOperations) {
                endOperationDeferLogLocked = endOperationDeferLogLocked(i);
            }
            return endOperationDeferLogLocked;
        }

        public void logOperation(int i, java.lang.String str) {
            synchronized (this.mOperations) {
                logOperationLocked(i, str);
            }
        }

        public void setResult(long j) {
            this.mResultLong = j;
        }

        public void setResult(java.lang.String str) {
            this.mResultString = str;
        }

        private boolean endOperationDeferLogLocked(int i) {
            android.database.sqlite.SQLiteConnection.Operation operationLocked = getOperationLocked(i);
            if (operationLocked == null) {
                return false;
            }
            if (android.os.Trace.isTagEnabled(1048576L)) {
                android.os.Trace.asyncTraceEnd(1048576L, operationLocked.getTraceMethodName(), operationLocked.mCookie);
            }
            operationLocked.mEndTime = android.os.SystemClock.uptimeMillis();
            operationLocked.mFinished = true;
            long j = operationLocked.mEndTime - operationLocked.mStartTime;
            this.mPool.onStatementExecuted(j);
            if (!android.database.sqlite.SQLiteDebug.NoPreloadHolder.DEBUG_LOG_SLOW_QUERIES || !android.database.sqlite.SQLiteDebug.shouldLogSlowQuery(j)) {
                return false;
            }
            return true;
        }

        private void logOperationLocked(int i, java.lang.String str) {
            android.database.sqlite.SQLiteConnection.Operation operationLocked = getOperationLocked(i);
            operationLocked.mResultLong = this.mResultLong;
            operationLocked.mResultString = this.mResultString;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            operationLocked.describe(sb, true);
            if (str != null) {
                sb.append(", ").append(str);
            }
            android.util.Log.d(android.database.sqlite.SQLiteConnection.TAG, sb.toString());
        }

        private int newOperationCookieLocked(int i) {
            int i2 = this.mGeneration;
            this.mGeneration = i2 + 1;
            return i | (i2 << 8);
        }

        private android.database.sqlite.SQLiteConnection.Operation getOperationLocked(int i) {
            android.database.sqlite.SQLiteConnection.Operation operation = this.mOperations[i & 255];
            if (operation.mCookie == i) {
                return operation;
            }
            return null;
        }

        public java.lang.String describeCurrentOperation() {
            synchronized (this.mOperations) {
                android.database.sqlite.SQLiteConnection.Operation operation = this.mOperations[this.mIndex];
                if (operation == null || operation.mFinished) {
                    return null;
                }
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                operation.describe(sb, false);
                return sb.toString();
            }
        }

        public void dump(android.util.Printer printer) {
            synchronized (this.mOperations) {
                printer.println("  Most recently executed operations:");
                int i = this.mIndex;
                android.database.sqlite.SQLiteConnection.Operation operation = this.mOperations[i];
                if (operation != null) {
                    java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    int i2 = 0;
                    do {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        sb.append("    ").append(i2).append(": [");
                        sb.append(simpleDateFormat.format(new java.util.Date(operation.mStartWallTime)));
                        sb.append("] ");
                        operation.describe(sb, false);
                        printer.println(sb.toString());
                        if (i > 0) {
                            i--;
                        } else {
                            i = 19;
                        }
                        i2++;
                        operation = this.mOperations[i];
                        if (operation == null) {
                            break;
                        }
                    } while (i2 < 20);
                } else {
                    printer.println("    <none>");
                }
            }
        }
    }

    private static final class Operation {
        private static final int MAX_TRACE_METHOD_NAME_LEN = 256;
        public java.util.ArrayList<java.lang.Object> mBindArgs;
        public int mCookie;
        public long mEndTime;
        public java.lang.Exception mException;
        public boolean mFinished;
        public java.lang.String mKind;
        public java.lang.String mPath;
        public long mResultLong;
        public java.lang.String mResultString;
        public java.lang.String mSql;
        public long mStartTime;
        public long mStartWallTime;

        private Operation() {
        }

        public void describe(java.lang.StringBuilder sb, boolean z) {
            sb.append(this.mKind);
            if (this.mFinished) {
                sb.append(" took ").append(this.mEndTime - this.mStartTime).append("ms");
            } else {
                sb.append(" started ").append(java.lang.System.currentTimeMillis() - this.mStartWallTime).append("ms ago");
            }
            sb.append(" - ").append(getStatus());
            if (this.mSql != null) {
                sb.append(", sql=\"").append(android.database.sqlite.SQLiteConnection.trimSqlForDisplay(this.mSql)).append("\"");
            }
            if (z && android.database.sqlite.SQLiteDebug.NoPreloadHolder.DEBUG_LOG_DETAILED && this.mBindArgs != null && this.mBindArgs.size() != 0) {
                sb.append(", bindArgs=[");
                int size = this.mBindArgs.size();
                for (int i = 0; i < size; i++) {
                    java.lang.Object obj = this.mBindArgs.get(i);
                    if (i != 0) {
                        sb.append(", ");
                    }
                    if (obj == null) {
                        sb.append("null");
                    } else if (obj instanceof byte[]) {
                        sb.append("<byte[]>");
                    } else if (obj instanceof java.lang.String) {
                        sb.append("\"").append((java.lang.String) obj).append("\"");
                    } else {
                        sb.append(obj);
                    }
                }
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            }
            sb.append(", path=").append(this.mPath);
            if (this.mException != null) {
                sb.append(", exception=\"").append(this.mException.getMessage()).append("\"");
            }
            if (this.mResultLong != Long.MIN_VALUE) {
                sb.append(", result=").append(this.mResultLong);
            }
            if (this.mResultString != null) {
                sb.append(", result=\"").append(this.mResultString).append("\"");
            }
        }

        private java.lang.String getStatus() {
            if (this.mFinished) {
                return this.mException != null ? "failed" : "succeeded";
            }
            return "running";
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String getTraceMethodName() {
            java.lang.String str = this.mKind + " " + this.mSql;
            if (str.length() > 256) {
                return str.substring(0, 256);
            }
            return str;
        }
    }

    long getLastInsertRowId() {
        try {
            return nativeLastInsertRowId(this.mConnectionPtr);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    long getLastChangedRowCount() {
        try {
            return nativeChanges(this.mConnectionPtr);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    long getTotalChangedRowCount() {
        try {
            return nativeTotalChanges(this.mConnectionPtr);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }
}
