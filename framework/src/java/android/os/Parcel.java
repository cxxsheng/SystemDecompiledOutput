package android.os;

/* loaded from: classes3.dex */
public final class Parcel {
    private static final int ARRAY_ALLOCATION_LIMIT = 1000000;
    private static final boolean DEBUG_ARRAY_MAP = false;
    private static final boolean DEBUG_RECYCLE = false;
    private static final int EX_BAD_PARCELABLE = -2;
    public static final int EX_HAS_NOTED_APPOPS_REPLY_HEADER = -127;
    private static final int EX_HAS_STRICTMODE_REPLY_HEADER = -128;
    private static final int EX_ILLEGAL_ARGUMENT = -3;
    private static final int EX_ILLEGAL_STATE = -5;
    private static final int EX_NETWORK_MAIN_THREAD = -6;
    private static final int EX_NULL_POINTER = -4;
    private static final int EX_PARCELABLE = -9;
    private static final int EX_SECURITY = -1;
    private static final int EX_SERVICE_SPECIFIC = -8;
    private static final int EX_TRANSACTION_FAILED = -129;
    private static final int EX_UNSUPPORTED_OPERATION = -7;
    public static final int FLAG_IS_REPLY_FROM_BLOCKING_ALLOWED_OBJECT = 1;
    public static final int FLAG_PROPAGATE_ALLOW_BLOCKING = 2;
    private static final int OK = 0;
    private static final int POOL_SIZE = 32;
    private static final int SIZE_BOOLEAN = 4;
    private static final int SIZE_BYTE = 1;
    private static final int SIZE_CHAR = 2;
    private static final int SIZE_COMPLEX_TYPE = 1;
    private static final int SIZE_DOUBLE = 8;
    private static final int SIZE_FLOAT = 4;
    private static final int SIZE_INT = 4;
    private static final int SIZE_LONG = 8;
    private static final int SIZE_SHORT = 2;
    private static final java.lang.String TAG = "Parcel";
    private static final int VAL_BOOLEAN = 9;
    private static final int VAL_BOOLEANARRAY = 23;
    private static final int VAL_BUNDLE = 3;
    private static final int VAL_BYTE = 20;
    private static final int VAL_BYTEARRAY = 13;
    private static final int VAL_CHAR = 29;
    private static final int VAL_CHARARRAY = 31;
    private static final int VAL_CHARSEQUENCE = 10;
    private static final int VAL_CHARSEQUENCEARRAY = 24;
    private static final int VAL_DOUBLE = 8;
    private static final int VAL_DOUBLEARRAY = 28;
    private static final int VAL_FLOAT = 7;
    private static final int VAL_FLOATARRAY = 32;
    private static final int VAL_IBINDER = 15;
    private static final int VAL_INTARRAY = 18;
    private static final int VAL_INTEGER = 1;
    private static final int VAL_LIST = 11;
    private static final int VAL_LONG = 6;
    private static final int VAL_LONGARRAY = 19;
    private static final int VAL_MAP = 2;
    private static final int VAL_NULL = -1;
    private static final int VAL_OBJECTARRAY = 17;
    private static final int VAL_PARCELABLE = 4;
    private static final int VAL_PARCELABLEARRAY = 16;
    private static final int VAL_PERSISTABLEBUNDLE = 25;
    private static final int VAL_SERIALIZABLE = 21;
    private static final int VAL_SHORT = 5;
    private static final int VAL_SHORTARRAY = 30;
    private static final int VAL_SIZE = 26;
    private static final int VAL_SIZEF = 27;
    private static final int VAL_SPARSEARRAY = 12;
    private static final int VAL_SPARSEBOOLEANARRAY = 22;
    private static final int VAL_STRING = 0;
    private static final int VAL_STRINGARRAY = 14;
    private static final int WRITE_EXCEPTION_STACK_TRACE_THRESHOLD_MS = 1000;
    private static android.os.Parcel sHolderPool;
    private static volatile long sLastWriteExceptionStackTrace;
    private static android.os.Parcel sOwnedPool;
    private static boolean sParcelExceptionStackTrace;
    private android.util.ArrayMap<java.lang.Class, java.lang.Object> mClassCookies;
    private int mFlags;
    private long mNativePtr;
    private long mNativeSize;
    private boolean mOwnsNativeParcelObject;
    private android.os.Parcel mPoolNext;
    private android.util.SparseArray<android.os.Parcelable> mReadSquashableParcelables;
    private java.lang.RuntimeException mStack;
    private android.util.ArrayMap<android.os.Parcelable, java.lang.Integer> mWrittenSquashableParcelables;
    private static final java.lang.Object sPoolSync = new java.lang.Object();
    private static int sOwnedPoolSize = 0;
    private static int sHolderPoolSize = 0;
    public static final android.os.Parcelable.Creator<java.lang.String> STRING_CREATOR = new android.os.Parcelable.Creator<java.lang.String>() { // from class: android.os.Parcel.1
        @Override // android.os.Parcelable.Creator
        public java.lang.String createFromParcel(android.os.Parcel parcel) {
            return parcel.readString();
        }

        @Override // android.os.Parcelable.Creator
        public java.lang.String[] newArray(int i) {
            return new java.lang.String[i];
        }
    };
    private static final java.util.HashMap<java.lang.ClassLoader, java.util.HashMap<java.lang.String, android.os.Parcelable.Creator<?>>> mCreators = new java.util.HashMap<>();
    private static final java.util.HashMap<java.lang.ClassLoader, java.util.HashMap<java.lang.String, android.util.Pair<android.os.Parcelable.Creator<?>, java.lang.Class<?>>>> sPairedCreators = new java.util.HashMap<>();
    private boolean mRecycled = false;
    private android.os.Parcel.ReadWriteHelper mReadWriteHelper = android.os.Parcel.ReadWriteHelper.DEFAULT;
    private boolean mAllowSquashing = false;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ParcelFlags {
    }

    public interface SquashReadHelper<T> {
        T readRawParceled(android.os.Parcel parcel);
    }

    public static native long getGlobalAllocCount();

    public static native long getGlobalAllocSize();

    private static native void nativeAppendFrom(long j, long j2, int i, int i2);

    private static native int nativeCompareData(long j, long j2);

    private static native boolean nativeCompareDataInRange(long j, int i, long j2, int i2, int i3);

    private static native long nativeCreate();

