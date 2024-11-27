package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class RSAESOAEPparams extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    public static final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier DEFAULT_HASH_ALGORITHM = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
    public static final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier DEFAULT_MASK_GEN_FUNCTION = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_mgf1, DEFAULT_HASH_ALGORITHM);
    public static final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier DEFAULT_P_SOURCE_ALGORITHM = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_pSpecified, new com.android.internal.org.bouncycastle.asn1.DEROctetString(new byte[0]));
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier hashAlgorithm;
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier maskGenAlgorithm;
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier pSourceAlgorithm;

    public static com.android.internal.org.bouncycastle.asn1.pkcs.RSAESOAEPparams getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.RSAESOAEPparams) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.RSAESOAEPparams) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.RSAESOAEPparams(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public RSAESOAEPparams() {
        this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
        this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
        this.pSourceAlgorithm = DEFAULT_P_SOURCE_ALGORITHM;
    }

    public RSAESOAEPparams(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier3) {
        this.hashAlgorithm = algorithmIdentifier;
        this.maskGenAlgorithm = algorithmIdentifier2;
        this.pSourceAlgorithm = algorithmIdentifier3;
    }

    public RSAESOAEPparams(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
        this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
        this.pSourceAlgorithm = DEFAULT_P_SOURCE_ALGORITHM;
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Sequence.getObjectAt(i);
            switch (aSN1TaggedObject.getTagNo()) {
                case 0:
                    this.hashAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1TaggedObject, true);
                    break;
                case 1:
                    this.maskGenAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1TaggedObject, true);
                    break;
                case 2:
                    this.pSourceAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1TaggedObject, true);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("unknown tag");
            }
        }
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getMaskGenAlgorithm() {
        return this.maskGenAlgorithm;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getPSourceAlgorithm() {
        return this.pSourceAlgorithm;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        if (!this.hashAlgorithm.equals(DEFAULT_HASH_ALGORITHM)) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.hashAlgorithm));
        }
        if (!this.maskGenAlgorithm.equals(DEFAULT_MASK_GEN_FUNCTION)) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 1, this.maskGenAlgorithm));
        }
        if (!this.pSourceAlgorithm.equals(DEFAULT_P_SOURCE_ALGORITHM)) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 2, this.pSourceAlgorithm));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
