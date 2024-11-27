package com.android.framework.protobuf.nano;

/* loaded from: classes4.dex */
public final class CodedInputByteBufferNano {
    private static final int DEFAULT_RECURSION_LIMIT = 64;
    private static final int DEFAULT_SIZE_LIMIT = 67108864;
    private final byte[] buffer;
    private int bufferPos;
    private int bufferSize;
    private int bufferSizeAfterLimit;
    private int bufferStart;
    private int lastTag;
    private int recursionDepth;
    private int currentLimit = Integer.MAX_VALUE;
    private int recursionLimit = 64;
    private int sizeLimit = 67108864;

    public static com.android.framework.protobuf.nano.CodedInputByteBufferNano newInstance(byte[] bArr) {
        return newInstance(bArr, 0, bArr.length);
    }

    public static com.android.framework.protobuf.nano.CodedInputByteBufferNano newInstance(byte[] bArr, int i, int i2) {
        return new com.android.framework.protobuf.nano.CodedInputByteBufferNano(bArr, i, i2);
    }

    public int readTag() throws java.io.IOException {
        if (isAtEnd()) {
            this.lastTag = 0;
            return 0;
        }
        this.lastTag = readRawVarint32();
        if (this.lastTag == 0) {
            throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.invalidTag();
        }
        return this.lastTag;
    }

