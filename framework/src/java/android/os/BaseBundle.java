package android.os;

/* loaded from: classes3.dex */
public class BaseBundle {
    static final int BUNDLE_MAGIC = 1279544898;
    private static final int BUNDLE_MAGIC_NATIVE = 1279544900;
    static final boolean DEBUG = false;
    static final int FLAG_DEFUSABLE = 1;
    private static final boolean LOG_DEFUSABLE = false;
    protected static final java.lang.String TAG = "Bundle";
    private static volatile boolean sShouldDefuse = false;
    private java.lang.ClassLoader mClassLoader;
    public int mFlags;
    private int mLazyValues;
    android.util.ArrayMap<java.lang.String, java.lang.Object> mMap;
    boolean mOwnsLazyValues;
    private boolean mParcelledByNative;
    volatile android.os.Parcel mParcelledData;
    private java.lang.ref.WeakReference<android.os.Parcel> mWeakParcelledData;

    public static void setShouldDefuse(boolean z) {
        sShouldDefuse = z;
    }

    static final class NoImagePreloadHolder {
        public static final android.os.Parcel EMPTY_PARCEL = android.os.Parcel.obtain();

        NoImagePreloadHolder() {
        }
    }

    BaseBundle(java.lang.ClassLoader classLoader, int i) {
        this.mMap = null;
        this.mParcelledData = null;
        this.mOwnsLazyValues = true;
        this.mLazyValues = 0;
        this.mWeakParcelledData = null;
        this.mMap = i > 0 ? new android.util.ArrayMap<>(i) : new android.util.ArrayMap<>();
        this.mClassLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    BaseBundle() {
        this((java.lang.ClassLoader) null, 0);
    }

    BaseBundle(android.os.Parcel parcel) {
        this.mMap = null;
        this.mParcelledData = null;
        this.mOwnsLazyValues = true;
        this.mLazyValues = 0;
        this.mWeakParcelledData = null;
        readFromParcelInner(parcel);
    }

    BaseBundle(android.os.Parcel parcel, int i) {
        this.mMap = null;
        this.mParcelledData = null;
        this.mOwnsLazyValues = true;
        this.mLazyValues = 0;
        this.mWeakParcelledData = null;
        readFromParcelInner(parcel, i);
    }

    BaseBundle(java.lang.ClassLoader classLoader) {
        this(classLoader, 0);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    BaseBundle(int i) {
        this((java.lang.ClassLoader) null, i);
    }

    BaseBundle(android.os.BaseBundle baseBundle) {
        this(baseBundle, false);
    }

    BaseBundle(android.os.BaseBundle baseBundle, boolean z) {
        android.os.Parcel parcel = null;
        this.mMap = null;
        this.mParcelledData = null;
        this.mOwnsLazyValues = true;
        this.mLazyValues = 0;
        this.mWeakParcelledData = null;
        synchronized (baseBundle) {
            this.mClassLoader = baseBundle.mClassLoader;
            if (baseBundle.mMap != null) {
                this.mOwnsLazyValues = false;
                baseBundle.mOwnsLazyValues = false;
                if (!z) {
                    this.mMap = new android.util.ArrayMap<>(baseBundle.mMap);
                } else {
                    android.util.ArrayMap<java.lang.String, java.lang.Object> arrayMap = baseBundle.mMap;
                    int size = arrayMap.size();
                    this.mMap = new android.util.ArrayMap<>(size);
                    for (int i = 0; i < size; i++) {
                        this.mMap.append(arrayMap.keyAt(i), deepCopyValue(arrayMap.valueAt(i)));
                    }
                }
            } else {
                this.mMap = null;
            }
            if (baseBundle.mParcelledData != null) {
                if (baseBundle.isEmptyParcel()) {
                    parcel = android.os.BaseBundle.NoImagePreloadHolder.EMPTY_PARCEL;
                    this.mParcelledByNative = false;
                } else {
                    parcel = android.os.Parcel.obtain();
                    parcel.appendFrom(baseBundle.mParcelledData, 0, baseBundle.mParcelledData.dataSize());
                    parcel.setDataPosition(0);
                    this.mParcelledByNative = baseBundle.mParcelledByNative;
                }
            } else {
                this.mParcelledByNative = false;
            }
            this.mParcelledData = parcel;
        }
    }

    public java.lang.String getPairValue() {
        unparcel();
        int size = this.mMap.size();
        if (size > 1) {
            android.util.Log.w(TAG, "getPairValue() used on Bundle with multiple pairs.");
        }
        if (size == 0) {
            return null;
        }
        try {
            return (java.lang.String) getValueAt(0, java.lang.String.class, new java.lang.Class[0]);
        } catch (android.os.BadTypeParcelableException | java.lang.ClassCastException e) {
            typeWarning("getPairValue()", "String", e);
            return null;
        }
    }

    void setClassLoader(java.lang.ClassLoader classLoader) {
        this.mClassLoader = classLoader;
    }

    java.lang.ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    final void unparcel() {
        unparcel(false);
    }

    final void unparcel(boolean z) {
        synchronized (this) {
            android.os.Parcel parcel = this.mParcelledData;
            if (parcel != null) {
                com.android.internal.util.Preconditions.checkState(this.mOwnsLazyValues);
                initializeFromParcelLocked(parcel, true, this.mParcelledByNative);
            }
            if (z) {
                int size = this.mMap.size();
                for (int i = 0; i < size; i++) {
                    getValueAt(i, null, new java.lang.Class[0]);
                }
            }
        }
    }

    @java.lang.Deprecated
    final java.lang.Object getValue(java.lang.String str) {
        return getValue(str, null);
    }

    final <T> T getValue(java.lang.String str, java.lang.Class<T> cls) {
        return (T) getValue(str, cls, null);
    }

    final <T> T getValue(java.lang.String str, java.lang.Class<T> cls, java.lang.Class<?>... clsArr) {
        int indexOfKey = this.mMap.indexOfKey(str);
        if (indexOfKey >= 0) {
            return (T) getValueAt(indexOfKey, cls, clsArr);
        }
        return null;
    }

    final <T> T getValueAt(int i, java.lang.Class<T> cls, java.lang.Class<?>... clsArr) {
        T t = (T) this.mMap.valueAt(i);
        if (t instanceof java.util.function.BiFunction) {
            synchronized (this) {
                t = (T) unwrapLazyValueFromMapLocked(i, cls, clsArr);
            }
        }
        return cls != null ? cls.cast(t) : t;
    }

    private java.lang.Object unwrapLazyValueFromMapLocked(int i, java.lang.Class<?> cls, java.lang.Class<?>... clsArr) {
        java.lang.Object valueAt = this.mMap.valueAt(i);
        if (valueAt instanceof java.util.function.BiFunction) {
            try {
                valueAt = ((java.util.function.BiFunction) valueAt).apply(cls, clsArr);
                this.mMap.setValueAt(i, valueAt);
                this.mLazyValues--;
                if (this.mOwnsLazyValues) {
                    com.android.internal.util.Preconditions.checkState(this.mLazyValues >= 0, "Lazy values ref count below 0");
                    if (this.mLazyValues == 0) {
                        com.android.internal.util.Preconditions.checkState(this.mWeakParcelledData.get() != null, "Parcel recycled earlier than expected");
                        recycleParcel(this.mWeakParcelledData.get());
                        this.mWeakParcelledData = null;
                    }
                }
            } catch (android.os.BadParcelableException e) {
                if (sShouldDefuse) {
                    android.util.Log.w(TAG, "Failed to parse item " + this.mMap.keyAt(i) + ", returning null.", e);
                    return null;
                }
                throw e;
            }
        }
        return valueAt;
    }

    private void initializeFromParcelLocked(android.os.Parcel parcel, boolean z, boolean z2) {
        if (isEmptyParcel(parcel)) {
            if (this.mMap == null) {
                this.mMap = new android.util.ArrayMap<>(1);
            } else {
                this.mMap.erase();
            }
            this.mParcelledByNative = false;
            this.mParcelledData = null;
            return;
        }
        int readInt = parcel.readInt();
        if (readInt < 0) {
            return;
        }
        android.util.ArrayMap<java.lang.String, java.lang.Object> arrayMap = this.mMap;
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap<>(readInt);
        } else {
            arrayMap.erase();
            arrayMap.ensureCapacity(readInt);
        }
        try {
            try {
                int readArrayMap = parcel.readArrayMap(arrayMap, readInt, !z2, z, this.mClassLoader);
                this.mWeakParcelledData = null;
                if (z) {
                    if (readArrayMap == 0) {
                        recycleParcel(parcel);
                    } else {
                        this.mWeakParcelledData = new java.lang.ref.WeakReference<>(parcel);
                    }
                }
                this.mLazyValues = readArrayMap;
            } catch (android.os.BadParcelableException e) {
                if (!sShouldDefuse) {
                    throw e;
                }
                android.util.Log.w(TAG, "Failed to parse Bundle, but defusing quietly", e);
                arrayMap.erase();
                this.mWeakParcelledData = null;
                if (z) {
                    recycleParcel(parcel);
                }
                this.mLazyValues = 0;
            }
            this.mParcelledByNative = false;
            this.mMap = arrayMap;
            this.mParcelledData = null;
        } catch (java.lang.Throwable th) {
            this.mWeakParcelledData = null;
            if (z) {
                recycleParcel(parcel);
            }
            this.mLazyValues = 0;
            this.mParcelledByNative = false;
            this.mMap = arrayMap;
            this.mParcelledData = null;
            throw th;
        }
    }

    public boolean isParcelled() {
        return this.mParcelledData != null;
    }

    public boolean isEmptyParcel() {
        return isEmptyParcel(this.mParcelledData);
    }

    private static boolean isEmptyParcel(android.os.Parcel parcel) {
        return parcel == android.os.BaseBundle.NoImagePreloadHolder.EMPTY_PARCEL;
    }

    private static void recycleParcel(android.os.Parcel parcel) {
        if (parcel != null && !isEmptyParcel(parcel)) {
            parcel.recycle();
        }
    }

    android.util.ArrayMap<java.lang.String, java.lang.Object> getItemwiseMap() {
        unparcel(true);
        return this.mMap;
    }

    public int size() {
        unparcel();
        return this.mMap.size();
    }

    public boolean isEmpty() {
        unparcel();
        return this.mMap.isEmpty();
    }

    public boolean isDefinitelyEmpty() {
        if (isParcelled()) {
            return isEmptyParcel();
        }
        return isEmpty();
    }

    public static boolean kindofEquals(android.os.BaseBundle baseBundle, android.os.BaseBundle baseBundle2) {
        return baseBundle == baseBundle2 || (baseBundle != null && baseBundle.kindofEquals(baseBundle2));
    }

    public boolean kindofEquals(android.os.BaseBundle baseBundle) {
        if (baseBundle == null) {
            return false;
        }
        if (isDefinitelyEmpty() && baseBundle.isDefinitelyEmpty()) {
            return true;
        }
        if (isParcelled() != baseBundle.isParcelled()) {
            return false;
        }
        if (isParcelled()) {
            return this.mParcelledData.compareData(baseBundle.mParcelledData) == 0;
        }
        return this.mMap.equals(baseBundle.mMap);
    }

    public void clear() {
        unparcel();
        if (this.mOwnsLazyValues && this.mWeakParcelledData != null) {
            recycleParcel(this.mWeakParcelledData.get());
        }
        this.mWeakParcelledData = null;
        this.mLazyValues = 0;
        this.mOwnsLazyValues = true;
        this.mMap.clear();
    }

    private java.lang.Object deepCopyValue(java.lang.Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof android.os.Bundle) {
            return ((android.os.Bundle) obj).deepCopy();
        }
        if (obj instanceof android.os.PersistableBundle) {
            return ((android.os.PersistableBundle) obj).deepCopy();
        }
        if (obj instanceof java.util.ArrayList) {
            return deepcopyArrayList((java.util.ArrayList) obj);
        }
        if (obj.getClass().isArray()) {
            if (obj instanceof int[]) {
                return ((int[]) obj).clone();
            }
            if (obj instanceof long[]) {
                return ((long[]) obj).clone();
            }
            if (obj instanceof float[]) {
                return ((float[]) obj).clone();
            }
            if (obj instanceof double[]) {
                return ((double[]) obj).clone();
            }
            if (obj instanceof java.lang.Object[]) {
                return ((java.lang.Object[]) obj).clone();
            }
            if (obj instanceof byte[]) {
                return ((byte[]) obj).clone();
            }
            if (obj instanceof short[]) {
                return ((short[]) obj).clone();
            }
            if (obj instanceof char[]) {
                return ((char[]) obj).clone();
            }
        }
        return obj;
    }

