package com.android.framework.protobuf.nano;

/* loaded from: classes4.dex */
public class Extension<M extends com.android.framework.protobuf.nano.ExtendableMessageNano<M>, T> {
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
    protected final java.lang.Class<T> clazz;
    protected final boolean repeated;
    public final int tag;
    protected final int type;

    @java.lang.Deprecated
    public static <M extends com.android.framework.protobuf.nano.ExtendableMessageNano<M>, T extends com.android.framework.protobuf.nano.MessageNano> com.android.framework.protobuf.nano.Extension<M, T> createMessageTyped(int i, java.lang.Class<T> cls, int i2) {
        return new com.android.framework.protobuf.nano.Extension<>(i, cls, i2, false);
    }

    public static <M extends com.android.framework.protobuf.nano.ExtendableMessageNano<M>, T extends com.android.framework.protobuf.nano.MessageNano> com.android.framework.protobuf.nano.Extension<M, T> createMessageTyped(int i, java.lang.Class<T> cls, long j) {
        return new com.android.framework.protobuf.nano.Extension<>(i, cls, (int) j, false);
    }

    public static <M extends com.android.framework.protobuf.nano.ExtendableMessageNano<M>, T extends com.android.framework.protobuf.nano.MessageNano> com.android.framework.protobuf.nano.Extension<M, T[]> createRepeatedMessageTyped(int i, java.lang.Class<T[]> cls, long j) {
        return new com.android.framework.protobuf.nano.Extension<>(i, cls, (int) j, true);
    }

    public static <M extends com.android.framework.protobuf.nano.ExtendableMessageNano<M>, T> com.android.framework.protobuf.nano.Extension<M, T> createPrimitiveTyped(int i, java.lang.Class<T> cls, long j) {
        return new com.android.framework.protobuf.nano.Extension.PrimitiveExtension(i, cls, (int) j, false, 0, 0);
    }

    public static <M extends com.android.framework.protobuf.nano.ExtendableMessageNano<M>, T> com.android.framework.protobuf.nano.Extension<M, T> createRepeatedPrimitiveTyped(int i, java.lang.Class<T> cls, long j, long j2, long j3) {
        return new com.android.framework.protobuf.nano.Extension.PrimitiveExtension(i, cls, (int) j, true, (int) j2, (int) j3);
    }

    private Extension(int i, java.lang.Class<T> cls, int i2, boolean z) {
        this.type = i;
        this.clazz = cls;
        this.tag = i2;
        this.repeated = z;
    }

    final T getValueFrom(java.util.List<com.android.framework.protobuf.nano.UnknownFieldData> list) {
        if (list == null) {
            return null;
        }
        return this.repeated ? getRepeatedValueFrom(list) : getSingularValueFrom(list);
    }

