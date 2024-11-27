package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class BooleanArrayList extends com.android.framework.protobuf.AbstractProtobufList<java.lang.Boolean> implements com.android.framework.protobuf.Internal.BooleanList, java.util.RandomAccess, com.android.framework.protobuf.PrimitiveNonBoxingCollection {
    private static final com.android.framework.protobuf.BooleanArrayList EMPTY_LIST = new com.android.framework.protobuf.BooleanArrayList(new boolean[0], 0);
    private boolean[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    public static com.android.framework.protobuf.BooleanArrayList emptyList() {
        return EMPTY_LIST;
    }

    BooleanArrayList() {
        this(new boolean[10], 0);
    }

    private BooleanArrayList(boolean[] zArr, int i) {
        this.array = zArr;
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
        if (!(obj instanceof com.android.framework.protobuf.BooleanArrayList)) {
            return super.equals(obj);
        }
        com.android.framework.protobuf.BooleanArrayList booleanArrayList = (com.android.framework.protobuf.BooleanArrayList) obj;
        if (this.size != booleanArrayList.size) {
            return false;
        }
        boolean[] zArr = booleanArrayList.array;
        for (int i = 0; i < this.size; i++) {
            if (this.array[i] != zArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.Collection, java.util.List
    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + com.android.framework.protobuf.Internal.hashBoolean(this.array[i2]);
        }
        return i;
    }

    @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
    /* renamed from: mutableCopyWithCapacity */
    public com.android.framework.protobuf.Internal.ProtobufList<java.lang.Boolean> mutableCopyWithCapacity2(int i) {
        if (i < this.size) {
            throw new java.lang.IllegalArgumentException();
        }
        return new com.android.framework.protobuf.BooleanArrayList(java.util.Arrays.copyOf(this.array, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public java.lang.Boolean get(int i) {
        return java.lang.Boolean.valueOf(getBoolean(i));
    }

    @Override // com.android.framework.protobuf.Internal.BooleanList
    public boolean getBoolean(int i) {
        ensureIndexInRange(i);
        return this.array[i];
    }

    @Override // java.util.AbstractList, java.util.List
    public int indexOf(java.lang.Object obj) {
        if (!(obj instanceof java.lang.Boolean)) {
            return -1;
        }
        boolean booleanValue = ((java.lang.Boolean) obj).booleanValue();
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.array[i] == booleanValue) {
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
    public java.lang.Boolean set(int i, java.lang.Boolean bool) {
        return java.lang.Boolean.valueOf(setBoolean(i, bool.booleanValue()));
    }

    @Override // com.android.framework.protobuf.Internal.BooleanList
    public boolean setBoolean(int i, boolean z) {
        ensureIsMutable();
        ensureIndexInRange(i);
        boolean z2 = this.array[i];
        this.array[i] = z;
        return z2;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(java.lang.Boolean bool) {
        addBoolean(bool.booleanValue());
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public void add(int i, java.lang.Boolean bool) {
        addBoolean(i, bool.booleanValue());
    }

    @Override // com.android.framework.protobuf.Internal.BooleanList
    public void addBoolean(boolean z) {
        ensureIsMutable();
        if (this.size == this.array.length) {
            boolean[] zArr = new boolean[((this.size * 3) / 2) + 1];
            java.lang.System.arraycopy(this.array, 0, zArr, 0, this.size);
            this.array = zArr;
        }
        boolean[] zArr2 = this.array;
        int i = this.size;
        this.size = i + 1;
        zArr2[i] = z;
    }

    private void addBoolean(int i, boolean z) {
        ensureIsMutable();
        if (i < 0 || i > this.size) {
            throw new java.lang.IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(i));
        }
        if (this.size < this.array.length) {
            java.lang.System.arraycopy(this.array, i, this.array, i + 1, this.size - i);
        } else {
            boolean[] zArr = new boolean[((this.size * 3) / 2) + 1];
            java.lang.System.arraycopy(this.array, 0, zArr, 0, i);
            java.lang.System.arraycopy(this.array, i, zArr, i + 1, this.size - i);
            this.array = zArr;
        }
        this.array[i] = z;
        this.size++;
        this.modCount++;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(java.util.Collection<? extends java.lang.Boolean> collection) {
        ensureIsMutable();
        com.android.framework.protobuf.Internal.checkNotNull(collection);
        if (!(collection instanceof com.android.framework.protobuf.BooleanArrayList)) {
            return super.addAll(collection);
        }
        com.android.framework.protobuf.BooleanArrayList booleanArrayList = (com.android.framework.protobuf.BooleanArrayList) collection;
        if (booleanArrayList.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < booleanArrayList.size) {
            throw new java.lang.OutOfMemoryError();
        }
        int i = this.size + booleanArrayList.size;
        if (i > this.array.length) {
            this.array = java.util.Arrays.copyOf(this.array, i);
        }
        java.lang.System.arraycopy(booleanArrayList.array, 0, this.array, this.size, booleanArrayList.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public java.lang.Boolean remove(int i) {
        ensureIsMutable();
        ensureIndexInRange(i);
        boolean z = this.array[i];
        if (i < this.size - 1) {
            java.lang.System.arraycopy(this.array, i + 1, this.array, i, (this.size - i) - 1);
        }
        this.size--;
        this.modCount++;
        return java.lang.Boolean.valueOf(z);
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
