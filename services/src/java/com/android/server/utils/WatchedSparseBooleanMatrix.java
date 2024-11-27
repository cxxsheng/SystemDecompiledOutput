package com.android.server.utils;

/* loaded from: classes2.dex */
public class WatchedSparseBooleanMatrix extends com.android.server.utils.WatchableImpl implements com.android.server.utils.Snappable {
    private static final int PACKING = 32;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static final int STEP = 64;
    static final int STRING_INUSE_INDEX = 2;
    static final int STRING_KEY_INDEX = 0;
    static final int STRING_MAP_INDEX = 1;
    private boolean[] mInUse;
    private int[] mKeys;
    private int[] mMap;
    private int mOrder;
    private int mSize;
    private int[] mValues;

    private void onChanged() {
        dispatchChange(this);
    }

    public WatchedSparseBooleanMatrix() {
        this(64);
    }

    public WatchedSparseBooleanMatrix(int i) {
        this.mOrder = i;
        if (this.mOrder < 64) {
            this.mOrder = 64;
        }
        if (this.mOrder % 64 != 0) {
            this.mOrder = ((i / 64) + 1) * 64;
        }
        if (this.mOrder < 64 || this.mOrder % 64 != 0) {
            throw new java.lang.RuntimeException("mOrder is " + this.mOrder + " initCap is " + i);
        }
        this.mInUse = com.android.internal.util.ArrayUtils.newUnpaddedBooleanArray(this.mOrder);
        this.mKeys = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(this.mOrder);
        this.mMap = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(this.mOrder);
        this.mValues = com.android.internal.util.ArrayUtils.newUnpaddedIntArray((this.mOrder * this.mOrder) / 32);
        this.mSize = 0;
    }

    private WatchedSparseBooleanMatrix(com.android.server.utils.WatchedSparseBooleanMatrix watchedSparseBooleanMatrix) {
        copyFrom(watchedSparseBooleanMatrix);
    }