    public void checkLastTagWas(int i) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
        if (this.lastTag != i) {
            throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.invalidEndTag();
        }
    }

    public boolean skipField(int i) throws java.io.IOException {
        switch (com.android.framework.protobuf.nano.WireFormatNano.getTagWireType(i)) {
            case 0:
                readInt32();
                return true;
            case 1:
                readRawLittleEndian64();
                return true;
            case 2:
                skipRawBytes(readRawVarint32());
                return true;
            case 3:
                skipMessage();
                checkLastTagWas(com.android.framework.protobuf.nano.WireFormatNano.makeTag(com.android.framework.protobuf.nano.WireFormatNano.getTagFieldNumber(i), 4));
                return true;
            case 4:
                return false;
            case 5:
                readRawLittleEndian32();
                return true;
            default:
                throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.invalidWireType();
        }
    }

    public void skipMessage() throws java.io.IOException {
        int readTag;
        do {
            readTag = readTag();
            if (readTag == 0) {
                return;
            }
        } while (skipField(readTag));
    }

    public double readDouble() throws java.io.IOException {
        return java.lang.Double.longBitsToDouble(readRawLittleEndian64());
    }

    public float readFloat() throws java.io.IOException {
        return java.lang.Float.intBitsToFloat(readRawLittleEndian32());
    }

    public long readUInt64() throws java.io.IOException {
        return readRawVarint64();
    }

    public long readInt64() throws java.io.IOException {
        return readRawVarint64();
    }

    public int readInt32() throws java.io.IOException {
        return readRawVarint32();
    }

    public long readFixed64() throws java.io.IOException {
        return readRawLittleEndian64();
    }

    public int readFixed32() throws java.io.IOException {
        return readRawLittleEndian32();
    }

    public boolean readBool() throws java.io.IOException {
        return readRawVarint32() != 0;
    }

    public java.lang.String readString() throws java.io.IOException {
        int readRawVarint32 = readRawVarint32();
        if (readRawVarint32 <= this.bufferSize - this.bufferPos && readRawVarint32 > 0) {
            java.lang.String str = new java.lang.String(this.buffer, this.bufferPos, readRawVarint32, com.android.framework.protobuf.nano.InternalNano.UTF_8);
            this.bufferPos += readRawVarint32;
            return str;
        }
        return new java.lang.String(readRawBytes(readRawVarint32), com.android.framework.protobuf.nano.InternalNano.UTF_8);
    }

    public void readGroup(com.android.framework.protobuf.nano.MessageNano messageNano, int i) throws java.io.IOException {
        if (this.recursionDepth >= this.recursionLimit) {
            throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.recursionLimitExceeded();
        }
        this.recursionDepth++;
        messageNano.mergeFrom(this);
        checkLastTagWas(com.android.framework.protobuf.nano.WireFormatNano.makeTag(i, 4));
        this.recursionDepth--;
    }

    public void readMessage(com.android.framework.protobuf.nano.MessageNano messageNano) throws java.io.IOException {
        int readRawVarint32 = readRawVarint32();
        if (this.recursionDepth >= this.recursionLimit) {
            throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.recursionLimitExceeded();
        }
        int pushLimit = pushLimit(readRawVarint32);
        this.recursionDepth++;
        messageNano.mergeFrom(this);
        checkLastTagWas(0);
        this.recursionDepth--;
        popLimit(pushLimit);
    }

    public byte[] readBytes() throws java.io.IOException {
        int readRawVarint32 = readRawVarint32();
        if (readRawVarint32 <= this.bufferSize - this.bufferPos && readRawVarint32 > 0) {
            byte[] bArr = new byte[readRawVarint32];
            java.lang.System.arraycopy(this.buffer, this.bufferPos, bArr, 0, readRawVarint32);
            this.bufferPos += readRawVarint32;
            return bArr;
        }
        if (readRawVarint32 == 0) {
            return com.android.framework.protobuf.nano.WireFormatNano.EMPTY_BYTES;
        }
        return readRawBytes(readRawVarint32);
    }

    public int readUInt32() throws java.io.IOException {
        return readRawVarint32();
    }

    public int readEnum() throws java.io.IOException {
        return readRawVarint32();
    }

    public int readSFixed32() throws java.io.IOException {
        return readRawLittleEndian32();
    }

    public long readSFixed64() throws java.io.IOException {
        return readRawLittleEndian64();
    }

    public int readSInt32() throws java.io.IOException {
        return decodeZigZag32(readRawVarint32());
    }

    public long readSInt64() throws java.io.IOException {
        return decodeZigZag64(readRawVarint64());
    }

    public int readRawVarint32() throws java.io.IOException {
        byte readRawByte = readRawByte();
        if (readRawByte >= 0) {
            return readRawByte;
        }
        int i = readRawByte & Byte.MAX_VALUE;
        byte readRawByte2 = readRawByte();
        if (readRawByte2 >= 0) {
            return i | (readRawByte2 << 7);
        }
        int i2 = i | ((readRawByte2 & Byte.MAX_VALUE) << 7);
        byte readRawByte3 = readRawByte();
        if (readRawByte3 >= 0) {
            return i2 | (readRawByte3 << 14);
        }
        int i3 = i2 | ((readRawByte3 & Byte.MAX_VALUE) << 14);
        byte readRawByte4 = readRawByte();
        if (readRawByte4 >= 0) {
            return i3 | (readRawByte4 << android.hardware.biometrics.face.AcquiredInfo.START);
        }
        int i4 = i3 | ((readRawByte4 & Byte.MAX_VALUE) << 21);
        byte readRawByte5 = readRawByte();
        int i5 = i4 | (readRawByte5 << 28);
        if (readRawByte5 < 0) {
            for (int i6 = 0; i6 < 5; i6++) {
                if (readRawByte() >= 0) {
                    return i5;
                }
            }
            throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.malformedVarint();
        }
        return i5;
    }

    public long readRawVarint64() throws java.io.IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            j |= (r3 & Byte.MAX_VALUE) << i;
            if ((readRawByte() & 128) == 0) {
                return j;
            }
        }
        throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.malformedVarint();
    }

    public int readRawLittleEndian32() throws java.io.IOException {
        return (readRawByte() & 255) | ((readRawByte() & 255) << 8) | ((readRawByte() & 255) << 16) | ((readRawByte() & 255) << 24);
    }

    public long readRawLittleEndian64() throws java.io.IOException {
        return ((readRawByte() & 255) << 8) | (readRawByte() & 255) | ((readRawByte() & 255) << 16) | ((readRawByte() & 255) << 24) | ((readRawByte() & 255) << 32) | ((readRawByte() & 255) << 40) | ((readRawByte() & 255) << 48) | ((readRawByte() & 255) << 56);
    }

    public static int decodeZigZag32(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public static long decodeZigZag64(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    private CodedInputByteBufferNano(byte[] bArr, int i, int i2) {
        this.buffer = bArr;
        this.bufferStart = i;
        this.bufferSize = i2 + i;
        this.bufferPos = i;
    }

    public int setRecursionLimit(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Recursion limit cannot be negative: " + i);
        }
        int i2 = this.recursionLimit;
        this.recursionLimit = i;
        return i2;
    }

    public int setSizeLimit(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Size limit cannot be negative: " + i);
        }
        int i2 = this.sizeLimit;
        this.sizeLimit = i;
        return i2;
    }

    public void resetSizeCounter() {
    }

    public int pushLimit(int i) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
        if (i < 0) {
            throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.negativeSize();
        }
        int i2 = i + this.bufferPos;
        int i3 = this.currentLimit;
        if (i2 > i3) {
            throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.truncatedMessage();
        }
        this.currentLimit = i2;
        recomputeBufferSizeAfterLimit();
        return i3;
    }

    private void recomputeBufferSizeAfterLimit() {
        this.bufferSize += this.bufferSizeAfterLimit;
        int i = this.bufferSize;
        if (i > this.currentLimit) {
            this.bufferSizeAfterLimit = i - this.currentLimit;
            this.bufferSize -= this.bufferSizeAfterLimit;
        } else {
            this.bufferSizeAfterLimit = 0;
        }
    }

    public void popLimit(int i) {
        this.currentLimit = i;
        recomputeBufferSizeAfterLimit();
    }

    public int getBytesUntilLimit() {
        if (this.currentLimit == Integer.MAX_VALUE) {
            return -1;
        }
        return this.currentLimit - this.bufferPos;
    }

    public boolean isAtEnd() {
        return this.bufferPos == this.bufferSize;
    }

    public int getPosition() {
        return this.bufferPos - this.bufferStart;
    }

    public byte[] getData(int i, int i2) {
        if (i2 == 0) {
            return com.android.framework.protobuf.nano.WireFormatNano.EMPTY_BYTES;
        }
        byte[] bArr = new byte[i2];
        java.lang.System.arraycopy(this.buffer, this.bufferStart + i, bArr, 0, i2);
        return bArr;
    }

    public void rewindToPosition(int i) {
        if (i > this.bufferPos - this.bufferStart) {
            throw new java.lang.IllegalArgumentException("Position " + i + " is beyond current " + (this.bufferPos - this.bufferStart));
        }
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Bad position " + i);
        }
        this.bufferPos = this.bufferStart + i;
    }

    public byte readRawByte() throws java.io.IOException {
        if (this.bufferPos == this.bufferSize) {
            throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.truncatedMessage();
        }
        byte[] bArr = this.buffer;
        int i = this.bufferPos;
        this.bufferPos = i + 1;
        return bArr[i];
    }

    public byte[] readRawBytes(int i) throws java.io.IOException {
        if (i < 0) {
            throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.negativeSize();
        }
        if (this.bufferPos + i > this.currentLimit) {
            skipRawBytes(this.currentLimit - this.bufferPos);
            throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.truncatedMessage();
        }
        if (i <= this.bufferSize - this.bufferPos) {
            byte[] bArr = new byte[i];
            java.lang.System.arraycopy(this.buffer, this.bufferPos, bArr, 0, i);
            this.bufferPos += i;
            return bArr;
        }
        throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.truncatedMessage();
    }

    public void skipRawBytes(int i) throws java.io.IOException {
        if (i < 0) {
            throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.negativeSize();
        }
        if (this.bufferPos + i > this.currentLimit) {
            skipRawBytes(this.currentLimit - this.bufferPos);
            throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.truncatedMessage();
        }
        if (i <= this.bufferSize - this.bufferPos) {
            this.bufferPos += i;
            return;
        }
        throw com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException.truncatedMessage();
    }

    java.lang.Object readPrimitiveField(int i) throws java.io.IOException {
        switch (i) {
            case 1:
                return java.lang.Double.valueOf(readDouble());
            case 2:
                return java.lang.Float.valueOf(readFloat());
            case 3:
                return java.lang.Long.valueOf(readInt64());
            case 4:
                return java.lang.Long.valueOf(readUInt64());
            case 5:
                return java.lang.Integer.valueOf(readInt32());
            case 6:
                return java.lang.Long.valueOf(readFixed64());
            case 7:
                return java.lang.Integer.valueOf(readFixed32());
            case 8:
                return java.lang.Boolean.valueOf(readBool());
            case 9:
                return readString();
            case 10:
            case 11:
            default:
                throw new java.lang.IllegalArgumentException("Unknown type " + i);
            case 12:
                return readBytes();
            case 13:
                return java.lang.Integer.valueOf(readUInt32());
            case 14:
                return java.lang.Integer.valueOf(readEnum());
            case 15:
                return java.lang.Integer.valueOf(readSFixed32());
            case 16:
                return java.lang.Long.valueOf(readSFixed64());
            case 17:
                return java.lang.Integer.valueOf(readSInt32());
            case 18:
                return java.lang.Long.valueOf(readSInt64());
        }
    }
}
