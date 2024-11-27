package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class SignerInformationVerifier {
    private com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestProvider;
    private com.android.internal.org.bouncycastle.operator.SignatureAlgorithmIdentifierFinder sigAlgorithmFinder;
    private com.android.internal.org.bouncycastle.cms.CMSSignatureAlgorithmNameGenerator sigNameGenerator;
    private com.android.internal.org.bouncycastle.operator.ContentVerifierProvider verifierProvider;

    public SignerInformationVerifier(com.android.internal.org.bouncycastle.cms.CMSSignatureAlgorithmNameGenerator cMSSignatureAlgorithmNameGenerator, com.android.internal.org.bouncycastle.operator.SignatureAlgorithmIdentifierFinder signatureAlgorithmIdentifierFinder, com.android.internal.org.bouncycastle.operator.ContentVerifierProvider contentVerifierProvider, com.android.internal.org.bouncycastle.operator.DigestCalculatorProvider digestCalculatorProvider) {
        this.sigNameGenerator = cMSSignatureAlgorithmNameGenerator;
        this.sigAlgorithmFinder = signatureAlgorithmIdentifierFinder;
        this.verifierProvider = contentVerifierProvider;
        this.digestProvider = digestCalculatorProvider;
    }

    public boolean hasAssociatedCertificate() {
        return this.verifierProvider.hasAssociatedCertificate();
    }

    public com.android.internal.org.bouncycastle.cert.X509CertificateHolder getAssociatedCertificate() {
        return this.verifierProvider.getAssociatedCertificate();
    }

    public com.android.internal.org.bouncycastle.operator.ContentVerifier getContentVerifier(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        return this.verifierProvider.get(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(this.sigAlgorithmFinder.find(this.sigNameGenerator.getSignatureName(algorithmIdentifier2, algorithmIdentifier)).getAlgorithm(), algorithmIdentifier.getParameters()));
    }

    public com.android.internal.org.bouncycastle.operator.DigestCalculator getDigestCalculator(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) throws com.android.internal.org.bouncycastle.operator.OperatorCreationException {
        return this.digestProvider.get(algorithmIdentifier);
    }
}
