package android.util;

/* loaded from: classes3.dex */
public class SparseArray<E> implements java.lang.Cloneable {
    private static final java.lang.Object DELETED = new java.lang.Object();
    private boolean mGarbage;
    private int[] mKeys;
    private int mSize;
    private java.lang.Object[] mValues;

    public SparseArray() {
        this(0);
    }

    public SparseArray(int i) {
        this.mGarbage = false;
        if (i == 0) {
            this.mKeys = android.util.EmptyArray.INT;
            this.mValues = android.util.EmptyArray.OBJECT;
        } else {
            this.mValues = com.android.internal.util.ArrayUtils.newUnpaddedObjectArray(i);
            this.mKeys = new int[this.mValues.length];
        }
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.util.SparseArray<E> m4835clone() {
        android.util.SparseArray<E> sparseArray;
        android.util.SparseArray<E> sparseArray2 = null;
        try {
            sparseArray = (android.util.SparseArray) super.clone();
        } catch (java.lang.CloneNotSupportedException e) {
        }
        try {
            sparseArray.mKeys = (int[]) this.mKeys.clone();
            sparseArray.mValues = (java.lang.Object[]) this.mValues.clone();
            return sparseArray;
        } catch (java.lang.CloneNotSupportedException e2) {
            sparseArray2 = sparseArray;
            return sparseArray2;
        }
    }

    public boolean contains(int i) {
        return indexOfKey(i) >= 0;
    }

    public E get(int i) {
        return get(i, null);
    }

    public E get(int i, E e) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch < 0 || this.mValues[binarySearch] == DELETED) {
            return e;
        }
        return (E) this.mValues[binarySearch];
    }

    public void delete(int i) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0 && this.mValues[binarySearch] != DELETED) {
            this.mValues[binarySearch] = DELETED;
            this.mGarbage = true;
        }
    }

    public E removeReturnOld(int i) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0 && this.mValues[binarySearch] != DELETED) {
            E e = (E) this.mValues[binarySearch];
            this.mValues[binarySearch] = DELETED;
            this.mGarbage = true;
            return e;
        }
        return null;
    }

    public void remove(int i) {
        delete(i);
    }

    public void removeAt(int i) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        if (this.mValues[i] != DELETED) {
            this.mValues[i] = DELETED;
            this.mGarbage = true;
        }
    }

    public void removeAtRange(int i, int i2) {
        int min = java.lang.Math.min(this.mSize, i2 + i);
        while (i < min) {
            removeAt(i);
            i++;
        }
    }

    private void gc() {
        int i = this.mSize;
        int[] iArr = this.mKeys;
        java.lang.Object[] objArr = this.mValues;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            java.lang.Object obj = objArr[i3];
            if (obj != DELETED) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.mGarbage = false;
        this.mSize = i2;
    }

    public void set(int i, E e) {
        put(i, e);
    }

    public void put(int i, E e) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            this.mValues[binarySearch] = e;
            return;
        }
        int i2 = ~binarySearch;
        if (i2 < this.mSize && this.mValues[i2] == DELETED) {
            this.mKeys[i2] = i;
            this.mValues[i2] = e;
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc();
            i2 = ~android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        }
        this.mKeys = com.android.internal.util.GrowingArrayUtils.insert(this.mKeys, this.mSize, i2, i);
        this.mValues = com.android.internal.util.GrowingArrayUtils.insert((E[]) this.mValues, this.mSize, i2, e);
        this.mSize++;
    }

    public int size() {
        if (this.mGarbage) {
            gc();
        }
        return this.mSize;
    }

    public int keyAt(int i) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        if (this.mGarbage) {
            gc();
        }
        return this.mKeys[i];
    }

    public E valueAt(int i) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        if (this.mGarbage) {
            gc();
        }
        return (E) this.mValues[i];
    }

    public void setValueAt(int i, E e) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        if (this.mGarbage) {
            gc();
        }
        this.mValues[i] = e;
    }

    public int indexOfKey(int i) {
        if (this.mGarbage) {
            gc();
        }
        return android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
    }

    public int indexOfValue(E e) {
        if (this.mGarbage) {
            gc();
        }
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == e) {
                return i;
            }
        }
        return -1;
    }

    public int indexOfValueByValue(E e) {
        if (this.mGarbage) {
            gc();
        }
        for (int i = 0; i < this.mSize; i++) {
            if (e == null) {
                if (this.mValues[i] == null) {
                    return i;
                }
            } else if (e.equals(this.mValues[i])) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        int i = this.mSize;
        java.lang.Object[] objArr = this.mValues;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    public void append(int i, E e) {
        if (this.mSize != 0 && i <= this.mKeys[this.mSize - 1]) {
            put(i, e);
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc();
        }
        this.mKeys = com.android.internal.util.GrowingArrayUtils.append(this.mKeys, this.mSize, i);
        this.mValues = com.android.internal.util.GrowingArrayUtils.append((E[]) this.mValues, this.mSize, e);
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
            E valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public boolean contentEquals(android.util.SparseArray<?> sparseArray) {
        int size;
        if (sparseArray == null || (size = size()) != sparseArray.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (this.mKeys[i] != sparseArray.mKeys[i] || !java.util.Objects.equals(this.mValues[i], sparseArray.mValues[i])) {
                return false;
            }
        }
        return true;
    }

    public int contentHashCode() {
        int size = size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i = (((i * 31) + this.mKeys[i2]) * 31) + java.util.Objects.hashCode(this.mValues[i2]);
        }
        return i;
    }
}
