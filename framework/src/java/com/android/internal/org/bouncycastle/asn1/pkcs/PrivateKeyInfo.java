package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class PrivateKeyInfo extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1Set attributes;
    private com.android.internal.org.bouncycastle.asn1.ASN1OctetString privateKey;
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier privateKeyAlgorithm;
    private com.android.internal.org.bouncycastle.asn1.ASN1BitString publicKey;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer version;

    public static com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private static int getVersionValue(com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer) {
        int intValueExact = aSN1Integer.intValueExact();
        if (intValueExact < 0 || intValueExact > 1) {
            throw new java.lang.IllegalArgumentException("invalid version for private key info");
        }
        return intValueExact;
    }

    public PrivateKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.io.IOException {
        this(algorithmIdentifier, aSN1Encodable, null, null);
    }

    public PrivateKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set) throws java.io.IOException {
        this(algorithmIdentifier, aSN1Encodable, aSN1Set, null);
    }

    public PrivateKeyInfo(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set, byte[] bArr) throws java.io.IOException {
        this.version = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bArr != null ? com.android.internal.org.bouncycastle.util.BigIntegers.ONE : com.android.internal.org.bouncycastle.util.BigIntegers.ZERO);
        this.privateKeyAlgorithm = algorithmIdentifier;
        this.privateKey = new com.android.internal.org.bouncycastle.asn1.DEROctetString(aSN1Encodable);
        this.attributes = aSN1Set;
        this.publicKey = bArr == null ? null : new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr);
    }

    private PrivateKeyInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        this.version = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(objects.nextElement());
        int versionValue = getVersionValue(this.version);
        this.privateKeyAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(objects.nextElement());
        this.privateKey = com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(objects.nextElement());
        int i = -1;
        while (objects.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) objects.nextElement();
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo <= i) {
                throw new java.lang.IllegalArgumentException("invalid optional field in private key info");
            }
            switch (tagNo) {
                case 0:
                    this.attributes = com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(aSN1TaggedObject, false);
                    break;
                case 1:
                    if (versionValue < 1) {
                        throw new java.lang.IllegalArgumentException("'publicKey' requires version v2(1) or later");
                    }
                    this.publicKey = com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1TaggedObject, false);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("unknown optional field in private key info");
            }
            i = tagNo;
        }
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getVersion() {
        return this.version;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Set getAttributes() {
        return this.attributes;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getPrivateKeyAlgorithm() {
        return this.privateKeyAlgorithm;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1OctetString getPrivateKey() {
        return new com.android.internal.org.bouncycastle.asn1.DEROctetString(this.privateKey.getOctets());
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable parsePrivateKey() throws java.io.IOException {
        return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(this.privateKey.getOctets());
    }

    public boolean hasPublicKey() {
        return this.publicKey != null;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable parsePublicKey() throws java.io.IOException {
        if (this.publicKey == null) {
            return null;
        }
        return com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(this.publicKey.getOctets());
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1BitString getPublicKeyData() {
        return this.publicKey;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(5);
        aSN1EncodableVector.add(this.version);
        aSN1EncodableVector.add(this.privateKeyAlgorithm);
        aSN1EncodableVector.add(this.privateKey);
        if (this.attributes != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 0, this.attributes));
        }
        if (this.publicKey != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, this.publicKey));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
