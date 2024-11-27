package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class BERTaggedObject extends com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject {
    public BERTaggedObject(int i, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        super(true, i, aSN1Encodable);
    }

    public BERTaggedObject(boolean z, int i, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        super(z, i, aSN1Encodable);
    }

    public BERTaggedObject(int i) {
        super(false, i, new com.android.internal.org.bouncycastle.asn1.BERSequence());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return this.explicit || this.obj.toASN1Primitive().isConstructed();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws java.io.IOException {
        int encodedLength = this.obj.toASN1Primitive().encodedLength();
        if (this.explicit) {
            return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateTagLength(this.tagNo) + com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateBodyLength(encodedLength) + encodedLength;
        }
        return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateTagLength(this.tagNo) + (encodedLength - 1);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        java.util.Enumeration objects;
        aSN1OutputStream.writeTag(z, 160, this.tagNo);
        aSN1OutputStream.write(128);
        if (!this.explicit) {
            if (this.obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString) {
                if (this.obj instanceof com.android.internal.org.bouncycastle.asn1.BEROctetString) {
                    objects = ((com.android.internal.org.bouncycastle.asn1.BEROctetString) this.obj).getObjects();
                } else {
                    objects = new com.android.internal.org.bouncycastle.asn1.BEROctetString(((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) this.obj).getOctets()).getObjects();
                }
            } else if (this.obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
                objects = ((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) this.obj).getObjects();
            } else if (this.obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Set) {
                objects = ((com.android.internal.org.bouncycastle.asn1.ASN1Set) this.obj).getObjects();
            } else {
                throw new com.android.internal.org.bouncycastle.asn1.ASN1Exception("not implemented: " + this.obj.getClass().getName());
            }
            aSN1OutputStream.writeElements(objects);
        } else {
            aSN1OutputStream.writePrimitive(this.obj.toASN1Primitive(), true);
        }
        aSN1OutputStream.write(0);
        aSN1OutputStream.write(0);
    }
}
