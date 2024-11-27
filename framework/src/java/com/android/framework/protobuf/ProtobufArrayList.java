package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class ProtobufArrayList<E> extends com.android.framework.protobuf.AbstractProtobufList<E> implements java.util.RandomAccess {
    private static final com.android.framework.protobuf.ProtobufArrayList<java.lang.Object> EMPTY_LIST = new com.android.framework.protobuf.ProtobufArrayList<>(new java.lang.Object[0], 0);
    private E[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    public static <E> com.android.framework.protobuf.ProtobufArrayList<E> emptyList() {
        return (com.android.framework.protobuf.ProtobufArrayList<E>) EMPTY_LIST;
    }

    ProtobufArrayList() {
        this(new java.lang.Object[10], 0);
    }

    private ProtobufArrayList(E[] eArr, int i) {
        this.array = eArr;
        this.size = i;
    }

    @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
    /* renamed from: mutableCopyWithCapacity */
    public com.android.framework.protobuf.ProtobufArrayList<E> mutableCopyWithCapacity2(int i) {
        if (i < this.size) {
            throw new java.lang.IllegalArgumentException();
        }
        return new com.android.framework.protobuf.ProtobufArrayList<>(java.util.Arrays.copyOf(this.array, i), this.size);
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(E e) {
        ensureIsMutable();
        if (this.size == this.array.length) {
            this.array = (E[]) java.util.Arrays.copyOf(this.array, ((this.size * 3) / 2) + 1);
        }
        E[] eArr = this.array;
        int i = this.size;
        this.size = i + 1;
        eArr[i] = e;
        this.modCount++;
        return true;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public void add(int i, E e) {
        ensureIsMutable();
        if (i < 0 || i > this.size) {
            throw new java.lang.IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(i));
        }
        if (this.size < this.array.length) {
            java.lang.System.arraycopy(this.array, i, this.array, i + 1, this.size - i);
        } else {
            E[] eArr = (E[]) createArray(((this.size * 3) / 2) + 1);
            java.lang.System.arraycopy(this.array, 0, eArr, 0, i);
            java.lang.System.arraycopy(this.array, i, eArr, i + 1, this.size - i);
            this.array = eArr;
        }
        this.array[i] = e;
        this.size++;
        this.modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public E get(int i) {
        ensureIndexInRange(i);
        return this.array[i];
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public E remove(int i) {
        ensureIsMutable();
        ensureIndexInRange(i);
        E e = this.array[i];
        if (i < this.size - 1) {
            java.lang.System.arraycopy(this.array, i + 1, this.array, i, (this.size - i) - 1);
        }
        this.size--;
        this.modCount++;
        return e;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public E set(int i, E e) {
        ensureIsMutable();
        ensureIndexInRange(i);
        E e2 = this.array[i];
        this.array[i] = e;
        this.modCount++;
        return e2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.size;
    }

    private static <E> E[] createArray(int i) {
        return (E[]) new java.lang.Object[i];
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
