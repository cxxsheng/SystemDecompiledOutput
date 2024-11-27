package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class DHParameter extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ASN1Integer g;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer l;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer p;

    public DHParameter(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, int i) {
        this.p = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger);
        this.g = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger2);
        if (i != 0) {
            this.l = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i);
        } else {
            this.l = null;
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.DHParameter(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private DHParameter(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        this.p = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(objects.nextElement());
        this.g = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(objects.nextElement());
        if (objects.hasMoreElements()) {
            this.l = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) objects.nextElement();
        } else {
            this.l = null;
        }
    }

    public java.math.BigInteger getP() {
        return this.p.getPositiveValue();
    }

    public java.math.BigInteger getG() {
        return this.g.getPositiveValue();
    }

    public java.math.BigInteger getL() {
        if (this.l == null) {
            return null;
        }
        return this.l.getPositiveValue();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.p);
        aSN1EncodableVector.add(this.g);
        if (getL() != null) {
            aSN1EncodableVector.add(this.l);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
