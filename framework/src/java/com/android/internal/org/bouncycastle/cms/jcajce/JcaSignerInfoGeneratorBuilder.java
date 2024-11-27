package com.android.internal.org.bouncycastle.cms.jcajce;

/* loaded from: classes4.dex */
public class JcaSignerInfoGeneratorBuilder {
    private com.android.internal.org.bouncycastle.cms.SignerInfoGeneratorBuilder builder;

    public JcaSignerInfoGeneratorBuilder(com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestCalculatorProvider) {
        this(digestCalculatorProvider, new com.android.internal.org.bouncycastle.cms.DefaultCMSSignatureEncryptionAlgorithmFinder());
    }

    public JcaSignerInfoGeneratorBuilder(com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestCalculatorProvider, com.android.internal.org.bouncycastle.cms.CMSSignatureEncryptionAlgorithmFinder cMSSignatureEncryptionAlgorithmFinder) {
        this.builder = new com.android.internal.org.bouncycastle.cms.SignerInfoGeneratorBuilder(digestCalculatorProvider, cMSSignatureEncryptionAlgorithmFinder);
    }

    public com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder setDirectSignature(boolean z) {
        this.builder.setDirectSignature(z);
        return this;
    }

    public com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder setSignedAttributeGenerator(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator cMSAttributeTableGenerator) {
        this.builder.setSignedAttributeGenerator(cMSAttributeTableGenerator);
        return this;
    }

    public com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder setUnsignedAttributeGenerator(com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator cMSAttributeTableGenerator) {
        this.builder.setUnsignedAttributeGenerator(cMSAttributeTableGenerator);
        return this;
    }

    public com.android.internal.org.bouncycastle.cms.SignerInfoGenerator build(com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        return this.builder.build(contentSigner, x509CertificateHolder);
    }

    public com.android.internal.org.bouncycastle.cms.SignerInfoGenerator build(com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, byte[] bArr) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        return this.builder.build(contentSigner, bArr);
    }

    public com.android.internal.org.bouncycastle.cms.SignerInfoGenerator build(com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, java.security.cert.X509Certificate x509Certificate) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException, java.security.cert.CertificateEncodingException {
        return build(contentSigner, new com.android.internal.org.bouncycastle.cert.jcajce.JcaX509CertificateHolder(x509Certificate));
    }
}
