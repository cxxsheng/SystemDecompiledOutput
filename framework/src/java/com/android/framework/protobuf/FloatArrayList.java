package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class FloatArrayList extends com.android.framework.protobuf.AbstractProtobufList<java.lang.Float> implements com.android.framework.protobuf.Internal.FloatList, java.util.RandomAccess, com.android.framework.protobuf.PrimitiveNonBoxingCollection {
    private static final com.android.framework.protobuf.FloatArrayList EMPTY_LIST = new com.android.framework.protobuf.FloatArrayList(new float[0], 0);
    private float[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    public static com.android.framework.protobuf.FloatArrayList emptyList() {
        return EMPTY_LIST;
    }

    FloatArrayList() {
        this(new float[10], 0);
    }

    private FloatArrayList(float[] fArr, int i) {
        this.array = fArr;
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
        if (!(obj instanceof com.android.framework.protobuf.FloatArrayList)) {
            return super.equals(obj);
        }
        com.android.framework.protobuf.FloatArrayList floatArrayList = (com.android.framework.protobuf.FloatArrayList) obj;
        if (this.size != floatArrayList.size) {
            return false;
        }
        float[] fArr = floatArrayList.array;
        for (int i = 0; i < this.size; i++) {
            if (java.lang.Float.floatToIntBits(this.array[i]) != java.lang.Float.floatToIntBits(fArr[i])) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.Collection, java.util.List
    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + java.lang.Float.floatToIntBits(this.array[i2]);
        }
        return i;
    }

    @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
    /* renamed from: mutableCopyWithCapacity */
    public com.android.framework.protobuf.Internal.ProtobufList<java.lang.Float> mutableCopyWithCapacity2(int i) {
        if (i < this.size) {
            throw new java.lang.IllegalArgumentException();
        }
        return new com.android.framework.protobuf.FloatArrayList(java.util.Arrays.copyOf(this.array, i), this.size);
    }

    @Override // java.util.AbstractList, java.util.List
    public java.lang.Float get(int i) {
        return java.lang.Float.valueOf(getFloat(i));
    }

    @Override // com.android.framework.protobuf.Internal.FloatList
    public float getFloat(int i) {
        ensureIndexInRange(i);
        return this.array[i];
    }

    @Override // java.util.AbstractList, java.util.List
    public int indexOf(java.lang.Object obj) {
        if (!(obj instanceof java.lang.Float)) {
            return -1;
        }
        float floatValue = ((java.lang.Float) obj).floatValue();
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.array[i] == floatValue) {
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
    public java.lang.Float set(int i, java.lang.Float f) {
        return java.lang.Float.valueOf(setFloat(i, f.floatValue()));
    }

    @Override // com.android.framework.protobuf.Internal.FloatList
    public float setFloat(int i, float f) {
        ensureIsMutable();
        ensureIndexInRange(i);
        float f2 = this.array[i];
        this.array[i] = f;
        return f2;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(java.lang.Float f) {
        addFloat(f.floatValue());
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public void add(int i, java.lang.Float f) {
        addFloat(i, f.floatValue());
    }

    @Override // com.android.framework.protobuf.Internal.FloatList
    public void addFloat(float f) {
        ensureIsMutable();
        if (this.size == this.array.length) {
            float[] fArr = new float[((this.size * 3) / 2) + 1];
            java.lang.System.arraycopy(this.array, 0, fArr, 0, this.size);
            this.array = fArr;
        }
        float[] fArr2 = this.array;
        int i = this.size;
        this.size = i + 1;
        fArr2[i] = f;
    }

    private void addFloat(int i, float f) {
        ensureIsMutable();
        if (i < 0 || i > this.size) {
            throw new java.lang.IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(i));
        }
        if (this.size < this.array.length) {
            java.lang.System.arraycopy(this.array, i, this.array, i + 1, this.size - i);
        } else {
            float[] fArr = new float[((this.size * 3) / 2) + 1];
            java.lang.System.arraycopy(this.array, 0, fArr, 0, i);
            java.lang.System.arraycopy(this.array, i, fArr, i + 1, this.size - i);
            this.array = fArr;
        }
        this.array[i] = f;
        this.size++;
        this.modCount++;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(java.util.Collection<? extends java.lang.Float> collection) {
        ensureIsMutable();
        com.android.framework.protobuf.Internal.checkNotNull(collection);
        if (!(collection instanceof com.android.framework.protobuf.FloatArrayList)) {
            return super.addAll(collection);
        }
        com.android.framework.protobuf.FloatArrayList floatArrayList = (com.android.framework.protobuf.FloatArrayList) collection;
        if (floatArrayList.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size < floatArrayList.size) {
            throw new java.lang.OutOfMemoryError();
        }
        int i = this.size + floatArrayList.size;
        if (i > this.array.length) {
            this.array = java.util.Arrays.copyOf(this.array, i);
        }
        java.lang.System.arraycopy(floatArrayList.array, 0, this.array, this.size, floatArrayList.size);
        this.size = i;
        this.modCount++;
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public java.lang.Float remove(int i) {
        ensureIsMutable();
        ensureIndexInRange(i);
        float f = this.array[i];
        if (i < this.size - 1) {
            java.lang.System.arraycopy(this.array, i + 1, this.array, i, (this.size - i) - 1);
        }
        this.size--;
        this.modCount++;
        return java.lang.Float.valueOf(f);
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
