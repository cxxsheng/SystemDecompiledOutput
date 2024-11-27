package android.database.sqlite;

/* loaded from: classes.dex */
public final class SQLiteRawStatement implements java.io.Closeable {
    private static final int SQLITE_BUSY = 5;
    public static final int SQLITE_DATA_TYPE_BLOB = 4;
    public static final int SQLITE_DATA_TYPE_FLOAT = 2;
    public static final int SQLITE_DATA_TYPE_INTEGER = 1;
    public static final int SQLITE_DATA_TYPE_NULL = 5;
    public static final int SQLITE_DATA_TYPE_TEXT = 3;
    private static final int SQLITE_DONE = 101;
    private static final int SQLITE_LOCKED = 6;
    private static final int SQLITE_ROW = 100;
    private static final java.lang.String TAG = "SQLiteRawStatement";
    private final android.database.sqlite.SQLiteDatabase mDatabase;
    private android.database.sqlite.SQLiteConnection.PreparedStatement mPreparedStatement;
    private final android.database.sqlite.SQLiteSession mSession;
    private final java.lang.String mSql;
    private final long mStatement;
    private java.lang.Thread mThread = java.lang.Thread.currentThread();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SQLiteDataType {
    }

    @dalvik.annotation.optimization.FastNative
    private static native void nativeBindBlob(long j, int i, byte[] bArr, int i2, int i3);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeBindDouble(long j, int i, double d);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeBindInt(long j, int i, int i2);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeBindLong(long j, int i, long j2);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeBindNull(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native int nativeBindParameterCount(long j);

    @dalvik.annotation.optimization.FastNative
    private static native int nativeBindParameterIndex(long j, java.lang.String str);

    @dalvik.annotation.optimization.FastNative
    private static native java.lang.String nativeBindParameterName(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeBindText(long j, int i, java.lang.String str);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeClearBindings(long j);

    @dalvik.annotation.optimization.FastNative
    private static native byte[] nativeColumnBlob(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native int nativeColumnBuffer(long j, int i, byte[] bArr, int i2, int i3, int i4);

    @dalvik.annotation.optimization.FastNative
    private static native int nativeColumnBytes(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native int nativeColumnCount(long j);

    @dalvik.annotation.optimization.FastNative
    private static native double nativeColumnDouble(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native int nativeColumnInt(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native long nativeColumnLong(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native java.lang.String nativeColumnName(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native java.lang.String nativeColumnText(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native int nativeColumnType(long j, int i);

    private static native void nativeReset(long j, boolean z);

    private static native int nativeStep(long j, boolean z);

    SQLiteRawStatement(android.database.sqlite.SQLiteDatabase sQLiteDatabase, java.lang.String str) {
        this.mDatabase = sQLiteDatabase;
        this.mSession = this.mDatabase.getThreadSession();
        this.mSession.throwIfNoTransaction();
        this.mSql = str;
        this.mPreparedStatement = this.mSession.acquirePersistentStatement(this.mSql, this);
        this.mStatement = this.mPreparedStatement.mStatementPtr;
    }

    private void throwIfInvalid() {
        if (this.mThread != java.lang.Thread.currentThread()) {
            if (this.mThread == null) {
                throw new java.lang.IllegalStateException("method called on a closed statement");
            }
            throw new java.lang.IllegalStateException("method called on a foreign thread: " + this.mThread);
        }
    }

    private void throwIfInvalidBounds(int i, int i2, int i3) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("invalid array length " + i);
        }
        if (i2 < 0 || i2 >= i) {
            throw new java.lang.IllegalArgumentException("invalid offset " + i2 + " for array length " + i);
        }
        if (i3 <= 0 || i - i2 < i3) {
            throw new java.lang.IllegalArgumentException("invalid offset " + i2 + " and length " + i3 + " for array length " + i);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.mThread != null) {
            throwIfInvalid();
            this.mSession.releasePersistentStatement(this.mPreparedStatement, this);
            this.mThread = null;
        }
    }

    public boolean isOpen() {
        return this.mThread != null;
    }

    public boolean step() {
        throwIfInvalid();
        try {
            int nativeStep = nativeStep(this.mStatement, true);
            switch (nativeStep) {
                case 5:
                    throw new android.database.sqlite.SQLiteDatabaseLockedException("database " + this.mDatabase + " busy");
                case 6:
                    throw new android.database.sqlite.SQLiteDatabaseLockedException("database " + this.mDatabase + " locked");
                case 100:
                    return true;
                case 101:
                    java.lang.ref.Reference.reachabilityFence(this);
                    return false;
                default:
                    throw new android.database.sqlite.SQLiteException("unknown error " + nativeStep);
            }
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public int stepNoThrow() {
        throwIfInvalid();
        try {
            return nativeStep(this.mStatement, false);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public void reset() {
        throwIfInvalid();
        try {
            nativeReset(this.mStatement, false);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public void clearBindings() {
        throwIfInvalid();
        try {
            nativeClearBindings(this.mStatement);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public int getParameterCount() {
        throwIfInvalid();
        try {
            return nativeBindParameterCount(this.mStatement);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public int getParameterIndex(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        throwIfInvalid();
        try {
            return nativeBindParameterIndex(this.mStatement, str);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public java.lang.String getParameterName(int i) {
        throwIfInvalid();
        try {
            return nativeBindParameterName(this.mStatement, i);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public void bindBlob(int i, byte[] bArr) {
        java.util.Objects.requireNonNull(bArr);
        throwIfInvalid();
        try {
            nativeBindBlob(this.mStatement, i, bArr, 0, bArr.length);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public void bindBlob(int i, byte[] bArr, int i2, int i3) {
        java.util.Objects.requireNonNull(bArr);
        throwIfInvalid();
        throwIfInvalidBounds(bArr.length, i2, i3);
        try {
            nativeBindBlob(this.mStatement, i, bArr, i2, i3);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public void bindDouble(int i, double d) {
        throwIfInvalid();
        try {
            nativeBindDouble(this.mStatement, i, d);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public void bindInt(int i, int i2) {
        throwIfInvalid();
        try {
            nativeBindInt(this.mStatement, i, i2);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public void bindLong(int i, long j) {
        throwIfInvalid();
        try {
            nativeBindLong(this.mStatement, i, j);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public void bindNull(int i) {
        throwIfInvalid();
        try {
            nativeBindNull(this.mStatement, i);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public void bindText(int i, java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        throwIfInvalid();
        try {
            nativeBindText(this.mStatement, i, str);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public int getResultColumnCount() {
        throwIfInvalid();
        try {
            return nativeColumnCount(this.mStatement);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public int getColumnType(int i) {
        throwIfInvalid();
        try {
            return nativeColumnType(this.mStatement, i);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public java.lang.String getColumnName(int i) {
        throwIfInvalid();
        try {
            return nativeColumnName(this.mStatement, i);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public int getColumnLength(int i) {
        throwIfInvalid();
        try {
            return nativeColumnBytes(this.mStatement, i);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public byte[] getColumnBlob(int i) {
        throwIfInvalid();
        try {
            return nativeColumnBlob(this.mStatement, i);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public int readColumnBlob(int i, byte[] bArr, int i2, int i3, int i4) {
        java.util.Objects.requireNonNull(bArr);
        throwIfInvalid();
        throwIfInvalidBounds(bArr.length, i2, i3);
        try {
            return nativeColumnBuffer(this.mStatement, i, bArr, i2, i3, i4);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public double getColumnDouble(int i) {
        throwIfInvalid();
        try {
            return nativeColumnDouble(this.mStatement, i);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public int getColumnInt(int i) {
        throwIfInvalid();
        try {
            return nativeColumnInt(this.mStatement, i);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public long getColumnLong(int i) {
        throwIfInvalid();
        try {
            return nativeColumnLong(this.mStatement, i);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public java.lang.String getColumnText(int i) {
        throwIfInvalid();
        try {
            return nativeColumnText(this.mStatement, i);
        } finally {
            java.lang.ref.Reference.reachabilityFence(this);
        }
    }

    public java.lang.String toString() {
        if (isOpen()) {
            return "SQLiteRawStatement: " + this.mSql;
        }
        return "SQLiteRawStatement: (closed) " + this.mSql;
    }
}
