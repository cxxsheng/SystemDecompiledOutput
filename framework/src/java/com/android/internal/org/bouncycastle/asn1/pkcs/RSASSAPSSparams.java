package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class RSASSAPSSparams extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    public static final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier DEFAULT_HASH_ALGORITHM = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
    public static final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier DEFAULT_MASK_GEN_FUNCTION = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_mgf1, DEFAULT_HASH_ALGORITHM);
    public static final com.android.internal.org.bouncycastle.asn1.ASN1Integer DEFAULT_SALT_LENGTH = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(20);
    public static final com.android.internal.org.bouncycastle.asn1.ASN1Integer DEFAULT_TRAILER_FIELD = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(1);
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier hashAlgorithm;
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier maskGenAlgorithm;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer saltLength;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer trailerField;

    public static com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.RSASSAPSSparams(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public RSASSAPSSparams() {
        this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
        this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
        this.saltLength = DEFAULT_SALT_LENGTH;
        this.trailerField = DEFAULT_TRAILER_FIELD;
    }

    public RSASSAPSSparams(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2, com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer, com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer2) {
        this.hashAlgorithm = algorithmIdentifier;
        this.maskGenAlgorithm = algorithmIdentifier2;
        this.saltLength = aSN1Integer;
        this.trailerField = aSN1Integer2;
    }

    private RSASSAPSSparams(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.hashAlgorithm = DEFAULT_HASH_ALGORITHM;
        this.maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
        this.saltLength = DEFAULT_SALT_LENGTH;
        this.trailerField = DEFAULT_TRAILER_FIELD;
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
                    this.saltLength = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1TaggedObject, true);
                    break;
                case 3:
                    this.trailerField = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1TaggedObject, true);
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

    public java.math.BigInteger getSaltLength() {
        return this.saltLength.getValue();
    }

    public java.math.BigInteger getTrailerField() {
        return this.trailerField.getValue();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(4);
        if (!this.hashAlgorithm.equals(DEFAULT_HASH_ALGORITHM)) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, this.hashAlgorithm));
        }
        if (!this.maskGenAlgorithm.equals(DEFAULT_MASK_GEN_FUNCTION)) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 1, this.maskGenAlgorithm));
        }
        if (!this.saltLength.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) DEFAULT_SALT_LENGTH)) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 2, this.saltLength));
        }
        if (!this.trailerField.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) DEFAULT_TRAILER_FIELD)) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 3, this.trailerField));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
