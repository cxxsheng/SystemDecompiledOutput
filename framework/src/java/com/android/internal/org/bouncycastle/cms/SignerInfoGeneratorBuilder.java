package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class SignerInfoGeneratorBuilder {
    private com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestProvider;
    private boolean directSignature;
    private com.android.internal.org.bouncycastle.cms.CMSSignatureEncryptionAlgorithmFinder sigEncAlgFinder;
    private com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator signedGen;
    private com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator unsignedGen;

    public SignerInfoGeneratorBuilder(com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestCalculatorProvider) {
        this(digestCalculatorProvider, new com.android.internal.org.bouncycastle.cms.DefaultCMSSignatureEncryptionAlgorithmFinder());
    }

    public SignerInfoGeneratorBuilder(com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestCalculatorProvider, com.android.internal.org.bouncycastle.cms.CMSSignatureEncryptionAlgorithmFinder cMSSignatureEncryptionAlgorithmFinder) {
        this.digestProvider = digestCalculatorProvider;
        this.sigEncAlgFinder = cMSSignatureEncryptionAlgorithmFinder;
    }

    public com.android.internal.org.bouncycastle.cms.SignerInfoGeneratorBuilder setDirectSignature(boolean z) {
        this.directSignature = z;
        return this;
    }

    public com.android.internal.org.bouncycastle.cms.SignerInfoGeneratorBuilder setSignedAttributeGenerator(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator cMSAttributeTableGenerator) {
        this.signedGen = cMSAttributeTableGenerator;
        return this;
    }

    public com.android.internal.org.bouncycastle.cms.SignerInfoGeneratorBuilder setUnsignedAttributeGenerator(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator cMSAttributeTableGenerator) {
        this.unsignedGen = cMSAttributeTableGenerator;
        return this;
    }

    public com.android.internal.org.bouncycastle.cms.SignerInfoGenerator build(com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        com.android.internal.org.bouncycastle.cms.SignerInfoGenerator createGenerator = createGenerator(contentSigner, new com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier(new com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber(x509CertificateHolder.toASN1Structure())));
        createGenerator.setAssociatedCertificate(x509CertificateHolder);
        return createGenerator;
    }

    public com.android.internal.org.bouncycastle.cms.SignerInfoGenerator build(com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, byte[] bArr) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        return createGenerator(contentSigner, new com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) new com.android.internal.org.bouncycastle.asn1.DEROctetString(bArr)));
    }

    private com.android.internal.org.bouncycastle.cms.SignerInfoGenerator createGenerator(com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier signerIdentifier) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        if (this.directSignature) {
            return new com.android.internal.org.bouncycastle.cms.SignerInfoGenerator(signerIdentifier, contentSigner, this.digestProvider, this.sigEncAlgFinder, true);
        }
        if (this.signedGen != null || this.unsignedGen != null) {
            if (this.signedGen == null) {
                this.signedGen = new com.android.internal.org.bouncycastle.cms.DefaultSignedAttributeTableGenerator();
            }
            return new com.android.internal.org.bouncycastle.cms.SignerInfoGenerator(signerIdentifier, contentSigner, this.digestProvider, this.sigEncAlgFinder, this.signedGen, this.unsignedGen);
        }
        return new com.android.internal.org.bouncycastle.cms.SignerInfoGenerator(signerIdentifier, contentSigner, this.digestProvider, this.sigEncAlgFinder);
    }
}
