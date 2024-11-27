package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public abstract class ASN1Set extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive implements com.android.internal.org.bouncycastle.util.Iterable<com.android.internal.org.bouncycastle.asn1.ASN1Encodable> {
    protected final com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] elements;
    protected final boolean isSorted;

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    abstract void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException;

    public static com.android.internal.org.bouncycastle.asn1.ASN1Set getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Set)) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1Set) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1SetParser) {
            return getInstance(((com.android.internal.org.bouncycastle.asn1.ASN1SetParser) obj).toASN1Primitive());
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("failed to construct set from byte[]: " + e.getMessage());
            }
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Encodable) {
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive = ((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Set) {
                return (com.android.internal.org.bouncycastle.asn1.ASN1Set) aSN1Primitive;
            }
        }
        throw new java.lang.IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Set getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (z) {
            if (!aSN1TaggedObject.isExplicit()) {
                throw new java.lang.IllegalArgumentException("object implicit - explicit expected.");
            }
            return getInstance(aSN1TaggedObject.getObject());
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive object = aSN1TaggedObject.getObject();
        if (aSN1TaggedObject.isExplicit()) {
            if (aSN1TaggedObject instanceof com.android.internal.org.bouncycastle.asn1.BERTaggedObject) {
                return new com.android.internal.org.bouncycastle.asn1.BERSet(object);
            }
            return new com.android.internal.org.bouncycastle.asn1.DLSet(object);
        }
        if (object instanceof com.android.internal.org.bouncycastle.asn1.ASN1Set) {
            com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set = (com.android.internal.org.bouncycastle.asn1.ASN1Set) object;
            if (aSN1TaggedObject instanceof com.android.internal.org.bouncycastle.asn1.BERTaggedObject) {
                return aSN1Set;
            }
            return (com.android.internal.org.bouncycastle.asn1.ASN1Set) aSN1Set.toDLObject();
        }
        if (object instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] arrayInternal = ((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) object).toArrayInternal();
            if (aSN1TaggedObject instanceof com.android.internal.org.bouncycastle.asn1.BERTaggedObject) {
                return new com.android.internal.org.bouncycastle.asn1.BERSet(false, arrayInternal);
            }
            return new com.android.internal.org.bouncycastle.asn1.DLSet(false, arrayInternal);
        }
        throw new java.lang.IllegalArgumentException("unknown object in getInstance: " + aSN1TaggedObject.getClass().getName());
    }

    protected ASN1Set() {
        this.elements = com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector.EMPTY_ELEMENTS;
        this.isSorted = true;
    }

    protected ASN1Set(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable == null) {
            throw new java.lang.NullPointerException("'element' cannot be null");
        }
        this.elements = new com.android.internal.org.bouncycastle.asn1.ASN1Encodable[]{aSN1Encodable};
        this.isSorted = true;
    }

    protected ASN1Set(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector, boolean z) {
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] takeElements;
        if (aSN1EncodableVector == null) {
            throw new java.lang.NullPointerException("'elementVector' cannot be null");
        }
        if (z && aSN1EncodableVector.size() >= 2) {
            takeElements = aSN1EncodableVector.copyElements();
            sort(takeElements);
        } else {
            takeElements = aSN1EncodableVector.takeElements();
        }
        this.elements = takeElements;
        this.isSorted = z || takeElements.length < 2;
    }

    protected ASN1Set(com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr, boolean z) {
        if (com.android.internal.org.bouncycastle.util.Arrays.isNullOrContainsNull(aSN1EncodableArr)) {
            throw new java.lang.NullPointerException("'elements' cannot be null, or contain null");
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] cloneElements = com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector.cloneElements(aSN1EncodableArr);
        if (z && cloneElements.length >= 2) {
            sort(cloneElements);
        }
        this.elements = cloneElements;
        this.isSorted = z || cloneElements.length < 2;
    }

    ASN1Set(boolean z, com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr) {
        this.elements = aSN1EncodableArr;
        this.isSorted = z || aSN1EncodableArr.length < 2;
    }

    public java.util.Enumeration getObjects() {
        return new java.util.Enumeration() { // from class: com.android.internal.org.bouncycastle.asn1.ASN1Set.1
            private int pos = 0;

            @Override // java.util.Enumeration
            public boolean hasMoreElements() {
                return this.pos < com.android.internal.org.bouncycastle.asn1.ASN1Set.this.elements.length;
            }

            @Override // java.util.Enumeration
            public java.lang.Object nextElement() {
                if (this.pos < com.android.internal.org.bouncycastle.asn1.ASN1Set.this.elements.length) {
                    com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr = com.android.internal.org.bouncycastle.asn1.ASN1Set.this.elements;
                    int i = this.pos;
                    this.pos = i + 1;
                    return aSN1EncodableArr[i];
                }
                throw new java.util.NoSuchElementException();
            }
        };
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getObjectAt(int i) {
        return this.elements[i];
    }

    public int size() {
        return this.elements.length;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] toArray() {
        return com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector.cloneElements(this.elements);
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1SetParser parser() {
        final int size = size();
        return new com.android.internal.org.bouncycastle.asn1.ASN1SetParser() { // from class: com.android.internal.org.bouncycastle.asn1.ASN1Set.2
            private int pos = 0;

            @Override // com.android.internal.org.bouncycastle.asn1.ASN1SetParser
            public com.android.internal.org.bouncycastle.asn1.ASN1Encodable readObject() throws java.io.IOException {
                if (size == this.pos) {
                    return null;
                }
                com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr = com.android.internal.org.bouncycastle.asn1.ASN1Set.this.elements;
                int i = this.pos;
                this.pos = i + 1;
                com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable = aSN1EncodableArr[i];
                if (aSN1Encodable instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
                    return ((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) aSN1Encodable).parser();
                }
                if (aSN1Encodable instanceof com.android.internal.org.bouncycastle.asn1.ASN1Set) {
                    return ((com.android.internal.org.bouncycastle.asn1.ASN1Set) aSN1Encodable).parser();
                }
                return aSN1Encodable;
            }

            @Override // com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
            public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getLoadedObject() {
                return com.android.internal.org.bouncycastle.asn1.ASN1Set.this;
            }

            @Override // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
            public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
                return com.android.internal.org.bouncycastle.asn1.ASN1Set.this;
            }
        };
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        int length = this.elements.length;
        int i = length + 1;
        while (true) {
            length--;
            if (length >= 0) {
                i += this.elements[length].toASN1Primitive().hashCode();
            } else {
                return i;
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr;
        if (this.isSorted) {
            aSN1EncodableArr = this.elements;
        } else {
            aSN1EncodableArr = (com.android.internal.org.bouncycastle.asn1.ASN1Encodable[]) this.elements.clone();
            sort(aSN1EncodableArr);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSet(true, aSN1EncodableArr);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return new com.android.internal.org.bouncycastle.asn1.DLSet(this.isSorted, this.elements);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Set)) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set = (com.android.internal.org.bouncycastle.asn1.ASN1Set) aSN1Primitive;
        int size = size();
        if (aSN1Set.size() != size) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.DERSet dERSet = (com.android.internal.org.bouncycastle.asn1.DERSet) toDERObject();
        com.android.internal.org.bouncycastle.asn1.DERSet dERSet2 = (com.android.internal.org.bouncycastle.asn1.DERSet) aSN1Set.toDERObject();
        for (int i = 0; i < size; i++) {
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive2 = dERSet.elements[i].toASN1Primitive();
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive3 = dERSet2.elements[i].toASN1Primitive();
            if (aSN1Primitive2 != aSN1Primitive3 && !aSN1Primitive2.asn1Equals(aSN1Primitive3)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return true;
    }

    public java.lang.String toString() {
        int size = size();
        if (size == 0) {
            return "[]";
        }
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append('[');
        int i = 0;
        while (true) {
            stringBuffer.append(this.elements[i]);
            i++;
            if (i < size) {
                stringBuffer.append(", ");
            } else {
                stringBuffer.append(']');
                return stringBuffer.toString();
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.util.Iterable, java.lang.Iterable
    public java.util.Iterator<com.android.internal.org.bouncycastle.asn1.ASN1Encodable> iterator() {
        return new com.android.internal.org.bouncycastle.util.Arrays.Iterator(toArray());
    }

    private static byte[] getDEREncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        try {
            return aSN1Encodable.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("cannot encode object added to SET");
        }
    }

    private static boolean lessThanOrEqual(byte[] bArr, byte[] bArr2) {
        int i = bArr[0] & (-33);
        int i2 = bArr2[0] & (-33);
        if (i == i2) {
            int min = java.lang.Math.min(bArr.length, bArr2.length) - 1;
            for (int i3 = 1; i3 < min; i3++) {
                if (bArr[i3] != bArr2[i3]) {
                    if ((bArr[i3] & 255) >= (bArr2[i3] & 255)) {
                        return false;
                    }
                    return true;
                }
            }
            if ((bArr[min] & 255) > (bArr2[min] & 255)) {
                return false;
            }
            return true;
        }
        if (i >= i2) {
            return false;
        }
        return true;
    }

    private static void sort(com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr) {
        int length = aSN1EncodableArr.length;
        if (length < 2) {
            return;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable = aSN1EncodableArr[0];
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable2 = aSN1EncodableArr[1];
        byte[] dEREncoded = getDEREncoded(aSN1Encodable);
        byte[] dEREncoded2 = getDEREncoded(aSN1Encodable2);
        if (lessThanOrEqual(dEREncoded2, dEREncoded)) {
            aSN1Encodable2 = aSN1Encodable;
            aSN1Encodable = aSN1Encodable2;
            dEREncoded2 = dEREncoded;
            dEREncoded = dEREncoded2;
        }
        for (int i = 2; i < length; i++) {
            com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable3 = aSN1EncodableArr[i];
            byte[] dEREncoded3 = getDEREncoded(aSN1Encodable3);
            if (lessThanOrEqual(dEREncoded2, dEREncoded3)) {
                aSN1EncodableArr[i - 2] = aSN1Encodable;
                aSN1Encodable = aSN1Encodable2;
                dEREncoded = dEREncoded2;
                aSN1Encodable2 = aSN1Encodable3;
                dEREncoded2 = dEREncoded3;
            } else if (lessThanOrEqual(dEREncoded, dEREncoded3)) {
                aSN1EncodableArr[i - 2] = aSN1Encodable;
                aSN1Encodable = aSN1Encodable3;
                dEREncoded = dEREncoded3;
            } else {
                int i2 = i - 1;
                while (true) {
                    i2--;
                    if (i2 <= 0) {
                        break;
                    }
                    com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable4 = aSN1EncodableArr[i2 - 1];
                    if (lessThanOrEqual(getDEREncoded(aSN1Encodable4), dEREncoded3)) {
                        break;
                    } else {
                        aSN1EncodableArr[i2] = aSN1Encodable4;
                    }
                }
                aSN1EncodableArr[i2] = aSN1Encodable3;
            }
        }
        aSN1EncodableArr[length - 2] = aSN1Encodable;
        aSN1EncodableArr[length - 1] = aSN1Encodable2;
    }
}
