package android.database;

/* loaded from: classes.dex */
public abstract class AbstractCursor implements android.database.CrossProcessCursor {
    private static final java.lang.String TAG = "Cursor";

    @java.lang.Deprecated
    protected boolean mClosed;

    @java.lang.Deprecated
    protected android.content.ContentResolver mContentResolver;
    protected java.lang.Long mCurrentRowID;
    private android.net.Uri mNotifyUri;
    private java.util.List<android.net.Uri> mNotifyUris;
    protected int mRowIdColumnIndex;
    private android.database.ContentObserver mSelfObserver;
    private boolean mSelfObserverRegistered;
    protected java.util.HashMap<java.lang.Long, java.util.Map<java.lang.String, java.lang.Object>> mUpdatedRows;
    private final java.lang.Object mSelfObserverLock = new java.lang.Object();
    private final android.database.DataSetObservable mDataSetObservable = new android.database.DataSetObservable();
    private final android.database.ContentObservable mContentObservable = new android.database.ContentObservable();
    private android.os.Bundle mExtras = android.os.Bundle.EMPTY;

    @java.lang.Deprecated
    protected int mPos = -1;
    private final dalvik.system.CloseGuard mCloseGuard = initCloseGuard();

    @Override // android.database.Cursor
    public abstract java.lang.String[] getColumnNames();

    @Override // android.database.Cursor
    public abstract int getCount();

    @Override // android.database.Cursor
    public abstract double getDouble(int i);

    @Override // android.database.Cursor
    public abstract float getFloat(int i);

    @Override // android.database.Cursor
    public abstract int getInt(int i);

    @Override // android.database.Cursor
    public abstract long getLong(int i);

    @Override // android.database.Cursor
    public abstract short getShort(int i);

    @Override // android.database.Cursor
    public abstract java.lang.String getString(int i);

    @Override // android.database.Cursor
    public abstract boolean isNull(int i);

    @Override // android.database.Cursor
    public int getType(int i) {
        return 3;
    }

    @Override // android.database.Cursor
    public byte[] getBlob(int i) {
        throw new java.lang.UnsupportedOperationException("getBlob is not supported");
    }

    @Override // android.database.CrossProcessCursor
    public android.database.CursorWindow getWindow() {
        return null;
    }

    @Override // android.database.Cursor
    public int getColumnCount() {
        return getColumnNames().length;
    }

    @Override // android.database.Cursor
    public void deactivate() {
        onDeactivateOrClose();
    }

    protected void onDeactivateOrClose() {
        if (this.mSelfObserver != null) {
            this.mContentResolver.unregisterContentObserver(this.mSelfObserver);
            this.mSelfObserverRegistered = false;
        }
        this.mDataSetObservable.notifyInvalidated();
    }

    @Override // android.database.Cursor
    public boolean requery() {
        if (this.mSelfObserver != null && !this.mSelfObserverRegistered) {
            int size = this.mNotifyUris.size();
            for (int i = 0; i < size; i++) {
                this.mContentResolver.registerContentObserver(this.mNotifyUris.get(i), true, this.mSelfObserver);
            }
            this.mSelfObserverRegistered = true;
        }
        this.mDataSetObservable.notifyChanged();
        return true;
    }

    @Override // android.database.Cursor
    public boolean isClosed() {
        return this.mClosed;
    }

