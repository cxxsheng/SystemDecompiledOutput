package android.util;

/* loaded from: classes3.dex */
public final class ArrayMap<K, V> implements java.util.Map<K, V> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean CONCURRENT_MODIFICATION_EXCEPTIONS = true;
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "ArrayMap";
    static java.lang.Object[] mBaseCache;
    static int mBaseCacheSize;
    static java.lang.Object[] mTwiceBaseCache;
    static int mTwiceBaseCacheSize;
    java.lang.Object[] mArray;
    private android.util.MapCollections<K, V> mCollections;
    int[] mHashes;
    private final boolean mIdentityHashCode;
    int mSize;
    static final int[] EMPTY_IMMUTABLE_INTS = new int[0];
    public static final android.util.ArrayMap EMPTY = new android.util.ArrayMap(-1);
    private static final java.lang.Object sBaseCacheLock = new java.lang.Object();
    private static final java.lang.Object sTwiceBaseCacheLock = new java.lang.Object();

    private static int binarySearchHashes(int[] iArr, int i, int i2) {
        try {
            return android.util.ContainerHelpers.binarySearch(iArr, i, i2);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new java.util.ConcurrentModificationException();
        }
    }

    int indexOf(java.lang.Object obj, int i) {
        int i2 = this.mSize;
        if (i2 == 0) {
            return -1;
        }
        int binarySearchHashes = binarySearchHashes(this.mHashes, i2, i);
        if (binarySearchHashes < 0) {
            return binarySearchHashes;
        }
        if (obj.equals(this.mArray[binarySearchHashes << 1])) {
            return binarySearchHashes;
        }
        int i3 = binarySearchHashes + 1;
        while (i3 < i2 && this.mHashes[i3] == i) {
            if (obj.equals(this.mArray[i3 << 1])) {
                return i3;
            }
            i3++;
        }
        for (int i4 = binarySearchHashes - 1; i4 >= 0 && this.mHashes[i4] == i; i4--) {
            if (obj.equals(this.mArray[i4 << 1])) {
                return i4;
            }
        }
        return ~i3;
    }

    int indexOfNull() {
        int i = this.mSize;
        if (i == 0) {
            return -1;
        }
        int binarySearchHashes = binarySearchHashes(this.mHashes, i, 0);
        if (binarySearchHashes < 0) {
            return binarySearchHashes;
        }
        if (this.mArray[binarySearchHashes << 1] == null) {
            return binarySearchHashes;
        }
        int i2 = binarySearchHashes + 1;
        while (i2 < i && this.mHashes[i2] == 0) {
            if (this.mArray[i2 << 1] == null) {
                return i2;
            }
            i2++;
        }
        for (int i3 = binarySearchHashes - 1; i3 >= 0 && this.mHashes[i3] == 0; i3--) {
            if (this.mArray[i3 << 1] == null) {
                return i3;
            }
        }
        return ~i2;
    }

    private void allocArrays(int i) {
        if (this.mHashes == EMPTY_IMMUTABLE_INTS) {
            throw new java.lang.UnsupportedOperationException("ArrayMap is immutable");
        }
        if (i == 8) {
            synchronized (sTwiceBaseCacheLock) {
                if (mTwiceBaseCache != null) {
                    java.lang.Object[] objArr = mTwiceBaseCache;
                    this.mArray = objArr;
                    try {
                        mTwiceBaseCache = (java.lang.Object[]) objArr[0];
                        this.mHashes = (int[]) objArr[1];
                    } catch (java.lang.ClassCastException e) {
                    }
                    if (this.mHashes != null) {
                        objArr[1] = null;
                        objArr[0] = null;
                        mTwiceBaseCacheSize--;
                        return;
                    } else {
                        android.util.Slog.wtf(TAG, "Found corrupt ArrayMap cache: [0]=" + objArr[0] + " [1]=" + objArr[1]);
                        mTwiceBaseCache = null;
                        mTwiceBaseCacheSize = 0;
                    }
                }
            }
        } else if (i == 4) {
            synchronized (sBaseCacheLock) {
                if (mBaseCache != null) {
                    java.lang.Object[] objArr2 = mBaseCache;
                    this.mArray = objArr2;
                    try {
                        mBaseCache = (java.lang.Object[]) objArr2[0];
                        this.mHashes = (int[]) objArr2[1];
                    } catch (java.lang.ClassCastException e2) {
                    }
                    if (this.mHashes != null) {
                        objArr2[1] = null;
                        objArr2[0] = null;
                        mBaseCacheSize--;
                        return;
                    } else {
                        android.util.Slog.wtf(TAG, "Found corrupt ArrayMap cache: [0]=" + objArr2[0] + " [1]=" + objArr2[1]);
                        mBaseCache = null;
                        mBaseCacheSize = 0;
                    }
                }
            }
        }
        this.mHashes = new int[i];
        this.mArray = new java.lang.Object[i << 1];
    }

    private static void freeArrays(int[] iArr, java.lang.Object[] objArr, int i) {
        if (iArr.length == 8) {
            synchronized (sTwiceBaseCacheLock) {
                if (mTwiceBaseCacheSize < 10) {
                    objArr[0] = mTwiceBaseCache;
                    objArr[1] = iArr;
                    for (int i2 = (i << 1) - 1; i2 >= 2; i2--) {
                        objArr[i2] = null;
                    }
                    mTwiceBaseCache = objArr;
                    mTwiceBaseCacheSize++;
                }
            }
            return;
        }
        if (iArr.length == 4) {
            synchronized (sBaseCacheLock) {
                if (mBaseCacheSize < 10) {
                    objArr[0] = mBaseCache;
                    objArr[1] = iArr;
                    for (int i3 = (i << 1) - 1; i3 >= 2; i3--) {
                        objArr[i3] = null;
                    }
                    mBaseCache = objArr;
                    mBaseCacheSize++;
                }
            }
        }
    }

    public ArrayMap() {
        this(0, false);
    }

    public ArrayMap(int i) {
        this(i, false);
    }

    public ArrayMap(int i, boolean z) {
        this.mIdentityHashCode = z;
        if (i < 0) {
            this.mHashes = EMPTY_IMMUTABLE_INTS;
            this.mArray = android.util.EmptyArray.OBJECT;
        } else if (i == 0) {
            this.mHashes = android.util.EmptyArray.INT;
            this.mArray = android.util.EmptyArray.OBJECT;
        } else {
            allocArrays(i);
        }
        this.mSize = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ArrayMap(android.util.ArrayMap<K, V> arrayMap) {
        this();
        if (arrayMap != 0) {
            putAll((android.util.ArrayMap) arrayMap);
        }
    }

    @Override // java.util.Map
    public void clear() {
        if (this.mSize > 0) {
            int[] iArr = this.mHashes;
            java.lang.Object[] objArr = this.mArray;
            int i = this.mSize;
            this.mHashes = android.util.EmptyArray.INT;
            this.mArray = android.util.EmptyArray.OBJECT;
            this.mSize = 0;
            freeArrays(iArr, objArr, i);
        }
        if (this.mSize > 0) {
            throw new java.util.ConcurrentModificationException();
        }
    }

    public void erase() {
        if (this.mSize > 0) {
            int i = this.mSize << 1;
            java.lang.Object[] objArr = this.mArray;
            for (int i2 = 0; i2 < i; i2++) {
                objArr[i2] = null;
            }
            this.mSize = 0;
        }
    }

    public void ensureCapacity(int i) {
        int i2 = this.mSize;
        if (this.mHashes.length < i) {
            int[] iArr = this.mHashes;
            java.lang.Object[] objArr = this.mArray;
            allocArrays(i);
            if (this.mSize > 0) {
                java.lang.System.arraycopy(iArr, 0, this.mHashes, 0, i2);
                java.lang.System.arraycopy(objArr, 0, this.mArray, 0, i2 << 1);
            }
            freeArrays(iArr, objArr, i2);
        }
        if (this.mSize != i2) {
            throw new java.util.ConcurrentModificationException();
        }
    }

    @Override // java.util.Map
    public boolean containsKey(java.lang.Object obj) {
        return indexOfKey(obj) >= 0;
    }

    public int indexOfKey(java.lang.Object obj) {
        if (obj == null) {
            return indexOfNull();
        }
        return indexOf(obj, this.mIdentityHashCode ? java.lang.System.identityHashCode(obj) : obj.hashCode());
    }

    public int indexOfValue(java.lang.Object obj) {
        int i = this.mSize * 2;
        java.lang.Object[] objArr = this.mArray;
        if (obj == null) {
            for (int i2 = 1; i2 < i; i2 += 2) {
                if (objArr[i2] == null) {
                    return i2 >> 1;
                }
            }
            return -1;
        }
        for (int i3 = 1; i3 < i; i3 += 2) {
            if (obj.equals(objArr[i3])) {
                return i3 >> 1;
            }
        }
        return -1;
    }

    @Override // java.util.Map
    public boolean containsValue(java.lang.Object obj) {
        return indexOfValue(obj) >= 0;
    }

    @Override // java.util.Map
    public V get(java.lang.Object obj) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey >= 0) {
            return (V) this.mArray[(indexOfKey << 1) + 1];
        }
        return null;
    }

    public K keyAt(int i) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        return (K) this.mArray[i << 1];
    }

    public V valueAt(int i) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        return (V) this.mArray[(i << 1) + 1];
    }

    public V setValueAt(int i, V v) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        int i2 = (i << 1) + 1;
        V v2 = (V) this.mArray[i2];
        this.mArray[i2] = v;
        return v2;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        int i;
        int indexOf;
        int i2 = this.mSize;
        if (k == null) {
            indexOf = indexOfNull();
            i = 0;
        } else {
            int identityHashCode = this.mIdentityHashCode ? java.lang.System.identityHashCode(k) : k.hashCode();
            i = identityHashCode;
            indexOf = indexOf(k, identityHashCode);
        }
        if (indexOf >= 0) {
            int i3 = (indexOf << 1) + 1;
            V v2 = (V) this.mArray[i3];
            this.mArray[i3] = v;
            return v2;
        }
        int i4 = ~indexOf;
        if (i2 >= this.mHashes.length) {
            int i5 = 8;
            if (i2 >= 8) {
                i5 = (i2 >> 1) + i2;
            } else if (i2 < 4) {
                i5 = 4;
            }
            int[] iArr = this.mHashes;
            java.lang.Object[] objArr = this.mArray;
            allocArrays(i5);
            if (i2 != this.mSize) {
                throw new java.util.ConcurrentModificationException();
            }
            if (this.mHashes.length > 0) {
                java.lang.System.arraycopy(iArr, 0, this.mHashes, 0, iArr.length);
                java.lang.System.arraycopy(objArr, 0, this.mArray, 0, objArr.length);
            }
            freeArrays(iArr, objArr, i2);
        }
        if (i4 < i2) {
            int i6 = i4 + 1;
            java.lang.System.arraycopy(this.mHashes, i4, this.mHashes, i6, i2 - i4);
            java.lang.System.arraycopy(this.mArray, i4 << 1, this.mArray, i6 << 1, (this.mSize - i4) << 1);
        }
        if (i2 != this.mSize || i4 >= this.mHashes.length) {
            throw new java.util.ConcurrentModificationException();
        }
        this.mHashes[i4] = i;
        int i7 = i4 << 1;
        this.mArray[i7] = k;
        this.mArray[i7 + 1] = v;
        this.mSize++;
        return null;
    }

    public void append(K k, V v) {
        int identityHashCode;
        int i = this.mSize;
        if (k == null) {
            identityHashCode = 0;
        } else {
            identityHashCode = this.mIdentityHashCode ? java.lang.System.identityHashCode(k) : k.hashCode();
        }
        if (i >= this.mHashes.length) {
            throw new java.lang.IllegalStateException("Array is full");
        }
        if (i > 0) {
            int i2 = i - 1;
            if (this.mHashes[i2] > identityHashCode) {
                java.lang.RuntimeException runtimeException = new java.lang.RuntimeException("here");
                runtimeException.fillInStackTrace();
                android.util.Log.w(TAG, "New hash " + identityHashCode + " is before end of array hash " + this.mHashes[i2] + " at index " + i + "", runtimeException);
                put(k, v);
                return;
            }
        }
        this.mSize = i + 1;
        this.mHashes[i] = identityHashCode;
        int i3 = i << 1;
        this.mArray[i3] = k;
        this.mArray[i3 + 1] = v;
    }

    public void validate() {
        int i = this.mSize;
        if (i <= 1) {
            return;
        }
        int i2 = 0;
        int i3 = this.mHashes[0];
        for (int i4 = 1; i4 < i; i4++) {
            int i5 = this.mHashes[i4];
            if (i5 != i3) {
                i2 = i4;
                i3 = i5;
            } else {
                java.lang.Object obj = this.mArray[i4 << 1];
                for (int i6 = i4 - 1; i6 >= i2; i6--) {
                    java.lang.Object obj2 = this.mArray[i6 << 1];
                    if (obj == obj2) {
                        throw new java.lang.IllegalArgumentException("Duplicate key in ArrayMap: " + obj);
                    }
                    if (obj != null && obj2 != null && obj.equals(obj2)) {
                        throw new java.lang.IllegalArgumentException("Duplicate key in ArrayMap: " + obj);
                    }
                }
            }
        }
    }

    public void putAll(android.util.ArrayMap<? extends K, ? extends V> arrayMap) {
        int i = arrayMap.mSize;
        ensureCapacity(this.mSize + i);
        if (this.mSize == 0) {
            if (i > 0) {
                java.lang.System.arraycopy(arrayMap.mHashes, 0, this.mHashes, 0, i);
                java.lang.System.arraycopy(arrayMap.mArray, 0, this.mArray, 0, i << 1);
                this.mSize = i;
                return;
            }
            return;
        }
        for (int i2 = 0; i2 < i; i2++) {
            put(arrayMap.keyAt(i2), arrayMap.valueAt(i2));
        }
    }

    @Override // java.util.Map
    public V remove(java.lang.Object obj) {
        int indexOfKey = indexOfKey(obj);
        if (indexOfKey >= 0) {
            return removeAt(indexOfKey);
        }
        return null;
    }

    public V removeAt(int i) {
        if (i >= this.mSize && android.util.UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new java.lang.ArrayIndexOutOfBoundsException(i);
        }
        int i2 = i << 1;
        V v = (V) this.mArray[i2 + 1];
        int i3 = this.mSize;
        int i4 = 0;
        if (i3 <= 1) {
            int[] iArr = this.mHashes;
            java.lang.Object[] objArr = this.mArray;
            this.mHashes = android.util.EmptyArray.INT;
            this.mArray = android.util.EmptyArray.OBJECT;
            freeArrays(iArr, objArr, i3);
        } else {
            int i5 = i3 - 1;
            if (this.mHashes.length > 8 && this.mSize < this.mHashes.length / 3) {
                int i6 = i3 > 8 ? i3 + (i3 >> 1) : 8;
                int[] iArr2 = this.mHashes;
                java.lang.Object[] objArr2 = this.mArray;
                allocArrays(i6);
                if (i3 != this.mSize) {
                    throw new java.util.ConcurrentModificationException();
                }
                if (i > 0) {
                    java.lang.System.arraycopy(iArr2, 0, this.mHashes, 0, i);
                    java.lang.System.arraycopy(objArr2, 0, this.mArray, 0, i2);
                }
                if (i < i5) {
                    int i7 = i + 1;
                    int i8 = i5 - i;
                    java.lang.System.arraycopy(iArr2, i7, this.mHashes, i, i8);
                    java.lang.System.arraycopy(objArr2, i7 << 1, this.mArray, i2, i8 << 1);
                }
            } else {
                if (i < i5) {
                    int i9 = i + 1;
                    int i10 = i5 - i;
                    java.lang.System.arraycopy(this.mHashes, i9, this.mHashes, i, i10);
                    java.lang.System.arraycopy(this.mArray, i9 << 1, this.mArray, i2, i10 << 1);
                }
                int i11 = i5 << 1;
                this.mArray[i11] = null;
                this.mArray[i11 + 1] = null;
            }
            i4 = i5;
        }
        if (i3 != this.mSize) {
            throw new java.util.ConcurrentModificationException();
        }
        this.mSize = i4;
        return v;
    }

    @Override // java.util.Map
    public int size() {
        return this.mSize;
    }

    @Override // java.util.Map
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof java.util.Map)) {
            return false;
        }
        java.util.Map map = (java.util.Map) obj;
        if (size() != map.size()) {
            return false;
        }
        for (int i = 0; i < this.mSize; i++) {
            try {
                K keyAt = keyAt(i);
                V valueAt = valueAt(i);
                java.lang.Object obj2 = map.get(keyAt);
                if (valueAt == null) {
                    if (obj2 != null || !map.containsKey(keyAt)) {
                        return false;
                    }
                } else if (!valueAt.equals(obj2)) {
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

    @Override // java.util.Map
    public int hashCode() {
        int[] iArr = this.mHashes;
        java.lang.Object[] objArr = this.mArray;
        int i = this.mSize;
        int i2 = 1;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            java.lang.Object obj = objArr[i2];
            i4 += (obj == null ? 0 : obj.hashCode()) ^ iArr[i3];
            i3++;
            i2 += 2;
        }
        return i4;
    }

    public java.lang.String toString() {
        if (isEmpty()) {
            return "{}";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            K keyAt = keyAt(i);
            if (keyAt != this) {
                sb.append(keyAt);
            } else {
                sb.append("(this Map)");
            }
            sb.append('=');
            V valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(com.android.internal.util.ArrayUtils.deepToString(valueAt));
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    private android.util.MapCollections<K, V> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new android.util.MapCollections<K, V>() { // from class: android.util.ArrayMap.1
                @Override // android.util.MapCollections
                protected int colGetSize() {
                    return android.util.ArrayMap.this.mSize;
                }

                @Override // android.util.MapCollections
                protected java.lang.Object colGetEntry(int i, int i2) {
                    return android.util.ArrayMap.this.mArray[(i << 1) + i2];
                }

                @Override // android.util.MapCollections
                protected int colIndexOfKey(java.lang.Object obj) {
                    return android.util.ArrayMap.this.indexOfKey(obj);
                }

                @Override // android.util.MapCollections
                protected int colIndexOfValue(java.lang.Object obj) {
                    return android.util.ArrayMap.this.indexOfValue(obj);
                }

                @Override // android.util.MapCollections
                protected java.util.Map<K, V> colGetMap() {
                    return android.util.ArrayMap.this;
                }

                @Override // android.util.MapCollections
                protected void colPut(K k, V v) {
                    android.util.ArrayMap.this.put(k, v);
                }

                @Override // android.util.MapCollections
                protected V colSetValue(int i, V v) {
                    return (V) android.util.ArrayMap.this.setValueAt(i, v);
                }

                @Override // android.util.MapCollections
                protected void colRemoveAt(int i) {
                    android.util.ArrayMap.this.removeAt(i);
                }

                @Override // android.util.MapCollections
                protected void colClear() {
                    android.util.ArrayMap.this.clear();
                }
            };
        }
        return this.mCollections;
    }

    public boolean containsAll(java.util.Collection<?> collection) {
        return android.util.MapCollections.containsAllHelper(this, collection);
    }

    @Override // java.util.Map
    public void forEach(java.util.function.BiConsumer<? super K, ? super V> biConsumer) {
        if (biConsumer == null) {
            throw new java.lang.NullPointerException("action must not be null");
        }
        int i = this.mSize;
        for (int i2 = 0; i2 < i; i2++) {
            if (i != this.mSize) {
                throw new java.util.ConcurrentModificationException();
            }
            biConsumer.accept(keyAt(i2), valueAt(i2));
        }
    }

    @Override // java.util.Map
    public void putAll(java.util.Map<? extends K, ? extends V> map) {
        ensureCapacity(this.mSize + map.size());
        for (java.util.Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public boolean removeAll(java.util.Collection<?> collection) {
        return android.util.MapCollections.removeAllHelper(this, collection);
    }

    @Override // java.util.Map
    public void replaceAll(java.util.function.BiFunction<? super K, ? super V, ? extends V> biFunction) {
        if (biFunction == null) {
            throw new java.lang.NullPointerException("function must not be null");
        }
        int i = this.mSize;
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 << 1;
            int i4 = i3 + 1;
            try {
                this.mArray[i4] = biFunction.apply(this.mArray[i3], this.mArray[i4]);
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                throw new java.util.ConcurrentModificationException();
            }
        }
        if (i != this.mSize) {
            throw new java.util.ConcurrentModificationException();
        }
    }

    public boolean retainAll(java.util.Collection<?> collection) {
        return android.util.MapCollections.retainAllHelper(this, collection);
    }

    @Override // java.util.Map
    public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
        return getCollection().getEntrySet();
    }

    @Override // java.util.Map
    public java.util.Set<K> keySet() {
        return getCollection().getKeySet();
    }

    @Override // java.util.Map
    public java.util.Collection<V> values() {
        return getCollection().getValues();
    }
}
