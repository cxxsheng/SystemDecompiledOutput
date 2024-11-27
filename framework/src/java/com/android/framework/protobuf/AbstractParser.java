package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public abstract class AbstractParser<MessageType extends com.android.framework.protobuf.MessageLite> implements com.android.framework.protobuf.Parser<MessageType> {
    private static final com.android.framework.protobuf.ExtensionRegistryLite EMPTY_REGISTRY = com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry();

    private com.android.framework.protobuf.UninitializedMessageException newUninitializedMessageException(MessageType messagetype) {
        if (messagetype instanceof com.android.framework.protobuf.AbstractMessageLite) {
            return ((com.android.framework.protobuf.AbstractMessageLite) messagetype).newUninitializedMessageException();
        }
        return new com.android.framework.protobuf.UninitializedMessageException(messagetype);
    }

    private MessageType checkMessageInitialized(MessageType messagetype) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        if (messagetype != null && !messagetype.isInitialized()) {
            throw newUninitializedMessageException(messagetype).asInvalidProtocolBufferException().setUnfinishedMessage(messagetype);
        }
        return messagetype;
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(com.android.framework.protobuf.CodedInputStream codedInputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (MessageType) parsePartialFrom(codedInputStream, EMPTY_REGISTRY);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return (MessageType) checkMessageInitialized((com.android.framework.protobuf.MessageLite) parsePartialFrom(codedInputStream, extensionRegistryLite));
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(com.android.framework.protobuf.CodedInputStream codedInputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parseFrom(codedInputStream, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        try {
            com.android.framework.protobuf.CodedInputStream newCodedInput = byteString.newCodedInput();
            MessageType messagetype = (MessageType) parsePartialFrom(newCodedInput, extensionRegistryLite);
            try {
                newCodedInput.checkLastTagWas(0);
                return messagetype;
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(messagetype);
            }
        } catch (com.android.framework.protobuf.InvalidProtocolBufferException e2) {
            throw e2;
        }
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(com.android.framework.protobuf.ByteString byteString) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parsePartialFrom(byteString, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(byteString, extensionRegistryLite));
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(com.android.framework.protobuf.ByteString byteString) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parseFrom(byteString, EMPTY_REGISTRY);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(java.nio.ByteBuffer byteBuffer, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        try {
            com.android.framework.protobuf.CodedInputStream newInstance = com.android.framework.protobuf.CodedInputStream.newInstance(byteBuffer);
            com.android.framework.protobuf.MessageLite messageLite = (com.android.framework.protobuf.MessageLite) parsePartialFrom(newInstance, extensionRegistryLite);
            try {
                newInstance.checkLastTagWas(0);
                return (MessageType) checkMessageInitialized(messageLite);
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(messageLite);
            }
        } catch (com.android.framework.protobuf.InvalidProtocolBufferException e2) {
            throw e2;
        }
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(java.nio.ByteBuffer byteBuffer) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parseFrom(byteBuffer, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(byte[] bArr, int i, int i2, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        try {
            com.android.framework.protobuf.CodedInputStream newInstance = com.android.framework.protobuf.CodedInputStream.newInstance(bArr, i, i2);
            MessageType messagetype = (MessageType) parsePartialFrom(newInstance, extensionRegistryLite);
            try {
                newInstance.checkLastTagWas(0);
                return messagetype;
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(messagetype);
            }
        } catch (com.android.framework.protobuf.InvalidProtocolBufferException e2) {
            throw e2;
        }
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(byte[] bArr, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parsePartialFrom(bArr, i, i2, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(byte[] bArr, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parsePartialFrom(bArr, 0, bArr.length, extensionRegistryLite);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(byte[] bArr) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parsePartialFrom(bArr, 0, bArr.length, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(byte[] bArr, int i, int i2, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(bArr, i, i2, extensionRegistryLite));
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(byte[] bArr, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parseFrom(bArr, i, i2, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(byte[] bArr, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parseFrom(bArr, 0, bArr.length, extensionRegistryLite);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(byte[] bArr) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parseFrom(bArr, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        com.android.framework.protobuf.CodedInputStream newInstance = com.android.framework.protobuf.CodedInputStream.newInstance(inputStream);
        MessageType messagetype = (MessageType) parsePartialFrom(newInstance, extensionRegistryLite);
        try {
            newInstance.checkLastTagWas(0);
            return messagetype;
        } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(messagetype);
        }
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(java.io.InputStream inputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parsePartialFrom(inputStream, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(inputStream, extensionRegistryLite));
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(java.io.InputStream inputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parseFrom(inputStream, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialDelimitedFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        try {
            int read = inputStream.read();
            if (read == -1) {
                return null;
            }
            return parsePartialFrom((java.io.InputStream) new com.android.framework.protobuf.AbstractMessageLite.Builder.LimitedInputStream(inputStream, com.android.framework.protobuf.CodedInputStream.readRawVarint32(read, inputStream)), extensionRegistryLite);
        } catch (java.io.IOException e) {
            throw new com.android.framework.protobuf.InvalidProtocolBufferException(e);
        }
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialDelimitedFrom(java.io.InputStream inputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parsePartialDelimitedFrom(inputStream, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseDelimitedFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialDelimitedFrom(inputStream, extensionRegistryLite));
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseDelimitedFrom(java.io.InputStream inputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException {
        return parseDelimitedFrom(inputStream, EMPTY_REGISTRY);
    }
}
