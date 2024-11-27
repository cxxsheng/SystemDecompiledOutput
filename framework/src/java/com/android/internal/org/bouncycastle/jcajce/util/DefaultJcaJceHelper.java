package com.android.internal.org.bouncycastle.jcajce.util;

/* loaded from: classes4.dex */
public class DefaultJcaJceHelper implements com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper {
    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.Cipher createCipher(java.lang.String str) throws java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException {
        return javax.crypto.Cipher.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.Mac createMac(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return javax.crypto.Mac.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.KeyAgreement createKeyAgreement(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return javax.crypto.KeyAgreement.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.AlgorithmParameterGenerator createAlgorithmParameterGenerator(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return java.security.AlgorithmParameterGenerator.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.AlgorithmParameters createAlgorithmParameters(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return java.security.AlgorithmParameters.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.KeyGenerator createKeyGenerator(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return javax.crypto.KeyGenerator.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.KeyFactory createKeyFactory(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return java.security.KeyFactory.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.SecretKeyFactory createSecretKeyFactory(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return javax.crypto.SecretKeyFactory.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.KeyPairGenerator createKeyPairGenerator(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return java.security.KeyPairGenerator.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.MessageDigest createDigest(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return java.security.MessageDigest.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.MessageDigest createMessageDigest(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return java.security.MessageDigest.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.Signature createSignature(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return java.security.Signature.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.cert.CertificateFactory createCertificateFactory(java.lang.String str) throws java.security.cert.CertificateException {
        return java.security.cert.CertificateFactory.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.SecureRandom createSecureRandom(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return java.security.SecureRandom.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.cert.CertPathBuilder createCertPathBuilder(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return java.security.cert.CertPathBuilder.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.cert.CertPathValidator createCertPathValidator(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return java.security.cert.CertPathValidator.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.cert.CertStore createCertStore(java.lang.String str, java.security.cert.CertStoreParameters certStoreParameters) throws java.security.NoSuchAlgorithmException, java.security.InvalidAlgorithmParameterException {
        return java.security.cert.CertStore.getInstance(str, certStoreParameters);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.ExemptionMechanism createExemptionMechanism(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        return javax.crypto.ExemptionMechanism.getInstance(str);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.KeyStore createKeyStore(java.lang.String str) throws java.security.KeyStoreException {
        return java.security.KeyStore.getInstance(str);
    }
}
