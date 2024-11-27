package android.text;

/* loaded from: classes3.dex */
class PackedObjectVector<E> {
    private int mColumns;
    private java.lang.Object[] mValues = libcore.util.EmptyArray.OBJECT;
    private int mRows = 0;
    private int mRowGapStart = 0;
    private int mRowGapLength = this.mRows;

    public PackedObjectVector(int i) {
        this.mColumns = i;
    }

    public E getValue(int i, int i2) {
        if (i >= this.mRowGapStart) {
            i += this.mRowGapLength;
        }
        return (E) this.mValues[(i * this.mColumns) + i2];
    }

    public void setValue(int i, int i2, E e) {
        if (i >= this.mRowGapStart) {
            i += this.mRowGapLength;
        }
        this.mValues[(i * this.mColumns) + i2] = e;
    }

    public void insertAt(int i, E[] eArr) {
        moveRowGapTo(i);
        if (this.mRowGapLength == 0) {
            growBuffer();
        }
        this.mRowGapStart++;
        this.mRowGapLength--;
        int i2 = 0;
        if (eArr == null) {
            while (i2 < this.mColumns) {
                setValue(i, i2, null);
                i2++;
            }
        } else {
            while (i2 < this.mColumns) {
                setValue(i, i2, eArr[i2]);
                i2++;
            }
        }
    }

    public void deleteAt(int i, int i2) {
        moveRowGapTo(i + i2);
        this.mRowGapStart -= i2;
        this.mRowGapLength += i2;
        size();
    }

    public int size() {
        return this.mRows - this.mRowGapLength;
    }

    public int width() {
        return this.mColumns;
    }

    private void growBuffer() {
        java.lang.Object[] newUnpaddedObjectArray = com.android.internal.util.ArrayUtils.newUnpaddedObjectArray(com.android.internal.util.GrowingArrayUtils.growSize(size()) * this.mColumns);
        int length = newUnpaddedObjectArray.length / this.mColumns;
        int i = this.mRows - (this.mRowGapStart + this.mRowGapLength);
        java.lang.System.arraycopy(this.mValues, 0, newUnpaddedObjectArray, 0, this.mColumns * this.mRowGapStart);
        java.lang.System.arraycopy(this.mValues, (this.mRows - i) * this.mColumns, newUnpaddedObjectArray, (length - i) * this.mColumns, i * this.mColumns);
        this.mRowGapLength += length - this.mRows;
        this.mRows = length;
        this.mValues = newUnpaddedObjectArray;
    }

    private void moveRowGapTo(int i) {
        if (i == this.mRowGapStart) {
            return;
        }
        if (i > this.mRowGapStart) {
            int i2 = (this.mRowGapLength + i) - (this.mRowGapStart + this.mRowGapLength);
            for (int i3 = this.mRowGapStart + this.mRowGapLength; i3 < this.mRowGapStart + this.mRowGapLength + i2; i3++) {
                int i4 = (i3 - (this.mRowGapStart + this.mRowGapLength)) + this.mRowGapStart;
                for (int i5 = 0; i5 < this.mColumns; i5++) {
                    this.mValues[(this.mColumns * i4) + i5] = this.mValues[(this.mColumns * i3) + i5];
                }
            }
        } else {
            int i6 = this.mRowGapStart - i;
            for (int i7 = (i + i6) - 1; i7 >= i; i7--) {
                int i8 = (((i7 - i) + this.mRowGapStart) + this.mRowGapLength) - i6;
                for (int i9 = 0; i9 < this.mColumns; i9++) {
                    this.mValues[(this.mColumns * i8) + i9] = this.mValues[(this.mColumns * i7) + i9];
                }
            }
        }
        this.mRowGapStart = i;
    }

    public void dump() {
        for (int i = 0; i < this.mRows; i++) {
            for (int i2 = 0; i2 < this.mColumns; i2++) {
                java.lang.Object obj = this.mValues[(this.mColumns * i) + i2];
                if (i < this.mRowGapStart || i >= this.mRowGapStart + this.mRowGapLength) {
                    java.lang.System.out.print(obj + " ");
                } else {
                    java.lang.System.out.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + obj + ") ");
                }
            }
            java.lang.System.out.print(" << \n");
        }
        java.lang.System.out.print("-----\n\n");
    }
}
