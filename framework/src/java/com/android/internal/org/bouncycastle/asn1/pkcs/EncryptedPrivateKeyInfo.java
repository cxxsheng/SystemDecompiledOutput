package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class EncryptedPrivateKeyInfo extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algId;
    private com.android.internal.org.bouncycastle.asn1.ASN1OctetString data;

    private EncryptedPrivateKeyInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        this.algId = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(objects.nextElement());
        this.data = com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(objects.nextElement());
    }

    public EncryptedPrivateKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.algId = algorithmIdentifier;
        this.data = new com.android.internal.org.bouncycastle.asn1.DEROctetString(bArr);
    }

    public static com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getEncryptionAlgorithm() {
        return this.algId;
    }

    public byte[] getEncryptedData() {
        return this.data.getOctets();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.algId);
        aSN1EncodableVector.add(this.data);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
