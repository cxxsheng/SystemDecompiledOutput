package com.android.internal.org.bouncycastle.crypto.signers;

/* loaded from: classes4.dex */
public class StandardDSAEncoding implements com.android.internal.org.bouncycastle.crypto.signers.DSAEncoding {
    public static final com.android.internal.org.bouncycastle.crypto.signers.StandardDSAEncoding INSTANCE = new com.android.internal.org.bouncycastle.crypto.signers.StandardDSAEncoding();

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAEncoding
    public byte[] encode(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        encodeValue(bigInteger, aSN1EncodableVector, bigInteger2);
        encodeValue(bigInteger, aSN1EncodableVector, bigInteger3);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.signers.DSAEncoding
    public java.math.BigInteger[] decode(java.math.BigInteger bigInteger, byte[] bArr) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bArr);
        if (aSN1Sequence.size() == 2) {
            java.math.BigInteger decodeValue = decodeValue(bigInteger, aSN1Sequence, 0);
            java.math.BigInteger decodeValue2 = decodeValue(bigInteger, aSN1Sequence, 1);
            if (com.android.internal.org.bouncycastle.util.Arrays.areEqual(encode(bigInteger, decodeValue, decodeValue2), bArr)) {
                return new java.math.BigInteger[]{decodeValue, decodeValue2};
            }
        }
        throw new java.lang.IllegalArgumentException("Malformed signature");
    }

    protected java.math.BigInteger checkValue(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        if (bigInteger2.signum() < 0 || (bigInteger != null && bigInteger2.compareTo(bigInteger) >= 0)) {
            throw new java.lang.IllegalArgumentException("Value out of range");
        }
        return bigInteger2;
    }

    protected java.math.BigInteger decodeValue(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence, int i) {
        return checkValue(bigInteger, ((com.android.internal.org.bouncycastle.asn1.ASN1Integer) aSN1Sequence.getObjectAt(i)).getValue());
    }

    protected void encodeValue(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector, java.math.BigInteger bigInteger2) {
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(checkValue(bigInteger, bigInteger2)));
    }
}
