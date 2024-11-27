package com.android.server.am;

/* loaded from: classes.dex */
public class UidProcessMap<E> {
    final android.util.SparseArray<android.util.ArrayMap<java.lang.String, E>> mMap = new android.util.SparseArray<>();

    public E get(int i, java.lang.String str) {
        android.util.ArrayMap<java.lang.String, E> arrayMap = this.mMap.get(i);
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.get(str);
    }

    public E put(int i, java.lang.String str, E e) {
        android.util.ArrayMap<java.lang.String, E> arrayMap = this.mMap.get(i);
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap<>(2);
            this.mMap.put(i, arrayMap);
        }
        arrayMap.put(str, e);
        return e;
    }

    public E remove(int i, java.lang.String str) {
        android.util.ArrayMap<java.lang.String, E> valueAt;
        int indexOfKey = this.mMap.indexOfKey(i);
        if (indexOfKey < 0 || (valueAt = this.mMap.valueAt(indexOfKey)) == null) {
            return null;
        }
        E remove = valueAt.remove(str);
        if (valueAt.isEmpty()) {
            this.mMap.removeAt(indexOfKey);
        }
        return remove;
    }

    public android.util.SparseArray<android.util.ArrayMap<java.lang.String, E>> getMap() {
        return this.mMap;
    }

    public int size() {
        return this.mMap.size();
    }

    public void clear() {
        this.mMap.clear();
    }

    public void putAll(com.android.server.am.UidProcessMap<E> uidProcessMap) {
        for (int size = uidProcessMap.mMap.size() - 1; size >= 0; size--) {
            int keyAt = uidProcessMap.mMap.keyAt(size);
            android.util.ArrayMap<java.lang.String, E> arrayMap = this.mMap.get(keyAt);
            if (arrayMap != null) {
                arrayMap.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends E>) uidProcessMap.mMap.valueAt(size));
            } else {
                this.mMap.put(keyAt, new android.util.ArrayMap<>(uidProcessMap.mMap.valueAt(size)));
            }
        }
    }
}
