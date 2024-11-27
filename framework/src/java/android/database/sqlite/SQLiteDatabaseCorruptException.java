package android.database.sqlite;

/* loaded from: classes.dex */
public class SQLiteDatabaseCorruptException extends android.database.sqlite.SQLiteException {
    public SQLiteDatabaseCorruptException() {
    }

    public SQLiteDatabaseCorruptException(java.lang.String str) {
        super(str);
    }

    public static boolean isCorruptException(java.lang.Throwable th) {
        while (th != null) {
            if (th instanceof android.database.sqlite.SQLiteDatabaseCorruptException) {
                return true;
            }
            th = th.getCause();
        }
        return false;
    }
}