    public void copyFrom(@android.annotation.NonNull com.android.server.utils.WatchedSparseBooleanMatrix watchedSparseBooleanMatrix) {
        this.mOrder = watchedSparseBooleanMatrix.mOrder;
        this.mSize = watchedSparseBooleanMatrix.mSize;
        this.mKeys = (int[]) watchedSparseBooleanMatrix.mKeys.clone();
        this.mMap = (int[]) watchedSparseBooleanMatrix.mMap.clone();
        this.mInUse = (boolean[]) watchedSparseBooleanMatrix.mInUse.clone();
        this.mValues = (int[]) watchedSparseBooleanMatrix.mValues.clone();
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.utils.WatchedSparseBooleanMatrix snapshot() {
        return new com.android.server.utils.WatchedSparseBooleanMatrix(this);
    }

    public boolean get(int i, int i2) {
        return get(i, i2, false);
    }

    public boolean get(int i, int i2, boolean z) {
        int indexOfKey = indexOfKey(i, false);
        int indexOfKey2 = indexOfKey(i2, false);
        if (indexOfKey >= 0 && indexOfKey2 >= 0) {
            return valueAt(indexOfKey, indexOfKey2);
        }
        return z;
    }

    public void put(int i, int i2, boolean z) {
        int indexOfKey = indexOfKey(i);
        int indexOfKey2 = indexOfKey(i2);
        if (indexOfKey < 0 || indexOfKey2 < 0) {
            if (indexOfKey < 0) {
                indexOfKey(i, true);
            }
            if (indexOfKey2 < 0) {
                indexOfKey(i2, true);
            }
            indexOfKey = indexOfKey(i);
            indexOfKey2 = indexOfKey(i2);
        }
        if (indexOfKey >= 0 && indexOfKey2 >= 0) {
            setValueAt(indexOfKey, indexOfKey2, z);
            return;
        }
        throw new java.lang.RuntimeException("matrix overflow");
    }

    public void deleteKey(int i) {
        int indexOfKey = indexOfKey(i, false);
        if (indexOfKey >= 0) {
            removeAt(indexOfKey);
        }
    }

    public void removeAt(int i) {
        validateIndex(i);
        this.mInUse[this.mMap[i]] = false;
        int i2 = i + 1;
        java.lang.System.arraycopy(this.mKeys, i2, this.mKeys, i, this.mSize - i2);
        this.mKeys[this.mSize - 1] = 0;
        java.lang.System.arraycopy(this.mMap, i2, this.mMap, i, this.mSize - i2);
        this.mMap[this.mSize - 1] = 0;
        this.mSize--;
        onChanged();
    }

    public void removeRange(int i, int i2) {
        if (i2 < i) {
            throw new java.lang.ArrayIndexOutOfBoundsException("toIndex < fromIndex");
        }
        int i3 = i2 - i;
        if (i3 == 0) {
            return;
        }
        validateIndex(i);
        validateIndex(i2 - 1);
        for (int i4 = i; i4 < i2; i4++) {
            this.mInUse[this.mMap[i4]] = false;
        }
        java.lang.System.arraycopy(this.mKeys, i2, this.mKeys, i, this.mSize - i2);
        java.lang.System.arraycopy(this.mMap, i2, this.mMap, i, this.mSize - i2);
        for (int i5 = this.mSize - i3; i5 < this.mSize; i5++) {
            this.mKeys[i5] = 0;
            this.mMap[i5] = 0;
        }
        this.mSize -= i3;
        onChanged();
    }

    public int size() {
        return this.mSize;
    }

    public void clear() {
        this.mSize = 0;
        java.util.Arrays.fill(this.mInUse, false);
        onChanged();
    }

    public int keyAt(int i) {
        validateIndex(i);
        return this.mKeys[i];
    }

    private boolean valueAtInternal(int i, int i2) {
        int i3 = (i * this.mOrder) + i2;
        return ((1 << (i3 % 32)) & this.mValues[i3 / 32]) != 0;
    }

    public boolean valueAt(int i, int i2) {
        validateIndex(i, i2);
        return valueAtInternal(this.mMap[i], this.mMap[i2]);
    }

    private void setValueAtInternal(int i, int i2, boolean z) {
        int i3 = (i * this.mOrder) + i2;
        int i4 = i3 / 32;
        int i5 = 1 << (i3 % 32);
        if (z) {
            int[] iArr = this.mValues;
            iArr[i4] = i5 | iArr[i4];
        } else {
            int[] iArr2 = this.mValues;
            iArr2[i4] = (~i5) & iArr2[i4];
        }
    }

    public void setValueAt(int i, int i2, boolean z) {
        validateIndex(i, i2);
        setValueAtInternal(this.mMap[i], this.mMap[i2], z);
        onChanged();
    }

    public int indexOfKey(int i) {
        return binarySearch(this.mKeys, this.mSize, i);
    }

    public boolean contains(int i) {
        return indexOfKey(i) >= 0;
    }

    private int indexOfKey(int i, boolean z) {
        int binarySearch = binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch < 0 && z) {
            binarySearch = ~binarySearch;
            if (this.mSize >= this.mOrder) {
                growMatrix();
            }
            int nextFree = nextFree(true);
            this.mKeys = com.android.internal.util.GrowingArrayUtils.insert(this.mKeys, this.mSize, binarySearch, i);
            this.mMap = com.android.internal.util.GrowingArrayUtils.insert(this.mMap, this.mSize, binarySearch, nextFree);
            this.mSize++;
            int i2 = this.mOrder / 32;
            int i3 = nextFree / 32;
            int i4 = ~(1 << (nextFree % 32));
            java.util.Arrays.fill(this.mValues, nextFree * i2, (nextFree + 1) * i2, 0);
            for (int i5 = 0; i5 < this.mSize; i5++) {
                int[] iArr = this.mValues;
                int i6 = (i5 * i2) + i3;
                iArr[i6] = iArr[i6] & i4;
            }
        }
        return binarySearch;
    }

    private void validateIndex(int i) {
        if (i >= this.mSize) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
    }

    private void validateIndex(int i, int i2) {
        validateIndex(i);
        validateIndex(i2);
    }

    private void growMatrix() {
        resizeMatrix(this.mOrder + 64);
    }

