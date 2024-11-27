package android.database.sqlite;

/* loaded from: classes.dex */
public abstract class SQLiteProgram extends android.database.sqlite.SQLiteClosable {
    private static final java.lang.String[] EMPTY_STRING_ARRAY = new java.lang.String[0];
    private final java.lang.Object[] mBindArgs;
    private final java.lang.String[] mColumnNames;
    private final android.database.sqlite.SQLiteDatabase mDatabase;
    private final int mNumParameters;
    private final boolean mReadOnly;
    private final java.lang.String mSql;

    SQLiteProgram(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str, java.lang.Object[] objArr, android.os.CancellationSignal cancellationSignal) {
        this.mDatabase = sQLiteDatabase;
        this.mSql = str.trim();
        int sqlStatementType = android.database.DatabaseUtils.getSqlStatementType(this.mSql);
        switch (sqlStatementType) {
            case 4:
            case 5:
            case 6:
                this.mReadOnly = false;
                this.mColumnNames = EMPTY_STRING_ARRAY;
                this.mNumParameters = 0;
                break;
            default:
                boolean z = sqlStatementType == 1;
                android.database.sqlite.SQLiteStatementInfo sQLiteStatementInfo = new android.database.sqlite.SQLiteStatementInfo();
                sQLiteDatabase.getThreadSession().prepare(this.mSql, sQLiteDatabase.getThreadDefaultConnectionFlags(z), cancellationSignal, sQLiteStatementInfo);
                this.mReadOnly = sQLiteStatementInfo.readOnly;
                this.mColumnNames = sQLiteStatementInfo.columnNames;
                this.mNumParameters = sQLiteStatementInfo.numParameters;
                break;
        }
        if (objArr != null && objArr.length > this.mNumParameters) {
            throw new java.lang.IllegalArgumentException("Too many bind arguments.  " + objArr.length + " arguments were provided but the statement needs " + this.mNumParameters + " arguments.");
        }
        if (this.mNumParameters != 0) {
            this.mBindArgs = new java.lang.Object[this.mNumParameters];
            if (objArr != null) {
                java.lang.System.arraycopy(objArr, 0, this.mBindArgs, 0, objArr.length);
                return;
            }
            return;
        }
        this.mBindArgs = null;
    }

    final android.database.sqlite.SQLiteDatabase getDatabase() {
        return this.mDatabase;
    }

    final java.lang.String getSql() {
        return this.mSql;
    }

    final java.lang.Object[] getBindArgs() {
        return this.mBindArgs;
    }

    final java.lang.String[] getColumnNames() {
        return this.mColumnNames;
    }

    protected final android.database.sqlite.SQLiteSession getSession() {
        return this.mDatabase.getThreadSession();
    }

    protected final int getConnectionFlags() {
        return this.mDatabase.getThreadDefaultConnectionFlags(this.mReadOnly);
    }

    protected final void onCorruption() {
        this.mDatabase.onCorruption();
    }

    @java.lang.Deprecated
    public final int getUniqueId() {
        return -1;
    }

    public void bindNull(int i) {
        bind(i, null);
    }

    public void bindLong(int i, long j) {
        bind(i, java.lang.Long.valueOf(j));
    }

    public void bindDouble(int i, double d) {
        bind(i, java.lang.Double.valueOf(d));
    }

    public void bindString(int i, java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("the bind value at index " + i + " is null");
        }
        bind(i, str);
    }

    public void bindBlob(int i, byte[] bArr) {
        if (bArr == null) {
            throw new java.lang.IllegalArgumentException("the bind value at index " + i + " is null");
        }
        bind(i, bArr);
    }

    public void clearBindings() {
        if (this.mBindArgs != null) {
            java.util.Arrays.fill(this.mBindArgs, (java.lang.Object) null);
        }
    }

    public void bindAllArgsAsStrings(java.lang.String[] strArr) {
        if (strArr != null) {
            for (int length = strArr.length; length != 0; length--) {
                bindString(length, strArr[length - 1]);
            }
        }
    }

    @Override // android.database.sqlite.SQLiteClosable
    protected void onAllReferencesReleased() {
        clearBindings();
    }

    private void bind(int i, java.lang.Object obj) {
        if (i >= 1 && i <= this.mNumParameters) {
            this.mBindArgs[i - 1] = obj;
            return;
        }
        throw new java.lang.IllegalArgumentException("Cannot bind argument at index " + i + " because the index is out of range.  The statement has " + this.mNumParameters + " parameters.");
    }
}
