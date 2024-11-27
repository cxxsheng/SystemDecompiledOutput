package android.util;

/* loaded from: classes3.dex */
public class LongSparseArray<E> implements java.lang.Cloneable {
    private static final java.lang.Object DELETED = new java.lang.Object();
    private boolean mGarbage;
    private long[] mKeys;
    private int mSize;
    private java.lang.Object[] mValues;

    public LongSparseArray() {
        this(10);
    }

    public LongSparseArray(int i) {
        this.mGarbage = false;
        if (i == 0) {
            this.mKeys = android.util.EmptyArray.LONG;
            this.mValues = android.util.EmptyArray.OBJECT;
        } else {
            this.mKeys = com.android.internal.util.ArrayUtils.newUnpaddedLongArray(i);
            this.mValues = com.android.internal.util.ArrayUtils.newUnpaddedObjectArray(i);
        }
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.util.LongSparseArray<E> m4820clone() {
        android.util.LongSparseArray<E> longSparseArray;
        android.util.LongSparseArray<E> longSparseArray2 = null;
        try {
            longSparseArray = (android.util.LongSparseArray) super.clone();
        } catch (java.lang.CloneNotSupportedException e) {
        }
        try {
            longSparseArray.mKeys = (long[]) this.mKeys.clone();
            longSparseArray.mValues = (java.lang.Object[]) this.mValues.clone();
            return longSparseArray;
        } catch (java.lang.CloneNotSupportedException e2) {
            longSparseArray2 = longSparseArray;
            return longSparseArray2;
        }
    }

    public E get(long j) {
        return get(j, null);
    }

    public E get(long j, E e) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (binarySearch < 0 || this.mValues[binarySearch] == DELETED) {
            return e;
        }
        return (E) this.mValues[binarySearch];
    }

    public void delete(long j) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (binarySearch >= 0 && this.mValues[binarySearch] != DELETED) {
            this.mValues[binarySearch] = DELETED;
            this.mGarbage = true;
        }
    }

    public void remove(long j) {
        delete(j);
    }

    public void removeIf(com.android.internal.util.function.LongObjPredicate<? super E> longObjPredicate) {
        java.util.Objects.requireNonNull(longObjPredicate);
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] != DELETED && longObjPredicate.test(this.mKeys[i], this.mValues[i])) {
                this.mValues[i] = DELETED;
                this.mGarbage = true;
            }
        }
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

    private void gc() {
        int i = this.mSize;
        long[] jArr = this.mKeys;
        java.lang.Object[] objArr = this.mValues;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            java.lang.Object obj = objArr[i3];
            if (obj != DELETED) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.mGarbage = false;
        this.mSize = i2;
    }

    public int firstIndexOnOrAfter(long j) {
        if (this.mGarbage) {
            gc();
        }
        int binarySearch = java.util.Arrays.binarySearch(this.mKeys, 0, size(), j);
        return binarySearch >= 0 ? binarySearch : (-binarySearch) - 1;
    }

    public int lastIndexOnOrBefore(long j) {
        int firstIndexOnOrAfter = firstIndexOnOrAfter(j);
        if (firstIndexOnOrAfter < size() && keyAt(firstIndexOnOrAfter) == j) {
            return firstIndexOnOrAfter;
        }
        return firstIndexOnOrAfter - 1;
    }

    public void put(long j, E e) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (binarySearch >= 0) {
            this.mValues[binarySearch] = e;
            return;
        }
        int i = ~binarySearch;
        if (i < this.mSize && this.mValues[i] == DELETED) {
            this.mKeys[i] = j;
            this.mValues[i] = e;
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc();
            i = ~android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        }
        this.mKeys = com.android.internal.util.GrowingArrayUtils.insert(this.mKeys, this.mSize, i, j);
        this.mValues = com.android.internal.util.GrowingArrayUtils.insert((E[]) this.mValues, this.mSize, i, e);
        this.mSize++;
    }

    public int size() {
        if (this.mGarbage) {
            gc();
        }
        return this.mSize;
    }

    public long keyAt(int i) {
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

    public int indexOfKey(long j) {
        if (this.mGarbage) {
            gc();
        }
        return android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
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

    public void append(long j, E e) {
        if (this.mSize != 0 && j <= this.mKeys[this.mSize - 1]) {
            put(j, e);
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc();
        }
        this.mKeys = com.android.internal.util.GrowingArrayUtils.append(this.mKeys, this.mSize, j);
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

    public static class StringParcelling implements com.android.internal.util.Parcelling<android.util.LongSparseArray<java.lang.String>> {
        @Override // com.android.internal.util.Parcelling
        public void parcel(android.util.LongSparseArray<java.lang.String> longSparseArray, android.os.Parcel parcel, int i) {
            if (longSparseArray == null) {
                parcel.writeInt(-1);
                return;
            }
            int i2 = ((android.util.LongSparseArray) longSparseArray).mSize;
            parcel.writeInt(i2);
            parcel.writeLongArray(((android.util.LongSparseArray) longSparseArray).mKeys);
            parcel.writeStringArray((java.lang.String[]) java.util.Arrays.copyOfRange(((android.util.LongSparseArray) longSparseArray).mValues, 0, i2, java.lang.String[].class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.internal.util.Parcelling
        public android.util.LongSparseArray<java.lang.String> unparcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == -1) {
                return null;
            }
            android.util.LongSparseArray<java.lang.String> longSparseArray = new android.util.LongSparseArray<>(0);
            ((android.util.LongSparseArray) longSparseArray).mSize = readInt;
            ((android.util.LongSparseArray) longSparseArray).mKeys = parcel.createLongArray();
            ((android.util.LongSparseArray) longSparseArray).mValues = parcel.createStringArray();
            com.android.internal.util.Preconditions.checkArgument(((android.util.LongSparseArray) longSparseArray).mKeys.length >= readInt);
            com.android.internal.util.Preconditions.checkArgument(((android.util.LongSparseArray) longSparseArray).mValues.length >= readInt);
            if (readInt > 0) {
                long j = ((android.util.LongSparseArray) longSparseArray).mKeys[0];
                for (int i = 1; i < readInt; i++) {
                    com.android.internal.util.Preconditions.checkArgument(j < ((android.util.LongSparseArray) longSparseArray).mKeys[i]);
                }
            }
            return longSparseArray;
        }
    }
}