    private static native byte[] nativeCreateByteArray(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeDataAvail(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeDataCapacity(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeDataPosition(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeDataSize(long j);

    private static native void nativeDestroy(long j);

    private static native void nativeEnforceInterface(long j, java.lang.String str);

    private static native void nativeFreeBuffer(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nativeGetOpenAshmemSize(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nativeHasFileDescriptors(long j);

    private static native boolean nativeHasFileDescriptorsInRange(long j, int i, int i2);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nativeIsForRpc(long j);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeMarkForBinder(long j, android.os.IBinder iBinder);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeMarkSensitive(long j);

    private static native byte[] nativeMarshall(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nativePushAllowFds(long j, boolean z);

    private static native byte[] nativeReadBlob(long j);

    private static native boolean nativeReadByteArray(long j, byte[] bArr, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeReadCallingWorkSourceUid(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native double nativeReadDouble(long j);

    @dalvik.annotation.optimization.FastNative
    private static native java.io.FileDescriptor nativeReadFileDescriptor(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nativeReadFloat(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeReadInt(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nativeReadLong(long j);

    @dalvik.annotation.optimization.FastNative
    private static native java.lang.String nativeReadString16(long j);

    @dalvik.annotation.optimization.FastNative
    private static native java.lang.String nativeReadString8(long j);

    @dalvik.annotation.optimization.FastNative
    private static native android.os.IBinder nativeReadStrongBinder(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nativeReplaceCallingWorkSourceUid(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeRestoreAllowFds(long j, boolean z);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeSetDataCapacity(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nativeSetDataPosition(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeSetDataSize(long j, int i);

    private static native void nativeSignalExceptionForError(int i);

    private static native void nativeUnmarshall(long j, byte[] bArr, int i, int i2);

    private static native void nativeWriteBlob(long j, byte[] bArr, int i, int i2);

    private static native void nativeWriteByteArray(long j, byte[] bArr, int i, int i2);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeWriteDouble(long j, double d);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeWriteFileDescriptor(long j, java.io.FileDescriptor fileDescriptor);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeWriteFloat(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeWriteInt(long j, int i);

    private static native void nativeWriteInterfaceToken(long j, java.lang.String str);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nativeWriteLong(long j, long j2);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeWriteString16(long j, java.lang.String str);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeWriteString8(long j, java.lang.String str);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeWriteStrongBinder(long j, android.os.IBinder iBinder);

    public static class ReadWriteHelper {
        public static final android.os.Parcel.ReadWriteHelper DEFAULT = new android.os.Parcel.ReadWriteHelper();

        public void writeString8(android.os.Parcel parcel, java.lang.String str) {
            parcel.writeString8NoHelper(str);
        }

        public void writeString16(android.os.Parcel parcel, java.lang.String str) {
            parcel.writeString16NoHelper(str);
        }

        public java.lang.String readString8(android.os.Parcel parcel) {
            return parcel.readString8NoHelper();
        }

        public java.lang.String readString16(android.os.Parcel parcel) {
            return parcel.readString16NoHelper();
        }
    }

    public static android.os.Parcel obtain() {
        android.os.Parcel parcel;
        synchronized (sPoolSync) {
            parcel = null;
            if (sOwnedPool != null) {
                android.os.Parcel parcel2 = sOwnedPool;
                sOwnedPool = parcel2.mPoolNext;
                parcel2.mPoolNext = null;
                sOwnedPoolSize--;
                parcel = parcel2;
            }
        }
        if (parcel == null) {
            return new android.os.Parcel(0L);
        }
        parcel.mRecycled = false;
        parcel.mReadWriteHelper = android.os.Parcel.ReadWriteHelper.DEFAULT;
        return parcel;
    }

    public static android.os.Parcel obtain(android.os.IBinder iBinder) {
        android.os.Parcel obtain = obtain();
        obtain.markForBinder(iBinder);
        return obtain;
    }

    public final void recycle() {
        if (this.mRecycled) {
            android.util.Log.wtf(TAG, "Recycle called on unowned Parcel. (recycle twice?) Here: " + android.util.Log.getStackTraceString(new java.lang.Throwable()) + " Original recycle call (if DEBUG_RECYCLE): ", this.mStack);
            return;
        }
        this.mRecycled = true;
        this.mClassCookies = null;
        freeBuffer();
        if (this.mOwnsNativeParcelObject) {
            synchronized (sPoolSync) {
                if (sOwnedPoolSize < 32) {
                    this.mPoolNext = sOwnedPool;
                    sOwnedPool = this;
                    sOwnedPoolSize++;
                }
            }
            return;
        }
        this.mNativePtr = 0L;
        synchronized (sPoolSync) {
            if (sHolderPoolSize < 32) {
                this.mPoolNext = sHolderPool;
                sHolderPool = this;
                sHolderPoolSize++;
            }
        }
    }

    public void setReadWriteHelper(android.os.Parcel.ReadWriteHelper readWriteHelper) {
        if (readWriteHelper == null) {
            readWriteHelper = android.os.Parcel.ReadWriteHelper.DEFAULT;
        }
        this.mReadWriteHelper = readWriteHelper;
    }

    public boolean hasReadWriteHelper() {
        return (this.mReadWriteHelper == null || this.mReadWriteHelper == android.os.Parcel.ReadWriteHelper.DEFAULT) ? false : true;
    }

    public final void markSensitive() {
        nativeMarkSensitive(this.mNativePtr);
    }

    private void markForBinder(android.os.IBinder iBinder) {
        nativeMarkForBinder(this.mNativePtr, iBinder);
    }

    public final boolean isForRpc() {
        return nativeIsForRpc(this.mNativePtr);
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    public void addFlags(int i) {
        this.mFlags = i | this.mFlags;
    }

    private boolean hasFlags(int i) {
        return (this.mFlags & i) == i;
    }

    public void setPropagateAllowBlocking() {
        addFlags(2);
    }

    public int dataSize() {
        return nativeDataSize(this.mNativePtr);
    }

    public final int dataAvail() {
        return nativeDataAvail(this.mNativePtr);
    }

    public final int dataPosition() {
        return nativeDataPosition(this.mNativePtr);
    }

    public final int dataCapacity() {
        return nativeDataCapacity(this.mNativePtr);
    }

    public final void setDataSize(int i) {
        nativeSetDataSize(this.mNativePtr, i);
    }

    public final void setDataPosition(int i) {
        nativeSetDataPosition(this.mNativePtr, i);
    }

    public final void setDataCapacity(int i) {
        nativeSetDataCapacity(this.mNativePtr, i);
    }

    public final boolean pushAllowFds(boolean z) {
        return nativePushAllowFds(this.mNativePtr, z);
    }

    public final void restoreAllowFds(boolean z) {
        nativeRestoreAllowFds(this.mNativePtr, z);
    }

    public final byte[] marshall() {
        return nativeMarshall(this.mNativePtr);
    }

    public final void unmarshall(byte[] bArr, int i, int i2) {
        nativeUnmarshall(this.mNativePtr, bArr, i, i2);
    }

    public final void appendFrom(android.os.Parcel parcel, int i, int i2) {
        nativeAppendFrom(this.mNativePtr, parcel.mNativePtr, i, i2);
    }

    public int compareData(android.os.Parcel parcel) {
        return nativeCompareData(this.mNativePtr, parcel.mNativePtr);
    }

    public static boolean compareData(android.os.Parcel parcel, int i, android.os.Parcel parcel2, int i2, int i3) {
        return nativeCompareDataInRange(parcel.mNativePtr, i, parcel2.mNativePtr, i2, i3);
    }

    public final void setClassCookie(java.lang.Class cls, java.lang.Object obj) {
        if (this.mClassCookies == null) {
            this.mClassCookies = new android.util.ArrayMap<>();
        }
        this.mClassCookies.put(cls, obj);
    }

    public final java.lang.Object getClassCookie(java.lang.Class cls) {
        if (this.mClassCookies != null) {
            return this.mClassCookies.get(cls);
        }
        return null;
    }

    public final void adoptClassCookies(android.os.Parcel parcel) {
        this.mClassCookies = parcel.mClassCookies;
    }

    public java.util.Map<java.lang.Class, java.lang.Object> copyClassCookies() {
        return new android.util.ArrayMap(this.mClassCookies);
    }

    public void putClassCookies(java.util.Map<java.lang.Class, java.lang.Object> map) {
        if (map == null) {
            return;
        }
        if (this.mClassCookies == null) {
            this.mClassCookies = new android.util.ArrayMap<>();
        }
        this.mClassCookies.putAll(map);
    }

    public boolean hasFileDescriptors() {
        return nativeHasFileDescriptors(this.mNativePtr);
    }

    public boolean hasFileDescriptors(int i, int i2) {
        return nativeHasFileDescriptorsInRange(this.mNativePtr, i, i2);
    }

    public static boolean hasFileDescriptors(java.lang.Object obj) {
        if (obj instanceof android.os.Parcel) {
            if (((android.os.Parcel) obj).hasFileDescriptors()) {
                return true;
            }
        } else if (obj instanceof android.os.Parcel.LazyValue) {
            if (((android.os.Parcel.LazyValue) obj).hasFileDescriptors()) {
                return true;
            }
        } else if (obj instanceof android.os.Parcelable) {
            if ((((android.os.Parcelable) obj).describeContents() & 1) != 0) {
                return true;
            }
        } else if (obj instanceof android.util.ArrayMap) {
            android.util.ArrayMap arrayMap = (android.util.ArrayMap) obj;
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                if (hasFileDescriptors(arrayMap.keyAt(i)) || hasFileDescriptors(arrayMap.valueAt(i))) {
                    return true;
                }
            }
        } else if (obj instanceof java.util.Map) {
            for (java.util.Map.Entry entry : ((java.util.Map) obj).entrySet()) {
                if (hasFileDescriptors(entry.getKey()) || hasFileDescriptors(entry.getValue())) {
                    return true;
                }
            }
        } else if (obj instanceof java.util.List) {
            java.util.List list = (java.util.List) obj;
            int size2 = list.size();
            for (int i2 = 0; i2 < size2; i2++) {
                if (hasFileDescriptors(list.get(i2))) {
                    return true;
                }
            }
        } else if (obj instanceof android.util.SparseArray) {
            android.util.SparseArray sparseArray = (android.util.SparseArray) obj;
            int size3 = sparseArray.size();
            for (int i3 = 0; i3 < size3; i3++) {
                if (hasFileDescriptors(sparseArray.valueAt(i3))) {
                    return true;
                }
            }
        } else if (obj instanceof java.lang.Object[]) {
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                if (hasFileDescriptors(obj2)) {
                    return true;
                }
            }
        } else {
            getValueType(obj);
        }
        return false;
    }

    public final void writeInterfaceToken(java.lang.String str) {
        nativeWriteInterfaceToken(this.mNativePtr, str);
    }

    public final void enforceInterface(java.lang.String str) {
        nativeEnforceInterface(this.mNativePtr, str);
    }

    public void enforceNoDataAvail() {
        int dataAvail = dataAvail();
        if (dataAvail > 0) {
            throw new android.os.BadParcelableException("Parcel data not fully consumed, unread size: " + dataAvail);
        }
    }

    public boolean replaceCallingWorkSourceUid(int i) {
        return nativeReplaceCallingWorkSourceUid(this.mNativePtr, i);
    }

    public int readCallingWorkSourceUid() {
        return nativeReadCallingWorkSourceUid(this.mNativePtr);
    }

    public final void writeByteArray(byte[] bArr) {
        writeByteArray(bArr, 0, bArr != null ? bArr.length : 0);
    }

    public final void writeByteArray(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            writeInt(-1);
        } else {
            com.android.internal.util.ArrayUtils.throwsIfOutOfBounds(bArr.length, i, i2);
            nativeWriteByteArray(this.mNativePtr, bArr, i, i2);
        }
    }

    public final void writeBlob(byte[] bArr) {
        writeBlob(bArr, 0, bArr != null ? bArr.length : 0);
    }

    public final void writeBlob(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            writeInt(-1);
        } else {
            com.android.internal.util.ArrayUtils.throwsIfOutOfBounds(bArr.length, i, i2);
            nativeWriteBlob(this.mNativePtr, bArr, i, i2);
        }
    }

    public final void writeInt(int i) {
        int nativeWriteInt = nativeWriteInt(this.mNativePtr, i);
        if (nativeWriteInt != 0) {
            nativeSignalExceptionForError(nativeWriteInt);
        }
    }

    public final void writeLong(long j) {
        int nativeWriteLong = nativeWriteLong(this.mNativePtr, j);
        if (nativeWriteLong != 0) {
            nativeSignalExceptionForError(nativeWriteLong);
        }
    }

    public final void writeFloat(float f) {
        int nativeWriteFloat = nativeWriteFloat(this.mNativePtr, f);
        if (nativeWriteFloat != 0) {
            nativeSignalExceptionForError(nativeWriteFloat);
        }
    }

    public final void writeDouble(double d) {
        int nativeWriteDouble = nativeWriteDouble(this.mNativePtr, d);
        if (nativeWriteDouble != 0) {
            nativeSignalExceptionForError(nativeWriteDouble);
        }
    }

    public final void writeString(java.lang.String str) {
        writeString16(str);
    }

    public final void writeString8(java.lang.String str) {
        this.mReadWriteHelper.writeString8(this, str);
    }

    public final void writeString16(java.lang.String str) {
        this.mReadWriteHelper.writeString16(this, str);
    }

    public void writeStringNoHelper(java.lang.String str) {
        writeString16NoHelper(str);
    }

    public void writeString8NoHelper(java.lang.String str) {
        nativeWriteString8(this.mNativePtr, str);
    }

    public void writeString16NoHelper(java.lang.String str) {
        nativeWriteString16(this.mNativePtr, str);
    }

    public final void writeBoolean(boolean z) {
        writeInt(z ? 1 : 0);
    }

    public final void writeCharSequence(java.lang.CharSequence charSequence) {
        android.text.TextUtils.writeToParcel(charSequence, this, 0);
    }

    public final void writeStrongBinder(android.os.IBinder iBinder) {
        nativeWriteStrongBinder(this.mNativePtr, iBinder);
    }

    public final void writeStrongInterface(android.os.IInterface iInterface) {
        writeStrongBinder(iInterface == null ? null : iInterface.asBinder());
    }

    public final void writeFileDescriptor(java.io.FileDescriptor fileDescriptor) {
        nativeWriteFileDescriptor(this.mNativePtr, fileDescriptor);
    }

    public final void writeRawFileDescriptor(java.io.FileDescriptor fileDescriptor) {
        nativeWriteFileDescriptor(this.mNativePtr, fileDescriptor);
    }

    public final void writeRawFileDescriptorArray(java.io.FileDescriptor[] fileDescriptorArr) {
        if (fileDescriptorArr != null) {
            writeInt(fileDescriptorArr.length);
            for (java.io.FileDescriptor fileDescriptor : fileDescriptorArr) {
                writeRawFileDescriptor(fileDescriptor);
            }
            return;
        }
        writeInt(-1);
    }

    public final void writeByte(byte b) {
        writeInt(b);
    }

    public final void writeMap(java.util.Map map) {
        writeMapInternal(map);
    }

    void writeMapInternal(java.util.Map<java.lang.String, java.lang.Object> map) {
        if (map == null) {
            writeInt(-1);
            return;
        }
        java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.Object>> entrySet = map.entrySet();
        int size = entrySet.size();
        writeInt(size);
        for (java.util.Map.Entry<java.lang.String, java.lang.Object> entry : entrySet) {
            writeValue(entry.getKey());
            writeValue(entry.getValue());
            size--;
        }
        if (size != 0) {
            throw new android.os.BadParcelableException("Map size does not match number of entries!");
        }
    }

    void writeArrayMapInternal(android.util.ArrayMap<java.lang.String, java.lang.Object> arrayMap) {
        if (arrayMap == null) {
            writeInt(-1);
            return;
        }
        int size = arrayMap.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeString(arrayMap.keyAt(i));
            writeValue(arrayMap.valueAt(i));
        }
    }

    public void writeArrayMap(android.util.ArrayMap<java.lang.String, java.lang.Object> arrayMap) {
        writeArrayMapInternal(arrayMap);
    }

    public <T extends android.os.Parcelable> void writeTypedArrayMap(android.util.ArrayMap<java.lang.String, T> arrayMap, int i) {
        if (arrayMap == null) {
            writeInt(-1);
            return;
        }
        int size = arrayMap.size();
        writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            writeString(arrayMap.keyAt(i2));
            writeTypedObject(arrayMap.valueAt(i2), i);
        }
    }

    public void writeArraySet(android.util.ArraySet<? extends java.lang.Object> arraySet) {
        int size = arraySet != null ? arraySet.size() : -1;
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeValue(arraySet.valueAt(i));
        }
    }

    public final void writeBundle(android.os.Bundle bundle) {
        if (bundle == null) {
            writeInt(-1);
        } else {
            bundle.writeToParcel(this, 0);
        }
    }

    public final void writePersistableBundle(android.os.PersistableBundle persistableBundle) {
        if (persistableBundle == null) {
            writeInt(-1);
        } else {
            persistableBundle.writeToParcel(this, 0);
        }
    }

    public final void writeSize(android.util.Size size) {
        writeInt(size.getWidth());
        writeInt(size.getHeight());
    }

    public final void writeSizeF(android.util.SizeF sizeF) {
        writeFloat(sizeF.getWidth());
        writeFloat(sizeF.getHeight());
    }

    public final void writeList(java.util.List list) {
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeValue(list.get(i));
        }
    }

    public final void writeArray(java.lang.Object[] objArr) {
        if (objArr == null) {
            writeInt(-1);
            return;
        }
        writeInt(objArr.length);
        for (java.lang.Object obj : objArr) {
            writeValue(obj);
        }
    }

    public final <T> void writeSparseArray(android.util.SparseArray<T> sparseArray) {
        if (sparseArray == null) {
            writeInt(-1);
            return;
        }
        int size = sparseArray.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeInt(sparseArray.keyAt(i));
            writeValue(sparseArray.valueAt(i));
        }
    }

    public final void writeSparseBooleanArray(android.util.SparseBooleanArray sparseBooleanArray) {
        if (sparseBooleanArray == null) {
            writeInt(-1);
            return;
        }
        int size = sparseBooleanArray.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeInt(sparseBooleanArray.keyAt(i));
            writeByte(sparseBooleanArray.valueAt(i) ? (byte) 1 : (byte) 0);
        }
    }

    public final void writeSparseIntArray(android.util.SparseIntArray sparseIntArray) {
        if (sparseIntArray == null) {
            writeInt(-1);
            return;
        }
        int size = sparseIntArray.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeInt(sparseIntArray.keyAt(i));
            writeInt(sparseIntArray.valueAt(i));
        }
    }

    public final void writeBooleanArray(boolean[] zArr) {
        if (zArr != null) {
            writeInt(zArr.length);
            for (boolean z : zArr) {
                writeInt(z ? 1 : 0);
            }
            return;
        }
        writeInt(-1);
    }

    private static <T> int getItemTypeSize(java.lang.Class<T> cls) {
        java.lang.Class<?> componentType = cls.getComponentType();
        if (componentType == java.lang.Boolean.TYPE) {
            return 4;
        }
        if (componentType == java.lang.Byte.TYPE) {
            return 1;
        }
        if (componentType == java.lang.Character.TYPE) {
            return 2;
        }
        if (componentType == java.lang.Integer.TYPE) {
            return 4;
        }
        if (componentType == java.lang.Long.TYPE) {
            return 8;
        }
        if (componentType == java.lang.Float.TYPE) {
            return 4;
        }
        return componentType == java.lang.Double.TYPE ? 8 : 1;
    }

    private void ensureWithinMemoryLimit(int i, int... iArr) {
        int i2 = 1;
        try {
            for (int i3 : iArr) {
                i2 = java.lang.Math.multiplyExact(i2, i3);
            }
        } catch (java.lang.ArithmeticException e) {
            android.util.Log.e(TAG, "ArithmeticException occurred while multiplying dimensions " + e);
            libcore.util.SneakyThrow.sneakyThrow(new android.os.BadParcelableException("Estimated array length is too large. Array Dimensions:" + java.util.Arrays.toString(iArr)));
        }
        ensureWithinMemoryLimit(i, i2);
    }

    private void ensureWithinMemoryLimit(int i, int i2) {
        int i3;
        try {
            i3 = java.lang.Math.multiplyExact(i, i2);
        } catch (java.lang.ArithmeticException e) {
            android.util.Log.e(TAG, "ArithmeticException occurred while multiplying values " + i + " and " + i2 + " Exception: " + e);
            libcore.util.SneakyThrow.sneakyThrow(new android.os.BadParcelableException("Estimated allocation size is too large. typeSize: " + i + " length: " + i2));
            i3 = 0;
        }
        boolean isDirectlyHandlingTransaction = android.os.Binder.isDirectlyHandlingTransaction();
        if (isDirectlyHandlingTransaction && i3 > 1000000) {
            android.util.Log.e(TAG, "Trying to Allocate " + i3 + " memory, In Binder Transaction : " + isDirectlyHandlingTransaction);
            libcore.util.SneakyThrow.sneakyThrow(new android.os.BadParcelableException("Allocation of size " + i3 + " is above allowed limit of 1MB"));
        }
    }

    public final boolean[] createBooleanArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(4, readInt);
        if (readInt >= 0 && readInt <= (dataAvail() >> 2)) {
            boolean[] zArr = new boolean[readInt];
            for (int i = 0; i < readInt; i++) {
                zArr[i] = readInt() != 0;
            }
            return zArr;
        }
        return null;
    }

    public final void readBooleanArray(boolean[] zArr) {
        int readInt = readInt();
        if (readInt == zArr.length) {
            for (int i = 0; i < readInt; i++) {
                zArr[i] = readInt() != 0;
            }
            return;
        }
        throw new java.lang.RuntimeException("bad array lengths");
    }

    public void writeShortArray(short[] sArr) {
        if (sArr != null) {
            writeInt(sArr.length);
            for (short s : sArr) {
                writeInt(s);
            }
            return;
        }
        writeInt(-1);
    }

    public short[] createShortArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(2, readInt);
        if (readInt >= 0 && readInt <= (dataAvail() >> 2)) {
            short[] sArr = new short[readInt];
            for (int i = 0; i < readInt; i++) {
                sArr[i] = (short) readInt();
            }
            return sArr;
        }
        return null;
    }

    public void readShortArray(short[] sArr) {
        int readInt = readInt();
        if (readInt == sArr.length) {
            for (int i = 0; i < readInt; i++) {
                sArr[i] = (short) readInt();
            }
            return;
        }
        throw new java.lang.RuntimeException("bad array lengths");
    }

    public final void writeCharArray(char[] cArr) {
        if (cArr != null) {
            writeInt(cArr.length);
            for (char c : cArr) {
                writeInt(c);
            }
            return;
        }
        writeInt(-1);
    }

    public final char[] createCharArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(2, readInt);
        if (readInt >= 0 && readInt <= (dataAvail() >> 2)) {
            char[] cArr = new char[readInt];
            for (int i = 0; i < readInt; i++) {
                cArr[i] = (char) readInt();
            }
            return cArr;
        }
        return null;
    }

    public final void readCharArray(char[] cArr) {
        int readInt = readInt();
        if (readInt == cArr.length) {
            for (int i = 0; i < readInt; i++) {
                cArr[i] = (char) readInt();
            }
            return;
        }
        throw new java.lang.RuntimeException("bad array lengths");
    }

    public final void writeIntArray(int[] iArr) {
        if (iArr != null) {
            writeInt(iArr.length);
            for (int i : iArr) {
                writeInt(i);
            }
            return;
        }
        writeInt(-1);
    }

    public final int[] createIntArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(4, readInt);
        if (readInt >= 0 && readInt <= (dataAvail() >> 2)) {
            int[] iArr = new int[readInt];
            for (int i = 0; i < readInt; i++) {
                iArr[i] = readInt();
            }
            return iArr;
        }
        return null;
    }

    public final void readIntArray(int[] iArr) {
        int readInt = readInt();
        if (readInt == iArr.length) {
            for (int i = 0; i < readInt; i++) {
                iArr[i] = readInt();
            }
            return;
        }
        throw new java.lang.RuntimeException("bad array lengths");
    }

    public final void writeLongArray(long[] jArr) {
        if (jArr != null) {
            writeInt(jArr.length);
            for (long j : jArr) {
                writeLong(j);
            }
            return;
        }
        writeInt(-1);
    }

    public final long[] createLongArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(8, readInt);
        if (readInt >= 0 && readInt <= (dataAvail() >> 3)) {
            long[] jArr = new long[readInt];
            for (int i = 0; i < readInt; i++) {
                jArr[i] = readLong();
            }
            return jArr;
        }
        return null;
    }

    public final void readLongArray(long[] jArr) {
        int readInt = readInt();
        if (readInt == jArr.length) {
            for (int i = 0; i < readInt; i++) {
                jArr[i] = readLong();
            }
            return;
        }
        throw new java.lang.RuntimeException("bad array lengths");
    }

    public final void writeFloatArray(float[] fArr) {
        if (fArr != null) {
            writeInt(fArr.length);
            for (float f : fArr) {
                writeFloat(f);
            }
            return;
        }
        writeInt(-1);
    }

    public final float[] createFloatArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(4, readInt);
        if (readInt >= 0 && readInt <= (dataAvail() >> 2)) {
            float[] fArr = new float[readInt];
            for (int i = 0; i < readInt; i++) {
                fArr[i] = readFloat();
            }
            return fArr;
        }
        return null;
    }

    public final void readFloatArray(float[] fArr) {
        int readInt = readInt();
        if (readInt == fArr.length) {
            for (int i = 0; i < readInt; i++) {
                fArr[i] = readFloat();
            }
            return;
        }
        throw new java.lang.RuntimeException("bad array lengths");
    }

    public final void writeDoubleArray(double[] dArr) {
        if (dArr != null) {
            writeInt(dArr.length);
            for (double d : dArr) {
                writeDouble(d);
            }
            return;
        }
        writeInt(-1);
    }

    public final double[] createDoubleArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(8, readInt);
        if (readInt >= 0 && readInt <= (dataAvail() >> 3)) {
            double[] dArr = new double[readInt];
            for (int i = 0; i < readInt; i++) {
                dArr[i] = readDouble();
            }
            return dArr;
        }
        return null;
    }

