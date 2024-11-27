package com.android.server.utils;

/* loaded from: classes2.dex */
public class WatchedSparseIntArray extends com.android.server.utils.WatchableImpl implements com.android.server.utils.Snappable {
    private final android.util.SparseIntArray mStorage;

    private void onChanged() {
        dispatchChange(this);
    }

    public WatchedSparseIntArray() {
        this.mStorage = new android.util.SparseIntArray();
    }

    public WatchedSparseIntArray(int i) {
        this.mStorage = new android.util.SparseIntArray(i);
    }

    public WatchedSparseIntArray(@android.annotation.NonNull android.util.SparseIntArray sparseIntArray) {
        this.mStorage = sparseIntArray.clone();
    }

    public WatchedSparseIntArray(@android.annotation.NonNull com.android.server.utils.WatchedSparseIntArray watchedSparseIntArray) {
        this.mStorage = watchedSparseIntArray.mStorage.clone();
    }

    public void copyFrom(@android.annotation.NonNull android.util.SparseIntArray sparseIntArray) {
        clear();
        int size = sparseIntArray.size();
        for (int i = 0; i < size; i++) {
            put(sparseIntArray.keyAt(i), sparseIntArray.valueAt(i));
        }
    }

    public void copyTo(@android.annotation.NonNull android.util.SparseIntArray sparseIntArray) {
        sparseIntArray.clear();
        int size = size();
        for (int i = 0; i < size; i++) {
            sparseIntArray.put(keyAt(i), valueAt(i));
        }
    }

    public android.util.SparseIntArray untrackedStorage() {
        return this.mStorage;
    }

    public int get(int i) {
        return this.mStorage.get(i);
    }

    public int get(int i, int i2) {
        return this.mStorage.get(i, i2);
    }

    public void delete(int i) {
        int indexOfKey = this.mStorage.indexOfKey(i);
        if (indexOfKey >= 0) {
            this.mStorage.removeAt(indexOfKey);
            onChanged();
        }
    }

    public void removeAt(int i) {
        this.mStorage.removeAt(i);
        onChanged();
    }

    public void put(int i, int i2) {
        this.mStorage.put(i, i2);
        onChanged();
    }

    public int size() {
        return this.mStorage.size();
    }

    public int keyAt(int i) {
        return this.mStorage.keyAt(i);
    }

    public int valueAt(int i) {
        return this.mStorage.valueAt(i);
    }

    public void setValueAt(int i, int i2) {
        if (this.mStorage.valueAt(i) != i2) {
            this.mStorage.setValueAt(i, i2);
            onChanged();
        }
    }

    public int indexOfKey(int i) {
        return this.mStorage.indexOfKey(i);
    }

    public int indexOfValue(int i) {
        return this.mStorage.indexOfValue(i);
    }

    public void clear() {
        int size = size();
        this.mStorage.clear();
        if (size > 0) {
            onChanged();
        }
    }

    public void append(int i, int i2) {
        this.mStorage.append(i, i2);
        onChanged();
    }

    public int[] copyKeys() {
        return this.mStorage.copyKeys();
    }

    public int hashCode() {
        return this.mStorage.hashCode();
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (obj instanceof com.android.server.utils.WatchedSparseIntArray) {
            return this.mStorage.equals(((com.android.server.utils.WatchedSparseIntArray) obj).mStorage);
        }
        return false;
    }

    public java.lang.String toString() {
        return this.mStorage.toString();
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.utils.WatchedSparseIntArray snapshot() {
        com.android.server.utils.WatchedSparseIntArray watchedSparseIntArray = new com.android.server.utils.WatchedSparseIntArray(this);
        watchedSparseIntArray.seal();
        return watchedSparseIntArray;
    }

    public void snapshot(@android.annotation.NonNull com.android.server.utils.WatchedSparseIntArray watchedSparseIntArray) {
        snapshot(this, watchedSparseIntArray);
    }

    public static void snapshot(@android.annotation.NonNull com.android.server.utils.WatchedSparseIntArray watchedSparseIntArray, @android.annotation.NonNull com.android.server.utils.WatchedSparseIntArray watchedSparseIntArray2) {
        if (watchedSparseIntArray.size() != 0) {
            throw new java.lang.IllegalArgumentException("snapshot destination is not empty");
        }
        int size = watchedSparseIntArray2.size();
        for (int i = 0; i < size; i++) {
            watchedSparseIntArray.mStorage.put(watchedSparseIntArray2.keyAt(i), watchedSparseIntArray2.valueAt(i));
        }
        watchedSparseIntArray.seal();
    }
}
