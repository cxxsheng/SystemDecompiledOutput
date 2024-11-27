package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public final class Internal {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    static final java.nio.charset.Charset US_ASCII = java.nio.charset.Charset.forName("US-ASCII");
    static final java.nio.charset.Charset UTF_8 = java.nio.charset.Charset.forName(android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
    static final java.nio.charset.Charset ISO_8859_1 = java.nio.charset.Charset.forName("ISO-8859-1");
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final java.nio.ByteBuffer EMPTY_BYTE_BUFFER = java.nio.ByteBuffer.wrap(EMPTY_BYTE_ARRAY);
    public static final com.android.framework.protobuf.CodedInputStream EMPTY_CODED_INPUT_STREAM = com.android.framework.protobuf.CodedInputStream.newInstance(EMPTY_BYTE_ARRAY);

    public interface BooleanList extends com.android.framework.protobuf.Internal.ProtobufList<java.lang.Boolean> {
        void addBoolean(boolean z);

        boolean getBoolean(int i);

        @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
        /* renamed from: mutableCopyWithCapacity, reason: merged with bridge method [inline-methods] */
        com.android.framework.protobuf.Internal.ProtobufList<java.lang.Boolean> mutableCopyWithCapacity2(int i);

        boolean setBoolean(int i, boolean z);
    }

    public interface DoubleList extends com.android.framework.protobuf.Internal.ProtobufList<java.lang.Double> {
        void addDouble(double d);

        double getDouble(int i);

        @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
        /* renamed from: mutableCopyWithCapacity */
        com.android.framework.protobuf.Internal.ProtobufList<java.lang.Double> mutableCopyWithCapacity2(int i);

        double setDouble(int i, double d);
    }

    public interface EnumLite {
        int getNumber();
    }

    public interface EnumLiteMap<T extends com.android.framework.protobuf.Internal.EnumLite> {
        T findValueByNumber(int i);
    }

    public interface EnumVerifier {
        boolean isInRange(int i);
    }

    public interface FloatList extends com.android.framework.protobuf.Internal.ProtobufList<java.lang.Float> {
        void addFloat(float f);

        float getFloat(int i);

        @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
        /* renamed from: mutableCopyWithCapacity */
        com.android.framework.protobuf.Internal.ProtobufList<java.lang.Float> mutableCopyWithCapacity2(int i);

        float setFloat(int i, float f);
    }

    public interface IntList extends com.android.framework.protobuf.Internal.ProtobufList<java.lang.Integer> {
        void addInt(int i);

        int getInt(int i);

        @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
        /* renamed from: mutableCopyWithCapacity */
        com.android.framework.protobuf.Internal.ProtobufList<java.lang.Integer> mutableCopyWithCapacity2(int i);

        int setInt(int i, int i2);
    }

    public interface LongList extends com.android.framework.protobuf.Internal.ProtobufList<java.lang.Long> {
        void addLong(long j);

        long getLong(int i);

        @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
        /* renamed from: mutableCopyWithCapacity */
        com.android.framework.protobuf.Internal.ProtobufList<java.lang.Long> mutableCopyWithCapacity2(int i);

        long setLong(int i, long j);
    }

    public interface ProtobufList<E> extends java.util.List<E>, java.util.RandomAccess {
        boolean isModifiable();

        void makeImmutable();

        /* renamed from: mutableCopyWithCapacity */
        com.android.framework.protobuf.Internal.ProtobufList<E> mutableCopyWithCapacity2(int i);
    }

    private Internal() {
    }

    static <T> T checkNotNull(T t) {
        if (t == null) {
            throw new java.lang.NullPointerException();
        }
        return t;
    }

    static <T> T checkNotNull(T t, java.lang.String str) {
        if (t == null) {
            throw new java.lang.NullPointerException(str);
        }
        return t;
    }

    public static java.lang.String stringDefaultValue(java.lang.String str) {
        return new java.lang.String(str.getBytes(ISO_8859_1), UTF_8);
    }

    public static com.android.framework.protobuf.ByteString bytesDefaultValue(java.lang.String str) {
        return com.android.framework.protobuf.ByteString.copyFrom(str.getBytes(ISO_8859_1));
    }

    public static byte[] byteArrayDefaultValue(java.lang.String str) {
        return str.getBytes(ISO_8859_1);
    }

    public static java.nio.ByteBuffer byteBufferDefaultValue(java.lang.String str) {
        return java.nio.ByteBuffer.wrap(byteArrayDefaultValue(str));
    }

    public static java.nio.ByteBuffer copyByteBuffer(java.nio.ByteBuffer byteBuffer) {
        java.nio.ByteBuffer duplicate = byteBuffer.duplicate();
        duplicate.clear();
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(duplicate.capacity());
        allocate.put(duplicate);
        allocate.clear();
        return allocate;
    }

    public static boolean isValidUtf8(com.android.framework.protobuf.ByteString byteString) {
        return byteString.isValidUtf8();
    }

    public static boolean isValidUtf8(byte[] bArr) {
        return com.android.framework.protobuf.Utf8.isValidUtf8(bArr);
    }

    public static byte[] toByteArray(java.lang.String str) {
        return str.getBytes(UTF_8);
    }

    public static java.lang.String toStringUtf8(byte[] bArr) {
        return new java.lang.String(bArr, UTF_8);
    }

    public static int hashLong(long j) {
        return (int) (j ^ (j >>> 32));
    }

    public static int hashBoolean(boolean z) {
        return z ? com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_SERVICE_DISABLED_APP : com.android.internal.logging.nano.MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT;
    }

    public static int hashEnum(com.android.framework.protobuf.Internal.EnumLite enumLite) {
        return enumLite.getNumber();
    }

    public static int hashEnumList(java.util.List<? extends com.android.framework.protobuf.Internal.EnumLite> list) {
        java.util.Iterator<? extends com.android.framework.protobuf.Internal.EnumLite> it = list.iterator();
        int i = 1;
        while (it.hasNext()) {
            i = (i * 31) + hashEnum(it.next());
        }
        return i;
    }

    public static boolean equals(java.util.List<byte[]> list, java.util.List<byte[]> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!java.util.Arrays.equals(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static int hashCode(java.util.List<byte[]> list) {
        java.util.Iterator<byte[]> it = list.iterator();
        int i = 1;
        while (it.hasNext()) {
            i = (i * 31) + hashCode(it.next());
        }
        return i;
    }

    public static int hashCode(byte[] bArr) {
        return hashCode(bArr, 0, bArr.length);
    }

    static int hashCode(byte[] bArr, int i, int i2) {
        int partialHash = partialHash(i2, bArr, i, i2);
        if (partialHash == 0) {
            return 1;
        }
        return partialHash;
    }

    static int partialHash(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    public static boolean equalsByteBuffer(java.nio.ByteBuffer byteBuffer, java.nio.ByteBuffer byteBuffer2) {
        if (byteBuffer.capacity() != byteBuffer2.capacity()) {
            return false;
        }
        return byteBuffer.duplicate().clear().equals(byteBuffer2.duplicate().clear());
    }

    public static boolean equalsByteBuffer(java.util.List<java.nio.ByteBuffer> list, java.util.List<java.nio.ByteBuffer> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!equalsByteBuffer(list.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static int hashCodeByteBuffer(java.util.List<java.nio.ByteBuffer> list) {
        java.util.Iterator<java.nio.ByteBuffer> it = list.iterator();
        int i = 1;
        while (it.hasNext()) {
            i = (i * 31) + hashCodeByteBuffer(it.next());
        }
        return i;
    }

    public static int hashCodeByteBuffer(java.nio.ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            int partialHash = partialHash(byteBuffer.capacity(), byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
            if (partialHash == 0) {
                return 1;
            }
            return partialHash;
        }
        int capacity = byteBuffer.capacity() <= 4096 ? byteBuffer.capacity() : 4096;
        byte[] bArr = new byte[capacity];
        java.nio.ByteBuffer duplicate = byteBuffer.duplicate();
        duplicate.clear();
        int capacity2 = byteBuffer.capacity();
        while (duplicate.remaining() > 0) {
            int remaining = duplicate.remaining() <= capacity ? duplicate.remaining() : capacity;
            duplicate.get(bArr, 0, remaining);
            capacity2 = partialHash(capacity2, bArr, 0, remaining);
        }
        if (capacity2 == 0) {
            return 1;
        }
        return capacity2;
    }

    public static <T extends com.android.framework.protobuf.MessageLite> T getDefaultInstance(java.lang.Class<T> cls) {
        try {
            java.lang.reflect.Method method = cls.getMethod("getDefaultInstance", new java.lang.Class[0]);
            return (T) method.invoke(method, new java.lang.Object[0]);
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException("Failed to get default instance for " + cls, e);
        }
    }

    static java.lang.Object mergeMessage(java.lang.Object obj, java.lang.Object obj2) {
        return ((com.android.framework.protobuf.MessageLite) obj).toBuilder().mergeFrom((com.android.framework.protobuf.MessageLite) obj2).buildPartial();
    }

    public static class ListAdapter<F, T> extends java.util.AbstractList<T> {
        private final com.android.framework.protobuf.Internal.ListAdapter.Converter<F, T> converter;
        private final java.util.List<F> fromList;

        public interface Converter<F, T> {
            T convert(F f);
        }

        public ListAdapter(java.util.List<F> list, com.android.framework.protobuf.Internal.ListAdapter.Converter<F, T> converter) {
            this.fromList = list;
            this.converter = converter;
        }

        @Override // java.util.AbstractList, java.util.List
        public T get(int i) {
            return (T) this.converter.convert(this.fromList.get(i));
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.fromList.size();
        }
    }

    public static class MapAdapter<K, V, RealValue> extends java.util.AbstractMap<K, V> {
        private final java.util.Map<K, RealValue> realMap;
        private final com.android.framework.protobuf.Internal.MapAdapter.Converter<RealValue, V> valueConverter;

        public interface Converter<A, B> {
            A doBackward(B b);

            B doForward(A a);
        }

        public static <T extends com.android.framework.protobuf.Internal.EnumLite> com.android.framework.protobuf.Internal.MapAdapter.Converter<java.lang.Integer, T> newEnumConverter(final com.android.framework.protobuf.Internal.EnumLiteMap<T> enumLiteMap, final T t) {
            return (com.android.framework.protobuf.Internal.MapAdapter.Converter<java.lang.Integer, T>) new com.android.framework.protobuf.Internal.MapAdapter.Converter<java.lang.Integer, T>() { // from class: com.android.framework.protobuf.Internal.MapAdapter.1
                /* JADX WARN: Incorrect return type in method signature: (Ljava/lang/Integer;)TT; */
                @Override // com.android.framework.protobuf.Internal.MapAdapter.Converter
                public com.android.framework.protobuf.Internal.EnumLite doForward(java.lang.Integer num) {
                    com.android.framework.protobuf.Internal.EnumLite findValueByNumber = com.android.framework.protobuf.Internal.EnumLiteMap.this.findValueByNumber(num.intValue());
                    return findValueByNumber == null ? t : findValueByNumber;
                }

                /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/Integer; */
                @Override // com.android.framework.protobuf.Internal.MapAdapter.Converter
                public java.lang.Integer doBackward(com.android.framework.protobuf.Internal.EnumLite enumLite) {
                    return java.lang.Integer.valueOf(enumLite.getNumber());
                }
            };
        }

        public MapAdapter(java.util.Map<K, RealValue> map, com.android.framework.protobuf.Internal.MapAdapter.Converter<RealValue, V> converter) {
            this.realMap = map;
            this.valueConverter = converter;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V get(java.lang.Object obj) {
            RealValue realvalue = this.realMap.get(obj);
            if (realvalue == null) {
                return null;
            }
            return this.valueConverter.doForward(realvalue);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public V put(K k, V v) {
            java.lang.Object put = this.realMap.put(k, this.valueConverter.doBackward(v));
            if (put == null) {
                return null;
            }
            return (V) this.valueConverter.doForward(put);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
            return new com.android.framework.protobuf.Internal.MapAdapter.SetAdapter(this.realMap.entrySet());
        }

        private class SetAdapter extends java.util.AbstractSet<java.util.Map.Entry<K, V>> {
            private final java.util.Set<java.util.Map.Entry<K, RealValue>> realSet;

            public SetAdapter(java.util.Set<java.util.Map.Entry<K, RealValue>> set) {
                this.realSet = set;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public java.util.Iterator<java.util.Map.Entry<K, V>> iterator() {
                return new com.android.framework.protobuf.Internal.MapAdapter.IteratorAdapter(this.realSet.iterator());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return this.realSet.size();
            }
        }

        private class IteratorAdapter implements java.util.Iterator<java.util.Map.Entry<K, V>> {
            private final java.util.Iterator<java.util.Map.Entry<K, RealValue>> realIterator;

            public IteratorAdapter(java.util.Iterator<java.util.Map.Entry<K, RealValue>> it) {
                this.realIterator = it;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.realIterator.hasNext();
            }

            @Override // java.util.Iterator
            public java.util.Map.Entry<K, V> next() {
                return new com.android.framework.protobuf.Internal.MapAdapter.EntryAdapter(this.realIterator.next());
            }

            @Override // java.util.Iterator
            public void remove() {
                this.realIterator.remove();
            }
        }

        private class EntryAdapter implements java.util.Map.Entry<K, V> {
            private final java.util.Map.Entry<K, RealValue> realEntry;

            public EntryAdapter(java.util.Map.Entry<K, RealValue> entry) {
                this.realEntry = entry;
            }

            @Override // java.util.Map.Entry
            public K getKey() {
                return this.realEntry.getKey();
            }

            @Override // java.util.Map.Entry
            public V getValue() {
                return (V) com.android.framework.protobuf.Internal.MapAdapter.this.valueConverter.doForward(this.realEntry.getValue());
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Map.Entry
            public V setValue(V v) {
                java.lang.Object value = this.realEntry.setValue(com.android.framework.protobuf.Internal.MapAdapter.this.valueConverter.doBackward(v));
                if (value != null) {
                    return (V) com.android.framework.protobuf.Internal.MapAdapter.this.valueConverter.doForward(value);
                }
                return null;
            }

            @Override // java.util.Map.Entry
            public boolean equals(java.lang.Object obj) {
                if (obj == this) {
                    return true;
                }
                if (obj instanceof java.util.Map.Entry) {
                    return getKey().equals(((java.util.Map.Entry) obj).getKey()) && getValue().equals(getValue());
                }
                return false;
            }

            @Override // java.util.Map.Entry
            public int hashCode() {
                return this.realEntry.hashCode();
            }
        }
    }
}
