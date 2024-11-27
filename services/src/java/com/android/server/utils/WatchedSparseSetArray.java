package com.android.server.utils;

/* loaded from: classes2.dex */
public class WatchedSparseSetArray<T> extends com.android.server.utils.WatchableImpl implements com.android.server.utils.Snappable {
    private final android.util.SparseSetArray mStorage;

    private void onChanged() {
        dispatchChange(this);
    }

    public WatchedSparseSetArray() {
        this.mStorage = new android.util.SparseSetArray();
    }

    public WatchedSparseSetArray(@android.annotation.NonNull com.android.server.utils.WatchedSparseSetArray<T> watchedSparseSetArray) {
        this.mStorage = new android.util.SparseSetArray(watchedSparseSetArray.untrackedStorage());
    }

    public WatchedSparseSetArray(@android.annotation.NonNull android.util.SparseSetArray<T> sparseSetArray) {
        this.mStorage = sparseSetArray;
    }

    public android.util.SparseSetArray<T> untrackedStorage() {
        return this.mStorage;
    }

    public boolean add(int i, T t) {
        boolean add = this.mStorage.add(i, t);
        onChanged();
        return add;
    }

    public void addAll(int i, android.util.ArraySet<T> arraySet) {
        this.mStorage.addAll(i, arraySet);
        onChanged();
    }

    public void clear() {
        this.mStorage.clear();
        onChanged();
    }

    public boolean contains(int i, T t) {
        return this.mStorage.contains(i, t);
    }

    public android.util.ArraySet<T> get(int i) {
        return this.mStorage.get(i);
    }

    public boolean remove(int i, T t) {
        if (this.mStorage.remove(i, t)) {
            onChanged();
            return true;
        }
        return false;
    }

    public void remove(int i) {
        this.mStorage.remove(i);
        onChanged();
    }

    public int size() {
        return this.mStorage.size();
    }

    public int keyAt(int i) {
        return this.mStorage.keyAt(i);
    }

    public int sizeAt(int i) {
        return this.mStorage.sizeAt(i);
    }

    public T valueAt(int i, int i2) {
        return (T) this.mStorage.valueAt(i, i2);
    }

    public void copyFrom(@android.annotation.NonNull android.util.SparseSetArray<T> sparseSetArray) {
        clear();
        int size = sparseSetArray.size();
        for (int i = 0; i < size; i++) {
            int keyAt = sparseSetArray.keyAt(i);
            this.mStorage.addAll(keyAt, sparseSetArray.get(keyAt));
        }
        onChanged();
    }

    @Override // com.android.server.utils.Snappable
    @android.annotation.NonNull
    public java.lang.Object snapshot() {
        com.android.server.utils.WatchedSparseSetArray watchedSparseSetArray = new com.android.server.utils.WatchedSparseSetArray(this);
        watchedSparseSetArray.seal();
        return watchedSparseSetArray;
    }

    public void snapshot(@android.annotation.NonNull com.android.server.utils.WatchedSparseSetArray<T> watchedSparseSetArray) {
        snapshot(this, watchedSparseSetArray);
    }

    public static void snapshot(@android.annotation.NonNull com.android.server.utils.WatchedSparseSetArray watchedSparseSetArray, @android.annotation.NonNull com.android.server.utils.WatchedSparseSetArray watchedSparseSetArray2) {
        if (watchedSparseSetArray.size() != 0) {
            throw new java.lang.IllegalArgumentException("snapshot destination is not empty");
        }
        int size = watchedSparseSetArray2.size();
        for (int i = 0; i < size; i++) {
            android.util.ArraySet<T> arraySet = watchedSparseSetArray2.get(i);
            int size2 = arraySet.size();
            for (int i2 = 0; i2 < size2; i2++) {
                watchedSparseSetArray.mStorage.add(watchedSparseSetArray2.keyAt(i), arraySet.valueAt(i2));
            }
        }
        watchedSparseSetArray.seal();
    }
}