    @Override // android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mClosed = true;
        this.mContentObservable.unregisterAll();
        onDeactivateOrClose();
        if (this.mCloseGuard != null) {
            this.mCloseGuard.close();
        }
    }

    @Override // android.database.CrossProcessCursor
    public boolean onMove(int i, int i2) {
        return true;
    }

    @Override // android.database.Cursor
    public void copyStringToBuffer(int i, android.database.CharArrayBuffer charArrayBuffer) {
        java.lang.String string = getString(i);
        if (string != null) {
            char[] cArr = charArrayBuffer.data;
            if (cArr != null && cArr.length >= string.length()) {
                string.getChars(0, string.length(), cArr, 0);
            } else {
                charArrayBuffer.data = string.toCharArray();
            }
            charArrayBuffer.sizeCopied = string.length();
            return;
        }
        charArrayBuffer.sizeCopied = 0;
    }

    public AbstractCursor() {
        if (this.mCloseGuard != null) {
            this.mCloseGuard.open("AbstractCursor.close");
        }
    }

    private dalvik.system.CloseGuard initCloseGuard() {
        return dalvik.system.CloseGuard.get();
    }

    private dalvik.system.CloseGuard initCloseGuard$ravenwood() {
        return null;
    }

    @Override // android.database.Cursor
    public final int getPosition() {
        return this.mPos;
    }

    @Override // android.database.Cursor
    public final boolean moveToPosition(int i) {
        int count = getCount();
        if (i >= count) {
            this.mPos = count;
            return false;
        }
        if (i < 0) {
            this.mPos = -1;
            return false;
        }
        if (i == this.mPos) {
            return true;
        }
        boolean onMove = onMove(this.mPos, i);
        if (!onMove) {
            this.mPos = -1;
        } else {
            this.mPos = i;
        }
        return onMove;
    }

    @Override // android.database.CrossProcessCursor
    public void fillWindow(int i, android.database.CursorWindow cursorWindow) {
        android.database.DatabaseUtils.cursorFillWindow(this, i, cursorWindow);
    }

    @Override // android.database.Cursor
    public final boolean move(int i) {
        return moveToPosition(this.mPos + i);
    }

    @Override // android.database.Cursor
    public final boolean moveToFirst() {
        return moveToPosition(0);
    }

    @Override // android.database.Cursor
    public final boolean moveToLast() {
        return moveToPosition(getCount() - 1);
    }

    @Override // android.database.Cursor
    public final boolean moveToNext() {
        return moveToPosition(this.mPos + 1);
    }

    @Override // android.database.Cursor
    public final boolean moveToPrevious() {
        return moveToPosition(this.mPos - 1);
    }

    @Override // android.database.Cursor
    public final boolean isFirst() {
        return this.mPos == 0 && getCount() != 0;
    }

    @Override // android.database.Cursor
    public final boolean isLast() {
        int count = getCount();
        return this.mPos == count + (-1) && count != 0;
    }

    @Override // android.database.Cursor
    public final boolean isBeforeFirst() {
        return getCount() == 0 || this.mPos == -1;
    }

    @Override // android.database.Cursor
    public final boolean isAfterLast() {
        return getCount() == 0 || this.mPos == getCount();
    }

    @Override // android.database.Cursor
    public int getColumnIndex(java.lang.String str) {
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf != -1) {
            android.util.Log.e(TAG, "requesting column name with table name -- " + str, new java.lang.Exception());
            str = str.substring(lastIndexOf + 1);
        }
        java.lang.String[] columnNames = getColumnNames();
        int length = columnNames.length;
        for (int i = 0; i < length; i++) {
            if (columnNames[i].equalsIgnoreCase(str)) {
                return i;
            }
        }
        return -1;
    }

    @Override // android.database.Cursor
    public int getColumnIndexOrThrow(java.lang.String str) {
        java.lang.String str2;
        int columnIndex = getColumnIndex(str);
        if (columnIndex < 0) {
            try {
                str2 = java.util.Arrays.toString(getColumnNames());
            } catch (java.lang.Exception e) {
                android.util.Log.d(TAG, "Cannot collect column names for debug purposes", e);
                str2 = "";
            }
            throw new java.lang.IllegalArgumentException("column '" + str + "' does not exist. Available columns: " + str2);
        }
        return columnIndex;
    }

    @Override // android.database.Cursor
    public java.lang.String getColumnName(int i) {
        return getColumnNames()[i];
    }

    @Override // android.database.Cursor
    public void registerContentObserver(android.database.ContentObserver contentObserver) {
        this.mContentObservable.registerObserver(contentObserver);
    }

    @Override // android.database.Cursor
    public void unregisterContentObserver(android.database.ContentObserver contentObserver) {
        if (!this.mClosed) {
            this.mContentObservable.unregisterObserver(contentObserver);
        }
    }

    @Override // android.database.Cursor
    public void registerDataSetObserver(android.database.DataSetObserver dataSetObserver) {
        this.mDataSetObservable.registerObserver(dataSetObserver);
    }

    @Override // android.database.Cursor
    public void unregisterDataSetObserver(android.database.DataSetObserver dataSetObserver) {
        this.mDataSetObservable.unregisterObserver(dataSetObserver);
    }

    protected void onChange(boolean z) {
        synchronized (this.mSelfObserverLock) {
            this.mContentObservable.dispatchChange(z, null);
            if (this.mNotifyUris != null && z) {
                int size = this.mNotifyUris.size();
                for (int i = 0; i < size; i++) {
                    this.mContentResolver.notifyChange(this.mNotifyUris.get(i), this.mSelfObserver);
                }
            }
        }
    }

    @Override // android.database.Cursor
    public void setNotificationUri(android.content.ContentResolver contentResolver, android.net.Uri uri) {
        setNotificationUris(contentResolver, java.util.Arrays.asList(uri));
    }

    @Override // android.database.Cursor
    public void setNotificationUris(android.content.ContentResolver contentResolver, java.util.List<android.net.Uri> list) {
        java.util.Objects.requireNonNull(contentResolver);
        java.util.Objects.requireNonNull(list);
        setNotificationUris(contentResolver, list, contentResolver.getUserId(), true);
    }

    public void setNotificationUris(android.content.ContentResolver contentResolver, java.util.List<android.net.Uri> list, int i, boolean z) {
        synchronized (this.mSelfObserverLock) {
            this.mNotifyUris = list;
            this.mNotifyUri = this.mNotifyUris.get(0);
            this.mContentResolver = contentResolver;
            if (this.mSelfObserver != null) {
                this.mContentResolver.unregisterContentObserver(this.mSelfObserver);
                this.mSelfObserverRegistered = false;
            }
            if (z) {
                this.mSelfObserver = new android.database.AbstractCursor.SelfContentObserver(this);
                int size = this.mNotifyUris.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mContentResolver.registerContentObserver(this.mNotifyUris.get(i2), true, this.mSelfObserver, i);
                }
                this.mSelfObserverRegistered = true;
            }
        }
    }

    @Override // android.database.Cursor
    public android.net.Uri getNotificationUri() {
        android.net.Uri uri;
        synchronized (this.mSelfObserverLock) {
            uri = this.mNotifyUri;
        }
        return uri;
    }

    @Override // android.database.Cursor
    public java.util.List<android.net.Uri> getNotificationUris() {
        java.util.List<android.net.Uri> list;
        synchronized (this.mSelfObserverLock) {
            list = this.mNotifyUris;
        }
        return list;
    }

    @Override // android.database.Cursor
    public boolean getWantsAllOnMoveCalls() {
        return false;
    }

    @Override // android.database.Cursor
    public void setExtras(android.os.Bundle bundle) {
        if (bundle == null) {
            bundle = android.os.Bundle.EMPTY;
        }
        this.mExtras = bundle;
    }

    @Override // android.database.Cursor
    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.database.Cursor
    public android.os.Bundle respond(android.os.Bundle bundle) {
        return android.os.Bundle.EMPTY;
    }

    @java.lang.Deprecated
    protected boolean isFieldUpdated(int i) {
        return false;
    }

    @java.lang.Deprecated
    protected java.lang.Object getUpdatedField(int i) {
        return null;
    }

    protected void checkPosition() {
        if (-1 == this.mPos || getCount() == this.mPos) {
            throw new android.database.CursorIndexOutOfBoundsException(this.mPos, getCount());
        }
    }

    protected void finalize() {
        if (this.mSelfObserver != null && this.mSelfObserverRegistered) {
            this.mContentResolver.unregisterContentObserver(this.mSelfObserver);
        }
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (!this.mClosed) {
                close();
            }
        } catch (java.lang.Exception e) {
        }
    }

    protected static class SelfContentObserver extends android.database.ContentObserver {
        java.lang.ref.WeakReference<android.database.AbstractCursor> mCursor;

        public SelfContentObserver(android.database.AbstractCursor abstractCursor) {
            super(null);
            this.mCursor = new java.lang.ref.WeakReference<>(abstractCursor);
        }

        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return false;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            android.database.AbstractCursor abstractCursor = this.mCursor.get();
            if (abstractCursor != null) {
                abstractCursor.onChange(false);
            }
        }
    }
}
