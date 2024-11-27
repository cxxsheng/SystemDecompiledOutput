package android.util;

/* loaded from: classes3.dex */
public class SparseLongArray implements java.lang.Cloneable {
    private int[] mKeys;
    private int mSize;
    private long[] mValues;

    public SparseLongArray() {
        this(0);
    }

    public SparseLongArray(int i) {
        if (i == 0) {
            this.mKeys = android.util.EmptyArray.INT;
            this.mValues = android.util.EmptyArray.LONG;
        } else {
            this.mValues = com.android.internal.util.ArrayUtils.newUnpaddedLongArray(i);
            this.mKeys = new int[this.mValues.length];
        }
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.util.SparseLongArray m4839clone() {
        android.util.SparseLongArray sparseLongArray;
        android.util.SparseLongArray sparseLongArray2 = null;
        try {
            sparseLongArray = (android.util.SparseLongArray) super.clone();
        } catch (java.lang.CloneNotSupportedException e) {
        }
        try {
            sparseLongArray.mKeys = (int[]) this.mKeys.clone();
            sparseLongArray.mValues = (long[]) this.mValues.clone();
            return sparseLongArray;
        } catch (java.lang.CloneNotSupportedException e2) {
            sparseLongArray2 = sparseLongArray;
            return sparseLongArray2;
        }
    }

    public long get(int i) {
        return get(i, 0L);
    }

    public long get(int i, long j) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch < 0) {
            return j;
        }
        return this.mValues[binarySearch];
    }

    public void delete(int i) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            removeAt(binarySearch);
        }
    }

    public void removeAtRange(int i, int i2) {
        int min = java.lang.Math.min(i2, this.mSize - i);
        int i3 = i + min;
        java.lang.System.arraycopy(this.mKeys, i3, this.mKeys, i, this.mSize - i3);
        java.lang.System.arraycopy(this.mValues, i3, this.mValues, i, this.mSize - i3);
        this.mSize -= min;
    }

    public void removeAt(int i) {
        int i2 = i + 1;
        java.lang.System.arraycopy(this.mKeys, i2, this.mKeys, i, this.mSize - i2);
        java.lang.System.arraycopy(this.mValues, i2, this.mValues, i, this.mSize - i2);
        this.mSize--;
    }

    public void put(int i, long j) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            this.mValues[binarySearch] = j;
            return;
        }
        int i2 = ~binarySearch;
        this.mKeys = com.android.internal.util.GrowingArrayUtils.insert(this.mKeys, this.mSize, i2, i);
        this.mValues = com.android.internal.util.GrowingArrayUtils.insert(this.mValues, this.mSize, i2, j);
        this.mSize++;
    }

    public void incrementValue(int i, long j) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            long[] jArr = this.mValues;
            jArr[binarySearch] = jArr[binarySearch] + j;
        } else {
            int i2 = ~binarySearch;
            this.mKeys = com.android.internal.util.GrowingArrayUtils.insert(this.mKeys, this.mSize, i2, i);
            this.mValues = com.android.internal.util.GrowingArrayUtils.insert(this.mValues, this.mSize, i2, j);
            this.mSize++;
        }
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

    public long valueAt(int i) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        return this.mValues[i];
    }

    public void setValueAt(int i, long j) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        this.mValues[i] = j;
    }

    public int indexOfKey(int i) {
        return android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
    }

    public int indexOfValue(long j) {
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == j) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        this.mSize = 0;
    }

    public void append(int i, long j) {
        if (this.mSize != 0 && i <= this.mKeys[this.mSize - 1]) {
            put(i, j);
            return;
        }
        this.mKeys = com.android.internal.util.GrowingArrayUtils.append(this.mKeys, this.mSize, i);
        this.mValues = com.android.internal.util.GrowingArrayUtils.append(this.mValues, this.mSize, j);
        this.mSize++;
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
