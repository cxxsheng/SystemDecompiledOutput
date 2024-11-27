package android.database.sqlite;

/* loaded from: classes.dex */
public final class SQLiteQuery extends android.database.sqlite.SQLiteProgram {
    private static final java.lang.String TAG = "SQLiteQuery";
    private final android.os.CancellationSignal mCancellationSignal;

    SQLiteQuery(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, android.os.CancellationSignal cancellationSignal) {
        super(sQLiteDatabase, str, null, cancellationSignal);
        this.mCancellationSignal = cancellationSignal;
    }

    int fillWindow(android.database.CursorWindow cursorWindow, int i, int i2, boolean z) {
        acquireReference();
        try {
            cursorWindow.acquireReference();
            try {
                try {
                    return getSession().executeForCursorWindow(getSql(), getBindArgs(), cursorWindow, i, i2, z, getConnectionFlags(), this.mCancellationSignal);
                } catch (android.database.sqlite.SQLiteDatabaseCorruptException e) {
                    onCorruption();
                    throw e;
                } catch (android.database.sqlite.SQLiteException e2) {
                    android.util.Log.e(TAG, "exception: " + e2.getMessage() + "; query: " + getSql());
                    throw e2;
                }
            } finally {
                cursorWindow.releaseReference();
            }
        } finally {
            releaseReference();
        }
    }

    public java.lang.String toString() {
        return "SQLiteQuery: " + getSql();
    }
}