    public final void readDoubleArray(double[] dArr) {
        int readInt = readInt();
        if (readInt == dArr.length) {
            for (int i = 0; i < readInt; i++) {
                dArr[i] = readDouble();
            }
            return;
        }
        throw new java.lang.RuntimeException("bad array lengths");
    }

    public final void writeStringArray(java.lang.String[] strArr) {
        writeString16Array(strArr);
    }

    public final java.lang.String[] createStringArray() {
        return createString16Array();
    }

    public final void readStringArray(java.lang.String[] strArr) {
        readString16Array(strArr);
    }

    public final void writeString8Array(java.lang.String[] strArr) {
        if (strArr != null) {
            writeInt(strArr.length);
            for (java.lang.String str : strArr) {
                writeString8(str);
            }
            return;
        }
        writeInt(-1);
    }

    public final java.lang.String[] createString8Array() {
        int readInt = readInt();
        ensureWithinMemoryLimit(1, readInt);
        if (readInt >= 0) {
            java.lang.String[] strArr = new java.lang.String[readInt];
            for (int i = 0; i < readInt; i++) {
                strArr[i] = readString8();
            }
            return strArr;
        }
        return null;
    }

    public final void readString8Array(java.lang.String[] strArr) {
        int readInt = readInt();
        if (readInt == strArr.length) {
            for (int i = 0; i < readInt; i++) {
                strArr[i] = readString8();
            }
            return;
        }
        throw new java.lang.RuntimeException("bad array lengths");
    }

    public final void writeString16Array(java.lang.String[] strArr) {
        if (strArr != null) {
            writeInt(strArr.length);
            for (java.lang.String str : strArr) {
                writeString16(str);
            }
            return;
        }
        writeInt(-1);
    }

    public final java.lang.String[] createString16Array() {
        int readInt = readInt();
        ensureWithinMemoryLimit(1, readInt);
        if (readInt >= 0) {
            java.lang.String[] strArr = new java.lang.String[readInt];
            for (int i = 0; i < readInt; i++) {
                strArr[i] = readString16();
            }
            return strArr;
        }
        return null;
    }

    public final void readString16Array(java.lang.String[] strArr) {
        int readInt = readInt();
        if (readInt == strArr.length) {
            for (int i = 0; i < readInt; i++) {
                strArr[i] = readString16();
            }
            return;
        }
        throw new java.lang.RuntimeException("bad array lengths");
    }

    public final void writeBinderArray(android.os.IBinder[] iBinderArr) {
        if (iBinderArr != null) {
            writeInt(iBinderArr.length);
            for (android.os.IBinder iBinder : iBinderArr) {
                writeStrongBinder(iBinder);
            }
            return;
        }
        writeInt(-1);
    }

    public final <T extends android.os.IInterface> void writeInterfaceArray(T[] tArr) {
        if (tArr != null) {
            writeInt(tArr.length);
            for (T t : tArr) {
                writeStrongInterface(t);
            }
            return;
        }
        writeInt(-1);
    }

    public final void writeCharSequenceArray(java.lang.CharSequence[] charSequenceArr) {
        if (charSequenceArr != null) {
            writeInt(charSequenceArr.length);
            for (java.lang.CharSequence charSequence : charSequenceArr) {
                writeCharSequence(charSequence);
            }
            return;
        }
        writeInt(-1);
    }

    public final void writeCharSequenceList(java.util.ArrayList<java.lang.CharSequence> arrayList) {
        if (arrayList != null) {
            int size = arrayList.size();
            writeInt(size);
            for (int i = 0; i < size; i++) {
                writeCharSequence(arrayList.get(i));
            }
            return;
        }
        writeInt(-1);
    }

    public final android.os.IBinder[] createBinderArray() {
        int readInt = readInt();
        ensureWithinMemoryLimit(1, readInt);
        if (readInt >= 0) {
            android.os.IBinder[] iBinderArr = new android.os.IBinder[readInt];
            for (int i = 0; i < readInt; i++) {
                iBinderArr[i] = readStrongBinder();
            }
            return iBinderArr;
        }
        return null;
    }

    public final void readBinderArray(android.os.IBinder[] iBinderArr) {
        int readInt = readInt();
        if (readInt == iBinderArr.length) {
            for (int i = 0; i < readInt; i++) {
                iBinderArr[i] = readStrongBinder();
            }
            return;
        }
        throw new java.lang.RuntimeException("bad array lengths");
    }

    public final <T extends android.os.IInterface> T[] createInterfaceArray(java.util.function.IntFunction<T[]> intFunction, java.util.function.Function<android.os.IBinder, T> function) {
        int readInt = readInt();
        ensureWithinMemoryLimit(1, readInt);
        if (readInt >= 0) {
            T[] apply = intFunction.apply(readInt);
            for (int i = 0; i < readInt; i++) {
                apply[i] = function.apply(readStrongBinder());
            }
            return apply;
        }
        return null;
    }

    public final <T extends android.os.IInterface> void readInterfaceArray(T[] tArr, java.util.function.Function<android.os.IBinder, T> function) {
        int readInt = readInt();
        if (readInt == tArr.length) {
            for (int i = 0; i < readInt; i++) {
                tArr[i] = function.apply(readStrongBinder());
            }
            return;
        }
        throw new android.os.BadParcelableException("bad array lengths");
    }

    public final <T extends android.os.Parcelable> void writeTypedList(java.util.List<T> list) {
        writeTypedList(list, 0);
    }

