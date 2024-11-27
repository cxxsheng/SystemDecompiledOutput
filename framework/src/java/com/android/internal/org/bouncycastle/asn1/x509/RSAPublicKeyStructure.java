package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class RSAPublicKeyStructure extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private java.math.BigInteger modulus;
    private java.math.BigInteger publicExponent;

    public static com.android.internal.org.bouncycastle.asn1.x509.RSAPublicKeyStructure getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.RSAPublicKeyStructure getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.RSAPublicKeyStructure)) {
            return (com.android.internal.org.bouncycastle.asn1.x509.RSAPublicKeyStructure) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
            return new com.android.internal.org.bouncycastle.asn1.x509.RSAPublicKeyStructure((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) obj);
        }
        throw new java.lang.IllegalArgumentException("Invalid RSAPublicKeyStructure: " + obj.getClass().getName());
    }

    public RSAPublicKeyStructure(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        this.modulus = bigInteger;
        this.publicExponent = bigInteger2;
    }

    public RSAPublicKeyStructure(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        this.modulus = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(objects.nextElement()).getPositiveValue();
        this.publicExponent = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(objects.nextElement()).getPositiveValue();
    }

    public java.math.BigInteger getModulus() {
        return this.modulus;
    }

    public java.math.BigInteger getPublicExponent() {
        return this.publicExponent;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(getModulus()));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(getPublicExponent()));
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
