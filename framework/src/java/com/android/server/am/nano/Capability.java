package com.android.server.am.nano;

/* loaded from: classes5.dex */
public final class Capability extends com.android.framework.protobuf.nano.MessageNano {
    private static volatile com.android.server.am.nano.Capability[] _emptyArray;
    public java.lang.String name;

    public static com.android.server.am.nano.Capability[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new com.android.server.am.nano.Capability[0];
                }
            }
        }
        return _emptyArray;
    }

    public Capability() {
        clear();
    }

    public com.android.server.am.nano.Capability clear() {
        this.name = "";
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
        if (!this.name.equals("")) {
            codedOutputByteBufferNano.writeString(1, this.name);
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        if (!this.name.equals("")) {
            return computeSerializedSize + com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSize(1, this.name);
        }
        return computeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public com.android.server.am.nano.Capability mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            switch (readTag) {
                case 0:
                    return this;
                case 10:
                    this.name = codedInputByteBufferNano.readString();
                    break;
                default:
                    if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static com.android.server.am.nano.Capability parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
        return (com.android.server.am.nano.Capability) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.am.nano.Capability(), bArr);
    }

    public static com.android.server.am.nano.Capability parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        return new com.android.server.am.nano.Capability().mergeFrom(codedInputByteBufferNano);
    }
}
