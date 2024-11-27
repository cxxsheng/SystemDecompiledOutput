package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public abstract class ASN1Sequence extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive implements com.android.internal.org.bouncycastle.util.Iterable<com.android.internal.org.bouncycastle.asn1.ASN1Encodable> {
    com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] elements;

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    abstract void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException;

    public static com.android.internal.org.bouncycastle.asn1.ASN1Sequence getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence)) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1SequenceParser) {
            return getInstance(((com.android.internal.org.bouncycastle.asn1.ASN1SequenceParser) obj).toASN1Primitive());
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(fromByteArray((byte[]) obj));
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("failed to construct sequence from byte[]: " + e.getMessage());
            }
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Encodable) {
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive = ((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
                return (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) aSN1Primitive;
            }
        }
        throw new java.lang.IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Sequence getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (z) {
            if (!aSN1TaggedObject.isExplicit()) {
                throw new java.lang.IllegalArgumentException("object implicit - explicit expected.");
            }
            return getInstance(aSN1TaggedObject.getObject());
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive object = aSN1TaggedObject.getObject();
        if (aSN1TaggedObject.isExplicit()) {
            if (aSN1TaggedObject instanceof com.android.internal.org.bouncycastle.asn1.BERTaggedObject) {
                return new com.android.internal.org.bouncycastle.asn1.BERSequence(object);
            }
            return new com.android.internal.org.bouncycastle.asn1.DLSequence(object);
        }
        if (object instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) object;
            if (aSN1TaggedObject instanceof com.android.internal.org.bouncycastle.asn1.BERTaggedObject) {
                return aSN1Sequence;
            }
            return (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) aSN1Sequence.toDLObject();
        }
        throw new java.lang.IllegalArgumentException("unknown object in getInstance: " + aSN1TaggedObject.getClass().getName());
    }

    protected ASN1Sequence() {
        this.elements = com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector.EMPTY_ELEMENTS;
    }

    protected ASN1Sequence(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable == null) {
            throw new java.lang.NullPointerException("'element' cannot be null");
        }
        this.elements = new com.android.internal.org.bouncycastle.asn1.ASN1Encodable[]{aSN1Encodable};
    }

    protected ASN1Sequence(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector) {
        if (aSN1EncodableVector == null) {
            throw new java.lang.NullPointerException("'elementVector' cannot be null");
        }
        this.elements = aSN1EncodableVector.takeElements();
    }

    protected ASN1Sequence(com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr) {
        if (com.android.internal.org.bouncycastle.util.Arrays.isNullOrContainsNull(aSN1EncodableArr)) {
            throw new java.lang.NullPointerException("'elements' cannot be null, or contain null");
        }
        this.elements = com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector.cloneElements(aSN1EncodableArr);
    }

    ASN1Sequence(com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr, boolean z) {
        this.elements = z ? com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector.cloneElements(aSN1EncodableArr) : aSN1EncodableArr;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] toArray() {
        return com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector.cloneElements(this.elements);
    }

    com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] toArrayInternal() {
        return this.elements;
    }

    public java.util.Enumeration getObjects() {
        return new java.util.Enumeration() { // from class: com.android.internal.org.bouncycastle.asn1.ASN1Sequence.1
            private int pos = 0;

            @Override // java.util.Enumeration
            public boolean hasMoreElements() {
                return this.pos < com.android.internal.org.bouncycastle.asn1.ASN1Sequence.this.elements.length;
            }

            @Override // java.util.Enumeration
            public java.lang.Object nextElement() {
                if (this.pos < com.android.internal.org.bouncycastle.asn1.ASN1Sequence.this.elements.length) {
                    com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.this.elements;
                    int i = this.pos;
                    this.pos = i + 1;
                    return aSN1EncodableArr[i];
                }
                throw new java.util.NoSuchElementException();
            }
        };
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1SequenceParser parser() {
        final int size = size();
        return new com.android.internal.org.bouncycastle.asn1.ASN1SequenceParser() { // from class: com.android.internal.org.bouncycastle.asn1.ASN1Sequence.2
            private int pos = 0;

            @Override // com.android.internal.org.bouncycastle.asn1.ASN1SequenceParser
            public com.android.internal.org.bouncycastle.asn1.ASN1Encodable readObject() throws java.io.IOException {
                if (size == this.pos) {
                    return null;
                }
                com.android.internal.org.bouncycastle.asn1.ASN1Encodable[] aSN1EncodableArr = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.this.elements;
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
                return com.android.internal.org.bouncycastle.asn1.ASN1Sequence.this;
            }

            @Override // com.android.internal.org.bouncycastle.asn1.ASN1Encodable
            public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
                return com.android.internal.org.bouncycastle.asn1.ASN1Sequence.this;
            }
        };
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getObjectAt(int i) {
        return this.elements[i];
    }

    public int size() {
        return this.elements.length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        int length = this.elements.length;
        int i = length + 1;
        while (true) {
            length--;
            if (length >= 0) {
                i = (i * 257) ^ this.elements[length].toASN1Primitive().hashCode();
            } else {
                return i;
            }
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence)) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) aSN1Primitive;
        int size = size();
        if (aSN1Sequence.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive2 = this.elements[i].toASN1Primitive();
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive3 = aSN1Sequence.elements[i].toASN1Primitive();
            if (aSN1Primitive2 != aSN1Primitive3 && !aSN1Primitive2.asn1Equals(aSN1Primitive3)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(this.elements, false);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return new com.android.internal.org.bouncycastle.asn1.DLSequence(this.elements, false);
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
        return new com.android.internal.org.bouncycastle.util.Arrays.Iterator(this.elements);
    }
}
