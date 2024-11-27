package com.android.framework.protobuf.nano;

/* loaded from: classes4.dex */
public final class InternalNano {
    public static final int TYPE_BOOL = 8;
    public static final int TYPE_BYTES = 12;
    public static final int TYPE_DOUBLE = 1;
    public static final int TYPE_ENUM = 14;
    public static final int TYPE_FIXED32 = 7;
    public static final int TYPE_FIXED64 = 6;
    public static final int TYPE_FLOAT = 2;
    public static final int TYPE_GROUP = 10;
    public static final int TYPE_INT32 = 5;
    public static final int TYPE_INT64 = 3;
    public static final int TYPE_MESSAGE = 11;
    public static final int TYPE_SFIXED32 = 15;
    public static final int TYPE_SFIXED64 = 16;
    public static final int TYPE_SINT32 = 17;
    public static final int TYPE_SINT64 = 18;
    public static final int TYPE_STRING = 9;
    public static final int TYPE_UINT32 = 13;
    public static final int TYPE_UINT64 = 4;
    static final java.nio.charset.Charset UTF_8 = java.nio.charset.Charset.forName(android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
    static final java.nio.charset.Charset ISO_8859_1 = java.nio.charset.Charset.forName("ISO-8859-1");
    public static final java.lang.Object LAZY_INIT_LOCK = new java.lang.Object();

    private InternalNano() {
    }

    public static java.lang.String stringDefaultValue(java.lang.String str) {
        return new java.lang.String(str.getBytes(ISO_8859_1), UTF_8);
    }

    public static byte[] bytesDefaultValue(java.lang.String str) {
        return str.getBytes(ISO_8859_1);
    }

    public static byte[] copyFromUtf8(java.lang.String str) {
        return str.getBytes(UTF_8);
    }

    public static boolean equals(int[] iArr, int[] iArr2) {
        if (iArr == null || iArr.length == 0) {
            return iArr2 == null || iArr2.length == 0;
        }
        return java.util.Arrays.equals(iArr, iArr2);
    }

    public static boolean equals(long[] jArr, long[] jArr2) {
        if (jArr == null || jArr.length == 0) {
            return jArr2 == null || jArr2.length == 0;
        }
        return java.util.Arrays.equals(jArr, jArr2);
    }

    public static boolean equals(float[] fArr, float[] fArr2) {
        if (fArr == null || fArr.length == 0) {
            return fArr2 == null || fArr2.length == 0;
        }
        return java.util.Arrays.equals(fArr, fArr2);
    }

    public static boolean equals(double[] dArr, double[] dArr2) {
        if (dArr == null || dArr.length == 0) {
            return dArr2 == null || dArr2.length == 0;
        }
        return java.util.Arrays.equals(dArr, dArr2);
    }

    public static boolean equals(boolean[] zArr, boolean[] zArr2) {
        if (zArr == null || zArr.length == 0) {
            return zArr2 == null || zArr2.length == 0;
        }
        return java.util.Arrays.equals(zArr, zArr2);
    }

    public static boolean equals(byte[][] bArr, byte[][] bArr2) {
        boolean z;
        boolean z2;
        int length = bArr == null ? 0 : bArr.length;
        int length2 = bArr2 == null ? 0 : bArr2.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i < length && bArr[i] == null) {
                i++;
            } else {
                while (i2 < length2 && bArr2[i2] == null) {
                    i2++;
                }
                if (i < length) {
                    z = false;
                } else {
                    z = true;
                }
                if (i2 < length2) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (z && z2) {
                    return true;
                }
                if (z != z2 || !java.util.Arrays.equals(bArr[i], bArr2[i2])) {
                    return false;
                }
                i++;
                i2++;
            }
        }
    }

    public static boolean equals(java.lang.Object[] objArr, java.lang.Object[] objArr2) {
        boolean z;
        boolean z2;
        int length = objArr == null ? 0 : objArr.length;
        int length2 = objArr2 == null ? 0 : objArr2.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i < length && objArr[i] == null) {
                i++;
            } else {
                while (i2 < length2 && objArr2[i2] == null) {
                    i2++;
                }
                if (i < length) {
                    z = false;
                } else {
                    z = true;
                }
                if (i2 < length2) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (z && z2) {
                    return true;
                }
                if (z != z2 || !objArr[i].equals(objArr2[i2])) {
                    return false;
                }
                i++;
                i2++;
            }
        }
    }

    public static int hashCode(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            return 0;
        }
        return java.util.Arrays.hashCode(iArr);
    }

    public static int hashCode(long[] jArr) {
        if (jArr == null || jArr.length == 0) {
            return 0;
        }
        return java.util.Arrays.hashCode(jArr);
    }

    public static int hashCode(float[] fArr) {
        if (fArr == null || fArr.length == 0) {
            return 0;
        }
        return java.util.Arrays.hashCode(fArr);
    }

    public static int hashCode(double[] dArr) {
        if (dArr == null || dArr.length == 0) {
            return 0;
        }
        return java.util.Arrays.hashCode(dArr);
    }

    public static int hashCode(boolean[] zArr) {
        if (zArr == null || zArr.length == 0) {
            return 0;
        }
        return java.util.Arrays.hashCode(zArr);
    }

    public static int hashCode(byte[][] bArr) {
        int length = bArr == null ? 0 : bArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            byte[] bArr2 = bArr[i2];
            if (bArr2 != null) {
                i = (i * 31) + java.util.Arrays.hashCode(bArr2);
            }
        }
        return i;
    }

    public static int hashCode(java.lang.Object[] objArr) {
        int length = objArr == null ? 0 : objArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            java.lang.Object obj = objArr[i2];
            if (obj != null) {
                i = (i * 31) + obj.hashCode();
            }
        }
        return i;
    }

    private static java.lang.Object primitiveDefaultValue(int i) {
        switch (i) {
            case 1:
                return java.lang.Double.valueOf(0.0d);
            case 2:
                return java.lang.Float.valueOf(0.0f);
            case 3:
            case 4:
            case 6:
            case 16:
            case 18:
                return 0L;
            case 5:
            case 7:
            case 13:
            case 14:
            case 15:
            case 17:
                return 0;
            case 8:
                return java.lang.Boolean.FALSE;
            case 9:
                return "";
            case 10:
            case 11:
            default:
                throw new java.lang.IllegalArgumentException("Type: " + i + " is not a primitive type.");
            case 12:
                return com.android.framework.protobuf.nano.WireFormatNano.EMPTY_BYTES;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <K, V> java.util.Map<K, V> mergeMapEntry(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano, java.util.Map<K, V> map, com.android.framework.protobuf.nano.MapFactories.MapFactory mapFactory, int i, int i2, V v, int i3, int i4) throws java.io.IOException {
        java.util.Map<K, V> forMap = mapFactory.forMap(map);
        int pushLimit = codedInputByteBufferNano.pushLimit(codedInputByteBufferNano.readRawVarint32());
        java.lang.Object obj = null;
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                break;
            }
            if (readTag == i3) {
                obj = codedInputByteBufferNano.readPrimitiveField(i);
            } else if (readTag == i4) {
                if (i2 == 11) {
                    codedInputByteBufferNano.readMessage(v);
                } else {
                    v = (V) codedInputByteBufferNano.readPrimitiveField(i2);
                }
            } else if (!codedInputByteBufferNano.skipField(readTag)) {
                break;
            }
        }
        codedInputByteBufferNano.checkLastTagWas(0);
        codedInputByteBufferNano.popLimit(pushLimit);
        if (obj == null) {
            obj = primitiveDefaultValue(i);
        }
        if (v == 0) {
            v = primitiveDefaultValue(i2);
        }
        forMap.put(obj, v);
        return forMap;
    }

    public static <K, V> void serializeMapField(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano, java.util.Map<K, V> map, int i, int i2, int i3) throws java.io.IOException {
        for (java.util.Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            if (key == null || value == null) {
                throw new java.lang.IllegalStateException("keys and values in maps cannot be null");
            }
            int computeFieldSize = com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeFieldSize(1, i2, key) + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeFieldSize(2, i3, value);
            codedOutputByteBufferNano.writeTag(i, 2);
            codedOutputByteBufferNano.writeRawVarint32(computeFieldSize);
            codedOutputByteBufferNano.writeField(1, i2, key);
            codedOutputByteBufferNano.writeField(2, i3, value);
        }
    }

    public static <K, V> int computeMapFieldSize(java.util.Map<K, V> map, int i, int i2, int i3) {
        int computeTagSize = com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeTagSize(i);
        int i4 = 0;
        for (java.util.Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            if (key == null || value == null) {
                throw new java.lang.IllegalStateException("keys and values in maps cannot be null");
            }
            int computeFieldSize = com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeFieldSize(1, i2, key) + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeFieldSize(2, i3, value);
            i4 += computeTagSize + computeFieldSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeRawVarint32Size(computeFieldSize);
        }
        return i4;
    }

    public static <K, V> boolean equals(java.util.Map<K, V> map, java.util.Map<K, V> map2) {
        if (map == map2) {
            return true;
        }
        if (map == null) {
            if (map2.size() == 0) {
                return true;
            }
            return false;
        }
        if (map2 == null) {
            if (map.size() == 0) {
                return true;
            }
            return false;
        }
        if (map.size() != map2.size()) {
            return false;
        }
        for (java.util.Map.Entry<K, V> entry : map.entrySet()) {
            if (!map2.containsKey(entry.getKey()) || !equalsMapValue(entry.getValue(), map2.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    private static boolean equalsMapValue(java.lang.Object obj, java.lang.Object obj2) {
        if (obj == null || obj2 == null) {
            throw new java.lang.IllegalStateException("keys and values in maps cannot be null");
        }
        if ((obj instanceof byte[]) && (obj2 instanceof byte[])) {
            return java.util.Arrays.equals((byte[]) obj, (byte[]) obj2);
        }
        return obj.equals(obj2);
    }

    public static <K, V> int hashCode(java.util.Map<K, V> map) {
        int i = 0;
        if (map == null) {
            return 0;
        }
        for (java.util.Map.Entry<K, V> entry : map.entrySet()) {
            i += hashCodeForMap(entry.getValue()) ^ hashCodeForMap(entry.getKey());
        }
        return i;
    }

    private static int hashCodeForMap(java.lang.Object obj) {
        if (obj instanceof byte[]) {
            return java.util.Arrays.hashCode((byte[]) obj);
        }
        return obj.hashCode();
    }

    public static void cloneUnknownFieldData(com.android.framework.protobuf.nano.ExtendableMessageNano extendableMessageNano, com.android.framework.protobuf.nano.ExtendableMessageNano extendableMessageNano2) {
        if (extendableMessageNano.unknownFieldData != null) {
            extendableMessageNano2.unknownFieldData = extendableMessageNano.unknownFieldData.m6594clone();
        }
    }
}
