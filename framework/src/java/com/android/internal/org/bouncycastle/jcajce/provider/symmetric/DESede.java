package com.android.internal.org.bouncycastle.jcajce.provider.symmetric;

/* loaded from: classes4.dex */
public final class DESede {
    private DESede() {
    }

    public static class ECB extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher {
        public ECB() {
            super(new com.android.internal.org.bouncycastle.crypto.engines.DESedeEngine());
        }
    }

    public static class CBC extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher {
        public CBC() {
            super(new com.android.internal.org.bouncycastle.crypto.modes.CBCBlockCipher(new com.android.internal.org.bouncycastle.crypto.engines.DESedeEngine()), 64);
        }
    }

    public static class DESede64 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac {
        public DESede64() {
            super(new com.android.internal.org.bouncycastle.crypto.macs.CBCBlockCipherMac(new com.android.internal.org.bouncycastle.crypto.engines.DESedeEngine(), 64));
        }
    }

    public static class DESede64with7816d4 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac {
        public DESede64with7816d4() {
            super(new com.android.internal.org.bouncycastle.crypto.macs.CBCBlockCipherMac(new com.android.internal.org.bouncycastle.crypto.engines.DESedeEngine(), 64, new com.android.internal.org.bouncycastle.crypto.paddings.ISO7816d4Padding()));
        }
    }

    public static class CBCMAC extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac {
        public CBCMAC() {
            super(new com.android.internal.org.bouncycastle.crypto.macs.CBCBlockCipherMac(new com.android.internal.org.bouncycastle.crypto.engines.DESedeEngine()));
        }
    }

    public static class Wrap extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher {
        public Wrap() {
            super(new com.android.internal.org.bouncycastle.crypto.engines.DESedeWrapEngine());
        }
    }

    public static class KeyGenerator extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator {
        private boolean keySizeSet;

