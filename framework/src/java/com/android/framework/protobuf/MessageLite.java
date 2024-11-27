package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public interface MessageLite extends com.android.framework.protobuf.MessageLiteOrBuilder {

    public interface Builder extends com.android.framework.protobuf.MessageLiteOrBuilder, java.lang.Cloneable {
        com.android.framework.protobuf.MessageLite build();

        com.android.framework.protobuf.MessageLite buildPartial();

        com.android.framework.protobuf.MessageLite.Builder clear();

        /* renamed from: clone */
        com.android.framework.protobuf.MessageLite.Builder mo6591clone();

        boolean mergeDelimitedFrom(java.io.InputStream inputStream) throws java.io.IOException;

        boolean mergeDelimitedFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException;

        com.android.framework.protobuf.MessageLite.Builder mergeFrom(com.android.framework.protobuf.ByteString byteString) throws com.android.framework.protobuf.InvalidProtocolBufferException;

        com.android.framework.protobuf.MessageLite.Builder mergeFrom(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

        com.android.framework.protobuf.MessageLite.Builder mergeFrom(com.android.framework.protobuf.CodedInputStream codedInputStream) throws java.io.IOException;

        com.android.framework.protobuf.MessageLite.Builder mergeFrom(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException;

        com.android.framework.protobuf.MessageLite.Builder mergeFrom(com.android.framework.protobuf.MessageLite messageLite);

        com.android.framework.protobuf.MessageLite.Builder mergeFrom(java.io.InputStream inputStream) throws java.io.IOException;

        com.android.framework.protobuf.MessageLite.Builder mergeFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException;

        com.android.framework.protobuf.MessageLite.Builder mergeFrom(byte[] bArr) throws com.android.framework.protobuf.InvalidProtocolBufferException;

        com.android.framework.protobuf.MessageLite.Builder mergeFrom(byte[] bArr, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException;

        com.android.framework.protobuf.MessageLite.Builder mergeFrom(byte[] bArr, int i, int i2, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;

        com.android.framework.protobuf.MessageLite.Builder mergeFrom(byte[] bArr, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException;
    }

    com.android.framework.protobuf.Parser<? extends com.android.framework.protobuf.MessageLite> getParserForType();

    int getSerializedSize();

    com.android.framework.protobuf.MessageLite.Builder newBuilderForType();

    com.android.framework.protobuf.MessageLite.Builder toBuilder();

    byte[] toByteArray();

    com.android.framework.protobuf.ByteString toByteString();

    void writeDelimitedTo(java.io.OutputStream outputStream) throws java.io.IOException;

    void writeTo(com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException;

    void writeTo(java.io.OutputStream outputStream) throws java.io.IOException;
}
