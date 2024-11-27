package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public interface Parser<MessageType> {
    MessageType parseDelimitedFrom(java.io.InputStream inputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parseDelimitedFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parseFrom(com.android.framework.protobuf.ByteString byteString) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parseFrom(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parseFrom(com.android.framework.protobuf.CodedInputStream codedInputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parseFrom(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parseFrom(java.io.InputStream inputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parseFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parseFrom(java.nio.ByteBuffer byteBuffer) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parseFrom(java.nio.ByteBuffer byteBuffer, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parseFrom(byte[] bArr) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parseFrom(byte[] bArr, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parseFrom(byte[] bArr, int i, int i2, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parseFrom(byte[] bArr, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parsePartialDelimitedFrom(java.io.InputStream inputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parsePartialDelimitedFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parsePartialFrom(com.android.framework.protobuf.ByteString byteString) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parsePartialFrom(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parsePartialFrom(com.android.framework.protobuf.CodedInputStream codedInputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parsePartialFrom(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parsePartialFrom(java.io.InputStream inputStream) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parsePartialFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parsePartialFrom(byte[] bArr) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parsePartialFrom(byte[] bArr, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parsePartialFrom(byte[] bArr, int i, int i2, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

    MessageType parsePartialFrom(byte[] bArr, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;
}
