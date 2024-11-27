package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public interface LazyStringList extends com.android.framework.protobuf.ProtocolStringList {
    void add(com.android.framework.protobuf.ByteString byteString);

    void add(byte[] bArr);

    boolean addAllByteArray(java.util.Collection<byte[]> collection);

    boolean addAllByteString(java.util.Collection<? extends com.android.framework.protobuf.ByteString> collection);

    java.util.List<byte[]> asByteArrayList();

    byte[] getByteArray(int i);

    com.android.framework.protobuf.ByteString getByteString(int i);

    java.lang.Object getRaw(int i);

    java.util.List<?> getUnderlyingElements();

    com.android.framework.protobuf.LazyStringList getUnmodifiableView();

    void mergeFrom(com.android.framework.protobuf.LazyStringList lazyStringList);

    void set(int i, com.android.framework.protobuf.ByteString byteString);

    void set(int i, byte[] bArr);
}
