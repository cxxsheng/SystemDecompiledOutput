package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class PBEParameter extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ASN1Integer iterations;
    com.android.internal.org.bouncycastle.asn1.ASN1OctetString salt;

    public PBEParameter(byte[] bArr, int i) {
        if (bArr.length != 8) {
            throw new java.lang.IllegalArgumentException("salt length must be 8");
        }
        this.salt = new com.android.internal.org.bouncycastle.asn1.DEROctetString(bArr);
        this.iterations = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i);
    }

    private PBEParameter(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.salt = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) aSN1Sequence.getObjectAt(0);
        this.iterations = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) aSN1Sequence.getObjectAt(1);
    }

    public static com.android.internal.org.bouncycastle.asn1.pkcs.PBEParameter getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.PBEParameter) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.PBEParameter) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.PBEParameter(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public java.math.BigInteger getIterationCount() {
        return this.iterations.getValue();
    }

    public byte[] getSalt() {
        return this.salt.getOctets();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.salt);
        aSN1EncodableVector.add(this.iterations);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
