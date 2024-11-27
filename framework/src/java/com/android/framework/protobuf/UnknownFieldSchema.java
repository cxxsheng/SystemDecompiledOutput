package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
abstract class UnknownFieldSchema<T, B> {
    abstract void addFixed32(B b, int i, int i2);

    abstract void addFixed64(B b, int i, long j);

    abstract void addGroup(B b, int i, T t);

    abstract void addLengthDelimited(B b, int i, com.android.framework.protobuf.ByteString byteString);

    abstract void addVarint(B b, int i, long j);

    abstract B getBuilderFromMessage(java.lang.Object obj);

    abstract T getFromMessage(java.lang.Object obj);

    abstract int getSerializedSize(T t);

    abstract int getSerializedSizeAsMessageSet(T t);

    abstract void makeImmutable(java.lang.Object obj);

    abstract T merge(T t, T t2);

    abstract B newBuilder();

    abstract void setBuilderToMessage(java.lang.Object obj, B b);

    abstract void setToMessage(java.lang.Object obj, T t);

    abstract boolean shouldDiscardUnknownFields(com.android.framework.protobuf.Reader reader);

    abstract T toImmutable(B b);

    abstract void writeAsMessageSetTo(T t, com.android.framework.protobuf.Writer writer) throws java.io.IOException;

    abstract void writeTo(T t, com.android.framework.protobuf.Writer writer) throws java.io.IOException;

    UnknownFieldSchema() {
    }

    final boolean mergeOneFieldFrom(B b, com.android.framework.protobuf.Reader reader) throws java.io.IOException {
        int tag = reader.getTag();
        int tagFieldNumber = com.android.framework.protobuf.WireFormat.getTagFieldNumber(tag);
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(tag)) {
            case 0:
                addVarint(b, tagFieldNumber, reader.readInt64());
                return true;
            case 1:
                addFixed64(b, tagFieldNumber, reader.readFixed64());
                return true;
            case 2:
                addLengthDelimited(b, tagFieldNumber, reader.readBytes());
                return true;
            case 3:
                B newBuilder = newBuilder();
                int makeTag = com.android.framework.protobuf.WireFormat.makeTag(tagFieldNumber, 4);
                mergeFrom(newBuilder, reader);
                if (makeTag != reader.getTag()) {
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidEndTag();
                }
                addGroup(b, tagFieldNumber, toImmutable(newBuilder));
                return true;
            case 4:
                return false;
            case 5:
                addFixed32(b, tagFieldNumber, reader.readFixed32());
                return true;
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
        }
    }

    final void mergeFrom(B b, com.android.framework.protobuf.Reader reader) throws java.io.IOException {
        while (reader.getFieldNumber() != Integer.MAX_VALUE && mergeOneFieldFrom(b, reader)) {
        }
    }
}