    private java.util.ArrayList deepcopyArrayList(java.util.ArrayList arrayList) {
        int size = arrayList.size();
        java.util.ArrayList arrayList2 = new java.util.ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList2.add(deepCopyValue(arrayList.get(i)));
        }
        return arrayList2;
    }

    public boolean containsKey(java.lang.String str) {
        unparcel();
        return this.mMap.containsKey(str);
    }

    @java.lang.Deprecated
    public java.lang.Object get(java.lang.String str) {
        unparcel();
        return getValue(str);
    }

    <T> T get(java.lang.String str, java.lang.Class<T> cls) {
        unparcel();
        try {
            return (T) getValue(str, (java.lang.Class) java.util.Objects.requireNonNull(cls));
        } catch (android.os.BadTypeParcelableException | java.lang.ClassCastException e) {
            typeWarning(str, cls.getCanonicalName(), e);
            return null;
        }
    }

    public void remove(java.lang.String str) {
        unparcel();
        this.mMap.remove(str);
    }

    public void putAll(android.os.PersistableBundle persistableBundle) {
        unparcel();
        persistableBundle.unparcel();
        this.mMap.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends java.lang.Object>) persistableBundle.mMap);
    }

    void putAll(android.util.ArrayMap arrayMap) {
        unparcel();
        this.mMap.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends java.lang.Object>) arrayMap);
    }

    public java.util.Set<java.lang.String> keySet() {
        unparcel();
        return this.mMap.keySet();
    }

    public void putObject(java.lang.String str, java.lang.Object obj) {
        if (obj == null) {
            putString(str, null);
            return;
        }
        if (obj instanceof java.lang.Boolean) {
            putBoolean(str, ((java.lang.Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof java.lang.Integer) {
            putInt(str, ((java.lang.Integer) obj).intValue());
            return;
        }
        if (obj instanceof java.lang.Long) {
            putLong(str, ((java.lang.Long) obj).longValue());
            return;
        }
        if (obj instanceof java.lang.Double) {
            putDouble(str, ((java.lang.Double) obj).doubleValue());
            return;
        }
        if (obj instanceof java.lang.String) {
            putString(str, (java.lang.String) obj);
            return;
        }
        if (obj instanceof boolean[]) {
            putBooleanArray(str, (boolean[]) obj);
            return;
        }
        if (obj instanceof int[]) {
            putIntArray(str, (int[]) obj);
            return;
        }
        if (obj instanceof long[]) {
            putLongArray(str, (long[]) obj);
        } else if (obj instanceof double[]) {
            putDoubleArray(str, (double[]) obj);
        } else {
            if (obj instanceof java.lang.String[]) {
                putStringArray(str, (java.lang.String[]) obj);
                return;
            }
            throw new java.lang.IllegalArgumentException("Unsupported type " + obj.getClass());
        }
    }

    public void putBoolean(java.lang.String str, boolean z) {
        unparcel();
        this.mMap.put(str, java.lang.Boolean.valueOf(z));
    }

    void putByte(java.lang.String str, byte b) {
        unparcel();
        this.mMap.put(str, java.lang.Byte.valueOf(b));
    }

    void putChar(java.lang.String str, char c) {
        unparcel();
        this.mMap.put(str, java.lang.Character.valueOf(c));
    }

    void putShort(java.lang.String str, short s) {
        unparcel();
        this.mMap.put(str, java.lang.Short.valueOf(s));
    }

    public void putInt(java.lang.String str, int i) {
        unparcel();
        this.mMap.put(str, java.lang.Integer.valueOf(i));
    }

    public void putLong(java.lang.String str, long j) {
        unparcel();
        this.mMap.put(str, java.lang.Long.valueOf(j));
    }

    void putFloat(java.lang.String str, float f) {
        unparcel();
        this.mMap.put(str, java.lang.Float.valueOf(f));
    }

    public void putDouble(java.lang.String str, double d) {
        unparcel();
        this.mMap.put(str, java.lang.Double.valueOf(d));
    }

    public void putString(java.lang.String str, java.lang.String str2) {
        unparcel();
        this.mMap.put(str, str2);
    }

    void putCharSequence(java.lang.String str, java.lang.CharSequence charSequence) {
        unparcel();
        this.mMap.put(str, charSequence);
    }

    void putIntegerArrayList(java.lang.String str, java.util.ArrayList<java.lang.Integer> arrayList) {
        unparcel();
        this.mMap.put(str, arrayList);
    }

    void putStringArrayList(java.lang.String str, java.util.ArrayList<java.lang.String> arrayList) {
        unparcel();
        this.mMap.put(str, arrayList);
    }

    void putCharSequenceArrayList(java.lang.String str, java.util.ArrayList<java.lang.CharSequence> arrayList) {
        unparcel();
        this.mMap.put(str, arrayList);
    }

    void putSerializable(java.lang.String str, java.io.Serializable serializable) {
        unparcel();
        this.mMap.put(str, serializable);
    }

    public void putBooleanArray(java.lang.String str, boolean[] zArr) {
        unparcel();
        this.mMap.put(str, zArr);
    }

    void putByteArray(java.lang.String str, byte[] bArr) {
        unparcel();
        this.mMap.put(str, bArr);
    }

    void putShortArray(java.lang.String str, short[] sArr) {
        unparcel();
        this.mMap.put(str, sArr);
    }

    void putCharArray(java.lang.String str, char[] cArr) {
        unparcel();
        this.mMap.put(str, cArr);
    }

    public void putIntArray(java.lang.String str, int[] iArr) {
        unparcel();
        this.mMap.put(str, iArr);
    }

    public void putLongArray(java.lang.String str, long[] jArr) {
        unparcel();
        this.mMap.put(str, jArr);
    }

    void putFloatArray(java.lang.String str, float[] fArr) {
        unparcel();
        this.mMap.put(str, fArr);
    }

    public void putDoubleArray(java.lang.String str, double[] dArr) {
        unparcel();
        this.mMap.put(str, dArr);
    }

    public void putStringArray(java.lang.String str, java.lang.String[] strArr) {
        unparcel();
        this.mMap.put(str, strArr);
    }

    void putCharSequenceArray(java.lang.String str, java.lang.CharSequence[] charSequenceArr) {
        unparcel();
        this.mMap.put(str, charSequenceArr);
    }

    public boolean getBoolean(java.lang.String str) {
        unparcel();
        return getBoolean(str, false);
    }

    void typeWarning(java.lang.String str, java.lang.Object obj, java.lang.String str2, java.lang.Object obj2, java.lang.RuntimeException runtimeException) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Key ");
        sb.append(str);
        sb.append(" expected ");
        sb.append(str2);
        if (obj != null) {
            sb.append(" but value was a ");
            sb.append(obj.getClass().getName());
        } else {
            sb.append(" but value was of a different type");
        }
        sb.append(".  The default value ");
        sb.append(obj2);
        sb.append(" was returned.");
        android.util.Log.w(TAG, sb.toString());
        android.util.Log.w(TAG, "Attempt to cast generated internal exception:", runtimeException);
    }

    void typeWarning(java.lang.String str, java.lang.Object obj, java.lang.String str2, java.lang.RuntimeException runtimeException) {
        typeWarning(str, obj, str2, "<null>", runtimeException);
    }

    void typeWarning(java.lang.String str, java.lang.String str2, java.lang.RuntimeException runtimeException) {
        typeWarning(str, null, str2, "<null>", runtimeException);
    }

    public boolean getBoolean(java.lang.String str, boolean z) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return z;
        }
        try {
            return ((java.lang.Boolean) obj).booleanValue();
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "Boolean", java.lang.Boolean.valueOf(z), e);
            return z;
        }
    }

    byte getByte(java.lang.String str) {
        unparcel();
        return getByte(str, (byte) 0).byteValue();
    }

    java.lang.Byte getByte(java.lang.String str, byte b) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return java.lang.Byte.valueOf(b);
        }
        try {
            return (java.lang.Byte) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "Byte", java.lang.Byte.valueOf(b), e);
            return java.lang.Byte.valueOf(b);
        }
    }

    char getChar(java.lang.String str) {
        unparcel();
        return getChar(str, (char) 0);
    }

    char getChar(java.lang.String str, char c) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return c;
        }
        try {
            return ((java.lang.Character) obj).charValue();
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "Character", java.lang.Character.valueOf(c), e);
            return c;
        }
    }

    short getShort(java.lang.String str) {
        unparcel();
        return getShort(str, (short) 0);
    }

    short getShort(java.lang.String str, short s) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return s;
        }
        try {
            return ((java.lang.Short) obj).shortValue();
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "Short", java.lang.Short.valueOf(s), e);
            return s;
        }
    }

    public int getInt(java.lang.String str) {
        unparcel();
        return getInt(str, 0);
    }

    public int getInt(java.lang.String str, int i) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return i;
        }
        try {
            return ((java.lang.Integer) obj).intValue();
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "Integer", java.lang.Integer.valueOf(i), e);
            return i;
        }
    }

    public long getLong(java.lang.String str) {
        unparcel();
        return getLong(str, 0L);
    }

    public long getLong(java.lang.String str, long j) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return j;
        }
        try {
            return ((java.lang.Long) obj).longValue();
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "Long", java.lang.Long.valueOf(j), e);
            return j;
        }
    }

    float getFloat(java.lang.String str) {
        unparcel();
        return getFloat(str, 0.0f);
    }

    float getFloat(java.lang.String str, float f) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return f;
        }
        try {
            return ((java.lang.Float) obj).floatValue();
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "Float", java.lang.Float.valueOf(f), e);
            return f;
        }
    }

    public double getDouble(java.lang.String str) {
        unparcel();
        return getDouble(str, 0.0d);
    }

    public double getDouble(java.lang.String str, double d) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return d;
        }
        try {
            return ((java.lang.Double) obj).doubleValue();
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "Double", java.lang.Double.valueOf(d), e);
            return d;
        }
    }

    public java.lang.String getString(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        try {
            return (java.lang.String) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "String", e);
            return null;
        }
    }

    public java.lang.String getString(java.lang.String str, java.lang.String str2) {
        java.lang.String string = getString(str);
        return string == null ? str2 : string;
    }

    java.lang.CharSequence getCharSequence(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        try {
            return (java.lang.CharSequence) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "CharSequence", e);
            return null;
        }
    }

    java.lang.CharSequence getCharSequence(java.lang.String str, java.lang.CharSequence charSequence) {
        java.lang.CharSequence charSequence2 = getCharSequence(str);
        return charSequence2 == null ? charSequence : charSequence2;
    }

    @java.lang.Deprecated
    java.io.Serializable getSerializable(java.lang.String str) {
        unparcel();
        java.lang.Object value = getValue(str);
        if (value == null) {
            return null;
        }
        try {
            return (java.io.Serializable) value;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, value, "Serializable", e);
            return null;
        }
    }

    <T extends java.io.Serializable> T getSerializable(java.lang.String str, java.lang.Class<T> cls) {
        return (T) get(str, cls);
    }

    <T> java.util.ArrayList<T> getArrayList(java.lang.String str, java.lang.Class<? extends T> cls) {
        unparcel();
        try {
            return (java.util.ArrayList) getValue(str, java.util.ArrayList.class, (java.lang.Class) java.util.Objects.requireNonNull(cls));
        } catch (android.os.BadTypeParcelableException | java.lang.ClassCastException e) {
            typeWarning(str, "ArrayList<" + cls.getCanonicalName() + ">", e);
            return null;
        }
    }

    java.util.ArrayList<java.lang.Integer> getIntegerArrayList(java.lang.String str) {
        return getArrayList(str, java.lang.Integer.class);
    }

    java.util.ArrayList<java.lang.String> getStringArrayList(java.lang.String str) {
        return getArrayList(str, java.lang.String.class);
    }

    java.util.ArrayList<java.lang.CharSequence> getCharSequenceArrayList(java.lang.String str) {
        return getArrayList(str, java.lang.CharSequence.class);
    }

    public boolean[] getBooleanArray(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (boolean[]) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "byte[]", e);
            return null;
        }
    }

    byte[] getByteArray(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (byte[]) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "byte[]", e);
            return null;
        }
    }

    short[] getShortArray(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (short[]) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "short[]", e);
            return null;
        }
    }

    char[] getCharArray(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (char[]) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "char[]", e);
            return null;
        }
    }

    public int[] getIntArray(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (int[]) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "int[]", e);
            return null;
        }
    }

    public long[] getLongArray(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (long[]) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "long[]", e);
            return null;
        }
    }

    float[] getFloatArray(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (float[]) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "float[]", e);
            return null;
        }
    }

    public double[] getDoubleArray(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (double[]) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "double[]", e);
            return null;
        }
    }

    public java.lang.String[] getStringArray(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (java.lang.String[]) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "String[]", e);
            return null;
        }
    }

    java.lang.CharSequence[] getCharSequenceArray(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (java.lang.CharSequence[]) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "CharSequence[]", e);
            return null;
        }
    }

    void writeToParcelInner(android.os.Parcel parcel, int i) {
        if (parcel.hasReadWriteHelper()) {
            unparcel(true);
        }
        synchronized (this) {
            android.os.Parcel parcel2 = this.mParcelledData;
            int i2 = BUNDLE_MAGIC;
            if (parcel2 != null) {
                if (this.mParcelledData == android.os.BaseBundle.NoImagePreloadHolder.EMPTY_PARCEL) {
                    parcel.writeInt(0);
                } else {
                    int dataSize = this.mParcelledData.dataSize();
                    parcel.writeInt(dataSize);
                    if (this.mParcelledByNative) {
                        i2 = BUNDLE_MAGIC_NATIVE;
                    }
                    parcel.writeInt(i2);
                    parcel.appendFrom(this.mParcelledData, 0, dataSize);
                }
                return;
            }
            android.util.ArrayMap<java.lang.String, java.lang.Object> arrayMap = this.mMap;
            if (arrayMap == null || arrayMap.size() <= 0) {
                parcel.writeInt(0);
                return;
            }
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(-1);
            parcel.writeInt(BUNDLE_MAGIC);
            int dataPosition2 = parcel.dataPosition();
            parcel.writeArrayMapInternal(arrayMap);
            int dataPosition3 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition3 - dataPosition2);
            parcel.setDataPosition(dataPosition3);
        }
    }

    void readFromParcelInner(android.os.Parcel parcel) {
        readFromParcelInner(parcel, parcel.readInt());
    }

    private void readFromParcelInner(android.os.Parcel parcel, int i) {
        if (i < 0) {
            throw new java.lang.RuntimeException("Bad length in parcel: " + i);
        }
        if (i == 0) {
            this.mParcelledByNative = false;
            this.mParcelledData = android.os.BaseBundle.NoImagePreloadHolder.EMPTY_PARCEL;
            return;
        }
        if (i % 4 != 0) {
            throw new java.lang.IllegalStateException("Bundle length is not aligned by 4: " + i);
        }
        int readInt = parcel.readInt();
        boolean z = readInt == BUNDLE_MAGIC;
        boolean z2 = readInt == BUNDLE_MAGIC_NATIVE;
        if (!z && !z2) {
            throw new java.lang.IllegalStateException("Bad magic number for Bundle: 0x" + java.lang.Integer.toHexString(readInt));
        }
        if (parcel.hasReadWriteHelper()) {
            synchronized (this) {
                this.mOwnsLazyValues = false;
                initializeFromParcelLocked(parcel, false, z2);
            }
            return;
        }
        int dataPosition = parcel.dataPosition();
        parcel.setDataPosition(android.util.MathUtils.addOrThrow(dataPosition, i));
        android.os.Parcel obtain = android.os.Parcel.obtain();
        obtain.setDataPosition(0);
        obtain.appendFrom(parcel, dataPosition, i);
        obtain.adoptClassCookies(parcel);
        obtain.setDataPosition(0);
        this.mOwnsLazyValues = true;
        this.mParcelledByNative = z2;
        this.mParcelledData = obtain;
    }

    public static void dumpStats(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, java.lang.Object obj) {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        obtain.writeValue(obj);
        int dataPosition = obtain.dataPosition();
        obtain.recycle();
        if (dataPosition > 1024) {
            indentingPrintWriter.println(str + " [size=" + dataPosition + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            if (obj instanceof android.os.BaseBundle) {
                dumpStats(indentingPrintWriter, (android.os.BaseBundle) obj);
            } else if (obj instanceof android.util.SparseArray) {
                dumpStats(indentingPrintWriter, (android.util.SparseArray) obj);
            }
        }
    }

    public static void dumpStats(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, android.util.SparseArray sparseArray) {
        indentingPrintWriter.increaseIndent();
        if (sparseArray == null) {
            indentingPrintWriter.println("[null]");
            return;
        }
        for (int i = 0; i < sparseArray.size(); i++) {
            dumpStats(indentingPrintWriter, "0x" + java.lang.Integer.toHexString(sparseArray.keyAt(i)), sparseArray.valueAt(i));
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static void dumpStats(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, android.os.BaseBundle baseBundle) {
        indentingPrintWriter.increaseIndent();
        if (baseBundle == null) {
            indentingPrintWriter.println("[null]");
            return;
        }
        android.util.ArrayMap<java.lang.String, java.lang.Object> itemwiseMap = baseBundle.getItemwiseMap();
        for (int i = 0; i < itemwiseMap.size(); i++) {
            dumpStats(indentingPrintWriter, itemwiseMap.keyAt(i), itemwiseMap.valueAt(i));
        }
        indentingPrintWriter.decreaseIndent();
    }
}
