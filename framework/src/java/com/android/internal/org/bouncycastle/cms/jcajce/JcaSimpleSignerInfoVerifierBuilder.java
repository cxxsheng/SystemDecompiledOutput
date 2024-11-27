package com.android.internal.org.bouncycastle.cms.jcajce;

/* loaded from: classes4.dex */
public class JcaSimpleSignerInfoVerifierBuilder {
    private com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper helper = new com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper();

    public com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder setProvider(java.security.Provider provider) {
        this.helper = new com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.ProviderHelper(provider);
        return this;
    }

    public com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder setProvider(java.lang.String str) {
        this.helper = new com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.NamedHelper(str);
        return this;
    }

    public com.android.internal.org.bouncycastle.cms.SignerInformationVerifier build(com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException, java.security.cert.CertificateException {
        return new com.android.internal.org.bouncycastle.cms.SignerInformationVerifier(new com.android.internal.org.bouncycastle.cms.DefaultCMSSignatureAlgorithmNameGenerator(), new com.android.internal.org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder(), this.helper.createContentVerifierProvider(x509CertificateHolder), this.helper.createDigestCalculatorProvider());
    }

    public com.android.internal.org.bouncycastle.cms.SignerInformationVerifier build(java.security.cert.X509Certificate x509Certificate) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        return new com.android.internal.org.bouncycastle.cms.SignerInformationVerifier(new com.android.internal.org.bouncycastle.cms.DefaultCMSSignatureAlgorithmNameGenerator(), new com.android.internal.org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder(), this.helper.createContentVerifierProvider(x509Certificate), this.helper.createDigestCalculatorProvider());
    }

    public com.android.internal.org.bouncycastle.cms.SignerInformationVerifier build(java.security.PublicKey publicKey) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        return new com.android.internal.org.bouncycastle.cms.SignerInformationVerifier(new com.android.internal.org.bouncycastle.cms.DefaultCMSSignatureAlgorithmNameGenerator(), new com.android.internal.org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder(), this.helper.createContentVerifierProvider(publicKey), this.helper.createDigestCalculatorProvider());
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

    private class NamedHelper extends com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper {
        private final java.lang.String providerName;

        public NamedHelper(java.lang.String str) {
            super();
            this.providerName = str;
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(java.security.PublicKey publicKey) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(publicKey);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(java.security.cert.X509Certificate x509Certificate) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(x509Certificate);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider createDigestCalculatorProvider() throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder().setProvider(this.providerName).build();
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException, java.security.cert.CertificateException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().setProvider(this.providerName).build(x509CertificateHolder);
        }
    }

    private class ProviderHelper extends com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper {
        private final java.security.Provider provider;

        public ProviderHelper(java.security.Provider provider) {
            super();
            this.provider = provider;
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(java.security.PublicKey publicKey) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().setProvider(this.provider).build(publicKey);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(java.security.cert.X509Certificate x509Certificate) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().setProvider(this.provider).build(x509Certificate);
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider createDigestCalculatorProvider() throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder().setProvider(this.provider).build();
        }

        @Override // com.android.internal.org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder.Helper
        com.android.internal.org.bouncycastle.operator.ContentVerifierProvider createContentVerifierProvider(com.android.internal.org.bouncycastle.cert.X509CertificateHolder x509CertificateHolder) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException, java.security.cert.CertificateException {
            return new com.android.internal.org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder().setProvider(this.provider).build(x509CertificateHolder);
        }
    }
}
