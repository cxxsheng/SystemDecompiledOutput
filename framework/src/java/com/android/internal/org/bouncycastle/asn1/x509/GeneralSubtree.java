package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class GeneralSubtree extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private static final java.math.BigInteger ZERO = java.math.BigInteger.valueOf(0);
    private com.android.internal.org.bouncycastle.asn1.x509.GeneralName base;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer maximum;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer minimum;

    private GeneralSubtree(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.base = com.android.internal.org.bouncycastle.asn1.x509.GeneralName.getInstance(aSN1Sequence.getObjectAt(0));
        switch (aSN1Sequence.size()) {
            case 1:
                return;
            case 2:
                com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(1));
                switch (aSN1TaggedObject.getTagNo()) {
                    case 0:
                        this.minimum = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1TaggedObject, false);
                        return;
                    case 1:
                        this.maximum = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1TaggedObject, false);
                        return;
                    default:
                        throw new java.lang.IllegalArgumentException("Bad tag number: " + aSN1TaggedObject.getTagNo());
                }
            case 3:
                com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject2 = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(1));
                if (aSN1TaggedObject2.getTagNo() != 0) {
                    throw new java.lang.IllegalArgumentException("Bad tag number for 'minimum': " + aSN1TaggedObject2.getTagNo());
                }
                this.minimum = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1TaggedObject2, false);
                com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject3 = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(2));
                if (aSN1TaggedObject3.getTagNo() != 1) {
                    throw new java.lang.IllegalArgumentException("Bad tag number for 'maximum': " + aSN1TaggedObject3.getTagNo());
                }
                this.maximum = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1TaggedObject3, false);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
    }

    public GeneralSubtree(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        this.base = generalName;
        if (bigInteger2 != null) {
            this.maximum = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger2);
        }
        if (bigInteger == null) {
            this.minimum = null;
        } else {
            this.minimum = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger);
        }
    }

    public GeneralSubtree(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName) {
        this(generalName, null, null);
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return new com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree getInstance(java.lang.Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree) {
            return (com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree) obj;
        }
        return new com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
    }

    public com.android.internal.org.bouncycastle.asn1.x509.GeneralName getBase() {
        return this.base;
    }

    public java.math.BigInteger getMinimum() {
        if (this.minimum == null) {
            return ZERO;
        }
        return this.minimum.getValue();
    }

    public java.math.BigInteger getMaximum() {
        if (this.maximum == null) {
            return null;
        }
        return this.maximum.getValue();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.base);
        if (this.minimum != null && !this.minimum.hasValue(ZERO)) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 0, this.minimum));
        }
        if (this.maximum != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, this.maximum));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
