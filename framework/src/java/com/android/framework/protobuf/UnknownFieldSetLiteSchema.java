package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
class UnknownFieldSetLiteSchema extends com.android.framework.protobuf.UnknownFieldSchema<com.android.framework.protobuf.UnknownFieldSetLite, com.android.framework.protobuf.UnknownFieldSetLite> {
    UnknownFieldSetLiteSchema() {
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    boolean shouldDiscardUnknownFields(com.android.framework.protobuf.Reader reader) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public com.android.framework.protobuf.UnknownFieldSetLite newBuilder() {
        return com.android.framework.protobuf.UnknownFieldSetLite.newInstance();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void addVarint(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite, int i, long j) {
        unknownFieldSetLite.storeField(com.android.framework.protobuf.WireFormat.makeTag(i, 0), java.lang.Long.valueOf(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void addFixed32(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite, int i, int i2) {
        unknownFieldSetLite.storeField(com.android.framework.protobuf.WireFormat.makeTag(i, 5), java.lang.Integer.valueOf(i2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void addFixed64(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite, int i, long j) {
        unknownFieldSetLite.storeField(com.android.framework.protobuf.WireFormat.makeTag(i, 1), java.lang.Long.valueOf(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void addLengthDelimited(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite, int i, com.android.framework.protobuf.ByteString byteString) {
        unknownFieldSetLite.storeField(com.android.framework.protobuf.WireFormat.makeTag(i, 2), byteString);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void addGroup(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite, int i, com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite2) {
        unknownFieldSetLite.storeField(com.android.framework.protobuf.WireFormat.makeTag(i, 3), unknownFieldSetLite2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public com.android.framework.protobuf.UnknownFieldSetLite toImmutable(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite) {
        unknownFieldSetLite.makeImmutable();
        return unknownFieldSetLite;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void setToMessage(java.lang.Object obj, com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite) {
        ((com.android.framework.protobuf.GeneratedMessageLite) obj).unknownFields = unknownFieldSetLite;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public com.android.framework.protobuf.UnknownFieldSetLite getFromMessage(java.lang.Object obj) {
        return ((com.android.framework.protobuf.GeneratedMessageLite) obj).unknownFields;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public com.android.framework.protobuf.UnknownFieldSetLite getBuilderFromMessage(java.lang.Object obj) {
        com.android.framework.protobuf.UnknownFieldSetLite fromMessage = getFromMessage(obj);
        if (fromMessage == com.android.framework.protobuf.UnknownFieldSetLite.getDefaultInstance()) {
            com.android.framework.protobuf.UnknownFieldSetLite newInstance = com.android.framework.protobuf.UnknownFieldSetLite.newInstance();
            setToMessage(obj, newInstance);
            return newInstance;
        }
        return fromMessage;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void setBuilderToMessage(java.lang.Object obj, com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite) {
        setToMessage(obj, unknownFieldSetLite);
    }

    @Override // com.android.framework.protobuf.UnknownFieldSchema
    void makeImmutable(java.lang.Object obj) {
        getFromMessage(obj).makeImmutable();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void writeTo(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        unknownFieldSetLite.writeTo(writer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public void writeAsMessageSetTo(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        unknownFieldSetLite.writeAsMessageSetTo(writer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public com.android.framework.protobuf.UnknownFieldSetLite merge(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite, com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite2) {
        if (com.android.framework.protobuf.UnknownFieldSetLite.getDefaultInstance().equals(unknownFieldSetLite2)) {
            return unknownFieldSetLite;
        }
        if (com.android.framework.protobuf.UnknownFieldSetLite.getDefaultInstance().equals(unknownFieldSetLite)) {
            return com.android.framework.protobuf.UnknownFieldSetLite.mutableCopyOf(unknownFieldSetLite, unknownFieldSetLite2);
        }
        return unknownFieldSetLite.mergeFrom(unknownFieldSetLite2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public int getSerializedSize(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite) {
        return unknownFieldSetLite.getSerializedSize();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.framework.protobuf.UnknownFieldSchema
    public int getSerializedSizeAsMessageSet(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite) {
        return unknownFieldSetLite.getSerializedSizeAsMessageSet();
    }
}
