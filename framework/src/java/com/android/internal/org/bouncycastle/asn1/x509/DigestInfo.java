package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class DigestInfo extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algId;
    private byte[] digest;

    public static com.android.internal.org.bouncycastle.asn1.x509.DigestInfo getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.DigestInfo getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.DigestInfo) {
            return (com.android.internal.org.bouncycastle.asn1.x509.DigestInfo) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.DigestInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public DigestInfo(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.digest = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
        this.algId = algorithmIdentifier;
    }

    public DigestInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        this.algId = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(objects.nextElement());
        this.digest = com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(objects.nextElement()).getOctets();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmId() {
        return this.algId;
    }

    public byte[] getDigest() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.digest);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.algId);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DEROctetString(this.digest));
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
