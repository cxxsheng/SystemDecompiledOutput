package com.android.framework.protobuf;

/* loaded from: classes4.dex */
class GeneratedMessageInfoFactory implements com.android.framework.protobuf.MessageInfoFactory {
    private static final com.android.framework.protobuf.GeneratedMessageInfoFactory instance = new com.android.framework.protobuf.GeneratedMessageInfoFactory();

    private GeneratedMessageInfoFactory() {
    }

    public static com.android.framework.protobuf.GeneratedMessageInfoFactory getInstance() {
        return instance;
    }

    @Override // com.android.framework.protobuf.MessageInfoFactory
    public boolean isSupported(java.lang.Class<?> cls) {
        return com.android.framework.protobuf.GeneratedMessageLite.class.isAssignableFrom(cls);
    }

    @Override // com.android.framework.protobuf.MessageInfoFactory
    public com.android.framework.protobuf.MessageInfo messageInfoFor(java.lang.Class<?> cls) {
        if (!com.android.framework.protobuf.GeneratedMessageLite.class.isAssignableFrom(cls)) {
            throw new java.lang.IllegalArgumentException("Unsupported message type: " + cls.getName());
        }
        try {
            return (com.android.framework.protobuf.MessageInfo) com.android.framework.protobuf.GeneratedMessageLite.getDefaultInstance(cls.asSubclass(com.android.framework.protobuf.GeneratedMessageLite.class)).buildMessageInfo();
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException("Unable to get message info for " + cls.getName(), e);
        }
    }
}
