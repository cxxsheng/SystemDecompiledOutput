package android.database;

/* loaded from: classes.dex */
public final class DefaultDatabaseErrorHandler implements android.database.DatabaseErrorHandler {
    private static final java.lang.String TAG = "DefaultDatabaseErrorHandler";

    @Override // android.database.DatabaseErrorHandler
    public void onCorruption(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        android.util.Log.e(TAG, "Corruption reported by sqlite on database: " + sQLiteDatabase.getPath());
        android.database.sqlite.SQLiteDatabase.wipeDetected(sQLiteDatabase.getPath(), "corruption");
        if (!sQLiteDatabase.isOpen()) {
            deleteDatabaseFile(sQLiteDatabase.getPath());
            return;
        }
        java.util.List<android.util.Pair<java.lang.String, java.lang.String>> list = null;
        try {
            try {
                list = sQLiteDatabase.getAttachedDbs();
            } catch (android.database.sqlite.SQLiteException e) {
            }
            try {
                sQLiteDatabase.close();
            } catch (android.database.sqlite.SQLiteException e2) {
            }
        } finally {
            if (list != null) {
                java.util.Iterator<android.util.Pair<java.lang.String, java.lang.String>> it = list.iterator();
                while (it.hasNext()) {
                    deleteDatabaseFile(it.next().second);
                }
            } else {
                deleteDatabaseFile(sQLiteDatabase.getPath());
            }
        }
    }

    private void deleteDatabaseFile(java.lang.String str) {
        if (str.equalsIgnoreCase(android.database.sqlite.SQLiteDatabaseConfiguration.MEMORY_DB_PATH) || str.trim().length() == 0) {
            return;
        }
        android.util.Log.e(TAG, "deleting the database file: " + str);
        try {
            android.database.sqlite.SQLiteDatabase.deleteDatabase(new java.io.File(str), false);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "delete failed: " + e.getMessage());
        }
    }
}
