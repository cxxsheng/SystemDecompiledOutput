package com.android.internal.database;

/* loaded from: classes4.dex */
public class SortCursor extends android.database.AbstractCursor {
    private static final java.lang.String TAG = "SortCursor";
    private int[][] mCurRowNumCache;
    private android.database.Cursor mCursor;
    private android.database.Cursor[] mCursors;
    private int[] mSortColumns;
    private final int ROWCACHESIZE = 64;
    private int[] mRowNumCache = new int[64];
    private int[] mCursorCache = new int[64];
    private int mLastCacheHit = -1;
    private android.database.DataSetObserver mObserver = new android.database.DataSetObserver() { // from class: com.android.internal.database.SortCursor.1
        @Override // android.database.DataSetObserver
        public void onChanged() {
            com.android.internal.database.SortCursor.this.mPos = -1;
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            com.android.internal.database.SortCursor.this.mPos = -1;
        }
    };

    public SortCursor(android.database.Cursor[] cursorArr, java.lang.String str) {
        this.mCursors = cursorArr;
        int length = this.mCursors.length;
        this.mSortColumns = new int[length];
        for (int i = 0; i < length; i++) {
            if (this.mCursors[i] != null) {
                this.mCursors[i].registerDataSetObserver(this.mObserver);
                this.mCursors[i].moveToFirst();
                this.mSortColumns[i] = this.mCursors[i].getColumnIndexOrThrow(str);
            }
        }
        this.mCursor = null;
        java.lang.String str2 = "";
        for (int i2 = 0; i2 < length; i2++) {
            if (this.mCursors[i2] != null && !this.mCursors[i2].isAfterLast()) {
                java.lang.String string = this.mCursors[i2].getString(this.mSortColumns[i2]);
                if (this.mCursor == null || string.compareToIgnoreCase(str2) < 0) {
                    this.mCursor = this.mCursors[i2];
                    str2 = string;
                }
            }
        }
        for (int length2 = this.mRowNumCache.length - 1; length2 >= 0; length2--) {
            this.mRowNumCache[length2] = -2;
        }
        this.mCurRowNumCache = (int[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Integer.TYPE, 64, length);
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
        if (i == i2) {
            return true;
        }
        int i3 = i2 % 64;
        if (this.mRowNumCache[i3] == i2) {
            int i4 = this.mCursorCache[i3];
            this.mCursor = this.mCursors[i4];
            if (this.mCursor == null) {
                android.util.Log.w(TAG, "onMove: cache results in a null cursor.");
                return false;
            }
            this.mCursor.moveToPosition(this.mCurRowNumCache[i3][i4]);
            this.mLastCacheHit = i3;
            return true;
        }
        this.mCursor = null;
        int length = this.mCursors.length;
        if (this.mLastCacheHit >= 0) {
            for (int i5 = 0; i5 < length; i5++) {
                if (this.mCursors[i5] != null) {
                    this.mCursors[i5].moveToPosition(this.mCurRowNumCache[this.mLastCacheHit][i5]);
                }
            }
        }
        if (i2 < i || i == -1) {
            for (int i6 = 0; i6 < length; i6++) {
                if (this.mCursors[i6] != null) {
                    this.mCursors[i6].moveToFirst();
                }
            }
            i = 0;
        }
        if (i < 0) {
            i = 0;
        }
        int i7 = -1;
        while (i <= i2) {
            java.lang.String str = "";
            i7 = -1;
            for (int i8 = 0; i8 < length; i8++) {
                if (this.mCursors[i8] != null && !this.mCursors[i8].isAfterLast()) {
                    java.lang.String string = this.mCursors[i8].getString(this.mSortColumns[i8]);
                    if (i7 < 0 || string.compareToIgnoreCase(str) < 0) {
                        i7 = i8;
                        str = string;
                    }
                }
            }
            if (i == i2) {
                break;
            }
            if (this.mCursors[i7] != null) {
                this.mCursors[i7].moveToNext();
            }
            i++;
        }
        this.mCursor = this.mCursors[i7];
        this.mRowNumCache[i3] = i2;
        this.mCursorCache[i3] = i7;
        for (int i9 = 0; i9 < length; i9++) {
            if (this.mCursors[i9] != null) {
                this.mCurRowNumCache[i3][i9] = this.mCursors[i9].getPosition();
            }
        }
        this.mLastCacheHit = -1;
        return true;
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
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            if (this.mCursors[i] != null) {
                return this.mCursors[i].getColumnNames();
            }
        }
        throw new java.lang.IllegalStateException("No cursor that can return names");
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public void deactivate() {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            if (this.mCursors[i] != null) {
                this.mCursors[i].deactivate();
            }
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        int length = this.mCursors.length;
        for (int i = 0; i < length; i++) {
            if (this.mCursors[i] != null) {
                this.mCursors[i].close();
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
