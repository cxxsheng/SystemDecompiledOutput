package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
abstract class BinaryReader implements com.android.framework.protobuf.Reader {
    private static final int FIXED32_MULTIPLE_MASK = 3;
    private static final int FIXED64_MULTIPLE_MASK = 7;

    public abstract int getTotalBytesRead();

    /* synthetic */ BinaryReader(com.android.framework.protobuf.BinaryReader.AnonymousClass1 anonymousClass1) {
        this();
    }

    public static com.android.framework.protobuf.BinaryReader newInstance(java.nio.ByteBuffer byteBuffer, boolean z) {
        if (byteBuffer.hasArray()) {
            return new com.android.framework.protobuf.BinaryReader.SafeHeapReader(byteBuffer, z);
        }
        throw new java.lang.IllegalArgumentException("Direct buffers not yet supported");
    }

    private BinaryReader() {
    }

    @Override // com.android.framework.protobuf.Reader
    public boolean shouldDiscardUnknownFields() {
        return false;
    }

    private static final class SafeHeapReader extends com.android.framework.protobuf.BinaryReader {
        private final byte[] buffer;
        private final boolean bufferIsImmutable;
        private int endGroupTag;
        private final int initialPos;
        private int limit;
        private int pos;
        private int tag;

        public SafeHeapReader(java.nio.ByteBuffer byteBuffer, boolean z) {
            super(null);
            this.bufferIsImmutable = z;
            this.buffer = byteBuffer.array();
            int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
            this.pos = arrayOffset;
            this.initialPos = arrayOffset;
            this.limit = byteBuffer.arrayOffset() + byteBuffer.limit();
        }

        private boolean isAtEnd() {
            return this.pos == this.limit;
        }

        @Override // com.android.framework.protobuf.BinaryReader
        public int getTotalBytesRead() {
            return this.pos - this.initialPos;
        }

        @Override // com.android.framework.protobuf.Reader
        public int getFieldNumber() throws java.io.IOException {
            if (isAtEnd()) {
                return Integer.MAX_VALUE;
            }
            this.tag = readVarint32();
            if (this.tag == this.endGroupTag) {
                return Integer.MAX_VALUE;
            }
            return com.android.framework.protobuf.WireFormat.getTagFieldNumber(this.tag);
        }

        @Override // com.android.framework.protobuf.Reader
        public int getTag() {
            return this.tag;
        }

        @Override // com.android.framework.protobuf.Reader
        public boolean skipField() throws java.io.IOException {
            if (isAtEnd() || this.tag == this.endGroupTag) {
                return false;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    skipVarint();
                    return true;
                case 1:
                    skipBytes(8);
                    return true;
                case 2:
                    skipBytes(readVarint32());
                    return true;
                case 3:
                    skipGroup();
                    return true;
                case 4:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 5:
                    skipBytes(4);
                    return true;
            }
        }

        @Override // com.android.framework.protobuf.Reader
        public double readDouble() throws java.io.IOException {
            requireWireType(1);
            return java.lang.Double.longBitsToDouble(readLittleEndian64());
        }

        @Override // com.android.framework.protobuf.Reader
        public float readFloat() throws java.io.IOException {
            requireWireType(5);
            return java.lang.Float.intBitsToFloat(readLittleEndian32());
        }

        @Override // com.android.framework.protobuf.Reader
        public long readUInt64() throws java.io.IOException {
            requireWireType(0);
            return readVarint64();
        }

        @Override // com.android.framework.protobuf.Reader
        public long readInt64() throws java.io.IOException {
            requireWireType(0);
            return readVarint64();
        }

        @Override // com.android.framework.protobuf.Reader
        public int readInt32() throws java.io.IOException {
            requireWireType(0);
            return readVarint32();
        }

        @Override // com.android.framework.protobuf.Reader
        public long readFixed64() throws java.io.IOException {
            requireWireType(1);
            return readLittleEndian64();
        }

        @Override // com.android.framework.protobuf.Reader
        public int readFixed32() throws java.io.IOException {
            requireWireType(5);
            return readLittleEndian32();
        }

