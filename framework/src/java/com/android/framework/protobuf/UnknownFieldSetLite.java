package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public final class UnknownFieldSetLite {
    private static final com.android.framework.protobuf.UnknownFieldSetLite DEFAULT_INSTANCE = new com.android.framework.protobuf.UnknownFieldSetLite(0, new int[0], new java.lang.Object[0], false);
    private static final int MIN_CAPACITY = 8;
    private int count;
    private boolean isMutable;
    private int memoizedSerializedSize;
    private java.lang.Object[] objects;
    private int[] tags;

    public static com.android.framework.protobuf.UnknownFieldSetLite getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    static com.android.framework.protobuf.UnknownFieldSetLite newInstance() {
        return new com.android.framework.protobuf.UnknownFieldSetLite();
    }

    static com.android.framework.protobuf.UnknownFieldSetLite mutableCopyOf(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite, com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite2) {
        int i = unknownFieldSetLite.count + unknownFieldSetLite2.count;
        int[] copyOf = java.util.Arrays.copyOf(unknownFieldSetLite.tags, i);
        java.lang.System.arraycopy(unknownFieldSetLite2.tags, 0, copyOf, unknownFieldSetLite.count, unknownFieldSetLite2.count);
        java.lang.Object[] copyOf2 = java.util.Arrays.copyOf(unknownFieldSetLite.objects, i);
        java.lang.System.arraycopy(unknownFieldSetLite2.objects, 0, copyOf2, unknownFieldSetLite.count, unknownFieldSetLite2.count);
        return new com.android.framework.protobuf.UnknownFieldSetLite(i, copyOf, copyOf2, true);
    }

    private UnknownFieldSetLite() {
        this(0, new int[8], new java.lang.Object[8], true);
    }

    private UnknownFieldSetLite(int i, int[] iArr, java.lang.Object[] objArr, boolean z) {
        this.memoizedSerializedSize = -1;
        this.count = i;
        this.tags = iArr;
        this.objects = objArr;
        this.isMutable = z;
    }

    public void makeImmutable() {
        this.isMutable = false;
    }

    void checkMutable() {
        if (!this.isMutable) {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public void writeTo(com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
        for (int i = 0; i < this.count; i++) {
            int i2 = this.tags[i];
            int tagFieldNumber = com.android.framework.protobuf.WireFormat.getTagFieldNumber(i2);
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(i2)) {
                case 0:
                    codedOutputStream.writeUInt64(tagFieldNumber, ((java.lang.Long) this.objects[i]).longValue());
                    break;
                case 1:
                    codedOutputStream.writeFixed64(tagFieldNumber, ((java.lang.Long) this.objects[i]).longValue());
                    break;
                case 2:
                    codedOutputStream.writeBytes(tagFieldNumber, (com.android.framework.protobuf.ByteString) this.objects[i]);
                    break;
                case 3:
                    codedOutputStream.writeTag(tagFieldNumber, 3);
                    ((com.android.framework.protobuf.UnknownFieldSetLite) this.objects[i]).writeTo(codedOutputStream);
                    codedOutputStream.writeTag(tagFieldNumber, 4);
                    break;
                case 4:
                default:
                    throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
                case 5:
                    codedOutputStream.writeFixed32(tagFieldNumber, ((java.lang.Integer) this.objects[i]).intValue());
                    break;
            }
        }
    }

    public void writeAsMessageSetTo(com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
        for (int i = 0; i < this.count; i++) {
            codedOutputStream.writeRawMessageSetExtension(com.android.framework.protobuf.WireFormat.getTagFieldNumber(this.tags[i]), (com.android.framework.protobuf.ByteString) this.objects[i]);
        }
    }

    void writeAsMessageSetTo(com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (writer.fieldOrder() == com.android.framework.protobuf.Writer.FieldOrder.DESCENDING) {
            for (int i = this.count - 1; i >= 0; i--) {
                writer.writeMessageSetItem(com.android.framework.protobuf.WireFormat.getTagFieldNumber(this.tags[i]), this.objects[i]);
            }
            return;
        }
        for (int i2 = 0; i2 < this.count; i2++) {
            writer.writeMessageSetItem(com.android.framework.protobuf.WireFormat.getTagFieldNumber(this.tags[i2]), this.objects[i2]);
        }
    }

    public void writeTo(com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (this.count == 0) {
            return;
        }
        if (writer.fieldOrder() == com.android.framework.protobuf.Writer.FieldOrder.ASCENDING) {
            for (int i = 0; i < this.count; i++) {
                writeField(this.tags[i], this.objects[i], writer);
            }
            return;
        }
        for (int i2 = this.count - 1; i2 >= 0; i2--) {
            writeField(this.tags[i2], this.objects[i2], writer);
        }
    }

    private static void writeField(int i, java.lang.Object obj, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        int tagFieldNumber = com.android.framework.protobuf.WireFormat.getTagFieldNumber(i);
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(i)) {
            case 0:
                writer.writeInt64(tagFieldNumber, ((java.lang.Long) obj).longValue());
                return;
            case 1:
                writer.writeFixed64(tagFieldNumber, ((java.lang.Long) obj).longValue());
                return;
            case 2:
                writer.writeBytes(tagFieldNumber, (com.android.framework.protobuf.ByteString) obj);
                return;
            case 3:
                if (writer.fieldOrder() == com.android.framework.protobuf.Writer.FieldOrder.ASCENDING) {
                    writer.writeStartGroup(tagFieldNumber);
                    ((com.android.framework.protobuf.UnknownFieldSetLite) obj).writeTo(writer);
                    writer.writeEndGroup(tagFieldNumber);
                    return;
                } else {
                    writer.writeEndGroup(tagFieldNumber);
                    ((com.android.framework.protobuf.UnknownFieldSetLite) obj).writeTo(writer);
                    writer.writeStartGroup(tagFieldNumber);
                    return;
                }
            case 4:
            default:
                throw new java.lang.RuntimeException(com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType());
            case 5:
                writer.writeFixed32(tagFieldNumber, ((java.lang.Integer) obj).intValue());
                return;
        }
    }

    public int getSerializedSizeAsMessageSet() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.count; i3++) {
            i2 += com.android.framework.protobuf.CodedOutputStream.computeRawMessageSetExtensionSize(com.android.framework.protobuf.WireFormat.getTagFieldNumber(this.tags[i3]), (com.android.framework.protobuf.ByteString) this.objects[i3]);
        }
        this.memoizedSerializedSize = i2;
        return i2;
    }

    public int getSerializedSize() {
        int computeUInt64Size;
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.count; i3++) {
            int i4 = this.tags[i3];
            int tagFieldNumber = com.android.framework.protobuf.WireFormat.getTagFieldNumber(i4);
            switch (com.android.framework.protobuf.WireFormat.getTagWireType(i4)) {
                case 0:
                    computeUInt64Size = com.android.framework.protobuf.CodedOutputStream.computeUInt64Size(tagFieldNumber, ((java.lang.Long) this.objects[i3]).longValue());
                    break;
                case 1:
                    computeUInt64Size = com.android.framework.protobuf.CodedOutputStream.computeFixed64Size(tagFieldNumber, ((java.lang.Long) this.objects[i3]).longValue());
                    break;
                case 2:
                    computeUInt64Size = com.android.framework.protobuf.CodedOutputStream.computeBytesSize(tagFieldNumber, (com.android.framework.protobuf.ByteString) this.objects[i3]);
                    break;
                case 3:
                    computeUInt64Size = (com.android.framework.protobuf.CodedOutputStream.computeTagSize(tagFieldNumber) * 2) + ((com.android.framework.protobuf.UnknownFieldSetLite) this.objects[i3]).getSerializedSize();
                    break;
                case 4:
                default:
                    throw new java.lang.IllegalStateException(com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType());
                case 5:
                    computeUInt64Size = com.android.framework.protobuf.CodedOutputStream.computeFixed32Size(tagFieldNumber, ((java.lang.Integer) this.objects[i3]).intValue());
                    break;
            }
            i2 += computeUInt64Size;
        }
        this.memoizedSerializedSize = i2;
        return i2;
    }

    private static boolean tagsEquals(int[] iArr, int[] iArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    private static boolean objectsEquals(java.lang.Object[] objArr, java.lang.Object[] objArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (!objArr[i2].equals(objArr2[i2])) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof com.android.framework.protobuf.UnknownFieldSetLite)) {
            return false;
        }
        com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite = (com.android.framework.protobuf.UnknownFieldSetLite) obj;
        if (this.count == unknownFieldSetLite.count && tagsEquals(this.tags, unknownFieldSetLite.tags, this.count) && objectsEquals(this.objects, unknownFieldSetLite.objects, this.count)) {
            return true;
        }
        return false;
    }

    private static int hashCode(int[] iArr, int i) {
        int i2 = 17;
        for (int i3 = 0; i3 < i; i3++) {
            i2 = (i2 * 31) + iArr[i3];
        }
        return i2;
    }

    private static int hashCode(java.lang.Object[] objArr, int i) {
        int i2 = 17;
        for (int i3 = 0; i3 < i; i3++) {
            i2 = (i2 * 31) + objArr[i3].hashCode();
        }
        return i2;
    }

    public int hashCode() {
        return ((((527 + this.count) * 31) + hashCode(this.tags, this.count)) * 31) + hashCode(this.objects, this.count);
    }

    final void printWithIndent(java.lang.StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.count; i2++) {
            com.android.framework.protobuf.MessageLiteToString.printField(sb, i, java.lang.String.valueOf(com.android.framework.protobuf.WireFormat.getTagFieldNumber(this.tags[i2])), this.objects[i2]);
        }
    }

    void storeField(int i, java.lang.Object obj) {
        checkMutable();
        ensureCapacity(this.count + 1);
        this.tags[this.count] = i;
        this.objects[this.count] = obj;
        this.count++;
    }

    private void ensureCapacity(int i) {
        if (i > this.tags.length) {
            int i2 = this.count + (this.count / 2);
            if (i2 >= i) {
                i = i2;
            }
            if (i < 8) {
                i = 8;
            }
            this.tags = java.util.Arrays.copyOf(this.tags, i);
            this.objects = java.util.Arrays.copyOf(this.objects, i);
        }
    }

    boolean mergeFieldFrom(int i, com.android.framework.protobuf.CodedInputStream codedInputStream) throws java.io.IOException {
        checkMutable();
        int tagFieldNumber = com.android.framework.protobuf.WireFormat.getTagFieldNumber(i);
        switch (com.android.framework.protobuf.WireFormat.getTagWireType(i)) {
            case 0:
                storeField(i, java.lang.Long.valueOf(codedInputStream.readInt64()));
                return true;
            case 1:
                storeField(i, java.lang.Long.valueOf(codedInputStream.readFixed64()));
                return true;
            case 2:
                storeField(i, codedInputStream.readBytes());
                return true;
            case 3:
                com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite = new com.android.framework.protobuf.UnknownFieldSetLite();
                unknownFieldSetLite.mergeFrom(codedInputStream);
                codedInputStream.checkLastTagWas(com.android.framework.protobuf.WireFormat.makeTag(tagFieldNumber, 4));
                storeField(i, unknownFieldSetLite);
                return true;
            case 4:
                return false;
            case 5:
                storeField(i, java.lang.Integer.valueOf(codedInputStream.readFixed32()));
                return true;
            default:
                throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidWireType();
        }
    }

    com.android.framework.protobuf.UnknownFieldSetLite mergeVarintField(int i, int i2) {
        checkMutable();
        if (i == 0) {
            throw new java.lang.IllegalArgumentException("Zero is not a valid field number.");
        }
        storeField(com.android.framework.protobuf.WireFormat.makeTag(i, 0), java.lang.Long.valueOf(i2));
        return this;
    }

    com.android.framework.protobuf.UnknownFieldSetLite mergeLengthDelimitedField(int i, com.android.framework.protobuf.ByteString byteString) {
        checkMutable();
        if (i == 0) {
            throw new java.lang.IllegalArgumentException("Zero is not a valid field number.");
        }
        storeField(com.android.framework.protobuf.WireFormat.makeTag(i, 2), byteString);
        return this;
    }

    private com.android.framework.protobuf.UnknownFieldSetLite mergeFrom(com.android.framework.protobuf.CodedInputStream codedInputStream) throws java.io.IOException {
        int readTag;
        do {
            readTag = codedInputStream.readTag();
            if (readTag == 0) {
                break;
            }
        } while (mergeFieldFrom(readTag, codedInputStream));
        return this;
    }

    com.android.framework.protobuf.UnknownFieldSetLite mergeFrom(com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite) {
        if (unknownFieldSetLite.equals(getDefaultInstance())) {
            return this;
        }
        checkMutable();
        int i = this.count + unknownFieldSetLite.count;
        ensureCapacity(i);
        java.lang.System.arraycopy(unknownFieldSetLite.tags, 0, this.tags, this.count, unknownFieldSetLite.count);
        java.lang.System.arraycopy(unknownFieldSetLite.objects, 0, this.objects, this.count, unknownFieldSetLite.count);
        this.count = i;
        return this;
    }
}
