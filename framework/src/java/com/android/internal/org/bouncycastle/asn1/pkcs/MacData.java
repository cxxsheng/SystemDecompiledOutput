package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class MacData extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    com.android.internal.org.bouncycastle.asn1.x509.DigestInfo digInfo;
    java.math.BigInteger iterationCount;
    byte[] salt;

    public static com.android.internal.org.bouncycastle.asn1.pkcs.MacData getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.MacData) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.MacData) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.MacData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private MacData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.digInfo = com.android.internal.org.bouncycastle.asn1.x509.DigestInfo.getInstance(aSN1Sequence.getObjectAt(0));
        this.salt = com.android.internal.org.bouncycastle.util.Arrays.clone(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets());
        if (aSN1Sequence.size() == 3) {
            this.iterationCount = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(2)).getValue();
        } else {
            this.iterationCount = ONE;
        }
    }

    public MacData(com.android.internal.org.bouncycastle.asn1.x509.DigestInfo digestInfo, byte[] bArr, int i) {
        this.digInfo = digestInfo;
        this.salt = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        this.iterationCount = java.math.BigInteger.valueOf(i);
    }

    public com.android.internal.org.bouncycastle.asn1.x509.DigestInfo getMac() {
        return this.digInfo;
    }

    public byte[] getSalt() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.salt);
    }

    public java.math.BigInteger getIterationCount() {
        return this.iterationCount;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        aSN1EncodableVector.add(this.digInfo);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DEROctetString(this.salt));
        if (!this.iterationCount.equals(ONE)) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.iterationCount));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
