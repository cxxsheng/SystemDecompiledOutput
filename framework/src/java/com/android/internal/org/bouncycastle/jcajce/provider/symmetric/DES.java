package com.android.internal.org.bouncycastle.jcajce.provider.symmetric;

/* loaded from: classes4.dex */
public final class DES {
    private DES() {
    }

    public static class ECB extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher {
        public ECB() {
            super(new com.android.internal.org.bouncycastle.crypto.engines.DESEngine());
        }
    }

    public static class CBC extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher {
        public CBC() {
            super(new com.android.internal.org.bouncycastle.crypto.modes.CBCBlockCipher(new com.android.internal.org.bouncycastle.crypto.engines.DESEngine()), 64);
        }
    }

    public static class DES64 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac {
        public DES64() {
            super(new com.android.internal.org.bouncycastle.crypto.macs.CBCBlockCipherMac(new com.android.internal.org.bouncycastle.crypto.engines.DESEngine(), 64));
        }
    }

    public static class DES64with7816d4 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac {
        public DES64with7816d4() {
            super(new com.android.internal.org.bouncycastle.crypto.macs.CBCBlockCipherMac(new com.android.internal.org.bouncycastle.crypto.engines.DESEngine(), 64, new com.android.internal.org.bouncycastle.crypto.paddings.ISO7816d4Padding()));
        }
    }

    public static class CBCMAC extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac {
        public CBCMAC() {
            super(new com.android.internal.org.bouncycastle.crypto.macs.CBCBlockCipherMac(new com.android.internal.org.bouncycastle.crypto.engines.DESEngine()));
        }
    }

    public static class KeyGenerator extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator {
        public KeyGenerator() {
            super("DES", 64, new com.android.internal.org.bouncycastle.crypto.generators.DESKeyGenerator());
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator, javax.crypto.KeyGeneratorSpi
        protected void engineInit(int i, java.security.SecureRandom secureRandom) {
            super.engineInit(i, secureRandom);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator, javax.crypto.KeyGeneratorSpi
        protected javax.crypto.SecretKey engineGenerateKey() {
            if (this.uninitialised) {
                this.engine.init(new com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters(com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom(), this.defaultKeySize));
                this.uninitialised = false;
            }
            return new javax.crypto.spec.SecretKeySpec(this.engine.generateKey(), this.algName);
        }
    }

    public static class KeyFactory extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory {
        public KeyFactory() {
            super("DES", null);
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
            if (javax.crypto.spec.DESKeySpec.class.isAssignableFrom(cls)) {
                try {
                    return new javax.crypto.spec.DESKeySpec(secretKey.getEncoded());
                } catch (java.lang.Exception e) {
                    throw new java.security.spec.InvalidKeySpecException(e.toString());
                }
            }
            throw new java.security.spec.InvalidKeySpecException("Invalid KeySpec");
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory, javax.crypto.SecretKeyFactorySpi
        protected javax.crypto.SecretKey engineGenerateSecret(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
            if (keySpec instanceof javax.crypto.spec.DESKeySpec) {
                return new javax.crypto.spec.SecretKeySpec(((javax.crypto.spec.DESKeySpec) keySpec).getKey(), "DES");
            }
            return super.engineGenerateSecret(keySpec);
        }
    }

    public static class DESPBEKeyFactory extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory {
        private int digest;
        private boolean forCipher;
        private int ivSize;
        private int keySize;
        private int scheme;

        public DESPBEKeyFactory(java.lang.String str, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, int i, int i2, int i3, int i4) {
            super(str, aSN1ObjectIdentifier);
            this.forCipher = z;
            this.scheme = i;
            this.digest = i2;
            this.keySize = i3;
            this.ivSize = i4;
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory, javax.crypto.SecretKeyFactorySpi
        protected javax.crypto.SecretKey engineGenerateSecret(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
            com.android.internal.org.bouncycastle.crypto.CipherParameters makePBEMacParameters;
            com.android.internal.org.bouncycastle.crypto.params.KeyParameter keyParameter;
            if (keySpec instanceof javax.crypto.spec.PBEKeySpec) {
                javax.crypto.spec.PBEKeySpec pBEKeySpec = (javax.crypto.spec.PBEKeySpec) keySpec;
                if (pBEKeySpec.getSalt() == null) {
                    return new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey(this.algName, this.algOid, this.scheme, this.digest, this.keySize, this.ivSize, pBEKeySpec, null);
                }
                if (this.forCipher) {
                    makePBEMacParameters = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(pBEKeySpec, this.scheme, this.digest, this.keySize, this.ivSize);
                } else {
                    makePBEMacParameters = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEMacParameters(pBEKeySpec, this.scheme, this.digest, this.keySize);
                }
                if (makePBEMacParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
                    keyParameter = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) makePBEMacParameters).getParameters();
                } else {
                    keyParameter = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) makePBEMacParameters;
                }
                com.android.internal.org.bouncycastle.crypto.params.DESParameters.setOddParity(keyParameter.getKey());
                return new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey(this.algName, this.algOid, this.scheme, this.digest, this.keySize, this.ivSize, pBEKeySpec, makePBEMacParameters);
            }
            throw new java.security.spec.InvalidKeySpecException("Invalid KeySpec");
        }
    }

