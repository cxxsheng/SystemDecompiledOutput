package com.android.framework.protobuf.nano;

/* loaded from: classes4.dex */
class FieldData implements java.lang.Cloneable {
    private com.android.framework.protobuf.nano.Extension<?, ?> cachedExtension;
    private java.util.List<com.android.framework.protobuf.nano.UnknownFieldData> unknownFieldData;
    private java.lang.Object value;

    /* JADX WARN: Multi-variable type inference failed */
    <T> FieldData(com.android.framework.protobuf.nano.Extension<?, T> extension, T t) {
        this.cachedExtension = extension;
        this.value = t;
    }

    FieldData() {
        this.unknownFieldData = new java.util.ArrayList();
    }

    void addUnknownField(com.android.framework.protobuf.nano.UnknownFieldData unknownFieldData) {
        this.unknownFieldData.add(unknownFieldData);
    }

    com.android.framework.protobuf.nano.UnknownFieldData getUnknownField(int i) {
        if (this.unknownFieldData != null && i < this.unknownFieldData.size()) {
            return this.unknownFieldData.get(i);
        }
        return null;
    }

    int getUnknownFieldSize() {
        if (this.unknownFieldData == null) {
            return 0;
        }
        return this.unknownFieldData.size();
    }

    /* JADX WARN: Multi-variable type inference failed */
    <T> T getValue(com.android.framework.protobuf.nano.Extension<?, T> extension) {
        if (this.value != null) {
            if (this.cachedExtension != extension) {
                throw new java.lang.IllegalStateException("Tried to getExtension with a differernt Extension.");
            }
        } else {
            this.cachedExtension = extension;
            this.value = extension.getValueFrom(this.unknownFieldData);
            this.unknownFieldData = null;
        }
        return (T) this.value;
    }

    /* JADX WARN: Multi-variable type inference failed */
    <T> void setValue(com.android.framework.protobuf.nano.Extension<?, T> extension, T t) {
        this.cachedExtension = extension;
        this.value = t;
        this.unknownFieldData = null;
    }

    int computeSerializedSize() {
        if (this.value != null) {
            return this.cachedExtension.computeSerializedSize(this.value);
        }
        java.util.Iterator<com.android.framework.protobuf.nano.UnknownFieldData> it = this.unknownFieldData.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += it.next().computeSerializedSize();
        }
        return i;
    }

    void writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano codedOutputByteBufferNano) throws java.io.IOException {
        if (this.value != null) {
            this.cachedExtension.writeTo(this.value, codedOutputByteBufferNano);
            return;
        }
        java.util.Iterator<com.android.framework.protobuf.nano.UnknownFieldData> it = this.unknownFieldData.iterator();
        while (it.hasNext()) {
            it.next().writeTo(codedOutputByteBufferNano);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.framework.protobuf.nano.FieldData)) {
            return false;
        }
        com.android.framework.protobuf.nano.FieldData fieldData = (com.android.framework.protobuf.nano.FieldData) obj;
        if (this.value != null && fieldData.value != null) {
            if (this.cachedExtension != fieldData.cachedExtension) {
                return false;
            }
            if (!this.cachedExtension.clazz.isArray()) {
                return this.value.equals(fieldData.value);
            }
            if (this.value instanceof byte[]) {
                return java.util.Arrays.equals((byte[]) this.value, (byte[]) fieldData.value);
            }
            if (this.value instanceof int[]) {
                return java.util.Arrays.equals((int[]) this.value, (int[]) fieldData.value);
            }
            if (this.value instanceof long[]) {
                return java.util.Arrays.equals((long[]) this.value, (long[]) fieldData.value);
            }
            if (this.value instanceof float[]) {
                return java.util.Arrays.equals((float[]) this.value, (float[]) fieldData.value);
            }
            if (this.value instanceof double[]) {
                return java.util.Arrays.equals((double[]) this.value, (double[]) fieldData.value);
            }
            if (this.value instanceof boolean[]) {
                return java.util.Arrays.equals((boolean[]) this.value, (boolean[]) fieldData.value);
            }
            return java.util.Arrays.deepEquals((java.lang.Object[]) this.value, (java.lang.Object[]) fieldData.value);
        }
        if (this.unknownFieldData != null && fieldData.unknownFieldData != null) {
            return this.unknownFieldData.equals(fieldData.unknownFieldData);
        }
        try {
            return java.util.Arrays.equals(toByteArray(), fieldData.toByteArray());
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    public int hashCode() {
        try {
            return 527 + java.util.Arrays.hashCode(toByteArray());
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    private byte[] toByteArray() throws java.io.IOException {
        byte[] bArr = new byte[computeSerializedSize()];
        writeTo(com.android.framework.protobuf.nano.CodedOutputByteBufferNano.newInstance(bArr));
        return bArr;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final com.android.framework.protobuf.nano.FieldData m6595clone() {
        com.android.framework.protobuf.nano.FieldData fieldData = new com.android.framework.protobuf.nano.FieldData();
        try {
            fieldData.cachedExtension = this.cachedExtension;
            if (this.unknownFieldData == null) {
                fieldData.unknownFieldData = null;
            } else {
                fieldData.unknownFieldData.addAll(this.unknownFieldData);
            }
            if (this.value != null) {
                if (this.value instanceof com.android.framework.protobuf.nano.MessageNano) {
                    fieldData.value = ((com.android.framework.protobuf.nano.MessageNano) this.value).mo6593clone();
                } else if (this.value instanceof byte[]) {
                    fieldData.value = ((byte[]) this.value).clone();
                } else {
                    int i = 0;
                    if (this.value instanceof byte[][]) {
                        byte[][] bArr = (byte[][]) this.value;
                        byte[][] bArr2 = new byte[bArr.length][];
                        fieldData.value = bArr2;
                        while (i < bArr.length) {
                            bArr2[i] = (byte[]) bArr[i].clone();
                            i++;
                        }
                    } else if (this.value instanceof boolean[]) {
                        fieldData.value = ((boolean[]) this.value).clone();
                    } else if (this.value instanceof int[]) {
                        fieldData.value = ((int[]) this.value).clone();
                    } else if (this.value instanceof long[]) {
                        fieldData.value = ((long[]) this.value).clone();
                    } else if (this.value instanceof float[]) {
                        fieldData.value = ((float[]) this.value).clone();
                    } else if (this.value instanceof double[]) {
                        fieldData.value = ((double[]) this.value).clone();
                    } else if (this.value instanceof com.android.framework.protobuf.nano.MessageNano[]) {
                        com.android.framework.protobuf.nano.MessageNano[] messageNanoArr = (com.android.framework.protobuf.nano.MessageNano[]) this.value;
                        com.android.framework.protobuf.nano.MessageNano[] messageNanoArr2 = new com.android.framework.protobuf.nano.MessageNano[messageNanoArr.length];
                        fieldData.value = messageNanoArr2;
                        while (i < messageNanoArr.length) {
                            messageNanoArr2[i] = messageNanoArr[i].mo6593clone();
                            i++;
                        }
                    }
                }
            }
            return fieldData;
        } catch (java.lang.CloneNotSupportedException e) {
            throw new java.lang.AssertionError(e);
        }
    }
}
