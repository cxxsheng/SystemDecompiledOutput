package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class SubjectPublicKeyInfo extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algId;
    private com.android.internal.org.bouncycastle.asn1.DERBitString keyData;

    public static com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo) {
            return (com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public SubjectPublicKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.io.IOException {
        this.keyData = new com.android.internal.org.bouncycastle.asn1.DERBitString(aSN1Encodable);
        this.algId = algorithmIdentifier;
    }

    public SubjectPublicKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.keyData = new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr);
        this.algId = algorithmIdentifier;
    }

    public SubjectPublicKeyInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        this.algId = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(objects.nextElement());
        this.keyData = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(objects.nextElement());
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithm() {
        return this.algId;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmId() {
        return this.algId;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive parsePublicKey() throws java.io.IOException {
        return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(this.keyData.getOctets());
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getPublicKey() throws java.io.IOException {
        return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(this.keyData.getOctets());
    }

    public com.android.internal.org.bouncycastle.asn1.DERBitString getPublicKeyData() {
        return this.keyData;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.algId);
        aSN1EncodableVector.add(this.keyData);
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
