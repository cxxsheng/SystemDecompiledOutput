package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric;

/* loaded from: classes4.dex */
public class DSA {
    private static final java.lang.String PREFIX = "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.";

    public static class Mappings extends com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider {
        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("AlgorithmParameters.DSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.AlgorithmParametersSpi");
            configurableProvider.addAlgorithm("AlgorithmParameterGenerator.DSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.AlgorithmParameterGeneratorSpi");
            configurableProvider.addAlgorithm("KeyPairGenerator.DSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.KeyPairGeneratorSpi");
            configurableProvider.addAlgorithm("KeyFactory.DSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.KeyFactorySpi");
            configurableProvider.addAlgorithm("Signature.SHA1withDSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner$stdDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.DSA", "SHA1withDSA");
            configurableProvider.addAlgorithm("Signature.NONEWITHDSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner$noneDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.RAWDSA", "NONEWITHDSA");
            addSignatureAlgorithm(configurableProvider, "SHA224", "DSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsa224", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha224);
            addSignatureAlgorithm(configurableProvider, "SHA256", "DSA", "com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner$dsa256", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.dsa_with_sha256);
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA/DSA", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1withDSA", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1WITHDSA", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10040.4.1", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.1.3.14.3.2.26with1.2.840.10040.4.3", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.DSAwithSHA1", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.DSAWITHSHA1", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.SHA1WithDSA", "SHA1withDSA");
            configurableProvider.addAlgorithm("Alg.Alias.Signature.DSAWithSHA1", "SHA1withDSA");
            com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.KeyFactorySpi keyFactorySpi = new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.KeyFactorySpi();
            for (int i = 0; i != com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil.dsaOids.length; i++) {
                configurableProvider.addAlgorithm("Alg.Alias.Signature." + com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil.dsaOids[i], "SHA1withDSA");
                registerOid(configurableProvider, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil.dsaOids[i], "DSA", keyFactorySpi);
                registerOidAlgorithmParameterGenerator(configurableProvider, com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil.dsaOids[i], "DSA");
            }
        }
    }
}
