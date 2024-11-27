package com.android.internal.telephony.uicc.asn1;

/* loaded from: classes5.dex */
public final class Asn1Node {
    private static final int INT_BYTES = 4;
    private final java.util.List<com.android.internal.telephony.uicc.asn1.Asn1Node> mChildren;
    private final boolean mConstructed;
    private byte[] mDataBytes;
    private int mDataLength;
    private int mDataOffset;
    private int mEncodedLength;
    private final int mTag;
    private static final java.util.List<com.android.internal.telephony.uicc.asn1.Asn1Node> EMPTY_NODE_LIST = java.util.Collections.emptyList();
    private static final byte[] TRUE_BYTES = {-1};
    private static final byte[] FALSE_BYTES = {0};

    public static final class Builder {
        private final java.util.List<com.android.internal.telephony.uicc.asn1.Asn1Node> mChildren;
        private final int mTag;

        private Builder(int i) {
            if (!com.android.internal.telephony.uicc.asn1.Asn1Node.isConstructedTag(i)) {
                throw new java.lang.IllegalArgumentException("Builder should be created for a constructed tag: " + i);
            }
            this.mTag = i;
            this.mChildren = new java.util.ArrayList();
        }

        public com.android.internal.telephony.uicc.asn1.Asn1Node.Builder addChild(com.android.internal.telephony.uicc.asn1.Asn1Node asn1Node) {
            this.mChildren.add(asn1Node);
            return this;
        }

        public com.android.internal.telephony.uicc.asn1.Asn1Node.Builder addChild(com.android.internal.telephony.uicc.asn1.Asn1Node.Builder builder) {
            this.mChildren.add(builder.build());
            return this;
        }

        public com.android.internal.telephony.uicc.asn1.Asn1Node.Builder addChildren(byte[] bArr) throws com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException {
            com.android.internal.telephony.uicc.asn1.Asn1Decoder asn1Decoder = new com.android.internal.telephony.uicc.asn1.Asn1Decoder(bArr, 0, bArr.length);
            while (asn1Decoder.hasNextNode()) {
                this.mChildren.add(asn1Decoder.nextNode());
            }
            return this;
        }

        public com.android.internal.telephony.uicc.asn1.Asn1Node.Builder addChildAsInteger(int i, int i2) {
            if (com.android.internal.telephony.uicc.asn1.Asn1Node.isConstructedTag(i)) {
                throw new java.lang.IllegalStateException("Cannot set value of a constructed tag: " + i);
            }
            byte[] signedIntToBytes = com.android.internal.telephony.uicc.IccUtils.signedIntToBytes(i2);
            addChild(new com.android.internal.telephony.uicc.asn1.Asn1Node(i, signedIntToBytes, 0, signedIntToBytes.length));
            return this;
        }

        public com.android.internal.telephony.uicc.asn1.Asn1Node.Builder addChildAsString(int i, java.lang.String str) {
            if (com.android.internal.telephony.uicc.asn1.Asn1Node.isConstructedTag(i)) {
                throw new java.lang.IllegalStateException("Cannot set value of a constructed tag: " + i);
            }
            byte[] bytes = str.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            addChild(new com.android.internal.telephony.uicc.asn1.Asn1Node(i, bytes, 0, bytes.length));
            return this;
        }

        public com.android.internal.telephony.uicc.asn1.Asn1Node.Builder addChildAsBytes(int i, byte[] bArr) {
            if (com.android.internal.telephony.uicc.asn1.Asn1Node.isConstructedTag(i)) {
                throw new java.lang.IllegalStateException("Cannot set value of a constructed tag: " + i);
            }
            addChild(new com.android.internal.telephony.uicc.asn1.Asn1Node(i, bArr, 0, bArr.length));
            return this;
        }

        public com.android.internal.telephony.uicc.asn1.Asn1Node.Builder addChildAsBytesFromHex(int i, java.lang.String str) {
            return addChildAsBytes(i, com.android.internal.telephony.uicc.IccUtils.hexStringToBytes(str));
        }

