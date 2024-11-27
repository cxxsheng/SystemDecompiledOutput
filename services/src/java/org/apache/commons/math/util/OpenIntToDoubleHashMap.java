package org.apache.commons.math.util;

/* loaded from: classes3.dex */
public class OpenIntToDoubleHashMap implements java.io.Serializable {
    private static final int DEFAULT_EXPECTED_SIZE = 16;
    protected static final byte FREE = 0;
    protected static final byte FULL = 1;
    private static final float LOAD_FACTOR = 0.5f;
    private static final int PERTURB_SHIFT = 5;
    protected static final byte REMOVED = 2;
    private static final int RESIZE_MULTIPLIER = 2;
    private static final long serialVersionUID = -3646337053166149105L;
    private transient int count;
    private int[] keys;
    private int mask;
    private final double missingEntries;
    private int size;
    private byte[] states;
    private double[] values;

    public OpenIntToDoubleHashMap() {
        this(16, Double.NaN);
    }

    public OpenIntToDoubleHashMap(double d) {
        this(16, d);
    }

    public OpenIntToDoubleHashMap(int i) {
        this(i, Double.NaN);
    }

    public OpenIntToDoubleHashMap(int i, double d) {
        int computeCapacity = computeCapacity(i);
        this.keys = new int[computeCapacity];
        this.values = new double[computeCapacity];
        this.states = new byte[computeCapacity];
        this.missingEntries = d;
        this.mask = computeCapacity - 1;
    }

    public OpenIntToDoubleHashMap(org.apache.commons.math.util.OpenIntToDoubleHashMap openIntToDoubleHashMap) {
        int length = openIntToDoubleHashMap.keys.length;
        this.keys = new int[length];
        java.lang.System.arraycopy(openIntToDoubleHashMap.keys, 0, this.keys, 0, length);
        this.values = new double[length];
        java.lang.System.arraycopy(openIntToDoubleHashMap.values, 0, this.values, 0, length);
        this.states = new byte[length];
        java.lang.System.arraycopy(openIntToDoubleHashMap.states, 0, this.states, 0, length);
        this.missingEntries = openIntToDoubleHashMap.missingEntries;
        this.size = openIntToDoubleHashMap.size;
        this.mask = openIntToDoubleHashMap.mask;
        this.count = openIntToDoubleHashMap.count;
    }

    private static int computeCapacity(int i) {
        if (i == 0) {
            return 1;
        }
        int ceil = (int) org.apache.commons.math.util.FastMath.ceil(i / 0.5f);
        if (java.lang.Integer.highestOneBit(ceil) == ceil) {
            return ceil;
        }
        return nextPowerOfTwo(ceil);
    }

    private static int nextPowerOfTwo(int i) {
        return java.lang.Integer.highestOneBit(i) << 1;
    }

    public double get(int i) {
        int hashOf = hashOf(i);
        int i2 = this.mask & hashOf;
        if (containsKey(i, i2)) {
            return this.values[i2];
        }
        if (this.states[i2] == 0) {
            return this.missingEntries;
        }
        int perturb = perturb(hashOf);
        int i3 = i2;
        while (this.states[i2] != 0) {
            i3 = probe(perturb, i3);
            i2 = this.mask & i3;
            if (!containsKey(i, i2)) {
                perturb >>= 5;
            } else {
                return this.values[i2];
            }
        }
        return this.missingEntries;
    }

    public boolean containsKey(int i) {
        int hashOf = hashOf(i);
        int i2 = this.mask & hashOf;
        if (containsKey(i, i2)) {
            return true;
        }
        if (this.states[i2] == 0) {
            return false;
        }
        int perturb = perturb(hashOf);
        int i3 = i2;
        while (this.states[i2] != 0) {
            i3 = probe(perturb, i3);
            i2 = this.mask & i3;
            if (containsKey(i, i2)) {
                return true;
            }
            perturb >>= 5;
        }
        return false;
    }

    public org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iterator() {
        return new org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator();
    }

    private static int perturb(int i) {
        return i & Integer.MAX_VALUE;
    }

    private int findInsertionIndex(int i) {
        return findInsertionIndex(this.keys, this.states, i, this.mask);
    }

    private static int findInsertionIndex(int[] iArr, byte[] bArr, int i, int i2) {
        int i3;
        int i4;
        int hashOf = hashOf(i);
        int i5 = hashOf & i2;
        if (bArr[i5] == 0) {
            return i5;
        }
        if (bArr[i5] == 1 && iArr[i5] == i) {
            return changeIndexSign(i5);
        }
        int perturb = perturb(hashOf);
        if (bArr[i5] != 1) {
            i3 = i5;
        } else {
            do {
                i5 = probe(perturb, i5);
                i4 = i5 & i2;
                perturb >>= 5;
                if (bArr[i4] != 1) {
                    break;
                }
            } while (iArr[i4] != i);
            i3 = i5;
            i5 = i4;
        }
        if (bArr[i5] == 0) {
            return i5;
        }
        if (bArr[i5] == 1) {
            return changeIndexSign(i5);
        }
        while (true) {
            i3 = probe(perturb, i3);
            int i6 = i3 & i2;
            if (bArr[i6] == 0) {
                return i5;
            }
            if (bArr[i6] == 1 && iArr[i6] == i) {
                return changeIndexSign(i6);
            }
            perturb >>= 5;
        }
    }

    private static int probe(int i, int i2) {
        return (i2 << 2) + i2 + i + 1;
    }

    private static int changeIndexSign(int i) {
        return (-i) - 1;
    }

    public int size() {
        return this.size;
    }