        public KeyGenerator() {
            super(android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES, 192, new com.android.internal.org.bouncycastle.crypto.generators.DESedeKeyGenerator());
            this.keySizeSet = false;
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator, javax.crypto.KeyGeneratorSpi
        protected void engineInit(int i, java.security.SecureRandom secureRandom) {
            super.engineInit(i, secureRandom);
            this.keySizeSet = true;
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator, javax.crypto.KeyGeneratorSpi
        protected javax.crypto.SecretKey engineGenerateKey() {
            if (this.uninitialised) {
                this.engine.init(new com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters(com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom(), this.defaultKeySize));
                this.uninitialised = false;
            }
            if (!this.keySizeSet) {
                byte[] generateKey = this.engine.generateKey();
                java.lang.System.arraycopy(generateKey, 0, generateKey, 16, 8);
                return new javax.crypto.spec.SecretKeySpec(generateKey, this.algName);
            }
            return new javax.crypto.spec.SecretKeySpec(this.engine.generateKey(), this.algName);
        }
    }

    public static class KeyGenerator3 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator {
        public KeyGenerator3() {
            super("DESede3", 192, new com.android.internal.org.bouncycastle.crypto.generators.DESedeKeyGenerator());
        }
    }

    public static class PBEWithSHAAndDES3Key extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher {
        public PBEWithSHAAndDES3Key() {
            super(new com.android.internal.org.bouncycastle.crypto.modes.CBCBlockCipher(new com.android.internal.org.bouncycastle.crypto.engines.DESedeEngine()), 2, 1, 192, 8);
        }
    }

    public static class PBEWithSHAAndDES2Key extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher {
        public PBEWithSHAAndDES2Key() {
            super(new com.android.internal.org.bouncycastle.crypto.modes.CBCBlockCipher(new com.android.internal.org.bouncycastle.crypto.engines.DESedeEngine()), 2, 1, 128, 8);
        }
    }

    public static class PBEWithSHAAndDES3KeyFactory extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.DES.DESPBEKeyFactory {
        public PBEWithSHAAndDES3KeyFactory() {
            super("PBEwithSHAandDES3Key-CBC", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, true, 2, 1, 192, 64);
        }
    }

    public static class PBEWithSHAAndDES2KeyFactory extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.DES.DESPBEKeyFactory {
        public PBEWithSHAAndDES2KeyFactory() {
            super("PBEwithSHAandDES2Key-CBC", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC, true, 2, 1, 128, 64);
        }
    }

    public static class KeyFactory extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory {
        public KeyFactory() {
            super(android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES, null);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory, javax.crypto.SecretKeyFactorySpi
        protected java.security.spec.KeySpec engineGetKeySpec(javax.crypto.SecretKey secretKey, java.lang.Class cls) throws java.security.spec.InvalidKeySpecException {
            if (cls == null) {
                throw new java.security.spec.InvalidKeySpecException("keySpec parameter is null");
            }
            if (secretKey == null) {
                throw new java.security.spec.InvalidKeySpecException("key parameter is null");
            }
            if (javax.crypto.spec.SecretKeySpec.class.isAssignableFrom(cls)) {
                return new javax.crypto.spec.SecretKeySpec(secretKey.getEncoded(), this.algName);
            }
            if (javax.crypto.spec.DESedeKeySpec.class.isAssignableFrom(cls)) {
                byte[] encoded = secretKey.getEncoded();
                try {
                    if (encoded.length == 16) {
                        byte[] bArr = new byte[24];
                        java.lang.System.arraycopy(encoded, 0, bArr, 0, 16);
                        java.lang.System.arraycopy(encoded, 0, bArr, 16, 8);
                        return new javax.crypto.spec.DESedeKeySpec(bArr);
                    }
                    return new javax.crypto.spec.DESedeKeySpec(encoded);
                } catch (java.lang.Exception e) {
                    throw new java.security.spec.InvalidKeySpecException(e.toString());
                }
            }
            throw new java.security.spec.InvalidKeySpecException("Invalid KeySpec");
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory, javax.crypto.SecretKeyFactorySpi
        protected javax.crypto.SecretKey engineGenerateSecret(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
            if (keySpec instanceof javax.crypto.spec.DESedeKeySpec) {
                return new javax.crypto.spec.SecretKeySpec(((javax.crypto.spec.DESedeKeySpec) keySpec).getKey(), android.security.keystore.KeyProperties.KEY_ALGORITHM_3DES);
            }
            return super.engineGenerateSecret(keySpec);
        }
    }

    public static class Mappings extends com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider {
        private static final java.lang.String PACKAGE = "com.android.internal.org.bouncycastle.jcajce.provider.symmetric";
        private static final java.lang.String PREFIX = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.DESede.class.getName();

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("Cipher.DESEDE", PREFIX + "$ECB");
            configurableProvider.addAlgorithm("Cipher.DESEDEWRAP", PREFIX + "$Wrap");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher." + com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_alg_CMS3DESwrap, "DESEDEWRAP");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.TDEA", "DESEDE");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.TDEAWRAP", "DESEDEWRAP");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", PREFIX + "$PBEWithSHAAndDES3Key");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", PREFIX + "$PBEWithSHAAndDES2Key");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pbeWithSHAAnd3_KeyTripleDES_CBC, "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pbeWithSHAAnd2_KeyTripleDES_CBC, "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDDESEDE", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND3-KEYTRIPLEDES-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND2-KEYTRIPLEDES-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND3-KEYDESEDE-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHAAND2-KEYDESEDE-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND3-KEYDESEDE-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND2-KEYDESEDE-CBC", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDDESEDE-CBC", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", PREFIX + "$PBEWithSHAAndDES3KeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", PREFIX + "$PBEWithSHAAndDES2KeyFactory");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1ANDDESEDE", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES3KEY-CBC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES2KEY-CBC", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBE", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.3", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.4", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWithSHAAnd3KeyTripleDES", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.3", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.4", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWithSHAAnd3KeyTripleDES", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        }
    }
}
