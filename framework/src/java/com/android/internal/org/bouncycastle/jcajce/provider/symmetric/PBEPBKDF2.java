package com.android.internal.org.bouncycastle.jcajce.provider.symmetric;

/* loaded from: classes4.dex */
public class PBEPBKDF2 {
    private static final java.util.Map prfCodes = new java.util.HashMap();

    static {
        prfCodes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA1, com.android.internal.org.bouncycastle.util.Integers.valueOf(1));
        prfCodes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA256, com.android.internal.org.bouncycastle.util.Integers.valueOf(4));
        prfCodes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA224, com.android.internal.org.bouncycastle.util.Integers.valueOf(7));
        prfCodes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA384, com.android.internal.org.bouncycastle.util.Integers.valueOf(8));
        prfCodes.put(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA512, com.android.internal.org.bouncycastle.util.Integers.valueOf(9));
    }

    private PBEPBKDF2() {
    }

    public static class AlgParams extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters {
        com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params params;

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded() {
            try {
                return this.params.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException("Oooops! " + e.toString());
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        protected byte[] engineGetEncoded(java.lang.String str) {
            if (isASN1FormatString(str)) {
                return engineGetEncoded();
            }
            return null;
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters
        protected java.security.spec.AlgorithmParameterSpec localEngineGetParameterSpec(java.lang.Class cls) throws java.security.spec.InvalidParameterSpecException {
            if (cls == javax.crypto.spec.PBEParameterSpec.class || cls == java.security.spec.AlgorithmParameterSpec.class) {
                return new javax.crypto.spec.PBEParameterSpec(this.params.getSalt(), this.params.getIterationCount().intValue());
            }
            throw new java.security.spec.InvalidParameterSpecException("unknown parameter spec passed to PBKDF2 PBE parameters object.");
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.spec.InvalidParameterSpecException {
            if (!(algorithmParameterSpec instanceof javax.crypto.spec.PBEParameterSpec)) {
                throw new java.security.spec.InvalidParameterSpecException("PBEParameterSpec required to initialise a PBKDF2 PBE parameters algorithm parameters object");
            }
            javax.crypto.spec.PBEParameterSpec pBEParameterSpec = (javax.crypto.spec.PBEParameterSpec) algorithmParameterSpec;
            this.params = new com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params(pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount());
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr) throws java.io.IOException {
            this.params = com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params.getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bArr));
        }

        @Override // java.security.AlgorithmParametersSpi
        protected void engineInit(byte[] bArr, java.lang.String str) throws java.io.IOException {
            if (isASN1FormatString(str)) {
                engineInit(bArr);
                return;
            }
            throw new java.io.IOException("Unknown parameters format in PBKDF2 parameters object");
        }

        @Override // java.security.AlgorithmParametersSpi
        protected java.lang.String engineToString() {
            return "PBKDF2 Parameters";
        }
    }

    public static class BasePBKDF2 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory {
        private int defaultDigest;
        private int ivSizeInBits;
        private int keySizeInBits;
        private int scheme;

        public BasePBKDF2(java.lang.String str, int i) {
            this(str, i, 1);
        }

        private BasePBKDF2(java.lang.String str, int i, int i2, int i3, int i4) {
            super(str, com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_PBKDF2);
            this.scheme = i;
            this.keySizeInBits = i3;
            this.ivSizeInBits = i4;
            this.defaultDigest = i2;
        }

        private BasePBKDF2(java.lang.String str, int i, int i2) {
            this(str, i, i2, 0, 0);
        }

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory, javax.crypto.SecretKeyFactorySpi
        protected javax.crypto.SecretKey engineGenerateSecret(java.security.spec.KeySpec keySpec) throws java.security.spec.InvalidKeySpecException {
            if (keySpec instanceof javax.crypto.spec.PBEKeySpec) {
                javax.crypto.spec.PBEKeySpec pBEKeySpec = (javax.crypto.spec.PBEKeySpec) keySpec;
                if (pBEKeySpec.getSalt() == null && pBEKeySpec.getIterationCount() == 0 && pBEKeySpec.getKeyLength() == 0 && pBEKeySpec.getPassword().length > 0 && this.keySizeInBits != 0) {
                    return new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey(this.algName, this.algOid, this.scheme, this.defaultDigest, this.keySizeInBits, this.ivSizeInBits, pBEKeySpec, null);
                }
                if (pBEKeySpec.getSalt() == null) {
                    throw new java.security.spec.InvalidKeySpecException("missing required salt");
                }
                if (pBEKeySpec.getIterationCount() <= 0) {
                    throw new java.security.spec.InvalidKeySpecException("positive iteration count required: " + pBEKeySpec.getIterationCount());
                }
                if (pBEKeySpec.getKeyLength() <= 0) {
                    throw new java.security.spec.InvalidKeySpecException("positive key length required: " + pBEKeySpec.getKeyLength());
                }
                if (pBEKeySpec.getPassword().length == 0) {
                    throw new java.lang.IllegalArgumentException("password empty");
                }
                if (pBEKeySpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.PBKDF2KeySpec) {
                    int digestCode = getDigestCode(((com.android.internal.org.bouncycastle.jcajce.spec.PBKDF2KeySpec) pBEKeySpec).getPrf().getAlgorithm());
                    int keyLength = pBEKeySpec.getKeyLength();
                    return new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey(this.algName, this.algOid, this.scheme, digestCode, keyLength, -1, pBEKeySpec, com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEMacParameters(pBEKeySpec, this.scheme, digestCode, keyLength));
                }
                int i = this.defaultDigest;
                int keyLength2 = pBEKeySpec.getKeyLength();
                return new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey(this.algName, this.algOid, this.scheme, i, keyLength2, -1, pBEKeySpec, com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEMacParameters(pBEKeySpec, this.scheme, i, keyLength2));
            }
            throw new java.security.spec.InvalidKeySpecException("Invalid KeySpec");
        }

        private int getDigestCode(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) throws java.security.spec.InvalidKeySpecException {
            java.lang.Integer num = (java.lang.Integer) com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.prfCodes.get(aSN1ObjectIdentifier);
            if (num != null) {
                return num.intValue();
            }
            throw new java.security.spec.InvalidKeySpecException("Invalid KeySpec: unknown PRF algorithm " + aSN1ObjectIdentifier);
        }
    }

    public static class PBKDF2withUTF8 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public PBKDF2withUTF8() {
            super("PBKDF2", 5);
        }
    }

    public static class BasePBKDF2WithHmacSHA1 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public BasePBKDF2WithHmacSHA1(java.lang.String str, int i) {
            super(str, i, 1);
        }
    }

    public static class PBKDF2WithHmacSHA1UTF8 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2WithHmacSHA1 {
        public PBKDF2WithHmacSHA1UTF8() {
            super("PBKDF2WithHmacSHA1", 5);
        }
    }

    public static class PBKDF2WithHmacSHA18BIT extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2WithHmacSHA1 {
        public PBKDF2WithHmacSHA18BIT() {
            super("PBKDF2WithHmacSHA1And8bit", 1);
        }
    }

    public static class BasePBKDF2WithHmacSHA224 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public BasePBKDF2WithHmacSHA224(java.lang.String str, int i) {
            super(str, i, 7);
        }
    }

    public static class PBKDF2WithHmacSHA224UTF8 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2WithHmacSHA224 {
        public PBKDF2WithHmacSHA224UTF8() {
            super("PBKDF2WithHmacSHA224", 5);
        }
    }

    public static class BasePBKDF2WithHmacSHA256 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public BasePBKDF2WithHmacSHA256(java.lang.String str, int i) {
            super(str, i, 4);
        }
    }

    public static class PBKDF2WithHmacSHA256UTF8 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2WithHmacSHA256 {
        public PBKDF2WithHmacSHA256UTF8() {
            super("PBKDF2WithHmacSHA256", 5);
        }
    }

    public static class BasePBKDF2WithHmacSHA384 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public BasePBKDF2WithHmacSHA384(java.lang.String str, int i) {
            super(str, i, 8);
        }
    }

    public static class PBKDF2WithHmacSHA384UTF8 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2WithHmacSHA384 {
        public PBKDF2WithHmacSHA384UTF8() {
            super("PBKDF2WithHmacSHA384", 5);
        }
    }

    public static class BasePBKDF2WithHmacSHA512 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public BasePBKDF2WithHmacSHA512(java.lang.String str, int i) {
            super(str, i, 9);
        }
    }

    public static class PBKDF2WithHmacSHA512UTF8 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2WithHmacSHA512 {
        public PBKDF2WithHmacSHA512UTF8() {
            super("PBKDF2WithHmacSHA512", 5);
        }
    }

    public static class PBEWithHmacSHA1AndAES_128 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public PBEWithHmacSHA1AndAES_128() {
            super("PBEWithHmacSHA1AndAES_128", 5, 1, 128, 128);
        }
    }

    public static class PBEWithHmacSHA224AndAES_128 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public PBEWithHmacSHA224AndAES_128() {
            super("PBEWithHmacSHA224AndAES_128", 5, 7, 128, 128);
        }
    }

    public static class PBEWithHmacSHA256AndAES_128 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public PBEWithHmacSHA256AndAES_128() {
            super("PBEWithHmacSHA256AndAES_128", 5, 4, 128, 128);
        }
    }

    public static class PBEWithHmacSHA384AndAES_128 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public PBEWithHmacSHA384AndAES_128() {
            super("PBEWithHmacSHA384AndAES_128", 5, 8, 128, 128);
        }
    }

    public static class PBEWithHmacSHA512AndAES_128 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public PBEWithHmacSHA512AndAES_128() {
            super("PBEWithHmacSHA512AndAES_128", 5, 9, 128, 128);
        }
    }

    public static class PBEWithHmacSHA1AndAES_256 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public PBEWithHmacSHA1AndAES_256() {
            super("PBEWithHmacSHA1AndAES_256", 5, 1, 256, 128);
        }
    }

    public static class PBEWithHmacSHA224AndAES_256 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public PBEWithHmacSHA224AndAES_256() {
            super("PBEWithHmacSHA224AndAES_256", 5, 7, 256, 128);
        }
    }

    public static class PBEWithHmacSHA256AndAES_256 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public PBEWithHmacSHA256AndAES_256() {
            super("PBEWithHmacSHA256AndAES_256", 5, 4, 256, 128);
        }
    }

    public static class PBEWithHmacSHA384AndAES_256 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public PBEWithHmacSHA384AndAES_256() {
            super("PBEWithHmacSHA384AndAES_256", 5, 8, 256, 128);
        }
    }

    public static class PBEWithHmacSHA512AndAES_256 extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.BasePBKDF2 {
        public PBEWithHmacSHA512AndAES_256() {
            super("PBEWithHmacSHA512AndAES_256", 5, 9, 256, 128);
        }
    }

    public static class Mappings extends com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider {
        private static final java.lang.String PREFIX = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.PBEPBKDF2.class.getName();

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2WithHmacSHA1AndUTF8", "PBKDF2WithHmacSHA1");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2with8BIT", "PBKDF2WithHmacSHA1And8BIT");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBKDF2withASCII", "PBKDF2WithHmacSHA1And8BIT");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA1", PREFIX + "$PBKDF2WithHmacSHA1UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA224", PREFIX + "$PBKDF2WithHmacSHA224UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA256", PREFIX + "$PBKDF2WithHmacSHA256UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA384", PREFIX + "$PBKDF2WithHmacSHA384UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA512", PREFIX + "$PBKDF2WithHmacSHA512UTF8");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA1AndAES_128", PREFIX + "$PBEWithHmacSHA1AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA224AndAES_128", PREFIX + "$PBEWithHmacSHA224AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA256AndAES_128", PREFIX + "$PBEWithHmacSHA256AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA384AndAES_128", PREFIX + "$PBEWithHmacSHA384AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA512AndAES_128", PREFIX + "$PBEWithHmacSHA512AndAES_128");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA1AndAES_256", PREFIX + "$PBEWithHmacSHA1AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA224AndAES_256", PREFIX + "$PBEWithHmacSHA224AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA256AndAES_256", PREFIX + "$PBEWithHmacSHA256AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA384AndAES_256", PREFIX + "$PBEWithHmacSHA384AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWithHmacSHA512AndAES_256", PREFIX + "$PBEWithHmacSHA512AndAES_256");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBKDF2WithHmacSHA1And8BIT", PREFIX + "$PBKDF2WithHmacSHA18BIT");
            configurableProvider.addPrivateAlgorithm("SecretKeyFactory.PBKDF2", PREFIX + "$PBKDF2withUTF8");
            configurableProvider.addPrivateAlgorithm("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.5.12", "PBKDF2");
        }
    }
}
