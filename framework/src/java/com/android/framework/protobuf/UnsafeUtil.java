package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class UnsafeUtil {
    static final boolean IS_BIG_ENDIAN;
    private static final int STRIDE = 8;
    private static final int STRIDE_ALIGNMENT_MASK = 7;
    private static final sun.misc.Unsafe UNSAFE = getUnsafe();
    private static final java.lang.Class<?> MEMORY_CLASS = com.android.framework.protobuf.Android.getMemoryClass();
    private static final boolean IS_ANDROID_64 = determineAndroidSupportByAddressSize(java.lang.Long.TYPE);
    private static final boolean IS_ANDROID_32 = determineAndroidSupportByAddressSize(java.lang.Integer.TYPE);
    private static final com.android.framework.protobuf.UnsafeUtil.MemoryAccessor MEMORY_ACCESSOR = getMemoryAccessor();
    private static final boolean HAS_UNSAFE_BYTEBUFFER_OPERATIONS = supportsUnsafeByteBufferOperations();
    private static final boolean HAS_UNSAFE_ARRAY_OPERATIONS = supportsUnsafeArrayOperations();
    static final long BYTE_ARRAY_BASE_OFFSET = arrayBaseOffset(byte[].class);
    private static final long BOOLEAN_ARRAY_BASE_OFFSET = arrayBaseOffset(boolean[].class);
    private static final long BOOLEAN_ARRAY_INDEX_SCALE = arrayIndexScale(boolean[].class);
    private static final long INT_ARRAY_BASE_OFFSET = arrayBaseOffset(int[].class);
    private static final long INT_ARRAY_INDEX_SCALE = arrayIndexScale(int[].class);
    private static final long LONG_ARRAY_BASE_OFFSET = arrayBaseOffset(long[].class);
    private static final long LONG_ARRAY_INDEX_SCALE = arrayIndexScale(long[].class);
    private static final long FLOAT_ARRAY_BASE_OFFSET = arrayBaseOffset(float[].class);
    private static final long FLOAT_ARRAY_INDEX_SCALE = arrayIndexScale(float[].class);
    private static final long DOUBLE_ARRAY_BASE_OFFSET = arrayBaseOffset(double[].class);
    private static final long DOUBLE_ARRAY_INDEX_SCALE = arrayIndexScale(double[].class);
    private static final long OBJECT_ARRAY_BASE_OFFSET = arrayBaseOffset(java.lang.Object[].class);
    private static final long OBJECT_ARRAY_INDEX_SCALE = arrayIndexScale(java.lang.Object[].class);
    private static final long BUFFER_ADDRESS_OFFSET = fieldOffset(bufferAddressField());
    private static final int BYTE_ARRAY_ALIGNMENT = (int) (BYTE_ARRAY_BASE_OFFSET & 7);

    static {
        IS_BIG_ENDIAN = java.nio.ByteOrder.nativeOrder() == java.nio.ByteOrder.BIG_ENDIAN;
    }

    private UnsafeUtil() {
    }

    static boolean hasUnsafeArrayOperations() {
        return HAS_UNSAFE_ARRAY_OPERATIONS;
    }

    static boolean hasUnsafeByteBufferOperations() {
        return HAS_UNSAFE_BYTEBUFFER_OPERATIONS;
    }

    static boolean isAndroid64() {
        return IS_ANDROID_64;
    }

    static <T> T allocateInstance(java.lang.Class<T> cls) {
        try {
            return (T) UNSAFE.allocateInstance(cls);
        } catch (java.lang.InstantiationException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    static long objectFieldOffset(java.lang.reflect.Field field) {
        return MEMORY_ACCESSOR.objectFieldOffset(field);
    }

    private static int arrayBaseOffset(java.lang.Class<?> cls) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            return MEMORY_ACCESSOR.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int arrayIndexScale(java.lang.Class<?> cls) {
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            return MEMORY_ACCESSOR.arrayIndexScale(cls);
        }
        return -1;
    }

    static byte getByte(java.lang.Object obj, long j) {
        return MEMORY_ACCESSOR.getByte(obj, j);
    }

    static void putByte(java.lang.Object obj, long j, byte b) {
        MEMORY_ACCESSOR.putByte(obj, j, b);
    }

    static int getInt(java.lang.Object obj, long j) {
        return MEMORY_ACCESSOR.getInt(obj, j);
    }

    static void putInt(java.lang.Object obj, long j, int i) {
        MEMORY_ACCESSOR.putInt(obj, j, i);
    }

    static long getLong(java.lang.Object obj, long j) {
        return MEMORY_ACCESSOR.getLong(obj, j);
    }

    static void putLong(java.lang.Object obj, long j, long j2) {
        MEMORY_ACCESSOR.putLong(obj, j, j2);
    }

    static boolean getBoolean(java.lang.Object obj, long j) {
        return MEMORY_ACCESSOR.getBoolean(obj, j);
    }

    static void putBoolean(java.lang.Object obj, long j, boolean z) {
        MEMORY_ACCESSOR.putBoolean(obj, j, z);
    }

    static float getFloat(java.lang.Object obj, long j) {
        return MEMORY_ACCESSOR.getFloat(obj, j);
    }

    static void putFloat(java.lang.Object obj, long j, float f) {
        MEMORY_ACCESSOR.putFloat(obj, j, f);
    }

    static double getDouble(java.lang.Object obj, long j) {
        return MEMORY_ACCESSOR.getDouble(obj, j);
    }

    static void putDouble(java.lang.Object obj, long j, double d) {
        MEMORY_ACCESSOR.putDouble(obj, j, d);
    }

    static java.lang.Object getObject(java.lang.Object obj, long j) {
        return MEMORY_ACCESSOR.getObject(obj, j);
    }

    static void putObject(java.lang.Object obj, long j, java.lang.Object obj2) {
        MEMORY_ACCESSOR.putObject(obj, j, obj2);
    }

    static byte getByte(byte[] bArr, long j) {
        return MEMORY_ACCESSOR.getByte(bArr, BYTE_ARRAY_BASE_OFFSET + j);
    }

    static void putByte(byte[] bArr, long j, byte b) {
        MEMORY_ACCESSOR.putByte(bArr, BYTE_ARRAY_BASE_OFFSET + j, b);
    }

    static int getInt(int[] iArr, long j) {
        return MEMORY_ACCESSOR.getInt(iArr, INT_ARRAY_BASE_OFFSET + (j * INT_ARRAY_INDEX_SCALE));
    }

    static void putInt(int[] iArr, long j, int i) {
        MEMORY_ACCESSOR.putInt(iArr, INT_ARRAY_BASE_OFFSET + (j * INT_ARRAY_INDEX_SCALE), i);
    }

    static long getLong(long[] jArr, long j) {
        return MEMORY_ACCESSOR.getLong(jArr, LONG_ARRAY_BASE_OFFSET + (j * LONG_ARRAY_INDEX_SCALE));
    }

    static void putLong(long[] jArr, long j, long j2) {
        MEMORY_ACCESSOR.putLong(jArr, LONG_ARRAY_BASE_OFFSET + (j * LONG_ARRAY_INDEX_SCALE), j2);
    }

    static boolean getBoolean(boolean[] zArr, long j) {
        return MEMORY_ACCESSOR.getBoolean(zArr, BOOLEAN_ARRAY_BASE_OFFSET + (j * BOOLEAN_ARRAY_INDEX_SCALE));
    }

    static void putBoolean(boolean[] zArr, long j, boolean z) {
        MEMORY_ACCESSOR.putBoolean(zArr, BOOLEAN_ARRAY_BASE_OFFSET + (j * BOOLEAN_ARRAY_INDEX_SCALE), z);
    }

    static float getFloat(float[] fArr, long j) {
        return MEMORY_ACCESSOR.getFloat(fArr, FLOAT_ARRAY_BASE_OFFSET + (j * FLOAT_ARRAY_INDEX_SCALE));
    }

    static void putFloat(float[] fArr, long j, float f) {
        MEMORY_ACCESSOR.putFloat(fArr, FLOAT_ARRAY_BASE_OFFSET + (j * FLOAT_ARRAY_INDEX_SCALE), f);
    }

    static double getDouble(double[] dArr, long j) {
        return MEMORY_ACCESSOR.getDouble(dArr, DOUBLE_ARRAY_BASE_OFFSET + (j * DOUBLE_ARRAY_INDEX_SCALE));
    }

    static void putDouble(double[] dArr, long j, double d) {
        MEMORY_ACCESSOR.putDouble(dArr, DOUBLE_ARRAY_BASE_OFFSET + (j * DOUBLE_ARRAY_INDEX_SCALE), d);
    }

    static java.lang.Object getObject(java.lang.Object[] objArr, long j) {
        return MEMORY_ACCESSOR.getObject(objArr, OBJECT_ARRAY_BASE_OFFSET + (j * OBJECT_ARRAY_INDEX_SCALE));
    }

    static void putObject(java.lang.Object[] objArr, long j, java.lang.Object obj) {
        MEMORY_ACCESSOR.putObject(objArr, OBJECT_ARRAY_BASE_OFFSET + (j * OBJECT_ARRAY_INDEX_SCALE), obj);
    }

    static void copyMemory(byte[] bArr, long j, long j2, long j3) {
        MEMORY_ACCESSOR.copyMemory(bArr, j, j2, j3);
    }

    static void copyMemory(long j, byte[] bArr, long j2, long j3) {
        MEMORY_ACCESSOR.copyMemory(j, bArr, j2, j3);
    }

    static void copyMemory(byte[] bArr, long j, byte[] bArr2, long j2, long j3) {
        java.lang.System.arraycopy(bArr, (int) j, bArr2, (int) j2, (int) j3);
    }

    static byte getByte(long j) {
        return MEMORY_ACCESSOR.getByte(j);
    }

    static void putByte(long j, byte b) {
        MEMORY_ACCESSOR.putByte(j, b);
    }

    static int getInt(long j) {
        return MEMORY_ACCESSOR.getInt(j);
    }

    static void putInt(long j, int i) {
        MEMORY_ACCESSOR.putInt(j, i);
    }

    static long getLong(long j) {
        return MEMORY_ACCESSOR.getLong(j);
    }

    static void putLong(long j, long j2) {
        MEMORY_ACCESSOR.putLong(j, j2);
    }

    static long addressOffset(java.nio.ByteBuffer byteBuffer) {
        return MEMORY_ACCESSOR.getLong(byteBuffer, BUFFER_ADDRESS_OFFSET);
    }

    static java.lang.Object getStaticObject(java.lang.reflect.Field field) {
        return MEMORY_ACCESSOR.getStaticObject(field);
    }

    static sun.misc.Unsafe getUnsafe() {
        try {
            return (sun.misc.Unsafe) java.security.AccessController.doPrivileged(new java.security.PrivilegedExceptionAction<sun.misc.Unsafe>() { // from class: com.android.framework.protobuf.UnsafeUtil.1
                @Override // java.security.PrivilegedExceptionAction
                public sun.misc.Unsafe run() throws java.lang.Exception {
                    for (java.lang.reflect.Field field : sun.misc.Unsafe.class.getDeclaredFields()) {
                        field.setAccessible(true);
                        java.lang.Object obj = field.get(null);
                        if (sun.misc.Unsafe.class.isInstance(obj)) {
                            return (sun.misc.Unsafe) sun.misc.Unsafe.class.cast(obj);
                        }
                    }
                    return null;
                }
            });
        } catch (java.lang.Throwable th) {
            return null;
        }
    }

    private static com.android.framework.protobuf.UnsafeUtil.MemoryAccessor getMemoryAccessor() {
        if (UNSAFE == null) {
            return null;
        }
        if (com.android.framework.protobuf.Android.isOnAndroidDevice()) {
            if (IS_ANDROID_64) {
                return new com.android.framework.protobuf.UnsafeUtil.Android64MemoryAccessor(UNSAFE);
            }
            if (IS_ANDROID_32) {
                return new com.android.framework.protobuf.UnsafeUtil.Android32MemoryAccessor(UNSAFE);
            }
            return null;
        }
        return new com.android.framework.protobuf.UnsafeUtil.JvmMemoryAccessor(UNSAFE);
    }

    private static boolean supportsUnsafeArrayOperations() {
        if (MEMORY_ACCESSOR == null) {
            return false;
        }
        return MEMORY_ACCESSOR.supportsUnsafeArrayOperations();
    }

    private static boolean supportsUnsafeByteBufferOperations() {
        if (MEMORY_ACCESSOR == null) {
            return false;
        }
        return MEMORY_ACCESSOR.supportsUnsafeByteBufferOperations();
    }

    static boolean determineAndroidSupportByAddressSize(java.lang.Class<?> cls) {
        if (!com.android.framework.protobuf.Android.isOnAndroidDevice()) {
            return false;
        }
        try {
            java.lang.Class<?> cls2 = MEMORY_CLASS;
            cls2.getMethod("peekLong", cls, java.lang.Boolean.TYPE);
            cls2.getMethod("pokeLong", cls, java.lang.Long.TYPE, java.lang.Boolean.TYPE);
            cls2.getMethod("pokeInt", cls, java.lang.Integer.TYPE, java.lang.Boolean.TYPE);
            cls2.getMethod("peekInt", cls, java.lang.Boolean.TYPE);
            cls2.getMethod("pokeByte", cls, java.lang.Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, java.lang.Integer.TYPE, java.lang.Integer.TYPE);
            cls2.getMethod("peekByteArray", cls, byte[].class, java.lang.Integer.TYPE, java.lang.Integer.TYPE);
            return true;
        } catch (java.lang.Throwable th) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.reflect.Field bufferAddressField() {
        java.lang.reflect.Field field;
        if (com.android.framework.protobuf.Android.isOnAndroidDevice() && (field = field(java.nio.Buffer.class, "effectiveDirectAddress")) != null) {
            return field;
        }
        java.lang.reflect.Field field2 = field(java.nio.Buffer.class, "address");
        if (field2 == null || field2.getType() != java.lang.Long.TYPE) {
            return null;
        }
        return field2;
    }

    private static int firstDifferingByteIndexNativeEndian(long j, long j2) {
        int numberOfTrailingZeros;
        if (IS_BIG_ENDIAN) {
            numberOfTrailingZeros = java.lang.Long.numberOfLeadingZeros(j ^ j2);
        } else {
            numberOfTrailingZeros = java.lang.Long.numberOfTrailingZeros(j ^ j2);
        }
        return numberOfTrailingZeros >> 3;
    }

    static int mismatch(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        if (i < 0 || i2 < 0 || i3 < 0 || i + i3 > bArr.length || i2 + i3 > bArr2.length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int i4 = 0;
        if (HAS_UNSAFE_ARRAY_OPERATIONS) {
            for (int i5 = (BYTE_ARRAY_ALIGNMENT + i) & 7; i4 < i3 && (i5 & 7) != 0; i5++) {
                if (bArr[i + i4] == bArr2[i2 + i4]) {
                    i4++;
                } else {
                    return i4;
                }
            }
            int i6 = ((i3 - i4) & (-8)) + i4;
            while (i4 < i6) {
                long j = i4;
                long j2 = getLong((java.lang.Object) bArr, BYTE_ARRAY_BASE_OFFSET + i + j);
                long j3 = getLong((java.lang.Object) bArr2, BYTE_ARRAY_BASE_OFFSET + i2 + j);
                if (j2 == j3) {
                    i4 += 8;
                } else {
                    return i4 + firstDifferingByteIndexNativeEndian(j2, j3);
                }
            }
        }
        while (i4 < i3) {
            if (bArr[i + i4] == bArr2[i2 + i4]) {
                i4++;
            } else {
                return i4;
            }
        }
        return -1;
    }

    private static long fieldOffset(java.lang.reflect.Field field) {
        if (field == null || MEMORY_ACCESSOR == null) {
            return -1L;
        }
        return MEMORY_ACCESSOR.objectFieldOffset(field);
    }

    private static java.lang.reflect.Field field(java.lang.Class<?> cls, java.lang.String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (java.lang.Throwable th) {
            return null;
        }
    }

    private static abstract class MemoryAccessor {
        sun.misc.Unsafe unsafe;

        public abstract void copyMemory(long j, byte[] bArr, long j2, long j3);

        public abstract void copyMemory(byte[] bArr, long j, long j2, long j3);

        public abstract boolean getBoolean(java.lang.Object obj, long j);

        public abstract byte getByte(long j);

        public abstract byte getByte(java.lang.Object obj, long j);

        public abstract double getDouble(java.lang.Object obj, long j);

        public abstract float getFloat(java.lang.Object obj, long j);

        public abstract int getInt(long j);

        public abstract long getLong(long j);

        public abstract java.lang.Object getStaticObject(java.lang.reflect.Field field);

        public abstract void putBoolean(java.lang.Object obj, long j, boolean z);

        public abstract void putByte(long j, byte b);

        public abstract void putByte(java.lang.Object obj, long j, byte b);

        public abstract void putDouble(java.lang.Object obj, long j, double d);

        public abstract void putFloat(java.lang.Object obj, long j, float f);

        public abstract void putInt(long j, int i);

        public abstract void putLong(long j, long j2);

        MemoryAccessor(sun.misc.Unsafe unsafe) {
            this.unsafe = unsafe;
        }

        public final long objectFieldOffset(java.lang.reflect.Field field) {
            return this.unsafe.objectFieldOffset(field);
        }

        public final int arrayBaseOffset(java.lang.Class<?> cls) {
            return this.unsafe.arrayBaseOffset(cls);
        }

        public final int arrayIndexScale(java.lang.Class<?> cls) {
            return this.unsafe.arrayIndexScale(cls);
        }

        public boolean supportsUnsafeArrayOperations() {
            if (this.unsafe == null) {
                return false;
            }
            try {
                java.lang.Class<?> cls = this.unsafe.getClass();
                cls.getMethod("objectFieldOffset", java.lang.reflect.Field.class);
                cls.getMethod("arrayBaseOffset", java.lang.Class.class);
                cls.getMethod("arrayIndexScale", java.lang.Class.class);
                cls.getMethod("getInt", java.lang.Object.class, java.lang.Long.TYPE);
                cls.getMethod("putInt", java.lang.Object.class, java.lang.Long.TYPE, java.lang.Integer.TYPE);
                cls.getMethod("getLong", java.lang.Object.class, java.lang.Long.TYPE);
                cls.getMethod("putLong", java.lang.Object.class, java.lang.Long.TYPE, java.lang.Long.TYPE);
                cls.getMethod("getObject", java.lang.Object.class, java.lang.Long.TYPE);
                cls.getMethod("putObject", java.lang.Object.class, java.lang.Long.TYPE, java.lang.Object.class);
                return true;
            } catch (java.lang.Throwable th) {
                com.android.framework.protobuf.UnsafeUtil.logMissingMethod(th);
                return false;
            }
        }

        public final int getInt(java.lang.Object obj, long j) {
            return this.unsafe.getInt(obj, j);
        }

        public final void putInt(java.lang.Object obj, long j, int i) {
            this.unsafe.putInt(obj, j, i);
        }

        public final long getLong(java.lang.Object obj, long j) {
            return this.unsafe.getLong(obj, j);
        }

        public final void putLong(java.lang.Object obj, long j, long j2) {
            this.unsafe.putLong(obj, j, j2);
        }

        public final java.lang.Object getObject(java.lang.Object obj, long j) {
            return this.unsafe.getObject(obj, j);
        }

        public final void putObject(java.lang.Object obj, long j, java.lang.Object obj2) {
            this.unsafe.putObject(obj, j, obj2);
        }

        public boolean supportsUnsafeByteBufferOperations() {
            if (this.unsafe == null) {
                return false;
            }
            try {
                java.lang.Class<?> cls = this.unsafe.getClass();
                cls.getMethod("objectFieldOffset", java.lang.reflect.Field.class);
                cls.getMethod("getLong", java.lang.Object.class, java.lang.Long.TYPE);
                return com.android.framework.protobuf.UnsafeUtil.bufferAddressField() != null;
            } catch (java.lang.Throwable th) {
                com.android.framework.protobuf.UnsafeUtil.logMissingMethod(th);
                return false;
            }
        }
    }

    private static final class JvmMemoryAccessor extends com.android.framework.protobuf.UnsafeUtil.MemoryAccessor {
        JvmMemoryAccessor(sun.misc.Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public java.lang.Object getStaticObject(java.lang.reflect.Field field) {
            return getObject(this.unsafe.staticFieldBase(field), this.unsafe.staticFieldOffset(field));
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public boolean supportsUnsafeArrayOperations() {
            if (!super.supportsUnsafeArrayOperations()) {
                return false;
            }
            try {
                java.lang.Class<?> cls = this.unsafe.getClass();
                cls.getMethod("getByte", java.lang.Object.class, java.lang.Long.TYPE);
                cls.getMethod("putByte", java.lang.Object.class, java.lang.Long.TYPE, java.lang.Byte.TYPE);
                cls.getMethod("getBoolean", java.lang.Object.class, java.lang.Long.TYPE);
                cls.getMethod("putBoolean", java.lang.Object.class, java.lang.Long.TYPE, java.lang.Boolean.TYPE);
                cls.getMethod("getFloat", java.lang.Object.class, java.lang.Long.TYPE);
                cls.getMethod("putFloat", java.lang.Object.class, java.lang.Long.TYPE, java.lang.Float.TYPE);
                cls.getMethod("getDouble", java.lang.Object.class, java.lang.Long.TYPE);
                cls.getMethod("putDouble", java.lang.Object.class, java.lang.Long.TYPE, java.lang.Double.TYPE);
                return true;
            } catch (java.lang.Throwable th) {
                com.android.framework.protobuf.UnsafeUtil.logMissingMethod(th);
                return false;
            }
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(java.lang.Object obj, long j) {
            return this.unsafe.getByte(obj, j);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(java.lang.Object obj, long j, byte b) {
            this.unsafe.putByte(obj, j, b);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public boolean getBoolean(java.lang.Object obj, long j) {
            return this.unsafe.getBoolean(obj, j);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putBoolean(java.lang.Object obj, long j, boolean z) {
            this.unsafe.putBoolean(obj, j, z);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public float getFloat(java.lang.Object obj, long j) {
            return this.unsafe.getFloat(obj, j);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putFloat(java.lang.Object obj, long j, float f) {
            this.unsafe.putFloat(obj, j, f);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public double getDouble(java.lang.Object obj, long j) {
            return this.unsafe.getDouble(obj, j);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putDouble(java.lang.Object obj, long j, double d) {
            this.unsafe.putDouble(obj, j, d);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public boolean supportsUnsafeByteBufferOperations() {
            if (!super.supportsUnsafeByteBufferOperations()) {
                return false;
            }
            try {
                java.lang.Class<?> cls = this.unsafe.getClass();
                cls.getMethod("getByte", java.lang.Long.TYPE);
                cls.getMethod("putByte", java.lang.Long.TYPE, java.lang.Byte.TYPE);
                cls.getMethod("getInt", java.lang.Long.TYPE);
                cls.getMethod("putInt", java.lang.Long.TYPE, java.lang.Integer.TYPE);
                cls.getMethod("getLong", java.lang.Long.TYPE);
                cls.getMethod("putLong", java.lang.Long.TYPE, java.lang.Long.TYPE);
                cls.getMethod("copyMemory", java.lang.Long.TYPE, java.lang.Long.TYPE, java.lang.Long.TYPE);
                cls.getMethod("copyMemory", java.lang.Object.class, java.lang.Long.TYPE, java.lang.Object.class, java.lang.Long.TYPE, java.lang.Long.TYPE);
                return true;
            } catch (java.lang.Throwable th) {
                com.android.framework.protobuf.UnsafeUtil.logMissingMethod(th);
                return false;
            }
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(long j) {
            return this.unsafe.getByte(j);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(long j, byte b) {
            this.unsafe.putByte(j, b);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public int getInt(long j) {
            return this.unsafe.getInt(j);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putInt(long j, int i) {
            this.unsafe.putInt(j, i);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public long getLong(long j) {
            return this.unsafe.getLong(j);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putLong(long j, long j2) {
            this.unsafe.putLong(j, j2);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(long j, byte[] bArr, long j2, long j3) {
            this.unsafe.copyMemory((byte[]) null, j, bArr, com.android.framework.protobuf.UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + j2, j3);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(byte[] bArr, long j, long j2, long j3) {
            this.unsafe.copyMemory(bArr, com.android.framework.protobuf.UnsafeUtil.BYTE_ARRAY_BASE_OFFSET + j, (byte[]) null, j2, j3);
        }
    }

    private static final class Android64MemoryAccessor extends com.android.framework.protobuf.UnsafeUtil.MemoryAccessor {
        Android64MemoryAccessor(sun.misc.Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public java.lang.Object getStaticObject(java.lang.reflect.Field field) {
            try {
                return field.get(null);
            } catch (java.lang.IllegalAccessException e) {
                return null;
            }
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(java.lang.Object obj, long j) {
            return com.android.framework.protobuf.UnsafeUtil.IS_BIG_ENDIAN ? com.android.framework.protobuf.UnsafeUtil.getByteBigEndian(obj, j) : com.android.framework.protobuf.UnsafeUtil.getByteLittleEndian(obj, j);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(java.lang.Object obj, long j, byte b) {
            if (com.android.framework.protobuf.UnsafeUtil.IS_BIG_ENDIAN) {
                com.android.framework.protobuf.UnsafeUtil.putByteBigEndian(obj, j, b);
            } else {
                com.android.framework.protobuf.UnsafeUtil.putByteLittleEndian(obj, j, b);
            }
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public boolean getBoolean(java.lang.Object obj, long j) {
            return com.android.framework.protobuf.UnsafeUtil.IS_BIG_ENDIAN ? com.android.framework.protobuf.UnsafeUtil.getBooleanBigEndian(obj, j) : com.android.framework.protobuf.UnsafeUtil.getBooleanLittleEndian(obj, j);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putBoolean(java.lang.Object obj, long j, boolean z) {
            if (com.android.framework.protobuf.UnsafeUtil.IS_BIG_ENDIAN) {
                com.android.framework.protobuf.UnsafeUtil.putBooleanBigEndian(obj, j, z);
            } else {
                com.android.framework.protobuf.UnsafeUtil.putBooleanLittleEndian(obj, j, z);
            }
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public float getFloat(java.lang.Object obj, long j) {
            return java.lang.Float.intBitsToFloat(getInt(obj, j));
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putFloat(java.lang.Object obj, long j, float f) {
            putInt(obj, j, java.lang.Float.floatToIntBits(f));
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public double getDouble(java.lang.Object obj, long j) {
            return java.lang.Double.longBitsToDouble(getLong(obj, j));
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putDouble(java.lang.Object obj, long j, double d) {
            putLong(obj, j, java.lang.Double.doubleToLongBits(d));
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public boolean supportsUnsafeByteBufferOperations() {
            return false;
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(long j) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(long j, byte b) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public int getInt(long j) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putInt(long j, int i) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public long getLong(long j) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putLong(long j, long j2) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(long j, byte[] bArr, long j2, long j3) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(byte[] bArr, long j, long j2, long j3) {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    private static final class Android32MemoryAccessor extends com.android.framework.protobuf.UnsafeUtil.MemoryAccessor {
        private static final long SMALL_ADDRESS_MASK = -1;

        private static int smallAddress(long j) {
            return (int) (j & (-1));
        }

        Android32MemoryAccessor(sun.misc.Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public java.lang.Object getStaticObject(java.lang.reflect.Field field) {
            try {
                return field.get(null);
            } catch (java.lang.IllegalAccessException e) {
                return null;
            }
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(java.lang.Object obj, long j) {
            return com.android.framework.protobuf.UnsafeUtil.IS_BIG_ENDIAN ? com.android.framework.protobuf.UnsafeUtil.getByteBigEndian(obj, j) : com.android.framework.protobuf.UnsafeUtil.getByteLittleEndian(obj, j);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(java.lang.Object obj, long j, byte b) {
            if (com.android.framework.protobuf.UnsafeUtil.IS_BIG_ENDIAN) {
                com.android.framework.protobuf.UnsafeUtil.putByteBigEndian(obj, j, b);
            } else {
                com.android.framework.protobuf.UnsafeUtil.putByteLittleEndian(obj, j, b);
            }
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public boolean getBoolean(java.lang.Object obj, long j) {
            return com.android.framework.protobuf.UnsafeUtil.IS_BIG_ENDIAN ? com.android.framework.protobuf.UnsafeUtil.getBooleanBigEndian(obj, j) : com.android.framework.protobuf.UnsafeUtil.getBooleanLittleEndian(obj, j);
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putBoolean(java.lang.Object obj, long j, boolean z) {
            if (com.android.framework.protobuf.UnsafeUtil.IS_BIG_ENDIAN) {
                com.android.framework.protobuf.UnsafeUtil.putBooleanBigEndian(obj, j, z);
            } else {
                com.android.framework.protobuf.UnsafeUtil.putBooleanLittleEndian(obj, j, z);
            }
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public float getFloat(java.lang.Object obj, long j) {
            return java.lang.Float.intBitsToFloat(getInt(obj, j));
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putFloat(java.lang.Object obj, long j, float f) {
            putInt(obj, j, java.lang.Float.floatToIntBits(f));
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public double getDouble(java.lang.Object obj, long j) {
            return java.lang.Double.longBitsToDouble(getLong(obj, j));
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putDouble(java.lang.Object obj, long j, double d) {
            putLong(obj, j, java.lang.Double.doubleToLongBits(d));
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public boolean supportsUnsafeByteBufferOperations() {
            return false;
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public byte getByte(long j) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putByte(long j, byte b) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public int getInt(long j) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putInt(long j, int i) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public long getLong(long j) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void putLong(long j, long j2) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(long j, byte[] bArr, long j2, long j3) {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override // com.android.framework.protobuf.UnsafeUtil.MemoryAccessor
        public void copyMemory(byte[] bArr, long j, long j2, long j3) {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte getByteBigEndian(java.lang.Object obj, long j) {
        return (byte) ((getInt(obj, (-4) & j) >>> ((int) (((~j) & 3) << 3))) & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte getByteLittleEndian(java.lang.Object obj, long j) {
        return (byte) ((getInt(obj, (-4) & j) >>> ((int) ((j & 3) << 3))) & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putByteBigEndian(java.lang.Object obj, long j, byte b) {
        long j2 = (-4) & j;
        int i = getInt(obj, j2);
        int i2 = ((~((int) j)) & 3) << 3;
        putInt(obj, j2, ((255 & b) << i2) | (i & (~(255 << i2))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putByteLittleEndian(java.lang.Object obj, long j, byte b) {
        long j2 = (-4) & j;
        int i = (((int) j) & 3) << 3;
        putInt(obj, j2, ((255 & b) << i) | (getInt(obj, j2) & (~(255 << i))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean getBooleanBigEndian(java.lang.Object obj, long j) {
        return getByteBigEndian(obj, j) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean getBooleanLittleEndian(java.lang.Object obj, long j) {
        return getByteLittleEndian(obj, j) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putBooleanBigEndian(java.lang.Object obj, long j, boolean z) {
        putByteBigEndian(obj, j, z ? (byte) 1 : (byte) 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void putBooleanLittleEndian(java.lang.Object obj, long j, boolean z) {
        putByteLittleEndian(obj, j, z ? (byte) 1 : (byte) 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logMissingMethod(java.lang.Throwable th) {
        java.util.logging.Logger.getLogger(com.android.framework.protobuf.UnsafeUtil.class.getName()).log(java.util.logging.Level.WARNING, "platform method missing - proto runtime falling back to safer methods: " + th);
    }
}
