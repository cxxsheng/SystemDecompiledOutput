package com.android.internal.org.bouncycastle.jcajce.util;

/* loaded from: classes4.dex */
public class NamedJcaJceHelper implements com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper {
    protected final java.lang.String providerName;

    public NamedJcaJceHelper(java.lang.String str) {
        this.providerName = str;
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.Cipher createCipher(java.lang.String str) throws java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.NoSuchProviderException {
        return javax.crypto.Cipher.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.Mac createMac(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return javax.crypto.Mac.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.KeyAgreement createKeyAgreement(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return javax.crypto.KeyAgreement.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.AlgorithmParameterGenerator createAlgorithmParameterGenerator(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return java.security.AlgorithmParameterGenerator.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.AlgorithmParameters createAlgorithmParameters(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return java.security.AlgorithmParameters.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.KeyGenerator createKeyGenerator(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return javax.crypto.KeyGenerator.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.KeyFactory createKeyFactory(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return java.security.KeyFactory.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.SecretKeyFactory createSecretKeyFactory(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return javax.crypto.SecretKeyFactory.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.KeyPairGenerator createKeyPairGenerator(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return java.security.KeyPairGenerator.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.MessageDigest createDigest(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return java.security.MessageDigest.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.MessageDigest createMessageDigest(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return java.security.MessageDigest.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.Signature createSignature(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return java.security.Signature.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.cert.CertificateFactory createCertificateFactory(java.lang.String str) throws java.security.cert.CertificateException, java.security.NoSuchProviderException {
        return java.security.cert.CertificateFactory.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.SecureRandom createSecureRandom(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return java.security.SecureRandom.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.cert.CertPathBuilder createCertPathBuilder(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return java.security.cert.CertPathBuilder.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.cert.CertPathValidator createCertPathValidator(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return java.security.cert.CertPathValidator.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.cert.CertStore createCertStore(java.lang.String str, java.security.cert.CertStoreParameters certStoreParameters) throws java.security.NoSuchAlgorithmException, java.security.InvalidAlgorithmParameterException, java.security.NoSuchProviderException {
        return java.security.cert.CertStore.getInstance(str, certStoreParameters, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public javax.crypto.ExemptionMechanism createExemptionMechanism(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return javax.crypto.ExemptionMechanism.getInstance(str, this.providerName);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper
    public java.security.KeyStore createKeyStore(java.lang.String str) throws java.security.KeyStoreException, java.security.NoSuchProviderException {
        return java.security.KeyStore.getInstance(str, this.providerName);
    }
}
