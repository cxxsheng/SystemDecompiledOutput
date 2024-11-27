package com.android.service.nano;

/* loaded from: classes5.dex */
public final class StringListParamProto extends com.android.framework.protobuf.nano.MessageNano {
    private static volatile com.android.service.nano.StringListParamProto[] _emptyArray;
    public java.lang.String[] element;

    public static com.android.service.nano.StringListParamProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new com.android.service.nano.StringListParamProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public StringListParamProto() {
        clear();
    }

    public com.android.service.nano.StringListParamProto clear() {
        this.element = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_STRING_ARRAY;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
        if (this.element != null && this.element.length > 0) {
            for (int i = 0; i < this.element.length; i++) {
                java.lang.String str = this.element[i];
                if (str != null) {
                    codedOutputByteBufferNano.writeString(1, str);
                }
            }
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        if (this.element != null && this.element.length > 0) {
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < this.element.length; i3++) {
                java.lang.String str = this.element[i3];
                if (str != null) {
                    i2++;
                    i += com.android.framework.protobuf.nano.CodedOutputByteBufferNano.computeStringSizeNoTag(str);
                }
            }
            return computeSerializedSize + i + (i2 * 1);
        }
        return computeSerializedSize;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public com.android.service.nano.StringListParamProto mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            switch (readTag) {
                case 0:
                    return this;
                case 10:
                    int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    int length = this.element == null ? 0 : this.element.length;
                    int i = repeatedFieldArrayLength + length;
                    java.lang.String[] strArr = new java.lang.String[i];
                    if (length != 0) {
                        java.lang.System.arraycopy(this.element, 0, strArr, 0, length);
                    }
                    while (length < i - 1) {
                        strArr[length] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    strArr[length] = codedInputByteBufferNano.readString();
                    this.element = strArr;
                    break;
                default:
                    if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static com.android.service.nano.StringListParamProto parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
        return (com.android.service.nano.StringListParamProto) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new com.android.service.nano.StringListParamProto(), bArr);
    }

    public static com.android.service.nano.StringListParamProto parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        return new com.android.service.nano.StringListParamProto().mergeFrom(codedInputByteBufferNano);
    }
}
