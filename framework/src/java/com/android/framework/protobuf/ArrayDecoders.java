package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class ArrayDecoders {
    private ArrayDecoders() {
    }

    static final class Registers {
        public final com.android.framework.protobuf.ExtensionRegistryLite extensionRegistry;
        public int int1;
        public long long1;
        public java.lang.Object object1;

        Registers() {
            this.extensionRegistry = com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry();
        }

        Registers(com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) {
            if (extensionRegistryLite == null) {
                throw new java.lang.NullPointerException();
            }
            this.extensionRegistry = extensionRegistryLite;
        }
    }

    static int decodeVarint32(byte[] bArr, int i, com.android.framework.protobuf.ArrayDecoders.Registers registers) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b >= 0) {
            registers.int1 = b;
            return i2;
        }
        return decodeVarint32(b, bArr, i2, registers);
    }

    static int decodeVarint32(int i, byte[] bArr, int i2, com.android.framework.protobuf.ArrayDecoders.Registers registers) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            registers.int1 = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            registers.int1 = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            registers.int1 = i7 | (b3 << android.hardware.biometrics.face.AcquiredInfo.START);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            registers.int1 = i9 | (b4 << 28);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                registers.int1 = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int decodeVarint64(byte[] bArr, int i, com.android.framework.protobuf.ArrayDecoders.Registers registers) {
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            registers.long1 = j;
            return i2;
        }
        return decodeVarint64(j, bArr, i2, registers);
    }

    static int decodeVarint64(long j, byte[] bArr, int i, com.android.framework.protobuf.ArrayDecoders.Registers registers) {
        int i2 = i + 1;
        byte b = bArr[i];
        long j2 = (j & 127) | ((b & Byte.MAX_VALUE) << 7);
        int i3 = 7;
        while (b < 0) {
            int i4 = i2 + 1;
            byte b2 = bArr[i2];
            i3 += 7;
            j2 |= (b2 & Byte.MAX_VALUE) << i3;
            i2 = i4;
            b = b2;
        }
        registers.long1 = j2;
        return i2;
    }

    static int decodeFixed32(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    static long decodeFixed64(byte[] bArr, int i) {
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }

    static double decodeDouble(byte[] bArr, int i) {
        return java.lang.Double.longBitsToDouble(decodeFixed64(bArr, i));
    }

    static float decodeFloat(byte[] bArr, int i) {
        return java.lang.Float.intBitsToFloat(decodeFixed32(bArr, i));
    }

    static int decodeString(byte[] bArr, int i, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1;
        if (i2 < 0) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
        }
        if (i2 == 0) {
            registers.object1 = "";
            return decodeVarint32;
        }
        registers.object1 = new java.lang.String(bArr, decodeVarint32, i2, com.android.framework.protobuf.Internal.UTF_8);
        return decodeVarint32 + i2;
    }

    static int decodeStringRequireUtf8(byte[] bArr, int i, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1;
        if (i2 < 0) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
        }
        if (i2 == 0) {
            registers.object1 = "";
            return decodeVarint32;
        }
        registers.object1 = com.android.framework.protobuf.Utf8.decodeUtf8(bArr, decodeVarint32, i2);
        return decodeVarint32 + i2;
    }

    static int decodeBytes(byte[] bArr, int i, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1;
        if (i2 < 0) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
        }
        if (i2 > bArr.length - decodeVarint32) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        if (i2 == 0) {
            registers.object1 = com.android.framework.protobuf.ByteString.EMPTY;
            return decodeVarint32;
        }
        registers.object1 = com.android.framework.protobuf.ByteString.copyFrom(bArr, decodeVarint32, i2);
        return decodeVarint32 + i2;
    }

    static int decodeMessageField(com.android.framework.protobuf.Schema schema, byte[] bArr, int i, int i2, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        java.lang.Object newInstance = schema.newInstance();
        int mergeMessageField = mergeMessageField(newInstance, schema, bArr, i, i2, registers);
        schema.makeImmutable(newInstance);
        registers.object1 = newInstance;
        return mergeMessageField;
    }

    static int decodeGroupField(com.android.framework.protobuf.Schema schema, byte[] bArr, int i, int i2, int i3, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        java.lang.Object newInstance = schema.newInstance();
        int mergeGroupField = mergeGroupField(newInstance, schema, bArr, i, i2, i3, registers);
        schema.makeImmutable(newInstance);
        registers.object1 = newInstance;
        return mergeGroupField;
    }

    static int mergeMessageField(java.lang.Object obj, com.android.framework.protobuf.Schema schema, byte[] bArr, int i, int i2, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        int i3;
        int i4 = i + 1;
        int i5 = bArr[i];
        if (i5 >= 0) {
            i3 = i4;
        } else {
            int decodeVarint32 = decodeVarint32(i5, bArr, i4, registers);
            i5 = registers.int1;
            i3 = decodeVarint32;
        }
        if (i5 < 0 || i5 > i2 - i3) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        int i6 = i5 + i3;
        schema.mergeFrom(obj, bArr, i3, i6, registers);
        registers.object1 = obj;
        return i6;
    }

    static int mergeGroupField(java.lang.Object obj, com.android.framework.protobuf.Schema schema, byte[] bArr, int i, int i2, int i3, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        int parseProto2Message = ((com.android.framework.protobuf.MessageSchema) schema).parseProto2Message(obj, bArr, i, i2, i3, registers);
        registers.object1 = obj;
        return parseProto2Message;
    }

    static int decodeVarint32List(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) {
        com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i2, registers);
        intArrayList.addInt(registers.int1);
        while (decodeVarint32 < i3) {
            int decodeVarint322 = decodeVarint32(bArr, decodeVarint32, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint32 = decodeVarint32(bArr, decodeVarint322, registers);
            intArrayList.addInt(registers.int1);
        }
        return decodeVarint32;
    }

    static int decodeVarint64List(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) {
        com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) protobufList;
        int decodeVarint64 = decodeVarint64(bArr, i2, registers);
        longArrayList.addLong(registers.long1);
        while (decodeVarint64 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, decodeVarint64, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint64 = decodeVarint64(bArr, decodeVarint32, registers);
            longArrayList.addLong(registers.long1);
        }
        return decodeVarint64;
    }

    static int decodeFixed32List(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) {
        com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) protobufList;
        intArrayList.addInt(decodeFixed32(bArr, i2));
        int i4 = i2 + 4;
        while (i4 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, i4, registers);
            if (i != registers.int1) {
                break;
            }
            intArrayList.addInt(decodeFixed32(bArr, decodeVarint32));
            i4 = decodeVarint32 + 4;
        }
        return i4;
    }

    static int decodeFixed64List(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) {
        com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) protobufList;
        longArrayList.addLong(decodeFixed64(bArr, i2));
        int i4 = i2 + 8;
        while (i4 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, i4, registers);
            if (i != registers.int1) {
                break;
            }
            longArrayList.addLong(decodeFixed64(bArr, decodeVarint32));
            i4 = decodeVarint32 + 8;
        }
        return i4;
    }

    static int decodeFloatList(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) {
        com.android.framework.protobuf.FloatArrayList floatArrayList = (com.android.framework.protobuf.FloatArrayList) protobufList;
        floatArrayList.addFloat(decodeFloat(bArr, i2));
        int i4 = i2 + 4;
        while (i4 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, i4, registers);
            if (i != registers.int1) {
                break;
            }
            floatArrayList.addFloat(decodeFloat(bArr, decodeVarint32));
            i4 = decodeVarint32 + 4;
        }
        return i4;
    }

    static int decodeDoubleList(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) {
        com.android.framework.protobuf.DoubleArrayList doubleArrayList = (com.android.framework.protobuf.DoubleArrayList) protobufList;
        doubleArrayList.addDouble(decodeDouble(bArr, i2));
        int i4 = i2 + 8;
        while (i4 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, i4, registers);
            if (i != registers.int1) {
                break;
            }
            doubleArrayList.addDouble(decodeDouble(bArr, decodeVarint32));
            i4 = decodeVarint32 + 8;
        }
        return i4;
    }

    static int decodeBoolList(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) {
        com.android.framework.protobuf.BooleanArrayList booleanArrayList = (com.android.framework.protobuf.BooleanArrayList) protobufList;
        int decodeVarint64 = decodeVarint64(bArr, i2, registers);
        booleanArrayList.addBoolean(registers.long1 != 0);
        while (decodeVarint64 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, decodeVarint64, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint64 = decodeVarint64(bArr, decodeVarint32, registers);
            booleanArrayList.addBoolean(registers.long1 != 0);
        }
        return decodeVarint64;
    }

    static int decodeSInt32List(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) {
        com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i2, registers);
        intArrayList.addInt(com.android.framework.protobuf.CodedInputStream.decodeZigZag32(registers.int1));
        while (decodeVarint32 < i3) {
            int decodeVarint322 = decodeVarint32(bArr, decodeVarint32, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint32 = decodeVarint32(bArr, decodeVarint322, registers);
            intArrayList.addInt(com.android.framework.protobuf.CodedInputStream.decodeZigZag32(registers.int1));
        }
        return decodeVarint32;
    }

    static int decodeSInt64List(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) {
        com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) protobufList;
        int decodeVarint64 = decodeVarint64(bArr, i2, registers);
        longArrayList.addLong(com.android.framework.protobuf.CodedInputStream.decodeZigZag64(registers.long1));
        while (decodeVarint64 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, decodeVarint64, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint64 = decodeVarint64(bArr, decodeVarint32, registers);
            longArrayList.addLong(com.android.framework.protobuf.CodedInputStream.decodeZigZag64(registers.long1));
        }
        return decodeVarint64;
    }

    static int decodePackedVarint32List(byte[] bArr, int i, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            decodeVarint32 = decodeVarint32(bArr, decodeVarint32, registers);
            intArrayList.addInt(registers.int1);
        }
        if (decodeVarint32 != i2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        return decodeVarint32;
    }

    static int decodePackedVarint64List(byte[] bArr, int i, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            decodeVarint32 = decodeVarint64(bArr, decodeVarint32, registers);
            longArrayList.addLong(registers.long1);
        }
        if (decodeVarint32 != i2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        return decodeVarint32;
    }

    static int decodePackedFixed32List(byte[] bArr, int i, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            intArrayList.addInt(decodeFixed32(bArr, decodeVarint32));
            decodeVarint32 += 4;
        }
        if (decodeVarint32 != i2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        return decodeVarint32;
    }

    static int decodePackedFixed64List(byte[] bArr, int i, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            longArrayList.addLong(decodeFixed64(bArr, decodeVarint32));
            decodeVarint32 += 8;
        }
        if (decodeVarint32 != i2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        return decodeVarint32;
    }

    static int decodePackedFloatList(byte[] bArr, int i, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        com.android.framework.protobuf.FloatArrayList floatArrayList = (com.android.framework.protobuf.FloatArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            floatArrayList.addFloat(decodeFloat(bArr, decodeVarint32));
            decodeVarint32 += 4;
        }
        if (decodeVarint32 != i2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        return decodeVarint32;
    }

    static int decodePackedDoubleList(byte[] bArr, int i, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        com.android.framework.protobuf.DoubleArrayList doubleArrayList = (com.android.framework.protobuf.DoubleArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            doubleArrayList.addDouble(decodeDouble(bArr, decodeVarint32));
            decodeVarint32 += 8;
        }
        if (decodeVarint32 != i2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        return decodeVarint32;
    }

    static int decodePackedBoolList(byte[] bArr, int i, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        com.android.framework.protobuf.BooleanArrayList booleanArrayList = (com.android.framework.protobuf.BooleanArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            decodeVarint32 = decodeVarint64(bArr, decodeVarint32, registers);
            booleanArrayList.addBoolean(registers.long1 != 0);
        }
        if (decodeVarint32 != i2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        return decodeVarint32;
    }

    static int decodePackedSInt32List(byte[] bArr, int i, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            decodeVarint32 = decodeVarint32(bArr, decodeVarint32, registers);
            intArrayList.addInt(com.android.framework.protobuf.CodedInputStream.decodeZigZag32(registers.int1));
        }
        if (decodeVarint32 != i2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        return decodeVarint32;
    }

    static int decodePackedSInt64List(byte[] bArr, int i, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            decodeVarint32 = decodeVarint64(bArr, decodeVarint32, registers);
            longArrayList.addLong(com.android.framework.protobuf.CodedInputStream.decodeZigZag64(registers.long1));
        }
        if (decodeVarint32 != i2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        return decodeVarint32;
    }

    static int decodeStringList(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        int decodeVarint32 = decodeVarint32(bArr, i2, registers);
        int i4 = registers.int1;
        if (i4 < 0) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
        }
        if (i4 == 0) {
            protobufList.add("");
        } else {
            protobufList.add(new java.lang.String(bArr, decodeVarint32, i4, com.android.framework.protobuf.Internal.UTF_8));
            decodeVarint32 += i4;
        }
        while (decodeVarint32 < i3) {
            int decodeVarint322 = decodeVarint32(bArr, decodeVarint32, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint32 = decodeVarint32(bArr, decodeVarint322, registers);
            int i5 = registers.int1;
            if (i5 < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            if (i5 == 0) {
                protobufList.add("");
            } else {
                protobufList.add(new java.lang.String(bArr, decodeVarint32, i5, com.android.framework.protobuf.Internal.UTF_8));
                decodeVarint32 += i5;
            }
        }
        return decodeVarint32;
    }

    static int decodeStringListRequireUtf8(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        int decodeVarint32 = decodeVarint32(bArr, i2, registers);
        int i4 = registers.int1;
        if (i4 < 0) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
        }
        if (i4 == 0) {
            protobufList.add("");
        } else {
            int i5 = decodeVarint32 + i4;
            if (!com.android.framework.protobuf.Utf8.isValidUtf8(bArr, decodeVarint32, i5)) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
            }
            protobufList.add(new java.lang.String(bArr, decodeVarint32, i4, com.android.framework.protobuf.Internal.UTF_8));
            decodeVarint32 = i5;
        }
        while (decodeVarint32 < i3) {
            int decodeVarint322 = decodeVarint32(bArr, decodeVarint32, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint32 = decodeVarint32(bArr, decodeVarint322, registers);
            int i6 = registers.int1;
            if (i6 < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            if (i6 == 0) {
                protobufList.add("");
            } else {
                int i7 = decodeVarint32 + i6;
                if (!com.android.framework.protobuf.Utf8.isValidUtf8(bArr, decodeVarint32, i7)) {
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
                }
                protobufList.add(new java.lang.String(bArr, decodeVarint32, i6, com.android.framework.protobuf.Internal.UTF_8));
                decodeVarint32 = i7;
            }
        }
        return decodeVarint32;
    }

    static int decodeBytesList(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        int decodeVarint32 = decodeVarint32(bArr, i2, registers);
        int i4 = registers.int1;
        if (i4 < 0) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
        }
        if (i4 > bArr.length - decodeVarint32) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        if (i4 == 0) {
            protobufList.add(com.android.framework.protobuf.ByteString.EMPTY);
        } else {
            protobufList.add(com.android.framework.protobuf.ByteString.copyFrom(bArr, decodeVarint32, i4));
            decodeVarint32 += i4;
        }
        while (decodeVarint32 < i3) {
            int decodeVarint322 = decodeVarint32(bArr, decodeVarint32, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint32 = decodeVarint32(bArr, decodeVarint322, registers);
            int i5 = registers.int1;
            if (i5 < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            if (i5 > bArr.length - decodeVarint32) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            if (i5 == 0) {
                protobufList.add(com.android.framework.protobuf.ByteString.EMPTY);
            } else {
                protobufList.add(com.android.framework.protobuf.ByteString.copyFrom(bArr, decodeVarint32, i5));
                decodeVarint32 += i5;
            }
        }
        return decodeVarint32;
    }

    static int decodeMessageList(com.android.framework.protobuf.Schema<?> schema, int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        int decodeMessageField = decodeMessageField(schema, bArr, i2, i3, registers);
        protobufList.add(registers.object1);
        while (decodeMessageField < i3) {
            int decodeVarint32 = decodeVarint32(bArr, decodeMessageField, registers);
            if (i != registers.int1) {
                break;
            }
            decodeMessageField = decodeMessageField(schema, bArr, decodeVarint32, i3, registers);
            protobufList.add(registers.object1);
        }
        return decodeMessageField;
    }

    static int decodeGroupList(com.android.framework.protobuf.Schema schema, int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.Internal.ProtobufList<?> protobufList, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        int i4 = (i & (-8)) | 4;
        int decodeGroupField = decodeGroupField(schema, bArr, i2, i3, i4, registers);
        protobufList.add(registers.object1);
        while (decodeGroupField < i3) {
            int decodeVarint32 = decodeVarint32(bArr, decodeGroupField, registers);
            if (i != registers.int1) {
                break;
            }
            decodeGroupField = decodeGroupField(schema, bArr, decodeVarint32, i3, i4, registers);
            protobufList.add(registers.object1);
        }
        return decodeGroupField;
    }

    static int decodeExtensionOrUnknownField(int i, byte[] bArr, int i2, int i3, java.lang.Object obj, com.android.framework.protobuf.MessageLite messageLite, com.android.framework.protobuf.UnknownFieldSchema<com.android.framework.protobuf.UnknownFieldSetLite, com.android.framework.protobuf.UnknownFieldSetLite> unknownFieldSchema, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension findLiteExtensionByNumber = registers.extensionRegistry.findLiteExtensionByNumber(messageLite, i >>> 3);
        if (findLiteExtensionByNumber == null) {
            return decodeUnknownField(i, bArr, i2, i3, com.android.framework.protobuf.MessageSchema.getMutableUnknownFields(obj), registers);
        }
        com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage extendableMessage = (com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) obj;
        extendableMessage.ensureExtensionsAreMutable();
        return decodeExtension(i, bArr, i2, i3, extendableMessage, findLiteExtensionByNumber, unknownFieldSchema, registers);
    }

    static int decodeExtension(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage, com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<?, ?> generatedExtension, com.android.framework.protobuf.UnknownFieldSchema<com.android.framework.protobuf.UnknownFieldSetLite, com.android.framework.protobuf.UnknownFieldSetLite> unknownFieldSchema, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        java.lang.Object obj;
        java.lang.Object obj2;
        com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> fieldSet = extendableMessage.extensions;
        int i4 = i >>> 3;
        if (generatedExtension.descriptor.isRepeated() && generatedExtension.descriptor.isPacked()) {
            switch (generatedExtension.getLiteType()) {
                case com.android.framework.protobuf.WireFormat.FieldType.DOUBLE:
                    com.android.framework.protobuf.DoubleArrayList doubleArrayList = new com.android.framework.protobuf.DoubleArrayList();
                    int decodePackedDoubleList = decodePackedDoubleList(bArr, i2, doubleArrayList, registers);
                    fieldSet.setField(generatedExtension.descriptor, doubleArrayList);
                    return decodePackedDoubleList;
                case com.android.framework.protobuf.WireFormat.FieldType.FLOAT:
                    com.android.framework.protobuf.FloatArrayList floatArrayList = new com.android.framework.protobuf.FloatArrayList();
                    int decodePackedFloatList = decodePackedFloatList(bArr, i2, floatArrayList, registers);
                    fieldSet.setField(generatedExtension.descriptor, floatArrayList);
                    return decodePackedFloatList;
                case com.android.framework.protobuf.WireFormat.FieldType.INT64:
                case com.android.framework.protobuf.WireFormat.FieldType.UINT64:
                    com.android.framework.protobuf.LongArrayList longArrayList = new com.android.framework.protobuf.LongArrayList();
                    int decodePackedVarint64List = decodePackedVarint64List(bArr, i2, longArrayList, registers);
                    fieldSet.setField(generatedExtension.descriptor, longArrayList);
                    return decodePackedVarint64List;
                case com.android.framework.protobuf.WireFormat.FieldType.INT32:
                case com.android.framework.protobuf.WireFormat.FieldType.UINT32:
                    com.android.framework.protobuf.IntArrayList intArrayList = new com.android.framework.protobuf.IntArrayList();
                    int decodePackedVarint32List = decodePackedVarint32List(bArr, i2, intArrayList, registers);
                    fieldSet.setField(generatedExtension.descriptor, intArrayList);
                    return decodePackedVarint32List;
                case com.android.framework.protobuf.WireFormat.FieldType.FIXED64:
                case com.android.framework.protobuf.WireFormat.FieldType.SFIXED64:
                    com.android.framework.protobuf.LongArrayList longArrayList2 = new com.android.framework.protobuf.LongArrayList();
                    int decodePackedFixed64List = decodePackedFixed64List(bArr, i2, longArrayList2, registers);
                    fieldSet.setField(generatedExtension.descriptor, longArrayList2);
                    return decodePackedFixed64List;
                case com.android.framework.protobuf.WireFormat.FieldType.FIXED32:
                case com.android.framework.protobuf.WireFormat.FieldType.SFIXED32:
                    com.android.framework.protobuf.IntArrayList intArrayList2 = new com.android.framework.protobuf.IntArrayList();
                    int decodePackedFixed32List = decodePackedFixed32List(bArr, i2, intArrayList2, registers);
                    fieldSet.setField(generatedExtension.descriptor, intArrayList2);
                    return decodePackedFixed32List;
                case com.android.framework.protobuf.WireFormat.FieldType.BOOL:
                    com.android.framework.protobuf.BooleanArrayList booleanArrayList = new com.android.framework.protobuf.BooleanArrayList();
                    int decodePackedBoolList = decodePackedBoolList(bArr, i2, booleanArrayList, registers);
                    fieldSet.setField(generatedExtension.descriptor, booleanArrayList);
                    return decodePackedBoolList;
                case com.android.framework.protobuf.WireFormat.FieldType.SINT32:
                    com.android.framework.protobuf.IntArrayList intArrayList3 = new com.android.framework.protobuf.IntArrayList();
                    int decodePackedSInt32List = decodePackedSInt32List(bArr, i2, intArrayList3, registers);
                    fieldSet.setField(generatedExtension.descriptor, intArrayList3);
                    return decodePackedSInt32List;
                case com.android.framework.protobuf.WireFormat.FieldType.SINT64:
                    com.android.framework.protobuf.LongArrayList longArrayList3 = new com.android.framework.protobuf.LongArrayList();
                    int decodePackedSInt64List = decodePackedSInt64List(bArr, i2, longArrayList3, registers);
                    fieldSet.setField(generatedExtension.descriptor, longArrayList3);
                    return decodePackedSInt64List;
                case com.android.framework.protobuf.WireFormat.FieldType.ENUM:
                    com.android.framework.protobuf.IntArrayList intArrayList4 = new com.android.framework.protobuf.IntArrayList();
                    int decodePackedVarint32List2 = decodePackedVarint32List(bArr, i2, intArrayList4, registers);
                    com.android.framework.protobuf.SchemaUtil.filterUnknownEnumList((java.lang.Object) extendableMessage, i4, (java.util.List<java.lang.Integer>) intArrayList4, generatedExtension.descriptor.getEnumType(), (java.lang.Object) null, (com.android.framework.protobuf.UnknownFieldSchema<UT, java.lang.Object>) unknownFieldSchema);
                    fieldSet.setField(generatedExtension.descriptor, intArrayList4);
                    return decodePackedVarint32List2;
                default:
                    throw new java.lang.IllegalStateException("Type cannot be packed: " + generatedExtension.descriptor.getLiteType());
            }
        }
        java.lang.Object obj3 = null;
        if (generatedExtension.getLiteType() == com.android.framework.protobuf.WireFormat.FieldType.ENUM) {
            i2 = decodeVarint32(bArr, i2, registers);
            if (generatedExtension.descriptor.getEnumType().findValueByNumber(registers.int1) == null) {
                com.android.framework.protobuf.SchemaUtil.storeUnknownEnum(extendableMessage, i4, registers.int1, null, unknownFieldSchema);
                return i2;
            }
            obj3 = java.lang.Integer.valueOf(registers.int1);
        } else {
            switch (generatedExtension.getLiteType()) {
                case com.android.framework.protobuf.WireFormat.FieldType.DOUBLE:
                    obj3 = java.lang.Double.valueOf(decodeDouble(bArr, i2));
                    i2 += 8;
                    break;
                case com.android.framework.protobuf.WireFormat.FieldType.FLOAT:
                    obj3 = java.lang.Float.valueOf(decodeFloat(bArr, i2));
                    i2 += 4;
                    break;
                case com.android.framework.protobuf.WireFormat.FieldType.INT64:
                case com.android.framework.protobuf.WireFormat.FieldType.UINT64:
                    i2 = decodeVarint64(bArr, i2, registers);
                    obj3 = java.lang.Long.valueOf(registers.long1);
                    break;
                case com.android.framework.protobuf.WireFormat.FieldType.INT32:
                case com.android.framework.protobuf.WireFormat.FieldType.UINT32:
                    i2 = decodeVarint32(bArr, i2, registers);
                    obj3 = java.lang.Integer.valueOf(registers.int1);
                    break;
                case com.android.framework.protobuf.WireFormat.FieldType.FIXED64:
                case com.android.framework.protobuf.WireFormat.FieldType.SFIXED64:
                    obj3 = java.lang.Long.valueOf(decodeFixed64(bArr, i2));
                    i2 += 8;
                    break;
                case com.android.framework.protobuf.WireFormat.FieldType.FIXED32:
                case com.android.framework.protobuf.WireFormat.FieldType.SFIXED32:
                    obj3 = java.lang.Integer.valueOf(decodeFixed32(bArr, i2));
                    i2 += 4;
                    break;
                case com.android.framework.protobuf.WireFormat.FieldType.BOOL:
                    i2 = decodeVarint64(bArr, i2, registers);
                    obj3 = java.lang.Boolean.valueOf(registers.long1 != 0);
                    break;
                case com.android.framework.protobuf.WireFormat.FieldType.SINT32:
                    i2 = decodeVarint32(bArr, i2, registers);
                    obj3 = java.lang.Integer.valueOf(com.android.framework.protobuf.CodedInputStream.decodeZigZag32(registers.int1));
                    break;
                case com.android.framework.protobuf.WireFormat.FieldType.SINT64:
                    i2 = decodeVarint64(bArr, i2, registers);
                    obj3 = java.lang.Long.valueOf(com.android.framework.protobuf.CodedInputStream.decodeZigZag64(registers.long1));
                    break;
                case com.android.framework.protobuf.WireFormat.FieldType.ENUM:
                    throw new java.lang.IllegalStateException("Shouldn't reach here.");
                case com.android.framework.protobuf.WireFormat.FieldType.BYTES:
                    i2 = decodeBytes(bArr, i2, registers);
                    obj3 = registers.object1;
                    break;
                case com.android.framework.protobuf.WireFormat.FieldType.STRING:
                    i2 = decodeString(bArr, i2, registers);
                    obj3 = registers.object1;
                    break;
                case com.android.framework.protobuf.WireFormat.FieldType.GROUP:
                    int i5 = (i4 << 3) | 4;
                    com.android.framework.protobuf.Schema schemaFor = com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) generatedExtension.getMessageDefaultInstance().getClass());
                    if (generatedExtension.isRepeated()) {
                        int decodeGroupField = decodeGroupField(schemaFor, bArr, i2, i3, i5, registers);
                        fieldSet.addRepeatedField(generatedExtension.descriptor, registers.object1);
                        return decodeGroupField;
                    }
                    java.lang.Object field = fieldSet.getField(generatedExtension.descriptor);
                    if (field != null) {
                        obj = field;
                    } else {
                        java.lang.Object newInstance = schemaFor.newInstance();
                        fieldSet.setField(generatedExtension.descriptor, newInstance);
                        obj = newInstance;
                    }
                    return mergeGroupField(obj, schemaFor, bArr, i2, i3, i5, registers);
                case com.android.framework.protobuf.WireFormat.FieldType.MESSAGE:
                    com.android.framework.protobuf.Schema schemaFor2 = com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) generatedExtension.getMessageDefaultInstance().getClass());
                    if (generatedExtension.isRepeated()) {
                        int decodeMessageField = decodeMessageField(schemaFor2, bArr, i2, i3, registers);
                        fieldSet.addRepeatedField(generatedExtension.descriptor, registers.object1);
                        return decodeMessageField;
                    }
                    java.lang.Object field2 = fieldSet.getField(generatedExtension.descriptor);
                    if (field2 != null) {
                        obj2 = field2;
                    } else {
                        java.lang.Object newInstance2 = schemaFor2.newInstance();
                        fieldSet.setField(generatedExtension.descriptor, newInstance2);
                        obj2 = newInstance2;
                    }
                    return mergeMessageField(obj2, schemaFor2, bArr, i2, i3, registers);
            }
        }
        if (generatedExtension.isRepeated()) {
            fieldSet.addRepeatedField(generatedExtension.descriptor, obj3);
        } else {
            fieldSet.setField(generatedExtension.descriptor, obj3);
        }
        return i2;
    }

    static int decodeUnknownField(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        if (com.android.framework.protobuf.WireFormat.getTagFieldNumber(i) == 0) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidTag();
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(i)) {
            case 0:
                int decodeVarint64 = decodeVarint64(bArr, i2, registers);
                unknownFieldSetLite.storeField(i, java.lang.Long.valueOf(registers.long1));
                return decodeVarint64;
            case 1:
                unknownFieldSetLite.storeField(i, java.lang.Long.valueOf(decodeFixed64(bArr, i2)));
                return i2 + 8;
            case 2:
                int decodeVarint32 = decodeVarint32(bArr, i2, registers);
                int i4 = registers.int1;
                if (i4 < 0) {
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
                }
                if (i4 > bArr.length - decodeVarint32) {
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
                }
                if (i4 == 0) {
                    unknownFieldSetLite.storeField(i, com.android.framework.protobuf.ByteString.EMPTY);
                } else {
                    unknownFieldSetLite.storeField(i, com.android.framework.protobuf.ByteString.copyFrom(bArr, decodeVarint32, i4));
                }
                return decodeVarint32 + i4;
            case 3:
                com.android.framework.protobuf.UnknownFieldSetLite newInstance = com.android.framework.protobuf.UnknownFieldSetLite.newInstance();
                int i5 = (i & (-8)) | 4;
                int i6 = 0;
                while (true) {
                    if (i2 < i3) {
                        int decodeVarint322 = decodeVarint32(bArr, i2, registers);
                        int i7 = registers.int1;
                        if (i7 == i5) {
                            i6 = i7;
                            i2 = decodeVarint322;
                        } else {
                            i6 = i7;
                            i2 = decodeUnknownField(i7, bArr, decodeVarint322, i3, newInstance, registers);
                        }
                    }
                }
                if (i2 > i3 || i6 != i5) {
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
                }
                unknownFieldSetLite.storeField(i, newInstance);
                return i2;
            case 4:
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidTag();
            case 5:
                unknownFieldSetLite.storeField(i, java.lang.Integer.valueOf(decodeFixed32(bArr, i2)));
                return i2 + 4;
        }
    }

    static int skipField(int i, byte[] bArr, int i2, int i3, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        if (com.android.framework.protobuf.WireFormat.getTagFieldNumber(i) == 0) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidTag();
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(i)) {
            case 0:
                return decodeVarint64(bArr, i2, registers);
            case 1:
                return i2 + 8;
            case 2:
                return decodeVarint32(bArr, i2, registers) + registers.int1;
            case 3:
                int i4 = (i & (-8)) | 4;
                int i5 = 0;
                while (i2 < i3) {
                    i2 = decodeVarint32(bArr, i2, registers);
                    i5 = registers.int1;
                    if (i5 != i4) {
                        i2 = skipField(i5, bArr, i2, i3, registers);
                    } else {
                        if (i2 <= i3 || i5 != i4) {
                            throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
                        }
                        return i2;
                    }
                }
                if (i2 <= i3) {
                }
                throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
            case 4:
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidTag();
            case 5:
                return i2 + 4;
        }
    }
}