    private T getRepeatedValueFrom(java.util.List<com.android.framework.protobuf.nano.UnknownFieldData> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < list.size(); i++) {
            com.android.framework.protobuf.nano.UnknownFieldData unknownFieldData = list.get(i);
            if (unknownFieldData.bytes.length != 0) {
                readDataInto(unknownFieldData, arrayList);
            }
        }
        int size = arrayList.size();
        if (size == 0) {
            return null;
        }
        T cast = this.clazz.cast(java.lang.reflect.Array.newInstance(this.clazz.getComponentType(), size));
        for (int i2 = 0; i2 < size; i2++) {
            java.lang.reflect.Array.set(cast, i2, arrayList.get(i2));
        }
        return cast;
    }

    private T getSingularValueFrom(java.util.List<com.android.framework.protobuf.nano.UnknownFieldData> list) {
        if (list.isEmpty()) {
            return null;
        }
        return this.clazz.cast(readData(com.android.framework.protobuf.nano.CodedInputByteBufferNano.newInstance(list.get(list.size() - 1).bytes)));
    }

    protected java.lang.Object readData(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) {
        java.lang.Class componentType = this.repeated ? this.clazz.getComponentType() : this.clazz;
        try {
            switch (this.type) {
                case 10:
                    com.android.framework.protobuf.nano.MessageNano messageNano = (com.android.framework.protobuf.nano.MessageNano) componentType.newInstance();
                    codedInputByteBufferNano.readGroup(messageNano, com.android.framework.protobuf.nano.WireFormatNano.getTagFieldNumber(this.tag));
                    return messageNano;
                case 11:
                    com.android.framework.protobuf.nano.MessageNano messageNano2 = (com.android.framework.protobuf.nano.MessageNano) componentType.newInstance();
                    codedInputByteBufferNano.readMessage(messageNano2);
                    return messageNano2;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown type " + this.type);
            }
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("Error reading extension field", e);
        } catch (java.lang.IllegalAccessException e2) {
            throw new java.lang.IllegalArgumentException("Error creating instance of class " + componentType, e2);
        } catch (java.lang.InstantiationException e3) {
            throw new java.lang.IllegalArgumentException("Error creating instance of class " + componentType, e3);
        }
    }

    protected void readDataInto(com.android.framework.protobuf.nano.UnknownFieldData unknownFieldData, java.util.List<java.lang.Object> list) {
        list.add(readData(com.android.framework.protobuf.nano.CodedInputByteBufferNano.newInstance(unknownFieldData.bytes)));
    }

    void writeTo(java.lang.Object obj, com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
        if (this.repeated) {
            writeRepeatedData(obj, codedOutputByteBufferNano);
        } else {
            writeSingularData(obj, codedOutputByteBufferNano);
        }
    }

    protected void writeSingularData(java.lang.Object obj, com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) {
        try {
            codedOutputByteBufferNano.writeRawVarint32(this.tag);
            switch (this.type) {
                case 10:
                    int tagFieldNumber = com.android.framework.protobuf.nano.WireFormatNano.getTagFieldNumber(this.tag);
                    codedOutputByteBufferNano.writeGroupNoTag((com.android.framework.protobuf.nano.MessageNano) obj);
                    codedOutputByteBufferNano.writeTag(tagFieldNumber, 4);
                    return;
                case 11:
                    codedOutputByteBufferNano.writeMessageNoTag((com.android.framework.protobuf.nano.MessageNano) obj);
                    return;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown type " + this.type);
            }
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    protected void writeRepeatedData(java.lang.Object obj, com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) {
        int length = java.lang.reflect.Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            java.lang.Object obj2 = java.lang.reflect.Array.get(obj, i);
            if (obj2 != null) {
                writeSingularData(obj2, codedOutputByteBufferNano);
            }
        }
    }

    int computeSerializedSize(java.lang.Object obj) {
        if (this.repeated) {
            return computeRepeatedSerializedSize(obj);
        }
        return computeSingularSerializedSize(obj);
    }

    protected int computeRepeatedSerializedSize(java.lang.Object obj) {
        int length = java.lang.reflect.Array.getLength(obj);
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (java.lang.reflect.Array.get(obj, i2) != null) {
                i += computeSingularSerializedSize(java.lang.reflect.Array.get(obj, i2));
            }
        }
        return i;
    }

    protected int computeSingularSerializedSize(java.lang.Object obj) {
        int tagFieldNumber = com.android.framework.protobuf.nano.WireFormatNano.getTagFieldNumber(this.tag);
        switch (this.type) {
            case 10:
                return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeGroupSize(tagFieldNumber, (com.android.framework.protobuf.nano.MessageNano) obj);
            case 11:
                return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(tagFieldNumber, (com.android.framework.protobuf.nano.MessageNano) obj);
            default:
                throw new java.lang.IllegalArgumentException("Unknown type " + this.type);
        }
    }

    private static class PrimitiveExtension<M extends com.android.framework.protobuf.nano.ExtendableMessageNano<M>, T> extends com.android.framework.protobuf.nano.Extension<M, T> {
        private final int nonPackedTag;
        private final int packedTag;

        public PrimitiveExtension(int i, java.lang.Class<T> cls, int i2, boolean z, int i3, int i4) {
            super(i, cls, i2, z);
            this.nonPackedTag = i3;
            this.packedTag = i4;
        }

        @Override // com.android.framework.protobuf.nano.Extension
        protected java.lang.Object readData(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) {
            try {
                return codedInputByteBufferNano.readPrimitiveField(this.type);
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("Error reading extension field", e);
            }
        }

        @Override // com.android.framework.protobuf.nano.Extension
        protected void readDataInto(com.android.framework.protobuf.nano.UnknownFieldData unknownFieldData, java.util.List<java.lang.Object> list) {
            if (unknownFieldData.tag == this.nonPackedTag) {
                list.add(readData(com.android.framework.protobuf.nano.CodedInputByteBufferNano.newInstance(unknownFieldData.bytes)));
                return;
            }
            com.android.framework.protobuf.nano.CodedInputByteBufferNano newInstance = com.android.framework.protobuf.nano.CodedInputByteBufferNano.newInstance(unknownFieldData.bytes);
            try {
                newInstance.pushLimit(newInstance.readRawVarint32());
                while (!newInstance.isAtEnd()) {
                    list.add(readData(newInstance));
                }
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("Error reading extension field", e);
            }
        }

        @Override // com.android.framework.protobuf.nano.Extension
        protected final void writeSingularData(java.lang.Object obj, com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) {
            try {
                codedOutputByteBufferNano.writeRawVarint32(this.tag);
                switch (this.type) {
                    case 1:
                        codedOutputByteBufferNano.writeDoubleNoTag(((java.lang.Double) obj).doubleValue());
                        return;
                    case 2:
                        codedOutputByteBufferNano.writeFloatNoTag(((java.lang.Float) obj).floatValue());
                        return;
                    case 3:
                        codedOutputByteBufferNano.writeInt64NoTag(((java.lang.Long) obj).longValue());
                        return;
                    case 4:
                        codedOutputByteBufferNano.writeUInt64NoTag(((java.lang.Long) obj).longValue());
                        return;
                    case 5:
                        codedOutputByteBufferNano.writeInt32NoTag(((java.lang.Integer) obj).intValue());
                        return;
                    case 6:
                        codedOutputByteBufferNano.writeFixed64NoTag(((java.lang.Long) obj).longValue());
                        return;
                    case 7:
                        codedOutputByteBufferNano.writeFixed32NoTag(((java.lang.Integer) obj).intValue());
                        return;
                    case 8:
                        codedOutputByteBufferNano.writeBoolNoTag(((java.lang.Boolean) obj).booleanValue());
                        return;
                    case 9:
                        codedOutputByteBufferNano.writeStringNoTag((java.lang.String) obj);
                        return;
                    case 10:
                    case 11:
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown type " + this.type);
                    case 12:
                        codedOutputByteBufferNano.writeBytesNoTag((byte[]) obj);
                        return;
                    case 13:
                        codedOutputByteBufferNano.writeUInt32NoTag(((java.lang.Integer) obj).intValue());
                        return;
                    case 14:
                        codedOutputByteBufferNano.writeEnumNoTag(((java.lang.Integer) obj).intValue());
                        return;
                    case 15:
                        codedOutputByteBufferNano.writeSFixed32NoTag(((java.lang.Integer) obj).intValue());
                        return;
                    case 16:
                        codedOutputByteBufferNano.writeSFixed64NoTag(((java.lang.Long) obj).longValue());
                        return;
                    case 17:
                        codedOutputByteBufferNano.writeSInt32NoTag(((java.lang.Integer) obj).intValue());
                        return;
                    case 18:
                        codedOutputByteBufferNano.writeSInt64NoTag(((java.lang.Long) obj).longValue());
                        return;
                }
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalStateException(e);
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // com.android.framework.protobuf.nano.Extension
        protected void writeRepeatedData(java.lang.Object obj, com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) {
            if (this.tag == this.nonPackedTag) {
                super.writeRepeatedData(obj, codedOutputByteBufferNano);
                return;
            }
            if (this.tag == this.packedTag) {
                int length = java.lang.reflect.Array.getLength(obj);
                int computePackedDataSize = computePackedDataSize(obj);
                try {
                    codedOutputByteBufferNano.writeRawVarint32(this.tag);
                    codedOutputByteBufferNano.writeRawVarint32(computePackedDataSize);
                    int i = 0;
                    switch (this.type) {
                        case 1:
                            while (i < length) {
                                codedOutputByteBufferNano.writeDoubleNoTag(java.lang.reflect.Array.getDouble(obj, i));
                                i++;
                            }
                            return;
                        case 2:
                            while (i < length) {
                                codedOutputByteBufferNano.writeFloatNoTag(java.lang.reflect.Array.getFloat(obj, i));
                                i++;
                            }
                            return;
                        case 3:
                            while (i < length) {
                                codedOutputByteBufferNano.writeInt64NoTag(java.lang.reflect.Array.getLong(obj, i));
                                i++;
                            }
                            return;
                        case 4:
                            while (i < length) {
                                codedOutputByteBufferNano.writeUInt64NoTag(java.lang.reflect.Array.getLong(obj, i));
                                i++;
                            }
                            return;
                        case 5:
                            while (i < length) {
                                codedOutputByteBufferNano.writeInt32NoTag(java.lang.reflect.Array.getInt(obj, i));
                                i++;
                            }
                            return;
                        case 6:
                            while (i < length) {
                                codedOutputByteBufferNano.writeFixed64NoTag(java.lang.reflect.Array.getLong(obj, i));
                                i++;
                            }
                            return;
                        case 7:
                            while (i < length) {
                                codedOutputByteBufferNano.writeFixed32NoTag(java.lang.reflect.Array.getInt(obj, i));
                                i++;
                            }
                            return;
                        case 8:
                            while (i < length) {
                                codedOutputByteBufferNano.writeBoolNoTag(java.lang.reflect.Array.getBoolean(obj, i));
                                i++;
                            }
                            return;
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        default:
                            throw new java.lang.IllegalArgumentException("Unpackable type " + this.type);
                        case 13:
                            while (i < length) {
                                codedOutputByteBufferNano.writeUInt32NoTag(java.lang.reflect.Array.getInt(obj, i));
                                i++;
                            }
                            return;
                        case 14:
                            while (i < length) {
                                codedOutputByteBufferNano.writeEnumNoTag(java.lang.reflect.Array.getInt(obj, i));
                                i++;
                            }
                            return;
                        case 15:
                            while (i < length) {
                                codedOutputByteBufferNano.writeSFixed32NoTag(java.lang.reflect.Array.getInt(obj, i));
                                i++;
                            }
                            return;
                        case 16:
                            while (i < length) {
                                codedOutputByteBufferNano.writeSFixed64NoTag(java.lang.reflect.Array.getLong(obj, i));
                                i++;
                            }
                            return;
                        case 17:
                            while (i < length) {
                                codedOutputByteBufferNano.writeSInt32NoTag(java.lang.reflect.Array.getInt(obj, i));
                                i++;
                            }
                            return;
                        case 18:
                            while (i < length) {
                                codedOutputByteBufferNano.writeSInt64NoTag(java.lang.reflect.Array.getLong(obj, i));
                                i++;
                            }
                            return;
                    }
                } catch (java.io.IOException e) {
                    throw new java.lang.IllegalStateException(e);
                }
            }
            throw new java.lang.IllegalArgumentException("Unexpected repeated extension tag " + this.tag + ", unequal to both non-packed variant " + this.nonPackedTag + " and packed variant " + this.packedTag);
        }

        private int computePackedDataSize(java.lang.Object obj) {
            int length = java.lang.reflect.Array.getLength(obj);
            int i = 0;
            switch (this.type) {
                case 1:
                case 6:
                case 16:
                    return length * 8;
                case 2:
                case 7:
                case 15:
                    return length * 4;
                case 3:
                    int i2 = 0;
                    while (i < length) {
                        i2 += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64SizeNoTag(java.lang.reflect.Array.getLong(obj, i));
                        i++;
                    }
                    return i2;
                case 4:
                    int i3 = 0;
                    while (i < length) {
                        i3 += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeUInt64SizeNoTag(java.lang.reflect.Array.getLong(obj, i));
                        i++;
                    }
                    return i3;
                case 5:
                    int i4 = 0;
                    while (i < length) {
                        i4 += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32SizeNoTag(java.lang.reflect.Array.getInt(obj, i));
                        i++;
                    }
                    return i4;
                case 8:
                    return length;
                case 9:
                case 10:
                case 11:
                case 12:
                default:
                    throw new java.lang.IllegalArgumentException("Unexpected non-packable type " + this.type);
                case 13:
                    int i5 = 0;
                    while (i < length) {
                        i5 += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeUInt32SizeNoTag(java.lang.reflect.Array.getInt(obj, i));
                        i++;
                    }
                    return i5;
                case 14:
                    int i6 = 0;
                    while (i < length) {
                        i6 += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeEnumSizeNoTag(java.lang.reflect.Array.getInt(obj, i));
                        i++;
                    }
                    return i6;
                case 17:
                    int i7 = 0;
                    while (i < length) {
                        i7 += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeSInt32SizeNoTag(java.lang.reflect.Array.getInt(obj, i));
                        i++;
                    }
                    return i7;
                case 18:
                    int i8 = 0;
                    while (i < length) {
                        i8 += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeSInt64SizeNoTag(java.lang.reflect.Array.getLong(obj, i));
                        i++;
                    }
                    return i8;
            }
        }

        @Override // com.android.framework.protobuf.nano.Extension
        protected int computeRepeatedSerializedSize(java.lang.Object obj) {
            if (this.tag == this.nonPackedTag) {
                return super.computeRepeatedSerializedSize(obj);
            }
            if (this.tag == this.packedTag) {
                int computePackedDataSize = computePackedDataSize(obj);
                return computePackedDataSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeRawVarint32Size(computePackedDataSize) + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeRawVarint32Size(this.tag);
            }
            throw new java.lang.IllegalArgumentException("Unexpected repeated extension tag " + this.tag + ", unequal to both non-packed variant " + this.nonPackedTag + " and packed variant " + this.packedTag);
        }

        @Override // com.android.framework.protobuf.nano.Extension
        protected final int computeSingularSerializedSize(java.lang.Object obj) {
            int tagFieldNumber = com.android.framework.protobuf.nano.WireFormatNano.getTagFieldNumber(this.tag);
            switch (this.type) {
                case 1:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeDoubleSize(tagFieldNumber, ((java.lang.Double) obj).doubleValue());
                case 2:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeFloatSize(tagFieldNumber, ((java.lang.Float) obj).floatValue());
                case 3:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(tagFieldNumber, ((java.lang.Long) obj).longValue());
                case 4:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeUInt64Size(tagFieldNumber, ((java.lang.Long) obj).longValue());
                case 5:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(tagFieldNumber, ((java.lang.Integer) obj).intValue());
                case 6:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeFixed64Size(tagFieldNumber, ((java.lang.Long) obj).longValue());
                case 7:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeFixed32Size(tagFieldNumber, ((java.lang.Integer) obj).intValue());
                case 8:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBoolSize(tagFieldNumber, ((java.lang.Boolean) obj).booleanValue());
                case 9:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(tagFieldNumber, (java.lang.String) obj);
                case 10:
                case 11:
                default:
                    throw new java.lang.IllegalArgumentException("Unknown type " + this.type);
                case 12:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBytesSize(tagFieldNumber, (byte[]) obj);
                case 13:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeUInt32Size(tagFieldNumber, ((java.lang.Integer) obj).intValue());
                case 14:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeEnumSize(tagFieldNumber, ((java.lang.Integer) obj).intValue());
                case 15:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeSFixed32Size(tagFieldNumber, ((java.lang.Integer) obj).intValue());
                case 16:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeSFixed64Size(tagFieldNumber, ((java.lang.Long) obj).longValue());
                case 17:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeSInt32Size(tagFieldNumber, ((java.lang.Integer) obj).intValue());
                case 18:
                    return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeSInt64Size(tagFieldNumber, ((java.lang.Long) obj).longValue());
            }
        }
    }
}
