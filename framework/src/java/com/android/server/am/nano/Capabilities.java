package com.android.server.am.nano;

/* loaded from: classes5.dex */
public final class Capabilities extends com.android.framework.protobuf.nano.MessageNano {
    private static volatile com.android.server.am.nano.Capabilities[] _emptyArray;
    public com.android.server.am.nano.Capability[] values;

    public static com.android.server.am.nano.Capabilities[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new com.android.server.am.nano.Capabilities[0];
                }
            }
        }
        return _emptyArray;
    }

    public Capabilities() {
        clear();
    }

    public com.android.server.am.nano.Capabilities clear() {
        this.values = com.android.server.am.nano.Capability.emptyArray();
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
        if (this.values != null && this.values.length > 0) {
            for (int i = 0; i < this.values.length; i++) {
                com.android.server.am.nano.Capability capability = this.values[i];
                if (capability != null) {
                    codedOutputByteBufferNano.writeMessage(1, capability);
                }
            }
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        if (this.values != null && this.values.length > 0) {
            for (int i = 0; i < this.values.length; i++) {
                com.android.server.am.nano.Capability capability = this.values[i];
                if (capability != null) {
                    computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(1, capability);
                }
            }
        }
        return computeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public com.android.server.am.nano.Capabilities mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            switch (readTag) {
                case 0:
                    return this;
                case 10:
                    int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    int length = this.values == null ? 0 : this.values.length;
                    int i = repeatedFieldArrayLength + length;
                    com.android.server.am.nano.Capability[] capabilityArr = new com.android.server.am.nano.Capability[i];
                    if (length != 0) {
                        java.lang.System.arraycopy(this.values, 0, capabilityArr, 0, length);
                    }
                    while (length < i - 1) {
                        capabilityArr[length] = new com.android.server.am.nano.Capability();
                        codedInputByteBufferNano.readMessage(capabilityArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    capabilityArr[length] = new com.android.server.am.nano.Capability();
                    codedInputByteBufferNano.readMessage(capabilityArr[length]);
                    this.values = capabilityArr;
                    break;
                default:
                    if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static com.android.server.am.nano.Capabilities parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
        return (com.android.server.am.nano.Capabilities) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.am.nano.Capabilities(), bArr);
    }

    public static com.android.server.am.nano.Capabilities parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        return new com.android.server.am.nano.Capabilities().mergeFrom(codedInputByteBufferNano);
    }
}
