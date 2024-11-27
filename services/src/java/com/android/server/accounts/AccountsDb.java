package com.android.server.accounts;

/* loaded from: classes.dex */
class AccountsDb implements java.lang.AutoCloseable {
    private static final java.lang.String ACCOUNTS_ID = "_id";
    private static final java.lang.String ACCOUNTS_LAST_AUTHENTICATE_TIME_EPOCH_MILLIS = "last_password_entry_time_millis_epoch";
    private static final java.lang.String ACCOUNTS_NAME = "name";
    private static final java.lang.String ACCOUNTS_PASSWORD = "password";
    private static final java.lang.String ACCOUNTS_PREVIOUS_NAME = "previous_name";
    private static final java.lang.String ACCOUNTS_TYPE = "type";
    private static final java.lang.String ACCOUNT_ACCESS_GRANTS = "SELECT name, uid FROM accounts, grants WHERE accounts_id=_id";
    private static final java.lang.String AUTHTOKENS_ACCOUNTS_ID = "accounts_id";
    private static final java.lang.String AUTHTOKENS_ID = "_id";
    private static final java.lang.String AUTHTOKENS_TYPE = "type";
    static final java.lang.String CE_DATABASE_NAME = "accounts_ce.db";
    private static final int CE_DATABASE_VERSION = 10;
    private static final java.lang.String CE_DB_PREFIX = "ceDb.";
    private static final java.lang.String CE_TABLE_ACCOUNTS = "ceDb.accounts";
    private static final java.lang.String CE_TABLE_AUTHTOKENS = "ceDb.authtokens";
    private static final java.lang.String CE_TABLE_EXTRAS = "ceDb.extras";
    private static final java.lang.String COUNT_OF_MATCHING_GRANTS = "SELECT COUNT(*) FROM grants, accounts WHERE accounts_id=_id AND uid=? AND auth_token_type=? AND name=? AND type=?";
    private static final java.lang.String COUNT_OF_MATCHING_GRANTS_ANY_TOKEN = "SELECT COUNT(*) FROM grants, accounts WHERE accounts_id=_id AND uid=? AND name=? AND type=?";
    private static final java.lang.String DATABASE_NAME = "accounts.db";
    static final java.lang.String DE_DATABASE_NAME = "accounts_de.db";
    private static final int DE_DATABASE_VERSION = 3;
    private static final java.lang.String EXTRAS_ACCOUNTS_ID = "accounts_id";
    private static final java.lang.String EXTRAS_ID = "_id";
    private static final java.lang.String EXTRAS_KEY = "key";
    private static final java.lang.String EXTRAS_VALUE = "value";
    private static final java.lang.String GRANTS_ACCOUNTS_ID = "accounts_id";
    private static final java.lang.String GRANTS_AUTH_TOKEN_TYPE = "auth_token_type";
    private static final java.lang.String GRANTS_GRANTEE_UID = "uid";
    static final int MAX_DEBUG_DB_SIZE = 64;
    private static final java.lang.String META_KEY = "key";
    private static final java.lang.String META_KEY_DELIMITER = ":";
    private static final java.lang.String META_KEY_FOR_AUTHENTICATOR_UID_FOR_TYPE_PREFIX = "auth_uid_for_type:";
    private static final java.lang.String META_VALUE = "value";
    private static final int PRE_N_DATABASE_VERSION = 9;
    private static final java.lang.String SELECTION_ACCOUNTS_ID_BY_ACCOUNT = "accounts_id=(select _id FROM accounts WHERE name=? AND type=?)";
    private static final java.lang.String SELECTION_META_BY_AUTHENTICATOR_TYPE = "key LIKE ?";
    private static final java.lang.String SHARED_ACCOUNTS_ID = "_id";
    static final java.lang.String TABLE_ACCOUNTS = "accounts";
    private static final java.lang.String TABLE_AUTHTOKENS = "authtokens";
    private static final java.lang.String TABLE_EXTRAS = "extras";
    private static final java.lang.String TABLE_GRANTS = "grants";
    private static final java.lang.String TABLE_META = "meta";
    static final java.lang.String TABLE_SHARED_ACCOUNTS = "shared_accounts";
    private static final java.lang.String TABLE_VISIBILITY = "visibility";
    private static final java.lang.String TAG = "AccountsDb";
    private static final java.lang.String VISIBILITY_ACCOUNTS_ID = "accounts_id";
    private static final java.lang.String VISIBILITY_PACKAGE = "_package";
    private static final java.lang.String VISIBILITY_VALUE = "value";
    private final android.content.Context mContext;
    private final com.android.server.accounts.AccountsDb.DeDatabaseHelper mDeDatabase;
    private volatile android.database.sqlite.SQLiteStatement mDebugStatementForLogging;
    private final java.io.File mPreNDatabaseFile;
    private static java.lang.String TABLE_DEBUG = "debug_table";
    private static java.lang.String DEBUG_TABLE_ACTION_TYPE = "action_type";
    private static java.lang.String DEBUG_TABLE_TIMESTAMP = "time";
    private static java.lang.String DEBUG_TABLE_CALLER_UID = "caller_uid";
    private static java.lang.String DEBUG_TABLE_TABLE_NAME = "table_name";
    private static java.lang.String DEBUG_TABLE_KEY = "primary_key";
    static java.lang.String DEBUG_ACTION_SET_PASSWORD = "action_set_password";
    static java.lang.String DEBUG_ACTION_CLEAR_PASSWORD = "action_clear_password";
    static java.lang.String DEBUG_ACTION_ACCOUNT_ADD = "action_account_add";
    static java.lang.String DEBUG_ACTION_ACCOUNT_REMOVE = "action_account_remove";
    static java.lang.String DEBUG_ACTION_ACCOUNT_REMOVE_DE = "action_account_remove_de";
    static java.lang.String DEBUG_ACTION_AUTHENTICATOR_REMOVE = "action_authenticator_remove";
    static java.lang.String DEBUG_ACTION_ACCOUNT_RENAME = "action_account_rename";
    static java.lang.String DEBUG_ACTION_CALLED_ACCOUNT_ADD = "action_called_account_add";
    static java.lang.String DEBUG_ACTION_CALLED_ACCOUNT_REMOVE = "action_called_account_remove";
    static java.lang.String DEBUG_ACTION_SYNC_DE_CE_ACCOUNTS = "action_sync_de_ce_accounts";
    static java.lang.String DEBUG_ACTION_CALLED_START_ACCOUNT_ADD = "action_called_start_account_add";
    static java.lang.String DEBUG_ACTION_CALLED_ACCOUNT_SESSION_FINISH = "action_called_account_session_finish";
    private static final java.lang.String ACCOUNTS_TYPE_COUNT = "count(type)";
    private static final java.lang.String[] ACCOUNT_TYPE_COUNT_PROJECTION = {com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, ACCOUNTS_TYPE_COUNT};
    private static final java.lang.String AUTHTOKENS_AUTHTOKEN = "authtoken";
    private static final java.lang.String[] COLUMNS_AUTHTOKENS_TYPE_AND_AUTHTOKEN = {com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, AUTHTOKENS_AUTHTOKEN};
    private static final java.lang.String[] COLUMNS_EXTRAS_KEY_AND_VALUE = {"key", "value"};
    final java.lang.Object mDebugStatementLock = new java.lang.Object();
    private volatile long mDebugDbInsertionPoint = -1;

