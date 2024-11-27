package android.util.proto;

/* loaded from: classes3.dex */
public final class ProtoInputStream extends android.util.proto.ProtoStream {
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int NO_MORE_FIELDS = -1;
    private static final byte STATE_FIELD_MISS = 4;
    private static final byte STATE_READING_PACKED = 2;
    private static final byte STATE_STARTED_FIELD_READ = 1;
    private byte[] mBuffer;
    private final int mBufferSize;
    private int mDepth;
    private int mDiscardedBytes;
    private int mEnd;
    private android.util.LongArray mExpectedObjectTokenStack;
    private int mFieldNumber;
    private int mOffset;
    private int mPackedEnd;
    private byte mState;
    private java.io.InputStream mStream;
    private int mWireType;

    public ProtoInputStream(java.io.InputStream inputStream, int i) {
        this.mState = (byte) 0;
        this.mExpectedObjectTokenStack = null;
        this.mDepth = -1;
        this.mDiscardedBytes = 0;
        this.mOffset = 0;
        this.mEnd = 0;
        this.mPackedEnd = 0;
        this.mStream = inputStream;
        if (i > 0) {
            this.mBufferSize = i;
        } else {
            this.mBufferSize = 8192;
        }
        this.mBuffer = new byte[this.mBufferSize];
    }

    public ProtoInputStream(java.io.InputStream inputStream) {
        this(inputStream, 8192);
    }

    public ProtoInputStream(byte[] bArr) {
        this.mState = (byte) 0;
        this.mExpectedObjectTokenStack = null;
        this.mDepth = -1;
        this.mDiscardedBytes = 0;
        this.mOffset = 0;
        this.mEnd = 0;
        this.mPackedEnd = 0;
        this.mBufferSize = bArr.length;
        this.mEnd = bArr.length;
        this.mBuffer = bArr;
        this.mStream = null;
    }

    public int getFieldNumber() {
        return this.mFieldNumber;
    }

    public int getWireType() {
        if ((this.mState & 2) == 2) {
            return 2;
        }
        return this.mWireType;
    }

    public int getOffset() {
        return this.mOffset + this.mDiscardedBytes;
    }

