package android.database.sqlite;

/* loaded from: classes.dex */
public final class SQLiteSession {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int TRANSACTION_MODE_DEFERRED = 0;
    public static final int TRANSACTION_MODE_EXCLUSIVE = 2;
    public static final int TRANSACTION_MODE_IMMEDIATE = 1;
    private android.database.sqlite.SQLiteConnection mConnection;
    private int mConnectionFlags;
    private final android.database.sqlite.SQLiteConnectionPool mConnectionPool;
    private int mConnectionUseCount;
    private final java.util.ArrayDeque<java.io.Closeable> mOpenDependents = new java.util.ArrayDeque<>();
    private android.database.sqlite.SQLiteSession.Transaction mTransactionPool;
    private android.database.sqlite.SQLiteSession.Transaction mTransactionStack;

    public SQLiteSession(android.database.sqlite.SQLiteConnectionPool sQLiteConnectionPool) {
        if (sQLiteConnectionPool == null) {
            throw new java.lang.IllegalArgumentException("connectionPool must not be null");
        }
        this.mConnectionPool = sQLiteConnectionPool;
    }

    public boolean hasTransaction() {
        return this.mTransactionStack != null;
    }

    public boolean hasNestedTransaction() {
        return (this.mTransactionStack == null || this.mTransactionStack.mParent == null) ? false : true;
    }

    public boolean hasConnection() {
        return this.mConnection != null;
    }

    public void beginTransaction(int i, android.database.sqlite.SQLiteTransactionListener sQLiteTransactionListener, int i2, android.os.CancellationSignal cancellationSignal) {
        throwIfTransactionMarkedSuccessful();
        beginTransactionUnchecked(i, sQLiteTransactionListener, i2, cancellationSignal);
    }

    private void beginTransactionUnchecked(int i, android.database.sqlite.SQLiteTransactionListener sQLiteTransactionListener, int i2, android.os.CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if (this.mTransactionStack == null) {
            acquireConnection(null, i2, cancellationSignal);
        }
        try {
            if (this.mTransactionStack == null) {
                switch (i) {
                    case 0:
                        this.mConnection.execute("BEGIN DEFERRED;", null, cancellationSignal);
                        break;
                    case 1:
                        this.mConnection.execute("BEGIN IMMEDIATE;", null, cancellationSignal);
                        break;
                    case 2:
                        this.mConnection.execute("BEGIN EXCLUSIVE;", null, cancellationSignal);
                        break;
                    default:
                        this.mConnection.execute("BEGIN;", null, cancellationSignal);
                        break;
                }
            }
            if (sQLiteTransactionListener != null) {
                try {
                    sQLiteTransactionListener.onBegin();
                } catch (java.lang.RuntimeException e) {
                    if (this.mTransactionStack == null) {
                        this.mConnection.execute("ROLLBACK;", null, cancellationSignal);
                    }
                    throw e;
                }
            }
            android.database.sqlite.SQLiteSession.Transaction obtainTransaction = obtainTransaction(i, sQLiteTransactionListener);
            obtainTransaction.mParent = this.mTransactionStack;
            this.mTransactionStack = obtainTransaction;
        } finally {
            if (this.mTransactionStack == null) {
                releaseConnection();
            }
        }
    }

    public void setTransactionSuccessful() {
        throwIfNoTransaction();
        throwIfTransactionMarkedSuccessful();
        this.mTransactionStack.mMarkedSuccessful = true;
        closeOpenDependents();
    }

    public void endTransaction(android.os.CancellationSignal cancellationSignal) {
        throwIfNoTransaction();
        endTransactionUnchecked(cancellationSignal, false);
    }

