package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class ManifestSchemaFactory implements com.android.framework.protobuf.SchemaFactory {
    private static final com.android.framework.protobuf.MessageInfoFactory EMPTY_FACTORY = new com.android.framework.protobuf.MessageInfoFactory() { // from class: com.android.framework.protobuf.ManifestSchemaFactory.1
        @Override // com.android.framework.protobuf.MessageInfoFactory
        public boolean isSupported(java.lang.Class<?> cls) {
            return false;
        }

        @Override // com.android.framework.protobuf.MessageInfoFactory
        public com.android.framework.protobuf.MessageInfo messageInfoFor(java.lang.Class<?> cls) {
            throw new java.lang.IllegalStateException("This should never be called.");
        }
    };
    private final com.android.framework.protobuf.MessageInfoFactory messageInfoFactory;

    public ManifestSchemaFactory() {
        this(getDefaultMessageInfoFactory());
    }

    private ManifestSchemaFactory(com.android.framework.protobuf.MessageInfoFactory messageInfoFactory) {
        this.messageInfoFactory = (com.android.framework.protobuf.MessageInfoFactory) com.android.framework.protobuf.Internal.checkNotNull(messageInfoFactory, "messageInfoFactory");
    }

    @Override // com.android.framework.protobuf.SchemaFactory
    public <T> com.android.framework.protobuf.Schema<T> createSchema(java.lang.Class<T> cls) {
        com.android.framework.protobuf.SchemaUtil.requireGeneratedMessage(cls);
        com.android.framework.protobuf.MessageInfo messageInfoFor = this.messageInfoFactory.messageInfoFor(cls);
        if (messageInfoFor.isMessageSetWireFormat()) {
            if (com.android.framework.protobuf.GeneratedMessageLite.class.isAssignableFrom(cls)) {
                return com.android.framework.protobuf.MessageSetSchema.newSchema(com.android.framework.protobuf.SchemaUtil.unknownFieldSetLiteSchema(), com.android.framework.protobuf.ExtensionSchemas.lite(), messageInfoFor.getDefaultInstance());
            }
            return com.android.framework.protobuf.MessageSetSchema.newSchema(com.android.framework.protobuf.SchemaUtil.proto2UnknownFieldSetSchema(), com.android.framework.protobuf.ExtensionSchemas.full(), messageInfoFor.getDefaultInstance());
        }
        return newSchema(cls, messageInfoFor);
    }

    private static <T> com.android.framework.protobuf.Schema<T> newSchema(java.lang.Class<T> cls, com.android.framework.protobuf.MessageInfo messageInfo) {
        if (com.android.framework.protobuf.GeneratedMessageLite.class.isAssignableFrom(cls)) {
            if (isProto2(messageInfo)) {
                return com.android.framework.protobuf.MessageSchema.newSchema(cls, messageInfo, com.android.framework.protobuf.NewInstanceSchemas.lite(), com.android.framework.protobuf.ListFieldSchema.lite(), com.android.framework.protobuf.SchemaUtil.unknownFieldSetLiteSchema(), com.android.framework.protobuf.ExtensionSchemas.lite(), com.android.framework.protobuf.MapFieldSchemas.lite());
            }
            return com.android.framework.protobuf.MessageSchema.newSchema(cls, messageInfo, com.android.framework.protobuf.NewInstanceSchemas.lite(), com.android.framework.protobuf.ListFieldSchema.lite(), com.android.framework.protobuf.SchemaUtil.unknownFieldSetLiteSchema(), null, com.android.framework.protobuf.MapFieldSchemas.lite());
        }
        if (isProto2(messageInfo)) {
            return com.android.framework.protobuf.MessageSchema.newSchema(cls, messageInfo, com.android.framework.protobuf.NewInstanceSchemas.full(), com.android.framework.protobuf.ListFieldSchema.full(), com.android.framework.protobuf.SchemaUtil.proto2UnknownFieldSetSchema(), com.android.framework.protobuf.ExtensionSchemas.full(), com.android.framework.protobuf.MapFieldSchemas.full());
        }
        return com.android.framework.protobuf.MessageSchema.newSchema(cls, messageInfo, com.android.framework.protobuf.NewInstanceSchemas.full(), com.android.framework.protobuf.ListFieldSchema.full(), com.android.framework.protobuf.SchemaUtil.proto3UnknownFieldSetSchema(), null, com.android.framework.protobuf.MapFieldSchemas.full());
    }

    private static boolean isProto2(com.android.framework.protobuf.MessageInfo messageInfo) {
        return messageInfo.getSyntax() == com.android.framework.protobuf.ProtoSyntax.PROTO2;
    }

    private static com.android.framework.protobuf.MessageInfoFactory getDefaultMessageInfoFactory() {
        return new com.android.framework.protobuf.ManifestSchemaFactory.CompositeMessageInfoFactory(com.android.framework.protobuf.GeneratedMessageInfoFactory.getInstance(), getDescriptorMessageInfoFactory());
    }

    private static class CompositeMessageInfoFactory implements com.android.framework.protobuf.MessageInfoFactory {
        private com.android.framework.protobuf.MessageInfoFactory[] factories;

        CompositeMessageInfoFactory(com.android.framework.protobuf.MessageInfoFactory... messageInfoFactoryArr) {
            this.factories = messageInfoFactoryArr;
        }

        @Override // com.android.framework.protobuf.MessageInfoFactory
        public boolean isSupported(java.lang.Class<?> cls) {
            for (com.android.framework.protobuf.MessageInfoFactory messageInfoFactory : this.factories) {
                if (messageInfoFactory.isSupported(cls)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.framework.protobuf.MessageInfoFactory
        public com.android.framework.protobuf.MessageInfo messageInfoFor(java.lang.Class<?> cls) {
            for (com.android.framework.protobuf.MessageInfoFactory messageInfoFactory : this.factories) {
                if (messageInfoFactory.isSupported(cls)) {
                    return messageInfoFactory.messageInfoFor(cls);
                }
            }
            throw new java.lang.UnsupportedOperationException("No factory is available for message type: " + cls.getName());
        }
    }

    private static com.android.framework.protobuf.MessageInfoFactory getDescriptorMessageInfoFactory() {
        try {
            return (com.android.framework.protobuf.MessageInfoFactory) java.lang.Class.forName("com.android.framework.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new java.lang.Class[0]).invoke(null, new java.lang.Object[0]);
        } catch (java.lang.Exception e) {
            return EMPTY_FACTORY;
        }
    }
}
