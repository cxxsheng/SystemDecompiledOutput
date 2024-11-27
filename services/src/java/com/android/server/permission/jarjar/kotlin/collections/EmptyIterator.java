package com.android.server.permission.jarjar.kotlin.collections;

/* compiled from: Collections.kt */
/* loaded from: classes2.dex */
public final class EmptyIterator implements java.util.ListIterator {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.jarjar.kotlin.collections.EmptyIterator INSTANCE = new com.android.server.permission.jarjar.kotlin.collections.EmptyIterator();

    @Override // java.util.ListIterator
    public /* bridge */ /* synthetic */ void add(java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public void remove() {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.ListIterator
    public /* bridge */ /* synthetic */ void set(java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    private EmptyIterator() {
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public boolean hasNext() {
        return false;
    }

    @Override // java.util.ListIterator
    public boolean hasPrevious() {
        return false;
    }

    @Override // java.util.ListIterator
    public int nextIndex() {
        return 0;
    }

    @Override // java.util.ListIterator
    public int previousIndex() {
        return -1;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    @org.jetbrains.annotations.NotNull
    public java.lang.Void next() {
        throw new java.util.NoSuchElementException();
    }

    @Override // java.util.ListIterator
    @org.jetbrains.annotations.NotNull
    public java.lang.Void previous() {
        throw new java.util.NoSuchElementException();
    }
}
