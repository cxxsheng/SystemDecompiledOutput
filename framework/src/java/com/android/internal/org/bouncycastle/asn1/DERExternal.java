package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class DERExternal extends com.android.internal.org.bouncycastle.asn1.ASN1External {
    public DERExternal(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector);
    }

    public DERExternal(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer, com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive, com.android.internal.org.bouncycastle.asn1.DERTaggedObject dERTaggedObject) {
        this(aSN1ObjectIdentifier, aSN1Integer, aSN1Primitive, dERTaggedObject.getTagNo(), dERTaggedObject.toASN1Primitive());
    }

    public DERExternal(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer, com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive, int i, com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive2) {
        super(aSN1ObjectIdentifier, aSN1Integer, aSN1Primitive, i, aSN1Primitive2);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1External, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        return this;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1External, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return this;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1External, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws java.io.IOException {
        return getEncoded().length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        if (this.directReference != null) {
            byteArrayOutputStream.write(this.directReference.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
        }
        if (this.indirectReference != null) {
            byteArrayOutputStream.write(this.indirectReference.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
        }
        if (this.dataValueDescriptor != null) {
            byteArrayOutputStream.write(this.dataValueDescriptor.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
        }
        byteArrayOutputStream.write(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, this.encoding, this.externalContent).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
        aSN1OutputStream.writeEncoded(z, 32, 8, byteArrayOutputStream.toByteArray());
    }
}
