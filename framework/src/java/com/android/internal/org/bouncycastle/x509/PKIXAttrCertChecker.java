package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
public abstract class PKIXAttrCertChecker implements java.lang.Cloneable {
    public abstract void check(com.android.internal.org.bouncycastle.x509.X509AttributeCertificate x509AttributeCertificate, java.security.cert.CertPath certPath, java.security.cert.CertPath certPath2, java.util.Collection collection) throws java.security.cert.CertPathValidatorException;

    public abstract java.lang.Object clone();

    public abstract java.util.Set getSupportedExtensions();
}
