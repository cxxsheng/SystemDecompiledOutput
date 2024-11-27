package com.android.server.permission.jarjar.kotlin.collections;

/* compiled from: Collections.kt */
/* loaded from: classes2.dex */
public final class EmptyList implements java.util.List, java.io.Serializable, java.util.RandomAccess {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.jarjar.kotlin.collections.EmptyList INSTANCE = new com.android.server.permission.jarjar.kotlin.collections.EmptyList();
    private static final long serialVersionUID = -7390468764508069838L;

    @Override // java.util.List
    public /* bridge */ /* synthetic */ void add(int i, java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public /* bridge */ /* synthetic */ boolean add(java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public boolean addAll(int i, java.util.Collection collection) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(java.util.Collection collection) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public /* bridge */ /* synthetic */ java.lang.Object remove(int i) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(java.util.Collection collection) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(java.util.Collection collection) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public /* bridge */ /* synthetic */ java.lang.Object set(int i, java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public java.lang.Object[] toArray() {
        return com.android.server.permission.jarjar.kotlin.jvm.internal.CollectionToArray.toArray(this);
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(tArr, "array");
        return (T[]) com.android.server.permission.jarjar.kotlin.jvm.internal.CollectionToArray.toArray(this, tArr);
    }

    private EmptyList() {
    }

    @Override // java.util.List, java.util.Collection
    public final /* bridge */ boolean contains(java.lang.Object element) {
        if (element instanceof java.lang.Void) {
            return contains((java.lang.Void) element);
        }
        return false;
    }

    @Override // java.util.List
    public final /* bridge */ int indexOf(java.lang.Object element) {
        if (element instanceof java.lang.Void) {
            return indexOf((java.lang.Void) element);
        }
        return -1;
    }

    @Override // java.util.List
    public final /* bridge */ int lastIndexOf(java.lang.Object element) {
        if (element instanceof java.lang.Void) {
            return lastIndexOf((java.lang.Void) element);
        }
        return -1;
    }

    @Override // java.util.List, java.util.Collection
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(@org.jetbrains.annotations.Nullable java.lang.Object other) {
        return (other instanceof java.util.List) && ((java.util.List) other).isEmpty();
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return 1;
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return "[]";
    }

    public int getSize() {
        return 0;
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return true;
    }

    public boolean contains(@org.jetbrains.annotations.NotNull java.lang.Void element) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(element, "element");
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(@org.jetbrains.annotations.NotNull java.util.Collection elements) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(elements, "elements");
        return elements.isEmpty();
    }

    @Override // java.util.List
    @org.jetbrains.annotations.NotNull
    public java.lang.Void get(int index) {
        throw new java.lang.IndexOutOfBoundsException("Empty list doesn't contain element at index " + index + '.');
    }

    public int indexOf(@org.jetbrains.annotations.NotNull java.lang.Void element) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(element, "element");
        return -1;
    }

    public int lastIndexOf(@org.jetbrains.annotations.NotNull java.lang.Void element) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(element, "element");
        return -1;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    @org.jetbrains.annotations.NotNull
    public java.util.Iterator iterator() {
        return com.android.server.permission.jarjar.kotlin.collections.EmptyIterator.INSTANCE;
    }

    @Override // java.util.List
    @org.jetbrains.annotations.NotNull
    public java.util.ListIterator listIterator() {
        return com.android.server.permission.jarjar.kotlin.collections.EmptyIterator.INSTANCE;
    }

    @Override // java.util.List
    @org.jetbrains.annotations.NotNull
    public java.util.ListIterator listIterator(int index) {
        if (index != 0) {
            throw new java.lang.IndexOutOfBoundsException("Index: " + index);
        }
        return com.android.server.permission.jarjar.kotlin.collections.EmptyIterator.INSTANCE;
    }

    @Override // java.util.List
    @org.jetbrains.annotations.NotNull
    public java.util.List subList(int fromIndex, int toIndex) {
        if (fromIndex == 0 && toIndex == 0) {
            return this;
        }
        throw new java.lang.IndexOutOfBoundsException("fromIndex: " + fromIndex + ", toIndex: " + toIndex);
    }

    private final java.lang.Object readResolve() {
        return INSTANCE;
    }
}
