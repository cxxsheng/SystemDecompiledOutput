package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class DoubleArrayList extends com.android.framework.protobuf.AbstractProtobufList<java.lang.Double> implements com.android.framework.protobuf.Internal.DoubleList, java.util.RandomAccess, com.android.framework.protobuf.PrimitiveNonBoxingCollection {
    private static final com.android.framework.protobuf.DoubleArrayList EMPTY_LIST = new com.android.framework.protobuf.DoubleArrayList(new double[0], 0);
    private double[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    public static com.android.framework.protobuf.DoubleArrayList emptyList() {
        return EMPTY_LIST;
    }

    DoubleArrayList() {
        this(new double[10], 0);
    }

    private DoubleArrayList(double[] dArr, int i) {
        this.array = dArr;
        this.size = i;
    }

    @Override // java.util.AbstractList
    protected void removeRange(int i, int i2) {
        ensureIsMutable();
        if (i2 < i) {
            throw new java.lang.IndexOutOfBoundsException("toIndex < fromIndex");
        }
        java.lang.System.arraycopy(this.array, i2, this.array, i, this.size - i2);
        this.size -= i2 - i;
        this.modCount++;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.Collection, java.util.List
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.framework.protobuf.DoubleArrayList)) {
            return super.equals(obj);
        }
        com.android.framework.protobuf.DoubleArrayList doubleArrayList = (com.android.framework.protobuf.DoubleArrayList) obj;
        if (this.size != doubleArrayList.size) {
            return false;
        }
        double[] dArr = doubleArrayList.array;
        for (int i = 0; i < this.size; i++) {
            if (java.lang.Double.doubleToLongBits(this.array[i]) != java.lang.Double.doubleToLongBits(dArr[i])) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.Collection, java.util.List
    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + com.android.framework.protobuf.Internal.hashLong(java.lang.Double.doubleToLongBits(this.array[i2]));
        }
        return i;
    }

    @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
    /* renamed from: mutableCopyWithCapacity */
    public com.android.framework.protobuf.Internal.ProtobufList<java.lang.Double> mutableCopyWithCapacity2(int i) {
        if (i < this.size) {
            throw new java.lang.IllegalArgumentException();
        }
        return new com.android.framework.protobuf.DoubleArrayList(java.util.Arrays.copyOf(this.array, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public java.lang.Double get(int i) {
        return java.lang.Double.valueOf(getDouble(i));
    }

    @Override // com.android.framework.protobuf.Internal.DoubleList
    public double getDouble(int i) {
        ensureIndexInRange(i);
        return this.array[i];
    }

    @Override // java.util.AbstractList, java.util.List
    public int indexOf(java.lang.Object obj) {
        if (!(obj instanceof java.lang.Double)) {
            return -1;
        }
        double doubleValue = ((java.lang.Double) obj).doubleValue();
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.array[i] == doubleValue) {
                return i;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(java.lang.Object obj) {
        return indexOf(obj) != -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.size;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public java.lang.Double set(int i, java.lang.Double d) {
        return java.lang.Double.valueOf(setDouble(i, d.doubleValue()));
    }

    @Override // com.android.framework.protobuf.Internal.DoubleList
    public double setDouble(int i, double d) {
        ensureIsMutable();
        ensureIndexInRange(i);
        double d2 = this.array[i];
        this.array[i] = d;
        return d2;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(java.lang.Double d) {
        addDouble(d.doubleValue());
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public void add(int i, java.lang.Double d) {
        addDouble(i, d.doubleValue());
    }

    @Override // com.android.framework.protobuf.Internal.DoubleList
    public void addDouble(double d) {
        ensureIsMutable();
        if (this.size == this.array.length) {
            double[] dArr = new double[((this.size * 3) / 2) + 1];
            java.lang.System.arraycopy(this.array, 0, dArr, 0, this.size);
            this.array = dArr;
        }
        double[] dArr2 = this.array;
        int i = this.size;
        this.size = i + 1;
        dArr2[i] = d;
    }

    private void addDouble(int i, double d) {
        ensureIsMutable();
        if (i < 0 || i > this.size) {
            throw new java.lang.IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(i));
        }
        if (this.size < this.array.length) {
            java.lang.System.arraycopy(this.array, i, this.array, i + 1, this.size - i);
        } else {
            double[] dArr = new double[((this.size * 3) / 2) + 1];
            java.lang.System.arraycopy(this.array, 0, dArr, 0, i);
            java.lang.System.arraycopy(this.array, i, dArr, i + 1, this.size - i);
            this.array = dArr;
        }
        this.array[i] = d;
        this.size++;
        this.modCount++;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(java.util.Collection<? extends java.lang.Double> collection) {
        ensureIsMutable();
        com.android.framework.protobuf.Internal.checkNotNull(collection);
        if (!(collection instanceof com.android.framework.protobuf.DoubleArrayList)) {
            return super.addAll(collection);
        }
        com.android.framework.protobuf.DoubleArrayList doubleArrayList = (com.android.framework.protobuf.DoubleArrayList) collection;
        if (doubleArrayList.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < doubleArrayList.size) {
            throw new java.lang.OutOfMemoryError();
        }
        int i = this.size + doubleArrayList.size;
        if (i > this.array.length) {
            this.array = java.util.Arrays.copyOf(this.array, i);
        }
        java.lang.System.arraycopy(doubleArrayList.array, 0, this.array, this.size, doubleArrayList.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public java.lang.Double remove(int i) {
        ensureIsMutable();
        ensureIndexInRange(i);
        double d = this.array[i];
        if (i < this.size - 1) {
            java.lang.System.arraycopy(this.array, i + 1, this.array, i, (this.size - i) - 1);
        }
        this.size--;
        this.modCount++;
        return java.lang.Double.valueOf(d);
    }

    private void ensureIndexInRange(int i) {
        if (i < 0 || i >= this.size) {
            throw new java.lang.IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(i));
        }
    }

    private java.lang.String makeOutOfBoundsExceptionMessage(int i) {
        return "Index:" + i + ", Size:" + this.size;
    }
}
