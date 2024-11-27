package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class SignerInfoGenerator {
    private byte[] calculatedDigest;
    private com.android.internal.org.bouncycastle.cert.X509CertificateHolder certHolder;
    private final com.android.internal.org.bouncycastle.operator.DigestAlgorithmIdentifierFinder digAlgFinder;
    private final com.android.internal.org.bouncycastle.operator.DigestCalculator digester;
    private final com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator sAttrGen;
    private final com.android.internal.org.bouncycastle.cms.CMSSignatureEncryptionAlgorithmFinder sigEncAlgFinder;
    private final com.android.internal.org.bouncycastle.operator.ContentSigner signer;
    private final com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier signerIdentifier;
    private final com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator unsAttrGen;

    SignerInfoGenerator(com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier signerIdentifier, com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestCalculatorProvider, com.android.internal.org.bouncycastle.cms.CMSSignatureEncryptionAlgorithmFinder cMSSignatureEncryptionAlgorithmFinder) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        this(signerIdentifier, contentSigner, digestCalculatorProvider, cMSSignatureEncryptionAlgorithmFinder, false);
    }

    SignerInfoGenerator(com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier signerIdentifier, com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestCalculatorProvider, com.android.internal.org.bouncycastle.cms.CMSSignatureEncryptionAlgorithmFinder cMSSignatureEncryptionAlgorithmFinder, boolean z) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        this.digAlgFinder = new com.android.internal.org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder();
        this.calculatedDigest = null;
        this.signerIdentifier = signerIdentifier;
        this.signer = contentSigner;
        if (digestCalculatorProvider != null) {
            this.digester = digestCalculatorProvider.get(this.digAlgFinder.find(contentSigner.getAlgorithmIdentifier()));
        } else {
            this.digester = null;
        }
        if (z) {
            this.sAttrGen = null;
            this.unsAttrGen = null;
        } else {
            this.sAttrGen = new com.android.internal.org.bouncycastle.cms.DefaultSignedAttributeTableGenerator();
            this.unsAttrGen = null;
        }
        this.sigEncAlgFinder = cMSSignatureEncryptionAlgorithmFinder;
    }

    public SignerInfoGenerator(com.android.internal.org.bouncycastle.cms.SignerInfoGenerator signerInfoGenerator, com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator cMSAttributeTableGenerator, com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator cMSAttributeTableGenerator2) {
        this.digAlgFinder = new com.android.internal.org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder();
        this.calculatedDigest = null;
        this.signerIdentifier = signerInfoGenerator.signerIdentifier;
        this.signer = signerInfoGenerator.signer;
        this.digester = signerInfoGenerator.digester;
        this.sigEncAlgFinder = signerInfoGenerator.sigEncAlgFinder;
        this.sAttrGen = cMSAttributeTableGenerator;
        this.unsAttrGen = cMSAttributeTableGenerator2;
    }

    SignerInfoGenerator(com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier signerIdentifier, com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestCalculatorProvider, com.android.internal.org.bouncycastle.cms.CMSSignatureEncryptionAlgorithmFinder cMSSignatureEncryptionAlgorithmFinder, com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator cMSAttributeTableGenerator, com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator cMSAttributeTableGenerator2) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        this.digAlgFinder = new com.android.internal.org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder();
        this.calculatedDigest = null;
        this.signerIdentifier = signerIdentifier;
        this.signer = contentSigner;
        if (digestCalculatorProvider != null) {
            this.digester = digestCalculatorProvider.get(this.digAlgFinder.find(contentSigner.getAlgorithmIdentifier()));
        } else {
            this.digester = null;
        }
        this.sAttrGen = cMSAttributeTableGenerator;
        this.unsAttrGen = cMSAttributeTableGenerator2;
        this.sigEncAlgFinder = cMSSignatureEncryptionAlgorithmFinder;
    }

    public com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier getSID() {
        return this.signerIdentifier;
    }

    public int getGeneratedVersion() {
        return this.signerIdentifier.isTagged() ? 3 : 1;
    }

    public boolean hasAssociatedCertificate() {
        return this.certHolder != null;
    }

    public com.android.internal.org.bouncycastle.cert.X509CertificateHolder getAssociatedCertificate() {
        return this.certHolder;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getDigestAlgorithm() {
        if (this.digester != null) {
            return this.digester.getAlgorithmIdentifier();
        }
        return this.digAlgFinder.find(this.signer.getAlgorithmIdentifier());
    }

    public java.io.OutputStream getCalculatingOutputStream() {
        if (this.digester != null) {
            if (this.sAttrGen == null) {
                return new com.android.internal.org.bouncycastle.util.io.TeeOutputStream(this.digester.getOutputStream(), this.signer.getOutputStream());
            }
            return this.digester.getOutputStream();
        }
        return this.signer.getOutputStream();
    }

    public com.android.internal.org.bouncycastle.asn1.cms.SignerInfo generate(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) throws com.android.internal.org.bouncycastle.cms.CMSException {
        com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier;
        com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set;
        com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set2;
        try {
            com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier findEncryptionAlgorithm = this.sigEncAlgFinder.findEncryptionAlgorithm(this.signer.getAlgorithmIdentifier());
            if (this.sAttrGen != null) {
                com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2 = this.digester.getAlgorithmIdentifier();
                this.calculatedDigest = this.digester.getDigest();
                com.android.internal.org.bouncycastle.asn1.ASN1Set attributeSet = getAttributeSet(this.sAttrGen.getAttributes(java.util.Collections.unmodifiableMap(getBaseParameters(aSN1ObjectIdentifier, this.digester.getAlgorithmIdentifier(), findEncryptionAlgorithm, this.calculatedDigest))));
                java.io.OutputStream outputStream = this.signer.getOutputStream();
                outputStream.write(attributeSet.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
                outputStream.close();
                algorithmIdentifier = algorithmIdentifier2;
                aSN1Set = attributeSet;
            } else if (this.digester != null) {
                com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier3 = this.digester.getAlgorithmIdentifier();
                this.calculatedDigest = this.digester.getDigest();
                algorithmIdentifier = algorithmIdentifier3;
                aSN1Set = null;
            } else {
                com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier find = this.digAlgFinder.find(this.signer.getAlgorithmIdentifier());
                this.calculatedDigest = null;
                algorithmIdentifier = find;
                aSN1Set = null;
            }
            byte[] signature = this.signer.getSignature();
            if (this.unsAttrGen == null) {
                aSN1Set2 = null;
            } else {
                java.util.Map baseParameters = getBaseParameters(aSN1ObjectIdentifier, algorithmIdentifier, findEncryptionAlgorithm, this.calculatedDigest);
                baseParameters.put(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.SIGNATURE, com.android.internal.org.bouncycastle.util.Arrays.clone(signature));
                aSN1Set2 = getAttributeSet(this.unsAttrGen.getAttributes(java.util.Collections.unmodifiableMap(baseParameters)));
            }
            return new com.android.internal.org.bouncycastle.asn1.cms.SignerInfo(this.signerIdentifier, algorithmIdentifier, aSN1Set, findEncryptionAlgorithm, new com.android.internal.org.bouncycastle.asn1.DEROctetString(signature), aSN1Set2);
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.cms.CMSException("encoding error.", e);
        }
    }

    void setAssociatedCertificate(com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) {
        this.certHolder = x509CertificateHolder;
    }

    private com.android.internal.org.bouncycastle.asn1.ASN1Set getAttributeSet(com.android.internal.org.bouncycastle.asn1.cms.AttributeTable attributeTable) {
        if (attributeTable != null) {
            return new com.android.internal.org.bouncycastle.asn1.DERSet(attributeTable.toASN1EncodableVector());
        }
        return null;
    }

    private java.util.Map getBaseParameters(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2, byte[] bArr) {
        java.util.HashMap hashMap = new java.util.HashMap();
        if (aSN1ObjectIdentifier != null) {
            hashMap.put(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.CONTENT_TYPE, aSN1ObjectIdentifier);
        }
        hashMap.put(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.DIGEST_ALGORITHM_IDENTIFIER, algorithmIdentifier);
        hashMap.put(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.SIGNATURE_ALGORITHM_IDENTIFIER, algorithmIdentifier2);
        hashMap.put(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.DIGEST, com.android.internal.org.bouncycastle.util.Arrays.clone(bArr));
        return hashMap;
    }

    public byte[] getCalculatedDigest() {
        if (this.calculatedDigest != null) {
            return com.android.internal.org.bouncycastle.util.Arrays.clone(this.calculatedDigest);
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator getSignedAttributeTableGenerator() {
        return this.sAttrGen;
    }

    public com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator getUnsignedAttributeTableGenerator() {
        return this.unsAttrGen;
    }
}
