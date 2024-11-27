package com.android.internal.app;

/* loaded from: classes4.dex */
public class ProcessMap<E> {
    final android.util.ArrayMap<java.lang.String, android.util.SparseArray<E>> mMap = new android.util.ArrayMap<>();

    public E get(java.lang.String str, int i) {
        android.util.SparseArray<E> sparseArray = this.mMap.get(str);
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(i);
    }

    public E put(java.lang.String str, int i, E e) {
        android.util.SparseArray<E> sparseArray = this.mMap.get(str);
        if (sparseArray == null) {
            sparseArray = new android.util.SparseArray<>(2);
            this.mMap.put(str, sparseArray);
        }
        sparseArray.put(i, e);
        return e;
    }

    public E remove(java.lang.String str, int i) {
        android.util.SparseArray<E> sparseArray = this.mMap.get(str);
        if (sparseArray != null) {
            E removeReturnOld = sparseArray.removeReturnOld(i);
            if (sparseArray.size() == 0) {
                this.mMap.remove(str);
            }
            return removeReturnOld;
        }
        return null;
    }

    public android.util.ArrayMap<java.lang.String, android.util.SparseArray<E>> getMap() {
        return this.mMap;
    }

    public int size() {
        return this.mMap.size();
    }

    public void clear() {
        this.mMap.clear();
    }

    public void putAll(com.android.internal.app.ProcessMap<E> processMap) {
        this.mMap.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends android.util.SparseArray<E>>) processMap.mMap);
    }
}