    AccountsDb(com.android.server.accounts.AccountsDb.DeDatabaseHelper deDatabaseHelper, android.content.Context context, java.io.File file) {
        this.mDeDatabase = deDatabaseHelper;
        this.mContext = context;
        this.mPreNDatabaseFile = file;
    }

    private static class CeDatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {
        CeDatabaseHelper(android.content.Context context, java.lang.String str) {
            super(context, str, (android.database.sqlite.SQLiteDatabase.CursorFactory) null, 10);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            android.util.Log.i(com.android.server.accounts.AccountsDb.TAG, "Creating CE database " + getDatabaseName());
            sQLiteDatabase.execSQL("CREATE TABLE accounts ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, type TEXT NOT NULL, password TEXT, UNIQUE(name,type))");
            sQLiteDatabase.execSQL("CREATE TABLE authtokens (  _id INTEGER PRIMARY KEY AUTOINCREMENT,  accounts_id INTEGER NOT NULL, type TEXT NOT NULL,  authtoken TEXT,  UNIQUE (accounts_id,type))");
            sQLiteDatabase.execSQL("CREATE TABLE extras ( _id INTEGER PRIMARY KEY AUTOINCREMENT, accounts_id INTEGER, key TEXT NOT NULL, value TEXT, UNIQUE(accounts_id,key))");
            createAccountsDeletionTrigger(sQLiteDatabase);
        }

        private void createAccountsDeletionTrigger(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(" CREATE TRIGGER accountsDelete DELETE ON accounts BEGIN   DELETE FROM authtokens     WHERE accounts_id=OLD._id ;   DELETE FROM extras     WHERE accounts_id=OLD._id ; END");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
            android.util.Log.i(com.android.server.accounts.AccountsDb.TAG, "Upgrade CE from version " + i + " to version " + i2);
            if (i == 9) {
                if (android.util.Log.isLoggable(com.android.server.accounts.AccountsDb.TAG, 2)) {
                    android.util.Log.v(com.android.server.accounts.AccountsDb.TAG, "onUpgrade upgrading to v10");
                }
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS meta");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS shared_accounts");
                sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS accountsDelete");
                createAccountsDeletionTrigger(sQLiteDatabase);
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS grants");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + com.android.server.accounts.AccountsDb.TABLE_DEBUG);
                i++;
            }
            if (i != i2) {
                android.util.Log.e(com.android.server.accounts.AccountsDb.TAG, "failed to upgrade version " + i + " to version " + i2);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onDowngrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
            android.util.Log.e(com.android.server.accounts.AccountsDb.TAG, "onDowngrade: recreate accounts CE table");
            com.android.server.accounts.AccountsDb.resetDatabase(sQLiteDatabase);
            onCreate(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onOpen(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            if (android.util.Log.isLoggable(com.android.server.accounts.AccountsDb.TAG, 2)) {
                android.util.Log.v(com.android.server.accounts.AccountsDb.TAG, "opened database accounts_ce.db");
            }
        }

        static com.android.server.accounts.AccountsDb.CeDatabaseHelper create(android.content.Context context, java.io.File file, java.io.File file2) {
            boolean z;
            boolean exists = file2.exists();
            if (android.util.Log.isLoggable(com.android.server.accounts.AccountsDb.TAG, 2)) {
                android.util.Log.v(com.android.server.accounts.AccountsDb.TAG, "CeDatabaseHelper.create ceDatabaseFile=" + file2 + " oldDbExists=" + file.exists() + " newDbExists=" + exists);
            }
            if (!exists && file.exists()) {
                z = migratePreNDbToCe(file, file2);
            } else {
                z = false;
            }
            com.android.server.accounts.AccountsDb.CeDatabaseHelper ceDatabaseHelper = new com.android.server.accounts.AccountsDb.CeDatabaseHelper(context, file2.getPath());
            ceDatabaseHelper.getWritableDatabase();
            ceDatabaseHelper.close();
            if (z) {
                android.util.Slog.i(com.android.server.accounts.AccountsDb.TAG, "Migration complete - removing pre-N db " + file);
                if (!android.database.sqlite.SQLiteDatabase.deleteDatabase(file)) {
                    android.util.Slog.e(com.android.server.accounts.AccountsDb.TAG, "Cannot remove pre-N db " + file);
                }
            }
            return ceDatabaseHelper;
        }

        private static boolean migratePreNDbToCe(java.io.File file, java.io.File file2) {
            android.util.Slog.i(com.android.server.accounts.AccountsDb.TAG, "Moving pre-N DB " + file + " to CE " + file2);
            try {
                android.os.FileUtils.copyFileOrThrow(file, file2);
                return true;
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.accounts.AccountsDb.TAG, "Cannot copy file to " + file2 + " from " + file, e);
                com.android.server.accounts.AccountsDb.deleteDbFileWarnIfFailed(file2);
                return false;
            }
        }
    }

    android.database.Cursor findAuthtokenForAllAccounts(java.lang.String str, java.lang.String str2) {
        return this.mDeDatabase.getReadableDatabaseUserIsUnlocked().rawQuery("SELECT ceDb.authtokens._id, ceDb.accounts.name, ceDb.authtokens.type FROM ceDb.accounts JOIN ceDb.authtokens ON ceDb.accounts._id = ceDb.authtokens.accounts_id WHERE ceDb.authtokens.authtoken = ? AND ceDb.accounts.type = ?", new java.lang.String[]{str2, str});
    }

