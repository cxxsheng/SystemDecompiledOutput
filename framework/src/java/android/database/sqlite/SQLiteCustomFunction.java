package android.database.sqlite;

/* loaded from: classes.dex */
public final class SQLiteCustomFunction {
    public final android.database.sqlite.SQLiteDatabase.CustomFunction callback;
    public final java.lang.String name;
    public final int numArgs;

    public SQLiteCustomFunction(java.lang.String str, int i, android.database.sqlite.SQLiteDatabase.CustomFunction customFunction) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("name must not be null.");
        }
        this.name = str;
        this.numArgs = i;
        this.callback = customFunction;
    }

    private void dispatchCallback(java.lang.String[] strArr) {
        this.callback.callback(strArr);
    }
}
