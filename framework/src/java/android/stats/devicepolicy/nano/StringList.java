package android.stats.devicepolicy.nano;

/* loaded from: classes3.dex */
public final class StringList extends com.android.framework.protobuf.nano.MessageNano {
    private static volatile android.stats.devicepolicy.nano.StringList[] _emptyArray;
    public java.lang.String[] stringValue;

    public static android.stats.devicepolicy.nano.StringList[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (com.android.framework.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new android.stats.devicepolicy.nano.StringList[0];
                }
            }
        }
        return _emptyArray;
    }

    public StringList() {
        clear();
    }

    public android.stats.devicepolicy.nano.StringList clear() {
        this.stringValue = com.android.framework.protobuf.nano.WireFormatNano.EMPTY_STRING_ARRAY;
        this.cachedSize = -1;
        return this;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
        if (this.stringValue != null && this.stringValue.length > 0) {
            for (int i = 0; i < this.stringValue.length; i++) {
                java.lang.String str = this.stringValue[i];
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
        if (this.stringValue != null && this.stringValue.length > 0) {
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < this.stringValue.length; i3++) {
                java.lang.String str = this.stringValue[i3];
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
    public android.stats.devicepolicy.nano.StringList mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            switch (readTag) {
                case 0:
                    return this;
                case 10:
                    int repeatedFieldArrayLength = com.android.framework.protobuf.nano.WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 10);
                    int length = this.stringValue == null ? 0 : this.stringValue.length;
                    int i = repeatedFieldArrayLength + length;
                    java.lang.String[] strArr = new java.lang.String[i];
                    if (length != 0) {
                        java.lang.System.arraycopy(this.stringValue, 0, strArr, 0, length);
                    }
                    while (length < i - 1) {
                        strArr[length] = codedInputByteBufferNano.readString();
                        codedInputByteBufferNano.readTag();
                        length++;
                    }
                    strArr[length] = codedInputByteBufferNano.readString();
                    this.stringValue = strArr;
                    break;
                default:
                    if (!com.android.framework.protobuf.nano.WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                        return this;
                    }
                    break;
            }
        }
    }

    public static android.stats.devicepolicy.nano.StringList parseFrom(byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
        return (android.stats.devicepolicy.nano.StringList) com.android.framework.protobuf.nano.MessageNano.mergeFrom(new android.stats.devicepolicy.nano.StringList(), bArr);
    }

    public static android.stats.devicepolicy.nano.StringList parseFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException {
        return new android.stats.devicepolicy.nano.StringList().mergeFrom(codedInputByteBufferNano);
    }
}
