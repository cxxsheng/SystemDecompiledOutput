package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public abstract class CodedInputStream {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int DEFAULT_SIZE_LIMIT = Integer.MAX_VALUE;
    private static volatile int defaultRecursionLimit = 100;
    int recursionDepth;
    int recursionLimit;
    private boolean shouldDiscardUnknownFields;
    int sizeLimit;
    com.android.framework.protobuf.CodedInputStreamReader wrapper;

    public abstract void checkLastTagWas(int i) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    public abstract void enableAliasing(boolean z);

    public abstract int getBytesUntilLimit();

    public abstract int getLastTag();

    public abstract int getTotalBytesRead();

    public abstract boolean isAtEnd() throws java.io.IOException;

    public abstract void popLimit(int i);

    public abstract int pushLimit(int i) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    public abstract boolean readBool() throws java.io.IOException;

    public abstract byte[] readByteArray() throws java.io.IOException;

    public abstract java.nio.ByteBuffer readByteBuffer() throws java.io.IOException;

    public abstract com.android.framework.protobuf.ByteString readBytes() throws java.io.IOException;

    public abstract double readDouble() throws java.io.IOException;

    public abstract int readEnum() throws java.io.IOException;

    public abstract int readFixed32() throws java.io.IOException;

    public abstract long readFixed64() throws java.io.IOException;

    public abstract float readFloat() throws java.io.IOException;

    public abstract <T extends com.android.framework.protobuf.MessageLite> T readGroup(int i, com.android.framework.protobuf.Parser<T> parser, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException;

    public abstract void readGroup(int i, com.android.framework.protobuf.MessageLite.Builder builder, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException;

    public abstract int readInt32() throws java.io.IOException;

    public abstract long readInt64() throws java.io.IOException;

    public abstract <T extends com.android.framework.protobuf.MessageLite> T readMessage(com.android.framework.protobuf.Parser<T> parser, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException;

    public abstract void readMessage(com.android.framework.protobuf.MessageLite.Builder builder, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException;

    public abstract byte readRawByte() throws java.io.IOException;

    public abstract byte[] readRawBytes(int i) throws java.io.IOException;

    public abstract int readRawLittleEndian32() throws java.io.IOException;

    public abstract long readRawLittleEndian64() throws java.io.IOException;

    public abstract int readRawVarint32() throws java.io.IOException;

    public abstract long readRawVarint64() throws java.io.IOException;

    abstract long readRawVarint64SlowPath() throws java.io.IOException;

    public abstract int readSFixed32() throws java.io.IOException;

    public abstract long readSFixed64() throws java.io.IOException;

    public abstract int readSInt32() throws java.io.IOException;

    public abstract long readSInt64() throws java.io.IOException;

    public abstract java.lang.String readString() throws java.io.IOException;

    public abstract java.lang.String readStringRequireUtf8() throws java.io.IOException;

    public abstract int readTag() throws java.io.IOException;

    public abstract int readUInt32() throws java.io.IOException;

    public abstract long readUInt64() throws java.io.IOException;

    @java.lang.Deprecated
    public abstract void readUnknownGroup(int i, com.android.framework.protobuf.MessageLite.Builder builder) throws java.io.IOException;

    public abstract void resetSizeCounter();

    public abstract boolean skipField(int i) throws java.io.IOException;

    @java.lang.Deprecated
    public abstract boolean skipField(int i, com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException;

    public abstract void skipMessage() throws java.io.IOException;

    public abstract void skipMessage(com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException;

    public abstract void skipRawBytes(int i) throws java.io.IOException;

    public static com.android.framework.protobuf.CodedInputStream newInstance(java.io.InputStream inputStream) {
        return newInstance(inputStream, 4096);
    }

    public static com.android.framework.protobuf.CodedInputStream newInstance(java.io.InputStream inputStream, int i) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("bufferSize must be > 0");
        }
        if (inputStream == null) {
            return newInstance(com.android.framework.protobuf.Internal.EMPTY_BYTE_ARRAY);
        }
        return new com.android.framework.protobuf.CodedInputStream.StreamDecoder(inputStream, i);
    }

    public static com.android.framework.protobuf.CodedInputStream newInstance(java.lang.Iterable<java.nio.ByteBuffer> iterable) {
        if (!com.android.framework.protobuf.CodedInputStream.UnsafeDirectNioDecoder.isSupported()) {
            return newInstance(new com.android.framework.protobuf.IterableByteBufferInputStream(iterable));
        }
        return newInstance(iterable, false);
    }

    static com.android.framework.protobuf.CodedInputStream newInstance(java.lang.Iterable<java.nio.ByteBuffer> iterable, boolean z) {
        int i = 0;
        int i2 = 0;
        for (java.nio.ByteBuffer byteBuffer : iterable) {
            i2 += byteBuffer.remaining();
            if (byteBuffer.hasArray()) {
                i |= 1;
            } else if (byteBuffer.isDirect()) {
                i |= 2;
            } else {
                i |= 4;
            }
        }
        if (i == 2) {
            return new com.android.framework.protobuf.CodedInputStream.IterableDirectByteBufferDecoder(iterable, i2, z);
        }
        return newInstance(new com.android.framework.protobuf.IterableByteBufferInputStream(iterable));
    }

    public static com.android.framework.protobuf.CodedInputStream newInstance(byte[] bArr) {
        return newInstance(bArr, 0, bArr.length);
    }

    public static com.android.framework.protobuf.CodedInputStream newInstance(byte[] bArr, int i, int i2) {
        return newInstance(bArr, i, i2, false);
    }

    static com.android.framework.protobuf.CodedInputStream newInstance(byte[] bArr, int i, int i2, boolean z) {
        com.android.framework.protobuf.CodedInputStream.ArrayDecoder arrayDecoder = new com.android.framework.protobuf.CodedInputStream.ArrayDecoder(bArr, i, i2, z);
        try {
            arrayDecoder.pushLimit(i2);
            return arrayDecoder;
        } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    public static com.android.framework.protobuf.CodedInputStream newInstance(java.nio.ByteBuffer byteBuffer) {
        return newInstance(byteBuffer, false);
    }

    static com.android.framework.protobuf.CodedInputStream newInstance(java.nio.ByteBuffer byteBuffer, boolean z) {
        if (byteBuffer.hasArray()) {
            return newInstance(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining(), z);
        }
        if (byteBuffer.isDirect() && com.android.framework.protobuf.CodedInputStream.UnsafeDirectNioDecoder.isSupported()) {
            return new com.android.framework.protobuf.CodedInputStream.UnsafeDirectNioDecoder(byteBuffer, z);
        }
        int remaining = byteBuffer.remaining();
        byte[] bArr = new byte[remaining];
        byteBuffer.duplicate().get(bArr);
        return newInstance(bArr, 0, remaining, true);
    }

    public void checkRecursionLimit() throws com.android.framework.protobuf.InvalidProtocolBufferException {
        if (this.recursionDepth >= this.recursionLimit) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.recursionLimitExceeded();
        }
    }

    private CodedInputStream() {
        this.recursionLimit = defaultRecursionLimit;
        this.sizeLimit = Integer.MAX_VALUE;
        this.shouldDiscardUnknownFields = false;
    }

    public final int setRecursionLimit(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Recursion limit cannot be negative: " + i);
        }
        int i2 = this.recursionLimit;
        this.recursionLimit = i;
        return i2;
    }

    public final int setSizeLimit(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Size limit cannot be negative: " + i);
        }
        int i2 = this.sizeLimit;
        this.sizeLimit = i;
        return i2;
    }

    final void discardUnknownFields() {
        this.shouldDiscardUnknownFields = true;
    }

    final void unsetDiscardUnknownFields() {
        this.shouldDiscardUnknownFields = false;
    }

    final boolean shouldDiscardUnknownFields() {
        return this.shouldDiscardUnknownFields;
    }

    public static int decodeZigZag32(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public static long decodeZigZag64(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    public static int readRawVarint32(int i, java.io.InputStream inputStream) throws java.io.IOException {
        if ((i & 128) == 0) {
            return i;
        }
        int i2 = i & 127;
        int i3 = 7;
        while (i3 < 32) {
            int read = inputStream.read();
            if (read == -1) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            i2 |= (read & 127) << i3;
            if ((read & 128) != 0) {
                i3 += 7;
            } else {
                return i2;
            }
        }
        while (i3 < 64) {
            int read2 = inputStream.read();
            if (read2 == -1) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            if ((read2 & 128) != 0) {
                i3 += 7;
            } else {
                return i2;
            }
        }
        throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
    }

    static int readRawVarint32(java.io.InputStream inputStream) throws java.io.IOException {
        int read = inputStream.read();
        if (read == -1) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        return readRawVarint32(read, inputStream);
    }

    private static final class ArrayDecoder extends com.android.framework.protobuf.CodedInputStream {
        private final byte[] buffer;
        private int bufferSizeAfterLimit;
        private int currentLimit;
        private boolean enableAliasing;
        private final boolean immutable;
        private int lastTag;
        private int limit;
        private int pos;
        private int startPos;

        private ArrayDecoder(byte[] bArr, int i, int i2, boolean z) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.buffer = bArr;
            this.limit = i2 + i;
            this.pos = i;
            this.startPos = this.pos;
            this.immutable = z;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readTag() throws java.io.IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            this.lastTag = readRawVarint32();
            if (com.android.framework.protobuf.WireFormat.getTagFieldNumber(this.lastTag) == 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidTag();
            }
            return this.lastTag;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void checkLastTagWas(int i) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if (this.lastTag != i) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidEndTag();
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int getLastTag() {
            return this.lastTag;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean skipField(int i) throws java.io.IOException {
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(i)) {
                case 0:
                    skipRawVarint();
                    return true;
                case 1:
                    skipRawBytes(8);
                    return true;
                case 2:
                    skipRawBytes(readRawVarint32());
                    return true;
                case 3:
                    skipMessage();
                    checkLastTagWas(com.android.framework.protobuf.WireFormat.makeTag(com.android.framework.protobuf.WireFormat.getTagFieldNumber(i), 4));
                    return true;
                case 4:
                    return false;
                case 5:
                    skipRawBytes(4);
                    return true;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean skipField(int i, com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(i)) {
                case 0:
                    long readInt64 = readInt64();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeUInt64NoTag(readInt64);
                    return true;
                case 1:
                    long readRawLittleEndian64 = readRawLittleEndian64();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeFixed64NoTag(readRawLittleEndian64);
                    return true;
                case 2:
                    com.android.framework.protobuf.ByteString readBytes = readBytes();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeBytesNoTag(readBytes);
                    return true;
                case 3:
                    codedOutputStream.writeUInt32NoTag(i);
                    skipMessage(codedOutputStream);
                    int makeTag = com.android.framework.protobuf.WireFormat.makeTag(com.android.framework.protobuf.WireFormat.getTagFieldNumber(i), 4);
                    checkLastTagWas(makeTag);
                    codedOutputStream.writeUInt32NoTag(makeTag);
                    return true;
                case 4:
                    return false;
                case 5:
                    int readRawLittleEndian32 = readRawLittleEndian32();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeFixed32NoTag(readRawLittleEndian32);
                    return true;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void skipMessage() throws java.io.IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag));
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void skipMessage(com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag, codedOutputStream));
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public double readDouble() throws java.io.IOException {
            return java.lang.Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public float readFloat() throws java.io.IOException {
            return java.lang.Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readUInt64() throws java.io.IOException {
            return readRawVarint64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readInt64() throws java.io.IOException {
            return readRawVarint64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readInt32() throws java.io.IOException {
            return readRawVarint32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readFixed64() throws java.io.IOException {
            return readRawLittleEndian64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readFixed32() throws java.io.IOException {
            return readRawLittleEndian32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean readBool() throws java.io.IOException {
            return readRawVarint64() != 0;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public java.lang.String readString() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0 && readRawVarint32 <= this.limit - this.pos) {
                java.lang.String str = new java.lang.String(this.buffer, this.pos, readRawVarint32, com.android.framework.protobuf.Internal.UTF_8);
                this.pos += readRawVarint32;
                return str;
            }
            if (readRawVarint32 == 0) {
                return "";
            }
            if (readRawVarint32 < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public java.lang.String readStringRequireUtf8() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0 && readRawVarint32 <= this.limit - this.pos) {
                java.lang.String decodeUtf8 = com.android.framework.protobuf.Utf8.decodeUtf8(this.buffer, this.pos, readRawVarint32);
                this.pos += readRawVarint32;
                return decodeUtf8;
            }
            if (readRawVarint32 == 0) {
                return "";
            }
            if (readRawVarint32 <= 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void readGroup(int i, com.android.framework.protobuf.MessageLite.Builder builder, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            checkRecursionLimit();
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(com.android.framework.protobuf.WireFormat.makeTag(i, 4));
            this.recursionDepth--;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public <T extends com.android.framework.protobuf.MessageLite> T readGroup(int i, com.android.framework.protobuf.Parser<T> parser, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            checkRecursionLimit();
            this.recursionDepth++;
            T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
            checkLastTagWas(com.android.framework.protobuf.WireFormat.makeTag(i, 4));
            this.recursionDepth--;
            return parsePartialFrom;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        @java.lang.Deprecated
        public void readUnknownGroup(int i, com.android.framework.protobuf.MessageLite.Builder builder) throws java.io.IOException {
            readGroup(i, builder, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void readMessage(com.android.framework.protobuf.MessageLite.Builder builder, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            checkRecursionLimit();
            int pushLimit = pushLimit(readRawVarint32);
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.recursionDepth--;
            if (getBytesUntilLimit() != 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            popLimit(pushLimit);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public <T extends com.android.framework.protobuf.MessageLite> T readMessage(com.android.framework.protobuf.Parser<T> parser, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            checkRecursionLimit();
            int pushLimit = pushLimit(readRawVarint32);
            this.recursionDepth++;
            T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.recursionDepth--;
            if (getBytesUntilLimit() != 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            popLimit(pushLimit);
            return parsePartialFrom;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public com.android.framework.protobuf.ByteString readBytes() throws java.io.IOException {
            com.android.framework.protobuf.ByteString copyFrom;
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0 && readRawVarint32 <= this.limit - this.pos) {
                if (this.immutable && this.enableAliasing) {
                    copyFrom = com.android.framework.protobuf.ByteString.wrap(this.buffer, this.pos, readRawVarint32);
                } else {
                    copyFrom = com.android.framework.protobuf.ByteString.copyFrom(this.buffer, this.pos, readRawVarint32);
                }
                this.pos += readRawVarint32;
                return copyFrom;
            }
            if (readRawVarint32 == 0) {
                return com.android.framework.protobuf.ByteString.EMPTY;
            }
            return com.android.framework.protobuf.ByteString.wrap(readRawBytes(readRawVarint32));
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public byte[] readByteArray() throws java.io.IOException {
            return readRawBytes(readRawVarint32());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public java.nio.ByteBuffer readByteBuffer() throws java.io.IOException {
            java.nio.ByteBuffer wrap;
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0 && readRawVarint32 <= this.limit - this.pos) {
                if (!this.immutable && this.enableAliasing) {
                    wrap = java.nio.ByteBuffer.wrap(this.buffer, this.pos, readRawVarint32).slice();
                } else {
                    wrap = java.nio.ByteBuffer.wrap(java.util.Arrays.copyOfRange(this.buffer, this.pos, this.pos + readRawVarint32));
                }
                this.pos += readRawVarint32;
                return wrap;
            }
            if (readRawVarint32 == 0) {
                return com.android.framework.protobuf.Internal.EMPTY_BYTE_BUFFER;
            }
            if (readRawVarint32 < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readUInt32() throws java.io.IOException {
            return readRawVarint32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readEnum() throws java.io.IOException {
            return readRawVarint32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readSFixed32() throws java.io.IOException {
            return readRawLittleEndian32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readSFixed64() throws java.io.IOException {
            return readRawLittleEndian64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readSInt32() throws java.io.IOException {
            return decodeZigZag32(readRawVarint32());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readSInt64() throws java.io.IOException {
            return decodeZigZag64(readRawVarint64());
        }

        /* JADX WARN: Code restructure failed: missing block: B:32:0x006a, code lost:
        
            if (r1[r2] < 0) goto L33;
         */
        @Override // com.android.framework.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int readRawVarint32() throws java.io.IOException {
            int i;
            int i2 = this.pos;
            if (this.limit != i2) {
                byte[] bArr = this.buffer;
                int i3 = i2 + 1;
                byte b = bArr[i2];
                if (b >= 0) {
                    this.pos = i3;
                    return b;
                }
                if (this.limit - i3 >= 9) {
                    int i4 = i3 + 1;
                    int i5 = b ^ (bArr[i3] << 7);
                    if (i5 < 0) {
                        i = i5 ^ (-128);
                    } else {
                        int i6 = i4 + 1;
                        int i7 = i5 ^ (bArr[i4] << 14);
                        if (i7 >= 0) {
                            i = i7 ^ 16256;
                            i4 = i6;
                        } else {
                            i4 = i6 + 1;
                            int i8 = i7 ^ (bArr[i6] << android.hardware.biometrics.face.AcquiredInfo.START);
                            if (i8 < 0) {
                                i = i8 ^ (-2080896);
                            } else {
                                int i9 = i4 + 1;
                                byte b2 = bArr[i4];
                                i = (i8 ^ (b2 << 28)) ^ 266354560;
                                if (b2 < 0) {
                                    i4 = i9 + 1;
                                    if (bArr[i9] < 0) {
                                        i9 = i4 + 1;
                                        if (bArr[i4] < 0) {
                                            i4 = i9 + 1;
                                            if (bArr[i9] < 0) {
                                                i9 = i4 + 1;
                                                if (bArr[i4] < 0) {
                                                    i4 = i9 + 1;
                                                }
                                            }
                                        }
                                    }
                                }
                                i4 = i9;
                            }
                        }
                    }
                    this.pos = i4;
                    return i;
                }
            }
            return (int) readRawVarint64SlowPath();
        }

        private void skipRawVarint() throws java.io.IOException {
            if (this.limit - this.pos >= 10) {
                skipRawVarintFastPath();
            } else {
                skipRawVarintSlowPath();
            }
        }

        private void skipRawVarintFastPath() throws java.io.IOException {
            for (int i = 0; i < 10; i++) {
                byte[] bArr = this.buffer;
                int i2 = this.pos;
                this.pos = i2 + 1;
                if (bArr[i2] >= 0) {
                    return;
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
        }

        private void skipRawVarintSlowPath() throws java.io.IOException {
            for (int i = 0; i < 10; i++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readRawVarint64() throws java.io.IOException {
            long j;
            int i = this.pos;
            if (this.limit != i) {
                byte[] bArr = this.buffer;
                int i2 = i + 1;
                byte b = bArr[i];
                if (b >= 0) {
                    this.pos = i2;
                    return b;
                }
                if (this.limit - i2 >= 9) {
                    int i3 = i2 + 1;
                    int i4 = b ^ (bArr[i2] << 7);
                    if (i4 < 0) {
                        j = i4 ^ (-128);
                    } else {
                        int i5 = i3 + 1;
                        int i6 = i4 ^ (bArr[i3] << 14);
                        if (i6 >= 0) {
                            j = i6 ^ 16256;
                            i3 = i5;
                        } else {
                            i3 = i5 + 1;
                            int i7 = i6 ^ (bArr[i5] << android.hardware.biometrics.face.AcquiredInfo.START);
                            if (i7 < 0) {
                                j = i7 ^ (-2080896);
                            } else {
                                long j2 = i7;
                                int i8 = i3 + 1;
                                long j3 = (bArr[i3] << 28) ^ j2;
                                if (j3 >= 0) {
                                    i3 = i8;
                                    j = j3 ^ 266354560;
                                } else {
                                    int i9 = i8 + 1;
                                    long j4 = j3 ^ (bArr[i8] << 35);
                                    if (j4 < 0) {
                                        j = (-34093383808L) ^ j4;
                                        i3 = i9;
                                    } else {
                                        int i10 = i9 + 1;
                                        long j5 = j4 ^ (bArr[i9] << 42);
                                        if (j5 >= 0) {
                                            i3 = i10;
                                            j = j5 ^ 4363953127296L;
                                        } else {
                                            int i11 = i10 + 1;
                                            long j6 = j5 ^ (bArr[i10] << 49);
                                            if (j6 < 0) {
                                                j = (-558586000294016L) ^ j6;
                                                i3 = i11;
                                            } else {
                                                int i12 = i11 + 1;
                                                long j7 = (j6 ^ (bArr[i11] << 56)) ^ 71499008037633920L;
                                                if (j7 >= 0) {
                                                    i3 = i12;
                                                    j = j7;
                                                } else {
                                                    int i13 = i12 + 1;
                                                    if (bArr[i12] >= 0) {
                                                        j = j7;
                                                        i3 = i13;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.pos = i3;
                    return j;
                }
            }
            return readRawVarint64SlowPath();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        long readRawVarint64SlowPath() throws java.io.IOException {
            long j = 0;
            for (int i = 0; i < 64; i += 7) {
                j |= (r3 & Byte.MAX_VALUE) << i;
                if ((readRawByte() & 128) == 0) {
                    return j;
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws java.io.IOException {
            int i = this.pos;
            if (this.limit - i < 4) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            this.pos = i + 4;
            return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws java.io.IOException {
            int i = this.pos;
            if (this.limit - i < 8) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            this.pos = i + 8;
            return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void enableAliasing(boolean z) {
            this.enableAliasing = z;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.startPos = this.pos;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int pushLimit(int i) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if (i < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            int totalBytesRead = i + getTotalBytesRead();
            if (totalBytesRead < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
            }
            int i2 = this.currentLimit;
            if (totalBytesRead > i2) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = totalBytesRead;
            recomputeBufferSizeAfterLimit();
            return i2;
        }

        private void recomputeBufferSizeAfterLimit() {
            this.limit += this.bufferSizeAfterLimit;
            int i = this.limit - this.startPos;
            if (i > this.currentLimit) {
                this.bufferSizeAfterLimit = i - this.currentLimit;
                this.limit -= this.bufferSizeAfterLimit;
            } else {
                this.bufferSizeAfterLimit = 0;
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void popLimit(int i) {
            this.currentLimit = i;
            recomputeBufferSizeAfterLimit();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            if (this.currentLimit == Integer.MAX_VALUE) {
                return -1;
            }
            return this.currentLimit - getTotalBytesRead();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean isAtEnd() throws java.io.IOException {
            return this.pos == this.limit;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return this.pos - this.startPos;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public byte readRawByte() throws java.io.IOException {
            if (this.pos == this.limit) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            int i = this.pos;
            this.pos = i + 1;
            return bArr[i];
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public byte[] readRawBytes(int i) throws java.io.IOException {
            if (i > 0 && i <= this.limit - this.pos) {
                int i2 = this.pos;
                this.pos += i;
                return java.util.Arrays.copyOfRange(this.buffer, i2, this.pos);
            }
            if (i <= 0) {
                if (i == 0) {
                    return com.android.framework.protobuf.Internal.EMPTY_BYTE_ARRAY;
                }
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void skipRawBytes(int i) throws java.io.IOException {
            if (i >= 0 && i <= this.limit - this.pos) {
                this.pos += i;
            } else {
                if (i < 0) {
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
                }
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
        }
    }

    private static final class UnsafeDirectNioDecoder extends com.android.framework.protobuf.CodedInputStream {
        private final long address;
        private final java.nio.ByteBuffer buffer;
        private int bufferSizeAfterLimit;
        private int currentLimit;
        private boolean enableAliasing;
        private final boolean immutable;
        private int lastTag;
        private long limit;
        private long pos;
        private long startPos;

        static boolean isSupported() {
            return com.android.framework.protobuf.UnsafeUtil.hasUnsafeByteBufferOperations();
        }

        private UnsafeDirectNioDecoder(java.nio.ByteBuffer byteBuffer, boolean z) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.buffer = byteBuffer;
            this.address = com.android.framework.protobuf.UnsafeUtil.addressOffset(byteBuffer);
            this.limit = this.address + byteBuffer.limit();
            this.pos = this.address + byteBuffer.position();
            this.startPos = this.pos;
            this.immutable = z;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readTag() throws java.io.IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            this.lastTag = readRawVarint32();
            if (com.android.framework.protobuf.WireFormat.getTagFieldNumber(this.lastTag) == 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidTag();
            }
            return this.lastTag;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void checkLastTagWas(int i) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if (this.lastTag != i) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidEndTag();
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int getLastTag() {
            return this.lastTag;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean skipField(int i) throws java.io.IOException {
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(i)) {
                case 0:
                    skipRawVarint();
                    return true;
                case 1:
                    skipRawBytes(8);
                    return true;
                case 2:
                    skipRawBytes(readRawVarint32());
                    return true;
                case 3:
                    skipMessage();
                    checkLastTagWas(com.android.framework.protobuf.WireFormat.makeTag(com.android.framework.protobuf.WireFormat.getTagFieldNumber(i), 4));
                    return true;
                case 4:
                    return false;
                case 5:
                    skipRawBytes(4);
                    return true;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean skipField(int i, com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(i)) {
                case 0:
                    long readInt64 = readInt64();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeUInt64NoTag(readInt64);
                    return true;
                case 1:
                    long readRawLittleEndian64 = readRawLittleEndian64();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeFixed64NoTag(readRawLittleEndian64);
                    return true;
                case 2:
                    com.android.framework.protobuf.ByteString readBytes = readBytes();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeBytesNoTag(readBytes);
                    return true;
                case 3:
                    codedOutputStream.writeUInt32NoTag(i);
                    skipMessage(codedOutputStream);
                    int makeTag = com.android.framework.protobuf.WireFormat.makeTag(com.android.framework.protobuf.WireFormat.getTagFieldNumber(i), 4);
                    checkLastTagWas(makeTag);
                    codedOutputStream.writeUInt32NoTag(makeTag);
                    return true;
                case 4:
                    return false;
                case 5:
                    int readRawLittleEndian32 = readRawLittleEndian32();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeFixed32NoTag(readRawLittleEndian32);
                    return true;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void skipMessage() throws java.io.IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag));
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void skipMessage(com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag, codedOutputStream));
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public double readDouble() throws java.io.IOException {
            return java.lang.Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public float readFloat() throws java.io.IOException {
            return java.lang.Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readUInt64() throws java.io.IOException {
            return readRawVarint64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readInt64() throws java.io.IOException {
            return readRawVarint64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readInt32() throws java.io.IOException {
            return readRawVarint32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readFixed64() throws java.io.IOException {
            return readRawLittleEndian64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readFixed32() throws java.io.IOException {
            return readRawLittleEndian32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean readBool() throws java.io.IOException {
            return readRawVarint64() != 0;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public java.lang.String readString() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0 && readRawVarint32 <= remaining()) {
                byte[] bArr = new byte[readRawVarint32];
                long j = readRawVarint32;
                com.android.framework.protobuf.UnsafeUtil.copyMemory(this.pos, bArr, 0L, j);
                java.lang.String str = new java.lang.String(bArr, com.android.framework.protobuf.Internal.UTF_8);
                this.pos += j;
                return str;
            }
            if (readRawVarint32 == 0) {
                return "";
            }
            if (readRawVarint32 < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public java.lang.String readStringRequireUtf8() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0 && readRawVarint32 <= remaining()) {
                java.lang.String decodeUtf8 = com.android.framework.protobuf.Utf8.decodeUtf8(this.buffer, bufferPos(this.pos), readRawVarint32);
                this.pos += readRawVarint32;
                return decodeUtf8;
            }
            if (readRawVarint32 == 0) {
                return "";
            }
            if (readRawVarint32 <= 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void readGroup(int i, com.android.framework.protobuf.MessageLite.Builder builder, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            checkRecursionLimit();
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(com.android.framework.protobuf.WireFormat.makeTag(i, 4));
            this.recursionDepth--;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public <T extends com.android.framework.protobuf.MessageLite> T readGroup(int i, com.android.framework.protobuf.Parser<T> parser, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            checkRecursionLimit();
            this.recursionDepth++;
            T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
            checkLastTagWas(com.android.framework.protobuf.WireFormat.makeTag(i, 4));
            this.recursionDepth--;
            return parsePartialFrom;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        @java.lang.Deprecated
        public void readUnknownGroup(int i, com.android.framework.protobuf.MessageLite.Builder builder) throws java.io.IOException {
            readGroup(i, builder, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void readMessage(com.android.framework.protobuf.MessageLite.Builder builder, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            checkRecursionLimit();
            int pushLimit = pushLimit(readRawVarint32);
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.recursionDepth--;
            if (getBytesUntilLimit() != 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            popLimit(pushLimit);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public <T extends com.android.framework.protobuf.MessageLite> T readMessage(com.android.framework.protobuf.Parser<T> parser, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            checkRecursionLimit();
            int pushLimit = pushLimit(readRawVarint32);
            this.recursionDepth++;
            T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.recursionDepth--;
            if (getBytesUntilLimit() != 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            popLimit(pushLimit);
            return parsePartialFrom;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public com.android.framework.protobuf.ByteString readBytes() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0 && readRawVarint32 <= remaining()) {
                if (this.immutable && this.enableAliasing) {
                    long j = readRawVarint32;
                    java.nio.ByteBuffer slice = slice(this.pos, this.pos + j);
                    this.pos += j;
                    return com.android.framework.protobuf.ByteString.wrap(slice);
                }
                byte[] bArr = new byte[readRawVarint32];
                long j2 = readRawVarint32;
                com.android.framework.protobuf.UnsafeUtil.copyMemory(this.pos, bArr, 0L, j2);
                this.pos += j2;
                return com.android.framework.protobuf.ByteString.wrap(bArr);
            }
            if (readRawVarint32 == 0) {
                return com.android.framework.protobuf.ByteString.EMPTY;
            }
            if (readRawVarint32 < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public byte[] readByteArray() throws java.io.IOException {
            return readRawBytes(readRawVarint32());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public java.nio.ByteBuffer readByteBuffer() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0 && readRawVarint32 <= remaining()) {
                if (!this.immutable && this.enableAliasing) {
                    long j = readRawVarint32;
                    java.nio.ByteBuffer slice = slice(this.pos, this.pos + j);
                    this.pos += j;
                    return slice;
                }
                byte[] bArr = new byte[readRawVarint32];
                long j2 = readRawVarint32;
                com.android.framework.protobuf.UnsafeUtil.copyMemory(this.pos, bArr, 0L, j2);
                this.pos += j2;
                return java.nio.ByteBuffer.wrap(bArr);
            }
            if (readRawVarint32 == 0) {
                return com.android.framework.protobuf.Internal.EMPTY_BYTE_BUFFER;
            }
            if (readRawVarint32 < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readUInt32() throws java.io.IOException {
            return readRawVarint32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readEnum() throws java.io.IOException {
            return readRawVarint32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readSFixed32() throws java.io.IOException {
            return readRawLittleEndian32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readSFixed64() throws java.io.IOException {
            return readRawLittleEndian64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readSInt32() throws java.io.IOException {
            return decodeZigZag32(readRawVarint32());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readSInt64() throws java.io.IOException {
            return decodeZigZag64(readRawVarint64());
        }

        /* JADX WARN: Code restructure failed: missing block: B:32:0x0083, code lost:
        
            if (com.android.framework.protobuf.UnsafeUtil.getByte(r4) < 0) goto L33;
         */
        @Override // com.android.framework.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int readRawVarint32() throws java.io.IOException {
            int i;
            long j = this.pos;
            if (this.limit != j) {
                long j2 = j + 1;
                byte b = com.android.framework.protobuf.UnsafeUtil.getByte(j);
                if (b >= 0) {
                    this.pos = j2;
                    return b;
                }
                if (this.limit - j2 >= 9) {
                    long j3 = j2 + 1;
                    int i2 = b ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j2) << 7);
                    if (i2 < 0) {
                        i = i2 ^ (-128);
                    } else {
                        long j4 = j3 + 1;
                        int i3 = i2 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j3) << 14);
                        if (i3 >= 0) {
                            i = i3 ^ 16256;
                            j3 = j4;
                        } else {
                            j3 = j4 + 1;
                            int i4 = i3 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j4) << android.hardware.biometrics.face.AcquiredInfo.START);
                            if (i4 < 0) {
                                i = i4 ^ (-2080896);
                            } else {
                                long j5 = j3 + 1;
                                byte b2 = com.android.framework.protobuf.UnsafeUtil.getByte(j3);
                                i = (i4 ^ (b2 << 28)) ^ 266354560;
                                if (b2 < 0) {
                                    j3 = j5 + 1;
                                    if (com.android.framework.protobuf.UnsafeUtil.getByte(j5) < 0) {
                                        long j6 = j3 + 1;
                                        if (com.android.framework.protobuf.UnsafeUtil.getByte(j3) < 0) {
                                            j3 = j6 + 1;
                                            if (com.android.framework.protobuf.UnsafeUtil.getByte(j6) < 0) {
                                                long j7 = j3 + 1;
                                                j3 = com.android.framework.protobuf.UnsafeUtil.getByte(j3) < 0 ? j7 + 1 : j7;
                                            }
                                        } else {
                                            j3 = j6;
                                        }
                                    }
                                } else {
                                    j3 = j5;
                                }
                            }
                        }
                    }
                    this.pos = j3;
                    return i;
                }
            }
            return (int) readRawVarint64SlowPath();
        }

        private void skipRawVarint() throws java.io.IOException {
            if (remaining() >= 10) {
                skipRawVarintFastPath();
            } else {
                skipRawVarintSlowPath();
            }
        }

        private void skipRawVarintFastPath() throws java.io.IOException {
            for (int i = 0; i < 10; i++) {
                long j = this.pos;
                this.pos = 1 + j;
                if (com.android.framework.protobuf.UnsafeUtil.getByte(j) >= 0) {
                    return;
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
        }

        private void skipRawVarintSlowPath() throws java.io.IOException {
            for (int i = 0; i < 10; i++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readRawVarint64() throws java.io.IOException {
            long j;
            long j2 = this.pos;
            if (this.limit != j2) {
                long j3 = j2 + 1;
                byte b = com.android.framework.protobuf.UnsafeUtil.getByte(j2);
                if (b >= 0) {
                    this.pos = j3;
                    return b;
                }
                if (this.limit - j3 >= 9) {
                    long j4 = j3 + 1;
                    int i = b ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j3) << 7);
                    if (i < 0) {
                        j = i ^ (-128);
                    } else {
                        long j5 = j4 + 1;
                        int i2 = i ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j4) << 14);
                        if (i2 >= 0) {
                            j = i2 ^ 16256;
                            j4 = j5;
                        } else {
                            j4 = j5 + 1;
                            int i3 = i2 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j5) << android.hardware.biometrics.face.AcquiredInfo.START);
                            if (i3 >= 0) {
                                long j6 = j4 + 1;
                                long j7 = i3 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j4) << 28);
                                if (j7 >= 0) {
                                    j = j7 ^ 266354560;
                                    j4 = j6;
                                } else {
                                    long j8 = j6 + 1;
                                    long j9 = j7 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j6) << 35);
                                    if (j9 < 0) {
                                        j = j9 ^ (-34093383808L);
                                        j4 = j8;
                                    } else {
                                        long j10 = j8 + 1;
                                        long j11 = j9 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j8) << 42);
                                        if (j11 >= 0) {
                                            j = j11 ^ 4363953127296L;
                                            j4 = j10;
                                        } else {
                                            long j12 = j10 + 1;
                                            long j13 = j11 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j10) << 49);
                                            if (j13 < 0) {
                                                j = j13 ^ (-558586000294016L);
                                                j4 = j12;
                                            } else {
                                                long j14 = j12 + 1;
                                                j = (j13 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j12) << 56)) ^ 71499008037633920L;
                                                if (j >= 0) {
                                                    j4 = j14;
                                                } else {
                                                    long j15 = 1 + j14;
                                                    if (com.android.framework.protobuf.UnsafeUtil.getByte(j14) >= 0) {
                                                        j4 = j15;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                j = i3 ^ (-2080896);
                            }
                        }
                    }
                    this.pos = j4;
                    return j;
                }
            }
            return readRawVarint64SlowPath();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        long readRawVarint64SlowPath() throws java.io.IOException {
            long j = 0;
            for (int i = 0; i < 64; i += 7) {
                j |= (r3 & Byte.MAX_VALUE) << i;
                if ((readRawByte() & 128) == 0) {
                    return j;
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws java.io.IOException {
            long j = this.pos;
            if (this.limit - j < 4) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            this.pos = 4 + j;
            return ((com.android.framework.protobuf.UnsafeUtil.getByte(j + 3) & 255) << 24) | (com.android.framework.protobuf.UnsafeUtil.getByte(j) & 255) | ((com.android.framework.protobuf.UnsafeUtil.getByte(1 + j) & 255) << 8) | ((com.android.framework.protobuf.UnsafeUtil.getByte(2 + j) & 255) << 16);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws java.io.IOException {
            long j = this.pos;
            if (this.limit - j < 8) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            this.pos = 8 + j;
            return ((com.android.framework.protobuf.UnsafeUtil.getByte(j + 7) & 255) << 56) | (com.android.framework.protobuf.UnsafeUtil.getByte(j) & 255) | ((com.android.framework.protobuf.UnsafeUtil.getByte(1 + j) & 255) << 8) | ((com.android.framework.protobuf.UnsafeUtil.getByte(2 + j) & 255) << 16) | ((com.android.framework.protobuf.UnsafeUtil.getByte(3 + j) & 255) << 24) | ((com.android.framework.protobuf.UnsafeUtil.getByte(4 + j) & 255) << 32) | ((com.android.framework.protobuf.UnsafeUtil.getByte(5 + j) & 255) << 40) | ((com.android.framework.protobuf.UnsafeUtil.getByte(6 + j) & 255) << 48);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void enableAliasing(boolean z) {
            this.enableAliasing = z;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.startPos = this.pos;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int pushLimit(int i) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if (i < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            int totalBytesRead = i + getTotalBytesRead();
            int i2 = this.currentLimit;
            if (totalBytesRead > i2) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = totalBytesRead;
            recomputeBufferSizeAfterLimit();
            return i2;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void popLimit(int i) {
            this.currentLimit = i;
            recomputeBufferSizeAfterLimit();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            if (this.currentLimit == Integer.MAX_VALUE) {
                return -1;
            }
            return this.currentLimit - getTotalBytesRead();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean isAtEnd() throws java.io.IOException {
            return this.pos == this.limit;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return (int) (this.pos - this.startPos);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public byte readRawByte() throws java.io.IOException {
            if (this.pos == this.limit) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            long j = this.pos;
            this.pos = 1 + j;
            return com.android.framework.protobuf.UnsafeUtil.getByte(j);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public byte[] readRawBytes(int i) throws java.io.IOException {
            if (i >= 0 && i <= remaining()) {
                byte[] bArr = new byte[i];
                long j = i;
                slice(this.pos, this.pos + j).get(bArr);
                this.pos += j;
                return bArr;
            }
            if (i <= 0) {
                if (i == 0) {
                    return com.android.framework.protobuf.Internal.EMPTY_BYTE_ARRAY;
                }
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void skipRawBytes(int i) throws java.io.IOException {
            if (i >= 0 && i <= remaining()) {
                this.pos += i;
            } else {
                if (i < 0) {
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
                }
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
        }

        private void recomputeBufferSizeAfterLimit() {
            this.limit += this.bufferSizeAfterLimit;
            int i = (int) (this.limit - this.startPos);
            if (i > this.currentLimit) {
                this.bufferSizeAfterLimit = i - this.currentLimit;
                this.limit -= this.bufferSizeAfterLimit;
            } else {
                this.bufferSizeAfterLimit = 0;
            }
        }

        private int remaining() {
            return (int) (this.limit - this.pos);
        }

        private int bufferPos(long j) {
            return (int) (j - this.address);
        }

        private java.nio.ByteBuffer slice(long j, long j2) throws java.io.IOException {
            int position = this.buffer.position();
            int limit = this.buffer.limit();
            java.nio.ByteBuffer byteBuffer = this.buffer;
            try {
                try {
                    byteBuffer.position(bufferPos(j));
                    byteBuffer.limit(bufferPos(j2));
                    return this.buffer.slice();
                } catch (java.lang.IllegalArgumentException e) {
                    com.android.framework.protobuf.InvalidProtocolBufferException truncatedMessage = com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
                    truncatedMessage.initCause(e);
                    throw truncatedMessage;
                }
            } finally {
                byteBuffer.position(position);
                byteBuffer.limit(limit);
            }
        }
    }

    private static final class StreamDecoder extends com.android.framework.protobuf.CodedInputStream {
        private final byte[] buffer;
        private int bufferSize;
        private int bufferSizeAfterLimit;
        private int currentLimit;
        private final java.io.InputStream input;
        private int lastTag;
        private int pos;
        private com.android.framework.protobuf.CodedInputStream.StreamDecoder.RefillCallback refillCallback;
        private int totalBytesRetired;

        private interface RefillCallback {
            void onRefill();
        }

        private StreamDecoder(java.io.InputStream inputStream, int i) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.refillCallback = null;
            com.android.framework.protobuf.Internal.checkNotNull(inputStream, "input");
            this.input = inputStream;
            this.buffer = new byte[i];
            this.bufferSize = 0;
            this.pos = 0;
            this.totalBytesRetired = 0;
        }

        private static int read(java.io.InputStream inputStream, byte[] bArr, int i, int i2) throws java.io.IOException {
            try {
                return inputStream.read(bArr, i, i2);
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                e.setThrownFromInputStream();
                throw e;
            }
        }

        private static long skip(java.io.InputStream inputStream, long j) throws java.io.IOException {
            try {
                return inputStream.skip(j);
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                e.setThrownFromInputStream();
                throw e;
            }
        }

        private static int available(java.io.InputStream inputStream) throws java.io.IOException {
            try {
                return inputStream.available();
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                e.setThrownFromInputStream();
                throw e;
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readTag() throws java.io.IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            this.lastTag = readRawVarint32();
            if (com.android.framework.protobuf.WireFormat.getTagFieldNumber(this.lastTag) == 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidTag();
            }
            return this.lastTag;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void checkLastTagWas(int i) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if (this.lastTag != i) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidEndTag();
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int getLastTag() {
            return this.lastTag;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean skipField(int i) throws java.io.IOException {
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(i)) {
                case 0:
                    skipRawVarint();
                    return true;
                case 1:
                    skipRawBytes(8);
                    return true;
                case 2:
                    skipRawBytes(readRawVarint32());
                    return true;
                case 3:
                    skipMessage();
                    checkLastTagWas(com.android.framework.protobuf.WireFormat.makeTag(com.android.framework.protobuf.WireFormat.getTagFieldNumber(i), 4));
                    return true;
                case 4:
                    return false;
                case 5:
                    skipRawBytes(4);
                    return true;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean skipField(int i, com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(i)) {
                case 0:
                    long readInt64 = readInt64();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeUInt64NoTag(readInt64);
                    return true;
                case 1:
                    long readRawLittleEndian64 = readRawLittleEndian64();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeFixed64NoTag(readRawLittleEndian64);
                    return true;
                case 2:
                    com.android.framework.protobuf.ByteString readBytes = readBytes();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeBytesNoTag(readBytes);
                    return true;
                case 3:
                    codedOutputStream.writeUInt32NoTag(i);
                    skipMessage(codedOutputStream);
                    int makeTag = com.android.framework.protobuf.WireFormat.makeTag(com.android.framework.protobuf.WireFormat.getTagFieldNumber(i), 4);
                    checkLastTagWas(makeTag);
                    codedOutputStream.writeUInt32NoTag(makeTag);
                    return true;
                case 4:
                    return false;
                case 5:
                    int readRawLittleEndian32 = readRawLittleEndian32();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeFixed32NoTag(readRawLittleEndian32);
                    return true;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void skipMessage() throws java.io.IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag));
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void skipMessage(com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag, codedOutputStream));
        }

        private class SkippedDataSink implements com.android.framework.protobuf.CodedInputStream.StreamDecoder.RefillCallback {
            private java.io.ByteArrayOutputStream byteArrayStream;
            private int lastPos;

            private SkippedDataSink() {
                this.lastPos = com.android.framework.protobuf.CodedInputStream.StreamDecoder.this.pos;
            }

            @Override // com.android.framework.protobuf.CodedInputStream.StreamDecoder.RefillCallback
            public void onRefill() {
                if (this.byteArrayStream == null) {
                    this.byteArrayStream = new java.io.ByteArrayOutputStream();
                }
                this.byteArrayStream.write(com.android.framework.protobuf.CodedInputStream.StreamDecoder.this.buffer, this.lastPos, com.android.framework.protobuf.CodedInputStream.StreamDecoder.this.pos - this.lastPos);
                this.lastPos = 0;
            }

            java.nio.ByteBuffer getSkippedData() {
                if (this.byteArrayStream == null) {
                    return java.nio.ByteBuffer.wrap(com.android.framework.protobuf.CodedInputStream.StreamDecoder.this.buffer, this.lastPos, com.android.framework.protobuf.CodedInputStream.StreamDecoder.this.pos - this.lastPos);
                }
                this.byteArrayStream.write(com.android.framework.protobuf.CodedInputStream.StreamDecoder.this.buffer, this.lastPos, com.android.framework.protobuf.CodedInputStream.StreamDecoder.this.pos);
                return java.nio.ByteBuffer.wrap(this.byteArrayStream.toByteArray());
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public double readDouble() throws java.io.IOException {
            return java.lang.Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public float readFloat() throws java.io.IOException {
            return java.lang.Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readUInt64() throws java.io.IOException {
            return readRawVarint64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readInt64() throws java.io.IOException {
            return readRawVarint64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readInt32() throws java.io.IOException {
            return readRawVarint32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readFixed64() throws java.io.IOException {
            return readRawLittleEndian64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readFixed32() throws java.io.IOException {
            return readRawLittleEndian32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean readBool() throws java.io.IOException {
            return readRawVarint64() != 0;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public java.lang.String readString() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0 && readRawVarint32 <= this.bufferSize - this.pos) {
                java.lang.String str = new java.lang.String(this.buffer, this.pos, readRawVarint32, com.android.framework.protobuf.Internal.UTF_8);
                this.pos += readRawVarint32;
                return str;
            }
            if (readRawVarint32 == 0) {
                return "";
            }
            if (readRawVarint32 <= this.bufferSize) {
                refillBuffer(readRawVarint32);
                java.lang.String str2 = new java.lang.String(this.buffer, this.pos, readRawVarint32, com.android.framework.protobuf.Internal.UTF_8);
                this.pos += readRawVarint32;
                return str2;
            }
            return new java.lang.String(readRawBytesSlowPath(readRawVarint32, false), com.android.framework.protobuf.Internal.UTF_8);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public java.lang.String readStringRequireUtf8() throws java.io.IOException {
            byte[] readRawBytesSlowPath;
            int readRawVarint32 = readRawVarint32();
            int i = this.pos;
            if (readRawVarint32 <= this.bufferSize - i && readRawVarint32 > 0) {
                readRawBytesSlowPath = this.buffer;
                this.pos = i + readRawVarint32;
            } else {
                if (readRawVarint32 == 0) {
                    return "";
                }
                if (readRawVarint32 <= this.bufferSize) {
                    refillBuffer(readRawVarint32);
                    byte[] bArr = this.buffer;
                    this.pos = readRawVarint32 + 0;
                    readRawBytesSlowPath = bArr;
                    i = 0;
                } else {
                    readRawBytesSlowPath = readRawBytesSlowPath(readRawVarint32, false);
                    i = 0;
                }
            }
            return com.android.framework.protobuf.Utf8.decodeUtf8(readRawBytesSlowPath, i, readRawVarint32);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void readGroup(int i, com.android.framework.protobuf.MessageLite.Builder builder, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            checkRecursionLimit();
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(com.android.framework.protobuf.WireFormat.makeTag(i, 4));
            this.recursionDepth--;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public <T extends com.android.framework.protobuf.MessageLite> T readGroup(int i, com.android.framework.protobuf.Parser<T> parser, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            checkRecursionLimit();
            this.recursionDepth++;
            T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
            checkLastTagWas(com.android.framework.protobuf.WireFormat.makeTag(i, 4));
            this.recursionDepth--;
            return parsePartialFrom;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        @java.lang.Deprecated
        public void readUnknownGroup(int i, com.android.framework.protobuf.MessageLite.Builder builder) throws java.io.IOException {
            readGroup(i, builder, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void readMessage(com.android.framework.protobuf.MessageLite.Builder builder, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            checkRecursionLimit();
            int pushLimit = pushLimit(readRawVarint32);
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.recursionDepth--;
            if (getBytesUntilLimit() != 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            popLimit(pushLimit);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public <T extends com.android.framework.protobuf.MessageLite> T readMessage(com.android.framework.protobuf.Parser<T> parser, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            checkRecursionLimit();
            int pushLimit = pushLimit(readRawVarint32);
            this.recursionDepth++;
            T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.recursionDepth--;
            if (getBytesUntilLimit() != 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            popLimit(pushLimit);
            return parsePartialFrom;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public com.android.framework.protobuf.ByteString readBytes() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 <= this.bufferSize - this.pos && readRawVarint32 > 0) {
                com.android.framework.protobuf.ByteString copyFrom = com.android.framework.protobuf.ByteString.copyFrom(this.buffer, this.pos, readRawVarint32);
                this.pos += readRawVarint32;
                return copyFrom;
            }
            if (readRawVarint32 == 0) {
                return com.android.framework.protobuf.ByteString.EMPTY;
            }
            return readBytesSlowPath(readRawVarint32);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public byte[] readByteArray() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 <= this.bufferSize - this.pos && readRawVarint32 > 0) {
                byte[] copyOfRange = java.util.Arrays.copyOfRange(this.buffer, this.pos, this.pos + readRawVarint32);
                this.pos += readRawVarint32;
                return copyOfRange;
            }
            return readRawBytesSlowPath(readRawVarint32, false);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public java.nio.ByteBuffer readByteBuffer() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 <= this.bufferSize - this.pos && readRawVarint32 > 0) {
                java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(java.util.Arrays.copyOfRange(this.buffer, this.pos, this.pos + readRawVarint32));
                this.pos += readRawVarint32;
                return wrap;
            }
            if (readRawVarint32 == 0) {
                return com.android.framework.protobuf.Internal.EMPTY_BYTE_BUFFER;
            }
            return java.nio.ByteBuffer.wrap(readRawBytesSlowPath(readRawVarint32, true));
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readUInt32() throws java.io.IOException {
            return readRawVarint32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readEnum() throws java.io.IOException {
            return readRawVarint32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readSFixed32() throws java.io.IOException {
            return readRawLittleEndian32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readSFixed64() throws java.io.IOException {
            return readRawLittleEndian64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readSInt32() throws java.io.IOException {
            return decodeZigZag32(readRawVarint32());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readSInt64() throws java.io.IOException {
            return decodeZigZag64(readRawVarint64());
        }

        /* JADX WARN: Code restructure failed: missing block: B:32:0x006a, code lost:
        
            if (r1[r2] < 0) goto L33;
         */
        @Override // com.android.framework.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int readRawVarint32() throws java.io.IOException {
            int i;
            int i2 = this.pos;
            if (this.bufferSize != i2) {
                byte[] bArr = this.buffer;
                int i3 = i2 + 1;
                byte b = bArr[i2];
                if (b >= 0) {
                    this.pos = i3;
                    return b;
                }
                if (this.bufferSize - i3 >= 9) {
                    int i4 = i3 + 1;
                    int i5 = b ^ (bArr[i3] << 7);
                    if (i5 < 0) {
                        i = i5 ^ (-128);
                    } else {
                        int i6 = i4 + 1;
                        int i7 = i5 ^ (bArr[i4] << 14);
                        if (i7 >= 0) {
                            i = i7 ^ 16256;
                            i4 = i6;
                        } else {
                            i4 = i6 + 1;
                            int i8 = i7 ^ (bArr[i6] << android.hardware.biometrics.face.AcquiredInfo.START);
                            if (i8 < 0) {
                                i = i8 ^ (-2080896);
                            } else {
                                int i9 = i4 + 1;
                                byte b2 = bArr[i4];
                                i = (i8 ^ (b2 << 28)) ^ 266354560;
                                if (b2 < 0) {
                                    i4 = i9 + 1;
                                    if (bArr[i9] < 0) {
                                        i9 = i4 + 1;
                                        if (bArr[i4] < 0) {
                                            i4 = i9 + 1;
                                            if (bArr[i9] < 0) {
                                                i9 = i4 + 1;
                                                if (bArr[i4] < 0) {
                                                    i4 = i9 + 1;
                                                }
                                            }
                                        }
                                    }
                                }
                                i4 = i9;
                            }
                        }
                    }
                    this.pos = i4;
                    return i;
                }
            }
            return (int) readRawVarint64SlowPath();
        }

        private void skipRawVarint() throws java.io.IOException {
            if (this.bufferSize - this.pos >= 10) {
                skipRawVarintFastPath();
            } else {
                skipRawVarintSlowPath();
            }
        }

        private void skipRawVarintFastPath() throws java.io.IOException {
            for (int i = 0; i < 10; i++) {
                byte[] bArr = this.buffer;
                int i2 = this.pos;
                this.pos = i2 + 1;
                if (bArr[i2] >= 0) {
                    return;
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
        }

        private void skipRawVarintSlowPath() throws java.io.IOException {
            for (int i = 0; i < 10; i++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readRawVarint64() throws java.io.IOException {
            long j;
            int i = this.pos;
            if (this.bufferSize != i) {
                byte[] bArr = this.buffer;
                int i2 = i + 1;
                byte b = bArr[i];
                if (b >= 0) {
                    this.pos = i2;
                    return b;
                }
                if (this.bufferSize - i2 >= 9) {
                    int i3 = i2 + 1;
                    int i4 = b ^ (bArr[i2] << 7);
                    if (i4 < 0) {
                        j = i4 ^ (-128);
                    } else {
                        int i5 = i3 + 1;
                        int i6 = i4 ^ (bArr[i3] << 14);
                        if (i6 >= 0) {
                            j = i6 ^ 16256;
                            i3 = i5;
                        } else {
                            i3 = i5 + 1;
                            int i7 = i6 ^ (bArr[i5] << android.hardware.biometrics.face.AcquiredInfo.START);
                            if (i7 < 0) {
                                j = i7 ^ (-2080896);
                            } else {
                                long j2 = i7;
                                int i8 = i3 + 1;
                                long j3 = (bArr[i3] << 28) ^ j2;
                                if (j3 >= 0) {
                                    i3 = i8;
                                    j = j3 ^ 266354560;
                                } else {
                                    int i9 = i8 + 1;
                                    long j4 = j3 ^ (bArr[i8] << 35);
                                    if (j4 < 0) {
                                        j = (-34093383808L) ^ j4;
                                        i3 = i9;
                                    } else {
                                        int i10 = i9 + 1;
                                        long j5 = j4 ^ (bArr[i9] << 42);
                                        if (j5 >= 0) {
                                            i3 = i10;
                                            j = j5 ^ 4363953127296L;
                                        } else {
                                            int i11 = i10 + 1;
                                            long j6 = j5 ^ (bArr[i10] << 49);
                                            if (j6 < 0) {
                                                j = (-558586000294016L) ^ j6;
                                                i3 = i11;
                                            } else {
                                                int i12 = i11 + 1;
                                                long j7 = (j6 ^ (bArr[i11] << 56)) ^ 71499008037633920L;
                                                if (j7 >= 0) {
                                                    i3 = i12;
                                                    j = j7;
                                                } else {
                                                    int i13 = i12 + 1;
                                                    if (bArr[i12] >= 0) {
                                                        j = j7;
                                                        i3 = i13;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.pos = i3;
                    return j;
                }
            }
            return readRawVarint64SlowPath();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        long readRawVarint64SlowPath() throws java.io.IOException {
            long j = 0;
            for (int i = 0; i < 64; i += 7) {
                j |= (r3 & Byte.MAX_VALUE) << i;
                if ((readRawByte() & 128) == 0) {
                    return j;
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws java.io.IOException {
            int i = this.pos;
            if (this.bufferSize - i < 4) {
                refillBuffer(4);
                i = this.pos;
            }
            byte[] bArr = this.buffer;
            this.pos = i + 4;
            return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws java.io.IOException {
            int i = this.pos;
            if (this.bufferSize - i < 8) {
                refillBuffer(8);
                i = this.pos;
            }
            byte[] bArr = this.buffer;
            this.pos = i + 8;
            return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void enableAliasing(boolean z) {
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.totalBytesRetired = -this.pos;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int pushLimit(int i) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if (i < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            int i2 = i + this.totalBytesRetired + this.pos;
            int i3 = this.currentLimit;
            if (i2 > i3) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = i2;
            recomputeBufferSizeAfterLimit();
            return i3;
        }

        private void recomputeBufferSizeAfterLimit() {
            this.bufferSize += this.bufferSizeAfterLimit;
            int i = this.totalBytesRetired + this.bufferSize;
            if (i > this.currentLimit) {
                this.bufferSizeAfterLimit = i - this.currentLimit;
                this.bufferSize -= this.bufferSizeAfterLimit;
            } else {
                this.bufferSizeAfterLimit = 0;
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void popLimit(int i) {
            this.currentLimit = i;
            recomputeBufferSizeAfterLimit();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            if (this.currentLimit == Integer.MAX_VALUE) {
                return -1;
            }
            return this.currentLimit - (this.totalBytesRetired + this.pos);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean isAtEnd() throws java.io.IOException {
            return this.pos == this.bufferSize && !tryRefillBuffer(1);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return this.totalBytesRetired + this.pos;
        }

        private void refillBuffer(int i) throws java.io.IOException {
            if (!tryRefillBuffer(i)) {
                if (i > (this.sizeLimit - this.totalBytesRetired) - this.pos) {
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.sizeLimitExceeded();
                }
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
        }

        private boolean tryRefillBuffer(int i) throws java.io.IOException {
            if (this.pos + i <= this.bufferSize) {
                throw new java.lang.IllegalStateException("refillBuffer() called when " + i + " bytes were already available in buffer");
            }
            if (i > (this.sizeLimit - this.totalBytesRetired) - this.pos || this.totalBytesRetired + this.pos + i > this.currentLimit) {
                return false;
            }
            if (this.refillCallback != null) {
                this.refillCallback.onRefill();
            }
            int i2 = this.pos;
            if (i2 > 0) {
                if (this.bufferSize > i2) {
                    java.lang.System.arraycopy(this.buffer, i2, this.buffer, 0, this.bufferSize - i2);
                }
                this.totalBytesRetired += i2;
                this.bufferSize -= i2;
                this.pos = 0;
            }
            int read = read(this.input, this.buffer, this.bufferSize, java.lang.Math.min(this.buffer.length - this.bufferSize, (this.sizeLimit - this.totalBytesRetired) - this.bufferSize));
            if (read == 0 || read < -1 || read > this.buffer.length) {
                throw new java.lang.IllegalStateException(this.input.getClass() + "#read(byte[]) returned invalid result: " + read + "\nThe InputStream implementation is buggy.");
            }
            if (read <= 0) {
                return false;
            }
            this.bufferSize += read;
            recomputeBufferSizeAfterLimit();
            if (this.bufferSize >= i) {
                return true;
            }
            return tryRefillBuffer(i);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public byte readRawByte() throws java.io.IOException {
            if (this.pos == this.bufferSize) {
                refillBuffer(1);
            }
            byte[] bArr = this.buffer;
            int i = this.pos;
            this.pos = i + 1;
            return bArr[i];
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public byte[] readRawBytes(int i) throws java.io.IOException {
            int i2 = this.pos;
            if (i <= this.bufferSize - i2 && i > 0) {
                int i3 = i + i2;
                this.pos = i3;
                return java.util.Arrays.copyOfRange(this.buffer, i2, i3);
            }
            return readRawBytesSlowPath(i, false);
        }

        private byte[] readRawBytesSlowPath(int i, boolean z) throws java.io.IOException {
            byte[] readRawBytesSlowPathOneChunk = readRawBytesSlowPathOneChunk(i);
            if (readRawBytesSlowPathOneChunk != null) {
                return z ? (byte[]) readRawBytesSlowPathOneChunk.clone() : readRawBytesSlowPathOneChunk;
            }
            int i2 = this.pos;
            int i3 = this.bufferSize - this.pos;
            this.totalBytesRetired += this.bufferSize;
            this.pos = 0;
            this.bufferSize = 0;
            java.util.List<byte[]> readRawBytesSlowPathRemainingChunks = readRawBytesSlowPathRemainingChunks(i - i3);
            byte[] bArr = new byte[i];
            java.lang.System.arraycopy(this.buffer, i2, bArr, 0, i3);
            for (byte[] bArr2 : readRawBytesSlowPathRemainingChunks) {
                java.lang.System.arraycopy(bArr2, 0, bArr, i3, bArr2.length);
                i3 += bArr2.length;
            }
            return bArr;
        }

        private byte[] readRawBytesSlowPathOneChunk(int i) throws java.io.IOException {
            if (i == 0) {
                return com.android.framework.protobuf.Internal.EMPTY_BYTE_ARRAY;
            }
            if (i < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            int i2 = this.totalBytesRetired + this.pos + i;
            if (i2 - this.sizeLimit > 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.sizeLimitExceeded();
            }
            if (i2 > this.currentLimit) {
                skipRawBytes((this.currentLimit - this.totalBytesRetired) - this.pos);
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            int i3 = this.bufferSize - this.pos;
            int i4 = i - i3;
            if (i4 < 4096 || i4 <= available(this.input)) {
                byte[] bArr = new byte[i];
                java.lang.System.arraycopy(this.buffer, this.pos, bArr, 0, i3);
                this.totalBytesRetired += this.bufferSize;
                this.pos = 0;
                this.bufferSize = 0;
                while (i3 < i) {
                    int read = read(this.input, bArr, i3, i - i3);
                    if (read == -1) {
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
                    }
                    this.totalBytesRetired += read;
                    i3 += read;
                }
                return bArr;
            }
            return null;
        }

        private java.util.List<byte[]> readRawBytesSlowPathRemainingChunks(int i) throws java.io.IOException {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (i > 0) {
                int min = java.lang.Math.min(i, 4096);
                byte[] bArr = new byte[min];
                int i2 = 0;
                while (i2 < min) {
                    int read = this.input.read(bArr, i2, min - i2);
                    if (read == -1) {
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
                    }
                    this.totalBytesRetired += read;
                    i2 += read;
                }
                i -= min;
                arrayList.add(bArr);
            }
            return arrayList;
        }

        private com.android.framework.protobuf.ByteString readBytesSlowPath(int i) throws java.io.IOException {
            byte[] readRawBytesSlowPathOneChunk = readRawBytesSlowPathOneChunk(i);
            if (readRawBytesSlowPathOneChunk != null) {
                return com.android.framework.protobuf.ByteString.copyFrom(readRawBytesSlowPathOneChunk);
            }
            int i2 = this.pos;
            int i3 = this.bufferSize - this.pos;
            this.totalBytesRetired += this.bufferSize;
            this.pos = 0;
            this.bufferSize = 0;
            java.util.List<byte[]> readRawBytesSlowPathRemainingChunks = readRawBytesSlowPathRemainingChunks(i - i3);
            byte[] bArr = new byte[i];
            java.lang.System.arraycopy(this.buffer, i2, bArr, 0, i3);
            for (byte[] bArr2 : readRawBytesSlowPathRemainingChunks) {
                java.lang.System.arraycopy(bArr2, 0, bArr, i3, bArr2.length);
                i3 += bArr2.length;
            }
            return com.android.framework.protobuf.ByteString.wrap(bArr);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void skipRawBytes(int i) throws java.io.IOException {
            if (i <= this.bufferSize - this.pos && i >= 0) {
                this.pos += i;
            } else {
                skipRawBytesSlowPath(i);
            }
        }

        private void skipRawBytesSlowPath(int i) throws java.io.IOException {
            if (i < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            if (this.totalBytesRetired + this.pos + i > this.currentLimit) {
                skipRawBytes((this.currentLimit - this.totalBytesRetired) - this.pos);
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            int i2 = 0;
            if (this.refillCallback == null) {
                this.totalBytesRetired += this.pos;
                int i3 = this.bufferSize - this.pos;
                this.bufferSize = 0;
                this.pos = 0;
                i2 = i3;
                while (i2 < i) {
                    try {
                        long j = i - i2;
                        long skip = skip(this.input, j);
                        if (skip < 0 || skip > j) {
                            throw new java.lang.IllegalStateException(this.input.getClass() + "#skip returned invalid result: " + skip + "\nThe InputStream implementation is buggy.");
                        }
                        if (skip == 0) {
                            break;
                        } else {
                            i2 += (int) skip;
                        }
                    } finally {
                        this.totalBytesRetired += i2;
                        recomputeBufferSizeAfterLimit();
                    }
                }
            }
            if (i2 < i) {
                int i4 = this.bufferSize - this.pos;
                this.pos = this.bufferSize;
                refillBuffer(1);
                while (true) {
                    int i5 = i - i4;
                    if (i5 > this.bufferSize) {
                        i4 += this.bufferSize;
                        this.pos = this.bufferSize;
                        refillBuffer(1);
                    } else {
                        this.pos = i5;
                        return;
                    }
                }
            }
        }
    }

    private static final class IterableDirectByteBufferDecoder extends com.android.framework.protobuf.CodedInputStream {
        private int bufferSizeAfterCurrentLimit;
        private long currentAddress;
        private java.nio.ByteBuffer currentByteBuffer;
        private long currentByteBufferLimit;
        private long currentByteBufferPos;
        private long currentByteBufferStartPos;
        private int currentLimit;
        private boolean enableAliasing;
        private final boolean immutable;
        private final java.lang.Iterable<java.nio.ByteBuffer> input;
        private final java.util.Iterator<java.nio.ByteBuffer> iterator;
        private int lastTag;
        private int startOffset;
        private int totalBufferSize;
        private int totalBytesRead;

        private IterableDirectByteBufferDecoder(java.lang.Iterable<java.nio.ByteBuffer> iterable, int i, boolean z) {
            super();
            this.currentLimit = Integer.MAX_VALUE;
            this.totalBufferSize = i;
            this.input = iterable;
            this.iterator = this.input.iterator();
            this.immutable = z;
            this.totalBytesRead = 0;
            this.startOffset = 0;
            if (i == 0) {
                this.currentByteBuffer = com.android.framework.protobuf.Internal.EMPTY_BYTE_BUFFER;
                this.currentByteBufferPos = 0L;
                this.currentByteBufferStartPos = 0L;
                this.currentByteBufferLimit = 0L;
                this.currentAddress = 0L;
                return;
            }
            tryGetNextByteBuffer();
        }

        private void getNextByteBuffer() throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if (!this.iterator.hasNext()) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            tryGetNextByteBuffer();
        }

        private void tryGetNextByteBuffer() {
            this.currentByteBuffer = this.iterator.next();
            this.totalBytesRead += (int) (this.currentByteBufferPos - this.currentByteBufferStartPos);
            this.currentByteBufferPos = this.currentByteBuffer.position();
            this.currentByteBufferStartPos = this.currentByteBufferPos;
            this.currentByteBufferLimit = this.currentByteBuffer.limit();
            this.currentAddress = com.android.framework.protobuf.UnsafeUtil.addressOffset(this.currentByteBuffer);
            this.currentByteBufferPos += this.currentAddress;
            this.currentByteBufferStartPos += this.currentAddress;
            this.currentByteBufferLimit += this.currentAddress;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readTag() throws java.io.IOException {
            if (isAtEnd()) {
                this.lastTag = 0;
                return 0;
            }
            this.lastTag = readRawVarint32();
            if (com.android.framework.protobuf.WireFormat.getTagFieldNumber(this.lastTag) == 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidTag();
            }
            return this.lastTag;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void checkLastTagWas(int i) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if (this.lastTag != i) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidEndTag();
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int getLastTag() {
            return this.lastTag;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean skipField(int i) throws java.io.IOException {
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(i)) {
                case 0:
                    skipRawVarint();
                    return true;
                case 1:
                    skipRawBytes(8);
                    return true;
                case 2:
                    skipRawBytes(readRawVarint32());
                    return true;
                case 3:
                    skipMessage();
                    checkLastTagWas(com.android.framework.protobuf.WireFormat.makeTag(com.android.framework.protobuf.WireFormat.getTagFieldNumber(i), 4));
                    return true;
                case 4:
                    return false;
                case 5:
                    skipRawBytes(4);
                    return true;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean skipField(int i, com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(i)) {
                case 0:
                    long readInt64 = readInt64();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeUInt64NoTag(readInt64);
                    return true;
                case 1:
                    long readRawLittleEndian64 = readRawLittleEndian64();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeFixed64NoTag(readRawLittleEndian64);
                    return true;
                case 2:
                    com.android.framework.protobuf.ByteString readBytes = readBytes();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeBytesNoTag(readBytes);
                    return true;
                case 3:
                    codedOutputStream.writeUInt32NoTag(i);
                    skipMessage(codedOutputStream);
                    int makeTag = com.android.framework.protobuf.WireFormat.makeTag(com.android.framework.protobuf.WireFormat.getTagFieldNumber(i), 4);
                    checkLastTagWas(makeTag);
                    codedOutputStream.writeUInt32NoTag(makeTag);
                    return true;
                case 4:
                    return false;
                case 5:
                    int readRawLittleEndian32 = readRawLittleEndian32();
                    codedOutputStream.writeUInt32NoTag(i);
                    codedOutputStream.writeFixed32NoTag(readRawLittleEndian32);
                    return true;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void skipMessage() throws java.io.IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag));
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void skipMessage(com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag, codedOutputStream));
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public double readDouble() throws java.io.IOException {
            return java.lang.Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public float readFloat() throws java.io.IOException {
            return java.lang.Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readUInt64() throws java.io.IOException {
            return readRawVarint64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readInt64() throws java.io.IOException {
            return readRawVarint64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readInt32() throws java.io.IOException {
            return readRawVarint32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readFixed64() throws java.io.IOException {
            return readRawLittleEndian64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readFixed32() throws java.io.IOException {
            return readRawLittleEndian32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean readBool() throws java.io.IOException {
            return readRawVarint64() != 0;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public java.lang.String readString() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                long j = readRawVarint32;
                if (j <= this.currentByteBufferLimit - this.currentByteBufferPos) {
                    byte[] bArr = new byte[readRawVarint32];
                    com.android.framework.protobuf.UnsafeUtil.copyMemory(this.currentByteBufferPos, bArr, 0L, j);
                    java.lang.String str = new java.lang.String(bArr, com.android.framework.protobuf.Internal.UTF_8);
                    this.currentByteBufferPos += j;
                    return str;
                }
            }
            if (readRawVarint32 > 0 && readRawVarint32 <= remaining()) {
                byte[] bArr2 = new byte[readRawVarint32];
                readRawBytesTo(bArr2, 0, readRawVarint32);
                return new java.lang.String(bArr2, com.android.framework.protobuf.Internal.UTF_8);
            }
            if (readRawVarint32 == 0) {
                return "";
            }
            if (readRawVarint32 < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public java.lang.String readStringRequireUtf8() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                long j = readRawVarint32;
                if (j <= this.currentByteBufferLimit - this.currentByteBufferPos) {
                    java.lang.String decodeUtf8 = com.android.framework.protobuf.Utf8.decodeUtf8(this.currentByteBuffer, (int) (this.currentByteBufferPos - this.currentByteBufferStartPos), readRawVarint32);
                    this.currentByteBufferPos += j;
                    return decodeUtf8;
                }
            }
            if (readRawVarint32 >= 0 && readRawVarint32 <= remaining()) {
                byte[] bArr = new byte[readRawVarint32];
                readRawBytesTo(bArr, 0, readRawVarint32);
                return com.android.framework.protobuf.Utf8.decodeUtf8(bArr, 0, readRawVarint32);
            }
            if (readRawVarint32 == 0) {
                return "";
            }
            if (readRawVarint32 <= 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void readGroup(int i, com.android.framework.protobuf.MessageLite.Builder builder, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            checkRecursionLimit();
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(com.android.framework.protobuf.WireFormat.makeTag(i, 4));
            this.recursionDepth--;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public <T extends com.android.framework.protobuf.MessageLite> T readGroup(int i, com.android.framework.protobuf.Parser<T> parser, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            checkRecursionLimit();
            this.recursionDepth++;
            T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
            checkLastTagWas(com.android.framework.protobuf.WireFormat.makeTag(i, 4));
            this.recursionDepth--;
            return parsePartialFrom;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        @java.lang.Deprecated
        public void readUnknownGroup(int i, com.android.framework.protobuf.MessageLite.Builder builder) throws java.io.IOException {
            readGroup(i, builder, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void readMessage(com.android.framework.protobuf.MessageLite.Builder builder, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            checkRecursionLimit();
            int pushLimit = pushLimit(readRawVarint32);
            this.recursionDepth++;
            builder.mergeFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.recursionDepth--;
            if (getBytesUntilLimit() != 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            popLimit(pushLimit);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public <T extends com.android.framework.protobuf.MessageLite> T readMessage(com.android.framework.protobuf.Parser<T> parser, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            checkRecursionLimit();
            int pushLimit = pushLimit(readRawVarint32);
            this.recursionDepth++;
            T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
            checkLastTagWas(0);
            this.recursionDepth--;
            if (getBytesUntilLimit() != 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            popLimit(pushLimit);
            return parsePartialFrom;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public com.android.framework.protobuf.ByteString readBytes() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                long j = readRawVarint32;
                if (j <= this.currentByteBufferLimit - this.currentByteBufferPos) {
                    if (this.immutable && this.enableAliasing) {
                        int i = (int) (this.currentByteBufferPos - this.currentAddress);
                        com.android.framework.protobuf.ByteString wrap = com.android.framework.protobuf.ByteString.wrap(slice(i, readRawVarint32 + i));
                        this.currentByteBufferPos += j;
                        return wrap;
                    }
                    byte[] bArr = new byte[readRawVarint32];
                    com.android.framework.protobuf.UnsafeUtil.copyMemory(this.currentByteBufferPos, bArr, 0L, j);
                    this.currentByteBufferPos += j;
                    return com.android.framework.protobuf.ByteString.wrap(bArr);
                }
            }
            if (readRawVarint32 > 0 && readRawVarint32 <= remaining()) {
                if (this.immutable && this.enableAliasing) {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    while (readRawVarint32 > 0) {
                        if (currentRemaining() == 0) {
                            getNextByteBuffer();
                        }
                        int min = java.lang.Math.min(readRawVarint32, (int) currentRemaining());
                        int i2 = (int) (this.currentByteBufferPos - this.currentAddress);
                        arrayList.add(com.android.framework.protobuf.ByteString.wrap(slice(i2, i2 + min)));
                        readRawVarint32 -= min;
                        this.currentByteBufferPos += min;
                    }
                    return com.android.framework.protobuf.ByteString.copyFrom(arrayList);
                }
                byte[] bArr2 = new byte[readRawVarint32];
                readRawBytesTo(bArr2, 0, readRawVarint32);
                return com.android.framework.protobuf.ByteString.wrap(bArr2);
            }
            if (readRawVarint32 == 0) {
                return com.android.framework.protobuf.ByteString.EMPTY;
            }
            if (readRawVarint32 < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public byte[] readByteArray() throws java.io.IOException {
            return readRawBytes(readRawVarint32());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public java.nio.ByteBuffer readByteBuffer() throws java.io.IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                long j = readRawVarint32;
                if (j <= currentRemaining()) {
                    if (!this.immutable && this.enableAliasing) {
                        this.currentByteBufferPos += j;
                        return slice((int) ((this.currentByteBufferPos - this.currentAddress) - j), (int) (this.currentByteBufferPos - this.currentAddress));
                    }
                    byte[] bArr = new byte[readRawVarint32];
                    com.android.framework.protobuf.UnsafeUtil.copyMemory(this.currentByteBufferPos, bArr, 0L, j);
                    this.currentByteBufferPos += j;
                    return java.nio.ByteBuffer.wrap(bArr);
                }
            }
            if (readRawVarint32 > 0 && readRawVarint32 <= remaining()) {
                byte[] bArr2 = new byte[readRawVarint32];
                readRawBytesTo(bArr2, 0, readRawVarint32);
                return java.nio.ByteBuffer.wrap(bArr2);
            }
            if (readRawVarint32 == 0) {
                return com.android.framework.protobuf.Internal.EMPTY_BYTE_BUFFER;
            }
            if (readRawVarint32 < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readUInt32() throws java.io.IOException {
            return readRawVarint32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readEnum() throws java.io.IOException {
            return readRawVarint32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readSFixed32() throws java.io.IOException {
            return readRawLittleEndian32();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readSFixed64() throws java.io.IOException {
            return readRawLittleEndian64();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readSInt32() throws java.io.IOException {
            return decodeZigZag32(readRawVarint32());
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readSInt64() throws java.io.IOException {
            return decodeZigZag64(readRawVarint64());
        }

        /* JADX WARN: Code restructure failed: missing block: B:32:0x008a, code lost:
        
            if (com.android.framework.protobuf.UnsafeUtil.getByte(r4) < 0) goto L33;
         */
        @Override // com.android.framework.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int readRawVarint32() throws java.io.IOException {
            int i;
            long j = this.currentByteBufferPos;
            if (this.currentByteBufferLimit != this.currentByteBufferPos) {
                long j2 = j + 1;
                byte b = com.android.framework.protobuf.UnsafeUtil.getByte(j);
                if (b >= 0) {
                    this.currentByteBufferPos++;
                    return b;
                }
                if (this.currentByteBufferLimit - this.currentByteBufferPos >= 10) {
                    long j3 = j2 + 1;
                    int i2 = b ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j2) << 7);
                    if (i2 < 0) {
                        i = i2 ^ (-128);
                    } else {
                        long j4 = j3 + 1;
                        int i3 = i2 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j3) << 14);
                        if (i3 >= 0) {
                            i = i3 ^ 16256;
                            j3 = j4;
                        } else {
                            j3 = j4 + 1;
                            int i4 = i3 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j4) << android.hardware.biometrics.face.AcquiredInfo.START);
                            if (i4 < 0) {
                                i = i4 ^ (-2080896);
                            } else {
                                long j5 = j3 + 1;
                                byte b2 = com.android.framework.protobuf.UnsafeUtil.getByte(j3);
                                i = (i4 ^ (b2 << 28)) ^ 266354560;
                                if (b2 < 0) {
                                    j3 = j5 + 1;
                                    if (com.android.framework.protobuf.UnsafeUtil.getByte(j5) < 0) {
                                        long j6 = j3 + 1;
                                        if (com.android.framework.protobuf.UnsafeUtil.getByte(j3) < 0) {
                                            j3 = j6 + 1;
                                            if (com.android.framework.protobuf.UnsafeUtil.getByte(j6) < 0) {
                                                long j7 = j3 + 1;
                                                j3 = com.android.framework.protobuf.UnsafeUtil.getByte(j3) < 0 ? j7 + 1 : j7;
                                            }
                                        } else {
                                            j3 = j6;
                                        }
                                    }
                                } else {
                                    j3 = j5;
                                }
                            }
                        }
                    }
                    this.currentByteBufferPos = j3;
                    return i;
                }
            }
            return (int) readRawVarint64SlowPath();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readRawVarint64() throws java.io.IOException {
            long j;
            long j2 = this.currentByteBufferPos;
            if (this.currentByteBufferLimit != this.currentByteBufferPos) {
                long j3 = j2 + 1;
                byte b = com.android.framework.protobuf.UnsafeUtil.getByte(j2);
                if (b >= 0) {
                    this.currentByteBufferPos++;
                    return b;
                }
                if (this.currentByteBufferLimit - this.currentByteBufferPos >= 10) {
                    long j4 = j3 + 1;
                    int i = b ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j3) << 7);
                    if (i < 0) {
                        j = i ^ (-128);
                    } else {
                        long j5 = j4 + 1;
                        int i2 = i ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j4) << 14);
                        if (i2 >= 0) {
                            j = i2 ^ 16256;
                            j4 = j5;
                        } else {
                            j4 = j5 + 1;
                            int i3 = i2 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j5) << android.hardware.biometrics.face.AcquiredInfo.START);
                            if (i3 >= 0) {
                                long j6 = j4 + 1;
                                long j7 = i3 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j4) << 28);
                                if (j7 >= 0) {
                                    j = j7 ^ 266354560;
                                    j4 = j6;
                                } else {
                                    long j8 = j6 + 1;
                                    long j9 = j7 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j6) << 35);
                                    if (j9 < 0) {
                                        j = j9 ^ (-34093383808L);
                                        j4 = j8;
                                    } else {
                                        long j10 = j8 + 1;
                                        long j11 = j9 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j8) << 42);
                                        if (j11 >= 0) {
                                            j = j11 ^ 4363953127296L;
                                            j4 = j10;
                                        } else {
                                            long j12 = j10 + 1;
                                            long j13 = j11 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j10) << 49);
                                            if (j13 < 0) {
                                                j = j13 ^ (-558586000294016L);
                                                j4 = j12;
                                            } else {
                                                long j14 = j12 + 1;
                                                j = (j13 ^ (com.android.framework.protobuf.UnsafeUtil.getByte(j12) << 56)) ^ 71499008037633920L;
                                                if (j >= 0) {
                                                    j4 = j14;
                                                } else {
                                                    long j15 = 1 + j14;
                                                    if (com.android.framework.protobuf.UnsafeUtil.getByte(j14) >= 0) {
                                                        j4 = j15;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                j = i3 ^ (-2080896);
                            }
                        }
                    }
                    this.currentByteBufferPos = j4;
                    return j;
                }
            }
            return readRawVarint64SlowPath();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        long readRawVarint64SlowPath() throws java.io.IOException {
            long j = 0;
            for (int i = 0; i < 64; i += 7) {
                j |= (r3 & Byte.MAX_VALUE) << i;
                if ((readRawByte() & 128) == 0) {
                    return j;
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws java.io.IOException {
            if (currentRemaining() >= 4) {
                long j = this.currentByteBufferPos;
                this.currentByteBufferPos += 4;
                return ((com.android.framework.protobuf.UnsafeUtil.getByte(j + 3) & 255) << 24) | (com.android.framework.protobuf.UnsafeUtil.getByte(j) & 255) | ((com.android.framework.protobuf.UnsafeUtil.getByte(1 + j) & 255) << 8) | ((com.android.framework.protobuf.UnsafeUtil.getByte(2 + j) & 255) << 16);
            }
            return (readRawByte() & 255) | ((readRawByte() & 255) << 8) | ((readRawByte() & 255) << 16) | ((readRawByte() & 255) << 24);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws java.io.IOException {
            if (currentRemaining() >= 8) {
                long j = this.currentByteBufferPos;
                this.currentByteBufferPos += 8;
                return ((com.android.framework.protobuf.UnsafeUtil.getByte(6 + j) & 255) << 48) | (com.android.framework.protobuf.UnsafeUtil.getByte(j) & 255) | ((com.android.framework.protobuf.UnsafeUtil.getByte(1 + j) & 255) << 8) | ((com.android.framework.protobuf.UnsafeUtil.getByte(2 + j) & 255) << 16) | ((com.android.framework.protobuf.UnsafeUtil.getByte(3 + j) & 255) << 24) | ((com.android.framework.protobuf.UnsafeUtil.getByte(4 + j) & 255) << 32) | ((com.android.framework.protobuf.UnsafeUtil.getByte(5 + j) & 255) << 40) | ((com.android.framework.protobuf.UnsafeUtil.getByte(j + 7) & 255) << 56);
            }
            return ((readRawByte() & 255) << 56) | (readRawByte() & 255) | ((readRawByte() & 255) << 8) | ((readRawByte() & 255) << 16) | ((readRawByte() & 255) << 24) | ((readRawByte() & 255) << 32) | ((readRawByte() & 255) << 40) | ((readRawByte() & 255) << 48);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void enableAliasing(boolean z) {
            this.enableAliasing = z;
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.startOffset = (int) ((this.totalBytesRead + this.currentByteBufferPos) - this.currentByteBufferStartPos);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int pushLimit(int i) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            if (i < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            int totalBytesRead = i + getTotalBytesRead();
            int i2 = this.currentLimit;
            if (totalBytesRead > i2) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            this.currentLimit = totalBytesRead;
            recomputeBufferSizeAfterLimit();
            return i2;
        }

        private void recomputeBufferSizeAfterLimit() {
            this.totalBufferSize += this.bufferSizeAfterCurrentLimit;
            int i = this.totalBufferSize - this.startOffset;
            if (i > this.currentLimit) {
                this.bufferSizeAfterCurrentLimit = i - this.currentLimit;
                this.totalBufferSize -= this.bufferSizeAfterCurrentLimit;
            } else {
                this.bufferSizeAfterCurrentLimit = 0;
            }
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void popLimit(int i) {
            this.currentLimit = i;
            recomputeBufferSizeAfterLimit();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            if (this.currentLimit == Integer.MAX_VALUE) {
                return -1;
            }
            return this.currentLimit - getTotalBytesRead();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public boolean isAtEnd() throws java.io.IOException {
            return (((long) this.totalBytesRead) + this.currentByteBufferPos) - this.currentByteBufferStartPos == ((long) this.totalBufferSize);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return (int) (((this.totalBytesRead - this.startOffset) + this.currentByteBufferPos) - this.currentByteBufferStartPos);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public byte readRawByte() throws java.io.IOException {
            if (currentRemaining() == 0) {
                getNextByteBuffer();
            }
            long j = this.currentByteBufferPos;
            this.currentByteBufferPos = 1 + j;
            return com.android.framework.protobuf.UnsafeUtil.getByte(j);
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public byte[] readRawBytes(int i) throws java.io.IOException {
            if (i >= 0) {
                long j = i;
                if (j <= currentRemaining()) {
                    byte[] bArr = new byte[i];
                    com.android.framework.protobuf.UnsafeUtil.copyMemory(this.currentByteBufferPos, bArr, 0L, j);
                    this.currentByteBufferPos += j;
                    return bArr;
                }
            }
            if (i >= 0 && i <= remaining()) {
                byte[] bArr2 = new byte[i];
                readRawBytesTo(bArr2, 0, i);
                return bArr2;
            }
            if (i <= 0) {
                if (i == 0) {
                    return com.android.framework.protobuf.Internal.EMPTY_BYTE_ARRAY;
                }
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        private void readRawBytesTo(byte[] bArr, int i, int i2) throws java.io.IOException {
            if (i2 >= 0 && i2 <= remaining()) {
                int i3 = i2;
                while (i3 > 0) {
                    if (currentRemaining() == 0) {
                        getNextByteBuffer();
                    }
                    int min = java.lang.Math.min(i3, (int) currentRemaining());
                    long j = min;
                    com.android.framework.protobuf.UnsafeUtil.copyMemory(this.currentByteBufferPos, bArr, (i2 - i3) + i, j);
                    i3 -= min;
                    this.currentByteBufferPos += j;
                }
                return;
            }
            if (i2 <= 0) {
                if (i2 == 0) {
                    return;
                } else {
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        @Override // com.android.framework.protobuf.CodedInputStream
        public void skipRawBytes(int i) throws java.io.IOException {
            if (i >= 0 && i <= ((this.totalBufferSize - this.totalBytesRead) - this.currentByteBufferPos) + this.currentByteBufferStartPos) {
                while (i > 0) {
                    if (currentRemaining() == 0) {
                        getNextByteBuffer();
                    }
                    int min = java.lang.Math.min(i, (int) currentRemaining());
                    i -= min;
                    this.currentByteBufferPos += min;
                }
                return;
            }
            if (i < 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.negativeSize();
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }

        private void skipRawVarint() throws java.io.IOException {
            for (int i = 0; i < 10; i++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
        }

        private int remaining() {
            return (int) (((this.totalBufferSize - this.totalBytesRead) - this.currentByteBufferPos) + this.currentByteBufferStartPos);
        }

        private long currentRemaining() {
            return this.currentByteBufferLimit - this.currentByteBufferPos;
        }

        private java.nio.ByteBuffer slice(int i, int i2) throws java.io.IOException {
            int position = this.currentByteBuffer.position();
            int limit = this.currentByteBuffer.limit();
            java.nio.ByteBuffer byteBuffer = this.currentByteBuffer;
            try {
                try {
                    byteBuffer.position(i);
                    byteBuffer.limit(i2);
                    return this.currentByteBuffer.slice();
                } catch (java.lang.IllegalArgumentException e) {
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
                }
            } finally {
                byteBuffer.position(position);
                byteBuffer.limit(limit);
            }
        }
    }
}
