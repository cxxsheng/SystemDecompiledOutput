package com.android.framework.protobuf.nano;

/* loaded from: classes4.dex */
public abstract class ExtendableMessageNano<M extends com.android.framework.protobuf.nano.ExtendableMessageNano<M>> extends com.android.framework.protobuf.nano.MessageNano {
    protected com.android.framework.protobuf.nano.FieldArray unknownFieldData;

    @Override // com.android.framework.protobuf.nano.MessageNano
    protected int computeSerializedSize() {
        if (this.unknownFieldData == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.unknownFieldData.size(); i2++) {
            i += this.unknownFieldData.dataAt(i2).computeSerializedSize();
        }
        return i;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
        if (this.unknownFieldData == null) {
            return;
        }
        for (int i = 0; i < this.unknownFieldData.size(); i++) {
            this.unknownFieldData.dataAt(i).writeTo(codedOutputByteBufferNano);
        }
    }

    public final boolean hasExtension(com.android.framework.protobuf.nano.Extension<M, ?> extension) {
        return (this.unknownFieldData == null || this.unknownFieldData.get(com.android.framework.protobuf.nano.WireFormatNano.getTagFieldNumber(extension.tag)) == null) ? false : true;
    }

    public final <T> T getExtension(com.android.framework.protobuf.nano.Extension<M, T> extension) {
        com.android.framework.protobuf.nano.FieldData fieldData;
        if (this.unknownFieldData == null || (fieldData = this.unknownFieldData.get(com.android.framework.protobuf.nano.WireFormatNano.getTagFieldNumber(extension.tag))) == null) {
            return null;
        }
        return (T) fieldData.getValue(extension);
    }

    public final <T> M setExtension(com.android.framework.protobuf.nano.Extension<M, T> extension, T t) {
        int tagFieldNumber = com.android.framework.protobuf.nano.WireFormatNano.getTagFieldNumber(extension.tag);
        com.android.framework.protobuf.nano.FieldData fieldData = null;
        if (t == null) {
            if (this.unknownFieldData != null) {
                this.unknownFieldData.remove(tagFieldNumber);
                if (this.unknownFieldData.isEmpty()) {
                    this.unknownFieldData = null;
                }
            }
        } else {
            if (this.unknownFieldData == null) {
                this.unknownFieldData = new com.android.framework.protobuf.nano.FieldArray();
            } else {
                fieldData = this.unknownFieldData.get(tagFieldNumber);
            }
            if (fieldData == null) {
                this.unknownFieldData.put(tagFieldNumber, new com.android.framework.protobuf.nano.FieldData(extension, t));
            } else {
                fieldData.setValue(extension, t);
            }
        }
        return this;
    }

    protected final boolean storeUnknownField(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano, int i) throws java.io.IOException {
        com.android.framework.protobuf.nano.FieldData fieldData;
        int position = codedInputByteBufferNano.getPosition();
        if (!codedInputByteBufferNano.skipField(i)) {
            return false;
        }
        int tagFieldNumber = com.android.framework.protobuf.nano.WireFormatNano.getTagFieldNumber(i);
        com.android.framework.protobuf.nano.UnknownFieldData unknownFieldData = new com.android.framework.protobuf.nano.UnknownFieldData(i, codedInputByteBufferNano.getData(position, codedInputByteBufferNano.getPosition() - position));
        if (this.unknownFieldData == null) {
            this.unknownFieldData = new com.android.framework.protobuf.nano.FieldArray();
            fieldData = null;
        } else {
            fieldData = this.unknownFieldData.get(tagFieldNumber);
        }
        if (fieldData == null) {
            fieldData = new com.android.framework.protobuf.nano.FieldData();
            this.unknownFieldData.put(tagFieldNumber, fieldData);
        }
        fieldData.addUnknownField(unknownFieldData);
        return true;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    /* renamed from: clone */
    public M mo6593clone() throws java.lang.CloneNotSupportedException {
        M m = (M) super.mo6593clone();
        com.android.framework.protobuf.nano.InternalNano.cloneUnknownFieldData(this, m);
        return m;
    }
}
