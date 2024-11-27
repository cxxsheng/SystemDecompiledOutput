package android.util;

/* loaded from: classes3.dex */
public class SparseSetArray<T> {
    private final android.util.SparseArray<android.util.ArraySet<T>> mData;

    public SparseSetArray() {
        this.mData = new android.util.SparseArray<>();
    }

    public SparseSetArray(android.util.SparseSetArray<T> sparseSetArray) {
        int size = sparseSetArray.size();
        this.mData = new android.util.SparseArray<>(size);
        for (int i = 0; i < size; i++) {
            int keyAt = sparseSetArray.keyAt(i);
            addAll(keyAt, sparseSetArray.get(keyAt));
        }
    }

    public boolean add(int i, T t) {
        android.util.ArraySet<T> arraySet = this.mData.get(i);
        if (arraySet == null) {
            arraySet = new android.util.ArraySet<>();
            this.mData.put(i, arraySet);
        }
        if (arraySet.contains(t)) {
            return false;
        }
        arraySet.add(t);
        return true;
    }

    public void addAll(int i, android.util.ArraySet<T> arraySet) {
        android.util.ArraySet<T> arraySet2 = this.mData.get(i);
        if (arraySet2 == null) {
            this.mData.put(i, new android.util.ArraySet<>((android.util.ArraySet) arraySet));
        } else {
            arraySet2.addAll((android.util.ArraySet<? extends T>) arraySet);
        }
    }

    public void clear() {
        this.mData.clear();
    }

    public boolean contains(int i, T t) {
        android.util.ArraySet<T> arraySet = this.mData.get(i);
        if (arraySet == null) {
            return false;
        }
        return arraySet.contains(t);
    }

    public android.util.ArraySet<T> get(int i) {
        return this.mData.get(i);
    }

    public boolean remove(int i, T t) {
        android.util.ArraySet<T> arraySet = this.mData.get(i);
        if (arraySet == null) {
            return false;
        }
        boolean remove = arraySet.remove(t);
        if (arraySet.size() == 0) {
            this.mData.remove(i);
        }
        return remove;
    }

    public void remove(int i) {
        this.mData.remove(i);
    }

    public int size() {
        return this.mData.size();
    }

    public int keyAt(int i) {
        return this.mData.keyAt(i);
    }

    public int sizeAt(int i) {
        android.util.ArraySet<T> valueAt = this.mData.valueAt(i);
        if (valueAt == null) {
            return 0;
        }
        return valueAt.size();
    }

    public T valueAt(int i, int i2) {
        return this.mData.valueAt(i).valueAt(i2);
    }

    public android.util.ArraySet<T> valuesAt(int i) {
        return this.mData.valueAt(i);
    }
}
