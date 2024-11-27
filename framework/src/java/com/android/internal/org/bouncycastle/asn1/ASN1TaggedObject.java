package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public abstract class ASN1TaggedObject extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive implements com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser {
    final boolean explicit;
    final com.android.internal.org.bouncycastle.asn1.ASN1Encodable obj;
    final int tagNo;

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    abstract void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException;

    public static com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (z) {
            return getInstance(aSN1TaggedObject.getObject());
        }
        throw new java.lang.IllegalArgumentException("implicitly tagged tagged object");
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject)) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(fromByteArray((byte[]) obj));
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("failed to construct tagged object from byte[]: " + e.getMessage());
            }
        }
        throw new java.lang.IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
    }

    public ASN1TaggedObject(boolean z, int i, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable == null) {
            throw new java.lang.NullPointerException("'obj' cannot be null");
        }
        this.tagNo = i;
        this.explicit = z || (aSN1Encodable instanceof com.android.internal.org.bouncycastle.asn1.ASN1Choice);
        this.obj = aSN1Encodable;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject)) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Primitive;
        if (this.tagNo != aSN1TaggedObject.tagNo || this.explicit != aSN1TaggedObject.explicit) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive2 = this.obj.toASN1Primitive();
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive3 = aSN1TaggedObject.obj.toASN1Primitive();
        return aSN1Primitive2 == aSN1Primitive3 || aSN1Primitive2.asn1Equals(aSN1Primitive3);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return (this.tagNo ^ (this.explicit ? 15 : 240)) ^ this.obj.toASN1Primitive().hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagNo() {
        return this.tagNo;
    }

    public boolean isExplicit() {
        return this.explicit;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getObject() {
        return this.obj.toASN1Primitive();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getObjectParser(int i, boolean z) throws java.io.IOException {
        switch (i) {
            case 4:
                return com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(this, z).parser();
            case 16:
                return com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(this, z).parser();
            case 17:
                return com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(this, z).parser();
            default:
                if (z) {
                    return getObject();
                }
                throw new com.android.internal.org.bouncycastle.asn1.ASN1Exception("implicit tagging not implemented for tag: " + i);
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getLoadedObject() {
        return toASN1Primitive();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        return new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(this.explicit, this.tagNo, this.obj);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return new com.android.internal.org.bouncycastle.asn1.DLTaggedObject(this.explicit, this.tagNo, this.obj);
    }

    public java.lang.String toString() {
        return android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + this.tagNo + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END + this.obj;
    }
}
