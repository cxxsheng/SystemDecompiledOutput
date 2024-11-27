package android.util;

/* loaded from: classes3.dex */
public class LongSparseLongArray implements java.lang.Cloneable {
    private long[] mKeys;
    private int mSize;
    private long[] mValues;

    public LongSparseLongArray() {
        this(10);
    }

    public LongSparseLongArray(int i) {
        if (i == 0) {
            this.mKeys = android.util.EmptyArray.LONG;
            this.mValues = android.util.EmptyArray.LONG;
        } else {
            this.mKeys = com.android.internal.util.ArrayUtils.newUnpaddedLongArray(i);
            this.mValues = new long[this.mKeys.length];
        }
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.util.LongSparseLongArray m4827clone() {
        android.util.LongSparseLongArray longSparseLongArray;
        android.util.LongSparseLongArray longSparseLongArray2 = null;
        try {
            longSparseLongArray = (android.util.LongSparseLongArray) super.clone();
        } catch (java.lang.CloneNotSupportedException e) {
        }
        try {
            longSparseLongArray.mKeys = (long[]) this.mKeys.clone();
            longSparseLongArray.mValues = (long[]) this.mValues.clone();
            return longSparseLongArray;
        } catch (java.lang.CloneNotSupportedException e2) {
            longSparseLongArray2 = longSparseLongArray;
            return longSparseLongArray2;
        }
    }

    public long get(long j) {
        return get(j, 0L);
    }

    public long get(long j, long j2) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (binarySearch < 0) {
            return j2;
        }
        return this.mValues[binarySearch];
    }

    public void delete(long j) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
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

    public void put(long j, long j2) {
        int binarySearch = android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (binarySearch >= 0) {
            this.mValues[binarySearch] = j2;
            return;
        }
        int i = ~binarySearch;
        this.mKeys = com.android.internal.util.GrowingArrayUtils.insert(this.mKeys, this.mSize, i, j);
        this.mValues = com.android.internal.util.GrowingArrayUtils.insert(this.mValues, this.mSize, i, j2);
        this.mSize++;
    }

    public int size() {
        return this.mSize;
    }

    public long keyAt(int i) {
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

    public int indexOfKey(long j) {
        return android.util.ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
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

    public void append(long j, long j2) {
        if (this.mSize != 0 && j <= this.mKeys[this.mSize - 1]) {
            put(j, j2);
            return;
        }
        this.mKeys = com.android.internal.util.GrowingArrayUtils.append(this.mKeys, this.mSize, j);
        this.mValues = com.android.internal.util.GrowingArrayUtils.append(this.mValues, this.mSize, j2);
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

    public static class Parcelling implements com.android.internal.util.Parcelling<android.util.LongSparseLongArray> {
        @Override // com.android.internal.util.Parcelling
        public void parcel(android.util.LongSparseLongArray longSparseLongArray, android.os.Parcel parcel, int i) {
            if (longSparseLongArray == null) {
                parcel.writeInt(-1);
                return;
            }
            parcel.writeInt(longSparseLongArray.mSize);
            parcel.writeLongArray(longSparseLongArray.mKeys);
            parcel.writeLongArray(longSparseLongArray.mValues);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.internal.util.Parcelling
        public android.util.LongSparseLongArray unparcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == -1) {
                return null;
            }
            android.util.LongSparseLongArray longSparseLongArray = new android.util.LongSparseLongArray(0);
            longSparseLongArray.mSize = readInt;
            longSparseLongArray.mKeys = parcel.createLongArray();
            longSparseLongArray.mValues = parcel.createLongArray();
            com.android.internal.util.Preconditions.checkArgument(longSparseLongArray.mKeys.length >= readInt);
            com.android.internal.util.Preconditions.checkArgument(longSparseLongArray.mValues.length >= readInt);
            if (readInt > 0) {
                long j = longSparseLongArray.mKeys[0];
                for (int i = 1; i < readInt; i++) {
                    com.android.internal.util.Preconditions.checkArgument(j < longSparseLongArray.mKeys[i]);
                }
            }
            return longSparseLongArray;
        }
    }
}
