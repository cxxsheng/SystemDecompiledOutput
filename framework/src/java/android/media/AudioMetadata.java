package android.media;

/* loaded from: classes2.dex */
public final class AudioMetadata {
    private static final int AUDIO_METADATA_OBJ_TYPE_BASEMAP = 6;
    private static final int AUDIO_METADATA_OBJ_TYPE_DOUBLE = 4;
    private static final int AUDIO_METADATA_OBJ_TYPE_FLOAT = 3;
    private static final int AUDIO_METADATA_OBJ_TYPE_INT = 1;
    private static final int AUDIO_METADATA_OBJ_TYPE_LONG = 2;
    private static final int AUDIO_METADATA_OBJ_TYPE_NONE = 0;
    private static final int AUDIO_METADATA_OBJ_TYPE_STRING = 5;
    private static final java.lang.String TAG = "AudioMetadata";
    private static final java.util.Map<java.lang.Class, java.lang.Integer> AUDIO_METADATA_OBJ_TYPES = java.util.Map.of(java.lang.Integer.class, 1, java.lang.Long.class, 2, java.lang.Float.class, 3, java.lang.Double.class, 4, java.lang.String.class, 5, android.media.AudioMetadata.BaseMap.class, 6);
    private static final java.nio.charset.Charset AUDIO_METADATA_CHARSET = java.nio.charset.StandardCharsets.UTF_8;
    private static final java.util.Map<java.lang.Integer, android.media.AudioMetadata.DataPackage<?>> DATA_PACKAGES = java.util.Map.of(1, new android.media.AudioMetadata.DataPackage<java.lang.Integer>() { // from class: android.media.AudioMetadata.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.media.AudioMetadata.DataPackage
        public java.lang.Integer unpack(java.nio.ByteBuffer byteBuffer) {
            return java.lang.Integer.valueOf(byteBuffer.getInt());
        }

        @Override // android.media.AudioMetadata.DataPackage
        public boolean pack(android.media.AudioMetadata.AutoGrowByteBuffer autoGrowByteBuffer, java.lang.Integer num) {
            autoGrowByteBuffer.putInt(num.intValue());
            return true;
        }
    }, 2, new android.media.AudioMetadata.DataPackage<java.lang.Long>() { // from class: android.media.AudioMetadata.3
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.media.AudioMetadata.DataPackage
        public java.lang.Long unpack(java.nio.ByteBuffer byteBuffer) {
            return java.lang.Long.valueOf(byteBuffer.getLong());
        }

        @Override // android.media.AudioMetadata.DataPackage
        public boolean pack(android.media.AudioMetadata.AutoGrowByteBuffer autoGrowByteBuffer, java.lang.Long l) {
            autoGrowByteBuffer.putLong(l.longValue());
            return true;
        }
    }, 3, new android.media.AudioMetadata.DataPackage<java.lang.Float>() { // from class: android.media.AudioMetadata.4
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.media.AudioMetadata.DataPackage
        public java.lang.Float unpack(java.nio.ByteBuffer byteBuffer) {
            return java.lang.Float.valueOf(byteBuffer.getFloat());
        }

        @Override // android.media.AudioMetadata.DataPackage
        public boolean pack(android.media.AudioMetadata.AutoGrowByteBuffer autoGrowByteBuffer, java.lang.Float f) {
            autoGrowByteBuffer.putFloat(f.floatValue());
            return true;
        }
    }, 4, new android.media.AudioMetadata.DataPackage<java.lang.Double>() { // from class: android.media.AudioMetadata.5
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.media.AudioMetadata.DataPackage
        public java.lang.Double unpack(java.nio.ByteBuffer byteBuffer) {
            return java.lang.Double.valueOf(byteBuffer.getDouble());
        }

        @Override // android.media.AudioMetadata.DataPackage
        public boolean pack(android.media.AudioMetadata.AutoGrowByteBuffer autoGrowByteBuffer, java.lang.Double d) {
            autoGrowByteBuffer.putDouble(d.doubleValue());
            return true;
        }
    }, 5, new android.media.AudioMetadata.DataPackage<java.lang.String>() { // from class: android.media.AudioMetadata.6
        @Override // android.media.AudioMetadata.DataPackage
        public java.lang.String unpack(java.nio.ByteBuffer byteBuffer) {
            int i = byteBuffer.getInt();
            if (byteBuffer.position() + i > byteBuffer.limit()) {
                return null;
            }
            byte[] bArr = new byte[i];
            byteBuffer.get(bArr);
            return new java.lang.String(bArr, android.media.AudioMetadata.AUDIO_METADATA_CHARSET);
        }

        @Override // android.media.AudioMetadata.DataPackage
        public boolean pack(android.media.AudioMetadata.AutoGrowByteBuffer autoGrowByteBuffer, java.lang.String str) {
            byte[] bytes = str.getBytes(android.media.AudioMetadata.AUDIO_METADATA_CHARSET);
            autoGrowByteBuffer.putInt(bytes.length);
            autoGrowByteBuffer.put(bytes);
            return true;
        }
    }, 6, new android.media.AudioMetadata.BaseMapPackage());
    private static final android.media.AudioMetadata.ObjectPackage OBJECT_PACKAGE = new android.media.AudioMetadata.ObjectPackage();