    private void resizeMatrix(int i) {
        if (i % 64 != 0) {
            throw new java.lang.IllegalArgumentException("matrix order " + i + " is not a multiple of 64");
        }
        int min = java.lang.Math.min(this.mOrder, i);
        boolean[] newUnpaddedBooleanArray = com.android.internal.util.ArrayUtils.newUnpaddedBooleanArray(i);
        java.lang.System.arraycopy(this.mInUse, 0, newUnpaddedBooleanArray, 0, min);
        int[] newUnpaddedIntArray = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(i);
        java.lang.System.arraycopy(this.mMap, 0, newUnpaddedIntArray, 0, min);
        int[] newUnpaddedIntArray2 = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(i);
        java.lang.System.arraycopy(this.mKeys, 0, newUnpaddedIntArray2, 0, min);
        int[] newUnpaddedIntArray3 = com.android.internal.util.ArrayUtils.newUnpaddedIntArray((i * i) / 32);
        for (int i2 = 0; i2 < min; i2++) {
            java.lang.System.arraycopy(this.mValues, (this.mOrder * i2) / 32, newUnpaddedIntArray3, (i * i2) / 32, min / 32);
        }
        this.mInUse = newUnpaddedBooleanArray;
        this.mMap = newUnpaddedIntArray;
        this.mKeys = newUnpaddedIntArray2;
        this.mValues = newUnpaddedIntArray3;
        this.mOrder = i;
    }

    private int nextFree(boolean z) {
        for (int i = 0; i < this.mInUse.length; i++) {
            if (!this.mInUse[i]) {
                this.mInUse[i] = z;
                return i;
            }
        }
        throw new java.lang.RuntimeException();
    }

    private int lastInuse() {
        for (int i = this.mOrder - 1; i >= 0; i--) {
            if (this.mInUse[i]) {
                for (int i2 = 0; i2 < this.mSize; i2++) {
                    if (this.mMap[i2] == i) {
                        return i2;
                    }
                }
                throw new java.lang.IndexOutOfBoundsException();
            }
        }
        return -1;
    }

    private void pack() {
        if (this.mSize == 0 || this.mSize == this.mOrder) {
            return;
        }
        while (true) {
            int nextFree = nextFree(false);
            if (nextFree < this.mSize) {
                this.mInUse[nextFree] = true;
                int lastInuse = lastInuse();
                int i = this.mMap[lastInuse];
                this.mInUse[i] = false;
                this.mMap[lastInuse] = nextFree;
                java.lang.System.arraycopy(this.mValues, (this.mOrder * i) / 32, this.mValues, (this.mOrder * nextFree) / 32, this.mOrder / 32);
                int i2 = i / 32;
                int i3 = 1 << (i % 32);
                int i4 = nextFree / 32;
                int i5 = 1 << (nextFree % 32);
                for (int i6 = 0; i6 < this.mOrder; i6++) {
                    if ((this.mValues[i2] & i3) == 0) {
                        int[] iArr = this.mValues;
                        iArr[i4] = iArr[i4] & (~i5);
                    } else {
                        int[] iArr2 = this.mValues;
                        iArr2[i4] = iArr2[i4] | i5;
                    }
                    i2 += this.mOrder / 32;
                    i4 += this.mOrder / 32;
                }
            } else {
                return;
            }
        }
    }

    public void compact() {
        pack();
        int i = (this.mOrder - this.mSize) / 64;
        if (i > 0) {
            resizeMatrix(this.mOrder - (i * 64));
        }
    }

    public int[] keys() {
        return java.util.Arrays.copyOf(this.mKeys, this.mSize);
    }

    public int capacity() {
        return this.mOrder;
    }

    public void setCapacity(int i) {
        if (i <= this.mOrder) {
            return;
        }
        if (i % 64 != 0) {
            i = ((i / 64) + 1) * 64;
        }
        resizeMatrix(i);
    }

