package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class ExtensionSchemaLite extends com.android.framework.protobuf.ExtensionSchema<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> {
    ExtensionSchemaLite() {
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    boolean hasExtensions(com.android.framework.protobuf.MessageLite messageLite) {
        return messageLite instanceof com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage;
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> getExtensions(java.lang.Object obj) {
        return ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) obj).extensions;
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    void setExtensions(java.lang.Object obj, com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> fieldSet) {
        ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) obj).extensions = fieldSet;
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> getMutableExtensions(java.lang.Object obj) {
        return ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) obj).ensureExtensionsAreMutable();
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    void makeImmutable(java.lang.Object obj) {
        getExtensions(obj).makeImmutable();
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    <UT, UB> UB parseExtension(java.lang.Object obj, com.android.framework.protobuf.Reader reader, java.lang.Object obj2, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> fieldSet, UB ub, com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema) throws java.io.IOException {
        java.lang.Object valueOf;
        java.util.ArrayList arrayList;
        com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension generatedExtension = (com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension) obj2;
        int number = generatedExtension.getNumber();
        if (generatedExtension.descriptor.isRepeated() && generatedExtension.descriptor.isPacked()) {
            switch (com.android.framework.protobuf.ExtensionSchemaLite.AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[generatedExtension.getLiteType().ordinal()]) {
                case 1:
                    arrayList = new java.util.ArrayList();
                    reader.readDoubleList(arrayList);
                    break;
                case 2:
                    arrayList = new java.util.ArrayList();
                    reader.readFloatList(arrayList);
                    break;
                case 3:
                    arrayList = new java.util.ArrayList();
                    reader.readInt64List(arrayList);
                    break;
                case 4:
                    arrayList = new java.util.ArrayList();
                    reader.readUInt64List(arrayList);
                    break;
                case 5:
                    arrayList = new java.util.ArrayList();
                    reader.readInt32List(arrayList);
                    break;
                case 6:
                    arrayList = new java.util.ArrayList();
                    reader.readFixed64List(arrayList);
                    break;
                case 7:
                    arrayList = new java.util.ArrayList();
                    reader.readFixed32List(arrayList);
                    break;
                case 8:
                    arrayList = new java.util.ArrayList();
                    reader.readBoolList(arrayList);
                    break;
                case 9:
                    arrayList = new java.util.ArrayList();
                    reader.readUInt32List(arrayList);
                    break;
                case 10:
                    arrayList = new java.util.ArrayList();
                    reader.readSFixed32List(arrayList);
                    break;
                case 11:
                    arrayList = new java.util.ArrayList();
                    reader.readSFixed64List(arrayList);
                    break;
                case 12:
                    arrayList = new java.util.ArrayList();
                    reader.readSInt32List(arrayList);
                    break;
                case 13:
                    arrayList = new java.util.ArrayList();
                    reader.readSInt64List(arrayList);
                    break;
                case 14:
                    arrayList = new java.util.ArrayList();
                    reader.readEnumList(arrayList);
                    ub = (UB) com.android.framework.protobuf.SchemaUtil.filterUnknownEnumList(obj, number, arrayList, generatedExtension.descriptor.getEnumType(), ub, unknownFieldSchema);
                    break;
                default:
                    throw new java.lang.IllegalStateException("Type cannot be packed: " + generatedExtension.descriptor.getLiteType());
            }
            fieldSet.setField(generatedExtension.descriptor, arrayList);
        } else {
            if (generatedExtension.getLiteType() == com.android.framework.protobuf.WireFormat.FieldType.ENUM) {
                int readInt32 = reader.readInt32();
                if (generatedExtension.descriptor.getEnumType().findValueByNumber(readInt32) == null) {
                    return (UB) com.android.framework.protobuf.SchemaUtil.storeUnknownEnum(obj, number, readInt32, ub, unknownFieldSchema);
                }
                valueOf = java.lang.Integer.valueOf(readInt32);
            } else {
                switch (com.android.framework.protobuf.ExtensionSchemaLite.AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[generatedExtension.getLiteType().ordinal()]) {
                    case 1:
                        valueOf = java.lang.Double.valueOf(reader.readDouble());
                        break;
                    case 2:
                        valueOf = java.lang.Float.valueOf(reader.readFloat());
                        break;
                    case 3:
                        valueOf = java.lang.Long.valueOf(reader.readInt64());
                        break;
                    case 4:
                        valueOf = java.lang.Long.valueOf(reader.readUInt64());
                        break;
                    case 5:
                        valueOf = java.lang.Integer.valueOf(reader.readInt32());
                        break;
                    case 6:
                        valueOf = java.lang.Long.valueOf(reader.readFixed64());
                        break;
                    case 7:
                        valueOf = java.lang.Integer.valueOf(reader.readFixed32());
                        break;
                    case 8:
                        valueOf = java.lang.Boolean.valueOf(reader.readBool());
                        break;
                    case 9:
                        valueOf = java.lang.Integer.valueOf(reader.readUInt32());
                        break;
                    case 10:
                        valueOf = java.lang.Integer.valueOf(reader.readSFixed32());
                        break;
                    case 11:
                        valueOf = java.lang.Long.valueOf(reader.readSFixed64());
                        break;
                    case 12:
                        valueOf = java.lang.Integer.valueOf(reader.readSInt32());
                        break;
                    case 13:
                        valueOf = java.lang.Long.valueOf(reader.readSInt64());
                        break;
                    case 14:
                        throw new java.lang.IllegalStateException("Shouldn't reach here.");
                    case 15:
                        valueOf = reader.readBytes();
                        break;
                    case 16:
                        valueOf = reader.readString();
                        break;
                    case 17:
                        if (!generatedExtension.isRepeated()) {
                            java.lang.Object field = fieldSet.getField(generatedExtension.descriptor);
                            if (field instanceof com.android.framework.protobuf.GeneratedMessageLite) {
                                com.android.framework.protobuf.Schema schemaFor = com.android.framework.protobuf.Protobuf.getInstance().schemaFor((com.android.framework.protobuf.Protobuf) field);
                                if (!((com.android.framework.protobuf.GeneratedMessageLite) field).isMutable()) {
                                    java.lang.Object newInstance = schemaFor.newInstance();
                                    schemaFor.mergeFrom(newInstance, field);
                                    fieldSet.setField(generatedExtension.descriptor, newInstance);
                                    field = newInstance;
                                }
                                reader.mergeGroupField(field, schemaFor, extensionRegistryLite);
                                return ub;
                            }
                        }
                        valueOf = reader.readGroup(generatedExtension.getMessageDefaultInstance().getClass(), extensionRegistryLite);
                        break;
                    case 18:
                        if (!generatedExtension.isRepeated()) {
                            java.lang.Object field2 = fieldSet.getField(generatedExtension.descriptor);
                            if (field2 instanceof com.android.framework.protobuf.GeneratedMessageLite) {
                                com.android.framework.protobuf.Schema schemaFor2 = com.android.framework.protobuf.Protobuf.getInstance().schemaFor((com.android.framework.protobuf.Protobuf) field2);
                                if (!((com.android.framework.protobuf.GeneratedMessageLite) field2).isMutable()) {
                                    java.lang.Object newInstance2 = schemaFor2.newInstance();
                                    schemaFor2.mergeFrom(newInstance2, field2);
                                    fieldSet.setField(generatedExtension.descriptor, newInstance2);
                                    field2 = newInstance2;
                                }
                                reader.mergeMessageField(field2, schemaFor2, extensionRegistryLite);
                                return ub;
                            }
                        }
                        valueOf = reader.readMessage(generatedExtension.getMessageDefaultInstance().getClass(), extensionRegistryLite);
                        break;
                    default:
                        valueOf = null;
                        break;
                }
            }
            if (generatedExtension.isRepeated()) {
                fieldSet.addRepeatedField(generatedExtension.descriptor, valueOf);
            } else {
                switch (com.android.framework.protobuf.ExtensionSchemaLite.AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[generatedExtension.getLiteType().ordinal()]) {
                    case 17:
                    case 18:
                        java.lang.Object field3 = fieldSet.getField(generatedExtension.descriptor);
                        if (field3 != null) {
                            valueOf = com.android.framework.protobuf.Internal.mergeMessage(field3, valueOf);
                            break;
                        }
                        break;
                }
                fieldSet.setField(generatedExtension.descriptor, valueOf);
            }
        }
        return ub;
    }

    /* renamed from: com.android.framework.protobuf.ExtensionSchemaLite$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$FieldType = new int[com.android.framework.protobuf.WireFormat.FieldType.values().length];

        static {
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.DOUBLE.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.FLOAT.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.INT64.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.UINT64.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.INT32.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.FIXED64.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.FIXED32.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.BOOL.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.UINT32.ordinal()] = 9;
            } catch (java.lang.NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SFIXED32.ordinal()] = 10;
            } catch (java.lang.NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SFIXED64.ordinal()] = 11;
            } catch (java.lang.NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SINT32.ordinal()] = 12;
            } catch (java.lang.NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SINT64.ordinal()] = 13;
            } catch (java.lang.NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.ENUM.ordinal()] = 14;
            } catch (java.lang.NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.BYTES.ordinal()] = 15;
            } catch (java.lang.NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.STRING.ordinal()] = 16;
            } catch (java.lang.NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.GROUP.ordinal()] = 17;
            } catch (java.lang.NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.MESSAGE.ordinal()] = 18;
            } catch (java.lang.NoSuchFieldError e18) {
            }
        }
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    int extensionNumber(java.util.Map.Entry<?, ?> entry) {
        return ((com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor) entry.getKey()).getNumber();
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    void serializeExtension(com.android.framework.protobuf.Writer writer, java.util.Map.Entry<?, ?> entry) throws java.io.IOException {
        com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
        if (extensionDescriptor.isRepeated()) {
            switch (com.android.framework.protobuf.ExtensionSchemaLite.AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.getLiteType().ordinal()]) {
                case 1:
                    com.android.framework.protobuf.SchemaUtil.writeDoubleList(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 2:
                    com.android.framework.protobuf.SchemaUtil.writeFloatList(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 3:
                    com.android.framework.protobuf.SchemaUtil.writeInt64List(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 4:
                    com.android.framework.protobuf.SchemaUtil.writeUInt64List(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 5:
                    com.android.framework.protobuf.SchemaUtil.writeInt32List(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 6:
                    com.android.framework.protobuf.SchemaUtil.writeFixed64List(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 7:
                    com.android.framework.protobuf.SchemaUtil.writeFixed32List(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 8:
                    com.android.framework.protobuf.SchemaUtil.writeBoolList(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 9:
                    com.android.framework.protobuf.SchemaUtil.writeUInt32List(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 10:
                    com.android.framework.protobuf.SchemaUtil.writeSFixed32List(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 11:
                    com.android.framework.protobuf.SchemaUtil.writeSFixed64List(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 12:
                    com.android.framework.protobuf.SchemaUtil.writeSInt32List(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 13:
                    com.android.framework.protobuf.SchemaUtil.writeSInt64List(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 14:
                    com.android.framework.protobuf.SchemaUtil.writeInt32List(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, extensionDescriptor.isPacked());
                    break;
                case 15:
                    com.android.framework.protobuf.SchemaUtil.writeBytesList(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer);
                    break;
                case 16:
                    com.android.framework.protobuf.SchemaUtil.writeStringList(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer);
                    break;
                case 17:
                    java.util.List list = (java.util.List) entry.getValue();
                    if (list != null && !list.isEmpty()) {
                        com.android.framework.protobuf.SchemaUtil.writeGroupList(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) list.get(0).getClass()));
                        break;
                    }
                    break;
                case 18:
                    java.util.List list2 = (java.util.List) entry.getValue();
                    if (list2 != null && !list2.isEmpty()) {
                        com.android.framework.protobuf.SchemaUtil.writeMessageList(extensionDescriptor.getNumber(), (java.util.List) entry.getValue(), writer, com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) list2.get(0).getClass()));
                        break;
                    }
                    break;
            }
        }
        switch (com.android.framework.protobuf.ExtensionSchemaLite.AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[extensionDescriptor.getLiteType().ordinal()]) {
            case 1:
                writer.writeDouble(extensionDescriptor.getNumber(), ((java.lang.Double) entry.getValue()).doubleValue());
                break;
            case 2:
                writer.writeFloat(extensionDescriptor.getNumber(), ((java.lang.Float) entry.getValue()).floatValue());
                break;
            case 3:
                writer.writeInt64(extensionDescriptor.getNumber(), ((java.lang.Long) entry.getValue()).longValue());
                break;
            case 4:
                writer.writeUInt64(extensionDescriptor.getNumber(), ((java.lang.Long) entry.getValue()).longValue());
                break;
            case 5:
                writer.writeInt32(extensionDescriptor.getNumber(), ((java.lang.Integer) entry.getValue()).intValue());
                break;
            case 6:
                writer.writeFixed64(extensionDescriptor.getNumber(), ((java.lang.Long) entry.getValue()).longValue());
                break;
            case 7:
                writer.writeFixed32(extensionDescriptor.getNumber(), ((java.lang.Integer) entry.getValue()).intValue());
                break;
            case 8:
                writer.writeBool(extensionDescriptor.getNumber(), ((java.lang.Boolean) entry.getValue()).booleanValue());
                break;
            case 9:
                writer.writeUInt32(extensionDescriptor.getNumber(), ((java.lang.Integer) entry.getValue()).intValue());
                break;
            case 10:
                writer.writeSFixed32(extensionDescriptor.getNumber(), ((java.lang.Integer) entry.getValue()).intValue());
                break;
            case 11:
                writer.writeSFixed64(extensionDescriptor.getNumber(), ((java.lang.Long) entry.getValue()).longValue());
                break;
            case 12:
                writer.writeSInt32(extensionDescriptor.getNumber(), ((java.lang.Integer) entry.getValue()).intValue());
                break;
            case 13:
                writer.writeSInt64(extensionDescriptor.getNumber(), ((java.lang.Long) entry.getValue()).longValue());
                break;
            case 14:
                writer.writeInt32(extensionDescriptor.getNumber(), ((java.lang.Integer) entry.getValue()).intValue());
                break;
            case 15:
                writer.writeBytes(extensionDescriptor.getNumber(), (com.android.framework.protobuf.ByteString) entry.getValue());
                break;
            case 16:
                writer.writeString(extensionDescriptor.getNumber(), (java.lang.String) entry.getValue());
                break;
            case 17:
                writer.writeGroup(extensionDescriptor.getNumber(), entry.getValue(), com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) entry.getValue().getClass()));
                break;
            case 18:
                writer.writeMessage(extensionDescriptor.getNumber(), entry.getValue(), com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) entry.getValue().getClass()));
                break;
        }
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    java.lang.Object findExtensionByNumber(com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.MessageLite messageLite, int i) {
        return extensionRegistryLite.findLiteExtensionByNumber(messageLite, i);
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    void parseLengthPrefixedMessageSetItem(com.android.framework.protobuf.Reader reader, java.lang.Object obj, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> fieldSet) throws java.io.IOException {
        com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension generatedExtension = (com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension) obj;
        fieldSet.setField(generatedExtension.descriptor, reader.readMessage(generatedExtension.getMessageDefaultInstance().getClass(), extensionRegistryLite));
    }

    @Override // com.android.framework.protobuf.ExtensionSchema
    void parseMessageSetItem(com.android.framework.protobuf.ByteString byteString, java.lang.Object obj, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> fieldSet) throws java.io.IOException {
        com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension generatedExtension = (com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension) obj;
        com.android.framework.protobuf.MessageLite.Builder newBuilderForType = generatedExtension.getMessageDefaultInstance().newBuilderForType();
        com.android.framework.protobuf.CodedInputStream newCodedInput = byteString.newCodedInput();
        newBuilderForType.mergeFrom(newCodedInput, extensionRegistryLite);
        fieldSet.setField(generatedExtension.descriptor, newBuilderForType.buildPartial());
        newCodedInput.checkLastTagWas(0);
    }
}
