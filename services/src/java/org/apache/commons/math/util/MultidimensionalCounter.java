package org.apache.commons.math.util;

/* loaded from: classes3.dex */
public class MultidimensionalCounter implements java.lang.Iterable<java.lang.Integer> {
    private final int dimension;
    private final int last;
    private final int[] size;
    private final int totalSize;
    private final int[] uniCounterOffset;

    public class Iterator implements java.util.Iterator<java.lang.Integer> {
        private int count = -1;
        private final int[] counter;

        Iterator() {
            this.counter = new int[org.apache.commons.math.util.MultidimensionalCounter.this.dimension];
            this.counter[org.apache.commons.math.util.MultidimensionalCounter.this.last] = -1;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            for (int i = 0; i < org.apache.commons.math.util.MultidimensionalCounter.this.dimension; i++) {
                if (this.counter[i] != org.apache.commons.math.util.MultidimensionalCounter.this.size[i] - 1) {
                    return true;
                }
            }
            return false;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public java.lang.Integer next() {
            int i = org.apache.commons.math.util.MultidimensionalCounter.this.last;
            while (true) {
                if (i >= 0) {
                    if (this.counter[i] == org.apache.commons.math.util.MultidimensionalCounter.this.size[i] - 1) {
                        this.counter[i] = 0;
                        i--;
                    } else {
                        int[] iArr = this.counter;
                        iArr[i] = iArr[i] + 1;
                        break;
                    }
                } else {
                    break;
                }
            }
            int i2 = this.count + 1;
            this.count = i2;
            return java.lang.Integer.valueOf(i2);
        }

        public int getCount() {
            return this.count;
        }

        public int[] getCounts() {
            return org.apache.commons.math.util.MultidimensionalCounter.this.copyOf(this.counter, org.apache.commons.math.util.MultidimensionalCounter.this.dimension);
        }

        public int getCount(int i) {
            return this.counter[i];
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public MultidimensionalCounter(int... iArr) {
        this.dimension = iArr.length;
        this.size = copyOf(iArr, this.dimension);
        this.uniCounterOffset = new int[this.dimension];
        this.last = this.dimension - 1;
        int i = iArr[this.last];
        int i2 = 0;
        while (i2 < this.last) {
            int i3 = i2 + 1;
            int i4 = 1;
            for (int i5 = i3; i5 < this.dimension; i5++) {
                i4 *= iArr[i5];
            }
            this.uniCounterOffset[i2] = i4;
            i *= iArr[i2];
            i2 = i3;
        }
        this.uniCounterOffset[this.last] = 0;
        if (i <= 0) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(java.lang.Integer.valueOf(i));
        }
        this.totalSize = i;
    }

    @Override // java.lang.Iterable
    public java.util.Iterator<java.lang.Integer> iterator() {
        return new org.apache.commons.math.util.MultidimensionalCounter.Iterator();
    }

    public int getDimension() {
        return this.dimension;
    }

    public int[] getCounts(int i) {
        if (i < 0 || i >= this.totalSize) {
            throw new org.apache.commons.math.exception.OutOfRangeException(java.lang.Integer.valueOf(i), 0, java.lang.Integer.valueOf(this.totalSize));
        }
        int[] iArr = new int[this.dimension];
        int i2 = 0;
        for (int i3 = 0; i3 < this.last; i3++) {
            int i4 = this.uniCounterOffset[i3];
            int i5 = 0;
            while (i2 <= i) {
                i2 += i4;
                i5++;
            }
            i2 -= i4;
            iArr[i3] = i5 - 1;
        }
        int i6 = 1;
        while (i2 < i) {
            i2 += i6;
            i6++;
        }
        iArr[this.last] = i6 - 1;
        return iArr;
    }

    public int getCount(int... iArr) throws org.apache.commons.math.exception.OutOfRangeException {
        if (iArr.length != this.dimension) {
            throw new org.apache.commons.math.exception.DimensionMismatchException(iArr.length, this.dimension);
        }
        int i = 0;
        for (int i2 = 0; i2 < this.dimension; i2++) {
            int i3 = iArr[i2];
            if (i3 < 0 || i3 >= this.size[i2]) {
                throw new org.apache.commons.math.exception.OutOfRangeException(java.lang.Integer.valueOf(i3), 0, java.lang.Integer.valueOf(this.size[i2] - 1));
            }
            i += this.uniCounterOffset[i2] * iArr[i2];
        }
        return i + iArr[this.last];
    }

    public int getSize() {
        return this.totalSize;
    }

    public int[] getSizes() {
        return copyOf(this.size, this.dimension);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < this.dimension; i++) {
            sb.append("[");
            sb.append(getCount(i));
            sb.append("]");
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] copyOf(int[] iArr, int i) {
        int[] iArr2 = new int[i];
        java.lang.System.arraycopy(iArr, 0, iArr2, 0, java.lang.Math.min(iArr.length, i));
        return iArr2;
    }
}
