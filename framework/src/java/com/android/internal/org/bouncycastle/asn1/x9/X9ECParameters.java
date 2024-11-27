package com.android.internal.org.bouncycastle.asn1.x9;

/* loaded from: classes4.dex */
public class X9ECParameters extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers {
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    private com.android.internal.org.bouncycastle.math.ec.ECCurve curve;
    private com.android.internal.org.bouncycastle.asn1.x9.X9FieldID fieldID;
    private com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint g;
    private java.math.BigInteger h;
    private java.math.BigInteger n;
    private byte[] seed;

    private X9ECParameters(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (!(aSN1Sequence.getObjectAt(0) instanceof com.android.internal.org.bouncycastle.asn1.ASN1Integer) || !((com.android.internal.org.bouncycastle.asn1.ASN1Integer) aSN1Sequence.getObjectAt(0)).hasValue(ONE)) {
            throw new java.lang.IllegalArgumentException("bad version in X9ECParameters");
        }
        this.n = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) aSN1Sequence.getObjectAt(4)).getValue();
        if (aSN1Sequence.size() == 6) {
            this.h = ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) aSN1Sequence.getObjectAt(5)).getValue();
        }
        com.android.internal.org.bouncycastle.asn1.x9.X9Curve x9Curve = new com.android.internal.org.bouncycastle.asn1.x9.X9Curve(com.android.internal.org.bouncycastle.asn1.x9.X9FieldID.getInstance(aSN1Sequence.getObjectAt(1)), this.n, this.h, com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(2)));
        this.curve = x9Curve.getCurve();
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable objectAt = aSN1Sequence.getObjectAt(3);
        if (objectAt instanceof com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint) {
            this.g = (com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint) objectAt;
        } else {
            this.g = new com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint(this.curve, (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) objectAt);
        }
        this.seed = x9Curve.getSeed();
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters) {
            return (com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public X9ECParameters(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint x9ECPoint, java.math.BigInteger bigInteger) {
        this(eCCurve, x9ECPoint, bigInteger, null, null);
    }

    public X9ECParameters(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint x9ECPoint, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        this(eCCurve, x9ECPoint, bigInteger, bigInteger2, null);
    }

    public X9ECParameters(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint x9ECPoint, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, byte[] bArr) {
        this.curve = eCCurve;
        this.g = x9ECPoint;
        this.n = bigInteger;
        this.h = bigInteger2;
        this.seed = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        if (com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.isFpCurve(eCCurve)) {
            this.fieldID = new com.android.internal.org.bouncycastle.asn1.x9.X9FieldID(eCCurve.getField().getCharacteristic());
            return;
        }
        if (com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.isF2mCurve(eCCurve)) {
            int[] exponentsPresent = ((com.android.internal.org.bouncycastle.math.field.PolynomialExtensionField) eCCurve.getField()).getMinimalPolynomial().getExponentsPresent();
            if (exponentsPresent.length == 3) {
                this.fieldID = new com.android.internal.org.bouncycastle.asn1.x9.X9FieldID(exponentsPresent[2], exponentsPresent[1]);
                return;
            } else {
                if (exponentsPresent.length == 5) {
                    this.fieldID = new com.android.internal.org.bouncycastle.asn1.x9.X9FieldID(exponentsPresent[4], exponentsPresent[1], exponentsPresent[2], exponentsPresent[3]);
                    return;
                }
                throw new java.lang.IllegalArgumentException("Only trinomial and pentomial curves are supported");
            }
        }
        throw new java.lang.IllegalArgumentException("'curve' is of an unsupported type");
    }

    public com.android.internal.org.bouncycastle.math.ec.ECCurve getCurve() {
        return this.curve;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint getG() {
        return this.g.getPoint();
    }

    public java.math.BigInteger getN() {
        return this.n;
    }

    public java.math.BigInteger getH() {
        return this.h;
    }

    public byte[] getSeed() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.seed);
    }

    public boolean hasSeed() {
        return this.seed != null;
    }

    public com.android.internal.org.bouncycastle.asn1.x9.X9Curve getCurveEntry() {
        return new com.android.internal.org.bouncycastle.asn1.x9.X9Curve(this.curve, this.seed);
    }

    public com.android.internal.org.bouncycastle.asn1.x9.X9FieldID getFieldIDEntry() {
        return this.fieldID;
    }

    public com.android.internal.org.bouncycastle.asn1.x9.X9ECPoint getBaseEntry() {
        return this.g;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(6);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(ONE));
        aSN1EncodableVector.add(this.fieldID);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.x9.X9Curve(this.curve, this.seed));
        aSN1EncodableVector.add(this.g);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.n));
        if (this.h != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.h));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
