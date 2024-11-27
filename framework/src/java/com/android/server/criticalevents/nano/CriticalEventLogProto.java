package com.android.server.criticalevents.nano;

/* loaded from: classes5.dex */
public final class CriticalEventLogProto extends com.android.framework.protobuf.nano.MessageNano {
    private static volatile com.android.server.criticalevents.nano.CriticalEventLogProto[] _emptyArray;
    public int capacity;
    public com.android.server.criticalevents.nano.CriticalEventProto[] events;
    public long timestampMs;
    public int windowMs;

    public static com.android.server.criticalevents.nano.CriticalEventLogProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new com.android.server.criticalevents.nano.CriticalEventLogProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public CriticalEventLogProto() {
        clear();
    }

    public com.android.server.criticalevents.nano.CriticalEventLogProto clear() {
        this.timestampMs = 0L;
        this.windowMs = 0;
        this.capacity = 0;
        this.events = com.android.server.criticalevents.nano.CriticalEventProto.emptyArray();
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
        if (this.timestampMs != 0) {
            codedOutputByteBufferNano.writeInt64(1, this.timestampMs);
        }
        if (this.windowMs != 0) {
            codedOutputByteBufferNano.writeInt32(2, this.windowMs);
        }
        if (this.capacity != 0) {
            codedOutputByteBufferNano.writeInt32(3, this.capacity);
        }
        if (this.events != null && this.events.length > 0) {
            for (int i = 0; i < this.events.length; i++) {
                com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto = this.events[i];
                if (criticalEventProto != null) {
                    codedOutputByteBufferNano.writeMessage(4, criticalEventProto);
                }
            }
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        if (this.timestampMs != 0) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt64Size(1, this.timestampMs);
        }
        if (this.windowMs != 0) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(2, this.windowMs);
        }
        if (this.capacity != 0) {
            computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeInt32Size(3, this.capacity);
        }
        if (this.events != null && this.events.length > 0) {
            for (int i = 0; i < this.events.length; i++) {
                com.android.server.criticalevents.nano.CriticalEventProto criticalEventProto = this.events[i];
                if (criticalEventProto != null) {
                    computeSerializedSize += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeMessageSize(4, criticalEventProto);
                }
            }
        }
        return computeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public com.android.server.criticalevents.nano.CriticalEventLogProto mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            switch (readTag) {
                case 0:
                    return this;
                case 8:
                    this.timestampMs = codedInputByteBufferNano.readInt64();
                    break;
                case 16:
                    this.windowMs = codedInputByteBufferNano.readInt32();
                    break;
                case 24:
                    this.capacity = codedInputByteBufferNano.readInt32();
                    break;
                case 34:
                    int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 34);
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

    public static com.android.server.criticalevents.nano.CriticalEventLogProto parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
        return (com.android.server.criticalevents.nano.CriticalEventLogProto) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.server.criticalevents.nano.CriticalEventLogProto(), bArr);
    }

    public static com.android.server.criticalevents.nano.CriticalEventLogProto parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        return new com.android.server.criticalevents.nano.CriticalEventLogProto().mergeFrom(codedInputByteBufferNano);
    }
}
