package android.os;

/* loaded from: classes3.dex */
public final class Bundle extends android.os.BaseBundle implements java.lang.Cloneable, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.Bundle> CREATOR;
    public static final android.os.Bundle EMPTY = new android.os.Bundle();
    static final int FLAG_ALLOW_FDS = 1024;
    static final int FLAG_HAS_FDS = 256;
    static final int FLAG_HAS_FDS_KNOWN = 512;
    public static final android.os.Bundle STRIPPED;

    static {
        EMPTY.mMap = android.util.ArrayMap.EMPTY;
        STRIPPED = new android.os.Bundle();
        STRIPPED.putInt("STRIPPED", 1);
        CREATOR = new android.os.Parcelable.Creator<android.os.Bundle>() { // from class: android.os.Bundle.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.Bundle createFromParcel(android.os.Parcel parcel) {
                return parcel.readBundle();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.Bundle[] newArray(int i) {
                return new android.os.Bundle[i];
            }
        };
    }

    public Bundle() {
        this.mFlags = 1536;
    }

    public Bundle(android.os.Parcel parcel) {
        super(parcel);
        this.mFlags = 1024;
        maybePrefillHasFds();
    }

    public Bundle(android.os.Parcel parcel, int i) {
        super(parcel, i);
        this.mFlags = 1024;
        maybePrefillHasFds();
    }

    Bundle(android.os.Bundle bundle, boolean z) {
        super(bundle, z);
    }

    private void maybePrefillHasFds() {
        if (this.mParcelledData != null) {
            if (this.mParcelledData.hasFileDescriptors()) {
                this.mFlags |= 768;
            } else {
                this.mFlags |= 512;
            }
        }
    }

    public Bundle(java.lang.ClassLoader classLoader) {
        super(classLoader);
        this.mFlags = 1536;
    }

    public Bundle(int i) {
        super(i);
        this.mFlags = 1536;
    }

    public Bundle(android.os.Bundle bundle) {
        super(bundle);
        this.mFlags = bundle.mFlags;
    }

    public Bundle(android.os.PersistableBundle persistableBundle) {
        super(persistableBundle);
        this.mFlags = 1536;
    }

    public static android.os.Bundle forPair(java.lang.String str, java.lang.String str2) {
        android.os.Bundle bundle = new android.os.Bundle(1);
        bundle.putString(str, str2);
        return bundle;
    }

    @Override // android.os.BaseBundle
    public void setClassLoader(java.lang.ClassLoader classLoader) {
        super.setClassLoader(classLoader);
    }

    @Override // android.os.BaseBundle
    public java.lang.ClassLoader getClassLoader() {
        return super.getClassLoader();
    }

    public boolean setAllowFds(boolean z) {
        boolean z2 = (this.mFlags & 1024) != 0;
        if (z) {
            this.mFlags |= 1024;
        } else {
            this.mFlags &= -1025;
        }
        return z2;
    }

    public void setDefusable(boolean z) {
        if (z) {
            this.mFlags |= 1;
        } else {
            this.mFlags &= -2;
        }
    }

    public static android.os.Bundle setDefusable(android.os.Bundle bundle, boolean z) {
        if (bundle != null) {
            bundle.setDefusable(z);
        }
        return bundle;
    }

    public java.lang.Object clone() {
        return new android.os.Bundle(this);
    }

    public android.os.Bundle deepCopy() {
        return new android.os.Bundle(this, true);
    }

    @Override // android.os.BaseBundle
    public void clear() {
        super.clear();
        this.mFlags = 1536;
    }

    @Override // android.os.BaseBundle
    public void remove(java.lang.String str) {
        super.remove(str);
        if ((this.mFlags & 256) != 0) {
            this.mFlags &= -513;
        }
    }

    public void putAll(android.os.Bundle bundle) {
        unparcel();
        bundle.unparcel();
        this.mOwnsLazyValues = false;
        bundle.mOwnsLazyValues = false;
        this.mMap.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends java.lang.Object>) bundle.mMap);
        if ((bundle.mFlags & 256) != 0) {
            this.mFlags |= 256;
        }
        if ((bundle.mFlags & 512) == 0) {
            this.mFlags &= -513;
        }
    }

    public int getSize() {
        if (this.mParcelledData != null) {
            return this.mParcelledData.dataSize();
        }
        return 0;
    }

    public boolean hasFileDescriptors() {
        int i;
        if ((this.mFlags & 512) == 0) {
            java.lang.Object obj = this.mParcelledData;
            if (obj == null) {
                obj = this.mMap;
            }
            if (android.os.Parcel.hasFileDescriptors(obj)) {
                i = this.mFlags | 256;
            } else {
                i = this.mFlags & (-257);
            }
            this.mFlags = i;
            this.mFlags |= 512;
        }
        return (this.mFlags & 256) != 0;
    }

    @Override // android.os.BaseBundle
    public void putObject(java.lang.String str, java.lang.Object obj) {
        if (obj instanceof java.lang.Byte) {
            putByte(str, ((java.lang.Byte) obj).byteValue());
            return;
        }
        if (obj instanceof java.lang.Character) {
            putChar(str, ((java.lang.Character) obj).charValue());
            return;
        }
        if (obj instanceof java.lang.Short) {
            putShort(str, ((java.lang.Short) obj).shortValue());
            return;
        }
        if (obj instanceof java.lang.Float) {
            putFloat(str, ((java.lang.Float) obj).floatValue());
            return;
        }
        if (obj instanceof java.lang.CharSequence) {
            putCharSequence(str, (java.lang.CharSequence) obj);
            return;
        }
        if (obj instanceof android.os.Parcelable) {
            putParcelable(str, (android.os.Parcelable) obj);
            return;
        }
        if (obj instanceof android.util.Size) {
            putSize(str, (android.util.Size) obj);
            return;
        }
        if (obj instanceof android.util.SizeF) {
            putSizeF(str, (android.util.SizeF) obj);
            return;
        }
        if (obj instanceof android.os.Parcelable[]) {
            putParcelableArray(str, (android.os.Parcelable[]) obj);
            return;
        }
        if (obj instanceof java.util.ArrayList) {
            putParcelableArrayList(str, (java.util.ArrayList) obj);
            return;
        }
        if (obj instanceof java.util.List) {
            putParcelableList(str, (java.util.List) obj);
            return;
        }
        if (obj instanceof android.util.SparseArray) {
            putSparseParcelableArray(str, (android.util.SparseArray) obj);
            return;
        }
        if (obj instanceof java.io.Serializable) {
            putSerializable(str, (java.io.Serializable) obj);
            return;
        }
        if (obj instanceof byte[]) {
            putByteArray(str, (byte[]) obj);
            return;
        }
        if (obj instanceof short[]) {
            putShortArray(str, (short[]) obj);
            return;
        }
        if (obj instanceof char[]) {
            putCharArray(str, (char[]) obj);
            return;
        }
        if (obj instanceof float[]) {
            putFloatArray(str, (float[]) obj);
            return;
        }
        if (obj instanceof java.lang.CharSequence[]) {
            putCharSequenceArray(str, (java.lang.CharSequence[]) obj);
            return;
        }
        if (obj instanceof android.os.Bundle) {
            putBundle(str, (android.os.Bundle) obj);
            return;
        }
        if (obj instanceof android.os.Binder) {
            putBinder(str, (android.os.Binder) obj);
        } else if (obj instanceof android.os.IBinder) {
            putIBinder(str, (android.os.IBinder) obj);
        } else {
            super.putObject(str, obj);
        }
    }

    @Override // android.os.BaseBundle
    public void putByte(java.lang.String str, byte b) {
        super.putByte(str, b);
    }

    @Override // android.os.BaseBundle
    public void putChar(java.lang.String str, char c) {
        super.putChar(str, c);
    }

    @Override // android.os.BaseBundle
    public void putShort(java.lang.String str, short s) {
        super.putShort(str, s);
    }

    @Override // android.os.BaseBundle
    public void putFloat(java.lang.String str, float f) {
        super.putFloat(str, f);
    }

    @Override // android.os.BaseBundle
    public void putCharSequence(java.lang.String str, java.lang.CharSequence charSequence) {
        super.putCharSequence(str, charSequence);
    }

    public void putParcelable(java.lang.String str, android.os.Parcelable parcelable) {
        unparcel();
        this.mMap.put(str, parcelable);
        this.mFlags &= -513;
    }

    public void putSize(java.lang.String str, android.util.Size size) {
        unparcel();
        this.mMap.put(str, size);
    }

    public void putSizeF(java.lang.String str, android.util.SizeF sizeF) {
        unparcel();
        this.mMap.put(str, sizeF);
    }

    public void putParcelableArray(java.lang.String str, android.os.Parcelable[] parcelableArr) {
        unparcel();
        this.mMap.put(str, parcelableArr);
        this.mFlags &= -513;
    }

    public void putParcelableArrayList(java.lang.String str, java.util.ArrayList<? extends android.os.Parcelable> arrayList) {
        unparcel();
        this.mMap.put(str, arrayList);
        this.mFlags &= -513;
    }

    public void putParcelableList(java.lang.String str, java.util.List<? extends android.os.Parcelable> list) {
        unparcel();
        this.mMap.put(str, list);
        this.mFlags &= -513;
    }

    public void putSparseParcelableArray(java.lang.String str, android.util.SparseArray<? extends android.os.Parcelable> sparseArray) {
        unparcel();
        this.mMap.put(str, sparseArray);
        this.mFlags &= -513;
    }

    @Override // android.os.BaseBundle
    public void putIntegerArrayList(java.lang.String str, java.util.ArrayList<java.lang.Integer> arrayList) {
        super.putIntegerArrayList(str, arrayList);
    }

    @Override // android.os.BaseBundle
    public void putStringArrayList(java.lang.String str, java.util.ArrayList<java.lang.String> arrayList) {
        super.putStringArrayList(str, arrayList);
    }

    @Override // android.os.BaseBundle
    public void putCharSequenceArrayList(java.lang.String str, java.util.ArrayList<java.lang.CharSequence> arrayList) {
        super.putCharSequenceArrayList(str, arrayList);
    }

    @Override // android.os.BaseBundle
    public void putSerializable(java.lang.String str, java.io.Serializable serializable) {
        super.putSerializable(str, serializable);
    }

    @Override // android.os.BaseBundle
    public void putByteArray(java.lang.String str, byte[] bArr) {
        super.putByteArray(str, bArr);
    }

    @Override // android.os.BaseBundle
    public void putShortArray(java.lang.String str, short[] sArr) {
        super.putShortArray(str, sArr);
    }

    @Override // android.os.BaseBundle
    public void putCharArray(java.lang.String str, char[] cArr) {
        super.putCharArray(str, cArr);
    }

    @Override // android.os.BaseBundle
    public void putFloatArray(java.lang.String str, float[] fArr) {
        super.putFloatArray(str, fArr);
    }

    @Override // android.os.BaseBundle
    public void putCharSequenceArray(java.lang.String str, java.lang.CharSequence[] charSequenceArr) {
        super.putCharSequenceArray(str, charSequenceArr);
    }

    public void putBundle(java.lang.String str, android.os.Bundle bundle) {
        unparcel();
        this.mMap.put(str, bundle);
    }

    public void putBinder(java.lang.String str, android.os.IBinder iBinder) {
        unparcel();
        this.mMap.put(str, iBinder);
    }

    @java.lang.Deprecated
    public void putIBinder(java.lang.String str, android.os.IBinder iBinder) {
        unparcel();
        this.mMap.put(str, iBinder);
    }

    @Override // android.os.BaseBundle
    public byte getByte(java.lang.String str) {
        return super.getByte(str);
    }

    @Override // android.os.BaseBundle
    public java.lang.Byte getByte(java.lang.String str, byte b) {
        return super.getByte(str, b);
    }

    @Override // android.os.BaseBundle
    public char getChar(java.lang.String str) {
        return super.getChar(str);
    }

    @Override // android.os.BaseBundle
    public char getChar(java.lang.String str, char c) {
        return super.getChar(str, c);
    }

    @Override // android.os.BaseBundle
    public short getShort(java.lang.String str) {
        return super.getShort(str);
    }

    @Override // android.os.BaseBundle
    public short getShort(java.lang.String str, short s) {
        return super.getShort(str, s);
    }

    @Override // android.os.BaseBundle
    public float getFloat(java.lang.String str) {
        return super.getFloat(str);
    }

    @Override // android.os.BaseBundle
    public float getFloat(java.lang.String str, float f) {
        return super.getFloat(str, f);
    }

    @Override // android.os.BaseBundle
    public java.lang.CharSequence getCharSequence(java.lang.String str) {
        return super.getCharSequence(str);
    }

    @Override // android.os.BaseBundle
    public java.lang.CharSequence getCharSequence(java.lang.String str, java.lang.CharSequence charSequence) {
        return super.getCharSequence(str, charSequence);
    }

    public android.util.Size getSize(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        try {
            return (android.util.Size) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "Size", e);
            return null;
        }
    }

    public android.util.SizeF getSizeF(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        try {
            return (android.util.SizeF) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "SizeF", e);
            return null;
        }
    }

    public android.os.Bundle getBundle(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (android.os.Bundle) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "Bundle", e);
            return null;
        }
    }

    @java.lang.Deprecated
    public <T extends android.os.Parcelable> T getParcelable(java.lang.String str) {
        unparcel();
        java.lang.Object value = getValue(str);
        if (value == null) {
            return null;
        }
        try {
            return (T) value;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, value, "Parcelable", e);
            return null;
        }
    }

    public <T> T getParcelable(java.lang.String str, java.lang.Class<T> cls) {
        return (T) get(str, cls);
    }

    @java.lang.Deprecated
    public android.os.Parcelable[] getParcelableArray(java.lang.String str) {
        unparcel();
        java.lang.Object value = getValue(str);
        if (value == null) {
            return null;
        }
        try {
            return (android.os.Parcelable[]) value;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, value, "Parcelable[]", e);
            return null;
        }
    }

    public <T> T[] getParcelableArray(java.lang.String str, java.lang.Class<T> cls) {
        unparcel();
        try {
            return (T[]) ((java.lang.Object[]) getValue(str, android.os.Parcelable[].class, (java.lang.Class) java.util.Objects.requireNonNull(cls)));
        } catch (android.os.BadTypeParcelableException | java.lang.ClassCastException e) {
            typeWarning(str, cls.getCanonicalName() + "[]", e);
            return null;
        }
    }

    @java.lang.Deprecated
    public <T extends android.os.Parcelable> java.util.ArrayList<T> getParcelableArrayList(java.lang.String str) {
        unparcel();
        java.lang.Object value = getValue(str);
        if (value == null) {
            return null;
        }
        try {
            return (java.util.ArrayList) value;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, value, "ArrayList", e);
            return null;
        }
    }

    public <T> java.util.ArrayList<T> getParcelableArrayList(java.lang.String str, java.lang.Class<? extends T> cls) {
        return getArrayList(str, cls);
    }

    @java.lang.Deprecated
    public <T extends android.os.Parcelable> android.util.SparseArray<T> getSparseParcelableArray(java.lang.String str) {
        unparcel();
        java.lang.Object value = getValue(str);
        if (value == null) {
            return null;
        }
        try {
            return (android.util.SparseArray) value;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, value, "SparseArray", e);
            return null;
        }
    }

    public <T> android.util.SparseArray<T> getSparseParcelableArray(java.lang.String str, java.lang.Class<? extends T> cls) {
        unparcel();
        try {
            return (android.util.SparseArray) getValue(str, android.util.SparseArray.class, (java.lang.Class) java.util.Objects.requireNonNull(cls));
        } catch (android.os.BadTypeParcelableException | java.lang.ClassCastException e) {
            typeWarning(str, "SparseArray<" + cls.getCanonicalName() + ">", e);
            return null;
        }
    }

    @Override // android.os.BaseBundle
    @java.lang.Deprecated
    public java.io.Serializable getSerializable(java.lang.String str) {
        return super.getSerializable(str);
    }

    @Override // android.os.BaseBundle
    public <T extends java.io.Serializable> T getSerializable(java.lang.String str, java.lang.Class<T> cls) {
        return (T) super.getSerializable(str, (java.lang.Class) java.util.Objects.requireNonNull(cls));
    }

    @Override // android.os.BaseBundle
    public java.util.ArrayList<java.lang.Integer> getIntegerArrayList(java.lang.String str) {
        return super.getIntegerArrayList(str);
    }

    @Override // android.os.BaseBundle
    public java.util.ArrayList<java.lang.String> getStringArrayList(java.lang.String str) {
        return super.getStringArrayList(str);
    }

    @Override // android.os.BaseBundle
    public java.util.ArrayList<java.lang.CharSequence> getCharSequenceArrayList(java.lang.String str) {
        return super.getCharSequenceArrayList(str);
    }

    @Override // android.os.BaseBundle
    public byte[] getByteArray(java.lang.String str) {
        return super.getByteArray(str);
    }

    @Override // android.os.BaseBundle
    public short[] getShortArray(java.lang.String str) {
        return super.getShortArray(str);
    }

    @Override // android.os.BaseBundle
    public char[] getCharArray(java.lang.String str) {
        return super.getCharArray(str);
    }

    @Override // android.os.BaseBundle
    public float[] getFloatArray(java.lang.String str) {
        return super.getFloatArray(str);
    }

    @Override // android.os.BaseBundle
    public java.lang.CharSequence[] getCharSequenceArray(java.lang.String str) {
        return super.getCharSequenceArray(str);
    }

    public android.os.IBinder getBinder(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (android.os.IBinder) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "IBinder", e);
            return null;
        }
    }

    @java.lang.Deprecated
    public android.os.IBinder getIBinder(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (android.os.IBinder) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "IBinder", e);
            return null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (!hasFileDescriptors()) {
            return 0;
        }
        return 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        boolean pushAllowFds = parcel.pushAllowFds((this.mFlags & 1024) != 0);
        try {
            writeToParcelInner(parcel, i);
        } finally {
            parcel.restoreAllowFds(pushAllowFds);
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        readFromParcelInner(parcel);
        this.mFlags = 1024;
        maybePrefillHasFds();
    }

    public synchronized java.lang.String toString() {
        if (this.mParcelledData != null) {
            if (isEmptyParcel()) {
                return "Bundle[EMPTY_PARCEL]";
            }
            return "Bundle[mParcelledData.dataSize=" + this.mParcelledData.dataSize() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
        return "Bundle[" + this.mMap.toString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public synchronized java.lang.String toShortString() {
        if (this.mParcelledData != null) {
            if (isEmptyParcel()) {
                return "EMPTY_PARCEL";
            }
            return "mParcelledData.dataSize=" + this.mParcelledData.dataSize();
        }
        return this.mMap.toString();
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        if (this.mParcelledData != null) {
            if (isEmptyParcel()) {
                protoOutputStream.write(1120986464257L, 0);
            } else {
                protoOutputStream.write(1120986464257L, this.mParcelledData.dataSize());
            }
        } else {
            protoOutputStream.write(1138166333442L, this.mMap.toString());
        }
        protoOutputStream.end(start);
    }
}
