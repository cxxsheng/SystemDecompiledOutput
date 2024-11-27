package com.android.internal.org.bouncycastle.asn1.x9;

/* loaded from: classes4.dex */
public class X9Curve extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers {
    private com.android.internal.org.bouncycastle.math.ec.ECCurve curve;
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier fieldIdentifier;
    private byte[] seed;

    public X9Curve(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve) {
        this(eCCurve, null);
    }

    public X9Curve(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, byte[] bArr) {
        this.fieldIdentifier = null;
        this.curve = eCCurve;
        this.seed = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        setFieldIdentifier();
    }

    public X9Curve(com.android.internal.org.bouncycastle.asn1.x9.X9FieldID x9FieldID, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        int intValueExact;
        int i;
        int i2;
        this.fieldIdentifier = null;
        this.fieldIdentifier = x9FieldID.getIdentifier();
        if (this.fieldIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) prime_field)) {
            this.curve = new com.android.internal.org.bouncycastle.math.ec.ECCurve.Fp(((com.android.internal.org.bouncycastle.asn1.ASN1Integer) x9FieldID.getParameters()).getValue(), new java.math.BigInteger(1, com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets()), new java.math.BigInteger(1, com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets()), bigInteger, bigInteger2);
        } else if (this.fieldIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) characteristic_two_field)) {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(x9FieldID.getParameters());
            int intValueExact2 = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) aSN1Sequence2.getObjectAt(0)).intValueExact();
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) aSN1Sequence2.getObjectAt(1);
            if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) tpBasis)) {
                i = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence2.getObjectAt(2)).intValueExact();
                i2 = 0;
                intValueExact = 0;
            } else if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) ppBasis)) {
                com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence3 = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1Sequence2.getObjectAt(2));
                int intValueExact3 = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence3.getObjectAt(0)).intValueExact();
                int intValueExact4 = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence3.getObjectAt(1)).intValueExact();
                intValueExact = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence3.getObjectAt(2)).intValueExact();
                i = intValueExact3;
                i2 = intValueExact4;
            } else {
                throw new java.lang.IllegalArgumentException("This type of EC basis is not implemented");
            }
            this.curve = new com.android.internal.org.bouncycastle.math.ec.ECCurve.F2m(intValueExact2, i, i2, intValueExact, new java.math.BigInteger(1, com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets()), new java.math.BigInteger(1, com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets()), bigInteger, bigInteger2);
        } else {
            throw new java.lang.IllegalArgumentException("This type of ECCurve is not implemented");
        }
        if (aSN1Sequence.size() == 3) {
            this.seed = ((com.android.internal.org.bouncycastle.asn1.DERBitString) aSN1Sequence.getObjectAt(2)).getBytes();
        }
    }

    private void setFieldIdentifier() {
        if (com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.isFpCurve(this.curve)) {
            this.fieldIdentifier = prime_field;
        } else {
            if (com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.isF2mCurve(this.curve)) {
                this.fieldIdentifier = characteristic_two_field;
                return;
            }
            throw new java.lang.IllegalArgumentException("This type of ECCurve is not implemented");
        }
    }

    public com.android.internal.org.bouncycastle.math.ec.ECCurve getCurve() {
        return this.curve;
    }

    public byte[] getSeed() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.seed);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        if (this.fieldIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) prime_field)) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.x9.X9FieldElement(this.curve.getA()).toASN1Primitive());
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.x9.X9FieldElement(this.curve.getB()).toASN1Primitive());
        } else if (this.fieldIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) characteristic_two_field)) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.x9.X9FieldElement(this.curve.getA()).toASN1Primitive());
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.x9.X9FieldElement(this.curve.getB()).toASN1Primitive());
        }
        if (this.seed != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERBitString(this.seed));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
