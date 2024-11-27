package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class DLTaggedObject extends com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject {
    public DLTaggedObject(boolean z, int i, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        super(z, i, aSN1Encodable);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return this.explicit || this.obj.toASN1Primitive().toDLObject().isConstructed();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws java.io.IOException {
        int encodedLength = this.obj.toASN1Primitive().toDLObject().encodedLength();
        if (this.explicit) {
            return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateTagLength(this.tagNo) + com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateBodyLength(encodedLength) + encodedLength;
        }
        return com.android.internal.org.bouncycastle.asn1.StreamUtil.calculateTagLength(this.tagNo) + (encodedLength - 1);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive dLObject = this.obj.toASN1Primitive().toDLObject();
        aSN1OutputStream.writeTag(z, (this.explicit || dLObject.isConstructed()) ? 160 : 128, this.tagNo);
        if (this.explicit) {
            aSN1OutputStream.writeLength(dLObject.encodedLength());
        }
        aSN1OutputStream.getDLSubStream().writePrimitive(dLObject, this.explicit);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return this;
    }
}
