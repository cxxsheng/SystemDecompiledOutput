package com.android.internal.org.bouncycastle.asn1.x9;

/* loaded from: classes4.dex */
public class X9FieldID extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.x9.X9ObjectIdentifiers {
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id;
    private com.android.internal.org.bouncycastle.asn1.ASN1Primitive parameters;

    public X9FieldID(java.math.BigInteger bigInteger) {
        this.id = prime_field;
        this.parameters = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger);
    }

    public X9FieldID(int i, int i2) {
        this(i, i2, 0, 0);
    }

    public X9FieldID(int i, int i2, int i3, int i4) {
        this.id = characteristic_two_field;
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i));
        if (i3 == 0) {
            if (i4 != 0) {
                throw new java.lang.IllegalArgumentException("inconsistent k values");
            }
            aSN1EncodableVector.add(tpBasis);
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i2));
        } else {
            if (i3 <= i2 || i4 <= i3) {
                throw new java.lang.IllegalArgumentException("inconsistent k values");
            }
            aSN1EncodableVector.add(ppBasis);
            com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
            aSN1EncodableVector2.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i2));
            aSN1EncodableVector2.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i3));
            aSN1EncodableVector2.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i4));
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector2));
        }
        this.parameters = new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    private X9FieldID(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.id = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.parameters = aSN1Sequence.getObjectAt(1).toASN1Primitive();
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.X9FieldID getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x9.X9FieldID) {
            return (com.android.internal.org.bouncycastle.asn1.x9.X9FieldID) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x9.X9FieldID(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getIdentifier() {
        return this.id;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getParameters() {
        return this.parameters;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.id);
        aSN1EncodableVector.add(this.parameters);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