        public com.android.internal.telephony.uicc.asn1.Asn1Node.Builder addChildAsBits(int i, int i2) {
            if (com.android.internal.telephony.uicc.asn1.Asn1Node.isConstructedTag(i)) {
                throw new java.lang.IllegalStateException("Cannot set value of a constructed tag: " + i);
            }
            byte[] bArr = new byte[5];
            int reverse = java.lang.Integer.reverse(i2);
            int i3 = 0;
            for (int i4 = 1; i4 < 5; i4++) {
                bArr[i4] = (byte) (reverse >> ((4 - i4) * 8));
                if (bArr[i4] != 0) {
                    i3 = i4;
                }
            }
            int i5 = i3 + 1;
            bArr[0] = com.android.internal.telephony.uicc.IccUtils.countTrailingZeros(bArr[i5 - 1]);
            addChild(new com.android.internal.telephony.uicc.asn1.Asn1Node(i, bArr, 0, i5));
            return this;
        }

        public com.android.internal.telephony.uicc.asn1.Asn1Node.Builder addChildAsBoolean(int i, boolean z) {
            if (com.android.internal.telephony.uicc.asn1.Asn1Node.isConstructedTag(i)) {
                throw new java.lang.IllegalStateException("Cannot set value of a constructed tag: " + i);
            }
            addChild(new com.android.internal.telephony.uicc.asn1.Asn1Node(i, z ? com.android.internal.telephony.uicc.asn1.Asn1Node.TRUE_BYTES : com.android.internal.telephony.uicc.asn1.Asn1Node.FALSE_BYTES, 0, 1));
            return this;
        }

        public com.android.internal.telephony.uicc.asn1.Asn1Node build() {
            return new com.android.internal.telephony.uicc.asn1.Asn1Node(this.mTag, this.mChildren);
        }
    }

