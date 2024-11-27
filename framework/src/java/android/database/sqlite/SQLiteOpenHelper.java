package android.database.sqlite;

/* loaded from: classes.dex */
public abstract class SQLiteOpenHelper implements java.lang.AutoCloseable {
    private static final java.lang.String TAG = android.database.sqlite.SQLiteOpenHelper.class.getSimpleName();
    private final android.content.Context mContext;
    private android.database.sqlite.SQLiteDatabase mDatabase;
    private boolean mIsInitializing;
    private final int mMinimumSupportedVersion;
    private final java.lang.String mName;
    private final int mNewVersion;
    private android.database.sqlite.SQLiteDatabase.OpenParams.Builder mOpenParamsBuilder;

    public abstract void onCreate(android.database.sqlite.SQLiteDatabase sQLiteDatabase);

    public abstract void onUpgrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2);

    public SQLiteOpenHelper(android.content.Context context, java.lang.String str, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, int i) {
        this(context, str, cursorFactory, i, (android.database.DatabaseErrorHandler) null);
    }

    public SQLiteOpenHelper(android.content.Context context, java.lang.String str, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, int i, android.database.DatabaseErrorHandler databaseErrorHandler) {
        this(context, str, cursorFactory, i, 0, databaseErrorHandler);
    }

    public SQLiteOpenHelper(android.content.Context context, java.lang.String str, int i, android.database.sqlite.SQLiteDatabase.OpenParams openParams) {
        this(context, str, i, 0, openParams.toBuilder());
    }

    public SQLiteOpenHelper(android.content.Context context, java.lang.String str, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, int i, int i2, android.database.DatabaseErrorHandler databaseErrorHandler) {
        this(context, str, i, i2, new android.database.sqlite.SQLiteDatabase.OpenParams.Builder());
        this.mOpenParamsBuilder.setCursorFactory(cursorFactory);
        this.mOpenParamsBuilder.setErrorHandler(databaseErrorHandler);
    }

    private SQLiteOpenHelper(android.content.Context context, java.lang.String str, int i, int i2, android.database.sqlite.SQLiteDatabase.OpenParams.Builder builder) {
        java.util.Objects.requireNonNull(builder);
        if (i < 1) {
            throw new java.lang.IllegalArgumentException("Version must be >= 1, was " + i);
        }
        this.mContext = context;
        this.mName = str;
        this.mNewVersion = i;
        this.mMinimumSupportedVersion = java.lang.Math.max(0, i2);
        setOpenParamsBuilder(builder);
    }

    public java.lang.String getDatabaseName() {
        return this.mName;
    }

    public void setWriteAheadLoggingEnabled(boolean z) {
        synchronized (this) {
            if (this.mOpenParamsBuilder.isWriteAheadLoggingEnabled() != z) {
                if (this.mDatabase != null && this.mDatabase.isOpen() && !this.mDatabase.isReadOnly()) {
                    if (z) {
                        this.mDatabase.enableWriteAheadLogging();
                    } else {
                        this.mDatabase.disableWriteAheadLogging();
                    }
                }
                this.mOpenParamsBuilder.setWriteAheadLoggingEnabled(z);
            }
            this.mOpenParamsBuilder.removeOpenFlags(Integer.MIN_VALUE);
        }
    }

    public void setLookasideConfig(int i, int i2) {
        synchronized (this) {
            if (this.mDatabase != null && this.mDatabase.isOpen()) {
                throw new java.lang.IllegalStateException("Lookaside memory config cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.setLookasideConfig(i, i2);
        }
    }

    public void setOpenParams(android.database.sqlite.SQLiteDatabase.OpenParams openParams) {
        java.util.Objects.requireNonNull(openParams);
        synchronized (this) {
            if (this.mDatabase != null && this.mDatabase.isOpen()) {
                throw new java.lang.IllegalStateException("OpenParams cannot be set after opening the database");
            }
            setOpenParamsBuilder(new android.database.sqlite.SQLiteDatabase.OpenParams.Builder(openParams));
        }
    }

    private void setOpenParamsBuilder(android.database.sqlite.SQLiteDatabase.OpenParams.Builder builder) {
        this.mOpenParamsBuilder = builder;
        this.mOpenParamsBuilder.addOpenFlags(268435456);
    }

    @java.lang.Deprecated
    public void setIdleConnectionTimeout(long j) {
        synchronized (this) {
            if (this.mDatabase != null && this.mDatabase.isOpen()) {
                throw new java.lang.IllegalStateException("Connection timeout setting cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.setIdleConnectionTimeout(j);
        }
    }

    public android.database.sqlite.SQLiteDatabase getWritableDatabase() {
        android.database.sqlite.SQLiteDatabase databaseLocked;
        synchronized (this) {
            databaseLocked = getDatabaseLocked(true);
        }
        return databaseLocked;
    }

    public android.database.sqlite.SQLiteDatabase getReadableDatabase() {
        android.database.sqlite.SQLiteDatabase databaseLocked;
        synchronized (this) {
            databaseLocked = getDatabaseLocked(false);
        }
        return databaseLocked;
    }

    private android.database.sqlite.SQLiteDatabase getDatabaseLocked(boolean z) {
        if (this.mDatabase != null) {
            if (!this.mDatabase.isOpen()) {
                this.mDatabase = null;
            } else if (!z || !this.mDatabase.isReadOnly()) {
                return this.mDatabase;
            }
        }
        if (this.mIsInitializing) {
            throw new java.lang.IllegalStateException("getDatabase called recursively");
        }
        android.database.sqlite.SQLiteDatabase sQLiteDatabase = this.mDatabase;
        try {
            this.mIsInitializing = true;
            if (sQLiteDatabase != null) {
                if (z && sQLiteDatabase.isReadOnly()) {
                    sQLiteDatabase.reopenReadWrite();
                }
            } else if (this.mName == null) {
                sQLiteDatabase = android.database.sqlite.SQLiteDatabase.createInMemory(this.mOpenParamsBuilder.build());
            } else {
                java.io.File databasePath = this.mContext.getDatabasePath(this.mName);
                android.database.sqlite.SQLiteDatabase.OpenParams build = this.mOpenParamsBuilder.build();
                try {
                    sQLiteDatabase = android.database.sqlite.SQLiteDatabase.openDatabase(databasePath, build);
                    setFilePermissionsForDb(databasePath.getPath());
                } catch (android.database.SQLException e) {
                    if (z) {
                        throw e;
                    }
                    android.util.Log.e(TAG, "Couldn't open database for writing (will try read-only):", e);
                    sQLiteDatabase = android.database.sqlite.SQLiteDatabase.openDatabase(databasePath, build.toBuilder().addOpenFlags(1).build());
                }
            }
            onConfigure(sQLiteDatabase);
            int version = sQLiteDatabase.getVersion();
            if (version != this.mNewVersion) {
                if (sQLiteDatabase.isReadOnly()) {
                    throw new android.database.sqlite.SQLiteException("Can't upgrade read-only database from version " + sQLiteDatabase.getVersion() + " to " + this.mNewVersion + ": " + this.mName);
                }
                if (version > 0 && version < this.mMinimumSupportedVersion) {
                    java.io.File file = new java.io.File(sQLiteDatabase.getPath());
                    onBeforeDelete(sQLiteDatabase);
                    sQLiteDatabase.close();
                    if (android.database.sqlite.SQLiteDatabase.deleteDatabase(file)) {
                        this.mIsInitializing = false;
                        return getDatabaseLocked(z);
                    }
                    throw new java.lang.IllegalStateException("Unable to delete obsolete database " + this.mName + " with version " + version);
                }
                sQLiteDatabase.beginTransaction();
                try {
                    if (version == 0) {
                        onCreate(sQLiteDatabase);
                    } else if (version > this.mNewVersion) {
                        onDowngrade(sQLiteDatabase, version, this.mNewVersion);
                    } else {
                        onUpgrade(sQLiteDatabase, version, this.mNewVersion);
                    }
                    sQLiteDatabase.setVersion(this.mNewVersion);
                    sQLiteDatabase.setTransactionSuccessful();
                    sQLiteDatabase.endTransaction();
                } catch (java.lang.Throwable th) {
                    sQLiteDatabase.endTransaction();
                    throw th;
                }
            }
            onOpen(sQLiteDatabase);
            this.mDatabase = sQLiteDatabase;
            this.mIsInitializing = false;
            if (sQLiteDatabase != null && sQLiteDatabase != this.mDatabase) {
                sQLiteDatabase.close();
            }
            return sQLiteDatabase;
        } finally {
            this.mIsInitializing = false;
            if (sQLiteDatabase != null && sQLiteDatabase != this.mDatabase) {
                sQLiteDatabase.close();
            }
        }
    }

    private static void setFilePermissionsForDb(java.lang.String str) {
        android.os.FileUtils.setPermissions(str, 432, -1, -1);
    }

    @Override // java.lang.AutoCloseable
    public synchronized void close() {
        if (this.mIsInitializing) {
            throw new java.lang.IllegalStateException("Closed during initialization");
        }
        if (this.mDatabase != null && this.mDatabase.isOpen()) {
            this.mDatabase.close();
            this.mDatabase = null;
        }
    }

    public void onConfigure(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
    }

    public void onBeforeDelete(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
    }

    public void onDowngrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
        throw new android.database.sqlite.SQLiteException("Can't downgrade database from version " + i + " to " + i2);
    }

    public void onOpen(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
    }
}