    private void endTransactionUnchecked(android.os.CancellationSignal cancellationSignal, boolean z) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        android.database.sqlite.SQLiteSession.Transaction transaction = this.mTransactionStack;
        boolean z2 = false;
        boolean z3 = (transaction.mMarkedSuccessful || z) && !transaction.mChildFailed;
        android.database.sqlite.SQLiteTransactionListener sQLiteTransactionListener = transaction.mListener;
        if (sQLiteTransactionListener != null) {
            try {
                if (z3) {
                    sQLiteTransactionListener.onCommit();
                } else {
                    sQLiteTransactionListener.onRollback();
                }
            } catch (java.lang.RuntimeException e) {
                e = e;
            }
        }
        z2 = z3;
        e = null;
        this.mTransactionStack = transaction.mParent;
        recycleTransaction(transaction);
        if (this.mTransactionStack != null) {
            if (!z2) {
                this.mTransactionStack.mChildFailed = true;
            }
        } else {
            closeOpenDependents();
            try {
                if (z2) {
                    this.mConnection.execute("COMMIT;", null, cancellationSignal);
                } else {
                    this.mConnection.execute("ROLLBACK;", null, cancellationSignal);
                }
            } finally {
                releaseConnection();
            }
        }
        if (e != null) {
            throw e;
        }
    }

    public boolean yieldTransaction(long j, boolean z, android.os.CancellationSignal cancellationSignal) {
        if (z) {
            throwIfNoTransaction();
            throwIfTransactionMarkedSuccessful();
            throwIfNestedTransaction();
        } else if (this.mTransactionStack == null || this.mTransactionStack.mMarkedSuccessful || this.mTransactionStack.mParent != null) {
            return false;
        }
        if (this.mTransactionStack.mChildFailed) {
            return false;
        }
        return yieldTransactionUnchecked(j, cancellationSignal);
    }

    private boolean yieldTransactionUnchecked(long j, android.os.CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if (!this.mConnectionPool.shouldYieldConnection(this.mConnection, this.mConnectionFlags)) {
            return false;
        }
        int i = this.mTransactionStack.mMode;
        android.database.sqlite.SQLiteTransactionListener sQLiteTransactionListener = this.mTransactionStack.mListener;
        int i2 = this.mConnectionFlags;
        endTransactionUnchecked(cancellationSignal, true);
        if (j > 0) {
            try {
                java.lang.Thread.sleep(j);
            } catch (java.lang.InterruptedException e) {
            }
        }
        beginTransactionUnchecked(i, sQLiteTransactionListener, i2, cancellationSignal);
        return true;
    }

    public void prepare(java.lang.String str, int i, android.os.CancellationSignal cancellationSignal, android.database.sqlite.SQLiteStatementInfo sQLiteStatementInfo) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            this.mConnection.prepare(str, sQLiteStatementInfo);
        } finally {
            releaseConnection();
        }
    }

    public void execute(java.lang.String str, java.lang.Object[] objArr, int i, android.os.CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        if (executeSpecial(str, objArr, i, cancellationSignal)) {
            return;
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            this.mConnection.execute(str, objArr, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    public long executeForLong(java.lang.String str, java.lang.Object[] objArr, int i, android.os.CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        if (executeSpecial(str, objArr, i, cancellationSignal)) {
            return 0L;
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            return this.mConnection.executeForLong(str, objArr, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    public java.lang.String executeForString(java.lang.String str, java.lang.Object[] objArr, int i, android.os.CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        if (executeSpecial(str, objArr, i, cancellationSignal)) {
            return null;
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            return this.mConnection.executeForString(str, objArr, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    public android.os.ParcelFileDescriptor executeForBlobFileDescriptor(java.lang.String str, java.lang.Object[] objArr, int i, android.os.CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        if (executeSpecial(str, objArr, i, cancellationSignal)) {
            return null;
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            return this.mConnection.executeForBlobFileDescriptor(str, objArr, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    public int executeForChangedRowCount(java.lang.String str, java.lang.Object[] objArr, int i, android.os.CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        if (executeSpecial(str, objArr, i, cancellationSignal)) {
            return 0;
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            return this.mConnection.executeForChangedRowCount(str, objArr, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    public long executeForLastInsertedRowId(java.lang.String str, java.lang.Object[] objArr, int i, android.os.CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        if (executeSpecial(str, objArr, i, cancellationSignal)) {
            return 0L;
        }
        acquireConnection(str, i, cancellationSignal);
        try {
            return this.mConnection.executeForLastInsertedRowId(str, objArr, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    public int executeForCursorWindow(java.lang.String str, java.lang.Object[] objArr, android.database.CursorWindow cursorWindow, int i, int i2, boolean z, int i3, android.os.CancellationSignal cancellationSignal) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("sql must not be null.");
        }
        if (cursorWindow == null) {
            throw new java.lang.IllegalArgumentException("window must not be null.");
        }
        if (executeSpecial(str, objArr, i3, cancellationSignal)) {
            cursorWindow.clear();
            return 0;
        }
        acquireConnection(str, i3, cancellationSignal);
        try {
            return this.mConnection.executeForCursorWindow(str, objArr, cursorWindow, i, i2, z, cancellationSignal);
        } finally {
            releaseConnection();
        }
    }

    private boolean executeSpecial(java.lang.String str, java.lang.Object[] objArr, int i, android.os.CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        switch (android.database.DatabaseUtils.getSqlStatementType(str)) {
            case 4:
                beginTransaction(2, null, i, cancellationSignal);
                break;
            case 5:
                setTransactionSuccessful();
                endTransaction(cancellationSignal);
                break;
            case 6:
                endTransaction(cancellationSignal);
                break;
        }
        return true;
    }

    private void acquireConnection(java.lang.String str, int i, android.os.CancellationSignal cancellationSignal) {
        if (this.mConnection == null) {
            this.mConnection = this.mConnectionPool.acquireConnection(str, i, cancellationSignal);
            this.mConnectionFlags = i;
        }
        this.mConnectionUseCount++;
    }

    private void releaseConnection() {
        int i = this.mConnectionUseCount - 1;
        this.mConnectionUseCount = i;
        if (i == 0) {
            try {
                this.mConnectionPool.releaseConnection(this.mConnection);
            } finally {
                this.mConnection = null;
            }
        }
    }

    android.database.sqlite.SQLiteConnection.PreparedStatement acquirePersistentStatement(java.lang.String str, java.io.Closeable closeable) {
        throwIfNoTransaction();
        throwIfTransactionMarkedSuccessful();
        this.mOpenDependents.addFirst(closeable);
        try {
            return this.mConnection.acquirePersistentStatement(str);
        } catch (java.lang.Throwable th) {
            this.mOpenDependents.remove(closeable);
            throw th;
        }
    }

    void releasePersistentStatement(android.database.sqlite.SQLiteConnection.PreparedStatement preparedStatement, java.io.Closeable closeable) {
        this.mConnection.releasePreparedStatement(preparedStatement);
        this.mOpenDependents.remove(closeable);
    }

    void closeOpenDependents() {
        while (this.mOpenDependents.size() > 0) {
            java.io.Closeable pollFirst = this.mOpenDependents.pollFirst();
            if (pollFirst != null) {
                try {
                    pollFirst.close();
                } catch (java.io.IOException e) {
                }
            }
        }
    }

    long getLastInsertRowId() {
        throwIfNoTransaction();
        return this.mConnection.getLastInsertRowId();
    }

    long getLastChangedRowCount() {
        throwIfNoTransaction();
        return this.mConnection.getLastChangedRowCount();
    }

    long getTotalChangedRowCount() {
        throwIfNoTransaction();
        return this.mConnection.getTotalChangedRowCount();
    }

    void throwIfNoTransaction() {
        if (this.mTransactionStack == null) {
            throw new java.lang.IllegalStateException("Cannot perform this operation because there is no current transaction.");
        }
    }

    private void throwIfTransactionMarkedSuccessful() {
        if (this.mTransactionStack != null && this.mTransactionStack.mMarkedSuccessful) {
            throw new java.lang.IllegalStateException("Cannot perform this operation because the transaction has already been marked successful.  The only thing you can do now is call endTransaction().");
        }
    }

    private void throwIfNestedTransaction() {
        if (hasNestedTransaction()) {
            throw new java.lang.IllegalStateException("Cannot perform this operation because a nested transaction is in progress.");
        }
    }

    private android.database.sqlite.SQLiteSession.Transaction obtainTransaction(int i, android.database.sqlite.SQLiteTransactionListener sQLiteTransactionListener) {
        android.database.sqlite.SQLiteSession.Transaction transaction = this.mTransactionPool;
        if (transaction != null) {
            this.mTransactionPool = transaction.mParent;
            transaction.mParent = null;
            transaction.mMarkedSuccessful = false;
            transaction.mChildFailed = false;
        } else {
            transaction = new android.database.sqlite.SQLiteSession.Transaction();
        }
        transaction.mMode = i;
        transaction.mListener = sQLiteTransactionListener;
        return transaction;
    }

    private void recycleTransaction(android.database.sqlite.SQLiteSession.Transaction transaction) {
        transaction.mParent = this.mTransactionPool;
        transaction.mListener = null;
        this.mTransactionPool = transaction;
    }

    private static final class Transaction {
        public boolean mChildFailed;
        public android.database.sqlite.SQLiteTransactionListener mListener;
        public boolean mMarkedSuccessful;
        public int mMode;
        public android.database.sqlite.SQLiteSession.Transaction mParent;

        private Transaction() {
        }
    }
}