        @Override // com.android.framework.protobuf.Reader
        public boolean readBool() throws java.io.IOException {
            requireWireType(0);
            return readVarint32() != 0;
        }

        @Override // com.android.framework.protobuf.Reader
        public java.lang.String readString() throws java.io.IOException {
            return readStringInternal(false);
        }

        @Override // com.android.framework.protobuf.Reader
        public java.lang.String readStringRequireUtf8() throws java.io.IOException {
            return readStringInternal(true);
        }

        public java.lang.String readStringInternal(boolean z) throws java.io.IOException {
            requireWireType(2);
            int readVarint32 = readVarint32();
            if (readVarint32 == 0) {
                return "";
            }
            requireBytes(readVarint32);
            if (z && !com.android.framework.protobuf.Utf8.isValidUtf8(this.buffer, this.pos, this.pos + readVarint32)) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
            }
            java.lang.String str = new java.lang.String(this.buffer, this.pos, readVarint32, com.android.framework.protobuf.Internal.UTF_8);
            this.pos += readVarint32;
            return str;
        }

        @Override // com.android.framework.protobuf.Reader
        public <T> T readMessage(java.lang.Class<T> cls, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            requireWireType(2);
            return (T) readMessage(com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) cls), extensionRegistryLite);
        }

        @Override // com.android.framework.protobuf.Reader
        public <T> T readMessageBySchemaWithCheck(com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            requireWireType(2);
            return (T) readMessage(schema, extensionRegistryLite);
        }

        private <T> T readMessage(com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            T newInstance = schema.newInstance();
            mergeMessageField(newInstance, schema, extensionRegistryLite);
            schema.makeImmutable(newInstance);
            return newInstance;
        }

        @Override // com.android.framework.protobuf.Reader
        public <T> void mergeMessageField(T t, com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int readVarint32 = readVarint32();
            requireBytes(readVarint32);
            int i = this.limit;
            int i2 = this.pos + readVarint32;
            this.limit = i2;
            try {
                schema.mergeFrom(t, this, extensionRegistryLite);
                if (this.pos != i2) {
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
                }
            } finally {
                this.limit = i;
            }
        }

        @Override // com.android.framework.protobuf.Reader
        @java.lang.Deprecated
        public <T> T readGroup(java.lang.Class<T> cls, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            requireWireType(3);
            return (T) readGroup(com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) cls), extensionRegistryLite);
        }

        @Override // com.android.framework.protobuf.Reader
        @java.lang.Deprecated
        public <T> T readGroupBySchemaWithCheck(com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            requireWireType(3);
            return (T) readGroup(schema, extensionRegistryLite);
        }

        private <T> T readGroup(com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            T newInstance = schema.newInstance();
            mergeGroupField(newInstance, schema, extensionRegistryLite);
            schema.makeImmutable(newInstance);
            return newInstance;
        }

        @Override // com.android.framework.protobuf.Reader
        public <T> void mergeGroupField(T t, com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int i = this.endGroupTag;
            this.endGroupTag = com.android.framework.protobuf.WireFormat.makeTag(com.android.framework.protobuf.WireFormat.getTagFieldNumber(this.tag), 4);
            try {
                schema.mergeFrom(t, this, extensionRegistryLite);
                if (this.tag != this.endGroupTag) {
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
                }
            } finally {
                this.endGroupTag = i;
            }
        }

        @Override // com.android.framework.protobuf.Reader
        public com.android.framework.protobuf.ByteString readBytes() throws java.io.IOException {
            com.android.framework.protobuf.ByteString copyFrom;
            requireWireType(2);
            int readVarint32 = readVarint32();
            if (readVarint32 == 0) {
                return com.android.framework.protobuf.ByteString.EMPTY;
            }
            requireBytes(readVarint32);
            if (this.bufferIsImmutable) {
                copyFrom = com.android.framework.protobuf.ByteString.wrap(this.buffer, this.pos, readVarint32);
            } else {
                copyFrom = com.android.framework.protobuf.ByteString.copyFrom(this.buffer, this.pos, readVarint32);
            }
            this.pos += readVarint32;
            return copyFrom;
        }

        @Override // com.android.framework.protobuf.Reader
        public int readUInt32() throws java.io.IOException {
            requireWireType(0);
            return readVarint32();
        }

        @Override // com.android.framework.protobuf.Reader
        public int readEnum() throws java.io.IOException {
            requireWireType(0);
            return readVarint32();
        }

        @Override // com.android.framework.protobuf.Reader
        public int readSFixed32() throws java.io.IOException {
            requireWireType(5);
            return readLittleEndian32();
        }

        @Override // com.android.framework.protobuf.Reader
        public long readSFixed64() throws java.io.IOException {
            requireWireType(1);
            return readLittleEndian64();
        }

        @Override // com.android.framework.protobuf.Reader
        public int readSInt32() throws java.io.IOException {
            requireWireType(0);
            return com.android.framework.protobuf.CodedInputStream.decodeZigZag32(readVarint32());
        }

        @Override // com.android.framework.protobuf.Reader
        public long readSInt64() throws java.io.IOException {
            requireWireType(0);
            return com.android.framework.protobuf.CodedInputStream.decodeZigZag64(readVarint64());
        }

        @Override // com.android.framework.protobuf.Reader
        public void readDoubleList(java.util.List<java.lang.Double> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.DoubleArrayList) {
                com.android.framework.protobuf.DoubleArrayList doubleArrayList = (com.android.framework.protobuf.DoubleArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 1:
                        break;
                    case 2:
                        int readVarint32 = readVarint32();
                        verifyPackedFixed64Length(readVarint32);
                        int i3 = this.pos + readVarint32;
                        while (this.pos < i3) {
                            doubleArrayList.addDouble(java.lang.Double.longBitsToDouble(readLittleEndian64_NoCheck()));
                        }
                        return;
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                }
                do {
                    doubleArrayList.addDouble(readDouble());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 1:
                    break;
                case 2:
                    int readVarint322 = readVarint32();
                    verifyPackedFixed64Length(readVarint322);
                    int i4 = this.pos + readVarint322;
                    while (this.pos < i4) {
                        list.add(java.lang.Double.valueOf(java.lang.Double.longBitsToDouble(readLittleEndian64_NoCheck())));
                    }
                    return;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            do {
                list.add(java.lang.Double.valueOf(readDouble()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readFloatList(java.util.List<java.lang.Float> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.FloatArrayList) {
                com.android.framework.protobuf.FloatArrayList floatArrayList = (com.android.framework.protobuf.FloatArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 2:
                        int readVarint32 = readVarint32();
                        verifyPackedFixed32Length(readVarint32);
                        int i3 = this.pos + readVarint32;
                        while (this.pos < i3) {
                            floatArrayList.addFloat(java.lang.Float.intBitsToFloat(readLittleEndian32_NoCheck()));
                        }
                        return;
                    case 5:
                        break;
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                }
                do {
                    floatArrayList.addFloat(readFloat());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 2:
                    int readVarint322 = readVarint32();
                    verifyPackedFixed32Length(readVarint322);
                    int i4 = this.pos + readVarint322;
                    while (this.pos < i4) {
                        list.add(java.lang.Float.valueOf(java.lang.Float.intBitsToFloat(readLittleEndian32_NoCheck())));
                    }
                    return;
                case 5:
                    break;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            do {
                list.add(java.lang.Float.valueOf(readFloat()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readUInt64List(java.util.List<java.lang.Long> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.LongArrayList) {
                com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 0:
                        break;
                    case 1:
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                    case 2:
                        int readVarint32 = this.pos + readVarint32();
                        while (this.pos < readVarint32) {
                            longArrayList.addLong(readVarint64());
                        }
                        requirePosition(readVarint32);
                        return;
                }
                do {
                    longArrayList.addLong(readUInt64());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int readVarint322 = this.pos + readVarint32();
                    while (this.pos < readVarint322) {
                        list.add(java.lang.Long.valueOf(readVarint64()));
                    }
                    requirePosition(readVarint322);
                    return;
            }
            do {
                list.add(java.lang.Long.valueOf(readUInt64()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readInt64List(java.util.List<java.lang.Long> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.LongArrayList) {
                com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 0:
                        break;
                    case 1:
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                    case 2:
                        int readVarint32 = this.pos + readVarint32();
                        while (this.pos < readVarint32) {
                            longArrayList.addLong(readVarint64());
                        }
                        requirePosition(readVarint32);
                        return;
                }
                do {
                    longArrayList.addLong(readInt64());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int readVarint322 = this.pos + readVarint32();
                    while (this.pos < readVarint322) {
                        list.add(java.lang.Long.valueOf(readVarint64()));
                    }
                    requirePosition(readVarint322);
                    return;
            }
            do {
                list.add(java.lang.Long.valueOf(readInt64()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readInt32List(java.util.List<java.lang.Integer> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.IntArrayList) {
                com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 0:
                        break;
                    case 1:
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                    case 2:
                        int readVarint32 = this.pos + readVarint32();
                        while (this.pos < readVarint32) {
                            intArrayList.addInt(readVarint32());
                        }
                        requirePosition(readVarint32);
                        return;
                }
                do {
                    intArrayList.addInt(readInt32());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int readVarint322 = this.pos + readVarint32();
                    while (this.pos < readVarint322) {
                        list.add(java.lang.Integer.valueOf(readVarint32()));
                    }
                    requirePosition(readVarint322);
                    return;
            }
            do {
                list.add(java.lang.Integer.valueOf(readInt32()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readFixed64List(java.util.List<java.lang.Long> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.LongArrayList) {
                com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 1:
                        break;
                    case 2:
                        int readVarint32 = readVarint32();
                        verifyPackedFixed64Length(readVarint32);
                        int i3 = this.pos + readVarint32;
                        while (this.pos < i3) {
                            longArrayList.addLong(readLittleEndian64_NoCheck());
                        }
                        return;
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                }
                do {
                    longArrayList.addLong(readFixed64());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 1:
                    break;
                case 2:
                    int readVarint322 = readVarint32();
                    verifyPackedFixed64Length(readVarint322);
                    int i4 = this.pos + readVarint322;
                    while (this.pos < i4) {
                        list.add(java.lang.Long.valueOf(readLittleEndian64_NoCheck()));
                    }
                    return;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            do {
                list.add(java.lang.Long.valueOf(readFixed64()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readFixed32List(java.util.List<java.lang.Integer> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.IntArrayList) {
                com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 2:
                        int readVarint32 = readVarint32();
                        verifyPackedFixed32Length(readVarint32);
                        int i3 = this.pos + readVarint32;
                        while (this.pos < i3) {
                            intArrayList.addInt(readLittleEndian32_NoCheck());
                        }
                        return;
                    case 5:
                        break;
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                }
                do {
                    intArrayList.addInt(readFixed32());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 2:
                    int readVarint322 = readVarint32();
                    verifyPackedFixed32Length(readVarint322);
                    int i4 = this.pos + readVarint322;
                    while (this.pos < i4) {
                        list.add(java.lang.Integer.valueOf(readLittleEndian32_NoCheck()));
                    }
                    return;
                case 5:
                    break;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            do {
                list.add(java.lang.Integer.valueOf(readFixed32()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readBoolList(java.util.List<java.lang.Boolean> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.BooleanArrayList) {
                com.android.framework.protobuf.BooleanArrayList booleanArrayList = (com.android.framework.protobuf.BooleanArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 0:
                        break;
                    case 1:
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                    case 2:
                        int readVarint32 = this.pos + readVarint32();
                        while (this.pos < readVarint32) {
                            booleanArrayList.addBoolean(readVarint32() != 0);
                        }
                        requirePosition(readVarint32);
                        return;
                }
                do {
                    booleanArrayList.addBoolean(readBool());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int readVarint322 = this.pos + readVarint32();
                    while (this.pos < readVarint322) {
                        list.add(java.lang.Boolean.valueOf(readVarint32() != 0));
                    }
                    requirePosition(readVarint322);
                    return;
            }
            do {
                list.add(java.lang.Boolean.valueOf(readBool()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readStringList(java.util.List<java.lang.String> list) throws java.io.IOException {
            readStringListInternal(list, false);
        }

        @Override // com.android.framework.protobuf.Reader
        public void readStringListRequireUtf8(java.util.List<java.lang.String> list) throws java.io.IOException {
            readStringListInternal(list, true);
        }

        public void readStringListInternal(java.util.List<java.lang.String> list, boolean z) throws java.io.IOException {
            int i;
            int i2;
            if (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag) != 2) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            if ((list instanceof com.android.framework.protobuf.LazyStringList) && !z) {
                com.android.framework.protobuf.LazyStringList lazyStringList = (com.android.framework.protobuf.LazyStringList) list;
                do {
                    lazyStringList.add(readBytes());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            do {
                list.add(readStringInternal(z));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public <T> void readMessageList(java.util.List<T> list, java.lang.Class<T> cls, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            readMessageList(list, com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) cls), extensionRegistryLite);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.framework.protobuf.Reader
        public <T> void readMessageList(java.util.List<T> list, com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int i;
            if (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag) != 2) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            int i2 = this.tag;
            do {
                list.add(readMessage(schema, extensionRegistryLite));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == i2);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        @java.lang.Deprecated
        public <T> void readGroupList(java.util.List<T> list, java.lang.Class<T> cls, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            readGroupList(list, com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) cls), extensionRegistryLite);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.framework.protobuf.Reader
        @java.lang.Deprecated
        public <T> void readGroupList(java.util.List<T> list, com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int i;
            if (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag) != 3) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            int i2 = this.tag;
            do {
                list.add(readGroup(schema, extensionRegistryLite));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == i2);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readBytesList(java.util.List<com.android.framework.protobuf.ByteString> list) throws java.io.IOException {
            int i;
            if (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag) != 2) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            do {
                list.add(readBytes());
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readUInt32List(java.util.List<java.lang.Integer> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.IntArrayList) {
                com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 0:
                        break;
                    case 1:
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                    case 2:
                        int readVarint32 = this.pos + readVarint32();
                        while (this.pos < readVarint32) {
                            intArrayList.addInt(readVarint32());
                        }
                        return;
                }
                do {
                    intArrayList.addInt(readUInt32());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int readVarint322 = this.pos + readVarint32();
                    while (this.pos < readVarint322) {
                        list.add(java.lang.Integer.valueOf(readVarint32()));
                    }
                    return;
            }
            do {
                list.add(java.lang.Integer.valueOf(readUInt32()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readEnumList(java.util.List<java.lang.Integer> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.IntArrayList) {
                com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 0:
                        break;
                    case 1:
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                    case 2:
                        int readVarint32 = this.pos + readVarint32();
                        while (this.pos < readVarint32) {
                            intArrayList.addInt(readVarint32());
                        }
                        return;
                }
                do {
                    intArrayList.addInt(readEnum());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int readVarint322 = this.pos + readVarint32();
                    while (this.pos < readVarint322) {
                        list.add(java.lang.Integer.valueOf(readVarint32()));
                    }
                    return;
            }
            do {
                list.add(java.lang.Integer.valueOf(readEnum()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readSFixed32List(java.util.List<java.lang.Integer> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.IntArrayList) {
                com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 2:
                        int readVarint32 = readVarint32();
                        verifyPackedFixed32Length(readVarint32);
                        int i3 = this.pos + readVarint32;
                        while (this.pos < i3) {
                            intArrayList.addInt(readLittleEndian32_NoCheck());
                        }
                        return;
                    case 5:
                        break;
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                }
                do {
                    intArrayList.addInt(readSFixed32());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 2:
                    int readVarint322 = readVarint32();
                    verifyPackedFixed32Length(readVarint322);
                    int i4 = this.pos + readVarint322;
                    while (this.pos < i4) {
                        list.add(java.lang.Integer.valueOf(readLittleEndian32_NoCheck()));
                    }
                    return;
                case 5:
                    break;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            do {
                list.add(java.lang.Integer.valueOf(readSFixed32()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readSFixed64List(java.util.List<java.lang.Long> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.LongArrayList) {
                com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 1:
                        break;
                    case 2:
                        int readVarint32 = readVarint32();
                        verifyPackedFixed64Length(readVarint32);
                        int i3 = this.pos + readVarint32;
                        while (this.pos < i3) {
                            longArrayList.addLong(readLittleEndian64_NoCheck());
                        }
                        return;
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                }
                do {
                    longArrayList.addLong(readSFixed64());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 1:
                    break;
                case 2:
                    int readVarint322 = readVarint32();
                    verifyPackedFixed64Length(readVarint322);
                    int i4 = this.pos + readVarint322;
                    while (this.pos < i4) {
                        list.add(java.lang.Long.valueOf(readLittleEndian64_NoCheck()));
                    }
                    return;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            do {
                list.add(java.lang.Long.valueOf(readSFixed64()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readSInt32List(java.util.List<java.lang.Integer> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.IntArrayList) {
                com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 0:
                        break;
                    case 1:
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                    case 2:
                        int readVarint32 = this.pos + readVarint32();
                        while (this.pos < readVarint32) {
                            intArrayList.addInt(com.android.framework.protobuf.CodedInputStream.decodeZigZag32(readVarint32()));
                        }
                        return;
                }
                do {
                    intArrayList.addInt(readSInt32());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int readVarint322 = this.pos + readVarint32();
                    while (this.pos < readVarint322) {
                        list.add(java.lang.Integer.valueOf(com.android.framework.protobuf.CodedInputStream.decodeZigZag32(readVarint32())));
                    }
                    return;
            }
            do {
                list.add(java.lang.Integer.valueOf(readSInt32()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        @Override // com.android.framework.protobuf.Reader
        public void readSInt64List(java.util.List<java.lang.Long> list) throws java.io.IOException {
            int i;
            int i2;
            if (list instanceof com.android.framework.protobuf.LongArrayList) {
                com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) list;
                switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                    case 0:
                        break;
                    case 1:
                    default:
                        throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                    case 2:
                        int readVarint32 = this.pos + readVarint32();
                        while (this.pos < readVarint32) {
                            longArrayList.addLong(com.android.framework.protobuf.CodedInputStream.decodeZigZag64(readVarint64()));
                        }
                        return;
                }
                do {
                    longArrayList.addLong(readSInt64());
                    if (isAtEnd()) {
                        return;
                    } else {
                        i2 = this.pos;
                    }
                } while (readVarint32() == this.tag);
                this.pos = i2;
                return;
            }
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int readVarint322 = this.pos + readVarint32();
                    while (this.pos < readVarint322) {
                        list.add(java.lang.Long.valueOf(com.android.framework.protobuf.CodedInputStream.decodeZigZag64(readVarint64())));
                    }
                    return;
            }
            do {
                list.add(java.lang.Long.valueOf(readSInt64()));
                if (isAtEnd()) {
                    return;
                } else {
                    i = this.pos;
                }
            } while (readVarint32() == this.tag);
            this.pos = i;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.framework.protobuf.Reader
        public <K, V> void readMap(java.util.Map<K, V> map, com.android.framework.protobuf.MapEntryLite.Metadata<K, V> metadata, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            requireWireType(2);
            int readVarint32 = readVarint32();
            requireBytes(readVarint32);
            int i = this.limit;
            this.limit = this.pos + readVarint32;
            try {
                java.lang.Object obj = metadata.defaultKey;
                java.lang.Object obj2 = metadata.defaultValue;
                while (true) {
                    int fieldNumber = getFieldNumber();
                    if (fieldNumber != Integer.MAX_VALUE) {
                        switch (fieldNumber) {
                            case 1:
                                obj = readField(metadata.keyType, null, null);
                            case 2:
                                obj2 = readField(metadata.valueType, metadata.defaultValue.getClass(), extensionRegistryLite);
                            default:
                                try {
                                    if (!skipField()) {
                                        throw new com.android.framework.protobuf.InvalidProtocolBufferException("Unable to parse map entry.");
                                        break;
                                    }
                                } catch (com.android.framework.protobuf.InvalidProtocolBufferException.InvalidWireTypeException e) {
                                    if (!skipField()) {
                                        throw new com.android.framework.protobuf.InvalidProtocolBufferException("Unable to parse map entry.");
                                    }
                                }
                        }
                    } else {
                        map.put(obj, obj2);
                        return;
                    }
                }
            } finally {
                this.limit = i;
            }
        }

        private java.lang.Object readField(com.android.framework.protobuf.WireFormat.FieldType fieldType, java.lang.Class<?> cls, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            switch (com.android.framework.protobuf.BinaryReader.AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[fieldType.ordinal()]) {
                case 1:
                    return java.lang.Boolean.valueOf(readBool());
                case 2:
                    return readBytes();
                case 3:
                    return java.lang.Double.valueOf(readDouble());
                case 4:
                    return java.lang.Integer.valueOf(readEnum());
                case 5:
                    return java.lang.Integer.valueOf(readFixed32());
                case 6:
                    return java.lang.Long.valueOf(readFixed64());
                case 7:
                    return java.lang.Float.valueOf(readFloat());
                case 8:
                    return java.lang.Integer.valueOf(readInt32());
                case 9:
                    return java.lang.Long.valueOf(readInt64());
                case 10:
                    return readMessage(cls, extensionRegistryLite);
                case 11:
                    return java.lang.Integer.valueOf(readSFixed32());
                case 12:
                    return java.lang.Long.valueOf(readSFixed64());
                case 13:
                    return java.lang.Integer.valueOf(readSInt32());
                case 14:
                    return java.lang.Long.valueOf(readSInt64());
                case 15:
                    return readStringRequireUtf8();
                case 16:
                    return java.lang.Integer.valueOf(readUInt32());
                case 17:
                    return java.lang.Long.valueOf(readUInt64());
                default:
                    throw new java.lang.RuntimeException("unsupported field type.");
            }
        }

        private int readVarint32() throws java.io.IOException {
            int i;
            int i2 = this.pos;
            if (this.limit == this.pos) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            int i3 = i2 + 1;
            byte b = this.buffer[i2];
            if (b >= 0) {
                this.pos = i3;
                return b;
            }
            if (this.limit - i3 < 9) {
                return (int) readVarint64SlowPath();
            }
            int i4 = i3 + 1;
            int i5 = b ^ (this.buffer[i3] << 7);
            if (i5 < 0) {
                i = i5 ^ (-128);
            } else {
                int i6 = i4 + 1;
                int i7 = i5 ^ (this.buffer[i4] << 14);
                if (i7 >= 0) {
                    i = i7 ^ 16256;
                    i4 = i6;
                } else {
                    i4 = i6 + 1;
                    int i8 = i7 ^ (this.buffer[i6] << android.hardware.biometrics.face.AcquiredInfo.START);
                    if (i8 < 0) {
                        i = i8 ^ (-2080896);
                    } else {
                        int i9 = i4 + 1;
                        byte b2 = this.buffer[i4];
                        i = (i8 ^ (b2 << 28)) ^ 266354560;
                        if (b2 < 0) {
                            i4 = i9 + 1;
                            if (this.buffer[i9] < 0) {
                                i9 = i4 + 1;
                                if (this.buffer[i4] < 0) {
                                    i4 = i9 + 1;
                                    if (this.buffer[i9] < 0) {
                                        i9 = i4 + 1;
                                        if (this.buffer[i4] < 0) {
                                            i4 = i9 + 1;
                                            if (this.buffer[i9] < 0) {
                                                throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
                                            }
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

        public long readVarint64() throws java.io.IOException {
            long j;
            int i = this.pos;
            if (this.limit == i) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            int i2 = i + 1;
            byte b = bArr[i];
            if (b >= 0) {
                this.pos = i2;
                return b;
            }
            if (this.limit - i2 < 9) {
                return readVarint64SlowPath();
            }
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
                                            if (bArr[i12] < 0) {
                                                throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
                                            }
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
            this.pos = i3;
            return j;
        }

        private long readVarint64SlowPath() throws java.io.IOException {
            long j = 0;
            for (int i = 0; i < 64; i += 7) {
                j |= (r3 & Byte.MAX_VALUE) << i;
                if ((readByte() & 128) == 0) {
                    return j;
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
        }

        private byte readByte() throws java.io.IOException {
            if (this.pos == this.limit) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
            byte[] bArr = this.buffer;
            int i = this.pos;
            this.pos = i + 1;
            return bArr[i];
        }

        private int readLittleEndian32() throws java.io.IOException {
            requireBytes(4);
            return readLittleEndian32_NoCheck();
        }

        private long readLittleEndian64() throws java.io.IOException {
            requireBytes(8);
            return readLittleEndian64_NoCheck();
        }

        private int readLittleEndian32_NoCheck() {
            int i = this.pos;
            byte[] bArr = this.buffer;
            this.pos = i + 4;
            return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
        }

        private long readLittleEndian64_NoCheck() {
            int i = this.pos;
            byte[] bArr = this.buffer;
            this.pos = i + 8;
            return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
        }

        private void skipVarint() throws java.io.IOException {
            if (this.limit - this.pos >= 10) {
                byte[] bArr = this.buffer;
                int i = this.pos;
                int i2 = 0;
                while (i2 < 10) {
                    int i3 = i + 1;
                    if (bArr[i] < 0) {
                        i2++;
                        i = i3;
                    } else {
                        this.pos = i3;
                        return;
                    }
                }
            }
            skipVarintSlowPath();
        }

        private void skipVarintSlowPath() throws java.io.IOException {
            for (int i = 0; i < 10; i++) {
                if (readByte() >= 0) {
                    return;
                }
            }
            throw com.android.framework.protobuf.InvalidProtocolBufferException.malformedVarint();
        }

        private void skipBytes(int i) throws java.io.IOException {
            requireBytes(i);
            this.pos += i;
        }

        private void skipGroup() throws java.io.IOException {
            int i = this.endGroupTag;
            this.endGroupTag = com.android.framework.protobuf.WireFormat.makeTag(com.android.framework.protobuf.WireFormat.getTagFieldNumber(this.tag), 4);
            while (getFieldNumber() != Integer.MAX_VALUE && skipField()) {
            }
            if (this.tag != this.endGroupTag) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
            }
            this.endGroupTag = i;
        }

        private void requireBytes(int i) throws java.io.IOException {
            if (i < 0 || i > this.limit - this.pos) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
        }

        private void requireWireType(int i) throws java.io.IOException {
            if (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag) != i) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
        }

        private void verifyPackedFixed64Length(int i) throws java.io.IOException {
            requireBytes(i);
            if ((i & 7) != 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
            }
        }

        private void verifyPackedFixed32Length(int i) throws java.io.IOException {
            requireBytes(i);
            if ((i & 3) != 0) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
            }
        }

        private void requirePosition(int i) throws java.io.IOException {
            if (this.pos != i) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
        }
    }

    /* renamed from: com.android.framework.protobuf.BinaryReader$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$FieldType = new int[com.android.framework.protobuf.WireFormat.FieldType.values().length];

        static {
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.BOOL.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.BYTES.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.DOUBLE.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.ENUM.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.FIXED32.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.FIXED64.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.FLOAT.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.INT32.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.INT64.ordinal()] = 9;
            } catch (java.lang.NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.MESSAGE.ordinal()] = 10;
            } catch (java.lang.NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SFIXED32.ordinal()] = 11;
            } catch (java.lang.NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SFIXED64.ordinal()] = 12;
            } catch (java.lang.NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SINT32.ordinal()] = 13;
            } catch (java.lang.NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SINT64.ordinal()] = 14;
            } catch (java.lang.NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.STRING.ordinal()] = 15;
            } catch (java.lang.NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.UINT32.ordinal()] = 16;
            } catch (java.lang.NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.UINT64.ordinal()] = 17;
            } catch (java.lang.NoSuchFieldError e17) {
            }
        }
    }
}
