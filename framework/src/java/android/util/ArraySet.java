package android.util;

/* loaded from: classes3.dex */
public final class ArraySet<E> implements java.util.Collection<E>, java.util.Set<E> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "ArraySet";
    static java.lang.Object[] sBaseCache;
    static int sBaseCacheSize;
    static java.lang.Object[] sTwiceBaseCache;
    static int sTwiceBaseCacheSize;
    java.lang.Object[] mArray;
    private android.util.MapCollections<E, E> mCollections;
    int[] mHashes;
    private final boolean mIdentityHashCode;
    int mSize;
    private static final java.lang.Object sBaseCacheLock = new java.lang.Object();
    private static final java.lang.Object sTwiceBaseCacheLock = new java.lang.Object();

    private int binarySearch(int[] iArr, int i) {
        try {
            return android.util.ContainerHelpers.binarySearch(iArr, this.mSize, i);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new java.util.ConcurrentModificationException();
        }
    }

    private int indexOf(java.lang.Object obj, int i) {
        int i2 = this.mSize;
        if (i2 == 0) {
            return -1;
        }
        int binarySearch = binarySearch(this.mHashes, i);
        if (binarySearch < 0) {
            return binarySearch;
        }
        if (obj.equals(this.mArray[binarySearch])) {
            return binarySearch;
        }
        int i3 = binarySearch + 1;
        while (i3 < i2 && this.mHashes[i3] == i) {
            if (obj.equals(this.mArray[i3])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = binarySearch - 1; i4 >= 0 && this.mHashes[i4] == i; i4--) {
            if (obj.equals(this.mArray[i4])) {
                return i4;
            }
        }
        return ~i3;
    }

    private int indexOfNull() {
        int i = this.mSize;
        if (i == 0) {
            return -1;
        }
        int binarySearch = binarySearch(this.mHashes, 0);
        if (binarySearch < 0) {
            return binarySearch;
        }
        if (this.mArray[binarySearch] == null) {
            return binarySearch;
        }
        int i2 = binarySearch + 1;
        while (i2 < i && this.mHashes[i2] == 0) {
            if (this.mArray[i2] == null) {
                return i2;
            }
            i2++;
        }
        for (int i3 = binarySearch - 1; i3 >= 0 && this.mHashes[i3] == 0; i3--) {
            if (this.mArray[i3] == null) {
                return i3;
            }
        }
        return ~i2;
    }

    private void allocArrays(int i) {
        if (i == 8) {
            synchronized (sTwiceBaseCacheLock) {
                if (sTwiceBaseCache != null) {
                    java.lang.Object[] objArr = sTwiceBaseCache;
                    try {
                        this.mArray = objArr;
                        sTwiceBaseCache = (java.lang.Object[]) objArr[0];
                        this.mHashes = (int[]) objArr[1];
                    } catch (java.lang.ClassCastException e) {
                    }
                    if (this.mHashes != null) {
                        objArr[1] = null;
                        objArr[0] = null;
                        sTwiceBaseCacheSize--;
                        return;
                    } else {
                        android.util.Slog.wtf(TAG, "Found corrupt ArraySet cache: [0]=" + objArr[0] + " [1]=" + objArr[1]);
                        sTwiceBaseCache = null;
                        sTwiceBaseCacheSize = 0;
                    }
                }
            }
        } else if (i == 4) {
            synchronized (sBaseCacheLock) {
                if (sBaseCache != null) {
                    java.lang.Object[] objArr2 = sBaseCache;
                    try {
                        this.mArray = objArr2;
                        sBaseCache = (java.lang.Object[]) objArr2[0];
                        this.mHashes = (int[]) objArr2[1];
                    } catch (java.lang.ClassCastException e2) {
                    }
                    if (this.mHashes != null) {
                        objArr2[1] = null;
                        objArr2[0] = null;
                        sBaseCacheSize--;
                        return;
                    } else {
                        android.util.Slog.wtf(TAG, "Found corrupt ArraySet cache: [0]=" + objArr2[0] + " [1]=" + objArr2[1]);
                        sBaseCache = null;
                        sBaseCacheSize = 0;
                    }
                }
            }
        }
        this.mHashes = new int[i];
        this.mArray = new java.lang.Object[i];
    }

    private static void freeArrays(int[] iArr, java.lang.Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (sTwiceBaseCacheLock) {
                if (sTwiceBaseCacheSize < 10) {
                    objArr[0] = sTwiceBaseCache;
                    objArr[1] = iArr;
                    for (int i2 = i - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    sTwiceBaseCache = objArr;
                    sTwiceBaseCacheSize++;
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (sBaseCacheLock) {
                if (sBaseCacheSize < 10) {
                    objArr[0] = sBaseCache;
                    objArr[1] = iArr;
                    for (int i3 = i - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    sBaseCache = objArr;
                    sBaseCacheSize++;
                }
            }
        }
    }

    public ArraySet() {
        this(0, false);
    }

    public ArraySet(int i) {
        this(i, false);
    }

    public ArraySet(int i, boolean z) {
        this.mIdentityHashCode = z;
        if (i == 0) {
            this.mHashes = android.util.EmptyArray.INT;
            this.mArray = android.util.EmptyArray.OBJECT;
        } else {
            allocArrays(i);
        }
        this.mSize = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArraySet(android.util.ArraySet<E> arraySet) {
        this();
        if (arraySet != 0) {
            addAll((android.util.ArraySet) arraySet);
        }
    }

    public ArraySet(java.util.Collection<? extends E> collection) {
        this();
        if (collection != null) {
            addAll(collection);
        }
    }

    public ArraySet(E[] eArr) {
        this();
        if (eArr != null) {
            for (E e : eArr) {
                add(e);
            }
        }
    }

    @Override // java.util.Collection, java.util.Set
    public void clear() {
        if (this.mSize != 0) {
            int[] iArr = this.mHashes;
            java.lang.Object[] objArr = this.mArray;
            int i = this.mSize;
            this.mHashes = android.util.EmptyArray.INT;
            this.mArray = android.util.EmptyArray.OBJECT;
            this.mSize = 0;
            freeArrays(iArr, objArr, i);
        }
        if (this.mSize != 0) {
            throw new java.util.ConcurrentModificationException();
        }
    }

    public void ensureCapacity(int i) {
        int i2 = this.mSize;
        if (this.mHashes.length < i) {
            int[] iArr = this.mHashes;
            java.lang.Object[] objArr = this.mArray;
            allocArrays(i);
            if (this.mSize > 0) {
                java.lang.System.arraycopy(iArr, 0, this.mHashes, 0, this.mSize);
                java.lang.System.arraycopy(objArr, 0, this.mArray, 0, this.mSize);
            }
            freeArrays(iArr, objArr, this.mSize);
        }
        if (this.mSize != i2) {
            throw new java.util.ConcurrentModificationException();
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean contains(java.lang.Object obj) {
        return indexOf(obj) >= 0;
    }

    public int indexOf(java.lang.Object obj) {
        if (obj == null) {
            return indexOfNull();
        }
        return indexOf(obj, this.mIdentityHashCode ? java.lang.System.identityHashCode(obj) : obj.hashCode());
    }

    public E valueAt(int i) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        return valueAtUnchecked(i);
    }

    public E valueAtUnchecked(int i) {
        return (E) this.mArray[i];
    }

    @Override // java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean add(E e) {
        int i;
        int indexOf;
        int i2 = this.mSize;
        if (e == null) {
            indexOf = indexOfNull();
            i = 0;
        } else {
            int identityHashCode = this.mIdentityHashCode ? java.lang.System.identityHashCode(e) : e.hashCode();
            i = identityHashCode;
            indexOf = indexOf(e, identityHashCode);
        }
        if (indexOf >= 0) {
            return false;
        }
        int i3 = ~indexOf;
        if (i2 >= this.mHashes.length) {
            int i4 = 8;
            if (i2 >= 8) {
                i4 = (i2 >> 1) + i2;
            } else if (i2 < 4) {
                i4 = 4;
            }
            int[] iArr = this.mHashes;
            java.lang.Object[] objArr = this.mArray;
            allocArrays(i4);
            if (i2 != this.mSize) {
                throw new java.util.ConcurrentModificationException();
            }
            if (this.mHashes.length > 0) {
                java.lang.System.arraycopy(iArr, 0, this.mHashes, 0, iArr.length);
                java.lang.System.arraycopy(objArr, 0, this.mArray, 0, objArr.length);
            }
            freeArrays(iArr, objArr, i2);
        }
        if (i3 < i2) {
            int i5 = i3 + 1;
            int i6 = i2 - i3;
            java.lang.System.arraycopy(this.mHashes, i3, this.mHashes, i5, i6);
            java.lang.System.arraycopy(this.mArray, i3, this.mArray, i5, i6);
        }
        if (i2 != this.mSize || i3 >= this.mHashes.length) {
            throw new java.util.ConcurrentModificationException();
        }
        this.mHashes[i3] = i;
        this.mArray[i3] = e;
        this.mSize++;
        return true;
    }

    public void append(E e) {
        int identityHashCode;
        int i = this.mSize;
        int i2 = this.mSize;
        if (e == null) {
            identityHashCode = 0;
        } else {
            identityHashCode = this.mIdentityHashCode ? java.lang.System.identityHashCode(e) : e.hashCode();
        }
        if (i2 >= this.mHashes.length) {
            throw new java.lang.IllegalStateException("Array is full");
        }
        if (i2 > 0 && this.mHashes[i2 - 1] > identityHashCode) {
            add(e);
        } else {
            if (i != this.mSize) {
                throw new java.util.ConcurrentModificationException();
            }
            this.mSize = i2 + 1;
            this.mHashes[i2] = identityHashCode;
            this.mArray[i2] = e;
        }
    }

    public void addAll(android.util.ArraySet<? extends E> arraySet) {
        int i = arraySet.mSize;
        ensureCapacity(this.mSize + i);
        if (this.mSize == 0) {
            if (i > 0) {
                java.lang.System.arraycopy(arraySet.mHashes, 0, this.mHashes, 0, i);
                java.lang.System.arraycopy(arraySet.mArray, 0, this.mArray, 0, i);
                if (this.mSize != 0) {
                    throw new java.util.ConcurrentModificationException();
                }
                this.mSize = i;
                return;
            }
            return;
        }
        for (int i2 = 0; i2 < i; i2++) {
            add(arraySet.valueAt(i2));
        }
    }

    @Override // java.util.Collection, java.util.Set
    public boolean remove(java.lang.Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf >= 0) {
            removeAt(indexOf);
            return true;
        }
        return false;
    }

    private boolean shouldShrink() {
        return this.mHashes.length > 8 && this.mSize < this.mHashes.length / 3;
    }

    private int getNewShrunkenSize() {
        if (this.mSize <= 8) {
            return 8;
        }
        return (this.mSize >> 1) + this.mSize;
    }

    public E removeAt(int i) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        int i2 = this.mSize;
        E e = (E) this.mArray[i];
        if (i2 <= 1) {
            clear();
        } else {
            int i3 = i2 - 1;
            if (shouldShrink()) {
                int newShrunkenSize = getNewShrunkenSize();
                int[] iArr = this.mHashes;
                java.lang.Object[] objArr = this.mArray;
                allocArrays(newShrunkenSize);
                if (i > 0) {
                    java.lang.System.arraycopy(iArr, 0, this.mHashes, 0, i);
                    java.lang.System.arraycopy(objArr, 0, this.mArray, 0, i);
                }
                if (i < i3) {
                    int i4 = i + 1;
                    int i5 = i3 - i;
                    java.lang.System.arraycopy(iArr, i4, this.mHashes, i, i5);
                    java.lang.System.arraycopy(objArr, i4, this.mArray, i, i5);
                }
            } else {
                if (i < i3) {
                    int i6 = i + 1;
                    int i7 = i3 - i;
                    java.lang.System.arraycopy(this.mHashes, i6, this.mHashes, i, i7);
                    java.lang.System.arraycopy(this.mArray, i6, this.mArray, i, i7);
                }
                this.mArray[i3] = null;
            }
            if (i2 != this.mSize) {
                throw new java.util.ConcurrentModificationException();
            }
            this.mSize = i3;
        }
        return e;
    }

    public boolean removeAll(android.util.ArraySet<? extends E> arraySet) {
        int i = arraySet.mSize;
        int i2 = this.mSize;
        for (int i3 = 0; i3 < i; i3++) {
            remove(arraySet.valueAt(i3));
        }
        return i2 != this.mSize;
    }

    @Override // java.util.Collection
    public boolean removeIf(java.util.function.Predicate<? super E> predicate) {
        if (this.mSize == 0) {
            return false;
        }
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < this.mSize; i3++) {
            if (predicate.test(this.mArray[i3])) {
                i++;
            } else {
                if (i2 != i3) {
                    this.mArray[i2] = this.mArray[i3];
                    this.mHashes[i2] = this.mHashes[i3];
                }
                i2++;
            }
        }
        if (i == 0) {
            return false;
        }
        if (i == this.mSize) {
            clear();
            return true;
        }
        this.mSize -= i;
        if (shouldShrink()) {
            int newShrunkenSize = getNewShrunkenSize();
            int[] iArr = this.mHashes;
            java.lang.Object[] objArr = this.mArray;
            allocArrays(newShrunkenSize);
            java.lang.System.arraycopy(iArr, 0, this.mHashes, 0, this.mSize);
            java.lang.System.arraycopy(objArr, 0, this.mArray, 0, this.mSize);
        } else {
            for (int i4 = this.mSize; i4 < this.mArray.length; i4++) {
                this.mArray[i4] = null;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public int size() {
        return this.mSize;
    }

    @Override // java.lang.Iterable
    public void forEach(java.util.function.Consumer<? super E> consumer) {
        if (consumer == null) {
            throw new java.lang.NullPointerException("action must not be null");
        }
        for (int i = 0; i < this.mSize; i++) {
            consumer.accept(valueAt(i));
        }
    }

    @Override // java.util.Collection, java.util.Set
    public java.lang.Object[] toArray() {
        java.lang.Object[] objArr = new java.lang.Object[this.mSize];
        java.lang.System.arraycopy(this.mArray, 0, objArr, 0, this.mSize);
        return objArr;
    }

    @Override // java.util.Collection, java.util.Set
    public <T> T[] toArray(T[] tArr) {
        if (tArr.length < this.mSize) {
            tArr = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance(tArr.getClass().getComponentType(), this.mSize));
        }
        java.lang.System.arraycopy(this.mArray, 0, tArr, 0, this.mSize);
        if (tArr.length > this.mSize) {
            tArr[this.mSize] = null;
        }
        return tArr;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof java.util.Set)) {
            return false;
        }
        java.util.Set set = (java.util.Set) obj;
        if (size() != set.size()) {
            return false;
        }
        for (int i = 0; i < this.mSize; i++) {
            try {
                if (!set.contains(valueAt(i))) {
                    return false;
                }
            } catch (java.lang.ClassCastException e) {
                return false;
            } catch (java.lang.NullPointerException e2) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        int[] iArr = this.mHashes;
        int i = this.mSize;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += iArr[i3];
        }
        return i2;
    }

    public java.lang.String toString() {
        if (isEmpty()) {
            return "{}";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(this.mSize * 14);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            E valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Set)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    private android.util.MapCollections<E, E> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new android.util.MapCollections<E, E>() { // from class: android.util.ArraySet.1
                @Override // android.util.MapCollections
                protected int colGetSize() {
                    return android.util.ArraySet.this.mSize;
                }

                @Override // android.util.MapCollections
                protected java.lang.Object colGetEntry(int i, int i2) {
                    return android.util.ArraySet.this.mArray[i];
                }

                @Override // android.util.MapCollections
                protected int colIndexOfKey(java.lang.Object obj) {
                    return android.util.ArraySet.this.indexOf(obj);
                }

                @Override // android.util.MapCollections
                protected int colIndexOfValue(java.lang.Object obj) {
                    return android.util.ArraySet.this.indexOf(obj);
                }

                @Override // android.util.MapCollections
                protected java.util.Map<E, E> colGetMap() {
                    throw new java.lang.UnsupportedOperationException("not a map");
                }

                @Override // android.util.MapCollections
                protected void colPut(E e, E e2) {
                    android.util.ArraySet.this.add(e);
                }

                @Override // android.util.MapCollections
                protected E colSetValue(int i, E e) {
                    throw new java.lang.UnsupportedOperationException("not a map");
                }

                @Override // android.util.MapCollections
                protected void colRemoveAt(int i) {
                    android.util.ArraySet.this.removeAt(i);
                }

                @Override // android.util.MapCollections
                protected void colClear() {
                    android.util.ArraySet.this.clear();
                }
            };
        }
        return this.mCollections;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public java.util.Iterator<E> iterator() {
        return getCollection().getKeySet().iterator();
    }

    @Override // java.util.Collection, java.util.Set
    public boolean containsAll(java.util.Collection<?> collection) {
        java.util.Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean addAll(java.util.Collection<? extends E> collection) {
        ensureCapacity(this.mSize + collection.size());
        java.util.Iterator<? extends E> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= add(it.next());
        }
        return z;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean removeAll(java.util.Collection<?> collection) {
        java.util.Iterator<?> it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= remove(it.next());
        }
        return z;
    }

    @Override // java.util.Collection, java.util.Set
    public boolean retainAll(java.util.Collection<?> collection) {
        boolean z = false;
        for (int i = this.mSize - 1; i >= 0; i--) {
            if (!collection.contains(this.mArray[i])) {
                removeAt(i);
                z = true;
            }
        }
        return z;
    }
}