    public static com.android.internal.telephony.uicc.asn1.Asn1Node.Builder newBuilder(int i) {
        return new com.android.internal.telephony.uicc.asn1.Asn1Node.Builder(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isConstructedTag(int i) {
        return (com.android.internal.telephony.uicc.IccUtils.unsignedIntToBytes(i)[0] & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) != 0;
    }

    private static int calculateEncodedBytesNumForLength(int i) {
        if (i > 127) {
            return 1 + com.android.internal.telephony.uicc.IccUtils.byteNumForUnsignedInt(i);
        }
        return 1;
    }

    Asn1Node(int i, byte[] bArr, int i2, int i3) {
        this.mTag = i;
        this.mConstructed = isConstructedTag(i);
        this.mDataBytes = bArr;
        this.mDataOffset = i2;
        this.mDataLength = i3;
        this.mChildren = this.mConstructed ? new java.util.ArrayList<>() : EMPTY_NODE_LIST;
        this.mEncodedLength = com.android.internal.telephony.uicc.IccUtils.byteNumForUnsignedInt(this.mTag) + calculateEncodedBytesNumForLength(this.mDataLength) + this.mDataLength;
    }

    private Asn1Node(int i, java.util.List<com.android.internal.telephony.uicc.asn1.Asn1Node> list) {
        this.mTag = i;
        this.mConstructed = true;
        this.mChildren = list;
        this.mDataLength = 0;
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mDataLength += list.get(i2).mEncodedLength;
        }
        this.mEncodedLength = com.android.internal.telephony.uicc.IccUtils.byteNumForUnsignedInt(this.mTag) + calculateEncodedBytesNumForLength(this.mDataLength) + this.mDataLength;
    }

    public int getTag() {
        return this.mTag;
    }

    public boolean isConstructed() {
        return this.mConstructed;
    }

    public boolean hasChild(int i, int... iArr) throws com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException {
        try {
            getChild(i, iArr);
            return true;
        } catch (com.android.internal.telephony.uicc.asn1.TagNotFoundException e) {
            return false;
        }
    }

    public com.android.internal.telephony.uicc.asn1.Asn1Node getChild(int i, int... iArr) throws com.android.internal.telephony.uicc.asn1.TagNotFoundException, com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException {
        if (!this.mConstructed) {
            throw new com.android.internal.telephony.uicc.asn1.TagNotFoundException(i);
        }
        int i2 = i;
        int i3 = 0;
        com.android.internal.telephony.uicc.asn1.Asn1Node asn1Node = this;
        while (asn1Node != null) {
            java.util.List<com.android.internal.telephony.uicc.asn1.Asn1Node> children = asn1Node.getChildren();
            int size = children.size();
            int i4 = 0;
            while (true) {
                if (i4 >= size) {
                    asn1Node = null;
                    break;
                }
                com.android.internal.telephony.uicc.asn1.Asn1Node asn1Node2 = children.get(i4);
                if (asn1Node2.getTag() != i2) {
                    i4++;
                } else {
                    asn1Node = asn1Node2;
                    break;
                }
            }
            if (i3 >= iArr.length) {
                break;
            }
            int i5 = i3 + 1;
            int i6 = iArr[i3];
            i3 = i5;
            i2 = i6;
        }
        if (asn1Node == null) {
            throw new com.android.internal.telephony.uicc.asn1.TagNotFoundException(i2);
        }
        return asn1Node;
    }

    public java.util.List<com.android.internal.telephony.uicc.asn1.Asn1Node> getChildren(int i) throws com.android.internal.telephony.uicc.asn1.TagNotFoundException, com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException {
        if (!this.mConstructed) {
            return EMPTY_NODE_LIST;
        }
        java.util.List<com.android.internal.telephony.uicc.asn1.Asn1Node> children = getChildren();
        if (children.isEmpty()) {
            return EMPTY_NODE_LIST;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = children.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.telephony.uicc.asn1.Asn1Node asn1Node = children.get(i2);
            if (asn1Node.getTag() == i) {
                arrayList.add(asn1Node);
            }
        }
        return arrayList.isEmpty() ? EMPTY_NODE_LIST : arrayList;
    }

    public java.util.List<com.android.internal.telephony.uicc.asn1.Asn1Node> getChildren() throws com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException {
        if (!this.mConstructed) {
            return EMPTY_NODE_LIST;
        }
        if (this.mDataBytes != null) {
            com.android.internal.telephony.uicc.asn1.Asn1Decoder asn1Decoder = new com.android.internal.telephony.uicc.asn1.Asn1Decoder(this.mDataBytes, this.mDataOffset, this.mDataLength);
            while (asn1Decoder.hasNextNode()) {
                this.mChildren.add(asn1Decoder.nextNode());
            }
            this.mDataBytes = null;
            this.mDataOffset = 0;
        }
        return this.mChildren;
    }

    public boolean hasValue() {
        return (this.mConstructed || this.mDataBytes == null) ? false : true;
    }

    public int asInteger() throws com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException {
        if (this.mConstructed) {
            throw new java.lang.IllegalStateException("Cannot get value of a constructed node.");
        }
        if (this.mDataBytes == null) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Data bytes cannot be null.");
        }
        try {
            return com.android.internal.telephony.uicc.IccUtils.bytesToInt(this.mDataBytes, this.mDataOffset, this.mDataLength);
        } catch (java.lang.IllegalArgumentException | java.lang.IndexOutOfBoundsException e) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Cannot parse data bytes.", e);
        }
    }

    public long asRawLong() throws com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException {
        if (this.mConstructed) {
            throw new java.lang.IllegalStateException("Cannot get value of a constructed node.");
        }
        if (this.mDataBytes == null) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Data bytes cannot be null.");
        }
        try {
            return com.android.internal.telephony.uicc.IccUtils.bytesToRawLong(this.mDataBytes, this.mDataOffset, this.mDataLength);
        } catch (java.lang.IllegalArgumentException | java.lang.IndexOutOfBoundsException e) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Cannot parse data bytes.", e);
        }
    }

    public java.lang.String asString() throws com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException {
        if (this.mConstructed) {
            throw new java.lang.IllegalStateException("Cannot get value of a constructed node.");
        }
        if (this.mDataBytes == null) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Data bytes cannot be null.");
        }
        try {
            return new java.lang.String(this.mDataBytes, this.mDataOffset, this.mDataLength, java.nio.charset.StandardCharsets.UTF_8);
        } catch (java.lang.IndexOutOfBoundsException e) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Cannot parse data bytes.", e);
        }
    }

    public byte[] asBytes() throws com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException {
        if (this.mConstructed) {
            throw new java.lang.IllegalStateException("Cannot get value of a constructed node.");
        }
        if (this.mDataBytes == null) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Data bytes cannot be null.");
        }
        byte[] bArr = new byte[this.mDataLength];
        try {
            java.lang.System.arraycopy(this.mDataBytes, this.mDataOffset, bArr, 0, this.mDataLength);
            return bArr;
        } catch (java.lang.IndexOutOfBoundsException e) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Cannot parse data bytes.", e);
        }
    }

    public int asBits() throws com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException {
        if (this.mConstructed) {
            throw new java.lang.IllegalStateException("Cannot get value of a constructed node.");
        }
        if (this.mDataBytes == null) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Data bytes cannot be null.");
        }
        try {
            int bytesToInt = com.android.internal.telephony.uicc.IccUtils.bytesToInt(this.mDataBytes, this.mDataOffset + 1, this.mDataLength - 1);
            for (int i = this.mDataLength - 1; i < 4; i++) {
                bytesToInt <<= 8;
            }
            return java.lang.Integer.reverse(bytesToInt);
        } catch (java.lang.IllegalArgumentException | java.lang.IndexOutOfBoundsException e) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Cannot parse data bytes.", e);
        }
    }

    public boolean asBoolean() throws com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException {
        if (this.mConstructed) {
            throw new java.lang.IllegalStateException("Cannot get value of a constructed node.");
        }
        if (this.mDataBytes == null) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Data bytes cannot be null.");
        }
        if (this.mDataLength != 1) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Cannot parse data bytes as boolean: length=" + this.mDataLength);
        }
        if (this.mDataOffset < 0 || this.mDataOffset >= this.mDataBytes.length) {
            throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Cannot parse data bytes.", new java.lang.ArrayIndexOutOfBoundsException(this.mDataOffset));
        }
        if (this.mDataBytes[this.mDataOffset] == -1) {
            return java.lang.Boolean.TRUE.booleanValue();
        }
        if (this.mDataBytes[this.mDataOffset] == 0) {
            return java.lang.Boolean.FALSE.booleanValue();
        }
        throw new com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException(this.mTag, "Cannot parse data bytes as boolean: " + ((int) this.mDataBytes[this.mDataOffset]));
    }

    public int getEncodedLength() {
        return this.mEncodedLength;
    }

    public int getDataLength() {
        return this.mDataLength;
    }

    public void writeToBytes(byte[] bArr, int i) {
        if (i < 0 || this.mEncodedLength + i > bArr.length) {
            throw new java.lang.IndexOutOfBoundsException("Not enough space to write. Required bytes: " + this.mEncodedLength);
        }
        write(bArr, i);
    }

    public byte[] toBytes() {
        byte[] bArr = new byte[this.mEncodedLength];
        write(bArr, 0);
        return bArr;
    }

    public java.lang.String toHex() {
        return com.android.internal.telephony.uicc.IccUtils.bytesToHexString(toBytes());
    }

    public java.lang.String getHeadAsHex() {
        java.lang.String bytesToHexString = com.android.internal.telephony.uicc.IccUtils.bytesToHexString(com.android.internal.telephony.uicc.IccUtils.unsignedIntToBytes(this.mTag));
        if (this.mDataLength <= 127) {
            return bytesToHexString + com.android.internal.telephony.uicc.IccUtils.byteToHex((byte) this.mDataLength);
        }
        byte[] unsignedIntToBytes = com.android.internal.telephony.uicc.IccUtils.unsignedIntToBytes(this.mDataLength);
        return (bytesToHexString + com.android.internal.telephony.uicc.IccUtils.byteToHex((byte) (unsignedIntToBytes.length | 128))) + com.android.internal.telephony.uicc.IccUtils.bytesToHexString(unsignedIntToBytes);
    }

    private int write(byte[] bArr, int i) {
        int i2;
        int unsignedIntToBytes = i + com.android.internal.telephony.uicc.IccUtils.unsignedIntToBytes(this.mTag, bArr, i);
        if (this.mDataLength <= 127) {
            i2 = unsignedIntToBytes + 1;
            bArr[unsignedIntToBytes] = (byte) this.mDataLength;
        } else {
            int i3 = unsignedIntToBytes + 1;
            int unsignedIntToBytes2 = com.android.internal.telephony.uicc.IccUtils.unsignedIntToBytes(this.mDataLength, bArr, i3);
            bArr[i3 - 1] = (byte) (unsignedIntToBytes2 | 128);
            i2 = unsignedIntToBytes2 + i3;
        }
        if (this.mConstructed && this.mDataBytes == null) {
            int size = this.mChildren.size();
            for (int i4 = 0; i4 < size; i4++) {
                i2 = this.mChildren.get(i4).write(bArr, i2);
            }
            return i2;
        }
        if (this.mDataBytes != null) {
            java.lang.System.arraycopy(this.mDataBytes, this.mDataOffset, bArr, i2, this.mDataLength);
            return i2 + this.mDataLength;
        }
        return i2;
    }
}
