package com.android.server.permission.jarjar.kotlin.collections;

/* compiled from: PrimitiveIterators.kt */
/* loaded from: classes2.dex */
public abstract class IntIterator implements java.util.Iterator<java.lang.Integer> {
    public abstract int nextInt();

    @Override // java.util.Iterator
    public void remove() {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Iterator
    public /* bridge */ /* synthetic */ java.lang.Integer next() {
        return java.lang.Integer.valueOf(nextInt());
    }
}