    public interface Key<T> {
        java.lang.String getName();

        java.lang.Class<T> getValueClass();
    }

    public static android.media.AudioMetadataMap createMap() {
        return new android.media.AudioMetadata.BaseMap();
    }

    public static class Format {
        public static final android.media.AudioMetadata.Key<java.lang.Integer> KEY_BIT_RATE = android.media.AudioMetadata.createKey(android.media.MediaFormat.KEY_BIT_RATE, java.lang.Integer.class);
        public static final android.media.AudioMetadata.Key<java.lang.Integer> KEY_CHANNEL_MASK = android.media.AudioMetadata.createKey(android.media.MediaFormat.KEY_CHANNEL_MASK, java.lang.Integer.class);
        public static final android.media.AudioMetadata.Key<java.lang.String> KEY_MIME = android.media.AudioMetadata.createKey(android.media.MediaFormat.KEY_MIME, java.lang.String.class);
        public static final android.media.AudioMetadata.Key<java.lang.Integer> KEY_SAMPLE_RATE = android.media.AudioMetadata.createKey(android.media.MediaFormat.KEY_SAMPLE_RATE, java.lang.Integer.class);
        public static final android.media.AudioMetadata.Key<java.lang.Integer> KEY_BIT_WIDTH = android.media.AudioMetadata.createKey("bit-width", java.lang.Integer.class);
        public static final android.media.AudioMetadata.Key<java.lang.Boolean> KEY_ATMOS_PRESENT = android.media.AudioMetadata.createKey("atmos-present", java.lang.Boolean.class);
        public static final android.media.AudioMetadata.Key<java.lang.Integer> KEY_HAS_ATMOS = android.media.AudioMetadata.createKey("has-atmos", java.lang.Integer.class);
        public static final android.media.AudioMetadata.Key<java.lang.Integer> KEY_AUDIO_ENCODING = android.media.AudioMetadata.createKey("audio-encoding", java.lang.Integer.class);
        public static final android.media.AudioMetadata.Key<java.lang.Integer> KEY_PRESENTATION_ID = android.media.AudioMetadata.createKey("presentation-id", java.lang.Integer.class);
        public static final android.media.AudioMetadata.Key<java.lang.Integer> KEY_PROGRAM_ID = android.media.AudioMetadata.createKey("program-id", java.lang.Integer.class);
        public static final android.media.AudioMetadata.Key<java.lang.Integer> KEY_PRESENTATION_CONTENT_CLASSIFIER = android.media.AudioMetadata.createKey("presentation-content-classifier", java.lang.Integer.class);
        public static final android.media.AudioMetadata.Key<java.lang.String> KEY_PRESENTATION_LANGUAGE = android.media.AudioMetadata.createKey("presentation-language", java.lang.String.class);

        private Format() {
        }
    }

    public static <T> android.media.AudioMetadata.Key<T> createKey(final java.lang.String str, final java.lang.Class<T> cls) {
        return new android.media.AudioMetadata.Key<T>() { // from class: android.media.AudioMetadata.1
            private final java.lang.String mName;
            private final java.lang.Class<T> mType;

            {
                this.mName = str;
                this.mType = cls;
            }

            @Override // android.media.AudioMetadata.Key
            public java.lang.String getName() {
                return this.mName;
            }

            @Override // android.media.AudioMetadata.Key
            public java.lang.Class<T> getValueClass() {
                return this.mType;
            }

            public boolean equals(java.lang.Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof android.media.AudioMetadata.Key)) {
                    return false;
                }
                android.media.AudioMetadata.Key key = (android.media.AudioMetadata.Key) obj;
                return this.mName.equals(key.getName()) && this.mType.equals(key.getValueClass());
            }

