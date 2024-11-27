package android.database;

/* loaded from: classes.dex */
public class RedactingCursor extends android.database.CrossProcessCursorWrapper {
    private final android.util.SparseArray<java.lang.Object> mRedactions;

    private RedactingCursor(android.database.Cursor cursor, android.util.SparseArray<java.lang.Object> sparseArray) {
        super(cursor);
        this.mRedactions = sparseArray;
    }

    public static android.database.Cursor create(android.database.Cursor cursor, java.util.Map<java.lang.String, java.lang.Object> map) {
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        java.lang.String[] columnNames = cursor.getColumnNames();
        for (int i = 0; i < columnNames.length; i++) {
            if (map.containsKey(columnNames[i])) {
                sparseArray.put(i, map.get(columnNames[i]));
            }
        }
        if (sparseArray.size() == 0) {
            return cursor;
        }
        return new android.database.RedactingCursor(cursor, sparseArray);
    }

    @Override // android.database.CrossProcessCursorWrapper, android.database.CrossProcessCursor
    public void fillWindow(int i, android.database.CursorWindow cursorWindow) {
        android.database.DatabaseUtils.cursorFillWindow(this, i, cursorWindow);
    }

    @Override // android.database.CrossProcessCursorWrapper, android.database.CrossProcessCursor
    public android.database.CursorWindow getWindow() {
        return null;
    }

    @Override // android.database.CursorWrapper
    public android.database.Cursor getWrappedCursor() {
        throw new java.lang.UnsupportedOperationException("Returning underlying cursor risks leaking redacted data");
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public double getDouble(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return ((java.lang.Double) this.mRedactions.valueAt(indexOfKey)).doubleValue();
        }
        return super.getDouble(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public float getFloat(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return ((java.lang.Float) this.mRedactions.valueAt(indexOfKey)).floatValue();
        }
        return super.getFloat(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getInt(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return ((java.lang.Integer) this.mRedactions.valueAt(indexOfKey)).intValue();
        }
        return super.getInt(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public long getLong(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return ((java.lang.Long) this.mRedactions.valueAt(indexOfKey)).longValue();
        }
        return super.getLong(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public short getShort(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return ((java.lang.Short) this.mRedactions.valueAt(indexOfKey)).shortValue();
        }
        return super.getShort(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public java.lang.String getString(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return (java.lang.String) this.mRedactions.valueAt(indexOfKey);
        }
        return super.getString(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public void copyStringToBuffer(int i, android.database.CharArrayBuffer charArrayBuffer) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            charArrayBuffer.data = ((java.lang.String) this.mRedactions.valueAt(indexOfKey)).toCharArray();
            charArrayBuffer.sizeCopied = charArrayBuffer.data.length;
        } else {
            super.copyStringToBuffer(i, charArrayBuffer);
        }
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public byte[] getBlob(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return (byte[]) this.mRedactions.valueAt(indexOfKey);
        }
        return super.getBlob(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getType(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return android.database.DatabaseUtils.getTypeOfObject(this.mRedactions.valueAt(indexOfKey));
        }
        return super.getType(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean isNull(int i) {
        int indexOfKey = this.mRedactions.indexOfKey(i);
        if (indexOfKey >= 0) {
            return this.mRedactions.valueAt(indexOfKey) == null;
        }
        return super.isNull(i);
    }
}
