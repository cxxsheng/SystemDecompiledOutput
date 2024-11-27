package com.android.internal.org.bouncycastle.jcajce.provider.symmetric;

/* loaded from: classes4.dex */
public final class Blowfish {
    private Blowfish() {
    }

    public static class ECB extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher {
        public ECB() {
            super(new com.android.internal.org.bouncycastle.crypto.engines.BlowfishEngine());
        }
    }

    public static class CBC extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher {
        public CBC() {
            super(new com.android.internal.org.bouncycastle.crypto.modes.CBCBlockCipher(new com.android.internal.org.bouncycastle.crypto.engines.BlowfishEngine()), 64);
        }
    }

    public static class KeyGen extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator {
        public KeyGen() {
            super("Blowfish", 128, new com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator());
        }
    }

    public static class AlgParams extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters {
        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters, java.security.AlgorithmParametersSpi
        protected java.lang.String engineToString() {
            return "Blowfish IV";
        }
    }

    public static class Mappings extends com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider {
        private static final java.lang.String PREFIX = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.Blowfish.class.getName();

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("Cipher.BLOWFISH", PREFIX + "$ECB");
            configurableProvider.addAlgorithm("KeyGenerator.BLOWFISH", PREFIX + "$KeyGen");
            configurableProvider.addAlgorithm("Alg.Alias.KeyGenerator", com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CBC, "BLOWFISH");
            configurableProvider.addAlgorithm("AlgorithmParameters.BLOWFISH", PREFIX + "$AlgParams");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters", com.android.internal.org.bouncycastle.asn1.misc.MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CBC, "BLOWFISH");
        }
    }
}
