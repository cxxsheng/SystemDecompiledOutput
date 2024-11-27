package android.database.sqlite;

/* loaded from: classes.dex */
public class SQLiteCursor extends android.database.AbstractWindowedCursor {
    static final int NO_COUNT = -1;
    static final java.lang.String TAG = "SQLiteCursor";
    private java.util.Map<java.lang.String, java.lang.Integer> mColumnNameMap;
    private final java.lang.String[] mColumns;
    private int mCount;
    private int mCursorWindowCapacity;
    private final android.database.sqlite.SQLiteCursorDriver mDriver;
    private final java.lang.String mEditTable;
    private boolean mFillWindowForwardOnly;
    private final android.database.sqlite.SQLiteQuery mQuery;

    @java.lang.Deprecated
    public SQLiteCursor(android.database.sqlite.SQLiteDatabase sQLiteDatabase, android.database.sqlite.SQLiteCursorDriver sQLiteCursorDriver, java.lang.String str, android.database.sqlite.SQLiteQuery sQLiteQuery) {
        this(sQLiteCursorDriver, str, sQLiteQuery);
    }

    public SQLiteCursor(android.database.sqlite.SQLiteCursorDriver sQLiteCursorDriver, java.lang.String str, android.database.sqlite.SQLiteQuery sQLiteQuery) {
        this.mCount = -1;
        if (sQLiteQuery == null) {
            throw new java.lang.IllegalArgumentException("query object cannot be null");
        }
        this.mDriver = sQLiteCursorDriver;
        this.mEditTable = str;
        this.mColumnNameMap = null;
        this.mQuery = sQLiteQuery;
        this.mColumns = sQLiteQuery.getColumnNames();
    }

    public android.database.sqlite.SQLiteDatabase getDatabase() {
        return this.mQuery.getDatabase();
    }

    @Override // android.database.AbstractCursor, android.database.CrossProcessCursor
    public boolean onMove(int i, int i2) {
        if (this.mWindow == null || i2 < this.mWindow.getStartPosition() || i2 >= this.mWindow.getStartPosition() + this.mWindow.getNumRows()) {
            fillWindow(i2);
            return true;
        }
        return true;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getCount() {
        if (this.mCount == -1) {
            fillWindow(0);
        }
        return this.mCount;
    }

    private void fillWindow(int i) {
        clearOrCreateWindow(getDatabase().getPath());
        try {
            com.android.internal.util.Preconditions.checkArgumentNonnegative(i, "requiredPos cannot be negative");
            if (this.mCount == -1) {
                this.mCount = this.mQuery.fillWindow(this.mWindow, i, i, true);
                this.mCursorWindowCapacity = this.mWindow.getNumRows();
                if (android.database.sqlite.SQLiteDebug.NoPreloadHolder.DEBUG_SQL_LOG) {
                    android.util.Log.d(TAG, "received count(*) from native_fill_window: " + this.mCount);
                    return;
                }
                return;
            }
            this.mQuery.fillWindow(this.mWindow, this.mFillWindowForwardOnly ? i : android.database.DatabaseUtils.cursorPickFillWindowStartPosition(i, this.mCursorWindowCapacity), i, false);
        } catch (java.lang.RuntimeException e) {
            closeWindow();
            throw e;
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getColumnIndex(java.lang.String str) {
        if (this.mColumnNameMap == null) {
            java.lang.String[] strArr = this.mColumns;
            int length = strArr.length;
            java.util.HashMap hashMap = new java.util.HashMap(length, 1.0f);
            for (int i = 0; i < length; i++) {
                hashMap.put(strArr[i], java.lang.Integer.valueOf(i));
            }
            this.mColumnNameMap = hashMap;
        }
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf != -1) {
            android.util.Log.e(TAG, "requesting column name with table name -- " + str, new java.lang.Exception());
            str = str.substring(lastIndexOf + 1);
        }
        java.lang.Integer num = this.mColumnNameMap.get(str);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public java.lang.String[] getColumnNames() {
        return this.mColumns;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void deactivate() {
        super.deactivate();
        this.mDriver.cursorDeactivated();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        super.close();
        synchronized (this) {
            this.mQuery.close();
            this.mDriver.cursorClosed();
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean requery() {
        if (isClosed()) {
            return false;
        }
        synchronized (this) {
            if (!this.mQuery.getDatabase().isOpen()) {
                return false;
            }
            if (this.mWindow != null) {
                this.mWindow.clear();
            }
            this.mPos = -1;
            this.mCount = -1;
            this.mDriver.cursorRequeried(this);
            try {
                return super.requery();
            } catch (java.lang.IllegalStateException e) {
                android.util.Log.w(TAG, "requery() failed " + e.getMessage(), e);
                return false;
            }
        }
    }

    @Override // android.database.AbstractWindowedCursor
    public void setWindow(android.database.CursorWindow cursorWindow) {
        super.setWindow(cursorWindow);
        this.mCount = -1;
    }

    public void setSelectionArguments(java.lang.String[] strArr) {
        this.mDriver.setBindArguments(strArr);
    }

    public void setFillWindowForwardOnly(boolean z) {
        this.mFillWindowForwardOnly = z;
    }

    @Override // android.database.AbstractCursor
    protected void finalize() {
        try {
            if (this.mWindow != null && android.os.StrictMode.vmSqliteObjectLeaksEnabled()) {
                java.lang.String sql = this.mQuery.getSql();
                int length = sql.length();
                java.lang.StringBuilder append = new java.lang.StringBuilder().append("Finalizing a Cursor that has not been deactivated or closed. database = ").append(this.mQuery.getDatabase().getLabel()).append(", table = ").append(this.mEditTable).append(", query = ");
                if (length > 1000) {
                    length = 1000;
                }
                android.os.StrictMode.onSqliteObjectLeaked(append.append(sql.substring(0, length)).toString(), null);
            }
        } finally {
            super.finalize();
        }
    }
}
