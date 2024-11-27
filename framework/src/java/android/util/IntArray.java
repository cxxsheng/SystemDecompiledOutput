package android.util;

/* loaded from: classes3.dex */
public class IntArray implements java.lang.Cloneable {
    private static final int MIN_CAPACITY_INCREMENT = 12;
    private int mSize;
    private int[] mValues;

    private IntArray(int[] iArr, int i) {
        this.mValues = iArr;
        this.mSize = com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, iArr.length, "size");
    }

    public IntArray() {
        this(0);
    }

    public IntArray(int i) {
        if (i == 0) {
            this.mValues = android.util.EmptyArray.INT;
        } else {
            this.mValues = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(i);
        }
        this.mSize = 0;
    }

    public static android.util.IntArray wrap(int[] iArr) {
        return new android.util.IntArray(iArr, iArr.length);
    }

    public static android.util.IntArray fromArray(int[] iArr, int i) {
        return wrap(java.util.Arrays.copyOf(iArr, i));
    }

    public void resize(int i) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        if (i <= this.mValues.length) {
            java.util.Arrays.fill(this.mValues, i, this.mValues.length, 0);
        } else {
            ensureCapacity(i - this.mSize);
        }
        this.mSize = i;
    }

    public void add(int i) {
        add(this.mSize, i);
    }

    public void add(int i, int i2) {
        ensureCapacity(1);
        int i3 = this.mSize - i;
        this.mSize++;
        com.android.internal.util.ArrayUtils.checkBounds(this.mSize, i);
        if (i3 != 0) {
            java.lang.System.arraycopy(this.mValues, i, this.mValues, i + 1, i3);
        }
        this.mValues[i] = i2;
    }

    public int binarySearch(int i) {
        return android.util.ContainerHelpers.binarySearch(this.mValues, this.mSize, i);
    }

    public void addAll(android.util.IntArray intArray) {
        int i = intArray.mSize;
        ensureCapacity(i);
        java.lang.System.arraycopy(intArray.mValues, 0, this.mValues, this.mSize, i);
        this.mSize += i;
    }

    public void addAll(int[] iArr) {
        int length = iArr.length;
        ensureCapacity(length);
        java.lang.System.arraycopy(iArr, 0, this.mValues, this.mSize, length);
        this.mSize += length;
    }

    private void ensureCapacity(int i) {
        int i2 = this.mSize;
        int i3 = i + i2;
        if (i3 >= this.mValues.length) {
            int i4 = (i2 < 6 ? 12 : i2 >> 1) + i2;
            if (i4 > i3) {
                i3 = i4;
            }
            int[] newUnpaddedIntArray = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(i3);
            java.lang.System.arraycopy(this.mValues, 0, newUnpaddedIntArray, 0, i2);
            this.mValues = newUnpaddedIntArray;
        }
    }

    public void clear() {
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.util.IntArray m4809clone() {
        return new android.util.IntArray((int[]) this.mValues.clone(), this.mSize);
    }

    public int get(int i) {
        com.android.internal.util.ArrayUtils.checkBounds(this.mSize, i);
        return this.mValues[i];
    }

    public void set(int i, int i2) {
        com.android.internal.util.ArrayUtils.checkBounds(this.mSize, i);
        this.mValues[i] = i2;
    }

    public int indexOf(int i) {
        int i2 = this.mSize;
        for (int i3 = 0; i3 < i2; i3++) {
            if (this.mValues[i3] == i) {
                return i3;
            }
        }
        return -1;
    }

    public boolean contains(int i) {
        return indexOf(i) != -1;
    }

    public void remove(int i) {
        com.android.internal.util.ArrayUtils.checkBounds(this.mSize, i);
        java.lang.System.arraycopy(this.mValues, i + 1, this.mValues, i, (this.mSize - i) - 1);
        this.mSize--;
    }

    public int size() {
        return this.mSize;
    }

    public int[] toArray() {
        return java.util.Arrays.copyOf(this.mValues, this.mSize);
    }

    public java.lang.String toString() {
        int i = this.mSize - 1;
        if (i == -1) {
            return "[]";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append('[');
        int i2 = 0;
        while (true) {
            sb.append(this.mValues[i2]);
            if (i2 == i) {
                return sb.append(']').toString();
            }
            sb.append(", ");
            i2++;
        }
    }
}
