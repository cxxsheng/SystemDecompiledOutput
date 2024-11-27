package android.database;

/* loaded from: classes.dex */
public final class BulkCursorToCursorAdaptor extends android.database.AbstractWindowedCursor {
    private static final java.lang.String TAG = "BulkCursor";
    private android.database.IBulkCursor mBulkCursor;
    private java.lang.String[] mColumns;
    private int mCount;
    private android.database.AbstractCursor.SelfContentObserver mObserverBridge = new android.database.AbstractCursor.SelfContentObserver(this);
    private boolean mWantsAllOnMoveCalls;

    public void initialize(android.database.BulkCursorDescriptor bulkCursorDescriptor) {
        this.mBulkCursor = bulkCursorDescriptor.cursor;
        this.mColumns = bulkCursorDescriptor.columnNames;
        this.mWantsAllOnMoveCalls = bulkCursorDescriptor.wantsAllOnMoveCalls;
        this.mCount = bulkCursorDescriptor.count;
        if (bulkCursorDescriptor.window != null) {
            setWindow(bulkCursorDescriptor.window);
        }
    }

    public android.database.IContentObserver getObserver() {
        return this.mObserverBridge.getContentObserver();
    }

    private void throwIfCursorIsClosed() {
        if (this.mBulkCursor == null) {
            throw new android.database.StaleDataException("Attempted to access a cursor after it has been closed.");
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getCount() {
        throwIfCursorIsClosed();
        return this.mCount;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0038 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0039 A[RETURN] */
    @Override // android.database.AbstractCursor, android.database.CrossProcessCursor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onMove(int i, int i2) {
        throwIfCursorIsClosed();
        try {
            if (this.mWindow != null && i2 >= this.mWindow.getStartPosition() && i2 < this.mWindow.getStartPosition() + this.mWindow.getNumRows()) {
                if (this.mWantsAllOnMoveCalls) {
                    this.mBulkCursor.onMove(i2);
                }
                if (this.mWindow != null) {
                    return false;
                }
                return true;
            }
            setWindow(this.mBulkCursor.getWindow(i2));
            if (this.mWindow != null) {
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Unable to get window because the remote process is dead");
            return false;
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void deactivate() {
        super.deactivate();
        if (this.mBulkCursor != null) {
            try {
                this.mBulkCursor.deactivate();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Remote process exception when deactivating");
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        super.close();
        if (this.mBulkCursor != null) {
            try {
                try {
                    this.mBulkCursor.close();
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(TAG, "Remote process exception when closing");
                }
            } finally {
                this.mBulkCursor = null;
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean requery() {
        throwIfCursorIsClosed();
        try {
            this.mCount = this.mBulkCursor.requery(getObserver());
            if (this.mCount != -1) {
                this.mPos = -1;
                closeWindow();
                super.requery();
                return true;
            }
            deactivate();
            return false;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Unable to requery because the remote process exception " + e.getMessage());
            deactivate();
            return false;
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public java.lang.String[] getColumnNames() {
        throwIfCursorIsClosed();
        return this.mColumns;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public android.os.Bundle getExtras() {
        throwIfCursorIsClosed();
        try {
            return this.mBulkCursor.getExtras();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public android.os.Bundle respond(android.os.Bundle bundle) {
        throwIfCursorIsClosed();
        try {
            return this.mBulkCursor.respond(bundle);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "respond() threw RemoteException, returning an empty bundle.", e);
            return android.os.Bundle.EMPTY;
        }
    }
}
