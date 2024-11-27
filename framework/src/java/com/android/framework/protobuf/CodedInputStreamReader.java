package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class CodedInputStreamReader implements com.android.framework.protobuf.Reader {
    private static final int FIXED32_MULTIPLE_MASK = 3;
    private static final int FIXED64_MULTIPLE_MASK = 7;
    private static final int NEXT_TAG_UNSET = 0;
    private int endGroupTag;
    private final com.android.framework.protobuf.CodedInputStream input;
    private int nextTag = 0;
    private int tag;

    public static com.android.framework.protobuf.CodedInputStreamReader forCodedInput(com.android.framework.protobuf.CodedInputStream codedInputStream) {
        if (codedInputStream.wrapper != null) {
            return codedInputStream.wrapper;
        }
        return new com.android.framework.protobuf.CodedInputStreamReader(codedInputStream);
    }

    private CodedInputStreamReader(com.android.framework.protobuf.CodedInputStream codedInputStream) {
        this.input = (com.android.framework.protobuf.CodedInputStream) com.android.framework.protobuf.Internal.checkNotNull(codedInputStream, "input");
        this.input.wrapper = this;
    }

    @Override // com.android.framework.protobuf.Reader
    public boolean shouldDiscardUnknownFields() {
        return this.input.shouldDiscardUnknownFields();
    }

    @Override // com.android.framework.protobuf.Reader
    public int getFieldNumber() throws java.io.IOException {
        if (this.nextTag != 0) {
            this.tag = this.nextTag;
            this.nextTag = 0;
        } else {
            this.tag = this.input.readTag();
        }
        if (this.tag == 0 || this.tag == this.endGroupTag) {
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
        if (this.input.isAtEnd() || this.tag == this.endGroupTag) {
            return false;
        }
        return this.input.skipField(this.tag);
    }

    private void requireWireType(int i) throws java.io.IOException {
        if (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag) != i) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
        }
    }

    @Override // com.android.framework.protobuf.Reader
    public double readDouble() throws java.io.IOException {
        requireWireType(1);
        return this.input.readDouble();
    }

    @Override // com.android.framework.protobuf.Reader
    public float readFloat() throws java.io.IOException {
        requireWireType(5);
        return this.input.readFloat();
    }

    @Override // com.android.framework.protobuf.Reader
    public long readUInt64() throws java.io.IOException {
        requireWireType(0);
        return this.input.readUInt64();
    }

    @Override // com.android.framework.protobuf.Reader
    public long readInt64() throws java.io.IOException {
        requireWireType(0);
        return this.input.readInt64();
    }

    @Override // com.android.framework.protobuf.Reader
    public int readInt32() throws java.io.IOException {
        requireWireType(0);
        return this.input.readInt32();
    }

    @Override // com.android.framework.protobuf.Reader
    public long readFixed64() throws java.io.IOException {
        requireWireType(1);
        return this.input.readFixed64();
    }

    @Override // com.android.framework.protobuf.Reader
    public int readFixed32() throws java.io.IOException {
        requireWireType(5);
        return this.input.readFixed32();
    }

    @Override // com.android.framework.protobuf.Reader
    public boolean readBool() throws java.io.IOException {
        requireWireType(0);
        return this.input.readBool();
    }

    @Override // com.android.framework.protobuf.Reader
    public java.lang.String readString() throws java.io.IOException {
        requireWireType(2);
        return this.input.readString();
    }

    @Override // com.android.framework.protobuf.Reader
    public java.lang.String readStringRequireUtf8() throws java.io.IOException {
        requireWireType(2);
        return this.input.readStringRequireUtf8();
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

    @Override // com.android.framework.protobuf.Reader
    public <T> void mergeMessageField(T t, com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        requireWireType(2);
        mergeMessageFieldInternal(t, schema, extensionRegistryLite);
    }

    private <T> void mergeMessageFieldInternal(T t, com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        int readUInt32 = this.input.readUInt32();
        if (this.input.recursionDepth >= this.input.recursionLimit) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.recursionLimitExceeded();
        }
        int pushLimit = this.input.pushLimit(readUInt32);
        this.input.recursionDepth++;
        schema.mergeFrom(t, this, extensionRegistryLite);
        this.input.checkLastTagWas(0);
        com.android.framework.protobuf.CodedInputStream codedInputStream = this.input;
        codedInputStream.recursionDepth--;
        this.input.popLimit(pushLimit);
    }

    private <T> T readMessage(com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        T newInstance = schema.newInstance();
        mergeMessageFieldInternal(newInstance, schema, extensionRegistryLite);
        schema.makeImmutable(newInstance);
        return newInstance;
    }

    @Override // com.android.framework.protobuf.Reader
    public <T> void mergeGroupField(T t, com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        requireWireType(3);
        mergeGroupFieldInternal(t, schema, extensionRegistryLite);
    }

    private <T> void mergeGroupFieldInternal(T t, com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
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

    private <T> T readGroup(com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        T newInstance = schema.newInstance();
        mergeGroupFieldInternal(newInstance, schema, extensionRegistryLite);
        schema.makeImmutable(newInstance);
        return newInstance;
    }

    @Override // com.android.framework.protobuf.Reader
    public com.android.framework.protobuf.ByteString readBytes() throws java.io.IOException {
        requireWireType(2);
        return this.input.readBytes();
    }

    @Override // com.android.framework.protobuf.Reader
    public int readUInt32() throws java.io.IOException {
        requireWireType(0);
        return this.input.readUInt32();
    }

    @Override // com.android.framework.protobuf.Reader
    public int readEnum() throws java.io.IOException {
        requireWireType(0);
        return this.input.readEnum();
    }

    @Override // com.android.framework.protobuf.Reader
    public int readSFixed32() throws java.io.IOException {
        requireWireType(5);
        return this.input.readSFixed32();
    }

    @Override // com.android.framework.protobuf.Reader
    public long readSFixed64() throws java.io.IOException {
        requireWireType(1);
        return this.input.readSFixed64();
    }

    @Override // com.android.framework.protobuf.Reader
    public int readSInt32() throws java.io.IOException {
        requireWireType(0);
        return this.input.readSInt32();
    }

    @Override // com.android.framework.protobuf.Reader
    public long readSInt64() throws java.io.IOException {
        requireWireType(0);
        return this.input.readSInt64();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readDoubleList(java.util.List<java.lang.Double> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.DoubleArrayList) {
            com.android.framework.protobuf.DoubleArrayList doubleArrayList = (com.android.framework.protobuf.DoubleArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 1:
                    break;
                case 2:
                    int readUInt32 = this.input.readUInt32();
                    verifyPackedFixed64Length(readUInt32);
                    int totalBytesRead = this.input.getTotalBytesRead() + readUInt32;
                    do {
                        doubleArrayList.addDouble(this.input.readDouble());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    return;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            do {
                doubleArrayList.addDouble(this.input.readDouble());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 1:
                break;
            case 2:
                int readUInt322 = this.input.readUInt32();
                verifyPackedFixed64Length(readUInt322);
                int totalBytesRead2 = this.input.getTotalBytesRead() + readUInt322;
                do {
                    list.add(java.lang.Double.valueOf(this.input.readDouble()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                return;
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
        }
        do {
            list.add(java.lang.Double.valueOf(this.input.readDouble()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readFloatList(java.util.List<java.lang.Float> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.FloatArrayList) {
            com.android.framework.protobuf.FloatArrayList floatArrayList = (com.android.framework.protobuf.FloatArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 2:
                    int readUInt32 = this.input.readUInt32();
                    verifyPackedFixed32Length(readUInt32);
                    int totalBytesRead = this.input.getTotalBytesRead() + readUInt32;
                    do {
                        floatArrayList.addFloat(this.input.readFloat());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    return;
                case 5:
                    break;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            do {
                floatArrayList.addFloat(this.input.readFloat());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 2:
                int readUInt322 = this.input.readUInt32();
                verifyPackedFixed32Length(readUInt322);
                int totalBytesRead2 = this.input.getTotalBytesRead() + readUInt322;
                do {
                    list.add(java.lang.Float.valueOf(this.input.readFloat()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                return;
            case 5:
                break;
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
        }
        do {
            list.add(java.lang.Float.valueOf(this.input.readFloat()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readUInt64List(java.util.List<java.lang.Long> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.LongArrayList) {
            com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        longArrayList.addLong(this.input.readUInt64());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    requirePosition(totalBytesRead);
                    return;
            }
            do {
                longArrayList.addLong(this.input.readUInt64());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 1:
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            case 2:
                int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    list.add(java.lang.Long.valueOf(this.input.readUInt64()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                requirePosition(totalBytesRead2);
                return;
        }
        do {
            list.add(java.lang.Long.valueOf(this.input.readUInt64()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readInt64List(java.util.List<java.lang.Long> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.LongArrayList) {
            com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        longArrayList.addLong(this.input.readInt64());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    requirePosition(totalBytesRead);
                    return;
            }
            do {
                longArrayList.addLong(this.input.readInt64());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 1:
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            case 2:
                int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    list.add(java.lang.Long.valueOf(this.input.readInt64()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                requirePosition(totalBytesRead2);
                return;
        }
        do {
            list.add(java.lang.Long.valueOf(this.input.readInt64()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readInt32List(java.util.List<java.lang.Integer> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.IntArrayList) {
            com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        intArrayList.addInt(this.input.readInt32());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    requirePosition(totalBytesRead);
                    return;
            }
            do {
                intArrayList.addInt(this.input.readInt32());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 1:
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            case 2:
                int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    list.add(java.lang.Integer.valueOf(this.input.readInt32()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                requirePosition(totalBytesRead2);
                return;
        }
        do {
            list.add(java.lang.Integer.valueOf(this.input.readInt32()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readFixed64List(java.util.List<java.lang.Long> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.LongArrayList) {
            com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 1:
                    break;
                case 2:
                    int readUInt32 = this.input.readUInt32();
                    verifyPackedFixed64Length(readUInt32);
                    int totalBytesRead = this.input.getTotalBytesRead() + readUInt32;
                    do {
                        longArrayList.addLong(this.input.readFixed64());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    return;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            do {
                longArrayList.addLong(this.input.readFixed64());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 1:
                break;
            case 2:
                int readUInt322 = this.input.readUInt32();
                verifyPackedFixed64Length(readUInt322);
                int totalBytesRead2 = this.input.getTotalBytesRead() + readUInt322;
                do {
                    list.add(java.lang.Long.valueOf(this.input.readFixed64()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                return;
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
        }
        do {
            list.add(java.lang.Long.valueOf(this.input.readFixed64()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readFixed32List(java.util.List<java.lang.Integer> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.IntArrayList) {
            com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 2:
                    int readUInt32 = this.input.readUInt32();
                    verifyPackedFixed32Length(readUInt32);
                    int totalBytesRead = this.input.getTotalBytesRead() + readUInt32;
                    do {
                        intArrayList.addInt(this.input.readFixed32());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    return;
                case 5:
                    break;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            do {
                intArrayList.addInt(this.input.readFixed32());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 2:
                int readUInt322 = this.input.readUInt32();
                verifyPackedFixed32Length(readUInt322);
                int totalBytesRead2 = this.input.getTotalBytesRead() + readUInt322;
                do {
                    list.add(java.lang.Integer.valueOf(this.input.readFixed32()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                return;
            case 5:
                break;
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
        }
        do {
            list.add(java.lang.Integer.valueOf(this.input.readFixed32()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readBoolList(java.util.List<java.lang.Boolean> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.BooleanArrayList) {
            com.android.framework.protobuf.BooleanArrayList booleanArrayList = (com.android.framework.protobuf.BooleanArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        booleanArrayList.addBoolean(this.input.readBool());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    requirePosition(totalBytesRead);
                    return;
            }
            do {
                booleanArrayList.addBoolean(this.input.readBool());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 1:
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            case 2:
                int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    list.add(java.lang.Boolean.valueOf(this.input.readBool()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                requirePosition(totalBytesRead2);
                return;
        }
        do {
            list.add(java.lang.Boolean.valueOf(this.input.readBool()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
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
        int readTag;
        int readTag2;
        if (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag) != 2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
        }
        if ((list instanceof com.android.framework.protobuf.LazyStringList) && !z) {
            com.android.framework.protobuf.LazyStringList lazyStringList = (com.android.framework.protobuf.LazyStringList) list;
            do {
                lazyStringList.add(readBytes());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        do {
            list.add(z ? readStringRequireUtf8() : readString());
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public <T> void readMessageList(java.util.List<T> list, java.lang.Class<T> cls, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        readMessageList(list, com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) cls), extensionRegistryLite);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.framework.protobuf.Reader
    public <T> void readMessageList(java.util.List<T> list, com.android.framework.protobuf.Schema<T> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        int readTag;
        if (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag) != 2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
        }
        int i = this.tag;
        do {
            list.add(readMessage(schema, extensionRegistryLite));
            if (this.input.isAtEnd() || this.nextTag != 0) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == i);
        this.nextTag = readTag;
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
        int readTag;
        if (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag) != 3) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
        }
        int i = this.tag;
        do {
            list.add(readGroup(schema, extensionRegistryLite));
            if (this.input.isAtEnd() || this.nextTag != 0) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == i);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readBytesList(java.util.List<com.android.framework.protobuf.ByteString> list) throws java.io.IOException {
        int readTag;
        if (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag) != 2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
        }
        do {
            list.add(readBytes());
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readUInt32List(java.util.List<java.lang.Integer> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.IntArrayList) {
            com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        intArrayList.addInt(this.input.readUInt32());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    requirePosition(totalBytesRead);
                    return;
            }
            do {
                intArrayList.addInt(this.input.readUInt32());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 1:
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            case 2:
                int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    list.add(java.lang.Integer.valueOf(this.input.readUInt32()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                requirePosition(totalBytesRead2);
                return;
        }
        do {
            list.add(java.lang.Integer.valueOf(this.input.readUInt32()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readEnumList(java.util.List<java.lang.Integer> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.IntArrayList) {
            com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        intArrayList.addInt(this.input.readEnum());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    requirePosition(totalBytesRead);
                    return;
            }
            do {
                intArrayList.addInt(this.input.readEnum());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 1:
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            case 2:
                int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    list.add(java.lang.Integer.valueOf(this.input.readEnum()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                requirePosition(totalBytesRead2);
                return;
        }
        do {
            list.add(java.lang.Integer.valueOf(this.input.readEnum()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readSFixed32List(java.util.List<java.lang.Integer> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.IntArrayList) {
            com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 2:
                    int readUInt32 = this.input.readUInt32();
                    verifyPackedFixed32Length(readUInt32);
                    int totalBytesRead = this.input.getTotalBytesRead() + readUInt32;
                    do {
                        intArrayList.addInt(this.input.readSFixed32());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    return;
                case 5:
                    break;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            do {
                intArrayList.addInt(this.input.readSFixed32());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 2:
                int readUInt322 = this.input.readUInt32();
                verifyPackedFixed32Length(readUInt322);
                int totalBytesRead2 = this.input.getTotalBytesRead() + readUInt322;
                do {
                    list.add(java.lang.Integer.valueOf(this.input.readSFixed32()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                return;
            case 5:
                break;
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
        }
        do {
            list.add(java.lang.Integer.valueOf(this.input.readSFixed32()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readSFixed64List(java.util.List<java.lang.Long> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.LongArrayList) {
            com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 1:
                    break;
                case 2:
                    int readUInt32 = this.input.readUInt32();
                    verifyPackedFixed64Length(readUInt32);
                    int totalBytesRead = this.input.getTotalBytesRead() + readUInt32;
                    do {
                        longArrayList.addLong(this.input.readSFixed64());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    return;
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            }
            do {
                longArrayList.addLong(this.input.readSFixed64());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 1:
                break;
            case 2:
                int readUInt322 = this.input.readUInt32();
                verifyPackedFixed64Length(readUInt322);
                int totalBytesRead2 = this.input.getTotalBytesRead() + readUInt322;
                do {
                    list.add(java.lang.Long.valueOf(this.input.readSFixed64()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                return;
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
        }
        do {
            list.add(java.lang.Long.valueOf(this.input.readSFixed64()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readSInt32List(java.util.List<java.lang.Integer> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.IntArrayList) {
            com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        intArrayList.addInt(this.input.readSInt32());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    requirePosition(totalBytesRead);
                    return;
            }
            do {
                intArrayList.addInt(this.input.readSInt32());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 1:
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            case 2:
                int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    list.add(java.lang.Integer.valueOf(this.input.readSInt32()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                requirePosition(totalBytesRead2);
                return;
        }
        do {
            list.add(java.lang.Integer.valueOf(this.input.readSInt32()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readSInt64List(java.util.List<java.lang.Long> list) throws java.io.IOException {
        int readTag;
        int readTag2;
        if (list instanceof com.android.framework.protobuf.LongArrayList) {
            com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) list;
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 1:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 2:
                    int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        longArrayList.addLong(this.input.readSInt64());
                    } while (this.input.getTotalBytesRead() < totalBytesRead);
                    requirePosition(totalBytesRead);
                    return;
            }
            do {
                longArrayList.addLong(this.input.readSInt64());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 1:
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
            case 2:
                int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    list.add(java.lang.Long.valueOf(this.input.readSInt64()));
                } while (this.input.getTotalBytesRead() < totalBytesRead2);
                requirePosition(totalBytesRead2);
                return;
        }
        do {
            list.add(java.lang.Long.valueOf(this.input.readSInt64()));
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    private void verifyPackedFixed64Length(int i) throws java.io.IOException {
        if ((i & 7) != 0) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.framework.protobuf.Reader
    public <K, V> void readMap(java.util.Map<K, V> map, com.android.framework.protobuf.MapEntryLite.Metadata<K, V> metadata, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        requireWireType(2);
        int pushLimit = this.input.pushLimit(this.input.readUInt32());
        java.lang.Object obj = metadata.defaultKey;
        java.lang.Object obj2 = metadata.defaultValue;
        while (true) {
            try {
                int fieldNumber = getFieldNumber();
                if (fieldNumber != Integer.MAX_VALUE && !this.input.isAtEnd()) {
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
                }
            } finally {
                this.input.popLimit(pushLimit);
            }
        }
        map.put(obj, obj2);
    }

    /* renamed from: com.android.framework.protobuf.CodedInputStreamReader$1, reason: invalid class name */
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

    private java.lang.Object readField(com.android.framework.protobuf.WireFormat.FieldType fieldType, java.lang.Class<?> cls, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        switch (com.android.framework.protobuf.CodedInputStreamReader.AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[fieldType.ordinal()]) {
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
                throw new java.lang.IllegalArgumentException("unsupported field type.");
        }
    }

    private void verifyPackedFixed32Length(int i) throws java.io.IOException {
        if ((i & 3) != 0) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
        }
    }

    private void requirePosition(int i) throws java.io.IOException {
        if (this.input.getTotalBytesRead() != i) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
    }
}
