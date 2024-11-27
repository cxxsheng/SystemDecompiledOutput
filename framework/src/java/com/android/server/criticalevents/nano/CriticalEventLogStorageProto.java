package com.android.server.criticalevents.nano;

/* loaded from: classes5.dex */
public final class CriticalEventLogStorageProto extends com.android.framework.protobuf.nano.MessageNano {
    private static volatile com.android.server.criticalevents.nano.CriticalEventLogStorageProto[] _emptyArray;
    public com.android.server.criticalevents.nano.CriticalEventProto[] events;

    public static com.android.server.criticalevents.nano.CriticalEventLogStorageProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new com.android.server.criticalevents.nano.CriticalEventLogStorageProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public CriticalEventLogStorageProto() {
        clear();
    }

    public com.android.server.criticalevents.nano.CriticalEventLogStorageProto clear() {
        this.events = com.android.server.criticalevents.nano.CriticalEventProto.emptyArray();
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
        if (this.events != null && this.events.length > 0) {
            for (int i = 0; i < this.events.length; i++) {
                com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto = this.events[i];
                if (criticalEventProto != null) {
                    codedOutputByteBufferNano.writeMessage(1, criticalEventProto);
                }
            }
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        if (this.events != null && this.events.length > 0) {
            for (int i = 0; i < this.events.length; i++) {
                com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto = this.events[i];
                if (criticalEventProto != null) {
                    computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(1, criticalEventProto);
                }
            }
        }
        return computeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public com.android.server.criticalevents.nano.CriticalEventLogStorageProto mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            switch (readTag) {
                case 0:
                    return this;
                case 10:
                    int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    int length = this.events == null ? 0 : this.events.length;
                    int i = repeatedFieldArrayLength + length;
                    com.android.server.criticalevents.nano.CriticalEventProto[] criticalEventProtoArr = new com.android.server.criticalevents.nano.CriticalEventProto[i];
                    if (length != 0) {
                        java.lang.System.arraycopy(this.events, 0, criticalEventProtoArr, 0, length);
                    }
                    while (length < i - 1) {
                        criticalEventProtoArr[length] = new com.android.server.criticalevents.nano.CriticalEventProto();
                        codedInputByteBufferNano.readMessage(criticalEventProtoArr[length]);
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    criticalEventProtoArr[length] = new com.android.server.criticalevents.nano.CriticalEventProto();
                    codedInputByteBufferNano.readMessage(criticalEventProtoArr[length]);
                    this.events = criticalEventProtoArr;
                    break;
                default:
                    if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static com.android.server.criticalevents.nano.CriticalEventLogStorageProto parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
        return (com.android.server.criticalevents.nano.CriticalEventLogStorageProto) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.criticalevents.nano.CriticalEventLogStorageProto(), bArr);
    }

    public static com.android.server.criticalevents.nano.CriticalEventLogStorageProto parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        return new com.android.server.criticalevents.nano.CriticalEventLogStorageProto().mergeFrom(codedInputByteBufferNano);
    }
}
