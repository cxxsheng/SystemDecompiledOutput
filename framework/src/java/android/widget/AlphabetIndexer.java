package android.widget;

/* loaded from: classes4.dex */
public class AlphabetIndexer extends android.database.DataSetObserver implements android.widget.SectionIndexer {
    private android.util.SparseIntArray mAlphaMap;
    protected java.lang.CharSequence mAlphabet;
    private java.lang.String[] mAlphabetArray;
    private int mAlphabetLength;
    private java.text.Collator mCollator;
    protected int mColumnIndex;
    protected android.database.Cursor mDataCursor;

    public AlphabetIndexer(android.database.Cursor cursor, int i, java.lang.CharSequence charSequence) {
        this.mDataCursor = cursor;
        this.mColumnIndex = i;
        this.mAlphabet = charSequence;
        this.mAlphabetLength = charSequence.length();
        this.mAlphabetArray = new java.lang.String[this.mAlphabetLength];
        for (int i2 = 0; i2 < this.mAlphabetLength; i2++) {
            this.mAlphabetArray[i2] = java.lang.Character.toString(this.mAlphabet.charAt(i2));
        }
        this.mAlphaMap = new android.util.SparseIntArray(this.mAlphabetLength);
        if (cursor != null) {
            cursor.registerDataSetObserver(this);
        }
        this.mCollator = java.text.Collator.getInstance();
        this.mCollator.setStrength(0);
    }

    @Override // android.widget.SectionIndexer
    public java.lang.Object[] getSections() {
        return this.mAlphabetArray;
    }

    public void setCursor(android.database.Cursor cursor) {
        if (this.mDataCursor != null) {
            this.mDataCursor.unregisterDataSetObserver(this);
        }
        this.mDataCursor = cursor;
        if (cursor != null) {
            this.mDataCursor.registerDataSetObserver(this);
        }
        this.mAlphaMap.clear();
    }

    protected int compare(java.lang.String str, java.lang.String str2) {
        java.lang.String substring;
        if (str.length() == 0) {
            substring = " ";
        } else {
            substring = str.substring(0, 1);
        }
        return this.mCollator.compare(substring, str2);
    }

    @Override // android.widget.SectionIndexer
    public int getPositionForSection(int i) {
        int i2;
        int i3;
        android.util.SparseIntArray sparseIntArray = this.mAlphaMap;
        android.database.Cursor cursor = this.mDataCursor;
        int i4 = 0;
        if (cursor == null || this.mAlphabet == null || i <= 0) {
            return 0;
        }
        if (i >= this.mAlphabetLength) {
            i = this.mAlphabetLength - 1;
        }
        int position = cursor.getPosition();
        int count = cursor.getCount();
        char charAt = this.mAlphabet.charAt(i);
        java.lang.String ch = java.lang.Character.toString(charAt);
        int i5 = sparseIntArray.get(charAt, Integer.MIN_VALUE);
        if (Integer.MIN_VALUE == i5) {
            i2 = count;
        } else if (i5 < 0) {
            i2 = -i5;
        } else {
            return i5;
        }
        if (i > 0 && (i3 = sparseIntArray.get(this.mAlphabet.charAt(i - 1), Integer.MIN_VALUE)) != Integer.MIN_VALUE) {
            i4 = java.lang.Math.abs(i3);
        }
        int i6 = (i2 + i4) / 2;
        while (i6 < i2) {
            cursor.moveToPosition(i6);
            java.lang.String string = cursor.getString(this.mColumnIndex);
            if (string == null) {
                if (i6 == 0) {
                    break;
                }
                i6--;
            } else {
                int compare = compare(string, ch);
                if (compare != 0) {
                    if (compare < 0) {
                        int i7 = i6 + 1;
                        if (i7 >= count) {
                            break;
                        }
                        i4 = i7;
                        i6 = (i4 + i2) / 2;
                    }
                } else if (i4 == i6) {
                    break;
                }
                i2 = i6;
                i6 = (i4 + i2) / 2;
            }
        }
        count = i6;
        sparseIntArray.put(charAt, count);
        cursor.moveToPosition(position);
        return count;
    }

    @Override // android.widget.SectionIndexer
    public int getSectionForPosition(int i) {
        int position = this.mDataCursor.getPosition();
        this.mDataCursor.moveToPosition(i);
        java.lang.String string = this.mDataCursor.getString(this.mColumnIndex);
        this.mDataCursor.moveToPosition(position);
        for (int i2 = 0; i2 < this.mAlphabetLength; i2++) {
            if (compare(string, java.lang.Character.toString(this.mAlphabet.charAt(i2))) == 0) {
                return i2;
            }
        }
        return 0;
    }

    @Override // android.database.DataSetObserver
    public void onChanged() {
        super.onChanged();
        this.mAlphaMap.clear();
    }

    @Override // android.database.DataSetObserver
    public void onInvalidated() {
        super.onInvalidated();
        this.mAlphaMap.clear();
    }
}
