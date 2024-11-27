package android.util;

/* loaded from: classes3.dex */
public class SparseIntArray implements java.lang.Cloneable {
    private int[] mKeys;
    private int mSize;
    private int[] mValues;

    public SparseIntArray() {
        this(0);
    }

    public SparseIntArray(int i) {
        if (i == 0) {
            this.mKeys = android.util.EmptyArray.INT;
            this.mValues = android.util.EmptyArray.INT;
        } else {
            this.mKeys = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(i);
            this.mValues = new int[this.mKeys.length];
        }
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.util.SparseIntArray m4838clone() {
        android.util.SparseIntArray sparseIntArray;
        android.util.SparseIntArray sparseIntArray2 = null;
        try {
            sparseIntArray = (android.util.SparseIntArray) super.clone();
        } catch (java.lang.CloneNotSupportedException e) {
        }
        try {
            sparseIntArray.mKeys = (int[]) this.mKeys.clone();
            sparseIntArray.mValues = (int[]) this.mValues.clone();
            return sparseIntArray;
        } catch (java.lang.CloneNotSupportedException e2) {
            sparseIntArray2 = sparseIntArray;
            return sparseIntArray2;
        }
    }

    public int get(int i) {
        return get(i, 0);
    }

    public int get(int i, int i2) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch < 0) {
            return i2;
        }
        return this.mValues[binarySearch];
    }

    public void delete(int i) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            removeAt(binarySearch);
        }
    }

    public void removeAt(int i) {
        int i2 = i + 1;
        java.lang.System.arraycopy(this.mKeys, i2, this.mKeys, i, this.mSize - i2);
        java.lang.System.arraycopy(this.mValues, i2, this.mValues, i, this.mSize - i2);
        this.mSize--;
    }

    public void put(int i, int i2) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            this.mValues[binarySearch] = i2;
            return;
        }
        int i3 = ~binarySearch;
        this.mKeys = com.android.internal.util.GrowingArrayUtils.insert(this.mKeys, this.mSize, i3, i);
        this.mValues = com.android.internal.util.GrowingArrayUtils.insert(this.mValues, this.mSize, i3, i2);
        this.mSize++;
    }

    public int size() {
        return this.mSize;
    }

    public int keyAt(int i) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        return this.mKeys[i];
    }

    public int valueAt(int i) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        return this.mValues[i];
    }

    public void setValueAt(int i, int i2) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        this.mValues[i] = i2;
    }

    public int indexOfKey(int i) {
        return android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
    }

    public int indexOfValue(int i) {
        for (int i2 = 0; i2 < this.mSize; i2++) {
            if (this.mValues[i2] == i) {
                return i2;
            }
        }
        return -1;
    }

    public void clear() {
        this.mSize = 0;
    }

    public void append(int i, int i2) {
        if (this.mSize != 0 && i <= this.mKeys[this.mSize - 1]) {
            put(i, i2);
            return;
        }
        this.mKeys = com.android.internal.util.GrowingArrayUtils.append(this.mKeys, this.mSize, i);
        this.mValues = com.android.internal.util.GrowingArrayUtils.append(this.mValues, this.mSize, i2);
        this.mSize++;
    }

    public int[] copyKeys() {
        if (size() == 0) {
            return null;
        }
        return java.util.Arrays.copyOf(this.mKeys, size());
    }

    public java.lang.String toString() {
        if (size() <= 0) {
            return "{}";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            sb.append(valueAt(i));
        }
        sb.append('}');
        return sb.toString();
    }
}
