package android.content;

/* loaded from: classes.dex */
public class ContentQueryMap extends java.util.Observable {
    private java.lang.String[] mColumnNames;
    private android.database.ContentObserver mContentObserver;
    private volatile android.database.Cursor mCursor;
    private android.os.Handler mHandlerForUpdateNotifications;
    private int mKeyColumn;
    private boolean mKeepUpdated = false;
    private java.util.Map<java.lang.String, android.content.ContentValues> mValues = null;
    private boolean mDirty = false;

    public ContentQueryMap(android.database.Cursor cursor, java.lang.String str, boolean z, android.os.Handler handler) {
        this.mHandlerForUpdateNotifications = null;
        this.mCursor = cursor;
        this.mColumnNames = this.mCursor.getColumnNames();
        this.mKeyColumn = this.mCursor.getColumnIndexOrThrow(str);
        this.mHandlerForUpdateNotifications = handler;
        setKeepUpdated(z);
        if (!z) {
            readCursorIntoCache(cursor);
        }
    }

    public void setKeepUpdated(boolean z) {
        if (z == this.mKeepUpdated) {
            return;
        }
        this.mKeepUpdated = z;
        if (!this.mKeepUpdated) {
            this.mCursor.unregisterContentObserver(this.mContentObserver);
            this.mContentObserver = null;
            return;
        }
        if (this.mHandlerForUpdateNotifications == null) {
            this.mHandlerForUpdateNotifications = new android.os.Handler();
        }
        if (this.mContentObserver == null) {
            this.mContentObserver = new android.database.ContentObserver(this.mHandlerForUpdateNotifications) { // from class: android.content.ContentQueryMap.1
                @Override // android.database.ContentObserver
                public void onChange(boolean z2) {
                    if (android.content.ContentQueryMap.this.countObservers() != 0) {
                        android.content.ContentQueryMap.this.requery();
                    } else {
                        android.content.ContentQueryMap.this.mDirty = true;
                    }
                }
            };
        }
        this.mCursor.registerContentObserver(this.mContentObserver);
        this.mDirty = true;
    }

    public synchronized android.content.ContentValues getValues(java.lang.String str) {
        if (this.mDirty) {
            requery();
        }
        return this.mValues.get(str);
    }

    public void requery() {
        android.database.Cursor cursor = this.mCursor;
        if (cursor == null) {
            return;
        }
        this.mDirty = false;
        if (!cursor.requery()) {
            return;
        }
        readCursorIntoCache(cursor);
        setChanged();
        notifyObservers();
    }

    private synchronized void readCursorIntoCache(android.database.Cursor cursor) {
        this.mValues = new java.util.HashMap(this.mValues != null ? this.mValues.size() : 0);
        while (cursor.moveToNext()) {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            for (int i = 0; i < this.mColumnNames.length; i++) {
                if (i != this.mKeyColumn) {
                    contentValues.put(this.mColumnNames[i], cursor.getString(i));
                }
            }
            this.mValues.put(cursor.getString(this.mKeyColumn), contentValues);
        }
    }

    public synchronized java.util.Map<java.lang.String, android.content.ContentValues> getRows() {
        if (this.mDirty) {
            requery();
        }
        return this.mValues;
    }

    public synchronized void close() {
        if (this.mContentObserver != null) {
            this.mCursor.unregisterContentObserver(this.mContentObserver);
            this.mContentObserver = null;
        }
        this.mCursor.close();
        this.mCursor = null;
    }

    protected void finalize() throws java.lang.Throwable {
        if (this.mCursor != null) {
            close();
        }
        super.finalize();
    }
}
