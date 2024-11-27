package com.android.framework.protobuf.nano;

/* loaded from: classes4.dex */
public final class CodedOutputByteBufferNano {
    public static final int LITTLE_ENDIAN_32_SIZE = 4;
    public static final int LITTLE_ENDIAN_64_SIZE = 8;
    private static final int MAX_UTF8_EXPANSION = 3;
    private final java.nio.ByteBuffer buffer;

    private CodedOutputByteBufferNano(byte[] bArr, int i, int i2) {
        this(java.nio.ByteBuffer.wrap(bArr, i, i2));
    }

    private CodedOutputByteBufferNano(java.nio.ByteBuffer byteBuffer) {
        this.buffer = byteBuffer;
        this.buffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
    }

    public static com.android.framework.protobuf.nano.CodedOutputByteBufferNano newInstance(byte[] bArr) {
        return newInstance(bArr, 0, bArr.length);
    }

    public static com.android.framework.protobuf.nano.CodedOutputByteBufferNano newInstance(byte[] bArr, int i, int i2) {
        return new com.android.framework.protobuf.nano.CodedOutputByteBufferNano(bArr, i, i2);
    }

    public void writeDouble(int i, double d) throws java.io.IOException {
        writeTag(i, 1);
        writeDoubleNoTag(d);
    }

    public void writeFloat(int i, float f) throws java.io.IOException {
        writeTag(i, 5);
        writeFloatNoTag(f);
    }

    public void writeUInt64(int i, long j) throws java.io.IOException {
        writeTag(i, 0);
        writeUInt64NoTag(j);
    }

    public void writeInt64(int i, long j) throws java.io.IOException {
        writeTag(i, 0);
        writeInt64NoTag(j);
    }

    public void writeInt32(int i, int i2) throws java.io.IOException {
        writeTag(i, 0);
        writeInt32NoTag(i2);
    }

    public void writeFixed64(int i, long j) throws java.io.IOException {
        writeTag(i, 1);
        writeFixed64NoTag(j);
    }

    public void writeFixed32(int i, int i2) throws java.io.IOException {
        writeTag(i, 5);
        writeFixed32NoTag(i2);
    }

    public void writeBool(int i, boolean z) throws java.io.IOException {
        writeTag(i, 0);
        writeBoolNoTag(z);
    }

    public void writeString(int i, java.lang.String str) throws java.io.IOException {
        writeTag(i, 2);
        writeStringNoTag(str);
    }

    public void writeGroup(int i, com.android.framework.protobuf.nano.MessageNano messageNano) throws java.io.IOException {
        writeTag(i, 3);
        writeGroupNoTag(messageNano);
        writeTag(i, 4);
    }

    public void writeMessage(int i, com.android.framework.protobuf.nano.MessageNano messageNano) throws java.io.IOException {
        writeTag(i, 2);
        writeMessageNoTag(messageNano);
    }

    public void writeBytes(int i, byte[] bArr) throws java.io.IOException {
        writeTag(i, 2);
        writeBytesNoTag(bArr);
    }

    public void writeUInt32(int i, int i2) throws java.io.IOException {
        writeTag(i, 0);
        writeUInt32NoTag(i2);
    }

    public void writeEnum(int i, int i2) throws java.io.IOException {
        writeTag(i, 0);
        writeEnumNoTag(i2);
    }

    public void writeSFixed32(int i, int i2) throws java.io.IOException {
        writeTag(i, 5);
        writeSFixed32NoTag(i2);
    }

    public void writeSFixed64(int i, long j) throws java.io.IOException {
        writeTag(i, 1);
        writeSFixed64NoTag(j);
    }

    public void writeSInt32(int i, int i2) throws java.io.IOException {
        writeTag(i, 0);
        writeSInt32NoTag(i2);
    }

    public void writeSInt64(int i, long j) throws java.io.IOException {
        writeTag(i, 0);
        writeSInt64NoTag(j);
    }

    public void writeDoubleNoTag(double d) throws java.io.IOException {
        writeRawLittleEndian64(java.lang.Double.doubleToLongBits(d));
    }

    public void writeFloatNoTag(float f) throws java.io.IOException {
        writeRawLittleEndian32(java.lang.Float.floatToIntBits(f));
    }

