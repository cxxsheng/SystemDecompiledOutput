package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public abstract class GeneratedMessageLite<MessageType extends com.android.framework.protobuf.GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends com.android.framework.protobuf.GeneratedMessageLite.Builder<MessageType, BuilderType>> extends com.android.framework.protobuf.AbstractMessageLite<MessageType, BuilderType> {
    private static final int MEMOIZED_SERIALIZED_SIZE_MASK = Integer.MAX_VALUE;
    private static final int MUTABLE_FLAG_MASK = Integer.MIN_VALUE;
    static final int UNINITIALIZED_HASH_CODE = 0;
    static final int UNINITIALIZED_SERIALIZED_SIZE = Integer.MAX_VALUE;
    private static java.util.Map<java.lang.Object, com.android.framework.protobuf.GeneratedMessageLite<?, ?>> defaultInstanceMap = new java.util.concurrent.ConcurrentHashMap();
    private int memoizedSerializedSize = -1;
    protected com.android.framework.protobuf.UnknownFieldSetLite unknownFields = com.android.framework.protobuf.UnknownFieldSetLite.getDefaultInstance();

    public interface ExtendableMessageOrBuilder<MessageType extends com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage<MessageType, BuilderType>, BuilderType extends com.android.framework.protobuf.GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>> extends com.android.framework.protobuf.MessageLiteOrBuilder {
        <Type> Type getExtension(com.android.framework.protobuf.ExtensionLite<MessageType, Type> extensionLite);

        <Type> Type getExtension(com.android.framework.protobuf.ExtensionLite<MessageType, java.util.List<Type>> extensionLite, int i);

        <Type> int getExtensionCount(com.android.framework.protobuf.ExtensionLite<MessageType, java.util.List<Type>> extensionLite);

        <Type> boolean hasExtension(com.android.framework.protobuf.ExtensionLite<MessageType, Type> extensionLite);
    }

    public enum MethodToInvoke {
        GET_MEMOIZED_IS_INITIALIZED,
        SET_MEMOIZED_IS_INITIALIZED,
        BUILD_MESSAGE_INFO,
        NEW_MUTABLE_INSTANCE,
        NEW_BUILDER,
        GET_DEFAULT_INSTANCE,
        GET_PARSER
    }

    protected abstract java.lang.Object dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke methodToInvoke, java.lang.Object obj, java.lang.Object obj2);

    boolean isMutable() {
        return (this.memoizedSerializedSize & Integer.MIN_VALUE) != 0;
    }

    void markImmutable() {
        this.memoizedSerializedSize &= Integer.MAX_VALUE;
    }

    int getMemoizedHashCode() {
        return this.memoizedHashCode;
    }

    void setMemoizedHashCode(int i) {
        this.memoizedHashCode = i;
    }

    void clearMemoizedHashCode() {
        this.memoizedHashCode = 0;
    }

    boolean hashCodeIsNotMemoized() {
        return getMemoizedHashCode() == 0;
    }

    @Override // com.android.framework.protobuf.MessageLite
    public final com.android.framework.protobuf.Parser<MessageType> getParserForType() {
        return (com.android.framework.protobuf.Parser) dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke.GET_PARSER);
    }

    @Override // com.android.framework.protobuf.MessageLiteOrBuilder
    public final MessageType getDefaultInstanceForType() {
        return (MessageType) dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE);
    }

    @Override // com.android.framework.protobuf.MessageLite
    public final BuilderType newBuilderForType() {
        return (BuilderType) dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_BUILDER);
    }

    MessageType newMutableInstance() {
        return (MessageType) dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE);
    }

    public java.lang.String toString() {
        return com.android.framework.protobuf.MessageLiteToString.toString(this, super.toString());
    }

    public int hashCode() {
        if (isMutable()) {
            return computeHashCode();
        }
        if (hashCodeIsNotMemoized()) {
            setMemoizedHashCode(computeHashCode());
        }
        return getMemoizedHashCode();
    }

    int computeHashCode() {
        return com.android.framework.protobuf.Protobuf.getInstance().schemaFor((com.android.framework.protobuf.Protobuf) this).hashCode(this);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return com.android.framework.protobuf.Protobuf.getInstance().schemaFor((com.android.framework.protobuf.Protobuf) this).equals(this, (com.android.framework.protobuf.GeneratedMessageLite) obj);
    }

    private final void ensureUnknownFieldsInitialized() {
        if (this.unknownFields == com.android.framework.protobuf.UnknownFieldSetLite.getDefaultInstance()) {
            this.unknownFields = com.android.framework.protobuf.UnknownFieldSetLite.newInstance();
        }
    }

    protected boolean parseUnknownField(int i, com.android.framework.protobuf.CodedInputStream codedInputStream) throws java.io.IOException {
        if (com.android.framework.protobuf.WireFormat.getTagWireType(i) == 4) {
            return false;
        }
        ensureUnknownFieldsInitialized();
        return this.unknownFields.mergeFieldFrom(i, codedInputStream);
    }

    protected void mergeVarintField(int i, int i2) {
        ensureUnknownFieldsInitialized();
        this.unknownFields.mergeVarintField(i, i2);
    }

    protected void mergeLengthDelimitedField(int i, com.android.framework.protobuf.ByteString byteString) {
        ensureUnknownFieldsInitialized();
        this.unknownFields.mergeLengthDelimitedField(i, byteString);
    }

    protected void makeImmutable() {
        com.android.framework.protobuf.Protobuf.getInstance().schemaFor((com.android.framework.protobuf.Protobuf) this).makeImmutable(this);
        markImmutable();
    }

    protected final <MessageType extends com.android.framework.protobuf.GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends com.android.framework.protobuf.GeneratedMessageLite.Builder<MessageType, BuilderType>> BuilderType createBuilder() {
        return (BuilderType) dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_BUILDER);
    }

    protected final <MessageType extends com.android.framework.protobuf.GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends com.android.framework.protobuf.GeneratedMessageLite.Builder<MessageType, BuilderType>> BuilderType createBuilder(MessageType messagetype) {
        return (BuilderType) createBuilder().mergeFrom(messagetype);
    }

    @Override // com.android.framework.protobuf.MessageLiteOrBuilder
    public final boolean isInitialized() {
        return isInitialized(this, java.lang.Boolean.TRUE.booleanValue());
    }

    @Override // com.android.framework.protobuf.MessageLite
    public final BuilderType toBuilder() {
        return (BuilderType) ((com.android.framework.protobuf.GeneratedMessageLite.Builder) dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_BUILDER)).mergeFrom((com.android.framework.protobuf.GeneratedMessageLite.Builder) this);
    }

    protected java.lang.Object dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke methodToInvoke, java.lang.Object obj) {
        return dynamicMethod(methodToInvoke, obj, null);
    }

    protected java.lang.Object dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        return dynamicMethod(methodToInvoke, null, null);
    }

    void clearMemoizedSerializedSize() {
        setMemoizedSerializedSize(Integer.MAX_VALUE);
    }

    @Override // com.android.framework.protobuf.AbstractMessageLite
    int getMemoizedSerializedSize() {
        return this.memoizedSerializedSize & Integer.MAX_VALUE;
    }

    @Override // com.android.framework.protobuf.AbstractMessageLite
    void setMemoizedSerializedSize(int i) {
        if (i < 0) {
            throw new java.lang.IllegalStateException("serialized size must be non-negative, was " + i);
        }
        this.memoizedSerializedSize = (i & Integer.MAX_VALUE) | (this.memoizedSerializedSize & Integer.MIN_VALUE);
    }

    @Override // com.android.framework.protobuf.MessageLite
    public void writeTo(com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
        com.android.framework.protobuf.Protobuf.getInstance().schemaFor((com.android.framework.protobuf.Protobuf) this).writeTo(this, com.android.framework.protobuf.CodedOutputStreamWriter.forCodedOutput(codedOutputStream));
    }

    @Override // com.android.framework.protobuf.AbstractMessageLite
    int getSerializedSize(com.android.framework.protobuf.Schema schema) {
        if (isMutable()) {
            int computeSerializedSize = computeSerializedSize(schema);
            if (computeSerializedSize < 0) {
                throw new java.lang.IllegalStateException("serialized size must be non-negative, was " + computeSerializedSize);
            }
            return computeSerializedSize;
        }
        if (getMemoizedSerializedSize() != Integer.MAX_VALUE) {
            return getMemoizedSerializedSize();
        }
        int computeSerializedSize2 = computeSerializedSize(schema);
        setMemoizedSerializedSize(computeSerializedSize2);
        return computeSerializedSize2;
    }

    @Override // com.android.framework.protobuf.MessageLite
    public int getSerializedSize() {
        return getSerializedSize(null);
    }

    private int computeSerializedSize(com.android.framework.protobuf.Schema<?> schema) {
        if (schema == null) {
            return com.android.framework.protobuf.Protobuf.getInstance().schemaFor((com.android.framework.protobuf.Protobuf) this).getSerializedSize(this);
        }
        return schema.getSerializedSize(this);
    }

    java.lang.Object buildMessageInfo() throws java.lang.Exception {
        return dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO);
    }

    static <T extends com.android.framework.protobuf.GeneratedMessageLite<?, ?>> T getDefaultInstance(java.lang.Class<T> cls) {
        com.android.framework.protobuf.GeneratedMessageLite<?, ?> generatedMessageLite = defaultInstanceMap.get(cls);
        if (generatedMessageLite == null) {
            try {
                java.lang.Class.forName(cls.getName(), true, cls.getClassLoader());
                generatedMessageLite = defaultInstanceMap.get(cls);
            } catch (java.lang.ClassNotFoundException e) {
                throw new java.lang.IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (generatedMessageLite == null) {
            generatedMessageLite = (T) ((com.android.framework.protobuf.GeneratedMessageLite) com.android.framework.protobuf.UnsafeUtil.allocateInstance(cls)).getDefaultInstanceForType();
            if (generatedMessageLite == null) {
                throw new java.lang.IllegalStateException();
            }
            defaultInstanceMap.put(cls, generatedMessageLite);
        }
        return (T) generatedMessageLite;
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<?, ?>> void registerDefaultInstance(java.lang.Class<T> cls, T t) {
        t.markImmutable();
        defaultInstanceMap.put(cls, t);
    }

    protected static java.lang.Object newMessageInfo(com.android.framework.protobuf.MessageLite messageLite, java.lang.String str, java.lang.Object[] objArr) {
        return new com.android.framework.protobuf.RawMessageInfo(messageLite, str, objArr);
    }

    protected final void mergeUnknownFields(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite) {
        this.unknownFields = com.android.framework.protobuf.UnknownFieldSetLite.mutableCopyOf(this.unknownFields, unknownFieldSetLite);
    }

    public static abstract class Builder<MessageType extends com.android.framework.protobuf.GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends com.android.framework.protobuf.GeneratedMessageLite.Builder<MessageType, BuilderType>> extends com.android.framework.protobuf.AbstractMessageLite.Builder<MessageType, BuilderType> {
        private final MessageType defaultInstance;
        protected MessageType instance;

        protected Builder(MessageType messagetype) {
            this.defaultInstance = messagetype;
            if (messagetype.isMutable()) {
                throw new java.lang.IllegalArgumentException("Default instance must be immutable.");
            }
            this.instance = newMutableInstance();
        }

        private MessageType newMutableInstance() {
            return (MessageType) this.defaultInstance.newMutableInstance();
        }

        protected final void copyOnWrite() {
            if (!this.instance.isMutable()) {
                copyOnWriteInternal();
            }
        }

        protected void copyOnWriteInternal() {
            MessageType newMutableInstance = newMutableInstance();
            mergeFromInstance(newMutableInstance, this.instance);
            this.instance = newMutableInstance;
        }

        @Override // com.android.framework.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return com.android.framework.protobuf.GeneratedMessageLite.isInitialized(this.instance, false);
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public final BuilderType clear() {
            if (this.defaultInstance.isMutable()) {
                throw new java.lang.IllegalArgumentException("Default instance must be immutable.");
            }
            this.instance = newMutableInstance();
            return this;
        }

        @Override // com.android.framework.protobuf.AbstractMessageLite.Builder
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public BuilderType mo6591clone() {
            BuilderType buildertype = (BuilderType) getDefaultInstanceForType().newBuilderForType();
            buildertype.instance = buildPartial();
            return buildertype;
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public MessageType buildPartial() {
            if (!this.instance.isMutable()) {
                return this.instance;
            }
            this.instance.makeImmutable();
            return this.instance;
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public final MessageType build() {
            MessageType buildPartial = buildPartial();
            if (!buildPartial.isInitialized()) {
                throw newUninitializedMessageException(buildPartial);
            }
            return buildPartial;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.framework.protobuf.AbstractMessageLite.Builder
        public BuilderType internalMergeFrom(MessageType messagetype) {
            return mergeFrom((com.android.framework.protobuf.GeneratedMessageLite.Builder<MessageType, BuilderType>) messagetype);
        }

        public BuilderType mergeFrom(MessageType messagetype) {
            if (getDefaultInstanceForType().equals(messagetype)) {
                return this;
            }
            copyOnWrite();
            mergeFromInstance(this.instance, messagetype);
            return this;
        }

        private static <MessageType> void mergeFromInstance(MessageType messagetype, MessageType messagetype2) {
            com.android.framework.protobuf.Protobuf.getInstance().schemaFor((com.android.framework.protobuf.Protobuf) messagetype).mergeFrom(messagetype, messagetype2);
        }

        @Override // com.android.framework.protobuf.MessageLiteOrBuilder
        public MessageType getDefaultInstanceForType() {
            return this.defaultInstance;
        }

        @Override // com.android.framework.protobuf.AbstractMessageLite.Builder, com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(byte[] bArr, int i, int i2, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            copyOnWrite();
            try {
                com.android.framework.protobuf.Protobuf.getInstance().schemaFor((com.android.framework.protobuf.Protobuf) this.instance).mergeFrom(this.instance, bArr, i, i + i2, new com.android.framework.protobuf.ArrayDecoders.Registers(extensionRegistryLite));
                return this;
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                throw e;
            } catch (java.io.IOException e2) {
                throw new java.lang.RuntimeException("Reading from byte array should not throw IOException.", e2);
            } catch (java.lang.IndexOutOfBoundsException e3) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
            }
        }

        @Override // com.android.framework.protobuf.AbstractMessageLite.Builder, com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(byte[] bArr, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return mergeFrom(bArr, i, i2, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // com.android.framework.protobuf.AbstractMessageLite.Builder, com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            copyOnWrite();
            try {
                com.android.framework.protobuf.Protobuf.getInstance().schemaFor((com.android.framework.protobuf.Protobuf) this.instance).mergeFrom(this.instance, com.android.framework.protobuf.CodedInputStreamReader.forCodedInput(codedInputStream), extensionRegistryLite);
                return this;
            } catch (java.lang.RuntimeException e) {
                if (e.getCause() instanceof java.io.IOException) {
                    throw ((java.io.IOException) e.getCause());
                }
                throw e;
            }
        }
    }

    public static abstract class ExtendableMessage<MessageType extends com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage<MessageType, BuilderType>, BuilderType extends com.android.framework.protobuf.GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>> extends com.android.framework.protobuf.GeneratedMessageLite<MessageType, BuilderType> implements com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder<MessageType, BuilderType> {
        protected com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> extensions = com.android.framework.protobuf.FieldSet.emptySet();

        protected final void mergeExtensionFields(MessageType messagetype) {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.m6592clone();
            }
            this.extensions.mergeFrom(messagetype.extensions);
        }

        protected <MessageType extends com.android.framework.protobuf.MessageLite> boolean parseUnknownField(MessageType messagetype, com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, int i) throws java.io.IOException {
            int tagFieldNumber = com.android.framework.protobuf.WireFormat.getTagFieldNumber(i);
            return parseExtension(codedInputStream, extensionRegistryLite, extensionRegistryLite.findLiteExtensionByNumber(messagetype, tagFieldNumber), i, tagFieldNumber);
        }

        private boolean parseExtension(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<?, ?> generatedExtension, int i, int i2) throws java.io.IOException {
            boolean z;
            boolean z2;
            com.android.framework.protobuf.MessageLite.Builder builder;
            java.lang.Object build;
            com.android.framework.protobuf.MessageLite messageLite;
            int tagWireType = com.android.framework.protobuf.WireFormat.getTagWireType(i);
            if (generatedExtension == null) {
                z = true;
                z2 = false;
            } else if (tagWireType == com.android.framework.protobuf.FieldSet.getWireFormatForFieldType(generatedExtension.descriptor.getLiteType(), false)) {
                z = false;
                z2 = false;
            } else if (generatedExtension.descriptor.isRepeated && generatedExtension.descriptor.type.isPackable() && tagWireType == com.android.framework.protobuf.FieldSet.getWireFormatForFieldType(generatedExtension.descriptor.getLiteType(), true)) {
                z2 = true;
                z = false;
            } else {
                z = true;
                z2 = false;
            }
            if (z) {
                return parseUnknownField(i, codedInputStream);
            }
            ensureExtensionsAreMutable();
            if (z2) {
                int pushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                if (generatedExtension.descriptor.getLiteType() == com.android.framework.protobuf.WireFormat.FieldType.ENUM) {
                    while (codedInputStream.getBytesUntilLimit() > 0) {
                        java.lang.Object findValueByNumber = generatedExtension.descriptor.getEnumType().findValueByNumber(codedInputStream.readEnum());
                        if (findValueByNumber == null) {
                            return true;
                        }
                        this.extensions.addRepeatedField(generatedExtension.descriptor, generatedExtension.singularToFieldSetType(findValueByNumber));
                    }
                } else {
                    while (codedInputStream.getBytesUntilLimit() > 0) {
                        this.extensions.addRepeatedField(generatedExtension.descriptor, com.android.framework.protobuf.FieldSet.readPrimitiveField(codedInputStream, generatedExtension.descriptor.getLiteType(), false));
                    }
                }
                codedInputStream.popLimit(pushLimit);
            } else {
                switch (generatedExtension.descriptor.getLiteJavaType()) {
                    case MESSAGE:
                        if (!generatedExtension.descriptor.isRepeated() && (messageLite = (com.android.framework.protobuf.MessageLite) this.extensions.getField(generatedExtension.descriptor)) != null) {
                            builder = messageLite.toBuilder();
                        } else {
                            builder = null;
                        }
                        if (builder == null) {
                            builder = generatedExtension.getMessageDefaultInstance().newBuilderForType();
                        }
                        if (generatedExtension.descriptor.getLiteType() == com.android.framework.protobuf.WireFormat.FieldType.GROUP) {
                            codedInputStream.readGroup(generatedExtension.getNumber(), builder, extensionRegistryLite);
                        } else {
                            codedInputStream.readMessage(builder, extensionRegistryLite);
                        }
                        build = builder.build();
                        break;
                    case ENUM:
                        int readEnum = codedInputStream.readEnum();
                        build = generatedExtension.descriptor.getEnumType().findValueByNumber(readEnum);
                        if (build == null) {
                            mergeVarintField(i2, readEnum);
                            return true;
                        }
                        break;
                    default:
                        build = com.android.framework.protobuf.FieldSet.readPrimitiveField(codedInputStream, generatedExtension.descriptor.getLiteType(), false);
                        break;
                }
                if (generatedExtension.descriptor.isRepeated()) {
                    this.extensions.addRepeatedField(generatedExtension.descriptor, generatedExtension.singularToFieldSetType(build));
                } else {
                    this.extensions.setField(generatedExtension.descriptor, generatedExtension.singularToFieldSetType(build));
                }
            }
            return true;
        }

        protected <MessageType extends com.android.framework.protobuf.MessageLite> boolean parseUnknownFieldAsMessageSet(MessageType messagetype, com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, int i) throws java.io.IOException {
            if (i == com.android.framework.protobuf.WireFormat.MESSAGE_SET_ITEM_TAG) {
                mergeMessageSetExtensionFromCodedStream(messagetype, codedInputStream, extensionRegistryLite);
                return true;
            }
            if (com.android.framework.protobuf.WireFormat.getTagWireType(i) == 2) {
                return parseUnknownField(messagetype, codedInputStream, extensionRegistryLite, i);
            }
            return codedInputStream.skipField(i);
        }

        private <MessageType extends com.android.framework.protobuf.MessageLite> void mergeMessageSetExtensionFromCodedStream(MessageType messagetype, com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int i = 0;
            com.android.framework.protobuf.ByteString byteString = null;
            com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<?, ?> generatedExtension = null;
            while (true) {
                int readTag = codedInputStream.readTag();
                if (readTag == 0) {
                    break;
                }
                if (readTag == com.android.framework.protobuf.WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                    i = codedInputStream.readUInt32();
                    if (i != 0) {
                        generatedExtension = extensionRegistryLite.findLiteExtensionByNumber(messagetype, i);
                    }
                } else if (readTag == com.android.framework.protobuf.WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                    if (i != 0 && generatedExtension != null) {
                        eagerlyMergeMessageSetExtension(codedInputStream, generatedExtension, extensionRegistryLite, i);
                        byteString = null;
                    } else {
                        byteString = codedInputStream.readBytes();
                    }
                } else if (!codedInputStream.skipField(readTag)) {
                    break;
                }
            }
            codedInputStream.checkLastTagWas(com.android.framework.protobuf.WireFormat.MESSAGE_SET_ITEM_END_TAG);
            if (byteString != null && i != 0) {
                if (generatedExtension != null) {
                    mergeMessageSetExtensionFromBytes(byteString, extensionRegistryLite, generatedExtension);
                } else if (byteString != null) {
                    mergeLengthDelimitedField(i, byteString);
                }
            }
        }

        private void eagerlyMergeMessageSetExtension(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<?, ?> generatedExtension, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, int i) throws java.io.IOException {
            parseExtension(codedInputStream, extensionRegistryLite, generatedExtension, com.android.framework.protobuf.WireFormat.makeTag(i, 2), i);
        }

        private void mergeMessageSetExtensionFromBytes(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<?, ?> generatedExtension) throws java.io.IOException {
            com.android.framework.protobuf.MessageLite.Builder builder;
            com.android.framework.protobuf.MessageLite messageLite = (com.android.framework.protobuf.MessageLite) this.extensions.getField(generatedExtension.descriptor);
            if (messageLite == null) {
                builder = null;
            } else {
                builder = messageLite.toBuilder();
            }
            if (builder == null) {
                builder = generatedExtension.getMessageDefaultInstance().newBuilderForType();
            }
            builder.mergeFrom(byteString, extensionRegistryLite);
            ensureExtensionsAreMutable().setField(generatedExtension.descriptor, generatedExtension.singularToFieldSetType(builder.build()));
        }

        com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> ensureExtensionsAreMutable() {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.m6592clone();
            }
            return this.extensions;
        }

        private void verifyExtensionContainingType(com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<MessageType, ?> generatedExtension) {
            if (generatedExtension.getContainingTypeDefaultInstance() != getDefaultInstanceForType()) {
                throw new java.lang.IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(com.android.framework.protobuf.ExtensionLite<MessageType, Type> extensionLite) {
            com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<MessageType, ?> checkIsLite = com.android.framework.protobuf.GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(checkIsLite);
            return this.extensions.hasField(checkIsLite.descriptor);
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(com.android.framework.protobuf.ExtensionLite<MessageType, java.util.List<Type>> extensionLite) {
            com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<MessageType, ?> checkIsLite = com.android.framework.protobuf.GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(checkIsLite);
            return this.extensions.getRepeatedFieldCount(checkIsLite.descriptor);
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(com.android.framework.protobuf.ExtensionLite<MessageType, Type> extensionLite) {
            com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<MessageType, ?> checkIsLite = com.android.framework.protobuf.GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(checkIsLite);
            java.lang.Object field = this.extensions.getField(checkIsLite.descriptor);
            if (field == null) {
                return checkIsLite.defaultValue;
            }
            return (Type) checkIsLite.fromFieldSetType(field);
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(com.android.framework.protobuf.ExtensionLite<MessageType, java.util.List<Type>> extensionLite, int i) {
            com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<MessageType, ?> checkIsLite = com.android.framework.protobuf.GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(checkIsLite);
            return (Type) checkIsLite.singularFromFieldSetType(this.extensions.getRepeatedField(checkIsLite.descriptor, i));
        }

        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        protected class ExtensionWriter {
            private final java.util.Iterator<java.util.Map.Entry<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor, java.lang.Object>> iter;
            private final boolean messageSetWireFormat;
            private java.util.Map.Entry<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor, java.lang.Object> next;

            private ExtensionWriter(boolean z) {
                this.iter = com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage.this.extensions.iterator();
                if (this.iter.hasNext()) {
                    this.next = this.iter.next();
                }
                this.messageSetWireFormat = z;
            }

            public void writeUntil(int i, com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
                while (this.next != null && this.next.getKey().getNumber() < i) {
                    com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor key = this.next.getKey();
                    if (this.messageSetWireFormat && key.getLiteJavaType() == com.android.framework.protobuf.WireFormat.JavaType.MESSAGE && !key.isRepeated()) {
                        codedOutputStream.writeMessageSetExtension(key.getNumber(), (com.android.framework.protobuf.MessageLite) this.next.getValue());
                    } else {
                        com.android.framework.protobuf.FieldSet.writeField(key, this.next.getValue(), codedOutputStream);
                    }
                    if (this.iter.hasNext()) {
                        this.next = this.iter.next();
                    } else {
                        this.next = null;
                    }
                }
            }
        }

        protected com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage<MessageType, BuilderType>.ExtensionWriter newExtensionWriter() {
            return new com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage.ExtensionWriter(false);
        }

        protected com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage<MessageType, BuilderType>.ExtensionWriter newMessageSetExtensionWriter() {
            return new com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage.ExtensionWriter(true);
        }

        protected int extensionsSerializedSize() {
            return this.extensions.getSerializedSize();
        }

        protected int extensionsSerializedSizeAsMessageSet() {
            return this.extensions.getMessageSetSerializedSize();
        }
    }

    public static abstract class ExtendableBuilder<MessageType extends com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage<MessageType, BuilderType>, BuilderType extends com.android.framework.protobuf.GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>> extends com.android.framework.protobuf.GeneratedMessageLite.Builder<MessageType, BuilderType> implements com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder<MessageType, BuilderType> {
        protected ExtendableBuilder(MessageType messagetype) {
            super(messagetype);
        }

        void internalSetExtensionSet(com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> fieldSet) {
            copyOnWrite();
            ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) this.instance).extensions = fieldSet;
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.Builder
        protected void copyOnWriteInternal() {
            super.copyOnWriteInternal();
            if (((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) this.instance).extensions != com.android.framework.protobuf.FieldSet.emptySet()) {
                ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) this.instance).extensions = ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) this.instance).extensions.m6592clone();
            }
        }

        private com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> ensureExtensionsAreMutable() {
            com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> fieldSet = ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) this.instance).extensions;
            if (fieldSet.isImmutable()) {
                com.android.framework.protobuf.FieldSet<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> m6592clone = fieldSet.m6592clone();
                ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) this.instance).extensions = m6592clone;
                return m6592clone;
            }
            return fieldSet;
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.Builder, com.android.framework.protobuf.MessageLite.Builder
        public final MessageType buildPartial() {
            if (!((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) this.instance).isMutable()) {
                return (MessageType) this.instance;
            }
            ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) this.instance).extensions.makeImmutable();
            return (MessageType) super.buildPartial();
        }

        private void verifyExtensionContainingType(com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<MessageType, ?> generatedExtension) {
            if (generatedExtension.getContainingTypeDefaultInstance() != getDefaultInstanceForType()) {
                throw new java.lang.IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(com.android.framework.protobuf.ExtensionLite<MessageType, Type> extensionLite) {
            return ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) this.instance).hasExtension(extensionLite);
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(com.android.framework.protobuf.ExtensionLite<MessageType, java.util.List<Type>> extensionLite) {
            return ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) this.instance).getExtensionCount(extensionLite);
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(com.android.framework.protobuf.ExtensionLite<MessageType, Type> extensionLite) {
            return (Type) ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) this.instance).getExtension(extensionLite);
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(com.android.framework.protobuf.ExtensionLite<MessageType, java.util.List<Type>> extensionLite, int i) {
            return (Type) ((com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage) this.instance).getExtension(extensionLite, i);
        }

        public final <Type> BuilderType setExtension(com.android.framework.protobuf.ExtensionLite<MessageType, Type> extensionLite, Type type) {
            com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<MessageType, ?> checkIsLite = com.android.framework.protobuf.GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(checkIsLite);
            copyOnWrite();
            ensureExtensionsAreMutable().setField(checkIsLite.descriptor, checkIsLite.toFieldSetType(type));
            return this;
        }

        public final <Type> BuilderType setExtension(com.android.framework.protobuf.ExtensionLite<MessageType, java.util.List<Type>> extensionLite, int i, Type type) {
            com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<MessageType, ?> checkIsLite = com.android.framework.protobuf.GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(checkIsLite);
            copyOnWrite();
            ensureExtensionsAreMutable().setRepeatedField(checkIsLite.descriptor, i, checkIsLite.singularToFieldSetType(type));
            return this;
        }

        public final <Type> BuilderType addExtension(com.android.framework.protobuf.ExtensionLite<MessageType, java.util.List<Type>> extensionLite, Type type) {
            com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<MessageType, ?> checkIsLite = com.android.framework.protobuf.GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(checkIsLite);
            copyOnWrite();
            ensureExtensionsAreMutable().addRepeatedField(checkIsLite.descriptor, checkIsLite.singularToFieldSetType(type));
            return this;
        }

        public final BuilderType clearExtension(com.android.framework.protobuf.ExtensionLite<MessageType, ?> extensionLite) {
            com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<MessageType, ?> checkIsLite = com.android.framework.protobuf.GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(checkIsLite);
            copyOnWrite();
            ensureExtensionsAreMutable().clearField(checkIsLite.descriptor);
            return this;
        }
    }

    public static <ContainingType extends com.android.framework.protobuf.MessageLite, Type> com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<ContainingType, Type> newSingularGeneratedExtension(ContainingType containingtype, Type type, com.android.framework.protobuf.MessageLite messageLite, com.android.framework.protobuf.Internal.EnumLiteMap<?> enumLiteMap, int i, com.android.framework.protobuf.WireFormat.FieldType fieldType, java.lang.Class cls) {
        return new com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<>(containingtype, type, messageLite, new com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor(enumLiteMap, i, fieldType, false, false), cls);
    }

    public static <ContainingType extends com.android.framework.protobuf.MessageLite, Type> com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType containingtype, com.android.framework.protobuf.MessageLite messageLite, com.android.framework.protobuf.Internal.EnumLiteMap<?> enumLiteMap, int i, com.android.framework.protobuf.WireFormat.FieldType fieldType, boolean z, java.lang.Class cls) {
        return new com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<>(containingtype, java.util.Collections.emptyList(), messageLite, new com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor(enumLiteMap, i, fieldType, true, z), cls);
    }

    static final class ExtensionDescriptor implements com.android.framework.protobuf.FieldSet.FieldDescriptorLite<com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor> {
        final com.android.framework.protobuf.Internal.EnumLiteMap<?> enumTypeMap;
        final boolean isPacked;
        final boolean isRepeated;
        final int number;
        final com.android.framework.protobuf.WireFormat.FieldType type;

        ExtensionDescriptor(com.android.framework.protobuf.Internal.EnumLiteMap<?> enumLiteMap, int i, com.android.framework.protobuf.WireFormat.FieldType fieldType, boolean z, boolean z2) {
            this.enumTypeMap = enumLiteMap;
            this.number = i;
            this.type = fieldType;
            this.isRepeated = z;
            this.isPacked = z2;
        }

        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public int getNumber() {
            return this.number;
        }

        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public com.android.framework.protobuf.WireFormat.FieldType getLiteType() {
            return this.type;
        }

        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public com.android.framework.protobuf.WireFormat.JavaType getLiteJavaType() {
            return this.type.getJavaType();
        }

        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public boolean isRepeated() {
            return this.isRepeated;
        }

        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public boolean isPacked() {
            return this.isPacked;
        }

        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public com.android.framework.protobuf.Internal.EnumLiteMap<?> getEnumType() {
            return this.enumTypeMap;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.framework.protobuf.FieldSet.FieldDescriptorLite
        public com.android.framework.protobuf.MessageLite.Builder internalMergeFrom(com.android.framework.protobuf.MessageLite.Builder builder, com.android.framework.protobuf.MessageLite messageLite) {
            return ((com.android.framework.protobuf.GeneratedMessageLite.Builder) builder).mergeFrom((com.android.framework.protobuf.GeneratedMessageLite.Builder) messageLite);
        }

        @Override // java.lang.Comparable
        public int compareTo(com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor extensionDescriptor) {
            return this.number - extensionDescriptor.number;
        }
    }

    static java.lang.reflect.Method getMethodOrDie(java.lang.Class cls, java.lang.String str, java.lang.Class... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (java.lang.NoSuchMethodException e) {
            throw new java.lang.RuntimeException("Generated message class \"" + cls.getName() + "\" missing method \"" + str + "\".", e);
        }
    }

    static java.lang.Object invokeOrDie(java.lang.reflect.Method method, java.lang.Object obj, java.lang.Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (java.lang.IllegalAccessException e) {
            throw new java.lang.RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (java.lang.reflect.InvocationTargetException e2) {
            java.lang.Throwable cause = e2.getCause();
            if (cause instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) cause);
            }
            if (cause instanceof java.lang.Error) {
                throw ((java.lang.Error) cause);
            }
            throw new java.lang.RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    public static class GeneratedExtension<ContainingType extends com.android.framework.protobuf.MessageLite, Type> extends com.android.framework.protobuf.ExtensionLite<ContainingType, Type> {
        final ContainingType containingTypeDefaultInstance;
        final Type defaultValue;
        final com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor descriptor;
        final com.android.framework.protobuf.MessageLite messageDefaultInstance;

        GeneratedExtension(ContainingType containingtype, Type type, com.android.framework.protobuf.MessageLite messageLite, com.android.framework.protobuf.GeneratedMessageLite.ExtensionDescriptor extensionDescriptor, java.lang.Class cls) {
            if (containingtype == null) {
                throw new java.lang.IllegalArgumentException("Null containingTypeDefaultInstance");
            }
            if (extensionDescriptor.getLiteType() == com.android.framework.protobuf.WireFormat.FieldType.MESSAGE && messageLite == null) {
                throw new java.lang.IllegalArgumentException("Null messageDefaultInstance");
            }
            this.containingTypeDefaultInstance = containingtype;
            this.defaultValue = type;
            this.messageDefaultInstance = messageLite;
            this.descriptor = extensionDescriptor;
        }

        public ContainingType getContainingTypeDefaultInstance() {
            return this.containingTypeDefaultInstance;
        }

        @Override // com.android.framework.protobuf.ExtensionLite
        public int getNumber() {
            return this.descriptor.getNumber();
        }

        @Override // com.android.framework.protobuf.ExtensionLite
        public com.android.framework.protobuf.MessageLite getMessageDefaultInstance() {
            return this.messageDefaultInstance;
        }

        java.lang.Object fromFieldSetType(java.lang.Object obj) {
            if (this.descriptor.isRepeated()) {
                if (this.descriptor.getLiteJavaType() == com.android.framework.protobuf.WireFormat.JavaType.ENUM) {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    java.util.Iterator it = ((java.util.List) obj).iterator();
                    while (it.hasNext()) {
                        arrayList.add(singularFromFieldSetType(it.next()));
                    }
                    return arrayList;
                }
                return obj;
            }
            return singularFromFieldSetType(obj);
        }

        java.lang.Object singularFromFieldSetType(java.lang.Object obj) {
            if (this.descriptor.getLiteJavaType() == com.android.framework.protobuf.WireFormat.JavaType.ENUM) {
                return this.descriptor.enumTypeMap.findValueByNumber(((java.lang.Integer) obj).intValue());
            }
            return obj;
        }

        java.lang.Object toFieldSetType(java.lang.Object obj) {
            if (this.descriptor.isRepeated()) {
                if (this.descriptor.getLiteJavaType() == com.android.framework.protobuf.WireFormat.JavaType.ENUM) {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    java.util.Iterator it = ((java.util.List) obj).iterator();
                    while (it.hasNext()) {
                        arrayList.add(singularToFieldSetType(it.next()));
                    }
                    return arrayList;
                }
                return obj;
            }
            return singularToFieldSetType(obj);
        }

        java.lang.Object singularToFieldSetType(java.lang.Object obj) {
            if (this.descriptor.getLiteJavaType() == com.android.framework.protobuf.WireFormat.JavaType.ENUM) {
                return java.lang.Integer.valueOf(((com.android.framework.protobuf.Internal.EnumLite) obj).getNumber());
            }
            return obj;
        }

        @Override // com.android.framework.protobuf.ExtensionLite
        public com.android.framework.protobuf.WireFormat.FieldType getLiteType() {
            return this.descriptor.getLiteType();
        }

        @Override // com.android.framework.protobuf.ExtensionLite
        public boolean isRepeated() {
            return this.descriptor.isRepeated;
        }

        @Override // com.android.framework.protobuf.ExtensionLite
        public Type getDefaultValue() {
            return this.defaultValue;
        }
    }

    protected static final class SerializedForm implements java.io.Serializable {
        private static final long serialVersionUID = 0;
        private final byte[] asBytes;
        private final java.lang.Class<?> messageClass;
        private final java.lang.String messageClassName;

        public static com.android.framework.protobuf.GeneratedMessageLite.SerializedForm of(com.android.framework.protobuf.MessageLite messageLite) {
            return new com.android.framework.protobuf.GeneratedMessageLite.SerializedForm(messageLite);
        }

        SerializedForm(com.android.framework.protobuf.MessageLite messageLite) {
            this.messageClass = messageLite.getClass();
            this.messageClassName = this.messageClass.getName();
            this.asBytes = messageLite.toByteArray();
        }

        protected java.lang.Object readResolve() throws java.io.ObjectStreamException {
            try {
                java.lang.reflect.Field declaredField = resolveMessageClass().getDeclaredField("DEFAULT_INSTANCE");
                declaredField.setAccessible(true);
                return ((com.android.framework.protobuf.MessageLite) declaredField.get(null)).newBuilderForType().mergeFrom(this.asBytes).buildPartial();
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                throw new java.lang.RuntimeException("Unable to understand proto buffer", e);
            } catch (java.lang.ClassNotFoundException e2) {
                throw new java.lang.RuntimeException("Unable to find proto buffer class: " + this.messageClassName, e2);
            } catch (java.lang.IllegalAccessException e3) {
                throw new java.lang.RuntimeException("Unable to call parsePartialFrom", e3);
            } catch (java.lang.NoSuchFieldException e4) {
                return readResolveFallback();
            } catch (java.lang.SecurityException e5) {
                throw new java.lang.RuntimeException("Unable to call DEFAULT_INSTANCE in " + this.messageClassName, e5);
            }
        }

        @java.lang.Deprecated
        private java.lang.Object readResolveFallback() throws java.io.ObjectStreamException {
            try {
                java.lang.reflect.Field declaredField = resolveMessageClass().getDeclaredField("defaultInstance");
                declaredField.setAccessible(true);
                return ((com.android.framework.protobuf.MessageLite) declaredField.get(null)).newBuilderForType().mergeFrom(this.asBytes).buildPartial();
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                throw new java.lang.RuntimeException("Unable to understand proto buffer", e);
            } catch (java.lang.ClassNotFoundException e2) {
                throw new java.lang.RuntimeException("Unable to find proto buffer class: " + this.messageClassName, e2);
            } catch (java.lang.IllegalAccessException e3) {
                throw new java.lang.RuntimeException("Unable to call parsePartialFrom", e3);
            } catch (java.lang.NoSuchFieldException e4) {
                throw new java.lang.RuntimeException("Unable to find defaultInstance in " + this.messageClassName, e4);
            } catch (java.lang.SecurityException e5) {
                throw new java.lang.RuntimeException("Unable to call defaultInstance in " + this.messageClassName, e5);
            }
        }

        private java.lang.Class<?> resolveMessageClass() throws java.lang.ClassNotFoundException {
            return this.messageClass != null ? this.messageClass : java.lang.Class.forName(this.messageClassName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <MessageType extends com.android.framework.protobuf.GeneratedMessageLite.ExtendableMessage<MessageType, BuilderType>, BuilderType extends com.android.framework.protobuf.GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>, T> com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension<MessageType, T> checkIsLite(com.android.framework.protobuf.ExtensionLite<MessageType, T> extensionLite) {
        if (!extensionLite.isLite()) {
            throw new java.lang.IllegalArgumentException("Expected a lite extension.");
        }
        return (com.android.framework.protobuf.GeneratedMessageLite.GeneratedExtension) extensionLite;
    }

    protected static final <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> boolean isInitialized(T t, boolean z) {
        byte byteValue = ((java.lang.Byte) t.dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED)).byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        boolean isInitialized = com.android.framework.protobuf.Protobuf.getInstance().schemaFor((com.android.framework.protobuf.Protobuf) t).isInitialized(t);
        if (z) {
            t.dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED, isInitialized ? t : null);
        }
        return isInitialized;
    }

    protected static com.android.framework.protobuf.Internal.IntList emptyIntList() {
        return com.android.framework.protobuf.IntArrayList.emptyList();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.framework.protobuf.Internal$IntList] */
    protected static com.android.framework.protobuf.Internal.IntList mutableCopy(com.android.framework.protobuf.Internal.IntList intList) {
        int size = intList.size();
        return intList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    protected static com.android.framework.protobuf.Internal.LongList emptyLongList() {
        return com.android.framework.protobuf.LongArrayList.emptyList();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.framework.protobuf.Internal$LongList] */
    protected static com.android.framework.protobuf.Internal.LongList mutableCopy(com.android.framework.protobuf.Internal.LongList longList) {
        int size = longList.size();
        return longList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    protected static com.android.framework.protobuf.Internal.FloatList emptyFloatList() {
        return com.android.framework.protobuf.FloatArrayList.emptyList();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.framework.protobuf.Internal$FloatList] */
    protected static com.android.framework.protobuf.Internal.FloatList mutableCopy(com.android.framework.protobuf.Internal.FloatList floatList) {
        int size = floatList.size();
        return floatList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    protected static com.android.framework.protobuf.Internal.DoubleList emptyDoubleList() {
        return com.android.framework.protobuf.DoubleArrayList.emptyList();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.framework.protobuf.Internal$DoubleList] */
    protected static com.android.framework.protobuf.Internal.DoubleList mutableCopy(com.android.framework.protobuf.Internal.DoubleList doubleList) {
        int size = doubleList.size();
        return doubleList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    protected static com.android.framework.protobuf.Internal.BooleanList emptyBooleanList() {
        return com.android.framework.protobuf.BooleanArrayList.emptyList();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.framework.protobuf.Internal$BooleanList] */
    protected static com.android.framework.protobuf.Internal.BooleanList mutableCopy(com.android.framework.protobuf.Internal.BooleanList booleanList) {
        int size = booleanList.size();
        return booleanList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    protected static <E> com.android.framework.protobuf.Internal.ProtobufList<E> emptyProtobufList() {
        return com.android.framework.protobuf.ProtobufArrayList.emptyList();
    }

    protected static <E> com.android.framework.protobuf.Internal.ProtobufList<E> mutableCopy(com.android.framework.protobuf.Internal.ProtobufList<E> protobufList) {
        int size = protobufList.size();
        return protobufList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static class DefaultInstanceBasedParser<T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> extends com.android.framework.protobuf.AbstractParser<T> {
        private final T defaultInstance;

        public DefaultInstanceBasedParser(T t) {
            this.defaultInstance = t;
        }

        @Override // com.android.framework.protobuf.Parser
        public T parsePartialFrom(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (T) com.android.framework.protobuf.GeneratedMessageLite.parsePartialFrom(this.defaultInstance, codedInputStream, extensionRegistryLite);
        }

        @Override // com.android.framework.protobuf.AbstractParser, com.android.framework.protobuf.Parser
        public T parsePartialFrom(byte[] bArr, int i, int i2, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (T) com.android.framework.protobuf.GeneratedMessageLite.parsePartialFrom(this.defaultInstance, bArr, i, i2, extensionRegistryLite);
        }
    }

    static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parsePartialFrom(T t, com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        T t2 = (T) t.newMutableInstance();
        try {
            com.android.framework.protobuf.Schema schemaFor = com.android.framework.protobuf.Protobuf.getInstance().schemaFor((com.android.framework.protobuf.Protobuf) t2);
            schemaFor.mergeFrom(t2, com.android.framework.protobuf.CodedInputStreamReader.forCodedInput(codedInputStream), extensionRegistryLite);
            schemaFor.makeImmutable(t2);
            return t2;
        } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
            e = e;
            if (e.getThrownFromInputStream()) {
                e = new com.android.framework.protobuf.InvalidProtocolBufferException((java.io.IOException) e);
            }
            throw e.setUnfinishedMessage(t2);
        } catch (com.android.framework.protobuf.UninitializedMessageException e2) {
            throw e2.asInvalidProtocolBufferException().setUnfinishedMessage(t2);
        } catch (java.io.IOException e3) {
            if (e3.getCause() instanceof com.android.framework.protobuf.InvalidProtocolBufferException) {
                throw ((com.android.framework.protobuf.InvalidProtocolBufferException) e3.getCause());
            }
            throw new com.android.framework.protobuf.InvalidProtocolBufferException(e3).setUnfinishedMessage(t2);
        } catch (java.lang.RuntimeException e4) {
            if (e4.getCause() instanceof com.android.framework.protobuf.InvalidProtocolBufferException) {
                throw ((com.android.framework.protobuf.InvalidProtocolBufferException) e4.getCause());
            }
            throw e4;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parsePartialFrom(T t, byte[] bArr, int i, int i2, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        T t2 = (T) t.newMutableInstance();
        try {
            com.android.framework.protobuf.Schema schemaFor = com.android.framework.protobuf.Protobuf.getInstance().schemaFor((com.android.framework.protobuf.Protobuf) t2);
            schemaFor.mergeFrom(t2, bArr, i, i + i2, new com.android.framework.protobuf.ArrayDecoders.Registers(extensionRegistryLite));
            schemaFor.makeImmutable(t2);
            return t2;
        } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
            e = e;
            if (e.getThrownFromInputStream()) {
                e = new com.android.framework.protobuf.InvalidProtocolBufferException((java.io.IOException) e);
            }
            throw e.setUnfinishedMessage(t2);
        } catch (com.android.framework.protobuf.UninitializedMessageException e2) {
            throw e2.asInvalidProtocolBufferException().setUnfinishedMessage(t2);
        } catch (java.io.IOException e3) {
            if (e3.getCause() instanceof com.android.framework.protobuf.InvalidProtocolBufferException) {
                throw ((com.android.framework.protobuf.InvalidProtocolBufferException) e3.getCause());
            }
            throw new com.android.framework.protobuf.InvalidProtocolBufferException(e3).setUnfinishedMessage(t2);
        } catch (java.lang.IndexOutOfBoundsException e4) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage().setUnfinishedMessage(t2);
        }
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parsePartialFrom(T t, com.android.framework.protobuf.CodedInputStream codedInputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (T) parsePartialFrom(t, codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry());
    }

    private static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T checkMessageInitialized(T t) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        if (t != null && !t.isInitialized()) {
            throw t.newUninitializedMessageException().asInvalidProtocolBufferException().setUnfinishedMessage(t);
        }
        return t;
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parseFrom(T t, java.nio.ByteBuffer byteBuffer, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parseFrom(t, com.android.framework.protobuf.CodedInputStream.newInstance(byteBuffer), extensionRegistryLite));
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parseFrom(T t, java.nio.ByteBuffer byteBuffer) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (T) parseFrom(t, byteBuffer, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parseFrom(T t, com.android.framework.protobuf.ByteString byteString) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parseFrom(t, byteString, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parseFrom(T t, com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialFrom(t, byteString, extensionRegistryLite));
    }

    private static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parsePartialFrom(T t, com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        com.android.framework.protobuf.CodedInputStream newCodedInput = byteString.newCodedInput();
        T t2 = (T) parsePartialFrom(t, newCodedInput, extensionRegistryLite);
        try {
            newCodedInput.checkLastTagWas(0);
            return t2;
        } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(t2);
        }
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parseFrom(T t, byte[] bArr) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialFrom(t, bArr, 0, bArr.length, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parseFrom(T t, byte[] bArr, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialFrom(t, bArr, 0, bArr.length, extensionRegistryLite));
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parseFrom(T t, java.io.InputStream inputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialFrom(t, com.android.framework.protobuf.CodedInputStream.newInstance(inputStream), com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parseFrom(T t, java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialFrom(t, com.android.framework.protobuf.CodedInputStream.newInstance(inputStream), extensionRegistryLite));
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parseFrom(T t, com.android.framework.protobuf.CodedInputStream codedInputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (T) parseFrom(t, codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parseFrom(T t, com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialFrom(t, codedInputStream, extensionRegistryLite));
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T t, java.io.InputStream inputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialDelimitedFrom(t, inputStream, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T t, java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (T) checkMessageInitialized(parsePartialDelimitedFrom(t, inputStream, extensionRegistryLite));
    }

    private static <T extends com.android.framework.protobuf.GeneratedMessageLite<T, ?>> T parsePartialDelimitedFrom(T t, java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        try {
            int read = inputStream.read();
            if (read == -1) {
                return null;
            }
            com.android.framework.protobuf.CodedInputStream newInstance = com.android.framework.protobuf.CodedInputStream.newInstance(new com.android.framework.protobuf.AbstractMessageLite.Builder.LimitedInputStream(inputStream, com.android.framework.protobuf.CodedInputStream.readRawVarint32(read, inputStream)));
            T t2 = (T) parsePartialFrom(t, newInstance, extensionRegistryLite);
            try {
                newInstance.checkLastTagWas(0);
                return t2;
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(t2);
            }
        } catch (com.android.framework.protobuf.InvalidProtocolBufferException e2) {
            if (e2.getThrownFromInputStream()) {
                throw new com.android.framework.protobuf.InvalidProtocolBufferException((java.io.IOException) e2);
            }
            throw e2;
        } catch (java.io.IOException e3) {
            throw new com.android.framework.protobuf.InvalidProtocolBufferException(e3);
        }
    }
}
