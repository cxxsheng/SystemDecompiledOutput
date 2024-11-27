package com.android.internal.org.bouncycastle.asn1.x9;

/* loaded from: classes4.dex */
public class DHDomainParameters extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer g;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer j;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer p;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer q;
    private com.android.internal.org.bouncycastle.asn1.x9.DHValidationParms validationParms;

    public static com.android.internal.org.bouncycastle.asn1.x9.DHDomainParameters getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.DHDomainParameters getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.x9.DHDomainParameters)) {
            return (com.android.internal.org.bouncycastle.asn1.x9.DHDomainParameters) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
            return new com.android.internal.org.bouncycastle.asn1.x9.DHDomainParameters((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) obj);
        }
        throw new java.lang.IllegalArgumentException("Invalid DHDomainParameters: " + obj.getClass().getName());
    }

    public DHDomainParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, java.math.BigInteger bigInteger4, com.android.internal.org.bouncycastle.asn1.x9.DHValidationParms dHValidationParms) {
        if (bigInteger == null) {
            throw new java.lang.IllegalArgumentException("'p' cannot be null");
        }
        if (bigInteger2 == null) {
            throw new java.lang.IllegalArgumentException("'g' cannot be null");
        }
        if (bigInteger3 == null) {
            throw new java.lang.IllegalArgumentException("'q' cannot be null");
        }
        this.p = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger);
        this.g = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger2);
        this.q = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger3);
        this.j = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger4);
        this.validationParms = dHValidationParms;
    }

    public DHDomainParameters(com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer, com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer2, com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer3, com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer4, com.android.internal.org.bouncycastle.asn1.x9.DHValidationParms dHValidationParms) {
        if (aSN1Integer == null) {
            throw new java.lang.IllegalArgumentException("'p' cannot be null");
        }
        if (aSN1Integer2 == null) {
            throw new java.lang.IllegalArgumentException("'g' cannot be null");
        }
        if (aSN1Integer3 == null) {
            throw new java.lang.IllegalArgumentException("'q' cannot be null");
        }
        this.p = aSN1Integer;
        this.g = aSN1Integer2;
        this.q = aSN1Integer3;
        this.j = aSN1Integer4;
        this.validationParms = dHValidationParms;
    }

    private DHDomainParameters(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 3 || aSN1Sequence.size() > 5) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        this.p = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(objects.nextElement());
        this.g = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(objects.nextElement());
        this.q = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(objects.nextElement());
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable next = getNext(objects);
        if (next != null && (next instanceof com.android.internal.org.bouncycastle.asn1.ASN1Integer)) {
            this.j = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(next);
            next = getNext(objects);
        }
        if (next != null) {
            this.validationParms = com.android.internal.org.bouncycastle.asn1.x9.DHValidationParms.getInstance(next.toASN1Primitive());
        }
    }

    private static com.android.internal.org.bouncycastle.asn1.ASN1Encodable getNext(java.util.Enumeration enumeration) {
        if (enumeration.hasMoreElements()) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1Encodable) enumeration.nextElement();
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getP() {
        return this.p;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getG() {
        return this.g;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getQ() {
        return this.q;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getJ() {
        return this.j;
    }

    public com.android.internal.org.bouncycastle.asn1.x9.DHValidationParms getValidationParms() {
        return this.validationParms;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(5);
        aSN1EncodableVector.add(this.p);
        aSN1EncodableVector.add(this.g);
        aSN1EncodableVector.add(this.q);
        if (this.j != null) {
            aSN1EncodableVector.add(this.j);
        }
        if (this.validationParms != null) {
            aSN1EncodableVector.add(this.validationParms);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