    public void writeUInt64NoTag(long j) throws java.io.IOException {
        writeRawVarint64(j);
    }

    public void writeInt64NoTag(long j) throws java.io.IOException {
        writeRawVarint64(j);
    }

    public void writeInt32NoTag(int i) throws java.io.IOException {
        if (i >= 0) {
            writeRawVarint32(i);
        } else {
            writeRawVarint64(i);
        }
    }

    public void writeFixed64NoTag(long j) throws java.io.IOException {
        writeRawLittleEndian64(j);
    }

    public void writeFixed32NoTag(int i) throws java.io.IOException {
        writeRawLittleEndian32(i);
    }

    public void writeBoolNoTag(boolean z) throws java.io.IOException {
        writeRawByte(z ? 1 : 0);
    }

    public void writeStringNoTag(java.lang.String str) throws java.io.IOException {
        try {
            int computeRawVarint32Size = computeRawVarint32Size(str.length());
            if (computeRawVarint32Size == computeRawVarint32Size(str.length() * 3)) {
                int position = this.buffer.position();
                if (this.buffer.remaining() < computeRawVarint32Size) {
                    throw new com.android.framework.protobuf.nano.CodedOutputByteBufferNano.OutOfSpaceException(position + computeRawVarint32Size, this.buffer.limit());
                }
                this.buffer.position(position + computeRawVarint32Size);
                encode(str, this.buffer);
                int position2 = this.buffer.position();
                this.buffer.position(position);
                writeRawVarint32((position2 - position) - computeRawVarint32Size);
                this.buffer.position(position2);
                return;
            }
            writeRawVarint32(encodedLength(str));
            encode(str, this.buffer);
        } catch (java.nio.BufferOverflowException e) {
            com.android.framework.protobuf.nano.CodedOutputByteBufferNano.OutOfSpaceException outOfSpaceException = new com.android.framework.protobuf.nano.CodedOutputByteBufferNano.OutOfSpaceException(this.buffer.position(), this.buffer.limit());
            outOfSpaceException.initCause(e);
            throw outOfSpaceException;
        }
    }

