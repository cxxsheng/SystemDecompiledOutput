package android.database;

/* loaded from: classes.dex */
public final class CursorJoiner implements java.util.Iterator<android.database.CursorJoiner.Result>, java.lang.Iterable<android.database.CursorJoiner.Result> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int[] mColumnsLeft;
    private int[] mColumnsRight;
    private android.database.CursorJoiner.Result mCompareResult;
    private boolean mCompareResultIsValid;
    private android.database.Cursor mCursorLeft;
    private android.database.Cursor mCursorRight;
    private java.lang.String[] mValues;

    public enum Result {
        RIGHT,
        LEFT,
        BOTH
    }

    public CursorJoiner(android.database.Cursor cursor, java.lang.String[] strArr, android.database.Cursor cursor2, java.lang.String[] strArr2) {
        if (strArr.length != strArr2.length) {
            throw new java.lang.IllegalArgumentException("you must have the same number of columns on the left and right, " + strArr.length + " != " + strArr2.length);
        }
        this.mCursorLeft = cursor;
        this.mCursorRight = cursor2;
        this.mCursorLeft.moveToFirst();
        this.mCursorRight.moveToFirst();
        this.mCompareResultIsValid = false;
        this.mColumnsLeft = buildColumnIndiciesArray(cursor, strArr);
        this.mColumnsRight = buildColumnIndiciesArray(cursor2, strArr2);
        this.mValues = new java.lang.String[this.mColumnsLeft.length * 2];
    }

    @Override // java.lang.Iterable
    public java.util.Iterator<android.database.CursorJoiner.Result> iterator() {
        return this;
    }

    private int[] buildColumnIndiciesArray(android.database.Cursor cursor, java.lang.String[] strArr) {
        int[] iArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            iArr[i] = cursor.getColumnIndexOrThrow(strArr[i]);
        }
        return iArr;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (!this.mCompareResultIsValid) {
            return (this.mCursorLeft.isAfterLast() && this.mCursorRight.isAfterLast()) ? false : true;
        }
        switch (this.mCompareResult) {
            case BOTH:
                return (this.mCursorLeft.isLast() && this.mCursorRight.isLast()) ? false : true;
            case LEFT:
                return (this.mCursorLeft.isLast() && this.mCursorRight.isAfterLast()) ? false : true;
            case RIGHT:
                return (this.mCursorLeft.isAfterLast() && this.mCursorRight.isLast()) ? false : true;
            default:
                throw new java.lang.IllegalStateException("bad value for mCompareResult, " + this.mCompareResult);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    public android.database.CursorJoiner.Result next() {
        if (!hasNext()) {
            throw new java.lang.IllegalStateException("you must only call next() when hasNext() is true");
        }
        incrementCursors();
        boolean z = !this.mCursorLeft.isAfterLast();
        boolean z2 = !this.mCursorRight.isAfterLast();
        if (z && z2) {
            populateValues(this.mValues, this.mCursorLeft, this.mColumnsLeft, 0);
            populateValues(this.mValues, this.mCursorRight, this.mColumnsRight, 1);
            switch (compareStrings(this.mValues)) {
                case -1:
                    this.mCompareResult = android.database.CursorJoiner.Result.LEFT;
                    break;
                case 0:
                    this.mCompareResult = android.database.CursorJoiner.Result.BOTH;
                    break;
                case 1:
                    this.mCompareResult = android.database.CursorJoiner.Result.RIGHT;
                    break;
            }
        } else if (z) {
            this.mCompareResult = android.database.CursorJoiner.Result.LEFT;
        } else {
            this.mCompareResult = android.database.CursorJoiner.Result.RIGHT;
        }
        this.mCompareResultIsValid = true;
        return this.mCompareResult;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    private static void populateValues(java.lang.String[] strArr, android.database.Cursor cursor, int[] iArr, int i) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            strArr[(i2 * 2) + i] = cursor.getString(iArr[i2]);
        }
    }

    private void incrementCursors() {
        if (this.mCompareResultIsValid) {
            switch (this.mCompareResult) {
                case BOTH:
                    this.mCursorLeft.moveToNext();
                    this.mCursorRight.moveToNext();
                    break;
                case LEFT:
                    this.mCursorLeft.moveToNext();
                    break;
                case RIGHT:
                    this.mCursorRight.moveToNext();
                    break;
            }
            this.mCompareResultIsValid = false;
        }
    }

    private static int compareStrings(java.lang.String... strArr) {
        if (strArr.length % 2 != 0) {
            throw new java.lang.IllegalArgumentException("you must specify an even number of values");
        }
        for (int i = 0; i < strArr.length; i += 2) {
            if (strArr[i] == null) {
                if (strArr[i + 1] != null) {
                    return -1;
                }
            } else {
                int i2 = i + 1;
                if (strArr[i2] == null) {
                    return 1;
                }
                int compareTo = strArr[i].compareTo(strArr[i2]);
                if (compareTo != 0) {
                    return compareTo < 0 ? -1 : 1;
                }
            }
        }
        return 0;
    }
}
