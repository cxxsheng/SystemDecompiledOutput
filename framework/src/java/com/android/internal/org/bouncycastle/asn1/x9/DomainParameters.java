package com.android.internal.org.bouncycastle.asn1.x9;

/* loaded from: classes4.dex */
public class DomainParameters extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private final com.android.internal.org.bouncycastle.asn1.ASN1Integer g;
    private final com.android.internal.org.bouncycastle.asn1.ASN1Integer j;
    private final com.android.internal.org.bouncycastle.asn1.ASN1Integer p;
    private final com.android.internal.org.bouncycastle.asn1.ASN1Integer q;
    private final com.android.internal.org.bouncycastle.asn1.x9.ValidationParams validationParams;

    public static com.android.internal.org.bouncycastle.asn1.x9.DomainParameters getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.DomainParameters getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x9.DomainParameters) {
            return (com.android.internal.org.bouncycastle.asn1.x9.DomainParameters) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x9.DomainParameters(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DomainParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, java.math.BigInteger bigInteger4, com.android.internal.org.bouncycastle.asn1.x9.ValidationParams validationParams) {
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
        if (bigInteger4 != null) {
            this.j = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger4);
        } else {
            this.j = null;
        }
        this.validationParams = validationParams;
    }

    private DomainParameters(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
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
        } else {
            this.j = null;
        }
        if (next != null) {
            this.validationParams = com.android.internal.org.bouncycastle.asn1.x9.ValidationParams.getInstance(next.toASN1Primitive());
        } else {
            this.validationParams = null;
        }
    }

    private static com.android.internal.org.bouncycastle.asn1.ASN1Encodable getNext(java.util.Enumeration enumeration) {
        if (enumeration.hasMoreElements()) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1Encodable) enumeration.nextElement();
        }
        return null;
    }

    public java.math.BigInteger getP() {
        return this.p.getPositiveValue();
    }

    public java.math.BigInteger getG() {
        return this.g.getPositiveValue();
    }

    public java.math.BigInteger getQ() {
        return this.q.getPositiveValue();
    }

    public java.math.BigInteger getJ() {
        if (this.j == null) {
            return null;
        }
        return this.j.getPositiveValue();
    }

    public com.android.internal.org.bouncycastle.asn1.x9.ValidationParams getValidationParams() {
        return this.validationParams;
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
        if (this.validationParams != null) {
            aSN1EncodableVector.add(this.validationParams);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
