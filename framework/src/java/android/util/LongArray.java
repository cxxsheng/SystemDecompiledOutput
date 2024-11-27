package android.util;

/* loaded from: classes3.dex */
public class LongArray implements java.lang.Cloneable {
    private static final int MIN_CAPACITY_INCREMENT = 12;
    private int mSize;
    private long[] mValues;

    private LongArray(long[] jArr, int i) {
        this.mValues = jArr;
        this.mSize = com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, jArr.length, "size");
    }

    public LongArray() {
        this(0);
    }

    public LongArray(int i) {
        if (i == 0) {
            this.mValues = android.util.EmptyArray.LONG;
        } else {
            this.mValues = com.android.internal.util.ArrayUtils.newUnpaddedLongArray(i);
        }
        this.mSize = 0;
    }

    public static android.util.LongArray wrap(long[] jArr) {
        return new android.util.LongArray(jArr, jArr.length);
    }

    public static android.util.LongArray fromArray(long[] jArr, int i) {
        return wrap(java.util.Arrays.copyOf(jArr, i));
    }

    public void resize(int i) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        if (i <= this.mValues.length) {
            java.util.Arrays.fill(this.mValues, i, this.mValues.length, 0L);
        } else {
            ensureCapacity(i - this.mSize);
        }
        this.mSize = i;
    }

    public void add(long j) {
        add(this.mSize, j);
    }

    public void add(int i, long j) {
        ensureCapacity(1);
        int i2 = this.mSize - i;
        this.mSize++;
        com.android.internal.util.ArrayUtils.checkBounds(this.mSize, i);
        if (i2 != 0) {
            java.lang.System.arraycopy(this.mValues, i, this.mValues, i + 1, i2);
        }
        this.mValues[i] = j;
    }

    public void addAll(android.util.LongArray longArray) {
        int i = longArray.mSize;
        ensureCapacity(i);
        java.lang.System.arraycopy(longArray.mValues, 0, this.mValues, this.mSize, i);
        this.mSize += i;
    }

    private void ensureCapacity(int i) {
        int i2 = this.mSize;
        int i3 = i + i2;
        if (i3 >= this.mValues.length) {
            int i4 = (i2 < 6 ? 12 : i2 >> 1) + i2;
            if (i4 > i3) {
                i3 = i4;
            }
            long[] newUnpaddedLongArray = com.android.internal.util.ArrayUtils.newUnpaddedLongArray(i3);
            java.lang.System.arraycopy(this.mValues, 0, newUnpaddedLongArray, 0, i2);
            this.mValues = newUnpaddedLongArray;
        }
    }

    public void clear() {
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.util.LongArray m4813clone() {
        android.util.LongArray longArray;
        android.util.LongArray longArray2 = null;
        try {
            longArray = (android.util.LongArray) super.clone();
        } catch (java.lang.CloneNotSupportedException e) {
        }
        try {
            longArray.mValues = (long[]) this.mValues.clone();
            return longArray;
        } catch (java.lang.CloneNotSupportedException e2) {
            longArray2 = longArray;
            return longArray2;
        }
    }

    public long get(int i) {
        com.android.internal.util.ArrayUtils.checkBounds(this.mSize, i);
        return this.mValues[i];
    }

    public void set(int i, long j) {
        com.android.internal.util.ArrayUtils.checkBounds(this.mSize, i);
        this.mValues[i] = j;
    }

    public int indexOf(long j) {
        int i = this.mSize;
        for (int i2 = 0; i2 < i; i2++) {
            if (this.mValues[i2] == j) {
                return i2;
            }
        }
        return -1;
    }

    public void remove(int i) {
        com.android.internal.util.ArrayUtils.checkBounds(this.mSize, i);
        java.lang.System.arraycopy(this.mValues, i + 1, this.mValues, i, (this.mSize - i) - 1);
        this.mSize--;
    }

    public int size() {
        return this.mSize;
    }

    public long[] toArray() {
        return java.util.Arrays.copyOf(this.mValues, this.mSize);
    }

    public static boolean elementsEqual(android.util.LongArray longArray, android.util.LongArray longArray2) {
        if (longArray == null || longArray2 == null) {
            return longArray == longArray2;
        }
        if (longArray.mSize != longArray2.mSize) {
            return false;
        }
        for (int i = 0; i < longArray.mSize; i++) {
            if (longArray.get(i) != longArray2.get(i)) {
                return false;
            }
        }
        return true;
    }
}