    public static class PBEWithMD5KeyFactory extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.DES.DESPBEKeyFactory {
        public PBEWithMD5KeyFactory() {
            super("PBEwithMD5andDES", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC, true, 0, 0, 64, 64);
        }
    }

    public static class PBEWithSHA1KeyFactory extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.DES.DESPBEKeyFactory {
        public PBEWithSHA1KeyFactory() {
            super("PBEwithSHA1andDES", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC, true, 0, 1, 64, 64);
        }
    }

    public static class PBEWithMD5 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher {
        public PBEWithMD5() {
            super(new com.android.internal.org.bouncycastle.crypto.modes.CBCBlockCipher(new com.android.internal.org.bouncycastle.crypto.engines.DESEngine()), 0, 0, 64, 8);
        }
    }

    public static class PBEWithSHA1 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher {
        public PBEWithSHA1() {
            super(new com.android.internal.org.bouncycastle.crypto.modes.CBCBlockCipher(new com.android.internal.org.bouncycastle.crypto.engines.DESEngine()), 0, 1, 64, 8);
        }
    }

    public static class Mappings extends com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider {
        private static final java.lang.String PACKAGE = "com.android.internal.org.bouncycastle.jcajce.provider.symmetric";
        private static final java.lang.String PREFIX = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.DES.class.getName();

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("Cipher.DES", PREFIX + "$ECB");
            configurableProvider.addAlgorithm("KeyGenerator.DES", PREFIX + "$KeyGenerator");
            configurableProvider.addAlgorithm("SecretKeyFactory.DES", PREFIX + "$KeyFactory");
            configurableProvider.addAlgorithm("AlgorithmParameters.DES", "com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters", com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.desCBC, "DES");
            configurableProvider.addAlgorithm("Cipher.PBEWITHMD5ANDDES", PREFIX + "$PBEWithMD5");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHA1ANDDES", PREFIX + "$PBEWithSHA1");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC, "PBEWITHMD5ANDDES");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC, "PBEWITHSHA1ANDDES");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHMD5ANDDES-CBC", "PBEWITHMD5ANDDES");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1ANDDES-CBC", "PBEWITHSHA1ANDDES");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHMD5ANDDES", PREFIX + "$PBEWithMD5KeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHA1ANDDES", PREFIX + "$PBEWithSHA1KeyFactory");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHMD5ANDDES-CBC", "PBEWITHMD5ANDDES");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHSHA1ANDDES-CBC", "PBEWITHSHA1ANDDES");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory." + com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC, "PBEWITHMD5ANDDES");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory." + com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC, "PBEWITHSHA1ANDDES");
        }

        private void addAlias(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) {
            configurableProvider.addAlgorithm("Alg.Alias.KeyGenerator." + aSN1ObjectIdentifier.getId(), str);
            configurableProvider.addAlgorithm("Alg.Alias.KeyFactory." + aSN1ObjectIdentifier.getId(), str);
        }
    }
}
