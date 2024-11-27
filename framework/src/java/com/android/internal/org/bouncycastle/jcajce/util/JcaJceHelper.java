package com.android.internal.org.bouncycastle.jcajce.util;

/* loaded from: classes4.dex */
public interface JcaJceHelper {
    java.security.AlgorithmParameterGenerator createAlgorithmParameterGenerator(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    java.security.AlgorithmParameters createAlgorithmParameters(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    java.security.cert.CertPathBuilder createCertPathBuilder(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    java.security.cert.CertPathValidator createCertPathValidator(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    java.security.cert.CertStore createCertStore(java.lang.String str, java.security.cert.CertStoreParameters certStoreParameters) throws java.security.NoSuchAlgorithmException, java.security.InvalidAlgorithmParameterException, java.security.NoSuchProviderException;

    java.security.cert.CertificateFactory createCertificateFactory(java.lang.String str) throws java.security.NoSuchProviderException, java.security.cert.CertificateException;

    javax.crypto.Cipher createCipher(java.lang.String str) throws java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.NoSuchProviderException;

    java.security.MessageDigest createDigest(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    javax.crypto.ExemptionMechanism createExemptionMechanism(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    javax.crypto.KeyAgreement createKeyAgreement(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    java.security.KeyFactory createKeyFactory(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    javax.crypto.KeyGenerator createKeyGenerator(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    java.security.KeyPairGenerator createKeyPairGenerator(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    java.security.KeyStore createKeyStore(java.lang.String str) throws java.security.KeyStoreException, java.security.NoSuchProviderException;

    javax.crypto.Mac createMac(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    java.security.MessageDigest createMessageDigest(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    javax.crypto.SecretKeyFactory createSecretKeyFactory(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    java.security.SecureRandom createSecureRandom(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;

    java.security.Signature createSignature(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException;
}
