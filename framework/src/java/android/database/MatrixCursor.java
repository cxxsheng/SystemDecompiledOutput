package android.database;

/* loaded from: classes.dex */
public class MatrixCursor extends android.database.AbstractCursor {
    private final int columnCount;
    private final java.lang.String[] columnNames;
    private java.lang.Object[] data;
    private int rowCount;

    public MatrixCursor(java.lang.String[] strArr, int i) {
        this.rowCount = 0;
        this.columnNames = strArr;
        this.columnCount = strArr.length;
        this.data = new java.lang.Object[this.columnCount * (i < 1 ? 1 : i)];
    }

    public MatrixCursor(java.lang.String[] strArr) {
        this(strArr, 16);
    }

    private java.lang.Object get(int i) {
        if (i < 0 || i >= this.columnCount) {
            throw new android.database.CursorIndexOutOfBoundsException("Requested column: " + i + ", # of columns: " + this.columnCount);
        }
        if (this.mPos < 0) {
            throw new android.database.CursorIndexOutOfBoundsException("Before first row.");
        }
        if (this.mPos >= this.rowCount) {
            throw new android.database.CursorIndexOutOfBoundsException("After last row.");
        }
        return this.data[(this.mPos * this.columnCount) + i];
    }

    public android.database.MatrixCursor.RowBuilder newRow() {
        int i = this.rowCount;
        this.rowCount = i + 1;
        ensureCapacity(this.rowCount * this.columnCount);
        return new android.database.MatrixCursor.RowBuilder(i);
    }

    public void addRow(java.lang.Object[] objArr) {
        if (objArr.length != this.columnCount) {
            throw new java.lang.IllegalArgumentException("columnNames.length = " + this.columnCount + ", columnValues.length = " + objArr.length);
        }
        int i = this.rowCount;
        this.rowCount = i + 1;
        int i2 = i * this.columnCount;
        ensureCapacity(this.columnCount + i2);
        java.lang.System.arraycopy(objArr, 0, this.data, i2, this.columnCount);
    }

    public void addRow(java.lang.Iterable<?> iterable) {
        int i = this.rowCount * this.columnCount;
        int i2 = this.columnCount + i;
        ensureCapacity(i2);
        if (iterable instanceof java.util.ArrayList) {
            addRow((java.util.ArrayList) iterable, i);
            return;
        }
        java.lang.Object[] objArr = this.data;
        for (java.lang.Object obj : iterable) {
            if (i == i2) {
                throw new java.lang.IllegalArgumentException("columnValues.size() > columnNames.length");
            }
            objArr[i] = obj;
            i++;
        }
        if (i != i2) {
            throw new java.lang.IllegalArgumentException("columnValues.size() < columnNames.length");
        }
        this.rowCount++;
    }

    private void addRow(java.util.ArrayList<?> arrayList, int i) {
        int size = arrayList.size();
        if (size != this.columnCount) {
            throw new java.lang.IllegalArgumentException("columnNames.length = " + this.columnCount + ", columnValues.size() = " + size);
        }
        this.rowCount++;
        java.lang.Object[] objArr = this.data;
        for (int i2 = 0; i2 < size; i2++) {
            objArr[i + i2] = arrayList.get(i2);
        }
    }

    private void ensureCapacity(int i) {
        if (i > this.data.length) {
            java.lang.Object[] objArr = this.data;
            int length = this.data.length * 2;
            if (length >= i) {
                i = length;
            }
            this.data = new java.lang.Object[i];
            java.lang.System.arraycopy(objArr, 0, this.data, 0, objArr.length);
        }
    }

    public class RowBuilder {
        private final int endIndex;
        private int index;
        private final int row;

        RowBuilder(int i) {
            this.row = i;
            this.index = i * android.database.MatrixCursor.this.columnCount;
            this.endIndex = this.index + android.database.MatrixCursor.this.columnCount;
        }

        public android.database.MatrixCursor.RowBuilder add(java.lang.Object obj) {
            if (this.index == this.endIndex) {
                throw new android.database.CursorIndexOutOfBoundsException("No more columns left.");
            }
            java.lang.Object[] objArr = android.database.MatrixCursor.this.data;
            int i = this.index;
            this.index = i + 1;
            objArr[i] = obj;
            return this;
        }

        public android.database.MatrixCursor.RowBuilder add(java.lang.String str, java.lang.Object obj) {
            for (int i = 0; i < android.database.MatrixCursor.this.columnNames.length; i++) {
                if (str.equals(android.database.MatrixCursor.this.columnNames[i])) {
                    android.database.MatrixCursor.this.data[(this.row * android.database.MatrixCursor.this.columnCount) + i] = obj;
                }
            }
            return this;
        }

        public final android.database.MatrixCursor.RowBuilder add(int i, java.lang.Object obj) {
            android.database.MatrixCursor.this.data[(this.row * android.database.MatrixCursor.this.columnCount) + i] = obj;
            return this;
        }
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getCount() {
        return this.rowCount;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public java.lang.String[] getColumnNames() {
        return this.columnNames;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public java.lang.String getString(int i) {
        java.lang.Object obj = get(i);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public short getShort(int i) {
        java.lang.Object obj = get(i);
        if (obj == null) {
            return (short) 0;
        }
        return obj instanceof java.lang.Number ? ((java.lang.Number) obj).shortValue() : java.lang.Short.parseShort(obj.toString());
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getInt(int i) {
        java.lang.Object obj = get(i);
        if (obj == null) {
            return 0;
        }
        return obj instanceof java.lang.Number ? ((java.lang.Number) obj).intValue() : java.lang.Integer.parseInt(obj.toString());
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public long getLong(int i) {
        java.lang.Object obj = get(i);
        if (obj == null) {
            return 0L;
        }
        return obj instanceof java.lang.Number ? ((java.lang.Number) obj).longValue() : java.lang.Long.parseLong(obj.toString());
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public float getFloat(int i) {
        java.lang.Object obj = get(i);
        if (obj == null) {
            return 0.0f;
        }
        return obj instanceof java.lang.Number ? ((java.lang.Number) obj).floatValue() : java.lang.Float.parseFloat(obj.toString());
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public double getDouble(int i) {
        java.lang.Object obj = get(i);
        if (obj == null) {
            return 0.0d;
        }
        return obj instanceof java.lang.Number ? ((java.lang.Number) obj).doubleValue() : java.lang.Double.parseDouble(obj.toString());
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public byte[] getBlob(int i) {
        return (byte[]) get(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getType(int i) {
        return android.database.DatabaseUtils.getTypeOfObject(get(i));
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean isNull(int i) {
        return get(i) == null;
    }
}
