package com.android.server.location.contexthub;

/* loaded from: classes2.dex */
public class ConcurrentLinkedEvictingDeque<E> extends java.util.concurrent.ConcurrentLinkedDeque<E> {
    private int mSize;

    ConcurrentLinkedEvictingDeque(int i) {
        this.mSize = i;
    }

    @Override // java.util.concurrent.ConcurrentLinkedDeque, java.util.AbstractCollection, java.util.Collection, java.util.Deque, java.util.Queue
    public boolean add(E e) {
        boolean add;
        synchronized (this) {
            try {
                if (size() == this.mSize) {
                    poll();
                }
                add = super.add(e);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return add;
    }
}
