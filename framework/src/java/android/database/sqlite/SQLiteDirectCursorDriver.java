package android.database.sqlite;

/* loaded from: classes.dex */
public final class SQLiteDirectCursorDriver implements android.database.sqlite.SQLiteCursorDriver {
    private final android.os.CancellationSignal mCancellationSignal;
    private final android.database.sqlite.SQLiteDatabase mDatabase;
    private final java.lang.String mEditTable;
    private android.database.sqlite.SQLiteQuery mQuery;
    private final java.lang.String mSql;

    public SQLiteDirectCursorDriver(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.String str2, android.os.CancellationSignal cancellationSignal) {
        this.mDatabase = sQLiteDatabase;
        this.mEditTable = str2;
        this.mSql = str;
        this.mCancellationSignal = cancellationSignal;
    }

    @Override // android.database.sqlite.SQLiteCursorDriver
    public android.database.Cursor query(android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, java.lang.String[] strArr) {
        android.database.Cursor newCursor;
        android.database.sqlite.SQLiteQuery sQLiteQuery = new android.database.sqlite.SQLiteQuery(this.mDatabase, this.mSql, this.mCancellationSignal);
        try {
            sQLiteQuery.bindAllArgsAsStrings(strArr);
            if (cursorFactory == null) {
                newCursor = new android.database.sqlite.SQLiteCursor(this, this.mEditTable, sQLiteQuery);
            } else {
                newCursor = cursorFactory.newCursor(this.mDatabase, this, this.mEditTable, sQLiteQuery);
            }
            this.mQuery = sQLiteQuery;
            return newCursor;
        } catch (java.lang.RuntimeException e) {
            sQLiteQuery.close();
            throw e;
        }
    }

    @Override // android.database.sqlite.SQLiteCursorDriver
    public void cursorClosed() {
    }

    @Override // android.database.sqlite.SQLiteCursorDriver
    public void setBindArguments(java.lang.String[] strArr) {
        this.mQuery.bindAllArgsAsStrings(strArr);
    }

    @Override // android.database.sqlite.SQLiteCursorDriver
    public void cursorDeactivated() {
    }

    @Override // android.database.sqlite.SQLiteCursorDriver
    public void cursorRequeried(android.database.Cursor cursor) {
    }

    public java.lang.String toString() {
        return "SQLiteDirectCursorDriver: " + this.mSql;
    }
}