    private static int encodedLength(java.lang.CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) < 128) {
            i++;
        }
        int i2 = length;
        while (true) {
            if (i < length) {
                char charAt = charSequence.charAt(i);
                if (charAt < 2048) {
                    i2 += (127 - charAt) >>> 31;
                    i++;
                } else {
                    i2 += encodedLengthGeneral(charSequence, i);
                    break;
                }
            } else {
                break;
            }
        }
        if (i2 < length) {
            throw new java.lang.IllegalArgumentException("UTF-8 length does not fit in int: " + (i2 + 4294967296L));
        }
        return i2;
    }

    private static int encodedLengthGeneral(java.lang.CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < 2048) {
                i2 += (127 - charAt) >>> 31;
            } else {
                i2 += 2;
                if (55296 <= charAt && charAt <= 57343) {
                    if (java.lang.Character.codePointAt(charSequence, i) < 65536) {
                        throw new java.lang.IllegalArgumentException("Unpaired surrogate at index " + i);
                    }
                    i++;
                }
            }
            i++;
        }
        return i2;
    }

    private static void encode(java.lang.CharSequence charSequence, java.nio.ByteBuffer byteBuffer) {
        if (byteBuffer.isReadOnly()) {
            throw new java.nio.ReadOnlyBufferException();
        }
        if (byteBuffer.hasArray()) {
            try {
                byteBuffer.position(encode(charSequence, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining()) - byteBuffer.arrayOffset());
                return;
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                java.nio.BufferOverflowException bufferOverflowException = new java.nio.BufferOverflowException();
                bufferOverflowException.initCause(e);
                throw bufferOverflowException;
            }
        }
        encodeDirect(charSequence, byteBuffer);
    }

    private static void encodeDirect(java.lang.CharSequence charSequence, java.nio.ByteBuffer byteBuffer) {
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < 128) {
                byteBuffer.put((byte) charAt);
            } else if (charAt < 2048) {
                byteBuffer.put((byte) ((charAt >>> 6) | 960));
                byteBuffer.put((byte) ((charAt & '?') | 128));
            } else if (charAt < 55296 || 57343 < charAt) {
                byteBuffer.put((byte) ((charAt >>> '\f') | 480));
                byteBuffer.put((byte) (((charAt >>> 6) & 63) | 128));
                byteBuffer.put((byte) ((charAt & '?') | 128));
            } else {
                int i2 = i + 1;
                if (i2 != charSequence.length()) {
                    char charAt2 = charSequence.charAt(i2);
                    if (!java.lang.Character.isSurrogatePair(charAt, charAt2)) {
                        i = i2;
                    } else {
                        int codePoint = java.lang.Character.toCodePoint(charAt, charAt2);
                        byteBuffer.put((byte) ((codePoint >>> 18) | 240));
                        byteBuffer.put((byte) (((codePoint >>> 12) & 63) | 128));
                        byteBuffer.put((byte) (((codePoint >>> 6) & 63) | 128));
                        byteBuffer.put((byte) ((codePoint & 63) | 128));
                        i = i2;
                    }
                }
                throw new java.lang.IllegalArgumentException("Unpaired surrogate at index " + (i - 1));
            }
            i++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001f, code lost:
    
        return r8 + r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int encode(java.lang.CharSequence charSequence, byte[] bArr, int i, int i2) {
        int i3;
        char charAt;
        int length = charSequence.length();
        int i4 = i2 + i;
        int i5 = 0;
        while (i5 < length && (i3 = i5 + i) < i4 && (charAt = charSequence.charAt(i5)) < 128) {
            bArr[i3] = (byte) charAt;
            i5++;
        }
        int i6 = i + i5;
        while (i5 < length) {
            char charAt2 = charSequence.charAt(i5);
            if (charAt2 < 128 && i6 < i4) {
                bArr[i6] = (byte) charAt2;
                i6++;
            } else if (charAt2 < 2048 && i6 <= i4 - 2) {
                int i7 = i6 + 1;
                bArr[i6] = (byte) ((charAt2 >>> 6) | 960);
                i6 = i7 + 1;
                bArr[i7] = (byte) ((charAt2 & '?') | 128);
            } else if ((charAt2 < 55296 || 57343 < charAt2) && i6 <= i4 - 3) {
                int i8 = i6 + 1;
                bArr[i6] = (byte) ((charAt2 >>> '\f') | 480);
                int i9 = i8 + 1;
                bArr[i8] = (byte) (((charAt2 >>> 6) & 63) | 128);
                bArr[i9] = (byte) ((charAt2 & '?') | 128);
                i6 = i9 + 1;
            } else {
                if (i6 <= i4 - 4) {
                    int i10 = i5 + 1;
                    if (i10 != charSequence.length()) {
                        char charAt3 = charSequence.charAt(i10);
                        if (!java.lang.Character.isSurrogatePair(charAt2, charAt3)) {
                            i5 = i10;
                        } else {
                            int codePoint = java.lang.Character.toCodePoint(charAt2, charAt3);
                            int i11 = i6 + 1;
                            bArr[i6] = (byte) ((codePoint >>> 18) | 240);
                            int i12 = i11 + 1;
                            bArr[i11] = (byte) (((codePoint >>> 12) & 63) | 128);
                            int i13 = i12 + 1;
                            bArr[i12] = (byte) (((codePoint >>> 6) & 63) | 128);
                            i6 = i13 + 1;
                            bArr[i13] = (byte) ((codePoint & 63) | 128);
                            i5 = i10;
                        }
                    }
                    throw new java.lang.IllegalArgumentException("Unpaired surrogate at index " + (i5 - 1));
                }
                throw new java.lang.ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + i6);
            }
            i5++;
        }
        return i6;
    }

    public void writeGroupNoTag(com.android.framework.protobuf.nano.MessageNano messageNano) throws java.io.IOException {
        messageNano.writeTo(this);
    }

    public void writeMessageNoTag(com.android.framework.protobuf.nano.MessageNano messageNano) throws java.io.IOException {
        writeRawVarint32(messageNano.getCachedSize());
        messageNano.writeTo(this);
    }

    public void writeBytesNoTag(byte[] bArr) throws java.io.IOException {
        writeRawVarint32(bArr.length);
        writeRawBytes(bArr);
    }

    public void writeUInt32NoTag(int i) throws java.io.IOException {
        writeRawVarint32(i);
    }

    public void writeEnumNoTag(int i) throws java.io.IOException {
        writeRawVarint32(i);
    }

    public void writeSFixed32NoTag(int i) throws java.io.IOException {
        writeRawLittleEndian32(i);
    }

    public void writeSFixed64NoTag(long j) throws java.io.IOException {
        writeRawLittleEndian64(j);
    }

    public void writeSInt32NoTag(int i) throws java.io.IOException {
        writeRawVarint32(encodeZigZag32(i));
    }

    public void writeSInt64NoTag(long j) throws java.io.IOException {
        writeRawVarint64(encodeZigZag64(j));
    }

    public static int computeDoubleSize(int i, double d) {
        return computeTagSize(i) + computeDoubleSizeNoTag(d);
    }

    public static int computeFloatSize(int i, float f) {
        return computeTagSize(i) + computeFloatSizeNoTag(f);
    }

    public static int computeUInt64Size(int i, long j) {
        return computeTagSize(i) + computeUInt64SizeNoTag(j);
    }

    public static int computeInt64Size(int i, long j) {
        return computeTagSize(i) + computeInt64SizeNoTag(j);
    }

    public static int computeInt32Size(int i, int i2) {
        return computeTagSize(i) + computeInt32SizeNoTag(i2);
    }

    public static int computeFixed64Size(int i, long j) {
        return computeTagSize(i) + computeFixed64SizeNoTag(j);
    }

    public static int computeFixed32Size(int i, int i2) {
        return computeTagSize(i) + computeFixed32SizeNoTag(i2);
    }

    public static int computeBoolSize(int i, boolean z) {
        return computeTagSize(i) + computeBoolSizeNoTag(z);
    }

    public static int computeStringSize(int i, java.lang.String str) {
        return computeTagSize(i) + computeStringSizeNoTag(str);
    }

    public static int computeGroupSize(int i, com.android.framework.protobuf.nano.MessageNano messageNano) {
        return (computeTagSize(i) * 2) + computeGroupSizeNoTag(messageNano);
    }

    public static int computeMessageSize(int i, com.android.framework.protobuf.nano.MessageNano messageNano) {
        return computeTagSize(i) + computeMessageSizeNoTag(messageNano);
    }

    public static int computeBytesSize(int i, byte[] bArr) {
        return computeTagSize(i) + computeBytesSizeNoTag(bArr);
    }

    public static int computeUInt32Size(int i, int i2) {
        return computeTagSize(i) + computeUInt32SizeNoTag(i2);
    }

    public static int computeEnumSize(int i, int i2) {
        return computeTagSize(i) + computeEnumSizeNoTag(i2);
    }

    public static int computeSFixed32Size(int i, int i2) {
        return computeTagSize(i) + computeSFixed32SizeNoTag(i2);
    }

    public static int computeSFixed64Size(int i, long j) {
        return computeTagSize(i) + computeSFixed64SizeNoTag(j);
    }

    public static int computeSInt32Size(int i, int i2) {
        return computeTagSize(i) + computeSInt32SizeNoTag(i2);
    }

    public static int computeSInt64Size(int i, long j) {
        return computeTagSize(i) + computeSInt64SizeNoTag(j);
    }

    public static int computeDoubleSizeNoTag(double d) {
        return 8;
    }

    public static int computeFloatSizeNoTag(float f) {
        return 4;
    }

    public static int computeUInt64SizeNoTag(long j) {
        return computeRawVarint64Size(j);
    }

    public static int computeInt64SizeNoTag(long j) {
        return computeRawVarint64Size(j);
    }

    public static int computeInt32SizeNoTag(int i) {
        if (i >= 0) {
            return computeRawVarint32Size(i);
        }
        return 10;
    }

    public static int computeFixed64SizeNoTag(long j) {
        return 8;
    }

    public static int computeFixed32SizeNoTag(int i) {
        return 4;
    }

    public static int computeBoolSizeNoTag(boolean z) {
        return 1;
    }

    public static int computeStringSizeNoTag(java.lang.String str) {
        int encodedLength = encodedLength(str);
        return computeRawVarint32Size(encodedLength) + encodedLength;
    }

    public static int computeGroupSizeNoTag(com.android.framework.protobuf.nano.MessageNano messageNano) {
        return messageNano.getSerializedSize();
    }

    public static int computeMessageSizeNoTag(com.android.framework.protobuf.nano.MessageNano messageNano) {
        int serializedSize = messageNano.getSerializedSize();
        return computeRawVarint32Size(serializedSize) + serializedSize;
    }

    public static int computeBytesSizeNoTag(byte[] bArr) {
        return computeRawVarint32Size(bArr.length) + bArr.length;
    }

    public static int computeUInt32SizeNoTag(int i) {
        return computeRawVarint32Size(i);
    }

    public static int computeEnumSizeNoTag(int i) {
        return computeRawVarint32Size(i);
    }

    public static int computeSFixed32SizeNoTag(int i) {
        return 4;
    }

    public static int computeSFixed64SizeNoTag(long j) {
        return 8;
    }

    public static int computeSInt32SizeNoTag(int i) {
        return computeRawVarint32Size(encodeZigZag32(i));
    }

    public static int computeSInt64SizeNoTag(long j) {
        return computeRawVarint64Size(encodeZigZag64(j));
    }

    public int spaceLeft() {
        return this.buffer.remaining();
    }

    public void checkNoSpaceLeft() {
        if (spaceLeft() != 0) {
            throw new java.lang.IllegalStateException("Did not write as much data as expected.");
        }
    }

    public int position() {
        return this.buffer.position();
    }

    public void reset() {
        this.buffer.clear();
    }

    public static class OutOfSpaceException extends java.io.IOException {
        private static final long serialVersionUID = -6947486886997889499L;

        OutOfSpaceException(int i, int i2) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space (pos " + i + " limit " + i2 + ").");
        }
    }

    public void writeRawByte(byte b) throws java.io.IOException {
        if (!this.buffer.hasRemaining()) {
            throw new com.android.framework.protobuf.nano.CodedOutputByteBufferNano.OutOfSpaceException(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.put(b);
    }

    public void writeRawByte(int i) throws java.io.IOException {
        writeRawByte((byte) i);
    }

    public void writeRawBytes(byte[] bArr) throws java.io.IOException {
        writeRawBytes(bArr, 0, bArr.length);
    }

    public void writeRawBytes(byte[] bArr, int i, int i2) throws java.io.IOException {
        if (this.buffer.remaining() >= i2) {
            this.buffer.put(bArr, i, i2);
            return;
        }
        throw new com.android.framework.protobuf.nano.CodedOutputByteBufferNano.OutOfSpaceException(this.buffer.position(), this.buffer.limit());
    }

    public void writeTag(int i, int i2) throws java.io.IOException {
        writeRawVarint32(com.android.framework.protobuf.nano.WireFormatNano.makeTag(i, i2));
    }

    public static int computeTagSize(int i) {
        return computeRawVarint32Size(com.android.framework.protobuf.nano.WireFormatNano.makeTag(i, 0));
    }

    public void writeRawVarint32(int i) throws java.io.IOException {
        while ((i & (-128)) != 0) {
            writeRawByte((i & 127) | 128);
            i >>>= 7;
        }
        writeRawByte(i);
    }

    public static int computeRawVarint32Size(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & android.content.pm.PackageManager.FLAGS_PERMISSION_RESERVED_PERMISSION_CONTROLLER) == 0 ? 4 : 5;
    }

    public void writeRawVarint64(long j) throws java.io.IOException {
        while (((-128) & j) != 0) {
            writeRawByte((((int) j) & 127) | 128);
            j >>>= 7;
        }
        writeRawByte((int) j);
    }

    public static int computeRawVarint64Size(long j) {
        if (((-128) & j) == 0) {
            return 1;
        }
        if (((-16384) & j) == 0) {
            return 2;
        }
        if (((-2097152) & j) == 0) {
            return 3;
        }
        if (((-268435456) & j) == 0) {
            return 4;
        }
        if (((-34359738368L) & j) == 0) {
            return 5;
        }
        if (((-4398046511104L) & j) == 0) {
            return 6;
        }
        if (((-562949953421312L) & j) == 0) {
            return 7;
        }
        if ((android.os.BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK & j) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    public void writeRawLittleEndian32(int i) throws java.io.IOException {
        if (this.buffer.remaining() < 4) {
            throw new com.android.framework.protobuf.nano.CodedOutputByteBufferNano.OutOfSpaceException(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.putInt(i);
    }

    public void writeRawLittleEndian64(long j) throws java.io.IOException {
        if (this.buffer.remaining() < 8) {
            throw new com.android.framework.protobuf.nano.CodedOutputByteBufferNano.OutOfSpaceException(this.buffer.position(), this.buffer.limit());
        }
        this.buffer.putLong(j);
    }

    public static int encodeZigZag32(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static long encodeZigZag64(long j) {
        return (j >> 63) ^ (j << 1);
    }

    static int computeFieldSize(int i, int i2, java.lang.Object obj) {
        switch (i2) {
            case 1:
                return computeDoubleSize(i, ((java.lang.Double) obj).doubleValue());
            case 2:
                return computeFloatSize(i, ((java.lang.Float) obj).floatValue());
            case 3:
                return computeInt64Size(i, ((java.lang.Long) obj).longValue());
            case 4:
                return computeUInt64Size(i, ((java.lang.Long) obj).longValue());
            case 5:
                return computeInt32Size(i, ((java.lang.Integer) obj).intValue());
            case 6:
                return computeFixed64Size(i, ((java.lang.Long) obj).longValue());
            case 7:
                return computeFixed32Size(i, ((java.lang.Integer) obj).intValue());
            case 8:
                return computeBoolSize(i, ((java.lang.Boolean) obj).booleanValue());
            case 9:
                return computeStringSize(i, (java.lang.String) obj);
            case 10:
                return computeGroupSize(i, (com.android.framework.protobuf.nano.MessageNano) obj);
            case 11:
                return computeMessageSize(i, (com.android.framework.protobuf.nano.MessageNano) obj);
            case 12:
                return computeBytesSize(i, (byte[]) obj);
            case 13:
                return computeUInt32Size(i, ((java.lang.Integer) obj).intValue());
            case 14:
                return computeEnumSize(i, ((java.lang.Integer) obj).intValue());
            case 15:
                return computeSFixed32Size(i, ((java.lang.Integer) obj).intValue());
            case 16:
                return computeSFixed64Size(i, ((java.lang.Long) obj).longValue());
            case 17:
                return computeSInt32Size(i, ((java.lang.Integer) obj).intValue());
            case 18:
                return computeSInt64Size(i, ((java.lang.Long) obj).longValue());
            default:
                throw new java.lang.IllegalArgumentException("Unknown type: " + i2);
        }
    }

    void writeField(int i, int i2, java.lang.Object obj) throws java.io.IOException {
        switch (i2) {
            case 1:
                writeDouble(i, ((java.lang.Double) obj).doubleValue());
                return;
            case 2:
                writeFloat(i, ((java.lang.Float) obj).floatValue());
                return;
            case 3:
                writeInt64(i, ((java.lang.Long) obj).longValue());
                return;
            case 4:
                writeUInt64(i, ((java.lang.Long) obj).longValue());
                return;
            case 5:
                writeInt32(i, ((java.lang.Integer) obj).intValue());
                return;
            case 6:
                writeFixed64(i, ((java.lang.Long) obj).longValue());
                return;
            case 7:
                writeFixed32(i, ((java.lang.Integer) obj).intValue());
                return;
            case 8:
                writeBool(i, ((java.lang.Boolean) obj).booleanValue());
                return;
            case 9:
                writeString(i, (java.lang.String) obj);
                return;
            case 10:
                writeGroup(i, (com.android.framework.protobuf.nano.MessageNano) obj);
                return;
            case 11:
                writeMessage(i, (com.android.framework.protobuf.nano.MessageNano) obj);
                return;
            case 12:
                writeBytes(i, (byte[]) obj);
                return;
            case 13:
                writeUInt32(i, ((java.lang.Integer) obj).intValue());
                return;
            case 14:
                writeEnum(i, ((java.lang.Integer) obj).intValue());
                return;
            case 15:
                writeSFixed32(i, ((java.lang.Integer) obj).intValue());
                return;
            case 16:
                writeSFixed64(i, ((java.lang.Long) obj).longValue());
                return;
            case 17:
                writeSInt32(i, ((java.lang.Integer) obj).intValue());
                return;
            case 18:
                writeSInt64(i, ((java.lang.Long) obj).longValue());
                return;
            default:
                throw new java.io.IOException("Unknown type: " + i2);
        }
    }
}
