package com.android.framework.protobuf.nano;

/* loaded from: classes4.dex */
final class UnknownFieldData {
    final byte[] bytes;
    final int tag;

    UnknownFieldData(int i, byte[] bArr) {
        this.tag = i;
        this.bytes = bArr;
    }

    int computeSerializedSize() {
        return com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeRawVarint32Size(this.tag) + 0 + this.bytes.length;
    }

    void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
        codedOutputByteBufferNano.writeRawVarint32(this.tag);
        codedOutputByteBufferNano.writeRawBytes(this.bytes);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.framework.protobuf.nano.UnknownFieldData)) {
            return false;
        }
        com.android.framework.protobuf.nano.UnknownFieldData unknownFieldData = (com.android.framework.protobuf.nano.UnknownFieldData) obj;
        return this.tag == unknownFieldData.tag && java.util.Arrays.equals(this.bytes, unknownFieldData.bytes);
    }

    public int hashCode() {
        return ((527 + this.tag) * 31) + java.util.Arrays.hashCode(this.bytes);
    }
}
