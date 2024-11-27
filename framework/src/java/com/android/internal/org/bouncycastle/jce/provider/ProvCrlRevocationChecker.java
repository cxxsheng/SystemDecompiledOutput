package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
class ProvCrlRevocationChecker implements com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker {
    private java.util.Date currentDate = null;
    private final com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper helper;
    private com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters params;

    public ProvCrlRevocationChecker(com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper jcaJceHelper) {
        this.helper = jcaJceHelper;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void setParameter(java.lang.String str, java.lang.Object obj) {
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void initialize(com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters) {
        this.params = pKIXCertRevocationCheckerParameters;
        this.currentDate = new java.util.Date();
    }

    public void init(boolean z) throws java.security.cert.CertPathValidatorException {
        if (z) {
            throw new java.security.cert.CertPathValidatorException("forward checking not supported");
        }
        this.params = null;
        this.currentDate = new java.util.Date();
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void check(java.security.cert.Certificate certificate) throws java.security.cert.CertPathValidatorException {
        java.lang.Throwable th;
        try {
            com.android.internal.org.bouncycastle.jce.provider.RFC3280CertPathUtilities.checkCRLs(this.params, this.params.getParamsPKIX(), this.currentDate, this.params.getValidDate(), (java.security.cert.X509Certificate) certificate, this.params.getSigningCert(), this.params.getWorkingPublicKey(), this.params.getCertPath().getCertificates(), this.helper);
        } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e) {
            if (e.getCause() == null) {
                th = e;
            } else {
                th = e.getCause();
            }
            throw new java.security.cert.CertPathValidatorException(e.getMessage(), th, this.params.getCertPath(), this.params.getIndex());
        }
    }
}
