package com.android.server.locksettings.recoverablekeystore.storage;

/* loaded from: classes2.dex */
class RecoverableKeyStoreDbHelper extends android.database.sqlite.SQLiteOpenHelper {
    private static final java.lang.String DATABASE_NAME = "recoverablekeystore.db";
    static final int DATABASE_VERSION_7 = 7;
    private static final java.lang.String SQL_CREATE_KEYS_ENTRY = "CREATE TABLE keys( _id INTEGER PRIMARY KEY,user_id INTEGER,uid INTEGER,alias TEXT,nonce BLOB,wrapped_key BLOB,platform_key_generation_id INTEGER,last_synced_at INTEGER,recovery_status INTEGER,key_metadata BLOB,UNIQUE(uid,alias))";
    private static final java.lang.String SQL_CREATE_RECOVERY_SERVICE_METADATA_ENTRY = "CREATE TABLE recovery_service_metadata (_id INTEGER PRIMARY KEY,user_id INTEGER,uid INTEGER,snapshot_version INTEGER,should_create_snapshot INTEGER,active_root_of_trust TEXT,public_key BLOB,cert_path BLOB,cert_serial INTEGER,secret_types TEXT,counter_id INTEGER,server_params BLOB,UNIQUE(user_id,uid))";
    private static final java.lang.String SQL_CREATE_ROOT_OF_TRUST_ENTRY = "CREATE TABLE root_of_trust (_id INTEGER PRIMARY KEY,user_id INTEGER,uid INTEGER,root_alias TEXT,cert_path BLOB,cert_serial INTEGER,UNIQUE(user_id,uid,root_alias))";
    private static final java.lang.String SQL_CREATE_USER_METADATA_ENTRY = "CREATE TABLE user_metadata( _id INTEGER PRIMARY KEY,user_id INTEGER UNIQUE,platform_key_generation_id INTEGER,user_serial_number INTEGER DEFAULT -1)";
    private static final java.lang.String SQL_CREATE_USER_METADATA_ENTRY_FOR_V7 = "CREATE TABLE user_metadata( _id INTEGER PRIMARY KEY,user_id INTEGER UNIQUE,platform_key_generation_id INTEGER,user_serial_number INTEGER DEFAULT -1,bad_remote_guess_counter INTEGER DEFAULT 0)";
    private static final java.lang.String SQL_DELETE_KEYS_ENTRY = "DROP TABLE IF EXISTS keys";
    private static final java.lang.String SQL_DELETE_RECOVERY_SERVICE_METADATA_ENTRY = "DROP TABLE IF EXISTS recovery_service_metadata";
    private static final java.lang.String SQL_DELETE_ROOT_OF_TRUST_ENTRY = "DROP TABLE IF EXISTS root_of_trust";
    private static final java.lang.String SQL_DELETE_USER_METADATA_ENTRY = "DROP TABLE IF EXISTS user_metadata";
    private static final java.lang.String TAG = "RecoverableKeyStoreDbHp";

    RecoverableKeyStoreDbHelper(android.content.Context context) {
        super(context, DATABASE_NAME, (android.database.sqlite.SQLiteDatabase.CursorFactory) null, getDbVersion(context));
    }

    private static int getDbVersion(android.content.Context context) {
        return 7;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(SQL_CREATE_KEYS_ENTRY);
        sQLiteDatabase.execSQL(SQL_CREATE_USER_METADATA_ENTRY_FOR_V7);
        sQLiteDatabase.execSQL(SQL_CREATE_RECOVERY_SERVICE_METADATA_ENTRY);
        sQLiteDatabase.execSQL(SQL_CREATE_ROOT_OF_TRUST_ENTRY);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
        android.util.Log.e(TAG, "Recreating recoverablekeystore after unexpected version downgrade.");
        dropAllKnownTables(sQLiteDatabase);
        onCreate(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
        try {
            if (i < 2) {
                dropAllKnownTables(sQLiteDatabase);
                onCreate(sQLiteDatabase);
                return;
            }
            if (i < 3 && i2 >= 3) {
                upgradeDbForVersion3(sQLiteDatabase);
                i = 3;
            }
            if (i < 4 && i2 >= 4) {
                upgradeDbForVersion4(sQLiteDatabase);
                i = 4;
            }
            if (i < 5 && i2 >= 5) {
                upgradeDbForVersion5(sQLiteDatabase);
                i = 5;
            }
            if (i < 6 && i2 >= 6) {
                upgradeDbForVersion6(sQLiteDatabase);
                i = 6;
            }
            if (i < 7 && i2 >= 7) {
                try {
                    upgradeDbForVersion7(sQLiteDatabase);
                } catch (android.database.sqlite.SQLiteException e) {
                    android.util.Log.w(TAG, "Column was added without version update - ignore error", e);
                }
                i = 7;
            }
            if (i != i2) {
                android.util.Log.e(TAG, "Failed to update recoverablekeystore database to the most recent version");
            }
        } catch (android.database.sqlite.SQLiteException e2) {
            android.util.Log.e(TAG, "Recreating recoverablekeystore after unexpected upgrade error.", e2);
            dropAllKnownTables(sQLiteDatabase);
            onCreate(sQLiteDatabase);
        }
    }

    private void dropAllKnownTables(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(SQL_DELETE_KEYS_ENTRY);
        sQLiteDatabase.execSQL(SQL_DELETE_USER_METADATA_ENTRY);
        sQLiteDatabase.execSQL(SQL_DELETE_RECOVERY_SERVICE_METADATA_ENTRY);
        sQLiteDatabase.execSQL(SQL_DELETE_ROOT_OF_TRUST_ENTRY);
    }

    private void upgradeDbForVersion3(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        addColumnToTable(sQLiteDatabase, "recovery_service_metadata", "cert_path", "BLOB", null);
        addColumnToTable(sQLiteDatabase, "recovery_service_metadata", "cert_serial", "INTEGER", null);
    }

    private void upgradeDbForVersion4(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        android.util.Log.d(TAG, "Updating recoverable keystore database to version 4");
        sQLiteDatabase.execSQL(SQL_CREATE_ROOT_OF_TRUST_ENTRY);
        addColumnToTable(sQLiteDatabase, "recovery_service_metadata", "active_root_of_trust", "TEXT", null);
    }

    private void upgradeDbForVersion5(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        android.util.Log.d(TAG, "Updating recoverable keystore database to version 5");
        addColumnToTable(sQLiteDatabase, "keys", "key_metadata", "BLOB", null);
    }

    private void upgradeDbForVersion6(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        android.util.Log.d(TAG, "Updating recoverable keystore database to version 6");
        addColumnToTable(sQLiteDatabase, "user_metadata", "user_serial_number", "INTEGER DEFAULT -1", null);
    }

    private void upgradeDbForVersion7(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        android.util.Log.d(TAG, "Updating recoverable keystore database to version 7");
        addColumnToTable(sQLiteDatabase, "user_metadata", "bad_remote_guess_counter", "INTEGER DEFAULT 0", null);
    }

    private static void addColumnToTable(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        android.util.Log.d(TAG, "Adding column " + str2 + " to " + str + ".");
        java.lang.String str5 = "ALTER TABLE " + str + " ADD COLUMN " + str2 + " " + str3;
        if (str4 != null && !str4.isEmpty()) {
            str5 = str5 + " DEFAULT " + str4;
        }
        sQLiteDatabase.execSQL(str5 + ";");
    }
}
