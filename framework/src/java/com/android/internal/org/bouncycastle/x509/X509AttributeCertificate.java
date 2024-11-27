package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
public interface X509AttributeCertificate extends java.security.cert.X509Extension {
    void checkValidity() throws java.security.cert.CertificateExpiredException, java.security.cert.CertificateNotYetValidException;

    void checkValidity(java.util.Date date) throws java.security.cert.CertificateExpiredException, java.security.cert.CertificateNotYetValidException;

    com.android.internal.org.bouncycastle.x509.X509Attribute[] getAttributes();

    com.android.internal.org.bouncycastle.x509.X509Attribute[] getAttributes(java.lang.String str);

    byte[] getEncoded() throws java.io.IOException;

    com.android.internal.org.bouncycastle.x509.AttributeCertificateHolder getHolder();

    com.android.internal.org.bouncycastle.x509.AttributeCertificateIssuer getIssuer();

    boolean[] getIssuerUniqueID();

    java.util.Date getNotAfter();

    java.util.Date getNotBefore();

    java.math.BigInteger getSerialNumber();

    byte[] getSignature();

    int getVersion();

    void verify(java.security.PublicKey publicKey, java.lang.String str) throws java.security.cert.CertificateException, java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.NoSuchProviderException, java.security.SignatureException;
}