    public double remove(int i) {
        int hashOf = hashOf(i);
        int i2 = this.mask & hashOf;
        if (containsKey(i, i2)) {
            return doRemove(i2);
        }
        if (this.states[i2] == 0) {
            return this.missingEntries;
        }
        int perturb = perturb(hashOf);
        int i3 = i2;
        while (this.states[i2] != 0) {
            i3 = probe(perturb, i3);
            i2 = this.mask & i3;
            if (!containsKey(i, i2)) {
                perturb >>= 5;
            } else {
                return doRemove(i2);
            }
        }
        return this.missingEntries;
    }

    private boolean containsKey(int i, int i2) {
        return (i != 0 || this.states[i2] == 1) && this.keys[i2] == i;
    }

    private double doRemove(int i) {
        this.keys[i] = 0;
        this.states[i] = 2;
        double d = this.values[i];
        this.values[i] = this.missingEntries;
        this.size--;
        this.count++;
        return d;
    }

    public double put(int i, double d) {
        boolean z;
        int findInsertionIndex = findInsertionIndex(i);
        double d2 = this.missingEntries;
        if (findInsertionIndex >= 0) {
            z = true;
        } else {
            findInsertionIndex = changeIndexSign(findInsertionIndex);
            d2 = this.values[findInsertionIndex];
            z = false;
        }
        this.keys[findInsertionIndex] = i;
        this.states[findInsertionIndex] = 1;
        this.values[findInsertionIndex] = d;
        if (z) {
            this.size++;
            if (shouldGrowTable()) {
                growTable();
            }
            this.count++;
        }
        return d2;
    }

    private void growTable() {
        int length = this.states.length;
        int[] iArr = this.keys;
        double[] dArr = this.values;
        byte[] bArr = this.states;
        int i = length * 2;
        int[] iArr2 = new int[i];
        double[] dArr2 = new double[i];
        byte[] bArr2 = new byte[i];
        int i2 = i - 1;
        for (int i3 = 0; i3 < length; i3++) {
            if (bArr[i3] == 1) {
                int i4 = iArr[i3];
                int findInsertionIndex = findInsertionIndex(iArr2, bArr2, i4, i2);
                iArr2[findInsertionIndex] = i4;
                dArr2[findInsertionIndex] = dArr[i3];
                bArr2[findInsertionIndex] = 1;
            }
        }
        this.mask = i2;
        this.keys = iArr2;
        this.values = dArr2;
        this.states = bArr2;
    }

    private boolean shouldGrowTable() {
        return ((float) this.size) > ((float) (this.mask + 1)) * 0.5f;
    }

    private static int hashOf(int i) {
        int i2 = i ^ ((i >>> 20) ^ (i >>> 12));
        return (i2 >>> 4) ^ ((i2 >>> 7) ^ i2);
    }

    public class Iterator {
        private int current;
        private int next;
        private final int referenceCount;

        private Iterator() {
            this.referenceCount = org.apache.commons.math.util.OpenIntToDoubleHashMap.this.count;
            this.next = -1;
            try {
                advance();
            } catch (java.util.NoSuchElementException e) {
            }
        }

        public boolean hasNext() {
            return this.next >= 0;
        }

        public int key() throws java.util.ConcurrentModificationException, java.util.NoSuchElementException {
            if (this.referenceCount != org.apache.commons.math.util.OpenIntToDoubleHashMap.this.count) {
                throw org.apache.commons.math.MathRuntimeException.createConcurrentModificationException(org.apache.commons.math.exception.util.LocalizedFormats.MAP_MODIFIED_WHILE_ITERATING, new java.lang.Object[0]);
            }
            if (this.current >= 0) {
                return org.apache.commons.math.util.OpenIntToDoubleHashMap.this.keys[this.current];
            }
            throw org.apache.commons.math.MathRuntimeException.createNoSuchElementException(org.apache.commons.math.exception.util.LocalizedFormats.ITERATOR_EXHAUSTED, new java.lang.Object[0]);
        }

        public double value() throws java.util.ConcurrentModificationException, java.util.NoSuchElementException {
            if (this.referenceCount != org.apache.commons.math.util.OpenIntToDoubleHashMap.this.count) {
                throw org.apache.commons.math.MathRuntimeException.createConcurrentModificationException(org.apache.commons.math.exception.util.LocalizedFormats.MAP_MODIFIED_WHILE_ITERATING, new java.lang.Object[0]);
            }
            if (this.current >= 0) {
                return org.apache.commons.math.util.OpenIntToDoubleHashMap.this.values[this.current];
            }
            throw org.apache.commons.math.MathRuntimeException.createNoSuchElementException(org.apache.commons.math.exception.util.LocalizedFormats.ITERATOR_EXHAUSTED, new java.lang.Object[0]);
        }

        public void advance() throws java.util.ConcurrentModificationException, java.util.NoSuchElementException {
            byte[] bArr;
            int i;
            if (this.referenceCount != org.apache.commons.math.util.OpenIntToDoubleHashMap.this.count) {
                throw org.apache.commons.math.MathRuntimeException.createConcurrentModificationException(org.apache.commons.math.exception.util.LocalizedFormats.MAP_MODIFIED_WHILE_ITERATING, new java.lang.Object[0]);
            }
            this.current = this.next;
            do {
                try {
                    bArr = org.apache.commons.math.util.OpenIntToDoubleHashMap.this.states;
                    i = this.next + 1;
                    this.next = i;
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    this.next = -2;
                    if (this.current < 0) {
                        throw org.apache.commons.math.MathRuntimeException.createNoSuchElementException(org.apache.commons.math.exception.util.LocalizedFormats.ITERATOR_EXHAUSTED, new java.lang.Object[0]);
                    }
                    return;
                }
            } while (bArr[i] != 1);
        }
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.count = 0;
    }
}