            public int hashCode() {
                return java.util.Objects.hash(this.mName, this.mType);
            }
        };
    }

    public static class BaseMap implements android.media.AudioMetadataMap {
        private final java.util.HashMap<android.util.Pair<java.lang.String, java.lang.Class<?>>, android.util.Pair<android.media.AudioMetadata.Key<?>, java.lang.Object>> mHashMap = new java.util.HashMap<>();

        @Override // android.media.AudioMetadataReadMap
        public <T> boolean containsKey(android.media.AudioMetadata.Key<T> key) {
            return this.mHashMap.get(pairFromKey(key)) != null;
        }

        @Override // android.media.AudioMetadataReadMap
        public android.media.AudioMetadataMap dup() {
            android.media.AudioMetadata.BaseMap baseMap = new android.media.AudioMetadata.BaseMap();
            baseMap.mHashMap.putAll(this.mHashMap);
            return baseMap;
        }

        @Override // android.media.AudioMetadataReadMap
        public <T> T get(android.media.AudioMetadata.Key<T> key) {
            return (T) getValueFromValuePair(this.mHashMap.get(pairFromKey(key)));
        }

        @Override // android.media.AudioMetadataReadMap
        public java.util.Set<android.media.AudioMetadata.Key<?>> keySet() {
            java.util.HashSet hashSet = new java.util.HashSet();
            java.util.Iterator<android.util.Pair<android.media.AudioMetadata.Key<?>, java.lang.Object>> it = this.mHashMap.values().iterator();
            while (it.hasNext()) {
                hashSet.add(it.next().first);
            }
            return hashSet;
        }

        @Override // android.media.AudioMetadataMap
        public <T> T remove(android.media.AudioMetadata.Key<T> key) {
            return (T) getValueFromValuePair(this.mHashMap.remove(pairFromKey(key)));
        }

        @Override // android.media.AudioMetadataMap
        public <T> T set(android.media.AudioMetadata.Key<T> key, T t) {
            java.util.Objects.requireNonNull(t);
            return (T) getValueFromValuePair(this.mHashMap.put(pairFromKey(key), new android.util.Pair<>(key, t)));
        }

        @Override // android.media.AudioMetadataReadMap
        public int size() {
            return this.mHashMap.size();
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof android.media.AudioMetadata.BaseMap)) {
                return false;
            }
            return this.mHashMap.equals(((android.media.AudioMetadata.BaseMap) obj).mHashMap);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mHashMap);
        }

        private static <T> android.util.Pair<java.lang.String, java.lang.Class<?>> pairFromKey(android.media.AudioMetadata.Key<T> key) {
            java.util.Objects.requireNonNull(key);
            return new android.util.Pair<>(key.getName(), key.getValueClass());
        }

        private static java.lang.Object getValueFromValuePair(android.util.Pair<android.media.AudioMetadata.Key<?>, java.lang.Object> pair) {
            if (pair == null) {
                return null;
            }
            return pair.second;
        }
    }

    private static class AutoGrowByteBuffer {
        private static final int DOUBLE_BYTE_COUNT = 8;
        private static final int FLOAT_BYTE_COUNT = 4;
        private static final int INTEGER_BYTE_COUNT = 4;
        private static final int LONG_BYTE_COUNT = 8;
        private java.nio.ByteBuffer mBuffer;

        AutoGrowByteBuffer() {
            this(1024);
        }

        AutoGrowByteBuffer(int i) {
            this.mBuffer = java.nio.ByteBuffer.allocateDirect(i);
        }

        public java.nio.ByteBuffer getRawByteBuffer() {
            int limit = this.mBuffer.limit();
            int position = this.mBuffer.position();
            this.mBuffer.limit(position);
            this.mBuffer.position(0);
            java.nio.ByteBuffer slice = this.mBuffer.slice();
            this.mBuffer.limit(limit);
            this.mBuffer.position(position);
            return slice;
        }

        public java.nio.ByteOrder order() {
            return this.mBuffer.order();
        }

        public int position() {
            return this.mBuffer.position();
        }

        public android.media.AudioMetadata.AutoGrowByteBuffer position(int i) {
            this.mBuffer.position(i);
            return this;
        }

        public android.media.AudioMetadata.AutoGrowByteBuffer order(java.nio.ByteOrder byteOrder) {
            this.mBuffer.order(byteOrder);
            return this;
        }

        public android.media.AudioMetadata.AutoGrowByteBuffer putInt(int i) {
            ensureCapacity(4);
            this.mBuffer.putInt(i);
            return this;
        }

        public android.media.AudioMetadata.AutoGrowByteBuffer putLong(long j) {
            ensureCapacity(8);
            this.mBuffer.putLong(j);
            return this;
        }

        public android.media.AudioMetadata.AutoGrowByteBuffer putFloat(float f) {
            ensureCapacity(4);
            this.mBuffer.putFloat(f);
            return this;
        }

        public android.media.AudioMetadata.AutoGrowByteBuffer putDouble(double d) {
            ensureCapacity(8);
            this.mBuffer.putDouble(d);
            return this;
        }

        public android.media.AudioMetadata.AutoGrowByteBuffer put(byte[] bArr) {
            ensureCapacity(bArr.length);
            this.mBuffer.put(bArr);
            return this;
        }

        private void ensureCapacity(int i) {
            if (this.mBuffer.remaining() < i) {
                int position = this.mBuffer.position() + i;
                if (position > 1073741823) {
                    throw new java.lang.IllegalStateException("Item memory requirements too large: " + position);
                }
                java.nio.ByteBuffer allocateDirect = java.nio.ByteBuffer.allocateDirect(position << 1);
                allocateDirect.order(this.mBuffer.order());
                this.mBuffer.flip();
                allocateDirect.put(this.mBuffer);
                this.mBuffer = allocateDirect;
            }
        }
    }

    private interface DataPackage<T> {
        boolean pack(android.media.AudioMetadata.AutoGrowByteBuffer autoGrowByteBuffer, T t);

        T unpack(java.nio.ByteBuffer byteBuffer);

        default java.lang.Class getMyType() {
            return (java.lang.Class) ((java.lang.reflect.ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        }
    }

    private static class ObjectPackage implements android.media.AudioMetadata.DataPackage<android.util.Pair<java.lang.Class, java.lang.Object>> {
        private ObjectPackage() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.media.AudioMetadata.DataPackage
        public android.util.Pair<java.lang.Class, java.lang.Object> unpack(java.nio.ByteBuffer byteBuffer) {
            int i = byteBuffer.getInt();
            android.media.AudioMetadata.DataPackage dataPackage = (android.media.AudioMetadata.DataPackage) android.media.AudioMetadata.DATA_PACKAGES.get(java.lang.Integer.valueOf(i));
            if (dataPackage == null) {
                android.util.Log.e(android.media.AudioMetadata.TAG, "Cannot find DataPackage for type:" + i);
                return null;
            }
            int i2 = byteBuffer.getInt();
            int position = byteBuffer.position();
            java.lang.Object unpack = dataPackage.unpack(byteBuffer);
            if (byteBuffer.position() - position != i2) {
                android.util.Log.e(android.media.AudioMetadata.TAG, "Broken data package");
                return null;
            }
            return new android.util.Pair<>(dataPackage.getMyType(), unpack);
        }

        @Override // android.media.AudioMetadata.DataPackage
        public boolean pack(android.media.AudioMetadata.AutoGrowByteBuffer autoGrowByteBuffer, android.util.Pair<java.lang.Class, java.lang.Object> pair) {
            java.lang.Integer num = (java.lang.Integer) android.media.AudioMetadata.AUDIO_METADATA_OBJ_TYPES.get(pair.first);
            if (num == null) {
                android.util.Log.e(android.media.AudioMetadata.TAG, "Cannot find data type for " + pair.first);
                return false;
            }
            android.media.AudioMetadata.DataPackage dataPackage = (android.media.AudioMetadata.DataPackage) android.media.AudioMetadata.DATA_PACKAGES.get(num);
            if (dataPackage == null) {
                android.util.Log.e(android.media.AudioMetadata.TAG, "Cannot find DataPackage for type:" + num);
                return false;
            }
            autoGrowByteBuffer.putInt(num.intValue());
            int position = autoGrowByteBuffer.position();
            autoGrowByteBuffer.putInt(0);
            int position2 = autoGrowByteBuffer.position();
            if (!dataPackage.pack(autoGrowByteBuffer, pair.second)) {
                android.util.Log.i(android.media.AudioMetadata.TAG, "Failed to pack object: " + pair.second);
                return false;
            }
            int position3 = autoGrowByteBuffer.position();
            autoGrowByteBuffer.position(position);
            autoGrowByteBuffer.putInt(position3 - position2);
            autoGrowByteBuffer.position(position3);
            return true;
        }
    }

    private static class BaseMapPackage implements android.media.AudioMetadata.DataPackage<android.media.AudioMetadata.BaseMap> {
        private BaseMapPackage() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.media.AudioMetadata.DataPackage
        public android.media.AudioMetadata.BaseMap unpack(java.nio.ByteBuffer byteBuffer) {
            android.media.AudioMetadata.BaseMap baseMap = new android.media.AudioMetadata.BaseMap();
            int i = byteBuffer.getInt();
            android.media.AudioMetadata.DataPackage dataPackage = (android.media.AudioMetadata.DataPackage) android.media.AudioMetadata.DATA_PACKAGES.get(5);
            if (dataPackage == null) {
                android.util.Log.e(android.media.AudioMetadata.TAG, "Cannot find DataPackage for String");
                return null;
            }
            for (int i2 = 0; i2 < i; i2++) {
                java.lang.String str = (java.lang.String) dataPackage.unpack(byteBuffer);
                if (str == null) {
                    android.util.Log.e(android.media.AudioMetadata.TAG, "Failed to unpack key for map");
                    return null;
                }
                android.util.Pair<java.lang.Class, java.lang.Object> unpack = android.media.AudioMetadata.OBJECT_PACKAGE.unpack(byteBuffer);
                if (unpack == null) {
                    android.util.Log.e(android.media.AudioMetadata.TAG, "Failed to unpack value for map");
                    return null;
                }
                if (str.equals(android.media.AudioMetadata.Format.KEY_HAS_ATMOS.getName()) && unpack.first == android.media.AudioMetadata.Format.KEY_HAS_ATMOS.getValueClass()) {
                    baseMap.set(android.media.AudioMetadata.Format.KEY_ATMOS_PRESENT, java.lang.Boolean.valueOf(((java.lang.Integer) unpack.second).intValue() != 0));
                } else {
                    baseMap.set(android.media.AudioMetadata.createKey(str, unpack.first), unpack.first.cast(unpack.second));
                }
            }
            return baseMap;
        }

        @Override // android.media.AudioMetadata.DataPackage
        public boolean pack(android.media.AudioMetadata.AutoGrowByteBuffer autoGrowByteBuffer, android.media.AudioMetadata.BaseMap baseMap) {
            autoGrowByteBuffer.putInt(baseMap.size());
            android.media.AudioMetadata.DataPackage dataPackage = (android.media.AudioMetadata.DataPackage) android.media.AudioMetadata.DATA_PACKAGES.get(5);
            if (dataPackage == null) {
                android.util.Log.e(android.media.AudioMetadata.TAG, "Cannot find DataPackage for String");
                return false;
            }
            java.util.Iterator<android.media.AudioMetadata.Key<?>> it = baseMap.keySet().iterator();
            while (it.hasNext()) {
                android.media.AudioMetadata.Key<?> next = it.next();
                java.lang.Object obj = baseMap.get(next);
                if (next == android.media.AudioMetadata.Format.KEY_ATMOS_PRESENT) {
                    next = android.media.AudioMetadata.Format.KEY_HAS_ATMOS;
                    obj = java.lang.Integer.valueOf(((java.lang.Boolean) obj).booleanValue() ? 1 : 0);
                }
                if (!dataPackage.pack(autoGrowByteBuffer, next.getName())) {
                    android.util.Log.i(android.media.AudioMetadata.TAG, "Failed to pack key: " + next.getName());
                    return false;
                }
                if (!android.media.AudioMetadata.OBJECT_PACKAGE.pack(autoGrowByteBuffer, new android.util.Pair<>(next.getValueClass(), obj))) {
                    android.util.Log.i(android.media.AudioMetadata.TAG, "Failed to pack value: " + baseMap.get(next));
                    return false;
                }
            }
            return true;
        }
    }

    public static android.media.AudioMetadata.BaseMap fromByteBuffer(java.nio.ByteBuffer byteBuffer) {
        android.media.AudioMetadata.DataPackage<?> dataPackage = DATA_PACKAGES.get(6);
        if (dataPackage == null) {
            android.util.Log.e(TAG, "Cannot find DataPackage for BaseMap");
            return null;
        }
        try {
            return (android.media.AudioMetadata.BaseMap) dataPackage.unpack(byteBuffer);
        } catch (java.nio.BufferUnderflowException e) {
            android.util.Log.e(TAG, "No enough data to unpack");
            return null;
        }
    }

    public static java.nio.ByteBuffer toByteBuffer(android.media.AudioMetadata.BaseMap baseMap, java.nio.ByteOrder byteOrder) {
        android.media.AudioMetadata.DataPackage<?> dataPackage = DATA_PACKAGES.get(6);
        if (dataPackage == null) {
            android.util.Log.e(TAG, "Cannot find DataPackage for BaseMap");
            return null;
        }
        android.media.AudioMetadata.AutoGrowByteBuffer autoGrowByteBuffer = new android.media.AudioMetadata.AutoGrowByteBuffer();
        autoGrowByteBuffer.order(byteOrder);
        if (!dataPackage.pack(autoGrowByteBuffer, baseMap)) {
            return null;
        }
        return autoGrowByteBuffer.getRawByteBuffer();
    }

    private AudioMetadata() {
    }
}