    public final <T extends android.os.Parcelable> void writeTypedSparseArray(android.util.SparseArray<T> sparseArray, int i) {
        if (sparseArray == null) {
            writeInt(-1);
            return;
        }
        int size = sparseArray.size();
        writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            writeInt(sparseArray.keyAt(i2));
            writeTypedObject(sparseArray.valueAt(i2), i);
        }
    }

    public <T extends android.os.Parcelable> void writeTypedList(java.util.List<T> list, int i) {
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            writeTypedObject(list.get(i2), i);
        }
    }

    public final void writeStringList(java.util.List<java.lang.String> list) {
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeString(list.get(i));
        }
    }

    public final void writeBinderList(java.util.List<android.os.IBinder> list) {
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeStrongBinder(list.get(i));
        }
    }

    public final <T extends android.os.IInterface> void writeInterfaceList(java.util.List<T> list) {
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        for (int i = 0; i < size; i++) {
            writeStrongInterface(list.get(i));
        }
    }

    public final <T extends android.os.Parcelable> void writeParcelableList(java.util.List<T> list, int i) {
        if (list == null) {
            writeInt(-1);
            return;
        }
        int size = list.size();
        writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            writeParcelable(list.get(i2), i);
        }
    }

    public final <T extends android.os.Parcelable> void writeTypedArray(T[] tArr, int i) {
        if (tArr != null) {
            writeInt(tArr.length);
            for (T t : tArr) {
                writeTypedObject(t, i);
            }
            return;
        }
        writeInt(-1);
    }

    public final <T extends android.os.Parcelable> void writeTypedObject(T t, int i) {
        if (t != null) {
            writeInt(1);
            t.writeToParcel(this, i);
        } else {
            writeInt(0);
        }
    }

    public <T> void writeFixedArray(T t, int i, int... iArr) {
        if (t == null) {
            writeInt(-1);
        } else {
            writeFixedArrayInternal(t, i, 0, iArr);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> void writeFixedArrayInternal(T t, int i, int i2, int[] iArr) {
        if (i2 >= iArr.length) {
            throw new android.os.BadParcelableException("Array has more dimensions than expected: " + iArr.length);
        }
        int i3 = iArr[i2];
        if (t == 0) {
            throw new android.os.BadParcelableException("Non-null array shouldn't have a null array.");
        }
        if (!t.getClass().isArray()) {
            throw new android.os.BadParcelableException("Not an array: " + t);
        }
        if (java.lang.reflect.Array.getLength(t) != i3) {
            throw new android.os.BadParcelableException("bad length: expected " + i3 + ", but got " + java.lang.reflect.Array.getLength(t));
        }
        java.lang.Class<?> componentType = t.getClass().getComponentType();
        if (!componentType.isArray() && i2 + 1 != iArr.length) {
            throw new android.os.BadParcelableException("Array has fewer dimensions than expected: " + iArr.length);
        }
        if (componentType == java.lang.Boolean.TYPE) {
            writeBooleanArray((boolean[]) t);
            return;
        }
        if (componentType == java.lang.Byte.TYPE) {
            writeByteArray((byte[]) t);
            return;
        }
        if (componentType == java.lang.Character.TYPE) {
            writeCharArray((char[]) t);
            return;
        }
        if (componentType == java.lang.Integer.TYPE) {
            writeIntArray((int[]) t);
            return;
        }
        if (componentType == java.lang.Long.TYPE) {
            writeLongArray((long[]) t);
            return;
        }
        if (componentType == java.lang.Float.TYPE) {
            writeFloatArray((float[]) t);
            return;
        }
        if (componentType == java.lang.Double.TYPE) {
            writeDoubleArray((double[]) t);
            return;
        }
        if (componentType == android.os.IBinder.class) {
            writeBinderArray((android.os.IBinder[]) t);
            return;
        }
        if (android.os.IInterface.class.isAssignableFrom(componentType)) {
            writeInterfaceArray((android.os.IInterface[]) t);
            return;
        }
        if (android.os.Parcelable.class.isAssignableFrom(componentType)) {
            writeTypedArray((android.os.Parcelable[]) t, i);
            return;
        }
        if (componentType.isArray()) {
            writeInt(i3);
            for (int i4 = 0; i4 < i3; i4++) {
                writeFixedArrayInternal(java.lang.reflect.Array.get(t, i4), i, i2 + 1, iArr);
            }
            return;
        }
        throw new android.os.BadParcelableException("unknown type for fixed-size array: " + componentType);
    }

    public final void writeValue(java.lang.Object obj) {
        if (obj instanceof android.os.Parcel.LazyValue) {
            ((android.os.Parcel.LazyValue) obj).writeToParcel(this);
            return;
        }
        int valueType = getValueType(obj);
        writeInt(valueType);
        if (isLengthPrefixed(valueType)) {
            int dataPosition = dataPosition();
            writeInt(-1);
            int dataPosition2 = dataPosition();
            writeValue(valueType, obj);
            int dataPosition3 = dataPosition();
            setDataPosition(dataPosition);
            writeInt(dataPosition3 - dataPosition2);
            setDataPosition(dataPosition3);
            return;
        }
        writeValue(valueType, obj);
    }

    public static int getValueType(java.lang.Object obj) {
        if (obj == null) {
            return -1;
        }
        if (obj instanceof java.lang.String) {
            return 0;
        }
        if (obj instanceof java.lang.Integer) {
            return 1;
        }
        if (obj instanceof java.util.Map) {
            return 2;
        }
        if (obj instanceof android.os.Bundle) {
            return 3;
        }
        if (obj instanceof android.os.PersistableBundle) {
            return 25;
        }
        if (obj instanceof android.util.SizeF) {
            return 27;
        }
        if (obj instanceof android.os.Parcelable) {
            return 4;
        }
        if (obj instanceof java.lang.Short) {
            return 5;
        }
        if (obj instanceof java.lang.Long) {
            return 6;
        }
        if (obj instanceof java.lang.Float) {
            return 7;
        }
        if (obj instanceof java.lang.Double) {
            return 8;
        }
        if (obj instanceof java.lang.Boolean) {
            return 9;
        }
        if (obj instanceof java.lang.CharSequence) {
            return 10;
        }
        if (obj instanceof java.util.List) {
            return 11;
        }
        if (obj instanceof android.util.SparseArray) {
            return 12;
        }
        if (obj instanceof boolean[]) {
            return 23;
        }
        if (obj instanceof byte[]) {
            return 13;
        }
        if (obj instanceof java.lang.String[]) {
            return 14;
        }
        if (obj instanceof java.lang.CharSequence[]) {
            return 24;
        }
        if (obj instanceof android.os.IBinder) {
            return 15;
        }
        if (obj instanceof android.os.Parcelable[]) {
            return 16;
        }
        if (obj instanceof int[]) {
            return 18;
        }
        if (obj instanceof long[]) {
            return 19;
        }
        if (obj instanceof java.lang.Byte) {
            return 20;
        }
        if (obj instanceof android.util.Size) {
            return 26;
        }
        if (obj instanceof double[]) {
            return 28;
        }
        if (obj instanceof java.lang.Character) {
            return 29;
        }
        if (obj instanceof short[]) {
            return 30;
        }
        if (obj instanceof char[]) {
            return 31;
        }
        if (obj instanceof float[]) {
            return 32;
        }
        java.lang.Class<?> cls = obj.getClass();
        if (cls.isArray() && cls.getComponentType() == java.lang.Object.class) {
            return 17;
        }
        if (obj instanceof java.io.Serializable) {
            return 21;
        }
        throw new java.lang.IllegalArgumentException("Parcel: unknown type for value " + obj);
    }

    public void writeValue(int i, java.lang.Object obj) {
        switch (i) {
            case -1:
                return;
            case 0:
                writeString((java.lang.String) obj);
                return;
            case 1:
                writeInt(((java.lang.Integer) obj).intValue());
                return;
            case 2:
                writeMap((java.util.Map) obj);
                return;
            case 3:
                writeBundle((android.os.Bundle) obj);
                return;
            case 4:
                writeParcelable((android.os.Parcelable) obj, 0);
                return;
            case 5:
                writeInt(((java.lang.Short) obj).intValue());
                return;
            case 6:
                writeLong(((java.lang.Long) obj).longValue());
                return;
            case 7:
                writeFloat(((java.lang.Float) obj).floatValue());
                return;
            case 8:
                writeDouble(((java.lang.Double) obj).doubleValue());
                return;
            case 9:
                writeInt(((java.lang.Boolean) obj).booleanValue() ? 1 : 0);
                return;
            case 10:
                writeCharSequence((java.lang.CharSequence) obj);
                return;
            case 11:
                writeList((java.util.List) obj);
                return;
            case 12:
                writeSparseArray((android.util.SparseArray) obj);
                return;
            case 13:
                writeByteArray((byte[]) obj);
                return;
            case 14:
                writeStringArray((java.lang.String[]) obj);
                return;
            case 15:
                writeStrongBinder((android.os.IBinder) obj);
                return;
            case 16:
                writeParcelableArray((android.os.Parcelable[]) obj, 0);
                return;
            case 17:
                writeArray((java.lang.Object[]) obj);
                return;
            case 18:
                writeIntArray((int[]) obj);
                return;
            case 19:
                writeLongArray((long[]) obj);
                return;
            case 20:
                writeInt(((java.lang.Byte) obj).byteValue());
                return;
            case 21:
                writeSerializable((java.io.Serializable) obj);
                return;
            case 22:
            default:
                throw new java.lang.RuntimeException("Parcel: unable to marshal value " + obj);
            case 23:
                writeBooleanArray((boolean[]) obj);
                return;
            case 24:
                writeCharSequenceArray((java.lang.CharSequence[]) obj);
                return;
            case 25:
                writePersistableBundle((android.os.PersistableBundle) obj);
                return;
            case 26:
                writeSize((android.util.Size) obj);
                return;
            case 27:
                writeSizeF((android.util.SizeF) obj);
                return;
            case 28:
                writeDoubleArray((double[]) obj);
                return;
            case 29:
                writeInt(((java.lang.Character) obj).charValue());
                return;
            case 30:
                writeShortArray((short[]) obj);
                return;
            case 31:
                writeCharArray((char[]) obj);
                return;
            case 32:
                writeFloatArray((float[]) obj);
                return;
        }
    }

    public final void writeParcelable(android.os.Parcelable parcelable, int i) {
        if (parcelable == null) {
            writeString(null);
        } else {
            writeParcelableCreator(parcelable);
            parcelable.writeToParcel(this, i);
        }
    }

    public final void writeParcelableCreator(android.os.Parcelable parcelable) {
        writeString(parcelable.getClass().getName());
    }

    private void ensureWrittenSquashableParcelables() {
        if (this.mWrittenSquashableParcelables != null) {
            return;
        }
        this.mWrittenSquashableParcelables = new android.util.ArrayMap<>();
    }

    public boolean allowSquashing() {
        boolean z = this.mAllowSquashing;
        this.mAllowSquashing = true;
        return z;
    }

    public void restoreAllowSquashing(boolean z) {
        this.mAllowSquashing = z;
        if (!this.mAllowSquashing) {
            this.mWrittenSquashableParcelables = null;
        }
    }

    private void resetSqaushingState() {
        if (this.mAllowSquashing) {
            android.util.Slog.wtf(TAG, "allowSquashing wasn't restored.");
        }
        this.mWrittenSquashableParcelables = null;
        this.mReadSquashableParcelables = null;
        this.mAllowSquashing = false;
    }

    private void ensureReadSquashableParcelables() {
        if (this.mReadSquashableParcelables != null) {
            return;
        }
        this.mReadSquashableParcelables = new android.util.SparseArray<>();
    }

    public boolean maybeWriteSquashed(android.os.Parcelable parcelable) {
        if (!this.mAllowSquashing) {
            writeInt(0);
            return false;
        }
        ensureWrittenSquashableParcelables();
        java.lang.Integer num = this.mWrittenSquashableParcelables.get(parcelable);
        if (num != null) {
            writeInt((dataPosition() - num.intValue()) + 4);
            return true;
        }
        writeInt(0);
        this.mWrittenSquashableParcelables.put(parcelable, java.lang.Integer.valueOf(dataPosition()));
        return false;
    }

    public <T extends android.os.Parcelable> T readSquashed(android.os.Parcel.SquashReadHelper<T> squashReadHelper) {
        int readInt = readInt();
        int dataPosition = dataPosition();
        if (readInt == 0) {
            T readRawParceled = squashReadHelper.readRawParceled(this);
            ensureReadSquashableParcelables();
            this.mReadSquashableParcelables.put(dataPosition, readRawParceled);
            return readRawParceled;
        }
        int i = dataPosition - readInt;
        T t = (T) this.mReadSquashableParcelables.get(i);
        if (t == null) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (int i2 = 0; i2 < this.mReadSquashableParcelables.size(); i2++) {
                sb.append(this.mReadSquashableParcelables.keyAt(i2)).append(' ');
            }
            android.util.Slog.wtfStack(TAG, "Map doesn't contain offset " + i + " : contains=" + sb.toString());
        }
        return t;
    }

    public final void writeSerializable(java.io.Serializable serializable) {
        if (serializable == null) {
            writeString(null);
            return;
        }
        java.lang.String name = serializable.getClass().getName();
        writeString(name);
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            java.io.ObjectOutputStream objectOutputStream = new java.io.ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.close();
            writeByteArray(byteArrayOutputStream.toByteArray());
        } catch (java.io.IOException e) {
            throw new android.os.BadParcelableException("Parcelable encountered IOException writing serializable object (name = " + name + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, e);
        }
    }

    public static void setStackTraceParceling(boolean z) {
        sParcelExceptionStackTrace = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void writeException(java.lang.Exception exc) {
        android.app.AppOpsManager.prefixParcelWithAppOpsIfNeeded(this);
        int exceptionCode = getExceptionCode(exc);
        writeInt(exceptionCode);
        android.os.StrictMode.clearGatheredViolations();
        if (exceptionCode == 0) {
            if (exc instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) exc);
            }
            throw new java.lang.RuntimeException(exc);
        }
        writeString(exc.getMessage());
        long elapsedRealtime = sParcelExceptionStackTrace ? android.os.SystemClock.elapsedRealtime() : 0L;
        if (sParcelExceptionStackTrace && elapsedRealtime - sLastWriteExceptionStackTrace > 1000) {
            sLastWriteExceptionStackTrace = elapsedRealtime;
            writeStackTrace(exc);
        } else {
            writeInt(0);
        }
        switch (exceptionCode) {
            case -9:
                int dataPosition = dataPosition();
                writeInt(0);
                writeParcelable((android.os.Parcelable) exc, 1);
                int dataPosition2 = dataPosition();
                setDataPosition(dataPosition);
                writeInt(dataPosition2 - dataPosition);
                setDataPosition(dataPosition2);
                return;
            case -8:
                writeInt(((android.os.ServiceSpecificException) exc).errorCode);
                return;
            default:
                return;
        }
    }

    public final void writeException$ravenwood(java.lang.Exception exc) {
        writeInt(getExceptionCode(exc));
        writeString(exc.getMessage());
        writeInt(0);
    }

    public static int getExceptionCode(java.lang.Throwable th) {
        if ((th instanceof android.os.Parcelable) && th.getClass().getClassLoader() == android.os.Parcelable.class.getClassLoader()) {
            return -9;
        }
        if (th instanceof java.lang.SecurityException) {
            return -1;
        }
        if (th instanceof android.os.BadParcelableException) {
            return -2;
        }
        if (th instanceof java.lang.IllegalArgumentException) {
            return -3;
        }
        if (th instanceof java.lang.NullPointerException) {
            return -4;
        }
        if (th instanceof java.lang.IllegalStateException) {
            return -5;
        }
        if (th instanceof android.os.NetworkOnMainThreadException) {
            return -6;
        }
        if (th instanceof java.lang.UnsupportedOperationException) {
            return -7;
        }
        if (!(th instanceof android.os.ServiceSpecificException)) {
            return 0;
        }
        return -8;
    }

    public void writeStackTrace(java.lang.Throwable th) {
        int dataPosition = dataPosition();
        writeInt(0);
        java.lang.StackTraceElement[] stackTrace = th.getStackTrace();
        int min = java.lang.Math.min(stackTrace.length, 5);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < min; i++) {
            sb.append("\tat ").append(stackTrace[i]).append('\n');
        }
        writeString(sb.toString());
        int dataPosition2 = dataPosition();
        setDataPosition(dataPosition);
        writeInt(dataPosition2 - dataPosition);
        setDataPosition(dataPosition2);
    }

    public final void writeNoException() {
        android.app.AppOpsManager.prefixParcelWithAppOpsIfNeeded(this);
        if (android.os.StrictMode.hasGatheredViolations()) {
            writeInt(-128);
            int dataPosition = dataPosition();
            writeInt(0);
            android.os.StrictMode.writeGatheredViolationsToParcel(this);
            int dataPosition2 = dataPosition();
            setDataPosition(dataPosition);
            writeInt(dataPosition2 - dataPosition);
            setDataPosition(dataPosition2);
            return;
        }
        writeInt(0);
    }

    public final void writeNoException$ravenwood() {
        writeInt(0);
    }

    public final void readException() {
        int readExceptionCode = readExceptionCode();
        if (readExceptionCode != 0) {
            readException(readExceptionCode, readString());
        }
    }

    public final int readExceptionCode() {
        int readInt = readInt();
        if (readInt == -127) {
            android.app.AppOpsManager.readAndLogNotedAppops(this);
            readInt = readInt();
        }
        if (readInt == -128) {
            if (readInt() == 0) {
                android.util.Log.e(TAG, "Unexpected zero-sized Parcel reply header.");
                return 0;
            }
            android.os.StrictMode.readAndHandleBinderCallViolations(this);
            return 0;
        }
        return readInt;
    }

    public final void readException(int i, java.lang.String str) {
        java.lang.String str2;
        if (readInt() <= 0) {
            str2 = null;
        } else {
            str2 = readString();
        }
        java.lang.Exception createException = createException(i, str);
        if (str2 != null) {
            android.util.ExceptionUtils.appendCause(createException, new android.os.RemoteException("Remote stack trace:\n" + str2, null, false, false));
        }
        libcore.util.SneakyThrow.sneakyThrow(createException);
    }

    private java.lang.Exception createException(int i, java.lang.String str) {
        java.lang.Exception createExceptionOrNull = createExceptionOrNull(i, str);
        if (createExceptionOrNull != null) {
            return createExceptionOrNull;
        }
        return new java.lang.RuntimeException("Unknown exception code: " + i + " msg " + str);
    }

    public java.lang.Exception createExceptionOrNull(int i, java.lang.String str) {
        switch (i) {
            case -9:
                if (readInt() > 0) {
                    return (java.lang.Exception) readParcelable(android.os.Parcelable.class.getClassLoader(), java.lang.Exception.class);
                }
                return new java.lang.RuntimeException(str + " [missing Parcelable]");
            case -8:
                return new android.os.ServiceSpecificException(readInt(), str);
            case -7:
                return new java.lang.UnsupportedOperationException(str);
            case -6:
                return new android.os.NetworkOnMainThreadException();
            case -5:
                return new java.lang.IllegalStateException(str);
            case -4:
                return new java.lang.NullPointerException(str);
            case -3:
                return new java.lang.IllegalArgumentException(str);
            case -2:
                return new android.os.BadParcelableException(str);
            case -1:
                return new java.lang.SecurityException(str);
            default:
                return null;
        }
    }

    public final int readInt() {
        return nativeReadInt(this.mNativePtr);
    }

    public final long readLong() {
        return nativeReadLong(this.mNativePtr);
    }

    public final float readFloat() {
        return nativeReadFloat(this.mNativePtr);
    }

    public final double readDouble() {
        return nativeReadDouble(this.mNativePtr);
    }

    public final java.lang.String readString() {
        return readString16();
    }

    public final java.lang.String readString8() {
        return this.mReadWriteHelper.readString8(this);
    }

    public final java.lang.String readString16() {
        return this.mReadWriteHelper.readString16(this);
    }

    public java.lang.String readStringNoHelper() {
        return readString16NoHelper();
    }

    public java.lang.String readString8NoHelper() {
        return nativeReadString8(this.mNativePtr);
    }

    public java.lang.String readString16NoHelper() {
        return nativeReadString16(this.mNativePtr);
    }

    public final boolean readBoolean() {
        return readInt() != 0;
    }

    public final java.lang.CharSequence readCharSequence() {
        return android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(this);
    }

    public final android.os.IBinder readStrongBinder() {
        android.os.IBinder nativeReadStrongBinder = nativeReadStrongBinder(this.mNativePtr);
        if (nativeReadStrongBinder != null && hasFlags(3)) {
            android.os.Binder.allowBlocking(nativeReadStrongBinder);
        }
        return nativeReadStrongBinder;
    }

    public final android.os.ParcelFileDescriptor readFileDescriptor() {
        java.io.FileDescriptor nativeReadFileDescriptor = nativeReadFileDescriptor(this.mNativePtr);
        if (nativeReadFileDescriptor != null) {
            return new android.os.ParcelFileDescriptor(nativeReadFileDescriptor);
        }
        return null;
    }

    public final java.io.FileDescriptor readRawFileDescriptor() {
        return nativeReadFileDescriptor(this.mNativePtr);
    }

    public final java.io.FileDescriptor[] createRawFileDescriptorArray() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        java.io.FileDescriptor[] fileDescriptorArr = new java.io.FileDescriptor[readInt];
        for (int i = 0; i < readInt; i++) {
            fileDescriptorArr[i] = readRawFileDescriptor();
        }
        return fileDescriptorArr;
    }

    public final void readRawFileDescriptorArray(java.io.FileDescriptor[] fileDescriptorArr) {
        int readInt = readInt();
        if (readInt == fileDescriptorArr.length) {
            for (int i = 0; i < readInt; i++) {
                fileDescriptorArr[i] = readRawFileDescriptor();
            }
            return;
        }
        throw new java.lang.RuntimeException("bad array lengths");
    }

    public final byte readByte() {
        return (byte) (readInt() & 255);
    }

    @java.lang.Deprecated
    public final void readMap(java.util.Map map, java.lang.ClassLoader classLoader) {
        readMapInternal(map, classLoader, null, null);
    }

    public <K, V> void readMap(java.util.Map<? super K, ? super V> map, java.lang.ClassLoader classLoader, java.lang.Class<K> cls, java.lang.Class<V> cls2) {
        java.util.Objects.requireNonNull(cls);
        java.util.Objects.requireNonNull(cls2);
        readMapInternal(map, classLoader, cls, cls2);
    }

    @java.lang.Deprecated
    public final void readList(java.util.List list, java.lang.ClassLoader classLoader) {
        readListInternal(list, readInt(), classLoader, null);
    }

    public <T> void readList(java.util.List<? super T> list, java.lang.ClassLoader classLoader, java.lang.Class<T> cls) {
        java.util.Objects.requireNonNull(cls);
        readListInternal(list, readInt(), classLoader, cls);
    }

    @java.lang.Deprecated
    public java.util.HashMap readHashMap(java.lang.ClassLoader classLoader) {
        return readHashMapInternal(classLoader, null, null);
    }

    public <K, V> java.util.HashMap<K, V> readHashMap(java.lang.ClassLoader classLoader, java.lang.Class<? extends K> cls, java.lang.Class<? extends V> cls2) {
        java.util.Objects.requireNonNull(cls);
        java.util.Objects.requireNonNull(cls2);
        return readHashMapInternal(classLoader, cls, cls2);
    }

    public final android.os.Bundle readBundle() {
        return readBundle(null);
    }

    public final android.os.Bundle readBundle(java.lang.ClassLoader classLoader) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        android.os.Bundle bundle = new android.os.Bundle(this, readInt);
        if (classLoader != null) {
            bundle.setClassLoader(classLoader);
        }
        return bundle;
    }

    public final android.os.PersistableBundle readPersistableBundle() {
        return readPersistableBundle(null);
    }

    public final android.os.PersistableBundle readPersistableBundle(java.lang.ClassLoader classLoader) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle(this, readInt);
        if (classLoader != null) {
            persistableBundle.setClassLoader(classLoader);
        }
        return persistableBundle;
    }

    public final android.util.Size readSize() {
        return new android.util.Size(readInt(), readInt());
    }

    public final android.util.SizeF readSizeF() {
        return new android.util.SizeF(readFloat(), readFloat());
    }

    public final byte[] createByteArray() {
        return nativeCreateByteArray(this.mNativePtr);
    }

    public final void readByteArray(byte[] bArr) {
        if (!nativeReadByteArray(this.mNativePtr, bArr, bArr != null ? bArr.length : 0)) {
            throw new java.lang.RuntimeException("bad array lengths");
        }
    }

    public final byte[] readBlob() {
        return nativeReadBlob(this.mNativePtr);
    }

    public final java.lang.String[] readStringArray() {
        return createString16Array();
    }

    public final java.lang.CharSequence[] readCharSequenceArray() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        java.lang.CharSequence[] charSequenceArr = new java.lang.CharSequence[readInt];
        for (int i = 0; i < readInt; i++) {
            charSequenceArr[i] = readCharSequence();
        }
        return charSequenceArr;
    }

    public final java.util.ArrayList<java.lang.CharSequence> readCharSequenceList() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        java.util.ArrayList<java.lang.CharSequence> arrayList = new java.util.ArrayList<>(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayList.add(readCharSequence());
        }
        return arrayList;
    }

    @java.lang.Deprecated
    public java.util.ArrayList readArrayList(java.lang.ClassLoader classLoader) {
        return readArrayListInternal(classLoader, null);
    }

    public <T> java.util.ArrayList<T> readArrayList(java.lang.ClassLoader classLoader, java.lang.Class<? extends T> cls) {
        java.util.Objects.requireNonNull(cls);
        return readArrayListInternal(classLoader, cls);
    }

    @java.lang.Deprecated
    public java.lang.Object[] readArray(java.lang.ClassLoader classLoader) {
        return readArrayInternal(classLoader, null);
    }

    public <T> T[] readArray(java.lang.ClassLoader classLoader, java.lang.Class<T> cls) {
        java.util.Objects.requireNonNull(cls);
        return (T[]) readArrayInternal(classLoader, cls);
    }

    @java.lang.Deprecated
    public <T> android.util.SparseArray<T> readSparseArray(java.lang.ClassLoader classLoader) {
        return readSparseArrayInternal(classLoader, null);
    }

    public <T> android.util.SparseArray<T> readSparseArray(java.lang.ClassLoader classLoader, java.lang.Class<? extends T> cls) {
        java.util.Objects.requireNonNull(cls);
        return readSparseArrayInternal(classLoader, cls);
    }

    public final android.util.SparseBooleanArray readSparseBooleanArray() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray(readInt);
        readSparseBooleanArrayInternal(sparseBooleanArray, readInt);
        return sparseBooleanArray;
    }

    public final android.util.SparseIntArray readSparseIntArray() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray(readInt);
        readSparseIntArrayInternal(sparseIntArray, readInt);
        return sparseIntArray;
    }

    public final <T> java.util.ArrayList<T> createTypedArrayList(android.os.Parcelable.Creator<T> creator) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        android.view.ViewGroup.ChildListForAutoFillOrContentCapture childListForAutoFillOrContentCapture = (java.util.ArrayList<T>) new java.util.ArrayList(readInt);
        while (readInt > 0) {
            childListForAutoFillOrContentCapture.add(readTypedObject(creator));
            readInt--;
        }
        return childListForAutoFillOrContentCapture;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> void readTypedList(java.util.List<T> list, android.os.Parcelable.Creator<T> creator) {
        int size = list.size();
        int readInt = readInt();
        int i = 0;
        while (i < size && i < readInt) {
            list.set(i, readTypedObject(creator));
            i++;
        }
        while (i < readInt) {
            list.add(readTypedObject(creator));
            i++;
        }
        while (i < size) {
            list.remove(readInt);
            i++;
        }
    }

    public final <T extends android.os.Parcelable> android.util.SparseArray<T> createTypedSparseArray(android.os.Parcelable.Creator<T> creator) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        android.net.wifi.WifiMigration.AnonymousClass1 anonymousClass1 = (android.util.SparseArray<T>) new android.util.SparseArray(readInt);
        for (int i = 0; i < readInt; i++) {
            anonymousClass1.append(readInt(), (android.os.Parcelable) readTypedObject(creator));
        }
        return anonymousClass1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T extends android.os.Parcelable> android.util.ArrayMap<java.lang.String, T> createTypedArrayMap(android.os.Parcelable.Creator<T> creator) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        android.util.ArrayMap<java.lang.String, T> arrayMap = (android.util.ArrayMap<java.lang.String, T>) new android.util.ArrayMap(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayMap.append(readString(), (android.os.Parcelable) readTypedObject(creator));
        }
        return arrayMap;
    }

    public final java.util.ArrayList<java.lang.String> createStringArrayList() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>(readInt);
        while (readInt > 0) {
            arrayList.add(readString());
            readInt--;
        }
        return arrayList;
    }

    public final java.util.ArrayList<android.os.IBinder> createBinderArrayList() {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        java.util.ArrayList<android.os.IBinder> arrayList = new java.util.ArrayList<>(readInt);
        while (readInt > 0) {
            arrayList.add(readStrongBinder());
            readInt--;
        }
        return arrayList;
    }

    public final <T extends android.os.IInterface> java.util.ArrayList<T> createInterfaceArrayList(java.util.function.Function<android.os.IBinder, T> function) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        java.util.ArrayList<T> arrayList = new java.util.ArrayList<>(readInt);
        while (readInt > 0) {
            arrayList.add(function.apply(readStrongBinder()));
            readInt--;
        }
        return arrayList;
    }

    public final void readStringList(java.util.List<java.lang.String> list) {
        int size = list.size();
        int readInt = readInt();
        int i = 0;
        while (i < size && i < readInt) {
            list.set(i, readString());
            i++;
        }
        while (i < readInt) {
            list.add(readString());
            i++;
        }
        while (i < size) {
            list.remove(readInt);
            i++;
        }
    }

    public final void readBinderList(java.util.List<android.os.IBinder> list) {
        int size = list.size();
        int readInt = readInt();
        int i = 0;
        while (i < size && i < readInt) {
            list.set(i, readStrongBinder());
            i++;
        }
        while (i < readInt) {
            list.add(readStrongBinder());
            i++;
        }
        while (i < size) {
            list.remove(readInt);
            i++;
        }
    }

    public final <T extends android.os.IInterface> void readInterfaceList(java.util.List<T> list, java.util.function.Function<android.os.IBinder, T> function) {
        int size = list.size();
        int readInt = readInt();
        int i = 0;
        while (i < size && i < readInt) {
            list.set(i, function.apply(readStrongBinder()));
            i++;
        }
        while (i < readInt) {
            list.add(function.apply(readStrongBinder()));
            i++;
        }
        while (i < size) {
            list.remove(readInt);
            i++;
        }
    }

    @java.lang.Deprecated
    public final <T extends android.os.Parcelable> java.util.List<T> readParcelableList(java.util.List<T> list, java.lang.ClassLoader classLoader) {
        return readParcelableListInternal(list, classLoader, null);
    }

    public <T> java.util.List<T> readParcelableList(java.util.List<T> list, java.lang.ClassLoader classLoader, java.lang.Class<? extends T> cls) {
        java.util.Objects.requireNonNull(list);
        java.util.Objects.requireNonNull(cls);
        return readParcelableListInternal(list, classLoader, cls);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> java.util.List<T> readParcelableListInternal(java.util.List<T> list, java.lang.ClassLoader classLoader, java.lang.Class<? extends T> cls) {
        int readInt = readInt();
        if (readInt == -1) {
            list.clear();
            return list;
        }
        int size = list.size();
        int i = 0;
        while (i < size && i < readInt) {
            list.set(i, readParcelableInternal(classLoader, cls));
            i++;
        }
        while (i < readInt) {
            list.add(readParcelableInternal(classLoader, cls));
            i++;
        }
        while (i < size) {
            list.remove(readInt);
            i++;
        }
        return list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T> T[] createTypedArray(android.os.Parcelable.Creator<T> creator) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        T[] newArray = creator.newArray(readInt);
        for (int i = 0; i < readInt; i++) {
            newArray[i] = readTypedObject(creator);
        }
        return newArray;
    }

    public final <T> void readTypedArray(T[] tArr, android.os.Parcelable.Creator<T> creator) {
        int readInt = readInt();
        if (readInt == tArr.length) {
            for (int i = 0; i < readInt; i++) {
                tArr[i] = readTypedObject(creator);
            }
            return;
        }
        throw new java.lang.RuntimeException("bad array lengths");
    }

    @java.lang.Deprecated
    public final <T> T[] readTypedArray(android.os.Parcelable.Creator<T> creator) {
        return (T[]) createTypedArray(creator);
    }

    public final <T> T readTypedObject(android.os.Parcelable.Creator<T> creator) {
        if (readInt() != 0) {
            return creator.createFromParcel(this);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void readFixedArray(T t) {
        java.lang.Class<?> componentType = t.getClass().getComponentType();
        if (componentType == java.lang.Boolean.TYPE) {
            readBooleanArray((boolean[]) t);
            return;
        }
        if (componentType == java.lang.Byte.TYPE) {
            readByteArray((byte[]) t);
            return;
        }
        if (componentType == java.lang.Character.TYPE) {
            readCharArray((char[]) t);
            return;
        }
        if (componentType == java.lang.Integer.TYPE) {
            readIntArray((int[]) t);
            return;
        }
        if (componentType == java.lang.Long.TYPE) {
            readLongArray((long[]) t);
            return;
        }
        if (componentType == java.lang.Float.TYPE) {
            readFloatArray((float[]) t);
            return;
        }
        if (componentType == java.lang.Double.TYPE) {
            readDoubleArray((double[]) t);
            return;
        }
        if (componentType == android.os.IBinder.class) {
            readBinderArray((android.os.IBinder[]) t);
            return;
        }
        if (componentType.isArray()) {
            int readInt = readInt();
            if (readInt != java.lang.reflect.Array.getLength(t)) {
                throw new android.os.BadParcelableException("Bad length: expected " + java.lang.reflect.Array.getLength(t) + ", but got " + readInt);
            }
            for (int i = 0; i < readInt; i++) {
                readFixedArray(java.lang.reflect.Array.get(t, i));
            }
            return;
        }
        throw new android.os.BadParcelableException("Unknown type for fixed-size array: " + componentType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T, S extends android.os.IInterface> void readFixedArray(T t, java.util.function.Function<android.os.IBinder, S> function) {
        java.lang.Class<?> componentType = t.getClass().getComponentType();
        if (android.os.IInterface.class.isAssignableFrom(componentType)) {
            readInterfaceArray((android.os.IInterface[]) t, function);
            return;
        }
        if (componentType.isArray()) {
            int readInt = readInt();
            if (readInt != java.lang.reflect.Array.getLength(t)) {
                throw new android.os.BadParcelableException("Bad length: expected " + java.lang.reflect.Array.getLength(t) + ", but got " + readInt);
            }
            for (int i = 0; i < readInt; i++) {
                readFixedArray((android.os.Parcel) java.lang.reflect.Array.get(t, i), (java.util.function.Function) function);
            }
            return;
        }
        throw new android.os.BadParcelableException("Unknown type for fixed-size array: " + componentType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T, S extends android.os.Parcelable> void readFixedArray(T t, android.os.Parcelable.Creator<S> creator) {
        java.lang.Class<?> componentType = t.getClass().getComponentType();
        if (android.os.Parcelable.class.isAssignableFrom(componentType)) {
            readTypedArray((android.os.Parcelable[]) t, creator);
            return;
        }
        if (componentType.isArray()) {
            int readInt = readInt();
            if (readInt != java.lang.reflect.Array.getLength(t)) {
                throw new android.os.BadParcelableException("Bad length: expected " + java.lang.reflect.Array.getLength(t) + ", but got " + readInt);
            }
            for (int i = 0; i < readInt; i++) {
                readFixedArray((android.os.Parcel) java.lang.reflect.Array.get(t, i), (android.os.Parcelable.Creator) creator);
            }
            return;
        }
        throw new android.os.BadParcelableException("Unknown type for fixed-size array: " + componentType);
    }

    private void ensureClassHasExpectedDimensions(java.lang.Class<?> cls, int i) {
        if (i <= 0) {
            throw new android.os.BadParcelableException("Fixed-size array should have dimensions.");
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (!cls.isArray()) {
                throw new android.os.BadParcelableException("Array has fewer dimensions than expected: " + i);
            }
            cls = cls.getComponentType();
        }
        if (cls.isArray()) {
            throw new android.os.BadParcelableException("Array has more dimensions than expected: " + i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T createFixedArray(java.lang.Class<T> cls, int... iArr) {
        T t;
        ensureClassHasExpectedDimensions(cls, iArr.length);
        java.lang.Class<?> componentType = cls.getComponentType();
        if (componentType == java.lang.Boolean.TYPE) {
            t = (T) createBooleanArray();
        } else if (componentType == java.lang.Byte.TYPE) {
            t = (T) createByteArray();
        } else if (componentType == java.lang.Character.TYPE) {
            t = (T) createCharArray();
        } else if (componentType == java.lang.Integer.TYPE) {
            t = (T) createIntArray();
        } else if (componentType == java.lang.Long.TYPE) {
            t = (T) createLongArray();
        } else if (componentType == java.lang.Float.TYPE) {
            t = (T) createFloatArray();
        } else if (componentType == java.lang.Double.TYPE) {
            t = (T) createDoubleArray();
        } else if (componentType == android.os.IBinder.class) {
            t = (T) createBinderArray();
        } else {
            if (componentType.isArray()) {
                int readInt = readInt();
                if (readInt < 0) {
                    return null;
                }
                if (readInt != iArr[0]) {
                    throw new android.os.BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + readInt);
                }
                java.lang.Class<?> componentType2 = componentType.getComponentType();
                while (componentType2.isArray()) {
                    componentType2 = componentType2.getComponentType();
                }
                ensureWithinMemoryLimit(getItemTypeSize(componentType2), iArr);
                T t2 = (T) java.lang.reflect.Array.newInstance(componentType2, iArr);
                for (int i = 0; i < readInt; i++) {
                    readFixedArray(java.lang.reflect.Array.get(t2, i));
                }
                return t2;
            }
            throw new android.os.BadParcelableException("Unknown type for fixed-size array: " + componentType);
        }
        if (t == null || java.lang.reflect.Array.getLength(t) == iArr[0]) {
            return t;
        }
        throw new android.os.BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + java.lang.reflect.Array.getLength(t));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T, S extends android.os.IInterface> T createFixedArray(java.lang.Class<T> cls, java.util.function.Function<android.os.IBinder, S> function, int... iArr) {
        ensureClassHasExpectedDimensions(cls, iArr.length);
        final java.lang.Class<?> componentType = cls.getComponentType();
        if (android.os.IInterface.class.isAssignableFrom(componentType)) {
            T t = (T) createInterfaceArray(new java.util.function.IntFunction() { // from class: android.os.Parcel$$ExternalSyntheticLambda0
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i) {
                    return android.os.Parcel.lambda$createFixedArray$0(componentType, i);
                }
            }, function);
            if (t != null && java.lang.reflect.Array.getLength(t) != iArr[0]) {
                throw new android.os.BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + java.lang.reflect.Array.getLength(t));
            }
            return t;
        }
        if (componentType.isArray()) {
            int readInt = readInt();
            if (readInt < 0) {
                return null;
            }
            if (readInt != iArr[0]) {
                throw new android.os.BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + readInt);
            }
            java.lang.Class<?> componentType2 = componentType.getComponentType();
            while (componentType2.isArray()) {
                componentType2 = componentType2.getComponentType();
            }
            ensureWithinMemoryLimit(getItemTypeSize(componentType2), iArr);
            T t2 = (T) java.lang.reflect.Array.newInstance(componentType2, iArr);
            for (int i = 0; i < readInt; i++) {
                readFixedArray((android.os.Parcel) java.lang.reflect.Array.get(t2, i), (java.util.function.Function) function);
            }
            return t2;
        }
        throw new android.os.BadParcelableException("Unknown type for fixed-size array: " + componentType);
    }

    static /* synthetic */ android.os.IInterface[] lambda$createFixedArray$0(java.lang.Class cls, int i) {
        return (android.os.IInterface[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T, S extends android.os.Parcelable> T createFixedArray(java.lang.Class<T> cls, android.os.Parcelable.Creator<S> creator, int... iArr) {
        ensureClassHasExpectedDimensions(cls, iArr.length);
        java.lang.Class<?> componentType = cls.getComponentType();
        if (android.os.Parcelable.class.isAssignableFrom(componentType)) {
            T t = (T) createTypedArray(creator);
            if (t != null && java.lang.reflect.Array.getLength(t) != iArr[0]) {
                throw new android.os.BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + java.lang.reflect.Array.getLength(t));
            }
            return t;
        }
        if (componentType.isArray()) {
            int readInt = readInt();
            if (readInt < 0) {
                return null;
            }
            if (readInt != iArr[0]) {
                throw new android.os.BadParcelableException("Bad length: expected " + iArr[0] + ", but got " + readInt);
            }
            java.lang.Class<?> componentType2 = componentType.getComponentType();
            while (componentType2.isArray()) {
                componentType2 = componentType2.getComponentType();
            }
            ensureWithinMemoryLimit(getItemTypeSize(componentType2), iArr);
            T t2 = (T) java.lang.reflect.Array.newInstance(componentType2, iArr);
            for (int i = 0; i < readInt; i++) {
                readFixedArray((android.os.Parcel) java.lang.reflect.Array.get(t2, i), (android.os.Parcelable.Creator) creator);
            }
            return t2;
        }
        throw new android.os.BadParcelableException("Unknown type for fixed-size array: " + componentType);
    }

    public final <T extends android.os.Parcelable> void writeParcelableArray(T[] tArr, int i) {
        if (tArr != null) {
            writeInt(tArr.length);
            for (T t : tArr) {
                writeParcelable(t, i);
            }
            return;
        }
        writeInt(-1);
    }

    public final java.lang.Object readValue(java.lang.ClassLoader classLoader) {
        return readValue(classLoader, (java.lang.Class) null, new java.lang.Class[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> T readValue(java.lang.ClassLoader classLoader, java.lang.Class<T> cls, java.lang.Class<?>... clsArr) {
        int readInt = readInt();
        if (isLengthPrefixed(readInt)) {
            int readInt2 = readInt();
            int dataPosition = dataPosition();
            T t = (T) readValue(readInt, classLoader, cls, clsArr);
            int dataPosition2 = dataPosition() - dataPosition;
            if (dataPosition2 != readInt2) {
                android.util.Slog.wtfStack(TAG, "Unparcelling of " + t + " of type " + valueTypeToString(readInt) + "  consumed " + dataPosition2 + " bytes, but " + readInt2 + " expected.");
                return t;
            }
            return t;
        }
        return (T) readValue(readInt, classLoader, cls, clsArr);
    }

    public java.lang.Object readLazyValue(java.lang.ClassLoader classLoader) {
        int dataPosition = dataPosition();
        int readInt = readInt();
        if (isLengthPrefixed(readInt)) {
            int readInt2 = readInt();
            if (readInt2 < 0) {
                return null;
            }
            int addOrThrow = android.util.MathUtils.addOrThrow(dataPosition(), readInt2);
            setDataPosition(addOrThrow);
            return new android.os.Parcel.LazyValue(this, dataPosition, addOrThrow - dataPosition, readInt, classLoader);
        }
        return readValue(readInt, classLoader, (java.lang.Class) null);
    }

    private static final class LazyValue implements java.util.function.BiFunction<java.lang.Class<?>, java.lang.Class<?>[], java.lang.Object> {
        private final int mLength;
        private final java.lang.ClassLoader mLoader;
        private java.lang.Object mObject;
        private final int mPosition;
        private volatile android.os.Parcel mSource;
        private final int mType;

        LazyValue(android.os.Parcel parcel, int i, int i2, int i3, java.lang.ClassLoader classLoader) {
            this.mSource = (android.os.Parcel) java.util.Objects.requireNonNull(parcel);
            this.mPosition = i;
            this.mLength = i2;
            this.mType = i3;
            this.mLoader = classLoader;
        }

        @Override // java.util.function.BiFunction
        public java.lang.Object apply(java.lang.Class<?> cls, java.lang.Class<?>[] clsArr) {
            android.os.Parcel parcel = this.mSource;
            if (parcel != null) {
                synchronized (parcel) {
                    if (this.mSource != null) {
                        int dataPosition = parcel.dataPosition();
                        try {
                            parcel.setDataPosition(this.mPosition);
                            this.mObject = parcel.readValue(this.mLoader, cls, clsArr);
                            parcel.setDataPosition(dataPosition);
                            this.mSource = null;
                        } catch (java.lang.Throwable th) {
                            parcel.setDataPosition(dataPosition);
                            throw th;
                        }
                    }
                }
            }
            return this.mObject;
        }

        public void writeToParcel(android.os.Parcel parcel) {
            android.os.Parcel parcel2 = this.mSource;
            if (parcel2 != null) {
                synchronized (parcel2) {
                    if (this.mSource != null) {
                        parcel.appendFrom(parcel2, this.mPosition, this.mLength);
                        return;
                    }
                }
            }
            parcel.writeValue(this.mObject);
        }

        public boolean hasFileDescriptors() {
            android.os.Parcel parcel = this.mSource;
            if (parcel != null) {
                synchronized (parcel) {
                    if (this.mSource != null) {
                        return parcel.hasFileDescriptors(this.mPosition, this.mLength);
                    }
                }
            }
            return android.os.Parcel.hasFileDescriptors(this.mObject);
        }

        public java.lang.String toString() {
            if (this.mSource != null) {
                return "Supplier{" + android.os.Parcel.valueTypeToString(this.mType) + "@" + this.mPosition + "+" + this.mLength + '}';
            }
            return "Supplier{" + this.mObject + "}";
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.os.Parcel.LazyValue)) {
                return false;
            }
            android.os.Parcel.LazyValue lazyValue = (android.os.Parcel.LazyValue) obj;
            android.os.Parcel parcel = this.mSource;
            android.os.Parcel parcel2 = lazyValue.mSource;
            if ((parcel == null) != (parcel2 == null)) {
                return false;
            }
            if (parcel == null) {
                return java.util.Objects.equals(this.mObject, lazyValue.mObject);
            }
            if (java.util.Objects.equals(this.mLoader, lazyValue.mLoader) && this.mType == lazyValue.mType && this.mLength == lazyValue.mLength) {
                return android.os.Parcel.compareData(parcel, this.mPosition, parcel2, lazyValue.mPosition, this.mLength);
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mSource == null), this.mObject, this.mLoader, java.lang.Integer.valueOf(this.mType), java.lang.Integer.valueOf(this.mLength));
        }
    }

    private <T> T readValue(int i, java.lang.ClassLoader classLoader, java.lang.Class<T> cls) {
        return (T) readValue(i, classLoader, cls, null);
    }

    private <T> T readValue(int i, java.lang.ClassLoader classLoader, java.lang.Class<T> cls, java.lang.Class<?>... clsArr) {
        T t;
        switch (i) {
            case -1:
                t = null;
                break;
            case 0:
                t = (T) readString();
                break;
            case 1:
                t = (T) java.lang.Integer.valueOf(readInt());
                break;
            case 2:
                checkTypeToUnparcel(cls, java.util.HashMap.class);
                java.lang.Class cls2 = (java.lang.Class) com.android.internal.util.ArrayUtils.getOrNull(clsArr, 0);
                java.lang.Class cls3 = (java.lang.Class) com.android.internal.util.ArrayUtils.getOrNull(clsArr, 1);
                com.android.internal.util.Preconditions.checkArgument((cls2 == null) == (cls3 == null));
                t = (T) readHashMapInternal(classLoader, cls2, cls3);
                break;
            case 3:
                t = (T) readBundle(classLoader);
                break;
            case 4:
                t = (T) readParcelableInternal(classLoader, cls);
                break;
            case 5:
                t = (T) java.lang.Short.valueOf((short) readInt());
                break;
            case 6:
                t = (T) java.lang.Long.valueOf(readLong());
                break;
            case 7:
                t = (T) java.lang.Float.valueOf(readFloat());
                break;
            case 8:
                t = (T) java.lang.Double.valueOf(readDouble());
                break;
            case 9:
                t = (T) java.lang.Boolean.valueOf(readInt() == 1);
                break;
            case 10:
                t = (T) readCharSequence();
                break;
            case 11:
                checkTypeToUnparcel(cls, java.util.ArrayList.class);
                t = (T) readArrayListInternal(classLoader, (java.lang.Class) com.android.internal.util.ArrayUtils.getOrNull(clsArr, 0));
                break;
            case 12:
                checkTypeToUnparcel(cls, android.util.SparseArray.class);
                t = (T) readSparseArrayInternal(classLoader, (java.lang.Class) com.android.internal.util.ArrayUtils.getOrNull(clsArr, 0));
                break;
            case 13:
                t = (T) createByteArray();
                break;
            case 14:
                t = (T) readStringArray();
                break;
            case 15:
                t = (T) readStrongBinder();
                break;
            case 16:
                java.lang.Class<android.os.Parcelable> cls4 = (java.lang.Class) com.android.internal.util.ArrayUtils.getOrNull(clsArr, 0);
                checkArrayTypeToUnparcel(cls, cls4 != null ? cls4 : android.os.Parcelable.class);
                t = (T) readParcelableArrayInternal(classLoader, cls4);
                break;
            case 17:
                java.lang.Class<?> cls5 = (java.lang.Class) com.android.internal.util.ArrayUtils.getOrNull(clsArr, 0);
                checkArrayTypeToUnparcel(cls, cls5 != null ? cls5 : java.lang.Object.class);
                t = (T) readArrayInternal(classLoader, cls5);
                break;
            case 18:
                t = (T) createIntArray();
                break;
            case 19:
                t = (T) createLongArray();
                break;
            case 20:
                t = (T) java.lang.Byte.valueOf(readByte());
                break;
            case 21:
                t = (T) readSerializableInternal(classLoader, cls);
                break;
            case 22:
                t = (T) readSparseBooleanArray();
                break;
            case 23:
                t = (T) createBooleanArray();
                break;
            case 24:
                t = (T) readCharSequenceArray();
                break;
            case 25:
                t = (T) readPersistableBundle(classLoader);
                break;
            case 26:
                t = (T) readSize();
                break;
            case 27:
                t = (T) readSizeF();
                break;
            case 28:
                t = (T) createDoubleArray();
                break;
            case 29:
                t = (T) java.lang.Character.valueOf((char) readInt());
                break;
            case 30:
                t = (T) createShortArray();
                break;
            case 31:
                t = (T) createCharArray();
                break;
            case 32:
                t = (T) createFloatArray();
                break;
            default:
                throw new android.os.BadParcelableException("Parcel " + this + ": Unmarshalling unknown type code " + i + " at offset " + (dataPosition() - 4));
        }
        if (t == null || cls == null || cls.isInstance(t)) {
            return t;
        }
        throw new android.os.BadTypeParcelableException("Unparcelled object " + t + " is not an instance of required class " + cls.getName() + " provided in the parameter");
    }

    private boolean isLengthPrefixed(int i) {
        switch (i) {
            case 2:
            case 4:
            case 11:
            case 12:
            case 16:
            case 17:
            case 21:
                return true;
            default:
                return false;
        }
    }

    private void checkArrayTypeToUnparcel(java.lang.Class<?> cls, java.lang.Class<?> cls2) {
        if (cls != null) {
            java.lang.Class<?> componentType = cls.getComponentType();
            if (componentType == null) {
                throw new android.os.BadTypeParcelableException("About to unparcel an array but type " + cls.getCanonicalName() + " required by caller is not an array.");
            }
            checkTypeToUnparcel(componentType, cls2);
        }
    }

    private void checkTypeToUnparcel(java.lang.Class<?> cls, java.lang.Class<?> cls2) {
        if (cls != null && !cls.isAssignableFrom(cls2)) {
            throw new android.os.BadTypeParcelableException("About to unparcel a " + cls2.getCanonicalName() + ", which is not a subtype of type " + cls.getCanonicalName() + " required by caller.");
        }
    }

    @java.lang.Deprecated
    public final <T extends android.os.Parcelable> T readParcelable(java.lang.ClassLoader classLoader) {
        return (T) readParcelableInternal(classLoader, null);
    }

    public <T> T readParcelable(java.lang.ClassLoader classLoader, java.lang.Class<T> cls) {
        java.util.Objects.requireNonNull(cls);
        return (T) readParcelableInternal(classLoader, cls);
    }

    private <T> T readParcelableInternal(java.lang.ClassLoader classLoader, java.lang.Class<T> cls) {
        android.os.Parcelable.Creator<T> readParcelableCreatorInternal = readParcelableCreatorInternal(classLoader, cls);
        if (readParcelableCreatorInternal == null) {
            return null;
        }
        if (readParcelableCreatorInternal instanceof android.os.Parcelable.ClassLoaderCreator) {
            return (T) ((android.os.Parcelable.ClassLoaderCreator) readParcelableCreatorInternal).createFromParcel(this, classLoader);
        }
        return readParcelableCreatorInternal.createFromParcel(this);
    }

    public final <T extends android.os.Parcelable> T readCreator(android.os.Parcelable.Creator<?> creator, java.lang.ClassLoader classLoader) {
        if (creator instanceof android.os.Parcelable.ClassLoaderCreator) {
            return (T) ((android.os.Parcelable.ClassLoaderCreator) creator).createFromParcel(this, classLoader);
        }
        return (T) creator.createFromParcel(this);
    }

    @java.lang.Deprecated
    public final android.os.Parcelable.Creator<?> readParcelableCreator(java.lang.ClassLoader classLoader) {
        return readParcelableCreatorInternal(classLoader, null);
    }

    public <T> android.os.Parcelable.Creator<T> readParcelableCreator(java.lang.ClassLoader classLoader, java.lang.Class<T> cls) {
        java.util.Objects.requireNonNull(cls);
        return readParcelableCreatorInternal(classLoader, cls);
    }

    private <T> android.os.Parcelable.Creator<T> readParcelableCreatorInternal(java.lang.ClassLoader classLoader, java.lang.Class<T> cls) {
        android.util.Pair<android.os.Parcelable.Creator<?>, java.lang.Class<?>> pair;
        java.lang.ClassLoader classLoader2;
        java.lang.String readString = readString();
        if (readString == null) {
            return null;
        }
        synchronized (sPairedCreators) {
            java.util.HashMap<java.lang.String, android.util.Pair<android.os.Parcelable.Creator<?>, java.lang.Class<?>>> hashMap = sPairedCreators.get(classLoader);
            if (hashMap == null) {
                sPairedCreators.put(classLoader, new java.util.HashMap<>());
                mCreators.put(classLoader, new java.util.HashMap<>());
                pair = null;
            } else {
                pair = hashMap.get(readString);
            }
        }
        if (pair != null) {
            android.os.Parcelable.Creator<T> creator = (android.os.Parcelable.Creator) pair.first;
            java.lang.Class<?> cls2 = pair.second;
            if (cls != null && !cls.isAssignableFrom(cls2)) {
                throw new android.os.BadTypeParcelableException("Parcelable creator " + readString + " is not a subclass of required class " + cls.getName() + " provided in the parameter");
            }
            return creator;
        }
        if (classLoader != null) {
            classLoader2 = classLoader;
        } else {
            try {
                classLoader2 = getClass().getClassLoader();
            } catch (java.lang.ClassNotFoundException e) {
                android.util.Log.e(TAG, "Class not found when unmarshalling: " + readString, e);
                throw new android.os.BadParcelableException("ClassNotFoundException when unmarshalling: " + readString, e);
            } catch (java.lang.IllegalAccessException e2) {
                android.util.Log.e(TAG, "Illegal access when unmarshalling: " + readString, e2);
                throw new android.os.BadParcelableException("IllegalAccessException when unmarshalling: " + readString, e2);
            } catch (java.lang.NoSuchFieldException e3) {
                throw new android.os.BadParcelableException("Parcelable protocol requires a Parcelable.Creator object called CREATOR on class " + readString, e3);
            }
        }
        java.lang.Class<?> cls3 = java.lang.Class.forName(readString, false, classLoader2);
        if (!android.os.Parcelable.class.isAssignableFrom(cls3)) {
            throw new android.os.BadParcelableException("Parcelable protocol requires subclassing from Parcelable on class " + readString);
        }
        if (cls != null && !cls.isAssignableFrom(cls3)) {
            throw new android.os.BadTypeParcelableException("Parcelable creator " + readString + " is not a subclass of required class " + cls.getName() + " provided in the parameter");
        }
        java.lang.reflect.Field field = cls3.getField("CREATOR");
        if ((field.getModifiers() & 8) == 0) {
            throw new android.os.BadParcelableException("Parcelable protocol requires the CREATOR object to be static on class " + readString);
        }
        if (!android.os.Parcelable.Creator.class.isAssignableFrom(field.getType())) {
            throw new android.os.BadParcelableException("Parcelable protocol requires a Parcelable.Creator object called CREATOR on class " + readString);
        }
        android.os.Parcelable.Creator<T> creator2 = (android.os.Parcelable.Creator) field.get(null);
        if (creator2 == null) {
            throw new android.os.BadParcelableException("Parcelable protocol requires a non-null Parcelable.Creator object called CREATOR on class " + readString);
        }
        synchronized (sPairedCreators) {
            sPairedCreators.get(classLoader).put(readString, android.util.Pair.create(creator2, cls3));
            mCreators.get(classLoader).put(readString, creator2);
        }
        return creator2;
    }

    @java.lang.Deprecated
    public android.os.Parcelable[] readParcelableArray(java.lang.ClassLoader classLoader) {
        return (android.os.Parcelable[]) readParcelableArrayInternal(classLoader, null);
    }

    public <T> T[] readParcelableArray(java.lang.ClassLoader classLoader, java.lang.Class<T> cls) {
        return (T[]) readParcelableArrayInternal(classLoader, (java.lang.Class) java.util.Objects.requireNonNull(cls));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> T[] readParcelableArrayInternal(java.lang.ClassLoader classLoader, java.lang.Class<T> cls) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        ensureWithinMemoryLimit(1, readInt);
        T[] tArr = (T[]) ((java.lang.Object[]) (cls == null ? new android.os.Parcelable[readInt] : java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, readInt)));
        for (int i = 0; i < readInt; i++) {
            tArr[i] = readParcelableInternal(classLoader, cls);
        }
        return tArr;
    }

    @java.lang.Deprecated
    public java.io.Serializable readSerializable() {
        return (java.io.Serializable) readSerializableInternal(null, null);
    }

    public <T> T readSerializable(java.lang.ClassLoader classLoader, java.lang.Class<T> cls) {
        java.util.Objects.requireNonNull(cls);
        if (classLoader == null) {
            classLoader = getClass().getClassLoader();
        }
        return (T) readSerializableInternal(classLoader, cls);
    }

    private <T> T readSerializableInternal(final java.lang.ClassLoader classLoader, java.lang.Class<T> cls) {
        java.lang.String readString = readString();
        if (readString == null) {
            return null;
        }
        if (cls != null && classLoader != null) {
            try {
                java.lang.Class<?> cls2 = java.lang.Class.forName(readString, false, classLoader);
                if (!cls.isAssignableFrom(cls2)) {
                    throw new android.os.BadTypeParcelableException("Serializable object " + cls2.getName() + " is not a subclass of required class " + cls.getName() + " provided in the parameter");
                }
            } catch (java.io.IOException e) {
                throw new android.os.BadParcelableException("Parcelable encountered IOException reading a Serializable object (name = " + readString + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, e);
            } catch (java.lang.ClassNotFoundException e2) {
                throw new android.os.BadParcelableException("Parcelable encountered ClassNotFoundException reading a Serializable object (name = " + readString + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, e2);
            }
        }
        T t = (T) new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(createByteArray())) { // from class: android.os.Parcel.2
            @Override // java.io.ObjectInputStream
            protected java.lang.Class<?> resolveClass(java.io.ObjectStreamClass objectStreamClass) throws java.io.IOException, java.lang.ClassNotFoundException {
                if (classLoader != null) {
                    return (java.lang.Class) java.util.Objects.requireNonNull(java.lang.Class.forName(objectStreamClass.getName(), false, classLoader));
                }
                return super.resolveClass(objectStreamClass);
            }
        }.readObject();
        if (cls != null && classLoader == null && !cls.isAssignableFrom(t.getClass())) {
            throw new android.os.BadTypeParcelableException("Serializable object " + t.getClass().getName() + " is not a subclass of required class " + cls.getName() + " provided in the parameter");
        }
        return t;
    }

    protected static final android.os.Parcel obtain(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    protected static final android.os.Parcel obtain(long j) {
        android.os.Parcel parcel;
        synchronized (sPoolSync) {
            parcel = null;
            if (sHolderPool != null) {
                android.os.Parcel parcel2 = sHolderPool;
                sHolderPool = parcel2.mPoolNext;
                parcel2.mPoolNext = null;
                sHolderPoolSize--;
                parcel = parcel2;
            }
        }
        if (parcel == null) {
            return new android.os.Parcel(j);
        }
        parcel.mRecycled = false;
        parcel.init(j);
        return parcel;
    }

    private Parcel(long j) {
        init(j);
    }

    private void init(long j) {
        if (j != 0) {
            this.mNativePtr = j;
            this.mOwnsNativeParcelObject = false;
        } else {
            this.mNativePtr = nativeCreate();
            this.mOwnsNativeParcelObject = true;
        }
    }

    private void freeBuffer() {
        this.mFlags = 0;
        resetSqaushingState();
        if (this.mOwnsNativeParcelObject) {
            nativeFreeBuffer(this.mNativePtr);
        }
        this.mReadWriteHelper = android.os.Parcel.ReadWriteHelper.DEFAULT;
    }

    private void destroy() {
        resetSqaushingState();
        if (this.mNativePtr != 0) {
            if (this.mOwnsNativeParcelObject) {
                nativeDestroy(this.mNativePtr);
            }
            this.mNativePtr = 0L;
        }
    }

    protected void finalize() throws java.lang.Throwable {
        destroy();
    }

    void readMapInternal(java.util.Map map, int i, java.lang.ClassLoader classLoader) {
        readMapInternal(map, i, classLoader, null, null);
    }

    private <K, V> java.util.HashMap<K, V> readHashMapInternal(java.lang.ClassLoader classLoader, java.lang.Class<? extends K> cls, java.lang.Class<? extends V> cls2) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        java.util.HashMap<K, V> hashMap = new java.util.HashMap<>(readInt);
        readMapInternal(hashMap, readInt, classLoader, cls, cls2);
        return hashMap;
    }

    private <K, V> void readMapInternal(java.util.Map<? super K, ? super V> map, java.lang.ClassLoader classLoader, java.lang.Class<K> cls, java.lang.Class<V> cls2) {
        readMapInternal(map, readInt(), classLoader, cls, cls2);
    }

    private <K, V> void readMapInternal(java.util.Map<? super K, ? super V> map, int i, java.lang.ClassLoader classLoader, java.lang.Class<K> cls, java.lang.Class<V> cls2) {
        while (i > 0) {
            map.put((java.lang.Object) readValue(classLoader, cls, new java.lang.Class[0]), (java.lang.Object) readValue(classLoader, cls2, new java.lang.Class[0]));
            i--;
        }
    }

    private void readArrayMapInternal(android.util.ArrayMap<? super java.lang.String, java.lang.Object> arrayMap, int i, java.lang.ClassLoader classLoader) {
        readArrayMap(arrayMap, i, true, false, classLoader);
    }

    int readArrayMap(android.util.ArrayMap<? super java.lang.String, java.lang.Object> arrayMap, int i, boolean z, boolean z2, java.lang.ClassLoader classLoader) {
        int i2 = 0;
        while (i > 0) {
            java.lang.String readString = readString();
            java.lang.Object readLazyValue = z2 ? readLazyValue(classLoader) : readValue(classLoader);
            if (readLazyValue instanceof android.os.Parcel.LazyValue) {
                i2++;
            }
            if (z) {
                arrayMap.append(readString, readLazyValue);
            } else {
                arrayMap.put(readString, readLazyValue);
            }
            i--;
        }
        if (z) {
            arrayMap.validate();
        }
        return i2;
    }

    public void readArrayMap(android.util.ArrayMap<? super java.lang.String, java.lang.Object> arrayMap, java.lang.ClassLoader classLoader) {
        int readInt = readInt();
        if (readInt < 0) {
            return;
        }
        readArrayMapInternal(arrayMap, readInt, classLoader);
    }

    public android.util.ArraySet<? extends java.lang.Object> readArraySet(java.lang.ClassLoader classLoader) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        android.util.ArraySet<? extends java.lang.Object> arraySet = new android.util.ArraySet<>(readInt);
        for (int i = 0; i < readInt; i++) {
            arraySet.append(readValue(classLoader));
        }
        return arraySet;
    }

    private void readListInternal(java.util.List list, int i, java.lang.ClassLoader classLoader) {
        readListInternal(list, i, classLoader, null);
    }

    private <T> void readListInternal(java.util.List<? super T> list, int i, java.lang.ClassLoader classLoader, java.lang.Class<T> cls) {
        while (i > 0) {
            list.add((java.lang.Object) readValue(classLoader, cls, new java.lang.Class[0]));
            i--;
        }
    }

    private <T> java.util.ArrayList<T> readArrayListInternal(java.lang.ClassLoader classLoader, java.lang.Class<? extends T> cls) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        java.util.ArrayList<T> arrayList = new java.util.ArrayList<>(readInt);
        readListInternal(arrayList, readInt, classLoader, cls);
        return arrayList;
    }

    private void readArrayInternal(java.lang.Object[] objArr, int i, java.lang.ClassLoader classLoader) {
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = readValue(classLoader, (java.lang.Class) null, new java.lang.Class[0]);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> T[] readArrayInternal(java.lang.ClassLoader classLoader, java.lang.Class<T> cls) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        T[] tArr = (T[]) ((java.lang.Object[]) (cls == null ? new java.lang.Object[readInt] : java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, readInt)));
        for (int i = 0; i < readInt; i++) {
            tArr[i] = readValue(classLoader, cls, new java.lang.Class[0]);
        }
        return tArr;
    }

    private void readSparseArrayInternal(android.util.SparseArray sparseArray, int i, java.lang.ClassLoader classLoader) {
        while (i > 0) {
            sparseArray.append(readInt(), readValue(classLoader));
            i--;
        }
    }

    private <T> android.util.SparseArray<T> readSparseArrayInternal(java.lang.ClassLoader classLoader, java.lang.Class<? extends T> cls) {
        int readInt = readInt();
        if (readInt < 0) {
            return null;
        }
        android.net.wifi.WifiMigration.AnonymousClass1 anonymousClass1 = (android.util.SparseArray<T>) new android.util.SparseArray(readInt);
        while (readInt > 0) {
            anonymousClass1.append(readInt(), readValue(classLoader, cls, new java.lang.Class[0]));
            readInt--;
        }
        return anonymousClass1;
    }

    private void readSparseBooleanArrayInternal(android.util.SparseBooleanArray sparseBooleanArray, int i) {
        while (i > 0) {
            int readInt = readInt();
            boolean z = true;
            if (readByte() != 1) {
                z = false;
            }
            sparseBooleanArray.append(readInt, z);
            i--;
        }
    }

    private void readSparseIntArrayInternal(android.util.SparseIntArray sparseIntArray, int i) {
        while (i > 0) {
            sparseIntArray.append(readInt(), readInt());
            i--;
        }
    }

    public long getOpenAshmemSize() {
        return nativeGetOpenAshmemSize(this.mNativePtr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String valueTypeToString(int i) {
        switch (i) {
            case -1:
                return "VAL_NULL";
            case 0:
            case 22:
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "VAL_INTEGER";
            case 2:
                return "VAL_MAP";
            case 3:
                return "VAL_BUNDLE";
            case 4:
                return "VAL_PARCELABLE";
            case 5:
                return "VAL_SHORT";
            case 6:
                return "VAL_LONG";
            case 7:
                return "VAL_FLOAT";
            case 8:
                return "VAL_DOUBLE";
            case 9:
                return "VAL_BOOLEAN";
            case 10:
                return "VAL_CHARSEQUENCE";
            case 11:
                return "VAL_LIST";
            case 12:
                return "VAL_SPARSEARRAY";
            case 13:
                return "VAL_BYTEARRAY";
            case 14:
                return "VAL_STRINGARRAY";
            case 15:
                return "VAL_IBINDER";
            case 16:
                return "VAL_PARCELABLEARRAY";
            case 17:
                return "VAL_OBJECTARRAY";
            case 18:
                return "VAL_INTARRAY";
            case 19:
                return "VAL_LONGARRAY";
            case 20:
                return "VAL_BYTE";
            case 21:
                return "VAL_SERIALIZABLE";
            case 23:
                return "VAL_BOOLEANARRAY";
            case 24:
                return "VAL_CHARSEQUENCEARRAY";
            case 25:
                return "VAL_PERSISTABLEBUNDLE";
            case 26:
                return "VAL_SIZE";
            case 27:
                return "VAL_SIZEF";
            case 28:
                return "VAL_DOUBLEARRAY";
            case 29:
                return "VAL_CHAR";
            case 30:
                return "VAL_SHORTARRAY";
            case 31:
                return "VAL_CHARARRAY";
            case 32:
                return "VAL_FLOATARRAY";
        }
    }
}
