package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public final class UnsafeByteOperations {
    private UnsafeByteOperations() {
    }

    public static com.android.framework.protobuf.ByteString unsafeWrap(byte[] bArr) {
        return com.android.framework.protobuf.ByteString.wrap(bArr);
    }

    public static com.android.framework.protobuf.ByteString unsafeWrap(byte[] bArr, int i, int i2) {
        return com.android.framework.protobuf.ByteString.wrap(bArr, i, i2);
    }

    public static com.android.framework.protobuf.ByteString unsafeWrap(java.nio.ByteBuffer byteBuffer) {
        return com.android.framework.protobuf.ByteString.wrap(byteBuffer);
    }

    public static void unsafeWriteTo(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ByteOutput byteOutput) throws java.io.IOException {
        byteString.writeTo(byteOutput);
    }
}
