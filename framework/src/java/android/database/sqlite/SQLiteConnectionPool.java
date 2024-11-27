package android.database.sqlite;

/* loaded from: classes.dex */
public final class SQLiteConnectionPool implements java.io.Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CONNECTION_FLAG_INTERACTIVE = 4;
    public static final int CONNECTION_FLAG_PRIMARY_CONNECTION_AFFINITY = 2;
    public static final int CONNECTION_FLAG_READ_ONLY = 1;
    private static final long CONNECTION_POOL_BUSY_MILLIS = 30000;
    private static final java.lang.String TAG = "SQLiteConnectionPool";
    private android.database.sqlite.SQLiteConnection mAvailablePrimaryConnection;
    private final android.database.sqlite.SQLiteDatabaseConfiguration mConfiguration;
    private android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter mConnectionWaiterPool;
    private android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter mConnectionWaiterQueue;
    private android.database.sqlite.SQLiteConnectionPool.IdleConnectionHandler mIdleConnectionHandler;
    private boolean mIsOpen;
    private int mMaxConnectionPoolSize;
    private int mNextConnectionId;
    private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.concurrent.atomic.AtomicBoolean mConnectionLeaked = new java.util.concurrent.atomic.AtomicBoolean();
    private final java.util.ArrayList<android.database.sqlite.SQLiteConnection> mAvailableNonPrimaryConnections = new java.util.ArrayList<>();
    public int mTotalPrepareStatementCacheMiss = 0;
    public int mTotalPrepareStatements = 0;
    private long mDatabaseSeqNum = 1;
    private final java.util.concurrent.atomic.AtomicLong mTotalStatementsTime = new java.util.concurrent.atomic.AtomicLong(0);
    private final java.util.concurrent.atomic.AtomicLong mTotalStatementsCount = new java.util.concurrent.atomic.AtomicLong(0);
    private final java.util.WeakHashMap<android.database.sqlite.SQLiteConnection, android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus> mAcquiredConnections = new java.util.WeakHashMap<>();

    enum AcquiredConnectionStatus {
        NORMAL,
        RECONFIGURE,
        DISCARD
    }

    private SQLiteConnectionPool(android.database.sqlite.SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        this.mConfiguration = new android.database.sqlite.SQLiteDatabaseConfiguration(sQLiteDatabaseConfiguration);
        setMaxConnectionPoolSizeLocked();
        if (this.mConfiguration.idleConnectionTimeoutMs != Long.MAX_VALUE) {
            setupIdleConnectionHandler(android.os.Looper.getMainLooper(), this.mConfiguration.idleConnectionTimeoutMs, null);
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    public static android.database.sqlite.SQLiteConnectionPool open(android.database.sqlite.SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        if (sQLiteDatabaseConfiguration == null) {
            throw new java.lang.IllegalArgumentException("configuration must not be null.");
        }
        android.database.sqlite.SQLiteConnectionPool sQLiteConnectionPool = new android.database.sqlite.SQLiteConnectionPool(sQLiteDatabaseConfiguration);
        sQLiteConnectionPool.open();
        return sQLiteConnectionPool;
    }

    private void open() {
        this.mAvailablePrimaryConnection = openConnectionLocked(this.mConfiguration, true);
        synchronized (this.mLock) {
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionReleased(this.mAvailablePrimaryConnection);
            }
        }
        this.mIsOpen = true;
        this.mCloseGuard.open("SQLiteConnectionPool.close");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        dispose(false);
    }

    private void dispose(boolean z) {
        if (this.mCloseGuard != null) {
            if (z) {
                this.mCloseGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if (!z) {
            synchronized (this.mLock) {
                throwIfClosedLocked();
                this.mIsOpen = false;
                closeAvailableConnectionsAndLogExceptionsLocked();
                int size = this.mAcquiredConnections.size();
                if (size != 0) {
                    android.util.Log.i(TAG, "The connection pool for " + this.mConfiguration.label + " has been closed but there are still " + size + " connections in use.  They will be closed as they are released back to the pool.");
                }
                wakeConnectionWaitersLocked();
            }
        }
    }

    public void reconfigure(android.database.sqlite.SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        if (sQLiteDatabaseConfiguration == null) {
            throw new java.lang.IllegalArgumentException("configuration must not be null.");
        }
        synchronized (this.mLock) {
            throwIfClosedLocked();
            boolean equalsIgnoreCase = this.mConfiguration.resolveJournalMode().equalsIgnoreCase(android.database.sqlite.SQLiteDatabase.JOURNAL_MODE_WAL) ^ sQLiteDatabaseConfiguration.resolveJournalMode().equalsIgnoreCase(android.database.sqlite.SQLiteDatabase.JOURNAL_MODE_WAL);
            if (equalsIgnoreCase) {
                if (!this.mAcquiredConnections.isEmpty()) {
                    throw new java.lang.IllegalStateException("Write Ahead Logging (WAL) mode cannot be enabled or disabled while there are transactions in progress.  Finish all transactions and release all active database connections first.");
                }
                closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
            }
            if ((sQLiteDatabaseConfiguration.foreignKeyConstraintsEnabled != this.mConfiguration.foreignKeyConstraintsEnabled) && !this.mAcquiredConnections.isEmpty()) {
                throw new java.lang.IllegalStateException("Foreign Key Constraints cannot be enabled or disabled while there are transactions in progress.  Finish all transactions and release all active database connections first.");
            }
            if (!((this.mConfiguration.openFlags ^ sQLiteDatabaseConfiguration.openFlags) == Integer.MIN_VALUE) && this.mConfiguration.openFlags != sQLiteDatabaseConfiguration.openFlags) {
                if (equalsIgnoreCase) {
                    closeAvailableConnectionsAndLogExceptionsLocked();
                }
                android.database.sqlite.SQLiteConnection openConnectionLocked = openConnectionLocked(sQLiteDatabaseConfiguration, true);
                closeAvailableConnectionsAndLogExceptionsLocked();
                discardAcquiredConnectionsLocked();
                this.mAvailablePrimaryConnection = openConnectionLocked;
                this.mConfiguration.updateParametersFrom(sQLiteDatabaseConfiguration);
                setMaxConnectionPoolSizeLocked();
            } else {
                this.mConfiguration.updateParametersFrom(sQLiteDatabaseConfiguration);
                setMaxConnectionPoolSizeLocked();
                closeExcessConnectionsAndLogExceptionsLocked();
                reconfigureAllConnectionsLocked();
            }
            wakeConnectionWaitersLocked();
        }
    }

    public android.database.sqlite.SQLiteConnection acquireConnection(java.lang.String str, int i, android.os.CancellationSignal cancellationSignal) {
        android.database.sqlite.SQLiteConnection waitForConnection = waitForConnection(str, i, cancellationSignal);
        synchronized (this.mLock) {
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionAcquired(waitForConnection);
            }
        }
        return waitForConnection;
    }

    public void releaseConnection(android.database.sqlite.SQLiteConnection sQLiteConnection) {
        synchronized (this.mLock) {
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionReleased(sQLiteConnection);
            }
            android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus remove = this.mAcquiredConnections.remove(sQLiteConnection);
            if (remove == null) {
                throw new java.lang.IllegalStateException("Cannot perform this operation because the specified connection was not acquired from this pool or has already been released.");
            }
            if (!this.mIsOpen) {
                closeConnectionAndLogExceptionsLocked(sQLiteConnection);
            } else if (sQLiteConnection.isPrimaryConnection()) {
                if (recycleConnectionLocked(sQLiteConnection, remove)) {
                    this.mAvailablePrimaryConnection = sQLiteConnection;
                }
                wakeConnectionWaitersLocked();
            } else if (this.mAvailableNonPrimaryConnections.size() >= this.mMaxConnectionPoolSize) {
                closeConnectionAndLogExceptionsLocked(sQLiteConnection);
            } else {
                if (recycleConnectionLocked(sQLiteConnection, remove)) {
                    this.mAvailableNonPrimaryConnections.add(sQLiteConnection);
                }
                wakeConnectionWaitersLocked();
            }
        }
    }

    private boolean recycleConnectionLocked(android.database.sqlite.SQLiteConnection sQLiteConnection, android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus acquiredConnectionStatus) {
        if (acquiredConnectionStatus == android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus.RECONFIGURE) {
            try {
                sQLiteConnection.reconfigure(this.mConfiguration);
            } catch (java.lang.RuntimeException e) {
                android.util.Log.e(TAG, "Failed to reconfigure released connection, closing it: " + sQLiteConnection, e);
                acquiredConnectionStatus = android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus.DISCARD;
            }
        }
        if (acquiredConnectionStatus == android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus.DISCARD) {
            closeConnectionAndLogExceptionsLocked(sQLiteConnection);
            return false;
        }
        return true;
    }

    public boolean hasAnyAvailableNonPrimaryConnection() {
        return this.mAvailableNonPrimaryConnections.size() > 0;
    }

    public boolean shouldYieldConnection(android.database.sqlite.SQLiteConnection sQLiteConnection, int i) {
        synchronized (this.mLock) {
            if (!this.mAcquiredConnections.containsKey(sQLiteConnection)) {
                throw new java.lang.IllegalStateException("Cannot perform this operation because the specified connection was not acquired from this pool or has already been released.");
            }
            if (!this.mIsOpen) {
                return false;
            }
            return isSessionBlockingImportantConnectionWaitersLocked(sQLiteConnection.isPrimaryConnection(), i);
        }
    }

    public void collectDbStats(java.util.ArrayList<android.database.sqlite.SQLiteDebug.DbStats> arrayList) {
        synchronized (this.mLock) {
            if (this.mAvailablePrimaryConnection != null) {
                this.mAvailablePrimaryConnection.collectDbStats(arrayList);
            }
            java.util.Iterator<android.database.sqlite.SQLiteConnection> it = this.mAvailableNonPrimaryConnections.iterator();
            while (it.hasNext()) {
                it.next().collectDbStats(arrayList);
            }
            java.util.Iterator<android.database.sqlite.SQLiteConnection> it2 = this.mAcquiredConnections.keySet().iterator();
            while (it2.hasNext()) {
                it2.next().collectDbStatsUnsafe(arrayList);
            }
            arrayList.add(new android.database.sqlite.SQLiteDebug.DbStats(this.mConfiguration.path, 0L, 0L, 0, this.mTotalPrepareStatements - this.mTotalPrepareStatementCacheMiss, this.mTotalPrepareStatementCacheMiss, this.mTotalPrepareStatements, true));
        }
    }

    private android.database.sqlite.SQLiteConnection openConnectionLocked(android.database.sqlite.SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, boolean z) {
        int i = this.mNextConnectionId;
        this.mNextConnectionId = i + 1;
        return android.database.sqlite.SQLiteConnection.open(this, sQLiteDatabaseConfiguration, i, z);
    }

    void onConnectionLeaked() {
        android.util.Log.w(TAG, "A SQLiteConnection object for database '" + this.mConfiguration.label + "' was leaked!  Please fix your application to end transactions in progress properly and to close the database when it is no longer needed.");
        this.mConnectionLeaked.set(true);
    }

    void onStatementExecuted(long j) {
        this.mTotalStatementsTime.addAndGet(j);
        this.mTotalStatementsCount.incrementAndGet();
    }

    private void closeAvailableConnectionsAndLogExceptionsLocked() {
        closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
        if (this.mAvailablePrimaryConnection != null) {
            closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
            this.mAvailablePrimaryConnection = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean closeAvailableConnectionLocked(int i) {
        for (int size = this.mAvailableNonPrimaryConnections.size() - 1; size >= 0; size--) {
            android.database.sqlite.SQLiteConnection sQLiteConnection = this.mAvailableNonPrimaryConnections.get(size);
            if (sQLiteConnection.getConnectionId() == i) {
                closeConnectionAndLogExceptionsLocked(sQLiteConnection);
                this.mAvailableNonPrimaryConnections.remove(size);
                return true;
            }
        }
        if (this.mAvailablePrimaryConnection != null && this.mAvailablePrimaryConnection.getConnectionId() == i) {
            closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
            this.mAvailablePrimaryConnection = null;
            return true;
        }
        return false;
    }

    private void closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked() {
        int size = this.mAvailableNonPrimaryConnections.size();
        for (int i = 0; i < size; i++) {
            closeConnectionAndLogExceptionsLocked(this.mAvailableNonPrimaryConnections.get(i));
        }
        this.mAvailableNonPrimaryConnections.clear();
    }

    void closeAvailableNonPrimaryConnectionsAndLogExceptions() {
        synchronized (this.mLock) {
            closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
        }
    }

    private void closeExcessConnectionsAndLogExceptionsLocked() {
        int size = this.mAvailableNonPrimaryConnections.size();
        while (true) {
            int i = size - 1;
            if (size > this.mMaxConnectionPoolSize - 1) {
                closeConnectionAndLogExceptionsLocked(this.mAvailableNonPrimaryConnections.remove(i));
                size = i;
            } else {
                return;
            }
        }
    }

    private void closeConnectionAndLogExceptionsLocked(android.database.sqlite.SQLiteConnection sQLiteConnection) {
        try {
            sQLiteConnection.close();
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionClosed(sQLiteConnection);
            }
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Failed to close connection, its fate is now in the hands of the merciful GC: " + sQLiteConnection, e);
        }
    }

    private void discardAcquiredConnectionsLocked() {
        markAcquiredConnectionsLocked(android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus.DISCARD);
    }

    private void reconfigureAllConnectionsLocked() {
        if (this.mAvailablePrimaryConnection != null) {
            try {
                this.mAvailablePrimaryConnection.reconfigure(this.mConfiguration);
            } catch (java.lang.RuntimeException e) {
                android.util.Log.e(TAG, "Failed to reconfigure available primary connection, closing it: " + this.mAvailablePrimaryConnection, e);
                closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
                this.mAvailablePrimaryConnection = null;
            }
        }
        int size = this.mAvailableNonPrimaryConnections.size();
        int i = 0;
        while (i < size) {
            android.database.sqlite.SQLiteConnection sQLiteConnection = this.mAvailableNonPrimaryConnections.get(i);
            try {
                sQLiteConnection.reconfigure(this.mConfiguration);
            } catch (java.lang.RuntimeException e2) {
                android.util.Log.e(TAG, "Failed to reconfigure available non-primary connection, closing it: " + sQLiteConnection, e2);
                closeConnectionAndLogExceptionsLocked(sQLiteConnection);
                this.mAvailableNonPrimaryConnections.remove(i);
                size--;
                i--;
            }
            i++;
        }
        markAcquiredConnectionsLocked(android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus.RECONFIGURE);
    }

    private void markAcquiredConnectionsLocked(android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus acquiredConnectionStatus) {
        if (!this.mAcquiredConnections.isEmpty()) {
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mAcquiredConnections.size());
            for (java.util.Map.Entry<android.database.sqlite.SQLiteConnection, android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus> entry : this.mAcquiredConnections.entrySet()) {
                android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus value = entry.getValue();
                if (acquiredConnectionStatus != value && value != android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus.DISCARD) {
                    arrayList.add(entry.getKey());
                }
            }
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mAcquiredConnections.put((android.database.sqlite.SQLiteConnection) arrayList.get(i), acquiredConnectionStatus);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x00c2 A[DONT_GENERATE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.database.sqlite.SQLiteConnection waitForConnection(java.lang.String str, int i, android.os.CancellationSignal cancellationSignal) {
        android.database.sqlite.SQLiteConnection sQLiteConnection;
        android.database.sqlite.SQLiteConnection sQLiteConnection2;
        java.lang.RuntimeException runtimeException;
        long j;
        boolean z = (i & 2) != 0;
        synchronized (this.mLock) {
            throwIfClosedLocked();
            if (cancellationSignal != null) {
                cancellationSignal.throwIfCanceled();
            }
            if (z) {
                sQLiteConnection = null;
            } else {
                sQLiteConnection = tryAcquireNonPrimaryConnectionLocked(str, i);
            }
            if (sQLiteConnection == null) {
                sQLiteConnection = tryAcquirePrimaryConnectionLocked(i);
            }
            if (sQLiteConnection != null) {
                return sQLiteConnection;
            }
            int priority = getPriority(i);
            final android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter obtainConnectionWaiterLocked = obtainConnectionWaiterLocked(java.lang.Thread.currentThread(), android.os.SystemClock.uptimeMillis(), priority, z, str, i);
            android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter connectionWaiter = this.mConnectionWaiterQueue;
            android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter connectionWaiter2 = null;
            while (true) {
                if (connectionWaiter == null) {
                    break;
                }
                if (priority > connectionWaiter.mPriority) {
                    obtainConnectionWaiterLocked.mNext = connectionWaiter;
                    break;
                }
                connectionWaiter2 = connectionWaiter;
                connectionWaiter = connectionWaiter.mNext;
            }
            if (connectionWaiter2 != null) {
                connectionWaiter2.mNext = obtainConnectionWaiterLocked;
            } else {
                this.mConnectionWaiterQueue = obtainConnectionWaiterLocked;
            }
            final int i2 = obtainConnectionWaiterLocked.mNonce;
            if (cancellationSignal != null) {
                cancellationSignal.setOnCancelListener(new android.os.CancellationSignal.OnCancelListener() { // from class: android.database.sqlite.SQLiteConnectionPool.1
                    @Override // android.os.CancellationSignal.OnCancelListener
                    public void onCancel() {
                        synchronized (android.database.sqlite.SQLiteConnectionPool.this.mLock) {
                            if (obtainConnectionWaiterLocked.mNonce == i2) {
                                android.database.sqlite.SQLiteConnectionPool.this.cancelConnectionWaiterLocked(obtainConnectionWaiterLocked);
                            }
                        }
                    }
                });
            }
            try {
                long j2 = obtainConnectionWaiterLocked.mStartTime + 30000;
                long j3 = 30000;
                while (true) {
                    if (this.mConnectionLeaked.compareAndSet(true, false)) {
                        synchronized (this.mLock) {
                            wakeConnectionWaitersLocked();
                        }
                    }
                    java.util.concurrent.locks.LockSupport.parkNanos(this, j3 * 1000000);
                    java.lang.Thread.interrupted();
                    synchronized (this.mLock) {
                        throwIfClosedLocked();
                        sQLiteConnection2 = obtainConnectionWaiterLocked.mAssignedConnection;
                        runtimeException = obtainConnectionWaiterLocked.mException;
                        if (sQLiteConnection2 != null || runtimeException != null) {
                            break;
                        }
                        long uptimeMillis = android.os.SystemClock.uptimeMillis();
                        if (uptimeMillis >= j2) {
                            logConnectionPoolBusyLocked(uptimeMillis - obtainConnectionWaiterLocked.mStartTime, i);
                            j2 = uptimeMillis + 30000;
                            j = 30000;
                        } else {
                            j = uptimeMillis - j2;
                        }
                    }
                    return sQLiteConnection2;
                    j3 = j;
                }
                recycleConnectionWaiterLocked(obtainConnectionWaiterLocked);
                if (sQLiteConnection2 != null) {
                    return sQLiteConnection2;
                }
                throw runtimeException;
            } finally {
                if (cancellationSignal != null) {
                    cancellationSignal.setOnCancelListener(null);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelConnectionWaiterLocked(android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter connectionWaiter) {
        if (connectionWaiter.mAssignedConnection != null || connectionWaiter.mException != null) {
            return;
        }
        android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter connectionWaiter2 = null;
        for (android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter connectionWaiter3 = this.mConnectionWaiterQueue; connectionWaiter3 != connectionWaiter; connectionWaiter3 = connectionWaiter3.mNext) {
            connectionWaiter2 = connectionWaiter3;
        }
        if (connectionWaiter2 != null) {
            connectionWaiter2.mNext = connectionWaiter.mNext;
        } else {
            this.mConnectionWaiterQueue = connectionWaiter.mNext;
        }
        connectionWaiter.mException = new android.os.OperationCanceledException();
        java.util.concurrent.locks.LockSupport.unpark(connectionWaiter.mThread);
        wakeConnectionWaitersLocked();
    }

    private void logConnectionPoolBusyLocked(long j, int i) {
        int i2;
        java.lang.Thread currentThread = java.lang.Thread.currentThread();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("The connection pool for database '").append(this.mConfiguration.label);
        sb.append("' has been unable to grant a connection to thread ");
        sb.append(currentThread.getId()).append(" (").append(currentThread.getName()).append(") ");
        sb.append("with flags 0x").append(java.lang.Integer.toHexString(i));
        sb.append(" for ").append(j * 0.001f).append(" seconds.\n");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i3 = 0;
        if (this.mAcquiredConnections.isEmpty()) {
            i2 = 0;
        } else {
            java.util.Iterator<android.database.sqlite.SQLiteConnection> it = this.mAcquiredConnections.keySet().iterator();
            i2 = 0;
            while (it.hasNext()) {
                java.lang.String describeCurrentOperationUnsafe = it.next().describeCurrentOperationUnsafe();
                if (describeCurrentOperationUnsafe != null) {
                    arrayList.add(describeCurrentOperationUnsafe);
                    i3++;
                } else {
                    i2++;
                }
            }
        }
        int size = this.mAvailableNonPrimaryConnections.size();
        if (this.mAvailablePrimaryConnection != null) {
            size++;
        }
        sb.append("Connections: ").append(i3).append(" active, ");
        sb.append(i2).append(" idle, ");
        sb.append(size).append(" available.\n");
        if (!arrayList.isEmpty()) {
            sb.append("\nRequests in progress:\n");
            java.util.Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                sb.append("  ").append((java.lang.String) it2.next()).append("\n");
            }
        }
        android.util.Log.w(TAG, sb.toString());
    }

    private void wakeConnectionWaitersLocked() {
        android.database.sqlite.SQLiteConnection sQLiteConnection;
        android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter connectionWaiter = this.mConnectionWaiterQueue;
        android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter connectionWaiter2 = null;
        boolean z = false;
        boolean z2 = false;
        while (connectionWaiter != null) {
            boolean z3 = true;
            if (this.mIsOpen) {
                try {
                    if (!connectionWaiter.mWantPrimaryConnection && !z) {
                        sQLiteConnection = tryAcquireNonPrimaryConnectionLocked(connectionWaiter.mSql, connectionWaiter.mConnectionFlags);
                        if (sQLiteConnection == null) {
                            z = true;
                        }
                    } else {
                        sQLiteConnection = null;
                    }
                    if (sQLiteConnection == null && !z2 && (sQLiteConnection = tryAcquirePrimaryConnectionLocked(connectionWaiter.mConnectionFlags)) == null) {
                        z2 = true;
                    }
                    if (sQLiteConnection != null) {
                        connectionWaiter.mAssignedConnection = sQLiteConnection;
                    } else if (!z || !z2) {
                        z3 = false;
                    } else {
                        return;
                    }
                } catch (java.lang.RuntimeException e) {
                    connectionWaiter.mException = e;
                }
            }
            android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter connectionWaiter3 = connectionWaiter.mNext;
            if (z3) {
                if (connectionWaiter2 != null) {
                    connectionWaiter2.mNext = connectionWaiter3;
                } else {
                    this.mConnectionWaiterQueue = connectionWaiter3;
                }
                connectionWaiter.mNext = null;
                java.util.concurrent.locks.LockSupport.unpark(connectionWaiter.mThread);
            } else {
                connectionWaiter2 = connectionWaiter;
            }
            connectionWaiter = connectionWaiter3;
        }
    }

    private android.database.sqlite.SQLiteConnection tryAcquirePrimaryConnectionLocked(int i) {
        android.database.sqlite.SQLiteConnection sQLiteConnection = this.mAvailablePrimaryConnection;
        if (sQLiteConnection != null) {
            this.mAvailablePrimaryConnection = null;
            finishAcquireConnectionLocked(sQLiteConnection, i);
            return sQLiteConnection;
        }
        java.util.Iterator<android.database.sqlite.SQLiteConnection> it = this.mAcquiredConnections.keySet().iterator();
        while (it.hasNext()) {
            if (it.next().isPrimaryConnection()) {
                return null;
            }
        }
        android.database.sqlite.SQLiteConnection openConnectionLocked = openConnectionLocked(this.mConfiguration, true);
        finishAcquireConnectionLocked(openConnectionLocked, i);
        return openConnectionLocked;
    }

    private android.database.sqlite.SQLiteConnection tryAcquireNonPrimaryConnectionLocked(java.lang.String str, int i) {
        int size = this.mAvailableNonPrimaryConnections.size();
        if (size > 1 && str != null) {
            for (int i2 = 0; i2 < size; i2++) {
                android.database.sqlite.SQLiteConnection sQLiteConnection = this.mAvailableNonPrimaryConnections.get(i2);
                if (sQLiteConnection.isPreparedStatementInCache(str)) {
                    this.mAvailableNonPrimaryConnections.remove(i2);
                    finishAcquireConnectionLocked(sQLiteConnection, i);
                    return sQLiteConnection;
                }
            }
        }
        if (size > 0) {
            android.database.sqlite.SQLiteConnection remove = this.mAvailableNonPrimaryConnections.remove(size - 1);
            finishAcquireConnectionLocked(remove, i);
            return remove;
        }
        int size2 = this.mAcquiredConnections.size();
        if (this.mAvailablePrimaryConnection != null) {
            size2++;
        }
        if (size2 < this.mMaxConnectionPoolSize) {
            android.database.sqlite.SQLiteConnection openConnectionLocked = openConnectionLocked(this.mConfiguration, false);
            finishAcquireConnectionLocked(openConnectionLocked, i);
            return openConnectionLocked;
        }
        return null;
    }

    private void finishAcquireConnectionLocked(android.database.sqlite.SQLiteConnection sQLiteConnection, int i) {
        try {
            sQLiteConnection.setOnlyAllowReadOnlyOperations((i & 1) != 0);
            this.mAcquiredConnections.put(sQLiteConnection, android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus.NORMAL);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Failed to prepare acquired connection for session, closing it: " + sQLiteConnection + ", connectionFlags=" + i);
            closeConnectionAndLogExceptionsLocked(sQLiteConnection);
            throw e;
        }
    }

    private boolean isSessionBlockingImportantConnectionWaitersLocked(boolean z, int i) {
        android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter connectionWaiter = this.mConnectionWaiterQueue;
        if (connectionWaiter != null) {
            int priority = getPriority(i);
            while (priority <= connectionWaiter.mPriority) {
                if (z || !connectionWaiter.mWantPrimaryConnection) {
                    return true;
                }
                connectionWaiter = connectionWaiter.mNext;
                if (connectionWaiter == null) {
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    private static int getPriority(int i) {
        return (i & 4) != 0 ? 1 : 0;
    }

    private void setMaxConnectionPoolSizeLocked() {
        if (this.mConfiguration.resolveJournalMode().equalsIgnoreCase(android.database.sqlite.SQLiteDatabase.JOURNAL_MODE_WAL)) {
            this.mMaxConnectionPoolSize = android.database.sqlite.SQLiteGlobal.getWALConnectionPoolSize();
        } else {
            this.mMaxConnectionPoolSize = 1;
        }
    }

    public void setupIdleConnectionHandler(android.os.Looper looper, long j, java.lang.Runnable runnable) {
        synchronized (this.mLock) {
            this.mIdleConnectionHandler = new android.database.sqlite.SQLiteConnectionPool.IdleConnectionHandler(looper, j, runnable);
        }
    }

    void disableIdleConnectionHandler() {
        synchronized (this.mLock) {
            this.mIdleConnectionHandler = null;
        }
    }

    private void throwIfClosedLocked() {
        if (!this.mIsOpen) {
            throw new java.lang.IllegalStateException("Cannot perform this operation because the connection pool has been closed.");
        }
    }

    private android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter obtainConnectionWaiterLocked(java.lang.Thread thread, long j, int i, boolean z, java.lang.String str, int i2) {
        android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter connectionWaiter = this.mConnectionWaiterPool;
        if (connectionWaiter != null) {
            this.mConnectionWaiterPool = connectionWaiter.mNext;
            connectionWaiter.mNext = null;
        } else {
            connectionWaiter = new android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter();
        }
        connectionWaiter.mThread = thread;
        connectionWaiter.mStartTime = j;
        connectionWaiter.mPriority = i;
        connectionWaiter.mWantPrimaryConnection = z;
        connectionWaiter.mSql = str;
        connectionWaiter.mConnectionFlags = i2;
        return connectionWaiter;
    }

    private void recycleConnectionWaiterLocked(android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter connectionWaiter) {
        connectionWaiter.mNext = this.mConnectionWaiterPool;
        connectionWaiter.mThread = null;
        connectionWaiter.mSql = null;
        connectionWaiter.mAssignedConnection = null;
        connectionWaiter.mException = null;
        connectionWaiter.mNonce++;
        this.mConnectionWaiterPool = connectionWaiter;
    }

    void clearAcquiredConnectionsPreparedStatementCache() {
        synchronized (this.mLock) {
            this.mDatabaseSeqNum++;
            if (!this.mAcquiredConnections.isEmpty()) {
                java.util.Iterator<android.database.sqlite.SQLiteConnection> it = this.mAcquiredConnections.keySet().iterator();
                while (it.hasNext()) {
                    it.next().setDatabaseSeqNum(this.mDatabaseSeqNum);
                }
            }
        }
    }

    public void dump(android.util.Printer printer, boolean z, android.util.ArraySet<java.lang.String> arraySet) {
        android.util.Printer create = android.util.PrefixPrinter.create(printer, "    ");
        synchronized (this.mLock) {
            if (arraySet != null) {
                java.lang.String parent = new java.io.File(this.mConfiguration.path).getParent();
                if (parent != null) {
                    arraySet.add(parent);
                }
            }
            boolean isLegacyCompatibilityWalEnabled = this.mConfiguration.isLegacyCompatibilityWalEnabled();
            printer.println("Connection pool for " + this.mConfiguration.path + ":");
            printer.println("  Open: " + this.mIsOpen);
            printer.println("  Max connections: " + this.mMaxConnectionPoolSize);
            printer.println("  Total execution time (ms): " + this.mTotalStatementsTime);
            printer.println("  Total statements executed: " + this.mTotalStatementsCount);
            if (this.mTotalStatementsCount.get() > 0) {
                printer.println("  Average time per statement (ms): " + (this.mTotalStatementsTime.get() / this.mTotalStatementsCount.get()));
            }
            printer.println("  Configuration: openFlags=" + this.mConfiguration.openFlags + ", isLegacyCompatibilityWalEnabled=" + isLegacyCompatibilityWalEnabled + ", journalMode=" + android.text.TextUtils.emptyIfNull(this.mConfiguration.resolveJournalMode()) + ", syncMode=" + android.text.TextUtils.emptyIfNull(this.mConfiguration.resolveSyncMode()));
            printer.println("  IsReadOnlyDatabase=" + this.mConfiguration.isReadOnlyDatabase());
            if (isLegacyCompatibilityWalEnabled) {
                printer.println("  Compatibility WAL enabled: wal_syncmode=" + android.database.sqlite.SQLiteCompatibilityWalFlags.getWALSyncMode());
            }
            if (this.mConfiguration.isLookasideConfigSet()) {
                printer.println("  Lookaside config: sz=" + this.mConfiguration.lookasideSlotSize + " cnt=" + this.mConfiguration.lookasideSlotCount);
            }
            if (this.mConfiguration.idleConnectionTimeoutMs != Long.MAX_VALUE) {
                printer.println("  Idle connection timeout: " + this.mConfiguration.idleConnectionTimeoutMs);
            }
            printer.println("  Available primary connection:");
            if (this.mAvailablePrimaryConnection != null) {
                this.mAvailablePrimaryConnection.dump(create, z);
            } else {
                create.println("<none>");
            }
            printer.println("  Available non-primary connections:");
            int i = 0;
            if (!this.mAvailableNonPrimaryConnections.isEmpty()) {
                int size = this.mAvailableNonPrimaryConnections.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mAvailableNonPrimaryConnections.get(i2).dump(create, z);
                }
            } else {
                create.println("<none>");
            }
            printer.println("  Acquired connections:");
            if (!this.mAcquiredConnections.isEmpty()) {
                for (java.util.Map.Entry<android.database.sqlite.SQLiteConnection, android.database.sqlite.SQLiteConnectionPool.AcquiredConnectionStatus> entry : this.mAcquiredConnections.entrySet()) {
                    entry.getKey().dumpUnsafe(create, z);
                    create.println("  Status: " + entry.getValue());
                }
            } else {
                create.println("<none>");
            }
            printer.println("  Connection waiters:");
            if (this.mConnectionWaiterQueue != null) {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter connectionWaiter = this.mConnectionWaiterQueue;
                while (connectionWaiter != null) {
                    create.println(i + ": waited for " + ((uptimeMillis - connectionWaiter.mStartTime) * 0.001f) + " ms - thread=" + connectionWaiter.mThread + ", priority=" + connectionWaiter.mPriority + ", sql='" + connectionWaiter.mSql + "'");
                    connectionWaiter = connectionWaiter.mNext;
                    i++;
                }
            } else {
                create.println("<none>");
            }
        }
    }

    @dalvik.annotation.optimization.NeverCompile
    public double getStatementCacheMissRate() {
        if (this.mTotalPrepareStatements == 0) {
            return 0.0d;
        }
        return this.mTotalPrepareStatementCacheMiss / this.mTotalPrepareStatements;
    }

    public long getTotalStatementsTime() {
        return this.mTotalStatementsTime.get();
    }

    public long getTotalStatementsCount() {
        return this.mTotalStatementsCount.get();
    }

    public java.lang.String toString() {
        return "SQLiteConnectionPool: " + this.mConfiguration.path;
    }

    public java.lang.String getPath() {
        return this.mConfiguration.path;
    }

    private static final class ConnectionWaiter {
        public android.database.sqlite.SQLiteConnection mAssignedConnection;
        public int mConnectionFlags;
        public java.lang.RuntimeException mException;
        public android.database.sqlite.SQLiteConnectionPool.ConnectionWaiter mNext;
        public int mNonce;
        public int mPriority;
        public java.lang.String mSql;
        public long mStartTime;
        public java.lang.Thread mThread;
        public boolean mWantPrimaryConnection;

        private ConnectionWaiter() {
        }
    }

    private class IdleConnectionHandler extends android.os.Handler {
        private final java.lang.Runnable mOnAllConnectionsIdle;
        private final long mTimeout;

        IdleConnectionHandler(android.os.Looper looper, long j, java.lang.Runnable runnable) {
            super(looper);
            this.mTimeout = j;
            this.mOnAllConnectionsIdle = runnable;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            synchronized (android.database.sqlite.SQLiteConnectionPool.this.mLock) {
                if (this != android.database.sqlite.SQLiteConnectionPool.this.mIdleConnectionHandler) {
                    return;
                }
                if (android.database.sqlite.SQLiteConnectionPool.this.closeAvailableConnectionLocked(message.what) && android.util.Log.isLoggable(android.database.sqlite.SQLiteConnectionPool.TAG, 3)) {
                    android.util.Log.d(android.database.sqlite.SQLiteConnectionPool.TAG, "Closed idle connection " + android.database.sqlite.SQLiteConnectionPool.this.mConfiguration.label + " " + message.what + " after " + this.mTimeout);
                }
                if (this.mOnAllConnectionsIdle != null) {
                    this.mOnAllConnectionsIdle.run();
                }
            }
        }

        void connectionReleased(android.database.sqlite.SQLiteConnection sQLiteConnection) {
            sendEmptyMessageDelayed(sQLiteConnection.getConnectionId(), this.mTimeout);
        }

        void connectionAcquired(android.database.sqlite.SQLiteConnection sQLiteConnection) {
            removeMessages(sQLiteConnection.getConnectionId());
        }

        void connectionClosed(android.database.sqlite.SQLiteConnection sQLiteConnection) {
            removeMessages(sQLiteConnection.getConnectionId());
        }
    }
}