    public int hashCode() {
        int hashCode = (((this.mSize * 31) + java.util.Arrays.hashCode(this.mKeys)) * 31) + java.util.Arrays.hashCode(this.mMap);
        for (int i = 0; i < this.mSize; i++) {
            int i2 = this.mMap[i];
            for (int i3 = 0; i3 < this.mSize; i3++) {
                hashCode = (hashCode * 31) + (valueAtInternal(i2, this.mMap[i3]) ? 1 : 0);
            }
        }
        return hashCode;
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.utils.WatchedSparseBooleanMatrix)) {
            return false;
        }
        com.android.server.utils.WatchedSparseBooleanMatrix watchedSparseBooleanMatrix = (com.android.server.utils.WatchedSparseBooleanMatrix) obj;
        if (this.mSize != watchedSparseBooleanMatrix.mSize || !java.util.Arrays.equals(this.mKeys, watchedSparseBooleanMatrix.mKeys)) {
            return false;
        }
        for (int i = 0; i < this.mSize; i++) {
            int i2 = this.mMap[i];
            for (int i3 = 0; i3 < this.mSize; i3++) {
                int i4 = this.mMap[i3];
                if (valueAtInternal(i2, i4) != watchedSparseBooleanMatrix.valueAtInternal(i2, i4)) {
                    return false;
                }
            }
        }
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    java.lang.String[] matrixToStringMeta() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < this.mSize; i++) {
            sb.append(this.mKeys[i]);
            if (i < this.mSize - 1) {
                sb.append(" ");
            }
        }
        java.lang.String substring = sb.substring(0);
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        for (int i2 = 0; i2 < this.mSize; i2++) {
            sb2.append(this.mMap[i2]);
            if (i2 < this.mSize - 1) {
                sb2.append(" ");
            }
        }
        java.lang.String substring2 = sb2.substring(0);
        java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
        for (int i3 = 0; i3 < this.mOrder; i3++) {
            sb3.append(this.mInUse[i3] ? "1" : "0");
        }
        return new java.lang.String[]{substring, substring2, sb3.substring(0)};
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    java.lang.String[] matrixToStringRaw() {
        java.lang.String[] strArr = new java.lang.String[this.mOrder];
        for (int i = 0; i < this.mOrder; i++) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(this.mOrder);
            for (int i2 = 0; i2 < this.mOrder; i2++) {
                sb.append(valueAtInternal(i, i2) ? "1" : "0");
            }
            strArr[i] = sb.substring(0);
        }
        return strArr;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    java.lang.String[] matrixToStringCooked() {
        java.lang.String[] strArr = new java.lang.String[this.mSize];
        for (int i = 0; i < this.mSize; i++) {
            int i2 = this.mMap[i];
            java.lang.StringBuilder sb = new java.lang.StringBuilder(this.mSize);
            for (int i3 = 0; i3 < this.mSize; i3++) {
                sb.append(valueAtInternal(i2, this.mMap[i3]) ? "1" : "0");
            }
            strArr[i] = sb.substring(0);
        }
        return strArr;
    }

    public java.lang.String[] matrixToString(boolean z) {
        java.lang.String[] matrixToStringCooked;
        java.lang.String[] matrixToStringMeta = matrixToStringMeta();
        if (z) {
            matrixToStringCooked = matrixToStringRaw();
        } else {
            matrixToStringCooked = matrixToStringCooked();
        }
        java.lang.String[] strArr = new java.lang.String[matrixToStringMeta.length + matrixToStringCooked.length];
        java.lang.System.arraycopy(matrixToStringMeta, 0, strArr, 0, matrixToStringMeta.length);
        java.lang.System.arraycopy(matrixToStringCooked, 0, strArr, matrixToStringMeta.length, matrixToStringCooked.length);
        return strArr;
    }

    public java.lang.String toString() {
        return "{" + this.mSize + "x" + this.mSize + "}";
    }

    private static int binarySearch(int[] iArr, int i, int i2) {
        int i3 = i - 1;
        int i4 = 0;
        while (i4 <= i3) {
            int i5 = (i4 + i3) >>> 1;
            int i6 = iArr[i5];
            if (i6 < i2) {
                i4 = i5 + 1;
            } else if (i6 > i2) {
                i3 = i5 - 1;
            } else {
                return i5;
            }
        }
        return ~i4;
    }
}