    java.util.Map<java.lang.String, java.lang.String> findAuthTokensByAccount(android.accounts.Account account) {
        android.database.sqlite.SQLiteDatabase readableDatabaseUserIsUnlocked = this.mDeDatabase.getReadableDatabaseUserIsUnlocked();
        java.util.HashMap hashMap = new java.util.HashMap();
        android.database.Cursor query = readableDatabaseUserIsUnlocked.query(CE_TABLE_AUTHTOKENS, COLUMNS_AUTHTOKENS_TYPE_AND_AUTHTOKEN, SELECTION_ACCOUNTS_ID_BY_ACCOUNT, new java.lang.String[]{account.name, account.type}, null, null, null);
        while (query.moveToNext()) {
            try {
                hashMap.put(query.getString(0), query.getString(1));
            } finally {
                query.close();
            }
        }
        return hashMap;
    }

    boolean deleteAuthtokensByAccountIdAndType(long j, java.lang.String str) {
        return this.mDeDatabase.getWritableDatabaseUserIsUnlocked().delete(CE_TABLE_AUTHTOKENS, "accounts_id=? AND type=?", new java.lang.String[]{java.lang.String.valueOf(j), str}) > 0;
    }

    boolean deleteAuthToken(java.lang.String str) {
        return this.mDeDatabase.getWritableDatabaseUserIsUnlocked().delete(CE_TABLE_AUTHTOKENS, "_id= ?", new java.lang.String[]{str}) > 0;
    }

    long insertAuthToken(long j, java.lang.String str, java.lang.String str2) {
        android.database.sqlite.SQLiteDatabase writableDatabaseUserIsUnlocked = this.mDeDatabase.getWritableDatabaseUserIsUnlocked();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("accounts_id", java.lang.Long.valueOf(j));
        contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, str);
        contentValues.put(AUTHTOKENS_AUTHTOKEN, str2);
        return writableDatabaseUserIsUnlocked.insert(CE_TABLE_AUTHTOKENS, AUTHTOKENS_AUTHTOKEN, contentValues);
    }

    int updateCeAccountPassword(long j, java.lang.String str) {
        android.database.sqlite.SQLiteDatabase writableDatabaseUserIsUnlocked = this.mDeDatabase.getWritableDatabaseUserIsUnlocked();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("password", str);
        return writableDatabaseUserIsUnlocked.update(CE_TABLE_ACCOUNTS, contentValues, "_id=?", new java.lang.String[]{java.lang.String.valueOf(j)});
    }

    boolean renameCeAccount(long j, java.lang.String str) {
        android.database.sqlite.SQLiteDatabase writableDatabaseUserIsUnlocked = this.mDeDatabase.getWritableDatabaseUserIsUnlocked();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("name", str);
        return writableDatabaseUserIsUnlocked.update(CE_TABLE_ACCOUNTS, contentValues, "_id=?", new java.lang.String[]{java.lang.String.valueOf(j)}) > 0;
    }

    boolean deleteAuthTokensByAccountId(long j) {
        return this.mDeDatabase.getWritableDatabaseUserIsUnlocked().delete(CE_TABLE_AUTHTOKENS, "accounts_id=?", new java.lang.String[]{java.lang.String.valueOf(j)}) > 0;
    }

    long findExtrasIdByAccountId(long j, java.lang.String str) {
        android.database.Cursor query = this.mDeDatabase.getReadableDatabaseUserIsUnlocked().query(CE_TABLE_EXTRAS, new java.lang.String[]{"_id"}, "accounts_id=" + j + " AND key=?", new java.lang.String[]{str}, null, null, null);
        try {
            if (query.moveToNext()) {
                return query.getLong(0);
            }
            query.close();
            return -1L;
        } finally {
            query.close();
        }
    }

    boolean updateExtra(long j, java.lang.String str) {
        android.database.sqlite.SQLiteDatabase writableDatabaseUserIsUnlocked = this.mDeDatabase.getWritableDatabaseUserIsUnlocked();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("value", str);
        return writableDatabaseUserIsUnlocked.update(TABLE_EXTRAS, contentValues, "_id=?", new java.lang.String[]{java.lang.String.valueOf(j)}) == 1;
    }

    long insertExtra(long j, java.lang.String str, java.lang.String str2) {
        android.database.sqlite.SQLiteDatabase writableDatabaseUserIsUnlocked = this.mDeDatabase.getWritableDatabaseUserIsUnlocked();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("key", str);
        contentValues.put("accounts_id", java.lang.Long.valueOf(j));
        contentValues.put("value", str2);
        return writableDatabaseUserIsUnlocked.insert(CE_TABLE_EXTRAS, "key", contentValues);
    }

