package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric;

/* loaded from: classes4.dex */
public class RSA {
    private static final java.lang.String PREFIX = "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.";
    private static final java.util.Map<java.lang.String, java.lang.String> generalRsaAttributes = new java.util.HashMap();

    static {
        generalRsaAttributes.put("SupportedKeyClasses", "javax.crypto.interfaces.RSAPublicKey|javax.crypto.interfaces.RSAPrivateKey");
        generalRsaAttributes.put("SupportedKeyFormats", "PKCS#8|X.509");
    }

    public static class Mappings extends com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider {
        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("AlgorithmParameters.PSS", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi$PSS");
            configurableProvider.addAttributes("Cipher.RSA", com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.RSA.generalRsaAttributes);
            configurableProvider.addAlgorithm("Cipher.RSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi$NoPadding");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA/RAW", android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//RAW", android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RSA//NOPADDING", android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA);
            configurableProvider.addAlgorithm("KeyFactory.RSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi");
            configurableProvider.addAlgorithm("KeyPairGenerator.RSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyPairGeneratorSpi");
            com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi keyFactorySpi = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi();
            registerOid(configurableProvider, com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.rsaEncryption, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA, keyFactorySpi);
            registerOid(configurableProvider, com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_ea_rsa, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA, keyFactorySpi);
            registerOid(configurableProvider, com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_RSAES_OAEP, android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA, keyFactorySpi);
        }

        private void addDigestSignature(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider, java.lang.String str, java.lang.String str2, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
            java.lang.String str3 = str + "WITHRSA";
            java.lang.String str4 = str + "withRSA";
            java.lang.String str5 = str + "WithRSA";
            java.lang.String str6 = str + "/RSA";
            java.lang.String str7 = str + "WITHRSAENCRYPTION";
            java.lang.String str8 = str + "withRSAEncryption";
            configurableProvider.addAlgorithm("Signature." + str3, str2);
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str4, str3);
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str5, str3);
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str7, str3);
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str8, str3);
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + (str + "WithRSAEncryption"), str3);
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str6, str3);
            if (aSN1ObjectIdentifier != null) {
                configurableProvider.addAlgorithm("Alg.Alias.Signature." + aSN1ObjectIdentifier, str3);
                configurableProvider.addAlgorithm("Alg.Alias.Signature.OID." + aSN1ObjectIdentifier, str3);
            }
        }

        private void addISO9796Signature(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider, java.lang.String str, java.lang.String str2) {
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "withRSA/ISO9796-2", str + "WITHRSA/ISO9796-2");
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "WithRSA/ISO9796-2", str + "WITHRSA/ISO9796-2");
            configurableProvider.addAlgorithm("Signature." + str + "WITHRSA/ISO9796-2", str2);
        }

        private void addPSSSignature(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider, java.lang.String str, java.lang.String str2) {
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "withRSA/PSS", str + "WITHRSAANDMGF1");
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "WithRSA/PSS", str + "WITHRSAANDMGF1");
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "withRSAandMGF1", str + "WITHRSAANDMGF1");
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "WithRSAAndMGF1", str + "WITHRSAANDMGF1");
            configurableProvider.addAlgorithm("Signature." + str + "WITHRSAANDMGF1", str2);
        }

        private void addX931Signature(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider, java.lang.String str, java.lang.String str2) {
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "withRSA/X9.31", str + "WITHRSA/X9.31");
            configurableProvider.addAlgorithm("Alg.Alias.Signature." + str + "WithRSA/X9.31", str + "WITHRSA/X9.31");
            configurableProvider.addAlgorithm("Signature." + str + "WITHRSA/X9.31", str2);
        }
    }
}
