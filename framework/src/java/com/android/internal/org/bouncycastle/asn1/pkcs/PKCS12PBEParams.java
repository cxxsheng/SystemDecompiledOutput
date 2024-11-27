package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class PKCS12PBEParams extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ASN1Integer iterations;
    com.android.internal.org.bouncycastle.asn1.ASN1OctetString iv;

    public PKCS12PBEParams(byte[] bArr, int i) {
        this.iv = new com.android.internal.org.bouncycastle.asn1.DEROctetString(bArr);
        this.iterations = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i);
    }

    private PKCS12PBEParams(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.iv = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) aSN1Sequence.getObjectAt(0);
        this.iterations = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public static com.android.internal.org.bouncycastle.asn1.pkcs.PKCS12PBEParams getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.PKCS12PBEParams) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.PKCS12PBEParams) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.PKCS12PBEParams(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public java.math.BigInteger getIterations() {
        return this.iterations.getValue();
    }

    public byte[] getIV() {
        return this.iv.getOctets();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.iv);
        aSN1EncodableVector.add(this.iterations);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
