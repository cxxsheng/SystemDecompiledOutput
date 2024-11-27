package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class LongArrayList extends com.android.framework.protobuf.AbstractProtobufList<java.lang.Long> implements com.android.framework.protobuf.Internal.LongList, java.util.RandomAccess, com.android.framework.protobuf.PrimitiveNonBoxingCollection {
    private static final com.android.framework.protobuf.LongArrayList EMPTY_LIST = new com.android.framework.protobuf.LongArrayList(new long[0], 0);
    private long[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    public static com.android.framework.protobuf.LongArrayList emptyList() {
        return EMPTY_LIST;
    }

    LongArrayList() {
        this(new long[10], 0);
    }

    private LongArrayList(long[] jArr, int i) {
        this.array = jArr;
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
        if (!(obj instanceof com.android.framework.protobuf.LongArrayList)) {
            return super.equals(obj);
        }
        com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) obj;
        if (this.size != longArrayList.size) {
            return false;
        }
        long[] jArr = longArrayList.array;
        for (int i = 0; i < this.size; i++) {
            if (this.array[i] != jArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.Collection, java.util.List
    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + com.android.framework.protobuf.Internal.hashLong(this.array[i2]);
        }
        return i;
    }

    @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
    /* renamed from: mutableCopyWithCapacity */
    public com.android.framework.protobuf.Internal.ProtobufList<java.lang.Long> mutableCopyWithCapacity2(int i) {
        if (i < this.size) {
            throw new java.lang.IllegalArgumentException();
        }
        return new com.android.framework.protobuf.LongArrayList(java.util.Arrays.copyOf(this.array, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public java.lang.Long get(int i) {
        return java.lang.Long.valueOf(getLong(i));
    }

    @Override // com.android.framework.protobuf.Internal.LongList
    public long getLong(int i) {
        ensureIndexInRange(i);
        return this.array[i];
    }

    @Override // java.util.AbstractList, java.util.List
    public int indexOf(java.lang.Object obj) {
        if (!(obj instanceof java.lang.Long)) {
            return -1;
        }
        long longValue = ((java.lang.Long) obj).longValue();
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.array[i] == longValue) {
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
    public java.lang.Long set(int i, java.lang.Long l) {
        return java.lang.Long.valueOf(setLong(i, l.longValue()));
    }

    @Override // com.android.framework.protobuf.Internal.LongList
    public long setLong(int i, long j) {
        ensureIsMutable();
        ensureIndexInRange(i);
        long j2 = this.array[i];
        this.array[i] = j;
        return j2;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(java.lang.Long l) {
        addLong(l.longValue());
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public void add(int i, java.lang.Long l) {
        addLong(i, l.longValue());
    }

    @Override // com.android.framework.protobuf.Internal.LongList
    public void addLong(long j) {
        ensureIsMutable();
        if (this.size == this.array.length) {
            long[] jArr = new long[((this.size * 3) / 2) + 1];
            java.lang.System.arraycopy(this.array, 0, jArr, 0, this.size);
            this.array = jArr;
        }
        long[] jArr2 = this.array;
        int i = this.size;
        this.size = i + 1;
        jArr2[i] = j;
    }

    private void addLong(int i, long j) {
        ensureIsMutable();
        if (i < 0 || i > this.size) {
            throw new java.lang.IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(i));
        }
        if (this.size < this.array.length) {
            java.lang.System.arraycopy(this.array, i, this.array, i + 1, this.size - i);
        } else {
            long[] jArr = new long[((this.size * 3) / 2) + 1];
            java.lang.System.arraycopy(this.array, 0, jArr, 0, i);
            java.lang.System.arraycopy(this.array, i, jArr, i + 1, this.size - i);
            this.array = jArr;
        }
        this.array[i] = j;
        this.size++;
        this.modCount++;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(java.util.Collection<? extends java.lang.Long> collection) {
        ensureIsMutable();
        com.android.framework.protobuf.Internal.checkNotNull(collection);
        if (!(collection instanceof com.android.framework.protobuf.LongArrayList)) {
            return super.addAll(collection);
        }
        com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) collection;
        if (longArrayList.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < longArrayList.size) {
            throw new java.lang.OutOfMemoryError();
        }
        int i = this.size + longArrayList.size;
        if (i > this.array.length) {
            this.array = java.util.Arrays.copyOf(this.array, i);
        }
        java.lang.System.arraycopy(longArrayList.array, 0, this.array, this.size, longArrayList.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public java.lang.Long remove(int i) {
        ensureIsMutable();
        ensureIndexInRange(i);
        long j = this.array[i];
        if (i < this.size - 1) {
            java.lang.System.arraycopy(this.array, i + 1, this.array, i, (this.size - i) - 1);
        }
        this.size--;
        this.modCount++;
        return java.lang.Long.valueOf(j);
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
