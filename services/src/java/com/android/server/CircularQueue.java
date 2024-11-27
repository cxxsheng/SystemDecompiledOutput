package com.android.server;

/* loaded from: classes.dex */
public class CircularQueue<K, V> extends java.util.LinkedList<K> {
    private final android.util.ArrayMap<K, V> mArrayMap = new android.util.ArrayMap<>();
    private final int mLimit;

    public CircularQueue(int i) {
        this.mLimit = i;
    }

    @Override // java.util.LinkedList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
    public boolean add(K k) throws java.lang.IllegalArgumentException {
        throw new java.lang.IllegalArgumentException("Call of add(key) prohibited. Please call put(key, value) instead. ");
    }

    @android.annotation.Nullable
    public V put(K k, V v) {
        super.add(k);
        this.mArrayMap.put(k, v);
        V v2 = null;
        while (size() > this.mLimit) {
            v2 = this.mArrayMap.remove(super.remove());
        }
        return v2;
    }

    public V removeElement(K k) {
        super.remove(k);
        return this.mArrayMap.remove(k);
    }

    public V getElement(K k) {
        return this.mArrayMap.get(k);
    }

    public boolean containsKey(K k) {
        return this.mArrayMap.containsKey(k);
    }

    public java.util.Collection<V> values() {
        return this.mArrayMap.values();
    }
}