    java.util.Map<java.lang.String, java.lang.String> findUserExtrasForAccount(android.accounts.Account account) {
        android.database.sqlite.SQLiteDatabase readableDatabaseUserIsUnlocked = this.mDeDatabase.getReadableDatabaseUserIsUnlocked();
        java.util.HashMap hashMap = new java.util.HashMap();
        android.database.Cursor query = readableDatabaseUserIsUnlocked.query(CE_TABLE_EXTRAS, COLUMNS_EXTRAS_KEY_AND_VALUE, SELECTION_ACCOUNTS_ID_BY_ACCOUNT, new java.lang.String[]{account.name, account.type}, null, null, null);
        while (query.moveToNext()) {
            try {
                hashMap.put(query.getString(0), query.getString(1));
            } catch (java.lang.Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        query.close();
        return hashMap;
    }

    long findCeAccountId(android.accounts.Account account) {
        android.database.Cursor query = this.mDeDatabase.getReadableDatabaseUserIsUnlocked().query(CE_TABLE_ACCOUNTS, new java.lang.String[]{"_id"}, "name=? AND type=?", new java.lang.String[]{account.name, account.type}, null, null, null);
        try {
            if (query.moveToNext()) {
                long j = query.getLong(0);
                query.close();
                return j;
            }
            query.close();
            return -1L;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    java.lang.String findAccountPasswordByNameAndType(java.lang.String str, java.lang.String str2) {
        android.database.Cursor query = this.mDeDatabase.getReadableDatabaseUserIsUnlocked().query(CE_TABLE_ACCOUNTS, new java.lang.String[]{"password"}, "name=? AND type=?", new java.lang.String[]{str, str2}, null, null, null);
        try {
            if (query.moveToNext()) {
                java.lang.String string = query.getString(0);
                query.close();
                return string;
            }
            query.close();
            return null;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    long insertCeAccount(android.accounts.Account account, java.lang.String str) {
        android.database.sqlite.SQLiteDatabase writableDatabaseUserIsUnlocked = this.mDeDatabase.getWritableDatabaseUserIsUnlocked();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("name", account.name);
        contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, account.type);
        contentValues.put("password", str);
        return writableDatabaseUserIsUnlocked.insert(CE_TABLE_ACCOUNTS, "name", contentValues);
    }

    static class DeDatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {
        private volatile boolean mCeAttached;
        private final int mUserId;

        private DeDatabaseHelper(android.content.Context context, int i, java.lang.String str) {
            super(context, str, (android.database.sqlite.SQLiteDatabase.CursorFactory) null, 3);
            this.mUserId = i;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            android.util.Log.i(com.android.server.accounts.AccountsDb.TAG, "Creating DE database for user " + this.mUserId);
            sQLiteDatabase.execSQL("CREATE TABLE accounts ( _id INTEGER PRIMARY KEY, name TEXT NOT NULL, type TEXT NOT NULL, previous_name TEXT, last_password_entry_time_millis_epoch INTEGER DEFAULT 0, UNIQUE(name,type))");
            sQLiteDatabase.execSQL("CREATE TABLE meta ( key TEXT PRIMARY KEY NOT NULL, value TEXT)");
            createGrantsTable(sQLiteDatabase);
            createSharedAccountsTable(sQLiteDatabase);
            createAccountsDeletionTrigger(sQLiteDatabase);
            createDebugTable(sQLiteDatabase);
            createAccountsVisibilityTable(sQLiteDatabase);
            createAccountsDeletionVisibilityCleanupTrigger(sQLiteDatabase);
        }

        private void createSharedAccountsTable(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE shared_accounts ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, type TEXT NOT NULL, UNIQUE(name,type))");
        }

        private void createAccountsDeletionTrigger(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(" CREATE TRIGGER accountsDelete DELETE ON accounts BEGIN   DELETE FROM grants     WHERE accounts_id=OLD._id ; END");
        }

        private void createGrantsTable(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE grants (  accounts_id INTEGER NOT NULL, auth_token_type STRING NOT NULL,  uid INTEGER NOT NULL,  UNIQUE (accounts_id,auth_token_type,uid))");
        }

        private void createAccountsVisibilityTable(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE visibility ( accounts_id INTEGER NOT NULL, _package TEXT NOT NULL, value INTEGER, PRIMARY KEY(accounts_id,_package))");
        }

        static void createDebugTable(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE " + com.android.server.accounts.AccountsDb.TABLE_DEBUG + " ( _id INTEGER," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_ACTION_TYPE + " TEXT NOT NULL, " + com.android.server.accounts.AccountsDb.DEBUG_TABLE_TIMESTAMP + " DATETIME," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_CALLER_UID + " INTEGER NOT NULL," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_TABLE_NAME + " TEXT NOT NULL," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_KEY + " INTEGER PRIMARY KEY)");
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("CREATE INDEX timestamp_index ON ");
            sb.append(com.android.server.accounts.AccountsDb.TABLE_DEBUG);
            sb.append(" (");
            sb.append(com.android.server.accounts.AccountsDb.DEBUG_TABLE_TIMESTAMP);
            sb.append(")");
            sQLiteDatabase.execSQL(sb.toString());
        }

        private void createAccountsDeletionVisibilityCleanupTrigger(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(" CREATE TRIGGER accountsDeleteVisibility DELETE ON accounts BEGIN   DELETE FROM visibility     WHERE accounts_id=OLD._id ; END");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
            android.util.Log.i(com.android.server.accounts.AccountsDb.TAG, "upgrade from version " + i + " to version " + i2);
            if (i == 1) {
                createAccountsVisibilityTable(sQLiteDatabase);
                createAccountsDeletionVisibilityCleanupTrigger(sQLiteDatabase);
                i = 3;
            }
            if (i == 2) {
                sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS accountsDeleteVisibility");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS visibility");
                createAccountsVisibilityTable(sQLiteDatabase);
                createAccountsDeletionVisibilityCleanupTrigger(sQLiteDatabase);
                i++;
            }
            if (i != i2) {
                android.util.Log.e(com.android.server.accounts.AccountsDb.TAG, "failed to upgrade version " + i + " to version " + i2);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onDowngrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
            android.util.Log.e(com.android.server.accounts.AccountsDb.TAG, "onDowngrade: recreate accounts DE table");
            com.android.server.accounts.AccountsDb.resetDatabase(sQLiteDatabase);
            onCreate(sQLiteDatabase);
        }

        public android.database.sqlite.SQLiteDatabase getReadableDatabaseUserIsUnlocked() {
            if (!this.mCeAttached) {
                android.util.Log.wtf(com.android.server.accounts.AccountsDb.TAG, "getReadableDatabaseUserIsUnlocked called while user " + this.mUserId + " is still locked. CE database is not yet available.", new java.lang.Throwable());
            }
            return super.getReadableDatabase();
        }

        public android.database.sqlite.SQLiteDatabase getWritableDatabaseUserIsUnlocked() {
            if (!this.mCeAttached) {
                android.util.Log.wtf(com.android.server.accounts.AccountsDb.TAG, "getWritableDatabaseUserIsUnlocked called while user " + this.mUserId + " is still locked. CE database is not yet available.", new java.lang.Throwable());
            }
            return super.getWritableDatabase();
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onOpen(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            if (android.util.Log.isLoggable(com.android.server.accounts.AccountsDb.TAG, 2)) {
                android.util.Log.v(com.android.server.accounts.AccountsDb.TAG, "opened database accounts_de.db");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void migratePreNDbToDe(java.io.File file) {
            android.util.Log.i(com.android.server.accounts.AccountsDb.TAG, "Migrate pre-N database to DE preNDbFile=" + file);
            android.database.sqlite.SQLiteDatabase writableDatabase = getWritableDatabase();
            writableDatabase.execSQL("ATTACH DATABASE '" + file.getPath() + "' AS preNDb");
            writableDatabase.beginTransaction();
            writableDatabase.execSQL("INSERT INTO accounts(_id,name,type, previous_name, last_password_entry_time_millis_epoch) SELECT _id,name,type, previous_name, last_password_entry_time_millis_epoch FROM preNDb.accounts");
            writableDatabase.execSQL("INSERT INTO shared_accounts(_id,name,type) SELECT _id,name,type FROM preNDb.shared_accounts");
            writableDatabase.execSQL("INSERT INTO " + com.android.server.accounts.AccountsDb.TABLE_DEBUG + "(_id," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_ACTION_TYPE + "," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_TIMESTAMP + "," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_CALLER_UID + "," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_TABLE_NAME + "," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_KEY + ") SELECT _id," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_ACTION_TYPE + "," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_TIMESTAMP + "," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_CALLER_UID + "," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_TABLE_NAME + "," + com.android.server.accounts.AccountsDb.DEBUG_TABLE_KEY + " FROM preNDb." + com.android.server.accounts.AccountsDb.TABLE_DEBUG);
            writableDatabase.execSQL("INSERT INTO grants(accounts_id,auth_token_type,uid) SELECT accounts_id,auth_token_type,uid FROM preNDb.grants");
            writableDatabase.execSQL("INSERT INTO meta(key,value) SELECT key,value FROM preNDb.meta");
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.execSQL("DETACH DATABASE preNDb");
        }
    }

    boolean deleteDeAccount(long j) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mDeDatabase.getWritableDatabase();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("_id=");
        sb.append(j);
        return writableDatabase.delete(TABLE_ACCOUNTS, sb.toString(), null) > 0;
    }

    long insertSharedAccount(android.accounts.Account account) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mDeDatabase.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("name", account.name);
        contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, account.type);
        return writableDatabase.insert(TABLE_SHARED_ACCOUNTS, "name", contentValues);
    }

    boolean deleteSharedAccount(android.accounts.Account account) {
        return this.mDeDatabase.getWritableDatabase().delete(TABLE_SHARED_ACCOUNTS, "name=? AND type=?", new java.lang.String[]{account.name, account.type}) > 0;
    }

    int renameSharedAccount(android.accounts.Account account, java.lang.String str) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mDeDatabase.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("name", str);
        return writableDatabase.update(TABLE_SHARED_ACCOUNTS, contentValues, "name=? AND type=?", new java.lang.String[]{account.name, account.type});
    }

    java.util.List<android.accounts.Account> getSharedAccounts() {
        android.database.sqlite.SQLiteDatabase readableDatabase = this.mDeDatabase.getReadableDatabase();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.database.Cursor cursor = null;
        try {
            cursor = readableDatabase.query(TABLE_SHARED_ACCOUNTS, new java.lang.String[]{"name", com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE}, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("name");
                int columnIndex2 = cursor.getColumnIndex(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE);
                do {
                    arrayList.add(new android.accounts.Account(cursor.getString(columnIndex), cursor.getString(columnIndex2)));
                } while (cursor.moveToNext());
            }
            return arrayList;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    long findSharedAccountId(android.accounts.Account account) {
        android.database.Cursor query = this.mDeDatabase.getReadableDatabase().query(TABLE_SHARED_ACCOUNTS, new java.lang.String[]{"_id"}, "name=? AND type=?", new java.lang.String[]{account.name, account.type}, null, null, null);
        try {
            if (query.moveToNext()) {
                return query.getLong(0);
            }
            query.close();
            return -1L;
        } finally {
            query.close();
        }
    }

    long findAccountLastAuthenticatedTime(android.accounts.Account account) {
        return android.database.DatabaseUtils.longForQuery(this.mDeDatabase.getReadableDatabase(), "SELECT last_password_entry_time_millis_epoch FROM accounts WHERE name=? AND type=?", new java.lang.String[]{account.name, account.type});
    }

    boolean updateAccountLastAuthenticatedTime(android.accounts.Account account) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mDeDatabase.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put(ACCOUNTS_LAST_AUTHENTICATE_TIME_EPOCH_MILLIS, java.lang.Long.valueOf(java.lang.System.currentTimeMillis()));
        return writableDatabase.update(TABLE_ACCOUNTS, contentValues, "name=? AND type=?", new java.lang.String[]{account.name, account.type}) > 0;
    }

    void dumpDeAccountsTable(java.io.PrintWriter printWriter) {
        android.database.Cursor query = this.mDeDatabase.getReadableDatabase().query(TABLE_ACCOUNTS, ACCOUNT_TYPE_COUNT_PROJECTION, null, null, com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, null, null);
        while (query.moveToNext()) {
            try {
                printWriter.println(query.getString(0) + "," + query.getString(1));
            } catch (java.lang.Throwable th) {
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        }
        query.close();
    }

    long findDeAccountId(android.accounts.Account account) {
        android.database.Cursor query = this.mDeDatabase.getReadableDatabase().query(TABLE_ACCOUNTS, new java.lang.String[]{"_id"}, "name=? AND type=?", new java.lang.String[]{account.name, account.type}, null, null, null);
        try {
            if (query.moveToNext()) {
                long j = query.getLong(0);
                query.close();
                return j;
            }
            query.close();
            return -1L;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    java.util.Map<java.lang.Long, android.accounts.Account> findAllDeAccounts() {
        android.database.sqlite.SQLiteDatabase readableDatabase = this.mDeDatabase.getReadableDatabase();
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        android.database.Cursor query = readableDatabase.query(TABLE_ACCOUNTS, new java.lang.String[]{"_id", com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, "name"}, null, null, null, null, "_id");
        while (query.moveToNext()) {
            try {
                long j = query.getLong(0);
                linkedHashMap.put(java.lang.Long.valueOf(j), new android.accounts.Account(query.getString(2), query.getString(1)));
            } catch (java.lang.Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        query.close();
        return linkedHashMap;
    }

    java.lang.String findDeAccountPreviousName(android.accounts.Account account) {
        android.database.Cursor query = this.mDeDatabase.getReadableDatabase().query(TABLE_ACCOUNTS, new java.lang.String[]{ACCOUNTS_PREVIOUS_NAME}, "name=? AND type=?", new java.lang.String[]{account.name, account.type}, null, null, null);
        try {
            if (query.moveToNext()) {
                java.lang.String string = query.getString(0);
                query.close();
                return string;
            }
            query.close();
            return null;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    long insertDeAccount(android.accounts.Account account, long j) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mDeDatabase.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("_id", java.lang.Long.valueOf(j));
        contentValues.put("name", account.name);
        contentValues.put(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE, account.type);
        contentValues.put(ACCOUNTS_LAST_AUTHENTICATE_TIME_EPOCH_MILLIS, java.lang.Long.valueOf(java.lang.System.currentTimeMillis()));
        return writableDatabase.insert(TABLE_ACCOUNTS, "name", contentValues);
    }

    boolean renameDeAccount(long j, java.lang.String str, java.lang.String str2) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mDeDatabase.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("name", str);
        contentValues.put(ACCOUNTS_PREVIOUS_NAME, str2);
        return writableDatabase.update(TABLE_ACCOUNTS, contentValues, "_id=?", new java.lang.String[]{java.lang.String.valueOf(j)}) > 0;
    }

    boolean deleteGrantsByAccountIdAuthTokenTypeAndUid(long j, java.lang.String str, long j2) {
        return this.mDeDatabase.getWritableDatabase().delete(TABLE_GRANTS, "accounts_id=? AND auth_token_type=? AND uid=?", new java.lang.String[]{java.lang.String.valueOf(j), str, java.lang.String.valueOf(j2)}) > 0;
    }

    java.util.List<java.lang.Integer> findAllUidGrants() {
        android.database.sqlite.SQLiteDatabase readableDatabase = this.mDeDatabase.getReadableDatabase();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.database.Cursor query = readableDatabase.query(TABLE_GRANTS, new java.lang.String[]{"uid"}, null, null, "uid", null, null);
        while (query.moveToNext()) {
            try {
                arrayList.add(java.lang.Integer.valueOf(query.getInt(0)));
            } finally {
                query.close();
            }
        }
        return arrayList;
    }

    long findMatchingGrantsCount(int i, java.lang.String str, android.accounts.Account account) {
        return android.database.DatabaseUtils.longForQuery(this.mDeDatabase.getReadableDatabase(), COUNT_OF_MATCHING_GRANTS, new java.lang.String[]{java.lang.String.valueOf(i), str, account.name, account.type});
    }

    long findMatchingGrantsCountAnyToken(int i, android.accounts.Account account) {
        return android.database.DatabaseUtils.longForQuery(this.mDeDatabase.getReadableDatabase(), COUNT_OF_MATCHING_GRANTS_ANY_TOKEN, new java.lang.String[]{java.lang.String.valueOf(i), account.name, account.type});
    }

    long insertGrant(long j, java.lang.String str, int i) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mDeDatabase.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("accounts_id", java.lang.Long.valueOf(j));
        contentValues.put(GRANTS_AUTH_TOKEN_TYPE, str);
        contentValues.put("uid", java.lang.Integer.valueOf(i));
        return writableDatabase.insert(TABLE_GRANTS, "accounts_id", contentValues);
    }

    boolean deleteGrantsByUid(int i) {
        return this.mDeDatabase.getWritableDatabase().delete(TABLE_GRANTS, "uid=?", new java.lang.String[]{java.lang.Integer.toString(i)}) > 0;
    }

    boolean setAccountVisibility(long j, java.lang.String str, int i) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mDeDatabase.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("accounts_id", java.lang.String.valueOf(j));
        contentValues.put(VISIBILITY_PACKAGE, str);
        contentValues.put("value", java.lang.String.valueOf(i));
        return writableDatabase.replace(TABLE_VISIBILITY, "value", contentValues) != -1;
    }

    java.lang.Integer findAccountVisibility(android.accounts.Account account, java.lang.String str) {
        android.database.Cursor query = this.mDeDatabase.getReadableDatabase().query(TABLE_VISIBILITY, new java.lang.String[]{"value"}, "accounts_id=(select _id FROM accounts WHERE name=? AND type=?) AND _package=? ", new java.lang.String[]{account.name, account.type, str}, null, null, null);
        try {
            if (query.moveToNext()) {
                return java.lang.Integer.valueOf(query.getInt(0));
            }
            query.close();
            return null;
        } finally {
            query.close();
        }
    }

    java.lang.Integer findAccountVisibility(long j, java.lang.String str) {
        android.database.Cursor query = this.mDeDatabase.getReadableDatabase().query(TABLE_VISIBILITY, new java.lang.String[]{"value"}, "accounts_id=? AND _package=? ", new java.lang.String[]{java.lang.String.valueOf(j), str}, null, null, null);
        try {
            if (query.moveToNext()) {
                return java.lang.Integer.valueOf(query.getInt(0));
            }
            query.close();
            return null;
        } finally {
            query.close();
        }
    }

    android.accounts.Account findDeAccountByAccountId(long j) {
        android.database.Cursor query = this.mDeDatabase.getReadableDatabase().query(TABLE_ACCOUNTS, new java.lang.String[]{"name", com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE}, "_id=? ", new java.lang.String[]{java.lang.String.valueOf(j)}, null, null, null);
        try {
            if (query.moveToNext()) {
                return new android.accounts.Account(query.getString(0), query.getString(1));
            }
            query.close();
            return null;
        } finally {
            query.close();
        }
    }

    java.util.Map<java.lang.String, java.lang.Integer> findAllVisibilityValuesForAccount(android.accounts.Account account) {
        android.database.sqlite.SQLiteDatabase readableDatabase = this.mDeDatabase.getReadableDatabase();
        java.util.HashMap hashMap = new java.util.HashMap();
        android.database.Cursor query = readableDatabase.query(TABLE_VISIBILITY, new java.lang.String[]{VISIBILITY_PACKAGE, "value"}, SELECTION_ACCOUNTS_ID_BY_ACCOUNT, new java.lang.String[]{account.name, account.type}, null, null, null);
        while (query.moveToNext()) {
            try {
                hashMap.put(query.getString(0), java.lang.Integer.valueOf(query.getInt(1)));
            } finally {
                query.close();
            }
        }
        return hashMap;
    }

    java.util.Map<android.accounts.Account, java.util.Map<java.lang.String, java.lang.Integer>> findAllVisibilityValues() {
        android.database.sqlite.SQLiteDatabase readableDatabase = this.mDeDatabase.getReadableDatabase();
        java.util.HashMap hashMap = new java.util.HashMap();
        android.database.Cursor rawQuery = readableDatabase.rawQuery("SELECT visibility._package, visibility.value, accounts.name, accounts.type FROM visibility JOIN accounts ON accounts._id = visibility.accounts_id", null);
        while (rawQuery.moveToNext()) {
            try {
                java.lang.String string = rawQuery.getString(0);
                java.lang.Integer valueOf = java.lang.Integer.valueOf(rawQuery.getInt(1));
                android.accounts.Account account = new android.accounts.Account(rawQuery.getString(2), rawQuery.getString(3));
                java.util.Map map = (java.util.Map) hashMap.get(account);
                if (map == null) {
                    map = new java.util.HashMap();
                    hashMap.put(account, map);
                }
                map.put(string, valueOf);
            } catch (java.lang.Throwable th) {
                rawQuery.close();
                throw th;
            }
        }
        rawQuery.close();
        return hashMap;
    }

    boolean deleteAccountVisibilityForPackage(java.lang.String str) {
        return this.mDeDatabase.getWritableDatabase().delete(TABLE_VISIBILITY, "_package=? ", new java.lang.String[]{str}) > 0;
    }

    long insertOrReplaceMetaAuthTypeAndUid(java.lang.String str, int i) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mDeDatabase.getWritableDatabase();
        android.content.ContentValues contentValues = new android.content.ContentValues();
        contentValues.put("key", META_KEY_FOR_AUTHENTICATOR_UID_FOR_TYPE_PREFIX + str);
        contentValues.put("value", java.lang.Integer.valueOf(i));
        return writableDatabase.insertWithOnConflict(TABLE_META, null, contentValues, 5);
    }

    java.util.Map<java.lang.String, java.lang.Integer> findMetaAuthUid() {
        android.database.Cursor query = this.mDeDatabase.getReadableDatabase().query(TABLE_META, new java.lang.String[]{"key", "value"}, SELECTION_META_BY_AUTHENTICATOR_TYPE, new java.lang.String[]{"auth_uid_for_type:%"}, null, null, "key");
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
        while (query.moveToNext()) {
            try {
                java.lang.String str = android.text.TextUtils.split(query.getString(0), META_KEY_DELIMITER)[1];
                java.lang.String string = query.getString(1);
                if (android.text.TextUtils.isEmpty(str) || android.text.TextUtils.isEmpty(string)) {
                    android.util.Slog.e(TAG, "Auth type empty: " + android.text.TextUtils.isEmpty(str) + ", uid empty: " + android.text.TextUtils.isEmpty(string));
                } else {
                    linkedHashMap.put(str, java.lang.Integer.valueOf(java.lang.Integer.parseInt(query.getString(1))));
                }
            } catch (java.lang.Throwable th) {
                query.close();
                throw th;
            }
        }
        query.close();
        return linkedHashMap;
    }

    boolean deleteMetaByAuthTypeAndUid(java.lang.String str, int i) {
        android.database.sqlite.SQLiteDatabase writableDatabase = this.mDeDatabase.getWritableDatabase();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(META_KEY_FOR_AUTHENTICATOR_UID_FOR_TYPE_PREFIX);
        sb.append(str);
        return writableDatabase.delete(TABLE_META, "key=? AND value=?", new java.lang.String[]{sb.toString(), java.lang.String.valueOf(i)}) > 0;
    }

    java.util.List<android.util.Pair<java.lang.String, java.lang.Integer>> findAllAccountGrants() {
        android.database.Cursor rawQuery = this.mDeDatabase.getReadableDatabase().rawQuery(ACCOUNT_ACCESS_GRANTS, null);
        if (rawQuery != null) {
            try {
                if (rawQuery.moveToFirst()) {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    do {
                        arrayList.add(android.util.Pair.create(rawQuery.getString(0), java.lang.Integer.valueOf(rawQuery.getInt(1))));
                    } while (rawQuery.moveToNext());
                    rawQuery.close();
                    return arrayList;
                }
            } catch (java.lang.Throwable th) {
                if (rawQuery != null) {
                    try {
                        rawQuery.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        java.util.List<android.util.Pair<java.lang.String, java.lang.Integer>> emptyList = java.util.Collections.emptyList();
        if (rawQuery != null) {
            rawQuery.close();
        }
        return emptyList;
    }

    private static class PreNDatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {
        private final android.content.Context mContext;
        private final int mUserId;

        PreNDatabaseHelper(android.content.Context context, int i, java.lang.String str) {
            super(context, str, (android.database.sqlite.SQLiteDatabase.CursorFactory) null, 9);
            this.mContext = context;
            this.mUserId = i;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            throw new java.lang.IllegalStateException("Legacy database cannot be created - only upgraded!");
        }

        private void createSharedAccountsTable(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE shared_accounts ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, type TEXT NOT NULL, UNIQUE(name,type))");
        }

        private void addLastSuccessfullAuthenticatedTimeColumn(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("ALTER TABLE accounts ADD COLUMN last_password_entry_time_millis_epoch DEFAULT 0");
        }

        private void addOldAccountNameColumn(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("ALTER TABLE accounts ADD COLUMN previous_name");
        }

        private void addDebugTable(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            com.android.server.accounts.AccountsDb.DeDatabaseHelper.createDebugTable(sQLiteDatabase);
        }

        private void createAccountsDeletionTrigger(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(" CREATE TRIGGER accountsDelete DELETE ON accounts BEGIN   DELETE FROM authtokens     WHERE accounts_id=OLD._id ;   DELETE FROM extras     WHERE accounts_id=OLD._id ;   DELETE FROM grants     WHERE accounts_id=OLD._id ; END");
        }

        private void createGrantsTable(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE grants (  accounts_id INTEGER NOT NULL, auth_token_type STRING NOT NULL,  uid INTEGER NOT NULL,  UNIQUE (accounts_id,auth_token_type,uid))");
        }

        static long insertMetaAuthTypeAndUid(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, int i) {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("key", com.android.server.accounts.AccountsDb.META_KEY_FOR_AUTHENTICATOR_UID_FOR_TYPE_PREFIX + str);
            contentValues.put("value", java.lang.Integer.valueOf(i));
            return sQLiteDatabase.insert(com.android.server.accounts.AccountsDb.TABLE_META, null, contentValues);
        }

        private void populateMetaTableWithAuthTypeAndUID(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.util.Map<java.lang.String, java.lang.Integer> map) {
            for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : map.entrySet()) {
                insertMetaAuthTypeAndUid(sQLiteDatabase, entry.getKey(), entry.getValue().intValue());
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
            android.util.Log.e(com.android.server.accounts.AccountsDb.TAG, "upgrade from version " + i + " to version " + i2);
            if (i == 1) {
                i++;
            }
            if (i == 2) {
                createGrantsTable(sQLiteDatabase);
                sQLiteDatabase.execSQL("DROP TRIGGER accountsDelete");
                createAccountsDeletionTrigger(sQLiteDatabase);
                i++;
            }
            if (i == 3) {
                sQLiteDatabase.execSQL("UPDATE accounts SET type = 'com.google' WHERE type == 'com.google.GAIA'");
                i++;
            }
            if (i == 4) {
                createSharedAccountsTable(sQLiteDatabase);
                i++;
            }
            if (i == 5) {
                addOldAccountNameColumn(sQLiteDatabase);
                i++;
            }
            if (i == 6) {
                addLastSuccessfullAuthenticatedTimeColumn(sQLiteDatabase);
                i++;
            }
            if (i == 7) {
                addDebugTable(sQLiteDatabase);
                i++;
            }
            if (i == 8) {
                populateMetaTableWithAuthTypeAndUID(sQLiteDatabase, com.android.server.accounts.AccountManagerService.getAuthenticatorTypeAndUIDForUser(this.mContext, this.mUserId));
                i++;
            }
            if (i != i2) {
                android.util.Log.e(com.android.server.accounts.AccountsDb.TAG, "failed to upgrade version " + i + " to version " + i2);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onOpen(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
            if (android.util.Log.isLoggable(com.android.server.accounts.AccountsDb.TAG, 2)) {
                android.util.Log.v(com.android.server.accounts.AccountsDb.TAG, "opened database accounts.db");
            }
        }
    }

    java.util.List<android.accounts.Account> findCeAccountsNotInDe() {
        android.database.Cursor rawQuery = this.mDeDatabase.getReadableDatabaseUserIsUnlocked().rawQuery("SELECT name,type FROM ceDb.accounts WHERE NOT EXISTS  (SELECT _id FROM accounts WHERE _id=ceDb.accounts._id )", null);
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList(rawQuery.getCount());
            while (rawQuery.moveToNext()) {
                arrayList.add(new android.accounts.Account(rawQuery.getString(0), rawQuery.getString(1)));
            }
            return arrayList;
        } finally {
            rawQuery.close();
        }
    }

    boolean deleteCeAccount(long j) {
        android.database.sqlite.SQLiteDatabase writableDatabaseUserIsUnlocked = this.mDeDatabase.getWritableDatabaseUserIsUnlocked();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("_id=");
        sb.append(j);
        return writableDatabaseUserIsUnlocked.delete(CE_TABLE_ACCOUNTS, sb.toString(), null) > 0;
    }

    boolean isCeDatabaseAttached() {
        return this.mDeDatabase.mCeAttached;
    }

    void beginTransaction() {
        this.mDeDatabase.getWritableDatabase().beginTransaction();
    }

    void setTransactionSuccessful() {
        this.mDeDatabase.getWritableDatabase().setTransactionSuccessful();
    }

    void endTransaction() {
        this.mDeDatabase.getWritableDatabase().endTransaction();
    }

    void attachCeDatabase(java.io.File file) {
        com.android.server.accounts.AccountsDb.CeDatabaseHelper.create(this.mContext, this.mPreNDatabaseFile, file);
        this.mDeDatabase.getWritableDatabase().execSQL("ATTACH DATABASE '" + file.getPath() + "' AS ceDb");
        this.mDeDatabase.mCeAttached = true;
    }

    long calculateDebugTableInsertionPoint() {
        try {
            android.database.sqlite.SQLiteDatabase readableDatabase = this.mDeDatabase.getReadableDatabase();
            int longForQuery = (int) android.database.DatabaseUtils.longForQuery(readableDatabase, "SELECT COUNT(*) FROM " + TABLE_DEBUG, null);
            if (longForQuery < 64) {
                return longForQuery;
            }
            return android.database.DatabaseUtils.longForQuery(readableDatabase, "SELECT " + DEBUG_TABLE_KEY + " FROM " + TABLE_DEBUG + " ORDER BY " + DEBUG_TABLE_TIMESTAMP + "," + DEBUG_TABLE_KEY + " LIMIT 1", null);
        } catch (android.database.sqlite.SQLiteException e) {
            android.util.Log.e(TAG, "Failed to open debug table" + e);
            return -1L;
        }
    }

    android.database.sqlite.SQLiteStatement compileSqlStatementForLogging() {
        return this.mDeDatabase.getWritableDatabase().compileStatement("INSERT OR REPLACE INTO " + TABLE_DEBUG + " VALUES (?,?,?,?,?,?)");
    }

    @android.annotation.Nullable
    android.database.sqlite.SQLiteStatement getStatementForLogging() {
        if (this.mDebugStatementForLogging != null) {
            return this.mDebugStatementForLogging;
        }
        try {
            this.mDebugStatementForLogging = compileSqlStatementForLogging();
            return this.mDebugStatementForLogging;
        } catch (android.database.sqlite.SQLiteException e) {
            android.util.Log.e(TAG, "Failed to open debug table" + e);
            return null;
        }
    }

    void closeDebugStatement() {
        synchronized (this.mDebugStatementLock) {
            try {
                if (this.mDebugStatementForLogging != null) {
                    this.mDebugStatementForLogging.close();
                    this.mDebugStatementForLogging = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    long reserveDebugDbInsertionPoint() {
        if (this.mDebugDbInsertionPoint == -1) {
            this.mDebugDbInsertionPoint = calculateDebugTableInsertionPoint();
            return this.mDebugDbInsertionPoint;
        }
        this.mDebugDbInsertionPoint = (this.mDebugDbInsertionPoint + 1) % 64;
        return this.mDebugDbInsertionPoint;
    }

    void dumpDebugTable(java.io.PrintWriter printWriter) {
        android.database.Cursor query = this.mDeDatabase.getReadableDatabase().query(TABLE_DEBUG, null, null, null, null, null, DEBUG_TABLE_TIMESTAMP);
        printWriter.println("AccountId, Action_Type, timestamp, UID, TableName, Key");
        printWriter.println("Accounts History");
        while (query.moveToNext()) {
            try {
                printWriter.println(query.getString(0) + "," + query.getString(1) + "," + query.getString(2) + "," + query.getString(3) + "," + query.getString(4) + "," + query.getString(5));
            } finally {
                query.close();
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mDeDatabase.close();
    }

    static void deleteDbFileWarnIfFailed(java.io.File file) {
        if (!android.database.sqlite.SQLiteDatabase.deleteDatabase(file)) {
            android.util.Log.w(TAG, "Database at " + file + " was not deleted successfully");
        }
    }

    public static com.android.server.accounts.AccountsDb create(android.content.Context context, int i, java.io.File file, java.io.File file2) {
        boolean exists = file2.exists();
        com.android.server.accounts.AccountsDb.DeDatabaseHelper deDatabaseHelper = new com.android.server.accounts.AccountsDb.DeDatabaseHelper(context, i, file2.getPath());
        if (!exists && file.exists()) {
            com.android.server.accounts.AccountsDb.PreNDatabaseHelper preNDatabaseHelper = new com.android.server.accounts.AccountsDb.PreNDatabaseHelper(context, i, file.getPath());
            preNDatabaseHelper.getWritableDatabase();
            preNDatabaseHelper.close();
            deDatabaseHelper.migratePreNDbToDe(file);
        }
        return new com.android.server.accounts.AccountsDb(deDatabaseHelper, context, file);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void resetDatabase(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        android.database.Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type ='table'", null);
        while (rawQuery.moveToNext()) {
            try {
                java.lang.String string = rawQuery.getString(0);
                if (!"android_metadata".equals(string) && !"sqlite_sequence".equals(string)) {
                    sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + string);
                }
            } finally {
            }
        }
        rawQuery.close();
        rawQuery = sQLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type ='trigger'", null);
        while (rawQuery.moveToNext()) {
            try {
                sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS " + rawQuery.getString(0));
            } finally {
            }
        }
        rawQuery.close();
    }
}
