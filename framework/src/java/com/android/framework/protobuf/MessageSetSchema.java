package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class MessageSetSchema<T> implements com.android.framework.protobuf.Schema<T> {
    private final com.android.framework.protobuf.MessageLite defaultInstance;
    private final com.android.framework.protobuf.ExtensionSchema<?> extensionSchema;
    private final boolean hasExtensions;
    private final com.android.framework.protobuf.UnknownFieldSchema<?, ?> unknownFieldSchema;

    private MessageSetSchema(com.android.framework.protobuf.UnknownFieldSchema<?, ?> unknownFieldSchema, com.android.framework.protobuf.ExtensionSchema<?> extensionSchema, com.android.framework.protobuf.MessageLite messageLite) {
        this.unknownFieldSchema = unknownFieldSchema;
        this.hasExtensions = extensionSchema.hasExtensions(messageLite);
        this.extensionSchema = extensionSchema;
        this.defaultInstance = messageLite;
    }

    static <T> com.android.framework.protobuf.MessageSetSchema<T> newSchema(com.android.framework.protobuf.UnknownFieldSchema<?, ?> unknownFieldSchema, com.android.framework.protobuf.ExtensionSchema<?> extensionSchema, com.android.framework.protobuf.MessageLite messageLite) {
        return new com.android.framework.protobuf.MessageSetSchema<>(unknownFieldSchema, extensionSchema, messageLite);
    }

    @Override // com.android.framework.protobuf.Schema
    public T newInstance() {
        if (this.defaultInstance instanceof com.android.framework.protobuf.GeneratedMessageLite) {
            return (T) ((com.android.framework.protobuf.GeneratedMessageLite) this.defaultInstance).newMutableInstance();
        }
        return (T) this.defaultInstance.newBuilderForType().buildPartial();
    }

    @Override // com.android.framework.protobuf.Schema
    public boolean equals(T t, T t2) {
        if (!this.unknownFieldSchema.getFromMessage(t).equals(this.unknownFieldSchema.getFromMessage(t2))) {
            return false;
        }
        if (this.hasExtensions) {
            return this.extensionSchema.getExtensions(t).equals(this.extensionSchema.getExtensions(t2));
        }
        return true;
    }

    @Override // com.android.framework.protobuf.Schema
    public int hashCode(T t) {
        int hashCode = this.unknownFieldSchema.getFromMessage(t).hashCode();
        if (this.hasExtensions) {
            return (hashCode * 53) + this.extensionSchema.getExtensions(t).hashCode();
        }
        return hashCode;
    }

    @Override // com.android.framework.protobuf.Schema
    public void mergeFrom(T t, T t2) {
        com.android.framework.protobuf.SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, t, t2);
        if (this.hasExtensions) {
            com.android.framework.protobuf.SchemaUtil.mergeExtensions(this.extensionSchema, t, t2);
        }
    }

    @Override // com.android.framework.protobuf.Schema
    public void writeTo(T t, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        java.util.Iterator<java.util.Map.Entry<?, java.lang.Object>> it = this.extensionSchema.getExtensions(t).iterator();
        while (it.hasNext()) {
            java.util.Map.Entry<?, java.lang.Object> next = it.next();
            com.android.framework.protobuf.FieldSet.FieldDescriptorLite fieldDescriptorLite = (com.android.framework.protobuf.FieldSet.FieldDescriptorLite) next.getKey();
            if (fieldDescriptorLite.getLiteJavaType() != com.android.framework.protobuf.WireFormat.JavaType.MESSAGE || fieldDescriptorLite.isRepeated() || fieldDescriptorLite.isPacked()) {
                throw new java.lang.IllegalStateException("Found invalid MessageSet item.");
            }
            if (next instanceof com.android.framework.protobuf.LazyField.LazyEntry) {
                writer.writeMessageSetItem(fieldDescriptorLite.getNumber(), ((com.android.framework.protobuf.LazyField.LazyEntry) next).getField().toByteString());
            } else {
                writer.writeMessageSetItem(fieldDescriptorLite.getNumber(), next.getValue());
            }
        }
        writeUnknownFieldsHelper(this.unknownFieldSchema, t, writer);
    }

    private <UT, UB> void writeUnknownFieldsHelper(com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema, T t, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        unknownFieldSchema.writeAsMessageSetTo(unknownFieldSchema.getFromMessage(t), writer);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:12:0x0080. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00cc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00cf  */
    @Override // com.android.framework.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void mergeFrom(T t, byte[] bArr, int i, int i2, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        com.android.framework.protobuf.GeneratedMessageLite generatedMessageLite = (com.android.framework.protobuf.GeneratedMessageLite) t;
        com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite = generatedMessageLite.unknownFields;
        if (unknownFieldSetLite == com.android.framework.protobuf.UnknownFieldSetLite.getDefaultInstance()) {
            unknownFieldSetLite = com.android.framework.protobuf.UnknownFieldSetLite.newInstance();
            generatedMessageLite.unknownFields = unknownFieldSetLite;
        }
        com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> ensureExtensionsAreMutable = ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) t).ensureExtensionsAreMutable();
        com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension generatedExtension = null;
        while (i < i2) {
            int decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr, i, registers);
            int i3 = registers.int1;
            if (i3 != com.android.framework.protobuf.WireFormat.MESSAGE_SET_ITEM_TAG) {
                if (com.android.framework.protobuf.WireFormat.getTagWireType(i3) == 2) {
                    com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension generatedExtension2 = (com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension) this.extensionSchema.findExtensionByNumber(registers.extensionRegistry, this.defaultInstance, com.android.framework.protobuf.WireFormat.getTagFieldNumber(i3));
                    if (generatedExtension2 != null) {
                        i = com.android.framework.protobuf.ArrayDecoders.decodeMessageField(com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) generatedExtension2.getMessageDefaultInstance().getClass()), bArr, decodeVarint32, i2, registers);
                        ensureExtensionsAreMutable.setField(generatedExtension2.descriptor, registers.object1);
                        generatedExtension = generatedExtension2;
                    } else {
                        i = com.android.framework.protobuf.ArrayDecoders.decodeUnknownField(i3, bArr, decodeVarint32, i2, unknownFieldSetLite, registers);
                        generatedExtension = generatedExtension2;
                    }
                } else {
                    i = com.android.framework.protobuf.ArrayDecoders.skipField(i3, bArr, decodeVarint32, i2, registers);
                }
            } else {
                int i4 = 0;
                com.android.framework.protobuf.ByteString byteString = null;
                while (decodeVarint32 < i2) {
                    decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr, decodeVarint32, registers);
                    int i5 = registers.int1;
                    int tagFieldNumber = com.android.framework.protobuf.WireFormat.getTagFieldNumber(i5);
                    int tagWireType = com.android.framework.protobuf.WireFormat.getTagWireType(i5);
                    switch (tagFieldNumber) {
                        case 2:
                            if (tagWireType == 0) {
                                decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr, decodeVarint32, registers);
                                i4 = registers.int1;
                                generatedExtension = (com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension) this.extensionSchema.findExtensionByNumber(registers.extensionRegistry, this.defaultInstance, i4);
                            } else if (i5 == com.android.framework.protobuf.WireFormat.MESSAGE_SET_ITEM_END_TAG) {
                                break;
                            } else {
                                decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.skipField(i5, bArr, decodeVarint32, i2, registers);
                            }
                        case 3:
                            if (generatedExtension != null) {
                                decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.decodeMessageField(com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) generatedExtension.getMessageDefaultInstance().getClass()), bArr, decodeVarint32, i2, registers);
                                ensureExtensionsAreMutable.setField(generatedExtension.descriptor, registers.object1);
                            } else if (tagWireType == 2) {
                                decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.decodeBytes(bArr, decodeVarint32, registers);
                                byteString = (com.android.framework.protobuf.ByteString) registers.object1;
                            } else if (i5 == com.android.framework.protobuf.WireFormat.MESSAGE_SET_ITEM_END_TAG) {
                            }
                            break;
                        default:
                            if (i5 == com.android.framework.protobuf.WireFormat.MESSAGE_SET_ITEM_END_TAG) {
                            }
                            break;
                    }
                    if (byteString != null) {
                        unknownFieldSetLite.storeField(com.android.framework.protobuf.WireFormat.makeTag(i4, 2), byteString);
                    }
                    i = decodeVarint32;
                }
                if (byteString != null) {
                }
                i = decodeVarint32;
            }
        }
        if (i != i2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
        }
    }

    @Override // com.android.framework.protobuf.Schema
    public void mergeFrom(T t, com.android.framework.protobuf.Reader reader, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, t, reader, extensionRegistryLite);
    }

    private <UT, UB, ET extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema, com.android.framework.protobuf.ExtensionSchema<ET> extensionSchema, T t, com.android.framework.protobuf.Reader reader, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        UB builderFromMessage = unknownFieldSchema.getBuilderFromMessage(t);
        com.android.framework.protobuf.FieldSet<ET> mutableExtensions = extensionSchema.getMutableExtensions(t);
        do {
            try {
                if (reader.getFieldNumber() == Integer.MAX_VALUE) {
                    return;
                }
            } finally {
                unknownFieldSchema.setBuilderToMessage(t, builderFromMessage);
            }
        } while (parseMessageSetItemOrUnknownField(reader, extensionRegistryLite, extensionSchema, mutableExtensions, unknownFieldSchema, builderFromMessage));
    }

    @Override // com.android.framework.protobuf.Schema
    public void makeImmutable(T t) {
        this.unknownFieldSchema.makeImmutable(t);
        this.extensionSchema.makeImmutable(t);
    }

    private <UT, UB, ET extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<ET>> boolean parseMessageSetItemOrUnknownField(com.android.framework.protobuf.Reader reader, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.ExtensionSchema<ET> extensionSchema, com.android.framework.protobuf.FieldSet<ET> fieldSet, com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema, UB ub) throws java.io.IOException {
        int tag = reader.getTag();
        if (tag != com.android.framework.protobuf.WireFormat.MESSAGE_SET_ITEM_TAG) {
            if (com.android.framework.protobuf.WireFormat.getTagWireType(tag) == 2) {
                java.lang.Object findExtensionByNumber = extensionSchema.findExtensionByNumber(extensionRegistryLite, this.defaultInstance, com.android.framework.protobuf.WireFormat.getTagFieldNumber(tag));
                if (findExtensionByNumber != null) {
                    extensionSchema.parseLengthPrefixedMessageSetItem(reader, findExtensionByNumber, extensionRegistryLite, fieldSet);
                    return true;
                }
                return unknownFieldSchema.mergeOneFieldFrom(ub, reader);
            }
            return reader.skipField();
        }
        java.lang.Object obj = null;
        int i = 0;
        com.android.framework.protobuf.ByteString byteString = null;
        while (reader.getFieldNumber() != Integer.MAX_VALUE) {
            int tag2 = reader.getTag();
            if (tag2 == com.android.framework.protobuf.WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                i = reader.readUInt32();
                obj = extensionSchema.findExtensionByNumber(extensionRegistryLite, this.defaultInstance, i);
            } else if (tag2 == com.android.framework.protobuf.WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                if (obj != null) {
                    extensionSchema.parseLengthPrefixedMessageSetItem(reader, obj, extensionRegistryLite, fieldSet);
                } else {
                    byteString = reader.readBytes();
                }
            } else if (!reader.skipField()) {
                break;
            }
        }
        if (reader.getTag() != com.android.framework.protobuf.WireFormat.MESSAGE_SET_ITEM_END_TAG) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidEndTag();
        }
        if (byteString != null) {
            if (obj != null) {
                extensionSchema.parseMessageSetItem(byteString, obj, extensionRegistryLite, fieldSet);
            } else {
                unknownFieldSchema.addLengthDelimited(ub, i, byteString);
            }
        }
        return true;
    }

    @Override // com.android.framework.protobuf.Schema
    public final boolean isInitialized(T t) {
        return this.extensionSchema.getExtensions(t).isInitialized();
    }

    @Override // com.android.framework.protobuf.Schema
    public int getSerializedSize(T t) {
        int unknownFieldsSerializedSize = getUnknownFieldsSerializedSize(this.unknownFieldSchema, t) + 0;
        if (this.hasExtensions) {
            return unknownFieldsSerializedSize + this.extensionSchema.getExtensions(t).getMessageSetSerializedSize();
        }
        return unknownFieldsSerializedSize;
    }

    private <UT, UB> int getUnknownFieldsSerializedSize(com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema, T t) {
        return unknownFieldSchema.getSerializedSizeAsMessageSet(unknownFieldSchema.getFromMessage(t));
    }
}
