package com.android.framework.protobuf.nano;

/* loaded from: classes4.dex */
public abstract class MessageNano {
    protected volatile int cachedSize = -1;

    public abstract com.android.framework.protobuf.nano.MessageNano mergeFrom(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano) throws java.io.IOException;

    public int getCachedSize() {
        if (this.cachedSize < 0) {
            getSerializedSize();
        }
        return this.cachedSize;
    }

    public int getSerializedSize() {
        int computeSerializedSize = computeSerializedSize();
        this.cachedSize = computeSerializedSize;
        return computeSerializedSize;
    }

    protected int computeSerializedSize() {
        return 0;
    }

    public void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
    }

    public static final byte[] toByteArray(com.android.framework.protobuf.nano.MessageNano messageNano) {
        int serializedSize = messageNano.getSerializedSize();
        byte[] bArr = new byte[serializedSize];
        toByteArray(messageNano, bArr, 0, serializedSize);
        return bArr;
    }

    public static final void toByteArray(com.android.framework.protobuf.nano.MessageNano messageNano, byte[] bArr, int i, int i2) {
        try {
            com.android.framework.protobuf.nano.CodedOutputByteBufferNano newInstance = com.android.framework.protobuf.nano.CodedOutputByteBufferNano.newInstance(bArr, i, i2);
            messageNano.writeTo(newInstance);
            newInstance.checkNoSpaceLeft();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public static final <T extends com.android.framework.protobuf.nano.MessageNano> T mergeFrom(T t, byte[] bArr) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
        return (T) mergeFrom(t, bArr, 0, bArr.length);
    }

    public static final <T extends com.android.framework.protobuf.nano.MessageNano> T mergeFrom(T t, byte[] bArr, int i, int i2) throws com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException {
        try {
            com.android.framework.protobuf.nano.CodedInputByteBufferNano newInstance = com.android.framework.protobuf.nano.CodedInputByteBufferNano.newInstance(bArr, i, i2);
            t.mergeFrom(newInstance);
            newInstance.checkLastTagWas(0);
            return t;
        } catch (com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException e) {
            throw e;
        } catch (java.io.IOException e2) {
            throw new java.lang.RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
    }

    public static final boolean messageNanoEquals(com.android.framework.protobuf.nano.MessageNano messageNano, com.android.framework.protobuf.nano.MessageNano messageNano2) {
        int serializedSize;
        if (messageNano == messageNano2) {
            return true;
        }
        if (messageNano == null || messageNano2 == null || messageNano.getClass() != messageNano2.getClass() || messageNano2.getSerializedSize() != (serializedSize = messageNano.getSerializedSize())) {
            return false;
        }
        byte[] bArr = new byte[serializedSize];
        byte[] bArr2 = new byte[serializedSize];
        toByteArray(messageNano, bArr, 0, serializedSize);
        toByteArray(messageNano2, bArr2, 0, serializedSize);
        return java.util.Arrays.equals(bArr, bArr2);
    }

    public java.lang.String toString() {
        return com.android.framework.protobuf.nano.MessageNanoPrinter.print(this);
    }

    @Override // 
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public com.android.framework.protobuf.nano.MessageNano mo6593clone() throws java.lang.CloneNotSupportedException {
        return (com.android.framework.protobuf.nano.MessageNano) super.clone();
    }
}
