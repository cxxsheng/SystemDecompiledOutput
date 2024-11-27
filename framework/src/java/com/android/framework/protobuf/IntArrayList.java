package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class IntArrayList extends com.android.framework.protobuf.AbstractProtobufList<java.lang.Integer> implements com.android.framework.protobuf.Internal.IntList, java.util.RandomAccess, com.android.framework.protobuf.PrimitiveNonBoxingCollection {
    private static final com.android.framework.protobuf.IntArrayList EMPTY_LIST = new com.android.framework.protobuf.IntArrayList(new int[0], 0);
    private int[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    public static com.android.framework.protobuf.IntArrayList emptyList() {
        return EMPTY_LIST;
    }

    IntArrayList() {
        this(new int[10], 0);
    }

    private IntArrayList(int[] iArr, int i) {
        this.array = iArr;
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
        if (!(obj instanceof com.android.framework.protobuf.IntArrayList)) {
            return super.equals(obj);
        }
        com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) obj;
        if (this.size != intArrayList.size) {
            return false;
        }
        int[] iArr = intArrayList.array;
        for (int i = 0; i < this.size; i++) {
            if (this.array[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.Collection, java.util.List
    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + this.array[i2];
        }
        return i;
    }

    @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
    /* renamed from: mutableCopyWithCapacity */
    public com.android.framework.protobuf.Internal.ProtobufList<java.lang.Integer> mutableCopyWithCapacity2(int i) {
        if (i < this.size) {
            throw new java.lang.IllegalArgumentException();
        }
        return new com.android.framework.protobuf.IntArrayList(java.util.Arrays.copyOf(this.array, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public java.lang.Integer get(int i) {
        return java.lang.Integer.valueOf(getInt(i));
    }

    @Override // com.android.framework.protobuf.Internal.IntList
    public int getInt(int i) {
        ensureIndexInRange(i);
        return this.array[i];
    }

    @Override // java.util.AbstractList, java.util.List
    public int indexOf(java.lang.Object obj) {
        if (!(obj instanceof java.lang.Integer)) {
            return -1;
        }
        int intValue = ((java.lang.Integer) obj).intValue();
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.array[i] == intValue) {
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
    public java.lang.Integer set(int i, java.lang.Integer num) {
        return java.lang.Integer.valueOf(setInt(i, num.intValue()));
    }

    @Override // com.android.framework.protobuf.Internal.IntList
    public int setInt(int i, int i2) {
        ensureIsMutable();
        ensureIndexInRange(i);
        int i3 = this.array[i];
        this.array[i] = i2;
        return i3;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(java.lang.Integer num) {
        addInt(num.intValue());
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public void add(int i, java.lang.Integer num) {
        addInt(i, num.intValue());
    }

    @Override // com.android.framework.protobuf.Internal.IntList
    public void addInt(int i) {
        ensureIsMutable();
        if (this.size == this.array.length) {
            int[] iArr = new int[((this.size * 3) / 2) + 1];
            java.lang.System.arraycopy(this.array, 0, iArr, 0, this.size);
            this.array = iArr;
        }
        int[] iArr2 = this.array;
        int i2 = this.size;
        this.size = i2 + 1;
        iArr2[i2] = i;
    }

    private void addInt(int i, int i2) {
        ensureIsMutable();
        if (i < 0 || i > this.size) {
            throw new java.lang.IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(i));
        }
        if (this.size < this.array.length) {
            java.lang.System.arraycopy(this.array, i, this.array, i + 1, this.size - i);
        } else {
            int[] iArr = new int[((this.size * 3) / 2) + 1];
            java.lang.System.arraycopy(this.array, 0, iArr, 0, i);
            java.lang.System.arraycopy(this.array, i, iArr, i + 1, this.size - i);
            this.array = iArr;
        }
        this.array[i] = i2;
        this.size++;
        this.modCount++;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(java.util.Collection<? extends java.lang.Integer> collection) {
        ensureIsMutable();
        com.android.framework.protobuf.Internal.checkNotNull(collection);
        if (!(collection instanceof com.android.framework.protobuf.IntArrayList)) {
            return super.addAll(collection);
        }
        com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) collection;
        if (intArrayList.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < intArrayList.size) {
            throw new java.lang.OutOfMemoryError();
        }
        int i = this.size + intArrayList.size;
        if (i > this.array.length) {
            this.array = java.util.Arrays.copyOf(this.array, i);
        }
        java.lang.System.arraycopy(intArrayList.array, 0, this.array, this.size, intArrayList.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public java.lang.Integer remove(int i) {
        ensureIsMutable();
        ensureIndexInRange(i);
        int i2 = this.array[i];
        if (i < this.size - 1) {
            java.lang.System.arraycopy(this.array, i + 1, this.array, i, (this.size - i) - 1);
        }
        this.size--;
        this.modCount++;
        return java.lang.Integer.valueOf(i2);
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
