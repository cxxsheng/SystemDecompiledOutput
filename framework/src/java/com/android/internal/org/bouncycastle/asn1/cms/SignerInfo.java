package com.android.internal.org.bouncycastle.asn1.cms;

/* loaded from: classes4.dex */
public class SignerInfo extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1Set authenticatedAttributes;
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier digAlgorithm;
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier digEncryptionAlgorithm;
    private com.android.internal.org.bouncycastle.asn1.ASN1OctetString encryptedDigest;
    private com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier sid;
    private com.android.internal.org.bouncycastle.asn1.ASN1Set unauthenticatedAttributes;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer version;

    public static com.android.internal.org.bouncycastle.asn1.cms.SignerInfo getInstance(java.lang.Object obj) throws java.lang.IllegalArgumentException {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.cms.SignerInfo) {
            return (com.android.internal.org.bouncycastle.asn1.cms.SignerInfo) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.cms.SignerInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public SignerInfo(com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier signerIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2, com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set2) {
        if (signerIdentifier.isTagged()) {
            this.version = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(3L);
        } else {
            this.version = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(1L);
        }
        this.sid = signerIdentifier;
        this.digAlgorithm = algorithmIdentifier;
        this.authenticatedAttributes = aSN1Set;
        this.digEncryptionAlgorithm = algorithmIdentifier2;
        this.encryptedDigest = aSN1OctetString;
        this.unauthenticatedAttributes = aSN1Set2;
    }

    public SignerInfo(com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier signerIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.cms.Attributes attributes, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2, com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString, com.android.internal.org.bouncycastle.asn1.cms.Attributes attributes2) {
        if (signerIdentifier.isTagged()) {
            this.version = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(3L);
        } else {
            this.version = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(1L);
        }
        this.sid = signerIdentifier;
        this.digAlgorithm = algorithmIdentifier;
        this.authenticatedAttributes = com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(attributes);
        this.digEncryptionAlgorithm = algorithmIdentifier2;
        this.encryptedDigest = aSN1OctetString;
        this.unauthenticatedAttributes = com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(attributes2);
    }

    public SignerInfo(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        this.version = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) objects.nextElement();
        this.sid = com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier.getInstance(objects.nextElement());
        this.digAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(objects.nextElement());
        java.lang.Object nextElement = objects.nextElement();
        if (nextElement instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            this.authenticatedAttributes = com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) nextElement, false);
            this.digEncryptionAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(objects.nextElement());
        } else {
            this.authenticatedAttributes = null;
            this.digEncryptionAlgorithm = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(nextElement);
        }
        this.encryptedDigest = com.android.internal.org.bouncycastle.asn1.DEROctetString.getInstance(objects.nextElement());
        if (objects.hasMoreElements()) {
            this.unauthenticatedAttributes = com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) objects.nextElement(), false);
        } else {
            this.unauthenticatedAttributes = null;
        }
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getVersion() {
        return this.version;
    }

    public com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier getSID() {
        return this.sid;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Set getAuthenticatedAttributes() {
        return this.authenticatedAttributes;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getDigestAlgorithm() {
        return this.digAlgorithm;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1OctetString getEncryptedDigest() {
        return this.encryptedDigest;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getDigestEncryptionAlgorithm() {
        return this.digEncryptionAlgorithm;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Set getUnauthenticatedAttributes() {
        return this.unauthenticatedAttributes;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(7);
        aSN1EncodableVector.add(this.version);
        aSN1EncodableVector.add(this.sid);
        aSN1EncodableVector.add(this.digAlgorithm);
        if (this.authenticatedAttributes != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 0, this.authenticatedAttributes));
        }
        aSN1EncodableVector.add(this.digEncryptionAlgorithm);
        aSN1EncodableVector.add(this.encryptedDigest);
        if (this.unauthenticatedAttributes != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, this.unauthenticatedAttributes));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
