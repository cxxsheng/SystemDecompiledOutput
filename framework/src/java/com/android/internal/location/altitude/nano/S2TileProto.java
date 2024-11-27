package com.android.internal.location.altitude.nano;

/* loaded from: classes4.dex */
public final class S2TileProto extends com.android.framework.protobuf.nano.MessageNano {
    private static volatile com.android.internal.location.altitude.nano.S2TileProto[] _emptyArray;
    public byte[] byteBuffer;
    public byte[] byteJpeg;
    public byte[] bytePng;
    public java.lang.String tileKey;

    public static com.android.internal.location.altitude.nano.S2TileProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new com.android.internal.location.altitude.nano.S2TileProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public S2TileProto() {
        clear();
    }

    public com.android.internal.location.altitude.nano.S2TileProto clear() {
        this.tileKey = "";
        this.byteBuffer = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_BYTES;
        this.byteJpeg = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_BYTES;
        this.bytePng = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_BYTES;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
        if (!this.tileKey.equals("")) {
            codedOutputByteBufferNano.writeString(1, this.tileKey);
        }
        if (!java.util.Arrays.equals(this.byteBuffer, com.android.framework.protobuf.nano.WireFormatNano.EMPTY_BYTES)) {
            codedOutputByteBufferNano.writeBytes(2, this.byteBuffer);
        }
        if (!java.util.Arrays.equals(this.byteJpeg, com.android.framework.protobuf.nano.WireFormatNano.EMPTY_BYTES)) {
            codedOutputByteBufferNano.writeBytes(3, this.byteJpeg);
        }
        if (!java.util.Arrays.equals(this.bytePng, com.android.framework.protobuf.nano.WireFormatNano.EMPTY_BYTES)) {
            codedOutputByteBufferNano.writeBytes(4, this.bytePng);
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        if (!this.tileKey.equals("")) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(1, this.tileKey);
        }
        if (!java.util.Arrays.equals(this.byteBuffer, com.android.framework.protobuf.nano.WireFormatNano.EMPTY_BYTES)) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBytesSize(2, this.byteBuffer);
        }
        if (!java.util.Arrays.equals(this.byteJpeg, com.android.framework.protobuf.nano.WireFormatNano.EMPTY_BYTES)) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBytesSize(3, this.byteJpeg);
        }
        if (!java.util.Arrays.equals(this.bytePng, com.android.framework.protobuf.nano.WireFormatNano.EMPTY_BYTES)) {
            return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeBytesSize(4, this.bytePng);
        }
        return computeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public com.android.internal.location.altitude.nano.S2TileProto mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            switch (readTag) {
                case 0:
                    return this;
                case 10:
                    this.tileKey = codedInputByteBufferNano.readString();
                    break;
                case 18:
                    this.byteBuffer = codedInputByteBufferNano.readBytes();
                    break;
                case 26:
                    this.byteJpeg = codedInputByteBufferNano.readBytes();
                    break;
                case 34:
                    this.bytePng = codedInputByteBufferNano.readBytes();
                    break;
                default:
                    if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static com.android.internal.location.altitude.nano.S2TileProto parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
        return (com.android.internal.location.altitude.nano.S2TileProto) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.internal.location.altitude.nano.S2TileProto(), bArr);
    }

    public static com.android.internal.location.altitude.nano.S2TileProto parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        return new com.android.internal.location.altitude.nano.S2TileProto().mergeFrom(codedInputByteBufferNano);
    }
}
