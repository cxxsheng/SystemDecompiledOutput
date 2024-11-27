package android.util;

/* loaded from: classes3.dex */
public final class FastImmutableArraySet<T> extends java.util.AbstractSet<T> {
    T[] mContents;
    android.util.FastImmutableArraySet.FastIterator<T> mIterator;

    public FastImmutableArraySet(T[] tArr) {
        this.mContents = tArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public java.util.Iterator<T> iterator() {
        android.util.FastImmutableArraySet.FastIterator<T> fastIterator = this.mIterator;
        if (fastIterator == null) {
            android.util.FastImmutableArraySet.FastIterator<T> fastIterator2 = new android.util.FastImmutableArraySet.FastIterator<>(this.mContents);
            this.mIterator = fastIterator2;
            return fastIterator2;
        }
        fastIterator.mIndex = 0;
        return fastIterator;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.mContents.length;
    }

    private static final class FastIterator<T> implements java.util.Iterator<T> {
        private final T[] mContents;
        int mIndex;

        public FastIterator(T[] tArr) {
            this.mContents = tArr;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.mIndex != this.mContents.length;
        }

        @Override // java.util.Iterator
        public T next() {
            T[] tArr = this.mContents;
            int i = this.mIndex;
            this.mIndex = i + 1;
            return tArr[i];
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
}