    public int nextField() throws java.io.IOException {
        if ((this.mState & 4) == 4) {
            this.mState = (byte) (this.mState & (-5));
            return this.mFieldNumber;
        }
        if ((this.mState & 1) == 1) {
            skip();
            this.mState = (byte) (this.mState & (-2));
        }
        if ((this.mState & 2) == 2) {
            if (getOffset() < this.mPackedEnd) {
                this.mState = (byte) (this.mState | 1);
                return this.mFieldNumber;
            }
            if (getOffset() == this.mPackedEnd) {
                this.mState = (byte) (this.mState & (-3));
            } else {
                throw new android.util.proto.ProtoParseException("Unexpectedly reached end of packed field at offset 0x" + java.lang.Integer.toHexString(this.mPackedEnd) + dumpDebugData());
            }
        }
        if (this.mDepth >= 0 && getOffset() == getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth))) {
            this.mFieldNumber = -1;
        } else {
            readTag();
        }
        return this.mFieldNumber;
    }

    public boolean nextField(long j) throws java.io.IOException {
        if (nextField() == ((int) j)) {
            return true;
        }
        this.mState = (byte) (this.mState | 4);
        return false;
    }

    public double readDouble(long j) throws java.io.IOException {
        assertFreshData();
        assertFieldNumber(j);
        checkPacked(j);
        switch ((int) ((android.util.proto.ProtoStream.FIELD_TYPE_MASK & j) >>> 32)) {
            case 1:
                assertWireType(1);
                double longBitsToDouble = java.lang.Double.longBitsToDouble(readFixed64());
                this.mState = (byte) (this.mState & (-2));
                return longBitsToDouble;
            default:
                throw new java.lang.IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") cannot be read as a double" + dumpDebugData());
        }
    }

    public float readFloat(long j) throws java.io.IOException {
        assertFreshData();
        assertFieldNumber(j);
        checkPacked(j);
        switch ((int) ((android.util.proto.ProtoStream.FIELD_TYPE_MASK & j) >>> 32)) {
            case 2:
                assertWireType(5);
                float intBitsToFloat = java.lang.Float.intBitsToFloat(readFixed32());
                this.mState = (byte) (this.mState & (-2));
                return intBitsToFloat;
            default:
                throw new java.lang.IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") is not a float" + dumpDebugData());
        }
    }

    public int readInt(long j) throws java.io.IOException {
        int readVarint;
        assertFreshData();
        assertFieldNumber(j);
        checkPacked(j);
        switch ((int) ((android.util.proto.ProtoStream.FIELD_TYPE_MASK & j) >>> 32)) {
            case 5:
            case 13:
            case 14:
                assertWireType(0);
                readVarint = (int) readVarint();
                break;
            case 7:
            case 15:
                assertWireType(5);
                readVarint = readFixed32();
                break;
            case 17:
                assertWireType(0);
                readVarint = decodeZigZag32((int) readVarint());
                break;
            default:
                throw new java.lang.IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") is not an int" + dumpDebugData());
        }
        this.mState = (byte) (this.mState & (-2));
        return readVarint;
    }

    public long readLong(long j) throws java.io.IOException {
        long readVarint;
        assertFreshData();
        assertFieldNumber(j);
        checkPacked(j);
        switch ((int) ((android.util.proto.ProtoStream.FIELD_TYPE_MASK & j) >>> 32)) {
            case 3:
            case 4:
                assertWireType(0);
                readVarint = readVarint();
                break;
            case 6:
            case 16:
                assertWireType(1);
                readVarint = readFixed64();
                break;
            case 18:
                assertWireType(0);
                readVarint = decodeZigZag64(readVarint());
                break;
            default:
                throw new java.lang.IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") is not an long" + dumpDebugData());
        }
        this.mState = (byte) (this.mState & (-2));
        return readVarint;
    }

    public boolean readBoolean(long j) throws java.io.IOException {
        assertFreshData();
        assertFieldNumber(j);
        checkPacked(j);
        switch ((int) ((android.util.proto.ProtoStream.FIELD_TYPE_MASK & j) >>> 32)) {
            case 8:
                assertWireType(0);
                boolean z = readVarint() != 0;
                this.mState = (byte) (this.mState & (-2));
                return z;
            default:
                throw new java.lang.IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") is not an boolean" + dumpDebugData());
        }
    }

    public java.lang.String readString(long j) throws java.io.IOException {
        assertFreshData();
        assertFieldNumber(j);
        switch ((int) ((android.util.proto.ProtoStream.FIELD_TYPE_MASK & j) >>> 32)) {
            case 9:
                assertWireType(2);
                java.lang.String readRawString = readRawString((int) readVarint());
                this.mState = (byte) (this.mState & (-2));
                return readRawString;
            default:
                throw new java.lang.IllegalArgumentException("Requested field id(" + getFieldIdString(j) + ") is not an string" + dumpDebugData());
        }
    }

    public byte[] readBytes(long j) throws java.io.IOException {
        assertFreshData();
        assertFieldNumber(j);
        switch ((int) ((android.util.proto.ProtoStream.FIELD_TYPE_MASK & j) >>> 32)) {
            case 11:
            case 12:
                assertWireType(2);
                byte[] readRawBytes = readRawBytes((int) readVarint());
                this.mState = (byte) (this.mState & (-2));
                return readRawBytes;
            default:
                throw new java.lang.IllegalArgumentException("Requested field type (" + getFieldIdString(j) + ") cannot be read as raw bytes" + dumpDebugData());
        }
    }

    public long start(long j) throws java.io.IOException {
        assertFreshData();
        assertFieldNumber(j);
        assertWireType(2);
        int readVarint = (int) readVarint();
        if (this.mExpectedObjectTokenStack == null) {
            this.mExpectedObjectTokenStack = new android.util.LongArray();
        }
        int i = this.mDepth + 1;
        this.mDepth = i;
        if (i == this.mExpectedObjectTokenStack.size()) {
            this.mExpectedObjectTokenStack.add(makeToken(0, (j & 2199023255552L) == 2199023255552L, this.mDepth, (int) j, getOffset() + readVarint));
        } else {
            this.mExpectedObjectTokenStack.set(this.mDepth, makeToken(0, (j & 2199023255552L) == 2199023255552L, this.mDepth, (int) j, getOffset() + readVarint));
        }
        if (this.mDepth > 0 && getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth)) > getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth - 1))) {
            throw new android.util.proto.ProtoParseException("Embedded Object (" + token2String(this.mExpectedObjectTokenStack.get(this.mDepth)) + ") ends after of parent Objects's (" + token2String(this.mExpectedObjectTokenStack.get(this.mDepth - 1)) + ") end" + dumpDebugData());
        }
        this.mState = (byte) (this.mState & (-2));
        return this.mExpectedObjectTokenStack.get(this.mDepth);
    }

    public void end(long j) {
        if (this.mExpectedObjectTokenStack.get(this.mDepth) != j) {
            throw new android.util.proto.ProtoParseException("end token " + j + " does not match current message token " + this.mExpectedObjectTokenStack.get(this.mDepth) + dumpDebugData());
        }
        if (getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth)) > getOffset()) {
            incOffset(getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth)) - getOffset());
        }
        this.mDepth--;
        this.mState = (byte) (this.mState & (-2));
    }

    private void readTag() throws java.io.IOException {
        fillBuffer();
        if (this.mOffset >= this.mEnd) {
            this.mFieldNumber = -1;
            return;
        }
        int readVarint = (int) readVarint();
        this.mFieldNumber = readVarint >>> 3;
        this.mWireType = readVarint & 7;
        this.mState = (byte) (this.mState | 1);
    }

    public int decodeZigZag32(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public long decodeZigZag64(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    private long readVarint() throws java.io.IOException {
        long j = 0;
        int i = 0;
        while (true) {
            fillBuffer();
            int i2 = this.mEnd - this.mOffset;
            if (i2 < 0) {
                throw new android.util.proto.ProtoParseException("Incomplete varint at offset 0x" + java.lang.Integer.toHexString(getOffset()) + dumpDebugData());
            }
            for (int i3 = 0; i3 < i2; i3++) {
                byte b = this.mBuffer[this.mOffset + i3];
                j |= (b & 127) << i;
                if ((b & 128) == 0) {
                    incOffset(i3 + 1);
                    return j;
                }
                i += 7;
                if (i > 63) {
                    throw new android.util.proto.ProtoParseException("Varint is too large at offset 0x" + java.lang.Integer.toHexString(getOffset() + i3) + dumpDebugData());
                }
            }
            incOffset(i2);
        }
    }

    private int readFixed32() throws java.io.IOException {
        if (this.mOffset + 4 <= this.mEnd) {
            incOffset(4);
            return (this.mBuffer[this.mOffset - 4] & 255) | ((this.mBuffer[this.mOffset - 3] & 255) << 8) | ((this.mBuffer[this.mOffset - 2] & 255) << 16) | ((this.mBuffer[this.mOffset - 1] & 255) << 24);
        }
        int i = 0;
        int i2 = 4;
        int i3 = 0;
        while (i2 > 0) {
            fillBuffer();
            int i4 = this.mEnd - this.mOffset < i2 ? this.mEnd - this.mOffset : i2;
            if (i4 < 0) {
                throw new android.util.proto.ProtoParseException("Incomplete fixed32 at offset 0x" + java.lang.Integer.toHexString(getOffset()) + dumpDebugData());
            }
            incOffset(i4);
            i2 -= i4;
            while (i4 > 0) {
                i |= (this.mBuffer[this.mOffset - i4] & 255) << i3;
                i4--;
                i3 += 8;
            }
        }
        return i;
    }

    private long readFixed64() throws java.io.IOException {
        int i = 8;
        if (this.mOffset + 8 <= this.mEnd) {
            incOffset(8);
            return ((this.mBuffer[this.mOffset - 7] & 255) << 8) | (this.mBuffer[this.mOffset - 8] & 255) | ((this.mBuffer[this.mOffset - 6] & 255) << 16) | ((this.mBuffer[this.mOffset - 5] & 255) << 24) | ((this.mBuffer[this.mOffset - 4] & 255) << 32) | ((this.mBuffer[this.mOffset - 3] & 255) << 40) | ((this.mBuffer[this.mOffset - 2] & 255) << 48) | ((this.mBuffer[this.mOffset - 1] & 255) << 56);
        }
        long j = 0;
        int i2 = 0;
        while (i > 0) {
            fillBuffer();
            int i3 = this.mEnd - this.mOffset < i ? this.mEnd - this.mOffset : i;
            if (i3 < 0) {
                throw new android.util.proto.ProtoParseException("Incomplete fixed64 at offset 0x" + java.lang.Integer.toHexString(getOffset()) + dumpDebugData());
            }
            incOffset(i3);
            i -= i3;
            while (i3 > 0) {
                j |= (this.mBuffer[this.mOffset - i3] & 255) << i2;
                i3--;
                i2 += 8;
            }
        }
        return j;
    }

    private byte[] readRawBytes(int i) throws java.io.IOException {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while ((this.mOffset + i) - i2 > this.mEnd) {
            int i3 = this.mEnd - this.mOffset;
            if (i3 > 0) {
                java.lang.System.arraycopy(this.mBuffer, this.mOffset, bArr, i2, i3);
                incOffset(i3);
                i2 += i3;
            }
            fillBuffer();
            if (this.mOffset >= this.mEnd) {
                throw new android.util.proto.ProtoParseException("Unexpectedly reached end of the InputStream at offset 0x" + java.lang.Integer.toHexString(this.mEnd) + dumpDebugData());
            }
        }
        int i4 = i - i2;
        java.lang.System.arraycopy(this.mBuffer, this.mOffset, bArr, i2, i4);
        incOffset(i4);
        return bArr;
    }

    private java.lang.String readRawString(int i) throws java.io.IOException {
        fillBuffer();
        if (this.mOffset + i <= this.mEnd) {
            java.lang.String str = new java.lang.String(this.mBuffer, this.mOffset, i, java.nio.charset.StandardCharsets.UTF_8);
            incOffset(i);
            return str;
        }
        if (i <= this.mBufferSize) {
            int i2 = this.mEnd - this.mOffset;
            java.lang.System.arraycopy(this.mBuffer, this.mOffset, this.mBuffer, 0, i2);
            this.mEnd = i2 + this.mStream.read(this.mBuffer, i2, i - i2);
            this.mDiscardedBytes += this.mOffset;
            this.mOffset = 0;
            java.lang.String str2 = new java.lang.String(this.mBuffer, this.mOffset, i, java.nio.charset.StandardCharsets.UTF_8);
            incOffset(i);
            return str2;
        }
        return new java.lang.String(readRawBytes(i), 0, i, java.nio.charset.StandardCharsets.UTF_8);
    }

    private void fillBuffer() throws java.io.IOException {
        if (this.mOffset >= this.mEnd && this.mStream != null) {
            this.mOffset -= this.mEnd;
            this.mDiscardedBytes += this.mEnd;
            if (this.mOffset >= this.mBufferSize) {
                int skip = (int) this.mStream.skip((this.mOffset / this.mBufferSize) * this.mBufferSize);
                this.mDiscardedBytes += skip;
                this.mOffset -= skip;
            }
            this.mEnd = this.mStream.read(this.mBuffer);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void skip() throws java.io.IOException {
        byte b;
        if ((this.mState & 2) == 2) {
            incOffset(this.mPackedEnd - getOffset());
        } else {
            switch (this.mWireType) {
                case 0:
                    do {
                        fillBuffer();
                        b = this.mBuffer[this.mOffset];
                        incOffset(1);
                    } while ((b & 128) != 0);
                case 1:
                    incOffset(8);
                    break;
                case 2:
                    fillBuffer();
                    incOffset((int) readVarint());
                    break;
                case 3:
                case 4:
                default:
                    throw new android.util.proto.ProtoParseException("Unexpected wire type: " + this.mWireType + " at offset 0x" + java.lang.Integer.toHexString(this.mOffset) + dumpDebugData());
                case 5:
                    incOffset(4);
                    break;
            }
        }
        this.mState = (byte) (this.mState & (-2));
    }

    private void incOffset(int i) {
        this.mOffset += i;
        if (this.mDepth >= 0 && getOffset() > getOffsetFromToken(this.mExpectedObjectTokenStack.get(this.mDepth))) {
            throw new android.util.proto.ProtoParseException("Unexpectedly reached end of embedded object.  " + token2String(this.mExpectedObjectTokenStack.get(this.mDepth)) + dumpDebugData());
        }
    }

    private void checkPacked(long j) throws java.io.IOException {
        if (this.mWireType == 2) {
            int readVarint = (int) readVarint();
            this.mPackedEnd = getOffset() + readVarint;
            this.mState = (byte) (2 | this.mState);
            switch ((int) ((android.util.proto.ProtoStream.FIELD_TYPE_MASK & j) >>> 32)) {
                case 1:
                case 6:
                case 16:
                    if (readVarint % 8 != 0) {
                        throw new java.lang.IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") packed length " + readVarint + " is not aligned for fixed64" + dumpDebugData());
                    }
                    this.mWireType = 1;
                    return;
                case 2:
                case 7:
                case 15:
                    if (readVarint % 4 != 0) {
                        throw new java.lang.IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") packed length " + readVarint + " is not aligned for fixed32" + dumpDebugData());
                    }
                    this.mWireType = 5;
                    return;
                case 3:
                case 4:
                case 5:
                case 8:
                case 13:
                case 14:
                case 17:
                case 18:
                    this.mWireType = 0;
                    return;
                case 9:
                case 10:
                case 11:
                case 12:
                default:
                    throw new java.lang.IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") is not a packable field" + dumpDebugData());
            }
        }
    }

    private void assertFieldNumber(long j) {
        if (((int) j) != this.mFieldNumber) {
            throw new java.lang.IllegalArgumentException("Requested field id (" + getFieldIdString(j) + ") does not match current field number (0x" + java.lang.Integer.toHexString(this.mFieldNumber) + ") at offset 0x" + java.lang.Integer.toHexString(getOffset()) + dumpDebugData());
        }
    }

    private void assertWireType(int i) {
        if (i != this.mWireType) {
            throw new android.util.proto.WireTypeMismatchException("Current wire type " + getWireTypeString(this.mWireType) + " does not match expected wire type " + getWireTypeString(i) + " at offset 0x" + java.lang.Integer.toHexString(getOffset()) + dumpDebugData());
        }
    }

    private void assertFreshData() {
        if ((this.mState & 1) != 1) {
            throw new android.util.proto.ProtoParseException("Attempting to read already read field at offset 0x" + java.lang.Integer.toHexString(getOffset()) + dumpDebugData());
        }
    }

    public java.lang.String dumpDebugData() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("\nmFieldNumber : 0x").append(java.lang.Integer.toHexString(this.mFieldNumber));
        sb.append("\nmWireType : 0x").append(java.lang.Integer.toHexString(this.mWireType));
        sb.append("\nmState : 0x").append(java.lang.Integer.toHexString(this.mState));
        sb.append("\nmDiscardedBytes : 0x").append(java.lang.Integer.toHexString(this.mDiscardedBytes));
        sb.append("\nmOffset : 0x").append(java.lang.Integer.toHexString(this.mOffset));
        sb.append("\nmExpectedObjectTokenStack : ").append(java.util.Objects.toString(this.mExpectedObjectTokenStack));
        sb.append("\nmDepth : 0x").append(java.lang.Integer.toHexString(this.mDepth));
        sb.append("\nmBuffer : ").append(java.util.Arrays.toString(this.mBuffer));
        sb.append("\nmBufferSize : 0x").append(java.lang.Integer.toHexString(this.mBufferSize));
        sb.append("\nmEnd : 0x").append(java.lang.Integer.toHexString(this.mEnd));
        return sb.toString();
    }
}
