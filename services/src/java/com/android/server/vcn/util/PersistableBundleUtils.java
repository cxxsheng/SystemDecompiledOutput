package com.android.server.vcn.util;

/* loaded from: classes2.dex */
public class PersistableBundleUtils {
    private static final java.lang.String BYTE_ARRAY_KEY = "BYTE_ARRAY_KEY";
    private static final java.lang.String COLLECTION_SIZE_KEY = "COLLECTION_LENGTH";
    private static final java.lang.String INTEGER_KEY = "INTEGER_KEY";
    private static final java.lang.String LIST_KEY_FORMAT = "LIST_ITEM_%d";
    private static final java.lang.String MAP_KEY_FORMAT = "MAP_KEY_%d";
    private static final java.lang.String MAP_VALUE_FORMAT = "MAP_VALUE_%d";
    private static final java.lang.String PARCEL_UUID_KEY = "PARCEL_UUID";
    private static final java.lang.String STRING_KEY = "STRING_KEY";
    public static final com.android.server.vcn.util.PersistableBundleUtils.Serializer<java.lang.Integer> INTEGER_SERIALIZER = new com.android.server.vcn.util.PersistableBundleUtils.Serializer() { // from class: com.android.server.vcn.util.PersistableBundleUtils$$ExternalSyntheticLambda0
        @Override // com.android.server.vcn.util.PersistableBundleUtils.Serializer
        public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
            android.os.PersistableBundle lambda$static$0;
            lambda$static$0 = com.android.server.vcn.util.PersistableBundleUtils.lambda$static$0((java.lang.Integer) obj);
            return lambda$static$0;
        }
    };
    public static final com.android.server.vcn.util.PersistableBundleUtils.Deserializer<java.lang.Integer> INTEGER_DESERIALIZER = new com.android.server.vcn.util.PersistableBundleUtils.Deserializer() { // from class: com.android.server.vcn.util.PersistableBundleUtils$$ExternalSyntheticLambda1
        @Override // com.android.server.vcn.util.PersistableBundleUtils.Deserializer
        public final java.lang.Object fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
            java.lang.Integer lambda$static$1;
            lambda$static$1 = com.android.server.vcn.util.PersistableBundleUtils.lambda$static$1(persistableBundle);
            return lambda$static$1;
        }
    };
    public static final com.android.server.vcn.util.PersistableBundleUtils.Serializer<java.lang.String> STRING_SERIALIZER = new com.android.server.vcn.util.PersistableBundleUtils.Serializer() { // from class: com.android.server.vcn.util.PersistableBundleUtils$$ExternalSyntheticLambda2
        @Override // com.android.server.vcn.util.PersistableBundleUtils.Serializer
        public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
            android.os.PersistableBundle lambda$static$2;
            lambda$static$2 = com.android.server.vcn.util.PersistableBundleUtils.lambda$static$2((java.lang.String) obj);
            return lambda$static$2;
        }
    };
    public static final com.android.server.vcn.util.PersistableBundleUtils.Deserializer<java.lang.String> STRING_DESERIALIZER = new com.android.server.vcn.util.PersistableBundleUtils.Deserializer() { // from class: com.android.server.vcn.util.PersistableBundleUtils$$ExternalSyntheticLambda3
        @Override // com.android.server.vcn.util.PersistableBundleUtils.Deserializer
        public final java.lang.Object fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
            java.lang.String lambda$static$3;
            lambda$static$3 = com.android.server.vcn.util.PersistableBundleUtils.lambda$static$3(persistableBundle);
            return lambda$static$3;
        }
    };

    public interface Deserializer<T> {
        T fromPersistableBundle(android.os.PersistableBundle persistableBundle);
    }

    public interface Serializer<T> {
        android.os.PersistableBundle toPersistableBundle(T t);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.os.PersistableBundle lambda$static$0(java.lang.Integer num) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putInt(INTEGER_KEY, num.intValue());
        return persistableBundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$static$1(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
        return java.lang.Integer.valueOf(persistableBundle.getInt(INTEGER_KEY));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.os.PersistableBundle lambda$static$2(java.lang.String str) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putString(STRING_KEY, str);
        return persistableBundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$static$3(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
        return persistableBundle.getString(STRING_KEY);
    }

    public static android.os.PersistableBundle fromParcelUuid(android.os.ParcelUuid parcelUuid) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putString(PARCEL_UUID_KEY, parcelUuid.toString());
        return persistableBundle;
    }

    public static android.os.ParcelUuid toParcelUuid(android.os.PersistableBundle persistableBundle) {
        return android.os.ParcelUuid.fromString(persistableBundle.getString(PARCEL_UUID_KEY));
    }

    @android.annotation.NonNull
    public static <T> android.os.PersistableBundle fromList(@android.annotation.NonNull java.util.List<T> list, @android.annotation.NonNull com.android.server.vcn.util.PersistableBundleUtils.Serializer<T> serializer) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putInt(COLLECTION_SIZE_KEY, list.size());
        for (int i = 0; i < list.size(); i++) {
            persistableBundle.putPersistableBundle(java.lang.String.format(LIST_KEY_FORMAT, java.lang.Integer.valueOf(i)), serializer.toPersistableBundle(list.get(i)));
        }
        return persistableBundle;
    }

    @android.annotation.NonNull
    public static <T> java.util.List<T> toList(@android.annotation.NonNull android.os.PersistableBundle persistableBundle, @android.annotation.NonNull com.android.server.vcn.util.PersistableBundleUtils.Deserializer<T> deserializer) {
        int i = persistableBundle.getInt(COLLECTION_SIZE_KEY);
        java.util.ArrayList arrayList = new java.util.ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(deserializer.fromPersistableBundle(persistableBundle.getPersistableBundle(java.lang.String.format(LIST_KEY_FORMAT, java.lang.Integer.valueOf(i2)))));
        }
        return arrayList;
    }

    public static android.os.PersistableBundle fromByteArray(byte[] bArr) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putString(BYTE_ARRAY_KEY, com.android.internal.util.HexDump.toHexString(bArr));
        return persistableBundle;
    }

    public static byte[] toByteArray(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
        java.lang.String string = persistableBundle.getString(BYTE_ARRAY_KEY);
        if (string == null || string.length() % 2 != 0) {
            throw new java.lang.IllegalArgumentException("PersistableBundle contains invalid byte array");
        }
        return com.android.internal.util.HexDump.hexStringToByteArray(string);
    }

    @android.annotation.NonNull
    public static <K, V> android.os.PersistableBundle fromMap(@android.annotation.NonNull java.util.Map<K, V> map, @android.annotation.NonNull com.android.server.vcn.util.PersistableBundleUtils.Serializer<K> serializer, @android.annotation.NonNull com.android.server.vcn.util.PersistableBundleUtils.Serializer<V> serializer2) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putInt(COLLECTION_SIZE_KEY, map.size());
        int i = 0;
        for (java.util.Map.Entry<K, V> entry : map.entrySet()) {
            java.lang.String format = java.lang.String.format(MAP_KEY_FORMAT, java.lang.Integer.valueOf(i));
            java.lang.String format2 = java.lang.String.format(MAP_VALUE_FORMAT, java.lang.Integer.valueOf(i));
            persistableBundle.putPersistableBundle(format, serializer.toPersistableBundle(entry.getKey()));
            persistableBundle.putPersistableBundle(format2, serializer2.toPersistableBundle(entry.getValue()));
            i++;
        }
        return persistableBundle;
    }

    @android.annotation.NonNull
    public static <K, V> java.util.LinkedHashMap<K, V> toMap(@android.annotation.NonNull android.os.PersistableBundle persistableBundle, @android.annotation.NonNull com.android.server.vcn.util.PersistableBundleUtils.Deserializer<K> deserializer, @android.annotation.NonNull com.android.server.vcn.util.PersistableBundleUtils.Deserializer<V> deserializer2) {
        int i = persistableBundle.getInt(COLLECTION_SIZE_KEY);
        java.util.LinkedHashMap<K, V> linkedHashMap = new java.util.LinkedHashMap<>(i);
        for (int i2 = 0; i2 < i; i2++) {
            java.lang.String format = java.lang.String.format(MAP_KEY_FORMAT, java.lang.Integer.valueOf(i2));
            java.lang.String format2 = java.lang.String.format(MAP_VALUE_FORMAT, java.lang.Integer.valueOf(i2));
            linkedHashMap.put(deserializer.fromPersistableBundle(persistableBundle.getPersistableBundle(format)), deserializer2.fromPersistableBundle(persistableBundle.getPersistableBundle(format2)));
        }
        return linkedHashMap;
    }

    @android.annotation.Nullable
    public static byte[] toDiskStableBytes(@android.annotation.NonNull android.os.PersistableBundle persistableBundle) throws java.io.IOException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        persistableBundle.writeToStream(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static android.os.PersistableBundle fromDiskStableBytes(@android.annotation.NonNull byte[] bArr) throws java.io.IOException {
        return android.os.PersistableBundle.readFromStream(new java.io.ByteArrayInputStream(bArr));
    }

    public static class LockingReadWriteHelper {
        private final java.util.concurrent.locks.ReadWriteLock mDiskLock = new java.util.concurrent.locks.ReentrantReadWriteLock();
        private final java.lang.String mPath;

        public LockingReadWriteHelper(@android.annotation.NonNull java.lang.String str) {
            java.util.Objects.requireNonNull(str, "fileName was null");
            this.mPath = str;
        }

        @android.annotation.Nullable
        public android.os.PersistableBundle readFromDisk() throws java.io.IOException {
            try {
                this.mDiskLock.readLock().lock();
                java.io.File file = new java.io.File(this.mPath);
                if (!file.exists()) {
                    this.mDiskLock.readLock().unlock();
                    return null;
                }
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
                try {
                    android.os.PersistableBundle readFromStream = android.os.PersistableBundle.readFromStream(fileInputStream);
                    fileInputStream.close();
                    return readFromStream;
                } finally {
                }
            } finally {
                this.mDiskLock.readLock().unlock();
            }
        }

        public void writeToDisk(@android.annotation.NonNull android.os.PersistableBundle persistableBundle) throws java.io.IOException {
            java.util.Objects.requireNonNull(persistableBundle, "bundle was null");
            try {
                this.mDiskLock.writeLock().lock();
                java.io.File file = new java.io.File(this.mPath);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                }
                java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
                try {
                    persistableBundle.writeToStream(fileOutputStream);
                    fileOutputStream.close();
                    this.mDiskLock.writeLock().unlock();
                } finally {
                }
            } catch (java.lang.Throwable th) {
                this.mDiskLock.writeLock().unlock();
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    public static android.os.PersistableBundle minimizeBundle(@android.annotation.NonNull android.os.PersistableBundle persistableBundle, java.lang.String... strArr) {
        java.lang.Object obj;
        android.os.PersistableBundle persistableBundle2 = new android.os.PersistableBundle();
        if (persistableBundle == null) {
            return persistableBundle2;
        }
        for (java.lang.String str : strArr) {
            if (persistableBundle.containsKey(str) && (obj = persistableBundle.get(str)) != null) {
                if (obj instanceof java.lang.Boolean) {
                    persistableBundle2.putBoolean(str, ((java.lang.Boolean) obj).booleanValue());
                } else if (obj instanceof boolean[]) {
                    persistableBundle2.putBooleanArray(str, (boolean[]) obj);
                } else if (obj instanceof java.lang.Double) {
                    persistableBundle2.putDouble(str, ((java.lang.Double) obj).doubleValue());
                } else if (obj instanceof double[]) {
                    persistableBundle2.putDoubleArray(str, (double[]) obj);
                } else if (obj instanceof java.lang.Integer) {
                    persistableBundle2.putInt(str, ((java.lang.Integer) obj).intValue());
                } else if (obj instanceof int[]) {
                    persistableBundle2.putIntArray(str, (int[]) obj);
                } else if (obj instanceof java.lang.Long) {
                    persistableBundle2.putLong(str, ((java.lang.Long) obj).longValue());
                } else if (obj instanceof long[]) {
                    persistableBundle2.putLongArray(str, (long[]) obj);
                } else if (obj instanceof java.lang.String) {
                    persistableBundle2.putString(str, (java.lang.String) obj);
                } else if (obj instanceof java.lang.String[]) {
                    persistableBundle2.putStringArray(str, (java.lang.String[]) obj);
                } else if (obj instanceof android.os.PersistableBundle) {
                    persistableBundle2.putPersistableBundle(str, (android.os.PersistableBundle) obj);
                }
            }
        }
        return persistableBundle2;
    }

    public static int getHashCode(@android.annotation.Nullable android.os.PersistableBundle persistableBundle) {
        if (persistableBundle == null) {
            return -1;
        }
        java.util.Iterator it = new java.util.TreeSet(persistableBundle.keySet()).iterator();
        int i = 0;
        while (it.hasNext()) {
            java.lang.String str = (java.lang.String) it.next();
            java.lang.Object obj = persistableBundle.get(str);
            if (obj instanceof android.os.PersistableBundle) {
                i = java.util.Objects.hash(java.lang.Integer.valueOf(i), str, java.lang.Integer.valueOf(getHashCode((android.os.PersistableBundle) obj)));
            } else {
                i = java.util.Objects.hash(java.lang.Integer.valueOf(i), str, obj);
            }
        }
        return i;
    }

    public static boolean isEqual(@android.annotation.Nullable android.os.PersistableBundle persistableBundle, @android.annotation.Nullable android.os.PersistableBundle persistableBundle2) {
        if (java.util.Objects.equals(persistableBundle, persistableBundle2)) {
            return true;
        }
        if (java.util.Objects.isNull(persistableBundle) != java.util.Objects.isNull(persistableBundle2) || !persistableBundle.keySet().equals(persistableBundle2.keySet())) {
            return false;
        }
        for (java.lang.String str : persistableBundle.keySet()) {
            java.lang.Object obj = persistableBundle.get(str);
            java.lang.Object obj2 = persistableBundle2.get(str);
            if (!java.util.Objects.equals(obj, obj2)) {
                if (java.util.Objects.isNull(obj) != java.util.Objects.isNull(obj2) || !java.util.Objects.equals(obj.getClass(), obj2.getClass())) {
                    return false;
                }
                if (obj instanceof android.os.PersistableBundle) {
                    if (!isEqual((android.os.PersistableBundle) obj, (android.os.PersistableBundle) obj2)) {
                        return false;
                    }
                } else if (obj.getClass().isArray()) {
                    if (obj instanceof boolean[]) {
                        if (!java.util.Arrays.equals((boolean[]) obj, (boolean[]) obj2)) {
                            return false;
                        }
                    } else if (obj instanceof double[]) {
                        if (!java.util.Arrays.equals((double[]) obj, (double[]) obj2)) {
                            return false;
                        }
                    } else if (obj instanceof int[]) {
                        if (!java.util.Arrays.equals((int[]) obj, (int[]) obj2)) {
                            return false;
                        }
                    } else if (obj instanceof long[]) {
                        if (!java.util.Arrays.equals((long[]) obj, (long[]) obj2)) {
                            return false;
                        }
                    } else if (!java.util.Arrays.equals((java.lang.Object[]) obj, (java.lang.Object[]) obj2)) {
                        return false;
                    }
                } else if (!java.util.Objects.equals(obj, obj2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static class PersistableBundleWrapper {

        @android.annotation.NonNull
        private final android.os.PersistableBundle mBundle;

        public PersistableBundleWrapper(@android.annotation.NonNull android.os.PersistableBundle persistableBundle) {
            java.util.Objects.requireNonNull(persistableBundle, "Bundle was null");
            this.mBundle = persistableBundle;
        }

        public int getInt(java.lang.String str, int i) {
            return this.mBundle.getInt(str, i);
        }

        @android.annotation.Nullable
        public int[] getIntArray(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable int[] iArr) {
            int[] intArray = this.mBundle.getIntArray(str);
            return intArray == null ? iArr : intArray;
        }

        public int hashCode() {
            return com.android.server.vcn.util.PersistableBundleUtils.getHashCode(this.mBundle);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper)) {
                return false;
            }
            return com.android.server.vcn.util.PersistableBundleUtils.isEqual(this.mBundle, ((com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper) obj).mBundle);
        }

        public java.lang.String toString() {
            return this.mBundle.toString();
        }
    }
}
