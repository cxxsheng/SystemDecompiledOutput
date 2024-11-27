package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class DSAParameter extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ASN1Integer g;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer p;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer q;

    public static com.android.internal.org.bouncycastle.asn1.x509.DSAParameter getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.DSAParameter getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.DSAParameter) {
            return (com.android.internal.org.bouncycastle.asn1.x509.DSAParameter) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.DSAParameter(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DSAParameter(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3) {
        this.p = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger);
        this.q = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger2);
        this.g = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger3);
    }

    private DSAParameter(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 3) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        this.p = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(objects.nextElement());
        this.q = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(objects.nextElement());
        this.g = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(objects.nextElement());
    }

    public java.math.BigInteger getP() {
        return this.p.getPositiveValue();
    }

    public java.math.BigInteger getQ() {
        return this.q.getPositiveValue();
    }

    public java.math.BigInteger getG() {
        return this.g.getPositiveValue();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.p);
        aSN1EncodableVector.add(this.q);
        aSN1EncodableVector.add(this.g);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
