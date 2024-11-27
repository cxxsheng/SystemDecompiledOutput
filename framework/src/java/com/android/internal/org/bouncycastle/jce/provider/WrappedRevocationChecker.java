package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
class WrappedRevocationChecker implements com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker {
    private final java.security.cert.PKIXCertPathChecker checker;

    public WrappedRevocationChecker(java.security.cert.PKIXCertPathChecker pKIXCertPathChecker) {
        this.checker = pKIXCertPathChecker;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void setParameter(java.lang.String str, java.lang.Object obj) {
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void initialize(com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters) throws java.security.cert.CertPathValidatorException {
        this.checker.init(false);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void check(java.security.cert.Certificate certificate) throws java.security.cert.CertPathValidatorException {
        this.checker.check(certificate);
    }
}
