package com.android.internal.widget.remotecompose.core.operations.utilities;

/* loaded from: classes5.dex */
public class IntMap<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int NOT_PRESENT = Integer.MIN_VALUE;
    private int[] mKeys = new int[16];
    int mSize;
    private java.util.ArrayList<T> mValues;

    public IntMap() {
        java.util.Arrays.fill(this.mKeys, Integer.MIN_VALUE);
        this.mValues = new java.util.ArrayList<>(16);
        for (int i = 0; i < 16; i++) {
            this.mValues.add(null);
        }
    }

    public void clear() {
        java.util.Arrays.fill(this.mKeys, Integer.MIN_VALUE);
        this.mValues.clear();
        this.mSize = 0;
    }

    public T put(int i, T t) {
        if (i == Integer.MIN_VALUE) {
            throw new java.lang.IllegalArgumentException("Key cannot be NOT_PRESENT");
        }
        if (this.mSize > this.mKeys.length * 0.75f) {
            resize();
        }
        return insert(i, t);
    }

    public T get(int i) {
        int findKey = findKey(i);
        if (findKey == -1) {
            return null;
        }
        return this.mValues.get(findKey);
    }

    public int size() {
        return this.mSize;
    }

    private T insert(int i, T t) {
        int i2;
        T t2;
        int hash = hash(i);
        int length = this.mKeys.length;
        while (true) {
            i2 = hash % length;
            if (this.mKeys[i2] == Integer.MIN_VALUE || this.mKeys[i2] == i) {
                break;
            }
            hash = i2 + 1;
            length = this.mKeys.length;
        }
        if (this.mKeys[i2] == Integer.MIN_VALUE) {
            this.mSize++;
            t2 = null;
        } else {
            t2 = this.mValues.get(i2);
        }
        this.mKeys[i2] = i;
        this.mValues.set(i2, t);
        return t2;
    }

    private int findKey(int i) {
        int hash = hash(i);
        int length = this.mKeys.length;
        while (true) {
            int i2 = hash % length;
            if (this.mKeys[i2] != Integer.MIN_VALUE) {
                if (this.mKeys[i2] == i) {
                    return i2;
                }
                hash = i2 + 1;
                length = this.mKeys.length;
            } else {
                return -1;
            }
        }
    }

    private int hash(int i) {
        return i;
    }

    private void resize() {
        int[] iArr = this.mKeys;
        java.util.ArrayList<T> arrayList = this.mValues;
        this.mKeys = new int[iArr.length * 2];
        for (int i = 0; i < this.mKeys.length; i++) {
            this.mKeys[i] = Integer.MIN_VALUE;
        }
        this.mValues = new java.util.ArrayList<>(iArr.length * 2);
        for (int i2 = 0; i2 < iArr.length * 2; i2++) {
            this.mValues.add(null);
        }
        this.mSize = 0;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            if (iArr[i3] != Integer.MIN_VALUE) {
                put(iArr[i3], arrayList.get(i3));
            }
        }
    }
}
