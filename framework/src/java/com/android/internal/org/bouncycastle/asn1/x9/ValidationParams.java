package com.android.internal.org.bouncycastle.asn1.x9;

/* loaded from: classes4.dex */
public class ValidationParams extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer pgenCounter;
    private com.android.internal.org.bouncycastle.asn1.DERBitString seed;

    public static com.android.internal.org.bouncycastle.asn1.x9.ValidationParams getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.ValidationParams getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x9.ValidationParams) {
            return (com.android.internal.org.bouncycastle.asn1.x9.ValidationParams) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x9.ValidationParams(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ValidationParams(byte[] bArr, int i) {
        if (bArr == null) {
            throw new java.lang.IllegalArgumentException("'seed' cannot be null");
        }
        this.seed = new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr);
        this.pgenCounter = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i);
    }

    public ValidationParams(com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString, com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer) {
        if (dERBitString == null) {
            throw new java.lang.IllegalArgumentException("'seed' cannot be null");
        }
        if (aSN1Integer == null) {
            throw new java.lang.IllegalArgumentException("'pgenCounter' cannot be null");
        }
        this.seed = dERBitString;
        this.pgenCounter = aSN1Integer;
    }

    private ValidationParams(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        this.seed = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1Sequence.getObjectAt(0));
        this.pgenCounter = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public byte[] getSeed() {
        return this.seed.getBytes();
    }

    public java.math.BigInteger getPgenCounter() {
        return this.pgenCounter.getPositiveValue();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.seed);
        aSN1EncodableVector.add(this.pgenCounter);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
