package android.database;

/* loaded from: classes.dex */
public class MergeCursor extends android.database.AbstractCursor {
    private android.database.Cursor mCursor;
    private android.database.Cursor[] mCursors;
    private android.database.DataSetObserver mObserver = new android.database.DataSetObserver() { // from class: android.database.MergeCursor.1
        @Override // android.database.DataSetObserver
        public void onChanged() {
            android.database.MergeCursor.this.mPos = -1;
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            android.database.MergeCursor.this.mPos = -1;
        }
    };

    public MergeCursor(android.database.Cursor[] cursorArr) {
        this.mCursors = cursorArr;
        this.mCursor = cursorArr[0];
        for (int i = 0; i < this.mCursors.length; i++) {
            if (this.mCursors[i] != null) {
                this.mCursors[i].registerDataSetObserver(this.mObserver);
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getCount() {
        int length = this.mCursors.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (this.mCursors[i2] != null) {
                i += this.mCursors[i2].getCount();
            }
        }
        return i;
    }

    @Override // android.database.AbstractCursor, android.database.CrossProcessCursor
    public boolean onMove(int i, int i2) {
        this.mCursor = null;
        int length = this.mCursors.length;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            if (this.mCursors[i3] != null) {
                if (i2 < this.mCursors[i3].getCount() + i4) {
                    this.mCursor = this.mCursors[i3];
                    break;
                }
                i4 += this.mCursors[i3].getCount();
            }
            i3++;
        }
        if (this.mCursor == null) {
            return false;
        }
        return this.mCursor.moveToPosition(i2 - i4);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public java.lang.String getString(int i) {
        return this.mCursor.getString(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public short getShort(int i) {
        return this.mCursor.getShort(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getInt(int i) {
        return this.mCursor.getInt(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public long getLong(int i) {
        return this.mCursor.getLong(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public float getFloat(int i) {
        return this.mCursor.getFloat(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public double getDouble(int i) {
        return this.mCursor.getDouble(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getType(int i) {
        return this.mCursor.getType(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean isNull(int i) {
        return this.mCursor.isNull(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public byte[] getBlob(int i) {
        return this.mCursor.getBlob(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public java.lang.String[] getColumnNames() {
        if (this.mCursor != null) {
            return this.mCursor.getColumnNames();
        }
        return new java.lang.String[0];
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void deactivate() {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            if (this.mCursors[i] != null) {
                this.mCursors[i].deactivate();
            }
        }
        super.deactivate();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            if (this.mCursors[i] != null) {
                this.mCursors[i].close();
            }
        }
        super.close();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void registerContentObserver(android.database.ContentObserver contentObserver) {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            if (this.mCursors[i] != null) {
                this.mCursors[i].registerContentObserver(contentObserver);
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void unregisterContentObserver(android.database.ContentObserver contentObserver) {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            if (this.mCursors[i] != null) {
                this.mCursors[i].unregisterContentObserver(contentObserver);
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void registerDataSetObserver(android.database.DataSetObserver dataSetObserver) {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            if (this.mCursors[i] != null) {
                this.mCursors[i].registerDataSetObserver(dataSetObserver);
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void unregisterDataSetObserver(android.database.DataSetObserver dataSetObserver) {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            if (this.mCursors[i] != null) {
                this.mCursors[i].unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean requery() {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            if (this.mCursors[i] != null && !this.mCursors[i].requery()) {
                return false;
            }
        }
        return true;
    }
}
