package com.android.internal.org.bouncycastle.cms.jcajce;

/* loaded from: classes4.dex */
public class JcaSignerInfoVerifierBuilder {
    private com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestProvider;
    private com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper helper = new com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper();
    private com.android.internal.org.bouncycastle.cms.CMSSignatureAlgorithmNameGenerator sigAlgNameGen = new com.android.internal.org.bouncycastle.cms.DefaultCMSSignatureAlgorithmNameGenerator();
    private com.android.internal.org.bouncycastle.operator.SignatureAlgorithmIdentifierFinder sigAlgIDFinder = new com.android.internal.org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder();

    public JcaSignerInfoVerifierBuilder(com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestCalculatorProvider) {
        this.digestProvider = digestCalculatorProvider;
    }

    public com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder setProvider(java.security.Provider provider) {
        this.helper = new com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.ProviderHelper(provider);
        return this;
    }

    public com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder setProvider(java.lang.String str) {
        this.helper = new com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.NamedHelper(str);
        return this;
    }

    public com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder setSignatureAlgorithmNameGenerator(com.android.internal.org.bouncycastle.cms.CMSSignatureAlgorithmNameGenerator cMSSignatureAlgorithmNameGenerator) {
        this.sigAlgNameGen = cMSSignatureAlgorithmNameGenerator;
        return this;
    }

    public com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder setSignatureAlgorithmFinder(com.android.internal.org.bouncycastle.operator.SignatureAlgorithmIdentifierFinder signatureAlgorithmIdentifierFinder) {
        this.sigAlgIDFinder = signatureAlgorithmIdentifierFinder;
        return this;
    }

    public com.android.internal.org.bouncycastle.cms.SignerInformationVerifier build(com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException, java.security.cert.CertificateException {
        return new com.android.internal.org.bouncycastle.cms.SignerInformationVerifier(this.sigAlgNameGen, this.sigAlgIDFinder, this.helper.createContentVerifierProvider(x509CertificateHolder), this.digestProvider);
    }

    public com.android.internal.org.bouncycastle.cms.SignerInformationVerifier build(java.security.cert.X509Certificate x509Certificate) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        return new com.android.internal.org.bouncycastle.cms.SignerInformationVerifier(this.sigAlgNameGen, this.sigAlgIDFinder, this.helper.createContentVerifierProvider(x509Certificate), this.digestProvider);
    }

    public com.android.internal.org.bouncycastle.cms.SignerInformationVerifier build(java.security.PublicKey publicKey) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        return new com.android.internal.org.bouncycastle.cms.SignerInformationVerifier(this.sigAlgNameGen, this.sigAlgIDFinder, this.helper.createContentVerifierProvider(publicKey), this.digestProvider);
    }

    private class Helper {
        private Helper() {
        }

        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(java.security.PublicKey publicKey) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().build(publicKey);
        }

        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(java.security.cert.X509Certificate x509Certificate) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().build(x509Certificate);
        }

        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException, java.security.cert.CertificateException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().build(x509CertificateHolder);
        }

        com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider createDigestCalculatorProvider() throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder().build();
        }
    }

    private class NamedHelper extends com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper {
        private final java.lang.String providerName;

        public NamedHelper(java.lang.String str) {
            super();
            this.providerName = str;
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(java.security.PublicKey publicKey) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(publicKey);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(java.security.cert.X509Certificate x509Certificate) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(x509Certificate);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider createDigestCalculatorProvider() throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder().setProvider(this.providerName).build();
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException, java.security.cert.CertificateException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(x509CertificateHolder);
        }
    }

    private class ProviderHelper extends com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper {
        private final java.security.Provider provider;

        public ProviderHelper(java.security.Provider provider) {
            super();
            this.provider = provider;
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(java.security.PublicKey publicKey) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().setProvider(this.provider).build(publicKey);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(java.security.cert.X509Certificate x509Certificate) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().setProvider(this.provider).build(x509Certificate);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider createDigestCalculatorProvider() throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder().setProvider(this.provider).build();
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException, java.security.cert.CertificateException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().setProvider(this.provider).build(x509CertificateHolder);
        }
    }
}
