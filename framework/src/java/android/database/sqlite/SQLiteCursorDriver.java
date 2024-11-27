package android.database.sqlite;

/* loaded from: classes.dex */
public interface SQLiteCursorDriver {
    void cursorClosed();

    void cursorDeactivated();

    void cursorRequeried(android.database.Cursor cursor);

    android.database.Cursor query(android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, java.lang.String[] strArr);

    void setBindArguments(java.lang.String[] strArr);
}
